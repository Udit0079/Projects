/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.other;

import com.cbs.dto.report.DayActivityPojo;
import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface DailyProcessManagementRemote {

    /**
     *
     * @param orgBrnCode
     * @return
     * @throws ApplicationException
     */
    public List selectFromBankDays(String orgBrnCode) throws ApplicationException;

    /**
     *
     * @param orgBrnCode
     * @return
     * @throws ApplicationException
     */
    public List selectMinDateFromBnkDays(String orgBrnCode) throws ApplicationException;

    /**
     *
     * @param orgBrnCode
     * @return
     * @throws ApplicationException
     */
    public String getMinMonthDate(String orgBrnCode) throws ApplicationException;

    /**
     *
     * @param orgBrnCode
     * @return
     * @throws ApplicationException
     */
    public String selectMinDateFromYearEnd(String orgBrnCode) throws ApplicationException;

    /**
     *
     * @param orgBrnCode
     * @return
     * @throws ApplicationException
     */
    public List selectFromBank2Days(String orgBrnCode) throws ApplicationException;

    /**
     *
     * @param orgBrnCode
     * @return
     * @throws ApplicationException
     */
    public List selectFromBank3Days(String orgBrnCode) throws ApplicationException;

    /**
     *
     * @param todayDate
     * @return
     * @throws ApplicationException
     */
    public List selectFromCBSBankDays(String todayDate) throws ApplicationException;

    /**
     *
     * @param orgBrnCode
     * @param date
     * @param userName
     * @return
     * @throws ApplicationException
     */
    public String dayBeginProcess(String orgBrnCode, String date, String userName) throws ApplicationException;

    public String crrSlrDeficitChecking(String dt) throws ApplicationException;

    /**
     *
     * @param tempBd
     * @param orgBrnCode
     * @param userName
     * @return
     * @throws ApplicationException
     */
    public String dayEndProcess(String tempBd, String orgBrnCode, String userName) throws ApplicationException;

    /**
     *
     * @param monthEndName
     * @param orgnBrCode
     * @param tempBd
     * @param userName
     * @return
     * @throws ApplicationException
     */
    public String monthEndProcess(String monthEndName, String orgnBrCode, String tempBd, String userName) throws ApplicationException;

    /**
     *
     * @param tempBd
     * @param orgBrnCode
     * @param userName
     * @return
     * @throws ApplicationException
     */
    public String yearEndProcess(String tempBd, String orgBrnCode, String userName) throws ApplicationException;

    /**
     *
     * @param orgBrnCode
     * @param todayDate
     * @return
     */
    public List<DayActivityPojo> getDayActivityReport(String orgBrnCode, String todayDate) throws ApplicationException;

    /**
     *
     * @param todayDate
     * @return
     * @throws ApplicationException
     */
    public List checkHolidayOnADate(String todayDate, String orgBrnCode) throws ApplicationException;

    /**
     *
     * @param date
     * @return
     * @throws ApplicationException
     */
    public String maxWorkingDate(String date, String orgBrnCode) throws ApplicationException;

    /**
     *
     * @param date
     * @param orgBrnCode
     * @return
     * @throws ApplicationException
     */
    public boolean isHolidayDate(String date, String orgBrnCode) throws ApplicationException;

    public List getAllBranchCodeExceptHO() throws com.cbs.exception.ApplicationException;

    public List getAllBranchCode() throws com.cbs.exception.ApplicationException;
    
    public boolean isYearEndCompleted(String tDate, String orgBrnCode) throws ApplicationException;
}
