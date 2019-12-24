/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.other.PostalDetailFacadeRemote;
import com.cbs.pojo.BulkRecoveryPojo;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.servlet.ServletContext;
import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;

public class BulkRecovery extends BaseBean {

    private String message;
    private UploadedFile uploadedFile;
    private final String jndiName = "PostalDetailFacade";
    private PostalDetailFacadeRemote remote = null;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    FtsPostingMgmtFacadeRemote fts;

    public BulkRecovery() {
        try {
            this.setMessage("Click on browse button to upload the file...");
            remote = (PostalDetailFacadeRemote) ServiceLocator.getInstance().lookup(jndiName);
            fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void uploadProcess() {
        this.setMessage("");
        if (uploadedFile == null) {
            System.out.println("Uploded File is null here");
            this.setMessage("Please select appropriate file to upload !");
            return;
        } else {
            String uploadedFileName = uploadedFile.getName();
            if (uploadedFileName == null || uploadedFileName.equalsIgnoreCase("")) {
                System.out.println("Uploded File Name is null or blank here");
                this.setMessage("Please select appropriate file to upload !");
                return;
            }
            try {
                String fileContentType = uploadedFile.getContentType();
                System.out.println("File content type " + fileContentType);

                ServletContext context = (ServletContext) getFacesContext().getCurrentInstance().getExternalContext().getContext();
//                String directory = context.getInitParameter("cts");
                String directory = context.getInitParameter("cbsFiles");
                File dir = new File(directory + "/");
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File xlsFile = new File(dir.getCanonicalPath() + File.separatorChar + uploadedFileName);

                if (fileContentType.equals("application/xsl") || fileContentType.equals("application/vnd.ms-excel") || fileContentType.equals("application/octet-stream")) {
                    List<BulkRecoveryPojo> dataList = new ArrayList<BulkRecoveryPojo>();
                    try {
                        dataList = parseExcel(xlsFile);
                    } catch (IOException ex) {
                        this.setMessage("Invalid File Format. " + ex.getMessage());
                        return;
                    } catch (ApplicationException ex) {
                        this.setMessage("Invalid File Format. " + ex.getMessage());
                        return;
                    }
                    // Checking 
                    String place = uploadedFileName.replace(".xls", "");
                    String srNo = remote.getsrNoByArea(place);
                    String curDtEnd = CbsUtil.monthLast(ymd.format(dmy.parse(getTodayDate())), 0);
                    String curFirstDt = curDtEnd.substring(0, 4) + curDtEnd.substring(4, 6) + "01";

//                    if (remote.getflag(getOrgnBrCode(), place, curDtEnd).equalsIgnoreCase("R")) {
//                        throw new ApplicationException("This " + place + " -> Already Demand recovered !");
//                    }

                    String isFileRec = remote.isReoover(getOrgnBrCode(), place);
                    if (isFileRec.equalsIgnoreCase("R")) {
                        throw new ApplicationException("This " + place + " -> Already Demand recovered !");
                    }
                    String prvDtEnd = CbsUtil.monthLast(ymd.format(dmy.parse(getTodayDate())), -1);
                    String prvFirstDt = prvDtEnd.substring(0, 4) + prvDtEnd.substring(4, 6) + "01";

//                    if (remote.getflag(remote.getsrNoByArea(place), prvFirstDt, prvDtEnd).equalsIgnoreCase("A")) {
//                        throw new ApplicationException("Previous month recovery is not complete!");
//                    }

                    // Interest Post Checking
                    List list = remote.getFrdtTodtByArea(place);
                    Vector intVector = (Vector) list.get(0);

                    List intPostCheckList = remote.interestPostChecking(getOrgnBrCode(), remote.armyInterestPostParameter(), intVector.get(0).toString(), intVector.get(1).toString());
                    if (!intPostCheckList.isEmpty()) {
                        throw new ApplicationException("First Interest Post, Then Recover !");
                    }
                    // End of Interest Post Checking

                    //Implementing Business Logic
                    String result = "";
                    //For Postal
                    if (fts.getBankCode().equalsIgnoreCase("oefb")) {
                        result = remote.processBulkRecoveryOef(dataList, getOrgnBrCode(), getUserName(), getTodayDate(), place);
                    } else {
                        if (fts.getCodeFromCbsParameterInfo("BNK_IDENTIFIER").equalsIgnoreCase("P")) {
                            result = remote.processBulkRecoveryPostal(dataList, getOrgnBrCode(), getUserName(), getTodayDate(), place);
                        } else {
                            //For Army
                            result = remote.processBulkRecovery(dataList, getOrgnBrCode(), getUserName(), getTodayDate(), place);
                        }
                    }

                    if (result.equals("true")) {
                        this.setMessage("Recovery has been done successfully.");
                    }
                } else {
                    System.out.println("Uploded File has not proper extension.");
                    this.setMessage("Please select appropriate file to upload !");
                    return;
                }
            } catch (Exception ex) {
                this.setMessage(ex.getMessage());
                return;
            }
        }
    }

    private List<BulkRecoveryPojo> parseExcel(File xlsFile) throws IOException, ApplicationException {
        try {
            List<BulkRecoveryPojo> pojoList = new ArrayList<BulkRecoveryPojo>();
            String bnkCode = fts.getBankCode();

            byte[] b = uploadedFile.getBytes();
            FileOutputStream fos = new FileOutputStream(xlsFile);
            fos.write(b);
            fos.close();

            POIFSFileSystem fs = new POIFSFileSystem(new BufferedInputStream(new FileInputStream(xlsFile)));
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            Iterator rows = sheet.rowIterator();
            int c = 0;
            while (rows.hasNext()) {
                List<String> tempList = new ArrayList<String>();
                BulkRecoveryPojo pojo = new BulkRecoveryPojo();
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
                /*
                 System.out.println("CustID is: " + tempList.get(0) + "\n");
                 System.out.println("Acno is: " + tempList.get(1) + "\n");
                 System.out.println("Loan EMI is: " + tempList.get(2) + "\n");
                 System.out.println("Premium is: " + tempList.get(3) + "\n");
                 System.out.println("Well Fare is: " + tempList.get(4) + "\n");
                 System.out.println("Amount is: " + tempList.get(5) + "\n");
                 System.out.println("ChqNo is: " + tempList.get(6) + "\n");
                 */
                if (bnkCode.equalsIgnoreCase("oefb")) {
                    pojo.setWellFare(tempList.get(3));
                    pojo.setAcno(tempList.get(5));
                    pojo.setLoanEmi(tempList.get(7));
                    pojo.setAmount(tempList.get(8));
                } else {
                    pojo.setAcno(tempList.get(2));
                    pojo.setLoanEmi(tempList.get(3));
                    pojo.setInsPremium(tempList.get(4));
                    pojo.setWellFare(tempList.get(5));
                    pojo.setAmount(tempList.get(6));
                    pojo.setChqNo(tempList.get(7));

                    if (tempList.get(8) == null || tempList.get(8).equals("")) {
                        pojo.setChqDt("");
                    } else {
                        if (new Validator().validateDate_dd_mm_yyyy(tempList.get(8))) {
                            pojo.setChqDt(tempList.get(8));
                        } else {
                            pojo.setChqDt("");
                        }
                    }
                }
                pojoList.add(pojo);
            }
            for (int i = 0; i < pojoList.size(); i++) {
                BulkRecoveryPojo obj = pojoList.get(i);
                System.out.println("||Acno: " + obj.getAcno() + "||EMI: " + obj.getLoanEmi() + "||Premium: " + obj.getInsPremium() + "||Well: " + obj.getWellFare() + "||Amount: " + obj.getAmount() + "||ChqNo: " + obj.getChqNo() + "||ChqDt: " + obj.getChqDt());
            }
            return pojoList;
        } catch (IOException ex) {
            System.out.println("** Parsing error" + ex.getMessage());
            throw new IOException("Invalid File Format.");
        } catch (Exception ex) {
            throw new ApplicationException("Invalid File Format. " + ex.getMessage());
        }
    }

    public void refreshForm() {
        this.setMessage("Click on browse button to upload the file...");
    }

    public String exitForm() {
        refreshForm();
        return "case1";
    }

    /**
     * Getter And Setter
     */
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
}
