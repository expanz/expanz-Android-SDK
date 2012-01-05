package com.expanz;

import android.app.Application;

import com.google.inject.Inject;
import com.google.inject.Singleton;


public class Tester {
	
	@Inject Application app;
	
	public void test() {
		app.fileList();
	}

}
