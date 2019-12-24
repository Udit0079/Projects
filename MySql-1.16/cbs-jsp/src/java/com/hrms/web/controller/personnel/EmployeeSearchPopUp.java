/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.controller.personnel;

import com.cbs.web.controller.BaseBean;
import com.hrms.web.pojo.EmployeeGrid;
import java.util.List;

/**
 *
 * @author Ankit Verma
 */
public class EmployeeSearchPopUp extends BaseBean{

    private String  empSearchCriteria, empSearchValue;
    PersonnelUtil personnelUtil;
    private List<EmployeeGrid> empSearchTable;
    private EmployeeGrid currentEmpItem = new EmployeeGrid();
    /** Creates a new instance of EmployeeSearchPopUp */
    public EmployeeSearchPopUp() {
        try {
            personnelUtil=new PersonnelUtil();
        } catch (Exception e) {
        }
    }

    public String getEmpSearchCriteria() {
        return empSearchCriteria;
    }

    public void setEmpSearchCriteria(String empSearchCriteria) {
        this.empSearchCriteria = empSearchCriteria;
    }

    public String getEmpSearchValue() {
        return empSearchValue;
    }

    public void setEmpSearchValue(String empSearchValue) {
        this.empSearchValue = empSearchValue;
    }

    public PersonnelUtil getPersonnelUtil() {
        return personnelUtil;
    }

    public void setPersonnelUtil(PersonnelUtil personnelUtil) {
        this.personnelUtil = personnelUtil;
    }

    public EmployeeGrid getCurrentEmpItem() {
        return currentEmpItem;
    }

    public void setCurrentEmpItem(EmployeeGrid currentEmpItem) {
        this.currentEmpItem = currentEmpItem;
    }

    public List<EmployeeGrid> getEmpSearchTable() {
        return empSearchTable;
    }

    public void setEmpSearchTable(List<EmployeeGrid> empSearchTable) {
        this.empSearchTable = empSearchTable;
    }
    
    
     public void getEmployeeTableData() {
        try {
            empSearchTable = personnelUtil.getEmployeeTableData(Integer.parseInt(getOrgnBrCode()), empSearchCriteria, empSearchValue);
           
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
     public void setEmpRowValues() {
//        try {
//            empCode = currentEmpItem.getEmpCode();
//            compCode = currentEmpItem.getCompCode();
//            getHttpSession().setAttribute("EMP_CODE", empCode);
//            getHttpSession().setAttribute("COMP_CODE", compCode);
//            getHttpSession().setAttribute("DEFAULT_COMP", defaultComp);
//             getHttpSession().setAttribute("EMP_ID", empId);
//            empName = currentEmpItem.getEmpName();
//            if (currentEmpItem.getEmpId() != null) {
//                setEmpId(currentEmpItem.getEmpId());
//            } else {
//                setEmpId("");
//            }
//            if (currentEmpItem.getEmpFName() != null) {
//                setEmpFName(currentEmpItem.getEmpFName());
//            } else {
//                setEmpFName("");
//            }
//            if (currentEmpItem.getEmpMName() != null) {
//                setEmpMName(currentEmpItem.getEmpMName());
//            } else {
//                setEmpMName("");
//            }
//            if (currentEmpItem.getEmpLName() != null) {
//                setEmpLName(currentEmpItem.getEmpLName());
//            } else {
//                setEmpLName("");
//            }
//            if (currentEmpItem.getEmpCardNo() != null) {
//                setEmpCardNo(currentEmpItem.getEmpCardNo());
//            } else {
//                setEmpCardNo("");
//            }
//            if (currentEmpItem.getDateOfBirth() != null) {
//                setDateOfBirth(currentEmpItem.getDateOfBirth());
//            } else {
//                setDateOfBirth("");
//            }
//            if (currentEmpItem.getJoiningDate() != null) {
//                setJoiningDate(currentEmpItem.getJoiningDate());
//            } else {
//                setJoiningDate("");
//            }
//            if (currentEmpItem.getSex() != '0') {
//                setSex(currentEmpItem.getSex());
//            } else {
//                setSex('0');
//            }
//            if (currentEmpItem.getWorkStatusCode() != null) {
//                setWorkStatusCode(currentEmpItem.getWorkStatusCode());
//            } else {
//                setWorkStatusCode("");
//            }
//            if (currentEmpItem.getBlockCode() != null) {
//                setBlockCode(currentEmpItem.getBlockCode());
//            } else {
//                setBlockCode("");
//            }
//            if (currentEmpItem.getUnitCode() != null) {
//                setUnitCode(currentEmpItem.getUnitCode());
//            } else {
//                setUnitCode("");
//            }
//            if (currentEmpItem.getGradeCode() != null) {
//                setGradeCode(currentEmpItem.getGradeCode());
//            } else {
//                setEmpFName("");
//            }
//            if (currentEmpItem.getDesgCode() != null) {
//                List<HrMstStructTO> hrMstStructTO = personnelDelegate.getInitialData(compCode, currentEmpItem.getDesgCode());
//                designationList = new ArrayList<SelectItem>();
//                designationList.add(new SelectItem(hrMstStructTO.get(0).getHrMstStructPKTO().getStructCode(), hrMstStructTO.get(0).getDescription()));
//            } else {
//                designationList = new ArrayList<SelectItem>();
//                designationList.add(new SelectItem("0", "--Select--"));
//            }
//            if (currentEmpItem.getEmployeeTypeCode() != null) {
//                setEmployeeTypeCode(currentEmpItem.getEmployeeTypeCode());
//            } else {
//                setEmployeeTypeCode("");
//            }
//            if (currentEmpItem.getCategoryCode() != null) {
//                setCategoryCode(currentEmpItem.getCategoryCode());
//            } else {
//                setCategoryCode("");
//            }
//            if (currentEmpItem.getZoneCode() != null) {
//                setZoneCode(currentEmpItem.getZoneCode());
//            } else {
//                setZoneCode("");
//            }
//            if (currentEmpItem.getLocationCode() != null) {
//                setLocationCode(currentEmpItem.getLocationCode());
//            } else {
//                setLocationCode("");
//            }
//            if (currentEmpItem.getDepartmentCode() != null) {
//                setDepartmentCode(currentEmpItem.getDepartmentCode());
//            } else {
//                setDepartmentCode("");
//            }
//            if (currentEmpItem.getSubDepartmentCode() != null) {
//                setSubDepartmentCode(currentEmpItem.getSubDepartmentCode());
//            } else {
//                setSubDepartmentCode("");
//            }
//            if (currentEmpItem.getBankCode() != null) {
//                setBankCode(currentEmpItem.getBankCode());
//            } else {
//                setBankCode("");
//            }
//            if (currentEmpItem.getBankAccountNo() != null) {
//                setBankAccountNo(currentEmpItem.getBankAccountNo());
//            } else {
//                setBankAccountNo("");
//            }
//            if (currentEmpItem.getFinanceLoanAccountCode() != null) {
//                setFinanceLoanAccountCode(currentEmpItem.getFinanceLoanAccountCode());
//            } else {
//                setFinanceLoanAccountCode("");
//            }
//            if (currentEmpItem.getPaymentMode() != null) {
//                setPaymentMode(currentEmpItem.getPaymentMode());
//            } else {
//                setPaymentMode("");
//            }
//            membershipGroup = new ArrayList<String>();
//            membershipGroup.add(String.valueOf(currentEmpItem.getESIMembership()));
//            membershipGroup.add(String.valueOf(currentEmpItem.getPFMembership()));
//            membershipGroup.add(String.valueOf(currentEmpItem.getVPFMembership()));
//            membershipGroup.add(String.valueOf(currentEmpItem.getTRUSTMembership()));
//            if (currentEmpItem.getESIMembership() != null && currentEmpItem.getESIMembership() == 'T') {
//                membershipGroup.add("ESI");
//            }
//            if (currentEmpItem.getPFMembership() != null && currentEmpItem.getPFMembership() == 'T') {
//                membershipGroup.add("PF");
//            }
//            if (currentEmpItem.getVPFMembership() != null && currentEmpItem.getVPFMembership() == 'T') {
//                membershipGroup.add("VPF");
//            }
//            if (currentEmpItem.getTRUSTMembership() != null && currentEmpItem.getTRUSTMembership() == 'T') {
//                membershipGroup.add("TRUST");
//            }
//            if (currentEmpItem.getEmpAddress() != null) {
//                setConAddressLine(currentEmpItem.getEmpAddress());
//            } else {
//                setConAddressLine("");
//            }
//            if (currentEmpItem.getEmpConCity() != null) {
//                setConCity(currentEmpItem.getEmpConCity());
//            } else {
//                setConCity("");
//            }
//            if (currentEmpItem.getEmpConState() != null) {
//                setConState(currentEmpItem.getEmpConState());
//            } else {
//                setConState("");
//            }
//            if (currentEmpItem.getEmpConPin() != null) {
//                setConPin(currentEmpItem.getEmpConPin());
//            } else {
//                setConPin("");
//            }
//            if (currentEmpItem.getEmpPhone() != null) {
//                setConPhone(currentEmpItem.getEmpPhone());
//            } else {
//                setConPhone("");
//            }
//            if (currentEmpItem.getEmpPerAddress() != null) {
//                setPerAddressLine(currentEmpItem.getEmpPerAddress());
//            } else {
//                setPerAddressLine("");
//            }
//            if (currentEmpItem.getEmpPerCity() != null) {
//                setPerCity(currentEmpItem.getEmpPerCity());
//            } else {
//                setPerCity("");
//            }
//            if (currentEmpItem.getEmpPerState() != null) {
//                setPerState(currentEmpItem.getEmpPerState());
//            } else {
//                setPerState("");
//            }
//            if (currentEmpItem.getEmpPerPin() != null) {
//                setPerPin(currentEmpItem.getEmpPerPin());
//            } else {
//                setPerPin("");
//            }
//            if (currentEmpItem.getEmpPerPhone() != null) {
//                setPerPhone(currentEmpItem.getEmpPerPhone());
//            } else {
//                setPerPhone("");
//            }
//            if (currentEmpItem.getFatherName_husbandName() != null) {
//                getEmployeeOtherDetail().setFatherName_husbandName(currentEmpItem.getFatherName_husbandName());
//            } else {
//                getEmployeeOtherDetail().setFatherName_husbandName("");
//            }
//            if (currentEmpItem.getFatherName_husbandOcc() != null) {
//                getEmployeeOtherDetail().setFatherName_husbandOcc(currentEmpItem.getFatherName_husbandOcc());
//            } else {
//                getEmployeeOtherDetail().setFatherName_husbandOcc("");
//            }
//            if (currentEmpItem.getFatherName_husbandDesg() != null) {
//                getEmployeeOtherDetail().setFatherName_husbandDesg(currentEmpItem.getFatherName_husbandDesg());
//            } else {
//                getEmployeeOtherDetail().setFatherName_husbandDesg("");
//            }
//            if (currentEmpItem.getFatherName_husbandOrg() != null) {
//                getEmployeeOtherDetail().setFatherName_husbandOrg(currentEmpItem.getFatherName_husbandOrg());
//            } else {
//                getEmployeeOtherDetail().setFatherName_husbandOrg("");
//            }
//            if (currentEmpItem.getFatherName_husbandPhone() != null) {
//                getEmployeeOtherDetail().setFatherName_husbandPhone(currentEmpItem.getFatherName_husbandPhone());
//            } else {
//                getEmployeeOtherDetail().setFatherName_husbandPhone("");
//            }
//            if (currentEmpItem.getBirthCity() != null) {
//                getEmployeeOtherDetail().setBirthCity(currentEmpItem.getBirthCity());
//            } else {
//                getEmployeeOtherDetail().setBirthCity("");
//            }
//            if (currentEmpItem.getBirthState() != null) {
//                getEmployeeOtherDetail().setBirthState(currentEmpItem.getBirthState());
//            } else {
//                getEmployeeOtherDetail().setBirthState("");
//            }
//            if (currentEmpItem.getNation() != null) {
//                getEmployeeOtherDetail().setNation(currentEmpItem.getNation());
//            } else {
//                getEmployeeOtherDetail().setNation("");
//            }
//            if (currentEmpItem.getHeight() != null) {
//                getEmployeeOtherDetail().setHeight(currentEmpItem.getHeight());
//            } else {
//                getEmployeeOtherDetail().setHeight("");
//            }
//            if (currentEmpItem.getWeight() != null) {
//                getEmployeeOtherDetail().setWeight(currentEmpItem.getWeight());
//            } else {
//                getEmployeeOtherDetail().setWeight("");
//            }
//            if (currentEmpItem.getHealth() != null) {
//                getEmployeeOtherDetail().setHealth(currentEmpItem.getHealth());
//            } else {
//                getEmployeeOtherDetail().setHealth("");
//            }
//            if (currentEmpItem.getChest() != null) {
//                getEmployeeOtherDetail().setChest(currentEmpItem.getChest());
//            } else {
//                getEmployeeOtherDetail().setChest("");
//            }
//            if (currentEmpItem.getBloodGroup() != null) {
//                getEmployeeOtherDetail().setBloodGroup(currentEmpItem.getBloodGroup());
//            } else {
//                getEmployeeOtherDetail().setBloodGroup("0");
//            }
//            if (currentEmpItem.getChildren() != null) {
//                getEmployeeOtherDetail().setChildren(String.valueOf(currentEmpItem.getChildren()));
//            } else {
//                getEmployeeOtherDetail().setChildren("0");
//            }
//            if (currentEmpItem.getMaritalStatus() != null) {
//                getEmployeeOtherDetail().setMaritalStatus(currentEmpItem.getMaritalStatus());
//            } else {
//                getEmployeeOtherDetail().setMaritalStatus('0');
//            }
//            if (currentEmpItem.getWeddingDate() != null) {
//                if (!currentEmpItem.getWeddingDate().equalsIgnoreCase("01/01/1900")) {
//                    getEmployeeOtherDetail().setWeddingDate(currentEmpItem.getWeddingDate());
//                } else {
//                    getEmployeeOtherDetail().setWeddingDate("");
//                }
//            }
//            if (currentEmpItem.getReligion() != null) {
//                getEmployeeOtherDetail().setReligion(currentEmpItem.getReligion());
//            } else {
//                getEmployeeOtherDetail().setReligion("");
//            }
//            if (currentEmpItem.getEmailId() != null) {
//                getEmployeeOtherDetail().setEmailId(currentEmpItem.getEmailId());
//            } else {
//                getEmployeeOtherDetail().setEmailId("");
//            }
//            if (currentEmpItem.getVisaDetail() != null) {
//                getEmployeeOtherDetail().setVisaDetail(currentEmpItem.getVisaDetail());
//            } else {
//                getEmployeeOtherDetail().setVisaDetail("");
//            }
//            if (currentEmpItem.getAdolescentNo() != null) {
//                getEmployeeOtherDetail().setAdolescentNo(currentEmpItem.getAdolescentNo());
//            } else {
//                getEmployeeOtherDetail().setAdolescentNo("");
//            }
//            if (currentEmpItem.getAdolescentDate() != null) {
//                if (!currentEmpItem.getAdolescentDate().equalsIgnoreCase("01/01/1900")) {
//                    getEmployeeOtherDetail().setAdolescentDate(currentEmpItem.getAdolescentDate());
//                } else {
//                    getEmployeeOtherDetail().setAdolescentDate("");
//                }
//            }
//            if (currentEmpItem.getAdolescentTokenNo() != null) {
//                getEmployeeOtherDetail().setAdolescentTokenNo(currentEmpItem.getAdolescentTokenNo());
//            } else {
//                getEmployeeOtherDetail().setAdolescentTokenNo("");
//            }
//            if (currentEmpItem.getAdolescentReferenceNo() != null) {
//                getEmployeeOtherDetail().setAdolescentReferenceNo(currentEmpItem.getAdolescentReferenceNo());
//            } else {
//                getEmployeeOtherDetail().setAdolescentReferenceNo("");
//            }
//            if (currentEmpItem.getAdolescentRelay() != null) {
//                getEmployeeOtherDetail().setAdolescentRelay(currentEmpItem.getAdolescentRelay());
//            } else {
//                getEmployeeOtherDetail().setAdolescentRelay("");
//            }
//            if (currentEmpItem.getAdolescentRelayDate() != null) {
//                if (!currentEmpItem.getAdolescentRelayDate().equalsIgnoreCase("01/01/1900")) {
//                    getEmployeeOtherDetail().setAdolescentRelayDate(currentEmpItem.getAdolescentRelayDate());
//                } else {
//                    getEmployeeOtherDetail().setAdolescentRelayDate("");
//                }
//            }
//            setMessage("");
//            setDisableTabs(false);
//        } catch (WebException e) {
//            logger.error("Exception occured while executing method busValidation()", e);
//            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("Exception occured while executing method busValidation()", e);
//            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
//        }
    } 
}
