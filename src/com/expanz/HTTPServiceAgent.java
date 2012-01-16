package com.expanz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import android.os.AsyncTask;
import android.util.Log;

import com.expanz.app.Config;
import com.expanz.model.request.RequestObject;
import com.expanz.model.response.ResponseObject;
import com.expanz.webservice.XmlHandler;
import com.google.inject.Inject;

/**
 * Implementation of a web service agent. Agent is the client side caller of a 
 * web service. This is a lightweight solution and should probably be replaced with a 
 * more heavyweight implementation that persists service call state for retry etc. 
 *
 */

public class HTTPServiceAgent extends AsyncTask<RequestObject, Void, List<ResponseObject>>
	implements ServiceAgent {
	
	private static final String LOG_TAG = "ServiceAgent ";
	
	private static DefaultHttpClient client;
	
	private ServiceCallback callback;
	
	@Inject 
	ExpanzApplication application;
	
	@Inject 
	Config config;
	
	static {
		
		HttpParams params = new BasicHttpParams();

		// Use generous timeouts for slow mobile networks
		HttpConnectionParams.setConnectionTimeout(params, 20 * 1000);
		HttpConnectionParams.setSoTimeout(params, 20 * 1000);

		HttpConnectionParams.setSocketBufferSize(params, 8192);

		client = new DefaultHttpClient();
	    ClientConnectionManager mgr = client.getConnectionManager();
	   
	    client = new DefaultHttpClient(new ThreadSafeClientConnManager(params, mgr.getSchemeRegistry()), params);
	  
	}
	

	/**
	 * Handles multiple remote request in order. 
	 * 
	 */
	@Override
	protected List<ResponseObject> doInBackground(RequestObject... requests) {
		
		long time = System.currentTimeMillis();
		
		List<ResponseObject> responses = new ArrayList<ResponseObject>();
		
		for(RequestObject<ResponseObject> request : requests) {
			
			request.setConfig(config);
			
			XmlHandler handler = request.getXmlHandler();
			
			if(handler == null) {
				throw new RuntimeException("Unkown XmlHandler for " + request.getClass().getName());
			}
			
			HttpPost post = new HttpPost(request.getUri());
			post.addHeader("Content-Type", "application/xml");
			
			final String requestPayload = request.getPayload();
			
			try {
				StringEntity se = new StringEntity(requestPayload, HTTP.UTF_8);
				Log.d(LOG_TAG + request.getClass().getSimpleName(), requestPayload);
				post.setEntity(se);
			} catch (UnsupportedEncodingException e1) {
				//should never happen as UTF-8 is supported
			}

			ResponseObject response;
			
			InputStream input = null;

			try {
		
				final HttpResponse resp = client.execute(post);
				final int status = resp.getStatusLine().getStatusCode();
				
				if (status != HttpStatus.SC_OK) {
					
					Log.e(LOG_TAG + request.getClass().getSimpleName(), "server unable to service request " + status);
					
					//TODO improve this error handling
					throw new RuntimeException("Unexpected server response "
							+ resp.getStatusLine() + " for "
							+ post.getRequestLine());
				}

				input = resp.getEntity().getContent();
				
				String responsePayload = convertToString(input);
			
				response = handler.parse(responsePayload);
				response.persist(responsePayload, application);
				
				response.getUri();
				response.setRequestTime(time);
				
				Log.d(LOG_TAG + response.getClass().getSimpleName() + "-" + request.getClass().getSimpleName(),
						responsePayload);
				

			} catch (Exception e) {
				response = handler.getTypedInstance();
				response.error("", "", e.getMessage()); 
			} finally {
				if(input != null) {
					try {
						input.close();
					} catch (IOException e) {
						//igonore
					}
				}
			}
			
			responses.add(response);
	
		}
		
		return responses;
		
	}
	
	
	/**
	 * Execute the callback
	 */
	@Override
	protected void onPostExecute(List<ResponseObject> result) {
		
		if(result != null && result.size() > 0 && callback != null) {
			//TODO just assume single response for now
			callback.completed(result.get(0));
		}
	}

	/**
	 * Convert Stream to String for persistence. 
	 * 
	 * @param input stream to stringify
	 * 
	 * @return xml String
	 */
	private String convertToString(InputStream input) {
		
		if (input == null) {
			return "";
		}
		
		BufferedReader r = new BufferedReader(new InputStreamReader(input));
		StringBuilder total = new StringBuilder();
		String line;
		try {
			while ((line = r.readLine()) != null) {
			    total.append(line);
			}
		} catch (IOException e) {
			throw new RuntimeException("Error reading remote server");
		} finally {
			try {
				input.close();
			} catch (IOException e) {
			}
		}
		
		return total.toString();

	}

	public void sendRequest(RequestObject request, ServiceCallback callback) {
		
		this.callback = callback;
		
		this.execute(request);
		
	}

}
