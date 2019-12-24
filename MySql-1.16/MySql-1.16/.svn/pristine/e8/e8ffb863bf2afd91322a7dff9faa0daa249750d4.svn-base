/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.investment;

import com.cbs.facade.ho.investment.InvestmentMgmtFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 *
 * @author sipl
 */
public class RemRecInt extends BaseBean {

    private String message;
    private String ctrNoDd;
    private List<SelectItem> ctrNoList;
    private String fdrVal;
    private String purDtVal;
    private String matDtVal;
    private String faceVal;
    private String bookVal;
    private String sellerNmVal;
    private String roiVal;
    private String intOptVal;
    private String brVal;
    private String totAmtVal;
    private String recIntVal;
    private InvestmentMgmtFacadeRemote remoteInterface = null;
    private final String jndiHomeName = "InvestmentMgmtFacade";
    private SimpleDateFormat dmyformatter = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("#.##");

    public String getBookVal() {
        return bookVal;
    }

    public void setBookVal(String bookVal) {
        this.bookVal = bookVal;
    }

    public String getBrVal() {
        return brVal;
    }

    public void setBrVal(String brVal) {
        this.brVal = brVal;
    }

    public String getCtrNoDd() {
        return ctrNoDd;
    }

    public void setCtrNoDd(String ctrNoDd) {
        this.ctrNoDd = ctrNoDd;
    }

    public List<SelectItem> getCtrNoList() {
        return ctrNoList;
    }

    public void setCtrNoList(List<SelectItem> ctrNoList) {
        this.ctrNoList = ctrNoList;
    }

    public String getFaceVal() {
        return faceVal;
    }

    public void setFaceVal(String faceVal) {
        this.faceVal = faceVal;
    }

    public String getFdrVal() {
        return fdrVal;
    }

    public void setFdrVal(String fdrVal) {
        this.fdrVal = fdrVal;
    }

    public String getIntOptVal() {
        return intOptVal;
    }

    public void setIntOptVal(String intOptVal) {
        this.intOptVal = intOptVal;
    }

    public String getMatDtVal() {
        return matDtVal;
    }

    public void setMatDtVal(String matDtVal) {
        this.matDtVal = matDtVal;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPurDtVal() {
        return purDtVal;
    }

    public void setPurDtVal(String purDtVal) {
        this.purDtVal = purDtVal;
    }

    public String getRecIntVal() {
        return recIntVal;
    }

    public void setRecIntVal(String recIntVal) {
        this.recIntVal = recIntVal;
    }

    public String getRoiVal() {
        return roiVal;
    }

    public void setRoiVal(String roiVal) {
        this.roiVal = roiVal;
    }

    public String getSellerNmVal() {
        return sellerNmVal;
    }

    public void setSellerNmVal(String sellerNmVal) {
        this.sellerNmVal = sellerNmVal;
    }

    public String getTotAmtVal() {
        return totAmtVal;
    }

    public void setTotAmtVal(String totAmtVal) {
        this.totAmtVal = totAmtVal;
    }

    public RemRecInt() {
        try {
            remoteInterface = (InvestmentMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            loadctrlno();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void loadctrlno() {
        try {
            ctrNoList = new ArrayList<SelectItem>();
            List<Integer> ctrlList = new ArrayList<Integer>();
            ctrlList = remoteInterface.getCtrlNoFromInvestmentFdrDetails();
            for (int i = 0; i < ctrlList.size(); i++) {
                String ctrlN = ctrlList.get(i).toString();
                ctrNoList.add(new SelectItem(ctrlN));
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void ctrNoLostFocus() {
        try {
            Integer ctrNo = Integer.parseInt(this.getCtrNoDd());
            Object[] dataArray = remoteInterface.getDetailDataOfCtrlNo(ctrNo);
            if (dataArray.length > 0) {
                this.setFdrVal(dataArray[0].toString());
                this.setPurDtVal(dmyformatter.format(dataArray[1]).toString());
                this.setMatDtVal(dmyformatter.format(dataArray[2]).toString());
                this.setFaceVal(formatter.format(dataArray[5]));
                this.setBookVal(formatter.format(dataArray[6]));
                this.setSellerNmVal(dataArray[3].toString());
                this.setRoiVal(dataArray[8].toString());
                this.setIntOptVal(dataArray[4].toString());
                this.setBrVal(dataArray[7].toString());
                this.setTotAmtVal(formatter.format(dataArray[9]));
                this.setRecIntVal(formatter.format(dataArray[10]));              
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void clrRecInt() {

        double dblTotAmtIntRec = 0.0;
        String msg = "";
        try {
            if (this.getTotAmtVal().equals("0")) {
                this.setMessage("Process can not be completed");
                return;
            }

            if (this.getRecIntVal().equals("0")) {
                this.setMessage("Process can not be completed");
                return;
            }

            if (this.getCtrNoDd().equals("")) {
                this.setMessage("Please select Control No");
                return;
            }

            Integer ctrNo = Integer.parseInt(this.getCtrNoDd());

            dblTotAmtIntRec = Double.parseDouble(this.getTotAmtVal()) - Double.parseDouble(this.getRecIntVal());
            msg = remoteInterface.updateFdrDates(dblTotAmtIntRec, ctrNo);
            if (msg.equalsIgnoreCase("true")) {
                this.setMessage("Process Completed Successfully");
            } else {
                this.setMessage("Process Not Completed");
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void ClearAll() {
        this.setMessage("");
        this.setCtrNoDd("");
        this.setFdrVal("");
        this.setPurDtVal("");
        this.setMatDtVal("");
        this.setFaceVal("");
        this.setBookVal("");
        this.setSellerNmVal("");
        this.setRoiVal("");
        this.setIntOptVal("");
        this.setBrVal("");
        this.setTotAmtVal("");
        this.setRecIntVal("");
    }

    public String exitButton() {
        ClearAll();
        return "case1";
    }
}