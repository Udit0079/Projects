/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.ho.master;

import javax.ejb.Remote;

/**
 *
 * @author admin
 */
@Remote
public interface ReportingFacadeRemote {

    public java.util.List getTableDetails() throws com.cbs.exception.ApplicationException;

    public java.lang.String saveRecord(java.lang.String acDescription, java.lang.String acNo, java.lang.String BalTp) throws com.cbs.exception.ApplicationException;

    public java.lang.String deleteRecord(java.lang.String acNo, java.lang.String BalTp) throws com.cbs.exception.ApplicationException;

    public java.util.List formLoad() throws com.cbs.exception.ApplicationException;

    public java.util.List minRepDate() throws com.cbs.exception.ApplicationException;

    public java.lang.String updateSingleRecord(java.lang.String s1, java.lang.String balance, java.lang.String enterBy) throws com.cbs.exception.ApplicationException;

    public java.util.List gridLoad() throws com.cbs.exception.ApplicationException;

    public java.util.List getReportDate(java.lang.String fDate, java.lang.String Tdate) throws com.cbs.exception.ApplicationException;

    public java.lang.String aftergetReportDate(java.lang.String repfridate, java.lang.String userName) throws com.cbs.exception.ApplicationException;

    public java.lang.String insertHistory(java.lang.String fDate, java.lang.String Tdate, java.lang.String userName) throws com.cbs.exception.ApplicationException;

    public java.lang.String calcRepFriNEW(java.lang.String myDate) throws com.cbs.exception.ApplicationException;
    
}
