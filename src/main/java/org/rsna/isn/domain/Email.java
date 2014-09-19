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
package org.rsna.isn.domain;

import org.apache.log4j.Logger;


/**
 * This class represents a email.
 *
 * @author Clifton Li
 * @version 3.2.0
 *
 */
public class Email {

        private String recipient;
        private String subject;
        private String body;
        private int emailId;

        private static final Logger logger = Logger.getLogger(org.rsna.isn.util.EmailUtil.class);

        public Email()
        {
                this.recipient = "";
                this.subject = "";
                this.body = "";
                this.emailId = 0;
        }

        public  Email(String recipient, String subject, String body)
        {
                this.recipient = recipient;
                this.subject = subject;
                this.body = body;
                this.emailId = 0;
        }
                
        public  Email(String recipient, String subject, String body, int emailId)
        {
                this.recipient = recipient;
                this.subject = subject;
                this.body = body;
                this.emailId = emailId;
        }
        
        public void setSubject(String subject)
        {
                this.subject = subject;
        }

        public String getSubject()
        {
                return subject;
        }

        public void setRecipient(String recipient)
        {
                this.recipient = recipient;
        }

        public String getRecipient()
        {
                return recipient;
        }

        public void setBody(String body)
        {
                this.body = body;
        }

        public String getBody()
        {
                return body;
        }

        public void setEmailId(int emailId)
        {
                this.emailId = emailId;
        }
    
        public int getEmailId()
        {
                return emailId;
        }
}
