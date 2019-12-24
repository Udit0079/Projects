
<%-- 
    Document   : TransactionDetails
    Created on : 22 Jun, 2013, 12:12:36 PM
    Author     : Nishant Srivastava
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
            <title>Outward Fund Transfer Report</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{TransactionDetails.todayDate}"/>
                        </h:panelGroup>

                        <h:outputLabel styleClass="headerLabel" value="Outward Fund Transfer Report"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{TransactionDetails.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="grdpane6" style="height:30px;text-align:center" styleClass="row2" width="100%" >
                        <h:outputText id="stxtError"  styleClass="error" value="#{TransactionDetails.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col1,col3,col3,col3,col3,col3" columns="6" id="gridpanel1"   style="height:30px;" styleClass="row1" width="100%" >
                        <h:outputLabel id="lblReportTypeList" styleClass="headerLabel"  value="Report type"  />
                        <h:selectOneListbox id="reportTypeListBox" value="#{TransactionDetails.reportType}" styleClass="ddlist" size="1"  style="width:120px" >
                            <f:selectItems id="reportTypeList" value="#{TransactionDetails.reportTypeList}" />
                         <a4j:support event="onblur" action="#{TransactionDetails.transferModeOperation}" reRender="stxtMsg,ddNeftBankName,lblNeftBankName"/>
                        </h:selectOneListbox> 
                        <h:outputLabel id="lblbranchCodeList" styleClass="headerLabel"  value="Branch"  />
                        <h:selectOneListbox id="branchCodeListBox" value="#{TransactionDetails.brCode}" styleClass="ddlist" size="1"  style="width:120px" >
                            <f:selectItems id="branchCodeList" value="#{TransactionDetails.branchCodeList}" />
                        </h:selectOneListbox> 
                        <h:outputLabel id="lblNeftBankName" styleClass="label" style="display:#{TransactionDetails.displaybnkNM}" value="Bank Name"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddNeftBankName" styleClass="ddlist" size="1" style="width:100px;display:#{TransactionDetails.displaybnkNM}" value="#{TransactionDetails.neftBankName}">
                                <f:selectItems value="#{TransactionDetails.neftBankNameList}"/>
                                <a4j:support event="onblur" />
                            </h:selectOneListbox>    
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col1,col3,col3,col3,col3,col3" columns="6" id="gridpanel2"   style="height:30px;" styleClass="row1" width="100%" >
                        <h:outputLabel id="lblStatus" styleClass="headerLabel"  value="Status"  />
                        <h:selectOneListbox id="statusListBox" value="#{TransactionDetails.statusType}" styleClass="ddlist" size="1"  style="width:120px" >
                            <f:selectItems id="statusTypeList" value="#{TransactionDetails.statusTypeList}" />
                        </h:selectOneListbox> 
                        <h:outputLabel id="lblToDate" styleClass="headerLabel"  value="From Date"  />
                        <h:panelGroup styleClass="label">
                            <h:inputText id="txtDate"   styleClass="input calInstDate" style="setMask();"  value="#{TransactionDetails.date}" size="10"/>
                            <font color="purple">DD/MM/YYYY</font>
                            </h:panelGroup>
                           
                            <h:outputLabel id="lblToDate11" styleClass="headerLabel"  value="To Date"  />
                            <h:panelGroup styleClass="label">
                                <h:inputText id="txtDate11"   styleClass="input calInstDate" style="setMask();"  value="#{TransactionDetails.toDt}" size="10"/>
                            <font color="purple">DD/MM/YYYY</font>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid columns="2" id="gridpanel5" style="width:100%;text-align:center;" styleClass="footer" >
                            <h:panelGroup id="btnPanel">
                                <h:commandButton id="btnDownload"  value="PDF Download" action="#{TransactionDetails.btnPdfAction}"  styleClass="color:#044F93;text-decoration:none;"/>
                                <a4j:commandButton  id="btnView" value="View Report"   action="#{TransactionDetails.viewReport}" reRender="stxtError,mainPanelGrid,txtDate" oncomplete="#{rich:element('txtDate')}.style=setMask()"/>
                                <a4j:commandButton id="btnRefresh" value="Refresh"  action="#{TransactionDetails.refreshForm}"  reRender="mainPanelGrid,txtDate" oncomplete="#{rich:element('txtDate')}.style=setMask()"/>
                                <a4j:commandButton id="btnExit" value="Exit"  action="#{TransactionDetails.exitAction}" reRender="mainPanelGrid" />
                            </h:panelGroup>
                        </h:panelGrid>                                                                                     
                        <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                        <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <f:facet name="header">
                                <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                            </f:facet>
                        </rich:modalPanel>
                    </h:panelGrid>
                </a4j:form>
        </body>
    </html>
</f:view>
