/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.other.PrintFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Spellar;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.pojo.other.Billpojo;
import com.cbs.web.print.SiplExporter;
import com.cbs.web.print.SiplPage;
import com.cbs.web.print.SiplText;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class BillPrinting extends BaseBean {

    private String message;
    private List<SelectItem> billTypeList;
    private String billType;
    private String instNo;
    private String seqNo;
    private String custName;
    private BigDecimal amount;
    private String inFavourOf;
    private String issueDt;
    private boolean printFlag;
    private String fileNameToPrnt;
    private String payableAt;
    Spellar obj = new Spellar();
    private final String jndiHomeName = "PrintManagementFacade";
    private PrintFacadeRemote beanRemote = null;
    // private CommonReportMethodsRemote commonFacade = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public List<SelectItem> getBillTypeList() {
        return billTypeList;
    }

    public void setBillTypeList(List<SelectItem> billTypeList) {
        this.billTypeList = billTypeList;
    }

    public String getInstNo() {
        return instNo;
    }

    public void setInstNo(String instNo) {
        this.instNo = instNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getInFavourOf() {
        return inFavourOf;
    }

    public void setInFavourOf(String inFavourOf) {
        this.inFavourOf = inFavourOf;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getIssueDt() {
        return issueDt;
    }

    public void setIssueDt(String issueDt) {
        this.issueDt = issueDt;
    }

    public boolean isPrintFlag() {
        return printFlag;
    }

    public void setPrintFlag(boolean printFlag) {
        this.printFlag = printFlag;
    }

    public String getFileNameToPrnt() {
        return fileNameToPrnt;
    }

    public void setFileNameToPrnt(String fileNameToPrnt) {
        this.fileNameToPrnt = fileNameToPrnt;
    }

    public String getPayableAt() {
        return payableAt;
    }

    public void setPayableAt(String payableAt) {
        this.payableAt = payableAt;
    }

    /**
     * Creates a new instance of BillPrinting
     */
    public BillPrinting() {
        try {
            //commonFacade = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            beanRemote = (PrintFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            billTypeList = new ArrayList<SelectItem>();
            billTypeList.add(new SelectItem("0", "--Select--"));

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            this.setTodayDate(sdf.format(date));
            this.setIssueDt(sdf.format(date));
            refreshFormForOnLoad();
            getOnLoadData();
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void getOnLoadData() {
        try {
            List dataList = beanRemote.getOnloadData();
            if (dataList.size() > 0) {
                for (int i = 0; i < dataList.size(); i++) {
                    Vector element = (Vector) dataList.get(i);
                    billTypeList.add(new SelectItem(element.get(0).toString(), element.get(0).toString()));
                }
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void getBillDetails() {
        this.setMessage("");
        String ogrnAlphaCode = "";
        try {
            if (this.billType.equalsIgnoreCase("0") || this.billType.equalsIgnoreCase("")) {
                this.setMessage("Please select billtype.");
                return;
            }
            if (this.instNo == null || this.instNo.equalsIgnoreCase("")) {
                this.setMessage("Please fill bill number.");
                return;
            }
            if (this.issueDt == null || this.issueDt.equalsIgnoreCase("")) {
                this.setMessage("Please fill issue date.");
                return;
            }
            String issueDate = this.issueDt.substring(6) + this.issueDt.substring(3, 5) + this.issueDt.substring(0, 2);
            List alphaCodeList = beanRemote.getAlphaCode(this.getOrgnBrCode());
            if (alphaCodeList.isEmpty()) {
                this.setMessage("There is not a corresponding branch");
                return;
            }

            Vector ele = (Vector) alphaCodeList.get(0);
            ogrnAlphaCode = ele.get(0).toString();
            String billNat = beanRemote.getBillNature(billType);
            List list = new ArrayList();
            if (billNat.equalsIgnoreCase("PO")) {
                list = beanRemote.getPODetail(instNo, issueDate, ogrnAlphaCode);
            } else if (billNat.equalsIgnoreCase("DD")) {
                //list = beanRemote.getDDDetail(instNo, issueDate, ogrnAlphaCode);
                list = beanRemote.getDDDetail(instNo, issueDate, this.getOrgnBrCode());
            } else if (this.billType.equalsIgnoreCase("AD")) {
                System.out.println("In AD printing");
            }
            if (list.isEmpty()) {
                refreshForm();
                this.setMessage("There is no data corressponding your details.");
                return;
            }
            Vector element = (Vector) list.get(0);
            this.setSeqNo(element.get(0).toString());
            this.setCustName(element.get(1).toString());

            this.setAmount(new BigDecimal(element.get(2).toString()));
            this.setInFavourOf(element.get(3).toString());
            this.setPayableAt(element.get(4).toString());
            printFlag = false;
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void newBillToPrint() {
        try {
            List paramHeader = beanRemote.getPrintingHeader(this.billType);
            if (paramHeader.isEmpty()) {
                throw new ApplicationException("Please fill the Header parameters");
            }
            Vector ele = (Vector) paramHeader.get(0);
            int width = Integer.parseInt(ele.get(0).toString());
            int height = Integer.parseInt(ele.get(1).toString());

            int tmpBreak = Integer.parseInt(ele.get(2).toString());
            int xOrd = Integer.parseInt(ele.get(3).toString());
            boolean lineBeak = tmpBreak == 1 ? true : false;

            float pdfWidth = Float.parseFloat(ele.get(4).toString()) * 72;
            float pdfHeight = Float.parseFloat(ele.get(5).toString()) * 72;
            float topMargin = Float.parseFloat(ele.get(6).toString());
            
            float leftMargin = Float.parseFloat(ele.get(7).toString());
            float fontSize = Float.parseFloat(ele.get(8).toString());
            
            DecimalFormat numFormat = new DecimalFormat("#.00");

            SiplExporter sipl = new SiplExporter(width, height, 5, 5);
            List<SiplPage> pages = new ArrayList<SiplPage>();
            SiplText text;
            SiplPage page = new SiplPage();

            List billLabelList = beanRemote.getPrintingParameters(this.billType, "L");
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

            String amtInWords = obj.spellAmount(this.amount.doubleValue()).substring(6).trim();
            List billParamsList = beanRemote.getPrintingParameters(this.billType, "V");
            String dateString = "";

            if (billParamsList.isEmpty()) {
                throw new ApplicationException("Please fill parameters from Bill Print parameter");
            }
            for (int i = 0; i < billParamsList.size(); i++) {
                Vector element = (Vector) billParamsList.get(i);

                text = new SiplText();
                text.setX(Integer.parseInt(element.get(1).toString()));
                text.setY(Integer.parseInt(element.get(2).toString()));

                text.setHeight(10);
                text.setWidth(Integer.parseInt(element.get(3).toString()));

                if (i == 0) {
                    if (text.getWidth() == 10) {
                        dateString = issueDt;
                    } else if (text.getWidth() == 15) {
                        dateString = this.issueDt.substring(0, 1) + " " + this.issueDt.substring(1, 2)
                                + " " + this.issueDt.substring(3, 4) + " " + this.issueDt.substring(4, 5) + " " + this.issueDt.substring(6, 7)
                                + " " + this.issueDt.substring(7, 8) + " " + this.issueDt.substring(8, 9) + " " + this.issueDt.substring(9, 10);
                    } else {
                        dateString = this.issueDt.substring(0, 1) + " " + this.issueDt.substring(1, 2)
                                + "  " + this.issueDt.substring(3, 4) + " " + this.issueDt.substring(4, 5) + "  " + this.issueDt.substring(6, 7)
                                + " " + this.issueDt.substring(7, 8) + "  " + this.issueDt.substring(8, 9) + " " + this.issueDt.substring(9, 10);
                    }
                    text.setText(dateString);
                }
                if (i == 1) {
                    text.setText(inFavourOf.toUpperCase());
                }
                if (i == 2) {
                    text.setText(amtInWords.toUpperCase());
                }
                if (i == 3) {
                    text.setText(numFormat.format(amount.doubleValue()));
                }
                if (i == 4) {
                    text.setText(seqNo);
                }
                if (i == 5) {
                    text.setText(payableAt.toUpperCase());
                }
                if (i == 6) {
                    text.setText(custName.toUpperCase());
                }
                page.addElement(text);
            }
            pages.add(page);
            sipl.setPages(pages);
            sipl.setLineBreak(lineBeak);
            sipl.setNewLineX(xOrd);
            ByteArrayOutputStream os = sipl.exportReport();
            System.out.println(os.toString());
            String msg = "";
            PrinterClass obj = new PrinterClass();
            
            if (beanRemote.isNewPrinting("PDF PRINTING")) {
                byte[] byteArr = obj.generatePDFStream(pdfWidth, pdfHeight,topMargin,leftMargin, fontSize, os.toString());
                msg = obj.printReport(pdfWidth, pdfHeight, byteArr);
            } else {
                String option = "TXT";
                if (beanRemote.isNewPrinting("NEW PRINTER")) {
                    option = "10";
                } else {
                    option = "TXT";
                }
                msg = obj.printReport(option, os.toByteArray());
            }
            if (msg.equalsIgnoreCase("true")) {
                this.setMessage("Print Successfull.");
            } else {
                this.setMessage("Error in Printing.");
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void billToPrint() {
        try {
            if (beanRemote.isNewPrinting("BILL PRINT")) {
                newBillToPrint();
            } else {
                oldBillToPrint();
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void oldBillToPrint() {
        int dateTopSpace = 0, dateLeftSpace = 0, payTopSpace = 0, payLeftSpace = 0, rupeesTopSpace = 0, rupeesLeftSpace = 0, rsTopSpace = 0, rsLeftSpace = 0, notOverRsTopSpace = 0, notOverRsLeftSpace = 0;
        int payableAtParTopSpace = 0, payableAtParLeftSpace = 0;
        try {
            String fileName = this.getOrgnBrCode() + this.getUserName() + this.instNo + this.billType + ".txt";
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

            String amtInWords = obj.spellAmount(this.amount.doubleValue()).substring(6).trim();
            String amtToPrintInRs = amount.toString().substring(0, amount.toString().indexOf("."));
            if (this.billType.equalsIgnoreCase("PO")) {

                List getPODtResult = beanRemote.getPODtValues();
                Vector vecPODt = (Vector) getPODtResult.get(0);

                List getPOResult = beanRemote.getPOValues();
                Vector vecPO = (Vector) getPOResult.get(0);

                List billParamsList = beanRemote.getBillParameters("PO");

                if (Integer.parseInt(vecPODt.get(0).toString()) == 1) {
                    if (billParamsList.size() > 0) {
                        for (int i = 0; i < billParamsList.size(); i++) {
                            Vector element = (Vector) billParamsList.get(i);
                            if (i == 0) {
                                dateTopSpace = Integer.parseInt(element.get(2).toString());
                                dateLeftSpace = Integer.parseInt(element.get(3).toString());
                            }
                            if (i == 1) {
                                payTopSpace = Integer.parseInt(element.get(2).toString());
                                payLeftSpace = Integer.parseInt(element.get(3).toString());
                            }
                            if (i == 2) {
                                rupeesTopSpace = Integer.parseInt(element.get(2).toString());
                                rupeesLeftSpace = Integer.parseInt(element.get(3).toString());
                            }
                            if (i == 3) {
                                rsTopSpace = Integer.parseInt(element.get(2).toString());
                                rsLeftSpace = Integer.parseInt(element.get(3).toString());
                            }
//                        if (i == 4) {
//                            notOverRsTopSpace = Integer.parseInt(element.get(2).toString());
//                            notOverRsLeftSpace = Integer.parseInt(element.get(3).toString());
//                        }
                        }
                        //Printing PayOrder File
                        for (int i = 0; i < dateTopSpace; i++) {
                            out.newLine();
                        }

                        if (Integer.parseInt(vecPODt.get(0).toString()) == 1) {
                            //Writing Date

                            //String dateString = space(dateLeftSpace) + this.issueDt.substring(0, 10);
                            String dateString = space(dateLeftSpace) + this.issueDt.substring(0, 1) + "  " + this.issueDt.substring(1, 2)
                                    + "  " + this.issueDt.substring(3, 4) + "  " + this.issueDt.substring(4, 5) + "   " + this.issueDt.substring(6, 7)
                                    + "  " + this.issueDt.substring(7, 8) + "   " + this.issueDt.substring(8, 9) + "  " + this.issueDt.substring(9, 10);
                            out.write(dateString);
                        } else {
                            //Writing Date
                            String dateString = space(dateLeftSpace) + this.issueDt.substring(0, 10);
                            out.write(dateString);
                        }

                        //Writing Pay
                        for (int i = 0; i < (payTopSpace - dateTopSpace); i++) {
                            out.newLine();
                        }
                        String payString = space(payLeftSpace) + this.inFavourOf;
                        out.write(payString);

                        int spaceRs = (rsLeftSpace - rupeesLeftSpace);

                        //Writing Rupees
                        for (int i = 0; i < (rupeesTopSpace - payTopSpace); i++) {
                            out.newLine();
                        }

                        if (amtInWords.length() >= spaceRs) {
                            String rupeesString = amtInWords.substring(0, spaceRs);
                            int spRs = rupeesString.lastIndexOf(" ");

                            String rupeesStr = space(rupeesLeftSpace) + amtInWords.substring(0, spRs);
                            out.write(rupeesStr);

                            String rsString = space(rsLeftSpace - (rupeesLeftSpace + spRs)) + amtToPrintInRs;
                            out.write(rsString);

                            out.newLine();

                            String rupeesString1 = space(rupeesLeftSpace - 20) + amtInWords.substring(spRs + 1);
                            out.write(rupeesString1);

                        } else {
                            String rupeesString = space(rupeesLeftSpace) + amtInWords;
                            out.write(rupeesString);

                            out.newLine();
                            String rsString = space(rsLeftSpace) + amtToPrintInRs;
                            out.write(rsString);
                        }
                        out.close();
                    } else {
                        this.setMessage("Please set bill parametrs");
                        return;
                    }
                } else {
                    if (billParamsList.size() > 0) {
                        for (int i = 0; i < billParamsList.size(); i++) {
                            Vector element = (Vector) billParamsList.get(i);
                            if (i == 0) {
                                dateTopSpace = Integer.parseInt(element.get(2).toString());
                                dateLeftSpace = Integer.parseInt(element.get(3).toString());
                            }
                            if (i == 1) {
                                payTopSpace = Integer.parseInt(element.get(2).toString());
                                payLeftSpace = Integer.parseInt(element.get(3).toString());
                            }
                            if (i == 2) {
                                rupeesTopSpace = Integer.parseInt(element.get(2).toString());
                                rupeesLeftSpace = Integer.parseInt(element.get(3).toString());
                            }
                            if (i == 3) {
                                rsTopSpace = Integer.parseInt(element.get(2).toString());
                                rsLeftSpace = Integer.parseInt(element.get(3).toString());
                            }
//                        if (i == 4) {
//                            notOverRsTopSpace = Integer.parseInt(element.get(2).toString());
//                            notOverRsLeftSpace = Integer.parseInt(element.get(3).toString());
//                        }
                        }
                        //Printing PayOrder File
                        for (int i = 0; i < dateTopSpace; i++) {
                            out.newLine();
                        }

                        if (Integer.parseInt(vecPODt.get(0).toString()) == 1) {
                            //Writing Date

                            //String dateString = space(dateLeftSpace) + this.issueDt.substring(0, 10);
                            String dateString = space(dateLeftSpace) + this.issueDt.substring(0, 1) + "  " + this.issueDt.substring(1, 2)
                                    + "  " + this.issueDt.substring(3, 4) + "  " + this.issueDt.substring(4, 5) + "   " + this.issueDt.substring(6, 7)
                                    + "  " + this.issueDt.substring(7, 8) + "   " + this.issueDt.substring(8, 9) + "  " + this.issueDt.substring(9, 10);
                            out.write(dateString);
                        } else {
                            //Writing Date
                            String dateString = space(dateLeftSpace) + this.issueDt.substring(0, 10);
                            out.write(dateString);
                        }

                        //Writing Pay
                        for (int i = 0; i < (payTopSpace - dateTopSpace); i++) {
                            out.newLine();
                        }
                        String payString = space(payLeftSpace) + this.inFavourOf;
                        out.write(payString);

                        int spaceRs = (rsLeftSpace - rupeesLeftSpace);

                        //Writing Rupees
                        for (int i = 0; i < (rupeesTopSpace - payTopSpace); i++) {
                            out.newLine();
                        }

                        String rupeesString = space(rupeesLeftSpace) + amtInWords;
                        out.write(rupeesString);

                        out.newLine();
                        out.newLine();
                        out.newLine();
                        String rsString = space(rsLeftSpace) + amtToPrintInRs;
                        out.write(rsString);

                        out.close();
                    } else {
                        this.setMessage("Please set bill parametrs");
                        return;
                    }
                }

                /**
                 * ********************************************************************************************
                 * Code to call the Print Method to ptint the text file
                 * generated.
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
                /**
                 * **********end here***************
                 */
            }
            if (this.billType.equalsIgnoreCase("HDFCDD") || this.billType.equalsIgnoreCase("DD")) {
                List getDDResult = beanRemote.getDDValues();
                Vector vecDD = (Vector) getDDResult.get(0);
                List billParamsList = beanRemote.getBillParameters("DD");
                if (billParamsList.size() > 0) {
                    for (int i = 0; i < billParamsList.size(); i++) {
                        Vector element = (Vector) billParamsList.get(i);
                        if (i == 0) {
                            dateTopSpace = Integer.parseInt(element.get(2).toString());
                            dateLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 1) {
                            payTopSpace = Integer.parseInt(element.get(2).toString());
                            payLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 2) {
                            rupeesTopSpace = Integer.parseInt(element.get(2).toString());
                            rupeesLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 3) {
                            rsTopSpace = Integer.parseInt(element.get(2).toString());
                            rsLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                        if (i == 4) {
                            payableAtParTopSpace = Integer.parseInt(element.get(2).toString());
                            payableAtParLeftSpace = Integer.parseInt(element.get(3).toString());
                        }
                    }

                    //Printing DemadDraft File
                    for (int i = 0; i < dateTopSpace; i++) {
                        out.newLine();
                    }

                    //Writing Date
                    String dateString = space(dateLeftSpace) + this.issueDt;
                    out.write(dateString);

                    //Writing Pay
                    for (int i = 0; i < (payTopSpace - dateTopSpace); i++) {
                        out.newLine();
                    }
                    String payString = space(payLeftSpace) + this.inFavourOf;
                    out.write(payString);

                    //Writing Rupees
                    for (int i = 0; i < (rupeesTopSpace - payTopSpace); i++) {
                        out.newLine();
                    }

                    if (amtInWords.length() > 50) {
                        String rupeesString = amtInWords.substring(0, 50);

                        int spRs = rupeesString.lastIndexOf(" ");

                        String rupeesStr = space(rupeesLeftSpace) + amtInWords.substring(0, spRs);
                        out.write(rupeesStr);

                        String rsString = space(rsLeftSpace - (rupeesLeftSpace + spRs)) + amtToPrintInRs;
                        out.write(rsString);

                        out.newLine();
                        out.newLine();

                        String rupeesString1 = space(rupeesLeftSpace - 14) + amtInWords.substring(spRs + 1);
                        out.write(rupeesString1);
                    } else {
                        String rupeesString = space(rupeesLeftSpace) + amtInWords;
                        out.write(rupeesString);

                        out.newLine();

                        String rsString = space(rsLeftSpace) + amtToPrintInRs;
                        out.write(rsString);
                    }

                    //Writing Payable At Par
                    for (int i = 0; i < (payableAtParTopSpace - rsTopSpace); i++) {
                        out.newLine();
                    }

                    if (Integer.parseInt(vecDD.get(0).toString()) == 1) {
                        String payableAtParString = space(payableAtParLeftSpace) + this.payableAt;
                        out.write(payableAtParString);
                    }

                    out.close();

                    /**
                     * ********************************************************************************************
                     * Code to call the Print Method to ptint the text file
                     * generated.
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
                    /**
                     * **********end here***************
                     */
                } else {
                    this.setMessage("Please set bill parametrs");
                    return;
                }
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public String viewData() {
        try {
            List<Billpojo> billpojos = new ArrayList<Billpojo>();
            Map fillParams = new HashMap();
            Billpojo row = new Billpojo();
            row.setAmt(amount.toString());
            row.setAmtToWords(obj.spellAmount(new Double(amount.toString())));
            row.setFavourof(inFavourOf);
            row.setPaybellat(payableAt);
            billpojos.add(row);
            new ReportBean().jasperReport("payorder", "text/html", new JRBeanCollectionDataSource(billpojos), fillParams, "Pay Order / Demand Draft");
            //((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            return "report";
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
        return null;
    }

    public String space(int alignmentValue) {
        String result = "";
        try {
            for (int p = 0; p < alignmentValue; p++) {
                result = result + " ";
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return result;
    }

    public void refreshForm() {
        try {
            this.setMessage("");
            this.setBillType("0");
            this.setInstNo("");
            this.setIssueDt(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
            this.setSeqNo("");
            this.setCustName("");
            this.setAmount(new BigDecimal("0.0"));
            this.setInFavourOf("");
            this.setPayableAt("");
            printFlag = true;
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void refreshFormForOnLoad() {
        try {
            this.setMessage("");
            this.setBillType("0");
            this.setInstNo("");
            this.setSeqNo("");
            this.setCustName("");
            this.setAmount(new BigDecimal("0.0"));
            this.setInFavourOf("");
            this.setPayableAt("");
            printFlag = true;
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
