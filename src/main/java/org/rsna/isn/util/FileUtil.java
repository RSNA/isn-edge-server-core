/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rsna.isn.util;

import java.io.File;

/**
 *
 * @author Wyatt Tellis
 */
public class FileUtil
{
	private FileUtil()
	{
	}
	
	public static File newFile(File dir, String name)
	{
		String safe = cleanupFileName(name);
		
		return new File(dir, safe);
	}
	
	public static File newFile(File dir, int name)
	{
		return new File(dir, Integer.toString(name));
	}

	public static String cleanupFileName(String src)
	{
		return src.replaceAll("[^\\w.\\-_]", "_");
	}
	
	public static int getFileCount(File dir)
	{
		if(!dir.isDirectory())
			return 0;
		
		int count = 0;
		for(File node : dir.listFiles())
		{
			if(node.isDirectory())
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
