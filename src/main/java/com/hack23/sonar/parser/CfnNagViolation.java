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

public class CfnNagViolation {

	private String id;
	private String type;
	private String message;
	private List<String> logical_resource_ids = new ArrayList<>();

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public List<String> getLogical_resource_ids() {
		return new ArrayList<>(logical_resource_ids);
	}

	public void setLogical_resource_ids(final List<String> logical_resource_ids) {
		this.logical_resource_ids = new ArrayList<>(logical_resource_ids);
	}
}
