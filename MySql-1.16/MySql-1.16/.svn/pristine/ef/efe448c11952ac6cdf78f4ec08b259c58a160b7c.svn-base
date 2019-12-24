/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.td;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.hrms.web.utils.WebUtil;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

public class TdEnquiry {

    private String orgBrnCode;
    HttpServletRequest request;
    private String todayDate;
    private String userName;
    private String message;
    private String st1;
    List<SelectItem> list1;
    String item1;
    List<SelectItem> list2;
    String item2;
    List<SelectItem> list3;
    String item3;
    String amount;
    String txtduration;
    String rateInterest;
    String investRs;
    boolean flag;
    boolean flag1;
    private String varVisisble;
    private String varVisisble1;
    private final String jndiHomeNameTdRcpt = "TdReceiptManagementFacade";
    private TdReceiptManagementFacadeRemote tdRcptMgmtRemote = null;
    
    private String mqPayment;
    private List<SelectItem> mqPaymentList;
    private String mqMonth;
    private boolean disDuration;
    private boolean disMqMonth;
    private String focusVar;
    private boolean disDurationTxt;
    private boolean disIntOpt;

    public String getVarVisisble1() {
        return varVisisble1;
    }

    public void setVarVisisble1(String varVisisble1) {
        this.varVisisble1 = varVisisble1;
    }

    public String getVarVisisble() {
        return varVisisble;
    }

    public void setVarVisisble(String varVisisble) {
        this.varVisisble = varVisisble;
    }

    public boolean isFlag1() {
        return flag1;
    }

    public void setFlag1(boolean flag1) {
        this.flag1 = flag1;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getInvestRs() {
        return investRs;
    }

    public void setInvestRs(String investRs) {
        this.investRs = investRs;
    }

    public String getRateInterest() {
        return rateInterest;
    }

    public void setRateInterest(String rateInterest) {
        this.rateInterest = rateInterest;
    }

    public String getTxtduration() {
        return txtduration;
    }

    public void setTxtduration(String txtduration) {
        this.txtduration = txtduration;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getItem1() {
        return item1;
    }

    public void setItem1(String item1) {
        this.item1 = item1;
    }

    public String getItem2() {
        return item2;
    }

    public void setItem2(String item2) {
        this.item2 = item2;
    }

    public String getItem3() {
        return item3;
    }

    public void setItem3(String item3) {
        this.item3 = item3;
    }

    public List<SelectItem> getList2() {
        return list2;
    }

    public void setList2(List<SelectItem> list2) {
        this.list2 = list2;
    }

    public List<SelectItem> getList3() {
        return list3;
    }

    public void setList3(List<SelectItem> list3) {
        this.list3 = list3;
    }

    public List<SelectItem> getList1() {
        return list1;
    }

    public void setList1(List<SelectItem> list1) {
        this.list1 = list1;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOrgBrnCode() {
        return orgBrnCode;
    }

    public void setOrgBrnCode(String orgBrnCode) {
        this.orgBrnCode = orgBrnCode;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public String getSt1() {
        return st1;
    }

    public void setSt1(String st1) {
        this.st1 = st1;
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

    public String getMqPayment() {
        return mqPayment;
    }

    public void setMqPayment(String mqPayment) {
        this.mqPayment = mqPayment;
    }

    public List<SelectItem> getMqPaymentList() {
        return mqPaymentList;
    }

    public void setMqPaymentList(List<SelectItem> mqPaymentList) {
        this.mqPaymentList = mqPaymentList;
    }   

    public String getMqMonth() {
        return mqMonth;
    }

    public void setMqMonth(String mqMonth) {
        this.mqMonth = mqMonth;
    }

    public boolean isDisDuration() {
        return disDuration;
    }

    public void setDisDuration(boolean disDuration) {
        this.disDuration = disDuration;
    }

    public boolean isDisDurationTxt() {
        return disDurationTxt;
    }

    public void setDisDurationTxt(boolean disDurationTxt) {
        this.disDurationTxt = disDurationTxt;
    }

    public boolean isDisIntOpt() {
        return disIntOpt;
    }

    public void setDisIntOpt(boolean disIntOpt) {
        this.disIntOpt = disIntOpt;
    }    
    
    public boolean isDisMqMonth() {
        return disMqMonth;
    }

    public void setDisMqMonth(boolean disMqMonth) {
        this.disMqMonth = disMqMonth;
    }

    public String getFocusVar() {
        return focusVar;
    }

    public void setFocusVar(String focusVar) {
        this.focusVar = focusVar;
    }

    /** Creates a new instance of td_Enquiry1 */
    public TdEnquiry() {
        try {
            request = getRquest();
            String orgnBrIp = WebUtil.getClientIP(request);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgBrnCode = Init.IP2BR.getBranch(localhost);
            Date dt = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(dt));
            setUserName(request.getRemoteUser());
            tdRcptMgmtRemote = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameTdRcpt);
            this.setMessage("");
            list1 = new ArrayList<SelectItem>();
            list2 = new ArrayList<SelectItem>();
            list3 = new ArrayList<SelectItem>();
            flag = true;
            flag = false;
            onload();
            mqPaymentList = new ArrayList<SelectItem>();
            mqPaymentList.add(new SelectItem("","--Select--"));
            mqPaymentList.add(new SelectItem("MQ","Monthly Interest"));
            mqPaymentList.add(new SelectItem("QQ","Quarterly Interest"));
            mqPaymentList.add(new SelectItem("OT","Others"));            
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
            Logger.getLogger(NewTdRecieptCreation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
            Logger.getLogger(NewTdRecieptCreation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void onBlurMQpayment(){
        this.setMqMonth("");
        this.setItem2("--Select--");
        this.setTxtduration("");
        this.setItem3("--Select--");
        this.setInvestRs("");
        if(mqPayment.equalsIgnoreCase("")){
            focusVar = "tdInqListbox";
            this.setMessage("Please Select Type Of Inquiry.");
            return;
        }else if(mqPayment.equalsIgnoreCase("MQ") || mqPayment.equalsIgnoreCase("QQ")){
            setDisDuration(true);
            setDisDurationTxt(true);
            setDisIntOpt(true);
            setDisMqMonth(false);
            focusVar = "mqMonthTxt";
        }else{
            setDisDuration(false);
            setDisDurationTxt(false);
            setDisIntOpt(false);
            setDisMqMonth(true);
            focusVar = "tdEnquirygridlistbox";
        }
    }

    public HttpServletRequest getRquest() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req == null) {

            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return req;
    }

    public void onload() {
        this.setMessage("");
        this.setAmount("");
        this.setInvestRs("");
        this.setRateInterest("");
        this.setTxtduration("");

        list1.add(new SelectItem("--Select--"));
        list1.add(new SelectItem("Review By Amount"));
        list1.add(new SelectItem("Review By Interest"));


        list2.add(new SelectItem("--Select--"));
        list2.add(new SelectItem("Days"));
        list2.add(new SelectItem("Months"));
        list2.add(new SelectItem("Years"));

        list3.add(new SelectItem("--Select--"));
        list3.add(new SelectItem("Monthly"));
        list3.add(new SelectItem("Quarterly"));
        list3.add(new SelectItem("Simple"));
        list3.add(new SelectItem("Cumulative"));
        list3.add(new SelectItem("Yearly"));

    }

    public void resetForm() {
        this.setMessage("");
        this.setAmount("");
        this.setInvestRs("");
        this.setRateInterest("");
        this.setTxtduration("");
        this.setMqPayment("");
        this.setMqMonth("");
        setDisDuration(false);
        setDisDurationTxt(false);
        setDisIntOpt(false);
        setDisMqMonth(true);
    }

    public void onChange() {
        this.setRateInterest("");
        this.setMessage("");
        this.setAmount("");
        this.setInvestRs("");
        this.setItem2("--Select--");
        this.setItem3("--Select--");
        this.setTxtduration("");
        this.setMqPayment("");
        this.setMqMonth("");
        if (item1.equals("Review By Amount")) {
            setVarVisisble("Enter TD amount You Would Like To Invest");
            setVarVisisble1("You Will Earn Approx Interest Of Rs.");
        } else if (item1.equals("Review By Interest")) {
            setVarVisisble("Enter The Interest You Would Like To Earn");
            setVarVisisble1("You Will Need To Invest Approx Rs.");
        } else {
            setVarVisisble("Enter TD Amount You Would Like To Invest");
            setVarVisisble1("You Will Earn Approx Interest Of Rs.");
        }

    }

    public void btnCalculateAction() {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        if((this.mqPayment == null) || (mqPayment.equalsIgnoreCase(""))){
            this.setMessage("Please Select Type Of Inquiry.");
            return;
        }
        
        if (item1.equals("--Select--")) {
            this.setMessage("Please Select Reveiw By Amount And Interest.");
            return;
        }
        
        if (item1.equals("Review By Amount")) {
            if ((this.amount == null) || (amount.equalsIgnoreCase(""))) {
                this.setMessage("Please Enter The Value For Amount");
                return;
            }
            Matcher amountCheck = p.matcher(amount);
            if (!amountCheck.matches()) {
                this.setMessage("Please Enter Numeric Value For Amount..");
                return;
            }
        }
        if (item1.equals("Review By Interest")) {
            if ((this.amount == null) || (amount.equalsIgnoreCase(""))) {
                this.setMessage("Please Enter The Value For Invest");
                return;
            }
            Matcher investRsCheck = p.matcher(amount);
            if (!investRsCheck.matches()) {
                this.setMessage("Please Enter Numeric Value For Invest..");
                return;
            }
        }
        if (Double.parseDouble(this.getAmount()) < 0) {
            this.setMessage("Please Enter Some Positive Value In Amount");
            return;
        }
        
        if(mqPayment.equalsIgnoreCase("MQ") || mqPayment.equalsIgnoreCase("QQ")){
            if ((this.mqMonth == null) || (mqMonth.equalsIgnoreCase(""))) {
                this.setMessage("Please Enter The Value For Months");
                return;
            }
            
            int mDiv = 12;
            if(mqPayment.equalsIgnoreCase("QQ")){
                mDiv = 3;
            }
            
            if(!(Integer.parseInt(this.mqMonth) % mDiv == 0)){
                this.setMessage("Please Enter The Proper Value For Months");
                return;
            }            
        }else{
            if (item2.equals("--Select--")) {
               this.setMessage("Please Select Duration.");
                return;
            }
            if ((this.txtduration == null) || (txtduration.equalsIgnoreCase(""))) {
                this.setMessage("Please Enter The Value For Duration");
                return;
            }
            Matcher durationCheck = p.matcher(txtduration);
            if (!durationCheck.matches()) {
                this.setMessage("Please Enter Numeric Value For Rate Of Duration.");
                return;
            }
            if (Double.parseDouble(this.getTxtduration()) < 0) {
                this.setMessage("Please Enter Some Positive Value In Duration.");
                return;
            }
            if (item3.equals("--Select--")) {
                this.setMessage("Please Select Interest Option.");
                return;
            }
        }
        
        if ((this.rateInterest == null) || (rateInterest.equalsIgnoreCase(""))) {
            this.setMessage("Please Enter The Value For Rate Of Interest");
            return;
        }
        
        Matcher billNoCheck = p.matcher(rateInterest);
        if (!billNoCheck.matches()) {
            this.setMessage("Please Enter Numeric Value For Rate Of Interest.");
            return;
        }
        if (Double.parseDouble(this.rateInterest) < 0) {
            this.setMessage("Please Enter Some Positive Value In Rate Of Interest.");
            return;
        }
        
        if (this.item1.equals("Review By Amount")) {
            calAmt();
        } else {
            btnCalculate();
        }
    }

    public void calAmt() {
        this.setMessage("");
        String as = "Please Select The Duration In Months Or Years For This Interest Option";
        String enterAmount = this.getAmount();
        Double amt = Double.parseDouble(enterAmount);
        String selectDuration = this.item2;
//        if ((item2.equals("Days")) && (item3.equals("Monthly") || item3.equals("Quarterly") || item3.equals("Cumulative"))) {
//            this.setMessage("Please Select The Duration In Months Or Years For This Interest Option");
//            return;
//        }
        String duration = this.getTxtduration();
        String interestOption = this.item3;
        String rateOfInterest = this.getRateInterest();
        float rate = Float.parseFloat(rateOfInterest);

        try {
            if(mqPayment.equalsIgnoreCase("MQ") || mqPayment.equalsIgnoreCase("QQ")){
                selectDuration = "Months";
                duration = this.mqMonth;
            }
            
            String date = tdRcptMgmtRemote.currentDate(selectDuration, duration);
            String[] values = null;
            String spliter = ": ";
            values = date.split(spliter);
            String fdDate = values[0];
            String matDate = values[1];
            String yy = matDate.substring(0, 4);
            String mm = matDate.substring(4, 6);
            String dd = matDate.substring(6, 8);
            String maturityDate = yy + "" + mm + "" + dd;
            if (this.item2.equals("Years")) {
                selectDuration = "Years";
            }
            if (this.item2.equals("Months")) {
                selectDuration = "Months";
            }
            String period = duration + selectDuration;
            String rsInvest;
            if(mqPayment.equalsIgnoreCase("OT")){
                rsInvest = tdRcptMgmtRemote.orgFDInterest(this.item3, rate, fdDate, maturityDate, amt, period, this.getOrgBrnCode());
            }else{
                rsInvest = tdRcptMgmtRemote.orgFDInterest("S", rate, fdDate, maturityDate, amt, period, this.getOrgBrnCode());
            }
            
            /*
            deciFormat.format(dTotal) and got the String value of dTotal=50377172.35*/
            //  new java.text.DecimalFormat("#,##0.00").format(1222556456.000);
            if(mqPayment.equalsIgnoreCase("MQ") || mqPayment.equalsIgnoreCase("QQ")){
                int mVal = 12;
                if(mqPayment.equalsIgnoreCase("QQ")){
                    mVal = mVal / 3;
                }
                String rValue = Double.toString(Math.round(Double.parseDouble(rsInvest)/ mVal));
                this.setInvestRs(rValue);
            }else{
                this.setInvestRs(rsInvest);
            }            
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
            Logger.getLogger(NewTdRecieptCreation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
            Logger.getLogger(NewTdRecieptCreation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void btnCalculate() {

        String reviewByAmount = this.item1;
        String enterAmount = this.getAmount();
        String selectDuration = this.item2;
        String duration = this.getTxtduration();
        String interestOption = this.item3;
        String rateOfInterest = this.getRateInterest();
        String brncode = this.getOrgBrnCode();
        onblurRoi();
        onblurDuration();
        try {
            if(mqPayment.equalsIgnoreCase("MQ") || mqPayment.equalsIgnoreCase("QQ")){
                selectDuration = "Months";
                duration = this.mqMonth;
                interestOption = "Simple";
            }
            String rsInvest = tdRcptMgmtRemote.tdEnquiryCalculation(reviewByAmount, enterAmount, selectDuration, duration, interestOption, rateOfInterest, brncode);

            if (rsInvest.equals("Please Select The Duration In Months Or Years For This Interest Option")) {
                this.setMessage("Please Select The Duration In Months Or Years For This Interest Option");
            } else {
                if(mqPayment.equalsIgnoreCase("MQ") || mqPayment.equalsIgnoreCase("QQ")){
                    int mVal = 12;
                    if(mqPayment.equalsIgnoreCase("QQ")){
                        mVal = 4;
                    }
                    String rValue = Double.toString(Math.round(Double.parseDouble(rsInvest) * mVal));
                    this.setInvestRs(rValue);
                }else{
                    this.setInvestRs(rsInvest);
                }
            }

        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
            Logger.getLogger(NewTdRecieptCreation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
            Logger.getLogger(NewTdRecieptCreation.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void onblurRoi() {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        Matcher billNoCheck = p.matcher(rateInterest);
        if (!billNoCheck.matches()) {
            this.setMessage("Please Enter Valid  Effective Rate Of Interest");
            return;
        }
        if (this.rateInterest.equals("")) {
            this.setMessage("Please Enter The Value For Rate Of Interest");
        } else if (Double.parseDouble(this.rateInterest) < 0) {
            this.setMessage("Please Enter Some Positive Value In Rate Of Interest.");
            return;
        } else {
            this.setMessage("");
        }
    }

    public void onblurDuration() {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        Matcher billNoCheck = p.matcher(txtduration);
        if (!billNoCheck.matches()) {
            this.setMessage("Please Enter Valid  Effective Duration");
            return;
        }
        if (this.txtduration.equals("")) {
            this.setMessage("Please Enter The Value For Duration");
        } else if (Double.parseDouble(this.getTxtduration()) < 0) {
            this.setMessage("Please Enter Some Positive Value In Duration.");
            return;
        } else {
            this.setMessage("");
        }
    }

    public void onblurAmount() {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        Matcher billNoCheck = p.matcher(amount);
        if (!billNoCheck.matches()) {
            this.setMessage("Please Enter Valid  Effective Amount");
            return;
        }
        if (this.amount.equals("")) {
            this.setMessage("Please Enter The Value For Amount");
        } else if (Double.parseDouble(this.getAmount()) < 0) {
            this.setMessage("Please Enter Some Positive Value In Amount");
            return;
        } else {
            this.setMessage("");
        }
    }

    public String exitBtnAction() {
        resetForm();
        return "case1";
    }
}
