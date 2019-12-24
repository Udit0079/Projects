/*
 * CREATED BY        :  ROHIT KRISHNA GUPTA
 * CREATION DATE     :  28 OCT 2010
 */
package com.cbs.web.controller.txn;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.txn.OtherAuthorizationManagementFacadeRemote;
import com.cbs.pojo.ChbookDetailPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Admin
 */
public class ChequeMaintenanceAuth extends BaseBean {

    private String errorMessage;
    private String message;
    private List<ChbookDetailPojo> gridDetail;
    int currentRow;
    ChbookDetailPojo currentItem;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private final String jndiHomeName = "OtherAuthorizationManagementFacade";
    private OtherAuthorizationManagementFacadeRemote otherAuthRemote = null;

    public ChbookDetailPojo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(ChbookDetailPojo currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<ChbookDetailPojo> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<ChbookDetailPojo> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

   /** Creates a new instance of ChequeMaintenanceAuth */
    public ChequeMaintenanceAuth() {
        try {
            otherAuthRemote = (OtherAuthorizationManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            chqDetailGridLoad();
            this.setErrorMessage("");
            this.setMessage("");

        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void chqDetailGridLoad() {
        gridDetail = new ArrayList<ChbookDetailPojo>();
        try {
            List resultList = new ArrayList();
            resultList = otherAuthRemote.gridLoad(this.getOrgnBrCode(), ymd.format(sdf.parse(getTodayDate())));
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    ChbookDetailPojo chq = new ChbookDetailPojo();
                    chq.setAcctNo(ele.get(0).toString());
                    chq.setCustName(ele.get(1).toString());
                    //chq.setChqBookNo(ele.get(2).toString());
                    chq.setChqNo(ele.get(2).toString());
                    chq.setChqNoTo(ele.get(3).toString());
                    chq.setChqDate(ele.get(7).toString());
                    chq.setAmount(ele.get(5).toString());
                    chq.setFavoring(ele.get(6).toString());
                    chq.setAuthBy(ele.get(10).toString());
                    chq.setStatus(Integer.parseInt(ele.get(4).toString()));
                    String mode = ele.get(5).toString().equals("1") ? "Operative" : "Stop Payment";
                    chq.setAuthMode(mode);
                    chq.setEnteredBy(ele.get(8).toString());
                    chq.setCustState(ele.get(11).toString());
                    chq.setBranchState(ele.get(12).toString());
                    
                   // chq.setChargeFlag(ele.get(12).toString().equals("N") ? "No" : "Yes");
                    gridDetail.add(chq);
                }
            } else {
                this.setErrorMessage("No Record For Authorization !!!");
                return;
            }

        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

   

    public void changeAuth() {
        this.setErrorMessage("");
        this.setMessage("");
        ChbookDetailPojo item = gridDetail.get(currentRow);
        if (item.getAuthBy().equalsIgnoreCase("N")) {
            if (item.getEnteredBy().equalsIgnoreCase(this.getUserName())) {
                this.setErrorMessage("You cannot authorize your own entry.");
                return;
            } else {
//                item.setAuthBy("Y");
//                gridDetail.remove(currentRow);
//                gridDetail.add(currentRow, item);
                boolean found = false;
                for (ChbookDetailPojo item1 : gridDetail) {
                    if (item1.getAuthBy().equalsIgnoreCase("Y")) {
                        found = true;
                    }
                }
                if (found) {
                    this.setErrorMessage("Only one transction can be authorize at one time.");
                } else {
                    item.setAuthBy("Y");
                    gridDetail.remove(currentRow);
                    gridDetail.add(currentRow, item);
                }
            }
        } else if (item.getAuthBy().equalsIgnoreCase("Y")) {
//            if (item.getEnteredBy().equalsIgnoreCase(this.userName)) {
//                this.setErrorMessage("You are not a authorized person");
//                return;
//            } else {
//                item.setAuthBy("N");
//                gridDetail.remove(currentRow);
//                gridDetail.add(currentRow, item);
//            }

            item.setAuthBy("N");
            gridDetail.remove(currentRow);
            gridDetail.add(currentRow, item);
            this.setErrorMessage("");
        }
    }

    public void authorizeBtn() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            if (!gridDetail.isEmpty()) {
                for (int i = 0; i < gridDetail.size(); i++) {
                    ChbookDetailPojo obj = gridDetail.get(i);
                    if (obj.getAuthBy().equalsIgnoreCase("Y")) {
                        String result = otherAuthRemote.authorizeAction(obj, this.getUserName(), this.getOrgnBrCode());
                        if (result.equals("success")) {
                            this.setMessage("Authorization has been completed.");
                        } else {
                            this.setErrorMessage(result);
                            return;
                        }
                    }
                }
            }
            chqDetailGridLoad();
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void refreshForm() {
        chqDetailGridLoad();
        this.setErrorMessage("");
        this.setMessage("");
    }

    public String exitBtnAction() {
        refreshForm();
        return "case1";
    }
}
