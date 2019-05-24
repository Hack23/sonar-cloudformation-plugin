/*
 * 
 */
package com.hack23.sonar;

import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.sonar.api.server.rule.RulesDefinition.Context;
import org.sonar.api.server.rule.RulesDefinition.Rule;

/**
 * The Class CloudformationRulesDefinitionTest.
 */
public class CloudformationRulesDefinitionTest {


	/**
	 * Define context test.
	 */
	@Test
	public void defineContextTest() {		

		CloudformationRulesDefinition ruleDefinition = new CloudformationRulesDefinition();
		
		Context context = new Context();
		ruleDefinition.define(context);

		for (Rule rule : context.repositories().get(0).rules()) {
			assertFalse(rule.tags().isEmpty());
		}
	}
}
