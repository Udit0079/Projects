package com.cbs.email.service;

import com.cbs.exception.ApplicationException;
import com.cbs.sms.service.MimeType;
import com.cbs.sms.service.VelocityEngineService;
import com.cbs.utils.ServiceLocator;
import java.io.StringWriter;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

public class MailService {

    private static final Logger logger = Logger.getLogger(MailService.class);

    public void postMail(EmailType emailType, MailRequestTO mailRequestTO)
            throws ApplicationException {
        MailManager mailManager = (MailManager) ServiceLocator.getInstance().lookup("MailManagerImpl");
        MailMessage mailMessage = null;
        if (emailType == EmailType.SEND_ACCT_STMAT) {
            mailMessage = new MailMessage(mailRequestTO.getServiceType(), mailRequestTO.getCustId(),
                    mailRequestTO.getBankEmail(), mailRequestTO.getBankName(), mailRequestTO.getCustEmail(),
                    mailRequestTO.getSubject(), getMailContent(mailRequestTO, EmailType.SEND_ACCT_STMAT),
                    MimeType.HTML, mailRequestTO.getPeriodFromDt(), mailRequestTO.getPeriodToDt(), "",
                    mailRequestTO.getStmtDueDt());
            mailManager.enqueueMail(mailMessage);
            return;
        }
        throw new ApplicationException("Invalid Event Type Defined. " + emailType);
    }

    public String getMailContent(MailRequestTO mailRequestTO, EmailType emailType) throws ApplicationException {
        try {
            VelocityContext context = getContext(mailRequestTO);
            Template velocityTemplate = VelocityEngineService.getInstance().getVelocityEngine().getTemplate(
                    EmailConstant.EMAIL_TEMPLATES_PATH.substring(1) + emailType.getValue());
            StringWriter writer = new StringWriter();
            velocityTemplate.merge(context, writer);
            return writer.toString();
        } catch (Exception e) {
            logger.error("Could not get mail contents from Velocity Engine.", e);
            throw new RuntimeException();
        }
    }

    private VelocityContext getContext(MailRequestTO mailRequestTO) {
        VelocityContext context = new VelocityContext();
        if (mailRequestTO != null) {
            context.put("serviceType", mailRequestTO.getServiceType());
            context.put("custId", mailRequestTO.getCustId());
            context.put("custName", mailRequestTO.getCustName());
            context.put("custEmail", mailRequestTO.getCustId());
            context.put("bankName", mailRequestTO.getBankName());
            context.put("bankEmail", mailRequestTO.getBankEmail());
            context.put("bankPhone", mailRequestTO.getBankPhone());
            context.put("dob", mailRequestTO.getDobText());
        }
        return context;
    }
}
