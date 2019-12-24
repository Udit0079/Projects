<%-- 
    Document   : afsToHtmModificaion
    Created on : 29 May, 2017, 11:04:30 AM
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
            <title>Security Mark</title>
        </head>
        <body>
            <a4j:form>
                <h:panelGrid id="maingrid" columns="1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid id="headergrid" columns="3" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{AfsHtmModification.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Security Mark"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{AfsHtmModification.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="msggrid" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7;border:1px ridge #BED6F8;">
                        <h:outputText id="message" styleClass="error" value="#{AfsHtmModification.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="amortPanel" columns="4" columnClasses="col13,col13,col13,col13" style="height:30px;border:1px ridge #BED6F8;"  styleClass="row1" width="120%">
                        <h:outputLabel id="secType" styleClass="label" value="Security Type"/>
                        <h:selectOneListbox id="secTypeDD" styleClass="ddlist" size="1" style="width:170px"value="#{AfsHtmModification.secType}">
                            <f:selectItems value="#{AfsHtmModification.secTypeList}"/>
                            <a4j:support action="#{AfsHtmModification.secTypeMethod}" event="onblur" reRender="message,ddCtrlNo"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblCtrlNo" styleClass="label" value="Control No."><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddCtrlNo" style="width:120px" styleClass="ddlist"  value="#{AfsHtmModification.ctrl}" size="1">
                            <f:selectItems value="#{AfsHtmModification.ctrlList}" />
                            <a4j:support action="#{AfsHtmModification.setmark}" event="onblur" reRender="message,newpasswwordaddress,panelSellar,panelSellarValue"/>
                        </h:selectOneListbox>                        
                    </h:panelGrid>
                    <h:panelGrid id="panelSellar" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row2" width="100%">
                        <h:outputLabel id="sellarl9" styleClass="label" value="Security Description"/>
                        <h:outputText id="sellarName"  styleClass="output" value="#{AfsHtmModification.sellarName}" />
                        <h:outputLabel id="manDtl9" styleClass="label" value="Maturity Date"/>
                        <h:outputText id="stxtMandt"  styleClass="output" value="#{AfsHtmModification.matdt}" />
                    </h:panelGrid>
                    <h:panelGrid id="panelSellarValue" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row1" width="100%">
                        <h:outputLabel id="sellarBookl9" styleClass="label" value="Book Value"/>
                        <h:outputText id="bookValue"  styleClass="output" value="#{AfsHtmModification.bookValue}" />
                        <h:outputLabel id="facel9" styleClass="label" value="Face Value"/>
                        <h:outputText id="faceValue"  styleClass="output" value="#{AfsHtmModification.faceValue}" />  
                    </h:panelGrid>
                    <h:panelGrid id="combogrid" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row2" width="100%">
                        <h:outputLabel id="retypeaddress" styleClass="label" value="Marked"   style="width:170px"/>
                        <h:inputText id="newpasswwordaddress" styleClass="input"  value="#{AfsHtmModification.marking}" disabled="true"/>
                        <h:outputLabel id="lblMarking" styleClass="label" value="Marking Type"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddMarking" style="width:120px" styleClass="ddlist" value="#{AfsHtmModification.markingType}" size="1">
                            <f:selectItems value="#{AfsHtmModification.markingTestList}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel" styleClass="vtop">
                            <a4j:commandButton id="btnChange" value="Change" action="#{AfsHtmModification.changeBtnAction}" reRender="maingrid"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{AfsHtmModification.refreshForm}" reRender="maingrid"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{AfsHtmModification.exitBtnAction}" reRender="maingrid"/>
                        </h:panelGroup>
                    </h:panelGrid>
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