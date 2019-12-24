/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.adaptor;

import com.cbs.dto.NeftRtgsOutwardPojo;
import com.cbs.entity.neftrtgs.NeftOwDetails;
import java.text.SimpleDateFormat;

public class ObjectAdaptorCommon {

    static SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    static SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public static NeftOwDetails adaptToNeftOwDetailsEntity(NeftRtgsOutwardPojo pojo) throws Exception {
        try {
            NeftOwDetails neftOwDetails = new NeftOwDetails();
            neftOwDetails.setPaymentType(pojo.getPaymentType());
            neftOwDetails.setUniqueCustomerRefNo(pojo.getUniqueCustomerRefNo());
            neftOwDetails.setBeneficiaryName(pojo.getBeneficiaryName());
            neftOwDetails.setBeneficiaryCode(pojo.getBeneficiaryCode());
            neftOwDetails.setTxnAmt(pojo.getNeftAmount());
            neftOwDetails.setPaymentDate(pojo.getPaymentDate());
            neftOwDetails.setCreditAccountNo(pojo.getCreditAccountNo());
            neftOwDetails.setOutsideBankIfscCode(pojo.getBeneficiaryIfsc());
            neftOwDetails.setDebitAccountNo(pojo.getDebitAccountNo());
            neftOwDetails.setBeneficiaryEmailId(pojo.getBeneficiaryEmailId());
            neftOwDetails.setBeneficiaryMobileNo(pojo.getBeneficiaryMobileNo());
            neftOwDetails.setCmsBankRefNo(pojo.getCmsBankRefNo());
            neftOwDetails.setUtrNo(pojo.getUtrNo());
            neftOwDetails.setInstNo(pojo.getInstNo());
            neftOwDetails.setInstDate(dmy.parse(pojo.getInstDate()));
            neftOwDetails.setDt(ymd.parse(pojo.getDt()));
            neftOwDetails.setTrantime(pojo.getTranTime());
            neftOwDetails.setStatus(pojo.getStatus());
            neftOwDetails.setReason(pojo.getReason());
            neftOwDetails.setDetails(pojo.getDetails());
            neftOwDetails.setOrgBrnid(pojo.getOrgnId());
            neftOwDetails.setDestBrnid(pojo.getDestbrnId());
            neftOwDetails.setAuth(pojo.getAuth());
            neftOwDetails.setEnterBy(pojo.getEnterBy());
            neftOwDetails.setAuthby(pojo.getAuthBy());
            neftOwDetails.setChargeType(pojo.getChargeType());
            neftOwDetails.setChargeAmount(pojo.getChargeAmount());
            neftOwDetails.setFileName(pojo.getFileName());
            neftOwDetails.setSenderCommModeType(pojo.getSenderCommModeType());
            neftOwDetails.setSenderCommMode(pojo.getSenderCommMode());
            neftOwDetails.setRemmitInfo(pojo.getRemmitInfo());
            neftOwDetails.setOutwardFileName(pojo.getOutwardFileName());
            neftOwDetails.setCPSMSMessageId(pojo.getCpsmsMessageId());
            neftOwDetails.setCPSMSBatchNo(pojo.getCpsmsBatchNo());
            neftOwDetails.setCPSMSTranIdCrTranId(pojo.getCpsmsTranIdCrTranId());
            neftOwDetails.setDebitSuccessTrsno(pojo.getDebitSuccessTrsno());
            neftOwDetails.setResponseUpdateTime(pojo.getResponseUpdateTime());
            neftOwDetails.setSuccessToFailureFlag(pojo.getSuccessToFailureFlag());
            neftOwDetails.setDebitAmount(pojo.getDebitAmount());
            neftOwDetails.setTrsNo(pojo.getTrsNo());

            return neftOwDetails;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
}
