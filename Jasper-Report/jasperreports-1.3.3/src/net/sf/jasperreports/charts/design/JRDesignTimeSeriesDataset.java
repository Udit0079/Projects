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
package net.sf.jasperreports.charts.design;

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.charts.JRTimeSeries;
import net.sf.jasperreports.charts.JRTimeSeriesDataset;
import net.sf.jasperreports.engine.JRChartDataset;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRExpressionCollector;
import net.sf.jasperreports.engine.design.JRDesignChartDataset;
import net.sf.jasperreports.engine.design.JRVerifier;

/**
 * @author Flavius Sana (flavius_sana@users.sourceforge.net)
 * @version $Id: JRDesignTimeSeriesDataset.java 1364 2006-08-31 15:13:20Z lucianc $  
 */
public class JRDesignTimeSeriesDataset extends JRDesignChartDataset implements JRTimeSeriesDataset {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	private List timeSeriesList = new ArrayList();
	private Class timePeriod = null;
	

	/**
	 * 
	 */
	public JRDesignTimeSeriesDataset( JRChartDataset dataset )
	{
		super( dataset );
	}

	/**
	 * 
	 */
	public JRTimeSeries[] getSeries()
	{
		JRTimeSeries[] timeSeriesArray = new JRTimeSeries[ timeSeriesList.size() ];
		timeSeriesList.toArray( timeSeriesArray );
		
		return timeSeriesArray;
	}
	
	/**
	 * 
	 */
	public List getSeriesList()
	{
		return timeSeriesList;
	}

	/**
	 * 
	 */
	public void addTimeSeries( JRTimeSeries timeSeries ) 
	{
		timeSeriesList.add( timeSeries );
	}
	
	/**
	 * 
	 */
	public JRTimeSeries removeTimeSeries( JRTimeSeries timeSeries ) 
	{
		if( timeSeries != null && timeSeriesList.contains( timeSeries ))
		{
			timeSeriesList.remove( timeSeries );
		}
		
		return timeSeries;
	}

	/**
	 * 
	 */
	public Class getTimePeriod() 
	{
		return timePeriod;
	}
	
	/**
	 * 
	 */
	public void setTimePeriod( Class timePeriod )
	{
		this.timePeriod = timePeriod;
	}

	/** 
	 * 
	 */
	public byte getDatasetType() 
	{
		return JRChartDataset.TIMESERIES_DATASET;
	}
	
	/**
	 *
	 */
	public void collectExpressions(JRExpressionCollector collector)
	{
		collector.collect(this);
	}


	public void validate(JRVerifier verifier)
	{
		verifier.verify(this);
	}

}
