<%-- 
    Document   : RdInstallment
    Created on : Apr 25, 2013, 11:15:10 AM
    Author     : sipl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>RD INSTALLMENT REPORT </title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".toDate").mask("39/19/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="rdInstallment">
                <h:panelGrid columns="1" id="mainPanel" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="blockPanel" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{RdInstallment.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="RD INSTALLMENT REPORT"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{RdInstallment.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{RdInstallment.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel1" columns="5" columnClasses="col13,col13,col13,col1,col17" styleClass="row2" width="100%">
                        <h:outputLabel id="lblAccountWise" styleClass="label" value="Option : "></h:outputLabel>
                        <h:selectOneListbox id="ddAccountWise" styleClass="ddlist" size="1" style="width:100px;" value="#{RdInstallment.option}">
                            <f:selectItems value="#{RdInstallment.optionList}"/>
                            <a4j:support action="#{RdInstallment.setOptions}" event="onblur" reRender="txtAccountNo,toDate" 
                                         oncomplete="if(#{RdInstallment.accFocus=='true'}){#{rich:element('txtAccountNo')}.focus();}
                                         else {#{rich:element('toDate')}.focus();} setMask();"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblAccountNo" styleClass="label" value="Account No. :"></h:outputLabel>
                        <h:inputText id="txtAccountNo" styleClass="input" maxlength="#{RdInstallment.acNoMaxLen}" value="#{RdInstallment.oldAccNo}" disabled="#{RdInstallment.accWise}" size="12">
                            <a4j:support action="#{RdInstallment.setAccount}" event="onblur" focus="toDate" reRender="errorMsg,stxtAccNo"/>
                        </h:inputText>
                        <h:outputText id="stxtAccNo" styleClass="output" value="#{RdInstallment.accNo}"/>
                    </h:panelGrid>  
                    <h:panelGrid id="panel2" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row1" width="100%">    
                        <h:outputText value="As On Date" styleClass="label"/>
                        <h:inputText id="toDate" styleClass="input toDate" style="setMask();width:70px;"  value="#{RdInstallment.toDt}">
                            <a4j:support event="onblur" actionListener="#{RdInstallment.asOnDateAction}" focus="viewBtn" reRender="errorMsg"/>
                        </h:inputText>
                        <h:outputText/>
                        <h:outputText/>
                    </h:panelGrid>                    
                    <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                        <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id ="viewBtn" value="Print Report" action="#{RdInstallment.viewReport}" reRender="errorMsg,toDate" oncomplete="#{rich:element('toDate')}.style=setMask()"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{RdInstallment.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton value="Refresh" action="#{RdInstallment.refresh}" oncomplete="#{rich:element('toDate')}.style=setMask()" reRender="errorMsg,toDate,mainPanel"/>
                            <a4j:commandButton value="Exit" action="#{RdInstallment.exitAction}" oncomplete="#{rich:element('toDate')}.style=setMask()" reRender="errorMsg,toDate,mainPanel"/>
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