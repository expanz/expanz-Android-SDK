package com.expanz.model.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates row data, i.e. each of the column values in the tuple
 */
public class DataRow {
	
	/**
	 * The type of the context object, used for menu's etc
	 */
	private String type;
	
	/**
	 * The id of the data item
	 */
	private String id;
	
	/**
	 * The field data for each of the columns/cells
	 */
	private List<DataCell> cells = new ArrayList<DataCell>();
	
	/**
	 * The parent data object
	 */
	private Data parent;
	
	/**
	 * Add a cell to this Row
	 * 
	 * @param cell the data for a specific cell
	 */
	public void addCell(DataCell cell) {
		cells.add(cell);
	}

	/**
	 * Returns the cells for this row
	 * 
	 * @return all of the cells for this row
	 */
	public List<DataCell> getCells() {
		return cells;
	}

	/**
	 * The type of the context object
	 * 
	 * @return the row's context object type
	 */
	public String getType() {
		return type;
	}

	/**
	 * The type of the row's context object
	 * 
	 * @param type the type/class of the context object
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Return the id of the row's data item
	 * 
	 * @return the id of the row's data item
	 */
	public String getId() {
		return id;
	}

	/**
	 * The id of the row's data item
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * The parent {@link Data} object
	 * 
	 * @return the parent object
	 */
	public Data getParent() {
		return parent;
	}

	/**
	 * Set the parent Data object
	 * 
	 * @param parent the parent data object
	 */
	public void setParent(Data parent) {
		this.parent = parent;
	}

}
