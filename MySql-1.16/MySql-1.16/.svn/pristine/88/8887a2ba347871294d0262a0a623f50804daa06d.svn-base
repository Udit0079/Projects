<%-- 
    Document   : fdAccountDetail
    Created on : Dec 7, 2013, 10:50:56 AM
    Author     : Athar Reza
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
            <title> FD Account Detail</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery){
                    setMask();
                });
                var row;
                function setMask(){
                    jQuery(".calInstDate").mask("39/19/9999");
                }
            </script>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <h:form id="InterestFdReport">
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{FdAccountDetails.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="FD Account Detail Report"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUsername" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="otUsername" styleClass="output" value="#{FdAccountDetails.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errPanel" style="text-align:center" styleClass="row1">
                        <h:outputText id="errmsg" value="#{FdAccountDetails.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="gridPanel4" styleClass="row2"> 
                        <h:outputLabel value="Branch" styleClass="label"/>
                        <h:selectOneListbox id="branchddl" size="1" value="#{FdAccountDetails.branch}" styleClass="ddlist">
                            <f:selectItems value="#{FdAccountDetails.branchList}" />
                        </h:selectOneListbox>
                        <h:outputLabel id="lblacType" styleClass="label" value="A/c. Type : "/>
                        <h:selectOneListbox id="ddAcType" styleClass="ddlist" value="#{FdAccountDetails.fdAcType}" size="1" style="width:140px">
                            <f:selectItems value="#{FdAccountDetails.fdAcTypeList}"/>                            
                        </h:selectOneListbox>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="gridPanel5" styleClass="row1">
                        <h:outputLabel id="lblFromAmt" value="From Amount" styleClass="label"/>
                        <h:inputText id="txtFrAmt" value="#{FdAccountDetails.frAmt}" styleClass="input" maxlength="15" size="11"/>
                        <h:outputLabel id="lblToAmt" value="To Amount" styleClass="label"/>
                        <h:inputText id="txtToAmt" value="#{FdAccountDetails.toAmt}" styleClass="input" maxlength="15" size="11"/>
                    </h:panelGrid>                  
                    <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="gridPanel6" styleClass="row2">
                        <h:outputLabel id="lblFromDate" value="From Date" styleClass="label"/>
                        <h:panelGroup id="groupPanelapptDt" layout="block">
                            <h:inputText id="calfrm" styleClass="input calInstDate" style="setMask()" size="11" maxlength="10" value="#{FdAccountDetails.calFromDate}"/>
                            <h:outputLabel id="lblapptDT" styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                        </h:panelGroup>                  
                        <h:outputLabel id="lbltoDate" value="To Date" styleClass="label"/>
                        <h:panelGroup id="groupPanelapptDt1" layout="block">
                            <h:inputText id="calto" styleClass="input calInstDate" style="setMask()" size="11" maxlength="10" value="#{FdAccountDetails.caltoDate}"/>
                            <h:outputLabel id="lblapptDT1" styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="FooterPanel" bgcolor="#fff" style="text-align:center;" styleClass="footer">
                        <a4j:region id="btnRgn">
                            <h:panelGroup id="btnPanel">
                                <h:commandButton id="btnPrint" value="Download Excel" actionListener="#{FdAccountDetails.excelPrint}"/>
                                <a4j:commandButton id="btnRefresh" value="Refresh" action="#{FdAccountDetails.refreshAction}" oncomplete="#{rich:element('calfrm')}.style=setMask();
                                                   #{rich:element('calto')}.style=setMask();" reRender="errPanel,gridPanel4,gridPanel5,gridPanel6"/>
                                <a4j:commandButton action="#{FdAccountDetails.exitAction}" value="Exit" reRender="errPanel,gridPanel4,gridPanel5,gridPanel6"/>
                            </h:panelGroup>
                        </a4j:region>                        
                    </h:panelGrid>
                </h:panelGrid>
            </h:form>
        </body>
    </html>
</f:view>
