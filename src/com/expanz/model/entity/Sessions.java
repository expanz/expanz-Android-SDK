package com.expanz.model.entity;

import android.net.Uri;
import android.provider.BaseColumns;

import com.expanz.providers.DefaultContentProvider;

/**
 * Persistable entity that represents a Session Response.
 */
public class Sessions {
	
	public static final class SessionEntity implements BaseColumns {
		
		/**
		 * Uri to the session table
		 */
		public static final Uri CONTENT_URI = Uri
				.parse("content://" + DefaultContentProvider.AUTHORITY + "/" + DefaultContentProvider.SESSION_TABLE);
		
		/**
		 * The session handle
		 */
		public static final String SESSION_HANDLE = "SESSION_HANDLE";
		
		/**
		 * The actual XML Payload. 
		 */
		public static final String PAYLOAD = "PAYLOAD";
		
	}

}
