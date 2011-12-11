package com.expanz.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import com.expanz.R;
import com.expanz.app.data.ListViewable;
import com.expanz.model.response.FieldResponse;

/**
 * Expanz implementation of the TextView widget. Displays
 * value of the specified remote field.
 * 
 * Required attributes are;
 * 
 * FieldId - the id of the associated remote field
 *
 */
public class TextViewEx extends TextView implements ExpanzFieldWidget, ListViewable {
	
	/**
	 * Id of the remote field
	 */
	private String fieldId;
	
	/**
	 * Should we use the label or value of the {@link FieldResponse}
	 */
	private boolean useValue;

	/**
	 * Ctor.
	 * 
	 * @param context the activity
	 */
	public TextViewEx(Context context) {
		super(context);
		throw new RuntimeException("field_id attribute must be defined");
	}

	/**
	 * Ctor.
	 * 
	 * @param context the activity
	 * @param attrs values defined in attr.xml
	 */
	public TextViewEx(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}

	/**
	 * Ctor.
	 * 
	 * @param context the activity
	 * @param attrs values defined in attr.xml
	 * @param defStyle the definition style
	 */
	public TextViewEx(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
	}
	
	/**
	 * Load values from the attribute set
	 * 
	 * @param attrs values defined in attr.xml
	 */
	private void init(AttributeSet attrs) {
		
		TypedArray a = getContext().obtainStyledAttributes(attrs,R.styleable.BaseViewEx);
		
		String fieldId = a.getString(R.styleable.BaseViewEx_field_id);
		
        if (fieldId != null) {
        	this.fieldId = fieldId;
      
        } else {
    		throw new RuntimeException("field_id attribute must be defined");
        }
        
        a = getContext().obtainStyledAttributes(attrs,R.styleable.TextViewEx);
		
		useValue = a.getBoolean(R.styleable.TextViewEx_use_value, false);
	}

	/**
	 * Get the id of the corresponding field
	 */
	public String getFieldId() {
		return fieldId;
	}

	/**
	 * Set the field id of the corresponding field
	 * @param fieldId
	 */
	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	/**
	 * Alows either value or label of {@link FieldResponse}
	 * @param useValue true if use value instead of label
	 */
	public void setUseValue(boolean useValue) {
		this.useValue = useValue;
	}

	/**
	 * Return the value state
	 * 
	 * @return true if using value
	 */
	public boolean isUseValue() {
		return useValue;
	}

	/**
	 * Updates the value of the TextView
	 */
	public void setField(FieldResponse field) {
		setText(useValue ? field.getValue() : field.getLabel());
	}

}
