<%--
    Document   : LoanBudgetMaster
    Created on : Jul 20, 2011, 2:53:10 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Loan Budget Master</title>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                 var row;
             </script>
        </head>
        <body>
            <a4j:form id="LoanBudgetMaster">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">

                    <h:panelGrid columns="3" id="headerpanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stDate" styleClass="output" value="#{LoanBudgetMaster.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="OL30" styleClass="headerLabel" value="Loan Budget Master"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{LoanBudgetMaster.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid  id="errPanel"   width="100%" style="height:1px;text-align:center;" styleClass="row1">
                        <h:outputText id="errMsg" value="#{LoanBudgetMaster.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanelFunc" style="text-align:left;" width="100%" styleClass="row2">
                      <h:outputLabel styleClass="label" style="padding-left:80px" value="Operation"><font class="required" style="color:red;">*</font></h:outputLabel>  
                      <h:selectOneListbox size="1" id="operationList"  styleClass="ddlist" value="#{LoanBudgetMaster.operation}">
                          <f:selectItems value="#{LoanBudgetMaster.operationList}"/>
                         <a4j:support event="onblur" action="#{LoanBudgetMaster.setOperation}" reRender="mainPanel"/>
                        </h:selectOneListbox>
                      <h:outputText style="padding-left:80px"/>
                      <h:outputText style="padding-left:80px"/>
                      </h:panelGrid>

                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel1" style="text-align:left;" width="100%" styleClass="row1">
                        <h:outputLabel styleClass="label" style="padding-left:80px" value="Loan Budget "><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="lblLoanBudget" maxlength="8" styleClass="input" value="#{LoanBudgetMaster.loanAmountString}" disabled="#{LoanBudgetMaster.disableLoanBudget}"/>
                        <h:outputLabel styleClass="label" style="padding-left:80px" value="Loan Available"/>
                        <h:inputText id="lblLoanAvailable" maxlength="8" styleClass="input" disabled="true" value="#{LoanBudgetMaster.loanAvailableString}"/>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel2" style="text-align:left;" width="100%" styleClass="row2">
                        <h:outputLabel styleClass="label" style="padding-left:80px" value="Principle Collection"/>
                        <h:inputText id="lblPrincipleCollection" maxlength="8" styleClass="input" disabled="true" value="#{LoanBudgetMaster.principleCollectionString}"/>
                        <h:outputLabel/><h:outputLabel/>
                    </h:panelGrid>


                    <h:panelGrid id="footerPanel4" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="save" value="Save" action="#{LoanBudgetMaster.saveAction}" disabled="#{LoanBudgetMaster.disableSaveButton}" reRender="mainPanel"/>
                            <a4j:commandButton id="delete" value="Delete" action="#{LoanBudgetMaster.deleteAction}" disabled="#{LoanBudgetMaster.disableDeleteButton}" reRender="mainPanel"/>
                            <a4j:commandButton id="cancel" value="Cancel" action="#{LoanBudgetMaster.cancelAction}" type="reset" reRender="mainPanel"/>
                            <a4j:commandButton id="exit" value="Exit" action="#{LoanBudgetMaster.exitAction}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
               
            </a4j:form>
        </body>
    </html>
</f:view>
