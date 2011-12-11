package com.expanz.model;


/**
 * Models the message properties that are defined in the expanz schema
 *
 */
public class Message {

	/**
	 * The type of the message as defined in {@link ExpanzConstants}
	 */
	private int messageType;
	
	/**
	 * The message String
	 */
	private String message;
	
	/**
	 * The key for message
	 */
	private String key;
	
	/**
	 * The source field
	 */
	private String source;
	
	/**
	 * Ctor.
	 */
	public Message() { }

	/**
	 * Ctor. 
	 * 
	 * @param messageType the message type
	 * @param message the message text
	 * @param key the key
	 * @param source the source field
	 */
	public Message(int messageType, String message, String key, String source) {
		super();
		this.messageType = messageType;
		this.message = message;
		this.key = key;
		this.source = source;
	}

	/**
	 * Return the message type
	 * 
	 * @return the message type
	 */
	public int getMessageType() {
		return messageType;
	}

	/**
	 * Set the message type
	 * 
	 * @param messageType the type of the message as defined in {@link ExpanzConstants}
	 */
	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

	/**
	 * Returns the message text
	 * 
	 * @return the message text
	 * 
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message text
	 * 
	 * @param message the message text
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Return the message key
	 * 
	 * @return the message key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Set the message key
	 * 
	 * @param key the key
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Returns the message Source
	 * 
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * Set the message source
	 * 
	 * @param source the source
	 */
	public void setSource(String source) {
		this.source = source;
	}
	
}
