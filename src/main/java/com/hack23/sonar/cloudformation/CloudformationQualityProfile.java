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

import java.util.HashSet;
import java.util.Set;

import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition;

/**
 * The Class CloudformationQualityProfile.
 */
public final class CloudformationQualityProfile implements BuiltInQualityProfilesDefinition {

	/** The Constant SUPPORTED_RULES. */
	private static final Set<String> SUPPORTED_RULES = new HashSet<>();

	static {
		SUPPORTED_RULES.add("F1");
		SUPPORTED_RULES.add("F2");
		SUPPORTED_RULES.add("F3");
		SUPPORTED_RULES.add("F4");
		SUPPORTED_RULES.add("F5");
		SUPPORTED_RULES.add("F6");
		SUPPORTED_RULES.add("F7");
		SUPPORTED_RULES.add("F8");
		SUPPORTED_RULES.add("F9");
		SUPPORTED_RULES.add("F10");
		SUPPORTED_RULES.add("F11");
		SUPPORTED_RULES.add("F12");
		SUPPORTED_RULES.add("F13");
		SUPPORTED_RULES.add("F14");
		SUPPORTED_RULES.add("F15");
		SUPPORTED_RULES.add("F16");
		SUPPORTED_RULES.add("F18");
		SUPPORTED_RULES.add("F19");		
		SUPPORTED_RULES.add("F20");
		SUPPORTED_RULES.add("F21");
		SUPPORTED_RULES.add("F22");
		SUPPORTED_RULES.add("F23");
		SUPPORTED_RULES.add("F24");
		SUPPORTED_RULES.add("F25");
		SUPPORTED_RULES.add("F26");
		SUPPORTED_RULES.add("F27");
		SUPPORTED_RULES.add("F28");
		SUPPORTED_RULES.add("F29");
		SUPPORTED_RULES.add("F30");
		SUPPORTED_RULES.add("F31");
		SUPPORTED_RULES.add("F32");
		SUPPORTED_RULES.add("F33");
		SUPPORTED_RULES.add("F34");
		SUPPORTED_RULES.add("F35");
		SUPPORTED_RULES.add("F36");
		SUPPORTED_RULES.add("F37");		
		SUPPORTED_RULES.add("F38");		
		SUPPORTED_RULES.add("F39");		
		SUPPORTED_RULES.add("F40");	
		SUPPORTED_RULES.add("F41");
		SUPPORTED_RULES.add("F42");
		SUPPORTED_RULES.add("F43");
		SUPPORTED_RULES.add("F44");
		SUPPORTED_RULES.add("F45");
		SUPPORTED_RULES.add("F46");
		SUPPORTED_RULES.add("F47");
		SUPPORTED_RULES.add("F48");
		SUPPORTED_RULES.add("F49");		
		SUPPORTED_RULES.add("F50");		
		SUPPORTED_RULES.add("F51");		
		SUPPORTED_RULES.add("F52");		
		SUPPORTED_RULES.add("F53");		
		SUPPORTED_RULES.add("F54");		
		SUPPORTED_RULES.add("F55");
		SUPPORTED_RULES.add("F56");
		SUPPORTED_RULES.add("F57");		
		SUPPORTED_RULES.add("F58");		
		SUPPORTED_RULES.add("F60");
		SUPPORTED_RULES.add("F61");
		SUPPORTED_RULES.add("F62");
		SUPPORTED_RULES.add("F63");
		SUPPORTED_RULES.add("F64");
		SUPPORTED_RULES.add("F65");
		SUPPORTED_RULES.add("F66");
		SUPPORTED_RULES.add("F67");
		SUPPORTED_RULES.add("F68");
		SUPPORTED_RULES.add("F69");
		SUPPORTED_RULES.add("F70");
		SUPPORTED_RULES.add("F71");		
		SUPPORTED_RULES.add("F74");		
		SUPPORTED_RULES.add("F75");
		SUPPORTED_RULES.add("F76");
		SUPPORTED_RULES.add("F77");
		SUPPORTED_RULES.add("F78");		
		SUPPORTED_RULES.add("F665");
		SUPPORTED_RULES.add("F1000");
		SUPPORTED_RULES.add("F2000");
		
		SUPPORTED_RULES.add("W1");
		SUPPORTED_RULES.add("W2");
		SUPPORTED_RULES.add("W5");
		SUPPORTED_RULES.add("W9");
		SUPPORTED_RULES.add("W10");
		SUPPORTED_RULES.add("W11");
		SUPPORTED_RULES.add("W12");
		SUPPORTED_RULES.add("W13");
		SUPPORTED_RULES.add("W14");
		SUPPORTED_RULES.add("W15");
		SUPPORTED_RULES.add("W16");
		SUPPORTED_RULES.add("W17");
		SUPPORTED_RULES.add("W18");
		SUPPORTED_RULES.add("W19");
		SUPPORTED_RULES.add("W20");
		SUPPORTED_RULES.add("W21");
		SUPPORTED_RULES.add("W22");
		SUPPORTED_RULES.add("W23");
		SUPPORTED_RULES.add("W24");
		SUPPORTED_RULES.add("W26");
		SUPPORTED_RULES.add("W27");
		SUPPORTED_RULES.add("W28");
		SUPPORTED_RULES.add("W29");
		SUPPORTED_RULES.add("W31");
		SUPPORTED_RULES.add("W32");
		SUPPORTED_RULES.add("W33");
		SUPPORTED_RULES.add("W34");
		SUPPORTED_RULES.add("W35");
		SUPPORTED_RULES.add("W36");
		SUPPORTED_RULES.add("W37");
		SUPPORTED_RULES.add("W38");
		SUPPORTED_RULES.add("W39");
		SUPPORTED_RULES.add("W40");
		SUPPORTED_RULES.add("W41");
		SUPPORTED_RULES.add("W42");
		SUPPORTED_RULES.add("W43");
		SUPPORTED_RULES.add("W44");
		SUPPORTED_RULES.add("W45");
		SUPPORTED_RULES.add("W46");
		SUPPORTED_RULES.add("W47");
		SUPPORTED_RULES.add("W48");
		SUPPORTED_RULES.add("W49");		
		SUPPORTED_RULES.add("W50");
		SUPPORTED_RULES.add("W51");
		SUPPORTED_RULES.add("W52");
		SUPPORTED_RULES.add("W53");
		SUPPORTED_RULES.add("W54");
		SUPPORTED_RULES.add("W55");
		SUPPORTED_RULES.add("W56");
		SUPPORTED_RULES.add("W57");
		SUPPORTED_RULES.add("W58");
		SUPPORTED_RULES.add("W59");
		SUPPORTED_RULES.add("W60");		
		SUPPORTED_RULES.add("W61");					
		SUPPORTED_RULES.add("W62");
		SUPPORTED_RULES.add("W63");		
		SUPPORTED_RULES.add("W64");		
		
	}

	public static boolean hasRule(final String id) {
		return SUPPORTED_RULES.contains(id);
	}
	
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

		for (final String ruleKey : SUPPORTED_RULES) {
			profile.activateRule(CloudformationRulesDefinition.REPO_KEY, ruleKey);
		}

		profile.done();
	}
}
