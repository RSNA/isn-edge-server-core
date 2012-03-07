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

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.rsna.isn.util.Environment;

/**
 * Base class for all Dao classes.
 * 
 * @author Wyatt Tellis
 * @version 2.1.0
 *
 */
public abstract class Dao
{
	private static final Logger logger = Logger.getLogger(Dao.class);

	private static final String url;

	private static final String user;

	private static final String pw;

	private static final ComboPooledDataSource ds;

	static
	{
		File confDir = Environment.getConfDir();
		File dbPropsFile = new File(confDir, "database.properties");

		try
		{
			FileInputStream input = new FileInputStream(dbPropsFile);

			Properties props = new Properties();
			props.load(input);

			url = props.getProperty("rsnadb.url");
			if (StringUtils.isBlank(url))
				throw new ExceptionInInitializerError("rsnadb.url is not defined");

			user = props.getProperty("rsnadb.user");
			if (StringUtils.isBlank(user))
				throw new ExceptionInInitializerError("rsnadb.user is not defined");

			pw = props.getProperty("rsnadb.password");
			if (StringUtils.isBlank(pw))
				throw new ExceptionInInitializerError("rsnadb.pw is not defined");

			logger.info("Loaded database properties from " + dbPropsFile.getPath());

			ds = new ComboPooledDataSource();
			ds.setDriverClass("org.postgresql.Driver"); //loads the jdbc driver
			ds.setJdbcUrl(url);
			ds.setUser(user);
			ds.setPassword(pw);
			ds.setPreferredTestQuery("SELECT 1");
			ds.setTestConnectionOnCheckout(true);
		}
		catch (Exception ex)
		{
			throw new ExceptionInInitializerError(ex);
		}

	}

	/**
	 * Get a connection to the RSNA database.
	 *
	 * @return A connection to the database
	 * @throws SQLException If there was an error obtaining a connection.
	 */
	protected Connection getConnection() throws SQLException
	{
		return ds.getConnection();
	}

}
