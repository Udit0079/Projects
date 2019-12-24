<%-- 
    Document   : WorkingStmtAdvances
    Created on : 29 Apr, 2013, 3:22:22 PM
    Author     : Nishant Srivastava
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
            <title>Working Statement for Advances</title>
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
            <a4j:form id="WorkingStmtAdvances">  
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{WorkingStmtAdvances.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Working Statement for Advances"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{WorkingStmtAdvances.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row1" style="height:30px;text-align:center;" width="100%">
                        <h:outputLabel id="lblMsg" styleClass="error" style="color:red;" value="#{WorkingStmtAdvances.message}"/>
                    </h:panelGrid>
                    <%--h:panelGrid id="gridPanel2" columns="4" columnClasses="col13,col13,col13,col13"  width="100%" styleClass="row1"--%>
                    <h:panelGrid id="gridPanel2" columns="6"  columnClasses="col3,col3,col3,col3,col3,col3"  width="100%" styleClass="row2">
                        <h:outputText value="Branch Wise" styleClass="label"/>
                        <h:selectOneListbox id="branch" size="1" value="#{WorkingStmtAdvances.branchCode}" styleClass="ddlist">
                            <f:selectItems value="#{WorkingStmtAdvances.branchCodeList}" />
                        </h:selectOneListbox>  
                        <h:outputLabel id="status" styleClass="label" value="Statement Type"/>
                        <h:selectOneListbox id="ddStatus" size="1" styleClass="ddlist" value="#{WorkingStmtAdvances.stmtType}" style="width:80%">
                            <f:selectItems value="#{WorkingStmtAdvances.stmtTypeList}"/>
                            <a4j:support event="onblur" actionListener="#{WorkingStmtAdvances.statusAction}" reRender="gridPanel3" oncomplete="setMask();" focus="frDate"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblDt" styleClass="label" value="As on Date"/>
                        <h:inputText id="frDate" size="10" styleClass="input issueDt" value="#{WorkingStmtAdvances.frdt}">
                            <f:convertDateTime pattern="dd/MM/yyyy"/>
                            <a4j:support event="onblur" actionListener="#{WorkingStmtAdvances.statusAction}" reRender="lblMsg,btnHtml,selectAcTp" oncomplete="if(#{WorkingStmtAdvances.status == 'none'}){#{rich:element('btnHtml')}.focus();}
                                         else if(#{WorkingStmtAdvances.status == ''}){#{rich:element('selectAcTp')}.focus();}setMask();"/>
                        </h:inputText>                        
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel3" columns="6" columnClasses="col3,col3,col3,col3,col3,col3"  width="100%" styleClass="row1" style="display:#{WorkingStmtAdvances.status};">
                        <h:outputText id="stxtAcTp" value="Account Type" styleClass="label"/>
                        <h:selectOneListbox id="selectAcTp" value="#{WorkingStmtAdvances.selectAcType}" styleClass="ddlist"  style="width:70px" size="1">
                            <f:selectItems id="selectAcTypeList" value="#{WorkingStmtAdvances.selectAcTpList}" />
                        </h:selectOneListbox>                         
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton action="#{WorkingStmtAdvances.btnHtmlAction}" id="btnHtml" value="Print Report" reRender="lblMsg"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{WorkingStmtAdvances.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{WorkingStmtAdvances.refresh}" reRender="mainPanel" oncomplete="setMask()"/>
                            <a4j:commandButton action="#{WorkingStmtAdvances.exitAction}" id="btnExit" value="Exit" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>            
        </body>
    </html>
</f:view>