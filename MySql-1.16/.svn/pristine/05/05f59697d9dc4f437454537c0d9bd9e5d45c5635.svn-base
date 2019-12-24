<%-- 
    Document   : OfficeDeptMaster
    Created on : Jan 19, 2016, 12:47:15 PM
    Author     : Administrator
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
            <title>Office Department Master</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
            <script type="text/javascript">
                var row;                
            </script>
        </head>
        <body>
            <a4j:form id="formOffDept">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{OfficeDeptMaster.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Office Department Master"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{OfficeDeptMaster.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                        <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;" styleClass="row1">
                            <h:outputText id="message" styleClass="msg" value="#{OfficeDeptMaster.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="Row1" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblOffice" styleClass="label" value="Office Id" style="padding-left:70px;"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddOffice" styleClass="ddlist" size="1" style="width:80px;" value="#{OfficeDeptMaster.offId}" disabled="#{OfficeDeptMaster.offDisable}">
                                <f:selectItems value="#{OfficeDeptMaster.offIdList}"/>
                                <a4j:support action="#{OfficeDeptMaster.offIdFn}" event="onblur" reRender="message,ddDept,stxtoffId"/>
                            </h:selectOneListbox>
                            <h:outputText id="stxtoffId" styleClass="output" value="#{OfficeDeptMaster.offName}"/>
                            <h:outputLabel/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="Row2" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblDept" styleClass="label" value="Department Id" style="padding-left:70px;"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddDept" styleClass="ddlist" size="1" style="width:80px;" value="#{OfficeDeptMaster.deptId}" disabled="#{OfficeDeptMaster.deptDisable}">
                                <f:selectItems value="#{OfficeDeptMaster.deptIdList}"/>
                                <a4j:support action="#{OfficeDeptMaster.deptIdFn}" event="onblur" reRender="message,stxtDeptId"/>
                            </h:selectOneListbox>
                            <h:outputText id="stxtDeptId" styleClass="output" value="#{OfficeDeptMaster.deptName}"/>
                            <h:outputLabel/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="Row3" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblDeptHead" styleClass="label" value="Department Head" style="padding-left:70px;"></h:outputLabel>
                            <h:inputText id="txtDeptHead" styleClass="input" value="#{OfficeDeptMaster.deptHead}"/>
                            <h:outputLabel id="lblAddr" styleClass="label" value="Address" style="padding-left:70px;"></h:outputLabel>
                            <h:inputText id="txtAddr" styleClass="input" value="#{OfficeDeptMaster.addr}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="vtop" columns="1" id="table" style="height:auto;" styleClass="row2" width="100%">
                            <a4j:region>
                                <rich:dataTable value="#{OfficeDeptMaster.gridDetail}" var="dataItem"
                                        rowClasses="row1,row2" id="taskList" rows="10" columnsWidth="100" rowKeyVar="row"
                                        onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                        onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="10"><h:outputText value="Master Details"/></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="Office Id"/></rich:column>
                                            <rich:column><h:outputText value="Office Name"/></rich:column>
                                            <rich:column><h:outputText value="Department Id"/></rich:column>
                                            <rich:column><h:outputText value="Department Name"/></rich:column>
                                            <rich:column><h:outputText value="Department Head"/></rich:column>
                                            <rich:column><h:outputText value="Address"/></rich:column>
                                            <rich:column><h:outputText value="SELECT"/></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{dataItem.officeId}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.officeName}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.departmentId}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.departmentName}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.departmentHead}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.address}"/></rich:column>
                                    <rich:column style="text-align:center;width:40px">
                                        <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{OfficeDeptMaster.select}" reRender="mainPanel">
                                            <h:graphicImage value="/resources/images/edit.gif" style="border:0"/>
                                            <f:setPropertyActionListener value="#{dataItem}" target="#{OfficeDeptMaster.currentItem}"/>
                                        </a4j:commandLink>
                                    </rich:column>
                                </rich:dataTable>
                                <rich:datascroller align="left" for="taskList" maxPages="20"/>
                            </a4j:region>
                        </h:panelGrid>
                        <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup layout="block" style="width:100%;text-align:center;">
                                <a4j:commandButton id="btnSave" value="#{OfficeDeptMaster.btnValue}" action="#{OfficeDeptMaster.saveMasterDetail}" disabled="#{OfficeDeptMaster.btnFlag}" reRender="mainPanel"/>
                                <a4j:commandButton id="btnRefresh" value="Refresh" action="#{OfficeDeptMaster.refreshForm}" reRender="mainPanel"/>
                                <a4j:commandButton id="btnExit" value="Exit" action="#{OfficeDeptMaster.exitBtnAction}" reRender="mainPanel"/>
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
