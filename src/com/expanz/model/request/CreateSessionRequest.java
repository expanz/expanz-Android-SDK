package com.expanz.model.request;

import com.expanz.app.Config;
import com.expanz.model.response.SessionResponse;
import com.expanz.webservice.CreateSessionHandler;
import com.expanz.webservice.XmlHandler;
import com.google.inject.Inject;

/**
 * CommandInput object for the com.expanz.ExpanzCommand. 
 * Used for the the CreateSession service call
 *
 */
public class CreateSessionRequest extends RequestObject<SessionResponse> {
	
	/**
	 * Handle parsing response XML
	 */
	private static final CreateSessionHandler handler = new CreateSessionHandler();
	
	/**
	 * The expanz application site
	 */
	private String appSite;
	
	/**
	 * The expanz auth domain
	 */
	private String authDomain;
	
	/**
	 * The expanz authentication mode
	 */
	private String authenticationMode;
	
	/**
	 * The expanz client type
	 */
	private String clientType;
	
	/**
	 * The expanz client version
	 */
	private String clientVersion;
	
	/**
	 * the expanz password
	 */
	private String password;
	
	/**
	 * The expanz schema version
	 */
	private String schemaVersion;
	
	/**
	 * The expanz station
	 */
	private String station;
	
	/**
	 * The expanz server timezone
	 */
	private String timeZone;
	
	/**
	 * The expanz user
	 */
	private String user;
	
	/**
	 * Use defaults
	 */
	private boolean defaultsSet;
	

	
	/**
	 * Ctor.
	 * 
	 * @param user the user name 
	 * @param password the password for the user
	 */
	public CreateSessionRequest(Config config, String user, String password) {
		this.config = config;
		this.user = user;
		this.password = password;
		this.rootElement = "CreateSessionX";
	}
	
	/**
	 * Set the site of the session request
	 * 
	 * @param appSite the name of the site
	 */
	public void setAppSite(String appSite) {
		this.appSite = appSite;
	}

	/**
	 * Set the auth domain of the session request
	 * 
	 * @param authDomain the name of the auth domain
	 */
	public void setAuthDomain(String authDomain) {
		this.authDomain = authDomain;
	}

	/**
	 * Set the auth mode of the session request
	 * 
	 * @param authenticationMode the authentication mode, e.g. Primary
	 */
	public void setAuthenticationMode(String authenticationMode) {
		this.authenticationMode = authenticationMode;
	}

	/**
	 * Set the type of the client, e.g. android
	 * 
	 * @param clientType the type of the client
	 */
	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	/**
	 * Set the version of the client, e.g. 1.0
	 * 
	 * @param clientVersion the version of the client
	 */
	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}

	/**
	 * Set the password for the user, Note not end user, rather expanz user
	 * 
	 * @param password the user's password
	 * 
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Set the schema version, e.g. 2.0
	 * 
	 * @param schemaVersion the version of the expanz schema
	 */
	public void setSchemaVersion(String schemaVersion) {
		this.schemaVersion = schemaVersion;
	}

	/**
	 * Set the station for the session.
	 * 
	 * @param station the name of the station
	 */
	public void setStation(String station) {
		this.station = station;
	}

	/**
	 * Set the timezone of the session.
	 * 
	 * @param timeZone the name of the timezone
	 */
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	/**
	 * Set the user name, Note not end user, rather expanz user
	 * 
	 * @param user the name of the user
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * Sets the defaultSet value of the session to either true or false
	 * 
	 * @param defaultsSet true to use default set
	 */
	public void setDefaultsSet(boolean defaultsSet) {
		this.defaultsSet = defaultsSet;
	}

	/**
	 * Returns the remote URI for the create session request. 
	 */
	@Override
	public String getUri() {
		return config.getExpanzSessionUrl();
	}

	/**
	 * Get the xml that will be used to create a session, via the POX HTTP service
	 */
	@Override
	protected String getSpecificXML() {
		
		setDefaults();
		
		StringBuilder xml = new StringBuilder();
		
		startElementWithAttributes(xml, "CreateSession");
		addStringAttribute(xml, "appSite", appSite);
		addStringAttribute(xml, "authDomain", authDomain);
		addStringAttribute(xml, "authenticationMode", authenticationMode);
		addStringAttribute(xml, "clientType", clientType);
		addStringAttribute(xml, "clientVersion", clientVersion);
		addStringAttribute(xml, "password", password);
		addStringAttribute(xml, "schemaVersion", schemaVersion);
		addStringAttribute(xml, "TimeZone", timeZone);
		addStringAttribute(xml, "station", station);
		addStringAttribute(xml, "user", user);
		
		closeSimpleElementWithAttributes(xml);	
		
		return xml.toString();
	}

	/**
	 * Get the payload (entire xml request)
	 */
	@Override
	public String getPayload() {
		return getExecPayload(null);
	}
	
	/**
	 * load the default values from the config
	 */
	private void setDefaults() {
		
		if(defaultsSet) {
			return; 
		}
		
		appSite = config.getExpanzSite();
		authDomain = config.getExpanzAuthDomain();
		authenticationMode = config.getExpanzAuthMode();
		clientType = config.getExpanzClientType();
		clientVersion = config.getExpanzClientVersion();
		schemaVersion = config.getExpanzSchemaVersion();
		timeZone = config.getExpanzTimezone();
		station = config.getExpanzStation();
		
		defaultsSet = true;
		
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
