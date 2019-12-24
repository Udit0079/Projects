<%-- 
    Document   : transactionAggregateReport
    Created on : Dec 17, 2011, 2:18:09 PM
    Author     : admin
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
            <title>Transaction Aggregate Report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                var row;
                function setMask() {
                    jQuery(".calInstDate").mask("39/19/9999");
                }
            </script>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <h:form id="transactionaggregate">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">

                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{TransactionAggregateReport.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Transaction Aggregate Report"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{TransactionAggregateReport.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{TransactionAggregateReport.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel1" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%">
                        <h:outputText value="Account Type" styleClass="label" />
                        <h:selectOneListbox id="ddlAccountType" size="1" styleClass="ddlist" value="#{TransactionAggregateReport.accType}" >
                            <f:selectItems value="#{TransactionAggregateReport.acctTypeList}"  />
                        </h:selectOneListbox>
                        <h:outputText value="Report Type" styleClass="label" />
                        <h:selectOneListbox id="ddlreportType" size="1" styleClass="ddlist" value="#{TransactionAggregateReport.reportType}" >
                            <f:selectItem itemLabel="Cash" itemValue="0" />
                            <f:selectItem itemLabel="Clearing" itemValue="1" />
                            <f:selectItem itemLabel="Transfer" itemValue="2" />
                            <f:selectItem itemLabel="Total Cash(Cr+Dr)" itemValue="00" />
                            <f:selectItem itemLabel="Total Clearing(Cr+Dr)" itemValue="01" />
                            <f:selectItem itemLabel="Total Transfer(Cr+Dr)" itemValue="02" />
                        </h:selectOneListbox>
                        <h:outputText value="Amount:" styleClass="label" />
                        <h:inputText id="txtAmount" value="#{TransactionAggregateReport.amt}" styleClass="input"/>
                    </h:panelGrid>  
                    <h:panelGrid id="panel2" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputLabel/>
                        <h:outputText value="From Date" styleClass="label"/>
                        <h:panelGroup id="groupPanelapptDt" layout="block">
                            <h:inputText id="calfrm" styleClass="input calInstDate"   style="width:70px;" maxlength="10" value="#{TransactionAggregateReport.frmDt}" >
                                <f:convertDateTime pattern="dd/MM/yyyy" />
                            </h:inputText>
                            <h:outputLabel id="lblapptDT" styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                        </h:panelGroup>
                        <h:outputText value="To Date" styleClass="label"/>
                        <h:panelGroup id="groupPanelapptDt1" layout="block">
                            <h:inputText id="calto" styleClass="input calInstDate"   style="width:70px;" maxlength="10" value="#{TransactionAggregateReport.toDt}" >
                                <f:convertDateTime pattern="dd/MM/yyyy" />
                            </h:inputText>
                            <h:outputLabel id="lblapptDT1" styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                        </h:panelGroup>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">  
                        <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton  value="Print Report" action="#{TransactionAggregateReport.viewReport()}"  
                                                oncomplete="#{rich:element('calfrm')}.style=setMask();
                                                #{rich:element('calto')}.style=setMask();"
                                                reRender="errorMsg,groupPanelapptDt,groupPanelapptDt1"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{TransactionAggregateReport.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton value="Reset" action="#{TransactionAggregateReport.refresh()}" oncomplete="setMask();" 
                                               reRender="groupPanelapptDt,groupPanelapptDt1,errorMsg,calfrm,calto,ddlreportType,ddlAccountType,txtAmount"/>
                            <a4j:commandButton   value="Exit" action="case1"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                    <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <f:facet name="header">
                            <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                        </f:facet>
                    </rich:modalPanel>
                </h:panelGrid>
            </h:form>
        </body>
    </html>
</f:view>
