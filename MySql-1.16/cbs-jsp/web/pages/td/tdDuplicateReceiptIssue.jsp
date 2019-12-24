<%-- 
    Document   : TdDuplicateReceiptIssue
    Created on : May 16, 2010, 4:40:29 PM
    Author     : Jitendra Kumar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!-- RichFaces tag library declaration -->
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>td Duplicate Receipt Issue </title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{TdDuplicateReceiptIssue.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="TD Duplicate Receipt Issue"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{TdDuplicateReceiptIssue.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel1" width="100%">
                        <h:panelGrid columns="1" id="gridPanel4" width="100%">
                            <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel15" style="height:30px;" styleClass="row1" width="100%">
                                <h:outputLabel id="label1" styleClass="label" value="Account No."><font class="required" color="red">*</font></h:outputLabel>
                                <h:panelGroup>
                                    <h:inputText id="txtAcno" value="#{TdDuplicateReceiptIssue.accountNo}" 
                                                 styleClass="input" tabindex="2" maxlength="#{TdDuplicateReceiptIssue.acNoMaxLen}" 
                                                 style="width:90px">
                                        <a4j:support actionListener="#{TdDuplicateReceiptIssue.getAccountDetails}" 
                                                     focus="txtRtNo"  event="onblur" 
                                                     reRender="stxtAcno,stxtMsg,txtRtNo,txtReceiptNo,stxtTotalPrinAmt,
                                                     stxtTdsDeducted,stxtTInterestTransferred,ddAcno,gridPanele,
                                                     stxtName,stxtBalanceInTdAccount"  limitToList="true" />
                                    </h:inputText>
                                    <h:outputText id="stxtAcno"  styleClass="output" value="#{TdDuplicateReceiptIssue.fullAccNo}" />
                                </h:panelGroup>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel16" style="height:30px;" styleClass="row2" width="100%">
                                <h:outputLabel id="label12" styleClass="label" value="Total Prin. Amount"/>
                                <h:outputText id="stxtTotalPrinAmt" styleClass="output" value="#{TdDuplicateReceiptIssue.prinAmt}" >
                                    <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                </h:outputText>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanela" style="height:30px;" styleClass="row1" width="100%">
                                <h:outputLabel id="labela" styleClass="label" value="TDS Deducted"/>
                                <h:outputText id="stxtTdsDeducted" styleClass="output" value="#{TdDuplicateReceiptIssue.tdsDeducted}" >
                                    <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                </h:outputText>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanelc" style="height:30px;" styleClass="row2" width="100%">
                                <h:outputLabel id="labelc" styleClass="label" value="Enter R.t. No."/>
                                <h:inputText id="txtRtNo" value="#{TdDuplicateReceiptIssue.rtNo}" styleClass="input" style="width:60px" tabindex="3" maxlength="12">
                                    <a4j:support actionListener="#{TdDuplicateReceiptIssue.setRtNumberValues}" focus="btnIssue"  event="onblur" reRender="stxtAcno,stxtMsg,txtRtNo,txtReceiptNo,stxtTotalPrinAmt,stxtTdsDeducted,stxtTInterestTransferred,stxtBalanceInTdAccount" limitToList="true" />

                                </h:inputText>
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid  columns="1" id="gridPanel103" width="100%" >
                            <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel14" style="height:30px;" styleClass="row1" width="100%">
                                <h:outputLabel id="label9" styleClass="label" value="Name"/>
                                <h:outputText id="stxtName"  styleClass="output" value="#{TdDuplicateReceiptIssue.custName}" />
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel17" style="height:30px;" styleClass="row2" width="100%">
                                <h:outputLabel id="label13" styleClass="label" value="Balance in TD A/C"/>
                                <h:outputText id="stxtBalanceInTdAccount" styleClass="output" value="#{TdDuplicateReceiptIssue.balanceInTd}" >
                                    <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                </h:outputText>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanelb" style="height:30px;" styleClass="row1" width="100%">
                                <h:outputLabel id="labelb" styleClass="label" value="Total Interest Transferred"/>
                                <h:outputText id="stxtTInterestTransferred" styleClass="output" value="#{TdDuplicateReceiptIssue.totalInt}" >
                                    <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                </h:outputText>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPaneld" style="height:30px;" styleClass="row2" width="100%">
                                <h:outputLabel id="labeld" styleClass="label" value="Receipt No."/>
                                <h:inputText id="txtReceiptNo"  disabled="true"value="#{TdDuplicateReceiptIssue.receiptNo}" styleClass="input" style="width:60px" >

                                </h:inputText>
                            </h:panelGrid>
                        </h:panelGrid>

                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="2" id="gridPanele" width="100%" styleClass="row2" style="height:200px;">
                       <a4j:region>
                            <rich:dataTable value="#{TdDuplicateReceiptIssue.tdDuplicate}" var="dataItem" rowClasses="row1,row2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="16"><h:outputText value="Duplicate Receipt Issue" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="acno." /></rich:column>
                                        <rich:column><h:outputText value="Control No." /></rich:column>
                                        <rich:column><h:outputText value="Rt No." /></rich:column>
                                        <rich:column><h:outputText value="Receipt No." /></rich:column>
                                        <rich:column><h:outputText value="ROI" /></rich:column>
                                        <rich:column><h:outputText value="TD Date" /></rich:column>
                                        <rich:column><h:outputText value="TD Date W.E.F" /></rich:column>
                                        <rich:column><h:outputText value="Mat. Date" /></rich:column>
                                        <rich:column><h:outputText value="Prin Amt(rs)" /></rich:column>
                                        <rich:column><h:outputText value="Int Opt" /></rich:column>
                                        <rich:column><h:outputText value="Int To Acno" /></rich:column>
                                        <rich:column><h:outputText value="Period" /></rich:column>
                                        <rich:column><h:outputText value="Intat Mat(rs)" /></rich:column>
                                        <rich:column><h:outputText value="Tot TD Amt(rs)" /></rich:column>
                                        <rich:column><h:outputText value="Status" /></rich:column>
                                        <rich:column><h:outputText value="Action" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.accountNumber}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.seqNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.voucherNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.receiptNo}" /></rich:column>
                                <rich:column ><h:outputText value="#{dataItem.roi}" /></rich:column>
                                <rich:column ><h:outputText value="#{dataItem.tdMadeDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.fdDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.matDt}" /></rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem.prinAmt}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column ><h:outputText value="#{dataItem.intOpt}" /></rich:column>
                                <rich:column ><h:outputText value="#{dataItem.intToAcno}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.period}" /></rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem.intAtMat}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem.totTdAmt}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column ><h:outputText value="#{dataItem.status}" /></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{TdDuplicateReceiptIssue.setRowValues}"
                                                     reRender="stxtAcno,txtRtNo,txtReceiptNo,stxtMsg,stxtTotalPrinAmt,stxtTdsDeducted,stxtTInterestTransferred,stxtBalanceInTdAccount" focus="btnIssue">
                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{TdDuplicateReceiptIssue.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{TdDuplicateReceiptIssue.currentRow}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="20" />
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel001" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                        <h:outputText id="stxtMsg" styleClass="error" value="#{TdDuplicateReceiptIssue.message}" />
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnIssue" value="Issue" action="#{TdDuplicateReceiptIssue.receiptIssues}" tabindex="4" reRender="txtReceiptNo,
                                               txtRtNo,stxtAcno,stxtTotalPrinAmt,stxtTdsDeducted,stxtTInterestTransferred,stxtBalanceInTdAccount,stxtName,gridPanele,taskList,stxtMsg" />
                            <a4j:commandButton id="btnDelete" value="Delete" action="#{TdDuplicateReceiptIssue.delete}" reRender="txtReceiptNo,txtRtNo,stxtAcno,stxtTotalPrinAmt,stxtTdsDeducted,stxtTInterestTransferred,stxtBalanceInTdAccount,stxtName,gridPanele,stxtMsg" tabindex="5"/>
                            <a4j:commandButton id="btnReset" value="Reset" action="#{TdDuplicateReceiptIssue.clearText}" reRender="txtReceiptNo,txtRtNo,stxtAcno,stxtTotalPrinAmt,stxtTdsDeducted,stxtTInterestTransferred,stxtBalanceInTdAccount,stxtName,gridPanele,stxtMsg" focus="ddAcno" tabindex="6"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{TdDuplicateReceiptIssue.exitFrm}" reRender="txtReceiptNo,txtRtNo,txtAcno,stxtAcno,stxtTotalPrinAmt,stxtTdsDeducted,stxtTInterestTransferred,stxtBalanceInTdAccount,stxtName,gridPanele,stxtMsg" tabindex="7"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
