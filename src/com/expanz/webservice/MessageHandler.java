package com.expanz.webservice;

import static org.xmlpull.v1.XmlPullParser.END_DOCUMENT;
import static org.xmlpull.v1.XmlPullParser.END_TAG;
import static org.xmlpull.v1.XmlPullParser.START_TAG;
import static org.xmlpull.v1.XmlPullParser.TEXT;

import org.xmlpull.v1.XmlPullParser;

import com.expanz.app.ExpanzConstants;
import com.expanz.model.Message;
import com.expanz.model.response.ResponseObject;

/**
 * Class used to create messages of the corresponding type that
 * will eventually be displayed to the user
 */
public class MessageHandler {
	
	private static final String ERROR_STRING = "Error";
	private static final String WARNING_STRING = "Warning";
	private static final String INFO_STRING = "Info";

	/**
	 * Parse out the messages from the response
	 * 
	 * @param type the message type
	 * @param parser the parse 
	 * @param response the response to add messages to
	 */
	public void parse(int type, XmlPullParser parser, ResponseObject response) {
		
		if (type == START_TAG
				&& Tags.MESSAGES.equals(parser.getName())) {
			
			int depth = parser.getDepth();
			
			try {
				while (((type = parser.next()) != END_TAG || parser
						.getDepth() > depth) && type != END_DOCUMENT) {
					
					if (type == START_TAG
							&& Tags.MESSAGE.equals(parser.getName())) {
						
						depth = parser.getDepth();
						
						Message message = new Message();
						message.setMessageType(determineMessageType(parser.getAttributeValue("", "type")));
						message.setKey(parser.getAttributeValue("", "key"));
						message.setSource(parser.getAttributeValue("", "source"));
						
						while (((type = parser.next()) != END_TAG || parser
								.getDepth() > depth) && type != END_DOCUMENT) {

							if (type == TEXT) {
								message.setMessage(parser.getText());
							}
						}
						
						response.addMessage(message);
						
					}
					
				}
			} catch (Exception e) {
				response.error("", "", e.getMessage());
			}
		}
	}
	
	/**
	 * Determine the type of the message based on the value in the XML  
	 * 
	 * @param messageType the value from the XML
	 * 
	 * @return the associated type
	 */
	private int determineMessageType(String messageType) {
		
		if(messageType.equals(ERROR_STRING)) {
			return ExpanzConstants.ERROR;
		}
		if(messageType.equals(INFO_STRING)) {
			return ExpanzConstants.INFO;
		}
		if(messageType.equals(WARNING_STRING)) {
			return ExpanzConstants.WARNING;
		}
		
		throw new RuntimeException("unkown message type " + messageType);
		
	}

}
