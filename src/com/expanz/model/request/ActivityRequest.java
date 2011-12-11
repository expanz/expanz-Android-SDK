package com.expanz.model.request;

import java.util.ArrayList;
import java.util.List;

import com.expanz.model.response.ActivityResponse;
import com.expanz.webservice.ActivityHandler;
import com.expanz.webservice.XmlHandler;

/**
 * CommandInput object for the com.expanz.ExpanzCommand. 
 * Used for the the Activity service call
 *
 */
public class ActivityRequest extends RequestObject<ActivityResponse> {
	
	/**
	 * Handle for the activity
	 */
	private String activityHandle;
	
	/**
	 * Call menu item
	 */
	private MenuActionRequest menuAction;
	
	/**
	 * Context object of the request
	 */
	private ContextRequest context;
	
	/**
	 * Call methods
	 */
	private List<MethodRequest> methods = new ArrayList<MethodRequest>();
	
	/**
	 * Specify required fields
	 */
	private List<FieldRequest> fields = new ArrayList<FieldRequest>();
	
	/**
	 * Set field values
	 */
	private List<DeltaRequest> deltas = new ArrayList<DeltaRequest>();
	
	/**
	 * Specify data sets
	 */
	private List<DataPublicationRequest> publications = new ArrayList<DataPublicationRequest>();
	
	/**
	 * Handle activity response XML
	 */
	private static ActivityHandler handler = new ActivityHandler();
	
	/**
	 * Ctor.
	 * 
	 * @param activityHandle unique identifier for the activity
	 * @param sessionHandle unique identifier for the session
	 */
	public ActivityRequest(String activityHandle, String sessionHandle) {
		this.activityHandle = activityHandle;
		this.sessionHandle = sessionHandle;
	}
	
	/**
	 * Adds a method to the request
	 * 
	 * @param method the method to add
	 */
	public void addMethod(MethodRequest method) {
		methods.add(method);
	}
	
	/**
	 * Adds a field to the request.
	 * 
	 * @param field the field to add
	 */
	public void addField(FieldRequest field) {
		fields.add(field);
	}
	
	/**
	 * Adds a delta for a field, i.e. updated value
	 * 
	 * @param delta the field value
	 */
	public void addDelta(DeltaRequest delta) {
		deltas.add(delta);
	}
	
	/**
	 * Adds a data publication to the request
	 *  
	 * @param publication
	 */
	public void addPublication(DataPublicationRequest publication) {
		publications.add(publication);
	}

	/**
	 * Get the Menu Action for this request
	 * 
	 * @return the menu action request of this activity request
	 */
	public MenuActionRequest getMenuAction() {
		return menuAction;
	}

	/**
	 * Set the menu action request for this activity request
	 * 
	 * @param menuAction the menu action of this request
	 */
	public void setMenuAction(MenuActionRequest menuAction) {
		this.menuAction = menuAction;
	}

	/**
	 * Gets the context of this activity, 
	 * i.e. what specific entity in a set/list etc. 
	 * 
	 * @return the context object of this request
	 */
	public ContextRequest getContext() {
		return context;
	}

	/**
	 * Set the context of the request, i.e. which data item
	 * are we interested in.
	 * 
	 * @param context the context object for this request
	 */
	public void setContext(ContextRequest context) {
		this.context = context;
	}

	/**
	 * Add the xml for the entire activity request
	 */
	@Override
	protected String getSpecificXML() {
		
		StringBuilder xml = new StringBuilder();
		
		startElementWithAttributes(xml, "Activity");
		addStringAttribute(xml, "activityHandle", activityHandle);
		closeParentElementWithAttributes(xml);
		
		addContext(xml);
		addMenuAction(xml);
		addMethods(xml);
		addDeltas(xml);
		addPublications(xml);
		addFields(xml);
		
		closeParentElement(xml, "Activity");
		
		return xml.toString();
	}
	
	/**
	 * Add the context element
	 * 
	 * @param xml the buffer to add the new element
	 */
	private void addContext(StringBuilder xml) {
		
		if(context != null) {
			startElementWithAttributes(xml, "Context");
			addStringAttribute(xml, "id", context.getId());
			addStringAttribute(xml, "Type", context.getType());
			addStringAttribute(xml, "contextObject", context.getContext());
			closeSimpleElementWithAttributes(xml);
		}
		
	}

	/**
	 * Add the menu action element
	 * 
	 * @param xml the buffer to add the new element
	 */
	private void addMenuAction(StringBuilder xml) {
		if(menuAction != null) {
			startElementWithAttributes(xml, "MenuAction");
			addBooleanAttribute(xml, "defaultAction", menuAction.getDefaultAction());
			addStringAttribute(xml, "contextObject", menuAction.getContext());
			addStringAttribute(xml, "action", menuAction.getAction());
			closeSimpleElementWithAttributes(xml);
		}
	}
	
	/**
	 * Add the method elements
	 * 
	 * @param xml the buffer to add the new element
	 */
	private void addMethods(StringBuilder xml) {
		
		for(MethodRequest method : methods) {
			startElementWithAttributes(xml, "Method");
			addStringAttribute(xml, "name", method.getName());
			addStringAttribute(xml, "contextObject", method.getContextObject());
			closeSimpleElementWithAttributes(xml);
		}
		
	}
	
	/**
	 * Add the field elements
	 * 
	 * @param xml the buffer to add the new element
	 */
	private void addFields(StringBuilder xml) {
		for(FieldRequest field : fields) {
			startElementWithAttributes(xml, "Delta");
			addStringAttribute(xml, "id", field.getId());
			addBooleanAttribute(xml, "masked", field.getMasked());
			closeSimpleElementWithAttributes(xml);
		}
	}
	
	/**
	 * Add the delta elements
	 * 
	 * @param xml the buffer to add the new element
	 */
	private void addDeltas(StringBuilder xml) {
		
		for(DeltaRequest delta : deltas) {
			startElementWithAttributes(xml, "Delta");
			addStringAttribute(xml, "id", delta.getId());
			addStringAttribute(xml, "value", delta.getValue());
			addStringAttribute(xml, "encoding", delta.getEncoding());
			
			if(delta.getText() != null) {
				closeParentElementWithAttributes(xml);
				addText(xml, delta.getText());
				closeParentElement(xml, "Delta");
			} else {
				closeSimpleElementWithAttributes(xml);
			}
			
		}
		
	}

	/**
	 * Add the data publication elements
	 * 
	 * @param xml the buffer to add the new element
	 */
	private void addPublications(StringBuilder xml) {
		for (DataPublicationRequest publication : publications) {

			startElementWithAttributes(xml, "DataPublication");
			addStringAttribute(xml, "query", publication.getQuery());
			addStringAttribute(xml, "id", publication.getId());
			addStringAttribute(xml, "populateMethod",
					publication.getPopulateMethod());
			addStringAttribute(xml, "contextObject", publication.getContext());
			addBooleanAttribute(xml, "autoPopulate",
					publication.getAutoPopulate());
			//TODO this should take a boolean like other booleans
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
