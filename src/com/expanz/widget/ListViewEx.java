package com.expanz.widget;

import java.lang.reflect.Field;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.expanz.R;
import com.expanz.ServiceCallback;
import com.expanz.app.ContextEx;
import com.expanz.app.data.ListSimpleAdapterEx;
import com.expanz.model.request.ActivityRequest;
import com.expanz.model.request.ContextRequest;
import com.expanz.model.request.DataPublicationRequest;
import com.expanz.model.request.MenuActionRequest;
import com.expanz.model.response.ActivityResponse;
import com.expanz.model.response.Data;
import com.expanz.model.response.DataRow;

public class ListViewEx extends ListView implements DataWidgetEx, ContextMenuAware {

	/**
	 * The row layout identifier
	 */
	private int rowLayout;
	
	/**
	 * Is the contextMenu enabled
	 */
	private boolean menuEnabled;

	/**
	 * Defines common DataWidgetEx properties
	 */
	private DataWidgetComposite composite = new DataWidgetComposite();

	/**
	 * Ctor.
	 * 
	 * @param context
	 *            the activity
	 */
	public ListViewEx(Context context) {
		super(context);
		this.setFocusable(true);
		throw new RuntimeException("activity_name attribute must be defined");
	}

	/**
	 * Ctor.
	 * 
	 * @param context
	 *            the activity
	 * @param attrs
	 *            values defined in attr.xml
	 */
	public ListViewEx(Context context, AttributeSet attrs) {

		super(context, attrs);
		this.setFocusable(true);
		init(attrs);
		addListener();

	}

	/**
	 * Ctor.
	 * 
	 * @param context
	 *            the activity
	 * @param attrs
	 *            values defined in attr.xml
	 * @param defStyle
	 *            the definition style
	 */
	public ListViewEx(Context context, AttributeSet attrs, int defStyle) {

		super(context, attrs, defStyle);
		this.setFocusable(true);
		init(attrs);
		addListener();

	}

	/**
	 * Load values from the attribute set
	 * 
	 * @param attrs
	 *            values defined in attr.xml
	 */
	private void init(AttributeSet attrs) {
		TypedArray a = getContext().obtainStyledAttributes(attrs,
				R.styleable.BaseViewEx);

		composite.setContext(a.getString(R.styleable.BaseViewEx_context_object));

		if (composite.getContext() == null) {
			throw new RuntimeException(
					"context_object attribute must be defined");
		}

		a = getContext().obtainStyledAttributes(attrs, R.styleable.ListViewEx);

		// defined in xml in format expanz:row_layout="@layout/name_of_file.xml"
		String fileName = a.getString(R.styleable.ListViewEx_row_layout);

		if (fileName == null) {
			throw new RuntimeException("row_layout attribute must be defined");
		}

		a = getContext()
				.obtainStyledAttributes(attrs, R.styleable.DataWidgetEx);

		composite.setDataId(a.getString(R.styleable.DataWidgetEx_data_id));

		if (composite.getDataId() == null) {
			throw new RuntimeException("data_id attribute must be defined");
		}

		composite.setQuery(a.getString(R.styleable.DataWidgetEx_query));
		composite.setPopulateMethod(a
				.getString(R.styleable.DataWidgetEx_populate_method));
		composite.setAutoPopulate(a.getBoolean(
				R.styleable.DataWidgetEx_auto_populate, false));
		composite.setRefresh(a.getBoolean(R.styleable.DataWidgetEx_refresh,
				false));
		composite.setUseThumbnails(a.getBoolean(
				R.styleable.DataWidgetEx_use_thumbnails, false));

		// can't find easier way, e.g. like a.getResourceId
		try {

			Class clazz = Class.forName("com.expanz.R$layout");

			Field field = clazz
					.getField(fileName.substring(fileName.lastIndexOf("/") + 1,
							fileName.lastIndexOf(".xml")));

			rowLayout = field.getInt(clazz.newInstance());

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("unable to find layout" + fileName);
		}
		
		a = getContext()
				.obtainStyledAttributes(attrs, R.styleable.ContextMenuAware);
		
		menuEnabled = (a.getBoolean(R.styleable.ContextMenuAware_menu_enabled,
				false));

	}

	/**
	 * Add the item selection listener to this widget
	 */
	private void addListener() {

		final ContextEx contextEx = (ContextEx) getContext();

		//TODO this will need to allow individual widgets within a list
		//to be selected independently 
		setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {

				DataRow data = (DataRow) getAdapter().getItem(position);

				ContextRequest context = new ContextRequest();
				context.setId(data.getId());
				context.setType(data.getType());
				context.setContext(composite.getContext());

				MenuActionRequest menuAction = new MenuActionRequest();
				menuAction.setDefaultAction(true);
				menuAction.setContext(composite.getContext());

				ActivityRequest request = new ActivityRequest(
						contextEx.getActivityHandle(),
						contextEx.getSessionHandle());
				request.setContext(context);
				request.setMenuAction(menuAction);

				contextEx.getCommand().execute(request,
						new ServiceCallback<ActivityResponse>() {

							public void completed(ActivityResponse response) {
								contextEx.initFields(response);
							}

						});

			}
			
		});

	}


	/**
	 * Loads the data into the ListViews adapter
	 * 
	 * @param data
	 *            the data to load
	 */
	public void updateData(Data data) {
		this.setAdapter(new ListSimpleAdapterEx(getContext(), rowLayout, data));
	}

	/**
	 * Get the row layout XML id
	 * 
	 * @return row layout id
	 */
	public int getRowLayout() {
		return rowLayout;
	}

	/**
	 * Get the data publication id
	 */
	public String getDataId() {
		return composite.getDataId();
	}

	/**
	 * Get the data publication
	 */
	public DataPublicationRequest toPublication() {
		return composite.toPublication();
	}

	/**
	 * Get the adapter for this widget's data
	 */
	public ListAdapter getListAdapter() {
		return getAdapter();
	}

	/**
	 * Get the context object for this widget
	 */
	public String getContextObject() {
		return composite.getContext();
	}

	
	public boolean isMenuEnabled() {
		return menuEnabled;
	}

	/**
	 * Returns the view of this ContextAwareWidget 
	 */
	public View getView() {
		return this;
	}

}
