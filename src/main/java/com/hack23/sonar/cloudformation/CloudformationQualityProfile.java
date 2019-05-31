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
	public void define(final Context context) {
		final NewBuiltInQualityProfile profile = context.createBuiltInQualityProfile("Cloudformation Rules",
				CloudformationLanguage.KEY);
		profile.setDefault(true);

		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "F1");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "F2");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "F3");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "F4");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "F5");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "F6");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "F7");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "F8");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "F9");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "F10");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "F11");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "F12");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "F13");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "F14");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "F15");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "F16");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "F18");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "F20");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "F21");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "F22");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "F23");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "F24");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "F25");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "F26");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "F27");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "F28");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "F29");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "F30");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "F31");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "F665");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "F1000");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "F2000");

		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "W1");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "W2");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "W5");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "W9");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "W10");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "W11");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "W12");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "W13");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "W14");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "W15");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "W16");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "W17");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "W18");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "W19");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "W20");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "W21");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "W22");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "W23");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "W24");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "W26");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "W27");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "W28");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "W29");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "W31");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "W32");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "W33");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "W34");
		profile.activateRule(CloudformationRulesDefinition.REPO_KEY, "W35");		
		profile.done();
	}
}
