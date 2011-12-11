package com.expanz.validation;

/**
 * Validator to determine if the supplied value is 
 * a valid number value. 
 *
 */
public class NumberValidator extends BaseValidator {
	
	/**
	 * Are null values allowed
	 */
	private Boolean nullable;
	
	/**
	 * What is the minimum allowable value
	 */
	private Double minValue;
	
	/**
	 * What is the maximum allowable value
	 */
	private Double maxValue;
	
	/**
	 * The validation specific attributes of the widget
	 */
	private String[] attributes = new String[] { "nullable", "minValue", "maxValue" };
	
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
		
		if (nullable != null) {
			if (!nullable && value == null) {
				//TODO externalize messages
				addMessage("must not be null");
				returnValue = false;
			}
		}
		
		if (minValue != null) {
			if (value != null) {
				try {
					
					Double convertedValue = stripCommon(value);
					
					if(convertedValue < minValue) {
						//TODO externalize messages
						addMessage("must be greater than " + maxValue);
						returnValue = false;
					}
					
					if(convertedValue > maxValue) {
						//TODO externalize messages
						addMessage("must be less than " + maxValue);
						returnValue = false;
					}
					
				} catch (Exception e) {
					//TODO externalize messages
					addMessage("invalid number");
					returnValue = false;
				}
			}
		}
		
		return returnValue;
	}

	/**
	 * Strip out common allowable prefixes and suffixes
	 * such as currency, percentages etc
	 * 
	 * @param value the value to clean
	 * 
	 * @return
	 */
	private Double stripCommon(String value) {
		
		value = value.replaceAll("\\$", "");
		value = value.replaceAll("\\%", "");
		
		Double converted = Double.parseDouble(value);
		
		return converted;
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
		
		if (attribute != null && value != null) {
			
			if(attribute.equals("nullable")) {
				
				if(value.equals("0")) {
					nullable = false;
				} else {
					nullable = true;
				}
				
			}
			
			if (attribute.equals("minValue")) {
				//probably safe not to type check here
				minValue = Double.parseDouble(value);
			}
			
			if (attribute.equals("maxValue")) {
				//probably safe not to type check here
				maxValue = Double.parseDouble(value);
			}
			
		}
		
	}

}
