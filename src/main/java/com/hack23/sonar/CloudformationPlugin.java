/*
 * 
 */
package com.hack23.sonar;

import org.sonar.api.Plugin;

/**
 * The Class CloudformationPlugin.
 */
public class CloudformationPlugin implements Plugin {

	/**
	 * Define.
	 *
	 * @param context the context
	 */
	@Override
	public void define(Context context) {
		context.addExtensions(CloudformationLanguage.class, CloudformationProperties.class);
		
	}

}
