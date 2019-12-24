/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.adaptor;

import com.cbs.dto.AccountMasterTO;
import com.cbs.dto.neftrtgs.NeftOwDetailsTO;
import com.cbs.entity.neftrtgs.AccountMaster;
import com.cbs.entity.neftrtgs.NeftOwDetails;

/**
 *
 * @author sipl
 */
public class ObjectAdaptorAccount {

    public static AccountMaster adaptToAccountMasterEntity(AccountMasterTO accountMasterTo) {
        AccountMaster acctMst = new AccountMaster();

        acctMst.setACNo(accountMasterTo.getaCNo());
        acctMst.setCustname(accountMasterTo.getCustname());
        acctMst.setOpeningDt(accountMasterTo.getOpeningDt());

        acctMst.setIntroAccno(accountMasterTo.getIntroAccno());
        acctMst.setIntDeposit(accountMasterTo.getIntDeposit());
        acctMst.setClosingDate(accountMasterTo.getClosingDate());

        acctMst.setClosingBal(accountMasterTo.getClosingBal());
        acctMst.setOperMode(accountMasterTo.getOperMode());
        acctMst.setJtName1(accountMasterTo.getJtName1());

        acctMst.setJtName2(accountMasterTo.getJtName2());
        acctMst.setJtName3(accountMasterTo.getJtName3());
        acctMst.setJtName4(accountMasterTo.getJtName4());

        acctMst.setLastOpDate(accountMasterTo.getLastOpDate());
        acctMst.setAccStatus(accountMasterTo.getAccStatus());
        acctMst.setOptstatus(accountMasterTo.getOptstatus());

        acctMst.setOrgnCode(accountMasterTo.getOrgnCode());
        acctMst.setNomination(accountMasterTo.getNomination());
        acctMst.setODLimit(accountMasterTo.getoDLimit());

        acctMst.setEnteredBy(accountMasterTo.getEnteredBy());
        acctMst.setAuthBy(accountMasterTo.getAuthBy());
        acctMst.setLastUpdateDt(accountMasterTo.getLastUpdateDt());

        acctMst.setAccttype(accountMasterTo.getAccttype());
        acctMst.setRelatioship(accountMasterTo.getRelatioship());
        acctMst.setLedgerFolioNo(accountMasterTo.getLedgerFolioNo());

        acctMst.setInstruction(accountMasterTo.getInstruction());
        acctMst.setPenalty(accountMasterTo.getPenalty());
        acctMst.setMinbal(accountMasterTo.getMinbal());

        acctMst.setRdmatdate(accountMasterTo.getRdmatdate());
        acctMst.setRdInstal(accountMasterTo.getRdInstal());
        acctMst.setChequebook(accountMasterTo.getChequebook());

        acctMst.setAdhoclimit(accountMasterTo.getAdhoclimit());
        acctMst.setAdhoctilldt(accountMasterTo.getAdhoctilldt());
        acctMst.setAdhocinterest(accountMasterTo.getAdhocinterest());

        acctMst.setCreationDt(accountMasterTo.getCreationDt());
        acctMst.setName(accountMasterTo.getName());
        acctMst.setCustType(accountMasterTo.getCustType());

        acctMst.setPenalty1(accountMasterTo.getPenalty1());
        acctMst.setTdsflag(accountMasterTo.getTdsflag());

        //  acctMst.setBaseBranch(accountMasterTo.getBaseBranch());

        return acctMst;
    }

    public static AccountMasterTO adaptToAccountMasterTO(AccountMaster accountMaster) {
        AccountMasterTO accMasterTo = new AccountMasterTO();

        accMasterTo.setaCNo(accountMaster.getACNo());
        accMasterTo.setCustname(accountMaster.getCustname());
        accMasterTo.setOpeningDt(accountMaster.getOpeningDt());

        accMasterTo.setIntroAccno(accountMaster.getIntroAccno());
        accMasterTo.setIntDeposit(accountMaster.getIntDeposit());
        accMasterTo.setClosingDate(accountMaster.getClosingDate());

        accMasterTo.setClosingBal(accountMaster.getClosingBal());
        accMasterTo.setOperMode(accountMaster.getOperMode());
        accMasterTo.setJtName1(accountMaster.getJtName1());

        accMasterTo.setJtName2(accountMaster.getJtName2());
        accMasterTo.setJtName3(accountMaster.getJtName3());
        accMasterTo.setJtName4(accountMaster.getJtName4());

        accMasterTo.setLastOpDate(accountMaster.getLastOpDate());
        accMasterTo.setAccStatus(accountMaster.getAccStatus());
        accMasterTo.setOptstatus(accountMaster.getOptstatus());

        accMasterTo.setOrgnCode(accountMaster.getOrgnCode());
        accMasterTo.setNomination(accountMaster.getNomination());
        accMasterTo.setoDLimit(accountMaster.getODLimit());

        accMasterTo.setEnteredBy(accountMaster.getEnteredBy());
        accMasterTo.setAuthBy(accountMaster.getAuthBy());
        accMasterTo.setLastUpdateDt(accountMaster.getLastUpdateDt());

        accMasterTo.setAccttype(accountMaster.getAccttype());
        accMasterTo.setRelatioship(accountMaster.getRelatioship());
        accMasterTo.setLedgerFolioNo(accountMaster.getLedgerFolioNo());

        accMasterTo.setInstruction(accountMaster.getInstruction());
        accMasterTo.setPenalty(accountMaster.getPenalty());
        accMasterTo.setMinbal(accountMaster.getMinbal());

        accMasterTo.setRdmatdate(accountMaster.getRdmatdate());
        accMasterTo.setRdInstal(accountMaster.getRdInstal());
        accMasterTo.setChequebook(accountMaster.getChequebook());

        accMasterTo.setAdhoclimit(accountMaster.getAdhoclimit());
        accMasterTo.setAdhoctilldt(accountMaster.getAdhoctilldt());
        accMasterTo.setAdhocinterest(accountMaster.getAdhocinterest());

        accMasterTo.setCreationDt(accountMaster.getCreationDt());
        accMasterTo.setName(accountMaster.getName());
        accMasterTo.setCustType(accountMaster.getCustType());

        accMasterTo.setPenalty1(accountMaster.getPenalty1());
        accMasterTo.setTdsflag(accountMaster.getTdsflag());

        //   accMasterTo.setBaseBranch(accountMaster.getBaseBranch());        

        return accMasterTo;
    }

    /**
     * 
     * @param neftOwDetailsTO
     * @return 
     */
    public static NeftOwDetails adaptToNeftOwDetailsEntity(NeftOwDetailsTO neftOwDetailsTO) {
        NeftOwDetails neftOwDetails = new NeftOwDetails();

        neftOwDetails.setPaymentType(neftOwDetailsTO.getPaymentType());
        neftOwDetails.setUniqueCustomerRefNo(neftOwDetailsTO.getUniqueCustomerRefNo());
        neftOwDetails.setBeneficiaryName(neftOwDetailsTO.getBeneficiaryName());
        neftOwDetails.setBeneficiaryCode(neftOwDetailsTO.getBeneficiaryCode());
        neftOwDetails.setTxnAmt(neftOwDetailsTO.getTxnAmt());
        neftOwDetails.setPaymentDate(neftOwDetailsTO.getPaymentDate());
        neftOwDetails.setCreditAccountNo(neftOwDetailsTO.getCreditAccountNo());
        neftOwDetails.setOutsideBankIfscCode(neftOwDetailsTO.getOutsideBankIfscCode());
        neftOwDetails.setDebitAccountNo(neftOwDetailsTO.getDebitAccountNo());
        neftOwDetails.setBeneficiaryEmailId(neftOwDetailsTO.getBeneficiaryEmailId());
        neftOwDetails.setBeneficiaryMobileNo(neftOwDetailsTO.getBeneficiaryMobileNo());
        neftOwDetails.setCmsBankRefNo(neftOwDetailsTO.getCmsBankRefNo());
        neftOwDetails.setUtrNo(neftOwDetailsTO.getUtrNo());
        neftOwDetails.setDt(neftOwDetailsTO.getDt());
        neftOwDetails.setTrantime(neftOwDetailsTO.getTrantime());
        neftOwDetails.setStatus(neftOwDetailsTO.getStatus());
        neftOwDetails.setReason(neftOwDetailsTO.getReason());
        neftOwDetails.setDetails(neftOwDetailsTO.getDetails());
        neftOwDetails.setOrgBrnid(neftOwDetailsTO.getOrgBrnid());
        neftOwDetails.setDestBrnid(neftOwDetailsTO.getDestBrnid());
        neftOwDetails.setAuth(neftOwDetailsTO.getAuth());
        neftOwDetails.setEnterBy(neftOwDetailsTO.getEnterBy());
        neftOwDetails.setAuthby(neftOwDetailsTO.getAuthby());

        return neftOwDetails;
    }

    /**
     * 
     * @param neftOwDetails
     * @return 
     */
    public static NeftOwDetailsTO adaptToMbSubscriberTabTO(NeftOwDetails neftOwDetails) {
        NeftOwDetailsTO neftOwDetailsTO = new NeftOwDetailsTO();

        neftOwDetailsTO.setPaymentType(neftOwDetails.getPaymentType());
        neftOwDetailsTO.setUniqueCustomerRefNo(neftOwDetails.getUniqueCustomerRefNo());
        neftOwDetailsTO.setBeneficiaryName(neftOwDetails.getBeneficiaryName());
        neftOwDetailsTO.setBeneficiaryCode(neftOwDetails.getBeneficiaryCode());
        neftOwDetailsTO.setTxnAmt(neftOwDetails.getTxnAmt());
        neftOwDetailsTO.setPaymentDate(neftOwDetails.getPaymentDate());
        neftOwDetailsTO.setCreditAccountNo(neftOwDetails.getCreditAccountNo());
        neftOwDetailsTO.setOutsideBankIfscCode(neftOwDetails.getOutsideBankIfscCode());
        neftOwDetailsTO.setDebitAccountNo(neftOwDetails.getDebitAccountNo());
        neftOwDetailsTO.setBeneficiaryEmailId(neftOwDetails.getBeneficiaryEmailId());
        neftOwDetailsTO.setBeneficiaryMobileNo(neftOwDetails.getBeneficiaryMobileNo());
        neftOwDetailsTO.setCmsBankRefNo(neftOwDetails.getCmsBankRefNo());
        neftOwDetailsTO.setUtrNo(neftOwDetails.getUtrNo());
        neftOwDetailsTO.setDt(neftOwDetails.getDt());
        neftOwDetailsTO.setTrantime(neftOwDetails.getTrantime());
        neftOwDetailsTO.setStatus(neftOwDetails.getStatus());
        neftOwDetailsTO.setReason(neftOwDetails.getReason());
        neftOwDetailsTO.setDetails(neftOwDetails.getDetails());
        neftOwDetailsTO.setOrgBrnid(neftOwDetails.getOrgBrnid());
        neftOwDetailsTO.setDestBrnid(neftOwDetails.getDestBrnid());
        neftOwDetailsTO.setAuth(neftOwDetails.getAuth());
        neftOwDetailsTO.setEnterBy(neftOwDetails.getEnterBy());
        neftOwDetailsTO.setAuthby(neftOwDetails.getAuthby());

        return neftOwDetailsTO;
    }
}
