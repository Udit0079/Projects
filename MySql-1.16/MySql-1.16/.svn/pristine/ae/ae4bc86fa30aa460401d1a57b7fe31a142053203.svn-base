/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.filter;

import com.cbs.exception.ApplicationException;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.facade.admin.AdminManagementFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.constant.WebConstants;
import com.cbs.web.listener.CbsSessionTracker;
import com.cbs.web.utils.IP2BranchCode;
import com.hrms.web.utils.WebUtil;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author sipl
 */
public class CbsSessionFilter implements Filter {

    FilterConfig config;
    private String timeoutPage = "login.jsp";

    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {

        ServletContext context = config.getServletContext();
        String propsFile = context.getInitParameter("config");
        try {
            Init.IP2BR = new IP2BranchCode(propsFile);
            if ((req instanceof HttpServletRequest) && (res instanceof HttpServletResponse)) {
                
                HttpServletRequest httpReq = (HttpServletRequest) req;
                HttpServletResponse httpRes = (HttpServletResponse) res;
                
                validateUserAndBranch(httpReq,httpRes);
                String url = httpReq.getRequestURL().toString();
                
                if (isInvalidOperation(httpReq, url) && !url.contains(WebConstants.ERROR_PAGE)) {
                    httpRes.sendRedirect(WebConstants.ERROR_PAGE);
                } else {
                    chain.doFilter(req, res);
                }
            }
        } catch (Exception ex) {
            System.out.println("Error  is " + ex.getMessage());
        }
    }

    public void init(FilterConfig config) throws ServletException {
        this.config = config;
    }

    public void destroy() {
        //add code to release any resource
    }

    public String getTimeoutPage() {
        return timeoutPage;
    }

    public void setTimeoutPage(String timeoutPage) {
        this.timeoutPage = timeoutPage;
    }

    private boolean isInvalidOperation(HttpServletRequest req, String url) {
        if (req.getSession().getAttribute("userid") != null) {
            if (req.getHeader("Referer") == null) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private void validateUserAndBranch(HttpServletRequest req, HttpServletResponse res) throws ApplicationException {
        try {
            HttpSession session = req.getSession();
            System.out.println(new Date() + ":The Requested IP Address using WebUtil is ->>>>>>>>>>>>>>>>>>>>>>>" + WebUtil.getClientIP(req));
            String curBr = Init.IP2BR.getBranch(InetAddress.getByName(WebUtil.getClientIP(req)));
            
            if (session.getAttribute("userid")!=null && session.getAttribute("usrbr")!= null && !curBr.equals(session.getAttribute("usrbr").toString())) {
                AdminManagementFacadeRemote beanRemote = (AdminManagementFacadeRemote) ServiceLocator.getInstance().lookup("AdminManagementFacade");
                System.out.println(new Date() + ": The User " + session.getAttribute("userid").toString() + " is logged out from IP Address " + WebUtil.getClientIP(req));
                beanRemote.logOut(session.getAttribute("userid").toString());
                res.sendRedirect("/cbs-jsp/j_spring_security_logout");
            }
        } catch (ServiceLocatorException ex) {
            Logger.getLogger(CbsSessionTracker.class.getName()).log(Level.ERROR, null, ex);
        } catch (ApplicationException ex) {
            Logger.getLogger(CbsSessionTracker.class.getName()).log(Level.ERROR, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(CbsSessionTracker.class.getName()).log(Level.ERROR, null, ex);
        }
    }
}
