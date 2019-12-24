<%-- 
    Document   : interestReport
    Created on : Jul 15, 2013, 11:48:26 AM
    Author     : sipl
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
            <title>Interest Report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
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
            <a4j:form id="InterestReport">
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{InterestReport.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="Interest Report"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUsername" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="otUsername" styleClass="output" value="#{InterestReport.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errPanel" style="text-align:center" styleClass="row1">
                        <h:outputText id="errmsg" value="#{InterestReport.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel4" styleClass="row2"> 
                        <h:outputLabel value="Branch" styleClass="label"/>
                        <h:selectOneListbox id="branchddl" size="1" value="#{InterestReport.branch}" styleClass="ddlist">
                            <f:selectItems value="#{InterestReport.branchList}" />
                        </h:selectOneListbox>
                        <h:outputText id="lblrepType" value="Report Type" styleClass="label"/>
                        <h:selectOneListbox id="ddrepType111" value="#{InterestReport.repType}" styleClass="ddlist"  style="width:80px" size="1">
                            <f:selectItems id="selectRepTypeList111" value="#{InterestReport.repTypeList}" />
                            <a4j:support  action="#{InterestReport.reportOption}" event="onblur"reRender="ddAcType,txtFrAmt,txtToAmt,ddtdsApp,btnPrint" oncomplete="setMask();" focus="#{InterestReport.focusId}"/> 
                        </h:selectOneListbox>
                        <h:outputLabel id="lblacType" styleClass="label" value="A/c. Type  "/>
                        <h:selectOneListbox id="ddAcType" styleClass="ddlist" value="#{InterestReport.ddAcType}" size="1" style="width:180px" disabled="#{InterestReport.disableField}">
                            <f:selectItems value="#{InterestReport.ddAcTypeList}"/>                            
                        </h:selectOneListbox> 
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel5" styleClass="row1">
                        <h:outputLabel id="lblFromAmt" value="From Amount" styleClass="label"/>
                        <h:inputText id="txtFrAmt" value="#{InterestReport.frAmt}" styleClass="input" maxlength="15" size="11" disabled="#{InterestReport.disableField}"/>
                        <h:outputLabel id="lblToAmt" value="To Amount" styleClass="label"/>
                        <h:inputText id="txtToAmt" value="#{InterestReport.toAmt}" styleClass="input" maxlength="15" size="11"disabled="#{InterestReport.disableField}"/>
                        <h:outputText id="lbltdsApp" value="Tds Applicable" styleClass="label"/>
                        <h:selectOneListbox id="ddtdsApp" value="#{InterestReport.tdsFlag}" styleClass="ddlist"  style="width:80px" size="1" disabled="#{InterestReport.disableField}">
                            <f:selectItems id="selecttdsApp" value="#{InterestReport.tdsflagList}" />
                        </h:selectOneListbox>
                    </h:panelGrid>                  
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel6" styleClass="row2">
                        <h:outputLabel id="lblFromDate" value="From Date" styleClass="label"/>
                        <h:panelGroup id="groupPanelapptDt" layout="block">
                            <h:inputText id="calfrm" styleClass="input calInstDate" style="setMask()" size="11" maxlength="10" value="#{InterestReport.calFromDate}"/>
                            <h:outputLabel id="lblapptDT" styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                        </h:panelGroup>                  
                        <h:outputLabel id="lbltoDate" value="To Date" styleClass="label"/>
                        <h:panelGroup id="groupPanelapptDt1" layout="block">
                            <h:inputText id="calto" styleClass="input calInstDate" style="setMask()" size="11" maxlength="10" value="#{InterestReport.caltoDate}"/>
                            <h:outputLabel id="lblapptDT1" styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                        </h:panelGroup>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="FooterPanel" bgcolor="#fff" style="text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnPrint" value="Print Report" action="#{InterestReport.printAction}" oncomplete="#{rich:element('calfrm')}.style=setMask();
                                               #{rich:element('calto')}.style=setMask();" reRender="errPanel,gridPanel4,gridPanel5,gridPanel6" disabled="#{InterestReport.disableField}"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{InterestReport.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{InterestReport.refreshAction}" oncomplete="#{rich:element('calfrm')}.style=setMask();
                                               #{rich:element('calto')}.style=setMask();" reRender="errPanel,gridPanel4,gridPanel5,gridPanel6,PanelGridMain"/>
                            <a4j:commandButton action="#{InterestReport.exitAction}" value="Exit" reRender="errPanel,gridPanel4,gridPanel5,gridPanel6"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>