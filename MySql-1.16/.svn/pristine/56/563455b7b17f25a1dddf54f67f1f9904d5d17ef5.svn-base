package com.cbs.web.controller.master;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.master.GeneralMasterFacadeRemote;
import com.cbs.facade.master.LedgerMasterFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.master.Trip;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

public final class LedgerCodeMaintenanceRegister extends BaseBean {
    
    private String message;
    private String glType;
    private String fromNo;
    private String toNo;
    private List<SelectItem> glTypeList;
    private List<Trip> trip;
    private LedgerMasterFacadeRemote ledCodeMReg;
    private GeneralMasterFacadeRemote genFacadeRemote;
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    
    public List<Trip> getTrip() {
        return trip;
    }
    
    public void setTrip(List<Trip> trip) {
        this.trip = trip;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getGlType() {
        return glType;
    }
    
    public void setGlType(String glType) {
        this.glType = glType;
    }
    
    public List<SelectItem> getGlTypeList() {
        return glTypeList;
    }
    
    public void setGlTypeList(List<SelectItem> glTypeList) {
        this.glTypeList = glTypeList;
    }
    
    public String getFromNo() {
        return fromNo;
    }
    
    public void setFromNo(String fromNo) {
        this.fromNo = fromNo;
    }
    
    public String getToNo() {
        return toNo;
    }
    
    public void setToNo(String toNo) {
        this.toNo = toNo;
    }
    
    public LedgerCodeMaintenanceRegister() {
        try {
            ledCodeMReg = (LedgerMasterFacadeRemote) ServiceLocator.getInstance().lookup("LedgerMasterFacade");
            genFacadeRemote = (GeneralMasterFacadeRemote) ServiceLocator.getInstance().lookup("GeneralMasterFacade");
            resetValues();
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
    
    public void onLoad() {
        try {
            glTypeList = new ArrayList<SelectItem>();
            glTypeList.add(new SelectItem("0", "--Select--"));
            List glList = genFacadeRemote.getRefCodeAndDescByNo("005");
            if (glList.isEmpty()) {
                this.setMessage("Please fill data in CBS REF REC TYPE for 005");
                return;
            } else {
                for (int i = 0; i < glList.size(); i++) {
                    Vector v1 = (Vector) glList.get(i);
                    glTypeList.add(new SelectItem(v1.get(0).toString()));
                }
                getTripData();
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
    
    public void getTripData() {
        this.setMessage("");
        try {
            trip = new ArrayList<Trip>();
            List list = ledCodeMReg.getAllDataFromGlDescRange();
            if (list.isEmpty()) {
                this.setMessage("There is no data. So you can add.");
                return;
            }
            for (int i = 0; i < list.size(); i++) {
                Vector element = (Vector) list.get(i);
                Trip pojo = new Trip();
                pojo.setDesc(element.get(0).toString());
                pojo.setCode(element.get(1).toString());
                pojo.setGlCode(element.get(2).toString());
                
                trip.add(pojo);
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    
    public String validation() {
        try {
            if (this.fromNo == null || this.toNo == null || this.glType.equals("0") || this.fromNo.equals("") || this.toNo.equals("")) {
                return "Please check because all three fields are mandatory.";
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (!this.fromNo.matches("[0-9]*")) {
                return "From no must be numeric only..";
            }
            if (!this.toNo.matches("[0-9]*")) {
                return "To no must be numeric only..";
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
        return "true";
    }
    
    public void saveAction() {
        this.setMessage("");
        try {
            if (!validation().equals("true")) {
                this.setMessage(validation());
                return;
            }
            String msg = ledCodeMReg.createGlRange(this.glType, Integer.parseInt(this.fromNo), Integer.parseInt(this.toNo), getUserName());
            if (msg.equalsIgnoreCase("true")) {
                resetValues();
                this.setMessage("Gl range has been created successfully.");
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
    
    public void resetValues() {
        try {
            this.setMessage("");
            this.setGlType("0");
            this.setFromNo("");
            this.setToNo("");
            onLoad();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
    
    public String btnExit() {
        resetValues();
        return "case1";
    }
}
