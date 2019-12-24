/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.servlets;

import com.cbs.exception.ServiceLocatorException;
import com.cbs.facade.dds.PigMyFacadeRemote;
import com.cbs.utils.ServiceLocator;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PigMeReceiver extends HttpServlet {

    PigMyFacadeRemote beanRemote = null;
    private final String jndiHomeName = "PigMyFacade";
    private static final Object LOCK = new Object();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String clientId = request.getParameter("clientId");
            String payLoad = request.getParameter("payLoad");
            beanRemote = (PigMyFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            synchronized(LOCK){
                response.getWriter().write(beanRemote.pigMeProcess(clientId, payLoad));
            }
        } catch (ServiceLocatorException | IOException ex) {
            System.out.println("Problem In PigMe Recever At CBS" + ex.getMessage());
            response.getWriter().write("0001:" + ex.getMessage());
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
