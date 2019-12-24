<%-- 
    Document   : CircularDetail
    Created on : Mar 2, 2012, 2:59:10 PM
    Author     : Deepshikha
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
            <title>Circular Details</title>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="cd" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{CircularDetails.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Circular Details"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{CircularDetails.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid> 
                    <h:panelGrid id="cd1" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="message" styleClass="msg" value="#{CircularDetails.messagea}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="cd2" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="cdll" styleClass="label" value="Circular No." style="padding-left:350px;"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="circularno" styleClass="input" value="#{CircularDetails.circularno}">
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid id="cd3" columnClasses="col8,col8" columns="2" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="cddl" styleClass="label" value="Circular Details" style="padding-left:350px;"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputTextarea id="circulardetail" value="#{CircularDetails.circulardetail}" cols="50" rows="3">
                        </h:inputTextarea>   
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="Stocktable" width="100%" styleClass="row2" style="height:168px;">
                        <a4j:region>
                            <rich:dataTable value="#{CircularDetails.circularList}" var="item" 
                                            rowClasses="row1, row2" id="taskList" rows="3" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column> <h:outputText value="Serial No." /> </rich:column>
                                        <rich:column> <h:outputText value="Circular No." /> </rich:column>
                                        <rich:column> <h:outputText value="Circular Detail" /> </rich:column>  
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column width="10%"><h:outputText value="#{item.no}" /></rich:column>
                                <rich:column width="28%"><h:outputText value="#{item.cirNo}" /></rich:column>
                                <rich:column width="28%"><h:outputText value="#{item.cirDtl}" /></rich:column>
                            </rich:dataTable>
                            <rich:datascroller id="scroller" align="left" for="taskList" maxPages="10" />
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="cdFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnSave" value="Save" action="#{CircularDetails.saveDetails()}" reRender="message"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{CircularDetails.exitBtn()}" />
                        </h:panelGroup>
                    </h:panelGrid>  
                </h:panelGrid>
            </a4j:form>
        </body> 
    </html>
</f:view>
