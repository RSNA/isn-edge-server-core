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
package org.rsna.isn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.rsna.isn.domain.Exam;
import org.rsna.isn.domain.Job;

/**
 * Programmatic interface to the "v_job_status" view.
 *
 * @author Wyatt Tellis
 * @version 1.2.0
 * 
 */
public class JobDao extends Dao
{
	/**
	 * Get all jobs with the specified status.
	 *
	 * @param status A job status code
	 * @return A set containing all the jobs with the specified status. The
	 * jobs are sorted by "last_transaction_timestamp"
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
	 * Update the status of a job.  This method will add a new row to the "transactions"
	 * table.
	 *
	 * @param job A job instance.
	 * @param status A job status code
	 * @param ex An exception whose stack trace will be included in the "comments"
	 * column 
	 * @throws SQLException If there was an error updating the job's status.
	 */
	public void updateStatus(Job job, int status, Throwable ex) throws SQLException
	{
		String msg = ExceptionUtils.getStackTrace(ex);

		updateStatus(job, status, msg);
	}

	/**
	 * Update the status of a job.  This method will add a new row to the "transactions"
	 * table.
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
	 * Update the status of a job.  This method will add a new row to the "transactions"
	 * table.
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
	}

	private Job buildEntity(ResultSet rs) throws SQLException
	{
		Job job = new Job();

		job.setJobId(rs.getInt("job_id"));
		job.setStatus(rs.getInt("status"));
		job.setStatusMessage(rs.getString("status_message"));
		job.setDelay(rs.getInt("delay_in_hrs"));
                job.setSingleUsePatientId(rs.getString("single_use_patient_id"));

		int examId = rs.getInt("exam_id");

		Exam exam = new ExamDao().getExam(examId);
		job.setExam(exam);

		return job;
	}

}
