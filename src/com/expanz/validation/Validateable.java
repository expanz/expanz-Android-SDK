package com.expanz.validation;

import java.util.List;

import com.expanz.model.Message;

/**
 * Marks a widget as being able to be validated
 */
public interface Validateable {
	
	/**
	 * The validation error messages
	 * @return the messages
	 */
	List<Message> getMessages();
	
	/**
	 * The attributes that define validateable properties of the 
	 * widget
	 * 
	 * @return the attributes
	 */
	String[] getAttributes();
	
	/**
	 * Set the field id value
	 * 
	 * @param field
	 */
	void initField(String field);
	
	/**
	 * Initialize the attribute values for the widget
	 * 
	 * @param attribute the attribute to set
	 * @param value the value to set it to
	 */
	void initAttribute(String attribute, String value);
	
	/**
	 * Validate the value of the input
	 * 
	 * @param value the input value
	 * 
	 * @return true if valid
	 */
	boolean validate(String value);

}
