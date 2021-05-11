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
import java.util.ArrayList;
import java.util.List;

import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;

/**
 * The Class AbstractProcessReports.
 */
abstract class AbstractProcessReports {

	
	/**
	 * Find template.
	 *
	 * @param fileSystem the file system
	 * @param templateName the template name
	 * @param filepath the filepath
	 * @return the input file
	 */
	InputFile findTemplate(final FileSystem fileSystem, final String templateName, final String filepath) {
		final List<InputFile> potentialReportTargets = new ArrayList<>();
		fileSystem.inputFiles(fileSystem.predicates().all()).forEach(potentialReportTargets::add);
		final String filterPath = filterPath(filepath);
		for (final InputFile inputFile : potentialReportTargets) {
			if (templateName.equals(inputFile.filename()) && inputFile.uri().toString().contains(filterPath)) {
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
