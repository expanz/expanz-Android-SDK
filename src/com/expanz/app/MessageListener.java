package com.expanz.app;

import java.util.Collection;

import com.expanz.model.Message;

/**
 * Allows Widgets to essentially callback to their context (Activity)  
 * and display messages.
 * 
 * Messages will generally be displayed using the specified message
 * type for the activity, i.e. toast, notification or alert. 
 * 
 */
public interface MessageListener {
	
	/**
	 * Display a single message
	 * @param message the message to be displayed.
	 */
	void displayMessage(Message message);
	
	/**
	 * Display multiple messages.
	 * 
	 * TODO the semantics for this need to be further investigated, 
	 * i.e. what if a get 10 alerts in a row. 
	 * 
	 * @param messages
	 */
	void displayMessages(Collection<Message> messages);

}
