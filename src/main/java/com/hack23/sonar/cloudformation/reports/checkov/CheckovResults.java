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

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class CheckovResults.
 */
public final class CheckovResults {

	/** The passed checks. */
	private List<CheckovPassedCheck> passedChecks;

	/** The failed checks. */
	private List<CheckovPassedCheck> failedChecks;

    /** The skipped checks. */
    private List<String> skippedChecks;

    /** The parsing errors. */
    private List<String> parsingErrors;

	/**
	 * Gets the passed checks.
	 *
	 * @return the passed checks
	 */
    @JsonProperty("passed_checks")
	public List<CheckovPassedCheck> getPassedChecks() {
		return passedChecks;
	}

	/**
	 * Sets the passed checks.
	 *
	 * @param passedChecks the new passed checks
	 */
	public void setPassedChecks(final List<CheckovPassedCheck> passedChecks) {
		this.passedChecks = passedChecks;
	}

	/**
	 * Gets the failed checks.
	 *
	 * @return the failed checks
	 */
    @JsonProperty("failed_checks")
	public List<CheckovPassedCheck> getFailedChecks() {
		return failedChecks;
	}

	/**
	 * Sets the failed checks.
	 *
	 * @param failedChecks the new failed checks
	 */
	public void setFailedChecks(final List<CheckovPassedCheck> failedChecks) {
		this.failedChecks = failedChecks;
	}

	/**
	 * Gets the skipped checks.
	 *
	 * @return the skipped checks
	 */
    @JsonProperty("skipped_checks")
	public List<String> getSkippedChecks() {
		return skippedChecks;
	}

	/**
	 * Sets the skipped checks.
	 *
	 * @param skippedChecks the new skipped checks
	 */
	public void setSkippedChecks(final List<String> skippedChecks) {
		this.skippedChecks = skippedChecks;
	}

	/**
	 * Gets the parsing errors.
	 *
	 * @return the parsing errors
	 */
    @JsonProperty("parsing_errors")
	public List<String> getParsingErrors() {
		return parsingErrors;
	}

	/**
	 * Sets the parsing errors.
	 *
	 * @param parsingErrors the new parsing errors
	 */
	public void setParsingErrors(final List<String> parsingErrors) {
		this.parsingErrors = parsingErrors;
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
