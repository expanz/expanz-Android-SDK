package com.expanz.model.request;

/**
 * CommandInput object for the com.expanz.ExpanzCommand. 
 * Used for the the Context element of an Activity service call
 *
 */
public class ContextRequest {
	
	/**
	 * Id of context object
	 */
	private String id;
	
	/**
	 * Type of the context object
	 */
	private String type;
	
	/**
	 * Name of the context object
	 */
	private String context;

	/**
	 * Returns the id of the context object
	 * @return the id 
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id of the context object
	 * @param id the id of the object
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the type of the context object
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Set the type of the context object
	 * 
	 * @param type the type of the context object
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Return the name of the context object
	 * @return
	 */
	public String getContext() {
		return context;
	}

	/**
	 * Set the name of the context object
	 * @param context set the name
	 */
	public void setContext(String context) {
		this.context = context;
	}

}
