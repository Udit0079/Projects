/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master;

import com.cbs.facade.misc.MinBalanceChargesFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.pojo.AtmMissingEntryPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

/**
 *
 * @author Admin
 */
public class AtmMissingEntryPosting extends BaseBean {

    private String message;
    private String atmBranch;
    private List<SelectItem> atmBranchList;
    private String atmId;
    private List<SelectItem> atmIdList;
    private String glHead;
    private Date valueDt;
    private Date date = new Date();
    private String amount;
    private String debitHead;
    int currentRow;
    boolean postButtonDisable;
    private List<AtmMissingEntryPojo> gridDetail;
    private AtmMissingEntryPojo currentItem = new AtmMissingEntryPojo();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private CommonReportMethodsRemote common;
    MinBalanceChargesFacadeRemote miscRemote;

    public AtmMissingEntryPosting() {
        try {
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            miscRemote = (MinBalanceChargesFacadeRemote) ServiceLocator.getInstance().lookup("MinBalanceChargesFacade");
            postButtonDisable = true;

            List brncode = new ArrayList();
            brncode = common.getAtmBranchList(this.getOrgnBrCode());
            atmBranchList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    atmBranchList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void showAtmId() {
        setMessage("");
        try {
            setMessage("");
            atmIdList = new ArrayList<SelectItem>();
            atmIdList.add(new SelectItem("S", "--Select--"));
            List list = common.getAtmId(this.atmBranch);
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector vtr = (Vector) list.get(i);
                    atmIdList.add(new SelectItem(vtr.get(0).toString()));
                }
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void showGlhead() {
        try {
            setMessage("");
            this.glHead = common.getAtmhead(atmId);
            this.debitHead = common.getNfsAtmHead();
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void gridLoad() {
        setMessage("");
        try {
            if (atmId.equalsIgnoreCase("S")) {
                setMessage("Please select Atm Id.");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher m = p.matcher(amount);
            if (this.amount == null || this.amount.equalsIgnoreCase("")) {
                setMessage("Amount can not be blank.");
                return;
            } else if (m.matches() != true) {
                setMessage("Please fill proper amount.");
                return;
            } else if (this.amount.equalsIgnoreCase("0") || this.amount.equalsIgnoreCase("0.00")) {
                setMessage("Amount can not be zero.");
                return;
            } else if (new BigDecimal(this.amount).compareTo(new BigDecimal("0")) == -1) {
                setMessage("Amount can not be negative.");
                return;
            }
            if (this.valueDt == null) {
                setMessage("Please fill Value date.");
                return;
            }
            if (!Validator.validateDate(dmy.format(valueDt))) {
                setMessage("Please fill proper date.");
                return;
            }
            if (valueDt.after(date)) {
                setMessage("Value date should be less than current date.");
                return;
            }
            //this.debitHead = common.getNfsAtmHead();
            gridDetail = new ArrayList<AtmMissingEntryPojo>();
            AtmMissingEntryPojo pojo = new AtmMissingEntryPojo();
            pojo.setAtmBranchTab(common.getAlphacodeByBrncode(getAtmBranch()));
            pojo.setAtmIdTab(getAtmId());
            pojo.setGlHeadTab(getGlHead());
            pojo.setAmountTab(Double.parseDouble(getAmount()));
            pojo.setValueDtTab(dmy.format(getValueDt()));
            pojo.setEnterByTab(getUserName());
            gridDetail.add(pojo);
            postButtonDisable = false;
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void postDetail() {
        setMessage("");
        try {

            if (gridDetail.get(0).getAtmIdTab() != null) {
                String result = miscRemote.getPostAtmMissingData(common.getAlphacodeByBrncode(this.atmBranch), this.atmId, this.glHead, Double.parseDouble(this.amount), ymd.format(valueDt), getUserName());
                if (result.substring(0, 4).equalsIgnoreCase("true")) {
                    setMessage("Atm missing entry successfully post.");
                }
                gridDetail = new ArrayList<AtmMissingEntryPojo>();
            }
            resetForm1();
            postButtonDisable = true;
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }

    }

    public void resetForm1() {
        this.setAtmId("S");
        this.setAtmId("");
        this.setGlHead("");
        this.setAmount("");
        this.setValueDt(date);
        this.setDebitHead("");
        gridDetail = new ArrayList<AtmMissingEntryPojo>();
        this.setPostButtonDisable(true);
    }

    public void resetForm() {
        setMessage("");
        this.setAtmId("S");
        this.setAtmId("");
        this.setGlHead("");
        this.setAmount("");
        this.setDebitHead("");
        this.setValueDt(date);
        gridDetail = new ArrayList<AtmMissingEntryPojo>();
        this.setPostButtonDisable(true);
    }

    public String exitForm() {
        resetForm();
        return "case1";
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public AtmMissingEntryPojo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(AtmMissingEntryPojo currentItem) {
        this.currentItem = currentItem;
    }

    public List<AtmMissingEntryPojo> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<AtmMissingEntryPojo> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public List<SelectItem> getAtmBranchList() {
        return atmBranchList;
    }

    public void setAtmBranchList(List<SelectItem> atmBranchList) {
        this.atmBranchList = atmBranchList;
    }

    public List<SelectItem> getAtmIdList() {
        return atmIdList;
    }

    public void setAtmIdList(List<SelectItem> atmIdList) {
        this.atmIdList = atmIdList;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAtmBranch() {
        return atmBranch;
    }

    public void setAtmBranch(String atmBranch) {
        this.atmBranch = atmBranch;
    }

    public String getAtmId() {
        return atmId;
    }

    public void setAtmId(String atmId) {
        this.atmId = atmId;
    }

    public String getDebitHead() {
        return debitHead;
    }

    public void setDebitHead(String debitHead) {
        this.debitHead = debitHead;
    }

    public String getGlHead() {
        return glHead;
    }

    public void setGlHead(String glHead) {
        this.glHead = glHead;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getValueDt() {
        return valueDt;
    }

    public void setValueDt(Date valueDt) {
        this.valueDt = valueDt;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public boolean isPostButtonDisable() {
        return postButtonDisable;
    }

    public void setPostButtonDisable(boolean postButtonDisable) {
        this.postButtonDisable = postButtonDisable;
    }
}
