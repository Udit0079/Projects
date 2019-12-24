/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.exception.ApplicationException;
import com.cbs.web.controller.BaseBean;
import com.hrms.web.utils.WebUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

/**
 *
 * @author root
 */
public class PrinterClass extends BaseBean {

    private Socket socketTCP;
    public static int PORT = 2001;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public String printingStatus(String fileNameToPrnt) {
        String msg = new String();
        try {
            String osName = System.getProperty("os.name");
            String fileCr = null;
            if (osName.equals("Linux")) {
                fileCr = "/install/billPrinting/";
            } else {
                fileCr = "c:\\billPrinting\\";
            }
            FileInputStream fin = new FileInputStream(fileCr + fileNameToPrnt);
            int size = fin.available();
            byte[] bytes = new byte[size];
            fin.read(bytes, 0, size);
            msg = printReport("TXT", bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }

    private void createConnection() throws Exception {
       // String remoteHost = getHttpRequest().getRemoteHost();
        String remoteHost = WebUtil.getClientIP(getHttpRequest());
        // String remoteHost = "192.168.2.107";
        socketTCP = new Socket(remoteHost, PORT);
        objectOutputStream = new ObjectOutputStream(socketTCP.getOutputStream());
        objectInputStream = new ObjectInputStream(socketTCP.getInputStream());
    }

    public String printReport(double width, double height, byte[] byteArr) {
        try {
            createConnection();
            writeObject(width, height, byteArr);
            Vector vect = (Vector) readObject();
            System.out.println("Result = " + vect.elementAt(0));
            return vect.elementAt(0).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public String printReport(String option, byte[] byteArr) {
        try {
            createConnection();
            writeObject(option, byteArr);
            Vector vect = (Vector) readObject();
            System.out.println("Result = " + vect.elementAt(0));
            return vect.elementAt(0).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public boolean writeObject(String option, byte[] byteArr) {
        try {
            Vector writeVector = new Vector();
            writeVector.add(option);
            writeVector.add(byteArr);
            objectOutputStream.writeObject(writeVector);
            objectOutputStream.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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

    private Object readObject() {
        try {
            Object readObject = objectInputStream.readObject();
            return readObject;
        } catch (Exception e) {
            if (socketTCP.isClosed()) {
                //System.out.println("SOCKET CLOSED FROM READ OBJECT");
            }
            return null;
        }
    }

    public byte[] generatePDFStream(float width, float height,float topMargin, float leftMargin, float fontSize, String data) throws ApplicationException {
        try {
            Rectangle pageSize = new Rectangle(width, height);
            Document document = new Document(pageSize, leftMargin, 0, topMargin, 0);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);
            document.open();

            Paragraph p = new Paragraph();
            p.setFont(new Font(FontFamily.TIMES_ROMAN, fontSize));
            p.add(data);
            document.add(p);
            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
}
