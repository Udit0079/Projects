/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo.neftrtgs.sbi;

public class BlockAItems {

    private String blockAIdentifier;
    private String bnkAppIdentifier;
    private String msgIdentifier;
    private String iOIdentifier;
    private String msgType;
    private String subMsgType;
    private String senderIFSC;
    private String receiverIFSC;
    private Integer deliveryMonitoringFlag;
    private Integer openNotificationFlag;
    private Integer nonDeliveryWarningFlag;
    private String obsolescencePeriod;
    private String msgUserReference;
    private Integer possibleDuplicateFlag;
    private String serviceIdentifier;
    private String originatingDate;
    private String originatingTime;
    private Integer testingAndTrainingFlag;
    private String sequenceNumber;
    private String filler;
    private String uniqueTransactionReference;
    private Integer rtgsPriority;
    private String blockAEndIdentifier;

    public String getiOIdentifier() {
        return iOIdentifier;
    }

    public void setiOIdentifier(String iOIdentifier) {
        this.iOIdentifier = iOIdentifier;
    }

    public String getMsgIdentifier() {
        return msgIdentifier;
    }

    public void setMsgIdentifier(String msgIdentifier) {
        this.msgIdentifier = msgIdentifier;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgUserReference() {
        return msgUserReference;
    }

    public void setMsgUserReference(String msgUserReference) {
        this.msgUserReference = msgUserReference;
    }

    public Integer getNonDeliveryWarningFlag() {
        return nonDeliveryWarningFlag;
    }

    public void setNonDeliveryWarningFlag(Integer nonDeliveryWarningFlag) {
        this.nonDeliveryWarningFlag = nonDeliveryWarningFlag;
    }

    public String getObsolescencePeriod() {
        return obsolescencePeriod;
    }

    public void setObsolescencePeriod(String obsolescencePeriod) {
        this.obsolescencePeriod = obsolescencePeriod;
    }

    public Integer getOpenNotificationFlag() {
        return openNotificationFlag;
    }

    public void setOpenNotificationFlag(Integer openNotificationFlag) {
        this.openNotificationFlag = openNotificationFlag;
    }

    public String getOriginatingDate() {
        return originatingDate;
    }

    public void setOriginatingDate(String originatingDate) {
        this.originatingDate = originatingDate;
    }

    public String getOriginatingTime() {
        return originatingTime;
    }

    public void setOriginatingTime(String originatingTime) {
        this.originatingTime = originatingTime;
    }

    public String getReceiverIFSC() {
        return receiverIFSC;
    }

    public void setReceiverIFSC(String receiverIFSC) {
        this.receiverIFSC = receiverIFSC;
    }

    public String getSenderIFSC() {
        return senderIFSC;
    }

    public void setSenderIFSC(String senderIFSC) {
        this.senderIFSC = senderIFSC;
    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getServiceIdentifier() {
        return serviceIdentifier;
    }

    public void setServiceIdentifier(String serviceIdentifier) {
        this.serviceIdentifier = serviceIdentifier;
    }

    public String getSubMsgType() {
        return subMsgType;
    }

    public void setSubMsgType(String subMsgType) {
        this.subMsgType = subMsgType;
    }

    public Integer getTestingAndTrainingFlag() {
        return testingAndTrainingFlag;
    }

    public void setTestingAndTrainingFlag(Integer testingAndTrainingFlag) {
        this.testingAndTrainingFlag = testingAndTrainingFlag;
    }

    public String getUniqueTransactionReference() {
        return uniqueTransactionReference;
    }

    public void setUniqueTransactionReference(String uniqueTransactionReference) {
        this.uniqueTransactionReference = uniqueTransactionReference;
    }

    public String getBlockAIdentifier() {
        return blockAIdentifier;
    }

    public void setBlockAIdentifier(String blockAIdentifier) {
        this.blockAIdentifier = blockAIdentifier;
    }

    public String getBnkAppIdentifier() {
        return bnkAppIdentifier;
    }

    public void setBnkAppIdentifier(String bnkAppIdentifier) {
        this.bnkAppIdentifier = bnkAppIdentifier;
    }

    public Integer getDeliveryMonitoringFlag() {
        return deliveryMonitoringFlag;
    }

    public void setDeliveryMonitoringFlag(Integer deliveryMonitoringFlag) {
        this.deliveryMonitoringFlag = deliveryMonitoringFlag;
    }

    public Integer getPossibleDuplicateFlag() {
        return possibleDuplicateFlag;
    }

    public void setPossibleDuplicateFlag(Integer possibleDuplicateFlag) {
        this.possibleDuplicateFlag = possibleDuplicateFlag;
    }

    public String getFiller() {
        return filler;
    }

    public void setFiller(String filler) {
        this.filler = filler;
    }

    public Integer getRtgsPriority() {
        return rtgsPriority;
    }

    public void setRtgsPriority(Integer rtgsPriority) {
        this.rtgsPriority = rtgsPriority;
    }

    public String getBlockAEndIdentifier() {
        return blockAEndIdentifier;
    }

    public void setBlockAEndIdentifier(String blockAEndIdentifier) {
        this.blockAEndIdentifier = blockAEndIdentifier;
    }

    public BlockAItems() {
    }

    public BlockAItems(String blockAIdentifier, String bnkAppIdentifier, String msgIdentifier,
            String iOIdentifier, String msgType, String subMsgType, String senderIFSC, String receiverIFSC,
            Integer deliveryMonitoringFlag, Integer openNotificationFlag, Integer nonDeliveryWarningFlag,
            String obsolescencePeriod, String msgUserReference, Integer possibleDuplicateFlag,
            String serviceIdentifier, String originatingDate, String originatingTime, Integer authorizationFlag,
            Integer testingAndTrainingFlag, String sequenceNumber, String filler, String uniqueTransactionReference,
            Integer rtgsPriority, String blockAEndIdentifier) {
        this.blockAIdentifier = blockAIdentifier;
        this.bnkAppIdentifier = bnkAppIdentifier;
        this.msgIdentifier = msgIdentifier;
        this.iOIdentifier = iOIdentifier;
        this.msgType = msgType;
        this.subMsgType = subMsgType;
        this.senderIFSC = senderIFSC;
        this.receiverIFSC = receiverIFSC;
        this.deliveryMonitoringFlag = deliveryMonitoringFlag;
        this.openNotificationFlag = openNotificationFlag;
        this.nonDeliveryWarningFlag = nonDeliveryWarningFlag;
        this.obsolescencePeriod = obsolescencePeriod;
        this.msgUserReference = msgUserReference;
        this.possibleDuplicateFlag = possibleDuplicateFlag;
        this.serviceIdentifier = serviceIdentifier;
        this.originatingDate = originatingDate;
        this.originatingTime = originatingTime;
        this.testingAndTrainingFlag = testingAndTrainingFlag;
        this.sequenceNumber = sequenceNumber;
        this.filler = filler;
        this.uniqueTransactionReference = uniqueTransactionReference;
        this.rtgsPriority = rtgsPriority;
        this.blockAEndIdentifier = blockAEndIdentifier;
    }

    @Override
    public String toString() {
        return (blockAIdentifier + bnkAppIdentifier + msgIdentifier + iOIdentifier + msgType + subMsgType
                + senderIFSC + receiverIFSC + deliveryMonitoringFlag.toString() + openNotificationFlag.toString()
                + nonDeliveryWarningFlag.toString() + obsolescencePeriod.toString() + msgUserReference
                + possibleDuplicateFlag.toString() + serviceIdentifier + originatingDate + originatingTime
                + testingAndTrainingFlag.toString() + sequenceNumber + filler + uniqueTransactionReference
                + rtgsPriority.toString() + blockAEndIdentifier);
    }
}
