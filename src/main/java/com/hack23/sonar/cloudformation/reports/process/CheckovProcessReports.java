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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
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
public final class CheckovProcessReports {

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
	public void processCheckovReport(final SensorContext context,final Optional<String> reportFilesProperty) throws IOException, FileNotFoundException {
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

		for (final CheckovPassedCheck failedChecks : checkovReport.getResults().getFailed_checks()) {

			final String filename = failedChecks.getFile_path();

			final InputFile templateInputFile = findTemplate(
					filename.substring(filename.lastIndexOf(File.separator) + 1, filename.length()), filename);

			addCheckovIssue(context, checkovReport, failedChecks, templateInputFile);
		}
	}

	/**
	 * Adds the checkov issue.
	 *
	 * @param context the context
	 * @param checkovReport the checkov report
	 * @param failedChecks the failed checks
	 * @param templateInputFile the template input file
	 */
	private void addCheckovIssue(final SensorContext context, final CheckovReport checkovReport,
			final CheckovPassedCheck failedChecks, final InputFile templateInputFile) {
		if (templateInputFile != null) {

			final List<Integer> line_numbers = failedChecks.getFile_line_range();
			for (final Integer line : line_numbers) {
				context.newIssue()
						.forRule(RuleKey.of("cfn-" + templateInputFile.language(),
								checkovReport.getCheck_type() + "-" + failedChecks.getCheck_id()))
						.at(new DefaultIssueLocation().on(templateInputFile).message(failedChecks.getCheck_name())
								.at(templateInputFile.selectLine(line)))
						.save();
			}
		} else {
			context.newIssue()
					.forRule(RuleKey.of("cfn-" + "yaml",
							checkovReport.getCheck_type() + "-" + failedChecks.getCheck_id()))
					.at(new DefaultIssueLocation().on(context.project()).message(failedChecks.getCheck_name())).save();
		}
	}

	/**
	 * Find template.
	 *
	 * @param templateName the template name
	 * @param filepath the filepath
	 * @return the input file
	 */
	private InputFile findTemplate(final String templateName, final String filepath) {
		final List<InputFile> potentialReportTargets = new ArrayList<>();
		fileSystem.inputFiles(fileSystem.predicates().all()).forEach(potentialReportTargets::add);
		final String filterPath = filterPath(filepath);
		LOGGER.info("Looking for cloudformation template matching filename:{} , path: {}", templateName, filterPath);

		for (final InputFile inputFile : potentialReportTargets) {
			if (templateName.equals(inputFile.filename()) && inputFile.uri().toString().contains(filterPath)) {
				LOGGER.info("matching path:{}" + inputFile.uri());
				LOGGER.info("matching filename:" + templateName + " = " + inputFile.filename());

				return inputFile;
			}
		}
		return null;
	}

	/**
	 * Filter path.
	 *
	 * @param filepath the filepath
	 * @return the string
	 */
	private static String filterPath(final String filepath) {
		return filepath.replace("." + File.separator, "").replace(".." + File.separator, "");
	}


}