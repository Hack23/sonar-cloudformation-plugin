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
package com.hack23.sonar.cloudformation.reports.checkov;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;

/**
 * The Class CheckovSonarqubeRuleGeneratorTest.
 */
public class CheckovSonarqubeRuleGeneratorTest {

	/** The Constant XML_ENTRY. */
	private final static String XML_ENTRY ="    <rule>\n"
			+ "		<key>{IaC}-{RULE_ID}</key>\n"
			+ "		<name>{NAME}</name>\n"
			+ "		<internalKey>{IaC}-{RULE_ID}</internalKey>\n"
			+ "		<description>{NAME}</description>\n"
			+ "		<severity>CRITICAL</severity>\n"
			+ "		<cardinality>SINGLE</cardinality>\n"
			+ "		<status>READY</status>\n"
			+ "		<type>VULNERABILITY</type>\n"
			+ "		<tag>security</tag>\n"
			+ "		<tag>checkov</tag>\n"
			+ "		<tag>{IaC}</tag>{EXTRA_TAGS}\n"
			+ "		<remediationFunction>CONSTANT_ISSUE</remediationFunction>\n"
			+ "		<remediationFunctionBaseEffort>10min</remediationFunctionBaseEffort>\n"
			+ "    </rule>";


	/*
    | Id           | Type              | Entity                                                                       | Policy                                                                                                                                                                                                   | IaC            |
|-----|--------------|-------------------|------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------|
|   0 | CKV_AWS_1    | data              | aws_iam_policy_document                                                      | Ensure IAM policies that allow full "*-*" administrative privileges are not created                                                                                                                      | Terraform      |
|   1 | CKV_AWS_1    | resource          | serverless_aws                                                               | Ensure IAM policies that allow full "*-*" administrative privileges are not created                                                                                                                      | serverless     |
|   2 | CKV_AWS_2    | resource          | aws_alb_listener                                                             | Ensure ALB protocol is HTTPS                                                                                                                                                                             | Terraform      |
|   3 | CKV_AWS_2    | resource          | aws_lb_listener                                                              | Ensure ALB protocol is HTTPS                                                                                                                                                                             | Terraform      |
	*
	*/

	/** The nist policy string mapping. */
	private static Map<String,String> NIST_POLICY_STRING_MAPPING = new HashMap<>();

	/** The Constant DETECT_SC_13. */
	private static final String DETECT_SC_13 ="encrypted at rest";
	
	/** The Constant DETECT2_SC_13. */
	private static final String DETECT2_SC_13 ="securely encrypted";
	
	/** The Constant SC_13_TAGS. */
	private static final String SC_13_TAGS ="\n		<tag>owasp-a6</tag>\n		<tag>cweid-311</tag>\n		<tag>800-53-sc-13</tag>";

	/** The Constant DETECT_SC_8. */
	private static final String DETECT_SC_8 ="encryption transit";
	
	/** The Constant DETECT2_SC_8. */
	private static final String DETECT2_SC_8 ="https";
	
	/** The Constant SC_8_TAGS. */
	private static final String SC_8_TAGS ="\n		<tag>owasp-a6</tag>\n		<tag>cweid-311</tag>\n		<tag>800-53-sc-8</tag>";

	/** The Constant DETECT_AC_6. */
	private static final String DETECT_AC_6 ="IAM policies";
	
	/** The Constant AC_6_TAGS. */
	private static final String AC_6_TAGS ="\n		<tag>owasp-a6</tag>\n		<tag>cweid-272</tag>\n		<tag>800-53-ac-6</tag>";

	/** The Constant DETECT_AC_4_GROUP. */
	private static final String DETECT_AC_4_GROUP ="security group";
	
	/** The Constant AC_4_TAGS_GROUP. */
	private static final String AC_4_TAGS_GROUP ="\n		<tag>owasp-a6</tag>\n		<tag>cweid-732</tag>\n		<tag>800-53-ac-4</tag>";

	/** The Constant DETECT_AC_4. */
	private static final String DETECT_AC_4 ="public";
	
	/** The Constant AC_4_TAGS. */
	private static final String AC_4_TAGS ="\n		<tag>owasp-a6</tag>\n		<tag>cweid-732</tag>\n		<tag>800-53-ac-4</tag>";

	/** The Constant DETECT_AU_12. */
	private static final String DETECT_AU_12 ="logging";
	
	/** The Constant AU_12_TAGS. */
	private static final String AU_12_TAGS ="\n		<tag>owasp-a10</tag>\n		<tag>cweid-778</tag>\n		<tag>800-53-au-12</tag>";

	/** The Constant DETECT_CP_9. */
	private static final String DETECT_CP_9 ="retention backup";
	
	/** The Constant CP_9_TAGS. */
	private static final String CP_9_TAGS ="\n		<tag>owasp-a6</tag>\n		<tag>cweid-693</tag>\n		<tag>800-53-cp-9</tag>";

	/** The Constant DETECT_AU_11. */
	private static final String DETECT_AU_11 ="retention log";
	
	/** The Constant AU_11_TAGS. */
	private static final String AU_11_TAGS ="\n		<tag>owasp-a6</tag>\n		<tag>cweid-779</tag>\n		<tag>800-53-au-11</tag>";


	/** The Constant keyrotation. */
	private static final String keyrotation ="rotat";

	static {
		NIST_POLICY_STRING_MAPPING.put(DETECT_SC_13,SC_13_TAGS);
		NIST_POLICY_STRING_MAPPING.put(DETECT2_SC_13,SC_13_TAGS);
		NIST_POLICY_STRING_MAPPING.put(DETECT_SC_8,SC_8_TAGS);
		NIST_POLICY_STRING_MAPPING.put(DETECT2_SC_8,SC_8_TAGS);
		NIST_POLICY_STRING_MAPPING.put(DETECT_AC_6,AC_6_TAGS);
		NIST_POLICY_STRING_MAPPING.put(DETECT_AC_4,AC_4_TAGS);
		NIST_POLICY_STRING_MAPPING.put(DETECT_AC_4_GROUP,AC_4_TAGS_GROUP);
		NIST_POLICY_STRING_MAPPING.put(DETECT_AU_12,AU_12_TAGS);
		NIST_POLICY_STRING_MAPPING.put(DETECT_CP_9,CP_9_TAGS);
		NIST_POLICY_STRING_MAPPING.put(DETECT_AU_11,AU_11_TAGS);
	}


	/**
	 * Generate sonarqube rule definitions for checkov test.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void generateSonarqubeRuleDefinitionsForCheckovTest() throws IOException {
		final CSVParser parser = CSVParser.parse(new InputStreamReader(this.getClass().getResourceAsStream("/checkov/rules.txt"),StandardCharsets.UTF_8), CSVFormat.EXCEL.withHeader().withDelimiter('|').withTrim());
		final List<CSVRecord> records = parser.getRecords();
		records.remove(0);
		final Map<String,String> map = new HashMap<>();
		for (final CSVRecord csvRecord : records) {
			if (csvRecord.isSet("Id") && "resource".equals(csvRecord.get("Type")) && !map.containsKey(csvRecord.get("IaC") +"-" + csvRecord.get("Id"))) {
				String ruleEntryUntagged = XML_ENTRY.replace("{RULE_ID}",csvRecord.get("Id")).replace("{NAME}",csvRecord.get("Policy")).replace("{IaC}",csvRecord.get("IaC").toLowerCase()).replace("\"","&quot;");

				for (final String key : NIST_POLICY_STRING_MAPPING.keySet()) {
					if (ruleEntryUntagged.toLowerCase().contains(key)) {
						ruleEntryUntagged = ruleEntryUntagged.replace("{EXTRA_TAGS}",NIST_POLICY_STRING_MAPPING.get(key));
					}
				}
				ruleEntryUntagged = ruleEntryUntagged.replace("{EXTRA_TAGS}","");
				System.out.println(ruleEntryUntagged);

				map.put(csvRecord.get("IaC") +"-" + csvRecord.get("Id"),"");
			}
		}

	}

}
