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
package net.sf.jasperreports.engine.fill;

import java.io.IOException;

import net.sf.jasperreports.engine.JRAbstractObjectFactory;
import net.sf.jasperreports.engine.JRBreak;
import net.sf.jasperreports.engine.JRChild;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExpressionCollector;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.xml.JRXmlWriter;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRFillBreak.java 1664 2007-03-26 09:48:03Z lucianc $
 */
public class JRFillBreak extends JRFillElement implements JRBreak
{


	/**
	 *
	 */
	protected JRFillBreak(
		JRBaseFiller filler,
		JRBreak breakElement, 
		JRFillObjectFactory factory
		)
	{
		super(filler, breakElement, factory);
	}


	protected JRFillBreak(JRFillBreak breakElement, JRFillCloneFactory factory)
	{
		super(breakElement, factory);
	}


	/**
	 *
	 */
	public int getWidth()
	{
		return filler.columnWidth;
	}

	/**
	 * 
	 */
	public byte getType()
	{
		return ((JRBreak)parent).getType();
	}
		
	/**
	 *
	 */
	public void setType(byte type)
	{
	}


	/**
	 *
	 */
	protected void evaluate(
		byte evaluation
		) throws JRException
	{
		this.reset();
		
		this.evaluatePrintWhenExpression(evaluation);
		
		setValueRepeating(true);
	}


	/**
	 *
	 */
	protected JRPrintElement fill()
	{
		return null;
//		JRPrintLine printLine = null;
//
//		printLine = new JRBasePrintLine(filler.getJasperPrint().getDefaultStyleProvider());
//		printLine.setX(0);
//		printLine.setY(this.getRelativeY());
//		printLine.setWidth(getWidth());
//		printLine.setHeight(1);
//		printLine.setPen(JRGraphicElement.PEN_DOTTED);
//		printLine.setForecolor(getForecolor());
//		
//		return printLine;
	}


	/**
	 *
	 */
	public JRChild getCopy(JRAbstractObjectFactory factory)
	{
		return factory.getBreak(this);
	}

	/**
	 *
	 */
	public void collectExpressions(JRExpressionCollector collector)
	{
		collector.collect(this);
	}

	/**
	 *
	 */
	public void writeXml(JRXmlWriter xmlWriter) throws IOException
	{
		xmlWriter.writeBreak(this);
	}

	/**
	 *
	 */
	protected void resolveElement (JRPrintElement element, byte evaluation)
	{
		// nothing
	}


	public JRCloneable createClone(JRFillCloneFactory factory)
	{
		return new JRFillBreak(this, factory);
	}


	/**
	 *
	 */
	public void rewind()
	{
	}


	/**
	 *
	 */
	protected boolean prepare(
		int availableStretchHeight,
		boolean isOverflow
		) throws JRException
	{
		super.prepare(availableStretchHeight, isOverflow);
		
		if (!this.isToPrint())
		{
			return false;
		}
		
		boolean isToPrint = true;

		if (isOverflow && this.isAlreadyPrinted())// && !this.isPrintWhenDetailOverflows())
		{
			isToPrint = false;
		}

		//boolean willOverflow = false;

		if (
			isToPrint && 
			availableStretchHeight < this.getRelativeY() - this.getY() - this.getBandBottomY()
			)
		{
			isToPrint = false;
			//willOverflow = true;
		}
		
		if (isToPrint)
		{
			if (getType() == JRBreak.TYPE_COLUMN)
			{
				if (!filler.isNewColumn || band.firstYElement != null)
				{
					setStretchHeight(getHeight() + availableStretchHeight - getRelativeY() + getY() + getBandBottomY());
				}
			}
			else
			{
				if (!filler.isNewPage || band.firstYElement != null)
				{
					setStretchHeight(getHeight() + availableStretchHeight - getRelativeY() + getY() + getBandBottomY());
					filler.columnIndex = filler.columnCount - 1;
				}
			}
		}
			
		this.setToPrint(isToPrint);
		this.setReprinted(false);

		return false;
	}
	
	
}
