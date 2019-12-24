<%-- 
    Document   : LockerMaster
    Created on : 16 Aug, 2010, 12:32:29 PM
    Author     : root
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
            <title>Locker Master</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="txnForm">
                <h:panelGrid bgcolor="#fff" columns="1" id="outerPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{LockerMaster.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Locker Addition Form"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{LockerMaster.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                        <h:panelGrid columns="1" id="msgRow" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputLabel id="lblMsg" styleClass="error" value="#{LockerMaster.message}"/>
                            <h:outputText id="errorMessage" styleClass="msg" value="#{LockerMaster.finalMsg}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row1" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblLockerType" styleClass="label" value="Locker Type :"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddLockerType" styleClass="ddlist"  size="1" value="#{LockerMaster.lockerType}" style="width:121px">
                                <f:selectItems value="#{LockerMaster.lockerTypeList}"/>
                                <a4j:support action="#{LockerMaster.getTableValues}" event="onblur"
                                             reRender="errorMessage,lblMsg,btnAddNew,gridPanel112" />
                            </h:selectOneListbox>
                            <h:outputLabel id="lblCabinetNo" styleClass="label" value="Cabinet No :"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtCabinetNo" styleClass="input" style="120px" value="#{LockerMaster.cabinetNo}" maxlength="6">
                                <a4j:support  action="#{LockerMaster.checkCabinetNo}" event="onblur"
                                              reRender="lblMsg,errorMessage"  />
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row3" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblLockerNo" styleClass="label" value="Locker No :"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtLockerNo" styleClass="input" style="120px" value="#{LockerMaster.lockerNo}" maxlength="6">
                                <a4j:support action="#{LockerMaster.checkLockerNo}" event="onblur"
                                             reRender="lblMsg,errorMessage"  />
                            </h:inputText>
                            <h:outputLabel id="lblKeyNo" styleClass="label" value="Key No :"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtKeyNo" styleClass="input" style="120px" value="#{LockerMaster.keyNo}" maxlength="6">
                                <a4j:support action="#{LockerMaster.checkKeyNo}" event="onblur"
                                             reRender="lblMsg,btnAddNew,errorMessage" />
                            </h:inputText>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col1,col2" columns="1" id="gridPanel112" width="100%" styleClass="row2">
                        <a4j:region>
                            <rich:dataTable value ="#{LockerMaster.locMaster}"
                                            rowClasses="row1, row2" id = "LockerTable" rows="10" columnsWidth="100" rowKeyVar="row" var ="item"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column><h:outputText value="Locker Type"  /> </rich:column>
                                        <rich:column><h:outputText value="Cabinet No." /></rich:column>
                                        <rich:column><h:outputText value="Locker No." /></rich:column>
                                        <rich:column><h:outputText value="Key No." /></rich:column>
                                        <rich:column><h:outputText value="Status" /></rich:column>
                                        <rich:column><h:outputText value="Enter By" /></rich:column>
                                        <rich:column><h:outputText value="Select" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{item.lockerType}"/></rich:column>
                                <rich:column><h:outputText value="#{item.cabinetNo}"/></rich:column>
                                <rich:column><h:outputText value="#{item.lockerNo}"/></rich:column>
                                <rich:column><h:outputText value="#{item.keyNo}"/></rich:column>
                                <rich:column><h:outputText value="#{item.status}"/></rich:column>
                                <rich:column><h:outputText value="#{item.enterBy}"/></rich:column>
                                <rich:column>
                                    <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{LockerMaster.select}" focus="txtCabinetNo" reRender="leftPanel,btnAddNew,btnUpdate">
                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{item}" target="#{LockerMaster.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{LockerMaster.currentRow}" />
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="LockerTable" maxPages="20" />
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid id="poFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnUpdate" value="Update" disabled="#{LockerMaster.updateDisable}" action="#{LockerMaster.saveAction}"
                                               reRender="lblMsg,errorMessage,LockerTable,btnUpdate,leftPanel,btnAddNew"/>
                            <a4j:commandButton id="btnAddNew" value="Add New"  disabled="#{LockerMaster.addNewDisable}" action="#{LockerMaster.saveAction}"
                                               reRender="lblMsg,errorMessage,LockerTable,btnAddNew,leftPanel,Row4,btnUpdate"/>
                            <a4j:commandButton id="btnReset" value="Reset" action="#{LockerMaster.resetValue}"
                                               reRender="errorMessage,lblMsg,leftPanel,gridPanel112" focus="ddLockerType"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{LockerMaster.exitForm}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>