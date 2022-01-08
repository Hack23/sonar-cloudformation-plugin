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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.TextPointer;
import org.sonar.api.batch.rule.ActiveRules;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.scan.filesystem.PathResolver;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

import com.hack23.sonar.cloudformation.reports.checkov.CheckovPassedCheck;
import com.hack23.sonar.cloudformation.reports.checkov.CheckovReport;

/**
 * The Class CheckovProcessReports.
 */
public final class CheckovProcessReports extends AbstractProcessReports {

	/** The Constant SENSOR_NAME. */
	public static final String SENSOR_NAME = "Cloudformation Check";

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Loggers.get(CheckovProcessReports.class);

	/** The checkov report reader. */
	private final CheckovReportReader checkovReportReader;

	/** The path resolver. */
	private final PathResolver pathResolver;

	/** The file system. */
	private final FileSystem fileSystem;

	/**
	 * Instantiates a new checkov process reports.
	 *
	 * @param fileSystem the file system
	 * @param pathResolver the path resolver
	 */
	public CheckovProcessReports(final FileSystem fileSystem,
			final PathResolver pathResolver) {
		super();
		this.checkovReportReader = new CheckovReportReader();
		this.fileSystem = fileSystem;
		this.pathResolver = pathResolver;
	}

	/**
	 * Process checkov report.
	 *
	 * @param context the context
	 * @param reportFilesProperty the report files property
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void processCheckovReport(final SensorContext context,final Optional<String> reportFilesProperty) throws IOException {
		if (reportFilesProperty.isPresent()) {

			final String reports = reportFilesProperty.get();
			LOGGER.info(CloudformationConstants.CHECKOV_REPORT_FILES_PROPERTY + "=" + reports);
			final String[] reportFiles = StringUtils.split(reports, ",");

			for (final String report : reportFiles) {
				LOGGER.info("Processing  checkov :" + report);
				if (pathResolver.relativeFile(fileSystem.baseDir(), report).exists()) {
					handleCheckovReports(context, report);
				} else {
					LOGGER.warn("Processing checkov:" + report + " missing");
				}
			}
		} else {
			LOGGER.warn("Missing property:{}", CloudformationConstants.CHECKOV_REPORT_FILES_PROPERTY);
		}
	}


	/**
	 * Handle checkov reports.
	 *
	 * @param context the context
	 * @param report the report
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void handleCheckovReports(final SensorContext context, final String report) throws IOException {
		LOGGER.info("Reading checkov reports:{}", report);

		final List<CheckovReport> checkovReportList = checkovReportReader
				.readReport(Files.newInputStream(pathResolver.relativeFile(fileSystem.baseDir(), report).toPath()));


		for (final CheckovReport checkovReport : checkovReportList) {

			final ActiveRules activeRules = context.activeRules();
			for (final CheckovPassedCheck failedChecks : checkovReport.getResults().getFailedChecks()) {
				final String filename = failedChecks.getFilePath();
				LOGGER.info("Checkov scanned file :{}", filename);

				final InputFile templateInputFile = findTemplate(fileSystem,
						filename.substring(filename.lastIndexOf(File.separator) + 1), filename);

				addCheckovIssue(context, activeRules, checkovReport, failedChecks, templateInputFile);
			}
		}
	}

	/**
	 * Adds the checkov issue.
	 *
	 * @param context the context
	 * @param activeRules the active rules
	 * @param checkovReport the checkov report
	 * @param failedChecks the failed checks
	 * @param templateInputFile the template input file
	 */
	private static void addCheckovIssue(final SensorContext context, final ActiveRules activeRules, final CheckovReport checkovReport,
			final CheckovPassedCheck failedChecks, final InputFile templateInputFile) {

		String repoName = "cloudformation-plugin-cfn";
		if (templateInputFile != null && "terraform".equalsIgnoreCase(templateInputFile.language())) {
			repoName = "cloudformation-plugin-terraform";
		}

		final RuleKey ruleKey = RuleKey.of(repoName,checkovReport.getCheckType() + "-" + failedChecks.getCheckId());

		if (activeRules.find(ruleKey) == null) {
			LOGGER.warn("No active checkov rule detected for:'{}' with key {} detected in {}",failedChecks.getCheckName(), ruleKey,failedChecks.getFilePath());
			return;
		}

		if (templateInputFile == null) {
			LOGGER.warn("File not found {} for rule {} issue not created", failedChecks.getFilePath(), ruleKey);
			return;
		}

		final List<Integer> lineNumbers = failedChecks.getFileLineRange();
		if(!lineNumbers.isEmpty()) {
			final TextPointer startLine = templateInputFile.selectLine(lineNumbers.get(0)).start();
			final TextPointer endLine = templateInputFile.selectLine(lineNumbers.get(lineNumbers.size()-1)).end();
			final NewIssue newIssue = context.newIssue().forRule(ruleKey);

			final NewIssueLocation location = newIssue.newLocation()
		            .on(templateInputFile).at(templateInputFile.newRange(startLine, endLine))
		            .message(failedChecks.getCheckName());
		    newIssue.at(location).save();
		}
	}


}