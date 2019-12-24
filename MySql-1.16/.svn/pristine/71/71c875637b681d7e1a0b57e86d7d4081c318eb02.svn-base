/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master.sm;

import com.cbs.dto.loan.CbsSchemeValidInstrumentsPKTO;
import com.cbs.dto.loan.CbsSchemeValidInstrumentsTO;
import com.cbs.dto.master.CbsRefRecTypePKTO;
import com.cbs.dto.master.CbsRefRecTypeTO;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.web.delegate.SchemeMasterManagementDelegate;
import com.cbs.web.exception.WebException;
import com.cbs.web.pojo.master.SchemeValidInstrumentsTable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

/**
 *
 * @author root
 */
public class SVI {

    private static final Logger logger = Logger.getLogger(CAD.class);
    private SchemeMaster schemeMaster;

    public SchemeMaster getSchemeMaster() {
        return schemeMaster;
    }

    public void setSchemeMaster(SchemeMaster schemeMaster) {
        this.schemeMaster = schemeMaster;
    }
    //Variables For svi.jsp file
    private String instrumentCode;
    private String crDrIndFlg;
    private String deleteSchemeValid;
    int count1SchemeValid = 0;
    int count2SchemeValid = 0;
    int selectCount = 0;
    private int currentRowSchValid;
    private int selectCountSchValid = 0;
    private boolean sviFlag;
    private SchemeValidInstrumentsTable currentItemSchValid = new SchemeValidInstrumentsTable();
    private List<SchemeValidInstrumentsTable> schValidTmp;
    private List<SchemeValidInstrumentsTable> schemeValid;
    private List<SelectItem> deleteSchemeValidList;
    private List<SelectItem> drCrIndicatorSchemeValidList;
    private List<SelectItem> instrumentCodeList;

    //Getter-Setter for svi.jsp file
    public boolean isSviFlag() {
        return sviFlag;
    }

    public void setSviFlag(boolean sviFlag) {
        this.sviFlag = sviFlag;
    }

    public int getCount1SchemeValid() {
        return count1SchemeValid;
    }

    public void setCount1SchemeValid(int count1SchemeValid) {
        this.count1SchemeValid = count1SchemeValid;
    }

    public int getCount2SchemeValid() {
        return count2SchemeValid;
    }

    public void setCount2SchemeValid(int count2SchemeValid) {
        this.count2SchemeValid = count2SchemeValid;
    }

    public String getCrDrIndFlg() {
        return crDrIndFlg;
    }

    public void setCrDrIndFlg(String crDrIndFlg) {
        this.crDrIndFlg = crDrIndFlg;
    }

    public SchemeValidInstrumentsTable getCurrentItemSchValid() {
        return currentItemSchValid;
    }

    public void setCurrentItemSchValid(SchemeValidInstrumentsTable currentItemSchValid) {
        this.currentItemSchValid = currentItemSchValid;
    }

    public int getCurrentRowSchValid() {
        return currentRowSchValid;
    }

    public void setCurrentRowSchValid(int currentRowSchValid) {
        this.currentRowSchValid = currentRowSchValid;
    }

    public String getDeleteSchemeValid() {
        return deleteSchemeValid;
    }

    public void setDeleteSchemeValid(String deleteSchemeValid) {
        this.deleteSchemeValid = deleteSchemeValid;
    }

    public List<SelectItem> getDeleteSchemeValidList() {
        return deleteSchemeValidList;
    }

    public void setDeleteSchemeValidList(List<SelectItem> deleteSchemeValidList) {
        this.deleteSchemeValidList = deleteSchemeValidList;
    }

    public List<SelectItem> getDrCrIndicatorSchemeValidList() {
        return drCrIndicatorSchemeValidList;
    }

    public void setDrCrIndicatorSchemeValidList(List<SelectItem> drCrIndicatorSchemeValidList) {
        this.drCrIndicatorSchemeValidList = drCrIndicatorSchemeValidList;
    }

    public String getInstrumentCode() {
        return instrumentCode;
    }

    public void setInstrumentCode(String instrumentCode) {
        this.instrumentCode = instrumentCode;
    }

    public List<SelectItem> getInstrumentCodeList() {
        return instrumentCodeList;
    }

    public void setInstrumentCodeList(List<SelectItem> instrumentCodeList) {
        this.instrumentCodeList = instrumentCodeList;
    }

    public List<SchemeValidInstrumentsTable> getSchValidTmp() {
        return schValidTmp;
    }

    public void setSchValidTmp(List<SchemeValidInstrumentsTable> schValidTmp) {
        this.schValidTmp = schValidTmp;
    }

    public List<SchemeValidInstrumentsTable> getSchemeValid() {
        return schemeValid;
    }

    public void setSchemeValid(List<SchemeValidInstrumentsTable> schemeValid) {
        this.schemeValid = schemeValid;
    }

    public int getSelectCountSchValid() {
        return selectCountSchValid;
    }

    public void setSelectCountSchValid(int selectCountSchValid) {
        this.selectCountSchValid = selectCountSchValid;
    }

    /** Creates a new instance of SVI */
    public SVI() {
      
        try {
            schValidTmp = new ArrayList<SchemeValidInstrumentsTable>();
            schemeValid = new ArrayList<SchemeValidInstrumentsTable>();

            drCrIndicatorSchemeValidList = new ArrayList<SelectItem>();
            drCrIndicatorSchemeValidList.add(new SelectItem("0", " "));
            drCrIndicatorSchemeValidList.add(new SelectItem("C", "Credit"));
            drCrIndicatorSchemeValidList.add(new SelectItem("D", "Debit"));

            deleteSchemeValidList = new ArrayList<SelectItem>();
            deleteSchemeValidList.add(new SelectItem("0", " "));
            deleteSchemeValidList.add(new SelectItem("Y", "Yes"));
            deleteSchemeValidList.add(new SelectItem("N", "No"));

            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
            List<CbsRefRecTypeTO> CbsRefRecTypeTOs2 = schemeMasterManagementDelegate.getCurrencyCode("178");
            if (CbsRefRecTypeTOs2.size() > 0) {
                instrumentCodeList = new ArrayList<SelectItem>();
                instrumentCodeList.add(new SelectItem("0", ""));
                for (CbsRefRecTypeTO refRecTo : CbsRefRecTypeTOs2) {
                    CbsRefRecTypePKTO cbsRefRecTypePKTO = refRecTo.getCbsRefRecTypePKTO();
                    instrumentCodeList.add(new SelectItem(cbsRefRecTypePKTO.getRefCode(), refRecTo.getRefDesc()));
                }
            }
			else
			{
				instrumentCodeList = new ArrayList<SelectItem>();
                instrumentCodeList.add(new SelectItem("0", ""));
			}
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    //Functionality for svi.jsp file
    public void selectSviDetails() {
         this.getSchemeMaster().setMessage("");
        try {
            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
            List<CbsSchemeValidInstrumentsTO> listTOs = schemeMasterManagementDelegate.getSviDetails(schemeMaster.getSchemeCode());
            if (listTOs.size() > 0) {
                for (CbsSchemeValidInstrumentsTO singleTO : listTOs) {
                    SchemeValidInstrumentsTable tableObj = new SchemeValidInstrumentsTable();
                    CbsSchemeValidInstrumentsPKTO pKTo = singleTO.getCbsSchemeValidInstrumentsPKTO();
                    tableObj.setInstrumentCode(pKTo.getInstrumentCode());
                    tableObj.setCrDrIndFlg(singleTO.getCrDrIndFlag());
                    tableObj.setDeleteSchemeValid(singleTO.getDelFlag());
                    tableObj.setCounterSaveUpdate("G");
                    schemeValid.add(tableObj);
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

    public void setValueInSchemeValidTable() {
        List<SchemeValidInstrumentsTable> tmpschValidTable = schemeValid;
        if (schemeMaster.functionFlag.equalsIgnoreCase("M") || schemeMaster.functionFlag.equalsIgnoreCase("A")) {
            for (int i = 0; i < tmpschValidTable.size(); i++) {
                String tmpInstrumentCode = tmpschValidTable.get(i).getInstrumentCode();

                if (tmpInstrumentCode.equalsIgnoreCase(instrumentCode)) {
                    schemeMaster.setMessage(instrumentCode + " already Exist In table. Enter Instrument Code");
                    return;
                }

            }
        }

        if (validateScheme().equalsIgnoreCase("false")) {
            return;
        }
        SchemeValidInstrumentsTable schValid = new SchemeValidInstrumentsTable();
        schValid.setInstrumentCode(getInstrumentCode());
        schValid.setCrDrIndFlg(getCrDrIndFlg());
        schValid.setDeleteSchemeValid(deleteSchemeValid);
        if (schemeMaster.functionFlag.equalsIgnoreCase("A")) {
            refreshSviForm();
            schValid.setCounterSaveUpdate("S");
        }

        schemeValid.add(schValid);
        if (schemeMaster.functionFlag.equalsIgnoreCase("M")) {
            if (selectCountSchValid == 0) {
                for (int i = 0; i < tmpschValidTable.size(); i++) {
                    String tmpInstCode = tmpschValidTable.get(i).getInstrumentCode();
                    if (!tmpInstCode.equalsIgnoreCase(instrumentCode)) {
                        schValid.setCounterSaveUpdate("S");

                        schValidTmp.add(schValid);
                        refreshSviForm();
                        return;
                    }
                }
                selectCount = 0;
            }
            if (currentItemSchValid.getInstrumentCode() == null || currentItemSchValid.getInstrumentCode().equalsIgnoreCase("")) {
                schValid.setCounterSaveUpdate("S");
                schValidTmp.add(schValid);
                refreshSviForm();
                return;
            }
            if (currentItemSchValid.getInstrumentCode().equalsIgnoreCase(instrumentCode)) {
                if (!currentItemSchValid.getCrDrIndFlg().equalsIgnoreCase(crDrIndFlg) || !currentItemSchValid.getDeleteSchemeValid().equalsIgnoreCase(deleteSchemeValid)) {
                    schValid.setCounterSaveUpdate("U");
                    refreshSviForm();
                    schValidTmp.add(schValid);
                }
                selectCountSchValid = 0;
            }
        }
    }

    public void selectSchemeValid() {
        selectCountSchValid = 1;
        if ((instrumentCode != null && currentItemSchValid.getInstrumentCode() != null)) {
            if (!instrumentCode.equalsIgnoreCase("0")) {
                if (!instrumentCode.equalsIgnoreCase(currentItemSchValid.getInstrumentCode())) {
                    count2SchemeValid = count1SchemeValid;
                    count1SchemeValid = count1SchemeValid + 1;
                    if (count2SchemeValid != count1SchemeValid) {
                        setValueInSchemeValidTable();
                    }
                } else {
                    count1SchemeValid = 0;
                }
            }
        }
        setInstrumentCode(currentItemSchValid.getInstrumentCode());
        setCrDrIndFlg(currentItemSchValid.getCrDrIndFlg());
        setDeleteSchemeValid(currentItemSchValid.getDeleteSchemeValid());
        schemeValid.remove(currentRowSchValid);
    }

    public String validateScheme() {
        String msg = "";
        if (instrumentCode.equalsIgnoreCase("0")) {
            msg = msg + "Please Select The Instrument Code.";
        }
        if (crDrIndFlg.equalsIgnoreCase("0")) {
            msg = msg + "Please Select  The Cr/Dr Flag.";
        }
        if (deleteSchemeValid.equalsIgnoreCase("0")) {
            msg = msg + "Please Select The Delete Flag";
        }
        if (!msg.equalsIgnoreCase("")) {
            schemeMaster.setMessage(msg);
            return "False";
        }
        return "true";
    }

    public void refreshSviForm() {
        this.setCrDrIndFlg("0");
        this.setDeleteSchemeValid("0");
        this.setInstrumentCode("0");
    }

    public void enableSviForm() {
        this.sviFlag = false;
    }

    public void disableSviForm() {
        this.sviFlag = true;
    }
}
