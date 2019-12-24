<%-- 
    Document   : DayEndStatus
    Created on : Jun 3, 2011, 10:56:11 AM
    Author     : sipl
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <title>Branch Status</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".calInstDate").mask("39/19/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="brStatus">
                <h:panelGrid columns="1" id="gridPanel1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="grpPanel1" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{BranchStatus.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Branch Status"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{BranchStatus.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid> 
                    <h:panelGrid  id="grdpane6" style="height:30px;text-align:center" styleClass="row1" width="100%" >
                        <h:outputText id="stxtError"  styleClass="error" value="#{BranchStatus.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col3" id="gridpanel12"   style="height:30px;" styleClass="row2" width="100%" >    
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel id="lblToDate" styleClass="headerLabel"  value="Till Date"  />
                        <h:panelGroup styleClass="label">
                            <h:inputText id="txtDate"   styleClass="input calInstDate" style="setMask();"  value="#{BranchStatus.date}" size="10"/>
                            <font color="purple">DD/MM/YYYY</font>
                        </h:panelGroup>          
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid> 
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton  id="btnView" value="View Report"   action="#{BranchStatus.viewReport}" reRender="gridPanel1,txtDate,stxtError" oncomplete="#{rich:element('txtDate')}.style=setMask()"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{BranchStatus.refreshForm}" reRender="gridPanel3,txtDate,stxtError" oncomplete="#{rich:element('txtDate')}.style=setMask()"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{BranchStatus.ExitForm}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel3" style="height:auto;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{BranchStatus.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="12" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="12"><h:outputText value="Branch Day End Status" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Serial No." /></rich:column>
                                        <rich:column><h:outputText value="Branch Name" /></rich:column>
                                        <rich:column><h:outputText value="Day Begin" /></rich:column>
                                        <rich:column><h:outputText value="SOD User" /></rich:column>
                                        <rich:column><h:outputText value="SOD UserName" /></rich:column>
                                        <rich:column><h:outputText value="SOD Time" /></rich:column>
                                        <rich:column><h:outputText value="Day End" /></rich:column>
                                        <rich:column><h:outputText value="EOD User" /></rich:column>
                                        <rich:column><h:outputText value="EOD UserName" /></rich:column>
                                        <rich:column><h:outputText value="EOD Time" /></rich:column>
                                        <rich:column><h:outputText value="Month End" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column width="50px"><h:outputText value="#{dataItem.serialNo}" /></rich:column>
                                <rich:column width="150px"><h:outputText value="#{dataItem.branchName}" /></rich:column>
                                <rich:column width="75px"><h:outputText value="#{dataItem.dayBeginFlag}" /></rich:column>
                                <rich:column width="150px"><h:outputText value="#{dataItem.beginUser}" /></rich:column>
                                <rich:column width="150px"><h:outputText value="#{dataItem.sodUserName}" /></rich:column>
                                <rich:column width="150px"><h:outputText value="#{dataItem.sodTime}" /></rich:column>
                                <rich:column width="75px"><h:outputText value="#{dataItem.dayEndFlag}" /></rich:column>
                                <rich:column width="150px"><h:outputText value="#{dataItem.endUser}" /></rich:column>
                                <rich:column width="150px"><h:outputText value="#{dataItem.eodUserName}" /></rich:column>
                                <rich:column width="150px"><h:outputText value="#{dataItem.eodTime}" /></rich:column>
                                <rich:column width="75px"><h:outputText value="#{dataItem.monthEndFlag}" /></rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="2" />
                        </a4j:region>                        
                    </h:panelGrid>                    
                    <%--h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{BranchStatus.refreshForm}" reRender="gridPanel3"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{BranchStatus.ExitForm}"/>
                        </h:panelGroup>
                    </h:panelGrid--%>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>