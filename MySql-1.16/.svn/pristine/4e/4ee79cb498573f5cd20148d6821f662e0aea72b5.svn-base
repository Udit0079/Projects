/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.servlets;

import com.cbs.facade.sms.RequestReceiverFacadeRemote;
import com.cbs.utils.ServiceLocator;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author root
 */
public class MissedCallHandler extends HttpServlet {

    RequestReceiverFacadeRemote beanRemote = null;
    private final String jndiHomeName = "RequestReceiverFacade";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String mobileNo = request.getParameter("mobile");
            String chanelId = request.getParameter("channel");
            String operator = request.getParameter("operator");
            String circle = request.getParameter("circle");
           // String qualityScore = request.getParameter("qualityscore");
            String missedCalTime = request.getParameter("time");
            beanRemote = (RequestReceiverFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            beanRemote.missedCallHandler(mobileNo, chanelId, operator, circle, missedCalTime);
            response.getWriter().print("success");
        } catch (Exception ex) {
            System.out.println("Problem in calling EJB Recever Facade" + ex.getMessage());
            throw new IOException("Problem in sending the SMS " + ex.getMessage());
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
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
