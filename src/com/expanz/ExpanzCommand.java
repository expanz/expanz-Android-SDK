package com.expanz;

import com.expanz.model.request.RequestObject;

public interface ExpanzCommand {
	
	void execute(RequestObject request, ServiceCallback callback);

}
