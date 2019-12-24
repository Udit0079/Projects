package com.cbs.web.controller.dds;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.dds.DDSManagementFacadeRemote;
import com.cbs.utils.CbsUtil;

import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Administrator
 */
public class InterestCalculation extends BaseBean{

    private String datetoday;
    private String msg;
    private String oldAcno, acNoLen;
    private String newAcno;
    private String name;
    private Date acopenDate;
    private Date matDt;
    private Date frDate;
    private Date toDate;
    private String peanalty;
    private String balance;
    private String interest;
    private String totInt;
    private String interestamt;
    private String actTotInt;
    private String balTds;
    private String totTds;
    private String finTds;
    private String closeActTdsToBeDeducted;
    private String closeActTdsDeducted;
    private String closeActIntFinYear;
    private String totalamt;
    private boolean calculateflag;
    private boolean postflag;
    DDSManagementFacadeRemote beanRemote;
    FtsPostingMgmtFacadeRemote ftsBeanRemote;
    Date date = new Date();
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    
    public String getDatetoday() {
        return datetoday;
    }

    public void setDatetoday(String datetoday) {
        this.datetoday = datetoday;
    }    
    
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public String getOldAcno() {
        return oldAcno;
    }

    public void setOldAcno(String oldAcno) {
        this.oldAcno = oldAcno;
    }
    
    public String getNewAcno() {
        return newAcno;
    }

    public void setNewAcno(String newAcno) {
        this.newAcno = newAcno;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Date getAcopenDate() {
        return acopenDate;
    }

    public void setAcopenDate(Date acopenDate) {
        this.acopenDate = acopenDate;
    }
     
    public Date getMatDt() {
        return matDt;
    }

    public void setMatDt(Date matDt) {
        this.matDt = matDt;
    }
    
    public Date getFrDate() {
        return frDate;
    }

    public void setFrDate(Date frDate) {
        this.frDate = frDate;
    }
    
    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
    
    public String getPeanalty() {
        return peanalty;
    }

    public void setPeanalty(String peanalty) {
        this.peanalty = peanalty;
    }
    
    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
    
    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }
    
    public String getTotInt() {
        return totInt;
    }

    public void setTotInt(String totInt) {
        this.totInt = totInt;
    }
    
    public String getInterestamt() {
        return interestamt;
    }

    public void setInterestamt(String interestamt) {
        this.interestamt = interestamt;
    }
    
    public String getActTotInt() {
        return actTotInt;
    }

    public void setActTotInt(String actTotInt) {
        this.actTotInt = actTotInt;
    }
    
    public String getBalTds() {
        return balTds;
    }

    public void setBalTds(String balTds) {
        this.balTds = balTds;
    }
    
    public String getTotTds() {
        return totTds;
    }

    public void setTotTds(String totTds) {
        this.totTds = totTds;
    }
    
    public String getFinTds() {
        return finTds;
    }

    public void setFinTds(String finTds) {
        this.finTds = finTds;
    }
    
    public String getCloseActTdsToBeDeducted() {
        return closeActTdsToBeDeducted;
    }

    public void setCloseActTdsToBeDeducted(String closeActTdsToBeDeducted) {
        this.closeActTdsToBeDeducted = closeActTdsToBeDeducted;
    }

    public String getCloseActTdsDeducted() {
        return closeActTdsDeducted;
    }

    public void setCloseActTdsDeducted(String closeActTdsDeducted) {
        this.closeActTdsDeducted = closeActTdsDeducted;
    }

    public String getCloseActIntFinYear() {
        return closeActIntFinYear;
    }

    public void setCloseActIntFinYear(String closeActIntFinYear) {
        this.closeActIntFinYear = closeActIntFinYear;
    }
    
    public String getTotalamt() {
        return totalamt;
    }

    public void setTotalamt(String totalamt) {
        this.totalamt = totalamt;
    }

    public boolean isCalculateflag() {
        return calculateflag;
    }

    public void setCalculateflag(boolean calculateflag) {
        this.calculateflag = calculateflag;
    }

    public boolean isPostflag() {
        return postflag;
    }

    public void setPostflag(boolean postflag) {
        this.postflag = postflag;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }
    
    public InterestCalculation() {
        try {
            ftsBeanRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            beanRemote = (DDSManagementFacadeRemote) ServiceLocator.getInstance().lookup("DDSManagementFacade");
            this.setAcNoLen(getAcNoLength());
            setDatetoday(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
            calculateflag = true;
            postflag = true;
        } catch (ApplicationException e) {
            setMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }
    
    public void onblurAcno() {
        try {
            msg = "";
            newAcno = "";
            //if (oldAcno.length() != 12) {
            if ((this.oldAcno.length() != 12) && (this.oldAcno.length() != (Integer.parseInt(this.getAcNoLen())))) {
                msg = "Account number should be Proper";
                return;
            }
            String newNo = ftsBeanRemote.getNewAccountNumber(oldAcno);
            if (newNo.startsWith("A/c")) {
                msg = newNo;
                return;
            } else {
                newAcno = newNo;
            }
            List result = beanRemote.getCustomerDetails(newAcno,getOrgnBrCode());
            if (result.size() > 0) {
                Vector vtr = (Vector) result.get(0);
                name = vtr.get(1).toString();
                calculateflag = false;
                postflag = true;
                String acopDt = vtr.get(2).toString();
                String matDate = vtr.get(3).toString();
                String postDt = vtr.get(4).toString();
                acopenDate = ymd.parse(acopDt);
                
                boolean a = ymd.parse(matDate).after(ymd.parse(ymd.format(date)));
                if(a){
                    toDate =  ymd.parse(ymd.format(date));
                    matDt = ymd.parse(matDate);
                }else{
                    toDate =  ymd.parse(matDate);  
                    matDt = ymd.parse(matDate);
                }
                
                boolean b = ymd.parse(postDt).after(ymd.parse(acopDt));
                if(b){
                    frDate =  ymd.parse(postDt);                    
                }else{
                    frDate =  ymd.parse(acopDt);
                }               

                balance = beanRemote.getBalance(newAcno);
            } else {
                msg = "Account No does not exist.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            setMsg(e.getLocalizedMessage());
        }
    }
    
   public void btnCalculate() {
        try {
            NumberFormat nft = new DecimalFormat("0.00");
            msg = validation();
            if (msg.equalsIgnoreCase("Ok")) {
                if (peanalty == null || peanalty.equalsIgnoreCase("")) {
                    peanalty = "0";
                }
                List<Double> result = beanRemote.interestCalcultaion(newAcno, toDate, acopenDate, Double.parseDouble(peanalty));
                interest = nft.format(result.get(0));
                balance = nft.format(result.get(1));
                totInt = nft.format(result.get(5));
                interestamt = nft.format(result.get(6));
                
                balTds = nft.format(result.get(4));
                totTds = nft.format(result.get(3));
                finTds = nft.format(result.get(2));
                
                closeActTdsToBeDeducted = nft.format(result.get(7));
                closeActTdsDeducted = nft.format(result.get(8));
                closeActIntFinYear = nft.format(result.get(9));

                actTotInt = nft.format(result.get(5) + result.get(6));
                //totalamt = nft.format((result.get(1) + result.get(5) + result.get(6)) - (result.get(4) + result.get(3) + result.get(7)));
                totalamt = nft.format((result.get(1) + result.get(6)) - (result.get(4) + result.get(7)));
                        
                postflag = false;
                msg="";
            }else{
                setMsg(msg);
            }
        } catch (Exception e) {
            setMsg(e.getMessage());
        }
    } 
    
    public void btnPost() {
        try {
            if (validation().equalsIgnoreCase("Ok")) {
                String postResult = beanRemote.postInterest(newAcno, name, getUserName(), Double.parseDouble(interestamt), Double.parseDouble(balTds), Double.parseDouble(closeActTdsToBeDeducted), getOrgnBrCode(), ymd.format(frDate), ymd.format(toDate));
                btnRefresh();
                msg = postResult;
            }
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }
    
    public void btnRefresh() {
        try {
            
            msg = "";
            oldAcno = "";
            newAcno = "";
            name = "";
            setAcopenDate(null);
            setMatDt(null);
            setFrDate(null);
            setToDate(null);
            peanalty = "";
            setBalance("");
            interest = "";
            setTotInt("");
            interestamt = "";
            setActTotInt("");
            setBalTds("");
            setTotTds("");
            setFinTds("");
            setCloseActTdsToBeDeducted("");
            setCloseActTdsDeducted("");
            setCloseActIntFinYear("");
            totalamt = "";
            postflag = true;
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }
    
    public String btnExit() {
        try {
            btnRefresh();
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
        return "case1";
    }
    
   public String validation() {
        try {
            if (newAcno == null || newAcno.length() != 12) {
                return "Account No Does Not Exist";
            }
            if (toDate == null) {
                return "Enter the To Date";
            }
            if(CbsUtil.dayDiff( ymd.parse(ymd.format(date)),toDate) > 0){
                return "Please check the to date";
            }
            if(CbsUtil.dayDiff(frDate, toDate) < 0){
                return "Please check the dates entered";
            }
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
        return "Ok";
    }          
}