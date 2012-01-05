package com.expanz;

import com.expanz.model.request.RequestObject;

public interface ServiceAgent {
	
	void sendRequest(RequestObject request, ServiceCallback callback);

}
