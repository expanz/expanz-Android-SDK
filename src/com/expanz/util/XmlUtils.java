package com.expanz.util;

import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/**
 * Simple static class for XML parsers. Prevents the need for creating many
 * instances of XmlPullParserFactory
 */
public class XmlUtils {
	
	private static XmlPullParserFactory sFactory;
	
	/**
	 * Create the factory if necessary and return a new pull parser. 
	 * 
	 * @param input the input stream to be parsed. 
	 * @return a newly created parser
	 * @throws XmlPullParserException if unable to create a parser from the stream
	 */
	public static XmlPullParser newPullParser(InputStream input) throws XmlPullParserException {
        
		if (sFactory == null) {
            sFactory = XmlPullParserFactory.newInstance();
        }
        
        final XmlPullParser parser = sFactory.newPullParser();
        parser.setInput(input, null);
        return parser;
    }

}
