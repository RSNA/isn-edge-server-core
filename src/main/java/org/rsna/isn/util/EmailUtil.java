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

import java.io.PrintStream;
import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;
import org.antlr.stringtemplate.StringTemplate;
import org.apache.commons.lang.StringUtils;
import org.rsna.isn.dao.ConfigurationDao;

import org.rsna.isn.dao.EmailConfigurationDao;
import org.rsna.isn.dao.EmailDao;
import org.rsna.isn.domain.Email;
import org.rsna.isn.domain.Job;


/**
 * This class implements sending email.
 *
 * @author Clifton Li
 * @version 3.2.0
 * @since 3.2.0
 */

public class EmailUtil {

    private static final Logger logger = Logger.getLogger(EmailUtil.class);
 
     /**
     * Send email from a queue
     * 
     * @return The value or null if the key is not in the "configurations" table
     * @throws SQLException If there was an error retrieving the value
     */  
    public static void sendInQueue(Email email) throws SQLException     
    {
            EmailDao queue = new EmailDao();
            
            try 
            {
                    send(email);
                    //flag sent in email queue
                    queue.updateQueue(email.getEmailId(),true,false,"Email Sent");      
            } 
            catch (Exception ex) 
            {
                    //flag failed in email queue
                    queue.updateQueue(email.getEmailId(),false,true,ex.toString());
            } 
    }

    public static String send(Email email) throws Exception
    {              
            return send(email.getRecipient(),email.getSubject(),email.getBody());
    }
    
     /**
     * Sends email using configurations from database
     * 
     * @return The debug output
     * @throws SQLException If there was an error retrieving the value
     */  
    public static String send(String subject, String body) throws Exception
    {          
            Properties props = getProperties();
            String recipients = props.getProperty("error_email_recipients");
            
            return send(recipients,subject,body);
    }

    public static String send(String receivers, String subject, String body) throws AddressException, MessagingException
    {       
            //Prepare stream for debug output
            ByteArrayOutputStream debugOutput = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(debugOutput);
            
            Properties props = getProperties();
            InternetAddress[] recipients = validateAddress(receivers);
            InternetAddress[] replyTo = validateAddress(props.getProperty("reply_to_email")); 
            InternetAddress[] from = validateAddress(props.getProperty("mail.smtp.from"));
            
            //reset property with bounce email
            String bounce_email = props.getProperty("bounce_email");
            
            if (bounce_email != null)
            {
                     props.put("mail.smtp.from",props.getProperty("bounce_email"));
            }
            
            Session session = buildSessionFromProperties(props);       
                
            //compose the message  
            try 
            {  
                    session.setDebug(true);
                    session.setDebugOut(ps);                            

                    MimeMessage message = new MimeMessage(session);  

                    message.addFrom(from);
                    message.setReplyTo(replyTo);
                    message.setRecipients(Message.RecipientType.TO,recipients);
                    message.setSubject(subject);  


                    message.setContent(buildMultipartContent(body));


                    //Send message  
                    Transport.send(message);

                    logger.info("Successfully sent email to " + receivers + " with subject "+ subject);

            }                     
            catch (AddressException ae)
            {
                    logger.error("Invalid recipient address: " + receivers);
                    throw ae;
            }    

            catch (MessagingException mex) 
            {
                     String error = org.apache.commons.lang.exception.ExceptionUtils.getFullStackTrace(mex);
    
                     if (StringUtils.contains(error, "550 5.1.1"))
                     {
                            logger.error("Remote server rejected mail for recipient - 550-5.1.1 The email account that you tried to reach does not exist.", mex);
                            throw mex;
                     }
                     else
                     {
                            logger.error("Uncaught exception while constructing MultiPart message ", mex);
                     }
                    
                    
            }  
            
            catch (Exception ex) 
            {
                    logger.error("Uncaught exception while sending ",ex);
            }  
            

            return debugOutput.toString();
    }
 
    // move to email util class. make static and return string
    public static String composeBody(Job job, String template, String message, int jobStatus, String jobStatusMsg) throws SQLException
    {   
            //clean the template
            template = template.toString().replaceAll("\r","\n");

            StringTemplate emailBody = new StringTemplate(template);

            emailBody.setAttribute("patientname",FormatText.formatPatientName(job.getExam().getPatientName()));       
            emailBody.setAttribute("accession",job.getExam().getAccNum());
            emailBody.setAttribute("jobid",job.getJobId());
            emailBody.setAttribute("jobstatus",jobStatusMsg);
            emailBody.setAttribute("jobstatuscode",jobStatus);
            emailBody.setAttribute("errormsg",message);


            ConfigurationDao config = new ConfigurationDao();
            emailBody.setAttribute("sitename",config.getConfiguration("site_id"));

            return emailBody.toString();
    }
        
     /**
     * Detects whether the message is HTML or text. 
     * Method will convert HTML to text for Multipart emails
     * 
     * @return The value or null if the key is not in the "configurations" table
     * @throws MessagingException If there was an error retrieving the value
     */  
    private static Multipart buildMultipartContent(String message) 
    {
            Multipart multiPart = new MimeMultipart("alternative");
            MimeBodyPart textPart = new MimeBodyPart();
            MimeBodyPart htmlPart = new MimeBodyPart();

            try
            {
                    if (FormatText.isHtml(message))
                    {
                            //build text part
                            textPart.setText(FormatText.htmlToText(message));                          

                            //build html part
                            htmlPart.setContent(message, "text/html; charset=utf-8");

                            //According to the multipart MIME specification, the order of the parts are important. 
                            //They should be added in order from low fidelity to high fidelity.
                            multiPart.addBodyPart(textPart); 
                            multiPart.addBodyPart(htmlPart);
                    }
                    //Send text part only if text message is detcted.
                    else
                    {
                            textPart.setText(message);
                            multiPart.addBodyPart(textPart);       
                    }
            }
            catch (MessagingException mex)
            {
                    logger.error("Uncaught exception while constructing MultiPart message ", mex);
            }

            return multiPart;    
    }
    
     /**
     * Construct a session from properties
     * @return The email session
     */  
    private static Session buildSessionFromProperties(Properties javaMailProperties)
    {
            Session session = Session.getDefaultInstance(javaMailProperties);  

            //use authenication when mail.smtp.auth is set to true
            if (javaMailProperties.getProperty("mail.smtp.auth") != null && javaMailProperties.getProperty("mail.smtp.auth").equals("true"))
            {
                    final String pass =  PasswordEncryption.decrypt(javaMailProperties.getProperty("password"));
                    final String username =  javaMailProperties.getProperty("username");

                     session = Session.getInstance(javaMailProperties,
                        new javax.mail.Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() { 
                        return new PasswordAuthentication(username,pass);
                        }
                    });
            }

            return session;
    }
    
    private static InternetAddress[] validateAddress(String recipients)
    {   
            InternetAddress[] recipient = null;
            try 
            {
                    if (recipients != null)
                    {
                            recipient = InternetAddress.parse(recipients);  

                            for (int i=0; i < recipient.length; i++)
                            {
                                recipient[i].validate();
                            }
                    }
            } 
            catch (AddressException ex) 
            {
                    logger.error("Invalid recipient address: " + recipients);
            }
                        
            return recipient;
    }
    
    private static Properties getProperties()
    {
            EmailConfigurationDao config = new EmailConfigurationDao();
            Properties props = new Properties();

            try 
            {
                props = config.getEmailConfiguration();   
            } 
            catch (SQLException ex) 
            {
                logger.error("Uncaught exception while retreiving email properties", ex);
            }  

            return props;   
    }
}
