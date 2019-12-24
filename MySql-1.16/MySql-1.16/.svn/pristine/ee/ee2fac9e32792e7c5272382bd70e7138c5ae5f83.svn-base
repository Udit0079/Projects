<%-- 
    Document   : guaranteeOnLoan
    Created on : Feb 9, 2017, 6:33:35 PM
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <title>Guarantor Overdue(Disbursement)</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />

            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                var row;
                function setMask() {
                    jQuery(".calFromDate").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="gauratorId">
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{GauranteeOnLoan.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="Guarantor Overdue (Disbursement)"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUsername" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="otUsername" styleClass="output" value="#{GauranteeOnLoan.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="errPanel" style="text-align:center" styleClass="row1">
                        <h:outputText id="errmsg" value="#{GauranteeOnLoan.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel4" style="text-align:center;" styleClass="row2">
                        <h:outputText id="lblacType" value="Branch Code" styleClass="label"/>
                        <h:selectOneListbox id="ddacType111" value="#{GauranteeOnLoan.brCode}" styleClass="ddlist"  style="width:100px" size="1">
                            <f:selectItems id="selectRepTypeList11" value="#{GauranteeOnLoan.branchCodeList}" />
                        </h:selectOneListbox> 
                        <h:outputLabel  value="As On Date" styleClass="label"/>
                        <h:inputText id="calFromDate" size="10" styleClass="input calFromDate" style="setMask();" value="#{GauranteeOnLoan.calDate}" >
                            <f:convertDateTime pattern="dd/MM/yyyy" />
                        </h:inputText>
                        <h:outputText id="lblReportType" value="Report Type" styleClass="label"/>
                        <h:selectOneListbox id="ddReportType" value="#{GauranteeOnLoan.reportType}" styleClass="ddlist"  style="width:100px" size="1">
                            <f:selectItems id="selectReportType" value="#{GauranteeOnLoan.reportTypeList}" />
                            <a4j:support event="onblur" action="#{GauranteeOnLoan.onReportAction}" reRender="gridPanel5"></a4j:support>
                        </h:selectOneListbox> 
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel5" style="text-align:center;display:#{GauranteeOnLoan.visible}" styleClass="row2">
                        <h:outputText value="Loan A/c No." styleClass="label"/>
                        <h:panelGroup id="folionoId">
                            <h:inputText id="txtacno" value="#{GauranteeOnLoan.acNo}" styleClass="input" size="12" maxlength="12" onkeyup="this.value=this.value.toUpperCase();">
                                <a4j:support event="onblur" actionListener="#{GauranteeOnLoan.acnoOnBulur}" reRender="newAcno,errmsg"/>
                            </h:inputText>
                            <h:outputText id="newAcno" value="#{GauranteeOnLoan.newAcNo}" styleClass="output" style="color:green"/>
                        </h:panelGroup>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="FooterPanel" bgcolor="#fff" style="text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnPrint" action="#{GauranteeOnLoan.printAction}" type="submit" value="Print Report" reRender="PanelGridMain"/> 
                            <h:commandButton id="btnPDF"  value="PDF Download" actionListener="#{GauranteeOnLoan.viewPdfReport}"  styleClass="color: #044F93;text-decoration: none;" /> 
                            <a4j:commandButton id="reset" value="Refresh" actionListener="#{GauranteeOnLoan.refreshPage}" reRender="errmsg,PanelGridMain"/>
                            <a4j:commandButton id="btnExit" action="#{GauranteeOnLoan.exitAction}" value="Exit" reRender="errmsg"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>