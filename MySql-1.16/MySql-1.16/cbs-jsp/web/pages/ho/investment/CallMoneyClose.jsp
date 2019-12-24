<%-- 
    Document   : CallMoneyClose
    Created on : May 6, 2015, 12:20:20 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Call Money Close</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />            
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="maingrid" columns="1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid id="headergrid" columns="3" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{callMoneyClose.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Call Money Close"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{callMoneyClose.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="msggrid" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7;border:1px ridge #BED6F8;">
                        <h:outputText id="message" styleClass="error" value="#{callMoneyClose.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="callCtrlGrid" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row1" width="100%">
                        <h:outputLabel id="lblCtrlNo" styleClass="label" value="Control No"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddCtrlNo" style="width: 120px" styleClass="ddlist"  value="#{callMoneyClose.ctrlNo}" size="1">
                            <f:selectItems value="#{callMoneyClose.ctrlNoList}"/>
                            <a4j:support event="onblur" action="#{callMoneyClose.onBlurCtrlNo}" reRender="message,stxtRoi,stxtDealDt,stxtCompDt,stxtPrinAmt,stxtIntAmt,
                                         stxtIntDr,stxtIntDrAmt,stxtIntCr,stxtIntCrAmt,stxtTotCr,stxtTotCrAmt,stxtTotDr,stxtTotDrAmt,
                                         stxtIntDrName,stxtIntCrName,stxtTotCrName,stxtTotDrName"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblroi" styleClass="label" value="Roi"></h:outputLabel>
                        <h:outputText id="stxtRoi" styleClass="output" value="#{callMoneyClose.roi}"/>
                    </h:panelGrid>
                    <h:panelGrid id="callDateGrid" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row2" width="100%">
                        <h:outputLabel id="lblDealDt" styleClass="label" value="Deal Date"></h:outputLabel>
                        <h:outputText id="stxtDealDt" styleClass="output" value="#{callMoneyClose.dealDt}"/>
                        <h:outputLabel id="lblCompDt" styleClass="label" value="Completion Date"></h:outputLabel>
                        <h:outputText id="stxtCompDt" styleClass="output" value="#{callMoneyClose.compDt}"/>
                    </h:panelGrid>    
                    <h:panelGrid id="callAmtGrid" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row1" width="100%">
                        <h:outputLabel id="lblPrinAmt" styleClass="label" value="Amount"></h:outputLabel>
                        <h:outputText id="stxtPrinAmt" styleClass="output" value="#{callMoneyClose.prinAmt}"/>
                        <h:outputLabel id="lblIntAmt" styleClass="label" value="Interest Amount"></h:outputLabel>
                        <h:outputText id="stxtIntAmt" styleClass="output" value="#{callMoneyClose.intAmt}"/>
                    </h:panelGrid>
                    <h:panelGrid id="callIntDrGrid" columns="5" columnClasses="col13,col17,col1,col13,col13" styleClass="row2" width="100%">
                        <h:outputLabel id="lblIntDr" styleClass="label" value="Intt (Dr) Head"></h:outputLabel>
                        <h:outputText id="stxtIntDr" styleClass="output" value="#{callMoneyClose.intDrHead}"/>
                        <h:outputText id="stxtIntDrName" value="#{callMoneyClose.intDrName}" styleClass="output"/>
                        <h:outputLabel id="lblIntDrAmt" styleClass="label" value="Interest (Dr) Amount"></h:outputLabel>
                        <h:outputText id="stxtIntDrAmt" styleClass="output" value="#{callMoneyClose.intDrAmt}"/>
                    </h:panelGrid>
                    <h:panelGrid id="callIntCrGrid" columns="5" columnClasses="col13,col17,col1,col13,col13" styleClass="row1" width="100%">
                        <h:outputLabel id="lblIntCr" styleClass="label" value="Intt (Cr) Head"></h:outputLabel>
                        <h:outputText id="stxtIntCr" styleClass="output" value="#{callMoneyClose.intCrHead}"/>
                        <h:outputText id="stxtIntCrName" value="#{callMoneyClose.intCrName}" styleClass="output"/>
                        <h:outputLabel id="lblIntCrAmt" styleClass="label" value="Interest (Cr) Amount"></h:outputLabel>
                        <h:outputText id="stxtIntCrAmt" styleClass="output" value="#{callMoneyClose.intCrAmt}"/>
                    </h:panelGrid>
                    <h:panelGrid id="callTotCrGrid" columns="5" columnClasses="col13,col17,col1,col13,col13" styleClass="row2" width="100%">
                        <h:outputLabel id="lblTotCr" styleClass="label" value="Total (Cr) Head"></h:outputLabel>
                        <h:outputText id="stxtTotCr" styleClass="output" value="#{callMoneyClose.totCrHead}"/>
                        <h:outputText id="stxtTotCrName" value="#{callMoneyClose.totCrName}" styleClass="output"/>
                        <h:outputLabel id="lblTotCrAmt" styleClass="label" value="Total (Cr) Amount"></h:outputLabel>
                        <h:outputText id="stxtTotCrAmt" styleClass="output" value="#{callMoneyClose.totCrAmt}"/>
                    </h:panelGrid>
                    <h:panelGrid id="callTotDrGrid" columns="5" columnClasses="col13,col17,col1,col13,col13" styleClass="row1" width="100%">
                        <h:outputLabel id="lblTotDr" styleClass="label" value="Total (Dr) Head"></h:outputLabel>
                        <h:outputText id="stxtTotDr" styleClass="output" value="#{callMoneyClose.totDrHead}"/>
                        <h:outputText id="stxtTotDrName" value="#{callMoneyClose.totDrName}" styleClass="output"/>
                        <h:outputLabel id="lblTotDrAmt" styleClass="label" value="Total (Dr) Amount"></h:outputLabel>
                        <h:outputText id="stxtTotDrAmt" styleClass="output" value="#{callMoneyClose.totDrAmt}"/>
                    </h:panelGrid>
                    <h:panelGrid id="remarkIdGrid" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row2" width="100%">
                        <h:outputLabel id="lblRemark" styleClass="label" value="Remark"/>
                        <h:inputText id="stxtRemark" styleClass="output" style="width: 220px" value="#{callMoneyClose.remark}"/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>    
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnPost" value="Save" oncomplete="#{rich:component('processPanel')}.show();" reRender="maingrid"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{callMoneyClose.resetForm}" reRender="maingrid"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{callMoneyClose.exitBtnAction}" reRender="maingrid"/>
                        </h:panelGroup>
                    </h:panelGrid>    
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="processPanel" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText id="confirmid" value="Are you sure to Close Call Money ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" action="#{callMoneyClose.saveAction}" oncomplete="#{rich:component('processPanel')}.hide();" reRender="maingrid"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('processPanel')}.hide();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>            
        </body>
    </html>
</f:view>