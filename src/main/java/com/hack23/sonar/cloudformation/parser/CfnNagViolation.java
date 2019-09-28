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
package com.hack23.sonar.cloudformation.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * The Class CfnNagViolation.
 */
public class CfnNagViolation {

	/** The id. */
	private String id;
	
	/** The type. */
	private String type;
	
	/** The message. */
	private String message;
	
	/** The logical resource ids. */
	private List<String> logical_resource_ids = new ArrayList<>();
	
	/** The line numbers. */
	private List<Integer> line_numbers = new ArrayList<>();


	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(final String type) {
		this.type = type;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(final String message) {
		this.message = message;
	}

	/**
	 * Gets the logical resource ids.
	 *
	 * @return the logical resource ids
	 */
	public List<String> getLogical_resource_ids() {
		return new ArrayList<>(logical_resource_ids);
	}

	/**
	 * Sets the logical resource ids.
	 *
	 * @param logical_resource_ids the new logical resource ids
	 */
	public void setLogical_resource_ids(final List<String> logical_resource_ids) {
		this.logical_resource_ids = new ArrayList<>(logical_resource_ids);
	}
	
	/**
	 * Gets the line numbers.
	 *
	 * @return the line numbers
	 */
	public List<Integer> getLine_numbers() {
		return new ArrayList<>(line_numbers);
	}

	/**
	 * Sets the line numbers.
	 *
	 * @param line_numbers the new line numbers
	 */
	public void setLine_numbers(final List<Integer> line_numbers) {
		this.line_numbers = new ArrayList<>(line_numbers);
	}

	@Override
	public final String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@Override
    public boolean equals(final Object object) {
    	return EqualsBuilder.reflectionEquals(this,object);
    }

	@Override
	public final int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}
