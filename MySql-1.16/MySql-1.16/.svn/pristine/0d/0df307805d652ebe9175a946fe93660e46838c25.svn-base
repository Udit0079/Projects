/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.clg.CtsManagementFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.other.PrintFacadeRemote;
import com.cbs.pojo.SalaryFilePojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

public class BatchTrfBean extends BaseBean {

    private String message;
    private UploadedFile uploadedFile;
    private PrintFacadeRemote printRemote = null;
    private final String jndiPrintName = "PrintManagementFacade";
    private FtsPostingMgmtFacadeRemote ftsRemote = null;
    private final String jndiFtsName = "FtsPostingMgmtFacade";
    private CtsManagementFacadeRemote ctsRemote = null;
    private final String ctsJndiName = "CtsManagementFacade";
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymmd = new SimpleDateFormat("yyyy-MM-dd");
    NumberFormat formatter = new DecimalFormat("#.##");
    Date curDt = new Date();

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

    public BatchTrfBean() {
        try {
            this.setMessage("Click on browse button to upload the file...");
            printRemote = (PrintFacadeRemote) ServiceLocator.getInstance().lookup(jndiPrintName);
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiFtsName);
            ctsRemote = (CtsManagementFacadeRemote) ServiceLocator.getInstance().lookup(ctsJndiName);
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void uploadProcess() {
        this.setMessage("");
        if (uploadedFile == null) {
            this.setMessage("Please select appropriate file to upload !");
            return;
        } else {
            String uploadedFileName = uploadedFile.getName();
            if (uploadedFileName == null || uploadedFileName.equalsIgnoreCase("")) {
                this.setMessage("Please select appropriate file to upload !");
                return;
            }
            String fileContentType = uploadedFile.getContentType();
            if (fileContentType.equals("application/xsl") || fileContentType.equals("application/vnd.ms-excel")) {
                List<SalaryFilePojo> pojoList = new ArrayList<SalaryFilePojo>();
                try {
                    ServletContext context = (ServletContext) getFacesContext().getCurrentInstance().getExternalContext().getContext();
//                    String directory = context.getInitParameter("cts");
                    String directory = context.getInitParameter("cbsFiles");
                    File dir = new File(directory + "/");
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    File xlsFile = new File(dir.getCanonicalPath() + File.separatorChar + uploadedFileName);
                    try {
                        String processMessage = "";
                        pojoList = readXLSFile(xlsFile);
                        if (!pojoList.isEmpty()) {
                            if (checkBatchAmount(pojoList).equalsIgnoreCase("true")) {
                                Float trsno = ftsRemote.getTrsNo();
                                for (int i = 0; i < pojoList.size(); i++) {
                                    SalaryFilePojo pojo = pojoList.get(i);
                                    Float recno = ftsRemote.getRecNo();
                                    processMessage = printRemote.insertSalaryFromXLS(pojo.getAcno(), pojo.getName(), ymd.format(curDt), ymd.format(curDt), pojo.getCrAmount(), pojo.getDrAmount(), pojo.getTy(), recno, pojo.getInstNo(), pojo.getInstDt(), pojo.getPayBy(), getUserName(), pojo.getDetails(), trsno, getOrgnBrCode(), ftsRemote.getCurrentBrnCode(pojo.getAcno()));
                                }
                                if (processMessage.equalsIgnoreCase("true")) {
                                    this.setMessage("Generated batch no is " + trsno);
                                }
                            } else {
                                this.setMessage("Please check total credit amount is not equal to total debit amount");
                            }
                        }
                    } catch (Exception ex) {
//                        ex.printStackTrace();
                        this.setMessage(ex.getMessage());
                        return;
                    }
                } catch (Exception ex) {
//                    ex.printStackTrace();
                    this.setMessage(ex.getMessage());
                }
            } else {
                this.setMessage("Please select appropriate file to upload !");
                return;
            }
        }
    }

    public List<SalaryFilePojo> readXLSFile(File xlsFile) throws IOException, ApplicationException {
        List<SalaryFilePojo> pojoList = new ArrayList<SalaryFilePojo>();
        String name = "", details = "";
//        double crAmt = 0, drAmt = 0;
        Integer ty = 0;
//        Integer payBy = 0;
        try {
            byte[] b = uploadedFile.getBytes();
            FileOutputStream fos = new FileOutputStream(xlsFile);
            fos.write(b);
            fos.close();

            POIFSFileSystem fs = new POIFSFileSystem(new BufferedInputStream(new FileInputStream(xlsFile)));
            HSSFWorkbook wb = new HSSFWorkbook(fs);

            HSSFSheet sheet = wb.getSheetAt(0);
            Iterator rows = sheet.rowIterator();

            int thLmtParam = 0;
            List chParamThresh = ftsRemote.getThreshLmtParam();
            if (!(chParamThresh == null || chParamThresh.isEmpty())) {
                Vector verLst = (Vector) chParamThresh.get(0);
                thLmtParam = Integer.parseInt(verLst.get(0).toString());
            }
            while (rows.hasNext()) {
                List<String> tempList = new ArrayList<String>();
                SalaryFilePojo pojo = new SalaryFilePojo();
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

                /**
                 * Field Validation**
                 */
                String acno = tempList.get(0);
                String valMessage = validateAccount(acno);
                if (!valMessage.equalsIgnoreCase("true")) {
                    throw new ApplicationException(valMessage);
                }
                /*Amount Validation*/
                String amount = tempList.get(2);
                valMessage = validateAmount(amount);
                if (!valMessage.equalsIgnoreCase("true")) {
                    throw new ApplicationException(valMessage);
                }
                /*Tran Type Validation*/
                String tranType = tempList.get(3);
                valMessage = validateTranType(tranType);
                if (!valMessage.equalsIgnoreCase("true")) {
                    throw new ApplicationException(valMessage);
                }

                if (tranType.equalsIgnoreCase("C")) {
                    ty = 0;
                } else if (tranType.equalsIgnoreCase("D")) {
                    ty = 1;
                }

                valMessage = ftsRemote.ftsAcnoValidate(acno, ty, "");
                if (!valMessage.equalsIgnoreCase("true")) {
                    throw new ApplicationException(valMessage + " Account number is " + acno);
                }

                if (tranType.equalsIgnoreCase("D")) {
                    valMessage = ftsRemote.checkBalance(acno, Double.parseDouble(amount), getUserName());
                    if (!valMessage.equalsIgnoreCase("true")) {
                        throw new ApplicationException(valMessage + " Account number is " + acno);
                    }
                }
                /*Name Validation*/
                if (tempList.get(1).equalsIgnoreCase("B")) {
                    name = ftsRemote.ftsGetCustName(acno);
                } else {
                    name = tempList.get(1);
                }
                if (name.length() > 70) {
                    throw new ApplicationException("Length of account name should not be greater than 100.");
                }
                /**
                 * Instrument Number**
                 */
                String instNo = tempList.get(4);
                valMessage = validateInstNo(acno, instNo, tranType);
                if (!valMessage.equalsIgnoreCase("true")) {
                    throw new ApplicationException(valMessage);
                }
                /**
                 * Instrument Date**
                 */
                String instDt = tempList.get(5);
                valMessage = validateInstDt(instNo, tranType, instDt);
                if (!valMessage.equalsIgnoreCase("true")) {
                    throw new ApplicationException(valMessage);
                }
                /**
                 * Details**
                 */
                if (tempList.get(6).equalsIgnoreCase("B")) {
                    details = "";
                } else {
                    details = tempList.get(6);
                }

                /**
                 * Code Added For ThreshHold Limit Checking *
                 */
                String AcNature = ftsRemote.getAccountNature(acno);
                if ((AcNature.equalsIgnoreCase(CbsConstant.CURRENT_AC) || AcNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) && ty.equals("0")) {
                    if (thLmtParam == 1) {
                        String chkThresh = ftsRemote.isThreshLmtExceed(acno, Double.parseDouble(amount), ymmd.format(dmy.parse(instDt)));
                        if (!chkThresh.equalsIgnoreCase("true")) {
                            throw new ApplicationException(chkThresh);
                        }
                    }
                }

                pojo.setAcno(acno);
                pojo.setName(name);
                if (tranType.equalsIgnoreCase("C")) {
                    pojo.setTy(ty);
                    pojo.setPayBy(3);
                    pojo.setCrAmount(Double.parseDouble(amount));
                    pojo.setDrAmount(0.0);
                } else if (tranType.equalsIgnoreCase("D")) {
                    pojo.setTy(ty);
                    pojo.setCrAmount(0.0);
                    pojo.setDrAmount(Double.parseDouble(amount));
                    if (instNo.equalsIgnoreCase("B")) {
                        pojo.setPayBy(3);
                    } else {
                        pojo.setPayBy(1);
                    }
                }
                if (instNo.equalsIgnoreCase("B")) {
                    pojo.setInstNo("");
                    pojo.setInstDt(ymd.format(new Date()));
                } else {
                    pojo.setInstNo(instNo);
                    pojo.setInstDt(ymd.format(dmy.parse(instDt)));
                }
                pojo.setDetails(details);

                pojoList.add(pojo);
            }
        } catch (IOException ex) {
//            ex.printStackTrace();
            throw new IOException(ex.getMessage());
        } catch (Exception e) {
//            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return pojoList;
    }

    public String validateAccount(String acno) throws ApplicationException {
        String result = "true";
        try {
            if (acno == null || acno.equals("")) {
                return result = "Please check account number is blank.";
            } else if (acno.length() != 12) {
                return result = "Please check length of account no that should be 12 digit. " + acno;
            }
            String checkAcno = ftsRemote.getNewAccountNumber(acno);
            if (checkAcno == null || checkAcno.equals("")) {
                return result = "Account number does not exist. " + acno;
            }
            if (ftsRemote.getAccountNature(acno).equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                if (printRemote.getMsgFlagByAcno(acno).equals("true")) {
                    return result = "This is not a valid account no. " + acno;
                }
            }
        } catch (Exception ex) {
            if (ex.getMessage().equalsIgnoreCase("Account number does not exist")) {
                throw new ApplicationException(ex.getMessage() + ". " + acno);
            } else {
                throw new ApplicationException(ex.getMessage());
            }
        }
        return result;
    }

    public String validateAmount(String amount) throws ApplicationException {
        String result = "true";
        try {
            System.out.println("Amount is-->" + amount);
            if (amount == null || amount.equals("")) {
                return result = "Amount can not be blank.";
            } else if (new BigDecimal(amount).compareTo(new BigDecimal("0")) == 0) {
                return result = "Amount can not be zero.";
            } else if (new BigDecimal(amount).compareTo(new BigDecimal("0")) == -1) {
                return result = "Amount can not be negative.";
            }
            if (amount.contains(".")) {
                if (amount.indexOf(".") != amount.lastIndexOf(".")) {
                    return result = "Invalid amount. Please correct the amount " + amount;
                } else if (amount.substring(amount.indexOf(".") + 1).length() > 2) {
                    return result = "Please fill the amount upto two decimal places. Amount is " + amount;
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return result;
    }

    public String validateTranType(String tranType) throws ApplicationException {
        String result = "true";
        try {
            if (tranType == null || tranType.equals("")) {
                return result = "Tran Type can not be blank.";
            }
            if (!((tranType.equalsIgnoreCase("C")) || (tranType.equalsIgnoreCase("D")))) {
                return result = "Tran Type value can be only C-For Credit or D-For Debit.";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return result;
    }

    public String validateInstNo(String acno, String instNo, String tranType) throws ApplicationException {
        String result = "true";
        try {
            if (tranType.equalsIgnoreCase("D")) {
                if (instNo != null && !instNo.equalsIgnoreCase("") && !instNo.equalsIgnoreCase("B")) {
                    String checkMessage = ctsRemote.chequeValidate(acno, instNo);
                    if (!checkMessage.equalsIgnoreCase("true")) {
                        return result = checkMessage;
                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return result;
    }

    public String validateInstDt(String instNo, String tranType, String instDt) throws ApplicationException {
        String result = "true";
        try {
            if (tranType.equalsIgnoreCase("D")) {
                if (instNo != null && !instNo.equalsIgnoreCase("") && !instNo.equalsIgnoreCase("B")) {
                    if (instDt.equalsIgnoreCase("B")) {
                        return result = "Instrument Date can not be blank for Instrument No " + instNo;
                    }
                    if (instDt.length() != 10) {
                        return result = "Instrument Date length should be of 10 digit. Instrument Date is " + instDt;
                    }
                    boolean dtVal = new Validator().validateDate_dd_mm_yyyy(instDt);
                    if (dtVal == false) {
                        return result = "Please Correct Instrument Date " + instDt;
                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return result;
    }

    public String checkBatchAmount(List<SalaryFilePojo> pojoList) throws ApplicationException {
        String result = "true";
        BigDecimal totalCrAmount = new BigDecimal("0");
        BigDecimal totalDrAmount = new BigDecimal("0");
        try {
            if (!pojoList.isEmpty()) {
                for (int i = 0; i < pojoList.size(); i++) {
                    SalaryFilePojo pojo = pojoList.get(i);
                    Integer drCr = pojo.getTy();
                    if (drCr == 0) {
                        totalCrAmount = totalCrAmount.add(new BigDecimal(formatter.format(pojo.getCrAmount())));
                    } else {
                        totalDrAmount = totalDrAmount.add(new BigDecimal(formatter.format(pojo.getDrAmount())));
                    }
                }
                if (totalCrAmount.compareTo(totalDrAmount) != 0) {
                    throw new ApplicationException("In this batch total Cr amount is not equal to total Dr amount.");
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return result;
    }

    public void refreshForm() {
        this.setMessage("Click on browse button to upload the file...");
    }

    public String exitForm() {
        refreshForm();
        return "case1";
    }
}
