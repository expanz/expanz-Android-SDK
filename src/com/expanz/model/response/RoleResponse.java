package com.expanz.model.response;

/**
 * Encapsulates Role data
 */
public class RoleResponse {
	
	/**
	 * The id of the role
	 */
	private String id;
	
	/**
	 * The value of the role
	 */
	private String value;
	
	/**
	 * Ctor.
	 */
	public RoleResponse() {}

	/**
	 * Ctor.
	 * 
	 * @param id the id of the role
	 * @param value the value of the role
	 */
	public RoleResponse(String id, String value) {
		this.id = id;
		this.value = value;
	}

	/**
	 * Returns the id of the role
	 * 
	 * @return the role's id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Set the id of the role
	 * 
	 * @param id the role's id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns the value/name of the role
	 * 
	 * @return the role's value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Set the name of the role
	 * 
	 * @param value the role's value
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	

}
