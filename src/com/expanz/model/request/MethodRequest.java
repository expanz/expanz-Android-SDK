package com.expanz.model.request;

/**
 * Encapsulates data to be used for a remote method request
 */
public class MethodRequest {
	
	/**
	 * Name of the remote method
	 */
	private String name;
	
	/**
	 * Context object for the remote method
	 */
	private String contextObject;

	/**
	 * Returns the name of the remote method
	 * 
	 * @return the name of the remote method
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of the remote method to be executed
	 * 
	 * @param name the method name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the context object of the method
	 * 
	 * @return the context object
	 */
	public String getContextObject() {
		return contextObject;
	}

	/**
	 * Set the context object name
	 * 
	 * @param contextObject the context object of the method
	 */
	public void setContextObject(String contextObject) {
		this.contextObject = contextObject;
	}

}
