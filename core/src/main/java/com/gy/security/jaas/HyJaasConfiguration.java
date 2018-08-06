package com.gy.security.jaas;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;

public class HyJaasConfiguration extends Configuration {
	public static final String ENTRY_NAME = "HaiYan";
	private Map<String, List<AppConfigurationEntry>> entries = new HashMap<String,List<AppConfigurationEntry>>();

	public AppConfigurationEntry[] getAppConfigurationEntry(String name) {
		List<AppConfigurationEntry> item = this.entries.get(name);
		if (item == null) {
			return new AppConfigurationEntry[0];
		}

		return ((AppConfigurationEntry[]) item.toArray(new AppConfigurationEntry[item.size()]));
	}

	public void refresh() {
	}

	public Map<String, List<AppConfigurationEntry>> getEntries() {
		return this.entries;
	}

	public void setEntries(Map<String, List<AppConfigurationEntry>> entries) {
		this.entries = entries;
	}

	public static AppConfigurationEntry createEntry(String loginModuleName,
			String controlFlagName) {
		return createEntry(loginModuleName, controlFlagName, Collections.singletonMap("debug", "true"));
	}

	public static AppConfigurationEntry createEntry(String loginModuleName,
			String controlFlagName, Map<String, ?> options) {
		AppConfigurationEntry.LoginModuleControlFlag controlFlag;
		if ("required".equalsIgnoreCase(controlFlagName))
			controlFlag = AppConfigurationEntry.LoginModuleControlFlag.REQUIRED;
		else if ("requisite".equalsIgnoreCase(controlFlagName))
			controlFlag = AppConfigurationEntry.LoginModuleControlFlag.REQUISITE;
		else if ("sufficient".equalsIgnoreCase(controlFlagName))
			controlFlag = AppConfigurationEntry.LoginModuleControlFlag.SUFFICIENT;
		else if ("optional".equalsIgnoreCase(controlFlagName))
			controlFlag = AppConfigurationEntry.LoginModuleControlFlag.OPTIONAL;
		else {
			throw new IllegalArgumentException("not support controlFlag : " + controlFlagName);
		}

		AppConfigurationEntry entry = new AppConfigurationEntry(loginModuleName, controlFlag, options);

		return entry;
	}
}
