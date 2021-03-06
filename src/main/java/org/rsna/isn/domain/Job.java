/* Copyright (c) <2016>, <Radiological Society of North America>
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


/**
 * This class represents a job.
 *
 * @author Wyatt Tellis
 * @author Clifton Li
 * @version 5.0.0
 * @since 4.1.0
 */
public class Job
{
        public static final int RSNA_NO_DEVICES_FOUND = -1;
        
	public static final int RSNA_WAITING_FOR_PREPARE_CONTENT = 1;

	public static final int RSNA_WAITING_FOR_EXAM_FINALIZATION = 21;

	public static final int RSNA_WAITING_FOR_DELAY_EXPIRATION = 22;

	public static final int RSNA_STARTED_DICOM_C_MOVE = 23;

	public static final int RSNA_WAITING_FOR_EXAM_COMPLETION = 24;
       
	public static final int RSNA_FAILED_TO_PREPARE_CONTENT = -20;

	public static final int RSNA_UNABLE_TO_FIND_IMAGES = -21;

	public static final int RSNA_DICOM_C_MOVE_FAILED = -23;

        public static final int RSNA_EXAM_CANCELED = -24;
        
	public static final int RSNA_WAITING_FOR_TRANSFER_CONTENT = 30;

	public static final int RSNA_STARTED_TRANSFER_CONTENT = 31;

	public static final int RSNA_STARTED_PATIENT_REGISTRATION = 32;

	public static final int RSNA_RETRIEVING_GLOBAL_ID = 33;

	public static final int RSNA_STARTED_DOCUMENT_SUBMISSION = 34;

	public static final int RSNA_FAILED_TO_TRANSFER_TO_CLEARINGHOUSE = -30;

	public static final int RSNA_FAILED_TO_REGISTER_PATIENT = -32;

	public static final int RSNA_FAILED_TO_RETRIEVE_GLOBAL_ID = -33;

	public static final int RSNA_FAILED_TO_SUBMIT_DOCUMENTS = -34;

	public static final int RSNA_COMPLETED_TRANSFER_TO_CLEARINGHOUSE = 40;

	private int jobId = -1;

	/**
	 * Get the value of jobId
	 *
	 * @return the value of jobId
	 */
	public int getJobId()
	{
		return jobId;
	}

	/**
	 * Set the value of jobId
	 *
	 * @param jobId new value of jobId
	 */
	public void setJobId(int jobId)
	{
		this.jobId = jobId;
	}
        
 	private int jobSetId = -1;

	/**
	 * Get the value of jobSetId
	 *
	 * @return the value of jobSetId
	 */
        public int getJobSetId()
        {
                return jobSetId;
        }
        
        /**
	 * Set the value of jobSetId
	 *
	 * @param jobSetId new value of jobSetId
	 */      
        public void setJobSetId(int jobSetId)
        {
                this.jobSetId = jobSetId;
        }

        
	private int status = 0;

	/**
	 * Get the value of status
	 *
	 * @return the value of status
	 */
	public int getStatus()
	{
		return status;
	}

	/**
	 * Set the value of status
	 *
	 * @param status new value of status
	 */
	public void setStatus(int status)
	{
		this.status = status;
	}

	private String statusMessage = "";

	/**
	 * Get the value of statusMessage
	 *
	 * @return the value of statusMessage
	 */
	public String getStatusMessage()
	{
		return statusMessage;
	}

	/**
	 * Set the value of statusMessage
	 *
	 * @param statusMessage new value of statusMessage
	 */
	public void setStatusMessage(String statusMessage)
	{
		this.statusMessage = statusMessage;
	}

	private int delay = 0;

	/**
	 * Get the value of delay
	 *
	 * @return the value of delay
	 */
	public int getDelay()
	{
		return delay;
	}

	/**
	 * Set the value of delay
	 *
	 * @param delay new value of delay
	 */
	public void setDelay(int delay)
	{
		this.delay = delay;
	}

	private Exam exam;

	/**
	 * Get the value of exam
	 *
	 * @return the value of exam
	 */
	public Exam getExam()
	{
		return exam;
	}

	/**
	 * Set the value of exam
	 *
	 * @param exam new value of exam
	 */
	public void setExam(Exam exam)
	{
		this.exam = exam;
	}

	private String singleUsePatientId;

	/**
	 * Get the value of singleUsePatientId
	 *
	 * @return the value of singleUsePatientId
	 */
	public String getSingleUsePatientId()
	{
		return singleUsePatientId;
	}

	/**
	 * Set the value of singleUsePatientId
	 *
	 * @param singleUsePatientId new value of singleUsePatientId
	 */
	public void setSingleUsePatientId(String singleUsePatientId)
	{
		this.singleUsePatientId = singleUsePatientId;
	}

        private String accessCode;
        
        /**
	 * Get the value of accessCode
	 *
	 * @return the value of accessCode
	 */
        public String getAccessCode()
	{
		return accessCode;
	}

	/**
	 * Set the value of accessCode
	 *
	 * @param accessCode new value of accessCode
	 */
        public void setAccessCode(String accessCode)
	{
		this.accessCode = accessCode;
	}

        private String globalId;
        
        /**
	 * Get the value of globalId
	 *
	 * @return the value of globalId
	 */
        public String getGlobalId()
	{
		return globalId;
	}
        
 	/**
	 * Set the value of globalId
	 *
	 * @param globalId new value of globalId
	 */
        public void setglobalId(String globalId)
	{
		this.globalId = globalId;
	}

        private String globalAA;
        
        /**
	 * Get the value of globalAA
	 *
	 * @return the value of globalAA
	 */
        public String getGlobalAA()
	{
		return globalAA;
	}
        
 	/**
	 * Set the value of globalAA
	 *
	 * @param globalAA new value of globalAA
	 */
        public void setglobalAA(String globalAA)
	{
		this.globalAA = globalAA;
	}        
	@Override
	public String toString()
	{
		return "job #" + jobId;
	}

	private boolean sendOnComplete = false;

	/**
	 * Get the value of sendOnComplete
	 *
	 * @return the value of sendOnComplete
	 * @since 3.1.0
	 */
	public boolean isSendOnComplete()
	{
		return sendOnComplete;
	}

	/**
	 * Set the value of sendOnComplete
	 *
	 * @param sendOnComplete new value of sendOnComplete
	 * @since 3.1.0
	 */
	public void setSendOnComplete(boolean sendOnComplete)
	{
		this.sendOnComplete = sendOnComplete;
	}

	private int remainingRetries = 0;

	/**
	 * Get the value of remainingRetries
	 *
	 * @return the value of remainingRetries
	 * @since 3.1.0
	 */
	public int getRemainingRetries()
	{
		return remainingRetries;
	}

	/**
	 * Set the value of remainingRetries
	 *
	 * @param remainingRetries new value of remainingRetries
	 * @since 3.1.0
	 */
	public void setRemainingRetries(int remainingRetries)
	{
		this.remainingRetries = remainingRetries;
	}
        
        private String emailAddress = "";
        
        /**
	 * Get the value of emailAddress
	 *
	 * @return the value of emailAddress
	 * @since 3.2.0
	 */
	public String getEmailAddress()
	{
		return emailAddress;
	}
	/**
	 * Set the value of EmailAddress
	 *
	 * @param emailAddress new value of emailAddress
	 * @since 3.2.0
	 */
	public void setEmailAddress(String emailAddress)
	{
		this.emailAddress = emailAddress;
	}
        
        private String phoneNumber = "";

        /**
         * Get the value of phoneNumber
         *
         * @return the value of phoneNumber
         * @since 4.1.0
         */
        public String getPhoneNumber()
        {
                return phoneNumber;
        }
        /**
         * Set the value of phoneNumber
         *
         * @param phoneNumber new value of phoneNumber
         * @since 4.1.0
         */
        public void setPhoneNumber(String phoneNumber)
        {
                this.phoneNumber = phoneNumber;
        }
}
