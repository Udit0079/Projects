
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.neftrtgs;

import com.cbs.entity.neftrtgs.NeftOwDetails;
import com.cbs.facade.neftrtgs.NeftRtgsMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.pojo.neftrtgs.NeftRtgsReversalPojo;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;

/**
 *
 * @author Athar Reza
 */
public class NeftRtgsReversal extends BaseBean {
    
    private String message;
    private String branch;
    private List<SelectItem> branchList;
    private String date;
    int currentRow;
    boolean postButtonDisable;
    private NeftRtgsReversalPojo currentItem = new NeftRtgsReversalPojo();
    private Date dt = new Date();
    private CommonReportMethodsRemote common;
    private List<NeftRtgsReversalPojo> gridDetail;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    private NeftRtgsMgmtFacadeRemote remoteInterface = null;
    private final String jndiHomeName = "NeftRtgsMgmtFacade";
    
    public NeftRtgsReversal() {
        setDate(dmy.format(dt));
        try {
            branchList = new ArrayList<SelectItem>();
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            remoteInterface = (NeftRtgsMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            List list = common.getAlphacodeBasedOnBranch(getOrgnBrCode());
            for (int i = 0; i < list.size(); i++) {
                Vector vtr = (Vector) list.get(i);
                branchList.add(new SelectItem(CbsUtil.lPadding(2, Integer.parseInt(vtr.get(1).toString())), vtr.get(0).toString()));
            }
            postButtonDisable = true;
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }
    
    public void gridLoad() {
        setMessage("");
        try {
            if (date == null || date.equalsIgnoreCase("")) {
                setMessage("Please fill date !");
                return;
            }
            if (!new Validator().validateDate_dd_mm_yyyy(date)) {
                setMessage("Please fill proper date !");
                return;
            }
            gridDetail = new ArrayList<>();
            List dataList = new ArrayList<>();
            dataList = remoteInterface.getNeftRtgsReversalData(branch, date);
            if (!dataList.isEmpty()) {
                for (int i = 0; i < dataList.size(); i++) {
                    Vector vtr = (Vector) dataList.get(i);
                    NeftRtgsReversalPojo pojo = new NeftRtgsReversalPojo();
                    pojo.setDrAcnoNo(vtr.get(0).toString());
                    pojo.setCrAcnoNo(vtr.get(1).toString());
                    pojo.setCrAcnoName(vtr.get(2).toString());
                    pojo.setCrIfscCode(vtr.get(3).toString());
                    pojo.setAmount(new BigDecimal(vtr.get(4).toString()));
                    pojo.setChequeNo(vtr.get(5).toString());
                    pojo.setPaymentType(vtr.get(6).toString());
                    pojo.setStatus(vtr.get(7).toString());
                    pojo.setCmsBankRefNo(vtr.get(8).toString());
                    pojo.setUtrNo(vtr.get(9).toString());
                    pojo.setUniqueCustRefNo(vtr.get(10).toString());
                    pojo.setInitiatedBankName(vtr.get(11).toString());
                    gridDetail.add(pojo);
                }
            } else {
                setMessage("Data does not exist !");
            }
            postButtonDisable = false;
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }
    
    public void postDetail() {
        setMessage("");
        try {
            if (currentItem.getUniqueCustRefNo() == null) {
                this.setMessage("Please Select row from table !");
                return;
            }
            NeftOwDetails neftObj = new NeftOwDetails();
            neftObj.setUniqueCustomerRefNo(currentItem.getUniqueCustRefNo());
            neftObj.setDebitAccountNo(currentItem.getDrAcnoNo());
            neftObj.setTxnAmt(currentItem.getAmount());
            neftObj.setStatus(currentItem.getStatus());
            neftObj.setInitiatedBankName(currentItem.getInitiatedBankName().trim());
            String result = remoteInterface.neftRtgsReversal(neftObj, getUserName());
            gridDetail.remove(currentRow);
            //resetForm();
            if (result.equalsIgnoreCase("true")) {
                this.setMessage("Reversal has been done successfully.");
            }
            currentItem = new NeftRtgsReversalPojo();
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }
    
    public void resetForm() {
        setMessage("");
        setDate(dmy.format(dt));
        gridDetail = new ArrayList<NeftRtgsReversalPojo>();
        this.setPostButtonDisable(true);
    }
    
    public String exitForm() {
        return "case1";
    }
    
    public String getBranch() {
        return branch;
    }
    
    public void setBranch(String branch) {
        this.branch = branch;
    }
    
    public List<SelectItem> getBranchList() {
        return branchList;
    }
    
    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }
    
    public String getDate() {
        return date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public Date getDt() {
        return dt;
    }
    
    public void setDt(Date dt) {
        this.dt = dt;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public List<NeftRtgsReversalPojo> getGridDetail() {
        return gridDetail;
    }
    
    public void setGridDetail(List<NeftRtgsReversalPojo> gridDetail) {
        this.gridDetail = gridDetail;
    }
    
    public NeftRtgsReversalPojo getCurrentItem() {
        return currentItem;
    }
    
    public void setCurrentItem(NeftRtgsReversalPojo currentItem) {
        this.currentItem = currentItem;
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
