/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.utils;

import com.cbs.dto.NpciInwardDto;
import com.cbs.dto.SSSRejectDto;
import com.cbs.exception.ApplicationException;
import com.cbs.pojo.neftrtgs.ExcelReaderPojo;
import com.cbs.pojo.neftrtgs.H2HOwReportPojo;
import com.cbs.pojo.neftrtgs.N02;
import com.cbs.pojo.neftrtgs.R41;
import com.cbs.pojo.neftrtgs.R42;
import com.cbs.pojo.neftrtgs.sbi.AckR09Adapter;
import com.cbs.pojo.neftrtgs.sbi.AckR90Adapter;
import com.cbs.pojo.neftrtgs.sbi.R41InwardAdaptor;
import java.io.*;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class ParseFileUtil {

    private static NumberFormat formater = new DecimalFormat("#.##");
    private static SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    private static SimpleDateFormat ymdhh = new SimpleDateFormat("dd-MMM-yy-hh:mm:ss");
    private static SimpleDateFormat ddMMMyyyy = new SimpleDateFormat("dd-MMM-yyyy");
    private static SimpleDateFormat dmmy = new SimpleDateFormat("dd-MM-yyyy");
    private static SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat ymdOne = new SimpleDateFormat("ddMMyyyy");
    private static SimpleDateFormat ymdh = new SimpleDateFormat("yyyy-MM-dd");

    public static List<ExcelReaderPojo> parseExcel(File xlsFile) throws IOException, ApplicationException {
        try {
            List<ExcelReaderPojo> pojoList = new ArrayList<ExcelReaderPojo>();

            POIFSFileSystem fs = new POIFSFileSystem(new BufferedInputStream(new FileInputStream(xlsFile)));
            HSSFWorkbook wb = new HSSFWorkbook(fs);

            HSSFSheet sheet = wb.getSheetAt(0);
            Iterator rows = sheet.rowIterator();
            while (rows.hasNext()) {
                List<String> tempList = new ArrayList<String>();
                ExcelReaderPojo pojo = new ExcelReaderPojo();
                HSSFRow row = (HSSFRow) rows.next();
                if (row.getRowNum() == 0) {
                    continue;
                }

                Iterator cells = row.cellIterator();
                while (cells.hasNext()) {
                    HSSFCell cell = (HSSFCell) cells.next();
                    if (HSSFCell.CELL_TYPE_NUMERIC == cell.getCellType()) {
                        tempList.add(String.valueOf(cell.getNumericCellValue()));
                    } else if (HSSFCell.CELL_TYPE_STRING == cell.getCellType()) {
                        tempList.add(cell.getStringCellValue());
                    } else if (HSSFCell.CELL_TYPE_BLANK == cell.getCellType()) {
                        tempList.add("");
                    }
                }
                if (!tempList.get(0).equals("")) {
                    pojo.setCopBankAccNo(tempList.get(0));
                    pojo.setIfsccode(tempList.get(1));
                    pojo.setUtr(tempList.get(2));
                    pojo.setBeneAccount(tempList.get(3));
                    pojo.setReceiverIfsc(tempList.get(4));

                    if (!tempList.get(5).equals("")) {
                        pojo.setAmount(new BigDecimal(formater.format(Double.parseDouble(tempList.get(5)))));
                    }
                    pojo.setTimestamp(tempList.get(6));
                    pojo.setSenderAcc(tempList.get(7));
                    pojo.setSenderIfsc(tempList.get(8));
                    String[] tmpArr = tempList.get(9).split("-");

                    if (tmpArr.length > 1) {
                        pojo.setSenderName(tmpArr[0]);
                        pojo.setSenderAddOne(tmpArr[1]);
                    } else {
                        pojo.setSenderName(tmpArr[0]);
                        pojo.setSenderAddOne(tmpArr[0]);
                    }

                    pojo.setSenderAddTwo(tempList.get(10) + tempList.get(11));
                    pojo.setTxnType(tempList.get(12));
                    pojo.setBeneName(tempList.get(13));
                    pojo.setBeneAdd(tempList.get(14));

                    pojo.setReceiverBankName("");
                    pojo.setReceiverBankCode("");
                    pojo.setReceiverBankAddress("");
                    pojo.setSponsorBankName("");
                    pojo.setSponsorBankCode("");

                    pojo.setSponsorIfsc("");
                    pojo.setSponsorRefNo("");
                    pojo.setSponsorAddress("");
                    pojo.setSenderBankName("");
                    pojo.setSenderBankCode("");

                    //New Addition due to the IDBI
                    pojo.setBeneAdd("");
                    pojo.setRemitInfo("");
                    pojo.setRemittanceOriginator("");
                    //End here

                    pojoList.add(pojo);
                }
            }
            return pojoList;
        } catch (IOException ex) {
            System.out.println("** Parsing error" + ex.getMessage());
            throw new IOException("Invalid File Format.");
        } catch (Exception ex) {
            throw new ApplicationException("Invalid File Format.");
        }
    }

    private static void replaceAndWriteFile(File file, List<String> replaceStrList) throws IOException {
        List<String> lines = new ArrayList<String>();
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line = in.readLine();
        while (line != null) {
            for (String str : replaceStrList) {
                if (line.contains(str)) {
                    line = line.replaceAll(str, "");
                }
            }
            lines.add(line);
            line = in.readLine();
        }
        in.close();
        PrintWriter out = new PrintWriter(file);
        for (String l : lines) {
            out.println(l);
        }
        out.close();
    }

    public static List<ExcelReaderPojo> parseXML(File xlsFile) throws ApplicationException {
        try {
            List<String> replaceStrList = new ArrayList<String>();
            replaceStrList.add("<p>");
            replaceStrList.add("&nbsp;");
            replaceAndWriteFile(xlsFile, replaceStrList);

            List<ExcelReaderPojo> pojoList = new ArrayList<ExcelReaderPojo>();
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(xlsFile);

            // normalize text representation
            doc.getDocumentElement().normalize();
            System.out.println("Root element of the doc is " + doc.getDocumentElement().getNodeName());


            NodeList listOfRow = doc.getElementsByTagName("tr");
            int totalRow = listOfRow.getLength();
            System.out.println("Total no of row: " + totalRow);

            for (int s = 1; s < listOfRow.getLength(); s++) {
                Node firstRowNode = listOfRow.item(s);
                if (firstRowNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element firstElement = (Element) firstRowNode;

                    NodeList colList = firstElement.getElementsByTagName("td");
                    int totalCol = colList.getLength();

                    //System.out.println("Total no of column: " + totalCol);

                    List<String> tempList = new ArrayList<String>();
                    ExcelReaderPojo pojo = new ExcelReaderPojo();

                    for (int i = 0; i < colList.getLength(); i++) {
                        Element colElement = (Element) colList.item(i);

                        NodeList textFNList = colElement.getChildNodes();
                        Node txtNode = (Node) textFNList.item(0);
                        if (txtNode.getNodeValue() != null) {
                            //System.out.println("Column Value : " + txtNode.getNodeValue().trim());
                            tempList.add(txtNode.getNodeValue().trim());
                        }
                    }
                    if (!tempList.get(0).equals("")) {
                        pojo.setCopBankAccNo(tempList.get(0));
                        pojo.setIfsccode(tempList.get(1));
                        pojo.setUtr(tempList.get(2));
                        pojo.setBeneAccount(tempList.get(3));
                        pojo.setReceiverIfsc(tempList.get(4));
                        if (!tempList.get(5).equals("")) {
                            pojo.setAmount(new BigDecimal(formater.format(Double.parseDouble(tempList.get(5)))));
                        }

                        pojo.setTimestamp(tempList.get(6));
                        pojo.setSenderAcc(tempList.get(7));
                        pojo.setSenderIfsc(tempList.get(8));
                        String[] tmpArr = tempList.get(9).split("-");
                        if (tmpArr.length > 1) {
                            pojo.setSenderName(tmpArr[0]);
                            pojo.setSenderAddOne(tmpArr[1]);
                        } else {
                            pojo.setSenderName(tmpArr[0]);
                            pojo.setSenderAddOne(tmpArr[0]);
                        }
                        pojo.setSenderAddTwo(tempList.get(10) + tempList.get(11));
                        pojo.setTxnType(tempList.get(12));
                        pojo.setBeneName(tempList.get(13));
                        pojo.setBeneAdd(tempList.get(14));

                        pojo.setReceiverBankName("");
                        pojo.setReceiverBankCode("");
                        pojo.setReceiverBankAddress("");
                        pojo.setSponsorBankName("");
                        pojo.setSponsorBankCode("");

                        pojo.setSponsorIfsc("");
                        pojo.setSponsorRefNo("");
                        pojo.setSponsorAddress("");
                        pojo.setSenderBankName("");
                        pojo.setSenderBankCode("");

                        //New Addition due to the IDBI
                        pojo.setBeneAdd("");
                        pojo.setRemitInfo("");
                        pojo.setRemittanceOriginator("");
                        //End here

                        pojoList.add(pojo);
                    }
                }
            }
            return pojoList;
        } catch (SAXParseException err) {
            System.out.println("** Parsing error" + ", line " + err.getLineNumber() + ", uri " + err.getSystemId());
            System.out.println(" " + err.getMessage());
            throw new ApplicationException("Invalid File Format.");
        } catch (SAXException e) {
            Exception x = e.getException();
            ((x == null) ? e : x).printStackTrace();
            throw new ApplicationException("Invalid File Format.");
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ApplicationException("Invalid File Format.");
        }
    }

    public static List<ExcelReaderPojo> parseIciciInwardTxtFile(File textFile) throws IOException, ApplicationException {
        FileInputStream fis = null;
        DataInputStream dis = null;
        BufferedReader br = null;
        try {
            List<ExcelReaderPojo> pojoList = new ArrayList<ExcelReaderPojo>();
            fis = new FileInputStream(textFile);
            dis = new DataInputStream(fis);
            br = new BufferedReader(new InputStreamReader(dis));
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] readArray = line.split("\\|");
                ExcelReaderPojo pojo = new ExcelReaderPojo();

                pojo.setCopBankAccNo("");
                pojo.setIfsccode("");
                pojo.setUtr(readArray[4]);
                pojo.setBeneAccount(readArray[1]);
                pojo.setReceiverIfsc("");

                pojo.setAmount(new BigDecimal(readArray[2]));
                pojo.setTimestamp(ymdhh.format(dmy.parse(readArray[3])));
                pojo.setSenderAcc(readArray[6]);
                pojo.setSenderIfsc("");
                pojo.setSenderName(readArray[7]);

                pojo.setSenderAddOne("");
                pojo.setSenderAddTwo("");
                pojo.setTxnType(readArray[8]);
                pojo.setBeneName(readArray[10]);
                pojo.setBeneAdd("");

                pojo.setReceiverBankName("");
                pojo.setReceiverBankCode(readArray[0]);
                pojo.setReceiverBankAddress("");
                pojo.setSponsorBankName(readArray[9]);
                pojo.setSponsorBankCode("");

                pojo.setSponsorIfsc("");
                pojo.setSponsorRefNo("");
                pojo.setSponsorAddress("");
                pojo.setSenderBankName(readArray[5]);
                pojo.setSenderBankCode("");

                //New Addition due to the IDBI
                pojo.setBeneAdd("");
                pojo.setRemitInfo("");
                pojo.setRemittanceOriginator("");
                //End here

                pojoList.add(pojo);
            }
            return pojoList;
        } catch (IOException ex) {
            System.out.println("** Parsing error" + ex.getMessage());
            throw new IOException("Invalid File Format." + ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException("Invalid File Format." + ex.getMessage());
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (dis != null) {
                dis.close();
            }
            if (br != null) {
                br.close();
            }
        }
    }

    public static List<ExcelReaderPojo> parseAxisBankTextFile(File textFile) throws IOException, ApplicationException {
        FileInputStream fis = null;
        DataInputStream dis = null;
        BufferedReader br = null;
        try {
            List<ExcelReaderPojo> pojoList = new ArrayList<ExcelReaderPojo>();
            fis = new FileInputStream(textFile);
            dis = new DataInputStream(fis);
            br = new BufferedReader(new InputStreamReader(dis));
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] readArray = line.split("\\|", 21);
                if (readArray[0].equalsIgnoreCase("NEFT") || readArray[0].equalsIgnoreCase("RTGS")) {
                    ExcelReaderPojo pojo = new ExcelReaderPojo();
                    pojo.setTxnType(readArray[0]);
                    pojo.setUtr(readArray[1]);
                    pojo.setSenderIfsc(readArray[2]);
                    pojo.setRelatedRefNo(readArray[3]);
                    pojo.setSenderAcc(readArray[4]);
                    pojo.setSenderName(readArray[5]);
                    pojo.setSenderAddOne(readArray[6]);
                    pojo.setReceiverIfsc(readArray[7]);
                    pojo.setBeneAccount(readArray[8]);
                    pojo.setBeneName(readArray[9]);
                    pojo.setRemitInfo(readArray[10]);
                    pojo.setAmount(new BigDecimal(readArray[11].toString()));
//                    pojo.setTranDate(readArray[12]);
                    pojo.setTimestamp(ymdhh.format(ymdh.parse(readArray[13])));
                    pojo.setNarration(readArray[15]);
                    pojo.setBeneAdd(readArray[16]);
                    pojo.setHeaderUtr(readArray[19]);
                    pojo.setBatchTime(readArray[20]);
                    pojo.setSponsorBankCode("");
                    pojo.setSponsorIfsc("");
                    pojo.setSponsorRefNo("");
                    pojo.setSponsorAddress("");
                    pojo.setCopBankAccNo("");
                    pojo.setIfsccode("");
                    pojo.setSenderAddTwo("");
                    pojo.setSponsorBankName("");
                    pojo.setRemittanceOriginator("");
                    pojo.setSenderBankCode("");
                    pojo.setSenderBankName("");
                    pojo.setReceiverBankName("");
                    pojo.setReason("");
                    pojo.setReasonCode("");
                    pojo.setReceiverBankAddress("");
                    pojo.setReceiverBankCode("");

                    pojoList.add(pojo);
                }
            }
            return pojoList;
        } catch (IOException ex) {
            System.out.println("** Parsing error" + ex.getMessage());
            throw new IOException("Invalid File Format." + ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException("Invalid File Format." + ex.getMessage());
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (dis != null) {
                dis.close();
            }
            if (br != null) {
                br.close();
            }
        }
    }

    public static List<ExcelReaderPojo> parseYesBankInwardTxtFile(File textFile) throws IOException, ApplicationException {
        FileInputStream fis = null;
        DataInputStream dis = null;
        BufferedReader br = null;
        try {
            List<ExcelReaderPojo> pojoList = new ArrayList<ExcelReaderPojo>();
            fis = new FileInputStream(textFile);
            dis = new DataInputStream(fis);
            br = new BufferedReader(new InputStreamReader(dis));
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] readArray = line.split("\\|");
                ExcelReaderPojo pojo = new ExcelReaderPojo();

                pojo.setCopBankAccNo(readArray[0]);
                pojo.setIfsccode(readArray[1]);
                pojo.setUtr(readArray[2]);
                pojo.setBeneAccount(readArray[3]);
                pojo.setReceiverIfsc(readArray[1]);

                pojo.setAmount(new BigDecimal(readArray[5]));
                pojo.setTimestamp(readArray[6]);
                pojo.setSenderAcc(readArray[7]);
                pojo.setSenderIfsc(readArray[8]);
                pojo.setSenderName("");

                pojo.setSenderAddOne(readArray[9]);
                pojo.setSenderAddTwo(readArray[10] + readArray[11]);
                pojo.setTxnType(readArray[12]);
                pojo.setBeneName(readArray[13]);
                pojo.setBeneAdd(readArray[14]);

                pojo.setReceiverBankName("");
                pojo.setReceiverBankCode("");
                pojo.setReceiverBankAddress("");
                pojo.setSponsorBankName("");
                pojo.setSponsorBankCode("");

                pojo.setSponsorIfsc("");
                pojo.setSponsorRefNo("");
                pojo.setSponsorAddress("");
                pojo.setSenderBankName("");
                pojo.setSenderBankCode("");

                //New Addition due to the IDBI
                pojo.setBeneAdd("");
                pojo.setRemitInfo("");
                pojo.setRemittanceOriginator("");
                //End here

                pojoList.add(pojo);
            }
            return pojoList;
        } catch (IOException ex) {
            System.out.println("** Parsing error" + ex.getMessage());
            throw new IOException("Invalid File Format." + ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException("Invalid File Format." + ex.getMessage());
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (dis != null) {
                dis.close();
            }
            if (br != null) {
                br.close();
            }
        }
    }

    public static List<H2HOwReportPojo> parseIciciReportTextFile(File textFile) throws IOException, ApplicationException {
        FileInputStream fis = null;
        DataInputStream dis = null;
        BufferedReader br = null;
        try {
            List<H2HOwReportPojo> pojoList = new ArrayList<H2HOwReportPojo>();
            fis = new FileInputStream(textFile);
            dis = new DataInputStream(fis);
            br = new BufferedReader(new InputStreamReader(dis));
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] readArray = line.split("\\|");
                H2HOwReportPojo pojo = new H2HOwReportPojo();

                pojo.setPaymentType(readArray[0]);
                pojo.setuCustRefNo(readArray[1]);
                pojo.setBeneName(readArray[2]);
                pojo.setBeneCode(readArray[3]);
                pojo.setAmount(new BigDecimal(readArray[4]));
                pojo.setPaymentDt(readArray[5]);
                pojo.setCrAccountNo(readArray[6]);
                pojo.setIfscCode(readArray[7]);
                pojo.setDrAccountNo(readArray[8]);
                pojo.setCmsRefNo(readArray[9]);
                pojo.setUtrNo(readArray[10]);
                pojo.setStatus(readArray[11]);

                pojoList.add(pojo);
            }
            return pojoList;
        } catch (IOException ex) {
            System.out.println("** Parsing error" + ex.getMessage());
            throw new IOException("Invalid File Format." + ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException("Invalid File Format." + ex.getMessage());
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (dis != null) {
                dis.close();
            }
            if (br != null) {
                br.close();
            }
        }
    }

    public static List<H2HOwReportPojo> parseYesBankReportTextFile(File textFile) throws IOException, ApplicationException {
        FileInputStream fis = null;
        DataInputStream dis = null;
        BufferedReader br = null;
        try {
            List<H2HOwReportPojo> pojoList = new ArrayList<H2HOwReportPojo>();
            fis = new FileInputStream(textFile);
            dis = new DataInputStream(fis);
            br = new BufferedReader(new InputStreamReader(dis));
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] readArray = line.split(",");
                H2HOwReportPojo pojo = new H2HOwReportPojo();

                pojo.setPaymentType(readArray[1]);
                pojo.setIfscCode(readArray[4]);
                pojo.setCrAccountNo(readArray[5]);
                pojo.setBeneName(readArray[6]);
                pojo.setuCustRefNo(readArray[7]);
                pojo.setPaymentDt(readArray[8]);
                pojo.setAmount(new BigDecimal(readArray[9]));
                pojo.setStatus(readArray[11]);
                pojo.setRejectReason(readArray[12]);
                pojo.setUtrNo(readArray[13]);

                pojoList.add(pojo);
            }
            return pojoList;
        } catch (IOException ex) {
            System.out.println("** Parsing error" + ex.getMessage());
            throw new IOException("Invalid File Format." + ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException("Invalid File Format." + ex.getMessage());
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (dis != null) {
                dis.close();
            }
            if (br != null) {
                br.close();
            }
        }
    }

    public static List<ExcelReaderPojo> parseCCBLCSV(File csvFile) throws IOException, ApplicationException {
        FileInputStream fis = null;
        DataInputStream dis = null;
        BufferedReader br = null;
        try {
            List<ExcelReaderPojo> pojoList = new ArrayList<ExcelReaderPojo>();
            fis = new FileInputStream(csvFile);
            dis = new DataInputStream(fis);
            br = new BufferedReader(new InputStreamReader(dis));
            String readLine = "";
            while ((readLine = br.readLine()) != null) {
                String[] readArray = readLine.split(",");
                if (!readArray[0].equalsIgnoreCase("Posting Date")) {
                    ExcelReaderPojo pojo = new ExcelReaderPojo();

                    pojo.setCopBankAccNo(readArray[1]);
                    pojo.setIfsccode("");
                    pojo.setUtr(readArray[4]);
                    pojo.setBeneAccount(readArray[2]);
                    pojo.setReceiverIfsc("");

                    pojo.setAmount(new BigDecimal(readArray[5]));
                    pojo.setTimestamp(ymdhh.format(ddMMMyyyy.parse(readArray[0])));
                    pojo.setSenderAcc(readArray[6]);
                    pojo.setSenderIfsc(readArray[9]);
                    pojo.setSenderName(readArray[7]);

                    pojo.setSenderAddOne("");
                    pojo.setSenderAddTwo("");
                    pojo.setTxnType("");
                    pojo.setBeneName(readArray[10]);
                    pojo.setBeneAdd(readArray[3]);

                    pojo.setReceiverBankName("");
                    pojo.setReceiverBankCode("");
                    pojo.setReceiverBankAddress("");
                    pojo.setSponsorBankName("");
                    pojo.setSponsorBankCode("");

                    pojo.setSponsorIfsc("");
                    pojo.setSponsorRefNo("");
                    pojo.setSponsorAddress("");
                    pojo.setSenderBankName(readArray[8]);
                    pojo.setSenderBankCode("");

                    //New Addition due to the IDBI
                    pojo.setBeneAdd("");
                    pojo.setRemitInfo("");
                    pojo.setRemittanceOriginator("");
                    //End here

                    pojoList.add(pojo);
                }
            }
            return pojoList;
        } catch (IOException ex) {
            System.out.println("** Parsing error" + ex.getMessage());
            throw new IOException("Invalid File Format." + ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException("Invalid File Format." + ex.getMessage());
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (dis != null) {
                dis.close();
            }
            if (br != null) {
                br.close();
            }
        }
    }

    public static List<ExcelReaderPojo> parseSNSBCSV(File csvFile) throws IOException, ApplicationException {
        FileInputStream fis = null;
        DataInputStream dis = null;
        BufferedReader br = null;
        try {
            List<ExcelReaderPojo> pojoList = new ArrayList<ExcelReaderPojo>();
            fis = new FileInputStream(csvFile);
            dis = new DataInputStream(fis);
            br = new BufferedReader(new InputStreamReader(dis));
            String readLine = "";
            while ((readLine = br.readLine()) != null) {
                String[] readArray = readLine.split(",");
                if (!readArray[0].equalsIgnoreCase("Posting Date")) {
                    ExcelReaderPojo pojo = new ExcelReaderPojo();

                    pojo.setCopBankAccNo(readArray[4]);
                    pojo.setIfsccode("");
                    pojo.setUtr(readArray[7]);
                    pojo.setBeneAccount(readArray[11]);
                    pojo.setReceiverIfsc(readArray[3]);

                    pojo.setAmount(new BigDecimal(readArray[10]));
                    pojo.setTimestamp(ymdhh.format(ddMMMyyyy.parse(readArray[1])));
                    pojo.setSenderAcc(readArray[8]);
                    pojo.setSenderIfsc(readArray[5]);
                    pojo.setSenderName(readArray[9].replaceAll("[\\W_]", " "));

                    if (readArray.length == 15) {
                        pojo.setSenderAddOne(readArray[13].replaceAll("[\\W_]", " "));
                        pojo.setSenderAddTwo(readArray[14].replaceAll("[\\W_]", " "));
                    } else {
                        pojo.setSenderAddOne("");
                        pojo.setSenderAddTwo("");
                    }

                    pojo.setTxnType(readArray[2]);
                    pojo.setBeneName(readArray[12].replaceAll("[\\W_]", " "));
                    pojo.setBeneAdd("");

                    pojo.setReceiverBankName("");
                    pojo.setReceiverBankCode("");
                    pojo.setReceiverBankAddress("");
                    pojo.setSponsorBankName("");
                    pojo.setSponsorBankCode("");

                    pojo.setSponsorIfsc("");
                    pojo.setSponsorRefNo("");
                    pojo.setSponsorAddress("");
                    pojo.setSenderBankName(readArray[6].replaceAll("[\\W_]", " "));
                    pojo.setSenderBankCode("");

                    //New Addition due to the IDBI
                    pojo.setBeneAdd("");
                    pojo.setRemitInfo("");
                    pojo.setRemittanceOriginator("");
                    //End here

                    pojoList.add(pojo);
                }
            }
            return pojoList;
        } catch (IOException ex) {
            System.out.println("** Parsing error" + ex.getMessage());
            throw new IOException("Invalid File Format." + ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException("Invalid File Format." + ex.getMessage());
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (dis != null) {
                dis.close();
            }
            if (br != null) {
                br.close();
            }
        }
    }

    public static List<H2HOwReportPojo> parseIciciNonUploadAndUploadLevelMis(File xlsFile) throws IOException, ApplicationException {
        try {
            List<H2HOwReportPojo> pojoList = new ArrayList<H2HOwReportPojo>();

            POIFSFileSystem fs = new POIFSFileSystem(new BufferedInputStream(new FileInputStream(xlsFile)));
            HSSFWorkbook wb = new HSSFWorkbook(fs);

            HSSFSheet sheet = wb.getSheetAt(0);
            Iterator rows = sheet.rowIterator();
            while (rows.hasNext()) {
                List<String> tempList = new ArrayList<String>();
                H2HOwReportPojo pojo = new H2HOwReportPojo();
                HSSFRow row = (HSSFRow) rows.next();
                if (row.getRowNum() == 0) {
                    continue;
                }

                Iterator cells = row.cellIterator();
                while (cells.hasNext()) {
                    HSSFCell cell = (HSSFCell) cells.next();
                    if (HSSFCell.CELL_TYPE_NUMERIC == cell.getCellType()) {
                        tempList.add(String.valueOf(cell.getNumericCellValue()));
                    } else if (HSSFCell.CELL_TYPE_STRING == cell.getCellType()) {
                        tempList.add(cell.getStringCellValue());
                    } else if (HSSFCell.CELL_TYPE_BLANK == cell.getCellType()) {
                        tempList.add("");
                    }
                }
                if (!tempList.get(0).equals("")) {
                    pojo.setBeneName(tempList.get(0));            // Setting of fileName
                    pojo.setRejectReason(tempList.get(3));
                    if (xlsFile.getName().trim().toUpperCase().contains("Name Or Part Of Iw-CONS File Name")) { //Upload-Level-MIS
                        pojo.setStatus(tempList.get(2));

                        String dataString = tempList.get(1).trim();
                        String[] arr = dataString.split("\\|");
                        pojo.setuCustRefNo(arr[1]);  //Unique Customer Ref No, I think that datastring is as like going into outward file.
                    }

                    pojoList.add(pojo);
                }
            }
            return pojoList;
        } catch (IOException ex) {
            System.out.println("** Parsing error" + ex.getMessage());
            throw new IOException("Invalid File Format.");
        } catch (Exception ex) {
            throw new ApplicationException("Invalid File Format.");
        }
    }

    public static String addTrailingSpaces(String str, int length) {
        String actualStr = str.trim();
        int remLength = length - (actualStr.length());
        if (remLength < 0) {
            actualStr = actualStr.substring(0, length);
        } else {
            for (int i = 0; i < remLength; i++) {
                actualStr = " " + actualStr;
            }
        }
        return actualStr;
    }

    public static String addSuffixSpaces(String str, int length) {
        String actualStr = str.trim();
        int remLength = length - (actualStr.length());
        if (remLength < 0) {
            actualStr = actualStr.substring(0, length);
        } else {
            for (int i = 0; i < remLength; i++) {
                actualStr = actualStr + " ";
            }
        }
        return actualStr;
    }

    public static String addTrailingZeros(String str, int length) {
        String actualStr = str.trim();
        int remLength = length - (actualStr.length());
        for (int i = 0; i < remLength; i++) {
            actualStr = "0" + actualStr;
        }
        return actualStr;
    }
    // Added by Manish kumar

    public static String addTrailingZerosWithoutDecimal(String str, int length) {
        if (str.substring(str.indexOf(".") + 1).length() == 1) {
            str += "0";
        }
        String charToDel = ".";
        String pat = "[" + Pattern.quote(charToDel) + "]";
        String actualStr = str.replaceAll(pat, "");
        actualStr = actualStr.trim();
        int remLength = length - (actualStr.length());
        for (int i = 0; i < remLength; i++) {
            actualStr = "0" + actualStr;
        }
        return actualStr;
    }

    public static String addSuffixZeros(String str, int length) {
        String actualStr = str.trim();
        int remLength = length - (actualStr.length());
        for (int i = 0; i < remLength; i++) {
            actualStr = actualStr + "0";
        }
        return actualStr;
    }

    public static String getParticularLengthData(String str, int length) {
        String actualStr = str.trim();
        int remLength = length - (actualStr.length());
        if (remLength < 0) {
            return actualStr.substring(0, length);
        } else {
            return actualStr;
        }
    }

    public static ExcelReaderPojo adaptObjectFromN02Msg(N02 n02) throws ApplicationException {
        ExcelReaderPojo pojo = new ExcelReaderPojo();
        try {
            pojo.setCopBankAccNo("");
            pojo.setIfsccode(n02.getFBeneficiary_branchs_IFSC());
            pojo.setUtr(n02.getFTransaction_Reference_Number());
            pojo.setRelatedRefNo(n02.getFRelated_Reference_Number());
            pojo.setBeneAccount(n02.getFBeneficiary_customer_ac_No());
            pojo.setReceiverIfsc(n02.getFBeneficiary_branchs_IFSC());
            pojo.setAmount(new BigDecimal(n02.getFAmount()));
            pojo.setTimestamp(ymdhh.format(dmmy.parse(n02.getFValue_Date())));
            pojo.setSenderAcc(n02.getFSending_customer_ac_No());
            pojo.setSenderIfsc(n02.getFSending_branchs_IFSC());
            pojo.setSenderName(n02.getFSending_customer_ac_name());
            pojo.setSenderAddOne("");
            pojo.setSenderAddTwo("");
            pojo.setTxnType("N02");
            pojo.setBeneName(n02.getFBeneficiary_customer_ac_name());
            pojo.setBeneAdd(n02.getFBeneficiary_customer_address());
            pojo.setReceiverBankName("");
            pojo.setReceiverBankCode("");
            pojo.setReceiverBankAddress("");
            pojo.setSponsorBankName("");
            pojo.setSponsorBankCode("");
            pojo.setSponsorIfsc("");
            pojo.setSponsorRefNo("");
            pojo.setSponsorAddress("");
            pojo.setSenderBankName("");
            pojo.setSenderBankCode("");
            pojo.setReasonCode(n02.getFReason_Code());
            pojo.setReason(n02.getFRejection_Reason());
            pojo.setRemitInfo(n02.getFRemittance_information());
            pojo.setRemittanceOriginator(n02.getFOriginator_of_Remittance());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return pojo;
    }

    public static ExcelReaderPojo adaptObjectFromR41Msg(R41 r41) throws ApplicationException {
        ExcelReaderPojo pojo = new ExcelReaderPojo();
        try {
            pojo.setCopBankAccNo("");
            pojo.setIfsccode(r41.getFRCID_1());
            pojo.setUtr(r41.getF2020_1());
            pojo.setBeneAccount(r41.getF5561_1());
            pojo.setReceiverIfsc(r41.getFRCID_1());
            pojo.setAmount(new BigDecimal(r41.getF4488_2()));
            pojo.setTimestamp(ymdhh.format(dmmy.parse(r41.getF4488_1())));
            pojo.setSenderAcc(r41.getF5500_1());
            pojo.setSenderIfsc(r41.getFSRID_1());
            pojo.setSenderName(r41.getF5500_2());
            pojo.setSenderAddOne("");
            pojo.setSenderAddTwo("");
            pojo.setTxnType("R41");
            pojo.setBeneName(r41.getF5561_2());
            pojo.setBeneAdd("");
            pojo.setReceiverBankName("");
            pojo.setReceiverBankCode("");
            pojo.setReceiverBankAddress("");
            pojo.setSponsorBankName("");
            pojo.setSponsorBankCode("");
            pojo.setSponsorIfsc("");
            pojo.setSponsorRefNo("");
            pojo.setSponsorAddress("");
            pojo.setSenderBankName(r41.getF5500_3());
            pojo.setSenderBankCode("");
            pojo.setReasonCode("");
            pojo.setReason("");
            pojo.setRemitInfo(r41.getF7495_1());
            pojo.setRemittanceOriginator("");
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return pojo;
    }

    public static Integer getNDigitRandomNumber(int n) {
        String pMultiplier = addSuffixZeros("1", n);
        String sMultiplier = addSuffixZeros("9", n);
        Random rnd = new Random();
        return Integer.parseInt(pMultiplier) + rnd.nextInt(Integer.parseInt(sMultiplier));
    }

    public static String getFixedLengthStrFromLast(String str, int fixedLength) {
        String actualStr = str.trim();
        if (actualStr.length() > fixedLength) {
            actualStr = actualStr.substring(actualStr.length() - fixedLength);
            return actualStr;
        } else {
            return actualStr;
        }
    }

    //Npci AADHAAR Processing.
    public static List<NpciInwardDto> parseNpciInwardTxtFile(File textFile) throws IOException, ApplicationException {
        FileInputStream fis = null;
        DataInputStream dis = null;
        BufferedReader br = null;
        try {
            List<NpciInwardDto> pojoList = new ArrayList<>();
            fis = new FileInputStream(textFile);
            dis = new DataInputStream(fis);
            br = new BufferedReader(new InputStreamReader(dis));
            String line = null, settlementDt = "";
            while ((line = br.readLine()) != null) {
                String apbsTranCode = line.substring(0, 2);
                if (apbsTranCode.equals("33")) {    //33 For Header.
                    settlementDt = line.substring(125, 133);
                }
                if (apbsTranCode.equals("77") || apbsTranCode.equals("78")
                        || apbsTranCode.equals("79") || apbsTranCode.equals("80")
                        || apbsTranCode.equals("82")) {
                    //77- DBT Transactions, 78- DBTL Transactions, 79- DBT High Value Transactions, 80- Other Non DBT Credits, 82 - PFMS
                    NpciInwardDto pojo = new NpciInwardDto();

                    pojo.setApbsTranCode(apbsTranCode);
                    pojo.setDestBankIIN(line.substring(2, 11));
                    pojo.setDestAcType(line.substring(11, 13));
                    pojo.setLedgerNo(line.substring(13, 16));
                    pojo.setBeneAadhaarNo(line.substring(16, 31));
                    pojo.setBeneName(line.substring(31, 71));
                    pojo.setSponsorBankIIN(line.substring(71, 80));
                    pojo.setUserNumber(line.substring(80, 87));
                    pojo.setUserName(line.substring(87, 107));
                    pojo.setUserCreditRef(line.substring(107, 120));
                    pojo.setAmount(line.substring(120, 133));
                    pojo.setReservedOne(line.substring(133, 143));
                    pojo.setReservedTwo(line.substring(143, 153));
                    pojo.setReservedThree(line.substring(153, 155));
                    pojo.setDestBankAcno(line.substring(155, 175));
                    pojo.setRetReasonCode(line.substring(175));
                    pojo.setSettlementDt(ymd.format(ymdOne.parse(settlementDt)));

                    pojoList.add(pojo);
                }
            }
            return pojoList;
        } catch (Exception ex) {
            throw new ApplicationException("Invalid File Format." + ex.getMessage());
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (dis != null) {
                dis.close();
            }
            if (br != null) {
                br.close();
            }
        }
    }

    //Npci NON-AADHAAR Processing.
    public static List<NpciInwardDto> parseNpciNonAadhaarInwardTxtFile(File textFile, String fileSeqNo)
            throws IOException, ApplicationException {
        FileInputStream fis = null;
        DataInputStream dis = null;
        BufferedReader br = null;
        //As it is value will be inserted in table from text file. if there is no value then spcaes will go.
        try {
            List<NpciInwardDto> pojoList = new ArrayList<NpciInwardDto>();
            fis = new FileInputStream(textFile);
            dis = new DataInputStream(fis);
            br = new BufferedReader(new InputStreamReader(dis));
            String line = null, originatorCode = "", responderCode = "", settlementDt = "";
            String fileRefNo = "", filler = "";
            while ((line = br.readLine()) != null) {
                String apbsTranCode = line.substring(0, 2);
                if (apbsTranCode.equals("30")) {    //30 For Header.
                    originatorCode = line.substring(2, 13);
                    responderCode = line.substring(13, 24);
                    settlementDt = line.substring(24, 32);
                    fileRefNo = line.substring(32, 42);
                    filler = line.substring(48, 500);
                }
                if (apbsTranCode.equals("70")) {    //70 For Record.
                    NpciInwardDto pojo = new NpciInwardDto();

                    pojo.setOriginatorCode(originatorCode);
                    pojo.setResponderCode(responderCode);
                    pojo.setSettlementDt(ymd.format(ymdOne.parse(settlementDt)));   //File_Coming_Dt
                    pojo.setFileSeqNo(fileSeqNo);
                    pojo.setApbsTranCode(line.substring(0, 2)); //Record_Identifier
                    pojo.setUserCreditRef(line.substring(2, 17));    //RRN
                    pojo.setDestBankIIN(line.substring(17, 28));  //Dest_Bank_Ifsc
                    pojo.setDestBankAcno(line.substring(28, 63)); //Dest_Bank_Acno
                    pojo.setBeneName(line.substring(63, 163)); //Ben_Name_Orgn
                    pojo.setLpgId(line.substring(163, 180));
                    pojo.setAcValidFlag(line.substring(180, 182));
                    pojo.setBenNameMatchFlag(line.substring(182, 184));
                    pojo.setBenNameResponder(line.substring(184, 384));
                    pojo.setBeneAadhaarNo(line.substring(384, 399));   //Aadhaar_No
                    pojo.setReservedOne(line.substring(399, 414)); //Mobile
                    pojo.setReservedTwo(line.substring(414, 484));     //Email
                    pojo.setFileRefNo(fileRefNo);
                    pojo.setFiller(filler);

                    pojoList.add(pojo);
                }
            }
            return pojoList;
        } catch (Exception ex) {
            throw new ApplicationException("Invalid File Format." + ex.getMessage());
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (dis != null) {
                dis.close();
            }
            if (br != null) {
                br.close();
            }
        }
    }

    //Uploading of ACH Inward.
    public static List<NpciInwardDto> parseAchInwardTxtFile(File textFile, String fileSeqNo) throws IOException, ApplicationException {
        FileInputStream fis = null;
        DataInputStream dis = null;
        BufferedReader br = null;
        try {
            List<NpciInwardDto> pojoList = new ArrayList<NpciInwardDto>();
            fis = new FileInputStream(textFile);
            dis = new DataInputStream(fis);
            br = new BufferedReader(new InputStreamReader(dis));
            String line = null, settlementDt = "", settlementCycle = "", headerDestIIN = "";
            while ((line = br.readLine()) != null) {
                String apbsTranCode = line.substring(0, 2);
                if (apbsTranCode.equals("12")) {    //12 For Header.
                    settlementDt = line.substring(125, 133);    //Header Settlement Date.
                    headerDestIIN = line.substring(160, 171);   //Header Dest IIN.
                    settlementCycle = line.substring(171, 173); //Header Settlement Cycle.
                }
                if (apbsTranCode.equals("23")) {    //23 For Record.
                    NpciInwardDto pojo = new NpciInwardDto();

                    pojo.setApbsTranCode(apbsTranCode);                         //ACH Transaction Code(Detail)
                    pojo.setControll2nd(line.substring(2, 11));
                    pojo.setDestAcType(line.substring(11, 13));                 //Destination Account Type
                    pojo.setLedgerNo(line.substring(13, 16));                   //Ledger Folio Number
                    pojo.setControll5th(line.substring(16, 31));
                    pojo.setBeneName(line.substring(31, 71));                   //Beneficiary Account Holders Name
                    pojo.setControll7th(line.substring(71, 80));
                    pojo.setControll8th(line.substring(80, 87));
                    pojo.setUserName(line.substring(87, 107));                  //User Name / Narration
                    pojo.setControll10th(line.substring(107, 120));
                    pojo.setAmount(line.substring(120, 133));                   //Amount
                    pojo.setItemSeqNo(line.substring(133, 143));
                    pojo.setCheckSum(line.substring(143, 153));
                    pojo.setAchFiller(line.substring(153, 160));
                    pojo.setDestBankIIN(line.substring(160, 171));              //Destination Bank IFSC / MICR/IIN
                    pojo.setDestBankAcno(line.substring(171, 206));             //Beneficiarys Bank Account number
                    pojo.setSponsorBankIIN(line.substring(206, 217));           //Sponsor Bank IFSC/MICR/IIN
                    pojo.setUserNumber(line.substring(217, 235));               //User Number
                    pojo.setUserCreditRef(line.substring(235, 265));            //Transaction Reference
                    pojo.setProductType(line.substring(265, 268));
                    pojo.setBeneAadhaarNo(line.substring(268, 283));            //Beneficiary Aadhaar Number
                    pojo.setUmrn(line.substring(283, 303));
                    pojo.setReservedFlag(line.substring(303, 304));
                    pojo.setReservedReason(line.substring(304, 306));
                    pojo.setSettlementDt(ymd.format(ymdOne.parse(settlementDt)));   //Settlement Date
                    pojo.setSettlementCycle(settlementCycle);
                    pojo.setHeaderDestIIN(headerDestIIN);
                    pojo.setFileRefNo(fileSeqNo);       //Just for credit narration.


                    pojoList.add(pojo);
                }
            }
            return pojoList;
        } catch (Exception ex) {
            throw new ApplicationException("Invalid File Format." + ex.getMessage());
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (dis != null) {
                dis.close();
            }
            if (br != null) {
                br.close();
            }
        }
    }

    public static List<H2HOwReportPojo> parseHdfcReportTextFile(File textFile) throws IOException, ApplicationException {
        FileInputStream fis = null;
        DataInputStream dis = null;
        BufferedReader br = null;
        try {
            List<H2HOwReportPojo> pojoList = new ArrayList<H2HOwReportPojo>();
            fis = new FileInputStream(textFile);
            dis = new DataInputStream(fis);
            br = new BufferedReader(new InputStreamReader(dis));
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] readArray = line.split(",", 16);
                H2HOwReportPojo pojo = new H2HOwReportPojo();

                pojo.setPaymentType(readArray[0].trim());
                pojo.setBeneCode(readArray[1].trim());
                pojo.setBeneName(readArray[2].trim());
                pojo.setAmount(new BigDecimal(readArray[3].trim()));
                pojo.setuCustRefNo(readArray[6].trim());
                pojo.setCmsRefNo(readArray[10].trim());
                pojo.setStatus(readArray[11].trim());
                pojo.setRejectReason(readArray[12].trim());
                pojo.setUtrNo(readArray[15].trim());

                pojoList.add(pojo);
            }
            return pojoList;
        } catch (IOException ex) {
            System.out.println("** Parsing error" + ex.getMessage());
            throw new IOException("Invalid File Format." + ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException("Invalid File Format." + ex.getMessage());
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (dis != null) {
                dis.close();
            }
            if (br != null) {
                br.close();
            }
        }
    }

    public static List<NpciInwardDto> parseNpciMapperResponse(File file) throws ApplicationException {
        List<NpciInwardDto> returnList = new ArrayList<>();
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(file);
            //normalize text representation
            doc.getDocumentElement().normalize();
            System.out.println("Root element of the doc is " + doc.getDocumentElement().getNodeName());
            //Total no of records
            NodeList listOfRecord = doc.getElementsByTagName("Record");
            int totalRecord = listOfRecord.getLength();
            System.out.println("Total no of record: " + totalRecord);
            for (int s = 0; s < listOfRecord.getLength(); s++) {
                Node recordNode = listOfRecord.item(s);
                if (recordNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element recordElement = (Element) recordNode;
                    NodeList recordChildList = recordElement.getChildNodes();
//                    System.out.println("Length Of Child List-->" + recordChildList.getLength());
//                    for (int z = 0; z < recordChildList.getLength(); z++) {
//                        Node n = (Node) recordChildList.item(z);
//                        System.out.println("Node Index-->" + z + ":::Value Is-->" + n.getTextContent());
//                    }

//                    Node aadharNoNode = (Node) recordChildList.item(0);
//                    Node uidResultNode = (Node) recordChildList.item(6);
//                    Node acceptedNode = (Node) recordChildList.item(7);

                    Node aadharNoNode = (Node) recordChildList.item(1);
                    Node reasoncodeNode = (Node) recordChildList.item(11);
                    Node uidResultNode = (Node) recordChildList.item(13);
                    Node acceptedNode = (Node) recordChildList.item(15);
//
                    if (aadharNoNode.getTextContent() != null || uidResultNode.getTextContent() != null || acceptedNode.getTextContent() != null) {
                        NpciInwardDto dto = new NpciInwardDto();
                        dto.setBeneAadhaarNo(aadharNoNode.getTextContent());   //AADHAAR_NO In Response File.
                        dto.setRetReasonCode(uidResultNode.getTextContent());  //UID_RESULT In Response File.
                        dto.setTranRef(acceptedNode.getTextContent()); //ACCEPTED In Response File.
                        dto.setReasonCode(reasoncodeNode.getTextContent()); //UID_REASON_CODE In Response File.
                        returnList.add(dto);
                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return returnList;
    }

    public static List<NpciInwardDto> parseNpciAvResponse(File textFile, String headerId,
            String detailId) throws IOException, ApplicationException {
        FileInputStream fis = null;
        DataInputStream dis = null;
        BufferedReader br = null;
        List<NpciInwardDto> returnList = new ArrayList<NpciInwardDto>();
        try {
            fis = new FileInputStream(textFile);
            dis = new DataInputStream(fis);
            br = new BufferedReader(new InputStreamReader(dis));
            String line = null;
            while ((line = br.readLine()) != null) {
                String lineCode = line.substring(0, 2);
                if (lineCode.equalsIgnoreCase(detailId)) {
                    NpciInwardDto dto = new NpciInwardDto();
                    dto.setLpgId(line.substring(163, 180));
                    dto.setAcValidFlag(line.substring(180, 182));
                    returnList.add(dto);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException("Invalid File Format." + ex.getMessage());
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (dis != null) {
                dis.close();
            }
            if (br != null) {
                br.close();
            }
        }
        return returnList;
    }

    //HSSFWorkbook - For XLS, XSSFWorkbook - For XLSX
    public static List<ExcelReaderPojo> parsePghadExcel(File xlsFile) throws Exception {
        List<ExcelReaderPojo> pojoList = new ArrayList<ExcelReaderPojo>();
        try {
            POIFSFileSystem fs = new POIFSFileSystem(new BufferedInputStream(new FileInputStream(xlsFile)));
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            Iterator rows = sheet.rowIterator();
            int c = 0;
            while (rows.hasNext()) {
                List<String> tempList = new ArrayList<String>();
                ExcelReaderPojo pojo = new ExcelReaderPojo();
                HSSFRow row = (HSSFRow) rows.next();
                if (row.getRowNum() == 0) {
                    c = row.getLastCellNum();
                    continue;
                }

                for (int i = 0; i < c; i++) {
                    HSSFCell cell = (HSSFCell) row.getCell(i, Row.CREATE_NULL_AS_BLANK);
                    if (HSSFCell.CELL_TYPE_NUMERIC == cell.getCellType()) {
                        tempList.add(String.valueOf(cell.getNumericCellValue()));
                    } else if (HSSFCell.CELL_TYPE_STRING == cell.getCellType()) {
                        tempList.add(cell.getStringCellValue());
                    } else if (HSSFCell.CELL_TYPE_BLANK == cell.getCellType()) {
                        tempList.add("");
                    }
                }

                if (!tempList.isEmpty()
                        && !tempList.get(4).equals("")
                        && !tempList.get(6).equals("")
                        && !tempList.get(9).equals("")
                        && !tempList.get(20).equals("")
                        && !tempList.get(22).equals("")) {
                    pojo.setAmount(new BigDecimal(formater.format(Double.parseDouble(tempList.get(4)))));
                    pojo.setTimestamp(ymdhh.format(ymdh.parse(tempList.get(6).substring(0, 10).trim())));
                    pojo.setBeneName(tempList.get(9));
                    pojo.setBeneAccount(tempList.get(20));
                    pojo.setUtr(tempList.get(22));
                    pojo.setSenderName(tempList.get(25).trim().length() > 200 ? tempList.get(25).trim().substring(0, 200)
                            : tempList.get(25).trim());
                    //Other details Setting in pojo
                    pojo.setCopBankAccNo("");
                    pojo.setIfsccode("");
                    pojo.setReceiverIfsc("");
                    pojo.setSenderAcc("");
                    pojo.setSenderIfsc("");
                    pojo.setTxnType("");
                    pojo.setSenderAddOne("");
                    pojo.setSenderAddTwo("");
                    pojo.setBeneAdd("");
                    pojo.setReceiverBankName("");
                    pojo.setReceiverBankCode("");
                    pojo.setReceiverBankAddress("");
                    pojo.setSponsorBankName("");
                    pojo.setSponsorBankCode("");
                    pojo.setSponsorIfsc("");
                    pojo.setSponsorRefNo("");
                    pojo.setSponsorAddress("");
                    pojo.setSenderBankName("");
                    pojo.setSenderBankCode("");
                    pojo.setRemitInfo("");
                    pojo.setRemittanceOriginator("");

                    pojoList.add(pojo);
                }
            }
            return pojoList;
        } catch (Exception ex) {
            throw new Exception("Problem In File Reading->" + ex.getMessage());
        }
    }

    public static List<SSSRejectDto> parsePmSBYRejection(File file) throws Exception {
        List<SSSRejectDto> pojoList = new ArrayList<SSSRejectDto>();
        FileInputStream fis = null;
        DataInputStream dis = null;
        BufferedReader br = null;
        try {
            fis = new FileInputStream(file);
            dis = new DataInputStream(fis);
            br = new BufferedReader(new InputStreamReader(dis));
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] readArray = line.split("\\~~");
                SSSRejectDto pojo = new SSSRejectDto();

                pojo.setAcno(readArray[0]);
                pojo.setCode(readArray[1]);
                pojo.setBrName(readArray[2]);
                pojo.setBrIfsc(readArray[3]);
                pojo.setCustId(readArray[4]);
                pojo.setEnrollDt(readArray[5]);
                pojo.setName(readArray[6]);
                pojo.setMailAdd(readArray[7]);
                pojo.setCity(readArray[8]);
                pojo.setState(readArray[9]);
                pojo.setPin(readArray[10]);
                pojo.setLocCategory(readArray[11]);
                pojo.setDob(readArray[12]);
                pojo.setGender(readArray[13]);
                pojo.setDisability(readArray[14]);
                pojo.setNomName(readArray[15]);
                pojo.setNomRel(readArray[16]);
                pojo.setGuardianName(readArray[17]);
                pojo.setAadharNo(readArray[18]);
                pojo.setPan(readArray[19]);
                pojo.setEmail(readArray[20]);
                pojo.setMobile(readArray[21]);
                pojo.setAgencyCode(readArray[22]);
                pojo.setErrorDesc(readArray[23]);

                pojoList.add(pojo);
            }
            return pojoList;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (dis != null) {
                dis.close();
            }
            if (br != null) {
                br.close();
            }
        }
    }

    public static List<NpciInwardDto> parseNpciCecsCreditInwardTxtFile(File textFile, String fileSeqNo) throws IOException, ApplicationException {
        FileInputStream fis = null;
        DataInputStream dis = null;
        BufferedReader br = null;
        try {
            List<NpciInwardDto> pojoList = new ArrayList<NpciInwardDto>();
            fis = new FileInputStream(textFile);
            dis = new DataInputStream(fis);
            br = new BufferedReader(new InputStreamReader(dis));
            String line = null, settlementDt = "", headerDestIIN = "";

            while ((line = br.readLine()) != null) {
                NpciInwardDto pojo = new NpciInwardDto();
                String apbsTranCode = line.substring(0, 2);
                if (apbsTranCode.equals("11")) {    //11 For Header.
                    headerDestIIN = line.substring(93, 103);    //Header Dest IIN.
                    settlementDt = line.substring(125, 133);   //Header Settlement Date.
                }

                if (apbsTranCode.equals("22")) {
                    pojo.setApbsTranCode(apbsTranCode);
                    pojo.setDestBankIIN(line.substring(2, 11));
                    pojo.setDestAcType(line.substring(11, 13));
                    pojo.setLedgerNo(line.substring(13, 16));
                    pojo.setDestBankAcno(line.substring(16, 31));
                    pojo.setBeneName(line.substring(31, 71));
                    pojo.setSponsorBankIIN(line.substring(71, 80));
                    pojo.setUserNumber(line.substring(80, 87));
                    pojo.setUserName(line.substring(87, 107));
                    pojo.setUserCreditRef(line.substring(107, 120));
                    pojo.setAmount(line.substring(120, 133));
                    pojo.setReservedOne(line.substring(133, 143));
                    pojo.setReservedTwo(line.substring(143, 153));
                    pojo.setReservedThree(line.substring(153, 160));
                    pojo.setHeaderDestIIN(headerDestIIN);
                    pojo.setSettlementDt(ymd.format(ymdOne.parse(settlementDt)));   //Settlement Date 
                    pojo.setItemSeqNo(fileSeqNo);

                    //Addition due to the new 306 format insertion
                    pojo.setCheckSum("");
                    pojo.setProductType("");
                    pojo.setSettlementCycle("");
                    pojo.setControll2nd("");
                    pojo.setControll5th("");
                    pojo.setControll7th("");
                    pojo.setControll8th("");
                    pojo.setControll10th("");
                    pojo.setAchFiller("");
                    pojo.setUmrn("");
                    pojo.setReservedFlag("");
                    pojo.setReservedReason("");

                    pojoList.add(pojo);
                }
            }
            return pojoList;
        } catch (Exception ex) {
            throw new ApplicationException("Invalid File Format." + ex.getMessage());
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (dis != null) {
                dis.close();
            }
            if (br != null) {
                br.close();
            }
        }
    }

    public static List<NpciInwardDto> parseOacTxtFile(File textFile, String fileSeqNo)
            throws IOException, ApplicationException {
        FileInputStream fis = null;
        DataInputStream dis = null;
        BufferedReader br = null;
        try {
            List<NpciInwardDto> pojoList = new ArrayList<NpciInwardDto>();
            fis = new FileInputStream(textFile);
            dis = new DataInputStream(fis);
            br = new BufferedReader(new InputStreamReader(dis));
            String line = null, headerId = "", orgCode = "", resCode = "", fileComingDt = "", fileRefNo = "";
            while ((line = br.readLine()) != null) {
                NpciInwardDto pojo = new NpciInwardDto();
                String apbsTranCode = line.substring(0, 2);
                if (apbsTranCode.equals("31")) {//31 For Header Identifier.
                    headerId = apbsTranCode;
                    orgCode = line.substring(2, 6);
                    resCode = line.substring(6, 10);
                    fileComingDt = line.substring(10, 18);
                    fileRefNo = line.substring(18, 28);
                }
                //Record Extraction
                if (apbsTranCode.equals("71")) {//71 For Record Identifier.
                    pojo.setHeaderId(headerId);
                    pojo.setOriginatorCode(orgCode);    //ORIGINATOR_CODE
                    pojo.setResponderCode(resCode);     //RESPONDER_CODE
                    pojo.setSettlementDt(ymd.format(ymdOne.parse(fileComingDt))); //File Upload Date In Header-FILE_COMING_DT
                    pojo.setFileRefNo(fileRefNo);
                    pojo.setFileSeqNo(fileSeqNo);
                    pojo.setRecordId(apbsTranCode);
                    pojo.setUserCreditRef(line.substring(2, 17)); //RRN
                    pojo.setDestBankIIN(line.substring(17, 28)); //MICR
                    pojo.setDestAcType(line.substring(28, 30)); //AC_TYPE
                    pojo.setOldAcno(line.substring(30, 50));
                    pojo.setOldAcName(line.substring(50, 150));
                    pojo.setUserNumber(line.substring(150, 157));
                    pojo.setUserName(line.substring(157, 177));
                    pojo.setTranRef(line.substring(177, 190));
                    pojo.setAcValidFlag(line.substring(190, 191)); //AC_VAL_FLAG
                    pojo.setCbsAcno(line.substring(191, 226));
                    pojo.setCbsAcName(line.substring(226, 326));
                    pojoList.add(pojo);
                }
            }
            return pojoList;
        } catch (Exception ex) {
            throw new ApplicationException("Invalid File Format." + ex.getMessage());
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (dis != null) {
                dis.close();
            }
            if (br != null) {
                br.close();
            }
        }
    }

    public static List<NpciInwardDto> parseEcsDrTxtFile(File textFile, String fileSeqNo, String fileNameDt)
            throws IOException, ApplicationException {
        FileInputStream fis = null;
        DataInputStream dis = null;
        BufferedReader br = null;
        try {
            List<NpciInwardDto> pojoList = new ArrayList<NpciInwardDto>();
            fis = new FileInputStream(textFile);
            dis = new DataInputStream(fis);
            br = new BufferedReader(new InputStreamReader(dis));
            String line = null, headerId = "", fileComingDt = "";
            while ((line = br.readLine()) != null) {
                NpciInwardDto pojo = new NpciInwardDto();
                String apbsTranCode = line.substring(0, 2);
                if (apbsTranCode.equals("55")) {//55 For ECS transaction code for NECS in Header Record.
                    headerId = apbsTranCode;
                    fileComingDt = line.substring(125, 133);
                }
                //Record Extraction
                if (apbsTranCode.equals("66")) {//66 For ECS Transaction Code in Credit Records
                    pojo.setHeaderId(headerId);
                    pojo.setOriginatorCode("");
                    pojo.setResponderCode("");
                    pojo.setSettlementDt(ymd.format(ymdOne.parse(fileComingDt))); //Settlement Date In Header-FILE_COMING_DT
                    pojo.setFileRefNo("");
                    pojo.setFileSeqNo(fileSeqNo);
                    pojo.setRecordId(apbsTranCode);
                    pojo.setUserCreditRef("");
                    pojo.setDestBankIIN(line.substring(2, 11)); //Destination sort code
                    pojo.setDestAcType(line.substring(11, 13)); //Destination Account Type
                    pojo.setOldAcno(line.substring(16, 31)); //Destination Account number
                    pojo.setOldAcName(line.substring(31, 71)); //Destination Account Holder name
                    pojo.setUserNumber(line.substring(80, 87)); //User Number
                    pojo.setUserName(line.substring(87, 107)); //User Name
                    pojo.setTranRef(line.substring(107, 120)); //Transaction Reference
                    pojo.setAmount(line.substring(120, 133)); //Amount
                    pojo.setAchFiller(line.substring(133, 143)); //Reserved (ACH Item Seq No.)
                    pojo.setFileNameDt(fileNameDt);
                    pojoList.add(pojo);
                }
            }
            return pojoList;
        } catch (Exception ex) {
            throw new ApplicationException("Invalid File Format." + ex.getMessage());
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (dis != null) {
                dis.close();
            }
            if (br != null) {
                br.close();
            }
        }
    }

    public static boolean isAlphabet(String str) {
        String regex = "[a-zA-Z]+";
        return str.matches(regex);
    }

    public static boolean isNumeric(String str) {
        String regex = "[0-9]+";
        return str.matches(regex);
    }

    /**
     * Returns true if s contains any character other than letters, numbers, or
     * spaces. Returns false otherwise. It is special character checking if
     * exists.
     */
    public static boolean containsSpecialCharacter(String s) {
        if (s == null) {
            return true;
        }
        Matcher m = Pattern.compile("[a-zA-Z0-9 ]*").matcher(s);
        return (!m.matches()) ? true : false;
    }

    /**
     * Returns true if s contains either character letters, numbers, or spaces.
     * Returns false otherwise.
     */
    public static boolean onlyNumericAlphabetAndSpaces(String s) {
        if (s == null) {
            return false;
        }
        Matcher m = Pattern.compile("[a-zA-Z0-9 ]*").matcher(s);
        return (!m.matches()) ? false : true;
    }

    //Parsing of new ECS Inward of 306 character.
    public static List<NpciInwardDto> parseEcsNewTxtFile(File textFile, String fileSeqNo) throws IOException, ApplicationException {
        FileInputStream fis = null;
        DataInputStream dis = null;
        BufferedReader br = null;
        try {
            List<NpciInwardDto> pojoList = new ArrayList<NpciInwardDto>();
            fis = new FileInputStream(textFile);
            dis = new DataInputStream(fis);
            br = new BufferedReader(new InputStreamReader(dis));
            String line = null, settlementDt = "", settlementCycle = "", headerDestIIN = "";
            while ((line = br.readLine()) != null) {
                String apbsTranCode = line.substring(0, 2);
                if (apbsTranCode.equals("12")) {    //12 For Header.
                    settlementDt = line.substring(125, 133);    //Header Settlement Date.
                    headerDestIIN = line.substring(160, 171);   //Header Dest IIN.
                    settlementCycle = line.substring(171, 173); //Header Settlement Cycle.
                }
                if (apbsTranCode.equals("23")) {    //23 For Record.
                    NpciInwardDto pojo = new NpciInwardDto();

                    pojo.setApbsTranCode(apbsTranCode);                         //ACH Transaction Code(Detail)
                    pojo.setControll2nd(line.substring(2, 11));
                    pojo.setDestAcType(line.substring(11, 13));                 //Destination Account Type
                    pojo.setLedgerNo(line.substring(13, 16));                   //Ledger Folio Number
                    pojo.setControll5th(line.substring(16, 31));
                    pojo.setBeneName(line.substring(31, 71));                   //Beneficiary Account Holders Name
                    pojo.setControll7th(line.substring(71, 80));
                    pojo.setControll8th(line.substring(80, 87));
                    pojo.setUserName(line.substring(87, 107));                  //User Name / Narration
                    pojo.setControll10th(line.substring(107, 120));
                    pojo.setAmount(line.substring(120, 133));                   //Amount
                    pojo.setReservedOne(line.substring(133, 143));              //Reserved (ACH Item Seq No.)
                    pojo.setCheckSum(line.substring(143, 153));
                    pojo.setAchFiller(line.substring(153, 160));
                    pojo.setDestBankIIN(line.substring(160, 171));              //Destination Bank IFSC / MICR/IIN
                    pojo.setDestBankAcno(line.substring(171, 206));             //Beneficiarys Bank Account number
                    pojo.setSponsorBankIIN(line.substring(206, 217));           //Sponsor Bank IFSC/MICR/IIN
                    pojo.setUserNumber(line.substring(217, 235));               //User Number
                    pojo.setUserCreditRef(line.substring(235, 265));            //Transaction Reference
                    pojo.setProductType(line.substring(265, 268));
                    pojo.setBeneAadhaarNo(line.substring(268, 283));            //Beneficiary Aadhaar Number
                    pojo.setUmrn(line.substring(283, 303));
                    pojo.setReservedFlag(line.substring(303, 304));
                    pojo.setReservedReason(line.substring(304, 306));
                    pojo.setSettlementDt(ymd.format(ymdOne.parse(settlementDt)));   //Settlement Date
                    pojo.setSettlementCycle(settlementCycle);
                    pojo.setHeaderDestIIN(headerDestIIN);
                    pojo.setItemSeqNo(fileSeqNo);                               //file name seq No
                    pojo.setReservedTwo("");
                    pojo.setReservedThree("");

                    pojoList.add(pojo);
                }
            }
            return pojoList;
        } catch (Exception ex) {
            throw new ApplicationException("Invalid File Format." + ex.getMessage());
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (dis != null) {
                dis.close();
            }
            if (br != null) {
                br.close();
            }
        }
    }

    //Parsing of ACH DR 306 Character.
    public static List<NpciInwardDto> parseAchDrNewTxtFile(File textFile, String fileSeqNo) throws
            IOException, ApplicationException {
        FileInputStream fis = null;
        DataInputStream dis = null;
        BufferedReader br = null;
        try {
            List<NpciInwardDto> pojoList = new ArrayList<NpciInwardDto>();
            fis = new FileInputStream(textFile);
            dis = new DataInputStream(fis);
            br = new BufferedReader(new InputStreamReader(dis));
            String line = null, settlementDt = "", settlementCycle = "", headerDestIIN = "", headerId = "";
            while ((line = br.readLine()) != null) {
                String apbsTranCode = line.substring(0, 2);
                if (apbsTranCode.equals("56")) {    //56 For Header.
                    headerId = apbsTranCode;
                    settlementDt = line.substring(125, 133);    //Header Settlement Date.
                    headerDestIIN = line.substring(160, 171);   //Header Dest IIN.
                    settlementCycle = line.substring(171, 173); //Header Settlement Cycle.
                }
                if (apbsTranCode.equals("67")) {    //67 For Record.
                    NpciInwardDto pojo = new NpciInwardDto();
                    pojo.setHeaderId(headerId);
                    pojo.setRecordId(apbsTranCode);                         //ACH Transaction Code(Detail)
                    pojo.setControll2nd(line.substring(2, 11));
                    pojo.setDestAcType(line.substring(11, 13));                 //Destination Account Type
                    pojo.setLedgerNo(line.substring(13, 16));                   //Ledger Folio Number
                    pojo.setControll5th(line.substring(16, 31));
                    pojo.setBeneName(line.substring(31, 71));                   //Beneficiary Account Holders Name
                    pojo.setControll7th(line.substring(71, 80));
                    pojo.setControll8th(line.substring(80, 87));
                    pojo.setUserName(line.substring(87, 107));                  //User Name/Narration
                    pojo.setControll10th(line.substring(107, 120));
                    pojo.setAmount(line.substring(120, 133));                   //Amount
                    pojo.setReservedOne(line.substring(133, 143));              //Reserved(ACH Item Seq No.)
                    pojo.setCheckSum(line.substring(143, 153));
                    pojo.setAchFiller(line.substring(153, 160));
                    pojo.setDestBankIIN(line.substring(160, 171));              //Destination Bank IFSC / MICR/IIN
                    pojo.setDestBankAcno(line.substring(171, 206));             //Beneficiarys Bank Account number
                    pojo.setSponsorBankIIN(line.substring(206, 217));           //Sponsor Bank IFSC/MICR/IIN
                    pojo.setUserNumber(line.substring(217, 235));               //User Number
                    pojo.setUserCreditRef(line.substring(235, 265));            //Transaction Reference
                    pojo.setProductType(line.substring(265, 268));
                    pojo.setBeneAadhaarNo(line.substring(268, 283));            //Beneficiary Aadhaar Number
                    pojo.setUmrn(line.substring(283, 303));
                    pojo.setReservedFlag(line.substring(303, 304));
                    pojo.setReservedReason(line.substring(304, 306));
                    pojo.setSettlementDt(ymd.format(ymdOne.parse(settlementDt)));   //Settlement Date
                    pojo.setSettlementCycle(settlementCycle);
                    pojo.setHeaderDestIIN(headerDestIIN);
                    pojo.setItemSeqNo(fileSeqNo);                                   //file name seq No
                    pojo.setReservedTwo("");
                    pojo.setReservedThree("");

                    pojoList.add(pojo);
                }
            }
            return pojoList;
        } catch (Exception ex) {
            throw new ApplicationException("Invalid File Format." + ex.getMessage());
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (dis != null) {
                dis.close();
            }
            if (br != null) {
                br.close();
            }
        }
    }

    public static List<H2HOwReportPojo> parseAxisReportTextFile(File textFile) throws IOException, ApplicationException {
        FileInputStream fis = null;
        DataInputStream dis = null;
        BufferedReader br = null;
        try {
            List<H2HOwReportPojo> pojoList = new ArrayList<H2HOwReportPojo>();
            fis = new FileInputStream(textFile);
            dis = new DataInputStream(fis);
            br = new BufferedReader(new InputStreamReader(dis));
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] readArray = line.split("\\^", 20);
                H2HOwReportPojo pojo = new H2HOwReportPojo();

                pojo.setuCustRefNo(readArray[3]);
                pojo.setUtrNo(readArray[13]);
                pojo.setStatus(readArray[14]);
                pojo.setRejectReason(readArray[17]);
                pojo.setReversalReason(readArray[18]);

                pojoList.add(pojo);
            }
            return pojoList;
        } catch (IOException ex) {
            System.out.println("** Parsing error" + ex.getMessage());
            throw new IOException("Invalid File Format." + ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException("Invalid File Format." + ex.getMessage());
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (dis != null) {
                dis.close();
            }
            if (br != null) {
                br.close();
            }
        }
    }

    public static List<ExcelReaderPojo> parseSbiIncomingN02Message(String receivedMessage, Map<String, String> fieldNameMap) throws Exception {
        List<ExcelReaderPojo> pojoList = new ArrayList<ExcelReaderPojo>();
        try {
            String block4 = receivedMessage.substring(receivedMessage.indexOf("{4:") + 3, receivedMessage.trim().length() - 2);
            String[] strArr = block4.split(":2020");
            String block4Header = "2020" + strArr[1];

            String[] headerArr = block4Header.split(":");
            String headerUtr = headerArr[1];
            String batchTime = headerArr[3];

            ExcelReaderPojo pojoObj;

            for (int i = 2; i < strArr.length; i++) {
                pojoObj = new ExcelReaderPojo();
                String msg = "2020" + strArr[i];
                String[] msgStrArr = msg.split(":");
                for (int j = 0; j < msgStrArr.length; j = j + 2) {
                    Method myObjMethod = Class.forName("com.cbs.pojo.neftrtgs.ExcelReaderPojo").getMethod(fieldNameMap.get(msgStrArr[j]), String.class);
                    myObjMethod.invoke(pojoObj, msgStrArr[j + 1]);
                }
                pojoList.add(pojoObj);
            }
            for (ExcelReaderPojo pojo : pojoList) {
                String txnAmount = pojo.getTxnAmount().replace(',', '.');
                pojo.setAmount(new BigDecimal(Double.parseDouble(txnAmount)));
                pojo.setHeaderUtr(headerUtr);
                pojo.setBatchTime(batchTime);
                pojo.setTimestamp(ymdhh.format(ymd.parse(pojo.getTimestamp())));

                pojo.setCopBankAccNo("");
                pojo.setIfsccode("");
                pojo.setSenderAddOne("");
                pojo.setSenderAddTwo("");
                pojo.setTxnType("N02");
                pojo.setReceiverBankName("");
                pojo.setReceiverBankCode("");
                pojo.setReceiverBankAddress("");
                pojo.setSponsorBankName("");
                pojo.setSponsorBankCode("");
                pojo.setSponsorIfsc("");
                pojo.setSponsorRefNo("");
                pojo.setSponsorAddress("");
                pojo.setSenderBankName("");
                pojo.setSenderBankCode("");
                pojo.setReason("");
                pojo.setReturnTranRefNo("");
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return pojoList;
    }

    public static Map<String, String> getN02FieldNameMap() throws Exception {
        try {
            Map<String, String> fieldNameMap = new HashMap<String, String>();
            fieldNameMap.put("2020", "setUtr");
            fieldNameMap.put("2006", "setRelatedRefNo");
            fieldNameMap.put("5756", "setSenderIfsc");
            fieldNameMap.put("6305", "setSenderAcype");
            fieldNameMap.put("6021", "setSenderAcc");
            fieldNameMap.put("6091", "setSenderName");
            fieldNameMap.put("7002", "setRemittanceOriginator");
            fieldNameMap.put("5569", "setReceiverIfsc");
            fieldNameMap.put("6310", "setBeneficiaryAcType");
            fieldNameMap.put("6061", "setBeneAccount");
            fieldNameMap.put("6081", "setBeneName");
            fieldNameMap.put("5565", "setBeneAdd");
            fieldNameMap.put("7495", "setRemitInfo");
            fieldNameMap.put("6346", "setReasonCode");
            fieldNameMap.put("6366", "setRejectReason");
            fieldNameMap.put("4038", "setTxnAmount");
            fieldNameMap.put("3380", "setTimestamp");
            fieldNameMap.put("3375", "setRemittanceDate");

            return fieldNameMap;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public static List<ExcelReaderPojo> parseSbiIncomingR41Message(String receivedMessage, Map<String, String> fieldNameMap,
            String senderIfsc, String receiverIfsc) throws Exception {
        List<ExcelReaderPojo> pojoList = new ArrayList<ExcelReaderPojo>();
        try {
            String block4 = receivedMessage.substring(receivedMessage.indexOf("{4::") + 4, receivedMessage.trim().length() - 2);
            String[] block4Arr = block4.split(":");
            R41InwardAdaptor obj = new R41InwardAdaptor();
            for (int i = 0; i < block4Arr.length; i = i + 2) {
                Method myObjMethod = Class.forName("com.cbs.pojo.neftrtgs.sbi.R41InwardAdaptor").getMethod(fieldNameMap.get(block4Arr[i]), String.class);
                myObjMethod.invoke(obj, block4Arr[i + 1]);
            }

            ExcelReaderPojo pojoObj = new ExcelReaderPojo();

            pojoObj.setUtr(obj.getTranRefNo());
            pojoObj.setTimestamp(ymdhh.format(ymd.parse(obj.getValueDtCurrencyAndAmount().substring(0, 8))));
            String txnAmount = obj.getValueDtCurrencyAndAmount().substring(11).replace(',', '.');
            pojoObj.setAmount(new BigDecimal(Double.parseDouble(txnAmount)));

            String[] lines = obj.getOrderingCustomer().split(System.getProperty("line.separator"));
            String senderName = "", senderAddress = "";
            senderName = lines[0];
            for (int i = 1; i < lines.length; i++) {
                senderAddress = senderAddress + lines[i];
            }
            pojoObj.setSenderName(senderName);
            pojoObj.setSenderAddOne(senderAddress);

            lines = obj.getBeneficiaryCustomer().split(System.getProperty("line.separator"));
            String benAcc = "";
            benAcc = lines[0].substring(1);

            pojoObj.setBeneAccount(benAcc);
            pojoObj.setBeneName(lines[1]);
            pojoObj.setBeneAdd(lines[2]);

            pojoObj.setCopBankAccNo("");
            pojoObj.setIfsccode("");
            pojoObj.setSenderAddTwo("");
            pojoObj.setTxnType("R41");
            pojoObj.setReceiverBankName("");
            pojoObj.setReceiverBankCode("");
            pojoObj.setReceiverBankAddress("");
            pojoObj.setSponsorBankName("");
            pojoObj.setSponsorBankCode("");
            pojoObj.setSponsorIfsc("");
            pojoObj.setSponsorRefNo("");
            pojoObj.setSponsorAddress("");
            pojoObj.setSenderBankName("");
            pojoObj.setSenderBankCode("");
            pojoObj.setReason("");
            pojoObj.setSenderAcc("");
            pojoObj.setSenderIfsc(senderIfsc);
            pojoObj.setReceiverIfsc(receiverIfsc);
            pojoObj.setReasonCode("");
            pojoObj.setRemitInfo("");
            pojoObj.setRemittanceOriginator("");
            pojoObj.setTxnAmount("");
            pojoObj.setHeaderUtr("");
            pojoObj.setBatchTime("");
            pojoObj.setRelatedRefNo("");
            pojoObj.setSenderAcype("");
            pojoObj.setBeneficiaryAcType("");
            pojoObj.setRejectReason("");
            pojoObj.setRemittanceDate("");
            pojoObj.setReturnTranRefNo("");

            pojoList.add(pojoObj);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return pojoList;
    }

    public static Map<String, String> getR41AdaptorFieldNameMap() throws Exception {
        try {
            Map<String, String> fieldNameMap = new HashMap<String, String>();
            fieldNameMap.put("2020", "setTranRefNo");
            fieldNameMap.put("4488", "setValueDtCurrencyAndAmount");
            fieldNameMap.put("5500", "setOrderingCustomer");
            fieldNameMap.put("5629", "setMobOrEmail");
            fieldNameMap.put("5517", "setOrderingInstitution_5517");
            fieldNameMap.put("5516", "setOrderingInstitution_5516");
            fieldNameMap.put("5518", "setSenderCorrespondent_5518");
            fieldNameMap.put("6717", "setSenderCorrespondent_6717");
            fieldNameMap.put("5521", "setSenderCorrespondent_5521");
            fieldNameMap.put("6500", "setReceiverCorrespondent_6500");
            fieldNameMap.put("6718", "setReceiverCorrespondent_6718");
            fieldNameMap.put("5526", "setReceiverCorrespondent_5526");
            fieldNameMap.put("6511", "setIntermediary_6511");
            fieldNameMap.put("5546", "setIntermediary_5546");
            fieldNameMap.put("6516", "setAccountWithInstitution_6516");
            fieldNameMap.put("6719", "setAccountWithInstitution_6719");
            fieldNameMap.put("5551", "setAccountWithInstitution_5551");
            fieldNameMap.put("5561", "setBeneficiaryCustomer");
            fieldNameMap.put("7023", "setDetailsOfPayment");
            fieldNameMap.put("7028", "setDetailsOfCharges");
            fieldNameMap.put("7495", "setSenderToReceiverInformation");

            return fieldNameMap;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public static Map<String, String> getR09AdaptorFieldNameMap() throws Exception {
        try {
            Map<String, String> fieldNameMap = new HashMap<String, String>();
            fieldNameMap.put("2020", "setTranRefNo");
            fieldNameMap.put("6450", "setSettledIndicator");
            fieldNameMap.put("6346", "setReasonCode");
            fieldNameMap.put("3525", "setSettlementTime");

            return fieldNameMap;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public static AckR09Adapter parseR09AckMessage(String receivedMessage, Map<String, String> fieldNameMap) throws Exception {
        AckR09Adapter obj = new AckR09Adapter();
        try {
            String block4 = receivedMessage.substring(receivedMessage.indexOf("{4:") + 4, receivedMessage.trim().length() - 2);
            String[] block4Arr = block4.split(":");
            for (int i = 0; i < block4Arr.length; i = i + 2) {
                Method myObjMethod = Class.forName("com.cbs.pojo.neftrtgs.sbi.AckR09Adapter").getMethod(fieldNameMap.get(block4Arr[i]), String.class);
                myObjMethod.invoke(obj, block4Arr[i + 1]);
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return obj;
    }

    public static Map<String, String> getR90AdaptorFieldNameMap() throws Exception {
        try {
            Map<String, String> fieldNameMap = new HashMap<String, String>();
            fieldNameMap.put("2020", "setTranRefNo");
            fieldNameMap.put("1076", "setAckIndicator");
            fieldNameMap.put("6346", "setReasonCode");

            return fieldNameMap;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public static AckR90Adapter parseR90AckMessage(String receivedMessage, Map<String, String> fieldNameMap) throws Exception {
        AckR90Adapter obj = new AckR90Adapter();
        try {
            String block4 = receivedMessage.substring(receivedMessage.indexOf("{4:") + 4, receivedMessage.trim().length() - 2);
            String[] block4Arr = block4.split(":");
            for (int i = 0; i < block4Arr.length; i = i + 2) {
                Method myObjMethod = Class.forName("com.cbs.pojo.neftrtgs.sbi.AckR90Adapter").getMethod(fieldNameMap.get(block4Arr[i]), String.class);
                myObjMethod.invoke(obj, block4Arr[i + 1]);
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return obj;
    }

    public static String getFixedLengthNewLineStr(String completeStr, int lineLength) throws Exception {
        String info = "", internalStr = "";
        try {
            completeStr = completeStr.trim().replaceAll("[\\W_]", " ");
            int remInfoLength = completeStr.length();
            for (int i = 0; i < remInfoLength; i = i + lineLength) {
                if (i + lineLength <= remInfoLength) {
                    internalStr = completeStr.substring(i, i + lineLength);
                } else {
                    internalStr = completeStr.substring(i, remInfoLength);
                }
                info = info.equals("") ? internalStr : info + "\n" + internalStr;
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return info;
    }

    public static ExcelReaderPojo adaptObjectFromR42Msg(R42 r42) throws ApplicationException {
        ExcelReaderPojo pojo = new ExcelReaderPojo();
        try {
            pojo.setUtr(r42.getF2006_1());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return pojo;
    }

    //Customer daily and weekly Rupay(CDR/CWR),Customer daily and weekly AEPS(CDA/CWA)
    public static List<NpciInwardDto> parseCDRCWRCDAAndCWA(File textFile) throws IOException, ApplicationException {
        FileInputStream fis = null;
        DataInputStream dis = null;
        BufferedReader br = null;
        try {
            List<NpciInwardDto> pojoList = new ArrayList<NpciInwardDto>();
            fis = new FileInputStream(textFile);
            dis = new DataInputStream(fis);
            br = new BufferedReader(new InputStreamReader(dis));
            String line = null, settlementDt = "", headerDestIIN = "", settlementCycle = "";
            while ((line = br.readLine()) != null) {
                String apbsTranCode = line.substring(0, 2);
                if (apbsTranCode.equals("12")) {    //12 For Header.
                    settlementDt = line.substring(125, 133);    //Header Settlement Date.
                    headerDestIIN = line.substring(160, 171);   //Header Dest IIN.
                    settlementCycle = line.substring(171, 173); //Header Settlement Cycle.
                }
                if (apbsTranCode.equals("23")) {    //23 For Record.
                    NpciInwardDto pojo = new NpciInwardDto();

                    pojo.setApbsTranCode(apbsTranCode);                         //ACH Transaction Code(Detail)
                    pojo.setControll2nd(line.substring(2, 11));
                    pojo.setDestAcType(line.substring(11, 13));                 //Destination Account Type
                    pojo.setLedgerNo(line.substring(13, 16));                   //Win Category
                    pojo.setControll5th(line.substring(16, 31));
                    pojo.setBeneName(line.substring(31, 71));                   //Beneficiary Account Holders Name
                    pojo.setControll7th(line.substring(71, 80));
                    pojo.setControll8th(line.substring(80, 87));
                    pojo.setUserName(line.substring(87, 107));                  //Transaction date+RRN
                    pojo.setControll10th(line.substring(107, 120));
                    pojo.setAmount(line.substring(120, 133));                   //Amount
                    pojo.setItemSeqNo(line.substring(133, 143));                //Reserved (ACH Item Seq No.)
                    pojo.setCheckSum(line.substring(143, 153));
                    pojo.setAchFiller(line.substring(153, 160));
                    pojo.setDestBankIIN(line.substring(160, 171));              //Destination Bank IFSC / MICR / IIN
                    pojo.setDestBankAcno(line.substring(171, 206));             //Rupay-CDR/CWR(Card Number),AEPS-CDA/CWA(AADHAR)
                    pojo.setSponsorBankIIN(line.substring(206, 217));           //Sponsor Bank IFSC/MICR/IIN
                    pojo.setUserNumber(line.substring(217, 235));               //User Number
                    pojo.setUserCreditRef(line.substring(235, 265));            //unique_winID+Tansaction amount
                    pojo.setProductType(line.substring(265, 268));
                    pojo.setBeneAadhaarNo(line.substring(268, 283));            //Aadhaar Number
                    pojo.setUmrn(line.substring(283, 303));                     //Rupay-CDR/CWR(ISSBank_Cd+ ISSBank_Name),AEPS-CDA/CWA(shispt+ISSBnknm)
                    pojo.setReservedFlag(line.substring(303, 304));
                    pojo.setReservedReason(line.substring(304, 306));
                    pojo.setSettlementDt(ymd.format(ymdOne.parse(settlementDt)));   //Settlement Date
                    pojo.setSettlementCycle(settlementCycle);
                    pojo.setHeaderDestIIN(headerDestIIN);
                    pojo.setFileRefNo("");

                    pojoList.add(pojo);
                }
            }
            return pojoList;
        } catch (Exception ex) {
            throw new ApplicationException("Invalid File Format." + ex.getMessage());
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (dis != null) {
                dis.close();
            }
            if (br != null) {
                br.close();
            }
        }
    }

    public static boolean isValidIfsc(String ifsc) throws Exception {
        try {
            if (ifsc == null || ifsc.trim().equals("")) {
                return false;
            }
            ifsc = ifsc.trim();
            if (ifsc.length() != 11) {
                return false;
            }
            if (!ParseFileUtil.isAlphabet(ifsc.substring(0, 4))) {
                return false;
            }
            if (!ifsc.substring(4, 5).equals("0")) {
                return false;
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return true;
    }

    /**
     *
     * @param amount
     * @param count
     * @return
     * @throws Exception If you do not want to pass the 0 amount then you have
     * to make separate checking. And also for 0 with desired decimal palces(For
     * example- if you want amount upto 2 decimal places then you have to stop
     * 0.00 also)
     */
    public static boolean isValidAmount(String amount, int count) throws Exception {
        try {
            if (amount == null) {
                return false;
            }
            amount = amount.trim();
            if (amount.equals("")) {
                return false;
            }
            Pattern pattern = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher isValid = pattern.matcher(amount);
            if (!isValid.matches()) {
                return false;
            }
            if (amount.indexOf(".") != amount.lastIndexOf(".")) {
                return false;
            }
            String[] splitter = amount.split("\\.");
            if (splitter.length == 1) {
                return true;
            }
            int decimalLength = splitter[1].length();  //After decimal count
            if (decimalLength == count) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    /*
     * If str contains a space then it will return true.
     */
    public boolean isHavingWhiteSpace(String str) throws Exception {
        try {
            Pattern pattern = Pattern.compile("\\s");
            Matcher matcher = pattern.matcher(str);
            return matcher.find();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    /*
     * If str contains a space then it will return true.
     */
    public static boolean isValidPAN(String pan) throws Exception {
        try {
            if (pan == null || pan.equals("") || pan.trim().length() != 10) {
                return false;
            }
            if (!isAlphabet(pan.substring(0, 5))) {
                return false;
            }
            if (!isNumeric(pan.substring(5, 9))) {
                return false;
            }
            if (!isAlphabet(pan.substring(9))) {
                return false;
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return true;
    }
    //Parsing of ACH DR Response 306 Character.

    public static List<NpciInwardDto> parseAchDrResponseNewTxtFile(File textFile, String fileSeqNo) throws
            IOException, ApplicationException {
        FileInputStream fis = null;
        DataInputStream dis = null;
        BufferedReader br = null;
        try {
            List<NpciInwardDto> pojoList = new ArrayList<>();
            fis = new FileInputStream(textFile);
            dis = new DataInputStream(fis);
            br = new BufferedReader(new InputStreamReader(dis));
            String line = null, settlementDt = "", settlementCycle = "", headerDestIIN = "", headerId = "", totalItem = "";
            while ((line = br.readLine()) != null) {
                String apbsTranCode = line.substring(0, 2);
                if (apbsTranCode.equals("56")) {    //56 For Header.
                    headerId = apbsTranCode;
                    settlementDt = line.substring(125, 133);    //Header Settlement Date.
                    headerDestIIN = line.substring(163, 174);   //Header Dest IIN.
                    totalItem = line.substring(238, 247);       //file total item.
                }
                if (apbsTranCode.equals("44") || apbsTranCode.equals("67")) {    //44-67 For Record.
                    NpciInwardDto pojo = new NpciInwardDto();
                    pojo.setHeaderId(headerId);
                    pojo.setRecordId(apbsTranCode);                         //ACH Transaction Code(Detail)
                    pojo.setControll2nd(line.substring(2, 11));
                    pojo.setDestAcType(line.substring(11, 13));                 //Destination Account Type
                    pojo.setLedgerNo(line.substring(13, 16));                   //Ledger Folio Number
                    pojo.setControll5th(line.substring(16, 31));
                    pojo.setBeneName(line.substring(31, 71));                   //Beneficiary Account Holders Name
                    pojo.setControll7th(line.substring(71, 80));
                    pojo.setControll8th(line.substring(80, 87));
                    pojo.setUserName(line.substring(87, 107));                  //User Name/Narration
                    pojo.setControll10th(line.substring(107, 120));
                    pojo.setAmount(line.substring(120, 133));                   //Amount
                    pojo.setReservedOne(line.substring(133, 143));              //Reserved(ACH Item Seq No.)
                    pojo.setCheckSum(line.substring(143, 153));
                    /*
                     14	Flag for success / return               1
                     15	Reason Code                             2
                     16	Destination Bank IFSC / MICR / IIN	11
                     17	Beneficiary's Bank Account number	35
                     18	Sponsor Bank IFSC / MICR / IIN          11
                     19	User Number                             18
                     20	Transaction Reference                   30
                     21	Product Type                            3
                     22	Beneficiary Aadhaar Number      	15
                     23	UMRN                                    20
                     24	Filler                                  7
                     */

                    pojo.setReservedFlag(line.substring(153, 154));             //flag success
                    pojo.setReservedReason(line.substring(154, 156));           //Reson Code
                    pojo.setDestBankIIN(line.substring(156, 167));              //Destination Bank IFSC / MICR/IIN
                    pojo.setDestBankAcno(line.substring(167, 202));             //Beneficiarys Bank Account number
                    pojo.setSponsorBankIIN(line.substring(202, 213));           //Sponsor Bank IFSC/MICR/IIN
                    pojo.setUserNumber(line.substring(213, 231));               //User Number
                    pojo.setUserCreditRef(line.substring(231, 261));            //Transaction Reference
                    pojo.setProductType(line.substring(261, 264));
                    pojo.setBeneAadhaarNo(line.substring(264, 279));            //Beneficiary Aadhaar Number
                    pojo.setUmrn(line.substring(279, 299));
                    pojo.setFiller(line.substring(299, 306));
                    pojo.setSettlementDt(ymd.format(ymdOne.parse(settlementDt)));   //Settlement Date
                    pojo.setSettlementCycle(settlementCycle);
                    pojo.setHeaderDestIIN(headerDestIIN);
                    pojo.setItemSeqNo(fileSeqNo);                                   //file name seq No
                    pojo.setReservedTwo("");
                    pojo.setReservedThree("");
                    pojoList.add(pojo);
                }
            }
            return pojoList;
        } catch (Exception ex) {
            throw new ApplicationException("Invalid File Format." + ex.getMessage());
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (dis != null) {
                dis.close();
            }
            if (br != null) {
                br.close();
            }
        }
    }

    //Parsing of ECS DR Response 156 Character.
    public static List<NpciInwardDto> parseEcsDrResponseNewTxtFile(File textFile, String fileSeqNo) throws
            IOException, ApplicationException {
        FileInputStream fis = null;
        DataInputStream dis = null;
        BufferedReader br = null;
        try {
            List<NpciInwardDto> pojoList = new ArrayList<>();
            fis = new FileInputStream(textFile);
            dis = new DataInputStream(fis);
            br = new BufferedReader(new InputStreamReader(dis));
            String line = null, settlementDt = "", settlementCycle = "", headerDestIIN = "", headerId = "", userRefNo = "";
            while ((line = br.readLine()) != null) {
                String apbsTranCode = line.substring(0, 2);
                if (apbsTranCode.equals("55")) {    //55 For Header.
                    /*
                     1	1	ECS transaction code for NECS       2	
                     2	2	User Number                         7	
                     3	3	User Name                           40	
                     4	4	User Reference                      14	
                     5	6	Sponsor Bank MICR                   9	
                     6	10	Total Amount  (Balancing Amount)    13	
                     7	11	Settlement Date (DDMMYYYY)          8	
                     */
                    headerId = apbsTranCode;
                    userRefNo = line.substring(49, 63);
                    headerDestIIN = line.substring(72, 81);   //Header Dest IIN.
                    settlementDt = line.substring(125, 133); //Header Settlement Date.
                }
                if (apbsTranCode.equals("44") || apbsTranCode.equals("66")) {    //66 For  success Record. 44 for undebited  record
                    NpciInwardDto pojo = new NpciInwardDto();
                    pojo.setHeaderId(headerId);
                    pojo.setRecordId(apbsTranCode);                         //ACH Transaction Code(Detail)
                    /*
                     1	ECS Transaction Code            2
                     2	Destination sort code           9
                     3	Destination Account Type	2
                     4	Ledger Folio Number             3
                     5	Destination Account number	15
                     6	Destination Account Holder's name	40
                     7	Sponsor Bank MICR               9
                     8	User Number                     7
                     9	User Name                       20
                     10	Transaction Reference           13
                     11	Amount                          13
                     12	ACH Item Seq No.                10
                     13	Checksum                        10
                     14	Flag for success / return	1
                     15	Filler                          1
                     16	Reason Code                     1
                     */
                    pojo.setDestBankIIN(line.substring(2, 11));              //Destination Bank IFSC / MICR/IIN
                    pojo.setDestAcType(line.substring(11, 13));                 //Destination Account Type
                    pojo.setLedgerNo(line.substring(13, 16));                   //Ledger Folio Number
                    pojo.setDestBankAcno(line.substring(16, 31));             //Beneficiarys Bank Account number
                    pojo.setBeneName(line.substring(31, 71));                   //Beneficiary Account Holders Name
                    pojo.setSponsorBankIIN(line.substring(71, 80));           //Sponsor Bank IFSC/MICR/IIN
                    pojo.setUserName(line.substring(80, 87));                  //User Name/Narration
                    pojo.setUserNumber(line.substring(87, 107));               //User Number
                    pojo.setUserCreditRef(line.substring(107, 120));            //Transaction Reference
                    pojo.setAmount(line.substring(120, 133));                   //Amount
                    pojo.setItemSeqNo(line.substring(133, 143));               //file name seq No
                    pojo.setCheckSum(line.substring(143, 153));
                    pojo.setReservedFlag(line.substring(153, 154));             //flag success
//                    pojo.setFiller(line.substring(154, 155));
                    pojo.setReservedReason(line.substring(154).trim());           //Reson Code
                    pojo.setSettlementDt(ymd.format(ymdOne.parse(settlementDt)));   //Settlement Date
                    pojo.setSettlementCycle(settlementCycle);
                    pojo.setHeaderDestIIN(headerDestIIN);
                    pojo.setFileRefNo(userRefNo);
                    pojo.setReservedTwo("");
                    pojo.setReservedThree("");
                    pojoList.add(pojo);
                }
            }
            return pojoList;
        } catch (Exception ex) {
            throw new ApplicationException("Invalid File Format." + ex.getMessage());
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (dis != null) {
                dis.close();
            }
            if (br != null) {
                br.close();
            }
        }
    }

    public static List<ExcelReaderPojo> parseAxisInwData(String acno, String benName, String valIfsc, String amount,
            String tranDate, String tranType, String remitterAccount, String remitterName, String remitterIfsc,
            String utrNo, String valueDate) throws Exception {
        List<ExcelReaderPojo> dataList = new ArrayList<>();
        try {
            ExcelReaderPojo pojo = new ExcelReaderPojo();

            pojo.setCopBankAccNo("");
            pojo.setIfsccode(valIfsc);
            pojo.setUtr(utrNo);
            pojo.setBeneAccount(acno);
            pojo.setReceiverIfsc(valIfsc);

            pojo.setAmount(new BigDecimal(amount));
            pojo.setTimestamp(ymdhh.format(ymdh.parse(valueDate)));
            pojo.setSenderAcc(remitterAccount);
            pojo.setSenderIfsc(remitterIfsc);
            pojo.setSenderName(remitterName);

            pojo.setSenderAddOne("");
            pojo.setSenderAddTwo("");
            pojo.setTxnType(tranType);
            pojo.setBeneName(benName);
            pojo.setBeneAdd("");

            pojo.setReceiverBankName("");
            pojo.setReceiverBankCode("");
            pojo.setReceiverBankAddress("");
            pojo.setSponsorBankName("");
            pojo.setSponsorBankCode("");

            pojo.setSponsorIfsc("");
            pojo.setSponsorRefNo("");
            pojo.setSponsorAddress("");
            pojo.setSenderBankName("");
            pojo.setSenderBankCode("");
            pojo.setBeneAdd("");
            pojo.setRemitInfo("");
            pojo.setRemittanceOriginator("");
            pojo.setTranDate(tranDate);

            dataList.add(pojo);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return dataList;
    }

    public static List<ExcelReaderPojo> parseIBLInwardExcel(File xlsFile) throws IOException, ApplicationException {
        SimpleDateFormat y1 = new SimpleDateFormat("dd/MM/yyyy"); // 05/12/2017
        SimpleDateFormat y2 = new SimpleDateFormat("dd/MMM/yy"); // 05/Dec/17
        try {
            List<ExcelReaderPojo> pojoList = new ArrayList<>();

            Workbook wb = new XSSFWorkbook(new BufferedInputStream(new FileInputStream(xlsFile)));
            Sheet sheet = wb.getSheetAt(0);
            Iterator rows = sheet.rowIterator();
            int noOfColumns = 0;

            while (rows.hasNext()) {
                List<String> tempList = new ArrayList<>();
                ExcelReaderPojo pojo = new ExcelReaderPojo();
                XSSFRow row = (XSSFRow) rows.next();
                if (row.getRowNum() == 0) {
                    noOfColumns = row.getLastCellNum();
                    continue;
                }

                for (int i = 0; i < noOfColumns; i++) {
                    XSSFCell cell = (XSSFCell) row.getCell(i, Row.CREATE_NULL_AS_BLANK);
                    if (XSSFCell.CELL_TYPE_NUMERIC == cell.getCellType()) {
                        if (DateUtil.isCellDateFormatted(cell)) {
                            System.out.println("Date Value Is-->" + cell.getDateCellValue());
                            tempList.add(ymdhh.format(cell.getDateCellValue()));
                        } else {
                            tempList.add(String.valueOf(cell.getNumericCellValue()));
                        }
                    } else if (XSSFCell.CELL_TYPE_STRING == cell.getCellType()) {
                        tempList.add(cell.getStringCellValue());
                    } else if (XSSFCell.CELL_TYPE_BLANK == cell.getCellType()) {
                        tempList.add("");
                    }
                }

                if (!tempList.get(0).equals("")) {
                    pojo.setBeneName(tempList.get(0).trim());
//                    pojo.setReceiverBankCode(tempList.get(1).trim());
//                    pojo.setBeneAccount(tempList.get(2).trim());
                    pojo.setReceiverBankCode(tempList.get(2).trim());
                    pojo.setBeneAccount(tempList.get(1).trim());
                    pojo.setIfsccode(tempList.get(3).trim());
                    System.out.println("List Value Is-->" + tempList.get(4).trim());
//                    pojo.setTimestamp(ymdhh.format(y2.parse(tempList.get(4).trim())));
                    pojo.setTimestamp(tempList.get(4).trim());
                    pojo.setAmount(new BigDecimal(tempList.get(5).trim()));
                    pojo.setUtr(tempList.get(6).trim());
                    pojo.setSenderAcc(tempList.get(7).trim());
                    pojo.setSenderName(tempList.get(8).trim());
                    pojo.setSenderBankName(tempList.get(9).trim());
                    pojo.setSenderBankCode(tempList.get(10).trim());
                    pojo.setSenderIfsc(tempList.get(11).trim());
                    pojo.setReceiverIfsc(tempList.get(3).trim());
                    if (pojo.getAmount().compareTo(new BigDecimal("200000")) <= 0) {
                        pojo.setTxnType("N");
                    } else {
                        pojo.setTxnType("R");
                    }

                    pojo.setBatchTime("");
                    pojo.setRelatedRefNo("");
                    pojo.setSenderAcype("");
                    pojo.setRemittanceOriginator("");
                    pojo.setBeneficiaryAcType("");
                    pojo.setBeneAdd("");
                    pojo.setRemitInfo("");
                    pojo.setReasonCode("");
                    pojo.setReason("");
                    pojo.setCopBankAccNo("");
                    pojo.setSenderAddOne("");
                    pojo.setSenderAddTwo("");
                    pojo.setReceiverBankName("");
                    pojo.setReceiverBankCode("");
                    pojo.setReceiverBankAddress("");
                    pojo.setSponsorBankName("");
                    pojo.setSponsorBankCode("");
                    pojo.setSponsorIfsc("");
                    pojo.setSponsorRefNo("");
                    pojo.setSponsorAddress("");

                    pojoList.add(pojo);
                }
            }
            return pojoList;
        } catch (IOException ex) {
            System.out.println("** Parsing error" + ex.getMessage());
            throw new IOException("Invalid File Format.");
        } catch (Exception ex) {
            throw new ApplicationException("Invalid File Format.");
        }
    }

    public static List<NpciInwardDto> parseNpciCbdtInwardTxtFile(File textFile, String fileDt, String fileSeqNo)
            throws IOException, ApplicationException {
        FileInputStream fis = null;
        DataInputStream dis = null;
        BufferedReader br = null;
        try {
            List<NpciInwardDto> pojoList = new ArrayList<>();
            fis = new FileInputStream(textFile);
            dis = new DataInputStream(fis);
            br = new BufferedReader(new InputStreamReader(dis));
            String line = null, originatorCode = "", responderCode = "", fileRefNo = "", settlementDt = "";
            while ((line = br.readLine()) != null) {
                String apbsTranCode = line.substring(0, 2);
                if (apbsTranCode.equals("30")) {    //30 For Header.
                    originatorCode = line.substring(2, 13).trim();
                    responderCode = line.substring(13, 24).trim();
                    fileRefNo = line.substring(24, 34).trim();
                    settlementDt = ymd.format(ymdOne.parse(fileDt));
                }
                if (apbsTranCode.equals("70")) {    //70 For Record.
                    NpciInwardDto pojo = new NpciInwardDto();

                    pojo.setOriginatorCode(originatorCode);
                    pojo.setResponderCode(responderCode);
                    pojo.setFileRefNo(fileRefNo);
                    pojo.setSettlementDt(settlementDt);
                    pojo.setFileSeqNo(fileSeqNo);
                    pojo.setApbsTranCode(line.substring(0, 2).trim()); //Record_Identifier
                    pojo.setUserCreditRef(line.substring(2, 17).trim());    //RRN
                    pojo.setDestBankIIN(line.substring(17, 28).trim());  //Dest_Bank_Ifsc
                    pojo.setDestBankAcno(line.substring(28, 63).trim()); //Dest_Bank_Acno
                    pojoList.add(pojo);
                }
            }
            return pojoList;
        } catch (Exception ex) {
            throw new ApplicationException("Invalid File Format." + ex.getMessage());
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (dis != null) {
                dis.close();
            }
            if (br != null) {
                br.close();
            }
        }
    }

    public static String addTrailingDesiredCharacter(String str, int length, String desiredChar) {
        String actualStr = str.trim().substring(length);
        for (int i = 0; i < length; i++) {
            actualStr = desiredChar + actualStr;
        }
        return actualStr;
    }
}
