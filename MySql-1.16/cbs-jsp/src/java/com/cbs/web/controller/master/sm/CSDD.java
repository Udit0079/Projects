/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master.sm;

import com.cbs.dto.loan.CbsSchemeDocumentDetailsPKTO;
import com.cbs.dto.loan.CbsSchemeDocumentDetailsTO;
import com.cbs.dto.master.CbsRefRecTypePKTO;
import com.cbs.dto.master.CbsRefRecTypeTO;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.web.delegate.SchemeMasterManagementDelegate;
import com.cbs.web.exception.WebException;
import com.cbs.web.pojo.master.SchemeDocumentDetails;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

/**
 *
 * @author root
 */
public class CSDD {

    private static final Logger logger = Logger.getLogger(CAD.class);
    private SchemeMaster schemeMaster;

    public SchemeMaster getSchemeMaster() {
        return schemeMaster;
    }

    public void setSchemeMaster(SchemeMaster schemeMaster) {
        this.schemeMaster = schemeMaster;
    }
    //Variables for csdd.jsp file
    private String documentCode;
    private String documentDesc;
    private String delFlagDocDetail;
    private String mandatoryDoc;
    private int count1DocDetail = 0;
    private int count2DocDetail = 0;
    private int selectCountTod = 0;
    private int currentRowDocDetail;
    private int selectCountDocDetail = 0;
    private boolean disableFlagDocDetail;
    private List<SchemeDocumentDetails> docDetail;
    private List<SelectItem> docCode;
    private SchemeDocumentDetails currentItemDoc = new SchemeDocumentDetails();
    private List<SchemeDocumentDetails> docDetailTmp;
    private List<SelectItem> deleteTODList;

    //Getter-Setter for csdd.jsp file
    public int getCount1DocDetail() {
        return count1DocDetail;
    }

    public void setCount1DocDetail(int count1DocDetail) {
        this.count1DocDetail = count1DocDetail;
    }

    public int getCount2DocDetail() {
        return count2DocDetail;
    }

    public void setCount2DocDetail(int count2DocDetail) {
        this.count2DocDetail = count2DocDetail;
    }

    public SchemeDocumentDetails getCurrentItemDoc() {
        return currentItemDoc;
    }

    public void setCurrentItemDoc(SchemeDocumentDetails currentItemDoc) {
        this.currentItemDoc = currentItemDoc;
    }

    public int getCurrentRowDocDetail() {
        return currentRowDocDetail;
    }

    public void setCurrentRowDocDetail(int currentRowDocDetail) {
        this.currentRowDocDetail = currentRowDocDetail;
    }

    public String getDelFlagDocDetail() {
        return delFlagDocDetail;
    }

    public void setDelFlagDocDetail(String delFlagDocDetail) {
        this.delFlagDocDetail = delFlagDocDetail;
    }

    public boolean isDisableFlagDocDetail() {
        return disableFlagDocDetail;
    }

    public void setDisableFlagDocDetail(boolean disableFlagDocDetail) {
        this.disableFlagDocDetail = disableFlagDocDetail;
    }

    public List<SelectItem> getDocCode() {
        return docCode;
    }

    public void setDocCode(List<SelectItem> docCode) {
        this.docCode = docCode;
    }

    public List<SchemeDocumentDetails> getDocDetail() {
        return docDetail;
    }

    public void setDocDetail(List<SchemeDocumentDetails> docDetail) {
        this.docDetail = docDetail;
    }

    public List<SchemeDocumentDetails> getDocDetailTmp() {
        return docDetailTmp;
    }

    public void setDocDetailTmp(List<SchemeDocumentDetails> docDetailTmp) {
        this.docDetailTmp = docDetailTmp;
    }

    public String getDocumentCode() {
        return documentCode;
    }

    public void setDocumentCode(String documentCode) {
        this.documentCode = documentCode;
    }

    public String getDocumentDesc() {
        return documentDesc;
    }

    public void setDocumentDesc(String documentDesc) {
        this.documentDesc = documentDesc;
    }

    public String getMandatoryDoc() {
        return mandatoryDoc;
    }

    public void setMandatoryDoc(String mandatoryDoc) {
        this.mandatoryDoc = mandatoryDoc;
    }

    public int getSelectCountDocDetail() {
        return selectCountDocDetail;
    }

    public void setSelectCountDocDetail(int selectCountDocDetail) {
        this.selectCountDocDetail = selectCountDocDetail;
    }

    public List<SelectItem> getDeleteTODList() {
        return deleteTODList;
    }

    public void setDeleteTODList(List<SelectItem> deleteTODList) {
        this.deleteTODList = deleteTODList;
    }

    /** Creates a new instance of CSDD */
    public CSDD() {
        try {
            docDetail = new ArrayList<SchemeDocumentDetails>();
            docDetailTmp = new ArrayList<SchemeDocumentDetails>();

            deleteTODList = new ArrayList<SelectItem>();
            deleteTODList.add(new SelectItem("0", ""));
            deleteTODList.add(new SelectItem("Y", "Yes"));
            deleteTODList.add(new SelectItem("N", "No"));

            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
            List<CbsRefRecTypeTO> CbsRefRecTypeTOs = schemeMasterManagementDelegate.getCurrencyCode("149");
            if (CbsRefRecTypeTOs.size() > 0) {
                docCode = new ArrayList<SelectItem>();
                docCode.add(new SelectItem("0", ""));
                for (CbsRefRecTypeTO refRecTo : CbsRefRecTypeTOs) {
                    CbsRefRecTypePKTO cbsRefRecTypePKTO = refRecTo.getCbsRefRecTypePKTO();
                    docCode.add(new SelectItem(cbsRefRecTypePKTO.getRefCode(), refRecTo.getRefDesc()));
                }
            }
			else
			{
				docCode = new ArrayList<SelectItem>();
                docCode.add(new SelectItem("0", ""));
			}
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    //Functionality for csdd.jsp file
    public void selectCSDDDetails() {
         this.getSchemeMaster().setMessage("");
        try {
            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
            List<CbsSchemeDocumentDetailsTO> cbsSchemeDocumentDetailsTOs = schemeMasterManagementDelegate.getDataDocDetail(schemeMaster.getSchemeCode());
            if (cbsSchemeDocumentDetailsTOs.size() > 0) {
                for (CbsSchemeDocumentDetailsTO obj : cbsSchemeDocumentDetailsTOs) {
                    CbsSchemeDocumentDetailsPKTO cbsSchemeDocumentDetailsPKTO = obj.getCbsSchemeDocumentDetailsPKTO();
                    SchemeDocumentDetails schemeDocumentDetails = new SchemeDocumentDetails();
                    schemeDocumentDetails.setDocumentCode(cbsSchemeDocumentDetailsPKTO.getDocumentCode());
                    schemeDocumentDetails.setDocumentDesc(obj.getDocumentDescription());
                    schemeDocumentDetails.setMandatoryDoc(obj.getMandatoryDocument());
                    schemeDocumentDetails.setDelFlagDocDetail(obj.getDelFlag());
                    schemeDocumentDetails.setCounterSaveUpdate("G");
                    docDetail.add(schemeDocumentDetails);
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void setValueInDocDetailTable() {
        List<SchemeDocumentDetails> docDetailTemp = docDetail;
        if (schemeMaster.functionFlag.equalsIgnoreCase("M") || schemeMaster.functionFlag.equalsIgnoreCase("A")) {
            if (selectCountTod == 0) {
                for (int i = 0; i < docDetailTemp.size(); i++) {
                    String documentCodetmp = docDetailTemp.get(i).getDocumentCode();
                    if (documentCodetmp.equalsIgnoreCase(documentCode)) {
                        schemeMaster.setMessage("Document Report Code Already Exists In The Table. Please Select other Document Report Code.");
                        return;
                    }
                }
            }
        }
        if (validateDocDetail().equalsIgnoreCase("false")) {
            return;
        }
        SchemeDocumentDetails documentDetail = new SchemeDocumentDetails();
        documentDetail.setDocumentCode(getDocumentCode());
        documentDetail.setMandatoryDoc(getMandatoryDoc());
        documentDetail.setDocumentDesc(getDocumentDesc());
        documentDetail.setDelFlagDocDetail(getDelFlagDocDetail());

        if (schemeMaster.functionFlag.equalsIgnoreCase("A")) {
            documentDetail.setCounterSaveUpdate("S");
            refreshCSDDForm();
        }

        docDetail.add(documentDetail);
        if (schemeMaster.functionFlag.equalsIgnoreCase("M")) {
            if (selectCountDocDetail != 1) {
                for (int i = 0; i < docDetailTemp.size(); i++) {
                    String documentCodetmp = docDetailTemp.get(i).getDocumentCode();
                    if (!documentCodetmp.equalsIgnoreCase(documentCode)) {
                        documentDetail.setCounterSaveUpdate("S");
                        docDetailTmp.add(documentDetail);
                        refreshCSDDForm();
                        return;
                    }
                }
            }
            if (currentItemDoc.getDocumentCode() == null || currentItemDoc.getDocumentCode().equalsIgnoreCase("")) {
                documentDetail.setCounterSaveUpdate("S");
                docDetailTmp.add(documentDetail);
                refreshCSDDForm();
                return;
            } else if (currentItemDoc.getDocumentCode().equalsIgnoreCase(documentCode)) {
                if (!currentItemDoc.getDocumentDesc().equalsIgnoreCase(documentDesc) || !currentItemDoc.getMandatoryDoc().equalsIgnoreCase(mandatoryDoc) || !currentItemDoc.getDelFlagDocDetail().equalsIgnoreCase(delFlagDocDetail)) {
                    documentDetail.setCounterSaveUpdate("U");
                    refreshCSDDForm();
                    docDetailTmp.add(documentDetail);
                }
                selectCountDocDetail = 0;
            }
        }
    }

    public void selectDocDetail() {
        schemeMaster.setMessage("");
        selectCountDocDetail = 1;
        
        if ((documentCode != null && currentItemDoc.getDocumentCode() != null)) {
            if (!documentCode.equalsIgnoreCase("")) {
                if (!documentCode.equalsIgnoreCase(currentItemDoc.getDocumentCode())) {
                    count2DocDetail = count1DocDetail;
                    count1DocDetail = count1DocDetail + 1;
                    if (count2DocDetail != count1DocDetail) {
                        setValueInDocDetailTable();
                    }
                } else {
                    count1DocDetail = 0;
                }
            }
        }

        setDocumentCode(currentItemDoc.getDocumentCode().toString());
        setDocumentDesc(currentItemDoc.getDocumentDesc());
        setDelFlagDocDetail(currentItemDoc.getDelFlagDocDetail());
        setMandatoryDoc(currentItemDoc.getMandatoryDoc());
        docDetail.remove(currentRowDocDetail);
    }

    public String validateDocDetail() {
        schemeMaster.setMessage("");
        String msg = "";
        if (documentCode.equalsIgnoreCase("0") || documentCode == null) {
            msg = msg + "Please Select Document Code";
        }
        if (documentDesc.equalsIgnoreCase("")) {
            msg = msg + "Please  Select Document Desc";
        }

        if (mandatoryDoc.equalsIgnoreCase("") || mandatoryDoc == null) {
            msg = msg + "Please Enter Mandatory Document";
        }
        if (delFlagDocDetail.equalsIgnoreCase("0")) {
            msg = msg + "Please Select The Delete Flag";
        }
        if (!msg.equalsIgnoreCase("")) {
            schemeMaster.setMessage(msg);

            return "False";
        }
        return "true";
    }

    public void refreshCSDDForm() {
        this.setDocumentCode("0");
        this.setDocumentDesc("");
        this.setMandatoryDoc("0");
        this.setDelFlagDocDetail("0");
        //To initialize the actual list for table.
        //docDetail.clear();
    }

    public void enableCSDDForm() {
        this.disableFlagDocDetail = false;
    }

    public void disableCSDDForm() {
        this.disableFlagDocDetail = true;
    }
}
