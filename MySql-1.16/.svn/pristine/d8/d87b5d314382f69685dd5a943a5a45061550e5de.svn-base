/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import com.cbs.exception.ApplicationException;
import com.cbs.web.utils.SiplTextExporter;
import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JExcelApiExporterParameter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRProperties;

public class ReportBean {

    public int TOTAL_NO_OF_PAGES_IN_REPORT = 0;

    /**
     * <p>
     * Construct a new application data bean instance.</p>
     */
    public ReportBean() {
    }
    /**
     * <p>
     * Prefix to the resource name for compiled reports.</p>
     */
    private static final String PREFIX = "/WEB-INF/reports/";
    /**
     * <p>
     * Suffix to the resource name for compiled reports.</p>
     *
     */
    private static final String SUFFIX = ".jasper";
    /**
     * <p>
     * Valid content types for reports that we can produce.</p>
     */
    private static final String[] VALID_TYPES = {"text/html", // Standard HTML representation
        "application/pdf", // Adobe Portable Document Format
        "text/plain", // Plain text format
        "excel", // Excel format
    };
    private static final String PDF_EXT = ".pdf";
    private static final String XLS_EXT = ".xls";

    public void jasperReport(String name, String type, JRBeanCollectionDataSource data, Map params, String reportName) {
        System.out.println("THE PRINT JASPER");
        boolean found = false;
        for (int i = 0; i < VALID_TYPES.length; i++) {
            if (VALID_TYPES[i].equals(type)) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new IllegalArgumentException("Invalid report type '" + type + "' requested");
        }

        JRProperties.setProperty("net.sf.jasperreports.xpath.executer.factory", "net.sf.jasperreports.engine.util.xml.JaxenXPathExecuterFactory");
        // Look up the compiled report design resource
        ExternalContext econtext = FacesContext.getCurrentInstance().getExternalContext();
        InputStream stream = econtext.getResourceAsStream(PREFIX + name + SUFFIX);

        if (stream == null) {
            throw new IllegalArgumentException("Unknown report name '" + name + "' requested");
        }

        JasperPrint jasperPrint = null;
        try {
            jasperPrint = JasperFillManager.fillReport(stream, params, data);
            if (name.equalsIgnoreCase("ACCOUNT_STATEMENT")) {
                TOTAL_NO_OF_PAGES_IN_REPORT = jasperPrint.getPages().size();
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new FacesException(e);
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
            }
        }

        // Configure the exporter to be used, along with the custom
        // parameters specific to the exporter type
        JRExporter exporter = null;
        HttpServletResponse response = (HttpServletResponse) econtext.getResponse();
        try {
            response.setContentType(type);
            if ("application/pdf".equals(type)) {
                exporter = new JRPdfExporter();
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                //exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, os);

                exporter.exportReport();
                getRequest().getSession().setAttribute("PDFRep", os);
                //fcontext.responseComplete();
            } else if ("text/html".equals(type)) {
                ByteArrayOutputStream sb = new ByteArrayOutputStream();
                ByteArrayOutputStream os = new ByteArrayOutputStream();

                /* For Viewing Report in text foramt on browser*/
                exporter = new SiplTextExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, sb);
                exporter.setParameter(JRTextExporterParameter.CHARACTER_ENCODING, "UTF-8");

                exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT, Float.parseFloat("12.00"));
                exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH, Float.parseFloat("5.15"));
                exporter.exportReport();

                /* For Printing Report in text foramt*/
                exporter = new SiplTextExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRTextExporterParameter.OUTPUT_STREAM, os);
                exporter.setParameter(JRTextExporterParameter.CHARACTER_ENCODING, "UTF-8");

                exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT, Float.parseFloat("12.00"));
                exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH, Float.parseFloat("5.15"));
                exporter.exportReport();

                getRequest().getSession().setAttribute("HTMLReport", sb);
                getRequest().getSession().setAttribute("ReportName", reportName);
                getRequest().getSession().setAttribute("TextReport", os);
                getRequest().getSession().setAttribute("Type", type);
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new FacesException(e);
        }
    }

    public void dynamicExcelJasper(String name, String type, JRBeanCollectionDataSource data, Map params, String reportName) throws ApplicationException {
        try {
            System.out.println("THE PRINT JASPER IN EXCEL");
            boolean found = false;
            for (int i = 0; i < VALID_TYPES.length; i++) {
                if (VALID_TYPES[i].equals(type)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new IllegalArgumentException("Invalid report type '" + type + "' requested");
            }

            JRProperties.setProperty("net.sf.jasperreports.xpath.executer.factory", "net.sf.jasperreports.engine.util.xml.JaxenXPathExecuterFactory");
            // Look up the compiled report design resource
            ExternalContext econtext = FacesContext.getCurrentInstance().getExternalContext();
            InputStream stream = econtext.getResourceAsStream(PREFIX + name + SUFFIX);


            if (stream == null) {
                throw new IllegalArgumentException("Unknown report name '" + name + "' requested");
            }

            JasperPrint jasperPrint = null;
            try {
                jasperPrint = JasperFillManager.fillReport(stream, params, data);
                if (name.equalsIgnoreCase("ACCOUNT_STATEMENT")) {
                    TOTAL_NO_OF_PAGES_IN_REPORT = jasperPrint.getPages().size();
                }
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                throw new FacesException(e);
            } finally {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            byte[] finalbyteArray = null;
            ServletOutputStream sout = null;
            HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            // JExcelApiExporter exporter = new JExcelApiExporter();
            JRXlsExporter exporter = new JRXlsExporter();

//            JasperPrint jp = JasperFillManager.fillReport(stream, params, data);
            exporter.setParameter(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            exporter.setParameter(JExcelApiExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
            exporter.setParameter(JExcelApiExporterParameter.JASPER_PRINT, jasperPrint);
            // exporter.setParameter(JExcelApiExporterParameter.OUTPUT_FILE_NAME, "d:/dynamicDemo.xls");
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
            exporter.setParameter(JExcelApiExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
            exporter.setParameter(JExcelApiExporterParameter.OFFSET_X, 0);
            exporter.setParameter(JExcelApiExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.FALSE);
            exporter.setParameter(JRXlsExporterParameter.SHEET_NAMES, new String[]{"Report"});
            exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
            exporter.setParameter(JRXlsExporterParameter.MAXIMUM_ROWS_PER_SHEET, 65000);
            exporter.exportReport();
            finalbyteArray = outputStream.toByteArray();
            response.setContentType("application/xsl");
            response.setContentLength(finalbyteArray.length);
            response.setHeader("Content-Disposition", "inline; filename=" + reportName.replace(" ", "") + ".xls");
            sout = response.getOutputStream();
            sout.write(finalbyteArray);
            sout.flush();
            facesContext.responseComplete();

        } catch (ColumnBuilderException columnBuilderException) {
            throw new ApplicationException(columnBuilderException.getMessage());
        } catch (JRException jRException) {
            throw new ApplicationException(jRException.getMessage());
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public static void close(Closeable resource) throws ApplicationException {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                throw new ApplicationException(e);
            }
        }
    }

    public String getDirPath(String fileExt) throws ApplicationException {
        try {
            String dirName = "";
            String osName = System.getProperty("os.name");
            if (osName.equalsIgnoreCase("Linux")) {
                if (fileExt.equalsIgnoreCase(PDF_EXT)) {
                    dirName = "/install/downloads/pdf/";
                } else if (fileExt.equalsIgnoreCase(XLS_EXT)) {
                    dirName = "/install/downloads/xls/";
                }
            } else if (osName.contains("Windows")) {
                if (fileExt.equalsIgnoreCase(PDF_EXT)) {
                    dirName = "d:/downloads/pdf/";
                } else if (fileExt.equalsIgnoreCase(XLS_EXT)) {
                    dirName = "d:/downloads/xls/";
                }
            }
            File dirPath = new File(dirName);
            if (!dirPath.exists()) {
                System.out.print("creating directory: " + dirName);
                boolean mkdirs = dirPath.mkdirs();
                System.out.print("--" + mkdirs);
                if (mkdirs) {
                    return dirName;
                } else {
                    throw new ApplicationException("Error in creating directory:" + dirName);
                }
            } else {
                return dirName;
            }
        } catch (ApplicationException e) {
            throw new ApplicationException(e.getMessage());
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public void openWindow(FacesContext facesContext, ExternalContext externalContext, String filePath) throws IOException, ApplicationException {
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
        File file = new File(filePath);
        BufferedInputStream input = null;
        BufferedOutputStream output = null;
        try {
            int size = (int) file.length();
            input = new BufferedInputStream(new FileInputStream(file), size);
            response.reset();
            response.setHeader("Content-Type", "application/pdf");
            response.setHeader("Content-Length", String.valueOf(size));
            response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
            output = new BufferedOutputStream(response.getOutputStream(), size);
            byte[] buffer = new byte[size];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();
        } catch (Exception e) {
            throw new ApplicationException(e);
        } finally {
            close(output);
            close(input);
            file.delete();
        }
        facesContext.responseComplete();
    }

    public void exportToPdfFile(FacesContext facesContext, ExternalContext externalContext, String filePath, String jasperName, JRBeanCollectionDataSource data, Map params) throws ApplicationException {
        try {
            InputStream stream = externalContext.getResourceAsStream(PREFIX + jasperName + SUFFIX);
            if (stream == null) {
                throw new IllegalArgumentException("Unknown report name '" + jasperName + "' requested");
            }
            JRProperties.setProperty("net.sf.jasperreports.xpath.executer.factory", "net.sf.jasperreports.engine.util.xml.JaxenXPathExecuterFactory");
            JasperPrint jasperPrint = null;
            try {
                jasperPrint = JasperFillManager.fillReport(stream, params, data);
                if (jasperName.equalsIgnoreCase("ACCOUNT_STATEMENT_PDF")) {
                    TOTAL_NO_OF_PAGES_IN_REPORT = jasperPrint.getPages().size();
                }
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                throw new FacesException(e);
            } finally {
                try {
                    stream.close();
                } catch (IOException e) {
                }
            }
            JasperExportManager.exportReportToPdfFile(jasperPrint, filePath);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
    }

    public boolean downloadPdf(String fileName, String jasperName, JRBeanCollectionDataSource data, Map params) throws ApplicationException {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            String dirPath = getDirPath(PDF_EXT);
            String filePath = dirPath + fileName + PDF_EXT;
            exportToPdfFile(facesContext, externalContext, filePath, jasperName, data, params);
            openWindow(facesContext, externalContext, filePath);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
    }

    public boolean openPdf(String fileName, String jasperName, JRBeanCollectionDataSource data, Map params) throws ApplicationException {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            String dirPath = getDirPath(PDF_EXT);
            String filePath = dirPath + fileName + PDF_EXT;
            exportToPdfFile(facesContext, externalContext, filePath, jasperName, data, params);
            //openWindow(facesContext, externalContext, filePath);
            getRequest().getSession().setAttribute("FilePath", filePath);
            getRequest().getSession().setAttribute("HTMLReport", null);
            getRequest().getSession().setAttribute("pages", TOTAL_NO_OF_PAGES_IN_REPORT);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void dynamicJasper(JRBeanCollectionDataSource obj, FastReportBuilder firstReport, String reportName) throws ApplicationException {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            byte[] finalbyteArray = null;
            ServletOutputStream sout = null;
            HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            // JExcelApiExporter exporter = new JExcelApiExporter();
            JRXlsExporter exporter = new JRXlsExporter();

            JasperPrint jp = buildReport(obj, firstReport);
            exporter.setParameter(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            exporter.setParameter(JExcelApiExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
            exporter.setParameter(JExcelApiExporterParameter.JASPER_PRINT, jp);
            // exporter.setParameter(JExcelApiExporterParameter.OUTPUT_FILE_NAME, "d:/dynamicDemo.xls");
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
            exporter.setParameter(JExcelApiExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
            exporter.setParameter(JExcelApiExporterParameter.OFFSET_X, 0);
            exporter.setParameter(JExcelApiExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.FALSE);
            exporter.setParameter(JRXlsExporterParameter.SHEET_NAMES, new String[]{"Report"});
            exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
            exporter.setParameter(JRXlsExporterParameter.MAXIMUM_ROWS_PER_SHEET, 65000);
            exporter.exportReport();
            finalbyteArray = outputStream.toByteArray();
            response.setContentType("application/xsl");
            response.setContentLength(finalbyteArray.length);
            response.setHeader("Content-Disposition", "inline; filename=" + reportName.replace(" ", "") + ".xls");
            sout = response.getOutputStream();
            sout.write(finalbyteArray);
            sout.flush();
            facesContext.responseComplete();

        } catch (ColumnBuilderException columnBuilderException) {
            throw new ApplicationException(columnBuilderException.getMessage());
        } catch (JRException jRException) {
            throw new ApplicationException(jRException.getMessage());
        } catch (ClassNotFoundException classNotFoundException) {
            throw new ApplicationException(classNotFoundException.getMessage());
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public void cibilExporterMultipleSheets(ArrayList<JasperPrint> list, String reportName, String[] sheetName) throws ApplicationException {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            byte[] finalbyteArray = null;
            ServletOutputStream sout = null;
            HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setParameter(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            exporter.setParameter(JExcelApiExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
            exporter.setParameter(JExcelApiExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
            exporter.setParameter(JExcelApiExporterParameter.OFFSET_X, 0);
            exporter.setParameter(JExcelApiExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.FALSE);
            exporter.setParameter(JRXlsExporterParameter.SHEET_NAMES, sheetName);
            exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
            exporter.setParameter(JRXlsExporterParameter.MAXIMUM_ROWS_PER_SHEET, 65000);
            exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, list);
            exporter.exportReport();
            finalbyteArray = outputStream.toByteArray();
            response.setContentType("application/xsl");
            response.setContentLength(finalbyteArray.length);
            response.setHeader("Content-Disposition", "inline; filename=" + reportName.replace(" ", "") + ".xls");
            sout = response.getOutputStream();
            sout.write(finalbyteArray);
            sout.flush();
            facesContext.responseComplete();
            System.out.println("Exported");
        } catch (ColumnBuilderException columnBuilderException) {
            throw new ApplicationException(columnBuilderException.getMessage());
        } catch (JRException jRException) {
            throw new ApplicationException(jRException.getMessage());
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public void dynamicJasperCSV(JRBeanCollectionDataSource obj, FastReportBuilder firstReport, String reportName) throws ApplicationException {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            byte[] finalbyteArray = null;
            ServletOutputStream sout = null;
            HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JRCsvExporter exporter = new JRCsvExporter();
            JasperPrint jp = buildReport(obj, firstReport);
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
            exporter.exportReport();
            finalbyteArray = outputStream.toByteArray();
            response.setContentType("application/csv");
            response.setContentLength(finalbyteArray.length);
            response.setHeader("Content-Disposition", "attachment; filename=" + reportName.replace(" ", "") + ".csv");
            sout = response.getOutputStream();
            sout.write(finalbyteArray);
            sout.flush();
            facesContext.responseComplete();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    public void dynamicJasperPdf(JRBeanCollectionDataSource obj, FastReportBuilder firstReport, String reportName) throws ApplicationException {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            byte[] finalbyteArray = null;
            ServletOutputStream sout = null;
            HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            JasperPrint jp = buildReport(obj, firstReport);
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
            exporter.exportReport();
            finalbyteArray = outputStream.toByteArray();
            response.setContentType("application/pdf");
            response.setContentLength(finalbyteArray.length);
            response.setHeader("Content-Disposition", "attachment; filename=" + reportName.replace(" ", "") + ".pdf");
            sout = response.getOutputStream();
            sout.write(finalbyteArray);
            sout.flush();
            facesContext.responseComplete();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    public void dynamicJasperHTML(JRBeanCollectionDataSource obj, FastReportBuilder firstReport) throws ApplicationException {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            byte[] finalbyteArray = null;
            ServletOutputStream sout = null;
            HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            JRPdfExporter exporter = new JRPdfExporter();
            JRHtmlExporter exporter = new JRHtmlExporter();
            JasperPrint jp = buildReport(obj, firstReport);
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
            exporter.exportReport();
            finalbyteArray = outputStream.toByteArray();
            response.setContentType("text/html");
            response.setContentLength(finalbyteArray.length);
            response.setHeader("Content-Disposition", "inline; filename=report.html");
            sout = response.getOutputStream();
            sout.write(finalbyteArray);
            sout.flush();
            facesContext.responseComplete();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    private static JasperPrint buildReport(JRBeanCollectionDataSource obj, FastReportBuilder firstReport) throws ColumnBuilderException, JRException, ClassNotFoundException {
        try {
            Style headerStyle = getHeaderStyle();
            Style detailStyle = new Style();
            detailStyle.setBorder(Border.THIN);
            detailStyle.setHorizontalAlign(HorizontalAlign.LEFT);
            firstReport.setIgnorePagination(true);
//            firstReport.setUseFullPageWidth(true);
            firstReport.setDefaultStyles(null, null, headerStyle, detailStyle);

            DynamicReport firstDynaRep = firstReport.build();
            JasperPrint jp = DynamicJasperHelper.generateJasperPrint(firstDynaRep, new ClassicLayoutManager(), obj);
            return jp;
        } catch (JRException jRException) {
            jRException.printStackTrace();
            throw jRException;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public JasperPrint buildReportNew(JRBeanCollectionDataSource obj, FastReportBuilder firstReport) throws ColumnBuilderException, JRException, ClassNotFoundException {
        try {
            Style headerStyle = getHeaderStyle();
            Style detailStyle = new Style();
            detailStyle.setBorder(Border.THIN);
            detailStyle.setHorizontalAlign(HorizontalAlign.LEFT);
            firstReport.setIgnorePagination(true);
            firstReport.setDefaultStyles(null, null, headerStyle, detailStyle);
            DynamicReport firstDynaRep = firstReport.build();
            JasperPrint jp = DynamicJasperHelper.generateJasperPrint(firstDynaRep, new ClassicLayoutManager(), obj);
            return jp;
        } catch (JRException jRException) {
            jRException.printStackTrace();
            throw jRException;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Style getHeaderStyle() {
        Style headerStyle = new Style();
        headerStyle.setFont(Font.ARIAL_MEDIUM_BOLD);
        headerStyle.setBorder(Border.THIN);
        headerStyle.setBorderBottom(Border.PEN_2_POINT);
        headerStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        headerStyle.setVerticalAlign(VerticalAlign.MIDDLE);
        headerStyle.setBackgroundColor(Color.LIGHT_GRAY);
        headerStyle.setTextColor(Color.BLUE);
        headerStyle.setTransparency(Transparency.OPAQUE);
        return headerStyle;
    }

    private static Style getDetailStyle() {
        Style detailStyle = new Style();
        detailStyle.setBorder(Border.THIN);
        detailStyle.setHorizontalAlign(HorizontalAlign.LEFT);
        return detailStyle;
    }

    public static AbstractColumn getColumn(String property, Class type, String title, int width)
            throws ColumnBuilderException {
        AbstractColumn columnState = ColumnBuilder.getInstance().setColumnProperty(property, type.getName()).setTitle(
                title).setWidth(Integer.valueOf(width)).setStyle(getDetailStyle()).setHeaderStyle(getHeaderStyle()).build();
        return columnState;
    }
    
//    public static void setPageSizeAndOrientation(JasperDesign design, ReportConfig config) {
//        // ReportConfig, PaperInfo.Size, PaperInfo.Orientation are custom classes/enums
//        JasperPrint jPrint = (JasperPrint) get(JRExporterParameter.JASPER_PRINT);
//	PaperInfo.Size size = JasperPrint.getPaperSize(config);
//	PaperInfo.Orientation orientation = ReportBuilder.getOrientation(config);
//       
//	if (size == PaperInfo.Size.LEGAL) {          
//		// Size of Legal is 8.5x14 in. At 72dpi, this translates to
//		// 8.5*72x14*72 = 612x1008.
//		if (orientation == PaperInfo.Orientation.LANDSCAPE) {
//			design.setPageWidth(1008);
//			design.setPageHeight(612);
//		} else {
//			design.setPageWidth(612);
//			design.setPageHeight(1008);
//		}
//	} else if (size == PaperInfo.Size.LETTER) {
//		// Size of Letter is 8.5x11 in. At 72dpi, this translates to
//		// 8.5*72x11*72 = 612x792.
//		if (orientation == PaperInfo.Orientation.LANDSCAPE) {
//			design.setPageWidth(792);
//			design.setPageHeight(612);
//		} else {
//			design.setPageWidth(612);
//			design.setPageHeight(792);
//		}
//	} else {
//		// Default is A4.
//		// Size of A4 is 8.27x11.69 in. At 72dpi, this translates to
//		// 8.27*72x11.69*72 = 595x842.
//		if (orientation == Papernfo.Orientation.LANDSCAPE) {
//			design.setPageWidth(842);
//			design.setPageHeight(595);
//		} else {
//			design.setPageWidth(595);
//			design.setPageHeight(842);
//		}
//	}
//}
//
//    private static class ReportConfig {
//
//        public ReportConfig() {
//        }
//    }
//
//    private static class ReportBuilder {
//
//        public ReportBuilder() {
//        }
//    }
//    
//    
//   public static CutsInfo calculateXCuts(ExporterNature nature, JasperPrint jasperPrint, int startPageIndex, int endPageIndex, int offsetX)
//{
//	CutsInfo xCuts = new CutsInfo();
//
//	List<JRPrintPage> pages = jasperPrint.getPages();
//	for (int pageIndex = startPageIndex; pageIndex <= endPageIndex; pageIndex++)
//	{
//		JRPrintPage page = pages.get(pageIndex);
//		addXCuts(nature, page.getElements(), offsetX, xCuts);
//	}
//
//	// add a cut at the page width if there are not parts and if no element goes beyond the page width
//	if (!jasperPrint.hasParts())
//	{
//		int width = jasperPrint.getPageWidth();
//		int lastCut = xCuts.getLastCutOffset();
//		if (lastCut < width)
//		{
//			xCuts.addCutOffset(width);
//		}
//	}
//
//	return xCuts;
//} 
//    
//   	protected static void addXCuts(ExporterNature nature, List<JRPrintElement> elementsList, int elementOffsetX, CutsInfo xCuts)
//	{
//		for (Iterator<JRPrintElement> it = elementsList.iterator(); it.hasNext();)
//		{
//			JRPrintElement element = it.next();
//
//			if (nature.isToExport(element))
//			{
//				xCuts.addCutOffset(element.getX() + elementOffsetX);
//				xCuts.addCutOffset(element.getX() + element.getWidth() + elementOffsetX);
//				
//				if (element instanceof JRPrintFrame)
//				{
//					JRPrintFrame frame = (JRPrintFrame) element;
//					addXCuts(
//						nature,
//						frame.getElements(),
//						element.getX() + elementOffsetX + frame.getLineBox().getLeftPadding().intValue(),
//						xCuts
//						);
//				}
//				
//				nature.setXProperties(xCuts.getPropertiesMap(), element);
//			}
//		}
//	}
//	
}
