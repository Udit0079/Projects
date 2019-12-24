/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.ibl.util;

import com.cbs.exception.ApplicationException;
import com.sun.xml.bind.marshaller.CharacterEscapeHandler;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.commons.lang.StringEscapeUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 *
 * @author root
 */
public class IblUtil {

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

    public static String createSOAPRequest(String req) throws ApplicationException {
        StringBuilder soapReq = new StringBuilder("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">\n");
        soapReq.append("<soapenv:Header/>\n");
        soapReq.append("<soapenv:Body>\n");

        soapReq.append(req);
        soapReq.append("\n");
        soapReq.append("</soapenv:Body>\n");
        soapReq.append("</soapenv:Envelope>");

        return soapReq.toString();
    }

    public static Document convertStringToDocument(String xmlStr) throws ApplicationException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;

            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlStr)));
            return doc;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    public static String getXmlString(Object reqMsg) throws ApplicationException {
        try {
            StringWriter writer = new StringWriter();
            JAXBContext jaxbContext = JAXBContext.newInstance(reqMsg.getClass());

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty("jaxb.fragment", Boolean.TRUE);
            jaxbMarshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new IBLNamespaceMapper());
            jaxbMarshaller.setProperty("com.sun.xml.bind.characterEscapeHandler",
                    new CharacterEscapeHandler() {
                        @Override
                        public void escape(char[] ch, int start, int length,
                                boolean isAttVal, Writer writer)
                        throws IOException {
                            writer.write(ch, start, length);
                        }
                    });
            jaxbMarshaller.marshal(reqMsg, writer);
            return writer.toString();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public static String getXmlStringWithoutPrifix(Object reqMsg) throws ApplicationException {
        try {
            StringWriter writer = new StringWriter();
            JAXBContext jaxbContext = JAXBContext.newInstance(reqMsg.getClass());

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty("jaxb.fragment", Boolean.TRUE);
            jaxbMarshaller.marshal(reqMsg, writer);
            return writer.toString();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public static String createPymtReq(String custId, String pymtInputXml) throws ApplicationException {
        StringBuilder req = new StringBuilder("<tem:ProcessTxnInXml>\n<tem:strCustId>");
        req.append(custId);
        req.append("</tem:strCustId>\n<tem:strInputTxn><![CDATA[");
        req.append(pymtInputXml);
        req.append("]]></tem:strInputTxn>\n</tem:ProcessTxnInXml>");
        return req.toString();
    }

    public static String createPymtEnqReq(String pymtInputXml) throws ApplicationException {
        
         StringBuilder req = new StringBuilder("<tem:GetTxnResponseInXml>\n<tem:strInput><![CDATA[");
         req.append(pymtInputXml);
         req.append("]]></tem:strInput>\n</tem:GetTxnResponseInXml>");
         return req.toString();
    }

    public static String createBalEnqReq(String pymtInputXml) throws ApplicationException {
        StringBuilder req = new StringBuilder("<tem:GetAccBalance>\n<tem:strInput><![CDATA[");
        req.append(pymtInputXml);
        req.append("]]></tem:strInput>\n</tem:GetAccBalance>");
        return req.toString();
    }

    public static String createAcctStmtReq(String pymtInputXml) throws ApplicationException {
        StringBuilder req = new StringBuilder("<tem:GetStatment><tem:strInput><![CDATA[");
        req.append(pymtInputXml);
        req.append("]]></tem:strInput></tem:GetStatment>");
        return req.toString();
    }

    public static String createIblWsUrl(String baseUrl, String clientId, String cleintSecret) throws ApplicationException {
        return baseUrl + "?client_id=" + clientId + "&client_secret=" + cleintSecret;
    }

}
