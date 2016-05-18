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
package org.rsna.isn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.rsna.isn.domain.Exam;
import org.rsna.isn.domain.Job;
import org.rsna.isn.domain.Email;
import org.rsna.isn.util.EmailUtil;

/**
 * Programmatic interface to the "v_job_status" view.
 *
 * @author Wyatt Tellis
 * @version 4.1.0
 * @since 1.0.0
 *
 */
public class JobDao extends Dao
{
	private static final Logger logger = Logger.getLogger(JobDao.class);

	/**
	 * Get all jobs with the specified status.
	 *
	 * @param status A job status code
	 * @return A set containing all the jobs with the specified status. The jobs
	 * are sorted by "last_transaction_timestamp"
	 * @throws SQLException If there was an error retrieving from the database
	 */
	public Set<Job> getJobsByStatus(int status) throws SQLException
	{
		Connection con = getConnection();
		try
		{
			Set<Job> jobs = new LinkedHashSet<Job>();

			String select = "SELECT * FROM v_job_status WHERE status =  " + status
					+ " ORDER BY last_transaction_timestamp";

			ResultSet rs = con.createStatement().executeQuery(select);
			while (rs.next())
			{
				Job job = buildEntity(rs);
				jobs.add(job);
			}

			return jobs;
		}
		finally
		{
			con.close();
		}

	}
	
	/**
	 * Locates jobs that are eligible to be retried.
	 * 
	 * @param lastUpdate Maximum transaction date.  Only jobs who last update 
	 * timestamp is older than this date will be retrieved.
	 * @param statuses Required error statuses.  Only jobs in the specified
	 * statuses will be retrieved. 
	 * 
	 * @return A set of jobs. 
	 * 
	 * @throws SQLException If there was a database error
	 * 
	 * @since 3.1.0
	 */
	public Set<Job> findRetryableJobs(Date lastUpdate, Integer... statuses) 
			throws SQLException
	{		
		Connection con = getConnection();
		try
		{
			Set<Job> jobs = new LinkedHashSet<Job>();
			
			if(ArrayUtils.isEmpty(statuses))
				return jobs;

			String select = "SELECT * FROM v_job_status "
					+ "WHERE status IN (" + StringUtils.join(statuses, ',') + ") "
					+ "AND remaining_retries > 0 "
					+ "AND last_transaction_timestamp < ?";

			PreparedStatement stmt = con.prepareStatement(select);
			stmt.setTimestamp(1, new Timestamp(lastUpdate.getTime()));
			
			ResultSet rs = stmt.executeQuery();
			while (rs.next())
			{
				Job job = buildEntity(rs);
				jobs.add(job);
			}

			return jobs;
		}
		finally
		{
			con.close();
		}
	}
	
	/**
	 * Retry the specified job. 
	 * 
	 * @param jobId The id of the job to retry.
	 * @return True if the job was retried, false if not
	 * 
	 * @throws SQLException If there was a database error
	 * 
	 * @since 3.1.0
	 */
	public boolean retryJob(int jobId) throws SQLException
	{
		Job job = getJobById(jobId);
		if(job == null)
			return false;
		
		int retries = job.getRemainingRetries();
		if(retries < 1)
			return false;
		
		Connection con = getConnection();
		try
		{
			con.setAutoCommit(false);
			
			String updateSql = "UPDATE jobs SET remaining_retries = ? WHERE job_id = ?";
			PreparedStatement updateStmt = con.prepareStatement(updateSql);
			updateStmt.setInt(1, retries - 1);
			updateStmt.setInt(2, jobId);
			
			if(updateStmt.executeUpdate() != 1)
			{
				throw new RuntimeException("Unable to update remaining "
						+ "retries value of job #" + jobId);
			}
			
			
			String comment = "Auto retry number " + retries;			
			String insertSql = "INSERT INTO transactions"
					+ "(job_id, status_code, comments) VALUES (?, 1, ?)";

			PreparedStatement insertStmt = con.prepareStatement(insertSql);
			insertStmt.setInt(1, jobId);
			insertStmt.setString(2, comment);
			insertStmt.execute();
			
			
			con.commit();
			
			return true;
		}
		catch(SQLException ex)
		{
			con.rollback();
			
			throw ex;
		}
		finally
		{
			con.close();
		}
	}

	/**
	 * Get a job by its id
	 *
	 * @param id The id of the job to find.
	 * @return A job instance or null if the job id is invalid.
	 * @throws SQLException If there was an error retrieving the job from the
	 * database.
	 */
	public Job getJobById(int id) throws SQLException
	{
		Connection con = getConnection();
		try
		{
			Job job = null;

			String select = "SELECT * FROM v_job_status WHERE job_id =  " + id;

			ResultSet rs = con.createStatement().executeQuery(select);
			if (rs.next())
				job = buildEntity(rs);
			rs.close();

			return job;
		}
		finally
		{
			con.close();
		}
	}

	/**
	 * Find jobs by MRN, accession number and status.
	 *
	 * @param mrn The MRN to search by
	 * @param accNum The accession number to search by
	 * @param statuses The statuses to search by
	 * @return A list of matching jobs in no specific order
	 * @throws SQLException If there was an error retrieving the list of jobs
	 * from the database.
	 */
	public List<Job> findJobs(String mrn, String accNum, Integer... statuses) throws SQLException
	{
		Connection con = getConnection();
		try
		{
			String sql = "SELECT v_job_status.* FROM v_job_status INNER JOIN v_exam_status "
					+ "ON v_job_status.exam_id = v_exam_status.exam_id "
					+ "WHERE v_job_status.status IN (" + StringUtils.join(statuses, ',') + ") "
					+ "AND mrn = ? "
					+ "AND accession_number = ?";


			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, mrn);
			stmt.setString(2, accNum);

			List<Job> jobs = new ArrayList<Job>();
			ResultSet rs = stmt.executeQuery();
			while (rs.next())
			{
				Job job = buildEntity(rs);
				jobs.add(job);
			}
			rs.close();

			return jobs;
		}
		finally
		{
			con.close();
		}
	}

	/**
	 * Update the status of a job. This method will add a new row to the
	 * "transactions" table.
	 *
	 * @param job A job instance.
	 * @param status A job status code
	 * @param ex An exception whose stack trace will be included in the
	 * "comments" column
	 * @throws SQLException If there was an error updating the job's status.
	 */
	public void updateStatus(Job job, int status, Throwable ex) throws SQLException
	{
		String msg = ExceptionUtils.getStackTrace(ex);

		updateStatus(job, status, msg);
	}

	/**
	 * Update the status of a job. This method will add a new row to the
	 * "transactions" table.
	 *
	 * @param job A job instance.
	 * @param status A job status code.
	 * @throws SQLException If there was an error updating the job's status.
	 */
	public void updateStatus(Job job, int status) throws SQLException
	{
		updateStatus(job, status, "");
	}

	/**
	 * Update the status of a job. This method will add a new row to the
	 * "transactions" table.
	 *
	 * @param job A job instance.
	 * @param status A job status code.
	 * @param message The message to include in the "comments" column.
	 * @throws SQLException If there was an error updating the job's status.
	 */
	public void updateStatus(Job job, int status, String message) throws SQLException
	{
		Connection con = getConnection();

		try
		{
			String insert = "INSERT INTO transactions"
					+ "(job_id, status_code, comments) VALUES (?, ?, ?)";

			PreparedStatement stmt = con.prepareStatement(insert);
			stmt.setInt(1, job.getJobId());
			stmt.setInt(2, status);
			stmt.setString(3, message);

			stmt.execute();
		}
		finally
		{
			con.close();
		}
                
                //check for retryable 
                //send email for jobs that are C-MOVE failed with 0 retries 
                //or all other negative statuses
                EmailDao emailDao = new EmailDao();
                String adminEmail = emailDao.getConfiguration("error_email_recipients");  
                String jobStatusMsg = getStatusMsg(status);
                
                //send email to patient when jobset is complete
                if (isJobSetComplete(job.getJobSetId()))
                {
                        if (emailDao.isEmailPatient() && status == Job.RSNA_COMPLETED_TRANSFER_TO_CLEARINGHOUSE)
                        {
                                String patientEmail = job.getEmailAddress();

                                if (patientEmail.isEmpty())
                                {             
                                        logger.warn("Patient email job could not be sent because missing email for job# " + job.getJobId());
                                }         
                                else 
                                {
                                        String body = EmailUtil.composeBody(job,emailDao.getConfiguration("patient_email_body"),message,status,"");
                                        
                                        //build the body of the email from the template and data from job
                                        Email email = new Email(patientEmail,emailDao.getConfiguration("patient_email_subject"),body);
                                        emailDao.addToQueue(email);
                                }
                        }
                        //queue error email if there are no images to send in the job set
                        else if (emailDao.isSentEmailErrors() && (status == Job.RSNA_EXAM_CANCELED || status == Job.RSNA_UNABLE_TO_FIND_IMAGES))
                        {
                                if (adminEmail.isEmpty())
                                {             
                                        logger.warn("Administrative email job could not sent because email address not set. Job# " + job.getJobId());
                                }     
                                else 
                                {
                                        message = "This job set was not submitted because all the exams were either cancelled or had no images.";
                                        String body = EmailUtil.composeBody(job,emailDao.getConfiguration("error_email_body"),message,status,jobStatusMsg);
                                        
                                        //build the body of the email from the template and data from job
                                        Email email = new Email(adminEmail,jobStatusMsg,body);
                                        emailDao.addToQueue(email);                          
                                }                                    
                        }
                }
                else 
                {
                        //send mail to administrative user if email error flag set and error code set
                        if (emailDao.isSentEmailErrors() && (status < 0 && job.getRemainingRetries() == 0))
                        {
                                if (adminEmail.isEmpty())
                                {             
                                        logger.warn("Administrative email job could not sent because email address not set. Job# " + job.getJobId());
                                }     
                                else 
                                {
                                        String body = EmailUtil.composeBody(job,emailDao.getConfiguration("error_email_body"),message,status,jobStatusMsg);
                                        
                                        //build the body of the email from the template and data from job
                                        Email email = new Email(adminEmail,jobStatusMsg,body);
                                        emailDao.addToQueue(email);                          
                                }
                        }
                }
        }
	/**
	 * Update the comments associated with the specified job. This method uses
	 * the value of expectedStatus to locate the appropriate row in the
	 * transactions table to update.
	 *
	 * @param job The job whose comments will be updated. Must not be null.
	 * @param expectedStatus The expected status of the job. If the job is not
	 * in this status, this method will throw an IllegalStateException
	 * exception.
	 * @param comments The new comments. Must not be null.
	 * @throws SQLException If there was a database error.
	 */
	public void updateComments(Job job, int expectedStatus, String comments) throws SQLException
	{
		Connection con = getConnection();

		try
		{
			int jobId = job.getJobId();

			String selectSql = "SELECT transaction_id, status_code FROM transactions "
					+ "WHERE job_id = ? ORDER BY modified_date DESC LIMIT 1";

			PreparedStatement selectStmt = con.prepareStatement(selectSql);
			selectStmt.setInt(1, jobId);

			ResultSet rs = selectStmt.executeQuery();
			if (!rs.next())
			{
				//
				// If there's nothing in the transactions table for this job then we
				// have a serious problem.
				//

				throw new IllegalStateException("No entries in the transactions "
						+ "table for job #" + jobId);
			}


			int actualStatus = rs.getInt("status_code");
			if (actualStatus != expectedStatus)
			{
				throw new IllegalStateException("Invalid status for job #" + jobId + ".  "
						+ "Expected: " + expectedStatus + ", got: " + actualStatus);
			}





			int transactionId = rs.getInt("transaction_id");

			String updateSql = "UPDATE transactions SET comments = ? WHERE transaction_id = ?";


			PreparedStatement updateStmt = con.prepareStatement(updateSql);
			updateStmt.setString(1, comments);
			updateStmt.setInt(2, transactionId);

			if (updateStmt.executeUpdate() != 1)
			{
				throw new IllegalStateException("Unable to update comments on "
						+ "row with transaction id: " + transactionId);
			}
		}
		finally
		{
			con.close();
		}
	}

	private Job buildEntity(ResultSet rs) throws SQLException
	{
		Job job = new Job();
                
                job.setJobSetId(rs.getInt("job_set_id"));
		job.setJobId(rs.getInt("job_id"));
		job.setStatus(rs.getInt("status"));
		job.setStatusMessage(rs.getString("status_message"));
		job.setDelay(rs.getInt("delay_in_hrs"));
		job.setSingleUsePatientId(rs.getString("single_use_patient_id"));
                job.setAccessCode(rs.getString("access_code"));
		job.setSendOnComplete(rs.getBoolean("send_on_complete"));
		job.setRemainingRetries(rs.getInt("remaining_retries"));
                job.setEmailAddress(rs.getString("email_address"));

		int examId = rs.getInt("exam_id");

		Exam exam = new ExamDao().getExam(examId);
		if (exam == null)
		{
			logger.warn("Unable to load exam data for job id: " + job.getJobId()
					+ " because no entry found was in v_exam_status "
					+ " for exam id: " + examId + ". ");
		}


		job.setExam(exam);

		return job;
	}

        public boolean isJobSetComplete(int jobSetId) throws SQLException
        {
                Connection con = getConnection();

		try
		{
                        String selectSql = "SELECT status from v_job_status WHERE job_set_id = ? AND status NOT IN (?,?) ";
        
                        PreparedStatement stmt = con.prepareStatement(selectSql); 
                        stmt.setInt(1, jobSetId);
                        stmt.setInt(2, Job.RSNA_UNABLE_TO_FIND_IMAGES);
                        stmt.setInt(3, Job.RSNA_EXAM_CANCELED);

                        ResultSet rs = stmt.executeQuery();


                        while (rs.next())
                        {
                                if (rs.getInt("status") != Job.RSNA_COMPLETED_TRANSFER_TO_CLEARINGHOUSE)
                                {
                                        return false;
                                }
                        }

                        return true;
		}
		finally
		{
			con.close();
		}
        }
      
        public String getStatusMsg(int status) throws SQLException
        {
                Connection con = getConnection();

                try
                {
                        String selectSql = "SELECT description from status_codes WHERE status_code = ?";

                        PreparedStatement stmt = con.prepareStatement(selectSql);
                        stmt.setInt(1, status);

                        ResultSet rs = stmt.executeQuery();
                        
                        while (rs.next())
                        {
                                return rs.getString("description");
                        }                                                   

                        return null;
                }
                finally
                {
                        con.close();
                }         
            
        }
}
