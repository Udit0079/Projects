<%-- 
    Document   : gstReport
    Created on : 25 Sep, 2017, 1:01:10 PM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE html>

<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Gstr Report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".issueDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="GstrReport">  
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{GstReport.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="GSTR REPORT"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{GstReport.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row2" style="height:30px;text-align:center;" width="100%">
                        <h:outputLabel id="lblMsg" styleClass="error" style="color:red;" value="#{GstReport.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel1" columns="6" columnClasses="col3,col3,col3,col3,col3,col1"  width="100%" styleClass="row1">
                        <h:outputLabel value="Branch" styleClass="label"/>
                        <h:selectOneListbox id="branchddl" size="1" value="#{GstReport.branch}" styleClass="ddlist" style="width:80px">
                            <f:selectItems value="#{GstReport.branchList}" />
                        </h:selectOneListbox>
                        <h:outputLabel id="reportLbl" styleClass="label" value="Report"/>
                        <h:selectOneListbox id="report" size="1" styleClass="ddlist" value="#{GstReport.report}"  style="width:150" >
                            <f:selectItems value="#{GstReport.reportList}"/>
                        </h:selectOneListbox>                    
                        <h:outputLabel id="optionLbl" styleClass="label" value="Option"/>
                        <h:selectOneListbox id="option" size="1" styleClass="ddlist" value="#{GstReport.option}" style="width:150" >
                            <f:selectItems value="#{GstReport.optionList}"/>
                            <a4j:support action="#{GstReport.actionOnOption}" event="onblur" reRender="acnoIt,print,btn,yearIt,gridPanel3"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel2" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" width="100%" styleClass="row1">
                        <h:outputLabel id="monthLbl" styleClass="label" value="Month"/>
                        <h:selectOneListbox id="months" size="1" styleClass="ddlist" value="#{GstReport.months}" >
                            <f:selectItems value="#{GstReport.monthsList}"/> 
                            <a4j:support  event="onblur"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="year" value="Year" styleClass="label"/>    
                        <h:inputText id="yearIt" value="#{GstReport.year}" styleClass="input" style="width:70px" maxlength="4" validatorMessage="Please enter 4 digits."><f:validateLength minimum="4" maximum="4" />                           
                            <a4j:support action="#{GstReport.actionOnYear}" event="onblur" reRender="btnGenId"/>
                        </h:inputText> 
                        <h:outputLabel id="acnoId" value="Account No." styleClass="label"/>    
                        <h:inputText id="acnoIt" value="#{GstReport.acNo}" styleClass="input" style="width:100px" maxlength="12" disabled="#{GstReport.disableAcno}" validatorMessage="Please enter 12 digits."><f:validateLength minimum="12" maximum="12" />                           
                            <a4j:support  event="onblur"/>
                        </h:inputText> 
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel3" columns="6" style="display:#{GstReport.gstPanelShow}" columnClasses="col3,col3,col3,col3,col3,col3" width="100%" styleClass="row1">
                     <h:outputLabel id="gstId" value="Gst No." styleClass="label"/>    
                        <h:inputText id="gstIdfr" value="#{GstReport.gstNo}" styleClass="input" style="width:100px" maxlength="15"  validatorMessage="Please enter 15 digits."><f:validateLength minimum="15" maximum="15" />                           
                            <a4j:support  event="onblur"/>
                        </h:inputText> 
                     <h:outputLabel></h:outputLabel>
                     <h:outputText></h:outputText>
                     <h:outputLabel></h:outputLabel>
                     <h:outputText></h:outputText>
                    </h:panelGrid>
                    <h:panelGrid id="panel4" columns="2" columnClasses="col2,col6" styleClass="row1" width="100%">
                        <h:outputLabel styleClass="label" style="width:100px;color:blue" value="Invoice No. Generation Condition-->"/>
                        <h:outputText id="markNote" styleClass="output" style="width:100px;color:blue" value="Invoice No. Generation will allow only after Month End."/>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">  
                            <a4j:region id="saveRegion">
                                <a4j:commandButton id="btnGenId" value="Generate Invoice No" action="#{GstReport.GenerateInvoiceNoProcess}" disabled= "#{GstReport.disableBntGen}" reRender="mainPanel,lblMsg"/>
                                <a4j:commandButton value= "Print Letter" id= "btn" action="#{GstReport.printLetter }" disabled= "#{GstReport.disablebntPdf}" reRender="lblMsg"/>
                                <a4j:commandButton  id="print" value="Download PDF" action="#{GstReport.viewReport}" disabled="#{GstReport.disablebntPdf}" reRender="lblMsg"/>
                                <h:commandButton value="Download Excel" id="btnSave" action="#{GstReport.downloadExcel}"/>
                                <a4j:commandButton value="Refresh" id="refresh" action="#{GstReport.refreshForm}" reRender="mainPanel,lblMsg" oncomplete="setMask()"/>
                                <a4j:commandButton id="btnExit" value="Exit" action="#{GstReport.exitFrom}" reRender="lblMsg"/>
                            </a4j:region>
                        </h:panelGroup>
                    </h:panelGrid> 
                    <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="saveRegion"/>
                    <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <f:facet name="header">
                            <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                        </f:facet>
                    </rich:modalPanel>
                </h:panelGrid>

            </a4j:form>            
        </body>
    </html>
</f:view>