/**
 *
 * @author Navneet Goyal
 */
package com.cbs.web.controller.report;

import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.hrms.web.utils.WebUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

public final class ReportPage extends BaseBean {

    private String message;
    private String reportName;
    public static int PORT = 2001;
    public boolean connected;
    private Socket socketTCP;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private String flag = "false";
    private String reasons = "true";
    private int rows;
    private String remarks;
    private int pages;
    private String acno;
    private String fromDt;
    private String toDt;
    MiscReportFacadeRemote beanObj;
    FtsPostingMgmtFacadeRemote facadeRemote;
    private boolean winClose;

    public boolean isWinClose() {
        return winClose;
    }

    public void setWinClose(boolean winClose) {
        this.winClose = winClose;
    }

    public ReportPage() {
        try {
            if (getHttpSession().getAttribute("ReportName") != null) {
                setReportName(getHttpRequest().getSession().getAttribute("ReportName").toString());
                if (reportName.equalsIgnoreCase("Account Statement")) {
                    beanObj = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");
                    facadeRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
                    fromDt = (getHttpSession().getAttribute("actualFromDt").toString());
                    toDt = (getHttpSession().getAttribute("actualToDt").toString());
                    acno = (getHttpSession().getAttribute("custNo").toString());
                    rows = Integer.parseInt(getHttpSession().getAttribute("rows").toString());
                    pages = Integer.parseInt(getHttpSession().getAttribute("pages").toString());
                }
            } else {
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp");
            }
            setMessage("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printReport() {
        try {
            if (reportName.equalsIgnoreCase("Account Statement")) {
                flag = "true";
                if (facadeRemote.getCurrentBrnCode(acno).equalsIgnoreCase(getOrgnBrCode())) {
                    String result = beanObj.checkAcStatementChargeOpt("true");
                    if (result.equalsIgnoreCase("true")) {
                        flag = "true";
                    } else {
                        flag = "false";
                    }
                } else {
                    String result = beanObj.checkAcStatementChargeOpt("false");
                    if (result.equalsIgnoreCase("true")) {
                        flag = "true";
                    } else {
                        flag = "false";
                    }
                }
            }
            //String remoteHost = getHttpRequest().getRemoteHost();
            String remoteHost = WebUtil.getClientIP(getHttpRequest());
            socketTCP = new Socket(remoteHost, PORT);
            objectOutputStream = new ObjectOutputStream(socketTCP.getOutputStream());
            objectInputStream = new ObjectInputStream(socketTCP.getInputStream());
            
            if (getHttpSession().getAttribute("TextReport") != null) {
                byte[] bytes = ((ByteArrayOutputStream) getHttpSession().getAttribute("TextReport")).toByteArray();
                writeObject(bytes);
                
            } else if (getHttpSession().getAttribute("FilePath") != null) {
                byte[] bytes = Files.readAllBytes(Paths.get(getHttpSession().getAttribute("FilePath").toString()));
                writeObject(595.44, 841.68, bytes);
            }
            Vector result = (Vector) readObject();
            System.out.println("result = " + result);
            if (result.elementAt(0).equals(true)) {
                if (reportName.equalsIgnoreCase("Account Statement")) {
                    beanObj.saveAccountDetails(acno, fromDt, toDt, getUserName());
                }
                setMessage("Report successfully printed");
            } else {
                setMessage("System error occurred");
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
                setMessage("System error occurred");
            }
        }
    }

    public void accountStatChargDeductCode() {
        try {
            String result = "";
            if (acno.substring(0, 2).equalsIgnoreCase(getOrgnBrCode())) {
                result = beanObj.originBranchAccountCharges(acno, rows, getOrgnBrCode(), getUserName(), fromDt, toDt, pages);
                setMessage(result);
            } else {
                result = beanObj.destinationBranchAccountCharges(acno, rows, getOrgnBrCode(), getUserName(), fromDt, toDt, pages);
                setMessage(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void noReasonData() {
        try {
            if (getRemarks().equalsIgnoreCase("")) {
                setMessage("Enter the reason for not deducting charges");
                reasons = "false";
                return;
            }
            reasons = "true";
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            String dt = formatter.format(new Date());
            String result = beanObj.insertReasons(acno, dt, getRemarks(), getUserName(), fromDt, toDt, getOrgnBrCode());
            if (result.equalsIgnoreCase("true")) {
                setMessage("Reason saved successfully");
            } else {
                reasons = "false";
                setMessage("Error,while saving reasons");
            }
        } catch (Exception ex) {
            setMessage("Error occured");
            reasons = "false";
            ex.printStackTrace();
        }
    }

    public String exitAction() throws IOException {
        if (reportName.equalsIgnoreCase("TDS CERTIFICATE")) {
            winClose = true;
            return null;
        }
        return reportName;
    }

    public boolean writeObject(double width, double height, byte[] byteArr) {
        try {
            Vector writeVector = new Vector();
            writeVector.add("PDF");
            writeVector.add(width);
            writeVector.add(height);

            writeVector.add(byteArr);
            objectOutputStream.writeObject(writeVector);
            objectOutputStream.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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

    private Object readObject() {
        try {
            Object readObject = objectInputStream.readObject();
            return readObject;
        } catch (Exception e) {
            e.printStackTrace();
            if (!this.socketTCP.isClosed()) {
                System.out.println("SOCKET CLOSED FROM READ OBJECT");
            }
            return null;
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

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
