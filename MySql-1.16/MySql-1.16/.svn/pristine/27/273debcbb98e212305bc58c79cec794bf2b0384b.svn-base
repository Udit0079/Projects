/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.dto.NpciFileDto;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.other.NpciMgmtFacadeRemote;
import com.cbs.facade.other.NpciYojnaMgmtFacadeRemote;
import com.cbs.facade.other.OtherNpciMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;

public class NpciController extends BaseBean {

    private String function;
    private String errorMessage;
    private String fileGenerationDt;
    private String btnValue;
    private String confirmText;
    private String seqEnable = "none";
    private String seqNo;
    private NpciFileDto currentItem;
    private List<SelectItem> functionList;
    private List<SelectItem> seqNoList;
    private List<NpciFileDto> gridDetail;
    private NpciMgmtFacadeRemote npciFacade = null;
    private CommonReportMethodsRemote reportRemote = null;
    private OtherNpciMgmtFacadeRemote otherNpciRemote;
    private FtsPostingMgmtFacadeRemote ftsRemote = null;
    private NpciYojnaMgmtFacadeRemote yojnaRemote = null;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public NpciController() {
        try {
            npciFacade = (NpciMgmtFacadeRemote) ServiceLocator.getInstance().lookup("NpciMgmtFacade");
            reportRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            otherNpciRemote = (OtherNpciMgmtFacadeRemote) ServiceLocator.getInstance().lookup("OtherNpciMgmtFacade");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            yojnaRemote = (NpciYojnaMgmtFacadeRemote) ServiceLocator.getInstance().lookup("NpciYojnaMgmtFacade");
            btnRefreshAction();
            initData();
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void initData() {
        try {
            functionList = new ArrayList<SelectItem>();
//            functionList.add(new SelectItem("GM", "Generate Mapper Files"));    //Aadhar Mapper
//            functionList.add(new SelectItem("SM", "Show Mapper Files"));
//            functionList.add(new SelectItem("GR", "Generate Return Files"));    //APBS Inward
//            functionList.add(new SelectItem("SR", "Show Return Files"));
//            functionList.add(new SelectItem("GA", "Generate ACH Return Files")); //ACH-CR-DBT,PFM,Normal
//            functionList.add(new SelectItem("SA", "Show ACH Return Files"));
//            functionList.add(new SelectItem("GE", "Generate ECS Return Files"));    //ECS Credit Inward
//            functionList.add(new SelectItem("SE", "Show ECS Return Files"));
//            functionList.add(new SelectItem("GO", "Generate OAC Return"));
//            functionList.add(new SelectItem("SO", "Show OAC Return"));
//            functionList.add(new SelectItem("GD", "Generate ECR DR Return")); //Old Ecs-Dr of 50 Character
//            functionList.add(new SelectItem("SD", "Show ECR DR Return"));
//            functionList.add(new SelectItem("G306", "Generate ECS 306 Return")); //ACH-CR, ECS of 306 Character
//            functionList.add(new SelectItem("S306", "Show ECS 306 Return"));
//            functionList.add(new SelectItem("GD306", "Generate ACH Debit Return")); //ACH DR of 306 Character
//            functionList.add(new SelectItem("SD306", "Show ACH Debit Return"));
//            functionList.add(new SelectItem("GENRUPAY", "Generate Rupay Return")); //Generate Rupay(CDR/CWR)
//            functionList.add(new SelectItem("SHOWRUPAY", "Show Rupay Return")); //Show Rupay
//            functionList.add(new SelectItem("GENAEPS", "Generate AEPS Return")); //Generate AEPS(CDA/CWA)
//            functionList.add(new SelectItem("SHOWAEPS", "Show AEPS Return")); //Show AEPS

            List list = reportRemote.getRefRecList("404");
            if (list.isEmpty()) {
                this.setErrorMessage("Please define master for code 404.");
                return;
            }
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                functionList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
            }
            seqNoList = new ArrayList<SelectItem>();
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void onChangeFunction() {
        this.setErrorMessage("");
        try {
            if (this.function == null || this.function.equals("")) {
                this.setErrorMessage("Please select function.");
                return;
            }
//            if (this.function.equals("GM")) {
//                this.setBtnValue("Generate Mapper File");
//            } else if (this.function.equals("SM")) {
//                this.setBtnValue("Show Mapper File");
//            } else if (this.function.equals("GR")) {
//                this.setBtnValue("Generate Return File");
//            } else if (this.function.equals("SR")) {
//                this.setBtnValue("Show Return File");
//            } else if (this.function.equals("GA")) {
//                this.setBtnValue("Generate ACH Return Files");
//            } else if (this.function.equals("SA")) {
//                this.setBtnValue("Show ACH Return Files");
//            } else if (this.function.equals("GE")) {
//                this.setBtnValue("Generate ECS Return Files");
//            } else if (this.function.equals("SE")) {
//                this.setBtnValue("Show ECS Return Files");
//            } else if (this.function.equals("GO")) {
//                this.setBtnValue("Generate OAC Return");
//            } else if (this.function.equals("SO")) {
//                this.setBtnValue("Show OAC Return");
//            } else if (this.function.equals("GD")) {
//                this.setBtnValue("Generate ECS DR Return");
//            } else if (this.function.equals("SD")) {
//                this.setBtnValue("Show ECS DR Return");
//            } else if (this.function.equals("G306")) {
//                this.setBtnValue("Generate ECS 306 Return");
//            } else if (this.function.equals("S306")) {
//                this.setBtnValue("Show ECS 306 Return");
//            } else if (this.function.equals("GD306")) {
//                this.setBtnValue("Generate ACH Debit Return");
//            } else if (this.function.equals("SD306")) {
//                this.setBtnValue("Show ACH Debit Return");
//            } else if (this.function.equals("GENRUPAY")) {
//                this.setBtnValue("Generate Rupay Return");
//            } else if (this.function.equals("SHOWRUPAY")) {
//                this.setBtnValue("Show Rupay Return");
//            } else if (this.function.equals("GENAEPS")) {
//                this.setBtnValue("Generate AEPS Return");
//            } else if (this.function.equals("SHOWAEPS")) {
//                this.setBtnValue("Show AEPS Return");
//            }

            if (this.function.toUpperCase().substring(0, 1).equalsIgnoreCase("G")) {
                this.setBtnValue("Generate");
            } else if (this.function.toUpperCase().substring(0, 1).equalsIgnoreCase("S")) {
                this.setBtnValue("Show");
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void dateAction() {
        this.setErrorMessage("");
        try {
            seqNoList = new ArrayList<SelectItem>();
            this.seqEnable = "none";
            if (validateField()) {
                if (this.function.equalsIgnoreCase("GR") || this.function.equalsIgnoreCase("SR") //APB Inward Return
                        || this.function.equalsIgnoreCase("GA") || this.function.equalsIgnoreCase("SA") //ACH Inward Return 
                        || this.function.equalsIgnoreCase("GE") || this.function.equalsIgnoreCase("SE")//ECS Inward Return 
                        || this.function.equalsIgnoreCase("GO")//OAC Return File
                        || this.function.equalsIgnoreCase("GD")//ECS DR return
                        || this.function.equalsIgnoreCase("GD306") //ACH-DR-306 (Previous ECS DR)
                        || this.function.equalsIgnoreCase("G306") || this.function.equalsIgnoreCase("S306")//ECS 306 Inward Return 
                        || this.function.equalsIgnoreCase("GENRUPAY") || this.function.equalsIgnoreCase("SHOWRUPAY")//Rupay
                        || this.function.equalsIgnoreCase("GENAEPS") || this.function.equalsIgnoreCase("SHOWAEPS")) { //AEPS
                    this.seqEnable = "";
                    seqNoList.add(new SelectItem(""));
                    List list = null;
                    if (this.function.equalsIgnoreCase("GR") || this.function.equalsIgnoreCase("SR")) { //APB Inward Return
                        list = npciFacade.getSeqNoForSettlementDate(this.fileGenerationDt, "APB");
                    } else if (this.function.equalsIgnoreCase("GA") || this.function.equalsIgnoreCase("SA")) { //ACH Inward Return
                        list = npciFacade.getSeqNoForSettlementDate(this.fileGenerationDt, "ACH");
                    } else if (this.function.equalsIgnoreCase("GE") || this.function.equalsIgnoreCase("SE")) { //ECS Inward Return
                        list = otherNpciRemote.findAllFileSeqNoForCreditInward(ymd.format(dmy.parse(this.fileGenerationDt)), "ECS");
                    } else if (this.function.equalsIgnoreCase("GO")) { //OAC Return File
                        list = otherNpciRemote.findAllFileSeqNo(ymd.format(dmy.parse(fileGenerationDt)), "NPCI-OAC");
                    } else if (this.function.equalsIgnoreCase("GD")) { //ECS DR Return File
                        list = otherNpciRemote.findAllFileSeqNo(ymd.format(dmy.parse(fileGenerationDt)), "ECS-DR");
                    } else if (this.function.equalsIgnoreCase("G306") || this.function.equalsIgnoreCase("S306")) { //ECS 306 Inward Return
                        list = otherNpciRemote.findAllFileSeqNoForCreditInward(ymd.format(dmy.parse(this.fileGenerationDt)), "ECS");
                    } else if (this.function.equalsIgnoreCase("GD306")) { //ACH-DR-306 (Previous ECS DR)
//                        list = otherNpciRemote.findAllFileSeqNo(ymd.format(dmy.parse(this.fileGenerationDt)), "ECS-DR");
                        list = otherNpciRemote.findAllFileSeqNo(ymd.format(dmy.parse(this.fileGenerationDt)), "ACH-DR");
                    } else if (this.function.equalsIgnoreCase("GENRUPAY") || this.function.equalsIgnoreCase("SHOWRUPAY")
                            || this.function.equalsIgnoreCase("GENAEPS") || this.function.equalsIgnoreCase("SHOWAEPS")) { //Rupay,AEPS
                        list = yojnaRemote.getYojnaSeqNoForSettlementDate(ymd.format(dmy.parse(fileGenerationDt)), this.function);
                    }

                    for (int i = 0; i < list.size(); i++) {
                        Vector ele = (Vector) list.get(i);
                        if (ele.get(0) == null) {
                            this.setErrorMessage("Seq no can not be null.");
                            return;
                        }
                        seqNoList.add(new SelectItem(ele.get(0).toString()));
                    }
                }
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void createConfirmTxt() {
        this.setErrorMessage("");
        try {
//            if (this.function.equals("GM") && this.btnValue.equalsIgnoreCase("Generate Mapper File")) {
//                this.setConfirmText("Are you sure to generate the Mapper files ?");
//            } else if (this.function.equals("SM") && this.btnValue.equalsIgnoreCase("Show Mapper File")) {
//                this.setConfirmText("Are you sure to show the Mapper files ?");
//            } else if (this.function.equals("GR") && this.btnValue.equalsIgnoreCase("Generate Return File")) {
//                this.setConfirmText("Are you sure to generate the APBS Return files ?");
//            } else if (this.function.equals("SR") && this.btnValue.equalsIgnoreCase("Show Return File")) {
//                this.setConfirmText("Are you sure to show the APBS Return files ?");
//            } else if (this.function.equals("GA") && this.btnValue.equalsIgnoreCase("Generate ACH Return Files")) {
//                this.setConfirmText("Are you sure to generate the ACH Return files ?");
//            } else if (this.function.equals("SA") && this.btnValue.equalsIgnoreCase("Show ACH Return Files")) {
//                this.setConfirmText("Are you sure to show the ACH Return files ?");
//            } else if (this.function.equals("GE") && this.btnValue.equalsIgnoreCase("Generate ECS Return Files")) {
//                this.setConfirmText("Are you sure to generate the ECS Return files ?");
//            } else if (this.function.equals("SE") && this.btnValue.equalsIgnoreCase("Show ECS Return Files")) {
//                this.setConfirmText("Are you sure to show the ECS Return files ?");
//            } else if (this.function.equals("GO") && this.btnValue.equalsIgnoreCase("Generate OAC Return")) {
//                this.setConfirmText("Are you sure to generate the OAC Return files ?");
//            } else if (this.function.equals("SO") && this.btnValue.equalsIgnoreCase("Show OAC Return")) {
//                this.setConfirmText("Are you sure to show the OAC Return files ?");
//            } else if (this.function.equals("GD") && this.btnValue.equalsIgnoreCase("Generate ECS DR Return")) {
//                this.setConfirmText("Are you sure to generate the ECS DR Return files ?");
//            } else if (this.function.equals("SD") && this.btnValue.equalsIgnoreCase("Show ECS DR Return")) {
//                this.setConfirmText("Are you sure to show the ECS DR Return files ?");
//            } else if (this.function.equals("G306") && this.btnValue.equalsIgnoreCase("Generate ECS 306 Return")) {
//                this.setConfirmText("Are you sure to generate the ECS 306 Return ?");
//            } else if (this.function.equals("S306") && this.btnValue.equalsIgnoreCase("Show ECS 306 Return")) {
//                this.setConfirmText("Are you sure to show the ECS 306 Return ?");
//            } else if (this.function.equals("GD306") && this.btnValue.equalsIgnoreCase("Generate ACH Debit Return")) {
//                this.setConfirmText("Are you sure to generate the ACH Debit Return ?");
//            } else if (this.function.equals("SD306") && this.btnValue.equalsIgnoreCase("Show ACH Debit Return")) {
//                this.setConfirmText("Are you sure to show the ACH Debit Return ?");
//            } else if (this.function.equals("GENRUPAY") && this.btnValue.equalsIgnoreCase("Generate Rupay Return")) {
//                this.setConfirmText("Are you sure to generate the RUPAY Return ?");
//            } else if (this.function.equals("SHOWRUPAY") && this.btnValue.equalsIgnoreCase("Show Rupay Return")) {
//                this.setConfirmText("Are you sure to show the RUPAY Return ?");
//            } else if (this.function.equals("GENAEPS") && this.btnValue.equalsIgnoreCase("Generate AEPS Return")) {
//                this.setConfirmText("Are you sure to generate the AEPS Return ?");
//            } else if (this.function.equals("SHOWAEPS") && this.btnValue.equalsIgnoreCase("Show AEPS Return")) {
//                this.setConfirmText("Are you sure to show the AEPS Return ?");
//            }

            if (this.function.toUpperCase().substring(0, 1).equalsIgnoreCase("G")) {
                this.setConfirmText("Are you sure to generate the files ?");
            } else if (this.function.toUpperCase().substring(0, 1).equalsIgnoreCase("S")) {
                this.setConfirmText("Are you sure to show the files ?");
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void processAction() {
        this.setErrorMessage("");
        try {
            if (validateField()) {
                if (this.function.equalsIgnoreCase("GM")) {
                    String result = npciFacade.generateNpciFiles(this.fileGenerationDt, getOrgnBrCode(),
                            getUserName(), getTodayDate(), "", "");
                    if (result.equals("true")) {
                        btnRefreshAction();
                        this.setErrorMessage("Mapper file has been generated successfully.");
                    } else {
                        this.setErrorMessage(result);
                    }
                } else if (this.function.equalsIgnoreCase("SM")) {
                    gridDetail = npciFacade.showGeneratedFiles("MF", this.fileGenerationDt, 0.0);
                    this.setErrorMessage("Now you can download a file from table.");
                } else if (this.function.equalsIgnoreCase("GR")) {
                    if (this.seqNo == null || this.seqNo.equals("")) {
                        this.setErrorMessage("Seq no can not be null or blank.");
                        return;
                    }
                    //fileGenerationDt is SETTLEMENT_DATE in cbs_npci_inward.
                    String result = npciFacade.generateNpciReturnFiles(this.fileGenerationDt, getOrgnBrCode(),
                            getUserName(), getTodayDate(), Double.parseDouble(this.seqNo), "", "");
                    if (result.equals("true")) {
                        btnRefreshAction();
                        this.setErrorMessage("Return file has been generated successfully.");
                    } else {
                        this.setErrorMessage(result);
                    }
                } else if (this.function.equalsIgnoreCase("SR")) {
                    if (this.seqNo == null || this.seqNo.equals("")) {
                        this.setErrorMessage("Seq no can not be null or blank.");
                        return;
                    }
                    gridDetail = npciFacade.showGeneratedFiles("RF", this.fileGenerationDt, Double.parseDouble(this.seqNo));
                    this.setErrorMessage("Now you can download a file from table.");
                } else if (this.function.equalsIgnoreCase("GA")) {
                    if (this.seqNo == null || this.seqNo.equals("")) {
                        this.setErrorMessage("Seq no can not be null or blank.");
                        return;
                    }
                    //fileGenerationDt is SETTLEMENT_DATE in cbs_npci_inward.
                    String result = npciFacade.generateAchReturnFiles(this.fileGenerationDt, getOrgnBrCode(),
                            getUserName(), getTodayDate(), Double.parseDouble(this.seqNo), "", "");
                    if (result.equals("true")) {
                        btnRefreshAction();
                        this.setErrorMessage("ACH Return file has been generated successfully.");
                    } else {
                        this.setErrorMessage(result);
                    }
                } else if (this.function.equalsIgnoreCase("SA")) {
                    if (this.seqNo == null || this.seqNo.equals("")) {
                        this.setErrorMessage("Seq no can not be null or blank.");
                        return;
                    }
                    gridDetail = npciFacade.showGeneratedFiles("CHI", this.fileGenerationDt, Double.parseDouble(this.seqNo));
                    this.setErrorMessage("Now you can download a file from table.");
                } else if (this.function.equalsIgnoreCase("GE")) {
                    //fileGenerationDt is SETTLEMENT_DATE in cbs_npci_inward.
                    String result = npciFacade.generateECSReturnFiles(this.fileGenerationDt, getOrgnBrCode(),
                            getUserName(), getTodayDate(), this.seqNo);
                    if (result.equals("true")) {
                        btnRefreshAction();
                        this.setErrorMessage("ECS Return file has been generated successfully.");
                    } else {
                        this.setErrorMessage(result);
                    }
                } else if (this.function.equalsIgnoreCase("SE")) {
                    if (this.seqNo == null || this.seqNo.equals("")) {
                        this.setErrorMessage("Seq no can not be null or blank.");
                        return;
                    }
                    gridDetail = npciFacade.showGeneratedFiles("EHI", this.fileGenerationDt, Double.parseDouble(this.seqNo));
                    this.setErrorMessage("Now you can download a file from table.");

                } else if (this.function.equalsIgnoreCase("GO")) { //OAC
                    if (this.seqNo == null || this.seqNo.equals("")) {
                        this.setErrorMessage("Seq no can not be null or blank.");
                        return;
                    }
                    //Here fileGenerationDt is the File Upload Date in coming file.
                    String result = otherNpciRemote.generateOacReturnFiles(ymd.format(dmy.parse(fileGenerationDt)),
                            seqNo, getOrgnBrCode(), getUserName(), getTodayDate(), "NPCI-OAC", "", "");
                    if (result.equals("true")) {
                        btnRefreshAction();
                        this.setErrorMessage("Return file has been generated successfully.");
                    } else {
                        this.setErrorMessage(result);
                    }
                } else if (this.function.equalsIgnoreCase("SO")) { //OAC
                    //Here fileGenerationDt is the File Upload Date in coming file.
                    gridDetail = otherNpciRemote.showOacFiles("OAC", ymd.format(dmy.parse(fileGenerationDt)));
                    this.setErrorMessage("Now you can download a file from table.");
                } else if (this.function.equalsIgnoreCase("GD")) { //ECS-DR
                    if (this.seqNo == null || this.seqNo.equals("")) {
                        this.setErrorMessage("Seq no can not be null or blank.");
                        return;
                    }
                    //Here fileGenerationDt is the File Settlement Date in coming file in header.
                    String result = otherNpciRemote.generateEcsDrReturnFiles(ymd.format(dmy.parse(fileGenerationDt)),
                            seqNo, getOrgnBrCode(), getUserName(), getTodayDate(), "ECS-DR", "", "");
                    if (result.equals("true")) {
                        btnRefreshAction();
                        this.setErrorMessage("Return file has been generated successfully.");
                    } else {
                        this.setErrorMessage(result);
                    }
                } else if (this.function.equalsIgnoreCase("SD")) { //ECS-DR
                    //Here fileGenerationDt is the File Settlement Date in coming file in header.
                    gridDetail = otherNpciRemote.showOacFiles("ECS-DR", ymd.format(dmy.parse(fileGenerationDt)));
                    this.setErrorMessage("Now you can download a file from table.");
                } else if (this.function.equalsIgnoreCase("G306")) {
                    //fileGenerationDt is SETTLEMENT_DATE in cbs_npci_inward.
                    String result = otherNpciRemote.generateECS306ReturnFiles(this.fileGenerationDt, getOrgnBrCode(),
                            getUserName(), getTodayDate(), this.seqNo, "", "");
                    if (result.equals("true")) {
                        btnRefreshAction();
                        this.setErrorMessage("ECS 306 Return file has been generated successfully.");
                    } else {
                        this.setErrorMessage(result);
                    }
                } else if (this.function.equalsIgnoreCase("S306")) {
                    if (this.seqNo == null || this.seqNo.equals("")) {
                        this.setErrorMessage("Seq no can not be null or blank.");
                        return;
                    }
                    gridDetail = npciFacade.showGeneratedFiles("EHI", this.fileGenerationDt, Double.parseDouble(this.seqNo.substring(6)));
                    this.setErrorMessage("Now you can download a file from table.");
                } else if (this.function.equalsIgnoreCase("GD306")) {
                    if (this.seqNo == null || this.seqNo.equals("")) {
                        this.setErrorMessage("Seq no can not be null or blank.");
                        return;
                    }
                    //fileGenerationDt is file_coming_dt in cbs_npci_inward.
                    String result = otherNpciRemote.generateAchDr306ReturnFiles(this.fileGenerationDt, getOrgnBrCode(),
                            getUserName(), getTodayDate(), this.seqNo, "", "");
                    if (result.equals("true")) {
                        btnRefreshAction();
                        this.setErrorMessage("ACH Debit Return file has been generated successfully.");
                    } else {
                        this.setErrorMessage(result);
                    }
                } else if (this.function.equalsIgnoreCase("SD306")) {
                    gridDetail = otherNpciRemote.showOacFiles("ACH-DR", ymd.format(dmy.parse(fileGenerationDt)));
                    this.setErrorMessage("Now you can download a file from table.");
                } else if (this.function.equalsIgnoreCase("GENRUPAY")) {
                    if (this.seqNo == null || this.seqNo.equals("")) {
                        this.setErrorMessage("Seq no can not be null or blank.");
                        return;
                    }
                    //Here fileGenerationDt is the File Settlement Date in coming file in header.
                    String result = yojnaRemote.generateYojnaReturnFiles(this.function, ymd.format(dmy.parse(fileGenerationDt)),
                            Double.parseDouble(this.seqNo), getOrgnBrCode(), getUserName(), getTodayDate());
                    if (result.equals("true")) {
                        btnRefreshAction();
                        this.setErrorMessage("Rupay Return file has been generated successfully.");
                    } else {
                        this.setErrorMessage(result);
                    }
                } else if (this.function.equalsIgnoreCase("SHOWRUPAY")) {
                    gridDetail = yojnaRemote.showYojnaFiles(ymd.format(dmy.parse(fileGenerationDt)), Double.parseDouble(this.seqNo));
                    this.setErrorMessage("Now you can download a file from table.");
                } else if (this.function.equalsIgnoreCase("GENAEPS")) {
                    if (this.seqNo == null || this.seqNo.equals("")) {
                        this.setErrorMessage("Seq no can not be null or blank.");
                        return;
                    }
                    //Here fileGenerationDt is the File Settlement Date in coming file in header.
                    String result = yojnaRemote.generateYojnaReturnFiles(this.function, ymd.format(dmy.parse(fileGenerationDt)),
                            Double.parseDouble(this.seqNo), getOrgnBrCode(), getUserName(), getTodayDate());
                    if (result.equals("true")) {
                        btnRefreshAction();
                        this.setErrorMessage("AEPS Return file has been generated successfully.");
                    } else {
                        this.setErrorMessage(result);
                    }
                } else if (this.function.equalsIgnoreCase("SHOWAEPS")) {
                    gridDetail = yojnaRemote.showYojnaFiles(ymd.format(dmy.parse(fileGenerationDt)), Double.parseDouble(this.seqNo));
                    this.setErrorMessage("Now you can download a file from table.");
                }
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void downloadFile() {
        try {
            if (currentItem == null) {
                this.setErrorMessage("Please download one file from table.");
                return;
            }
            HttpServletResponse res = (HttpServletResponse) getHttpResponse();
            String ctxPath = getFacesContext().getExternalContext().getRequestContextPath();

            List list = npciFacade.getBankDetails();
            Vector ele = (Vector) list.get(0);
            if (ele.get(8) == null) {
                this.setErrorMessage("Please define Aadhar Location in bank detail.");
                return;
            }
            String dirName = ele.get(8).toString().trim();
            String keyPwd = ftsRemote.getKeyPwd();

            if (!keyPwd.equals("")) {
                res.sendRedirect(ctxPath + "/downloadFile/?dirName=" + dirName + "&fileName=" + currentItem.getFileName() + "&pwd=" + keyPwd);
            } else {
                res.sendRedirect(ctxPath + "/downloadFile/?dirName=" + dirName + "&fileName=" + currentItem.getFileName());
            }
        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
    }

    public boolean validateField() {
        boolean result = false;
        try {
            if (this.function == null || this.function.equals("")) {
                this.setErrorMessage("Please select Function.");
                return false;
            }
            if (new Validator().validateDate_dd_mm_yyyy(this.fileGenerationDt) == false) {
                this.setErrorMessage("Please select proper File Generation Date.");
                return false;
            }
            if (dmy.parse(this.fileGenerationDt).after(dmy.parse(getTodayDate()))) {
                this.setErrorMessage("File Generation Date can not be greater than Current Date.");
                return false;
            }
            String alphaCode = reportRemote.getAlphacodeByBrncode(getOrgnBrCode());
            if (!alphaCode.equalsIgnoreCase("HO")) {
                this.setErrorMessage("Aadhaar related any work will be perform from HO.");
                return false;
            }
            result = true;
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
        return result;
    }

    public void btnRefreshAction() {
        this.setErrorMessage("");
        this.setFunction("GM");
        this.setFileGenerationDt(getTodayDate());
        this.setBtnValue("Generate");
        currentItem = null;
        gridDetail = new ArrayList<NpciFileDto>();
        this.setConfirmText("");
        seqEnable = "none";
        seqNoList = new ArrayList<SelectItem>();
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

    public String getFileGenerationDt() {
        return fileGenerationDt;
    }

    public void setFileGenerationDt(String fileGenerationDt) {
        this.fileGenerationDt = fileGenerationDt;
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

    public String getBtnValue() {
        return btnValue;
    }

    public void setBtnValue(String btnValue) {
        this.btnValue = btnValue;
    }

    public NpciFileDto getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(NpciFileDto currentItem) {
        this.currentItem = currentItem;
    }

    public List<NpciFileDto> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<NpciFileDto> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getConfirmText() {
        return confirmText;
    }

    public void setConfirmText(String confirmText) {
        this.confirmText = confirmText;
    }

    public String getSeqEnable() {
        return seqEnable;
    }

    public void setSeqEnable(String seqEnable) {
        this.seqEnable = seqEnable;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public List<SelectItem> getSeqNoList() {
        return seqNoList;
    }

    public void setSeqNoList(List<SelectItem> seqNoList) {
        this.seqNoList = seqNoList;
    }
}
