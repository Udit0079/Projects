
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.ho.share.DividendCalculationFacadeRemote;
import com.cbs.facade.other.PrintFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Spellar;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.print.SiplExporter;
import com.cbs.web.print.SiplPage;
import com.cbs.web.print.SiplText;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author root
 */
public class FDPrinting extends BaseBean {

    private String message;
    private boolean printFlag;
    private String oldAcno, acNoLen;
    private Float rtNo;
    private BigDecimal faceValue;
    private String custName;
    private BigDecimal matAmt;
    private String tdMadeDt;
    private String fdDate;
    private String matDate;
    private String period;
    private Float roi;
    private String jtName;
    private String acType;
    private String fileNameToPrnt;
    private final String jndiHomeName = "PrintManagementFacade";
    private PrintFacadeRemote beanRemote = null;
    private final String ftsPostingJndiName = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsBeanRemote = null;
    private String newAcno;
    private DividendCalculationFacadeRemote remote;
    private Float receiptNo;
    private String opMode;
    private String addr;
    private String dob;
    private int diff;

    private List<String> fdDetailsList;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isPrintFlag() {
        return printFlag;
    }

    public void setPrintFlag(boolean printFlag) {
        this.printFlag = printFlag;
    }

    public Float getRtNo() {
        return rtNo;
    }

    public void setRtNo(Float rtNo) {
        this.rtNo = rtNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public BigDecimal getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(BigDecimal faceValue) {
        this.faceValue = faceValue;
    }

    public String getFdDate() {
        return fdDate;
    }

    public void setFdDate(String fdDate) {
        this.fdDate = fdDate;
    }

    public BigDecimal getMatAmt() {
        return matAmt;
    }

    public void setMatAmt(BigDecimal matAmt) {
        this.matAmt = matAmt;
    }

    public String getMatDate() {
        return matDate;
    }

    public void setMatDate(String matDate) {
        this.matDate = matDate;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Float getRoi() {
        return roi;
    }

    public void setRoi(Float roi) {
        this.roi = roi;
    }

    public String getTdMadeDt() {
        return tdMadeDt;
    }

    public void setTdMadeDt(String tdMadeDt) {
        this.tdMadeDt = tdMadeDt;
    }

    public String getFileNameToPrnt() {
        return fileNameToPrnt;
    }

    public void setFileNameToPrnt(String fileNameToPrnt) {
        this.fileNameToPrnt = fileNameToPrnt;
    }

    public Float getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(Float receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public String getJtName() {
        return jtName;
    }

    public void setJtName(String jtName) {
        this.jtName = jtName;
    }

    public String getOpMode() {
        return opMode;
    }

    public void setOpMode(String opMode) {
        this.opMode = opMode;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

    /**
     * Creates a new instance of FDPrinting
     */
    public FDPrinting() {
        try {
            beanRemote = (PrintFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            ftsBeanRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(ftsPostingJndiName);
            this.setAcNoLen(getAcNoLength());
            refreshForm();
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void getFDDetails() {
        try {
            if (beanRemote.isNewPrinting("FD PRINT")) {
                getNewFDDetails();
            } else {
                getOldFDDetails();
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void getNewFDDetails() {
        fdDetailsList = new ArrayList<String>();
        this.setMessage("");
        try {
            if (this.oldAcno == null || this.oldAcno.equalsIgnoreCase("")) {
                this.setMessage("Please fill account number.");
                return;
                //} else if (this.oldAcno.length() < 12) {
            } else if (!this.oldAcno.equalsIgnoreCase("") && ((this.oldAcno.length() != 12) && (this.oldAcno.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                this.setMessage("Please fill proper account number.");
                return;
            } else if (this.rtNo == null || this.rtNo == 0.0f) {
                this.setMessage("Please fill Voucher No.");
                return;
            }
            fdDetailsList = beanRemote.getNewFdDetails(this.newAcno, this.rtNo);

            this.setFaceValue(new BigDecimal(fdDetailsList.get(9)));
            this.setCustName(fdDetailsList.get(1));
            this.setTdMadeDt(fdDetailsList.get(6));
            this.setFdDate(fdDetailsList.get(7));
            this.setMatDate(fdDetailsList.get(8));

            String prd = fdDetailsList.get(12);
            this.setPeriod(prd.trim());
            this.setRoi(Float.parseFloat(fdDetailsList.get(11)));
            this.setReceiptNo(Float.parseFloat(fdDetailsList.get(15)));

            this.setJtName(fdDetailsList.get(2));
            this.setAcType(fdDetailsList.get(5));
            this.setOpMode(fdDetailsList.get(16));
            this.setAddr(fdDetailsList.get(20));
            this.setDob(fdDetailsList.get(4));

            this.setMatAmt(new BigDecimal(fdDetailsList.get(17)));
            this.printFlag = false;
        } catch (Exception e) {
            refreshForm();
            this.setMessage(e.getMessage());
        }
    }

    public void getOldFDDetails() {
        this.setMessage("");
        try {
            if (this.oldAcno == null || this.oldAcno.equalsIgnoreCase("")) {
                this.setMessage("Please fill account number.");
                return;
                //} else if (this.oldAcno.length() < 12) {
            } else if (!this.oldAcno.equalsIgnoreCase("") && ((this.oldAcno.length() != 12) && (this.oldAcno.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                this.setMessage("Please fill proper account number.");
                return;
            } else if (this.rtNo == null || this.rtNo == 0.0f) {
                this.setMessage("Please fill Voucher No.");
                return;
            }
            List fdDetails = beanRemote.getFDDetails(this.newAcno, this.rtNo);
            if (fdDetails.size() > 0) {
                Vector element = (Vector) fdDetails.get(0);
                this.setFaceValue(new BigDecimal(element.get(0).toString()));
                this.setCustName(element.get(1).toString());
                this.setTdMadeDt(element.get(2).toString());
                this.setFdDate(element.get(3).toString());
                this.setMatDate(element.get(4).toString());
                String prd = element.get(8).toString() + "/" + element.get(7).toString() + "/" + element.get(6).toString();
                this.setPeriod(prd.trim());
                this.setRoi(Float.parseFloat(element.get(9).toString()));
                this.setReceiptNo(Float.parseFloat(element.get(11).toString()));
                String jName = "";
                if (!(element.get(12).toString().equalsIgnoreCase(""))) {
                    jName = element.get(12).toString();
                }
                if (!(element.get(13).toString().equalsIgnoreCase(""))) {
                    jName = jName + " / " + element.get(13).toString();
                }
                if (!(element.get(14).toString().equalsIgnoreCase(""))) {
                    jName = jName + " / " + element.get(14).toString();
                }
                if (!(element.get(15).toString().equalsIgnoreCase(""))) {
                    jName = jName + " / " + element.get(15).toString();
                }
                this.setJtName(jName);
                this.setAcType(element.get(16).toString());
                this.setOpMode(element.get(17).toString());
                this.setAddr(element.get(18).toString());
                this.setDob(element.get(19).toString());
                diff = Integer.parseInt(element.get(20).toString());

                this.setMatAmt(new BigDecimal(fdDetails.get(1).toString()));
                this.printFlag = false;
            } else {
                refreshForm();
                this.setMessage("There is no data corressponding your details.");
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void fdToPrint() {
        try {
            if (beanRemote.isNewPrinting("FD PRINT")) {
                newFdToPrint();
            } else {
                oldFdToPrint();
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void newFdToPrint() {
        try {
            List paramHeader = beanRemote.getPrintingHeader("FD");
            if (paramHeader.isEmpty()) {
                throw new ApplicationException("Please fill the Header parameters");
            }
            Vector ele = (Vector) paramHeader.get(0);
            int width = Integer.parseInt(ele.get(0).toString());
            int height = Integer.parseInt(ele.get(1).toString());

            float pdfWidth = Float.parseFloat(ele.get(4).toString()) * 72;
            float pdfHeight = Float.parseFloat(ele.get(5).toString()) * 72;
            float topMargin = Float.parseFloat(ele.get(6).toString());
            float leftMargin = Float.parseFloat(ele.get(7).toString());
            float fontSize = Float.parseFloat(ele.get(8).toString());
            
            int tmpBreak = Integer.parseInt(ele.get(2).toString());
            int xOrd = Integer.parseInt(ele.get(3).toString());
            boolean lineBeak = tmpBreak == 1 ? true : false;

            List billLabelList = beanRemote.getPrintingParameters("FDL", "L");
            SiplExporter sipl = new SiplExporter(width, height, 5, 5);

            List<SiplPage> pages = new ArrayList<SiplPage>();
            SiplText text;
            SiplPage page = new SiplPage();
            for (int i = 0; i < billLabelList.size(); i++) {
                Vector element = (Vector) billLabelList.get(i);

                text = new SiplText();
                text.setX(Integer.parseInt(element.get(1).toString()));
                text.setY(Integer.parseInt(element.get(2).toString()));

                text.setHeight(8);
                text.setWidth(Integer.parseInt(element.get(3).toString()));
                text.setText(element.get(0).toString());
                page.addElement(text);
            }
            List fdValueList = beanRemote.getPrintingParameters("FD", "V");
            for (int i = 0; i < fdValueList.size(); i++) {
                Vector element = (Vector) fdValueList.get(i);

                text = new SiplText();
                text.setX(Integer.parseInt(element.get(1).toString()));
                text.setY(Integer.parseInt(element.get(2).toString()));

                text.setHeight(8);
                text.setWidth(Integer.parseInt(element.get(3).toString()));
                text.setText(fdDetailsList.get(i));
                page.addElement(text);
            }
            pages.add(page);
            sipl.setLineBreak(lineBeak);
            sipl.setNewLineX(xOrd);
            sipl.setPages(pages);
            ByteArrayOutputStream os = sipl.exportReport();
            System.out.println(os.toString());
            String msg = "";
            PrinterClass prinObj = new PrinterClass();

            if (beanRemote.isNewPrinting("PDF PRINTING")) {
                byte[] byteArr = prinObj.generatePDFStream(pdfWidth, pdfHeight,topMargin,leftMargin,fontSize, os.toString());
                msg = prinObj.printReport(pdfWidth, pdfHeight, byteArr);
            } else {
                String option = "TXT";
                if (beanRemote.isNewPrinting("NEW PRINTER")) {
                    option = "10";
                } else {
                    option = "TXT";
                }
                msg = prinObj.printReport(option, os.toByteArray());
            }
            if (msg.equalsIgnoreCase("true")) {
                this.setMessage("Print Successfull.");
            } else {
                this.setMessage("Error in Printing.");
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void oldFdToPrint() {
        int acnoTopSpace = 0, acnoLeftSpace = 0, rtNoTopSpace = 0, rtNoLeftSpace = 0, printAmtRsTopSpace = 0, printAmtRsLeftSpace = 0, printAmtRupeesTopSpace = 0, printAmtRupeesLeftSpace = 0, dobTopSpace = 0;
        int matAmtRsTopSpace = 0, matAmtRsLeftSpace = 0, matAmtRupeesTopSpace = 0, matAmtRupeesLeftSpace = 0, dateTopSpace = 0, dateLeftSpace = 0, wefTopSpace = 0, wefLeftSpace = 0, addTopSpace = 0, addLeftSpace = 0;
        int dueDateTopSpace = 0, dueDateLeftSpace = 0, periodTopSpace = 0, periodLeftSpace = 0, intRateTopSpace = 0, intRateLeftSpace = 0, custnameTopSpace = 0, custnameLeftSpace = 0;
        int acTypeTopSpace = 0, acTypeLeftSpace = 0, jtNameTopSpace = 0, jtNameLeftSpace = 0, dobLeftSpace = 0;
        int opModeTopSpace = 0, opModeLeftSpace = 0;
        try {
            String fileName = this.getOrgnBrCode() + this.getUserName() + this.receiptNo.toString().substring(0, this.receiptNo.toString().indexOf(".")) + "FD" + ".txt";
            String osName = System.getProperty("os.name");
            String createdFile = "";
            File folder;
            if (osName.equals("Linux")) {
                createdFile = "/install/billPrinting/" + fileName;
                folder = new File("/install/billPrinting");
            } else {
                createdFile = "c:\\billPrinting\\" + fileName;
                folder = new File("c:\\billPrinting");
            }
            if (!folder.exists()) {
                folder.mkdir();
            }
            File f = new File(createdFile);
            if (!f.exists()) {
                f.createNewFile();
            }
            FileWriter fstream = new FileWriter(f);
            BufferedWriter out = new BufferedWriter(fstream);
            fileNameToPrnt = f.getName().toString();

            Spellar obj = new Spellar();
            String printAmtInWords = obj.spellAmount(this.faceValue.doubleValue()).substring(6).trim();
            String prinAmt = this.faceValue.toString().substring(0, this.faceValue.toString().indexOf("."));
            String matAmtInWords = obj.spellAmount(this.matAmt.doubleValue()).substring(6).trim();
            String maturityAmt = this.matAmt.toString().substring(0, this.matAmt.toString().indexOf("."));

            List getFDResult = beanRemote.getFDValues();
            Vector vecFD = (Vector) getFDResult.get(0);

            /*FD Printing Of Jamshedpur*/
            if (Integer.parseInt(vecFD.get(0).toString()) == 1) {
                List fdParametresList = beanRemote.getBillParameters("FD");
                if (fdParametresList.size() > 0) {
                    for (int i = 0; i < fdParametresList.size(); i++) {
                        Vector element = (Vector) fdParametresList.get(i);
                        if (i == 0) {
                            acTypeTopSpace = Integer.parseInt(element.get(2).toString());
                            acTypeLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 1) {
                            dueDateTopSpace = Integer.parseInt(element.get(2).toString());
                            dueDateLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 2) {
                            acnoTopSpace = Integer.parseInt(element.get(2).toString());
                            acnoLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 3) {
                            dateTopSpace = Integer.parseInt(element.get(2).toString());
                            dateLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 4) {
                            custnameTopSpace = Integer.parseInt(element.get(2).toString());
                            custnameLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 5) {
                            jtNameTopSpace = Integer.parseInt(element.get(2).toString());
                            jtNameLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 6) {
                            printAmtRsTopSpace = Integer.parseInt(element.get(2).toString());
                            printAmtRsLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 7) {
                            printAmtRupeesTopSpace = Integer.parseInt(element.get(2).toString());
                            printAmtRupeesLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 8) {
                            periodTopSpace = Integer.parseInt(element.get(2).toString());
                            periodLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 9) {
                            intRateTopSpace = Integer.parseInt(element.get(2).toString());
                            intRateLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 10) {
                            matAmtRsTopSpace = Integer.parseInt(element.get(2).toString());
                            matAmtRsLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 11) {
                            matAmtRupeesTopSpace = Integer.parseInt(element.get(2).toString());
                            matAmtRupeesLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                    }

                    //Writing Account Type 
                    for (int i = 0; i < acTypeTopSpace; i++) {
                        out.newLine();
                    }

                    String acTpString = space(acTypeLeftSpace) + this.acType;
                    out.write(acTpString);

                    //Writing Maturity Date 
                    String dueDateString = space(dateLeftSpace - (acTypeLeftSpace + this.acType.length())) + this.matDate;
                    out.write(dueDateString);

                    //Writing acno,rtno,date
                    for (int i = 0; i < (acnoTopSpace - acTypeTopSpace); i++) {
                        out.newLine();
                    }

                    String acnoString = space(acnoLeftSpace) + this.newAcno;
                    out.write(acnoString);

                    String dateString = space(dateLeftSpace - (acnoLeftSpace + this.newAcno.length())) + this.fdDate;
                    out.write(dateString);

                    //Writing custname
                    for (int i = 0; i < (custnameTopSpace - acnoTopSpace); i++) {
                        out.newLine();
                    }

                    String custNameString = space(custnameLeftSpace) + this.custName;
                    out.write(custNameString);

                    //Writing joint Name 
                    for (int i = 0; i < (jtNameTopSpace - custnameTopSpace); i++) {
                        out.newLine();
                    }

                    String jtNameString = space(jtNameLeftSpace) + this.jtName;
                    out.write(jtNameString);

                    //Writing printAmtRs
                    for (int i = 0; i < (printAmtRsTopSpace - jtNameTopSpace); i++) {
                        out.newLine();
                    }

                    String printAmtRsString = space(printAmtRsLeftSpace) + prinAmt;
                    out.write(printAmtRsString);

                    //Writing printAmtRupees,wef                    
                    int lenTot = 50;
                    if (printAmtInWords.length() > lenTot) {
                        String remStr = printAmtInWords.substring(0, lenTot);
                        int totPos = remStr.lastIndexOf(" ");

                        String printAmtRupeesString = space(printAmtRupeesLeftSpace - (printAmtRsLeftSpace + prinAmt.length())) + printAmtInWords.substring(0, totPos);
                        out.write(printAmtRupeesString);

                        out.newLine();
                        out.newLine();
                        //7 is left space for second line printAmtRupees.
                        String printAmtRupeesSecondLineString = space(30) + printAmtInWords.substring(totPos);
                        out.write(printAmtRupeesSecondLineString);
                        out.newLine();

                    } else {
                        String printAmtRupeesString = space(printAmtRupeesLeftSpace - (printAmtRsLeftSpace + prinAmt.length())) + printAmtInWords;
                        out.write(printAmtRupeesString);
                        out.newLine();
                    }

                    //Writing period
                    for (int i = 0; i < (periodTopSpace - (printAmtRsTopSpace + 1)); i++) {
                        out.newLine();
                    }

                    String periodString = space(periodLeftSpace) + this.period;
                    out.write(periodString);

                    String intRateString = space(intRateLeftSpace - (periodLeftSpace + this.period.length())) + this.roi;
                    out.write(intRateString);

                    //Writing matAmtRs
                    for (int i = 0; i < (matAmtRsTopSpace - intRateTopSpace); i++) {
                        out.newLine();
                    }

                    String matAmtRsString = space(matAmtRsLeftSpace) + maturityAmt;
                    out.write(matAmtRsString);

                    int lenTotM = 50;
                    if (matAmtInWords.length() > lenTotM) {
                        String remStrM = matAmtInWords.substring(0, lenTotM);
                        int totPosM = remStrM.lastIndexOf(" ");

                        String matAmtRupeesString = space(matAmtRupeesLeftSpace - (matAmtRsTopSpace + maturityAmt.length())) + matAmtInWords.substring(0, totPosM);
                        out.write(matAmtRupeesString);

                        out.newLine();
                        out.newLine();
                        //7 is left space for second line printAmtRupees.
                        String matAmtRupeesSecondLineString = space(30) + matAmtInWords.substring(totPosM);
                        out.write(matAmtRupeesSecondLineString);
                        out.newLine();
                    } else {
                        String matAmtRupeesString = space(matAmtRupeesLeftSpace - (matAmtRsTopSpace + maturityAmt.length())) + matAmtInWords;
                        out.write(matAmtRupeesString);
                        out.newLine();
                    }
                    out.close();
                } else {
                    this.setMessage("Please set FD parameters");
                    return;
                }
            }

            /*FD Printing Of Dhanbad*/
            if (Integer.parseInt(vecFD.get(0).toString()) == 2) {
                List fdParametresList = beanRemote.getBillParameters("FD");
                if (fdParametresList.size() > 0) {
                    for (int i = 0; i < fdParametresList.size(); i++) {
                        Vector element = (Vector) fdParametresList.get(i);
                        if (i == 0) {
                            opModeTopSpace = Integer.parseInt(element.get(2).toString());
                            opModeLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 1) {
                            dueDateTopSpace = Integer.parseInt(element.get(2).toString());
                            dueDateLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 2) {
                            acnoTopSpace = Integer.parseInt(element.get(2).toString());
                            acnoLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 3) {
                            matAmtRsTopSpace = Integer.parseInt(element.get(2).toString());
                            matAmtRsLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 4) {
                            dateTopSpace = Integer.parseInt(element.get(2).toString());
                            dateLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 5) {
                            custnameTopSpace = Integer.parseInt(element.get(2).toString());
                            custnameLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 6) {
                            printAmtRupeesTopSpace = Integer.parseInt(element.get(2).toString());
                            printAmtRupeesLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 7) {
                            periodTopSpace = Integer.parseInt(element.get(2).toString());
                            periodLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 8) {
                            intRateTopSpace = Integer.parseInt(element.get(2).toString());
                            intRateLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 9) {
                            printAmtRsTopSpace = Integer.parseInt(element.get(2).toString());
                            printAmtRsLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                    }

                    //Writing Operation Mode
                    for (int i = 0; i < opModeTopSpace; i++) {
                        out.newLine();
                    }

                    String opModeString = space(opModeLeftSpace) + "Operation Mode   " + this.opMode;
                    out.write(opModeString);

                    //Writing Maturity Date
                    for (int i = 0; i < (dueDateTopSpace - opModeTopSpace); i++) {
                        out.newLine();
                    }

                    String dueDateString = space(dueDateLeftSpace) + this.matDate;
                    out.write(dueDateString);

                    String acnoString = space(acnoLeftSpace - (dueDateLeftSpace + this.matDate.length())) + this.newAcno;
                    out.write(acnoString);

                    //Writing acno,rtno,date
                    for (int i = 0; i < (matAmtRsTopSpace - acnoTopSpace); i++) {
                        out.newLine();
                    }

                    String matAmtRsString = space(matAmtRsLeftSpace) + maturityAmt;
                    out.write(matAmtRsString);

                    String dateString = space(dateLeftSpace - (matAmtRsLeftSpace + maturityAmt.length())) + this.tdMadeDt;
                    out.write(dateString);

                    //Writing custname
                    for (int i = 0; i < (custnameTopSpace - matAmtRsTopSpace); i++) {
                        out.newLine();
                    }

                    String custNameString = space(custnameLeftSpace) + this.custName;
                    out.write(custNameString);

                    //Writing printAmtRs
                    for (int i = 0; i < (printAmtRupeesTopSpace - custnameTopSpace); i++) {
                        out.newLine();
                        out.newLine();
                    }

                    String printAmtRsString = space(printAmtRupeesLeftSpace) + printAmtInWords;
                    out.write(printAmtRsString);

                    //Writing printAmtRs
                    for (int i = 0; i < (periodTopSpace - printAmtRupeesTopSpace); i++) {
                        out.newLine();
                    }

                    String periodString = space(periodLeftSpace) + this.period;
                    out.write(periodString);

                    //Writing printAmtRs
                    for (int i = 0; i < (intRateTopSpace - periodTopSpace); i++) {
                        out.newLine();
                    }

                    String intString = space(intRateLeftSpace) + this.roi;
                    out.write(intString);

                    for (int i = 0; i < (printAmtRsTopSpace - intRateTopSpace); i++) {
                        out.newLine();
                    }

                    String rsString = space(printAmtRsLeftSpace) + this.faceValue;
                    out.write(rsString);

                    out.close();
                } else {
                    this.setMessage("Please set FD parameters");
                    return;
                }
            }

            /*FD Printing Of Tapindu*/
            if (Integer.parseInt(vecFD.get(0).toString()) == 3) {
                List fdParametresList = beanRemote.getBillParameters("FD");
                if (fdParametresList.size() > 0) {
                    for (int i = 0; i < fdParametresList.size(); i++) {
                        Vector element = (Vector) fdParametresList.get(i);
                        if (i == 0) {
                            dueDateTopSpace = Integer.parseInt(element.get(2).toString());
                            dueDateLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 1) {
                            dateTopSpace = Integer.parseInt(element.get(2).toString());
                            dateLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 2) {
                            acnoTopSpace = Integer.parseInt(element.get(2).toString());
                            acnoLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 3) {
                            custnameTopSpace = Integer.parseInt(element.get(2).toString());
                            custnameLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 4) {
                            printAmtRupeesTopSpace = Integer.parseInt(element.get(2).toString());
                            printAmtRupeesLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 5) {
                            intRateTopSpace = Integer.parseInt(element.get(2).toString());
                            intRateLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 6) {
                            periodTopSpace = Integer.parseInt(element.get(2).toString());
                            periodLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 7) {
                            matAmtRsTopSpace = Integer.parseInt(element.get(2).toString());
                            matAmtRsLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 8) {
                            printAmtRsTopSpace = Integer.parseInt(element.get(2).toString());
                            printAmtRsLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                    }

                    for (int i = 0; i < dueDateTopSpace; i++) {
                        out.newLine();
                    }

                    String dueDateString = space(dueDateLeftSpace) + this.matDate;
                    out.write(dueDateString);

                    //Writing acno,rtno,date
                    for (int i = 0; i < (dateTopSpace - dueDateTopSpace); i++) {
                        out.newLine();
                    }

                    String dateString = space(dateLeftSpace) + this.tdMadeDt;
                    out.write(dateString);

                    for (int i = 0; i < (acnoTopSpace - dateTopSpace); i++) {
                        out.newLine();
                    }

                    String acnoString = space(acnoLeftSpace) + this.newAcno;
                    out.write(acnoString);

                    //Writing custname
                    for (int i = 0; i < (custnameTopSpace - acnoTopSpace); i++) {
                        out.newLine();
                    }

                    String custNameString = space(custnameLeftSpace) + this.custName;
                    out.write(custNameString);

                    //Writing printAmtRupees
                    for (int i = 0; i < (printAmtRupeesTopSpace - custnameTopSpace); i++) {
                        out.newLine();
                    }

                    String printAmtRupeesString = space(printAmtRupeesLeftSpace) + printAmtInWords;
                    out.write(printAmtRupeesString);

                    //Writing joint Name 
                    for (int i = 0; i < (intRateTopSpace - printAmtRupeesTopSpace); i++) {
                        out.newLine();
                    }

                    String intRateString = space(intRateLeftSpace) + this.roi;
                    out.write(intRateString);

                    //Writing period
                    for (int i = 0; i < (periodTopSpace - intRateTopSpace); i++) {
                        out.newLine();
                    }

                    int fstPos = this.period.indexOf("/");
                    int lstPos = this.period.lastIndexOf("/");

                    String periodD = this.period.substring(0, fstPos - 1);
                    String periodM = this.period.substring(fstPos + 1, lstPos - 1);
                    String periodY = this.period.substring(lstPos + 1, this.period.length());

                    String periodNew = periodD + " Days /" + periodM + " Months /" + periodY + " Years";

                    String periodString = space(periodLeftSpace) + periodNew;
                    out.write(periodString);

                    //Writing matAmtRs
                    for (int i = 0; i < (matAmtRsTopSpace - periodTopSpace); i++) {
                        out.newLine();
                    }

                    String matAmtRsString = space(matAmtRsLeftSpace) + maturityAmt;
                    out.write(matAmtRsString);

                    //Writing printAmtRs
                    for (int i = 0; i < (printAmtRsTopSpace - matAmtRsTopSpace); i++) {
                        out.newLine();
                    }

                    String printAmtRsString = space(printAmtRsLeftSpace) + prinAmt;
                    out.write(printAmtRsString);
                    out.close();
                } else {
                    this.setMessage("Please set FD parameters");
                    return;
                }
            }

            /*FD Printing Of Noida Commercial*/
            if (Integer.parseInt(vecFD.get(0).toString()) == 4) {
                List fdParametresList = beanRemote.getBillParameters("FD");
                if (fdParametresList.size() > 0) {
                    for (int i = 0; i < fdParametresList.size(); i++) {
                        Vector element = (Vector) fdParametresList.get(i);
                        if (i == 0) {
                            dueDateTopSpace = Integer.parseInt(element.get(2).toString());
                            dueDateLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 1) {
                            wefTopSpace = Integer.parseInt(element.get(2).toString());
                            wefLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 2) {
                            matAmtRsTopSpace = Integer.parseInt(element.get(2).toString());
                            matAmtRsLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 3) {
                            acnoTopSpace = Integer.parseInt(element.get(2).toString());
                            acnoLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 4) {
                            dateTopSpace = Integer.parseInt(element.get(2).toString());
                            dateLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 5) {
                            custnameTopSpace = Integer.parseInt(element.get(2).toString());
                            custnameLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 6) {
                            addTopSpace = Integer.parseInt(element.get(2).toString());
                            addLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 7) {
                            printAmtRupeesTopSpace = Integer.parseInt(element.get(2).toString());
                            printAmtRupeesLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 8) {
                            periodTopSpace = Integer.parseInt(element.get(2).toString());
                            periodLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 9) {
                            intRateTopSpace = Integer.parseInt(element.get(2).toString());
                            intRateLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 10) {
                            printAmtRsTopSpace = Integer.parseInt(element.get(2).toString());
                            printAmtRsLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                    }

                    for (int i = 0; i < dueDateTopSpace; i++) {
                        out.newLine();
                    }

                    String dueDateString = space(dueDateLeftSpace) + this.matDate;
                    out.write(dueDateString);

                    for (int i = 0; i < (wefTopSpace - dueDateTopSpace); i++) {
                        out.newLine();
                    }

                    String wefDateString = space(wefLeftSpace) + this.fdDate;
                    out.write(wefDateString);

                    for (int i = 0; i < (matAmtRsTopSpace - wefTopSpace); i++) {
                        out.newLine();
                    }

                    String matAmtRsString = space(matAmtRsLeftSpace) + maturityAmt;
                    out.write(matAmtRsString);

                    for (int i = 0; i < (acnoTopSpace - matAmtRsTopSpace); i++) {
                        out.newLine();
                    }

                    String acnoString = space(acnoLeftSpace) + this.newAcno;
                    out.write(acnoString);

                    for (int i = 0; i < (dateTopSpace - acnoTopSpace); i++) {
                        out.newLine();
                    }

                    String dateString = space(dateLeftSpace) + this.tdMadeDt;
                    out.write(dateString);

                    //Writing custname
                    for (int i = 0; i < (custnameTopSpace - dateTopSpace); i++) {
                        out.newLine();
                    }

                    String custNameString = space(custnameLeftSpace) + this.custName;
                    out.write(custNameString);

                    //Writing custname
                    for (int i = 0; i < (addTopSpace - custnameTopSpace); i++) {
                        out.newLine();
                    }

                    String addString = space(addLeftSpace) + this.addr;
                    out.write(addString);

                    //Writing printAmtRupees
                    for (int i = 0; i < (printAmtRupeesTopSpace - addTopSpace); i++) {
                        out.newLine();
                    }

                    String printAmtRupeesString = space(printAmtRupeesLeftSpace) + printAmtInWords;
                    out.write(printAmtRupeesString);

                    //Writing period
                    for (int i = 0; i < (periodTopSpace - printAmtRupeesTopSpace); i++) {
                        out.newLine();
                    }

                    int fstPos = this.period.indexOf("/");
                    int lstPos = this.period.lastIndexOf("/");

                    String periodD = this.period.substring(0, fstPos - 1);
                    String periodM = this.period.substring(fstPos + 1, lstPos - 1);
                    String periodY = this.period.substring(lstPos + 1, this.period.length());

                    String periodNew = periodD + " Days /" + periodM + " Months /" + periodY + " Years";

                    String periodString = space(periodLeftSpace) + periodNew;
                    out.write(periodString);

                    for (int i = 0; i < (intRateTopSpace - periodTopSpace); i++) {
                        out.newLine();
                    }

                    String intRateString = space(intRateLeftSpace) + this.roi;
                    out.write(intRateString);

                    //Writing printAmtRs
                    for (int i = 0; i < (printAmtRsTopSpace - intRateTopSpace); i++) {
                        out.newLine();
                    }

                    String printAmtRsString = space(printAmtRsLeftSpace) + prinAmt;
                    out.write(printAmtRsString);
                    out.close();
                } else {
                    this.setMessage("Please set FD parameters");
                    return;
                }
            }

            /*FD Printing Of Morena*/
            if (Integer.parseInt(vecFD.get(0).toString()) == 5) {
                List fdParametresList = beanRemote.getBillParameters("FD");
                if (fdParametresList.size() > 0) {
                    for (int i = 0; i < fdParametresList.size(); i++) {
                        Vector element = (Vector) fdParametresList.get(i);
                        if (i == 0) {
                            dateTopSpace = Integer.parseInt(element.get(2).toString());
                            dateLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 1) {
                            custnameTopSpace = Integer.parseInt(element.get(2).toString());
                            custnameLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 2) {
                            acnoTopSpace = Integer.parseInt(element.get(2).toString());
                            acnoLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 3) {
                            printAmtRupeesTopSpace = Integer.parseInt(element.get(2).toString());
                            printAmtRupeesLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 4) {
                            printAmtRsTopSpace = Integer.parseInt(element.get(2).toString());
                            printAmtRsLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 5) {
                            periodTopSpace = Integer.parseInt(element.get(2).toString());
                            periodLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 6) {
                            intRateTopSpace = Integer.parseInt(element.get(2).toString());
                            intRateLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 7) {
                            dueDateTopSpace = Integer.parseInt(element.get(2).toString());
                            dueDateLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 8) {
                            matAmtRsTopSpace = Integer.parseInt(element.get(2).toString());
                            matAmtRsLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                    }

                    for (int i = 0; i < dateTopSpace; i++) {
                        out.newLine();
                    }

                    String dateString = space(dateLeftSpace) + this.tdMadeDt;
                    out.write(dateString);

                    //Writing custname
                    for (int i = 0; i < (custnameTopSpace - dateTopSpace); i++) {
                        out.newLine();
                    }

                    if (this.custName.length() <= 30) {
                        String custNameString = space(custnameLeftSpace) + this.custName + space(acnoLeftSpace - (custnameLeftSpace + this.custName.length())) + this.newAcno;;
                        out.write(custNameString);
                        if (!jtName.equalsIgnoreCase("")) {
                            out.newLine();
                            String jtNameString = space(5) + this.jtName;
                            out.write(jtNameString);
                        }
                    } else {
                        int lenTotM = 30;
                        String arr3 = this.custName;
                        String remStr1 = "", remStr2 = "";
                        if (arr3.length() > lenTotM) {
                            String remStrM = arr3.substring(0, lenTotM);
                            int totPosM = remStrM.lastIndexOf(" ");
                            remStr1 = remStrM.substring(0, totPosM);
                            remStr2 = arr3.substring(totPosM + 1);
                        }

                        String custNameString = space(custnameLeftSpace) + remStr1 + space(acnoLeftSpace - (custnameLeftSpace + remStr1.length())) + this.newAcno;;
                        out.write(custNameString);

                        out.newLine();
                        out.newLine();
                        if (!jtName.equalsIgnoreCase("")) {
                            String custNameString1 = space(5) + remStr2 + "/" + this.jtName;
                            out.write(custNameString1);
                        } else {
                            String custNameString1 = space(5) + remStr2;
                            out.write(custNameString1);
                        }
                    }

                    //Writing printAmtRs
                    if (!jtName.equalsIgnoreCase("")) {
                        for (int i = 0; i < (printAmtRupeesTopSpace - (acnoTopSpace + 1)); i++) {
                            out.newLine();
                        }
                    } else {
                        for (int i = 0; i < (printAmtRupeesTopSpace - acnoTopSpace); i++) {
                            out.newLine();
                        }
                    }

                    String printAmtRsString = space(printAmtRupeesLeftSpace) + prinAmt;
                    out.write(printAmtRsString);

                    //Writing printAmtRupees
                    for (int i = 0; i < (printAmtRsTopSpace - printAmtRupeesTopSpace); i++) {
                        out.newLine();
                    }

                    int fstPos = this.period.indexOf("/");
                    int lstPos = this.period.lastIndexOf("/");

                    String periodD = this.period.substring(0, fstPos - 1);
                    String periodM = this.period.substring(fstPos + 1, lstPos - 1);
                    String periodY = this.period.substring(lstPos + 1, this.period.length());

                    String periodNew = periodD + "   " + periodM + "    " + periodY;

                    if (printAmtInWords.length() <= 46) {
                        String printAmtRupeesString = space(printAmtRsLeftSpace) + printAmtInWords + space(periodLeftSpace - (printAmtRsLeftSpace + printAmtInWords.length())) + periodNew;
                        out.write(printAmtRupeesString);
                    } else {
                        int lenTotM = 46;
                        String arr4 = printAmtInWords;
                        String remSt1 = "", remSt2 = "";
                        if (arr4.length() > lenTotM) {
                            String remStM = arr4.substring(0, lenTotM);
                            int totPoM = remStM.lastIndexOf(" ");
                            remSt1 = remStM.substring(0, totPoM);
                            remSt2 = arr4.substring(totPoM + 1);
                        }

                        String printAmtRupeesString = space(printAmtRsLeftSpace) + remSt1 + space(periodLeftSpace - (printAmtRsLeftSpace + remSt1.length())) + periodNew;
                        out.write(printAmtRupeesString);

                        out.newLine();

                        String printAmtRupeesString1 = space(5) + remSt2;
                        out.write(printAmtRupeesString1);
                    }

                    for (int i = 0; i < (intRateTopSpace - printAmtRsTopSpace); i++) {
                        out.newLine();
                    }

                    String intRateString = space(intRateLeftSpace) + this.roi;
                    out.write(intRateString);

                    for (int i = 0; i < (dueDateTopSpace - intRateTopSpace); i++) {
                        out.newLine();
                    }

                    String dueDateString = space(dueDateLeftSpace) + this.matDate;
                    out.write(dueDateString);

                    for (int i = 0; i < (matAmtRsTopSpace - dueDateTopSpace); i++) {
                        out.newLine();
                    }

                    String matAmtRsString = space(matAmtRsLeftSpace) + maturityAmt;
                    out.write(matAmtRsString);
                    out.close();
                } else {
                    this.setMessage("Please set FD parameters");
                    return;
                }
            }

            /*FD Printing Of Budaun*/
            if (Integer.parseInt(vecFD.get(0).toString()) == 6) {
                List fdParametresList = beanRemote.getBillParameters("FD");
                if (fdParametresList.size() > 0) {
                    for (int i = 0; i < fdParametresList.size(); i++) {
                        Vector element = (Vector) fdParametresList.get(i);
                        if (i == 0) {
                            acnoTopSpace = Integer.parseInt(element.get(2).toString());
                            acnoLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 1) {
                            dateTopSpace = Integer.parseInt(element.get(2).toString());
                            dateLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 2) {
                            custnameTopSpace = Integer.parseInt(element.get(2).toString());
                            custnameLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 3) {
                            printAmtRupeesTopSpace = Integer.parseInt(element.get(2).toString());
                            printAmtRupeesLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 4) {
                            printAmtRsTopSpace = Integer.parseInt(element.get(2).toString());
                            printAmtRsLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 5) {
                            matAmtRsTopSpace = Integer.parseInt(element.get(2).toString());
                            matAmtRsLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 6) {
                            periodTopSpace = Integer.parseInt(element.get(2).toString());
                            periodLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 7) {
                            intRateTopSpace = Integer.parseInt(element.get(2).toString());
                            intRateLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 8) {
                            dueDateTopSpace = Integer.parseInt(element.get(2).toString());
                            dueDateLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 9) {
                            dobTopSpace = Integer.parseInt(element.get(2).toString());
                            dobLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                    }

                    for (int i = 0; i < acnoTopSpace; i++) {
                        out.newLine();
                    }

                    String acString = space(40) + this.getTodayDate() + space(acnoLeftSpace - (40 + this.getTodayDate().length())) + this.newAcno + space(dateLeftSpace - (acnoLeftSpace)) + this.tdMadeDt;
                    out.write(acString);

                    //Writing custname
                    for (int i = 0; i < (custnameTopSpace - acnoTopSpace); i++) {
                        out.newLine();
                    }

                    String custNameString = space(custnameLeftSpace) + this.custName;
                    out.write(custNameString);

                    //Writing printAmtRs
                    for (int i = 0; i < (printAmtRupeesTopSpace - custnameTopSpace); i++) {
                        out.newLine();
                    }

                    String printAmtRsString = space(printAmtRupeesLeftSpace) + printAmtInWords;
                    out.write(printAmtRsString);

                    //Writing printAmtRs
                    for (int i = 0; i < (printAmtRsTopSpace - printAmtRupeesTopSpace); i++) {
                        out.newLine();
                    }

                    int fstPos = this.period.indexOf("/");
                    int lstPos = this.period.lastIndexOf("/");

                    String periodD = this.period.substring(0, fstPos - 1);
                    String periodM = this.period.substring(fstPos + 1, lstPos - 1);
                    String periodY = this.period.substring(lstPos + 1, this.period.length());

                    String periodNew = periodD + "Days/" + periodM + "Months/" + periodY + "Years";

                    String printAllString = space(printAmtRsLeftSpace) + prinAmt + space(matAmtRsLeftSpace - printAmtRsLeftSpace) + matAmt + space(periodLeftSpace - matAmtRsLeftSpace) + periodNew + space(intRateLeftSpace - periodLeftSpace) + roi + space(dueDateLeftSpace - intRateLeftSpace) + matDate;
                    out.write(printAllString);

                    int sts = diff / 365;
                    if (sts < 18) {
                        for (int i = 0; i < (dobTopSpace - printAmtRsTopSpace); i++) {
                            out.newLine();
                        }

                        String dobString = space(dobLeftSpace) + this.dob.substring(6) + "/" + this.dob.substring(4, 6) + "/" + this.dob.substring(0, 4);
                        out.write(dobString);
                    }
                    out.close();
                } else {
                    this.setMessage("Please set FD parameters");
                    return;
                }
            }

            /**
             * ********************************************************************************************
             * Code to call the Print Method to ptint the text file generated.
             * /*********************************************************************************************
             */
            PrinterClass printerObj = new PrinterClass();
            String printingStatusResult = printerObj.printingStatus(fileNameToPrnt);
            if (printingStatusResult.equalsIgnoreCase("true")) {
                f.delete();
                this.setMessage("Print Successfull.");
            } else {
                this.setMessage(printingStatusResult);
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void onLostFocus() {
        try {
            this.setMessage("");
            if (this.oldAcno == null || this.oldAcno.equalsIgnoreCase("")) {
                refreshForm();
                this.setMessage("Please fill account number.");
                return;
                //} else if (this.oldAcno.length() < 12) {
            } else if (!this.oldAcno.equalsIgnoreCase("") && ((this.oldAcno.length() != 12) && (this.oldAcno.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                refreshForm();
                this.setMessage("Please fill proper account number.");
                return;
            }
            String result = ftsBeanRemote.getNewAccountNumber(oldAcno);
            if (result.startsWith("A/c")) {
                refreshForm();
                setMessage(result);
            } else {
                newAcno = result;
            }
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public String space(int alignmentValue) {
        String result = "";
        try {
            for (int p = 0; p < alignmentValue; p++) {
                result = result + " ";
            }
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
        return result;
    }

    public void refreshForm() {
        try {
            this.setMessage("");
            this.setOldAcno("");
            this.setNewAcno("");
            this.setRtNo(0.0f);
            this.setReceiptNo(0.0f);
            this.setFaceValue(new BigDecimal("0.0"));
            this.setCustName("");
            this.setMatAmt(new BigDecimal("0.0"));
            this.setTdMadeDt("");
            this.setFdDate("");
            this.setMatDate("");
            this.setPeriod("");
            this.setRoi(0.0f);
            this.setAddr("");
            this.printFlag = true;
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitForm() {
        try {
            refreshForm();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }
}
