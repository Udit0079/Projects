package com.cbs.web.controller.ho.share;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.share.ShareTransferFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Zeeshan Waris
 */
public class ShareValue extends BaseBean {

    ShareTransferFacadeRemote remoteObject;
    private String message;
    private String shareValue;
    Date effDt = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getEffDt() {
        return effDt;
    }

    public void setEffDt(Date effDt) {
        this.effDt = effDt;
    }

    public String getShareValue() {
        return shareValue;
    }

    public void setShareValue(String shareValue) {
        this.shareValue = shareValue;
    }

    public ShareValue() {
        try {
            remoteObject = (ShareTransferFacadeRemote) ServiceLocator.getInstance().lookup("ShareTransferFacade");
            this.setMessage("");
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public String btnExit() {
        btnRefresh();
        return "case1";
    }

    public void btnRefresh() {
        this.setMessage("");
        setShareValue("");
        setEffDt(new Date());
    }

    public void saveBtnAction() {
        if (shareValue == null || shareValue.equalsIgnoreCase("")) {
            this.setMessage("Please fill Share Value");
            return;
        }
        if (!shareValue.matches("[0-9.]*")) {
            this.setMessage("Please enter numeric value in Share Value");
            return;
        }
        if (shareValue.length() > 5) {
            this.setMessage("Please enter Share Value less than 5 digits !");
            return;
        }
        if (effDt == null) {
            this.setMessage("Please fill Effective Date");
            return;
        }
        try {
            String result = remoteObject.saveShareValue(Double.parseDouble(shareValue), ymd.format(effDt));
            this.setMessage(result);
            this.setShareValue("");
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
}
