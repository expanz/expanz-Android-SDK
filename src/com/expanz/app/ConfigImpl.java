package com.expanz.app;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.expanz.ExpanzApplication;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Implementation of the Config Object design pattern, holds configuration 
 * information and can be passed around or injected easily for lookup. 
 * 
 * All configuration parameters should be resolved via this class only. 
 * 
 * Default properties have convenience methods e.g. {@link Config.getExpanzSite}. All
 * application specific properties should use the {@link Config.getAppSpecificProperty} 
 * method. 
 *
 */
@Singleton
public class ConfigImpl implements Config {
	
	/**
	 * Has this config been initialized
	 */
	private boolean initialized;
	
	/**
	 * Holds each of the config properties
	 */
	private Map<String, String> configProperties = new HashMap<String, String>();
	
	@Inject
	ExpanzApplication application;
	
	/**
	 * {@inheritDoc}
	 */
	public void init() {
		
		if(!initialized) {
			initialized = true;
		} else {
			return; // don't bother re-initializing
		}
		
		try {

			Class clazz = Class.forName("com.expanz.R$string");

			Object instance = clazz.newInstance();
			
			for(Field field : clazz.getFields()) {				
				configProperties.put(field.getName(), application.getString(field.getInt(instance)));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public String getExpanzSite() {
		return configProperties.get("expanz_site");
	}

	/**
	 * {@inheritDoc}
	 */
	public String getExpanzNamespace() {
		return configProperties.get("expanz_namespace");
	}

	/**
	 * {@inheritDoc}
	 */
	public String getExpanzSessionUrl() {
		return configProperties.get("expanz_default_session_url");
	}

	/**
	 * {@inheritDoc}
	 */
	public String getExpanzExecUrl() {
		return configProperties.get("expanz_default_exec_url");
	}

	/**
	 * {@inheritDoc}
	 */
	public String getExpanzAuthDomain() {
		return configProperties.get("expanz_auth_domain");
	}

	/**
	 * {@inheritDoc}
	 */
	public String getExpanzAuthMode() {
		return configProperties.get("expanz_auth_mode");
	}

	/**
	 * {@inheritDoc}
	 */
	public String getExpanzClientType() {
		return configProperties.get("expanz_client_type");
	}

	/**
	 * {@inheritDoc}
	 */
	public String getExpanzClientVersion() {
		return configProperties.get("expanz_client_version");
	}

	/**
	 * {@inheritDoc}
	 */
	public String getExpanzSchemaVersion() {
		return configProperties.get("expanz_schema_version");
	}

	/**
	 * {@inheritDoc}
	 */
	public String getExpanzTimezone() {
		return configProperties.get("expanz_timezone");
	}

	/**
	 * {@inheritDoc}
	 */
	public String getExpanzStation() {
		return configProperties.get("expanz_station");
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String getAppSpecificProperty(String key) {
		return configProperties.get(key);
	}

}
