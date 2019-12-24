<%-- 
    Document   : shareDetails
    Created on : Mar 24, 2012, 11:09:59 AM
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
            <title>Share Details Report</title>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
            <script type="text/javascript">
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".frDt").mask("39/19/9999");
                    jQuery(".toDt").mask("39/19/9999");
                }
            </script>
        </head>
        <body>
            <h:form id="shareDetails">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{shareDetails.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Share Details Report"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{shareDetails.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{shareDetails.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel1" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%">
                        <h:outputLabel value="Branch" styleClass="label"/>
                        <h:selectOneListbox id="branchddl" size="1" value="#{shareDetails.branch}" styleClass="ddlist">
                            <f:selectItems value="#{shareDetails.branchList}" />
                        </h:selectOneListbox>
                        <h:outputText value="Status" styleClass="label"/>
                        <h:selectOneListbox id="statusddl" size="1" value="#{shareDetails.status}" styleClass="ddlist">
                            <f:selectItem itemLabel="ALL" itemValue="ALL" />
                            <f:selectItem itemLabel="ACTIVE" itemValue="A" />
                            <f:selectItem itemLabel="INACTIVE" itemValue="C" />
                        </h:selectOneListbox>                       
                        <h:outputLabel value="Order By" styleClass="label"/>
                        <h:selectOneListbox id="orderby" size="1" value="#{shareDetails.orderBy}" styleClass="ddlist">
                            <f:selectItems value="#{shareDetails.orderByList}" />
                        </h:selectOneListbox>               
                    </h:panelGrid>
                    <h:panelGrid id="panel2" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">  
                        <h:outputText value="Issue From Date" styleClass="label"/>
                        <h:inputText id="txtacno" value="#{shareDetails.frDt}" styleClass="input frDt" size="12" >
                            <f:convertDateTime pattern="dd/MM/yyyy" />
                            <a4j:support event="onblur" focus="frDt" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputText id="newAcno" value="Issue To Date" styleClass="label" />
                        <h:inputText id="frDt" styleClass="input frDt" size="12" value="#{shareDetails.toDt}" >
                            <f:convertDateTime pattern="dd/MM/yyyy" />
                            <a4j:support event="onblur" focus="print" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel value="Report Type" styleClass="label"/>
                        <h:selectOneListbox id="reportId" size="1" value="#{shareDetails.reportType}" styleClass="ddlist">
                            <f:selectItems value="#{shareDetails.reportTypeList}" />
                            <a4j:support event="onblur" action="#{shareDetails.onReportTypeAction}" reRender="btnDownload"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                        <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton  id="print" value="Print Report" action="#{shareDetails.viewReport}" reRender="errorMsg"/>
                            <h:commandButton id="btnDownload"  value="#{shareDetails.button}" action="#{shareDetails.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="reset" value="Refresh" actionListener="#{shareDetails.refreshPage}" reRender="errorMsg,txtacno,newAcno,frDt,toDt,btnDownload"/>
                            <a4j:commandButton  id="exit" value="Exit" action="case1"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
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
