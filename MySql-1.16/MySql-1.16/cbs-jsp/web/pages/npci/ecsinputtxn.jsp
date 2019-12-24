<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>ECS TXN PROCESSING</title>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1"  style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="headerPanel" columns="3"  style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup>
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{ecsTxnBean.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="ECS TXN PROCESSING"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ecsTxnBean.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="messagePanel" style="width:100%;text-align:center;" 
                                 styleClass="row1" width="100%">
                        <h:outputText id="stxtMessage" styleClass="error" value="#{ecsTxnBean.errorMessage}"/>
                    </h:panelGrid>
                    <h:panelGrid id="txnPanelGrid" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" 
                                 styleClass="row2" width="100%">
                        <h:outputLabel id="lblTxnType" styleClass="label" value="Txn.Type"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddTxnType" style="width:140px" styleClass="ddlist"  value="#{ecsTxnBean.txnType}" size="1">
                            <f:selectItems value="#{ecsTxnBean.txnTypeList}"/>
                            <a4j:support event="onblur" action="#{ecsTxnBean.txnTypeAction}" 
                                         reRender="stxtMessage,tablePanel,taskList"/>
                        </h:selectOneListbox>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;" styleClass="row1" width="100%">
                        <rich:extendedDataTable enableContextMenu="false" id="taskList" width="100%" 
                                                value="#{ecsTxnBean.gridDetail}" styleClass="gridrow1,gridrow2,noTableBorder"
                                                height="231px" var="dataItem" rowKeyVar="row" 
                                                noDataLabel="No Record Found">
                            <f:facet name="header">
                                <h:outputText value="Entry Detail"/>
                            </f:facet>
                            <rich:column width="12%">
                                <f:facet name="header">
                                    <h:outputText value="Unique Txn No"/>
                                </f:facet>
                                <h:outputText value="#{dataItem.uRefNo}"/>
                            </rich:column>
                            <rich:column width="12%">
                                <f:facet name="header">
                                    <h:outputText value="MICR"/>
                                </f:facet>
                                <h:outputText value="#{dataItem.micr}"/>
                            </rich:column>
                            <rich:column width="12%">
                                <f:facet name="header">
                                    <h:outputText value="A/c Type"/>
                                </f:facet>
                                <h:outputText value="#{dataItem.acType}"/>
                            </rich:column>
                            <rich:column width="12%">
                                <f:facet name="header">
                                    <h:outputText value="A/c No"/>
                                </f:facet>
                                <h:outputText value="#{dataItem.acNo}"/>
                            </rich:column>
                            <rich:column width="12%">
                                <f:facet name="header">
                                    <h:outputText value="A/c Name"/>
                                </f:facet>
                                <h:outputText value="#{dataItem.name}"/>
                            </rich:column>
                            <rich:column width="12%">
                                <f:facet name="header">
                                    <h:outputText value="Amount"/>
                                </f:facet>
                                <h:outputText value="#{dataItem.amount}"/>
                            </rich:column>
                            <rich:column width="12%">
                                <f:facet name="header">
                                    <h:outputText value="Own Bank A/c No"/>
                                </f:facet>
                                <h:outputText value="#{dataItem.ownAcno}"/>
                            </rich:column>
                            <rich:column width="12%">
                                <f:facet name="header">
                                    <h:outputText value="Enter By"/>
                                </f:facet>
                                <h:outputText value="#{dataItem.entry_by}"/>
                            </rich:column>
                            <rich:column width="4%" style="text-align:center"> 
                                <f:facet name="header">
                                    <h:selectBooleanCheckbox value="#{ecsTxnBean.allSelected}">
                                        <a4j:support event="onchange" action="#{ecsTxnBean.setAllBoxSelected}" reRender="taskList"/>
                                    </h:selectBooleanCheckbox>
                                </f:facet>
                                <h:selectBooleanCheckbox value="#{dataItem.selected}"/>
                            </rich:column>
                        </rich:extendedDataTable>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnHtml" value="Process" oncomplete="#{rich:component('processPanel')}.show();" 
                                               reRender="stxtMessage,processPanel"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{ecsTxnBean.btnRefreshAction}" 
                                               reRender="mainPanel"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{ecsTxnBean.btnExitAction}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:region id="btnProcessRegion">
                <rich:modalPanel id="processPanel" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText id="confirmid" value="Are you sure to process ?"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="btnYes" action="#{ecsTxnBean.processAction}" 
                                                           oncomplete="#{rich:component('processPanel')}.hide(); #{rich:element('ddTxnType')}.focus();" 
                                                           reRender="stxtMessage,mainPanel"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('processPanel')}.hide();"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
            </a4j:region>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="btnProcessRegion"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
