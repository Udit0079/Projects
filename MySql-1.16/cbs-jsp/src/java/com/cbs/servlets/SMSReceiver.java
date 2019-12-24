/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.servlets;

import com.cbs.facade.sms.RequestReceiverFacadeRemote;
import com.cbs.utils.ServiceLocator;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author root
 */
public class SMSReceiver extends HttpServlet {

    RequestReceiverFacadeRemote beanRemote = null;
    private final String jndiHomeName = "RequestReceiverFacade";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String mobileNo = request.getParameter("Mobile"); //91XXXXXXXXXX
            String message = request.getParameter("Sms");
            beanRemote = (RequestReceiverFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            if (message.length() == 3) { //For Pull SMS
                beanRemote.processOnRequestMessage(mobileNo, message);
            } else if (message.length() == 25) { //For Aadhar Registration
                beanRemote.onlineAadhaarRegistration(mobileNo, message);
            }
            response.getWriter().print("success");
        } catch (Exception ex) {
            System.out.println("Problem in calling EJB Recever Facade" + ex.getMessage());
            throw new IOException("Problem in sending the SMS " + ex.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
