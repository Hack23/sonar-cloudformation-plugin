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
import org.sonar.api.server.rule.RulesDefinition.Repository;
import org.sonar.api.server.rule.RulesDefinition.Rule;

import com.hack23.sonar.cloudformation.reports.process.CfnNagProcessReports;

/**
 * The Class CloudformationQualityProfile.
 */
public final class CloudformationQualityProfile implements BuiltInQualityProfilesDefinition {

	/** The Constant CLOUDFORMATION_RULES. */
	private static final String CLOUDFORMATION_RULES = "Cloudformation Rules";

	/** The Constant IAC_RULES. */
	private static final String IAC_RULES = "IAC Rules";

	/** The Constant SERVERLESS. */
	private static final String SERVERLESS = "serverless";

	/** The Constant CLOUDFORMATION. */
	private static final String CLOUDFORMATION = "cloudformation";

	/** The Constant CHECKOV. */
	private static final String CHECKOV = "checkov";

	/** The Constant CFN_RULE_PREFIX. */
	private static final String CFN_RULE_PREFIX = "cfn-";

	/** The cloudformation rules definition. */
	private final CloudformationRulesDefinition cloudformationRulesDefinition;

	/**
	 * Instantiates a new cloudformation quality profile.
	 *
	 * @param cloudformationRulesDefinition the cloudformation rules definition
	 */
	public CloudformationQualityProfile(final CloudformationRulesDefinition cloudformationRulesDefinition) {
		super();
		this.cloudformationRulesDefinition = cloudformationRulesDefinition;
	}

	/**
	 * Define.
	 *
	 * @param context the context
	 */
	@Override
	public void define(final Context context) {
		extracted(context, "yaml");
		extracted(context, "json");
	}

	/**
	 * Extracted.
	 *
	 * @param context  the context
	 * @param language the language
	 */
	private void extracted(final Context context, final String language) {
		createCloudformationQualityProfile(context, language);
		createIacQualityProfile(context, language);
	}

	/**
	 * Creates the iac quality profile.
	 *
	 * @param context the context
	 * @param language the language
	 */
	private void createIacQualityProfile(final Context context, final String language) {
		final NewBuiltInQualityProfile iacProfile = context.createBuiltInQualityProfile(IAC_RULES, language);
		for (final Repository repository : cloudformationRulesDefinition.getContext().repositories()) {
			if (repository.key().contains(CFN_RULE_PREFIX + language)) {
				for (final Rule rule : repository.rules()) {
					if (rule.tags().contains(CHECKOV)) {
						iacProfile.activateRule(CFN_RULE_PREFIX + language, rule.key());
					}
				}
			}
		}
		for (final String ruleKey : CfnNagProcessReports.SUPPORTED_RULES) {
			iacProfile.activateRule(CFN_RULE_PREFIX + language, ruleKey);
		}
		iacProfile.done();
	}

	/**
	 * Creates the cloudformation quality profile.
	 *
	 * @param context the context
	 * @param language the language
	 */
	private void createCloudformationQualityProfile(final Context context, final String language) {
		final NewBuiltInQualityProfile cloudFormationprofile = context
				.createBuiltInQualityProfile(CLOUDFORMATION_RULES, language);
		for (final Repository repository : cloudformationRulesDefinition.getContext().repositories()) {
			if (repository.key().contains(CFN_RULE_PREFIX + language)) {
				for (final Rule rule : repository.rules()) {
					if (rule.tags().contains(CHECKOV) && (rule.tags().contains(CLOUDFORMATION) || rule.tags().contains(SERVERLESS))) {
						cloudFormationprofile.activateRule(CFN_RULE_PREFIX + language, rule.key());
					} 
				}
			}
		}
		for (final String ruleKey : CfnNagProcessReports.SUPPORTED_RULES) {
			cloudFormationprofile.activateRule(CFN_RULE_PREFIX + language, ruleKey);
		}
		cloudFormationprofile.done();
	}
}
