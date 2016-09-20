/* Copyright (c) <2014>, <Radiological Society of North America>
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
import org.rsna.isn.domain.Email;

/**
 * Programmatic interface to the email_configurations table.
 *
 * @author Clifton Li
 * @version 3.2.0
 * @since 3.2.0
 *
 */
public class EmailDao extends EmailConfigurationDao
{ 
    
    public void addToQueue(Email email) throws SQLException
    {
            Connection con = getConnection();
            try       
            {
                    String insertSql = "INSERT INTO email_jobs"
                                    + "(recipient, subject, body,created_date) VALUES (?, ?, ?, now())";

                    PreparedStatement insertStmt = con.prepareStatement(insertSql);
                    insertStmt.setString(1, email.getRecipient());
                    insertStmt.setString(2, email.getSubject());
                    insertStmt.setString(3, email.getBody());
                    insertStmt.execute();
            }
            finally
            {
                    con.close();
            }       
    }
    
    public Email getFromQueue(int emailQueueId) throws SQLException
    {
            Connection con = getConnection();
            
            Email email = new Email();
            
            try
            {       
                    String selectSql = "SELECT * FROM email_jobs WHERE email_job_id = ?";
                    
                    PreparedStatement stmt = con.prepareStatement(selectSql);

                    stmt.setInt(1, emailQueueId);

                    ResultSet rs = stmt.executeQuery();
                    
                    while (rs.next())
                    {
                            email.setBody(rs.getString("body"));
                            email.setRecipient(rs.getString("recipient"));
                            email.setSubject(rs.getString("subject"));
                    }
            }
            finally
            {
                    con.close();
            }       
            
            return email;
    }
        
    public void updateQueue(int emailQueueId,boolean sent,boolean failed,String comments) throws SQLException
    {           
            Connection con = getConnection();

            try
            {
                    String updateSql = "UPDATE email_jobs SET sent = ?, failed = ?, comments = ? WHERE email_job_id = ?";

                    PreparedStatement updateStmt = con.prepareStatement(updateSql);
                    updateStmt.setBoolean(1, sent);
                    updateStmt.setBoolean(2, failed);
                    updateStmt.setString(3, comments);
                    updateStmt.setInt(4, emailQueueId);

                    if (updateStmt.executeUpdate() != 1)
                    {
                            throw new IllegalStateException("No entries in the email_jobs "
                                            + "table for email job #" + emailQueueId);
                    }
            }

            finally
            {
                    con.close();
            }                   
    }

    private Email buildEntity(ResultSet rs) throws SQLException
    {
            Email email = new Email();
            email.setBody(rs.getString("body"));
            email.setRecipient(rs.getString("recipient"));
            email.setSubject(rs.getString("subject"));
            email.setEmailId(rs.getInt("email_job_id"));

            return email;
    }
    
    public Set<Email> findEmailsToSend() throws SQLException
    {    
            Set<Email> emailSet = new LinkedHashSet<Email>();
            
            Connection con = getConnection();
            try
            {
                    String select = "SELECT * FROM email_jobs WHERE sent = FALSE and failed = FALSE";

                    ResultSet rs = con.createStatement().executeQuery(select);
                    while (rs.next())
                    {
                            Email email = buildEntity(rs);
                            emailSet.add(email);
                    }

                    return emailSet;
            }
            finally
            {
                    con.close();
            }     
    }
    
    public boolean isEmailPatient() throws SQLException
    {
            return Boolean.parseBoolean(getConfiguration("enable_patient_email"));
    }  
    
    public boolean isSentEmailErrors() throws SQLException
    {
            return Boolean.parseBoolean(getConfiguration("enable_error_email"));
    }
}
