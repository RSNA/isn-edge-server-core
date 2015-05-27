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
 * 
 * 
 * 3.1.0
 *      03/04/2013: Wyatt Tellis
 *           * Added constants for supported exam statuses
 */
package org.rsna.isn.domain;

import java.util.Date;
import org.apache.commons.lang.StringUtils;

/**
 * This class represents an exam
 *
 * @author Wyatt Tellis
 * @version 3.2.0
 * @since 1.0.0
 *
 */
public class Exam
{
	public static final String ORDERED = "ORDERED";

	public static final String SCHEDULED = "SCHEDULED";

	public static final String IN_PROGRESS = "IN-PROGRESS";

	public static final String COMPLETED = "COMPLETED";

	public static final String DICTATED = "DICTATED";

	public static final String PRELIMINARY = "PRELIMINARY";

	public static final String FINALIZED = "FINALIZED";

	public static final String REVISED = "REVISED";

	public static final String ADDENDED = "ADDENDED";

	public static final String NON_REPORTABLE = "NON-REPORTABLE";

	public static final String CANCELED = "CANCELED";

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
        
        private String patientName = "";

	/**
	 * Get the value of patientName
	 *
	 * @return the value of patientName
	 */
	public String getPatientName()
        {
		return patientName;
	}

	/**
	 * Set the value of patientName
	 *
	 * @param patientName new value of patientName
	 */
	public void setPatientName(String patientName)
	{
		this.patientName = patientName;
	}
        
	private String email = "";

	/**
	 * Get the value of email
	 *
	 * @return the value of email
	 */
	public String getEmail()
        {
		return email;
	}

	/**
	 * Set the value of email
	 *
	 * @param email new value of email
	 */
	public void setEmail(String email)
	{
		this.email = email;
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
        
        public boolean isReportAvailable()
        {
                if (StringUtils.isBlank(this.report))
                {
                        return false;
                }
                else
                {
                        return true;
                }
        }

}
