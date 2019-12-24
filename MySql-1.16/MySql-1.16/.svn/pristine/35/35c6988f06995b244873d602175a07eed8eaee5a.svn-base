package com.cbs.web.controller.master;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.web.pojo.master.EntryGridLoad;
import com.cbs.facade.master.OtherMasterFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

public final class EntryFrTxMast extends BaseBean {

    List<SelectItem> roundListOff;
    List<SelectItem> applyOn;
    String applyOnItem;
    String listitem;
    List<SelectItem> taxNatureList;
    String taxNature;
    private OtherMasterFacadeRemote otherMasterRemote;
    private EntryGridLoad rd;
    private List<EntryGridLoad> gridData;
    String LbTax;
    String txtGLHd;
    String txtROT;
    String txtMinTax;
    String txtMaxTax;
    String message5;
    boolean saveValue;
    private String taxType;
    private int currentRow;
    EntryGridLoad authorized;
    int k = 0;
    String message;
    private Date todayDt = new Date();

    public Date getTodayDt() {
        return todayDt;
    }

    public void setTodayDt(Date todayDt) {
        this.todayDt = todayDt;
    }

    public String getTxtMaxTax() {
        return txtMaxTax;
    }

    public void setTxtMaxTax(String txtMaxTax) {
        this.txtMaxTax = txtMaxTax;
    }

    public String getTxtMinTax() {
        return txtMinTax;
    }

    public void setTxtMinTax(String txtMinTax) {
        this.txtMinTax = txtMinTax;
    }

    public String getTxtROT() {
        return txtROT;
    }

    public void setTxtROT(String txtROT) {
        this.txtROT = txtROT;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public EntryGridLoad getAuthorized() {
        return authorized;
    }

    public void setAuthorized(EntryGridLoad authorized) {
        this.authorized = authorized;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public List<SelectItem> getApplyOn() {
        return applyOn;
    }

    public void setApplyOn(List<SelectItem> applyOn) {
        this.applyOn = applyOn;
    }

    public String getApplyOnItem() {
        return applyOnItem;
    }

    public void setApplyOnItem(String applyOnItem) {
        this.applyOnItem = applyOnItem;
    }

    public boolean isSaveValue() {
        return saveValue;
    }

    public void setSaveValue(boolean saveValue) {
        this.saveValue = saveValue;
    }

    public String getMessage5() {
        return message5;
    }

    public void setMessage5(String message5) {
        this.message5 = message5;
    }

    public String getLbTax() {
        return LbTax;
    }

    public void setLbTax(String LbTax) {
        this.LbTax = LbTax;
    }

    public String getTxtGLHd() {
        return txtGLHd;
    }

    public void setTxtGLHd(String txtGLHd) {
        this.txtGLHd = txtGLHd;
    }

    public List<EntryGridLoad> getGridData() {
        return gridData;
    }

    public void setGridData(List<EntryGridLoad> gridData) {
        this.gridData = gridData;
    }

    public EntryGridLoad getRd() {
        return rd;
    }

    public void setRd(EntryGridLoad rd) {
        this.rd = rd;
    }

    public String getListitem() {
        return listitem;
    }

    public void setListitem(String listitem) {
        this.listitem = listitem;
    }

    public List<SelectItem> getRoundListOff() {
        return roundListOff;
    }

    public void setRoundListOff(List<SelectItem> roundListOff) {
        this.roundListOff = roundListOff;
    }

    public String getTaxNature() {
        return taxNature;
    }

    public void setTaxNature(String taxNature) {
        this.taxNature = taxNature;
    }

    public List<SelectItem> getTaxNatureList() {
        return taxNatureList;
    }

    public void setTaxNatureList(List<SelectItem> taxNatureList) {
        this.taxNatureList = taxNatureList;
    }

    public EntryFrTxMast() {
        try {
            otherMasterRemote = (OtherMasterFacadeRemote) ServiceLocator.getInstance().lookup("OtherMasterFacade");
            this.setMessage5("");
            applyOn = new ArrayList<SelectItem>();
            onLoad();
            addRecord();
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void onLoad() {
        setMessage5("");
        try {
            if (!applyOn.isEmpty()) {
                applyOn.clear();
            }
            saveValue = true;
            this.LbTax = "";
            this.txtGLHd = "";
            this.txtROT = "";
            this.txtMinTax = "";
            this.txtMaxTax = "";
            roundListOff = new ArrayList<SelectItem>();
            roundListOff.add(new SelectItem("0"));
            roundListOff.add(new SelectItem("1"));
            roundListOff.add(new SelectItem("2"));
            roundListOff.add(new SelectItem("3"));
            roundListOff.add(new SelectItem("4"));
            roundListOff.add(new SelectItem("5"));
            roundListOff.add(new SelectItem("6"));
            roundListOff.add(new SelectItem("7"));
            roundListOff.add(new SelectItem("8"));
            roundListOff.add(new SelectItem("9"));
            taxNatureList = new ArrayList<SelectItem>();
            taxNatureList.add(new SelectItem("VARIABLE"));
            taxNatureList.add(new SelectItem("FIXED"));
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void addRecord() {
        try {
            List result = new ArrayList();
            gridData = new ArrayList<EntryGridLoad>();
            result = otherMasterRemote.loadTable();
            for (int i = 0; i < result.size(); i++) {
                Vector ele = (Vector) result.get(i);
                rd = new EntryGridLoad();
                rd.setTaxName(ele.get(0).toString());
                rd.setDateApplic(ele.get(1).toString());
                rd.setGlHead(ele.get(2).toString());
                rd.setRot(Float.parseFloat(ele.get(3).toString()));
                String rotApplyOn = ele.get(4).toString();
                if (rotApplyOn == null || rotApplyOn.equals("")) {
                    rd.setRotApplOn("");
                } else if (rotApplyOn.equalsIgnoreCase("C")) {
                    rd.setRotApplOn("COMMISSION");
                }
                rd.setMinTaxe(Float.parseFloat(ele.get(5).toString()));
                rd.setMaxTaxe(Float.parseFloat(ele.get(6).toString()));
                rd.setApp(ele.get(7).toString());
                gridData.add(rd);
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void txtGlhdKeyDown() {
        try {
            this.setMessage5("");
            this.setMessage("");
            if (!applyOn.isEmpty()) {
                applyOn.clear();
            }
            this.txtROT = "";
            this.txtMinTax = "";
            this.txtMaxTax = "";
            roundListOff = new ArrayList<SelectItem>();
            roundListOff.add(new SelectItem("0"));
            roundListOff.add(new SelectItem("1"));
            roundListOff.add(new SelectItem("2"));
            roundListOff.add(new SelectItem("3"));
            roundListOff.add(new SelectItem("4"));
            roundListOff.add(new SelectItem("5"));
            roundListOff.add(new SelectItem("6"));
            roundListOff.add(new SelectItem("7"));
            roundListOff.add(new SelectItem("8"));
            roundListOff.add(new SelectItem("9"));
            taxNatureList = new ArrayList<SelectItem>();
            taxNatureList.add(new SelectItem("VARIABLE"));
            taxNatureList.add(new SelectItem("FIXED"));
            String glNo = this.getTxtGLHd();
            int size = glNo.length();
            if (size != 6) {
                this.setMessage5("Please enter the valid 6 digit acno");
                this.setTxtGLHd("");
            } else {
                String acNo = this.getTxtGLHd();
                List result = otherMasterRemote.taxNature(acNo);
                if (result == null) {
                    this.onLoad();
                    this.setMessage5("No Gl head exists for this number.");
                    this.setTxtGLHd("");
                } else {
                    saveValue = false;
                    this.setMessage5("");
                    Vector ele = (Vector) result.get(0);
                    this.setLbTax(ele.get(0).toString());
                    loadApply();
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void loadApply() {
        try {
            applyOn.add(new SelectItem("COMMISSION"));
            List result = otherMasterRemote.loadApply(this.getLbTax());
            if (!result.isEmpty()) {
                Vector ele = (Vector) result.get(0);
                applyOn.add(new SelectItem(ele.get(0).toString()));
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void fetchCurrentRow(ActionEvent event) {
        try {
            String ctrlNumber = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("ctrlNo"));
            currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
            for (EntryGridLoad item4 : this.gridData) {
                if (item4.getGlHead().equals(ctrlNumber)) {
                    authorized = item4;
                }
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void setRowValues() {
        this.setMessage("");
        this.setMessage5("");
        try {
            if (authorized.getApp().equals("N")) {
                this.setMessage5("Selected tax is already disabled");
            } else {
                String st1 = otherMasterRemote.update(authorized.getTaxName());
                if (st1.equals("transaction successful")) {
                    authorized.setApp("N");
                    this.setMessage5("Data has been updated successfully.");
                } else {
                    this.setMessage5("Sorry can not disable the selected tax.");
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void save() {
        this.setMessage("");
        this.setMessage5("");
        String nature = "";
        try {
            if (this.txtGLHd == null || this.txtGLHd.equals("") || this.getTxtGLHd().length() < 6) {
                this.setMessage5("Please check the gl head, the size of gl head must be of 6 digit.");
                return;
            }
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            if (todayDt.before(ymd.parse(ymd.format(new Date())))) {
                this.setMessage5("The selected date can not be less than current date.");
                return;
            }
            if (this.getTxtROT().equals("")) {
                this.setMessage5("Please enter the rate of tax.");
                return;
            }

            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher rateCheck = p.matcher(this.getTxtROT());
            if (!rateCheck.matches()) {
                this.setMessage5("Please enter valid numeric value for rate of tax.");
                return;
            }
            if (Double.parseDouble(this.getTxtROT()) < 0 || Double.parseDouble(this.getTxtROT()) >= 25) {
                this.setMessage5("Rate of interest must be between 0 and 25%");
                return;
            }
            if (this.getTxtMinTax().equals("")) {
                this.setMessage5("Please enter the minimum of tax.");
                return;
            }
            rateCheck = p.matcher(this.getTxtMinTax());
            if (!rateCheck.matches()) {
                this.setMessage5("Please enter valid numeric value for rate of tax.");
                this.setTxtMinTax("");
                return;
            }
            if (Double.parseDouble(this.getTxtMinTax()) < 1) {
                this.setMessage5("Minimum tax can not be less than 1.");
                return;
            }
            if (this.txtMaxTax.equals("")) {
                this.setMessage5("PLease enter the max tax.");
                return;
            }
            rateCheck = p.matcher(this.getTxtMaxTax());
            if (!rateCheck.matches()) {
                this.setMessage5("Please enter valid numeric value for max tax.");
                this.setTxtMaxTax("");
                return;
            }
            rateCheck = p.matcher(this.getTxtMaxTax());
            if (Double.parseDouble(getTxtMaxTax()) > 9999999.99) {
                this.setMessage5("Max tax is exceeding Limit..");
                return;
            }
            if (Double.parseDouble(this.getTxtMaxTax()) < Double.parseDouble(getTxtMinTax())) {
                this.setMessage5("Maximum tax amount should be greater than minimum tax.");
                return;
            }
            taxType = "T";
            String rotAppOn = "C";
            if (this.taxNature.equals("VARIABLE")) {
                nature = "V";
            } else {
                nature = "F";
            }
            List result = otherMasterRemote.save(this.getLbTax());
            if (result.isEmpty()) {
                List result1 = otherMasterRemote.save2();
                Vector v4 = (Vector) result1.get(0);
                if (!v4.get(0).toString().equals("0")) {
                    Vector v2 = (Vector) result1.get(0);
                    Integer s1 = Integer.parseInt(v2.get(0).toString());
                    s1 = s1 + 1;
                    taxType = "T" + s1.toString();
                }
            } else {
                Vector v1 = (Vector) result.get(0);
                taxType = v1.get(0).toString();
            }
            String cboAppOn = this.applyOnItem;
            List result2 = otherMasterRemote.save3(cboAppOn);
            if (!result2.isEmpty()) {
                Vector v3 = (Vector) result2.get(0);
                rotAppOn = v3.get(0).toString();
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String st1 = otherMasterRemote.update2(this.getLbTax(), taxType, sdf.format(todayDt), Double.parseDouble(this.getTxtROT()),
                    rotAppOn, CbsAcCodeConstant.GL_ACCNO.getAcctCode() + this.getTxtGLHd(), Double.parseDouble(this.getTxtMinTax()), Double.parseDouble(this.txtMaxTax),
                    this.getUserName(), "system", nature, this.listitem, "Y");
            this.setMessage(st1);
            this.onLoad();
            this.addRecord();
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void refresh() {
        try {
            this.setMessage5("");
            this.setMessage("");
            if (!applyOn.isEmpty()) {
                applyOn.clear();
            }
            this.setTxtGLHd("");
            this.setLbTax("");
            this.setTxtROT("");
            this.setTxtMinTax("");
            this.setTxtMaxTax("");
            saveValue = true;
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitForm() {
        refresh();
        return "case1";
    }
}
