package com.expanz.model.response;

/**
 * Encapsulates the meta data of a column of data from a publication. 
 */
public class DataColumn {
	
	/**
	 * The id of the column
	 */
	private String id;
	
	/**
	 * The label of the column
	 */
	private String label;
	
	/**
	 * The id of the field associated with the column
	 */
	private String fieldId;
	
	/**
	 * The data type of the column data
	 */
	private String datatype;

	/**
	 * Ctor.
	 * 
	 * @param id the id of the column
	 * @param label the label for the column
	 * @param fieldId the id of the associated field
	 * @param datatype the data type of the column data 
	 */
	public DataColumn(String id, String label, String fieldId, String datatype) {
		this.id = id;
		this.label = label;
		this.fieldId = fieldId;
		this.datatype = datatype;
	}

	/**
	 * Returns the column's label/title
	 * 
	 * @return the label for the column
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Set the Label for the column
	 * 
	 * @param label the label for the column
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Get the id of the associated field
	 * 
	 * @return the field's id
	 */
	public String getFieldId() {
		return fieldId;
	}

	/**
	 * Set the id of the associated field
	 * 
	 * @param fieldId the associated field
	 */
	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	/**
	 * Get the id of the column
	 * 
	 * @return the id of the column
	 */
	public String getId() {
		return id;
	}

	/**
	 * Set the id of the column
	 * 
	 * @param id the id of the column
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Return the data type of the column data
	 * 
	 * @return the column's data type
	 */
	public String getDatatype() {
		return datatype;
	}

	/**
	 * Set the data type of the column
	 * 
	 * @param datatype the columns data type, e.g. Number
	 */
	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

}
