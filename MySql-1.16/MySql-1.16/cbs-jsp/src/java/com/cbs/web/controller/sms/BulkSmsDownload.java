/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.sms;

import com.cbs.dto.NpciFileDto;
import com.cbs.facade.sms.SmsManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

public class BulkSmsDownload extends BaseBean {

    private String errorMessage;
    private String fromdt;
    private String toInddt;
    private String filesLocation;
    private List<NpciFileDto> gridDetail;
    private NpciFileDto currentItem;
//    private FtsPostingMgmtFacadeRemote ftsRemote;
//    private CommonReportMethodsRemote commonReportRemote;
    private SmsManagementFacadeRemote smsMgmtFacade = null;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public BulkSmsDownload() {
        try {
//            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
//            commonReportRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            smsMgmtFacade = (SmsManagementFacadeRemote) ServiceLocator.getInstance().lookup("SmsManagementFacade");

//            gridDetail = new ArrayList<>();
            ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
             this.filesLocation = context.getInitParameter("cbsFiles") + "/BULK-SMS";
            File dir = new File(this.filesLocation + "/");
            if (!dir.exists()) {
                dir.mkdirs();
            }
         } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void gridDetailAction() {
        try {
                if (fromdt == null || fromdt.trim().equals("")) {
                    this.setErrorMessage("Please fill the from date.");
                    return;
                }
                if (!new Validator().validateDate_dd_mm_yyyy(fromdt)) {
                    this.setErrorMessage("Please fill proper from date.");
                    return;
                }
                if (dmy.parse(fromdt).compareTo(ymd.parse(ymd.format(new Date()))) > 0) {
                    this.setErrorMessage("From date can not be greater than current date.");
                    return;
                }
                if (toInddt == null || toInddt.trim().equals("")) {
                    this.setErrorMessage("Please fill the to date .");
                    return;
                }
                if (!new Validator().validateDate_dd_mm_yyyy(toInddt)) {
                    this.setErrorMessage("Please fill proper to date.");
                    return;
                }
                if (dmy.parse(toInddt).compareTo(ymd.parse(ymd.format(new Date()))) > 0) {
                    this.setErrorMessage("To date can not be greater than current date.");
                    return;
                }
                if (dmy.parse(fromdt).compareTo(dmy.parse(toInddt)) > 0) {
                    this.setErrorMessage("From date can not be greater than to date.");
                    return;
                }
            gridDetail = new ArrayList<>();
            gridDetail = smsMgmtFacade.getBulkSmsData(ymd.format(dmy.parse(this.fromdt)), ymd.format(dmy.parse(this.toInddt)));
            if (gridDetail.isEmpty()) {
                this.setErrorMessage("There is no file to download.");
                return;
            }
            for (NpciFileDto dto : gridDetail) {
                System.out.println("Ist FileName Is-->" + dto.getFileName());
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
            return;
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
            
           res.sendRedirect(ctxPath + "/downloadFile/?dirName=" + this.filesLocation + "/" + "&fileName=" + currentItem.getOrignalFileName());
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void btnRefreshAction() {
        this.setErrorMessage("");
        currentItem = null;
        gridDetail = new ArrayList<>();
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getFromdt() {
        return fromdt;
    }

    public void setFromdt(String fromdt) {
        this.fromdt = fromdt;
    }

    public String getToInddt() {
        return toInddt;
    }

    public void setToInddt(String toInddt) {
        this.toInddt = toInddt;
    }

    public List<NpciFileDto> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<NpciFileDto> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public NpciFileDto getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(NpciFileDto currentItem) {
        this.currentItem = currentItem;
    }

    public String getFilesLocation() {
        return filesLocation;
    }

    public void setFilesLocation(String filesLocation) {
        this.filesLocation = filesLocation;
    }
}
