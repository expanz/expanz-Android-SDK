package com.expanz.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.Application;
import android.content.res.XmlResourceParser;

import com.expanz.ExpanzApplication;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

/**
 * Holds the mapping information defined in the file
 * /res/xml/activity_mappings.xml
 */
@Singleton
public class ActivityMappingHolder {

	/**
	 * The ActivityMappings defined in activity_mappings.xml
	 */
	private static Map<String, ActivityMapping> activities = new HashMap<String, ActivityMapping>(0);

	/**
	 * The Transitional ActivityMappings defined in activity_mappings.xml, i.e.
	 * those that don't create a new server side "expanz" activity
	 */
	private static Map<String, ActivityMapping> transitions = new HashMap<String, ActivityMapping>(0);

	/**
	 * Both standard and transitional activities
	 */
	private static Map<String, ActivityMapping> all = new HashMap<String, ActivityMapping>(0);

	/**
	 * The default mapping, i.e. started after successful login
	 */
	private static ActivityMapping defaultMapping;

	/**
	 * The very first activity, e.g. a login
	 */
	private static ActivityMapping entryPointMapping;
	
	/**
	 * Resource class name that holds reference to activity_mappings XML file
	 */
	private static String mappingClassName = "com.expanz.R$xml";
	
	/**
	 * Field name for activity_mappings, mostly used as a convenience for testing
	 */
	private static String mappingFieldName = "activity_mappings";
	
	@Inject Application application;
	

	/**
	 * Setter for the mapping class name, i.e. name of package followed by .R$xml
	 * 
	 * @param mappingClassName
	 */
	public static void setMappingClassName(String mappingClassName) {
		ActivityMappingHolder.mappingClassName = mappingClassName;
	}

	/**
	 * Setter for field, i.e. name of activity mappings file
	 * 
	 * @param mappingFieldName
	 */
	public static void setMappingFieldName(String mappingFieldName) {
		ActivityMappingHolder.mappingFieldName = mappingFieldName;
	}

	/**
	 * Returns the activities map.
	 * 
	 * Map format is;
	 * 
	 * <Activity Name>:::<Activity Style>
	 * 
	 * @return
	 */
	public Map<String, ActivityMapping> getActivities() {
		return activities;
	}

	/**
	 * Return the default mapping, i.e. started after a successful login
	 * 
	 * @return
	 */
	public ActivityMapping getDefault() {
		return defaultMapping;
	}

	/**
	 * Is the given class an entry point
	 * 
	 * @param clazz the class to verify
	 * 
	 * @return true if entry
	 */
	public boolean isEntryPoint(Class<? extends Activity> clazz) {

		if (entryPointMapping != null
				&& entryPointMapping.getForm().equals(clazz)) {
			return true;
		}

		return false;
	}


	/**
	 * Get an activity by name and style
	 * 
	 * @param name the name of the activity
	 * @param style the style of the activity
	 * 
	 * @return the mapping, null if not found
	 */
	public ActivityMapping get(String name, String style) {

		if (style == null) {
			style = "";
		}

		return activities.get(name + ":::" + style);
	}

	/**
	 * Get a transitional activity by name and style
	 * 
	 * @param name the name of the activity
	 * @param transitionStyle the name of the style
	 * 
	 * @return the mapping, null if not found
	 */
	public ActivityMapping getTransition(String name, String transitionStyle) {

		if (transitionStyle == null) {
			transitionStyle = "";
		}

		return transitions.get(name + ":::" + transitionStyle);
	}

	/**
	 * Get an activity by it's class/form.
	 * 
	 * Note: form is used in other expanz environments
	 * to denote view/screen etc. This is a legacy term
	 * from the use of windows forms. It should probably 
	 * be revised. 
	 * 
	 * @param name class name
	 * @return the mapping for the class
	 */
	public ActivityMapping getByForm(String name) {
		return all.get(name);
	}

	/**
	 * Load the mappings file resource from disk to parse
	 * 
	 * Made public for test purposes. 
	 * 
	 */
	public void load() {

		XmlResourceParser resourceParser = null;

		try {

			Class mappingClass = Class.forName(mappingClassName);

			Field mappingField = mappingClass.getField(mappingFieldName);

			resourceParser = ((ExpanzApplication)application).getResources()
					.getXml(mappingField.getInt(mappingClass.newInstance()));

		} catch (Exception e) {
			e.printStackTrace();
			//TODO make this a specific runtime, catching Exception not so bad here 
			//due to all errors being fatal
			throw new RuntimeException("error parsing activity_mappings " + e.getMessage());
		}
		
		ActivityMappingParser mappingParser = new ActivityMappingParserImpl();
		
		MappingParseResult result = mappingParser.parse(resourceParser);
		
		if(result != null) {
			activities = result.getActivities();
			transitions = result.getTransitions();
			all = result.getAll();
			defaultMapping = result.getDefaultMapping();
			entryPointMapping = result.getEntryPointMapping();
		}

	}

}
