package com.expanz;

import com.expanz.model.request.RequestObject;

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
public class ExpanzCommand {
	
	private static ExpanzCommand instance;

	
	/**
	 * Singleton constructor
	 */
	private ExpanzCommand() {
	}

	/**
	 * Get a reference to the singleton object
	 * 
	 * @return the singleton object
	 */
	public static ExpanzCommand getInstance() {

		if (instance == null) {
			instance = new ExpanzCommand();
		}

		return instance;
	}
	
	/**
	 * Execute the command and return a response via callback
	 * 
	 * @param request
	 * @param callback Asynchronously update UI
	 * @return
	 */
	public void execute(RequestObject request, ServiceCallback callback) {
		
		ServiceAgent agent = new ServiceAgent(callback);
		
		agent.execute(request);
		
	}
	
}
