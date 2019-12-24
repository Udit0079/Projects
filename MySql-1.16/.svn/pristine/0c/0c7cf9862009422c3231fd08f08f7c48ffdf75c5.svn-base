<%-- 
    Document   : cpsmsBatchDetail
    Created on : 29 Jun, 2017, 5:47:24 PM
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
            <title>CPSMS Batch Detail Report</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{cPSMSBatchDtl.todayDate}"/>
                        </h:panelGroup>

                        <h:outputLabel styleClass="headerLabel" value="CPSMS Batch Detail Report"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{cPSMSBatchDtl.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="grdpane6" style="height:30px;text-align:center" styleClass="row2" width="100%" >
                        <h:outputText id="stxtError"  styleClass="error" value="#{cPSMSBatchDtl.msg}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col1,col4,col1,col4,col1,col4" columns="6" id="gridpanel1"   style="height:30px;" styleClass="row1" width="100%" >
                        <h:outputLabel id="lblDate" styleClass="headerLabel"  value="Date"  />
                        <h:panelGroup styleClass="label">
                            <h:inputText id="txtDate"   styleClass="input calInstDate" style="setMask();"  value="#{cPSMSBatchDtl.date}" size="10">
                                <a4j:support event="onblur" action="#{cPSMSBatchDtl.onblurDate()}" reRender="mainPanelGrid" focus="ddMsgId" />

                            </h:inputText>
                            <font color="purple">DD/MM/YYYY</font>
                            </h:panelGroup>
                            <h:outputLabel id="lblMsgId" styleClass="headerLabel"  value="CPSMS Massage Id"  />
                            <h:selectOneListbox id="ddMsgId" value="#{cPSMSBatchDtl.cpsmsMsgId}" styleClass="ddlist" size="1"  style="width:120px" >
                                <f:selectItems value="#{cPSMSBatchDtl.cpsmsMsgIdList}" />
                                <a4j:support event="onblur" action="#{cPSMSBatchDtl.onblurCPSMSMsgId()}" reRender="mainPanelGrid"  focus="ddBatchNo"/>
                            </h:selectOneListbox> 
                            <h:outputLabel id="lblBatchNo" styleClass="headerLabel"  value="CPSMS Batch No."  />
                            <h:selectOneListbox id="ddBatchNo" value="#{cPSMSBatchDtl.cpsmsBatchNo}" styleClass="ddlist" size="1"  style="width:120px" >
                                <f:selectItems value="#{cPSMSBatchDtl.cpsmsBatchNoList}" />
                            </h:selectOneListbox> 
                        </h:panelGrid>

                    <h:panelGrid columns="2" id="gridpanel5" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{cPSMSBatchDtl.downloadPdfreport()}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton  id="btnView" value="View Report"   action="#{cPSMSBatchDtl.viewReport()}" reRender="stxtError,mainPanelGrid,txtDate" oncomplete="#{rich:element('txtDate')}.style=setMask()"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh"  action="#{cPSMSBatchDtl.btnRefreshAction()}"  reRender="mainPanelGrid,txtDate" oncomplete="#{rich:element('txtDate')}.style=setMask()"/>
                            <a4j:commandButton id="btnExit" value="Exit"  action="#{cPSMSBatchDtl.btnExitAction()}" reRender="mainPanelGrid" />
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
