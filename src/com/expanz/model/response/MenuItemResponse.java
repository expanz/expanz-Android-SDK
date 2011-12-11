package com.expanz.model.response;

/**
 * Encapsulates menu item data.
 *
 */
public class MenuItemResponse {
	
	/**
	 * The remote action to execute for the item
	 */
	private String action;
	
	/**
	 * The text label of menu item
	 */
	private String text;
	
	/**
	 * To transition to another activity, without starting a new one
	 */
	private String transitionStyle;
	
	/**
	 * Ctor.
	 */
	public MenuItemResponse() {}
	
	/**
	 * Ctor.
	 * 
	 * @param action the action to remotely execute
	 * @param text the text of the menu item
	 * @param transitionStyle the name of an activity transition
	 */
	public MenuItemResponse(String action, String text, String transitionStyle) {
		this.action = action;
		this.text = text;
		this.transitionStyle = transitionStyle;
	}

	/**
	 * Returns the action to remotely execute
	 * 
	 * @return the action name
	 */
	public String getAction() {
		return action;
	}

	/**
	 * Set the remote action name
	 * 
	 * @param action the action name
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * Return the menu item text/label
	 * 
	 * @return the text of the menu item
	 */
	public String getText() {
		return text;
	}

	/**
	 * Set the text of the menu item
	 * 
	 * @param text the menu item text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Get the style name for an activity transition, 
	 * i.e. start an android activity without creating and 
	 * re-use the current Expanz activity
	 * 
	 * @return the name of the transition style
	 */
	public String getTransitionStyle() {
		return transitionStyle;
	}

	/**
	 * Set the transition style name
	 * 
	 * @param transitionStyle the name of the transition 
	 */
	public void setTransitionStyle(String transitionStyle) {
		this.transitionStyle = transitionStyle;
	}

}
