package com.expanz.providers;

import com.expanz.model.entity.Activities;
import com.expanz.model.entity.Sessions;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Default Data Access Object. The class defines common 
 * operations on the 
 *
 */
public class DefaultContentProvider extends ContentProvider {

	/**
	 * Helper used for creation and updating of the database schema
	 */
	private DatabaseHelper dbHelper;

	/**
	 * Authority for database 
	 */
	public static final String AUTHORITY = "com.expanz.providers.defaultcontentprovider";

	/**
	 * Name of the database
	 */
	private static final String DATABASE_NAME = "ExpanzService.db";

	/**
	 * Current Version of the database
	 */
	private static final int DATABASE_VERSION = 1;

	/**
	 * Activity table name
	 */
	public static final String ACTIVITY_TABLE = "activity";

	/**
	 * Session table name
	 */
	public static final String SESSION_TABLE = "session";

	/**
	 * URI matcher for authority to table mapping
	 */
	private static final UriMatcher sUriMatcher;

	/**
	 * Identifier for all Activities operation
	 */
	private static final int ACTIVIES = 1;

	/**
	 * Identifier for all Sessions operation
	 */
	private static final int SESSIONS = 2;
	
	/**
	 * Identifier for single Activity operation
	 */
	private static final int ACTIVITY = 3;
	
	/**
	 * Identifier for single session operation
	 */
	private static final int SESSION = 4;

	static {

		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(AUTHORITY, ACTIVITY_TABLE, ACTIVIES);
		sUriMatcher.addURI(AUTHORITY, SESSION_TABLE, SESSIONS);
		sUriMatcher.addURI(AUTHORITY, ACTIVITY_TABLE + "/#", ACTIVITY);
		sUriMatcher.addURI(AUTHORITY, SESSION_TABLE + "/#", SESSION);

	}

	/**
	 * Static class for database schema creation and update
	 */
	private static class DatabaseHelper extends SQLiteOpenHelper {

		/**
		 * Ctor.
		 * 
		 * @param context the current context (activity/application) 
		 */
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void onCreate(SQLiteDatabase db) {

			db.execSQL("Create table "
					+ ACTIVITY_TABLE
					+ "( _id INTEGER PRIMARY KEY AUTOINCREMENT, ACTIVITY_HANDLE TEXT, PAYLOAD TEXT);");

			db.execSQL("Create table "
					+ SESSION_TABLE
					+ "( _id INTEGER PRIMARY KEY AUTOINCREMENT, SESSION_HANDLE TEXT, PAYLOAD TEXT);");
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + ACTIVITY_TABLE);
			db.execSQL("DROP TABLE IF EXISTS " + SESSION_TABLE);
			onCreate(db);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {

		SQLiteDatabase db = dbHelper.getWritableDatabase();

		int count;

		switch (sUriMatcher.match(uri)) {

		case ACTIVIES:

			count = db.delete(ACTIVITY_TABLE, selection, selectionArgs);

			break;

		case SESSIONS:

			count = db.delete(SESSION_TABLE, selection, selectionArgs);

			break;
			
		case ACTIVITY:

			count = db.delete(ACTIVITY_TABLE, selection, selectionArgs);

			break;

		case SESSION:

			count = db.delete(ACTIVITY_TABLE, selection, selectionArgs);

			break;

		default:

			throw new IllegalArgumentException("Unknown URI " + uri);

		}

		getContext().getContentResolver().notifyChange(uri, null);

		return count;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getType(Uri uri) {

		switch (sUriMatcher.match(uri)) {

		case ACTIVIES:

			return "vnd.android.cursor.dir/vnd.expanz.mactivity";

		case SESSIONS:

			return "vnd.android.cursor.dir/vnd.expanz.msession";
			
		case ACTIVITY:

			return "vnd.android.cursor.item/vnd.expanz.activity";

		case SESSION:

			return "vnd.android.cursor.item/vnd.expanz.session";

		default:

			return null;

		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Uri insert(Uri uri, ContentValues values) {

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		long rowId;

		switch (sUriMatcher.match(uri)) {

		case ACTIVIES:

			rowId = db.insert(ACTIVITY_TABLE, "", values);
			
			if (rowId > 0) {

				Uri rowUri = ContentUris.withAppendedId(Activities.ActivityEntity.CONTENT_URI, rowId);
				getContext().getContentResolver().notifyChange(rowUri, null);

				return rowUri;
			}

			break;

		case SESSIONS:

			rowId = db.insert(SESSION_TABLE, "", values);
			
			if (rowId > 0) {

				Uri rowUri = ContentUris.withAppendedId(Sessions.SessionEntity.CONTENT_URI, rowId);
				getContext().getContentResolver().notifyChange(rowUri, null);

				return rowUri;
			}

			break;

		default:

			throw new IllegalArgumentException("Unknown URI " + uri);

		}

		throw new SQLException("Failed to insert row into " + uri);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onCreate() {
		dbHelper = new DatabaseHelper(getContext());
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		SQLiteDatabase db = dbHelper.getReadableDatabase();

		switch (sUriMatcher.match(uri)) {

		case ACTIVIES:

			qb.setTables(ACTIVITY_TABLE);

			break;
			
		case ACTIVITY:
		
	        qb.appendWhere(Activities.ActivityEntity._ID + "="
	                + uri.getLastPathSegment());

			qb.setTables(ACTIVITY_TABLE);

			break;

		case SESSIONS:

			qb.setTables(SESSION_TABLE);

			break;
			
		case SESSION:
			
			qb.appendWhere(Sessions.SessionEntity._ID + "="
	                + uri.getLastPathSegment());

			qb.setTables(SESSION_TABLE);

			break;

		default:

			throw new IllegalArgumentException("Unknown URI " + uri);

		}

		Cursor c = qb.query(db, projection, selection, selectionArgs, null, null,
				sortOrder);

		c.setNotificationUri(getContext().getContentResolver(), uri);

		return c;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {

		SQLiteDatabase db = dbHelper.getWritableDatabase();

		int count;

		switch (sUriMatcher.match(uri)) {

		case ACTIVIES:

			count = db.update(ACTIVITY_TABLE, values, selection, selectionArgs);

			break;
		
		case ACTIVITY:

			count = db.update(ACTIVITY_TABLE, values, selection, selectionArgs);

			break;

		case SESSIONS:

			count = db.update(SESSION_TABLE, values, selection, selectionArgs);

			break;
		
		case SESSION:

			count = db.update(SESSION_TABLE, values, selection, selectionArgs);

			break;

		default:

			throw new IllegalArgumentException("Unknown URI " + uri);

		}

		getContext().getContentResolver().notifyChange(uri, null);

		return count;

	}

}
