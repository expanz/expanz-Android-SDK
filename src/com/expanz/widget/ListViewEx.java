package com.expanz.widget;

import java.lang.reflect.Field;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import com.expanz.R;
import com.expanz.app.data.ListSimpleAdapterEx;
import com.expanz.model.response.Data;

public class ListViewEx extends ListView {
	
	/**
	 * The context object associated with this list view
	 */
	private String modelContextObject;
	
	/**
	 * The row layout identifier
	 */
	private int rowLayout;
	
	/**
	 * Ctor.
	 * 
	 * @param context the activity
	 */
	public ListViewEx(Context context) {
		super(context);
		this.setFocusable(true);
		throw new RuntimeException("activity_name attribute must be defined");
	}

	/**
	 * Ctor. 
	 * 
	 * @param context the activity
	 * @param attrs values defined in attr.xml
	 */
	public ListViewEx(Context context, AttributeSet attrs) {

		super(context, attrs);
		this.setFocusable(true);
		init(attrs);
	
	}

	/**
	 * Ctor.
	 * 
	 * @param context the activity
	 * @param attrs values defined in attr.xml
	 * @param defStyle the definition style
	 */
	public ListViewEx(Context context, AttributeSet attrs, int defStyle) {

		super(context, attrs, defStyle);
		this.setFocusable(true);
		init(attrs);
		
	}
	
	/**
	 * Load values from the attribute set
	 * 
	 * @param attrs values defined in attr.xml
	 */
	private void init(AttributeSet attrs) {
		TypedArray a = getContext().obtainStyledAttributes(attrs,R.styleable.BaseViewEx);
		
		modelContextObject = a.getString(R.styleable.BaseViewEx_context_object);
		
        if (modelContextObject == null) {
        	throw new RuntimeException("context_object attribute must be defined");
        } 
        
        a = getContext().obtainStyledAttributes(attrs,R.styleable.ListViewEx);
        
        //defined in xml in format expanz:row_layout="@+layout/name_of_file.xml"
        String fileName = a.getString(R.styleable.ListViewEx_row_layout);
        
        if (fileName == null) {
        	throw new RuntimeException("row_layout attribute must be defined");
        } 
        
        //can't find easier way, e.g. like a.getResourceId
        try {
			
			Class clazz = Class.forName("com.expanz.R$layout");
			
			Field field = clazz .getField(fileName.substring(fileName.lastIndexOf("/") + 1, fileName.lastIndexOf(".xml")));
			
			rowLayout = field.getInt(clazz.newInstance());
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("unable to find layout" + fileName);
		}
            
	}

	/**
	 * Get the context object associated with this View.
	 * 
	 * @return the context object
	 */
	public String getModelContextObject() {
		return modelContextObject;
	}
	
	/**
	 * Loads the data into the ListViews adapter
	 * 
	 * @param data the data to load
	 */
	public void updateData(Data data) {
		this.setAdapter(new ListSimpleAdapterEx(getContext(), rowLayout, data));
	}

	public int getRowLayout() {
		return rowLayout;
	}

}
