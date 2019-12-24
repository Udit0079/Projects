<%-- 
    Document   : tdDueReport
    Created on : Dec 19, 2013, 3:44:20 PM
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
            <link rel="stylesheet" href="/cbs-jsp/resources/txn_style.css" type="text/css"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>TD DUE REPORT</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{tdDueReport.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="TD DUE REPORT"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{tdDueReport.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row2" style="height:30px;text-align:center;" width="100%">
                        <h:outputLabel id="lblMsg" styleClass="error" style="color:red;" value="#{tdDueReport.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel1" columns="4" columnClasses="col13,col13,col13,col13" width="100%" styleClass="row1">
                        <h:outputLabel id="bnkType" styleClass="label" value="Bank Name"/>
                        <h:selectOneListbox id="ddbnkType" size="1" styleClass="ddlist" value="#{tdDueReport.bankType}">
                            <f:selectItems value="#{tdDueReport.bankTypeList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblTillDt" styleClass="label" value="Till Date"/>
                        <h:inputText id="txtDt" size="10" styleClass="input issueDt" style="setMask();" value="#{tdDueReport.tillDt}"/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton action="#{tdDueReport.processAction}" id="btnProcess" value="Print Report" reRender="mainPanel"/>
                            <a4j:commandButton action="#{tdDueReport.resetAction}" id="btnReset" value="Refresh" reRender="mainPanel" oncomplete="#{rich:element('txtDt')}.style=setMask()"/>
                            <a4j:commandButton action="#{tdDueReport.exitAction}" id="btnExit" value="Exit" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>