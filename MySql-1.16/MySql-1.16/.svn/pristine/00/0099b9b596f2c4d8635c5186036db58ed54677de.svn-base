<%-- 
    Document   : ChequeHonuredReport
    Created on : 24 Feb, 2012, 1:14:19 PM
    Author     : Zeeshan Waris
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Cheque Honoured Certificate</title>
        </head>
        <body>
            <a4j:form id="ChequeHonuredReport">                
                <h:panelGrid id="PanelGridMain" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="HeaderPanel" columns="3"  style="text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{ChequeHonuredReport.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="Cheque Honoured Certificate"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUsername" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="otUsername" styleClass="output" value="#{ChequeHonuredReport.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="errPanel" style="text-align:center" styleClass="row1">
                        <h:outputText id="errmsg" value="#{ChequeHonuredReport.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3" columns="5" id="gridPanel4"  styleClass="row2"> 
                        <h:outputLabel/>
                        <h:outputLabel id="lblAccountNo" value="Account No" styleClass="label"/>
                        <h:panelGroup  layout="block">
                            <h:inputText id="txtAccountNo" style="width:100px" value="#{ChequeHonuredReport.accountNo}" styleClass="input" maxlength="#{ChequeHonuredReport.acNoMaxLen}">
                                <a4j:support actionListener="#{ChequeHonuredReport.getNewAccountNo}"  event="onblur" focus="txtChequeNo" reRender="stxtStAccountNo,errmsg"/> 
                            </h:inputText>
                            <h:outputText id="stxtStAccountNo" styleClass="output" value="#{ChequeHonuredReport.newAcno}" />
                        </h:panelGroup>
                        <h:outputLabel  value="Cheque No" styleClass="label"/>
                        <h:inputText id="txtChequeNo" style="width: 90px" value="#{ChequeHonuredReport.chequeNo}"styleClass="input" maxlength="10"/>
                    </h:panelGrid>
                    <h:panelGrid id="FooterPanel" bgcolor="#fff" style="text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnPrint" value="Print Report" action="#{ChequeHonuredReport.printAction}"reRender="errmsg"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{ChequeHonuredReport.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{ChequeHonuredReport.refreshAction}"  reRender="errmsg,txtAccountNo,stxtStAccountNo,txtChequeNo"/>
                            <a4j:commandButton action="#{ChequeHonuredReport.exitAction}" value="Exit" reRender="errmsg"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>                
            </a4j:form>
            
                    <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                    <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <f:facet name="header">
                            <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                        </f:facet>
                    </rich:modalPanel>
            
            
        </body>
    </html>
</f:view>
