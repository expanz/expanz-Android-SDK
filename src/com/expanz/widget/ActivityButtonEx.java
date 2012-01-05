package com.expanz.widget;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.expanz.R;
import com.expanz.app.ContextEx;
import com.expanz.app.ExpanzConstants;
import com.expanz.util.ActivityMapping;
import com.expanz.util.ActivityMappingHolder;
import com.google.inject.Inject;

/**
 * Expanz Widget for creating an "Expanz" activity 
 * 
 * Takes 2 parameters activityName and activityStyle
 * 
 * activityName is the name of the expanz Activity to create, this will need to map
 * to a valid activity mapping defined in activity_mapping.xml
 * 
 * activityStyle is the name of the activity style, e.g. Browse, activityStyle is not 
 * a required value
 * 
 */
public class ActivityButtonEx extends Button {

	private String activityName;
	private String activityStyle;
	private ActivityMapping mapping;

	
	/**
	 * Ctor.
	 * 
	 * @param context the activity
	 */
	public ActivityButtonEx(Context context) {
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
	public ActivityButtonEx(Context context, AttributeSet attrs) {

		super(context, attrs);
		this.setFocusable(true);
		init(attrs);
		registerListener();
		
	}

	/**
	 * Ctor.
	 * 
	 * @param context the activity
	 * @param attrs values defined in attr.xml
	 * @param defStyle the definition style
	 */
	public ActivityButtonEx(Context context, AttributeSet attrs, int defStyle) {

		super(context, attrs, defStyle);
		this.setFocusable(true);
		init(attrs);
		registerListener();
		
		
	}
	
	/**
	 * Load values from the attribute set
	 * 
	 * @param attrs values defined in attr.xml
	 */
	private void init(AttributeSet attrs) {
		TypedArray a = getContext().obtainStyledAttributes(attrs,R.styleable.BaseViewEx);
		
		activityName = a.getString(R.styleable.BaseViewEx_activity_name);
		activityStyle = a.getString(R.styleable.BaseViewEx_activity_style);
		
        if (activityName == null) {
        	throw new RuntimeException("method_name attribute must be defined");
        } 
            
	}
	 	
	/**
	 * Registers an on click listener that starts the activity
	 */
	private void registerListener() {
		
		setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				
				ContextEx context = (ContextEx) getContext();
				
				mapping = context.getMappingHolder().get(activityName, activityStyle);
			
				if(mapping != null) {

					Intent intent = new Intent(getContext(), mapping.getForm());
					intent.putExtra(ExpanzConstants.SESSION_HANDLE, context.getSessionHandle());
					getContext().startActivity(intent);
					
				}

			}

		});
		
	}

}
