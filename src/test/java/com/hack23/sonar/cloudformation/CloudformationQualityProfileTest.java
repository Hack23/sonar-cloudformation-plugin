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

import org.junit.Assert;
import org.junit.Test;
import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition.BuiltInQualityProfile;
import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition.Context;
import org.sonar.api.server.rule.RulesDefinitionXmlLoader;

/**
 * The Class CloudformationQualityProfileTest.
 */
public class CloudformationQualityProfileTest extends Assert {

	/**
	 * Define test.
	 */
	@Test
	public void defineTest() {
		final Context context = new Context();
		final org.sonar.api.server.rule.RulesDefinition.Context context2 = new org.sonar.api.server.rule.RulesDefinition.Context();
		final CloudformationRulesDefinition cloudformationRulesDefinition = new CloudformationRulesDefinition( new RulesDefinitionXmlLoader());
		cloudformationRulesDefinition.define(context2);
		new CloudformationQualityProfile(cloudformationRulesDefinition).define(context);


		{
		final BuiltInQualityProfile qualityProfile = context.profile("cloudformation","Cloudformation(cfn-nag,checkov) Rules");
		assertNotNull(qualityProfile);
		assertFalse(qualityProfile.isDefault());
		assertEquals(278,qualityProfile.rules().size());
		}
		{
		final BuiltInQualityProfile qualityProfile = context.profile("terraform","Terraform(checkov) Rules");
		assertNotNull(qualityProfile);
		assertFalse(qualityProfile.isDefault());
		assertEquals(594,qualityProfile.rules().size());
		}

	}

}
