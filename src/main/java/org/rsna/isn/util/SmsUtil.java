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
package org.rsna.isn.util;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import org.rsna.isn.dao.SmsDao;

public class SmsUtil {

        public static void send(String receipient, String message) throws TwilioRestException, SQLException {

            //doesnt like the keystore
            //removing keystore properties for now until a better solution is found
            System.getProperties().remove("javax.net.ssl.keyStore");
            System.getProperties().remove("javax.net.ssl.keyStorePassword");
            System.getProperties().remove("javax.net.ssl.trustStore");
            System.getProperties().remove("javax.net.ssl.trustStorePassword");
            
            SmsDao smsDao = new SmsDao();
            org.rsna.isn.domain.Sms sms = smsDao.getSms();

            TwilioRestClient client = new TwilioRestClient(sms.getAccountId(), sms.getToken());

            // Build the parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("To", receipient));
            params.add(new BasicNameValuePair("From", sms.getSender()));
            params.add(new BasicNameValuePair("Body",message));

            MessageFactory messageFactory = client.getAccount().getMessageFactory();
            Message response = messageFactory.create(params);
            //System.out.println(response.getSid());
        }
        
}
