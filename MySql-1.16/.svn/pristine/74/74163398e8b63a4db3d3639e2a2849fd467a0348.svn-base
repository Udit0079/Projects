<%-- 
    Document   : dueDateDiary
    Created on : Jul 23, 2012, 11:32:53 AM
    Author     : root
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
            <title>Investment FDR Reports</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{dueDateDiary.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="FDR Reports"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{dueDateDiary.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row2" style="height:30px;text-align:center;" width="100%">
                        <h:outputLabel id="message" styleClass="error" style="color:red;" value="#{dueDateDiary.message}"/>
                    </h:panelGrid>
                    <h:panelGrid  id="selectGrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputLabel id="lblSelect" styleClass="label" value="Select Status"/>
                        <h:selectOneListbox id="ddSelect" size="1" styleClass="ddlist" value="#{dueDateDiary.selectStatus}">
                            <f:selectItems value="#{dueDateDiary.selectStatusList}"/>
                            <a4j:support event="onblur" action="#{dueDateDiary.onBlurStatus}" oncomplete="setMask();" reRender="message,lblFrDt,txtFrDt,lblToDt,txtToDt"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblFrDt" styleClass="label" value="#{dueDateDiary.frDtVal}" style="display:#{dueDateDiary.lblFrDate}"/>
                        <h:inputText id="txtFrDt" size="10" styleClass="input issueDt" value="#{dueDateDiary.frDt}" style="display:#{dueDateDiary.txtFrDate}">
                            <a4j:support event="onblur" action="#{dueDateDiary.onBlurFrDt}" reRender="message" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel id="lblToDt" styleClass="label" value="To Date" style="display:#{dueDateDiary.lblToDate}"/>
                        <h:inputText id="txtToDt" size="10" styleClass="input issueDt" value="#{dueDateDiary.toDt}" style="display:#{dueDateDiary.txtToDate}">
                            <a4j:support event="onblur" action="#{dueDateDiary.onBlurToDt}" reRender="message" oncomplete="setMask();"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton action="#{dueDateDiary.printAction}" id="btnPrint" value="Print Report" reRender="mainPanel"/>
                            <a4j:commandButton action="#{dueDateDiary.resetAction}" id="btnReset" value="Refresh" reRender="mainPanel"/>
                            <a4j:commandButton action="#{dueDateDiary.exitAction}" id="btnExit" value="Exit" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
