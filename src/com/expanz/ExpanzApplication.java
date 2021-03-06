package com.expanz;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import roboguice.application.RoboApplication;
import android.os.Environment;

import com.expanz.app.Config;
import com.expanz.app.ExpanzConstants;
import com.expanz.app.data.ImageViewExListViewableHandler;
import com.expanz.app.data.ListViewableHandler;
import com.expanz.app.data.TextViewExListViewableHandler;
import com.expanz.model.entity.Activities;
import com.expanz.model.entity.Sessions;
import com.expanz.util.ActivityMappingHolder;
import com.expanz.widget.ImageViewEx;
import com.expanz.widget.TextViewEx;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.Singleton;

/**
 * An application context that acts as a global registry for data/state that can
 * be used across the whole application.
 * 
 * This class should remain read only if possible, to avoid the need for thread
 * synchronization etc.
 * 
 */
@Singleton
public class ExpanzApplication extends RoboApplication {
	
	@Inject ActivityMappingHolder holder;
	
	@Inject Config config;

	/**
	 * Map of Widgets that can be displayed in ListView
	 */
	private Map<String, ListViewableHandler> listViewables =
			new HashMap<String, ListViewableHandler>();
	
	/**
	 * Static instance for use anywhere
	 */
//	private static ExpanzApplication instance;
	
	/**
	 * Ctor.
	 * 
	 * Sets the static instance
	 * 
	 */
//	public ExpanzApplication() {
//		instance = this;
//	}

	/**
	 * Execute on application startup
	 */
	@Override
	public void onCreate() {
		
		getInjector();
		
		config.init();
		
		holder.load();

		registerListViewable(ImageViewEx.class.getName(),
				new ImageViewExListViewableHandler());
		registerListViewable(TextViewEx.class.getName(),
				new TextViewExListViewableHandler());

		doCleanup();

		super.onCreate();

	}

	/**
	 * Executed on application close
	 */
	@Override
	public void onTerminate() {

		doCleanup();

		super.onTerminate();
	}

	/**
	 * Clean up database and file system
	 */
	private void doCleanup() {

		// Delete all existing Activities
		try {
			getContentResolver().delete(Activities.ActivityEntity.CONTENT_URI,
					null, null);
		} catch (Exception e) {
		}

		// Delete all existing Sessions
		try {
			getContentResolver().delete(Sessions.SessionEntity.CONTENT_URI,
					null, null);
		} catch (Exception e) {
		}

		// Delete any images captured via camera
		// Note: Allow users to specify their own capture directory if they
		// do not want these temporary files to be cleaned up
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)
				&& !Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED_READ_ONLY)) {

			File cacheDir = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath() + ExpanzConstants.IMAGE_STORE_DIR);

			if (cacheDir.exists()) {
				cacheDir.delete();
			}
		}

	}

	/**
	 * Allow Expanz applications to override this default class
	 * and specify their own widgets to be displayed in ListView. 
	 * 
	 * @param widgetClass
	 * @param listViewable
	 */
	protected void registerListViewable(String widgetClass,
			ListViewableHandler listViewable) {
		listViewables.put(widgetClass, listViewable);
	}

	/**
	 * Returns a the ListViewable objects available to the application.
	 * @return
	 */
	public Map<String, ListViewableHandler> getListViewables() {
		return listViewables;
	}
	
	/**
	 * Returns the instance of this that can be referenced anywhere in the 
	 * app, e.g. for resource lookup
	 * 
	 * @return the instance of the ApplicationContext
	 */
//	public static ExpanzApplication getInstance() {
//		
//		//for tests
//		if(instance == null) {
//			instance = new ExpanzApplication();
//		}
//		
//		return instance;
//	}
	
	/**
	 * Add Roboguice modules
	 */
	@Override
	protected void addApplicationModules(List<Module> modules) {
        modules.add(new ExpanzCoreModule());
    }

}
