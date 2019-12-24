/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.controller.hrd;

import com.hrms.web.pojo.ViewOrgnTable;
import com.cbs.servlets.Init;
import com.cbs.web.controller.BaseBean;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.exception.WebException;
import com.hrms.web.delegate.RecruitmentDelegate;
import com.hrms.web.delegate.OrganizationDelegate;
import com.hrms.web.utils.LocalizationUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import java.net.InetAddress;
import javax.faces.model.SelectItem;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import com.hrms.common.to.CompanyMasterTO;
import com.hrms.web.utils.WebUtil;

/**
 *
 * @author Zeeshan waris
 */
public class ViewOrganization extends BaseBean {

    private List<CompanyMasterTO> companyMasterTOs;
    private WebUtil webUtil = new WebUtil();
    private static final Logger logger = Logger.getLogger(ViewOrganization.class);
    private String message;
    private String department;
    private String grade;
    private String compCode = "0";
    private List<SelectItem> departmentList;
    private List<SelectItem> gradeList;
    Date date = new Date();
    private List<ViewOrgnTable> contSearch;
    private ViewOrgnTable currentItem = new ViewOrgnTable();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<SelectItem> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<SelectItem> departmentList) {
        this.departmentList = departmentList;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public List<SelectItem> getGradeList() {
        return gradeList;
    }

    public void setGradeList(List<SelectItem> gradeList) {
        this.gradeList = gradeList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ViewOrgnTable> getContSearch() {
        return contSearch;
    }

    public void setContSearch(List<ViewOrgnTable> contSearch) {
        this.contSearch = contSearch;
    }

    public ViewOrgnTable getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(ViewOrgnTable currentItem) {
        this.currentItem = currentItem;
    }

    public ViewOrganization() {
        try {
            compCode = getOrgnBrCode();
            companyMasterTOs = webUtil.getCompanyMasterTO(Integer.parseInt(compCode));
            this.setMessage("");
            dropdownList(Integer.parseInt(compCode), "DEP%");
            dropdownList(Integer.parseInt(compCode), "GRA%");
        } catch (Exception e) {
            logger.error("Exception occured while executing method ViewOrganization()", e);
            logger.error("ViewOrganization()", e);
        }
    }

    public void dropdownList(int compCode, String description) {
        try {
            RecruitmentDelegate recruitmentDelegate = new RecruitmentDelegate();
            List<HrMstStructTO> structMasterTOs = recruitmentDelegate.getPositionList(compCode, description);
            if (description.equalsIgnoreCase("DEP%")) {
                departmentList = new ArrayList<SelectItem>();
                departmentList.add(new SelectItem("0", "--Select--"));
                for (HrMstStructTO structMasterTO : structMasterTOs) {
                    departmentList.add(new SelectItem(structMasterTO.getHrMstStructPKTO().getStructCode(),
                            structMasterTO.getDescription()));
                }
            }
            if (description.equalsIgnoreCase("GRA%")) {
                gradeList = new ArrayList<SelectItem>();
                gradeList.add(new SelectItem("0", "--Select--"));
                for (HrMstStructTO structMasterTO : structMasterTOs) {
                    gradeList.add(new SelectItem(structMasterTO.getHrMstStructPKTO().getStructCode(),
                            structMasterTO.getDescription()));
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method dropdownList()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method dropdownList()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method dropdownList()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void viewDetails() {
        try {
            setMessage("");
            if (department.equalsIgnoreCase("0")) {
                setMessage("Please select the Department");
                return;
            }
            if (grade.equalsIgnoreCase("0")) {
                setMessage("Please select the Grade");
                return;
            }
            contSearch = new ArrayList<ViewOrgnTable>();
            List resultLt = new ArrayList();
            OrganizationDelegate organizationDelegate = new OrganizationDelegate();
            resultLt = organizationDelegate.viewOrgnList(Integer.parseInt(compCode), department, grade);
            if (resultLt.size() != 0) {
                Iterator i1 = resultLt.iterator();
                while (i1.hasNext()) {
                    Object[] result = (Object[]) i1.next();
                    ViewOrgnTable rd = new ViewOrgnTable();
                    if (result[0] != null) {
                        rd.setEmpName(result[0].toString());
                    }
                    if (result[1] != null) {
                        rd.setDesgcode(result[1].toString());
                    }
                    if (result[2] != null) {
                        rd.setDescription(result[2].toString());
                    }
                    contSearch.add(rd);
                }
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method viewDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(e.getMessageKey()));
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method viewDetails()", e1);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        } catch (Exception e) {
            logger.error("Exception occured while executing method viewDetails()", e);
            this.setMessage(LocalizationUtil.getLocalizedText(ExceptionCode.SYSTEM_EXCEPTION_OCCURED));
        }
    }

    public void refreshButtonAction() {
        setMessage("");
        setDepartment("0");
        setGrade("0");
        contSearch = new ArrayList<ViewOrgnTable>();
    }

    public String btnExit() {
        try {
            refreshButtonAction();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "case1";
    }
}
