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

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinitionXmlLoader;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

/**
 * The Class CloudformationRulesDefinition.
 */
public final class CloudformationRulesDefinition implements RulesDefinition {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Loggers.get(CloudformationRulesDefinition.class);

	/** The Constant PATH_TO_RULES_XML. */
	private static final String PATH_TO_RULES_XML = "/cloudformation-rules.xml";

	/** The Constant PATH_TO_CHECKOV_CLOUDFORMATION_RULES_XML. */
	private static final String PATH_TO_CHECKOV_CLOUDFORMATION_RULES_XML = "/cloudformation-checkov-cloudformation-rules.xml";

	/** The Constant PATH_TO_CHECKOV_TERRAFOM_RULES_XML. */
	private static final String PATH_TO_CHECKOV_TERRAFOM_RULES_XML = "/cloudformation-checkov-terraform-rules.xml";
	

	/** The Constant KEY. */
	public static final String KEY = "repo";

	/** The Constant NAME. */
	public static final String NAME = "repository";

	/** The context. */
	private Context context;
	
	/** The xml loader. */
	private final RulesDefinitionXmlLoader xmlLoader;

	/**
	 * Instantiates a new cloudformation rules definition.
	 *
	 * @param xmlLoader the xml loader
	 */
	public CloudformationRulesDefinition(RulesDefinitionXmlLoader xmlLoader) {
		super();
		this.xmlLoader = xmlLoader;
	}

	/**
	 * Gets the context.
	 *
	 * @return the context
	 */
	public Context getContext() {
		return context;
	}

	/**
	 * Define rules for cloudformation.
	 *
	 * @param context the context
	 * @param repositoryKey the repository key
	 * @param repositoryName the repository name
	 * @param languageKey the language key
	 */
	private void defineRulesForCloudformation(final Context context, final String repositoryKey, final String repositoryName,
			final String languageKey) {
		final NewRepository repository = context.createRepository(repositoryKey, languageKey).setName(repositoryName);

		addRules(repository, this.getClass().getResourceAsStream(PATH_TO_RULES_XML));
		addRules(repository, this.getClass().getResourceAsStream(PATH_TO_CHECKOV_CLOUDFORMATION_RULES_XML));
		repository.done();
	}

	
	/**
	 * Define rules for terraform.
	 *
	 * @param context the context
	 * @param repositoryKey the repository key
	 * @param repositoryName the repository name
	 * @param languageKey the language key
	 */
	private void defineRulesForTerraform(final Context context, final String repositoryKey, final String repositoryName,
			final String languageKey) {
		final NewRepository repository = context.createRepository(repositoryKey, languageKey).setName(repositoryName);
		addRules(repository, this.getClass().getResourceAsStream(PATH_TO_CHECKOV_TERRAFOM_RULES_XML));
		repository.done();
	}

	
	/**
	 * Adds the rules.
	 *
	 * @param repository the repository
	 * @param rulesXml the rules xml
	 */
	private void addRules(final NewRepository repository, final InputStream rulesXml) {
		if (rulesXml != null) {
			xmlLoader.load(repository, new InputStreamReader(rulesXml,StandardCharsets.UTF_8));
			for (final NewRule newRule : repository.rules()) {
				addNewRule(newRule);
			}
		}
	}

	/**
	 * Adds the new rule.
	 *
	 * @param newRule the new rule
	 */
	private static void addNewRule(final NewRule newRule) {
		try {
			final Set<String> tags = (Set<String>) FieldUtils.readField(newRule, "tags", true);
			for (final String tag : tags) {

				if (tag.contains("cweid-")) {
					newRule.addCwe(Integer.parseInt(tag.replace("cweid-", "")));
				}

				if (tag.contains("owasp-")) {
					newRule.addOwaspTop10(OwaspTop10.valueOf(tag.replace("owasp-", "").toUpperCase()));
				}
			}
		} catch (final IllegalAccessException e) {
			LOGGER.warn("Problem parsing security tags", e);
		}
	}

	/**
	 * Define.
	 *
	 * @param context the context
	 */
	@Override
	public void define(final Context context) {
		this.context = context;
		defineRulesForCloudformation(context, "cloudformation-plugin-cfn" ,"Cloudformation plugin(cfn) rules", "cloudformation");
		defineRulesForTerraform(context, "cloudformation-plugin-terraform" ,"Cloudformation plugin(terrraform) Rules", "terraform");
	}

}
