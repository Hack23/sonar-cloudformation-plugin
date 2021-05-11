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
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.sonar.api.batch.fs.internal.DefaultFileSystem;
import org.sonar.api.batch.fs.internal.DefaultInputFile;
import org.sonar.api.batch.fs.internal.TestInputFileBuilder;
import org.sonar.api.batch.sensor.internal.SensorContextTester;
import org.sonar.api.scan.filesystem.PathResolver;

/**
 * The Class CfnNagProcessReportsTest.
 */
public class CfnNagProcessReportsTest extends Assert {


	/**
	 * Execute simple nag report test.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void executeSimpleNagReportTest() throws IOException {
		final DefaultFileSystem fileSystem = new DefaultFileSystem(
				FileSystems.getDefault().getPath(".").toAbsolutePath());

		final DefaultInputFile inputFile = new TestInputFileBuilder("key",
				"src/test/resources/aws-cross-account-manager-master.yml")
						.setLanguage("yaml")
						.initMetadata(new String(Files.readAllBytes(
								FileSystems.getDefault().getPath("src/test/resources/aws-cross-account-manager-master.yml"))))
						.setCharset(StandardCharsets.UTF_8).build();
		fileSystem.add(inputFile);

		final CfnNagProcessReports cloudformationSensor = new CfnNagProcessReports(fileSystem, new PathResolver());

		final SensorContextTester sensorContext = SensorContextTester
				.create(FileSystems.getDefault().getPath(".").toAbsolutePath());
		((DefaultFileSystem) sensorContext.fileSystem()).add(inputFile);
		cloudformationSensor.processCfnNagReport(sensorContext,Optional.of("src/test/resources/aws-cross-account-manager-master.yml.nag"));
		assertFalse(sensorContext.allIssues().isEmpty());
		assertEquals(44,sensorContext.allIssues().size());

	}


	/**
	 * Execute simple nag report missing template test.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void executeSimpleNagReportMissingTemplateTest() throws IOException {
		final DefaultFileSystem fileSystem = new DefaultFileSystem(
				FileSystems.getDefault().getPath(".").toAbsolutePath());

		final CfnNagProcessReports cloudformationSensor = new CfnNagProcessReports(fileSystem, new PathResolver());

		final SensorContextTester sensorContext = SensorContextTester
				.create(FileSystems.getDefault().getPath(".").toAbsolutePath());
		cloudformationSensor.processCfnNagReport(sensorContext,Optional.of("src/test/resources/aws-cross-account-manager-master.yml.nag"));
		assertFalse(sensorContext.allIssues().isEmpty());
		assertEquals(13,sensorContext.allIssues().size());
	}

	/**
	 * Execute simple nag scan report test.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void executeSimpleNagScanReportTest() throws IOException {
		final DefaultFileSystem fileSystem = new DefaultFileSystem(
				FileSystems.getDefault().getPath(".").toAbsolutePath());

		final DefaultInputFile inputFile = new TestInputFileBuilder("key", "src/test/resources/CloudTrailAllAccounts.yml")
				.setLanguage("yaml")
				.initMetadata(new String(Files.readAllBytes(
						FileSystems.getDefault().getPath("src/test/resources/CloudTrailAllAccounts.yml"))))
				.setCharset(StandardCharsets.UTF_8).build();
		fileSystem.add(inputFile);

		final CfnNagProcessReports cloudformationSensor = new CfnNagProcessReports(fileSystem, new PathResolver());

		final SensorContextTester sensorContext = SensorContextTester
				.create(FileSystems.getDefault().getPath(".").toAbsolutePath());
		((DefaultFileSystem) sensorContext.fileSystem()).add(inputFile);
		cloudformationSensor.processCfnNagReport(sensorContext,Optional.of("src/test/resources/cfn-nag-scan.nagscan"));
		assertFalse(sensorContext.allIssues().isEmpty());
		assertEquals(6,sensorContext.allIssues().size());
	}

	/**
	 * Execute simple nag scan report template missing test.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void executeSimpleNagScanReportTemplateMissingTest() throws IOException {
		final DefaultFileSystem fileSystem = new DefaultFileSystem(
				FileSystems.getDefault().getPath(".").toAbsolutePath());

		final CfnNagProcessReports cloudformationSensor = new CfnNagProcessReports(fileSystem, new PathResolver());

		final SensorContextTester sensorContext = SensorContextTester
				.create(FileSystems.getDefault().getPath(".").toAbsolutePath());
		cloudformationSensor.processCfnNagReport(sensorContext,Optional.of("src/test/resources/cfn-nag-scan.nagscan"));
		assertFalse(sensorContext.allIssues().isEmpty());
		assertEquals(6,sensorContext.allIssues().size());
	}

	/**
	 * Execute nag scan report bad property test.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void executeNagScanReportBadPropertyTest() throws IOException {
		final DefaultFileSystem fileSystem = new DefaultFileSystem(
				FileSystems.getDefault().getPath(".").toAbsolutePath());

		final CfnNagProcessReports cloudformationSensor = new CfnNagProcessReports(fileSystem, new PathResolver());

		final SensorContextTester sensorContext = SensorContextTester
				.create(FileSystems.getDefault().getPath(".").toAbsolutePath());
		cloudformationSensor.processCfnNagReport(sensorContext,Optional.of("src/test/resources/file-not-exist.nagscan"));
		assertTrue(sensorContext.allIssues().isEmpty());
	}



	/**
	 * Execute missing property test.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void executeMissingPropertyTest() throws IOException {
		final DefaultFileSystem fileSystem = new DefaultFileSystem(
				FileSystems.getDefault().getPath(".").toAbsolutePath());

		final CfnNagProcessReports cloudformationSensor = new CfnNagProcessReports(fileSystem, new PathResolver());

		final SensorContextTester sensorContext = SensorContextTester
				.create(FileSystems.getDefault().getPath(".").toAbsolutePath());
		cloudformationSensor.processCfnNagReport(sensorContext,Optional.empty());
		assertTrue(sensorContext.allIssues().isEmpty());
	}


	/**
	 * Execute mixed file report test.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void executeMixedFileReportTest() throws IOException {
		final DefaultFileSystem fileSystem = new DefaultFileSystem(
				FileSystems.getDefault().getPath(".").toAbsolutePath());

		final DefaultInputFile inputFile = new TestInputFileBuilder("key", "src/test/resources/CloudTrailAllAccounts.yml")
				.setLanguage("yaml")
				.initMetadata(new String(Files.readAllBytes(
						FileSystems.getDefault().getPath("src/test/resources/CloudTrailAllAccounts.yml"))))
				.setCharset(StandardCharsets.UTF_8).build();
		fileSystem.add(inputFile);

		final DefaultInputFile inputFile2 = new TestInputFileBuilder("key",
				"src/test/resources/aws-cross-account-manager-master.yml")
						.setLanguage("yaml")
						.initMetadata(new String(Files.readAllBytes(
								FileSystems.getDefault().getPath("src/test/resources/aws-cross-account-manager-master.yml"))))
						.setCharset(StandardCharsets.UTF_8).build();
		fileSystem.add(inputFile2);

		final CfnNagProcessReports cloudformationSensor = new CfnNagProcessReports(fileSystem, new PathResolver());

		final SensorContextTester sensorContext = SensorContextTester
				.create(FileSystems.getDefault().getPath(".").toAbsolutePath());
		((DefaultFileSystem) sensorContext.fileSystem()).add(inputFile);
		((DefaultFileSystem) sensorContext.fileSystem()).add(inputFile2);
		cloudformationSensor.processCfnNagReport(sensorContext,Optional.of("src/test/resources/aws-cross-account-manager-master.yml.nag,src/test/resources/cfn-nag-scan.nagscan"));
		assertFalse(sensorContext.allIssues().isEmpty());
		assertEquals(58,sensorContext.allIssues().size());
	}


	/**
	 * Execute file report with custom rules test.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void executeFileReportWithCustomRulesTest() throws IOException {
		final DefaultFileSystem fileSystem = new DefaultFileSystem(
				FileSystems.getDefault().getPath(".").toAbsolutePath());

		final DefaultInputFile inputFile = new TestInputFileBuilder("key", "src/test/resources/CloudTrailAllAccounts.yml")
				.setLanguage("yaml")
				.initMetadata(new String(Files.readAllBytes(
						FileSystems.getDefault().getPath("src/test/resources/CloudTrailAllAccounts.yml"))))
				.setCharset(StandardCharsets.UTF_8).build();
		fileSystem.add(inputFile);

		final DefaultInputFile inputFile2 = new TestInputFileBuilder("key",
				"src/test/resources/aws-cross-account-manager-master.yml")
						.setLanguage("yaml")
						.initMetadata(new String(Files.readAllBytes(
								FileSystems.getDefault().getPath("src/test/resources/aws-cross-account-manager-master.yml"))))
						.setCharset(StandardCharsets.UTF_8).build();
		fileSystem.add(inputFile2);

		final CfnNagProcessReports cloudformationSensor = new CfnNagProcessReports(fileSystem, new PathResolver());

		final SensorContextTester sensorContext = SensorContextTester
				.create(FileSystems.getDefault().getPath(".").toAbsolutePath());
		((DefaultFileSystem) sensorContext.fileSystem()).add(inputFile);
		((DefaultFileSystem) sensorContext.fileSystem()).add(inputFile2);
		cloudformationSensor.processCfnNagReport(sensorContext,Optional.of("src/test/resources/cfn-nag-scan-custom-rules.nagscan"));
		assertFalse(sensorContext.allIssues().isEmpty());
		assertEquals(14,sensorContext.allIssues().size());
	}

	/**
	 * Execute file report with missing line numbers test.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void executeFileReportWithMissingLineNumbersTest() throws IOException {
		final DefaultFileSystem fileSystem = new DefaultFileSystem(
				FileSystems.getDefault().getPath(".").toAbsolutePath());

		final DefaultInputFile inputFile = new TestInputFileBuilder("key", "src/test/resources/CloudTrailAllAccounts.yml")
				.setLanguage("yaml")
				.initMetadata(new String(Files.readAllBytes(
						FileSystems.getDefault().getPath("src/test/resources/CloudTrailAllAccounts.yml"))))
				.setCharset(StandardCharsets.UTF_8).build();
		fileSystem.add(inputFile);

		final DefaultInputFile inputFile2 = new TestInputFileBuilder("key",
				"src/test/resources/aws-cross-account-manager-master.yml")
						.setLanguage("yaml")
						.initMetadata(new String(Files.readAllBytes(
								FileSystems.getDefault().getPath("src/test/resources/aws-cross-account-manager-master.yml"))))
						.setCharset(StandardCharsets.UTF_8).build();
		fileSystem.add(inputFile2);

		final CfnNagProcessReports cloudformationSensor = new CfnNagProcessReports(fileSystem, new PathResolver());

		final SensorContextTester sensorContext = SensorContextTester
				.create(FileSystems.getDefault().getPath(".").toAbsolutePath());
		((DefaultFileSystem) sensorContext.fileSystem()).add(inputFile);
		((DefaultFileSystem) sensorContext.fileSystem()).add(inputFile2);
		cloudformationSensor.processCfnNagReport(sensorContext,Optional.of("src/test/resources/cfn-nag-scan-missing-linenumbers.nagscan"));
		assertFalse(sensorContext.allIssues().isEmpty());
		assertEquals(8,sensorContext.allIssues().size());
	}

}