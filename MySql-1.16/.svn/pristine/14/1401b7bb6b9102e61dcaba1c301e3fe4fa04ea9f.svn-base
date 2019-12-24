<%@ page isErrorPage="true" import="java.io.*" contentType="text/html"%>

<%@ page import="com.cbs.utils.CbsUtil"%>
<%@ page import="com.cbs.servlets.Init" %>
<%@ page import="java.net.InetAddress" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Vector" %>
<%@ page import="com.cbs.facade.admin.AdminManagementFacadeRemote"%>
<%@ page import="com.cbs.utils.ServiceLocator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String branchName = "";
    String bnkName = "";
    InetAddress localhost = InetAddress.getByName(request.getRemoteAddr());
    String brCode = Init.IP2BR.getBranch(localhost);
    if (brCode == null) {
        response.sendRedirect("/cbs-jsp/faces/error.jsp");
    } else {
        AdminManagementFacadeRemote beanRemote = (AdminManagementFacadeRemote) ServiceLocator.getInstance().lookup("AdminManagementFacade");
        List bankResultList = beanRemote.bankAddress(brCode);
        if (bankResultList.size() > 0) {
            for (int i = 0; i < bankResultList.size(); i++) {
                Vector ele = (Vector) bankResultList.get(i);
                branchName = CbsUtil.toProperCase((ele.get(1).toString() + " " + ele.get(2).toString()).toLowerCase());
                bnkName = CbsUtil.toProperCase((ele.get(0).toString()).toLowerCase());
            }
        }
        if (!beanRemote.isUserExist(Init.IP2BR.getBranch(localhost))) {
            response.sendRedirect("/cbs-jsp/faces/superuser.jsp");
        }
    }
%>
<html>
    <head>
        <title>e-Core::Error</title>
        <link rel="stylesheet" type="text/css" href="/cbs-jsp/resources/login-style.css" />
    </head>
    <body class="body">
        <table cellspacing="0" cellpadding="0" width="100%">
            <tbody>
                <tr>
                    <td>
                        <table width="100%" cellpadding="0" cellspacing="0">
                            <tbody>
                                <tr>
                                    <td>
                                        <div class="bnkName"><%=bnkName%></div>
                                    </td>
                                </tr>

                                <tr>
                                    <td >
                                        <table style=" background-color: #52aec6" width="100%">
                                            <tbody>
                                                <tr><td></td></tr></tbody>
                                        </table>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="100%">
                                       <span class="brName"><%=branchName%></span>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table style="background-color: #52aec6" width="100%">
                            <tbody>
                                <tr><td></td></tr></tbody>
                        </table>
                    </td>
                </tr>
                <tr><td width="100%">&nbsp;</td></tr>
                <tr>
                    <td>
                        <div id="logonBodyContent">
                            <br>
                            <table width="60%" cellspacing="0" cellpadding="0" border="0" align="center" >
                                <tbody>
                                    <tr>
                                        <td>
                                            <table width="100%" cellspacing="0" cellpadding="0" border="0" class="alert_table">
                                                <tbody>
                                                    <tr>
                                                        <td width="10%"><img title="Alert" alt="Alert" src="/cbs-jsp/resources/images/ico_alert.gif"></td>
                                                        <td class="alert_text">Error</td>
                                                    </tr>
                                                    <tr>
                                                        <td class="alert_header" colspan="2">Suggestions:</td>
                                                    </tr>
                                                    <tr>
                                                        <td colspan="2">
                                                            <ul>
                                                                <br/><li><p>Your banking session is closed because you have performed an incorrect operation.<br/>
						 Henceforth, please remember to use the navigation links to navigate within the Application.<br/></p>
                                                                </li>
                                                                <li>Please <a href="/cbs-jsp/j_spring_security_logout">Click Here</a> to login.</li>
                                                            </ul>
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>

                    </td>
                </tr>
                <tr>
                    <td ><div class="footer">&copy;&nbsp;Copyright <%=bnkName%></div></td>
                </tr>
            </tbody>
        </table>
    </body>
</html>
                                             