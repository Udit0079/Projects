<%-- 
    Document   : ChequeBookAuthorization
    Created on : 28 Oct, 2010, 11:02:14 AM
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
            <title>Cheque Book Authorization</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{ChequeBookAuthorization.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Cheque Book Authorization"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ChequeBookAuthorization.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                        <h:panelGrid  columns="1" id="msgRow" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputLabel id="lblMsg" styleClass="error" value="#{ChequeBookAuthorization.message}"/>
                            <h:outputText id="errorMessage" styleClass="msg" value="#{ChequeBookAuthorization.finalMsg}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row1" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblTodayDate" styleClass="label" value="Today Date is"/>
                            <h:outputText id="stxtTodayDate" styleClass="output" value="#{ChequeBookAuthorization.todayDate}"/>
                            <h:outputLabel id="lblAuthorizationType" styleClass="label" value="Authorization Type "/>
                            <h:selectOneListbox id="ddAuthorizationType" styleClass="ddlist"  size="1" value="#{ChequeBookAuthorization.authType}">

                                <f:selectItems value="#{ChequeBookAuthorization.authTypeList}"/>
                                <a4j:support actionListener="#{ChequeBookAuthorization.chbkAuthTableValues}" event="onblur"
                                              reRender="ChequeBookAuthorizationPanel"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col1,col2" columns="1" id="ChequeBookAuthorizationPanel" width="100%" styleClass="row2">
                        <a4j:region>
                            <rich:dataTable value ="#{ChequeBookAuthorization.chBookList}"
                                            rowClasses="row1, row2" id = "ChequeBookAuthorizationTable" rows="10" columnsWidth="100" rowKeyVar="row" var ="item"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column><h:outputText value="Account No."  /> </rich:column>
                                        <rich:column><h:outputText value="Customer Name" /></rich:column>
                                        <rich:column><h:outputText value="Chq Series" /></rich:column>
                                        <rich:column><h:outputText value="Chq No From" /></rich:column>
                                        <rich:column><h:outputText value="Chq No To" /></rich:column>
                                        <rich:column><h:outputText value="No Of Leafs" /></rich:column>
                                        <rich:column><h:outputText value="Issue By" /></rich:column>
                                        <rich:column><h:outputText value="Charges" /></rich:column>
                                        <rich:column><h:outputText value="Auth " /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{item.acno}"/></rich:column>
                                <rich:column><h:outputText value="#{item.custName}"/></rich:column>
                                <rich:column><h:outputText value="#{item.chqSeries}"/></rich:column>
                                <rich:column><h:outputText value="#{item.chqNoFrom}"/></rich:column>
                                <rich:column><h:outputText value="#{item.chqNoTo}"/></rich:column>
                                <rich:column><h:outputText value="#{item.noOfLeaf}"/></rich:column>
                                <rich:column><h:outputText value="#{item.issuedBy}"/></rich:column>
                                <rich:column><h:outputText value="#{item.chargeFlag == 'Y'? 'Yes' : 'No'}"/></rich:column>
                                <rich:column style="text-align:center;cursor:pointer;">
                                    <h:outputText value="#{item.auth}"/>
                                    <a4j:support action="#{ChequeBookAuthorization.changeStatus}" ajaxSingle="true" event="ondblclick" reRender="ChequeBookAuthorizationPanel,btnAuthorize,lblMsg,errorMessage" >
                                        <f:setPropertyActionListener value="#{row}" target="#{ChequeBookAuthorization.currentRow}"/>
                                    </a4j:support>
                                </rich:column>
                            </rich:dataTable>
                        </a4j:region>
                        <rich:datascroller align="left" for="ChequeBookAuthorizationTable" maxPages="40" />
                    </h:panelGrid>
                    <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
                    <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <f:facet name="header">
                            <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                        </f:facet>
                    </rich:modalPanel>
                    <rich:modalPanel id="savePanel" autosized="true" width="200" onshow="#{rich:element('btnYes')}.focus();">
                        <f:facet name="header">
                            <h:outputText value="Confirm Dialog ?" style="padding-right:15px;" />
                        </f:facet>
                        <h:form>
                            <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                <tbody>
                                    <tr style="height:40px">
                                        <td colspan="2" width="50%">
                                            <h:outputText value="Are You Sure To Authorize ?" styleClass="output"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="Yes" id="btnYes" ajaxSingle="true" action="#{ChequeBookAuthorization.saveAction}"
                                                               onclick="#{rich:component('savePanel')}.hide();" reRender="lblMsg,ChequeBookAuthorizationPanel,btnAuthorize,errorMessage"/>
                                        </td>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="Cancel"  ajaxSingle="true" onclick="#{rich:component('savePanel')}.hide();return false;" />
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </h:form>
                    </rich:modalPanel>

                    <h:panelGrid id="poFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnAuthorize" value="Authorize" onclick="#{rich:component('savePanel')}.show()" disabled="#{ChequeBookAuthorization.disableAuth}" reRender="btnAuthorize">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnRefresh" value="Refresh">
                                <a4j:support  event="onclick" action="#{ChequeBookAuthorization.refresh}"
                                              reRender="lblMsg,ChequeBookAuthorizationPanel,leftPanel,errorMessage,ChequeBookAuthorizationTable"/>
                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{ChequeBookAuthorization.exitBtnAction}" reRender="lblMsg,ChequeBookAuthorizationPanel,leftPanel,errorMessage">
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
