package com.expanz.model.request;

/**
 * CommandInput object for the com.expanz.ExpanzCommand. 
 * Used for the the Field element of the Activity service call
 *
 */
public class FieldRequest {
	
	/**
	 * Id of the field
	 */
	private String id;
	
	/**
	 * Should this field be returned, if activity is suppressed
	 */
	private Boolean masked;
	
	/**
	 * Ctor.
	 */
	public FieldRequest() {} 
	
	/**
	 * Ctor.
	 * 
	 * @param id the field id
	 */
	public FieldRequest(String id) {
		this.id = id;
	}

	/**
	 * Ctor.
	 * 
	 * @param id the field id
	 * @param masked is this field masked
	 */
	public FieldRequest(String id, boolean masked) {
		this.id = id;
		this.masked = masked;
	}

	/**
	 * Returns the id of the field
	 * 
	 * @return the field id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Returns masking state
	 * 
	 * @return true if field is masked, not returned
	 */
	public Boolean getMasked() {
		return masked;
	}
	
}
