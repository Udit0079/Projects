<%-- 
    Document   : loanPeriod
    Created on : Dec 9, 2011, 3:26:26 PM
    Author     : admin
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
            <title>Loan Period Report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".calInstDate").mask("39/19/9999");
                }

            </script>
        </head>
        <body>
            <h:form id="loanPeriod">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{loanPeriod.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Loan Period Report"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{loanPeriod.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{loanPeriod.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel11" columns="4" columnClasses="col2,col7,col2,col7" styleClass="row2" width="100%">
                        <h:outputLabel value="Branch" styleClass="label"/>
                        <h:selectOneListbox id="branchddl" size="1" value="#{loanPeriod.branch}" styleClass="ddlist">
                            <f:selectItems value="#{loanPeriod.branchList}" />
                        </h:selectOneListbox>
                        <h:outputText />
                        <h:outputText />                        
                    </h:panelGrid>     
                    <h:panelGrid id="panel1" columns="4" columnClasses="col2,col7,col2,col7" styleClass="row2" width="100%">
                        <h:outputText value="Report Type" styleClass="label"/>
                        <h:selectOneListbox id="reportTypeddl" size="1" value="#{loanPeriod.reportType}" styleClass="ddlist">
                            <f:selectItems value="#{loanPeriod.reportTypeList}"/>
                            <a4j:support event="onblur" actionListener="#{loanPeriod.reportBased}"  reRender="a1" focus="#{loanPeriod.focusAbleId}" />
                        </h:selectOneListbox>
                        <h:outputText value="Sanction/Roi Based" styleClass="label" />
                        <h:selectOneListbox id="roibasedddl" size="1" value="#{loanPeriod.basedReport}" styleClass="ddlist" disabled="#{loanPeriod.roisenboolean}">
                            <f:selectItem itemLabel="ROI BASED" itemValue="0" />
                            <f:selectItem itemLabel="SANCTION BASED" itemValue="1" />
                            <a4j:support event="onblur" actionListener="#{loanPeriod.sencionedBased}"  reRender="a1" focus="#{loanPeriod.focusAbleRoiId}" />
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="panel2" columns="4" columnClasses="col2,col7,col2,col7" styleClass="row1"  width="100%"  rendered="#{loanPeriod.firstPanel}" >
                        <h:outputLabel value="From roi" styleClass="label"/>
                        <h:panelGroup>
                            <h:inputText id="txtfrom" value="#{loanPeriod.roifrm}" required="true" requiredMessage="Please enter From Roi">
                                <a4j:support  ajaxSingle="true" event="onblur"/>
                            </h:inputText>
                            <rich:message for="txtfrom" styleClass="error"/>
                        </h:panelGroup>
                        <h:outputLabel value="To roi" styleClass="label"/>
                        <h:panelGroup>
                            <h:inputText id="txtto" value="#{loanPeriod.roito}" required="true" requiredMessage="Please enter To Roi">
                                <a4j:support  ajaxSingle="true" event="onblur" />
                            </h:inputText>
                            <rich:message for="txtto" styleClass="error"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="panel3" columns="4" columnClasses="col2,col7,col2,col7" styleClass="row1" width="100%" rendered="#{loanPeriod.secondPanel}">
                        <h:outputLabel value="Sanction From" styleClass="label"/>
                        <h:panelGroup>
                            <h:inputText id="txtsanfrom" value="#{loanPeriod.senfrm}" required="true" requiredMessage="Please enter Sanction From"><f:convertNumber pattern="#.##" />
                                <a4j:support  ajaxSingle="true" event="onblur"  reRender="errorMsg"/>
                            </h:inputText>
                            <rich:message for="txtsanfrom" styleClass="error"/>
                        </h:panelGroup>
                        <h:outputLabel value="Sanction To" styleClass="label"/>
                        <h:panelGroup>
                            <h:inputText id="txtsansto" value="#{loanPeriod.sento}" required="true" requiredMessage="Please enter Sanction To"> <f:convertNumber pattern="#.##" /> 
                                <a4j:support  ajaxSingle="true" event="onblur" />
                            </h:inputText>
                            <rich:message for="txtsansto" styleClass="error"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columns="4" columnClasses="col2,col7,col2,col7" styleClass="row2" width="100%">
                        <h:outputLabel id="lblAcType" value="A/C Type" styleClass="label"/>
                        <h:selectOneListbox id="ddlAccctype" size="1" value="#{loanPeriod.acType}" styleClass="ddlist">
                            <f:selectItems value="#{loanPeriod.acctypeList}" />
                            <a4j:support event="onblur" action="#{loanPeriod.blurAcctype}" oncomplete="#{rich:element('calFromDate')}.style=setMask();#{rich:element('calToDate')}.style=setMask()" reRender="ddlSchemeType" />
                        </h:selectOneListbox>
                        <h:outputLabel id="lblGroupBy" value="Term #" styleClass="label"/>
                        <h:selectOneListbox id="ddlmode" size="1" value="#{loanPeriod.term}" styleClass="ddlist" disabled="#{loanPeriod.termBoolean}" >
                            <f:selectItem itemLabel="ALL" itemValue="3"/>
                            <f:selectItem itemLabel="SHORT TERM" itemValue="0" />
                            <f:selectItem itemLabel="MEDIUM TERM" itemValue="1" />
                            <f:selectItem itemLabel="LONG TERM" itemValue="2" />
                            <a4j:support  ajaxSingle="true" event="onblur"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanelscheme"  styleClass="row1" width="100%">
                        <h:outputLabel id="lblAcScehem" value="Select Secheme" styleClass="label"/>
                        <h:selectOneListbox id="ddlSchemeType" size="1" styleClass="ddlist" value="#{loanPeriod.schemetype}" >
                            <f:selectItems value="#{loanPeriod.schemeList}" />
                            <a4j:support event="onblur" ajaxSingle="true" />
                        </h:selectOneListbox>
                        <h:outputLabel id="lblLoanPeriod" value="Loan Period" styleClass="label"/>
                        <h:selectOneListbox id="ddlLoanPeriod" size="1" styleClass="ddlist" value="#{loanPeriod.loanPeriod}" >
                            <f:selectItems value="#{loanPeriod.loanPeriodList}" />
                            <a4j:support event="onblur" ajaxSingle="true" />
                        </h:selectOneListbox> 
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanelfromDate"  styleClass="row2" width="100%" >
                        <h:outputLabel value="(A/c Opening) From date" styleClass="label"/>
                        <h:inputText id="calFromDate" size="10" styleClass="input calInstDate" style="setMask();" value="#{loanPeriod.frmDt}">
                            <f:convertDateTime pattern="dd/MM/yyyy" />
                            <a4j:support event="onblur" ajaxSingle="true" />
                        </h:inputText>
                        <h:outputLabel value="To date" styleClass="label"/>
                        <h:inputText id="calToDate" size="10" styleClass="input calInstDate" style="setMask();" value="#{loanPeriod.toDt}">
                            <f:convertDateTime pattern="dd/MM/yyyy" />
                            <a4j:support  ajaxSingle="true" event="onblur"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanelIntfromDate"  styleClass="row1" width="100%" >
                        <h:outputLabel value="(Interest) From date" styleClass="label"/>
                        <h:inputText id="calIntFromDate" size="10" styleClass="input calInstDate" style="setMask();" value="#{loanPeriod.frmDtTran}">
                            <f:convertDateTime pattern="dd/MM/yyyy" />
                            <a4j:support event="onblur" ajaxSingle="true" />
                        </h:inputText>
                        <h:outputLabel value="To date" styleClass="label"/>
                        <h:inputText id="calIntToDate" size="10" styleClass="input calInstDate" style="setMask();" value="#{loanPeriod.toDtTran}">
                            <f:convertDateTime pattern="dd/MM/yyyy" />
                            <a4j:support  ajaxSingle="true" event="onblur"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                        <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                            <a4j:region id="a4jRegion">
                                <a4j:commandButton  value="Print Report" action="#{loanPeriod.viewReport}" reRender="errorMsg,calFromDate,calToDate,a1" oncomplete="#{rich:element('calFromDate')}.style=setMask();#{rich:element('calToDate')}.style=setMask()"/>
                                <h:commandButton id="btnDownload"  value="PDF Download" action="#{loanPeriod.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                                <a4j:commandButton value="Refresh" actionListener="#{loanPeriod.refresh()}" reRender="errorMsg,calFromDate,calToDate,a1" oncomplete="#{rich:element('calFromDate')}.style=setMask();#{rich:element('calToDate')}.style=setMask()"/>
                                <a4j:commandButton   value="Exit" action="#{loanPeriod.exitAction}" reRender="errorMsg,calFromDate,calToDate,a1" oncomplete="#{rich:element('calFromDate')}.style=setMask();#{rich:element('calToDate')}.style=setMask()"/>
                            </a4j:region>
                        </h:panelGroup>
                    </h:panelGrid>
                    <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="a4jRegion"/>
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
