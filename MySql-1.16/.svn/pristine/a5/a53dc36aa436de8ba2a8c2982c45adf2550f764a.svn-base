/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.email.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.apache.log4j.Logger;

@Stateless
@TransactionManagement(value = TransactionManagementType.BEAN)
public class MailSenderImpl implements MailSender {

    private static final Logger logger = Logger.getLogger(MailSenderImpl.class);
    @Resource(lookup = "mail/MailSession")
    private Session mailSession;
    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;

    public void postEmail(MailMessage mailMessage) {
        UserTransaction ut = context.getUserTransaction();
        try {
            String attachmentFileName = "STMT_" + "XXXXXXXXX" + mailMessage.getCustId().
                    substring(mailMessage.getCustId().length() - 1) + "_"
                    + mailMessage.getPeriodFromDt() + "_" + mailMessage.getPeriodToDt() + ".pdf";

            File custStmtFile = new File(EmailConstant.STMT_LOCATION + attachmentFileName);
            if (!custStmtFile.exists()) {
                System.out.println("Statement was not found for customer-->" + mailMessage.getCustId().trim());
                return;
            }

            MimeMessage mimeMessage = new MimeMessage(mailSession);

            mimeMessage.setFrom(new InternetAddress(mailSession.getProperty("mail.from")));
            InternetAddress[] address = {new InternetAddress(mailMessage.getToAddress())};
            mimeMessage.setRecipients(javax.mail.Message.RecipientType.TO, address);
            mimeMessage.setSubject(mailMessage.getSubject());
            mimeMessage.setSentDate(new Date());

            Multipart multipart = new MimeMultipart();
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setContent(mailMessage.getBody(), mailMessage.getMimeType().getValue());

            MimeBodyPart attachmentBodyPart = new MimeBodyPart();

            DataSource source = new FileDataSource(EmailConstant.STMT_LOCATION + attachmentFileName); //Particular customer a/c statement pdf
            attachmentBodyPart.setDataHandler(new DataHandler(source));
            attachmentBodyPart.setFileName(attachmentFileName);   //Name of the attachment file

            multipart.addBodyPart(textBodyPart);    //Body part
            multipart.addBodyPart(attachmentBodyPart);  //Attachement part

            mimeMessage.setContent(multipart);

            Transport.send(mimeMessage);
            //Logging of Email Sending
            ut.begin();
            int n = em.createNativeQuery("insert into cbs_email(service_type,customer_id,stmt_time,stmt_file_name,"
                    + "stmt_file_size,stmt_due_dt) "
                    + "values('" + mailMessage.getServiceType() + "','" + mailMessage.getCustId() + "',"
                    + "current_timestamp,'" + attachmentFileName + "'," + ((custStmtFile.length()) / 1024) + ","
                    + "'" + mailMessage.getStmtDueDt() + "')").executeUpdate();
            if (n <= 0) {
                System.out.println("Problem In Email Logging For Customer-->" + mailMessage.getCustId()
                        + ":Service Type-->" + mailMessage.getServiceType() + ":On Date-->" + new Date());
                ut.rollback();
            } else {
                ut.commit();
            }
            //Generating the backup of customer statement
            generateStmtBackup(custStmtFile, EmailConstant.STMT_BKP_LOCATION);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error In PostEmail Method-->>>>>", e);
        }
    }

    private static void generateStmtBackup(File custStmtFile, String localIwBackupDir) throws IOException {
        try {
            FileInputStream fis = new FileInputStream(custStmtFile);
            FileOutputStream fos = new FileOutputStream(localIwBackupDir + custStmtFile.getName());
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
            fos.close();
            fis.close();
            custStmtFile.delete();
        } catch (Exception ex) {
            throw new IOException(ex.getMessage());
        }
    }
}
