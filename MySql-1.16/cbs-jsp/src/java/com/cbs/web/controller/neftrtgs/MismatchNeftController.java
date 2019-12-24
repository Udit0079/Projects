/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.neftrtgs;

import com.cbs.dto.NeftRtgsMismatchPojo;
import com.cbs.entity.neftrtgs.CbsAutoNeftDetails;
import com.cbs.entity.neftrtgs.NeftRtgsStatus;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.neftrtgs.UploadNeftRtgsMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.report.other.JointHolderBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;

public class MismatchNeftController extends BaseBean {

    private String message;
    private String processType;
    private List<SelectItem> processTypeList;
    private String neftBank;
    private List<SelectItem> neftBankList;
    private String actionType;
    private List<SelectItem> actionTypeList;
    private String pUtr;
    private String pBeneAccount;
    private String pAmount;
    private String pStatus;
    private String viewID = "/pages/master/sm/test.jsp";
    private boolean disProcessButton;
    private boolean jtDetailPopUp;
    private List<SelectItem> pStatusList;
    private NeftRtgsMismatchPojo currentItem;
    private List<NeftRtgsMismatchPojo> tableDataList;
    private List<NeftRtgsMismatchPojo> processList;
    //private List<CbsAutoNeftDetails> neftAutoObj;
    private UploadNeftRtgsMgmtFacadeRemote remote = null;
    private final String jndiHomeName = "UploadNeftRtgsMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsRemote;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    Date cutDt = new Date();
    private static final Object LOCK = new Object();
    private JointHolderBean jointBean;
    private CommonReportMethodsRemote commonReport;

    public MismatchNeftController() {
        this.setMessage("");
        try {
            remote = (UploadNeftRtgsMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            commonReport = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");

            this.setpUtr("");
            this.setpBeneAccount("");
            this.setpAmount("");
            this.setpStatus("0");
            this.disProcessButton = true;

            processTypeList = new ArrayList<>();
            processTypeList.add(new SelectItem("--Select--"));
            processTypeList.add(new SelectItem("AUTO"));
            processTypeList.add(new SelectItem("MANUAL"));

            neftBankList = new ArrayList<>();
            neftBankList.add(new SelectItem("--Select--"));

            processList = new ArrayList<>();

            pStatusList = new ArrayList<>();
            pStatusList.add(new SelectItem("0", "--Select--"));
//            pStatusList.add(new SelectItem("R", "RETURN"));
//            pStatusList.add(new SelectItem("P", "PROCESS"));

            actionTypeList = new ArrayList<>();
            actionTypeList.add(new SelectItem("0", "--Select--"));
            List list = commonReport.getRefRecList("417");
            if (list.isEmpty()) {
                this.setMessage("Please define mode in reference master.");
                return;
            }
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                actionTypeList.add(new SelectItem(ele.get(0).toString().trim(), ele.get(1).toString().trim()));
            }

            this.setMessage("Please select process type.");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void processTypeAction() {
        this.setMessage("");
        try {
            if (this.processType == null || this.processType.equals("--Select--")) {
                this.setMessage("Please select process type.");
                return;
            }
            neftBankList = new ArrayList<>();
            neftBankList.add(new SelectItem("--Select--"));
            List<CbsAutoNeftDetails> list = remote.getNeftMisMatchBankName(this.processType);
            if (list.isEmpty()) {
                this.setMessage("There is no banks for manual inward uploading.");
                return;
            }
            for (CbsAutoNeftDetails obj : list) {
                neftBankList.add(new SelectItem(obj.getNeftBankName()));
            }
            this.setMessage("Please select mode.");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void actionTypeAction() {
        this.setMessage("");
        try {
            if (this.processType == null || this.processType.equals("--Select--")) {
                this.setMessage("Please select process type.");
                return;
            }
            if (this.actionType == null || this.actionType.equals("0")) {
                this.setMessage("Please select mode.");
                return;
            }
            pStatusList = new ArrayList<>();
            pStatusList.add(new SelectItem("0", "--Select--"));
            if (actionType.equalsIgnoreCase("M")) {
                pStatusList.add(new SelectItem("R", "RETURN"));
                pStatusList.add(new SelectItem("P", "PROCESS"));
            } else if (actionType.equalsIgnoreCase("U")) {
                pStatusList.add(new SelectItem("R", "RETURN"));
            }
            this.setMessage("Please select neft bank.");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void neftBankAction() {
        this.setMessage("");
        tableDataList = new ArrayList<>();
        try {
            if (this.processType == null || this.processType.equals("--Select--")) {
                this.setMessage("Please Select Process Type");
                return;
            }
            if (this.neftBank == null || this.neftBank.equals("--Select--")) {
                this.setMessage("Please Select Neft Bank");
                return;
            }
            if (this.actionType == null || this.actionType.equals("0")) {
                this.setMessage("Please Select Mode");
            }
            getMismatchData(this.processType, this.neftBank);
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void getMismatchData(String processType, String nefBankName) {
        tableDataList = new ArrayList<>();
        try {
            if (actionType.equalsIgnoreCase("M")) {
                List<NeftRtgsStatus> resultList = remote.getMismatchData(ymd.parse(ymd.format(cutDt)), "Mismatch",
                        processType, nefBankName);
                if (!resultList.isEmpty()) {
                    for (NeftRtgsStatus entity : resultList) {
                        NeftRtgsMismatchPojo pojo = new NeftRtgsMismatchPojo();

                        pojo.setUtr(entity.getUtr());
                        pojo.setBeneAccount(entity.getBeneAccount());
                        pojo.setBeneName(entity.getBeneName());
                        pojo.setReceiverIfsc(entity.getReceiverIfsc());
                        pojo.setAmount(entity.getAmount());
                        pojo.setReason(entity.getReason());
                        pojo.setActualValue(entity.getDetails());
                        pojo.setTxnType(entity.getTxnType());
                        pojo.setSenderName(entity.getSenderName());
                        pojo.setValueDt(ymd.format(entity.getValueDt()));

                        tableDataList.add(pojo);
                    }
                    this.setMessage("Please select a row from table to process.");
                } else {
                    this.setMessage("There is no mismatch data.");
                }

            } else if (actionType.equalsIgnoreCase("U")) {
                List<NeftRtgsStatus> returnList = remote.getUnSuccessEntity(processType, nefBankName);
                if (!returnList.isEmpty()) {
                    for (NeftRtgsStatus entity : returnList) {
                        NeftRtgsMismatchPojo pojo = new NeftRtgsMismatchPojo();

                        pojo.setUtr(entity.getUtr());
                        pojo.setBeneAccount(entity.getBeneAccount());
                        pojo.setBeneName(entity.getBeneName());
                        pojo.setReceiverIfsc(entity.getReceiverIfsc());
                        pojo.setAmount(entity.getAmount());
                        pojo.setReason(entity.getReason());
                        pojo.setActualValue(entity.getDetails());
                        pojo.setTxnType(entity.getTxnType());
                        pojo.setSenderName(entity.getSenderName());
                        pojo.setValueDt(ymd.format(entity.getValueDt()));

                        tableDataList.add(pojo);
                    }
                    this.setMessage("Please select a row from table to process.");
                } else {
                    this.setMessage("There is no data.");
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void createProcessData() {
        this.setMessage("");
        try {
            this.pUtr = currentItem.getUtr();
            this.pBeneAccount = currentItem.getBeneAccount();
            this.pAmount = currentItem.getAmount().toString();
            this.setMessage("Status should be selected.");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void validateStatus() {
        this.setMessage("");
        if (this.pStatus.equals("0")) {
            this.setMessage("Please select status");
        } else {
            this.disProcessButton = false;
            this.setMessage("Please click on process button to proceed.");
        }
    }

    public void processAction() {
        this.setMessage("");
        try {
            if (this.pStatus.equals("0")) {
                this.setMessage("Please select status");
                return;
            }
            if (currentItem == null) {
                this.setMessage("There is no data to process. First you have to select a row from table !");
                return;
            }
            if (pUtr == null || pUtr.equals("")) {
                this.setMessage("First you have to select a row from table !");
                return;
            }
            processList = new ArrayList<>();
            processList.add(currentItem);
            String result = "";
            if (actionType.equalsIgnoreCase("M")) {
                synchronized (LOCK) {
                    result = remote.processMismatchData(processList, pStatus, getOrgnBrCode(),
                            getUserName(), "Mismatch", this.processType, this.neftBank);
                }
                if (!result.equalsIgnoreCase("success")) {
                    this.setMessage(result);
                    return;
                }

                this.setpUtr("");
                this.setpBeneAccount("");
                this.setpAmount("");
                this.disProcessButton = true;
                this.currentItem = null;
                processList = new ArrayList<>();
                getMismatchData(this.processType, this.neftBank);
                if (pStatus.equals("R")) {
                    this.setMessage("Data has been returned successfully. Please check the report.");
                } else if (pStatus.equals("P")) {
                    this.setMessage("Data has been processed successfully. Please check the report.");
                }
            } else if (actionType.equalsIgnoreCase("U") && pStatus.equals("R")) {
                synchronized (LOCK) {
                    result = remote.generateIblIwReturnFiles(processList, "Unsuccess");
                }
                if (!result.equalsIgnoreCase("success")) {
                    this.setMessage(result);
                    return;
                }
                this.setpUtr("");
                this.setpBeneAccount("");
                this.setpAmount("");
                this.disProcessButton = true;
                this.currentItem = null;
                processList = new ArrayList<>();
                getMismatchData(this.processType, this.neftBank);
                this.setMessage("Data has been returned successfully.");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void validateJointAccount() {
        this.setMessage("");
        this.jtDetailPopUp = false;
        try {
            if (this.pBeneAccount == null || this.pBeneAccount.equals("")) {
                this.setMessage("Beneficiary account can not be blank.");
                return;
            }
            System.out.println("Form Account No Is->" + pBeneAccount);
            ftsRemote.getNewAccountNumber(this.pBeneAccount);
            this.jtDetailPopUp = true;
            this.setViewID("/pages/other/jointholderdetail.jsp");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void jtDetails() {
        this.setMessage("");
        try {
            jointBean.jtDetails(ftsRemote.getNewAccountNumber(this.pBeneAccount));
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void resetForm() {
        this.setMessage("");
        this.setProcessType("--Select--");
        this.setpUtr("");
        this.setActionType("0");
        this.setNeftBank("0");
        this.setpBeneAccount("");
        this.setpAmount("");
        this.setpStatus("0");
        this.disProcessButton = true;
        this.currentItem = null;
        processList = new ArrayList<NeftRtgsMismatchPojo>();
        tableDataList = new ArrayList<NeftRtgsMismatchPojo>();
        this.setMessage("Please select process type.");
    }

    public String exitBtnAction() {
        try {
            resetForm();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
        return "case1";
    }

    //Getter And Setter
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProcessType() {
        return processType;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }

    public List<SelectItem> getProcessTypeList() {
        return processTypeList;
    }

    public void setProcessTypeList(List<SelectItem> processTypeList) {
        this.processTypeList = processTypeList;
    }

    public String getNeftBank() {
        return neftBank;
    }

    public void setNeftBank(String neftBank) {
        this.neftBank = neftBank;
    }

    public List<SelectItem> getNeftBankList() {
        return neftBankList;
    }

    public void setNeftBankList(List<SelectItem> neftBankList) {
        this.neftBankList = neftBankList;
    }

    public String getpAmount() {
        return pAmount;
    }

    public void setpAmount(String pAmount) {
        this.pAmount = pAmount;
    }

    public String getpBeneAccount() {
        return pBeneAccount;
    }

    public void setpBeneAccount(String pBeneAccount) {
        this.pBeneAccount = pBeneAccount;
    }

    public String getpStatus() {
        return pStatus;
    }

    public void setpStatus(String pStatus) {
        this.pStatus = pStatus;
    }

    public List<SelectItem> getpStatusList() {
        return pStatusList;
    }

    public void setpStatusList(List<SelectItem> pStatusList) {
        this.pStatusList = pStatusList;
    }

    public String getpUtr() {
        return pUtr;
    }

    public void setpUtr(String pUtr) {
        this.pUtr = pUtr;
    }

    public boolean isDisProcessButton() {
        return disProcessButton;
    }

    public void setDisProcessButton(boolean disProcessButton) {
        this.disProcessButton = disProcessButton;
    }

    public List<NeftRtgsMismatchPojo> getTableDataList() {
        return tableDataList;
    }

    public void setTableDataList(List<NeftRtgsMismatchPojo> tableDataList) {
        this.tableDataList = tableDataList;
    }

    public NeftRtgsMismatchPojo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(NeftRtgsMismatchPojo currentItem) {
        this.currentItem = currentItem;
    }

    public List<NeftRtgsMismatchPojo> getProcessList() {
        return processList;
    }

    public void setProcessList(List<NeftRtgsMismatchPojo> processList) {
        this.processList = processList;
    }

    public boolean isJtDetailPopUp() {
        return jtDetailPopUp;
    }

    public void setJtDetailPopUp(boolean jtDetailPopUp) {
        this.jtDetailPopUp = jtDetailPopUp;
    }

    public JointHolderBean getJointBean() {
        return jointBean;
    }

    public void setJointBean(JointHolderBean jointBean) {
        this.jointBean = jointBean;
    }

    public String getViewID() {
        return viewID;
    }

    public void setViewID(String viewID) {
        this.viewID = viewID;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public List<SelectItem> getActionTypeList() {
        return actionTypeList;
    }

    public void setActionTypeList(List<SelectItem> actionTypeList) {
        this.actionTypeList = actionTypeList;
    }
}
