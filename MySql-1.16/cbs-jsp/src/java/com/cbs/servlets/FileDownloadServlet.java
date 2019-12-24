/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.servlets;

import com.cbs.constant.BusinessConstant;
import com.cbs.utils.SignUtil;
import com.cbs.web.constant.WebConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author root
 */
public class FileDownloadServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        ServletOutputStream os = null;
        FileInputStream fin = null;
        try {
            if (request.getParameter("pwd") == null) {
                String dirName = request.getParameter("dirName");
                System.out.println("dirName in Servlet   " + dirName);
                String branchFileName = request.getParameter("fileName");
                System.out.println("branchFileName  in Servlet   " + branchFileName);
                File f = new File(dirName + branchFileName);
                if (f.exists()) {
                    os = response.getOutputStream();
                    ServletContext context = getServletConfig().getServletContext();
                    String mimetype = context.getMimeType(branchFileName);

                    response.setContentType((mimetype != null) ? mimetype : "application/octet-stream");
                    response.setContentLength((int) f.length());
                    response.setHeader("Content-Disposition", "attachment; filename=\"" + branchFileName + "\"");

                    fin = new FileInputStream(f);
                    int size = fin.available();
                    response.setContentLength(size);
                    byte[] ab = new byte[size];

                    int bytesread;
                    do {
                        bytesread = fin.read(ab, 0, size);
                        if (bytesread > -1) {
                            os.write(ab, 0, bytesread);
                        }
                    } while (bytesread > -1);

                } else {
                    PrintWriter out = response.getWriter();
                    out.println("File not found");
                }
            } else {
                String dirName = request.getParameter("dirName");
                String branchFileName = request.getParameter("fileName");
                File f = new File(dirName + branchFileName);

                if (f.exists()) {
                    SignUtil su = new SignUtil();
                    String xml = new String(Files.readAllBytes(Paths.get(dirName + branchFileName, new String[0])));
                    //String output = su.getSignedEnvelope(false, "", xml, dirName + "key/Test_InterOp_Class2_CodeSign.pfx", "idrbt");  //Test
//                    String output = su.getSignedEnvelope(false, "", xml, WebConstants.DSG_KEY_PATH, request.getParameter("pwd")); //Comment for WebConstants, Using from BusinessConstant
                    String output = su.getSignedEnvelope(false, "", xml, BusinessConstant.DSG_KEY_PATH, request.getParameter("pwd"));
                    os = response.getOutputStream();
                    ServletContext context = getServletConfig().getServletContext();
                    String mimetype = context.getMimeType(branchFileName);

                    response.setContentType((mimetype != null) ? mimetype : "application/octet-stream");
                    response.setContentLength(output.getBytes().length);
                    response.setHeader("Content-Disposition", "attachment; filename=\"" + branchFileName + "\"");
                    os.write(output.getBytes());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            fin.close();
            os.flush();
            os.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
