package com.expanz.widget;

import android.view.View;

/**
 * Interface to mark widgets as being able to display a context menu
 * i.e. via long click
 *
 */
public interface ContextMenuAware {
	
	/**
	 * Is the menu enabled for this widget instance
	 * 
	 * @return true if enabled
	 */
	boolean isMenuEnabled();
	
	/**
	 * Returns the view so can be registered to menu
	 * 
	 * @return the view itself
	 */
	View getView();

}
