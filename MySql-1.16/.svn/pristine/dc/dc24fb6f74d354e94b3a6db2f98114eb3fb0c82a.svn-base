<!-- 
    Document   : Misc_Parameters
    Created on : Mar 31, 2010, 4:31:28 PM
    Author     : root
-->
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Misc Parameters</title>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{MiscParameters.todayDate}" />
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="MISC Parameters"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label14" styleClass="headerLabel" value="User:"/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{MiscParameters.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid columns="1" width="100%">
                        <h:panelGrid id="errorPanel" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                            <h:outputText id="stxtError" styleClass="error" value="#{MiscParameters.errorMsg}"/>
                        </h:panelGrid>

                        <h:panelGrid id="gridPanel2" columns="4" columnClasses="col2,col7,col2,col7" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblPurpose" styleClass="label" value="Purpose"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddPurpose" tabindex="1" styleClass="ddlist" size="1" value="#{MiscParameters.purpose}">
                                <f:selectItems value="#{MiscParameters.acctDescOption}"/>
                                <a4j:support  action="#{MiscParameters.onChangeListBox}" event="onchange" reRender="txtCharges,txtCharges1"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblAccount" styleClass="label" value="Account Type"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddAccount" tabindex="2" styleClass="ddlist" size="1" value="#{MiscParameters.accType}" style="width:90px;">
                                <f:selectItems value="#{MiscParameters.balTypeOption}"/>
                                <a4j:support action="#{MiscParameters.onLoadAcctTypeAndPurpose}" event="onblur" reRender="ddMisc,txtCharges,txtCharges1,calDtEffect,stxtOther5,stxtOther2"/>
                            </h:selectOneListbox>
                        </h:panelGrid>

                        <h:panelGrid id="gridPanel21" columns="4" columnClasses="col2,col7,col2,col7" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblMisc" styleClass="label" value="Misc.GL Head" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddMisc" tabindex="3" styleClass="ddlist" size="1" value="#{MiscParameters.miscGLHead}" style="width:200px;">
                                <f:selectItems value="#{MiscParameters.gLHeadtype}"/>
                                <a4j:support event="onblur" action="#{MiscParameters.setGLHeadAndDesc}" reRender="stxtOther2,stxtOther5"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblCharges" styleClass="label" value="Charges"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText  disabled="#{MiscParameters.charge}" tabindex="4" id="txtCharges" styleClass="input" style="width:90px" value="#{MiscParameters.code1}">
                                <a4j:support event="onblur" action="#{MiscParameters.onblurCharges}" reRender="stxtError,txtCharges"/>
                            </h:inputText>
                        </h:panelGrid>

                        <h:panelGrid id="gridPanel19" columns="4" columnClasses="col2,col7,col2,col7" width="100%" style="height:30px;" styleClass="row1">
                            <h:outputLabel id="lblCharges1" styleClass="label" value="Charges1"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText disabled="#{MiscParameters.charge1}" tabindex="5" id="txtCharges1" styleClass="input" style="width:90px" value="#{MiscParameters.code2}">
                                <a4j:support event="onblur" action="#{MiscParameters.onblurCharges1}" reRender="stxtError,stxtOther2"/>
                            </h:inputText>
                            <h:outputLabel id="lblOther" styleClass="label" value="Issue / Selling Date"><font class="required" color="red">*</font></h:outputLabel>
                            <h:panelGroup id="groupPanel28" layout="block" >
                                <rich:calendar datePattern="dd/MM/yyyy" tabindex="6" id="calDtEffect" value="#{MiscParameters.issueDate}" inputSize="10">
                                    <a4j:support event="onblur"  action="#{MiscParameters.onChangeCalendar}" reRender="stxtError"/>
                                </rich:calendar>
                            </h:panelGroup>
                        </h:panelGrid>

                        <h:panelGrid columns="4" columnClasses="col2,col7,col2,col7" styleClass="row2" id="gridPanel15" width="100%">
                            <h:outputText id="stxtOther6" styleClass="output"/>
                            <h:outputText id="stxtOther5" styleClass="output" value="#{MiscParameters.code5}" style="color:blue;"/>
                            <h:outputText id="stxtOther7" styleClass="output"/>
                            <h:outputText id="stxtOther2" styleClass="output" value="#{MiscParameters.code4}" style="color:blue;"/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnPost" tabindex="7" value="Save" action="#{MiscParameters.createSaveOrUpdate}" focus="btnRefresh"
                                               reRender="txtCharges1,stxtError,txtCharges,gridPanel2,gridPanel15,lblOther,ddMisc"/>
                            <a4j:commandButton id="btnRefresh" tabindex="8" value="Refresh" action="#{MiscParameters.refresh}" focus="ddPurpose"
                                               reRender="txtCharges1,stxtError,txtCharges,gridPanel2,gridPanel15,lblOther,ddMisc" />
                            <a4j:commandButton id="btnExit" tabindex="9" value="Exit" action="#{MiscParameters.exitBtnAction}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>