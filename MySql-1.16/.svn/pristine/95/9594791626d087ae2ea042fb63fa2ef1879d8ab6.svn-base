/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.controller.payroll;

import com.hrms.web.pojo.SlabMasterGrid;
import com.cbs.web.controller.BaseBean;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.to.HrSalaryStructurePKTO;
import com.hrms.common.to.HrSalaryStructureTO;
import com.hrms.common.to.HrSlabMasterPKTO;
import com.hrms.common.to.HrSlabMasterTO;
import com.hrms.facade.payroll.PayrollOtherMgmFacadeRemote;
import com.hrms.facade.payroll.PayrollTransactionsFacadeRemote;
import com.hrms.utils.HrServiceLocator;
import com.hrms.web.delegate.PayrollMasterManagementDelegate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

public class SlabMaster extends BaseBean {

    private static final Logger logger = Logger.getLogger(SlabMaster.class);
    private String function;
    private String message;
    private String fromDate;
    private String toDate;
    private int compCode;
    private String mode = "SAVE";
    private String slabFromAmount;
    private String slabToAmount;
    private String slabCriteria;
    private String slabCriteriaAmount;
    private boolean disableSave;
    private boolean disableAdd;
    private boolean disableDelete;
    private boolean disableDescription;
    private boolean disableFromAmount;
    private boolean disableToAmount;
    private boolean disableSlabCriteria;
    private boolean disableSlabCriteriaAmt;
    private boolean disablePurposeList;
    private boolean disableNatureList;
    private String purposeList;
    private String natureList;
    private List<SelectItem> natureListBox;
    private List<SelectItem> slabMasterDescList;
    private List<SelectItem> purposeListBox;
    private SlabMasterGrid slabMastercurrentItem;
    private List<SelectItem> slabCriteriaList;
    private List<SelectItem> functionList;
    private String salarySlabId;
    private String slabMaxAmount;
    private String slabMinAmount;
    private String desgId;
    private String baseComponent;
    private String depnComponent;
    private List<SelectItem> salarySlabIdList;
    private List<SelectItem> desgIdList;
    private List<HrSlabMasterTO> slabMasterBatchList;
    private List<HrSlabMasterTO> modifiedSlabMasterBatchList;
    private HrSlabMasterTO slabMasterBatchcurrentItem;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private PayrollTransactionsFacadeRemote payrollRemote;
    private PayrollOtherMgmFacadeRemote payrollOthRemote;
    private boolean disablegrup;
    private List<SelectItem> earnigslabMasterDescList;
    static String maxSlabCodeVar="";
    int count=0;
    private List slabCodeList ; 

    public SlabMaster() {
        try {
            payrollRemote = (PayrollTransactionsFacadeRemote) HrServiceLocator.getInstance().lookup("PayrollTransactionsFacade");
            payrollOthRemote = (PayrollOtherMgmFacadeRemote) HrServiceLocator.getInstance().lookup("PayrollOtherMgmFacade");
            this.setMessage("");
            compCode = Integer.parseInt(getOrgnBrCode());                  
            functionList = new ArrayList<SelectItem>();
            functionList.add(new SelectItem("0", "--SELECT--"));
            functionList.add(new SelectItem("1", "ADD"));
//          functionList.add(new SelectItem("2", "VIEW"));
            functionList.add(new SelectItem("MODIFY", "MODIFY"));
            slabMasterDescList = new ArrayList<SelectItem>();
            slabMasterDescList.add(new SelectItem("0", "--SELECT--"));
            slabCriteriaList = new ArrayList<SelectItem>();
            slabCriteriaList.add(new SelectItem("0", "--SELECT--"));
            slabCriteriaList.add(new SelectItem("PERCENTAGE", "PERCENTAGE"));
            slabCriteriaList.add(new SelectItem("AMOUNT", "AMOUNT"));
            salarySlabIdList = new ArrayList<SelectItem>();
            salarySlabIdList.add(new SelectItem("0", "--SELECT--"));
            List list = payrollOthRemote.getBasicSalarySlabList(getTodayDate());
            for (int i = 0; i < list.size(); i++) {
                Object[] ele = (Object[]) list.get(i);
                salarySlabIdList.add(new SelectItem(ele[0].toString(),ele[1].toString()));
            }
            slabMasterBatchList = new ArrayList<HrSlabMasterTO>();
            modifiedSlabMasterBatchList= new ArrayList<HrSlabMasterTO>();
            getInitalData();
            slabCodeList = new ArrayList();
            
            
        } catch (Exception e) {
            logger.error("Exception occured while executing method SlabMaster()", e);
        }
    }

    public void getInitalData() {
        try {
            purposeListBox = new ArrayList<SelectItem>();
            purposeListBox.add(new SelectItem("0", "--SELECT--"));
            natureListBox = new ArrayList<SelectItem>();
            natureListBox.add(new SelectItem("0", "--SELECT--"));
            PayrollMasterManagementDelegate masterManagementDelegate = new PayrollMasterManagementDelegate();
            List<HrMstStructTO> HrMstStructTOs = masterManagementDelegate.getInitialData(compCode, "PUR%");
            if (!HrMstStructTOs.isEmpty()) {
                for (HrMstStructTO HrMstStructTO : HrMstStructTOs) {
                    purposeListBox.add(new SelectItem(HrMstStructTO.getHrMstStructPKTO().getStructCode(),
                            HrMstStructTO.getDescription()));
                }
            }
            HrMstStructTOs = masterManagementDelegate.getInitialData(compCode, "ALL%");
            if (!HrMstStructTOs.isEmpty()) {
                for (HrMstStructTO HrMstStructTO : HrMstStructTOs) {
                    natureListBox.add(new SelectItem(HrMstStructTO.getHrMstStructPKTO().getStructCode(),
                            HrMstStructTO.getDescription()));
                }
            }

            List list = payrollRemote.getFinYears(getOrgnBrCode());
            for (int i = 0; i < list.size(); i++) {
                Object[] ele = (Object[]) list.get(0);
                this.setFromDate(ele[0].toString().trim());
                this.setToDate(ele[1].toString().trim());
            }
            /*Change from DES to SLA for emp or */
            HrMstStructTOs = masterManagementDelegate.getInitialData(compCode, "SLA%");
            desgIdList = new ArrayList<SelectItem>();
            desgIdList.add(new SelectItem("0", "--SELECT--"));
            if (!HrMstStructTOs.isEmpty()) {
                for (HrMstStructTO HrMstStructTO : HrMstStructTOs) {
                    desgIdList.add(new SelectItem(HrMstStructTO.getHrMstStructPKTO().getStructCode(), HrMstStructTO.getDescription()));
                }
            }
              
            disablegrup = false;
            maxSlabCodeVar = payrollOthRemote.maxSlabCode();
        } catch (Exception e) {
            logger.error("Exception occured while executing method getInitalData()", e);
            this.setMessage(e.getMessage());
        }
    }

    public void showPopupToModify() {
    }

    public void updateModifySlabData() {         
     
        slabMasterBatchcurrentItem.setRangeFrom(Double.valueOf(getSlabFromAmount()));
        slabMasterBatchcurrentItem.setRangeTo(Double.valueOf(getSlabToAmount()));
        slabMasterBatchcurrentItem.setSlabCriteria(getSlabCriteria());
        slabMasterBatchcurrentItem.setSlabCriteriaAmt(Double.valueOf(getSlabCriteriaAmount()));
        slabMasterBatchcurrentItem.setSlabCriteriaMaxAmt(Double.valueOf(getSlabMaxAmount()));  
        slabMasterBatchcurrentItem.setSlabCriteriaMinAmt(Double.valueOf(getSlabMinAmount())); 
           setSlabCriteria("");
           setSlabCriteriaAmount("");
           setSlabFromAmount("");
           setSlabToAmount("");
           setMessage("");
           setSlabMaxAmount("");
           setSlabMinAmount("");
           setBaseComponent("0");
           setDepnComponent("0");
    }

    public void onblurDesignation() {
        try {
            if (function.equalsIgnoreCase("1")) {
                List list = payrollOthRemote.getSlabMasterData(desgId);
                if (!list.isEmpty()) {
                    this.setMessage("Slab already defined for this designation.Please Modify.! ");
                }
            } else if (function.equalsIgnoreCase("MODIFY")) {
                    slabMasterBatchList = payrollOthRemote.getSlabMasterData(desgId);
                     disablegrup = false;
                if (!slabMasterBatchList.isEmpty()) {
                    salarySlabId = slabMasterBatchList.get(0).getHrSlabMasterPK().getSalarySlabId();
                    natureList = slabMasterBatchList.get(0).getHrSlabMasterPK().getNature();
                    disablegrup = true;
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void getDescription() {
        try {
            earnigslabMasterDescList = new ArrayList<SelectItem>();         
            earnigslabMasterDescList.add(new SelectItem("0", "--SELECT--"));
            HrSalaryStructurePKTO hrSalaryStructurePKTOEarn = new HrSalaryStructurePKTO();
            hrSalaryStructurePKTOEarn.setAlDesc("none");
            hrSalaryStructurePKTOEarn.setCompCode(compCode);
            hrSalaryStructurePKTOEarn.setDateFrom(getFromDate());
            hrSalaryStructurePKTOEarn.setDateTo(getToDate());
            hrSalaryStructurePKTOEarn.setNature(natureList);
            hrSalaryStructurePKTOEarn.setPurposeCode("PUR001");
            PayrollMasterManagementDelegate masterManagementDelegate = new PayrollMasterManagementDelegate();
            List<HrSalaryStructureTO> hrSalaryStructureTOsEarn = masterManagementDelegate.getSlabMasterInitialData(hrSalaryStructurePKTOEarn);
            if (!hrSalaryStructureTOsEarn.isEmpty()) {
                for (HrSalaryStructureTO hrSalaryStructureTO : hrSalaryStructureTOsEarn) {
                    earnigslabMasterDescList.add(new SelectItem(hrSalaryStructureTO.getHrSalaryStructurePKTO().getAlDesc()));
                }
            }

            slabMasterDescList = new ArrayList<SelectItem>();
            slabMasterDescList.add(new SelectItem("0", "--SELECT--"));
            HrSalaryStructurePKTO hrSalaryStructurePKTO = new HrSalaryStructurePKTO();
            hrSalaryStructurePKTO.setAlDesc("none");
            hrSalaryStructurePKTO.setCompCode(compCode);
            hrSalaryStructurePKTO.setDateFrom(getFromDate());
            hrSalaryStructurePKTO.setDateTo(getToDate());
            hrSalaryStructurePKTO.setNature(natureList);
            hrSalaryStructurePKTO.setPurposeCode(purposeList);
            List<HrSalaryStructureTO> hrSalaryStructureTOs = masterManagementDelegate.getSlabMasterInitialData(hrSalaryStructurePKTO);
            if (!hrSalaryStructureTOs.isEmpty()) {
                for (HrSalaryStructureTO hrSalaryStructureTO : hrSalaryStructureTOs) {
                    slabMasterDescList.add(new SelectItem(hrSalaryStructureTO.getHrSalaryStructurePKTO().getAlDesc()));
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void onChangeFunction() {
        if (function.equals("0")) {
            setMessage("Please select an function");
        }
    }

    public void slabCriteriaInitialData() {
        try {
            if (function.equals("1") && (natureList.equalsIgnoreCase("ALL001"))) {
                disablegrup = true;
            } else {
                disablegrup = false;
            }
            if (function.equals("2")) {
                if (purposeList.equalsIgnoreCase("0")) {
                    setMessage("Select the purpose field for getting the Slab Details");
                    return;
                }
            }
            getDescription();
        } catch (Exception e) {
            logger.error("Exception occured while executing method slabCriteriaInitialData()", e);
            this.setMessage(e.getMessage());
        }
    }

    public void setRowValuesSlabMasterGrid() {
        setNatureList(slabMastercurrentItem.getNatureCode());
        setSlabCriteria(slabMastercurrentItem.getSlabCriteria());
        setSlabCriteriaAmount(slabMastercurrentItem.getSlabCriteriaAmt());
        setSlabFromAmount(slabMastercurrentItem.getStartRange());
        setSlabToAmount(slabMastercurrentItem.getEndRange());
        slabMasterDescList = new ArrayList<SelectItem>();
        slabMasterDescList.add(new SelectItem(slabMastercurrentItem.getBaseComponent()));
//        setSlabMasterDescription(slabMastercurrentItem.getDescription());
        disablePurposeList = true;
        disableNatureList = true;
        disableDescription = true;
        disableFromAmount = true;
        disableSlabCriteria = true;
        disableSlabCriteriaAmt = true;
        disableToAmount = true;
    }

    public void removeSlabMasterFromGrid() {
        boolean result = slabMasterBatchList.remove(slabMasterBatchcurrentItem);
        setMessage("Item removed Successfully " + result);
    }

    public void setSlabMasterDataToform() {
        if (!function.equalsIgnoreCase("MODIFY")) {
            return;
        }
       mode = "UPDATE";
            this.purposeList = slabMasterBatchcurrentItem.getHrSlabMasterPK().getPurposeCode();
            compCode = slabMasterBatchcurrentItem.getHrSlabMasterPK().getCompCode();
            natureList = slabMasterBatchcurrentItem.getHrSlabMasterPK().getNature();
            slabFromAmount = String.valueOf(slabMasterBatchcurrentItem.getRangeFrom());
            slabToAmount = String.valueOf(slabMasterBatchcurrentItem.getRangeTo());
            slabCriteria = slabMasterBatchcurrentItem.getSlabCriteria();
            slabCriteriaAmount = String.valueOf(slabMasterBatchcurrentItem.getSlabCriteriaAmt());
            slabMaxAmount = String.valueOf(slabMasterBatchcurrentItem.getSlabCriteriaMaxAmt());
            slabMinAmount = String.valueOf(slabMasterBatchcurrentItem.getSlabCriteriaMinAmt());
            salarySlabId = slabMasterBatchcurrentItem.getHrSlabMasterPK().getSalarySlabId();
        // aditya add
         baseComponent=slabMasterBatchcurrentItem.getBaseComponent();
         depnComponent=slabMasterBatchcurrentItem.getDepnComponent();
       getDescription();
       setSlabFromAmount(String.valueOf(slabMasterBatchcurrentItem.getRangeFrom()));
       setSlabToAmount( String.valueOf(slabMasterBatchcurrentItem.getRangeTo()));
       setSlabCriteria(slabMasterBatchcurrentItem.getSlabCriteria());
       setSlabCriteriaAmount(String.valueOf(slabMasterBatchcurrentItem.getSlabCriteriaAmt()));
       setSlabMaxAmount(String.valueOf(slabMasterBatchcurrentItem.getSlabCriteriaMaxAmt()));
       setSlabMinAmount(String.valueOf(slabMasterBatchcurrentItem.getSlabCriteriaMinAmt()));
       setBaseComponent(slabMasterBatchcurrentItem.getBaseComponent());
       setDepnComponent(slabMasterBatchcurrentItem.getDepnComponent());
  
    }

    public void addSlabMasterToGrid() {
      try {

            HrSlabMasterPKTO hrSlabMasterPKTO = new HrSlabMasterPKTO();
            HrSlabMasterTO hrSlabMasterTO = new HrSlabMasterTO();
          if(function.equalsIgnoreCase("1")){
    
            hrSlabMasterPKTO.setPurposeCode(purposeList);
            hrSlabMasterTO.setBaseComponent(baseComponent);
            hrSlabMasterTO.setDepnComponent(depnComponent);
            hrSlabMasterPKTO.setCompCode(compCode);
            hrSlabMasterPKTO.setDateFrom(formatter.parse(getFromDate()));
            hrSlabMasterPKTO.setDateTo(formatter.parse(getToDate()));
            hrSlabMasterPKTO.setNature(natureList);
            hrSlabMasterPKTO.setSlabCode("SLA");
            hrSlabMasterTO.setDesgId(desgId);
            hrSlabMasterTO.setAppFlg('Y');
            hrSlabMasterTO.setAuthBy(getUserName());
            hrSlabMasterTO.setDefaultComp(compCode);
            hrSlabMasterTO.setEnteredBy(getUserName());
            hrSlabMasterTO.setHrSlabMasterPK(hrSlabMasterPKTO);
            hrSlabMasterTO.setModDate(new Date());
            hrSlabMasterTO.setRangeFrom(Double.parseDouble(this.slabFromAmount));
            hrSlabMasterTO.setRangeTo(Double.parseDouble(this.slabToAmount));
            hrSlabMasterTO.setSlabCriteria(slabCriteria);
            hrSlabMasterTO.setSlabCriteriaAmt(Double.parseDouble(slabCriteriaAmount));
            hrSlabMasterTO.setStatFlag("Y");
            hrSlabMasterTO.setStatUpFlag("N");
            hrSlabMasterTO.setSlabCriteriaMaxAmt(slabMaxAmount == null || slabMaxAmount.equalsIgnoreCase("")
                    ? 0.0 : Double.valueOf(slabMaxAmount));
            hrSlabMasterPKTO.setSalarySlabId(salarySlabId);
            hrSlabMasterTO.setSlabCriteriaMinAmt(slabMinAmount == null || slabMinAmount.equalsIgnoreCase("")
                    ? 0.0 : Double.valueOf(slabMinAmount));
            hrSlabMasterTO.setBaseComponent(baseComponent);
            hrSlabMasterTO.setDepnComponent(depnComponent);
            
          }else if(function.equalsIgnoreCase("MODIFY")){
              
              for(int i=0;i<slabMasterBatchList.size();i++){
                  slabCodeList.add(slabMasterBatchList.get(i).getHrSlabMasterPK().getSlabCode());
              }
              
              if(slabMasterBatchcurrentItem!=null){
              if(slabCodeList.contains(slabMasterBatchcurrentItem.getHrSlabMasterPK().getSlabCode())){
                  updateModifySlabData();   
                  return;
              }}else {

            hrSlabMasterPKTO.setPurposeCode(purposeList);
            hrSlabMasterTO.setBaseComponent(baseComponent);
            hrSlabMasterTO.setDepnComponent(depnComponent);
            hrSlabMasterPKTO.setCompCode(compCode);
            hrSlabMasterPKTO.setDateFrom(formatter.parse(getFromDate()));
            hrSlabMasterPKTO.setDateTo(formatter.parse(getToDate()));
            hrSlabMasterPKTO.setNature(natureList);
           if( hrSlabMasterPKTO.getSlabCode()== null && count==0){
               
               hrSlabMasterPKTO.setSlabCode(payrollOthRemote.maxSlabCode());
               count++;
           }else if(hrSlabMasterPKTO.getSlabCode()== null && count>0){
               int num = Integer.parseInt(maxSlabCodeVar.substring(4))+count;
               String numString = String.valueOf(num);
               int l = numString.length();
               String zeros="00000000";
               String newSlabCode= "SLA"+zeros.substring(l)+numString;
               hrSlabMasterPKTO.setSlabCode(newSlabCode);
               count++;
               
           }
        //  maxSlabCodeVar=hrSlabMasterPKTO.getSlabCode();
            hrSlabMasterTO.setDesgId(desgId);
            hrSlabMasterTO.setAppFlg('Y');
            hrSlabMasterTO.setAuthBy(getUserName());
            hrSlabMasterTO.setDefaultComp(compCode);
            hrSlabMasterTO.setEnteredBy(getUserName());
            hrSlabMasterTO.setHrSlabMasterPK(hrSlabMasterPKTO);
            hrSlabMasterTO.setModDate(new Date());
            hrSlabMasterTO.setRangeFrom(Double.parseDouble(this.slabFromAmount));
            hrSlabMasterTO.setRangeTo(Double.parseDouble(this.slabToAmount));
            hrSlabMasterTO.setSlabCriteria(slabCriteria);
            hrSlabMasterTO.setSlabCriteriaAmt(Double.parseDouble(slabCriteriaAmount));
            hrSlabMasterTO.setStatFlag("Y");
            hrSlabMasterTO.setStatUpFlag("N");
            hrSlabMasterTO.setSlabCriteriaMaxAmt(slabMaxAmount == null || slabMaxAmount.equalsIgnoreCase("")
                    ? 0.0 : Double.valueOf(slabMaxAmount));
            hrSlabMasterPKTO.setSalarySlabId(salarySlabId);
            hrSlabMasterTO.setSlabCriteriaMinAmt(slabMinAmount == null || slabMinAmount.equalsIgnoreCase("")
                    ? 0.0 : Double.valueOf(slabMinAmount));
            hrSlabMasterTO.setBaseComponent(baseComponent);
            hrSlabMasterTO.setDepnComponent(depnComponent);
            modifiedSlabMasterBatchList.add(hrSlabMasterTO);
            
          }}

            String valResult = validations();
            if (!valResult.equalsIgnoreCase("true")) {
                this.setMessage(valResult);
                return;
            }
            /* To chech duplicate mapping in existing Mapping list*/
            
            
            for (HrSlabMasterTO obj : slabMasterBatchList) {
                if ((obj.getBaseComponent().equalsIgnoreCase(hrSlabMasterTO.getBaseComponent())
                        && obj.getDepnComponent().equalsIgnoreCase(hrSlabMasterTO.getDepnComponent()))
                        && (obj.getRangeFrom() == hrSlabMasterTO.getRangeFrom()
                        && obj.getRangeTo() == hrSlabMasterTO.getRangeTo())) {
                    setMessage("This Component mapping already exist.");
                    return;
                }
            }
            /*Duplicacy checking end*/
            slabMasterBatchList.add(hrSlabMasterTO);
            
     //       Collections.sort(slabMasterBatchList, new Compare());
            /* To Refresh the input fields */
            setSlabCriteria("0");
            setSlabCriteriaAmount("");
            setSlabFromAmount("");
            setSlabToAmount("");
            setMessage("");
            setSlabMaxAmount("0");
            setSlabMinAmount("0");
            setBaseComponent("0");
            setDepnComponent("0");
         
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
//            if(function.equalsIgnoreCase("MODIFY")){
//                updateModifySlabData() ;
//            }
    }

    public void saveSlabMaster() {
        try {
            if (mode.equalsIgnoreCase("UPDATE") || mode.equalsIgnoreCase("DELETE")) {
                if (slabCriteria.equalsIgnoreCase("0")) {
                    setMessage("Select the slab criteria amount!!!");
                    return;
                }
                if (getSlabCriteriaAmount().equalsIgnoreCase("0")) {
                    setMessage("Select the slab criteria amount!!!");
                    return;
                }
            }
            String result = "";
            if (mode.equalsIgnoreCase("SAVE") && (function.equalsIgnoreCase("1"))) {
                if (!slabMasterBatchList.isEmpty()) {
                    result = payrollOthRemote.saveModifySlabMasterBatchData(slabMasterBatchList, mode);
                    if (result.equalsIgnoreCase("true")) {
                        this.setMessage("successfully inserted");
                    }
                }
            } else if (function.equalsIgnoreCase("MODIFY")) {
                mode = "UPDATE";
                result = payrollOthRemote.saveModifySlabMasterBatchData(slabMasterBatchList, mode);
                if (result.equalsIgnoreCase("true")) {
                    this.setMessage("successfully inserted");
                }
            }
            refreshSlabMasterForm();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
            logger.error("Exception occured while executing method saveSlabMaster()", e);
        }
    }

    public void refreshSlabMasterForm() {
        disableDelete = true;
        disableSave = false;
        disableDescription = false;
        disableFromAmount = false;
        disableSlabCriteria = false;
        disableSlabCriteriaAmt = false;
        disableToAmount = false;
        disableNatureList = false;
        disablePurposeList = false;
        slabMasterDescList = new ArrayList<SelectItem>();
        slabMasterDescList.add(new SelectItem("0", "--SELECT--"));
        setNatureList("0");
        setPurposeList("0");
        setSlabCriteria("0");
        setSlabCriteriaAmount("");
        setSlabFromAmount("");
        setSlabToAmount("");
//        setMessage("");
        setFunction("0");
        setSalarySlabId("0");
        setSlabMaxAmount("");
        setDesgId("0");
        slabMasterBatchList = new ArrayList<HrSlabMasterTO>();
        mode = "SAVE";
        disablegrup = false;
    }

    public String validations() {
        try {
            if (desgId.equalsIgnoreCase("0")) {
                return "Select the  Designation field!!!";
            }
            if (salarySlabId.equalsIgnoreCase("0")) {
                return "Select the  Basic Salary Slab field!!!";
            }
            if (natureList.equalsIgnoreCase("0")) {
                return "Select the nature field!!!";
            }
            if (purposeList.equalsIgnoreCase("0")) {
                return "Select the purpose field!!!";
            }
            if (getSlabFromAmount().equalsIgnoreCase("")) {
                return "From amount field cannot be empty!!!";
            }
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher SlabFromAmount = p.matcher(this.getSlabFromAmount());
            if (!SlabFromAmount.matches()) {
                return "Enter Valid numeric value for Slab From amount!!!";
            }
            if (Double.parseDouble(getSlabFromAmount()) < 0) {
                return "Slab from amount cannot be negative!!!";
            }
            if (getSlabToAmount().equalsIgnoreCase("")) {
                return "To amount field cannot be empty!!!";
            }
            Matcher SlabToAmount = p.matcher(this.getSlabToAmount());
            if (!SlabToAmount.matches()) {
                return "Enter Valid numeric value for Slab to amount!!!";
            }

            if (baseComponent.equalsIgnoreCase("0")) {
                this.setMessage("Please select Base Component !");
            }
            if (depnComponent.equalsIgnoreCase("0")) {
                this.setMessage("Please select Dependent Component !");
            }
            if (Double.parseDouble(getSlabToAmount()) < 0) {
                return "Slab to amount cannot be negative!!!";
            }
            if (slabCriteria.equalsIgnoreCase("0")) {
                return "select the appropriate value for slab critera!!!";
            }
            if (getSlabCriteriaAmount().equalsIgnoreCase("")) {
                return "Slab criteria field cannot be empty!!!";
            }
            Matcher criteriaAmount = p.matcher(this.getSlabCriteriaAmount());
            if (!criteriaAmount.matches()) {
                return "Enter Valid numeric value for criteria amount!!!";
            }
            if (Double.parseDouble(getSlabCriteriaAmount()) < 0) {
                return "Slab criteria amount cannot be negative!!!";
            }
            if (!(getSlabMaxAmount().equalsIgnoreCase("") || getSlabMaxAmount().equalsIgnoreCase("0") || getSlabMaxAmount().equalsIgnoreCase("0.0"))) {
                Matcher SlabmaxAmount = p.matcher(this.getSlabMaxAmount());
                if (!SlabmaxAmount.matches()) {
                    return "Enter Valid numeric value for Component Max Amount!!!";
                }
            }
            
             if (!(getSlabMinAmount().equalsIgnoreCase("") || getSlabMinAmount().equalsIgnoreCase("0") || getSlabMinAmount().equalsIgnoreCase("0.0"))) {
                Matcher SlabminiAmount = p.matcher(this.getSlabMinAmount());
                if (!SlabminiAmount.matches()) {
                    return "Enter Valid numeric value for Component Max Amount!!!";
                }
            }
        } catch (NumberFormatException e) {
            return "Numeric field are not valid!!!";
        } catch (Exception e) {
        }
        return "true";
    }

    public String btnExitAction() {
        refreshSlabMasterForm();
        return "case1";
    }

    public boolean isDisableNatureList() {
        return disableNatureList;
    }

    public void setDisableNatureList(boolean disableNatureList) {
        this.disableNatureList = disableNatureList;
    }

    public boolean isDisablePurposeList() {
        return disablePurposeList;
    }

    public void setDisablePurposeList(boolean disablePurposeList) {
        this.disablePurposeList = disablePurposeList;
    }

    public String getNatureList() {
        return natureList;
    }

    public void setNatureList(String natureList) {
        this.natureList = natureList;
    }

    public List<SelectItem> getNatureListBox() {
        return natureListBox;
    }

    public void setNatureListBox(List<SelectItem> natureListBox) {
        this.natureListBox = natureListBox;
    }

    public String getPurposeList() {
        return purposeList;
    }

    public void setPurposeList(String purposeList) {
        this.purposeList = purposeList;
    }

    public List<SelectItem> getPurposeListBox() {
        return purposeListBox;
    }

    public void setPurposeListBox(List<SelectItem> purposeListBox) {
        this.purposeListBox = purposeListBox;
    }

    public boolean isDisableDelete() {
        return disableDelete;
    }

    public void setDisableDelete(boolean disableDelete) {
        this.disableDelete = disableDelete;
    }

    public boolean isDisableDescription() {
        return disableDescription;
    }

    public void setDisableDescription(boolean disableDescription) {
        this.disableDescription = disableDescription;
    }

    public boolean isDisableFromAmount() {
        return disableFromAmount;
    }

    public void setDisableFromAmount(boolean disableFromAmount) {
        this.disableFromAmount = disableFromAmount;
    }

    public boolean isDisableSave() {
        return disableSave;
    }

    public void setDisableSave(boolean disableSave) {
        this.disableSave = disableSave;
    }

    public boolean isDisableSlabCriteria() {
        return disableSlabCriteria;
    }

    public void setDisableSlabCriteria(boolean disableSlabCriteria) {
        this.disableSlabCriteria = disableSlabCriteria;
    }

    public boolean isDisableSlabCriteriaAmt() {
        return disableSlabCriteriaAmt;
    }

    public void setDisableSlabCriteriaAmt(boolean disableSlabCriteriaAmt) {
        this.disableSlabCriteriaAmt = disableSlabCriteriaAmt;
    }

    public boolean isDisableToAmount() {
        return disableToAmount;
    }

    public void setDisableToAmount(boolean disableToAmount) {
        this.disableToAmount = disableToAmount;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSlabCriteria() {
        return slabCriteria;
    }

    public void setSlabCriteria(String slabCriteria) {
        this.slabCriteria = slabCriteria;
    }

    public String getSlabCriteriaAmount() {
        return slabCriteriaAmount;
    }

    public void setSlabCriteriaAmount(String slabCriteriaAmount) {
        this.slabCriteriaAmount = slabCriteriaAmount;
    }

    public List<SelectItem> getSlabCriteriaList() {
        return slabCriteriaList;
    }

    public void setSlabCriteriaList(List<SelectItem> slabCriteriaList) {
        this.slabCriteriaList = slabCriteriaList;
    }

    public String getSlabFromAmount() {
        return slabFromAmount;
    }

    public void setSlabFromAmount(String slabFromAmount) {
        this.slabFromAmount = slabFromAmount;
    }

    public List<SelectItem> getSlabMasterDescList() {
        return slabMasterDescList;
    }

    public void setSlabMasterDescList(List<SelectItem> slabMasterDescList) {
        this.slabMasterDescList = slabMasterDescList;
    }

    public SlabMasterGrid getSlabMastercurrentItem() {
        return slabMastercurrentItem;
    }

    public void setSlabMastercurrentItem(SlabMasterGrid slabMastercurrentItem) {
        this.slabMastercurrentItem = slabMastercurrentItem;
    }

    public String getSlabToAmount() {
        return slabToAmount;
    }

    public void setSlabToAmount(String slabToAmount) {
        this.slabToAmount = slabToAmount;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public List<SelectItem> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<SelectItem> functionList) {
        this.functionList = functionList;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getSalarySlabId() {
        return salarySlabId;
    }

    public void setSalarySlabId(String salarySlabId) {
        this.salarySlabId = salarySlabId;
    }

    public List<SelectItem> getSalarySlabIdList() {
        return salarySlabIdList;
    }

    public void setSalarySlabIdList(List<SelectItem> salarySlabIdList) {
        this.salarySlabIdList = salarySlabIdList;
    }

    public List<HrSlabMasterTO> getSlabMasterBatchList() {
        return slabMasterBatchList;
    }

    public void setSlabMasterBatchList(List<HrSlabMasterTO> slabMasterBatchList) {
        this.slabMasterBatchList = slabMasterBatchList;
    }

    public String getSlabMaxAmount() {
        return slabMaxAmount;
    }

    public void setSlabMaxAmount(String slabMaxAmount) {
        this.slabMaxAmount = slabMaxAmount;
    }

    public HrSlabMasterTO getSlabMasterBatchcurrentItem() {
        return slabMasterBatchcurrentItem;
    }

    public void setSlabMasterBatchcurrentItem(HrSlabMasterTO slabMasterBatchcurrentItem) {
        this.slabMasterBatchcurrentItem = slabMasterBatchcurrentItem;
    }

    public boolean isDisableAdd() {
        return disableAdd;
    }

    public void setDisableAdd(boolean disableAdd) {
        this.disableAdd = disableAdd;
    }

    public String getDesgId() {
        return desgId;
    }

    public void setDesgId(String desgId) {
        this.desgId = desgId;
    }

    public List<SelectItem> getDesgIdList() {
        return desgIdList;
    }

    public void setDesgIdList(List<SelectItem> desgIdList) {
        this.desgIdList = desgIdList;
    }

    public String getBaseComponent() {
        return baseComponent;
    }

    public void setBaseComponent(String baseComponent) {
        this.baseComponent = baseComponent;
    }

    public String getDepnComponent() {
        return depnComponent;
    }

    public void setDepnComponent(String depnComponent) {
        this.depnComponent = depnComponent;
    }

    public String getSlabMinAmount() {
        return slabMinAmount;
    }

    public void setSlabMinAmount(String slabMinAmount) {
        this.slabMinAmount = slabMinAmount;
    }
         
    
    
    public boolean isDisablegrup() {
        return disablegrup;
    }

    public void setDisablegrup(boolean disablegrup) {
        this.disablegrup = disablegrup;
    }

    public List<SelectItem> getEarnigslabMasterDescList() {
        return earnigslabMasterDescList;
    }

    public void setEarnigslabMasterDescList(List<SelectItem> earnigslabMasterDescList) {
        this.earnigslabMasterDescList = earnigslabMasterDescList;
    }

    public class Compare implements Comparator<HrSlabMasterTO> {

        @Override
        public int compare(HrSlabMasterTO e1, HrSlabMasterTO e2) {
            if ((e1.getBaseComponent().equalsIgnoreCase("BASIC"))) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}
