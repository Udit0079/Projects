/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.txn;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.facade.txn.TdAuthorizationManagementFacadeRemote;
import com.cbs.web.pojo.txn.GridLoadDupRecAuth;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.context.FacesContext;

public class TdDuplicateRecptAuth extends BaseBean {

    private List<GridLoadDupRecAuth> gridData;
    double totTdAmt;
    static String name;
    GridLoadDupRecAuth authorized;
    private int currentRow;
    String msg;
    public float receiptNo;
    public float vchNo;
    boolean value1;
    private String accountNumber;
    private String fddt;
    private float roi;
    //private String orgnBrCode;
    private final String jndiHomeName = "TdAuthorizationManagementFacade";
    private TdAuthorizationManagementFacadeRemote tdAuthRemote = null;
    private final String jndiName = "TdReceiptManagementFacade";
    private TdReceiptManagementFacadeRemote tdRcptMgmtRemote = null;

    public GridLoadDupRecAuth getAuthorized() {
        return authorized;
    }

    public void setAuthorized(GridLoadDupRecAuth authorized) {
        this.authorized = authorized;
    }

    public boolean isValue1() {
        return value1;
    }

    public void setValue1(boolean value1) {
        this.value1 = value1;

    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCurrentRow() {

        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public List<GridLoadDupRecAuth> getGridData() {
        return gridData;
    }

    public void setGridData(List<GridLoadDupRecAuth> gridData) {
        this.gridData = gridData;
    }

    public TdDuplicateRecptAuth() {
        try {
            tdAuthRemote = (TdAuthorizationManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            tdRcptMgmtRemote = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiName);
            
            this.setMsg("");
            gridload();
        } catch (ApplicationException e) {
            this.setMsg(e.getMessage());
        } catch (Exception e) {
            this.setMsg(e.getMessage());
        }
    }

    public void gridload() {
        try {
            value1 = true;
            List result = new ArrayList();
            gridData = new ArrayList<>();
            result = tdAuthRemote.gridLoadingDuplicateReceipt(this.getOrgnBrCode());

            for (int i = 0; i < result.size(); i++) {
                Vector ele = (Vector) result.get(i);
                GridLoadDupRecAuth rd = new GridLoadDupRecAuth();

                rd.setAcNo(ele.get(0).toString());

                rd.setVchNo(Float.parseFloat(ele.get(1).toString()));

                rd.setRoi(Float.parseFloat(ele.get(2).toString()));

                rd.setTdMadeDt(ele.get(3).toString());

                rd.setFdDt(ele.get(4).toString());
                String fdDt = ele.get(4).toString().substring(6, 10) + ele.get(4).toString().substring(3, 5) + ele.get(4).toString().substring(0, 2);

                if (ele.get(4).equals("")) {

                    rd.setPrinAmt(Float.parseFloat(ele.get(4).toString()));
                } else {

                    rd.setPrinAmt(Float.parseFloat(ele.get(6).toString()));
                }

                rd.setMatDt(ele.get(5).toString());
                String matDt = ele.get(5).toString().substring(6, 10) + ele.get(5).toString().substring(3, 5) + ele.get(5).toString().substring(0, 2);

                rd.setIntOpt(ele.get(7).toString());

                rd.setReceiptNo(Float.parseFloat(ele.get(8).toString()));

                rd.setPeriod(ele.get(9).toString());
                if (ele.get(10).equals("null")) {
                    rd.setOfAcno(ele.get(10).toString());
                }

                rd.setSeqNo(Float.parseFloat(ele.get(11).toString()));

                String fdTotal1 = tdRcptMgmtRemote.orgFDInterest(ele.get(7).toString(), Float.parseFloat(ele.get(2).toString()), fdDt, matDt, Float.parseFloat(ele.get(6).toString()), ele.get(9).toString(),ele.get(0).toString().substring(0,2));
                double fdTotal = Double.parseDouble(fdTotal1);
                rd.setIntAtMat(fdTotal);

                if ((ele.get(7).toString()).equals("M") || (ele.get(7).toString()).equals("Q")) {
                    String totTdAmt1 = ele.get(6).toString();
                    totTdAmt = Double.parseDouble(totTdAmt1);
                    rd.setTotTdAmt(totTdAmt);
                } else if ((ele.get(7).toString()).equals("C") || (ele.get(7).toString()).equals("S")) {
                    String prinamt = ele.get(6).toString();
                    double totalamt = Double.parseDouble(prinamt) + fdTotal;
                    rd.setTotTdAmt(totalamt);
                }

                if ((ele.get(12).toString()).equals("c")) {
                    rd.setStatus("closed");
                } else {
                    rd.setStatus("Active");
                }


                rd.setEnterBy(ele.get(13).toString());
                rd.setAuth(ele.get(14).toString());

                gridData.add(rd);
            }
        } catch (ApplicationException e) {
            this.setMsg(e.getMessage());
        } catch (Exception e) {
            this.setMsg(e.getMessage());
        }
    }

  
    public void authorizeClick() {
        try {
            if (authorized.getEnterBy().equals(this.getUserName())) {
                setMsg("You Cannot Authorize Your Own Entry");
                return;
            } else {
                accountNumber = authorized.getAcNo();
                vchNo = authorized.getVchNo();
                fddt = authorized.getFdDt();
                receiptNo = authorized.getReceiptNo();
                roi = authorized.getRoi();
            }
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            String currentDate;
            Date date = new Date();
            currentDate = ymd.format(date);

            String result = tdAuthRemote.authorizationChange(accountNumber, vchNo, fddt, receiptNo, roi, this.getUserName(), this.getOrgnBrCode(), currentDate);

            if(result.length() > 4){
                if (result.substring(0,4).equals("true")) {
                    gridData.remove(authorized);
                    //this.setMsg("Record has been successfully authorized And Receipt No is " + result.substring(5, result.lastIndexOf(":")) + " And Batch No Is 0" + result.substring(result.lastIndexOf(":")+1));
                    this.setMsg("Record has been successfully authorized And Receipt No is " + (long)Float.parseFloat(result.substring(5)));
                } else if (result.equals("No New Receipt Avaliable For Issue")) {
                    this.setMsg("No New Receipt Avaliable For Issue");
                } else {
                    this.setMsg("Record could not be authorizd");
                }
            }else{
                this.setMsg("Record could not be authorizd");
            }            

        } catch (ApplicationException e) {
            this.setMsg(e.getMessage());
        } catch (Exception e) {
            this.setMsg(e.getMessage());
        }
    }

    public void refreshBtnAction() {
        gridload();
        this.setMsg("");
    }

    public String exitForm() {
        refreshBtnAction();
        return "case1";
    }
}
