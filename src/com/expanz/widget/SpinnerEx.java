package com.expanz.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Spinner;

import com.expanz.R;
import com.expanz.ServiceCallback;
import com.expanz.app.ContextEx;
import com.expanz.model.request.ActivityRequest;
import com.expanz.model.request.DataPublicationRequest;
import com.expanz.model.request.DeltaRequest;
import com.expanz.model.response.ActivityResponse;
import com.expanz.model.response.Data;
import com.expanz.model.response.DataCell;
import com.expanz.model.response.DataRow;

/**
 * Expanz implementation of the Spinner widget
 * 
 * Required fields are;
 * 
 * fieldId - the id of the associated server side field
 * 
 */
public class SpinnerEx extends Spinner implements DataWidgetEx {

	/**
	 * Id of the associated field
	 */
	private String fieldId;

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
	public SpinnerEx(Context context) {
		super(context);
		this.setFocusable(true);
		throw new RuntimeException("method_name attribute must be defined");
	}

	/**
	 * Ctor.
	 * 
	 * @param context
	 *            the activity
	 * @param attrs
	 *            values defined in attr.xml
	 */
	public SpinnerEx(Context context, AttributeSet attrs) {

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
	public SpinnerEx(Context context, AttributeSet attrs, int defStyle) {

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

		fieldId = a.getString(R.styleable.BaseViewEx_field_id);

		if (fieldId == null) {
			throw new RuntimeException("field_id attribute must be defined");
		}
		
		composite.setDataId(a.getString(R.styleable.DataWidgetEx_data_id));
		
		a = getContext().obtainStyledAttributes(attrs,
				R.styleable.DataWidgetEx);

		composite.setDataId(a.getString(R.styleable.DataWidgetEx_data_id));

		if (composite.getDataId() == null) {
			throw new RuntimeException("data_id attribute must be defined");
		}
		
		composite.setQuery(a.getString(R.styleable.DataWidgetEx_query));
		composite.setPopulateMethod(a.getString(R.styleable.DataWidgetEx_populate_method));
		composite.setAutoPopulate(a.getBoolean(R.styleable.DataWidgetEx_auto_populate, false));
		composite.setRefresh(a.getBoolean(R.styleable.DataWidgetEx_refresh, false));
		composite.setUseThumbnails(a.getBoolean(R.styleable.DataWidgetEx_use_thumbnails, false));
		

	}

	/**
	 * Add a listener for selection events
	 */
	private void addListener() {
		
		//TODO this seems to get called on data load, need to potentially disable
		//while data load is happening

		setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> adapterView, View view,
					int position, long id) {

				ItemHolder holder = (ItemHolder) adapterView.getAdapter()
						.getItem(position);

				if (holder != null) {

					final ContextEx contextEx = (ContextEx) getContext();

					ActivityRequest request = new ActivityRequest(
							contextEx.getActivityHandle(),
							contextEx.getSessionHandle());
					DeltaRequest deltaRequest = new DeltaRequest(fieldId,
							holder.id);

					request.addDelta(deltaRequest);

					contextEx.getCommand().execute(request,
							new ServiceCallback<ActivityResponse>() {

								public void completed(ActivityResponse response) {
									contextEx.initFields(response);
								}
							});
				}

			}

			public void onNothingSelected(AdapterView<?> adapterView) {
				//just chill back spinner
			}
		});

	}

	/**
	 * Return the id of the associated field
	 * 
	 * @return
	 */
	public String getFieldId() {
		return fieldId;
	}

	/**
	 * Load the data into the spinner
	 */
	public void updateData(Data data) {

		List<ItemHolder> values = new ArrayList<ItemHolder>();

		List<DataRow> rows = data.getRows();

		if (rows != null) {
			for (DataRow dataRow : rows) {
				List<DataCell> cells = dataRow.getCells();

				if (cells != null && cells.size() > 0) {

					ItemHolder holder = new ItemHolder();

					holder.id = dataRow.getId();
					holder.value = cells.get(0).getValue();

					values.add(holder);
				}
			}
		}

		ArrayAdapter<ItemHolder> adapter = new ArrayAdapter<ItemHolder>(
				getContext(), android.R.layout.simple_spinner_item, values);

		setAdapter(adapter);

	}

	/**
	 * Get the id of the data publication
	 */
	public String getDataId() {
		return composite.getDataId();
	}

	/**
	 * Simple Struct for adapter items
	 */
	class ItemHolder {

		String id;
		String value;

		@Override
		public String toString() {
			return value;
		}

	}

	/**
	 * Get the data publication
	 */
	public DataPublicationRequest toPublication() {
		return composite.toPublication();
	}

	/**
	 * Get the adapter for this widget
	 */
	public ListAdapter getListAdapter() {
		return getListAdapter();
	}

	/**
	 * REturn the context for this widget
	 */
	public String getContextObject() {
		return composite.getContext();
	}

}
