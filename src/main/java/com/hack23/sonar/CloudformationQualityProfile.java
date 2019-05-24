/*
 * 
 */
package com.hack23.sonar;

import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition;

/**
 * The Class CloudformationQualityProfile.
 */
public class CloudformationQualityProfile implements BuiltInQualityProfilesDefinition {

	  /**
  	 * Define.
  	 *
  	 * @param context the context
  	 */
  	@Override
	  public void define(Context context) {
	    NewBuiltInQualityProfile profile = context.createBuiltInQualityProfile("Cloudformation Rules", CloudformationLanguage.KEY);
	    profile.setDefault(true);

	    NewBuiltInActiveRule rule1 = profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "F1");

	    profile.done();
	  }
}
