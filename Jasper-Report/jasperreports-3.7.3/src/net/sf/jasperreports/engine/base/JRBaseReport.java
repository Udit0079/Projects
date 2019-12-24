/*
 * JasperReports - Free Java Reporting Library.
 * Copyright (C) 2001 - 2009 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is part of JasperReports.
 *
 * JasperReports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JasperReports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with JasperReports. If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.jasperreports.engine.base;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRExpressionCollector;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRPropertiesHolder;
import net.sf.jasperreports.engine.JRPropertiesMap;
import net.sf.jasperreports.engine.JRQuery;
import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.JRReportFont;
import net.sf.jasperreports.engine.JRReportTemplate;
import net.sf.jasperreports.engine.JRScriptlet;
import net.sf.jasperreports.engine.JRSection;
import net.sf.jasperreports.engine.JRSortField;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.JRVariable;
import net.sf.jasperreports.engine.design.events.JRChangeEventsSupport;
import net.sf.jasperreports.engine.design.events.JRPropertyChangeSupport;
import net.sf.jasperreports.engine.type.OrientationEnum;
import net.sf.jasperreports.engine.type.PrintOrderEnum;
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;
import net.sf.jasperreports.engine.type.WhenResourceMissingTypeEnum;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRBaseReport.java 3715 2010-04-08 18:08:49Z teodord $
 */
public class JRBaseReport implements JRReport, Serializable, JRChangeEventsSupport
{


	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	public static final String PROPERTY_WHEN_NO_DATA_TYPE = "whenNoDataType";

	/**
	 *
	 */
	protected String name = null;
	protected String language = LANGUAGE_JAVA;
	protected int columnCount = 1;
	protected PrintOrderEnum printOrderValue = PrintOrderEnum.VERTICAL;
	protected int pageWidth = 595;
	protected int pageHeight = 842;
	protected OrientationEnum orientationValue = OrientationEnum.PORTRAIT;
	protected WhenNoDataTypeEnum whenNoDataTypeValue = WhenNoDataTypeEnum.NO_PAGES;
	protected int columnWidth = 555;
	protected int columnSpacing = 0;
	protected int leftMargin = 20;
	protected int rightMargin = 20;
	protected int topMargin = 30;
	protected int bottomMargin = 30;
	protected boolean isTitleNewPage = false;
	protected boolean isSummaryNewPage = false;
	protected boolean isSummaryWithPageHeaderAndFooter = false;
	protected boolean isFloatColumnFooter = false;
	protected boolean ignorePagination = false;

	/**
	 *
	 */
	protected String formatFactoryClass = null;

	/**
	 *
	 */
	protected Set importsSet = null;

	/**
	 * Report templates.
	 */
	protected JRReportTemplate[] templates;

	protected JRReportFont defaultFont = null;
	protected JRReportFont[] fonts = null;
	protected JRStyle defaultStyle = null;
	protected JRStyle[] styles = null;

	/**
	 * The main dataset of the report.
	 */
	protected JRDataset mainDataset;

	/**
	 * Sub datasets of the report.
	 */
	protected JRDataset[] datasets;

	protected JRBand background = null;
	protected JRBand title = null;
	protected JRBand pageHeader = null;
	protected JRBand columnHeader = null;
	protected JRSection detailSection = null;
	protected JRBand columnFooter = null;
	protected JRBand pageFooter = null;
	protected JRBand lastPageFooter = null;
	protected JRBand summary = null;
	protected JRBand noData = null;


	/**
	 *
	 */
	public JRBaseReport()
	{
	}

	public JRBaseReport(JRReport report, JRExpressionCollector expressionCollector)
	{
		this(report, new JRBaseObjectFactory(expressionCollector));
	}
	
	/**
	 * Constructs a copy of a report.
	 */
	public JRBaseReport(JRReport report, JRBaseObjectFactory factory)
	{
		/*   */
		name = report.getName();
		language = report.getLanguage();
		columnCount = report.getColumnCount();
		printOrderValue = report.getPrintOrderValue();
		pageWidth = report.getPageWidth();
		pageHeight = report.getPageHeight();
		orientationValue = report.getOrientationValue();
		whenNoDataTypeValue = report.getWhenNoDataTypeValue();
		columnWidth = report.getColumnWidth();
		columnSpacing = report.getColumnSpacing();
		leftMargin = report.getLeftMargin();
		rightMargin = report.getRightMargin();
		topMargin = report.getTopMargin();
		bottomMargin = report.getBottomMargin();
		isTitleNewPage = report.isTitleNewPage();
		isSummaryNewPage = report.isSummaryNewPage();
		isSummaryWithPageHeaderAndFooter = report.isSummaryWithPageHeaderAndFooter();
		isFloatColumnFooter = report.isFloatColumnFooter();
		ignorePagination = report.isIgnorePagination();

		formatFactoryClass = report.getFormatFactoryClass();

		/*   */
		String[] imports = report.getImports();
		if (imports != null && imports.length > 0)
		{
			importsSet = new HashSet(imports.length);
			importsSet.addAll(Arrays.asList(imports));
		}

		/*   */
		factory.setDefaultStyleProvider(this);

		copyTemplates(report, factory);

		/*   */
		defaultFont = factory.getReportFont(report.getDefaultFont());

		/*   */
		JRReportFont[] jrFonts = report.getFonts();
		if (jrFonts != null && jrFonts.length > 0)
		{
			fonts = new JRReportFont[jrFonts.length];
			for(int i = 0; i < fonts.length; i++)
			{
				fonts[i] = factory.getReportFont(jrFonts[i]);
			}
		}

		/*   */
		defaultStyle = factory.getStyle(report.getDefaultStyle());

		/*   */
		JRStyle[] jrStyles = report.getStyles();
		if (jrStyles != null && jrStyles.length > 0)
		{
			styles = new JRStyle[jrStyles.length];
			for(int i = 0; i < styles.length; i++)
			{
				styles[i] = factory.getStyle(jrStyles[i]);
			}
		}

		mainDataset = factory.getDataset(report.getMainDataset());

		JRDataset[] datasetArray = report.getDatasets();
		if (datasetArray != null && datasetArray.length > 0)
		{
			datasets = new JRDataset[datasetArray.length];
			for (int i = 0; i < datasets.length; i++)
			{
				datasets[i] = factory.getDataset(datasetArray[i]);
			}
		}

		background = factory.getBand(report.getBackground());
		title = factory.getBand(report.getTitle());
		pageHeader = factory.getBand(report.getPageHeader());
		columnHeader = factory.getBand(report.getColumnHeader());
		detailSection = factory.getSection(report.getDetailSection());
		columnFooter = factory.getBand(report.getColumnFooter());
		pageFooter = factory.getBand(report.getPageFooter());
		lastPageFooter = factory.getBand(report.getLastPageFooter());
		summary = factory.getBand(report.getSummary());
		noData = factory.getBand(report.getNoData());
	}


	protected void copyTemplates(JRReport report, JRBaseObjectFactory factory)
	{
		JRReportTemplate[] reportTemplates = report.getTemplates();
		if (reportTemplates == null || reportTemplates.length == 0)
		{
			templates = null;
		}
		else
		{
			templates = new JRReportTemplate[reportTemplates.length];
			for (int i = 0; i < reportTemplates.length; i++)
			{
				templates[i] = factory.getReportTemplate(reportTemplates[i]);
			}
		}
	}

	public JRBaseReport(JRReport report)
	{
		this(report, (JRExpressionCollector) null);
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
	public String getLanguage()
	{
		return language;
	}

	/**
	 *
	 */
	public int getColumnCount()
	{
		return columnCount;
	}

	/**
	 * @deprecated Replaced by {@link #getPrintOrderValue()}.
	 */
	public byte getPrintOrder()
	{
		return getPrintOrderValue().getValue();
	}

	/**
	 *
	 */
	public PrintOrderEnum getPrintOrderValue()
	{
		return printOrderValue;
	}

	/**
	 *
	 */
	public int getPageWidth()
	{
		return pageWidth;
	}

	/**
	 *
	 */
	public int getPageHeight()
	{
		return pageHeight;
	}

	/**
	 * @deprecated Replaced by {@link #getOrientationValue()}.
	 */
	public byte getOrientation()
	{
		return getOrientationValue().getValue();
	}

	/**
	 *
	 */
	public OrientationEnum getOrientationValue()
	{
		return orientationValue;
	}

	/**
	 * @deprecated Replaced by {@link #getWhenNoDataTypeValue()}.
	 */
	public byte getWhenNoDataType()
	{
		return getWhenNoDataTypeValue().getValue();
	}

	/**
	 *
	 */
	public WhenNoDataTypeEnum getWhenNoDataTypeValue()
	{
		return whenNoDataTypeValue;
	}

	/**
	 * @deprecated Replaced by {@link #setWhenNoDataType(WhenNoDataTypeEnum)}.
	 */
	public void setWhenNoDataType(byte whenNoDataType)
	{
		setWhenNoDataType(WhenNoDataTypeEnum.getByValue(whenNoDataType));
	}

	/**
	 *
	 */
	public void setWhenNoDataType(WhenNoDataTypeEnum whenNoDataTypeValue)
	{
		Object old = whenNoDataTypeValue;
		this.whenNoDataTypeValue = whenNoDataTypeValue;
		getEventSupport().firePropertyChange(PROPERTY_WHEN_NO_DATA_TYPE, old, whenNoDataTypeValue);
	}

	/**
	 *
	 */
	public int getColumnWidth()
	{
		return columnWidth;
	}

	/**
	 *
	 */
	public int getColumnSpacing()
	{
		return columnSpacing;
	}

	/**
	 *
	 */
	public int getLeftMargin()
	{
		return leftMargin;
	}

	/**
	 *
	 */
	public int getRightMargin()
	{
		return rightMargin;
	}

	/**
	 *
	 */
	public int getTopMargin()
	{
		return topMargin;
	}

	/**
	 *
	 */
	public int getBottomMargin()
	{
		return bottomMargin;
	}

	/**
	 *
	 */
	public boolean isTitleNewPage()
	{
		return isTitleNewPage;
	}

	/**
	 *
	 */
	public boolean isSummaryNewPage()
	{
		return isSummaryNewPage;
	}

	/**
	 *
	 */
	public boolean isSummaryWithPageHeaderAndFooter()
	{
		return isSummaryWithPageHeaderAndFooter;
	}

	/**
	 *
	 */
	public boolean isFloatColumnFooter()
	{
		return isFloatColumnFooter;
	}

	/**
	 *
	 */
	public String getScriptletClass()
	{
		return mainDataset.getScriptletClass();
	}

	/**
	 *
	 */
	public String getFormatFactoryClass()
	{
		return formatFactoryClass;
	}

	/**
	 *
	 */
	public String getResourceBundle()
	{
		return mainDataset.getResourceBundle();
	}

	/**
	 *
	 */
	public String[] getPropertyNames()
	{
		return mainDataset.getPropertiesMap().getPropertyNames();
	}

	/**
	 *
	 */
	public String getProperty(String propName)
	{
		return mainDataset.getPropertiesMap().getProperty(propName);
	}

	/**
	 *
	 */
	public void setProperty(String propName, String value)
	{
		mainDataset.getPropertiesMap().setProperty(propName, value);
	}

	/**
	 *
	 */
	public void removeProperty(String propName)
	{
		mainDataset.getPropertiesMap().removeProperty(propName);
	}

	/**
	 *
	 */
	public String[] getImports()
	{
		if (importsSet != null)
		{
			return (String[])importsSet.toArray(new String[importsSet.size()]);
		}
		return null;
	}

	/**
	 * @deprecated
	 */
	public JRReportFont getDefaultFont()
	{
		return defaultFont;
	}

	/**
	 * @deprecated
	 */
	public JRReportFont[] getFonts()
	{
		return fonts;
	}

	/**
	 *
	 */
	public JRStyle getDefaultStyle()
	{
		return defaultStyle;
	}

	/**
	 *
	 */
	public JRStyle[] getStyles()
	{
		return styles;
	}

	/**
	 * Gets an array of report scriptlets (excluding the scriptletClass one).
	 */
	public JRScriptlet[] getScriptlets()
	{
		return mainDataset.getScriptlets();
	}

	/**
	 * Gets an array of report parameters (including built-in ones).
	 */
	public JRParameter[] getParameters()
	{
		return mainDataset.getParameters();
	}

	/**
	 *
	 */
	public JRQuery getQuery()
	{
		return mainDataset.getQuery();
	}

	/**
	 *  Gets an array of report fields.
	 */
	public JRField[] getFields()
	{
		return mainDataset.getFields();
	}

	/**
	 *  Gets an array of sort report fields.
	 */
	public JRSortField[] getSortFields()
	{
		return mainDataset.getSortFields();
	}

	/**
	 * Gets an array of report variables.
	 */
	public JRVariable[] getVariables()
	{
		return mainDataset.getVariables();
	}

	/**
	 *
	 */
	public JRGroup[] getGroups()
	{
		return mainDataset.getGroups();
	}

	/**
	 *
	 */
	public JRBand getBackground()
	{
		return background;
	}

	/**
	 *
	 */
	public JRBand getTitle()
	{
		return title;
	}

	/**
	 *
	 */
	public JRBand getPageHeader()
	{
		return pageHeader;
	}

	/**
	 *
	 */
	public JRBand getColumnHeader()
	{
		return columnHeader;
	}

	/**
	 * @deprecated Replaced by {@link #getDetailSection()}.
	 */
	public JRBand getDetail()
	{
		return 
			detailSection == null 
			|| detailSection.getBands() == null 
			|| detailSection.getBands().length == 0 
				? null 
				: (JRBand)detailSection.getBands()[0];
	}

	/**
	 *
	 */
	public JRSection getDetailSection()
	{
		return detailSection;
	}

	/**
	 *
	 */
	public JRBand getColumnFooter()
	{
		return columnFooter;
	}

	/**
	 *
	 */
	public JRBand getPageFooter()
	{
		return pageFooter;
	}

	/**
	 *
	 */
	public JRBand getLastPageFooter()
	{
		return lastPageFooter;
	}

	/**
	 *
	 */
	public JRBand getSummary()
	{
		return summary;
	}


	/**
	 * @deprecated Replaced by {@link #getWhenResourceMissingTypeValue()}.
	 */
	public byte getWhenResourceMissingType()
	{
		return getWhenResourceMissingTypeValue().getValue();
	}

	/**
	 *
	 */
	public WhenResourceMissingTypeEnum getWhenResourceMissingTypeValue()
	{
		return mainDataset.getWhenResourceMissingTypeValue();
	}

	/**
	 * @deprecated Replaced by {@link #setWhenResourceMissingType(WhenResourceMissingTypeEnum)}.
	 */
	public void setWhenResourceMissingType(byte whenResourceMissingType)
	{
		setWhenResourceMissingType(WhenResourceMissingTypeEnum.getByValue(whenResourceMissingType));
	}

	/**
	 *
	 */
	public void setWhenResourceMissingType(WhenResourceMissingTypeEnum whenResourceMissingType)
	{
		mainDataset.setWhenResourceMissingType(whenResourceMissingType);
	}


	public JRDataset getMainDataset()
	{
		return mainDataset;
	}


	public JRDataset[] getDatasets()
	{
		return datasets;
	}


	public boolean isIgnorePagination()
	{
		return ignorePagination;
	}

	public boolean hasProperties()
	{
		return mainDataset.hasProperties();
	}

	public JRPropertiesMap getPropertiesMap()
	{
		return mainDataset.getPropertiesMap();
	}

	public JRPropertiesHolder getParentProperties()
	{
		return null;
	}

	public JRReportTemplate[] getTemplates()
	{
		return templates;
	}

	/**
	 * @return the noData
	 */
	public JRBand getNoData() {
		return noData;
	}
	
	private transient JRPropertyChangeSupport eventSupport;
	
	public JRPropertyChangeSupport getEventSupport()
	{
		synchronized (this)
		{
			if (eventSupport == null)
			{
				eventSupport = new JRPropertyChangeSupport(this);
			}
		}
		
		return eventSupport;
	}

	
	/*
	 * These fields are only for serialization backward compatibility.
	 */
	private int PSEUDO_SERIAL_VERSION_UID = JRConstants.PSEUDO_SERIAL_VERSION_UID; //NOPMD
	/**
	 * @deprecated
	 */
	private JRBand detail = null;
	/**
	 * @deprecated
	 */
	private byte whenNoDataType;
	/**
	 * @deprecated
	 */
	private byte printOrder;
	/**
	 * @deprecated
	 */
	private byte orientation;
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		
		if (detail != null)
		{
			detailSection = new JRBaseSection(detail);
			detail = null;
		}
		
		if (PSEUDO_SERIAL_VERSION_UID < JRConstants.PSEUDO_SERIAL_VERSION_UID_3_7_2)
		{
			whenNoDataTypeValue = WhenNoDataTypeEnum.getByValue(whenNoDataType);
			printOrderValue = PrintOrderEnum.getByValue(printOrder);
			orientationValue = OrientationEnum.getByValue(orientation);
		}
	}

}
