package com.expanz.model.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates the data represented by a data publication
 *
 */
public class Data {
	
	/**
	 * The id of the data publication
	 */
	private String id;
	
	/**
	 * The context object of the data publication
	 */
	private String contextObject;
	
	/**
	 * Used to return a contextual menu for a data publication item
	 */
	private String contextMenuMethod;
	
	/**
	 * Each of the data items in the publication
	 */
	private List<DataRow> rows = new ArrayList<DataRow>();

	/**
	 * Returns the id of the data publication
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Set the id of the data publication
	 * 
	 * @param id the publication's unique id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns of the the rows of the data set/publication
	 *  
	 * @return the list of rows
	 */
	public List<DataRow> getRows() {
		return rows;
	}

	/**
	 * Get the context object name associated with the publication
	 * 
	 * @return the context object name
	 */
	public String getContextObject() {
		return contextObject;
	}

	/**
	 * Set the context object of the associated publication
	 * 
	 * @param contextObject the context object name
	 */
	public void setContextObject(String contextObject) {
		this.contextObject = contextObject;
	}

	/**
	 * Get the context menu method, i.e. the method to return the context menu
	 * 
	 * @return get the 
	 */
	public String getContextMenuMethod() {
		return contextMenuMethod;
	}

	/**
	 * Set the method name for the contextual menu
	 * 
	 * @param contextMenuMethod name of the method
	 */
	public void setContextMenuMethod(String contextMenuMethod) {
		this.contextMenuMethod = contextMenuMethod;
	}

}
