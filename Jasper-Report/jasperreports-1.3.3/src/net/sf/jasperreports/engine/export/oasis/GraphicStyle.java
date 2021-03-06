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
package net.sf.jasperreports.engine.export.oasis;

import java.io.IOException;
import java.io.Writer;

import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRGraphicElement;
import net.sf.jasperreports.engine.JRPrintGraphicElement;
import net.sf.jasperreports.engine.util.JRColorUtil;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: GraphicStyle.java 1664 2007-03-26 09:48:03Z lucianc $
 */
public class GraphicStyle extends Style
{
	/**
	 *
	 */
	private String backcolor = null;
	private String forecolor = null;
	private String style = null;
	private String width = null;
	
	
	/**
	 *
	 */
	public GraphicStyle(Writer styleWriter, JRPrintGraphicElement element)
	{
		super(styleWriter);
		
		if (element.getMode() == JRElement.MODE_OPAQUE)
		{
			//fill = "solid";
			backcolor = JRColorUtil.getColorHexa(element.getBackcolor());
		}
		else
		{
			//fill = "none";
		}

		forecolor = JRColorUtil.getColorHexa(element.getForecolor());

		double doubleWidth = 0;

		switch (element.getPen())
		{
			case JRGraphicElement.PEN_DOTTED :
			{
				style = "dash";
				doubleWidth = 1;
				break;
			}
			case JRGraphicElement.PEN_4_POINT :
			{
				style = "solid";
				doubleWidth = 4;
				break;
			}
			case JRGraphicElement.PEN_2_POINT :
			{
				style = "solid";
				doubleWidth = 2;
				break;
			}
			case JRGraphicElement.PEN_THIN :
			{
				style = "solid";
				doubleWidth = 0.5f;
				break;
			}
			case JRGraphicElement.PEN_NONE :
			{
				style = "none";
				break;
			}
			case JRGraphicElement.PEN_1_POINT :
			default :
			{
				style = "solid";
				doubleWidth = 1;
				break;
			}
		}

		width = String.valueOf(Utility.translatePixelsToInchesWithNoRoundOff(doubleWidth));
	}
	
	/**
	 *
	 */
	public String getId()
	{
		//return fill + "|" + backcolor
		StringBuffer id = new StringBuffer();
		id.append(backcolor);
		id.append("|");
		id.append(forecolor);
		id.append("|");
		id.append(style);
		id.append("|");
		id.append(width);
		return id.toString();
	}

	/**
	 *
	 */
	public void write(String lineStyleName) throws IOException
	{
		styleWriter.write(" <style:style style:name=\"" + lineStyleName + "\"");
		styleWriter.write(" style:family=\"graphic\">\n");
		styleWriter.write("   <style:graphic-properties");		
		styleWriter.write(" draw:fill-color=\"#" + backcolor + "\"");
		styleWriter.write(" svg:stroke-color=\"#" + forecolor + "\"");
		styleWriter.write(" draw:stroke=\"" + style + "\"");
		styleWriter.write(" draw:stroke-dash=\"Dashed\"");
		styleWriter.write(" svg:stroke-width=\"" + width + "in\"");
		styleWriter.write("/>\n");
		styleWriter.write("</style:style>\n");
	}

}

