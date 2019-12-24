/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.npci;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.other.OtherNpciMgmtFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.util.List;

/**
 *
 * @author root
 */
public class NpciPendingFileAuthStatus extends BaseBean {

    private String errMessage;
    private List<String[]> gridDetail;
    private OtherNpciMgmtFacadeRemote otherNpciRemote;

    public NpciPendingFileAuthStatus() {
        try {
            otherNpciRemote = (OtherNpciMgmtFacadeRemote) ServiceLocator.getInstance().lookup("OtherNpciMgmtFacade");
            gridDetail = otherNpciRemote.getUnAuthNPCIFile();
        } catch (Exception ex) {
            errMessage = ex.getMessage();
        }
    }

    public void refreshForm() {
        try {
            gridDetail = otherNpciRemote.getUnAuthNPCIFile();
            errMessage = "";
        } catch (ApplicationException ex) {
            errMessage = ex.getMessage();
        }
    }

    public String exitForm() {
        refreshForm();
        return "case1";
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public List getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List gridDetail) {
        this.gridDetail = gridDetail;
    }
}
