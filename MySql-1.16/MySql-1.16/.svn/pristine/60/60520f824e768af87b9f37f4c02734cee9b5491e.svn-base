<%-- 
    Document   : rdDdsProvisionInterest
    Created on : 22 Mar, 2018, 12:55:04 PM
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
            <title>RD & DDS Provision</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{RdDdsProvisionInterest.todayDate}"/>
                        </h:panelGroup>

                        <h:outputLabel styleClass="headerLabel" value="RD & DDS Provision"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{RdDdsProvisionInterest.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="grdpane6" style="height:30px;text-align:center" styleClass="row2" width="100%" >
                        <h:outputText id="stxtError"  styleClass="error" value="#{RdDdsProvisionInterest.msg}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col15,col15,col15,col15,col15,col15,col15,col1" columns="8" id="gridPanel4"  styleClass="row2">         
                        <h:outputText value="Branch Wise:" styleClass="label"/>
                        <h:selectOneListbox id="branch" size="1" value="#{RdDdsProvisionInterest.branchCode}" styleClass="ddlist">
                            <f:selectItems value="#{RdDdsProvisionInterest.branchCodeList}" />
                        </h:selectOneListbox>
                        <h:outputLabel value="Account Nature" styleClass="label"/>
                        <h:selectOneListbox id="ddNature" value="#{RdDdsProvisionInterest.acNature}" styleClass="ddlist" size="1" style="width:70px">
                            <f:selectItems value="#{RdDdsProvisionInterest.acNatureList}"/>
                            <a4j:support actionListener="#{RdDdsProvisionInterest.getAcTypeForNature}" event="onblur" reRender="selectListBox" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblAcType" styleClass="label"  value="Account Type" />
                        <h:selectOneListbox id="selectListBox" value="#{RdDdsProvisionInterest.selectAcType}" styleClass="ddlist"  style="width:70px" size="1" >
                            <f:selectItems id="selectAcTypeList" value="#{RdDdsProvisionInterest.selectAcTypeList}" />
                        </h:selectOneListbox>  
                        <h:outputLabel id="lblToDate" styleClass="label"  value="To Date" />
                        <h:panelGroup styleClass="label">
                            <h:inputText id="txtDate"   styleClass="input calInstDate" style="width:70px;"  value="#{RdDdsProvisionInterest.date}"/>
                            <font color="purple">DD/MM/YYYY</font>
                            </h:panelGroup>
                            <h:outputLabel/>
                        </h:panelGrid>
                        <h:panelGrid columns="2" id="gridpanel5" style="width:100%;text-align:center;" styleClass="footer" >
                            <h:panelGroup id="btnPanel">
                                <a4j:commandButton  id="btnView" value="View"   action="#{RdDdsProvisionInterest.viewReport}" reRender="stxtError,mainPanelGrid,txtDate,branch,ddNature,selectListBox" oncomplete="setMask()"/>
                                <h:commandButton id="btnDownload"  value="PDF Download" action="#{RdDdsProvisionInterest.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                                <a4j:commandButton id="btnRefresh" value="Refresh"  action="#{RdDdsProvisionInterest.refreshForm}"  reRender="mainPanelGrid,txtDate,branch,ddNature,selectListBox" oncomplete="setMask()"/>
                                <a4j:commandButton id="btnExit" value="Exit"  action="#{RdDdsProvisionInterest.exitAction}" reRender="mainPanelGrid" />
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
