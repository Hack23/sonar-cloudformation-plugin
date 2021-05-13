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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.issue.internal.DefaultIssueLocation;
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

	/** The Constant SUPPORTED_RULES. */
	public static final Set<String> SUPPORTED_RULES = new HashSet<>();

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

	static {
		SUPPORTED_RULES.add("F1");
		SUPPORTED_RULES.add("F2");
		SUPPORTED_RULES.add("F3");
		SUPPORTED_RULES.add("F4");
		SUPPORTED_RULES.add("F5");
		SUPPORTED_RULES.add("F6");
		SUPPORTED_RULES.add("F7");
		SUPPORTED_RULES.add("F8");
		SUPPORTED_RULES.add("F9");
		SUPPORTED_RULES.add("F10");
		SUPPORTED_RULES.add("F11");
		SUPPORTED_RULES.add("F12");
		SUPPORTED_RULES.add("F13");
		SUPPORTED_RULES.add("F14");
		SUPPORTED_RULES.add("F15");
		SUPPORTED_RULES.add("F16");
		SUPPORTED_RULES.add("F18");
		SUPPORTED_RULES.add("F19");
		SUPPORTED_RULES.add("F20");
		SUPPORTED_RULES.add("F21");
		SUPPORTED_RULES.add("F22");
		SUPPORTED_RULES.add("F23");
		SUPPORTED_RULES.add("F24");
		SUPPORTED_RULES.add("F25");
		SUPPORTED_RULES.add("F26");
		SUPPORTED_RULES.add("F27");
		SUPPORTED_RULES.add("F28");
		SUPPORTED_RULES.add("F29");
		SUPPORTED_RULES.add("F30");
		SUPPORTED_RULES.add("F31");
		SUPPORTED_RULES.add("F32");
		SUPPORTED_RULES.add("F33");
		SUPPORTED_RULES.add("F34");
		SUPPORTED_RULES.add("F35");
		SUPPORTED_RULES.add("F36");
		SUPPORTED_RULES.add("F37");
		SUPPORTED_RULES.add("F38");
		SUPPORTED_RULES.add("F39");
		SUPPORTED_RULES.add("F40");
		SUPPORTED_RULES.add("F41");
		SUPPORTED_RULES.add("F42");
		SUPPORTED_RULES.add("F43");
		SUPPORTED_RULES.add("F44");
		SUPPORTED_RULES.add("F45");
		SUPPORTED_RULES.add("F46");
		SUPPORTED_RULES.add("F47");
		SUPPORTED_RULES.add("F48");
		SUPPORTED_RULES.add("F49");
		SUPPORTED_RULES.add("F50");
		SUPPORTED_RULES.add("F51");
		SUPPORTED_RULES.add("F52");
		SUPPORTED_RULES.add("F53");
		SUPPORTED_RULES.add("F54");
		SUPPORTED_RULES.add("F55");
		SUPPORTED_RULES.add("F56");
		SUPPORTED_RULES.add("F57");
		SUPPORTED_RULES.add("F58");
		SUPPORTED_RULES.add("F60");
		SUPPORTED_RULES.add("F61");
		SUPPORTED_RULES.add("F62");
		SUPPORTED_RULES.add("F63");
		SUPPORTED_RULES.add("F64");
		SUPPORTED_RULES.add("F65");
		SUPPORTED_RULES.add("F66");
		SUPPORTED_RULES.add("F67");
		SUPPORTED_RULES.add("F68");
		SUPPORTED_RULES.add("F69");
		SUPPORTED_RULES.add("F70");
		SUPPORTED_RULES.add("F71");
		SUPPORTED_RULES.add("F74");
		SUPPORTED_RULES.add("F75");
		SUPPORTED_RULES.add("F76");
		SUPPORTED_RULES.add("F77");
		SUPPORTED_RULES.add("F78");
		SUPPORTED_RULES.add("F79");
		SUPPORTED_RULES.add("F80");

		SUPPORTED_RULES.add("F665");
		SUPPORTED_RULES.add("F1000");
		SUPPORTED_RULES.add("F2000");

		SUPPORTED_RULES.add("W1");
		SUPPORTED_RULES.add("W2");
		SUPPORTED_RULES.add("W5");
		SUPPORTED_RULES.add("W9");
		SUPPORTED_RULES.add("W10");
		SUPPORTED_RULES.add("W11");
		SUPPORTED_RULES.add("W12");
		SUPPORTED_RULES.add("W13");
		SUPPORTED_RULES.add("W14");
		SUPPORTED_RULES.add("W15");
		SUPPORTED_RULES.add("W16");
		SUPPORTED_RULES.add("W17");
		SUPPORTED_RULES.add("W18");
		SUPPORTED_RULES.add("W19");
		SUPPORTED_RULES.add("W20");
		SUPPORTED_RULES.add("W21");
		SUPPORTED_RULES.add("W22");
		SUPPORTED_RULES.add("W23");
		SUPPORTED_RULES.add("W24");
		SUPPORTED_RULES.add("W26");
		SUPPORTED_RULES.add("W27");
		SUPPORTED_RULES.add("W28");
		SUPPORTED_RULES.add("W29");
		SUPPORTED_RULES.add("W31");
		SUPPORTED_RULES.add("W32");
		SUPPORTED_RULES.add("W33");
		SUPPORTED_RULES.add("W34");
		SUPPORTED_RULES.add("W35");
		SUPPORTED_RULES.add("W36");
		SUPPORTED_RULES.add("W37");
		SUPPORTED_RULES.add("W38");
		SUPPORTED_RULES.add("W39");
		SUPPORTED_RULES.add("W40");
		SUPPORTED_RULES.add("W41");
		SUPPORTED_RULES.add("W42");
		SUPPORTED_RULES.add("W43");
		SUPPORTED_RULES.add("W44");
		SUPPORTED_RULES.add("W45");
		SUPPORTED_RULES.add("W46");
		SUPPORTED_RULES.add("W47");
		SUPPORTED_RULES.add("W48");
		SUPPORTED_RULES.add("W49");
		SUPPORTED_RULES.add("W50");
		SUPPORTED_RULES.add("W51");
		SUPPORTED_RULES.add("W52");
		SUPPORTED_RULES.add("W53");
		SUPPORTED_RULES.add("W54");
		SUPPORTED_RULES.add("W55");
		SUPPORTED_RULES.add("W56");
		SUPPORTED_RULES.add("W57");
		SUPPORTED_RULES.add("W58");
		SUPPORTED_RULES.add("W59");
		SUPPORTED_RULES.add("W60");
		SUPPORTED_RULES.add("W61");
		SUPPORTED_RULES.add("W62");
		SUPPORTED_RULES.add("W63");
		SUPPORTED_RULES.add("W64");
		SUPPORTED_RULES.add("W65");
		SUPPORTED_RULES.add("W66");
		SUPPORTED_RULES.add("W67");
		SUPPORTED_RULES.add("W68");
		SUPPORTED_RULES.add("W69");
		SUPPORTED_RULES.add("W70");
		SUPPORTED_RULES.add("W71");
		SUPPORTED_RULES.add("W72");
		SUPPORTED_RULES.add("W73");
		SUPPORTED_RULES.add("W74");
		SUPPORTED_RULES.add("W75");
		SUPPORTED_RULES.add("W76");
		SUPPORTED_RULES.add("W77");
		SUPPORTED_RULES.add("W78");
		SUPPORTED_RULES.add("W79");
		SUPPORTED_RULES.add("W80");
		SUPPORTED_RULES.add("W81");
		SUPPORTED_RULES.add("W82");
		SUPPORTED_RULES.add("W83");
		SUPPORTED_RULES.add("W84");
		SUPPORTED_RULES.add("W85");
		SUPPORTED_RULES.add("W86");
		SUPPORTED_RULES.add("W87");
		SUPPORTED_RULES.add("W88");
		SUPPORTED_RULES.add("W89");
		SUPPORTED_RULES.add("W90");
		SUPPORTED_RULES.add("W91");
		SUPPORTED_RULES.add("W92");

		SUPPORTED_RULES.add("W1200");
		SUPPORTED_RULES.add("W1201");

		SUPPORTED_RULES.add(UNDEFINED_FAILURE);
		SUPPORTED_RULES.add(UNDEFINED_WARNING);
	}

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
	 * Checks for rule.
	 *
	 * @param id the id
	 * @return true, if successful
	 */
	public static boolean hasRule(final String id) {
		return SUPPORTED_RULES.contains(id);
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
	 * @throws FileNotFoundException the file not found exception
	 */
	private void handleCfnNagScanReports(final SensorContext context, final String report)
			throws FileNotFoundException {
		LOGGER.info("Reading cfn-nag reports:{}", report);
		final List<CfnNagScanReport> cfnNagscanReports = cfnNagScanReportReader
				.readReport(new FileInputStream(pathResolver.relativeFile(fileSystem.baseDir(), report)));

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
		if (templateInputFile != null) {

			if (violation.getLineNumbers().isEmpty()) {
				context.newIssue().forRule(RuleKey.of("cfn-" + templateInputFile.language(), findRuleId(violation)))
						.at(new DefaultIssueLocation().on(templateInputFile).message(violation.getMessage())).save();
			} else {
				final List<Integer> line_numbers = violation.getLineNumbers();
				for (final Integer line : line_numbers) {
					if (line != null && line >= 0) {
						context.newIssue()
								.forRule(RuleKey.of("cfn-" + templateInputFile.language(), findRuleId(violation)))
								.at(new DefaultIssueLocation().on(templateInputFile).message(violation.getMessage())
										.at(templateInputFile.selectLine(line)))
								.save();
					} else {
						context.newIssue()
								.forRule(RuleKey.of("cfn-" + templateInputFile.language(), findRuleId(violation)))
								.at(new DefaultIssueLocation().on(templateInputFile).message(violation.getMessage()))
								.save();
					}
				}
			}
		} else {
			context.newIssue().forRule(RuleKey.of("cfn-" + "yaml", findRuleId(violation)))
					.at(new DefaultIssueLocation().on(context.project()).message(violation.getMessage())).save();
		}
	}

	/**
	 * Find rule id.
	 *
	 * @param violation the violation
	 * @return the string
	 */
	private static String findRuleId(final CfnNagViolation violation) {
		if (hasRule(violation.getId())) {
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