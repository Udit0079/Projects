/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.neftrtgs.yes.api;

import com.cbs.exception.ApplicationException;
import com.cbs.ibl.util.IblUtil;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import org.apache.commons.lang.StringEscapeUtils;
import org.w3c.dom.Document;

public class YesUtil {

    public static String createYesWsUrl(String baseUrl, String clientId, String cleintSecret) throws ApplicationException {
        return baseUrl + "?client_id=" + clientId + "&client_secret=" + cleintSecret;
    }

    public static String createSOAPRequest(com.cbs.neftrtgs.yes.api.PaymentRequest payReq) throws Exception {
        MessageFactory factory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
        SOAPMessage soapMsg = factory.createMessage();
        SOAPPart part = soapMsg.getSOAPPart();
        SOAPEnvelope envelope = part.getEnvelope();

        envelope.removeAttribute("xmlns:env");
        envelope.setPrefix("soap");
        envelope.addNamespaceDeclaration("ser", "http://www.quantiguous.com/services");

        SOAPHeader header = envelope.getHeader();
        header.setPrefix("soap");
        SOAPBody body = envelope.getBody();
        body.setPrefix("soap");

        SOAPBodyElement element = body.addBodyElement(envelope.createQName("remit", "ser"));
        element.addChildElement("version", "ser").addTextNode(payReq.getVersion());
        element.addChildElement("uniqueRequestNo", "ser").addTextNode(payReq.getUniqueRequestNo());
        element.addChildElement("partnerCode", "ser").addTextNode(payReq.getPartnerCode());
        element.addChildElement("customerID", "ser").addTextNode(payReq.getCustomerId());
        element.addChildElement("debitAccountNo", "ser").addTextNode(payReq.getDebitAccountNo());
        element.addChildElement("remitterAccountNo", "ser").addTextNode(payReq.getRemitterAccountNo());
        element.addChildElement("remitterIFSC", "ser").addTextNode(payReq.getRemitterIFSC());
        if (!payReq.getTransferType().equalsIgnoreCase("FT")) {
            element.addChildElement("remitterName", "ser").addTextNode(payReq.getRemitterName());
        }
        element.addChildElement("beneficiaryName", "ser").addTextNode(payReq.getBeneficiaryName());
        element.addChildElement("beneficiaryAccountNo", "ser").addTextNode(payReq.getBeneficiaryAccountNo());
        element.addChildElement("beneficiaryIFSC", "ser").addTextNode(payReq.getBeneficiaryIFSC());
        element.addChildElement("transferType", "ser").addTextNode(payReq.getTransferType());
        element.addChildElement("transferCurrencyCode", "ser").addTextNode(payReq.getTransferCurrencyCode());
        element.addChildElement("transferAmount", "ser").addTextNode(payReq.getTransferAmount());
        element.addChildElement("remitterToBeneficiaryInfo", "ser").addTextNode(payReq.getRemitterToBeneficiaryInfo());

//        soapMsg.writeTo(System.out);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        soapMsg.writeTo(out);
        return new String(out.toByteArray());
    }

    public static Document executeWSOperation(String wsUrl, String operation, String inputReq) throws ApplicationException {
        try {
            URL url = new URL(wsUrl);
            URLConnection connection = url.openConnection();
            HttpURLConnection httpConn = (HttpURLConnection) connection;

            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte[] buffer = inputReq.getBytes();
            bout.write(buffer);
            byte[] b = bout.toByteArray();
            String SOAPAction = "http://tempuri.org/IDomesticPayService/" + operation;

            httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            httpConn.setRequestProperty("SOAPAction", SOAPAction);

            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);

            OutputStream out = httpConn.getOutputStream();
            out.write(b);
            out.close();
            InputStreamReader isr = new InputStreamReader(httpConn.getInputStream());
            BufferedReader in = new BufferedReader(isr);

            String responseString = "";
            String outputString = "";

            while ((responseString = in.readLine()) != null) {
                outputString = outputString + responseString;
            }
            String xmlStr = StringEscapeUtils.unescapeXml(outputString);
            return IblUtil.convertStringToDocument(xmlStr);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
}
