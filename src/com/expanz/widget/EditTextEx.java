package com.expanz.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import com.expanz.R;
import com.expanz.ServiceCallback;
import com.expanz.app.ContextEx;
import com.expanz.model.Message;
import com.expanz.model.request.ActivityRequest;
import com.expanz.model.request.DeltaRequest;
import com.expanz.model.response.ActivityResponse;
import com.expanz.model.response.FieldResponse;
import com.expanz.validation.Validateable;

/**
 * This widget extends EditText and sends delta requests to the Expanz server. 
 * 
 * Required a attributes are;
 * 
 * fieldId the id of the the field used to specify the 
 *
 */
public class EditTextEx extends EditText implements ExpanzFieldWidget {
	
	private String fieldId;
	
	private Validateable validator;
		
	/**
	 * Ctor.
	 * 
	 * @param context the activity
	 */
	public EditTextEx(Context context) {
		super(context);
		registerListener();
		throw new RuntimeException("field_id attribute must be defined");
	}

	/**
	 * Ctor. 
	 * 
	 * @param context the activity
	 * @param attrs values defined in attr.xml
	 */
	public EditTextEx(Context context, AttributeSet attrs) {

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
	public EditTextEx(Context context, AttributeSet attrs, int defStyle) {

		super(context, attrs, defStyle);
		init(attrs);
		registerListener();
		
	}
	
	/**
	 * Set the validator, which is used for validating and displaying
	 * error messages. 
	 *  
	 * @param validator
	 */
	public void setValidator(Validateable validator) {
		this.validator = validator;
	}

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
	 * Register the OnFocusChangeListener that will send delta requests when the 
	 * EditText view 
	 */
	private void registerListener() {
		
		this.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			public void onFocusChange(View view, boolean hasFocus) {
				
				if(validator != null) {
					if(!validator.validate(getText().toString())) {
						for(Message message : validator.getMessages()) {
							setError((message == null) ? null : message.getMessage());
						}
					}
				}
				
				if(view instanceof EditTextEx) {
					
					EditTextEx exView = (EditTextEx) view;
					
					final ContextEx contextEx = (ContextEx) getContext();
					
					if(!hasFocus) {
						
						ActivityRequest request = new ActivityRequest(contextEx.getActivityHandle(), contextEx.getSessionHandle());
						DeltaRequest deltaRequest = new DeltaRequest(exView.getFieldId(), exView.getText().toString());
						
						request.addDelta(deltaRequest);
						
						contextEx.getCommand().execute(request,
								new ServiceCallback<ActivityResponse>() {

							public void completed(ActivityResponse response) {
								
								contextEx.initFields(response);
								
								for (FieldResponse field : response.getFields()) {
									if(field.getId().equals(fieldId)) {
										
										if(field.isNull()) {
											setText(null);
										} else {
											setText(field.getValue());
										}
										
										if(!field.isValid()) {
											
											Message message = response.getMessage(field.getId());
											
											setError((message == null) ? null : message.getMessage());
										} else {
											setError(null);
										}
										
									}
									
								}
								
							}
						});
						
						
					}
					
					contextEx.hideKeyboard();
				}

			}

		});
		
	}

	/**
	 * Get the field id of the editable field. 
	 */
	public String getFieldId() {
		return fieldId;
	}

	/**
	 * Set the field id of the editable field
	 * @param fieldId
	 */
	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	/**
	 * Set the editable view's text 
	 */
	public void setField(FieldResponse field) {
		setText(field.getValue());
		
	}

}
