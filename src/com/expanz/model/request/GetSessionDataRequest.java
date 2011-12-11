package com.expanz.model.request;

import com.expanz.model.response.SessionResponse;
import com.expanz.webservice.GetSessionDataHandler;
import com.expanz.webservice.XmlHandler;

/**
 * CommandInput object for the com.expanz.ExpanzCommand. 
 * Used for the the GetSessionData service call
 *
 */
public class GetSessionDataRequest extends RequestObject<SessionResponse> {
	
	/**
	 * Handles parsing SessionResponse XML
	 */
	private static final GetSessionDataHandler handler = new GetSessionDataHandler();
	
	/**
	 * Ctor.
	 * 
	 * @param sessionHandle the session identifier for the request
	 * 
	 */
	public GetSessionDataRequest(String sessionHandle) {
		this.sessionHandle = sessionHandle;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getSpecificXML() {
	
		StringBuilder xml = new StringBuilder();
		
		startElementWithAttributes(xml, "GetSessionData");
		closeSimpleElementWithAttributes(xml);
		
		return xml.toString();
	}

	/**
	 * Used to parse the response of this request. 
	 * 
	 * Association made internally rather that a global 
	 * request to response mapping registry. 
	 * 
	 */
	@Override
	public XmlHandler<SessionResponse> getXmlHandler() {
		return handler;
	}
 
}
