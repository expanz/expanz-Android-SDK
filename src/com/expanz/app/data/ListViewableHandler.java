package com.expanz.app.data;

import android.view.View;

import com.expanz.model.response.DataRow;

/**
 * Allows for the extensibility of the List Adapter to render multiple Expanz Widgets.
 * 
 * This interface will allow users to register their own custom types via the
 * ExpanzApplication class, e.g. either subclass ExpanzApplication or add
 * ListViewable objects directly to it.  
 * 
 */
public interface ListViewableHandler {
	
	/**
	 * Update the widget based on the supplied data.
	 * 
	 * @param convertView the current convert view (re-used view), if any. 
	 * @param rowView the actual view.
	 * @param data the data to update the view with. 
	 * @param position the position in the data set. 
	 * @param id the unique id for the position in the data set
	 */
	void handle(View convertView, View rowView, DataRow data, int position, long id);

}
