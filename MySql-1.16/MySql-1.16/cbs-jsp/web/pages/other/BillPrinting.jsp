<%-- 
    Document   : BillPrinting
    Created on : Sep 22, 2011, 11:57:37 AM
    Author     : root
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
            <title>Bill Printing</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".txtIssueDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="billprinting">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="grpPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{BillPrinting.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="PO/DD Printing"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{BillPrinting.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="msgGrid" columns="1" width="100%" styleClass="row2" style="width:100%;text-align:center;">
                        <h:outputText id="msg" styleClass="error" value="#{BillPrinting.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="inputPanel" columns="6" columnClasses="col3,col3,col3,col3,col3,col1" styleClass="row1" style="width:100%;text-align:left;">
                        <h:outputLabel id="lblBillType" styleClass="label" value="Bill Type"/>
                        <h:selectOneListbox id="ddBillType" styleClass="ddlist"  size="1" style="width:80px" value="#{BillPrinting.billType}">
                            <f:selectItems value="#{BillPrinting.billTypeList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblBillNo"styleClass="label" value="Instrument Number/Bill Number"/>
                        <h:inputText id="txtNo" styleClass="input" style="width:80px" value="#{BillPrinting.instNo}" maxlength="6"/>
                        <h:outputLabel id="lblIssueDt" styleClass="label" value="Issue Date"/>
                        <h:inputText id="txtIssueDt" size="10" styleClass="input txtIssueDt" value="#{BillPrinting.issueDt}">
                            <a4j:support event="onblur" action="#{BillPrinting.getBillDetails}" oncomplete="setMask();" reRender="msg,stxtSeq,stxtCustName,stxtAmount,stxtInfavourOf,stxtPayableAt,ddBillType,txtNo,txtIssueDt,print,view"/>
                        </h:inputText>
                    </h:panelGrid>
                    <rich:panel id="poddDetails" header="PO/DD Details">
                        <h:panelGrid id="poddGridoOne" columns="6" columnClasses="col3,col3,col3,col3,col3,col1" styleClass="row2" style="width:100%;text-align:left;">
                            <h:outputLabel id="lblSeq" styleClass="label" value="Seq No"/>
                            <h:outputText id="stxtSeq" styleClass="output" value="#{BillPrinting.seqNo}"/>
                            <h:outputLabel id="lblCustName" styleClass="label" value="CustName"/>
                            <h:outputText id="stxtCustName" styleClass="output" value="#{BillPrinting.custName}"/>
                            <h:outputLabel id="lblAmount" styleClass="label" value="Amount"/>
                            <h:outputText id="stxtAmount" styleClass="output" value="#{BillPrinting.amount}"/>
                        </h:panelGrid>
                        <h:panelGrid id="poddGridoTwo" columns="6" columnClasses="col3,col3,col3,col3,col3,col1" styleClass="row1" style="width:100%;text-align:left;">
                            <h:outputLabel id="lblInfavourOf" styleClass="label" value="In Favour Of"/>
                            <h:outputText id="stxtInfavourOf" styleClass="output" value="#{BillPrinting.inFavourOf}"/>
                            <h:outputLabel id="lblPayableAt" styleClass="label" value="Payable At"/>
                            <h:outputText id="stxtPayableAt" styleClass="output" value="#{BillPrinting.payableAt}"/>
                            <h:outputLabel/>
                            <h:outputLabel/>
                        </h:panelGrid>
                    </rich:panel>
                    <h:panelGrid id="btnPanel" columnClasses="col3,col3,col3,col3,col3,col1" columns="6" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnGroupPanel">
                            <a4j:commandButton id="print" value="Print" action="#{BillPrinting.billToPrint}" reRender="mainPanel,msg" disabled="#{BillPrinting.printFlag}"/>
                            <a4j:commandButton id="refresh" value="Refresh" action="#{BillPrinting.refreshForm}" oncomplete="setMask();" reRender="msg,stxtSeq,stxtCustName,stxtAmount,stxtInfavourOf,stxtPayableAt,ddBillType,txtNo,txtIssueDt,print"/>
                            <a4j:commandButton id="exit" value="Exit" action="#{BillPrinting.exitForm}" reRender="msg,stxtSeq,stxtCustName,stxtAmount,stxtInfavourOf,stxtPayableAt,ddBillType,txtNo,txtIssueDt,print"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
