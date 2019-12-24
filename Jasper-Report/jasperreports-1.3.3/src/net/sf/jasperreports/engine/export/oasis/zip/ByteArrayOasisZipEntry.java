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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import net.sf.jasperreports.engine.JRRuntimeException;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: ByteArrayOasisZipEntry.java 1664 2007-03-26 09:48:03Z lucianc $
 */
public class ByteArrayOasisZipEntry implements OasisZipEntry 
{
	/**
	 * 
	 */
	private String name = null;
	private ByteArrayOutputStream baos = null;
	
	/**
	 * 
	 */
	public ByteArrayOasisZipEntry(String name)
	{
		this(name, null);
	}
	
	/**
	 * 
	 */
	public ByteArrayOasisZipEntry(String name, byte[] bytes)
	{
		this.name = name;

		if (bytes == null)
		{
			baos = new ByteArrayOutputStream();
		}
		else
		{
			baos = new ByteArrayOutputStream(bytes.length);
			try
			{
				baos.write(bytes);
			}
			catch (IOException e)
			{
				throw new JRRuntimeException(e);
			}
		}
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
		return new BufferedWriter(new OutputStreamWriter(baos, "UTF-8"));//FIXMEODT deal with stream closing
	}
	
	/**
	 * 
	 */
	public InputStream getInputStream() throws IOException
	{
		return new ByteArrayInputStream(baos.toByteArray());
	}
	
	/**
	 * 
	 */
	public BufferedReader getReader() throws IOException
	{
		return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(baos.toByteArray()), "UTF-8"));
	}
	
}
