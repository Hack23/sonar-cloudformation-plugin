package com.hack23.sonar;

import org.sonar.api.Plugin;

public class CloudformationPlugin implements Plugin {

	@Override
	public void define(Context context) {
		context.addExtensions(CloudformationLanguage.class, CloudformationProperties.class);
		
	}

}
