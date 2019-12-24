/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.loan;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.loan.LoanGenralFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.pojo.loan.DemandMarkingTable;
import com.hrms.web.utils.WebUtil;
import java.awt.event.ActionEvent;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author root
 */
public class DemandMarkingManaged {

    String todayDate;
    HttpServletRequest request;
    String userName;
    String orgBrnCode;
    String demPrinAmt = "0";
    String demIntAmt = "0";
    String demChg = "0";
    String demTotalAmt = "0";
    String otgPrinAmt = ".00";
    String otgIntAmt = ".00";
    String otgChg = ".00";
    String otgTotalAmt = ".00";
    private InitialContext ctx;
    private LoanGenralFacadeRemote dmr;
    String accNo;
    String errorMsg;
    String partyName;
    Date asOnDate;
    int currentRow;
    boolean chkAuthValue;
    String isGridEmpty;
    boolean flag = false;
    String acNoFlag;
    DemandMarkingTable dmTable;
    List<DemandMarkingTable> dmTableList = new ArrayList<DemandMarkingTable>();

    /*************************************************************************************************************************/
    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
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

    public String getOrgBrnCode() {
        return orgBrnCode;
    }

    public void setOrgBrnCode(String orgBrnCode) {
        this.orgBrnCode = orgBrnCode;
    }

    public String getDemChg() {
        return demChg;
    }

    public void setDemChg(String demChg) {
        this.demChg = demChg;
    }

    public String getDemIntAmt() {
        return demIntAmt;
    }

    public void setDemIntAmt(String demIntAmt) {
        this.demIntAmt = demIntAmt;
    }

    public String getDemPrinAmt() {
        return demPrinAmt;
    }

    public void setDemPrinAmt(String demPrinAmt) {
        this.demPrinAmt = demPrinAmt;
    }

    public String getDemTotalAmt() {
        return demTotalAmt;
    }

    public void setDemTotalAmt(String demTotalAmt) {
        this.demTotalAmt = demTotalAmt;
    }

    public String getOtgChg() {
        return otgChg;
    }

    public void setOtgChg(String otgChg) {
        this.otgChg = otgChg;
    }

    public String getOtgIntAmt() {
        return otgIntAmt;
    }

    public void setOtgIntAmt(String otgIntAmt) {
        this.otgIntAmt = otgIntAmt;
    }

    public String getOtgPrinAmt() {
        return otgPrinAmt;
    }

    public void setOtgPrinAmt(String otgPrinAmt) {
        this.otgPrinAmt = otgPrinAmt;
    }

    public String getOtgTotalAmt() {
        return otgTotalAmt;
    }

    public void setOtgTotalAmt(String otgTotalAmt) {
        this.otgTotalAmt = otgTotalAmt;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public Date getAsOnDate() {
        return asOnDate;
    }

    public void setAsOnDate(Date asOnDate) {
        this.asOnDate = asOnDate;
    }

    public DemandMarkingTable getDmTable() {
        return dmTable;
    }

    public void setDmTable(DemandMarkingTable dmTable) {
        this.dmTable = dmTable;
    }

    public List<DemandMarkingTable> getDmTableList() {
        return dmTableList;
    }

    public void setDmTableList(List<DemandMarkingTable> dmTableList) {
        this.dmTableList = dmTableList;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public boolean isChkAuthValue() {
        return chkAuthValue;
    }

    public void setChkAuthValue(boolean chkAuthValue) {
        this.chkAuthValue = chkAuthValue;
    }

    public String getIsGridEmpty() {
        return isGridEmpty;
    }

    public void setIsGridEmpty(String isGridEmpty) {
        this.isGridEmpty = isGridEmpty;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getAcNoFlag() {
        return acNoFlag;
    }

    public void setAcNoFlag(String acNoFlag) {
        this.acNoFlag = acNoFlag;
    }

    /*************************************************************************************************************************/
    public DemandMarkingManaged() {
        try {
            dmr = (LoanGenralFacadeRemote) ServiceLocator.getInstance().lookup("LoanGenralFacade");
            request = getRquest();
            String orgnBrIp = WebUtil.getClientIP(request);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgBrnCode = Init.IP2BR.getBranch(localhost);
            Date dt = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(dt));
            setUserName(request.getRemoteUser());
            setAsOnDate(dt);
            refreshForm();
        } catch (ApplicationException e) {
            errorMsg = e.getMessage();
        } catch (Exception e) {
            errorMsg = e.getMessage();
        }
    }

    public HttpServletRequest getRquest() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return req;
    }

    public void setdataForPresentOtgAndPartyName() {
        boolean chkAcNum = false;
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        try {
            if (accNo.equalsIgnoreCase("") || accNo.equalsIgnoreCase("0") || accNo == null) {
                this.setErrorMsg("Please fill the Account No.");
                return;
            }
            List resultCustname = dmr.getCustname(accNo);
            if (resultCustname.isEmpty()) {
                setPartyName("");
                this.setErrorMsg("Invalid Account No.");
                this.setDemPrinAmt("0");
                this.setDemIntAmt("0");
                this.setDemChg("0");
                this.setDemTotalAmt("0");
                this.setAsOnDate(dt);
                acNoFlag = "true";
                return;
            } else {
                acNoFlag = "false";
                Vector vecCustname = (Vector) resultCustname.get(0);
                String strCustName = vecCustname.get(0).toString();
                setErrorMsg("");
                setPartyName(strCustName);
                chkAcNum = true;
            }

            String strForOtgPrn = dmr.fnOtgPrn(accNo, sdf.format(dt));
            if (strForOtgPrn.equalsIgnoreCase("Data Not Find!!!")) {
                this.setErrorMsg("Data Not Find!!!");
                this.setDemPrinAmt("0");
                this.setDemIntAmt("0");
                this.setDemChg("0");
                this.setDemTotalAmt("0");
                this.setAsOnDate(dt);
                return;
            } else {
                this.setErrorMsg("");
                this.setOtgPrinAmt(String.valueOf(java.lang.Math.abs(Float.parseFloat(strForOtgPrn))));
            }
            String strForOtgInt = dmr.fnOtgInst(accNo, sdf.format(dt));
            if (strForOtgInt.equalsIgnoreCase("Data Not Find!!!")) {
                this.setErrorMsg("Data Not Find!!!");
                this.setDemPrinAmt("0");
                this.setDemIntAmt("0");
                this.setDemChg("0");
                this.setDemTotalAmt("0");
                this.setAsOnDate(dt);
                return;
            } else {
                this.setErrorMsg("");
                this.setOtgIntAmt(String.valueOf(java.lang.Math.abs(Float.parseFloat(strForOtgInt))));
            }
            String strForOtgChg = dmr.FnOtgChg(accNo, sdf.format(dt));
            if (strForOtgChg.equalsIgnoreCase("Data Not Find!!!")) {
                this.setErrorMsg("Data Not Find!!!");
                this.setDemPrinAmt("0");
                this.setDemIntAmt("0");
                this.setDemChg("0");
                this.setDemTotalAmt("0");
                this.setAsOnDate(dt);
                return;
            } else {
                this.setErrorMsg("");
                this.setOtgChg(String.valueOf(java.lang.Math.abs(Float.parseFloat(strForOtgChg))));
            }
            this.setOtgTotalAmt(String.valueOf((Float.parseFloat(this.otgPrinAmt) + Float.parseFloat(this.otgIntAmt) + Float.parseFloat(this.otgChg))));

        } catch (ApplicationException e) {
            errorMsg = e.getMessage();
        } catch (Exception e) {
            errorMsg = e.getMessage();
        }
    }

    public void demPrinLostFocus() {
        if (this.demPrinAmt.equalsIgnoreCase("") || this.demPrinAmt.length() == 0) {
            this.demPrinAmt = "0";
            this.setErrorMsg("Please Enter Demand Principal.");
            return;
        }
        demTotalAmt = String.valueOf(Float.parseFloat(demIntAmt) + Float.parseFloat(demChg) + Float.parseFloat(demPrinAmt));
    }

    public void demIntLostFocus() {
        if (this.demIntAmt.equalsIgnoreCase("") || this.demIntAmt.length() == 0) {
            this.demIntAmt = "0";
            this.setErrorMsg("Please Enter Demand Interest.");
            return;
        }
        demTotalAmt = String.valueOf(Float.parseFloat(demPrinAmt) + Float.parseFloat(demChg) + Float.parseFloat(demIntAmt));
    }

    public void demChgLostFocus() {
        if (this.demChg.equalsIgnoreCase("") || this.demChg.length() == 0) {
            this.demChg = "0";
            this.setErrorMsg("Please Enter Demand Charges.");
            return;
        }
        demTotalAmt = String.valueOf(Float.parseFloat(demPrinAmt) + Float.parseFloat(demChg) + Float.parseFloat(demIntAmt));
    }

    public void setDataIntoGridOnClickButton() {
        try {
            getFinYear();


            /*  Written By Zeeshan  */
            if (Float.parseFloat(demPrinAmt) <= 0) {
                this.setErrorMsg("Demand Principal Must Not Be Zero Or Less Than Zero");
                return;
            }

            if (Float.parseFloat(demIntAmt) < 0) {
                this.setErrorMsg("Demand Interest Not Less Than Zero");
                return;
            }

            if (Float.parseFloat(demChg) < 0) {
                this.setErrorMsg("Demand Charges Not Less Than Zero");
                return;
            }

            /**************************/
            if (Float.parseFloat(this.demTotalAmt) >= Float.parseFloat(this.otgTotalAmt)) {
                this.setErrorMsg("Total Demand Must Be Less Then or Equal To O/s Balance!!!");
                return;
            }
            List list = dmr.selectDemandDt(accNo);
            for (int i = 0; i < list.size(); i++) {
                Vector vec = (Vector) list.get(i);
                String str = vec.get(0).toString();
                Calendar calender1 = Calendar.getInstance();
                Calendar calendar2 = Calendar.getInstance();
                Date dt = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                calender1.set(Integer.parseInt(sdf.format(asOnDate).substring(6)), Integer.parseInt(sdf.format(asOnDate).substring(3, 5)), Integer.parseInt(sdf.format(asOnDate).substring(0, 2)));
                calendar2.set(Integer.parseInt(str.substring(0, 4)), Integer.parseInt(str.substring(5, 7)), Integer.parseInt(str.substring(8, 10)));
                if (calendar2.equals(calender1)) {
                    this.setErrorMsg("Demand Already Assigned on Date:" + sdf.format(asOnDate));
                    return;
                }
            }
            this.setErrorMsg("");
            if (this.chkAuthValue == true) {
                dmTableList.clear();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                dmTable = new DemandMarkingTable();
                dmTable.setAccountNo(getAccNo());
                String getAsOnDateInString = sdf.format(asOnDate);
                dmTable.setDemandDate(getAsOnDateInString.substring(6) + "/" + getAsOnDateInString.substring(4, 6) + "/" + getAsOnDateInString.substring(0, 4));
                dmTable.setPrinDemand(getDemPrinAmt());
                dmTable.setIntDemand(getDemIntAmt());
                dmTable.setChgDemand(getDemChg());
                dmTable.setPartyName(getPartyName());
                dmTableList.add(dmTable);
                this.setChkAuthValue(false);
                this.flag = false;
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                dmTable = new DemandMarkingTable();
                dmTable.setAccountNo(getAccNo());
                String getAsOnDateInString = sdf.format(asOnDate);
                dmTable.setDemandDate(getAsOnDateInString.substring(6) + "/" + getAsOnDateInString.substring(4, 6) + "/" + getAsOnDateInString.substring(0, 4));
                dmTable.setPrinDemand(getDemPrinAmt());
                dmTable.setIntDemand(getDemIntAmt());
                dmTable.setChgDemand(getDemChg());
                dmTable.setPartyName(getPartyName());
                dmTableList.add(dmTable);
            }
        } catch (ApplicationException e) {
            errorMsg = e.getMessage();
        } catch (Exception e) {
            errorMsg = e.getMessage();
        }
    }

    public void saveDataFromGridInTable() {

        String b[][] = new String[dmTableList.size()][6];
        for (int i = 0; i < dmTableList.size(); i++) {
            b[i][0] = ((DemandMarkingTable) dmTableList.get(i)).getAccountNo().toString();
            b[i][1] = ((DemandMarkingTable) dmTableList.get(i)).getPartyName().toString();
            b[i][2] = ((DemandMarkingTable) dmTableList.get(i)).getDemandDate().toString();
            b[i][3] = ((DemandMarkingTable) dmTableList.get(i)).getPrinDemand().toString();
            b[i][4] = ((DemandMarkingTable) dmTableList.get(i)).getIntDemand().toString();
            b[i][5] = ((DemandMarkingTable) dmTableList.get(i)).getChgDemand().toString();
        }
        List arraylist = new ArrayList();
        for (int i = 0; i < dmTableList.size(); i++) {
            for (int j = 0; j < 6; j++) {
                Object combinedArray = b[i][j];
                arraylist.add(combinedArray);
            }
        }
        try {
            if (arraylist.isEmpty()) {
                this.setErrorMsg("");
                this.setErrorMsg("No Data For SAVE!!!");
                return;
            } else {
                String saveResult = dmr.saveGridData(arraylist);
                if (saveResult.equalsIgnoreCase("Data has been saved successfully.")) {
                    refreshForm();
                    this.setPartyName("");
                    setErrorMsg(saveResult);
                } else {
                    setErrorMsg(saveResult);
                }
            }
        } catch (ApplicationException e) {
            errorMsg = e.getMessage();
        } catch (Exception e) {
            errorMsg = e.getMessage();
        }

    }

    public void removeCurrentRow() {
        dmTableList.remove(dmTable);
    }

    public void fetchCurrentRow(ActionEvent event) {

        String accCode = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("accCode"));
        currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (DemandMarkingTable item : dmTableList) {
            if (item.getAccountNo().equalsIgnoreCase(accCode)) {
                dmTable = item;
            }
        }
    }

    public void chkAuthBoxClick() {
        try {
            if (this.chkAuthValue == true) {
                if (dmTableList.isEmpty()) {
                    List listOfAuth = dmr.selectDataForChkAuthClick(accNo);
                    if (listOfAuth.isEmpty()) {
                        this.setErrorMsg("No UnAuthorise Record Was Found.");
                    } else {
                        for (int i = 0; i < listOfAuth.size(); i++) {
                            Vector vecForAuth = (Vector) listOfAuth.get(i);
                            dmTable = new DemandMarkingTable();
                            dmTable.setAccountNo(accNo.substring(2, 10));
                            dmTable.setPartyName(partyName);
                            dmTable.setPrinDemand(vecForAuth.get(1).toString());
                            dmTable.setIntDemand(vecForAuth.get(2).toString());
                            dmTable.setChgDemand(vecForAuth.get(3).toString());
                            String dmnddate = vecForAuth.get(0).toString().substring(8, 10) + "/" + vecForAuth.get(0).toString().substring(5, 7) + "/" + vecForAuth.get(0).toString().substring(0, 4);
                            dmTable.setDemandDate(dmnddate);
                            dmTableList.add(dmTable);
                        }
                        this.flag = true;
                    }
                    isGridEmpty = "True";
                } else {
                    isGridEmpty = "False";
                }
            } else {
                dmTableList.clear();
                isGridEmpty = "True";
                this.flag = false;
            }
        } catch (ApplicationException e) {
            errorMsg = e.getMessage();
        } catch (Exception e) {
            errorMsg = e.getMessage();
        }
    }

    public void selectDataForChk() {
        try {
            List listOfAuth = dmr.selectDataForChkAuthClick(accNo);
            if (!listOfAuth.isEmpty()) {
                for (int i = 0; i < listOfAuth.size(); i++) {
                    Vector vecForAuth = (Vector) listOfAuth.get(i);
                    dmTable = new DemandMarkingTable();
                    dmTable.setAccountNo(accNo.substring(2, 10));
                    dmTable.setPartyName(partyName);
                    dmTable.setPrinDemand(vecForAuth.get(1).toString());
                    dmTable.setIntDemand(vecForAuth.get(2).toString());
                    dmTable.setChgDemand(vecForAuth.get(3).toString());
                    String dmnddate = vecForAuth.get(0).toString().substring(8, 10) + "/" + vecForAuth.get(0).toString().substring(5, 7) + "/" + vecForAuth.get(0).toString().substring(0, 4);
                    dmTable.setDemandDate(dmnddate);
                    dmTableList.add(dmTable);
                }
            }
        } catch (ApplicationException e) {
            errorMsg = e.getMessage();
        } catch (Exception e) {
            errorMsg = e.getMessage();
        }
    }

    public void saveDataFromGridAfterPopUp() {
        saveDataFromGridInTable();
        dmTableList.clear();
        selectDataForChk();
        this.flag = true;
    }

    public void discardDataFromGridAfterPopUp() {
        dmTableList.clear();
        selectDataForChk();
        this.setErrorMsg("Data has not been posted.");
        this.flag = true;
    }

    public void getFinYear() {
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            String finYear = dmr.finYear(sdf.format(dt), orgBrnCode);
            String findate = "01" + "04" + finYear;
            Calendar calender1 = Calendar.getInstance();
            Calendar calendar2 = Calendar.getInstance();
            calender1.set(Integer.parseInt(findate.substring(4)), Integer.parseInt(findate.substring(2, 4)), Integer.parseInt(findate.substring(0, 2)));
            calendar2.set(Integer.parseInt(sdf.format(dt).substring(0, 4)), Integer.parseInt(sdf.format(dt).substring(4, 6)), Integer.parseInt(sdf.format(dt).substring(6)));
            if (calendar2.before(calender1)) {
                this.setErrorMsg("Selected Date Is Not In Current Finical Year" + finYear);
                refreshForm();
                return;
            }
        } catch (ApplicationException e) {
            errorMsg = e.getMessage();
        } catch (Exception e) {
            errorMsg = e.getMessage();
        }
    }

    public void refreshForm() {
        Date date = new Date();
        this.setPartyName("");
        this.setOtgPrinAmt(".00");
        this.setOtgIntAmt(".00");
        this.setOtgChg(".00");
        this.setOtgTotalAmt(".00");
        this.setDemPrinAmt("0");
        this.setDemIntAmt("0");
        this.setDemChg("0");
        this.setDemTotalAmt("0");
        this.setAsOnDate(date);
        this.setErrorMsg("");
        dmTableList.clear();
    }

    public String exitForm() {
        refreshForm();
        this.setAccNo("");
        this.setPartyName("");
        return "case1";
    }

    public void formRefresh() {
        refreshForm();
        this.setAccNo("");
        this.setPartyName("");
    }
}
