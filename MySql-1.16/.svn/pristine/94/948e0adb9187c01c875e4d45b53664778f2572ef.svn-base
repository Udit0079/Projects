package com.cbs.web.controller.misc;

import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.misc.AcctEnqueryFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.misc.CustomerList;
import java.math.BigDecimal;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;
import java.util.ArrayList;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;

public class CustomerSearch extends BaseBean {

    Date date;
    SimpleDateFormat sdf;
    InitialContext ctx;
    AcctEnqueryFacadeRemote remoteObject;
    private String searchList;
    private String flag1;
    private String flag2;
    private String flag3;
    private String flag4;
    private String custFatherflag;
    private String todayDate;
    private String paramValue;
    private String custName;
    private String fatherName;
    private String userName, acNoLen;
    private List<CustomerList> customerDetailList;
    private CustomerList currentDetail;
    private int currentRow;
    private String msg;
    private HttpServletRequest req;
    private final String FtsPostingMgmtFacadeJndiName = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote fts = null;
    private CommonReportMethodsRemote commanRemot = null;
    private LoanReportFacadeRemote lrfRemote;
    private String newAcno;
    private String status;

    public String getCustFatherflag() {
        return custFatherflag;
    }

    public void setCustFatherflag(String custFatherflag) {
        this.custFatherflag = custFatherflag;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getNewAcno() {
        return newAcno;
    }

    public void setNewAcno(String newAcno) {
        this.newAcno = newAcno;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getFlag1() {
        return flag1;
    }

    public void setFlag1(String flag1) {
        this.flag1 = flag1;
    }

    public String getFlag2() {
        return flag2;
    }

    public void setFlag2(String flag2) {
        this.flag2 = flag2;
    }

    public String getFlag3() {
        return flag3;
    }

    public void setFlag3(String flag3) {
        this.flag3 = flag3;
    }

    public String getSearchList() {
        return searchList;
    }

    public void setSearchList(String searchList) {
        this.searchList = searchList;
    }

    public String getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }

    public CustomerList getCurrentDetail() {
        return currentDetail;
    }

    public void setCurrentDetail(CustomerList currentDetail) {
        this.currentDetail = currentDetail;
    }

    public List<CustomerList> getCustomerDetailList() {
        return customerDetailList;
    }

    public void setCustomerDetailList(List<CustomerList> customerDetailList) {
        this.customerDetailList = customerDetailList;
    }

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFlag4() {
        return flag4;
    }

    public void setFlag4(String flag4) {
        this.flag4 = flag4;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public CustomerSearch() {
        try {
            req = getRequest();
            remoteObject = (AcctEnqueryFacadeRemote) ServiceLocator.getInstance().lookup("AcctEnqueryFacade");
            fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(FtsPostingMgmtFacadeJndiName);
            commanRemot = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            lrfRemote = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup("LoanReportFacade");
            this.setAcNoLen(getAcNoLength());
            date = new Date();
            sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(date));
            setUserName(req.getRemoteUser());
        } catch (ApplicationException e) {
            setMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public void onblurSessionCriteria() {
        try {
            msg = "";
            custFatherflag = "false";
            if (this.getSearchList().equalsIgnoreCase("Account Number")) {
                flag1 = "true";
            } else if (this.getSearchList().equalsIgnoreCase("Customer Name")) {
                flag2 = "true";
            } else if (this.getSearchList().equalsIgnoreCase("Customer ID")) {
                flag3 = "true";
            } else if (this.getSearchList().equalsIgnoreCase("Joint Customer Name")) {
                flag4 = "true";
            } else if (this.getSearchList().equalsIgnoreCase("PAN Number")) {
                flag4 = "true";
            } else if (this.getSearchList().equalsIgnoreCase("AADHAAR (UIDAI)")) {
                flag4 = "true";
            } else if (this.getSearchList().equalsIgnoreCase("P.Token No.")) {
                flag3 = "true";
            } else if (this.getSearchList().equalsIgnoreCase("Customer Name & Father Name")) {
                custFatherflag = "true";
            } else if (this.getSearchList().equalsIgnoreCase("Mobile No.")) {
                flag3 = "true";
            } else {
                msg = "Select One Option !!";
            }
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public void reset() {
        msg = "";
        flag1 = "false";
        flag2 = "false";
        flag3 = "false";
        custFatherflag = "false";
        searchList = "--SELECT--";
        status = "--SELECT--";
        paramValue = "";
        custName = "";
        fatherName = "";
        newAcno = "";
        //currentDetail.setAccNo("");
        customerDetailList = new ArrayList<CustomerList>();

    }

    public void getDetail() {
        List resultList = null;
        int i;
        try {
            newAcno = "";
            customerDetailList = new ArrayList<CustomerList>();
            if (status.equalsIgnoreCase("--SELECT--") || status == null) {
                msg = "Please select status !!";
                return;
            }
            if (!this.searchList.endsWith("Customer Name & Father Name")) {
                if (paramValue.equals("")) {
                    msg = "Please Enter Some Value !!";
                    return;
                }
            } else {
                if (custName.equals("")) {
                    msg = "Please Enter Some Value !!";
                    return;
                }
            }
            //    currentDetail.setAccNo(paramValue);
            if ((this.searchList.equals("Account Number")) || (this.searchList.equals("Customer Name")) || (this.searchList.equals("Customer ID"))
                    || (this.searchList.equals("PAN Number")) || (this.searchList.equals("Joint Customer Name"))
                    || (this.searchList.equals("AADHAAR (UIDAI)")) || (this.searchList.equals("P.Token No."))
                    || (this.searchList.equals("Customer Name & Father Name")) || (this.searchList.equals("Mobile No."))) {
                msg = "";
                if (this.searchList.equals("Account Number")) {

//                    paramValue = fts.getNewAccountNumber(paramValue);
//                    newAcno = paramValue;

                    if (this.paramValue == null || this.paramValue.equalsIgnoreCase("") || this.paramValue.length() == 0) {
                        this.setMsg("Please Enter the Account No.");
                        return;
                    }
                    if (!paramValue.matches("[0-9a-zA-z]*")) {
                        this.setMsg("Please Enter Valid  Account No.");
                        return;
                    }
                    //if (paramValue.length() != 12) {
                    if (!this.paramValue.equalsIgnoreCase("") && ((this.paramValue.length() != 12) && (this.paramValue.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                        this.setMsg("Account number is not valid.");
                        return;
                    }
                    paramValue = fts.getNewAccountNumber(paramValue);
                    newAcno = paramValue;
                    if ((paramValue.length() == 12) || (this.paramValue.length() == (Integer.parseInt(this.getAcNoLen())))) {
                        resultList = remoteObject.getData1(this.paramValue, status);
                    } else {
                        currentDetail.setCustID("");
                        currentDetail.setAccNo("");
                        currentDetail.setCustName("");
                        currentDetail.setDob("");
                        currentDetail.setfName("");
                        currentDetail.setmName("");
                        currentDetail.setPhone("");
                        currentDetail.setPan_gir("");
                        currentDetail.setPermAdd("");
                        currentDetail.setMailAdd("");
                        currentDetail.setJtName1("");
                        currentDetail.setJtName2("");
                        currentDetail.setJtName3("");
                        currentDetail.setJtName4("");
                        currentDetail.setJtCustId1("");
                        currentDetail.setJtCustId2("");
                        currentDetail.setJtCustId3("");
                        currentDetail.setJtCustId4("");
                        customerDetailList = null;

                        currentRow = 0;
                        msg = "Please Enter Valid 12 Digit Account Number !!";
                        flag2 = "false";
                        //return null;
                    }
                } else if (this.searchList.equals("Customer Name")) {
                    newAcno = "";
                    resultList = remoteObject.getData2(this.paramValue, status);
                } else if (this.searchList.equals("Customer Name & Father Name")) {
                    newAcno = "";
                    resultList = remoteObject.getData5(this.custName, this.fatherName, status);
                } else if (this.searchList.equals("Customer ID")) {
                    newAcno = "";
                    resultList = remoteObject.getData3(this.paramValue, "id", status);
                } else if (this.searchList.equals("PAN Number")) {
                    newAcno = "";
                    if (paramValue.length() != 10) {
                        this.setMsg("Please enter valid PAN");
                        return;
                    }
                    resultList = remoteObject.getData3(this.paramValue, "pan", status);
                } else if (this.searchList.equals("AADHAAR (UIDAI)")) {
                    newAcno = "";
                    resultList = remoteObject.getData3(this.paramValue, "AADHAAR", status);
                } else if (this.searchList.equals("Joint Customer Name")) {
                    newAcno = "";
                    resultList = remoteObject.getData4(this.paramValue, status);
                } else if (this.searchList.equals("P.Token No.")) {
                    newAcno = "";
                    resultList = remoteObject.getData3(this.paramValue, "TokenNo", status);
                } else if (this.searchList.equals("Mobile No.")) {
                    newAcno = "";
                    resultList = remoteObject.getData3(this.paramValue, "MobileNo", status);
                }

                if (resultList.size() >= 1) {
                    if (this.searchList.equals("Joint Customer Name")) {
                        for (i = 0; i < resultList.size(); i++) {
                            Vector val = (Vector) resultList.get(i);
                            currentDetail = new CustomerList();
                            currentDetail.setCurrentRow(i + 1);

                            //account number
                            if (val.get(0) == null || val.get(0).toString().equalsIgnoreCase("")) {
                                currentDetail.setAccNo("NA");
                            } else {
                                currentDetail.setAccNo(val.get(0).toString());
                            }

                            //customer name
                            if (val.get(1) == null || val.get(1).toString().equalsIgnoreCase("")) {
                                currentDetail.setCustName("NA");
                            } else {
                                currentDetail.setCustName(val.get(1).toString());
                            }

                            //fathername
                            if (val.get(2) == null || val.get(2).toString().trim().equalsIgnoreCase("")) {
                                currentDetail.setfName("NA");
                            } else {
                                currentDetail.setfName(val.get(2).toString().trim());
                            }

                            //DateOfBirth
                            if (val.get(3) == null || val.get(3).toString().equalsIgnoreCase("")) {
                                currentDetail.setDob("NA");
                            } else {
                                currentDetail.setDob(val.get(3).toString());
                            }

                            //MailAddressLine1
                            if (val.get(4) == null || val.get(4).toString().equalsIgnoreCase("")) {
                                currentDetail.setMailAdd("NA");
                            } else {
                                currentDetail.setMailAdd(val.get(4).toString());
                            }


                            if (val.get(5) == null || val.get(5).toString().equalsIgnoreCase("")) {
                                currentDetail.setJtName1("NA");
                            } else {
                                currentDetail.setJtName1(val.get(5).toString());
                            }


                            if (val.get(6) == null || val.get(6).toString().equalsIgnoreCase("")) {
                                currentDetail.setJtName2("NA");
                            } else {
                                currentDetail.setJtName2(val.get(6).toString());
                            }

                            if (val.get(7) == null || val.get(7).toString().equalsIgnoreCase("")) {
                                currentDetail.setJtName3("NA");
                            } else {
                                currentDetail.setJtName3(val.get(7).toString());
                            }

                            if (val.get(8) == null || val.get(8).toString().equalsIgnoreCase("")) {
                                currentDetail.setJtName4("NA");
                            } else {
                                currentDetail.setJtName4(val.get(8).toString());
                            }

                            if (val.get(9) == null || val.get(9).toString().equalsIgnoreCase("")) {
                                currentDetail.setJtCustId1("NA");
                            } else {
                                currentDetail.setJtCustId1(val.get(9).toString());
                            }

                            if (val.get(10) == null || val.get(10).toString().equalsIgnoreCase("")) {
                                currentDetail.setJtCustId2("NA");
                            } else {
                                currentDetail.setJtCustId2(val.get(10).toString());
                            }

                            if (val.get(11) == null || val.get(11).toString().equalsIgnoreCase("")) {
                                currentDetail.setJtCustId3("NA");
                            } else {
                                currentDetail.setJtCustId3(val.get(11).toString());
                            }

                            if (val.get(12) == null || val.get(12).toString().equalsIgnoreCase("")) {
                                currentDetail.setJtCustId4("NA");
                            } else {
                                currentDetail.setJtCustId4(val.get(12).toString());
                            }
                            if (val.get(13) == null || val.get(13).toString().equalsIgnoreCase("")) {
                                currentDetail.setAccStatus("NA");
                            } else {
                                currentDetail.setAccStatus(val.get(13).toString());
                            }
                            double bal = 0;
                            if (val.get(1).toString().length() == 12 && !val.get(1).toString().substring(0, 4).equals(CbsConstant.SHARE_AC)) {
                                bal = commanRemot.getBalanceOnDate(val.get(0).toString(), new SimpleDateFormat("yyyyMMdd").format(date));
                            }

                            BigDecimal amount = new BigDecimal(bal).setScale(2, BigDecimal.ROUND_HALF_UP);
                            currentDetail.setAccBal(amount.toString());
                            customerDetailList.add(currentDetail);
                        }
                        currentRow = i;
                    } else {
                        for (i = 0; i < resultList.size(); i++) {
                            Vector val = (Vector) resultList.get(i);
                            currentDetail = new CustomerList();
                            currentDetail.setCurrentRow(i + 1);
                            //customer id
                            if (val.get(0) == null || val.get(0).toString().equalsIgnoreCase("")) {
                                currentDetail.setCustID("NA");
                            } else {
                                currentDetail.setCustID(val.get(0).toString());
                            }

                            //account number
                            if (val.get(1) == null || val.get(1).toString().equalsIgnoreCase("")) {
                                currentDetail.setAccNo("NA");
                            } else {
                                currentDetail.setAccNo(val.get(1).toString());
                            }

                            //customer name
                            if (val.get(2) == null || val.get(2).toString().equalsIgnoreCase("")) {
                                currentDetail.setCustName("NA");
                            } else {
                                String custname = val.get(2).toString();
                                String middleName = val.get(10).toString();
                                String lastName = val.get(11).toString();

                                custname = custname.trim() + " " + middleName.trim();
                                custname = custname.trim() + " " + lastName.trim();


                                currentDetail.setCustName(custname);
                            }

                            //DateOfBirth
                            if (val.get(3) == null || val.get(3).toString().equalsIgnoreCase("")) {
                                currentDetail.setDob("NA");
                            } else {
                                currentDetail.setDob(sdf.format(val.get(3)).toString());
                            }

                            //fathername
                            if (val.get(4) == null || val.get(4).toString().trim().equalsIgnoreCase("")) {
                                currentDetail.setfName("NA");
                            } else {
                                currentDetail.setfName(val.get(4).toString().trim());
                            }

                            //mothername
                            if (val.get(5) == null || val.get(5).toString().trim().equalsIgnoreCase("")) {
                                currentDetail.setmName("NA");
                            } else {
                                currentDetail.setmName(val.get(5).toString().trim());
                            }

                            //mobilenumber
                            if (val.get(6) == null || val.get(6).toString().equalsIgnoreCase("")) {
                                currentDetail.setPhone("NA");
                            } else {
                                currentDetail.setPhone(val.get(6).toString());
                            }

                            //PAN_GIRNumber
                            if (val.get(7) == null || val.get(7).toString().equalsIgnoreCase("")) {
                                currentDetail.setPan_gir("NA");
                            } else {
                                currentDetail.setPan_gir(val.get(7).toString());
                            }

                            //PerAddressLine1
                            if (val.get(8) == null || val.get(8).toString().equalsIgnoreCase("")) {
                                currentDetail.setPermAdd("NA");
                            } else {
                                currentDetail.setPermAdd(val.get(8).toString());
                            }

                            //MailAddressLine1
                            if (val.get(9) == null || val.get(9).toString().equalsIgnoreCase("")) {
                                currentDetail.setMailAdd("NA");
                            } else {
                                currentDetail.setMailAdd(val.get(9).toString());
                            }
                            //account Status
                            if (val.get(12) == null || val.get(12).toString().equalsIgnoreCase("")) {
                                currentDetail.setAccStatus("NA");
                            } else {
                                currentDetail.setAccStatus(val.get(12).toString());
                            }
                            double bal = 0;
                            if (val.get(1).toString().length() == 12 && !val.get(1).toString().substring(0, 4).equals(CbsConstant.SHARE_AC)) {
                                bal = commanRemot.getBalanceOnDate(val.get(1).toString(), new SimpleDateFormat("yyyyMMdd").format(date));
                            } else {
                                bal = lrfRemote.getshareBal(val.get(1).toString(), new SimpleDateFormat("yyyyMMdd").format(date));
                            }
                            BigDecimal amount = new BigDecimal(bal).setScale(2, BigDecimal.ROUND_HALF_UP);
                            currentDetail.setAccBal(amount.toString());
                            customerDetailList.add(currentDetail);
                        }
                        currentRow = i;
                    }
                } else if (resultList == null || resultList.isEmpty()) {
                    msg = "No Results Found !!";
                    currentDetail = new CustomerList();
                    currentDetail.setCustID("");
                    currentDetail.setAccNo("");
                    currentDetail.setCustName("");
                    currentDetail.setDob("");
                    currentDetail.setfName("");
                    currentDetail.setmName("");
                    currentDetail.setPhone("");
                    currentDetail.setPan_gir("");
                    currentDetail.setPermAdd("");
                    currentDetail.setMailAdd("");
                    currentDetail.setJtName1("");
                    currentDetail.setJtName2("");
                    currentDetail.setJtName3("");
                    currentDetail.setJtName4("");
                    currentDetail.setJtCustId1("");
                    currentDetail.setJtCustId2("");
                    currentDetail.setJtCustId3("");
                    currentDetail.setJtCustId4("");
                    customerDetailList = null;
                    currentRow = 0;
                }
            }
        } catch (ApplicationException e) {
            setMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }

    }

    public String exitAction() {
        reset();
        return "case1";
    }
}
