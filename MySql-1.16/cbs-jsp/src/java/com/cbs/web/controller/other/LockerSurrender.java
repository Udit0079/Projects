/*
 * Created By:ROHIT KRISHNA GUPTA
 */
package com.cbs.web.controller.other;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.share.ShareTransferFacadeRemote;
import com.cbs.facade.other.LockerManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.pojo.other.LockerSurrenderGrid;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.other.LockerEnquiryGrid;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

/**
 *
 * @author Admin
 */
public class LockerSurrender extends BaseBean {

    private String errorMessage;
    private String message;
    private String cabNo;
    private String lockerType;
    private String lockerNo;
    private String lesseName;
    private String lesseAcNo;
    private String rentDueDt;
    private List<SelectItem> cabNoList;
    private List<SelectItem> lockerTypeList;
    private List<LockerSurrenderGrid> gridDetail;
    private List<LockerEnquiryGrid> authGridDetail;
    private String acCloseFlag;
    private final String jndiHomeName = "LockerManagementFacade";
    private LockerManagementFacadeRemote beanRemote = null;
    private ShareTransferFacadeRemote shareRemoteObject = null;
    private String opt;
    private List<SelectItem> optList;
    private String disView;
    private String disView1;
    private String focusId;
    private boolean disCab;
    private boolean disLocker;
    private boolean disLocNo;
    private LockerEnquiryGrid currentItem;
    private String pageEnterBy;
    private boolean disDelete;
    private String pAcNo;

    public List<SelectItem> getCabNoList() {
        return cabNoList;
    }

    public void setCabNoList(List<SelectItem> cabNoList) {
        this.cabNoList = cabNoList;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SelectItem> getLockerTypeList() {
        return lockerTypeList;
    }

    public void setLockerTypeList(List<SelectItem> lockerTypeList) {
        this.lockerTypeList = lockerTypeList;
    }

    public String getCabNo() {
        return cabNo;
    }

    public void setCabNo(String cabNo) {
        this.cabNo = cabNo;
    }

    public String getLockerType() {
        return lockerType;
    }

    public void setLockerType(String lockerType) {
        this.lockerType = lockerType;
    }

    public String getLockerNo() {
        return lockerNo;
    }

    public void setLockerNo(String lockerNo) {
        this.lockerNo = lockerNo;
    }

    public String getLesseAcNo() {
        return lesseAcNo;
    }

    public void setLesseAcNo(String lesseAcNo) {
        this.lesseAcNo = lesseAcNo;
    }

    public String getLesseName() {
        return lesseName;
    }

    public void setLesseName(String lesseName) {
        this.lesseName = lesseName;
    }

    public String getRentDueDt() {
        return rentDueDt;
    }

    public void setRentDueDt(String rentDueDt) {
        this.rentDueDt = rentDueDt;
    }

    public List<LockerSurrenderGrid> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<LockerSurrenderGrid> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getAcCloseFlag() {
        return acCloseFlag;
    }

    public void setAcCloseFlag(String acCloseFlag) {
        this.acCloseFlag = acCloseFlag;
    }

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    public List<SelectItem> getOptList() {
        return optList;
    }

    public void setOptList(List<SelectItem> optList) {
        this.optList = optList;
    }

    public String getDisView() {
        return disView;
    }

    public void setDisView(String disView) {
        this.disView = disView;
    }

    public String getDisView1() {
        return disView1;
    }

    public void setDisView1(String disView1) {
        this.disView1 = disView1;
    }    

    public List<LockerEnquiryGrid> getAuthGridDetail() {
        return authGridDetail;
    }

    public void setAuthGridDetail(List<LockerEnquiryGrid> authGridDetail) {
        this.authGridDetail = authGridDetail;
    }

    public String getFocusId() {
        return focusId;
    }

    public void setFocusId(String focusId) {
        this.focusId = focusId;
    }

    public boolean isDisCab() {
        return disCab;
    }

    public void setDisCab(boolean disCab) {
        this.disCab = disCab;
    }

    public boolean isDisLocker() {
        return disLocker;
    }

    public void setDisLocker(boolean disLocker) {
        this.disLocker = disLocker;
    }

    public boolean isDisLocNo() {
        return disLocNo;
    }

    public void setDisLocNo(boolean disLocNo) {
        this.disLocNo = disLocNo;
    }

    public LockerEnquiryGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(LockerEnquiryGrid currentItem) {
        this.currentItem = currentItem;
    }

    public String getPageEnterBy() {
        return pageEnterBy;
    }

    public void setPageEnterBy(String pageEnterBy) {
        this.pageEnterBy = pageEnterBy;
    }

    public boolean isDisDelete() {
        return disDelete;
    }

    public void setDisDelete(boolean disDelete) {
        this.disDelete = disDelete;
    }    

    public String getpAcNo() {
        return pAcNo;
    }

    public void setpAcNo(String pAcNo) {
        this.pAcNo = pAcNo;
    }
    
    /** Creates a new instance of LockerSurrender */
    public LockerSurrender() {
        try {
            beanRemote = (LockerManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            shareRemoteObject = (ShareTransferFacadeRemote) ServiceLocator.getInstance().lookup("ShareTransferFacade");
            if (getHttpRequest().getParameter("code") == null || getHttpRequest().getParameter("code").toString().equalsIgnoreCase("")) {
                this.setAcCloseFlag("");
            } else {
                this.setAcCloseFlag(getHttpRequest().getParameter("code").toString());
            }
            this.setErrorMessage("");
            this.setMessage("");
            lockerTypeList = new ArrayList<SelectItem>();
            lockerTypeList.add(new SelectItem("--Select--"));
            
            String levelId = shareRemoteObject.getLevelId(getUserName(), getOrgnBrCode());
            
            optList = new ArrayList<SelectItem>();
            optList.add(new SelectItem("1", "Add"));
            if (levelId.equals("1") || levelId.equals("2")) {
                optList.add(new SelectItem("3", "Verify"));
            }
            setDisView1("none");
            setDisView(""); 
            cabNoCombo();
            setDisDelete(true);
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }
    
    public void optAction(){
        this.setErrorMessage("");
        this.setMessage("");
        this.setCabNo("--Select--");
        this.setLockerType("--Select--");
        this.setLockerNo("");
        this.setLesseAcNo("");
        this.setLesseName("");
        this.setRentDueDt("");
        gridDetail = new ArrayList<LockerSurrenderGrid>();
        if(opt.equalsIgnoreCase("1")){
            setDisView1("none");
            setDisView("");
            setDisCab(false);
            setDisLocker(false);
            setDisLocNo(false);
            setFocusId("ddCabNo");
            setDisDelete(true);
        }else if(opt.equalsIgnoreCase("3")){
            setDisView("none");
            setDisView1("");
            setDisCab(true);
            setDisLocker(true);
            setDisLocNo(true);
            getunAuthDetail();
            setFocusId("authGrid");
        }
    }

    public void cabNoCombo() {
        try {
            List resultList = new ArrayList();
            resultList = beanRemote.cabNo(getOrgnBrCode());
            cabNoList = new ArrayList<SelectItem>();
            cabNoList.add(new SelectItem("--Select--"));
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                for (Object ee : ele) {
                    cabNoList.add(new SelectItem(ee.toString()));
                }
            }
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void lockerTypeCombo() {
        this.setErrorMessage("");
        this.setMessage("");
        if (this.cabNo.equalsIgnoreCase("--Select--")) {
            this.setErrorMessage("Please select cab no. !!!");
            return;
        }
        try {
            List resultList = new ArrayList();
            resultList = beanRemote.lockerType(getOrgnBrCode(), this.cabNo);
            lockerTypeList = new ArrayList<SelectItem>();
            lockerTypeList.add(new SelectItem("--Select--"));
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                for (Object ee : ele) {
                    lockerTypeList.add(new SelectItem(ee.toString()));
                }
            }
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void lockerNoLostFocus() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setLesseAcNo("");
        this.setLesseName("");
        this.setRentDueDt("");
        gridDetail = new ArrayList<LockerSurrenderGrid>();
        try {
            if (this.cabNo.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select cab no. !!!");
                return;
            }
            if (this.lockerType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select locker type !!!");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.lockerNo.equalsIgnoreCase("") || this.lockerNo.length() == 0) {
                this.setErrorMessage("Please enter locker no.(NUMBERS ONLY) !!!");
                return;
            }
            Matcher rentCheck = p.matcher(lockerNo);
            if (!rentCheck.matches()) {
                this.setErrorMessage("Please enter valid locker no.(NUMBERS ONLY) !!!");
                return;
            }
            if (this.lockerNo.contains(".")) {
                this.setErrorMessage("Please enter valid locker no.(NUMBERS ONLY) !!!");
                return;
            }
            List resultList = new ArrayList();
            resultList = beanRemote.lockerNoLostFocus1(getOrgnBrCode(), this.cabNo, this.lockerNo, this.lockerType);
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector recLst = (Vector) resultList.get(i);
                    if (recLst.get(5).toString().equalsIgnoreCase("N")) {
                        this.setErrorMessage("Please authorize the issued locker.");
                        return;
                    } else {
                        String msg = beanRemote.unAuthLockerExist(getOrgnBrCode(), cabNo, lockerNo, lockerType, recLst.get(3).toString());
                        if(msg.equalsIgnoreCase("true")){
                            this.setLesseAcNo(recLst.get(3).toString());
                            this.setLesseName(recLst.get(4).toString());
                            rentDueDate();
                            gridLoad();
                        }else{
                            this.setErrorMessage("Locker no. already surrenderd and pending for authorization !!!");
                            return;
                        }
                    }
                }
            } else {
                this.setErrorMessage("Locker no. does not exists !!!");
                return;
            }
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void rentDueDate() {
        try {
            List resultList = new ArrayList();
            resultList = beanRemote.rentDueDt(getOrgnBrCode(), this.cabNo, this.lockerNo, this.lockerType);
            if (!resultList.isEmpty()) {
                Vector recLst = (Vector) resultList.get(0);
                this.setRentDueDt(recLst.get(0).toString());
            } else {
                return;
            }
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void gridLoad() {
        gridDetail = new ArrayList<LockerSurrenderGrid>();
        try {
            List resultList = new ArrayList();
            resultList = beanRemote.gridLoad(getOrgnBrCode(), this.cabNo, this.lockerNo, this.lockerType,this.lesseAcNo);
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    LockerSurrenderGrid detail = new LockerSurrenderGrid();
                    detail.setCabNo(ele.get(0).toString());
                    detail.setLockerType(ele.get(1).toString());
                    detail.setLockerNo(ele.get(2).toString());
                    detail.setAcctNo(ele.get(3).toString());
                    detail.setCustname(ele.get(4).toString());
                    detail.setRent(Double.parseDouble(ele.get(5).toString()));
                    detail.setRentDueDt(ele.get(6).toString());
                    detail.setMode(ele.get(7).toString());
                    detail.setAdOpr1(ele.get(8).toString());
                    detail.setAdOpr2(ele.get(9).toString());
                    detail.setAdOpr3(ele.get(10).toString());
                    detail.setAdOpr4(ele.get(11).toString());
                    detail.setSecurity(Double.parseDouble(ele.get(12).toString()));
                    gridDetail.add(detail);
                }
            } else {
                return;
            }
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }
    
    public void deleteBtn(){
        try {
            if (this.cabNo.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select cab no. !!!");
                return;
            }
            if (this.lockerType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select locker type !!!");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.lockerNo.equalsIgnoreCase("") || this.lockerNo.length() == 0) {
                this.setErrorMessage("Please enter locker no.");
                return;
            }
            Matcher rentCheck = p.matcher(lockerNo);
            if (!rentCheck.matches()) {
                this.setErrorMessage("Please enter valid locker no.(NUMBERS ONLY) !!!");
                return;
            }
            if (this.lesseName == null || this.lesseName.equalsIgnoreCase("") || this.lesseName.length() == 0) {
                this.setErrorMessage("Lesse's name is blank !!!");
                return;
            }
            if (this.lesseAcNo == null || this.lesseAcNo.equalsIgnoreCase("") || this.lesseAcNo.length() == 0) {
                this.setErrorMessage("Lesse's A/C. No. is blank !!!");
                return;
            }
            if (this.rentDueDt == null || this.rentDueDt.equalsIgnoreCase("") || this.rentDueDt.length() == 0) {
                this.setErrorMessage("Rent due date is blank !!!");
                return;
            }
            
            String result = beanRemote.deleteLocker(getOrgnBrCode(), this.cabNo, this.lockerNo, this.lockerType, getUserName(), pAcNo);
            if (result == null) {
                this.setMessage("Problem occurred in locker surrendering !!!");
                return;
            } else {
                if (result.equals("true")) {
                    getunAuthDetail();
                    this.setCabNo("--Select--");
                    this.setLockerType("--Select--");
                    this.setLockerNo("");
                    this.setLesseAcNo("");  
                    this.setLesseName("");
                    this.setRentDueDt("");
                    this.setErrorMessage("");
                    this.setDisDelete(true);
                    this.setMessage("Locker has been deleted successfully.");                    
                } else {
                    this.setErrorMessage(result);
                    return;
                }
            }
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void surrenderBtn() {
        try {
            if (this.cabNo.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select cab no. !!!");
                return;
            }
            if (this.lockerType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select locker type !!!");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.lockerNo.equalsIgnoreCase("") || this.lockerNo.length() == 0) {
                this.setErrorMessage("Please enter locker no.");
                return;
            }
            Matcher rentCheck = p.matcher(lockerNo);
            if (!rentCheck.matches()) {
                this.setErrorMessage("Please enter valid locker no.(NUMBERS ONLY) !!!");
                return;
            }
            if (this.lesseName == null || this.lesseName.equalsIgnoreCase("") || this.lesseName.length() == 0) {
                this.setErrorMessage("Lesse's name is blank !!!");
                return;
            }
            if (this.lesseAcNo == null || this.lesseAcNo.equalsIgnoreCase("") || this.lesseAcNo.length() == 0) {
                this.setErrorMessage("Lesse's A/C. No. is blank !!!");
                return;
            }
            if (this.rentDueDt == null || this.rentDueDt.equalsIgnoreCase("") || this.rentDueDt.length() == 0) {
                this.setErrorMessage("Rent due date is blank !!!");
                return;
            }
            
            if (opt.equalsIgnoreCase("1")) {
                String result = beanRemote.surrenderLocker(getOrgnBrCode(), this.cabNo, this.lockerNo, this.lockerType, getUserName());
                if (result == null) {
                   this.setMessage("Problem occurred in locker surrendering !!!");
                    return;
                } else {
                    if (result.equals("true")) {
                        this.setErrorMessage("");
                        this.setMessage("Locker has been successfully surrendered.");
                        gridLoad();
                        this.setCabNo("--Select--");
                        this.setLockerType("--Select--");
                        this.setLockerNo("");
                    } else {
                        this.setErrorMessage(result);
                        return;
                    }
                }
            }else if (opt.equalsIgnoreCase("3")) {
                if (pageEnterBy.equalsIgnoreCase(getUserName())) {
                    this.setMessage("You Can't Authorize Your Own Entry.");
                    return;
                } else {
                    String result = beanRemote.surrenderLockerAuth(getOrgnBrCode(), this.cabNo, this.lockerNo, this.lockerType, getUserName());
                    if (result == null) {
                        this.setMessage("Problem occurred in locker surrendering !!!");
                        return;
                    } else {
                        if (result.equalsIgnoreCase("true")) {
                            this.setErrorMessage("");
                            this.setMessage("Locker has been successfully authorized.");
                            this.setCabNo("--Select--");
                            this.setLockerType("--Select--");
                            this.setLockerNo("");
                            this.setDisDelete(true);
                            getunAuthDetail();
                        } else {
                            this.setErrorMessage(result);
                            return;
                        }
                    }
                }
            }            
            this.setLesseAcNo("");
            this.setLesseName("");
            this.setRentDueDt("");
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }
    
    public void getunAuthDetail() {
        this.setErrorMessage("");
        this.setMessage("");
        authGridDetail = new ArrayList<LockerEnquiryGrid>();
        try {
            List resultList = new ArrayList();
            resultList = beanRemote.getUnAuthSurrenderLocker(getOrgnBrCode());
            if (resultList.isEmpty()) {
                this.setErrorMessage("No Detail Pending For Authorization");
                return;
            } else {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    LockerEnquiryGrid detail = new LockerEnquiryGrid();
                    detail.setCabiNateNo(ele.get(0).toString());
                    detail.setLockerType(ele.get(1).toString());
                    detail.setLockerNo(ele.get(2).toString());
                    detail.setDebitedAcNo(ele.get(3).toString());
                    detail.setDebitedCustName(ele.get(4).toString());
                    authGridDetail.add(detail);                
                }
            }
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }
    
    public void setTableDataToForm() {
        this.setMessage("");
        try {
            if (currentItem != null) {                
                this.setCabNo(currentItem.getCabiNateNo().toString());
                lockerTypeCombo();
                this.setLockerType(currentItem.getLockerType().toString());
                this.setLockerNo(currentItem.getLockerNo().toString()); 
                this.setPageEnterBy(currentItem.getDebitedCustName());
                this.setpAcNo(currentItem.getDebitedAcNo());
            }
            List resultList = new ArrayList();
            resultList = beanRemote.lockerNoLostFocus1(getOrgnBrCode(), this.cabNo, this.lockerNo, this.lockerType);
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector recLst = (Vector) resultList.get(i);
                    if (recLst.get(5).toString().equalsIgnoreCase("N")) {
                        this.setErrorMessage("Please authorize the issued locker.");
                        return;
                    } else {
                        this.setLesseAcNo(recLst.get(3).toString());
                        this.setLesseName(recLst.get(4).toString());
                        rentDueDate(); 
                        setDisDelete(false);
                    }
                }
            } else {
                this.setErrorMessage("Locker no. does not exists !!!");
                return;
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void resetForm() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setCabNo("--Select--");
            this.setLockerType("--Select--");
            this.setLockerNo("");
            this.setLesseAcNo("");
            this.setLesseName("");
            this.setRentDueDt("");
            this.setOpt("1");
            this.setPageEnterBy("");
            setDisDelete(true);
            this.setpAcNo("");
            gridDetail = new ArrayList<LockerSurrenderGrid>();
            authGridDetail = new ArrayList<LockerEnquiryGrid>();
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }

    public String exitFrm() {
        ExternalContext ext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            if (this.acCloseFlag == null || this.acCloseFlag.equalsIgnoreCase("")) {
                resetForm();
                return "case1";
            } else {
                this.setAcCloseFlag(null);
                ext.redirect(ext.getRequestContextPath() + "/faces/pages/admin/AccountClosingRegister.jsp?code=" + 1);
                resetForm();
                return "case2";
            }
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
        return "case1";
    }
}
