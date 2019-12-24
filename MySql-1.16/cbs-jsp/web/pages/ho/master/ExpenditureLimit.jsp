<%-- 
    Document   : ExpenditureLimit
    Created on : Mar 5, 2012, 11:32:54 AM
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
            <title>Expenditure Limit</title>
        </head>
        <body>

            <a4j:form id="form1">
                <h:panelGrid columns="1" id="el" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="el1" styleClass="header" style="width:100%;text-align:center;">
                         <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{ExpenditureLimit.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Expenditure Limit" />
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ExpenditureLimit.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid> 
                    <h:panelGrid id="el2" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="message" styleClass="error" value="#{ExpenditureLimit.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="el3" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="ela" styleClass="label" value="Branch" style="padding-left:350px;"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="cdBank" style="width:21px;" styleClass="ddlist" size="1" value="#{ExpenditureLimit.branchName}" >
                            <f:selectItems value="#{ExpenditureLimit.branchNamesList}"/>
                            <a4j:support actionListener="#{ExpenditureLimit.getLimitDetails()}" event="onblur" reRender="eld,elbtnSave,elbtnUpdate" focus="eld"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="el4" columnClasses="col8,col8" columns="2" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="elc" styleClass="label" value="Limit" style="padding-left:350px;"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="eld" styleClass="input" value="#{ExpenditureLimit.amount}">
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="cdFooter" style="width:100%;text-align:center;" styleClass="footer">    
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="elbtnSave" value="Save" action="#{ExpenditureLimit.saveDetails}" reRender="message,cdBank" disabled="#{ExpenditureLimit.flag}"/>
                            <a4j:commandButton id="elbtnUpdate" value="Update" action="#{ExpenditureLimit.updateDetails()}" reRender="message,cdBank" disabled="#{ExpenditureLimit.flag1}"/>
                            <a4j:commandButton id="elbtnRefresh" value="Refresh" action="#{ExpenditureLimit.refreshBtn()}" reRender="message,cdBank,eld,elbtnSave,elbtnUpdate" />
                            <a4j:commandButton id="elbtnExit" value="Exit" action="#{ExpenditureLimit.exitBtn()}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>      

            </a4j:form>
        </body>
    </html>
</f:view>
