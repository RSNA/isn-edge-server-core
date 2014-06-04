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

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import org.rsna.isn.util.Environment;
import org.rsna.isn.util.PasswordEncryption;
/**
 * 
 * Programmatic interface to the email_configurations table.
 * 
 * @author Clifton Li
 * @version 3.2.0
 * @since 3.2.0
 */

public class EmailConfigurationDao extends Dao
{
    	/**
	 * Get the value associated with a email configuration key
	 * 
	 * @param key The key to lookup
	 * @return The value or null if the key is not in the "configurations" table
	 * @throws SQLException If there was an error retrieving the value
	 */        
    	public String getConfiguration(String key) throws SQLException
	{
		Connection con = getConnection();

		PreparedStatement stmt = con.prepareStatement("SELECT * FROM email_configurations WHERE \"key\" = ?");
		stmt.setString(1, key);

		String config = null;
		ResultSet rs = stmt.executeQuery();
		if(rs.next())		
			config = rs.getString("value");


		rs.close();
		con.close();

		return config;
	}
        
	/**
	 * Get the value associated with a configuration key
	 * 
	 * @param key The key to lookup
	 * @return The value or null if the key is not in the "configurations" table
	 * @throws SQLException If there was an error retrieving the value
	 */        
        public Properties getEmailConfiguration() throws SQLException
        {
            	Connection con = getConnection();
		//PreparedStatement stmt = con.prepareStatement("SELECT * FROM email_configurations WHERE \"key\" LIKE ?");
                //stmt.setString(1, "mail.%");
                PreparedStatement stmt = con.prepareStatement("SELECT * FROM email_configurations");
                
                
                Properties props = new Properties();
                
                ResultSet rs = stmt.executeQuery();
		while(rs.next())
                {
                    props.put(rs.getString("key"),rs.getString("value"));
                }
                
                rs.close();
		con.close();
                
                return props;
        }
        
        public boolean isEmailFlagSet(int status) throws SQLException
        {
                Connection con = getConnection();

                try
                {
                        String selectSql = "SELECT send_alert from status_codes WHERE status_code = ?";

                        PreparedStatement stmt = con.prepareStatement(selectSql);
                        stmt.setInt(1, status);

                        ResultSet rs = stmt.executeQuery();
                        
                        while (rs.next())
                        {
                                return rs.getBoolean("send_alert");
                        }                                                   

                        return false;
                }
                finally
                {
                        con.close();
                }
        }
        
        public String getPassword() throws SQLException
        {
                Connection con = getConnection();
                              
                String selectSql = "SELECT value FROM email_configurations WHERE \"key\" LIKE ?";
                PreparedStatement stmt = con.prepareStatement(selectSql);
                stmt.setString(1, "password");

                String password = null;
                ResultSet rs = stmt.executeQuery();

                while (rs.next())
                {
                         password = PasswordEncryption.decrypt(rs.getString("value"));
                }     

                rs.close();
                con.close();
                
                return password;
        }
        
        public String getUsername() throws SQLException
        {
                Connection con = getConnection();

                try
                {
                        String selectSql = "SELECT password FROM email_configurations WHERE \"key\" LIKE ?";

                        PreparedStatement stmt = con.prepareStatement(selectSql);
                        stmt.setString(1, "username");

                        ResultSet rs = stmt.executeQuery();
                        
                        while (rs.next())
                        {
                                 return rs.getString("username");
                        }     
                        
                        return "";
                }
                
                finally
                {
                        con.close();
                }           
        }
        
        public static void updateEmailPropFile() throws SQLException
        {
            EmailConfigurationDao config = new EmailConfigurationDao();
            Connection con = config.getConnection();
            
            Properties props = new Properties();
            
            try
            {
                    String selectSql = "SELECT * FROM email_configurations order by key ASC";

                    PreparedStatement stmt = con.prepareStatement(selectSql);
                    ResultSet rs = stmt.executeQuery();
                    
                    while (rs.next())
                    {
                        props.setProperty(rs.getString("key"), rs.getString("value"));
                    }     

                    File confDir = Environment.getConfDir();
                    File emailPropsFile = new File(confDir, "email.properties");

                    //save properties to conf directory
                    props.store(new FileOutputStream(emailPropsFile), null);          
                
            }
            catch (Exception ex)
            {
                    throw new ExceptionInInitializerError(ex);
            }
        }
                
}
