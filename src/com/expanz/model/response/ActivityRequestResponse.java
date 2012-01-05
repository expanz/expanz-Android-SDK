package com.expanz.model.response;

import com.expanz.ExpanzCommandImpl;

/**
 * Holds state data for the response data from 
 * an activity-request request.
 * 
 * Forms the Command output object of the {@link ExpanzCommandImpl}
 *
 */
public class ActivityRequestResponse {
	
	/**
	 * The id of the activity to create
	 */
	private String id;
	
	/**
	 * The key of the activity to create
	 */
	private String key;
	
	/**
	 * The style of the activity to create
	 */
	private String style;

	/**
	 * Return the id of the activity
	 * 
	 * @return the activity's id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Set the id of the activity
	 * 
	 * @param id the id of the activity
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns the key of the activity
	 * 
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Set the key of the activity
	 * 
	 * @param key the activity key
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Get the style of the activity
	 * 
	 * @return the activity style
	 */
	public String getStyle() {
		return style;
	}

	/**
	 * Set the style of the activity
	 * 
	 * @param style the style of the activity
	 */
	public void setStyle(String style) {
		this.style = style;
	}
	
}
