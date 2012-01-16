package com.expanz.model.response;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.net.Uri;

import com.expanz.app.ExpanzConstants;
import com.expanz.model.Message;

/**
 * Base CommandOutput for the com.expanz.ExpanzCommand class
 */
public abstract class ResponseObject { 
	
	protected Uri uri;
	
	/**
	 * A simple check to avoid most race conditions
	 * Note: this is a temporary measure and should be improved
	 */
	private long requestTime;
	
	/**
	 * holds any messages returned in the service response
	 */
	private Map<String, Message> messages = new HashMap<String, Message>();
	
	/**
	 * Get the messages contained in the response
	 * @return
	 */
	public Collection<Message> getMessages() {
		return messages.values();
	}
	
	/**
	 * Get a message for a specific source (field)
	 * 
	 * @param source
	 * @return
	 */
	public Message getMessage(String source) {
		return messages.get(source);
	}

	/**
	 * Add a message to the collection
	 * 
	 * @param message
	 */
	public void addMessage(Message message) {
		messages.put(message.getSource(), message);
	}
	
	/**
	 * Returns true if the response contained any messages
	 *  
	 * @return
	 */
	public boolean hasMessage() {
		return messages.size() > 0;
	}
	
	/**
	 * Convenience method to add error messages
	 * 
	 * @param key
	 * @param source
	 * @param message
	 */
	public void error(String key, String source, String message) {
		messages.put(source, new Message(ExpanzConstants.ERROR, message, key, source));
	}
	
	/**
	 * Convenience method to add info messages
	 * 
	 * @param key
	 * @param source
	 * @param message
	 */
	public void info(String key, String source, String message) {
		messages.put(source, new Message(ExpanzConstants.INFO, message, key, source));
	}
	
	/**
	 * Convenience method to add warning messages
	 * 
	 * @param key
	 * @param source
	 * @param message
	 */
	public void warning(String key, String source, String message) {
		messages.put(source, new Message(ExpanzConstants.WARNING, message, key, source));
	}
	
	/**
	 * Persists the raw XML data for the specific class
	 * 
	 * @param payload the raw data to persist
	 */
	public abstract void persist(String payload, Context context);

	/**
	 * The URI of the persisted data
	 * @return
	 */
	public Uri getUri() {
		return uri;
	}

	/**
	 * The time of the request for the response
	 * 
	 * @return the time of the request
	 */
	public long getRequestTime() {
		return requestTime;
	}

	/**
	 * Return the time the corresponding request was made
	 * 
	 * @param requestTime
	 */
	public void setRequestTime(long requestTime) {
		this.requestTime = requestTime;
	}

}
