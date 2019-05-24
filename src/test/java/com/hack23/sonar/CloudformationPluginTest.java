/*
 * 
 */
package com.hack23.sonar;

import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.sonar.api.Plugin;

/**
 * The Class CloudformationPluginTest.
 */
public class CloudformationPluginTest {

	/**
	 * Extensions test.
	 */
	@Test
	public void extensionsTest() {
		final Plugin.Context context = mock(Plugin.Context.class);
		new CloudformationPlugin().define(context);
	}
}
