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
            <title>Ledger Code Maintenance Register</title>
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
        </head>
        <body>
            <a4j:form id="ledgerForm">
                <h:panelGrid bgcolor="#fff" columns="1" id="outerPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{LedgerCodeMaintenanceRegister.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Ledger Code Maintenance Register"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{LedgerCodeMaintenanceRegister.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7" columns="2" id="msgRow" style="width:100%;text-align:center;" styleClass="row1">
                        <h:outputLabel id="lblMsg" styleClass="error" value = "#{LedgerCodeMaintenanceRegister.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="Row1" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputLabel id="lblGlType" styleClass="label" value="G.L. Type"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddGlType" styleClass="ddlist"  size="1"  value="#{LedgerCodeMaintenanceRegister.glType}">
                                <f:selectItems value="#{LedgerCodeMaintenanceRegister.glTypeList}"/>
                            </h:selectOneListbox>
                            <h:outputLabel/>
                            <h:outputLabel/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="Row2" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblFrNo" styleClass="label" value="From Code No." ><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtFrNo" styleClass="input" size="8" value="#{LedgerCodeMaintenanceRegister.fromNo}" maxlength="6"/>
                            <h:outputLabel id="lblToNo" styleClass="label" value="To Code No." ><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtToNo" styleClass="input" size="8" value="#{LedgerCodeMaintenanceRegister.toNo}" maxlength="6"/>
                        </h:panelGrid>
                        <h:panelGrid id="glDetailPanel" columnClasses="col1,col2" columns="1" width="100%" styleClass="row1">
                            <rich:dataTable id="glTable" value="#{LedgerCodeMaintenanceRegister.trip}" rowClasses="row1,row2" rows="6" columnsWidth="100" rowKeyVar="row" var="item"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="3"></rich:column>
                                        <rich:column breakBefore="true" width="20%">
                                            <h:outputText value="GL Name"/>
                                        </rich:column>
                                        <rich:column width="20%">
                                            <h:outputText value="From No."/>
                                        </rich:column>
                                        <rich:column width="20%">
                                            <h:outputText value="To No."/>
                                        </rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{item.desc}"/></rich:column>
                                <rich:column><h:outputText value="#{item.code}"/></rich:column>
                                <rich:column><h:outputText value="#{item.glCode}"/></rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="glTable" maxPages="40"/>
                        </h:panelGrid>
                        <rich:modalPanel id="savePanel" autosized="true" width="250" onshow="#{rich:element('btnYes')}.focus();">
                            <f:facet name="header">
                                <h:outputText value="Are you sure to save data ?" style="padding-right:15px;" />
                            </f:facet>
                            <h:form>
                            <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                <tbody>
                                    <tr style="height:50px">
                                        <td align="center" width="50%" colspan="2">
                                            <a4j:commandButton value="Yes" id= "btnYes" ajaxSingle="true" action="#{LedgerCodeMaintenanceRegister.saveAction}"

                                                               onclick="#{rich:component('savePanel')}.hide();" reRender="lblMsg,ddGlType,txtFrNo,txtToNo,glDetailPanel,glTable"/>
                                        </td>
                                        <td align="center" width="50%" colspan="2">
                                            <a4j:commandButton value="Cancel"  ajaxSingle="true" onclick="#{rich:component('savePanel')}.hide();return false;" />
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </h:form>
                    </rich:modalPanel>
                    <h:panelGrid id="poFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnSave" value="Save" oncomplete="#{rich:component('savePanel')}.show();"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" actionListener="#{LedgerCodeMaintenanceRegister.resetValues}" reRender="lblMsg,ddGlType,txtFrNo,txtToNo,glDetailPanel,glTable"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{LedgerCodeMaintenanceRegister.btnExit}" reRender="lblMsg,ddGlType,txtFrNo,txtToNo,glDetailPanel,glTable"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>