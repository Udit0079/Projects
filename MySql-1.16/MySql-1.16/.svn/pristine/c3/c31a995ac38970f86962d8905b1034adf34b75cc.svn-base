/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.neftrtgs;

import com.cbs.entity.neftrtgs.CbsAutoNeftDetails;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.neftrtgs.H2HMgmtFacadeRemote;
import com.cbs.facade.neftrtgs.UploadNeftRtgsMgmtFacadeRemote;
import com.cbs.pojo.neftrtgs.ExcelReaderPojo;
import com.cbs.utils.ParseFileUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class UploadNeftRtgs extends BaseBean {

    private String message;
    private String bank;
    private List<SelectItem> bankList;
    private UploadedFile uploadedFile;
    private UploadNeftRtgsMgmtFacadeRemote remote = null;
    private final String jndiHomeName = "UploadNeftRtgsMgmtFacade";
    private H2HMgmtFacadeRemote h2hRemote;
    private NumberFormat formater = new DecimalFormat("#.##");
    private SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat ymdhh = new SimpleDateFormat("dd-MMM-yy-hh:mm:ss");
    private SimpleDateFormat ddMMMyyyy = new SimpleDateFormat("dd-MMM-yyyy");

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public List<SelectItem> getBankList() {
        return bankList;
    }

    public void setBankList(List<SelectItem> bankList) {
        this.bankList = bankList;
    }

    public UploadNeftRtgs() {
        try {
            this.setMessage("Click on browse button to upload the file...");
            remote = (UploadNeftRtgsMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            h2hRemote = (H2HMgmtFacadeRemote) ServiceLocator.getInstance().lookup("H2HMgmtFacade");
            retrieveNeftBanks();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void retrieveNeftBanks() {
        try {
            bankList = new ArrayList<SelectItem>();
            List<CbsAutoNeftDetails> autoNeftList = remote.getAutoNeftDetailsByProcess("manual");
            if (autoNeftList == null || autoNeftList.isEmpty()) {
                this.setMessage("Please fill details in cbs auto neft details !");
                return;
            }
            bankList.add(new SelectItem("--Select--"));
            for (CbsAutoNeftDetails obj : autoNeftList) {
                String neftBankName = obj.getNeftBankName();
                if (neftBankName == null || neftBankName.equals("")) {
                    this.setMessage("Please fill neft bank name in cbs auto neft details !");
                    return;
                }
                bankList.add(new SelectItem(neftBankName));
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void uploadProcess() {
        this.setMessage("");
        if (uploadedFile == null || bank == null || bank.equals("--Select--")) {
            this.setMessage("Please select appropriate bank and file to upload !");
        } else {
            String uploadedFileName = uploadedFile.getName();
            if (uploadedFileName == null || uploadedFileName.equalsIgnoreCase("")) {
                this.setMessage("Please select appropriate file name to upload !");
                return;
            }
            try {
                CbsAutoNeftDetails autoObj = remote.getNeftDetailsByNefBankNameAndProcess(bank, "manual");
                if (autoObj.getNeftBankName() == null || autoObj.getNeftBankName().trim().equals("")
                        || autoObj.getNeftBankName().trim().length() == 0) {
                    this.setMessage("Please define appropriate neft bank name.");
                    return;
                }
                if (autoObj.getProcessType() == null || autoObj.getProcessType().trim().equals("")
                        || autoObj.getProcessType().trim().length() == 0) {
                    this.setMessage("Please define appropriate process type.");
                    return;
                }
                if (autoObj.getNeftBankName().trim().equalsIgnoreCase("IBL")) {
                    if (autoObj.getOwHead() == null
                            || autoObj.getOwHead().equals("")
                            || autoObj.getOwHead().length() != 12) {
                        this.setMessage("Please define appropriate outward head for bank: " + bank);
                        return;
                    }
                } else {
                    if (autoObj.getIwHead() == null
                            || autoObj.getIwHead().equals("")
                            || autoObj.getIwHead().length() != 12) {
                        this.setMessage("Please define appropriate inward head for bank: " + bank);
                        return;
                    }
                    if (autoObj.getIwFileType() == null
                            || autoObj.getIwFileType().trim().equals("")) {
                        this.setMessage("Please define appropriate manual inward file type for bank: " + bank);
                        return;
                    }
                }

                String fileContentType = uploadedFile.getContentType();
                System.out.println("File content type " + fileContentType);

                ServletContext context = (ServletContext) getFacesContext().getCurrentInstance().
                        getExternalContext().getContext();
                String directory = context.getInitParameter("cbsFiles");
                File dir = new File(directory + "/");
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                File xlsFile = new File(dir.getCanonicalPath() + File.separatorChar + uploadedFileName);
                if (bank.equalsIgnoreCase("hdfc")
                        && autoObj.getIwFileType().equalsIgnoreCase("csv")) { //ccbl,kcbl,iucb,tucb,snsb
                    if (fileContentType.equals("text/csv")
                            || fileContentType.equals("application/octet-stream")
                            || fileContentType.equals("application/vnd.ms-excel")) {
                        List<ExcelReaderPojo> pojoList = new ArrayList<ExcelReaderPojo>();
                        try {
                            pojoList = parseCSV(xlsFile, uploadedFileName.trim());
                        } catch (Exception e) {
                            this.setMessage("Proble in reading the CSV File." + e.getMessage());
                            return;
                        }
                        //Implementing Business Logic
                        String result = remote.neftRtgsUploadProcess(pojoList, getOrgnBrCode(), getUserName(),
                                autoObj.getIwHead(), autoObj.getProcess(), autoObj.getNeftBankName());
                        iwFileConclusionAfterProcessing(result);
                    } else {
                        System.out.println("Uploded File has not proper extension.");
                        this.setMessage("Please select appropriate file to upload !");
                    }
                } else if (bank.equalsIgnoreCase("yes")
                        && autoObj.getIwFileType().equalsIgnoreCase("xls")) { //kucb,ucbh,bucb
                    if (fileContentType.equals("application/xls")
                            || fileContentType.equals("application/xsl")
                            || fileContentType.equals("application/vnd.ms-excel")
                            || fileContentType.equals("application/octet-stream")) {
                        List<ExcelReaderPojo> pojoList = new ArrayList<ExcelReaderPojo>();
                        try {
                            pojoList = parseExcel(xlsFile);
                        } catch (IOException ex) {
                            pojoList = parseXML(xlsFile);
                        } catch (ApplicationException ex) {
                            this.setMessage("Invalid File Format.");
                            return;
                        }
                        //Implementing Business Logic
                        String result = remote.neftRtgsUploadProcess(pojoList, getOrgnBrCode(), getUserName(),
                                autoObj.getIwHead(), autoObj.getProcess(), autoObj.getNeftBankName());
                        iwFileConclusionAfterProcessing(result);
                    } else {
                        System.out.println("Uploded File has not proper extension.");
                        this.setMessage("Please select appropriate file to upload !");
                    }
                } else if (bank.equalsIgnoreCase("icici")
                        && autoObj.getIwFileType().equalsIgnoreCase("txt")) { //sicb, eucb
                    if (fileContentType.equals("text/plain")
                            || fileContentType.equals("application/octet-stream")) {
                        List<ExcelReaderPojo> pojoList = new ArrayList<ExcelReaderPojo>();
                        try {
                            byte[] b = uploadedFile.getBytes();
                            FileOutputStream fos = new FileOutputStream(xlsFile);
                            fos.write(b);
                            fos.close();

                            pojoList = ParseFileUtil.parseIciciInwardTxtFile(xlsFile);
                        } catch (Exception e) {
                            this.setMessage("Proble in reading the Text File." + e.getMessage());
                            return;
                        }
                        //Implementing Business Logic
                        String result = remote.neftRtgsUploadProcess(pojoList, getOrgnBrCode(), getUserName(),
                                autoObj.getIwHead(), autoObj.getProcess(), autoObj.getNeftBankName());
                        iwFileConclusionAfterProcessing(result);
                    } else {
                        System.out.println("Uploded File has not proper extension.");
                        this.setMessage("Please select appropriate file to upload !");
                    }
                } else if (bank.equalsIgnoreCase("yes")
                        && autoObj.getIwFileType().equalsIgnoreCase("txt")) { //ucbb,gnsb
                    System.out.println("File Content Type = " + fileContentType);
                    if (fileContentType.equals("text/plain")) {
                        List<ExcelReaderPojo> pojoList = new ArrayList<ExcelReaderPojo>();
                        try {
                            byte[] b = uploadedFile.getBytes();
                            FileOutputStream fos = new FileOutputStream(xlsFile);
                            fos.write(b);
                            fos.close();

                            pojoList = ParseFileUtil.parseYesBankInwardTxtFile(xlsFile);
                        } catch (Exception ex) {
                            this.setMessage("Proble in reading the Text File." + ex.getMessage());
                            return;
                        }
                        //Implementing Business Logic
                        String result = remote.neftRtgsUploadProcess(pojoList, getOrgnBrCode(), getUserName(),
                                autoObj.getIwHead(), autoObj.getProcess(), autoObj.getNeftBankName());
                        iwFileConclusionAfterProcessing(result);
                    } else {
                        System.out.println("Uploded File has not proper extension.");
                        this.setMessage("Please select appropriate file to upload !");
                    }
                } else if (bank.equalsIgnoreCase("yes")
                        && autoObj.getIwFileType().equalsIgnoreCase("pghad")) { //Pratapghad
                    if (fileContentType.equals("application/xls")
                            || fileContentType.equals("application/vnd.ms-excel")
                            || fileContentType.equals("application/octet-stream")
                            || fileContentType.equals("application/xsl")) {
                        List<ExcelReaderPojo> pojoList = new ArrayList<ExcelReaderPojo>();
                        try {
                            byte[] b = uploadedFile.getBytes();
                            FileOutputStream fos = new FileOutputStream(xlsFile);
                            fos.write(b);
                            fos.close();

                            pojoList = ParseFileUtil.parsePghadExcel(xlsFile);
                        } catch (Exception ex) {
                            this.setMessage("Proble in reading the Text File." + ex.getMessage());
                            return;
                        }
                        //Implementing Business Logic
                        String result = remote.neftRtgsUploadProcess(pojoList, getOrgnBrCode(), getUserName(),
                                autoObj.getIwHead(), autoObj.getProcess(), autoObj.getNeftBankName());
                        iwFileConclusionAfterProcessing(result);
                    } else {
                        System.out.println("Uploded File has not proper extension.");
                        this.setMessage("Please select appropriate file to upload !");
                    }
                } else if (bank.equalsIgnoreCase("ibl")) {
                    String fileExtension = uploadedFileName.substring(uploadedFileName.indexOf(".") + 1).trim();
                    if (fileExtension.equalsIgnoreCase("csv")) {//Final IBL Outward Return
                        if (!autoObj.getProcessType().trim().equalsIgnoreCase("OR")) { //Outward Return
                            this.setMessage("IBL return should have process type OW.");
                            return;
                        }

                        if (!(fileContentType.equals("text/csv")
                                || fileContentType.equals("application/octet-stream")
                                || fileContentType.equals("application/vnd.ms-excel"))) {
                            System.out.println("Uploded File has not proper extension.");
                            this.setMessage("Please select appropriate file to upload !");
                            return;
                        }

                        List<ExcelReaderPojo> pojoList = new ArrayList<>();
                        try {
                            pojoList = parseIblCSV(xlsFile);
                        } catch (Exception e) {
                            this.setMessage("Problem in reading the CSV File For Indusand Bank." + e.getMessage());
                            return;
                        }
                        //Implementing Business Logic
                        String result = h2hRemote.finalIblReturn(pojoList, autoObj);
                        if (!result.equalsIgnoreCase("success")) {
                            this.setMessage("There is problem in return file uploading.");
                            return;
                        }
                        this.setMessage("Return file has been uploaded successfully.");
                    }


//                    else if (fileExtension.equalsIgnoreCase("xls")) { //Inward
//                        if (!(fileContentType.equals("application/xls")
//                                || fileContentType.equals("application/xsl")
//                                || fileContentType.equals("application/vnd.ms-excel")
//                                || fileContentType.equals("application/octet-stream"))) {
//                            System.out.println("Uploded File has not proper extension.");
//                            this.setMessage("Please select appropriate file to upload !");
//                            return;
//                        }
//
//                        List<ExcelReaderPojo> pojoList = new ArrayList<>();
//                        try {
//                            byte[] b = uploadedFile.getBytes();
//                            FileOutputStream fos = new FileOutputStream(xlsFile);
//                            fos.write(b);
//                            fos.close();
//
//                            pojoList = ParseFileUtil.parseIBLInwardExcel(xlsFile);
//                        } catch (Exception e) {
//                            this.setMessage("Problem in reading the XLS File For Indusand Bank." + e.getMessage());
//                            return;
//                        }
//                        //Implementing Business Logic
//                        String result = remote.neftRtgsUploadProcess(pojoList, getOrgnBrCode(), getUserName(),
//                                autoObj.getIwHead(), autoObj.getProcess(), autoObj.getNeftBankName());
//                        iwFileConclusionAfterProcessing(result);
//                    }
                } else if (bank.equalsIgnoreCase("idbi")
                        && autoObj.getIwFileType().equalsIgnoreCase("xls")) { //Ramgariya
                    if (fileContentType.equals("application/xls")
                            || fileContentType.equals("application/xsl")
                            || fileContentType.equals("application/vnd.ms-excel")
                            || fileContentType.equals("application/octet-stream")) {
                        List<ExcelReaderPojo> pojoList = new ArrayList<>();
                        String fileType = uploadedFileName.trim().toLowerCase().substring(0, 1);
                        try {
                            pojoList = parseIdbiExcel(xlsFile, fileType);
                        } catch (Exception ex) {
                            this.setMessage("Invalid File Format.");
                            return;
                        }
                        //Implementing Business Logic For Both Inward(NEFT/RTGS) and Outward Return(NEFT/RTGS)
                        String result = "";
                        if (pojoList.get(0).getReasonCode() == null || pojoList.get(0).getReasonCode().equalsIgnoreCase("")) {
                            result = remote.neftRtgsUploadProcess(pojoList, getOrgnBrCode(), getUserName(),
                                    autoObj.getIwHead(), autoObj.getProcess(), autoObj.getNeftBankName());
                            iwFileConclusionAfterProcessing(result);
                        } else {
                            result = h2hRemote.outwardNeftRtgsReversalProcessing(pojoList, autoObj, getOrgnBrCode(), getUserName());
                            if (!result.equalsIgnoreCase("true")) {
                                this.setMessage(result);
                                return;
                            }
                            this.setMessage("Return file has been uploaded successfully.");
                        }
                    } else {
                        System.out.println("Uploded File has not proper extension.");
                        this.setMessage("Please select appropriate file to upload !");
                    }
                } else if (bank.equalsIgnoreCase("axis")
                        && autoObj.getIwFileType().equalsIgnoreCase("txt")) {
                    if (fileContentType.equals("text/plain")
                            || fileContentType.equals("application/octet-stream")) {
                        List<ExcelReaderPojo> pojoList = new ArrayList<>();
                        try {
                            byte[] b = uploadedFile.getBytes();
                            FileOutputStream fos = new FileOutputStream(xlsFile);
                            fos.write(b);
                            fos.close();
                            pojoList = ParseFileUtil.parseAxisBankTextFile(xlsFile);
                        } catch (Exception ex) {
                            this.setMessage("Problem in reding the file " + ex.getMessage());
                            return;
                        }
                        //Implementing Business Logic
                        String result = remote.neftRtgsUploadProcess(pojoList, getOrgnBrCode(), getUserName(),
                                autoObj.getIwHead(), autoObj.getProcess(), autoObj.getNeftBankName());
                        iwFileConclusionAfterProcessing(result);
                    } else {
                        System.out.println("Uploded File has not proper extension.");
                        this.setMessage("Please select appropriate file to upload !");
                    }
                }
            } catch (Exception ex) {
                this.setMessage(ex.getMessage());
            }
        }
    }

    public void iwFileConclusionAfterProcessing(String result) {
        if (!result.equalsIgnoreCase("true")) {
            this.setMessage(result);
            return;
        }
        this.setMessage("Data has been processed successfully. Please check the report.");
    }

    //HSSFWorkbook - For XLS, XSSFWorkbook - For XLSX
    private List<ExcelReaderPojo> parseExcel(File xlsFile) throws IOException, ApplicationException {
        try {
            List<ExcelReaderPojo> pojoList = new ArrayList<ExcelReaderPojo>();

            byte[] b = uploadedFile.getBytes();
            FileOutputStream fos = new FileOutputStream(xlsFile);
            fos.write(b);
            fos.close();

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

    private void replaceAndWriteFile(InputStream is, File file, List<String> replaceStrList) throws IOException {
        List<String> lines = new ArrayList<String>();
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
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

    private List<ExcelReaderPojo> parseXML(File xlsFile) throws ApplicationException {
        try {
            List<String> replaceStrList = new ArrayList<String>();
            replaceStrList.add("<p>");
            replaceStrList.add("&nbsp;");
            replaceAndWriteFile(uploadedFile.getInputStream(), xlsFile, replaceStrList);

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
//                    int totalCol = colList.getLength();

//                    System.out.println("Total no of column: " + totalCol);

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

    private List<ExcelReaderPojo> parseCSV(File csvFile, String iwFileName) throws IOException, ApplicationException {
        FileInputStream fis = null;
        DataInputStream dis = null;
        BufferedReader br = null;
        try {
            List<ExcelReaderPojo> pojoList = new ArrayList<ExcelReaderPojo>();
            byte[] b = uploadedFile.getBytes();
            FileOutputStream fos = new FileOutputStream(csvFile);
            fos.write(b);
            fos.close();

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
                    pojo.setIwFileName(iwFileName);

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

    private List<ExcelReaderPojo> parseExcelForSicb(File xlsFile) throws IOException, ApplicationException {
        try {
            List<ExcelReaderPojo> pojoList = new ArrayList<ExcelReaderPojo>();

            byte[] b = uploadedFile.getBytes();
            FileOutputStream fos = new FileOutputStream(xlsFile);
            fos.write(b);
            fos.close();

            POIFSFileSystem fs = new POIFSFileSystem(new BufferedInputStream(new FileInputStream(xlsFile)));
            HSSFWorkbook wb = new HSSFWorkbook(fs);

            HSSFSheet sheet = wb.getSheetAt(0);

            Iterator rows = sheet.rowIterator();
            while (rows.hasNext()) {
                List<String> tempList = new ArrayList<String>();
                ExcelReaderPojo pojo = new ExcelReaderPojo();
                HSSFRow row = (HSSFRow) rows.next();
                if (row.getRowNum() == 0 || row.getRowNum() == 1) {
                    continue;
                }

                Iterator cells = row.cellIterator();
                while (cells.hasNext()) {
                    HSSFCell cell;
                    try {
                        cell = (HSSFCell) cells.next();
                    } catch (NoSuchElementException e) {
                        continue;
                    }
                    if (HSSFCell.CELL_TYPE_NUMERIC == cell.getCellType()) {
                        tempList.add(String.valueOf(cell.getNumericCellValue()));
                    } else if (HSSFCell.CELL_TYPE_STRING == cell.getCellType()) {
                        tempList.add(cell.getStringCellValue());
                    } else if (HSSFCell.CELL_TYPE_BLANK == cell.getCellType()) {
                        tempList.add("");
                    }
                }
                if (!tempList.get(0).equals("") && tempList.get(0).trim().length() == 12) {
                    pojo.setCopBankAccNo("");
                    pojo.setIfsccode("");
                    pojo.setUtr(tempList.get(3));
                    pojo.setBeneAccount(tempList.get(0));
                    pojo.setReceiverIfsc("");

                    if (!tempList.get(5).equals("")) {
                        pojo.setAmount(new BigDecimal(formater.format(Double.parseDouble(tempList.get(5)))));
                    }
                    pojo.setTimestamp(ymdhh.format(dmy.parse(tempList.get(1))));
                    pojo.setSenderAcc("");
                    pojo.setSenderIfsc(tempList.get(7));
                    pojo.setSenderName(tempList.get(4));

                    pojo.setSenderAddOne("");
                    pojo.setSenderAddTwo("");
                    pojo.setTxnType("");
                    pojo.setBeneName(tempList.get(8));
                    pojo.setBeneAdd("");

                    pojo.setReceiverBankName("");
                    pojo.setReceiverBankCode(tempList.get(2));
                    pojo.setReceiverBankAddress("");
                    pojo.setSponsorBankName("");
                    pojo.setSponsorBankCode("");

                    pojo.setSponsorIfsc("");
                    pojo.setSponsorRefNo(tempList.get(6));
                    pojo.setSponsorAddress("");
                    pojo.setSenderBankName("");
                    pojo.setSenderBankCode("");

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

    private List<ExcelReaderPojo> parseIblCSV(File csvFile) throws IOException, ApplicationException {
        FileInputStream fis = null;
        DataInputStream dis = null;
        BufferedReader br = null;
        try {
            List<ExcelReaderPojo> pojoList = new ArrayList<>();
            byte[] b = uploadedFile.getBytes();
            FileOutputStream fos = new FileOutputStream(csvFile);
            fos.write(b);
            fos.close();

            fis = new FileInputStream(csvFile);
            dis = new DataInputStream(fis);
            br = new BufferedReader(new InputStreamReader(dis));
            String readLine = "";
            while ((readLine = br.readLine()) != null) {
                String[] readArray = readLine.split(",");
                if (!readArray[0].equalsIgnoreCase("CustomerRefNum")) {
                    ExcelReaderPojo pojo = new ExcelReaderPojo();

                    pojo.setRelatedRefNo(readArray[0].trim());  //Customer Ref Num
                    pojo.setTxnType(readArray[1].trim());  //Product Type
                    pojo.setAmount(new BigDecimal(readArray[2].trim()));  //Amount
                    pojo.setReturnTranRefNo(readArray[3].trim());  //Bank Reference No
                    pojo.setTimestamp(readArray[4].trim());   //Credit Date
                    pojo.setReasonCode(readArray[5].trim());  //Txn Status
                    pojo.setReason(readArray[6].trim());  //Return Reason

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

    private List<ExcelReaderPojo> parseIdbiExcel(File xlsFile, String fileType) throws IOException, ApplicationException {
        try {
            List<ExcelReaderPojo> pojoList = new ArrayList<>();
            byte[] b = uploadedFile.getBytes();
            FileOutputStream fos = new FileOutputStream(xlsFile);
            fos.write(b);
            fos.close();

            POIFSFileSystem fs = new POIFSFileSystem(new BufferedInputStream(new FileInputStream(xlsFile)));
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            Iterator rows = sheet.rowIterator();
            int noOfColumns = 0;

            while (rows.hasNext()) {
                List<String> tempList = new ArrayList<>();
                ExcelReaderPojo pojo = new ExcelReaderPojo();
                HSSFRow row = (HSSFRow) rows.next();
                if (row.getRowNum() == 0) {
                    noOfColumns = row.getLastCellNum();
                    continue;
                }

                for (int i = 0; i < noOfColumns; i++) {
                    HSSFCell cell = (HSSFCell) row.getCell(i, Row.CREATE_NULL_AS_BLANK);
                    if (HSSFCell.CELL_TYPE_NUMERIC == cell.getCellType()) {
                        tempList.add(String.valueOf(cell.getNumericCellValue()));
                    } else if (HSSFCell.CELL_TYPE_STRING == cell.getCellType()) {
                        tempList.add(cell.getStringCellValue());
                    } else if (HSSFCell.CELL_TYPE_BLANK == cell.getCellType()) {
                        tempList.add("");
                    }
                }

                if (!tempList.get(0).equals("")) {
                    if (fileType.equalsIgnoreCase("R")) { //For NEFT
                        pojo.setBatchTime(tempList.get(0).trim());
                        pojo.setUtr(tempList.get(1).trim());
                        pojo.setRelatedRefNo(tempList.get(2).trim());
                        pojo.setSenderIfsc(tempList.get(3).trim());
                        pojo.setSenderAcype(tempList.get(4).trim());
                        pojo.setSenderAcc(tempList.get(5).trim());
                        pojo.setSenderName(tempList.get(6).trim());
                        pojo.setRemittanceOriginator(tempList.get(7).trim());
                        pojo.setIfsccode(tempList.get(8).trim());
                        pojo.setBeneficiaryAcType(tempList.get(9).trim());
                        pojo.setBeneAccount(tempList.get(10).trim());
                        pojo.setBeneName(tempList.get(11).trim());
                        pojo.setBeneAdd(tempList.get(12).trim());
                        pojo.setRemitInfo(tempList.get(13).trim());
                        pojo.setReasonCode(tempList.get(14).trim());
                        pojo.setReason(tempList.get(15).trim());
                        pojo.setAmount(new BigDecimal(tempList.get(16).trim()));
                        pojo.setTimestamp(ymdhh.format(ddMMMyyyy.parse(tempList.get(17).trim())));
                        pojo.setReceiverIfsc(tempList.get(8).trim());
                        pojo.setTxnType("N02");
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
                        pojo.setSenderBankName("");
                        pojo.setSenderBankCode("");
                    } else if (fileType.equalsIgnoreCase("F")) { //For RTGS
                        pojo.setUtr(tempList.get(0).trim());
                        pojo.setTimestamp(ymdhh.format(ddMMMyyyy.parse(tempList.get(1).trim())));
                        pojo.setAmount(new BigDecimal(tempList.get(2).trim()));
                        pojo.setRelatedRefNo(tempList.get(3).trim());
                        pojo.setSenderIfsc(tempList.get(4).trim());
                        pojo.setSenderAcc(tempList.get(5).trim());
                        pojo.setSenderName(tempList.get(6).trim());
                        pojo.setSenderAddOne(tempList.get(7).trim());
                        pojo.setIfsccode(tempList.get(8).trim());
                        pojo.setBeneAccount(tempList.get(9).trim());
                        pojo.setBeneName(tempList.get(10).trim());
                        pojo.setBeneAdd(tempList.get(11).trim());
                        pojo.setReasonCode(tempList.get(12).trim());
                        pojo.setReason(tempList.get(13).trim());
                        pojo.setReceiverIfsc(tempList.get(8).trim());
                        pojo.setTxnType("R41");
                        pojo.setCopBankAccNo("");
                        pojo.setSenderAddTwo("");
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
                        pojo.setBatchTime("");
                        pojo.setSenderAcype("");
                        pojo.setBeneficiaryAcType("");
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

//    private List<ExcelReaderPojo> parseIBLInwardExcel(File xlsFile) throws IOException, ApplicationException {
//        SimpleDateFormat y1 = new SimpleDateFormat("dd/MM/yyyy"); // 05/12/2017
//        SimpleDateFormat y2 = new SimpleDateFormat("dd/MMM/yy"); // 05/Dec/17
//        try {
//            List<ExcelReaderPojo> pojoList = new ArrayList<>();
//            byte[] b = uploadedFile.getBytes();
//            FileOutputStream fos = new FileOutputStream(xlsFile);
//            fos.write(b);
//            fos.close();
//
//            POIFSFileSystem fs = new POIFSFileSystem(new BufferedInputStream(new FileInputStream(xlsFile)));
//            HSSFWorkbook wb = new HSSFWorkbook(fs);
//            HSSFSheet sheet = wb.getSheetAt(0);
//            Iterator rows = sheet.rowIterator();
//            int noOfColumns = 0;
//
//            while (rows.hasNext()) {
//                List<String> tempList = new ArrayList<>();
//                ExcelReaderPojo pojo = new ExcelReaderPojo();
//                HSSFRow row = (HSSFRow) rows.next();
//                if (row.getRowNum() == 0) {
//                    noOfColumns = row.getLastCellNum();
//                    continue;
//                }
//
//                for (int i = 0; i < noOfColumns; i++) {
//                    HSSFCell cell = (HSSFCell) row.getCell(i, Row.CREATE_NULL_AS_BLANK);
//                    if (HSSFCell.CELL_TYPE_NUMERIC == cell.getCellType()) {
//                        tempList.add(String.valueOf(cell.getNumericCellValue()));
//                    } else if (HSSFCell.CELL_TYPE_STRING == cell.getCellType()) {
//                        tempList.add(cell.getStringCellValue());
//                    } else if (HSSFCell.CELL_TYPE_BLANK == cell.getCellType()) {
//                        tempList.add("");
//                    }
//                }
//
//                if (!tempList.get(0).equals("")) {
//                    pojo.setBeneName(tempList.get(0).trim());
//                    pojo.setReceiverBankCode(tempList.get(1).trim());
//                    pojo.setBeneAccount(tempList.get(2).trim());
//                    pojo.setIfsccode(tempList.get(3).trim());
//                    pojo.setTimestamp(ymdhh.format(y2.parse(tempList.get(4).trim())));
//                    pojo.setAmount(new BigDecimal(tempList.get(5).trim()));
//                    pojo.setUtr(tempList.get(6).trim());
//                    pojo.setSenderAcc(tempList.get(7).trim());
//                    pojo.setSenderName(tempList.get(8).trim());
//                    pojo.setSenderBankName(tempList.get(9).trim());
//                    pojo.setSenderBankCode(tempList.get(10).trim());
//                    pojo.setSenderIfsc(tempList.get(11).trim());
//                    pojo.setReceiverIfsc(tempList.get(3).trim());
//                    if (pojo.getAmount().compareTo(new BigDecimal("200000")) <= 0) {
//                        pojo.setTxnType("N");
//                    } else {
//                        pojo.setTxnType("R");
//                    }
//
//                    pojo.setBatchTime("");
//                    pojo.setRelatedRefNo("");
//                    pojo.setSenderAcype("");
//                    pojo.setRemittanceOriginator("");
//                    pojo.setBeneficiaryAcType("");
//                    pojo.setBeneAdd("");
//                    pojo.setRemitInfo("");
//                    pojo.setReasonCode("");
//                    pojo.setReason("");
//                    pojo.setCopBankAccNo("");
//                    pojo.setSenderAddOne("");
//                    pojo.setSenderAddTwo("");
//                    pojo.setReceiverBankName("");
//                    pojo.setReceiverBankCode("");
//                    pojo.setReceiverBankAddress("");
//                    pojo.setSponsorBankName("");
//                    pojo.setSponsorBankCode("");
//                    pojo.setSponsorIfsc("");
//                    pojo.setSponsorRefNo("");
//                    pojo.setSponsorAddress("");
//
//                    pojoList.add(pojo);
//                }
//            }
//            return pojoList;
//        } catch (IOException ex) {
//            System.out.println("** Parsing error" + ex.getMessage());
//            throw new IOException("Invalid File Format.");
//        } catch (Exception ex) {
//            throw new ApplicationException("Invalid File Format.");
//        }
//    }
//    private List<ExcelReaderPojo> parseIdbiXML(File xlsFile, String fileType) throws ApplicationException {
//        try {
//            List<String> replaceStrList = new ArrayList<>();
//            replaceStrList.add("<p>");
//            replaceStrList.add("&nbsp;");
//            replaceAndWriteFile(uploadedFile.getInputStream(), xlsFile, replaceStrList);
//
//            List<ExcelReaderPojo> pojoList = new ArrayList<>();
//            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
//            Document doc = docBuilder.parse(xlsFile);
//
//            // normalize text representation
//            doc.getDocumentElement().normalize();
//            System.out.println("Root element of the doc is " + doc.getDocumentElement().getNodeName());
//            NodeList listOfRow = doc.getElementsByTagName("tr");
//            int totalRow = listOfRow.getLength();
//            System.out.println("Total no of row: " + totalRow);
//
//            for (int s = 1; s < listOfRow.getLength(); s++) {
//                Node firstRowNode = listOfRow.item(s);
//                if (firstRowNode.getNodeType() == Node.ELEMENT_NODE) {
//                    Element firstElement = (Element) firstRowNode;
//
//                    NodeList colList = firstElement.getElementsByTagName("td");
//                    List<String> tempList = new ArrayList<String>();
//                    ExcelReaderPojo pojo = new ExcelReaderPojo();
//
//                    for (int i = 0; i < colList.getLength(); i++) {
//                        Element colElement = (Element) colList.item(i);
//
//                        NodeList textFNList = colElement.getChildNodes();
//                        Node txtNode = (Node) textFNList.item(0);
//                        if (txtNode.getNodeValue() != null) {
//                            //System.out.println("Column Value : " + txtNode.getNodeValue().trim());
//                            tempList.add(txtNode.getNodeValue().trim());
//                        }
//                    }
//                    if (!tempList.get(0).equals("")) {
//                        pojo.setCopBankAccNo(tempList.get(0));
//                        pojo.setIfsccode(tempList.get(1));
//                        pojo.setUtr(tempList.get(2));
//                        pojo.setBeneAccount(tempList.get(3));
//                        pojo.setReceiverIfsc(tempList.get(4));
//                        if (!tempList.get(5).equals("")) {
//                            pojo.setAmount(new BigDecimal(formater.format(Double.parseDouble(tempList.get(5)))));
//                        }
//
//                        pojo.setTimestamp(tempList.get(6));
//                        pojo.setSenderAcc(tempList.get(7));
//                        pojo.setSenderIfsc(tempList.get(8));
//                        String[] tmpArr = tempList.get(9).split("-");
//                        if (tmpArr.length > 1) {
//                            pojo.setSenderName(tmpArr[0]);
//                            pojo.setSenderAddOne(tmpArr[1]);
//                        } else {
//                            pojo.setSenderName(tmpArr[0]);
//                            pojo.setSenderAddOne(tmpArr[0]);
//                        }
//                        pojo.setSenderAddTwo(tempList.get(10) + tempList.get(11));
//                        pojo.setTxnType(tempList.get(12));
//                        pojo.setBeneName(tempList.get(13));
//                        pojo.setBeneAdd(tempList.get(14));
//
//                        pojo.setReceiverBankName("");
//                        pojo.setReceiverBankCode("");
//                        pojo.setReceiverBankAddress("");
//                        pojo.setSponsorBankName("");
//                        pojo.setSponsorBankCode("");
//
//                        pojo.setSponsorIfsc("");
//                        pojo.setSponsorRefNo("");
//                        pojo.setSponsorAddress("");
//                        pojo.setSenderBankName("");
//                        pojo.setSenderBankCode("");
//
//                        //New Addition due to the IDBI
//                        pojo.setBeneAdd("");
//                        pojo.setRemitInfo("");
//                        pojo.setRemittanceOriginator("");
//                        //End here
//
//                        pojoList.add(pojo);
//                    }
//                }
//            }
//            return pojoList;
//        } catch (SAXParseException err) {
//            System.out.println("** Parsing error" + ", line " + err.getLineNumber() + ", uri " + err.getSystemId());
//            System.out.println(" " + err.getMessage());
//            throw new ApplicationException("Invalid File Format.");
//        } catch (SAXException e) {
//            Exception x = e.getException();
//            ((x == null) ? e : x).printStackTrace();
//            throw new ApplicationException("Invalid File Format.");
//        } catch (Throwable t) {
//            t.printStackTrace();
//            throw new ApplicationException("Invalid File Format.");
//        }
//    }
    public void refreshForm() {
        this.setBank("--Select--");
        this.setMessage("Click on browse button to upload the file...");
    }

    public String exitForm() {
        refreshForm();
        return "case1";
    }
}
