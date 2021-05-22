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

import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.sonar.api.Plugin.Context;
import org.sonar.api.SonarEdition;
import org.sonar.api.SonarQubeSide;
import org.sonar.api.SonarRuntime;
import org.sonar.api.config.internal.MapSettings;
import org.sonar.api.internal.PluginContextImpl;
import org.sonar.api.internal.SonarRuntimeImpl;
import org.sonar.api.utils.Version;

/**
 * The Class CloudformationPluginTest.
 */
public class CloudformationPluginTest {

	/**
	 * Extensions test.
	 */
	@Test
	public void extensionsTest() {
		final SonarRuntime runtime = SonarRuntimeImpl.forSonarQube(Version.create(7, 9), SonarQubeSide.SCANNER,
				SonarEdition.COMMUNITY);
		final Context context = new PluginContextImpl.Builder().setSonarRuntime(runtime)
				.setBootConfiguration(new MapSettings().asConfig()).build();

		new CloudformationPlugin().define(context);
		assertFalse(context.getExtensions().isEmpty());
	}
}
