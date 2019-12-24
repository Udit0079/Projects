<%-- 
    Document   : atmTransactionStatus
    Created on : Aug 30, 2013, 12:45:15 PM
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
            <title> ATM Transaction Status Report</title>
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
            <h:form id="AtmTransaction">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">

                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{AtmTransactionStatus.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="ATM Transaction Status Report"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{AtmTransactionStatus.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{AtmTransactionStatus.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="mode" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputText id="lblStatus1N" value="Mode" styleClass="label"/>
                        <h:selectOneListbox id="ddStatus11N" value="#{AtmTransactionStatus.mode}" styleClass="ddlist" style="width:100px" size="1"  >
                            <f:selectItems id="selectStatus1N1" value="#{AtmTransactionStatus.modeList}"/>
                            <a4j:support event="onblur" action="#{AtmTransactionStatus.optionsMode}" 
                                         oncomplete="if(#{rich:element('ddStatus11N')}.value=='All'){#{rich:element('ddacType111')}.focus();}
                                             else if((#{rich:element('ddStatus11N')}.value=='AC')){#{rich:element('aaccontno')}.focus();}" reRender="errorMsg,ddacType111,lblStatus1NN,aaccontno,reportId" />
                        </h:selectOneListbox>
                
                        <h:outputText id="lblStatus1NN" value="Account No" styleClass="label" />
                        <h:inputText id="aaccontno"  value="#{AtmTransactionStatus.accountNo}" style="width:80px"  maxlength="12"  disabled="#{AtmTransactionStatus.disbleAccountnNo}" />
                        <a4j:support event="onblur"  focus="ddacType111"/>    
                        
                        <h:outputText id="lblacType" value="Type" styleClass="label"/>
                        <h:selectOneListbox id="ddacType111" value="#{AtmTransactionStatus.type}" styleClass="ddlist"  style="width:100px" size="1">
                            <f:selectItems id="selectRepTypeList11" value="#{AtmTransactionStatus.typeList}" />
                            <a4j:support event="onblur" action="#{AtmTransactionStatus.options}" reRender="errorMsg,ddacType112,reportId" focus="ddacType112"/> 
                        </h:selectOneListbox> 
                    </h:panelGrid>
                    <h:panelGrid id="panel1" columns="6" columnClasses="col3,col3,col3,col3,col3,col3"  styleClass="row2" width="100%">
                        <h:outputText id="lblacType2" value="Transaction" styleClass="label"/>
                        <h:selectOneListbox id="ddacType112" value="#{AtmTransactionStatus.transaction}" styleClass="ddlist"  style="width:100px" size="1">
                            <f:selectItems id="selectRepTypeList12" value="#{AtmTransactionStatus.transactionList}" />
                        </h:selectOneListbox>
                        <h:outputText value="Atm Branch " styleClass="label"/>
                        <h:selectOneListbox id="branchID" size="1" value="#{AtmTransactionStatus.branchCode}" styleClass="ddlist">
                            <f:selectItems value="#{AtmTransactionStatus.branchCodeList}" />
                            <a4j:support action="#{AtmTransactionStatus.getAtmIdVal}" event="onblur" reRender="reportId" focus="reportId"/> 
                        </h:selectOneListbox> 
                        <h:outputText value="Atm Id:" styleClass="label"/>
                        <h:selectOneListbox id="reportId" size="1" value="#{AtmTransactionStatus.atmId}" styleClass="ddlist" style="width:90px" disabled="#{AtmTransactionStatus.disbleAtmId}">
                            <f:selectItems value="#{AtmTransactionStatus.atmIdList}" />
                        </h:selectOneListbox> 
                    </h:panelGrid>
                    <h:panelGrid id="panel2" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputText value="From Date" styleClass="label"/>

                        <h:inputText id="txtFrmDate"   styleClass="input calInstDate" style="setMask();width:70px;"  value="#{AtmTransactionStatus.frmDt}"/>

                        <h:outputText value="To Date" styleClass="label"/>

                        <h:inputText id="txtToDate"   styleClass="input calInstDate" style="setMask();width:70px;"  value="#{AtmTransactionStatus.toDt}"/>

                        <h:outputText id="lblStatus1" value="Status" styleClass="label"/>
                        <h:selectOneListbox id="ddStatus11" value="#{AtmTransactionStatus.status}" styleClass="ddlist"  style="width:100px" size="1">
                            <f:selectItems id="selectStatus11" value="#{AtmTransactionStatus.statusList}" />
                        </h:selectOneListbox>
                    </h:panelGrid>

                    <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                        <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnPrint" value="Print Report" action="#{AtmTransactionStatus.viewReport}" reRender="errorMsg"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{AtmTransactionStatus.btnPdfAction}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{AtmTransactionStatus.refresh}" reRender="a1,errorMsg,ddStatus11N,selectRepTypeList11,ddacType112,selectRepTypeList12" oncomplete="setMask()"/>
                            <a4j:commandButton id="btnClose" value="Exit" action="#{AtmTransactionStatus.exitAction}" reRender="errorMsg"/>
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
