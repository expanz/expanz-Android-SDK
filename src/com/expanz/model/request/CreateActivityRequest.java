package com.expanz.model.request;

import java.util.ArrayList;
import java.util.List;

import com.expanz.model.response.ActivityResponse;
import com.expanz.webservice.CreateActivityHandler;
import com.expanz.webservice.XmlHandler;

/**
 * CommandInput object for the com.expanz.ExpanzCommand. 
 * Used for the the CreateActivity service call
 *
 */
public class CreateActivityRequest extends RequestObject<ActivityResponse> {
	
	/**
	 * Name of expanz activity to create
	 */
	private String name;
	
	/**
	 * Create activity for this context object instance
	 */
	private String initialKey;
	
	/**
	 * Style of the activity, e.g. Browse
	 */
	private String style;
	
	/**
	 * Only bring back specified fields
	 */
	private Boolean suppressFields = true;
	
	/**
	 * List of data sets to return
	 */
	private List<DataPublicationRequest> publications = new ArrayList<DataPublicationRequest>();
	
	/**
	 * List of media fields
	 */
	private List<String> mediaResourceFields = new ArrayList<String>();
	
	/**
	 * Only bring back these fields
	 */
	private List<String> fields = new ArrayList<String>();
	
	/**
	 * Handle parsing response XML
	 */
	private static final CreateActivityHandler handler = new CreateActivityHandler();
	
	/**
	 * Static factory method for the creation of a CreateActivityRequest with the following params
	 * @param name the name of the activity
	 * @param sessionHandle the unique identifier for the session
	 * @return the statically created CreateActivityRequest
	 */
	public static CreateActivityRequest createWithName(String name, String sessionHandle) {
		
		CreateActivityRequest request = new CreateActivityRequest();
		
		request.name = name;
		request.sessionHandle = sessionHandle;
		
		return request;
	}
	
	/**
	 * Static factory method for the creation of a CreateActivityRequest with the following params
	 * @param name the name of the activity
	 * @param sessionHandle the unique identifier for the session
	 * @param style the style of the activity, e.g. 'Browse'
	 * @return the statically created CreateActivityRequest
	 */
	public static CreateActivityRequest createWithNameAndStyle(String name, String style, String sessionHandle) {
		
		CreateActivityRequest request = new CreateActivityRequest();
		
		request.name = name;
		request.sessionHandle = sessionHandle;
		request.style = style;
		
		return request;
	}
	
	/**
	 * Static factory method for the creation of a CreateActivityRequest with the following params
	 * @param name the name of the activity
	 * @param sessionHandle the unique identifier for the session
	 * @param style the style of the activity, e.g. 'Browse'
	 * @param key the key of the activity, e.g. 10
	 * @return the statically created CreateActivityRequest
	 */
	public static CreateActivityRequest createWithNameStyleAndKey(String name, String style, String key, String sessionHandle) {
		
		CreateActivityRequest request = new CreateActivityRequest();
		
		request.name = name;
		request.sessionHandle = sessionHandle;
		request.style = style;
		request.initialKey = key;
		
		return request;
	}
	
	/**
	 * Add media resource field to identifier, fields such as images 
	 * 
	 * @param mediaFields
	 */
	public void addMediaResourceFields(List<String> mediaFields) {
		mediaResourceFields.addAll(mediaFields);
	}
	
	/**
	 * Add a data publication to the activity
	 * @param publication the publication to retireve data for
	 */
	public void addPublication(DataPublicationRequest publication) {
		if(publication != null) {
			publications.add(publication);
		}
	}
	
	/**
	 * Add a field to limit the fields returned by the activity
	 * 
	 * @param field
	 */
	public void addField(String field) {
		fields.add(field);
	}

	/**
	 * Returns the name of the activity
	 * 
	 * @return the activity's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * The initial key, i.e. the data item of interest
	 * 
	 * @return the value of the items' key
	 */
	public String getInitialKey() {
		return initialKey;
	}

	/**
	 * Get the style of the activity, e.g. Browse
	 * 
	 * @return the style 
	 */
	public String getStyle() {
		return style;
	}

	/**
	 * Returns the unique session identifier for the activity
	 * 
	 * @return the session handle string
	 */
	public String getSessionHandle() {
		return sessionHandle;
	}

	/**
	 * Returns a boolean indication of this activity should suppress fields.
	 * 
	 * @return true if fields are to be suppressed
	 * 
	 */
	public Boolean getSuppressFields() {
		return suppressFields;
	}

	/**
	 * Set the name of the activity, as defined on the server side
	 * 
	 * @param name the activity's name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Set the item of interest, if any.
	 * 
	 * @param initialKey item of interest in the data set, e.g. 10
	 */
	public void setInitialKey(String initialKey) {
		this.initialKey = initialKey;
	}

	/**
	 * Set the style of the activity, e.g. Browse
	 * 
	 * @param style the value of the style
	 */
	public void setStyle(String style) {
		this.style = style;
	}

	/**
	 * Set to true if you wish field to be suppressed. 
	 * Then unmask individual fields.
	 * 
	 * @param suppressFields
	 */
	public void setSuppressFields(Boolean suppressFields) {
		this.suppressFields = suppressFields;
	}

	/**
	 * Returns XML that will be used to create an activity
	 * via the POX HTTP interface
	 */
	@Override
	protected String getSpecificXML() {
		
		StringBuilder xml = new StringBuilder();
		
		startElementWithAttributes(xml, "CreateActivity");
		addStringAttribute(xml, "name", name);
		addStringAttribute(xml, "initialKey", initialKey);
		addStringAttribute(xml, "style", style);
		addBooleanAttribute(xml, "suppressFields", suppressFields);
		closeParentElementWithAttributes(xml);
		addFields(xml);
		addPublications(xml);
		addMediaFields(xml);
		closeParentElement(xml, "CreateActivity");	
		
		return xml.toString();
	}
	
	/**
	 * Add the media field elements
	 * 
	 * @param xml the buffer to add the new element
	 */
	private void addMediaFields(StringBuilder xml) {
		for(String mediaField : mediaResourceFields) {
			startElementWithAttributes(xml, "Field");
			addStringAttribute(xml, "id", mediaField);
			addStringAttribute(xml, "Publish", "URL");
			closeSimpleElementWithAttributes(xml);
		}
	}
	
	/**
	 * Add the field elements, fields should only be added
	 * if they are to be unmasked
	 * 
	 * @param xml the buffer to add the new element
	 */
	private void addFields(StringBuilder xml) {
		for(String field : fields) {
			startElementWithAttributes(xml, "Field");
			addStringAttribute(xml, "id", field);
			addStringAttribute(xml, "masked", "0");
			closeSimpleElementWithAttributes(xml);
		}
	}
	
	/**
	 * Add the data publication elements
	 * 
	 * @param xml the buffer to add the new element
	 */
	private void addPublications(StringBuilder xml) {
		for(DataPublicationRequest publication : publications) {
			
			startElementWithAttributes(xml, "DataPublication");
			addStringAttribute(xml, "id", publication.getId());
			addStringAttribute(xml, "query", publication.getQuery());
			addStringAttribute(xml, "populateMethod", publication.getPopulateMethod());
			addStringAttribute(xml, "contextObject", publication.getContext());
			addBooleanAttribute(xml, "autoPopulate", publication.getAutoPopulate());
			//TODO this should take a boolean like other booleans (0 or 1)
			addStringAttribute(xml, "useThumbNailImages", publication.isUseThumbnails() ? "true" : "false");
			closeSimpleElementWithAttributes(xml);
			
		}
	}

	/**
	 * Used to parse the response of this request. 
	 * 
	 * Association made internally rather that a global 
	 * request to response mapping registry. 
	 * 
	 */
	@Override
	public XmlHandler<ActivityResponse> getXmlHandler() {
		return handler;
	}

}
