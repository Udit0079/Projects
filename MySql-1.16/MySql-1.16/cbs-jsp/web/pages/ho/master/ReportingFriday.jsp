<%-- 
    Document   : ReportingFriday
    Created on : Sep 24, 2010, 4:46:35 PM
    Author     : Sudhir Kr Bisht
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
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>Reporting Friday</title>
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
                    <h:panelGrid id="gridPanel" columns="3" style="width:100%;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{ReportingFriday.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Reporting Friday"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ReportingFriday.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="errorMsgReporting"   width="100%" style="text-align:center" styleClass="row2">
                        <h:outputText id="errorGrid" value="#{ReportingFriday.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel1" columns="2" columnClasses="col8,col8" width="100%">
                        <h:panelGrid id="gridPanel2" columns="2" columnClasses="col8,col8" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblFromDate" styleClass="label" value="#{ReportingFriday.label1}"/>
                            <h:inputText id="txtFromDt" size="10" styleClass="input issueDt" value="#{ReportingFriday.fromDt}">
                                <a4j:support event="onblur" focus="txtToDt" oncomplete="setMask();"/>
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid id="gridPanel3" columns="2" columnClasses="col8,col8" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblToDate" styleClass="label" value="#{ReportingFriday.label2}"    style="display:#{ReportingFriday.hiddenLabelToDate};"/>
                            <h:inputText id="txtToDt" size="10" styleClass="input issueDt" value="#{ReportingFriday.toDt}">
                                <a4j:support event="onblur" oncomplete="setMask();"/>
                            </h:inputText>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel11" columns="2" columnClasses="col8,col8" width="100%" styleClass="row2"  >
                        <h:panelGrid id="gridPanel21" columns="2" columnClasses="col8,col8" style="height:30px;"  width="100%">
                            <h:outputLabel id="lblFromDate1" styleClass="label" value="Balance" style="display:#{ReportingFriday.hiddenInput};"/>
                            <h:inputText  id="lblFromDate12" styleClass="input" value="#{ReportingFriday.balance}" style="display:#{ReportingFriday.hiddenInput};"/>
                        </h:panelGrid>
                        <h:panelGrid id="gridPanel31" columns="2" columnClasses="col8,col8" style="height:30px;"  width="100%">
                            <h:outputLabel id="lblToDate1" styleClass="label" value=""/>
                            <h:outputLabel id="lblToDate12" styleClass="label" value=""/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel4" columns="1" columnClasses="vtop" styleClass="row2" style="height:200px;" width="100%">
                        <rich:dataTable    value="#{ReportingFriday.gridData}"  var="dataItem" rowClasses="gridrow1, gridrow2" rows="10" id="taskList" columnsWidth="100" onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column><h:outputText value="S.No"/></rich:column>
                                    <rich:column><h:outputText value="Reporting Friday"/></rich:column>
                                    <rich:column><h:outputText value="Net Liabilities(Rs)"/></rich:column>
                                    <rich:column><h:outputText value="EnterBy"/></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column>
                                <h:outputText value="#{dataItem.serial}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataItem.reportingFriday}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataItem.netLiabilities}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataItem.enterBy}"/>
                            </rich:column>
                        </rich:dataTable>
                        <rich:datascroller align="left" for="taskList" maxPages="3"/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel" styleClass="vtop">
                            <a4j:commandButton  id="buttonUpdate" value="Update" action="#{ReportingFriday.update}" reRender="errorGrid,taskList,lblFromDate12"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{ReportingFriday.refresh}" reRender="mainPanel"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{ReportingFriday.exitForm}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
