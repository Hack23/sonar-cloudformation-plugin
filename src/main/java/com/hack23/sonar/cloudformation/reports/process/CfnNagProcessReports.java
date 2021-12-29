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
import org.sonar.api.batch.rule.ActiveRules;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.scan.filesystem.PathResolver;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

import com.hack23.sonar.cloudformation.reports.cfnnag.CfnNagScanReport;
import com.hack23.sonar.cloudformation.reports.cfnnag.CfnNagViolation;

/**
 * The Class CfnNagProcessReports.
 */
public final class CfnNagProcessReports extends AbstractProcessReports {

	/** The Constant UNDEFINED_FAILURE. */
	public static final String UNDEFINED_FAILURE = "FUNDEFINED";

	/** The Constant UNDEFINED_WARNING. */
	public static final String UNDEFINED_WARNING = "WUNDEFINED";

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Loggers.get(CfnNagProcessReports.class);

	/** The cfn nag scan report reader. */
	private final CfnNagScanReportReader cfnNagScanReportReader;

	/** The path resolver. */
	private final PathResolver pathResolver;

	/** The file system. */
	private final FileSystem fileSystem;


	/**
	 * Instantiates a new cfn nag process reports.
	 *
	 * @param fileSystem the file system
	 * @param pathResolver the path resolver
	 */
	public CfnNagProcessReports(final FileSystem fileSystem, final PathResolver pathResolver) {
		super();
		this.cfnNagScanReportReader = new CfnNagScanReportReader();
		this.fileSystem = fileSystem;
		this.pathResolver = pathResolver;
	}

	/**
	 * Process cfn nag report.
	 *
	 * @param context the context
	 * @param reportFilesProperty the report files property
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void processCfnNagReport(final SensorContext context, final Optional<String> reportFilesProperty)
			throws IOException {
		if (reportFilesProperty.isPresent()) {

			final String reports = reportFilesProperty.get();
			final String[] reportFiles = StringUtils.split(reports, ",");

			for (final String report : reportFiles) {
				LOGGER.info("Processing cfn-nag :" + report);
				if (pathResolver.relativeFile(fileSystem.baseDir(), report).exists()) {

					handleCfnNagScanReports(context, report);
				} else {
					LOGGER.warn("Processing cfn-nag:" + report + " missing");
				}
			}
		} else {
			LOGGER.warn("Missing property:{}", CloudformationConstants.CFN_NAG_REPORT_FILES_PROPERTY);
		}
	}

	/**
	 * Handle cfn nag scan reports.
	 *
	 * @param context the context
	 * @param report the report
	 * @throws IOException 
	 */
	private void handleCfnNagScanReports(final SensorContext context, final String report)
			throws IOException {
		LOGGER.info("Reading cfn-nag reports:{}", report);
		final List<CfnNagScanReport> cfnNagscanReports = cfnNagScanReportReader
				.readReport(Files.newInputStream(pathResolver.relativeFile(fileSystem.baseDir(), report).toPath()));

		for (final CfnNagScanReport nagScanReport : cfnNagscanReports) {

			final String filename = nagScanReport.getFilename();
			LOGGER.info("Cfn-nag scanned file :{}", filename);

			final InputFile templateInputFile = findTemplate(fileSystem,
					filename.substring(filename.lastIndexOf(File.separator) + 1, filename.length()), filename);

			final List<CfnNagViolation> violations = nagScanReport.getFileResults().getViolations();
			for (final CfnNagViolation cfnNagViolation : violations) {
				addIssue(context, cfnNagViolation, templateInputFile);
			}
		}
	}

	/**
	 * Adds the issue.
	 *
	 * @param context the context
	 * @param violation the violation
	 * @param templateInputFile the template input file
	 */
	private static void addIssue(final SensorContext context, final CfnNagViolation violation,
			final InputFile templateInputFile) {
		final ActiveRules activeRules = context.activeRules();

		if (templateInputFile != null) {

			if (violation.getLineNumbers().isEmpty()) {
				NewIssue newIssue = context.newIssue().forRule(RuleKey.of("cfn-" + templateInputFile.language(), findRuleId(activeRules, violation)));				
				NewIssueLocation location = newIssue.newLocation()
			            .on(templateInputFile)
			            .message(violation.getMessage());
			    newIssue.at(location).save(); 
						
			} else {
				final List<Integer> lineNumbers = violation.getLineNumbers();
				for (final Integer line : lineNumbers) {
					if (line != null && line >= 0) {
						NewIssue newIssue = context.newIssue().forRule(RuleKey.of("cfn-" + templateInputFile.language(), findRuleId(activeRules, violation)));				
						NewIssueLocation location = newIssue.newLocation()
					            .on(templateInputFile).at(templateInputFile.selectLine(line))
					            .message(violation.getMessage());
					    newIssue.at(location).save(); 
						
					} else {
						NewIssue newIssue = context.newIssue().forRule(RuleKey.of("cfn-" + templateInputFile.language(), findRuleId(activeRules, violation)));				
						NewIssueLocation location = newIssue.newLocation()
					            .on(templateInputFile)
					            .message(violation.getMessage());
					    newIssue.at(location).save(); 
					}
				}
			}
		} else {
			NewIssue newIssue = context.newIssue().forRule(RuleKey.of("cfn-yaml", findRuleId(activeRules, violation)));				
			NewIssueLocation location = newIssue.newLocation()
		            .on(context.project())
		            .message(violation.getMessage());
		    newIssue.at(location).save(); 
		}
	}

	/**
	 * Find rule id.
	 *
	 * @param violation the violation
	 * @return the string
	 */
	private static String findRuleId(final ActiveRules activeRules, final CfnNagViolation violation) {
		RuleKey ruleKeyYaml = RuleKey.of("cfn-yaml", violation.getId());
		RuleKey ruleKeyJson = RuleKey.of("cfn-json", violation.getId());
		
		if (activeRules.find(ruleKeyYaml) != null || activeRules.find(ruleKeyJson) != null) {
			return violation.getId();
		} else {
			if (violation.getId().startsWith("W")) {
				return UNDEFINED_WARNING;
			} else {
				return UNDEFINED_FAILURE;
			}
		}
	}

}