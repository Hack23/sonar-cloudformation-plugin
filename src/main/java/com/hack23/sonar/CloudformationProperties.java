/*
 * 
 */
package com.hack23.sonar;

import java.util.Arrays;
import java.util.List;

import org.sonar.api.config.PropertyDefinition;
import org.sonar.api.resources.Qualifiers;


/**
 * The Class CloudformationProperties.
 */
public class CloudformationProperties {

	  /** The Constant FILE_SUFFIXES_KEY. */
  	public static final String FILE_SUFFIXES_KEY = "sonar.cloudformation.file.suffixes";
	  
  	/** The Constant FILE_SUFFIXES_DEFAULT_VALUE. */
  	public static final String FILE_SUFFIXES_DEFAULT_VALUE = ".template";

	  /**
  	 * Instantiates a new cloudformation properties.
  	 */
  	private CloudformationProperties() {
	    // only statics
	  }

	  /**
  	 * Gets the properties.
  	 *
  	 * @return the properties
  	 */
  	public static List<PropertyDefinition> getProperties() {
	    return Arrays.asList(PropertyDefinition.builder(FILE_SUFFIXES_KEY)
	      .defaultValue(FILE_SUFFIXES_DEFAULT_VALUE)
	      .category("Cloudformation")
	      .name("File Suffixes")
	      .description("Comma-separated list of suffixes for files to analyze.")
	      .onQualifiers(Qualifiers.PROJECT)
	      .build());
	  }
}
