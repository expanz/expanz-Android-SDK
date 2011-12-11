package com.expanz.model.request;

/**
 * CommandInput object for the com.expanz.ExpanzCommand. 
 * Used for the the CreateActivity service call
 *
 */
public class MenuActionRequest {
	
	/**
	 * Execute the default menu action
	 */
	private Boolean defaultAction;
	
	/**
	 * The context object for the menu request
	 */
	private String context;
	
	/**
	 * The menu action to execute
	 */
	private String action;

	/**
	 * Returns state of default action execution
	 * 
	 * @return true if default action is to be executed
	 */
	public Boolean getDefaultAction() {
		return defaultAction;
	}

	/**
	 * Set the default action execution flag
	 * 
	 * @param defaultAction true if you wish the default action to be
	 * executed
	 */
	public void setDefaultAction(Boolean defaultAction) {
		this.defaultAction = defaultAction;
	}

	/**
	 * Returns the context object of the request
	 * 
	 * @return the name of the context object
	 */
	public String getContext() {
		return context;
	}

	/**
	 * Set the context object
	 * 
	 * @param context the name of the context object
	 */
	public void setContext(String context) {
		this.context = context;
	}

	/**
	 * Returns name of the menu action
	 * 
	 * @return the menu action name
	 */
	public String getAction() {
		return action;
	}

	/**
	 * Name of the action to be remotely executed
	 * 
	 * @param action the action to be executed
	 */
	public void setAction(String action) {
		this.action = action;
	}

}
