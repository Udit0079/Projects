<%-- 
    Document   : LoanInsurancePosting
    Created on : Jun 10, 2016, 3:30:04 PM
    Author     : Admin
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
            <title>Loan Insurance Posting</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".calTillDate").mask("39/19/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="loanInsurancePosting">
                <h:panelGrid bgcolor="#fff" columns="1" id="outerPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{loanInsurancePosting.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Loan Insurance Posting"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{loanInsurancePosting.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                        <h:panelGrid columnClasses="col2,col7" columns="2" id="msgRow" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputLabel id="lblMsg" styleClass="error" value="#{loanInsurancePosting.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row2" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel value="Branch" styleClass="label"/>
                            <h:selectOneListbox id="branchddl" size="1" value="#{loanInsurancePosting.branch}" styleClass="ddlist">
                                <f:selectItems value="#{loanInsurancePosting.branchList}" />
                                <a4j:support event="onblur"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblAcctType" styleClass="label" value="Account Desc : "><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddAcctType" styleClass="ddlist" size="1" value="#{loanInsurancePosting.acctType}">
                                <f:selectItems value="#{loanInsurancePosting.acctTypeList}"/>
                                <a4j:support event="onblur"/>
                            </h:selectOneListbox>                                                        
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row4" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblTillDate" styleClass="label" value = "As On Date :"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="calTillDate" styleClass="input calTillDate" style="width:70px;" maxlength="8" value="#{loanInsurancePosting.tillDate}">
                                <f:convertDateTime pattern="dd/MM/yyyy" />
                                <a4j:support event="onblur"/>
                            </h:inputText>
                            <h:outputLabel/>
                            <h:outputLabel/>
                        </h:panelGrid>    
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row5" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblDebitAmt" styleClass="label" value = "Debit Account :"/>
                            <h:outputText id="stxtDebitAmt" styleClass="output" value="#{loanInsurancePosting.debitAcc}"/>
                            <h:outputLabel id="lblTotalDebit" styleClass="label" value="Total Debit :"/>
                            <h:outputText id="stxtTotalDebit" styleClass="output" value="#{loanInsurancePosting.totalDebit}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row6" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblCreditAmt" styleClass="label" value = "Credit Account :" />
                            <h:outputText id="stxtCreditAmt" styleClass="output" value="#{loanInsurancePosting.creditAcc}"/>
                            <h:outputLabel id="lblTotalCredit" styleClass="label" value="Total Credit :" />
                            <h:outputText id="stxtTotalCredit" styleClass="output" value="#{loanInsurancePosting.totalCredit}"/>
                        </h:panelGrid>                        
                    </h:panelGrid>
                    <h:panelGrid id="poFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:region id="btnPnl">
                                <a4j:commandButton id="btnCalculate" action="#{loanInsurancePosting.calculationData}" value="Calculate" disabled="#{loanInsurancePosting.disabCalc}" oncomplete="if(#{loanInsurancePosting.calcFlag==true}){
                                               #{rich:component('popUpRepPanel')}.show();
                                               }
                                               else if(#{loanInsurancePosting.calcFlag==false})
                                               {
                                               #{rich:component('popUpRepPanel')}.hide();
                                               }" reRender="lblMsg,popUpRepPanel,btnPost,btnCalculate,stxtDebitAmt,stxtTotalDebit,stxtCreditAmt,stxtTotalCredit,calTillDate"/>
                                <a4j:commandButton id="btnPost" value="Post" disabled="#{loanInsurancePosting.disablePost}" action="#{loanInsurancePosting.postAction}"
                                        reRender="lblMsg,btnPost,stxtDebitAmt,stxtTotalDebit,stxtCreditAmt,stxtTotalCredit,calTillDate,btnCalculate"/>
                                <a4j:commandButton id="btnRefresh" value="Refresh" action="#{loanInsurancePosting.refresh}" oncomplete="setMask();" reRender="lblMsg,btnPost,stxtDebitAmt,stxtTotalDebit,stxtCreditAmt,stxtTotalCredit,calTillDate,btnCalculate,branchddl,ddAcctType"/>
                                <a4j:commandButton id="btnExit" value="Exit" action="#{loanInsurancePosting.exitBtnAction}"/>
                            </a4j:region>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>                
            </a4j:form>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="btnPnl"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;" >
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>                 
            <rich:modalPanel id="popUpRepPanel" label="Form" width="1000" height="550" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpRepPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Fidelity Adjustment Report" style="text-align:center;"/>
                    </h:panelGrid>
                </f:facet>
                <f:facet name="controls">
                    <h:outputLink  id="closelink" onclick="#{rich:component('popUpRepPanel')}.hide()">
                        <h:graphicImage value="/resources/images/close.gif" style="border:0" />
                    </h:outputLink>
                </f:facet>
                <table width="100%">
                    <tbody>
                        <tr>
                            <td>
                                <a4j:include viewId="#{loanInsurancePosting.viewID}"/>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
