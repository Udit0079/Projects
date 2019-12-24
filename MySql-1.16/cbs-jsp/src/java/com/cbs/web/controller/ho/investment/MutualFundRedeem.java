package com.cbs.web.controller.ho.investment;

import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.ho.investment.InvestmentMgmtFacadeRemote;
import com.cbs.facade.ho.share.ShareTransferFacadeRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.pojo.InvestmentFDRInterestPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;

public class MutualFundRedeem extends BaseBean{
  private String message;
  private String function;
  private String mBank;
  private String focusId;
  private String inputDisFlag = "none";
  private String pendingSeqNo;
  private String amt;
  private String redeemAmt;
  private String acNoLen;
  private String crAcNo;
  private String crAcNewNo;
  private String crAcDesc;
  private String drAcNo;
  private String drAcNewNo;
  private String drAcDesc;
  private String remark;
  private String btnValue = "Save";
  private List<SelectItem> functionList;
  private List<SelectItem> mBankList;
  private List<SelectItem> pendingSeqNoList;
  private boolean disAmt;
  private boolean disCrAcNo;
  private boolean disDrAcNo;
  private boolean disRemark;
  private boolean deleteDisable;
  private boolean saveDisable;
  private boolean disFunction;
  private boolean disGrid;
  private boolean disPartialAmt;
  private boolean disPartialRedAmt;
  private InvestmentFDRInterestPojo currentItem;
  private List<InvestmentFDRInterestPojo> tableList;
  private ShareTransferFacadeRemote shareRemoteObject = null;
  private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
  private MiscReportFacadeRemote beanRemote = null;
  private InvestmentMgmtFacadeRemote invFacadeRemote = null;
  private String genDate;
  private String enterBy;
  private String partialAmt;
  private String partialRedeemAmt;
  private String totalRedeemedPartialAmt;
  private String totalRemainingAmt;
  private Double fillerPartialAmt = Double.valueOf(0.0D);
  NumberFormat formatter = new DecimalFormat("#.00");
  private double am = 0.0D;
  private double ra = 0.0D;
  private double amm = 0.0D;
  private double raa = 0.0D;
  
  
  
  public MutualFundRedeem(){
    try{
      this.shareRemoteObject = ((ShareTransferFacadeRemote)ServiceLocator.getInstance().lookup("ShareTransferFacade"));
      this.ftsPostRemote = ((FtsPostingMgmtFacadeRemote)ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade"));
      this.beanRemote = ((MiscReportFacadeRemote)ServiceLocator.getInstance().lookup("MiscReportFacade"));
      this.invFacadeRemote = ((InvestmentMgmtFacadeRemote)ServiceLocator.getInstance().lookup("InvestmentMgmtFacade"));
      setAcNoLen(getAcNoLength());
      String levelId = this.shareRemoteObject.getLevelId(getUserName(), getOrgnBrCode());
      this.functionList = new ArrayList();
      this.functionList.add(new SelectItem("", "-Select-"));
      this.functionList.add(new SelectItem("1", "Redeem"));
      if ((levelId.equals("1")) || (levelId.equals("2"))) {
        this.functionList.add(new SelectItem("3", "Verify"));
      }
      setDeleteDisable(true);
      setDisPartialAmt(true);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public void getBankList(){
    try
    {
      this.mBankList = new ArrayList();
      if (this.function.equalsIgnoreCase("1"))
      {
        List list = this.invFacadeRemote.getRedeemBankList(getFunction());
        for (int i = 0; i < list.size(); i++)
        {
          Vector ele = (Vector)list.get(i);
          this.mBankList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
        }
      }
      else if (this.function.equalsIgnoreCase("3"))
      {
        setDisGrid(true);
        List list = this.invFacadeRemote.getRedeemBankList(getFunction());
        for (int i = 0; i < list.size(); i++)
        {
          Vector ele = (Vector)list.get(i);
          this.mBankList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
        }
      }
      setDisFunction(true);
    }
    catch (Exception e){
      setMessage(e.getMessage());
    }
  }
  
  public void addOrVerifyOperation(){
    try{
      setMessage("");
      setAmt("");
      setBtnValue("Save");
      setSaveDisable(false);
      setRedeemAmt("");
      setDisAmt(false);
      setCrAcNo("");
      setCrAcNewNo("");
      setCrAcDesc("");
      setDisCrAcNo(false);
      setDrAcNo("");
      setDrAcNewNo("");
      setDrAcDesc("");
      setDisDrAcNo(false);
      setRemark("");
      setDisRemark(false);
      setDeleteDisable(true);
      setDisGrid(false);
      if (this.function.equals("")){
        setMessage("Please select Mutual Fund Bank.");
        return;
      }
      if (this.function.equals("1")){
        setInputDisFlag("none");
        getActiveCtrlToRedeem();
        setBtnValue("Save");
        setFocusId("txtAmt");
        setSaveDisable(false);
        setDisAmt(false);
        setDisCrAcNo(false);
        setDisDrAcNo(false);
        setDisRemark(false);
        setDisPartialRedAmt(false);
      }
      else if ((this.function.equals("3")) && 
        (this.mBank != null) && (!this.mBank.equalsIgnoreCase(""))){
        setInputDisFlag("");
        getUnAuthSeqNo();
        setBtnValue("Verify");
        setFocusId("ddPAcNo");
        setSaveDisable(false);
        setDisAmt(true);
        setDisCrAcNo(true);
        setDisDrAcNo(true);
        setDisRemark(true);
        setDeleteDisable(false);
        setDisPartialRedAmt(true);
      }
    }
    catch (Exception e){
      setMessage(e.getMessage());
    }
  }
  
  public void getPlHead(){
    try{
      String qOpt = "";
      if (Double.parseDouble(this.partialAmt) <= Double.parseDouble(this.partialRedeemAmt)) {
        qOpt = "PL_MUTUAL_HEAD";
      } else {
        qOpt = "LOSS_MUTUAL_HEAD";
      }
      String plHead = getOrgnBrCode() + this.ftsPostRemote.getAcnoByPurpose(qOpt);
      setCrAcNo(plHead);
      getNewCrAcNo();
      setDisCrAcNo(true);
    }
    catch (Exception e)
    {
      setMessage(e.getMessage());
    }
  }
  
  public void getUnAuthSeqNo(){
    try
    {
      this.pendingSeqNoList = new ArrayList();
      List statusList = new ArrayList();
      statusList.add("A");
      statusList.add("R");
      statusList.add("P");
      statusList.add("L");
      
      List list = this.invFacadeRemote.getUnAuthSeqList(statusList, this.mBank);
      if (!list.isEmpty()) {
        for (int i = 0; i < list.size(); i++)
        {
          Vector ele = (Vector)list.get(i);
          this.pendingSeqNoList.add(new SelectItem(ele.get(0).toString()));
        }
      }
    }
    catch (Exception e)
    {
      setMessage(e.getMessage());
    }
  }
  
  public void getUnAuthSeqNoDetail(){
    setDisGrid(true);
    try
    {
      this.tableList = new ArrayList();
      List<InvestmentFDRInterestPojo> list = this.invFacadeRemote.getSeqNoToVerifyDtl(getmBank(), Integer.parseInt(this.pendingSeqNo));
      if (list.isEmpty())
      {
        setMessage("Data does not exist");
      }
      else
      {
        BigDecimal aTot = new BigDecimal("0");BigDecimal pTot = new BigDecimal("0");BigDecimal trTot = new BigDecimal("0");BigDecimal prTot = new BigDecimal("0");
        for (int i = 0; i < list.size(); i++)
        {
          InvestmentFDRInterestPojo actCtrlPojo = new InvestmentFDRInterestPojo();
          actCtrlPojo.setsNo(Integer.toString(i));
          actCtrlPojo.setCtrlNo(((InvestmentFDRInterestPojo)list.get(i)).getCtrlNo());
          actCtrlPojo.setFaceValue(((InvestmentFDRInterestPojo)list.get(i)).getFaceValue());
          actCtrlPojo.setPurchaseDt(((InvestmentFDRInterestPojo)list.get(i)).getPurchaseDt());
          actCtrlPojo.setFdrNo(((InvestmentFDRInterestPojo)list.get(i)).getFdrNo());
          actCtrlPojo.setEnterBy(((InvestmentFDRInterestPojo)list.get(i)).getEnterBy());
          actCtrlPojo.setDrHead(((InvestmentFDRInterestPojo)list.get(i)).getDrHead());
          actCtrlPojo.setCrHead(((InvestmentFDRInterestPojo)list.get(i)).getCrHead());
          actCtrlPojo.setLastIntPaidDt(((InvestmentFDRInterestPojo)list.get(i)).getLastIntPaidDt());
          actCtrlPojo.setBookValue(((InvestmentFDRInterestPojo)list.get(i)).getBookValue());
          actCtrlPojo.setSecDesc(((InvestmentFDRInterestPojo)list.get(i)).getSecDesc());
          actCtrlPojo.setIntOpt(((InvestmentFDRInterestPojo)list.get(i)).getIntOpt());
          actCtrlPojo.setPartialAmt(((InvestmentFDRInterestPojo)list.get(i)).getPartialAmt());
          actCtrlPojo.setPartialRedeemAmt(((InvestmentFDRInterestPojo)list.get(i)).getPartialRedeemAmt());
          actCtrlPojo.setTotalRedeemedPartialAmt(((InvestmentFDRInterestPojo)list.get(i)).getTotalRedeemedPartialAmt());
          actCtrlPojo.setTotalRemainingAmt(((InvestmentFDRInterestPojo)list.get(i)).getTotalRemainingAmt());
          setGenDate(((InvestmentFDRInterestPojo)list.get(i)).getLastIntPaidDt());
          setEnterBy(((InvestmentFDRInterestPojo)list.get(i)).getEnterBy());
          setCrAcNo(((InvestmentFDRInterestPojo)list.get(i)).getCrHead());
          getNewCrAcNo();
          setDrAcNo(((InvestmentFDRInterestPojo)list.get(i)).getDrHead());
          getNewDrAcNo();
          setRemark(((InvestmentFDRInterestPojo)list.get(i)).getSecDesc());
          this.tableList.add(actCtrlPojo);
          aTot = aTot.add(((InvestmentFDRInterestPojo)list.get(i)).getFaceValue());
          pTot = pTot.add(BigDecimal.valueOf(((InvestmentFDRInterestPojo)list.get(i)).getPartialAmt().doubleValue()));
          trTot = trTot.add(BigDecimal.valueOf(((InvestmentFDRInterestPojo)list.get(i)).getTotalRemainingAmt().doubleValue()));
          prTot = prTot.add(BigDecimal.valueOf(((InvestmentFDRInterestPojo)list.get(i)).getPartialRedeemAmt().doubleValue()));
        }
        setAmt(aTot.toString());
        setPartialAmt(pTot.toString());
        setPartialRedeemAmt(prTot.toString());
        setTotalRedeemedPartialAmt(prTot.toString());
        setTotalRemainingAmt(trTot.toString());
        setDisAmt(true);
        setDisCrAcNo(true);
        setDisDrAcNo(true);
        setDisRemark(true);
      }
    }
    catch (Exception e){
      setMessage(e.getMessage());
    }
  }
  
  public void getActiveCtrlToRedeem(){
    setDisGrid(false);
    try{
      this.tableList = new ArrayList();
      List<InvestmentFDRInterestPojo> list = this.invFacadeRemote.getCtrlToRedeem(getmBank());
      if (list.isEmpty()) {
        setMessage("Data does not exist");
      } else {
        for (int i = 0; i < list.size(); i++){
          InvestmentFDRInterestPojo actCtrlPojo = new InvestmentFDRInterestPojo();
          actCtrlPojo.setsNo(Integer.toString(i));
          actCtrlPojo.setCtrlNo(((InvestmentFDRInterestPojo)list.get(i)).getCtrlNo());
          actCtrlPojo.setFaceValue(((InvestmentFDRInterestPojo)list.get(i)).getFaceValue());
          actCtrlPojo.setPurchaseDt(((InvestmentFDRInterestPojo)list.get(i)).getPurchaseDt());
          actCtrlPojo.setFdrNo(((InvestmentFDRInterestPojo)list.get(i)).getFdrNo());
          actCtrlPojo.setEnterBy(((InvestmentFDRInterestPojo)list.get(i)).getEnterBy());
          actCtrlPojo.setDrHead(((InvestmentFDRInterestPojo)list.get(i)).getDrHead());
          actCtrlPojo.setCrHead(((InvestmentFDRInterestPojo)list.get(i)).getCrHead());
          actCtrlPojo.setIntOpt(((InvestmentFDRInterestPojo)list.get(i)).getIntOpt());
          actCtrlPojo.setTotalRemainingAmt(((InvestmentFDRInterestPojo)list.get(i)).getTotalRemainingAmt());
          actCtrlPojo.setPartialAmt(((InvestmentFDRInterestPojo)list.get(i)).getPartialAmt());
          actCtrlPojo.setPartialRedeemAmt(((InvestmentFDRInterestPojo)list.get(i)).getPartialRedeemAmt());
          actCtrlPojo.setTotalRedeemedPartialAmt(((InvestmentFDRInterestPojo)list.get(i)).getTotalRedeemedPartialAmt());
          this.tableList.add(actCtrlPojo);
        }
      }
    }
    catch (Exception e){
      setMessage(e.getMessage());
    }
  }
  
  public String btnExit(){
    refreshButtonAction();
    return "case1";
  }
  
  public void refreshButtonAction(){
    this.tableList = new ArrayList();
    this.mBankList = new ArrayList();
    setMessage("");
    setFunction("");
    setInputDisFlag("none");
    setBtnValue("Save");
    setSaveDisable(false);
    setAmt("");
    setPartialAmt("");
    setTotalRemainingAmt("");
    setTotalRedeemedPartialAmt("");
    setRedeemAmt("");
    setPartialRedeemAmt("");
    setDisAmt(false);
    setCrAcNo("");
    setCrAcNewNo("");
    setCrAcDesc("");
    setDisCrAcNo(false);
    setDrAcNo("");
    setDrAcNewNo("");
    setDrAcDesc("");
    setDisDrAcNo(false);
    setRemark("");
    setDisRemark(false);
    setDeleteDisable(true);
    setDisFunction(false);
    setDisGrid(false);
    this.raa = 0.0D;
    this.amm = 0.0D;
    this.am = 0.0D;
    this.ra = 0.0D;
  }

  public void setTableDataToForm(){
    try
    {
      setMessage("");
      
      double pa = 0.0D;
      if ((this.amt == null) || (this.amt.equalsIgnoreCase(""))) {
        this.amt = "0.00";
      }
      if ((this.currentItem.getPartialAmtInput() == null) || (this.currentItem.getPartialAmtInput().equals(""))) {
        this.currentItem.setPartialAmtInput(Double.valueOf(0.0D));
      }
      if ((this.totalRemainingAmt == null) || (this.totalRemainingAmt.equalsIgnoreCase(""))) {
        this.totalRemainingAmt = "0.0";
      }
      if (this.currentItem.getPartialAmtInput().doubleValue() > this.currentItem.getTotalRemainingAmt().doubleValue()){
        setMessage("Partial Input Amount can't greater than Remaining Amount");
        return;
      }
      if ((Double.valueOf(this.amt).doubleValue() == 0.0D) && (Double.valueOf(this.totalRemainingAmt).doubleValue() == 0.0D)){
        for (int j = 0; j < this.tableList.size(); j++){
          this.am += Double.valueOf(((InvestmentFDRInterestPojo)this.tableList.get(j)).getFaceValue().toString()).doubleValue();
          this.ra += Double.valueOf(((InvestmentFDRInterestPojo)this.tableList.get(j)).getTotalRemainingAmt().toString()).doubleValue();
        }
        setAmt(String.valueOf(this.formatter.format(this.am)));
        setTotalRemainingAmt(String.valueOf(this.formatter.format(this.ra)));
        this.raa = this.am;
        this.amm = this.ra;
      }
      else{
        setAmt(String.valueOf(this.formatter.format(this.raa)));
        setTotalRemainingAmt(String.valueOf(this.formatter.format(this.amm)));
        this.am = 0.0D;
        this.ra = 0.0D;
      }
      for (int i = 0; i < this.tableList.size(); i++){
        pa += ((InvestmentFDRInterestPojo)this.tableList.get(i)).getPartialAmtInput().doubleValue();
        setPartialAmt(String.valueOf(this.formatter.format(pa)));
      }
    }
    catch (Exception e){
      setMessage(e.getMessage());
    }
  }
  
  public void getNewCrAcNo(){
    try{
      if ((this.crAcNo.equalsIgnoreCase("")) || (this.crAcNo == null) || (this.crAcNo.equalsIgnoreCase("null"))){
        setMessage("Please Enter Proper Credit Account No.");
        return;
      }
      if ((!this.crAcNo.equalsIgnoreCase("")) && (this.crAcNo.length() != 12) && (this.crAcNo.length() != Integer.parseInt(getAcNoLen()))){
        setMessage("Please Fill Proper Credit Account Number.");
        return;
      }
      this.crAcNewNo = this.ftsPostRemote.getNewAccountNumber(this.crAcNo);
      this.crAcDesc = this.beanRemote.checkAcno(this.crAcNewNo);
    }
    catch (Exception e)
    {
      setMessage(e.getMessage());
    }
  }
  
  public void getNewDrAcNo(){
    try{
      setMessage("");
      if ((this.drAcNo.equalsIgnoreCase("")) || (this.drAcNo == null) || (this.drAcNo.equalsIgnoreCase("null"))){
        setMessage("Please Enter Proper Debit Account No.");
        return;
      }
      if ((!this.drAcNo.equalsIgnoreCase("")) && (this.drAcNo.length() != 12) && (this.drAcNo.length() != Integer.parseInt(getAcNoLen()))){
        setMessage("Please Fill Proper Debit Account Number.");
        return;
      }
      this.drAcNewNo = this.ftsPostRemote.getNewAccountNumber(this.drAcNo);
      this.drAcDesc = this.beanRemote.checkAcno(this.drAcNewNo);
    }
    catch (Exception e){
      setMessage(e.getMessage());
    }
  }
  
  public void saveAction(){
    try {
      if ((this.function == null) || (this.function.equalsIgnoreCase(""))){
        setMessage("Please Select Function");
        return;
      }
      if ((this.amt.equalsIgnoreCase("")) || (Double.parseDouble(this.amt) == 0.0D)){
        setMessage("Please Fill Proper Amount");
        return;
      }
      if ((this.partialRedeemAmt.equalsIgnoreCase("")) || (Double.parseDouble(this.partialRedeemAmt) == 0.0D)) {
        setMessage("Please Fill Proper Partial Redeem Amount");
        return;
      }
      if ((Double.valueOf(this.partialAmt).doubleValue() > Double.valueOf(this.totalRemainingAmt).doubleValue()) && (this.function.equals("1"))) {
        setMessage(" Partial Amount can't be greater than Remaining Amount !!");
        return;
      }
      if ((this.crAcNewNo.equalsIgnoreCase("")) || (this.crAcNewNo == null) || (this.crAcNewNo.equalsIgnoreCase("null"))){
        setMessage("Please Enter Proper Credit Account No.");
        return;
      }
      if ((this.drAcNewNo.equalsIgnoreCase("")) || (this.drAcNewNo == null) || (this.drAcNewNo.equalsIgnoreCase("null"))){
        setMessage("Please Enter Proper Debit Account No.");
        return;
      }
      if (!this.crAcNewNo.substring(0, 2).equalsIgnoreCase(getOrgnBrCode())){
        setMessage("Please Enter Credit Account No. of your branch");
        return;
      }
      if (!this.drAcNewNo.substring(0, 2).equalsIgnoreCase(getOrgnBrCode())){
        setMessage("Please Enter Debit Account No. of your branch");
        return;
      }
      if ((this.function.equalsIgnoreCase("1")) && (this.btnValue.equalsIgnoreCase("Save"))){
        String msg = this.invFacadeRemote.saveMFundRedeemDetail(this.tableList, this.drAcNewNo, this.crAcNewNo, Double.parseDouble(this.partialAmt), Double.parseDouble(this.partialRedeemAmt), getUserName(), this.remark, Double.valueOf(this.totalRemainingAmt).doubleValue());
        if (msg.substring(0, 4).equalsIgnoreCase("true"))
        {
          refreshButtonAction();
          String msg1 = "Data has been Saved successfully and Generated Seq. No is " + msg.substring(4);
          setMessage(msg1);
        }
      }
      else if ((this.function.equalsIgnoreCase("3")) && (this.btnValue.equalsIgnoreCase("Verify"))) {
        if (this.enterBy.equalsIgnoreCase(getUserName())){
          setMessage("You Can't Authorize Your Own Entry.");
          return;
        }
        String msg = this.invFacadeRemote.verifyMutualFundRedeemDetail(this.tableList, Integer.parseInt(this.pendingSeqNo), this.drAcNewNo, this.crAcNewNo, this.mBank, this.genDate, getUserName(), this.enterBy, this.remark, getOrgnBrCode(), Double.valueOf(this.partialAmt), Double.valueOf(this.partialRedeemAmt), Double.valueOf(this.totalRedeemedPartialAmt), Double.valueOf(this.totalRemainingAmt));
        if (msg.substring(0, 4).equalsIgnoreCase("true")){
          refreshButtonAction();
          String msg1 = "Data has been Verified successfully and Generated " + msg.substring(4);
          setMessage(msg1);
        }
      }
    }
    catch (Exception e){
      setMessage(e.getMessage());
    }
  }
  
     public void deleteAction(){
      try{
        if (this.function.equalsIgnoreCase("")){
          setMessage("Please Select Function");
          return;
        }
        if ((this.pendingSeqNo == null) || (this.pendingSeqNo.equalsIgnoreCase("")) || (this.pendingSeqNo.equalsIgnoreCase("null"))){
          setMessage("Please Select Pending Sequence No.");
          return;
        }
        if ((this.amt.equalsIgnoreCase("")) || (Double.parseDouble(this.amt) == 0.0D)){
          setMessage("Please Fill Proper Amount");
          return;
        }
        if ((this.crAcNewNo.equalsIgnoreCase("")) || (this.crAcNewNo == null) || (this.crAcNewNo.equalsIgnoreCase("null"))){
          setMessage("Please Enter Proper Credit Account No.");
          return;
        }
        if ((this.drAcNewNo.equalsIgnoreCase("")) || (this.drAcNewNo == null) || (this.drAcNewNo.equalsIgnoreCase("null"))){
          setMessage("Please Enter Proper Debit Account No.");
          return;
        }
        String msg = this.invFacadeRemote.deleteMutualFundDetail(Integer.parseInt(this.pendingSeqNo), getUserName());
        if (msg.substring(0, 4).equalsIgnoreCase("true")){
          refreshButtonAction();
          String msg1 = "Data has been Deleted successfully";
          setMessage(msg1);
        }
    }
    catch (Exception e){
      setMessage(e.getMessage());
    }
  }
  
  public void getRedeemAlert(){
    if ((this.partialRedeemAmt.equalsIgnoreCase("")) || (Double.parseDouble(this.partialRedeemAmt) == 0.0D)){
      setMessage("Please Fill Proper Redeem Amount");
      return;
    }
    getPlHead();
  }

          public String getMessage(){
            return this.message;
          }

          public Double getFillerPartialAmt(){
            return this.fillerPartialAmt;
          }

          public void setFillerPartialAmt(Double fillerPartialAmt){
            this.fillerPartialAmt = fillerPartialAmt;
          }

          public void setMessage(String message){
            this.message = message;
          }

          public String getFunction(){
            return this.function;
          }

          public void setFunction(String function){
            this.function = function;
          }

          public String getmBank() {
            return this.mBank;
          }

          public void setmBank(String mBank){
            this.mBank = mBank;
          }

          public String getFocusId(){
            return this.focusId;
          }

          public void setFocusId(String focusId){
            this.focusId = focusId;
          }

          public String getInputDisFlag(){
            return this.inputDisFlag;
          }

          public void setInputDisFlag(String inputDisFlag){
            this.inputDisFlag = inputDisFlag;
          }

          public String getPendingSeqNo() {
            return this.pendingSeqNo;
          }

          public void setPendingSeqNo(String pendingSeqNo){
            this.pendingSeqNo = pendingSeqNo;
          }

          public String getAmt(){
            return this.amt;
          }

          public void setAmt(String amt){
            this.amt = amt;
          }

          public String getRedeemAmt(){
            return this.redeemAmt;
          }

          public void setRedeemAmt(String redeemAmt){
            this.redeemAmt = redeemAmt;
          }

          public String getAcNoLen(){
            return this.acNoLen;
          }

          public void setAcNoLen(String acNoLen){
            this.acNoLen = acNoLen;
          }

          public String getCrAcNo(){
            return this.crAcNo;
          }

          public void setCrAcNo(String crAcNo){
            this.crAcNo = crAcNo;
          }

          public String getCrAcNewNo(){
            return this.crAcNewNo;
          }

          public void setCrAcNewNo(String crAcNewNo){
            this.crAcNewNo = crAcNewNo;
          }

          public String getCrAcDesc(){
            return this.crAcDesc;
          }

          public void setCrAcDesc(String crAcDesc){
            this.crAcDesc = crAcDesc;
          }

          public String getDrAcNo(){
            return this.drAcNo;
          }

          public void setDrAcNo(String drAcNo){
            this.drAcNo = drAcNo;
          }

          public String getDrAcNewNo(){
            return this.drAcNewNo;
          }

          public void setDrAcNewNo(String drAcNewNo){
            this.drAcNewNo = drAcNewNo;
          }

          public String getDrAcDesc(){
            return this.drAcDesc;
          }

          public void setDrAcDesc(String drAcDesc){
            this.drAcDesc = drAcDesc;
          }

          public String getRemark(){
            return this.remark;
          }

          public void setRemark(String remark){
            this.remark = remark;
          }

          public String getBtnValue(){
            return this.btnValue;
          }

          public void setBtnValue(String btnValue){
            this.btnValue = btnValue;
          }

          public List<SelectItem> getFunctionList(){
            return this.functionList;
          }

          public void setFunctionList(List<SelectItem> functionList){
            this.functionList = functionList;
          }

          public List<SelectItem> getmBankList(){
            return this.mBankList;
          }

          public void setmBankList(List<SelectItem> mBankList) {
            this.mBankList = mBankList;
          }

          public List<SelectItem> getPendingSeqNoList(){
            return this.pendingSeqNoList;
          }

          public void setPendingSeqNoList(List<SelectItem> pendingSeqNoList){
            this.pendingSeqNoList = pendingSeqNoList;
          }

          public boolean isDisAmt(){
            return this.disAmt;
          }

          public void setDisAmt(boolean disAmt){
            this.disAmt = disAmt;
          }

          public boolean isDisCrAcNo(){
            return this.disCrAcNo;
          }

          public void setDisCrAcNo(boolean disCrAcNo){
            this.disCrAcNo = disCrAcNo;
          }

          public boolean isDisDrAcNo(){
            return this.disDrAcNo;
          }

          public void setDisDrAcNo(boolean disDrAcNo){
            this.disDrAcNo = disDrAcNo;
          }

          public boolean isDisRemark() {
            return this.disRemark;
          }

          public void setDisRemark(boolean disRemark){
            this.disRemark = disRemark;
          }

          public boolean isDeleteDisable(){
            return this.deleteDisable;
          }

          public void setDeleteDisable(boolean deleteDisable){
            this.deleteDisable = deleteDisable;
          }

          public boolean isSaveDisable(){
            return this.saveDisable;
          }

          public void setSaveDisable(boolean saveDisable){
            this.saveDisable = saveDisable;
          }

          public boolean isDisFunction(){
            return this.disFunction;
          }

          public void setDisFunction(boolean disFunction){
            this.disFunction = disFunction;
          }

          public boolean isDisGrid(){
            return this.disGrid;
          }

          public void setDisGrid(boolean disGrid){
            this.disGrid = disGrid;
          }

          public InvestmentFDRInterestPojo getCurrentItem(){
            return this.currentItem;
          }

          public void setCurrentItem(InvestmentFDRInterestPojo currentItem) {
            this.currentItem = currentItem;
          }

          public List<InvestmentFDRInterestPojo> getTableList(){
            return this.tableList;
          }

          public void setTableList(List<InvestmentFDRInterestPojo> tableList){
            this.tableList = tableList;
          }

          public String getGenDate(){
            return this.genDate;
          }

          public void setGenDate(String genDate){
            this.genDate = genDate;
          }

          public String getEnterBy(){
            return this.enterBy;
          }

          public void setEnterBy(String enterBy){
            this.enterBy = enterBy;
          }

          public String getPartialAmt(){
            return this.partialAmt;
          }

          public void setPartialAmt(String partialAmt){
            this.partialAmt = partialAmt;
          }

          public String getPartialRedeemAmt(){
            return this.partialRedeemAmt;
          }

          public void setPartialRedeemAmt(String partialRedeemAmt) {
            this.partialRedeemAmt = partialRedeemAmt;
          }

          public String getTotalRedeemedPartialAmt(){
            return this.totalRedeemedPartialAmt;
          }

          public void setTotalRedeemedPartialAmt(String totalRedeemedPartialAmt){
            this.totalRedeemedPartialAmt = totalRedeemedPartialAmt;
          }

          public String getTotalRemainingAmt(){
            return this.totalRemainingAmt;
          }

          public boolean isDisPartialAmt(){
            return this.disPartialAmt;
          }

          public void setDisPartialAmt(boolean disPartialAmt){
            this.disPartialAmt = disPartialAmt;
          }

          public void setTotalRemainingAmt(String totalRemainingAmt){
            this.totalRemainingAmt = totalRemainingAmt;
          }  

    public boolean isDisPartialRedAmt() {
        return disPartialRedAmt;
    }

    public void setDisPartialRedAmt(boolean disPartialRedAmt) {
        this.disPartialRedAmt = disPartialRedAmt;
    }
          

}


