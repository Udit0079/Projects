<%-- 
    Document   : loanborrowerusermodifications
    Created on : 26 Nov, 2018, 11:19:42 AM
    Author     : root
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Loan Borrower User Modifications</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
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
            <h:form id="loanPeriod">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{LoanBorrowerUserModifications.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Loan Borrower User  Modification"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{LoanBorrowerUserModifications.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{LoanBorrowerUserModifications.errorMsg}"/>
                    </h:panelGrid> 
                    <h:panelGrid id="panel3" columns="6" columnClasses="col3,col3,col3,col3,col3,col3"  styleClass="row2" width="100%" >
                        <h:outputText value="Report Option"  styleClass="label"/>
                        <h:selectOneListbox id="ddreportOption" value="#{LoanBorrowerUserModifications.repOption}" size="1" styleClass="ddlist" style="width:100px">
                            <f:selectItems value="#{LoanBorrowerUserModifications.repOptionList}"/>
                            <a4j:support event="onblur" action="#{LoanBorrowerUserModifications.blurAcctNature}"  reRender="panel1,acnolbl,rolbl" />
                        </h:selectOneListbox> 
                        <h:outputLabel id="acnolbl" styleClass="label" value="Account No "  style="display:#{LoanBorrowerUserModifications.display1}"></h:outputLabel>
                        <h:inputText id="rolbl" styleClass="input" value="#{LoanBorrowerUserModifications.acno}" size="#{LoanBorrowerUserModifications.acNoMaxLen}" maxlength="#{LoanBorrowerUserModifications.acNoMaxLen}"  style="display:#{LoanBorrowerUserModifications.display1}">                
                        </h:inputText>
                        <h:outputLabel/>
                        <h:outputLabel/>                        
                    </h:panelGrid>                              
                    <h:panelGrid id="panel1" columns="6" columnClasses="col3,col3,col3,col3,col3,col3"  styleClass="row2" width="100%"  style="display:#{LoanBorrowerUserModifications.display}">
                        <h:outputText value="Branch Wise" styleClass="label"/>
                        <h:selectOneListbox id="branch" size="1" value="#{LoanBorrowerUserModifications.branchCode}" styleClass="ddlist">
                            <f:selectItems value="#{LoanBorrowerUserModifications.branchCodeList}" />
                        </h:selectOneListbox> 
                        <h:outputText value="A/c Nature" styleClass="label"/>
                        <h:selectOneListbox id="ddlAcNatureHeader" value="#{LoanBorrowerUserModifications.acNature}" size="1" styleClass="ddlist" style="width:100px">
                            <f:selectItems value="#{LoanBorrowerUserModifications.acNatureList}"/>
                            <a4j:support event="onblur" action="#{LoanBorrowerUserModifications.blurAcctNature}"  reRender="ddlAccTypeHeader" />
                        </h:selectOneListbox>                               
                        <h:outputText value="A/c. Type" styleClass="label"/>
                        <h:selectOneListbox id="ddlAccTypeHeader" value="#{LoanBorrowerUserModifications.acctType}" size="1" styleClass="ddlist" style="width:100px">
                            <f:selectItems value="#{LoanBorrowerUserModifications.acctTypeList}"/>
                            <a4j:support event="onblur"/>
                        </h:selectOneListbox> 
                    </h:panelGrid>     
                    <h:panelGrid id="panel2" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">                        
                    <h:outputLabel value="From Date" styleClass="label"/>
                    <h:inputText id="frDt" styleClass="input calInstDate" size="12"  value="#{LoanBorrowerUserModifications.frmDt}" >
                         <a4j:support event="onblur" oncomplete="setMask()"/>
                    </h:inputText>
                    <h:outputLabel value="To Date" styleClass="label"/>
                    <h:inputText id="toDt"  styleClass="input calInstDate"size="12"  value="#{LoanBorrowerUserModifications.toDt}" >
                         <a4j:support event="onblur" oncomplete="setMask()"/>
                    </h:inputText>
                    <h:outputLabel/>
                    <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                        <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">                          
                            <a4j:commandButton id="print" value="Print Report" action="#{LoanBorrowerUserModifications.printReport()}" reRender="errorMsg,txtFrmDate,txtToDate" oncomplete="setMask()"/>
                            <a4j:commandButton value="Refresh" action="#{LoanBorrowerUserModifications.refresh}" oncomplete="setMask()" reRender="errorMsg,branch,ddlAcNatureHeader,ddlAccTypeHeader,frDt,toDt"/>
                            <a4j:commandButton   value="Exit" action="#{LoanBorrowerUserModifications.exitAction}" oncomplete="setMask()" reRender="txtToDate,a1"/>
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

