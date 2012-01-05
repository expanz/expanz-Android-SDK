package com.expanz.widget;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;

import com.expanz.R;
import com.expanz.model.response.FieldResponse;

/**
 * Widget to launch the device's phone. This widget requires an 
 * Image as it extends ImageButton.
 * 
 * Required a attributes are;
 * 
 * fieldId the id of the the field used to launch the phone (i.e. a valid phone number field). 
 *
 */
public class CallImageButtonEx extends ImageButton implements ExpanzFieldWidget {
	
	private String fieldId;
	
	private FieldResponse field;
	
	/**
	 * Ctor.
	 * 
	 * @param context the activity
	 */
	public CallImageButtonEx(Context context) {
		super(context);
	}

	/**
	 * Ctor. 
	 * 
	 * @param context the activity
	 * @param attrs values defined in attr.xml
	 */
	public CallImageButtonEx(Context context, AttributeSet attrs) {
		super(context, attrs);
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
	public CallImageButtonEx(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
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
		
		String fieldId = a.getString(R.styleable.BaseViewEx_field_id);
		
        if (fieldId != null) {
        	this.fieldId = fieldId;
      
        } else {
    		throw new RuntimeException("field_id attribute must be defined");
        }
        

	}
	
	/**
	 * Registers an on click listener that starts the activity
	 */
	private void registerListener() {
		
		setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				
				if(field == null) {
					return;
				}
				
				Intent callIntent = new Intent(Intent.ACTION_CALL);
		        callIntent.setData(Uri.parse("tel:" + field.getValue()));
		        getContext().startActivity(Intent.createChooser(callIntent, "Find Location"));
			}
			
		});
		
	}

	/**
	 * Returns the fieldId of the phone number field. 
	 */
	public String getFieldId() {
		return fieldId;
	}

	/**
	 * Set the phone number field. Requires a valid phone number.
	 */
	public void setField(FieldResponse field) {
		this.field = field;
	}


}
