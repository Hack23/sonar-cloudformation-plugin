package com.hack23.sonar;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.sonar.api.config.Configuration;
import org.sonar.api.resources.AbstractLanguage;

public class CloudformationLanguage extends AbstractLanguage {

	  public static final String NAME = "Cloudformation";
	  public static final String KEY = "cfn";

	  private final Configuration config;

	  public CloudformationLanguage(Configuration config) {
	    super(KEY, NAME);
	    this.config = config;
	  }

	  @Override
	  public String[] getFileSuffixes() {
	    String[] suffixes = filterEmptyStrings(config.getStringArray(CloudformationProperties.FILE_SUFFIXES_KEY));
	    if (suffixes.length == 0) {
	      suffixes = StringUtils.split(CloudformationProperties.FILE_SUFFIXES_DEFAULT_VALUE, ",");
	    }
	    return suffixes;
	  }

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
