<%-- 
    Document   : todayCollRep
    Created on : Jan 1, 2013, 11:19:33 AM
    Author     : sipl
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Today's Collection Report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />

            <script type="text/javascript"> 
                jQuery(function(jQuery){
                    setMask();
                });
                var row;
                function setMask(){
                    jQuery(".tillDateText").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPaneHeader" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="dtPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date:"/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{todayCollRep.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblCollRep" styleClass="headerLabel" value="Today's Collection Report"/>
                        <h:panelGroup id="usrPanel" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User:"/>
                            <h:outputText styleClass="output" id="stxtUser" value="#{todayCollRep.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row1" style="height:30px;" width="100%">
                        <h:outputLabel id="lblMsg" styleClass="error" style="color:red;" value="#{todayCollRep.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row3" style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel id="lblAcType" styleClass="label" value = "Account Type"/>
                        <h:selectOneListbox id="ddAcType" style="width:80px;" styleClass="ddlist" value="#{todayCollRep.allAcType}">
                            <f:selectItems value="#{todayCollRep.allAcTpList}"/>
                        </h:selectOneListbox>                       
                        <h:outputLabel id="lblAgCode" styleClass="label" value="Agent Code"/>
                        <h:selectOneListbox id="ddAgCode" style="width:150px;" styleClass="ddlist" value="#{todayCollRep.allAgCode}" size="1">
                            <f:selectItems value="#{todayCollRep.allAgCodeList}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row4" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputLabel id="lblTillDate" value = "Date" styleClass="label"/>
                        <h:inputText id="tillDateText"  styleClass="input tillDateText" value="#{todayCollRep.tillDt}" size="10">
                            <f:convertDateTime pattern="dd/MM/yyyy"/>
                        </h:inputText>
                        <h:outputLabel id="lblSort" styleClass="label" value="Sorting"/>
                        <h:selectOneListbox id="ddSort" style="width:100px;" styleClass="ddlist" value="#{todayCollRep.allSort}" size="1">
                            <f:selectItems value="#{todayCollRep.allSortList}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>                   
                    <h:panelGrid columns="1" styleClass="footer" style="width:100%;text-align:center;">  
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton action="#{todayCollRep.btnHtmlAction}" id="btnHtml" value="Print Report" reRender="lblMsg"/>
                                <h:commandButton id="btnDownload"  value="PDF Download" action="#{todayCollRep.btnPdfAction}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton action="#{todayCollRep.btnExitAction}" id="btnExit" value="Exit" reRender="lblMsg,ddSort,tillDateText,ddAgCode,ddAcType"/>
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