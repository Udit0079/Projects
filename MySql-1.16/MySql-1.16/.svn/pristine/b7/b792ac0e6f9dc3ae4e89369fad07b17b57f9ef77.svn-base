/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.constant.CbsConstant;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.other.PostalDetailFacadeRemote;
import com.cbs.pojo.PostalDemandPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author root
 */
public class SingleDemandGeneration extends BaseBean {

    private String message;
    private String acno;
    private String tillDt;
    private List<PostalDemandPojo> tableDataList;
    private PostalDetailFacadeRemote remote = null;
    private FtsPostingMgmtFacadeRemote ftsRemote = null;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    /**
     * Creates a new instance of SingleDemandGeneration
     */
    public SingleDemandGeneration() {
        try {
            remote = (PostalDetailFacadeRemote) ServiceLocator.getInstance().lookup("PostalDetailFacade");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            btnRefreshAction();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void validateAccount() {
        this.setMessage("");
        try {
            if (this.acno == null || this.acno.equals("") || this.acno.length() != 12) {
                this.setMessage("Please fill 12 digit A/c no.");
                return;
            }
            if (!getOrgnBrCode().equals(ftsRemote.getCurrentBrnCode(this.acno))) {
                this.setMessage("Please fill self branch for A/c No:");
                return;
            }
            if (!ftsRemote.getAccountNature(this.acno).equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                this.setMessage("Please fill A/c no of loan nature");
                return;
            }
            String result = ftsRemote.ftsAcnoValidate(this.acno, 0, "");
            if (!result.equalsIgnoreCase("true")) {
                this.setMessage(result);
                return;
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void processAccountDemand() {
        this.setMessage("");
        tableDataList = new ArrayList<PostalDemandPojo>();
        try {
            if (this.tillDt == null || this.tillDt.equals("")) {
                this.setMessage("Please fill till date.");
                return;
            }
            if (!new Validator().validateDate_dd_mm_yyyy(this.tillDt)) {
                this.setMessage("Please fill proper till date.");
                return;
            }
            List<PostalDemandPojo> list = remote.getAllDemandOfAccount(this.acno, ymd.format(dmy.parse(this.tillDt)));
            if (list.isEmpty()) {
                this.setMessage("There is no demand for this a/c no: " + this.acno);
                return;
            }
            for (int i = 0; i < list.size(); i++) {
                PostalDemandPojo pojo = new PostalDemandPojo();
                PostalDemandPojo obj = list.get(i);

                pojo.setAcno(obj.getAcno());
                String dmdType = obj.getDmdType();
                if (dmdType.equals("PLOAN")) {
                    pojo.setDmdType("LOAN EMI");
                } else if (dmdType.equals("PINSP")) {
                    pojo.setDmdType("PREMIUM EMI");
                } else if (dmdType.equals("PWELL")) {
                    pojo.setDmdType("MEMBER WELLFARE");
                }

                pojo.setOverDueAmt(obj.getOverDueAmt());
                pojo.setDueDt(obj.getDueDt());

                tableDataList.add(pojo);
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void btnRefreshAction() {
        this.setMessage("");
        this.setAcno("");
        this.setTillDt(getTodayDate());
        tableDataList = new ArrayList<PostalDemandPojo>();
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }

    /**
     * Getter And Setter
     */
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getTillDt() {
        return tillDt;
    }

    public void setTillDt(String tillDt) {
        this.tillDt = tillDt;
    }

    public List<PostalDemandPojo> getTableDataList() {
        return tableDataList;
    }

    public void setTableDataList(List<PostalDemandPojo> tableDataList) {
        this.tableDataList = tableDataList;
    }
}
