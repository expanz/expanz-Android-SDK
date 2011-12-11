package com.expanz.model.request;

/**
 * CommandInput object for the com.expanz.ExpanzCommand. 
 * Used for the the DataPublication element of the Activity/CreateActivity service call
 *
 */
public class DataPublicationRequest {
	
	/**
	 * The context object class of the the data set
	 */
	private String context;
	
	/**
	 * Execute this populate method
	 */
	private String populateMethod;
	
	/**
	 * Execute this query
	 */
	private String query;
	
	/**
	 * Auto populate the data set
	 */
	private Boolean autoPopulate;
	
	/**
	 * Id for the data set
	 */
	private String id;
	
	/**
	 * Refresh data
	 */
	private Boolean refresh = true;
	
	/**
	 * Return thumbnail images, if available
	 */
	private boolean useThumbnails = false; 
	
	/**
	 * 
	 * Static factory Method
	 * 
	 * @param id identifier for the data set
	 * @param populateMethod execute this method
	 * @param autoPopulate
	 * @return new instance
	 */
	public static DataPublicationRequest forCreateActivity(String id, String populateMethod, boolean autoPopulate) {
		
		DataPublicationRequest request = new DataPublicationRequest();
		
		request.id = id;
		request.populateMethod = populateMethod;
		request.autoPopulate = autoPopulate;
		
		return request;
		
	}
	
	/**
	 * Static Factory Method
	 * 
	 * @param id identifier for the data set
	 * @param populateMethod execute this method
	 * @param context the context of the data set
	 * @return new instance
	 * @return
	 */
	public static DataPublicationRequest forCreateActivityWithContext(String id, String populateMethod, String context) {
		
		DataPublicationRequest request = new DataPublicationRequest();
		
		request.id = id;
		request.populateMethod = populateMethod;
		request.autoPopulate = null;
		request.context = context;
		
		return request;
		
	}

	/**
	 * Returns the context of the data set
	 * 
	 * @return context String else null if no context
	 */
	public String getContext() {
		return context;
	}

	/**
	 * Set the context object of the data set
	 * 
	 * @param context name of the context
	 */
	public void setContext(String context) {
		this.context = context;
	}

	/**
	 * Returns the unique identifier of the data set
	 * 
	 * @return unique id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Set the unique id of the data set
	 * 
	 * @param id unique id for data set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Return state of the refresh
	 * 
	 * @return true if data is to be refreshed
	 */
	public Boolean getRefresh() {
		return refresh;
	}

	/**
	 * Specify whether data should be refreshed
	 * 
	 * @param refresh true to refresh
	 */
	public void setRefresh(Boolean refresh) {
		this.refresh = refresh;
	}

	/**
	 * Returns name of populate method to be executed
	 * 
	 * @return name of the method, null if no method to be called
	 */
	public String getPopulateMethod() {
		return populateMethod;
	}

	/**
	 * Set the name of the populate method
	 * 
	 * @param populateMethod method to be executed
	 */
	public void setPopulateMethod(String populateMethod) {
		this.populateMethod = populateMethod;
	}

	/**
	 * Returns state of auto populate
	 * 
	 * @return true if data should be auto populated
	 */
	public Boolean getAutoPopulate() {
		return autoPopulate;
	}

	/**
	 * Set the state of the data publications auto popluate
	 * 
	 * @param autoPopulate true if you wish to have the data 
	 * returned as part of this request
	 * 
	 */
	public void setAutoPopulate(Boolean autoPopulate) {
		this.autoPopulate = autoPopulate;
	}

	/**
	 * Returns a query name if specified
	 * 
	 * @return name of remote query to be executed
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * Set the name of remote query to be executed
	 * 
	 * @param query name of query to be executed
	 */
	public void setQuery(String query) {
		this.query = query;
	}

	/**
	 * Return Does this data publication require thumbnail images
	 * 
	 * @return true if thumbnails required
	 */
	public boolean isUseThumbnails() {
		return useThumbnails;
	}

	/**
	 * Set Does this data publication require thumbnail images
	 * 
	 * @param useThumbnails true if thumbnails required
	 */
	public void setUseThumbnails(boolean useThumbnails) {
		this.useThumbnails = useThumbnails;
	}

}
