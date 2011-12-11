package com.expanz.model.request;

/**
 * CommandInput object for the com.expanz.ExpanzCommand. 
 * Used for the the Field Delta element of the Activity service call
 *
 */
public class DeltaRequest {
	
	/**
	 * Id of field
	 */
	private String id;
	
	/**
	 * New value of field
	 */
	private String value;
	
	/**
	 * Encoding of data
	 */
	private String encoding;
	
	/**
	 * Used for encoded values
	 */
	private String text;
	
	/**
	 * Ctor.
	 */
	public DeltaRequest() {}
	
	/**
	 * Ctor
	 * 
	 * @param id field id
	 * @param value new value of field
	 */
	public DeltaRequest(String id, String value) {
		this.id = id;
		this.value = value;
	}

	/**
	 * Set the id for the associated field
	 * 
	 * @param id the id of the field
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Set the new value of the field data
	 * 
	 * @param value the new value 
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Returns the id of the associated field
	 * 
	 * @return the value of the field
	 */
	public String getId() {
		return id;
	}

	/**
	 * The value of the data
	 * 
	 * @return the fields data value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Returns the encoding of the text/value
	 * 
	 * @return the encoding type, e.g. BASE64
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * Set the encoding type
	 * 
	 * @param encoding the type of the encoding, e.g. BASE64
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * Return the value of encoded text data
	 * 
	 * @return encoded text data
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the value of encoded textual data
	 * 
	 * @param text encoded text data, e.g. a binary image
	 * that has been base64 encoded
	 */
	public void setText(String text) {
		this.text = text;
	}

}
