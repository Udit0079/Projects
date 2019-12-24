/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master.sm;

import com.cbs.dto.loan.CbsSchemeCustAccountDetailsTO;
import com.cbs.dto.master.CbsExceptionMasterTO;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.web.delegate.SchemeMasterManagementDelegate;
import com.cbs.web.exception.WebException;
import com.cbs.web.pojo.master.SearchCode;
import java.util.ArrayList;
import java.util.List;
import javax.faces.event.ActionEvent;
import org.apache.log4j.Logger;

/**
 *
 * @author root
 */
public class CAD {

    private static final Logger logger = Logger.getLogger(CAD.class);
    private SchemeMaster schemeMaster;

    public SchemeMaster getSchemeMaster() {
        return schemeMaster;
    }

    public void setSchemeMaster(SchemeMaster schemeMaster) {
        this.schemeMaster = schemeMaster;
    }
    //Variables For cad.jsp file
    private String chequeNotAllowed;
    private String stChequeNotAllowed;
    private String chequeNotIssued;
    private String stChequeNotIssued;
    private String chequeIssuedToMinor;
    private String stChequeIssuedToMinormessage;
    private String chequeStopped;
    private String stChequeStopped;
    private String chequeCautioned;
    private String stChequeCautioned;
    private String introducerNotCust;
    private String stIntroducerNotCust;
    private String introducerNewCust;
    private String stIntroducerNewCust;
    private String employeesOwnAccount;
    private String stEmployeesOwnAccount;
    private String sIPerfixCharges;
    private String stSIPerfixCharges;
    private String drAgainstCC;
    private String stDrAgainstCC;
    private String clsPendingJobs;
    private String stClsPendingJobs;
    private String clsPendingSI;
    private String stClsPendingSI;
    private String clsPendingCC;
    private String stClsPendingCC;
    private String clsPendingChq;
    private String stClsPendingChq;
    private String clsPendingProxy;
    private String stClsPendingProxy;
    private String chequeIssuedButNotAck;
    private String stChequeIssuedButNotAck;
    private String chequeUnusable;
    private String stChequeUnusable;
    private String chequeStoppedButNotVerified;
    private String stChequeStoppedButNotVerified;
    private List<SearchCode> tabSearch;
    private boolean chequeNotAllowedDisable;
    private boolean chequeNotIssuedDisable;
    private boolean chequeIssuedToMinorDisable;
    private boolean chequeStoppedDisable;
    private boolean chequeCautionedDisable;
    private boolean introducerNotCustDisable;
    private boolean introducerNewCustDisable;
    private boolean employeesOwnAccountDisable;
    private boolean sIPerfixChargesDisable;
    private boolean drAgainstCCDisable;
    private boolean clsPendingJobsDisable;
    private boolean clsPendingSIDisable;
    private boolean clsPendingCCDisable;
    private boolean clsPendingChqDisable;
    private boolean clsPendingProxyDisable;
    private boolean chequeIssuedButNotAckDisable;
    private boolean chequeUnusableDisable;
    private boolean chequeStoppedButNotVerifiedDisable;

    //Getter-Setter for cad.jsp file
    public List<SearchCode> getTabSearch() {
        return tabSearch;
    }

    public void setTabSearch(List<SearchCode> tabSearch) {
        this.tabSearch = tabSearch;
    }

    public String getChequeCautioned() {
        return chequeCautioned;
    }

    public void setChequeCautioned(String chequeCautioned) {
        this.chequeCautioned = chequeCautioned;
    }

    public boolean isChequeCautionedDisable() {
        return chequeCautionedDisable;
    }

    public void setChequeCautionedDisable(boolean chequeCautionedDisable) {
        this.chequeCautionedDisable = chequeCautionedDisable;
    }

    public String getChequeIssuedButNotAck() {
        return chequeIssuedButNotAck;
    }

    public void setChequeIssuedButNotAck(String chequeIssuedButNotAck) {
        this.chequeIssuedButNotAck = chequeIssuedButNotAck;
    }

    public boolean isChequeIssuedButNotAckDisable() {
        return chequeIssuedButNotAckDisable;
    }

    public void setChequeIssuedButNotAckDisable(boolean chequeIssuedButNotAckDisable) {
        this.chequeIssuedButNotAckDisable = chequeIssuedButNotAckDisable;
    }

    public String getChequeIssuedToMinor() {
        return chequeIssuedToMinor;
    }

    public void setChequeIssuedToMinor(String chequeIssuedToMinor) {
        this.chequeIssuedToMinor = chequeIssuedToMinor;
    }

    public boolean isChequeIssuedToMinorDisable() {
        return chequeIssuedToMinorDisable;
    }

    public void setChequeIssuedToMinorDisable(boolean chequeIssuedToMinorDisable) {
        this.chequeIssuedToMinorDisable = chequeIssuedToMinorDisable;
    }

    public String getChequeNotAllowed() {
        return chequeNotAllowed;
    }

    public void setChequeNotAllowed(String chequeNotAllowed) {
        this.chequeNotAllowed = chequeNotAllowed;
    }

    public boolean isChequeNotAllowedDisable() {
        return chequeNotAllowedDisable;
    }

    public void setChequeNotAllowedDisable(boolean chequeNotAllowedDisable) {
        this.chequeNotAllowedDisable = chequeNotAllowedDisable;
    }

    public String getChequeNotIssued() {
        return chequeNotIssued;
    }

    public void setChequeNotIssued(String chequeNotIssued) {
        this.chequeNotIssued = chequeNotIssued;
    }

    public boolean isChequeNotIssuedDisable() {
        return chequeNotIssuedDisable;
    }

    public void setChequeNotIssuedDisable(boolean chequeNotIssuedDisable) {
        this.chequeNotIssuedDisable = chequeNotIssuedDisable;
    }

    public String getChequeStopped() {
        return chequeStopped;
    }

    public void setChequeStopped(String chequeStopped) {
        this.chequeStopped = chequeStopped;
    }

    public String getChequeStoppedButNotVerified() {
        return chequeStoppedButNotVerified;
    }

    public void setChequeStoppedButNotVerified(String chequeStoppedButNotVerified) {
        this.chequeStoppedButNotVerified = chequeStoppedButNotVerified;
    }

    public boolean isChequeStoppedButNotVerifiedDisable() {
        return chequeStoppedButNotVerifiedDisable;
    }

    public void setChequeStoppedButNotVerifiedDisable(boolean chequeStoppedButNotVerifiedDisable) {
        this.chequeStoppedButNotVerifiedDisable = chequeStoppedButNotVerifiedDisable;
    }

    public boolean isChequeStoppedDisable() {
        return chequeStoppedDisable;
    }

    public void setChequeStoppedDisable(boolean chequeStoppedDisable) {
        this.chequeStoppedDisable = chequeStoppedDisable;
    }

    public String getChequeUnusable() {
        return chequeUnusable;
    }

    public void setChequeUnusable(String chequeUnusable) {
        this.chequeUnusable = chequeUnusable;
    }

    public boolean isChequeUnusableDisable() {
        return chequeUnusableDisable;
    }

    public void setChequeUnusableDisable(boolean chequeUnusableDisable) {
        this.chequeUnusableDisable = chequeUnusableDisable;
    }

    public String getClsPendingCC() {
        return clsPendingCC;
    }

    public void setClsPendingCC(String clsPendingCC) {
        this.clsPendingCC = clsPendingCC;
    }

    public boolean isClsPendingCCDisable() {
        return clsPendingCCDisable;
    }

    public void setClsPendingCCDisable(boolean clsPendingCCDisable) {
        this.clsPendingCCDisable = clsPendingCCDisable;
    }

    public String getClsPendingChq() {
        return clsPendingChq;
    }

    public void setClsPendingChq(String clsPendingChq) {
        this.clsPendingChq = clsPendingChq;
    }

    public boolean isClsPendingChqDisable() {
        return clsPendingChqDisable;
    }

    public void setClsPendingChqDisable(boolean clsPendingChqDisable) {
        this.clsPendingChqDisable = clsPendingChqDisable;
    }

    public String getClsPendingJobs() {
        return clsPendingJobs;
    }

    public void setClsPendingJobs(String clsPendingJobs) {
        this.clsPendingJobs = clsPendingJobs;
    }

    public boolean isClsPendingJobsDisable() {
        return clsPendingJobsDisable;
    }

    public void setClsPendingJobsDisable(boolean clsPendingJobsDisable) {
        this.clsPendingJobsDisable = clsPendingJobsDisable;
    }

    public String getClsPendingProxy() {
        return clsPendingProxy;
    }

    public void setClsPendingProxy(String clsPendingProxy) {
        this.clsPendingProxy = clsPendingProxy;
    }

    public boolean isClsPendingProxyDisable() {
        return clsPendingProxyDisable;
    }

    public void setClsPendingProxyDisable(boolean clsPendingProxyDisable) {
        this.clsPendingProxyDisable = clsPendingProxyDisable;
    }

    public String getClsPendingSI() {
        return clsPendingSI;
    }

    public void setClsPendingSI(String clsPendingSI) {
        this.clsPendingSI = clsPendingSI;
    }

    public boolean isClsPendingSIDisable() {
        return clsPendingSIDisable;
    }

    public void setClsPendingSIDisable(boolean clsPendingSIDisable) {
        this.clsPendingSIDisable = clsPendingSIDisable;
    }

    public String getDrAgainstCC() {
        return drAgainstCC;
    }

    public void setDrAgainstCC(String drAgainstCC) {
        this.drAgainstCC = drAgainstCC;
    }

    public boolean isDrAgainstCCDisable() {
        return drAgainstCCDisable;
    }

    public void setDrAgainstCCDisable(boolean drAgainstCCDisable) {
        this.drAgainstCCDisable = drAgainstCCDisable;
    }

    public String getEmployeesOwnAccount() {
        return employeesOwnAccount;
    }

    public void setEmployeesOwnAccount(String employeesOwnAccount) {
        this.employeesOwnAccount = employeesOwnAccount;
    }

    public boolean isEmployeesOwnAccountDisable() {
        return employeesOwnAccountDisable;
    }

    public void setEmployeesOwnAccountDisable(boolean employeesOwnAccountDisable) {
        this.employeesOwnAccountDisable = employeesOwnAccountDisable;
    }

    public String getIntroducerNewCust() {
        return introducerNewCust;
    }

    public void setIntroducerNewCust(String introducerNewCust) {
        this.introducerNewCust = introducerNewCust;
    }

    public boolean isIntroducerNewCustDisable() {
        return introducerNewCustDisable;
    }

    public void setIntroducerNewCustDisable(boolean introducerNewCustDisable) {
        this.introducerNewCustDisable = introducerNewCustDisable;
    }

    public String getIntroducerNotCust() {
        return introducerNotCust;
    }

    public void setIntroducerNotCust(String introducerNotCust) {
        this.introducerNotCust = introducerNotCust;
    }

    public boolean isIntroducerNotCustDisable() {
        return introducerNotCustDisable;
    }

    public void setIntroducerNotCustDisable(boolean introducerNotCustDisable) {
        this.introducerNotCustDisable = introducerNotCustDisable;
    }

    public String getsIPerfixCharges() {
        return sIPerfixCharges;
    }

    public void setsIPerfixCharges(String sIPerfixCharges) {
        this.sIPerfixCharges = sIPerfixCharges;
    }

    public boolean issIPerfixChargesDisable() {
        return sIPerfixChargesDisable;
    }

    public void setsIPerfixChargesDisable(boolean sIPerfixChargesDisable) {
        this.sIPerfixChargesDisable = sIPerfixChargesDisable;
    }

    public String getStChequeCautioned() {
        return stChequeCautioned;
    }

    public void setStChequeCautioned(String stChequeCautioned) {
        this.stChequeCautioned = stChequeCautioned;
    }

    public String getStChequeIssuedButNotAck() {
        return stChequeIssuedButNotAck;
    }

    public void setStChequeIssuedButNotAck(String stChequeIssuedButNotAck) {
        this.stChequeIssuedButNotAck = stChequeIssuedButNotAck;
    }

    public String getStChequeIssuedToMinormessage() {
        return stChequeIssuedToMinormessage;
    }

    public void setStChequeIssuedToMinormessage(String stChequeIssuedToMinormessage) {
        this.stChequeIssuedToMinormessage = stChequeIssuedToMinormessage;
    }

    public String getStChequeNotAllowed() {
        return stChequeNotAllowed;
    }

    public void setStChequeNotAllowed(String stChequeNotAllowed) {
        this.stChequeNotAllowed = stChequeNotAllowed;
    }

    public String getStChequeNotIssued() {
        return stChequeNotIssued;
    }

    public void setStChequeNotIssued(String stChequeNotIssued) {
        this.stChequeNotIssued = stChequeNotIssued;
    }

    public String getStChequeStopped() {
        return stChequeStopped;
    }

    public void setStChequeStopped(String stChequeStopped) {
        this.stChequeStopped = stChequeStopped;
    }

    public String getStChequeStoppedButNotVerified() {
        return stChequeStoppedButNotVerified;
    }

    public void setStChequeStoppedButNotVerified(String stChequeStoppedButNotVerified) {
        this.stChequeStoppedButNotVerified = stChequeStoppedButNotVerified;
    }

    public String getStChequeUnusable() {
        return stChequeUnusable;
    }

    public void setStChequeUnusable(String stChequeUnusable) {
        this.stChequeUnusable = stChequeUnusable;
    }

    public String getStClsPendingCC() {
        return stClsPendingCC;
    }

    public void setStClsPendingCC(String stClsPendingCC) {
        this.stClsPendingCC = stClsPendingCC;
    }

    public String getStClsPendingChq() {
        return stClsPendingChq;
    }

    public void setStClsPendingChq(String stClsPendingChq) {
        this.stClsPendingChq = stClsPendingChq;
    }

    public String getStClsPendingJobs() {
        return stClsPendingJobs;
    }

    public void setStClsPendingJobs(String stClsPendingJobs) {
        this.stClsPendingJobs = stClsPendingJobs;
    }

    public String getStClsPendingProxy() {
        return stClsPendingProxy;
    }

    public void setStClsPendingProxy(String stClsPendingProxy) {
        this.stClsPendingProxy = stClsPendingProxy;
    }

    public String getStClsPendingSI() {
        return stClsPendingSI;
    }

    public void setStClsPendingSI(String stClsPendingSI) {
        this.stClsPendingSI = stClsPendingSI;
    }

    public String getStDrAgainstCC() {
        return stDrAgainstCC;
    }

    public void setStDrAgainstCC(String stDrAgainstCC) {
        this.stDrAgainstCC = stDrAgainstCC;
    }

    public String getStEmployeesOwnAccount() {
        return stEmployeesOwnAccount;
    }

    public void setStEmployeesOwnAccount(String stEmployeesOwnAccount) {
        this.stEmployeesOwnAccount = stEmployeesOwnAccount;
    }

    public String getStIntroducerNewCust() {
        return stIntroducerNewCust;
    }

    public void setStIntroducerNewCust(String stIntroducerNewCust) {
        this.stIntroducerNewCust = stIntroducerNewCust;
    }

    public String getStIntroducerNotCust() {
        return stIntroducerNotCust;
    }

    public void setStIntroducerNotCust(String stIntroducerNotCust) {
        this.stIntroducerNotCust = stIntroducerNotCust;
    }

    public String getStSIPerfixCharges() {
        return stSIPerfixCharges;
    }

    public void setStSIPerfixCharges(String stSIPerfixCharges) {
        this.stSIPerfixCharges = stSIPerfixCharges;
    }

    /** Creates a new instance of CAD */
    public CAD() {
       
    }

    //Functionality for cad.jsp file
    public void descChequeNotAllowed() {
        schemeMaster.setMessage("");
        try {
            if (this.chequeNotAllowed.equals("")) {
                this.setStChequeNotAllowed("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.chequeNotAllowed);
                this.setStChequeNotAllowed(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setStChequeNotAllowed("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void desChequeNotIssued() {
        this.schemeMaster.setMessage("");
        try {
            if (this.chequeNotIssued.equals("")) {
                this.setStChequeNotIssued("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.chequeNotIssued);
                this.setStChequeNotIssued(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setStChequeNotIssued("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void desChequeIssuedToMinor() {
        this.schemeMaster.setMessage("");
        try {
            if (this.chequeIssuedToMinor.equals("")) {
                this.setStChequeIssuedToMinormessage("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.chequeIssuedToMinor);
                this.setStChequeIssuedToMinormessage(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setStChequeIssuedToMinormessage("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descChequeStopped() {
        this.schemeMaster.setMessage("");
        try {
            if (this.chequeStopped.equals("")) {
                this.setStChequeStopped("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.chequeStopped);
                this.setStChequeStopped(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setStChequeStopped("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descChequeCautioned() {
        this.schemeMaster.setMessage("");
        try {
            if (this.chequeCautioned.equals("")) {
                this.setStChequeCautioned("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.chequeCautioned);
                this.setStChequeCautioned(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setStChequeCautioned("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descIntroducerNotCust() {
        this.schemeMaster.setMessage("");
        try {
            if (this.introducerNotCust.equals("")) {
                this.setStIntroducerNotCust("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.introducerNotCust);
                this.setStIntroducerNotCust(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setStIntroducerNotCust("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descIntroducerNewCust() {
        this.schemeMaster.setMessage("");
        try {
            if (this.introducerNewCust.equals("")) {
                this.setStIntroducerNewCust("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.introducerNewCust);
                this.setStIntroducerNewCust(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setStIntroducerNewCust("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descEmployeesOwnAccount() {
        this.schemeMaster.setMessage("");
        try {
            if (this.employeesOwnAccount.equals("")) {
                this.setStEmployeesOwnAccount("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.employeesOwnAccount);
                this.setStEmployeesOwnAccount(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setStEmployeesOwnAccount("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descSIPerfixCharges() {
        this.schemeMaster.setMessage("");
        try {
            if (this.sIPerfixCharges.equals("")) {
                this.setStSIPerfixCharges("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.sIPerfixCharges);
                this.setStSIPerfixCharges(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setStSIPerfixCharges("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descDrAgainstCC() {
        this.schemeMaster.setMessage("");
        try {
            if (this.drAgainstCC.equals("")) {
                this.setStDrAgainstCC("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.drAgainstCC);
                this.setStDrAgainstCC(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setStDrAgainstCC("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descClsPendingJobs() {
        this.schemeMaster.setMessage("");
        try {
            if (this.clsPendingJobs.equals("")) {
                this.setStClsPendingJobs("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.clsPendingJobs);
                this.setStClsPendingJobs(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setStClsPendingJobs("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descClsPendingSI() {
        this.schemeMaster.setMessage("");
        try {
            if (this.clsPendingSI.equals("")) {
                this.setStClsPendingSI("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.clsPendingSI);
                this.setStClsPendingSI(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setStClsPendingSI("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descClsPendingCC() {
        this.schemeMaster.setMessage("");
        try {
            if (this.clsPendingCC.equals("")) {
                this.setStClsPendingCC("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.clsPendingCC);
                this.setStClsPendingCC(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setStClsPendingCC("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descClsPendingChq() {
        this.schemeMaster.setMessage("");
        try {
            if (this.clsPendingChq.equals("")) {
                this.setStClsPendingChq("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.clsPendingChq);
                this.setStClsPendingChq(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setStClsPendingChq("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descClsPendingProxy() {
        this.schemeMaster.setMessage("");
        try {
            if (this.clsPendingProxy.equals("")) {
                this.setStClsPendingProxy("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.clsPendingProxy);
                this.setStClsPendingProxy(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setStClsPendingProxy("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descChequeIssuedButNotAck() {
        this.schemeMaster.setMessage("");
        try {
            if (this.chequeIssuedButNotAck.equals("")) {
                this.setStChequeIssuedButNotAck("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.chequeIssuedButNotAck);
                this.setStChequeIssuedButNotAck(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setStChequeIssuedButNotAck("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descChequeUnusable() {
        this.schemeMaster.setMessage("");
        try {
            if (this.chequeUnusable.equals("")) {
                this.setStChequeUnusable("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.chequeUnusable);
                this.setStChequeUnusable(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setStChequeUnusable("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void descChequeStoppedButNotVerified() {
        this.schemeMaster.setMessage("");
        try {
            if (this.chequeStoppedButNotVerified.equals("")) {
                this.setStChequeStoppedButNotVerified("");
            } else {
                SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
                CbsExceptionMasterTO cbsExceptionMasterTO = schemeMasterManagementDelegate.getExceptionDesc(this.chequeStoppedButNotVerified);
                this.setStChequeStoppedButNotVerified(cbsExceptionMasterTO.getExceptionDesc());
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
            if (e.getMessageKey().equalsIgnoreCase("no.result.found")) {
                this.schemeMaster.setMessage("");
                this.setStChequeStoppedButNotVerified("Please fill The right code.");
                this.schemeMaster.setMessage("Please fill The right code.");
            }
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }
    }

    public void fetchCurrentRow(ActionEvent event) {
    }

    public void getTableDetails() {
        this.schemeMaster.setMessage("");
        tabSearch = new ArrayList<SearchCode>();
        try {
            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
            List<CbsExceptionMasterTO> cbsExceptionMasterTOs = schemeMasterManagementDelegate.getExceptionCode();
            if (cbsExceptionMasterTOs.size() > 0) {
                for (CbsExceptionMasterTO cbsExpMasterTO : cbsExceptionMasterTOs) {
                    SearchCode searchCode = new SearchCode();
                    searchCode.setCode(cbsExpMasterTO.getExceptionCode());
                    searchCode.setDesc(cbsExpMasterTO.getExceptionDesc());
                    tabSearch.add(searchCode);
                }
            } else {
                this.schemeMaster.setMessage("Records does not exist.");
            }
        } catch (WebException e) {
            logger.error("Exception occured while executing method createEvent()", e);
        } catch (ServiceLocatorException e1) {
            logger.error("Exception occured while executing method createEvent()", e1);
        } catch (Exception e) {
            logger.error("Exception occured while executing method createEvent()", e);
        }

    }

    public void cadFormData() {
         this.getSchemeMaster().setMessage("");
        try {
            SchemeMasterManagementDelegate schemeMasterManagementDelegate = new SchemeMasterManagementDelegate();
            CbsSchemeCustAccountDetailsTO cbsSchemeCustAccountDetailsTO = schemeMasterManagementDelegate.cadFormData(schemeMaster.getSchemeCode());
            if (cbsSchemeCustAccountDetailsTO != null) {
                if (cbsSchemeCustAccountDetailsTO.getChequeNotAllowed() == null || cbsSchemeCustAccountDetailsTO.getChequeNotAllowed().equalsIgnoreCase("")) {
                    this.setChequeNotAllowed("");
                } else {
                    this.setChequeNotAllowed(cbsSchemeCustAccountDetailsTO.getChequeNotAllowed());
                    descChequeNotAllowed();
                }
                if (cbsSchemeCustAccountDetailsTO.getChequeNotIssued() == null || cbsSchemeCustAccountDetailsTO.getChequeNotIssued().equalsIgnoreCase("")) {
                    this.setChequeNotIssued("");
                } else {
                    this.setChequeNotIssued(cbsSchemeCustAccountDetailsTO.getChequeNotIssued());
                    desChequeNotIssued();
                }
                if (cbsSchemeCustAccountDetailsTO.getChequeIssuedToMinor() == null || cbsSchemeCustAccountDetailsTO.getChequeIssuedToMinor().equalsIgnoreCase("")) {
                    this.setChequeIssuedToMinor("");
                } else {
                    this.setChequeIssuedToMinor(cbsSchemeCustAccountDetailsTO.getChequeIssuedToMinor());
                    desChequeIssuedToMinor();
                }
                if (cbsSchemeCustAccountDetailsTO.getChequeStopped() == null || cbsSchemeCustAccountDetailsTO.getChequeStopped().equalsIgnoreCase("")) {
                    this.setChequeStopped("");
                } else {
                    this.setChequeStopped(cbsSchemeCustAccountDetailsTO.getChequeStopped());
                    descChequeStopped();
                }
                if (cbsSchemeCustAccountDetailsTO.getChequeCautioned() == null || cbsSchemeCustAccountDetailsTO.getChequeCautioned().equalsIgnoreCase("")) {
                    this.setChequeCautioned("");
                } else {
                    this.setChequeCautioned(cbsSchemeCustAccountDetailsTO.getChequeCautioned());
                    descChequeCautioned();
                }
                if (cbsSchemeCustAccountDetailsTO.getIntroducerNotCust() == null || cbsSchemeCustAccountDetailsTO.getIntroducerNotCust().equalsIgnoreCase("")) {
                    this.setIntroducerNotCust("");
                } else {
                    this.setIntroducerNotCust(cbsSchemeCustAccountDetailsTO.getIntroducerNotCust());
                    descIntroducerNotCust();
                }
                if (cbsSchemeCustAccountDetailsTO.getIntroducerNewCust() == null || cbsSchemeCustAccountDetailsTO.getIntroducerNewCust().equalsIgnoreCase("")) {
                    this.setIntroducerNewCust("");
                } else {
                    this.setIntroducerNewCust(cbsSchemeCustAccountDetailsTO.getIntroducerNewCust());
                    descIntroducerNewCust();
                }
                if (cbsSchemeCustAccountDetailsTO.getEmployeeOwnAccount() == null || cbsSchemeCustAccountDetailsTO.getEmployeeOwnAccount().equalsIgnoreCase("")) {
                    this.setEmployeesOwnAccount("");
                } else {
                    this.setEmployeesOwnAccount(cbsSchemeCustAccountDetailsTO.getEmployeeOwnAccount());
                    descEmployeesOwnAccount();
                }
                if (cbsSchemeCustAccountDetailsTO.getSiPerfixCharges() == null || cbsSchemeCustAccountDetailsTO.getSiPerfixCharges().equalsIgnoreCase("")) {
                    this.setsIPerfixCharges("");
                } else {
                    this.setsIPerfixCharges(cbsSchemeCustAccountDetailsTO.getSiPerfixCharges());
                    descSIPerfixCharges();
                }
                if (cbsSchemeCustAccountDetailsTO.getDrAgainstCc() == null || cbsSchemeCustAccountDetailsTO.getDrAgainstCc().equalsIgnoreCase("")) {
                    this.setDrAgainstCC("");
                } else {
                    this.setDrAgainstCC(cbsSchemeCustAccountDetailsTO.getDrAgainstCc());
                    descDrAgainstCC();
                }
                if (cbsSchemeCustAccountDetailsTO.getClsPendingJobs() == null || cbsSchemeCustAccountDetailsTO.getClsPendingJobs().equalsIgnoreCase("")) {
                    this.setClsPendingJobs("");
                } else {
                    this.setClsPendingJobs(cbsSchemeCustAccountDetailsTO.getClsPendingJobs());
                    descClsPendingJobs();
                }
                if (cbsSchemeCustAccountDetailsTO.getClsPendingSi() == null || cbsSchemeCustAccountDetailsTO.getClsPendingSi().equalsIgnoreCase("")) {
                    this.setClsPendingSI("");
                } else {
                    this.setClsPendingSI(cbsSchemeCustAccountDetailsTO.getClsPendingSi());
                    descClsPendingSI();
                }
                if (cbsSchemeCustAccountDetailsTO.getClsPendingCc() == null || cbsSchemeCustAccountDetailsTO.getClsPendingCc().equalsIgnoreCase("")) {
                    this.setClsPendingCC("");
                } else {
                    this.setClsPendingCC(cbsSchemeCustAccountDetailsTO.getClsPendingCc());
                    descClsPendingCC();
                }
                if (cbsSchemeCustAccountDetailsTO.getClsPendingChq() == null || cbsSchemeCustAccountDetailsTO.getClsPendingChq().equalsIgnoreCase("")) {
                    this.setClsPendingChq("");
                } else {
                    this.setClsPendingChq(cbsSchemeCustAccountDetailsTO.getClsPendingChq());
                    descClsPendingChq();
                }
                if (cbsSchemeCustAccountDetailsTO.getClsPendingProxy() == null || cbsSchemeCustAccountDetailsTO.getClsPendingProxy().equalsIgnoreCase("")) {
                    this.setClsPendingProxy("");
                } else {
                    this.setClsPendingProxy(cbsSchemeCustAccountDetailsTO.getClsPendingProxy());
                    descClsPendingProxy();
                }
                if (cbsSchemeCustAccountDetailsTO.getChequeIssuedButNotAck() == null || cbsSchemeCustAccountDetailsTO.getChequeIssuedButNotAck().equalsIgnoreCase("")) {
                    this.setChequeIssuedButNotAck("");
                } else {
                    this.setChequeIssuedButNotAck(cbsSchemeCustAccountDetailsTO.getChequeIssuedButNotAck());
                    descChequeIssuedButNotAck();
                }
                if (cbsSchemeCustAccountDetailsTO.getChequeUnusable() == null || cbsSchemeCustAccountDetailsTO.getChequeUnusable().equalsIgnoreCase("")) {
                    this.setChequeUnusable("");
                } else {
                    this.setChequeUnusable(cbsSchemeCustAccountDetailsTO.getChequeUnusable());
                    descChequeUnusable();
                }
                if (cbsSchemeCustAccountDetailsTO.getChequeStoppedButNotVerified() == null || cbsSchemeCustAccountDetailsTO.getChequeStoppedButNotVerified().equalsIgnoreCase("")) {
                    this.setChequeStoppedButNotVerified("");
                } else {
                    this.setChequeStoppedButNotVerified(cbsSchemeCustAccountDetailsTO.getChequeStoppedButNotVerified());
                    descChequeStoppedButNotVerified();
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

    public void refreshCADForm() {
        this.setChequeNotAllowed("");
        this.setStChequeNotAllowed("");
        this.setChequeNotIssued("");
        this.setStChequeNotIssued("");
        this.setChequeIssuedToMinor("");
        this.setStChequeIssuedToMinormessage("");
        this.setChequeStopped("");
        this.setStChequeStopped("");
        this.setChequeCautioned("");
        this.setStChequeCautioned("");
        this.setIntroducerNotCust("");
        this.setStIntroducerNotCust("");
        this.setIntroducerNewCust("");
        this.setStIntroducerNewCust("");
        this.setEmployeesOwnAccount("");
        this.setStEmployeesOwnAccount("");
        this.setsIPerfixCharges("");
        this.setStSIPerfixCharges("");
        this.setDrAgainstCC("");
        this.setStDrAgainstCC("");
        this.setClsPendingJobs("");
        this.setStClsPendingJobs("");
        this.setClsPendingSI("");
        this.setStClsPendingSI("");
        this.setClsPendingCC("");
        this.setStClsPendingCC("");
        this.setClsPendingChq("");
        this.setStClsPendingChq("");
        this.setClsPendingProxy("");
        this.setStClsPendingProxy("");
        this.setChequeIssuedButNotAck("");
        this.setStChequeIssuedButNotAck("");
        this.setChequeUnusable("");
        this.setStChequeUnusable("");
        this.setChequeStoppedButNotVerified("");
        this.setStChequeStoppedButNotVerified("");
        tabSearch = new ArrayList<SearchCode>();
    }

    public void disableCADForm() {
        this.chequeNotAllowedDisable = true;
        this.chequeNotIssuedDisable = true;
        this.chequeIssuedToMinorDisable = true;
        this.chequeStoppedDisable = true;
        this.chequeCautionedDisable = true;
        this.introducerNotCustDisable = true;
        this.introducerNewCustDisable = true;
        this.employeesOwnAccountDisable = true;
        this.sIPerfixChargesDisable = true;
        this.drAgainstCCDisable = true;
        this.clsPendingJobsDisable = true;
        this.clsPendingSIDisable = true;
        this.clsPendingCCDisable = true;
        this.clsPendingChqDisable = true;
        this.clsPendingProxyDisable = true;
        this.chequeIssuedButNotAckDisable = true;
        this.chequeUnusableDisable = true;
        this.chequeStoppedButNotVerifiedDisable = true;
    }

    public void enableCADForm() {
        this.chequeNotAllowedDisable = false;
        this.chequeNotIssuedDisable = false;
        this.chequeIssuedToMinorDisable = false;
        this.chequeStoppedDisable = false;
        this.chequeCautionedDisable = false;
        this.introducerNotCustDisable = false;
        this.introducerNewCustDisable = false;
        this.employeesOwnAccountDisable = false;
        this.sIPerfixChargesDisable = false;
        this.drAgainstCCDisable = false;
        this.clsPendingJobsDisable = false;
        this.clsPendingSIDisable = false;
        this.clsPendingCCDisable = false;
        this.clsPendingChqDisable = false;
        this.clsPendingProxyDisable = false;
        this.chequeIssuedButNotAckDisable = false;
        this.chequeUnusableDisable = false;
        this.chequeStoppedButNotVerifiedDisable = false;
    }
}
