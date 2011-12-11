package com.expanz.widget;

import com.expanz.model.response.FieldResponse;

/**
 * Interface to allow widgets to be asynchronously updated 
 * via a {@link FieldResponse} object
 *
 */
public interface ExpanzFieldWidget {
	
	/**
	 * Returns the id of the associated field
	 * 
	 * @return the id
	 */
	String getFieldId();
	
	/**
	 * Setter to allow the FieldResponse object to 
	 * updated he value(s) of the widget
	 * @param field
	 */
	void setField(FieldResponse field);

}
