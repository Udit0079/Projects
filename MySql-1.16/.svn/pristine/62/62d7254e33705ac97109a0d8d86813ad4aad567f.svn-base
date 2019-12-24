/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.misc;

import com.cbs.dto.PoSearchPojo;
import com.cbs.facade.inventory.InventorySplitAndMergeFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 *
 * @author Nishant Srivastava
 */
public class PoSearch extends BaseBean {

    private String seqNoInstNo;
    private String status;
    private Date tilldt;
    private String message;
    private boolean disInstNo;
    private boolean disDt;
    private String focusId;
    private InventorySplitAndMergeFacadeRemote invfacade;
    private List<PoSearchPojo> gridDetail;
    private List<SelectItem> statusList;
    SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyyMMdd");

    public String getFocusId() {
        return focusId;
    }

    public void setFocusId(String focusId) {
        this.focusId = focusId;
    }

    public List<PoSearchPojo> getGridDetail() {
        return gridDetail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setGridDetail(ArrayList<PoSearchPojo> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getSeqNoInstNo() {
        return seqNoInstNo;
    }

    public void setSeqNoInstNo(String seqNoInstNo) {
        this.seqNoInstNo = seqNoInstNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SelectItem> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<SelectItem> status1List) {
        this.statusList = status1List;
    }

    public Date getTilldt() {
        return tilldt;
    }

    public void setTilldt(Date tilldt) {
        this.tilldt = tilldt;
    }

    public boolean isDisDt() {
        return disDt;
    }

    public void setDisDt(boolean disDt) {
        this.disDt = disDt;
    }

    public boolean isDisInstNo() {
        return disInstNo;
    }

    public void setDisInstNo(boolean disInstNo) {
        this.disInstNo = disInstNo;
    }

    public PoSearch() {
        try {
            invfacade = (InventorySplitAndMergeFacadeRemote) ServiceLocator.getInstance().lookup("InventorySplitAndMergeFacade");
            statusList = new ArrayList<SelectItem>();
            statusList.add(new SelectItem("0", "--Select--"));
            statusList.add(new SelectItem("1", "Seq Number"));
            statusList.add(new SelectItem("2", "Inst Number"));
            statusList.add(new SelectItem("3", "Issue Date"));
            setTilldt(new Date());
            setFocusId("ddStatus");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void statusAction() {
        if (this.status.equals("1") || this.status.equals("2")) {
            this.disInstNo = false;
            this.disDt = true;
            setFocusId("seqNo");
        } else if (this.status.equals("3")) {
            this.disInstNo = true;
            this.disDt = false;
            setFocusId("frDate");
        } else {
            this.disInstNo = true;
            this.disDt = true;
            setFocusId("btnYes");
        }
    }

    public void getPoDetails() {
        try {
            if (this.tilldt == null) {
                setMessage("Please fill Date.");
            }
            if (this.seqNoInstNo == null) {
                setMessage("Please fill the Seq No/Inst No.");
            }
            if(this.seqNoInstNo.equals("")){
                setMessage("Please fill the Seq No/Inst No.");
            }
            if (this.status.equalsIgnoreCase("0")) {
                this.setMessage("Please select Search Option ");
                return;
            }
            setMessage("");
            gridDetail = new ArrayList<PoSearchPojo>();
            gridDetail = invfacade.getPoDetails(status, seqNoInstNo, dmyFormat.format(tilldt),getOrgnBrCode());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void refreshForm() {
        try {
            this.setMessage("");
            this.setStatus("--Select--");
            this.setSeqNoInstNo("");
            this.setTilldt(new Date());
            gridDetail = new ArrayList<PoSearchPojo>();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitBtnAction() {
        try {
            refreshForm();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }
}
