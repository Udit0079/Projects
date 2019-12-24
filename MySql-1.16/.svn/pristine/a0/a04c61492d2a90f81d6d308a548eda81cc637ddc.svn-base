<%--
    Document   : Welcome Bean
    Created on : Apr 07, 2011, 02:45:43 PM
    Author     : Dhirendra Singh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/login-style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Welcome to e-Core</title>
            <style>
                .optionList {
                    height:22px;
                }
                .vertical-menu-cell {
                    padding:0px 4px 0px 4px;
                }
                .rich-mpnl-body{
                    padding: 0px !important
                }
                .extdt-cell-div{
                    white-space: normal !important;
                }

            </style>
        </head>
        <body class="body">
            <h:form id="form1">
                <table cellspacing="0" cellpadding="0" width="100%">
                    <tbody>
                        <tr>
                            <td>
                                <table width="100%" cellpadding="0" cellspacing="0">
                                    <tbody>
                                        <tr>
                                            <td width="60%">
                                                <div class="bnkName">
                                                    <h:outputText id="bnkName" value="#{welcome.bnkName}"/>
                                                </div>
                                            </td>
                                            <td width="40%" align="right">
                                                <table cellpadding="0" cellspacing="0" width="100%">
                                                    <tr>
                                                        <td width="100%" align="right">
                                                            <span class="brName">
                                                                <h:outputLabel value="IVR Branch Code : " styleClass="headerLabel"/>
                                                                <h:outputLabel value="#{welcome.ivrBrCode}" style="color:#044F93" styleClass="headerLabel"/>
                                                                <h:outputLabel value=" | Help Desk Contact Details : " styleClass="headerLabel"/>
                                                                <h:outputLabel value="8010455455 " style="color:#044F93" styleClass="headerLabel"/>
                                                            </span>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td width="100%" align="right">
                                                            <span class="brName">
                                                                <a4j:commandLink hreflang="#" value="FAQ" style="color:#044F93; text-decoration:none" onclick="#{rich:component('faqAlert')}.show()"/>
                                                                <h:outputLabel value=" | "  style="display:#{welcome.urlDisplay}"/>
                                                                <h:outputLink style="color:#044F93; text-decoration:none;display:#{welcome.urlDisplay}" target="_blank" value="#{welcome.manualUrl}">User Manual</h:outputLink>
                                                                <h:outputLabel value=" | Today's Date:" styleClass="headerLabel"/>
                                                                <h:outputLabel value="#{welcome.todayDate}" id="lblDate" style="color:#044F93" styleClass="headerLabel"/>
                                                            </span>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td width="100%" align="right">
                                                            <span class="brName">
                                                                <h:outputLabel value="IP Address :" styleClass="headerLabel"/>
                                                                <h:outputLabel value="#{welcome.httpRequest.remoteAddr}" style="color:#044F93;" styleClass="headerLabel"/>
                                                                <h:outputLabel value=" | " />
                                                                <a4j:commandLink id="logout" value="Logout" action="#{welcome.logout}" style="text-decoration:none;color:red;"/>
                                                            </span>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td >
                                        </tr>
                                        <tr>
                                            <td colspan="3">
                                                <table style="background-color: #52aec6" width="100%">
                                                    <tbody><tr><td></td></tr></tbody>
                                                </table>
                                            </td>
                                        </tr>
                                        <tr height="30">
                                            <td width="50%">
                                                <span class="brName"><h:outputText id="brName" value="#{welcome.branchName}"/></span>
                                                <span class="error"><h:outputText id="message" value="#{welcome.message}"/></span>
                                            </td>
                                            <td width="50%" align="right">
                                                <span class="brName">
                                                    <h:outputText id="title" value="Welcome "/>
                                                    <h:outputText id="userName" value="#{welcome.fullUserName}"/>
                                                </span>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td> 
                                <table style="background-color: #52aec6" width="100%">
                                    <tbody><tr><td></td></tr></tbody>
                                </table>
                                <%-- <rich:toolBar id="menuBar">
                                    <c:forEach var="listMenu" items="#{welcome.menuList}">
                                        <rich:dropDownMenu disabled="#{!listMenu.enabled}">
                                            <f:facet name="label">
                                                <h:panelGroup>
                                                    <h:outputText value="#{listMenu.menuCaption}"/>
                                                </h:panelGroup>
                                            </f:facet>
                                            <c:forEach var="menuItem" items="#{listMenu.menuItems}">
                                                <rich:menuItem value="#{menuItem.menuItemCaption}" action="#{welcome.urlNevigation}"
                                                               disabled="#{!menuItem.enabled}" submitMode="server">
                                                    <f:setPropertyActionListener value="#{menuItem}" target="#{welcome.currentItem}"/>
                                                </rich:menuItem>
                                            </c:forEach>
                                            <c:forEach var="menuGpItem" items="#{listMenu.menuGroupList}">
                                                <rich:menuGroup value="#{menuGpItem.menuGroupCaption}">
                                                    <c:forEach var="menuItem" items="#{menuGpItem.menuItems}" >
                                                        <rich:menuItem value="#{menuItem.menuItemCaption}" action="#{welcome.urlNevigation}"
                                                                       disabled="#{!menuItem.enabled}" submitMode="server">
                                                            <f:setPropertyActionListener value="#{menuItem}" target="#{welcome.currentItem}"/>
                                                        </rich:menuItem>
                                                    </c:forEach>
                                                </rich:menuGroup>
                                            </c:forEach>
                                        </rich:dropDownMenu>
</c:forEach>
                                </rich:toolBar>--%>
                            </td>
                        </tr>
                        <tr>
                            <td width="100%">
                                <%--<rich:panelMenu style="width:200px"  mode="server" iconExpandedGroup="disc" iconCollapsedGroup="disc" 
                                                iconExpandedTopGroup="chevronUp" iconGroupTopPosition="right" iconCollapsedTopGroup="chevronDown">
                                    <c:forEach var="listMenu" items="#{welcome.menuList}">
                                        <rich:panelMenuGroup label="#{listMenu.menuCaption}">
                                            <c:forEach var="menuItem" items="#{listMenu.menuItems}">
                                                <rich:panelMenuItem label="#{menuItem.menuItemCaption}">
                                                    
                                                </rich:panelMenuItem>
                                            </c:forEach>
                                            <c:forEach var="menuGpItem" items="#{listMenu.menuGroupList}">
                                                <rich:panelMenuGroup label="#{menuGpItem.menuGroupCaption}">
                                                    <c:forEach var="menuItem" items="#{menuGpItem.menuItems}" >
                                                        <rich:panelMenuItem label="#{menuItem.menuItemCaption}">
                                                            
                                                        </rich:panelMenuItem>
                                                    </c:forEach>
                                                </rich:panelMenuGroup>
                                            </c:forEach>
                                        </rich:panelMenuGroup>
                                    </c:forEach>
</rich:panelMenu>--%>
                                <h:panelGrid cellpadding="0" cellspacing="0" styleClass="vertical-menu-cell" columnClasses="optionList">
                                    <c:forEach var="listMenu" items="#{welcome.menuList}">

                                        <rich:dropDownMenu disabled="#{!listMenu.enabled}" direction="bottom-right" jointPoint="tr">
                                            <f:facet name="label">
                                                <h:panelGroup>
                                                    <h:outputText value="#{listMenu.menuCaption}"/>
                                                </h:panelGroup>
                                            </f:facet>
                                            <c:forEach var="menuItem" items="#{listMenu.menuItems}">
                                                <rich:menuItem value="#{menuItem.menuItemCaption}" action="#{welcome.urlNevigation}"
                                                               disabled="#{!menuItem.enabled}" submitMode="server" reRender="message">
                                                    <f:setPropertyActionListener value="#{menuItem}" target="#{welcome.currentItem}"/>
                                                </rich:menuItem>
                                            </c:forEach>
                                            <c:forEach var="menuGpItem" items="#{listMenu.menuGroupList}">
                                                <rich:menuGroup value="#{menuGpItem.menuGroupCaption}" direction="bottom-right">
                                                    <c:forEach var="menuItem" items="#{menuGpItem.menuItems}" >
                                                        <rich:menuItem value="#{menuItem.menuItemCaption}" action="#{welcome.urlNevigation}"
                                                                       disabled="#{!menuItem.enabled}" submitMode="server" reRender="message">
                                                            <f:setPropertyActionListener value="#{menuItem}" target="#{welcome.currentItem}"/>
                                                        </rich:menuItem>
                                                    </c:forEach>
                                                </rich:menuGroup>
                                            </c:forEach>
                                        </rich:dropDownMenu>
                                    </c:forEach>
                                </h:panelGrid>
                            </td>
                        </tr>
                        <tr>
                            <td ><div class="footer"> &copy;&nbsp;Copyright <h:outputText value="#{welcome.bnkName}"/></div></td>
                        </tr>
                    </tbody>
                </table>
            </h:form>
            <a4j:region>
                <rich:modalPanel id="pymtAlert" autosized="true" width="310" showWhenRendered="#{welcome.displayflag}">
                    <f:facet name="header">
                        <h:outputText value="Payment Alert" style="color:red;padding-right:15px; font-weight:bold;font-size:15px" />
                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:justify;font-weight:bold;">
                            <tbody>
                                <tr style="height:60px">
                                    <td>
                                        <h:outputText value="#{welcome.pymtAlertMsg}" style="color:red; font-weight:bold;font-size:15px"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center">
                                        <a4j:commandButton value="OK" id="btnYes" action="#{welcome.hidePymtAlertPanel}" reRender="pymtAlert"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
            </a4j:region>
            <a4j:region>
                <rich:modalPanel id="faqAlert" autosized="true" width="700" height="500">
                    <f:facet name="header">
                        <h:outputText value="Frequently Asked Question" style="color:blue;padding-right:15px; font-weight:bold;font-size:12px" />
                    </f:facet>
                    <f:facet name="controls">
                        <h:panelGroup>
                            <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="hidelink2" />
                            <rich:componentControl for="faqAlert" attachTo="hidelink2" operation="hide" event="onclick" />
                        </h:panelGroup>
                    </f:facet>
                    <h:form>
                        <h:panelGroup layout="block" style="height:500px; overflow:auto">
                            <jsp:include page="faq.html"/>
                        </h:panelGroup>
                        <h:panelGroup layout="block" style="backgronud-color:#044F93; text-align:center">
                            <a4j:commandButton value="OK" id="btnYes" style="width:60px" onclick="#{rich:component('faqAlert')}.hide()" reRender="faqAlert"/>
                        </h:panelGroup>
                    </h:form>
                </rich:modalPanel>
            </a4j:region>

            <a4j:region>
                <rich:modalPanel id="verAlert" autosized="true" width="700" height="400" showWhenRendered="#{welcome.showReleaseNote}" >
                    <f:facet name="header">
                        <h:outputText value="Release Note" style="color:blue;padding-right:15px; font-weight:bold;font-size:12px" />
                    </f:facet>
                    <f:facet name="controls">
                        <h:panelGroup>
                            <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="hidelink" />
                            <rich:componentControl for="verAlert" attachTo="hidelink" operation="hide" event="onclick" />
                        </h:panelGroup>
                    </f:facet>
                    <h:form>
                        <h:panelGrid width="100%" style="background-color:#e8eef7;height:360px;" cellpadding="0" cellspacing="0">
                        <rich:extendedDataTable value="#{welcome.versionList}" var="dataItem"
                                                rowClasses="gridrow1,gridrow2" id="taskList" rowKeyVar="row" height="356px"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                            <rich:column width="140">
                                <f:facet name="header"><h:outputText value="Module Name" /></f:facet>
                                <h:outputText value="#{dataItem.moduleName}" />
                            </rich:column>
                            <rich:column width="560px" style="white-space:wrap">
                                <f:facet name="header"><h:outputText value="Version Description" /></f:facet>
                                <h:outputText value="#{dataItem.versionDesc}" />
                            </rich:column>
                        </rich:extendedDataTable>
                        <h:panelGroup layout="block" style="backgronud-color:#044F93; text-align:center">
                            <a4j:commandButton value="OK" id="btnYes" style="width:60px;height:22px" actionListener="#{welcome.hideReleaseNoteAlert()}" onclick="#{rich:component('verAlert')}.hide()" reRender="verAlert"/>
                        </h:panelGroup>
                        </h:panelGrid>
                    </h:form>
                </rich:modalPanel>
            </a4j:region>
        </body>
    </html>
</f:view>
