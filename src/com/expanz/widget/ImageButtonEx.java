package com.expanz.widget;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;

import com.expanz.ExpanzCommand;
import com.expanz.R;
import com.expanz.ServiceCallback;
import com.expanz.app.ContextEx;
import com.expanz.model.Message;
import com.expanz.model.request.ActivityRequest;
import com.expanz.model.request.MethodRequest;
import com.expanz.model.response.ActivityResponse;
import com.expanz.model.response.FieldResponse;
import com.google.inject.Inject;

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
								Map<String, List<TextViewEx>> labels = contextEx
										.getFieldLabels();
								Map<String, List<ExpanzFieldWidget>> fieldWidgetMap = contextEx
										.getFieldWidgets();

								for (FieldResponse field : activity.getFields()) {
									List<TextViewEx> fieldLabels = labels
											.get(field.getId());

									for (TextViewEx label : fieldLabels) {

										if (label != null) {
											if (label.isUseValue()) {
												label.setText(field.getValue());
											} else if (field.getLabel() != null) {
												label.setText(field.getLabel());
											}
										}

									}

									List<ExpanzFieldWidget> fieldWidgets = fieldWidgetMap
											.get(field.getId());

									if (fieldWidgets != null) {

										for (ExpanzFieldWidget fieldWidget : fieldWidgets) {

											if (fieldWidget instanceof EditTextEx) {

												EditTextEx editable = (EditTextEx) fieldWidget;

												if (field.isNull()) {
													editable.setText(null);
												} else {
													editable.setText(field
															.getValue());
												}

												if (!field.isValid()) {

													Message message = activity
															.getMessage(field
																	.getId());

													editable.setError((message == null) ? null
															: message
																	.getMessage());
												}
											}

										}

									}
								}

								if (activity.hasMessage()) {
									contextEx.displayMessages(activity
											.getMessages());
								}
							}

						});

			}

		});

	}
}
