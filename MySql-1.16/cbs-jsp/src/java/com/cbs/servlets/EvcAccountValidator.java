/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.servlets;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.EvcAccountValidationFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.pojo.admin.EvcRequest;
import com.cbs.web.pojo.admin.EvcResponse;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.util.List;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author root
 */
public class EvcAccountValidator extends HttpServlet {

    private EvcAccountValidationFacadeRemote facade;
    Gson gSon;

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
        EvcResponse evcRes = new EvcResponse();
        try {
            if (gSon == null) {
                gSon = new Gson();
            }
            facade = (EvcAccountValidationFacadeRemote) ServiceLocator.getInstance().lookup("EvcAccountValidationFacade");

            String evcJsonReq = request.getParameter("evcReq");

            System.out.println("In comming Account Validation Json Request is " + evcJsonReq);

            EvcRequest evcReq = gSon.fromJson(evcJsonReq, EvcRequest.class);

            evcRes.setUniqueRequestId(evcReq.getUniqueRequestId());
            evcRes.setName("");
            evcRes.setPanVerFlag("N");
            evcRes.setMobileVerFlag("N");
            evcRes.setEmailVerFlag("N");

            String rs = facade.logEvcAccountValidationReq(evcReq.getUniqueRequestId(), evcJsonReq);
            if (rs.equalsIgnoreCase("True")) {
                List rsList = facade.evcAccountValidate(evcReq.getPan(), evcReq.getAcno(), evcReq.getIfsc());
                Vector rsVec = (Vector) rsList.get(0);

                String custName = rsVec.get(1).toString();
                String mobile = rsVec.get(2).toString();
                String email = rsVec.get(3).toString();
                String status = rsVec.get(4).toString();

                evcRes.setName(custName);
                evcRes.setPanVerFlag("Y");

                if (mobile.equals(evcReq.getMobileNumber())) {
                    evcRes.setMobileVerFlag("Y");
                    if (email.equals(evcReq.getEmailId())) {
                        evcRes.setEmailVerFlag("Y");
                        if (status.equals("1")) {
                            evcRes.setResponseCode("0000");
                        } else if (status.equals("9")) {
                            evcRes.setResponseCode("EF-WS-BVB-ERR-100018");
                        } else if (status.equals("2")) {
                            evcRes.setResponseCode("EF-WS-BVB-ERR-100019");
                        }
                    } else {
                        evcRes.setEmailVerFlag("N");
                        evcRes.setResponseCode("EF-WS-BVB-ERR-100016");
                    }
                } else {
                    evcRes.setMobileVerFlag("N");
                    evcRes.setResponseCode("EF-WS-BVB-ERR-100013");
                }
                try {
                    facade.updateEvcAccountValidationRes(evcRes.getUniqueRequestId(), gSon.toJson(evcRes));
                } catch (ApplicationException ex) {
                    System.out.println("Error in Updating EVC Validation Response in Database" + ex.getMessage());
                }
                response.getWriter().print(gSon.toJson(evcRes));
            } else {
                evcRes.setResponseCode(rs);
                response.getWriter().print(gSon.toJson(evcRes));
            }
        } catch (JsonSyntaxException | ApplicationException ex) {
            if (ex.getMessage().equals("PAN")) {
                evcRes.setPanVerFlag("N");
                evcRes.setResponseCode("EF-WS-BVB-ERR-10004");
            } else if (ex.getMessage().equals("IFSC")) {
                evcRes.setPanVerFlag("N");
                evcRes.setResponseCode("EF-WS-BVB-ERR-100012");
            }else if (ex.getMessage().equals("ACCOUNT")) {
                evcRes.setPanVerFlag("N");
                evcRes.setResponseCode("EF-WS-BVB-ERR-100017");
            }else {
                evcRes.setName("");
                evcRes.setPanVerFlag("N");
                evcRes.setMobileVerFlag("N");
                evcRes.setEmailVerFlag("N");
                evcRes.setResponseCode("");
            }
            try {
                facade.updateEvcAccountValidationRes(evcRes.getUniqueRequestId(), gSon.toJson(evcRes));
            } catch (ApplicationException ex1) {
                System.out.println("Error in Updating EVC Validation Response in Database" + ex1.getMessage());
            }
            response.getWriter().print(gSon.toJson(evcRes));
        }

    }

//    private EvcResponse createResponse(String uniqueId, String name, String panFlag, String mobileFlag, String emailFlag){
//        EvcResponse resp = new EvcResponse();
//        resp.setUniqueRequestId(uniqueId);
//        resp.setName(name);
//        resp.setPanVerFlag(panFlag);
//        resp.setMobileVerFlag(mobileFlag);
//        resp.setEmailVerFlag(emailFlag);
//        return resp;
//    }
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
