package com.expanz.widget;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;

/**
 * Widget for capturing password text.
 *
 */
public class EditTextPasswordEx extends EditTextEx {
	
	/**
	 * Ctor.
	 * 
	 * @param context the activity
	 */
	public EditTextPasswordEx(Context context) {
		super(context);
		setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
	}

	/**
	 * Ctor.
	 * 
	 * @param context the activity
	 * @param attrs values defined in attr.xml
	 */
	public EditTextPasswordEx(Context context, AttributeSet attrs) {

		super(context, attrs);
		setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
	}

	/**
	 * Ctor.
	 * 
	 * @param context the activity
	 * @param attrs values defined in attr.xml
	 * @param defStyle the definition style
	 */
	public EditTextPasswordEx(Context context, AttributeSet attrs, int defStyle) {

		super(context, attrs, defStyle);
		setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
	}
	
	
}
