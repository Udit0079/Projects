<%-- 
    Document   : finalBalance
    Created on : Mar 23, 2012, 12:54:15 PM
    Author     : admin
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
            <title>Final Balance Report</title>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
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
            <h:form id="finalBalance">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{finalBalanceReport.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Final Balance Report" />
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{finalBalanceReport.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{finalBalanceReport.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel1" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%">
                        <h:outputText id="lblacType" value="Branch Name:" styleClass="label"/>
                        <h:selectOneListbox id="ddacType111" value="#{finalBalanceReport.alphacode}" styleClass="ddlist"  style="width:100px" size="1">
                            <f:selectItems id="selectRepTypeList11" value="#{finalBalanceReport.alphacodeList}" />
                        </h:selectOneListbox> 
                        <h:outputText value="From Folio Number" styleClass="label"/>
                        <h:panelGroup layout="block">
                        <h:inputText id="txtfrmacno" value="#{finalBalanceReport.frmFolioNoShow}" styleClass="input" maxlength="12" size="12" onkeyup="this.value=this.value.toUpperCase();">
                            <a4j:support action="#{finalBalanceReport.folioFromMethod}" event="onblur" focus="txttoacno"  reRender="stxtFolioFromShow,txttoacno,errorMsg" oncomplete="setMask();"/>                     
                        </h:inputText>
                        <h:outputText id="stxtFolioFromShow" styleClass="output" value="#{finalBalanceReport.frmFolioNo}" />
                        </h:panelGroup>
                        <h:outputText value="To Folio Number" styleClass="label"/>
                        <h:panelGroup layout="block">
                        <h:inputText id="txttoacno" value="#{finalBalanceReport.toFolioNoShow}" styleClass="input" size="12" maxlength="12" onkeyup="this.value=this.value.toUpperCase();">
                            <a4j:support action="#{finalBalanceReport.folioToMethod}" event="onblur" focus="frDt" reRender="stxtFolioToShow,errorMsg" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputText id="stxtFolioToShow" styleClass="output" value="#{finalBalanceReport.toFolioNo}" />
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="panel2" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputText id="newAcno" value="As on Date" styleClass="label" />
                        <h:inputText id="frDt" styleClass="input frDt" size="12" value="#{finalBalanceReport.date}">
                            <f:convertDateTime pattern="dd/MM/yyyy" />
                            <a4j:support event="onblur" focus="print" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel id="lblmember" styleClass="label" value="Account Catageory"/>
                        <h:selectOneListbox id="ddmember" size="1" styleClass="ddlist" value="#{finalBalanceReport.accountCat}" style="width:100px;">
                            <f:selectItems value="#{finalBalanceReport.accountCatList}"/>
                        </h:selectOneListbox>

                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                        <a4j:region id="saveRegion">
                            <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                                <a4j:commandButton  id="print" value="Print Report" action="#{finalBalanceReport.viewReport}" reRender="errorMsg"/>
                                <h:commandButton id="btnDownload"  value="PDF Download" action="#{finalBalanceReport.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                                <a4j:commandButton id="reset" value="Refresh" actionListener="#{finalBalanceReport.refreshPage}" reRender="errorMsg,txtfrmacno,stxtFolioFromShow,newAcno,frDt,toDt,txttoacno,stxtFolioToShow"/>
                                <a4j:commandButton  id="exit" value="Exit" action="case1"/>
                            </h:panelGroup>
                        </a4j:region>
                    </h:panelGrid>
                    <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="saveRegion"/>
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
