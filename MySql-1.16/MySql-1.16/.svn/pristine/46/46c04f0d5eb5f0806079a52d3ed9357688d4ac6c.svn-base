<?xml version="1.0" encoding="UTF-8"?>
<!-- 
    Document   : GlDescription
    Created on : Oct 10, 2011, 11:55:40 AM
    Author     : sipl
-->

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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <title>Ledger Description Register</title>
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="glDescForm">
                <h:panelGrid bgcolor="#fff" columns="1" id="outerPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel1" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{GlDescription.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Ledger Description Register"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{GlDescription.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="mainPanel" style="width:100%;text-align:center;">
                        <h:panelGrid columnClasses="col2,col7" columns="2" id="msgRow" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputLabel id="lblMsg" styleClass="error" value = "#{GlDescription.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col6" columns="2" id="Row1" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblGlType" styleClass="label" value="G.L. Description"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtGlDesc" styleClass="input" maxlength="100" onblur="this.value = this.value.toUpperCase();" value="#{GlDescription.glType}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row2" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblGlRangeFr" styleClass="label" value="G.L. Range From" ><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtGlRangeFr" styleClass="input" maxlength="6" style="120px" value="#{GlDescription.rangeFrom}"/>
                            <h:outputLabel id="lblGlRangeTo" styleClass="label" value="G.L. Range To" ><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtGlRangeTo" styleClass="input" style="120px" maxlength="6" value="#{GlDescription.rangeTo}"/>
                        </h:panelGrid>                        
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col1,col1,col1,col1,col1" columns="1" id="GlPanel" width="100%" styleClass="row1">
                        <a4j:region>
                            <rich:dataTable value ="#{GlDescription.glDescTab}"
                                            rowClasses="row1, row2" id = "GLDescTable" rows="6" columnsWidth="100" rowKeyVar="row" var ="item"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="7"></rich:column>
                                        <rich:column breakBefore="true" style="width:5%;"><h:outputText value="Code No."  /> </rich:column>
                                        <rich:column style="width:40%;"><h:outputText value="GL Description" /></rich:column>
                                        <rich:column style="width:25%;"><h:outputText value="GL Range From" /></rich:column>
                                        <rich:column style="width:25%;"><h:outputText value="GL Range To" /></rich:column>
                                        <rich:column style="width:5%;"><h:outputText value="Select" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{item.glCode}"/></rich:column>
                                <rich:column><h:outputText value="#{item.glDes}"/></rich:column>
                                <rich:column><h:outputText value="#{item.glRangeFr}"/></rich:column>
                                <rich:column><h:outputText value="#{item.glRangeTo}"/></rich:column>
                                <rich:column>
                                    <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{GlDescription.select}" focus="txtCabinetNo" reRender="lblMsg,mainPanel,GlPanel,btnSave,btnUpdate">
                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{item}" target="#{GlDescription.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{GlDescription.currentRow}" />
                                        <rich:toolTip for="selectlink" value="Select" />
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                        </a4j:region>
                        <rich:datascroller align="left" for="GLDescTable" maxPages="40" />
                    </h:panelGrid>

                    <h:panelGrid id="poFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnSave" value="Save" disabled="#{GlDescription.saveDisable}" action="#{GlDescription.saveAction}" reRender="lblMsg,mainPanel,GlPanel,btnSave,btnUpdate"/>
                            <a4j:commandButton id="btnUpdate" value="Update" disabled="#{GlDescription.updateDisable}" action="#{GlDescription.updateAction}" reRender="lblMsg,mainPanel,GlPanel,btnSave,btnUpdate"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{GlDescription.refreshAction}" reRender="lblMsg,mainPanel,GlPanel,btnSave,btnUpdate"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{GlDescription.btnExit}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
