<%-- 
    Document   : WorkingStmtBorrowing
    Created on : Feb 23, 2013, 12:30:40 PM
    Author     : sipl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE html>

<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Working Statement Borrowing</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".issueDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{WorkingStmtBorrowing.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Working Statement Borrowing"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{WorkingStmtBorrowing.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row1" style="height:30px;text-align:center;" width="100%">
                        <h:outputLabel id="lblMsg" styleClass="error" style="color:red;" value="#{WorkingStmtBorrowing.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel2" columns="6"  columnClasses="col3,col3,col3,col3,col3,col3"  width="100%" styleClass="row2">           
                        <h:outputText value="Branch Wise" styleClass="label"/>
                        <h:selectOneListbox id="branch" size="1" value="#{WorkingStmtBorrowing.branchCode}" styleClass="ddlist">
                            <f:selectItems value="#{WorkingStmtBorrowing.branchCodeList}" />
                        </h:selectOneListbox>  
                        <h:outputLabel id="status" styleClass="label" value="Account"/>
                        <h:selectOneListbox id="ddStatus" size="1" styleClass="ddlist" value="#{WorkingStmtBorrowing.acType}" style="width:50px">
                            <f:selectItems value="#{WorkingStmtBorrowing.acTypeList}"/>
                            <a4j:support event="onblur" oncomplete="setMask();" focus="frDate"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblDt" styleClass="label" value="Date"/>
                        <h:inputText id="frDate" size="10" styleClass="input issueDt" value="#{WorkingStmtBorrowing.frdt}">
                            <f:convertDateTime pattern="dd/MM/yyyy"/>
                            <a4j:support event="onblur" oncomplete="setMask();" focus="btnHtml"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton action="#{WorkingStmtBorrowing.btnHtmlAction}" id="btnHtml" value="Print Report" reRender="lblMsg"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{WorkingStmtBorrowing.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{WorkingStmtBorrowing.refresh}" reRender="mainPanel" oncomplete="setMask()"/>
                            <a4j:commandButton action="#{WorkingStmtBorrowing.exitAction}" id="btnExit" value="Exit" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>