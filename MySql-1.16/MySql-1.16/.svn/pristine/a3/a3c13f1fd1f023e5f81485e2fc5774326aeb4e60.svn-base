<%-- 
    Document   : tdsFormDetails
    Created on : Jun 17, 2015, 4:18:44 PM
    Author     : Administrator
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
            <title>TDS Forms Detail</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
        </head>
        <body>
            <h:form id="TdsFormReport">
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
                <h:panelGrid id="PanelGridMain" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="HeaderPanel" columns="3" style="text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{TdsFormDetail.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="TDS Form Report"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUsername" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="otUsername" styleClass="output" value="#{TdsFormDetail.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errPanel" style="text-align:center" styleClass="row1">
                        <h:outputText id="errmsg" value="#{TdsFormDetail.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="gridPanel4" styleClass="row2"> 
                        <h:outputLabel value="Branch" styleClass="label"/>
                        <h:selectOneListbox id="branchddl" size="1" value="#{TdsFormDetail.branch}" styleClass="ddlist">
                            <f:selectItems value="#{TdsFormDetail.branchList}" />
                        </h:selectOneListbox>
                        <h:outputLabel id="lblacType" styleClass="label" value="A/c. Type : "/>
                        <h:selectOneListbox id="ddAcType" styleClass="ddlist" value="#{TdsFormDetail.fdAcType}" size="1" style="width:70px">
                            <f:selectItems value="#{TdsFormDetail.fdAcTypeList}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="gridPanel5" styleClass="row1"> 
                        <h:outputLabel id="lblrepType" styleClass="label" value="Report Option"/>
                        <h:selectOneListbox id="ddRepType" styleClass="ddlist" value="#{TdsFormDetail.repType}" size="1">
                            <f:selectItems value="#{TdsFormDetail.repTypeList}"/>
                              <a4j:support event="onblur" actionListener="#{TdsFormDetail.onReportOption}" reRender="txtFinan,errmsg" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                        <h:outputText value="Financial Year" styleClass="label"/>
                        <h:panelGroup id="finanId">
                            <h:inputText id="txtFinan" value="#{TdsFormDetail.finYear}" styleClass="input" size="8" maxlength="4" disabled="#{TdsFormDetail.finYearDisable}"onkeyup="this.value=this.value.toUpperCase();">
                                <a4j:support event="onblur" actionListener="#{TdsFormDetail.getNewfYear}" reRender="newFinan,errmsg" oncomplete="setMask();"/>
                            </h:inputText>
                            <h:outputText id="newFinan" value="#{TdsFormDetail.newFinYear}" styleClass="output" style="color:green"/>
                        </h:panelGroup>
                    </h:panelGrid>                    
                    <h:panelGrid id="FooterPanel" bgcolor="#fff" style="text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnPrint" value="Print Report" actionListener="#{TdsFormDetail.printReport}" reRender="errPanel,gridPanel4,gridPanel5"/>
                            <h:commandButton id="btnPDF"  value="PDF Download" actionListener="#{TdsFormDetail.viewPdfReport}"  styleClass="color: #044F93;text-decoration: none;"  ></h:commandButton>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{TdsFormDetail.refreshAction}" reRender="errPanel,gridPanel4,gridPanel5"/>
                            <a4j:commandButton action="#{TdsFormDetail.exitAction}" value="Exit" reRender="errPanel,gridPanel4,gridPanel5"/>
                        </h:panelGroup>                        
                    </h:panelGrid>
                </h:panelGrid>
            </h:form>
        </body>
    </html>
</f:view>