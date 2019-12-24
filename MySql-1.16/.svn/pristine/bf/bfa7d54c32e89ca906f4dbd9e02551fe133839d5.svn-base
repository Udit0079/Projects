/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.listener;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.ckycr.CkycrProcessMgmtFacadeRemote;
import com.cbs.facade.clg.h2h.ClgH2hMgmtFacadeRemote;
import com.cbs.facade.clg.h2h.ClgH2hVfsMgmtFacadeRemote;
import com.cbs.facade.cpsms.FcraCpsmsMgmtFacadeRemote;
import com.cbs.facade.email.EmailMgmtFacadeRemote;
import com.cbs.facade.neftrtgs.H2HMgmtFacadeRemote;
import com.cbs.facade.npci.h2h.H2HNpciMgmtFacadeRemote;
import com.cbs.facade.sms.TimerServiceFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import java.util.Date;

public class SiplScheduler {

    TimerServiceFacadeRemote facade;
    H2HMgmtFacadeRemote h2hFacade;
    CkycrProcessMgmtFacadeRemote ckycrFacade;
    H2HNpciMgmtFacadeRemote npciH2hRemote;
    ClgH2hMgmtFacadeRemote ctsAutoRemote;
    ClgH2hVfsMgmtFacadeRemote ctsVfsRemote;
    EmailMgmtFacadeRemote emailRemote;
    FcraCpsmsMgmtFacadeRemote fcraCpsmsRemote;
    private boolean isSMSModuleOn;
    private boolean isH2HModuleOn;
    private boolean isCkycrModuleOn;
    private boolean isNpciH2HModuleOn;
    private boolean isAutoCtsOn;
    private boolean isAutoEmailOn;
    private boolean isFcraCpsmsOn;
    private boolean isEODSmsOn;

    public SiplScheduler() {
        try {
            facade = (TimerServiceFacadeRemote) ServiceLocator.getInstance().lookup("TimerServiceFacade");
            h2hFacade = (H2HMgmtFacadeRemote) ServiceLocator.getInstance().lookup("H2HMgmtFacade");
            ckycrFacade = (CkycrProcessMgmtFacadeRemote) ServiceLocator.getInstance().lookup("CkycrProcessMgmtFacade");
            npciH2hRemote = (H2HNpciMgmtFacadeRemote) ServiceLocator.getInstance().lookup("H2HNpciMgmtFacade");
            ctsAutoRemote = (ClgH2hMgmtFacadeRemote) ServiceLocator.getInstance().lookup("ClgH2hMgmtFacade");
            ctsVfsRemote = (ClgH2hVfsMgmtFacadeRemote) ServiceLocator.getInstance().lookup("ClgH2hVfsMgmtFacade");
            emailRemote = (EmailMgmtFacadeRemote) ServiceLocator.getInstance().lookup("EmailMgmtFacade");
            fcraCpsmsRemote = (FcraCpsmsMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FcraCpsmsMgmtFacade");
            CbsUtil.newGlPattern = facade.isNewGl();
            isSMSModuleOn = facade.isAtmSmsModuleOn();
            isH2HModuleOn = h2hFacade.isH2HModuleOn();
            isCkycrModuleOn = ckycrFacade.isModuleOn("CKYCR");
            isNpciH2HModuleOn = ckycrFacade.isModuleOn("NPCI-H2H");
            isAutoCtsOn = ckycrFacade.isModuleOn("AUTO-CTS");
            isAutoEmailOn = ckycrFacade.isModuleOn("AUTO-EMAIL");
            isFcraCpsmsOn = ckycrFacade.isModuleOn("FCRA-CPSMS");
            isEODSmsOn = ckycrFacade.isModuleOn("EOD-SMS");
        } catch (Exception ex) {
            System.out.println("Problem in Creating instance of Scheduler");
            ex.printStackTrace();
        }
    }

    public void smsSender() {
        try {
            System.out.println(new Date() + ": Checking SMS Alert Module is on or not..............");
//            if (isSMSModuleOn) {
                System.out.println(new Date() + ": SMS Alert Module is on. Executing SMS Sender Service..............");
                facade.sendMessage();
                System.out.println("SMS Sender Service successfully executed..............");
//            }
        } catch (Exception ex) {
            System.out.println("Execution of SMS Sender Service Failed.............." + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /*Code for Host to Host Neft-Rtgs*********/
    public void neftRtgsScheduler() {
        try {
            System.out.println(new Date() + ": Checking H2H Engine is on or not..............");
            if (isH2HModuleOn) {
                System.out.println(new Date() + ": H2H Engine is on. Executing Host to Host Engine for NEFT-RTGS..............");
                h2hFacade.eftProcess();
                System.out.println(new Date() + ": Host to Host Engine successfully executed..............");
            }
        } catch (ApplicationException e) {
            System.out.println("Execution of Host to Host Engine Failed.............." + e.getMessage());
            e.printStackTrace();
        }
    }

    public void ckycrScheduler() {
        try {
            System.out.println(new Date() + ": Checking CKYCR Engine is on or not..............");
            if (isCkycrModuleOn) {
                System.out.println(new Date() + ": CKYCR Engine is on. Executing Host to Host Engine for CKYCR..............");
                ckycrFacade.ckycrProcess();
                System.out.println(new Date() + ": CKYCR Engine successfully executed..............");
            }
        } catch (Exception e) {
            System.out.println("Execution of CKYCR Engine Failed.............." + e.getMessage());
            e.printStackTrace();
        }
    }

    public void npciH2hScheduler() {
        try {
            System.out.println(new Date() + ": Checking NPCI H2H Engine is on or not..............");
            if (isNpciH2HModuleOn) {
                System.out.println(new Date() + ": NPCI H2H Engine is on. Executing Host to Host Engine for NPCI..............");
                npciH2hRemote.npciH2HProcess();
                System.out.println(new Date() + ": NPCI H2H Engine successfully executed..............");
            }
        } catch (Exception e) {
            System.out.println("Execution of NPCI H2H Engine Failed.............." + e.getMessage());
            e.printStackTrace();
        }
    }

    public void autoCtsScheduler() {
        try {
            System.out.println(new Date() + ": Checking AUTO CTS Engine is on or not..............");
            if (isAutoCtsOn) {
                System.out.println(new Date() + ": AUTO CTS Engine is on. Executing AUTO CTS Engine ..............");
//                ctsAutoRemote.processUploadCts();
                ctsVfsRemote.processUploadCts();
                System.out.println(new Date() + ": AUTO CTS Engine successfully executed..............");
            }
        } catch (Exception e) {
            System.out.println("Execution of AUTO CTS Failed.............." + e.getMessage());
            e.printStackTrace();
        }
    }

    public void autoEmailScheduler() {
        try {
            System.out.println(new Date() + ": Checking EMAIL Engine is on or not..............");
            if (isAutoEmailOn) {
                System.out.println(new Date() + ": AUTO EMAIL Engine is on. Executing AUTO EMAIL Engine ..............");
                emailRemote.emailProcess();
                System.out.println(new Date() + ": AUTO EMAIL Engine successfully executed..............");
            }
        } catch (Exception e) {
            System.out.println("Execution of AUTO EMAIL Failed.............." + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void autoEODSmsScheduler() {
        try {
            System.out.println(new Date() + ": Checking EOD SMS Engine is on or not..............");
            if (isEODSmsOn) {
                System.out.println(new Date() + ": EOD SMS Engine is on. Executing EOD SMS Engine ..............");
                facade.sendEODMessage();
                System.out.println(new Date() + ": EOD SMS Engine successfully executed..............");
            }
        } catch (Exception e) {
            System.out.println("Execution of EOD SMS Failed.............." + e.getMessage());
            e.printStackTrace();
        }
    }

    //Schedulers Regarding FCRA
    public void fcraQuickAccountValScheduler() {
        try {
            System.out.println(new Date() + ": Checking FCRA Quick Account Validation Engine is on or not..............");
            if (isFcraCpsmsOn) {
                System.out.println(new Date() + ": AUTO FCRA Quick Account Validation Engine is on. Executing AUTO FCRA Engine ..............");
                fcraCpsmsRemote.accValQuickResponse();
                System.out.println(new Date() + ": AUTO FCRA Quick Account Validation Engine successfully executed..............");
            }
        } catch (Exception e) {
            System.out.println("Execution of AUTO FCRA Quick Account Validation Failed.............." + e.getMessage());
            e.printStackTrace();
        }
    }

    public void fcraRegularAccountValScheduler() {
        try {
            System.out.println(new Date() + ": Checking FCRA Regular Account Validation Engine is on or not..............");
            if (isFcraCpsmsOn) {
                System.out.println(new Date() + ": AUTO FCRA Regular Account Validation Engine is on. Executing AUTO FCRA Engine ..............");
                fcraCpsmsRemote.accValRegularResponse();
                System.out.println(new Date() + ": AUTO FCRA Regular Account Validation Engine successfully executed..............");
            }
        } catch (Exception e) {
            System.out.println("Execution of AUTO FCRA Regular Account Validation Failed.............." + e.getMessage());
            e.printStackTrace();
        }
    }

    public void fcraTxnScheduler() {
        try {
            System.out.println(new Date() + ": Checking FCRA Transaction Engine is on or not..............");
            if (isFcraCpsmsOn) {
                System.out.println(new Date() + ": AUTO FCRA Transaction Engine is on. Executing AUTO FCRA Engine ..............");
                fcraCpsmsRemote.generateDailyTransactionDetail();
                System.out.println(new Date() + ": AUTO FCRA Transaction Engine successfully executed..............");
            }
        } catch (Exception e) {
            System.out.println("Execution of AUTO FCRA Transaction Failed.............." + e.getMessage());
            e.printStackTrace();
        }
    }

    public void fcraTxnHistoryScheduler() {
        try {
            System.out.println(new Date() + ": Checking FCRA Txn History Engine is on or not..............");
            if (isFcraCpsmsOn) {
                System.out.println(new Date() + ": AUTO FCRA Txn History Engine is on. Executing AUTO FCRA Engine ..............");
                fcraCpsmsRemote.generateTransactionHistoryDetail();
                System.out.println(new Date() + ": AUTO FCRA Txn History Engine successfully executed..............");
            }
        } catch (Exception e) {
            System.out.println("Execution of AUTO FCRA Txn History Failed.............." + e.getMessage());
            e.printStackTrace();
        }
    }
}
