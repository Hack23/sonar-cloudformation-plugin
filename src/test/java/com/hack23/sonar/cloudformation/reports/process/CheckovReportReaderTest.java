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
package com.hack23.sonar.cloudformation.reports.process;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.hack23.sonar.cloudformation.reports.checkov.CheckovReport;

/**
 * The Class CheckovReportReaderTest.
 */
public class CheckovReportReaderTest extends Assert {



	/**
	 * Read cloudformatation report test.
	 */
	@Test
	public void readCloudformatationReportTest() {
		final List<CheckovReport> checkovReport = new CheckovReportReader().readReport(CheckovReportReaderTest.class.getResourceAsStream("/checkov/cia-dist-cloudformation.checkov-report"));
		assertNotNull(checkovReport);
		assertFalse(checkovReport.isEmpty());
		assertEquals("cloudformation", checkovReport.get(0).getCheckType());
		assertNotNull(checkovReport.get(0).getSummary());
		assertNotNull(checkovReport.get(0).getResults());
	}

	/**
	 * Read terraform report test.
	 */
	@Test
	public void readTerraformReportTest() {
		final List<CheckovReport> checkovReport = new CheckovReportReader().readReport(CheckovReportReaderTest.class.getResourceAsStream("/checkov/main.checkov-report"));
		assertNotNull(checkovReport);
		assertFalse(checkovReport.isEmpty());
		assertEquals("terraform", checkovReport.get(0).getCheckType());
		assertNotNull(checkovReport.get(0).getSummary());
		assertNotNull(checkovReport.get(0).getResults());
	}
	
	/**
	 * Read arm report test.
	 */
	@Test
	public void readArmReportTest() {
		final List<CheckovReport> checkovReport = new CheckovReportReader().readReport(CheckovReportReaderTest.class.getResourceAsStream("/checkov/azuredeploy.checkov-report"));
		assertNotNull(checkovReport);
		assertFalse(checkovReport.isEmpty());
		assertEquals("arm", checkovReport.get(0).getCheckType());
		assertNotNull(checkovReport.get(0).getSummary());
		assertNotNull(checkovReport.get(0).getResults());
	}
	

	
	/**
	 * Read report failure test.
	 */
	@Test
	public void readReportFailureTest() {
		final List<CheckovReport> checkovReport = new CheckovReportReader().readReport(new ByteArrayInputStream("".getBytes()));
		assertNotNull(checkovReport);
		assertTrue(checkovReport.isEmpty());
	}

}
