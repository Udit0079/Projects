<%-- 
    Document   : IdMerging
    Created on : Nov 14, 2014, 10:54:01 AM
    Author     : mayank
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>ID Merging</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
        </head>
        <body>
            <a4j:form id="IdMerge">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{IdMerge.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="ID Merging"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{IdMerge.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row2" style="height:30px;text-align:center;" width="100%">
                        <h:outputLabel id="lblMsg" styleClass="error" style="color:red;" value="#{IdMerge.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel2" columns="4" columnClasses="col13,col13,col13,col13" width="100%" styleClass="row1">
                        <h:outputLabel/>
                        <h:outputLabel id="orgId" value="Original Id" styleClass="label"/>
                        <h:inputText id="orgNo" value="#{IdMerge.orgCode}" styleClass="input" style="width:80px">
                            <a4j:support event="onblur" action="#{IdMerge.getOrgDetails}" reRender="mainPanel,taskList,lblMsg"/>
                        </h:inputText>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="table" style="height:auto;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{IdMerge.gridDetail}" var="dataItem"
                                rowClasses="row1,row2" id="taskList" rows="10" columnsWidth="100" rowKeyVar="row"
                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="15"><h:outputText value="ORIGINAL ID DETAILS"/></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="S.No."/></rich:column>
                                        <rich:column><h:outputText value="Account No"/></rich:column>
                                        <rich:column><h:outputText value="Name"/></rich:column>
                                        <rich:column><h:outputText value="Father Name"/></rich:column>
                                        <rich:column><h:outputText value="Date Of Birth"/></rich:column>
                                        <rich:column><h:outputText value="Pan No."/></rich:column>
                                        <rich:column><h:outputText value="Corr. Address"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.sNo}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.acno}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.custname}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.fname}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.dob}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.panno}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.craddress}"/></rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="20"/>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel4" columns="4" columnClasses="col13,col13,col13,col13" width="100%" styleClass="row1">
                        <h:outputLabel/>
                        <h:outputLabel id="mergId" value="To Be Merged Id" styleClass="label"/>
                        <h:inputText id="mergNo" value="#{IdMerge.mergCode}" styleClass="input" style="width:80px">
                            <a4j:support event="onblur" action="#{IdMerge.getMergDetails}" reRender="mainPanel,taskList1,lblMsg"/>
                        </h:inputText>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="table1" style="height:auto;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{IdMerge.gridDetail1}" var="dataItem1"
                                rowClasses="row1,row2" id="taskList1" rows="10" columnsWidth="100" rowKeyVar="row"
                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="15"><h:outputText value="MERGED ID DETAILS"/></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="S.No."/></rich:column>
                                        <rich:column><h:outputText value="Account No"/></rich:column>
                                        <rich:column><h:outputText value="Name"/></rich:column>
                                        <rich:column><h:outputText value="Father Name"/></rich:column>
                                        <rich:column><h:outputText value="Date Of Birth"/></rich:column>
                                        <rich:column><h:outputText value="Pan No."/></rich:column>
                                        <rich:column><h:outputText value="Corr. Address"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet> 
                                <rich:column><h:outputText value="#{dataItem1.sNo}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.acno}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.custname}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.fname}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.dob}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.panno}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.craddress}"/></rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList1" maxPages="20"/>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton value="Merge" id="btnSave" action="#{IdMerge.saveDetails}" reRender="mainPanel,taskList,lblMsg,taskList1"/>
                            <a4j:commandButton value="Refresh" id="refresh" action="#{IdMerge.refreshForm}" reRender="mainPanel,taskList,lblMsg,taskList1"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{IdMerge.exitBtnAction}" reRender="mainPanel,taskList,lblMsg,taskList1"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>                
            </a4j:form>
        </body>
    </html>
</f:view>