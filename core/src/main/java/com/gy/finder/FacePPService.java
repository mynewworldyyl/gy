package com.gy.finder;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.facepp.error.FaceppParseException;
import com.facepp.http.HttpRequests;
import com.facepp.http.PostParameters;
import com.gy.base.CommonException;
import com.gy.base.SpringContext;
import com.gy.base.i18n.I18NUtils;
import com.gy.base.id.CacheBaseIDManager;
import com.gy.entity.Account;
import com.gy.finder.entity.CollectLostInfo;
import com.gy.finder.entity.Face;
import com.gy.finder.entity.ImageData;
import com.gy.finder.entity.ImageMatch;
import com.gy.finder.entity.LostInfo;
import com.gy.finder.entity.Task;
import com.gy.util.Utils;

@Component
@Scope("singleton")
public class FacePPService {
	
	private final static Logger logger = Logger.getLogger(FacePPService.class);
	
	public static final String TASK_TYPE_LOST_INFO = "LostInfoImage";
	public static final String TASK_TYPE_COLL_LOST_INFO = "CollectLostInfoImage";

	public static final int SINGLE_TASK_SIZE = 10;
	
	enum LostInfoStep {

		init(1),
		createPerson(2),
		detectionDetect(3),
		personAddFace(4),
		faceCompare(4)
		;
		
		LostInfoStep(int code) {
			this.code = code;
		}
		
		public int getCode() {
			return this.code;
		}
		
		private final int code;
		
	};
	
	@Value("#{configProperties['facePlusPlusKey']}")
	private String apiKey;
	
	@Value("#{configProperties['facePlusPlusSecret']}")
	private String apiSecret;
	
	@Value("#{configProperties['localServerUrl']}")
	private String localServerUrl;
	
	@Value("#{configProperties['messageServerUrl']}")
	private String messageServerUrl;
	
	@Value("#{configProperties['connectionTimeout']}")
	private int connectionTimeout;
	
	@Value("#{configProperties['http.timeout']}")
	private int timeout = 0;
	
	private boolean isCN=true;
	
	private boolean isDebug=true;
	
	private Executor executor = Executors.newFixedThreadPool(1);
	
	private int processorNum = Runtime.getRuntime().availableProcessors();
	private Worker[] workers = new Worker[processorNum];
	
	private ThreadLocal<CacheBaseIDManager> idGenLocal = new ThreadLocal<CacheBaseIDManager>();
	private ThreadLocal<LostInfoDao> lostInfoDaoLocal = new ThreadLocal<LostInfoDao>();
	private ThreadLocal<FacePPService> faceServicenLocal = new ThreadLocal<FacePPService>();
	
	@PostConstruct
	public void init() {
		for(int i=0; i< this.processorNum; i++) {
			this.workers[i] = new Worker(i);
		}
		 initHttpClient();
	}
	
	public void startWork(long[] taskIds) {
		for(int index = 0; index < taskIds.length; index++) {
			int divider = (int)taskIds[index]%this.processorNum;
			if(this.workers[divider].isRunning()){
				continue;
			}
			this.executor.execute(this.workers[divider]);
		}
	}
	
	private void initThreadLocal() {
		CacheBaseIDManager idGenerator = SpringContext.getContext().getBean(CacheBaseIDManager.class);
		idGenLocal.set(idGenerator);
		
		LostInfoDao lostInfoDao = SpringContext.getContext().getBean(LostInfoDao.class);
		lostInfoDaoLocal.set(lostInfoDao);
		
		FacePPService faceService = SpringContext.getContext().getBean(FacePPService.class);
		faceServicenLocal.set(faceService);
	}
	
	private class Worker implements Runnable {

		private int id;
		
		private boolean isInit = false;
		
		private boolean isRunning = false;
		
		public boolean isRunning() {
			synchronized(this) {
				return this.isRunning;
			}
		}
		
		public void setRunntin(boolean r) {
			synchronized(this) {
				if(this.isRunning) {
					return;
				}
				this.isRunning = true;
			}
		}
		
		public Worker(int id) {
			this.id = id;
		}
		
		public void initWorker() {

			if(!this.isInit) {
				synchronized(this) {
					if(!this.isInit) {
						initThreadLocal();
						this.isInit = true;
					}
				}
			}
			
		}
		
		@Override
		public void run() {
			
			if(this.isRunning) {
				return;
			}
			setRunntin(true);
			initWorker();
			
			while(true) {
				
				List<Task> tasks = FacePPService.this.selectTasks(id);
				if(tasks == null || tasks.size() < 1) {
					break;
				}
				
				Iterator<Task> ite = tasks.iterator();
				while(ite.hasNext()) {
					Task task = ite.next();
					try {
						if(TASK_TYPE_LOST_INFO.equals(task.getTypecode())) {
							faceServicenLocal.get().handleLostInfo(task);
						}else if(TASK_TYPE_COLL_LOST_INFO.equals(task.getTypecode())) {
							faceServicenLocal.get().handleCollectionLostInfo(task);
						}
					} catch (Exception e) {
						//backup the task by the step
						logger.error("",e);
						break;
					}
				}
				if(tasks.size() == SINGLE_TASK_SIZE) {
					try {
						//每10秒运行一批次
						Thread.sleep(1000*10);
					} catch (InterruptedException e) {
						logger.equals(e);
					}
				} else {
					break;
				}
			}
			setRunntin(false);
		}
		
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void handleLostInfo(Task task) throws JSONException {
		
		LostInfo lostInfo = this.lostInfoDaoLocal.get().getEntityManager()
				.createNamedQuery("LostInfoSelectById", LostInfo.class)
				.setParameter("id", task.getRefId())
				.getSingleResult();
		
		if(lostInfo == null) {
			this.updateInvalidTask(task,I18NUtils.getInstance().getString("FinderTaskNoRelatedLostInfo"));
			throw new CommonException("FinderTaskNoRelatedLostInfo");
		}
		
		Account act = task.getCreatedBy();
		if(act.getPersonId() == null) {
			this.createPerson(task);
		}
		
		ImageData imageData = this.lostInfoDaoLocal.get().getEntityManager()
				.createNamedQuery("ImageDataSelectByRefId", ImageData.class)
				.setParameter("refId", lostInfo.getId())
				.setParameter("ownerMod", task.getTypecode())
				.getSingleResult();
		if(imageData == null) {
			this.updateInvalidTask(task,I18NUtils.getInstance().getString("FinderTaskNoRelatedImageData"));
			throw new CommonException("FinderTaskNoRelatedImageData");
		}
				
		HttpRequests req = this.createRequest();
		PostParameters params = new PostParameters();
		params.setAsync(false);
		String url =  this.localServerUrl + "/bbs/file/download?fileId=559b7c4a1f0fa3c86bfc7d4d"; //this.localServerUrl + imageData.getImageUrl();
		params.setUrl(url);
		params.setMode("oneface");
		
		boolean isSuccess = true;
		JSONObject rst=null;
		try {
			rst = req.detectionDetect(params);
			//isSuccess = rst.getBoolean("success");
		} catch (FaceppParseException e) {
			isSuccess = false;
			logger.error(e);
		}
		if(!isSuccess) {
			this.updateTask(task,I18NUtils.getInstance().getString("FailToParseImage")
					,true,true,false,Task.STATUS_FAIL,LostInfoStep.detectionDetect.getCode());
			throw new CommonException("FailToParseImage");
		}

		//save image data
		imageData.setSessionId(rst.getString("session_id"));
		imageData.setFaceCount(rst.getJSONArray("face").length());
		imageData.setGotResultOn(new Date());
		imageData.setTimeout(72);
		imageData.setThirdImgId(rst.getString("img_id"));
		imageData.setImgWidth(rst.getInt("img_width"));
		imageData.setImgHeight(rst.getInt("img_height"));
		imageData.setResult(rst.toString());
		
		this.updateImageData(imageData);
		
		//save face 
		JSONArray faces = rst.getJSONArray("face");
		if(faces.length() ==0) {
			this.updateInvalidTask(task,I18NUtils.getInstance().getString("ImageDataNoFace"));
			throw new CommonException("ImageDataNoFace");
		}
		
		JSONObject f = faces.getJSONObject(0);
		Face face = new Face();
		face.setClient(task.getClient());
		face.setCreatedBy(task.getCreatedBy());
		face.setCreatedOn(new Date());
		face.setFaceId(f.getString("face_id"));
		face.setFaceJson(f.toString());
		face.setId(this.idGenLocal.get().getNumId(Face.class).longValue());
		face.setImageData(imageData);
		face.setOwnerMod(TASK_TYPE_LOST_INFO);
		face.setUpdatedBy(task.getUpdatedBy());
		face.setUpdatedOn(new Date());
		this.saveFace(face);
		
		//将face 加到person中
		isSuccess = true;
		req = this.createRequest();
		params = new PostParameters()
		.setPersonId(act.getPersonId())
		.setFaceId(new String[]{face.getFaceId()});
		try {
			rst = req.personAddFace(params);
			isSuccess = rst.getBoolean("success");
		} catch (FaceppParseException e) {
			isSuccess = false;
		}
		if(!isSuccess) {
			this.updateTask(task,I18NUtils.getInstance().getString("FailToAddFaceToPerson")
					,true,true,false,Task.STATUS_FAIL,LostInfoStep.personAddFace.getCode());
			throw new CommonException("FailToAddFaceToPerson");
		}
		
		List<Face> collLostInfoFaces = this.lostInfoDaoLocal.get().getEntityManager()
				.createNamedQuery("FaceSelectCollLostInfoFaces", Face.class)
				.setParameter("ownerMod", TASK_TYPE_COLL_LOST_INFO)
				//.setParameter("lostDate", lostInfo.getLostDate())
				.getResultList();
		if(collLostInfoFaces == null || collLostInfoFaces.isEmpty()) {
			this.updateEndTask(task,I18NUtils.getInstance().getString("NoValidCollLostInfo"));
			return;
		}
		
		Iterator<Number> imgMatchIds = this.idGenLocal.get().getNumIds(ImageMatch.class, 
				task.getClient().getId(), collLostInfoFaces.size()).iterator();
		
		StringBuffer sb = new StringBuffer(task.getExt0());
		

		for(Face collFace : collLostInfoFaces) {
			isSuccess = true;
			req = this.createRequest();
			params = new PostParameters()
			.setFaceId1(face.getFaceId())
			.setFaceId2(collFace.getFaceId())
			.setAsync(false);
			try {
				rst = req.recognitionCompare(params);
				//isSuccess = rst.getBoolean("success");
			} catch (FaceppParseException e) {
				isSuccess = false;
			}
			if(!isSuccess) {
				task.setExt0(sb.toString());
				this.updateTask(task,I18NUtils.getInstance().getString("FailToFaceCompare")
						,true,true,false,Task.STATUS_FAIL,LostInfoStep.faceCompare.getCode());
				throw new CommonException("FailToFaceCompare");
			}
			
			sb.append(face.getId()).append(":").append(collFace.getId()).append(";");
			
			ImageMatch im = new ImageMatch();
			im.setClient(task.getClient());
			im.setCollectLostInfo(this.lostInfoDaoLocal.get().getEntityManager()
					.find(CollectLostInfo.class, collFace.getImageData().getRefId()));
			im.setCreatedBy(task.getCreatedBy());
			im.setCreatedOn(new Date());
			im.setDestFaceId(collFace.getId());
			JSONObject jo = rst.getJSONObject("component_similarity");
			im.setEye(jo.getDouble("eye"));
			im.setEyebrow(jo.getDouble("eyebrow"));
			im.setMonth(jo.getDouble("mouth"));
			im.setNose(jo.getDouble("nose"));
			im.setId(imgMatchIds.next().longValue());
			im.setLostInfo(lostInfo);
			im.setOwnerMod(TASK_TYPE_LOST_INFO);
			im.setResult(rst.toString());
			im.setSimilarity(rst.getDouble("similarity"));
			im.setSrcFaceId(face.getId());
			im.setUpdatedBy(task.getUpdatedBy());
			im.setUpdatedOn(new Date());
			this.saveImageMatch(im);
			sb.append(face.getId()).append(":").append(collFace.getId()).append(";");
		}
		
		this.updateEndTask(task,I18NUtils.getInstance().getString("TaskNomalEnd"));
	}
	
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void handleCollectionLostInfo(Task task) throws JSONException {
		
    	CollectLostInfo lostInfo = this.lostInfoDaoLocal.get().getEntityManager()
				.createNamedQuery("CollectLostInfoSelectById", CollectLostInfo.class)
				.setParameter("id", task.getRefId())
				.getSingleResult();
		
		if(lostInfo == null) {
			this.updateInvalidTask(task,I18NUtils.getInstance().getString("FinderTaskNoRelatedLostInfo"));
			throw new CommonException("FinderTaskNoRelatedLostInfo");
		}
		
		Account act = task.getCreatedBy();
		if(act.getPersonId() == null) {
			this.createPerson(task);
		}
		
		ImageData imageData = this.lostInfoDaoLocal.get().getEntityManager()
				.createNamedQuery("ImageDataSelectByRefId", ImageData.class)
				.setParameter("refId", lostInfo.getId())
				.setParameter("ownerMod", task.getTypecode())
				.getSingleResult();
		if(imageData == null) {
			this.updateInvalidTask(task,I18NUtils.getInstance().getString("FinderTaskNoRelatedImageData"));
			throw new CommonException("FinderTaskNoRelatedImageData");
		}
				
		HttpRequests req = this.createRequest();
		PostParameters params = new PostParameters();
		params.setAsync(false);
		String url =  this.localServerUrl + "/bbs/file/download?fileId=559bd03d93200478127a14e3";// this.localServerUrl + imageData.getImageUrl();
		params.setUrl(url);
		params.setMode("oneface");
		
		boolean isSuccess = true;
		JSONObject rst=null;
		try {
			rst = req.detectionDetect(params);
			//isSuccess = rst.getBoolean("success");
		} catch (FaceppParseException e) {
			isSuccess = false;
		}
		if(!isSuccess) {
			this.updateTask(task,I18NUtils.getInstance().getString("FailToParseImage")
					,true,true,false,Task.STATUS_FAIL,LostInfoStep.detectionDetect.getCode());
			throw new CommonException("FailToParseImage");
		}

		//save image data
		imageData.setSessionId(rst.getString("session_id"));
		imageData.setFaceCount(rst.getJSONArray("face").length());
		imageData.setGotResultOn(new Date());
		imageData.setTimeout(72);
		imageData.setThirdImgId(rst.getString("img_id"));
		imageData.setImgWidth(rst.getInt("img_width"));
		imageData.setImgHeight(rst.getInt("img_height"));
		imageData.setResult(rst.toString());
		this.updateImageData(imageData);
		
		//save face 
		JSONArray faces = rst.getJSONArray("face");
		if(faces.length() ==0) {
			this.updateInvalidTask(task,I18NUtils.getInstance().getString("ImageDataNoFace"));
			throw new CommonException("ImageDataNoFace");
		}
		
		JSONObject f = faces.getJSONObject(0);
		Face face = new Face();
		face.setClient(task.getClient());
		face.setCreatedBy(task.getCreatedBy());
		face.setCreatedOn(new Date());
		face.setFaceId(f.getString("face_id"));
		face.setFaceJson(f.toString());
		face.setId(this.idGenLocal.get().getNumId(Face.class).longValue());
		face.setImageData(imageData);
		face.setOwnerMod(TASK_TYPE_COLL_LOST_INFO);
		face.setUpdatedBy(task.getUpdatedBy());
		face.setUpdatedOn(new Date());
		saveFace(face);
		
		//将face 加到person中
		isSuccess = true;
		req = this.createRequest();
		params = new PostParameters()
		.setPersonId(act.getPersonId())
		.setFaceId(new String[]{face.getFaceId()});
		try {
			rst = req.personAddFace(params);
			isSuccess = rst.getBoolean("success");
		} catch (FaceppParseException e) {
			isSuccess = false;
		}
		if(!isSuccess) {
			this.updateTask(task,I18NUtils.getInstance().getString("FailToAddFaceToPerson")
					,true,true,false,Task.STATUS_FAIL,LostInfoStep.personAddFace.getCode());
			throw new CommonException("FailToAddFaceToPerson");
		}
		
		List<Face> lostInfoFaces = this.lostInfoDaoLocal.get().getEntityManager()
				.createNamedQuery("FaceSelectLostInfoFaces", Face.class)
				.setParameter("ownerMod", TASK_TYPE_LOST_INFO)
				//.setParameter("lostDate", lostInfo.getLostDate())
				.getResultList();
		if(lostInfoFaces == null || lostInfoFaces.isEmpty()) {
			this.updateEndTask(task,I18NUtils.getInstance().getString("NoValidCollLostInfo"));
			return;
		}
		
		Iterator<Number> imgMatchIds = this.idGenLocal.get().getNumIds(ImageMatch.class, 
				task.getClient().getId(), lostInfoFaces.size()).iterator();
		
		StringBuffer sb = new StringBuffer(task.getExt0());
		for(Face lostFace : lostInfoFaces) {
			isSuccess = true;
			req = this.createRequest();
			params = new PostParameters()
			.setFaceId1(lostFace.getFaceId())
			.setFaceId2(face.getFaceId())
			.setAsync(false);
			try {
				rst = req.recognitionCompare(params);
			} catch (FaceppParseException e) {
				isSuccess = false;
				task.setExt0(sb.toString());
				this.updateTask(task,I18NUtils.getInstance().getString("FailToFaceCompare")
						,true,true,false,Task.STATUS_FAIL,LostInfoStep.faceCompare.getCode());
				throw new CommonException("FailToFaceCompare",e);
			}
			
			ImageMatch im = new ImageMatch();
			im.setClient(task.getClient());
			im.setCollectLostInfo(lostInfo);
			im.setCreatedBy(task.getCreatedBy());
			im.setCreatedOn(new Date());
			im.setDestFaceId(face.getId());
			JSONObject jo = rst.getJSONObject("component_similarity");
			im.setEye(jo.getDouble("eye"));
			im.setEyebrow(jo.getDouble("eyebrow"));
			im.setMonth(jo.getDouble("mouth"));
			im.setNose(jo.getDouble("nose"));
			im.setId(imgMatchIds.next().longValue());
			im.setLostInfo(this.lostInfoDaoLocal.get().getEntityManager()
					.find(LostInfo.class, lostFace.getImageData().getRefId()));
			im.setOwnerMod(TASK_TYPE_LOST_INFO);
			im.setResult(rst.toString());
			im.setSimilarity(rst.getDouble("similarity"));
			im.setSrcFaceId(lostFace.getId());
			im.setUpdatedBy(task.getUpdatedBy());
			im.setUpdatedOn(new Date());
			this.saveImageMatch(im);
			sb.append(lostFace.getId()).append(":").append(face.getId()).append(";");
		}
		this.updateEndTask(task,I18NUtils.getInstance().getString("TaskNomalEnd"));
	}
	
	
	private  HttpClient httpClient = null;
	
	@SuppressWarnings("deprecation")
	private void  initHttpClient() {
		 httpClient = new DefaultHttpClient();
         HttpParams httpParams = httpClient.getParams();
         HttpProtocolParams.setContentCharset(httpParams, "UTF-8");
         HttpConnectionParams.setConnectionTimeout(httpParams, connectionTimeout);
         
         HttpConnectionParams.setSoTimeout(httpParams, timeout);
	}
	
	public void notifyTaskEnd(Long taskId) {
		this.initThreadLocal();
       Task task = this.lostInfoDaoLocal.get().getEntityManager().find(Task.class, taskId);
       if(task != null) {
    	   this.notifyTaskEnd(task);
       }
	}
	
	public void notifyTaskEnd(Task task) {
		
        StringBuffer sb = new StringBuffer(this.messageServerUrl);
        HttpPost request = new HttpPost(sb.toString());
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
        nvps.add(new BasicNameValuePair("taskId", task.getId()+""));  
        String content = null;
        try {
        	 request.setEntity(new UrlEncodedFormEntity(nvps));  
			HttpResponse response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
		    content = EntityUtils.toString(entity, HttpProtocolParams.getContentCharset(httpClient.getParams()));
			/*Type type = new TypeToken<Set<String>>(){}.getType();
			content=content.substring(1, content.length()-1);
			content = content.replaceAll("\\\\", "");
			ids = new Gson().fromJson(content, type);*/
		} catch (Exception e) {
		    throw new RuntimeException(content,e);
		} 
	
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor=Throwable.class)
	public void saveFace(Face face) {
		this.lostInfoDaoLocal.get().getEntityManager().persist(face);
	}

	private void updateInvalidTask(Task task, String remark) {
		this.updateTask(task, remark, false, false, false, Task.STATUS_FAIL, -1);
	}
	
	private void updateEndTask(Task task, String remark) {
		this.updateTask(task, remark, false, false, true, Task.STATUS_SUCCESS, -1);
		notifyTaskEnd(task);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor=Throwable.class)
	public void saveImageMatch(ImageMatch imageMatch) {
		this.lostInfoDaoLocal.get().getEntityManager().persist(imageMatch);
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor=Throwable.class)
	public void updateImageData(ImageData imageData) {
		this.lostInfoDaoLocal.get().getEntityManager().merge(imageData);
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor=Throwable.class)
	public void updateTask(Task task, String remark, boolean isValid
			, boolean readyForNext, boolean isFinish,String status,int stepCode) {
		
		StringBuffer sb = new StringBuffer(task.getRemark());
		sb.append(Utils.getInstance().formatDate(new Date()))
		.append(remark)
		.append(System.getProperty("line.separator"));
		task.setRemark(sb.toString());
		task.setValid(isValid);
		task.setReadyForNext(readyForNext);
		task.setIsFinish(isFinish);
		task.setStatus(status);
		if(stepCode != -1) {
			task.setStepIndex(stepCode);
		}
		this.lostInfoDaoLocal.get().getEntityManager().merge(task);
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor=Throwable.class)
	public void createPerson(Task task) {
		
		 Account act = task.getCreatedBy();
		HttpRequests req = this.createRequest();
		boolean isSuccess = true;
		Exception ex = null;
		
		try {
			JSONObject rst = req.personCreate();
			String personId = rst.getString("person_id");
			act.setPersonId(personId);
			this.lostInfoDaoLocal.get().getEntityManager().merge(act);
		} catch (FaceppParseException e) {
			isSuccess=false;
			ex =e;
		} catch (JSONException e) {
			isSuccess=false;
			ex =e;
		}
		
		if(!isSuccess) {
			this.updateTask(task,I18NUtils.getInstance().getString("FailToCreatePerson")
					,true,true,false,Task.STATUS_FAIL,LostInfoStep.createPerson.getCode());
			throw new CommonException("FailToCreatePerson",ex);
		}
	}

	private HttpRequests createRequest() {
		return new HttpRequests(this.apiKey,this.apiSecret,this.isCN, this.isDebug);
	}
	
	private List<Task> selectTasks(int dividerNum) {
		List<Task> tasks = this.lostInfoDaoLocal.get().getEntityManager()
		.createNamedQuery("TaskSelectNotFinish", Task.class)
		//.setParameter("typecode", typecode)
		.setParameter("modeNum", this.processorNum)
		.setParameter("dividerNum", dividerNum)
		.setMaxResults(SINGLE_TASK_SIZE)
		.getResultList();
		return tasks;
	}
	
	
}
