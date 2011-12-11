package com.expanz.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.expanz.R;
import com.expanz.RemoteImageReader;
import com.expanz.app.data.ListViewable;
import com.expanz.model.response.FieldResponse;

/**
 * Widget that displays an image that is retrieved from a remote server.
 * 
 * Image will be asynchronously downloaded via {@link RemoteImageReader}
 */
public class ImageViewEx extends ImageView implements ExpanzFieldWidget, ListViewable {
	
	private String fieldId;

	/**
	 * Ctor.
	 * 
	 * @param context the activity
	 */
	public ImageViewEx(Context context) {
		super(context);
		throw new RuntimeException("field_id attribute must be defined");
	}

	/**
	 * Ctor.
	 * 
	 * @param context the activity
	 * @param attrs values defined in attr.xml
	 */
	public ImageViewEx(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}

	/**
	 * Ctor.
	 * 
	 * @param context the activity
	 * @param attrs values defined in attr.xml
	 * @param defStyle the definition style
	 */
	public ImageViewEx(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
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
	 * Get the id of the corresponding field
	 */
	public String getFieldId() {
		return fieldId;
	}

	/**
	 * Set the id of the corresponding field
	 * @param fieldId
	 */
	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	/**
	 * Asynchronously retrieve the image via the URL in the {@link FieldResponse}
	 */
	public void setField(FieldResponse field) {
		RemoteImageReader reader = new RemoteImageReader(
				this, field.getUrl());
		reader.execute();
	}


}
