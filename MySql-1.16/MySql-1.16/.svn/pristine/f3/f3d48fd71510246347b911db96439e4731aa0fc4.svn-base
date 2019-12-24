<%-- 
    Document   : bankIinDetail
    Created on : 17 Jan, 2018, 3:53:42 PM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>BANK IIN DETAIL</title>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="Panel1" columns="3"  style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup>
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{BankIinDetail.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblincident" styleClass="headerLabel" value="Bank IIN Detail"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{BankIinDetail.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="messagePanel" columnClasses="col8,col8" columns="2"  style="width:100%;text-align:center;"styleClass="row1" width="100%"> 
                        <h:outputText id="stxtMessage" styleClass="error" value="#{BankIinDetail.message}"/>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col3,col2,col3,col3,col3,col3" columns="6" id="gridPanel2" width="100%" styleClass="row1">                  
                        <h:outputLabel id="lblBnkType" styleClass="label" value="Bank"></h:outputLabel>
                        <h:selectOneListbox id="ddBnkType" style="width:200px" styleClass="ddlist"  value="#{BankIinDetail.bankName}" size="1">
                            <f:selectItems value="#{BankIinDetail.bankList}"/>
                            <a4j:support action="#{BankIinDetail.bankOptionOnblur}" event="onblur" reRender="txtIinNo,txtBankType,txtIfsc,txtMicr"/>
                        </h:selectOneListbox>
                        <h:outputLabel></h:outputLabel>
                        <h:outputLabel></h:outputLabel>
                        <h:outputLabel id="iinNo" styleClass="label" value="IIN "/>
                        <h:outputText id="txtIinNo" styleClass="output" value="#{BankIinDetail.iinNo}"/>
                        <h:outputLabel></h:outputLabel>
                        <h:outputLabel></h:outputLabel>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col2,col3,col3,col3,col3" columns="6" id="Panel5" style="width:100%;" styleClass="row1">
                        <h:outputLabel id="bankType" styleClass="label" value="Bank Type: "/>
                        <h:outputText id="txtBankType" styleClass="output" value="#{BankIinDetail.bankType}"/>
                        <h:outputLabel id="ifsc" styleClass="label" value="IFSC Code: "/>
                        <h:outputText id="txtIfsc" styleClass="output" value="#{BankIinDetail.ifscCode}"/>
                        <h:outputLabel id="micr" styleClass="label" value="MICR: "/>
                        <h:outputText id="txtMicr" styleClass="output" value="#{BankIinDetail.micr}"/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{BankIinDetail.btnRefreshAction}" 
                                               reRender="mainPanel"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{BankIinDetail.btnExitAction}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>