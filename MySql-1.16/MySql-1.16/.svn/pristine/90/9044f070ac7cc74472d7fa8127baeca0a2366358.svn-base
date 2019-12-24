<%-- 
    Document   : cashdepositaggregate
    Created on : Dec 12, 2011, 6:17:04 PM
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
            <title>Deposit And Withdrawal aggregate report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />

            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                var row;
                function setMask() {
                    jQuery(".fromdate").mask("99/99/9999");
                    jQuery(".toDate").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel0" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date:"/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{CashDepositAggregate.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblAccountMaintenanceRegister" styleClass="headerLabel" value="Deposit And Withdrawal Aggregate Report"/>
                        <h:panelGroup id="a4" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User:"/>
                            <h:outputText styleClass="output" id="stxtUser" value="#{CashDepositAggregate.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorMsgReporting" style="text-align:center" styleClass="row2" width="100%">
                        <h:outputText id="errorGrid" styleClass="error" value="#{CashDepositAggregate.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col7,col3,col7,col3,col7" columns="3" id="mainPanel0" width="100%" styleClass="row1" style="text-align:center"> 
                        <h:panelGrid columnClasses="col3,col7,col3,col7" columns="2" id="bodyPanel0" width="100%" style="text-align:center">
                            <h:outputLabel id="lblBranchWise" styleClass="label" value="Branch"></h:outputLabel>
                            <h:selectOneListbox id="ddBranch" styleClass="ddlist" size="1" style="width:100px;" value="#{CashDepositAggregate.branchOption}">
                                <f:selectItems value="#{CashDepositAggregate.branchOptionList}"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col7,col3,col7" columns="2" id="bodyPanel12" width="100%" style="text-align:center">
                            <h:outputLabel id="lblRrepType" styleClass="label" value="Report Type"></h:outputLabel>
                            <h:selectOneListbox id="ddRepType" styleClass="ddlist" size="1" style="width:100px;" value="#{CashDepositAggregate.repTypeOption}">
                                <f:selectItems value="#{CashDepositAggregate.repTypeOptionList}"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col7,col3,col7,col3,col7" columns="3" id="mainPanel1" width="100%" styleClass="row1" style="text-align:center">                        
                        <h:panelGrid columnClasses="col3,col7,col3,col7" columns="2" id="bodyPanel11" width="100%" style="text-align:center">
                            <h:outputLabel id="lblAcNatType" styleClass="label" value="Account Nature"></h:outputLabel>
                            <h:selectOneListbox id="ddAcNat" styleClass="ddlist" size="1" style="width:100px;" value="#{CashDepositAggregate.acNatureOption}">
                                <f:selectItems value="#{CashDepositAggregate.acNatureOptionList}"/>
                                <a4j:support id="idnature"action="#{CashDepositAggregate.getAcTypeForNature}" event="onblur" reRender="accType,accTypeList"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col7,col3,col7" columns="2" id="bodyPanel1" width="100%" style="text-align:center">
                            <h:outputLabel id="accType" styleClass="label" value="Account Type"/>
                            <h:selectOneListbox id="accTypeList"  style="width:100px;" styleClass="ddlist" size="1" value="#{CashDepositAggregate.accountType}"> 
                                <f:selectItems value="#{CashDepositAggregate.accountTypeList}"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>                   
                    <h:panelGrid columnClasses="col3,col7,col3,col7,col3,col7" columns="3" id="mainpanel4" width="100%" styleClass="row1" style="text-align:center">
                        <h:panelGrid columnClasses="col3,col7,col3,col7" columns="2" id="bodyPanel3" width="100%" style="text-align:center">
                            <h:outputLabel id="tranType" styleClass="label" value="Type" style="padding-right:20px"/>
                            <h:selectOneListbox id="tranTypeList"  style="width:100px;" styleClass="ddlist" size="1" value="#{CashDepositAggregate.transactionType}"> 
                                <f:selectItems value="#{CashDepositAggregate.transactionTypeList}"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col7,col3,col7" columns="2" id="bodyPanel4" width="100%" style="text-align:center">
                            <h:outputLabel id="tranOpt" styleClass="label" value="Option" style="padding-right:20px"/>
                            <h:selectOneListbox id="tranOptList"  style="width:100px;" styleClass="ddlist" size="1" value="#{CashDepositAggregate.trOptType}"> 
                                <f:selectItems value="#{CashDepositAggregate.trOptList}"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col7,col3,col7,col3,col7" columns="3" id="mainpanel5" width="100%" styleClass="row1" style="text-align:center">
                        <h:panelGrid columnClasses="col3,col7,col3,col7" columns="2" id="bodyPanel5" width="100%" style="text-align:center">                            
                            <h:outputLabel id="lblfromDate" styleClass="label" value="From Date" style="padding-right:15px"/>
                            <h:inputText id="fromdate" styleClass="input fromdate" value="#{CashDepositAggregate.fromDate}" size="8">
                                <f:convertDateTime pattern="dd/MM/yyyy"/>
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col7,col3,col7" columns="2" id="mainpanel6" width="100%" style="text-align:center">
                            <h:outputLabel id="lblTODate" styleClass="label" value="To Date" style="padding-right:15px"/>
                            <h:inputText id="toDate" styleClass="input toDate" value="#{CashDepositAggregate.toDate}" size="8">
                                <f:convertDateTime pattern="dd/MM/yyyy" />
                            </h:inputText>  
                        </h:panelGrid>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col7,col3,col7,col3,col7" columns="3" id="mainPanel2" width="100%" styleClass="row2" style="text-align:center">
                        <h:panelGrid columnClasses="col3,col7,col3,col7" columns="2" id="bodyPanel2" width="100%" style="text-align:center">
                            <h:outputLabel id="amt" styleClass="label" value="Amount Type"/>
                            <h:selectOneListbox id="amtTypeList"  style="width:100px;" styleClass="ddlist" size="1" value="#{CashDepositAggregate.amountType}"> 
                                <f:selectItems value="#{CashDepositAggregate.amountList}"/>
                                <a4j:support action="#{CashDepositAggregate.amountListValueChng}" event="onblur" reRender="framt,framtText,toamt,toamtText,tranTypeList" focus="framtText" oncomplete="setMask();"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                         <h:panelGrid columnClasses="col3,col7,col3,col7" columns="2" id="bodyPanel6" width="100%" style="text-align:center">
                             <h:outputLabel id="framt" styleClass="label" value="#{CashDepositAggregate.displayfromDate}" style ="display:#{CashDepositAggregate.flag
                                            }" />
                             <h:inputText id="framtText" styleClass="input" value="#{CashDepositAggregate.fromAmt}" maxlength="15" style="width:100px"/>
                         </h:panelGrid>
                         <h:panelGrid columnClasses="col3,col7,col3,col7" columns="2" id="bodyPanel7" width="100%" style="text-align:center">
                             <h:outputLabel id="toamt" styleClass="label" value="To Amount" style="display:#{CashDepositAggregate.invisible}"/>
                             <h:inputText id="toamtText" styleClass="input" value="#{CashDepositAggregate.toAmt}" maxlength="15" style="display:#{CashDepositAggregate.invisible}"/>
                         </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columns="5" id="gridpanel6" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="a1" layout="block">
                            <a4j:commandButton id="btnReport" value="Print Report" action="#{CashDepositAggregate.printReport()}" 
                                               oncomplete="if(#{CashDepositAggregate.flag=='false'}) {#{rich:element('errorMsgReporting')}.style.display='';} 
                                               else {#{rich:element('errorMsgReporting')}.style.display=none;}" reRender="errorMsgReporting"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{CashDepositAggregate.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{CashDepositAggregate.refresh}" reRender="mainPanel" oncomplete="setMask()"/>
                            <a4j:commandButton id="exit" value="Exit" action="#{CashDepositAggregate.exitFrm()}"/>
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
