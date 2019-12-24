<%-- 
    Document   : loanPrincipalInterestDetail
    Created on : Mar 20, 2015, 6:39:02 PM
    Author     : Admin
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
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".calFromDate").mask("99/99/9999");
                    jQuery(".calToDate").mask("99/99/9999");
                }
            </script>
            <title>Loan Recovery Detail</title>
        </head>
        <body>
            <a4j:form id="LoanSanctionDetailDuringthePeriod">
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{LoanPrincipalInterestDetail.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="Loan Recovery Detail"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUsername" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="otUsername" styleClass="output" value="#{LoanPrincipalInterestDetail.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="errPanel" style="text-align:center" styleClass="row1">
                        <h:outputText id="errmsg" value="#{LoanPrincipalInterestDetail.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col15,col15,col15,col15,col15,col15,col15,col1" columns="8" id="gridPanel4"  styleClass="row2"> 
                        <h:outputText value="Branch Wise" styleClass="label"/>
                        <h:selectOneListbox id="branch" size="1" value="#{LoanPrincipalInterestDetail.branchCode}" styleClass="ddlist">
                            <f:selectItems value="#{LoanPrincipalInterestDetail.branchCodeList}" />
                        </h:selectOneListbox>  
                        <h:outputLabel id="lblacType" styleClass="label"  value="A/c Type"/>
                        <h:selectOneListbox id="ddacType" styleClass="ddlist" size="1" style="width: 70px" value="#{LoanPrincipalInterestDetail.acType}" >
                            <f:selectItems value="#{LoanPrincipalInterestDetail.acTypeList}"/>
                        </h:selectOneListbox>
                        
                        <h:outputLabel id="lblFromDate" value="From Date" styleClass="label"/>
                        <h:inputText id="calFromDate" size="10" styleClass="input calFromDate" style="setMask();" value="#{LoanPrincipalInterestDetail.calFromDate}">
                            <f:convertDateTime pattern="dd/MM/yyyy" />
                        </h:inputText>
                        
                        <h:outputLabel id="lblToDate" value="To Date" styleClass="label"/>
                        <h:inputText id="calToDate" size="10" styleClass="input calToDate" style="setMask();" value="#{LoanPrincipalInterestDetail.calToDate}">
                            <f:convertDateTime pattern="dd/MM/yyyy" />
                        </h:inputText>
                        
                    </h:panelGrid>
                    <h:panelGrid id="FooterPanel" bgcolor="#fff" style="text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnPrint" value="Print Report" action="#{LoanPrincipalInterestDetail.printAction}" reRender="errmsg"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{LoanPrincipalInterestDetail.refreshAction}" reRender="errmsg,txtAccountNo"/>
                            <a4j:commandButton action="#{LoanPrincipalInterestDetail.exitAction}" value="Exit" reRender="errmsg"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
