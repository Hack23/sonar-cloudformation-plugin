/*
 * 
 */
package com.hack23.sonar;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.sonar.api.config.PropertyDefinition;

/**
 * The Class CloudformationPropertiesTest.
 */
public class CloudformationPropertiesTest {

	/**
	 * Gets the properties test.
	 *
	 * @return the properties test
	 */
	@Test
	public void getPropertiesTest() {
		List<PropertyDefinition> properties = CloudformationProperties.getProperties();
		assertEquals(1,properties.size());
	}
}
