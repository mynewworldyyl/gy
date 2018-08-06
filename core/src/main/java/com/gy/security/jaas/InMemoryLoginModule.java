package com.gy.security.jaas;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

public class InMemoryLoginModule  implements LoginModule {
  private Subject subject;
  private CallbackHandler callbackHandler;
  private Map<String, ?> sharedState;
  private Map<String, ?> options;
  private boolean succeeded;
  private boolean commitSucceeded;
  private String username;
  private char[] password;
  private Principal userPrincipal;

  public InMemoryLoginModule() {
    this.succeeded = false;
    this.commitSucceeded = false;
  }

  public void initialize(Subject subject, CallbackHandler callbackHandler, 
		  Map<String, ?> sharedState, Map<String, ?> options) {
    this.subject = subject;
    this.callbackHandler = callbackHandler;
    this.sharedState = sharedState;
    this.options = options;
  }

  public boolean login() throws LoginException {
    if (this.callbackHandler == null) 
    	throw new LoginException("Error: no CallbackHandler available to garner authentication information from the user");

    Callback[] callbacks = new Callback[2];
    callbacks[0] = new NameCallback("user name: ");
    callbacks[1] = new PasswordCallback("password: ", false);
    
    try {
      this.callbackHandler.handle(callbacks);
      
      this.username = ((NameCallback)callbacks[0]).getName();
      char[] tmpPassword = ((PasswordCallback)callbacks[1]).getPassword();
      if (tmpPassword == null) {
         tmpPassword = new char[0];
      }
      this.password = new char[tmpPassword.length];
      
      System.arraycopy(tmpPassword, 0, this.password, 0, tmpPassword.length);
      
      ((PasswordCallback)callbacks[1]).clearPassword();
    } catch (IOException ioe) {
      throw new LoginException(ioe.toString());
    } catch (UnsupportedCallbackException uce) {
      throw new LoginException("Error: " + uce.getCallback().toString() + " not available to garner authentication information " + "from the user");
    }

    InMemoryLoginModuleConfig config = InMemoryLoginModuleConfig.getConfig();

    InMemoryLoginModuleConfig.UserDetail userDetail = config.getUserDetail(this.username);

    if (userDetail != null) {
      if (org.apache.commons.lang.StringUtils.equals(new String(this.password), userDetail.getPassword())) {
        this.succeeded = true;
        return true;
      }

      throw new FailedLoginException("Password Incorrect");
    }

    throw new FailedLoginException("User Name Incorrect");
  }

  public boolean commit() throws LoginException {
    if (!this.succeeded) {
      return false;
    }

    this.userPrincipal = new com.gy.security.jaas.SimplePrincipal(this.username);
    if (!(this.subject.getPrincipals().contains(this.userPrincipal))) {
    	this.subject.getPrincipals().add(this.userPrincipal);
    }

    this.username = null;
    for (int i = 0; i < this.password.length; ++i){
      this.password[i] = ' ';
    }
    
    this.password = null;
    this.commitSucceeded = true;
    
    return true;
  }

  public boolean abort() throws LoginException {
    if (!this.succeeded)
      return false;
    
    if ((this.succeeded == true) && (!this.commitSucceeded)) {
      this.succeeded = false;
      this.username = null;
      if (this.password != null) {
        for (int i = 0; i < this.password.length; ++i){
            this.password[i] = ' ';
        }
        this.password = null;
      }
      
      this.userPrincipal = null;
    } else {
      logout();
    }
    
    return true;
  }

  public boolean logout()  throws LoginException {
    this.subject.getPrincipals().remove(this.userPrincipal);
    this.succeeded = false;
    this.succeeded = this.commitSucceeded;
    this.username = null;
    if (this.password != null) {
      for (int i = 0; i < this.password.length; ++i){
          this.password[i] = ' ';
      }
      this.password = null;
    }
    
    this.userPrincipal = null;
    return true;
  }

  public Map<String, ?> getSharedState() {
    return this.sharedState;
  }

  public void setSharedState(Map<String, ?> sharedState) {
    this.sharedState = sharedState; }

  public Map<String, ?> getOptions() {
    return this.options;
  }

  public void setOptions(Map<String, ?> options) {
    this.options = options;
  }
}