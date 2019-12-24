/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.other.LockerManagementFacadeRemote;
import com.cbs.web.pojo.other.LockerEnquiryGrid;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
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
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Admin
 */
public class LockerEnquiry extends BaseBean{

    private String errorMessage;
    private String message;
    private String searchWise;
    private String lableForChange;
    private String debtCustName;
    private String lockerNo;
    private String keyNo;
   // private HttpServletRequest req;
    private List<SelectItem> searchWiseList;
    private List<LockerEnquiryGrid> gridDetail;
    private final String jndiHomeName = "LockerManagementFacade";
    private LockerManagementFacadeRemote beanRemote = null;

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

    

//    public HttpServletRequest getReq() {
//        return req;
//    }
//
//    public void setReq(HttpServletRequest req) {
//        this.req = req;
//    }

    public String getSearchWise() {
        return searchWise;
    }

    public void setSearchWise(String searchWise) {
        this.searchWise = searchWise;
    }

    public List<SelectItem> getSearchWiseList() {
        return searchWiseList;
    }

    public void setSearchWiseList(List<SelectItem> searchWiseList) {
        this.searchWiseList = searchWiseList;
    }

//    public String getTodayDate() {
//        return todayDate;
//    }
//
//    public void setTodayDate(String todayDate) {
//        this.todayDate = todayDate;
//    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }

    public String getLableForChange() {
        return lableForChange;
    }

    public void setLableForChange(String lableForChange) {
        this.lableForChange = lableForChange;
    }

    public String getDebtCustName() {
        return debtCustName;
    }

    public void setDebtCustName(String debtCustName) {
        this.debtCustName = debtCustName;
    }

    public String getKeyNo() {
        return keyNo;
    }

    public void setKeyNo(String keyNo) {
        this.keyNo = keyNo;
    }

    public String getLockerNo() {
        return lockerNo;
    }

    public void setLockerNo(String lockerNo) {
        this.lockerNo = lockerNo;
    }

    public List<LockerEnquiryGrid> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<LockerEnquiryGrid> gridDetail) {
        this.gridDetail = gridDetail;
    }

    /** Creates a new instance of LockerEnquiry */
    public LockerEnquiry() {
       // req = getRequest();
        try {
            beanRemote = (LockerManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
//            String orgnBrIp = WebUtil.getClientIP(req);
//            InetAddress localhost = InetAddress.getByName(orgnBrIp);
//            orgnBrCode = Init.IP2BR.getBranch(localhost);
//            setUserName(req.getRemoteUser());
//            Date date = new Date();
//            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//            setTodayDate(sdf.format(date));
            this.setErrorMessage("");
            this.setMessage("");
            searchWiseList = new ArrayList<SelectItem>();
            searchWiseList.add(new SelectItem("--Select--"));
            searchWiseList.add(new SelectItem("Lesse Name Wise"));
            searchWiseList.add(new SelectItem("Locker No. Wise"));
            searchWiseList.add(new SelectItem("Key No. Wise"));
            searchWiseList.add(new SelectItem("Joint Holder Wise"));
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }

//    public HttpServletRequest getRequest() {
//        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//        if (request == null) {
//            throw new RuntimeException("Sorry. Got a null request from faces context");
//        }
//        return request;
//    }

    public void searchWiseMethod() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            if (this.searchWise.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select search wise.");
                this.setLableForChange("");
                //this.setDebtCustName("");
            } else if (this.searchWise.equalsIgnoreCase("Lesse Name Wise")) {
                this.setLableForChange("Customer Name :");
                this.setDebtCustName("");
            } else if (this.searchWise.equalsIgnoreCase("Locker No. Wise")) {
                this.setLableForChange("Locker No. :");
                this.setDebtCustName("");
            } else if (this.searchWise.equalsIgnoreCase("Key No. Wise")) {
                this.setLableForChange("Key No. :");
                this.setDebtCustName("");
            }else if (this.searchWise.equalsIgnoreCase("Joint Holder Wise")) {
                this.setLableForChange("Joint Holder Name :");
                this.setDebtCustName("");
            }
        } catch (Exception e) {
            setErrorMessage(e.getMessage());
        }
    }

    public void gridLoad() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            if (this.searchWise.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select search wise.");
                return;
            }
            if (lableForChange.equalsIgnoreCase("Customer Name :") || lableForChange.equalsIgnoreCase("Joint Holder Name :")) {
                if (this.debtCustName.equalsIgnoreCase("") || this.debtCustName.length() == 0) {
                    this.setErrorMessage("Please enter customer name.");
                    return;
                } else {
                    String name = this.debtCustName;
                    boolean blnAlpha = false;
                    char chr[] = null;
                    if (name != null) {
                        chr = name.toCharArray();
                    }
                    for (int i = 0; i < chr.length; i++) {
                        if ((chr[i] >= 'A' && chr[i] <= 'Z') || (chr[i] >= 'a' && chr[i] <= 'z')) {
                            blnAlpha = true;
                            break;
                        }
                    }
                    if (!blnAlpha == true) {
                        this.setErrorMessage("Please enter valid name.");
                        this.setMessage("");
                        return;
                    }
                }
            }
            if (lableForChange.equalsIgnoreCase("Locker No. :")) {
                Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
                if (this.debtCustName == null || this.debtCustName.equalsIgnoreCase("") || this.debtCustName.length() == 0) {
                    this.setErrorMessage("Please enter locker no.");
                    return;
                }
                if (this.debtCustName.contains(".")) {
                    this.setErrorMessage("Please enter valid locker no.");
                    return;
                }
                Matcher rentCheck = p.matcher(debtCustName);
                if (!rentCheck.matches()) {
                    this.setErrorMessage("Please enter valid locker no.");
                    return;
                }
            }
            if (lableForChange.equalsIgnoreCase("Key No. :")) {
                Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
                if (this.debtCustName == null || this.debtCustName.equalsIgnoreCase("") || this.debtCustName.length() == 0) {
                    this.setErrorMessage("Please enter key no.");
                    return;
                }
                if (this.debtCustName.contains(".")) {
                    this.setErrorMessage("Please enter valid key no.");
                    return;
                }
                Matcher rentCheck = p.matcher(debtCustName);
                if (!rentCheck.matches()) {
                    this.setErrorMessage("Please enter valid key no.");
                    return;
                }
            }
            gridDetail = new ArrayList<LockerEnquiryGrid>();
            List resultList = new ArrayList();
            resultList = beanRemote.gridDeatil1(lableForChange, this.debtCustName, getOrgnBrCode());
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    LockerEnquiryGrid detail = new LockerEnquiryGrid();
                    detail.setCabiNateNo(ele.get(0).toString());
                    detail.setLockerType(ele.get(1).toString());
                    detail.setLockerNo(ele.get(2).toString());
                    detail.setDebitedAcNo(ele.get(3).toString());
                    detail.setRent(Float.parseFloat(ele.get(4).toString()));
                    detail.setRentDueDt(ele.get(5).toString());
                    detail.setDebitedCustName(ele.get(6).toString());
                    detail.setKeyNo(ele.get(7).toString());
                    detail.setAdOpr1(ele.get(8).toString());
                    detail.setAdOpr2(ele.get(9).toString());
                    detail.setAdOpr3(ele.get(10).toString());
                    detail.setAdOpr4(ele.get(11).toString());
                    gridDetail.add(detail);
                }
            } else {
                this.setErrorMessage("No locker active.");
                return;
            }
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void resetForm() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setSearchWise("--Select--");
            this.setDebtCustName("");
            this.setLableForChange("");
            gridDetail = new ArrayList<LockerEnquiryGrid>();
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }

    public String exitFrm() {
        try {
            resetForm();
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
        return "case1";
    }
}
