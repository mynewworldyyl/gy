package com.gy.security;

import java.security.Principal;

public interface HyTenantPrincipal extends Principal {
	  public abstract long getDbId();

	  public abstract void setDbId(long paramLong);
}