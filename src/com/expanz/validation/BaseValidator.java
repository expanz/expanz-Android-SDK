package com.expanz.validation;

import java.util.ArrayList;
import java.util.List;

import com.expanz.app.ExpanzConstants;
import com.expanz.model.Message;

/**
 * Abstract Base Class for client side validation.
 * 
 */
public abstract class BaseValidator implements Validateable {
	
	/**
	 * The validation error messages
	 */
	private List<Message> messages = new ArrayList<Message>();
	
	/**
	 * The field that is being validated
	 */
	protected String field;
	
	/**
	 * Returns the validation error messages
	 */
	public List<Message> getMessages() {
		return messages;
	}

	/**
	 * Add message to the validation errors
	 * 
	 * @param text the message text
	 */
	protected void addMessage(String text) {
		
		Message message = new Message();
		message.setMessage(text);
		message.setSource("field");
		message.setMessageType(ExpanzConstants.ERROR);
		messages.add(message);
		
	}
}
