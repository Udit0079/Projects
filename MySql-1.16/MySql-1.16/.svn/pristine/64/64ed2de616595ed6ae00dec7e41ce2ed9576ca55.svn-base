/*
 * CREATED BY    :  ROHIT KRISHNA GUPTA
 * CREATION DATE :  25 APRIL 2011
 */
package com.cbs.web.controller.other;

import com.cbs.dto.report.CbsGeneralLedgerBookPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.other.BankProcessManagementFacadeRemote;
import com.cbs.facade.report.GLReportFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.pojo.GlbList;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class HoGlbFlatFile {

    private String orgnBrCode;
    private String userName;
    private String todayDate;
    private String errorMessage;
    private String message;
    private Date calDate;
    private HttpServletRequest req;
    private String reportDate;
    private boolean saveLink;
    private boolean flag1;
    private String fileName;
    private final String jndiHomeName = "BankProcessManagementFacade";
    private String viewID="/pages/master/sm/test.jsp";
    private BankProcessManagementFacadeRemote beanRemote = null;

    public String getViewID() {
        return viewID;
    }

    public void setViewID(String viewID) {
        this.viewID = viewID;
    }

    public Date getCalDate() {
        return calDate;
    }

    public void setCalDate(Date calDate) {
        this.calDate = calDate;
    }

    
   

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOrgnBrCode() {
        return orgnBrCode;
    }

    public void setOrgnBrCode(String orgnBrCode) {
        this.orgnBrCode = orgnBrCode;
    }

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }

    public String getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public boolean isFlag1() {
        return flag1;
    }

    public void setFlag1(boolean flag1) {
        this.flag1 = flag1;
    }

    public boolean isSaveLink() {
        return saveLink;
    }

    public void setSaveLink(boolean saveLink) {
        this.saveLink = saveLink;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    /** Creates a new instance of HoGlbFlatFile */
    public HoGlbFlatFile() {
        try {
            beanRemote = (BankProcessManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            req = getRequest();
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());
            Date date = new Date();
            this.setTodayDate(sdf.format(date));
            this.setCalDate((date));
            this.setSaveLink(false);
            this.setFlag1(false);
            this.setErrorMessage("");
            this.setMessage("");
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void saveToFile() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setSaveLink(false);
            this.setFlag1(false);
            if (this.calDate == null) {
                this.setErrorMessage("Please enter date.");
                return;
            }
            if (this.orgnBrCode == null || this.orgnBrCode.equalsIgnoreCase("")) {
                this.setErrorMessage("Branch code cannot be blank.");
                return;
            }   
            String tmpDt = ymd.format(calDate);
            String result = beanRemote.createFile(tmpDt, this.orgnBrCode, this.userName);
            if (result == null || result.equalsIgnoreCase("") || result.length() == 0) {
                this.setErrorMessage("System error occured !!!");
                return;
            } else {
                if (result.contains("!")) {
                    this.setErrorMessage(result);
                    return;
                } else {
                    this.setMessage(result.substring(0, result.indexOf(":-")));
                    this.setSaveLink(true);
                    String tmp = result.substring(result.indexOf("/") + 1).trim();
                    tmp = tmp.substring(tmp.indexOf("/") + 1).trim();
                    this.setFileName(tmp);
                }
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void openReport() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setSaveLink(false);
        this.setFlag1(false);
        try {
            if (this.calDate == null) {
                this.setErrorMessage("Please enter date.");
                return;
            }
            this.reportDate = ymd.format(calDate);
            String dt= sdf.format(calDate);
            GLReportFacadeRemote facadeRemote=(GLReportFacadeRemote)ServiceLocator.getInstance().lookup("GLReportFacade");
            String report = "GLB Report";
            List<CbsGeneralLedgerBookPojo> glbDataList =facadeRemote.getGeneralLedgerBookData(reportDate, orgnBrCode);
             List<CbsGeneralLedgerBookPojo> assList = new ArrayList<CbsGeneralLedgerBookPojo>();
            List<CbsGeneralLedgerBookPojo> libList = new ArrayList<CbsGeneralLedgerBookPojo>();
            double grandTotalAssets = 0.0;
            double grandTotalLib = 0.0;
            if (!glbDataList.isEmpty()) {
                for (CbsGeneralLedgerBookPojo cbsPojo : glbDataList) {
                    CbsGeneralLedgerBookPojo pojo1 = new CbsGeneralLedgerBookPojo();
                    CbsGeneralLedgerBookPojo pojo2 = new CbsGeneralLedgerBookPojo();
                    if (cbsPojo.getACTYPE().equalsIgnoreCase("A")) {
                        pojo1.setACNAME(cbsPojo.getACNAME());
                        pojo1.setACTYPE(cbsPojo.getACTYPE());
                        pojo1.setAMOUNT(cbsPojo.getAMOUNT());
                        pojo1.setBankaddress(cbsPojo.getBankaddress());
                        pojo1.setBankname(cbsPojo.getBankname());
                        pojo1.setGNO(cbsPojo.getGNO());
                        grandTotalAssets += Double.parseDouble(pojo1.getAMOUNT().toString());
                        assList.add(pojo1);
                    } else {
                        pojo2.setACNAME(cbsPojo.getACNAME());
                        pojo2.setACTYPE(cbsPojo.getACTYPE());
                        pojo2.setAMOUNT(cbsPojo.getAMOUNT());
                        pojo2.setBankaddress(cbsPojo.getBankaddress());
                        pojo2.setBankname(cbsPojo.getBankname());
                        pojo2.setGNO(cbsPojo.getGNO());
                        grandTotalLib += Double.parseDouble(pojo2.getAMOUNT().toString());
                        libList.add(pojo2);
                    }

                }

            }
            //        libList.addAll(pojoList1);
            if (!libList.isEmpty() && (!assList.isEmpty())) {
                Map fillParams = new HashMap();
                fillParams.put("dd-MMM-yyyy", dt);
                fillParams.put("pPrintedDate", dt);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportName", report);
                fillParams.put("pBankName", glbDataList.get(0).getBankname());
                fillParams.put("pBankAdd", glbDataList.get(0).getBankaddress());
                fillParams.put("pLibGrandTotal", grandTotalLib);
                fillParams.put("pAssGrandTotal", grandTotalAssets);
                fillParams.put("SUBREPORT_DIR", ((HttpSession)(FacesContext.getCurrentInstance().getExternalContext().getSession(true))).getServletContext().getRealPath("/WEB-INF/reports")+"/");
                GlbList myList = new GlbList();
                myList.setAssList(new JRBeanCollectionDataSource(assList));
                myList.setLibList(new JRBeanCollectionDataSource(libList));
                List<GlbList> s = new ArrayList<GlbList>();
                s.add(myList);
                new ReportBean().jasperReport("generalLedgerBook", "text/html",
                        new JRBeanCollectionDataSource(s), fillParams, report);
           //     ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                 flag1=true;
                 this.setViewID("/report/ReportPagePopUp.jsp");
            }
           
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void resetForm() {
        try {
            Date date = new Date();
            this.setErrorMessage("");
            this.setMessage("");
            this.setCalDate(date);
            this.setSaveLink(false);
            flag1=false;
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public String exitBtnAction() {
        try {
            resetForm();
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }

    public void downloadFile() {
        HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        String ctxPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        try {
            String dirName = "";
            String osName = System.getProperty("os.name");
            if (osName.equalsIgnoreCase("Linux")) {
                dirName = "/install/HOGLBFlatFile/";
            } else {
                dirName = "C:\\HOGLBFlatFile\\";
            }
            res.sendRedirect(ctxPath + "/downloadFile/?dirName=" + dirName + "&fileName=" + fileName);
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }
}
