/*
 * ============================================================================
 * GNU Lesser General Public License
 * ============================================================================
 *
 * JasperReports - Free Java report-generating library.
 * Copyright (C) 2001-2006 JasperSoft Corporation http://www.jaspersoft.com
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307, USA.
 * 
 * JasperSoft Corporation
 * 303 Second Street, Suite 450 North
 * San Francisco, CA 94107
 * http://www.jaspersoft.com
 */
package net.sf.jasperreports.engine.export.oasis.zip;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: FileOasisZipEntry.java 1688 2007-03-30 14:43:16Z teodord $
 */
public class FileOasisZipEntry implements OasisZipEntry 
{
	/**
	 * 
	 */
	private String name = null;
	private File file = null;
//	private Writer writer = null;
//	private InputStream inputStream = null;
	
	/**
	 * 
	 */
	public FileOasisZipEntry(String name, File file)
	{
		this.name = name;
		this.file = file;
	}
	
	/**
	 * 
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * 
	 */
	public Writer getWriter() throws IOException
	{
//		if (writer == null)
//		{
//			writer = new BufferedWriter(new FileWriter(file));
//		}
//		return writer;
		return new BufferedWriter(new FileWriter(file));
	}
	
	/**
	 * 
	 */
	public InputStream getInputStream() throws IOException
	{
//		if (inputStream == null)
//		{
//			inputStream = new FileInputStream(file);
//		}
//		return inputStream;
		return new FileInputStream(file);
	}
	
	/**
	 * 
	 */
	public BufferedReader getReader() throws IOException
	{
		return new BufferedReader(new FileReader(file));
	}
	
	/**
	 * 
	 *
	public void close() throws IOException
	{
		try
		{
			if (writer != null)
			{
				writer.flush();
			}
		}
		finally
		{
			if (writer != null)
			{
				try
				{
					writer.close();
				}
				catch(IOException e)
				{
				}
			}

			if (inputStream != null)
			{
				try
				{
					inputStream.close();
				}
				catch (IOException e)
				{
				}
			}
		}
		
		writer = null;
		inputStream = null;
	}
	*/
}
