/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.adaptor;

import com.hrms.common.to.HrMstBusPKTO;
import com.hrms.common.to.HrMstBusTO;
import com.hrms.common.to.HrMstCompLoanPKTO;
import com.hrms.common.to.HrMstCompLoanTO;
import com.hrms.common.to.HrMstDeptSubdeptPKTO;
import com.hrms.common.to.HrMstDeptSubdeptTO;
import com.hrms.common.to.HrMstRoutePKTO;
import com.hrms.common.to.HrMstRouteTO;
import com.hrms.common.to.HrMstShiftPKTO;
import com.hrms.common.to.HrMstShiftTO;
import com.hrms.common.to.HrMstZoneLocationPKTO;
import com.hrms.common.to.HrMstZoneLocationTO;
import com.hrms.entity.hr.HrMstBus;
import com.hrms.entity.hr.HrMstBusPK;
import com.hrms.entity.hr.HrMstDeptSubdept;
import com.hrms.entity.hr.HrMstDeptSubdeptPK;
import com.hrms.entity.hr.HrMstRoute;
import com.hrms.entity.hr.HrMstRoutePK;
import com.hrms.entity.hr.HrMstZoneLocation;
import com.hrms.entity.hr.HrMstZoneLocationPK;
import com.hrms.entity.payroll.HrMstShift;
import com.hrms.entity.payroll.HrMstShiftPK;
import com.hrms.entity.personnel.HrMstCompLoan;
import com.hrms.entity.personnel.HrMstCompLoanPK;

/**
 *
 * @author Administrator
 */
public class ObjectAdaptorDefinitions {

    /****************  Written By Navneet ************************/
    /***************************  HrMstBus  ***********************************/
    public static HrMstBus adaptToHrMstBusEntity(HrMstBusTO hrMstBusTO) {
        HrMstBus hrMstBus = new HrMstBus();
        HrMstBusPK hrMstBusPK = new HrMstBusPK();
        hrMstBusPK.setBusNo(hrMstBusTO.getHrMstBusPKTO().getBusNo());
        hrMstBusPK.setCompCode(hrMstBusTO.getHrMstBusPKTO().getCompCode());
        hrMstBus.setHrMstBusPK(hrMstBusPK);
        hrMstBus.setAuthBy(hrMstBusTO.getAuthBy());
        hrMstBus.setBusDriver(hrMstBusTO.getBusDriver());
        hrMstBus.setBusEndTm(hrMstBusTO.getBusEndTm());
        hrMstBus.setBusStartTm(hrMstBusTO.getBusStartTm());
        hrMstBus.setDefaultComp(hrMstBusTO.getDefaultComp());
        hrMstBus.setEnteredBy(hrMstBusTO.getEnteredBy());
        hrMstBus.setHrPersonnelTransportCollection(hrMstBusTO.getHrPersonnelTransportCollection());
        hrMstBus.setModDate(hrMstBusTO.getModDate());
        hrMstBus.setRemarks(hrMstBusTO.getRemarks());
        hrMstBus.setStatFlag(hrMstBusTO.getStatFlag());
        hrMstBus.setStatUpFlag(hrMstBusTO.getStatUpFlag());
        return hrMstBus;
    }

    public static HrMstBusTO adaptToHrMstBusTO(HrMstBus hrMstBus) {

        HrMstBusTO hrMstBusTO = new HrMstBusTO();
        HrMstBusPKTO hrMstBusPKTO = new HrMstBusPKTO();
        hrMstBusPKTO.setBusNo(hrMstBus.getHrMstBusPK().getBusNo());
        hrMstBusPKTO.setCompCode(hrMstBus.getHrMstBusPK().getCompCode());
        hrMstBusTO.setHrMstBusPKTO(hrMstBusPKTO);
        hrMstBusTO.setAuthBy(hrMstBus.getAuthBy());
        hrMstBusTO.setBusDriver(hrMstBus.getBusDriver());
        hrMstBusTO.setBusEndTm(hrMstBus.getBusEndTm());
        hrMstBusTO.setBusStartTm(hrMstBus.getBusStartTm());
        hrMstBusTO.setDefaultComp(hrMstBus.getDefaultComp());
        hrMstBusTO.setEnteredBy(hrMstBus.getEnteredBy());
        hrMstBusTO.setHrPersonnelTransportCollection(hrMstBus.getHrPersonnelTransportCollection());
        hrMstBusTO.setModDate(hrMstBus.getModDate());
        hrMstBusTO.setRemarks(hrMstBus.getRemarks());
        hrMstBusTO.setStatFlag(hrMstBus.getStatFlag());
        hrMstBusTO.setStatUpFlag(hrMstBus.getStatUpFlag());
        return hrMstBusTO;
    }

    /***************************  HrMstRoute  ***********************************/
    public static HrMstRoute adaptToHrMstRouteEntity(HrMstRouteTO hrMstRouteTO) {
        HrMstRoute hrMstRoute = new HrMstRoute();
        HrMstRoutePK hrMstRoutePK = new HrMstRoutePK();
        hrMstRoutePK.setCompCode(hrMstRouteTO.getHrMstRoutePKTO().getCompCode());
        hrMstRoutePK.setRouteCode(hrMstRouteTO.getHrMstRoutePKTO().getRouteCode());
        hrMstRoute.setHrMstRoutePK(hrMstRoutePK);
        hrMstRoute.setAuthBy(hrMstRouteTO.getAuthBy());
        hrMstRoute.setDefaultComp(hrMstRouteTO.getDefaultComp());
        hrMstRoute.setEnteredBy(hrMstRouteTO.getEnteredBy());
        hrMstRoute.setHrPersonnelTransportCollection(hrMstRouteTO.getHrPersonnelTransportCollection());
        hrMstRoute.setModDate(hrMstRouteTO.getModDate());
        hrMstRoute.setRouteEnd(hrMstRouteTO.getRouteEnd());
        hrMstRoute.setRouteStart(hrMstRouteTO.getRouteStart());
        hrMstRoute.setRouteVia(hrMstRouteTO.getRouteVia());
        hrMstRoute.setStatFlag(hrMstRouteTO.getStatFlag());
        hrMstRoute.setStatUpFlag(hrMstRouteTO.getStatUpFlag());
        return hrMstRoute;
    }

    public static HrMstRouteTO adaptToHrMstRouteTO(HrMstRoute hrMstRoute) {
        HrMstRouteTO hrMstRouteTO = new HrMstRouteTO();
        HrMstRoutePKTO hrMstRoutePKTO = new HrMstRoutePKTO();
        hrMstRoutePKTO.setCompCode(hrMstRoute.getHrMstRoutePK().getCompCode());
        hrMstRoutePKTO.setRouteCode(hrMstRoute.getHrMstRoutePK().getRouteCode());
        hrMstRouteTO.setAuthBy(hrMstRoute.getAuthBy());
        hrMstRouteTO.setDefaultComp(hrMstRoute.getDefaultComp());
        hrMstRouteTO.setEnteredBy(hrMstRoute.getEnteredBy());
        hrMstRouteTO.setHrMstRoutePKTO(hrMstRoutePKTO);
        hrMstRouteTO.setHrPersonnelTransportCollection(hrMstRoute.getHrPersonnelTransportCollection());
        hrMstRouteTO.setModDate(hrMstRoute.getModDate());
        hrMstRouteTO.setRouteEnd(hrMstRoute.getRouteEnd());
        hrMstRouteTO.setRouteStart(hrMstRoute.getRouteStart());
        hrMstRouteTO.setRouteVia(hrMstRoute.getRouteVia());
        hrMstRouteTO.setStatFlag(hrMstRoute.getStatFlag());
        hrMstRouteTO.setStatUpFlag(hrMstRoute.getStatUpFlag());
        return hrMstRouteTO;
    }

    /***************************  HrMstDeptSubdept  ***********************************/
    public static HrMstDeptSubdept adaptToHrMstDeptSubdeptEntity(HrMstDeptSubdeptTO hrMstDeptSubdeptTO) {
        HrMstDeptSubdept hrMstDeptSubdept = new HrMstDeptSubdept();
        HrMstDeptSubdeptPK hrMstDeptSubdeptPK = new HrMstDeptSubdeptPK();
        hrMstDeptSubdeptPK.setCompCode(hrMstDeptSubdeptTO.getHrMstDeptSubdeptPKTO().getCompCode());
        hrMstDeptSubdeptPK.setDeptCode(hrMstDeptSubdeptTO.getHrMstDeptSubdeptPKTO().getDeptCode());
        hrMstDeptSubdeptPK.setSubDeptCode(hrMstDeptSubdeptTO.getHrMstDeptSubdeptPKTO().getSubDeptCode());
        hrMstDeptSubdept.setHrMstDeptSubdeptPK(hrMstDeptSubdeptPK);
        hrMstDeptSubdept.setAuthBy(hrMstDeptSubdeptTO.getAuthBy());
        hrMstDeptSubdept.setDefaultComp(hrMstDeptSubdeptTO.getDefaultComp());
        hrMstDeptSubdept.setEnteredBy(hrMstDeptSubdeptTO.getEnteredBy());
        hrMstDeptSubdept.setHrMstStruct(hrMstDeptSubdeptTO.getHrMstStruct());
        hrMstDeptSubdept.setHrMstStruct1(hrMstDeptSubdeptTO.getHrMstStruct1());
        hrMstDeptSubdept.setModDate(hrMstDeptSubdeptTO.getModDate());
        hrMstDeptSubdept.setStatFlag(hrMstDeptSubdeptTO.getStatFlag());
        hrMstDeptSubdept.setStatUpFlag(hrMstDeptSubdeptTO.getStatUpFlag());
        return hrMstDeptSubdept;
    }

    public static HrMstDeptSubdeptTO adaptToHrMstDeptSubdeptTO(HrMstDeptSubdept hrMstDeptSubdept) {
        HrMstDeptSubdeptTO hrMstDeptSubdeptTO = new HrMstDeptSubdeptTO();
        HrMstDeptSubdeptPKTO hrMstDeptSubdeptPKTO = new HrMstDeptSubdeptPKTO();
        hrMstDeptSubdeptPKTO.setCompCode(hrMstDeptSubdept.getHrMstDeptSubdeptPK().getCompCode());
        hrMstDeptSubdeptPKTO.setDeptCode(hrMstDeptSubdept.getHrMstDeptSubdeptPK().getDeptCode());
        hrMstDeptSubdeptPKTO.setSubDeptCode(hrMstDeptSubdept.getHrMstDeptSubdeptPK().getSubDeptCode());
        hrMstDeptSubdeptTO.setHrMstDeptSubdeptPKTO(hrMstDeptSubdeptPKTO);
        hrMstDeptSubdeptTO.setAuthBy(hrMstDeptSubdept.getAuthBy());
        hrMstDeptSubdeptTO.setDefaultComp(hrMstDeptSubdept.getDefaultComp());
        hrMstDeptSubdeptTO.setEnteredBy(hrMstDeptSubdept.getEnteredBy());
        hrMstDeptSubdeptTO.setHrMstStruct(hrMstDeptSubdept.getHrMstStruct());
        hrMstDeptSubdeptTO.setHrMstStruct1(hrMstDeptSubdept.getHrMstStruct1());
        hrMstDeptSubdeptTO.setModDate(hrMstDeptSubdept.getModDate());
        hrMstDeptSubdeptTO.setStatFlag(hrMstDeptSubdept.getStatFlag());
        hrMstDeptSubdeptTO.setStatUpFlag(hrMstDeptSubdept.getStatUpFlag());
        return hrMstDeptSubdeptTO;
    }

    /***************************  HrMstCompLoan  ***********************************/
    public static HrMstCompLoan adaptToHrMstCompLoanEntity(HrMstCompLoanTO hrMstCompLoanTO) {
        HrMstCompLoan hrMstCompLoan = new HrMstCompLoan();
        HrMstCompLoanPK hrMstCompLoanPK = new HrMstCompLoanPK();
        hrMstCompLoanPK.setCompCode(hrMstCompLoanTO.getHrMstCompLoanPKTO().getCompCode());
        hrMstCompLoanPK.setFromDate(hrMstCompLoanTO.getHrMstCompLoanPKTO().getFromDate());
        hrMstCompLoanPK.setToDate(hrMstCompLoanTO.getHrMstCompLoanPKTO().getToDate());
        hrMstCompLoan.setHrMstCompLoanPK(hrMstCompLoanPK);
        hrMstCompLoan.setAuthBy(hrMstCompLoanTO.getAuthBy());
        hrMstCompLoan.setDefaultComp(hrMstCompLoanTO.getDefaultComp());
        hrMstCompLoan.setEnteredBy(hrMstCompLoanTO.getEnteredBy());
        hrMstCompLoan.setLoanAvailable(hrMstCompLoanTO.getLoanAvailable());
        hrMstCompLoan.setLoanBudget(hrMstCompLoanTO.getLoanBudget());
        hrMstCompLoan.setModDate(hrMstCompLoanTO.getModDate());
        hrMstCompLoan.setPrincipleCollection(hrMstCompLoanTO.getPrincipleCollection());
        hrMstCompLoan.setStatFlag(hrMstCompLoanTO.getStatFlag());
        hrMstCompLoan.setStatUpFlag(hrMstCompLoanTO.getStatUpFlag());
        return hrMstCompLoan;
    }

    public static HrMstCompLoanTO adaptToHrMstCompLoanTO(HrMstCompLoan hrMstCompLoan) {
        HrMstCompLoanTO hrMstCompLoanTO = new HrMstCompLoanTO();
        HrMstCompLoanPKTO hrMstCompLoanPKTO = new HrMstCompLoanPKTO();
        hrMstCompLoanPKTO.setCompCode(hrMstCompLoan.getHrMstCompLoanPK().getCompCode());
        hrMstCompLoanPKTO.setFromDate(hrMstCompLoan.getHrMstCompLoanPK().getFromDate());
        hrMstCompLoanPKTO.setToDate(hrMstCompLoan.getHrMstCompLoanPK().getToDate());
        hrMstCompLoanTO.setHrMstCompLoanPKTO(hrMstCompLoanPKTO);
        hrMstCompLoanTO.setAuthBy(hrMstCompLoan.getAuthBy());
        hrMstCompLoanTO.setDefaultComp(hrMstCompLoan.getDefaultComp());
        hrMstCompLoanTO.setEnteredBy(hrMstCompLoan.getEnteredBy());
        hrMstCompLoanTO.setLoanAvailable(hrMstCompLoan.getLoanAvailable());
        hrMstCompLoanTO.setLoanBudget(hrMstCompLoan.getLoanBudget());
        hrMstCompLoanTO.setModDate(hrMstCompLoan.getModDate());
        hrMstCompLoanTO.setPrincipleCollection(hrMstCompLoan.getPrincipleCollection());
        hrMstCompLoanTO.setStatFlag(hrMstCompLoan.getStatFlag());
        hrMstCompLoanTO.setStatUpFlag(hrMstCompLoan.getStatUpFlag());
        return hrMstCompLoanTO;
    }

    /**
     *
     * @param hrMstShiftPKTO
     * @return
     */
    public static HrMstShiftPK adaptToHrMstShiftPKEntity(HrMstShiftPKTO hrMstShiftPKTO) {
        HrMstShiftPK hrMstShiftPK = new HrMstShiftPK();
        hrMstShiftPK.setCompCode(hrMstShiftPKTO.getCompCode());
        hrMstShiftPK.setShiftCode(hrMstShiftPKTO.getShiftCode());
        return hrMstShiftPK;
    }

    /**
     *
     * @param hrMstShiftTO
     * @return
     */
    public static HrMstShift adaptToHrMstShiftEntity(HrMstShiftTO hrMstShiftTO) {
        HrMstShift hrMstShift = new HrMstShift();
        hrMstShift.setAuthBy(hrMstShiftTO.getAuthBy());
        hrMstShift.setDefaultComp(hrMstShiftTO.getDefaultComp());
        hrMstShift.setEnteredBy(hrMstShiftTO.getEnteredBy());
        hrMstShift.setGraceShiftBreak(hrMstShiftTO.getGraceShiftBreak());
        hrMstShift.setGraceShiftIn(hrMstShiftTO.getGraceShiftIn());
        hrMstShift.setGraceShiftOut(hrMstShiftTO.getGraceShiftOut());
        hrMstShift.setHrMstShiftPK(PayrollObjectAdaptor.adaptToHrMstShiftPKEntity(hrMstShiftTO.getHrMstShiftPKTO()));
        hrMstShift.setModDate(hrMstShiftTO.getModDate());
        hrMstShift.setOdTimeFirst(hrMstShiftTO.getOdTimeFirst());
        hrMstShift.setOdTimeSecond(hrMstShiftTO.getOdTimeSecond());
        hrMstShift.setShiftBfrom(hrMstShiftTO.getShiftBfrom());
        hrMstShift.setShiftBto(hrMstShiftTO.getShiftBto());
        hrMstShift.setShiftDesc(hrMstShiftTO.getShiftDesc());
        hrMstShift.setShiftIn(hrMstShiftTO.getShiftIn());
        hrMstShift.setShiftOut(hrMstShiftTO.getShiftOut());
        hrMstShift.setShiftOutPunch(hrMstShiftTO.getShiftOutPunch());
        hrMstShift.setShiftPunchTime(hrMstShiftTO.getShiftPunchTime());
        hrMstShift.setStatFlag(hrMstShiftTO.getStatFlag());
        hrMstShift.setStatUpFlag(hrMstShiftTO.getStatUpFlag());
        return hrMstShift;
    }

    /**
     *
     * @param hrMstShiftPK
     * @return
     */
    public static HrMstShiftPKTO adaptToHrMstShiftPKTO(HrMstShiftPK hrMstShiftPK) {
        HrMstShiftPKTO hrMstShiftPKTO = new HrMstShiftPKTO();
        hrMstShiftPKTO.setCompCode(hrMstShiftPK.getCompCode());
        hrMstShiftPKTO.setShiftCode(hrMstShiftPK.getShiftCode());
        return hrMstShiftPKTO;
    }

    /**
     *
     * @param hrMstShift
     * @return
     */
    public static HrMstShiftTO adaptToHrMstShiftEntityTO(HrMstShift hrMstShift) {
        //////////////   collection
        HrMstShiftTO hrMstShiftTO = new HrMstShiftTO();
        hrMstShiftTO.setAuthBy(hrMstShift.getAuthBy());
        hrMstShiftTO.setDefaultComp(hrMstShift.getDefaultComp());
        hrMstShiftTO.setEnteredBy(hrMstShift.getEnteredBy());
        hrMstShiftTO.setGraceShiftBreak(hrMstShift.getGraceShiftBreak());
        hrMstShiftTO.setGraceShiftIn(hrMstShift.getGraceShiftIn());
        hrMstShiftTO.setGraceShiftOut(hrMstShift.getGraceShiftOut());
        hrMstShiftTO.setHrMstShiftPKTO(PayrollObjectAdaptor.adaptToHrMstShiftPKTO(hrMstShift.getHrMstShiftPK()));
        hrMstShiftTO.setModDate(hrMstShift.getModDate());
        hrMstShiftTO.setOdTimeFirst(hrMstShift.getOdTimeFirst());
        hrMstShiftTO.setOdTimeSecond(hrMstShift.getOdTimeSecond());
        hrMstShiftTO.setShiftBfrom(hrMstShift.getShiftBfrom());
        hrMstShiftTO.setShiftBto(hrMstShift.getShiftBto());
        hrMstShiftTO.setShiftDesc(hrMstShift.getShiftDesc());
        hrMstShiftTO.setShiftIn(hrMstShift.getShiftIn());
        hrMstShiftTO.setShiftOut(hrMstShift.getShiftOut());
        hrMstShiftTO.setShiftOutPunch(hrMstShift.getShiftOutPunch());
        hrMstShiftTO.setShiftPunchTime(hrMstShift.getShiftPunchTime());
        hrMstShiftTO.setStatFlag(hrMstShift.getStatFlag());
        hrMstShiftTO.setStatUpFlag(hrMstShift.getStatUpFlag());
        return hrMstShiftTO;

    }

    public static HrMstZoneLocationPK adaptHrMstZoneLocationPKEntity(HrMstZoneLocationPKTO hrMstZoneLocationPKTO) {
        HrMstZoneLocationPK hrMstZoneLocationPK = new HrMstZoneLocationPK();
        hrMstZoneLocationPK.setCompCode(hrMstZoneLocationPKTO.getCompCode());
        hrMstZoneLocationPK.setLocationCode(hrMstZoneLocationPKTO.getLocationCode());
        hrMstZoneLocationPK.setZoneCode(hrMstZoneLocationPKTO.getZoneCode());
        return hrMstZoneLocationPK;
    }

    public static HrMstZoneLocationPKTO adaptToHrMstZoneLocationPKTO(HrMstZoneLocationPK hrMstZoneLocationPK) {
        HrMstZoneLocationPKTO hrMstZoneLocationPKTO = new HrMstZoneLocationPKTO();
        hrMstZoneLocationPKTO.setCompCode(hrMstZoneLocationPK.getCompCode());
        hrMstZoneLocationPKTO.setLocationCode(hrMstZoneLocationPK.getLocationCode());
        hrMstZoneLocationPKTO.setZoneCode(hrMstZoneLocationPK.getZoneCode());
        return hrMstZoneLocationPKTO;
    }

    public static HrMstZoneLocation adaptToHrMstZoneLocationEntity(HrMstZoneLocationTO hrMstZoneLocationTO) {
        HrMstZoneLocation hrMstZoneLocation = new HrMstZoneLocation();
        hrMstZoneLocation.setHrMstZoneLocationPK(ObjectAdaptorHr.adaptHrMstZoneLocationPKEntity(hrMstZoneLocationTO.getHrMstZoneLocationPKTO()));
        hrMstZoneLocation.setHrMstStruct(ObjectAdaptorHr.adaptToMstStructEntity(hrMstZoneLocationTO.getHrMstStructTO()));
        hrMstZoneLocation.setHrMstStruct1(ObjectAdaptorHr.adaptToMstStructEntity(hrMstZoneLocationTO.getHrMstStruct1TO()));
        hrMstZoneLocation.setStatFlag(hrMstZoneLocationTO.getStatFlag());
        hrMstZoneLocation.setStatUpFlag(hrMstZoneLocationTO.getStatUpFlag());
        hrMstZoneLocation.setModDate(hrMstZoneLocationTO.getModDate());
        hrMstZoneLocation.setDefaultComp(hrMstZoneLocationTO.getDefaultComp());
        hrMstZoneLocation.setAuthBy(hrMstZoneLocationTO.getAuthBy());
        hrMstZoneLocation.setEnteredBy(hrMstZoneLocationTO.getEnteredBy());
        return hrMstZoneLocation;
    }

    public static HrMstZoneLocationTO adaptToHrMstZoneLocationTO(HrMstZoneLocation hrMstZoneLocation) {
        HrMstZoneLocationTO hrMstZoneLocationTO = new HrMstZoneLocationTO();
        hrMstZoneLocationTO.setHrMstZoneLocationPKTO(ObjectAdaptorHr.adaptToHrMstZoneLocationPKTO(hrMstZoneLocation.getHrMstZoneLocationPK()));
        hrMstZoneLocationTO.setHrMstStructTO(ObjectAdaptorHr.adaptToStructMasterTO(hrMstZoneLocation.getHrMstStruct()));
        hrMstZoneLocationTO.setHrMstStruct1TO(ObjectAdaptorHr.adaptToStructMasterTO(hrMstZoneLocation.getHrMstStruct1()));
        hrMstZoneLocationTO.setStatFlag(hrMstZoneLocation.getStatFlag());
        hrMstZoneLocationTO.setStatUpFlag(hrMstZoneLocation.getStatUpFlag());
        hrMstZoneLocationTO.setModDate(hrMstZoneLocation.getModDate());
        hrMstZoneLocationTO.setDefaultComp(hrMstZoneLocation.getDefaultComp());
        hrMstZoneLocationTO.setAuthBy(hrMstZoneLocation.getAuthBy());
        hrMstZoneLocationTO.setEnteredBy(hrMstZoneLocation.getEnteredBy());
        return hrMstZoneLocationTO;
    }
    /***************** END **********************/
}
