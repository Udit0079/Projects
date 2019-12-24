package com.cbs.web.controller.master;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.master.BankAndLoanMasterFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.master.LoanInspectionTable;
import com.cbs.web.pojo.master.LoanInspectionRecordTable;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

public final class LoanInspectionChargesMaster extends BaseBean {

    private HttpServletRequest req;
    private BankAndLoanMasterFacadeRemote testEJB;
    private String userName=getUserName();
    private String todayDate=getTodayDate();
    private ArrayList<SelectItem> instCode;
    private String insmntCode;
    private String message;
    int slabcode;
    int slab = 0;
    String amtFrm;
    double amtFrom;
    float amtTo = 0;
    String chkInstCode;
    String flag = "F";
    private String from;
    private String to;
    Date asOnDate = new Date();
    private ArrayList<LoanInspectionTable> list;
    private ArrayList<LoanInspectionTable> slbcd;
    private ArrayList<LoanInspectionRecordTable> datalist;
    private int currentRow;
    List arraylist = new ArrayList();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    private boolean fromDisable;
    private boolean fromAmountDisable;
    private int checkLength;
    private boolean btnDisable;
    private List<SelectItem> instrumentCodeOption;
    private String chargesAmt;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    LoanInspectionTable currentItem = new LoanInspectionTable();

    public ArrayList<LoanInspectionRecordTable> getDatalist() {
        return datalist;
    }

    public void setDatalist(ArrayList<LoanInspectionRecordTable> datalist) {
        this.datalist = datalist;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getChargesAmt() {
        return chargesAmt;
    }

    public void setChargesAmt(String chargesAmt) {
        this.chargesAmt = chargesAmt;
    }

    public List<SelectItem> getInstrumentCodeOption() {
        return instrumentCodeOption;
    }

    public void setInstrumentCodeOption(List<SelectItem> instrumentCodeOption) {
        this.instrumentCodeOption = instrumentCodeOption;
    }

    public boolean isBtnDisable() {
        return btnDisable;
    }

    public void setBtnDisable(boolean btnDisable) {
        this.btnDisable = btnDisable;
    }

    public int getCheckLength() {
        return checkLength;
    }

    public void setCheckLength(int checkLength) {
        this.checkLength = checkLength;
    }

    public boolean isFromAmountDisable() {
        return fromAmountDisable;
    }

    public void setFromAmountDisable(boolean fromAmountDisable) {
        this.fromAmountDisable = fromAmountDisable;
    }

    public boolean isFromDisable() {
        return fromDisable;
    }

    public void setFromDisable(boolean fromDisable) {
        this.fromDisable = fromDisable;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public LoanInspectionTable getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(LoanInspectionTable currentItem) {
        this.currentItem = currentItem;
    }

    public ArrayList<LoanInspectionTable> getList() {
        return list;
    }

    public void setList(ArrayList<LoanInspectionTable> list) {
        this.list = list;
    }

    public ArrayList<LoanInspectionTable> getSlbcd() {
        return slbcd;
    }

    public void setSlbcd(ArrayList<LoanInspectionTable> slbcd) {
        this.slbcd = slbcd;
    }

    public String getInsmntCode() {
        return insmntCode;
    }

    public void setInsmntCode(String insmntCode) {
        this.insmntCode = insmntCode;
    }

    public Date getAsOnDate() {
        return asOnDate;
    }

    public void setAsOnDate(Date asOnDate) {
        this.asOnDate = asOnDate;
    }

    public String getAmtFrm() {
        return amtFrm;
    }

    public void setAmtFrm(String amtFrm) {
        this.amtFrm = amtFrm;
    }

    public int getSlabcode() {
        return slabcode;
    }

    public void setSlabcode(int slabcode) {
        this.slabcode = slabcode;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }

    public ArrayList<SelectItem> getInstCode() {
        return instCode;
    }

    public void setInstCode(ArrayList<SelectItem> instCode) {
        this.instCode = instCode;
    }

    public LoanInspectionChargesMaster() {
        try {
            testEJB = (BankAndLoanMasterFacadeRemote) ServiceLocator.getInstance().lookup("BankAndLoanMasterFacade");
            req = getHttpRequest();
            getInstrumentCode();
            fromAmountDisable = true;
            list = new ArrayList<LoanInspectionTable>();
            setTo("0");
            setChargesAmt("0");
            setBtnDisable(true);
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void getInstrumentCode() {
        try {
            List resultList = new ArrayList();
            resultList = testEJB.getTLDLCAAcTypeDropDown();
            if (!resultList.isEmpty()) {
                instrumentCodeOption = new ArrayList<SelectItem>();
                instrumentCodeOption.add(new SelectItem("--Select--"));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    for (Object ee : ele) {
                        instrumentCodeOption.add(new SelectItem(ee.toString()));
                    }
                }
            } else {
                instrumentCodeOption = new ArrayList<SelectItem>();
                instrumentCodeOption.add(new SelectItem("--Select--"));
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void setTable() {
        try {
            btnDisable = false;
            createObj();
            checkLength = list.size();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    private void createObj() {
        try {
            if (onblurChecking().equalsIgnoreCase("false")) {
                return;
            }
            if (currentItem.getAcno() != null) {
                if (!insmntCode.equalsIgnoreCase(currentItem.getAcno())) {
                    this.setMessage("Please save the record and choose another A/C type ");
                    return;
                }
            }
            LoanInspectionTable rtm = new LoanInspectionTable();
            if (Float.parseFloat(to) <= Float.parseFloat(from)) {
                this.setMessage("Amount To must be greater than From Amount ");
                rtm.setSlabFloor(Float.toString(amtTo));
                return;
            }
            rtm.setAcno(insmntCode);
            rtm.setRoi(chargesAmt);
            rtm.setSlabciel(from);
            rtm.setSlabFloor(to);
            rtm.setFromDate(dmy.format(asOnDate));
            rtm.setEnterby(userName);
            rtm.setTrantime(todayDate);
            list.add(rtm);
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void setIntoFrom() {
        try {
            fromAmountDisable = true;
            slbcd = new ArrayList<LoanInspectionTable>();
            this.setMessage("");
            if (insmntCode == null) {
                this.setMessage("Please select the A/c Type");
                return;
            }
            if (insmntCode.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please select the A/c Type");
                return;
            }
            float a;
            if (list.isEmpty()) {
                a = 0;
            } else {
                a = new Float(((LoanInspectionTable) list.get(list.size() - 1)).getSlabFloor());
            }
            setFrom(Float.toString(a + 1));
            setSlabcode(slab + 1);
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void fetchCurrentRow(ActionEvent event) {
        try {
            String intcode = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("Acount No."));
            currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
            for (LoanInspectionTable item : list) {
                if (item.getAcno().equals(intcode)) {
                    currentItem = item;
                    break;
                }
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void delete() {
        try {
            setFlag("D");
            list.remove(currentItem);
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void onClick() {
        try {
            if (insmntCode == null) {
                this.setMessage("Please select the A/C Type");
                return;
            }
            if (insmntCode.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please select the A/C Type");
                return;
            }
            datalist = new ArrayList<LoanInspectionRecordTable>();
            List gridLt = new ArrayList();
            gridLt = testEJB.amountSlabTableLoanInspectionCharges(insmntCode);
            if (gridLt.size() > 0) {
                for (int i = 0; i < gridLt.size(); i++) {
                    Vector ele = (Vector) gridLt.get(i);
                    LoanInspectionRecordTable rd = new LoanInspectionRecordTable();
                    rd.setRecno(ele.get(0).toString());
                    rd.setAcno(ele.get(1).toString());
                    rd.setRoi(ele.get(2).toString());
                    rd.setSlabFloor(ele.get(3).toString());
                    rd.setSlabciel(ele.get(4).toString());
                    rd.setFromDate(ele.get(5).toString());
                    rd.setEnterby(ele.get(6).toString());
                    rd.setTrantime(ele.get(7).toString());
                    datalist.add(rd);
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void saveGrid() {
        try {
            String a[][] = new String[list.size()][7];
            for (int i = 0; i < list.size(); i++) {
                a[i][0] = ((LoanInspectionTable) list.get(i)).getAcno().toString();
                a[i][1] = new Double(((LoanInspectionTable) list.get(i)).getRoi()).toString();
                a[i][2] = new Double(((LoanInspectionTable) list.get(i)).getSlabFloor()).toString();
                a[i][3] = new Double(((LoanInspectionTable) list.get(i)).getSlabciel()).toString();
                a[i][4] = ((LoanInspectionTable) list.get(i)).getFromDate().toString();
                a[i][5] = ((LoanInspectionTable) list.get(i)).getEnterby().toString();
                a[i][6] = ((LoanInspectionTable) list.get(i)).getTrantime().toString();
            }
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < 7; j++) {
                    Object combinedArray = a[i][j];
                    arraylist.add(combinedArray);
                }
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void saveClick() {
        try {
            saveGrid();
            String result;
            result = testEJB.saveInspectionData(arraylist, insmntCode);
            this.setMessage(result);
            list = new ArrayList<LoanInspectionTable>();
            arraylist = new ArrayList();
            setFrom("0.0");
            setTo("0");
            setChargesAmt("0");
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String onblurChecking() {
        try {
            this.setMessage("");
            if (asOnDate == null) {
                this.setMessage("Please fill the Applicable Date");
                return "false";
            }
            if (from == null || from.equalsIgnoreCase("")) {
                this.setMessage("Please enter numeric value in Amount From");
                return "false";
            } else {
                if (!from.matches("[0-9.]*")) {
                    this.setMessage("Please enter numeric value in Amount From");
                    return "false";
                }
            }
            if (to == null || to.equalsIgnoreCase("")) {
                this.setMessage("Please enter numeric value in Amount To");
                return "false";
            } else {
                if (!to.matches("[0-9.]*")) {
                    this.setMessage("Please enter numeric value in Amount To");
                    return "false";
                }
            }
            if (chargesAmt == null || chargesAmt.equalsIgnoreCase("")) {
                this.setMessage("Please enter numeric value in Charges Amount");
                return "false";
            } else {
                if (!to.matches("[0-9.]*")) {
                    this.setMessage("Please enter numeric value in Charges Amount");
                    return "false";
                }
            }
            return "true";
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
            return "false";
        }
    }

    public void tableData() {
        try {
            onblurChecking();
            setTable();
            setChargesAmt("0");
            setFrom("0");
            setTo("0");
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void refreshBtn() {
        try {
            this.setMessage("");
            list = new ArrayList<LoanInspectionTable>();
            datalist = new ArrayList<LoanInspectionRecordTable>();
            arraylist = new ArrayList();
            setInsmntCode("--Select--");
            setFrom("");
            setTo("0");
            setChargesAmt("0");
            btnDisable = true;
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitForm() {
        refreshBtn();
        return "case1";
    }
}
