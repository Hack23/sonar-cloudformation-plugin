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
package com.hack23.sonar.cloudformation.reports.checkov;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class CheckovReport.
 */
public final class CheckovReport {

	/** The check type. */
	private String checkType;

	/** The summary. */
	private CheckovSummary summary;

	/** The results. */
	private CheckovResults results;



	/**
	 * Gets the check type.
	 *
	 * @return the check type
	 */
	@JsonProperty("check_type")
	public String getCheckType() {
		return checkType;
	}

	/**
	 * Sets the check type.
	 *
	 * @param checkType the new check type
	 */
	public void setCheckType(final String checkType) {
		this.checkType = checkType;
	}

	/**
	 * Gets the summary.
	 *
	 * @return the summary
	 */
	public CheckovSummary getSummary() {
		return summary;
	}

	/**
	 * Sets the summary.
	 *
	 * @param summary the new summary
	 */
	public void setSummary(final CheckovSummary summary) {
		this.summary = summary;
	}

	/**
	 * Gets the results.
	 *
	 * @return the results
	 */
	public CheckovResults getResults() {
		return results;
	}

	/**
	 * Sets the results.
	 *
	 * @param results the new results
	 */
	public void setResults(final CheckovResults results) {
		this.results = results;
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
