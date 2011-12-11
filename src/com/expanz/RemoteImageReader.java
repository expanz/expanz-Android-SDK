package com.expanz;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import com.expanz.app.data.ImageCache;

/**
 * Asynchronously read blob data from remote server, server does not have to be an Expanz server.
 * This class ensures updates to the View is done on the UI thread.
 * 
 * Each Image requires it's own RemoteImageReader
 *
 */
public class RemoteImageReader extends AsyncTask<Void, Void, Bitmap> {
	
	private ImageView image;
	private String url;
	private ImageCache.Id cacheId;
	private View convertView;
	
	private static DefaultHttpClient client;
	
	static {
		
		HttpParams params = new BasicHttpParams();

		// Use generous timeouts for slow mobile networks
		//TODO allow this to be configurable through strings.xml
		HttpConnectionParams.setConnectionTimeout(params, 20 * 1000);
		HttpConnectionParams.setSoTimeout(params, 20 * 1000);

		HttpConnectionParams.setSocketBufferSize(params, 8192);

		client = new DefaultHttpClient();
	    ClientConnectionManager mgr = client.getConnectionManager();
	   
	    client = new DefaultHttpClient(new ThreadSafeClientConnManager(params, mgr.getSchemeRegistry()), params);
	  
	}

	/**
	 * Constructor
	 * 
	 * @param image the view to update with the remote image
	 * @param url the url of the remote image
	 */
	public RemoteImageReader(ImageView image, String url) {
		this.image = image;
		this.url = url;
	}

	/**
	 * Set this if the image is to be stored in the image cache, e.g. for ListViews
	 * 
	 * Note: we are not caching a list's convertView as this is error prone, we are
	 * just caching the images contained in the convertView.
	 * 
	 * @param cacheId
	 */
	public void setCacheId(ImageCache.Id cacheId) {
		this.cacheId = cacheId;
	}
	
	/**
	 * Work to be done on the background thread
	 */
	@Override
	protected Bitmap doInBackground(Void... arg0) {
		
		if(url == null) {
			return null;
		}
		
		HttpGet get = new HttpGet(url);
		
		Bitmap image = null;
		InputStream input = null;

		try {

			final HttpResponse resp = client.execute(get);
			final int status = resp.getStatusLine().getStatusCode();

			if (status != HttpStatus.SC_OK) {
				throw new RuntimeException("Unexpected server response "
						+ resp.getStatusLine() + " for " + get.getRequestLine());
			}

			input = resp.getEntity().getContent();
			
			image = BitmapFactory.decodeStream(input);
			
			if(cacheId != null) {
				ImageCache.getInstance().add(cacheId, image);
			}

			
		} catch (Exception e) {
			//TODO should we allow users to display a stock image or should server always be king?
			e.printStackTrace();
		} finally {
			if(input != null) {
				try {
					input.close();
				} catch (IOException e) {
					//igonore
				}
			}
		}
		
		return image;
		
	}

	
	/**
	 * Update the ImageView with the blob data from the server.
	 */
	@Override
	protected void onPostExecute(Bitmap result) {
		if(result != null) {
			
			boolean set = true;
			
			if(convertView != null && cacheId != null && convertView.getTag() != null) {
				
				//there is a tiny window where this may cause an image not to render
				//TODO alternate solution needed
				if(!convertView.getTag().equals(cacheId.rowId)) {
					set = false;
				}

			}
			
			if(set) {
				image.setImageBitmap(result);
			}
		}
		convertView = null; 
	}

	/**
	 * Keep a reference to the convertView associated with this rendered as the convertView 
	 * is merely a reference to a currently visible view. Hence don't update the image if 
	 * the reference has changed. 
	 * 
	 * 
	 * @param convertView
	 */
	public void setConvertView(View convertView) {
		this.convertView = convertView;
	}

	
	
}
