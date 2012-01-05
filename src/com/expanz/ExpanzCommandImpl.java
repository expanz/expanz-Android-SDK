package com.expanz;

import com.expanz.model.request.RequestObject;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * Implementation of a modified command pattern for interaction with server data.
 * In general use the command pattern can become unsafe as the caller
 * doesn't know extent of the work being done inside the command (i.e. how expensive it is),
 * in this case there is only one implementation so the  usage is pretty obvious thus 
 * it fits well. Also note the command output is returned asynchronously through the callback.
 * 
 * RequestObject - command input
 * ResponseObject - command output (via ServiceCallback)
 *
 */
@Singleton
public class ExpanzCommandImpl implements ExpanzCommand {
	
	@Inject Provider<ServiceAgent> serviceAgentProvider;
	
	/**
	 * Execute the command and return a response via callback
	 * 
	 * @param request
	 * @param callback Asynchronously update UI
	 * @return
	 */
	public void execute(RequestObject request, ServiceCallback callback) {
		
		ServiceAgent agent = serviceAgentProvider.get();
		
		agent.sendRequest(request, callback);
		
	}
	
}
