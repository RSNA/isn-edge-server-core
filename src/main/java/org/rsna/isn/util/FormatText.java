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
package org.rsna.isn.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

/**
 * This class implements text formating methods
 *
 * @author Clifton Li
 * @version 3.2.0
 * @since 3.2.0
 */

public class FormatText {
    
    public static boolean isHtml(String body)
    {
            return !Jsoup.isValid(body, Whitelist.none());
    }
    
     /**
     * Converts html to text 
     *
     * @return text with line breaks
     */
    public static String htmlToText(String bodyHtml) 
    {
            Document doc = Jsoup.parse(FormatText.cleanPreserveLineBreaks(bodyHtml));
            return doc.body().text();
    }
    
     /**
     * Preserve line breaks when <br> and <p> tags are used
     *
     */
    public static String cleanPreserveLineBreaks(String bodyHtml) 
    {
            // get pretty printed html with preserved br and p tags
            String prettyPrintedBodyFragment = Jsoup.clean(bodyHtml, "", Whitelist.none().addTags("br", "p"), new Document.OutputSettings().prettyPrint(true));
            
            // get plain text with preserved line breaks by disabled prettyPrint
            return Jsoup.clean(prettyPrintedBodyFragment, "", Whitelist.none(), new Document.OutputSettings().prettyPrint(false));
    }
    
    
    /**
     * Removes the carets and reformats as first middle last name
     *
     * @return the formated patient name
     */
    public static String formatPatientName(String patientName)
    {
            String[] patientNameParts = patientName.split("\\^");
            
            String firstName = "";
            String lastName = "";
            String middleName = "";
            
            if(patientNameParts.length > 0) 
            {
                firstName = patientNameParts[1];
                lastName = patientNameParts[0];
                middleName = patientNameParts.length == 3 ? " " + patientNameParts[2] + " " : " ";
            }
                
            return firstName + middleName + lastName;
    }
     /**
     * Wraps string with <report></report> elements
     *
     */
    public static String addReportTag(String report)
    {
            return "<report>" + report + "</report>";    
    }
     /**
     * Formats access code by adding a dash after every 3rd character
     *
     * @return the formated accessCode
     */    
    public static String formatAccessCode(String code)
    {
        StringBuilder accessCode = new StringBuilder(code);
        int offset = 3;
        
        for (int idx = offset;idx < accessCode.length(); idx += offset)
        {
            accessCode.insert(idx,"-");
            idx++;
        }
        
        return accessCode.toString();
    }
}
