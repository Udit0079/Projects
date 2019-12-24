/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.master.GeneralMasterFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.master.ChargeMastersTable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;
import javax.naming.NamingException;

/**
 *
 * @author Athar Reza
 */
public final class ChargeMasters extends BaseBean {

    private String message;
    private String errorMsg;
    private String userName;
    private String todayDate;
    private boolean chargeTypeDisable = false;
    private String chargeType;
    private List<SelectItem> chargeTypeList;
    private String chargeName;
    private List<SelectItem> chargeNmeList;
    private String acctType;
    private List<SelectItem> accTypeList;
    private String fixFlag;
    private List<SelectItem> fixFlagList;
    private String fromRange;
    private String toRange;
    private String startDate;
    private String lastEffDate;
    private Date effDt = new Date();
    private String creationDt;
    private String crglhead;
    private String newCrGlHead = "";
    private double amount;
    private String updateDate;
    private String changeDate;
    private String updateBy;
    private String enterBy;
    private boolean flag;
    private boolean amountDisable;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private List<ChargeMastersTable> chargeMasterData;
    private int currentRow;
    private ChargeMastersTable currentItem = new ChargeMastersTable();
    List tempList;
    Vector tempVector;
    private LoanReportFacadeRemote local;
    private GeneralMasterFacadeRemote generalFacadeRemote;
    private OtherReportFacadeRemote loanFacade;
    private FtsPostingMgmtFacadeRemote ftsRemote;
    private CommonReportMethodsRemote commonReportRemote;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public boolean isChargeTypeDisable() {
        return chargeTypeDisable;
    }

    public void setChargeTypeDisable(boolean chargeTypeDisable) {
        this.chargeTypeDisable = chargeTypeDisable;
    }

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }

    public Date getEffDt() {
        return effDt;
    }

    public void setEffDt(Date effDt) {
        this.effDt = effDt;
    }

    public boolean isAmountDisable() {
        return amountDisable;
    }

    public void setAmountDisable(boolean amountDisable) {
        this.amountDisable = amountDisable;
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public List<SelectItem> getAccTypeList() {
        return accTypeList;
    }

    public void setAccTypeList(List<SelectItem> accTypeList) {
        this.accTypeList = accTypeList;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getChargeName() {
        return chargeName;
    }

    public void setChargeName(String chargeName) {
        this.chargeName = chargeName;
    }

    public List<SelectItem> getChargeNmeList() {
        return chargeNmeList;
    }

    public void setChargeNmeList(List<SelectItem> chargeNmeList) {
        this.chargeNmeList = chargeNmeList;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public List<SelectItem> getChargeTypeList() {
        return chargeTypeList;
    }

    public void setChargeTypeList(List<SelectItem> chargeTypeList) {
        this.chargeTypeList = chargeTypeList;
    }

    public String getCreationDt() {
        return creationDt;
    }

    public void setCreationDt(String creationDt) {
        this.creationDt = creationDt;
    }

    public String getCrglhead() {
        return crglhead;
    }

    public void setCrglhead(String crglhead) {
        this.crglhead = crglhead;
    }

    public ChargeMastersTable getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(ChargeMastersTable currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getFixFlag() {
        return fixFlag;
    }

    public void setFixFlag(String fixFlag) {
        this.fixFlag = fixFlag;
    }

    public List<SelectItem> getFixFlagList() {
        return fixFlagList;
    }

    public void setFixFlagList(List<SelectItem> fixFlagList) {
        this.fixFlagList = fixFlagList;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getFromRange() {
        return fromRange;
    }

    public void setFromRange(String fromRange) {
        this.fromRange = fromRange;
    }

    public List<ChargeMastersTable> getChargeMasterData() {
        return chargeMasterData;
    }

    public void setChargeMasterData(List<ChargeMastersTable> chargeMasterData) {
        this.chargeMasterData = chargeMasterData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToRange() {
        return toRange;
    }

    public void setToRange(String toRange) {
        this.toRange = toRange;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public CommonReportMethodsRemote getCommonReportRemote() {
        return commonReportRemote;
    }

    public void setCommonReportRemote(CommonReportMethodsRemote commonReportRemote) {
        this.commonReportRemote = commonReportRemote;
    }

    public String getLastEffDate() {
        return lastEffDate;
    }

    public void setLastEffDate(String lastEffDate) {
        this.lastEffDate = lastEffDate;
    }
    
    public ChargeMasters() {
        try {
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            generalFacadeRemote = (GeneralMasterFacadeRemote) ServiceLocator.getInstance().lookup("GeneralMasterFacade");
            loanFacade = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            commonReportRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            userName = getUserName();
            todayDate = getTodayDate();
            fixFlagList = new ArrayList<SelectItem>();
            fixFlagList.add(new SelectItem("0", "--SELECT--"));
            fixFlagList.add(new SelectItem("P", "Percentage"));
            fixFlagList.add(new SelectItem("F", "Fixed"));

            chargeTypeList = new ArrayList<SelectItem>();
            chargeTypeList.add(new SelectItem("0", "--SELECT--"));
            List referenceList = commonReportRemote.getRefRecList("446");
            if (referenceList.isEmpty()) {
                this.setMessage("Please define initial data for code 446");
                return;
            }
            for (int i = 0; i < referenceList.size(); i++) {
                Vector ele = (Vector) referenceList.get(i);
                chargeTypeList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
            }
//            chargeTypeList.add(new SelectItem("CLEARING", "CLEARING"));
//            chargeTypeList.add(new SelectItem("NEFT-RTGS", "NEFT-RTGS"));
//            chargeTypeList.add(new SelectItem("OTH-CHARGE","OTH-CHARGE"));
            accTypeList = new ArrayList<SelectItem>();
            acType();
            chargeType();
            displayTableDetails();
            setFlag(true);
            setMessage("");
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void acType() {
        try {
            local = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup("LoanReportFacade");
            accTypeList.add(new SelectItem("--SELECT--"));
            tempList = loanFacade.getAcctTypeList();
            for (int i = 0; i < tempList.size(); i++) {
                tempVector = (Vector) tempList.get(i);
                accTypeList.add(new SelectItem(tempVector.get(0), tempVector.get(0).toString()));
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void chargeType() {
        try {
            chargeNmeList = new ArrayList<SelectItem>();
            chargeNmeList.add(new SelectItem("0", "--SELECT--"));
            List chargeGetList = commonReportRemote.getChargeCode(this.chargeType);
            for (int i = 0; i < chargeGetList.size(); i++) {
                Vector chr = (Vector) chargeGetList.get(i);
                List chargenamelist = commonReportRemote.getChargeName(chr.get(0).toString());
                Vector chargename = (Vector) chargenamelist.get(0);
                chargeNmeList.add(new SelectItem(chr.get(0).toString(), chargename.get(0).toString()));

            }
            displayTableDetails();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void resetButton() {
        this.setFlag(true);
        this.setMessage("");
        chargeType();
    }

    public void saveBtnAction() {
        this.setMessage("");
        this.setErrorMsg("");
        flag = true;
        try {
            this.newCrGlHead = "";
            newCrGlHead = CbsAcCodeConstant.GL_ACCNO.getAcctCode() + ftsRemote.lPading(this.crglhead, 6, "0") + "01";
            if (chargeName.equals("0")) {
                this.setMessage("Please select the Charge Name.");
                return;
            }
            if (chargeType.equals("0")) {
                this.setMessage("Please select the Charge Type.");
                return;
            }
            if (acctType.equals("0")) {
                this.setMessage("Please select the A/c Type.");
                return;
            }
            if (fixFlag.equals("0")) {
                this.setMessage("Please select the fix Flag.");
                return;
            }
            if (fromRange.equals("")) {
                this.setMessage("Please fill the From Range.");
                return;
            }
            if (toRange.equals("")) {
                this.setMessage("Please fill the To Range.");
                return;
            }
            if(Double.parseDouble(fromRange) > Double.parseDouble(toRange)){
                this.setMessage("From Range must be less than To Range.");
                return;
            }
            if (crglhead.equals("")) {
                this.setMessage("Please fill the Cr glhead.");
                return;
            }
            if(effDt.getTime()<= sdf.parse(sdf.format(new Date())).getTime()){
                this.setMessage("Effective Date must be Future Date.");
                return;
            }
            startDate = ymd.format(this.effDt);
            String result = generalFacadeRemote.saveUpdateChargeMasters("save", chargeType, chargeName, acctType, fromRange, toRange, fixFlag, amount, 
                    startDate, newCrGlHead, enterBy, creationDt, updateBy, updateDate, userName);
            this.setMessage(result);
            clear();
            displayTableDetails();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void displayTableDetails() throws NamingException {
        chargeMasterData = new ArrayList<>();
        try {
            List resultLt = generalFacadeRemote.tableDataChargeMasters(chargeType);
            for (int i = 0; i < resultLt.size(); i++) {
                Vector ele = (Vector) resultLt.get(i);
                ChargeMastersTable pojo = new ChargeMastersTable();
                pojo.setChargesType(ele.get(0).toString());
                pojo.setChargesName(ele.get(1).toString());
                pojo.setAcType(ele.get(2).toString());
                pojo.setFrRange(ele.get(3).toString());
                pojo.setToeRange(ele.get(4).toString());
                pojo.setFixprFlag(ele.get(5).toString());
                pojo.setAmt(Double.parseDouble(ele.get(6).toString()));
                pojo.setEffDate(ele.get(7).toString());
                pojo.setCrglHead(ele.get(8).toString());
                pojo.setEnterBy(ele.get(9).toString());
                pojo.setCreationDt(ele.get(10).toString());

                if (ele.get(11) == null) {
                    pojo.setUpdateby("");
                } else {
                    pojo.setUpdateby(ele.get(11).toString());
                }
                if (ele.get(12) == null) {
                    pojo.setUpdateDt("");
                } else {
                    pojo.setUpdateDt(ele.get(12).toString());
                }
                chargeMasterData.add(pojo);
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void select() {
        try {
            setMessage("");
            flag = false;
            setChargeType(currentItem.getChargesType());
            setChargeName(currentItem.getChargesName());
            setAcctType(currentItem.getAcType());
            setFromRange(currentItem.getFrRange());
            setToRange(currentItem.getToeRange());
            setFixFlag(currentItem.getFixprFlag());
            setAmount(currentItem.getAmt());
            setEffDt(sdf.parse(currentItem.getEffDate()));
            setLastEffDate(currentItem.getEffDate());
            setCrglhead(currentItem.getCrglHead());
            setEnterBy(currentItem.getEnterBy());
            String year = currentItem.getCreationDt().substring(0, 4);
            String month = currentItem.getCreationDt().substring(5, 7);
            String day = currentItem.getCreationDt().substring(8, 10);
            String time = currentItem.getCreationDt().substring(10);
            String crDate = day + "-" + month + "-" + year + time;
            setCreationDt(crDate);
            if (currentItem.getUpdateby().equalsIgnoreCase("")) {
                setUpdateBy("");
            } else {
                setUpdateBy(currentItem.getUpdateby());
            }
            if (currentItem.getUpdateDt().equalsIgnoreCase("")) {
                setChangeDate("");
            } else {
                String updtYear = currentItem.getUpdateDt().substring(0, 4);
                String updtMonth = currentItem.getUpdateDt().substring(5, 7);
                String updtDay = currentItem.getUpdateDt().substring(8, 10);
                String updtTime = currentItem.getUpdateDt().substring(10);
                String updatedDate = updtDay + "-" + updtMonth + "-" + updtYear + updtTime;
                setChangeDate(updatedDate);
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void updateBtnAction() {
        this.setMessage("");
        this.setErrorMsg("");
        try {

            Date date = new Date();
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
            setUpdateDate(sdf1.format(date));
            startDate = ymd.format(this.effDt);
            if (chargeName.equals("0")) {
                this.setMessage("Please select the Charge Name.");
                return;
            }
            if (chargeType.equals("0")) {
                this.setMessage("Please select the Charge Type.");
                return;
            }
            if (acctType.equals("0")) {
                this.setMessage("Please select the A/c Type.");
                return;
            }
            if (fixFlag.equals("0")) {
                this.setMessage("Please select the fix Flag.");
                return;
            }
            if (fromRange.equals("")) {
                this.setMessage("Please fill the From Range.");
                return;
            }
            if (toRange.equals("")) {
                this.setMessage("Please fill the To Range.");
                return;
            }
            if(Double.parseDouble(fromRange) > Double.parseDouble(toRange)){
                this.setMessage("From Range must be less than To Range.");
                return;
            }
            if (crglhead.equals("")) {
                this.setMessage("Please fill the Cr glhead.");
                return;
            }
            if(effDt.getTime()<= sdf.parse(sdf.format(new Date())).getTime()){
                this.setMessage("Effective Date must be Future Date.");
                return;
            }
            String result = generalFacadeRemote.saveUpdateChargeMasters("update", chargeType, chargeName, acctType, fromRange, toRange, fixFlag, amount, 
                    startDate, crglhead, currentItem.getEnterBy(), currentItem.getCreationDt(), todayDate, updateDate, userName);
            this.setMessage(result);
            clear();
            displayTableDetails();
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void clear() {
        try {
            flag = true;
            setAcctType("--SELECT--");
            setAmount(0.00);
            setChargeName("0");
            setChargeType("0");
            setCrglhead("");
            setFixFlag("0");
            setFromRange("");
            setErrorMsg("");
            setToRange("");
            setEffDt(new Date());
            setChangeDate("");
            setCreationDt("");
            setEnterBy("");
            setUpdateBy("");

        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());

        }
    }

    public void clear1() {
        try {
            flag = true;
            setAcctType("--SELECT--");
            setAmount(0.00);
            setChargeName("0");
            setChargeType("0");
            setCrglhead("");
            setFixFlag("0");
            setFromRange("");
            setMessage("");
            setErrorMsg("");
            setToRange("");
            setEffDt(new Date());
            setChangeDate("");
            setCreationDt("");
            setEnterBy("");
            setUpdateBy("");
            this.chargeName = "0";

        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());

        }
    }

    public String exitForm() {
        clear();
        this.setMessage("");
        return "case1";
    }
}
