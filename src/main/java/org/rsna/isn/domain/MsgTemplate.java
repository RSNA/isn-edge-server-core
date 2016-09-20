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

package org.rsna.isn.domain;

import org.antlr.stringtemplate.StringTemplate;

/**
 * 
 * 
 * @author Clifton Li
 * @version 4.1.0
 * @since 4.1.0
 */

public class MsgTemplate extends StringTemplate{
        
        private String patientName = "";
        
        private String accession = "";
        
        private String jobStatus = "";
        
        private String jobStatusCode = "";

        private String accessCode = "";
        
        private String errorMsg = "";
        
        private String siteId = "";
        
        //private StringTemplate template = new StringTemplate();
        
        @Override
        public void setTemplate(String message)
        {
            message = message.replaceAll("\r","\n");
            super.setTemplate(message);
           
        }
        public void setPatientname(String patientName)
        {
                this.setAttribute("patientname", patientName);
        }
        
        public void setAccession(String accession)
        {
                this.setAttribute("accession", accession);
        }
                
        public void setJobStatus(String jobStatus)
        {
                this.setAttribute("jobstatus", jobStatus);
        }

        public void setJobStatusCode(String jobStatusCode)
        {
                this.setAttribute("jobstatuscode", jobStatusCode);
        }
                
        public void setAccessCode(String accessCode)
        {
                this.setAttribute("accesscode", accessCode);
        }
        
        public void setErrorMsg(String errorMsg)
        {
                this.setAttribute("errorsmsg", errorMsg);
        }
        
        public void setSiteId(String siteId)
        {
                this.setAttribute("site_id", siteId);
        }
        
        public String compose()
        {
                return this.toString();
        }
}
