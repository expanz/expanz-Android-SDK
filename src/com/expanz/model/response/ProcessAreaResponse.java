package com.expanz.model.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates the Process Area
 *
 */
public class ProcessAreaResponse {
	
	/**
	 * The id of the process area
	 */
	private String id;
	
	/**
	 * The title of the process area
	 */
	private String title;
	
	/**
	 * The activities associated with this process area
	 */
	private List<ProcessAreaActivityResponse> activities = new ArrayList<ProcessAreaActivityResponse>();

	/**
	 * Returns the activities of the process area
	 * 
	 * @return the activities
	 */
	public List<ProcessAreaActivityResponse> getActivities() {
		return activities;
	}

	/**
	 * Returns the id of the process area
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Set the id of the process area
	 * 
	 * @param id the id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns the title of the process area
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set the title of the process area
	 * 
	 * @param title the title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

}
