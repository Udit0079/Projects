<%-- 
    Document   : termLoanDetail
    Created on : 29 Mar, 2018, 10:54:47 AM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Rbi Inspection Report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".calInstDate").mask("39/19/9999");
                }

            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="mainPanelGrid" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{TermLoanDetail.todayDate}"/>
                        </h:panelGroup>

                        <h:outputLabel styleClass="headerLabel" value="Rbi Inspection Report"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{TermLoanDetail.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="grdpane6" style="height:30px;text-align:center" styleClass="row2" width="100%" >
                        <h:outputText id="stxtError"  styleClass="error" value="#{TermLoanDetail.msg}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col15,col15,col15,col15,col15,col15,col15,col1" columns="8" id="gridPanel4"  styleClass="row2">         
                        <h:outputText value="Branch Wise:" styleClass="label"/>
                        <h:selectOneListbox id="branch" size="1" value="#{TermLoanDetail.branchCode}" styleClass="ddlist">
                            <f:selectItems value="#{TermLoanDetail.branchCodeList}" />
                        </h:selectOneListbox>
                        <h:outputLabel value="Account Nature" styleClass="label"/>
                        <h:selectOneListbox id="ddNature" value="#{TermLoanDetail.acNature}" styleClass="ddlist" size="1" style="width:70px">
                            <f:selectItems value="#{TermLoanDetail.acNatureList}"/>
                            <a4j:support actionListener="#{TermLoanDetail.getAcTypeForNature}" event="onblur" reRender="selectListBox" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblAcType" styleClass="label"  value="Account Type" />
                        <h:selectOneListbox id="selectListBox" value="#{TermLoanDetail.selectAcType}" styleClass="ddlist"  style="width:70px" size="1" >
                            <f:selectItems id="selectAcTypeList" value="#{TermLoanDetail.selectAcTypeList}" />
                        </h:selectOneListbox>  
                        <h:outputLabel id="lblToDate" styleClass="label"  value="As On Date" />
                        <h:panelGroup styleClass="label">
                            <h:inputText id="txtDate"   styleClass="input calInstDate" style="width:70px;"  value="#{TermLoanDetail.date}"/>
                            <font color="purple">DD/MM/YYYY</font>
                            </h:panelGroup>
                            <h:outputLabel/>
                        </h:panelGrid>
                        <h:panelGrid columns="2" id="gridpanel5" style="width:100%;text-align:center;" styleClass="footer" >
                            <h:panelGroup id="btnPanel">
                             <%--   <a4j:commandButton  id="btnView" value="View Report"   action="#{TermLoanDetail.viewReport}" reRender="stxtError,mainPanelGrid,txtDate,branch,ddNature,selectListBox" oncomplete="setMask()"/>   --%>
                                <h:commandButton id="btnExcPrint" value="Download Excel" actionListener="#{TermLoanDetail.excelPrint}"/>
                                <a4j:commandButton id="btnRefresh" value="Refresh"  action="#{TermLoanDetail.refreshForm}"  reRender="mainPanelGrid,txtDate,branch,ddNature,selectListBox" oncomplete="setMask()"/>
                                <a4j:commandButton id="btnExit" value="Exit"  action="#{TermLoanDetail.exitAction}" reRender="mainPanelGrid" />
                            </h:panelGroup>
                        </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
            </a4j:form>
        </body>
    </html>
</f:view>
