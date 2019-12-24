<%-- 
    Document   : shareAreaWise
    Created on : Feb 21, 2014, 10:51:33 AM
    Author     : Athar Reza
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
            <title>Area Wise Share Report</title>
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
            <h:form id="shareHolders">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{ShareAreaWise.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Area Wise Share Report"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ShareAreaWise.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{ShareAreaWise.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="gridPanel4"  styleClass="row2">
                        <h:outputText id="lblBrnType" value="Branch Wise:" styleClass="label"/>
                        <h:selectOneListbox id="ddBrnTyp" value="#{ShareAreaWise.branch}" styleClass="ddlist" style="width:100px" size="1">
                            <f:selectItems id="selectBrnRepTypeList" value="#{ShareAreaWise.brnList}"/>
                            <a4j:support event="onblur" action="#{ShareAreaWise.onBlurArea}" reRender="ddacType111" focus="ddacType111"/>
                        </h:selectOneListbox>
                        <h:outputText id="lblacType" value="Area Wise:" styleClass="label"/>
                        <h:selectOneListbox id="ddacType111" value="#{ShareAreaWise.area}" styleClass="ddlist"  style="width:100px" size="1">
                            <f:selectItems id="selectRepTypeList11" value="#{ShareAreaWise.areaList}" />
                        </h:selectOneListbox>                        
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="gridPanel5"  styleClass="row1">
                        <h:outputText id="lblDate" value="As on Date:" styleClass="label"/>
                        <h:inputText id="frDt" styleClass="input frDt" size="12" value="#{ShareAreaWise.date}"/>
                        <h:outputText/>
                        <h:outputText/>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                        <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton  id="print" value="Print Report" action="#{ShareAreaWise.viewReport}" reRender="errorMsg" oncomplete="setMask()"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{ShareAreaWise.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                             <h:commandButton id="btnPrint" value="Excel Download" actionListener="#{ShareAreaWise.excelPrint}"/>
                            <a4j:commandButton id="reset" value="Refresh" actionListener="#{ShareAreaWise.refreshPage}" reRender="a1,errorMsg" oncomplete="setMask()"/>
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
