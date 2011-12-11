package com.expanz.model.entity;

import com.expanz.providers.DefaultContentProvider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Persistable entity that represents an Activity Response.
 */
public class Activities {
	
	public static final class ActivityEntity implements BaseColumns {
		
		/**
		 * Uri to the Activity table.
		 */
		public static final Uri CONTENT_URI = Uri
				.parse("content://" + DefaultContentProvider.AUTHORITY + "/" + DefaultContentProvider.ACTIVITY_TABLE);
		
		/**
		 * The handle for the activity.
		 */
		public static final String ACTIVITY_HANDLE = "ACTIVITY_HANDLE";
		
		/**
		 * The actual xml payload of the response data. 
		 */
		public static final String PAYLOAD = "PAYLOAD";
		
	}

}
