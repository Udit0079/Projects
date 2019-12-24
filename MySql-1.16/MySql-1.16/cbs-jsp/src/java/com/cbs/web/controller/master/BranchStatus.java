package com.cbs.web.controller.master;

import com.cbs.exception.ApplicationException;
import com.cbs.web.pojo.master.BranchManagementGrid;
import com.cbs.facade.master.BankAndLoanMasterFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public final class BranchStatus extends BaseBean {

    BankAndLoanMasterFacadeRemote activeUserEjb;
    int currentRow;
    BranchManagementGrid currentItem;
    private List<BranchManagementGrid> gridDetail;
    private String message;
    private String date;
    private Date dt = new Date();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
     private String bUserName;
    String eUserName;
  
  
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BranchManagementGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(BranchManagementGrid currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public List<BranchManagementGrid> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<BranchManagementGrid> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public BranchStatus() {
        try {
            date = dmy.format(dt);
            activeUserEjb = (BankAndLoanMasterFacadeRemote) ServiceLocator.getInstance().lookup("BankAndLoanMasterFacade");

            //  loadData();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void viewReport() {
        setMessage("");
        try {
            if (date.equalsIgnoreCase("")) {
                setMessage("Please fill date !");
                return;
            }

            if (!Validator.validateDate(date)) {
                setMessage("Please select Proper from date ");
                return;
            }

            if (dmy.parse(date).after(getDt())) {
                setMessage("Please Till date should be less than Login date ");
                return;
            }

            loadData();
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void loadData() {
        try {
            gridDetail = new ArrayList<BranchManagementGrid>();
            List resultList = new ArrayList();
            String asOnDate = ymd.format(dmy.parse(date));
            resultList = activeUserEjb.dataLoadBranchStatus(asOnDate);
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    BranchManagementGrid detail = new BranchManagementGrid();
                    detail.setSerialNo(i + 1);

                    String dStatus;
                    if (ele.get(0).toString().equalsIgnoreCase("N")) {
                        dStatus = "No";
                    } else {
                        dStatus = "Yes";
                    }
                    detail.setDayEndFlag(dStatus);
                    String mStatus;
                    if (ele.get(1).toString().equalsIgnoreCase("N")) {
                        mStatus = "No";
                    } else {
                        mStatus = "Yes";
                    }
                    detail.setMonthEndFlag(mStatus);

                    String bStatus;
                    if (ele.get(2).toString().equalsIgnoreCase("N")) {
                        bStatus = "No";
                    } else {
                        bStatus = "Yes";
                    }
                    detail.setDayBeginFlag(bStatus);

                    String bUsr = null;
                    if (ele.get(3).toString().equalsIgnoreCase("null")) {
                        bUsr = "";
                    } else {
                        bUsr = ele.get(3).toString();
                    }
                    detail.setBeginUser(bUsr);

                    detail.setBranchName(ele.get(4).toString());
                    String eUsr = null;
                    if (ele.get(5).toString().equalsIgnoreCase("null")) {
                        eUsr = "";
                    } else {
                        eUsr = ele.get(5).toString();
                    }
                    detail.setEndUser(eUsr);
                    if (ele.get(6) == null) {
                        detail.setSodTime("");
                    } else {
                        detail.setSodTime(ele.get(6).toString());
                    }
                    if (ele.get(7) == null) {
                        detail.setEodTime("");
                    } else {
                        detail.setEodTime(ele.get(7).toString());
                    }
                      if(bUsr.equalsIgnoreCase("")){
                          detail.setSodUserName("");
                      }else{
                          bUserName= activeUserEjb.getUserName(bUsr);    
                          if(!bUserName.equalsIgnoreCase("")){
                          detail.setSodUserName(bUserName);  
                          }else{
                          detail.setSodUserName("");    
                          }
                      }
                      
                     if(eUsr.equalsIgnoreCase("")){
                          detail.setEodUserName("");
                      }else{
                              eUserName = activeUserEjb.getUserName(eUsr);
                               if(!eUserName.equalsIgnoreCase("")){
                           detail.setEodUserName(eUserName);  
                          }else{
                          detail.setEodUserName("");    
                          }
                              
                              
                                           
                      } 
                    gridDetail.add(detail);
                }
            } else {
                return;
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String ExitForm() {
        // loadData();
        return "case1";
    }

    public void refreshForm() {
        this.setDate(dmy.format(dt));
        //loadData();
        setMessage("");
    }
}
