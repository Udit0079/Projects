<%-- 
    Document   : invoicenoGenerateMaster
    Created on : 23 Aug, 2018, 12:49:42 PM
    Author     : root
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
            <title>Invoice No Generate Master</title>
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
            <a4j:form id="kycCateg">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{InvoicenoGenerateMaster.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Invoice No Generate Master"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{InvoicenoGenerateMaster.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="errPanel" style="text-align:center" styleClass="row1">
                        <h:outputText id="errmsg" value="#{InvoicenoGenerateMaster.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel1" columns="8" columnClasses="col15,col15,col15,col15,col15,col15,col15,col1" styleClass="row2" width="100%">
                        
                        <h:outputLabel id="optionLbl" styleClass="label" value="Option"/>
                        <h:selectOneListbox id="option" size="1" styleClass="ddlist" value="#{InvoicenoGenerateMaster.option}" style="width:150" >
                            <f:selectItems value="#{InvoicenoGenerateMaster.optionList}"/>
                            <a4j:support action="#{InvoicenoGenerateMaster.actionOnOption}" event="onblur" reRender="acNoId,finYearId,errmsg"/>
                        </h:selectOneListbox>
                        
                        
                        <h:outputText id="stxtAcno" value="Account No" styleClass="label"/>
                        <h:panelGroup id="acNoPanel"> 
                        <h:inputText id="acNoId" styleClass="input toDate" style="width:80px;"  value="#{InvoicenoGenerateMaster.acNo}" disabled = "#{InvoicenoGenerateMaster.acnoDisable}" >
                            <a4j:support action="#{InvoicenoGenerateMaster.getAccountDetails}" event="onblur" reRender="custNameIdid,finYearId,acNoIdb,errmsg" />
                        </h:inputText>
                            <h:outputText id="acNoIdb"  styleClass="output" value="#{InvoicenoGenerateMaster.newAcno}"style ="color:green" />
                        </h:panelGroup>
                        
                        <h:outputLabel id="custNameId" styleClass="label" value="Customer Name"/>
                        <h:outputText id="custNameIdid"  styleClass="output" value="#{InvoicenoGenerateMaster.customerName}" style ="color:blue"/>
                        <h:outputLabel id="finYear" styleClass="label" value="Financial Year"/>
                        <h:outputText id="finYearId"  styleClass="output" value="#{InvoicenoGenerateMaster.finYear}"style ="color:blue" />
                        
                    </h:panelGrid>
                    <h:panelGrid id="FooterPanel" bgcolor="#fff" style="text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton action="#{InvoicenoGenerateMaster.GenerateInvoiceNoProcess}" id="btnGenId" value="Generate Invoice No" reRender="mainPanel,message"/>
                            <a4j:commandButton action="#{InvoicenoGenerateMaster.btnRefreshAction}" id="btnReset" value="Refresh" reRender="mainPanel,message"/>
                            <a4j:commandButton action="#{InvoicenoGenerateMaster.btnExitAction}" value="Exit" reRender="errmsg"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>

