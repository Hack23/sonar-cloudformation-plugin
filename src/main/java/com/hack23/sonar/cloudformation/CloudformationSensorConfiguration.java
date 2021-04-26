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

import java.util.Optional;

import org.sonar.api.config.Configuration;
import org.sonar.api.scanner.ScannerSide;

/**
 * The Class CloudformationSensorConfiguration.
 */
@ScannerSide
public final class CloudformationSensorConfiguration {

	/** The configuration. */
	private final Configuration configuration;

	/**
	 * Instantiates a new cloudformation sensor configuration.
	 *
	 * @param configuration the configuration
	 */
	public CloudformationSensorConfiguration(final Configuration configuration) {
		this.configuration = configuration;
	}

	/**
	 * Gets the cfn nag report files.
	 *
	 * @return the cfn nag report files
	 */
	public Optional<String> getCfnNagReportFiles() {
		return this.configuration.get(CloudformationConstants.CFN_NAG_REPORT_FILES_PROPERTY);
	}

	/**
	 * Gets the checkov report files.
	 *
	 * @return the checkov report files
	 */
	public Optional<String> getCheckovReportFiles() {
		return this.configuration.get(CloudformationConstants.CHECKOV_REPORT_FILES_PROPERTY);
	}

}
