package com.cbs.web.controller.ho.master;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.master.CrrDailyEntryFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.pojo.ho.SavingDepositTable;
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
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Sudhir kr bisht
 */
public class SavingDeposit {

    private String orgBrnCode;
    private String todayDate;
    private String userName;
    private float amt;
    private HttpServletRequest request;
    private String effectiveDate;
    private String amount;
    private String enterBy;
    private List<SavingDepositTable> tableDtList;
    private SavingDepositTable tableDt;
    private int currentRow;
    private String newDate1;
    private String message;
    private String saveFlg = "false";
    private String updateFlg = "true";
    private CrrDailyEntryFacadeRemote crrDailyEntryFacadeService = null;

    Date dt = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getNewDate1() {
        return newDate1;
    }

    public void setNewDate1(String newDate1) {
        this.newDate1 = newDate1;
    }   

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public SavingDepositTable getTableDt() {
        return tableDt;
    }

    public void setTableDt(SavingDepositTable tableDt) {
        this.tableDt = tableDt;
    }

    public List<SavingDepositTable> getTableDtList() {
        return tableDtList;
    }

    public void setTableDtList(List<SavingDepositTable> tableDtList) {
        this.tableDtList = tableDtList;
    }

    public float getAmt() {
        return amt;
    }

    public void setAmt(float amt) {
        this.amt = amt;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getOrgBrnCode() {
        return orgBrnCode;
    }

    public void setOrgBrnCode(String orgBrnCode) {
        this.orgBrnCode = orgBrnCode;
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

    public String getSaveFlg() {
        return saveFlg;
    }

    public void setSaveFlg(String saveFlg) {
        this.saveFlg = saveFlg;
    }

    public String getUpdateFlg() {
        return updateFlg;
    }

    public void setUpdateFlg(String updateFlg) {
        this.updateFlg = updateFlg;
    }  
    

    public SavingDeposit() {
        request = getRequest();
        try {
            String orgnBrIp = WebUtil.getClientIP(request);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgBrnCode = Init.IP2BR.getBranch(localhost);
            setUserName(request.getRemoteUser());
            setTodayDate(sdf.format(dt));
            tableDtList = new ArrayList<SavingDepositTable>();
            crrDailyEntryFacadeService = (CrrDailyEntryFacadeRemote) ServiceLocator.getInstance().lookup("CrrDailyEntryFacade");
            this.setNewDate1(sdf.format(dt));
            getTableDetail();
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }

    }

    public HttpServletRequest getRequest() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return req;
    }

    private void getTableDetail() {
        List lst1 = new ArrayList();
        try {
            lst1 = crrDailyEntryFacadeService.getTableDetailsSavingDeposit();
            if (!lst1.isEmpty()) {
                for (int j = 0; j < lst1.size(); j++) {
                    Vector element = (Vector) lst1.get(j);
                    tableDt = new SavingDepositTable();
                    tableDt.setApplicableDate(element.get(0).toString());
                    tableDt.setAddIntAmount(Float.parseFloat(element.get(1).toString()));
                    tableDt.setAddIntEnter(element.get(2).toString());
                    tableDtList.add(tableDt);
                }
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void setRowValues() {
        try {
            setNewDate1(tableDt.getApplicableDate());
            setAmount(String.valueOf(tableDt.getAddIntAmount()));
            setSaveFlg("true");
            setUpdateFlg("false");
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void saveRecord() {
        try {
            setMessage("");
            String st1 = onblurUpdate();
            if (!st1.equals("true")) {
                this.setMessage(st1);
                this.setAmount("");
                return;
            }
            if (!tableDtList.isEmpty()) {
                tableDtList.clear();
            }
                               
            String date1 = ymd.format(sdf.parse(this.newDate1));
            Double amt1 = Double.parseDouble(this.getAmount());
            String enterBy1 = this.getUserName();
            String result = crrDailyEntryFacadeService.saveRecord(date1, amt1, enterBy1);
            setMessage(result);
            this.setNewDate1(sdf.format(dt));
            this.setAmount("");
            getTableDetail();
            setSaveFlg("false");
            setUpdateFlg("true");
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void setRowUpdate() {
        try {
            setMessage("");
            String st1 = onblurUpdate();
            if (!st1.equals("true")) {
                this.setMessage(st1);
                this.setAmount("");
                return;
            }
            float amt1 = Float.parseFloat(this.getAmount());
            String enterBy1 = this.getUserName();

            setAmount(String.valueOf(tableDt.getAddIntAmount()));
            String date = tableDt.getApplicableDate();
            String result = crrDailyEntryFacadeService.setRowUpdate(date, amt1, enterBy1);
            setMessage(result);
            tableDtList.clear();
            getTableDetail();
            this.setNewDate1(sdf.format(dt));
            this.setAmount("");
            setSaveFlg("false");
            setUpdateFlg("true");
        } catch (ApplicationException e) {
            this.setMessage("Problem in data updation with error " + e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public String onblurUpdate() {
        try {
            String error = "";
            if (this.newDate1.equals("")) {
                error = "Error!! Enter valid Date";
                return error;
            }
            if (this.getAmount().equals("")) {
                error = "Error!! Enter valid numeric value for amount";
                return error;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher billNoCheck = p.matcher(this.getAmount());
            if (!billNoCheck.matches()) {
                error = "Error!! Enter valid numeric value for amount";
                return error;
            }
            if (Double.parseDouble(this.getAmount()) < 0) {
                error = "Error!! Amount could not be negative";
                return error;
            }
            this.setMessage("");
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
        return "true";
    }

    public void refresh() {
        try {
            this.setAmount("");
            setNewDate1(sdf.format(dt));
            this.setMessage("");
            setSaveFlg("false");
            setUpdateFlg("true");
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public String exitForm() {
        try {
            refresh();
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }
}
