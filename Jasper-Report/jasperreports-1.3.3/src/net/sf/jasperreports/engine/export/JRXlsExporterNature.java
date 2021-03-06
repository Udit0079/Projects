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

/*
 * Contributors:
 * Greg Hilton 
 */

package net.sf.jasperreports.engine.export;

import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRPrintImage;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRXlsExporterNature.java 1696 2007-04-02 12:14:28Z lucianc $
 */
public class JRXlsExporterNature implements ExporterNature
{
	
	/**
	 * 
	 */
	private static final JRXlsExporterNature INSTANCE = new JRXlsExporterNature();

	/**
	 * 
	 */
	public static JRXlsExporterNature getInstance()
	{
		return INSTANCE; 
	}

	/**
	 * 
	 */
	private JRXlsExporterNature()
	{
	}
	
	/**
	 * 
	 */
	public boolean isToExport(JRPrintElement element)
	{
		return !(element instanceof JRPrintImage);
	}
	
	/**
	 * 
	 */
	public boolean isDeep()
	{
		return true;
	}
	
	/**
	 * 
	 */
	public boolean isSplitSharedRowSpan()
	{
		return false;
	}

	/**
	 * 
	 */
	public boolean isSpanCells()
	{
		return true;
	}
	
	/**
	 * 
	 */
	public boolean isIgnoreLastRow()
	{
		return false;
	}

	public boolean isHorizontallyMergeEmptyCells()
	{
		return false;
	}
		
}
