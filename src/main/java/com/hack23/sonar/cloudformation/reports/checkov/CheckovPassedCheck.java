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
 * The Class CheckovPassedCheck.
 */
public final class CheckovPassedCheck {

    /** The check id. */
    private String checkId;

    /** The check name. */
    private String checkName;

    /** The check result. */
    private Object checkResult;

    /** The code block. */
    private List<Object>  codeBlock;

    /** The file path. */
    private String filePath;

    /** The file line range. */
    private List<Integer> fileLineRange;

    /** The resource. */
    private String resource;

    /** The file abs path. */
    private String fileAbsPath;

    /** The entity tags. */
    private String entityTags;

    /** The caller file path. */
    private String callerFilePath;

    /** The caller file line range. */
    private String callerFileLineRange;

    /** The fixed definition. */
    private String fixedDefinition;

	/** The evaluations. */
    private Object evaluations;

    /** The check class. */
    private String checkClass;

    /** The guideline. */
    private String guideline;

	/**
	 * Gets the check id.
	 *
	 * @return the check id
	 */
    @JsonProperty("check_id")
	public String getCheckId() {
		return checkId;
	}

	/**
	 * Sets the check id.
	 *
	 * @param checkId the new check id
	 */
	public void setCheckId(final String checkId) {
		this.checkId = checkId;
	}

	/**
	 * Gets the check name.
	 *
	 * @return the check name
	 */
    @JsonProperty("check_name")
	public String getCheckName() {
		return checkName;
	}

	/**
	 * Sets the check name.
	 *
	 * @param checkName the new check name
	 */
	public void setCheckName(final String checkName) {
		this.checkName = checkName;
	}

	/**
	 * Gets the check result.
	 *
	 * @return the check result
	 */
    @JsonProperty("check_result")
	public Object getCheckResult() {
		return checkResult;
	}

	/**
	 * Sets the check result.
	 *
	 * @param checkResult the new check result
	 */
	public void setCheckResult(final Object checkResult) {
		this.checkResult = checkResult;
	}

	/**
	 * Gets the code block.
	 *
	 * @return the code block
	 */
    @JsonProperty("code_block")
	public List<Object> getCodeBlock() {
		return codeBlock;
	}

	/**
	 * Sets the code block.
	 *
	 * @param codeBlock the new code block
	 */
	public void setCodeBlock(final List<Object> codeBlock) {
		this.codeBlock = codeBlock;
	}



	/**
	 * Gets the file path.
	 *
	 * @return the file path
	 */
    @JsonProperty("file_path")
	public String getFilePath() {
		return filePath;
	}

	/**
	 * Sets the file path.
	 *
	 * @param filePath the new file path
	 */
	public void setFilePath(final String filePath) {
		this.filePath = filePath;
	}

	/**
	 * Gets the file line range.
	 *
	 * @return the file line range
	 */
    @JsonProperty("file_line_range")
	public List<Integer> getFileLineRange() {
		return fileLineRange;
	}

	/**
	 * Sets the file line range.
	 *
	 * @param fileLineRange the new file line range
	 */
	public void setFileLineRange(final List<Integer> fileLineRange) {
		this.fileLineRange = fileLineRange;
	}

	/**
	 * Gets the resource.
	 *
	 * @return the resource
	 */
	public String getResource() {
		return resource;
	}

	/**
	 * Sets the resource.
	 *
	 * @param resource the new resource
	 */
	public void setResource(final String resource) {
		this.resource = resource;
	}

	/**
	 * Gets the evaluations.
	 *
	 * @return the evaluations
	 */
	public Object getEvaluations() {
		return evaluations;
	}

	/**
	 * Sets the evaluations.
	 *
	 * @param evaluations the new evaluations
	 */
	public void setEvaluations(final Object evaluations) {
		this.evaluations = evaluations;
	}

	/**
	 * Gets the check class.
	 *
	 * @return the check class
	 */
    @JsonProperty("check_class")
	public String getCheckClass() {
		return checkClass;
	}

	/**
	 * Sets the check class.
	 *
	 * @param checkClass the new check class
	 */
	public void setCheckClass(final String checkClass) {
		this.checkClass = checkClass;
	}

	/**
	 * Gets the guideline.
	 *
	 * @return the guideline
	 */
	public String getGuideline() {
		return guideline;
	}

	/**
	 * Sets the guideline.
	 *
	 * @param guideline the new guideline
	 */
	public void setGuideline(final String guideline) {
		this.guideline = guideline;
	}

    /**
     * Gets the file abs path.
     *
     * @return the file abs path
     */
    @JsonProperty("file_abs_path")
	public String getFileAbsPath() {
		return fileAbsPath;
	}

	/**
	 * Sets the file abs path.
	 *
	 * @param fileAbsPath the new file abs path
	 */
	public void setFileAbsPath(final String fileAbsPath) {
		this.fileAbsPath = fileAbsPath;
	}



	/**
	 * Gets the entity tags.
	 *
	 * @return the entity tags
	 */
    @JsonProperty("entity_tags")
	public String getEntityTags() {
		return entityTags;
	}

	/**
	 * Sets the entity tags.
	 *
	 * @param entityTags the new entity tags
	 */
	public void setEntityTags(final String entityTags) {
		this.entityTags = entityTags;
	}

	/**
	 * Gets the caller file path.
	 *
	 * @return the caller file path
	 */
    @JsonProperty("caller_file_path")
	public String getCallerFilePath() {
		return callerFilePath;
	}

	/**
	 * Sets the caller file path.
	 *
	 * @param callerFilePath the new caller file path
	 */
	public void setCallerFilePath(final String callerFilePath) {
		this.callerFilePath = callerFilePath;
	}

	/**
	 * Gets the caller file line range.
	 *
	 * @return the caller file line range
	 */
    @JsonProperty("caller_file_line_range")
	public String getCallerFileLineRange() {
		return callerFileLineRange;
	}

	/**
	 * Sets the caller file line range.
	 *
	 * @param callerFileLineRange the new caller file line range
	 */
	public void setCallerFileLineRange(final String callerFileLineRange) {
		this.callerFileLineRange = callerFileLineRange;
	}

	/**
	 * Gets the fixed definition.
	 *
	 * @return the fixed definition
	 */
    @JsonProperty("fixed_definition")
	public String getFixedDefinition() {
		return fixedDefinition;
	}

	/**
	 * Sets the fixed definition.
	 *
	 * @param fixedDefinition the new fixed definition
	 */
	public void setFixedDefinition(final String fixedDefinition) {
		this.fixedDefinition = fixedDefinition;
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
		return EqualsBuilder.reflectionEquals(this, object);
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
