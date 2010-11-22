/* Copyright (c) <2010>, <Radiological Society of North America>
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * Neither the name of the <RSNA> nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
 * USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY
 * OF SUCH DAMAGE.
 */
package org.rsna.isn.domain;

import org.apache.commons.lang.StringUtils;

/**
 * This class represents a report author.  It handles parsing data from
 * the "signer", "dictator" and "transcriber" columns in the "reports" table.
 *
 * @author Wyatt Tellis
 * @version 1.2.0
 */
public class Author
{
	/**
	 * Create an Author instance from a string.
	 *
	 * @param author A string with the following format: last^first^middle^id
	 * @exception IllegalArgumentException If the string does not have the right
	 * format.
	 */
	public Author(String author)
	{
		String tokens[] = StringUtils.splitPreserveAllTokens(author, '^');
		if (tokens.length < 3)
			throw new IllegalArgumentException("Invalid author name: " + author);

		this.id = tokens[0];
		this.lastName = tokens[1];
		this.firstName = tokens[2];
	}

	private String lastName;

	/**
	 * Get the value of lastName
	 *
	 * @return the value of lastName
	 */
	public String getLastName()
	{
		return lastName;
	}

	/**
	 * Set the value of lastName
	 *
	 * @param lastName new value of lastName
	 */
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	private String firstName;

	/**
	 * Get the value of firstName
	 *
	 * @return the value of firstName
	 */
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * Set the value of firstName
	 *
	 * @param firstName new value of firstName
	 */
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	private String id;

	/**
	 * Get the value of id
	 *
	 * @return the value of id
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * Set the value of id
	 *
	 * @param id new value of id
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	@Override
	public String toString()
	{
		return "Author{" + "lastName=" + lastName
				+ "firstName=" + firstName + "id=" + id + '}';
	}

}
