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
package com.hack23.sonar;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.sonar.api.config.Configuration;
import org.sonar.api.resources.AbstractLanguage;

/**
 * The Class CloudformationLanguage.
 */
public class CloudformationLanguage extends AbstractLanguage {

	  /** The Constant NAME. */
  	public static final String NAME = "Cloudformation";
	  
  	/** The Constant KEY. */
  	public static final String KEY = "cfn";

	  /** The config. */
  	private final Configuration config;

	  /**
  	 * Instantiates a new cloudformation language.
  	 *
  	 * @param config the config
  	 */
  	public CloudformationLanguage(Configuration config) {
	    super(KEY, NAME);
	    this.config = config;
	  }

	  /**
  	 * Gets the file suffixes.
  	 *
  	 * @return the file suffixes
  	 */
  	@Override
	  public String[] getFileSuffixes() {
	    String[] suffixes = filterEmptyStrings(config.getStringArray(CloudformationProperties.FILE_SUFFIXES_KEY));
	    if (suffixes.length == 0) {
	      suffixes = StringUtils.split(CloudformationProperties.FILE_SUFFIXES_DEFAULT_VALUE, ",");
	    }
	    return suffixes;
	  }

	  /**
  	 * Filter empty strings.
  	 *
  	 * @param stringArray the string array
  	 * @return the string[]
  	 */
  	private String[] filterEmptyStrings(String[] stringArray) {
	    List<String> nonEmptyStrings = new ArrayList<String>();
	    for (String string : stringArray) {
	      if (StringUtils.isNotBlank(string.trim())) {
	        nonEmptyStrings.add(string.trim());
	      }
	    }
	    return nonEmptyStrings.toArray(new String[nonEmptyStrings.size()]);
	  }


}
