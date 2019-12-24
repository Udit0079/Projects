/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.facade.payroll;

import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.pojo.BonusChecklistPojo;
import com.cbs.pojo.GPFRegisterPojo;
import com.cbs.pojo.SalaryRegisterPojo;
import com.cbs.pojo.consolidatedMsrPojo;
import com.cbs.utils.CbsUtil;
import com.hrms.adaptor.ObjectAdaptorHr;
import com.hrms.adaptor.PayrollObjectAdaptor;
import com.hrms.common.exception.ApplicationException;
import com.hrms.common.to.EmpAllocationGridPojo;
import com.hrms.common.to.EmpSalaryAllocationGridTO;
import com.hrms.common.to.HrSalaryProcessingDetailPKTO;
import com.hrms.common.to.HrSalaryProcessingDetailTO;
import com.hrms.common.to.HrSalaryProcessingPKTO;
import com.hrms.common.to.HrSalaryProcessingTO;
import com.hrms.common.to.HrSlabMasterPKTO;
import com.hrms.common.to.HrSlabMasterTO;
import com.hrms.common.to.SalaryAllocationTO;
import com.hrms.dao.HrAttendanceDetailsDAO;
import com.hrms.dao.HrPersonnelDetailsDAO;
import com.hrms.dao.HrSalaryAllocationDAO;
import com.hrms.dao.HrSalaryProcessingDAO;
import com.hrms.dao.HrSlabMasterDAO;
import com.hrms.entity.hr.HrPersonnelDetails;
import com.hrms.entity.hr.HrPersonnelDetailsPK;
import com.hrms.entity.payroll.HrAttendanceDetails;
import com.hrms.entity.payroll.HrAttendanceDetailsPK;
import com.hrms.entity.payroll.HrSalaryAllocationPK;
import com.hrms.entity.payroll.HrSalaryProcessing;
import com.hrms.entity.payroll.HrTaxInvestmentCategory;
import com.hrms.entity.payroll.HrTaxSlabMaster;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

/**
 *
 * @author root
 */
@Stateless(mappedName = "PayrollOtherMgmFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class PayrollOtherMgmFacade implements PayrollOtherMgmFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    private FtsPostingMgmtFacadeRemote ftsRemote;
    @EJB
    private FtsPostingMgmtFacadeRemote ftsFacade;        
    


    @Override
    public List getBasicSalarySlabList(String date) throws ApplicationException {
        List dataList = new ArrayList();
        try {
            String query = "select salary_slab_id,concat(salary_slab_id,'-',salary_from_amt,'->',salary_to_amt) as salSlabDesc "
                    + "from hr_basic_salary_structure where effective_date<='" + new SimpleDateFormat("yyyyMMdd").format(new SimpleDateFormat("dd/MM/yyyy").parse(date)) + "'";
            dataList = em.createNativeQuery(query).getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    @Override
    public String saveModifySlabMasterBatchData(List<HrSlabMasterTO> slabMasterBatch, String function) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            if (!slabMasterBatch.isEmpty()) {
                ut.begin();

                if (function.equalsIgnoreCase("SAVE")) {
                    for (HrSlabMasterTO pojo : slabMasterBatch) {
                        HrSlabMasterDAO hrSlabMasterDAO = new HrSlabMasterDAO(em);
                        String slabCode = hrSlabMasterDAO.calculateSlabCode();
                        pojo.getHrSlabMasterPK().setSlabCode(slabCode);

                      
                        int componentOrder = 0;
                        
                        List basicSalSlab = em.createNativeQuery("select salary_slab_id from hr_basic_salary_structure where "
                                + " salary_slab_id='" + pojo.getHrSlabMasterPK().getSalarySlabId() + "'"
                                + " and salary_from_amt<='" + pojo.getRangeFrom() + "' "
                                + " and salary_to_amt>='" + pojo.getRangeTo() + "'").getResultList();
                        if (basicSalSlab.isEmpty()) {
                            throw new ApplicationException("slab From or To Amount not in basic salary range !");
                        }

                        //To check basic salary allocation already exist
                        List basicSalExist = em.createNativeQuery("select SALARY_SLAB_ID from  hr_slab_master where DESG_ID='" + pojo.getDesgId() + "' "
                                + "and BASE_COMPONENT='" + pojo.getBaseComponent() + "' and DEPEND_COMPONENT='" + pojo.getDepnComponent() + "' "
                                + "and RANGE_FROM='" + pojo.getRangeFrom() + "' and RANGE_TO='" + pojo.getRangeTo() + "'").getResultList();
                        if (!basicSalExist.isEmpty()) {
                            throw new ApplicationException(pojo.getBaseComponent() + " ==> " + pojo.getDepnComponent() + " alredy exist.!");
                        }

                        String insQuery = "INSERT INTO hr_slab_master (SLAB_CODE, DESG_ID, COMP_CODE, BASE_COMPONENT,"
                                + " DEPEND_COMPONENT, SALARY_SLAB_ID, SLAB_CRITERIA, SLAB_CRITERIA_AMT, "
                                + " MAX_CRITERIA_AMT, PURPOSE_CODE, NATURE, RANGE_FROM, RANGE_TO, COMPONENT_ORDER,"
                                + " APP_FLG, AUTH_BY, DEFAULT_COMP, ENTERED_BY, MOD_DATE, STAT_FLAG, STAT_UP_FLAG,MIN_CRITERIA_AMT) VALUES"
                                + " ('"+  pojo.getHrSlabMasterPK().getSlabCode() + "', '" + pojo.getDesgId() + "', '" + pojo.getHrSlabMasterPK().getCompCode() + "', "
                                + " '" + pojo.getBaseComponent() + "', '" + pojo.getDepnComponent() + "', '" + pojo.getHrSlabMasterPK().getSalarySlabId() + "', "
                                + " '" + pojo.getSlabCriteria() + "', '" + pojo.getSlabCriteriaAmt() + "', '" + pojo.getSlabCriteriaMaxAmt() + "', "
                                + " '" + pojo.getHrSlabMasterPK().getPurposeCode() + "', '" + pojo.getHrSlabMasterPK().getNature() + "', '" + pojo.getRangeFrom() + "',"
                                + " '" + pojo.getRangeTo() + "', '" + componentOrder + "', '" + pojo.getAppFlg() + "', '" + pojo.getAuthBy() + "', "
                                + " '" + pojo.getHrSlabMasterPK().getCompCode() + "', '" + pojo.getEnteredBy() + "', '" + new SimpleDateFormat("yyyy-MM-dd").format(pojo.getModDate()) + "', '" + pojo.getStatFlag() + "', "
                                + " '" + pojo.getStatUpFlag() + "','"+pojo.getSlabCriteriaMinAmt()+"');";

                        int insertResult = em.createNativeQuery(insQuery).executeUpdate();
                        if (insertResult <= 0) {
                            throw new ApplicationException("Data insertion problem slab Master");
                        }
                    }
                    ut.commit();
                } else if (function.equalsIgnoreCase("UPDATE")) {                   
                      String insQuery= "";
                      String insAgainQuery="";
                                    
                    List<HrSlabMasterTO> slabMasterBatchnew = new ArrayList<HrSlabMasterTO>();
                    slabMasterBatchnew = slabMasterBatch;
                    List<HrSlabMasterTO> oldslabMasterBatchData = new ArrayList<HrSlabMasterTO>();
                    List oldslablistDetails = em.createNativeQuery("select SLAB_CODE,DESG_ID,COMP_CODE,BASE_COMPONENT,DEPEND_COMPONENT,SALARY_SLAB_ID,SLAB_CRITERIA,SLAB_CRITERIA_AMT,"
                            + "MAX_CRITERIA_AMT,PURPOSE_CODE,NATURE,RANGE_FROM,RANGE_TO,"
                            + "COMPONENT_ORDER,APP_FLG,AUTH_BY,DEFAULT_COMP,ENTERED_BY,MOD_DATE,STAT_FLAG,STAT_UP_FLAG,MIN_CRITERIA_AMT from hr_slab_master where DESG_ID ='"+slabMasterBatch.get(0).getDesgId()+"'").getResultList();
                    if(!oldslablistDetails.isEmpty()){
                          for (Object obj : oldslablistDetails) {
                           Object[] ele = (Object[]) obj;
                             HrSlabMasterTO hrSlabMasterTO = new HrSlabMasterTO();
                             HrSlabMasterPKTO hrSlabMasterPKTO = new HrSlabMasterPKTO();
                             hrSlabMasterPKTO.setSlabCode(ele[0].toString());
                             hrSlabMasterTO.setDesgId(ele[1].toString());
                             hrSlabMasterPKTO.setCompCode(Integer.parseInt(ele[2].toString()));
                             hrSlabMasterTO.setBaseComponent(ele[3].toString());
                             hrSlabMasterTO.setDepnComponent(ele[4].toString());
                             hrSlabMasterPKTO.setSalarySlabId(ele[5].toString());
                             hrSlabMasterTO.setSlabCriteria(ele[6].toString());
                             hrSlabMasterTO.setSlabCriteriaAmt(Double.parseDouble(ele[7].toString()));
                             hrSlabMasterTO.setSlabCriteriaMaxAmt(Double.parseDouble(ele[8].toString()));
                             hrSlabMasterPKTO.setPurposeCode(ele[9].toString());
                             hrSlabMasterPKTO.setNature(ele[10].toString());
                             hrSlabMasterTO.setRangeFrom(Double.parseDouble(ele[11].toString()));
                             hrSlabMasterTO.setRangeTo(Double.parseDouble(ele[12].toString()));
                             hrSlabMasterTO.setComponentOrder(ele[13].toString());
                             hrSlabMasterTO.setAppFlg(ele[14].toString().charAt(0));
                             hrSlabMasterTO.setAuthBy(ele[15].toString());
                             hrSlabMasterTO.setDefaultComp(Integer.parseInt(ele[16].toString()));
                             hrSlabMasterTO.setEnteredBy(ele[17].toString());
                             hrSlabMasterTO.setModDate(new SimpleDateFormat("yyyyMMdd").parse(ele[18].toString()));
                             hrSlabMasterTO.setStatFlag(ele[19].toString());
                             hrSlabMasterTO.setStatUpFlag(ele[20].toString());
                             hrSlabMasterTO.setSlabCriteriaMinAmt(Double.parseDouble(ele[21].toString()));
                             hrSlabMasterTO.setHrSlabMasterPK(hrSlabMasterPKTO);
                             oldslabMasterBatchData.add(hrSlabMasterTO);
                    }
                     
                       for(int i=0;i<oldslabMasterBatchData.size();i++){     
                              insQuery = "INSERT INTO hr_slab_master_his (SLAB_CODE, DESG_ID, COMP_CODE, BASE_COMPONENT,"
                                + " DEPEND_COMPONENT, SALARY_SLAB_ID, SLAB_CRITERIA, SLAB_CRITERIA_AMT, "
                                + " MAX_CRITERIA_AMT, PURPOSE_CODE, NATURE, RANGE_FROM, RANGE_TO, COMPONENT_ORDER,"
                                + " APP_FLG, AUTH_BY, DEFAULT_COMP, ENTERED_BY, MOD_DATE, STAT_FLAG, STAT_UP_FLAG,MIN_CRITERIA_AMT) VALUES"
                                + " ('"+ oldslabMasterBatchData.get(i).getHrSlabMasterPK().getSlabCode() + "', '" + oldslabMasterBatchData.get(i).getDesgId() + "', '" + oldslabMasterBatchData.get(i).getHrSlabMasterPK().getCompCode() + "', "
                                + " '" + oldslabMasterBatchData.get(i).getBaseComponent() + "', '" + oldslabMasterBatchData.get(i).getDepnComponent() + "', '" + oldslabMasterBatchData.get(i).getHrSlabMasterPK().getSalarySlabId() + "', "
                                + " '" + oldslabMasterBatchData.get(i).getSlabCriteria() + "', '" + oldslabMasterBatchData.get(i).getSlabCriteriaAmt() + "', '" + oldslabMasterBatchData.get(i).getSlabCriteriaMaxAmt() + "', "
                                + " '" + oldslabMasterBatchData.get(i).getHrSlabMasterPK().getPurposeCode() + "', '" + oldslabMasterBatchData.get(i).getHrSlabMasterPK().getNature() + "', '" + oldslabMasterBatchData.get(i).getRangeFrom() + "',"
                                + " '" + oldslabMasterBatchData.get(i).getRangeTo() + "', '" + oldslabMasterBatchData.get(i).getComponentOrder() + "', '" + oldslabMasterBatchData.get(i).getAppFlg() + "', '" + oldslabMasterBatchData.get(i).getAuthBy() + "', "
                                + " '" + oldslabMasterBatchData.get(i).getHrSlabMasterPK().getCompCode() + "', '" + oldslabMasterBatchData.get(i).getEnteredBy() + "', '" + new SimpleDateFormat("yyyy-MM-dd").format(oldslabMasterBatchData.get(i).getModDate()) + "', '" + oldslabMasterBatchData.get(i).getStatFlag() + "', "
                                + " '" + oldslabMasterBatchData.get(i).getStatUpFlag() + "','"+oldslabMasterBatchData.get(i).getSlabCriteriaMinAmt()+"')";
                      
                            int insertResult = em.createNativeQuery(insQuery).executeUpdate();
                        if (insertResult <= 0) {
                            throw new ApplicationException("Data insertion problem slab Master history table");
                        }
                       }
                          ut.commit();
                    }
                           ut.begin();
                       // deletion of old data of slab master table
                       int delQuery = em.createNativeQuery("delete  from hr_slab_master where DESG_ID='"+slabMasterBatchnew.get(0).getDesgId()+"' and SALARY_SLAB_ID='"+slabMasterBatchnew.get(0).getHrSlabMasterPK().getSalarySlabId()+"'").executeUpdate();
                          if (delQuery <= 0) {
                            ut.rollback();
                            throw new ApplicationException("Data insertion problem slab Master history table");
                           
                      }
                        ut.commit();
                        ut.begin();
                        int componentOrder=0;
                        
                     for(int j=0;j<slabMasterBatchnew.size();j++){
                         
                    //        HrSlabMasterDAO hrSlabMasterDAO = new HrSlabMasterDAO(em);
                    //      String slabCode = hrSlabMasterDAO.calculateSlabCode();
                        
                   //        slabMasterBatchnew.get(j).getHrSlabMasterPK().setSlabCode(slabCode);
                          
                              insAgainQuery = "INSERT INTO hr_slab_master (SLAB_CODE, DESG_ID, COMP_CODE, BASE_COMPONENT,"
                                + " DEPEND_COMPONENT, SALARY_SLAB_ID, SLAB_CRITERIA, SLAB_CRITERIA_AMT, "
                                + " MAX_CRITERIA_AMT, PURPOSE_CODE, NATURE, RANGE_FROM, RANGE_TO, COMPONENT_ORDER,"
                                + " APP_FLG, AUTH_BY, DEFAULT_COMP, ENTERED_BY, MOD_DATE, STAT_FLAG, STAT_UP_FLAG,MIN_CRITERIA_AMT) VALUES"
                                + " ('"+ slabMasterBatchnew.get(j).getHrSlabMasterPK().getSlabCode() + "', '" + slabMasterBatchnew.get(j).getDesgId() + "', '" + slabMasterBatchnew.get(j).getHrSlabMasterPK().getCompCode() + "', "
                                + " '" + slabMasterBatchnew.get(j).getBaseComponent() + "', '" + slabMasterBatchnew.get(j).getDepnComponent() + "', '" + slabMasterBatchnew.get(j).getHrSlabMasterPK().getSalarySlabId() + "', "
                                + " '" + slabMasterBatchnew.get(j).getSlabCriteria() + "', '" + slabMasterBatchnew.get(j).getSlabCriteriaAmt() + "', '" + slabMasterBatchnew.get(j).getSlabCriteriaMaxAmt() + "', "
                                + " '" + slabMasterBatchnew.get(j).getHrSlabMasterPK().getPurposeCode() + "', '" + slabMasterBatchnew.get(j).getHrSlabMasterPK().getNature() + "', '" + slabMasterBatchnew.get(j).getRangeFrom() + "',"
                                + " '" + slabMasterBatchnew.get(j).getRangeTo() + "', '" + componentOrder + "', '" + slabMasterBatchnew.get(j).getAppFlg() + "', '" + slabMasterBatchnew.get(j).getAuthBy() + "', "
                                + " '" + slabMasterBatchnew.get(j).getHrSlabMasterPK().getCompCode() + "', '" + slabMasterBatchnew.get(j).getEnteredBy() + "', '" + new SimpleDateFormat("yyyy-MM-dd").format(slabMasterBatchnew.get(j).getModDate()) + "', '" + slabMasterBatchnew.get(j).getStatFlag() + "', "
                                + " '" + slabMasterBatchnew.get(j).getStatUpFlag() + "','"+slabMasterBatchnew.get(j).getSlabCriteriaMinAmt()+"')";
                      
                            int intResult = em.createNativeQuery(insAgainQuery).executeUpdate();
                     if (intResult <= 0) {
                            ut.rollback();
                            throw new ApplicationException("Data insertion problem table");                        
                     }
                         
                     }

                     ut.commit();  
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }

        return "true";
    }

    @Override
    public List<HrSlabMasterTO> getSlabMasterData(String desgId) throws ApplicationException {
        List<HrSlabMasterTO> slabDataList = new ArrayList<HrSlabMasterTO>();
        try {
            List basicSalSlab = em.createNativeQuery("select SLAB_CODE, DESG_ID, COMP_CODE, BASE_COMPONENT, DEPEND_COMPONENT, SALARY_SLAB_ID,\n"
                 + "SLAB_CRITERIA, SLAB_CRITERIA_AMT, MAX_CRITERIA_AMT, PURPOSE_CODE, NATURE, \n"
                 + "RANGE_FROM, RANGE_TO,  APP_FLG, AUTH_BY, DEFAULT_COMP, ENTERED_BY, \n"
                 + "MOD_DATE, STAT_FLAG, STAT_UP_FLAG,MIN_CRITERIA_AMT from hr_slab_master where DESG_ID='" + desgId + "'").getResultList();
            if (basicSalSlab.isEmpty()) {
                throw new ApplicationException("No Slab For this salary slab !");
            } 
            for(int i = 0; i < basicSalSlab.size(); i++) {
                Object[] ele = (Object[]) basicSalSlab.get(i);
                HrSlabMasterTO hrSlabMasterTO = new HrSlabMasterTO();
                HrSlabMasterPKTO hrSlabMasterPKTO = new HrSlabMasterPKTO();
                hrSlabMasterPKTO.setSlabCode(ele[0].toString());
                hrSlabMasterTO.setDesgId(ele[1].toString());
                hrSlabMasterPKTO.setCompCode(Integer.parseInt(ele[2].toString()));
                hrSlabMasterTO.setBaseComponent(ele[3].toString());
                hrSlabMasterTO.setDepnComponent(ele[4].toString());
                hrSlabMasterPKTO.setSalarySlabId(ele[5].toString());
                hrSlabMasterTO.setSlabCriteria(ele[6].toString());
                hrSlabMasterTO.setSlabCriteriaAmt(Double.parseDouble(ele[7].toString()));
                hrSlabMasterTO.setSlabCriteriaMaxAmt(Double.parseDouble(ele[8].toString()));
                hrSlabMasterPKTO.setPurposeCode(ele[9].toString());
                hrSlabMasterPKTO.setNature(ele[10].toString());
                hrSlabMasterTO.setRangeFrom(Double.parseDouble(ele[11].toString()));
                hrSlabMasterTO.setRangeTo(Double.parseDouble(ele[12].toString()));
                hrSlabMasterTO.setAppFlg(ele[13].toString().charAt(0));
                hrSlabMasterTO.setAuthBy(ele[14].toString());
                hrSlabMasterTO.setDefaultComp(Integer.parseInt(ele[15].toString()));
                hrSlabMasterTO.setEnteredBy(ele[16].toString());
                hrSlabMasterTO.setModDate(new SimpleDateFormat("yyyyMMdd").parse(ele[17].toString()));
                hrSlabMasterTO.setStatFlag(ele[18].toString());
                hrSlabMasterTO.setStatUpFlag(ele[19].toString());
                hrSlabMasterTO.setSlabCriteriaMinAmt(Double.parseDouble(ele[20].toString()));
                hrSlabMasterTO.setHrSlabMasterPK(hrSlabMasterPKTO);
                slabDataList.add(hrSlabMasterTO);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return slabDataList;
    }

//    
//       public List<EmpSalaryAllocationGridTO> getSalaryBreakUp(int compCode, String empCd, String purposeCode) throws ApplicationException {
//        List<EmpSalaryAllocationGridTO> empsalaryBreakUpLst = new ArrayList<>();
//       
//             double finalCompAmt = 0.0;
//             try {
//           //    int pfFixParam = ftsFacade.getCodeForReportName("PFFIX");
//            // This Map define dependent on basic component amount
//            List<EmpSalaryAllocationGridTO> baseComponentEmpWiseList = getSalaryComponent(compCode, empCd, purposeCode );
//            if (!baseComponentEmpWiseList.isEmpty()) {
//                double CompAmt;
//                for (EmpSalaryAllocationGridTO elem : baseComponentEmpWiseList) {
//                    CompAmt = 0.0;
//                    if (elem.getSlabCriteria().equalsIgnoreCase("PERCENTAGE")) {
//                        CompAmt += (elem.getSlabCriteriaAmt() / 100) * elem.getBasicSalary();
//                    } else if (elem.getSlabCriteria().equalsIgnoreCase("AMOUNT")) {
//                        CompAmt += elem.getSlabCriteriaAmt();
//                    }
//                    // To restrict component amount not greater than max component amount
//                    if ((elem.getSlabCriteriaMaxAmt() != 0.0) && (CompAmt >= elem.getSlabCriteriaMaxAmt())) {
//                        CompAmt = elem.getSlabCriteriaMaxAmt();                      
//                    }   
//                    
//                    if ((elem.getSlabCriteriaMinAmt() != 0.0) && (CompAmt <= elem.getSlabCriteriaMinAmt())) {
//                        CompAmt = elem.getSlabCriteriaMinAmt();                      
//                    }  
//                     elem.setAmount(CompAmt);               
//                     if(purposeCode.equalsIgnoreCase("PUR001")){
//                        baseAmtMap.put(elem.getDependentComponent(), elem.getAmount());                      
//                     }
//                     if(purposeCode.equalsIgnoreCase("PUR002")){
//                        baseAmtMap.put(elem.getDependentComponent(), elem.getAmount());                           
//                     }
//                
//            }
//                       
//          List listOfDistinctComponent = em.createNativeQuery("select distinct DEPEND_COMPONENT,ifnull(ss.GL_CODE,''),ifnull(ss.DESC_SHORT_CODE,'') from "
//                    + "hr_salary_allocation sa,hr_basic_salary_structure bs,hr_slab_master sm,hr_pe"
//                    + "rsonnel_details pd,hr_salary_structure ss where "
//                    + "sa.EMP_CODE=pd.EMP_CODE and sa.SLAB_CODE=sm.SLAB_CODE and bs.salary_slab_id=sm.SALARY_SLAB_ID and ss.AL_DESC=sm.DEPEND_COMPONENT and "
//                    + "sa.EMP_CODE='" + empCd + "'/* and pd.BASE_BRANCH='" + compCode + "' */ and sm.PURPOSE_CODE='" + purposeCode + "' "
//                    + "and (sm.RANGE_FROM<=sa.BASIC_SALARY and  sm.RANGE_TO>=sa.BASIC_SALARY ) and sm.APP_FLG='Y' ORDER by sm.DEPEND_COMPONENT desc").getResultList();
//
//           List<EmpSalaryAllocationGridTO> componentEmpWiseList = getSalaryComponent(compCode, empCd, purposeCode);
//
//           Map componetAmtMap = new HashMap();
//
//            if ((!componentEmpWiseList.isEmpty()) || (!listOfDistinctComponent.isEmpty()) ) {
//                
//                for (Object compObj : listOfDistinctComponent) {
//                     Object[] ele = (Object[]) compObj;
//                      
//                    String currComponent = ele[0].toString();
//                    double pfamt = 0.0;
//                    double componentAmnt =0.0;                           
//                    String shCode = "";
//                    String glCode = "";
//                         glCode = ele[1].toString();
//                         shCode = ele[2].toString();
//                      
//                    for (EmpSalaryAllocationGridTO componentData : componentEmpWiseList) {
//
//                       String slabCriteria = componentData.getSlabCriteria();
//                       double slabCriteriaAmount = componentData.getSlabCriteriaAmt();
//                      if (componentData.getDependentComponent().equalsIgnoreCase(currComponent)) {
//                            // To define Base Amount for calculation
//                            double baseAmount = 0.0;
//                            double compAmt = 0.0;
//
//                            if (componentData.getBaseComponent().equalsIgnoreCase("BASIC")) {
//                                baseAmount = componentData.getBasicSalary();                                
//                            } else if(baseAmtMap.containsKey(componentData.getBaseComponent())) {
//                                baseAmount = (Double) baseAmtMap.get(componentData.getBaseComponent());
//                            }else if(restMap.containsKey(componentData.getBaseComponent())){
//                                baseAmount = (Double) restMap.get(componentData.getBaseComponent());
//                            }
//                     
//                            if (slabCriteria.equalsIgnoreCase("PERCENTAGE")) {
//                                compAmt += (slabCriteriaAmount / 100) * baseAmount ;
//                                componentData.setAmount(compAmt);
//
//                                  if(baseAmtMap.containsKey(componentData.getDependentComponent())){
//                                      double amnt = compAmt + (Double)baseAmtMap.get(componentData.getDependentComponent());;
//                                        baseAmtMap.put(componentData.getDependentComponent(),amnt);
//                                }
//                                  else if(!(baseAmtMap.containsKey(componentData.getDependentComponent())) && !(restMap.containsKey(componentData.getDependentComponent()))){
//                                    restMap.put(componentData.getDependentComponent(),compAmt);
//                                }else{
//                                     double amnt = compAmt + (Double)restMap.get(componentData.getDependentComponent());                                    
//                                        restMap.put(componentData.getDependentComponent(),amnt);
//                                }
// 
//                                baseComponentEmpWiseList.add(componentData);
//                            } else if (slabCriteria.equalsIgnoreCase("AMOUNT")) {
//                                compAmt += slabCriteriaAmount  ;
//                                componentData.setAmount(compAmt);
//      
//                                  if(baseAmtMap.containsKey(componentData.getDependentComponent())){
//                                      double amnt = compAmt + (Double)baseAmtMap.get(componentData.getDependentComponent());;
//                                        baseAmtMap.put(componentData.getDependentComponent(),amnt);
//                                }
//                                 else if(!(baseAmtMap.containsKey(componentData.getDependentComponent())) && !(restMap.containsKey(componentData.getDependentComponent()))){
//                                     restMap.put(componentData.getDependentComponent(),compAmt);
//                                }else{
//                                    double amnt = compAmt + (Double)restMap.get(componentData.getDependentComponent());;
//                                        restMap.put(componentData.getDependentComponent(),amnt);
//                                }
//                                
//                                baseComponentEmpWiseList.add(componentData);
//                            }
//                            // To restrict component amount not greater than max component amount
//                            if ((componentData.getSlabCriteriaMaxAmt() != 0.0) && (compAmt > componentData.getSlabCriteriaMaxAmt())) {
//                                compAmt = componentData.getSlabCriteriaMaxAmt();
//                                componentData.setAmount(compAmt);
//
//                                if(baseAmtMap.containsKey(componentData.getDependentComponent())){
//                                      double amnt = compAmt + (Double)baseAmtMap.get(componentData.getDependentComponent());;
//                                        baseAmtMap.put(componentData.getDependentComponent(),amnt);
//                                }
//                                 else if(!(baseAmtMap.containsKey(componentData.getDependentComponent())) && !(restMap.containsKey(componentData.getDependentComponent()))){
//                                     restMap.put(componentData.getDependentComponent(),compAmt);
//                                }else {
//                                    double amnt = compAmt + (Double)restMap.get(componentData.getDependentComponent());;
//                                     restMap.put(componentData.getDependentComponent(),amnt);
//                                }
//                                 
//                                baseComponentEmpWiseList.add(componentData);
//                            }
//                     // aditya added
//                            
//                              if ((componentData.getSlabCriteriaMinAmt() != 0.0) && (compAmt <= componentData.getSlabCriteriaMinAmt())) {
//                                compAmt = componentData.getSlabCriteriaMinAmt();
//                                componentData.setAmount(compAmt);
//
//                                if(baseAmtMap.containsKey(componentData.getDependentComponent())){
//                                      double amnt = compAmt + (Double)baseAmtMap.get(componentData.getDependentComponent());;
//                                        baseAmtMap.put(componentData.getDependentComponent(),amnt);
//                                }
//                                 else if(!(baseAmtMap.containsKey(componentData.getDependentComponent())) && !(restMap.containsKey(componentData.getDependentComponent()))){
//                                     restMap.put(componentData.getDependentComponent(),compAmt);
//                                }else {
//                                    double amnt = compAmt + (Double)restMap.get(componentData.getDependentComponent());;
//                                        restMap.put(componentData.getDependentComponent(),amnt);
//                                }
//                                 
//                                baseComponentEmpWiseList.add(componentData);
//                            }
//                            
//                            //end
//                            
//                            
//                            componentAmnt = componentAmnt + compAmt ;
//                    
//                            if(purposeCode.equalsIgnoreCase("PUR002")){
//                                 if(baseAmtMap.containsKey(componentData.getDependentComponent())){
//                                      double ant =(Double) baseAmtMap.get(componentData.getDependentComponent());                                    
//                                      baseAmtMap.put(componentData.getDependentComponent(),ant);
//                                  }else{
//                                      double ant =(Double) restMap.get(componentData.getDependentComponent());                                     
//                                      restMap.put(componentData.getDependentComponent(),ant);
//                                  }
//                           }
//
//                        }
//                    
//                    }
//                
//                    // add here EmpSalaryAllocationGridTO object and get the base from that object component amout as
//                    if(purposeCode.equalsIgnoreCase("PUR001")){
//                    EmpSalaryAllocationGridTO empCompDtl = new EmpSalaryAllocationGridTO();
//                    empCompDtl.setComponent(currComponent);
//
//                     if(baseAmtMap.containsKey(currComponent)){                      
//                          double compAmount = (Double)baseAmtMap.get(ele[0].toString());
//                          empCompDtl.setAmount(compAmount);
//                     }
//                     else{
//                    double compAmount = componentAmnt ;
//                    empCompDtl.setAmount(compAmount);
//                    }
//                    
//                    empCompDtl.setGlCode(glCode);
//                    empCompDtl.setShCode(shCode);
//                    componetAmtMap.put(currComponent, empCompDtl);
//                    empsalaryBreakUpLst.add(empCompDtl);
//                     } else{
//                    EmpSalaryAllocationGridTO empCompDtl = new EmpSalaryAllocationGridTO();
//                    empCompDtl.setComponent(currComponent);
//                    if(baseAmtMap.containsKey(currComponent)){
//                // add parameter PFFIX for khattri pf=1800    
//                 
//               // commenting code of PffixParam         
////                        if(pfFixParam == 1){
////                     if(purposeCode.equalsIgnoreCase("PUR002") && shCode.equalsIgnoreCase("1")){                        
////                        
////                       for(int i=0;i<baseComponentEmpWiseList.size();i++){
////                          if(baseComponentEmpWiseList.get(i).getPurposeCode().equalsIgnoreCase("PUR002") && baseComponentEmpWiseList.get(i).getShCode().equalsIgnoreCase("1") ){
////                              pfamt = baseComponentEmpWiseList.get(i).getSlabCriteriaMaxAmt();
////                        }}
////                         
////                         if((Double)baseAmtMap.get(currComponent)>=pfamt && pfamt!= 0.0){
////                            empCompDtl.setAmount(pfamt); 
////                         }else{
//                            empCompDtl.setAmount((Double)baseAmtMap.get(currComponent));
////                         } 
////                     }
////                     
////                     else{
////                          empCompDtl.setAmount((Double)baseAmtMap.get(currComponent));
////                     
////                     }} else if(pfFixParam==0){
////                         empCompDtl.setAmount((Double)baseAmtMap.get(currComponent));
////                    }}else if(pfFixParam==0 && !(baseAmtMap.containsKey(currComponent))){
////                        empCompDtl.setAmount((Double)restMap.get(currComponent));
////                    }
//                    // end
//                    empCompDtl.setGlCode(glCode);
//                    empCompDtl.setShCode(shCode);
//                    componetAmtMap.put(currComponent, empCompDtl);
//                    empsalaryBreakUpLst.add(empCompDtl);   
//                    }                   
//                }
//            }
//           
//        
//         finalCompAmt = 0.0;
//     
//        }}
//          // aditya add {   
//             }
//             catch (Exception ex) {       
//            throw new ApplicationException(ex.getMessage());
//        }
//        return empsalaryBreakUpLst;
//    }
//    
//         
       
       
       
    
//    public List<EmpSalaryAllocationGridTO> getSalaryBreakUp(int compCode, String empCd, String purposeCode) throws ApplicationException {
//        List<EmpSalaryAllocationGridTO> empsalaryBreakUpLst = new ArrayList<>();
//       
//             double finalCompAmt = 0.0;
//             try {
//              int pfFixParam = ftsFacade.getCodeForReportName("PFFIX");
//            // This Map define dependent on basic component amount
//            List<EmpSalaryAllocationGridTO> baseComponentEmpWiseList = getSalaryComponent(compCode, empCd, purposeCode, "BASIC");
//            if (!baseComponentEmpWiseList.isEmpty()) {
//                double CompAmt;
//                for (EmpSalaryAllocationGridTO elem : baseComponentEmpWiseList) {
//                    CompAmt = 0.0;
//                    if (elem.getSlabCriteria().equalsIgnoreCase("PERCENTAGE")) {
//                        CompAmt += (elem.getSlabCriteriaAmt() / 100) * elem.getBasicSalary();
//                    } else if (elem.getSlabCriteria().equalsIgnoreCase("AMOUNT")) {
//                        CompAmt +=  elem.getSlabCriteriaAmt();
//                    }
//                    // To restrict component amount not greater than max component amount
//                    if ((elem.getSlabCriteriaMaxAmt() != 0.0) && (CompAmt > elem.getSlabCriteriaMaxAmt())) {
//                        CompAmt = elem.getSlabCriteriaMaxAmt();                      
//                    }
//                    // for minimum slab criteria amount
//                    if((elem.getSlabCriteriaMinAmt() != 0.0 ) && (CompAmt < elem.getSlabCriteriaMinAmt())){
//                        CompAmt = elem.getSlabCriteriaMinAmt();
//                    }
//                    elem.setAmount(CompAmt);               
//                   if(purposeCode.equalsIgnoreCase("PUR001")){
//                      baseAmtMap.put(elem.getDependentComponent(), elem.getAmount());                       
//                    }
//                   if(purposeCode.equalsIgnoreCase("PUR002")){
//                      baseAmtMap.put(elem.getDependentComponent(), elem.getAmount());                           
//                    }                
//            }
//                       
//            List listOfDistinctComponent = em.createNativeQuery("select distinct DEPEND_COMPONENT,ifnull(ss.GL_CODE,''),ifnull(ss.DESC_SHORT_CODE,'') from "
//                    + "hr_salary_allocation sa,hr_basic_salary_structure bs,hr_slab_master sm,hr_pe"
//                    + "rsonnel_details pd,hr_salary_structure ss where "
//                    + "sa.EMP_CODE=pd.EMP_CODE and sa.SLAB_CODE=sm.SLAB_CODE and bs.salary_slab_id=sm.SALARY_SLAB_ID and ss.AL_DESC=sm.DEPEND_COMPONENT and "
//                    + "sa.EMP_CODE='" + empCd + "'/* and pd.BASE_BRANCH='" + compCode + "' */ and sm.PURPOSE_CODE='" + purposeCode + "' "
//                    + "and (sm.RANGE_FROM<=sa.BASIC_SALARY and  sm.RANGE_TO>=sa.BASIC_SALARY ) and sm.APP_FLG='Y' ORDER by sm.DEPEND_COMPONENT desc").getResultList();
//
//            List<EmpSalaryAllocationGridTO> componentEmpWiseList = getSalaryComponent(compCode, empCd, purposeCode, "ALL");
//
//            Map componetAmtMap = new HashMap();
//
//            if ((!componentEmpWiseList.isEmpty()) || (!listOfDistinctComponent.isEmpty()) ) {
//                
//                for (Object compObj : listOfDistinctComponent) {
//                     Object[] ele = (Object[]) compObj;                     
//                    String currComponent = ele[0].toString();
//                    double pfamt = 0.0;
//                    double componentAmnt = 0.0;                           
//                    String shCode = "";
//                    String glCode = "";
//                         glCode = ele[1].toString();
//                         shCode = ele[2].toString();
//                       
//                    for (EmpSalaryAllocationGridTO componentData : componentEmpWiseList) {
//
//                        String slabCriteria = componentData.getSlabCriteria();
//                        double slabCriteriaAmount = componentData.getSlabCriteriaAmt();
//                     
//                   
//                        if (componentData.getDependentComponent().equalsIgnoreCase(currComponent)) {
//                            // To define Base Amount for calculation    
//                            double baseAmount = 0.0;
//                            double compAmt = 0.0;
//
//                            if (componentData.getBaseComponent().equalsIgnoreCase("BASIC")) {
//                                baseAmount = componentData.getBasicSalary();                                
//                            } else if(baseAmtMap.containsKey(componentData.getBaseComponent())) {
//                                baseAmount = (Double) baseAmtMap.get(componentData.getBaseComponent());
//                            }else if(restMap.containsKey(componentData.getBaseComponent())){
//                                baseAmount = (Double) restMap.get(componentData.getBaseComponent());
//                            }
//                     
//                            if (slabCriteria.equalsIgnoreCase("PERCENTAGE")) {
//                                compAmt += (slabCriteriaAmount / 100) * baseAmount ;
//                                componentData.setAmount(compAmt);
//
//                                if(baseAmtMap.containsKey(componentData.getDependentComponent())){
//                                      double amnt = compAmt + (Double)baseAmtMap.get(componentData.getDependentComponent());
//                                      baseAmtMap.put(componentData.getDependentComponent(),amnt);
//                                }
//                               else if(!(baseAmtMap.containsKey(componentData.getDependentComponent())) && !(restMap.containsKey(componentData.getDependentComponent()))){
//                                     restMap.put(componentData.getDependentComponent(),compAmt);
//                              }else{
//                                     double amnt = compAmt + (Double)restMap.get(componentData.getDependentComponent());                                    
//                                     restMap.put(componentData.getDependentComponent(),amnt);
//                                }
// 
//                                baseComponentEmpWiseList.add(componentData);
//                            } else if (slabCriteria.equalsIgnoreCase("AMOUNT")) {
//                                compAmt += slabCriteriaAmount  ;
//                                componentData.setAmount(compAmt);
//      
//                                if(baseAmtMap.containsKey(componentData.getDependentComponent())){
//                                        double amnt = compAmt + (Double)baseAmtMap.get(componentData.getDependentComponent());
//                                        baseAmtMap.put(componentData.getDependentComponent(),amnt);
//                                }else if(!(baseAmtMap.containsKey(componentData.getDependentComponent())) && !(restMap.containsKey(componentData.getDependentComponent()))){
//                                     restMap.put(componentData.getDependentComponent(),compAmt);
//                                }else{
//                                    double amnt = compAmt + (Double)restMap.get(componentData.getDependentComponent());
//                                        restMap.put(componentData.getDependentComponent(),amnt);
//                                }
//                                
//                                baseComponentEmpWiseList.add(componentData);
//                            }
//                            // To restrict component amount not greater than max component amount
//                            if ((componentData.getSlabCriteriaMaxAmt() != 0.0) && (compAmt > componentData.getSlabCriteriaMaxAmt())) {
//                                compAmt = componentData.getSlabCriteriaMaxAmt();
//                                componentData.setAmount(compAmt);
//
//                                if(baseAmtMap.containsKey(componentData.getDependentComponent())){
//                                      double amnt = compAmt + (Double)baseAmtMap.get(componentData.getDependentComponent());
//                                        baseAmtMap.put(componentData.getDependentComponent(),amnt);
//                                }
//                                 else if(!(baseAmtMap.containsKey(componentData.getDependentComponent())) && !(restMap.containsKey(componentData.getDependentComponent()))){
//                                     restMap.put(componentData.getDependentComponent(),compAmt);
//                                }else {
//                                    double amnt = compAmt + (Double)restMap.get(componentData.getDependentComponent());;
//                                    restMap.put(componentData.getDependentComponent(),amnt);
//                                }
//                                 
//                                baseComponentEmpWiseList.add(componentData);
//                            }
//                        
//                            componentAmnt = componentAmnt + compAmt ;
//                    
//                            if(purposeCode.equalsIgnoreCase("PUR002")){
//                                  if(baseAmtMap.containsKey(componentData.getDependentComponent())){
//                                      double ant =(Double) baseAmtMap.get(componentData.getDependentComponent());                                    
//                                      baseAmtMap.put(componentData.getDependentComponent(),ant);
//                                  }else{
//                                      double ant =(Double) restMap.get(componentData.getDependentComponent());                                     
//                                      restMap.put(componentData.getDependentComponent(),ant);
//                                  }
//                            }
//
//                        }
//                    
//                    }
//                
//                    // add here EmpSalaryAllocationGridTO object and get the base from that object component amout as
//                    if(purposeCode.equalsIgnoreCase("PUR001")){
//                    EmpSalaryAllocationGridTO empCompDtl = new EmpSalaryAllocationGridTO();
//                    empCompDtl.setComponent(currComponent);
//
//                     if(baseAmtMap.containsKey(currComponent)){
//                     double compAmount = (Double)baseAmtMap.get(ele[0].toString());
//                     empCompDtl.setAmount(compAmount);
//                     }else{
//                    double compAmount = componentAmnt ;
//                    empCompDtl.setAmount(compAmount);
//                    }                   
//                    empCompDtl.setGlCode(glCode);
//                    empCompDtl.setShCode(shCode);
//                    componetAmtMap.put(currComponent, empCompDtl);
//                    empsalaryBreakUpLst.add(empCompDtl);
//                     } else{
//                    EmpSalaryAllocationGridTO empCompDtl = new EmpSalaryAllocationGridTO();
//                    empCompDtl.setComponent(currComponent);
//                    if(baseAmtMap.containsKey(currComponent)){
//                // add parameter PFFIX for khattri pf=1800    
                   //  if(pfFixParam == 1){
//                     if(purposeCode.equalsIgnoreCase("PUR002") && shCode.equalsIgnoreCase("1")){                        
//                        
//                       for(int i=0;i<baseComponentEmpWiseList.size();i++){
//                          if(baseComponentEmpWiseList.get(i).getPurposeCode().equalsIgnoreCase("PUR002") && baseComponentEmpWiseList.get(i).getShCode().equalsIgnoreCase("1") ){
//                              pfamt = baseComponentEmpWiseList.get(i).getSlabCriteriaMaxAmt();
//                        }}
                         
//                         if((Double)baseAmtMap.get(currComponent)>=pfamt && pfamt!= 0.0){
//                            empCompDtl.setAmount(pfamt); 
//                         }else{
//                         empCompDtl.setAmount((Double)baseAmtMap.get(currComponent));
//                         } 
//                     }
//                     
//                     else{
//                          empCompDtl.setAmount((Double)baseAmtMap.get(currComponent));
//                     
//                     }} else if(pfFixParam==0){
//                         empCompDtl.setAmount((Double)baseAmtMap.get(currComponent));
//                    }}else if(pfFixParam==0 && !(baseAmtMap.containsKey(currComponent))){
//                        empCompDtl.setAmount((Double)restMap.get(currComponent));
//                    }
//                    
//                    empCompDtl.setGlCode(glCode);
//                    empCompDtl.setShCode(shCode);
//                    componetAmtMap.put(currComponent, empCompDtl);
//                    empsalaryBreakUpLst.add(empCompDtl);   
//                    }                   
//                }
//            }
//           
//        
//         finalCompAmt = 0.0;
//     
//        }}catch (Exception ex) {       
//            throw new ApplicationException(ex.getMessage());
//        }
//        return empsalaryBreakUpLst;
//    }

       //getSalaryComponent code before min balance minimum slab amount update
       
//     public List<EmpSalaryAllocationGridTO> getSalaryComponent(int compCode, String empCd, String purposeCode, String baseComp) throws ApplicationException {
//        List<EmpSalaryAllocationGridTO> empsalaryBreakUpLst = new ArrayList<>();
//            
//        String condn = "";
//        if (baseComp.equalsIgnoreCase("BASIC")) {
//            condn = "and  BASE_COMPONENT='BASIC'";
//        } else if (baseComp.equalsIgnoreCase("ALL")) {
//            condn = "and  BASE_COMPONENT<>'BASIC'";
//        } 
////        else if (baseComp.equalsIgnoreCase("NONBASIC")) {
////            condn = "and  BASE_COMPONENT<>'BASIC'";
////        }
//           
//         List list = em.createNativeQuery("select pd.EMP_CODE,pd.BASE_BRANCH,sm.BASE_COMPONENT,sm.DEPEND_COMPONENT,sa.BASIC_SALARY,sm.SLAB_CRITERIA,sm.SLAB_CRITERIA_AMT,sm.MAX_CRITERIA_AMT,\n"
//                + "ss.NATURE,ss.PURPOSE_CODE,ifnull(GL_CODE,''),ifnull(DESC_SHORT_CODE,''),COMPONENT_ORDER,sm.MIN_CRITERIA_AMT from \n"
//                + "hr_salary_allocation sa,hr_basic_salary_structure bs,hr_slab_master sm,hr_personnel_details pd,hr_salary_structure ss where \n"
//                + "sa.EMP_CODE=pd.EMP_CODE and sa.SLAB_CODE=sm.SLAB_CODE and bs.salary_slab_id=sm.SALARY_SLAB_ID and ss.AL_DESC=sm.DEPEND_COMPONENT and "
//                + "sa.EMP_CODE='" + empCd + "' and sm.PURPOSE_CODE='" + purposeCode + "'   " + condn + ""   
//                + "and (sm.RANGE_FROM<=sa.BASIC_SALARY and  sm.RANGE_TO>=sa.BASIC_SALARY ) and sm.APP_FLG='Y' ORDER by COMPONENT_ORDER ASC").getResultList();
//         if (!list.isEmpty()) {
//            for (Object obj : list) {
//                Object[] ele = (Object[]) obj;
//                EmpSalaryAllocationGridTO pojo = new EmpSalaryAllocationGridTO();
//                pojo.setEmpid(ele[0].toString());
//                pojo.setBaseComponent(ele[2].toString());
//                pojo.setDependentComponent(ele[3].toString());
//                pojo.setBasicSalary(Double.valueOf(ele[4].toString()));
//                pojo.setSlabCriteria(ele[5].toString());
//                pojo.setSlabCriteriaAmt(Double.valueOf(ele[6].toString()));
//                pojo.setSlabCriteriaMaxAmt(Double.valueOf(ele[7].toString()));
//                pojo.setNature(ele[8].toString());
//                pojo.setPurposeCode(ele[9].toString());
//                pojo.setGlCode(ele[10].toString());
//                pojo.setShCode(ele[11].toString());
//                pojo.setCompOrder(Integer.valueOf(ele[12].toString()));
//                pojo.setSlabCriteriaMinAmt(Double.valueOf(ele[13].toString()));
//                empsalaryBreakUpLst.add(pojo);
//            }            
//        }
//        return empsalaryBreakUpLst;
//    }
      // end
       
       
     public List<EmpSalaryAllocationGridTO> getSalaryComponent(int compCode, String empCd, String purposeCode) throws ApplicationException {
        List<EmpSalaryAllocationGridTO> empsalaryBreakUpLst = new ArrayList<>();
    
          List list = em.createNativeQuery("select pd.EMP_CODE,pd.BASE_BRANCH,sm.BASE_COMPONENT,sm.DEPEND_COMPONENT,sa.BASIC_SALARY,sm.SLAB_CRITERIA,sm.SLAB_CRITERIA_AMT,sm.MAX_CRITERIA_AMT,\n"
                + "ss.NATURE,ss.PURPOSE_CODE,ifnull(GL_CODE,''),ifnull(DESC_SHORT_CODE,''),COMPONENT_ORDER,ifnull(sm.MIN_CRITERIA_AMT,0.0) as MIN_CRITERIA_AMT  from \n"
                + "hr_salary_allocation sa,hr_basic_salary_structure bs,hr_slab_master sm,hr_personnel_details pd,hr_salary_structure ss where \n"
                + "sa.EMP_CODE=pd.EMP_CODE and sa.SLAB_CODE=sm.SLAB_CODE and bs.salary_slab_id=sm.SALARY_SLAB_ID and ss.AL_DESC=sm.DEPEND_COMPONENT and "
                + "sa.EMP_CODE='" + empCd + "'"   
                + "and (sm.RANGE_FROM<=sa.BASIC_SALARY and  sm.RANGE_TO>=sa.BASIC_SALARY ) and sm.APP_FLG='Y' ORDER by COMPONENT_ORDER ASC").getResultList();
         if (!list.isEmpty()) {
            for (Object obj : list) {
                Object[] ele = (Object[]) obj;
                EmpSalaryAllocationGridTO pojo = new EmpSalaryAllocationGridTO();
                pojo.setEmpid(ele[0].toString());
                pojo.setBaseComponent(ele[2].toString());
                pojo.setDependentComponent(ele[3].toString());
                pojo.setBasicSalary(Double.valueOf(ele[4].toString()));
                pojo.setSlabCriteria(ele[5].toString());
                pojo.setSlabCriteriaAmt(Double.valueOf(ele[6].toString()));
                pojo.setSlabCriteriaMaxAmt(Double.valueOf(ele[7].toString()));
                pojo.setNature(ele[8].toString());
                pojo.setPurposeCode(ele[9].toString());
                pojo.setGlCode(ele[10].toString());
                pojo.setShCode(ele[11].toString());
                pojo.setCompOrder(Integer.valueOf(ele[12].toString()));
                pojo.setSlabCriteriaMinAmt(Double.valueOf(ele[13].toString()));
                empsalaryBreakUpLst.add(pojo);
            }            
        }
        return empsalaryBreakUpLst;
    }   
     
  @Override
    public List<HrSalaryProcessingTO> salaryCalculation(HrSalaryProcessingTO comingHrSalaryProcessingTO, String selectionCriteria, String selectedValues, String month) throws Exception {
        long begin = System.nanoTime();
         HashMap<String,Double> pfBaseMap = new HashMap<String,Double>();
        
        List<HrSalaryProcessingTO> salaryList = new ArrayList<>();
        try {
            HrPersonnelDetailsDAO personnelDAO = new HrPersonnelDetailsDAO(em);
            HrSalaryProcessingDAO salaryDAO = new HrSalaryProcessingDAO(em);
            HrAttendanceDetailsDAO attendanceDetailsDAO = new HrAttendanceDetailsDAO(em);
            HrSalaryAllocationDAO salaryAllocationDAO = new HrSalaryAllocationDAO(em);

            int dayInMonth = 0;
            List dateCntList = em.createNativeQuery("select ifnull(code,0) from parameterinfo_report "
                    + " where reportname = 'DAYS_IN_MONTH'").getResultList();
            if (!dateCntList.isEmpty()) {
                Vector vecMonth = (Vector) dateCntList.get(0);
                dayInMonth = Integer.parseInt(vecMonth.get(0).toString());
            }

            int taxOnSal = 0;
            List taxSalList = em.createNativeQuery("select ifnull(code,0) from parameterinfo_report "
                    + " where reportname = 'TAX_ON_SAL'").getResultList();
            if (!taxSalList.isEmpty()) {
                taxOnSal = Integer.parseInt(taxSalList.get(0).toString());
            }

            //GROSS POSTING IN ACCOUNT OR NET POSTING 0 : GROSS , 1: NET 
            int grossOrNet = ftsRemote.getCodeForReportName("GROSS_OR_NET");

            List<HrPersonnelDetails> empList = new ArrayList<>();
            if (selectionCriteria.equalsIgnoreCase("EWE")) {
                HrPersonnelDetails personnelDetails = personnelDAO.findByEmpStatusAndEmpId(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode(), selectedValues, 'Y');
                empList.add(personnelDetails);
            } else {
                empList = personnelDAO.findEntityEmpStatusY(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode(), selectedValues);
            }
            if (empList.isEmpty()) {
                throw new Exception("Either Employee does not exist or Employee is not active.");
            }

            String prevEmpId = "";
            for (HrPersonnelDetails personnelDetails : empList) {
                
                 Map<String,Long> dedMap = new HashMap<String,Long>(); 
          
                HrSalaryProcessingTO goingHrSalaryProcessingTO = new HrSalaryProcessingTO();
                HrSalaryProcessingPKTO goingHrsalaryProcessingPKTO = new HrSalaryProcessingPKTO();
                goingHrsalaryProcessingPKTO.setCalDateFrom(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCalDateFrom());
                goingHrsalaryProcessingPKTO.setCalDateTo(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCalDateTo());
                goingHrsalaryProcessingPKTO.setCompCode(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                goingHrsalaryProcessingPKTO.setMonths(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getMonths());
                goingHrSalaryProcessingTO.setHrSalaryProcessingPK(goingHrsalaryProcessingPKTO);
                goingHrSalaryProcessingTO.setAuthBy(comingHrSalaryProcessingTO.getAuthBy());
                goingHrSalaryProcessingTO.setDefaultComp(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                goingHrSalaryProcessingTO.setEnteredBy(comingHrSalaryProcessingTO.getEnteredBy());
                goingHrSalaryProcessingTO.setStatFlag("Y");
                goingHrSalaryProcessingTO.setStatUpFlag("Y");
                goingHrSalaryProcessingTO.setModDate(new Date());
                goingHrSalaryProcessingTO.setYear(comingHrSalaryProcessingTO.getYear());

                List refNoList = em.createNativeQuery("select ifnull(max(ref_no),0)+1 from hr_salary_processing").getResultList();
                int refNo = Integer.valueOf(refNoList.get(0).toString());

                comingHrSalaryProcessingTO.getHrSalaryProcessingPK().setEmpCode(personnelDetails.getHrPersonnelDetailsPK().getEmpCode().longValue());
                goingHrSalaryProcessingTO.setHrPersonnelDetailsTO(ObjectAdaptorHr.adaptTOHrPersonnelDetailsTO(personnelDetails));

                List<HrSalaryProcessing> hrSalaryProcessingList = salaryDAO.getSalaryForTheMonthWithPostFlag(PayrollObjectAdaptor.adaptToHrSalaryProcessingEntity(goingHrSalaryProcessingTO));
                if (!hrSalaryProcessingList.isEmpty()) {
                    throw new Exception("Salary already posted for month " + comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getMonths() + " and for employee " + personnelDetails.getEmpName());
                }
                HrSalaryAllocationPK salaryAllocationPK = new HrSalaryAllocationPK();
                salaryAllocationPK.setCompCode(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode()); //90
                salaryAllocationPK.setEmpCode(personnelDetails.getHrPersonnelDetailsPK().getEmpCode().longValue());

                List basicSal = em.createNativeQuery("select distinct BASIC_SALARY from  hr_salary_allocation where EMP_CODE='"+personnelDetails.getHrPersonnelDetailsPK().getEmpCode().longValue()+"' ").getResultList();
                 String basicSalary = basicSal.get(0).toString();
                 goingHrSalaryProcessingTO.setBasicSalary(basicSalary);

                HrAttendanceDetailsPK hrAttendetailsPK = new HrAttendanceDetailsPK();
                HrAttendanceDetails hrAttenDetails = new HrAttendanceDetails();

                hrAttendetailsPK.setAttenMonth(month);
                hrAttendetailsPK.setAttenYear(Integer.parseInt(comingHrSalaryProcessingTO.getYear()));
                hrAttendetailsPK.setCompCode(salaryAllocationPK.getCompCode());
                hrAttendetailsPK.setEmpCode(salaryAllocationPK.getEmpCode());

                hrAttenDetails.setPostFlag('Y');
                hrAttenDetails.setHrAttendanceDetailsPK(hrAttendetailsPK);

                List<HrAttendanceDetails> hrAttendanceDetailsList = attendanceDetailsDAO.findAttendanceDetailsPostedForMonth(hrAttenDetails);
                if (hrAttendanceDetailsList.isEmpty()) {
                    throw new Exception("Attendance is not posted for month " + comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getMonths() + " and for employee " + personnelDetails.getEmpName());
                }
       
                HrAttendanceDetails attendanceDetails = hrAttendanceDetailsList.get(0);
                int absentDays = attendanceDetails.getAbsentDays();
                long overTimePd = attendanceDetails.getOverTimePeriod();
                String overTimeunit = attendanceDetails.getOverTimeUnit();
                double deductDays = attendanceDetails.getDeductDays();

                int monthDays;
                if (dayInMonth == 0) {
                    monthDays = 30;
                } else {
                    Date dt = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(month);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(dt);
                    monthDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                }

                double actualDays = monthDays - (absentDays + deductDays);

                List<HrSalaryProcessingDetailTO> hrSalaryProcessingFinalDetailTO = new ArrayList<>();
//                baseAmtMap.clear();
//                restMap.clear(); 
                // aditya added
                
//                List acno = em.createNativeQuery("select BANK_ACCOUNT_CODE from hr_personnel_details where EMP_CODE='"+personnelDetails.getHrPersonnelDetailsPK().getEmpCode().longValue()+"'").getResultList();
//                if(!acno.isEmpty()){
//                 
//                HrSalaryProcessingTO hspt = new HrSalaryProcessingTO();
//                 hspt.setAcno(acno.get(0).toString());
//                }
                
                
                List<String> pfComp = getPFBaseComponent(personnelDetails.getHrPersonnelDetailsPK().getEmpCode()) ;
                List basicSalCompDetails = em.createNativeQuery("select distinct a.EMP_CODE,h.COMP_CODE,a.BASIC_SALARY,h.NATURE,h.PURPOSE_CODE,ifnull(h.GL_CODE,''),h.DESC_SHORT_CODE  from hr_salary_structure h, hr_salary_allocation a where h.AL_DESC='basic' and a.COMP_CODE= h.COMP_CODE and  EMP_CODE='"+personnelDetails.getHrPersonnelDetailsPK().getEmpCode().longValue()+"'").getResultList();
                  if (!basicSalCompDetails.isEmpty()) {
                for (Object obj : basicSalCompDetails) {
                Object[] ele = (Object[]) obj;
                 double totAmt = 0d;
                 totAmt  =  Math.round(((Double.valueOf(ele[2].toString())) * actualDays) / monthDays);
                // totAmt = Double.parseDouble(new DecimalFormat("##.##").format();
                   HrSalaryProcessingDetailPKTO hrSalaryProcessingDetailPK1 = new HrSalaryProcessingDetailPKTO();
                   HrSalaryProcessingDetailTO hrSalaryProcessingDetailTO1 = new HrSalaryProcessingDetailTO();
                      hrSalaryProcessingDetailPK1.setCompCode(Integer.valueOf(ele[1].toString()));
                      hrSalaryProcessingDetailPK1.setComponentType("BASIC");
                      hrSalaryProcessingDetailPK1.setEmpCode(Long.decode(ele[0].toString()));
                      hrSalaryProcessingDetailPK1.setRefNo(refNo);
                      hrSalaryProcessingDetailTO1.setDefaultComp(Integer.valueOf(ele[1].toString()));
                      hrSalaryProcessingDetailTO1.setSalary(totAmt);
                      hrSalaryProcessingDetailTO1.setHrSalaryProcessingDetailPK(hrSalaryProcessingDetailPK1);
                      hrSalaryProcessingDetailTO1.setGlCode(ele[5].toString());
                      hrSalaryProcessingDetailTO1.setShCode(Integer.valueOf(ele[6].toString()));
                      hrSalaryProcessingDetailTO1.setPurCode("PUR001");
                    //    hrSalaryProcessingDetailTO1.setAcno(acno.get(0).toString());
                     hrSalaryProcessingFinalDetailTO.add(hrSalaryProcessingDetailTO1);
                
                      if(pfComp.contains("BASIC")){
                       pfBaseMap.put("BASIC", totAmt);
                            }                     
            }}
                List<EmpSalaryAllocationGridTO> salaryAmtList = getNewSalaryBreakUp(salaryAllocationPK.getCompCode(), String.valueOf(salaryAllocationPK.getEmpCode()), "PUR001");
                double allowancesAmt = 0d;
                if (!salaryAmtList.isEmpty()) {
                    for (EmpSalaryAllocationGridTO ele : salaryAmtList) {
                        if((!ele.getComponent().equalsIgnoreCase("BASIC")) && ele.getPurposeCode().equalsIgnoreCase("PUR001")){
                          if((ele.getComponent().contains("SPL") || ele.getComponent().contains("Spl") || ele.getComponent().contains("spl"))  && ele.getPurposeCode().equalsIgnoreCase("PUR001")){
                          String compName="SPL";
                          double totAmt = (ele.getAmount() * actualDays) / monthDays;
                        int shCode = ele.getShCode().equalsIgnoreCase("") || ele.getShCode() == null ? 0 : Integer.parseInt(ele.getShCode());
                        HrSalaryProcessingDetailPKTO hrSalaryProcessingDetailPK = new HrSalaryProcessingDetailPKTO();
                        HrSalaryProcessingDetailTO hrSalaryProcessingDetailTO = new HrSalaryProcessingDetailTO();
                        hrSalaryProcessingDetailPK.setCompCode(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                        hrSalaryProcessingDetailPK.setComponentType(compName);
                        hrSalaryProcessingDetailPK.setEmpCode(salaryAllocationPK.getEmpCode());
                        hrSalaryProcessingDetailPK.setRefNo(refNo);
                        hrSalaryProcessingDetailTO.setDefaultComp(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                        hrSalaryProcessingDetailTO.setSalary(Math.round(totAmt));
                        hrSalaryProcessingDetailTO.setHrSalaryProcessingDetailPK(hrSalaryProcessingDetailPK);
                        hrSalaryProcessingDetailTO.setGlCode(ele.getGlCode());
                        hrSalaryProcessingDetailTO.setShCode(shCode);
                        hrSalaryProcessingDetailTO.setPurCode("PUR001");
                        hrSalaryProcessingFinalDetailTO.add(hrSalaryProcessingDetailTO);
                        allowancesAmt = allowancesAmt + Math.round(totAmt);
           
                        if(pfComp.contains(ele.getComponent()) && !ele.getComponent().equalsIgnoreCase("BASIC")){
                           pfBaseMap.put(ele.getComponent(), totAmt);
                        }
                      }else{
                        double totAmt = (ele.getAmount() * actualDays) / monthDays;
                        int shCode = ele.getShCode().equalsIgnoreCase("") || ele.getShCode() == null ? 0 : Integer.parseInt(ele.getShCode());
                        HrSalaryProcessingDetailPKTO hrSalaryProcessingDetailPK = new HrSalaryProcessingDetailPKTO();
                        HrSalaryProcessingDetailTO hrSalaryProcessingDetailTO = new HrSalaryProcessingDetailTO();
                        hrSalaryProcessingDetailPK.setCompCode(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                        hrSalaryProcessingDetailPK.setComponentType(ele.getComponent());
                        hrSalaryProcessingDetailPK.setEmpCode(salaryAllocationPK.getEmpCode());
                        hrSalaryProcessingDetailPK.setRefNo(refNo);
                        hrSalaryProcessingDetailTO.setDefaultComp(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                        hrSalaryProcessingDetailTO.setSalary(Math.round(totAmt));
                        hrSalaryProcessingDetailTO.setHrSalaryProcessingDetailPK(hrSalaryProcessingDetailPK);
                        hrSalaryProcessingDetailTO.setGlCode(ele.getGlCode());
                        hrSalaryProcessingDetailTO.setShCode(shCode);
                        hrSalaryProcessingDetailTO.setPurCode("PUR001");
                        hrSalaryProcessingFinalDetailTO.add(hrSalaryProcessingDetailTO);
                        allowancesAmt = allowancesAmt + Math.round(totAmt);
           
                        if(pfComp.contains(ele.getComponent()) && !ele.getComponent().contains("BASIC")){
                           pfBaseMap.put(ele.getComponent(), totAmt);
                        }
           
                      }
                     }}
                
                // List<EmpSalaryAllocationGridTO> salaryAmtListPUR002 = getSalaryBreakUp(salaryAllocationPK.getCompCode(), String.valueOf(salaryAllocationPK.getEmpCode()), "PUR002");

              double deductionAmt = 0d;
             
                if (!salaryAmtList.isEmpty()) {
                    int a=0 , b=0,c=0;
                      double totDedAmt =0d;  
                      double basicAmt = 0.0;
                    for (EmpSalaryAllocationGridTO ele : salaryAmtList) {
                        if(ele.getComponent().equalsIgnoreCase("BASIC")){
                            basicAmt= ele.getAmount();
                        }
                        if(!ele.getComponent().equalsIgnoreCase("BASIC") && ele.getPurposeCode().equalsIgnoreCase("PUR002")){
                            if(ele.getComponent().equalsIgnoreCase("PF")){
                        int pfFixParam = ftsFacade.getCodeForReportName("PFFIX");
                        double basic = 0.0;
                        double minpfcompAmt = 15000.0;
                        double pfAmt =1800.0;
                        
                      totDedAmt = (ele.getAmount() * actualDays) / monthDays;
                         if(pfFixParam == 1){
                              totDedAmt= 0d;      
                             for(Map.Entry<String,Double> entry :pfBaseMap.entrySet()){
                                 basic+= Math.round((Double)(entry.getValue()));
                             }
                       //  12% pf on dependent components     
                         double finalpfamt = ((basic * 12)/100);    
                      //    totDedAmt = (finalpfamt * actualDays) / monthDays;    
                             
                           if(basic  >= minpfcompAmt){
                               totDedAmt = pfAmt;
                           }else{
                               totDedAmt = finalpfamt;    
                           }
                        }
                        int shCode = ele.getShCode().equalsIgnoreCase("") || ele.getShCode() == null ? 0 : Integer.parseInt(ele.getShCode());
                        HrSalaryProcessingDetailPKTO hrSalaryProcessingDetailPK = new HrSalaryProcessingDetailPKTO();
                        HrSalaryProcessingDetailTO hrSalaryProcessingDetailTO = new HrSalaryProcessingDetailTO();
                        hrSalaryProcessingDetailPK.setCompCode(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                        hrSalaryProcessingDetailPK.setComponentType(ele.getComponent());
                        hrSalaryProcessingDetailPK.setEmpCode(salaryAllocationPK.getEmpCode());
                        hrSalaryProcessingDetailPK.setRefNo(refNo);
                        hrSalaryProcessingDetailTO.setDefaultComp(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                        hrSalaryProcessingDetailTO.setSalary(Math.round(totDedAmt));
                        hrSalaryProcessingDetailTO.setHrSalaryProcessingDetailPK(hrSalaryProcessingDetailPK);
                        hrSalaryProcessingDetailTO.setGlCode(ele.getGlCode());
                        hrSalaryProcessingDetailTO.setShCode(shCode);
                        hrSalaryProcessingDetailTO.setPurCode("PUR002");
                        hrSalaryProcessingFinalDetailTO.add(hrSalaryProcessingDetailTO);
                        if(!dedMap.containsKey(ele.getComponent())){
                        dedMap.put(ele.getComponent(), Math.round(totDedAmt));
                        }    
                        pfBaseMap.clear();
                        }   
                    else if(ele.getComponent().contains("INC")){
                       totDedAmt=  Math.round(ele.getAmount()) ;
                       String compName = "INCOME TAX";
                        int shCode = ele.getShCode().equalsIgnoreCase("") || ele.getShCode() == null ? 0 : Integer.parseInt(ele.getShCode());
                        HrSalaryProcessingDetailPKTO hrSalaryProcessingDetailPK = new HrSalaryProcessingDetailPKTO();
                        HrSalaryProcessingDetailTO hrSalaryProcessingDetailTO = new HrSalaryProcessingDetailTO();
                        hrSalaryProcessingDetailPK.setCompCode(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                        hrSalaryProcessingDetailPK.setComponentType(compName);
                        hrSalaryProcessingDetailPK.setEmpCode(salaryAllocationPK.getEmpCode());
                        hrSalaryProcessingDetailPK.setRefNo(refNo);
                        hrSalaryProcessingDetailTO.setDefaultComp(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                        hrSalaryProcessingDetailTO.setSalary(Math.round(ele.getAmount()));
                        hrSalaryProcessingDetailTO.setHrSalaryProcessingDetailPK(hrSalaryProcessingDetailPK);
                        hrSalaryProcessingDetailTO.setGlCode(ele.getGlCode());
                        hrSalaryProcessingDetailTO.setShCode(shCode);
                        hrSalaryProcessingDetailTO.setPurCode("PUR002");
                        hrSalaryProcessingFinalDetailTO.add(hrSalaryProcessingDetailTO);
                        if(!dedMap.containsKey(ele.getComponent())){
                        dedMap.put(ele.getComponent(), Math.round(totDedAmt));
                        }
                      }
                      else if(ele.getComponent().contains("LIC")){
                         totDedAmt=  Math.round(ele.getAmount()) ; 
                         String CompName = "LIC";
                         int shCode = ele.getShCode().equalsIgnoreCase("") || ele.getShCode() == null ? 0 : Integer.parseInt(ele.getShCode());
                        HrSalaryProcessingDetailPKTO hrSalaryProcessingDetailPK = new HrSalaryProcessingDetailPKTO();
                        HrSalaryProcessingDetailTO hrSalaryProcessingDetailTO = new HrSalaryProcessingDetailTO();
                        hrSalaryProcessingDetailPK.setCompCode(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                        hrSalaryProcessingDetailPK.setComponentType(CompName);
                        hrSalaryProcessingDetailPK.setEmpCode(salaryAllocationPK.getEmpCode());
                        hrSalaryProcessingDetailPK.setRefNo(refNo);
                        hrSalaryProcessingDetailTO.setDefaultComp(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                        hrSalaryProcessingDetailTO.setSalary(Math.round(ele.getAmount()));
                        hrSalaryProcessingDetailTO.setHrSalaryProcessingDetailPK(hrSalaryProcessingDetailPK);
                        hrSalaryProcessingDetailTO.setGlCode(ele.getGlCode());
                        hrSalaryProcessingDetailTO.setShCode(shCode);
                        hrSalaryProcessingDetailTO.setPurCode("PUR002");
                        hrSalaryProcessingFinalDetailTO.add(hrSalaryProcessingDetailTO);
                        if(!dedMap.containsKey(ele.getComponent())){
                        dedMap.put(ele.getComponent(), Math.round(totDedAmt));
                        }
                      }  
                      
                      else if(ele.getComponent().contains("TDS")){
                         totDedAmt=  Math.round(ele.getAmount()) ; 
                         String CompName = "TDS";
                         int shCode = ele.getShCode().equalsIgnoreCase("") || ele.getShCode() == null ? 0 : Integer.parseInt(ele.getShCode());
                        HrSalaryProcessingDetailPKTO hrSalaryProcessingDetailPK = new HrSalaryProcessingDetailPKTO();
                        HrSalaryProcessingDetailTO hrSalaryProcessingDetailTO = new HrSalaryProcessingDetailTO();
                        hrSalaryProcessingDetailPK.setCompCode(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                        hrSalaryProcessingDetailPK.setComponentType(CompName);
                        hrSalaryProcessingDetailPK.setEmpCode(salaryAllocationPK.getEmpCode());
                        hrSalaryProcessingDetailPK.setRefNo(refNo);
                        hrSalaryProcessingDetailTO.setDefaultComp(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                        hrSalaryProcessingDetailTO.setSalary(Math.round(ele.getAmount()));
                        hrSalaryProcessingDetailTO.setHrSalaryProcessingDetailPK(hrSalaryProcessingDetailPK);
                        hrSalaryProcessingDetailTO.setGlCode(ele.getGlCode());
                        hrSalaryProcessingDetailTO.setShCode(shCode);
                        hrSalaryProcessingDetailTO.setPurCode("PUR002");
                        hrSalaryProcessingFinalDetailTO.add(hrSalaryProcessingDetailTO);
                        if(!dedMap.containsKey(ele.getComponent())){
                        dedMap.put(ele.getComponent(), Math.round(totDedAmt));
                        }
                      }else{
                        totDedAmt=  Math.round(ele.getAmount()) ;                        
                        int shCode = ele.getShCode().equalsIgnoreCase("") || ele.getShCode() == null ? 0 : Integer.parseInt(ele.getShCode());
                        HrSalaryProcessingDetailPKTO hrSalaryProcessingDetailPK = new HrSalaryProcessingDetailPKTO();
                        HrSalaryProcessingDetailTO hrSalaryProcessingDetailTO = new HrSalaryProcessingDetailTO();
                        hrSalaryProcessingDetailPK.setCompCode(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                        hrSalaryProcessingDetailPK.setComponentType(ele.getComponent());
                        hrSalaryProcessingDetailPK.setEmpCode(salaryAllocationPK.getEmpCode());
                        hrSalaryProcessingDetailPK.setRefNo(refNo);
                        hrSalaryProcessingDetailTO.setDefaultComp(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                        hrSalaryProcessingDetailTO.setSalary(Math.round(ele.getAmount()));
                        hrSalaryProcessingDetailTO.setHrSalaryProcessingDetailPK(hrSalaryProcessingDetailPK);
                        hrSalaryProcessingDetailTO.setGlCode(ele.getGlCode());
                        hrSalaryProcessingDetailTO.setShCode(shCode);
                        hrSalaryProcessingDetailTO.setPurCode("PUR002");
                        hrSalaryProcessingFinalDetailTO.add(hrSalaryProcessingDetailTO);
                        if(!dedMap.containsKey(ele.getComponent())){
                        dedMap.put(ele.getComponent(), Math.round(totDedAmt)); 
                      }  
                       
                      }
                        
                       // deductionAmt = deductionAmt + Math.round(totDedAmt);  
              
                        }
                    } 
                }
                
                  for(Map.Entry<String,Long> entry: dedMap.entrySet()){
                       deductionAmt = deductionAmt + entry.getValue();
                  }  
                int loanRecInSal = ftsRemote.getCodeForReportName("LOAN_REC_IN_SAL");
                if (loanRecInSal == 1) {
                    if (!prevEmpId.equalsIgnoreCase(personnelDetails.getEmpId())) {
                        prevEmpId = personnelDetails.getEmpId();

                        List acNoLst = getLoanAcList(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode(),
                                personnelDetails.getEmpId());
                        if (!acNoLst.isEmpty()) {
                            for (int l = 0; l < acNoLst.size(); l++) {
                                Object[] lLsVec = (Object[]) acNoLst.get(l);
                                String lAcNo = lLsVec[0].toString();
                                double emiAmt = Double.parseDouble(lLsVec[1].toString());
                                String compType = lLsVec[2].toString();
                                HrSalaryProcessingDetailPKTO hrSalaryProcessingDetailPK = new HrSalaryProcessingDetailPKTO();
                                HrSalaryProcessingDetailTO hrSalaryProcessingDetailTO = new HrSalaryProcessingDetailTO();
                                hrSalaryProcessingDetailPK.setCompCode(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                                hrSalaryProcessingDetailPK.setComponentType(compType);
                                hrSalaryProcessingDetailPK.setEmpCode(salaryAllocationPK.getEmpCode());
                                hrSalaryProcessingDetailPK.setRefNo(refNo);
                                hrSalaryProcessingDetailTO.setDefaultComp(comingHrSalaryProcessingTO.getHrSalaryProcessingPK().getCompCode());
                                hrSalaryProcessingDetailTO.setSalary(Math.round(emiAmt));
                                hrSalaryProcessingDetailTO.setHrSalaryProcessingDetailPK(hrSalaryProcessingDetailPK);
                                hrSalaryProcessingDetailTO.setGlCode("");
                                hrSalaryProcessingDetailTO.setShCode(0);

                                if (grossOrNet == 1) {
                                    hrSalaryProcessingDetailTO.setPurCode("PUR003");
                                } else if ((grossOrNet == 2 || (grossOrNet == 0))) {
                                    hrSalaryProcessingDetailTO.setPurCode("PUR002");
                                    deductionAmt = deductionAmt + Math.round(emiAmt);
                                }

                                hrSalaryProcessingFinalDetailTO.add(hrSalaryProcessingDetailTO);
                            }
                        }
                    }
                }

                List<HrSalaryProcessing> preTaxAndSalaryList = salaryDAO.getTaxProcessEmpForYearWithPostFlag(salaryAllocationPK);
                int salaryPostedMonths = 0;
                if (!preTaxAndSalaryList.isEmpty()) {
                    salaryPostedMonths = preTaxAndSalaryList.size();
                }
                if(salaryPostedMonths == 12){
                    salaryPostedMonths = 0;
                }
                double preSalaryAmt = 0d;
                double deductedTaxAmt = 0d;
                for (HrSalaryProcessing taxObj : preTaxAndSalaryList) {
                    preSalaryAmt = preSalaryAmt + taxObj.getGrossSalary();
                    deductedTaxAmt = deductedTaxAmt + taxObj.getDeductiveTax();
                }
                List<HrTaxInvestmentCategory> taxInvCatList = salaryDAO.findByCompCodeAndEmpCode(personnelDetails.getHrPersonnelDetailsPK().getCompCode(), personnelDetails.getHrPersonnelDetailsPK().getEmpCode().longValue());
                double totalInvestmentAmt = 0d;
                for (HrTaxInvestmentCategory taxInvCat : taxInvCatList) {
                    if (taxInvCat.getCategoryAmt() > taxInvCat.getCategoryMaxLimit()) {
                        totalInvestmentAmt = totalInvestmentAmt + taxInvCat.getCategoryMaxLimit();
                    } else {
                        totalInvestmentAmt = totalInvestmentAmt + taxInvCat.getCategoryAmt();
                    }
                }

                List<HrTaxSlabMaster> taxSlabList = salaryDAO.findByCompCodeAndTaxFor(personnelDetails.getHrPersonnelDetailsPK().getCompCode(), String.valueOf(personnelDetails.getSex()));
                double overTimeSalary = 0d, totalAnnualSalary = 0d, monthSalary = 0d;
                if (grossOrNet == 0) {
                    monthSalary = (allowancesAmt + deductionAmt) ;
                    if (overTimeunit.equalsIgnoreCase("HOUR")) {
                        overTimeSalary = (((allowancesAmt + deductionAmt) / monthDays) * (overTimePd / 24));
                        monthSalary = monthSalary + overTimeSalary+ (Double.valueOf(basicSalary)* actualDays)/monthDays;
                    } else {
                        overTimeSalary = (((allowancesAmt + deductionAmt) / monthDays) * (overTimePd / (24 * 60)));
                        monthSalary = monthSalary + overTimeSalary + (Double.valueOf(basicSalary)* actualDays)/monthDays ;
                    }
                    totalAnnualSalary = preSalaryAmt + monthSalary + (allowancesAmt + deductionAmt) * (11 - salaryPostedMonths);
                } else {
                    monthSalary = (allowancesAmt - deductionAmt) ;
                    if (overTimeunit.equalsIgnoreCase("HOUR")) {
                        overTimeSalary = (((allowancesAmt - deductionAmt) / monthDays) * (overTimePd / 24));
                        monthSalary = monthSalary + overTimeSalary  + (Double.valueOf(basicSalary)* actualDays)/monthDays ; 
                    } else {
                        overTimeSalary = (((allowancesAmt - deductionAmt) / monthDays) * (overTimePd / (24 * 60)));
                        monthSalary = monthSalary + overTimeSalary  + (Double.valueOf(basicSalary)* actualDays)/monthDays ; 
                    }
                    totalAnnualSalary = preSalaryAmt + monthSalary + (allowancesAmt - deductionAmt) * (11 - salaryPostedMonths);
                }

                double taxableSalary = totalAnnualSalary - totalInvestmentAmt;
                double totalIncomeTax = 0d;
                for (HrTaxSlabMaster taxSlab : taxSlabList) {
                    if (taxableSalary > taxSlab.getRangeTo()) {
                        totalIncomeTax = totalIncomeTax + (taxSlab.getRangeTo() * taxSlab.getApplicableTax()) / 100;
                        taxableSalary = taxableSalary - taxSlab.getRangeTo();
                    } else {
                        totalIncomeTax = totalIncomeTax + (taxableSalary * taxSlab.getApplicableTax()) / 100;
                        break;
                    }
                }
                double remaingTaxAmt = totalIncomeTax - deductedTaxAmt;
                double monthlyTaxAmt = 0d;
                if (taxOnSal != 0) {
                    monthlyTaxAmt = Math.ceil(remaingTaxAmt / (12 - salaryPostedMonths));
                }
                double monthlyNetSalary = monthSalary - monthlyTaxAmt ;
                goingHrSalaryProcessingTO.setRefNo(refNo);
                goingHrSalaryProcessingTO.setGrossSalary(Math.round(monthSalary));
                goingHrSalaryProcessingTO.setSalary(Math.round(monthlyNetSalary));
                goingHrSalaryProcessingTO.setDeductiveTax(Math.round(monthlyTaxAmt));
                goingHrSalaryProcessingTO.setPostFlag('Y');
                goingHrSalaryProcessingTO.setHrSalaryProcessingDetailTO(hrSalaryProcessingFinalDetailTO);
                salaryList.add(goingHrSalaryProcessingTO);
            }
                 dedMap.clear();
        } }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
          
        return salaryList;
    }

    public List getLoanAcList(int compCode, String empCode) throws ApplicationException {
        List acNoList = new ArrayList();
        try {
            acNoList = em.createNativeQuery("select distinct h.loan_ac,e.INSTALLAMT,am.AcctType from hr_emp_loan_detail h, emidetails e, accountmaster a, accounttypemaster am "
                    + "where h.emp_id = '" + empCode + "' and h.comp_code =" + compCode + " and h.loan_ac = e.acno and e.status ='UNPAID' and e.acno = a.acno and a.Accttype = am.AcctCode").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return acNoList;
    }
    //Aditya add
       
    @Override
    public List getDataFromEmployeeId(String empId) throws ApplicationException {
        List slabCodeList = new ArrayList();     
    
         slabCodeList = em.createNativeQuery("select a.SLAB_CODE,a.BASE_COMPONENT,a.DEPEND_COMPONENT,a.SLAB_CRITERIA ,a.SALARY_SLAB_ID, b.EMP_CODE ,a.SLAB_CRITERIA_AMT,a.MAX_CRITERIA_AMT,a.MIN_CRITERIA_AMT"
                 + " from hr_slab_master a,hr_personnel_details b ,cbs_ref_rec_mapping c   \n" +
           "where a.DESG_ID= c.GNO and b.DESG_CODE=c.S_GNO  and b.EMP_ID = '"+empId+"'").getResultList();

        return slabCodeList ;
    }
    
    
    
//      @Override
//    public List getDataFromEmployeeId(String empId) throws ApplicationException {
//        List slabCodeList = new ArrayList();     
//         slabCodeList = em.createNativeQuery("select SLAB_CODE,BASE_COMPONENT,DEPEND_COMPONENT,SLAB_CRITERIA ,SALARY_SLAB_ID, b.EMP_CODE from hr_slab_master a,hr_personnel_details b "
//                + "where a.DESG_ID= b.DESG_CODE  and b.EMP_ID = '"+empId+"'").getResultList();
//
//        return slabCodeList;
//    }
    
 
    
    @Override
    public List<EmpAllocationGridPojo> getDataFromSlabNumber(List finalSelectedSlabCode) throws ApplicationException {
        List<EmpAllocationGridPojo> empSalAllocPojo = new ArrayList<EmpAllocationGridPojo>();
        EmpAllocationGridPojo p = new EmpAllocationGridPojo();
        for (int i = 0; i < finalSelectedSlabCode.size(); i++) {
            List slabCodeDetails = em.createNativeQuery("select COMP_CODE, BASE_COMPONENT,DEPEND_COMPONENT,SALARY_SLAB_ID,SLAB_CRITERIA,COMPONENT_ORDER from hr_slab_master where SLAB_CODE='" + finalSelectedSlabCode.get(0).toString() + "';").getResultList();
            p.setCompCode(((Object[]) slabCodeDetails.get(i))[0].toString());    
            empSalAllocPojo.add(p);
        }
        return empSalAllocPojo;
    }

    
    
    @Override
    public String getRangeOfBasicSalary(String salSlabId, String basicSal) throws ApplicationException {
         List rangeAmount = em.createNativeQuery("select salary_from_amt,salary_to_amt from hr_basic_salary_structure where salary_slab_id='" + salSlabId + "'").getResultList();
            String salaryFromAmt = ((Object[]) rangeAmount.get(0))[0].toString();
            String salaryToAmt = ((Object[]) rangeAmount.get(0))[1].toString();
            float basicSalary = Float.parseFloat(basicSal);
            Float sfa = Float.parseFloat(salaryFromAmt);
            Float sta = Float.parseFloat(salaryToAmt);
            if (basicSalary <= sfa || basicSalary >= sta) {
                return "Invalid Basic Salary";
            }
            return "";
    }

    
    @Override
    public String saveSalAllocationDetails(SalaryAllocationTO salAllocTo, List slabNumberList) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        int flag = 0;
        int employeeCode = Integer.parseInt(salAllocTo.getEmpCode());
        int compCode = salAllocTo.getCompanyCode();
        String basicSalary = salAllocTo.getBasicSalary();
        String allocationDate = salAllocTo.getAllocationDate();
        char statFlag = salAllocTo.getStatFlag();
        char statUpFlag = salAllocTo.getStatUpFlag();
        String authBy = salAllocTo.getAuthBy();
        String modDate = salAllocTo.getModificationDate();
        String enterBy = salAllocTo.getEnteredBy();
        try {
            ut.begin();
            for (int i = 0; i < slabNumberList.size(); i++) {
                Query insertQuery = em.createNativeQuery("Insert into hr_salary_allocation(EMP_CODE,COMP_CODE,SLAB_CODE,BASIC_SALARY,ALLOCATION_DATE,STAT_FLAG,STAT_UP_FLAG,AUTH_BY,MOD_DATE,ENTERED_BY) "
                        + "VALUES"
                        + "('" + employeeCode + "','" + compCode + "','" + slabNumberList.get(i) + "','" + basicSalary + "','" + allocationDate + "',"
                        + "'" + statFlag + "','" + statUpFlag + "','" + authBy + "','" + modDate + "','" + enterBy + "')");
                flag = insertQuery.executeUpdate();
                if(flag < 0) {
                    return "Problem in Insertion";
                }
            }
            ut.commit();
            return "Data is saved successfully";
        } catch (Exception ex) {
            try {
                ut.rollback();
                return "already in database";
            } catch (Exception e) {
                   throw new ApplicationException(e.getMessage());
            }
        }
    }

    @Override
    public List<EmpAllocationGridPojo> getPreviousSalAllocDetails(String empId) throws ApplicationException {
       // UserTransaction ut = context.getUserTransaction();
        List empDetailsFromEmpId = em.createNativeQuery("select COMP_CODE,BASE_BRANCH,EMP_CODE,DESG_CODE from hr_personnel_details where EMP_ID = '" + empId + "'").getResultList();
        String empCode = ((Object[]) empDetailsFromEmpId.get(0))[2].toString();
        String compCode=((Object[])empDetailsFromEmpId.get(0))[0].toString();
        List allocatedSlabsDeatils = em.createNativeQuery("select EMP_CODE,COMP_CODE,SLAB_CODE,BASIC_SALARY,Date_format(ALLOCATION_DATE,'%e/%m/%Y'),STAT_FLAG,STAT_UP_FLAG,AUTH_BY,MOD_DATE,ENTERED_BY from hr_salary_allocation where EMP_CODE='" + empCode + "' and COMP_CODE='" + compCode + "'").getResultList();
        List<EmpAllocationGridPojo> empAllocationGridPojo = new ArrayList<>();
        EmpAllocationGridPojo empAllocGridPojo;
        for (int i = 0; i < allocatedSlabsDeatils.size(); i++) {
            empAllocGridPojo = new EmpAllocationGridPojo();
            empAllocGridPojo.setEmpCode(((Object[]) allocatedSlabsDeatils.get(i))[0].toString());
            empAllocGridPojo.setCompCode(((Object[]) allocatedSlabsDeatils.get(i))[1].toString());
            empAllocGridPojo.setSlabCode(((Object[]) allocatedSlabsDeatils.get(i))[2].toString());
            empAllocGridPojo.setBasicSalary(((Object[]) allocatedSlabsDeatils.get(i))[3].toString());
            empAllocGridPojo.setAllocationDate(((Object[]) allocatedSlabsDeatils.get(i))[4].toString());
            empAllocGridPojo.setStatFlag(((Object[]) allocatedSlabsDeatils.get(i))[5].toString());
            empAllocGridPojo.setStatUpFlag(((Object[]) allocatedSlabsDeatils.get(i))[6].toString());
            empAllocGridPojo.setAuthBy(((Object[]) allocatedSlabsDeatils.get(i))[7].toString());
            empAllocGridPojo.setModDate(((Object[]) allocatedSlabsDeatils.get(i))[8].toString());
            empAllocGridPojo.setEnterBy(((Object[]) allocatedSlabsDeatils.get(i))[9].toString());
            empAllocationGridPojo.add(empAllocGridPojo);
        }
        return empAllocationGridPojo;
    }

    @Override
    public String saveSalAllocationDetailsInHis(List<EmpAllocationGridPojo> list,String modifiedDate,String User) throws ApplicationException {
            UserTransaction ut = context.getUserTransaction();
            SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy/MM/dd");
            int flag = 0;
            try {
                ut.begin();
                for (int i = 0; i < list.size(); i++) {
                    Query insertQuery = em.createNativeQuery("Insert into hr_salary_allocation_his(EMP_CODE,COMP_CODE,SLAB_CODE,BASIC_SALARY,ALLOCATION_DATE,STAT_FLAG,STAT_UP_FLAG,AUTH_BY,MOD_DATE,ENTERED_BY) "
                            + "VALUES"
                            + "('" + list.get(i).getEmpCode() + "','" + list.get(i).getCompCode() + "','" + list.get(i).getSlabCode() + "','" + list.get(i).getBasicSalary() + "','" + ymdFormat.format(dmyFormat.parse(list.get(i).getAllocationDate())) + "',"
                            + "'" + list.get(i).getStatFlag() + "','" + list.get(i).getStatUpFlag() + "','" + list.get(i).getAuthBy() + "','" + ymdFormat.format(dmyFormat.parse(modifiedDate)) + "','" +User + "')");
                    flag = insertQuery.executeUpdate();
                    if (flag < 0) {
                        return "Problem in Insertion";
                    }

                }
                ut.commit();
                return "Data is saved successfully";
            } catch (Exception ex) {
                try {
                    ut.rollback();
                    return "Already In Database";
                } catch (Exception e) {
                    throw new ApplicationException(ex.getMessage());
                }
            }     
    }

    @Override
    public String deleteOldSalAllocationDetails(List<EmpAllocationGridPojo> list) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy/MM/dd");
        int flag = 0;
        try {
            ut.begin();
            for (int i = 0; i < list.size(); i++) {
                Query deleteQuery = em.createNativeQuery("delete from  hr_salary_allocation where EMP_CODE='" + list.get(i).getEmpCode() + "' and COMP_CODE='" + list.get(i).getCompCode() + "' and SLAB_CODE='" + list.get(i).getSlabCode() + "'; ");

                flag = deleteQuery.executeUpdate();
                if (flag < 0) {
                    return "Problem in Insertion";
                }

            }
           ut.commit();
            return "Data is saved successfully";
        } catch (Exception ex) {
            try {              
              ut.rollback();
            } catch (Exception e) {
               throw new ApplicationException(ex.getMessage());
            }
        }
        return "";
    }
    
    
    @Override
    public List<HrPersonnelDetails> allEmployeeList() throws ApplicationException {
   
         List<HrPersonnelDetails> allEmpIdAndName = new ArrayList<HrPersonnelDetails>();
        // List empList = em.createNativeQuery("select EMP_ID ,EMP_NAME,EMP_CODE from hr_personnel_details where WORK_STATUS IN('STA001','STA002','STA003')").getResultList();
      
         List empList = em.createNativeQuery("select EMP_ID ,EMP_NAME,EMP_CODE from hr_personnel_details where WORK_STATUS IN('STA001')").getResultList();
         if (!empList.isEmpty()) {
                for (Object obj : empList) {
                Object[] ele = (Object[]) obj;  
                HrPersonnelDetails hpd = new HrPersonnelDetails();
                HrPersonnelDetailsPK hpdPK = new HrPersonnelDetailsPK();
                hpd.setEmpId(ele[0].toString());
                hpd.setEmpName(ele[1].toString());
                hpdPK.setEmpCode(BigInteger.valueOf(Long.decode(ele[2].toString())));
                hpd.setHrPersonnelDetailsPK(hpdPK);
                allEmpIdAndName.add(hpd);               
                }
         }
        return allEmpIdAndName;
    }
    
    @Override
    public String  maxSlabCode() throws ApplicationException  {
            String maxslabCode="",zero="00000000";
            String maxSlabCodeNum ="";
          List slabCodeList = em.createNativeQuery("select cast(substring(max(SLAB_CODE),4)+1 as unsigned) from hr_slab_master").getResultList();
          if(!slabCodeList.isEmpty()){           
             maxSlabCodeNum =slabCodeList.get(0).toString();
          }
          int len = maxSlabCodeNum.length();
          maxslabCode= "SLA"+zero.substring(len)+maxSlabCodeNum;
            return maxslabCode;
    }
    
    @Override
     public List<GPFRegisterPojo> getGpfRegisterData(String month ,String year,String todayDate) throws ApplicationException{
        List<GPFRegisterPojo> gpfregList = new ArrayList<>();
        double basicsalfix = 15000;
        double pffix = 1800;  
        String payableDays ="30.00";       
        SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
        List m = new ArrayList();
        int age = 58;       
      //  List compList = em.createNativeQuery("select distinct  a.BASE_COMPONENT from  hr_slab_master a  where  a.DEPEND_COMPONENT= (select b.AL_DESC from  hr_salary_structure b where b.PURPOSE_CODE='PUR002' and b.DESC_SHORT_CODE='1')").getResultList();
        List<HrPersonnelDetails> empList = allEmployeeList();    
       for(int i=0;i<empList.size();i++){    
           
          List salDtl = em.createNativeQuery("select ifnull(sum(AMOUNT),0) from hr_salary_processing_dtl where EMP_CODE = '"+empList.get(i).getHrPersonnelDetailsPK().getEmpCode()+"' and date_format(MONTH_START_DATE,'%Y')='"+year+"' and MONTH = '"+month+"'\n" +
            "and COMPONENT_TYPE in(select distinct BASE_COMPONENT from hr_slab_master where DEPEND_COMPONENT =  \n" +
            "(select d.AL_DESC from  hr_salary_structure d where d.PURPOSE_CODE='PUR002' \n" +
            "and d.DESC_SHORT_CODE='1') )").getResultList();
          List pfDtl = em.createNativeQuery("select s.AMOUNT,p.EMP_CODE,p.EMP_NAME,Date_format(p.BIRTH_DATE,\"%d/%m/%Y\")  from hr_personnel_details p,hr_salary_processing_dtl s where s.COMPONENT_TYPE = 'PF' and s.EMP_CODE='"+empList.get(i).getHrPersonnelDetailsPK().getEmpCode()+"' and s.EMP_CODE=p.EMP_CODE and s.PURPOSE_CODE ='PUR002' and s.MONTH='"+month+"'  and  DATE_FORMAT(s.MONTH_START_DATE,'%Y')='"+year+"' ").getResultList();
                GPFRegisterPojo  grPojo = new GPFRegisterPojo();
                if(Double.valueOf(salDtl.get(0).toString())>basicsalfix){
                grPojo.setSalary(basicsalfix); 
            }else{
                grPojo.setSalary(Double.valueOf(salDtl.get(0).toString())); 
            }
           if(!pfDtl.isEmpty()){
            for (Object obj : pfDtl) {  
              Object[] ele = (Object[]) obj;
              grPojo.setGpfCode(ele[1].toString());
              grPojo.setDayspayable(payableDays);
              grPojo.setEmployeeName(ele[2].toString());
              
            try {
                m  = CbsUtil.getYrMonDayDiff(ymd.format(dmy.parse(ele[3].toString())), ymd.format(dmy.parse(todayDate)));
             } catch (ParseException ex) {
                Logger.getLogger(PayrollOtherMgmFacade.class.getName()).log(Level.SEVERE, null, ex);
             }
              if(Integer.parseInt(m.get(0).toString())<age){
                  if(Double.valueOf(ele[0].toString())>pffix){
                   grPojo.setGpf(pffix);
                   Long gpfAmt = Math.round(pffix * 0.3061);
                   grPojo.setGpfEmpContribution(gpfAmt);
                   Long pensionAmt = Math.round(pffix * 0.6939);
                   grPojo.setPension(Double.valueOf(String.valueOf(pensionAmt)));
                  }else{
                   grPojo.setGpf(Double.valueOf(ele[0].toString()));
                   Long gpfAmt = Math.round(Double.valueOf(ele[0].toString()) * 0.3061);
                   grPojo.setGpfEmpContribution(gpfAmt);
                   Long pensionAmt = Math.round(Double.valueOf(ele[0].toString()) * 0.6939);
                   grPojo.setPension(Double.valueOf(String.valueOf(pensionAmt)));  
          
                  }
              }else{
                   if(Double.valueOf(ele[0].toString())>pffix){
                   grPojo.setGpf(pffix);             
                   grPojo.setGpfEmpContribution(pffix);               
                   grPojo.setPension(Double.valueOf("0"));
                  }else{
                   grPojo.setGpf(Double.valueOf(ele[0].toString()));             
                   grPojo.setGpfEmpContribution(Double.valueOf(ele[0].toString()));             
                   grPojo.setPension(Double.valueOf("0"));  
                  }
              }
            }
      }

            gpfregList.add(grPojo);
       }
        return gpfregList;
    }
    
     @Override
     public List<BonusChecklistPojo> getBonusCheckListData(String branchName,String fromDate,String toDate)throws ApplicationException {
      try {
         List<BonusChecklistPojo> bonusChecklistData = new ArrayList();
         
         List<BonusChecklistPojo> empListData = new ArrayList();
            List monthList = new ArrayList();
            HashMap salMap = new HashMap();
            monthList.add("APRIL");
            monthList.add("MAY");
            monthList.add("JUNE");
            monthList.add("JULY");
            monthList.add("AUGUST");
            monthList.add("SEPTEMBER");
            monthList.add("OCTOBER");
            monthList.add("NOVEMBER");
            monthList.add("DECEMBER");
            monthList.add("JANUARY");
            monthList.add("FEBRUARY");
            monthList.add("MARCH");
            
            String year1 =fromDate.substring(0, 4);
            String year2 =toDate.substring(0,4);
            List bcList = new ArrayList();
            List empDetailsList = em.createNativeQuery("select distinct p.EMP_CODE,p.EMP_NAME,p.EMP_ID from  hr_personnel_details p ,bnkadd b where b.branchcode= p.BASE_BRANCH and b.branchcode='"+branchName+"'").getResultList();
             if(!empDetailsList.isEmpty()){          
             for (Object obj : empDetailsList) {
                    Object[] ele = (Object[]) obj;
                    BonusChecklistPojo bcPojo = new BonusChecklistPojo();
                    bcPojo.setEmpCode(ele[0].toString());
                    bcPojo.setEmpName(ele[1].toString());
                    bcPojo.setEmpId(ele[2].toString());
                    empListData.add(bcPojo);                
             }}
   
         for(int i=0;i<empListData.size();i++){   
              BonusChecklistPojo bcAllPojo = new BonusChecklistPojo();
              double amnt = 0.0;
             for(int j=0;j<monthList.size();j++){
                bcList =em.createNativeQuery("select ifnull(sum(AMOUNT),0)  from hr_salary_processing_dtl where EMP_CODE='"+empListData.get(i).getEmpCode()+"' and COMPONENT_TYPE in ('BASIC','CASHIER','DA','DAFTARY','DRIVER','IT ALLOW','SPL','SPL ASST','HEAD CASHIER' , 'IT ALLO1','SPECIAL ASSITANT') and month ='"+monthList.get(j).toString()+"' and Date_Format(MONTH_START_DATE,'%Y%m%d') between '"+fromDate+"' and '"+toDate+"' ").getResultList();

                bcAllPojo.setBonusCompTotal(Math.round(Double.valueOf(bcList.get(0).toString())));
                if(monthList.get(j).toString().equalsIgnoreCase("APRIL")){
                    bcAllPojo.setAprilAmt(Math.round(Double.valueOf(bcList.get(0).toString())));
                }else if(monthList.get(j).toString().equalsIgnoreCase("MAY")){         
                    bcAllPojo.setMayAmt(Math.round(Double.valueOf(bcList.get(0).toString())));
                }else if(monthList.get(j).toString().equalsIgnoreCase("JUNE")){
                    bcAllPojo.setJuneAmt(Math.round(Double.valueOf(bcList.get(0).toString())));
                }else if(monthList.get(j).toString().equalsIgnoreCase("JULY")){
                    bcAllPojo.setJulyAmt(Math.round(Double.valueOf(bcList.get(0).toString())));
                }else if(monthList.get(j).toString().equalsIgnoreCase("AUGUST")){
                    bcAllPojo.setAugustAmt(Math.round(Double.valueOf(bcList.get(0).toString())));
                }else if(monthList.get(j).toString().equalsIgnoreCase("SEPTEMBER")){
                    bcAllPojo.setSeptemberAmt(Math.round(Double.valueOf(bcList.get(0).toString())));
                }else if(monthList.get(j).toString().equalsIgnoreCase("OCTOBER")){
                    bcAllPojo.setOctoberAmt(Math.round(Double.valueOf(bcList.get(0).toString())));
                }else if(monthList.get(j).toString().equalsIgnoreCase("NOVEMBER")){
                    bcAllPojo.setNovemberAmt(Math.round(Double.valueOf(bcList.get(0).toString())));
                }else if(monthList.get(j).toString().equalsIgnoreCase("DECEMBER")){
                    bcAllPojo.setDecemberAmt(Math.round(Double.valueOf(bcList.get(0).toString())));
                }else if(monthList.get(j).toString().equalsIgnoreCase("JANUARY")){
                    bcAllPojo.setJanuaryAmt(Math.round(Double.valueOf(bcList.get(0).toString())));
                }else if(monthList.get(j).toString().equalsIgnoreCase("FEBRUARY")){
                    bcAllPojo.setFebruaryAmt(Math.round(Double.valueOf(bcList.get(0).toString())));
                }else if(monthList.get(j).toString().equalsIgnoreCase("MARCH")){
                    bcAllPojo.setMarchAmt(Math.round(Double.valueOf(bcList.get(0).toString())));
                }
                if(salMap.containsKey(empListData.get(i).getEmpCode())){
                     double amt=0.0;
                   amt= Math.round((Double)salMap.get(empListData.get(i).getEmpCode()))+Math.round(Double.valueOf(bcList.get(0).toString()));
                   salMap.put(empListData.get(i).getEmpCode(), amt);
                }else{
                   salMap.put(empListData.get(i).getEmpCode(),(Double.valueOf(bcList.get(0).toString())));
                }
              
             }
                bcAllPojo.setEmpCode(empListData.get(i).getEmpCode());
                bcAllPojo.setEmpName(empListData.get(i).getEmpName());
                bcAllPojo.setEmpId(empListData.get(i).getEmpId());           
                  if(salMap.containsKey(empListData.get(i).getEmpCode())){   
                    bcAllPojo.setTotalSalary((Double)salMap.get(empListData.get(i).getEmpCode()));
                    // Bonus would be 15% of sum of bonus Components 
                    amnt = ((Double)salMap.get(empListData.get(i).getEmpCode()))*.15 ;
                      bcAllPojo.setGrossBonus(Math.round(amnt));                
                 }
                 bonusChecklistData.add(bcAllPojo);
         }
         salMap.clear();
        return bonusChecklistData;
       } catch(Exception e){
           throw new  ApplicationException(e.getMessage());
       }
     }
   
   @Override
   public List<SalaryRegisterPojo> getMonthlySalRegisterData(String category,String branch,String month,String year) throws ApplicationException  {
      List<SalaryRegisterPojo> salRegPojoList = new ArrayList<SalaryRegisterPojo>();     
      String branchName="";
      if(!category.equalsIgnoreCase("ALL")){
          branchName = "and b.BrnCode='"+branch+"'";
      }else{
          branchName = "";
      }   
    
      try{    
        double compDefaultAmt = 0.00;
        List earningCompList = new ArrayList();
        List empComponentList = new ArrayList();
        List empDtl = new ArrayList();
        List empListData = new ArrayList();       
        int count=0;
        List empDetailsList = em.createNativeQuery("select distinct p.EMP_CODE from  hr_personnel_details p ,branchmaster b where b.BrnCode= p.BASE_BRANCH "+branchName+"").getResultList();
         if(!empDetailsList.isEmpty()){                        
           for(int i=0;i<empDetailsList.size();i++){                   
            empListData.add(empDetailsList.get(i).toString());                   
         }}
        
        List earningComp= em.createNativeQuery("select distinct COMPONENT_TYPE from hr_salary_processing_dtl where PURPOSE_CODE in ('PUR001','PUR002','PUR003')\n" +
         "and component_type  in (component_type like '%INC%' or component_type like '%LIC%' or component_type like '%ITAX%')").getResultList();
         if(!earningComp.isEmpty()){
             for(int a=0;a<earningComp.size();a++){
                  if(!(earningComp.get(a).toString().contains("LIC")) && !(earningComp.get(a).toString().contains("INC")) ){
                    earningCompList.add(earningComp.get(a).toString());  
                    count++;
                  }
             } 
             earningCompList.add(new String("LIC"));
             earningCompList.add(new String("INC"));
             count = count + 2;
         }
         
         for(int i=0;i<empListData.size();i++){           
            empDtl = em.createNativeQuery("select hp.EMP_ID,hp.EMP_NAME,hsp.SALARY from hr_personnel_details hp ,hr_salary_processing hsp\n" +
            "where hp.EMP_CODE= hsp.EMP_CODE and hp.EMP_CODE='"+empListData.get(i).toString()+"' and hsp.Months ='"+month+"' and Date_format(hsp.MOD_DATE,'%Y')="+year+"").getResultList();
              if(!empDtl.isEmpty()){          
             for(Object obj : empDtl) { 
              Object[] ele1 = (Object[]) obj;
            }}
           
         List empCompList = em.createNativeQuery("select  a.COMPONENT_TYPE,ifnull(a.AMOUNT,0) ,a.PURPOSE_CODE \n" +
         "from hr_salary_processing_dtl a ,hr_personnel_details b,hr_salary_processing c where a.EMP_CODE='"+empListData.get(i).toString()+"'"+
         "and a.PURPOSE_CODE in('PUR001','PUR002','PUR003') and a.MONTH='"+month+"'\n" +
         "and a.MONTH = c.MONTHS and a.EMP_CODE=b.EMP_CODE  and a.EMP_CODE=c.EMP_CODE and DATE_FORMAT(a.MONTH_START_DATE,'%Y')='"+year+"'\n" +
         "and DATE_FORMAT(a.MONTH_START_DATE,'%Y')=DATE_FORMAT(c.MOD_DATE,'%Y')\n" +
         "order By COMPONENT_TYPE").getResultList();
          for (Object obj : empCompList) {
             Object[] ele = (Object[]) obj;
             empComponentList.add(ele[0].toString());
          }
            
           for (Object obj : empCompList) {
                SalaryRegisterPojo srPojo = new SalaryRegisterPojo();
              for(Object obj1 : empDtl) { 
              Object[] ele1 = (Object[]) obj1;
                srPojo.setEmpCode(empListData.get(i).toString());
                srPojo.setEmpId(ele1[0].toString());
                srPojo.setEmpName(ele1[1].toString());                                                     
                srPojo.setNetpayAmt(Double.valueOf(ele1[2].toString()));
                srPojo.setCompCount(count);
              }
              Object[] ele = (Object[]) obj;
      
              if(empComponentList.contains(ele[0].toString())){
               if(ele[2].toString().equalsIgnoreCase("PUR001")){
                srPojo.setComponentName(ele[0].toString());
                srPojo.setComponentAmount(Double.valueOf(ele[1].toString()));
                srPojo.setPurposeCode(ele[2].toString());
                srPojo.setPurposeType("1.E");               
               }else if(ele[2].toString().equalsIgnoreCase("PUR002") || ele[2].toString().equalsIgnoreCase("PUR003")){
              if(ele[0].toString().contains("ITAX") ||ele[0].toString().contains("INCOMETAX_") || ele[0].toString().contains("INCOME TAX") || ele[0].toString().contains("INCTAX_")){
                srPojo.setComponentName("INCOME TAX");
                srPojo.setComponentAmount(0.00-Double.valueOf(ele[1].toString()));
                srPojo.setPurposeCode(ele[2].toString());
                srPojo.setPurposeType("2.D");  
                
             }else if(ele[0].toString().contains("LIC") || ele[0].toString().contains("LIC_")){
                srPojo.setComponentName("LIC");
                srPojo.setComponentAmount(0.00-Double.valueOf(ele[1].toString()));
                srPojo.setPurposeCode(ele[2].toString());
                srPojo.setPurposeType("2.D");    
              
             }else{
                srPojo.setComponentName(ele[0].toString());
                srPojo.setComponentAmount(0.00-Double.valueOf(ele[1].toString()));
                srPojo.setPurposeCode(ele[2].toString());
                srPojo.setPurposeType("2.D");              
                }            
             }}else{
               if(ele[2].toString().equalsIgnoreCase("PUR001")){
                srPojo.setComponentName(ele[0].toString());
                srPojo.setComponentAmount(compDefaultAmt);
                srPojo.setPurposeCode(ele[2].toString());
                srPojo.setPurposeType("1.E");  
             } else if(ele[2].toString().equalsIgnoreCase("PUR002") || ele[2].toString().equalsIgnoreCase("PUR003")){
                srPojo.setComponentName(ele[0].toString());
                srPojo.setComponentAmount(compDefaultAmt);
                srPojo.setPurposeCode(ele[2].toString());
                srPojo.setPurposeType("2.D");   
             }}  
              salRegPojoList.add(srPojo);              
         }
          
         }
     }catch(Exception e){
         new ApplicationException(e.getMessage());
     }
        return  salRegPojoList;
 }
   
   public List<EmpSalaryAllocationGridTO> getNewSalaryBreakUp(int compCode, String empCd, String purposeCode) throws ApplicationException {
       
      
     List<EmpSalaryAllocationGridTO> list1 = new ArrayList();
     List<EmpSalaryAllocationGridTO> list = getSalaryComponent(compCode, empCd, purposeCode );
     
     List listofCompNonbasic  = new ArrayList();
     List listOfCompNonBasicDep = new ArrayList();
    
     Map<String,List<String>> map = new HashMap<String,List<String>>();
     Map<String,List<String>> restMap = new HashMap<String,List<String>>();
     Map<String,Boolean> EnableComp = new HashMap<String,Boolean>();
     Map<String,Double> amountMap = new HashMap<String,Double>();
     amountMap.put("BASIC",list.get(0).getBasicSalary());
    

    for(EmpSalaryAllocationGridTO p11 :list){
        if(!(p11.getBaseComponent().equalsIgnoreCase("BASIC"))){
            if(!listofCompNonbasic.contains(p11.getBaseComponent())){
            listofCompNonbasic.add(p11.getBaseComponent());
            }
        }                                                                                                                                                                                                  
    }
 
    for(Object s : listofCompNonbasic){
        String str = s.toString();
        List<String> dependencyList = new ArrayList();
        for(EmpSalaryAllocationGridTO p12: list){            
          if(p12.getDependentComponent().toString().equalsIgnoreCase(str)){
             dependencyList.add(p12.getBaseComponent()) ;
          }                                         
        }
          map.put(str,dependencyList); 
    }
    
    for(EmpSalaryAllocationGridTO p12 : list){
        if(!listofCompNonbasic.contains(p12.getDependentComponent())){
            listOfCompNonBasicDep.add(p12.getDependentComponent());
        }
    }
 
    for(Object s : listOfCompNonBasicDep){
        String str = s.toString();
        List<String> dependencyList = new ArrayList();
        for(EmpSalaryAllocationGridTO p12: list){    
          if(p12.getDependentComponent().toString().equalsIgnoreCase(str)){
             dependencyList.add(p12.getBaseComponent()) ;
          }  
        }
          map.put(str,dependencyList); 
     }

     List<String> calList = new ArrayList();
        for(EmpSalaryAllocationGridTO p13 : list){
            if(!calList.contains(p13.getBaseComponent())){
                calList.add(p13.getBaseComponent());
            }
            if(!calList.contains(p13.getDependentComponent())){
                calList.add(p13.getDependentComponent());
            }
        }
   
          for(Object o :calList){
          if(o.toString().equalsIgnoreCase("BASIC")){
              EnableComp.put(o.toString(),true);
          }else{
              EnableComp.put(o.toString(),false);
          }
          }
 
       while((map.size())>0 || (restMap.size())>0){            
        for (Map.Entry<String,List<String>> entry : map.entrySet()) {
          String dependComp = entry.getKey().toString();
          List<String> list3 = new ArrayList<String>();
             list3 = entry.getValue();
          boolean  b = mapDecider(list3, EnableComp) ;
            if(b){
               slabCalculation(dependComp,list3,amountMap,EnableComp,list);             
            }else{
               restMap.put(dependComp,list3);
           }         
          }
           map.clear();
         if(restMap.isEmpty()){
             break;
         }else{
             for(Map.Entry<String,List<String>> entry : restMap.entrySet()){
                   map.put(entry.getKey(),entry.getValue()); 
              }
           restMap.clear();
         }         
       }
          
 
     for(String strr :calList){
         
         EmpSalaryAllocationGridTO esag = new EmpSalaryAllocationGridTO();
         esag.setComponent(strr);
         esag.setAmount(amountMap.get(strr));
         for(EmpSalaryAllocationGridTO empSalAlloc : list ){
             int counter = 0;
             
             if(empSalAlloc.getDependentComponent().equalsIgnoreCase(strr)){
                 counter++;
               esag.setGlCode(empSalAlloc.getGlCode());
               esag.setCompOrder(empSalAlloc.getCompOrder());
               esag.setCompCode(empSalAlloc.getCompCode());
               esag.setShCode(empSalAlloc.getShCode());
               esag.setPurposeCode(empSalAlloc.getPurposeCode());               
             }
             if(counter >0){
                 break;
             }
         }
         list1.add(esag);
     }
    
      return list1;
   }
   
    public boolean mapDecider (List<String> str3 , Map<String,Boolean> EnableComp){
        for(String com : str3){
            if(!EnableComp.get(com)){
                return false;
            }  
        }
        return true;
    }
   
    public void slabCalculation(String str1,List<String> str2,Map<String,Double> amountMap,Map<String,Boolean> EnableComp,List<EmpSalaryAllocationGridTO> list){
           
          for(String st3 : str2) {
              double compAmt= 0.0;
           for(EmpSalaryAllocationGridTO p14 :list){
               if(p14.getBaseComponent().equalsIgnoreCase(st3) && p14.getDependentComponent().equalsIgnoreCase(str1)){
                   if(p14.getSlabCriteria().equalsIgnoreCase("PERCENTAGE")){
                       Double dvar = (Double) ((amountMap.get(st3))* p14.getSlabCriteriaAmt() *(.01)) ;
                       if(dvar >= p14.getSlabCriteriaMaxAmt() && p14.getSlabCriteriaMaxAmt() != 0.0){
                          dvar = p14.getSlabCriteriaMaxAmt();
                           if(!amountMap.containsKey(str1)){
                           compAmt = Double.valueOf(dvar); 
                           }else if(amountMap.containsKey(str1) && Double.valueOf(amountMap.get(str1).toString()) > 0.0){
                           compAmt = Double.valueOf(amountMap.get(str1).toString()) + dvar; 
                       }
                       } else if(dvar <= p14.getSlabCriteriaMinAmt() && p14.getSlabCriteriaMinAmt() != 0.0)  {
                            dvar = p14.getSlabCriteriaMinAmt();
                         if(!amountMap.containsKey(str1)){
                           compAmt = Double.valueOf(dvar);
                         } else  if(amountMap.containsKey(str1) && Double.valueOf(amountMap.get(str1).toString()) > 0.0){
                           compAmt = Double.valueOf(amountMap.get(str1).toString()) + dvar; 
                       }
                    }else{
                             dvar = dvar;
                         if(!amountMap.containsKey(str1)){
                              compAmt = Double.valueOf(dvar);
                         }else if(amountMap.containsKey(str1) && Double.valueOf(amountMap.get(str1).toString()) > 0.0){
                              compAmt =(Double)( Double.valueOf(amountMap.get(str1).toString()) + dvar); 
                       }
                       }
                       amountMap.put(str1,compAmt);
                        if(EnableComp.containsKey(str1) && EnableComp.get(str1) != true){
                           EnableComp.put(str1,true);
                       }
                       
                   }else if(p14.getSlabCriteria().equalsIgnoreCase("AMOUNT")){
                       Double dvar = p14.getSlabCriteriaAmt();
                       if(dvar >= p14.getSlabCriteriaMaxAmt() && p14.getSlabCriteriaMaxAmt()!= 0.0){
                           dvar = p14.getSlabCriteriaMaxAmt();
                         if(!amountMap.containsKey(str1)){
                             compAmt = Double.valueOf(dvar);
                         }else if(amountMap.containsKey(str1) && Double.valueOf(amountMap.get(str1).toString()) > 0.0){
                             compAmt = Double.valueOf(amountMap.get(str1).toString()) + dvar; 
                       }
                       } else if(dvar <= p14.getSlabCriteriaMinAmt() && p14.getSlabCriteriaMinAmt()!= 0.0 )  {
                            dvar = p14.getSlabCriteriaMinAmt();
                            if(!amountMap.containsKey(str1)){
                             compAmt = Double.valueOf(dvar);
                       }else if(amountMap.containsKey(str1) && Double.valueOf(amountMap.get(str1)) > 0.0){
                             compAmt = Double.valueOf(amountMap.get(str1).toString()) + dvar; 
                       }
                       }else{
                            dvar = dvar;
                           if(!amountMap.containsKey(str1)){
                             compAmt = Double.valueOf(dvar);
                       }else if(amountMap.containsKey(str1) && Double.valueOf(amountMap.get(str1).toString()) > 0.0){
                             compAmt = Double.valueOf(amountMap.get(str1).toString()) + dvar; 
                       }
                       }
                       amountMap.put(str1,compAmt);
                       if(EnableComp.containsKey(str1) && EnableComp.get(str1) != true ){
                          EnableComp.put(str1,true);
                       }
                   }
               }
           }
          }
          
          if(amountMap.containsKey(str1)){
              String compName = str1;
              Double compAmt = (Double) amountMap.get(str1);
              int count = 0;             
              for(EmpSalaryAllocationGridTO p136 :list){
                     if(count>0){
                      break;
                       }
                  if(p136.getDependentComponent().equalsIgnoreCase(compName)){
                      if(compAmt>= p136.getSlabCriteriaMaxAmt() && p136.getSlabCriteriaMaxAmt()!= 0.0){
                          compAmt= p136.getSlabCriteriaMaxAmt();                         
                      }else if(compAmt <= p136.getSlabCriteriaMinAmt() && p136.getSlabCriteriaMinAmt()!= 0.0){
                          compAmt= p136.getSlabCriteriaMinAmt();
                      }else{
                          compAmt = compAmt;
                      }
                       count++;
                  }
              amountMap.put(compName,compAmt);              
              }             
          }
    }
    
    @Override
    public String getEmpBranchCode(String EmpId) throws ApplicationException {
        String branchCode ="";
        try{            
           Object branchCodeObj = em.createNativeQuery("select BASE_BRANCH from hr_personnel_details WHERE emp_id = '"+EmpId+"'").getSingleResult();
             branchCode = branchCodeObj.toString();
        }catch(Exception ex){
            throw new ApplicationException(ex.getMessage());
        }
        
       return branchCode;
    }
    
    
      
   public List<String> getPFBaseComponent(BigInteger empcode) throws ApplicationException {
       List<String> pfBaseCompList = new ArrayList<String>();
       pfBaseCompList = em.createNativeQuery("select BASE_COMPONENT from  hr_slab_master where slab_code in (select SLAB_CODE"
               + " from hr_salary_allocation where emp_code = '"+empcode+"' \n" 
               +  "and DEPEND_COMPONENT=(select AL_DESC from hr_salary_structure"
               + " where PURPOSE_CODE = 'PUR002' and DESC_SHORT_CODE=1))").getResultList();
      
       return pfBaseCompList;
  }
    
   
   
   public List<consolidatedMsrPojo> getConsolidatedMsrData(String month,String year) throws ApplicationException{
       List<consolidatedMsrPojo> dataList = new ArrayList();
       List<String> compList = new ArrayList();  
       List<Integer> branchCodeList = new ArrayList();
        double compDefaultAmt = 0.00;
       Map<Integer,String> branchMap = new HashMap<Integer,String>();
        int count=0;
       List componentList  = em.createNativeQuery("select distinct COMPONENT_TYPE from hr_salary_processing_dtl where PURPOSE_CODE in ('PUR001','PUR002','PUR003') and Date_format(MONTH_START_DATE,'%Y') = '"+year+"'\n" +
       "and MONTH ='"+month+"'\n" +
       "and component_type  in (component_type like '%INC%' or component_type like '%LIC%' or component_type like '%ITAX%') order by COMPONENT_TYPE  ").getResultList();
         if(!componentList.isEmpty()){
             for(int a=0;a<componentList.size();a++){
                  if(!(componentList.get(a).toString().contains("LIC")) && !(componentList.get(a).toString().contains("INC")) ){
                    compList.add(componentList.get(a).toString());  
                    count++;
                  }
             } 
             compList.add(new String("LIC"));
             compList.add(new String("INC"));
             count = count + 2;
         }
       
       List branchList = em.createNativeQuery("select distinct a.BrnCode, AlphaCode from branchmaster a,hr_personnel_details b where a.BrnCode =b.BASE_BRANCH").getResultList();
        if(!branchList.isEmpty()){
             for (Object obj : branchList) {
             Object[] ele = (Object[]) obj;
             branchMap.put(Integer.parseInt(ele[0].toString()),ele[1].toString());
             branchCodeList.add(Integer.parseInt(ele[0].toString()));       
          }
        }       
               
       for(int i=0;i<branchCodeList.size();i++){
           for(int j=0;j<compList.size();j++){
           
            List amountSum = em.createNativeQuery("  \n" +
            " select ifnull(COMPONENT_TYPE,''),ifnull(sum(AMOUNT),0.0),ifnull(PURPOSE_CODE,'') from hr_salary_processing_dtl  where COMPONENT_TYPE like '%"+compList.get(j).toString()+"%' \n" +
            " and MONTH = '"+month+"' and Date_format(MONTH_START_DATE,'%Y')='"+year+"'\n" +
            "and EMP_CODE in (select EMP_CODE from hr_personnel_details where cast(BASE_BRANCH as unsigned ) = "+Integer.parseInt(branchCodeList.get(i).toString())+" ) ").getResultList();

               if(!amountSum.isEmpty()){                
                  consolidatedMsrPojo  cmPojo = new consolidatedMsrPojo();   
                  cmPojo.setBranch(branchMap.get(Integer.parseInt(branchCodeList.get(i).toString())));
                  for (Object obj : amountSum) {
                   Object[] ele = (Object[]) obj;                       
           
               if(ele[2].toString().equalsIgnoreCase("PUR001")){
                   if(compList.contains(ele[0].toString())){   
                cmPojo.setComponentName(ele[0].toString());
                cmPojo.setComponentAmount(Double.valueOf(ele[1].toString()));
                cmPojo.setPurposeCode(ele[2].toString()== null?"":ele[2].toString());
                cmPojo.setPurposeType("1.E");               
               }}else if(ele[2].toString().equalsIgnoreCase("PUR002") || ele[2].toString().equalsIgnoreCase("PUR003")){
            
              if(ele[0].toString().contains("INC") || ele[0].toString().contains("ITAX") ||ele[0].toString().contains("INCOMETAX_") || ele[0].toString().contains("INCOME TAX") || ele[0].toString().contains("INCTAX_") ){
                cmPojo.setComponentName("INCOME TAX");
                cmPojo.setComponentAmount(0.00-Double.valueOf(ele[1].toString()));
                cmPojo.setPurposeCode(ele[2].toString()== null?"":ele[2].toString());
                cmPojo.setPurposeType("2.D");  
                
             }else if(ele[0].toString().contains("LIC") || ele[0].toString().contains("LIC_")){
                cmPojo.setComponentName("LIC");
                cmPojo.setComponentAmount(0.00-Double.valueOf(ele[1].toString()));
                cmPojo.setPurposeCode(ele[2].toString()== null?"":ele[2].toString());
                cmPojo.setPurposeType("2.D");  
             }else{
                cmPojo.setComponentName(ele[0].toString());
                cmPojo.setComponentAmount(0.00-Double.valueOf(ele[1].toString()));
                cmPojo.setPurposeCode(ele[2].toString()== null?"":ele[2].toString());
                cmPojo.setPurposeType("2.D");              
                }            
             }else{
                  
               if(ele[2].toString().equalsIgnoreCase("")){
                cmPojo.setComponentName(compList.get(j).toString());
                cmPojo.setComponentAmount(compDefaultAmt);
                cmPojo.setPurposeCode(ele[2].toString()== null?"":ele[2].toString());
                cmPojo.setPurposeType("1.E");  
               } 
                else if(ele[2].toString().equalsIgnoreCase("PUR002") || ele[2].toString().equalsIgnoreCase("PUR003")){
                cmPojo.setComponentName(ele[0].toString());
                cmPojo.setComponentAmount(compDefaultAmt);
                cmPojo.setPurposeCode(ele[2].toString()== null?"":ele[2].toString());
                cmPojo.setPurposeType("2.D");   
           }
          }  
              dataList.add(cmPojo);    
                  
              }
          }
      } 
     }
     return dataList;
   }
    
   
   @Override
  public String getEcrFileData(String path, String month, String year) throws ApplicationException
  {
    BufferedWriter bw = null;
    FileWriter fw = null;
    String fileName = "";
    File file = null;
    List ecrdetail1 = new ArrayList();
    List ecrdetail2 = new ArrayList();
    try {
      fileName = "ECRFile.txt";
      
      file = new File(path + "/" + fileName);
      if (file.exists()) {
        file.delete();
      }
      if (file.createNewFile()) {
        fw = new FileWriter(file.getCanonicalFile());
        bw = new BufferedWriter(fw);
      }
      List empList = em.createNativeQuery("select distinct  hsa.EMP_CODE from hr_salary_allocation hsa ,hr_salary_processing_dtl hspd where hsa.SLAB_CODE in \n(select sm.SLAB_CODE from hr_slab_master sm where sm.DEPEND_COMPONENT =(select a.AL_DESC from hr_salary_structure a\nwhere a.PURPOSE_CODE='PUR002' and a.DESC_SHORT_CODE ='1' )) and hspd.EMP_CODE= hsa.EMP_CODE and MONTH='" + month + "' and \n" + "Date_format(ENTERED_DATE,'%Y')='" + year + "' order by  hsa.EMP_CODE").getResultList();
      
      for (int i = 0; i < empList.size(); i++) {
        System.out.println(i);
        String uanNumber = "";String empName = "";String grossSal = "";String pfDependentCompAmt = "";String pfAmt = "";String deductdays = "";
        String pfAmteptt = "";String pfAmttpss = "";String refunds = "0";
        ecrdetail1 = em.createNativeQuery("select hpd.UAN_NUMBER ,hpd.EMP_NAME ,sum(hrs.AMOUNT) as pfCompAmt ,had.DEDUCT_DAYS   from hr_slab_master sm ,hr_salary_allocation ha, hr_salary_processing_dtl hrs ,\nhr_personnel_details hpd,hr_attendance_details had \nwhere sm.DEPEND_COMPONENT = (select a.AL_DESC from hr_salary_structure a where a.PURPOSE_CODE ='PUR002' and a.DESC_SHORT_CODE='1' ) \nand  sm.slab_code=ha.SLAB_CODE and ha.EMP_CODE='" + empList.get(i).toString() + "' and hrs.EMP_CODE=ha.EMP_CODE and had.EMP_CODE=ha.EMP_CODE  and hpd.EMP_CODE = ha.EMP_CODE" + " and hrs.MONTH = '" + month + "' and DATE_FORMAT(hrs.ENTERED_DATE,'%Y')='" + year + "'\n" + "and sm.BASE_COMPONENT= hrs.COMPONENT_TYPE and  hrs.MONTH = had.ATTEN_MONTH and Date_format(had.MOD_DATE,'%Y')=DATE_FORMAT(hrs.ENTERED_DATE,'%Y')").getResultList();
       double pdca;
        for (Object obj : ecrdetail1) {
          Object[] ele = (Object[])obj;
          uanNumber = ele[0].toString();
          empName = ele[1].toString();
          pdca = Double.valueOf(ele[2].toString());
          if(pdca >15000.00){
              pdca =15000.00;
          }else{
              pdca = pdca;
          }
          pfDependentCompAmt = String.valueOf(Math.round(pdca));
         // pfAmteptt = String.valueOf(Math.round(Double.valueOf(pfDependentCompAmt).doubleValue() * 0.0833D));
         // pfAmttpss = String.valueOf(Math.round(Double.valueOf(pfDependentCompAmt).doubleValue() * 0.0367D));
          deductdays = ele[3].toString();
        }
   
        ecrdetail2 = em.createNativeQuery("select a.AMOUNT,b.GROSS_SALARY from hr_salary_processing_dtl a ,hr_salary_processing b where COMPONENT_TYPE=(select a.AL_DESC from hr_salary_structure a where a.PURPOSE_CODE ='PUR002'\nand a.DESC_SHORT_CODE='1') and a.MONTH = '" + month + "' and DATE_FORMAT(a.ENTERED_DATE,'%Y')='" + year + "' and a.EMP_CODE=" + empList.get(i).toString() + "\n" + "and a.EMP_CODE = b.EMP_CODE and a.MONTH = b.MONTHS and DATE_FORMAT(a.ENTERED_DATE,'%Y')=DATE_FORMAT(b.MOD_DATE,'%Y')").getResultList();
        
        for (Object obj1 : ecrdetail2) {
          Object[] ele2 = (Object[])obj1;
          pfAmt = String.valueOf(Math.round(Double.valueOf(ele2[0].toString())));
          pfAmteptt = String.valueOf(Math.round(Double.valueOf(ele2[0].toString()) * 0.6939));
          pfAmttpss = String.valueOf(Math.round(Double.valueOf(ele2[0].toString()) * 0.3061));
          grossSal = String.valueOf(Math.round(Double.valueOf(ele2[1].toString()).doubleValue()));
        }
        bw.write(uanNumber + "#~#" + empName + "#~#" + grossSal + "#~#" + pfDependentCompAmt + "#~#" + pfDependentCompAmt + "#~#" + pfDependentCompAmt + "#~#" + pfAmt + "#~#" + pfAmteptt + "#~#" + pfAmttpss + "#~#" + deductdays + "#~#" + refunds);
        

        bw.write("\n");
      }
      
      return fileName;
    }
    catch (Exception ex)
    {
      throw new ApplicationException(ex.getMessage());
    } finally {
      try {
        if (bw != null) {
          bw.close();
        }
        if (fw != null) {
          fw.close();
        }
      } catch (Exception e) {
        file.delete();
        throw new ApplicationException(e.getMessage());
      }
    }
  }

   
   
}
