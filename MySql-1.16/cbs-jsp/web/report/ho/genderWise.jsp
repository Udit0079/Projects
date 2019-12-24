<%-- 
    Document   : genderWise
    Created on : Mar 22, 2013, 11:08:10 AM
    Author     : Athar Reza
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Gender_Wise_Report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".asOnDate").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="GenderWiseReport">
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/> 
                    </f:facet> 
                </rich:modalPanel>
                <h:panelGrid id="PanelGridMain" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="HeaderPanel" columns="3"  style="text-align:center;" styleClass="header" width="100%">
                        <h:panelGroup id="groupPanel1" layout="block">         
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{FdGenderWise.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="Gender Wise Report"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUsername" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="otUsername" styleClass="output" value="#{FdGenderWise.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>   
                    <h:panelGrid  id="errPanel" style="text-align:center" styleClass="row1">
                        <h:outputText id="errmsg" value="#{FdGenderWise.message}" styleClass="error"/>
                    </h:panelGrid> 
                    <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="gridPanel4"  styleClass="row2">
                        <h:outputText id="lblacType" value="Branch Wise" styleClass="label"/>
                        <h:selectOneListbox id="ddacType111" value="#{FdGenderWise.branchCode}" styleClass="ddlist"  style="width:90px" size="1">
                            <f:selectItems id="selectRepTypeList11" value="#{FdGenderWise.branchCodeList}" />
                        </h:selectOneListbox> 
                        <h:outputLabel id="lblasOnDate" value="As On Date" styleClass="label"/>
                        <h:inputText id="asOnDate" styleClass="input asOnDate" maxlength="10" value="#{FdGenderWise.asOnDate}" style="width:90px;setMask();"/>     
                    </h:panelGrid>                   
                    <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="gridPanel5"  styleClass="row1">
                        <h:outputLabel id="lblFromYear" value="From Year" styleClass="label"/>
                        <h:inputText id="frYear" styleClass="input frYear" maxlength="10" value="#{FdGenderWise.fromYear}" style="width:90px;setMask();"/>
                        <h:outputLabel id="lbltoYear"  value="To Year" styleClass="label" />
                        <h:inputText id="toYear" styleClass="input Year" maxlength="10" value="#{FdGenderWise.toYear}" style="width:90px;setMask();"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="gridPanel6"  styleClass="row2">
                        <h:outputLabel id="lblCreditAmount" styleClass="label"  value=" Amount"/>
                        <h:inputText id="txtcrAmt"  style="width:90px" value="#{FdGenderWise.amount}" styleClass="input">
                            <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                        </h:inputText> 
                        <h:outputLabel id="lblOutputType" value="Output Type" styleClass="label"/>
                        <h:selectOneListbox id="ddacType112" value="#{FdGenderWise.outputType}" styleClass="ddlist"  style="width:90px" size="1">
                            <f:selectItems id="selectRepTypeList12" value="#{FdGenderWise.outputTypeList}" />
                        </h:selectOneListbox>                      
                    </h:panelGrid>
                    <h:panelGrid id="FooterPanel" bgcolor="#fff" style="text-align:center;" styleClass="footer" width="100%">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnPrint" value="View Report" action="#{FdGenderWise.PrintViwe}" reRender="errmsg"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{FdGenderWise.refresh}" reRender="PanelGridMain" oncomplete="setMask()"/>
                            <a4j:commandButton id="btnClose" value="Exit" action="#{FdGenderWise.closeAction}" reRender="errmsg"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>

