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
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.lang.StringUtils;
import org.rsna.isn.domain.Author;
import org.rsna.isn.domain.Exam;

/**
 * Programmatic interface to the "v_exam_status" view.
 * 
 * @author Wyatt Tellis
 * @version 3.2.0
 * @since 2.1.0
 * 
 */
public class ExamDao extends Dao
{
	/**
	 * Lookup an exam by its exam id.
	 *
	 * @param examId The id of the exam to find
	 * @return An exam instance or null if the exam id is invalid
	 * @throws SQLException If there was an error retrieving the exam from the
	 * database
	 */
	public Exam getExam(int examId) throws SQLException
	{
		Connection con = getConnection();
		try
		{
			String examSelect = "SELECT * from v_exam_status WHERE exam_id = " + examId;

			ResultSet rs = con.createStatement().executeQuery(examSelect);
			if (rs.next())
			{
				Exam exam = new Exam();
                                
                                exam.setPatientName(rs.getString("patient_name"));
				exam.setMrn(rs.getString("mrn"));
				exam.setAccNum(rs.getString("accession_number"));
				exam.setStatus(rs.getString("status"));
				exam.setStatusTimestamp(rs.getTimestamp("status_timestamp"));
				exam.setReport(rs.getString("report_text"));
                                exam.setEmail(rs.getString("email_address"));
                                exam.setExamDescription(rs.getString("exam_description"));

				String signer = rs.getString("signer");
				if (StringUtils.isNotBlank(signer))
					exam.setSigner(new Author(signer));


				return exam;
			}

			return null;
		}
		finally
		{
			con.close();
		}
	}

}
