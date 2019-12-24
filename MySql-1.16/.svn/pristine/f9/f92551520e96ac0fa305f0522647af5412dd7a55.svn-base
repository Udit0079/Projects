<%--
    Document   : ClearingDeposit
    Created on : 03 feb, 2011, 6:31:56 PM
    Author     : Zeeshan Waris
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
            <title>Clearing Authorization</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="Form">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{ClearingDeposit.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Clearing Authorization"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="labelUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ClearingDeposit.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid   columns="1" id="subbodyPanel" style="width:100%;">
                        <h:panelGrid columnClasses="col2,col7,col9" columns="2" id="Panel790" style="width:100%;text-align:center;" styleClass="row2">
                            <h:outputText id="stxtMsg" styleClass="error" value="#{ClearingDeposit.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1" columns="2" id="Panel10" style="width:100%;" styleClass="row1">
                            <h:outputLabel id="lblOption" styleClass="label"  value="Authorization Mode"><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddGotTo" value="#{ClearingDeposit.authSelection}" size="1" style="width:160px" styleClass="ddlist">
                                    <f:selectItems value="#{ClearingDeposit.ddModeOfAuth}"/>
                                    <a4j:support ajaxSingle="true" actionListener="#{ClearingDeposit.getTableStock}" event="onchange" reRender="stxtMsg,Stocktable" />
                                </h:selectOneListbox>
                            </h:panelGrid>

                        <h:panelGrid columnClasses="vtop" columns="1" id="Stocktable" width="100%" styleClass="row2" style="height:168px;">
                            <a4j:region>
                                <rich:dataTable value="#{ClearingDeposit.statusTable}" var="item"
                                                rowClasses="row1, row2" id="taskList" rows="6" rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column width="42px"><h:outputText value="S.No"/></rich:column>
                                            <rich:column width="80px"><h:outputText value="Account No" /></rich:column>
                                            <rich:column width="150px"><h:outputText value="Customer Name" /></rich:column>

                                            <rich:column width="100px"><h:outputText value="Balance After Inst." /></rich:column>
                                            <rich:column width="70px"><h:outputText value="Cr Amount"/></rich:column>
                                            <rich:column width="70px"><h:outputText value="Dr Amount "/></rich:column>
                                            <rich:column width="50px"><h:outputText value="Instr No"/></rich:column>
                                            <rich:column width="80px"><h:outputText value="Inst Date"/></rich:column>
                                            <rich:column width="30"><h:outputText value="Related To"/></rich:column>
                                            <rich:column width="65px"><h:outputText value="Enter By"/></rich:column>
                                            <rich:column width="55px"><h:outputText value="Token No"/></rich:column>
                                            <rich:column width="80px"><h:outputText value="Value Date"/></rich:column>
                                            <rich:column width="50px"><h:outputText value="Rec No"/></rich:column>
                                            <rich:column ><h:outputText value="Details"/></rich:column>
                                            <rich:column width="65px"><h:outputText value="Auth Status"/></rich:column>

                                            <rich:column width="55px" rendered="#{ClearingDeposit.authSelection=='1'}"><h:outputText value="Sign. View" /></rich:column>
                                            <rich:column width="33px"><h:outputText value="Delete"/></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column styleClass="#{(item.iy == 99)? 'highlightrow' : ''}"><h:outputText value="#{item.sno}" /></rich:column>
                                    <rich:column styleClass="#{(item.iy == 99)? 'highlightrow' : ''}"><h:outputText value="#{item.acNo}" /></rich:column>
                                    <rich:column styleClass="#{(item.iy == 99)? 'highlightrow' : ''}"><h:outputText value="#{item.custName}" /></rich:column>
                                    <rich:column styleClass="#{(item.iy == 99)? 'highlightrow' : ''}">
                                        <h:outputText value="#{item.balance}">
                                            <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                        </h:outputText>
                                    </rich:column>

                                    <rich:column styleClass="#{(item.iy == 99)? 'highlightrow' : ''}">
                                        <h:outputText value="#{item.crAmt}">
                                            <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                        </h:outputText>
                                    </rich:column>
                                    <rich:column styleClass="#{(item.iy == 99)? 'highlightrow' : ''}">
                                        <h:outputText value="#{item.drAmt}">
                                            <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                        </h:outputText>
                                    </rich:column>

                                    <rich:column styleClass="#{(item.iy == 99)? 'highlightrow' : ''}"><h:outputText value="#{item.instNo}"/></rich:column>
                                    <rich:column styleClass="#{(item.iy == 99)? 'highlightrow' : ''}"><h:outputText value="#{item.instDt}"/></rich:column>
                                    <rich:column styleClass="#{(item.iy == 99)? 'highlightrow' : ''}"><h:outputText  value="#{item.relatedTo}"/></rich:column>
                                    <rich:column styleClass="#{(item.iy == 99)? 'highlightrow' : ''}"><h:outputText value="#{item.enterBy}"/></rich:column>
                                    <rich:column styleClass="#{(item.iy == 99)? 'highlightrow' : ''}"><h:outputText value="#{item.tokenNo}"/></rich:column>
                                    <rich:column styleClass="#{(item.iy == 99)? 'highlightrow' : ''}"><h:outputText value="#{item.valueDt}"/></rich:column>
                                    <rich:column styleClass="#{(item.iy == 99)? 'highlightrow' : ''}"><h:outputText value="#{item.recNo}"/></rich:column>
                                    <rich:column styleClass="#{(item.iy == 99)? 'highlightrow' : ''}"><h:outputText value="#{item.viewDetails}"/></rich:column>
                                    <rich:column styleClass="#{(item.iy == 99)? 'highlightrow' : ''}" style="text-align:center;cursor:pointer;">
                                        <h:outputText value="#{item.auth}"/>
                                        <a4j:support action="#{ClearingDeposit.changeAuth}" ajaxSingle="true" event="ondblclick" reRender="stxtMsg,taskList,btnSave" >
                                            <f:setPropertyActionListener value="#{item}" target="#{ClearingDeposit.currentItem}"/>
                                            <f:setPropertyActionListener value="#{row}" target="#{ClearingDeposit.currentRow}"/>
                                        </a4j:support>
                                    </rich:column>

                                    <rich:column styleClass="#{(item.iy == 99)? 'highlightrow' : ''}" style="text-align:center;" rendered="#{ClearingDeposit.authSelection=='1'}">
                                        <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{ClearingDeposit.select}" oncomplete="if(#{ClearingDeposit.authSelection=='1'})#{rich:component('sigView')}.show();" reRender="stxtMsg,footerPanel,signature,txtAcNo,txtAcName,txtOpMode,txtOpDt,txtJtName,txtAcIns,txtCustomerId,txtPanNo,txtSigAnnualTrunOver,txtProfession,txtSigRiskCat,txtSigDpLimit">
                                            <h:graphicImage value="/resources/images/passed.gif" style="border:0" />
                                            <f:setPropertyActionListener value="#{item}" target="#{ClearingDeposit.currentItem}"/>
                                            <f:setPropertyActionListener value="#{row}" target="#{ClearingDeposit.currentRow}" />
                                        </a4j:commandLink>
                                    </rich:column>

                                    <rich:column styleClass="#{(item.iy == 99)? 'highlightrow' : ''}" style="text-align:center;">
                                        <a4j:commandLink ajaxSingle="true" id="deletelink" reRender="subbodyPanel" oncomplete="#{rich:component('deletePanel')}.show()">
                                            <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                            <f:setPropertyActionListener value="#{row}" target="#{ClearingDeposit.currentRow}" />
                                            <f:setPropertyActionListener value="#{item}" target="#{ClearingDeposit.currentItem}"/>
                                        </a4j:commandLink>
                                    </rich:column>

                                </rich:dataTable>
                                <rich:datascroller id="scroller" align="left" for="taskList" maxPages="10" />
                            </a4j:region>
                        </h:panelGrid>

                        <h:panelGrid columnClasses="col1" columns="2" id="PanelSignal" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblSignal" style="font-size:12px;color:green" styleClass="headerLabel" value=" "/>
                        </h:panelGrid>

                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" columns="3" columnClasses="col19,col19,col19">
                        <h:panelGroup id="imageMsgPanel" style="text-align:left;" layout="block">
                            <h:outputText id="stxtInstruction" styleClass="output" value="To view A/C Info. change Auth Status to Y and Press F4" style="color:blue;"/>
                        </h:panelGroup>
                        <h:panelGroup id="BtnPanel">
                            <a4j:region id="btnPnl">
                                <a4j:commandButton id="btnSave" value="Authorize"  disabled="#{ClearingDeposit.authorizeValue}" action="#{ClearingDeposit.saveBtnAction}" 
                                                   reRender="taskList,mainPanel,stxtMsg" />
                                <a4j:commandButton  id="btnUpdate" value="Refresh" action="#{ClearingDeposit.btnRefresh}" reRender="stxtMsg,taskList,btnSave" />
                                <a4j:commandButton id="btnExit" value="Exit" action="#{ClearingDeposit.btnExit}"/>
                            </a4j:region>
                        </h:panelGroup>
                        <h:panelGroup layout="block">
                            <h:outputLabel/>
                        </h:panelGroup>
                    </h:panelGrid>

                </h:panelGrid>
                <a4j:jsFunction name="showAcDeatil" oncomplete="if(#{ClearingDeposit.accountNo==null} || #{ClearingDeposit.accountNo==''}){#{rich:component('acView')}.hide();} else{if(#{ClearingDeposit.authFlag=='Y'}){#{rich:component('acView')}.show();} else{#{rich:component('acView')}.hide();}}" reRender="acView,stxtMsg" action="#{ClearingDeposit.acctViewAuthUnAuth}"/>
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
                            <h:outputText id="txtAccNo" style="output" value="#{ClearingDeposit.accountNo}"/>
                            <h:outputLabel id="lblOpeningBal" styleClass="label" value="Opening Balance:"/>
                            <h:outputText id="txtOpeningBal" style="output" value="#{ClearingDeposit.openBalance}">
                                <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                            </h:outputText>
                            <h:outputLabel id="lblPresentBal" styleClass="label" value="Present Balance:"/>
                            <h:outputText id="txtPresentBal" style="output" value="#{ClearingDeposit.presentBalance}">
                                <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                            </h:outputText>
                        </h:panelGrid>

                        <h:panelGrid id="acViewAuthGrid" width="100%" style="background-color:#e8eef7;height:260px" columnClasses="vtop">
                            <rich:dataTable  var="txnItem" value="#{ClearingDeposit.txnDetailList}"
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
                            <rich:dataTable  var="unAuthItem" value="#{ClearingDeposit.txnDetailUnAuthList}"
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
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="btnPnl"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header"><h:graphicImage url="/resources/images/ajax-loader.gif"/></f:facet>
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
                        <h:outputText id="txtAcNo" style="output" value="#{ClearingDeposit.accNo}"/>
                        <h:outputLabel id="lblAcName" styleClass="label" value="Name"/>
                        <h:outputText id="txtAcName" style="output" value="#{ClearingDeposit.accountName}"/>
                        <h:outputLabel id="lblCustomerId" styleClass="label" value="Customer Id"/>
                        <h:outputText id="txtCustomerId" style="output" value="#{ClearingDeposit.custId}"/>
                    </h:panelGrid>

                    <h:panelGrid id="sigViewRow2" columns="6" columnClasses="col1,col3,col3,col3,col3,col3" width="100%" styleClass="row1" style="text-align:left;">
                        <h:outputLabel id="lblOpMode" styleClass="label" value="Operation Mode"/>
                        <h:outputText id="txtOpMode" style="output" value="#{ClearingDeposit.opMode}"/>
                        <h:outputLabel id="lblOpDt" styleClass="label" value="Opening Date"/>
                        <h:outputText id="txtOpDt" style="output" value="#{ClearingDeposit.openDate}"/>
                        <h:outputLabel id="lblPanNo" styleClass="label" value="Pan No"/>
                        <h:outputText id="txtPanNo" style="output" value="#{ClearingDeposit.custPanNo}"/>
                    </h:panelGrid>

                    <h:panelGrid id="sigViewRow3" columns="4" columnClasses="col1,col51,col3,col3" width="100%" styleClass="row2" style="text-align:left;">
                        <h:outputLabel id="lblJtName" styleClass="label" value="Jt.Name"/>
                        <h:outputText id="txtJtName" style="output" value="#{ClearingDeposit.jtName}"/>
                        <h:outputLabel id="lblSigAnnualTrunOver" styleClass="label" value="Annual Turnover"/>
                        <h:outputText id="txtSigAnnualTrunOver" style="output" value="#{ClearingDeposit.annualTurnover}"/>
                    </h:panelGrid>

                    <h:panelGrid id="sigViewRow4" columns="4" columnClasses="col1,col51,col3,col3" width="100%" styleClass="row1" style="text-align:left;">
                        <h:outputLabel id="lblAcInst" styleClass="label" value="A/c Instruction"/>
                        <h:outputText id="txtAcIns" style="output" value="#{ClearingDeposit.accInstruction}"/>
                        <h:outputLabel id="lblProfession" styleClass="label" value="Profession"/>
                        <h:outputText id="txtProfession" style="output" value="#{ClearingDeposit.profession}"/>
                    </h:panelGrid>

                    <h:panelGrid id="custIdPanelGrid" columns="4" columnClasses="col1,col51,col3,col3" width="100%" styleClass="row2" style="text-align:left;">
                        <h:outputLabel id="lblSigRiskCat" styleClass="label" value="Risk Category"/>
                        <h:outputText id="txtSigRiskCat" style="output" value="#{ClearingDeposit.riskCategorization}"/>
                        <h:outputLabel id="lblSigDpLimit" styleClass="label" value="DP Limit"/>
                        <h:outputText id="txtSigDpLimit" style="output" value="#{ClearingDeposit.dpLimit}"/>
                    </h:panelGrid>

                    <h:panelGrid id="Panel" style="width:100%;text-align:center;">
                        <h:panelGroup layout="block" id="sigViewPanel" style="overflow:auto;width:700px;height:300px;text-align:center;background-color:#e8eef7">
                            <a4j:mediaOutput id="signature" element="img" createContent="#{ClearingDeposit.createSignature}" value="#{ClearingDeposit.accNo}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <%--<h:panelGrid columns="1" id="signErrorPanel" style="width:100%;text-align:center;" styleClass="row1">
                        <h:outputLabel id="txtSignError" styleClass="error" value="#{ClearingDeposit.errorMsg}"/>
                    </h:panelGrid>--%>
                    <h:panelGrid id="sigViewFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="sigViewBtnPanel">
                            <a4j:commandButton id="sigViewClose" value="Close" onclick="#{rich:component('sigView')}.hide(); return false;">
                                <%--<rich:toolTip for="sigViewClose" value="Click To close Signature View"></rich:toolTip>--%>
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </a4j:form>
            </rich:modalPanel>

            <rich:modalPanel id="deletePanel" autosized="true" width="250" onshow="#{rich:element('Yes')}.focus()">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" />
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
                                    <a4j:region id="yesBtn">
                                        <a4j:commandButton id="Yes" value="Yes" ajaxSingle="true" action="#{ClearingDeposit.delete}"
                                                           oncomplete="#{rich:component('deletePanel')}.hide();" reRender="taskList,stxtMsg" />
                                    </a4j:region>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton id="No" value="No" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="yesBtn"/>
            <rich:modalPanel id="delWait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header"><h:graphicImage url="/resources/images/ajax-loader.gif"/></f:facet>
            </rich:modalPanel>
            <rich:hotKey id="F4Key"key="F4" handler="showAcDeatil();"/>
        </body>
    </html>
</f:view>
