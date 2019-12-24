/*
 * ============================================================================
 * GNU Lesser General Public License
 * ============================================================================
 * 
 * JasperReports - Free Java report-generating library. Copyright (C) 2001-2003
 * Teodor Danciu teodord@hotmail.com
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation; either version 2.1 of the License, or (at your
 * option) any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307, USA.
 * 
 * JasperSoft Corporation
 * 303 Second Street, Suite 450 North
 * San Francisco, CA 94107
 * http://www.jaspersoft.com
 */

/*
 * Contributor: Manuel Paul <mpaul@ratundtat.com>, 
 *				Rat & Tat Beratungsgesellschaft mbH, 
 *				Muehlenkamp 6c,
 *				22303 Hamburg,
 *				Germany.
 */
package net.sf.jasperreports.engine.export;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Dimension2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import jxl.CellView;
import jxl.JXLException;
import jxl.SheetSettings;
import jxl.Workbook;
import jxl.biff.DisplayFormat;
import jxl.format.Alignment;
import jxl.format.BoldStyle;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.Orientation;
import jxl.format.PageOrientation;
import jxl.format.PaperSize;
import jxl.format.Pattern;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Blank;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableHyperlink;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.CellValue;
import jxl.write.biff.RowsExceededException;
import net.sf.jasperreports.engine.JRAlignment;
import net.sf.jasperreports.engine.JRBox;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.JRGraphicElement;
import net.sf.jasperreports.engine.JRHyperlink;
import net.sf.jasperreports.engine.JRImage;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRPrintFrame;
import net.sf.jasperreports.engine.JRPrintImage;
import net.sf.jasperreports.engine.JRPrintLine;
import net.sf.jasperreports.engine.JRPrintText;
import net.sf.jasperreports.engine.JRRenderable;
import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.JRTextElement;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.data.BooleanTextValue;
import net.sf.jasperreports.engine.export.data.DateTextValue;
import net.sf.jasperreports.engine.export.data.NumberTextValue;
import net.sf.jasperreports.engine.export.data.StringTextValue;
import net.sf.jasperreports.engine.export.data.TextValue;
import net.sf.jasperreports.engine.export.data.TextValueHandler;
import net.sf.jasperreports.engine.util.JRImageLoader;
import net.sf.jasperreports.engine.util.JRStyledText;

import org.apache.commons.collections.ReferenceMap;


/**
 * @author Manuel Paul (mpaul@ratundtat.com)
 * @version $Id: JExcelApiExporter.java 1696 2007-04-02 12:14:28Z lucianc $
 */
public class JExcelApiExporter extends JRXlsAbstractExporter
{
	protected static final Colour WHITE = Colour.WHITE;
	protected static final Colour BLACK = Colour.BLACK;
	
	private static Map colorsCache = new ReferenceMap();


	private Map loadedCellStyles = new HashMap();

	private WritableWorkbook workbook = null;

	private WritableSheet sheet = null;

	private WritableCellFormat emptyCellStyle = null;

	private Pattern backgroundMode = Pattern.SOLID;
	
	private Map numberFormats;
	private Map dateFormats;

	protected Map formatPatternsMap = null;
	
	public JExcelApiExporter()
	{
		numberFormats = new HashMap();
		dateFormats = new HashMap();
	}

	protected void setParameters()
	{
		super.setParameters();
		formatPatternsMap = (Map)getParameter(JRXlsExporterParameter.FORMAT_PATTERNS_MAP);
	}

	protected void setBackground()
	{
		if (isWhitePageBackground)
		{
			this.backgroundMode = Pattern.SOLID;
		}
		else
		{
			this.backgroundMode = Pattern.NONE;
		}
	}

	protected void openWorkbook(OutputStream os) throws JRException
	{
		try
		{
			workbook = Workbook.createWorkbook(os);
			emptyCellStyle = new WritableCellFormat();
			emptyCellStyle.setBackground(WHITE, backgroundMode);
		}
		catch (IOException e)
		{
			throw new JRException("Error generating XLS report : " + jasperPrint.getName(), e);
		}
		catch (WriteException e)
		{
			throw new JRException("Error generating XLS report : " + jasperPrint.getName(), e);
		}
	}

	protected void createSheet(String name)
	{
		sheet = workbook.createSheet(name, Integer.MAX_VALUE);
		setSheetSettings(sheet);
	}

	protected void closeWorkbook(OutputStream os) throws JRException
	{
		try
		{
			workbook.write();
			workbook.close();
		}
		catch (IOException e)
		{
			throw new JRException("Error generating XLS report : " + jasperPrint.getName(), e);
		}
		catch (WriteException e)
		{
			throw new JRException("Error generating XLS report : " + jasperPrint.getName(), e);
		}
	}

	protected void setColumnWidth(short index, short width)
	{
		CellView cv = new CellView();
		cv.setSize(width);
		sheet.setColumnView(index, cv);
	}

	protected void setRowHeight(int y, int lastRowHeight) throws JRException
	{
		try
		{
			sheet.setRowView(y, (lastRowHeight * 20)); // twips
		}
		catch (RowsExceededException e)
		{
			throw new JRException("Error generating XLS report : " + jasperPrint.getName(), e);
		}
	}

	protected void setCell(int x, int y)
	{
	}

	protected void addBlankCell(JRExporterGridCell gridCell, int colIndex, int rowIndex) throws JRException
	{
		try
		{
			Colour forecolor = BLACK;
			if (gridCell.getForecolor() != null)
			{
				forecolor = getNearestColour(gridCell.getForecolor());
			}
			
			Colour backcolor = WHITE;
			if (gridCell.getCellBackcolor() != null)
			{
				backcolor = getNearestColour(gridCell.getCellBackcolor());
			}
			
			Pattern mode = backgroundMode;

			WritableFont cellFont = getLoadedFont(getDefaultFont(), forecolor.getValue());
			WritableCellFormat cellStyle = getLoadedCellStyle(mode, backcolor, 
					Alignment.LEFT.getValue(), VerticalAlignment.TOP.getValue(), Orientation.HORIZONTAL.getValue(),
					cellFont, gridCell);
			
			sheet.addCell(new Blank(colIndex, rowIndex, cellStyle));
		}
		catch (RowsExceededException e)
		{
			throw new JRException("Error generating XLS report : " + jasperPrint.getName(), e);
		}
		catch (WriteException e)
		{
			throw new JRException("Error generating XLS report : " + jasperPrint.getName(), e);
		}
	}

	protected void exportLine(JRPrintLine line, JRExporterGridCell gridCell, int x, int y) throws JRException
	{
		addMergeRegion(gridCell, x, y);

		Colour forecolor2 = getNearestColour(line.getForecolor());
		WritableFont cellFont2 = this.getLoadedFont(getDefaultFont(), forecolor2.getValue());
		WritableCellFormat cellStyle2 = this.getLoadedCellStyle(Pattern.SOLID, forecolor2, Alignment.LEFT.getValue(),
																VerticalAlignment.TOP.getValue(), Orientation.HORIZONTAL.getValue(),
																cellFont2, gridCell);
		
		Blank cell2 = new Blank(x, y, cellStyle2);

		try
		{
			sheet.addCell(cell2);
		}
		catch (Exception e)
		{
			throw new JRException("Can't add cell.", e);
		}
	}

	protected void exportRectangle(JRPrintElement element, JRExporterGridCell gridCell, int x, int y) throws JRException
	{
		addMergeRegion(gridCell, x, y);

		Colour forecolor = getNearestColour(element.getForecolor());
		Colour backcolor = WHITE;
		Pattern mode = this.backgroundMode;

		if (gridCell.getCellBackcolor() != null)
		{
			mode = Pattern.SOLID;
			backcolor = getNearestColour(gridCell.getCellBackcolor());
		}

		WritableFont cellFont2 = this.getLoadedFont(getDefaultFont(), forecolor.getValue());
		WritableCellFormat cellStyle2 = this.getLoadedCellStyle(mode, backcolor, Alignment.LEFT.getValue(), 
																VerticalAlignment.TOP.getValue(), Orientation.HORIZONTAL.getValue(),
																cellFont2, gridCell);
		Blank cell2 = new Blank(x, y, cellStyle2);

		try
		{
			sheet.addCell(cell2);
		}
		catch (Exception e)
		{
			throw new JRException("Can't add cell.", e);
		}
	}

	protected void exportText(JRPrintText text, JRExporterGridCell gridCell, int x, int y) throws JRException
	{
		addMergeRegion(gridCell, x, y);

		JRStyledText styledText = getStyledText(text);

		if (styledText != null)
		{
			Colour forecolor = getNearestColour(text.getForecolor());
			WritableFont cellFont = this.getLoadedFont(text, forecolor.getValue());
			
			TextAlignHolder alignment = getTextAlignHolder(text);
			int horizontalAlignment = getHorizontalAlignment(alignment);
			int verticalAlignment = getVerticalAlignment(alignment);
			int rotation = getRotation(alignment);

			Pattern mode = this.backgroundMode;
			Colour backcolor = WHITE;

			if (gridCell.getCellBackcolor() != null)
			{
				mode = Pattern.SOLID;
				backcolor = getNearestColour(gridCell.getCellBackcolor());
			}

			StyleInfo baseStyle = new StyleInfo(mode, backcolor, 
					horizontalAlignment, verticalAlignment, 
					rotation, cellFont, 
					gridCell.getBox());
			
			try
			{
				String textStr = styledText.getText();
				
				switch (text.getHyperlinkType())
				{
					case JRHyperlink.HYPERLINK_TYPE_REFERENCE:
					{
						if (text.getHyperlinkReference() != null)
						{
							URL url = new URL(text.getHyperlinkReference());
							WritableHyperlink hyperlink = new WritableHyperlink(x, y, x, y, url, textStr);
							sheet.addHyperlink(hyperlink);
							break;
						}
					}
					
					case JRHyperlink.HYPERLINK_TYPE_NONE:
					default:
					{
						addCell(x, y, text, textStr, baseStyle);
					}
				}
			}
			catch (Exception e)
			{
				throw new JRException("Can't add cell.", e);
			}
		}
	}

	protected void addCell(int x, int y, JRPrintText text, String textStr, StyleInfo baseStyle) throws WriteException, RowsExceededException, JRException
	{
		CellValue cellValue;
		if (isDetectCellType)
		{
			cellValue = getDetectedCellValue(x, y, text, textStr, baseStyle);
		}
		else if (isAutoDetectCellType)
		{
			cellValue = getAutoDetectedCellValue(x, y, textStr, baseStyle);
		}
		else
		{
			cellValue = getLabelCell(x, y, textStr, baseStyle);
		}
		
		sheet.addCell(cellValue);
	}


	protected CellValue getDetectedCellValue(int x, int y, JRPrintText text, String textStr, StyleInfo baseStyle) throws JRException
	{
		TextValue textValue = getTextValue(text, textStr);
		CellTextValueHandler handler = new CellTextValueHandler(x, y, baseStyle);
		textValue.handle(handler);
		return handler.getResult();
	}

	protected class CellTextValueHandler implements TextValueHandler
	{
		private final int x;
		private final int y;
		private final StyleInfo baseStyle;
		
		private CellValue result;
		
		public CellTextValueHandler(int x, int y, StyleInfo baseStyle)
		{
			this.x = x;
			this.y = y;
			this.baseStyle = baseStyle;
		}

		public void handle(StringTextValue textValue) throws JRException
		{
			WritableCellFormat cellStyle = getLoadedCellStyle(baseStyle);
			result = new Label(x, y, textValue.getText(), cellStyle);
		}

		public void handle(NumberTextValue textValue) throws JRException
		{
			if (textValue.getPattern() != null)
			{
				baseStyle.setDisplayFormat(getNumberFormat(textValue.getPattern()));
			}

			WritableCellFormat cellStyle = getLoadedCellStyle(baseStyle);
			if (textValue.getValue() == null)
			{
				result = blank(cellStyle);
			}
			else
			{
				result = new Number(x, y, textValue.getValue().doubleValue(), cellStyle);
			}
		}

		public void handle(DateTextValue textValue) throws JRException
		{
			baseStyle.setDisplayFormat(getDateFormat(textValue.getPattern()));
			WritableCellFormat cellStyle = getLoadedCellStyle(baseStyle);
			if (textValue.getValue() == null)
			{
				result = blank(cellStyle);
			}
			else
			{
				result = new DateTime(x, y, textValue.getValue(), cellStyle);
			}
		}

		public void handle(BooleanTextValue textValue) throws JRException
		{
			WritableCellFormat cellStyle = getLoadedCellStyle(baseStyle);
			if (textValue.getValue() == null)
			{
				result = blank(cellStyle);
			}
			else
			{
				result = new jxl.write.Boolean(x, y, textValue.getValue().booleanValue(), cellStyle);
			}
		}

		protected Blank blank(WritableCellFormat cellStyle)
		{
			return new Blank(x, y, cellStyle);
		}
		
		public CellValue getResult()
		{
			return result;
		}	
	}

	protected jxl.write.NumberFormat getNumberFormat(String pattern)
	{
		String convertedPattern = getConvertedPattern(pattern);
		jxl.write.NumberFormat cellFormat = (jxl.write.NumberFormat) numberFormats.get(convertedPattern);
		if (cellFormat == null)
		{
			cellFormat = new jxl.write.NumberFormat(convertedPattern);
			numberFormats.put(convertedPattern, cellFormat);
		}
		return cellFormat;
	}

	protected jxl.write.DateFormat getDateFormat(String pattern)
	{
		String convertedPattern = getConvertedPattern(pattern);
		jxl.write.DateFormat cellFormat = (jxl.write.DateFormat) dateFormats.get(convertedPattern);
		if (cellFormat == null)
		{
			cellFormat = new jxl.write.DateFormat(convertedPattern);
			dateFormats.put(convertedPattern, cellFormat);
		}
		return cellFormat;
	}

	protected CellValue getAutoDetectedCellValue(int x, int y, String textStr, StyleInfo baseStyle) throws JRException
	{
		CellValue cellValue;
		try
		{
			double d = Double.parseDouble(textStr);
			WritableCellFormat cellStyle = getLoadedCellStyle(baseStyle);
			cellValue = new Number(x, y, d, cellStyle);
		}
		catch (NumberFormatException nfe)
		{
			cellValue = getLabelCell(x, y, textStr, baseStyle);
		}
		return cellValue;
	}

	protected CellValue getLabelCell(int x, int y, String textStr, StyleInfo baseStyle) throws JRException
	{
		WritableCellFormat cellStyle = getLoadedCellStyle(baseStyle);
		CellValue cellValue = new Label(x, y, textStr, cellStyle);
		return cellValue;
	}

	protected void addMergeRegion(JRExporterGridCell gridCell, int x, int y) throws JRException
	{
		if (gridCell.getColSpan() > 1 || gridCell.getRowSpan() > 1)
		{
			try
			{
				sheet.mergeCells(x, y, (x + gridCell.getColSpan() - 1), (y + gridCell.getRowSpan() - 1));
			}
			catch (JXLException e)
			{
				throw new JRException("Can't merge cells.", e);
			}
		}
	}

	private int getHorizontalAlignment(TextAlignHolder alignment)
	{
		switch (alignment.horizontalAlignment)
		{
			case JRAlignment.HORIZONTAL_ALIGN_RIGHT:
				return Alignment.RIGHT.getValue();
			case JRAlignment.HORIZONTAL_ALIGN_CENTER:
				return Alignment.CENTRE.getValue();
			case JRAlignment.HORIZONTAL_ALIGN_JUSTIFIED:
				return Alignment.JUSTIFY.getValue();
			case JRAlignment.HORIZONTAL_ALIGN_LEFT:
			default:
				return Alignment.LEFT.getValue();
		}
	}

	private int getVerticalAlignment(TextAlignHolder alignment)
	{
		switch (alignment.verticalAlignment)
		{
			case JRAlignment.VERTICAL_ALIGN_BOTTOM:
				return VerticalAlignment.BOTTOM.getValue();
			case JRAlignment.VERTICAL_ALIGN_MIDDLE:
				return VerticalAlignment.CENTRE.getValue();
			case JRAlignment.VERTICAL_ALIGN_JUSTIFIED:
				return VerticalAlignment.JUSTIFY.getValue();
			case JRAlignment.VERTICAL_ALIGN_TOP:
			default:
				return VerticalAlignment.TOP.getValue();
		}
	}

	private int getRotation(TextAlignHolder alignment)
	{
		switch (alignment.rotation)
		{
			case JRTextElement.ROTATION_LEFT:
				return Orientation.PLUS_90.getValue();
			case JRTextElement.ROTATION_RIGHT:
				return Orientation.MINUS_90.getValue();
			case JRTextElement.ROTATION_UPSIDE_DOWN:
			case JRTextElement.ROTATION_NONE:
			default:
				return Orientation.HORIZONTAL.getValue();
		}
	}

	protected void exportImage(JRPrintImage element, JRExporterGridCell gridCell, int x, int y) throws JRException
	{
		addMergeRegion(gridCell, x, y);

		//if ((element.getRenderer() != null) && (element.getRenderer().getImageData() != null))
		//{
			try
			{
				JRRenderable renderer = element.getRenderer();
				
				int leftPadding = element.getLeftPadding();
				int topPadding = element.getTopPadding();
				int rightPadding = element.getRightPadding();
				int bottomPadding = element.getBottomPadding();

				int availableImageWidth = element.getWidth() - leftPadding - rightPadding;
				availableImageWidth = availableImageWidth < 0 ? 0 : availableImageWidth;

				int availableImageHeight = element.getHeight() - topPadding - bottomPadding;
				availableImageHeight = availableImageHeight < 0 ? 0 : availableImageHeight;


				if (availableImageWidth > 0 && availableImageHeight > 0 && renderer != null)
				{
					int normalWidth = availableImageWidth;
					int normalHeight = availableImageHeight;

					Dimension2D dimension = renderer.getDimension();
					if (dimension != null)
					{
						normalWidth = (int) dimension.getWidth();
						normalHeight = (int) dimension.getHeight();
					}

					float xalignFactor = 0f;
					switch (element.getHorizontalAlignment())
					{
						case JRAlignment.HORIZONTAL_ALIGN_RIGHT:
						{
							xalignFactor = 1f;
							break;
						}
						case JRAlignment.HORIZONTAL_ALIGN_CENTER:
						{
							xalignFactor = 0.5f;
							break;
						}
						case JRAlignment.HORIZONTAL_ALIGN_LEFT:
						default:
						{
							xalignFactor = 0f;
							break;
						}
					}

					float yalignFactor = 0f;
					switch (element.getVerticalAlignment())
					{
						case JRAlignment.VERTICAL_ALIGN_BOTTOM:
						{
							yalignFactor = 1f;
							break;
						}
						case JRAlignment.VERTICAL_ALIGN_MIDDLE:
						{
							yalignFactor = 0.5f;
							break;
						}
						case JRAlignment.VERTICAL_ALIGN_TOP:
						default:
						{
							yalignFactor = 0f;
							break;
						}
					}
					
					BufferedImage bi = new BufferedImage(availableImageWidth, availableImageHeight, BufferedImage.TYPE_INT_ARGB);
					Graphics2D grx = bi.createGraphics();
					if (JRElement.MODE_OPAQUE == element.getMode())
					{
						grx.setColor(element.getBackcolor());
						grx.fillRect(0, 0, availableImageWidth, availableImageHeight);
					}
				
					switch (element.getScaleImage())
					{
						case JRImage.SCALE_IMAGE_CLIP:
						{
							int xoffset = (int) (xalignFactor * (availableImageWidth - normalWidth));
							int yoffset = (int) (yalignFactor * (availableImageHeight - normalHeight));

							renderer.render(grx, new Rectangle(xoffset, yoffset,
								normalWidth, normalHeight));

							break;
						}
						case JRImage.SCALE_IMAGE_FILL_FRAME:
						{
							renderer.render(grx, new Rectangle(0, 0,
								availableImageWidth, availableImageHeight));

							break;
						}
						case JRImage.SCALE_IMAGE_RETAIN_SHAPE:
						default:
						{
							if (element.getHeight() > 0)
							{
								double ratio = (double) normalWidth / (double) normalHeight;

								if (ratio > (double) availableImageWidth / (double) availableImageHeight)
								{
									normalWidth = availableImageWidth;
									normalHeight = (int) (availableImageWidth / ratio);
								}
								else
								{
									normalWidth = (int) (availableImageHeight * ratio);
									normalHeight = availableImageHeight;
								}

								int xoffset = (int) (xalignFactor * (availableImageWidth - normalWidth));
								int yoffset = (int) (yalignFactor * (availableImageHeight - normalHeight));

								renderer.render(grx, new Rectangle(xoffset, yoffset,
									normalWidth, normalHeight));
							}

							break;
						}
					}
					
					Pattern mode = this.backgroundMode;
					Colour background = WHITE;
					Colour forecolor = getNearestColour(element.getForecolor());
					
					if (element.getBorderColor() != null ){
						forecolor = getNearestColour(element.getBorderColor());
					}
					
					WritableFont cellFont2 = this.getLoadedFont(getDefaultFont(), forecolor.getValue());
					
					if(element.getMode() == JRElement.MODE_OPAQUE ){
						mode = Pattern.SOLID;
						background = getNearestColour(element.getBackcolor());
					}
					
					WritableCellFormat cellStyle2 = this.getLoadedCellStyle(mode, background, Alignment.LEFT.getValue(),
																VerticalAlignment.TOP.getValue(), Orientation.HORIZONTAL.getValue(),
																cellFont2, gridCell);
					
				
					sheet.addCell(new Blank(x, y, cellStyle2));
					WritableImage image = 
						new WritableImage(
							x, 
							y, 
							gridCell.getColSpan(), 
							gridCell.getRowSpan(), 
							JRImageLoader.loadImageDataFromAWTImage(bi, JRRenderable.IMAGE_TYPE_PNG)
							);
					
						
					sheet.addImage(image);
				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				throw new JRException("The cell cannot be added", ex);
			}
			catch (Error err)
			{
				err.printStackTrace();
				throw new JRException("The cell cannot be added", err);
			}
		//}
	}

	protected static Colour getNearestColour(Color awtColor)
	{
		Colour color = (Colour) colorsCache.get(awtColor);
		
		if (color == null)
		{
			Colour[] colors = Colour.getAllColours();
			if ((colors != null) && (colors.length > 0))
			{
				Colour crtColor = null;
				int[] rgb = null;
				int diff = 0;
				int minDiff = 999;

				for (int i = 0; i < colors.length; i++)
				{
					crtColor = colors[i];
					rgb = new int[3];
					rgb[0] = crtColor.getDefaultRGB().getRed();
					rgb[1] = crtColor.getDefaultRGB().getGreen();
					rgb[2] = crtColor.getDefaultRGB().getBlue();

					diff = Math.abs(rgb[0] - awtColor.getRed()) + Math.abs(rgb[1] - awtColor.getGreen()) + Math.abs(rgb[2] - awtColor.getBlue());

					if (diff < minDiff)
					{
						minDiff = diff;
						color = crtColor;
					}
				}
			}
			
			colorsCache.put(awtColor, color);
		}
		
		return color;
	}
	
	
	/*private static Colour getNearestColour(Color awtColor) {
		Colour retVal = null;
		Colour[] colors = Colour.getAllColours();
		
		int diff = 50;
		
		if (colors != null && colors.length > 0 ){
			Colour crtColor = null;
			for (int i = 0; i < colors.length; i++) {
				crtColor = colors[i];
				
				int red = crtColor.getDefaultRGB().getRed();
				if (Math.abs(awtColor.getRed() - red) < diff) {
					int green = crtColor.getDefaultRGB().getGreen();
					if (Math.abs(awtColor.getGreen() - green) < diff) {
						int blue = crtColor.getDefaultRGB().getBlue();
						if (Math.abs(awtColor.getBlue() - blue) < diff) {
							retVal = crtColor;
						}
					}
				}
			}
		}
		
		return retVal;
	}*/

	private WritableFont getLoadedFont(JRFont font, int forecolor) throws JRException
	{
		WritableFont cellFont = null;
		
		if (this.loadedFonts != null && this.loadedFonts.size() > 0)
		{
			for (int i = 0; i < this.loadedFonts.size(); i++)
			{
				WritableFont cf = (WritableFont) this.loadedFonts.get(i);

				int fontSize = font.getFontSize();
				if (isFontSizeFixEnabled)
					fontSize -= 1;

				String fontName = font.getFontName();
				if (fontMap != null && fontMap.containsKey(fontName))
				{
					fontName = (String) fontMap.get(fontName);
				}

				if ((cf.getName().equals(fontName)) 
						&& (cf.getColour().getValue() == forecolor) 
						&& (cf.getPointSize() == fontSize)
						&& (cf.getUnderlineStyle() == UnderlineStyle.SINGLE ? (font.isUnderline()) : (!font.isUnderline())) 
						&& (cf.isStruckout() == font.isStrikeThrough())
						&& (cf.getBoldWeight() == BoldStyle.BOLD.getValue() ? (font.isBold()) : (!font.isBold())) 
						&& (cf.isItalic() == font.isItalic()))
				{
					cellFont = cf;
					break;
				}
			}
		}

		try
		{
			if (cellFont == null)
			{
				int fontSize = font.getFontSize();
				if (isFontSizeFixEnabled)
					fontSize -= 1;

				String fontName = font.getFontName();
				if (fontMap != null && fontMap.containsKey(fontName))
				{
					fontName = (String) fontMap.get(fontName);
				}

				cellFont = 
					new WritableFont(
						WritableFont.createFont(fontName),
						fontSize, 
						font.isBold() ? WritableFont.BOLD : WritableFont.NO_BOLD, 
					    font.isItalic(),
					    font.isUnderline() ? UnderlineStyle.SINGLE : UnderlineStyle.NO_UNDERLINE,
					    Colour.getInternalColour(forecolor)
					    );
				cellFont.setStruckout(font.isStrikeThrough());
				
				this.loadedFonts.add(cellFont);
			}
		}
		catch (Exception e)
		{
			throw new JRException("Can't get loaded fonts.", e);
		}

		return cellFont;
	}

	protected static class BoxStyle
	{
		protected final BorderLineStyle topBorder;
		protected final BorderLineStyle bottomBorder;
		protected final BorderLineStyle leftBorder;
		protected final BorderLineStyle rightBorder;
		protected final Colour topBorderColour;
		protected final Colour bottomBorderColour;
		protected final Colour leftBorderColour;
		protected final Colour rightBorderColour;
		private final int hash;

		public BoxStyle(JRBox box)
		{
			if(box != null && box.getTopBorder() != JRGraphicElement.PEN_NONE)
			{
				topBorder = getBorderLineStyle(box.getTopBorder());
				topBorderColour = getNearestColour(box.getTopBorderColor());
			}
			else
			{
				topBorder = BorderLineStyle.NONE;
				topBorderColour = BLACK;
			}
				
			if(box != null && box.getBottomBorder() != JRGraphicElement.PEN_NONE)
			{
				bottomBorder = getBorderLineStyle(box.getBottomBorder());
				bottomBorderColour = getNearestColour(box.getBottomBorderColor());
			}
			else
			{
				bottomBorder = BorderLineStyle.NONE;
				bottomBorderColour = BLACK;
			}
				
			if(box != null && box.getLeftBorder()!= JRGraphicElement.PEN_NONE)
			{
				leftBorder = getBorderLineStyle(box.getLeftBorder());
				leftBorderColour = getNearestColour(box.getLeftBorderColor());
			}
			else
			{
				leftBorder = BorderLineStyle.NONE;
				leftBorderColour = BLACK;
			}
				
			if(box != null && box.getRightBorder() != JRGraphicElement.PEN_NONE)
			{
				rightBorder = getBorderLineStyle(box.getRightBorder());
				rightBorderColour = getNearestColour(box.getRightBorderColor());
			}	
			else
			{
				rightBorder = BorderLineStyle.NONE;
				rightBorderColour = BLACK;
			}
			
			hash = computeHash();
		}

		private int computeHash()
		{
			int hashCode = topBorder.hashCode();
			hashCode = 31*hashCode + topBorderColour.hashCode();
			hashCode = 31*hashCode + bottomBorder.hashCode();
			hashCode = 31*hashCode + bottomBorderColour.hashCode();
			hashCode = 31*hashCode + leftBorder.hashCode();
			hashCode = 31*hashCode + leftBorderColour.hashCode();
			hashCode = 31*hashCode + rightBorder.hashCode();
			hashCode = 31*hashCode + rightBorderColour.hashCode();
			return hashCode;
		}
		
		public int hashCode()
		{
			return hash;
		}
		
		public boolean equals(Object o)
		{
			BoxStyle b = (BoxStyle) o;
			
			return 
				b.topBorder.equals(topBorder) &&
				b.topBorderColour.equals(topBorderColour) &&
				b.bottomBorder.equals(bottomBorder) &&
				b.bottomBorderColour.equals(bottomBorderColour) &&
				b.leftBorder.equals(leftBorder) &&
				b.leftBorderColour.equals(leftBorderColour) &&
				b.rightBorder.equals(rightBorder) &&
				b.rightBorderColour.equals(rightBorderColour);
		}
		
		public String toString()
		{
			return "(" +
				topBorder.getValue() + "/" + topBorderColour.getValue() + "," +
				bottomBorder.getValue() + "/" + bottomBorderColour.getValue() + "," +
				leftBorder.getValue() + "/" + leftBorderColour.getValue() + "," +
				rightBorder.getValue() + "/" + rightBorderColour.getValue() + ")";
		}
	}
	
	protected static class StyleInfo
	{
		protected final Pattern mode;
		protected final Colour backcolor;
		protected final int horizontalAlignment;
		protected final int verticalAlignment;
		protected final int rotation;
		protected final WritableFont font;
		protected final BoxStyle box;
		private DisplayFormat displayFormat;
		private int hashCode;
		
		protected StyleInfo(Pattern mode, Colour backcolor, int horizontalAlignment, int verticalAlignment, int rotation, WritableFont font, JRBox box)
		{
			this.mode = mode;
			this.backcolor = backcolor;
			this.horizontalAlignment = horizontalAlignment;
			this.verticalAlignment = verticalAlignment;
			this.rotation = rotation;
			this.font = font;
			this.box = new BoxStyle(box);
		
			computeHash();
		}
		
		protected void computeHash()
		{
			int hash = this.mode.hashCode();
			hash = 31*hash + this.backcolor.hashCode();
			hash = 31*hash + this.horizontalAlignment;
			hash = 31*hash + this.verticalAlignment;
			hash = 31*hash + this.rotation;
			hash = 31*hash + this.font.hashCode();
			hash = 31*hash + (this.box == null ? 0 : this.box.hashCode());
			hash = 31*hash + (this.displayFormat == null ? 0 : this.displayFormat.hashCode());
			
			hashCode = hash;
		}
		
		public int hashCode()
		{
			return hashCode;
		}
		
		public boolean equals(Object o)
		{
			StyleInfo k = (StyleInfo) o;
			
			return k.mode.equals(mode) && k.backcolor.equals(backcolor) &&
				k.horizontalAlignment == horizontalAlignment && k.verticalAlignment == verticalAlignment &&
				k.rotation == rotation && k.font.equals(font) &&
				(k.box == null ? box == null : (box != null && k.box.equals(box))) &&
				(k.displayFormat == null ? displayFormat == null : (displayFormat!= null && k.displayFormat.equals(displayFormat)));
		}
		
		public DisplayFormat getDisplayFormat()
		{
			return displayFormat;
		}
		
		public void setDisplayFormat(DisplayFormat displayFormat)
		{
			this.displayFormat = displayFormat;
			computeHash();
		}
		
		public String toString()
		{
			return "(" +
				mode + "," + backcolor + "," + 
				horizontalAlignment + "," + verticalAlignment + "," + 
				rotation + "," + font + "," +
				box + "," + displayFormat + ")";
		}
	}
	
	private WritableCellFormat getLoadedCellStyle(Pattern mode, Colour backcolor, int horizontalAlignment,
			int verticalAlignment, int rotation, WritableFont font, JRExporterGridCell gridCell) throws JRException
	{
		StyleInfo styleKey = new StyleInfo(mode, backcolor, horizontalAlignment, verticalAlignment, rotation, font, gridCell.getBox());
		return getLoadedCellStyle(styleKey);
	}


	protected WritableCellFormat getLoadedCellStyle(StyleInfo styleKey) throws JRException
	{	
		WritableCellFormat cellStyle = (WritableCellFormat) loadedCellStyles.get(styleKey);
		
		if (cellStyle == null)
		{
			try
			{
				if (styleKey.getDisplayFormat() == null)
				{
					cellStyle = new WritableCellFormat(styleKey.font);
				}
				else
				{
					cellStyle = new WritableCellFormat(styleKey.font, styleKey.getDisplayFormat());
				}
				cellStyle.setBackground(styleKey.backcolor, styleKey.mode);
				cellStyle.setAlignment(Alignment.getAlignment(styleKey.horizontalAlignment));
				cellStyle.setVerticalAlignment(VerticalAlignment.getAlignment(styleKey.verticalAlignment));
				cellStyle.setOrientation(Orientation.getOrientation(styleKey.rotation));
				cellStyle.setWrap(true);
				
				BoxStyle box = styleKey.box;
				cellStyle.setBorder(Border.TOP, box.topBorder, box.topBorderColour);
				cellStyle.setBorder(Border.BOTTOM, box.bottomBorder, box.bottomBorderColour);
				cellStyle.setBorder(Border.LEFT, box.leftBorder, box.leftBorderColour);
				cellStyle.setBorder(Border.RIGHT, box.rightBorder, box.rightBorderColour);
			}
			catch (Exception e)
			{
				throw new JRException("Error setting cellFormat-template.", e);
			}
			
			loadedCellStyles.put(styleKey, cellStyle);
		}
		
		return cellStyle;
	}
	
	/**
	 * @param lineStyle
	 */
	protected static BorderLineStyle getBorderLineStyle(byte lineStyle) {
		BorderLineStyle retVal = null;
		switch(lineStyle) {
			case JRGraphicElement.PEN_THIN:
				retVal =  BorderLineStyle.THIN;
				break;
				
			case JRGraphicElement.PEN_1_POINT:
			case JRGraphicElement.PEN_2_POINT:
				retVal = BorderLineStyle.MEDIUM;
				break;
			
			case JRGraphicElement.PEN_4_POINT:
				retVal = BorderLineStyle.THICK;
				break;
				
			case JRGraphicElement.PEN_DOTTED:
				retVal = BorderLineStyle.DOTTED;
				break;
				
			default:
				retVal = BorderLineStyle.NONE;
		}
		
		return retVal;
	}

	private final void setSheetSettings(WritableSheet sheet)
	{
		PageOrientation po;
		PaperSize ps;

		if (jasperPrint.getOrientation() == JRReport.ORIENTATION_PORTRAIT)
			po = PageOrientation.PORTRAIT;
		else
			po = PageOrientation.LANDSCAPE;

		if ((ps = getSuitablePaperSize(jasperPrint)) != null)
			sheet.setPageSetup(po, ps, 0, 0);
		else
			sheet.setPageSetup(po);

		SheetSettings sheets = sheet.getSettings();

		sheets.setTopMargin(0.0);
		sheets.setLeftMargin(0.0);
		sheets.setRightMargin(0.0);
		sheets.setBottomMargin(0.0);

		sheets.setHeaderMargin(0.0);
		sheets.setFooterMargin(0.0);
	}

	private final PaperSize getSuitablePaperSize(JasperPrint jasP)
	{

		if (jasP == null)
			return null;

		long width = 0;
		long height = 0;
		PaperSize ps = null;

		if ((jasP.getPageWidth() != 0) && (jasP.getPageHeight() != 0))
		{

			double dWidth = (jasP.getPageWidth() / 72.0);
			double dHeight = (jasP.getPageHeight() / 72.0);

			height = Math.round(dHeight * 25.4);
			width = Math.round(dWidth * 25.4);

			// Compare to ISO 216 A-Series (A3-A5). All other ISO 216 formats
			// not supported by JExcelApi yet.
			for (int i = 3; i < 6; i++)
			{
				int w = calculateWidthForDinAN(i);
				int h = calculateHeightForDinAN(i);

				if (((w == width) && (h == height)) || ((h == width) && (w == height)))
				{
					if (i == 3)
						ps = PaperSize.A3;
					else if (i == 4)
						ps = PaperSize.A4;
					else if (i == 5)
						ps = PaperSize.A5;
					break;
				}
			}

			// Compare to common North American Paper Sizes (ANSI X3.151-1987).
			if (ps == null)
			{
				// ANSI X3.151-1987 - "Letter" (216 � 279 mm)
				if (((width == 216) && (height == 279)) || ((width == 279) && (height == 216)))
				{
					ps = PaperSize.LETTER;
				}
				// ANSI X3.151-1987 - "Legal" (216 � 356 mm)
				if (((width == 216) && (height == 356)) || ((width == 356) && (height == 216)))
				{
					ps = PaperSize.LEGAL;
				}
				// ANSI X3.151-1987 - "Executive" (190 � 254 mm)
				// Not supperted by JExcelApi yet.

				// ANSI X3.151-1987 - "Ledger/Tabloid" (279 � 432 mm)
				// Not supperted by JExcelApi yet.
			}
		}
		return ps;
	}

	
	public static TextAlignHolder getTextAlignHolder(JRPrintText textElement)
	{
		short horizontalAlignment;
		short verticalAlignment;
		short rotation = textElement.getRotation();

		switch (textElement.getRotation())
		{
			case JRTextElement.ROTATION_LEFT :
			{
				switch (textElement.getHorizontalAlignment())
				{
					case JRAlignment.HORIZONTAL_ALIGN_LEFT :
					{
						verticalAlignment = JRAlignment.VERTICAL_ALIGN_BOTTOM;
						break;
					}
					case JRAlignment.HORIZONTAL_ALIGN_CENTER :
					{
						verticalAlignment = JRAlignment.VERTICAL_ALIGN_MIDDLE;
						break;
					}
					case JRAlignment.HORIZONTAL_ALIGN_RIGHT :
					{
						verticalAlignment = JRAlignment.VERTICAL_ALIGN_TOP;
						break;
					}
					case JRAlignment.HORIZONTAL_ALIGN_JUSTIFIED :
					{
						verticalAlignment = JRAlignment.VERTICAL_ALIGN_JUSTIFIED;
						break;
					}
					default :
					{
						verticalAlignment = JRAlignment.VERTICAL_ALIGN_BOTTOM;
					}
				}

				switch (textElement.getVerticalAlignment())
				{
					case JRAlignment.VERTICAL_ALIGN_TOP :
					{
						horizontalAlignment = JRAlignment.HORIZONTAL_ALIGN_LEFT;
						break;
					}
					case JRAlignment.VERTICAL_ALIGN_MIDDLE :
					{
						horizontalAlignment = JRAlignment.HORIZONTAL_ALIGN_CENTER;
						break;
					}
					case JRAlignment.VERTICAL_ALIGN_BOTTOM :
					{
						horizontalAlignment = JRAlignment.HORIZONTAL_ALIGN_RIGHT;
						break;
					}
					default :
					{
						horizontalAlignment = JRAlignment.HORIZONTAL_ALIGN_LEFT;
					}
				}

				break;
			}
			case JRTextElement.ROTATION_RIGHT :
			{
				switch (textElement.getHorizontalAlignment())
				{
					case JRAlignment.HORIZONTAL_ALIGN_LEFT :
					{
						verticalAlignment = JRAlignment.VERTICAL_ALIGN_TOP;
						break;
					}
					case JRAlignment.HORIZONTAL_ALIGN_CENTER :
					{
						verticalAlignment = JRAlignment.VERTICAL_ALIGN_MIDDLE;
						break;
					}
					case JRAlignment.HORIZONTAL_ALIGN_RIGHT :
					{
						verticalAlignment = JRAlignment.VERTICAL_ALIGN_BOTTOM;
						break;
					}
					case JRAlignment.HORIZONTAL_ALIGN_JUSTIFIED :
					{
						verticalAlignment = JRAlignment.VERTICAL_ALIGN_JUSTIFIED;
						break;
					}
					default :
					{
						verticalAlignment = JRAlignment.VERTICAL_ALIGN_TOP;
					}
				}

				switch (textElement.getVerticalAlignment())
				{
					case JRAlignment.VERTICAL_ALIGN_TOP :
					{
						horizontalAlignment = JRAlignment.HORIZONTAL_ALIGN_RIGHT;
						break;
					}
					case JRAlignment.VERTICAL_ALIGN_MIDDLE :
					{
						horizontalAlignment = JRAlignment.HORIZONTAL_ALIGN_CENTER;
						break;
					}
					case JRAlignment.VERTICAL_ALIGN_BOTTOM :
					{
						horizontalAlignment = JRAlignment.HORIZONTAL_ALIGN_LEFT;
						break;
					}
					default :
					{
						horizontalAlignment = JRAlignment.HORIZONTAL_ALIGN_RIGHT;
					}
				}

				break;
			}
			case JRTextElement.ROTATION_UPSIDE_DOWN:
			case JRTextElement.ROTATION_NONE :
			default :
			{
				horizontalAlignment = textElement.getHorizontalAlignment();
				verticalAlignment = textElement.getVerticalAlignment();
			}
		}

		return new TextAlignHolder(horizontalAlignment, verticalAlignment, rotation);
	}
	
	
	
	// Berechnungsvorschriften f�r die DIN Formate A, B, und C.
	// Die Angabe der Breite/H�he erfolgt in [mm].

	private final int calculateWidthForDinAN(int n)
	{
		return (int) (Math.pow(2.0, (-0.25 - (n / 2.0))) * 1000.0);
	}

	private final int calculateHeightForDinAN(int n)
	{
		return (int) (Math.pow(2.0, (0.25 - (n / 2.0))) * 1000.0);
	}

	private final int calculateWidthForDinBN(int n)
	{
		return (int) (Math.pow(2.0, -(n / 2.0)) * 1000.0);
	}

	private final int calculateHeightForDinBN(int n)
	{
		return (int) (Math.pow(2.0, (0.5 - (n / 2.0))) * 1000.0);
	}

	private final int calculateWidthForDinCN(int n)
	{
		return (int) (Math.pow(2.0, (-0.125 - (n / 2.0))) * 1000.0);
	}

	private final int calculateHeightForDinCN(int n)
	{
		return (int) (Math.pow(2.0, (0.375 - (n / 2.0))) * 1000.0);
	}

	protected void exportFrame(JRPrintFrame frame, JRExporterGridCell gridCell, int x, int y) throws JRException
	{
		addMergeRegion(gridCell, x, y);

		Colour forecolor = getNearestColour(frame.getForecolor());
		Colour backcolor = WHITE;
		Pattern mode = backgroundMode;

		if (frame.getMode() == JRElement.MODE_OPAQUE)
		{
			mode = Pattern.SOLID;
			backcolor = getNearestColour(frame.getBackcolor());
		}

		WritableFont cellFont = getLoadedFont(getDefaultFont(), forecolor.getValue());
		WritableCellFormat cellStyle = getLoadedCellStyle(mode, backcolor, 
				Alignment.LEFT.getValue(), VerticalAlignment.TOP.getValue(), Orientation.HORIZONTAL.getValue(),
				cellFont, gridCell);
		
		Blank cell = new Blank(x, y, cellStyle);
		try
		{
			sheet.addCell(cell);
		}
		catch (JXLException e)
		{
			throw new JRException("Can't add cell.", e);
		}
	}
	
	/**
	 * This method is intended to modify a given format pattern so to include 
	 * only the accepted proprietary format characters. The resulted pattern 
	 * will possibly truncate the original pattern
	 * @param pattern
	 * @return pattern converted to accepted proprietary formats
	 */
	private String getConvertedPattern(String pattern)
	{
		if (formatPatternsMap != null && formatPatternsMap.containsKey(pattern))
		{
			return (String) formatPatternsMap.get(pattern);
		}
		return pattern;
	}
	
	
	protected ExporterNature getNature()
	{
		return JExcelApiExporterNature.getInstance();
	}
	
		
}

