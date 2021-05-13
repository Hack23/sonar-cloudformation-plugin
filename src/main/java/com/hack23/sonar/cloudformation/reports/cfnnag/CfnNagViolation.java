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
package com.hack23.sonar.cloudformation.reports.cfnnag;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class CfnNagViolation.
 */
public final class CfnNagViolation {

	/** The id. */
	private String id;

	/** The type. */
	private String type;

	/** The message. */
	private String message;

	/** The logical resource ids. */
	private List<String> logicalResourceIds = new ArrayList<>();

	/** The line numbers. */
	private List<Integer> lineNumbers = new ArrayList<>();


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
	@JsonProperty("logical_resource_ids")
	public List<String> getLogicalResourceIds() {
		return new ArrayList<>(logicalResourceIds);
	}

	/**
	 * Sets the logical resource ids.
	 *
	 * @param logicalResourceIds the new logical resource ids
	 */
	public void setLogicalResourceIds(final List<String> logicalResourceIds) {
		this.logicalResourceIds = new ArrayList<>(logicalResourceIds);
	}

	/**
	 * Gets the line numbers.
	 *
	 * @return the line numbers
	 */
	@JsonProperty("line_numbers")
	public List<Integer> getLineNumbers() {
		return new ArrayList<>(lineNumbers);
	}

	/**
	 * Sets the line numbers.
	 *
	 * @param lineNumbers the new line numbers
	 */
	public void setLineNumbers(final List<Integer> lineNumbers) {
		this.lineNumbers = new ArrayList<>(lineNumbers);
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	/**
	 * Equals.
	 *
	 * @param object the object
	 * @return true, if successful
	 */
	@Override
    public boolean equals(final Object object) {
    	return EqualsBuilder.reflectionEquals(this,object);
    }

	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}
