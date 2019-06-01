/*
 * Cloudformation Plugin for SonarQube
 * Copyright (C) 2019 James Pether Sörling
 * james@hack23.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package com.hack23.sonar.cloudformation;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;
import org.sonar.api.config.Configuration;

public class CloudformationLanguageTest extends Assert {

	@Test
	public void getFileSuffixesTest() {
		final Configuration configuration = mock(Configuration.class);
		when(configuration.getStringArray(CloudformationProperties.FILE_SUFFIXES_KEY)).thenReturn(new String[] {".template"});
		
		final CloudformationLanguage cloudformationLanguage = new CloudformationLanguage(configuration);
		final String[] suffixes = cloudformationLanguage.getFileSuffixes();
		assertEquals(".template",suffixes[0]);
	}
	
	@Test
	public void getFileSuffixesMultipleValuesTest() {
		final Configuration configuration = mock(Configuration.class);
		when(configuration.getStringArray(CloudformationProperties.FILE_SUFFIXES_KEY)).thenReturn(new String[] {".template",".yml"});
		
		final CloudformationLanguage cloudformationLanguage = new CloudformationLanguage(configuration);
		final String[] suffixes = cloudformationLanguage.getFileSuffixes();
		assertEquals(".template",suffixes[0]);
		assertEquals(".yml",suffixes[1]);
		
	}

}
