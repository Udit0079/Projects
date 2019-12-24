<%-- 
    Document   : chargeposting
    Created on : Jul 28, 2012, 3:44:53 PM
    Author     : Ankit Verma
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Charge Master</title>
        </head>
        <body>
            <a4j:form>
                <a4j:region id="btnAjaxGrid">
                    <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                        <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                            <h:panelGroup id="groupPanel2" layout="block">
                                <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                                <h:outputText id="stxtDate" styleClass="output" value="#{chargePosting.todayDate}"/>
                            </h:panelGroup>
                            <h:outputLabel id="label2" styleClass="headerLabel" value="Charge Posting"/>
                            <h:panelGroup id="groupPanel3" layout="block">
                                <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                                <h:outputText id="stxtUser" styleClass="output" value="#{chargePosting.userName}"/>
                            </h:panelGroup>                         
                        </h:panelGrid>
                        <h:panelGrid id="inputPanel" columnClasses="col3,col3,col3,col3,col3,col1" columns="6" styleClass="row1" style="width:100%;text-align:left;">
                            <h:outputLabel id="lblType" styleClass="label" value="Type"/>
                            <h:selectOneListbox id="rDDType" value="#{chargePosting.type}" style="ddstyle" size="1">
                                <f:selectItem itemValue="0" itemLabel="--SELECT--" />
                                <f:selectItem itemLabel="ALL" itemValue="A"/>
                                <f:selectItem itemLabel="Individual" itemValue="I"/>
                                <a4j:support event="onblur" action="#{chargePosting.onBlurOfType}" reRender="msg,txtAccountNo,ddType" focus="#{chargePosting.focusId}"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblAccountNo" value="Account No." styleClass="label"/>
                            <h:panelGroup layout="block" style="width:100%;text-align:left;">
                                <h:inputText id="txtAccountNo" styleClass="input" value="#{chargePosting.acNo}" maxlength="#{chargePosting.acNoMaxLen}" disabled="#{chargePosting.disableAccountNo}">
                                    <a4j:support event="onblur" action="#{chargePosting.onBlurGetNewAccount}" reRender="ddType,stxtAccNo" focus="ddType"/> 
                                </h:inputText>
                                <h:outputText id="stxtAccNo" styleClass="output" value="#{chargePosting.accountNo}"/>
                            </h:panelGroup>
                            <h:outputLabel id="lblMessageType" styleClass="label" value="Request Type"/>
                            <h:selectOneListbox id="ddType" value="#{chargePosting.messageType}" style="ddstyle" size="1">
                                <f:selectItem itemValue="0" itemLabel="--SELECT--"/>
                                <f:selectItem itemValue="PROMOTIONAL"/>
                                <f:selectItem itemValue="TRANSACTIONAL"/>
                                <f:selectItem itemValue="ON-REQUEST"/>
                                <a4j:support event="onblur" action="#{chargePosting.onBlurOfRequestType}" reRender="msg,grid,datatablepanelGrid,save" focus="save"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid id="datatablepanelGrid" style="width:100%"> 
                            <rich:dataTable id="grid" value="#{chargePosting.chargePostingList}" var="dataItem" rows="10" width="100%" >
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column width=""><h:outputText value="S.No." /></rich:column>
                                        <rich:column><h:outputText value="Account no." /></rich:column>
                                        <rich:column><h:outputText value="Billing Account no." /></rich:column>
                                        <rich:column><h:outputText value="Message Type"/></rich:column>
                                        <rich:column><h:outputText value="No. of Messages"/></rich:column>
                                        <rich:column><h:outputText value="Charges"/></rich:column>
                                        <%--<rich:column><h:outputText value="Service Charge"/></rich:column>--%>
                                        <rich:column><h:outputText value="From Date"/></rich:column>
                                        <rich:column><h:outputText value="To Date"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.sno}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.acno}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.billingAcno}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.messageType}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.noOfMsg}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.charges}"/></rich:column>
                                <%--<rich:column><h:outputText value="#{dataItem.serCharges}"/></rich:column>--%>
                                <rich:column><h:outputText value="#{dataItem.fromDate}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.toDate}"/></rich:column>
                            </rich:dataTable>
                            <rich:datascroller for="grid" maxPages="50" style="text-align:left"/>
                        </h:panelGrid> 
                        <h:panelGrid columns="2" columnClasses="col10,col11" style="text-align:left;width:100%" width="100%" styleClass="footer" >
                            <h:outputText id="msg" styleClass="error" value="#{chargePosting.msg}"/>

                            <h:panelGroup id="btnGroupPanel" >
                                <a4j:commandButton id="save" value="Post" actionListener="#{chargePosting.postAction}" reRender="mainPanel"/>
                                <a4j:commandButton id="refresh" value="Refresh" action="#{chargePosting.refreshForm}"  reRender="mainPanel" focus="rDDType"/>
                                <a4j:commandButton id="poBtnCancel" value="Exit"  action="#{chargePosting.exitForm}" reRender="mainPanel"/>
                            </h:panelGroup>

                        </h:panelGrid>
                    </h:panelGrid>
                </a4j:region>
            </a4j:form>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="btnAjaxGrid"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>