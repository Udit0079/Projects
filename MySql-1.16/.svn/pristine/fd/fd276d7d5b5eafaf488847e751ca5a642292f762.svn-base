<%-- 
    Document   : form1
    Created on : 21 Nov, 2014, 2:20:14 PM
    Author     : root
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
            <title>Form I To VIII</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".calFromDate").mask("99/99/9999");
                    jQuery(".calToDate").mask("99/99/9999");
                }

            </script>
        </head>
        <body>
            <h:form id="loanPeriod">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{form1.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Form I To VIII"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{form1.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{form1.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel1" columns="6" columnClasses="col3,col3,col3,col3,col3,col3"  styleClass="row2" width="100%">
                        <h:outputText value="Branch Wise:" styleClass="label"/>
                        <h:selectOneListbox id="branchId" size="1" value="#{form1.branch}" styleClass="ddlist" style="width:80px">
                            <f:selectItems value="#{form1.branchList}" />
                        </h:selectOneListbox> 
                        <h:outputLabel id="lblMonth" styleClass="label"  value="Month"><font class="required" color="red">*</font></h:outputLabel>
                            <h:panelGroup>
                                <h:selectOneListbox id="ddMonth" styleClass="ddlist" size="1" style="width: 70px" value="#{form1.month}" >
                                    <f:selectItems value="#{form1.monthList}"/>
                                </h:selectOneListbox>
                                <h:inputText id="txtYear" styleClass="input" value="#{form1.year}" maxlength="4" size="5" />
                            </h:panelGroup>
                            <h:outputText value="Report Type:" styleClass="label"/>
                            <h:selectOneListbox id="reportID" size="1" value="#{form1.repType}" styleClass="ddlist" style="width:80px">
                                <f:selectItems value="#{form1.repTypeList}" />
                                <a4j:support action="#{form1.chenageOperation}" event="onblur" reRender="gridPanel5" />
                            </h:selectOneListbox>        
                        </h:panelGrid>
                        <rich:panel header="Officer Information" style="text-align:left;">          
                            <h:panelGrid columnClasses="col3,col3,col3,col3" columns="4" id="gridPanel5"  styleClass="row2" style="display:#{form1.display}">       
                                <h:outputLabel value="Name of the First Signatory :" styleClass="label"/>
                                <h:inputText id="firstNameId" styleClass="input" value="#{form1.name1}" size="25" maxlength="100"/>
                                <h:outputLabel value="Designation of officer(with Stamp) :" styleClass="label"/>
                                <h:inputText id="desigFirstId" styleClass="input" value="#{form1.designation1}" size="25" maxlength="100"/>  
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col3,col3,col3,col3" columns="4" id="gridPanel6"  styleClass="row2">       
                                <h:outputLabel value="Name of the Second Signatory :" styleClass="label"/>
                                <h:inputText id="secNameId" styleClass="input" value="#{form1.name2}" size="25" maxlength="100"/>
                                <h:outputLabel value="Designation of officer(with Stamp) :" styleClass="label"/>
                                <h:inputText id="desigSecId" styleClass="input" value="#{form1.designation2}" size="25" maxlength="100"/> 
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col3,col3,col3,col3" columns="4" id="gridPanel7"  styleClass="row2">       
                                <h:outputLabel value="Name of Concurrent Auditor(With Stamp) :" styleClass="label"/>
                                <h:inputText id="auditorId" styleClass="input" value="#{form1.auditorName}" size="25" maxlength="100"/>
                                <h:outputLabel value="Address :" styleClass="label"/>
                                <h:inputText id="addId" styleClass="input" value="#{form1.auditorAdd}" size="40" maxlength="200"/> 
                            </h:panelGrid> 
                            <h:panelGrid columnClasses="col3,col3,col3,col3" columns="4" id="gridPanel8"  styleClass="row2">       
                                <h:outputLabel value="Place :" styleClass="label"/>
                                <h:inputText id="placeId" styleClass="input" value="#{form1.place}" size="25" maxlength="100"/>
                                <h:outputLabel/>
                                <h:outputLabel/>
                            </h:panelGrid> 
                        </rich:panel>  
                        <h:panelGrid columns="1" id="gridPanel9" style="text-align: center; width: 100%" styleClass="footer">
                            <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                                <h:commandButton id="btnDownload"  value="PDF Download" action="#{form1.btnPdfAction}"  styleClass="color:#044F93;text-decoration:none;"/>
                                <a4j:commandButton id="btnRefresh" value="Refresh" action="#{form1.btnRefreshAction}" reRender="a1" oncomplete="setMask()"/>
                                <a4j:commandButton id="btnClose" value="Exit" action="#{form1.btnExitAction}" reRender="errorMsg"/>
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
