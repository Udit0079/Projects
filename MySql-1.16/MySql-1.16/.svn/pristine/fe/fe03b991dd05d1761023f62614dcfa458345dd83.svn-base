<%-- 
    Document   : form15g15hDetail
    Created on : Jun 28, 2016, 5:19:34 PM
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
            <title>Form 15G / 15H</title>
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
            <h:form id="loanAcStateme">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{Form15G15HDetail.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Form 15G / 15H"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{Form15G15HDetail.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row2" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{Form15G15HDetail.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel4"  styleClass="row1"> 
                        <h:outputText value="Branch Wise" styleClass="label"/>
                        <h:selectOneListbox id="branch" size="1" value="#{Form15G15HDetail.branch}" styleClass="ddlist">
                            <f:selectItems value="#{Form15G15HDetail.branchList}" />
                        </h:selectOneListbox>
                        <h:outputText value="From Date" styleClass="label"/>
                        <h:inputText id="txtFrmDate"   styleClass="input frDt" style="width:70px;"  value="#{Form15G15HDetail.frDt}"/>
                        <h:outputText value="To Date" styleClass="label"/>
                        <h:inputText id="txtToDate"   styleClass="input toDt" style="width:70px;"  value="#{Form15G15HDetail.toDt}"/>
                        <%--h:outputText value="Financial Year" styleClass="label"/>
                        <h:panelGroup id="finanId">
                            <h:inputText id="txtFinan" value="#{Form15G15HDetail.finYear}" styleClass="input" size="8" maxlength="4" onkeyup="this.value=this.value.toUpperCase();">
                                <a4j:support event="onblur" actionListener="#{Form15G15HDetail.getNewfYear}" reRender="newFinan,errorMsg" oncomplete="setMask();" />
                            </h:inputText>
                            <h:outputText id="newFinan" value="#{Form15G15HDetail.newFinYear}" styleClass="output" style="color:green"/>
                        </h:panelGroup--%>
                        <%--h:outputText value="Report Type" styleClass="label"/>
                        <h:selectOneListbox id="repId" size="1" value="#{Form15G15HDetail.repType}" styleClass="ddlist">
                            <f:selectItems value="#{Form15G15HDetail.repTypeList}" />
                        </h:selectOneListbox--%> 
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel5"  styleClass="row2">
                        <h:outputText value="Report Type" styleClass="label"/>
                        <h:selectOneListbox id="repId" size="1" value="#{Form15G15HDetail.repType}" styleClass="ddlist">
                            <f:selectItems value="#{Form15G15HDetail.repTypeList}" />
                        </h:selectOneListbox> 
                        <h:outputText value="Financial Year" styleClass="label"/>
                        <h:panelGroup id="finanId">
                            <h:inputText id="txtFinan" value="#{Form15G15HDetail.finYear}" styleClass="input" size="8" maxlength="4">
                                <a4j:support event="onblur" actionListener="#{Form15G15HDetail.getNewfYear}" reRender="newFinan,errmsg" oncomplete="setMask();"/>
                            </h:inputText>
                            <h:outputText id="newFinan" value="#{Form15G15HDetail.newFinYear}" styleClass="output" style="color:green"/>
                        </h:panelGroup>
                        <h:outputText/> 
                        <h:outputText/> 
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                        <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                            <h:commandButton id="btnPrint" value="Excel Download" actionListener="#{Form15G15HDetail.excelPrint}"/>
                            <h:commandButton id="csvPrint" value="CSV Download" actionListener="#{Form15G15HDetail.csvPrint}"/>
                            <a4j:commandButton id="reset" value="Refresh" actionListener="#{Form15G15HDetail.refreshPage}" oncomplete="setMask()" reRender="a1,errorMsg"/>
                            <a4j:commandButton  id="exit" value="Exit" action="#{Form15G15HDetail.exitPage}"reRender="a1,errorMsg"/>
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
