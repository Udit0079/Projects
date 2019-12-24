/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.misc;

import com.cbs.facade.misc.MinBalanceChargesFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.pojo.AtmReversalPostingPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Admin
 */
public class AtmReversalPosting extends BaseBean {

    private String message;
    private String frDate;
    private String toDate;
    int currentRow;
    boolean postButtonDisable;
    private AtmReversalPostingPojo currentItem = new AtmReversalPostingPojo();
    private Date dt = new Date();
    private CommonReportMethodsRemote common;
    MinBalanceChargesFacadeRemote miscRemote;
    private List<AtmReversalPostingPojo> gridDetail;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public AtmReversalPosting() {
        try {
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            miscRemote = (MinBalanceChargesFacadeRemote) ServiceLocator.getInstance().lookup("MinBalanceChargesFacade");
            postButtonDisable = true;
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void dateValdation() {
        try {
            if (frDate == null || frDate.equalsIgnoreCase("")) {
                setMessage("Please fill the from date!");
                return;
            }
            if (!Validator.validateDate(frDate)) {
                setMessage("Please fill Proper from date!");
                return;
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());

        }
    }

    public void gridLoad() {
        setMessage("");
        try {
            if (frDate == null || frDate.equalsIgnoreCase("")) {
                setMessage("Please fill the from date!");
                return;
            }
            if (!Validator.validateDate(frDate)) {
                setMessage("Please fill Proper from date!");
                return;
            }
            if (toDate == null || toDate.equalsIgnoreCase("")) {
                setMessage("Please fill the To date!");
                return;
            }
            if (!Validator.validateDate(toDate)) {
                setMessage("Please fill Proper To date!");
                return;
            }

            if (dmy.parse(frDate).after(dmy.parse(toDate))) {
                setMessage("Please Form date should be less than To date !");
                return;
            }
            gridDetail = new ArrayList<AtmReversalPostingPojo>();
            List dataList = miscRemote.getAtmRevsersalData(ymd.format(dmy.parse(frDate)), ymd.format(dmy.parse(toDate)));
            if (!dataList.isEmpty()) {
                for (int i = 0; i < dataList.size(); i++) {
                    Vector vtr = (Vector) dataList.get(i);
                    AtmReversalPostingPojo pojo = new AtmReversalPostingPojo();
                    if (vtr.get(0).toString().equalsIgnoreCase("0")) {
                        pojo.setReversalIndicater("Withdrawal");
                    } else {
                        pojo.setReversalIndicater("Reversal");
                    }
                    pojo.setAmount(Double.parseDouble(vtr.get(1).toString()));
                    pojo.setStNo(vtr.get(2).toString());
                    pojo.setOstNo(vtr.get(3).toString());
                    pojo.setCardNo(vtr.get(4).toString());
                    pojo.setTranDate(vtr.get(5).toString());
                    pojo.setIncomingDate(vtr.get(6).toString());
                    pojo.setProcessFlag(vtr.get(7).toString());
                    pojo.setProcessStatus(vtr.get(8).toString());
                    gridDetail.add(pojo);
                }
            } else {
                setMessage("Data does not exist !");
            }
            postButtonDisable = false;
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    public void postDetail() {
        setMessage("");
        try {

            if (currentItem.getStNo() == null) {
                this.setMessage("Please Select only one row from table !");
                return;
            }
            
            String result = miscRemote.getPostReversalData(currentItem, getUserName());
            gridDetail.remove(currentRow);
            if (result.substring(0, 4).equalsIgnoreCase("true")) {
                this.setMessage("Atm Reversal has been done successfully.");
            }
            currentItem = new AtmReversalPostingPojo();

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void resetForm() {
        setMessage("");
        // setDate(dmy.format(dt));
        this.setFrDate("");
        this.setToDate("");
        gridDetail = new ArrayList<AtmReversalPostingPojo>();
        this.setPostButtonDisable(true);
    }

    public String exitForm() {
        resetForm();
        return "case1";
    }

    public AtmReversalPostingPojo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(AtmReversalPostingPojo currentItem) {
        this.currentItem = currentItem;
    }

    public List<AtmReversalPostingPojo> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<AtmReversalPostingPojo> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public String getFrDate() {
        return frDate;
    }

    public void setFrDate(String frDate) {
        this.frDate = frDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isPostButtonDisable() {
        return postButtonDisable;
    }

    public void setPostButtonDisable(boolean postButtonDisable) {
        this.postButtonDisable = postButtonDisable;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
}
