<%@ page import="com.cbs.utils.CbsUtil"%>
<%@ page import="com.cbs.servlets.Init" %>
<%@ page import="com.hrms.web.utils.WebUtil"%>
<%@ page import="java.net.InetAddress" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Vector" %>
<%@ page import="com.cbs.facade.admin.AdminManagementFacadeRemote"%>
<%@ page import="com.cbs.utils.ServiceLocator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String branchName = "";
    String bnkName = "";
    InetAddress localhost = InetAddress.getByName(WebUtil.getClientIP(request));
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
        <title>e-Core::Login</title>
        <link rel="stylesheet" type="text/css" href="/cbs-jsp/resources/login-style.css" />
        <script type="text/javascript">
            var message="Function Disabled!"; 
            function clickIE4() {
                if (event.button==2) {
                    alert(message); 
                    return false; 
                } 
            } 
            function clickNS4(e) { 
                if (document.layers||document.getElementById) {
                    if(!document.all) {
                        if (e.which==2||e.which==3) { 
                        alert(message); 
                        return false; 
                        } 
                    }                    
                } 
            } 
            if (document.layers) { 
                document.captureEvents(Event.MOUSEDOWN); 
                document.onmousedown=clickNS4; 
            } 
            else if (document.all) {
                if(!document.getElementById){
                    document.onmousedown=clickIE4; 
                }                
            } 
            document.oncontextmenu=new Function("alert(message);return false") 
            function disableCtrlKeyCombination(e){
                var forbiddenKeys = new Array("a", "n", "c", "x", "v", "j");
                var key;
                var isCtrl;

                if(window.event){
                    key = window.event.keyCode;     //IE
                    if(window.event.ctrlKey)
                        isCtrl = true;
                    else
                        isCtrl = false;
                }
                else{
                    key = e.which;     //firefox
                    if(e.ctrlKey)
                        isCtrl = true;
                    else
                        isCtrl = false;
                }

                if(isCtrl){
                    for(i=0; i<forbiddenkeys .length; i++){
                        if(forbiddenKeys[i].toLowerCase() == String.fromCharCode(key).toLowerCase()){
                            alert("Key combination CTRL" +String.fromCharCode(key)+" has been disabled.");
                            return false;
                        }
                    }
                }
                return true;
            }
            
            function setFocus(){
                document.getElementById("username").focus();
            }
        </script>
    </head>
    <body class="body" onload="setFocus()">
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
                                        <table style="background-color: #52aec6" width="100%">
                                            <tbody>
                                                <tr><td></td></tr></tbody>
                                        </table>
                                    </td>
                                </tr>
                                <tr height="30">
                                    <td width="100%">
                                        <span class="brName"><%=branchName%></span>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td > 
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
                            <table width="60%" cellspacing="0" cellpadding="0" border="0" align="center" class="tblbrdr">
                                <tbody>
                                    <tr>
                                        <td>
                                            <div id="loginpage" style="min-width: 700px">
                                                <table width="100%">
                                                    <tbody>
                                                        <tr>
                                                            <td width="50%" align="left">
                                                                <div align="left" style="width:114px">
                                                                    <a target="www.infostellar.com" href="http://www.infostellar.com/">
                                                                        <img border="0" src="/cbs-jsp/resources/images/logo.gif" title="www.infostellar.com" alt="www.infostellar.com">
                                                                    </a>
                                                                </div>
                                                            </td>
                                                            <td class="bsep"></td>
                                                            <td width="50%" align="center" class="vkb_aligntop">
                                                                <form autocomplete="off" action="/cbs-jsp/j_spring_security_check" method="post">
                                                                    <table width="100%" cellspacing="0" cellpadding="0" border="0" class="vkb_tbl">
                                                                        <tbody>
                                                                            <tr>
                                                                                <td colspan="3">
                                                                                    <p class="vkb_login_heading">
                                                                                        <span>Login to e-Bank(CBS)</span>
                                                                                    </p>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td width="22%" nowrap="nowrap"><strong>User Name * </strong></td>
                                                                                <td width="78%">
                                                                                    <input type="text" style="height:23px;" maxlength="20" size="23" tabindex="1" name="j_username" id="username"
                                                                                           onkeydown="return disableCtrlKeyCombination(event);" onkeypress="return disableCtrlKeyCombination(event);" onpaste="return false" 
                                                                                           oncopy="return false"/>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td><strong>Password * </strong></td>
                                                                                <td nowrap="nowrap">
                                                                                    <input type="password" style="height:23px;" name="j_password" size="23" title="password" tabindex="2" id="password" 
                                                                                           onkeydown="return disableCtrlKeyCombination(event);" onkeypress="return disableCtrlKeyCombination(event);" onpaste="return false" 
                                                                                           oncopy="return false"/>
                                                                                </td> 
                                                                            </tr>
                                                                            <tr>
                                                                                <td>&nbsp;</td>
                                                                                <td>&nbsp;</td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td></td>
                                                                                <td nowrap="nowrap">
                                                                                    <input type="submit" style="width:65px;height:20px" tabindex="3" id="Button2" value="Login" class="button_class">
                                                                                    &nbsp;
                                                                                    <input type="reset" style="width:65px;height:20px" tabindex="4" id="Cancel2" value="Reset" class="button_class" name="Cancel2">
                                                                                </td>
                                                                            </tr>
                                                                        </tbody>
                                                                    </table>
                                                                </form>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td colspan="3">
                                                                <c:if test="${param.error == 1}">
                                                                    <c:if test='${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message=="Change Password"}'>
                                                                        <%
                                                                            response.sendRedirect("/cbs-jsp/faces/ChgPassword.jsp");
                                                                        %>
                                                                    </c:if>
                                                                    <c:if test='${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message=="User credentials have expired"}'>
                                                                        <%
                                                                            response.sendRedirect("/cbs-jsp/faces/ChgPassword.jsp");
                                                                        %>
                                                                    </c:if>
                                                                    <div class="errorblock">${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</div>
                                                                </c:if>
                                                                <c:if test="${param.error == 2}">
                                                                    <div class="errorblock">You are successfully logged out.</div>
                                                                </c:if>
                                                                <c:if test="${param.error == 3}">
                                                                    <div class="errorblock">${sessionScope.SPRING_SECURITY_LAST_EXCEPTION}</div>
                                                                </c:if>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>   
                                                                <br><br>
                                                            </td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>

                            <div id="logonRemark">
                                <br>
                                <table width="60%" cellspacing="0" cellpadding="0" border="0" align="center">
                                    <tbody>
                                        <tr>
                                            <td class="notecls">
                                                <ul>
                                                    <li>Mandatory fields are marked with an asterisk (*)</li>
                                                    <li>Your user name and password are highly confidential.</li>
                                                    <li>Do not provide your username and password anywhere other than this page</li>
                                                </ul>
                                            </td>
                                        </tr>                  
                                        <tr><td>&nbsp;</td></tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                    </td>
                </tr>
                <tr>
                    <td ><div class="footer"> &copy;&nbsp;Copyright <%=bnkName%></div></td>
                </tr>
            </tbody>
        </table>
    </body>
</html>

