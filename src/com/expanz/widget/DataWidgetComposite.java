package com.expanz.widget;

import com.expanz.model.request.DataPublicationRequest;

public class DataWidgetComposite {
	
	private String dataId;
	
	private String query;
	
	private String populateMethod;
	
	private String context;
	
	private boolean autoPopulate;
	
	private boolean useThumbnails;
	
	private boolean refresh;

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getPopulateMethod() {
		return populateMethod;
	}

	public void setPopulateMethod(String populateMethod) {
		this.populateMethod = populateMethod;
	}

	public boolean isAutoPopulate() {
		return autoPopulate;
	}

	public void setAutoPopulate(boolean autoPopulate) {
		this.autoPopulate = autoPopulate;
	}
	
	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public boolean isUseThumbnails() {
		return useThumbnails;
	}

	public void setUseThumbnails(boolean useThumbnails) {
		this.useThumbnails = useThumbnails;
	}

	public boolean isRefresh() {
		return refresh;
	}

	public void setRefresh(boolean refresh) {
		this.refresh = refresh;
	}

	public DataPublicationRequest toPublication() {
		
		DataPublicationRequest request = new DataPublicationRequest();
		request.setId(dataId);
		request.setContext(context);
		request.setPopulateMethod(populateMethod);
		request.setUseThumbnails(useThumbnails);
		request.setRefresh(refresh);
		request.setQuery(query);
		request.setAutoPopulate(autoPopulate);
		
		return request;
	}

}
