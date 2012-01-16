package com.expanz.webservice;

import static org.xmlpull.v1.XmlPullParser.END_DOCUMENT;
import static org.xmlpull.v1.XmlPullParser.END_TAG;
import static org.xmlpull.v1.XmlPullParser.START_TAG;
import static org.xmlpull.v1.XmlPullParser.TEXT;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.expanz.model.response.MenuResponse;
import com.expanz.model.response.ProcessAreaActivityResponse;
import com.expanz.model.response.ProcessAreaResponse;
import com.expanz.model.response.RoleResponse;
import com.expanz.model.response.SessionResponse;
import com.expanz.util.XmlUtils;

public class GetSessionDataHandler implements XmlHandler<SessionResponse> {
	
	/**
	 * Handles the display of error/info/system messages etc.
	 */
	private MessageHandler messageHandler = new MessageHandler();
	
	/**
	 * Parse a raw xml string
	 */
	public SessionResponse parse(String xml) {
		
		XmlPullParser parser = null;
		SessionResponse response = new SessionResponse();

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
	public SessionResponse parse(XmlPullParser parser) {
		
		SessionResponse session = new SessionResponse();
		
		int type;
		
		try {

			while ((type = parser.next()) != END_DOCUMENT) {

				if (type == START_TAG
						&& Tags.MENU.equals(parser.getName())) {
					
					final MenuResponse menu = new MenuResponse();
					session.setMenu(menu);

					final int menuDepth = parser.getDepth();
					
					while (((type = parser.next()) != END_TAG || parser
							.getDepth() > menuDepth) && type != END_DOCUMENT) {
						
						
						if (type == START_TAG
								&& Tags.PROCESS_AREA.equals(parser.getName())) {
							
							type = parseProcessArea(parser, menu);
							
						}
						
						if (type == START_TAG
								&& Tags.ROLES.equals(parser.getName())) {
							
							type = parseRoles(parser, menu);
						}
						
						messageHandler.parse(type, parser, session);
						
					}
					
				}

			}

		} catch (Exception e) {
			session.error("", "", e.getMessage());
		}
		
		return session;
	}

	/**
	 * Parse the role elements
	 * 
	 * @param parser
	 * @param menu
	 * @return
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private int parseRoles(XmlPullParser parser, final MenuResponse menu)
			throws XmlPullParserException, IOException {
		int type;
		final int rolesDepth = parser.getDepth();
		
		while (((type = parser.next()) != END_TAG || parser
				.getDepth() > rolesDepth) && type != END_DOCUMENT) {
			if (type == START_TAG
					&& Tags.ROLE.equals(parser.getName())) {
				
				final RoleResponse role = new RoleResponse();
				role.setId(parser.getAttributeValue("", "id"));
				
				if (type == TEXT) {
					role.setValue(parser.getText());
				}
				
				menu.getRoles().add(role);
			}
		}
		return type;
	}

	/**
	 * Parse the process area elements
	 * 
	 * @param parser
	 * @param menu
	 * @return
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private int parseProcessArea(XmlPullParser parser, final MenuResponse menu)
			throws XmlPullParserException, IOException {
		int type;
		final ProcessAreaResponse processArea = new ProcessAreaResponse();
		
		processArea.setId(parser.getAttributeValue(null, "id")); 
		processArea.setTitle(parser.getAttributeValue(null, "title")); 
		
		menu.addProcessArea(processArea);
		
		final int processAreaDepth = parser.getDepth();
		
		while (((type = parser.next()) != END_TAG || parser
				.getDepth() > processAreaDepth) && type != END_DOCUMENT) {
			if (type == START_TAG
					&& Tags.ACTIVITY.equals(parser.getName())) {
				
				final ProcessAreaActivityResponse processAreaActivity = 
						new ProcessAreaActivityResponse(parser.getAttributeValue("", "name"), 
								parser.getAttributeValue("", "title"), 
								parser.getAttributeValue("", "style"));
				
				processArea.getActivities().add(processAreaActivity);
				
			}
		}
		return type;
	}

	/**
	 * Creates a new instance of the ResponseObject object associated
	 * with this parser
	 */
	public SessionResponse getTypedInstance() {
		return new SessionResponse();
	}

}
