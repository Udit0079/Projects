/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.servlets;

import com.cbs.facade.report.NpaReportFacadeRemote;
import com.cbs.pojo.AllSmsPojo;
import com.cbs.utils.ServiceLocator;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author root
 */
public class SmsReportHandler extends HttpServlet {
    Gson gSon;
    NpaReportFacadeRemote beanRemote = null;
    private final String jndiHomeName = "NpaReportFacade";
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
            if(gSon == null) gSon = new Gson();
            String fromDt = request.getParameter("fromDt");
            String toDt = request.getParameter("toDt");
            
            beanRemote = (NpaReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            List<AllSmsPojo> allSms = beanRemote.getAllMessageAtHo(fromDt, toDt,"");
            
            response.getWriter().print(gSon.toJson(allSms));
        } catch (Exception ex) {
            System.out.println("Problem in calling getAllMessageAtHo" + ex.getMessage());
            throw new IOException("Problem in getting SMS data" + ex.getMessage());
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
