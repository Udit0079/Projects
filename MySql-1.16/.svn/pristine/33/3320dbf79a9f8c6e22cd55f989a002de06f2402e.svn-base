<%-- 
    Document   : AadharLpgStatus
    Created on : Mar 20, 2015, 12:03:50 PM
    Author     : root
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
            <title>Aadhar Lpg Status</title>
        </head>
        <body>
            <a4j:form id="formAadharlpgStatus">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{AadharLpgStatus.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Aadhar LPG Status"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{AadharLpgStatus.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;" styleClass="row1">
                        <h:outputText id="message" styleClass="msg" value="#{AadharLpgStatus.msg}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col4,col2,col4,col2,col4" columns="6" id="Row1" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputLabel id="lblStFlg" styleClass="label" value="Status"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddStFlg" styleClass="ddlist" size="1" style="width:80px;" value="#{AadharLpgStatus.status}">
                                <f:selectItems value="#{AadharLpgStatus.statusList}"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblLpgFlg" styleClass="label" value="Id Type"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddLpgFlg" styleClass="ddlist" size="1" style="width:80px;" value="#{AadharLpgStatus.type}">
                                <f:selectItems value="#{AadharLpgStatus.typeList}"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblaadharid" styleClass="label" value="LPG Id/Aadhar No"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtaadharNo" styleClass="input" maxlength="17" value="#{AadharLpgStatus.aadharNo}"/>
                        </h:panelGrid>
                        <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup layout="block" style="width:100%;text-align:center;">
                                <a4j:commandButton id="btnShow" value="Show" action="#{AadharLpgStatus.showStatus}" reRender="message,table,taskList"/>
                                <a4j:commandButton id="btnRefresh" value="Refresh" action="#{AadharLpgStatus.refreshForm}" reRender="mainPanel"/>
                                <a4j:commandButton id="btnExit" value="Exit" action="#{AadharLpgStatus.exitBtnAction}" reRender="mainPanel"/>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="vtop" columns="1" id="table" style="height:auto;" styleClass="row2" width="100%">
                            <a4j:region>
                                <rich:dataTable value="#{AadharLpgStatus.gridDetail}" var="dataItem" rowClasses="row1,row2" id="taskList" rows="10" columnsWidth="100" rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="13"><h:outputText value="Aadhar/Lpg Status Details"/></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="Cust Id"/></rich:column>
                                            <rich:column><h:outputText value="Customer Name" /></rich:column>
                                            <rich:column><h:outputText value="Mapped A/c No." /></rich:column>
                                            <rich:column><h:outputText value="Branch" /></rich:column>
                                            <rich:column><h:outputText value="Mandate Flag" /></rich:column>
                                            <rich:column><h:outputText value="Man. Date" /></rich:column>
                                            <rich:column><h:outputText value="Reg. Type" /></rich:column>
                                            <rich:column><h:outputText value="Status" /></rich:column>
                                            <rich:column><h:outputText value="Reg. By" /></rich:column>
                                            <rich:column><h:outputText value="Reg. Date" /></rich:column>
                                            <rich:column><h:outputText value="Update By" /></rich:column>
                                            <rich:column><h:outputText value="Update Date" /></rich:column>
                                            <rich:column><h:outputText value="Reason" /></rich:column>  
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{dataItem.custId}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.name}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.acNo}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.branch}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.manDateFlg}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.manDate}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.regType}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.status}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.regBy}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.regDate}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.updateBy}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.updateDt}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.reason}" /></rich:column>
                                </rich:dataTable>
                                <rich:datascroller align="left" for="taskList" maxPages="20"/>
                            </a4j:region>
                        </h:panelGrid>
                    </h:panelGrid>
                </a4j:form>
        </body>
    </html>
</f:view>

