package com.expanz.model.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates the data in the context menu
 *
 */
public class ContextMenuResponse {
	
	/**
	 * Each of the menu items available
	 */
	private List<MenuItemResponse> items = new ArrayList<MenuItemResponse>();
	
	/**
	 * Adds a new menu item
	 * 
	 * @param item the menu item to add
	 */
	public void addItem(MenuItemResponse item) {
		items.add(item);
	}

	/**
	 * Returns all the menu items
	 * 
	 * @return the menu items available
	 */
	public List<MenuItemResponse> getItems() {
		return items;
	}

}
