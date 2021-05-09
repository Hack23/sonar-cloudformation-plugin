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

import java.io.IOException;

import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.scan.filesystem.PathResolver;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.api.utils.log.Profiler;

import com.hack23.sonar.cloudformation.reports.process.CfnNagProcessReports;
import com.hack23.sonar.cloudformation.reports.process.CheckovProcessReports;

/**
 * The Class CloudformationSensor.
 */
public final class CloudformationSensor implements Sensor {

	/** The Constant SENSOR_NAME. */
	public static final String SENSOR_NAME = "Cloudformation Check";

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Loggers.get(CloudformationSensor.class);

	/** The cfn nag process reports. */
	private final CfnNagProcessReports cfnNagProcessReports;

	/** The checkov process reports. */
	private final CheckovProcessReports checkovProcessReports;

	/** The configuration. */
	private final CloudformationSensorConfiguration configuration;

	/**
	 * Instantiates a new cloudformation sensor.
	 *
	 * @param configuration the configuration
	 * @param fileSystem the file system
	 * @param pathResolver the path resolver
	 */
	public CloudformationSensor(final CloudformationSensorConfiguration configuration, final FileSystem fileSystem,
			final PathResolver pathResolver) {
		super();
		this.configuration = configuration;
		this.cfnNagProcessReports = new CfnNagProcessReports(fileSystem, pathResolver);
		this.checkovProcessReports = new CheckovProcessReports(fileSystem, pathResolver);
	}

	/**
	 * Describe.
	 *
	 * @param descriptor the descriptor
	 */
	@Override
	public void describe(final SensorDescriptor descriptor) {
		descriptor.name(SENSOR_NAME);

	}

	/**
	 * Execute.
	 *
	 * @param context the context
	 */
	@Override
	public void execute(final SensorContext context) {
		final Profiler profiler = Profiler.create(LOGGER);
		profiler.startInfo("Process iac reports");

		try {
			cfnNagProcessReports.processCfnNagReport(context,configuration.getCfnNagReportFiles());
			checkovProcessReports.processCheckovReport(context,configuration.getCheckovReportFiles());
		} catch (final IOException e) {
			throw new RuntimeException("Can not process iac reports.", e);
		} finally {
			profiler.stopInfo();
		}
	}

}