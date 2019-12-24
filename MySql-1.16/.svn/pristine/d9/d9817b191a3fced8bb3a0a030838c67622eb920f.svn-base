<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <title>Account Statement</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".calFromDate").mask("99/99/9999");
                    jQuery(".calToDate").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="AccountStatememtWise">
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>

                <h:panelGrid id="PanelGridMain" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="HeaderPanel" columns="3"  style="text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{AccountStatememtWise.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="Account Statement Wise"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUsername" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="otUsername" styleClass="output" value="#{AccountStatememtWise.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid  id="errPanel" style="text-align:center" styleClass="row1">
                        <h:outputText id="errmsg" value="#{AccountStatememtWise.message}" styleClass="error"/>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col1,col3,col3,col3,col3,col3" columns="6" style="text-align:left;" styleClass="row2">
                        <h:outputLabel/>
                        <h:outputLabel value="Account No :" styleClass="label"/>
                        <h:inputText id="txtAccountNo" styleClass="input" style="width:100px" value="#{AccountStatememtWise.accountNo}" onkeyup="this.value = this.value.toUpperCase();" maxlength="12">
                            <a4j:support event="onblur" actionListener="#{AccountStatememtWise.onBlurAccountNumber}" reRender="summaryPanel,errmsg,newAcNo" oncomplete="setMask();" focus="calFromDate"/>
                        </h:inputText>
                        <h:outputText id="newAcNo" value="#{AccountStatememtWise.newAcNo}" styleClass="output" style="color:green"/>
                        <h:outputLabel/>
                        <h:outputLabel/>                                                
                    </h:panelGrid>                                
                    <h:panelGrid columnClasses="col1,col3,col3,col3,col3,col3" columns="6" style="text-align:left;" styleClass="row1">
                        <h:outputLabel/>
                        <h:outputLabel value="From Date :" styleClass="label"/>
                        <h:inputText id="calFromDate" size="10" styleClass="input calFromDate" style="setMask();" value="#{AccountStatememtWise.fromDate}"/>
                        <h:outputLabel value="To Date :" styleClass="label"/>
                        <h:inputText id="calToDate" size="10" styleClass="input calToDate" style="setMask();" value="#{AccountStatememtWise.toDate}"/>
                        <h:outputLabel/>                     
                    </h:panelGrid>
                    <rich:panel id="summaryPanel" header="Last Account Statement Summary" headerClass="rich-header">
                        <h:panelGrid style="width: 100%;">
                            <h:panelGrid columnClasses="col1,col3,col3,col3,col3,col3" columns="6" style="text-align:left;" styleClass="row2">
                                <h:outputLabel/>
                                <h:outputLabel  value="Last Statement Date From" styleClass="label"/>
                                <h:outputText value="#{AccountStatememtWise.lastStmtFromDate}" styleClass="msg"/>
                                <h:outputLabel  value="Last Statement Date To" styleClass="label"/>
                                <h:outputText value="#{AccountStatememtWise.lastStmtToDate}" styleClass="msg"/>
                                <h:outputLabel/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col1,col3,col3,col3,col3,col3" columns="6" style="text-align:left;" styleClass="row1">
                                <h:outputLabel/>
                                <h:outputLabel  value="Last Printed Date" styleClass="label"/>
                                <h:outputText value="#{AccountStatememtWise.lastStmtPrintedDate}" styleClass="msg"/>
                                <h:outputLabel  value="Printed By" styleClass="label"/>
                                <h:outputText value="#{AccountStatememtWise.lastStmtPrintedBy}" styleClass="msg"/>
                                <h:outputLabel/>
                            </h:panelGrid>
                        </h:panelGrid>
                    </rich:panel>

                    <h:panelGrid id="FooterPanel" bgcolor="#fff" style="text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnPrint" action="#{AccountStatememtWise.printAction}" type="submit" value="Print Report" reRender="errmsg">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" action="#{AccountStatememtWise.exitAction}" value="Exit">
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>

                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>