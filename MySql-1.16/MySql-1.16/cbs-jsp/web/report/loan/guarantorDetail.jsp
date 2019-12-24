<%-- 
    Document   : guarantorDetail
    Created on : Feb 2, 2016, 10:58:32 AM
    Author     : Admin
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>Guarantor Detail </title>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".calInstDate").mask("39/19/9999");
                }
            </script>
        </head>
        <body>
            <h:form id="shareHolders">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{GuarantorDetail.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Guarantor Detail"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{GuarantorDetail.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{GuarantorDetail.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel4"  styleClass="row2" >
                        <h:outputText id="stxtbranchCode" value="Branch Code" styleClass="label"/>
                        <h:selectOneListbox id="selectbranchCode" value="#{GuarantorDetail.branch}" styleClass="ddlist"  style="width:100px" size="1">
                            <f:selectItems id="selectbranchCodeList" value="#{GuarantorDetail.branchList}" />
                        </h:selectOneListbox>
                        <h:outputText value="As On Date" styleClass="label"/>
                        <h:inputText id="toDate" styleClass="input calInstDate" style="width:70px;"  value="#{GuarantorDetail.asOnDt}"/>
                        <h:outputText/>
                        <h:outputText/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel5"  styleClass="row2" style="display:#{GuarantorDetail.displayPanal}">
                        <h:outputText id="lblrepType" value="Report Type:" styleClass="label"/>
                        <h:selectOneListbox id="ddrepType111" value="#{GuarantorDetail.repType}" styleClass="ddlist"  style="width:90px" size="1">
                            <f:selectItems id="selectRepTypeList111" value="#{GuarantorDetail.repTypeList}" />
                            <a4j:support event="onblur" action="#{GuarantorDetail.reportTypeAction}" reRender="ddguarType,selectguarList,errorMsg"> </a4j:support>
                        </h:selectOneListbox> 
                        <h:outputText id="lblguarType" value="Guarantor Type:" styleClass="label"/>
                        <h:selectOneListbox id="ddguarType" value="#{GuarantorDetail.guarantor}" styleClass="ddlist"  style="width:90px" size="1">
                            <f:selectItems id="selectguarList" value="#{GuarantorDetail.guarantorList}" />
                            <a4j:support event="onblur" action="#{GuarantorDetail.guarOptAction}" reRender="lbloptType,txtfrmacno"> </a4j:support>
                        </h:selectOneListbox> 
                        <h:outputText id="lbloptType" value="#{GuarantorDetail.lableButton}" styleClass="label"/>
                        <h:panelGroup id="pId">
                            <h:inputText id="txtfrmacno" value="#{GuarantorDetail.acNo}" styleClass="input" maxlength="12" size="12" disabled="#{GuarantorDetail.disableAcno}">
                                <a4j:support action="#{GuarantorDetail.guarantorAction}" event="onblur" reRender="guarId,errorMsg" />                     
                            </h:inputText>
                            <h:outputLabel id="guarId" value="#{GuarantorDetail.newAcNo}" styleClass="label" style="color:green"></h:outputLabel>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                        <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton  id="print" value="Print Report" action="#{GuarantorDetail.viewReport}" reRender="errorMsg"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{GuarantorDetail.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{GuarantorDetail.refreshPage}" reRender="a1" oncomplete="setMask()"/>
                            <a4j:commandButton  id="exit" value="Exit" action="case1"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
                    <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <f:facet name="header">
                            <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                        </f:facet>
                    </rich:modalPanel>
                </h:panelGrid>
            </h:form>
        </body>
    </html>
</f:view>
