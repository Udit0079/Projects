<%-- 
    Document   : LockerRentMaster
    Created on : Sep 17, 2010, 10:30:39 AM
    Author     : ROHIT KRISHNA GUPTA
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
            <title>Locker Rent Master</title>
            <script type="text/javascript">

            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{LockerRentMaster.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Locker Rent Master"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{LockerRentMaster.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="errorMessage" styleClass="error" value="#{LockerRentMaster.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{LockerRentMaster.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="a8" width="100%" style="height:30px;" styleClass="row1">
                        <h:outputLabel id="lblLockerType" styleClass="label" value="Locker Type :"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddLockerType" tabindex="1" styleClass="ddlist" value="#{LockerRentMaster.lockerType}" size="1" style="width: 102px">
                            <f:selectItems value="#{LockerRentMaster.lockerTypeList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblCustCat" styleClass="label" value="Customer Category :"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddCustCat" tabindex="2" styleClass="ddlist" value="#{LockerRentMaster.custCat}" size="1" style="width: 102px">
                            <f:selectItems value="#{LockerRentMaster.custCatList}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="a11" width="100%" style="height:30px;" styleClass="row2">
                        <h:outputLabel id="lblRentAssociated" styleClass="label" value="Rent Associated :"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtRentAssociated" maxlength="8" size="16" tabindex="3" value="#{LockerRentMaster.rent}" styleClass="input"/>
                        <h:outputLabel id="lblEffDt" styleClass="label" value="Effective Date :"><font class="required" color="red">*</font></h:outputLabel>
                        <rich:calendar datePattern="dd/MM/yyyy" id="calEffDt" value="#{LockerRentMaster.effDate}" tabindex="4" inputSize="10"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="a19" style="height:auto;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{LockerRentMaster.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="10" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="12"><h:outputText value="Details" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Effective Date" /></rich:column>
                                        <rich:column><h:outputText value="Locker Type" /></rich:column>
                                        <rich:column><h:outputText value="Customer Category" /></rich:column>
                                        <rich:column><h:outputText value="Rent" /></rich:column>
                                        <rich:column><h:outputText value="Enter By" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.effDate}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.lockerType}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.custCat}" /></rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem.rent}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column><h:outputText value="#{dataItem.enterBy}" /></rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="20" />
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnAdd" tabindex="5" value="Add" action="#{LockerRentMaster.addDetail}"  reRender="message,errorMessage,lpg,a19,taskList" focus="ddLockerType"/>
                            <a4j:commandButton id="btnHistory" tabindex="6" value="History" action="#{LockerRentMaster.gridLoad}"  reRender="message,errorMessage,lpg,a19,taskList" focus="ddLockerType"/>
                            <a4j:commandButton id="btnRefresh" tabindex="7" value="Refresh" action="#{LockerRentMaster.resetForm}"  reRender="message,errorMessage,lpg,a8,a11,a12,a13,a19,taskList" focus="ddLockerType"/>
                            <a4j:commandButton id="btnExit" tabindex="8" value="Exit" action="#{LockerRentMaster.exitFrm}" reRender="message,errorMessage" />
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
