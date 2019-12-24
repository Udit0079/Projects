<%-- 
    Document   : callmoneyauth
    Created on : Jul 6, 2012, 2:28:13 PM
    Author     : root
--%>

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
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Call Money Authorization</title>
        </head>
        <body>
            <a4j:form>
                <h:panelGrid id="maingrid" columns="1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid id="headergrid" columns="3" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{callMoneyAuth.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Call Money Authorization"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{callMoneyAuth.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid id="msggrid" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7;border:1px ridge #BED6F8;">
                        <h:outputText id="message" styleClass="error" value="#{callMoneyAuth.message}"/>
                    </h:panelGrid>

                    <h:panelGrid id="functiongrid" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row1" width="100%">
                        <h:outputLabel id="lblFunction" styleClass="label" value="Function"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddFunction" style="width: 120px" styleClass="ddlist"  value="#{callMoneyAuth.function}" size="1">
                            <f:selectItems value="#{callMoneyAuth.functionList}" />
                            <a4j:support action="#{callMoneyAuth.onChangeFunction}" event="onchange" reRender="message,txtCtrlNo,btnProcess"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblCtrlNo" styleClass="label" value="Control No."><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtCtrlNo" value="#{callMoneyAuth.ctrlNo}" size="10" styleClass="input">
                            <a4j:support action="#{callMoneyAuth.onBlurCtrlNo}" event="onblur" reRender="message,btnProcess" focus="btnProcess"/>
                        </h:inputText>
                    </h:panelGrid>

                    <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                        <a4j:region id="tblRegion">
                            <rich:dataTable value="#{callMoneyAuth.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column><h:outputText value="SNo."/></rich:column>
                                        <rich:column><h:outputText value="Controll No."/></rich:column>
                                        <rich:column><h:outputText value="Deal Date"/></rich:column>
                                        <rich:column><h:outputText value="Roi"/></rich:column>
                                        <rich:column><h:outputText value="Amount"/></rich:column>
                                        <rich:column><h:outputText value="No Of Days"/></rich:column>
                                        <rich:column><h:outputText value="Comp. Date"/></rich:column>
                                        <rich:column><h:outputText value="int. Amount "/></rich:column>
                                        <rich:column><h:outputText value="Details" /></rich:column>
                                        <rich:column><h:outputText value="Enter By" /></rich:column>
                                        <rich:column><h:outputText value="GlHead" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.sno}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.ctrlNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.dealDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.roi}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.amount}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.noOfDays}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.compDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.intAmount}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.details}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.enterBy}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.glheadAcno}" /></rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="10"/>
                        </a4j:region>
                    </h:panelGrid>

                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnProcess" value="#{callMoneyAuth.processBtnCaption}" action="#{callMoneyAuth.populateMessage}" oncomplete="#{rich:component('processPanel')}.show();" disabled="#{callMoneyAuth.processBtnVisible}" reRender="message,ddFunction,txtCtrlNo,tablePanel,taskList,processPanel,confirmid" />
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{callMoneyAuth.resetForm}" reRender="maingrid,processPanel,confirmid"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{callMoneyAuth.exitBtnAction}" reRender="maingrid,processPanel,confirmid"/>
                        </h:panelGroup>
                    </h:panelGrid>

                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="processPanel" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText id="confirmid" value="#{callMoneyAuth.confirmationMsg}"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" action="#{callMoneyAuth.processAction}" 
                                                       oncomplete="#{rich:component('processPanel')}.hide(); #{rich:element('ddFunction')}.focus();" 
                                                       reRender="message,maingrid,tablePanel,taskList"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('processPanel')}.hide();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
