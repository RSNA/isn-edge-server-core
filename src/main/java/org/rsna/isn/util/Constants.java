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

/**
 * This class contains constants required by multiple apps.
 *
 * @author Wyatt Tellis
 * @author Clifton Li
 * @version 5.0.0
 * @since 2.1.0
 */
public class Constants
{
	
	/**
	 * Identifies the Assigning Authority used for patient identifiers 
         * sent to the Clearinghouse
	 */
	//public static final String RSNA_ISN_ASSIGNING_AUTHORITY = "1.3.6.1.4.1.19376.3.840.1.1.1";  
        public static final String RSNA_ISN_ASSIGNING_AUTHORITY = "1.3.6.1.4.1.21367.13.20.3000"; 
        
 	/**
	 * Identifies the Assigning Authority used for accession numbers
         * sent to the Clearinghouse
	 */       
        public static final String SITE_ASSIGNING_AUTHORITY = "1.3.6.1.4.1.19376.3.840.1.1.5"; 
	/**
	 * Identifies the root for the sourceId in the submission set. 
         * Individual document sources will be assigned a unique value based on this root.
	 */        
        //public static final String RSNA_ISN_ROOT_SOURCE_ID = "1.3.6.1.4.1.19376.3.840.1.1.4";  
        //delete - not used
        public static final String RSNA_ISN_ROOT_SOURCE_ID = "1.3.6.1.4.1.21367";
        /**
	 * The assigning authority universal id type assigned to the RSNA image sharing
	 * network.
	 */                  
	public static final String RSNA_UNIVERSAL_ID_TYPE = "ISO";

	private Constants()
	{
	}

}
