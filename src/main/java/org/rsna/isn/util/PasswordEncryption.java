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
package org.rsna.isn.util;

import org.jasypt.util.text.BasicTextEncryptor;

/**
 * This class contains utility methods for working with the edge server's file
 * system.
 *
 * @author Clifton Li
 * @version 3.2.0
 * @since 3.2.0
 */
public class PasswordEncryption {
    
    private static String key = "rsna.org";
    
    public static String encrypt(String password)
    {
        
        // Creates an instance of BasicPasswordEncryptor.
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();

        // Encrypted version of user password.
        textEncryptor.setPassword(key);
        String encrypted = textEncryptor.encrypt(password);
        
        return encrypted;
    }
    
    public static String decrypt(String encrypted)
    {
         // Creates an instance of BasicPasswordEncryptor.
         BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
         
         // Decrypted version of user password.
         textEncryptor.setPassword(key);
         String password = textEncryptor.decrypt(encrypted);
        
         return password;
    }
}
