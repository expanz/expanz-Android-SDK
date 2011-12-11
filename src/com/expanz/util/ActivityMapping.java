package com.expanz.util;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

import com.expanz.model.request.DataPublicationRequest;
import com.expanz.model.response.ProcessAreaActivityResponse;

/**
 * Holds an entry from the config file /res/xml/activity_mappings.xml
 */
public class ActivityMapping {
	
	
	private Class<? extends Activity> mAndroidActivity;
	
	private String mExpanzActivityName;
	
	private String mStyle;
	
	private String mTitle;
	
	private Integer mRootLayout;
	
	private Integer mContentView;
	
	private Integer mMessageHandlerType;
	
	private boolean mCreateSession;
	
	private boolean mCreateActivity;
	
	private boolean mEntryPoint;
	
	private List<Integer> mListHeaders = new ArrayList<Integer>();
	
	private List<Integer> mListFooters = new ArrayList<Integer>();
	
	private List<String> mMenuItems = new ArrayList<String>();
	
	private List<DataPublicationRequest> mPublications = new ArrayList<DataPublicationRequest>();
	
	private Integer mListView;
	
	private boolean list;
	
	
	public ActivityMapping() {
	}
	
	public ActivityMapping(String form, String style) throws ClassNotFoundException {
		
		mAndroidActivity = (Class<? extends Activity>) Class.forName(form);
		mStyle = style;
		
	} 

	public Class<? extends Activity> getForm() {
		return mAndroidActivity;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		this.mTitle = title;
	}

	public Class<? extends Activity> getAndroidActivity() {
		return mAndroidActivity;
	}

	public void setAndroidActivity(Class<? extends Activity> mAndroidActivity) {
		this.mAndroidActivity = mAndroidActivity;
	}

	public String getStyle() {
		return mStyle;
	}

	public void setStyle(String mStyle) {
		this.mStyle = mStyle;
	}

	public Integer getMessageHandlerType() {
		return mMessageHandlerType;
	}

	public void setMessageHandlerType(Integer mMessageHandlerType) {
		this.mMessageHandlerType = mMessageHandlerType;
	}

	public boolean isCreateSession() {
		return mCreateSession;
	}

	public void setCreateSession(boolean mCreateSession) {
		this.mCreateSession = mCreateSession;
	}

	public boolean isCreateActivity() {
		return mCreateActivity;
	}

	public void setCreateActivity(boolean mCreateActivity) {
		this.mCreateActivity = mCreateActivity;
	}

	public Integer getRootLayout() {
		return mRootLayout;
	}

	public void setRootLayout(Integer mRootLayout) {
		this.mRootLayout = mRootLayout;
	}

	public Integer getContentView() {
		return mContentView;
	}

	public void setContentView(Integer mContentView) {
		this.mContentView = mContentView;
	}

	public String getExpanzActivityName() {
		return mExpanzActivityName;
	}

	public void setExpanzActivityName(String mExpanzActivityName) {
		this.mExpanzActivityName = mExpanzActivityName;
	}

	public boolean isEntryPoint() {
		return mEntryPoint;
	}

	public void setEntryPoint(boolean mEntryPoint) {
		this.mEntryPoint = mEntryPoint;
	}

	public List<Integer> getListHeaders() {
		return mListHeaders;
	}

	public void addListHeader(Integer listHeader) {
		this.mListHeaders.add(listHeader);
	}

	public Integer getListView() {
		return mListView;
	}

	public void setListView(Integer listView) {
		this.mListView = listView;
	}

	public boolean isList() {
		return list;
	}

	public void setList(boolean list) {
		this.list = list;
	}

	public List<DataPublicationRequest> getPublications() {
		return mPublications;
	}

	public void addPublication(DataPublicationRequest publication) {
		this.mPublications.add(publication);
	}

	public List<String> getMenuItems() {
		return mMenuItems;
	}

	public void addMenuItem(String menuItem) {
		this.mMenuItems.add(menuItem);
	}

	public List<Integer> getListFooters() {
		return mListFooters;
	}

	public void addListFooter(Integer listFooter) {
		this.mListFooters.add(listFooter);
	}

	public static ActivityMapping createMapping(ProcessAreaActivityResponse activity) throws ClassNotFoundException {
		
		return new ActivityMapping(activity.getName(), activity.getStyle());
		
	}

}
