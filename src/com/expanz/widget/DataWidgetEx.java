package com.expanz.widget;

import com.expanz.model.request.DataPublicationRequest;
import com.expanz.model.response.Data;

public interface DataWidgetEx {
	
	void updateData(Data data);
	
	String getDataId();
	
	DataPublicationRequest toPublication();

}
