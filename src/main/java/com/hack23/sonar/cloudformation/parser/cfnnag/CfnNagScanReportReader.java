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
package com.hack23.sonar.cloudformation.parser.cfnnag;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

// TODO: Auto-generated Javadoc
/**
 * The Class CfnNagScanReportReader.
 */
public class CfnNagScanReportReader {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Loggers.get(CfnNagScanReportReader.class);

	/**
	 * Read report.
	 *
	 * @param input the input
	 * @return the list
	 */
	public List<CfnNagScanReport> readReport(final InputStream input) {
		final ObjectMapper objectMapper = new ObjectMapper();

		try {
			return objectMapper.readValue(input, new TypeReference<List<CfnNagScanReport>>() {});
		} catch (final IOException e) {
			LOGGER.warn("Problem reading cfn nag report json:{}", e.getMessage());
			return new ArrayList<>();
		}
	}
}
