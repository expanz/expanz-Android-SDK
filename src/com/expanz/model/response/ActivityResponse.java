package com.expanz.model.response;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;

import com.expanz.ExpanzCommandImpl;
import com.expanz.model.entity.Activities;
import com.expanz.model.request.DataPublicationRequest;


/**
 * Holds state data for the response data from 
 * an activity request.
 * 
 * Forms the Command output object of the {@link ExpanzCommandImpl}
 *
 */
public class ActivityResponse extends ResponseObject {
	
	/**
	 * The handle of the newly created activity
	 */
	private String handle;
	
	/**
	 * The title of the activity
	 */
	private String title;
	
	/**
	 * The fields available from the activity
	 */
	private List<FieldResponse> fields = new ArrayList<FieldResponse>();
	
	/**
	 * Data sets that were returned, i.e. via {@link DataPublicationRequest}s
	 */
	private List<Data> data = new ArrayList<Data>();
	
	/**
	 * Allows launching alternate activities
	 */
	private ActivityRequestResponse activityRequest;
	
	/**
	 * Menu associated with an activity
	 */
	private ContextMenuResponse contextMenu;
	
	
	/**
	 * Add a data set
	 * 
	 * @param dataSet a set of {@link DataRow}s
	 */
	public void addData(Data dataSet) {
		data.add(dataSet);
	}

	/**
	 * Return the activity handle
	 * 
	 * @return handle of the created activity
	 */
	public String getHandle() {
		return handle;
	}

	/**
	 * Set the activity's handle
	 * 
	 * @param handle the activity's handle
	 */
	public void setHandle(String handle) {
		this.handle = handle;
	}

	/**
	 * The title of the activity
	 * 
	 * @return the activity's title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set the title of the activity
	 * 
	 * @param title the activity's title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Field data associated with the activity.
	 * 
	 * @return List of each of the activity's fields
	 */
	public List<FieldResponse> getFields() {
		return fields;
	}

	/**
	 * Data publications associated with the activity request
	 * 
	 * @return a List if each of the data publications
	 */
	public List<Data> getData() {
		return data;
	}

	/**
	 * Return an activity request associated with the current activity
	 * 
	 * TODO can there be multiple activity requests
	 * 
	 * @return an activity request if any available
	 */
	public ActivityRequestResponse getActivityRequest() {
		return activityRequest;
	}

	/**
	 * Set an ActivityRequestResponse associated with this activity
	 * 
	 * @param activityRequest set if available
	 */
	public void setActivityRequest(ActivityRequestResponse activityRequest) {
		this.activityRequest = activityRequest;
	}

	/**
	 * Return the context menu associated with an activity
	 * 
	 * @return the context menu, null if no menu
	 * 
	 */
	public ContextMenuResponse getContextMenu() {
		return contextMenu;
	}

	/**
	 * Set the context menu of the activity
	 * 
	 * @param contextMenu
	 */
	public void setContextMenu(ContextMenuResponse contextMenu) {
		this.contextMenu = contextMenu;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void persist(String payload, Context context) {
		
		ContentValues values = new ContentValues();
		
		values.put(Activities.ActivityEntity.ACTIVITY_HANDLE, handle);
		values.put(Activities.ActivityEntity.PAYLOAD, payload);
		
		uri = context.getContentResolver().insert(Activities.ActivityEntity.CONTENT_URI, values);
		
	}

}
