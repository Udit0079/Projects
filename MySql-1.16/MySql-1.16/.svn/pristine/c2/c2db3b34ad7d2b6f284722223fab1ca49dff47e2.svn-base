/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.neftrtgs;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.NeftRtgsOutwardPojo;
import com.cbs.entity.neftrtgs.NeftOwDetails;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.clg.CtsManagementFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.neftrtgs.H2HMgmtFacadeRemote;
import com.cbs.facade.neftrtgs.NeftRtgsMgmtFacadeRemote;
import com.cbs.facade.neftrtgs.UploadNeftRtgsMgmtFacadeRemote;
import com.cbs.facade.txn.TransactionManagementFacadeRemote;
import com.cbs.pojo.neftrtgs.ExcelReaderPojo;
import com.cbs.utils.ParseFileUtil;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 *
 * @author root
 */
public class BulkUploadNeftRtgs extends BaseBean {

    private String message;
    private UploadedFile uploadedFile;
    private UploadNeftRtgsMgmtFacadeRemote remote = null;
    private final String jndiHomeName = "UploadNeftRtgsMgmtFacade";
    private H2HMgmtFacadeRemote h2hRemote;
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    private final String jndiHomeNameTr = "TransactionManagementFacade";
    private TransactionManagementFacadeRemote txnRemote = null;
    private CtsManagementFacadeRemote ctsRemote = null;
    private final String ctsJndiName = "CtsManagementFacade";
    private NeftRtgsMgmtFacadeRemote remoteInterface = null;
    private final String rjndiHomeName = "NeftRtgsMgmtFacade";
    private NumberFormat formater = new DecimalFormat("#.##");
    private SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat ymdhh = new SimpleDateFormat("dd-MMM-yy-hh:mm:ss");
    private SimpleDateFormat ddMMMyyyy = new SimpleDateFormat("dd-MMM-yyyy");
    private SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymdms = new SimpleDateFormat("yyMMddHHmmssSSS");
    SimpleDateFormat dhmsss = new SimpleDateFormat("ddHHmmssSSS");

    public BulkUploadNeftRtgs() {
        try {
            this.setMessage("Click on browse button to upload the file...");
            remote = (UploadNeftRtgsMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
            txnRemote = (TransactionManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameTr);
            ctsRemote = (CtsManagementFacadeRemote) ServiceLocator.getInstance().lookup(ctsJndiName);
            remoteInterface = (NeftRtgsMgmtFacadeRemote) ServiceLocator.getInstance().lookup(rjndiHomeName);

        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }

    }

    public void uploadProcess() {
        this.setMessage("");
        try {
            if (uploadedFile == null) {
                this.setMessage("Please select the file to upload.");
                return;
            }
            String uploadedFileName = uploadedFile.getName();
            if (uploadedFileName.equalsIgnoreCase(null) || uploadedFileName.equalsIgnoreCase("")) {
                this.setMessage("Please select the appropriate file to upload.");
                return;
            }
            String fileContentType = uploadedFile.getContentType();
            System.out.println("File content type " + fileContentType);

            ServletContext context = (ServletContext) getFacesContext().getCurrentInstance().getExternalContext().getContext();
            String directory = context.getInitParameter("cbsFiles");
            File dir = new File(directory + "/");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File xlsFile = new File(dir.getCanonicalPath() + File.separatorChar + uploadedFileName);
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
                    pojoList = parseExcel(xlsFile); //xls file reader

                } catch (Exception e) {
                    this.setMessage("Problem In reading the file." + e.getMessage());
                    return;
                }

                if (!pojoList.isEmpty()) {
                    for (ExcelReaderPojo obj : pojoList) {
                        String debitAcno = obj.getSenderAcc();
                        String beneAcNo = obj.getBeneAccount();
                        String beneIfsc = obj.getReceiverIfsc();
                        String instNo = obj.getInstNo();
                        String instDt = obj.getInstDt();
                        String batchMode = obj.getBatchMode();
                        BigDecimal Amt = obj.getAmount();

                        if (debitAcno == null || debitAcno.equalsIgnoreCase("") || ((debitAcno.length() != 12))) {
                            this.setMessage("Debit ac no is not proper." + debitAcno);
                            return;
                        }
                        if (!debitAcno.substring(0, 2).equalsIgnoreCase(getOrgnBrCode())) {
                            this.setMessage("Debit Ac no should be your branch.for this debit ac no -" + debitAcno);
                            return;
                        }
                        String newAcNo = ftsPostRemote.getNewAccountNumber(debitAcno);

                        List paramList = ftsPostRemote.isModuleActiveBasedOnAcCode(ftsPostRemote.getAccountCode(newAcNo));
                        if (!paramList.isEmpty()) {
                            Vector paramVector = (Vector) paramList.get(0);
                            String modFlag = paramVector.get(1).toString();
                            if (modFlag.equalsIgnoreCase("N")) {
                                this.setMessage("Transaction is not allowed for this type of account." + debitAcno);
                                return;
                            }
                        }
                        String acNature = ftsPostRemote.getAccountNature(newAcNo);

                        if (acNature.equalsIgnoreCase("PO") && !getOrgnBrCode().equalsIgnoreCase(newAcNo.substring(0, 2))) {
                            this.setMessage("You can not generate transaction in other branch gl head for debit Ac no -" + debitAcno);
                            return;
                        }

                        if (!(acNature.equalsIgnoreCase(CbsConstant.SAVING_AC) || acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)
                                || acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER))) {
                            this.setMessage("Transaction is not allowed other than saving,current and gl nature for debit ac." + debitAcno);
                            return;
                        } else {
                            List list = txnRemote.selectForStopBalance(newAcNo, acNature);
                            if (list == null || list.isEmpty()) {
                                this.setMessage("");
                            } else {
                                this.setMessage("Account has stop payment mark for debit ac no." + debitAcno);
                            }
                            if (acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                                list = txnRemote.selectFromGLTable(debitAcno);
                                if (list == null || list.isEmpty()) {
                                    this.setMessage("A/c no does not exist :" + debitAcno);
                                    return;
                                }
                                Vector vec = (Vector) list.get(0);
                                int postflag = Integer.parseInt(vec.get(2).toString());
                                if (postflag == 1) {
                                    this.setMessage("Transaction is not allowed for this A/c." + debitAcno);
                                    return;
                                }
                                int msgflag = Integer.parseInt(vec.get(3).toString());
                                if (msgflag == 4) {
                                    this.setMessage("Transaction is not allowed for PO/DD GL Heads");
                                    return;
                                }
                            }
                        }

                        String resultMessage = ftsPostRemote.ftsAcnoValidate(newAcNo, 1, "");
                        if (!resultMessage.equalsIgnoreCase("true")) {
                            this.setMessage(resultMessage + "-" + debitAcno);
                            return;
                        }

                        if ((beneAcNo == null) || (beneAcNo.equalsIgnoreCase("")) || beneAcNo.length() == 0) {
                            this.setMessage("Invalid Beneficiary A/c for debit Ac -" + debitAcno);
                            return;
                        }

                        if (!ParseFileUtil.isValidIfsc(beneIfsc)) {
                            this.setMessage("Invalid Beneficiary IFSC for debit ac -" + debitAcno + " :ifsc- " + beneIfsc);
                            return;
                        }

                        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
                        if (!(instNo == null || instNo.equals("") || instNo.length() == 0)) {
                            Matcher instNoCheck = p.matcher(instNo);
                            if (!instNoCheck.matches()) {
                                this.setMessage("Invalid Inst.No. Entry. for debit acno" + debitAcno);
                                return;
                            }

                            String checkMessage = ctsRemote.chequeValidate(newAcNo, instNo.trim());
                            if (!checkMessage.equalsIgnoreCase("true")) {
                                this.setMessage(checkMessage + "-" + debitAcno);
                                return;
                            }

                            if (instDt == null || instDt.equals("") || instDt.length() < 10) {
                                this.setMessage("Inst Dt is necessary for debit ac no.-" + debitAcno);
                                return;
                            }

                            boolean resultInstDt = new Validator().validateDate_dd_mm_yyyy(instDt);
                            if (resultInstDt == false) {
                                this.setMessage("Improper Inst. Date for debit ac no -" + debitAcno);
                                return;
                            }

                            String chqDtMsg = ftsPostRemote.ftsInstDateValidate(ymd.format(dmy.parse(instDt)));
                            if (!chqDtMsg.equalsIgnoreCase("true")) {
                                this.setMessage(chqDtMsg);
                                return;
                            }
                        } else {
                            if (!ftsPostRemote.existInParameterInfoReport("NEFT-VCH-ALLOW")) {
                                this.setMessage("Vocher entry is not allowed.");
                                return;
                            }
                        }

                        if (Amt.equals(BigDecimal.ZERO) || (Amt.toBigInteger() == (BigDecimal.ZERO).toBigInteger())) {
                            this.setMessage("Amount should not Zero for debit ac no -" + debitAcno);
                            return;
                        }

                        String chBal = ftsPostRemote.checkBalance(newAcNo, Double.parseDouble(obj.getAmount().toString()), getUserName());
                        if (!chBal.equalsIgnoreCase("true")) {
                            this.setMessage(chBal + "for debit ac no.-" + debitAcno);
                            return;
                        }
                    }
                } else {
                    this.setMessage("This file have no data to upload.");
                    return;
                }

                String result = getAllValueBulkNeftRequest(pojoList);
                if (result.trim().substring(0, 4).equalsIgnoreCase("true")) {
                    this.setMessage("File has been uploaded successfully. Batch No is: " + result.trim().substring(5));
                } else {
                    this.setMessage(result);
                }
            } else {
                System.out.println("Uploded File has not proper extension.");
                this.setMessage("Please select appropriate file to upload !");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }

    }

    private String getAllValueBulkNeftRequest(List<ExcelReaderPojo> list) throws ApplicationException {
        String resultMessage = "";
        try {
            List<NeftRtgsOutwardPojo> resultList = new ArrayList<NeftRtgsOutwardPojo>();
            BigDecimal totalDebitAmount = BigDecimal.ZERO;
            for (ExcelReaderPojo obj : list) {
                String debitAcno = obj.getSenderAcc();
                String beneAcNo = obj.getBeneAccount();
                String beneIfsc = obj.getReceiverIfsc();
                String instNo = obj.getInstNo();
                String instDt = obj.getInstDt();
                String neftBankName = obj.getSponsorBankName();
                String newAcNo = ftsPostRemote.getNewAccountNumber(debitAcno);


                String uCRefNo = "";
                if (obj.getTxnType().equalsIgnoreCase("X")) {
                    String hoAccountNo = ftsPostRemote.getCodeFromCbsParameterInfo("IMPS-OW-HEAD");
                    if (hoAccountNo == null || hoAccountNo.trim().equals("")) {
                        return "Please define IMPS Head.";
                    }
                    uCRefNo = ymdms.format(new Date());
                } else {
//                    autoObj = remoteInterface.findAutoNeftDetailForOutward();
                    if (obj.getSponsorBankName().trim().equalsIgnoreCase("icici")) {
                        uCRefNo = debitAcno.substring(0, 10) + ymdms.format(new Date());
                    } else if (obj.getSponsorBankName().trim().equalsIgnoreCase("axis")) {
                        uCRefNo = dhmsss.format(new Date());
                    } else {
                        uCRefNo = ymdms.format(new Date());
                    }
                }


                NeftRtgsOutwardPojo pojo = new NeftRtgsOutwardPojo();

                pojo.setPaymentType(obj.getTxnType());
                pojo.setUniqueCustomerRefNo(uCRefNo);
                pojo.setBeneficiaryName(obj.getBeneName());
                pojo.setBeneficiaryCode(obj.getBeneficiaryAcType() == null ? "" : obj.getBeneficiaryAcType());
                pojo.setNeftAmount(new BigDecimal(obj.getAmount().toString()));
//                pojo.setPaymentDate(ymd.format(new Date()));
                pojo.setPaymentDate(dmy.format(new Date()));
                pojo.setCreditAccountNo(beneAcNo);
                pojo.setBeneficiaryIfsc(beneIfsc);
                pojo.setDebitAccountNo(debitAcno);
                pojo.setBeneficiaryEmailId("");
                pojo.setBeneficiaryMobileNo("");
                pojo.setCmsBankRefNo("");
                pojo.setUtrNo("");
                pojo.setInstNo(instNo.trim());
                pojo.setInstDate(instDt);
                pojo.setDt(ymd.format(new Date()));
                pojo.setTranTime(new Date());
                pojo.setStatus("P");
                pojo.setReason("");
                pojo.setDetails("Pending");
                pojo.setOrgnId(getOrgnBrCode());
                pojo.setDestbrnId(newAcNo.substring(0, 2));
                pojo.setAuth("N");
                pojo.setEnterBy(getUserName());
                pojo.setAuthBy("");
                pojo.setChargeType("");
                pojo.setBatchMode(obj.getBatchMode());
                pojo.setChargeAmount(new BigDecimal(0.0));
                pojo.setFileName("");
                pojo.setSenderCommModeType("");
                pojo.setSenderCommMode("");
                pojo.setRemmitInfo("");
                pojo.setOutwardFileName("");
                pojo.setCpsmsMessageId("");
                pojo.setCpsmsBatchNo("");
                pojo.setCpsmsTranIdCrTranId("");
                pojo.setDebitSuccessTrsno(0f);
                pojo.setResponseUpdateTime(new Date());
                pojo.setSuccessToFailureFlag("");
//                pojo.setDebitAmount(new BigDecimal(obj.getAmount().toString()));
                totalDebitAmount = totalDebitAmount.add(pojo.getNeftAmount());
                pojo.setNeftBankName(obj.getSenderBankName() == null ? "" : obj.getSponsorBankName());

                resultList.add(pojo);
            }
            String batchType = "";
            Map<String, String> mapBatchMode = new HashMap<String, String>();
            for (NeftRtgsOutwardPojo obj : resultList) {
                mapBatchMode.put(obj.getBatchMode(), obj.getBatchMode());
            }
            Set<Map.Entry<String, String>> setBatch = mapBatchMode.entrySet();
            Iterator<Map.Entry<String, String>> it = setBatch.iterator();
            List<NeftOwDetails> dataList = new ArrayList<>();
            float trsno = 0f;
            while (it.hasNext()) {
                dataList = new ArrayList<>();
                Map.Entry<String, String> entryBatch = it.next();
                batchType = entryBatch.getValue();
                if (!batchType.equalsIgnoreCase("100")) {
                    trsno = ftsPostRemote.getTrsNo();
                }
                for (int m = 0; m < resultList.size(); m++) {
                    if (resultList.get(m).getBatchMode().equalsIgnoreCase(batchType)) {
                        if (batchType.equalsIgnoreCase("100")) {
                            trsno = ftsPostRemote.getTrsNo();
                        }
                        NeftOwDetails neftOwEntity = new NeftOwDetails();

                        neftOwEntity.setPaymentType(resultList.get(m).getPaymentType());
                        neftOwEntity.setUniqueCustomerRefNo(resultList.get(m).getUniqueCustomerRefNo());
                        neftOwEntity.setBeneficiaryName(resultList.get(m).getBeneficiaryName());
                        neftOwEntity.setBeneficiaryCode(resultList.get(m).getBeneficiaryCode());
                        neftOwEntity.setTxnAmt(resultList.get(m).getNeftAmount());
                        neftOwEntity.setPaymentDate(resultList.get(m).getPaymentDate());
                        neftOwEntity.setCreditAccountNo(resultList.get(m).getCreditAccountNo());
                        neftOwEntity.setOutsideBankIfscCode(resultList.get(m).getBeneficiaryIfsc());
                        neftOwEntity.setDebitAccountNo(resultList.get(m).getDebitAccountNo());
                        neftOwEntity.setBeneficiaryEmailId(resultList.get(m).getBeneficiaryEmailId());
                        neftOwEntity.setBeneficiaryMobileNo(resultList.get(m).getBeneficiaryMobileNo());
                        neftOwEntity.setCmsBankRefNo(resultList.get(m).getCmsBankRefNo());
                        neftOwEntity.setUtrNo(resultList.get(m).getUtrNo());
                        neftOwEntity.setInstNo(resultList.get(m).getInstNo());
                        if (resultList.get(m).getInstDate().equalsIgnoreCase("")) {
                            neftOwEntity.setInstDate(dmy.parse(getTodayDate()));
                        } else {
                            neftOwEntity.setInstDate(dmy.parse(resultList.get(m).getInstDate()));
                        }
                        neftOwEntity.setDt(ymd.parse(resultList.get(m).getDt()));
                        neftOwEntity.setTrantime(resultList.get(m).getTranTime());
                        neftOwEntity.setStatus(resultList.get(m).getStatus());
                        neftOwEntity.setReason(resultList.get(m).getReason());
                        neftOwEntity.setDetails(resultList.get(m).getDetails());
                        neftOwEntity.setOrgBrnid(resultList.get(m).getOrgnId());
                        neftOwEntity.setDestBrnid(resultList.get(m).getDestbrnId());
                        neftOwEntity.setAuth(resultList.get(m).getAuth());
                        neftOwEntity.setEnterBy(resultList.get(m).getEnterBy());
                        neftOwEntity.setAuthby(resultList.get(m).getAuthBy());
                        neftOwEntity.setChargeType(resultList.get(m).getChargeType());
                        neftOwEntity.setChargeAmount(resultList.get(m).getChargeAmount());
                        neftOwEntity.setFileName(resultList.get(m).getFileName());
                        neftOwEntity.setSenderCommModeType(resultList.get(m).getSenderCommModeType());
                        neftOwEntity.setSenderCommMode(resultList.get(m).getSenderCommMode());
                        neftOwEntity.setRemmitInfo(resultList.get(m).getRemmitInfo());
                        neftOwEntity.setOutwardFileName(resultList.get(m).getOutwardFileName());
                        neftOwEntity.setCPSMSMessageId(resultList.get(m).getCpsmsMessageId());
                        neftOwEntity.setCPSMSBatchNo(resultList.get(m).getCpsmsBatchNo());
                        neftOwEntity.setCPSMSTranIdCrTranId(resultList.get(m).getCpsmsTranIdCrTranId());
                        neftOwEntity.setDebitSuccessTrsno(resultList.get(m).getDebitSuccessTrsno());
                        neftOwEntity.setResponseUpdateTime(resultList.get(m).getResponseUpdateTime());
                        neftOwEntity.setSuccessToFailureFlag(resultList.get(m).getSuccessToFailureFlag());
//                        neftOwEntity.setDebitAmount(resultList.get(m).getDebitAmount());
                        neftOwEntity.setDebitAmount(totalDebitAmount);
                        neftOwEntity.setTrsNo(trsno);
                        neftOwEntity.setInitiatedBankName(resultList.get(m).getNeftBankName());

                        dataList.add(neftOwEntity);

                    }
                }
                if (!dataList.isEmpty()) {
                    resultMessage = remoteInterface.saveNeftOwDetails(dataList, getOrgnBrCode(), getUserName(), getTodayDate());
                    if (resultMessage.equalsIgnoreCase("true")) {
                        resultMessage = resultMessage + ":" + trsno;
                    }
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
            return ex.getMessage();
        }
//        return "true";
        return resultMessage;
    }

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
                        //System.out.println("Numeric Value-->" + String.valueOf(cell.getNumericCellValue()).replaceAll("[\\W_]", " ").trim());
                    } else if (HSSFCell.CELL_TYPE_STRING == cell.getCellType()) {
                        tempList.add(cell.getStringCellValue());
                        //System.out.println("String Value-->" + cell.getStringCellValue().replaceAll("[\\W_]", " ").trim());
                    } else if (HSSFCell.CELL_TYPE_BLANK == cell.getCellType()) {
                        tempList.add("");
                    }
                }
                if (!tempList.isEmpty()) {
                    if (!tempList.get(0).equalsIgnoreCase("")) {
                        pojo.setTxnType(tempList.get(0).replaceAll("[\\W_]", " ").trim());
                        //pojo.setBeneAccount(tempList.get(1).replaceAll("[\\W_]", " ").trim());
                        String result = isHavingSpecialCharacter(tempList.get(1));
                        if (!result.equalsIgnoreCase("ok")) {
                            throw new ApplicationException(result);
                        }
                        pojo.setBeneAccount(tempList.get(1).trim());

                        pojo.setBeneName(tempList.get(2));
                        pojo.setBeneficiaryAcType(tempList.get(3));

//                        pojo.setReceiverIfsc(tempList.get(4).replaceAll("[\\W_]", " ").trim());
                        result = isHavingSpecialCharacter(tempList.get(4));
                        if (!result.equalsIgnoreCase("ok")) {
                            throw new ApplicationException(result);
                        }
                        pojo.setReceiverIfsc(tempList.get(4).trim());

                        pojo.setAmount(new BigDecimal(tempList.get(5)));
                        pojo.setSenderAcc(tempList.get(6));
                        pojo.setSenderName(tempList.get(7));
                        if (!(tempList.size() == 10)) {
                            pojo.setInstNo(tempList.get(8) == null ? "" : tempList.get(8));
                            pojo.setInstDt(tempList.get(9) == null ? "" : tempList.get(9));
                            pojo.setSponsorBankName(tempList.get(11));
                            pojo.setBatchMode(tempList.get(10));
                        } else {
                            pojo.setInstNo("");
                            pojo.setInstDt("");
                            pojo.setSponsorBankName(tempList.get(9));
                            pojo.setBatchMode(tempList.get(8));
                        }
                        pojo.setUtr("");
                        pojo.setSponsorBankCode("");

                        pojo.setSponsorIfsc("");
                        pojo.setSponsorRefNo("");
                        pojo.setSponsorAddress("");
                        pojo.setSenderBankName("");
                        pojo.setSenderBankCode("");


                        pojoList.add(pojo);
                    }
                }
            }
            return pojoList;
        } catch (IOException e) {
            System.out.println("** Parsing error" + e.getMessage());
            throw new IOException("Invalid File Format." + e.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException("Invalid File Format." + ex.getMessage());
        }
    }

    private String isHavingSpecialCharacter(String s) {
        String result = "Ok";
        if (s == null || s.trim().isEmpty()) {
            return result = "Incorrect data: " + s;
        }

        Pattern p = Pattern.compile("[a-zA-Z0-9]*");
        Matcher m = p.matcher(s);
        if (!m.matches()) {
            return result = "Special character found for: " + s;
        }
        return result;
    }
    
    public void downloadSampleFile() {
        try {
            System.out.println("In Download Sample");
            HttpServletResponse res = (HttpServletResponse) getHttpResponse();
            String ctxPath = getFacesContext().getExternalContext().getRequestContextPath();

            String dirLocation = "/opt/conf/";
            String sampleFile = "BulkNeftSampleFile.xls";

            res.sendRedirect(ctxPath + "/downloadFile/?dirName=" + dirLocation + "&fileName=" + sampleFile);
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void refreshForm() {
        this.setMessage("Click on browse button to upload the file...");
    }

    public String exitForm() {
        refreshForm();
        return "case1";
    }

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
