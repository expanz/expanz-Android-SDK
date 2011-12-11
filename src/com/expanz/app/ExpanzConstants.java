package com.expanz.app;

/**
 * Constants class, note constants are used over type safe enums as
 * this is the recommended practice for performance/memory reasons.
 *
 */
public class ExpanzConstants {

	/**
	 * Use alerts as the default message type
	 */
	public static final int TYPE_ALERT = 1;
	
	/**
	 * Use toasts as the default message type
	 */
	public static final int TYPE_TOAST = 2;
	
	/**
	 * Use notifications as the default message type
	 */
	public static final int TYPE_NOTIFICATION = 3;
	
	/**
	 * Set message type to error
	 */
	public static final int ERROR = 1;
	
	/**
	 * Set message type to warning
	 */
	public static final int WARNING = 2;
	
	/**
	 * Set message type to info
	 */
	public static final int INFO = 3;

	/**
	 * Session handle identifier for activities
	 */
	public static final String SESSION_HANDLE = "sessionhandle";

	/**
	 * Activity handle identifier for "Android" activities
	 */
	public static final String ACTIVITY_HANDLE = "activityhandle";

	/**
	 * URI of stored activity payload
	 */
	public static final String ACTIVITY_URI = "activityuri";

	/**
	 * Used for switching between activities
	 */
	public static final String TRANSITION_URI = "transitionuri";

	/**
	 * Used for switching between activities
	 */
	public static final String INIT_KEY = "initKey";

	/**
	 * Used for switching between activities
	 */
	public static final String INIT_TYPE = "initType";

	/**
	 * Used for pref to check if activity is newly created.
	 */
	public static String NOT_NEW = "not new";

	/**
	 * Directory for storage of image capture data
	 */
	public static final String IMAGE_STORE_DIR = "/Android/data/com.expanz/camera/cache";
	
	
}
