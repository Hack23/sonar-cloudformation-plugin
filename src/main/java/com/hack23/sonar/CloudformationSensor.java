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
package com.hack23.sonar;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import com.hack23.sonar.parser.CfnNagReport;
import com.hack23.sonar.parser.CfnNagReportReader;
import com.hack23.sonar.parser.CfnNagViolation;

public class CloudformationSensor implements Sensor {

	private static final String SENSOR_NAME = "Cloudformation Check";
	private static final Logger LOGGER = Loggers.get(CloudformationSensor.class);

	private final CfnNagReportReader cfnNagReportReader;
	private final CloudformationSensorConfiguration configuration;
	private final PathResolver pathResolver;
	private final FileSystem fileSystem;

	public CloudformationSensor(final CloudformationSensorConfiguration configuration,final FileSystem fileSystem, final PathResolver pathResolver) {
		super();
		this.cfnNagReportReader = new CfnNagReportReader();
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
			final Optional<String> reportFiles = configuration.getReportFiles();

			
			if (reportFiles.isPresent() && pathResolver.relativeFile(fileSystem.baseDir(), configuration.getReportFiles().get()).exists()) {
				
				List<InputFile> potentialReportTargets = new ArrayList<>();
				fileSystem.inputFiles(fileSystem.predicates().or(fileSystem.predicates().hasLanguage(CloudformationLanguage.KEY),fileSystem.predicates().hasLanguage("yaml"),
						fileSystem.predicates().hasLanguage("json"))).forEach(potentialReportTargets::add);

				for (InputFile inputFile : potentialReportTargets) {
					LOGGER.warn("WIP:"+ configuration.getReportFiles().get() + " = " + inputFile.filename());
				}
								
				
				final CfnNagReport cfnNagReport = cfnNagReportReader
						.readReport(new FileInputStream(pathResolver.relativeFile(fileSystem.baseDir(), configuration.getReportFiles().get())));
				if (cfnNagReport != null) {
					final List<CfnNagViolation> violations = cfnNagReport.getViolations();
					for (final CfnNagViolation cfnNagViolation : violations) {
						addIssue(context, cfnNagViolation);
					}
				}
			}
		} catch (final Exception e) {
			throw new RuntimeException("Can not process cfn-nag reports.", e);
		} finally {
			profiler.stopInfo();
		}
	}

	private void addIssue(final SensorContext context, final CfnNagViolation violation) {
		context.newIssue().forRule(RuleKey.of(CloudformationRulesDefinition.REPO_KEY, violation.getId()))
				.at(new DefaultIssueLocation().on(context.project()).message(violation.getMessage())).save();
	}
}
