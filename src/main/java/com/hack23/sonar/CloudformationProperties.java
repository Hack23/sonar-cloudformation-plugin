package com.hack23.sonar;

import java.util.Arrays;
import java.util.List;
import org.sonar.api.config.PropertyDefinition;
import org.sonar.api.resources.Qualifiers;


public class CloudformationProperties {

	  public static final String FILE_SUFFIXES_KEY = "sonar.cloudformation.file.suffixes";
	  public static final String FILE_SUFFIXES_DEFAULT_VALUE = ".template";

	  private CloudformationProperties() {
	    // only statics
	  }

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
