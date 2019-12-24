<%--
    Document   : transactionauthorization
    Created on : Aug 19, 2011, 2:27:18 PM
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>DDS Authorization</title>
        </head>
        <body>
            <a4j:keepAlive beanName="TransactionAuthorization"/>
            <a4j:form>
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="headerPanel" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{TransactionAuthorization.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="DDS Authorization"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="Username: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{TransactionAuthorization.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid id="errPanel" style="width:100%;text-align:center;" styleClass="row2">
                        <h:outputText value="#{TransactionAuthorization.errorMessage}" styleClass="error"/>
                    </h:panelGrid>

                    <%--h:panelGrid id="panel1" columnClasses="col2" columns="4" style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel value="Pending Agent Codes For Authorization" styleClass="label"/>
                        <h:selectOneListbox id="ddPendingAgents" styleClass="ddlist" size="1" value="#{TransactionAuthorization.pendingAgent}">
                            <f:selectItems value="#{TransactionAuthorization.pendingAgents}"/>
                        </h:selectOneListbox>
                    </h:panelGrid--%>

                    <h:panelGrid id="panel2" columnClasses="col13,col13,col13,col13" columns="4" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputLabel value="Pending Agents" styleClass="label"/>
                        <h:panelGroup>
                            <%--h:selectOneListbox id="ddAcctType" styleClass="ddlist" size="1">
                                <f:selectItems value="#{TransactionAuthorization.acctType}"/>
                            </h:selectOneListbox--%>
                            <h:selectOneListbox id="ddAgentCode" styleClass="ddlist" size="1" value="#{TransactionAuthorization.agent}" style="width:180px">
                                <f:selectItems value="#{TransactionAuthorization.pendingAgents}"/>
                                <a4j:support event="onblur" focus="ddRepType" reRender="errPanel,txtAgentName,panel3,footerPanel,stxtAmount,ddRepType"/>
                            </h:selectOneListbox>
                        </h:panelGroup>
                        <h:outputLabel value="Report Type" styleClass="label"/>
                        <h:selectOneListbox id="ddRepType" styleClass="ddlist" size="1" value="#{TransactionAuthorization.repTp}">
                            <f:selectItems value="#{TransactionAuthorization.repLst}"/>
                            <a4j:support event="onblur" action="#{TransactionAuthorization.onBlurAgentCode}" reRender="errPanel,txtAgentName,panel3,footerPanel,stxtAmount"/>
                        </h:selectOneListbox>                        
                    </h:panelGrid>
                    <h:panelGrid id="panel12" columnClasses="col13,col13,col13,col13" columns="4" style="width:100%;text-align:left;" styleClass="row1">
                        <%--h:outputLabel value="Agent Name :" styleClass="label"/>
                        <h:inputText id="txtAgentName" value="#{TransactionAuthorization.agentName}" readonly="true" styleClass="input"/--%>
                        <h:outputLabel value="Total Amount :" styleClass="label"/>
                        <h:outputText id="stxtAmount" styleClass="output" value="#{TransactionAuthorization.amount}"/>
                        <h:outputText/>
                        <h:outputText/>
                    </h:panelGrid>

                    <h:panelGrid id="panel3" columnClasses="col2,col2" columns="1" style="width:100%;text-align:left;" styleClass="row2">

                        <h:panelGrid id="panel9" columnClasses="vtop" columns="1" style="height:380px" styleClass="row2" width="100%">
                            <a4j:region>
                                <rich:dataTable id="entryTable" value="#{TransactionAuthorization.entryTable}" var="dataItem" rowClasses="gridrow1,gridrow2" rows="15" columnsWidth="100" rowKeyVar="row" onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column><h:outputText value="S.No." style="text-align:center"/></rich:column>
                                            <rich:column><h:outputText value="Receipt No." style="text-align:center"/></rich:column>
                                            <rich:column><h:outputText value="Account No." style="text-align:left" /></rich:column>
                                            <rich:column><h:outputText value="Amount" style="text-align:left"/></rich:column>
                                            <rich:column><h:outputText value="Entered By" style="text-align:left" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{dataItem.sno}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.receiptNo}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.accountNo}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.amount}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.enteredBy}" /></rich:column>
                                </rich:dataTable>
                                <rich:datascroller align="left" for="entryTable" maxPages="10" />
                            </a4j:region>
                        </h:panelGrid>

                    </h:panelGrid>

                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnAuthorize" value="Authorize" disabled="#{TransactionAuthorization.disableAuthorizeButton}" onclick="#{rich:component('confirmationPanel')}.show()" reRender="ddPendingAgents"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" type="reset" action="#{TransactionAuthorization.refresh}" reRender="mainPanel,ddAgentCode"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{TransactionAuthorization.exitBtnAction}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <rich:modalPanel id="confirmationPanel" autosized="true" width="250" onshow="#{rich:element('yes')}.focus()">
                        <f:facet name="header">
                            <h:outputText value="Confirmation DialogBox" />
                        </f:facet>
                        <h:form>
                            <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                <tbody>
                                    <tr style="height:40px">
                                        <td colspan="2">
                                            <h:outputText value="Are You Sure To Save The Data?"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="center" width="50%">
                                            <a4j:commandButton id="yes" value="Yes" ajaxSingle="true" action="#{TransactionAuthorization.authorizeBtnAction}"
                                                               onclick="#{rich:component('confirmationPanel')}.hide();" reRender="ddPendingAgents,mainPanel" />

                                        </td>
                                        <td align="center" width="50%">
                                            <a4j:commandButton id="No" value="No" onclick="#{rich:component('confirmationPanel')}.hide();return false;" />
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </h:form>
                    </rich:modalPanel>
                </h:panelGrid>
            </a4j:form>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>

