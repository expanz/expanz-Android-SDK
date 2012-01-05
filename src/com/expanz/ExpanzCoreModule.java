package com.expanz;

import roboguice.config.AbstractAndroidModule;

import com.expanz.app.Config;
import com.expanz.app.ConfigImpl;
import com.expanz.util.ImageCaptureAgent;
import com.expanz.util.ImageCapturer;

public class ExpanzCoreModule extends AbstractAndroidModule {
	
	public ExpanzCoreModule() {
		super();
	}

	@Override
	protected void configure() {
		bind(Config.class).to(ConfigImpl.class).asEagerSingleton();
		bind(ImageCapturer.class).to(ImageCaptureAgent.class);
		bind(ExpanzCommand.class).to(ExpanzCommandImpl.class);
		bind(ServiceAgent.class).to(HTTPServiceAgent.class);
	}

}
