/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.npci;

import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.other.OtherNpciMgmtFacadeRemote;
import com.cbs.pojo.OnlineAadharRegistrationPojo;
import com.cbs.pojo.ThresoldTransactionInfoPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;

/**
 *
 * @author root
 */
public class OnlineAadharUpdation extends BaseBean {

    private String errorMessage;
    private String branch;
    private List<SelectItem> branchList;
    private String frDt;
    private String toDt;
    private String noOfRecord;
    private boolean allSelected;
    private List<OnlineAadharRegistrationPojo> gridDetail;
    private OtherNpciMgmtFacadeRemote otherNpciRemote;
    private FtsPostingMgmtFacadeRemote ftsRemote;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public OnlineAadharUpdation() {
        try {
            this.setFrDt(dmy.format(new Date()));
            this.setToDt(dmy.format(new Date()));

            otherNpciRemote = (OtherNpciMgmtFacadeRemote) ServiceLocator.getInstance().lookup("OtherNpciMgmtFacade");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
        } catch (Exception ex) {
            setErrorMessage(ex.getMessage());
        }
    }

    public void setAadharData() {
        setErrorMessage("");
        int recordNo = 0;
        try {
            if (frDt == null || frDt.equalsIgnoreCase("")) {
                setErrorMessage("Please fill From Date");
                return;
            }
            if (toDt == null || toDt.equalsIgnoreCase("")) {
                setErrorMessage("Please fill To Date");
                return;
            }
            if (noOfRecord == null || noOfRecord.equalsIgnoreCase("")) {
                setErrorMessage("Please fill No of Record");
                return;
            }
            gridDetail = new ArrayList<OnlineAadharRegistrationPojo>();
            List list = otherNpciRemote.getOlineAadharRegistrationData(ymd.format(dmy.parse(frDt)), ymd.format(dmy.parse(toDt)), getOrgnBrCode());
            recordNo = list.size();
            if (!list.isEmpty()) {
                if (recordNo <= Integer.parseInt(noOfRecord)) {
                    recordNo = recordNo;
                } else {
                    recordNo = Integer.parseInt(noOfRecord);
                }
                for (int i = 0; i < recordNo; i++) {
                    Vector vtr = (Vector) list.get(i);
                    OnlineAadharRegistrationPojo pojo = new OnlineAadharRegistrationPojo();
                    pojo.setCustId(vtr.get(0).toString());
                    pojo.setAcNo(vtr.get(1).toString());
                    pojo.setCustName(vtr.get(2).toString());
                    pojo.setMobileNo(vtr.get(3).toString());
                    pojo.setAadharNo(vtr.get(4).toString().substring(0, 12));
                    pojo.setAadharNoInCbs(otherNpciRemote.getAadharNoByCustId(vtr.get(0).toString()));
                    pojo.setFatherName(vtr.get(5).toString());
                    pojo.setAddress(vtr.get(6).toString());
                    pojo.setDob(vtr.get(7).toString());
                    pojo.setRequestNo(vtr.get(8).toString());
                    pojo.setRequestType(vtr.get(9).toString());
                    pojo.setEntry_by(getUserName());
                    pojo.setSelected(true);
                    gridDetail.add(pojo);
                    allSelected = true;
                }
            }

            // this.setErrorMessage("Please Tick the check box for updation !");
        } catch (Exception e) {
            setErrorMessage(e.getMessage());
        }
    }

    public void setAllBoxSelected() {
        try {
            System.out.println("In All Selected CheckBox.");
            if (allSelected) {
                for (OnlineAadharRegistrationPojo pojo : gridDetail) {
                    pojo.setSelected(true);
                }
            } else {
                for (OnlineAadharRegistrationPojo pojo : gridDetail) {
                    pojo.setSelected(false);
                }
            }
        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
    }

    public void processAction() {
        setErrorMessage("");
        try {

            if (frDt == null || frDt.equalsIgnoreCase("")) {
                setErrorMessage("Please fill From Date");
                return;
            }
            if (toDt == null || toDt.equalsIgnoreCase("")) {
                setErrorMessage("Please fill To Date");
                return;
            }
            if (noOfRecord == null || noOfRecord.equalsIgnoreCase("")) {
                setErrorMessage("Please fill No of Record");
                return;
            }
            String result = otherNpciRemote.onlineAadharUpdation(gridDetail, frDt, getUserName(), getOrgnBrCode());
            if (result.equalsIgnoreCase("true")) {
                setErrorMessage("Data update successfully");
                clear();
            }
        } catch (Exception e) {
            setErrorMessage(e.getMessage());
        }
    }

    public void btnRefreshAction() {
        setErrorMessage("");
        this.setFrDt(dmy.format(new Date()));
        this.setToDt(dmy.format(new Date()));
        this.noOfRecord = "";
        this.allSelected = false;
        setAllBoxSelected();
        gridDetail = new ArrayList<OnlineAadharRegistrationPojo>();
    }

    public void clear() {
        this.setFrDt(dmy.format(new Date()));
        this.setToDt(dmy.format(new Date()));
        this.noOfRecord = "";
        this.allSelected = false;
        setAllBoxSelected();
        gridDetail = new ArrayList<OnlineAadharRegistrationPojo>();
    }

    public String btnExitAction() {
        return "case1";
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
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

    public String getFrDt() {
        return frDt;
    }

    public void setFrDt(String frDt) {
        this.frDt = frDt;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public String getNoOfRecord() {
        return noOfRecord;
    }

    public void setNoOfRecord(String noOfRecord) {
        this.noOfRecord = noOfRecord;
    }

    public boolean isAllSelected() {
        return allSelected;
    }

    public void setAllSelected(boolean allSelected) {
        this.allSelected = allSelected;
    }

    public List<OnlineAadharRegistrationPojo> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<OnlineAadharRegistrationPojo> gridDetail) {
        this.gridDetail = gridDetail;
    }
}
