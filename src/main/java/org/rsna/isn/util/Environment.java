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

import java.io.File;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.PropertyConfigurator;

/**
 * This class contains methods for accessing information about the
 * installation environment.
 * Note: init() should be called before using any of the other methods from this
 * class
 * 
 * @author Wyatt Tellis
 * @version 2.1.0
 * 
 *
 */
public class Environment
{

    private static String rootPath;

    private Environment()
    {
    }

    /**
     * Initializes the rnsa directory information and the log4j logging library.
     * It looks for the environment variable RSNA_ROOT or system property called
     * rsna.root and then computes other paths from that. If it fails find either
     * it will default to ${user.home}/rsna.
     *
     * @param appName A string containing the short name of the app.  This string
     * is used to locate the appropriate log4j configuration using the path:
     * ${rsna.root}/conf/${appName}-log4j.properties
     * @throws IllegalArgumentException If RSNA_ROOT or rsna.root is invalid or
     * if any of the subdirectories do not exist or cannot be created.
     */
    public synchronized static void init(String appName)
    {
        if (rootPath != null)
            return;

        //
        // Read from "rsna.root" system property
        //
        String sys = System.getProperty("rsna.root");
        if (StringUtils.isNotBlank(sys))
        {
            File rootDir = new File(sys);

            if (!rootDir.isDirectory())
                throw new IllegalArgumentException(sys + " is not a directory.");

            rootPath = sys;
        }

        //
        // Read from RSNA_ROOT environment variable
        //
        String env = System.getenv("RSNA_ROOT");
        if (rootPath == null && StringUtils.isNotBlank(env))
        {
            File rootDir = new File(env);

            if (!rootDir.isDirectory())
                throw new IllegalArgumentException(env + " is not a directory.");

            rootPath = env;
        }


        //
        // Default to ${user.home}/rsna
        //
        if (rootPath == null)
        {
            String user = System.getProperty("user.home");

            File rootDir = new File(user, "rsna");
            rootDir.mkdir();

            if (!rootDir.isDirectory())
                throw new IllegalArgumentException("Unable to create "
                        + rootDir + " directory.");

            rootPath = rootDir.getPath();
        }


        File dcmDir = getDcmDir();
        dcmDir.mkdir();
        if (!dcmDir.isDirectory())
        {
            if (!dcmDir.isDirectory())
                throw new IllegalArgumentException(dcmDir + " is not a directory.");
        }



        File tmpDir = getTmpDir();
        tmpDir.mkdir();
        if (!tmpDir.isDirectory())
        {
            if (!tmpDir.isDirectory())
                throw new IllegalArgumentException(tmpDir + " is not a directory.");
        }



        File confDir = getConfDir();
        if (!confDir.isDirectory())
        {
            if (!confDir.isDirectory())
                throw new IllegalArgumentException(confDir + " is not a directory.");
        }


        File log4j = new File(confDir, appName + "-log4j.properties");
        PropertyConfigurator.configure(log4j.getPath());



        File keystore = new File(confDir, "keystore.jks");
        setProperty("javax.net.ssl.keyStore", keystore.getPath());
        setProperty("javax.net.ssl.keyStorePassword", "edge1234");

        File truststore = new File(confDir, "truststore.jks");
        setProperty("javax.net.ssl.trustStore", truststore.getPath());
        setProperty("javax.net.ssl.trustStorePassword", "edge1234");
    }

    /**
     * Get the rsna root directory.
     *
     * @return A file instance
     */
    public static File getRootDir()
    {
        return new File(rootPath);
    }

    /**
     * Get the rsna/dcm directory.
     *
     * @return A file instance
     */
    public static File getDcmDir()
    {
        return new File(rootPath, "dcm");
    }

    /**
     * Get the rsna/tmp directory.
     * @return A file instance
     */
    public static File getTmpDir()
    {
        return new File(rootPath, "tmp");
    }

    /**
     * Get the rsna/conf directory.
     * @return A file instance
     */
    public static File getConfDir()
    {
        return new File(rootPath, "conf");
    }

    private static void setProperty(String key, String value)
    {
        String temp = System.getProperty(key);
        if(StringUtils.isBlank(temp))
            System.setProperty(key, value);
    }

}
