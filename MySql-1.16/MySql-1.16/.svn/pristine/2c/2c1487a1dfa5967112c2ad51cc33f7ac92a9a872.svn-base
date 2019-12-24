/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.investment;

import com.cbs.entity.ho.investment.InvestmentCallHead;
import com.cbs.entity.ho.investment.InvestmentCallMaster;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.ho.investment.InvestmentMgmtFacadeRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.investment.CallMoneyPojo;
import com.cbs.web.pojo.investment.ControlNoComparator;
import com.cbs.web.pojo.investment.SnoAscComparator;
import com.cbs.web.pojo.investment.SnoComparator;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.faces.model.SelectItem;

/**
 *
 * @author root
 */
public class CallMoneyEntry extends BaseBean {
    private String message;
    private String dealDate;
    private String roi;
    private String amount;
    private String noOfdays;
    private String compDt;        
    private String intAmount;              
    private String details;       
    private String function;
    private String processBtnCaption;
    private String confirmationMsg;
    private String gridOperation;
    private List<SelectItem> functionList;
    private MiscReportFacadeRemote beanRemote = null;
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    private boolean visibleSave;
    private boolean selectImage;
    private CallMoneyPojo currentItem;
    private List<CallMoneyPojo> gridDetail;
    private String matAmount;
    private InvestmentMgmtFacadeRemote remoteObj = null;
    private final String jndiHomeName = "InvestmentMgmtFacade";
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    NumberFormat formatter = new DecimalFormat("#.00");
    Date dt = new Date();
    private String glheadAcno;
    private String drAcNewNo;
    private String drAcDesc;
    private boolean disableGlHead = false;

    public String getFunction() {
        return function;
    }

    public String getGridOperation() {
        return gridOperation;
    }

    public void setGridOperation(String gridOperation) {
        this.gridOperation = gridOperation;
    }

    public String getDrAcNewNo() {
        return drAcNewNo;
    }

    public void setDrAcNewNo(String drAcNewNo) {
        this.drAcNewNo = drAcNewNo;
    }

    public String getDrAcDesc() {
        return drAcDesc;
    }

    public void setDrAcDesc(String drAcDesc) {
        this.drAcDesc = drAcDesc;
    }

//    public String getAcNoLen() {
//        return acNoLen;
//    }
//
//    public void setAcNoLen(String acNoLen) {
//        this.acNoLen = acNoLen;
//    }

    public void setFunction(String function) {
        this.function = function;
    }

    public List<SelectItem> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<SelectItem> functionList) {
        this.functionList = functionList;
    }

    public List<CallMoneyPojo> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<CallMoneyPojo> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public CallMoneyPojo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(CallMoneyPojo currentItem) {
        this.currentItem = currentItem;
    }

    public boolean isVisibleSave() {
        return visibleSave;
    }

    public void setVisibleSave(boolean visibleSave) {
        this.visibleSave = visibleSave;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCompDt() {
        return compDt;
    }

    public void setCompDt(String compDt) {
        this.compDt = compDt;
    }

    public String getNoOfdays() {
        return noOfdays;
    }

    public void setNoOfdays(String noOfdays) {
        this.noOfdays = noOfdays;
    }

    public String getDealDate() {
        return dealDate;
    }

    public void setDealDate(String dealDate) {
        this.dealDate = dealDate;
    }

    public String getDetails() {
        return details;
    }

    public String getConfirmationMsg() {
        return confirmationMsg;
    }

    public void setConfirmationMsg(String confirmationMsg) {
        this.confirmationMsg = confirmationMsg;
    }

    public String getProcessBtnCaption() {
        return processBtnCaption;
    }

    public void setProcessBtnCaption(String processBtnCaption) {
        this.processBtnCaption = processBtnCaption;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getIntAmount() {
        return intAmount;
    }

    public void setIntAmount(String intAmount) {
        this.intAmount = intAmount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRoi() {
        return roi;
    }

    public void setRoi(String roi) {
        this.roi = roi;
    }

    public boolean isSelectImage() {
        return selectImage;
    }

    public void setSelectImage(boolean selectImage) {
        this.selectImage = selectImage;
    }

    public String getMatAmount() {
        return matAmount;
    }

    public void setMatAmount(String matAmount) {
        this.matAmount = matAmount;
    }

    public String getGlheadAcno() {
        return glheadAcno;
    }

    public void setGlheadAcno(String glheadAcno) {
        this.glheadAcno = glheadAcno;
    }

    public boolean isDisableGlHead() {
        return disableGlHead;
    }

    public void setDisableGlHead(boolean disableGlHead) {
        this.disableGlHead = disableGlHead;
    }
   
    public CallMoneyEntry() {
        gridDetail = new ArrayList<CallMoneyPojo>();
        try {
            remoteObj = (InvestmentMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            beanRemote = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            addFunctionList();
            resetForm();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void addFunctionList() {
        functionList = new ArrayList<SelectItem>();
        functionList.add(new SelectItem("A", "ADD"));
        functionList.add(new SelectItem("M", "MODIFY"));
    }

    public void setProcessButtonCaption() {
        this.visibleSave = true;
        if (this.function.equals("A")) {
            this.processBtnCaption = "Save";
            
        } else if (this.function.equals("M")) {
            this.processBtnCaption = "Modify";
        }
    }

    public void onChangeFunction() {
        this.setMessage("");
        resetFormField();
        setProcessButtonCaption();
        gridDetail = new ArrayList<CallMoneyPojo>();
        if (this.function.equals("M")) {
            this.selectImage = true;
            this.setGridOperation("Select");
            getTableData();
        } else if (this.function.equals("A")) {
            this.selectImage = false;
            this.setGridOperation("");
            this.setMessage("Fill all the required fields to add an entry !");
        }
    }

    public void populateMessage() {
        this.setConfirmationMsg("");
        if (this.function.equals("A")) {
            this.setConfirmationMsg("Are you sure to save this entry!");
            return;
        } else if (this.function.equals("M")) {
            this.setConfirmationMsg("Are you sure to modify this entry!");
            return;
        }
    }

    public void processAction() {
        this.setMessage("");
        if (this.function.equals("A")) {
            saveProcess();
        } else if (this.function.equals("M")) {
            updateProcess();
        }
    }

    public void onBlurDealDate() {
        this.setMessage("");
        if (this.dealDate == null || this.dealDate.equals("")) {
            this.setMessage("Please fill deal date !");
            return;
        }

        if (this.dealDate.length() < 10) {
            this.setMessage("Please fill proper deal date !");
            return;
        }

        boolean result = new Validator().validateDate_dd_mm_yyyy(this.dealDate);
        if (result == false) {
            this.setMessage("Please fill proper deal date !");
            return;
        }
        try {
            if (dmy.parse(this.dealDate).compareTo(ymd.parse(ymd.format(dt))) < 0) {
                this.setMessage("Deal Date can not be less than ToDays Date !");
                return;
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void onBlurRoi() {
        this.setMessage("");
        if (this.roi == null | this.roi.equals("") || this.roi.equals("0.0") || this.roi.equals("0")) {
            this.setMessage("Please fill roi !");
            return;
        }
    }

    public void onBlurAmount() {
        this.setMessage("");
        if (this.amount == null | this.amount.equals("") || this.amount.equals("0.0") || this.amount.equals("0")) {
            this.setMessage("Please fill amount !");
            return;
        }
    }

    public void onBlurNoOfDays() {
        this.setMessage("");
        if (this.noOfdays == null || this.noOfdays.equals("") || this.noOfdays.equals("0.0")) {
            this.setMessage("Please fill No Of Days !");
            return;
        }
        try {
            String strDt = CbsUtil.dateAdd(ymd.format(dmy.parse(this.dealDate)), Integer.parseInt(this.noOfdays));
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.US);
            String weekDay = sdf.format(ymd.parse(strDt));
            if (weekDay.equalsIgnoreCase("Sunday")) {
                this.setMessage("The Completion Date is on Sunday, Please Check the Date !");
                return;
            }            
            this.setCompDt(dmy.format(ymd.parse(strDt)));

            String chL = chkLeapYear(Integer.parseInt(this.dealDate.substring(6,10)));    
            if(chL.equalsIgnoreCase("true")){
                this.setIntAmount(formatter.format((Double.parseDouble(this.amount) * Double.parseDouble(this.roi) * Double.parseDouble(this.noOfdays)) / 36600));
            }else{
                this.setIntAmount(formatter.format((Double.parseDouble(this.amount) * Double.parseDouble(this.roi) * Double.parseDouble(this.noOfdays)) / 36500));
            }
            this.setMatAmount(formatter.format(Double.parseDouble(this.amount) + Double.parseDouble(this.getIntAmount())));
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
      
    public void onBlurDetails(){
        this.setMessage("");
        if (this.details == null | this.details.equals("") || this.details.equals("0.0") || this.details.equals("0")) {
            this.setMessage("Please fill details !");
            return;
        }
        try{
          
              InvestmentCallHead entityGl = remoteObj.getInvestCallHeadByCode("1");
              this.glheadAcno = entityGl.getCrGlhead();
        }catch(Exception ex){
            this.setMessage(ex.getMessage());
        }
    }
    
     
    
    public void onBlurGlHead() { 
      try {
          if (this.function.equals("A")) {
            //gridDetail = new ArrayList<CallMoneyPojo>();
            
//              
//                if (!this.glheadAcno.equalsIgnoreCase("") || !this.glheadAcno == null || this.glheadAcno.equalsIgnoreCase("null")) {
//                this.setMessage("Please Enter Proper Credit Account No.");
//                return;
//            }
//            if (this.glheadAcno.equalsIgnoreCase("") || (this.glheadAcno.length() != 12)) {
//                this.setMessage("Please Fill Proper Credit Account Number.");
//                return;
//            }
            this.setMessage("If You Want Modifity GlHead You Can");
              
              
            
            drAcNewNo = ftsPostRemote.getNewAccountNumber(glheadAcno);
            drAcDesc  = beanRemote.checkAcno(drAcNewNo);
         
            createTableObj();
//            if(!gridDetail.isEmpty()){
//                disableGlHead = true; 
//            }else{
//                 disableGlHead = false;
//            }
            this.visibleSave = false;
            resetFormField();
            this.setMessage("Now you can save the entry !");
        } else if (this.function.equals("M")) {     
                if (this.glheadAcno.equalsIgnoreCase("") || this.glheadAcno == null || this.glheadAcno.equalsIgnoreCase("null")) {
                this.setMessage("Please Enter Proper Credit Account No.");
                return;
            }
            if (this.glheadAcno.equalsIgnoreCase("") || (this.glheadAcno.length() != 12)) {
                this.setMessage("Please Fill Proper Credit Account Number.");
                return;
            }
            this.setMessage("");
            drAcNewNo = ftsPostRemote.getNewAccountNumber(this.glheadAcno);
           
            drAcDesc  = beanRemote.checkAcno(drAcNewNo);
           
            createTableObj();
            this.visibleSave = false;
            resetFormField();
            this.setMessage("Now you can update the entry !");
        }

       } catch (Exception e) {
            setMessage(e.getMessage());
            drAcDesc="";
            drAcNewNo="";
        }

    }

    public void createTableObj() {
       
        try {
            if (this.function.equals("A")) {
                 CallMoneyPojo pojo = new CallMoneyPojo();
                if (gridDetail.isEmpty()) {
                    pojo.setSno(1);
                    pojo.setCtrlNo(remoteObj.getMaxCtrlNo());
                } else {
                    Collections.sort(gridDetail, new SnoComparator());
                    CallMoneyPojo snoPojo = gridDetail.get(0);
                    pojo.setSno(snoPojo.getSno() + 1);

                    Collections.sort(gridDetail, new ControlNoComparator());
                    CallMoneyPojo ctrlPojo = gridDetail.get(0);
                    pojo.setCtrlNo(ctrlPojo.getCtrlNo() + 1);
                }
                pojo.setDealDt(this.dealDate);
                pojo.setCompDt(this.compDt);
                pojo.setRoi(Double.parseDouble(this.roi));
                pojo.setAmount(formatter.format(Double.parseDouble(this.amount)));
                pojo.setNoOfDays(Integer.parseInt(this.noOfdays));
                pojo.setAuth("N");
                pojo.setAuthBy("");
                pojo.setEnterBy(getUserName());
                pojo.setLasUpdateBy("");
                //pojo.setLastUpdateDt(dt);
                pojo.setDetails(this.details);
                pojo.setTranTime(dt);
                pojo.setStatus("Pending");
                pojo.setIntAmount(Double.parseDouble(this.intAmount));
                pojo.setGlheadAcno(glheadAcno);

                gridDetail.add(pojo);

                Collections.sort(gridDetail, new SnoAscComparator());
                
            } else if (this.function.equals("M")) {
                 gridDetail = new ArrayList<CallMoneyPojo>();
                 CallMoneyPojo pojo = new CallMoneyPojo();
                                pojo.setSno(1);
                pojo.setCtrlNo(currentItem.getCtrlNo());
                pojo.setDealDt(this.dealDate);
                pojo.setCompDt(this.compDt);
                pojo.setRoi(Double.parseDouble(this.roi));
                pojo.setAmount(formatter.format(Double.parseDouble(this.amount)));
                pojo.setNoOfDays(Integer.parseInt(this.noOfdays));
                pojo.setAuth(currentItem.getAuth());
                pojo.setAuthBy(currentItem.getAuthBy());
                pojo.setEnterBy(currentItem.getEnterBy());
                pojo.setLasUpdateBy(getUserName());
                pojo.setDetails(this.details);
                pojo.setTranTime(currentItem.getTranTime());
                pojo.setStatus(currentItem.getStatus());
                pojo.setIntAmount(Double.parseDouble(this.intAmount));
                pojo.setGlheadAcno(this.glheadAcno);

                gridDetail.add(pojo);
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void setTableData() {
        try{
        this.setMessage("Now you can update this record !");
        if (currentItem != null) {
            this.setDealDate(currentItem.getDealDt());
            this.setRoi(Double.toString(currentItem.getRoi()));
            this.setAmount(formatter.format(Double.parseDouble(currentItem.getAmount())));
            this.setNoOfdays(Integer.toString(currentItem.getNoOfDays()));
            this.setCompDt(currentItem.getCompDt());
            this.setIntAmount(Double.toString(currentItem.getIntAmount()));
            this.setDetails(currentItem.getDetails());
            this.setMatAmount(String.valueOf(Double.valueOf(currentItem.getAmount())+currentItem.getIntAmount()));
            this.setGlheadAcno(currentItem.getGlheadAcno());
            this.disableGlHead=false;
            this.setVisibleSave(false);
            drAcNewNo = ftsPostRemote.getNewAccountNumber(currentItem.getGlheadAcno());
            drAcDesc  = beanRemote.checkAcno(drAcNewNo);
        }
        }catch(Exception e){
            this.setMessage(e.getMessage());
        }
    }

    public void saveProcess() {
        this.setMessage("");
        String msg = "";
        try {
            if (!gridDetail.isEmpty()) {
                for (int i = 0; i < gridDetail.size(); i++) {
                    InvestmentCallMaster entity = new InvestmentCallMaster();
                    CallMoneyPojo pojo = gridDetail.get(i);
                    entity.setCtrlNo(((Integer) pojo.getCtrlNo()).longValue());
                    entity.setDealDt(dmy.parse(pojo.getDealDt()));
                    entity.setCompletionDt(dmy.parse(pojo.getCompDt()));
                    entity.setRoi(pojo.getRoi());
                    entity.setAmt(Double.parseDouble(pojo.getAmount()));
                    entity.setNoOfDays(pojo.getNoOfDays());                                              
                    entity.setAuth("N");
                    entity.setAuthBy("");
                    entity.setEnterBy(getUserName());
                    entity.setLastUpdateBy("");
                    //entity.setLastUpdateDt("");
                    entity.setDetails(pojo.getDetails());
                    entity.setTranTime(dt);
                    entity.setStatus("Pending");
                    entity.setIntAmt(pojo.getIntAmount());
                    entity.setDrGlHead(pojo.getGlheadAcno());

                    msg = remoteObj.saveInvestmentCallMaster(entity);
                    if (!msg.equalsIgnoreCase("true")) {
                        this.setMessage("Problem In Control No :" + entity.getCtrlNo());
                        return;
                    }
                }
                if (msg.equalsIgnoreCase("true")) {
                    resetForm();
                    this.setMessage("Data has been saved successfully !");
                    return;
                }
                
               
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void updateProcess() {
        this.setMessage("");
        try {
            if (!gridDetail.isEmpty()) {
                InvestmentCallMaster entity = new InvestmentCallMaster();
                
                CallMoneyPojo pojo = gridDetail.get(0);
                entity.setCtrlNo(((Integer) pojo.getCtrlNo()).longValue());
                entity.setDealDt(dmy.parse(pojo.getDealDt()));
                entity.setCompletionDt(dmy.parse(pojo.getCompDt()));
                entity.setRoi(pojo.getRoi());
                entity.setAmt(Double.parseDouble(pojo.getAmount()));
                entity.setNoOfDays(pojo.getNoOfDays());
                entity.setAuth(pojo.getAuth());
                entity.setAuthBy(pojo.getAuthBy());
                entity.setEnterBy(pojo.getEnterBy());
                entity.setLastUpdateBy(pojo.getLasUpdateBy());
                entity.setLastUpdateDt(dt);
                entity.setDetails(pojo.getDetails());
                entity.setTranTime(pojo.getTranTime());
                entity.setStatus(pojo.getStatus());
                entity.setIntAmt(pojo.getIntAmount());
                entity.setDrGlHead(pojo.getGlheadAcno());

                String msg = remoteObj.updateInvestmentCallMaster(entity);
                if (msg.equalsIgnoreCase("true")) {
                    resetForm();
                    this.setMessage("Data has been updated successfully !");
                    return;
                }
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public boolean validate() {
        this.setMessage("");
        try {
            if (this.dealDate == null || this.dealDate.equals("")) {
                this.setMessage("Please fill deal date !");
                return false;
            }

            if (this.dealDate.length() < 10) {
                this.setMessage("Please fill proper deal date !");
                return false;
            }

            boolean result = new Validator().validateDate_dd_mm_yyyy(this.dealDate);
            if (result == false) {
                this.setMessage("Please fill proper deal date !");
                return false;
            }

            if (dmy.parse(this.dealDate).compareTo(ymd.parse(ymd.format(dt))) < 0) {
                this.setMessage("Deal Date can not be less than ToDays Date !");
                return false;
            }

            if (this.roi == null | this.roi.equals("") || this.roi.equals("0.0") || this.roi.equals("0")) {
                this.setMessage("Please fill roi !");
                return false;
            }

            if (this.amount == null | this.amount.equals("") || this.amount.equals("0.0") || this.amount.equals("0")) {
                this.setMessage("Please fill amount !");
                return false;
            }

            if (this.noOfdays == null || this.noOfdays.equals("") || this.noOfdays.equals("0.0")) {
                this.setMessage("Please fill proper No Of Days !");
                return false;
            }
            
            
            if(this.glheadAcno == null || this.glheadAcno.equalsIgnoreCase("") || this.glheadAcno.equals("0.00") || this.glheadAcno.equals("0")){
                this.setMessage("Please fill valid glhead");
            }
            
            if(!(glheadAcno.length()== 12)){
                this.setMessage("Please fill Valid glhead");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
        return true;
    }

    public void resetFormField() {
        //this.function = "A";
        this.setDealDate(dmy.format(dt));
        this.setRoi("");
        this.setAmount("");
        this.setNoOfdays("");
        this.setCompDt("");
        this.setIntAmount("");
        this.setDetails("");
        this.setMatAmount("");
       
    }

    public void resetForm() {
        this.setMessage("");
        resetFormField();
        this.function = "A";
        this.confirmationMsg = "";
        this.visibleSave = false;
        this.gridOperation = "";
        this.selectImage = false;
        this.setDrAcNewNo("");
        this.setGlheadAcno("");
        this.setDrAcDesc("");
        this.processBtnCaption = "Save";
        this.setMessage("Please select function for add or modify !");
        gridDetail = new ArrayList<CallMoneyPojo>();
    }

    public void getTableData() {
        gridDetail = new ArrayList<CallMoneyPojo>();
        try {
            List<InvestmentCallMaster> entityList = remoteObj.getUnAuhorizeCallMaster();
            if (!entityList.isEmpty()) {
                for (int j = 0; j < entityList.size(); j++) {
                    InvestmentCallMaster entity = entityList.get(j);
                    CallMoneyPojo pojo = new CallMoneyPojo();
                    pojo.setSno(j + 1);
                    pojo.setCtrlNo(entity.getCtrlNo().intValue());
                    pojo.setDealDt(dmy.format(entity.getDealDt()));
                    pojo.setCompDt(dmy.format(entity.getCompletionDt()));
                    pojo.setRoi(entity.getRoi());
                    pojo.setAmount(formatter.format(entity.getAmt()));
                    pojo.setNoOfDays(entity.getNoOfDays());
                    pojo.setAuth(entity.getAuth());
                    pojo.setAuthBy(entity.getAuthBy());
                    pojo.setEnterBy(entity.getEnterBy());
                    pojo.setLasUpdateBy(entity.getLastUpdateBy());
                    pojo.setLastUpdateDt(entity.getLastUpdateDt());
                    pojo.setDetails(entity.getDetails());
                    pojo.setTranTime(entity.getTranTime());
                    pojo.setStatus(entity.getStatus());
                    pojo.setIntAmount(entity.getIntAmt());
                    pojo.setGlheadAcno(entity.getDrGlHead());
                    gridDetail.add(pojo);
                }
                this.setMessage("Select a row from table to modify !");
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public String exitBtnAction() {
        try {
            resetForm();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
        return "case1";
    }

    public String chkLeapYear(int chYr) {
        String msg = "";
        if ((chYr % 400 == 0) || ((chYr % 100) != 0 && (chYr % 4 == 0))) {
            msg = "true";
        } else {
            msg = "false";
        }
        return msg;
    }
    
    public void intAmountOnBlur(){
        this.setMatAmount(formatter.format(Double.parseDouble(this.amount)+Double.parseDouble(this.intAmount)));
    }
}
