package com.expanz.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;

import com.expanz.R;
import com.expanz.ServiceCallback;
import com.expanz.app.ContextEx;
import com.expanz.model.request.ActivityRequest;
import com.expanz.model.request.MethodRequest;
import com.expanz.model.response.ActivityResponse;

/**
 * Widget that calls remote methods on the Expanz server.
 * 
 * Required attributes are;
 * 
 * methodName - the name of the remote method to execute
 * 
 */
public class ImageButtonEx extends ImageButton {

	private String methodName;
	private String contextObject;

	/**
	 * Ctor.
	 * 
	 * @param context
	 *            the activity
	 */
	public ImageButtonEx(Context context) {
		super(context);
	}

	/**
	 * Ctor.
	 * 
	 * @param context
	 *            the activity
	 * @param attrs
	 *            values defined in attr.xml
	 */
	public ImageButtonEx(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
		registerListener();
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
	public ImageButtonEx(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
		registerListener();
	}

	/**
	 * Load values from the attribute set
	 * 
	 * @param attrs
	 *            values defined in attr.xml
	 */
	private void init(AttributeSet attrs) {

		TypedArray a = getContext().obtainStyledAttributes(attrs,
				R.styleable.ButtonEx);

		methodName = a.getString(R.styleable.ButtonEx_method_name);

		if (methodName == null) {
			throw new RuntimeException("method_name attribute must be defined");
		}

		a = getContext().obtainStyledAttributes(attrs, R.styleable.BaseViewEx);

		contextObject = a.getString(R.styleable.BaseViewEx_context_object);

	}

	/**
	 * Register an OnClickListener that will call the remote method
	 */
	private void registerListener() {

		setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {

				if (isInEditMode()) {
					return;
				}

				final ContextEx contextEx = (ContextEx) getContext();

				contextEx.grabFocusHack();

				contextEx.hideKeyboard();

				ActivityRequest request = new ActivityRequest(
						contextEx.getActivityHandle(),
						contextEx.getSessionHandle());

				MethodRequest method = new MethodRequest();
				method.setName(methodName);
				method.setContextObject(contextObject);
				request.addMethod(method);

				contextEx.getCommand().execute(request,
						new ServiceCallback<ActivityResponse>() {

							public void completed(ActivityResponse activity) {
								
								contextEx.initFields(activity);
								
							}

						});

			}

		});

	}
}
