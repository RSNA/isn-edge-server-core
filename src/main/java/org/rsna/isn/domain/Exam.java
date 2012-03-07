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

import java.util.Date;

/**
 * This class represents an exam
 *
 * @author Wyatt Tellis
 * @version 2.1.0
 *
 */
public class Exam
{
	private String mrn = "";

	/**
	 * Get the value of mrn
	 *
	 * @return the value of mrn
	 */
	public String getMrn()
	{
		return mrn;
	}

	/**
	 * Set the value of mrn
	 *
	 * @param mrn new value of mrn
	 */
	public void setMrn(String mrn)
	{
		this.mrn = mrn;
	}

	private String accNum = "";

	/**
	 * Get the value of accNum
	 *
	 * @return the value of accNum
	 */
	public String getAccNum()
	{
		return accNum;
	}

	/**
	 * Set the value of accNum
	 *
	 * @param accNum new value of accNum
	 */
	public void setAccNum(String accNum)
	{
		this.accNum = accNum;
	}

	private String status = "";

	/**
	 * Get the value of status
	 *
	 * @return the value of status
	 */
	public String getStatus()
	{
		return status;
	}

	/**
	 * Set the value of status
	 *
	 * @param status new value of status
	 */
	public void setStatus(String status)
	{
		this.status = status;
	}

	private Date statusTimestamp;

	/**
	 * Get the value of statusTimestamp
	 *
	 * @return the value of statusTimestamp
	 */
	public Date getStatusTimestamp()
	{
		return statusTimestamp;
	}

	/**
	 * Set the value of statusTimestamp
	 *
	 * @param statusTimestamp new value of statusTimestamp
	 */
	public void setStatusTimestamp(Date statusTimestamp)
	{
		this.statusTimestamp = statusTimestamp;
	}

	private Author signer;

	/**
	 * Get the value of signer
	 *
	 * @return the value of signer
	 */
	public Author getSigner()
	{
		return signer;
	}

	/**
	 * Set the value of signer
	 *
	 * @param signer new value of signer
	 */
	public void setSigner(Author signer)
	{
		this.signer = signer;
	}

	private String report;

	/**
	 * Get the value of report
	 *
	 * @return the value of report
	 */
	public String getReport()
	{
		return report;
	}

	/**
	 * Set the value of report
	 *
	 * @param report new value of report
	 */
	public void setReport(String report)
	{
		this.report = report;
	}

	@Override
	public String toString()
	{
		StringBuilder str = new StringBuilder();
		str.append("exam ");
		str.append(mrn);
		str.append("/");
		str.append(accNum);
		
		return str.toString();
	}

	
}
