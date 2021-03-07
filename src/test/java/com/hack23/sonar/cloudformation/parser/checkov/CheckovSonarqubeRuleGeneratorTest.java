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
package com.hack23.sonar.cloudformation.parser.checkov;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;

public class CheckovSonarqubeRuleGeneratorTest {

	private final static String XML_ENTRY ="    <rule>\n"
			+ "		<key>{RULE_ID}</key>\n"
			+ "		<name>{NAME}</name>\n"
			+ "		<internalKey>{RULE_ID}</internalKey>\n"
			+ "		<description>{NAME}</description>\n"
			+ "		<severity>CRITICAL</severity>\n"
			+ "		<cardinality>SINGLE</cardinality>\n"
			+ "		<status>READY</status>\n"
			+ "		<type>VULNERABILITY</type>\n"
			+ "		<tag>security</tag>\n"
			+ "		<tag>checkov</tag>\n"
			+ "		<tag>{IaC}</tag>\n"
			+ "		<tag>{Type}</tag>\n"
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
	*/
	
	
	/**
	 * Read report test.
	 * @throws IOException 
	 */
	@Test
	public void generateSonarqubeRuleDefinitionsForCheckovTest() throws IOException {
		final CSVParser parser = CSVParser.parse(new InputStreamReader(this.getClass().getResourceAsStream("/checkov/rules.txt"),StandardCharsets.UTF_8), CSVFormat.EXCEL.withHeader().withDelimiter('|').withTrim());
		final List<CSVRecord> records = parser.getRecords();
		records.remove(0);
		for (final CSVRecord csvRecord : records) {
			if (csvRecord.isSet("Id")) {
				System.out.println(XML_ENTRY.replace("{RULE_ID}",csvRecord.get("Id")).replace("{NAME}",csvRecord.get("Policy")).replace("{IaC}",csvRecord.get("IaC").toLowerCase()).replace("{Type}",csvRecord.get("Type").toLowerCase()));
			}
		}

	}

}
