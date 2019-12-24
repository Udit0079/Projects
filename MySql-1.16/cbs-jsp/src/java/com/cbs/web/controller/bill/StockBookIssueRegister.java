/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.bill;

import com.cbs.web.pojo.bill.ShowAllReciept;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.bill.BillCommissionFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.dto.Stock;
import com.cbs.dto.Issue;
import com.cbs.web.controller.BaseBean;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 *
 * @author root
 */
public class StockBookIssueRegister extends BaseBean{

    private String amtRangeFrom;
    private BigDecimal amtRangeTo;
    String cmbInstType;
    String cmbGoto;
    long cmbAmtfrm;
    long cmbAmtTo;
    String combCode;
    private String numFrom;
    private String numTo;
    String message;
    private String noFromDataIssue;
    private String noToDataIssue;
    private String leavesDataIssue;
    private String lotDataIssue;
    private boolean noToDisable;
    private boolean noFromDisable;
    private boolean noOfLeavwsDisable;
    private boolean printLOtDisable;
    private HttpServletRequest req;
    private ArrayList<SelectItem> comboGoto;
    private ArrayList<SelectItem> comboinstType;
    private ArrayList<SelectItem> comboCode;
    private ArrayList<SelectItem> comboAmtFrm = new ArrayList<SelectItem>();
    private ArrayList<SelectItem> comboAmtTo = new ArrayList<SelectItem>();
    private ArrayList<SelectItem> combNoLeaves = new ArrayList<SelectItem>();
    private ArrayList<SelectItem> combprintlot = new ArrayList<SelectItem>();
    private ArrayList<SelectItem> combAmountFrom;
    private ArrayList<SelectItem> combAmountTo = new ArrayList<SelectItem>();
    List arraylist = new ArrayList();
    private List<ShowAllReciept> allreciept;
    private List<Stock> show;
    private List<Issue> issueList;
    Stock currentItem = new Stock();
    Issue currentItem1 = new Issue();
    private ShowAllReciept currentItem2 = new ShowAllReciept();
    boolean disable;
    private String txtLeaves;
    String lot;
    String status = "F";
    int i = 1;
    String msg;
    private int currentRow;
    private int currentRow1;
    private int currentRow2;
    private InitialContext ctx;
    private BillCommissionFacadeRemote billCommFacde;
    String saveResult;
    private boolean flag;
    private boolean check;
    private boolean checkFrom;
    private boolean disableSlabCode;
    private boolean addButton;
    /**
     * Added for series deletion
     */
    private String fromDDNo;
    private String toDDNo;
    private String instrumentType;
    private String slabCode;
    private String delSeries;
    private String delMessage;

    public String getDelMessage() {
        return delMessage;
    }

    public void setDelMessage(String delMessage) {
        this.delMessage = delMessage;
    }

    public String getDelSeries() {
        return delSeries;
    }

    public void setDelSeries(String delSeries) {
        this.delSeries = delSeries;
    }

    public String getFromDDNo() {
        return fromDDNo;
    }

    public void setFromDDNo(String fromDDNo) {
        this.fromDDNo = fromDDNo;
    }

    public String getInstrumentType() {
        return instrumentType;
    }

    public void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
    }

    public String getSlabCode() {
        return slabCode;
    }

    public void setSlabCode(String slabCode) {
        this.slabCode = slabCode;
    }

    public String getToDDNo() {
        return toDDNo;
    }

    public void setToDDNo(String toDDNo) {
        this.toDDNo = toDDNo;
    }

    /**
     * Addition end
     * @return 
     */
    public boolean isAddButton() {
        return addButton;
    }

    public void setAddButton(boolean addButton) {
        this.addButton = addButton;
    }

    public boolean isDisableSlabCode() {
        return disableSlabCode;
    }

    public void setDisableSlabCode(boolean disableSlabCode) {
        this.disableSlabCode = disableSlabCode;
    }

    public String getNumFrom() {
        return numFrom;
    }

    public void setNumFrom(String numFrom) {
        this.numFrom = numFrom;
    }

    public String getCombCode() {
        return combCode;
    }

    public void setCombCode(String combCode) {
        this.combCode = combCode;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean isCheckFrom() {
        return checkFrom;
    }

    public void setCheckFrom(boolean checkFrom) {
        this.checkFrom = checkFrom;
    }

    public String getNumTo() {
        return numTo;
    }

    public void setNumTo(String numTo) {
        this.numTo = numTo;
    }

    public ShowAllReciept getCurrentItem2() {
        return currentItem2;
    }

    public void setCurrentItem2(ShowAllReciept currentItem2) {
        this.currentItem2 = currentItem2;
    }

    public int getCurrentRow2() {
        return currentRow2;
    }

    public void setCurrentRow2(int currentRow2) {
        this.currentRow2 = currentRow2;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public List<ShowAllReciept> getAllreciept() {
        return allreciept;
    }

    public void setAllreciept(List<ShowAllReciept> allreciept) {
        this.allreciept = allreciept;
    }

    public ArrayList<SelectItem> getComboCode() {
        return comboCode;
    }

    public void setComboCode(ArrayList<SelectItem> comboCode) {
        this.comboCode = comboCode;
    }

    public int getCurrentRow1() {
        return currentRow1;
    }

    public void setCurrentRow1(int currentRow1) {
        this.currentRow1 = currentRow1;
    }

    public String getLeavesDataIssue() {
        return leavesDataIssue;
    }

    public void setLeavesDataIssue(String leavesDataIssue) {
        this.leavesDataIssue = leavesDataIssue;
    }

    public String getNoToDataIssue() {
        return noToDataIssue;
    }

    public void setNoToDataIssue(String noToDataIssue) {
        this.noToDataIssue = noToDataIssue;
    }

    public String getLotDataIssue() {
        return lotDataIssue;
    }

    public void setLotDataIssue(String lotDataIssue) {
        this.lotDataIssue = lotDataIssue;
    }

    public String getNoFromDataIssue() {
        return noFromDataIssue;
    }

    public void setNoFromDataIssue(String noFromDataIssue) {
        this.noFromDataIssue = noFromDataIssue;
    }

    public boolean isNoOfLeavwsDisable() {
        return noOfLeavwsDisable;
    }

    public void setNoOfLeavwsDisable(boolean noOfLeavwsDisable) {
        this.noOfLeavwsDisable = noOfLeavwsDisable;
    }

    public boolean isNoToDisable() {
        return noToDisable;
    }

    public void setNoToDisable(boolean noToDisable) {
        this.noToDisable = noToDisable;
    }

    public boolean isNoFromDisable() {
        return noFromDisable;
    }

    public void setNoFromDisable(boolean noFromDisable) {
        this.noFromDisable = noFromDisable;
    }

    public boolean isPrintLOtDisable() {
        return printLOtDisable;
    }

    public void setPrintLOtDisable(boolean printLOtDisable) {
        this.printLOtDisable = printLOtDisable;
    }

    public List<Issue> getIssueList() {
        return issueList;
    }

    public void setIssueList(List<Issue> issueList) {
        this.issueList = issueList;
    }

    public ArrayList<SelectItem> getCombAmountFrom() {
        return combAmountFrom;
    }

    public void setCombAmountFrom(ArrayList<SelectItem> combAmountFrom) {
        this.combAmountFrom = combAmountFrom;
    }

    public ArrayList<SelectItem> getCombAmountTo() {
        return combAmountTo;
    }

    public void setCombAmountTo(ArrayList<SelectItem> combAmountTo) {
        this.combAmountTo = combAmountTo;
    }

    public ArrayList<SelectItem> getCombNoLeaves() {
        return combNoLeaves;
    }

    public void setCombNoLeaves(ArrayList<SelectItem> combNoLeaves) {
        this.combNoLeaves = combNoLeaves;
    }

    public ArrayList<SelectItem> getCombprintlot() {
        return combprintlot;
    }

    public void setCombprintlot(ArrayList<SelectItem> combprintlot) {
        this.combprintlot = combprintlot;
    }

    public String getAmtRangeFrom() {
        return amtRangeFrom;
    }

    public void setAmtRangeFrom(String amtRangeFrom) {
        this.amtRangeFrom = amtRangeFrom;
    }

    public BigDecimal getAmtRangeTo() {
        return amtRangeTo;
    }

    public void setAmtRangeTo(BigDecimal amtRangeTo) {
        this.amtRangeTo = amtRangeTo;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Stock getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(Stock currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getTxtLeaves() {
        return txtLeaves;
    }

    public void setTxtLeaves(String txtLeaves) {
        this.txtLeaves = txtLeaves;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public List<Stock> getShow() {
        return show;
    }

    public void setShow(List<Stock> show) {
        this.show = show;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getCmbAmtTo() {
        return cmbAmtTo;
    }

    public void setCmbAmtTo(long cmbAmtTo) {
        this.cmbAmtTo = cmbAmtTo;
    }

    public long getCmbAmtfrm() {
        return cmbAmtfrm;
    }

    public void setCmbAmtfrm(long cmbAmtfrm) {
        this.cmbAmtfrm = cmbAmtfrm;
    }

    public ArrayList<SelectItem> getComboAmtTo() {
        return comboAmtTo;
    }

    public void setComboAmtTo(ArrayList<SelectItem> comboAmtTo) {
        this.comboAmtTo = comboAmtTo;
    }

    public ArrayList<SelectItem> getComboAmtFrm() {
        return comboAmtFrm;
    }

    public void setComboAmtFrm(ArrayList<SelectItem> comboAmtFrm) {
        this.comboAmtFrm = comboAmtFrm;
    }

    public ArrayList<SelectItem> getComboinstType() {
        return comboinstType;
    }

    public void setComboinstType(ArrayList<SelectItem> comboinstType) {
        this.comboinstType = comboinstType;
    }

    public String getCmbInstType() {
        return cmbInstType;
    }

    public void setCmbInstType(String cmbInstType) {
        this.cmbInstType = cmbInstType;
    }

    public String getCmbGoto() {
        return cmbGoto;
    }

    public void setCmbGoto(String cmbGoto) {
        this.cmbGoto = cmbGoto;
    }

    public ArrayList<SelectItem> getComboGoto() {
        return comboGoto;
    }

    public void setComboGoto(ArrayList<SelectItem> comboGoto) {
        this.comboGoto = comboGoto;
    }

   

    public StockBookIssueRegister() {
        try {
            billCommFacde = (BillCommissionFacadeRemote) ServiceLocator.getInstance().lookup("BillCommissionFacade");
            gotocombo();
            getInstType();
            this.setDisable(true);
            comboCode = new ArrayList<SelectItem>();
            combAmountFrom = new ArrayList<SelectItem>();
            show = new ArrayList<Stock>();
            issueList = new ArrayList<Issue>();
            this.setMsg("");
            flag = true;
            allreciept = new ArrayList<ShowAllReciept>();
            setNumFrom("0");
            setNumTo("0");
            setTxtLeaves("0");
            disableSlabCode = false;
            addButton = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gotocombo() {
        setComboGoto(new ArrayList<SelectItem>());
        comboGoto.add(new SelectItem("0", "--Select--"));
        comboGoto.add(new SelectItem("1", "Stock Register"));
        comboGoto.add(new SelectItem("2", "Book Issue"));
    }

    public void getInstType() {
        try {
            List resultList = new ArrayList();
            ctx = new InitialContext();

            resultList = billCommFacde.instTypeStock();
            if (!resultList.isEmpty()) {
                comboinstType = new ArrayList<SelectItem>();
                getComboinstType().add(new SelectItem("--Select--"));
                for (int v = 0; v < resultList.size(); v++) {
                    Vector ele = (Vector) resultList.get(v);
                    getComboinstType().add(new SelectItem(ele.get(0)));
                }
            } else {
                comboinstType = new ArrayList<SelectItem>();
                getComboinstType().add(new SelectItem("--Select--"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void getCode() {
        this.setMsg("");
        try {
            comboCode = new ArrayList<SelectItem>();
            List resultList = new ArrayList();
            resultList = billCommFacde.setSlabStockBook(cmbInstType);
            comboCode = new ArrayList<SelectItem>();
            for (int t = 0; t < resultList.size(); t++) {
                Vector ele = (Vector) resultList.get(t);
                comboCode.add(new SelectItem(ele.get(0)));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // setting the range  from ,to  of amount range
    public void setRange() {
        try {
            this.setMsg("");
            comboAmtFrm = new ArrayList<SelectItem>();
            comboAmtTo = new ArrayList<SelectItem>();
            List result = new ArrayList();

            if (this.combCode == null || this.combCode.equalsIgnoreCase("") || this.combCode.length() == 0) {
                this.combCode = "0";
            }
            result = billCommFacde.setAmtSlabStockBook(cmbInstType, Integer.parseInt(combCode));
            if (!result.isEmpty()) {
                for (int k = 0; k < result.size(); k++) {
                    Vector ele = (Vector) result.get(k);
                    comboAmtFrm.add(new SelectItem(ele.get(0).toString()));
                    comboAmtTo.add(new SelectItem(new BigDecimal(Float.parseFloat(ele.get(1).toString()))));
                }
            } else {
                this.setMsg("No Data Exist For Slab code  " + combCode);
                comboAmtFrm = new ArrayList<SelectItem>();
                comboAmtTo = new ArrayList<SelectItem>();
                combAmountFrom = new ArrayList<SelectItem>();
                combAmountTo = new ArrayList<SelectItem>();
                combNoLeaves = new ArrayList<SelectItem>();
                combprintlot = new ArrayList<SelectItem>();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void showData() //throws NamingException {
    {
        show = new ArrayList<Stock>();
        try {
            List resultLt = new ArrayList();

            resultLt = billCommFacde.showDataStockBook(cmbInstType, getOrgnBrCode());
            if (resultLt.size() > 0) {
                for (int j = 0; j < resultLt.size(); j++) {
                    Vector ele = (Vector) resultLt.get(j);
                    Stock rd = new Stock();
                    rd.setsNo(Integer.parseInt(ele.get(0).toString()));
                    rd.setInstCode(ele.get(1).toString());
                    rd.setCode(Integer.parseInt(ele.get(2).toString()));
                    rd.setNumFrom(ele.get(3).toString());
                    rd.setNumTo(ele.get(4).toString());
                    rd.setAmtFrom(Float.parseFloat(ele.get(5).toString()));
                    rd.setAmtto(new BigDecimal(Float.parseFloat((String) ele.get(6))));
                    rd.setLeaves(ele.get(7).toString());
                    rd.setPrintlot(ele.get(8).toString());
                    rd.setStatus(ele.get(9).toString());
                    rd.setEnterBy(ele.get(10).toString());
                    show.add(rd);
                }
            } else {
                this.setMessage("No Record Found!!!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void chkSeriesStock() {
        try {
            this.setMsg("");
            String result = null;
            Date date = new Date();
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            String tdate = ymd.format(date);
            result = billCommFacde.chkSeriesStock(cmbInstType, Integer.parseInt(combCode), lot, Integer.parseInt(numFrom), Integer.parseInt(numTo), getOrgnBrCode());
            saveResult = result;
            this.setMsg(result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveClick() {
        this.setMsg("");
        try {
            if (saveResult.equalsIgnoreCase("Cheque Book Series Already Issued")) {
                this.setMsg("Cheque Book Series Already Issued");

            } else {
                String result;
                Date date = new Date();
                SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
                String tdate = ymd.format(date);
                result = billCommFacde.saveStockBook(show, tdate, getOrgnBrCode());
                this.setMsg(result);
                disable = true;
                show = new ArrayList<Stock>();
                setTxtLeaves("0");
                setNumTo("0");
                setNumFrom("0");
                setLot("");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private Stock createObj() throws ParseException {
        Stock stk = new Stock();
        stk.setAmtFrom(Float.parseFloat(amtRangeFrom.toString()));
        stk.setAmtto(amtRangeTo);
        stk.setCode(Integer.parseInt(combCode));
        stk.setEnterBy(getUserName());
        stk.setInstCode(cmbInstType);
        stk.setNumFrom(numFrom);
        stk.setNumTo(numTo);
        stk.setLeaves(txtLeaves);
        stk.setPrintlot(lot);
        stk.setStatus(status);
        stk.setsNo(i++);
        return stk;
    }

    public void setTable() throws ParseException {
        if (cmbInstType.equalsIgnoreCase("--Select--") || combCode == null) {
            this.setMsg("Please Select The InstType And SlabCode");
            return;
        }
        if (numFrom.equalsIgnoreCase("") || numFrom == null) {
            this.setMsg("Please fill Positive Value in No. From.");
            return;
        }
        if (numTo.equalsIgnoreCase("") || numTo == null) {
            this.setMsg("Please fill Positive Value in No. To");
            return;
        }
        if (onblurChecking().equalsIgnoreCase("false")) {
            return;
        }
        chkSeriesStock();
        if (saveResult.equals("Cheque Book Series Already Issued")) {
            setMsg("Cheque Book Series Already Issued");
            return;
        }
        if (show.size() > 0) {
            setMsg("Before Change the PrintLot,InstCode and Amount Slab,Please You SAVE DATA");
            return;
        }


        if (numTo != null && numFrom != null) {
            if (Integer.parseInt(numTo) < Integer.parseInt(numFrom)) {
                this.setMsg("To Number Must Be Greater Than From Number!!!");
                return;
            }
        }

        if (lot.equalsIgnoreCase("")) {
            this.setMsg("You must insert Print Lot");
        } else {
            Stock stk = createObj();
            show.add(stk);
            disable = false;
        }
    }

    public void setLeaves() {
        if (numFrom.equalsIgnoreCase("") || numFrom == null) {
            this.setMsg("Please fill Positive Value in No. From.");
        } else {
            if (numTo.equalsIgnoreCase("") || numTo == null) {
                this.setMsg("Please fill Positive Value in No. To");
            } else {
                if (onblurChecking().equalsIgnoreCase("false")) {
                    return;
                }
                if (Integer.parseInt(numFrom) < 0) {
                    this.setMsg("No. from Can't Be Negative. Please fill Positive Value");
                } else if (Integer.parseInt(numTo) < 0) {
                    this.setMsg("No. To Can't Be Negative. Please fill Positive Value");
                }
                checkFrom = true;
                if (Integer.parseInt(numTo) - Integer.parseInt(numFrom) > 0) {
                    int total = (Integer.parseInt(numTo) - Integer.parseInt(numFrom)) + 1;
                    txtLeaves = Integer.toString(total);
                } else {
                    this.setMsg("To Number Must Be Greater Than From Number!!!");
                    checkFrom = false;
                }
            }
        }
    }

    public void fetchCurrentRow(ActionEvent event) {
        String SNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("Status"));
        currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (Stock item : show) {
            if (item.getInstCode().equals(SNo)) {
                currentItem = item;
                break;
            }
        }
    }

    public void delete() {
        show.remove(currentRow);
    }

    public void getCodeIssue() {
        this.setMsg("");
        try {
            comboCode = new ArrayList<SelectItem>();
            List resultList = new ArrayList();
            resultList = billCommFacde.setSlabStockBook(cmbInstType);
            setComboCode(new ArrayList<SelectItem>());
            for (int t = 0; t < resultList.size(); t++) {
                Vector ele = (Vector) resultList.get(t);
                comboCode.add(new SelectItem(ele.get(0)));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setRangeIssue() {
        try {
            disableSlabCode = false;
            this.setMsg("");
            combAmountFrom = new ArrayList<SelectItem>();
            combAmountTo = new ArrayList<SelectItem>();
            combNoLeaves = new ArrayList<SelectItem>();
            combprintlot = new ArrayList<SelectItem>();
            List result = new ArrayList();
            if (this.combCode == null || this.combCode.equalsIgnoreCase("") || this.combCode.length() == 0) {
                this.combCode = "0";
            }

            result = billCommFacde.setCodeTableIssue(cmbInstType, Integer.parseInt(combCode), getOrgnBrCode());
            if (!result.isEmpty()) {
                for (int k = 0; k < result.size(); k++) {
                    Vector ele = (Vector) result.get(k);
                    //combAmountFrom.add(new SelectItem(ele.get(0).toString()));
                    combAmountFrom.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                    combAmountTo.add(new SelectItem(ele.get(2).toString()));
                    combNoLeaves.add(new SelectItem(ele.get(3).toString()));
                    combprintlot.add(new SelectItem(ele.get(4).toString()));
                }
                if (cmbGoto.equalsIgnoreCase("2")) {
                    disableSlabCode = true;
                }

            } else {
                this.setMsg("No Data Exist For Slab Code" + combCode);
                disableSlabCode = false;
                if (noFromDataIssue == null && noToDataIssue == null && leavesDataIssue == null && lotDataIssue == null) {
                    setMsg("No Data Found!!!");
                    setNoFromDisable(true);
                    setNoToDisable(true);
                    setNoOfLeavwsDisable(true);
                    setPrintLOtDisable(true);
                    return;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setnumberFromValue() {
        try {
            disableSlabCode = false;
            this.setMsg("");
            //combAmountFrom = new ArrayList<SelectItem>();
            combAmountTo = new ArrayList<SelectItem>();
            combNoLeaves = new ArrayList<SelectItem>();
            combprintlot = new ArrayList<SelectItem>();
            List result = new ArrayList();

            result = billCommFacde.setNumberFromData(noFromDataIssue, Integer.parseInt(combCode), getOrgnBrCode());
            if (!result.isEmpty()) {
                for (int k = 0; k < result.size(); k++) {
                    Vector ele = (Vector) result.get(k);
                    combAmountTo.add(new SelectItem(ele.get(2).toString()));
                    combNoLeaves.add(new SelectItem(ele.get(3).toString()));
                    combprintlot.add(new SelectItem(ele.get(4).toString()));
                }
                if (cmbGoto.equalsIgnoreCase("2")) {
                    disableSlabCode = true;
                }

            } else {
                this.setMsg("No Data Exist For Slab Code" + combCode);
                disableSlabCode = false;
                if (noFromDataIssue == null && noToDataIssue == null && leavesDataIssue == null && lotDataIssue == null) {
                    setMsg("No Data Found!!!");
                    setNoFromDisable(true);
                    setNoToDisable(true);
                    setNoOfLeavwsDisable(true);
                    setPrintLOtDisable(true);
                    return;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void showDataIssue() //throws NamingException {
    {
        issueList = new ArrayList<Issue>();
        try {
            List resultLt = new ArrayList();

            resultLt = billCommFacde.showDataStockBook(cmbInstType, getOrgnBrCode());
            if (resultLt.size() > 0) {
                for (int j = 0; j < resultLt.size(); j++) {
                    Vector ele = (Vector) resultLt.get(j);
                    Issue rd = new Issue();
                    rd.setInsttype(ele.get(0).toString());
                    rd.setCode(Integer.parseInt(ele.get(1).toString()));
                    rd.setNumFrom((ele.get(2).toString()));
                    rd.setNumTo((ele.get(3).toString()));
                    rd.setAmtFrom(Float.parseFloat(ele.get(4).toString()));
                    rd.setAmtTo(new BigDecimal(ele.get(5).toString()));
                    rd.setLeaves((ele.get(6).toString()));
                    rd.setPrintlot(ele.get(7).toString());
                    rd.setIssuedBy(ele.get(8).toString());
                    issueList.add(rd);
                }
            } else {
                this.setMessage("There does not exist Any data");
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    private Issue createObjIssue() throws ParseException {

        String numFrm = null;
        try {
            numFrm = billCommFacde.numberFromData(noFromDataIssue, getOrgnBrCode());
        } catch (ApplicationException ex) {
            Logger.getLogger(StockBookIssueRegister.class.getName()).log(Level.SEVERE, null, ex);
        }
        Issue issue = new Issue();
        issue.setAmtFrom(Float.parseFloat(amtRangeFrom.toString()));
        issue.setAmtTo(amtRangeTo);
        issue.setCode(Integer.parseInt(combCode));
        issue.setIssuedBy(getUserName());
        issue.setInsttype(cmbInstType);
        issue.setNumFrom(numFrm);
        issue.setNumTo(noToDataIssue);
        issue.setLeaves(leavesDataIssue);
        issue.setPrintlot(lotDataIssue);
        return issue;
    }

    public void setTableIssue() throws ParseException {
        issueList = new ArrayList<Issue>();
        combAmountTo = new ArrayList<SelectItem>();
        combNoLeaves = new ArrayList<SelectItem>();
        combprintlot = new ArrayList<SelectItem>();
        try {
            if (noFromDataIssue == null && noToDataIssue == null && leavesDataIssue == null && lotDataIssue == null) {
                setMsg("No Data Find!!!");
                issueList = new ArrayList<Issue>();
                return;
            }
            flag = true;
            setnumberFromValue();
            disable = false;
            addButton = false;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String addButton() {
        issueList = new ArrayList<Issue>();
        try {
            Issue stk = createObjIssue();
            issueList.add(stk);
            addButton = true;
            return "";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public void fetchCurrentRowIssue(ActionEvent event) {
        String SNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("Code"));
        currentRow1 = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));

        for (Issue item : issueList) {
            if (item.getInsttype().equals(SNo)) {
                currentItem1 = item;
                break;
            }
        }
    }

    public void deleteIssue() {
        issueList.remove(currentRow1);
    }

    public void checkGoto() {

        if (cmbGoto.equalsIgnoreCase("1")) {
        } else if (cmbGoto.equalsIgnoreCase("2")) {
            setRangeIssue();
        }
    }

    public void setRangeIssueBook() {
        try {
            this.setMsg("");
            if (combCode == null || combCode.equalsIgnoreCase("")) {
                this.setMsg("Please Select Proper Inst Type");
                return;
            }

            comboAmtFrm = new ArrayList<SelectItem>();
            comboAmtTo = new ArrayList<SelectItem>();
            List result = new ArrayList();

            result = billCommFacde.setAmtSlabIssueStockBook(cmbInstType, Integer.parseInt(combCode), getOrgnBrCode());
            if (!result.isEmpty()) {
                for (int k = 0; k < result.size(); k++) {
                    Vector ele = (Vector) result.get(k);
                    comboAmtFrm.add(new SelectItem(ele.get(0).toString()));
                    comboAmtTo.add(new SelectItem(new BigDecimal(Float.parseFloat(ele.get(1).toString()))));
                }
            } else {
                this.setMsg("No Data Exist For Slab Code = " + combCode);
                comboAmtFrm = new ArrayList<SelectItem>();
                comboAmtTo = new ArrayList<SelectItem>();
                combAmountFrom = new ArrayList<SelectItem>();
                combAmountTo = new ArrayList<SelectItem>();
                combNoLeaves = new ArrayList<SelectItem>();
                combprintlot = new ArrayList<SelectItem>();
                return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void checkSaveButton() {
        if (cmbGoto.equalsIgnoreCase("1")) {

            saveClick();

        } else if (cmbGoto.equalsIgnoreCase("2")) {

            saveClickIssue();
        }
    }

    public void checkSlabAmtCode() {
        if (cmbGoto.equalsIgnoreCase("1")) {
            setRange();

        } else if (cmbGoto.equalsIgnoreCase("2")) {
            setNoFromDisable(false);
            setNoToDisable(false);
            setNoOfLeavwsDisable(false);
            setPrintLOtDisable(false);
            setRangeIssueBook();
        }
    }

    public void saveClickIssue() {
        flag = true;
        try {
            this.setMsg("");
            String result;
            Date date = new Date();
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            String tdate = ymd.format(date);

            result = billCommFacde.saveBookIssue(issueList, tdate, getOrgnBrCode(), getUserName());
            this.setMsg(result);
            disable = true;
            issueList = new ArrayList<Issue>();
//            combAmountFrom = new ArrayList<SelectItem>();
//            combAmountTo = new ArrayList<SelectItem>();
//            combNoLeaves = new ArrayList<SelectItem>();
//            combprintlot = new ArrayList<SelectItem>();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void bookIssueGridDblClick() {
        try {

            this.setMsg("");
            String result;
            Date date = new Date();
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            String tdate = ymd.format(date);
            result = billCommFacde.bookIssueGridDoubleClick(tdate, getOrgnBrCode(), getUserName(), currentItem2.getInsType(), Integer.parseInt(combCode), currentItem2.getDdnum(), currentItem2.getSeries());

            changeValue();
            this.setMsg(result);
            disable = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void showAllDataStoc() {
        if (cmbInstType.equalsIgnoreCase("--Select--") || combCode == null) {
            this.setMsg("Please Select The InstType And SlabCode");
        } else {
            flag = false;
            if (cmbGoto.equalsIgnoreCase("1")) {
                getTableDetails();
            } else if (cmbGoto.equalsIgnoreCase("2")) {
                setNoFromDisable(true);
                setNoToDisable(true);
                setNoOfLeavwsDisable(true);
                setPrintLOtDisable(true);
                getTableDetails();
            }
        }
    }

    public void fetchCurrentRow2(ActionEvent event) {
        String accNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("Series"));
        currentRow2 = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (ShowAllReciept Item : allreciept) {
            if (Item.getSeries().equals(accNo)) {
                currentItem2 = Item;
                break;
            }
        }
    }

    public void getTableDetails() {
        try {
            allreciept = new ArrayList<ShowAllReciept>();
            String resultList;
            int j, k, l, m, n, o;
            resultList = billCommFacde.showAllDataIssue(cmbInstType, Integer.parseInt(combCode), getOrgnBrCode());
            if (resultList.equalsIgnoreCase("No Data Resulted")) {
                this.setMsg("There Does Not Exist Any Data For This Inst Type -->  " + cmbInstType + "  And   Slab Code -->  " + combCode);

            }
            if (resultList.contains("[")) {
                String[] values = null;
                resultList = resultList.replace("]", "");
                resultList = resultList.replace("[", "");
                String spliter = ", ";
                values = resultList.split(spliter);
                for (j = 0, k = 1, l = 2, m = 3, n = 4, o = 5; o <= (values.length); j = j + 6, k = k + 6, l = l + 6, m = m + 6, n = n + 6, o = o + 6) {
                    ShowAllReciept rd = new ShowAllReciept();
                    rd.setInsType(values[j]);
                    rd.setDdnum(Float.parseFloat(values[k]));
                    rd.setSeries(values[l]);
                    rd.setStats(values[m]);
                    rd.setAmtFrm(Float.parseFloat(values[n]));
                    rd.setAmtto(new BigDecimal(Float.parseFloat((String) values[o])));
                    allreciept.add(rd);
                }
            } else {
                this.setMessage(resultList);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void changeValue() {
        ShowAllReciept item = allreciept.get(currentRow2);
        if (item.getStats().equalsIgnoreCase("F")) {
            item.setStats("D");
            allreciept.remove(currentRow);
            allreciept.add(currentRow, item);
        }
    }

    public void bookIssueGridDoubleClick() {
        try {
            this.setMsg("");
            String result;
            Date date = new Date();
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            String tdate = ymd.format(date);

            result = billCommFacde.bookIssueGridDoubleClick(tdate, getOrgnBrCode(), getUserName(), cmbInstType, Integer.parseInt(combCode), currentItem2.getDdnum(), currentItem2.getSeries());
            changeValue();
            this.setMsg(result);
            disable = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void refreshButton() {
        try {
            disable = true;
            checkFrom = false;
            this.setMsg("");
            flag = true;
            setCmbGoto("--Select--");
            setCmbInstType("--Select--");
            setTxtLeaves("0");
            setNumTo("0");
            setNumFrom("0");
            setLot("");
            disableSlabCode = false;
            combAmountFrom = new ArrayList<SelectItem>();
            show = new ArrayList<Stock>();
            issueList = new ArrayList<Issue>();
            allreciept = new ArrayList<ShowAllReciept>();
            comboCode = new ArrayList<SelectItem>();
            comboAmtFrm = new ArrayList<SelectItem>();
            comboAmtTo = new ArrayList<SelectItem>();
            combNoLeaves = new ArrayList<SelectItem>();
            combprintlot = new ArrayList<SelectItem>();
            combAmountTo = new ArrayList<SelectItem>();
            refreshDelForm();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String onblurChecking() {
        try {
            if (numFrom == null || numFrom.equalsIgnoreCase("")) {
            } else {
                if (!numFrom.matches("[0-9]*")) {
                    this.setMsg("Please Enter Numeric Value in  No. From");
                    return "false";
                }
            }
            if (numTo == null || numTo.equalsIgnoreCase("")) {
            } else {
                if (!numTo.matches("[0-9]*")) {
                    this.setMsg("Please Enter Numeric Value in No. To");
                    return "false";
                }
            }
            if (txtLeaves == null || txtLeaves.equalsIgnoreCase("")) {
            } else {
                if (!txtLeaves.matches("[0-9]*")) {
                    this.setMsg("Please Enter Numeric Value in No. Of Leaves");
                    return "false";
                }
            }
            if (lot == null || lot.equalsIgnoreCase("")) {
            } else {
                if (!lot.matches("[0-9 a-zA-Z]*")) {
                    this.setMsg("Print Lot Can't be Negative");
                    return "false";
                }
            }
            return "true";
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
        return "true";
    }

    public void tableChange() {
        issueList = new ArrayList<Issue>();
        try {
            if (cmbGoto.equalsIgnoreCase("0")) {
                refreshButton();
                this.setMessage("");
                if (allreciept.size() > 0) {
                    flag = true;
                }
            }
//            this.setMessage("");
//            if (cmbGoto.equalsIgnoreCase("0")) {
//                if (allreciept.size() > 0) {
//                    flag = true;
//                }
//            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public String exitBtnAction() {
        refreshButton();
        return "case1";
    }

    public void deleteSeries() {
        this.setDelMessage("");
        try {
            if (this.fromDDNo == null || this.fromDDNo.equals("")) {
                this.setDelMessage("Please Fill To DDNo !");
                return;
            }
            if (this.toDDNo == null || this.toDDNo.equals("")) {
                this.setDelMessage("Please Fill To DDNo !");
                return;
            }
            if (!fromDDNo.matches("[0-9]*")) {
                this.setDelMessage("Please enter numeric value in From DDNo !");
                return;
            }
            if (!toDDNo.matches("[0-9]*")) {
                this.setDelMessage("Please enter numeric value in To DDNo !");
                return;
            }
            double fromNo = Double.parseDouble(this.fromDDNo);
            double toNo = Double.parseDouble(this.toDDNo);
            if (toNo < fromNo) {
                this.setDelMessage("To DDNo can not be less than From DDNo !");
                return;
            }
            if (this.instrumentType.equalsIgnoreCase("--Select--")) {
                this.setDelMessage("Please Select Instrument Type !");
                return;
            }
            if (this.slabCode == null || this.slabCode.equals("")) {
                this.setDelMessage("Please Fill Slab Code !");
                return;
            }
            if (this.delSeries == null || this.delSeries.equals("")) {
                this.setDelMessage("Please Fill Series !");
                return;
            }
            String result = billCommFacde.deleteSeries(fromNo, toNo, instrumentType, slabCode, delSeries, getUserName(), getOrgnBrCode());
            this.setDelMessage(result);
        } catch (Exception ex) {
            this.setDelMessage(ex.getMessage());
        }
    }

    public void refreshDelForm() {
        this.setDelMessage("");
        this.setFromDDNo("");
        this.setToDDNo("");
        this.setInstrumentType("--Select--");
        this.setSlabCode("");
        this.setDelSeries("");
    }
}
