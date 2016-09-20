/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rsna.isn.domain;

/**
 *
 * @author clifton
 */
public class Sms {
    
    private String accountId = "";
    
    private String token = "";
    
    private String message = "";
    
    private String sender = "";
    
    private String recipient = "";
    
    private int smsId = 0;
    
    public void setAccountId(String accountId)
    {
            this.accountId = accountId;
    }

    public void setToken(String token)
    {
            this.token = token;
    }
    public void setMessage(String message)
    {
            this.message = message;
    }    
    
    public void setSender(String sender)
    {
            this.sender = sender;
    }
    
    public void setRecipient(String recipient)
    {
            this.recipient = recipient;
    }
  
    public void setEmailId(int smsId)
    {
                this.smsId = smsId;
    }
    
    public String getAccountId()
    {
            return this.accountId;
    }
    
    public String getToken()
    {
            return this.token;
    }
    
    public String getMessage()
    {
            return this.message;
    }

    public String getRecipient()
    {
            return this.recipient;
    }

    public String getSender()
    {
            return this.sender;
    }
    
    public int getSmslId()
    {
            return smsId;
    }
}
