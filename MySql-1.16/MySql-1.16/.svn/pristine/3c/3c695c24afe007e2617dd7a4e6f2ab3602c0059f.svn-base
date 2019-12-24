<%-- 
    Document   : DailyProcess
    Created on : Jan 5, 2011, 2:22:13 PM
    Author     : sipl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Day Begin/Day End Process</title>
        </head>
        <body>
            <a4j:form id="dailyProcessForm">
                <h:panelGrid columns="1" id="a1" width="100%" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{DailyProcess.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Day Begin/Day End Process"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{DailyProcess.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col2,col6" columns="2" id="bodyPanel" style="width:100%;" >
                        <h:panelGrid columns="1" id="rightPanel"  style="width:102%;height:430px;text-align:center;" border="1">
                            <h:panelGroup  style="height:150px;padding-top:20">
                                <h:panelGroup id="dayB" layout="block" style="height:30px">
                                    <a4j:commandButton id="btnDayBegin" value="Day Begin Process" style="width:55%" action="#{DailyProcess.dayBeginProcess}" disabled="#{DailyProcess.dayBeginFlag}" 
                                                       reRender="btnDayBegin,btnDayEnd,leftPanel,btnMEnd,btnYrEnd,errorMsg"/>
                                </h:panelGroup>

                                <h:panelGroup id="dayEnd" layout="block" style="height:30px">
                                    <a4j:commandButton id="btnDayEnd" value="Day End Process" style="width:55%" onclick="#{rich:component('dayEndPanel')}.show();" disabled="#{DailyProcess.dayEndFlag}"/>
                                </h:panelGroup>
                                
                                <h:panelGroup id="monEnd" layout="block" style="height:30px">
                                    <a4j:commandButton id="btnMEnd" value="Month End Process" style="width:55%" onclick="#{rich:component('monthEndPanel')}.show();" disabled="#{DailyProcess.monthEndFlag}" reRender="stxtMonthEndResponse"/>
                                </h:panelGroup>
                                
                                <h:panelGroup id="yrEnd" layout="block" style="height:30px">
                                    <a4j:commandButton id="btnYrEnd" value="Year End Process" style="width:55%" onclick="#{rich:component('yearEndPanel')}.show();" disabled="#{DailyProcess.yearEndFlag}"/>
                                </h:panelGroup>
                            </h:panelGroup>
                        </h:panelGrid>

                        <h:panelGrid id="leftPanel" style="width:100%;height:430px;;text-align:left;" >
                            <h:panelGrid columnClasses="col12,col10" columns="2" id="bodyPanel2" style="width:100%;text-align:left">
                                <h:panelGrid id="leftPanel3" style="width:100%;height:430px;text-align:left" border="1">
                                    <h:outputText id="errorMsg" styleClass="#{DailyProcess.msgStyle}" value="#{DailyProcess.errorMsg}"/>
                                </h:panelGrid>
                                <h:panelGrid id="leftPanel2" style="width:100%;height:430px;" border="1">
                                    <a4j:commandLink id="dayActivityLink" value="Day Activity Report" style="font-weight:bold;padding-left:50px;text-decoration:none;display:#{DailyProcess.reportFlag}"
                                                     action="#{DailyProcess.getDayActivityReport}" styleClass="output" reRender="popUpRepPanel" oncomplete=" #{rich:component('popUpRepPanel')}.show();">
                                    </a4j:commandLink>
                                </h:panelGrid>
                            </h:panelGrid>
                        </h:panelGrid>
                    </h:panelGrid>

                    <h:panelGrid id="poFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <a4j:commandButton id="btnClose" action="#{DailyProcess.exitForm}"value="Exit"  reRender="dayActivityLink"/>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="dayEndPanel" autosized="true" width="250">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/images/modal/close.png" styleClass="hidelink" id="hideLinkDayEnd" />
                        <rich:componentControl for="dayEndPanel" attachTo="hideLinkDayEnd" operation="hide" event="onclick" />
                    </h:panelGroup>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td align="center" width="50%" colspan="2">
                                    <h:outputText id="stxtDayEndResponse" value="Are you sure to execute Day End Process for Today?"style="padding-right:15px;" />
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" ajaxSingle="true" action="#{DailyProcess.dayEndProcess}" onclick="#{rich:component('dayEndPanel')}.hide();" 
                                                       reRender="btnDayBegin,btnDayEnd,leftPanel,btnMEnd,btnYrEnd,errorMsg"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" ajaxSingle="true" onclick="#{rich:component('dayEndPanel')}.hide();return false;"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>


            <rich:modalPanel id="monthEndPanel" autosized="true" width="250">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/images/modal/close.png" styleClass="hidelink" id="hideLinkMonthEnd" />
                        <rich:componentControl for="monthEndPanel" attachTo="hideLinkMonthEnd" operation="hide" event="onclick" />
                    </h:panelGroup>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td align="center" width="50%" colspan="2">
                                    <h:outputText id="stxtMonthEndResponse" value="Are you sure to execute Month End Process for #{DailyProcess.monthEndName}?"style="padding-right:15px;"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" ajaxSingle="true" action="#{DailyProcess.monthEndProcess}" onclick="#{rich:component('monthEndPanel')}.hide();" 
                                                       reRender="btnDayBegin,btnDayEnd,leftPanel,btnMEnd,btnYrEnd,errorMsg"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" ajaxSingle="true" onclick="#{rich:component('monthEndPanel')}.hide();return false;"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>

            <rich:modalPanel id="yearEndPanel" autosized="true" width="250">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/images/modal/close.png" styleClass="hidelink" id="hideLinkYearEnd" />
                        <rich:componentControl for="yearEndPanel" attachTo="hideLinkYearEnd" operation="hide" event="onclick" />
                    </h:panelGroup>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td align="center" width="50%" colspan="2">
                                    <h:outputText id="stxtYearEndResponse" value="Are you sure to execute Year End Process ?" style="padding-right:15px;"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" ajaxSingle="true" action="#{DailyProcess.yearEndProcess()}" onclick="#{rich:component('yearEndPanel')}.hide();" 
                                                       reRender="btnDayBegin,btnDayEnd,leftPanel,btnMEnd,btnYrEnd,errorMsg"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" ajaxSingle="true" onclick="#{rich:component('yearEndPanel')}.hide();return false;"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            
             <rich:modalPanel id="popUpRepPanel" label="Form" width="1000" height="550" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpRepPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Day Activity Report" style="text-align:center;"/>
                    </h:panelGrid>
                </f:facet>
                <f:facet name="controls">
                    <h:outputLink  id="closelink" onclick="#{rich:component('popUpRepPanel')}.hide()">
                        <h:graphicImage value="/resources/images/close.gif" style="border:0" />
                    </h:outputLink>
                </f:facet>  
                <table width="100%">
                    <tbody>
                        <tr>
                            <td>
                                <a4j:include viewId="#{DailyProcess.viewID}" />
                            </td>
                        </tr>
                    </tbody>
                </table>
            </rich:modalPanel>

            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide();" />
            <rich:modalPanel id="wait" autosized="true" width="250" height="60" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tr style="height:40px">
                            <td align="center" width="50%" colspan="2">
                                <h:outputText value="Processing Wait Please..."/>
                            </td>
                        </tr>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
