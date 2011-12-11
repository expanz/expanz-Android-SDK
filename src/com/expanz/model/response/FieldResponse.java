package com.expanz.model.response;

import com.expanz.validation.Validateable;

/**
 * Encapsulates the data for the field elements
 */
public class FieldResponse {
	
	/**
	 * The id of the field
	 */
	private String id;
	
	/**
	 * The label of the field
	 */
	private String label;
	
	/**
	 * The data type of the field
	 */
	private String type;
	
	/**
	 * The value of the field
	 */
	private String value;
	
	/**
	 * The URL of field data, e.g. image data
	 */
	private String url;
	
	/**
	 * The file extension of the field, e.g. JPEG
	 */
	private String fileExt;
	
	/**
	 * Are null values allowable for the field
	 */
	private boolean nullable;
	
	/**
	 * Is the current field value valid
	 */
	private boolean valid;
	
	/**
	 * Is the field disabled/un-editable in the current context
	 */
	private boolean disabled;
	
	/**
	 * Is the value currently null
	 */
	private boolean isNull;

	/**
	 * Validates the value 
	 */
	private Validateable validator;

	/**
	 * Returns the field id
	 * 
	 * @return the field's id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Set the field's id
	 * 
	 * @param id the field'd id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Return the field's label
	 * 
	 * @return the field's label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Set the field's label
	 * 
	 * @param label the field's label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Returns the field's data type
	 * 
	 * @return the data type of the field, e.g. Phone
	 */
	public String getType() {
		return type;
	}

	/**
	 * Set the data type of the field
	 * 
	 * @param type the data type of the field, e.g. Number
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Returns the value of the field
	 * 
	 * @return the field's value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Set the value of the field
	 * 
	 * @param value the field's value
	 * 
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Does the field allow nulls
	 * 
	 * @return true if null is allowable
	 */
	public boolean isNullable() {
		return nullable;
	}

	/**
	 * Set the state of the nullable flag
	 * 
	 * @param nullable true if null values are allowable
	 */
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	/**
	 * Is the current value valid
	 * 
	 * @return true if value is valid
	 */
	public boolean isValid() {
		return valid;
	}

	/**
	 * Set the validity of the current value
	 * 
	 * @param valid true if value is currently valid
	 */
	public void setValid(boolean valid) {
		this.valid = valid;
	}

	/**
	 * Is the value currently editable
	 * 
	 * @return true if unable to edit
	 */
	public boolean isDisabled() {
		return disabled;
	}

	/**
	 * Set the editability of the field
	 * 
	 * @param disabled true if disabled from edits
	 */
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
	/**
	 * Set the value of the field as being null
	 * 
	 * @param isNull true if value is currently null
	 */
	public void setIsNull(boolean isNull) {
		this.isNull = isNull;
	}
	
	/**
	 * Is the value currently null
	 * 
	 * @return true if value is null
	 */
	public boolean isNull() {
		return isNull;
	}

	/**
	 * Get the validator associated with the field
	 * 
	 * @return a validator for the field
	 */
	public Validateable getValidator() {
		return validator;
	}

	/**
	 * Set the {@link Validateable} (validator) for the field
	 * 
	 * @param validator used to validate the field
	 */
	public void setValidator(Validateable validator) {
		this.validator = validator;
	}

	/**
	 * Get the URL of the field data, e.g. for image data
	 * 
	 * @return the URL of the associated data
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Set the URL of  associated data, e.g. for image data
	 * 
	 * @param url the URL of the data
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Returns the file extension of the associated data
	 * 
	 * @return the file extension, e.g. JPEG
	 */
	public String getFileExt() {
		return fileExt;
	}

	/**
	 * Set the file extension of the associated data
	 * 
	 * @param fileExt the file extension, e.g. PDF
	 */
	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

}
