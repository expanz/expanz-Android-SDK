package com.expanz.app;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;

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
public class Config {
	
	/**
	 * Singletone instance
	 */
	private static Config instance;
	
	/**
	 * Has this config been initialized
	 */
	private boolean initialized;
	
	/**
	 * Holds each of the config properties
	 */
	private Map<String, String> configProperties = new HashMap<String, String>();
	
	/**
	 * Singleton instance
	 * 
	 * @return
	 */
	public static Config getInstance() {
		
		if(instance == null) {
			instance = new Config();
		}
		
		return instance;
	}
	
	/**
	 * Initialize the configuration items
	 * 
	 * @param context
	 */
	public void init(Context context) {
		
		if(!initialized) {
			initialized = true;
		} else {
			return; // don't bother re-initializing
		}
		
		if(instance == null) {
			instance = new Config();
		}
		
		try {

			Class clazz = Class.forName("com.expanz.R$string");

			Object instance = clazz.newInstance();
			
			for(Field field : clazz.getFields()) {				
				configProperties.put(field.getName(), context.getString(field.getInt(instance)));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * The Expanz site, apps are currently only able to support a single site. 
	 * @return
	 */
	public String getExpanzSite() {
		return configProperties.get("expanz_site");
	}

	/**
	 * E.g. Android
	 * @return
	 */
	public String getExpanzNamespace() {
		return configProperties.get("expanz_namespace");
	}

	/**
	 * The location for the create session url
	 * @return
	 */
	public String getExpanzSessionUrl() {
		return configProperties.get("expanz_default_session_url");
	}

	/**
	 * The location for the create session url
	 * @return
	 */
	public String getExpanzExecUrl() {
		return configProperties.get("expanz_default_exec_url");
	}

	/**
	 * The auth mode for this app
	 * @return
	 */
	public String getExpanzAuthDomain() {
		return configProperties.get("expanz_auth_domain");
	}

	/**
	 * The auth mode for this app
	 * @return
	 */
	public String getExpanzAuthMode() {
		return configProperties.get("expanz_auth_mode");
	}

	/**
	 * The expanz Client type
	 * @return
	 */
	public String getExpanzClientType() {
		return configProperties.get("expanz_client_type");
	}

	/**
	 * The expanz client version, e.g. 1.0
	 * @return
	 */
	public String getExpanzClientVersion() {
		return configProperties.get("expanz_client_version");
	}

	/**
	 * The schema version, e.g. 1.0
	 * @return
	 */
	public String getExpanzSchemaVersion() {
		return configProperties.get("expanz_schema_version");
	}

	/**
	 * The timezone, e.g. EST
	 * @return
	 */
	public String getExpanzTimezone() {
		return configProperties.get("expanz_timezone");
	}

	/**
	 * The expanz station
	 * @return
	 */
	public String getExpanzStation() {
		return configProperties.get("expanz_station");
	}
	
	/**
	 * Allows a mechanism for applications to specify their own properties
	 * 
	 * @param key the property name
	 * @return the property value
	 */
	public String getAppSpecificProperty(String key) {
		return configProperties.get(key);
	}

}
