/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.inventory;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.bill.BillCommissionFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.inventory.InventorySplitAndMergeFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.inventory.IssueCheqbook;
import com.cbs.web.pojo.inventory.TableChBook;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;
import javax.naming.InitialContext;

/**
 *
 * @author root
 */
public class IssueChBookWithSlab extends BaseBean {
    private final String jndiHomeName = "InventorySplitAndMergeFacade";
    InventorySplitAndMergeFacadeRemote beanRemote;
    private String chqFrom;
    private String chqTo;
    private String noOfLeaves;
    private String cheqSeries;
    private String printlot;
    private String remarks;
    private String message;
    private List<IssueCheqbook> stocktable;
    private int currentRow;
    private IssueCheqbook currentItem = new IssueCheqbook();
    private List<TableChBook> issueTable;
    private boolean flag, flagDisable;
    private boolean Valid;
    private boolean seriesDisable;
    private boolean disableSlabCode;
    private boolean check;
    private boolean checkFrom;
    private boolean checkLeaves;
    private String amtRangeFrom;
    private BigDecimal amtRangeTo;
    private boolean noToDisable;
    private boolean noFromDisable;
    private boolean noOfLeavwsDisable;
    String cmbInstType;
    String cmbGoto;
    long cmbAmtfrm;
    long cmbAmtTo;
    String combCode;
    private String invtClass;
    private List<SelectItem> invtClassList;
    private String invtType;
    private List<SelectItem> invtTypeList;
    private List<SelectItem> chequeSeriesList;
    private ArrayList<SelectItem> comboGoto;
    private ArrayList<SelectItem> comboinstType;
    private ArrayList<SelectItem> comboCode;
    private ArrayList<SelectItem> comboAmtFrm = new ArrayList<SelectItem>();
    private ArrayList<SelectItem> comboAmtTo = new ArrayList<SelectItem>();
    private ArrayList<SelectItem> combNoLeaves = new ArrayList<SelectItem>();
    private ArrayList<SelectItem> combprintlot = new ArrayList<SelectItem>();
    private ArrayList<SelectItem> combAmountFrom;
    private ArrayList<SelectItem> combAmountTo = new ArrayList<SelectItem>();
    private final String FtsPostingMgmtFacadeJndiName = "FtsPostingMgmtFacade";
    private BillCommissionFacadeRemote billCommFacde;
    private InitialContext ctx;
    private FtsPostingMgmtFacadeRemote fts = null;

   
    public IssueChBookWithSlab(){
        try{
          beanRemote = (InventorySplitAndMergeFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
          billCommFacde = (BillCommissionFacadeRemote) ServiceLocator.getInstance().lookup("BillCommissionFacade");
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(FtsPostingMgmtFacadeJndiName);
            setTodayDate(sdf.format(date));
            this.setMessage("");
            setSeriesDisable(false);
            getInstType();
            combAmountFrom = new ArrayList<SelectItem>();
            invtTypeList = new ArrayList<SelectItem>();
            invtTypeList.add(new SelectItem("--Select--"));
            invtClassListDetail();
            flagDisable = true;
        }catch(Exception ex){
            this.setMessage(ex.getMessage());
        }
    }
    
    public void invtClassListDetail(){
        try{
            List resultList =new ArrayList();
            resultList = beanRemote.inventoryClassCombo();
            invtClassList = new ArrayList<SelectItem>();
            invtClassList.add(new SelectItem("--Select--"));
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    invtClassList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
            }
        }catch(ApplicationException e){
            this.setMessage(e.getMessage());
        }catch(Exception ex){
            this.setMessage(ex.getMessage());
        }
        
    }
    
    public void setChequeSeriesList(){
        try{
            this.setMessage("");
            stocktable = new ArrayList<IssueCheqbook>();
            setChqFrom("");
            setChqTo("");
            setNoOfLeaves("");
            chequeSeriesList = new ArrayList<SelectItem>();
            chequeSeriesList.add(new SelectItem("--Select--"));
            if (this.invtClass.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please select inventory class.");
                return;
            }
            if (this.invtType.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please select Inventory Type.");
                return;
            }
            List resultList = new ArrayList();
            resultList = beanRemote.chequeSeriesCombo(getOrgnBrCode(), invtClass, invtType);
            if (!resultList.isEmpty()) {
                chequeSeriesList = new ArrayList<SelectItem>();
                chequeSeriesList.add(new SelectItem("--Select--"));
                if (!resultList.isEmpty()) {
                    for (int i = 0; i < resultList.size(); i++) {
                        Vector ele = (Vector) resultList.get(i);
                        chequeSeriesList.add(new SelectItem(ele.get(0).toString()));
                    }
                }
                 } else {
                this.setMessage("No Cheque Series available corresponding this Inventory Type");
            }
        }catch(ApplicationException ex){
            this.setMessage(ex.getMessage());
        }catch(Exception ex){
            this.setMessage(ex.getMessage());
        }
    }
    
    public void setInventoryType(){
        try{
         this.setMessage("");
            invtTypeList = new ArrayList<SelectItem>();
            invtTypeList.add(new SelectItem("--Select--"));
            if (this.invtClass.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please select inventory class.");
                return;
            }
            List resultList = new ArrayList();
            resultList = beanRemote.inventoryTypeCombo(invtClass);
            invtTypeList = new ArrayList<SelectItem>();
            invtTypeList.add(new SelectItem("--Select--"));
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    invtTypeList.add(new SelectItem(ele.get(0).toString()));
                }
            }
        }catch(ApplicationException e){
            this.setMessage(e.getMessage());
        }catch(Exception ex){
            this.setMessage(ex.getMessage());
        }
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
        this.setMessage("");
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
      
     public void checkSlabAmtCode(){
           
            setRange();
}
      
     
      
      public void setRange(){
           try {
            this.setMessage("");
            comboAmtFrm = new ArrayList<SelectItem>();
            comboAmtTo = new ArrayList<SelectItem>();
            List result = new ArrayList();

            if (this.combCode == null || this.combCode.equalsIgnoreCase("") || this.combCode.length() == 0) {
                this.combCode = "0";
            }
            result = billCommFacde.setAmtSlabIssueStockBook(cmbInstType, Integer.parseInt(combCode), getOrgnBrCode());
            if (!result.isEmpty()) {
                for (int k = 0; k < result.size(); k++) {
                    Vector ele = (Vector) result.get(k);
                    comboAmtFrm.add(new SelectItem(ele.get(0).toString()));
                    comboAmtTo.add(new SelectItem(new BigDecimal(Float.parseFloat(ele.get(1).toString()))));
                 }
            } else {
                this.setMessage("No Data Exist For Slab code  " + combCode);
                comboAmtFrm = new ArrayList<SelectItem>();
                comboAmtTo = new ArrayList<SelectItem>();
                combAmountFrom = new ArrayList<SelectItem>();
                combAmountTo = new ArrayList<SelectItem>();
                combNoLeaves = new ArrayList<SelectItem>();
                combprintlot =new ArrayList<SelectItem>();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
      }

      
     public void getTableStock(){
        try{
            if (this.invtClass.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please select inventory class.");
                return;
            }
            if (this.invtType.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please select Inventory Type.");
                return;
            }
            if (this.cheqSeries.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please select Cheque Series.");
                return;
            }
            stocktable = new ArrayList<IssueCheqbook>();
            List resultLt = new ArrayList();
            resultLt = beanRemote.gridLoadForStockDetail(getOrgnBrCode(), this.invtClass, this.invtType, this.cheqSeries);
             int j = 1;
            if (!resultLt.isEmpty()) {
                for (int i = 0; i < resultLt.size(); i++) {
                    Vector ele = (Vector) resultLt.get(i);
                    IssueCheqbook rd = new IssueCheqbook();
                    rd.setChNofrom(ele.get(0).toString());
                    rd.setChNoTo(ele.get(1).toString());
                    rd.setStockDt(ele.get(2).toString());
                    rd.setSno(j++);
                    stocktable.add(rd);
                }
            } else {
                this.setMessage("Inventory does not exists to issue.");
                return;
            }
        }catch(ApplicationException ex){
            this.setMessage(ex.getMessage());
        }catch(Exception ex){
            this.setMessage(ex.getMessage());
        }
    }
    
    public void select(){
        setRemarks("");
        setFlagDisable(false);
        setChqFrom(currentItem.getChNofrom());
        setChqTo(currentItem.getChNoTo());
        int result = (Integer.parseInt(chqTo) - Integer.parseInt(chqFrom)) + 1;
        setNoOfLeaves(Integer.toString(result));
    }
    
    public void setLeaves(){
        if (onblurChecking().equalsIgnoreCase("false")) {
            return;
        }
        check = true;
        checkFrom = true;
        checkLeaves = true;
        this.setMessage("");
        if (chqTo != null) {
                if (Integer.parseInt(chqTo) - Integer.parseInt(chqFrom) > 0) {
                    int noLeaves = (Integer.parseInt(chqTo) - Integer.parseInt(chqFrom)) + 1;
                    setNoOfLeaves(Integer.toString(noLeaves));

                } else {

                    this.setMessage("Chq. No. To Must Be Greater Than Chq. No. From !!!");
                    check = false;
                }
                if ((Integer.parseInt(chqTo) - Integer.parseInt(chqFrom)) > 100) {
                    this.setMessage("Sorry, Leaves Can't Exceed 100");
                    check = false;
                }

                if ((Integer.parseInt(chqTo) - Integer.parseInt(chqFrom)) < 0) {
                    this.setMessage("Please Enter the Cheque Series properly");
                    check = false;
                }
                if (Integer.parseInt(chqFrom) < 0) {
                    this.setMessage("Chq. No. From Can't Be Less than Zero ");
                    checkFrom = false;
                } else if (Integer.parseInt(chqTo) < 0) {
                    this.setMessage("Chq.No. To Can't Be Less than Zero ");
                    check = false;

                } else if (Integer.parseInt(noOfLeaves) < 0) {
                    this.setMessage("No. Of Leaves Can't Be Less than Zero ");
                    checkLeaves = false;
                }else {
                this.setMessage("Please Enter the Chq.No. To");
                check = false;
                }}else {
            this.setMessage("Please Enter the Chq. No. From");
        }
    }
    
    public void saveBtnAction(){
        try{
             if (onblurChecking().equalsIgnoreCase("false")) {
                return;
            }
             if (cheqSeries.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please Select the cheque series");
                return;
            }
               if (Integer.parseInt(chqTo) <= 0) {
                this.setMessage("Cheq No To Not less Than Zero Or Equal Zero! please fill proper series");
                return;
            }
            if (Integer.parseInt(chqFrom) <= 0) {
                this.setMessage("Cheq No from Not less Than Zero Or Equal Zero!please fill proper series");
                return;
            }
            if ((Integer.parseInt(chqTo) - Integer.parseInt(chqFrom)) > 100) {
                this.setMessage("SORRY LEAFS CAN NOT EXCEED 100 !");
                return;
            }

            if ((Integer.parseInt(chqTo) - Integer.parseInt(chqFrom)) < 0) {
                this.setMessage("PLEASE ENTER NO OF LEAFS PROPERLY !");
                return;
            }
            if ((Integer.parseInt(chqTo) < Integer.parseInt(chqFrom))) {
                this.setMessage("Chq.No. To Is Greater than Chq. No. From");
                return;
            }
            
             SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
             String result =beanRemote.ChqBookIssueWithSlabDeatilSave(chqFrom,this.chqTo,this.noOfLeaves,this.cheqSeries,this.cmbInstType,
                     this.amtRangeFrom.toString(),amtRangeTo.toString(),this.combCode,this.invtClass,this.invtType,this.printlot,getOrgnBrCode(),getUserName(),getTodayDate());
               this.setMessage(result);
                setChqFrom("");
                setChqTo("");
                setNoOfLeaves("");
                setRemarks("");
                setAmtRangeFrom("");
                setAmtRangeTo(null);
                setPrintlot("");
                setCmbInstType("");
                setCombCode("");
                this.comboAmtFrm=null;
            this.comboAmtTo=null;
            this.comboCode=null;
            
            
            comboCode = new ArrayList<SelectItem>();
            comboAmtFrm = new ArrayList<SelectItem>();
            comboAmtTo = new ArrayList<SelectItem>();
            issueTable = new ArrayList<TableChBook>();
            stocktable = new ArrayList<IssueCheqbook>();
            //setStAccountNo("");
           
            this.setInvtClass("--Select--");
            this.setInvtType("--Select--");
            this.setCheqSeries("--Select--");
            flagDisable = true;
           
            
        }catch(ApplicationException e){
            this.setMessage(e.getMessage());
        }catch(Exception e){
            this.setMessage(e.getMessage());
        }
        
        
        
    }


     public String onblurChecking(){
        try{
            if (chqFrom == null || chqFrom.equalsIgnoreCase("")) {
                this.setMessage("Please Fill Chq. No. From");
                return "false";
            } else {
                if (!chqFrom.matches("[0-9]*")) {
                    this.setMessage("Please Enter Numeric Value in Chq. No. From");
                    return "false";
                }
            }
            if (chqTo == null || chqTo.equalsIgnoreCase("")) {
                this.setMessage("Please Fill Chq.No. To");
                return "false";
            } else {
                if (!chqTo.matches("[0-9]*")) {
                    this.setMessage("Please Enter Numeric Value in Chq.No. To");
                    return "false";
                }
            }
            if (noOfLeaves == null || noOfLeaves.equalsIgnoreCase("")) {
                this.setMessage("Please Fill No. Of Leaves");
                return "false";
            } else {
                if (!noOfLeaves.matches("[0-9]*")) {
                    this.setMessage("Please Enter Numeric Value in No. Of Leaves");
                    return "false";
                }
            }
           if (Integer.parseInt(chqFrom) < 0) {
                this.setMessage("Chq. No. From Can't Be Less than Zero ");
                return "false";
            }
            if (Integer.parseInt(chqTo) < 0) {
                this.setMessage("Chq.No. To Can't Be Less than Zero ");
                return "false";
            }
            if (Integer.parseInt(noOfLeaves) < 0) {
                this.setMessage("No. Of Leaves Can't Be Less than Zero ");
                return "false";
            }
            return "true";
        }catch(Exception ex){
            this.setMessage(ex.getMessage());
        }
        return "true";
    }
            
   
    
    public void btnRefresh(){
        this.setMessage("");
            
            setChqFrom("");
            setChqTo("");
            setNoOfLeaves("");
            setRemarks("");
            setAmtRangeFrom("");
            setAmtRangeTo(null);
            setPrintlot("");
            setCmbInstType("0");
            this.comboAmtFrm=null;
            this.comboAmtTo=null;
            this.comboCode=null;
            
            
            comboCode = new ArrayList<SelectItem>();
            comboAmtFrm = new ArrayList<SelectItem>();
            comboAmtTo = new ArrayList<SelectItem>();
            issueTable = new ArrayList<TableChBook>();
            stocktable = new ArrayList<IssueCheqbook>();
            //setStAccountNo("");
           
            this.setInvtClass("--Select--");
            this.setInvtType("--Select--");
            this.setCheqSeries("--Select--");
            flagDisable = true;
     }
    
    
     public String btnExit() {
        try {
            btnRefresh();
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
        return "case1";
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

    public String getCheqSeries() {
        return cheqSeries;
    }

    public void setCheqSeries(String cheqSeries) {
        this.cheqSeries = cheqSeries;
    }

    public List<SelectItem> getChequeSeriesList() {
        return chequeSeriesList;
    }

    public void setChequeSeriesList(List<SelectItem> chequeSeriesList) {
        this.chequeSeriesList = chequeSeriesList;
    }

    public String getChqFrom() {
        return chqFrom;
    }

    public void setChqFrom(String chqFrom) {
        this.chqFrom = chqFrom;
    }
    
    public String getChqTo() {
        return chqTo;
    }

    public void setChqTo(String chqTo) {
        this.chqTo = chqTo;
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

    public String getCmbGoto() {
        return cmbGoto;
    }

    public void setCmbGoto(String cmbGoto) {
        this.cmbGoto = cmbGoto;
    }

    public String getCmbInstType() {
        return cmbInstType;
    }

    public void setCmbInstType(String cmbInstType) {
        this.cmbInstType = cmbInstType;
    }

    public String getCombCode() {
        return combCode;
    }

    public void setCombCode(String combCode) {
        this.combCode = combCode;
    }

    public ArrayList<SelectItem> getCombNoLeaves() {
        return combNoLeaves;
    }

    public void setCombNoLeaves(ArrayList<SelectItem> combNoLeaves) {
        this.combNoLeaves = combNoLeaves;
    }

    public ArrayList<SelectItem> getComboAmtFrm() {
        return comboAmtFrm;
    }

    public void setComboAmtFrm(ArrayList<SelectItem> comboAmtFrm) {
        this.comboAmtFrm = comboAmtFrm;
    }

    public ArrayList<SelectItem> getComboAmtTo() {
        return comboAmtTo;
    }

    public void setComboAmtTo(ArrayList<SelectItem> comboAmtTo) {
        this.comboAmtTo = comboAmtTo;
    }

    public ArrayList<SelectItem> getComboCode() {
        return comboCode;
    }

    public void setComboCode(ArrayList<SelectItem> comboCode) {
        this.comboCode = comboCode;
    }

    public ArrayList<SelectItem> getComboGoto() {
        return comboGoto;
    }

    public void setComboGoto(ArrayList<SelectItem> comboGoto) {
        this.comboGoto = comboGoto;
    }

    public ArrayList<SelectItem> getComboinstType() {
        return comboinstType;
    }

    public void setComboinstType(ArrayList<SelectItem> comboinstType) {
        this.comboinstType = comboinstType;
    }

    public IssueCheqbook getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(IssueCheqbook currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public String getInvtClass() {
        return invtClass;
    }

    public void setInvtClass(String invtClass) {
        this.invtClass = invtClass;
    }

    public List<SelectItem> getInvtClassList() {
        return invtClassList;
    }

    public void setInvtClassList(List<SelectItem> invtClassList) {
        this.invtClassList = invtClassList;
    }

    public String getInvtType() {
        return invtType;
    }

    public void setInvtType(String invtType) {
        this.invtType = invtType;
    }

    public List<SelectItem> getInvtTypeList() {
        return invtTypeList;
    }

    public void setInvtTypeList(List<SelectItem> invtTypeList) {
        this.invtTypeList = invtTypeList;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public FtsPostingMgmtFacadeRemote getFts() {
        return fts;
    }

    public void setFts(FtsPostingMgmtFacadeRemote fts) {
        this.fts = fts;
    }

    public String getFtsPostingMgmtFacadeJndiName() {
        return FtsPostingMgmtFacadeJndiName;
    }

    public String getJndiHomeName() {
        return jndiHomeName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNoOfLeaves() {
        return noOfLeaves;
    }

    public void setNoOfLeaves(String noOfLeaves) {
        this.noOfLeaves = noOfLeaves;
    }

    public List<TableChBook> getIssueTable() {
        return issueTable;
    }

    public void setIssueTable(List<TableChBook> issueTable) {
        this.issueTable = issueTable;
    }

    public List<IssueCheqbook> getStocktable() {
        return stocktable;
    }

    public void setStocktable(List<IssueCheqbook> stocktable) {
        this.stocktable = stocktable;
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

    public boolean isCheckLeaves() {
        return checkLeaves;
    }

    public void setCheckLeaves(boolean checkLeaves) {
        this.checkLeaves = checkLeaves;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlagDisable() {
        return flagDisable;
    }

    public void setFlagDisable(boolean flagDisable) {
        this.flagDisable = flagDisable;
    }

    public boolean isSeriesDisable() {
        return seriesDisable;
    }

    public void setSeriesDisable(boolean seriesDisable) {
        this.seriesDisable = seriesDisable;
    }

    public boolean isValid() {
        return Valid;
    }

    public void setValid(boolean Valid) {
        this.Valid = Valid;
    }

    public boolean isDisableSlabCode() {
        return disableSlabCode;
    }

    public void setDisableSlabCode(boolean disableSlabCode) {
        this.disableSlabCode = disableSlabCode;
    }

    public InventorySplitAndMergeFacadeRemote getBeanRemote() {
        return beanRemote;
    }

    public void setBeanRemote(InventorySplitAndMergeFacadeRemote beanRemote) {
        this.beanRemote = beanRemote;
    }

    public BillCommissionFacadeRemote getBillCommFacde() {
        return billCommFacde;
    }

    public void setBillCommFacde(BillCommissionFacadeRemote billCommFacde) {
        this.billCommFacde = billCommFacde;
    }

    public InitialContext getCtx() {
        return ctx;
    }

    public void setCtx(InitialContext ctx) {
        this.ctx = ctx;
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

    public boolean isNoFromDisable() {
        return noFromDisable;
    }

    public void setNoFromDisable(boolean noFromDisable) {
        this.noFromDisable = noFromDisable;
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

    public String getPrintlot() {
        return printlot;
    }

    public void setPrintlot(String printlot) {
        this.printlot = printlot;
    }

    public ArrayList<SelectItem> getCombprintlot() {
        return combprintlot;
    }

    public void setCombprintlot(ArrayList<SelectItem> combprintlot) {
        this.combprintlot = combprintlot;
    }
  }
