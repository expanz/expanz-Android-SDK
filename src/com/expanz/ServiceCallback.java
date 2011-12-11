package com.expanz;

import com.expanz.model.response.ResponseObject;

/**
 * Allows for the update of the response data from a remote server call to be done on the UI thread
 *
 * @param <T>
 */
public interface ServiceCallback<T extends ResponseObject> {
	
	/**
	 * This will be done on the UI thread so there is no need for 
	 * implementing classes to enforce this. 
	 * 
	 * @param response
	 */
	public void completed(T response);

}
