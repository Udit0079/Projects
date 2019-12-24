/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.servlets;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class CkycrImageServlet extends HttpServlet {

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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BufferedInputStream in = null;
        try {
            ServletContext context = getServletContext();
            String directory = context.getInitParameter("ckycrTempImageLocation");
            String ckycNo = request.getParameter("ckycNo");
            String rqId = request.getParameter("rqId");
            String file = request.getParameter("file");
            // Get image file.
            if (!(rqId == null || rqId.equals("")) && (ckycNo == null || ckycNo.equals(""))) {
                ckycNo = rqId;

                if (file.split(".").length < 1) {
//                if (!file.split(".")[1].equalsIgnoreCase("jpg")) {
                    file = file + ".jpg";
//                }
                }
            }
            if (file != null && !file.equals("") && new File(directory + "/" + ckycNo + "/" + file).exists()) {
                in = new BufferedInputStream(new FileInputStream(directory + "/" + ckycNo + "/" + file));
                // Get image contents.
                byte[] bytes = new byte[in.available()];
                in.read(bytes);
                // Write image contents to response.
                response.getOutputStream().write(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getLogger(ImageServlet.class.getName()).log(Level.ERROR, null, e);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(ImageServlet.class.getName()).log(Level.ERROR, null, e);
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                Logger.getLogger(ImageServlet.class.getName()).log(Level.ERROR, null, e);
            }

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
