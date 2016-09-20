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
import java.util.LinkedHashSet;
import java.util.Set;
import org.rsna.isn.domain.Sms;

/**
 * 
 * Programmatic interface to the SMS_configurations table.
 * 
 * @author Clifton Li
 * @version 4.1.0
 * @since 4.1.0
 */

public class SmsDao extends Dao
{
    	/**
	 * Get the value associated with a SMS configuration key
	 * 
	 * @param key The key to lookup
	 * @return The value or null if the key is not in the "configurations" table
	 * @throws SQLException If there was an error retrieving the value
	 */
    
    	public String getConfiguration(String key) throws SQLException
	{
		Connection con = getConnection();

		PreparedStatement stmt = con.prepareStatement("SELECT * FROM sms_configurations WHERE \"key\" = ?");
		stmt.setString(1, key);

		String config = null;
		ResultSet rs = stmt.executeQuery();
		if(rs.next())		
			config = rs.getString("value");


		rs.close();
		con.close();

		return config;
	}
        
        public Sms getSms() throws SQLException
        {                
                Sms sms = new Sms();
                
                sms.setAccountId(getConfiguration("account_id"));
                sms.setToken(getConfiguration("token"));
                sms.setSender(getConfiguration("sender"));
                sms.setMessage(getConfiguration("body"));

                return sms;
        }
        
        public Boolean isSmsEnabled() throws SQLException
        {
                return Boolean.parseBoolean(getConfiguration("enable_sms"));
        }

        public void addToQueue(Sms sms) throws SQLException
        {
                Connection con = getConnection();
                try       
                {
                        String insertSql = "INSERT INTO sms_jobs"
                                        + "(recipient, message,created_date) VALUES (?, ?, now())";

                        PreparedStatement insertStmt = con.prepareStatement(insertSql);
                        insertStmt.setString(1, sms.getRecipient());
                        insertStmt.setString(2, sms.getMessage());
                        insertStmt.execute();
                }
                finally
                {
                        con.close();
                }       
        }
        
        public void updateQueue(int smsQueueId,boolean sent,boolean failed,String comments) throws SQLException
        {           
                Connection con = getConnection();

                try
                {
                        String updateSql = "UPDATE sms_jobs SET sent = ?, failed = ?, comments = ? WHERE sms_job_id = ?";

                        PreparedStatement updateStmt = con.prepareStatement(updateSql);
                        updateStmt.setBoolean(1, sent);
                        updateStmt.setBoolean(2, failed);
                        updateStmt.setString(3, comments);
                        updateStmt.setInt(4, smsQueueId);

                        if (updateStmt.executeUpdate() != 1)
                        {
                                throw new IllegalStateException("No entries in the email_jobs "
                                                + "table for email job #" + smsQueueId);
                        }
                }

                finally
                {
                        con.close();
                }                   
        }
        
        public Set<Sms> findSmsToSend() throws SQLException
        {    
                Set<Sms> smsSet = new LinkedHashSet<Sms>();

                Connection con = getConnection();
                try
                {
                        String select = "SELECT * FROM sms_jobs WHERE sent = FALSE and failed = FALSE";

                        ResultSet rs = con.createStatement().executeQuery(select);
                        while (rs.next())
                        {
                                Sms sms = buildEntity(rs);
                                smsSet.add(sms);
                        }

                        return smsSet;
                }
                finally
                {
                        con.close();
                }     
        }
        
        private Sms buildEntity(ResultSet rs) throws SQLException
        {
            Sms sms = new Sms();
            sms.setRecipient(rs.getString("recipient"));
            sms.setMessage(rs.getString("message"));
            sms.setEmailId(rs.getInt("sms_job_id"));

            return sms;
        }
}
