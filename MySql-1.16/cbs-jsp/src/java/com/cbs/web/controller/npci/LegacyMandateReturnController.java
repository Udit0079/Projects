/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.npci;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import com.cbs.dto.other.AutoMandateTo;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.other.NpciMandateFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class LegacyMandateReturnController extends BaseBean {

    private String message;
    private String fileType;
    private String mmsType;
    private String returnDate;
    private List<SelectItem> fileTypeList;
    private List<SelectItem> mmsTypeList;
    private List<AutoMandateTo> gridDetail;
    private NpciMandateFacadeRemote npciMandateRemote;
    private CommonReportMethodsRemote commonReport;
    private FtsPostingMgmtFacadeRemote ftsRemote;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymdformat = new SimpleDateFormat("yyyy-MM-dd");

    public LegacyMandateReturnController() {
        try {
            fileTypeList = new ArrayList<SelectItem>();
            fileTypeList.add(new SelectItem("0", "--Select--"));
            fileTypeList.add(new SelectItem("V", "Verification File"));
            fileTypeList.add(new SelectItem("R", "Return File"));

            mmsTypeList = new ArrayList<SelectItem>();
            mmsTypeList.add(new SelectItem("0", "--Select--"));
            mmsTypeList.add(new SelectItem("CREATE"));

            npciMandateRemote = (NpciMandateFacadeRemote) ServiceLocator.getInstance().lookup("NpciMandateFacade");
            commonReport = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");

            refreshForm();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public boolean validateField() {
        try {
            if (this.fileType == null || this.fileType.equals("0") || this.fileType.equals("")) {
                this.setMessage("Please select File Type.");
                return false;
            }
            if (this.mmsType == null || this.mmsType.equals("0") || this.mmsType.equals("")) {
                this.setMessage("Please select Mandate Type.");
                return false;
            }
            if (this.returnDate == null || this.returnDate.equals("")) {
                this.setMessage("Please fill Date.");
                return false;
            }
            if (!new Validator().validateDate_dd_mm_yyyy(this.returnDate)) {
                this.setMessage("Please fill proper Date.");
                return false;
            }
            if (dmy.parse(this.returnDate).compareTo(dmy.parse(dmy.format(new Date()))) > 0) {
                this.setMessage("Date can not be greater than current date.");
                return false;
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
            return false;
        }
        return true;
    }

    public void returnDateAction() {
        this.setMessage("");
        try {
            if (!validateField()) {
                return;
            }
            gridDetail = new ArrayList<AutoMandateTo>();
            List list = npciMandateRemote.getLegacyMandateFileData(this.fileType, this.mmsType,
                    ymd.format(dmy.parse(this.returnDate)));
            if (list.isEmpty()) {
                this.setMessage("There is no data to generate the file.");
                return;
            }
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                AutoMandateTo obj = new AutoMandateTo();

                obj.setMsgId(ele.get(0).toString().trim());
                obj.setCreDtTm(ele.get(1).toString().trim());
                obj.setInitgPtyIdOrgIdOthrId(ele.get(2).toString().trim());
                obj.setInstgAgtFinInstnIdClrSysMmbIdMmbId(ele.get(3).toString().trim());
                obj.setInstgAgtFinInstnIdNm(ele.get(4).toString().trim());
                obj.setInstdAgtFinInstnIdClrSysMmbIdMmbId(ele.get(5).toString().trim());
                obj.setInstdAgtFinInstnIdNm(ele.get(6).toString().trim());
                obj.setMndtId(ele.get(7).toString().trim());
                obj.setMndtReqId(ele.get(8).toString().trim());

                obj.setTpSvcLvlPrtry(ele.get(9).toString().trim());
                obj.setTpLclInstrmPrtry(ele.get(10).toString().trim());
                obj.setOcrncsSeqTp(ele.get(11).toString().trim());
                obj.setOcrncsFrqcy(ele.get(12).toString().trim());
                obj.setOcrncsFrstColltnDt(ele.get(13).toString().trim());
                obj.setOcrncsFnlColltnDt(ele.get(14).toString().trim());
                obj.setColltnAmt(new BigDecimal(ele.get(15).toString().trim()));
                obj.setMaxAmt(new BigDecimal(ele.get(16).toString().trim()));

                obj.setCdtrNm(ele.get(17).toString().trim());
                obj.setCdtrAcctIdOthrId(ele.get(18).toString().trim());
                obj.setCdtrAgtFinInstnIdClrSysMmbIdMmbId(ele.get(19).toString().trim());
                obj.setCdtrAgtFinInstnIdNm(ele.get(20).toString().trim());
                obj.setDbtrNm(ele.get(21).toString().trim());
                obj.setDbtrIdPrvtIdOthrId(ele.get(22).toString().trim());
                obj.setDbtrIdPrvtIdOthrSchmeNmPrtry(ele.get(23).toString().trim());
                obj.setDbtrCtctDtlsPhneNb(ele.get(24).toString().trim());
                obj.setDbtrCtctDtlsMobNb(ele.get(25).toString().trim());
                obj.setDbtrCtctDtlsEmailAdr(ele.get(26).toString().trim());
                obj.setDbtrCtctDtlsOthr(ele.get(27).toString().trim());

                obj.setDbtrAcctIdOthrId(ele.get(28).toString().trim());
                obj.setDbtrAcctTpPrtry(ele.get(29).toString().trim());
                obj.setDbtrAgtFinInstnIdClrSysMmbIdMmbId(ele.get(30).toString().trim());
                obj.setDbtrAgtFinInstnIdNm(ele.get(31).toString().trim());
                obj.setMandateType(ele.get(32).toString().trim());
                obj.setRejectCode(ele.get(35).toString().trim());
                obj.setImageName(ele.get(36).toString().trim());
                obj.setVerifyBy(ele.get(37).toString().trim());

                gridDetail.add(obj);
            }
            this.setMessage("Now you can generate the file.");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void processAction() {
        this.setMessage("");
        try {
            if (!validateField()) {
                return;
            }
            if (gridDetail.isEmpty()) {
                this.setMessage("There is no data to generate the file.");
                return;
            }

            List<AutoMandateTo> repList = new ArrayList<AutoMandateTo>();
            List<AutoMandateTo> returnrepList = new ArrayList<AutoMandateTo>();
            String destBankCode = ftsRemote.getNpciBankCode();

            if (fileType.equalsIgnoreCase("V")) {

                for (int i = 0; i < gridDetail.size(); i++) {
                    AutoMandateTo object = gridDetail.get(i);
                    AutoMandateTo pojo = new AutoMandateTo();

                    pojo.setInstgAgtFinInstnIdNm(object.getInstdAgtFinInstnIdNm());
                    pojo.setMndtId(object.getMndtId());
                    pojo.setOcrncsFrqcy(object.getOcrncsFrqcy());
                    if (object.getOcrncsFrstColltnDt().trim().equalsIgnoreCase("")) {
                        pojo.setOcrncsFrstColltnDt(object.getOcrncsFrstColltnDt());
                    } else {
                        pojo.setOcrncsFrstColltnDt(dmy.format(ymdformat.parse(object.getOcrncsFrstColltnDt())));
                    }
                    if (object.getOcrncsFnlColltnDt().trim().equalsIgnoreCase("")) {
                        pojo.setOcrncsFnlColltnDt(object.getOcrncsFnlColltnDt());
                    } else {
                        pojo.setOcrncsFnlColltnDt(dmy.format(ymdformat.parse(object.getOcrncsFnlColltnDt())));
                    }
                    pojo.setColltnAmt(object.getColltnAmt());
                    pojo.setMaxAmt(object.getMaxAmt());
                    pojo.setCdtrNm(object.getCdtrNm());
                    pojo.setDbtrNm(object.getDbtrNm());
                    pojo.setDbtrAcctIdOthrId(object.getDbtrAcctIdOthrId());
                    pojo.setDbtrAgtFinInstnIdClrSysMmbIdMmbId(object.getDbtrAgtFinInstnIdClrSysMmbIdMmbId());
                    String[] fdt = object.getImageName().split("-");
                    pojo.setImageName(dmy.format(ymd.parse(fdt[4])));
                    repList.add(pojo);
                }
            } else {

                for (int i = 0; i < gridDetail.size(); i++) {
                    AutoMandateTo object = gridDetail.get(i);
                    AutoMandateTo pojo = new AutoMandateTo();
                    pojo.setsNo(i + 1);
                    pojo.setMndtId(object.getMndtId()); // UMRN
                    String[] sbn = object.getImageName().split("-");
                    pojo.setImageName(sbn[2]); //  Sponser Bank
                    pojo.setOcrncsFrstColltnDt(destBankCode); //  destination bank code
                    pojo.setCdtrAcctIdOthrId(object.getCdtrAcctIdOthrId()); //cdtrAcctIdOthrId Utility code
                    pojo.setRejectCode(object.getRejectCode()); // Reason
                    returnrepList.add(pojo);
                }
            }

            if (fileType.equalsIgnoreCase("V")) {
                FastReportBuilder verificationFileData = genrateVerificationFile();
                new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(repList), verificationFileData, "Verification File_" + ymd.format(dmy.parse(returnDate)));
            } else {
                FastReportBuilder returnFileData = genrateReturnFile();
                new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(returnrepList), returnFileData, "Return File_" + ymd.format(dmy.parse(returnDate)));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            this.setMessage(ex.getMessage());
        }
    }

    public FastReportBuilder genrateVerificationFile() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;

            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);


            AbstractColumn imageName = ReportBean.getColumn("imageName", String.class, "File date", 120);
            AbstractColumn mndtId = ReportBean.getColumn("mndtId", String.class, "UMRN No.", 140);
            AbstractColumn dbtrAgtFinInstnIdClrSysMmbIdMmbId = ReportBean.getColumn("dbtrAgtFinInstnIdClrSysMmbIdMmbId", String.class, "MICR code", 120);
            AbstractColumn dbtrAcctIdOthrId = ReportBean.getColumn("dbtrAcctIdOthrId", String.class, "A/C number", 100);
            AbstractColumn dbtrNm = ReportBean.getColumn("dbtrNm", String.class, "A/C Name", 180);
            AbstractColumn ocrncsFrqcy = ReportBean.getColumn("ocrncsFrqcy", String.class, "Frequency", 100);
            AbstractColumn ocrncsFrstColltnDt = ReportBean.getColumn("ocrncsFrstColltnDt", String.class, "First Collection dt", 100);
            AbstractColumn ocrncsFnlColltnDt = ReportBean.getColumn("ocrncsFnlColltnDt", String.class, "Final collection dt", 100);
            AbstractColumn colltnAmt = ReportBean.getColumn("colltnAmt", BigDecimal.class, "Collection amt", 100);
            AbstractColumn maxAmt = ReportBean.getColumn("maxAmt", BigDecimal.class, "Max amount", 100);
            AbstractColumn cdtrNm = ReportBean.getColumn("cdtrNm", String.class, "Creditor Name", 200);
            AbstractColumn instgAgtFinInstnIdNm = ReportBean.getColumn("instgAgtFinInstnIdNm", String.class, "Creditor Bank Name", 200);

            fastReport.addColumn(imageName);
            width = width + imageName.getWidth();

            fastReport.addColumn(mndtId);
            width = width + mndtId.getWidth();

            fastReport.addColumn(dbtrAgtFinInstnIdClrSysMmbIdMmbId);
            width = width + dbtrAgtFinInstnIdClrSysMmbIdMmbId.getWidth();

            fastReport.addColumn(dbtrAcctIdOthrId);
            width = width + dbtrAcctIdOthrId.getWidth();

            fastReport.addColumn(dbtrNm);
            width = width + dbtrNm.getWidth();

            fastReport.addColumn(ocrncsFrqcy);
            width = width + ocrncsFrqcy.getWidth();


            fastReport.addColumn(ocrncsFrstColltnDt);
            width = width + ocrncsFrstColltnDt.getWidth();

            fastReport.addColumn(ocrncsFnlColltnDt);
            width = width + ocrncsFnlColltnDt.getWidth();

            fastReport.addColumn(colltnAmt);
            colltnAmt.setStyle(style);
            width = width + colltnAmt.getWidth();

            fastReport.addColumn(maxAmt);
            maxAmt.setStyle(style);
            width = width + maxAmt.getWidth();

            fastReport.addColumn(cdtrNm);
            width = width + cdtrNm.getWidth();

            fastReport.addColumn(instgAgtFinInstnIdNm);
            width = width + instgAgtFinInstnIdNm.getWidth();

            Page page = new Page(1300, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);

            fastReport.setTitle("Verification File");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastReport;
    }

    public FastReportBuilder genrateReturnFile() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;

            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);

            AbstractColumn sNo = ReportBean.getColumn("sNo", Integer.class, "S.No", 60);
            AbstractColumn mndtId = ReportBean.getColumn("mndtId", String.class, "UMRN No.", 140);
            AbstractColumn imageName = ReportBean.getColumn("imageName", String.class, "Sponsor Bank Code", 120);
            AbstractColumn ocrncsFrstColltnDt = ReportBean.getColumn("ocrncsFrstColltnDt", String.class, "Destination Bank Code", 100);
            AbstractColumn cdtrAcctIdOthrId = ReportBean.getColumn("cdtrAcctIdOthrId", String.class, "Utitily Code", 180);
            AbstractColumn rejectCode = ReportBean.getColumn("rejectCode", String.class, "Resons for Cancellation", 100);

            fastReport.addColumn(sNo);
            width = width + sNo.getWidth();

            fastReport.addColumn(mndtId);
            width = width + mndtId.getWidth();

            fastReport.addColumn(imageName);
            width = width + imageName.getWidth();

            fastReport.addColumn(ocrncsFrstColltnDt);
            width = width + ocrncsFrstColltnDt.getWidth();

            fastReport.addColumn(cdtrAcctIdOthrId);
            width = width + cdtrAcctIdOthrId.getWidth();

            fastReport.addColumn(rejectCode);
            width = width + rejectCode.getWidth();

            Page page = new Page(1300, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);

            fastReport.setTitle("Return File");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastReport;
    }

    public void refreshForm() {
        this.setMessage("");
        this.setFileType("0");
        this.setMmsType("0");
        this.setReturnDate(getTodayDate());
        gridDetail = new ArrayList<AutoMandateTo>();
        this.setMessage("Please select File Type.");
    }

    public String exitForm() {
        refreshForm();
        return "case1";
    }

    //Getter And Setter
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMmsType() {
        return mmsType;
    }

    public void setMmsType(String mmsType) {
        this.mmsType = mmsType;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public List<SelectItem> getMmsTypeList() {
        return mmsTypeList;
    }

    public void setMmsTypeList(List<SelectItem> mmsTypeList) {
        this.mmsTypeList = mmsTypeList;
    }

    public List<AutoMandateTo> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<AutoMandateTo> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public List<SelectItem> getFileTypeList() {
        return fileTypeList;
    }

    public void setFileTypeList(List<SelectItem> fileTypeList) {
        this.fileTypeList = fileTypeList;
    }
}
