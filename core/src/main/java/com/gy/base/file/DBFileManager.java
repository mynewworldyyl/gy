package com.gy.base.file;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.fileupload.FileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gy.base.UserContext;
import com.gy.base.id.CacheBaseIDManager;
import com.gy.usercenter.AccountCenterManager;
import com.gy.util.Utils;

@Component
public class DBFileManager {

	@Autowired
	private DbFileDao fileDao;
	
	@Autowired
	private CacheBaseIDManager generator;
	
	@Autowired
	private AccountCenterManager acManager;
	
	
	public DbFile getLatestFile(String contentType) {
		DbFile file = (DbFile)fileDao.getEntityManager().createNamedQuery("selectLatest")
		.setParameter("contentType", contentType)
		.getSingleResult();
		return file;
	}
	
	
	public DbFile getDbFile(String fileId) {
		return this.fileDao.find(DbFile.class, fileId);
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public String processFiles(List<FileItem> items) {
		if(items == null || items.isEmpty()) {
			return Utils.getInstance().getResponse(null, false, null);
		}
		
		String specfileType = null;
		
		Set<FileAttr> attrs = new HashSet<FileAttr>();
		
		for(FileItem i : items) {
			if(!i.isFormField()) {
				continue;
			}
			FileAttr fa = new FileAttr();
			fa.setClient(UserContext.getAccount().getClient());
			fa.setName(i.getFieldName());
			fa.setValue(i.getString());
			attrs.add(fa);
		}
		
		Map<String,String> nameToIds = new HashMap<String,String>();
		for(FileItem i : items) {
			if(i.isFormField()) {
				continue;
			}
			String fileType = specfileType;
			if(fileType == null) {
				fileType = this.getContentType(i);
			}
			
			DbFile f = new DbFile();
			f.setId(generator.getStringId(DbFile.class));
			f.setClient(UserContext.getAccount().getClient());
			f.setContentType(fileType);
			f.setMineType(i.getContentType());
			f.setName(i.getName());
			//f.setOwner(UserContext.getAccount());
			f.setData(this.getData(i));
			
			for(FileAttr fa : attrs) {
				FileAttr fac=null;
				try {
					fac = fa.clone();
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				f.getAttrs().add(fac);
				fac.setId(generator.getStringId(FileAttr.class));
				fac.setFile(f);
			}
			
			this.fileDao.save(f);
			nameToIds.put(f.getName(), f.getId());
		}
		
		String resp = Utils.getInstance().getResponse(nameToIds, true, null);
		return resp;
		
	}
	
	private String getContentType(FileItem i) {
		String type = DbFile.ContentType.Normal.name();
		String contentType = i.getContentType();		
		if(i.getName().toLowerCase().endsWith(".apk")) {
			type = DbFile.ContentType.APK.name();
		}else if(contentType.startsWith("image")) {
			type = DbFile.ContentType.Image.name();
		}
		return type;
	}

	private byte[] getData(FileItem i) {
		if(i == null) {
			return null;
		}
		return i.get();
	}
	
	
}
