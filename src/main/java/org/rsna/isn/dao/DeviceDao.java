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
import java.util.LinkedHashSet;
import java.util.Set;
import org.rsna.isn.domain.Device;

/**
 * Programmatic interface to the "devices" table.
 *
 * @author Wyatt Tellis
 * @version 2.1.0
 * 
 */
public class DeviceDao extends Dao
{
	/**
	 * Get all the devices in the "devices" table.
	 *
	 * @return A set containing all the devices defined in the "devices" table.
	 * @throws SQLException If there was an error retrieving the list of devices.
	 */
	public Set<Device> getDevices() throws SQLException
	{
		Set<Device> devices = new LinkedHashSet<Device>();

		Connection con = getConnection();
		try
		{
			ResultSet rs = con.createStatement().
					executeQuery("SELECT * FROM devices ORDER BY ae_title");
			while (rs.next())
			{
				Device device = new Device();

				device.setHost(rs.getString("host"));
				device.setPort(rs.getInt("port_number"));
				device.setAeTitle(rs.getString("ae_title"));

				devices.add(device);
			}

		}
		finally
		{
			con.close();
		}

		return devices;
	}

}
