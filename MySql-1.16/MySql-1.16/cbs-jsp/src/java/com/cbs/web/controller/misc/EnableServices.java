/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.misc;

import com.cbs.dto.CustomerDetail;
import com.cbs.dto.report.AccontDetailList;
import com.cbs.email.service.MailCustomerInfo;
import com.cbs.facade.email.EmailMgmtFacadeRemote;
import com.cbs.facade.misc.MiscMgmtFacadeS1Remote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;

public class EnableServices extends BaseBean {

    private String msg;
    private List<SelectItem> functionList;
    private String function;
    private List<SelectItem> serviceTypeList;
    private String serviceType;
    private String customerId;
    private List<SelectItem> frequencyList;
    private String frequency;
    private String startDate;
    private List<SelectItem> startIndctList;
    private String startIndct;
    private List<AccontDetailList> gridDetail;
    private List<AccontDetailList> verifyDetail;
    private MailCustomerInfo currentItem;
    private AccontDetailList verifyCurrentItem;
    private boolean checkAllBox;
    private boolean btndisbl;
    private String confirmText;
    private String email;
    private String btnLbl = "SAVE";
    private boolean verifyPanelViewFlag = false;
    private String hideServiceType = "";
    private String hideCustID = "";
    private String hidePanel2 = "";
    private String hidepanel3 = "";
    private String hideTablePanel = "";
    private String hideTablePanel1 = "none";
    private CommonReportMethodsRemote reportRemote = null;
    private EmailMgmtFacadeRemote emailMgmRemote = null;
    private MiscMgmtFacadeS1Remote miscRemoteS1;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    //verify panel field
    private String acno1;
    private String name1;
    private String serviceType1;
    private String custid1;
    private String email1;
    private String frequency1;
    private String date1;
    private String startFlag1;

    public EnableServices() {
        try {
            reportRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            emailMgmRemote = (EmailMgmtFacadeRemote) ServiceLocator.getInstance().lookup("EmailMgmtFacade");
            miscRemoteS1 = (MiscMgmtFacadeS1Remote) ServiceLocator.getInstance().lookup("MiscMgmtFacadeS1");

            functionList = new ArrayList<SelectItem>();
            functionList.add(new SelectItem("0", "--Select--"));
            functionList.add(new SelectItem("ADD", "ADD"));
            functionList.add(new SelectItem("MODIFY", "MODIFY"));
            functionList.add(new SelectItem("VERIFY", "VERIFY"));

            frequencyList = new ArrayList<SelectItem>();
            frequencyList.add(new SelectItem("0", "--Select--"));
            List list = reportRemote.getRefRecList("370");
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                frequencyList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
            }
            serviceTypeList = new ArrayList<SelectItem>();
            serviceTypeList.add(new SelectItem("0", "--Select--"));
            list = reportRemote.getRefRecList("371");
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                serviceTypeList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
            }
            startIndctList = new ArrayList<SelectItem>();
            startIndctList.add(new SelectItem("0", "--Select--"));
            list = reportRemote.getRefRecList("372");
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                startIndctList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
            }
            this.btnRefreshAction();
            this.btndisbl = false;
        } catch (Exception ex) {
            setMsg(ex.getMessage());
        }
    }

    public void functionAction() {
        try {
            this.msg = "";
            this.serviceType = "";
            this.customerId = "";
            this.frequency = "";
            this.startDate = "";
            this.startIndct = "";
            this.currentItem = null;
            this.gridDetail = null;
            this.checkAllBox = false;
            this.confirmText = "";
            this.email = "";

            if (this.function.equalsIgnoreCase("ADD")) {
                this.btnLbl = "SAVE";
                this.hideServiceType = "";
                this.hideCustID = "";
                this.hidePanel2 = "";
                this.hidepanel3 = "";
                this.hideTablePanel = "";
                this.hideTablePanel1 = "none";
                this.btndisbl = false;
            } else if (this.function.equalsIgnoreCase("MODIFY")) {
                this.btnLbl = "UPDATE";
                this.hideServiceType = "";
                this.hideCustID = "";
                this.hidePanel2 = "";
                this.hidepanel3 = "";
                this.hideTablePanel = "";
                this.hideTablePanel1 = "none";
                this.btndisbl = false;
            } else if (this.function.equalsIgnoreCase("VERIFY")) {
                this.hideServiceType = "none";
                this.hideCustID = "none";
                this.hidePanel2 = "none";
                this.hidepanel3 = "none";
                this.hideTablePanel = "none";
                this.hideTablePanel1 = "";
                this.verifyPanelViewFlag = true;
                this.btndisbl = true;

                verifyDetail = new ArrayList<>();
                List resultList = emailMgmRemote.gridDetailForEnableService(ymd.format(dmy.parse(getTodayDate())));
                if (!resultList.isEmpty()) {
                    for (int i = 0; i < resultList.size(); i++) {
                        AccontDetailList mailInfo = new AccontDetailList();
                        Vector ele = (Vector) resultList.get(i);
                        mailInfo.setAcno(ele.get(0).toString());
                        mailInfo.setCustId(ele.get(1).toString());
                        CustomerDetail customerObj = miscRemoteS1.getCustomerDetailByAccountNo(mailInfo.getAcno());
                        mailInfo.setCustName(customerObj.getCustomerName());

                        verifyDetail.add(mailInfo);
                    }
                    this.setMsg("Please select an entry from table to verify.");
                }
            }
        } catch (Exception ex) {
            setMsg(ex.getMessage());
        }
    }

    public void getviewDetails() {
        this.setVerifyPanelViewFlag(true);
//        verifyDetail = new ArrayList<AccontDetailList>();
        try {
            if (function.equalsIgnoreCase("VERIFY")) {
                List resultList = emailMgmRemote.gridDetailForVerifyEnableService(this.function, verifyCurrentItem.getAcno(), verifyCurrentItem.getCustId());
                if (!resultList.isEmpty()) {
                    Vector ele = (Vector) resultList.get(0);
                    List Servicename = reportRemote.getfrequencydescription("371", ele.get(0).toString());
                    this.setServiceType1(Servicename.get(0).toString().trim());
                    this.setAcno1(ele.get(1).toString());
                    this.setCustid1(ele.get(2).toString());
                    this.setEmail1(ele.get(3).toString());
                    List freDescription = reportRemote.getfrequencydescription("370", ele.get(4).toString());
                    this.setFrequency1(freDescription.get(0).toString().trim());
                    this.setDate1(ele.get(5).toString());
                    List startFlag = reportRemote.getfrequencydescription("372", ele.get(6).toString());
                    this.setStartFlag1(startFlag.get(0).toString().trim());
                    this.setName1(verifyCurrentItem.getCustName());
                }
            }
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    public void verification() {
        //verifyDetail = new ArrayList<>();
        try {
            if (verifyDetail.isEmpty() || verifyCurrentItem == null) {
                this.setMsg("This entry has been verified successfully.");
                return;
            }
            //if (this.function.equalsIgnoreCase("VERIFY")) {
            String verify = emailMgmRemote.verifyEnableServicesDetail(verifyCurrentItem, getUserName(), getTodayDate());
            if (!verify.equalsIgnoreCase("true")) {
                this.setMsg(verify);
                return;
            }
            verifyDetail.remove(verifyCurrentItem);
            this.setMsg("This entry has been verified successfully.");
            //}
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    public void onblurCustId() {
        try {
            if ((this.function == null || this.function.equals("0"))) {
                setMsg("Please select funnction.");
                return;
            }
            if ((this.serviceType == null || this.serviceType.equals("0"))) {
                setMsg("Please select service.");
                return;
            }
            if ((this.customerId == null || this.customerId.equals(""))) {
                setMsg("Please fill customer id.");
                return;
            }
            if (this.customerId.length() > 10) {
                setMsg("Please fill customer id less than 10 digit.");
                return;
            }
            List IsCustIdExist = reportRemote.checkCustId(this.customerId);
            if (IsCustIdExist.isEmpty()) {
                setMsg("This customer id does not exist.");
                return;
            }

            this.msg = "";
            this.gridDetail = null;

            MailCustomerInfo data = emailMgmRemote.getAccForEnableServices(function, serviceType, customerId);
            if (this.function.equalsIgnoreCase("ADD")) {
                gridDetail = data.getAccList();
            } else if (this.function.equalsIgnoreCase("MODIFY")) {
                gridDetail = data.getAccList();
                this.frequency = data.getFrequency();
                this.startDate = data.getStartDate();
                this.startIndct = data.getStartFlag();
                this.email = data.getEmail();
            }
        } catch (Exception ex) {
            setMsg(ex.getMessage());
        }
    }

    public void checkAll() {
        if (this.checkAllBox == true) {
            for (int i = 0; i < gridDetail.size(); i++) {
                gridDetail.get(i).setCheckBox(true);
            }
        } else if (this.checkAllBox == false) {
            for (int i = 0; i < gridDetail.size(); i++) {
                gridDetail.get(i).setCheckBox(false);
            }
        }
    }

    public void createConfirmTxt() {
        this.setMsg("");
        if (this.function == null || this.function.equals("0")) {
            this.setMsg("Please select function.");
            return;
        }
        this.setConfirmText("");
        if (this.function.equals("ADD")) {
            this.setConfirmText("Are you sure to enable the service on select accounts?");
        } else if (this.function.equals("MODIFY")) {
            this.setConfirmText("Are you sure to modify the service on select accounts?");
        }
    }

    public void processAction() {
        try {
            this.msg = "";

            if (validateField()) {
                MailCustomerInfo commanInfo = new MailCustomerInfo();
                commanInfo.setServiceType(serviceType);
                commanInfo.setFrequency(frequency);
                commanInfo.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(dmy.parse(startDate)));
                commanInfo.setStartFlag(startIndct);
                commanInfo.setCustomerId(customerId);
                commanInfo.setEmail(email);
                commanInfo.setAccList(gridDetail);
                String result = emailMgmRemote.enableModifyAccountServeces(commanInfo, function, getUserName(), startDate);
                if (result.equalsIgnoreCase("true")) {
                    btnRefreshAction();
                    this.setMsg("Data successfully saved.");
                }
            }
            gridDetail = null;

        } catch (Exception ex) {
            ex.printStackTrace();
            this.setMsg(ex.getMessage());
        }
    }

    public void btnRefreshAction() {
        this.btnLbl = "SAVE";
        this.hideServiceType = "";
        this.hideCustID = "";
        this.hidePanel2 = "";
        this.hidepanel3 = "";
        this.hideTablePanel = "";
        this.hideTablePanel1 = "none";
        this.verifyDetail = null;
        this.verifyCurrentItem = null;
        this.btndisbl = false;
        this.msg = "";
        this.function = "";
        this.serviceType = "";
        this.customerId = "";
        this.frequency = "";
        this.startDate = "";
        this.startIndct = "";
        this.currentItem = null;
        this.gridDetail = null;
        this.checkAllBox = false;
        this.confirmText = "";
        this.email = "";
    }

    public boolean validateField() {
        if ((this.function == null || this.function.equals("0"))) {
            setMsg("Please select funnction.");
            return false;
        }
        if ((this.serviceType == null || this.serviceType.equals("0"))) {
            setMsg("Please select service.");
            return false;
        }
        if ((this.customerId == null || this.customerId.equals(""))) {
            setMsg("Please fill customer id.");
            return false;
        }
        if (this.customerId.length() > 10) {
            setMsg("Please fill customer id less than 10 digit.");
            return false;
        }


        if ((this.frequency == null || this.frequency.equals("0"))) {
            setMsg("Please select frequency.");
            return false;
        }
        if ((this.startDate == null || this.startDate.equals(""))) {
            setMsg("Please fill start date.");
            return false;
        }
        if ((this.startIndct == null || this.startIndct.equals("0"))) {
            setMsg("Please select start indicator.");
            return false;
        }
        if ((this.email == null || this.email.equals(""))) {
            setMsg("Please fill email id.");
            return false;
        } else {
            String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
            java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
            java.util.regex.Matcher m = p.matcher(email);
            if (!m.matches()) {
                setMsg("Please fill valid email id.");
                return false;
            }
        }

        return true;
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }

    public void checkUnCheck() {
        // This method is required for grid check box. 
    }

    /*--------------------------------------------------------------Gettar And Settar------------------------------------------------------*/
    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartIndct() {
        return startIndct;
    }

    public void setStartIndct(String startIndct) {
        this.startIndct = startIndct;
    }

    public List<SelectItem> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<SelectItem> functionList) {
        this.functionList = functionList;
    }

    public List<SelectItem> getServiceTypeList() {
        return serviceTypeList;
    }

    public void setServiceTypeList(List<SelectItem> serviceTypeList) {
        this.serviceTypeList = serviceTypeList;
    }

    public List<SelectItem> getFrequencyList() {
        return frequencyList;
    }

    public void setFrequencyList(List<SelectItem> frequencyList) {
        this.frequencyList = frequencyList;
    }

    public List<SelectItem> getStartIndctList() {
        return startIndctList;
    }

    public void setStartIndctList(List<SelectItem> startIndctList) {
        this.startIndctList = startIndctList;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<AccontDetailList> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<AccontDetailList> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public boolean isCheckAllBox() {
        return checkAllBox;
    }

    public void setCheckAllBox(boolean checkAllBox) {
        this.checkAllBox = checkAllBox;
    }

    public MailCustomerInfo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(MailCustomerInfo currentItem) {
        this.currentItem = currentItem;
    }

    public String getConfirmText() {
        return confirmText;
    }

    public void setConfirmText(String confirmText) {
        this.confirmText = confirmText;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBtnLbl() {
        return btnLbl;
    }

    public void setBtnLbl(String btnLbl) {
        this.btnLbl = btnLbl;
    }

    public String getHideServiceType() {
        return hideServiceType;
    }

    public void setHideServiceType(String hideServiceType) {
        this.hideServiceType = hideServiceType;
    }

    public String getHideCustID() {
        return hideCustID;
    }

    public void setHideCustID(String hideCustID) {
        this.hideCustID = hideCustID;
    }

    public String getHidePanel2() {
        return hidePanel2;
    }

    public void setHidePanel2(String hidePanel2) {
        this.hidePanel2 = hidePanel2;
    }

    public String getHidepanel3() {
        return hidepanel3;
    }

    public void setHidepanel3(String hidepanel3) {
        this.hidepanel3 = hidepanel3;
    }

    public String getHideTablePanel() {
        return hideTablePanel;
    }

    public void setHideTablePanel(String hideTablePanel) {
        this.hideTablePanel = hideTablePanel;
    }

    public String getHideTablePanel1() {
        return hideTablePanel1;
    }

    public void setHideTablePanel1(String hideTablePanel1) {
        this.hideTablePanel1 = hideTablePanel1;
    }

    public List<AccontDetailList> getVerifyDetail() {
        return verifyDetail;
    }

    public void setVerifyDetail(List<AccontDetailList> verifyDetail) {
        this.verifyDetail = verifyDetail;
    }

    public MiscMgmtFacadeS1Remote getMiscRemoteS1() {
        return miscRemoteS1;
    }

    public void setMiscRemoteS1(MiscMgmtFacadeS1Remote miscRemoteS1) {
        this.miscRemoteS1 = miscRemoteS1;
    }

    public boolean isVerifyPanelViewFlag() {
        return verifyPanelViewFlag;
    }

    public void setVerifyPanelViewFlag(boolean verifyPanelViewFlag) {
        this.verifyPanelViewFlag = verifyPanelViewFlag;
    }

    public String getAcno1() {
        return acno1;
    }

    public void setAcno1(String acno1) {
        this.acno1 = acno1;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getServiceType1() {
        return serviceType1;
    }

    public void setServiceType1(String serviceType1) {
        this.serviceType1 = serviceType1;
    }

    public String getCustid1() {
        return custid1;
    }

    public void setCustid1(String custid1) {
        this.custid1 = custid1;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getFrequency1() {
        return frequency1;
    }

    public void setFrequency1(String frequency1) {
        this.frequency1 = frequency1;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public String getStartFlag1() {
        return startFlag1;
    }

    public void setStartFlag1(String startFlag1) {
        this.startFlag1 = startFlag1;
    }

    public AccontDetailList getVerifyCurrentItem() {
        return verifyCurrentItem;
    }

    public void setVerifyCurrentItem(AccontDetailList verifyCurrentItem) {
        this.verifyCurrentItem = verifyCurrentItem;
    }

    public boolean isBtndisbl() {
        return btndisbl;
    }

    public void setBtndisbl(boolean btndisbl) {
        this.btndisbl = btndisbl;
    }
}
