package com.gy.base;

import java.util.Locale;

import com.gy.entity.Account;
import com.gy.usercenter.ACAccount;

public class UserContext {

    private static ThreadLocal<ACAccount> entityThreadLocal = new ThreadLocal<ACAccount>();
    //private static final String CURRENT_USER_KEY = "_current_user_key";
    public static final String CURRENT_LOCALE_KEY = "_current_locale_key";

    public static boolean DEBUG = true;
    
    public static String getCurrentClientId() {
        return getAccount().getClient().getId();
    }

    public static String getCurrentUserId() {
        return getAccount().getId().toString();
    }

    public final static ACAccount getCurrentACAccount() {
    	ACAccount aca =  entityThreadLocal.get();
        return aca;
    }
    
    public final static Account getAccount() {
      return (Account)UserContext.getCurrentACAccount().getAcct();
    }
    

    public static void releaseContext() {
        entityThreadLocal.remove();
    }

    public static void setLocale(Locale locale) {
        entityThreadLocal.get().setAttribute(CURRENT_LOCALE_KEY, locale);
    }

    public static Locale getLocale() {
        if (entityThreadLocal.get() != null) {
            Locale locale = (Locale)entityThreadLocal.get().getAttribute(CURRENT_LOCALE_KEY);
            if (locale != null) {
                return locale;
            }
        }
        return Locale.getDefault();
    }
    
    public static void initForTestcase(ACAccount aca) {
    	init(aca);
    }
    
    public static void init(ACAccount aca) {
    	 entityThreadLocal.set(aca);
    }

	/*public static void init(String accountName, String clientId, String localeStr) {
		
		if(accountName == null || "".equals(accountName.trim())) {
			throw new RuntimeException("User not login");
		}
		if(clientId == null || "".equals(clientId.trim())) {
			throw new RuntimeException("User not login");
		}
		if(localeStr == null || "".equals(localeStr.trim())) {
			throw new RuntimeException("User not login");
		}
		String[] cl = localeStr.split(",");
		Locale l = null;
		if(cl.length >=2) {
			l = new Locale(cl[0],cl[1]);
		}else {
			l = new Locale(cl[0]);
		}
		//init( accountName,  clientId,  l);
    
	}*/
	
  /* public static void init(String accountName, String clientId, Locale l) {
		IAccountDao acctMapper =  SpringContext.getContext().getBean(IAccountDao.class);
		Account account = acctMapper.selectByName(accountName);
		
		ACAccount aca = new ACAccount();
		aca.setAccountName(account.getUserName());
		aca.setAcct(aca);
		aca.setAcct(account);
		aca.setId(this.generator.getStringId(ACAccount.class));
		
		UserInfo ui = new UserInfo(account.getClient(),account,l);
        UserSession s = new UserSession(ui);
        entityThreadLocal.set(s);
	}*/
   
   
}
