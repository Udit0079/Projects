/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.share;

import com.cbs.dto.report.MemberReferenceMappingPojo;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.ho.share.ShareTransferFacadeRemote;
import com.cbs.facade.loan.AdvancesInformationTrackingRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ParseFileUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class MemberRefMapping extends BaseBean {

    private String message;
    private String designation;
    private String referBy;
    private String function;
    private String reportIn;
    private String searchBy;
    private String membershipNo;
    private String newMemShipNo;
    private String membershiprefNo;
    private String panel3Display="none";
    private String panel4Display="none";
    private String gridDisplay="none";
    private String panel5Display="";
    private String memFieldShow="none";
    private String datefieldShow="none";
    List<SelectItem> referByList;
    List<SelectItem> designationOption;
    private List<SelectItem> functionList;
    private List<SelectItem> searchByList;
    private List<SelectItem> reportInList;
    private String name;
    private String folioNo;
    private String orgnBrCode;
    private boolean disablesavebtn = true;
    private boolean disableprintbtn = true;
    private boolean disableprintLabel=true;
    private String folioNumber, folioNoShow, acNoLen;
    private String folioFieldShow = "";
    private List<MemberReferenceMappingPojo> reportList;
    private List<MemberReferenceMappingPojo> gridDetail;
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    AdvancesInformationTrackingRemote aitr;
    ShareTransferFacadeRemote remoteObject;
    FtsPostingMgmtFacadeRemote ftsPostRemote;
    private CommonReportMethodsRemote common;
    String dt ;

    public MemberRefMapping() {
        try {
            remoteObject = (ShareTransferFacadeRemote) ServiceLocator.getInstance().lookup("ShareTransferFacade");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            aitr = (AdvancesInformationTrackingRemote) ServiceLocator.getInstance().lookup("AdvancesInformationTrackingFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            reportList = new ArrayList();

            functionList = new ArrayList<SelectItem>();
            functionList.add(new SelectItem("0", "-Select-"));
            functionList.add(new SelectItem("1", "Add"));
            functionList.add(new SelectItem("2", "Enquiry"));
            functionList.add(new SelectItem("3","Modify"));
            designationOption = new ArrayList<>();
            referByList = new ArrayList<SelectItem>();
            
            reportInList =new ArrayList<SelectItem>();
            reportInList.add(new SelectItem("0","--Select--"));
            reportInList.add(new SelectItem("S","Summary"));
            reportInList.add(new SelectItem("D","Details"));
            
            searchByList = new ArrayList<SelectItem>();
            searchByList.add(new SelectItem("0","--Select--"));
            searchByList.add(new SelectItem("P","Designation"));
            searchByList.add(new SelectItem("M","Member"));
         } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }
    
    public void searchByAction(){
        this.setMessage("");
    try{
        if(searchBy.equalsIgnoreCase("M")){
            this.panel4Display = "";
            this.panel5Display="none";
        }else{
            this.panel4Display="none";
            this.panel5Display="";
        }
   }catch(Exception ex){
        this.setMessage(ex.getMessage());
    }
  }

    public void referedByDetails() {
        refresh();
        try {
            setMessage("");
            if (function.equalsIgnoreCase("1")) {
                this.folioFieldShow = "";
                this.disableprintbtn = true;
                this.disablesavebtn = false;
                this.disableprintLabel=true;
                this.panel3Display = "none";
                this.panel4Display="none";
                this.memFieldShow = "none";
                this.datefieldShow="";
                this.setDt(getTodayDate());
            } else if(function.equalsIgnoreCase("2")) {
                this.folioFieldShow = "none";
                this.disableprintbtn = false;
                this.disablesavebtn = true;
                this.disableprintLabel=false;
                this.panel3Display="";
                this.panel4Display="none";
                this.memFieldShow="none";
                this.datefieldShow="";
                this.setDt(getTodayDate());
            } else if(function.equalsIgnoreCase("3")){
                this.disableprintbtn = true;
                this.disablesavebtn = false;
                this.disableprintLabel=true;
                this.panel3Display = "none";
                this.panel4Display="none";
                this.memFieldShow="";
                this.folioFieldShow="none";
                this.datefieldShow="none";
            }
            List listForDesignation = new ArrayList();
            designationOption = new ArrayList<>();
            designationOption.add(new SelectItem("0", "--Select--"));
            if (function.equalsIgnoreCase("2")) {
                designationOption.add(new SelectItem("A", "ALL"));
            }
            listForDesignation = aitr.refRecCode("416");
            for (int i = 0; i < listForDesignation.size(); i++) {
                Vector element = (Vector) listForDesignation.get(i);
                designationOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
    
    public void onblurMemReferedOperation(){
        try{
            List result = remoteObject.getMembershipRecordIndividually(this.membershiprefNo);
            if(result.isEmpty()){
                this.setMessage("There is no data .");
                return;
            }else{
                Vector vec = (Vector) result.get(0);
                String designation = vec.get(1).toString();
                String referBy = vec.get(2).toString();
                this.setDesignation(designation);
               referByList = new ArrayList<SelectItem>();
               referByList.add(new SelectItem("0", "--Select--"));
               List listOfPersonName = aitr.getDesignationPerson("416",designation);
                for (int j = 0; j < listOfPersonName.size(); j++) {
                Vector ele = (Vector) listOfPersonName.get(j);
//                referByList.add(new SelectItem(ele.get(1).toString()));
                referByList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
            }
               this.setReferBy(referBy);
            }
            
        }catch(Exception ex){
            this.setMessage(ex.getMessage());
        }
        
    }
    
    public void onblurMembershipNOOperation(){
         this.gridDisplay="";
        try{
            if(membershipNo.length()>6){
                this.setMessage("Maximum length of membership no is 6 only.");
                return;
            }
            if(!membershipNo.equalsIgnoreCase("")){
              this.newMemShipNo ="9014"+ ParseFileUtil.addTrailingZeros(membershipNo, 6) +"01";   
            }else{
                this.setMessage("Membership no. can not be blank.");
                return;
            }
           gridDetail = remoteObject.getReferenceDetailMembershipWise(this.newMemShipNo);
       }catch(Exception ex){
            this.setMessage(ex.getMessage());
        }
    }

    public void personNameVisible() {
        try {
            setMessage("");
            referByList = new ArrayList<SelectItem>();
            referByList.add(new SelectItem("0", "--Select--"));
            if (function.equalsIgnoreCase("2")) {
                referByList.add(new SelectItem("A", "All"));
            }
            List listOfPersonName = aitr.getDesignationPerson("416", getDesignation());
            for (int j = 0; j < listOfPersonName.size(); j++) {
                Vector ele = (Vector) listOfPersonName.get(j);
//                referByList.add(new SelectItem(ele.get(1).toString()));
                referByList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
//                referByFolio = ele.get(0).toString();
            }

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void onblurFolioHolderDetails() {
        try {
            if (this.designation == null || this.designation.equals("0") || this.designation.equals("")) {
                this.setMessage("Please select the Refer by Designation/Role");
                return;
            }
            if (this.referBy == null || this.referBy.equalsIgnoreCase("0") || this.referBy.equalsIgnoreCase("")) {
                this.setMessage("Please select the person name designation");
                return;
            }
            onblurChecking();
            folioNo = ftsPostRemote.getNewAccountNumber(folioNoShow);

            this.setMessage("");
            List resultList = new ArrayList();
            resultList = remoteObject.folioDetail(folioNo);
            if (resultList.size() > 0) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    if (ele.get(0) != null) {
                        folioNumber = ele.get(0).toString();
                    }
                    if (ele.get(1) != null) {
                        setName(ele.get(1).toString());
                    }
                }
            } else {
                this.setMessage("Folio No. Does Not Exist");
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public String onblurChecking() {
        try {
            setMessage("");
            if (function.equalsIgnoreCase("0")) {
                this.setMessage("Please select function!");
                return "false";
            }
            if (designation.equalsIgnoreCase("0")) {
                this.setMessage("Please define refering person's designation/role");
                return "false";
            }
            if (this.getReferBy().equalsIgnoreCase("0")) {
                this.setMessage("Please give the designated person name");
                return "false";
            }
            if (folioNo == null || folioNo.equalsIgnoreCase("")) {
                this.setMessage("Please Fill Folio No.");
                return "false";
            }
            if (folioNo.length() < 12) {
                this.setMessage("Please Enter Numeric Value in Folio No.");
                return "false";
            }
            if (!this.folioNoShow.equalsIgnoreCase("") && ((this.folioNoShow.length() != 12) && (this.folioNoShow.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                setMessage("Please fill proper Folio No.");
                return "false";
            }
            return "true";
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return "true";
    }

    public void saveBtnAction() {
        try {
            if (function.equalsIgnoreCase("1")) {
                if (onblurChecking().equalsIgnoreCase("false")) {
                    return;
                }
                String enteredDate = ymd.format(sdf.parse(getTodayDate()));
                String result = remoteObject.saveReferedByDetail(folioNumber, referBy, designation, getUserName(), enteredDate);
                refresh();
                if (result.equalsIgnoreCase("true")) {
                    this.setMessage("Data has been saved successfully");
                } else {
                    this.setMessage(result);
                }
            }else if(function.equalsIgnoreCase("3")){
               String enteredDate = ymd.format(sdf.parse(getTodayDate())); 
               String result = remoteObject.saveReferedByDetail(this.membershiprefNo,this.referBy,this.designation, getUserName(), enteredDate);
                refresh();
                if (result.equalsIgnoreCase("true")) {
                    this.setMessage("Data has been saved successfully");
                } else {
                    this.setMessage(result);
                }
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
    
    
    public void printLabel(){
        try{
        getReferBy();
        String report = "Member Reference Label";
        setMessage("");
        if (function.equalsIgnoreCase("")) {
                this.setMessage("Please select function!");
                return;
            }
            if (designation.equalsIgnoreCase("0") || designation.equalsIgnoreCase(null) || designation.equalsIgnoreCase("")) {
                this.setMessage("Please define refering person's designation/role");
                return;
            }
            if (referBy.equalsIgnoreCase("0") || referBy.equalsIgnoreCase(null) || referBy.equalsIgnoreCase("")) {
                this.setMessage("Please give the designated person name");
                return;
            }
           reportList = remoteObject.getReferenceDetails(designation, this.referBy,ymd.format(sdf.parse(dt)));
            Map fillParams = new HashMap(); 
            new ReportBean().openPdf(report, "MemberReferenceLabel", new JRBeanCollectionDataSource(reportList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);
        }catch(Exception ex){
            this.setMessage(ex.getMessage());
        }
        }

    public void printReportAction() {
        try {
            getReferBy();
            String report = "Member Reference Mapping Report";
            setMessage("");
            if (function.equalsIgnoreCase("")) {
                this.setMessage("Please select function!");
                return;
            }
            if (designation.equalsIgnoreCase("0") || designation.equalsIgnoreCase(null) || designation.equalsIgnoreCase("")) {
                this.setMessage("Please define refering person's designation/role");
                return;
            }
            if (referBy.equalsIgnoreCase("0") || referBy.equalsIgnoreCase(null) || referBy.equalsIgnoreCase("")) {
                this.setMessage("Please give the designated person name");
                return;
            }

            reportList = remoteObject.getReferenceDetails(designation, this.referBy,ymd.format(sdf.parse(dt)));
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pDate", getTodayDate());
           


            new ReportBean().jasperReport("Member_reference_mapping_report", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, report);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void printPDF() {
        try {
            getReferBy();
            String report = "Member Reference Mapping Report";
            setMessage("");
            if (function.equalsIgnoreCase("")) {
                this.setMessage("Please select function!");
                return;
            }
            if (designation.equalsIgnoreCase("0") || designation.equalsIgnoreCase(null) || designation.equalsIgnoreCase("")) {
                this.setMessage("Please define refering person's designation/role");
                return;
            }
            if (referBy.equalsIgnoreCase("0") || referBy.equalsIgnoreCase(null) || referBy.equalsIgnoreCase("")) {
                this.setMessage("Please give the designated person name");
                return;
            }

            reportList = remoteObject.getReferenceDetails(designation, this.referBy,ymd.format(sdf.parse(dt)));
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pDate", getTodayDate());
            fillParams.put("pPrintIn",this.reportIn);
//            new ReportBean().downloadPdf(report, "Member_reference_mapping_report", new JRBeanCollectionDataSource(reportList), fillParams);          
//            new ReportBean().jasperReport("Member_reference_mapping_report", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, report);
//            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            new ReportBean().openPdf(report, "Member_reference_mapping_report", new JRBeanCollectionDataSource(reportList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void refresh() {
        this.setDesignation("0");
        this.designationOption = null;
        this.setReferBy("0");
        this.referByList = null;
        this.setMessage("");
        setName("");
        setFolioNo("");
        setFolioNoShow("");
        this.folioFieldShow = "";
        this.setDisableprintbtn(true);
        this.setDisablesavebtn(true);
        this.panel3Display="none";
        this.panel4Display="none";
        this.panel5Display="";
        this.gridDisplay="none";
        gridDetail = new ArrayList<>();
        this.setReferBy("0");
        this.setSearchBy("0");
        this.setMembershipNo("");
        this.memFieldShow="none";
        this.setMembershiprefNo("");
    }

    public String exitForm() {
        return "case1";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public List<SelectItem> getDesignationOption() {
        return designationOption;
    }

    public void setDesignationOption(List<SelectItem> designationOption) {
        this.designationOption = designationOption;
    }

    public List<MemberReferenceMappingPojo> getReportList() {
        return reportList;
    }

    public void setReportList(List<MemberReferenceMappingPojo> reportList) {
        this.reportList = reportList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFolioNo() {
        return folioNo;
    }

    public void setFolioNo(String folioNo) {
        this.folioNo = folioNo;
    }

    public String getFolioNumber() {
        return folioNumber;
    }

    public void setFolioNumber(String folioNumber) {
        this.folioNumber = folioNumber;
    }

    public String getFolioNoShow() {
        return folioNoShow;
    }

    public void setFolioNoShow(String folioNoShow) {
        this.folioNoShow = folioNoShow;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

    public String getReferBy() {
        return referBy;
    }

    public void setReferBy(String referBy) {
        this.referBy = referBy;
    }

    public List<SelectItem> getReferByList() {
        return referByList;
    }

    public void setReferByList(List<SelectItem> referByList) {
        this.referByList = referByList;
    }

    public List<SelectItem> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<SelectItem> functionList) {
        this.functionList = functionList;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public boolean isDisablesavebtn() {
        return disablesavebtn;
    }

    public void setDisablesavebtn(boolean disablesavebtn) {
        this.disablesavebtn = disablesavebtn;
    }

    public boolean isDisableprintbtn() {
        return disableprintbtn;
    }

    public void setDisableprintbtn(boolean disableprintbtn) {
        this.disableprintbtn = disableprintbtn;
    }

    public String getFolioFieldShow() {
        return folioFieldShow;
    }

    public void setFolioFieldShow(String folioFieldShow) {
        this.folioFieldShow = folioFieldShow;
    }

    public String getReportIn() {
        return reportIn;
    }

    public void setReportIn(String reportIn) {
        this.reportIn = reportIn;
    }

    public String getSearchBy() {
        return searchBy;
    }

    public void setSearchBy(String searchBy) {
        this.searchBy = searchBy;
    }

    public List<SelectItem> getSearchByList() {
        return searchByList;
    }

    public void setSearchByList(List<SelectItem> searchByList) {
        this.searchByList = searchByList;
    }

    public List<SelectItem> getReportInList() {
        return reportInList;
    }

    public void setReportInList(List<SelectItem> reportInList) {
        this.reportInList = reportInList;
    }

    public String getPanel3Display() {
        return panel3Display;
    }

    public void setPanel3Display(String panel3Display) {
        this.panel3Display = panel3Display;
    }

    public String getMembershipNo() {
        return membershipNo;
    }

    public void setMembershipNo(String membershipNo) {
        this.membershipNo = membershipNo;
    }

    public String getPanel4Display() {
        return panel4Display;
    }

    public void setPanel4Display(String panel4Display) {
        this.panel4Display = panel4Display;
    }

    public String getPanel5Display() {
        return panel5Display;
    }

    public void setPanel5Display(String panel5Display) {
        this.panel5Display = panel5Display;
    }

    public boolean isDisableprintLabel() {
        return disableprintLabel;
    }

    public void setDisableprintLabel(boolean disableprintLabel) {
        this.disableprintLabel = disableprintLabel;
    }

    public String getNewMemShipNo() {
        return newMemShipNo;
    }

    public void setNewMemShipNo(String newMemShipNo) {
        this.newMemShipNo = newMemShipNo;
    }

    public List<MemberReferenceMappingPojo> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<MemberReferenceMappingPojo> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getGridDisplay() {
        return gridDisplay;
    }

    public void setGridDisplay(String gridDisplay) {
        this.gridDisplay = gridDisplay;
    }

    public String getMembershiprefNo() {
        return membershiprefNo;
    }

    public void setMembershiprefNo(String membershiprefNo) {
        this.membershiprefNo = membershiprefNo;
    }

    public String getMemFieldShow() {
        return memFieldShow;
    }

    public void setMemFieldShow(String memFieldShow) {
        this.memFieldShow = memFieldShow;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getDatefieldShow() {
        return datefieldShow;
    }

    public void setDatefieldShow(String datefieldShow) {
        this.datefieldShow = datefieldShow;
    }
    
    
    
    
    
  
}
