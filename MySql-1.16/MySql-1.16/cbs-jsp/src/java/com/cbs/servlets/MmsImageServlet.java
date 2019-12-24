/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.servlets;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author root
 */
public class MmsImageServlet extends HttpServlet {

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
            String directory = context.getInitParameter("mms");
            String imageName = request.getParameter("file");
            String fileNo = request.getParameter("fileNo");
            String mandateType = request.getParameter("mandate");
            String fileUpDt = request.getParameter("fileUpDt");
            String mmsMode = request.getParameter("mmsMode");

            // Get image file.
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
            if (mmsMode.equalsIgnoreCase("N")) {
                directory = directory + "/" + ymd.format(dmy.parse(fileUpDt)) + "/" + fileNo + "_" + mandateType + "/";
            } else if (mmsMode.equalsIgnoreCase("L")) {
                directory = directory + "/" + ymd.format(dmy.parse(fileUpDt)) + "/" + fileNo + "_" + mandateType + "_LEGACY" + "/";
            }


            if (imageName != null && !imageName.equals("") && new File(directory + imageName).exists()) {
                in = new BufferedInputStream(new FileInputStream(directory + imageName));

                // Get image contents.
                byte[] bytes = new byte[in.available()];
                in.read(bytes);

                // Write image contents to response.
                response.getOutputStream().write(bytes);
            }
        } catch (Exception e) {
            System.out.println("MmsImageServlet Problem-->" + e.getMessage());
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                System.out.println("MmsImageServlet Problem finally-->" + e.getMessage());
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
