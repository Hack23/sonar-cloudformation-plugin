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
 * The Class CheckovPassedCheck.
 */
public final class CheckovPassedCheck {

    /** The check id. */
    private String check_id;

    /** The check name. */
    private String check_name;

    /** The check result. */
    private Object check_result;

    /** The code block. */
    private List<Object>  code_block;

    /** The file path. */
    private String file_path;

    /** The file line range. */
    private List<Integer> file_line_range;

    /** The resource. */
    private String resource;

    /** The file abs path. */
    private String file_abs_path;

    /** The entity tags. */
    private String entity_tags;

    /** The caller file path. */
    private String caller_file_path;

    /** The caller file line range. */
    private String caller_file_line_range;

    /** The fixed definition. */
    private String fixed_definition;

	/** The evaluations. */
    private Object evaluations;

    /** The check class. */
    private String check_class;

    /** The guideline. */
    private String guideline;

	/**
	 * Gets the check id.
	 *
	 * @return the check id
	 */
	public String getCheck_id() {
		return check_id;
	}

	/**
	 * Sets the check id.
	 *
	 * @param check_id the new check id
	 */
	public void setCheck_id(final String check_id) {
		this.check_id = check_id;
	}

	/**
	 * Gets the check name.
	 *
	 * @return the check name
	 */
	public String getCheck_name() {
		return check_name;
	}

	/**
	 * Sets the check name.
	 *
	 * @param check_name the new check name
	 */
	public void setCheck_name(final String check_name) {
		this.check_name = check_name;
	}

	/**
	 * Gets the check result.
	 *
	 * @return the check result
	 */
	public Object getCheck_result() {
		return check_result;
	}

	/**
	 * Sets the check result.
	 *
	 * @param check_result the new check result
	 */
	public void setCheck_result(final Object check_result) {
		this.check_result = check_result;
	}

	/**
	 * Gets the code block.
	 *
	 * @return the code block
	 */
	public List<Object> getCode_block() {
		return code_block;
	}

	/**
	 * Sets the code block.
	 *
	 * @param code_block the new code block
	 */
	public void setCode_block(final List<Object> code_block) {
		this.code_block = code_block;
	}



	/**
	 * Gets the file path.
	 *
	 * @return the file path
	 */
	public String getFile_path() {
		return file_path;
	}

	/**
	 * Sets the file path.
	 *
	 * @param file_path the new file path
	 */
	public void setFile_path(final String file_path) {
		this.file_path = file_path;
	}

	/**
	 * Gets the file line range.
	 *
	 * @return the file line range
	 */
	public List<Integer> getFile_line_range() {
		return file_line_range;
	}

	/**
	 * Sets the file line range.
	 *
	 * @param file_line_range the new file line range
	 */
	public void setFile_line_range(final List<Integer> file_line_range) {
		this.file_line_range = file_line_range;
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
	public String getCheck_class() {
		return check_class;
	}

	/**
	 * Sets the check class.
	 *
	 * @param check_class the new check class
	 */
	public void setCheck_class(final String check_class) {
		this.check_class = check_class;
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
    public String getFile_abs_path() {
		return file_abs_path;
	}

	/**
	 * Sets the file abs path.
	 *
	 * @param file_abs_path the new file abs path
	 */
	public void setFile_abs_path(final String file_abs_path) {
		this.file_abs_path = file_abs_path;
	}



	/**
	 * Gets the entity tags.
	 *
	 * @return the entity tags
	 */
	public String getEntity_tags() {
		return entity_tags;
	}

	/**
	 * Sets the entity tags.
	 *
	 * @param entity_tags the new entity tags
	 */
	public void setEntity_tags(final String entity_tags) {
		this.entity_tags = entity_tags;
	}

	/**
	 * Gets the caller file path.
	 *
	 * @return the caller file path
	 */
	public String getCaller_file_path() {
		return caller_file_path;
	}

	/**
	 * Sets the caller file path.
	 *
	 * @param caller_file_path the new caller file path
	 */
	public void setCaller_file_path(final String caller_file_path) {
		this.caller_file_path = caller_file_path;
	}

	/**
	 * Gets the caller file line range.
	 *
	 * @return the caller file line range
	 */
	public String getCaller_file_line_range() {
		return caller_file_line_range;
	}

	/**
	 * Sets the caller file line range.
	 *
	 * @param caller_file_line_range the new caller file line range
	 */
	public void setCaller_file_line_range(final String caller_file_line_range) {
		this.caller_file_line_range = caller_file_line_range;
	}

	/**
	 * Gets the fixed definition.
	 *
	 * @return the fixed definition
	 */
	public String getFixed_definition() {
		return fixed_definition;
	}

	/**
	 * Sets the fixed definition.
	 *
	 * @param fixed_definition the new fixed definition
	 */
	public void setFixed_definition(final String fixed_definition) {
		this.fixed_definition = fixed_definition;
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