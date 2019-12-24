<%-- 
    Document   : tdPeriodReport
    Created on : Jan 21, 2014, 4:03:21 PM
    Author     : Athar Reza
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>TD Period Report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".calInstDate").mask("99/99/9999");
                }

            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="mainPanelGrid" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{TdPeriodReport.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="TD Period Report"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{TdPeriodReport.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="grdpane6" style="height:30px;text-align:center" styleClass="row2" width="100%" >
                        <h:outputText id="stxtError"  styleClass="error" value="#{TdPeriodReport.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col3" id="gridpanel1"   style="height:30px;" styleClass="row1" width="100%" >
                        <h:outputLabel id="lblbranchCodeList" styleClass="headerLabel"  value="Type"  />
                        <h:selectOneListbox id="branchCodeListBox" value="#{TdPeriodReport.type}" styleClass="ddlist" size="1"  style="width:100px" >
                            <f:selectItems id="branchCodeList" value="#{TdPeriodReport.typeList}" />
                            <a4j:support action="#{TdPeriodReport.viewButoon}" event="onblur"reRender="txtFrDay,txtToDay,stxtError,btnView,branchCodeListBox,mainPanelGrid,asOnDt"focus="#{TdPeriodReport.focusId}" oncomplete="setMask();"/>
                        </h:selectOneListbox> 
                        <h:outputText value="AS On Date" styleClass="label"/>
                        <h:inputText id="asOnDt" styleClass="input calInstDate" style="width:70px;"  value="#{TdPeriodReport.asOnDt}" disabled="#{TdPeriodReport.disableDate}">
                            <a4j:support event="onblur" focus="btnView" oncomplete="setMask();"/>
                        </h:inputText>
                    </h:panelGrid>  
                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col3" id="gridpanel111"   style="height:30px;" styleClass="row1" width="100%" >
                        <h:outputLabel id="lblFromDays" value="From Days" styleClass="label"/>
                        <h:inputText id="txtFrDay" value="#{TdPeriodReport.fromDays}" styleClass="input" maxlength="15" size="11" disabled="#{TdPeriodReport.disableFrDaysToDays}">
                            <a4j:support event="onblur" focus="txtToDay" oncomplete="setMask();"/>
                        </h:inputText> 
                        <h:outputLabel id="lblToDays" value="To Days" styleClass="label"/>
                        <h:inputText id="txtToDay" value="#{TdPeriodReport.toDays}" styleClass="input" maxlength="15" size="11" disabled="#{TdPeriodReport.disableFrDaysToDays}">
                            <a4j:support event="onblur" focus="btnView" oncomplete="setMask();"/>
                        </h:inputText> 
                    </h:panelGrid> 
                    <a4j:region id="ajReason">
                        <h:panelGrid columns="2" id="gridpanel5" style="width:100%;text-align:center;" styleClass="footer" >
                            <h:panelGroup id="btnPanel">
                                <a4j:commandButton  id="btnView" value="#{TdPeriodReport.tyButton}" action="#{TdPeriodReport.btnAction}" reRender="stxtError,mainPanelGrid,txtFrDay,txtToDay,branchCodeListBox" oncomplete="setMask();"/>
                                <a4j:commandButton id="btnRefresh" value="Refresh"  action="#{TdPeriodReport.refreshForm}"  reRender="mainPanelGrid" oncomplete="setMask();"/>
                                <a4j:commandButton id="btnExit" value="Exit"  action="#{TdPeriodReport.exitAction}" reRender="mainPanelGrid" />
                            </h:panelGroup>
                        </h:panelGrid> 
                    </a4j:region>   
                    <h:panelGrid columnClasses="vtop" columns="1" id="Stocktable" width="100%" styleClass="row2" style="height:168px;">
                        <a4j:region>
                            <rich:dataTable value="#{TdPeriodReport.gridDetail}" var="item"
                                            rowClasses="row1, row2" id="taskList" rows="10" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="13"><h:outputText value="Details"/></rich:column>
                                        <rich:column breakBefore="true" ><h:outputText value="Sr.No."/></rich:column>
                                        <rich:column ><h:outputText value="From Days" /></rich:column>
                                        <rich:column ><h:outputText value="To Days" /></rich:column> 
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{item.srNo}" /></rich:column>
                                <rich:column><h:outputText value="#{item.fromDays}" /></rich:column>
                                <rich:column><h:outputText value="#{item.toDays}" /></rich:column>                      
                            </rich:dataTable>
                            <rich:datascroller id="scroller" align="left" for="taskList" maxPages="10" />
                        </a4j:region>
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
