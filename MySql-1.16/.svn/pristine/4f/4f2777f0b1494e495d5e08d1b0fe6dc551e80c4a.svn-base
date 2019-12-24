<%-- 
    Document   : cashDepositAggregate
    Created on : Jan 29, 2014, 12:48:45 PM
    Author     : Athar Reza
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Cash Deposit Aggregate Any One day Report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                var row;
                function setMask() {
                    jQuery(".calInstDate").mask("39/19/9999");
                }
            </script>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <h:form id="transactionaggregate">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{CashDepositAggregateAnyOneDay.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Cash Deposit Aggregate Any One day"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{CashDepositAggregateAnyOneDay.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{CashDepositAggregateAnyOneDay.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel1" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row2" width="100%">
                        <h:outputLabel id="lblBranchWise" styleClass="label" value="Branch : "><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddBranch" styleClass="ddlist" size="1" style="width:70px;" value="#{CashDepositAggregateAnyOneDay.branchOption}">
                                <f:selectItems value="#{CashDepositAggregateAnyOneDay.branchOptionList}"/>
                            </h:selectOneListbox>

                        <h:outputText value="Option" styleClass="label" />
                        <h:selectOneListbox id="ddlOption" size="1" styleClass="ddlist" value="#{CashDepositAggregateAnyOneDay.option}"style="width:70px" >
                            <f:selectItems value="#{CashDepositAggregateAnyOneDay.optionList}"  />
                        </h:selectOneListbox>
                    </h:panelGrid>

                    <h:panelGrid id="panel3" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row1" width="100%">
                        <h:outputLabel value="Account Nature" styleClass="label"/>
                        <h:selectOneListbox id="ddNature" value="#{CashDepositAggregateAnyOneDay.acNature}" styleClass="ddlist" size="1" style="width:70px">
                            <f:selectItems value="#{CashDepositAggregateAnyOneDay.acNatureList}"/>
                            <a4j:support actionListener="#{CashDepositAggregateAnyOneDay.getAcTypeForNature}" event="onchange" reRender="ddlAccountType,ddlreportType" oncomplete="setMask();"/>
                        </h:selectOneListbox>

                        <h:outputText value="Account Type" styleClass="label" />
                        <h:selectOneListbox id="ddlAccountType" size="1" styleClass="ddlist" value="#{CashDepositAggregateAnyOneDay.accType}" style="width:70px">
                            <f:selectItems value="#{CashDepositAggregateAnyOneDay.acctTypeList}"  />
                        </h:selectOneListbox> 
                    </h:panelGrid>

                    <h:panelGrid id="panel2" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row2" width="100%">
                        <h:outputText value="Report Type" styleClass="label" />
                        <h:selectOneListbox id="ddlreportType" size="1" styleClass="ddlist" value="#{CashDepositAggregateAnyOneDay.reportType}" style="width:70px" disabled="#{CashDepositAggregateAnyOneDay.disableRepType}">
                            <f:selectItem itemLabel="Cash" itemValue="0" />     
                        </h:selectOneListbox>
                        <h:outputText value="Amount" styleClass="label" />
                        <h:inputText id="txtAmount" value="#{CashDepositAggregateAnyOneDay.amt}" styleClass="input"style="width:70px"/>
                    </h:panelGrid> 

                    <h:panelGrid id="panel4" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row1" width="100%">
                        <h:outputText value="From Date" styleClass="label"/>
                        <h:panelGroup id="groupPanelapptDt" layout="block">
                            <h:inputText id="calfrm" styleClass="input calInstDate"   style="width:70px;setMask()" maxlength="10" value="#{CashDepositAggregateAnyOneDay.frmDt}" >
                                <f:convertDateTime pattern="dd/MM/yyyy" />
                            </h:inputText>
                            <h:outputLabel id="lblapptDT" styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                        </h:panelGroup>
                        <h:outputText value="To Date" styleClass="label"/>
                        <h:panelGroup id="groupPanelapptDt1" layout="block">
                            <h:inputText id="calto" styleClass="input calInstDate"   style="width:70px;setMask()" maxlength="10" value="#{CashDepositAggregateAnyOneDay.toDt}" >
                                <f:convertDateTime pattern="dd/MM/yyyy" />
                            </h:inputText>
                            <h:outputLabel id="lblapptDT1" styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                        </h:panelGroup>                        
                    </h:panelGrid>

                    <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">  
                        <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton  value="Print Report" action="#{CashDepositAggregateAnyOneDay.viewReport()}"  
                                                oncomplete="setMask();"
                                                reRender="errorMsg,groupPanelapptDt,groupPanelapptDt1"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{CashDepositAggregateAnyOneDay.btnPdfAction}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <h:commandButton id="btnPrint" value="Download Excel" actionListener="#{CashDepositAggregateAnyOneDay.printAction}"/>                    
                            <a4j:commandButton value="Reset" action="#{CashDepositAggregateAnyOneDay.refresh()}" oncomplete="setMask();" reRender="a1,groupPanelapptDt,groupPanelapptDt1,errorMsg,calfrm,calto,ddlreportType,ddlAccountType,txtAmount"/>
                            <a4j:commandButton   value="Exit" action="case1"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                    <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <f:facet name="header">
                            <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                        </f:facet>
                    </rich:modalPanel>
                </h:panelGrid>
            </h:form>
        </body>
    </html>
</f:view>
