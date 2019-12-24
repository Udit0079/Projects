/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.neftrtgs;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.neftrtgs.NeftRtgsMgmtFacadeRemote;
import com.cbs.facade.other.PrintFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.pojo.neftrtgs.NeftDataReconsilationPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author root
 */
public class NeftDataReconsilation extends BaseBean {

    private String ReconsilitaionDate;
    private String function;
    private String reconDate;
    private String enableUpload = "none";
    private String enablepdf = "none";
    private UploadedFile uploadedFile;
    private String errorMsg;
    private String msg;
    private String focusId = "";
    Date dt = new Date();
    private final String rjndiHomeName = "NeftRtgsMgmtFacade";
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymmd = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmyhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private List<SelectItem> functionList;
    private CommonReportMethodsRemote common;
    private PrintFacadeRemote beanRemote = null;
    private NeftRtgsMgmtFacadeRemote remoteInterface = null;

    public NeftDataReconsilation() {
        try {
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            beanRemote = (PrintFacadeRemote) ServiceLocator.getInstance().lookup("PrintManagementFacade");
            remoteInterface = (NeftRtgsMgmtFacadeRemote) ServiceLocator.getInstance().lookup(rjndiHomeName);
            reconDate = dmy.format(dt);
            functionList = new ArrayList<>();
            functionList.add(new SelectItem("S", "--Select--"));
            functionList.add(new SelectItem("U", "Upload File"));
            functionList.add(new SelectItem("R", "Reconsilation Report"));
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void validateDt() {
        this.setErrorMsg("");
        try {
            if (this.reconDate == null || this.reconDate.equals("") || this.reconDate.length() < 10) {
                this.setErrorMsg("Please fill proper Date.");
                return;
            }
            boolean result = new Validator().validateDate_dd_mm_yyyy(this.reconDate);
            if (result == false) {
                this.setErrorMsg("Please fill proper Date.");
                return;
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void optionsMode() {
        setErrorMsg("");
        this.setEnableUpload("none");
        this.setEnablepdf("none");
        this.setReconDate(getTodayDate());
        if (function == null || function.equalsIgnoreCase("S")) {
            this.setErrorMsg("Please select the function ");
            return;
        }
        if (function.equalsIgnoreCase("U")) {
            enableUpload = "";
            enablepdf = "none";
            setFocusId("file");
        }
        if (function.equalsIgnoreCase("R")) {
            enableUpload = "none";
            enablepdf = "";
            setFocusId("txtFrmDate");
        }
    }

    public void uploadProcess() {
        this.setErrorMsg("");
        String brnName = "";
        String brnAddress = "";
        this.setErrorMsg("");
        if (function.equalsIgnoreCase("U")) {
            if (uploadedFile == null) {
                System.out.println("Uploded File is null here");
                this.setErrorMsg("Please select appropriate file to upload !");

            } else {
                String uploadedFileName = uploadedFile.getName();
                if (uploadedFileName == null || uploadedFileName.equalsIgnoreCase("")) {
                    System.out.println("Uploded File Name is null or blank here");
                    this.setErrorMsg("Please select appropriate file to upload !");
                    return;
                }
                try {
                    String fileContentType = uploadedFile.getContentType();
                    System.out.println("File content type " + fileContentType);

                    ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
//                String directory = context.getInitParameter("cts");
                    String directory = context.getInitParameter("cbsFiles");
                    File dir = new File(directory + "/");
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    File xlsFile = new File(dir.getCanonicalPath() + File.separatorChar + uploadedFileName);

                    if (fileContentType.equals("application/xsl") || fileContentType.equals("application/vnd.ms-excel") || fileContentType.equals("application/octet-stream") || fileContentType.equals("application/xslx")) {
                        List<NeftDataReconsilationPojo> dataList = new ArrayList<>();
                        try {
                            dataList = parseExcel(xlsFile);
                            this.setErrorMsg("done");
                        } catch (IOException | ApplicationException ex) {
                            this.setErrorMsg("Invalid File Format. " + ex.getMessage());
                            return;
                        }
                        String result = remoteInterface.insertNeftDataReconsilation(dataList);
                        if (result.equals("true")) {
                            this.setErrorMsg("Data has been uploaded successfully.");
                        }
                    } else {
                        System.out.println("Uploded File has not proper extension.");
                        this.setErrorMsg("Please select appropriate file to upload !");
                        return;
                    }
                } catch (Exception ex) {
                    this.setErrorMsg(ex.getMessage());
                    return;
                }
            }
        } else if (function.equalsIgnoreCase("R")) {
            try {
                if (reconDate == null || reconDate.equalsIgnoreCase("")) {
                    setErrorMsg("Please fill reconsilation date");
                }
                String report = "Neft Data Reconsilation";
                String pdate = getTodayDate();
                List brnnamead = new ArrayList();
                brnnamead = common.getBranchNameandAddress(this.getOrgnBrCode());
                if (brnnamead.size() > 0) {
                    brnName = (String) brnnamead.get(0);
                    brnAddress = (String) brnnamead.get(1);
                }
                Map fillParams = new HashMap();
                fillParams.put("pBnkName", brnName);
                fillParams.put("pBnkAddr", brnAddress);
                fillParams.put("pRepName", report);
                fillParams.put("pRepDate", this.reconDate);
                fillParams.put("pPrintDate", pdate);
                fillParams.put("pPrintedBy", getUserName());
                List reportList = remoteInterface.getNeftDataReconsilation(reconDate);
                if (reportList.isEmpty()) {
                    setErrorMsg("Data does not exits.");
                    return;
                }
                new ReportBean().jasperReport("Neft_Data_Reconsilation", "text/html",
                        new JRBeanCollectionDataSource(reportList), fillParams, "Neft_Data_Reconsilation");

                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                getHttpSession().setAttribute("ReportName", report);
            } catch (Exception e) {
                this.setErrorMsg(e.getMessage());
            }
        }
    }

    private List<NeftDataReconsilationPojo> parseExcel(File xlsFile) throws IOException, ApplicationException {
        try {
            List<NeftDataReconsilationPojo> pojoList = new ArrayList<>();
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
                NeftDataReconsilationPojo pojo = new NeftDataReconsilationPojo();
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
                pojo.setTranDate(dmyhms.format(ymmd.parse(tempList.get(0).toString())));
                pojo.setValueDate(dmyhms.format(ymmd.parse(tempList.get(1).toString())));
                pojo.setChqNo(tempList.get(2));
                pojo.setTxnDetail(tempList.get(3));
                pojo.setDrCr(tempList.get(5));
                pojo.setAmount(BigDecimal.valueOf(new Double(tempList.get(4))));
                pojo.setBalance(BigDecimal.valueOf(new Double(tempList.get(6))));
                pojo.setBranch(tempList.get(7));
                pojoList.add(pojo);
            }
            return pojoList;
        } catch (IOException ex) {
            this.setErrorMsg("** Parsing error" + ex.getMessage());
            throw new IOException("Invalid File Format.");
        } catch (Exception ex) {
            throw new ApplicationException("Invalid File Format. " + ex.getMessage());
        }
    }

    public void btnPdfAction() {
        String brnName = "";
        String brnAddress = "";
        try {
            if (function == null || function.equalsIgnoreCase("")) {
                setErrorMsg("Please select function !!");
            }
            if (reconDate == null || reconDate.equalsIgnoreCase("")) {
                setErrorMsg("Please fill reconsilation date");
            }
            String report = "Neft Data Reconsilation";
            List brnnamead = new ArrayList();
            brnnamead = common.getBranchNameandAddress(this.getOrgnBrCode());
            if (brnnamead.size() > 0) {
                brnName = (String) brnnamead.get(0);
                brnAddress = (String) brnnamead.get(1);
            }
            String pdate = getTodayDate();
            Map fillParams = new HashMap();
            fillParams.put("pBnkName", brnName);
            fillParams.put("pBnkAddr", brnAddress);
            fillParams.put("pRepName", report);
            fillParams.put("pPrintDate", pdate);
            fillParams.put("pRepDate", this.reconDate);
            fillParams.put("pPrintedBy", getUserName());
            List reportList = remoteInterface.getNeftDataReconsilation(reconDate);
            if (reportList.isEmpty()) {
                setErrorMsg("Data does not exits.");
                return;
            }
            new ReportBean().openPdf("Neft_Data_Reconsilation", "Neft_Data_Reconsilation", new JRBeanCollectionDataSource(reportList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            getHttpSession().setAttribute("ReportName", report);
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void refreshForm() {
        setErrorMsg("");
        setFunction("S");
        setReconsilitaionDate(getTodayDate());
        setUploadedFile(null);
        this.enablepdf = "none";
        this.enableUpload = "none";
    }

    public String exitForm() {
        return "case1";
    }

    public String getFocusId() {
        return focusId;
    }

    public void setFocusId(String focusId) {
        this.focusId = focusId;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public List<SelectItem> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<SelectItem> functionList) {
        this.functionList = functionList;
    }

    public String getReconsilitaionDate() {
        return ReconsilitaionDate;
    }

    public void setReconsilitaionDate(String ReconsilitaionDate) {
        this.ReconsilitaionDate = ReconsilitaionDate;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getReconDate() {
        return reconDate;
    }

    public void setReconDate(String reconDate) {
        this.reconDate = reconDate;
    }

    public String getEnableUpload() {
        return enableUpload;
    }

    public void setEnableUpload(String enableUpload) {
        this.enableUpload = enableUpload;
    }

    public String getEnablepdf() {
        return enablepdf;
    }

    public void setEnablepdf(String enablepdf) {
        this.enablepdf = enablepdf;
    }
}
