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

/**
 * This class contains utility methods for working with the edge server's file
 * system.
 *
 * @author Wyatt Tellis
 * @since 3.0.0
 * @version 3.0.0
 */
public class FileUtil
{
	private FileUtil()
	{
	}

	/**
	 * Convenience method to create a file instance from a potentially unsafe 
	 * file name.
	 * 
	 * @param dir Parent directory of the file.
	 * @param name The value to use as the file name. The value will be 
	 * "cleaned up". 
	 * to ensure it's safe to use as a file name.
	 * @return A File instance.
	 */
	public static File newFile(File dir, String name)
	{
		String safe = cleanupFileName(name);

		return new File(dir, safe);
	}

	/**
	 * Convenience method to create a new file instance from an integer.
	 * 
	 * @param dir Parent directory of the file.
	 * @param name The integer value to use as the name. 
	 * @return A File instance.
	 */
	public static File newFile(File dir, int name)
	{
		return new File(dir, Integer.toString(name));
	}

	/**
	 * Convenience method to cleanup a string for use as a file name. 
	 * 
	 * @param src The "unsafe" file name. 
	 * @return A string that is safe to use as a file name.
	 */
	public static String cleanupFileName(String src)
	{
		return src.replaceAll("[^\\w.\\-_]", "_");
	}

	/**
	 * Get the total number of files in the specified directory (including subdirectories). 
	 * 
	 * @param dir The directory to search
	 * @return The file count
	 */
	public static int getFileCount(File dir)
	{
		if (!dir.isDirectory())
			return 0;

		int count = 0;
		for (File node : dir.listFiles())
		{
			if (node.isDirectory())
				count += getFileCount(node);
			else
				count++;
		}

		return count;
	}

	/*
	 public static void main(String argv[])
	 {
	 System.out.println(cleanupFileName("Test File_name-$9/.dcm"));
	 }
	 */
}
