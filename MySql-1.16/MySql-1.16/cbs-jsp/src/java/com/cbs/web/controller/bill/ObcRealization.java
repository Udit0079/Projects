/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.bill;

import com.cbs.web.pojo.bill.ObcRealse;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.bill.InwardFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

public class ObcRealization {
    InwardFacadeRemote obcrealise;
    private String todayDate;
    private String userName;
    private HttpServletRequest req;
    private String orgnBrCode;
    private String message;
    private String billType;
    private String billNo;
    private String year;
    private List<SelectItem> fYear;
    private List<SelectItem> reason;
    private String reason1;
    private String acNo;
    private String name;
    private String instAmt;
    private String instNo;
    private String instDate;
    private String commision;
    private String postAmt;
    private Date realiseDate;
    private String bnkCharges;
    private float credit;
    private List<ObcRealse> incis;
    private String blank;
    private String retCharges;
    private String Relsdate;
    private Date Relsdate1;
    private String instDate2;
    private Date instDate3;
    private String panelReason;
    private String flag1;
    boolean flag;
    boolean flagg;

    public boolean isFlagg() {
        return flagg;
    }

    public void setFlagg(boolean flagg) {
        this.flagg = flagg;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
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

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }

    public String getOrgnBrCode() {
        return orgnBrCode;
    }

    public void setOrgnBrCode(String orgnBrCode) {
        this.orgnBrCode = orgnBrCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<SelectItem> getfYear() {
        return fYear;
    }

    public void setfYear(List<SelectItem> fYear) {
        this.fYear = fYear;
    }

    public List<SelectItem> getReason() {
        return reason;
    }

    public void setReason(List<SelectItem> reason) {
        this.reason = reason;
    }

    public String getReason1() {
        return reason1;
    }

    public void setReason1(String reason1) {
        this.reason1 = reason1;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstAmt() {
        return instAmt;
    }

    public void setInstAmt(String instAmt) {
        this.instAmt = instAmt;
    }

    public String getInstNo() {
        return instNo;
    }

    public void setInstNo(String instNo) {
        this.instNo = instNo;
    }

    public String getInstDate() {
        return instDate;
    }

    public void setInstDate(String instDate) {
        this.instDate = instDate;
    }

    public String getCommision() {
        return commision;
    }

    public void setCommision(String commision) {
        this.commision = commision;
    }

    public String getPostAmt() {
        return postAmt;
    }

    public void setPostAmt(String postAmt) {
        this.postAmt = postAmt;
    }

    public Date getRealiseDate() {
        return realiseDate;
    }

    public void setRealiseDate(Date realiseDate) {
        this.realiseDate = realiseDate;
    }

    public String getBnkCharges() {
        return bnkCharges;
    }

    public void setBnkCharges(String bnkCharges) {
        this.bnkCharges = bnkCharges;
    }

    public float getCredit() {
        return credit;
    }

    public void setCredit(float credit) {
        this.credit = credit;
    }

    public List<ObcRealse> getIncis() {
        return incis;
    }

    public void setIncis(List<ObcRealse> incis) {
        this.incis = incis;
    }

    public String getBlank() {
        return blank;
    }

    public void setBlank(String blank) {
        this.blank = blank;
    }

    public String getRetCharges() {
        return retCharges;
    }

    public void setRetCharges(String retCharges) {
        this.retCharges = retCharges;
    }

    public String getRelsdate() {
        return Relsdate;
    }

    public void setRelsdate(String Relsdate) {
        this.Relsdate = Relsdate;
    }

    public Date getRelsdate1() {
        return Relsdate1;
    }

    public void setRelsdate1(Date Relsdate1) {
        this.Relsdate1 = Relsdate1;
    }

    public String getInstDate2() {
        return instDate2;
    }

    public void setInstDate2(String instDate2) {
        this.instDate2 = instDate2;
    }

    public Date getInstDate3() {
        return instDate3;
    }

    public void setInstDate3(Date instDate3) {
        this.instDate3 = instDate3;
    }

    public String getPanelReason() {
        return panelReason;
    }

    public void setPanelReason(String panelReason) {
        this.panelReason = panelReason;
    }

    public String getFlag1() {
        return flag1;
    }

    public void setFlag1(String flag1) {
        this.flag1 = flag1;
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public ObcRealization() {

        req = getRequest();
        try {
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            setTodayDate(sdf.format(date));
            obcrealise = (InwardFacadeRemote) ServiceLocator.getInstance().lookup("InwardFacade");
            setRealiseDate(date);
            fYear();
            gridData();
            clear();

        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void fYear() {
        try {
            List resultList = new ArrayList();
            List resultList1 = new ArrayList();


            resultList = obcrealise.fYear(this.orgnBrCode);
            fYear = new ArrayList<SelectItem>();
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                for (Object ee : ele) {
                    fYear.add(new SelectItem(ee.toString()));
                }
            }
            resultList1 = obcrealise.descriptionCodeBook();
            reason = new ArrayList<SelectItem>();
            for (int i = 0; i < resultList1.size(); i++) {
                Vector ele = (Vector) resultList1.get(i);
                for (Object ee : ele) {
                    reason.add(new SelectItem(ee.toString()));
                }
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void gridData() {
        incis = new ArrayList<ObcRealse>();
        try {
            List resultList = new ArrayList();
            resultList = obcrealise.grdDataObcRealization(orgnBrCode);
            for (int i = 0; i < resultList.size(); i++) {
                Vector v = (Vector) resultList.get(i);
                ObcRealse tab = new ObcRealse();
                tab.setAccountNo(v.get(0).toString());
                tab.setInstNo(v.get(1).toString());
                tab.setAmount(v.get(2).toString());
                tab.setInstDate(v.get(3).toString());
                tab.setBankName(v.get(4).toString());
                tab.setBankAddress(v.get(5).toString());
                tab.setBillType(v.get(6).toString());
                tab.setEnterBy(v.get(7).toString());
                tab.setBillNo(v.get(8).toString());
                tab.setfYear(v.get(9).toString());
                incis.add(tab);
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void tabData() {
        try {
            panelReason = "True";
            setCredit(0);
            setName("");
            setMessage("");
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (billNo.equals("") || billNo == null) {
                flag1 = "false";
                this.setMessage("Please Enter Bill No");
                return;
            }
            if (year.equals("--SELECT--") || year == null) {
                this.setMessage("Please Select Year");
                return;
            }
            Matcher billNoCheck = p.matcher(billNo);
            if (!billNoCheck.matches()) {
                this.setMessage("Please Enter Bill No In Integers");
                this.setBillNo("");
                return;
            }
            List resultList = new ArrayList();
            resultList = obcrealise.tabDataObcRealization(Float.parseFloat(billNo), Integer.parseInt(year), billType, this.orgnBrCode);
            String nm = obcrealise.custName(Float.parseFloat(billNo), Integer.parseInt(year), billType, this.orgnBrCode);
            if (!nm.isEmpty()) {
                setName(nm);
                flag1 = "true";
            } else {
                setMessage("Bill No. Does Not Exist");
                this.setBillNo("");
                this.setName("");
                setInstNo("");
                setInstAmt("");
                setInstDate("");
                setAcNo("");
                setCommision("");
                setPostAmt("");
                flag1 = "false";
            }
            Vector v = (Vector) resultList.get(0);
            this.setInstNo(v.get(0).toString());
            this.setInstAmt(v.get(1).toString());
            this.setInstDate(v.get(2).toString());
            this.setAcNo(v.get(3).toString());
            this.setCommision(v.get(4).toString());
            this.setPostAmt(v.get(5).toString());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            setInstDate3(sdf.parse(instDate));
            setInstDate2(ymd.format(instDate3));
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void tabOnCharge() {
        try {
            setMessage("");
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");

            if (billNo.equals("") || billNo == null) {
                this.setMessage("Please Enter Bill No");
                return;
            }
            if (year.equals("--SELECT--") || year == null) {
                this.setMessage("Please Select Year");
                return;
            }
            Matcher billNoCheck = p.matcher(billNo);
            if (!billNoCheck.matches()) {
                this.setMessage("Please Enter Bill No In Integers");
                this.setBillNo("");
                return;
            }
            if (commision.equals("") || commision == null) {
                this.setMessage("Please Enter Commision");
                return;
            }
            Matcher commisionCheck = p.matcher(commision);
            if (!commisionCheck.matches()) {
                this.setMessage("Please Enter Commision In Integers");
                setCommision("");
                return;
            }
            if (postAmt.equals("") || postAmt == null) {
                this.setMessage("Please Enter Commision");
                return;
            }
            Matcher postAmtCheck = p.matcher(postAmt);
            if (!postAmtCheck.matches()) {
                this.setMessage("Please Enter Postage Amount In Integers");
                setCommision("");
                return;
            }
            if (this.realiseDate != null) {
                String s1 = onblurNomDob();
                if (!s1.equalsIgnoreCase("true")) {
                    this.setMessage(s1);
                    return;
                } else {
                    this.setMessage("");
                }
            } else {
                this.setMessage("");
            }
            if (bnkCharges.equalsIgnoreCase("")) {
                this.setMessage("Please Enter Collecting Bank Charges");
                return;
            }
            Matcher bnkChargesCheck = p.matcher(bnkCharges);
            if (!bnkChargesCheck.matches()) {
                this.setMessage("Please Enter Collecting Bank Charges In Integers");
                setBnkCharges("");
                return;
            } else {
                flag = false;
            }
            float resultList = obcrealise.OtherCharges(Float.parseFloat(billNo), Integer.parseInt(year), billType, Float.parseFloat(bnkCharges), Float.parseFloat(instAmt), Float.parseFloat(commision), Float.parseFloat(postAmt), this.orgnBrCode);
            setCredit(resultList);
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void realisedClick() {
        try {
            setMessage("");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");

            if (billNo.equals("") || billNo == null) {
                this.setMessage("Please Enter Bill No");
                return;
            }
            if (year.equals("--SELECT--") || year == null) {
                this.setMessage("Please Select Year");
                return;
            }
            Matcher billNoCheck = p.matcher(billNo);
            if (!billNoCheck.matches()) {
                this.setMessage("Please Enter Bill No In Integers");
                this.setBillNo("");
                return;
            }
            if (commision.equals("") || commision == null) {
                this.setMessage("Please Enter Commision");
                return;
            }
            Matcher commisionCheck = p.matcher(commision);
            if (!commisionCheck.matches()) {
                this.setMessage("Please Enter Valid Commision");
                setCommision("");
                return;
            }
            if (postAmt.equals("") || postAmt == null) {
                this.setMessage("Please Enter Commision");
                return;
            }
            Matcher postAmtCheck = p.matcher(postAmt);
            if (!postAmtCheck.matches()) {
                this.setMessage("Please Enter Postage Amount In Integers");
                setCommision("");
                return;
            }
            if (this.realiseDate != null) {
                String s1 = onblurNomDob();
                if (!s1.equalsIgnoreCase("true")) {
                    this.setMessage(s1);
                    return;
                } else {
                    this.setMessage("");
                }
            } else {
                this.setMessage("");
            }
            if (bnkCharges.equalsIgnoreCase("")) {
                this.setMessage("Please Enter Collecting Bank Charges");
                return;
            }
            Matcher bnkChargesCheck = p.matcher(bnkCharges);
            if (!bnkChargesCheck.matches()) {
                this.setMessage("Please Enter Collecting Bank Charges In Integers");
                setBnkCharges("");
                return;
            }
            setRelsdate(ymd.format(realiseDate));
            String rsList1 = obcrealise.passClickODBCRealization(Float.parseFloat(billNo), Integer.parseInt(year), billType, Float.parseFloat(bnkCharges), Float.parseFloat(instAmt), Float.parseFloat(commision), Float.parseFloat(postAmt), credit, acNo, instNo, Relsdate, userName, orgnBrCode, instDate2, panelReason);
            setMessage(rsList1);
            gridData();
            clear1();
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void dishonorClickBtn() {
        try {
            setMessage("");
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            panelReason = "False";
            flagg = true;
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void dishonorClick() {
        try {
            incis = new ArrayList<ObcRealse>();
            setMessage("");
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");


            if (billNo.equals("") || billNo == null) {
                this.setMessage("Please Enter Bill No");
                return;
            }
            if (year.equals("--SELECT--") || year == null) {
                this.setMessage("Please Select Year");
                return;
            }
            Matcher billNoCheck = p.matcher(billNo);
            if (!billNoCheck.matches()) {
                this.setMessage("Please Enter Bill No In Integers");
                this.setBillNo("");
                return;
            }
            if (commision.equals("") || commision == null) {
                this.setMessage("Please Enter Commision");
                return;
            }
            Matcher commisionCheck = p.matcher(commision);
            if (!commisionCheck.matches()) {
                this.setMessage("Please Enter Commision In Integers");
                setCommision("");
                return;
            }
            if (postAmt.equals("") || postAmt == null) {
                this.setMessage("Please Enter Commision");
                return;
            }
            Matcher postAmtCheck = p.matcher(postAmt);
            if (!postAmtCheck.matches()) {
                this.setMessage("Please Enter Postage Amount In Integers");
                setCommision("");
                return;
            }
            if (this.realiseDate != null) {
                String s1 = onblurNomDob();
                if (!s1.equalsIgnoreCase("true")) {
                    this.setMessage(s1);
                    return;
                } else {
                    this.setMessage("");
                }
            } else {
                this.setMessage("");
            }
            if (retCharges.equals("") || retCharges == null) {
                this.setMessage("Please Enter Return Charges");
                return;
            }
            Matcher retChargesCheck = p.matcher(retCharges);
            if (!retChargesCheck.matches()) {
                this.setMessage("Please Enter Return Charges In Integers");
                setRetCharges("");
                return;
            }
            if (reason1.equals("--SELECT--") || reason1 == null) {
                this.setMessage("Please Enter Reason For Cancel.");
                return;
            }
            String rsList = obcrealise.dishonorClickOBCRealization(instNo, instDate2, Float.parseFloat(instAmt), billType, Float.parseFloat(billNo), Integer.parseInt(year), panelReason, Float.parseFloat(bnkCharges), Float.parseFloat(retCharges), reason1, acNo, Float.parseFloat(commision), Float.parseFloat(postAmt), credit, userName, orgnBrCode);
            setMessage(rsList);
            gridData();
            clear2();
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

//    public void validation(){
//     Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
//
//             if (billNo.equals("")||billNo==null) {
//                      this.setMessage("Please Enter Bill No");
//                        return;
//            }else{
//             this.setMessage("");
//            }
//             if (year.equals("--Select--")||year==null) {
//                      this.setMessage("Please Select Year");
//                        return;
//            }else{
//             this.setMessage("");
//            }
//                 Matcher billNoCheck = p.matcher(billNo);
//            if (!billNoCheck.matches()) {
//                this.setMessage("Please Enter Valid Bill No");
//                 this.setBillNo("");
//                return;
//            }else{
//             this.setMessage("");
//            }
//                  if (commision.equals("")||commision==null) {
//                      this.setMessage("Please Enter Commision");
//                        return;
//            }else{
//             this.setMessage("");
//            }
//                   Matcher commisionCheck = p.matcher(commision);
//            if (!commisionCheck.matches()) {
//                this.setMessage("Please Enter Valid Commision");
//                 setCommision("");
//                return;
//            }else{
//             this.setMessage("");
//            }
//                    if (postAmt.equals("")||postAmt==null) {
//                      this.setMessage("Please Enter Commision");
//                        return;
//            }else{
//             this.setMessage("");
//            }
//                   Matcher postAmtCheck = p.matcher(postAmt);
//            if (!postAmtCheck.matches()) {
//                this.setMessage("Please Enter Valid Postage Amount");
//                 setCommision("");
//                return;
//            }else{
//             this.setMessage("");
//            }
//                    if (this.realiseDate != null) {
//                String s1 = onblurNomDob();
//                if (!s1.equalsIgnoreCase("true")) {
//                    this.setMessage(s1);
//                    return;
//                } else {
//                    this.setMessage("");
//                }
//            } else {
//                this.setMessage("");
//            }
//
//
//    }
    public void clear() {
        flag = true;
        panelReason = "true";
        setMessage("");
        this.setBillNo("");
        setBnkCharges("");
        this.setYear("--SELECT--");
        this.setName("");
        setInstNo("");
        setInstAmt("");
        setInstDate("");
        setAcNo("");
        setCommision("");
        setPostAmt("");
        setRetCharges("");
        setReason1("--SELECT--");
        flagg = false;
    }

    public void clear1() {
        this.setBillNo("");
        this.setYear("--SELECT--");
        setBnkCharges("");
        this.setName("");
        setInstNo("");
        setInstAmt("");
        setInstDate("");
        setAcNo("");
        setCommision("");
        setPostAmt("");
    }

    public void clear2() {
        flag = true;
        this.setBillNo("");
        this.setYear("--SELECT--");
        setBnkCharges("");
        this.setName("");
        setInstNo("");
        setInstAmt("");
        setInstDate("");
        setAcNo("");
        setCommision("");
        setPostAmt("");
        setReason1("--SELECT--");
        setRetCharges("");
    }

    public String onblurNomDob() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String msg1 = "";
        try {
            List dateDif = obcrealise.dateDiff(sdf.format(this.realiseDate).toString());
            Vector vecDateDiff = (Vector) dateDif.get(0);
            String strDateDiff = vecDateDiff.get(0).toString();
            if (Integer.parseInt(strDateDiff) < 0) {
                this.setMessage("You Can't Select Date After The Current Date.");
                return msg1 = "You Can't Select Date After The Current Date.";
            } else {
                this.setMessage("");
                return msg1 = "true";
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        }
        return msg1;
    }

    public String exitForm() {
        clear();
        return "case1";
    }
}
