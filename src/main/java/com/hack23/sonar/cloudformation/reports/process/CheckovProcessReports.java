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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.rule.ActiveRules;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.issue.internal.DefaultIssueLocation;
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
	 * @throws FileNotFoundException the file not found exception
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
	 * @throws FileNotFoundException the file not found exception
	 */
	private void handleCheckovReports(final SensorContext context, final String report) throws FileNotFoundException {
		LOGGER.info("Reading checkov reports:{}", report);

		final CheckovReport checkovReport = checkovReportReader
				.readReport(new FileInputStream(pathResolver.relativeFile(fileSystem.baseDir(), report)));

		final ActiveRules activeRules = context.activeRules();
		for (final CheckovPassedCheck failedChecks : checkovReport.getResults().getFailedChecks()) {			
			final String filename = failedChecks.getFile_path();
			LOGGER.info("Checkov scanned file :{}", filename);

			final InputFile templateInputFile = findTemplate(fileSystem, filename.substring(filename.lastIndexOf(File.separator) + 1, filename.length()), filename);

			addCheckovIssue(context, activeRules, checkovReport, failedChecks, templateInputFile);
		}
	}

	/**
	 * Adds the checkov issue.
	 *
	 * @param context the context
	 * @param activeRules 
	 * @param checkovReport the checkov report
	 * @param failedChecks the failed checks
	 * @param templateInputFile the template input file
	 */
	private static void addCheckovIssue(final SensorContext context, final ActiveRules activeRules, final CheckovReport checkovReport,
			final CheckovPassedCheck failedChecks, final InputFile templateInputFile) {

		String repoName = "cfn-yaml";
		if (templateInputFile != null) {
			repoName = "cfn-" + templateInputFile.language();
		}
		
		final RuleKey ruleKey = RuleKey.of(repoName,checkovReport.getCheckType() + "-" + failedChecks.getCheck_id());
	
		if (activeRules.find(ruleKey) == null) {
			LOGGER.warn("No active checkov rule detected for:'{}' with key {} detected in {}",failedChecks.getCheck_name(), ruleKey,failedChecks.getFile_path());
			return;
		}
		
		if (templateInputFile == null) {
			LOGGER.warn("File not found {} for rule {} issue not created", failedChecks.getFile_path(), ruleKey);
			return;
		}

		final List<Integer> line_numbers = failedChecks.getFile_line_range();
		for (final Integer line : line_numbers) {
			context.newIssue().forRule(ruleKey).at(new DefaultIssueLocation().on(templateInputFile)
					.message(failedChecks.getCheck_name()).at(templateInputFile.selectLine(line))).save();
		}
	}


}