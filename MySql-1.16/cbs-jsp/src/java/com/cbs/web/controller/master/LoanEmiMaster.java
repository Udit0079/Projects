package com.cbs.web.controller.master;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.master.BankAndLoanMasterFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.master.EMIMasterGrid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

/**
 *
 * @author ROHIT KRISHNA
 */
public final class LoanEmiMaster extends BaseBean {

    BankAndLoanMasterFacadeRemote emiMaster;
    private String errorMessage;
    private String message;
    private String oldAcctNo;
    private String acctNo, acNoLen;
    private String custName;
    private String sno;
    private String installAmt;
    private String status;
    private Date dueDt;
    private Date paytmentDt;
    private String excessAmt;
    private boolean flag1;
    private List<SelectItem> statusList;
    private List<EMIMasterGrid> gridDetail;
    int currentRow;
    private EMIMasterGrid currentItem = new EMIMasterGrid();
    private final String FtsPostingMgmtFacadeJndiName = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote fts = null;
    private String acCloseFlag;
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");

    public String getOldAcctNo() {
        return oldAcctNo;
    }

    public void setOldAcctNo(String oldAcctNo) {
        this.oldAcctNo = oldAcctNo;
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public Date getDueDt() {
        return dueDt;
    }

    public void setDueDt(Date dueDt) {
        this.dueDt = dueDt;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getExcessAmt() {
        return excessAmt;
    }

    public void setExcessAmt(String excessAmt) {
        this.excessAmt = excessAmt;
    }

    public String getInstallAmt() {
        return installAmt;
    }

    public void setInstallAmt(String installAmt) {
        this.installAmt = installAmt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getPaytmentDt() {
        return paytmentDt;
    }

    public void setPaytmentDt(Date paytmentDt) {
        this.paytmentDt = paytmentDt;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
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

    public void setStatusList(List<SelectItem> statusList) {
        this.statusList = statusList;
    }

    public EMIMasterGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(EMIMasterGrid currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public List<EMIMasterGrid> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<EMIMasterGrid> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public boolean isFlag1() {
        return flag1;
    }

    public void setFlag1(boolean flag1) {
        this.flag1 = flag1;
    }

    public String getAcCloseFlag() {
        return acCloseFlag;
    }

    public void setAcCloseFlag(String acCloseFlag) {
        this.acCloseFlag = acCloseFlag;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }
    
    public LoanEmiMaster() {
        try {
            emiMaster = (BankAndLoanMasterFacadeRemote) ServiceLocator.getInstance().lookup("BankAndLoanMasterFacade");
            fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(FtsPostingMgmtFacadeJndiName);
            this.setAcNoLen(getAcNoLength());
            String code = getHttpRequest().getParameter("code");
            if (code == null || code.equalsIgnoreCase("")) {
                this.setAcCloseFlag("");
            } else {
                this.setAcCloseFlag(code);
                this.setOldAcctNo(code);
                getCustomerEMIDetail();
            }
            this.setErrorMessage("");
            this.setMessage("");
            statusList = new ArrayList<SelectItem>();
            statusList.add(new SelectItem("--Select--"));
            statusList.add(new SelectItem("PAID"));
            statusList.add(new SelectItem("UNPAID"));
            this.setFlag1(true);
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void getCustomerEMIDetail() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setSno("");
            this.setDueDt(null);
            this.setInstallAmt("");
            this.setExcessAmt("");
            this.setCustName("");
            this.setPaytmentDt(null);
            this.setStatus("--Select--");
            gridDetail = new ArrayList<EMIMasterGrid>();
            this.setFlag1(true);
//            if (this.acctNo == null || this.acctNo.equalsIgnoreCase("") || this.acctNo.length() == 0) {
//                this.setErrorMessage("PLEASE ENTER ACCOUNT NO.");
//                return;
//            }
//            if (this.acctNo.length() != 12) {
//                this.setErrorMessage("PLEASE ENTER 12 DIGIT ACCOUNT NO.");
//                return;
//            }
            
            if (this.oldAcctNo == null || this.oldAcctNo.equalsIgnoreCase("") || this.oldAcctNo.length() == 0) {
                this.setMessage("Please Enter the Account No.");             
                return;
            }
            if (!oldAcctNo.matches("[0-9a-zA-z]*")) {                  
                    setMessage("Please Enter Valid  Account No.");
                    return ;
                }
            //if (oldAcctNo.length() != 12) {
            if (!this.oldAcctNo.equalsIgnoreCase("") && ((this.oldAcctNo.length() != 12) && (this.oldAcctNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                setMessage("Account number is not valid.");
                return;
            }
           acctNo = fts.getNewAccountNumber(oldAcctNo);
            setAcctNo(acctNo); 
            String result = emiMaster.customerDetailLoanEmiMaster(this.acctNo);
            if (result == null) {
                this.setErrorMessage("ACCOUNT NOT FOUND !!!");
                return;
            } else {
                if (result.contains("!")) {
                    this.setErrorMessage(result);
                    return;
                } else {
                    if (result.substring(0, 4).equalsIgnoreCase("true")) {
                        this.setCustName(result.substring(4));
                        gridLoad();
                    }
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void gridLoad() {
        try {
            gridDetail = new ArrayList<EMIMasterGrid>();
            List resultList = new ArrayList();
            resultList = emiMaster.gridDetailLoanEmiMaster(this.acctNo);
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    EMIMasterGrid detail = new EMIMasterGrid();
                    detail.setSno(ele.get(0).toString());
                    detail.setDueDt(ele.get(1).toString());
                    detail.setInstallAmt(ele.get(2).toString());
                    detail.setPrinAmt(ele.get(3).toString());
                    detail.setInterestAmt(ele.get(4).toString());
                    detail.setStatus(ele.get(5).toString());
                    if (ele.get(6).toString() == null) {
                        detail.setPayDt("");
                    } else if (ele.get(6).toString().equalsIgnoreCase("01/01/1900")) {
                        detail.setPayDt("");
                    } else {
                        detail.setPayDt(ele.get(6).toString());
                    }
                    detail.setExcessAmt(ele.get(7).toString());
                    gridDetail.add(detail);
                }
            } else {
                return;
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void fillValuesofGridInFields() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            this.setFlag1(false);
            this.setSno("");
            this.setDueDt(null);
            this.setInstallAmt("");
            this.setExcessAmt("");
            this.setCustName("");
            this.setPaytmentDt(null);
            this.setStatus("--Select--");
            this.setSno(currentItem.getSno());
            this.setDueDt(sdf.parse(currentItem.getDueDt()));
            this.setInstallAmt(currentItem.getInstallAmt());
            this.setExcessAmt(currentItem.getExcessAmt());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void resetForm() {
        this.setErrorMessage("");
        this.setMessage("");
        this.setSno("");
        this.setAcctNo("");
        this.setOldAcctNo("");
        this.setDueDt(null);
        this.setInstallAmt("");
        this.setExcessAmt("");
        this.setCustName("");
        this.setPaytmentDt(null);
        this.setStatus("--Select--");
        gridDetail = new ArrayList<EMIMasterGrid>();
    }

    public String exitForm() {
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
            setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }

    public void saveBtnAction() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            if (this.acctNo == null || this.acctNo.equalsIgnoreCase("") || this.acctNo.length() == 0) {
                this.setErrorMessage("ACCOUNT NO. IS BLANK !!!");
                return;
            }
            if (this.acctNo.length() != 12) {
                this.setErrorMessage("PLEASE ENTER 12 DIGIT ACCOUNT NO.");
                return;
            }
            if (this.sno == null || this.sno.equalsIgnoreCase("") || this.sno.length() == 0) {
                this.setErrorMessage("S.NO. IS BLANK !!!");
                return;
            }
            if (this.status.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("PLEASE SELECT STATUS !!!");
                return;
            }
            String payDtTemp = "";
            if (this.status.equalsIgnoreCase("PAID")) {
                if (this.paytmentDt == null) {
                    this.setErrorMessage("PLEASE ENTER PAY DATE !!!");
                    return;
                } else {
                    payDtTemp = ymd.format(this.paytmentDt);
                }
            } else if (this.status.equalsIgnoreCase("UNPAID")) {
                if (this.paytmentDt != null) {
                    this.setPaytmentDt(null);
                    payDtTemp = "";
                }
            }
            if (this.excessAmt == null || this.excessAmt.equalsIgnoreCase("") || this.excessAmt.length() == 0) {
                this.setExcessAmt("0.0");
            } else {
                Matcher limitCheck = p.matcher(this.excessAmt);
                if (!limitCheck.matches()) {
                    this.setErrorMessage("PLEASE ENTER VALID EXCESS AMOUNT.");
                    return;
                }
                if (this.excessAmt.contains(".")) {
                    if (this.excessAmt.indexOf(".") != this.excessAmt.lastIndexOf(".")) {
                        this.setErrorMessage("INVALID EXCESS AMOUNT.PLEASE FILL THE EXCESS AMOUNT CORRECTLY.");
                        return;
                    } else if (this.excessAmt.substring(excessAmt.indexOf(".") + 1).length() > 2) {
                        this.setErrorMessage("PLEASE ENTER THE EXCESS AMOUNT UPTO TWO DECIMAL PLACES.");
                        return;
                    }
                }
                if (Float.parseFloat(this.excessAmt) < 0) {
                    this.setErrorMessage("EXCESS AMOUNT CANNOT BE NEGATIVE !!!");
                    return;
                }
            }
            String result = emiMaster.saveDetailLoanEmiMaster(this.acctNo, this.sno, this.status, payDtTemp, this.excessAmt,this.getUserName());
            if (result == null) {
                this.setErrorMessage("Record Could Be Saved !!!");
                return;
            } else {
                if (result.equalsIgnoreCase("true")) {
                    this.setMessage("Record Updated Successfully");
                    gridLoad();
                    this.setSno("");
                    this.setDueDt(null);
                    this.setInstallAmt("");
                    this.setExcessAmt("");
                    this.setCustName("");
                    this.setPaytmentDt(null);
                    this.setStatus("--Select--");
                } else {
                    this.setErrorMessage(result);
                    return;
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void updatePrevAction() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            if (this.acctNo == null || this.acctNo.equalsIgnoreCase("") || this.acctNo.length() == 0) {
                this.setErrorMessage("ACCOUNT NO. IS BLANK !!!");
                return;
            }
            if (this.acctNo.length() != 12) {
                this.setErrorMessage("PLEASE ENTER 12 DIGIT ACCOUNT NO.");
                return;
            }
            if (this.sno == null || this.sno.equalsIgnoreCase("") || this.sno.length() == 0) {
                this.setErrorMessage("S.NO. IS BLANK !!!");
                return;
            }
            if (this.status.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("PLEASE SELECT STATUS !!!");
                return;
            }
            String payDtTemp = "";
            if (this.status.equalsIgnoreCase("PAID")) {
                if (this.paytmentDt == null) {
                    this.setErrorMessage("PLEASE ENTER PAY DATE !!!");
                    return;
                } else {
                    payDtTemp = ymd.format(this.paytmentDt);
                }
            } else if (this.status.equalsIgnoreCase("UNPAID")) {
                if (this.paytmentDt != null) {
                    this.setPaytmentDt(null);
                    payDtTemp = "";
                }
            }
            if (this.excessAmt == null || this.excessAmt.equalsIgnoreCase("") || this.excessAmt.length() == 0) {
                this.setExcessAmt("0.0");
            } else {
                Matcher limitCheck = p.matcher(this.excessAmt);
                if (!limitCheck.matches()) {
                    this.setErrorMessage("PLEASE ENTER VALID EXCESS AMOUNT.");
                    return;
                }
                if (this.excessAmt.contains(".")) {
                    if (this.excessAmt.indexOf(".") != this.excessAmt.lastIndexOf(".")) {
                        this.setErrorMessage("INVALID EXCESS AMOUNT.PLEASE FILL THE EXCESS AMOUNT CORRECTLY.");
                        return;
                    } else if (this.excessAmt.substring(excessAmt.indexOf(".") + 1).length() > 2) {
                        this.setErrorMessage("PLEASE ENTER THE EXCESS AMOUNT UPTO TWO DECIMAL PLACES.");
                        return;
                    }
                }
                if (Float.parseFloat(this.excessAmt) < 0) {
                    this.setErrorMessage("EXCESS AMOUNT CANNOT BE NEGATIVE !!!");
                    return;
                }
            }
            String result = emiMaster.updatePreviousEMI(this.acctNo, this.sno, this.status, payDtTemp, this.excessAmt,this.getUserName());
            if (result == null) {
                this.setErrorMessage("Record Could Be Saved !!!");
                return;
            } else {
                if (result.equalsIgnoreCase("true")) {
                    this.setMessage("Record Updated Successfully");
                    gridLoad();
                    this.setSno("");
                    this.setDueDt(null);
                    this.setInstallAmt("");
                    this.setExcessAmt("");
                    this.setCustName("");
                    this.setPaytmentDt(null);
                    this.setStatus("--Select--");
                } else {
                    this.setErrorMessage(result);
                    return;
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
}
