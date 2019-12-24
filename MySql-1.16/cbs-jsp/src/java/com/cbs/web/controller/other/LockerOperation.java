/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.other.LockerManagementFacadeRemote;
import com.cbs.facade.report.LockerReportFacadeRemote;
import com.cbs.facade.txn.OtherAuthorizationManagementFacadeRemote;
import com.cbs.pojo.FolioLedgerPojo;
import com.cbs.utils.Base64;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;

/**
 *
 * @author Administrator
 */
public class LockerOperation extends BaseBean {

    private String message;
    private String fnDetails;
    private List<SelectItem> fnDetailsList;
    private String lockerType;
    private List<SelectItem> lockerTypeList;
    private String lockerNoStyle = "";
    private String lockerNoStyle1 = "none";
    private boolean disableInTime;
    private String cabNo;
    private List<SelectItem> cabList;
    private String lockerNo;
    private String tmpLockerNo;
    private String lockerNoDropDown;
    private List<SelectItem> lockerNoDropDownList;
    private String accountName;
    private String openDate;
    private String keyNo;
    private String opMode;
    private String attachedAcNo;
    private String lTrnDate;
    private BigDecimal balance;
    private String lOpDate;
    private String rPaidDt;
    private BigDecimal curRent;
    private BigDecimal dueRent;
    private String selectHH1;
    private List<SelectItem> selectHH1List;
    private String selectMM1;
    private List<SelectItem> selectMM1List;
    private String selectSS1;
    private List<SelectItem> selectSS1List;
    private String selectHH2;
    private List<SelectItem> selectHH2List;
    private String selectMM2;
    private List<SelectItem> selectMM2List;
    private String selectSS2;
    private List<SelectItem> selectSS2List;
    private String date;
    String imageData;
    private List<FolioLedgerPojo> tableList;
    private List<FolioLedgerPojo> tableList1;
    private String opOperName;
    private String rDueDt;
    private String bntButton = "Save";
    private String focusId;
    private final String jndiHomeName = "LockerManagementFacade";
    private LockerManagementFacadeRemote beanRemote = null;
    private final String jndiHomeNameOth = "OtherAuthorizationManagementFacade";
    private OtherAuthorizationManagementFacadeRemote otherAuthRemote = null;
    LockerReportFacadeRemote beanReportRemote;
    private NumberFormat integerFormat = new DecimalFormat("0");
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymdms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    NumberFormat formatter = new DecimalFormat("#.##");

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLockerType() {
        return lockerType;
    }

    public void setLockerType(String lockerType) {
        this.lockerType = lockerType;
    }

    public List<SelectItem> getLockerTypeList() {
        return lockerTypeList;
    }

    public void setLockerTypeList(List<SelectItem> lockerTypeList) {
        this.lockerTypeList = lockerTypeList;
    }

    public String getCabNo() {
        return cabNo;
    }

    public void setCabNo(String cabNo) {
        this.cabNo = cabNo;
    }

    public List<SelectItem> getCabList() {
        return cabList;
    }

    public void setCabList(List<SelectItem> cabList) {
        this.cabList = cabList;
    }

    public String getLockerNo() {
        return lockerNo;
    }

    public void setLockerNo(String lockerNo) {
        this.lockerNo = lockerNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public String getKeyNo() {
        return keyNo;
    }

    public void setKeyNo(String keyNo) {
        this.keyNo = keyNo;
    }

    public String getOpMode() {
        return opMode;
    }

    public void setOpMode(String opMode) {
        this.opMode = opMode;
    }

    public String getAttachedAcNo() {
        return attachedAcNo;
    }

    public void setAttachedAcNo(String attachedAcNo) {
        this.attachedAcNo = attachedAcNo;
    }

    public String getlTrnDate() {
        return lTrnDate;
    }

    public void setlTrnDate(String lTrnDate) {
        this.lTrnDate = lTrnDate;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getlOpDate() {
        return lOpDate;
    }

    public void setlOpDate(String lOpDate) {
        this.lOpDate = lOpDate;
    }

    public String getrPaidDt() {
        return rPaidDt;
    }

    public void setrPaidDt(String rPaidDt) {
        this.rPaidDt = rPaidDt;
    }

    public BigDecimal getCurRent() {
        return curRent;
    }

    public void setCurRent(BigDecimal curRent) {
        this.curRent = curRent;
    }

    public BigDecimal getDueRent() {
        return dueRent;
    }

    public void setDueRent(BigDecimal dueRent) {
        this.dueRent = dueRent;
    }

    public String getSelectHH1() {
        return selectHH1;
    }

    public void setSelectHH1(String selectHH1) {
        this.selectHH1 = selectHH1;
    }

    public List<SelectItem> getSelectHH1List() {
        return selectHH1List;
    }

    public void setSelectHH1List(List<SelectItem> selectHH1List) {
        this.selectHH1List = selectHH1List;
    }

    public String getSelectMM1() {
        return selectMM1;
    }

    public void setSelectMM1(String selectMM1) {
        this.selectMM1 = selectMM1;
    }

    public List<SelectItem> getSelectMM1List() {
        return selectMM1List;
    }

    public void setSelectMM1List(List<SelectItem> selectMM1List) {
        this.selectMM1List = selectMM1List;
    }

    public String getSelectSS1() {
        return selectSS1;
    }

    public void setSelectSS1(String selectSS1) {
        this.selectSS1 = selectSS1;
    }

    public List<SelectItem> getSelectSS1List() {
        return selectSS1List;
    }

    public void setSelectSS1List(List<SelectItem> selectSS1List) {
        this.selectSS1List = selectSS1List;
    }

    public String getSelectHH2() {
        return selectHH2;
    }

    public void setSelectHH2(String selectHH2) {
        this.selectHH2 = selectHH2;
    }

    public List<SelectItem> getSelectHH2List() {
        return selectHH2List;
    }

    public void setSelectHH2List(List<SelectItem> selectHH2List) {
        this.selectHH2List = selectHH2List;
    }

    public String getSelectMM2() {
        return selectMM2;
    }

    public void setSelectMM2(String selectMM2) {
        this.selectMM2 = selectMM2;
    }

    public List<SelectItem> getSelectMM2List() {
        return selectMM2List;
    }

    public void setSelectMM2List(List<SelectItem> selectMM2List) {
        this.selectMM2List = selectMM2List;
    }

    public String getSelectSS2() {
        return selectSS2;
    }

    public void setSelectSS2(String selectSS2) {
        this.selectSS2 = selectSS2;
    }

    public List<SelectItem> getSelectSS2List() {
        return selectSS2List;
    }

    public void setSelectSS2List(List<SelectItem> selectSS2List) {
        this.selectSS2List = selectSS2List;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public List<FolioLedgerPojo> getTableList() {
        return tableList;
    }

    public void setTableList(List<FolioLedgerPojo> tableList) {
        this.tableList = tableList;
    }

    public String getOpOperName() {
        return opOperName;
    }

    public void setOpOperName(String opOperName) {
        this.opOperName = opOperName;
    }

    public String getrDueDt() {
        return rDueDt;
    }

    public void setrDueDt(String rDueDt) {
        this.rDueDt = rDueDt;
    }

    public List<FolioLedgerPojo> getTableList1() {
        return tableList1;
    }

    public void setTableList1(List<FolioLedgerPojo> tableList1) {
        this.tableList1 = tableList1;
    }

    public String getFnDetails() {
        return fnDetails;
    }

    public void setFnDetails(String fnDetails) {
        this.fnDetails = fnDetails;
    }

    public List<SelectItem> getFnDetailsList() {
        return fnDetailsList;
    }

    public void setFnDetailsList(List<SelectItem> fnDetailsList) {
        this.fnDetailsList = fnDetailsList;
    }

    public String getLockerNoDropDown() {
        return lockerNoDropDown;
    }

    public void setLockerNoDropDown(String lockerNoDropDown) {
        this.lockerNoDropDown = lockerNoDropDown;
    }

    public List<SelectItem> getLockerNoDropDownList() {
        return lockerNoDropDownList;
    }

    public void setLockerNoDropDownList(List<SelectItem> lockerNoDropDownList) {
        this.lockerNoDropDownList = lockerNoDropDownList;
    }

    public String getLockerNoStyle() {
        return lockerNoStyle;
    }

    public void setLockerNoStyle(String lockerNoStyle) {
        this.lockerNoStyle = lockerNoStyle;
    }

    public String getLockerNoStyle1() {
        return lockerNoStyle1;
    }

    public void setLockerNoStyle1(String lockerNoStyle1) {
        this.lockerNoStyle1 = lockerNoStyle1;
    }

    public String getBntButton() {
        return bntButton;
    }

    public void setBntButton(String bntButton) {
        this.bntButton = bntButton;
    }

    public String getTmpLockerNo() {
        return tmpLockerNo;
    }

    public void setTmpLockerNo(String tmpLockerNo) {
        this.tmpLockerNo = tmpLockerNo;
    }

    public boolean isDisableInTime() {
        return disableInTime;
    }

    public void setDisableInTime(boolean disableInTime) {
        this.disableInTime = disableInTime;
    }

    public String getFocusId() {
        return focusId;
    }

    public void setFocusId(String focusId) {
        this.focusId = focusId;
    }

    public LockerOperation() {
        try {
            beanRemote = (LockerManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            beanReportRemote = (LockerReportFacadeRemote) ServiceLocator.getInstance().lookup("LockerReportFacade");
            otherAuthRemote = (OtherAuthorizationManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameOth);
            tableList = new ArrayList<FolioLedgerPojo>();
            lockerTypeCombo();
            date = sdf.format(new Date());
            selectHH1List = new ArrayList<SelectItem>();
            selectHH2List = new ArrayList<SelectItem>();
            selectMM1List = new ArrayList<SelectItem>();
            selectMM2List = new ArrayList<SelectItem>();
            selectSS1List = new ArrayList<SelectItem>();
            selectSS1List.add(new SelectItem("AM"));
            selectSS1List.add(new SelectItem("PM"));
            selectSS2List = new ArrayList<SelectItem>();
            selectSS2List.add(new SelectItem("AM"));
            selectSS2List.add(new SelectItem("PM"));

            fnDetailsList = new ArrayList<>();
            fnDetailsList.add(new SelectItem("S", "--Select--"));
            fnDetailsList.add(new SelectItem("A", "ADD (In)"));
            fnDetailsList.add(new SelectItem("U", "UPDATE (Out)"));

            for (Integer i = 0; i <= 12; i++) {
                String hour = i.toString();
                int length = hour.length();
                if (length < 2) {
                    hour = "0" + hour;
                }
                selectHH1List.add(new SelectItem(hour));
                selectHH2List.add(new SelectItem(hour));
            }
            for (Integer j = 0; j <= 60; j++) {
                String minute = j.toString();
                int length = minute.length();
                if (length < 2) {
                    minute = "0" + minute;
                }
                selectMM1List.add(new SelectItem(minute));
                selectMM2List.add(new SelectItem(minute));
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void setFnOption() {
        setMessage("");

        if (fnDetails.equalsIgnoreCase("A")) {

            this.lockerNoStyle = "";
            this.lockerNoStyle1 = "none";
            this.setBntButton("Save");

            this.focusId = "txtLockerNo";

            this.disableInTime = false;
            lockerNoDropDownList = new ArrayList<>();

            cabList = new ArrayList<SelectItem>();
            this.setLockerNo("");
            this.setAccountName("");
            this.setOpenDate("");
            this.setKeyNo("");
            this.setOpMode("");
            this.setAttachedAcNo("");
            this.setlTrnDate("");
            this.setBalance(new BigDecimal("0"));
            this.setlOpDate("");
            this.setrPaidDt("");
            this.setCurRent(new BigDecimal("0"));
            this.setDueRent(new BigDecimal("0"));
            tableList = new ArrayList<FolioLedgerPojo>();
            tableList1 = new ArrayList<FolioLedgerPojo>();
            this.setLockerType("--Select--");
            this.setMessage("");
            this.setSelectHH1("00");
            this.setSelectMM1("00");
            this.setSelectSS1("AM");
            this.setSelectHH2("00");
            this.setSelectMM2("00");
            this.setSelectSS2("AM");
            this.setOpOperName("");
            this.setrDueDt("");
            imageData = null;
            this.tmpLockerNo = "";

        } else {

            this.lockerNoStyle = "none";
            this.lockerNoStyle1 = "";
            this.setBntButton("Update");

            this.focusId = "ddlockerNolist";

            this.disableInTime = true;
            lockerNoDropDownList = new ArrayList<>();


            cabList = new ArrayList<SelectItem>();
            this.setLockerNo("");
            this.setAccountName("");
            this.setOpenDate("");
            this.setKeyNo("");
            this.setOpMode("");
            this.setAttachedAcNo("");
            this.setlTrnDate("");
            this.setBalance(new BigDecimal("0"));
            this.setlOpDate("");
            this.setrPaidDt("");
            this.setCurRent(new BigDecimal("0"));
            this.setDueRent(new BigDecimal("0"));
            tableList = new ArrayList<FolioLedgerPojo>();
            tableList1 = new ArrayList<FolioLedgerPojo>();
            this.setLockerType("--Select--");
            this.setMessage("");
            this.setSelectHH1("00");
            this.setSelectMM1("00");
            this.setSelectSS1("AM");
            this.setSelectHH2("00");
            this.setSelectMM2("00");
            this.setSelectSS2("AM");
            this.setOpOperName("");
            this.setrDueDt("");
            imageData = null;
            this.tmpLockerNo = "";


        }
    }

    public void lockerTypeCombo() {
        try {
            List resultList = new ArrayList();
            resultList = beanRemote.lockerTypeOnLoad(getOrgnBrCode());
            lockerTypeList = new ArrayList<SelectItem>();
            lockerTypeList.add(new SelectItem("--Select--"));
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                for (Object ee : ele) {
                    lockerTypeList.add(new SelectItem(ee.toString()));
                }
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void onChangeLockerType() {
        try {
            cabList = new ArrayList<SelectItem>();
            cabList.add(new SelectItem("0", "--Select--"));
            List list = beanReportRemote.getLockerCabinetNumbersByLockerType(lockerType, getOrgnBrCode());
            Vector ele = null;
            for (int i = 0; i < list.size(); i++) {
                ele = (Vector) list.get(i);
                cabList.add(new SelectItem(ele.get(0).toString(), integerFormat.format(ele.get(0))));
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void getChangeLockerNo() {
        lockerNoDropDownList = new ArrayList<>();
        if (fnDetails.equalsIgnoreCase("U")) {
            try {
                List list = beanReportRemote.getLockerNoByLockerTypeAndCabNo(lockerType, cabNo, getOrgnBrCode());
                for (int i = 0; i < list.size(); i++) {
                    Vector vtr = (Vector) list.get(i);
                    lockerNoDropDownList.add(new SelectItem(vtr.get(0).toString()));
                }
            } catch (Exception e) {
                setMessage(e.getMessage());
            }
        }
    }

    public String exitAction() {
        return "case1";
    }

    public void onChangeLockerNo() {
        List<String> resLst;
        setAccountName("");
        setOpenDate("");
        setKeyNo("");
        setOpMode("");
        setAttachedAcNo("");
        setlTrnDate("");
        setBalance(new BigDecimal("0"));
        setlOpDate("");
        setrPaidDt("");
        setCurRent(new BigDecimal("0"));
        setDueRent(new BigDecimal("0"));
        setOpOperName("");
        setrDueDt("");
        tableList = new ArrayList<FolioLedgerPojo>();
        imageData = null;
        try {
            this.tmpLockerNo = this.getLockerNo();
            if (fnDetails.equalsIgnoreCase("U")) {
                this.tmpLockerNo = lockerNoDropDown;
            }
            resLst = beanRemote.getlockerNoDetail(getOrgnBrCode(), this.getCabNo(), this.tmpLockerNo, this.getLockerType());
            if (!resLst.isEmpty()) {
                setAccountName(resLst.get(0).toString());
                setOpenDate(resLst.get(1).toString());
                setKeyNo(resLst.get(2).toString());
                setOpMode(resLst.get(3).toString());
                setAttachedAcNo(resLst.get(4).toString());
                setlTrnDate(resLst.get(7).toString());
                setBalance(new BigDecimal(formatter.format(Double.parseDouble(resLst.get(8).toString()))));
                setlOpDate(resLst.get(9).toString());
                setrPaidDt(resLst.get(5).toString());
                setCurRent(new BigDecimal(resLst.get(6).toString()));
                setDueRent(new BigDecimal(resLst.get(10).toString()));
                setOpOperName(resLst.get(11).toString());
                setrDueDt(resLst.get(12).toString());

                if (fnDetails.equalsIgnoreCase("U")) {
                    setSelectHH1(CbsUtil.lPadding(2, Integer.parseInt(resLst.get(13))));
                    setSelectMM1(CbsUtil.lPadding(2, Integer.parseInt(resLst.get(14))));
                    setSelectSS1(resLst.get(15));
                    
                    setSelectHH2(CbsUtil.lPadding(2, Integer.parseInt(resLst.get(16))));
                    setSelectMM2(CbsUtil.lPadding(2, Integer.parseInt(resLst.get(17))));
                    setSelectSS2(resLst.get(18));
                }

                getSignatureDetail();
                getGridData();
                getGridTimeData();
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void createSignature(OutputStream out, Object data) throws IOException {
        if (null == data) {
            return;
        }
        if (imageData != null) {
            byte[] sigBytes = Base64.decode(imageData);
            out.write(sigBytes);
        }
    }

    public void getSignatureDetail() {
        try {
            String signature;
            signature = otherAuthRemote.getMergSignatureDetails(this.getAttachedAcNo());
            if (!signature.equalsIgnoreCase("Signature not found")) {
                String imageCode = signature.trim();
                String directoryPath = CbsUtil.getSigFilePath(imageCode.substring(4), imageCode.substring(0, 4));
                String encryptAcno = CbsUtil.encryptText(this.getAttachedAcNo());
                String filePath = directoryPath + encryptAcno + ".xml";
                imageData = CbsUtil.readImageFromXMLFile(new File(filePath));
            } else {
                imageData = null;
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void getGridData() {
        try {
            tableList = new ArrayList<FolioLedgerPojo>();
            List reportList = new ArrayList();
            reportList = beanRemote.getLockerDetail(this.getAttachedAcNo());
            if (reportList.size() <= 0) {
            } else {
                for (int i = 0; i < reportList.size(); i++) {
                    Vector tableVector = (Vector) reportList.get(i);
                    FolioLedgerPojo mt = new FolioLedgerPojo();
                    mt.setFolioNo(tableVector.get(0).toString());
                    mt.setCustName(tableVector.get(1).toString());
                    mt.setCrPbNo(tableVector.get(2).toString());
                    mt.setpNo(tableVector.get(3).toString());
                    mt.setFatherName(tableVector.get(4).toString());
                    tableList.add(mt);
                }
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void getGridTimeData() {
        try {
            tableList1 = new ArrayList<FolioLedgerPojo>();
            List timedetailList = new ArrayList();
            timedetailList = beanRemote.getLockerTimeDetail(this.getAttachedAcNo());
            if (timedetailList.size() > 0) {
                for (int i = 0; i < timedetailList.size(); i++) {
                    Vector vec = (Vector) timedetailList.get(i);
                    FolioLedgerPojo mt1 = new FolioLedgerPojo();
                    mt1.setInTime(vec.get(0).toString());
                    mt1.setOutTime(vec.get(1).toString());
                    mt1.setDateOfOperation(vec.get(2).toString());
                    tableList1.add(mt1);
                }
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void resetAction() {
        this.fnDetails = "S";
        cabList = new ArrayList<SelectItem>();
        this.setLockerNo("");
        this.setAccountName("");
        this.setOpenDate("");
        this.setKeyNo("");
        this.setOpMode("");
        this.setAttachedAcNo("");
        this.setlTrnDate("");
        this.setBalance(new BigDecimal("0"));
        this.setlOpDate("");
        this.setrPaidDt("");
        this.setCurRent(new BigDecimal("0"));
        this.setDueRent(new BigDecimal("0"));
        tableList = new ArrayList<FolioLedgerPojo>();
        tableList1 = new ArrayList<FolioLedgerPojo>();
        this.setLockerType("--Select--");
        this.setMessage("");
        this.setSelectHH1("00");
        this.setSelectMM1("00");
        this.setSelectSS1("AM");
        this.setSelectHH2("00");
        this.setSelectMM2("00");
        this.setSelectSS2("AM");
        this.setOpOperName("");
        this.setrDueDt("");
        imageData = null;

        this.setBntButton("Save");
        this.lockerNoStyle = "";
        this.lockerNoStyle1 = "none";
        this.disableInTime = false;
        this.tmpLockerNo = "";
    }

    public void saveAction() {
        try {
            setMessage("");
            if (validation().equalsIgnoreCase("False")) {
                return;
            }
            String inTime = "";
            String outTime = "";
            String dt1[] = getTodayDate().split("/");
            String frmDate = dt1[2] + "-" + dt1[1] + "-" + dt1[0];

            if (selectSS1.equalsIgnoreCase("AM")) {
                inTime = frmDate + " " + selectHH1 + ":" + selectMM1 + ":00";
            } else {
                int hour;
                if (Integer.parseInt(selectHH1) == 12) {
                    hour = Integer.parseInt(selectHH1) + 0;
                } else {
                    hour = Integer.parseInt(selectHH1) + 12;
                }
                inTime = frmDate + " " + hour + ":" + selectMM1 + ":00";
            }

            if (selectSS2.equalsIgnoreCase("AM")) {
                outTime = frmDate + " " + selectHH2 + ":" + selectMM2 + ":00";
            } else {
                int hour;
                if (Integer.parseInt(selectHH2) == 12) {
                    hour = Integer.parseInt(selectHH2) + 0;
                } else {
                    hour = Integer.parseInt(selectHH2) + 12;
                }
                outTime = frmDate + " " + hour + ":" + selectMM2 + ":00";
            }

            if (ymdms.parse(inTime).after(ymdms.parse(outTime)) || ymdms.parse(inTime).equals(ymdms.parse(outTime))) {
                this.setMessage("In Time Can't Be Greater or Equal Out Time");
                return;
            }

            String msg = beanRemote.SaveData(getFnDetails(), attachedAcNo, Integer.parseInt(tmpLockerNo), Double.parseDouble(cabNo), lockerType, Double.parseDouble(keyNo),
                    accountName, getUserName(), inTime, outTime, getOrgnBrCode());

            setMessage(msg);
            if (msg.equalsIgnoreCase("Data Saved Successfully") || msg.equalsIgnoreCase("Data update Successfully")) {
                cabList = new ArrayList<SelectItem>();
                this.setLockerNo("");
                this.setAccountName("");
                this.setOpenDate("");
                this.setKeyNo("");
                this.setOpMode("");
                this.setAttachedAcNo("");
                this.setlTrnDate("");
                this.setBalance(new BigDecimal("0"));
                this.setlOpDate("");
                this.setrPaidDt("");
                this.setCurRent(new BigDecimal("0"));
                this.setDueRent(new BigDecimal("0"));
                tableList1 = new ArrayList<FolioLedgerPojo>();
                tableList = new ArrayList<FolioLedgerPojo>();
                this.setLockerType("--Select--");
                this.setSelectHH1("00");
                this.setSelectMM1("00");
                this.setSelectSS1("AM");
                this.setSelectHH2("00");
                this.setSelectMM2("00");
                this.setSelectSS2("AM");
                this.setOpOperName("");
                this.setrDueDt("");
                imageData = null;
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String validation() {
        try {
            String msg = "";
            if (this.lockerType.equalsIgnoreCase("--Select--")) {
                msg = "Please Select LockerType";
                setMessage(msg);
                return "False";
            }

            if (this.cabNo.equalsIgnoreCase("--Select--") || this.cabNo.equalsIgnoreCase("")) {
                msg = "Please Select Cabinet Number";
                setMessage(msg);
                return "False";
            }

            if (this.tmpLockerNo == null || this.tmpLockerNo.equalsIgnoreCase("")) {
                msg = "Please Fill Locker Number";
                setMessage(msg);
                return "False";
            }

            if (this.selectHH1.equalsIgnoreCase("00")) {
                msg = "Please Fill HH Of In Time";
                setMessage(msg);
                return "False";
            }

            if (this.selectHH2.equalsIgnoreCase("00")) {
                msg = "Please Fill HH Of Out Time";
                setMessage(msg);
                return "False";
            }
            return "true";
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
            return "false";
        }
    }
}