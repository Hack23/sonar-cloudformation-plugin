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
package com.hack23.sonar.cloudformation.parser.cfnnag;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class CfnNagReport.
 */
public final class CfnNagReport {

	/** The failure count. */
	private int failureCount;
	
	/** The violations. */
	private List<CfnNagViolation> violations = new ArrayList<>();

	/**
	 * Gets the failure count.
	 *
	 * @return the failure count
	 */
	@JsonProperty("failure_count")
	public int getFailureCount() {
		return failureCount;
	}

	/**
	 * Sets the failure count.
	 *
	 * @param failureCount the new failure count
	 */
	public void setFailureCount(final int failureCount) {
		this.failureCount = failureCount;
	}

	/**
	 * Gets the violations.
	 *
	 * @return the violations
	 */
	public List<CfnNagViolation> getViolations() {
		return new ArrayList<>(violations);
	}

	/**
	 * Sets the violations.
	 *
	 * @param violations the new violations
	 */
	public void setViolations(final List<CfnNagViolation> violations) {
		this.violations = new ArrayList<>(violations);
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
