package com.cbs.web.controller.admin;

import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AccountMaintenanceFacadeRemote;
import com.cbs.facade.admin.AccountOpeningFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.admin.AcctEditDocumentDetailTable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

public class AccountMaintenanceRegister extends BaseBean {

    AccountMaintenanceFacadeRemote amrRemote;
    private String message;
    private String acno;
    private String chkbkissue;
    private String custname;
    private String acnumber;
    private String opermode;
    private List<SelectItem> opermodeoption;
    private String fhname;
    private String mailingadd;
    private String nominee;
    private String permadd;
    private String phoneno;
    private String aplminblchg;
    private String panno;
    private String orgtype;
    private String orgTypeDesc;
    private List<SelectItem> orgtypeOption;
    private String intrstopt;
    private List<SelectItem> docnamOption;
    private String actCategory;
    private List<SelectItem> actCategoryList;
    private String jtname1;
    private String docsnm;
    private String jtname2;
    private String docdetails;
    private String jtname3;
    private String jtname4;
    private String guradianname;
    private String acinstruction;
    private String custid1;
    private String custid2;
    private String custid3;
    private String custid4;
    private String oldAcno;
    private Date acopendt;
    private Date maturitydate;
    private String interest;
    private String amount;
    private boolean disableAmt;
    private List<AcctEditDocumentDetailTable> incis;
    private String oldCodde;
    private int minBalOpt;
    private String acopendt1;
    private String maturitydate1;
    private Date datetext1;
    private String datetext;
    //private String acno1;
    private String relnominee;
    private String addnominee;
    private Date dobnominee;
    private String dobnominee1;
    private String introAccount;
    boolean flag1;
    boolean flag2;
    boolean flag3;
    boolean flag4;
    String customerId;
    private String flag = "true";
    private boolean displayMaturityDetails = false;
    FtsPostingMgmtFacadeRemote ftsPostRemote;
    private String oldChkBookValue;
    private List<SelectItem> chkBookList;
    private List<SelectItem> intOptList;
    private List<SelectItem> aplMinBalList;
    private List<SelectItem> dmdList;
    private String dmdFlag;
    private String displayDmd = "none";
    String tdsApplicable;
    List<SelectItem> tdsApplicableList;
    String tdsDetails;
    private String displayTds = "none";
    CommonReportMethodsRemote reportMethodsLocal;
    private String hufFamily;
    private String hufFlag, acNoLen;
    // Added by Manish kumar
    private AccountOpeningFacadeRemote acOpenFacadeRemote = null;
    public String getDisplayDmd() {
        return displayDmd;
    }

    public void setDisplayDmd(String displayDmd) {
        this.displayDmd = displayDmd;
    }

    public List<SelectItem> getDmdList() {
        return dmdList;
    }

    public void setDmdList(List<SelectItem> dmdList) {
        this.dmdList = dmdList;
    }

    public String getDmdFlag() {
        return dmdFlag;
    }

    public void setDmdFlag(String dmdFlag) {
        this.dmdFlag = dmdFlag;
    }

    public List<SelectItem> getAplMinBalList() {
        return aplMinBalList;
    }

    public void setAplMinBalList(List<SelectItem> aplMinBalList) {
        this.aplMinBalList = aplMinBalList;
    }

    public List<SelectItem> getIntOptList() {
        return intOptList;
    }

    public void setIntOptList(List<SelectItem> intOptList) {
        this.intOptList = intOptList;
    }

    public List<SelectItem> getChkBookList() {
        return chkBookList;
    }

    public void setChkBookList(List<SelectItem> chkBookList) {
        this.chkBookList = chkBookList;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getOldCodde() {
        return oldCodde;
    }

    public void setOldCodde(String oldCodde) {
        this.oldCodde = oldCodde;
    }

    public boolean isDisplayMaturityDetails() {
        return displayMaturityDetails;
    }

    public void setDisplayMaturityDetails(boolean displayMaturityDetails) {
        this.displayMaturityDetails = displayMaturityDetails;
    }

    public String getIntroAccount() {
        return introAccount;
    }

    public void setIntroAccount(String introAccount) {
        this.introAccount = introAccount;
    }

    public String getAcinstruction() {
        return acinstruction;
    }

    public void setAcinstruction(String acinstruction) {
        this.acinstruction = acinstruction;
    }

    public String getAcnumber() {
        return acnumber;
    }

    public void setAcnumber(String acnumber) {
        this.acnumber = acnumber;
    }

    public Date getAcopendt() {
        return acopendt;
    }

    public void setAcopendt(Date acopendt) {
        this.acopendt = acopendt;
    }

    public String getAcopendt1() {
        return acopendt1;
    }

    public void setAcopendt1(String acopendt1) {
        this.acopendt1 = acopendt1;
    }

    public String getAddnominee() {
        return addnominee;
    }

    public void setAddnominee(String addnominee) {
        this.addnominee = addnominee;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public boolean isDisableAmt() {
        return disableAmt;
    }

    public void setDisableAmt(boolean disableAmt) {
        this.disableAmt = disableAmt;
    }

    public String getAplminblchg() {
        return aplminblchg;
    }

    public void setAplminblchg(String aplminblchg) {
        this.aplminblchg = aplminblchg;
    }

    public String getChkbkissue() {
        return chkbkissue;
    }

    public void setChkbkissue(String chkbkissue) {
        this.chkbkissue = chkbkissue;
    }

    public String getCustid1() {
        return custid1;
    }

    public void setCustid1(String custid1) {
        this.custid1 = custid1;
    }

    public String getCustid2() {
        return custid2;
    }

    public void setCustid2(String custid2) {
        this.custid2 = custid2;
    }

    public String getCustid3() {
        return custid3;
    }

    public void setCustid3(String custid3) {
        this.custid3 = custid3;
    }

    public String getCustid4() {
        return custid4;
    }

    public void setCustid4(String custid4) {
        this.custid4 = custid4;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getDatetext() {
        return datetext;
    }

    public void setDatetext(String datetext) {
        this.datetext = datetext;
    }

    public Date getDatetext1() {
        return datetext1;
    }

    public void setDatetext1(Date datetext1) {
        this.datetext1 = datetext1;
    }

    public Date getDobnominee() {
        return dobnominee;
    }

    public void setDobnominee(Date dobnominee) {
        this.dobnominee = dobnominee;
    }

    public String getDobnominee1() {
        return dobnominee1;
    }

    public void setDobnominee1(String dobnominee1) {
        this.dobnominee1 = dobnominee1;
    }

    public String getDocdetails() {
        return docdetails;
    }

    public void setDocdetails(String docdetails) {
        this.docdetails = docdetails;
    }

    public List<SelectItem> getDocnamOption() {
        return docnamOption;
    }

    public void setDocnamOption(List<SelectItem> docnamOption) {
        this.docnamOption = docnamOption;
    }

    public String getDocsnm() {
        return docsnm;
    }

    public void setDocsnm(String docsnm) {
        this.docsnm = docsnm;
    }

    public String getFhname() {
        return fhname;
    }

    public void setFhname(String fhname) {
        this.fhname = fhname;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getGuradianname() {
        return guradianname;
    }

    public void setGuradianname(String guradianname) {
        this.guradianname = guradianname;
    }

    public List<AcctEditDocumentDetailTable> getIncis() {
        return incis;
    }

    public void setIncis(List<AcctEditDocumentDetailTable> incis) {
        this.incis = incis;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getIntrstopt() {
        return intrstopt;
    }

    public void setIntrstopt(String intrstopt) {
        this.intrstopt = intrstopt;
    }

    public String getJtname1() {
        return jtname1;
    }

    public void setJtname1(String jtname1) {
        this.jtname1 = jtname1;
    }

    public String getJtname2() {
        return jtname2;
    }

    public void setJtname2(String jtname2) {
        this.jtname2 = jtname2;
    }

    public String getJtname3() {
        return jtname3;
    }

    public void setJtname3(String jtname3) {
        this.jtname3 = jtname3;
    }

    public String getJtname4() {
        return jtname4;
    }

    public void setJtname4(String jtname4) {
        this.jtname4 = jtname4;
    }

    public String getMailingadd() {
        return mailingadd;
    }

    public void setMailingadd(String mailingadd) {
        this.mailingadd = mailingadd;
    }

    public Date getMaturitydate() {
        return maturitydate;
    }

    public void setMaturitydate(Date maturitydate) {
        this.maturitydate = maturitydate;
    }

    public String getMaturitydate1() {
        return maturitydate1;
    }

    public void setMaturitydate1(String maturitydate1) {
        this.maturitydate1 = maturitydate1;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMinBalOpt() {
        return minBalOpt;
    }

    public void setMinBalOpt(int minBalOpt) {
        this.minBalOpt = minBalOpt;
    }

    public String getNominee() {
        return nominee;
    }

    public void setNominee(String nominee) {
        this.nominee = nominee;
    }

    public String getOpermode() {
        return opermode;
    }

    public void setOpermode(String opermode) {
        this.opermode = opermode;
    }

    public List<SelectItem> getOpermodeoption() {
        return opermodeoption;
    }

    public void setOpermodeoption(List<SelectItem> opermodeoption) {
        this.opermodeoption = opermodeoption;
    }

    public String getOrgtype() {
        return orgtype;
    }

    public void setOrgtype(String orgtype) {
        this.orgtype = orgtype;
    }

    public List<SelectItem> getOrgtypeOption() {
        return orgtypeOption;
    }

    public void setOrgtypeOption(List<SelectItem> orgtypeOption) {
        this.orgtypeOption = orgtypeOption;
    }

    public String getPanno() {
        return panno;
    }

    public void setPanno(String panno) {
        this.panno = panno;
    }

    public String getPermadd() {
        return permadd;
    }

    public void setPermadd(String permadd) {
        this.permadd = permadd;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getRelnominee() {
        return relnominee;
    }

    public void setRelnominee(String relnominee) {
        this.relnominee = relnominee;
    }

    public boolean isFlag1() {
        return flag1;
    }

    public void setFlag1(boolean flag1) {
        this.flag1 = flag1;
    }

    public boolean isFlag2() {
        return flag2;
    }

    public void setFlag2(boolean flag2) {
        this.flag2 = flag2;
    }

    public boolean isFlag3() {
        return flag3;
    }

    public void setFlag3(boolean flag3) {
        this.flag3 = flag3;
    }

    public boolean isFlag4() {
        return flag4;
    }

    public void setFlag4(boolean flag4) {
        this.flag4 = flag4;
    }

    public String getOldAcno() {
        return oldAcno;
    }

    public void setOldAcno(String oldAcno) {
        this.oldAcno = oldAcno;
    }

    public String getActCategory() {
        return actCategory;
    }

    public void setActCategory(String actCategory) {
        this.actCategory = actCategory;
    }

    public List<SelectItem> getActCategoryList() {
        return actCategoryList;
    }

    public void setActCategoryList(List<SelectItem> actCategoryList) {
        this.actCategoryList = actCategoryList;
    }

    public String getTdsApplicable() {
        return tdsApplicable;
    }

    public void setTdsApplicable(String tdsApplicable) {
        this.tdsApplicable = tdsApplicable;
    }

    public List<SelectItem> getTdsApplicableList() {
        return tdsApplicableList;
    }

    public void setTdsApplicableList(List<SelectItem> tdsApplicableList) {
        this.tdsApplicableList = tdsApplicableList;
    }

    public String getTdsDetails() {
        return tdsDetails;
    }

    public void setTdsDetails(String tdsDetails) {
        this.tdsDetails = tdsDetails;
    }

    public String getDisplayTds() {
        return displayTds;
    }

    public void setDisplayTds(String displayTds) {
        this.displayTds = displayTds;
    }

    public String getHufFamily() {
        return hufFamily;
    }

    public void setHufFamily(String hufFamily) {
        this.hufFamily = hufFamily;
    }

    public String getHufFlag() {
        return hufFlag;
    }

    public void setHufFlag(String hufFlag) {
        this.hufFlag = hufFlag;
    }

    public String getOrgTypeDesc() {
        return orgTypeDesc;
    }

    public void setOrgTypeDesc(String orgTypeDesc) {
        this.orgTypeDesc = orgTypeDesc;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }
    
    public AccountMaintenanceRegister() {
        try {
            amrRemote = (AccountMaintenanceFacadeRemote) ServiceLocator.getInstance().lookup("AccountMaintenanceFacade");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            reportMethodsLocal = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            acOpenFacadeRemote = (AccountOpeningFacadeRemote) ServiceLocator.getInstance().lookup("AccountOpeningFacade");
            this.setAcNoLen(getAcNoLength());
            tdsApplicableList = new ArrayList<SelectItem>();
            tdsApplicableList.add(new SelectItem("Y", "Yes"));
            tdsApplicableList.add(new SelectItem("N", "No"));

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(date));
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            setDobnominee(null);
            onLoadFields();
            setAcopendt(date);
            setMaturitydate1(ymd.format(date));
            setDatetext1(date);
            setDatetext(ymd.format(datetext1));
            reFresh();
            this.disableAmt = true;
            this.setHufFlag("true");
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void onLoadFields() {
        try {

            List resultList1 = amrRemote.onLoadList3();
            opermodeoption = new ArrayList<SelectItem>();
            opermodeoption.add(new SelectItem("0", "-- Select--"));
            for (int j = 0; j < resultList1.size(); j++) {
                Vector ele = (Vector) resultList1.get(j);
                opermodeoption.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
            }

            List resultList2 = amrRemote.onLoadList4();
            orgtypeOption = new ArrayList<SelectItem>();
            orgtypeOption.add(new SelectItem("0", "-- Select--"));
            for (int k = 0; k < resultList2.size(); k++) {
                Vector ele = (Vector) resultList2.get(k);
                orgtypeOption.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
            }

            List resultList4 = reportMethodsLocal.getActCategoryDetails();
            actCategoryList = new ArrayList<SelectItem>();
            actCategoryList.add(new SelectItem("0", "-- Select--"));
            for (int k = 0; k < resultList4.size(); k++) {
                Vector ele = (Vector) resultList4.get(k);
                actCategoryList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
            }

            List resultList3 = amrRemote.onLoadList5();
            docnamOption = new ArrayList<SelectItem>();
            docnamOption.add(new SelectItem("0", "-- Select--"));
            for (int l = 0; l < resultList3.size(); l++) {
                Vector ele = (Vector) resultList3.get(l);
                docnamOption.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
            }
            chkBookList = new ArrayList<SelectItem>();
            chkBookList.add(new SelectItem("0", "No"));
            chkBookList.add(new SelectItem("1", "Yes"));

            intOptList = new ArrayList<SelectItem>();
            intOptList.add(new SelectItem("", "-- Select--"));
            intOptList.add(new SelectItem("S", "SIMPLE"));
            intOptList.add(new SelectItem("C", "CUMULATIVE"));

            aplMinBalList = new ArrayList<SelectItem>();
            aplMinBalList.add(new SelectItem("1", "Yes"));
            aplMinBalList.add(new SelectItem("2", "No"));

            dmdList = new ArrayList<SelectItem>();
            dmdList.add(new SelectItem("1", "Yes"));
            dmdList.add(new SelectItem("0", "No"));
            if (amrRemote.dmdAmtFlag().equalsIgnoreCase("Y")) {
                setDisplayDmd("");
            } else {
                setDisplayDmd("none");
            }

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void customerInfo() {
        try {
            setMessage("");
            acno = "";
            oldChkBookValue = "";
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

            if (oldAcno == null || oldAcno.equalsIgnoreCase("")) {
                throw new ApplicationException("Please Enter Account Number");
            }
            acno = ftsPostRemote.getNewAccountNumber(oldAcno);

            String acType1 = ftsPostRemote.getAccountNature(acno);
            if ((acType1.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acType1.equalsIgnoreCase(CbsConstant.DEMAND_LOAN))) {
                this.disableAmt = false;
            } else {
                this.disableAmt = true;
            }

            if (acType1.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                this.displayTds = "";
            } else {
                this.displayTds = "none";
            }

            String result = amrRemote.custInfoEdit(acno);

            if (acType1.equalsIgnoreCase(CbsConstant.RECURRING_AC) || acType1.equalsIgnoreCase(CbsConstant.TERM_LOAN)
                    || acType1.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                flag = "false";
            } else {
                flag = "false";
            }
            String spliter = ":~";
            String[] values = result.split(spliter);
            if (values[0].equalsIgnoreCase("9")) {
                flag = "true";
                setMessage("This Account has been closed.");
            } else {
                flag = "false";
                setMessage("");
            }

            setIntrstopt(values[1]);
            setFhname(values[2]);
            setCustname(values[3]);

            setChkbkissue(values[4]);
            oldChkBookValue = values[4];

            setMailingadd(values[5]);
            setPermadd(values[6]);
            setPhoneno(values[7]);

            setPanno(values[8]);
            setNominee(values[9]);
            setAcinstruction(values[11]);

            String str = values[12];
            setAcopendt(sdf.parse(str));
            setAcopendt1(ymd.format(acopendt));

            String str1 = values[13];
            if (str1 == null || str1.equalsIgnoreCase("")) {
                Date date = new Date();
                setMaturitydate(date);
                displayMaturityDetails = false;
            } else {
                setMaturitydate(sdf.parse(str1));
                if (acType1.equalsIgnoreCase(CbsConstant.RECURRING_AC) || acType1.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                    displayMaturityDetails = true;
                } else {
                    displayMaturityDetails = false;
                }
            }
            setMaturitydate1(ymd.format(maturitydate));

            setInterest(values[16]);
            setAplminblchg(values[15]);

            setAmount(values[14]);
            setGuradianname(values[17]);

            setOldCodde(values[18]);
            setOpermode(values[19]);
            if(values[20] == null || values[20].equals("")) setOrgtype("26"); else setOrgtype(values[20]);
            orgTypeDesc = acOpenFacadeRemote.getRecRefDiscription("021", this.getOrgtype());
            setJtname1(values[21]);
            setJtname2(values[22]);
            setJtname3(values[23]);
            setJtname4(values[24]);

            setRelnominee(values[10]);
            setAddnominee(values[26]);
            String dob = values[27];

            if (!dob.equals("")) {
                setDobnominee(sdf.parse(dob));
            }

            setCustid1(values[28]);
            setCustid2(values[29]);
            setCustid3(values[30]);
            setCustid4(values[31]);

            customerId = values[32];
            setDmdFlag(values[33]);
            setActCategory(values[34]);
            if (acType1.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                setTdsApplicable(values[35]);
            }
            if(values[19].equalsIgnoreCase("18")){
                setHufFlag("false");
            }else{
                setHufFlag("true");
            }
            if(values[36].equalsIgnoreCase("Blank")){
                setHufFamily("");
            }else{
                setHufFamily(values[36]);
            }
            setDocsnm("");
            griddata(acno);
            setAcnumber(acno);         

        } catch (Exception e) {
            setMessage(e.getMessage());
            incis.clear();
        }
    }

    public void OnblurOfjname1() {
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            setMessage("");
            if ((custid1.equals("")) || (custid1 == null)) {
                throw new ApplicationException("Please Enter Customer Id For JT Name 1.");
            }
            Matcher Jt2CustomerIdCheck = p.matcher(custid1);
            if (!Jt2CustomerIdCheck.matches()) {
                throw new ApplicationException("JTName1 ID  should be Numerical.");
            }
            if (customerId.equalsIgnoreCase(custid1)) {
                throw new ApplicationException("JtName1 ID And Customer Id can not be Same");
            }
            setJtname1(amrRemote.customerId(custid1));
        } catch (Exception e) {
            setCustid1("");
            setJtname1("");
            setMessage(e.getMessage());
        }
    }

    public void OnblurOfjname2() {
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            setMessage("");
            if ((custid2.equals("")) || (custid2 == null)) {
                throw new ApplicationException("Please Enter Customer Id For JT Name 2.");
            }
            Matcher Jt2CustomerIdCheck = p.matcher(custid2);
            if (!Jt2CustomerIdCheck.matches()) {
                throw new ApplicationException("JTName2 ID  should be Numerical.");
            }
            if (customerId.equalsIgnoreCase(custid2)) {
                throw new ApplicationException("JtName2 ID And Customer Id can not be Same");
            }
            if (this.custid1.equalsIgnoreCase(this.custid2)) {
                throw new ApplicationException("TWO JOINT HOLDERS NAME CANNOT BE SAME !!!");
            }
            setJtname2(amrRemote.customerId(custid2));
        } catch (Exception e) {
            setCustid2("");
            setJtname2("");
            setMessage(e.getMessage());
        }
    }

    public void OnblurOfjname3() {
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            setMessage("");
            if ((custid3.equals("")) || (custid3 == null)) {
                throw new ApplicationException("Please Enter Customer Id For JT Name 3.");
            }
            Matcher Jt2CustomerIdCheck = p.matcher(custid3);
            if (!Jt2CustomerIdCheck.matches()) {
                throw new ApplicationException("JTName3 ID  should be Numerical.");
            }
            if (customerId.equalsIgnoreCase(custid3)) {
                throw new ApplicationException("JtName3 ID And Customer Id can not be Same");
            }
            if (this.custid1.equalsIgnoreCase(this.custid3) || this.custid2.equalsIgnoreCase(this.custid3)) {
                throw new ApplicationException("JOINT HOLDERS NAME CANNOT BE SAME !!!");
            }
            setJtname3(amrRemote.customerId(custid3));

        } catch (Exception e) {
            setCustid3("");
            setJtname3("");
            setMessage(e.getMessage());
        }
    }

    public void OnblurOfjname4() {
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            setMessage("");
            if ((custid4.equals("")) || (custid4 == null)) {
                throw new ApplicationException("Please Enter Customer Id For JT Name 4.");
            }
            Matcher Jt2CustomerIdCheck = p.matcher(custid4);
            if (!Jt2CustomerIdCheck.matches()) {
                throw new ApplicationException("JTName4 ID  should be Numerical.");
            }
            if (customerId.equalsIgnoreCase(custid4)) {
                throw new ApplicationException("JtName3 ID And Customer Id can not be Same");
            }
            if (this.custid1.equalsIgnoreCase(this.custid4) || this.custid2.equalsIgnoreCase(this.custid4) || this.custid3.equalsIgnoreCase(this.custid4)) {
                throw new ApplicationException("JOINT HOLDERS NAME CANNOT BE SAME !!!");
            }
            setJtname4(amrRemote.customerId(custid4));
        } catch (Exception e) {
            setCustid4("");
            setJtname4("");
            setMessage(e.getMessage());
        }
    }

    public void griddata(String acno) {
        try {
            incis = new ArrayList<AcctEditDocumentDetailTable>();
            List list = amrRemote.grid(acno);
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector v = (Vector) list.get(i);
                    AcctEditDocumentDetailTable tab = new AcctEditDocumentDetailTable();
                    tab.setDescription(v.get(0).toString());
                    tab.setDocudetails(v.get(1).toString());
                    incis.add(tab);
                }
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void updateData() {
        try {
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            if (oldAcno == null || oldAcno.equalsIgnoreCase("")) {
                this.setMessage("Please Enter Account Number");
                incis.clear();
                return;
            } else if (!ftsPostRemote.getCurrentBrnCode(acno).equalsIgnoreCase(getOrgnBrCode())) {
                setMessage("This is not your branch's account no.");
                incis.clear();
                return;
            }
            if (!this.nominee.trim().equalsIgnoreCase("")) {
                if (!this.nominee.matches("[a-zA-z. ]*")) {
                    this.setMessage("Fill Nominee as Characters.Please Do Not Use Like(.,%#@!etc)");
                    return;
                } else {
                    this.setMessage("");
                }
            } else {
                this.setMessage("");
            }
            if (!this.relnominee.equalsIgnoreCase("")) {
                if (!this.relnominee.matches("[a-zA-z]+([ '-][a-zA-Z]+)*")) {
                    this.setMessage("Fill Relationship With Nominee as Characters.Please Do Not Use Like(.,%#@!etc)");
                    return;
                } else {
                    this.setMessage("");
                }
            } else {
                this.setMessage("");
            }
            if (!this.guradianname.trim().equalsIgnoreCase("")) {
                if (!this.guradianname.matches("[a-zA-z]+([ '-][a-zA-Z]+)*")) {
                    this.setMessage("Fill Guardian Name as Characters.Please Do Not Use Like(.,%#@!etc)");
                    return;
                } else {
                    this.setMessage("");
                }
            } else {
                this.setMessage("");
            }
            if (this.dobnominee == null) {
                setDobnominee1(ymd.format(ymd.parse("19000101")));
            } else {
                if (this.dobnominee != null && dobnominee.getTime() > new Date().getTime()) {
                    throw new ApplicationException("Nominee dob must be less than current date.");
                }
                setDobnominee1(ymd.format(dobnominee));
            }
            if (opermode.equals("1")) {
                setJtname1("");
                setJtname2("");
                setJtname3("");
                setJtname4("");
                setCustid1("");
                setCustid2("");
                setCustid3("");
                setCustid4("");
            } else if (opermode.equals("5")) {
                if ((jtname1.equals("")) || (jtname1 == null)) {
                    this.setMessage("Please Enter Jtname1 ");
                    return;
                }
                if ((jtname2.equals("")) || (jtname2 == null)) {
                    this.setMessage("Please Enter Jtname2 ");
                    return;
                }
            } else if (opermode.equals("6")) {
                if ((jtname1.equals("")) || (jtname1 == null)) {
                    this.setMessage("Please Enter Jtname1 ");
                    return;
                }
                if ((jtname2.equals("")) || (jtname2 == null)) {
                    this.setMessage("Please Enter Jtname2 ");
                    return;
                }

                if ((jtname3.equals("")) || (jtname3 == null)) {
                    this.setMessage("Please Enter Jtname3 ");
                    return;
                }
            } else if (opermode.equals("16")) {
                if ((jtname1.equals("")) || (jtname1 == null)) {
                    this.setMessage("Please Enter Jtname1 ");
                    return;
                }
                if ((jtname2.equals("")) || (jtname2 == null)) {
                    this.setMessage("Please Enter Jtname2 ");
                    return;
                }
                if ((jtname3.equals("")) || (jtname3 == null)) {
                    this.setMessage("Please Enter Jtname3 ");
                    return;
                }
                if ((jtname4.equals("")) || (jtname4 == null)) {
                    this.setMessage("Please Enter Jtname4 ");
                    return;
                }
            }
            
            if(actCategory.equalsIgnoreCase("HUF") || opermode.equalsIgnoreCase("18")){
                if(!(actCategory.equalsIgnoreCase("HUF") && opermode.equalsIgnoreCase("18"))){
                    this.setMessage("Please Select Proper Account Category And Operation Mode");
                    return;
                }
            }
            
            Integer nomAge = 0;
            String statusNom = "";
            if (!nominee.equalsIgnoreCase("") || nominee.length() != 0) {
                if (dobnominee != null) {
                    SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
                    String nomineeAge = sdf1.format(dobnominee);
                    int yearDOB = Integer.parseInt(nomineeAge.substring(6));

                    DateFormat dateFormat = new SimpleDateFormat("yyyy");
                    java.util.Date date1 = new java.util.Date();
                    int thisYear = Integer.parseInt(dateFormat.format(date1));
                    nomAge = thisYear - yearDOB;
                    if (dobnominee.equals(sdf1.parse(getTodayDate()))) {
                        statusNom = "";
                    } else if (nomAge >= 18) {
                        statusNom = "N";
                    } else if (nomAge < 18) {
                        statusNom = "Y";
                    }
                }
            }
            if (interest.equalsIgnoreCase("")) {
                interest = "0.0";
            }
            if (amount.equalsIgnoreCase("")) {
                amount = "0.0";
            }
            if (incis.size() <= 0) {
                if (!docsnm.equals("0")) {
                    if (docdetails.equalsIgnoreCase("") || docdetails == null) {
                        this.setMessage("Please enter the document details");
                        return;
                    }
                } else {
                    if (introAccount == null || introAccount.equalsIgnoreCase("")) {
                        this.setMessage("Please enter either introducer account No or document details.");
                        return;
                    }
                }
            }
            
            if(opermode.equalsIgnoreCase("18")){
                if ((jtname1.equals("")) || (jtname1 == null)) {
                    this.setMessage("Jtname1 Can't Be Blank In Case Of HUF Account.");
                    return;
                }
                
                if ((hufFamily.equals("")) || (hufFamily == null)) {                
                    this.setMessage("Huf Family Detail Can't Be Blank In Case Of HUF Account.");
                    return;
                }
            }
            
            String rs = amrRemote.updateAcctOpenEdit(acno, custname, acOpenFacadeRemote.removeSomeSpecialChar(mailingadd), acOpenFacadeRemote.removeSomeSpecialChar(permadd), phoneno, Integer.parseInt(orgtype), Integer.parseInt(opermode), Integer.parseInt(docsnm),
                    docdetails, panno, guradianname, fhname, introAccount, jtname1, jtname2, nominee, relnominee, jtname3, jtname4,
                    Float.parseFloat(interest), Float.parseFloat(amount), acopendt1, maturitydate1, acinstruction, Integer.parseInt(aplminblchg), Integer.parseInt(chkbkissue),
                    intrstopt, datetext, getUserName(), getOrgnBrCode(), addnominee, relnominee, dobnominee1, statusNom, nomAge, custid1, custid2, custid3, custid4, Integer.parseInt(dmdFlag), actCategory, tdsApplicable, hufFamily);
            setMessage(rs);
            setCustname("");
            setFhname("");
            setMailingadd("");
            setPermadd("");
            setPhoneno("");
            setPanno("");
            setIntrstopt("");
            setDocsnm("0");
            setDocdetails("");
            setAcnumber("");
            setOpermode("0");
            setNominee("");
            setAplminblchg("1");
            setChkbkissue("1");
            setOrgtype("0");
            setActCategory("0");
            setJtname1("");
            setJtname2("");
            setJtname3("");
            setJtname4("");
            setCustid1("");
            setCustid2("");
            setCustid3("");
            setCustid4("");
            setGuradianname("");
            setAcinstruction("");
            setOldCodde("");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setAcopendt(sdf.parse(getTodayDate()));
            setMaturitydate(sdf.parse(getTodayDate()));
            setDobnominee(null);
            setAddnominee("");
            setRelnominee("");
            setInterest("");
            setAmount("");
            setIntroAccount("");
            flag = "true";
            incis = new ArrayList<AcctEditDocumentDetailTable>();
            this.setHufFamily("");
            this.setHufFlag("true");
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void onblurNomDob() {
        try {
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void reFresh() {
        try {
            incis = new ArrayList<AcctEditDocumentDetailTable>();
            flag = "true";
            setOldAcno("");
            setMessage("");
            setCustname("");
            setFhname("");
            setMailingadd("");
            setPermadd("");
            setPhoneno("");
            setPanno("");
            setIntrstopt("");
            setDocsnm("0");
            setDocdetails("");
            setAcnumber("");
            setOpermode("0");
            setNominee("");
            setAplminblchg("1");
            setChkbkissue("1");
            setOrgtype("0");
            setJtname1("");
            setJtname2("");
            setJtname3("");
            setJtname4("");
            setCustid1("");
            setCustid2("");
            setCustid3("");
            setCustid4("");
            setGuradianname("");
            setAcinstruction("");
            setOldCodde("");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setAcopendt(sdf.parse(getTodayDate()));
            setMaturitydate(sdf.parse(getTodayDate()));
            setDobnominee(null);
            setInterest("");
            setAmount("");
            setAddnominee("");
            setRelnominee("");
            setIntroAccount("");
            acno = "";
            oldChkBookValue = "";
            setActCategory("0");
            this.displayTds = "none";
            displayMaturityDetails = false;
            this.setHufFamily("");
            this.setHufFlag("true");
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    public void onblurOperatingMode() {
        try {
            setMessage("");

            this.setCustid1("");
            this.setJtname1("");

            this.setCustid2("");
            this.setJtname2("");

            this.setCustid3("");
            this.setJtname3("");

            this.setCustid4("");
            this.setJtname4(""); 
            if(!opermode.equals("18")){
                this.setHufFlag("true");
                this.setHufFamily("");
            }

            if (this.opermode.equals("1")) {
                flag1 = true;
                flag2 = true;
                flag3 = true;
                flag4 = true;
            } else if (this.opermode.equals("4")) {
                flag1 = false;
                flag2 = false;
                flag3 = false;
                flag4 = false;
            } else if (this.opermode.equals("5")) {
                flag1 = false;
                flag2 = false;
                flag3 = true;
                flag4 = true;
            } else if (this.opermode.equals("6")) {
                flag1 = false;
                flag2 = false;
                flag3 = false;
                flag4 = true;
            } else if (this.opermode.equals("14")) {
                flag1 = false;
                flag2 = false;
                flag3 = false;
                flag4 = false;
            } else if (this.opermode.equals("15")) {
                flag1 = false;
                flag2 = false;
                flag3 = false;
                flag4 = false;
            } else if (this.opermode.equals("16")) {
                flag1 = false;
                flag2 = false;
                flag3 = false;
                flag4 = false;
            } else if (this.opermode.equals("2")) {
                flag1 = false;
                flag2 = true;
                flag3 = true;
                flag4 = true;
            } else if (this.opermode.equals("18")) {
                flag1 = false;
                flag2 = true;
                flag3 = true;
                flag4 = true;
                this.setHufFlag("false");
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getIntroducerAcctDetails() {
        try {
            setMessage("");
            setIntroAccount("");
            if (incis.size() <= 0 && docsnm.equals("") && docdetails.equalsIgnoreCase("")) {
                if (oldCodde.equalsIgnoreCase("") || oldCodde == null) {
                    throw new ApplicationException("Please Enter Introducer Account No");
                }
            }
            if (!oldCodde.equalsIgnoreCase("") || oldCodde != null) {
                String introAcNo = ftsPostRemote.getNewAccountNumber(oldCodde);
                String resultStr = amrRemote.introducerAcDetail(introAcNo);
                setIntroAccount(resultStr);
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
            setIntroAccount("");
            setOldCodde("");
        }
    }

    public void setchkBookIssue() {
        try {
            String staus= "F";
            String acNature = reportMethodsLocal.getAcNatureByAcNo(this.acno);
            if(acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC) || acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)){
            List statusList = acOpenFacadeRemote.getChbookStatusList(this.acno, acNature);
            if(!statusList.isEmpty()){
                if(statusList.contains(staus)){
                  throw new ApplicationException("You cannot change the cheque book issue option from Yes to No"); 
                }
            }
          }
//            if (oldChkBookValue.equals("1") && chkbkissue.equals("0")) {
//                throw new ApplicationException("You cannot change the cheque book issue option from Yes to No");
//            }
        } catch (Exception e) {
            setMessage(e.getMessage());
            chkbkissue = "1";
        }
    }

    public String exitFrm() {
        try {
            reFresh();
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
        return "case1";
    }
}
