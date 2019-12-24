package com.cbs.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class InvestmentFDRInterestPojo
{
  private Integer ctrlNo;
  private String lastIntPaidDt;
  private String intOpt;
  private BigDecimal totAmtIntRec;
  private String purchaseDt;
  private String matDt;
  private BigDecimal faceValue;
  private Double roi;
  private String sellerName;
  private String secDesc;
  private BigDecimal bookValue;
  private String fdrNo;
  private String crHead;
  private String drHead;
  private String enterBy;
  private String authBy;
  private Date trantime;
  private BigDecimal amtIntRec;
  private String sNo;
  private boolean selectChk;
  private String secType;
  private String branch1;
  private String branch2;
  private String days;
  private String months;
  private String years;
  private String crHeadName;
  private String drHeadName;
  private Double partialAmt;
  private Double partialRedeemAmt;
  private Double totalRedeemedPartialAmt;
  private Double totalRemainingAmt;
  private Double partialAmtInput = Double.valueOf(0.0D);
  
  public Double getPartialAmtInput()
  {
    return this.partialAmtInput;
  }
  
  public void setPartialAmtInput(Double partialAmtInput)
  {
    this.partialAmtInput = partialAmtInput;
  }
  
  public String getAuthBy()
  {
    return this.authBy;
  }
  
  public void setAuthBy(String authBy)
  {
    this.authBy = authBy;
  }
  
  public String getCrHead()
  {
    return this.crHead;
  }
  
  public void setCrHead(String crHead)
  {
    this.crHead = crHead;
  }
  
  public Integer getCtrlNo()
  {
    return this.ctrlNo;
  }
  
  public void setCtrlNo(Integer ctrlNo)
  {
    this.ctrlNo = ctrlNo;
  }
  
  public String getDrHead()
  {
    return this.drHead;
  }
  
  public void setDrHead(String drHead)
  {
    this.drHead = drHead;
  }
  
  public String getEnterBy()
  {
    return this.enterBy;
  }
  
  public void setEnterBy(String enterBy)
  {
    this.enterBy = enterBy;
  }
  
  public String getFdrNo()
  {
    return this.fdrNo;
  }
  
  public void setFdrNo(String fdrNo)
  {
    this.fdrNo = fdrNo;
  }
  
  public String getIntOpt()
  {
    return this.intOpt;
  }
  
  public void setIntOpt(String intOpt)
  {
    this.intOpt = intOpt;
  }
  
  public String getLastIntPaidDt()
  {
    return this.lastIntPaidDt;
  }
  
  public void setLastIntPaidDt(String lastIntPaidDt)
  {
    this.lastIntPaidDt = lastIntPaidDt;
  }
  
  public String getMatDt()
  {
    return this.matDt;
  }
  
  public void setMatDt(String matDt)
  {
    this.matDt = matDt;
  }
  
  public String getPurchaseDt()
  {
    return this.purchaseDt;
  }
  
  public void setPurchaseDt(String purchaseDt)
  {
    this.purchaseDt = purchaseDt;
  }
  
  public Double getRoi()
  {
    return this.roi;
  }
  
  public void setRoi(Double roi)
  {
    this.roi = roi;
  }
  
  public String getSecDesc()
  {
    return this.secDesc;
  }
  
  public void setSecDesc(String secDesc)
  {
    this.secDesc = secDesc;
  }
  
  public String getSellerName()
  {
    return this.sellerName;
  }
  
  public void setSellerName(String sellerName)
  {
    this.sellerName = sellerName;
  }
  
  public Date getTrantime()
  {
    return this.trantime;
  }
  
  public void setTrantime(Date trantime)
  {
    this.trantime = trantime;
  }
  
  public BigDecimal getBookValue()
  {
    return this.bookValue;
  }
  
  public void setBookValue(BigDecimal bookValue)
  {
    this.bookValue = bookValue;
  }
  
  public BigDecimal getFaceValue()
  {
    return this.faceValue;
  }
  
  public void setFaceValue(BigDecimal faceValue)
  {
    this.faceValue = faceValue;
  }
  
  public BigDecimal getTotAmtIntRec()
  {
    return this.totAmtIntRec;
  }
  
  public void setTotAmtIntRec(BigDecimal totAmtIntRec)
  {
    this.totAmtIntRec = totAmtIntRec;
  }
  
  public BigDecimal getAmtIntRec()
  {
    return this.amtIntRec;
  }
  
  public void setAmtIntRec(BigDecimal amtIntRec)
  {
    this.amtIntRec = amtIntRec;
  }
  
  public String getsNo()
  {
    return this.sNo;
  }
  
  public void setsNo(String sNo)
  {
    this.sNo = sNo;
  }
  
  public boolean isSelectChk()
  {
    return this.selectChk;
  }
  
  public void setSelectChk(boolean selectChk)
  {
    this.selectChk = selectChk;
  }
  
  public String getSecType()
  {
    return this.secType;
  }
  
  public void setSecType(String secType)
  {
    this.secType = secType;
  }
  
  public String getBranch1()
  {
    return this.branch1;
  }
  
  public void setBranch1(String branch1)
  {
    this.branch1 = branch1;
  }
  
  public String getBranch2()
  {
    return this.branch2;
  }
  
  public void setBranch2(String branch2)
  {
    this.branch2 = branch2;
  }
  
  public String getDays()
  {
    return this.days;
  }
  
  public void setDays(String days)
  {
    this.days = days;
  }
  
  public String getMonths()
  {
    return this.months;
  }
  
  public void setMonths(String months)
  {
    this.months = months;
  }
  
  public String getYears()
  {
    return this.years;
  }
  
  public void setYears(String years)
  {
    this.years = years;
  }
  
  public String getCrHeadName()
  {
    return this.crHeadName;
  }
  
  public void setCrHeadName(String crHeadName)
  {
    this.crHeadName = crHeadName;
  }
  
  public String getDrHeadName()
  {
    return this.drHeadName;
  }
  
  public void setDrHeadName(String drHeadName)
  {
    this.drHeadName = drHeadName;
  }
  
  public Double getPartialAmt()
  {
    return this.partialAmt;
  }
  
  public void setPartialAmt(Double partialAmt)
  {
    this.partialAmt = partialAmt;
  }
  
  public Double getPartialRedeemAmt()
  {
    return this.partialRedeemAmt;
  }
  
  public void setPartialRedeemAmt(Double partialRedeemAmt)
  {
    this.partialRedeemAmt = partialRedeemAmt;
  }
  
  public Double getTotalRedeemedPartialAmt()
  {
    return this.totalRedeemedPartialAmt;
  }
  
  public void setTotalRedeemedPartialAmt(Double totalRedeemedPartialAmt)
  {
    this.totalRedeemedPartialAmt = totalRedeemedPartialAmt;
  }
  
  public Double getTotalRemainingAmt()
  {
    return this.totalRemainingAmt;
  }
  
  public void setTotalRemainingAmt(Double totalRemainingAmt)
  {
    this.totalRemainingAmt = totalRemainingAmt;
  }
}
