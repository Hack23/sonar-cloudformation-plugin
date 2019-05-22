package com.hack23.sonar;

import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition;

public class CloudformationQualityProfile implements BuiltInQualityProfilesDefinition {

	  @Override
	  public void define(Context context) {
	    NewBuiltInQualityProfile profile = context.createBuiltInQualityProfile("Cloudformation Rules", CloudformationLanguage.KEY);
	    profile.setDefault(true);

	    NewBuiltInActiveRule rule1 = profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "F1");

	    profile.done();
	  }
}
