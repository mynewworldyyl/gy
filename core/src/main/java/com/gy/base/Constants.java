package com.gy.base;

import java.util.Locale;
import java.util.TimeZone;

/**
 * 
 * @author Rick zhai
 * 2013-12-2 下午12:03:57
 */
public interface Constants {
	public static final String CLIENT_ID = "clientId";
	
	public static final String USER_NAME = "userName";
	
	public static final String PASSWORD = "pwd";

	public static interface WebAppAttribute {
		public final static String APP_NAME = "APP_NAME";
		public final static String ROOT_REAL_PATH = "ROOT_REAL_PATH";
		public final static String APP_ONLINE_USERS = "APP_ONLINE_USERS";
	}
	
	public static interface SessionAttribute {
		public final static String HY_CONTEXT = "HY_CONTEXT";
		public final static String LOGON_CONTEXT = "LG_CONTEXT";
		public final static String LOGIN_COMPANY = "LG_COMPANY";
		public static final String LOGON_USER = "LG_USER";
		public static final String LOGON_USER_NAME = "LG_USER_NAME";
		public static final String LOGON_USER_NUMBER = "LG_USER_NUMBER";
		public static final String LOGON_RELAYSTATE = "LOGON_RELAYSTATE";
		public static final String LOGON_TIME = "LOGON_TIME";
		public static final String HY_KICKOUT = "HY_KICKOUT";
	}

	public static interface LOCALE {
		public static Locale HK = new Locale("uk", "CN", "HK");
		public static Locale ZH = new Locale("zh", "CN", "");
		public static Locale Default = Locale.CHINA;
	}

	public static final String DATE_FORMAT_ISO = "yyyy-MM-dd";

	public static final String DATE_FORMAT_CN = DATE_FORMAT_ISO;

	public static final String DATETIME_FORMAT_ISO = "yyyy-MM-dd HH:mm:ss";

	public static final String DATETIME_FORMAT_CN = DATETIME_FORMAT_ISO;

	public static final String DATE_FORMAT_HK = "dd/MM/yyyy";

	public static final String DATETIME_FORMAT_HK = "dd/MM/yyyy HH:mm:ss";
	
	public static final TimeZone DEFAULT_TIMEZONE = TimeZone.getTimeZone("PRC");

}
