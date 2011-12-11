package com.expanz.widget;

import com.expanz.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.Spinner;

/**
 * Expanz implementation of the Spinner widget
 * 
 * Required fields are;
 * 
 * fieldId - the id of the associated server side field
 *
 */
public class SpinnerEx extends Spinner {
	
	/**
	 * Id of the associated field
	 */
	private String fieldId;
	
	/**
	 * Ctor.
	 * 
	 * @param context the activity
	 */
	public SpinnerEx(Context context) {
		super(context);
		this.setFocusable(true);
		throw new RuntimeException("method_name attribute must be defined");
	}

	/**
	 * Ctor.
	 * 
	 * @param context the activity
	 * @param attrs values defined in attr.xml
	 */
	public SpinnerEx(Context context, AttributeSet attrs) {

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
	public SpinnerEx(Context context, AttributeSet attrs, int defStyle) {

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
		
		fieldId = a.getString(R.styleable.BaseViewEx_field_id);
		
        if (fieldId == null) {
    		throw new RuntimeException("field_id attribute must be defined");
        }
        
        
	}

	/**
	 * Return the id of the associated field
	 * @return
	 */
	public String getFieldId() {
		return fieldId;
	}


}
