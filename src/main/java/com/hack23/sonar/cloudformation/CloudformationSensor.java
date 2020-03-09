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
import java.io.FileNotFoundException;
import java.io.IOException;
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

/**
 * The Class CloudformationSensor.
 */
public final class CloudformationSensor implements Sensor {

	/** The Constant SENSOR_NAME. */
	public static final String SENSOR_NAME = "Cloudformation Check";

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Loggers.get(CloudformationSensor.class);

	/** The cfn nag report reader. */
	private final CfnNagReportReader cfnNagReportReader;

	/** The cfn nag scan report reader. */
	private final CfnNagScanReportReader cfnNagScanReportReader;

	/** The configuration. */
	private final CloudformationSensorConfiguration configuration;

	/** The path resolver. */
	private final PathResolver pathResolver;

	/** The file system. */
	private final FileSystem fileSystem;

	/**
	 * Instantiates a new cloudformation sensor.
	 *
	 * @param configuration the configuration
	 * @param fileSystem    the file system
	 * @param pathResolver  the path resolver
	 */
	public CloudformationSensor(final CloudformationSensorConfiguration configuration, final FileSystem fileSystem,
			final PathResolver pathResolver) {
		super();
		this.cfnNagReportReader = new CfnNagReportReader();
		this.cfnNagScanReportReader = new CfnNagScanReportReader();
		this.configuration = configuration;
		this.fileSystem = fileSystem;
		this.pathResolver = pathResolver;
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
		profiler.startInfo("Process cfn-nag reports");

		try {
			final Optional<String> reportFilesProperty = configuration.getReportFiles();

			if (reportFilesProperty.isPresent()) {

				final String reports = reportFilesProperty.get();
				LOGGER.info(CloudformationConstants.REPORT_FILES_PROPERTY + "=" + reports);
				final String[] reportFiles = StringUtils.split(reports, ",");

				for (final String report : reportFiles) {
					LOGGER.info("Processing:" + report);
					if (pathResolver.relativeFile(fileSystem.baseDir(), report).exists() && report.endsWith(".nag")) {

						handleCfnNagReports(context, report);
					} else if (pathResolver.relativeFile(fileSystem.baseDir(), report).exists()
							&& report.endsWith(".nagscan")) {

						handleCfnNagScanReports(context, report);
					} else {
						LOGGER.warn("Processing:" + report + " missing or do not end with .nag or .nagscan");
					}
				}
			} else {
				LOGGER.warn("Missing property:{}",CloudformationConstants.REPORT_FILES_PROPERTY);
			}
		} catch (final IOException e) {
			throw new RuntimeException("Can not process cfn-nag reports.", e);
		} finally {
			profiler.stopInfo();
		}
	}

	/**
	 * Handle cfn nag reports.
	 *
	 * @param context the context
	 * @param report  the report
	 * @throws FileNotFoundException the file not found exception
	 */
	private void handleCfnNagReports(final SensorContext context, final String report) throws FileNotFoundException {
		final String templateName = pathResolver.relativeFile(fileSystem.baseDir(), report).getName().replace(".nag","");

		final InputFile templateInputFile = findTemplate(templateName);

		final CfnNagReport cfnNagReport = cfnNagReportReader
				.readReport(new FileInputStream(pathResolver.relativeFile(fileSystem.baseDir(), report)));
		if (cfnNagReport != null) {
			final List<CfnNagViolation> violations = cfnNagReport.getViolations();
			for (final CfnNagViolation cfnNagViolation : violations) {
				addIssue(context, cfnNagViolation, templateInputFile);
			}
		}
	}

	/**
	 * Handle cfn nag scan reports.
	 *
	 * @param context the context
	 * @param report  the report
	 * @throws FileNotFoundException the file not found exception
	 */
	private void handleCfnNagScanReports(final SensorContext context, final String report)
			throws FileNotFoundException {
		final List<CfnNagScanReport> cfnNagscanReports = cfnNagScanReportReader
				.readReport(new FileInputStream(pathResolver.relativeFile(fileSystem.baseDir(), report)));

		for (final CfnNagScanReport nagScanReport : cfnNagscanReports) {

			final String filename = nagScanReport.getFilename();
			final InputFile templateInputFile = findTemplate(
					filename.substring(filename.lastIndexOf(File.separator) + 1, filename.length()));

			final List<CfnNagViolation> violations = nagScanReport.getFile_results().getViolations();
			for (final CfnNagViolation cfnNagViolation : violations) {
				addIssue(context, cfnNagViolation, templateInputFile);
			}
		}
	}

	/**
	 * Find template.
	 *
	 * @param templateName the template name
	 * @return the input file
	 */
	private InputFile findTemplate(final String templateName) {
		final List<InputFile> potentialReportTargets = new ArrayList<>();
		fileSystem.inputFiles(fileSystem.predicates().all()).forEach(potentialReportTargets::add);
		LOGGER.info("Looking for cloudformation template matching:" + templateName);

		for (final InputFile inputFile : potentialReportTargets) {
			if (templateName.equals(inputFile.filename())) {
				LOGGER.info("matching:" + templateName + " = " + inputFile.filename());
				return inputFile;
			}
		}
		return null;
	}

	/**
	 * Adds the issue.
	 *
	 * @param context           the context
	 * @param violation         the violation
	 * @param templateInputFile the template input file
	 */
	private static void addIssue(final SensorContext context, final CfnNagViolation violation,
			final InputFile templateInputFile) {
		if (CloudformationQualityProfile.hasRule(violation.getId())) {
			if (templateInputFile != null) {

				if (violation.getLine_numbers().isEmpty()) {
					context.newIssue().forRule(RuleKey.of(CloudformationRulesDefinition.REPO_KEY, violation.getId()))
							.at(new DefaultIssueLocation().on(templateInputFile).message(violation.getMessage()))
							.save();
				} else {
					final List<Integer> line_numbers = violation.getLine_numbers();
					for (final Integer line : line_numbers) {
						if (line != null && line >= 0) {
							context.newIssue()
									.forRule(RuleKey.of(CloudformationRulesDefinition.REPO_KEY, violation.getId()))
									.at(new DefaultIssueLocation().on(templateInputFile).message(violation.getMessage())
											.at(templateInputFile.selectLine(line)))
									.save();
						} else {
							context.newIssue().forRule(RuleKey.of(CloudformationRulesDefinition.REPO_KEY, violation.getId()))
							.at(new DefaultIssueLocation().on(templateInputFile).message(violation.getMessage()))
							.save();							
						}
					}
				}
			} else {
				context.newIssue().forRule(RuleKey.of(CloudformationRulesDefinition.REPO_KEY, violation.getId()))
						.at(new DefaultIssueLocation().on(context.project()).message(violation.getMessage())).save();
			}
		} else {
			LOGGER.warn("Rule not supported {}:{}", violation.getId(), violation.getMessage());
		}
	}
}