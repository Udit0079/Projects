<%-- 
    Document   : searchreport
    Created on : 19 Dec, 2011, 10:24:49 AM
    Author     : Zeeshan Waris
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Search Report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".calInstDate").mask("39/19/9999");
                }

            </script>
        </head>
        <body>
            <a4j:form id="SearchReport">
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{SearchReport.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="Search Report"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUsername" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="otUsername" styleClass="output" value="#{SearchReport.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="errPanel" style="text-align:center" styleClass="row1">
                        <h:outputText id="errmsg" value="#{SearchReport.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel1" columns="6" columnClasses="col3,col3,col3,col3,col3,col3"  styleClass="row2" width="100%">   
                        <h:outputText value="Branch" styleClass="label"/>
                        <h:selectOneListbox id="listbranch" value="#{SearchReport.branch}" styleClass="ddlist" size="1"  style="width:80px">
                            <f:selectItems value="#{SearchReport.branchList}" />
                             <a4j:support  action="#{SearchReport.getAccountypeList}" event="onblur" reRender="checkboxEnterBy,lblEnter,ddEnterBy,checkboxAuthBy,lblAuthBy,ddAuthBy" />
                        </h:selectOneListbox> 
                        <h:outputText/>
                        <h:outputText/>
                        <h:outputText/>
                        <h:outputText/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel3"  styleClass="row2"> 
                        <h:panelGroup>
                            <h:selectBooleanCheckbox id="checkboxEnterBy" value="#{SearchReport.chenterBy}">
                                <a4j:support event="onchange" actionListener="#{SearchReport.chkEnterbyprocessValueChange}"  
                                             oncomplete="#{rich:element('calfromDate')}.style=setMask();
                                             #{rich:element('calToDate')}.style=setMask();" reRender="ddEnterBy,calfromDate,calToDate"/>
                            </h:selectBooleanCheckbox>
                            <h:outputLabel id="lblEnter" styleClass="label"  value="Enter By"/>
                        </h:panelGroup>
                        <h:selectOneListbox id="ddEnterBy" styleClass="ddlist" size="1" style="width: 134px" value="#{SearchReport.ddEnterBy}"  disabled="#{SearchReport.chenterByDisable}">
                            <f:selectItems value="#{SearchReport.enterByList}"/>
                        </h:selectOneListbox>
                        <h:panelGroup>
                            <h:selectBooleanCheckbox id="checkboxAuthBy" value="#{SearchReport.chAuthBy}">
                                <a4j:support event="onchange" actionListener="#{SearchReport.chkAuthbyprocessValueChange}"  oncomplete="#{rich:element('calfromDate')}.style=setMask();
                                             #{rich:element('calToDate')}.style=setMask();" reRender="ddAuthBy,calfromDate,calToDate"/>
                            </h:selectBooleanCheckbox>
                            <h:outputLabel id="lblAuthBy" styleClass="label"  value="Auth By"/>
                        </h:panelGroup>
                        <h:selectOneListbox id="ddAuthBy" styleClass="ddlist" size="1" style="width: 134px" value="#{SearchReport.ddAuthby}" disabled="#{SearchReport.chAuthByDisable}">
                            <f:selectItems value="#{SearchReport.enterByList}"/>
                        </h:selectOneListbox>
                        <h:panelGroup>
                            <h:selectBooleanCheckbox id="checkboxTransaction" value="#{SearchReport.chTransaction}">
                                <a4j:support event="onchange" actionListener="#{SearchReport.chkTransactionprocessValueChange}" oncomplete="#{rich:element('calfromDate')}.style=setMask();
                                             #{rich:element('calToDate')}.style=setMask();" reRender="ddTransaction,calfromDate,calToDate"/>
                            </h:selectBooleanCheckbox>
                            <h:outputLabel id="lblTransaction" styleClass="label"  value="Transaction"/>
                        </h:panelGroup>
                        <h:selectOneListbox id="ddTransaction" styleClass="ddlist" size="1" style="width: 134px" value="#{SearchReport.ddTransaction}" disabled="#{SearchReport.chTransactionDisable}">
                            <f:selectItems value="#{SearchReport.transactionList}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel4"  styleClass="row2"> 
                        <h:panelGroup>
                            <h:selectBooleanCheckbox id="checkboxType" value="#{SearchReport.chType}">
                                <a4j:support event="onchange" actionListener="#{SearchReport.chkTypeprocessValueChange}" oncomplete="#{rich:element('calfromDate')}.style=setMask();
                                             #{rich:element('calToDate')}.style=setMask();" reRender="ddType,calfromDate,calToDate"/>
                            </h:selectBooleanCheckbox>
                            <h:outputLabel id="lblType" styleClass="label"  value="Type"/>
                        </h:panelGroup>
                        <h:selectOneListbox id="ddType" styleClass="ddlist" size="1" style="width: 134px" value="#{SearchReport.ddType}" disabled="#{SearchReport.chTypeDisable}">
                            <f:selectItems value="#{SearchReport.typeList}"/>
                        </h:selectOneListbox>
                        <h:panelGroup>
                            <h:selectBooleanCheckbox id="checkboxInstNo" value="#{SearchReport.chInstNo}">
                                <a4j:support event="onchange" actionListener="#{SearchReport.chkInstNoprocessValueChange}"  oncomplete="#{rich:element('calfromDate')}.style=setMask();
                                             #{rich:element('calToDate')}.style=setMask();" reRender="txtInstNo,calfromDate,calToDate"/>
                            </h:selectBooleanCheckbox>
                            <h:outputLabel id="lblInstNo" styleClass="label"  value="Inst No."/>
                        </h:panelGroup>
                        <h:inputText id="txtInstNo"  style="width: 134px" value="#{SearchReport.instNo}" styleClass="input" maxlength="12"  onkeyup="this.value = this.value.toUpperCase();" disabled="#{SearchReport.chInstNoDisable}"/>
                        <h:outputLabel id="lblFromAmount" styleClass="label"  value="From Amount"/>
                        <h:inputText id="txtFromAmount"  style="width: 134px" value="#{SearchReport.fromAmt}" styleClass="input" maxlength="12"  onkeyup="this.value = this.value.toUpperCase();" />
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel5"  styleClass="row1"> 
                        <h:outputLabel id="lblToAmount" styleClass="label"  value="To Amount"/>
                        <h:inputText id="txtToAmount"  style="width: 134px" value="#{SearchReport.toAmt}" styleClass="input" maxlength="12"  onkeyup="this.value = this.value.toUpperCase();"/>
                        <h:outputLabel id="lblFromDate" value="From Date" styleClass="label"/>
                        <%--  <rich:calendar datePattern="dd/MM/yyyy" value="#{SearchReport.calFromDate}" styleClass="rich-calendar-input" inputSize="10" /> --%>
                        <h:inputText id="calfromDate" size="10" styleClass="input calInstDate" style="setMask();" value="#{SearchReport.calFromDate}" >
                            <f:convertDateTime pattern="dd/MM/yyyy" />
                        </h:inputText>
                        <h:outputLabel id="lbltoDate"  value="To Date"  styleClass="label"/>
                        <%--<rich:calendar datePattern="dd/MM/yyyy" value="#{SearchReport.caltoDate}" styleClass="rich-calendar-input" inputSize="10" /> --%>
                        <h:inputText id="calToDate" size="10" styleClass="input calInstDate" style="setMask();" value="#{SearchReport.caltoDate}" >
                            <f:convertDateTime pattern="dd/MM/yyyy" />
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid id="FooterPanel" bgcolor="#fff" style="text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnPrint" value="Print Report" action="#{SearchReport.printAction}" oncomplete="#{rich:element('calfromDate')}.style=setMask();
                                               #{rich:element('calToDate')}.style=setMask();" reRender="errmsg,calfromDate,calToDate"/>
                             <h:commandButton id="btnDownload"  value="PDF Download" action="#{SearchReport.btnPdfAction}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{SearchReport.refreshAction}"  oncomplete="#{rich:element('calfromDate')}.style=setMask();
                                               #{rich:element('calToDate')}.style=setMask();" reRender="PanelGridMain,calfromDate,calToDate"/>
                            <a4j:commandButton action="#{SearchReport.exitAction}" value="Exit" reRender="errmsg"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>


