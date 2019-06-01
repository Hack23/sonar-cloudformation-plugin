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

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.batch.sensor.issue.internal.DefaultIssueLocation;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.scan.filesystem.PathResolver;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.api.utils.log.Profiler;

import com.hack23.sonar.cloudformation.parser.CfnNagReport;
import com.hack23.sonar.cloudformation.parser.CfnNagReportReader;
import com.hack23.sonar.cloudformation.parser.CfnNagScanReport;
import com.hack23.sonar.cloudformation.parser.CfnNagScanReportReader;
import com.hack23.sonar.cloudformation.parser.CfnNagViolation;

public class CloudformationSensor implements Sensor {

	private static final String SENSOR_NAME = "Cloudformation Check";
	private static final Logger LOGGER = Loggers.get(CloudformationSensor.class);

	private final CfnNagReportReader cfnNagReportReader;
	private final CfnNagScanReportReader cfnNagScanReportReader;
	private final CloudformationSensorConfiguration configuration;
	private final PathResolver pathResolver;
	private final FileSystem fileSystem;

	public CloudformationSensor(final CloudformationSensorConfiguration configuration, final FileSystem fileSystem,
			final PathResolver pathResolver) {
		super();
		this.cfnNagReportReader = new CfnNagReportReader();
		this.cfnNagScanReportReader = new CfnNagScanReportReader();
		this.configuration = configuration;
		this.fileSystem = fileSystem;
		this.pathResolver = pathResolver;
	}

	@Override
	public void describe(final SensorDescriptor descriptor) {
		descriptor.name(SENSOR_NAME);

	}

	@Override
	public void execute(final SensorContext context) {
		final Profiler profiler = Profiler.create(LOGGER);
		profiler.startInfo("Process cfn-nag reports");

		try {
			final Optional<String> reportFilesProperty = configuration.getReportFiles();

			if (reportFilesProperty.isPresent()) {

				String reports = reportFilesProperty.get();

				String[] reportFiles = StringUtils.split(reports, ",");

				for (String report : reportFiles) {

					if (pathResolver.relativeFile(fileSystem.baseDir(), report).exists() && report.endsWith(".nag")) {

						String templateName = pathResolver.relativeFile(fileSystem.baseDir(), report).getName()
								.replace(".nag", "");

						InputFile templateInputFile = findTemplate(templateName);

						final CfnNagReport cfnNagReport = cfnNagReportReader.readReport(
								new FileInputStream(pathResolver.relativeFile(fileSystem.baseDir(), report)));
						if (cfnNagReport != null) {
							final List<CfnNagViolation> violations = cfnNagReport.getViolations();
							for (final CfnNagViolation cfnNagViolation : violations) {
								addIssue(context, cfnNagViolation, templateInputFile);
							}
						}
					} else if (pathResolver.relativeFile(fileSystem.baseDir(), report).exists()
							&& report.endsWith(".nagscan")) {

						final List<CfnNagScanReport> cfnNagscanReports = cfnNagScanReportReader.readReport(
								new FileInputStream(pathResolver.relativeFile(fileSystem.baseDir(), report)));
						
						for (CfnNagScanReport nagScanReport : cfnNagscanReports) {
														
							String filename = nagScanReport.getFilename();
							InputFile templateInputFile = findTemplate(filename.substring(filename.lastIndexOf(File.separator),filename.length()));
							
							final List<CfnNagViolation> violations = nagScanReport.getFile_results().getViolations();
							for (final CfnNagViolation cfnNagViolation : violations) {
								addIssue(context, cfnNagViolation, templateInputFile);
							}
						}
					}
				}
			}
		} catch (final Exception e) {
			throw new RuntimeException("Can not process cfn-nag reports.", e);
		} finally {
			profiler.stopInfo();
		}
	}

	private InputFile findTemplate(String templateName) {
		List<InputFile> potentialReportTargets = new ArrayList<>();
		fileSystem.inputFiles(fileSystem.predicates().all()).forEach(potentialReportTargets::add);
		LOGGER.info("Looking for cloudformation template matching:" + templateName);

		for (InputFile inputFile : potentialReportTargets) {
			if (templateName.equals(inputFile.filename())) {
				LOGGER.warn("matching:" + templateName + " = " + inputFile.filename());
				return inputFile;
			}
		}
		return null;
	}

	private void addIssue(final SensorContext context, final CfnNagViolation violation, InputFile templateInputFile) {
		if (templateInputFile != null) {

			if (violation.getLine_numbers().isEmpty()) {
				context.newIssue().forRule(RuleKey.of(CloudformationRulesDefinition.REPO_KEY, violation.getId()))
						.at(new DefaultIssueLocation().on(templateInputFile).message(violation.getMessage())).save();
			} else {
				List<Integer> line_numbers = violation.getLine_numbers();
				for (Integer line : line_numbers) {
					context.newIssue().forRule(RuleKey.of(CloudformationRulesDefinition.REPO_KEY, violation.getId()))
							.at(new DefaultIssueLocation().on(templateInputFile).message(violation.getMessage())
									.at(templateInputFile.selectLine(line)))
							.save();
				}
			}
		} else {
			context.newIssue().forRule(RuleKey.of(CloudformationRulesDefinition.REPO_KEY, violation.getId()))
					.at(new DefaultIssueLocation().on(context.project()).message(violation.getMessage())).save();
		}
	}
}