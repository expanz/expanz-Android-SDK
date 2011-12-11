package com.expanz.model.response;

import android.content.ContentValues;

import com.expanz.ExpanzApplication;
import com.expanz.ExpanzCommand;
import com.expanz.model.entity.Sessions;

/**
 * Encapsulates the Session data
 * 
 * Forms the Command output part of the {@link ExpanzCommand}
 *
 */
public class SessionResponse extends ResponseObject {
	
	/**
	 * The session handle
	 */
	private String sessionHandle;
	
	/**
	 * The menu response
	 */
	private MenuResponse menu;
	
	/**
	 * The UI preferences for the session
	 */
	private UIPreferencesResponse uiPreferences;

	/**
	 * Returns the session's handle (unique id)
	 * 
	 * @return the sessions handle
	 */
	public String getSessionHandle() {
		return sessionHandle;
	}

	/**
	 * Set the session's handle
	 * 
	 * @param sessionHandle the handle (unique id)
	 */
	public void setSessionHandle(String sessionHandle) {
		this.sessionHandle = sessionHandle;
	}

	/**
	 * Return the menu for the session
	 * 
	 * @return the menu
	 */
	public MenuResponse getMenu() {
		return menu;
	}

	/**
	 * Set the menu for the session
	 * 
	 * @param menu the menu
	 */
	public void setMenu(MenuResponse menu) {
		this.menu = menu;
	}

	/**
	 * Returns the UI Preference for the session
	 * 
	 * @return the UI Preferences
	 */
	public UIPreferencesResponse getUiPreferences() {
		return uiPreferences;
	}

	/**
	 * Set the UI Preferences for the session
	 * 
	 * @param uiPreferences the UI Preferences
	 */
	public void setUiPreferences(UIPreferencesResponse uiPreferences) {
		this.uiPreferences = uiPreferences;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void persist(String payload) {
		
		ContentValues values = new ContentValues();
		
		values.put(Sessions.SessionEntity.SESSION_HANDLE, sessionHandle);
		values.put(Sessions.SessionEntity.PAYLOAD, payload);
		
		uri = ExpanzApplication.getInstance().getContentResolver().insert(Sessions.SessionEntity.CONTENT_URI, values);
	}

}
