package com.expanz.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.expanz.R;
import com.expanz.ServiceCallback;
import com.expanz.app.ActivityEx;
import com.expanz.app.ContextEx;
import com.expanz.app.ExpanzConstants;
import com.expanz.model.Message;
import com.expanz.model.response.SessionResponse;

/**
 * A composite widget that contains all the necessary widgets
 * to allow users to log in to an application. 
 *
 */
public class DefaultLoginEx extends TableLayout {

	/**
	 * Shows progress of login request
	 */
	private ProgressBar progress;
	
	/**
	 * To capture username
	 */
	private EditText usernameEdit;
	
	/**
	 * To capture password
	 */
	private EditText passwordEdit;
	
	/**
	 * To capture guest check
	 */
	private CheckBox guestCheck; 
	
	
	/**
	 * Ctor.
	 * 
	 * @param context the activity
	 */
	public DefaultLoginEx(Context context) {
		super(context);
		throw new RuntimeException("must define attributes");
	}

	/**
	 * Ctor.
	 * 
	 * @param context the activity
	 * @param attrs values defined in attr.xml
	 */
	public DefaultLoginEx(Context context, AttributeSet attrs) {
		super(context, attrs);
		layout(context, attrs);
	}

	/**
	 * Ctor.
	 * 
	 * @param context the activity
	 * @param attrs values defined in attr.xml
	 * @param defStyle the definition style
	 */
	private void layout(Context context, AttributeSet attrs) {
		
		LayoutParams parentParams = new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	
		setStretchAllColumns(false);
		setColumnStretchable(1, true);
		
		this.setLayoutParams(parentParams);

		addLogo(context, attrs);
		addUsername(context, attrs);
		addPassword(context, attrs);
		addGuestCheck(context, attrs);
		addButton(context, attrs);
		addProgress(context, attrs);
		
	}

	/**
	 * Add the progress bar to the widget
	 * 
	 * @param context the activity
	 * @param attrs values defined in attr.xml
	 */
	private void addProgress(Context context, AttributeSet attrs) {
		
		TableRow progressRow = new TableRow(context, attrs);

		progress = new ProgressBar(context, attrs);
		
		TableLayout.LayoutParams rowParams = new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		rowParams.setMargins(0, 5, 0, 5);
		
		TableRow.LayoutParams progressParams = new TableRow.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		
		progressParams.gravity = Gravity.CENTER;
		progressParams.span = 2;
		progressParams.weight = 1.0f;
		
		progress.setVisibility(INVISIBLE);
		
		progress.setLayoutParams(progressParams);

		progressRow.setLayoutParams(rowParams);
		progressRow.addView(progress);
		
		addView(progressRow);

		
	}

	/**
	 * Add the logo to the widget
	 * 
	 * @param context the activity
	 * @param attrs values defined in attr.xml
	 */
	private void addLogo(Context context, AttributeSet attrs) {
		
		TableRow logoRow = new TableRow(context, attrs);
		
		ImageView loginLogo = new ImageView(context, attrs);
		
		TableLayout.LayoutParams rowParams = new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		rowParams.setMargins(0, 15, 0, 35);
		
		logoRow.setLayoutParams(rowParams);
		
		TableRow.LayoutParams logoParams = new TableRow.LayoutParams(160, 45);
		logoParams.gravity = Gravity.LEFT;
		logoParams.span = 2;
		
		loginLogo.setLayoutParams(logoParams);
		
		//TODO allow users to specify their own logo, i.e. override default not intuitive
		loginLogo.setImageResource(R.drawable.expanz_login); 
		
		logoRow.addView(loginLogo);
		
		addView(logoRow);
		
	}

	/**
	 * Add the username to the widget
	 * 
	 * @param context the activity
	 * @param attrs values defined in attr.xml
	 */
	private void addUsername(Context context, AttributeSet attrs) {
		
		TableRow userRow = new TableRow(context, attrs);
		
		TableLayout.LayoutParams rowParams = new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		rowParams.setMargins(5, 3, 5, 3);
		
		userRow.setLayoutParams(rowParams);

		TextView usernameTitle = new TextView(context, attrs);
		usernameTitle.setText("Username"); // TODO load from resources to allow for i18n etc
		
		
		TableRow.LayoutParams userTitleParams = new TableRow.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		userTitleParams.setMargins(15, 3, 10, 0);
		
		usernameTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
		
		usernameTitle.setLayoutParams(userTitleParams);

		userRow.addView(usernameTitle);

		TableRow.LayoutParams userParams = new TableRow.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		userParams.setMargins(5, 3, 5, 5);
		
		usernameEdit = new EditText(context, attrs);
		usernameEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
		usernameEdit.setLayoutParams(userParams);
		
		userRow.addView(usernameEdit);

		addView(userRow);
	}

	/**
	 * Add the password to the widget
	 * 
	 * @param context the activity
	 * @param attrs values defined in attr.xml
	 */
	private void addPassword(Context context, AttributeSet attrs) {
		
		TableRow passRow = new TableRow(context, attrs);
		
		TableLayout.LayoutParams rowParams = new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		rowParams.setMargins(5, 3, 5, 3);
		
		passRow.setLayoutParams(rowParams);

		TextView passwordTitle = new TextView(context, attrs);
		passwordTitle.setText("Password"); // TODO load from resources to allow for i18n etc

		TableRow.LayoutParams passTitleParams = new TableRow.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		
		passTitleParams.setMargins(15, 3, 10, 0);
		
		passwordTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
		
		passwordTitle.setLayoutParams(passTitleParams);

		passRow.addView(passwordTitle);

		TableRow.LayoutParams passParams = new TableRow.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	
		passParams.setMargins(5, 3, 5, 5);

		passwordEdit = new EditText(context, attrs);
		passwordEdit.setLayoutParams(passParams);
		
		//TODO note that some emulators/phones don't honor these... why?
		passwordEdit.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
		passwordEdit.setTransformationMethod(new android.text.method.PasswordTransformationMethod().getInstance());
		
		passwordEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		
		passRow.addView(passwordEdit);

		addView(passRow);
	}
	
	/**
	 * Add the guest checkbox to the widget
	 * 
	 * @param context the activity
	 * @param attrs values defined in attr.xml
	 */
	private void addGuestCheck(Context context, AttributeSet attrs) {
		
		TableRow guestRow = new TableRow(context, attrs);
		
		TableLayout.LayoutParams rowParams = new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		rowParams.setMargins(5, 3, 5, 3);
		
		guestRow.setLayoutParams(rowParams);
		
		TextView spacer = new TextView(context, attrs);
		spacer.setText(""); 

		TableRow.LayoutParams spacerParams = new TableRow.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		
		//spacerParams.weight = 0.1f;
		
		spacer.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
		
		spacer.setLayoutParams(spacerParams);

		guestRow.addView(spacer);

		TableRow.LayoutParams guestParams = new TableRow.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		
		//guestParams.weight = 0.9f;
	
		guestCheck = new CheckBox(context, attrs);
		guestCheck.setText("Guest");
		guestCheck.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		guestCheck.setLayoutParams(guestParams);
		
		guestCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				
				if(isChecked) {
					usernameEdit.setEnabled(false);
					passwordEdit.setEnabled(false);
				} else {
					usernameEdit.setEnabled(true);
					passwordEdit.setEnabled(true);
				}
				
			} 
			
		});

		guestRow.addView(guestCheck);
		
		addView(guestRow);
	}

	/**
	 * Add the login button to the widget
	 * 
	 * @param context the activity
	 * @param attrs values defined in attr.xml
	 */
	private void addButton(final Context context, AttributeSet attrs) {
		
		TableRow buttonRow = new TableRow(context, attrs);
		
		TableLayout.LayoutParams rowParams = new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		rowParams.setMargins(5, 3, 5, 3);
		
		buttonRow.setLayoutParams(rowParams);
	
		ImageButton login = new ImageButton(context, attrs);
		login.setImageResource(R.drawable.login);
		login.setBackgroundResource(android.R.color.transparent);
		
		TableRow.LayoutParams buttonParams = new TableRow.LayoutParams(90, 40);
		buttonParams.gravity = Gravity.RIGHT;
		buttonParams.span = 2;
		buttonParams.weight = 1.0f;

		buttonRow.addView(login, buttonParams);
	
		addView(buttonRow);
		
		final ImageButton ref = login;

		login.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				
				ref.setColorFilter(0xFF00FF00, PorterDuff.Mode.MULTIPLY);
				
				final ContextEx contextEx = (ContextEx) context;
				contextEx.showProgress("Logging in");
				
				((ActivityEx) context).createSession(usernameEdit.getText()
						.toString(), passwordEdit.getText().toString(),
						guestCheck.isChecked(),
						new ServiceCallback<SessionResponse>() {

							public void completed(SessionResponse session) {
								
								ref.setColorFilter(null);
								
								if (session.getSessionHandle() != null) {
									Intent i = new Intent((Activity) context,
											contextEx.getMappingHolder()
													.getDefault().getForm());
									i.putExtra(ExpanzConstants.SESSION_HANDLE,
											session.getSessionHandle());
									context.startActivity(i);
								} else {
									
									Message error = new Message(
											ExpanzConstants.ERROR,
											"invalid credentials", "", "");
									((ActivityEx) context)
											.displayMessage(error);
								}
								
								contextEx.hideProgress();
								
							}

						});

			}

		});

	}

}
