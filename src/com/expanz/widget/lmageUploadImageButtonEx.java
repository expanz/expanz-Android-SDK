package com.expanz.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;

import com.expanz.R;
import com.expanz.app.ContextEx;
import com.expanz.model.entity.ImageDetails;
import com.expanz.model.response.FieldResponse;

public class lmageUploadImageButtonEx extends ImageButton implements ExpanzFieldWidget {
	
	private String fileName;
	private String fieldId;
	private String associatedFieldId;
	private String type = "JPEG";
	private String autoSaveMethod;
	private int quality = 70;
	private float scale = 0.3f;
	private FieldResponse field;
	
	/**
	 * Ctor.
	 * 
	 * @param context the activity
	 */
	public lmageUploadImageButtonEx(Context context) {
		super(context);
	}

	/**
	 * Ctor.
	 * 
	 * @param context the activity
	 * @param attrs values defined in attr.xml
	 */
	public lmageUploadImageButtonEx(Context context, AttributeSet attrs) {
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
	public lmageUploadImageButtonEx(Context context, AttributeSet attrs, int defStyle) {
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
		
		fieldId = a.getString(R.styleable.BaseViewEx_field_id);
		
		if (fieldId == null) {
    		throw new RuntimeException("file attribute must be defined");
        }
		
		a = getContext().obtainStyledAttributes(attrs,R.styleable.ImageCaptureEx);
		
		fileName = a.getString(R.styleable.ImageCaptureEx_file);
		
		if (fileName == null) {
    		throw new RuntimeException("file attribute must be defined");
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


	public String getFieldId() {
		return fieldId;
	}

	public void setField(FieldResponse field) {
		this.field = field;
	}


}
