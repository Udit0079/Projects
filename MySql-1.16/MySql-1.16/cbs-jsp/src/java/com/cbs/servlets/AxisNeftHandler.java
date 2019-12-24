/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.servlets;

import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.neftrtgs.UploadNeftRtgsMgmtFacadeRemote;
import com.cbs.pojo.neftrtgs.ExcelReaderPojo;
import com.cbs.utils.ParseFileUtil;
import com.cbs.utils.ServiceLocator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author root
 */
public class AxisNeftHandler extends HttpServlet {

    private FtsPostingMgmtFacadeRemote ftsRemote;
    private UploadNeftRtgsMgmtFacadeRemote neftRemote;

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
        String result = "", requestType = "";
        try {
            requestType = request.getParameter("requestType");
            String acno = request.getParameter("acno");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            neftRemote = (UploadNeftRtgsMgmtFacadeRemote) ServiceLocator.getInstance().lookup("UploadNeftRtgsMgmtFacade");
            if (requestType.equalsIgnoreCase("AV")) { //Account Validation
                result = ftsRemote.ftsAcnoValidateAuto(ftsRemote.getNewAccountNumber(acno), 0);
                System.out.println("Ac Validation Request-->" + result);
                if (result.equalsIgnoreCase("true")) {
                    response.getWriter().print("0:Successful");
                } else {
                    result = (result == null) ? "invalid" : result;
                    response.getWriter().print("1:" + result);
                }
            } else if (requestType.equalsIgnoreCase("TP")) { //Transaction Processing
                List<ExcelReaderPojo> pojoList = new ArrayList<>();
                try {
                    pojoList = ParseFileUtil.parseAxisInwData(acno, request.getParameter("name"), request.getParameter("valIfsc"),
                            request.getParameter("amount"), ftsRemote.getBusinessDate(), request.getParameter("tranType"),
                            request.getParameter("remitterAccount"), request.getParameter("remitterName"),
                            request.getParameter("remitterIfsc"), request.getParameter("utrNo"), request.getParameter("valueDate"));
                } catch (Exception ex) {
                    System.out.println("Invalid Data From Axis. " + ex.getMessage());
                }
                result = neftRemote.neftRtgsUploadProcess(pojoList, "90", "System",
                        neftRemote.getAxisInwHead("AUTO", "AXIS"), "AUTO", "AXIS");
                System.out.println("Transaction Processing Result-->" + result);
                if (result.equalsIgnoreCase("true")) {
                    response.getWriter().print("0:Successful");
                } else {
                    result = (result == null) ? "Fail" : result;
                    response.getWriter().print("1:" + result);
                }
            }
        } catch (Exception ex) {
            System.out.println("Problem in CBS Side" + ex.getMessage());
            if (requestType.equalsIgnoreCase("AV")) {
                response.getWriter().print("1:invalid");
            } else if (requestType.equalsIgnoreCase("TP")) {
                response.getWriter().print("1:Fail");
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
