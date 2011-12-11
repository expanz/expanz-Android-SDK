package com.expanz.widget;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;

import com.expanz.R;
import com.expanz.model.response.FieldResponse;

/**
 * Widget used for launching email client. This class extends ImageButton
 * so assumes there will be an image specified for the button. 
 *
 */
public class EmailImageButtonEx extends ImageButton implements ExpanzFieldWidget {
	
	private String fieldId;
	
	private FieldResponse field;
	
	/**
	 * Ctor.
	 * 
	 * @param context the activity
	 */
	public EmailImageButtonEx(Context context) {
		super(context);
	}

	/**
	 * Ctor.
	 * 
	 * @param context the activity
	 * @param attrs values defined in attr.xml
	 */
	public EmailImageButtonEx(Context context, AttributeSet attrs) {
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
	public EmailImageButtonEx(Context context, AttributeSet attrs, int defStyle) {
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
	 * Register OnClickListener for launching email application
	 */
	private void registerListener() {
		
		setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				
				if(field == null) {
					return;
				}
				
				final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

				emailIntent.setType("plain/text");
				emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ field.getValue() } );
				
				getContext().startActivity(Intent.createChooser(emailIntent, "Send mail"));
			}
			
		});
		
	}

	/**
	 * The field id of the associated email field, 
	 * requires valid email address value
	 */
	public String getFieldId() {
		return fieldId;
	}

	/**
	 * Set the field object
	 */
	public void setField(FieldResponse field) {
		this.field = field;
	}


}
