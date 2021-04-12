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
package com.hack23.sonar.cloudformation.parser.checkov;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class CheckovSummary.
 */
public final class CheckovSummary {

    /** The passed. */
    private int passed;
    
    /** The failed. */
    private int failed;
    
    /** The skipped. */
    private int skipped;
    
    /** The parsing errors. */
    private int parsingErrors;
    
    /** The checkov version. */
    private String checkovVersion;
    

    
	/**
	 * Gets the passed.
	 *
	 * @return the passed
	 */
	public int getPassed() {
		return passed;
	}

	/**
	 * Sets the passed.
	 *
	 * @param passed the new passed
	 */
	public void setPassed(int passed) {
		this.passed = passed;
	}

	/**
	 * Gets the failed.
	 *
	 * @return the failed
	 */
	public int getFailed() {
		return failed;
	}

	/**
	 * Sets the failed.
	 *
	 * @param failed the new failed
	 */
	public void setFailed(int failed) {
		this.failed = failed;
	}

	/**
	 * Gets the skipped.
	 *
	 * @return the skipped
	 */
	public int getSkipped() {
		return skipped;
	}

	/**
	 * Sets the skipped.
	 *
	 * @param skipped the new skipped
	 */
	public void setSkipped(int skipped) {
		this.skipped = skipped;
	}

	/**
	 * Gets the parsing errors.
	 *
	 * @return the parsing errors
	 */
	@JsonProperty("parsing_errors")
	public int getParsingErrors() {
		return parsingErrors;
	}

	/**
	 * Sets the parsing errors.
	 *
	 * @param parsingErrors the new parsing errors
	 */
	public void setParsingErrors(int parsingErrors) {
		this.parsingErrors = parsingErrors;
	}

	/**
	 * Gets the checkov version.
	 *
	 * @return the checkov version
	 */
	@JsonProperty("checkov_version")
	public String getCheckovVersion() {
		return checkovVersion;
	}

	/**
	 * Sets the checkov version.
	 *
	 * @param checkovVersion the new checkov version
	 */
	public void setCheckovVersion(String checkovVersion) {
		this.checkovVersion = checkovVersion;
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
