<%-- 
    Document   : loanAcDetailStatement
    Created on : Dec 23, 2013, 7:00:13 PM
    Author     : Athar Reza
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
            <link rel="stylesheet" href="/cbs-jsp/resources/txn_style.css" type="text/css"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Loan Account Detail Statement</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".frDt").mask("99/99/9999");
                    jQuery(".toDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="kycCateg">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{LoanAcDetailStatement.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Loan Account Detail Statement"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{LoanAcDetailStatement.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row2" style="height:30px;text-align:center;" width="100%">
                        <h:outputLabel id="message" styleClass="error" style="color:red;" value="#{LoanAcDetailStatement.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel2" columns="4"  columnClasses="col2,col7,col2,col7"  width="100%" styleClass="row1">
                        <h:outputLabel id="lblAllAccount" styleClass="label" />
                        <h:selectOneRadio id="ddAllAccount" style="width:140px;" styleClass="label" value="#{LoanAcDetailStatement.allAccount}">
                            <f:selectItems value="#{LoanAcDetailStatement.allAccountList}"/>
                            <a4j:support  action="#{LoanAcDetailStatement.getdisableAcctType()}" event="onchange"
                                          reRender="a9,gridPanel2,gridPanel3,ddAllAccount,frDt" focus="ddAccountType"/>   
                        </h:selectOneRadio>          
                        <h:outputLabel id="lblAccountType" styleClass="label" value="Account Type :"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddAccountType" styleClass="ddlist" size="1" style="width: 50px" disabled="#{LoanAcDetailStatement.disableAcctType}" value="#{LoanAcDetailStatement.accountType}">
                            <f:selectItems value="#{LoanAcDetailStatement.accountTypeList}"/>
                            <a4j:support  event="onblur"reRender="a9,ddAccountWise,lblAccountType,toDt" focus="toDt"/>
                        </h:selectOneListbox>   
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row2" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputLabel id="lblAccountWise" styleClass="label" />
                        <h:selectOneRadio id="ddAccountWise" style="width:140px;" styleClass="label" value="#{LoanAcDetailStatement.accountWise}">
                            <f:selectItems value="#{LoanAcDetailStatement.accountWiseList}"/>
                            <a4j:support  action="#{LoanAcDetailStatement.disableAcctTypeNo}" event="onchange"
                                          reRender="a9,Row2,ddAccountWise,ddAccountType,ddAllAccount" focus="txtAccountNo"/>   
                        </h:selectOneRadio>
                        <h:outputLabel id="lblAccountNo" styleClass="label" value="Account No. :"><font class="required" color="red">*</font></h:outputLabel>
                        <h:panelGroup layout="block">
                            <h:inputText id="txtAccountNo" styleClass="input" maxlength="12" value="#{LoanAcDetailStatement.oldAccNo}" disabled="#{LoanAcDetailStatement.disableAcctNo}" onkeyup="this.value=this.value.toUpperCase();" size="12">
                                <a4j:support  action="#{LoanAcDetailStatement.getdisableAcctTypeNo}" event="onblur"
                                              reRender="a9,stxtAccNo,txtAccountNo" focus="frDt"/>
                            </h:inputText>
                            <h:outputText id="stxtAccNo" styleClass="output" value="#{LoanAcDetailStatement.accountNo}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel3" columns="4"  columnClasses="col2,col7,col2,col7"  width="100%" styleClass="row1">
                        <h:outputLabel value="From Date" styleClass="label"/>
                        <h:inputText id="frDt" styleClass="input frDt" maxlength="10" value="#{LoanAcDetailStatement.frmDt}" disabled="#{LoanAcDetailStatement.disableFrmDt}"style="width:70px;setMask();"/>
                        <a4j:support event="onchange"/>
                        <h:outputLabel value="To Date" styleClass="label"/>
                        <h:inputText id="toDt" styleClass="input toDt" maxlength="10" value="#{LoanAcDetailStatement.toDt}" style="width:70px;setMask();"/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnPrint" value="Print Report" action="#{LoanAcDetailStatement.viewReport}" reRender="message"/>
                             <h:commandButton id="btnDownload"  value="PDF Download" action="#{LoanAcDetailStatement.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{LoanAcDetailStatement.btnRefreshAction}"  reRender="mainPanel" oncomplete="setMask();"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{LoanAcDetailStatement.btnExitAction}"  reRender="mainPanel" oncomplete="setMask();"/>
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
