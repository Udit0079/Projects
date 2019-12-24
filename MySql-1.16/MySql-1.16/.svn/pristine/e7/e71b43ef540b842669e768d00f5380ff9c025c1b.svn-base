

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Projected Interest Calculation</title>
            <script type="text/javascript">

            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{ProjectedInterestCalculation.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Projected Interest Calculation"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ProjectedInterestCalculation.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="errorMessage" styleClass="error" value="#{ProjectedInterestCalculation.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{ProjectedInterestCalculation.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="4" id="a3" width="100%" style="height:30px;" styleClass="row1">
                        <h:panelGroup layout="block">
                        <h:outputLabel id="brnName" styleClass="label" value="Branch Name"/>
                        <h:selectOneListbox size="1" id="brnList" styleClass="ddBrnList"value="#{ProjectedInterestCalculation.brnCode}" tabindex="1">
                            <f:selectItems value="#{ProjectedInterestCalculation.brnList}"/>
                        </h:selectOneListbox>
                        </h:panelGroup>
                        <h:panelGroup layout="block">
                        <h:outputLabel id="lblAcctType" styleClass="label" value="Account Type :"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddAcctType" tabindex="2" styleClass="ddlist" value="#{ProjectedInterestCalculation.acctType}" size="1">
                            <f:selectItems value="#{ProjectedInterestCalculation.acctTypeList}"/>
                            <a4j:support action="#{ProjectedInterestCalculation.setdescription}" event="onblur" reRender="message,errorMessage" 
                                         limitToList="true" focus="ddInterestOption" />
                        </h:selectOneListbox>
                        </h:panelGroup>
                        <h:panelGroup layout="block">
                        <h:outputLabel id="lblInterestOption" styleClass="label" value="Interest Option :"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddInterestOption" tabindex="3" styleClass="ddlist" value="#{ProjectedInterestCalculation.intOpt}" size="1">
                            <f:selectItems value="#{ProjectedInterestCalculation.intOptionList}"/>
                        </h:selectOneListbox>
                        </h:panelGroup>
                        <h:panelGroup layout="block">
                        <h:outputLabel id="lblDt" styleClass="label" value="Till Date :"><font class="required" color="red">*</font></h:outputLabel>
                        <rich:calendar datePattern="dd/MM/yyyy" id="calDate" value="#{ProjectedInterestCalculation.tillDate}" inputSize="10" tabindex="4"/>
                        </h:panelGroup>
                    </h:panelGrid>
                        
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnCalculation" tabindex="5" value="Calculate" action="#{ProjectedInterestCalculation.calculation}" reRender="message,errorMessage,taskList,a19" focus="btnRefresh"/>
                            <a4j:commandButton id="btnRefresh" tabindex="6" value="Refresh" action="#{ProjectedInterestCalculation.resetForm}" reRender="a3,a4,message,errorMessage,taskList,a19" focus="ddAcctType"/>
                            <a4j:commandButton id="btnExit" tabindex="7" value="Exit" action="#{ProjectedInterestCalculation.exitFrm}" reRender="message,errorMessage" />
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
                <rich:messages></rich:messages>
            </a4j:form>
        </body>
    </html>
</f:view>
