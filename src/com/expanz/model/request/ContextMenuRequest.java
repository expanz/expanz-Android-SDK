package com.expanz.model.request;

/**
 * 
 * CommandInput object for the com.expanz.ExpanzCommand. 
 * Used for the the ContextMenu element of the Activity service call
 *
 */
public class ContextMenuRequest {
	
	private String contextObject;

	public String getContextObject() {
		return contextObject;
	}

	public void setContextObject(String contextObject) {
		this.contextObject = contextObject;
	}

}
