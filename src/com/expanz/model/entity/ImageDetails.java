package com.expanz.model.entity;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * This class contains the meta data of an 
 * image stored on local disk.  
 * 
 * It allows for a more efficient way of accessing 
 * images between activities rather than passing image
 * data in a parcelable via an intent etc. 
 *
 */
public class ImageDetails {

	/**
	 * Local URI of the image
	 */
	private Uri imageUri;

	/**
	 * Filename of the image
	 */
	private String fileName;

	/**
	 * Associated field of the image
	 */
	private String field;

	/**
	 * Type of the image, e.g JPG
	 */
	private String type;

	/**
	 * Allows method to be called after upload
	 */
	private String autoSaveMethod;

	/**
	 * Converted image to this quality
	 */
	private int quality;

	/**
	 * Scale image by this factor
	 */
	private float scale;
	
	/**
	 * Ctor.
	 */
	public ImageDetails() {}

	/**
	 * Ctor.
	 * 
	 * @param fileName name of the image
	 * @param field the assoc field id
	 * @param type type of the image, e.g. JPG
	 * @param autoSaveMethod called immediately after upload
	 * @param quality convert to this quality
	 * @param scale scale to this size
	 */
	public ImageDetails(String fileName, String field,
			String type, String autoSaveMethod, int quality, float scale) {
		super();
		this.fileName = fileName;
		this.field = field;
		this.type = type;
		this.autoSaveMethod = autoSaveMethod;
		this.quality = quality;
		this.scale = scale;
	}

	/**
	 * Return the local URI of the image
	 * @return
	 */
	public Uri getImageUri() {
		return imageUri;
	}

	/**
	 * Set the local URI of the image
	 * @param imageUri
	 */
	public void setImageUri(Uri imageUri) {
		this.imageUri = imageUri;
	}

	/**
	 * Get the filename
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Set the filename
	 * 
	 * @param fileName name of file
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Get the id of the associated field
	 * 
	 * @return field id
	 */
	public String getField() {
		return field;
	}

	/**
	 * Set the id of associated field
	 * 
	 * @param field the field id
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * Get the type of the image, e.g JPG
	 * 
	 * @return image type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Set the type of the image
	 * 
	 * @param type the image type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Get the auto save method
	 * 
	 * @return null if none, else auto save method name
	 */
	public String getAutoSaveMethod() {
		return autoSaveMethod;
	}

	/**
	 * If automatically saving of image after upload
	 * is required then specify the name here
	 * 
	 * @param autoSaveMethod name of method to call after upload
	 */
	public void setAutoSaveMethod(String autoSaveMethod) {
		this.autoSaveMethod = autoSaveMethod;
	}

	/**
	 * Return the converted quality of the image
	 * 
	 * @return image quality value
	 */
	public int getQuality() {
		return quality;
	}

	/**
	 * Set the quality of the image
	 * 
	 * @param quality image will be conveted to this quality
	 */
	public void setQuality(int quality) {
		this.quality = quality;
	}

	/**
	 * Get the image scale size
	 * 
	 * @return scale size
	 */
	public float getScale() {
		return scale;
	}

	/**
	 * Set the image scale size
	 * 
	 * @param scale
	 */
	public void setScale(float scale) {
		this.scale = scale;
	}

	/**
	 * Get the format of the image
	 * 
	 * @return format of the image, defaults to JPEG
	 */
	public Bitmap.CompressFormat getCompressFormat() {
		if (type != null) {
			return Bitmap.CompressFormat.valueOf(type);
		} else {
			return Bitmap.CompressFormat.JPEG;
		}

	}

}