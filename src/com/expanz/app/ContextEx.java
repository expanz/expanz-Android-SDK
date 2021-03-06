package com.expanz.app;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.expanz.ExpanzCommand;
import com.expanz.model.Message;
import com.expanz.model.entity.ImageDetails;
import com.expanz.model.response.ActivityResponse;
import com.expanz.model.response.ContextMenuResponse;
import com.expanz.util.ActivityMappingHolder;
import com.expanz.widget.ExpanzFieldWidget;

/**
 * A common interface that is to be implemented by any Expanz 'Android' activity
 * that will allow child widgets to call common methods
 *
 */
public interface ContextEx {
	
	/**
	 * The activity Handle for the current context.
	 */
	String getActivityHandle();

	/**
	 * The session Handle for the current context.
	 */
	String getSessionHandle();
	
	/**
	 * Hack for pre Android 3.0 bug
	 */
	void grabFocusHack();
	
	/**
	 * Allow the keyb oard to be hidden
	 */
	void hideKeyboard();
	
	/**
	 * Allows an image to be uploaded to the server
	 */
	void uploadImage(ImageDetails details);
	
	/**
	 * The ExpanzFieldWidget for the current activity
	 */
	Map<String, List<ExpanzFieldWidget>> getFieldWidgets();
	
	/**
	 * Display error messages etc using the current message state of the activity. 
	 */
	void displayMessages(Collection<Message> messages);
	
	/**
	 * Show a progress Dialog
	 * 
	 * @param message dialog test
	 */
	void showProgress(String message);

	/**
	 * Stop showing a progress dialog
	 */
	void hideProgress();
	
	/**
	 * Used mostly so widgets can get access to mappings
	 */
	ActivityMappingHolder getMappingHolder();
	
	/**
	 * Used mostly so widgets can get access to commands
	 */
	ExpanzCommand getCommand();
	
	/**
	 * Update the state of widgets on the activity
	 * that are contained in the response
	 * 
	 * @param response the activity response
	 */
	void initFields(ActivityResponse response);
	
	/**
	 * Set the current menu Response
	 * 
	 * @param contextMenuResponse
	 */
	//void setCurrentContextMenu(ContextMenuResponse contextMenuResponse);
	
}
