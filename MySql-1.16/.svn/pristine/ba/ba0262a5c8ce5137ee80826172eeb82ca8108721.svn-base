/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.facade.other.NpciMgmtFacadeRemote;
import com.cbs.pojo.AadharLpgStatusPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

public class AadharLpgStatus extends BaseBean {

    private String msg;
    private String status;
    private String type;
    private String aadharNo;
    private List<SelectItem> statusList;
    private List<SelectItem> typeList;
    private List<AadharLpgStatusPojo> gridDetail;
    private AadharLpgStatusPojo currentItem;
    private NpciMgmtFacadeRemote npciMgmtFacadeRemote = null;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Date date = new Date();

    public AadharLpgStatus() {
        try {
            npciMgmtFacadeRemote = (NpciMgmtFacadeRemote) ServiceLocator.getInstance().lookup("NpciMgmtFacade");
            statusList = new ArrayList<SelectItem>();
            statusList.add(new SelectItem("", "-Select-"));
            statusList.add(new SelectItem("C", "Current"));
            statusList.add(new SelectItem("H", "History"));
            statusList.add(new SelectItem("CB", "CBS Level Registration"));

            typeList = new ArrayList<SelectItem>();
            typeList.add(new SelectItem("", "-Select-"));
            typeList.add(new SelectItem("AD", "Aadhar No"));
            typeList.add(new SelectItem("NA", "Lpg Id"));
            refreshForm();
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }

    }

    public boolean validateField() {
        this.setMsg("");
        Pattern p = Pattern.compile("[0-9]*");
        try {
            if (this.status == null || this.status.equalsIgnoreCase("-Select-") || this.status.equals("")) {
                setMsg("Please select Status.");
                return false;
            }
            if (this.type == null || this.type.equalsIgnoreCase("-Select-") || this.type.equals("")) {
                setMsg("Please select Id Type.");
                return false;
            }
            if (this.aadharNo == null || this.aadharNo.equalsIgnoreCase("")) {
                setMsg("Please fill LPG Id/Aadhar No.");
                return false;
            }
            Matcher matcher = p.matcher(this.aadharNo);
            if (!matcher.matches()) {
                setMsg("Aadhar Number/Lpg Id should be in numeric digits.");
                return false;
            }
            if (this.type.equals("AD") && this.aadharNo.length() != 12) {
                setMsg("Aadhar Number Should be 12 Digit");
                return false;
            }
            if (this.type.equals("NA") && this.aadharNo.length() != 17) {
                setMsg("Lpg Id Should be 17 Digit");
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public void showStatus() {
        this.setMsg("");
        try {
            if (validateField()) {
                if (this.status.equalsIgnoreCase("CB")) {
                    gridDetail = npciMgmtFacadeRemote.getAadharAndLpgCbsRegistration(this.type, this.aadharNo);
                } else {
                    gridDetail = npciMgmtFacadeRemote.getAadharLpgStatus(this.status, this.aadharNo);
                }
                if (gridDetail.isEmpty()) {
                    this.setMsg("This Aadhar No/LPG Id -- >" + this.aadharNo + " Does Not Exist !");
                }
            }
        } catch (Exception e) {
            setMsg(e.getMessage());
        }
    }

    public void refreshForm() {
        this.setMsg("");
        this.setStatus("-Select-");
        this.setType("-Select-");
        this.setAadharNo("");
        gridDetail = new ArrayList<AadharLpgStatusPojo>();
    }

    public String exitBtnAction() {
        refreshForm();
        return "case1";
    }

    //Getter And Setter
    public AadharLpgStatusPojo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(AadharLpgStatusPojo currentItem) {
        this.currentItem = currentItem;
    }

    public List<AadharLpgStatusPojo> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<AadharLpgStatusPojo> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public List<SelectItem> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<SelectItem> statusList) {
        this.statusList = statusList;
    }

    public NpciMgmtFacadeRemote getNpciMgmtFacadeRemote() {
        return npciMgmtFacadeRemote;
    }

    public void setNpciMgmtFacadeRemote(NpciMgmtFacadeRemote npciMgmtFacadeRemote) {
        this.npciMgmtFacadeRemote = npciMgmtFacadeRemote;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getAadharNo() {
        return aadharNo;
    }

    public void setAadharNo(String aadharNo) {
        this.aadharNo = aadharNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<SelectItem> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<SelectItem> typeList) {
        this.typeList = typeList;
    }
}
