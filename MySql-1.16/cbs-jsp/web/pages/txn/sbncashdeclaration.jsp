<%--
    Document   : NpciInputDetail
    Created on : Feb 1, 2016, 12:56:06 PM
    Author     : Administrator
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
            <title>SBN Cash Deposit Declaration</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
        </head>
        <body>
            <a4j:form id="npciInputForm">
                <h:panelGrid bgcolor="#fff" columns="1" id="outerPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="headerPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{SbnDepositDecl.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="SBN Cash Deposit Declaration"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{SbnDepositDecl.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="mainPanel" style="width:100%;text-align:center;">
                        <h:panelGrid columnClasses="col2,col7" columns="2" id="msgRow" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputLabel id="lblMsg" styleClass="error" value="#{SbnDepositDecl.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1,col4,col1,col4,col1,col4" columns="6" id="gridPanel1" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblFuntion" styleClass="label" value="Function" style="display:"/>
                            <h:selectOneListbox id="ddFunction" styleClass="ddlist" size="1" style="width:80px;" value="#{SbnDepositDecl.function}" >
                                <f:selectItems value="#{SbnDepositDecl.functionList}"/>
                                <a4j:support event="onblur" action="#{SbnDepositDecl.funcAction}" reRender="mainPanel,btnLabel,lblMsg,postPanel,npciPanelGrid" focus="#{SbnDepositDecl.focusId}"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="labelacNo" styleClass="label" value="Account No "/>
                            <h:panelGroup layout="block">
                                <h:inputText id="txtacNo" styleClass="input" value="#{SbnDepositDecl.acNo}" maxlength="12" size="15" readonly="#{SbnDepositDecl.readable}">
                                    <a4j:support event="onblur" action="#{SbnDepositDecl.accNoLostFocus}" reRender="mainPanel,lblMsg" focus="txtAmt"/>
                                </h:inputText>
                                <h:outputText id="txtnewacNo" styleClass="output" value="#{SbnDepositDecl.newAcno}"/>
                            </h:panelGroup>
                            <h:outputLabel id="labelcustId" styleClass="label" value="Customer Id "/>
                            <h:outputText id="txtcustId" styleClass="output" value="#{SbnDepositDecl.custId}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1,col4,col1,col4,col1,col4" columns="6" id="gridPanel2" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="labelname" styleClass="label" value="Customer Name "/>
                            <h:outputText id="txtname" styleClass="output" value="#{SbnDepositDecl.acName}"/>                                                        
                            <h:outputLabel id="labeljtName" styleClass="label" value="JT/GU Name "/>
                            <h:outputText id="txtjtName" styleClass="output" value="#{SbnDepositDecl.jtName}"/>
                            <h:outputLabel id="labeloperMode" styleClass="label" value="Operation Mode "/>
                            <h:outputText id="txtopermode" styleClass="output" value="#{SbnDepositDecl.operMode}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1,col4,col1,col4,col1,col4" columns="6" id="gridPanel3" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="labelOpenDt" styleClass="label" value="Opening Date"/>
                            <h:outputText id="txtOpenDt" styleClass="output" value="#{SbnDepositDecl.openingDt}"/>
                            <h:outputLabel id="labelStatus" styleClass="label" value="Account Status"/>
                            <h:outputText id="txtStatus" styleClass="output" value="#{SbnDepositDecl.status}"/>
                            <h:outputLabel id="labelAmt" styleClass="label" value="Amount "/>
                            <h:inputText id="txtAmt" styleClass="input" value="#{SbnDepositDecl.amt}" maxlength="13" size="15" readonly="#{SbnDepositDecl.readable}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1,col42,col1,col4" columns="4" id="gridPanel4" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="labelDetails" styleClass="label" value="Declaration"/>
                            <h:inputTextarea id="txtdetails" value="#{SbnDepositDecl.details}" rows="3" style="width:400px" readonly="#{SbnDepositDecl.readable}"/>
                            <h:outputLabel/>
                            <h:outputLabel/>
                        </h:panelGrid>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col1,col2" columns="1" id="npciPanelGrid" width="100%" styleClass="row1" style="display:#{SbnDepositDecl.gridStyle};height:50%">
                        <a4j:region>
                            <rich:dataTable value ="#{SbnDepositDecl.itemList}"
                                            rowClasses="row1, row2" id = "taskList" rows="6" columnsWidth="100" rowKeyVar="row" var ="dataItem"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column><h:outputText value="Account No"/> </rich:column>
                                        <rich:column><h:outputText value="Customer Name" /></rich:column>
                                        <rich:column><h:outputText value="Amount" /></rich:column>
                                        <rich:column><h:outputText value="Declaration" /></rich:column>
                                        <rich:column><h:outputText value="Enter BY" /></rich:column>
                                        <rich:column><h:outputText value="Select" /></rich:column>                                        
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.acNo}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.custName}"/></rich:column>
                                <rich:column style="text-align:right;">
                                    <h:outputText value="#{dataItem.instAmt}">
                                        <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                    </h:outputText>
                                </rich:column>
                                <rich:column><h:outputText value="#{dataItem.details}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.enterBy}"/></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{SbnDepositDecl.processData}" reRender="lblMsg,ddFunction,mainPanel">
                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{SbnDepositDecl.currentItem}"/>                                    
                                    </a4j:commandLink>
                                </rich:column>                                
                            </rich:dataTable>
                        </a4j:region>
                        <rich:datascroller align="left" for="taskList" maxPages="20"/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnLabel" value="#{SbnDepositDecl.btnLbl}" onclick="#{rich:component('postPanel')}.show()"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{SbnDepositDecl.refresh}" 
                                               reRender="mainPanel,npciPanelGrid,btnPanel"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{SbnDepositDecl.exitForm}" reRender="lblMsg"/>                            
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="postPanel" autosized="true" width="200" onshow="#{rich:element('btnYes')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirm Dialog ?" style="padding-right:15px;" />
                </f:facet>
                <a4j:region id="postRegion">
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px;">
                                    <td colspan="2">
                                        <h:outputText value="#{SbnDepositDecl.btnPopUp}" styleClass="output"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="btnYes" ajaxSingle="true" action="#{SbnDepositDecl.saveDetails}"
                                                           onclick="#{rich:component('postPanel')}.hide();" reRender="mainPanel,npciPanelGrid,btnPanel"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Cancel"  ajaxSingle="true" onclick="#{rich:component('postPanel')}.hide();return false;"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </a4j:region>
            </rich:modalPanel>    
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>