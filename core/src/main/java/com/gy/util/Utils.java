package com.gy.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import com.alibaba.fastjson.JSON;
import com.gy.base.Constants;
import com.gy.base.Response;
import com.gy.base.UserContext;
import com.gy.base.Constants.LOCALE;
import com.gy.base.i18n.I18NUtils;
import com.gy.usercenter.ACAccount;

public class Utils {

	private static final Utils instance = new Utils();
	private Utils(){};
	public static Utils getInstance(){return instance;}
	
	public byte[] loadStreamAsBtyes(InputStream is) {
		byte[] bytes = new byte[1024];
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int len = -1;
		try {
			while((len=is.read(bytes)) > 0) {
				baos.write(bytes, 0, len);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return baos.toByteArray();
	}
	
	public String[] getMapKeys(Map<String,String> params,String[] values) {
		if(values == null || values.length < 1) {
			return null;
		}
		if(params == null || params.isEmpty()) {
			return null;
		}
		
		Map<String,String> kvs = this.exchangeKeyValue(params);
		String[] keys = new String[values.length];
		int index = 0;
		for(String v : values) {
			keys[index++] = kvs.get(v);
		}
		return keys;
	}
	
	
	
	public String[] getMapValues(Map<String,String> params,String[] keys) {
		if(keys == null || keys.length < 1) {
			return null;
		}
		if(params == null || params.isEmpty()) {
			return null;
		}
		
		String[] values = new String[keys.length];
		int index = 0;
		for(String k : keys) {
			values[index++] = params.get(k);
		}
		return values;
	}
	
	 public Map<String,String> exchangeKeyValue(Map<String,String> params) {
	    	Map<String,String> ps = new HashMap<String,String>();
	    	for(Map.Entry<String, String> e : params.entrySet()) {
	    		ps.put(e.getValue(), e.getKey());
	    	}
	    	return ps;
		}
	    

	    public Map<String,String> arrayAsMap(String[] items) {
	    	Map<String,String> ps = new HashMap<String,String>();
	    	for(String item : items) {
	    		ps.put(item, item);
	    	}
	    	return ps;
		}
	
	public String[] getMapKeys(Map<String,String> params) {
		String[] keys = new String[params.size()];
		return params.keySet().toArray(keys);
	}
	
	public String[] getMapValues(Map<String,String> params) {
		String[] keys = new String[params.size()];
		return params.values().toArray(keys);
	}
	
	public String[] getCollectionValues(Object collection) {
		Collection c = null;
		String type = collection.getClass().getName();
		if(collection instanceof Collection) {
			c = (Collection)collection;
		}else if(collection instanceof Map) {
			c = ((Map)collection).values();
		}else if(collection instanceof Object[]) {
			c = Arrays.asList((Object[])collection);
		} else if(collection instanceof String) {
			String[] is = this.stringToArray((String)collection);
			if(is != null) {
				c = Arrays.asList(is);
			}
		}
		if(c == null || c.isEmpty()) {
			return null;
		}
		String[] vs = new String[c.size()];
		Iterator ite = c.iterator();
		for(int index = 0; ite.hasNext(); index++) {
			vs[index] = ite.next().toString().trim();
		}
		return vs;
	}
	
   
    
    public String[] stringToArray(String values) {
    	if(values == null || values.trim().equals("")) {
    		return null;
    	}
    	String[] vs = null;
		if(values.startsWith("[") && values.endsWith("]")) {
			values = values.substring(1);
			values = values.substring(0,values.length()-1);
			vs = values.split(",");
		}else {
			vs = new String[1];
			vs[0] = values;
		}
		return vs;
    }
    
    public String getResponse(Object obj,boolean state,String msg) {
    	
		Response resp = new Response(state);
		ACAccount aca = UserContext.getCurrentACAccount();
        if(aca != null) {
        	resp.setLoginKey(aca.getLoginKey());	
		}
		if(obj != null) {
			resp.setData(JsonUtils.getInstance().toJson(obj, true));
			resp.setClassType(obj.getClass().getName());
		}
		if(msg != null) {
			resp.setMsg(I18NUtils.getInstance().getString(msg));
		}
		return JsonUtils.getInstance().toJson(resp, false);
	}    
   
    
    private  DateFormat createDateFormat(Locale locale) {
		DateFormat format;

		if (LOCALE.HK.equals(locale)) {
			format = new SimpleDateFormat(Constants.DATE_FORMAT_HK);
		} else if (LOCALE.ZH.equals(locale)) {
			format = new SimpleDateFormat(Constants.DATE_FORMAT_CN);
		} else {
			format = new SimpleDateFormat(Constants.DATE_FORMAT_ISO);
		}

		format.setTimeZone(getTimeZone()); // 用于时区支持

		return format;
	}

	private  DateFormat createDateTimeFormat(Locale locale) {
		DateFormat format;

		if (LOCALE.HK.equals(locale)) {
			format = new SimpleDateFormat(Constants.DATETIME_FORMAT_HK);
		} else if (LOCALE.ZH.equals(locale)) {
			format = new SimpleDateFormat(Constants.DATETIME_FORMAT_CN);
		} else {
			format = new SimpleDateFormat(Constants.DATETIME_FORMAT_ISO);
		}

		format.setTimeZone(getTimeZone()); // 用于时区支持

		return format;
	}
	
	public  TimeZone getTimeZone() {
		/*TimeZone timeZone = null;
		HyContext context = Env.getContext();
		if (context != null) {
			timeZone = context.getTimeZone();
		}

		if (timeZone == null) {
			timeZone = CONSTANTS.DEFAULT_TIMEZONE;
		}*/

		return TimeZone.getTimeZone("PRC");
	}
    
    public  String formatDate(Date date) {
		return formatDate(date, UserContext.getLocale());
	}

	public  String formatDate(Date date, Locale locale) {
		if (locale == null) locale = UserContext.getLocale();

		return createDateFormat(locale).format(date);
	}

	public  String formatDate(Calendar calendar, Locale locale) {
		return formatDate(new Date(calendar.getTimeInMillis()), locale);
	}

	/**
	 * 格式化日期
	 * @param date
	 * @param format
	 * @return
	 */
	public  String formatDate(Date date, String format) {
		return formatDate(date, format, UserContext.getLocale());
	}

	/**
	 * 格式化日期
	 * @param date
	 * @param format
	 * @param loacle
	 * @return
	 */
	public  String formatDate(Date date, String format, Locale loacle) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format, loacle);
		dateFormat.setTimeZone(getTimeZone());
		return dateFormat.format(date);
	}

	public  String formatDateTime(Date date) {
		return formatDateTime(date, UserContext.getLocale());
	}

	public String formatDateTime(Date date, Locale locale) {
		if (locale == null) locale = UserContext.getLocale();

		return createDateTimeFormat(locale).format(date);
	}

	public String formatDateTime(Calendar calendar, Locale locale) {
		return formatDateTime(new Date(calendar.getTimeInMillis()), locale);
	}

	/**
	 * 截除时间的毫秒部分
	 * 
	 * @param date
	 * @return
	 */
	public final Date tuncateMillis(Date date) {
		return new Date(tuncateMillis(date.getTime()));
	}

	/**
	 * 截除时间的毫秒部分
	 * 
	 * @param calendar
	 * @return
	 */
	public final void tuncateMillis(Calendar calendar) {
		calendar.setTimeInMillis(tuncateMillis(calendar.getTimeInMillis()));
	}

	/**
	 * 截除时间部分
	 * @param calendar
	 */
	public final Date truncateTime(Date date) {
		Calendar calendar = Calendar.getInstance(getTimeZone(), UserContext.getLocale());
		calendar.setTime(date);
		truncateTime(calendar);
		return calendar.getTime();
	}

	/**
	 * 截除时间部分
	 * @param calendar
	 */
	public final Date truncateTime(Date date, TimeZone timeZone) {
		Calendar calendar = Calendar.getInstance(timeZone);
		calendar.setTime(date);
		truncateTime(calendar);
		return calendar.getTime();
	}

	/**
	 * 截除时间部分
	 * @param calendar
	 */
	public final  void truncateTime(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	}

	/**
	 * 截除时间的毫秒部分
	 * 
	 * @param millis
	 * @return
	 */
	public final  long tuncateMillis(long millis) {
		return (millis / 1000) * 1000;
	}
	
	public String getJsonResult(String callback,String data) {
		if(callback != null) {
			return callback+"("+data+")";
		}else {
			return data;
		}	
	}
}
