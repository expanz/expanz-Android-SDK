package com.expanz.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;

/**
 * Widget that allows a text link to launch an email application.
 * 
 *
 */
public class EmailLinkView extends TextViewEx {
	
	/**
	 * Ctor.
	 * 
	 * @param context the activity
	 * @param attrs values defined in attr.xml
	 */
	public EmailLinkView(Context context) {
		super(context);
	}

	/**
	 * Ctor.
	 * 
	 * @param context the activity
	 * @param attrs values defined in attr.xml
	 */
	public EmailLinkView(Context context, AttributeSet attrs) {
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
	public EmailLinkView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		registerListener();
		setUseValue(true);
	}
	
	/**
	 * Register an OnClickListener to launch the email application
	 */
	private void registerListener() {
		
		setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				
				final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

				emailIntent.setType("plain/text");
				emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ getText().toString() } );
				
				getContext().startActivity(Intent.createChooser(emailIntent, "Send mail"));
			}
			
		});
		
	}

}
