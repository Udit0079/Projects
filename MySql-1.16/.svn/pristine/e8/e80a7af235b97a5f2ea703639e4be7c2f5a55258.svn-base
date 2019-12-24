/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.loan;

import com.cbs.exception.ApplicationException;
import com.cbs.web.pojo.intcal.AmortizationSchedule;
import com.cbs.facade.loan.AdvancesInformationTrackingRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.hrms.web.utils.WebUtil;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author admin
 */
public class EMICalculater {

    String orgnBrCode;
    HttpServletRequest req;
    private String todayDate;
    private String userName;
    private String message;
    private String roi;
    private String periodicity;
    double totalPrincipal = 0d;
    double totalInterest = 0d;
    double totalInstallment = 0d;
    double totalOutstanding = 0d;
    private String interestOption;
    private List<SelectItem> interestOptionList;

    public double getTotalInstallment() {
        return totalInstallment;
    }

    public void setTotalInstallment(double totalInstallment) {
        this.totalInstallment = totalInstallment;
    }

    public double getTotalInterest() {
        return totalInterest;
    }

    public void setTotalInterest(double totalInterest) {
        this.totalInterest = totalInterest;
    }

    public double getTotalPrincipal() {
        return totalPrincipal;
    }

    public void setTotalPrincipal(double totalPrincipal) {
        this.totalPrincipal = totalPrincipal;
    }

    public double getTotalOutstanding() {
        return totalOutstanding;
    }

    public void setTotalOutstanding(double totalOutstanding) {
        this.totalOutstanding = totalOutstanding;
    }

    public String getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(String periodicity) {
        this.periodicity = periodicity;
    }
    private String principal;
    private String noOfInstallment;
    private Date emiStartingDate = new Date();
    private List<SelectItem> periodList;
    private List<AmortizationSchedule> amortSch = new ArrayList<AmortizationSchedule>();
    AdvancesInformationTrackingRemote advInfTrack;

    public List<AmortizationSchedule> getAmortSch() {
        return amortSch;
    }

    public void setAmortSch(List<AmortizationSchedule> amortSch) {
        this.amortSch = amortSch;
    }

    public Date getEmiStartingDate() {
        return emiStartingDate;
    }

    public void setEmiStartingDate(Date emiStartingDate) {
        this.emiStartingDate = emiStartingDate;
    }

    public String getNoOfInstallment() {
        return noOfInstallment;
    }

    public void setNoOfInstallment(String noOfInstallment) {
        this.noOfInstallment = noOfInstallment;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getRoi() {
        return roi;
    }

    public void setRoi(String roi) {
        this.roi = roi;
    }

    public List<SelectItem> getPeriodList() {
        return periodList;
    }

    public void setPeriodList(List<SelectItem> periodList) {
        this.periodList = periodList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getInterestOption() {
        return interestOption;
    }

    public void setInterestOption(String interestOption) {
        this.interestOption = interestOption;
    }

    public List<SelectItem> getInterestOptionList() {
        return interestOptionList;
    }

    public void setInterestOptionList(List<SelectItem> interestOptionList) {
        this.interestOptionList = interestOptionList;
    }
       
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public EMICalculater() {
        req = getRequest();
        try {
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
             advInfTrack = (AdvancesInformationTrackingRemote) ServiceLocator.getInstance().lookup("AdvancesInformationTrackingFacade");
            setUserName(req.getRemoteUser());
            setTodayDate(sdf.format(date));
            setMessage("");
            setPeriodList(new ArrayList<SelectItem>());
            periodList.add(new SelectItem("0", "--SELECT--"));
            periodList.add(new SelectItem("M", "Monthly"));
            periodList.add(new SelectItem("Q", "Quarterly"));
            periodList.add(new SelectItem("HY", "Half Yearly"));
            
            interestOptionList = new ArrayList<>();
            interestOptionList.add(new SelectItem("0", "--SELECT--"));
            interestOptionList.add(new SelectItem("S", "Simple"));
            interestOptionList.add(new SelectItem("C", "Compounding"));
            
            
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void calculateActionRepayment() {
        setMessage("");
        // setErrorMessage("");
        String msg = "";
        amortSch = new ArrayList<AmortizationSchedule>();
        setTotalInstallment(0d);
        setTotalInterest(0d);
        setTotalPrincipal(0d);
        String resultList;
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        try {
            if (periodicity.equalsIgnoreCase("0")) {
                setMessage("Please Select Periodicity.");
                return;
            }
            if (emiStartingDate == null) {
                setMessage("Please Select the EMI Starting Date.");
                return;
            }
            if (principal == null || principal.equalsIgnoreCase("")) {
                setMessage("Please Enter the principal Amount.");
                return;
            } else {
                Matcher period = p.matcher(principal);
                if (!period.matches()) {
                    setMessage("Please Enter numeric value for Principle Amount.");
                    return;
                }
                if (!principal.matches("[0-9.]*")) {
                    setMessage("Please Enter numeric value for Principle Amount.");
                    return;
                }
            }
            if (roi.equalsIgnoreCase("") || roi.equalsIgnoreCase("0")) {
                setMessage("Please Enter Rate Of Interest.");
                return;
            } else {
                Matcher rate = p.matcher(roi);
                if (!rate.matches()) {
                    setMessage("Please Enter numeric value for ROI.");
                    return;
                } else if (Float.parseFloat(roi) <= 0) {
                    setMessage("ROI Can not be Zero or less than zero");
                    return;
                } else if (Float.parseFloat(roi) > 79) {
                    setMessage("Overflow");
                    return;
                }
            }

            if (noOfInstallment.equalsIgnoreCase("") || noOfInstallment.equalsIgnoreCase("0")) {
                setMessage("Please Enter No Of Installment.");
                return;
            } else {
                Matcher period = p.matcher(noOfInstallment);
                if (!period.matches()) {
                    setMessage("Please Enter numeric value for Installment.");
                    return;
                }
                if (!noOfInstallment.matches("[0-9.]*")) {
                    setMessage("Please Enter numeric value for Installment.");
                    return;
                } else if (Float.parseFloat(noOfInstallment) > 11149) {
                    setMessage("Overflow");
                    return;
                }
            }


            String temp = "";
            int i, j, k, l, m, n, o, q;
           
            resultList = advInfTrack.Calculate(temp, 1, Float.parseFloat(principal), Integer.parseInt(noOfInstallment),
                    Float.parseFloat(roi), ymd.format(emiStartingDate), Float.parseFloat(principal), periodicity, temp,this.interestOption);
            NumberFormat formatter = new DecimalFormat("#0.00");
            if (resultList.contains("[")) {
                String[] values = null;
                resultList = resultList.replace("]", "");
                resultList = resultList.replace("[", "");
                resultList = resultList.replace(":", "");
                String spliter = ", ";
                values = resultList.split(spliter);
                double openingPri = Double.parseDouble(principal);
                for (i = 0, j = 1, k = 2, l = 3, m = 4, n = 5, o = 6, q = 7; i < (values.length); i = i + 8, j = j + 8, k = k + 8, l = l + 8, m = m + 8, n = n + 8, o = o + 8, q = q + 8 ) {
                    AmortizationSchedule rd = new AmortizationSchedule();
                    rd.setSno(values[i]);
                    rd.setDueDate(values[j]);
                    rd.setPrincipleAmt((new BigDecimal(Double.parseDouble(values[k].toString())).toString()));
                    totalPrincipal = totalPrincipal + Double.parseDouble(values[k]);
                    rd.setInstallment((new BigDecimal(Double.parseDouble(values[m].toString())).toString()));
                    totalInstallment = totalInstallment + Double.parseDouble(values[m]);
                    rd.setInterestAmt((new BigDecimal(Double.parseDouble(values[l].toString())).toString()));
                    //totalInterest = totalInterest + Double.parseDouble(values[l]);
                    totalInterest = totalInstallment - totalPrincipal;
                    rd.setOpeningPrinciple(formatter.format(openingPri));
                    openingPri = openingPri-Double.parseDouble(values[k]);
                    rd.setClosingPrinciple(formatter.format(openingPri<0?0:openingPri)); 
                    openingPri = openingPri<0?0:openingPri;
//                    openingPri = ((openingPri-Double.parseDouble(values[k]))<=0 && openingPri>=0)?openingPri:openingPri-Double.parseDouble(values[k]);
//                    rd.setClosingPrinciple(formatter.format(((openingPri-Double.parseDouble(values[k]) )<=0  && openingPri>=0)?openingPri:openingPri-Double.parseDouble(values[k])));
//                    openingPri = ((openingPri-Double.parseDouble(values[k]))<=0 && openingPri>=0)?0:openingPri;
                    amortSch.add(rd);
                }

            } else {
                setMessage(resultList);
            }

        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }

    }

    public void refresh() {
        try {
            amortSch = new ArrayList<AmortizationSchedule>();
            setMessage("");
            setPrincipal("");
            setEmiStartingDate(sdf.parse(sdf.format(date)));
            setRoi("");
            setNoOfInstallment("");
            setPeriodicity("");
            setTotalInstallment(0d);
            setTotalInterest(0d);
            setTotalPrincipal(0d);
        } catch (Exception ex) {
             setMessage(ex.getMessage());
        }
    }

    public String exitBtnAction() {
        try {
            refresh();
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
        return "case1";
    }
}
