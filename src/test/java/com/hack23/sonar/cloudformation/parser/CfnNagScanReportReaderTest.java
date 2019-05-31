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
package com.hack23.sonar.cloudformation.parser;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.hack23.sonar.cloudformation.parser.CfnNagScanReport;
import com.hack23.sonar.cloudformation.parser.CfnNagScanReportReader;

public class CfnNagScanReportReaderTest extends Assert {

	@Test
	public void readReportTest() {
		final List<CfnNagScanReport> cfnNagReport = new CfnNagScanReportReader().readReport(CfnNagScanReportReaderTest.class.getResourceAsStream("/cfn-nag-scan.nagscan"));

		assertNotNull(cfnNagReport);
		assertFalse(cfnNagReport.isEmpty());
//		assertEquals(1,cfnNagReport.getFailure_count());
//		assertEquals(5,cfnNagReport.getViolations().size());
//		final CfnNagViolation nagViolation = cfnNagReport.getViolations().get(0);
//		assertEquals("W12",nagViolation.getId());
//		assertEquals("WARN",nagViolation.getType());
//		assertEquals("IAM policy should not allow * resource",nagViolation.getMessage());
//		assertEquals(4,nagViolation.getLogical_resource_ids().size());
//		assertEquals(4,nagViolation.getLine_numbers().size());
	}
}
