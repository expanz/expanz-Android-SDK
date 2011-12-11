package com.expanz.widget;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;

/**
 * Widget to launch the device's phone.
 * 
 * Required a attributes are;
 * 
 * fieldId the id of the the field used to launch the phone (i.e. a valid phone number field). 
 *
 */
public class CallLinkViewEx extends TextViewEx {
	
	/**
	 * Ctor.
	 * 
	 * @param context the activity
	 */
	public CallLinkViewEx(Context context) {
		super(context);
	}

	/**
	 * Ctor. 
	 * 
	 * @param context the activity
	 * @param attrs values defined in attr.xml
	 */
	public CallLinkViewEx(Context context, AttributeSet attrs) {
		super(context, attrs);
		registerListener();
		setUseValue(true);
	}

	/**
	 * Ctor.
	 * 
	 * @param context the activity
	 * @param attrs values defined in attr.xml
	 * @param defStyle the definition style
	 */
	public CallLinkViewEx(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		registerListener();
		setUseValue(true);
	}
	
	/**
	 * Registers an on click listener that starts the phone activity
	 */
	private void registerListener() {
		
		setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				
				
				Intent callIntent = new Intent(Intent.ACTION_CALL);
		        callIntent.setData(Uri.parse("tel:" + getText()));
		        getContext().startActivity(Intent.createChooser(callIntent, "Find Location"));
			}
			
		});
		
	}

}
