/**
 *
 * @author Navneet Goyal
 */
package com.cbs.web.controller.report;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.cbs.web.controller.BaseBean;
import com.hrms.web.utils.WebUtil;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public final class ReportPagePopUp extends BaseBean {

    private String message;
    private String reportName;
    public static int PORT = 2001;
    public boolean connected;
    private Socket socketTCP;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public ReportPagePopUp() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        setTodayDate(sdf.format(date));
        setUserName(getHttpRequest().getRemoteUser());
        if (getHttpRequest().getSession().getAttribute("ReportName") != null) {
            setReportName(getHttpRequest().getSession().getAttribute("ReportName").toString());
        }
        setMessage("");
    }

    public void printReport() {
        try {
            if (getHttpSession().getAttribute("TextReport") != null) {
                //String remoteHost = getHttpRequest().getRemoteHost();
                String remoteHost = WebUtil.getClientIP(getHttpRequest());
                byte[] bytes = ((ByteArrayOutputStream) getHttpSession().getAttribute("TextReport")).toByteArray();
                socketTCP = new Socket(remoteHost, PORT);
                objectOutputStream = new ObjectOutputStream(socketTCP.getOutputStream());
                objectInputStream = new ObjectInputStream(socketTCP.getInputStream());
                writeObject(bytes);
            }
        } catch (Exception e) {
            if (e.getClass().getSimpleName().equalsIgnoreCase("ConnectException")) {
                setMessage("Printer is not connected !!");
            } else {
                setMessage("System error occurred");
            }
        } finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (Exception e) {
                setMessage(e.getLocalizedMessage());
            }
        }
    }

//    public String exitAction() throws IOException {
//        return reportName;
//    }
    public boolean writeObject(byte[] byteArr) {
        try {
            Vector writeVector = new Vector();
            writeVector.add("TXT");
            writeVector.add(byteArr);
            objectOutputStream.writeObject(writeVector);
            objectOutputStream.flush();
            return true;
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
            return false;
        }
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

