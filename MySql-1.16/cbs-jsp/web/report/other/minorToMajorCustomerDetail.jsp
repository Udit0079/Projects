<%-- 
    Document   : minorToMajorCustomerDetail
    Created on : 28 Aug, 2018, 3:22:38 PM
    Author     : root
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <title>Minor To Major Customer Details</title>
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".frDt").mask("99/99/9999");
                    jQuery(".toDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="minortomajorcustomerdetail">
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
                <h:panelGrid id="PanelGridMain" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="HeaderPanel" columns="3"  style="text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{MinorToMajorCustomerDetail.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="Minor To Major Customer Details"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUsername" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="otUsername" styleClass="output" value="#{MinorToMajorCustomerDetail.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="errPanel" style="text-align:center" styleClass="row1">
                        <h:outputText id="errmsg" value="#{MinorToMajorCustomerDetail.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="8" id="gridPanel4" style="text-align:center;" styleClass="row2">
                        <h:outputLabel value="Branch Wise" styleClass="label"/>
                        <h:selectOneListbox id="branch" size="1" value="#{MinorToMajorCustomerDetail.branchCode}" styleClass="ddlist" style="width:70px">
                            <f:selectItems value="#{MinorToMajorCustomerDetail.branchCodeList}" /> 
                        </h:selectOneListbox> 
                        <h:outputLabel value="From Date" styleClass="label"/>
                        <h:inputText id="frDt" styleClass="input frDt" size="12" value="#{MinorToMajorCustomerDetail.frmDt}" style="width:70px">
                            <a4j:support event="onblur" focus="toDt"oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel value="To Date" styleClass="label"/>
                        <h:inputText id="toDt" styleClass="input toDt" size="12" value="#{MinorToMajorCustomerDetail.toDt}" style="width:70px">
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid id="FooterPanel" bgcolor="#fff" style="text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnPrint" action="#{MinorToMajorCustomerDetail.printAction}" type="submit" value="Print Report" reRender="PanelGridMain,errmsg"oncomplete="setMask();"/>
                            <h:commandButton id="btnPDF"  value="PDF Download" actionListener="#{MinorToMajorCustomerDetail.viewPdfReport}"  styleClass="color: #044F93;text-decoration: none;"  ></h:commandButton>
                            <a4j:commandButton id="btnReset" actionListener="#{MinorToMajorCustomerDetail.resetAction}" type="reset" value="Reset" reRender="PanelGridMain,errmsg" oncomplete="setMask();"/>
                            <a4j:commandButton id="btnExit" action="#{MinorToMajorCustomerDetail.exitAction}" value="Exit" reRender="PanelGridMain,errmsg"oncomplete="setMask();"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
