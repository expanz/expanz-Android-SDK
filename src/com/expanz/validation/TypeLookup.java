package com.expanz.validation;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple lookup for the validation to type mapping
 * 
 * types are defined in the response XML
 * 
 */
public class TypeLookup {
	
	public static Map<String, String> validators = new HashMap<String, String>();
	
	static {
		validators.put("string", "com.expanz.validation.StringValidator");
		validators.put("number", "com.expanz.validation.NumberValidator");
	}

}
