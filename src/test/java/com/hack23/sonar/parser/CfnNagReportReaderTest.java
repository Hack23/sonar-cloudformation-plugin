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
package com.hack23.sonar.parser;

import org.junit.Assert;
import org.junit.Test;

public class CfnNagReportReaderTest extends Assert {

	@Test
	public void readReportTest() {
		final CfnNagReport cfnNagReport = new CfnNagReportReader().readReport(CfnNagReportReaderTest.class.getResourceAsStream("/cfn_nag_report.json"));
		assertNotNull(cfnNagReport);
		assertEquals(1,cfnNagReport.getFailure_count());
		assertEquals(4,cfnNagReport.getViolations().size());
		final CfnNagViolation nagViolation = cfnNagReport.getViolations().get(0);
		assertEquals("W12",nagViolation.getId());
		assertEquals("WARN",nagViolation.getType());
		assertEquals("IAM policy should not allow * resource",nagViolation.getMessage());
		assertEquals(4,nagViolation.getLogical_resource_ids().size());
		assertEquals(1,nagViolation.getLine_numbers().size());
	}
}
