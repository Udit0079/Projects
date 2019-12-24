/*
 * CREATED BY      :  ROHIT KRISHNA GUPTA
 */
package com.cbs.web.controller.bill;

import com.cbs.web.pojo.bill.BillCommissionRegGrid;
import com.cbs.facade.bill.BillCommissionFacadeRemote;
import com.cbs.facade.txn.TransactionManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

/**
 *
 * @author Admin
 */
public class BillCommissionRegister extends BaseBean {

    private TransactionManagementFacadeRemote txnRemote = null;
    private final String jndiHomeName = "TransactionManagementFacade";
    BillCommissionFacadeRemote commReg;
    private String errorMessage;
    private String message;
    private Date wefDate;
    private List<SelectItem> billTypeList;
    private String billType;
    int currentRow;
    private String amtFrom;
    private String amtTo;
    private String comChg;
    private String minChg;
    private String cancelChg;
    private String payBy;
    private String placeType;
    private String surCharges;
    private String labelCancelChg;
    private String pt;
    private boolean eFlag;
    private BillCommissionRegGrid currentItem = new BillCommissionRegGrid();
    private List<BillCommissionRegGrid> gridDetail;
    private List<SelectItem> palceTypeList;
    private List<SelectItem> payByList;
    private List<SelectItem> ptList;
    private boolean surChargeDisabled;
    private boolean cancelChargeDisabled;
    private boolean placeTypeDisabled;

    public boolean isCancelChargeDisabled() {
        return cancelChargeDisabled;
    }

    public void setCancelChargeDisabled(boolean cancelChargeDisabled) {
        this.cancelChargeDisabled = cancelChargeDisabled;
    }

    public boolean isPlaceTypeDisabled() {
        return placeTypeDisabled;
    }

    public void setPlaceTypeDisabled(boolean placeTypeDisabled) {
        this.placeTypeDisabled = placeTypeDisabled;
    }

    public boolean isSurChargeDisabled() {
        return surChargeDisabled;
    }

    public void setSurChargeDisabled(boolean surChargeDisabled) {
        this.surChargeDisabled = surChargeDisabled;
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

    public List<BillCommissionRegGrid> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<BillCommissionRegGrid> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public List<SelectItem> getBillTypeList() {
        return billTypeList;
    }

    public void setBillTypeList(List<SelectItem> billTypeList) {
        this.billTypeList = billTypeList;
    }

    public Date getWefDate() {
        return wefDate;
    }

    public void setWefDate(Date wefDate) {
        this.wefDate = wefDate;
    }

    public BillCommissionRegGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(BillCommissionRegGrid currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public String getAmtFrom() {
        return amtFrom;
    }

    public void setAmtFrom(String amtFrom) {
        this.amtFrom = amtFrom;
    }

    public String getAmtTo() {
        return amtTo;
    }

    public void setAmtTo(String amtTo) {
        this.amtTo = amtTo;
    }

    public String getCancelChg() {
        return cancelChg;
    }

    public void setCancelChg(String cancelChg) {
        this.cancelChg = cancelChg;
    }

    public String getComChg() {
        return comChg;
    }

    public void setComChg(String comChg) {
        this.comChg = comChg;
    }

    public String getMinChg() {
        return minChg;
    }

    public void setMinChg(String minChg) {
        this.minChg = minChg;
    }

    public String getPayBy() {
        return payBy;
    }

    public void setPayBy(String payBy) {
        this.payBy = payBy;
    }

    public String getPlaceType() {
        return placeType;
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    public String getSurCharges() {
        return surCharges;
    }

    public void setSurCharges(String surCharges) {
        this.surCharges = surCharges;
    }

    public List<SelectItem> getPalceTypeList() {
        return palceTypeList;
    }

    public void setPalceTypeList(List<SelectItem> palceTypeList) {
        this.palceTypeList = palceTypeList;
    }

    public List<SelectItem> getPayByList() {
        return payByList;
    }

    public void setPayByList(List<SelectItem> payByList) {
        this.payByList = payByList;
    }

    public String getLabelCancelChg() {
        return labelCancelChg;
    }

    public void setLabelCancelChg(String labelCancelChg) {
        this.labelCancelChg = labelCancelChg;
    }

    public String getPt() {
        return pt;
    }

    public void setPt(String pt) {
        this.pt = pt;
    }

    public List<SelectItem> getPtList() {
        return ptList;
    }

    public void setPtList(List<SelectItem> ptList) {
        this.ptList = ptList;
    }

    public boolean iseFlag() {
        return eFlag;
    }

    public void seteFlag(boolean eFlag) {
        this.eFlag = eFlag;
    }

    /**
     * Creates a new instance of BillCommissionRegister
     */
    public BillCommissionRegister() {
        try {
            commReg = (BillCommissionFacadeRemote) ServiceLocator.getInstance().lookup("BillCommissionFacade");
            txnRemote = (TransactionManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            gridLoad();
            billType();
            slabDate();
            palceTypeList = new ArrayList<SelectItem>();
            palceTypeList.add(new SelectItem("OutSide"));
            palceTypeList.add(new SelectItem("Local"));
            payByList = new ArrayList<SelectItem>();
            payByList.add(new SelectItem("--Select--"));
            payByList.add(new SelectItem("Cheque"));
            payByList.add(new SelectItem("Cash"));
            ptList = new ArrayList<SelectItem>();
            ptList.add(new SelectItem("--Select--"));
            ptList.add(new SelectItem("Yes"));
            ptList.add(new SelectItem("No"));
            this.setLabelCancelChg("Cancellation Charge :");
            this.setErrorMessage("");
            this.setMessage("");
            this.seteFlag(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gridLoad() {
        gridDetail = new ArrayList<BillCommissionRegGrid>();
        try {
            List resultList = new ArrayList();
            resultList = commReg.loadGridBillCommission();
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    BillCommissionRegGrid detail = new BillCommissionRegGrid();
                    detail.setCollectType(ele.get(0).toString());
                    detail.setAmtFrom(Double.parseDouble(ele.get(1).toString()));
                    detail.setAmtTo(Double.parseDouble(ele.get(2).toString()));
                    if (ele.get(11).toString().equalsIgnoreCase("Y")) {
                        detail.setCommCharge(ele.get(3).toString() + "P.T.");
                    } else {
                        detail.setCommCharge(ele.get(3).toString());
                    }
                    if (ele.get(4).toString().equalsIgnoreCase("0.0")) {
                        detail.setPostage("");
                    } else {
                        detail.setPostage(ele.get(4).toString());
                    }
                    detail.setPlaceType(ele.get(5).toString());
                    detail.setMinCharge(ele.get(6).toString());
                    if (ele.get(7).toString().equalsIgnoreCase("0.0")) {
                        detail.setSurCharge("");
                    } else {
                        detail.setSurCharge(ele.get(7).toString());
                    }
                    if (ele.get(8).toString().equalsIgnoreCase("1")) {
                        detail.setPayBy("Cheque");
                    } else {
                        detail.setPayBy("Cash");
                    }
                    detail.setMaxCharge(ele.get(9) == null ? "" : ele.get(9).toString());
                    detail.setSlabDate(ele.get(10).toString());
                    gridDetail.add(detail);
                }
            } else {
                return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void billType() {
        try {
            List billTy = commReg.billTypeLoad();
            billTypeList = new ArrayList<SelectItem>();
            billTypeList.add(new SelectItem("--Select--"));
            billTypeList.add(new SelectItem("ODD"));
            billTypeList.add(new SelectItem("OBC"));
            for (int i = 0; i < billTy.size(); i++) {
                Vector ele = (Vector) billTy.get(i);
                billTypeList.add(new SelectItem(ele.get(0).toString(), ele.get(0).toString()));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    Date wefDt;

    public void slabDate() {
        try {
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            Date date = new Date();
            wefDt = date;
            this.setWefDate(wefDt);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void fetchCurrentRow(ActionEvent event) {
        String dt = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("slabDate"));
        currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (BillCommissionRegGrid item : gridDetail) {
            if (item.getSlabDate().equalsIgnoreCase(dt)) {
                currentItem = item;
            }
        }
    }

    public void fillValuesofGridInFields() {
        this.setErrorMessage("");
        this.setMessage("");
        eFlag = true;
        try {
            this.setBillType(currentItem.getCollectType());
            this.setErrorMessage("");
            this.setMessage("");
            NumberFormat formatter = new DecimalFormat("#0.00");
            this.setAmtFrom(String.valueOf(formatter.format(currentItem.getAmtFrom())));
            this.setAmtTo(String.valueOf(formatter.format(currentItem.getAmtTo())));
            int n = currentItem.getCommCharge().indexOf("P", 0);
            if (n > 0) {
                String tmp = currentItem.getCommCharge().substring(0, n);
                this.setComChg(tmp);
                this.setPt("Yes");
            } else {
                this.setComChg(currentItem.getCommCharge());
                this.setPt("No");
            }

            if (billType.equalsIgnoreCase("OBC") || billType.equalsIgnoreCase("ODD")) {
                this.setCancelChg(currentItem.getPostage());
            } else {
                List billNat = txnRemote.strBillNature(this.getBillType());
                Vector vBillNat = (Vector) billNat.get(0);
                String bNature = vBillNat.get(0).toString();
                if (bNature.equalsIgnoreCase("PO") || bNature.equalsIgnoreCase("DD")) {
                    this.setCancelChg(currentItem.getMaxCharge());
                }
            }
            this.setMinChg(currentItem.getMinCharge());
            this.setCancelChg(currentItem.getMaxCharge());
            this.setPlaceType(currentItem.getPlaceType());
            this.setSurCharges(currentItem.getSurCharge());
            this.setPayBy(currentItem.getPayBy());

            billTypeSelection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void delete() {
        this.setErrorMessage("");
        this.setMessage("");
        String tmp;
        try {
            int n = currentItem.getCommCharge().indexOf("P", 0);
            if (n > 0) {
                tmp = currentItem.getCommCharge().substring(0, n);
            } else {
                tmp = currentItem.getCommCharge();
            }
            String result = commReg.deleteRecordBillCommission(currentItem.getCollectType(), currentItem.getAmtFrom(), Float.parseFloat(tmp));
            if (result.equals("true")) {
                this.setMessage("Record Deleted Successfully.");
            } else {
                this.setErrorMessage("Record Not Deleted.");
                return;
            }
            gridLoad();
            resetForm();
            eFlag = false;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void billTypeSelection() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            if (this.billType.equalsIgnoreCase("OBC")) {
                this.setLabelCancelChg("Postage Charge :");
                this.setSurChargeDisabled(true);
                this.setSurCharges("0");
                this.setPlaceTypeDisabled(false);
                this.setCancelChargeDisabled(false);
            } else if (this.billType.equalsIgnoreCase("ODD")) {
                this.setLabelCancelChg("Postage Charge :");
                this.setSurChargeDisabled(true);
                this.setSurCharges("0");
                this.setPlaceTypeDisabled(false);
                this.setCancelChargeDisabled(true);
                this.setCancelChg("0");
            } else {
                List billNat = txnRemote.strBillNature(this.getBillType());
                if (!billNat.isEmpty()) {
                    Vector vBillNat = (Vector) billNat.get(0);
                    String bNature = vBillNat.get(0).toString();
                    if (bNature.equalsIgnoreCase("DD")) {
                        this.setLabelCancelChg("Cancellation Charge :");
                        this.setSurChargeDisabled(true);
                        this.setSurCharges("0");
//                        this.setPlaceTypeDisabled(true);
                        this.setCancelChargeDisabled(false);
                    } else if (bNature.equalsIgnoreCase("PO")) {
                        this.setLabelCancelChg("Cancellation Charge :");
                        this.setSurChargeDisabled(true);
                        this.setSurCharges("0");
//                        this.setPlaceTypeDisabled(true);
                        this.setCancelChargeDisabled(false);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveDetail() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            if (billType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Bill Type");
                return;
            }
            if (this.wefDate == null) {
                this.setErrorMessage("Please Enter With Effect From Date.");
                return;
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (this.amtFrom == null || this.amtFrom.equalsIgnoreCase("") || this.amtFrom.length() == 0) {
                this.setErrorMessage("Please Enter Amount From.");
                return;
            }
            Matcher amFrom = p.matcher(amtFrom);
            if (!amFrom.matches()) {
                this.setErrorMessage("Please Enter Valid Amount From.");
                return;
            }
            if (this.amtFrom.contains(".")) {
                if (this.amtFrom.indexOf(".") != this.amtFrom.lastIndexOf(".")) {
                    this.setErrorMessage("Please Enter Valid Amount From.");
                    return;
                } else if (this.amtFrom.substring(amtFrom.indexOf(".") + 1).length() > 2) {
                    this.setErrorMessage("Please Fill The Amount From Upto Two Decimal Places.");
                    return;
                }
            }
            if (Double.parseDouble(amtFrom) < 0) {
                this.setErrorMessage("Amount From Cannot Be Less Than Zero.");
                return;
            }
            if (this.amtTo == null || this.amtTo.equalsIgnoreCase("") || this.amtTo.length() == 0) {
                this.setErrorMessage("Please Enter Amount To.");
                return;
            }
            Matcher amTo = p.matcher(amtTo);
            if (!amTo.matches()) {
                this.setErrorMessage("Please Enter Valid Amount To.");
                return;
            }
            if (this.amtTo.contains(".")) {
                if (this.amtTo.indexOf(".") != this.amtTo.lastIndexOf(".")) {
                    this.setErrorMessage("Please Enter Valid Amount To.");
                    return;
                } else if (this.amtTo.substring(amtTo.indexOf(".") + 1).length() > 2) {
                    this.setErrorMessage("Please Fill The Amount To Upto Two Decimal Places.");
                    return;
                }
            }
            if (Double.parseDouble(amtTo) < 0) {
                this.setErrorMessage("Amount To Cannot Be Less Than Zero.");
                return;
            }
            if (Double.parseDouble(amtTo) < Double.parseDouble(amtFrom)) {
                this.setErrorMessage("Amount From Could Not Less Than Amount To.");
                return;
            }
            if (this.comChg == null || this.comChg.equalsIgnoreCase("") || this.comChg.length() == 0) {
                this.setErrorMessage("Please Enter Commission Charge.");
                return;
            }
            Matcher cmChg = p.matcher(comChg);
            if (!cmChg.matches()) {
                this.setErrorMessage("Please Enter Valid Commission Charge.");
                return;
            }
            if (this.comChg.contains(".")) {
                if (this.comChg.indexOf(".") != this.comChg.lastIndexOf(".")) {
                    this.setErrorMessage("Please Enter Valid Commission Charge.");
                    return;
                } else if (this.comChg.substring(comChg.indexOf(".") + 1).length() > 2) {
                    this.setErrorMessage("Please Fill The Commission Charges Upto Two Decimal Places.");
                    return;
                }
            }
            if (Float.parseFloat(comChg) < 0) {
                this.setErrorMessage("Commission Charges Cannot Be Less Than Zero.");
                return;
            }
            if (this.pt.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select P.T.");
                return;
            }
            String comFlag = null;
            if (this.pt.equalsIgnoreCase("Yes")) {
                comFlag = "Y";
            } else if (this.pt.equalsIgnoreCase("No")) {
                comFlag = "N";
            }
            if (this.minChg.equalsIgnoreCase("") || this.minChg.length() == 0) {
                this.setErrorMessage("Please Enter Minimum Charge.");
                return;
            }
            Matcher minCh = p.matcher(minChg);
            if (!minCh.matches()) {
                this.setErrorMessage("Please Enter Valid Minimum Charge.");
                return;
            }
            if (this.minChg.contains(".")) {
                if (this.minChg.indexOf(".") != this.minChg.lastIndexOf(".")) {
                    this.setErrorMessage("Please Enter Valid Minimum Charge.");
                    return;
                } else if (this.minChg.substring(minChg.indexOf(".") + 1).length() > 2) {
                    this.setErrorMessage("Please Fill The Minimum Charges Upto Two Decimal Places.");
                    return;
                }
            }
            if (Float.parseFloat(minChg) < 0) {
                this.setErrorMessage("Minimum Charges Cannot Be Less Than Zero.");
                return;
            }
            float postageChg = (float) 0.0;
            float cancellationChg = (float) 0.0;
            if (this.billType.equalsIgnoreCase("OBC") || this.billType.equalsIgnoreCase("ODD")) {
                if (this.cancelChg.equalsIgnoreCase("") || this.cancelChg.length() == 0) {
                    this.setErrorMessage("Please Enter Postage Charge.");
                    return;
                }
                Matcher canChg = p.matcher(cancelChg);
                if (!canChg.matches()) {
                    this.setErrorMessage("Please Enter Valid Postage Charge.");
                    return;
                }
                if (Float.parseFloat(cancelChg) < 0) {
                    this.setErrorMessage("Cancellation Charges Cannot Be Less Than Zero.");
                    return;
                }
                if (this.cancelChg.equalsIgnoreCase("") || this.cancelChg.length() == 0) {
                    postageChg = (float) 0.0;
                } else {
                    postageChg = Float.parseFloat(this.cancelChg);
                }
            } else {
                if (this.cancelChg.equalsIgnoreCase("") || this.cancelChg.length() == 0) {
                    this.setErrorMessage("Please Enter Cancellation Charge.");
                    return;
                }
                Matcher canChg = p.matcher(cancelChg);
                if (!canChg.matches()) {
                    this.setErrorMessage("Please Enter Valid Cancellation Charge.");
                    return;
                }
                if (this.cancelChg.contains(".")) {
                    if (this.cancelChg.indexOf(".") != this.cancelChg.lastIndexOf(".")) {
                        this.setErrorMessage("Please Enter Valid Cancellation Charge.");
                        return;
                    } else if (this.cancelChg.substring(cancelChg.indexOf(".") + 1).length() > 2) {
                        this.setErrorMessage("Please Fill The Cancellation Charge Upto Two Decimal Places.");
                        return;
                    }
                }
                if (Float.parseFloat(cancelChg) < 0) {
                    this.setErrorMessage("Cancellation Charges Cannot Be Less Than Zero.");
                    return;
                }
                if (this.cancelChg.equalsIgnoreCase("") || this.cancelChg.length() == 0) {
                    cancellationChg = (float) 0.0;
                } else {
                    cancellationChg = Float.parseFloat(this.cancelChg);
                }
            }
            if (this.surCharges.equalsIgnoreCase("") || this.surCharges.length() == 0) {
                this.setErrorMessage("Please Enter SurCharge.");
                return;
            }
            Matcher surCh = p.matcher(surCharges);
            if (!surCh.matches()) {
                this.setErrorMessage("Please Enter Valid SurCharge.");
                return;
            }
            if (this.surCharges.contains(".")) {
                if (this.surCharges.indexOf(".") != this.surCharges.lastIndexOf(".")) {
                    this.setErrorMessage("Please Enter Valid SurCharge.");
                    return;
                } else if (this.surCharges.substring(surCharges.indexOf(".") + 1).length() > 2) {
                    this.setErrorMessage("Please Fill The SurCharge Upto Two Decimal Places.");
                    return;
                }
            }
            if (Float.parseFloat(surCharges) < 0) {
                this.setErrorMessage("Surcharges Cannot Be Less Than Zero.");
                return;
            }
            int payByVal = 0;
            if (this.payBy.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Pay By.");
                return;
            }
            if (this.payBy.equalsIgnoreCase("Cheque")) {
                payByVal = 1;
            } else if (this.payBy.equalsIgnoreCase("Cash")) {
                payByVal = 0;
            }
            if (this.placeType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please Select Place Type.");
                return;
            }
            Float temp = 0.0f;
            if (currentItem.getCommCharge() != null) {
                if (currentItem.getCommCharge().contains("P")) {
                    temp = Float.parseFloat(currentItem.getCommCharge().substring(0, currentItem.getCommCharge().indexOf("P", 0)));
                } else {
                    temp = Float.parseFloat(currentItem.getCommCharge());
                }
            } else {
                temp = 0.0f;
            }
            String temp1 = "";
            if (currentItem.getCollectType() == null) {
                temp1 = "";
            } else {
                temp1 = currentItem.getCollectType();
            }
            double temp2 = 0.0d;
            if (currentItem.getAmtFrom() == null) {
                temp2 = 0.0f;
            } else {
                temp2 = currentItem.getAmtFrom();
            }
            double temp3 = 0.0d;
            if (currentItem.getAmtTo() == null) {
                temp3 = 0.0f;
            } else {
                temp3 = currentItem.getAmtTo();
            }
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

            String result = commReg.saveDetailBillCommission(this.billType, Double.parseDouble(this.amtFrom), Double.parseDouble(this.amtTo),
                    Float.parseFloat(this.minChg), Float.parseFloat(this.comChg), postageChg, ymd.format(this.wefDate), this.placeType,
                    Float.parseFloat(this.surCharges), payByVal, comFlag, this.getUserName(), cancellationChg, this.eFlag, temp1, temp2, temp3, temp);
            if (result.equals("true")) {
                if (this.eFlag == false) {
                    this.setMessage("Record Saved Successfully.");
                } else {
                    this.setMessage("Record Updated Successfully.");
                }
            } else {
                this.setErrorMessage(result);
                return;
            }
            eFlag = false;
            gridLoad();
            this.setAmtFrom("");
            this.setAmtTo("");
            this.setComChg("");
            this.setMinChg("");
            this.setCancelChg("");
            this.setBillType("--Select--");
            this.setPayBy("--Select--");
            this.setPlaceType("--Select--");
            this.setSurCharges("");
            this.setPt("--Select--");
            setWefDate(wefDt);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void resetForm() {
        try {
            this.setAmtFrom("");
            this.setAmtTo("");
            this.setComChg("");
            this.setMinChg("");
            this.setCancelChg("");
            this.setBillType("--Select--");
            this.setPayBy("--Select--");
            this.setPlaceType("--Select--");
            this.setSurCharges("");
            this.setPt("--Select--");
            setWefDate(wefDt);
            this.seteFlag(false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void resetForm1() {
        try {
            this.setErrorMessage("");
            this.setMessage("");
            gridLoad();
            this.setAmtFrom("");
            this.setAmtTo("");
            this.setComChg("");
            this.setMinChg("");
            this.setCancelChg("");
            this.setBillType("--Select--");
            this.setPayBy("--Select--");
            this.setPlaceType("--Select--");
            this.setSurCharges("");
            this.setPt("--Select--");
            setWefDate(wefDt);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String exitForm() {
        try {
            resetForm1();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "case1";
    }
}
