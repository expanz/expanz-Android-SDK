package com.expanz.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;

import com.expanz.ExpanzCommand;
import com.expanz.ServiceCallback;
import com.expanz.app.ActivityEx;
import com.expanz.app.ExpanzConstants;
import com.expanz.model.entity.ImageDetails;
import com.expanz.model.request.ActivityRequest;
import com.expanz.model.request.DeltaRequest;
import com.expanz.model.request.MethodRequest;
import com.expanz.model.response.ActivityResponse;
import com.expanz.model.response.FieldResponse;
import com.expanz.widget.ExpanzFieldWidget;
import com.expanz.widget.ImageViewEx;
import com.google.inject.Inject;

/**
 * A simple utility class for transmitting captured images from a device's
 * inbuilt camera.
 * 
 * This is currently not done async to the UI thread, if latency issues etc are 
 * experienced then this should be refactored to be an async task. 
 * 
 */
public class ImageCaptureAgent implements ImageCapturer {
	
	private ImageDetails imageDeets;
	private String activityHandle;
	private String sessionHandle;
	private Map<String, List<ExpanzFieldWidget>> fieldWidgets;
	private Activity context;

	@Inject ExpanzCommand expanzCommand;
	
	/**
	 * Capture the image via intent and persist to local disk
	 * 
	 * @param imageDeets
	 *            the details for a previously captured image.
	 * @param activityHandle
	 *            the activity the update request is associated with.
	 * @param sessionHandle
	 *            the session the update request is associated with.
	 * @param fieldWidgets
	 *            the fieldWidgets that maybe be updated as a consequence. of
	 *            the request.
	 * @param context
	 *            the activity associated with the capture.
	 * @throws IOException throws exception if unable to create cached image
	 */
	public void capture(ImageDetails imageDeets, String activityHandle,
			String sessionHandle,
			Map<String, List<ExpanzFieldWidget>> fieldWidgets, Activity context) throws IOException {
		this.imageDeets = imageDeets;
		this.activityHandle = activityHandle;
		this.sessionHandle = sessionHandle;
		this.fieldWidgets = fieldWidgets;
		this.context = context; 
		
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		
		File cacheDir = null;
		
		boolean needsCreating = true;
		
		//Use external media if available
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) &&
				!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
			
			cacheDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
					ExpanzConstants.IMAGE_STORE_DIR);
			
			if (!cacheDir.exists()) {
				if (cacheDir.mkdirs()) {
					needsCreating = false;
				}
			} else {
				needsCreating = false;
			}
			
		} 
		
		//Use internal media as a fallback
		if (needsCreating) {

			throw new IOException("uable to write to external storage device "
					+ cacheDir.getAbsolutePath()
					+ ExpanzConstants.IMAGE_STORE_DIR);

		}
		
		File photo = new File(cacheDir, imageDeets.getFileName());
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
	
		imageDeets.setImageUri(Uri.fromFile(photo));

		((Activity)context).startActivityForResult(intent, ActivityEx.TAKE_PICTURE);
		
	}

	/**
	 * Sends the captured image to the server.
	 */
	public void send() {
		
		context.getContentResolver().notifyChange(imageDeets.getImageUri(), null);

		Bitmap bitmap;

		bitmap = decodeFile();

		Matrix matrix = new Matrix();
		matrix.postScale(imageDeets.getScale(), imageDeets.getScale());

		bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);

		ByteArrayOutputStream ostream = new ByteArrayOutputStream();

		bitmap.compress(imageDeets.getCompressFormat(), 100, ostream);

		String base64encoded = Base64.encodeToString(ostream.toByteArray(),
				Base64.DEFAULT);

		try {
			ostream.close();
		} catch (IOException e) {
		}

		ActivityRequest request = new ActivityRequest(activityHandle,
				sessionHandle);

		DeltaRequest deltaRequest = new DeltaRequest(imageDeets.getField(),
				"$longData$");

		if (imageDeets.getAutoSaveMethod() != null) {
			MethodRequest method = new MethodRequest();
			method.setName(imageDeets.getAutoSaveMethod());
		}

		deltaRequest.setEncoding("BASE64");
		deltaRequest.setText(base64encoded);

		request.addDelta(deltaRequest);

		final Bitmap imageRef = bitmap;
		
		expanzCommand.execute(request,
				new ServiceCallback<ActivityResponse>() {

					public void completed(ActivityResponse activity) {

						for (FieldResponse field : activity.getFields()) {

							if (field.getId().equals(imageDeets.getField())) {
								if (field.isValid()) {

									if (field.isValid()) {
										List<ExpanzFieldWidget> widgets = fieldWidgets
												.get(imageDeets.getField());

										if (widgets != null) {

											for (ExpanzFieldWidget widget : widgets) {
												if (widget instanceof ImageViewEx) {

													ImageViewEx imageView = (ImageViewEx) widget;

													if (imageView != null) {
														imageView
																.setImageBitmap(imageRef);
													}

												}

											}

										}

									}

								}

							}

						}

					}
				});

	}

	/**
	 * Decode the image data into a Bitmap Object.
	 */
	private Bitmap decodeFile() {

		try {

			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;

			BitmapFactory.decodeStream(context.getContentResolver()
					.openInputStream(imageDeets.getImageUri()), null, options);
			
			// 600 pixels is used as the desired sample width
			// anything bigger resulted in out of memory exceptions on tested devices
			// may need to make this configurable for users that require higher quality
			// image sampling.
			int sampleSize = options.outWidth / 600;
	
			options = new BitmapFactory.Options();
			
			if(sampleSize > 1) {
				options.inSampleSize = sampleSize;
			}

			return BitmapFactory.decodeStream(context.getContentResolver()
					.openInputStream(imageDeets.getImageUri()), null, options);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
