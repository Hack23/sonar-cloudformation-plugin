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

import java.io.IOException;
import java.io.InputStream;

import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hack23.sonar.cloudformation.reports.checkov.CheckovReport;

/**
 * The Class CheckovReportReader.
 */
public class CheckovReportReader {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Loggers.get(CheckovReportReader.class);

	/**
	 * Read report.
	 *
	 * @param input the input
	 * @return the checkov report
	 */
	public CheckovReport readReport(final InputStream input) {
		final ObjectMapper objectMapper = new ObjectMapper();

		try {
			return objectMapper.readValue(input, CheckovReport.class);
		} catch (final IOException e) {
			LOGGER.warn("Problem reading checkov report json:{}", e.getMessage());
			return new CheckovReport();
		}
	}
}