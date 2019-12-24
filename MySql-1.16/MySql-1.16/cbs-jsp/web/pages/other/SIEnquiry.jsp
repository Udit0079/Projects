<%-- 
    Document   : SIEnquiry
    Created on : Sep 20, 2010, 5:28:13 PM
    Author     : root
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title> Standing Instruction Enquiry</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript"></script>
        </head>
        <body>
            <a4j:form>
                <h:panelGrid bgcolor="#fff" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header" width="100%">
                        <h:panelGroup id="groupPanel" layout="block" >
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date:" />
                            <h:outputText id="stxtDate" styleClass="output" value="#{SIEnquiry.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblincident" styleClass="headerLabel" value=" Standing Instruction Enquiry"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lbluser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{SIEnquiry.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="d22" style="height:30px;text-align:center;" styleClass="row1" width="100%" >
                        <h:outputText id="stxtmessage" styleClass="error" value="#{SIEnquiry.message}"  style="width:100%;text-align:left;"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel1" columns="2" columnClasses="col8,col8" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblstatus" styleClass="headerLabel" style="padding-left:350px" value="Standing Instruction Enquiry " />
                        <h:selectOneListbox id="ddstatus" styleClass="ddlist" size="1" style="width: 150px"  value="#{SIEnquiry.status}">                           
                            <f:selectItems value="#{SIEnquiry.statusType}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columns="3" columnClasses="col8,col8" id="grdPanel2" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblactype" style="padding-left:350px" styleClass="headerLabel" value="Account No :" />
                        <h:panelGroup >
                            <h:inputText id="txtcode" styleClass="input"  size="#{SIEnquiry.acNoMaxLen}" style="width:100px" value="#{SIEnquiry.oldAcno}"  maxlength="#{SIEnquiry.acNoMaxLen}">
                                <a4j:support id="a4j" event="onblur" reRender="stxtmessage,taskList,a19,newAcno"  action="#{SIEnquiry.griddata}"/>
                            </h:inputText>
                            <h:outputLabel id="newAcno" styleClass="label" value="#{SIEnquiry.newAcno}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="a19" style="height:auto;" styleClass="row1" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{SIEnquiry.incis}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="12" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="8"><h:outputText value="Details" /></rich:column>
                                        <rich:column breakBefore="true" rendered="#{SIEnquiry.code1=='0' || SIEnquiry.code1=='2'}"  ><h:outputText  value="Credited A/c"  /></rich:column>
                                        <rich:column  rendered="#{SIEnquiry.code1=='1' || SIEnquiry.code1=='2'}" ><h:outputText value=" Debited A/c"/></rich:column>
                                        <rich:column><h:outputText value=" Inst No"/></rich:column>
                                        <rich:column><h:outputText value="Effective Dt" /></rich:column>
                                        <rich:column><h:outputText value="Amount" /></rich:column>
                                        <rich:column><h:outputText value="Status" /></rich:column>
                                        <rich:column><h:outputText value="Entry Date" /></rich:column>
                                        <rich:column><h:outputText value="Enter By" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column rendered="#{SIEnquiry.code1=='0' || SIEnquiry.code1=='2'}"  ><h:outputText value="#{dataItem.craccount}" /></rich:column>
                                <rich:column  rendered="#{SIEnquiry.code1=='1' || SIEnquiry.code1=='2'}"  ><h:outputText value="#{dataItem.draccount}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.instrnno}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.effdate}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.amount}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.status}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.entrydate}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.enteryby}" /></rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="20" />
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gridpanel6" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup layout="block" >
                            <a4j:commandButton id="btnRefresh" value="Refresh"  reRender="stxtmessage,taskList,ddstatus,ddactype,txtcode,grdPanel2,gridPanel1,newAcno" style="width:60px;" action="#{SIEnquiry.reFresh}">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnClose" value="Exit" style="width:60px;" action="#{SIEnquiry.exitForm}">
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </f:view>