package com.hrms.web.controller.personnel;

import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.HrMembershipDetailTO;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.to.HrPersonnelDependentTO;
import com.hrms.common.to.HrPersonnelDetailsPKTO;
import com.hrms.common.to.HrPersonnelDetailsTO;
import com.hrms.common.to.HrPersonnelHobbyTO;
import com.hrms.common.to.HrPersonnelLangTO;
import com.hrms.common.to.HrPersonnelPreviousCompanyTO;
import com.hrms.common.to.HrPersonnelQualificationTO;
import com.hrms.common.to.HrPersonnelReferenceTO;
import com.hrms.common.to.HrPersonnelTransportTO;
import com.hrms.common.utils.HrmsUtil;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.exception.WebException;
import com.hrms.web.pojo.DependentGrid;
import com.hrms.web.pojo.EmployeeGrid;
import com.hrms.web.pojo.HobbyGrid;
import com.hrms.web.pojo.InstallmentGrid;
import com.hrms.web.pojo.LanguageGrid;
import com.hrms.web.pojo.MembershipGrid;
import com.hrms.web.pojo.PreviousCompanyGrid;
import com.hrms.web.pojo.QualificationGrid;
import com.hrms.web.pojo.ReferenceGrid;
import com.hrms.web.pojo.TransportGrid;
import com.hrms.web.delegate.PersonnelDelegate;
import com.hrms.web.utils.LocalizationUtil;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class PersonnelUtil {
    
    public SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    public SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
    public NumberFormat formatter = new DecimalFormat("0.00");
    private static final Logger logger = Logger.getLogger(PersonnelUtil.class);
    public SimpleDateFormat hmmFormat = new SimpleDateFormat("h:mm");
    private String message;

//    public String validateAmount(String amountString) {
//        try {
//            if (!amountString.matches("[0-9].*")) {
//                return "Loan Amount Is In Incorrect Format !!";
//            }
//            String period = ".";
//            int len = amountString.length();
//            int result = 0;
//            if (len > 0) {
//                int start = amountString.indexOf(period);
//                while (start != -1) {
//                    result++;
//                    start = amountString.indexOf(period, start + 1);
//                }
//                if (result > 1) {
//                    return "Multiple Occurances Of Period (.) Is Not Allowed !!";
//                }
//            } else {
//                return "Please Fill Amount !!";
//            }
//            return "OK";
//        } catch (Exception e) {
//            logger.error("Exception occured while executing method validateAmount()", e);
//            return "Exception Occurred !!";
//        }
//    }
    public boolean validate24HourTime(String time) {
        try {
            if (time.matches("[0-9:]*") && time.length() == 5 && time.charAt(2) == ':') {
                int hour = Integer.parseInt(time.substring(0, 2));
                int minute = Integer.parseInt(time.substring(3));
                if (hour < 0 || minute < 0) {
                    setMessage("Time in " + time + " cannot be negative !!");
                    return false;
                }
                if (minute >= 60) {
                    setMessage("Start Minute in " + time + " Cannot be equal to or greater than 60");
                    return false;
                }
                if (hour >= 24) {
                    setMessage("Hour in " + time + " Cannot be equal to or greater than 24");
                    return false;
                }
            } else {
                setMessage("Incorrect Time Format !!");
                return false;
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method validate24HourTime()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return true;
    }
    
    public List<InstallmentGrid> calculateAdvanceInstallments(double amount, int noOfInstallment, char periodicity, String startingDate) throws WebException {
        double principal;
        int iPeriod = 0;
        List<InstallmentGrid> table = new ArrayList<InstallmentGrid>();
        InstallmentGrid currentRow = null;
        try {
            principal = HrmsUtil.round(amount / noOfInstallment, 2);
            if (periodicity == 'M') {
                iPeriod = 1;
            } else if (periodicity == 'Q') {
                iPeriod = 3;
            } else if (periodicity == 'H') {
                iPeriod = 6;
            } else if (periodicity == 'Y') {
                iPeriod = 12;
            }
            int i = 0;
            while (i < noOfInstallment) {
                currentRow = new InstallmentGrid();
                currentRow.setSno(++i);
                currentRow.setDueDate(dmyFormat.format(ymdFormat.parse(startingDate)));
                currentRow.setPrincipal(principal);
                currentRow.setCurrentInstallment(i + "/" + noOfInstallment);
                currentRow.setRepayFlag('N');
                table.add(currentRow);
                startingDate = HrmsUtil.monthAdd(startingDate, iPeriod);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method calculateAdvanceInstallments()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return table;
    }
    
    public List<InstallmentGrid> calculateLoanInstallments(String loanPlan, double amount, double ROI, int noOfInstallment, char periodicity, String startingDate) throws WebException {
        double tmpInterest = 0.0,
                tmpPrincipal = 0.0,
                A1 = 0.0,
                A2 = 0.0,
                K1 = 0.0,
                K2 = 0.0,
                K3 = 0.0,
                tmpAmount = 0.0,
                tmpROI = 0.0,
                tmpInstallment = 0.0;
        int noOfInstallmentPerYear = 0,
                iPeriod = 0,
                i = 0;
        List<InstallmentGrid> table = new ArrayList<InstallmentGrid>();
        InstallmentGrid currentRow = null;
        try {
            if (periodicity == 'M') {
                noOfInstallmentPerYear = 12;
                iPeriod = 1;
            } else if (periodicity == 'Q') {
                noOfInstallmentPerYear = 4;
                iPeriod = 3;
            } else if (periodicity == 'H') {
                noOfInstallmentPerYear = 2;
                iPeriod = 6;
            } else if (periodicity == 'Y') {
                noOfInstallmentPerYear = 1;
                iPeriod = 12;
            }
            if (loanPlan.equalsIgnoreCase("EMI")) {
                tmpAmount = amount;
                tmpROI = ROI;
                K1 = ROI / (noOfInstallmentPerYear * 100);
                tmpInterest = tmpAmount * K1;
                K2 = 1 + K1;
                K3 = Math.pow(K2, noOfInstallment);
                A1 = (K1 * K3) / (K3 - 1);
                A2 = tmpAmount * A1;
                i = 0;
                while (i < noOfInstallment) {
                    tmpInterest = tmpAmount * K1;
                    tmpPrincipal = A2 - tmpInterest;
                    tmpAmount = tmpAmount - A2 + tmpInterest;
                    currentRow = new InstallmentGrid();
                    currentRow.setSno(++i);
                    currentRow.setPrincipal(Double.parseDouble(formatter.format(HrmsUtil.round(tmpPrincipal, 2))));
                    currentRow.setInterestAmount(Double.parseDouble(formatter.format(HrmsUtil.round(tmpInterest, 2))));
                    currentRow.setTotalAmount(Double.parseDouble(formatter.format(HrmsUtil.round(tmpPrincipal, 2) + HrmsUtil.round(tmpInterest, 2))));
                    currentRow.setDueDate(dmyFormat.format(ymdFormat.parse(startingDate)));
                    currentRow.setCurrentInstallment(i + "/" + noOfInstallment);
                    currentRow.setRepayFlag('N');
                    table.add(currentRow);
                    startingDate = HrmsUtil.monthAdd(startingDate, iPeriod);
                }
            } else if (loanPlan.equalsIgnoreCase("EPRP")) {
                tmpAmount = amount;
                tmpROI = ROI;
                tmpPrincipal = tmpAmount / noOfInstallment;
                i = 0;
                while (i < noOfInstallment) {
                    tmpInterest = ((tmpAmount * tmpROI) / (noOfInstallmentPerYear * 100.0));
                    tmpInstallment = tmpPrincipal + tmpInterest;
                    tmpAmount = tmpAmount - tmpPrincipal;
                    currentRow = new InstallmentGrid();
                    currentRow.setSno(++i);
                    currentRow.setPrincipal(Double.parseDouble(formatter.format(HrmsUtil.round(tmpPrincipal, 2))));
                    currentRow.setInterestAmount(Double.parseDouble(formatter.format(HrmsUtil.round(tmpInterest, 2))));
                    currentRow.setTotalAmount(Double.parseDouble(formatter.format(HrmsUtil.round(tmpPrincipal, 2) + HrmsUtil.round(tmpInterest, 2))));
                    currentRow.setDueDate(dmyFormat.format(ymdFormat.parse(startingDate)));
                    currentRow.setCurrentInstallment(i + "/" + noOfInstallment);
                    currentRow.setRepayFlag('N');
                    table.add(currentRow);
                    startingDate = HrmsUtil.monthAdd(startingDate, iPeriod);
                }
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method calculateLoanInstallments()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return table;
    }
    
    public List<EmployeeGrid> getEmployeeTableData(int compCode, String empSearchCriteria, String empSearchValue) throws WebException {
        List<EmployeeGrid> empSearchTable = new ArrayList<EmployeeGrid>();
        try {
            PersonnelDelegate personnelDelegate = new PersonnelDelegate();
            List<HrPersonnelDetailsTO> hrPersonnelDetailsTOs = personnelDelegate.getHrPersonnelData(compCode, empSearchCriteria, empSearchValue);
            int i = 0;
            for (HrPersonnelDetailsTO hrPersonnelDetailsTO : hrPersonnelDetailsTOs) {
                EmployeeGrid currentEmpRow = new EmployeeGrid();
                currentEmpRow.setSno(++i);
                currentEmpRow.setCompCode(hrPersonnelDetailsTO.getHrPersonnelDetailsPKTO().getCompCode());
                currentEmpRow.setDefaultComp(hrPersonnelDetailsTO.getDefaultComp());
                currentEmpRow.setEmpId(hrPersonnelDetailsTO.getEmpId());
                currentEmpRow.setEmpName(hrPersonnelDetailsTO.getEmpName());
                currentEmpRow.setEmpFName(hrPersonnelDetailsTO.getEmpFirName());
                currentEmpRow.setEmpMName(hrPersonnelDetailsTO.getEmpMidName());
                currentEmpRow.setEmpLName(hrPersonnelDetailsTO.getEmpLastName());
                currentEmpRow.setEmpAddress(hrPersonnelDetailsTO.getEmpContAdd());
                currentEmpRow.setEmpConCity(hrPersonnelDetailsTO.getEmpContCity());
                currentEmpRow.setEmpConState(hrPersonnelDetailsTO.getEmpContState());
                currentEmpRow.setEmpConPin(hrPersonnelDetailsTO.getEmpContPin());
                currentEmpRow.setEmpPhone(hrPersonnelDetailsTO.getEmpContTel());
                currentEmpRow.setEmpPerAddress(hrPersonnelDetailsTO.getEmpPermAdd());
                currentEmpRow.setEmpPerCity(hrPersonnelDetailsTO.getEmpPermCity());
                currentEmpRow.setEmpPerState(hrPersonnelDetailsTO.getEmpPermState());
                currentEmpRow.setEmpPerPin(hrPersonnelDetailsTO.getEmpPermPin());
                currentEmpRow.setEmpPerPhone(hrPersonnelDetailsTO.getEmpPermTel());
                currentEmpRow.setEmpCode(hrPersonnelDetailsTO.getHrPersonnelDetailsPKTO().getEmpCode());
                currentEmpRow.setDepartmentCode(hrPersonnelDetailsTO.getDeptCode());
                currentEmpRow.setSubDepartmentCode(hrPersonnelDetailsTO.getSubdeptCode());
                currentEmpRow.setDesgCode(hrPersonnelDetailsTO.getDesgCode());
                currentEmpRow.setGradeCode(hrPersonnelDetailsTO.getGradeCode());
                currentEmpRow.setEmpCardNo(hrPersonnelDetailsTO.getEmpCardNo());
                currentEmpRow.setDateOfBirth(dmyFormat.format(hrPersonnelDetailsTO.getBirthDate()));
                currentEmpRow.setSex(hrPersonnelDetailsTO.getSex());
                currentEmpRow.setJoiningDate(dmyFormat.format(hrPersonnelDetailsTO.getJoinDate()));
                currentEmpRow.setStatus(hrPersonnelDetailsTO.getEmpStatus());
                currentEmpRow.setWorkStatusCode(hrPersonnelDetailsTO.getWorkStatus());
                currentEmpRow.setSex(hrPersonnelDetailsTO.getSex());
                currentEmpRow.setBlockCode(hrPersonnelDetailsTO.getBlock());
                currentEmpRow.setUnitCode(hrPersonnelDetailsTO.getUnitName());
                currentEmpRow.setEmployeeTypeCode(hrPersonnelDetailsTO.getEmpType());
                currentEmpRow.setCategoryCode(hrPersonnelDetailsTO.getCatgCode());
                currentEmpRow.setZoneCode(hrPersonnelDetailsTO.getZones());
                currentEmpRow.setLocationCode(hrPersonnelDetailsTO.getLocatCode());
                currentEmpRow.setBankCode(hrPersonnelDetailsTO.getBankCode());
                currentEmpRow.setBankAccountNo(hrPersonnelDetailsTO.getBankAccountCode());
                currentEmpRow.setFinanceLoanAccountCode(hrPersonnelDetailsTO.getFinAccountCode());
                currentEmpRow.setPaymentMode(hrPersonnelDetailsTO.getPayMode());
                currentEmpRow.setFatherName_husbandName(hrPersonnelDetailsTO.getFathHusName());
                currentEmpRow.setFatherName_husbandOcc(hrPersonnelDetailsTO.getFatherHusOcc());
                currentEmpRow.setFatherName_husbandDesg(hrPersonnelDetailsTO.getFatherHusDesig());
                currentEmpRow.setFatherName_husbandOrg(hrPersonnelDetailsTO.getFatherHusOrg());
                currentEmpRow.setFatherName_husbandPhone(hrPersonnelDetailsTO.getFatherHusPhone());
                currentEmpRow.setBirthCity(hrPersonnelDetailsTO.getBirthCity());
                currentEmpRow.setBirthState(hrPersonnelDetailsTO.getBirthState());
                currentEmpRow.setNation(hrPersonnelDetailsTO.getNation());
                currentEmpRow.setHeight(hrPersonnelDetailsTO.getHeight());
                currentEmpRow.setWeight(hrPersonnelDetailsTO.getWeight());
                currentEmpRow.setHealth(hrPersonnelDetailsTO.getHealth());
                currentEmpRow.setChest(hrPersonnelDetailsTO.getChest());
                currentEmpRow.setBloodGroup(hrPersonnelDetailsTO.getBloodGrp());
                currentEmpRow.setChildren(hrPersonnelDetailsTO.getChildren());
                currentEmpRow.setMaritalStatus(hrPersonnelDetailsTO.getMaritalStatus());
                if (hrPersonnelDetailsTO.getWeddingDate() != null) {
                    currentEmpRow.setWeddingDate(dmyFormat.format(hrPersonnelDetailsTO.getWeddingDate()));
                }
                currentEmpRow.setReligion(hrPersonnelDetailsTO.getReligion());
                currentEmpRow.setEmailId(hrPersonnelDetailsTO.getEmailId());
                currentEmpRow.setVisaDetail(hrPersonnelDetailsTO.getVisaDet());
                currentEmpRow.setAdolescentNo(hrPersonnelDetailsTO.getCertAdosNo());
                currentEmpRow.setAdolescentTokenNo(hrPersonnelDetailsTO.getCertTokNo());
                if (hrPersonnelDetailsTO.getCertAdosDate() != null) {
                    currentEmpRow.setAdolescentDate(dmyFormat.format(hrPersonnelDetailsTO.getCertAdosDate()));
                }
                currentEmpRow.setAdolescentReferenceNo(hrPersonnelDetailsTO.getCertRef());
                currentEmpRow.setAdolescentRelay(hrPersonnelDetailsTO.getRelay());
                if (hrPersonnelDetailsTO.getRelayDate() != null) {
                    currentEmpRow.setAdolescentRelayDate(dmyFormat.format(hrPersonnelDetailsTO.getRelayDate()));
                }
                if (hrPersonnelDetailsTO.getBasicSal() != null) {
                    currentEmpRow.setBasicSalary(hrPersonnelDetailsTO.getBasicSal());
                } else {
                    currentEmpRow.setBasicSalary(0.00);
                }
                if (hrPersonnelDetailsTO.getEsiMember() != null) {
                    currentEmpRow.setESIMembership(hrPersonnelDetailsTO.getEsiMember());
                }
                if (hrPersonnelDetailsTO.getPfMember() != null) {
                    currentEmpRow.setPFMembership(hrPersonnelDetailsTO.getPfMember());
                }
                if (hrPersonnelDetailsTO.getVpfMember() != null) {
                    currentEmpRow.setVPFMembership(hrPersonnelDetailsTO.getVpfMember());
                }
                if (hrPersonnelDetailsTO.getTrustMember() != null) {
                    currentEmpRow.setTRUSTMembership(hrPersonnelDetailsTO.getTrustMember());
                }
                currentEmpRow.setCustomerId(hrPersonnelDetailsTO.getCustomerId());
                currentEmpRow.setPfAccount(hrPersonnelDetailsTO.getPfAccount());
                currentEmpRow.setBaseBranch(hrPersonnelDetailsTO.getBaseBranch());
                currentEmpRow.setDesignation(hrPersonnelDetailsTO.getDesgCode());
                currentEmpRow.setUanNo(hrPersonnelDetailsTO.getUanNumber());
                empSearchTable.add(currentEmpRow);
            }
            
        } catch (Exception e) {
            logger.error("Exception occured while executing method getEmployeeTableData()", e);
            this.setMessage(e.getMessage());
        }
        return empSearchTable;
    }
    
    public List<ReferenceGrid> getReferenceTableData(long empCode) throws WebException {
        List<ReferenceGrid> referenceGrids = new ArrayList<ReferenceGrid>();
        try {
            PersonnelDelegate personnelDelegate = new PersonnelDelegate();
            List<HrPersonnelReferenceTO> hrPersonnelReferenceTOs = personnelDelegate.getReferenceTableData(empCode);
            int i = 0;
            for (HrPersonnelReferenceTO hrPersonnelReferenceTO : hrPersonnelReferenceTOs) {
                ReferenceGrid currentreferenceRow = new ReferenceGrid();
                currentreferenceRow.setSno(++i);
                currentreferenceRow.setAddress(hrPersonnelReferenceTO.getRefAdd());
                currentreferenceRow.setCity(hrPersonnelReferenceTO.getRefCity());
                currentreferenceRow.setRefCode(hrPersonnelReferenceTO.getHrPersonnelReferencePKTO().getRefCode());
                currentreferenceRow.setEmpCode(hrPersonnelReferenceTO.getHrPersonnelReferencePKTO().getEmpCode());
                currentreferenceRow.setName(hrPersonnelReferenceTO.getRefName());
                currentreferenceRow.setOccupation(hrPersonnelReferenceTO.getRefOcc());
                currentreferenceRow.setPhone(hrPersonnelReferenceTO.getRefPhone());
                currentreferenceRow.setPin(hrPersonnelReferenceTO.getRefPin());
                currentreferenceRow.setState(hrPersonnelReferenceTO.getRefState());
                referenceGrids.add(currentreferenceRow);
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method getReferenceTableData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method getReferenceTableData()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getReferenceTableData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return referenceGrids;
    }
    
    public List<QualificationGrid> getQualificationTableData(long empCode) throws WebException {
        List<QualificationGrid> qualificationsGrids = new ArrayList<QualificationGrid>();
        try {
            PersonnelDelegate personnelDelegate = new PersonnelDelegate();
            List<HrPersonnelQualificationTO> hrPersonnelQualificationTOs = personnelDelegate.getQualificationTableData(empCode);
            int i = 0;
            for (HrPersonnelQualificationTO hrPersonnelQualificationTO : hrPersonnelQualificationTOs) {
                QualificationGrid currentRow = new QualificationGrid();
                currentRow.setSno(++i);
                currentRow.setDivision(hrPersonnelQualificationTO.getDivision());
                if (hrPersonnelQualificationTO.getDuration() != null) {
                    currentRow.setDuration(hrPersonnelQualificationTO.getDuration());
                } else {
                    currentRow.setDuration(0);
                }
                currentRow.setEmpCode(hrPersonnelQualificationTO.getHrPersonnelQualificationPKTO().getEmpCode());
                currentRow.setInstituteName(hrPersonnelQualificationTO.getInstituteName());
                currentRow.setMarks(hrPersonnelQualificationTO.getPercentageMarks());
                currentRow.setQualificationCode(hrPersonnelQualificationTO.getHrPersonnelQualificationPKTO().getQualCode());
                List<HrMstStructTO> hrMstStructTOs1 = personnelDelegate.getInitialData(hrPersonnelQualificationTO.getHrPersonnelQualificationPKTO().getCompCode(), hrPersonnelQualificationTO.getHrPersonnelQualificationPKTO().getQualCode());
                for (HrMstStructTO hrMstStructTO : hrMstStructTOs1) {
                    currentRow.setQualificationDescription(hrMstStructTO.getDescription());
                }
                List<HrMstStructTO> hrMstStructTOs2 = personnelDelegate.getInitialData(hrPersonnelQualificationTO.getHrPersonnelQualificationPKTO().getCompCode(), hrPersonnelQualificationTO.getSpecializationCode());
                for (HrMstStructTO hrMstStructTO : hrMstStructTOs2) {
                    currentRow.setSpecializationDescription(hrMstStructTO.getDescription());
                }
                currentRow.setSpecializationCode(hrPersonnelQualificationTO.getSpecializationCode());
                currentRow.setSubjectsName(hrPersonnelQualificationTO.getSubName());
                currentRow.setYear(hrPersonnelQualificationTO.getYear());
                qualificationsGrids.add(currentRow);
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method getQualificationTableData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method getQualificationTableData()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getQualificationTableData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return qualificationsGrids;
    }
    
    public List<DependentGrid> getDependentTableData(long empCode) throws WebException {
        List<DependentGrid> dependentGrids = new ArrayList<DependentGrid>();
        try {
            PersonnelDelegate personnelDelegate = new PersonnelDelegate();
            List<HrPersonnelDependentTO> hrPersonnelDependentTOs = personnelDelegate.getDependentTableData(empCode);
            int i = 0;
            for (HrPersonnelDependentTO hrPersonnelDependentTO : hrPersonnelDependentTOs) {
                DependentGrid currentdependentRow = new DependentGrid();
                currentdependentRow.setSno(++i);
                currentdependentRow.setName(hrPersonnelDependentTO.getDepName());
                currentdependentRow.setPetName(hrPersonnelDependentTO.getPetName());
                currentdependentRow.setAge(hrPersonnelDependentTO.getDepAge());
                currentdependentRow.setRelation(hrPersonnelDependentTO.getDepRel());
                currentdependentRow.setOccupation(hrPersonnelDependentTO.getOccupation());
                currentdependentRow.setEmpCode(hrPersonnelDependentTO.getHrPersonnelDependentPKTO().getEmpCode());
                currentdependentRow.setDependentCode(hrPersonnelDependentTO.getHrPersonnelDependentPKTO().getDependentCode());
                dependentGrids.add(currentdependentRow);
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method getDependentTableData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method getDependentTableData()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getDependentTableData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return dependentGrids;
    }
    
    public List<PreviousCompanyGrid> getExperienceData(long empCode) throws WebException {
        List<PreviousCompanyGrid> previousCompanyGrids = new ArrayList<PreviousCompanyGrid>();
        try {
            PersonnelDelegate personnelDelegate = new PersonnelDelegate();
            List<HrPersonnelPreviousCompanyTO> hrPersonnelPreviousCompanyTOs = personnelDelegate.getExperienceData(empCode);
            int i = 0;
            for (HrPersonnelPreviousCompanyTO hrPersonnelPreviousCompanyTO : hrPersonnelPreviousCompanyTOs) {
                PreviousCompanyGrid currentpreviousCompanyRow = new PreviousCompanyGrid();
                currentpreviousCompanyRow.setSno(++i);
                currentpreviousCompanyRow.setEmpCode(hrPersonnelPreviousCompanyTO.getHrPersonnelPreviousCompanyPKTO().getEmpCode());
                currentpreviousCompanyRow.setPrevCompCode(hrPersonnelPreviousCompanyTO.getHrPersonnelPreviousCompanyPKTO().getPrevCompCode());
                currentpreviousCompanyRow.setDurationFrom(dmyFormat.format(hrPersonnelPreviousCompanyTO.getHrPersonnelPreviousCompanyPKTO().getDurFrom()));
                currentpreviousCompanyRow.setDurationTo(dmyFormat.format(hrPersonnelPreviousCompanyTO.getHrPersonnelPreviousCompanyPKTO().getDurTo()));
                currentpreviousCompanyRow.setPreCompName(hrPersonnelPreviousCompanyTO.getCompName());
                currentpreviousCompanyRow.setAnnualTurnOver(String.valueOf(hrPersonnelPreviousCompanyTO.getAnnualTurn()));
                currentpreviousCompanyRow.setPrevCompAddress(hrPersonnelPreviousCompanyTO.getCompAdd());
                currentpreviousCompanyRow.setReasonOfLeaving(hrPersonnelPreviousCompanyTO.getReasonLeaving());
                currentpreviousCompanyRow.setDesignationOnJoining(hrPersonnelPreviousCompanyTO.getDesgJoin());
                currentpreviousCompanyRow.setDesignationOnLeaving(hrPersonnelPreviousCompanyTO.getDesgLeave());
                currentpreviousCompanyRow.setStrengthUnder(String.valueOf(hrPersonnelPreviousCompanyTO.getEmpUnder()));
                currentpreviousCompanyRow.setStrengthTotal(String.valueOf(hrPersonnelPreviousCompanyTO.getTotEmp()));
                currentpreviousCompanyRow.setSalaryOnJoining(hrPersonnelPreviousCompanyTO.getSalJoin());
                currentpreviousCompanyRow.setSalaryOnLeaving(hrPersonnelPreviousCompanyTO.getSalLeave());
                previousCompanyGrids.add(currentpreviousCompanyRow);
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method getExperienceData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method getExperienceData()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getExperienceData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return previousCompanyGrids;
    }
    
    public List<TransportGrid> getTransportData(long empCode) throws WebException {
        List<TransportGrid> transportGrids = new ArrayList<TransportGrid>();
        try {
            PersonnelDelegate personnelDelegate = new PersonnelDelegate();
            List<HrPersonnelTransportTO> hrPersonnelTransportTOs = personnelDelegate.getTransportTableData(empCode);
            int i = 0;
            for (HrPersonnelTransportTO hrPersonnelTransportTO : hrPersonnelTransportTOs) {
                TransportGrid currenttransportRow = new TransportGrid();
                currenttransportRow.setSno(++i);
                currenttransportRow.setRouteCode(hrPersonnelTransportTO.getHrPersonnelTransportPKTO().getRouteCode());
                currenttransportRow.setBusNo(hrPersonnelTransportTO.getHrPersonnelTransportPKTO().getBusNo());
                currenttransportRow.setPickUpPoint(hrPersonnelTransportTO.getPickPnt());
                currenttransportRow.setPickUpTime(hmmFormat.format(hrPersonnelTransportTO.getPickTm()));
                currenttransportRow.setEmpCode(hrPersonnelTransportTO.getHrPersonnelTransportPKTO().getEmpCode());
                transportGrids.add(currenttransportRow);
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method getTransportData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method getTransportData()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getTransportData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return transportGrids;
    }
    
    public List<MembershipGrid> getMembershipData(long empCode) throws WebException {
        List<MembershipGrid> membershipGrids = new ArrayList<MembershipGrid>();
        try {
            PersonnelDelegate personnelDelegate = new PersonnelDelegate();
            List<HrMembershipDetailTO> hrMembershipDetailTOs = personnelDelegate.getMembershipTableData(empCode);
            int i = 0;
            for (HrMembershipDetailTO hrMembershipDetailTO : hrMembershipDetailTOs) {
                MembershipGrid currentMembershipRow = new MembershipGrid();
                currentMembershipRow.setAccomodationType(hrMembershipDetailTO.getAccomdType());
                currentMembershipRow.setEmpCode(hrMembershipDetailTO.getHrMembershipDetailPKTO().getEmpCode());
                currentMembershipRow.setIndiMember(hrMembershipDetailTO.getIndivMember());
                currentMembershipRow.setInsuranceNo(hrMembershipDetailTO.getInsuranceNo());
                currentMembershipRow.setMemNo(hrMembershipDetailTO.getHrMembershipDetailPKTO().getMemNo());
                currentMembershipRow.setPassportDate(hrMembershipDetailTO.getPassportDate());
                currentMembershipRow.setPassportNo(hrMembershipDetailTO.getPassportNo());
                currentMembershipRow.setProfMember(hrMembershipDetailTO.getProfMember());
                currentMembershipRow.setTravelledOverseas(hrMembershipDetailTO.getTravelOver());
                currentMembershipRow.setSno(++i);
                membershipGrids.add(currentMembershipRow);
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method getMembershipData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method getMembershipData()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getMembershipData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return membershipGrids;
    }
    
    public List<LanguageGrid> getLanguageData(long empCode) throws WebException {
        List<LanguageGrid> languageGrids = new ArrayList<LanguageGrid>();
        try {
            PersonnelDelegate personnelDelegate = new PersonnelDelegate();
            List<HrPersonnelLangTO> hrPersonnelLangTOs = personnelDelegate.getLanguageTableData(empCode);
            int i = 0;
            for (HrPersonnelLangTO hrPersonnelLangTO : hrPersonnelLangTOs) {
                LanguageGrid currentLanguageRow = new LanguageGrid();
                currentLanguageRow.setSno(++i);
                currentLanguageRow.setLangRead(hrPersonnelLangTO.getLangRead());
                currentLanguageRow.setLangSpeak(hrPersonnelLangTO.getLangSpeak());
                currentLanguageRow.setLangWrite(hrPersonnelLangTO.getLangWrite());
                currentLanguageRow.setLanguage(hrPersonnelLangTO.getHrPersonnelLangPKTO().getLang());
                currentLanguageRow.setEmpCode(hrPersonnelLangTO.getHrPersonnelLangPKTO().getEmpCode());
                languageGrids.add(currentLanguageRow);
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method getLanguageData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method getLanguageData()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getLanguageData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return languageGrids;
    }
    
    public List<HobbyGrid> getHobbyData(long empCode) throws WebException {
        List<HobbyGrid> hobbyGrids = new ArrayList<HobbyGrid>();
        try {
            PersonnelDelegate personnelDelegate = new PersonnelDelegate();
            List<HrPersonnelHobbyTO> hrPersonnelHobbyTOs = personnelDelegate.getHobbyTableData(empCode);
            int i = 0;
            for (HrPersonnelHobbyTO hrPersonnelHobbyTO : hrPersonnelHobbyTOs) {
                HobbyGrid currentHobbyRow = new HobbyGrid();
                currentHobbyRow.setSno(++i);
                currentHobbyRow.setHobby(hrPersonnelHobbyTO.getHrPersonnelHobbyPKTO().getHobbyName());
                currentHobbyRow.setEmpCode(hrPersonnelHobbyTO.getHrPersonnelHobbyPKTO().getEmpCode());
                hobbyGrids.add(currentHobbyRow);
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method getHobbyData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method getHobbyData()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method getHobbyData()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return hobbyGrids;
    }
    
    public HrPersonnelDetailsTO getHrPersonnelDetailsTO(int compCode, long empCode) {
        HrPersonnelDetailsTO hrPersonnelDetailsTO = new HrPersonnelDetailsTO();
        HrPersonnelDetailsPKTO hrPersonnelDetailsPKTO = new HrPersonnelDetailsPKTO();
        try {
            hrPersonnelDetailsPKTO.setCompCode(compCode);
            hrPersonnelDetailsPKTO.setEmpCode(empCode);
            hrPersonnelDetailsTO.setHrPersonnelDetailsPKTO(hrPersonnelDetailsPKTO);
        } catch (Exception e) {
            logger.error("Exception occured while executing method getHrPersonnelDetailsTO()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
        return hrPersonnelDetailsTO;
    }
    
    public SimpleDateFormat getHmmFormat() {
        return hmmFormat;
    }
    
    public void setHmmFormat(SimpleDateFormat hmmFormat) {
        this.hmmFormat = hmmFormat;
    }
    
    public SimpleDateFormat getDmyFormat() {
        return dmyFormat;
    }
    
    public void setDmyFormat(SimpleDateFormat dmyFormat) {
        this.dmyFormat = dmyFormat;
    }
    
    public NumberFormat getFormatter() {
        return formatter;
    }
    
    public void setFormatter(NumberFormat formatter) {
        this.formatter = formatter;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public SimpleDateFormat getYmdFormat() {
        return ymdFormat;
    }
    
    public void setYmdFormat(SimpleDateFormat ymdFormat) {
        this.ymdFormat = ymdFormat;
    }
}
