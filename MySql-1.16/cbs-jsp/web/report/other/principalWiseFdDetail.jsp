<%-- 
    Document   : principalWiseFdDetail
    Created on : Sep 6, 2016, 12:06:38 PM
    Author     : Admin
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
            <title>Principal Wise Fd Detail</title>
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
            <a4j:form id="PrincipalWiseReport">
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{PrincipalWiseFdDetail.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="Principal Wise Fd Detail"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUsername" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="otUsername" styleClass="output" value="#{PrincipalWiseFdDetail.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errPanel" style="text-align:center" styleClass="row1">
                        <h:outputText id="errmsg" value="#{PrincipalWiseFdDetail.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel4" styleClass="row2"> 
                        <h:outputLabel value="Branch" styleClass="label"/>
                        <h:selectOneListbox id="branchddl" size="1" value="#{PrincipalWiseFdDetail.branch}" styleClass="ddlist" style="width:80px">
                            <f:selectItems value="#{PrincipalWiseFdDetail.branchList}" />
                        </h:selectOneListbox>
                        <h:outputLabel value="Report Option" styleClass="label"/>
                        <h:selectOneListbox id="dateId" size="1" value="#{PrincipalWiseFdDetail.dateType}" styleClass="ddlist" style="width:80px">
                            <f:selectItems value="#{PrincipalWiseFdDetail.dateTypeList}" />
                            <a4j:support id="idFromDt" event="onblur" action="#{PrincipalWiseFdDetail.dateTypeOnBlur}" oncomplete="setMask();" reRender="calfrm,calto,errmsg" />
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel6" styleClass="row1">
                        <h:outputLabel id="lblFromDate" value="From Date" styleClass="label"/>
                        <h:inputText id="calfrm" styleClass="input calInstDate" style="setMask()" size="11" maxlength="10" value="#{PrincipalWiseFdDetail.calFromDate}" disabled="#{PrincipalWiseFdDetail.disDateType}"/>
                        <h:outputLabel id="lbltoDate" value="To Date" styleClass="label"/>
                        <h:inputText id="calto" styleClass="input calInstDate" style="setMask()" size="11" maxlength="10" value="#{PrincipalWiseFdDetail.caltoDate}"/>
                    </h:panelGrid> 
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel5" styleClass="row2">
                        <h:outputLabel id="lblFromAmt" value="From Amount" styleClass="label"/>
                        <h:inputText id="txtFrAmt" value="#{PrincipalWiseFdDetail.frAmt}" styleClass="input" maxlength="15" size="11" />
                        <h:outputLabel id="lblToAmt" value="To Amount" styleClass="label"/>
                        <h:inputText id="txtToAmt" value="#{PrincipalWiseFdDetail.toAmt}" styleClass="input" maxlength="15" size="11" />
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel7" styleClass="row1">

                        <h:outputText id="lblrepType" value="Report Type" styleClass="label"/>
                        <h:selectOneListbox id="ddrepType111" value="#{PrincipalWiseFdDetail.repType}" styleClass="ddlist"  style="width:80px" size="1">
                            <f:selectItems id="selectRepTypeList111" value="#{PrincipalWiseFdDetail.repList}" />
                        </h:selectOneListbox>

                        <h:outputText id="lblactCatg" value="Account Category" styleClass="label"/>
                        <h:selectOneListbox id="ddactCatg111" value="#{PrincipalWiseFdDetail.actCatgory}" styleClass="ddlist"  style="width:80px" size="1">
                            <f:selectItems id="selectactCatgList111" value="#{PrincipalWiseFdDetail.actCatgoryList}" />
                        </h:selectOneListbox>

                    </h:panelGrid>

                    <h:panelGrid id="FooterPanel" bgcolor="#fff" style="text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnPrint" value="Print Report" action="#{PrincipalWiseFdDetail.printAction}" oncomplete="setMask();" reRender="errPanel,PanelGridMain"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{PrincipalWiseFdDetail.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{PrincipalWiseFdDetail.refreshAction}" oncomplete="setMask();" reRender="errPanel,PanelGridMain"/>
                            <a4j:commandButton action="#{PrincipalWiseFdDetail.exitAction}" value="Exit" reRender="errPanel,PanelGridMain"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>