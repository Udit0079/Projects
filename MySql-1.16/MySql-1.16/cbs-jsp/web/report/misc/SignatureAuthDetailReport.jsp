<%--
    Document   : SignatureAuthDetailReport
    Created on : Sep 13, 2017, 4:34:24 PM
    Author     : Admin
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
            <title>Authorized Signature Detail</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                var row;
                function setMask() {
                    jQuery(".calfrm").mask("39/19/9999");
                    jQuery(".calto").mask("39/19/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="authSigDetail">
                <h:panelGrid columns="1" id="mainPanelGrid" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{SignatureAuthDetailReport.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Authorized Signature Detail Report"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{SignatureAuthDetailReport.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="grdpane6" style="height:30px;text-align:center" styleClass="row2" width="100%">
                        <h:outputText id="stxtError" styleClass="error" value="#{SignatureAuthDetailReport.msg}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="4" columnClasses="col13,col13,col13,col13" id="gridpanel1" styleClass="row1" width="100%">
                        <h:outputText value="A/c Nature"styleClass="label"/>
                        <h:selectOneListbox id="ddacType" size="1" value="#{SignatureAuthDetailReport.acnoNature}" styleClass="ddlist">
                            <f:selectItems value="#{SignatureAuthDetailReport.acnoNatureList}"/>
                            <a4j:support action="#{SignatureAuthDetailReport.getAcTypeByAcNature}" event="onblur" reRender="selectListBox" focus="selectListBox"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblAcType" styleClass="headerLabel" value="Account Type"/>
                        <h:selectOneListbox id="selectListBox" value="#{SignatureAuthDetailReport.selectAcType}" styleClass="ddlist" size="1">
                            <f:selectItems id="selectAcTypeList" value="#{SignatureAuthDetailReport.selectAcTypeList}"/>
                            <a4j:support action="#{SignatureAuthDetailReport.ddAcTypeProcessValueChange}" event="onblur" reRender="ddAgent,agentLbl" focus="ddOpt"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columns="4" columnClasses="col13,col13,col13,col13" id="gridpanel2" styleClass="row2" width="100%" >
                        <h:outputText value="Option" styleClass="label"/>
                        <h:selectOneListbox id="ddOpt" size="1" value="#{SignatureAuthDetailReport.optType}" styleClass="ddlist">
                            <f:selectItems value="#{SignatureAuthDetailReport.optTypeLst}"/>
                            <a4j:support action="#{SignatureAuthDetailReport.changeOnOption}" event="onblur" reRender="txtFromAcno,lblFromAc,txtToAcno,lblToAc,calfrm,calto" oncomplete="setMask();" focus="#{SignatureAuthDetailReport.focusId}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="agentLbl" value="Agent Code : " styleClass="label" style="display:#{SignatureAuthDetailReport.displayAgCode}"/>
                        <h:selectOneListbox id="ddAgent" styleClass="ddlist" value="#{SignatureAuthDetailReport.agentCode}" size="1" style="width:70px;display:#{SignatureAuthDetailReport.displayAgCode}">
                            <f:selectItems value="#{SignatureAuthDetailReport.agentCodeList}"/>
                             <a4j:support focus="txtFromAcno"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columns="4" columnClasses="col13,col13,col13,col13" id="gridpanel3" styleClass="row1" width="100%">
                        <h:outputLabel id="label6" styleClass="label" value="From A/c. No. : "/>
                        <h:panelGroup id="groupPanelAcNo" layout="block">
                            <h:inputText value="#{SignatureAuthDetailReport.txtFromAcno}" id="txtFromAcno" styleClass="input" style="width:80px" disabled="#{SignatureAuthDetailReport.disableFrAc}"/>
                            <h:outputLabel value="#{SignatureAuthDetailReport.lblFromAc}" id="lblFromAc"/>
                        </h:panelGroup>
                        <h:outputLabel id="label7" styleClass="label" value="To A/c. No. : "/>
                        <h:panelGroup id="groupPanelAcNo1" layout="block">
                            <h:inputText value="#{SignatureAuthDetailReport.txtToAcno}" id="txtToAcno" styleClass="input" style="width:80px" disabled="#{SignatureAuthDetailReport.disableToAc}" >
                                <a4j:support action="#{SignatureAuthDetailReport.txtToAcnoProcessValueChange}" event="onblur" reRender="lblFromAc,lblToAc,stxtError" oncomplete="setMask();" focus="btnView"/>
                            </h:inputText>
                            <h:outputLabel value="#{SignatureAuthDetailReport.lblToAc}" id="lblToAc"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columns="4" columnClasses="col13,col13,col13,col13" id="gridpanel4" styleClass="row2" width="100%">
                        <h:outputText value="From Date" styleClass="label"/>
                        <h:panelGroup id="groupPanelapptDt" layout="block">
                            <h:inputText id="calfrm" styleClass="input calfrm" style="width:70px;setMask()" maxlength="10" value="#{SignatureAuthDetailReport.frmDt}" disabled="#{SignatureAuthDetailReport.disableFrDt}"/>
                            <h:outputLabel id="lblapptDT" styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                        </h:panelGroup>
                        <h:outputText value="To Date" styleClass="label"/>
                        <h:panelGroup id="groupPanelapptDt1" layout="block">
                            <h:inputText id="calto" styleClass="input calto" style="width:70px;setMask()" maxlength="10" value="#{SignatureAuthDetailReport.toDt}" disabled="#{SignatureAuthDetailReport.disableToDt}">
                                <a4j:support focus="btnView"/>
                            </h:inputText>
                            <h:outputLabel id="lblapptDT1" styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columns="2" id="gridpanel5" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnView" value="View" action="#{SignatureAuthDetailReport.viewReport}" reRender="stxtError,mainPanelGrid"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{SignatureAuthDetailReport.refreshForm}" reRender="mainPanelGrid"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{SignatureAuthDetailReport.exitAction}" reRender="mainPanelGrid"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>