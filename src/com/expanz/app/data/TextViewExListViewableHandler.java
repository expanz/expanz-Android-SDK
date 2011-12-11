package com.expanz.app.data;

import android.view.View;

import com.expanz.model.response.DataCell;
import com.expanz.model.response.DataRow;
import com.expanz.widget.TextViewEx;

/**
 * Set the appropriate value for the TextView, i.e. setText
 */
public class TextViewExListViewableHandler implements ListViewableHandler {

	/**
	 * Simply calls set text on any text view, with the appropriate value 
	 * from the data row. 
	 */
	public void handle(View convertView, View rowView, DataRow data,
			int position, long id) {
		
		final TextViewEx textView = (TextViewEx) rowView;
		
		for(DataCell cell : data.getCells()) {
			if(textView.getFieldId().equals(cell.getFieldId())) {
				((TextViewEx) rowView).setText(cell.getValue());
			}
		}
		
	}

}
