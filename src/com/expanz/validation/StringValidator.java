package com.expanz.validation;

/**
 * Validator to determine if the supplied value is 
 * a valid number value. 
 *
 */
public class StringValidator extends BaseValidator {
	
	/**
	 * Is the string nullable
	 */
	private Boolean nullable;

	/**
	 * What is the maximum length of the string
	 */
	private Integer maxLength;

	/**
	 * The validation specific attributes of the widget
	 */
	private String[] attributes = new String[] { "nullable", "maxLength" };

	/**
	 * Returns the validation specific attributes of the widget
	 */
	public String[] getAttributes() {
		return attributes;
	}

	/**
	 * Validate the widget
	 */
	public boolean validate(String value) {
		
		boolean returnValue = true;
		
		if(nullable != null) {
			if(!nullable && value == null) {
				//TODO externalize messages
				addMessage("must not be null");
				returnValue = false;
			}
		}
		
		if(maxLength != null) {
			if(value != null && value.length() > maxLength) {
				//TODO externalize messages
				addMessage("must be less than " +  maxLength);
				returnValue = false;
			}
		}
		
		return returnValue;
	}

	/**
	 * Set the field id of the validating field
	 */
	public void initField(String field) {
		this.field = field;
	}

	/**
	 * Set the attribute values, nullable, min and max
	 */
	public void initAttribute(String attribute, String value) {
		
		if(attribute != null && value != null) {
			
			if(attribute.equals("nullable")) {
				
				if(value.equals("0")) {
					nullable = false;
				} else {
					nullable = true;
				}
				
			}
			
			if(attribute.equals("maxLength")) {
				//probably safe not to type check here
				maxLength = Integer.parseInt(value);
			}
			
		}
		
	}

}
