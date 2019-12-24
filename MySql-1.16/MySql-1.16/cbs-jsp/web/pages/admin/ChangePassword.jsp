<%-- 
    Document   : ChangePassword
    Created on : Dec 1, 2010, 11:54:50 AM
    Author     : Sudhir 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>Change Password</title>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="gridPanel" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{ChangePassword.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Password Change"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ChangePassword.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="errorMsgReporting" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                        <h:outputText id="errorGrid" styleClass="error" value="#{ChangePassword.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel2" columns="2" columnClasses="col8,col8" style="height:30px;width:100%" styleClass="row2" width="100%">

                        <h:outputLabel id="lblFromDate" styleClass="label" value="Old Password"  style="padding-left:350px"/>
                        <h:inputSecret id="input1" styleClass="input" value="#{ChangePassword.oldPassword}"/>

                    </h:panelGrid>
                    <h:panelGrid id="gridPanel3" columns="2" columnClasses="col8,col8" style="height:30px;width:100%" styleClass="row1" width="100%">

                        <h:outputLabel id="lblToDate" styleClass="label" value="New Pasword"  style="padding-left:350px"/>
                        <h:inputSecret id="newpasswword" styleClass="input"  value="#{ChangePassword.newPassword}" />

                    </h:panelGrid>
                    <h:panelGrid id="newpasspanel" columns="2" columnClasses="col8,col8" style="height:30px;width:100%" styleClass="row2" width="100%">

                        <h:outputLabel id="retypenewpassword" styleClass="label" value="Retype New Pasword"   style="padding-left:350px"/>
                        <h:inputSecret id="newpasswwordretype" styleClass="input"  value="#{ChangePassword.reNewPassword}"/>

                    </h:panelGrid>

                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel" styleClass="vtop">

                            <a4j:commandButton  id="change" value="Change"  oncomplete="#{rich:component('showPanelForGrid')}.show();"/>
                            <a4j:commandButton  id="cancel" value="Cancel" reRender="errorGrid,newpasswwordretype,input1,newpasswword"   action="#{ChangePassword.cancelButton}"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{ChangePassword.exitBtnAction}" reRender="errorGrid,newpasswwordretype,input1,newpasswword"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="showPanelForGrid" autosized="true" width="300">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup> 
                        <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="hidelink1" />
                        <rich:componentControl for="showPanelForGrid" attachTo="hidelink1" operation="hide" event="onclick" />
                    </h:panelGroup>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color: #E8EEF7">
                        <tbody>
                            <tr>
                                <td colspan="2" height="50px">
                                    <h:outputText value="Are you sure you want to change the password ?" style="padding-left:15px;font-size:12px" />
                                </td>
                            </tr>
                            <tr>
                                <td align="right" width="50%">
                                    <a4j:commandButton value="Yes" ajaxSingle="true" action="#{ChangePassword.btnChangeaction}"
                                                       onclick="#{rich:component('showPanelForGrid')}.hide();" reRender="errorGrid,input1,newpasswword,newpasswwordretype"/>
                                </td>
                                <td align="left" width="50%">
                                    <a4j:commandButton value="No" onclick="#{rich:component('showPanelForGrid')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>

        </body>
    </html>

</f:view>
