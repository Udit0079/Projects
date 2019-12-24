<%--
    Document   : CashDeposit
    Created on : Jan 21, 2011, 4:01:48 PM
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
            <link rel="stylesheet" href="/cbs-jsp/resources/txn_style.css" type="text/css"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Cash Deposit Authorization</title>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{CashDeposit.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Cash Deposit Authorization"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{CashDeposit.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="errormsg" width="100%" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="errmsg" value="#{CashDeposit.msg}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel1" width="100%" styleClass="row2" style="height:200px;">
                        <rich:contextMenu attached="false" id="menu" submitMode="ajax" oncollapse="row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'">
                            <rich:menuItem value="Select Record" ajaxSingle="true" actionListener="#{CashDeposit.fetchCurrentRow}">
                                <a4j:actionparam name="recno" value="{recNo}"/>
                                <a4j:actionparam name="row" value="{currentRow}"/>
                            </rich:menuItem>
                        </rich:contextMenu>
                        <rich:dataTable  rows="6" value="#{CashDeposit.cashCrItemList}" var="dataItem" rowClasses="gridrow1, gridrow2" id="datalist" columnsWidth="100"
                                         rowKeyVar="row" onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column width="32px"><h:outputText value="Sr.No." /> </rich:column>
                                    <rich:column width="55px"><h:outputText value="Voucher No." /></rich:column>
                                    <rich:column width="80px"><h:outputText value="A/c No." /></rich:column>
                                    <rich:column width="250px"><h:outputText value="Customer Name" /></rich:column>
                                    <rich:column width="70px"><h:outputText value="Inst. Amount" /></rich:column>
                                    <rich:column width="100px"><h:outputText value="Balance Before Inst." /></rich:column>
                                    <%--<rich:column><h:outputText value="Inst.No" /></rich:column>
                                    <rich:column><h:outputText value="Inst.Dt" /></rich:column>--%>
                                    <rich:column width="65px"><h:outputText value="Value Dt" /></rich:column>
                                    <rich:column width="30"><h:outputText value="Related To"/></rich:column>
                                    <rich:column width="65px"><h:outputText value="Enter By" /></rich:column>
                                    <rich:column width="50px"><h:outputText value="Rec No." /></rich:column>
                                    <rich:column><h:outputText value="Details" /></rich:column>
                                    <rich:column width="65px"><h:outputText value="Auth Status" /></rich:column>
                                    <rich:column width="55px" ><h:outputText value="Sign. View"/></rich:column>
                                    <%--rich:column width="45px" rendered="#{CashDeposit.cashMode == 'N'}"><h:outputText value="Delete" /></rich:column--%>
                                    <rich:column width="45px"><h:outputText value="Delete" /></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{dataItem.sNo}" style="text-align:center"/></rich:column>
                            <rich:column><h:outputText  value="#{dataItem.vchNo}" style="text-align:center"/></rich:column>
                            <rich:column ><h:outputText  value="#{dataItem.acNo}" style="text-align:center"/></rich:column>
                            <rich:column ><h:outputText  value="#{dataItem.custName}" style="text-align:center"/></rich:column>
                            <rich:column >
                                <h:outputText  value="#{dataItem.instAmt}" style="text-align:center">
                                    <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                </h:outputText>
                            </rich:column>
                            <rich:column >
                                <h:outputText  value="#{dataItem.balBeforeInst}" style="text-align:center">
                                    <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                </h:outputText>
                            </rich:column>
                            <%--<rich:column ><h:outputText  value="#{dataItem.instNo}" style="text-align:center"/></rich:column>
                            <rich:column ><h:outputText  value="#{dataItem.instDt}" style="text-align:center"/></rich:column>--%>
                            <rich:column ><h:outputText  value="#{dataItem.valueDt}" style="text-align:center"/></rich:column>
                            <rich:column ><h:outputText  value="#{dataItem.relatedTo}" style="text-align:center"/></rich:column>
                            <rich:column ><h:outputText  value="#{dataItem.enterBy}" style="text-align:center"/></rich:column>
                            <rich:column ><h:outputText  value="#{dataItem.recNo}" style="text-align:center"/></rich:column>
                            <rich:column ><h:outputText  value="#{dataItem.viewDetails}" style="text-align:center"/></rich:column>
                            <rich:column style="text-align:center;cursor:pointer;"> 
                                <h:outputText  value="#{dataItem.authStatus}" style="text-align:center"/>
                                <a4j:support action="#{CashDeposit.changeStatus}" ajaxSingle="true" event="ondblclick" reRender="datalist,errormsg,authorize,delete">
                                    <f:setPropertyActionListener value="#{dataItem}" target="#{CashDeposit.currentItem}"/>
                                    <f:setPropertyActionListener value="#{row}" target="#{CashDeposit.currentRow}"/>
                                </a4j:support>
                            </rich:column>
                            
                            <rich:column style="text-align:center;">
                                    <a4j:commandLink ajaxSingle="true" id="signViewLink" action="#{CashDeposit.getSignatureDetail}"
                                                     oncomplete="#{rich:component('sigView')}.show();" reRender="signature,txtAcNo,txtAcName,txtOpMode,txtOpDt,txtJtName,txtAcIns,txtCustomerId,txtPanNo,txtSigAnnualTrunOver,txtProfession,txtSigRiskCat,txtSigDpLimit">
                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{row}" target="#{CashDeposit.currentRow}" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{CashDeposit.currentItem}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            
                            <%--rich:column style="text-align:center;" rendered="#{CashDeposit.cashMode == 'N'}"--%>
                            <rich:column style="text-align:center;">
                                <a4j:commandLink ajaxSingle="true" id="deletelink" oncomplete="#{rich:component('deletePanel')}.show()">
                                    <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                    <f:setPropertyActionListener value="#{row}" target="#{CashDeposit.currentRow}" />
                                    <f:setPropertyActionListener value="#{dataItem}" target="#{CashDeposit.currentItem}"/>
                                </a4j:commandLink>
                            </rich:column>
                        </rich:dataTable>
                        <rich:datascroller align="left" for="datalist" maxPages="20" />
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" columns="3" columnClasses="col19,col19,col19">
                        <h:panelGroup id="imageMsgPanel" style="text-align:left;" layout="block">
                            <h:outputText id="stxtInstruction" styleClass="output" value="To view A/C Info. change Auth Status to Y and Press F4, For Denomination Detail - F8" style="color:blue;"/>
                        </h:panelGroup>
                        <h:panelGroup id="btnPanel" styleClass="vtop">
                            <a4j:region id="btnPnl">
                                <a4j:commandButton id="authorize" disabled="#{CashDeposit.authorizeValue}" action="#{CashDeposit.authorizeAction}" value="Authorize" reRender="errmsg,datalist,authorize,delete"/>
                                <%--<a4j:commandButton id="delete" disabled="#{CashDeposit.deleteValue}" value="Delete"  onclick="#{rich:component('deletePanel')}.show();"/>--%>
                                <a4j:commandButton id="refresh" action="#{CashDeposit.refreshForm}" value="Refresh" reRender="errmsg,datalist,authorize,delete"/>
                                <a4j:commandButton id="exit" action="#{CashDeposit.exitForm}" value="Exit" reRender="errmsg,datalist,authorize,delete"/>
                            </a4j:region>
                        </h:panelGroup>
                        <h:panelGroup layout="block">
                            <h:outputLabel/>
                        </h:panelGroup>
                        
                    </h:panelGrid>
                </h:panelGrid>
                    <a4j:jsFunction name="showAcDeatil" oncomplete="if(#{CashDeposit.accNo==null} || #{CashDeposit.accNo==''}){#{rich:component('acView')}.hide();} else{#{rich:component('acView')}.show();}" reRender="acView,errmsg" action="#{CashDeposit.acctViewAuthUnAuth}"/>
                    <a4j:jsFunction name="showDenoDeatil" oncomplete="if(#{CashDeposit.accNo==null} || #{CashDeposit.accNo==''}){#{rich:component('denoView')}.hide();} else{#{rich:component('denoView')}.show();}" reRender="denoView,errmsg" action="#{CashDeposit.acctDenoView}"/>
            </a4j:form>
            
            <rich:modalPanel id="sigView" height="530" width="700" style="align:right">
                <f:facet name="header">
                    <h:outputText value="Signature Detail"/>
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="closelink1"/>
                        <rich:componentControl for="sigView" attachTo="closelink1" operation="hide" event="onclick"/>
                    </h:panelGroup>
                </f:facet>
                <a4j:form>
                    <h:panelGrid id="Panel" style="width:100%;text-align:center;">
                        <h:panelGrid id="sigViewRow1" columns="6" columnClasses="col1,col3,col3,col3,col3,col3" width="100%" styleClass="row2" style="text-align:left;">
                            <h:outputLabel id="lblAccNo" styleClass="label" value="Account No."/>
                            <h:outputText id="txtAcNo" style="output" value="#{CashDeposit.accNo}"/>
                            <h:outputLabel id="lblAcName" styleClass="label" value="Name"/>
                            <h:outputText id="txtAcName" style="output" value="#{CashDeposit.accountName}"/>
                            <h:outputLabel id="lblCustomerId" styleClass="label" value="Customer Id"/>
                            <h:outputText id="txtCustomerId" style="output" value="#{CashDeposit.custId}"/>
                        </h:panelGrid>

                        <h:panelGrid id="sigViewRow2" columns="6" columnClasses="col1,col3,col3,col3,col3,col3" width="100%" styleClass="row1" style="text-align:left;">
                            <h:outputLabel id="lblOpMode" styleClass="label" value="Operation Mode"/>
                            <h:outputText id="txtOpMode" style="output" value="#{CashDeposit.opMode}"/>
                            <h:outputLabel id="lblOpDt" styleClass="label" value="Opening Date"/>
                            <h:outputText id="txtOpDt" style="output" value="#{CashDeposit.openDate}"/>
                            <h:outputLabel id="lblPanNo" styleClass="label" value="Pan No"/>
                            <h:outputText id="txtPanNo" style="output" value="#{CashDeposit.custPanNo}"/>
                        </h:panelGrid>

                        <h:panelGrid id="sigViewRow3" columns="4" columnClasses="col1,col51,col3,col3" width="100%" styleClass="row2" style="text-align:left;">
                            <h:outputLabel id="lblJtName" styleClass="label" value="Jt.Name"/>
                            <h:outputText id="txtJtName" style="output" value="#{CashDeposit.jtName}"/>
                            <h:outputLabel id="lblSigAnnualTrunOver" styleClass="label" value="Annual Turnover"/>
                            <h:outputText id="txtSigAnnualTrunOver" style="output" value="#{CashDeposit.annualTurnover}"/>
                        </h:panelGrid>

                        <h:panelGrid id="sigViewRow4" columns="4" columnClasses="col1,col51,col3,col3" width="100%" styleClass="row1" style="text-align:left;">
                            <h:outputLabel id="lblAcInst" styleClass="label" value="A/c Instruction"/>
                            <h:outputText id="txtAcIns" style="output" value="#{CashDeposit.accInstruction}"/>
                            <h:outputLabel id="lblProfession" styleClass="label" value="Profession"/>
                            <h:outputText id="txtProfession" style="output" value="#{CashDeposit.profession}"/>
                        </h:panelGrid>
                        
                        <h:panelGrid id="custIdPanelGrid" columns="4" columnClasses="col1,col51,col3,col3" width="100%" styleClass="row2" style="text-align:left;">
                            <h:outputLabel id="lblSigRiskCat" styleClass="label" value="Risk Category"/>
                            <h:outputText id="txtSigRiskCat" style="output" value="#{CashDeposit.riskCategorization}"/>
                            <h:outputLabel id="lblSigDpLimit" styleClass="label" value="DP Limit"/>
                            <h:outputText id="txtSigDpLimit" style="output" value="#{CashDeposit.dpLimit}"/>
                        </h:panelGrid>
                        
                        <h:panelGroup layout="block" id="sigViewPanel" style="overflow:auto;width:700px;height:300px;text-align:center;background-color:#e8eef7">
                            <a4j:mediaOutput id="signature" element="img" createContent="#{CashDeposit.createSignature}" value="#{CashDeposit.accNo}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <%--<h:panelGrid columns="1" id="signErrorPanel" style="width:100%;text-align:center;" styleClass="row1">
                        <h:outputLabel id="txtSignError" styleClass="error" value="#{CashDeposit.errorMsg}"/>
                    </h:panelGrid>--%>
                    <h:panelGrid id="sigViewFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="sigViewBtnPanel">
                            <a4j:commandButton id="sigViewClose" value="Close" onclick="#{rich:component('sigView')}.hide(); return false;">

                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </a4j:form>
            </rich:modalPanel>

            <rich:modalPanel id="deletePanel" label="Confirmation Dialog" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();" style="">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog"/>
                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText value="Are you sure to delete this transaction ? "/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" ajaxSingle="true" action="#{CashDeposit.deleteForm}" reRender="errmsg,datalist,authorize,delete"
                                                           oncomplete="#{rich:component('deletePanel')}.hide();"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="btnPnl"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
            <rich:modalPanel id="acView" height="560" width="900" left="true">
                <f:facet name="header">
                    <h:outputText value="Account Detail"/>
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="closelink"/>
                        <rich:componentControl for="acView" attachTo="closelink" operation="hide" event="onclick"/>
                    </h:panelGroup>
                </f:facet>
                <a4j:form>
                    <h:panelGrid id="acViewPanel" width="100%">
                        <h:panelGrid id="acViewRow1" columns="6" columnClasses="col1,col2,col1,col2,col1,col1," width="100%" styleClass="row1" style="text-align:left;">                            <h:outputLabel id="lblAccNo" styleClass="label" value="Account No."/>
                            <h:outputText id="txtAccNo" style="output" value="#{CashDeposit.accNo}"/>
                            <h:outputLabel id="lblOpeningBal" styleClass="label" value="Opening Balance:"/>
                            <h:outputText id="txtOpeningBal" style="output" value="#{CashDeposit.openBalance}">
                                <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                            </h:outputText>
                            <h:outputLabel id="lblPresentBal" styleClass="label" value="Present Balance:"/>
                            <h:outputText id="txtPresentBal" style="output" value="#{CashDeposit.presentBalance}">
                                <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                            </h:outputText>
                        </h:panelGrid>

                        <h:panelGrid id="acViewAuthGrid" width="100%" style="background-color:#e8eef7;height:260px" columnClasses="vtop">
                            <rich:dataTable  var="txnItem" value="#{CashDeposit.txnDetailList}"
                                             rowClasses="gridrow1, gridrow2" id="authTxnList" rows="8"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                             width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="8"><h:outputText value="Account Details" /></rich:column>
                                        <rich:column breakBefore="true" style="width:"><h:outputText value="Date" /></rich:column>
                                        <rich:column><h:outputText value="Particulars" /></rich:column>
                                        <rich:column><h:outputText value="Cheque No." /></rich:column>
                                        <rich:column><h:outputText value="Withdrawl" /></rich:column>
                                        <rich:column><h:outputText value="Deposite" /></rich:column>
                                        <rich:column><h:outputText value="Balance" /></rich:column>
                                        <rich:column><h:outputText value="Entered By" /></rich:column>
                                        <rich:column><h:outputText value="Authorized" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{txnItem.dt}"/></rich:column>
                                <rich:column><h:outputText value="#{txnItem.details}"/></rich:column>
                                <rich:column><h:outputText value="#{txnItem.instNo}"/></rich:column>
                                <rich:column><h:outputText value="#{txnItem.drAmt}">
                                        <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                                    </h:outputText>
                                </rich:column>
                                <rich:column><h:outputText value="#{txnItem.crAmt}">
                                        <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                                    </h:outputText>
                                </rich:column>
                                <rich:column><h:outputText value="#{txnItem.amount}">
                                        <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                                    </h:outputText>
                                </rich:column>
                                <rich:column><h:outputText value="#{txnItem.enterBy}"/></rich:column>
                                <rich:column><h:outputText  value="#{txnItem.authBy}"/></rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="authTxnList" maxPages="20" />
                        </h:panelGrid>


                        <h:panelGrid id="pendingTxnGrid" width="100%" style="background-color:#edebeb;height:200px;" columnClasses="vtop">
                            <rich:dataTable  var="unAuthItem" value="#{CashDeposit.txnDetailUnAuthList}"
                                             rowClasses="gridrow1, gridrow2" id="pendingTxnList" rows="5"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                             width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="8"><h:outputText value="Today's Pending Authorization" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Date" /></rich:column>
                                        <rich:column><h:outputText value="Particulars" /></rich:column>
                                        <rich:column><h:outputText value="Cheque No." /></rich:column>
                                        <rich:column><h:outputText value="Withdrawl" /></rich:column>
                                        <rich:column><h:outputText value="Deposite" /></rich:column>
                                        <rich:column><h:outputText value="Balance" /></rich:column>
                                        <rich:column><h:outputText value="Entered By" /></rich:column>
                                        <rich:column><h:outputText value="Authorized" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{unAuthItem.dt}"/></rich:column>
                                <rich:column><h:outputText value="#{unAuthItem.details}"/></rich:column>
                                <rich:column><h:outputText value="#{unAuthItem.instNo}"/></rich:column>
                                <rich:column><h:outputText value="#{unAuthItem.drAmt}">
                                        <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                                    </h:outputText>
                                </rich:column>
                                <rich:column><h:outputText value="#{unAuthItem.crAmt}">
                                        <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                                    </h:outputText>
                                </rich:column>
                                <rich:column><h:outputText value="#{unAuthItem.amount}">
                                        <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                                    </h:outputText>
                                </rich:column>
                                <rich:column><h:outputText value="#{unAuthItem.enterBy}"/></rich:column>
                                <rich:column><h:outputText value="#{unAuthItem.authBy}"/></rich:column>
                            </rich:dataTable>
                            <rich:datascroller id="unauthDataScroll"align="left" for="pendingTxnList" maxPages="20" />
                        </h:panelGrid>
                    </h:panelGrid>

                    <h:panelGrid id="acViewFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="acViewBtnPanel">
                            <a4j:commandButton id="acViewClose" value="Close" onclick="#{rich:component('acView')}.hide(); return false;">

                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </a4j:form>
              </rich:modalPanel>
            
              <rich:modalPanel id="denoView" height="560" width="900" left="true">
                <f:facet name="header">
                    <h:outputText value="Denomination Detail"/>
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="denoCloselink"/>
                        <rich:componentControl for="denoView" attachTo="denoCloselink" operation="hide" event="onclick"/>
                    </h:panelGroup>
                </f:facet>
                <a4j:form>
                    <h:panelGrid id="denoViewPanel" width="100%">
                        <h:panelGrid id="denoViewAuthGrid" width="100%" style="background-color:#e8eef7;height:260px" columnClasses="vtop">
                            <rich:dataTable  var="dataItem1" value="#{CashDeposit.denominationTable}"
                                             rowClasses="gridrow1, gridrow2" id="denominationTable" rows="8"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                             width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="25"><h:outputText value="Denomination Detail" /></rich:column>
                                        <rich:column width="30%" breakBefore="true"><h:outputText value="Denomination Value" style="text-align:center"/></rich:column>
                                        <rich:column width="25%"><h:outputText value="No. of Notes / Coins" style="text-align:left" /></rich:column>
                                        <rich:column width="30%"><h:outputText value="Amount"/></rich:column>
                                        <rich:column width="30%"><h:outputText value="Old/New"/></rich:column>
                                        <rich:column width="15%"><h:outputText value="Receive/Return"/></rich:column>                                        
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column style="text-align:right">
                                    <h:outputText value="#{dataItem1.denoValue}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column style="text-align:right"><h:outputText value="#{dataItem1.denoNo}"/></rich:column>
                                <rich:column style="text-align:right">
                                    <h:outputText value="#{dataItem1.denoAmount}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column style="text-align:right"><h:outputText value="#{dataItem1.flag}"/></rich:column>
                                <rich:column style="text-align:right"><h:outputText value="#{dataItem1.tySh}"/></rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="authTxnList" maxPages="20" />
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="denoViewFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="denoViewBtnPanel">
                            <a4j:commandButton id="denoViewClose" value="Close" onclick="#{rich:component('denoView')}.hide(); return false;"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </a4j:form>
              </rich:modalPanel>            
            <rich:hotKey id="F4Key"key="F4" handler="showAcDeatil();"/>
            <rich:hotKey id="F8Key"key="F8" handler="showDenoDeatil();"/>
        </body>
    </html>
</f:view>
