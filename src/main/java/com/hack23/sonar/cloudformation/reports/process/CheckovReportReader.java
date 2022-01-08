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
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
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
	 * @return the list
	 */
	public List<CheckovReport> readReport(final InputStream input) {
		final ObjectMapper objectMapper = JsonMapper.builder().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).build();
		String report = null;
		try {
		    report = new String(IOUtils.toByteArray(input));

			return objectMapper.readValue(report,  new TypeReference<List<CheckovReport>>() { });
		} catch (final IOException e) {

			try {
				final CheckovReport checkovReport = objectMapper.readValue(report, CheckovReport.class);
				final List<CheckovReport> reportList = new ArrayList<>();
				reportList.add(checkovReport);
				return reportList;
			} catch (final IOException e2) {
				LOGGER.warn("Problem reading checkov report json:{}", e2.getMessage());
				return new ArrayList<>();
			}
		}
	}
}