package com.expanz.util;

import static org.xmlpull.v1.XmlPullParser.END_DOCUMENT;
import static org.xmlpull.v1.XmlPullParser.END_TAG;
import static org.xmlpull.v1.XmlPullParser.START_TAG;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.res.XmlResourceParser;

import com.expanz.ExpanzApplication;
import com.expanz.app.ExpanzConstants;
import com.expanz.model.request.DataPublicationRequest;

/**
 * Holds the mapping information defined in the file
 * /res/xml/activity_mappings.xml
 */
public class ActivityMappingHolder {

	/**
	 * The ActivityMappings defined in
	 */
	private static Map<String, ActivityMapping> activities = new HashMap<String, ActivityMapping>();
	private static Map<String, ActivityMapping> transitions = new HashMap<String, ActivityMapping>();
	private static Map<String, ActivityMapping> all = new HashMap<String, ActivityMapping>();

	private static ActivityMappingHolder instance;

	private static ActivityMapping defaultMapping;
	
	private static ActivityMapping entryPointMapping;

	private ActivityMappingHolder() {
	}

	public Map<String, ActivityMapping> getActivities() {
		return activities;
	}

	public ActivityMapping getDefault() {
		return defaultMapping;
	}
	
	public boolean isEntryPoint(Class<? extends Activity> clazz) {
		
		if(entryPointMapping != null && entryPointMapping.getForm().equals(clazz)) {
			return true;
		}
		
		return false;
	}

	/**
	 * Singleton getInstance
	 * 
	 * @param context
	 * @return
	 */
	public static ActivityMappingHolder getInstance() {

		if (instance == null) {
			instance = new ActivityMappingHolder();
			load();
		}

		return instance;

	}

	public ActivityMapping get(String name, String style) {

		if (style == null) {
			style = "";
		}

		return activities.get(name + ":::" + style);
	}

	public ActivityMapping getTransition(String name, String transitionStyle) {

		if (transitionStyle == null) {
			transitionStyle = "";
		}

		return transitions.get(name + ":::" + transitionStyle);
	}
	
	public ActivityMapping getByForm(String name) {
		return all.get(name);
	}

	private static void load() {
		
		XmlResourceParser parser = null;
		
		try {
			
			Class mappingClass = Class.forName("com.expanz.R$xml");
			
			Field mappingField = mappingClass.getField("activity_mappings");
			
			parser = ExpanzApplication.getInstance().getResources().getXml(mappingField.getInt(mappingClass.newInstance()));
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return;
		} 

		try {

			int type;

			while ((type = parser.next()) != END_DOCUMENT) {

				if (type == START_TAG && "activity".equals(parser.getName())) {

					final int activityDepth = parser.getDepth();

					String activity = parser.getAttributeValue(null, "name");
					String form = parser.getAttributeValue(null, "form");
					String style = parser.getAttributeValue(null, "style");
					String transition = parser.getAttributeValue(null, "transitionStyle");
					boolean createSession = parser.getAttributeBooleanValue(null, "createSession", false);
					boolean createActivity = parser.getAttributeBooleanValue(null, "createActivity", false);
					boolean isList = parser.getAttributeBooleanValue(null, "isList", false);
					
					ActivityMapping am = new ActivityMapping();
					
					am.setAndroidActivity((Class<? extends Activity>) Class.forName(form));
					am.setCreateSession(createSession);
					am.setCreateActivity(createActivity);
					am.setExpanzActivityName(activity);
					am.setList(isList);

					if (transition != null) { // parse transitions

						am.setStyle(transition);

						transitions.put(activity + ":::" + transition, am);

					} else { // parse activities

						if (style == null) {
							am.setStyle("");
						} else {
							am.setStyle(style);
						}

						boolean isDefault = parser.getAttributeBooleanValue(
								null, "default", false);

						if (isDefault) {
							defaultMapping = am;
						}
						
						boolean entryPoint = parser.getAttributeBooleanValue(
								null, "entryPoint", false);
						
						if (entryPoint) {
							entryPointMapping = am;
							am.setEntryPoint(true);
						}

						if(activity != null && activity.trim().length() > 0) {
							activities.put(activity + ":::" + style, am);
						}

					}
					
					while (((type = parser.next()) != END_TAG || parser
							.getDepth() > activityDepth)
							&& type != END_DOCUMENT) {
						if (type == START_TAG
								&& "layout".equals(parser.getName())) {
							
							String contentView = parser.getAttributeValue(null, "contentView");
							
							if(contentView != null) {
								
								try {
									
									Class clazz = Class.forName("com.expanz.R$layout");
									Field field = clazz .getField(contentView);
									
									am.setContentView(field.getInt(clazz.newInstance()));
									
								} catch (Exception e) {
									//ignore for now maybe give user a more informed message
								}
								
							}
							
							String rootLayout = parser.getAttributeValue(null, "rootLayout");
							
							if(rootLayout != null) {
								
								try {
									
									Class clazz = Class.forName("com.expanz.R$id");
									Field field = clazz .getField(rootLayout);
									
									am.setRootLayout(field.getInt(clazz.newInstance()));
									
								} catch (Exception e) {
									//ignore for now maybe give user a more informed message
								}
								
							}
							
						}
						
						if (type == START_TAG
								&& "messageHandler".equals(parser.getName())) {
							
							String handlerType = parser.getAttributeValue(null, "type");
							
							if(handlerType.equals("toast")) {
								am.setMessageHandlerType(ExpanzConstants.TYPE_TOAST);
							} else if(handlerType.equals("alert")) {
								am.setMessageHandlerType(ExpanzConstants.TYPE_ALERT);
							} else if(handlerType.equals("notification")) {
								am.setMessageHandlerType(ExpanzConstants.TYPE_NOTIFICATION);
							}
							
						}
						
						if (type == START_TAG
								&& "menu".equals(parser.getName())) {
							am.addMenuItem(parser.getAttributeValue(null, "name"));
						}
						
						if (type == START_TAG
								&& "listHeaderView".equals(parser.getName())) {
							
							String header = parser.getAttributeValue(null, "name");
							
							if(header != null) {
								
								try {

									Class clazz = Class.forName("com.expanz.R$layout");
									
									Field field = clazz .getField(header);
									
									am.addListHeader(field.getInt(clazz.newInstance()));
									
								} catch (Exception e) {
									//ignore for now maybe give user a more informed message
								}
								
							}
			
						}
						
						if (type == START_TAG
								&& "listFooterView".equals(parser.getName())) {
							
							String footer = parser.getAttributeValue(null, "name");
							
							if(footer != null) {
								
								try {

									Class clazz = Class.forName("com.expanz.R$layout");
									
									Field field = clazz .getField(footer);
									
									am.addListFooter(field.getInt(clazz.newInstance()));
									
								} catch (Exception e) {
									//ignore for now maybe give user a more informed message
								}
								
							}
			
						}
						
						if (type == START_TAG
								&& "listView".equals(parser.getName())) {
							
							String listView = parser.getAttributeValue(null, "name");
							
							if(listView != null) {
								
								try {
									
									Class clazz = Class.forName("com.expanz.R$layout");
									
									Field field = clazz .getField(listView);
									
									am.setListView(field.getInt(clazz.newInstance()));
									
								} catch (Exception e) {
									//ignore for now maybe give user a more informed message
								}
								
							}
						}
						
						if (type == START_TAG
								&& "dataPublication".equals(parser.getName())) {
							
							String populateMethod = parser.getAttributeValue(null, "populateMethod");
							String id = parser.getAttributeValue(null, "id");
							String query = parser.getAttributeValue(null, "query");
							String context = parser.getAttributeValue(null, "context");
							boolean autoPopulate = parser.getAttributeBooleanValue(null, "autoPopulate", true);
							boolean useThumbnails = parser.getAttributeBooleanValue(null, "useThumbNailImages", false);
							
							DataPublicationRequest publication = new DataPublicationRequest();
							publication.setPopulateMethod(populateMethod);
							publication.setAutoPopulate(autoPopulate);
							publication.setContext(context);
							publication.setId(id);
							publication.setQuery(query);
							publication.setUseThumbnails(useThumbnails);
							
							am.addPublication(publication);
							
						}
					}
					
					all.put(form, am);

				}
			}

		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
