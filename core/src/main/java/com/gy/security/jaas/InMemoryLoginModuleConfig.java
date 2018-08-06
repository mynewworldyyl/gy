package com.gy.security.jaas;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import com.gy.base.SpringContext;

public class InMemoryLoginModuleConfig {
	public static final String BEAN_NAME = "common-security-inMemoryLoginModuleConfig";
	private static AtomicReference<InMemoryLoginModuleConfig> configRef = new AtomicReference<InMemoryLoginModuleConfig>();
	private List<UserDetail> users;

	public InMemoryLoginModuleConfig() {
		this.users = new ArrayList<UserDetail>();
	}

	public static InMemoryLoginModuleConfig getConfig() {
		if (configRef.get() == null) {
			InMemoryLoginModuleConfig config = (InMemoryLoginModuleConfig) SpringContext.getContext().getBean(BEAN_NAME);
			configRef.compareAndSet(null, config);
		}

		return ((InMemoryLoginModuleConfig) configRef.get());
	}

	public static void setConfig(InMemoryLoginModuleConfig config) {
		configRef.set(config);
	}

	public List<UserDetail> getUsers() {
		return this.users;
	}

	public UserDetail getUserDetail(String name) {
		for (UserDetail item : this.users) {
			if (org.apache.commons.lang.StringUtils.equalsIgnoreCase(item.getName(), name))
				return item;
		}

		return null;
	}

	public void setUsers(List<UserDetail> users) {
		this.users = users;
	}

	public static class UserDetail {
		private String name;
		private String password;

		public String getName() {
			return this.name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPassword() {
			return this.password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}
}