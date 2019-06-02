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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.sonar.api.batch.fs.internal.DefaultFileSystem;
import org.sonar.api.batch.fs.internal.DefaultInputFile;
import org.sonar.api.batch.fs.internal.TestInputFileBuilder;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.internal.DefaultSensorDescriptor;
import org.sonar.api.batch.sensor.internal.SensorContextTester;
import org.sonar.api.config.Configuration;
import org.sonar.api.scan.filesystem.PathResolver;

/**
 * The Class CloudformationSensorTest.
 */
public class CloudformationSensorTest extends Assert {

	/**
	 * Describe test.
	 */
	@Test
	public void describeTest() {
		final CloudformationSensor cloudformationSensor = new CloudformationSensor(null, null, null);
		final DefaultSensorDescriptor sensorDescriptor = new DefaultSensorDescriptor();
		cloudformationSensor.describe(sensorDescriptor);
		assertEquals(CloudformationSensor.SENSOR_NAME, sensorDescriptor.name());
	}

	/**
	 * Execute simple nag report test.
	 * 
	 * @throws IOException
	 */
	@Test
	public void executeSimpleNagReportTest() throws IOException {
		final Configuration configuration = mock(Configuration.class);
		when(configuration.get(CloudformationConstants.REPORT_FILES_PROPERTY))
				.thenReturn(Optional.of("src/test/resources/aws-cross-account-manager-master.yml.nag"));

		final CloudformationSensorConfiguration cloudformationSensorConfiguration = new CloudformationSensorConfiguration(
				configuration);

		final DefaultFileSystem fileSystem = new DefaultFileSystem(
				FileSystems.getDefault().getPath(".").toAbsolutePath());

		final DefaultInputFile inputFile = new TestInputFileBuilder("key",
				"src/test/resources/aws-cross-account-manager-master.yml")
						.setLanguage("java")
						.initMetadata(new String(Files.readAllBytes(
								FileSystems.getDefault().getPath("src/test/resources/aws-cross-account-manager-master.yml"))))
						.setCharset(StandardCharsets.UTF_8).build();
		fileSystem.add(inputFile);

		final CloudformationSensor cloudformationSensor = new CloudformationSensor(cloudformationSensorConfiguration,
				fileSystem, new PathResolver());

		final SensorContext sensorContext = SensorContextTester
				.create(FileSystems.getDefault().getPath(".").toAbsolutePath());
		((DefaultFileSystem) sensorContext.fileSystem()).add(inputFile);
		cloudformationSensor.execute(sensorContext);
	}

	/**
	 * Execute simple nag report missing template test.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void executeSimpleNagReportMissingTemplateTest() throws IOException {
		final Configuration configuration = mock(Configuration.class);
		when(configuration.get(CloudformationConstants.REPORT_FILES_PROPERTY))
				.thenReturn(Optional.of("src/test/resources/aws-cross-account-manager-master.yml.nag"));

		final CloudformationSensorConfiguration cloudformationSensorConfiguration = new CloudformationSensorConfiguration(
				configuration);

		final DefaultFileSystem fileSystem = new DefaultFileSystem(
				FileSystems.getDefault().getPath(".").toAbsolutePath());

		final CloudformationSensor cloudformationSensor = new CloudformationSensor(cloudformationSensorConfiguration,
				fileSystem, new PathResolver());

		final SensorContext sensorContext = SensorContextTester
				.create(FileSystems.getDefault().getPath(".").toAbsolutePath());
		cloudformationSensor.execute(sensorContext);
	}

	/**
	 * Execute simple nag scan report test.
	 * 
	 * @throws IOException
	 */
	@Test
	public void executeSimpleNagScanReportTest() throws IOException {
		final Configuration configuration = mock(Configuration.class);
		when(configuration.get(CloudformationConstants.REPORT_FILES_PROPERTY))
				.thenReturn(Optional.of("src/test/resources/cfn-nag-scan.nagscan"));

		final CloudformationSensorConfiguration cloudformationSensorConfiguration = new CloudformationSensorConfiguration(
				configuration);

		final DefaultFileSystem fileSystem = new DefaultFileSystem(
				FileSystems.getDefault().getPath(".").toAbsolutePath());

		final DefaultInputFile inputFile = new TestInputFileBuilder("key", "src/test/resources/CloudTrailAllAccounts.yml")
				.setLanguage("java")
				.initMetadata(new String(Files.readAllBytes(
						FileSystems.getDefault().getPath("src/test/resources/CloudTrailAllAccounts.yml"))))
				.setCharset(StandardCharsets.UTF_8).build();
		fileSystem.add(inputFile);

		final CloudformationSensor cloudformationSensor = new CloudformationSensor(cloudformationSensorConfiguration,
				fileSystem, new PathResolver());

		final SensorContext sensorContext = SensorContextTester
				.create(FileSystems.getDefault().getPath(".").toAbsolutePath());
		((DefaultFileSystem) sensorContext.fileSystem()).add(inputFile);
		cloudformationSensor.execute(sensorContext);
	}

	/**
	 * Execute simple nag scan report template missing test.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void executeSimpleNagScanReportTemplateMissingTest() throws IOException {
		final Configuration configuration = mock(Configuration.class);
		when(configuration.get(CloudformationConstants.REPORT_FILES_PROPERTY))
				.thenReturn(Optional.of("src/test/resources/cfn-nag-scan.nagscan"));

		final CloudformationSensorConfiguration cloudformationSensorConfiguration = new CloudformationSensorConfiguration(
				configuration);

		final DefaultFileSystem fileSystem = new DefaultFileSystem(
				FileSystems.getDefault().getPath(".").toAbsolutePath());

		final CloudformationSensor cloudformationSensor = new CloudformationSensor(cloudformationSensorConfiguration,
				fileSystem, new PathResolver());

		final SensorContext sensorContext = SensorContextTester
				.create(FileSystems.getDefault().getPath(".").toAbsolutePath());
		cloudformationSensor.execute(sensorContext);
	}

	/**
	 * Execute nag scan report bad property test.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void executeNagScanReportBadPropertyTest() throws IOException {
		final Configuration configuration = mock(Configuration.class);
		when(configuration.get(CloudformationConstants.REPORT_FILES_PROPERTY))
				.thenReturn(Optional.of("src/test/resources/file-not-exist.nagscan"));

		final CloudformationSensorConfiguration cloudformationSensorConfiguration = new CloudformationSensorConfiguration(
				configuration);

		final DefaultFileSystem fileSystem = new DefaultFileSystem(
				FileSystems.getDefault().getPath(".").toAbsolutePath());

		final CloudformationSensor cloudformationSensor = new CloudformationSensor(cloudformationSensorConfiguration,
				fileSystem, new PathResolver());

		final SensorContext sensorContext = SensorContextTester
				.create(FileSystems.getDefault().getPath(".").toAbsolutePath());
		cloudformationSensor.execute(sensorContext);
	}

	
	
	/**
	 * Execute missing property test.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void executeMissingPropertyTest() throws IOException {
		final Configuration configuration = mock(Configuration.class);
		when(configuration.get(CloudformationConstants.REPORT_FILES_PROPERTY))
				.thenReturn(Optional.empty());

		final CloudformationSensorConfiguration cloudformationSensorConfiguration = new CloudformationSensorConfiguration(
				configuration);

		final DefaultFileSystem fileSystem = new DefaultFileSystem(
				FileSystems.getDefault().getPath(".").toAbsolutePath());

		final CloudformationSensor cloudformationSensor = new CloudformationSensor(cloudformationSensorConfiguration,
				fileSystem, new PathResolver());

		final SensorContext sensorContext = SensorContextTester
				.create(FileSystems.getDefault().getPath(".").toAbsolutePath());
		cloudformationSensor.execute(sensorContext);
	}

	
	/**
	 * Execute mixed file report test.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void executeMixedFileReportTest() throws IOException {
		final Configuration configuration = mock(Configuration.class);
		when(configuration.get(CloudformationConstants.REPORT_FILES_PROPERTY))
				.thenReturn(Optional.of("src/test/resources/aws-cross-account-manager-master.yml.nag,src/test/resources/cfn-nag-scan.nagscan"));

		final CloudformationSensorConfiguration cloudformationSensorConfiguration = new CloudformationSensorConfiguration(
				configuration);

		final DefaultFileSystem fileSystem = new DefaultFileSystem(
				FileSystems.getDefault().getPath(".").toAbsolutePath());

		final DefaultInputFile inputFile = new TestInputFileBuilder("key", "src/test/resources/CloudTrailAllAccounts.yml")
				.setLanguage("java")
				.initMetadata(new String(Files.readAllBytes(
						FileSystems.getDefault().getPath("src/test/resources/CloudTrailAllAccounts.yml"))))
				.setCharset(StandardCharsets.UTF_8).build();
		fileSystem.add(inputFile);

		final DefaultInputFile inputFile2 = new TestInputFileBuilder("key",
				"src/test/resources/aws-cross-account-manager-master.yml")
						.setLanguage("java")
						.initMetadata(new String(Files.readAllBytes(
								FileSystems.getDefault().getPath("src/test/resources/aws-cross-account-manager-master.yml"))))
						.setCharset(StandardCharsets.UTF_8).build();
		fileSystem.add(inputFile2);
		
		final CloudformationSensor cloudformationSensor = new CloudformationSensor(cloudformationSensorConfiguration,
				fileSystem, new PathResolver());

		final SensorContext sensorContext = SensorContextTester
				.create(FileSystems.getDefault().getPath(".").toAbsolutePath());
		((DefaultFileSystem) sensorContext.fileSystem()).add(inputFile);
		((DefaultFileSystem) sensorContext.fileSystem()).add(inputFile2);
		cloudformationSensor.execute(sensorContext);
	}

	
}