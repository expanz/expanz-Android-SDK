package com.expanz.webservice;

import static org.xmlpull.v1.XmlPullParser.END_DOCUMENT;
import static org.xmlpull.v1.XmlPullParser.END_TAG;
import static org.xmlpull.v1.XmlPullParser.START_TAG;
import static org.xmlpull.v1.XmlPullParser.TEXT;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.expanz.model.response.ActivityRequestResponse;
import com.expanz.model.response.ActivityResponse;
import com.expanz.model.response.ContextMenuResponse;
import com.expanz.model.response.Data;
import com.expanz.model.response.DataCell;
import com.expanz.model.response.DataColumn;
import com.expanz.model.response.DataRow;
import com.expanz.model.response.FieldResponse;
import com.expanz.model.response.MenuItemResponse;
import com.expanz.util.XmlUtils;

/**
 * StAX based parser for parsing activity response XML
 *
 */
public class ActivityHandler implements XmlHandler<ActivityResponse> {

	/**
	 * Handles the display of error/info/system messages etc.
	 */
	private MessageHandler messageHandler = new MessageHandler();
	
	/**
	 * Parse a raw xml string
	 */
	public ActivityResponse parse(String xml) {
		
		XmlPullParser parser = null;
		
		ActivityResponse response = new ActivityResponse();

		try {
			parser = XmlUtils.newPullParser(new ByteArrayInputStream(xml
					.getBytes()));
			response = parse(parser);
		} catch (XmlPullParserException e) {
			response.error("", "", e.getMessage());
		}

		return response;
	}
	
	/**
	 * Parse from pre initialized XmlPullParser
	 */
	public ActivityResponse parse(XmlPullParser parser) {

		Map<String, DataColumn> fieldLookup = new HashMap<String, DataColumn>();

		ActivityResponse response = new ActivityResponse();

		int type;

		try {

			while ((type = parser.next()) != END_DOCUMENT) {

				if (type == START_TAG && Tags.ACTIVITY.equals(parser.getName())) {

					final int activityDepth = parser.getDepth();

					response.setHandle(parser.getAttributeValue("", "activityHandle"));
					response.setTitle(parser.getAttributeValue("", "title"));

					while (((type = parser.next()) != END_TAG || parser
							.getDepth() > activityDepth)
							&& type != END_DOCUMENT) {
						if (type == START_TAG
								&& Tags.FIELD.equals(parser.getName())) {

							parseField(parser, response);

						}
						
						if (type == START_TAG
								&& Tags.CONTEXT_MENU.equals(parser.getName())) {
							
							type = parseContextMenu(parser, response);

						}

						if (type == START_TAG
								&& Tags.DATA.equals(parser.getName())) {

							type = parseData(parser, fieldLookup, response);
							
						}

						messageHandler.parse(type, parser, response);

					}

				}
				
				if (type == START_TAG
						&& Tags.ACTIVITY_REQUEST.equals(parser.getName())) {
					
					parseActivityRequest(parser, response);

				}
				
				

			}

		} catch (Exception e) {
			response.error("", "", e.getMessage());
		}

		return response;
	}

	/**
	 * Parse the data element
	 * 
	 * @param parser
	 * @param fieldLookup
	 * @param response
	 * @return
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private int parseData(XmlPullParser parser,
			Map<String, DataColumn> fieldLookup, ActivityResponse response)
			throws XmlPullParserException, IOException {
		
		int type;
		final int dataDepth = parser.getDepth();

		Data data = new Data();
		data.setId(parser.getAttributeValue(null, "id"));
		data.setContextObject(parser.getAttributeValue(null, "contextObject"));
		data.setContextMenuMethod(parser.getAttributeValue(null, "contextMenuMethod"));
		response.addData(data);

		while (((type = parser.next()) != END_TAG || parser
				.getDepth() > dataDepth)
				&& type != END_DOCUMENT) {
			
			if (type == START_TAG
					&& Tags.COLUMN.equals(parser.getName())) {
				DataColumn column = new DataColumn(parser.getAttributeValue(null, "id"),
						parser.getAttributeValue(null, "label"),
						parser.getAttributeValue(null, "field"), 
						parser.getAttributeValue(null, "datatype"));
				
				fieldLookup.put(column.getId(), column);
				
			}
			
			if (type == START_TAG
					&& Tags.ROW.equals(parser.getName())) {
				
				final int rowDepth = parser.getDepth();
				
				DataRow row = new DataRow();
				data.getRows().add(row);
				row.setParent(data);
				
				row.setId(parser.getAttributeValue(null, "id"));
				row.setType(parser.getAttributeValue(null, "Type"));
				
				DataCell cell = null;
				
				while (((type = parser.next()) != END_TAG || parser
						.getDepth() > rowDepth) && type != END_DOCUMENT) {
													
					if (type == START_TAG
							&& Tags.CELL.equals(parser.getName())) {
						
						cell = new DataCell();
						
						cell.setId(parser.getAttributeValue(null, "id"));
						
						DataColumn column = fieldLookup.get(cell.getId());
						
						if(column != null) {
							cell.setFieldId(column.getFieldId());
							cell.setLabel(column.getLabel());
						}
						
					}
					
					if (type == TEXT) {
						if(cell != null) {
							cell.setValue(parser.getText());
							row.addCell(cell);
							cell = null;
						}
						
					}
					
				}
				
			}
			
		}
		return type;
	}

	/**
	 * Parse the Activity Request
	 * 
	 * @param parser
	 * @param response
	 */
	private void parseActivityRequest(XmlPullParser parser,
			ActivityResponse response) {
		
		ActivityRequestResponse activityRequest = new ActivityRequestResponse();
		activityRequest.setId(parser.getAttributeValue(null, "id"));
		activityRequest.setKey(parser.getAttributeValue(null, "key"));
		activityRequest.setStyle(parser.getAttributeValue(null, "style"));
			
		response.setActivityRequest(activityRequest);
	}

	/**
	 * Parse the ContextMenu element
	 * 
	 * @param parser 
	 * @param response
	 * @return
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private int parseContextMenu(XmlPullParser parser, ActivityResponse response)
			throws XmlPullParserException, IOException {
		
		int type;
		ContextMenuResponse contextMenu = new ContextMenuResponse();
		
		int contextMenuDepth = parser.getDepth();
		
		while (((type = parser.next()) != END_TAG || parser
				.getDepth() > contextMenuDepth)
				&& type != END_DOCUMENT) {
			
			if (type == START_TAG
					&& Tags.MENU_ITEM.equals(parser.getName())) {
				
				MenuItemResponse menuItem = new MenuItemResponse(parser.getAttributeValue(null, "action"), 
						parser.getAttributeValue(null, "text"), parser.getAttributeValue(null, "transitionStyle"));
				
				contextMenu.addItem(menuItem);
				
			}
		}
			
		response.setContextMenu(contextMenu);
		return type;
	}

	/**
	 * Parse the Field elements
	 * 
	 * @param parser
	 * @param response
	 */
	private void parseField(XmlPullParser parser, ActivityResponse response) {
		
		final FieldResponse field = new FieldResponse();

		field.setId(parser.getAttributeValue(null, "id"));
		field.setLabel(parser.getAttributeValue(null, "label"));
		field.setType(parser.getAttributeValue(null,"datatype"));
		field.setUrl(parser.getAttributeValue(null,"url"));
		field.setFileExt(parser.getAttributeValue(null,"fileExt"));

		String nullable = parser.getAttributeValue(null,"nullable");

		if (nullable != null && nullable.equals("1")) {
			field.setNullable(true);
		}

		String valid = parser
				.getAttributeValue(null, "valid");

		if (valid != null && valid.equals("1")) {
			field.setValid(true);
		}
		
		//TODO why is this not a boolean attribute, e.g. longData="1"
		if("$longData$".equals(parser.getAttributeValue(null, "value"))) {
			field.setValue(parser.getText());
		} else {
			field.setValue(parser.getAttributeValue(null, "value"));
		}

		response.getFields().add(field);
	}

	/**
	 * Creates a new instance of the ResponseObject object associated
	 * with this parser
	 */
	public ActivityResponse getTypedInstance() {
		return new ActivityResponse();
	}

}
