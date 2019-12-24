<%-- 
    Document   : overdueemi
    Created on : Dec 21, 2011, 10:44:35 AM
    Author     : Sudhir
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
            <title>Loan over due EMI Report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />

            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                var row;
                function setMask() {
                    jQuery(".calInstDate").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel0" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date:"/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{LoanOverdueEMI.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblAccountMaintenanceRegister" styleClass="headerLabel" value="Over Of Loans(EMI Based)"/>
                        <h:panelGroup id="a4" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User:"/>
                            <h:outputText styleClass="output" id="stxtUser" value="#{LoanOverdueEMI.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorMsgReporting" style="display:none;text-align:center" styleClass="row1" width="100%">
                        <h:outputText id="errorGrid" styleClass="error" value="#{LoanOverdueEMI.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="6" columnClasses="col2,col17,col17,col2,col2,col2" id="mainPanel1" width="100%" styleClass="row1" style="text-align:center">
                        <h:outputLabel/>
                        <h:outputLabel value="Branch" styleClass="label"/>
                        <h:selectOneListbox id="branchddl" size="1" value="#{LoanOverdueEMI.branch}" styleClass="ddlist">
                            <f:selectItems value="#{LoanOverdueEMI.branchList}" />
                        </h:selectOneListbox>
                        <h:outputLabel id="reportType" styleClass="headerLabel" value="Sector  wise  report  for  overdue  of  loan"/>
                        <h:selectOneListbox id="reportTypeList"  style="width:280px;" styleClass="ddlist" size="1" value="#{LoanOverdueEMI.sectorWise}"> 
                            <f:selectItems value="#{LoanOverdueEMI.sectorWiseList}"/>
                            <a4j:support event="onchange" action="#{LoanOverdueEMI.onchangeSectorWise()}" reRender="errorMsgReporting,sectorList,groupByList,acCodeList,fromText,toText"/>
                        </h:selectOneListbox>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col17,col17,col2,col2,col2" columns="6" id="mainPanel11" width="100%" styleClass="row2" style="text-align:center">
                        <h:outputLabel/> 
                        <h:outputLabel id="sectorType" styleClass="headerLabel" value="Sector"/>
                        <h:selectOneListbox id="sectorList" disabled="#{LoanOverdueEMI.disableSector}" style="width:280px;" styleClass="ddlist" size="1" value="#{LoanOverdueEMI.sector}"> 
                            <f:selectItems value="#{LoanOverdueEMI.sectorList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="statusId" styleClass="headerLabel" value="Status"/>
                        <h:selectOneListbox id="statusIdList" style="width:80px;" styleClass="ddlist" size="1" value="#{LoanOverdueEMI.status}"> 
                            <f:selectItems value="#{LoanOverdueEMI.statusList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel/> 
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="mainPanel3" width="100%" styleClass="row1" style="text-align:center">
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="bodyPanel3" width="100%" style="text-align:center">
                            <h:outputLabel/>
                            <h:outputLabel/>
                            <h:outputLabel id="groupBy" styleClass="label" value="Group by"/>
                            <h:selectOneListbox id="groupByList" disabled="#{LoanOverdueEMI.disableGroupBy}"  style="width:120px;" styleClass="ddlist" size="1" value="#{LoanOverdueEMI.groupBy}"> 
                                <f:selectItems value="#{LoanOverdueEMI.groupByList}"/>
                                <a4j:support event="onchange" action="#{LoanOverdueEMI.onChangeGroupType()}" reRender="acCodeList"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="bodyPanel4" width="100%" style="text-align:center">
                            <h:outputLabel/>
                            <h:outputLabel id="acCode" styleClass="label" value="A/C Code" style="padding-right:20px"/>
                            <h:selectOneListbox id="acCodeList" disabled="#{LoanOverdueEMI.disableAccountCode }"  style="width:120px;" styleClass="ddlist" size="1" value="#{LoanOverdueEMI.acCode}"> 
                                <f:selectItems value="#{LoanOverdueEMI.acCodeList}"/>
                            </h:selectOneListbox>
                            <h:outputLabel/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="mainpanel6" width="100%" styleClass="row2" style="text-align:center">
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="bodyPanel6" width="100%" style="text-align:center">
                            <h:outputLabel/>
                            <h:outputLabel/>
                            <h:outputLabel id="asOnDate" styleClass="label" value="As on Date" style="padding-right:10px"/>
                            <h:inputText id="date" styleClass="input calInstDate" style="width:70px;" value="#{LoanOverdueEMI.asOnDate}">
                                <f:convertDateTime pattern="dd/MM/yyyy"/>
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="bodyPanel5" width="100%" style="text-align:center">
                            <h:panelGroup>
                                <h:selectBooleanCheckbox id="checkBox" value="#{LoanOverdueEMI.checkBox}">
                                    <a4j:support event="onclick" action="#{LoanOverdueEMI.onClickcheckBox()}" reRender="errorMsgReporting,errorGrid,fromText,toText"/>
                                </h:selectBooleanCheckbox>
                                <h:outputLabel id="lblTODate" styleClass="label" value="EMI Pending"/>
                            </h:panelGroup>
                            <h:panelGroup>
                                <h:outputLabel id="fromlabel"  styleClass="label" value="From"/>
                                <h:inputText id="fromText" disabled="#{LoanOverdueEMI.disableFromText}" maxlength="8" styleClass="input" value="#{LoanOverdueEMI.fromText}"/>
                            </h:panelGroup>
                            <h:panelGroup>
                                <h:outputLabel styleClass="label" id="toLabel" value="To"/>
                                <h:inputText id="toText" disabled="#{LoanOverdueEMI.disableTotext}"  maxlength="8" styleClass="input" value="#{LoanOverdueEMI.toText}"/>    
                            </h:panelGroup>
                            <h:outputLabel/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columns="5" id="gridpanel6" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="a1" layout="block">
                            <a4j:commandButton id="btnReport" value="Print Report" action="#{LoanOverdueEMI.printReport()}" 
                                               oncomplete="if(#{LoanOverdueEMI.flag=='false'}) {#{rich:element('errorMsgReporting')}.style.display='';} 
                                               else {#{rich:element('errorMsgReporting')}.style.display=none;}" reRender="errorMsgReporting"/>
                            <a4j:commandButton id="viewPDF" value="View PDF" action="#{LoanOverdueEMI.printPDF()}" 
                                               oncomplete="if(#{LoanOverdueEMI.flag=='false'}) {#{rich:element('errorMsgReporting')}.style.display='';} 
                                               else {#{rich:element('errorMsgReporting')}.style.display=none;}" reRender="errorMsgReporting"/>
                            <h:commandButton id="btnExcel" value="Download Excel" actionListener="#{LoanOverdueEMI.printExcelReport()}"/>
                            <a4j:commandButton id="exit" value="Exit" action="#{LoanOverdueEMI.exitFrm()}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:status  onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
