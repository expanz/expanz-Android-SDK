package com.expanz.model.response;

/**
 * Encapsulates the data for a {@link DataRow}s cell
 */
public class DataCell {
	
	/**
	 * Id of the cell
	 */
	private String id;
	
	/**
	 * Id of the field associated with the cell
	 */
	private String fieldId;
	
	/**
	 * The value of the field associated with the cell
	 */
	private String value;
	
	/**
	 * The label/title of the cell
	 */
	private String label;

	/**
	 * Returns the label/title/header of the cell
	 * 
	 * @return the cell label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Set the cell label
	 * 
	 * @param label the cell's label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Returns the id of the cell
	 * 
	 * @return unique identifier for the cell
	 */
	public String getId() {
		return id;
	}

	/**
	 * Set the id of the cell
	 * 
	 * @param id the cell's id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Return the id of the associated field
	 * 
	 * @return the field id
	 */
	public String getFieldId() {
		return fieldId;
	}

	/**
	 * Set the id of the associated field
	 * 
	 * @param fieldId the associated field's id
	 */
	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	/**
	 * Return the value of the cell data
	 * 
	 * @return the actual value of the cell
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Set the value of the cell
	 * 
	 * @param value the cell's value
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
