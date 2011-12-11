package com.expanz.model.request;

import com.expanz.app.Config;
import com.expanz.model.response.ResponseObject;
import com.expanz.webservice.XmlHandler;

/**
 * Base CommandInput class for the com.expanz.ExpanzCommand
 * Provides basic xml formatting etc.
 * 
 * Currently XML specific but could easily support JSON etc via
 * abstracting out a composite format renderer.
 *
 */
public abstract class RequestObject<T extends ResponseObject> {
	
	/**
	 * Root element of the request
	 */
	protected String rootElement;
	
	/**
	 * Session handle of the request
	 */
	protected String sessionHandle;
	
	/**
	 * Get the specific XML for the subclass, i.e. elements inside the ESA stuff
	 * @return
	 */
	abstract protected String getSpecificXML();
	
	/**
	 * The XML parser for the specific subclass
	 * @return
	 */
	abstract public XmlHandler<T> getXmlHandler();
	
	/**
	 * Get the payload (XML) for the specific subclass, i.e. the total assembled XML.
	 * Set rootElement for classes that don't use Exec root Element. 
	 * @return
	 */
	public String getPayload() {
		return getExecPayload(sessionHandle);
	}
	
	/**
	 * Return the URL of the service call, defaults to Exec but should be overridden 
	 * by subclasses that doen't use the Exec URL such as CreateSession.
	 * @return
	 */
	public String getUri() {
		return Config.getInstance().getExpanzExecUrl();
	}
	
	/**
	 * Starts an element with format such as &lt;CreateSesssion&gt;
	 * 
	 * @param builder
	 * @param name
	 */
	protected void startParentElement(StringBuilder builder, String name) {
		builder.append("<");
		builder.append(name);
		builder.append(">");
	}
	
	/**
	 * Starts an element with format such as &lt;CreateSesssion intended for use when 
	 * attributes are to be added
	 * 
	 * @param builder
	 * @param name
	 */
	protected void startElementWithAttributes(StringBuilder builder, String name) {
		builder.append("<");
		builder.append(name);
		builder.append(" ");
	}
	
	/**
	 * Close an element that has attributes, 
	 * i.e. existing &lt;CreateSesssion blah="123" will add &gt; to the end
	 * @param builder
	 */
	protected void closeParentElementWithAttributes(StringBuilder builder) {
		builder.append(">");
	}
	
	/**
	 * Close an element that has attributes, but no child elements
	 * i.e. existing &lt;CreateSesssion blah="123" will add /&gt; to the end
	 * @param builder
	 */
	protected void closeSimpleElementWithAttributes(StringBuilder builder) {
		builder.append("/>");
	}
	
	/**
	 * Close an element that has child elements, 
	 * i.e. existing &lt;CreateSesssion blah="123" will add /&gt; to the end
	 * @param builder
	 */
	protected void closeParentElement(StringBuilder builder, String name) {
		builder.append("</");
		builder.append(name);
		builder.append(">");
	}
	
	/**
	 * Add an attribute of type boolean, maps to value of 1 (true) or 0 (false)
	 * 
	 * @param builder
	 * @param attribute
	 * @param value
	 */
	protected void addBooleanAttribute(StringBuilder builder, String attribute, Boolean value) {
		
		if(value != null) {
			builder.append(attribute);
			builder.append("=\"");
			builder.append(value ? "1" : "0");
			builder.append("\" ");
		}
	
	}
	
	/**
	 * Add an attribute of type boolean, maps to value of 1 (true) or 0 (false)
	 * 
	 * @param builder
	 * @param attribute
	 * @param value
	 */
	protected void addStringAttribute(StringBuilder builder, String attribute, String value) {
		
		if(value != null && value.trim().length() > 0) {
			builder.append(attribute);
			builder.append("=\"");
			builder.append(value);
			builder.append("\" ");
		}
	
	}
	
	/**
	 * Add text to an element etc
	 * 
	 * @param builder
	 * @param text
	 */
	protected void addText(StringBuilder builder, String text) {
		
		if(text != null) {
			builder.append(text);
		}
	
	}
	
	/**
	 * Return the total payload for the service call
	 * 
	 * @param sessionHandle
	 * @return
	 */
	protected String getExecPayload(String sessionHandle) {
		
		if(rootElement == null) {
			rootElement = "ExecX";
		}
		
		StringBuilder xml = new StringBuilder();
		
		startElementWithAttributes(xml, rootElement); //e.g CreateSessionX or ExecX
		addStringAttribute(xml, "xmlns", Config.getInstance().getExpanzNamespace());
		closeParentElementWithAttributes(xml);
		
		startParentElement(xml, "xml");
		startParentElement(xml, "ESA");
		
		xml.append(getSpecificXML());

		closeParentElement(xml, "ESA");
		closeParentElement(xml, "xml");
		
		if(sessionHandle != null) {
		
			startParentElement(xml, "sessionHandle");
		
			addText(xml, sessionHandle);
		
			closeParentElement(xml, "sessionHandle");

		}
		
		closeParentElement(xml, rootElement);
		
		return xml.toString();
		
	}

}
