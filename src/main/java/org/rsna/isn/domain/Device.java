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
package org.rsna.isn.domain;

/**
 * This class represents a remote PACS as specified in the "devices"
 * table.
 *
 * @author Wyatt Tellis
 * @version 1.2.0
 * 
 */
public class Device
{
	private String host;

	/**
	 * Get the value of host
	 *
	 * @return the value of host
	 */
	public String getHost()
	{
		return host;
	}

	/**
	 * Set the value of host
	 *
	 * @param host new value of host
	 */
	public void setHost(String host)
	{
		this.host = host;
	}

	private int port;

	/**
	 * Get the value of port
	 *
	 * @return the value of port
	 */
	public int getPort()
	{
		return port;
	}

	/**
	 * Set the value of port
	 *
	 * @param port new value of port
	 */
	public void setPort(int port)
	{
		this.port = port;
	}

	private String aeTitle;

	/**
	 * Get the value of aeTitle
	 *
	 * @return the value of aeTitle
	 */
	public String getAeTitle()
	{
		return aeTitle;
	}

	/**
	 * Set the value of aeTitle
	 *
	 * @param aeTitle new value of aeTitle
	 */
	public void setAeTitle(String aeTitle)
	{
		this.aeTitle = aeTitle;
	}

}
