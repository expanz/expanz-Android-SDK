package com.expanz.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.expanz.R;
import com.expanz.app.ContextEx;
import com.expanz.model.entity.ImageDetails;
import com.expanz.model.response.FieldResponse;

/**
 * Widget that calls remote methods on the Expanz server.
 * 
 * Required attributes are;
 * 
 * methodName - the name of the remote method to execute
 *
 */
public class ImageUploadButtonEx extends Button implements ExpanzFieldWidget {

	private String fileName;
	private String fieldId;
	private String associatedFieldId;
	private String type = "JPEG";
	private String autoSaveMethod;
	private int quality = 70;
	private float scale = 0.3f;

	/**
	 * Ctor.
	 * 
	 * @param context the activity
	 */
	public ImageUploadButtonEx(Context context) {
		super(context);
		this.setFocusable(true);
		throw new RuntimeException("activity_name attribute must be defined");
	}

	/**
	 * Ctor.
	 * 
	 * @param context the activity
	 * @param attrs values defined in attr.xml
	 */
	public ImageUploadButtonEx(Context context, AttributeSet attrs) {

		super(context, attrs);
		this.setFocusable(true);
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
	public ImageUploadButtonEx(Context context, AttributeSet attrs, int defStyle) {

		super(context, attrs, defStyle);
		this.setFocusable(true);
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
		
		fieldId = a.getString(R.styleable.BaseViewEx_field_id);
		
		if (fieldId == null) {
    		throw new RuntimeException("file attribute must be defined");
        }
		
		a = getContext().obtainStyledAttributes(attrs,R.styleable.ImageCaptureEx);
		
		fileName = a.getString(R.styleable.ImageCaptureEx_file);
		
		if (fileName == null) {
    		fileName = "photo.jpg";
        }
		
		type = a.getString(R.styleable.ImageCaptureEx_type);
		
		if(type == null) {
			type = "JPEG";
		}
		
		quality = a.getInt(R.styleable.ImageCaptureEx_quality, 70);
		
		scale = a.getFloat(R.styleable.ImageCaptureEx_scale, 0.3f);
		
		autoSaveMethod = a.getString(R.styleable.ImageCaptureEx_auto_save_method);
		
		associatedFieldId = a.getString(R.styleable.ImageCaptureEx_assoc_field_id);
            
	}
	 	
	/**
	 * Register OnClickListener that will upload the image to the server
	 */
	private void registerListener() {
		
		setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				
				ContextEx context = (ContextEx)getContext();
				
				ImageDetails details = new ImageDetails();
				details.setField(associatedFieldId);
				details.setFileName(fileName);
				details.setType(type);
				details.setQuality(quality);
				details.setScale(scale);
				details.setAutoSaveMethod(autoSaveMethod);
				
				context.uploadImage(details);

			}

		});
		
	}

	/**
	 * Return the id of the associated field
	 */
	public String getFieldId() {
		return fieldId;
	}

	/**
	 * Required as part of the {@link ExpanzFieldWidget}
	 * but no implementation currently required. 
	 */
	public void setField(FieldResponse field) {
		
	}

}
