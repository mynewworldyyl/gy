package com.gy.security;

import java.security.Principal;

public interface ApplicationPrincipal  extends Principal {
	  public abstract long getId();

	  public abstract void setId(long paramLong);

	  public abstract void setName(String paramString);

	  public abstract String getDisplayName();

	  public abstract void setDisplayName(String paramString);

	  public abstract String getRemoteHost();

	  public abstract void setRemoteHost(String paramString);
	}