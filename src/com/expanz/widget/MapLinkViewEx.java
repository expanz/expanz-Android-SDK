package com.expanz.widget;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;

/**
 * Widget that launches a location/navigation application with the 
 * users current geo location
 *
 */
public class MapLinkViewEx extends TextViewEx {
	
	/**
	 * Ctor.
	 * 
	 * @param context the activity
	 */
	public MapLinkViewEx(Context context) {
		super(context);
	}

	/**
	 * Ctor.
	 * 
	 * @param context the activity
	 * @param attrs values defined in attr.xml
	 */
	public MapLinkViewEx(Context context, AttributeSet attrs) {
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
	public MapLinkViewEx(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		registerListener();
		setUseValue(true);
	}
	
	/**
	 * Register an OnClickListener that will launch the location based app
	 */
	private void registerListener() {
		
		setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
				Uri.parse("geo:0,0?q=" + getText()));
		        getContext().startActivity(Intent.createChooser(intent, "Find Location"));
			}
			
		});
		
	}

}
