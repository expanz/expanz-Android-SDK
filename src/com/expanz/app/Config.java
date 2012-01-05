package com.expanz.app;

public interface Config {

	/**
	 * Initialize the configuration items
	 * 
	 */
	public abstract void init();

	/**
	 * The Expanz site, apps are currently only able to support a single site. 
	 * @return
	 */
	public abstract String getExpanzSite();

	/**
	 * E.g. Android
	 * @return
	 */
	public abstract String getExpanzNamespace();

	/**
	 * The location for the create session url
	 * @return
	 */
	public abstract String getExpanzSessionUrl();

	/**
	 * The location for the create session url
	 * @return
	 */
	public abstract String getExpanzExecUrl();

	/**
	 * The auth mode for this app
	 * @return
	 */
	public abstract String getExpanzAuthDomain();

	/**
	 * The auth mode for this app
	 * @return
	 */
	public abstract String getExpanzAuthMode();

	/**
	 * The expanz Client type
	 * @return
	 */
	public abstract String getExpanzClientType();

	/**
	 * The expanz client version, e.g. 1.0
	 * @return
	 */
	public abstract String getExpanzClientVersion();

	/**
	 * The schema version, e.g. 1.0
	 * @return
	 */
	public abstract String getExpanzSchemaVersion();

	/**
	 * The timezone, e.g. EST
	 * @return
	 */
	public abstract String getExpanzTimezone();

	/**
	 * The expanz station
	 * @return
	 */
	public abstract String getExpanzStation();

	/**
	 * Allows a mechanism for applications to specify their own properties
	 * 
	 * @param key the property name
	 * @return the property value
	 */
	public abstract String getAppSpecificProperty(String key);

}