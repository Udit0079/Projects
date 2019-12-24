/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.web.delegate;

import com.hrms.facade.hrd.RecruitmentFacadeRemote;
import com.hrms.common.to.HrConsultantTO;
import com.hrms.common.exception.ApplicationException;
import com.hrms.common.to.HrDirectRecTO;
import com.hrms.common.to.HrManpowerTO;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.utils.HrServiceLocator;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.exception.WebException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Zeeshan Waris
 */
 public class RecruitmentDelegate {
 private final String jndiHomeName = "RecruitmentFacade";
 private RecruitmentFacadeRemote beanRemote = null;
/**
 *
 * @throws ServiceLocatorException
 */
 public RecruitmentDelegate() throws ServiceLocatorException {
        try{
            Object lookup = HrServiceLocator.getInstance().lookup(jndiHomeName);
            beanRemote = (RecruitmentFacadeRemote) lookup;
        }catch (Exception e) {
            throw new ServiceLocatorException(e);            
        }
    }

 /**
 *
 * @param compCode
 * @param code
 * @return
 * @throws WebException
 */
  public List<HrMstStructTO> getPositionList(int compCode,String code) throws WebException {
        List<HrMstStructTO> structMasterTOs = new ArrayList<HrMstStructTO>();
        try {
            structMasterTOs = beanRemote.getDirectRecruitmentList(compCode,code);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return structMasterTOs;
    }
/**
 *
 * @param compCode
 * @param zone
 * @return
 * @throws WebException
 */
    public List directRecruitmentZoneList(int compCode,String zone) throws WebException {
        List searchList = new ArrayList();
        try {
            searchList = beanRemote.directRecruitmentZoneCode(compCode,zone);
       } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
       }
        return searchList;
    }
/**
 *
 * @param compCode
 * @param empId
 * @return
 * @throws WebException
 */
     public List directRecruitmentInterviewersDetailsById(int compCode,String empId) throws WebException {
        List detailList = new ArrayList();
        try {
            detailList = beanRemote.directRecruitmentInterviewerDetails(compCode,empId);
       } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
       }
        return detailList;
    }
/**
 *
 * @param compCode
 * @param empName
 * @return
 * @throws WebException
 */
      public List directRecruitmentInterviewersDetailsByName(int compCode,String empName) throws WebException {
        List detailList = new ArrayList();
        try {
            detailList = beanRemote.directRecruitmentInterviewerByName(compCode,empName);
       } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
       }
        return detailList;
    }
/**
 *
 * @param compCode
 * @param candName
 * @return
 * @throws WebException
 */
    public List directRecruitmentViewDetails(int compCode,String candName) throws WebException {
        List detailList = new ArrayList();
        try {
            detailList = beanRemote.directRecruitmentViewDetail(compCode,candName);
       } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
       }
        return detailList;
    }
/**
 *
 * @param compCode
 * @param arNum
 * @return
 * @throws WebException
 */
    public List directRecruitmentUpdateDetails(int compCode,String arNum) throws WebException {
        List detailList = new ArrayList();
        try {
            detailList = beanRemote.directRecruitmentUpdateDetail(compCode,arNum);
       } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
       }
        return detailList;
    }
/**
 *
 * @param compCode
 * @return
 * @throws WebException
 */
     public List directRecruitmentSaveCheck(int compCode) throws WebException {
        List detailList = new ArrayList();
        try {
            detailList = beanRemote.directRecruitmentSaveCheckAction(compCode);
       } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
       }
        return detailList;
    }
/**
 *
 * @return
 * @throws WebException
 */
      public List directRecruitmentValidCheck() throws WebException {
        List detailList = new ArrayList();
        try {
            detailList = beanRemote.directRecruitmentSaveValidAction();
       } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
       }
        return detailList;
       }
/**
 *
 * @param compcode
 * @param superId
 * @return
 * @throws WebException
 */
       public List directRecruitmentSuperCode(int compcode,String superId) throws WebException {
        List detailList = new ArrayList();
        try {
            detailList = beanRemote.directRecruitmentSuperCodeForSave(compcode,superId);
       } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
       }
        return detailList;
       }
/**
 *
 * @param directObj
 * @return
 * @throws WebException
 */
        public String directRecruitmentSave(HrDirectRecTO directObj) throws WebException {
        String result = null;
        try {
            result = beanRemote.directRecruitmentSave(directObj);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }
/**
 *
 * @param directObj
 * @return
 * @throws WebException
 */
     public String directRecruitmentUpdate(HrDirectRecTO directObj) throws WebException {
        String result = null;
        try {
            result = beanRemote.directRecruitmentUpdate(directObj);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }
/**
 *
 * @param compCode
 * @param arNo
 * @return
 * @throws WebException
 */
   public String directRecruitmentDelete(int compCode, String arNo) throws WebException {
        String result = null;
        try {
            result = beanRemote.directRecruitmentDeleteRecord(compCode,arNo);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    /**
    *
    * @param compCode
    * @return
    * @throws WebException
    */
    public List<HrMstStructTO> getInitialData(int compCode) throws WebException {
        List<HrMstStructTO> structMasterTOs = new ArrayList<HrMstStructTO>();
        try {
            structMasterTOs = beanRemote.getIntialData(compCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return structMasterTOs;
    }

   /**
    *
    * @param compCode
    * @param consultantName
    * @return
    * @throws WebException
    */
    public List<HrConsultantTO> getTableDetails(int compCode,String consultantName) throws WebException {
        List<HrConsultantTO> hrConsultantTOs = new ArrayList<HrConsultantTO>();
        try {
            hrConsultantTOs = beanRemote.getTableDetails(compCode,consultantName);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return hrConsultantTOs;
    }
   /**
    *
    * @param compCode
    * @param consCode
    * @param consultantName
    * @return
    * @throws WebException
    */

     public List<HrConsultantTO> getConsultantsDetails(int compCode,String consCode,String consultantName) throws WebException {
        List<HrConsultantTO> hrConsultantTOs = new ArrayList<HrConsultantTO>();
        try {
            hrConsultantTOs = beanRemote.getConsultantDetails(compCode,consCode,consultantName);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return hrConsultantTOs;
    }

   /**
    *
    * @param consultantObj
    * @return
    * @throws WebException
    */
    public String saveConsultant(HrConsultantTO consultantObj) throws WebException {
        String result = null;
        try {
            result = beanRemote.saveConsultant(consultantObj);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

   /**
    *
    * @param consultantObj
    * @return
    * @throws WebException
    */
      public String updateConsultant(HrConsultantTO consultantObj) throws WebException {
        String result = null;
        try {
            result = beanRemote.updateConsultant(consultantObj);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }
   /**
    *
    * @param compCode
    * @param consCode
    * @return
    * @throws WebException
    */
       public String ConsultantDelete(int compCode, String consCode) throws WebException {
        String result = null;
        try {
            result = beanRemote.deleteConsultantAction(compCode,consCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }
/**
 * 
 * @param compCode
 * @param code
 * @return
 * @throws WebException
 */
   public List<HrMstStructTO> getManPowerPlanningList(int compCode,String code) throws WebException {
        List<HrMstStructTO> structMasterTOs = new ArrayList<HrMstStructTO>();
        try {
            structMasterTOs = beanRemote.getManPowerList(compCode,code);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return structMasterTOs;
    }
/**
 *
 * @param compCode
 * @return
 * @throws WebException
 */
  public List manpowerDetailList(int compCode) throws WebException {
        List detailList = new ArrayList();
        try {
            detailList = beanRemote.manpowerDetails(compCode);
       } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
       }
        return detailList;
    }
/**
 *
 * @param compCode
 * @param year
 * @param month
 * @param zone
 * @param deptCode
 * @return
 * @throws WebException
 */
   public String manpowerPlanningDelete(int compCode, int year,String month,String zone,String deptCode) throws WebException {
        String result = null;
        try {
            result = beanRemote.deleteManPowerPlanAction(compCode,year,month,zone,deptCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }
/**
 *
 * @param compCode
 * @param desgCode
 * @return
 * @throws WebException
 */
    public List manpowerGradeCodeList(int compCode,String desgCode) throws WebException {

        List detailList = new ArrayList();
        try {
            detailList = beanRemote.manpowerPlanningGradeCode(compCode,desgCode);
       } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
       }
        return detailList;
    }
/**
 *
 * @param compCode
 * @param zones
 * @param deptCode
 * @param desgCode
 * @param gradeCode
 * @return
 * @throws WebException
 */
      public List manpowerEmployeeNoList(int compCode,String zones,String deptCode,String desgCode,String gradeCode) throws WebException {
        List detailList = new ArrayList();
        try {
            detailList = beanRemote.manpowerPlanningEmployeeNo(compCode,zones,deptCode,desgCode,gradeCode);
       } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
       }
        return detailList;
    }
/**
 *
 * @param directObj
 * @return
 * @throws WebException
 */
      public String manpowerPlanSave(HrManpowerTO directObj) throws WebException {
        String result = null;
        try {
            result = beanRemote.manpowerSave(directObj);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }
      /**
       *
       * @param directObj
       * @return
       * @throws WebException
       */
      public String manpowerPlanupdate(HrManpowerTO directObj) throws WebException {
        String result = null;
        try {
            result = beanRemote.manpowerupdate(directObj);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }
}
