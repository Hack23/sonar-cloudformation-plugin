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

/**
 * The Class CheckovResults.
 */
public final class CheckovResults {

	/** The passed checks. */
	private List<CheckovPassedCheck> passed_checks;

	/** The failed checks. */
	private List<CheckovPassedCheck> failed_checks;

    /** The skipped checks. */
    private List<String> skipped_checks;

    /** The parsing errors. */
    private List<String> parsing_errors;

	/**
	 * Gets the passed checks.
	 *
	 * @return the passed checks
	 */
	public List<CheckovPassedCheck> getPassed_checks() {
		return passed_checks;
	}

	/**
	 * Sets the passed checks.
	 *
	 * @param passed_checks the new passed checks
	 */
	public void setPassed_checks(final List<CheckovPassedCheck> passed_checks) {
		this.passed_checks = passed_checks;
	}

	/**
	 * Gets the failed checks.
	 *
	 * @return the failed checks
	 */
	public List<CheckovPassedCheck> getFailed_checks() {
		return failed_checks;
	}

	/**
	 * Sets the failed checks.
	 *
	 * @param failed_checks the new failed checks
	 */
	public void setFailed_checks(final List<CheckovPassedCheck> failed_checks) {
		this.failed_checks = failed_checks;
	}

	/**
	 * Gets the skipped checks.
	 *
	 * @return the skipped checks
	 */
	public List<String> getSkipped_checks() {
		return skipped_checks;
	}

	/**
	 * Sets the skipped checks.
	 *
	 * @param skipped_checks the new skipped checks
	 */
	public void setSkipped_checks(final List<String> skipped_checks) {
		this.skipped_checks = skipped_checks;
	}

	/**
	 * Gets the parsing errors.
	 *
	 * @return the parsing errors
	 */
	public List<String> getParsing_errors() {
		return parsing_errors;
	}

	/**
	 * Sets the parsing errors.
	 *
	 * @param parsing_errors the new parsing errors
	 */
	public void setParsing_errors(final List<String> parsing_errors) {
		this.parsing_errors = parsing_errors;
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
