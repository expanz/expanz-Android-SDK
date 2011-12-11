package com.expanz.app.data;

import android.graphics.Bitmap;
import android.view.View;

import com.expanz.RemoteImageReader;
import com.expanz.model.response.DataCell;
import com.expanz.model.response.DataRow;
import com.expanz.widget.ImageViewEx;

/**
 * This class handles the rendering of ImageViewEx classes in a ListView.
 * 
 * This class needs to be registered in the Application Context (com.expanz.ExpanzApplication or similar).
 * 
 */
public class ImageViewExListViewableHandler implements ListViewableHandler {

	/**
	 * Implementation of the Rendering. Note rendering is done asynchronously.
	 * 
	 * If available a cached image is used temporarily until the actual image is downloaded.  
	 */
	public void handle(View convertView, View rowView, DataRow data,
			int position, long id) {
		
		final ImageViewEx imageView = (ImageViewEx) rowView;
		
		ImageCache.Id cacheId = new ImageCache.Id();
		cacheId.rowId = id;
		cacheId.fieldId = imageView.getFieldId();
			
		Bitmap existingImage = ImageCache.getInstance().get(cacheId);
			
		imageView.setImageBitmap(existingImage);
		
		for(DataCell cell : data.getCells()) {
			
			if(imageView.getFieldId().equals(cell.getFieldId())) {
				RemoteImageReader reader = new RemoteImageReader((ImageViewEx)rowView, cell.getValue());
				reader.setCacheId(cacheId);
				reader.setConvertView(convertView);
				reader.execute();
			}
			
		}
		
	}

}
