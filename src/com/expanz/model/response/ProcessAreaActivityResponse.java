package com.expanz.model.response;

/**
 * Encapsulates Process Area data
 *
 */
public class ProcessAreaActivityResponse {
	
	/**
	 * The name of the process area
	 */
	private String name;
	
	/**
	 * The title of the process area
	 */
	private String title;
	
	/**
	 * The style of the process area
	 */
	private String style;
	
	/**
	 * Ctor.
	 */
	public ProcessAreaActivityResponse() {}

	/**
	 * Ctor.
	 * 
	 * @param name the name of the process area
	 * @param title the title of the process area
	 * @param style the style of the process area
	 */
	public ProcessAreaActivityResponse(String name, String title, String style) {
	
		this.name = name;
		this.title = title;
		this.style = style;
	}

	/**
	 * Returns the name of the process area
	 * 
	 * @return the name of the process area
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of the process area
	 * 
	 * @param name the name of the process area
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the title of the process area
	 * 
	 * @return the title of the process area
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set the title of the process area
	 * 
	 * @param title the title of the process area
	 * 
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Returns the style of the process area
	 * 
	 * @return the style name
	 */
	public String getStyle() {
		return style;
	}

	/**
	 * Set the style of the process area
	 * 
	 * @param style the stle name
	 */
	public void setStyle(String style) {
		this.style = style;
	}
	
	
	
}
