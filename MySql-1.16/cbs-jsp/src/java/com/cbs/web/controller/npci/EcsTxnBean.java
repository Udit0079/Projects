/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.npci;

import com.cbs.constant.CbsConstant;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.other.OtherNpciMgmtFacadeRemote;
import com.cbs.pojo.NpciInputPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;

public class EcsTxnBean extends BaseBean {

    private String errorMessage;
    private String txnType;
    private boolean allSelected;
    private List<SelectItem> txnTypeList;
    private List<NpciInputPojo> gridDetail;
    private OtherNpciMgmtFacadeRemote otherNpciRemote;
    private FtsPostingMgmtFacadeRemote ftsRemote;

    public EcsTxnBean() {
        try {
            otherNpciRemote = (OtherNpciMgmtFacadeRemote) ServiceLocator.getInstance().lookup("OtherNpciMgmtFacade");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");

            txnTypeList = new ArrayList<SelectItem>();
            txnTypeList.add(new SelectItem("0", "--Select--"));
            txnTypeList.add(new SelectItem("ECT", "Credit"));
            txnTypeList.add(new SelectItem("EDT", "Debit"));
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void setAllBoxSelected() {
        try {
            System.out.println("In All Selected CheckBox.");
            if (allSelected) {
                for (NpciInputPojo obj : gridDetail) {
                    obj.setSelected(true);
                }
            } else {
                for (NpciInputPojo obj : gridDetail) {
                    obj.setSelected(false);
                }
            }
        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
    }

    public void txnTypeAction() {
        try {
            gridDetail = new ArrayList<NpciInputPojo>();
            List list = otherNpciRemote.retrieveTxnData(this.txnType, getOrgnBrCode());
            if (list.isEmpty()) {
                this.setErrorMessage("There is no data to process.");
                return;
            }
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                NpciInputPojo obj = new NpciInputPojo();

                obj.setuRefNo(ele.get(0).toString().trim());
                obj.setMicr(ele.get(1).toString().trim());
                obj.setAcType(ele.get(2).toString().trim());
                obj.setAcNo(ele.get(3).toString().trim());
                obj.setName(ele.get(4).toString().trim());

                BigDecimal individualAmt = new BigDecimal(ele.get(5).toString().trim()).divide(new BigDecimal(100));
                obj.setAmount(individualAmt.toString());
                obj.setOwnAcno(ele.get(6).toString().trim());
                obj.setEntry_by(ele.get(7).toString().trim());

                gridDetail.add(obj);
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void processAction() {
        List<NpciInputPojo> processList = new ArrayList<NpciInputPojo>();
        try {
            if (this.txnType == null || this.txnType.equals("0")) {
                this.setErrorMessage("Please select Txn.Type.");
                return;
            }
            if (gridDetail.isEmpty()) {
                this.setErrorMessage("There is no data to process.");
                return;
            }
            for (NpciInputPojo pojo : gridDetail) {
                if (pojo.isSelected()) {
                    processList.add(pojo);
                }
            }
            //For Testing Purpose Only
            for (NpciInputPojo pojo : processList) {
                System.out.println("Unique Ref No Is-->" + pojo.getuRefNo());
            }
            //Testing End
            if (txnType.equalsIgnoreCase("ECT")) {
                //Balance checking
                for (NpciInputPojo pojo : processList) {
                    String actNature = ftsRemote.getAccountNature(pojo.getOwnAcno().trim());
                    String chkBalResult = ftsRemote.chkBal(pojo.getOwnAcno().trim(), 1, 1, actNature);
                    if (!chkBalResult.equalsIgnoreCase("true")) {
                        if (actNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                            String chkBalance = ftsRemote.checkBalForOdLimit(pojo.getOwnAcno().trim(),
                                    Double.parseDouble(pojo.getAmount()), getUserName());
                            if (!chkBalance.equalsIgnoreCase("1")) {
                                this.setErrorMessage("Balance Exceeds for A/c No-->" + pojo.getOwnAcno().trim()
                                        + "::Unique No-->" + pojo.getuRefNo());
                                return;
                            }
                        } else if (actNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                            String balResult = ftsRemote.checkBalance(pojo.getOwnAcno().trim(),
                                    Double.parseDouble(pojo.getAmount()), getUserName());
                            if (!balResult.equalsIgnoreCase("true")) {
                                this.setErrorMessage("Balance Exceeds for A/c No-->" + pojo.getOwnAcno().trim()
                                        + "::Unique No-->" + pojo.getuRefNo());
                                return;
                            }
                        }
                    }
                }
                //Balance checking end here
                String result = otherNpciRemote.processEcsInputTxn(processList, this.txnType, getUserName(),
                        getTodayDate(), getOrgnBrCode());
                if (!result.equalsIgnoreCase("true")) {
                    this.setErrorMessage(result);
                    return;
                }
                btnRefreshAction();
                this.setErrorMessage("Transaction has been processed successfully.");
            } else if (txnType.equalsIgnoreCase("EDT")) {
                String result = otherNpciRemote.processEcsInputTxn(processList, this.txnType, getUserName(),
                        getTodayDate(), getOrgnBrCode());
                if (!result.equalsIgnoreCase("true")) {
                    this.setErrorMessage(result);
                    return;
                }
                btnRefreshAction();
                this.setErrorMessage("Transaction has been processed successfully.");
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void btnRefreshAction() {
        this.setErrorMessage("");
        this.setTxnType("0");
        this.allSelected = false;
        setAllBoxSelected();
        gridDetail = new ArrayList<NpciInputPojo>();
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }

    //Getter And Setter
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public List<NpciInputPojo> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<NpciInputPojo> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public List<SelectItem> getTxnTypeList() {
        return txnTypeList;
    }

    public void setTxnTypeList(List<SelectItem> txnTypeList) {
        this.txnTypeList = txnTypeList;
    }

    public boolean isAllSelected() {
        return allSelected;
    }

    public void setAllSelected(boolean allSelected) {
        this.allSelected = allSelected;
    }
}
