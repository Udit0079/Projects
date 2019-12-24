/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.alm;

import com.cbs.dto.report.AlmAnnexture1Table;
import com.cbs.dto.report.AlmPojo;
import com.cbs.dto.report.RbiAlmPojo;
import com.cbs.dto.report.RbiSossPojo;
import com.cbs.facade.report.ALMReportFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.ALMQtrReportFacadeRemote;
import com.cbs.facade.report.RbiMonthlyReportFacadeRemote;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Alok Yadav
 */
public class AlmAnnexture1 extends BaseBean {

    private String message;
    private String tillDate;
    private String repOpt;
    private List<SelectItem> repOptionList;
    private String reportFormat;
    private List<SelectItem> reportFormatIn;
    private final String almFacadeHomeName = "ALMReportFacade";    
    private final String commonFacadeHomeName = "CommonReportMethods";
    private final String rbiFacadeHomeName = "RbiMonthlyReportFacade";
    private final String almQtrFacadeHomeName = "ALMQtrReportFacade";
    private ALMQtrReportFacadeRemote almQtrFacadeRemote = null;
    private ALMReportFacadeRemote almFacadeRemote = null;
    private CommonReportMethodsRemote commonFacadeRemote = null;
    private RbiMonthlyReportFacadeRemote rbiFacadeRemote = null;    
    private SimpleDateFormat ymdFormatter = new SimpleDateFormat("yyyyMMdd"),
            dmyformatter = new SimpleDateFormat("dd/MM/yyyy");
    private String flag;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRepOpt() {
        return repOpt;
    }

    public void setRepOpt(String repOpt) {
        this.repOpt = repOpt;
    }

    public List<SelectItem> getRepOptionList() {
        return repOptionList;
    }

    public void setRepOptionList(List<SelectItem> repOptionList) {
        this.repOptionList = repOptionList;
    }

    public String getTillDate() {
        return tillDate;
    }

    public void setTillDate(String tillDate) {
        this.tillDate = tillDate;
    }

    public String getReportFormat() {
        return reportFormat;
    }

    public void setReportFormat(String reportFormat) {
        this.reportFormat = reportFormat;
    }

    public List<SelectItem> getReportFormatIn() {
        return reportFormatIn;
    }

    public void setReportFormatIn(List<SelectItem> reportFormatIn) {
        this.reportFormatIn = reportFormatIn;
    }

    /** Creates a new instance of AlmAnnexture1 */
    public AlmAnnexture1() {
        try {
            almFacadeRemote = (ALMReportFacadeRemote) ServiceLocator.getInstance().lookup(almFacadeHomeName);
            almQtrFacadeRemote = (ALMQtrReportFacadeRemote) ServiceLocator.getInstance().lookup(almQtrFacadeHomeName);
            rbiFacadeRemote = (RbiMonthlyReportFacadeRemote) ServiceLocator.getInstance().lookup(rbiFacadeHomeName);
            commonFacadeRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(commonFacadeHomeName);
            this.setMessage("");
            repOptionList = new ArrayList<SelectItem>();
            reportFormatIn = new ArrayList<SelectItem>();
            repOptionList.add(new SelectItem("", "--Select--"));
            repOptionList.add(new SelectItem("R", "Rs"));
            repOptionList.add(new SelectItem("T", "Thousand"));
            repOptionList.add(new SelectItem("L", "Lacs"));
            repOptionList.add(new SelectItem("C", "Crore"));

            reportFormatIn.add(new SelectItem("N", "In Detail"));
            reportFormatIn.add(new SelectItem("Y", "In Summary"));

        } catch (ApplicationException e) {
            this.setMessage(e.getExceptionCode().getExceptionMessage());
            flag = "false";
        } catch (Exception e) {
            this.setMessage(e.getMessage());
            flag = "false";
        }
    }

    public void printReport() {
        try {
            if (tillDate == null) {
                setMessage("Please fill the Till Date");
                flag = "false";
                return;
            }
            if (!Validator.validateDate(tillDate)) {
                setMessage("Please fill the valid Till Date");
                flag = "false";
                return;
            }
            if (this.repOpt == null || this.repOpt.equalsIgnoreCase("")) {
                setMessage("Please Select the Report Option");
                return;
            }
            String amtIn = null;
            Double reptOpt = 1d;
            if (repOpt.equalsIgnoreCase("T")) {
                reptOpt = 1000d;
                amtIn = "Amounts (Rs. in Thousand)";
            } else if (repOpt.equalsIgnoreCase("L")) {
                reptOpt = 100000d;
                amtIn = "Amounts (Rs. in Lac)";
            } else if (repOpt.equalsIgnoreCase("C")) {
                reptOpt = 10000000d;
                amtIn = "Amounts (Rs. in Crore)";
            } else if (repOpt.equalsIgnoreCase("R")) {
                reptOpt = 1d;
                amtIn = "Amounts (Rs.)";
            }
            flag = "true";
            setMessage("");
            String dt = ymdFormatter.format(dmyformatter.parse(tillDate));
            List<RbiAlmPojo> almAnnextureTable = almQtrFacadeRemote.hoAlmDetailsSummary("ALM-L", dt, getOrgnBrCode(), reptOpt, getReportFormat(), "L");
            if (almAnnextureTable.isEmpty()) {
                setMessage("No proper data available!!!");
                return;
            }
            String bnkName = "";
            String bnkAddress = "";
            List bankDetails = commonFacadeRemote.getBranchNameandAddress(getOrgnBrCode());
            if (!bankDetails.isEmpty()) {
                bnkName = bankDetails.get(0).toString();
                bnkAddress = bankDetails.get(1).toString();
            }

            String report = "ALM (Residual Maturity)";
            Map fillParams = new HashMap();

            fillParams.put("reprt", report);
            fillParams.put("pPrintedDate", tillDate);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pBankName", bnkName);
            fillParams.put("pBankAddress", bnkAddress);
            fillParams.put("pAmtIn", amtIn);
            List bucketList = almQtrFacadeRemote.getAlmBucketMaster("L", dt, "ALL");
            if (!bucketList.isEmpty()) {
                for (int b = 0; b < bucketList.size(); b++) {
                    Vector vect = (Vector) bucketList.get(b);
                    int bucktNo = Integer.parseInt(vect.get(2).toString());
                    if (bucktNo == 1) {
                        fillParams.put("pbkt1", vect.get(3).toString());
                    } else if (bucktNo == 2) {
                        fillParams.put("pbkt2", vect.get(3).toString());
                    } else if (bucktNo == 3) {
                        fillParams.put("pbkt3", vect.get(3).toString());
                    } else if (bucktNo == 4) {
                        fillParams.put("pbkt4", vect.get(3).toString());
                    } else if (bucktNo == 5) {
                        fillParams.put("pbkt5", vect.get(3).toString());
                    } else if (bucktNo == 6) {
                        fillParams.put("pbkt6", vect.get(3).toString());
                    } else if (bucktNo == 7) {
                        fillParams.put("pbkt7", vect.get(3).toString());
                    } else if (bucktNo == 8) {
                        fillParams.put("pbkt8", vect.get(3).toString());
                    } else if (bucktNo == 9) {
                        fillParams.put("pbkt9", vect.get(3).toString());
                    } else if (bucktNo ==10) {
                        fillParams.put("pbkt10", vect.get(3).toString());
                    } else if (bucktNo == 11) {
                        fillParams.put("pbkt11", vect.get(3).toString());
                    } else if (bucktNo == 12) {
                        fillParams.put("pbkt12", vect.get(3).toString());
                    }
                }
            }
            //new ReportBean().jasperReport("HoAlm1", "text/html", new JRBeanCollectionDataSource(almAnnextureTable), fillParams, "ALM Report");
            List tierList = commonFacadeRemote.getCode("TIER_FOR_ALM");
            String tier="";
            if(!tierList.isEmpty()) {
                tier = tierList.get(0).toString();
            }
            if(tier.equalsIgnoreCase("2")) {
                new ReportBean().jasperReport("Ho_Alm_Maturity_Summ_Details_TIER_2", "text/html", new JRBeanCollectionDataSource(almAnnextureTable), fillParams, "ALM Report");
            } else {
                new ReportBean().jasperReport("Ho_Alm_Maturity_Summ_Details", "text/html", new JRBeanCollectionDataSource(almAnnextureTable), fillParams, "ALM Report");
            }            
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
            flag = "false";
        } catch (Exception e) {
            this.setMessage(e.getMessage());
            flag = "false";
        }
    }

    public void printPDF() {

        try {
            if (tillDate == null) {
                setMessage("Please fill the Till Date");
                flag = "false";
                return;
            }
            if (!Validator.validateDate(tillDate)) {
                setMessage("Please fill the valid Till Date");
                flag = "false";
                return;
            }
            if (this.repOpt == null || this.repOpt.equalsIgnoreCase("")) {
                setMessage("Please Select the Report Option");
                return;
            }
            flag = "true";
            setMessage("");
            String amtIn = null;
            Double reptOpt = 1d;
            if (repOpt.equalsIgnoreCase("T")) {
                reptOpt = 1000d;
                amtIn = "Amounts (Rs. in Thousand)";
            } else if (repOpt.equalsIgnoreCase("L")) {
                reptOpt = 100000d;
                amtIn = "Amounts (Rs. in Lac)";
            } else if (repOpt.equalsIgnoreCase("C")) {
                reptOpt = 10000000d;
                amtIn = "Amounts (Rs. in Crore)";
            } else if (repOpt.equalsIgnoreCase("R")) {
                reptOpt = 1d;
                amtIn = "Amounts (Rs.)";
            }
            String dt = ymdFormatter.format(dmyformatter.parse(tillDate));
//            List<AlmPojo> almAnnextureTable = rbiFacadeRemote.hoAlm(dt, getOrgnBrCode(), repOpt);
            List<RbiAlmPojo> almAnnextureTable = almQtrFacadeRemote.hoAlmDetailsSummary("ALM-L", dt, getOrgnBrCode(), reptOpt, getReportFormat(), "L");
//            for(int i = 0; i<almAnnextureTable.size();i++){
//                System.out.println("ALM-L:"+almAnnextureTable.get(i).getDescription()+"; "+almAnnextureTable.get(i).getgCode()+";"+almAnnextureTable.get(i).getSgCode()+";"+almAnnextureTable.get(i).getSsgCode()+";"+almAnnextureTable.get(i).getSssgCode()+";"+almAnnextureTable.get(i).getSsssgCode()+";"+almAnnextureTable.get(i).getCodeFr()+";"+almAnnextureTable.get(i).getCodeTo()+";"+almAnnextureTable.get(i).getAcNature()+";"+almAnnextureTable.get(i).getAcType());
//            }
            if (almAnnextureTable.isEmpty()) {
                setMessage("No proper data available!!!");
                return;
            }
            String bnkName = "";
            String bnkAddress = "";
            List bankDetails = commonFacadeRemote.getBranchNameandAddress(getOrgnBrCode());
            if (!bankDetails.isEmpty()) {
                bnkName = bankDetails.get(0).toString();
                bnkAddress = bankDetails.get(1).toString();
            }
            String report = "ALM (Residual Maturity)";
            Map fillParams = new HashMap();

            fillParams.put("report", report);
            fillParams.put("pPrintedDate", tillDate);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pBankName", bnkName);
            fillParams.put("pBankAddress", bnkAddress);
            fillParams.put("pAmtIn", amtIn);
            List bucketList = almQtrFacadeRemote.getAlmBucketMaster("L", dt, "ALL");
            if (!bucketList.isEmpty()) {
                for (int b = 0; b < bucketList.size(); b++) {
                    Vector vect = (Vector) bucketList.get(b);
                    int bucktNo = Integer.parseInt(vect.get(2).toString());
                    if (bucktNo == 1) {
                        fillParams.put("pbkt1", vect.get(3).toString());
                    } else if (bucktNo == 2) {
                        fillParams.put("pbkt2", vect.get(3).toString());
                    } else if (bucktNo == 3) {
                        fillParams.put("pbkt3", vect.get(3).toString());
                    } else if (bucktNo == 4) {
                        fillParams.put("pbkt4", vect.get(3).toString());
                    } else if (bucktNo == 5) {
                        fillParams.put("pbkt5", vect.get(3).toString());
                    } else if (bucktNo == 6) {
                        fillParams.put("pbkt6", vect.get(3).toString());
                    } else if (bucktNo == 7) {
                        fillParams.put("pbkt7", vect.get(3).toString());
                    } else if (bucktNo == 8) {
                        fillParams.put("pbkt8", vect.get(3).toString());
                    } else if (bucktNo == 9) {
                        fillParams.put("pbkt9", vect.get(3).toString());
                    } else if (bucktNo ==10) {
                        fillParams.put("pbkt10", vect.get(3).toString());
                    } else if (bucktNo == 11) {
                        fillParams.put("pbkt11", vect.get(3).toString());
                    } else if (bucktNo == 12) {
                        fillParams.put("pbkt12", vect.get(3).toString());
                    }
                }
            }
            List tierList = commonFacadeRemote.getCode("TIER_FOR_ALM");
            String tier="";
            if(!tierList.isEmpty()) {
                tier = tierList.get(0).toString();
            }
            if(tier.equalsIgnoreCase("2")) {
                new ReportBean().downloadPdf("ALM" + ymdFormatter.format(dmyformatter.parse(this.tillDate)), "Ho_Alm_Maturity_Summ_Details_TIER_2", new JRBeanCollectionDataSource(almAnnextureTable), fillParams);
            } else {
                new ReportBean().downloadPdf("ALM" + ymdFormatter.format(dmyformatter.parse(this.tillDate)), "Ho_Alm_Maturity_Summ_Details", new JRBeanCollectionDataSource(almAnnextureTable), fillParams);
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
            flag = "false";
        } catch (Exception e) {
            this.setMessage(e.getMessage());
            flag = "false";
        }
    }

    public void refresh() {
        setTillDate("");
        setMessage("");
        this.setReportFormat("N");
    }

    public String exitFrm() {
        return "case1";
    }
}
