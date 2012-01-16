package com.expanz.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.expanz.R;

public class ContextMenuButton extends Button implements ContextMenuAware {
	
	/**
	 * Remote object on which to execute method
	 */
	private String contextObject;

	/**
	 * Ctor.
	 * 
	 * @param context
	 *            the activity
	 */
	public ContextMenuButton(Context context) {
		super(context);
		this.setFocusable(true);
		throw new RuntimeException("context_object attribute must be defined");
	}

	/**
	 * Ctor.
	 * 
	 * @param context
	 *            the activity
	 * @param attrs
	 *            values defined in attr.xml
	 */
	public ContextMenuButton(Context context, AttributeSet attrs) {

		super(context, attrs);
		this.setFocusable(true);
		init(attrs);

	}

	/**
	 * Ctor.
	 * 
	 * @param context
	 *            the activity
	 * @param attrs
	 *            values defined in attr.xml
	 * @param defStyle
	 *            the definition style
	 */
	public ContextMenuButton(Context context, AttributeSet attrs, int defStyle) {

		super(context, attrs, defStyle);
		this.setFocusable(true);
		init(attrs);

	}

	/**
	 * Load values from the attribute set
	 * 
	 * @param attrs
	 *            values defined in attr.xml
	 */
	private void init(AttributeSet attrs) {
		TypedArray a = getContext().obtainStyledAttributes(attrs,
				R.styleable.BaseViewEx);

		contextObject = a.getString(R.styleable.BaseViewEx_context_object);

		if (contextObject == null) {
			throw new RuntimeException("context_object attribute must be defined");
		}

	}

	public boolean isMenuEnabled() {
		//should always be enabled for this widget type
		return true;
	}

	public View getView() {
		return this;
	}


}
