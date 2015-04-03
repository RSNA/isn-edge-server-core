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

/**
 * 
 * Programmatic interface to the "configurations" table.
 * 
 * @author Wyatt Tellis
 * @version 3.1.0
 * @since 1.0.0
 */
public class ConfigurationDao extends Dao
{
	/**
	 * Get the value associated with a configuration key
	 * 
	 * @param key The key to lookup
	 * @return The value or null if the key is not in the "configurations" table
	 * @throws SQLException If there was an error retrieving the value
	 */
	public String getConfiguration(String key) throws SQLException
	{
		Connection con = getConnection();

		PreparedStatement stmt = con.prepareStatement("SELECT * FROM configurations WHERE \"key\" = ?");
		stmt.setString(1, key);

		String config = null;
		ResultSet rs = stmt.executeQuery();
		if(rs.next())		
			config = rs.getString("value");


		rs.close();
		con.close();

		return config;
	}
        
        public boolean isAttachDicomReport() throws SQLException
        {
                return Boolean.parseBoolean(getConfiguration("secondary-capture-report-enabled"));
        }
}
