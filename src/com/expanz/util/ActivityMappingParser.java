package com.expanz.util;

import android.content.res.XmlResourceParser;

/**
 * Interface for parsing the activity mappings
 */
public interface ActivityMappingParser {
	
	/**
	 * Do the parsing
	 * 
	 * @param parser the XmlResourceParser to parser
	 * @return result containing parsed objects
	 */
	MappingParseResult parse(XmlResourceParser parser);

}
