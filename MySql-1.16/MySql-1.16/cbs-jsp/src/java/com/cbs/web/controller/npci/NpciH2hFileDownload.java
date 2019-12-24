/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.npci;

import com.cbs.dto.Npcih2hfilePojo;
import com.cbs.facade.other.OtherNpciMgmtFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.servlet.http.HttpServletResponse;

public class NpciH2hFileDownload extends BaseBean {

    private String errorMessage;
    private String toInddt;
    private List<Npcih2hfilePojo> gridDetail;
    private Npcih2hfilePojo currentItem;
    private List<Npcih2hfilePojo> gridIncomDtl;
    private Npcih2hfilePojo currentItem1;
    private Npcih2hfilePojo currentItem2;
    private List<Npcih2hfilePojo> gridDetailres;
    private OtherNpciMgmtFacadeRemote otherNpciRemote = null;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    Properties props = null;

    public NpciH2hFileDownload() {
        try {
            otherNpciRemote = (OtherNpciMgmtFacadeRemote) ServiceLocator.getInstance().lookup("OtherNpciMgmtFacade");
            props = new Properties();
            props.load(new FileReader(new File("/opt/conf/wslocation.properties")));
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void gridDetailAction() {
        this.setErrorMessage("");
        try {
            if (toInddt.equalsIgnoreCase(null) || toInddt.equalsIgnoreCase("") || this.toInddt.equalsIgnoreCase("__/__/____")) {
                this.setErrorMessage("Please fill the Date.");
                return;
            }
            List<Npcih2hfilePojo> allFiles = otherNpciRemote.getFileDetail(ymd.format(dmy.parse(this.toInddt)));
            if (allFiles.isEmpty()) {
                this.setErrorMessage("There is no data to show.");
                return;
            }
            gridIncomDtl = new ArrayList<>();
            gridDetail = new ArrayList<>();
            gridDetailres = new ArrayList<>();

            for (Npcih2hfilePojo obj : allFiles) {
                if (obj.getFileType().equalsIgnoreCase("IW")) {
                    gridIncomDtl.add(obj);
                } else if (obj.getFileType().equalsIgnoreCase("OW")) {
                    gridDetail.add(obj);
                } else if (obj.getFileType().equalsIgnoreCase("RES")) {
                    gridDetailres.add(obj);
                }
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void downloadFile(String downloadOption) {
        try {
            HttpServletResponse res = (HttpServletResponse) getHttpResponse();
            String ctxPath = getFacesContext().getExternalContext().getRequestContextPath();

            if (downloadOption.equalsIgnoreCase("IW")) {
                if (currentItem1 == null) {
                    this.setErrorMessage("Please select download link.");
                    return;
                }
                res.sendRedirect(ctxPath + "/downloadFile/?dirName=" + props.getProperty("cbs.iw.bkp.location") + "&fileName=" + currentItem1.getFileName());
            } else if (downloadOption.equalsIgnoreCase("OW")) {
                if (currentItem == null) {
                    this.setErrorMessage("Please select download link.");
                    return;
                }
                res.sendRedirect(ctxPath + "/downloadFile/?dirName=" + props.getProperty("cbs.ow.bkp.encrypted.location") + "&fileName=" + currentItem.getFileName());
            } else if (downloadOption.equalsIgnoreCase("RES")) {
                if (currentItem2 == null) {
                    this.setErrorMessage("Please select download link.");
                    return;
                }
                res.sendRedirect(ctxPath + "/downloadFile/?dirName=" + props.getProperty("cbs.res.location") + "&fileName=" + currentItem2.getFileName());
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void btnRefreshAction() {
        this.setErrorMessage("");
        this.gridDetail = new ArrayList<>();
        this.gridIncomDtl = new ArrayList<>();
        this.gridDetailres = new ArrayList<>();
        this.currentItem = null;
        this.currentItem1 = null;
        this.currentItem2 = null;
        this.setToInddt("");
    }

    public String btnExitAction() {
        return "case1";
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getToInddt() {
        return toInddt;
    }

    public void setToInddt(String toInddt) {
        this.toInddt = toInddt;
    }

    public List<Npcih2hfilePojo> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<Npcih2hfilePojo> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public Npcih2hfilePojo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(Npcih2hfilePojo currentItem) {
        this.currentItem = currentItem;
    }

    public List<Npcih2hfilePojo> getGridIncomDtl() {
        return gridIncomDtl;
    }

    public void setGridIncomDtl(List<Npcih2hfilePojo> gridIncomDtl) {
        this.gridIncomDtl = gridIncomDtl;
    }

    public Npcih2hfilePojo getCurrentItem1() {
        return currentItem1;
    }

    public void setCurrentItem1(Npcih2hfilePojo currentItem1) {
        this.currentItem1 = currentItem1;
    }

    public Npcih2hfilePojo getCurrentItem2() {
        return currentItem2;
    }

    public void setCurrentItem2(Npcih2hfilePojo currentItem2) {
        this.currentItem2 = currentItem2;
    }

    public List<Npcih2hfilePojo> getGridDetailres() {
        return gridDetailres;
    }

    public void setGridDetailres(List<Npcih2hfilePojo> gridDetailres) {
        this.gridDetailres = gridDetailres;
    }
}
