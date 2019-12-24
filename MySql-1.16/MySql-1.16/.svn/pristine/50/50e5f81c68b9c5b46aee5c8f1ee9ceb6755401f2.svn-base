/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master.sm;

import com.cbs.dto.loan.CbsSchemeAccountOpenMatrixTO;
import com.cbs.dto.master.CbsRefRecTypePKTO;
import com.cbs.dto.master.CbsRefRecTypeTO;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.web.delegate.SchemeMasterManagementDelegate;
import com.cbs.web.exception.WebException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

/**
 *
 * @author root
 */
public class AOM {

    private static final Logger logger = Logger.getLogger(AOM.class);
    private SchemeMaster schemeMaster;

    public SchemeMaster getSchemeMaster() {
        return schemeMaster;
    }

    public void setSchemeMaster(SchemeMaster schemeMaster) {
        this.schemeMaster = schemeMaster;
    }
    //Variables for aom.jsp file
    private String matrixDesc;
    private String validInvalidFlag;
    private String custStatus;
    private String custSector;
    private String custSubSector;
    private String custTypeCode;
    private String custConstitution;
    private String custEmpId;
    private String custOtherBank;
    private String modeOfOperation;
    private String guaranteeCover;
    private String natureOfAdvance;
    private String typeOfAdvance;
    private String modeOfAdvance;
    private String purposeOfAdvance;
    private String custMinorFlag;
    private String chequedAllowedFlag;
    private String accountTurnDetail;
    private String accountOwnership;
    private String delFlagmat;
    private boolean aomFlag;
    private List<SelectItem> ddValidInvalidFlag;
    private List<SelectItem> ddCustStatus;
    private List<SelectItem> ddCustSector;
    private List<SelectItem> ddCustSubSector;
    private List<SelectItem> ddCustTypeCode;
    private List<SelectItem> ddCustConstitution;
    private List<SelectItem> ddModeOfOperation;
    private List<SelectItem> ddGuaranteeCover;
    private List<SelectItem> ddNatureOfAdvance;
    private List<SelectItem> ddTypeOfAdvance;
    private List<SelectItem> ddModeOfAdvance;
    private List<SelectItem> ddPurposeOfAdvance;
    private List<SelectItem> ddAomTrnRefNo;

    //Getter-Setter for aom.jsp file
    public String getAccountOwnership() {
        return accountOwnership;
    }

    public void setAccountOwnership(String accountOwnership) {
        this.accountOwnership = accountOwnership;
    }

    public String getAccountTurnDetail() {
        return accountTurnDetail;
    }

    public void setAccountTurnDetail(String accountTurnDetail) {
        this.accountTurnDetail = accountTurnDetail;
    }

    public String getChequedAllowedFlag() {
        return chequedAllowedFlag;
    }

    public void setChequedAllowedFlag(String chequedAllowedFlag) {
        this.chequedAllowedFlag = chequedAllowedFlag;
    }

    public String getCustConstitution() {
        return custConstitution;
    }

    public void setCustConstitution(String custConstitution) {
        this.custConstitution = custConstitution;
    }

    public String getCustEmpId() {
        return custEmpId;
    }

    public void setCustEmpId(String custEmpId) {
        this.custEmpId = custEmpId;
    }

    public String getCustMinorFlag() {
        return custMinorFlag;
    }

    public void setCustMinorFlag(String custMinorFlag) {
        this.custMinorFlag = custMinorFlag;
    }

    public String getCustOtherBank() {
        return custOtherBank;
    }

    public void setCustOtherBank(String custOtherBank) {
        this.custOtherBank = custOtherBank;
    }

    public String getCustSector() {
        return custSector;
    }

    public void setCustSector(String custSector) {
        this.custSector = custSector;
    }

    public String getCustStatus() {
        return custStatus;
    }

    public void setCustStatus(String custStatus) {
        this.custStatus = custStatus;
    }

    public String getCustSubSector() {
        return custSubSector;
    }

    public void setCustSubSector(String custSubSector) {
        this.custSubSector = custSubSector;
    }

    public String getCustTypeCode() {
        return custTypeCode;
    }

    public void setCustTypeCode(String custTypeCode) {
        this.custTypeCode = custTypeCode;
    }

    public String getDelFlagmat() {
        return delFlagmat;
    }

    public void setDelFlagmat(String delFlagmat) {
        this.delFlagmat = delFlagmat;
    }

    public String getGuaranteeCover() {
        return guaranteeCover;
    }

    public void setGuaranteeCover(String guaranteeCover) {
        this.guaranteeCover = guaranteeCover;
    }

    public String getMatrixDesc() {
        return matrixDesc;
    }

    public void setMatrixDesc(String matrixDesc) {
        this.matrixDesc = matrixDesc;
    }

    public String getModeOfAdvance() {
        return modeOfAdvance;
    }

    public void setModeOfAdvance(String modeOfAdvance) {
        this.modeOfAdvance = modeOfAdvance;
    }

    public String getModeOfOperation() {
        return modeOfOperation;
    }

    public void setModeOfOperation(String modeOfOperation) {
        this.modeOfOperation = modeOfOperation;
    }

    public String getNatureOfAdvance() {
        return natureOfAdvance;
    }

    public void setNatureOfAdvance(String natureOfAdvance) {
        this.natureOfAdvance = natureOfAdvance;
    }

    public String getPurposeOfAdvance() {
        return purposeOfAdvance;
    }

    public void setPurposeOfAdvance(String purposeOfAdvance) {
        this.purposeOfAdvance = purposeOfAdvance;
    }

    public String getTypeOfAdvance() {
        return typeOfAdvance;
    }

    public void setTypeOfAdvance(String typeOfAdvance) {
        this.typeOfAdvance = typeOfAdvance;
    }

    public String getValidInvalidFlag() {
        return validInvalidFlag;
    }

    public void setValidInvalidFlag(String validInvalidFlag) {
        this.validInvalidFlag = validInvalidFlag;
    }

    public List<SelectItem> getDdCustConstitution() {
        return ddCustConstitution;
    }

    public void setDdCustConstitution(List<SelectItem> ddCustConstitution) {
        this.ddCustConstitution = ddCustConstitution;
    }

    public List<SelectItem> getDdCustSector() {
        return ddCustSector;
    }

    public void setDdCustSector(List<SelectItem> ddCustSector) {
        this.ddCustSector = ddCustSector;
    }

    public List<SelectItem> getDdCustStatus() {
        return ddCustStatus;
    }

    public void setDdCustStatus(List<SelectItem> ddCustStatus) {
        this.ddCustStatus = ddCustStatus;
    }

    public List<SelectItem> getDdCustSubSector() {
        return ddCustSubSector;
    }

    public void setDdCustSubSector(List<SelectItem> ddCustSubSector) {
        this.ddCustSubSector = ddCustSubSector;
    }

    public List<SelectItem> getDdCustTypeCode() {
        return ddCustTypeCode;
    }

    public void setDdCustTypeCode(List<SelectItem> ddCustTypeCode) {
        this.ddCustTypeCode = ddCustTypeCode;
    }

    public List<SelectItem> getDdGuaranteeCover() {
        return ddGuaranteeCover;
    }

    public void setDdGuaranteeCover(List<SelectItem> ddGuaranteeCover) {
        this.ddGuaranteeCover = ddGuaranteeCover;
    }

    public List<SelectItem> getDdModeOfAdvance() {
        return ddModeOfAdvance;
    }

    public void setDdModeOfAdvance(List<SelectItem> ddModeOfAdvance) {
        this.ddModeOfAdvance = ddModeOfAdvance;
    }

    public List<SelectItem> getDdModeOfOperation() {
        return ddModeOfOperation;
    }

    public void setDdModeOfOperation(List<SelectItem> ddModeOfOperation) {
        this.ddModeOfOperation = ddModeOfOperation;
    }

    public List<SelectItem> getDdNatureOfAdvance() {
        return ddNatureOfAdvance;
    }

    public void setDdNatureOfAdvance(List<SelectItem> ddNatureOfAdvance) {
        this.ddNatureOfAdvance = ddNatureOfAdvance;
    }

    public List<SelectItem> getDdPurposeOfAdvance() {
        return ddPurposeOfAdvance;
    }

    public void setDdPurposeOfAdvance(List<SelectItem> ddPurposeOfAdvance) {
        this.ddPurposeOfAdvance = ddPurposeOfAdvance;
    }

    public List<SelectItem> getDdTypeOfAdvance() {
        return ddTypeOfAdvance;
    }

    public void setDdTypeOfAdvance(List<SelectItem> ddTypeOfAdvance) {
        this.ddTypeOfAdvance = ddTypeOfAdvance;
    }

    public List<SelectItem> getDdValidInvalidFlag() {
        return ddValidInvalidFlag;
    }

    public void setDdValidInvalidFlag(List<SelectItem> ddValidInvalidFlag) {
        this.ddValidInvalidFlag = ddValidInvalidFlag;
    }

    public boolean isAomFlag() {
        return aomFlag;
    }

    public void setAomFlag(boolean aomFlag) {
        this.aomFlag = aomFlag;
    }

    public List<SelectItem> getDdAomTrnRefNo() {
        return ddAomTrnRefNo;
    }

    public void setDdAomTrnRefNo(List<SelectItem> ddAomTrnRefNo) {
        this.ddAomTrnRefNo = ddAomTrnRefNo;
    }

    /** Creates a new instance of AOM */
    public AOM() {
        //INITIALIZATION OF ALL COMBO-BOX LIST
        ddCustSector = new ArrayList<SelectItem>();
        ddCustSubSector = new ArrayList<SelectItem>();
        ddCustStatus = new ArrayList<SelectItem>();
        ddModeOfOperation = new ArrayList<SelectItem>();
        ddGuaranteeCover = new ArrayList<SelectItem>();
        ddNatureOfAdvance = new ArrayList<SelectItem>();
        ddTypeOfAdvance = new ArrayList<SelectItem>();
        ddModeOfAdvance = new ArrayList<SelectItem>();
        ddPurposeOfAdvance = new ArrayList<SelectItem>();
        ddCustTypeCode = new ArrayList<SelectItem>();
        ddCustConstitution = new ArrayList<SelectItem>();
       try {
            ddValidInvalidFlag = new ArrayList<SelectItem>();
            ddValidInvalidFlag.add(new SelectItem("0", ""));
            ddValidInvalidFlag.add(new SelectItem("V", "VALID"));
            ddValidInvalidFlag.add(new SelectItem("I", "INVALID"));

            ddAomTrnRefNo = new ArrayList<SelectItem>();
            ddAomTrnRefNo.add(new SelectItem("0", ""));
            ddAomTrnRefNo.add(new SelectItem("Y", "Yes"));
            ddAomTrnRefNo.add(new SelectItem("N", "No"));

            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();

            List<CbsRefRecTypeTO> CbsRefRecTypeTOs1 = schemeMasterManagementDelegate.getCurrencyCode("22");
            if (CbsRefRecTypeTOs1.size() > 0) {
                ddCustSector = new ArrayList<SelectItem>();
                ddCustSector.add(new SelectItem("0", ""));
                for (CbsRefRecTypeTO refRecTo : CbsRefRecTypeTOs1) {
                    CbsRefRecTypePKTO cbsRefRecTypePKTO = refRecTo.getCbsRefRecTypePKTO();
                    ddCustSector.add(new SelectItem(cbsRefRecTypePKTO.getRefCode(), refRecTo.getRefDesc()));
                }
            }

            List<CbsRefRecTypeTO> CbsRefRecTypeTOs2 = schemeMasterManagementDelegate.getCurrencyCode("23");
            if (CbsRefRecTypeTOs2.size() > 0) {
                ddCustSubSector = new ArrayList<SelectItem>();
                ddCustSubSector.add(new SelectItem("0", ""));
                for (CbsRefRecTypeTO refRecTo : CbsRefRecTypeTOs2) {
                    CbsRefRecTypePKTO cbsRefRecTypePKTO = refRecTo.getCbsRefRecTypePKTO();
                    ddCustSubSector.add(new SelectItem(cbsRefRecTypePKTO.getRefCode(), refRecTo.getRefDesc()));
                }
            }
			else
			{
				ddCustSubSector = new ArrayList<SelectItem>();
                ddCustSubSector.add(new SelectItem("0", ""));
			}

            List<CbsRefRecTypeTO> CbsRefRecTypeTOs = schemeMasterManagementDelegate.getCurrencyCode("25");
            if (CbsRefRecTypeTOs.size() > 0) {
                ddCustStatus = new ArrayList<SelectItem>();
                ddCustStatus.add(new SelectItem("0", ""));
                for (CbsRefRecTypeTO refRecTo : CbsRefRecTypeTOs) {
                    CbsRefRecTypePKTO cbsRefRecTypePKTO = refRecTo.getCbsRefRecTypePKTO();
                    ddCustStatus.add(new SelectItem(cbsRefRecTypePKTO.getRefCode(), refRecTo.getRefDesc()));
                }
            }
			else
			{
				ddCustStatus = new ArrayList<SelectItem>();
                ddCustStatus.add(new SelectItem("0", ""));
			}

            List<CbsRefRecTypeTO> CbsRefRecTypeTOs5 = schemeMasterManagementDelegate.getCurrencyCode("27");
            if (CbsRefRecTypeTOs5.size() > 0) {
                ddModeOfOperation = new ArrayList<SelectItem>();
                ddModeOfOperation.add(new SelectItem("0", ""));
                for (CbsRefRecTypeTO refRecTo : CbsRefRecTypeTOs5) {
                    CbsRefRecTypePKTO cbsRefRecTypePKTO = refRecTo.getCbsRefRecTypePKTO();
                    ddModeOfOperation.add(new SelectItem(cbsRefRecTypePKTO.getRefCode(), refRecTo.getRefDesc()));
                }
            }
			else
			{
				ddModeOfOperation = new ArrayList<SelectItem>();
                ddModeOfOperation.add(new SelectItem("0", ""));
			}

            List<CbsRefRecTypeTO> CbsRefRecTypeTOs6 = schemeMasterManagementDelegate.getCurrencyCode("33");
            if (CbsRefRecTypeTOs6.size() > 0) {
              
                for (CbsRefRecTypeTO refRecTo : CbsRefRecTypeTOs6) {
                    CbsRefRecTypePKTO cbsRefRecTypePKTO = refRecTo.getCbsRefRecTypePKTO();
                    ddGuaranteeCover.add(new SelectItem(cbsRefRecTypePKTO.getRefCode(), refRecTo.getRefDesc()));
                }
            }
			else
			{
				ddGuaranteeCover = new ArrayList<SelectItem>();
                ddGuaranteeCover.add(new SelectItem("0", ""));
			}

            List<CbsRefRecTypeTO> CbsRefRecTypeTOs7 = schemeMasterManagementDelegate.getCurrencyCode("38");
            if (CbsRefRecTypeTOs7.size() > 0) {
                ddNatureOfAdvance = new ArrayList<SelectItem>();
                ddNatureOfAdvance.add(new SelectItem("0", ""));
                for (CbsRefRecTypeTO refRecTo : CbsRefRecTypeTOs7) {
                    CbsRefRecTypePKTO cbsRefRecTypePKTO = refRecTo.getCbsRefRecTypePKTO();
                    ddNatureOfAdvance.add(new SelectItem(cbsRefRecTypePKTO.getRefCode(), refRecTo.getRefDesc()));
                }
            }
			else
			{
				ddNatureOfAdvance = new ArrayList<SelectItem>();
                ddNatureOfAdvance.add(new SelectItem("0", ""));
			}

            List<CbsRefRecTypeTO> CbsRefRecTypeTOs8 = schemeMasterManagementDelegate.getCurrencyCode("39");
            if (CbsRefRecTypeTOs8.size() > 0) {
                ddTypeOfAdvance = new ArrayList<SelectItem>();
                ddTypeOfAdvance.add(new SelectItem("0", ""));
                for (CbsRefRecTypeTO refRecTo : CbsRefRecTypeTOs8) {
                    CbsRefRecTypePKTO cbsRefRecTypePKTO = refRecTo.getCbsRefRecTypePKTO();
                    ddTypeOfAdvance.add(new SelectItem(cbsRefRecTypePKTO.getRefCode(), refRecTo.getRefDesc()));
                }
            }
			else
			{
				ddTypeOfAdvance = new ArrayList<SelectItem>();
                ddTypeOfAdvance.add(new SelectItem("0", ""));
			}

            List<CbsRefRecTypeTO> CbsRefRecTypeTOs9 = schemeMasterManagementDelegate.getCurrencyCode("40");
            if (CbsRefRecTypeTOs9.size() > 0) {
                ddModeOfAdvance = new ArrayList<SelectItem>();
                ddModeOfAdvance.add(new SelectItem("0", ""));
                for (CbsRefRecTypeTO refRecTo : CbsRefRecTypeTOs9) {
                    CbsRefRecTypePKTO cbsRefRecTypePKTO = refRecTo.getCbsRefRecTypePKTO();
                    ddModeOfAdvance.add(new SelectItem(cbsRefRecTypePKTO.getRefCode(), refRecTo.getRefDesc()));
                }
            }
			else
			{
				ddModeOfAdvance = new ArrayList<SelectItem>();
                ddModeOfAdvance.add(new SelectItem("0", ""));
			}

            List<CbsRefRecTypeTO> CbsRefRecTypeTOs10 = schemeMasterManagementDelegate.getCurrencyCode("41");
            if (CbsRefRecTypeTOs10.size() > 0) {
                ddPurposeOfAdvance = new ArrayList<SelectItem>();
                ddPurposeOfAdvance.add(new SelectItem("0", ""));
                for (CbsRefRecTypeTO refRecTo : CbsRefRecTypeTOs10) {
                    CbsRefRecTypePKTO cbsRefRecTypePKTO = refRecTo.getCbsRefRecTypePKTO();
                    ddPurposeOfAdvance.add(new SelectItem(cbsRefRecTypePKTO.getRefCode(), refRecTo.getRefDesc()));
                }
            }
			else
			{
				ddPurposeOfAdvance = new ArrayList<SelectItem>();
                ddPurposeOfAdvance.add(new SelectItem("0", ""));
			}

            List<CbsRefRecTypeTO> CbsRefRecTypeTOs3 = schemeMasterManagementDelegate.getCurrencyCode("43");
            if (CbsRefRecTypeTOs3.size() > 0) {
                ddCustTypeCode = new ArrayList<SelectItem>();
                ddCustTypeCode.add(new SelectItem("0", ""));
                for (CbsRefRecTypeTO refRecTo : CbsRefRecTypeTOs3) {
                    CbsRefRecTypePKTO cbsRefRecTypePKTO = refRecTo.getCbsRefRecTypePKTO();
                    ddCustTypeCode.add(new SelectItem(cbsRefRecTypePKTO.getRefCode(), refRecTo.getRefDesc()));
                }
            }
			else
			{
				ddCustTypeCode = new ArrayList<SelectItem>();
                ddCustTypeCode.add(new SelectItem("0", ""));
               
			}

            List<CbsRefRecTypeTO> CbsRefRecTypeTOs4 = schemeMasterManagementDelegate.getCurrencyCode("44");
            if (CbsRefRecTypeTOs4.size() > 0) {
                ddCustConstitution = new ArrayList<SelectItem>();
                ddCustConstitution.add(new SelectItem("0", ""));
                for (CbsRefRecTypeTO refRecTo : CbsRefRecTypeTOs4) {
                    CbsRefRecTypePKTO cbsRefRecTypePKTO = refRecTo.getCbsRefRecTypePKTO();
                    ddCustConstitution.add(new SelectItem(cbsRefRecTypePKTO.getRefCode(), refRecTo.getRefDesc()));
                }
            }
			else
			{
				ddCustConstitution = new ArrayList<SelectItem>();
                ddCustConstitution.add(new SelectItem("0", ""));
			}
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void selectOpenMatrix() {
        schemeMaster.setMessage("");
        try {
            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
            CbsSchemeAccountOpenMatrixTO cbsSchemeAccountOpenMatrixTO = schemeMasterManagementDelegate.getSelectAccountOpenMatrix(schemeMaster.getSchemeCode());
            if (cbsSchemeAccountOpenMatrixTO != null) {
                if (cbsSchemeAccountOpenMatrixTO.getMatrixDescription() == null || cbsSchemeAccountOpenMatrixTO.getMatrixDescription().equalsIgnoreCase("")) {
                    this.setMatrixDesc("");
                } else {
                    this.setMatrixDesc(cbsSchemeAccountOpenMatrixTO.getMatrixDescription());
                }
                if (cbsSchemeAccountOpenMatrixTO.getValidInvalidFlag() == null || cbsSchemeAccountOpenMatrixTO.getValidInvalidFlag().equalsIgnoreCase("")) {
                    this.setValidInvalidFlag("0");
                } else {
                    this.setValidInvalidFlag(cbsSchemeAccountOpenMatrixTO.getValidInvalidFlag());
                }
                if (cbsSchemeAccountOpenMatrixTO.getCustStatus() == null || cbsSchemeAccountOpenMatrixTO.getCustStatus().equalsIgnoreCase("")) {
                    this.setCustStatus("0");
                } else {
                    this.setCustStatus(cbsSchemeAccountOpenMatrixTO.getCustStatus());
                }
                if (cbsSchemeAccountOpenMatrixTO.getCustSector() == null || cbsSchemeAccountOpenMatrixTO.getCustSector().equalsIgnoreCase("")) {
                    this.setCustSector("0");
                } else {
                    this.setCustSector(cbsSchemeAccountOpenMatrixTO.getCustSector());
                }
                if (cbsSchemeAccountOpenMatrixTO.getCustSubSector() == null || cbsSchemeAccountOpenMatrixTO.getCustSubSector().equalsIgnoreCase("")) {
                    this.setCustSubSector("0");
                } else {
                    this.setCustSubSector(cbsSchemeAccountOpenMatrixTO.getCustSubSector());
                }
                if (cbsSchemeAccountOpenMatrixTO.getCustTypeCode() == null || cbsSchemeAccountOpenMatrixTO.getCustTypeCode().equalsIgnoreCase("")) {
                    this.setCustTypeCode("0");
                } else {
                    this.setCustTypeCode(cbsSchemeAccountOpenMatrixTO.getCustTypeCode());
                }
                if (cbsSchemeAccountOpenMatrixTO.getCustConstitution() == null || cbsSchemeAccountOpenMatrixTO.getCustConstitution().equalsIgnoreCase("")) {
                    this.setCustConstitution("0");
                } else {
                    this.setCustConstitution(cbsSchemeAccountOpenMatrixTO.getCustConstitution());
                }
                if (cbsSchemeAccountOpenMatrixTO.getCusTempId() == null || cbsSchemeAccountOpenMatrixTO.getCusTempId().equalsIgnoreCase("")) {
                    this.setCustEmpId("");
                } else {
                    this.setCustEmpId(cbsSchemeAccountOpenMatrixTO.getCusTempId());
                }
                if (cbsSchemeAccountOpenMatrixTO.getCustOtherBank() == null || cbsSchemeAccountOpenMatrixTO.getCustOtherBank().equalsIgnoreCase("")) {
                    this.setCustOtherBank("");
                } else {
                    this.setCustOtherBank(cbsSchemeAccountOpenMatrixTO.getCustOtherBank());
                }
                if (cbsSchemeAccountOpenMatrixTO.getModeOfOperation() == null || cbsSchemeAccountOpenMatrixTO.getModeOfOperation().equalsIgnoreCase("")) {
                    this.setModeOfOperation("0");
                } else {
                    this.setModeOfOperation(cbsSchemeAccountOpenMatrixTO.getModeOfOperation());
                }
                if (cbsSchemeAccountOpenMatrixTO.getGuaranteeCover() == null || cbsSchemeAccountOpenMatrixTO.getGuaranteeCover().equalsIgnoreCase("")) {
                    this.setGuaranteeCover("0");
                } else {
                    this.setGuaranteeCover(cbsSchemeAccountOpenMatrixTO.getGuaranteeCover());
                }
                if (cbsSchemeAccountOpenMatrixTO.getNatureOfAdvance() == null || cbsSchemeAccountOpenMatrixTO.getNatureOfAdvance().equalsIgnoreCase("")) {
                    this.setNatureOfAdvance("0");
                } else {
                    this.setNatureOfAdvance(cbsSchemeAccountOpenMatrixTO.getNatureOfAdvance());
                }
                if (cbsSchemeAccountOpenMatrixTO.getTypeOfAdvance() == null || cbsSchemeAccountOpenMatrixTO.getTypeOfAdvance().equalsIgnoreCase("")) {
                    this.setTypeOfAdvance("0");
                } else {
                    this.setTypeOfAdvance(cbsSchemeAccountOpenMatrixTO.getTypeOfAdvance());
                }
                if (cbsSchemeAccountOpenMatrixTO.getModeOfAdvance() == null || cbsSchemeAccountOpenMatrixTO.getModeOfAdvance().equalsIgnoreCase("")) {
                    this.setModeOfAdvance("0");
                } else {
                    this.setModeOfAdvance(cbsSchemeAccountOpenMatrixTO.getModeOfAdvance());
                }
                if (cbsSchemeAccountOpenMatrixTO.getPurposeOfAdvance() == null || cbsSchemeAccountOpenMatrixTO.getPurposeOfAdvance().equalsIgnoreCase("")) {
                    this.setPurposeOfAdvance("0");
                } else {
                    this.setPurposeOfAdvance(cbsSchemeAccountOpenMatrixTO.getPurposeOfAdvance());
                }
                if (cbsSchemeAccountOpenMatrixTO.getCustMinorFlag() == null || cbsSchemeAccountOpenMatrixTO.getCustMinorFlag().equalsIgnoreCase("")) {
                    this.setCustMinorFlag("0");
                } else {
                    this.setCustMinorFlag(cbsSchemeAccountOpenMatrixTO.getCustMinorFlag());
                }
                if (cbsSchemeAccountOpenMatrixTO.getChequeAllowedFlag() == null || cbsSchemeAccountOpenMatrixTO.getChequeAllowedFlag().equalsIgnoreCase("")) {
                    this.setChequedAllowedFlag("0");
                } else {
                    this.setChequedAllowedFlag(cbsSchemeAccountOpenMatrixTO.getChequeAllowedFlag());
                }
                if (cbsSchemeAccountOpenMatrixTO.getAccountTurnDetail() == null || cbsSchemeAccountOpenMatrixTO.getAccountTurnDetail().equalsIgnoreCase("")) {
                    this.setAccountTurnDetail("0");
                } else {
                    this.setAccountTurnDetail(cbsSchemeAccountOpenMatrixTO.getAccountTurnDetail());
                }
                if (cbsSchemeAccountOpenMatrixTO.getAccountOwnership() == null || cbsSchemeAccountOpenMatrixTO.getAccountOwnership().equalsIgnoreCase("")) {
                    this.setAccountOwnership("");
                } else {
                    this.setAccountOwnership(cbsSchemeAccountOpenMatrixTO.getAccountOwnership());
                }
                if (cbsSchemeAccountOpenMatrixTO.getDelFlag() == null || cbsSchemeAccountOpenMatrixTO.getDelFlag().equalsIgnoreCase("")) {
                    this.setDelFlagmat("0");
                } else {
                    this.setDelFlagmat(cbsSchemeAccountOpenMatrixTO.getDelFlag());
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

    public void refreshAOMForm() {
        this.setMatrixDesc("");
        this.setValidInvalidFlag("0");
        this.setCustStatus("0");
        this.setCustSector("0");
        this.setCustSubSector("0");
        this.setCustTypeCode("0");
        this.setCustConstitution("0");
        this.setCustEmpId("");
        this.setCustOtherBank("");
        this.setModeOfOperation("0");
        this.setGuaranteeCover("0");
        this.setNatureOfAdvance("0");
        this.setTypeOfAdvance("0");
        this.setModeOfAdvance("0");
        this.setPurposeOfAdvance("0");
        this.setCustMinorFlag("0");
        this.setChequedAllowedFlag("0");
        this.setAccountTurnDetail("0");
        this.setAccountOwnership("");
        this.setDelFlagmat("0");
    }

    public void enableAOMForm() {
        this.aomFlag = false;
    }

    public void disableAOMForm() {
        this.aomFlag = true;
    }
}
