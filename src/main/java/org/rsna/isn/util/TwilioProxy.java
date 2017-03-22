/* Copyright (c) <2017>, <Radiological Society of North America>
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

package org.rsna.isn.util;

import org.apache.http.HttpHost;
import org.apache.http.HttpVersion;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;

import com.twilio.sdk.TwilioRestClient;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.rsna.isn.dao.SmsDao;

/**
 *
 * @author Clifton Li
 * @version 5.0.0
 */

public class TwilioProxy extends TwilioRestClient {

        private static final Logger logger = Logger.getLogger(TwilioProxy.class);
        
        private static final int CONNECTION_TIMEOUT = 10000;

        public TwilioProxy(String accountSid, String authToken) throws SQLException 
        {
                super(accountSid, authToken);
                setHttpClient();
        }    
        
public void setHttpClient() throws SQLException 
{
        ThreadSafeClientConnManager connMgr = new ThreadSafeClientConnManager();
        connMgr.setDefaultMaxPerRoute(10);
        
        DefaultHttpClient httpclient = new DefaultHttpClient(connMgr);
        setHttpClient(httpclient);
        httpclient.getParams().setParameter("http.protocol.version", HttpVersion.HTTP_1_1);
        httpclient.getParams().setParameter("http.socket.timeout",
                new Integer(CONNECTION_TIMEOUT));
        httpclient.getParams().setParameter("http.connection.timeout",
                new Integer(CONNECTION_TIMEOUT));
        httpclient.getParams().setParameter("http.protocol.content-charset", "UTF-8");

        SmsDao dao = new SmsDao();
        boolean isProxySet = Boolean.parseBoolean(dao.getConfiguration("proxy_set"));
        
        if (isProxySet)
        {
                String proxyHost = dao.getConfiguration("proxy_host");
                String proxyPort = dao.getConfiguration("proxy_port");
                
                logger.info("Using proxy " + proxyHost + ":" + proxyPort);
                
                HttpHost proxy = new HttpHost(proxyHost, Integer.valueOf(proxyPort));
                httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        }
  }        
        
}
