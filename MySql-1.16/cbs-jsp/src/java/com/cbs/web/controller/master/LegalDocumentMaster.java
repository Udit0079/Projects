package com.cbs.web.controller.master;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.master.GeneralMasterFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.master.LegalDocumentTable;
import javax.faces.context.FacesContext;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.faces.model.SelectItem;
import java.util.List;
import java.util.Vector;
import javax.naming.NamingException;

public final class LegalDocumentMaster extends BaseBean {

    GeneralMasterFacadeRemote generalFacadeRemote;
    private String message;
    private String acctNature;
    private List<SelectItem> acctNatureOption;
    private List<LegalDocumentTable> legalDocuments;
    int currentRow;
    LegalDocumentTable currentItem;
    private String legalDoct;

    public String getLegalDoct() {
        return legalDoct;
    }

    public void setLegalDoct(String legalDoct) {
        this.legalDoct = legalDoct;
    }

    public LegalDocumentTable getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(LegalDocumentTable currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public List<LegalDocumentTable> getLegalDocuments() {
        return legalDocuments;
    }

    public void setLegalDocuments(List<LegalDocumentTable> legalDocuments) {
        this.legalDocuments = legalDocuments;
    }

    public List<SelectItem> getAcctNatureOption() {
        return acctNatureOption;
    }

    public void setAcctNatureOption(List<SelectItem> acctNatureOption) {
        this.acctNatureOption = acctNatureOption;
    }

    public String getAcctNature() {
        return acctNature;
    }

    public void setAcctNature(String acctNature) {
        this.acctNature = acctNature;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LegalDocumentMaster() {
        try {
            generalFacadeRemote = (GeneralMasterFacadeRemote) ServiceLocator.getInstance().lookup("GeneralMasterFacade");
            getAccountNature();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void getAccountNature() {
        try {
            List acnoNatureList = new ArrayList();
            acnoNatureList = generalFacadeRemote.accountNatureLegalDocumentMaster();
            acctNatureOption = new ArrayList<SelectItem>();
            for (int i = 0; i < acnoNatureList.size(); i++) {
                Vector ele = (Vector) acnoNatureList.get(i);
                for (Object ee : ele) {
                    acctNatureOption.add(new SelectItem(ee.toString(), ee.toString()));
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void getTableDetails() throws NamingException {
        legalDocuments = new ArrayList<LegalDocumentTable>();
        try {
            setMessage("");
            List resultLt = new ArrayList();
            resultLt = generalFacadeRemote.tableDetailsLegalDocumentMaster(acctNature);
            if (!resultLt.isEmpty()) {
                for (int i = 0; i < resultLt.size(); i++) {
                    Vector ele = (Vector) resultLt.get(i);
                    LegalDocumentTable ld = new LegalDocumentTable();
                    ld.setAccType(ele.get(0).toString());
                    ld.setLegalDocument(ele.get(1).toString());
                    ld.setEnterBy(ele.get(2).toString());
                    ld.setCode(Integer.parseInt(ele.get(3).toString()));
                    ld.setTranTime(ele.get(4).toString());
                    legalDocuments.add(ld);
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void fetchCurrentRow(ActionEvent event) {
        try {
            String accNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("acNo"));
            currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
            for (LegalDocumentTable item : legalDocuments) {
                if (item.getEnterBy().equals(accNo)) {
                    currentItem = item;
                    break;
                }
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void delete() {
        try {
            legalDocuments = new ArrayList<LegalDocumentTable>();
            setMessage("");
            String deleteEntry = new String();
            deleteEntry = generalFacadeRemote.deleteLegalDocumentData(currentItem.getCode(), currentItem.getLegalDocument(), currentItem.getEnterBy(), currentItem.getAccType());
            List resultLt = new ArrayList();
            resultLt = generalFacadeRemote.tableDetailsLegalDocumentMaster(currentItem.getAccType());
            if (!resultLt.isEmpty()) {
                for (int i = 0; i < resultLt.size(); i++) {
                    Vector ele = (Vector) resultLt.get(i);
                    LegalDocumentTable ld = new LegalDocumentTable();
                    ld.setAccType(ele.get(0).toString());
                    ld.setLegalDocument(ele.get(1).toString());
                    ld.setEnterBy(ele.get(2).toString());
                    ld.setCode(Integer.parseInt(ele.get(3).toString()));
                    ld.setTranTime(ele.get(4).toString());
                    legalDocuments.add(ld);
                }
            }
            this.setMessage(deleteEntry);
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void save() {
        try {
            this.setMessage("");
            if (this.acctNature.equalsIgnoreCase("--SELECT--")) {
                this.setMessage("Please Select The Account Nature.");
                return;
            }
            if (this.legalDoct.equalsIgnoreCase("") || this.legalDoct.length() == 0) {
                this.setMessage("Please Enter The legal Document.");
                return;
            }
            if ((legalDoct.contains("-")) || (legalDoct.contains("*")) || (legalDoct.contains("&")) || (legalDoct.contains("%")) || (legalDoct.contains("$")) || (legalDoct.contains("^"))) {
                this.setMessage("Please Enter Valid  legal Document.");
                return;
            }
            String saveData = generalFacadeRemote.saveLegalDocumentData(acctNature, legalDoct, getUserName());
            getTableDetails();
            this.setMessage(saveData);
            this.setLegalDoct("");
            setAcctNature("--SELECT--");
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void clearText() {
        this.setMessage("");
        this.setLegalDoct("");
        setAcctNature("--SELECT--");
        legalDocuments = new ArrayList<LegalDocumentTable>();
    }

    public String exitForm() {
        clearText();
        return "case1";
    }
}
