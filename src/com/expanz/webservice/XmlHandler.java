package com.expanz.webservice;

import org.xmlpull.v1.XmlPullParser;

import com.expanz.model.response.ResponseObject;

/**
 * Interface that defines common methods for xml responses.
 * 
 * Note xml response is the xml returned from the expanz server
 *
 * @param <T>
 */
public interface XmlHandler<T extends ResponseObject> {
	
	/**
	 * Parse the supplied valid xml string
	 * @param xml the string to parse
	 * 
	 * @return the response object of the relative type
	 */
	T parse(String xml);
	
	/**
	 * Parse the supplied valid XmlPullParser
	 * @param parser the parser to parse
	 * 
	 * @return the response object of the relative type
	 */
	T parse(XmlPullParser parser);
	
	/**
	 * Allows caller to instantiate a Response object
	 * of the appropriate type
	 * 
	 * @return the new instance
	 */
	T getTypedInstance();

}
