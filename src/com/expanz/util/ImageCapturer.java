package com.expanz.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import android.app.Activity;

import com.expanz.model.entity.ImageDetails;
import com.expanz.widget.ExpanzFieldWidget;

public interface ImageCapturer {

	void capture(ImageDetails imageDeets, String activityHandle,
			String sessionHandle,
			Map<String, List<ExpanzFieldWidget>> fieldWidgets, Activity context) throws IOException ;

	void send();

}
