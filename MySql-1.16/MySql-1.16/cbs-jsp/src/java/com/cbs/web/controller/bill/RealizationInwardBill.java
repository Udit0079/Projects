/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.bill;

import com.cbs.web.pojo.bill.RealizationInwardBillPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.bill.InwardFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.hrms.web.utils.WebUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.ArrayList;
import javax.faces.model.SelectItem;
import java.util.List;
import java.util.Vector;
import javax.naming.NamingException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 *
 * @author root
 */
public class RealizationInwardBill {
    InwardFacadeRemote facdeRemote;
    private String orgnBrCode;
    private String todayDate;
    private HttpServletRequest req;
    private String userName;
    private String message;
    private List<SelectItem> year;
    private List<SelectItem> alphaCode;
    private List<SelectItem> reasonfCancel;
    private List<RealizationInwardBillPojo> realizationBill;
    private String billNo;
    private String years;
    private String billType;
    private String instNumbers;
    private double instAmount;
    private String instDate;
    private String accountNo;
    private String custName;
    private float bankCumu;
    private float postage;
    private String alphaCodes;
    private String panalAlpha;
    private String ho;
    private String acToBeCredited;
    private String panelReason;
    private double ourCharges;
    private double amtToBeDebited;
    private double returnCharges;
    private String reasonForCancel1;
    private String varPayBy;
    String allResult;

    public String getReasonForCancel1() {
        return reasonForCancel1;
    }

    public void setReasonForCancel1(String reasonForCancel1) {
        this.reasonForCancel1 = reasonForCancel1;
    }

    public String getVarPayBy() {
        return varPayBy;
    }

    public void setVarPayBy(String varPayBy) {
        this.varPayBy = varPayBy;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public double getReturnCharges() {
        return returnCharges;
    }

    public void setReturnCharges(double returnCharges) {
        this.returnCharges = returnCharges;
    }

    public double getInstAmount() {
        return instAmount;
    }

    public void setInstAmount(double instAmount) {
        this.instAmount = instAmount;
    }

    public double getOurCharges() {
        return ourCharges;
    }

    public void setOurCharges(double ourCharges) {
        this.ourCharges = ourCharges;
    }

    public double getAmtToBeDebited() {
        return amtToBeDebited;
    }

    public void setAmtToBeDebited(double amtToBeDebited) {
        this.amtToBeDebited = amtToBeDebited;
    }

//    public float getReturnCharges() {
//        return returnCharges;
//    }
//
//    public void setReturnCharges(float returnCharges) {
//        this.returnCharges = returnCharges;
//    }
//    public float getAmtToBeDebited() {
//        return amtToBeDebited;
//    }
//
//    public void setAmtToBeDebited(float amtToBeDebited) {
//        this.amtToBeDebited = amtToBeDebited;
//    }
//    public float getOurCharges() {
//        return ourCharges;
//    }
//
//    public void setOurCharges(float ourCharges) {
//        this.ourCharges = ourCharges;
//    }
    public String getPanelReason() {
        return panelReason;
    }

    public void setPanelReason(String panelReason) {
        this.panelReason = panelReason;
    }

    public String getAcToBeCredited() {
        return acToBeCredited;
    }

    public void setAcToBeCredited(String acToBeCredited) {
        this.acToBeCredited = acToBeCredited;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAlphaCodes() {
        return alphaCodes;
    }

    public void setAlphaCodes(String alphaCodes) {
        this.alphaCodes = alphaCodes;
    }

    public float getBankCumu() {
        return bankCumu;
    }

    public void setBankCumu(float bankCumu) {
        this.bankCumu = bankCumu;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getHo() {
        return ho;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

//    public float getInstAmount() {
//        return instAmount;
//    }
//
//    public void setInstAmount(float instAmount) {
//        this.instAmount = instAmount;
//    }
    public String getInstDate() {
        return instDate;
    }

    public void setInstDate(String instDate) {
        this.instDate = instDate;
    }

    public String getInstNumbers() {
        return instNumbers;
    }

    public void setInstNumbers(String instNumbers) {
        this.instNumbers = instNumbers;
    }

    public String getPanalAlpha() {
        return panalAlpha;
    }

    public void setPanalAlpha(String panalAlpha) {
        this.panalAlpha = panalAlpha;
    }

    public float getPostage() {
        return postage;
    }

    public void setPostage(float postage) {
        this.postage = postage;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public List<RealizationInwardBillPojo> getRealizationBill() {
        return realizationBill;
    }

    public void setRealizationBill(List<RealizationInwardBillPojo> realizationBill) {
        this.realizationBill = realizationBill;
    }

    public List<SelectItem> getAlphaCode() {
        return alphaCode;
    }

    public void setAlphaCode(List<SelectItem> alphaCode) {
        this.alphaCode = alphaCode;
    }

    public List<SelectItem> getReasonfCancel() {
        return reasonfCancel;
    }

    public void setReasonfCancel(List<SelectItem> reasonfCancel) {
        this.reasonfCancel = reasonfCancel;
    }

    public List<SelectItem> getYear() {
        return year;
    }

    public void setYear(List<SelectItem> year) {
        this.year = year;
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

    /** Creates a new instance of RealizationInwardBill */
    public RealizationInwardBill() {
        try {
            facdeRemote = (InwardFacadeRemote) ServiceLocator.getInstance().lookup("InwardFacade");
            req = getRequest();
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());
            //setUserName("manager1");
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(date));
            reasonfCancel = new ArrayList<SelectItem>();
            panelReason = "False";
            this.setMessage("");
            getAllDroupDowndata();
            getTableDetails();

        } catch (ApplicationException ex) {
            message = ex.getMessage();
        } catch (Exception ex) {
            message = ex.getMessage();
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void getAllDroupDowndata() {
        try {
            List yearDataList = new ArrayList();
            List alphaList = new ArrayList();
            List reasonCancelList = new ArrayList();
            yearDataList = facdeRemote.fYearInward(orgnBrCode);
            alphaList = facdeRemote.alphaCode(orgnBrCode);
            reasonCancelList = facdeRemote.reasonForCancel();
            year = new ArrayList<SelectItem>();
            alphaCode = new ArrayList<SelectItem>();
            reasonfCancel = new ArrayList<SelectItem>();
            for (int i = 0; i < yearDataList.size(); i++) {
                Vector ele = (Vector) yearDataList.get(i);
                for (Object ee : ele) {
                    year.add(new SelectItem(ee.toString(), ee.toString()));
                }
            }
            for (int j = 0; j < alphaList.size(); j++) {
                Vector ele1 = (Vector) alphaList.get(j);
                for (Object ee1 : ele1) {
                    alphaCode.add(new SelectItem(ee1.toString(), ee1.toString()));
                }
            }
            for (int k = 0; k < reasonCancelList.size(); k++) {
                Vector ele2 = (Vector) reasonCancelList.get(k);
                for (Object ee2 : ele2) {
                    reasonfCancel.add(new SelectItem(ee2.toString(), ee2.toString()));
                }
            }
        } catch (ApplicationException ex) {
            message = ex.getMessage();
        } catch (Exception ex) {
            message = ex.getMessage();
        }
    }

    public void getTableDetails() throws NamingException {
        realizationBill = new ArrayList<RealizationInwardBillPojo>();
        try {
            List resultLt = new ArrayList();


            resultLt = facdeRemote.LoadGridBookedData(orgnBrCode);
            if (!resultLt.isEmpty()) {
                for (int i = 0; i < resultLt.size(); i++) {
                    Vector ele = (Vector) resultLt.get(i);
                    RealizationInwardBillPojo realization = new RealizationInwardBillPojo();

                    realization.setBillNo(Float.parseFloat(ele.get(0).toString()));
                    realization.setBillType(ele.get(1).toString());
                    realization.setRemType(ele.get(2).toString());
                    realization.setfYear(Integer.parseInt(ele.get(3).toString()));
                    realization.setAcNo(ele.get(4).toString());
                    realization.setInstNo(ele.get(5).toString());
                    realization.setInstAmount(Float.parseFloat(ele.get(6).toString()));
                    realization.setCommu(Float.parseFloat(ele.get(7).toString()));
                    realization.setPostage(Float.parseFloat(ele.get(8).toString()));
                    realization.setInstDt(ele.get(9).toString());
                    realization.setBankName(ele.get(10).toString());
                    realization.setBankAddress(ele.get(11).toString());
                    realization.setEnterBy(ele.get(12).toString());

                    realizationBill.add(realization);
                }
            }
        } catch (ApplicationException ex) {
            message = ex.getMessage();
        } catch (Exception ex) {
            message = ex.getMessage();
        }
    }

    public void setFieldDisplayValues() {
        this.setMessage("");
        panelReason = "False";
        if (this.billType.equalsIgnoreCase("--Select--")) {
            this.setMessage("Please Select Bill Type.");
            return;
        }
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        if (this.billNo.equalsIgnoreCase("") || this.billNo.length() == 0) {
            this.setMessage("Please Enter Bill No.");
            return;
        }
        Matcher billNoCheck = p.matcher(billNo);
        if (!billNoCheck.matches()) {
            this.setMessage("Please Enter Valid  Bill No.");
            this.setAccountNo("");
            return;
        }
        if (this.years.equalsIgnoreCase("--Select--")) {
            this.setMessage("Please Select Year.");
            return;
        }

        try {
            //panelReason="False";
            if (billType.equals("")) {
                this.setMessage("Please Select bill Type ");
            }

            allResult = facdeRemote.fieldDisplay(Float.parseFloat(billNo), Integer.parseInt(years), billType, orgnBrCode);
            if (allResult.equals("Bill No. Does Not Exist")) {
                this.setMessage(allResult);
            } else {
                String[] values = null;
                String spliter = ": ";
                values = allResult.split(spliter);
                this.setInstNumbers(values[0]);
                this.setInstAmount(Float.parseFloat(values[1]));
                this.setInstDate(values[2]);
                this.setAccountNo(values[3]);
                this.setCustName(values[4]);
                this.setBankCumu(Float.parseFloat(values[5]));
                this.setPostage(Float.parseFloat(values[6]));
                this.setAlphaCodes(values[7]);
                this.setPanalAlpha(values[8]);
                String h = (values[9]);
                if ((h.equals("")) || (h == null)) {
                    this.setHo("");

                } else {
                    this.setHo(h);
                }
                this.setAcToBeCredited(values[10]);
            }
        } catch (ApplicationException ex) {
            message = ex.getMessage();
        } catch (Exception ex) {
            message = ex.getMessage();
        }
    }

    public void dishonorButtonClick() {
        try {
            panelReason = "True";

        } catch (Exception ex) {
            message = ex.getMessage();
        }
    }

    public void txtOurChargesOnChange() {
        try {
            double taxAmt = 0f;
            String charge = String.valueOf(ourCharges);
            float ourChgs = Float.parseFloat(charge);
            if (ourCharges > 0) {
                String findTaxes = facdeRemote.findTax(ourChgs);
                if (findTaxes.contains("[")) {
                    String[] values = null;
                    findTaxes = findTaxes.replace("]", "");
                    findTaxes = findTaxes.replace("[", "");
                    String[] temp = findTaxes.split(",");
                    for (String str : temp) {
                        if (!str.equals("") || str != null) {
                            taxAmt = Double.parseDouble(str);
                        }
                    }
                }
            }
            double TxtAmtDebited = instAmount - (ourCharges + taxAmt);
            setAmtToBeDebited(TxtAmtDebited);
        } catch (ApplicationException ex) {
            message = ex.getMessage();
        } catch (Exception ex) {
            message = ex.getMessage();
        }
    }

    public void dishonorYesButtonClick() {
        this.setMessage("");
        if (this.billType.equalsIgnoreCase("--Select--")) {
            this.setMessage("Please Select Bill Type.");
            return;
        }
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        if (this.billNo.equalsIgnoreCase("") || this.billNo.length() == 0) {
            this.setMessage("Please Enter Bill No.");
            return;
        }
        Matcher billNoCheck = p.matcher(billNo);
        if (!billNoCheck.matches()) {
            this.setMessage("Please Enter Valid  Bill No.");
            this.setAccountNo("");
            return;
        }
        if (this.years.equalsIgnoreCase("--Select--")) {
            this.setMessage("Please Select Year.");
            return;
        }
        if (this.allResult.equalsIgnoreCase("Bill No. Does Not Exist")) {
            this.setMessage("Bill No. Does Not Exist So You Can Not Dishonor This Record");
            return;
        }
        try {
            String dd = instDate.substring(0, 2);
            String mm = instDate.substring(3, 5);
            String yy = instDate.substring(6, 10);
            String correctDt = yy + "" + mm + "" + dd;
            String dishonorYes = facdeRemote.dishonorClick(instNumbers, correctDt, instAmount, billType, Float.parseFloat(billNo), Integer.parseInt(years), panelReason, ourCharges, amtToBeDebited, returnCharges, reasonForCancel1, accountNo, userName, orgnBrCode);
            this.setMessage(dishonorYes);
            if (dishonorYes.equals("Dishonor Is Successfull")) {
                clearText1();
                this.setReturnCharges(0);
                panelReason = "False";
            }
            getTableDetails();
        } catch (ApplicationException ex) {
            message = ex.getMessage();
        } catch (Exception ex) {
            message = ex.getMessage();
        }
    }

    public void clearText1() {
        this.setAccountNo("");
        this.setBillType("--SELECT--");
        this.setAlphaCodes("--SELECT--");
        this.setYears("--SELECT--");
        this.setCustName("");
        this.setInstNumbers("");
        this.setBillNo("");
        this.setInstAmount(0);
        this.setInstDate("");
        this.setBankCumu(0);
        this.setPostage(0);
        this.setOurCharges(0);
        this.setAmtToBeDebited(0);
        this.setAcToBeCredited("");
        this.setHo("");
        this.setReturnCharges(0);
        panelReason = "False";
    }

    public void clearText() {
        try {

            this.setAccountNo("");
            this.setBillType("--SELECT--");
            this.setAlphaCodes("--SELECT--");
            this.setYears("--SELECT--");
            this.setMessage("");
            this.setCustName("");
            this.setInstNumbers("");
            this.setBillNo("");
            this.setInstAmount(0);
            this.setInstDate("");
            this.setBankCumu(0);
            this.setPostage(0);
            this.setOurCharges(0);
            this.setAmtToBeDebited(0);
            this.setAcToBeCredited("");
            this.setHo("");
            this.setReturnCharges(0);
            getTableDetails();
            panelReason = "False";

        } catch (Exception ex) {
            message = ex.getMessage();
        }

    }

    public void passClick() {
        this.setMessage("");
        if (this.billType.equalsIgnoreCase("--Select--")) {
            this.setMessage("Please Select Bill Type.");
            return;
        }
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        if (this.billNo.equalsIgnoreCase("") || this.billNo.length() == 0) {
            this.setMessage("Please Enter Bill No.");
            return;
        }
        Matcher billNoCheck = p.matcher(billNo);
        if (!billNoCheck.matches()) {
            this.setMessage("Please Enter Valid  Bill No.");
            this.setAccountNo("");
            return;
        }
        if (this.years.equalsIgnoreCase("--Select--")) {
            this.setMessage("Please Select Year.");
            return;
        }
        if (this.allResult.equalsIgnoreCase("Bill No. Does Not Exist")) {
            this.setMessage("Bill No. Does Not Exist So You Can Not Pass This Record");
            return;
        }
        try {
            String acResult = facdeRemote.accountValidationResult(accountNo, instNumbers);
            if (acResult.equals("Instrument is Not Issued")) {
                varPayBy = "4";
            } else {
                varPayBy = "3";
                this.setMessage(acResult);
            }

        } catch (ApplicationException ex) {
            message = ex.getMessage();
        } catch (Exception ex) {
            message = ex.getMessage();
        }
    }

    public void passYesClick() {
        this.setMessage("");
        if (this.billType.equalsIgnoreCase("--Select--")) {
            this.setMessage("Please Select Bill Type.");
            return;
        }
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        if (this.billNo.equalsIgnoreCase("") || this.billNo.length() == 0) {
            this.setMessage("Please Enter Bill No.");
            return;
        }
        Matcher billNoCheck = p.matcher(billNo);
        if (!billNoCheck.matches()) {
            this.setMessage("Please Enter Valid  Bill No.");
            this.setAccountNo("");
            return;
        }
        if (this.years.equalsIgnoreCase("--Select--")) {
            this.setMessage("Please Select Year.");
            return;
        }
        try {
            String instA = String.valueOf(instAmount);
            float instAmt = Float.parseFloat(instA);
            String acResult = facdeRemote.accountValidation(accountNo, instAmt);
            if (acResult.equals("False")) {
                passButtonClick();
                getTableDetails();
                clearText1();
            } else {
                String[] values = null;
                String spliter = ": ";
                values = acResult.split(spliter);
                String abc = (values[0]);
                this.setMessage(abc);
            }

        } catch (ApplicationException ex) {
            message = ex.getMessage();
        } catch (Exception ex) {
            message = ex.getMessage();
        }
    }

    public void passButtonClick() {
        try {
            String dd = instDate.substring(0, 2);
            String mm = instDate.substring(3, 5);
            String yy = instDate.substring(6, 10);
            String correctDt = yy + "" + mm + "" + dd;
            //double amtToDebited=instAmount - ourCharges;
            String passResults = facdeRemote.passClick(instNumbers, correctDt, instAmount, billType, Float.parseFloat(billNo), Integer.parseInt(years), panelReason, ourCharges, amtToBeDebited, returnCharges, reasonForCancel1, accountNo, custName, Integer.parseInt(varPayBy), userName, orgnBrCode);
            this.setMessage(passResults);
            panelReason = "False";
        } catch (ApplicationException ex) {
            message = ex.getMessage();
        } catch (Exception ex) {
            message = ex.getMessage();
        }
    }

    public String exitForm() {
        clearText();
        panelReason = "False";
        return "case1";
    }
}
