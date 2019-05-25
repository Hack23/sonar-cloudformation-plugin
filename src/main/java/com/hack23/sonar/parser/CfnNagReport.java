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
package com.hack23.sonar.parser;

import java.util.ArrayList;
import java.util.List;

public class CfnNagReport {

	private int failure_count;
	private List<CfnNagViolation> violations = new ArrayList<>();

	public int getFailure_count() {
		return failure_count;
	}

	public void setFailure_count(final int failure_count) {
		this.failure_count = failure_count;
	}

	public List<CfnNagViolation> getViolations() {
		return violations;
	}

	public void setViolations(final List<CfnNagViolation> violations) {
		this.violations = violations;
	}

}
