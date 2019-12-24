package com.cbs.sms.service;

import com.cbs.dto.sms.SmsRequestTO;
import java.io.StringWriter;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import com.cbs.exception.ApplicationException;
import com.cbs.utils.ServiceLocator;

public class SmsService {

    private static String SMS_TEMPLATES_PATH = "";

    public SmsService() {
        try {
          //  String templateType = PropertyContainer.getProperties().getProperty("msg.template");
            SMS_TEMPLATES_PATH  = "resources/templates/sms/" + PropertyContainer.getProperties().getProperty("sms.template.path") +"/";
//            if (templateType.equalsIgnoreCase("hi")) {
//                SMS_TEMPLATES_PATH = "resources/templates/sms/hi/";
//            } else if (templateType.equalsIgnoreCase("en")) {
//                SMS_TEMPLATES_PATH = "resources/templates/sms/en/";
//            }
        } catch (Exception ex) {
            System.out.println("Problem in sms properties file initilization");
        }
    }

    /**
     *
     * @param smsRequestTO
     * @throws ApplicationException
     */
    public void postSms(SmsRequestTO smsRequestTO) throws ApplicationException {
        try {
            SmsManager smsManager = (SmsManager) ServiceLocator.getInstance().lookup("SmsManagerImpl");
            SmsMessage smsMessage = null;
            smsMessage = new SmsMessage(smsRequestTO.getMessageType(), smsRequestTO.getActualAcNo(), smsRequestTO.getMobile(),
                    getSmsContent(smsRequestTO, smsRequestTO.getSmsTemplate()), MimeType.TEXT,
                    smsRequestTO.getModuleName(), smsRequestTO.getUserMsgId(), smsRequestTO.getUserName());
            smsManager.enqueueMail(smsMessage);
            System.out.println("Sms has been enqueue from postSms() method in SmsService.");
        } catch (Exception ex) {
            System.out.println("SmsService postSms() method error::" + ex.getMessage());
            throw new ApplicationException(ex.getMessage());
        }
    }

    /**
     *
     * @param smsRequestTO
     * @param smsType
     * @return
     * @throws ApplicationException
     */
    public String getSmsContent(SmsRequestTO smsRequestTO, SmsType smsType) throws ApplicationException {
        try {
            VelocityContext context = getContext(smsRequestTO);
            Template velocityTemplate = VelocityEngineService.getInstance().getVelocityEngine().
                    getTemplate(SMS_TEMPLATES_PATH + smsType.getValue());
            StringWriter writer = new StringWriter();
            velocityTemplate.merge(context, writer);
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     *
     * @param smsRequestTO
     * @return
     */
    private VelocityContext getContext(SmsRequestTO smsRequestTO) {

        VelocityContext context = new VelocityContext();
        if (smsRequestTO != null) {
            context.put("smsTemplate", smsRequestTO.getSmsTemplate());
            context.put("acno", smsRequestTO.getAcNo());
            context.put("amount", smsRequestTO.getAmount());
            context.put("tranType", smsRequestTO.getTranType());
            context.put("ty", smsRequestTO.getTy());
            context.put("mobile", smsRequestTO.getMobile());
            context.put("bankName", smsRequestTO.getBankName());
            context.put("txnDate", smsRequestTO.getTxnDt());
            context.put("balance", smsRequestTO.getBalance());
            context.put("msgType", smsRequestTO.getMessageType());
            context.put("actualAcNo", smsRequestTO.getActualAcNo());
            context.put("promoMsg", smsRequestTO.getPromoMsg());
            context.put("pinNo", smsRequestTO.getPin());
            context.put("pullCode", smsRequestTO.getPullCode());
            context.put("services", smsRequestTO.getServices());
            context.put("miniStatement", smsRequestTO.getMiniStatement());
            context.put("firstCheque", smsRequestTO.getFirstCheque());
            context.put("lastCheque", smsRequestTO.getLastCheque());
            context.put("userName", smsRequestTO.getUserName());
            context.put("details", smsRequestTO.getDetails());
            context.put("pendingBalance", smsRequestTO.getPendingBalance());
        }
        return context;
    }
}
