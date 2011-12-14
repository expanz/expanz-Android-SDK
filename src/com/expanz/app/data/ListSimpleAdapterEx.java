package com.expanz.app.data;

import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.expanz.ExpanzApplication;
import com.expanz.model.response.Data;
import com.expanz.model.response.DataRow;

/**
 * Adapter used for ListViews. 
 * 
 * Currently supported widgets for ListViews are;
 * 
 * TextViewEx
 * ImageViewEx
 * 
 * Users can register their own types via the com.expanz.ExpanzApplication class.
 * 
 * Currently Expanz does not support pagination so all List data is loaded in the constructor
 * via the Data object. Note: this is dangerous.  
 * 
 */
public class ListSimpleAdapterEx extends ArrayAdapter<DataRow> {
	
	private static final String TAG = "ListSimpleAdapterEx";
	
	private int mRowLayoutId;
	
	private Map<String, ListViewableHandler> listViewables;
	
	/**
	 * Ctor
	 * @param context the context (Activity) of the List
	 * @param rowLayoutId the layout file reference
	 * @param data the data of the List
	 */
	public ListSimpleAdapterEx(Context context, int rowLayoutId, Data data) {
		super(context,rowLayoutId, data.getRows());
		mRowLayoutId = rowLayoutId;
		
		ExpanzApplication appContext = (ExpanzApplication)context.getApplicationContext();
		
		listViewables = appContext.getListViewables();
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		DataRow row = getItem(position);
		
		if(convertView == null) {
			LayoutInflater inflater = ((Activity)getContext()).getLayoutInflater();
			convertView = inflater.inflate(mRowLayoutId, null, true);
		}
		
		convertView.setTag(new Long(getItemId(position)));
	
		recurseChildren(convertView, convertView, row, position);
		
		return convertView;
	}
	
	/**
	 * Recurse through the children to determine 
	 * 
	 * @param convertView
	 * @param rowView
	 * @param data
	 * @param position
	 */
	private void recurseChildren(View convertView, View rowView, DataRow data, int position) {
		
		if(rowView == null) {
			return;
		}
		
		if(rowView instanceof ViewGroup) {
			
			int childCount = ((ViewGroup) rowView).getChildCount();
			
			if(childCount > 0) {
				for(int i = 0; i < childCount; i++) {
					recurseChildren(convertView, ((ViewGroup) rowView).getChildAt(i), data, position);
				}
			}
			
		} else if(rowView instanceof ListViewable) {
			
			ListViewable listViewable = (ListViewable) rowView;
			
			ListViewableHandler listViewableHandler = listViewables.get(listViewable.getClass().getName());
			
			if(listViewableHandler != null) {
				listViewableHandler.handle(convertView, rowView, data, position, getItemId(position));
			}
			
		}
		
	}

}
