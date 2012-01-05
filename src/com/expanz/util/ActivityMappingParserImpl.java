package com.expanz.util;

import static org.xmlpull.v1.XmlPullParser.END_DOCUMENT;
import static org.xmlpull.v1.XmlPullParser.END_TAG;
import static org.xmlpull.v1.XmlPullParser.START_TAG;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.res.XmlResourceParser;

import com.expanz.app.ExpanzConstants;
import com.expanz.model.request.DataPublicationRequest;

/**
 * This class is the XML parser for the activity_mappings.xml file located under
 * /res/xml.
 * 
 * It is a basic StAX based parser that converts the XML elements into a
 * corresponding object model
 * 
 */
public class ActivityMappingParserImpl implements ActivityMappingParser {

	public MappingParseResult parse(XmlResourceParser parser) {

		MappingParseResult result = new MappingParseResult();

		try {

			int type;

			while ((type = parser.next()) != END_DOCUMENT) {

				if (type == START_TAG && "activity".equals(parser.getName())) {

					final int activityDepth = parser.getDepth();

					String transition = parser.getAttributeValue(null,
							"transitionStyle");
					String activity = parser.getAttributeValue(null, "name");
					String form = parser.getAttributeValue(null, "form");
					boolean createSession = parser.getAttributeBooleanValue(
							null, "createSession", false);
					boolean createActivity = parser.getAttributeBooleanValue(
							null, "createActivity", false);
					boolean isList = parser.getAttributeBooleanValue(null,
							"isList", false);

					ActivityMapping am = new ActivityMapping();

					am.setAndroidActivity((Class<? extends Activity>) Class.forName(form));
					am.setCreateSession(createSession);
					am.setCreateActivity(createActivity);
					am.setExpanzActivityName(activity);
					am.setList(isList);

					if (transition != null) {
						parseTransition(am, result, activity, transition);
					} else {
						parserActivity(parser, am, result);
					}

					while (((type = parser.next()) != END_TAG || parser
							.getDepth() > activityDepth)
							&& type != END_DOCUMENT) {
						if (type == START_TAG
								&& "layout".equals(parser.getName())) {

							parseLayout(parser, am);

						}

						if (type == START_TAG
								&& "messageHandler".equals(parser.getName())) {

							parseMessageHandler(parser, am);

						}

						if (type == START_TAG
								&& "menu".equals(parser.getName())) {

							am.addMenuItem(parser.getAttributeValue(null,
									"name"));

						}

						if (type == START_TAG
								&& "listHeaderView".equals(parser.getName())) {

							parseListHeader(parser, am);

						}

						if (type == START_TAG
								&& "listFooterView".equals(parser.getName())) {

							parseListFooter(parser, am);

						}

						if (type == START_TAG
								&& "listView".equals(parser.getName())) {

							parseListView(parser, am);
						}

						if (type == START_TAG
								&& "dataPublication".equals(parser.getName())) {

							parserDataPublications(parser, am);

						}
					}

					result.getAll().put(form, am);

				}
			}

		} catch (Exception e) {
			//bad form to catch Exception but in this case any error here is non-recoverable
			e.printStackTrace();
			throw new RuntimeException("error parsing activity_mappings.xml " + e.getMessage());
		}

		return result;
	}

	/**
	 * Parse the transitional activities, i.e. Android activities that do not 
	 * create a new server side "expanz" activity but rather re-use the current
	 * "expanz" activity
	 * 
	 * @param am the mapping details
	 * @param result the result of the parsing
	 * @param activity the activity name
	 * @param transition the transition style
	 */
	private void parseTransition(ActivityMapping am, MappingParseResult result,
			String activity, String transition) {
		am.setStyle(transition);

		result.getTransitions().put(activity + ":::" + transition, am);
	}

	/**
	 * Parse the activity specific details. 
	 * 
	 * @param parser the current parser
	 * @param am the mapping details
	 * @param result stores the result of the parsing
	 */
	private void parserActivity(XmlResourceParser parser, ActivityMapping am,
			MappingParseResult result) {

		String style = parser.getAttributeValue(null, "style");

		if (style == null) {
			am.setStyle("");
		} else {
			am.setStyle(style);
		}

		boolean isDefault = parser.getAttributeBooleanValue(null, "default",
				false);

		if (isDefault) {
			result.setDefaultMapping(am);
		}

		boolean entryPoint = parser.getAttributeBooleanValue(null,
				"entryPoint", false);

		if (entryPoint) {
			result.setEntryPointMapping(am);
			am.setEntryPoint(true);
		}

		if (am.getExpanzActivityName() != null && am.getExpanzActivityName().trim().length() > 0) {
			result.getActivities().put(am.getExpanzActivityName() + ":::" + style, am);
		}

	}

	/**
	 * Parse the layout details, i.e. contentView and rootLayout
	 * 
	 * @param parser the current parser
	 * @param am the mapping details
	 */
	private void parseLayout(XmlResourceParser parser, ActivityMapping am) {

		String contentView = parser.getAttributeValue(null, "contentView");

		if (contentView != null) {

			try {

				Class clazz = Class.forName("com.expanz.R$layout");
				Field field = clazz.getField(contentView);

				am.setContentView(field.getInt(clazz.newInstance()));

			} catch (Exception e) {
				// ignore for now maybe give user a more informed message
			}

		}

		String rootLayout = parser.getAttributeValue(null, "rootLayout");

		if (rootLayout != null) {

			try {

				Class clazz = Class.forName("com.expanz.R$id");
				Field field = clazz.getField(rootLayout);

				am.setRootLayout(field.getInt(clazz.newInstance()));

			} catch (Exception e) {
				//TODO it would be good to validate activity_mappings.xml 
				//at compile time, e.g. tie this parser into the build cycle
			}

		}

	}

	/**
	 * Parse the messageHandling details. There are currently
	 * Three options for message handling.
	 * 
	 * 1. Toast
	 * 2. Alert
	 * 3. Notification
	 * 
	 * @param parser the current parser
	 * @param am the mapping details
	 */
	private void parseMessageHandler(XmlResourceParser parser,
			ActivityMapping am) {

		String handlerType = parser.getAttributeValue(null, "type");

		if (handlerType.equals("toast")) {
			am.setMessageHandlerType(ExpanzConstants.TYPE_TOAST);
		} else if (handlerType.equals("alert")) {
			am.setMessageHandlerType(ExpanzConstants.TYPE_ALERT);
		} else if (handlerType.equals("notification")) {
			am.setMessageHandlerType(ExpanzConstants.TYPE_NOTIFICATION);
		}

	}

	/**
	 * Parse the header for List based activities
	 * 
	 * @param parser the current parser
	 * @param am the mapping details
	 */
	private void parseListHeader(XmlResourceParser parser, ActivityMapping am) {

		String header = parser.getAttributeValue(null, "name");

		if (header != null) {

			try {

				Class clazz = Class.forName("com.expanz.R$layout");

				Field field = clazz.getField(header);

				am.addListHeader(field.getInt(clazz.newInstance()));

			} catch (Exception e) {
				//TODO it would be good to validate activity_mappings.xml 
				//at compile time, e.g. tie this parser into the build cycle
			}

		}

	}

	/**
	 * Parse the footer for List based activities
	 * 
	 * @param parser the current parser
	 * @param am the mapping details
	 */
	private void parseListFooter(XmlResourceParser parser, ActivityMapping am) {

		String footer = parser.getAttributeValue(null, "name");

		if (footer != null) {

			try {

				Class clazz = Class.forName("com.expanz.R$layout");

				Field field = clazz.getField(footer);

				am.addListFooter(field.getInt(clazz.newInstance()));

			} catch (Exception e) {
				//TODO it would be good to validate activity_mappings.xml 
				//at compile time, e.g. tie this parser into the build cycle
			}

		}

	}

	/**
	 * Parse the details for List based activities
	 * 
	 * @param parser the current parser
	 * @param am the mapping details
	 */
	private void parseListView(XmlResourceParser parser, ActivityMapping am) {

		String listView = parser.getAttributeValue(null, "name");

		if (listView != null) {

			try {

				Class clazz = Class.forName("com.expanz.R$layout");

				Field field = clazz.getField(listView);

				am.setListView(field.getInt(clazz.newInstance()));

			} catch (Exception e) {
				//TODO it would be good to validate activity_mappings.xml 
				//at compile time, e.g. tie this parser into the build cycle
			}

		}
	}

	/**
	 * Parse the data publication for List based activities/views
	 * 
	 * @param parser the current parser
	 * @param am the mapping details
	 */
	private void parserDataPublications(XmlResourceParser parser,
			ActivityMapping am) {

		String populateMethod = parser
				.getAttributeValue(null, "populateMethod");
		String id = parser.getAttributeValue(null, "id");
		String query = parser.getAttributeValue(null, "query");
		String context = parser.getAttributeValue(null, "context");
		boolean autoPopulate = parser.getAttributeBooleanValue(null,
				"autoPopulate", true);
		boolean useThumbnails = parser.getAttributeBooleanValue(null,
				"useThumbNailImages", false);

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
