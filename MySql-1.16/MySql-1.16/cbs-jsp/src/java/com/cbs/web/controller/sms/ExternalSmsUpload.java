/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.sms;

import com.cbs.dto.sms.BulkSmsTo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.sms.SmsManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;

public class ExternalSmsUpload extends BaseBean {

    private UploadedFile uploadedFile;
    private String message;
    private Integer count;
    private String textMessage;
    private FtsPostingMgmtFacadeRemote ftsRemote = null;
    private SmsManagementFacadeRemote smsMgmtFacade = null;
    private Properties props = null;

    public ExternalSmsUpload() {
        try {
            props = new Properties();
            props.load(new FileReader("/opt/conf/sms.properties"));

            this.setMessage("Please select your file...");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            smsMgmtFacade = (SmsManagementFacadeRemote) ServiceLocator.getInstance().lookup("SmsManagementFacade");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void uploadProcess() {
        this.setMessage("");
        if (uploadedFile == null) {
            this.setMessage("Please select appropriate file to upload");
            return;
        }
        try {
            String vendor = props.getProperty("sms.vendor");
            if (this.textMessage.trim() == null || this.textMessage.trim().equals("")) {
                this.setMessage("Please enter some text.");
                return;
            }
            if (this.textMessage.trim().length() > 160) {
                this.setMessage("Message length should be maximun of 160 digit");
                return;
            }
            String uploadedFileName = uploadedFile.getName();    //With extension   
            if (uploadedFileName == null || uploadedFileName.equalsIgnoreCase("")) {
                this.setMessage("Please select appropriate file to upload");
                return;
            }
//            String actualFileName = uploadedFileName.trim().substring(0, uploadedFileName.indexOf(".")); //Without extension
            String fileContentType = uploadedFile.getContentType();
            System.out.println("The Content Tpye of the uploded file is >>>>>>>>>>>>>>>>>>>>>>>>>> " + fileContentType);
            if (fileContentType.equals("application/xls")
                    || fileContentType.equals("application/octet-stream")
                    || fileContentType.equals("application/vnd.ms-excel")) {
                ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                String tempDirectory = context.getInitParameter("cbsFiles");

                File dir = new File(tempDirectory + "/" + "BULK-SMS/");
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File bulkSmsFile = new File(dir.getCanonicalPath() + "/" + uploadedFileName);
                //Writing file into filesystem**
                byte[] b = uploadedFile.getBytes();
                FileOutputStream fos = new FileOutputStream(bulkSmsFile);
                fos.write(b);
                fos.close();
                //Parsing of uploaded file.
                List<BulkSmsTo> bulkSmsList = new ArrayList<>();
                try {
                    bulkSmsList = parseExcel(bulkSmsFile);
                } catch (Exception ex) {
                    this.setMessage("Invalid File Format." + ex.getMessage());
                    return;
                }
                System.out.println("After Conversion List Size-->" + bulkSmsList.size());
                Integer bulkSmsCount = ftsRemote.getCodeForReportName("BULK-SMS-COUNT");
                Integer bulkSmsPerFile = ftsRemote.getCodeForReportName("BULK-SMS-PER-FILE");
                if (bulkSmsList.size() >= bulkSmsCount) {
                    List<List<BulkSmsTo>> fileSmsList = new ArrayList<>();
                    for (int i = 0; i < bulkSmsList.size(); i = i + bulkSmsPerFile) {
                        List<BulkSmsTo> subList;
                        if (i + bulkSmsPerFile < bulkSmsList.size()) {
                            subList = bulkSmsList.subList(i, i + bulkSmsPerFile);
                        } else {
                            subList = bulkSmsList.subList(i, bulkSmsList.size());
                        }
                        fileSmsList.add(subList);
                    }

                    String result = "";
                    if (vendor.equalsIgnoreCase("digialaya")) {
                        List<String> xmlString = new InstantMessaging().createXmlStringFiles(fileSmsList);
                        result = smsMgmtFacade.sendBulkSms(vendor, null, bulkSmsList, xmlString, "External", bulkSmsFile, getUserName(),
                                getOrgnBrCode(), getTodayDate());
                    } else if (vendor.equalsIgnoreCase("gupshup")) {
                        List<File> files = new InstantMessaging().createGupshupXlsFiles(fileSmsList);
                        result = smsMgmtFacade.sendBulkSms(vendor, files, bulkSmsList, null, "External", bulkSmsFile,
                                getUserName(), getOrgnBrCode(), getTodayDate());
                    }
                    refreshForm();
                    this.message = result;
                } else {
                    this.setMessage("There must be sufficient data to upload for bulk sms.");
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void countingAction() {
        count = this.textMessage.length();
    }

    private List<BulkSmsTo> parseExcel(File xlsFile) throws IOException, ApplicationException {
        SimpleDateFormat dhmsss = new SimpleDateFormat("ddHHmmssSSS");
        try {
            List<BulkSmsTo> pojoList = new ArrayList<>();

            POIFSFileSystem fs = new POIFSFileSystem(new BufferedInputStream(new FileInputStream(xlsFile)));
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);

            Iterator rows = sheet.rowIterator();
            while (rows.hasNext()) {
                List<String> tempList = new ArrayList<>();
                BulkSmsTo pojo = new BulkSmsTo();
                HSSFRow row = (HSSFRow) rows.next();
                if (row.getRowNum() == 0) {
                    continue;
                }

                Iterator cells = row.cellIterator();
                while (cells.hasNext()) {
                    HSSFCell cell = (HSSFCell) cells.next();
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    if (HSSFCell.CELL_TYPE_STRING == cell.getCellType()) {
                        tempList.add(cell.getStringCellValue());
                    }
                }
                if (!tempList.get(0).equals("")) {
                    if (!tempList.get(0).trim().substring(0, 1).equalsIgnoreCase("+") || tempList.get(0).trim().length() != 13) {
                        throw new Exception("Please define 13 digit phone no : " + tempList.get(0));
                    }
                    pojo.setAccountNo("");
                    pojo.setPhone(tempList.get(0));
                    pojo.setMessage(this.textMessage.trim());
                    pojo.setMessageType("TRANSACTIONAL");
                    pojo.setUserName(getUserName());
                    pojo.setModuleName("External");
                    pojo.setMasks("Custom");
                    pojo.setUniqueMessageId(dhmsss.format(new Date()) + row.getRowNum());

                    pojoList.add(pojo);
                }
            }
            return pojoList;
        } catch (IOException ex) {
            System.out.println("** Parsing error" + ex.getMessage());
            throw new IOException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public void downloadSampleFile() {
        try {
            System.out.println("In Download Sample");
            HttpServletResponse res = (HttpServletResponse) getHttpResponse();
            String ctxPath = getFacesContext().getExternalContext().getRequestContextPath();

            String dirLocation = "/opt/conf/";
            String sampleFile = "SampleFile.xls";

            res.sendRedirect(ctxPath + "/downloadFile/?dirName=" + dirLocation + "&fileName=" + sampleFile);
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void refreshForm() {
        this.setMessage("Please select your file...");
        this.setTextMessage("");
    }

    public String exitForm() {
        return "case1";
    }

    //Getter And Setter
    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }
}
