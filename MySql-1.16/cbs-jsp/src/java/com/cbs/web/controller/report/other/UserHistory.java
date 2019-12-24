/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import com.cbs.dto.report.ho.UserHistoryDto;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.DDSReportFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.pojo.CompareByFlag;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.collections.comparators.ComparatorChain;

/**
 *
 * @author root
 */
public class UserHistory extends BaseBean {

    private String errMsg;
    private String branch;
    private String frDt;
    private String toDt;
    private List<SelectItem> branchList;
    private CommonReportMethodsRemote common;
    private DDSReportFacadeRemote ddsRemote;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public UserHistory() {
        try {
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ddsRemote = (DDSReportFacadeRemote) ServiceLocator.getInstance().lookup("DDSReportFacade");
            onLoadData();
            refresh();
        } catch (Exception ex) {
            this.setErrMsg(ex.getMessage());
        }
    }

    public void onLoadData() {
        try {
            branchList = new ArrayList<SelectItem>();
            branchList.add(new SelectItem("--Select--"));
            List list = common.getAlphacodeAllAndBranch(getOrgnBrCode());
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector vtr = (Vector) list.get(i);
                    branchList.add(new SelectItem(CbsUtil.lPadding(2, Integer.parseInt(vtr.get(1).toString())),
                            vtr.get(0).toString()));
                }
            }
        } catch (Exception ex) {
            this.setErrMsg(ex.getMessage());
        }
    }

    public void htmlReport() {
        this.setErrMsg("");
        try {
            if (validateField()) {
                String reportName = "User History";
                Map fillParams = new HashMap();
                List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
                fillParams.put("pBankName", brNameAndAddList.get(0).toString());
                fillParams.put("pBranchAddress", brNameAndAddList.get(1).toString());
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportDt", this.frDt + "--" + this.toDt);
                fillParams.put("pReportName", reportName);

                System.out.println("From date is:" + this.frDt + "::::" + this.toDt);
                List<UserHistoryDto> dataList = ddsRemote.printUserHistory(this.branch, ymd.format(dmy.parse(this.frDt)),
                        ymd.format(dmy.parse(this.toDt)), getUserName());
                if (dataList.isEmpty()) {
                    this.setErrMsg("There is no data to print.");
                    return;
                }
                for (int i = 0; i < dataList.size(); i++) {
                    UserHistoryDto dto = dataList.get(i);
                    System.out.println("-->UserId is:" + dto.getUserid() + "::" + dto.getFlag());
                }
                ComparatorChain chObj = new ComparatorChain();
                chObj.addComparator(new CompareByFlag());
                Collections.sort(dataList, chObj);
                new ReportBean().jasperReport("User_History", "text/html", new JRBeanCollectionDataSource(dataList), fillParams, "User History");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                refresh();
            }
        } catch (Exception ex) {
            this.setErrMsg(ex.getMessage());
        }
    }

    public boolean validateField() {
        try {
            if (this.branch == null || this.branch.equals("--Select--")) {
                this.setErrMsg("Please select Branch.");
                return false;
            }
            if (this.frDt == null || this.frDt.equals("")) {
                this.setErrMsg("Please fill From Date.");
                return false;
            }
            if (!new Validator().validateDate_dd_mm_yyyy(this.frDt)) {
                this.setErrMsg("Please fill proper From Date.");
                return false;
            }
            if (this.toDt == null || this.toDt.equals("")) {
                this.setErrMsg("Please fill To Date.");
                return false;
            }
            if (!new Validator().validateDate_dd_mm_yyyy(this.toDt)) {
                this.setErrMsg("Please fill proper To Date.");
                return false;
            }
            if (dmy.parse(this.frDt).compareTo(dmy.parse(this.toDt)) > 0) {
                this.setErrMsg("From Date can not be greater than To Date.");
                return false;
            }
        } catch (Exception ex) {
            this.setErrMsg(ex.getMessage());
        }
        return true;
    }

    public void refresh() {
        this.setErrMsg("");
        this.setBranch("--Select--");
        this.setFrDt(getTodayDate());
        this.setToDt(getTodayDate());
    }

    public String exit() {
        refresh();
        return "case1";
    }

    /*Getter And Setter*/
    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getFrDt() {
        return frDt;
    }

    public void setFrDt(String frDt) {
        this.frDt = frDt;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }
}
