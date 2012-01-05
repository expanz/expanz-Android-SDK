package com.expanz.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Simple POJO class that holds the result of parsing the activity_mapping.xml 
 * configuration file. 
 *
 */
public class MappingParseResult {
	
	/**
	 * The ActivityMappings defined in activity_mappings.xml
	 */
	private Map<String, ActivityMapping> activities = new HashMap<String, ActivityMapping>();

	/**
	 * The Transitional ActivityMappings defined in activity_mappings.xml, i.e.
	 * those that don't create a new server side "expanz" activity
	 */
	private Map<String, ActivityMapping> transitions = new HashMap<String, ActivityMapping>();

	/**
	 * Both standard and transitional activities
	 */
	private Map<String, ActivityMapping> all = new HashMap<String, ActivityMapping>();
	
	/**
	 * The default mapping, i.e. started after successful login
	 */
	private ActivityMapping defaultMapping;

	/**
	 * The very first activity, e.g. a login
	 */
	private ActivityMapping entryPointMapping;
	
	
	/**
	 * Get the activities contained in the mapping file
	 * 
	 * @return map of activities, activity name:::style is key
	 */
	public Map<String, ActivityMapping> getActivities() {
		return activities;
	}
	
	/**
	 * Get the transitional activities contained in the mapping file
	 * 
	 * @return map of activities, activity name:::style is key
	 */
	public Map<String, ActivityMapping> getTransitions() {
		return transitions;
	}
	
	/**
	 * Get all the activities/transitions contained in the mapping file
	 * 
	 * @return map of activities, activity name:::style is key
	 */
	public Map<String, ActivityMapping> getAll() {
		return all;
	}
	
	/**
	 * Return the default mapping, i.e. started after login
	 * 
	 * @return the default mapping, null if none
	 */
	public ActivityMapping getDefaultMapping() {
		return defaultMapping;
	}
	
	/**
	 * Set the default mapping
	 * 
	 * @param defaultMapping
	 */
	public void setDefaultMapping(ActivityMapping defaultMapping) {
		this.defaultMapping = defaultMapping;
	}
	
	/**
	 * Get the entry point, i.e. first activity
	 * @return
	 */
	public ActivityMapping getEntryPointMapping() {
		return entryPointMapping;
	}
	
	/**
	 * Set the entry point
	 * 
	 * @param entryPointMapping
	 */
	public void setEntryPointMapping(ActivityMapping entryPointMapping) {
		this.entryPointMapping = entryPointMapping;
	}
	
}
