package com.expanz.webservice;

import static org.xmlpull.v1.XmlPullParser.END_DOCUMENT;
import static org.xmlpull.v1.XmlPullParser.END_TAG;
import static org.xmlpull.v1.XmlPullParser.START_TAG;
import static org.xmlpull.v1.XmlPullParser.TEXT;

import java.io.ByteArrayInputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.expanz.model.response.SessionResponse;
import com.expanz.util.XmlUtils;

public class CreateSessionHandler implements XmlHandler<SessionResponse> {
	
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

		SessionResponse response = new SessionResponse();

		int type;
		
		try {

			while ((type = parser.next()) != END_DOCUMENT) {

				if (type == START_TAG
						&& Tags.CREATE_SESSION.equals(parser.getName())) {

					final int depth = parser.getDepth();

					while (((type = parser.next()) != END_TAG || parser
							.getDepth() > depth) && type != END_DOCUMENT) {
						if (type == TEXT) {
							final String handle = parser.getText();

							if (handle != null && handle.length() > 0) {
								response.setSessionHandle(handle);
							}
						}
					}
					
					messageHandler.parse(type, parser, response);

				}
				
			}

		} catch (Exception e) {
			response.error("", "", e.getMessage());
		}

		return response;
	}

	/**
	 * Creates a new instance of the ResponseObject object associated
	 * with this parser
	 */
	public SessionResponse getTypedInstance() {
		return new SessionResponse();
	}

}
