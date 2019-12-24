package com.cbs.web.pojo.ho;

import com.hrms.web.utils.WebUtil;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class PopUpReportShare {

    private String todayDate;
    private String userName;
    private String reportName;
    public static int PORT = 2001;
    public boolean connected;
    private Socket socketTCP;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public PopUpReportShare() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        setTodayDate(sdf.format(date));
        setUserName(getRequest().getRemoteUser());
        if (getRequest().getSession().getAttribute("ReportName") != null) {
            setReportName(getRequest().getSession().getAttribute("ReportName").toString());
        }
    }

    public void printReport() {
        try {
            if (getRequest().getSession().getAttribute("TextReport") != null) {
                //String remoteHost = getRequest().getRemoteHost();
                String remoteHost = WebUtil.getClientIP(getRequest());
                byte[] bytes = ((ByteArrayOutputStream) getRequest().getSession().getAttribute("TextReport")).toByteArray();
                socketTCP = new Socket(remoteHost, PORT);
                System.out.println("Before Getting OutPutStream From Server>>>>>>>>>");
                objectOutputStream = new ObjectOutputStream(socketTCP.getOutputStream());
                System.out.println("Before Getting InPutStream From Server>>>>>>>>>");
                objectInputStream = new ObjectInputStream(socketTCP.getInputStream());
                System.out.println("After Getting InPutStream From Server>>>>>>>>>");
                writeObject(bytes);
                Vector vect = (Vector) readObject();
                System.out.println("Result = " + vect.elementAt(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                objectOutputStream.close();
                objectInputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void exitAction() {
        try {
//            getRequest().getSession().removeAttribute("datagrid");
//            getRequest().getSession().removeAttribute("bankName");
//            getRequest().getSession().removeAttribute("branchAddress");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object readObject() {
        try {
            Object readObject = objectInputStream.readObject();
            return readObject;
        } catch (Exception e) {
            if (!socketTCP.isClosed()) {
                System.out.println("SOCKET CLOSED FROM READ OBJECT");
            }
            return null;
        }
    }

    public boolean writeObject(byte[] byteArr) {
        try {
            Vector writeVector = new Vector();
            writeVector.add("TXT");
            writeVector.add(byteArr);
            objectOutputStream.writeObject(writeVector);
            objectOutputStream.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
