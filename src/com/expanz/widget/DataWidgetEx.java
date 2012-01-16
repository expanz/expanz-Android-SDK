package com.expanz.widget;

import android.widget.ListAdapter;

import com.expanz.model.request.DataPublicationRequest;
import com.expanz.model.response.Data;

/**
 * Interface that defines a common contract for 
 * widgets that relate to {@link Data} objects
 *
 */
public interface DataWidgetEx {
	
	/**
	 * Update the widget with the supplied data
	 * @param data the repeating data
	 */
	void updateData(Data data);
	
	/**
	 * Returns the data id of the data item
	 * @return the data id
	 */
	String getDataId();
	
	/**
	 * Convert to a data publication request
	 * 
	 * @return the data publication
	 */
	DataPublicationRequest toPublication();
	
	/**
	 * Get the adapter for this widget
	 * 
	 * @return the data adapter
	 */
	ListAdapter getListAdapter();
	
	/**
	 * Get the context object for this widget
	 * 
	 * @return context object name
	 */
	String getContextObject();

}
