<%-- 
    Document   : shareDividendDetail
    Created on : Oct 5, 2015, 10:46:35 AM
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
            <title>Share Dividend  Detail</title>
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
            <h:form id="dividendStmt">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">

                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{ShareDividendDetail.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Share Dividend  Detail"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ShareDividendDetail.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{ShareDividendDetail.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="gridPanel9"  styleClass="row2">
                        <h:outputText id="lblacType" value="Area" styleClass="label"/>
                        <h:selectOneListbox id="ddacType11" value="#{ShareDividendDetail.area}" styleClass="ddlist"  style="width:100px" size="1">
                            <f:selectItems id="selectRepTypeList111" value="#{ShareDividendDetail.areaList}" />
                        </h:selectOneListbox> 
                        <h:outputText/>
                        <h:outputText/>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="gridPanel4"  styleClass="row2">
                        <h:outputText id="lblBrnType" value="Financial Year:" styleClass="label"/>
                        <h:selectOneListbox id="ddBrnTyp" value="#{ShareDividendDetail.finYear}" styleClass="ddlist" style="width:100px" size="1">
                            <f:selectItems id="selectBrnRepTypeList" value="#{ShareDividendDetail.finYearList}"/>

                        </h:selectOneListbox>
                        <h:outputText id="lblacTypeList" value="Report Type:" styleClass="label"/>
                        <h:selectOneListbox id="ddacType111" value="#{ShareDividendDetail.reportType}" styleClass="ddlist"  style="width:100px" size="1">
                            <f:selectItems id="selectRepTypeList11" value="#{ShareDividendDetail.reportTypeList}" />
                        </h:selectOneListbox>                        
                    </h:panelGrid>

                    <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                        <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton  id="print" value="Print Report" action="#{ShareDividendDetail.viewReport}" reRender="errorMsg"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{ShareDividendDetail.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="reset" value="Refresh" actionListener="#{ShareDividendDetail.refreshPage}" reRender="errorMsg,txtfrmacno,ddReportType,panel1"/>
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
