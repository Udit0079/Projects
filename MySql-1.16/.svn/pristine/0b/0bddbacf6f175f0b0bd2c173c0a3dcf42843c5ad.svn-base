<!--
    Document   : Transfer Authorization
    Created on : Jan 28, 2011, 10:32:26 AM
    Author     : Dhirendra Singh
-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Transfer Authorization</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
        </head>
        <body>
            <a4j:form id="cashDrForm">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{XferAuthorization.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Transfer Authorization"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{XferAuthorization.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="selectedList" style=" width:100%;height:200px" styleClass="row1" columnClasses="vtop">

                        <rich:dataTable value="#{XferAuthorization.selectedItemList}" var="selectedItem"
                                        rowClasses="gridrow1, gridrow2" id="selectedItemList" rows="5" rowKeyVar="row"
                                        onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                        onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                        onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column width="30"><h:outputText value="Account No."/></rich:column>
                                    <rich:column width="100"><h:outputText value="Customer Name"/></rich:column>
                                    <rich:column width="60"><h:outputText value="Credit"/></rich:column>
                                    <rich:column width="60"><h:outputText value="Debit"/></rich:column>
                                    <rich:column width="30"><h:outputText value="Value Dt"/></rich:column>
                                    <rich:column width="30"><h:outputText value="Inst. No"/></rich:column>
                                    <rich:column width="30"><h:outputText value="Inst. Dt"/></rich:column>
                                    <rich:column width="30"><h:outputText value="Related To"/></rich:column>
                                    <rich:column width="50"><h:outputText value="Enter By"/></rich:column>
                                    <rich:column width="150"><h:outputText value="Details"/></rich:column>
                                    <rich:column width="30px;" rendered="#{XferAuthorization.orgnBrCode == '90'}"><h:outputText value="AD Details"/></rich:column>
                                    <rich:column width="30px;"><h:outputText value="Sign View"/></rich:column>
                                    <rich:column width="30px;"><h:outputText value="A/c View"/></rich:column>

                                </rich:columnGroup>
                            </f:facet>

                            <rich:column styleClass="#{(dataItem.iy == 99)? 'highlightrow' : ''}"><h:outputText value="#{selectedItem.acNo}" /></rich:column>
                            <rich:column styleClass="#{(dataItem.iy == 99)? 'highlightrow' : ''}"><h:outputText value="#{selectedItem.custName}" /></rich:column>

                            <rich:column styleClass="#{(dataItem.iy == 99)? 'highlightrow' : ''}">
                                <h:outputText value="#{selectedItem.crAmt}">
                                    <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                </h:outputText>
                            </rich:column>
                            <rich:column styleClass="#{(dataItem.iy == 99)? 'highlightrow' : ''}">
                                <h:outputText value="#{selectedItem.drAmt}">
                                    <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                </h:outputText>
                            </rich:column>
                            <rich:column styleClass="#{(dataItem.iy == 99)? 'highlightrow' : ''}"><h:outputText value="#{selectedItem.valueDt}" /></rich:column>
                            <rich:column styleClass="#{(dataItem.iy == 99)? 'highlightrow' : ''}"><h:outputText value="#{selectedItem.instNo}" /></rich:column>
                            <rich:column styleClass="#{(dataItem.iy == 99)? 'highlightrow' : ''}"><h:outputText value="#{selectedItem.instDt}" /></rich:column>
                            <rich:column styleClass="#{(dataItem.iy == 99)? 'highlightrow' : ''}"><h:outputText value="#{selectedItem.relatedTo}"/></rich:column>
                            <rich:column styleClass="#{(dataItem.iy == 99)? 'highlightrow' : ''}"><h:outputText value="#{selectedItem.enterBy}" /></rich:column>
                            <rich:column styleClass="#{(dataItem.iy == 99)? 'highlightrow' : ''}"><h:outputText value="#{selectedItem.viewDetails}"/></rich:column>
                            <rich:column styleClass="#{(dataItem.iy == 99)? 'highlightrow' : ''}" style="text-align:center;" rendered="#{XferAuthorization.orgnBrCode == '90'}">
                                <a4j:commandLink ajaxSingle="true" id="adViewLink" action="#{XferAuthorization.viewAdviceDetails}" oncomplete="#{rich:component('adView')}.show();" 
                                                 reRender="adView,txtAdNo,txtAdName">
                                    <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                    <f:setPropertyActionListener value="#{selectedItem}" target="#{XferAuthorization.currentItem}"/>
                                    <f:setPropertyActionListener value="#{row}" target="#{XferAuthorization.currentRow}" />
                                </a4j:commandLink>
                            </rich:column>
                            <rich:column styleClass="#{(dataItem.iy == 99)? 'highlightrow' : ''}" style="text-align:center;">
                                <a4j:commandLink ajaxSingle="true" id="signViewLink" action="#{XferAuthorization.signatureDetails}" oncomplete="if(#{XferAuthorization.openPopUp==true}){#{rich:component('sigView')}.show();} else if(#{XferAuthorization.openPopUp==false}){#{rich:component('sigView')}.hide();}" reRender="signature,txtAcNo,txtAcName,txtOpMode,txtOpDt,txtJtName,txtAcIns,txtCustomerId,txtPanNo,txtSigAnnualTrunOver,txtProfession,txtSigRiskCat,txtSigDpLimit">
                                    <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                    <f:setPropertyActionListener value="#{selectedItem}" target="#{XferAuthorization.currentItem}"/>
                                    <f:setPropertyActionListener value="#{row}" target="#{XferAuthorization.currentRow}" />
                                </a4j:commandLink>
                            </rich:column>
                            <rich:column styleClass="#{(dataItem.iy == 99)? 'highlightrow' : ''}" style="text-align:center;">
                                <a4j:commandLink ajaxSingle="true" id="acViewLink" action="#{XferAuthorization.acctViewAuthUnAuth}" oncomplete="#{rich:component('acView')}.show();" reRender="acView,errMsg">
                                    <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                    <f:setPropertyActionListener value="#{selectedItem}" target="#{XferAuthorization.currentItem}"/>
                                </a4j:commandLink>
                            </rich:column>

                        </rich:dataTable>
                        <rich:datascroller id="selectedListScroller" align="left" for="selectedItemList" maxPages="5"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="crdrBalPanel" style="width:100%;text-align:left;display:#{XferAuthorization.crdrFlag}" styleClass="row2">
                        <h:outputLabel id="labelCrBal" styleClass="label" value="Cr. Amount"/>
                        <h:outputText id="stxtCrBal" styleClass="output" value="#{XferAuthorization.crAmount}">
                            <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                        </h:outputText>
                        <h:outputLabel id="labelDrBal" styleClass="label" value="Dr. Amount"/>
                        <h:outputText id="stxtDrBal" styleClass="output" value="#{XferAuthorization.drAmount}">
                            <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                        </h:outputText>
                    </h:panelGrid>
                    <h:panelGrid columns="3" columnClasses="col3,col3,col5" style="width: 100%;" styleClass="row2">
                        <h:panelGroup id="batchPanel" >
                            <h:outputLabel id="batchLbl" value="Batch No to Authorize:" styleClass="label"/>
                            <h:inputText id="batchNo" styleClass="input" value="#{XferAuthorization.trsNo}" size="10">
                                <a4j:support actionListener="#{XferAuthorization.setSelectedBatch}" event ="onblur"
                                             reRender="selectedItemList,selectedListScroller,taskList,message,errMsg,crdrBalPanel,xferDataScroll"/>
                            </h:inputText>
                        </h:panelGroup>
                        <h:outputText id="message" styleClass="msg" value="#{XferAuthorization.message}"/>
                        <h:outputText id="errMsg" styleClass="error" value="#{XferAuthorization.errorMsg}"/>

                    </h:panelGrid>
                    <h:panelGrid id="gridPanelTable" style=" width:100%;height:200px" styleClass="row1" columnClasses="vtop">

                        <rich:dataTable value="#{XferAuthorization.xferDetailsItemList}" var="dataItem"
                                        rowClasses="gridrow1, gridrow2" id="taskList" rows="5" rowKeyVar="row"
                                        onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                        onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                        onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column ><h:outputText value="Batch No."/></rich:column>
                                    <rich:column ><h:outputText value="Account No."/></rich:column>
                                    <rich:column ><h:outputText value="Customer Name"/></rich:column>

                                    <rich:column ><h:outputText value="Balance"/></rich:column>
                                    <rich:column ><h:outputText value="Credit"/></rich:column>
                                    <rich:column ><h:outputText value="Debit"/></rich:column>

                                    <rich:column ><h:outputText value="Value Dt"/></rich:column>
                                    <rich:column ><h:outputText value="Inst. No"/></rich:column>
                                    <rich:column ><h:outputText value="Inst. Dt"/></rich:column>
                                    <rich:column ><h:outputText value="Auth"/></rich:column>

                                    <rich:column ><h:outputText value="Enter By"/></rich:column>
                                    <rich:column ><h:outputText value="Rec No."/></rich:column>
                                    <rich:column><h:outputText value="Details"/></rich:column>
                                </rich:columnGroup>
                            </f:facet>

                            <rich:column styleClass="#{(dataItem.iy == 99)? 'highlightrow' : ''}">
                                <h:outputText value="#{dataItem.trsNo}">
                                    <f:convertNumber type="number" pattern="#"/>
                                </h:outputText>
                            </rich:column>
                            <rich:column styleClass="#{(dataItem.iy == 99)? 'highlightrow' : ''}"><h:outputText value="#{dataItem.acNo}" /></rich:column>
                            <rich:column styleClass="#{(dataItem.iy == 99)? 'highlightrow' : ''}"><h:outputText value="#{dataItem.custName}" /></rich:column>
                            <rich:column styleClass="#{(dataItem.iy == 99)? 'highlightrow' : ''}">
                                <h:outputText value="#{dataItem.balance}">
                                    <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                </h:outputText>
                            </rich:column>
                            <rich:column styleClass="#{(dataItem.iy == 99)? 'highlightrow' : ''}">
                                <h:outputText value="#{dataItem.crAmt}">
                                    <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                </h:outputText>
                            </rich:column>
                            <rich:column styleClass="#{(dataItem.iy == 99)? 'highlightrow' : ''}">
                                <h:outputText value="#{dataItem.drAmt}">
                                    <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                </h:outputText>
                            </rich:column>
                            <rich:column styleClass="#{(dataItem.iy == 99)? 'highlightrow' : ''}"><h:outputText value="#{dataItem.valueDt}" /></rich:column>
                            <rich:column styleClass="#{(dataItem.iy == 99)? 'highlightrow' : ''}"><h:outputText value="#{dataItem.instNo}" /></rich:column>
                            <rich:column styleClass="#{(dataItem.iy == 99)? 'highlightrow' : ''}"><h:outputText value="#{dataItem.instDt}" /></rich:column>
                            <rich:column styleClass="#{(dataItem.iy == 99)? 'highlightrow' : ''}"><h:outputText value="#{dataItem.auth}" /></rich:column>
                            <rich:column styleClass="#{(dataItem.iy == 99)? 'highlightrow' : ''}"><h:outputText value="#{dataItem.enterBy}" /></rich:column>
                            <rich:column styleClass="#{(dataItem.iy == 99)? 'highlightrow' : ''}">
                                <h:outputText value="#{dataItem.recNo}">
                                    <f:convertNumber type="number" pattern="#"/>
                                </h:outputText>
                            </rich:column>
                            <rich:column styleClass="#{(dataItem.iy == 99)? 'highlightrow' : ''}"><h:outputText value="#{dataItem.viewDetails}"/></rich:column>

                        </rich:dataTable>
                        <rich:datascroller align="left" for="taskList" maxPages="5" id="xferDataScroll"/>
                    </h:panelGrid>

                    <h:panelGrid columns="3" id="gridPanelBtn" style="width:100%;text-align:center;" styleClass="footer" columnClasses="col19,col19,col19">
                        <h:panelGroup id="imageMsgPanel" style="text-align:left;" layout="block">
                            <h:outputText id="stxtInstruction" styleClass="output" value="" style="color:blue;"/>
                        </h:panelGroup>

                        <h:panelGroup id="gridPaneGrp" layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnSave" value="Authorize" action="#{XferAuthorization.authorizeXferDetail}" reRender="selectedItemList,taskList,batchPanel,message,errMsg,stxtCrBal,stxtDrBal"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" reRender="selectedItemList,taskList,batchPanel,message,errMsg,stxtCrBal,stxtDrBal"
                                               action="#{XferAuthorization.refresh}"/>
                            <a4j:commandButton id="btnExit" value="Exit" reRender="bodyPanel" action="#{XferAuthorization.btnExit_action}"/>
                        </h:panelGroup>

                        <h:panelGroup layout="block">
                            <h:outputLabel/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
            </a4j:form>
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
                        <h:panelGrid id="acViewRow1" columns="6" columnClasses="col1,col2,col1,col2,col1,col1," width="100%" styleClass="row1" style="text-align:left;">
                            <h:outputLabel id="lblAccNo" styleClass="label" value="Account No."/>
                            <h:outputText id="txtAccNo" style="output" value="#{XferAuthorization.accountNo}"/>
                            <h:outputLabel id="lblOpeningBal" styleClass="label" value="Opening Balance:"/>
                            <h:outputText id="txtOpeningBal" style="output" value="#{XferAuthorization.openBalance}">
                                <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                            </h:outputText>
                            <h:outputLabel id="lblPresentBal" styleClass="label" value="Present Balance:"/>
                            <h:outputText id="txtPresentBal" style="output" value="#{XferAuthorization.presentBalance}">
                                <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                            </h:outputText>
                        </h:panelGrid>
                        <h:panelGrid id="acViewRow2" columns="4" columnClasses="col1,col2,col1,col9" width="100%" styleClass="row2" style="text-align:left;">
                            <h:outputLabel id="lblAcViewNominee" styleClass="label" value="Nominee"/>
                            <h:outputText id="txtAcViewNominee" style="output" value="#{XferAuthorization.acViewNominee}"/>
                            <h:outputLabel id="lblAcViewJtname" styleClass="label" value="Jt.Name"/>
                            <h:outputText id="txtAcViewJtname" style="output" value="#{XferAuthorization.acViewJtName}"/>
                        </h:panelGrid>

                        <h:panelGrid id="acViewAuthGrid" width="100%" style="background-color:#e8eef7;height:260px" columnClasses="vtop">
                            <rich:dataTable  var="txnItem" value="#{XferAuthorization.txnDetailList}"
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
                            <rich:dataTable  var="unAuthItem" value="#{XferAuthorization.txnDetailUnAuthList}"
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

                    <h:panelGrid id="sigViewRow1" columns="6" columnClasses="col1,col3,col3,col3,col3,col3" width="100%" styleClass="row2" style="text-align:left;">
                        <h:outputLabel id="lblAccNo" styleClass="label" value="Account No."/>
                        <h:outputText id="txtAcNo" style="output" value="#{XferAuthorization.accNo}"/>
                        <h:outputLabel id="lblAcName" styleClass="label" value="Name"/>
                        <h:outputText id="txtAcName" style="output" value="#{XferAuthorization.accountName}"/>
                        <h:outputLabel id="lblCustomerId" styleClass="label" value="Customer Id"/>
                        <h:outputText id="txtCustomerId" style="output" value="#{XferAuthorization.custId}"/>
                    </h:panelGrid>

                    <h:panelGrid id="sigViewRow2" columns="6" columnClasses="col1,col3,col3,col3,col3,col3" width="100%" styleClass="row1" style="text-align:left;">
                        <h:outputLabel id="lblOpMode" styleClass="label" value="Operation Mode"/>
                        <h:outputText id="txtOpMode" style="output" value="#{XferAuthorization.opMode}"/>
                        <h:outputLabel id="lblOpDt" styleClass="label" value="Opening Date"/>
                        <h:outputText id="txtOpDt" style="output" value="#{XferAuthorization.openDate}"/>
                        <h:outputLabel id="lblPanNo" styleClass="label" value="Pan No"/>
                        <h:outputText id="txtPanNo" style="output" value="#{XferAuthorization.custPanNo}"/>
                    </h:panelGrid>

                    <h:panelGrid id="sigViewRow3" columns="4" columnClasses="col1,col51,col3,col3" width="100%" styleClass="row2" style="text-align:left;">
                        <h:outputLabel id="lblJtName" styleClass="label" value="Jt.Name"/>
                        <h:outputText id="txtJtName" style="output" value="#{XferAuthorization.jtName}"/>
                        <h:outputLabel id="lblSigAnnualTrunOver" styleClass="label" value="Annual Turnover"/>
                        <h:outputText id="txtSigAnnualTrunOver" style="output" value="#{XferAuthorization.annualTurnover}"/>
                    </h:panelGrid>

                    <h:panelGrid id="sigViewRow4" columns="4" columnClasses="col1,col51,col3,col3" width="100%" styleClass="row1" style="text-align:left;">
                        <h:outputLabel id="lblAcInst" styleClass="label" value="A/c Instruction"/>
                        <h:outputText id="txtAcIns" style="output" value="#{XferAuthorization.accInstruction}"/>
                        <h:outputLabel id="lblProfession" styleClass="label" value="Profession"/>
                        <h:outputText id="txtProfession" style="output" value="#{XferAuthorization.profession}"/>
                    </h:panelGrid>

                    <h:panelGrid id="custIdPanelGrid" columns="4" columnClasses="col1,col51,col3,col3" width="100%" styleClass="row2" style="text-align:left;">
                        <h:outputLabel id="lblSigRiskCat" styleClass="label" value="Risk Category"/>
                        <h:outputText id="txtSigRiskCat" style="output" value="#{XferAuthorization.riskCategorization}"/>
                        <h:outputLabel id="lblSigDpLimit" styleClass="label" value="DP Limit"/>
                        <h:outputText id="txtSigDpLimit" style="output" value="#{XferAuthorization.dpLimit}"/>
                    </h:panelGrid>

                    <h:panelGrid id="Panel" style="width:100%;text-align:center;">
                        <h:panelGroup layout="block" id="sigViewPanel" style="overflow:auto;width:700px;height:300px;text-align:center;background-color:#e8eef7">
                            <a4j:mediaOutput id="signature" element="img" createContent="#{XferAuthorization.createSignature}" value="#{XferAuthorization.accNo}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <%--<h:panelGrid columns="1" id="signErrorPanel" style="width:100%;text-align:center;" styleClass="row1">
                        <h:outputLabel id="txtSignError" styleClass="error" value="#{XferAuthorization.msg}"/>
                    </h:panelGrid>--%>
                    <h:panelGrid id="sigViewFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="sigViewBtnPanel">
                            <a4j:commandButton id="sigViewClose" value="Close" onclick="#{rich:component('sigView')}.hide(); return false;"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </a4j:form>
            </rich:modalPanel>

            <rich:modalPanel id="adView" height="150" width="400" style="align:right">
                <f:facet name="header">
                    <h:outputText value="Advice Detail"/>
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="closelink2"/>
                        <rich:componentControl for="adView" attachTo="closelink2" operation="hide" event="onclick"/>
                    </h:panelGroup>
                </f:facet>
                <a4j:form>
                    <h:panelGrid id="adViewRow1" columns="4" columnClasses="col1,col2,col1,col2" width="100%" styleClass="row2" style="text-align:left;">
                        <h:outputLabel id="lbladNo" styleClass="label" value="Advice No."/>
                        <h:outputText id="txtAdNo" style="output" value="#{XferAuthorization.adNo}"/>
                        <h:outputLabel id="lblAdName" styleClass="label" value="Advice Branch Name"/>
                        <h:outputText id="txtAdName" style="output" value="#{XferAuthorization.adBrName}"/>
                    </h:panelGrid>
                    <h:panelGrid id="adViewRow2" columns="1" width="100%" styleClass="row1" style="text-align:left;">
                    </h:panelGrid>    
                    <h:panelGrid id="adViewFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <a4j:commandButton id="adViewClose" value="Close" onclick="#{rich:component('adView')}.hide(); return false;"/>
                    </h:panelGrid>
                </a4j:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>