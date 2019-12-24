<%-- 
    Document   : TermDepositRenewal
    Created on : 4 Nov, 2010, 11:25:27 AM
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
            <title>Term Deposit Renewal</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{TermDepositRenewal.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Term Deposit Renewal"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="labelUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{TermDepositRenewal.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid   columns="1" id="subbodyPanel" style="width:100%;">
                        <h:panelGrid columnClasses="col2,col7,col9" columns="2" id="Panel790" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputText id="stxtMsg" styleClass="error" value="#{TermDepositRenewal.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1,col2,col3,col3,col3,col3" columns="6" id="functionList" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblpfuntion" styleClass="label" value="Function."><font class="error">*</font></h:outputLabel>
                            <h:selectOneListbox size="1" id="ddFunction"  styleClass="ddlist" value="#{TermDepositRenewal.function}" style="width:100px;">  
                                <f:selectItems value="#{TermDepositRenewal.functionList}"/>
                                <a4j:support actionListener="#{TermDepositRenewal.changeFunction()}" event="onblur"  
                                         reRender="txtaccountDetails,btnSave,lblPAcno,ddPAcNo,lblPVNo,ddPRtNo,btnUpdate,Stocktable" limitToList="true" focus="#{TermDepositRenewal.focusId}" />
                            </h:selectOneListbox>
                            <h:outputLabel id="lblPAcno"  styleClass="label" value="Pending Account No"  style="display:#{TermDepositRenewal.inputDisFlag};"/>
                            <h:selectOneListbox id="ddPAcNo" size="1" styleClass="ddlist" value="#{TermDepositRenewal.pendingAcNo}" style="width:100px;display:#{TermDepositRenewal.inputDisFlag};">  
                                <f:selectItems value="#{TermDepositRenewal.pendingAcNoList}"/>
                                <a4j:support actionListener="#{TermDepositRenewal.getUnAuthRtNo}"  event="onblur"  focus="#{TermDepositRenewal.focusId}" reRender="ddPRtNo,stxtMsg"  limitToList="true" />
                            </h:selectOneListbox>
                            <h:outputLabel id="lblPVNo" styleClass="label" value="Pending Receipt No"  style="display:#{TermDepositRenewal.inputDisFlag};"/>
                            <h:selectOneListbox id="ddPRtNo" size="1" styleClass="ddlist" value="#{TermDepositRenewal.pendingRtNo}" style="width:100px;display:#{TermDepositRenewal.inputDisFlag};">  
                                <f:selectItems value="#{TermDepositRenewal.pendingRtNoList}"/>
                                <a4j:support actionListener="#{TermDepositRenewal.getRtDetails()}"  event="onblur" limitToList="true" focus="#{TermDepositRenewal.focusId}"
                                             reRender="stxtMsg,Stocktable,Panel1,Panel2,Panel4,gridPanel1tds,Panel5,Panel7,Panel8,btnSave,Panel18"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <rich:panel header="" style="font-size:50px;" >
                            <h:panelGrid columnClasses="col1,col1" columns="5" id="PanelStartPage" style="width:100%;" styleClass="row1">
                                <h:outputLabel id="lblaccountDetails" styleClass="label"  value="Account Number :" style="font-size:10px;"/>   
                                <h:panelGroup id="groupPanelNo" layout="block">
                                    <h:inputText id="txtaccountDetails" value="#{TermDepositRenewal.acctNo}" maxlength="#{TermDepositRenewal.acNoMaxLen}" styleClass="input" style="width:90px" disabled="#{TermDepositRenewal.acNoDisFlag}">
                                        <a4j:support actionListener="#{TermDepositRenewal.getTableRenewal}"  event="onblur"  focus="selectlink" reRender="lblStAccountNo,Stocktable,stxtStAccountNo,stxtMsg,Panel8,Panel1,Panel2,Panel3,Panel4,Panel5,Panel6,Panel7" />
                                    </h:inputText>
                                </h:panelGroup>
                                <h:outputText id="stxtStAccountNo" styleClass="output" value="#{TermDepositRenewal.stAcctNo}"/>
                                <h:outputLabel id="lblStAccountNo" styleClass="label" style="font-size:12px;color:navy" value="Please Enter Account Number (If You Find As a Single Account Wise ) OR Press =>> (All A/C)  Button For Multiple Account" rendered="#{TermDepositRenewal.lblDisable}"/>
                                <a4j:region id="getAll">
                                    <a4j:commandButton  id="btnUpdate" value="All A/C" actionListener="#{TermDepositRenewal.allAccount}" reRender="mainPanel,txtaccountDetails,stxtMsg,Stocktable,Panel1,Panel2,Panel3,Panel4,Panel5,Panel6,Panel7,Panel8,txtaccountDetails" focus="selectlink" disabled="#{TermDepositRenewal.btnAllDisFlag}"/>
                                </a4j:region>
                            </h:panelGrid>
                        </rich:panel>
                        <h:panelGrid columnClasses="vtop" columns="1" id="Stocktable" width="100%" styleClass="row2" style="height:168px;">
                            <a4j:region>
                                <rich:dataTable value="#{TermDepositRenewal.tdTable}" var="item" rowClasses="row1, row2" id="taskList" rows="6" columnsWidth="100" rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="13"></rich:column>
                                            <rich:column breakBefore="true" ><h:outputText value="Account No"/></rich:column>
                                            <rich:column ><h:outputText value="Reciept No" /></rich:column>
                                            <rich:column ><h:outputText value="Prin Amt" /></rich:column>
                                            <rich:column ><h:outputText value="Td Date " /></rich:column>
                                            <rich:column ><h:outputText value="Maturity Date" /></rich:column>
                                            <rich:column ><h:outputText value="Int Opt" /></rich:column>
                                            <rich:column ><h:outputText value="ROI" /></rich:column>
                                            <rich:column ><h:outputText value="Renewal Period " /></rich:column>
                                            <rich:column ><h:outputText value="Renew" /></rich:column>
                                            <rich:column ><h:outputText value="Control No." /></rich:column>
                                            <rich:column ><h:outputText value="RT No." /></rich:column>
                                            <rich:column ><h:outputText value="Next Pay Date" /></rich:column>
                                            <rich:column ><h:outputText value="select" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{item.acno}" /></rich:column>
                                    <rich:column><h:outputText value="#{item.recieptNo}" /></rich:column>
                                    <rich:column><h:outputText value="#{item.printamt}" /></rich:column>
                                    <rich:column><h:outputText value="#{item.tdDate}" /></rich:column>
                                    <rich:column><h:outputText value="#{item.matDt}" /></rich:column>
                                    <rich:column><h:outputText value="#{item.intopt}" /></rich:column>
                                    <rich:column><h:outputText value="#{item.roi}" /></rich:column>
                                    <rich:column><h:outputText value="#{item.renewalPeriod}" /></rich:column>
                                    <rich:column><h:outputText value="#{item.renew}" /></rich:column>
                                    <rich:column><h:outputText value="#{item.controlNo}"   /></rich:column>
                                    <rich:column><h:outputText value="#{item.rtNo}"   /></rich:column>
                                    <rich:column><h:outputText value="#{item.payDate}"   /></rich:column>
                                    <rich:column>
                                        <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{TermDepositRenewal.select}" focus="ddRenewalAccount"
                                                         reRender="Panel1,Panel2,Panel4,Panel5,Panel7,Panel8,stxtMsg,btnSave,gridPanel1tds,Panel18" >
                                            <h:graphicImage value="/resources/images/passed.gif" style="border:0" />
                                            <f:setPropertyActionListener value="#{item}" target="#{TermDepositRenewal.currentItem}"/>
                                            <f:setPropertyActionListener value="#{row}" target="#{TermDepositRenewal.currentRow}" />
                                        </a4j:commandLink>
                                    </rich:column>
                                </rich:dataTable>
                                <rich:datascroller id="scroller" align="left" for="taskList" maxPages="10" />
                            </a4j:region>

                            <rich:modalPanel id="savePanel" autosized="true" width="250" onshow="#{rich:element('yes')}.focus()">
                                <f:facet name="header">
                                    <h:outputText value="Confirmation DialogBox" />
                                </f:facet>
                                <h:form>
                                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                        <tbody>
                                            <tr style="height:40px">
                                                <td colspan="2">
                                                    <h:outputText value="Are You Sure?"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center" width="50%">
                                                    <a4j:region id="yesBtn">
                                                        <a4j:commandButton id="yes" value="Yes" ajaxSingle="true" action="#{TermDepositRenewal.saveBtnAction}"
                                                                           onclick="#{rich:component('savePanel')}.hide();" oncomplete="if(#{TermDepositRenewal.batchNo==''}){#{rich:component('custInfoViewPanel')}.hide();} else{#{rich:component('custInfoViewPanel')}.show();}" reRender="stxtMsg,subbodyPanel,gridPanel2,gridPanel2,Panel3,Panel4,Panel5,Panel6,stxtAccNo,stxtrcptNo,stxtRtNo,stxtwefDt,stxtMatDt,stxtRenewAmt,stxtMatAmt,stxtRoi,stxtRemInt,stxtBatchNo,functionList" />
                                                    </a4j:region>
                                                </td>
                                                <td align="center" width="50%">
                                                    <a4j:commandButton id="No" value="No" onclick="#{rich:component('savePanel')}.hide();return false;" />
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </h:form>
                            </rich:modalPanel>
                        </h:panelGrid>

                        <h:panelGrid columnClasses="col3,col2,col3,col3,col3,col3" columns="6" id="Panel1" style="width:100%;" styleClass="row1">
                            <h:outputLabel id="lblAccountNo" styleClass="label"  value="Account No"/>
                            <h:panelGroup id="groupPanelAcno" layout="block">
                            <h:outputText id="stxtAccountNo" styleClass="output" value="#{TermDepositRenewal.accountNo}" />
                            <h:outputText id="stxtCustName" styleClass="output" value="#{TermDepositRenewal.custName}" style="color:green" />
                            </h:panelGroup>
                            <h:outputLabel id="lblRenewedTdDate" styleClass="label"  value="Renewed TD Date"/>
                            <h:outputText id="stxtRenewedTdDate" styleClass="output" value="#{TermDepositRenewal.renewedTdDate}" />
                            <h:outputLabel id="lblSTRecieptNo" styleClass="label"  value="Reciept No"/>
                            <h:outputText id="stxtSTRecieptNo" styleClass="output" value="#{TermDepositRenewal.recieptNo}" />

                        </h:panelGrid>

                        <h:panelGrid columnClasses="col3,col2,col3,col3,col3,col3" columns="6" id="Panel2" style="width:100%;" styleClass="row2">
                            <h:outputLabel id="lblRenewedMatDate" value="Renewed Maturity Date" styleClass="label" />
                            <h:outputText id="stxtRenewedMatDate" styleClass="output" value="#{TermDepositRenewal.renewedMatDate}"/>
                            <h:outputLabel id="lblTdsDeductedForLastFinyear" styleClass="label"  value="TDS Deducted For Last Fin year"/>
                            <h:outputText id="stxtTdsDeductedForLastFinyear" styleClass="output" value="#{TermDepositRenewal.tdsDeductedForLastFinyear}" />
                            <h:outputLabel id="lblRenewedMatAmt" value="Renewed Maturity Amount" styleClass="label" />
                            <h:inputText id="txtRenewedMatAmt" styleClass="input" style="width:90px" value="#{TermDepositRenewal.renewedMatAmt}" disabled="true">
                                <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                            </h:inputText>
                        </h:panelGrid>

                        <h:panelGrid columnClasses="col3,col2,col3,col3,col3,col3" columns="6" id="Panel4" style="width:100%;" styleClass="row1">
                            <h:outputLabel id="lblTdsDeducted" styleClass="label"  value="TDS Deducted"/>
                            <h:outputText id="stxtTdsDeducted" styleClass="output" value="#{TermDepositRenewal.tdsDeducted}" />
                            <h:outputLabel id="lblRoi" value="Rate Of Interest" styleClass="label"/>
                            <h:inputText id="txtRoi" style="width:90px" value="#{TermDepositRenewal.roi}"  disabled="#{TermDepositRenewal.disableAll}" styleClass="input">
                                <a4j:support event="onblur" actionListener="#{TermDepositRenewal.matAmt}"   reRender="txtRenewedMatAmt,stxtMsg"/>
                            </h:inputText>
                            <h:outputLabel id="lblTdsToBeDeducted" styleClass="label"  value="TDS To Be Deducted"/>
                            <h:inputText id="txtTdsToBeDeducted" style="width:90px" value="#{TermDepositRenewal.tdsToBeDeducted}" styleClass="input" disabled="true"/>
                        </h:panelGrid>
                            
                    <h:panelGrid columnClasses="col3,col2,col3,col3,col3,col3" columns="6" id="gridPanel1tds" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblTDStobeDeductedCust" styleClass="label" value="TDS to be Deducted For Closed Accounts / Receipts "/>
                        <h:outputText id="stxtTDStobeDeductedCust" styleClass="output"  value="#{TermDepositRenewal.closeActTdsToBeDeducted}"/>
                        <h:outputLabel id="lblTDSDeductedCust" styleClass="label" value="TDS Deducted For Closed Accounts / Receipts"/>
                        <h:outputText id="stxtTDSDeductedCust" styleClass="output"  value="#{TermDepositRenewal.closeActTdsDeducted}"/>
                        <h:outputLabel id="lblCloseActInt" styleClass="label" value="Interest For Closed Accounts / Receipts"/>
                        <h:outputText id="stxtCloseActInt" styleClass="output"  value="#{TermDepositRenewal.closeActIntFinYear}"/>
                    </h:panelGrid>  
                        
                        <h:panelGrid columnClasses="col3,col2,col3,col3,col3,col3" columns="6" id="Panel5" style="width:100%;" styleClass="row2">
                            <h:outputLabel id="lblRenewalAccount" value="Renewal Account" styleClass="label"/>
                            <h:selectOneListbox id="ddRenewalAccount" styleClass="ddlist" size="1" style="width: 70px" value="#{TermDepositRenewal.renewalAccount}" disabled="#{TermDepositRenewal.disableAll}">
                                <f:selectItems value="#{TermDepositRenewal.ddRenewalAcct}"/>
                                <a4j:support actionListener="#{TermDepositRenewal.ddRenewalAcctprocessValueChange}"  event="onblur" reRender="Panel7,Panel6,ddSeries" focus="txtRenewalAmount"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblRenewalAmount" styleClass="label"  value="Renewal Amount"/>
                            <h:inputText id="txtRenewalAmount" style="width:90px" value="#{TermDepositRenewal.renewalAmount}" styleClass="input" disabled="#{TermDepositRenewal.disableAll}">
                                <a4j:support event="onblur" actionListener="#{TermDepositRenewal.matAmtRef}"   reRender="txtRenewedMatAmt,stxtMsg,txtRoi"/>                                
                            </h:inputText>
                            <h:outputLabel id="lblRecieptNo" value="Reciept No" styleClass="label"/>
                            <h:selectOneListbox id="ddRecieptNo" styleClass="ddlist" size="1" style="width: 70px" value="#{TermDepositRenewal.ddRecieptNo}" disabled="#{TermDepositRenewal.recieptNoDisabled}" >
                                <f:selectItems value="#{TermDepositRenewal.ddRecieptno}"/>
                            </h:selectOneListbox>
                        </h:panelGrid>

                        <h:panelGrid columnClasses="col3,col2,col3,col3,col3,col3" columns="6" id="Panel7" style="width:100%;" styleClass="row1">
                            <h:outputLabel id="lblInterestOption" styleClass="label"  value="Interest Option"/>
                            <h:selectOneListbox id="ddInterestOption" styleClass="ddlist" size="1" style="width: 90px"  value="#{TermDepositRenewal.interestOption}" disabled="#{TermDepositRenewal.disableAll}">
                                <f:selectItems value="#{TermDepositRenewal.ddInterestOption}" />
                            </h:selectOneListbox>
                            <h:outputLabel id="lblSeries" value="Series" styleClass="label" rendered="#{TermDepositRenewal.seriesVisible}" />
                            <h:selectOneListbox id="ddSeries" styleClass="ddlist" size="1" style="width: 70px" value="#{TermDepositRenewal.series}" rendered="#{TermDepositRenewal.seriesVisible}" disabled="#{TermDepositRenewal.disableAll}">
                                <f:selectItems value="#{TermDepositRenewal.ddSeries}"/>
                            </h:selectOneListbox>
                            <h:outputText rendered="#{TermDepositRenewal.stxVisible}"/>
                            <h:outputText rendered="#{TermDepositRenewal.stxVisible}"/>
                            <h:outputLabel id="lblApplicableRoi" value="Applicable ROI" styleClass="label"/>
                            <h:selectOneListbox id="ddApplicableRoi" styleClass="ddlist" size="1" style="width: 70px" value="#{TermDepositRenewal.applicableRoi}" disabled="#{TermDepositRenewal.disableAll}" >
                                <f:selectItems value="#{TermDepositRenewal.ddApplicableRoi}"/>
                            </h:selectOneListbox>
                        </h:panelGrid>

                        <h:panelGrid columnClasses="col3,col2,col3,col3,col3,col3" columns="6" id="Panel8" style="width:100%;" styleClass="row2">
                            <h:outputLabel id="lblYear" styleClass="label"  value="Year"/>
                            <h:inputText id="txtYear" style="width: 40px" value="#{TermDepositRenewal.year}" disabled="#{TermDepositRenewal.disableAll}"styleClass="input" >
                                <a4j:support actionListener="#{TermDepositRenewal.rateOfInt}"  event="onblur" reRender="Panel4,Panel3,Panel2,stxtMsg"/>
                            </h:inputText>
                            <h:outputLabel id="lblMonth" value="Month" styleClass="label" />
                            <h:inputText id="txtMonth" style="width: 40px" value="#{TermDepositRenewal.month}" disabled="#{TermDepositRenewal.disableAll}" styleClass="input">
                                <a4j:support actionListener="#{TermDepositRenewal.rateOfInt}"  event="onblur" reRender="Panel2,stxtMsg"/>
                            </h:inputText>
                            <h:outputLabel id="lblDays" value="Days" styleClass="label" />
                            <h:inputText id="txtDays" style="width: 40px" value="#{TermDepositRenewal.days}"  disabled="#{TermDepositRenewal.disableAll}" styleClass="input">
                                <a4j:support actionListener="#{TermDepositRenewal.rateOfInt}"  event="onblur" reRender="Panel3,Panel2,Panel4,stxtMsg,btnSave" focus="btnSave"/>
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col2,col3,col3,col3,col3" columns="6" id="Panel18" styleClass="row1">
                            <h:outputLabel id="BalanInt" styleClass="label"  value="Remaining Int"/>
                            <h:outputText id="stxtBalInt" styleClass="output" value="#{TermDepositRenewal.remBalInt}"/>
                            <h:outputLabel/>
                            <h:outputLabel/>
                            <h:outputLabel/>
                            <h:outputLabel/>
                        </h:panelGrid>
                    </h:panelGrid>

                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">  
                        <h:panelGroup id="BtnPanel">
                            <a4j:region id="saveRegion">
                                <a4j:commandButton id="btnSave" value="#{TermDepositRenewal.btnLabel}" oncomplete="#{rich:component('savePanel')}.show()" disabled="#{TermDepositRenewal.savedis}" reRender="stxtMsg,functionList,subbodyPanel,gridPanel2,Panel3,Panel4,Panel5,Panel6,functionList"/>
                                <a4j:commandButton id="btnRefresh" value="Refresh" action="#{TermDepositRenewal.clearAction}" reRender="stxtMsg,Stocktable,stxtStAccountNo,Panel1,Panel2,Panel3,Panel4,Panel5,Panel6,Panel7,Panel8,txtaccountDetails,lblPAcno,ddPAcNo,lblPVNo,ddPRtNo,functionList,Panel18"/>
                            </a4j:region>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{TermDepositRenewal.btnExit}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="saveRegion"/>
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
                <a4j:status onstart="#{rich:component('waitAll')}.show()" onstop="#{rich:component('waitAll')}.hide()" for="getAll"/>
                <rich:modalPanel id="waitAll" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
            </a4j:form>
            <rich:modalPanel id="custInfoViewPanel" autosized="true" width="500"style="text-align:center;">
                <f:facet name="header"/>                
                <h:form id="form2">
                    <h:panelGrid id="mainPanel2" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%" >
                        <h:panelGrid id="grid1" columns="2" columnClasses="col8,col8" styleClass="row1" style="width:100%;">
                            <h:outputLabel id="lblAccountNo" styleClass="label" value="Account No."/>
                            <h:outputText id="stxtAccNo" styleClass="output" style="width:50%" value="#{TermDepositRenewal.acNo}">
                            </h:outputText>
                        </h:panelGrid>
                        <h:panelGrid id="grid2" columns="2" columnClasses="col8,col8" styleClass="row2" style="width:100%;">
                            <h:outputLabel id="rcptNo" styleClass="label" value="Receipt No."/>
                            <h:outputText id="stxtrcptNo" styleClass="output" style="width:50%" value="#{TermDepositRenewal.newReceiptNo}">
                            </h:outputText>
                        </h:panelGrid>
                        <h:panelGrid id="grid3" columns="2" columnClasses="col8,col8" styleClass="row1" style="width:100%;">
                            <h:outputLabel id="rtNo" styleClass="label" value="R.T.No."/>
                            <h:outputText id="stxtRtNo" styleClass="output" style="width:50%" value="#{TermDepositRenewal.newVoucherNo}">
                            </h:outputText>
                        </h:panelGrid>
                        <h:panelGrid id="grid4" columns="2" columnClasses="col8,col8" styleClass="row2" style="width:100%;">
                            <h:outputLabel id="wefDt" styleClass="label" value="W.e.f. Date"/>
                            <h:outputText id="stxtwefDt" styleClass="output" style="width:50%" value="#{TermDepositRenewal.renewedTdDate}">
                            </h:outputText>
                        </h:panelGrid>
                        <h:panelGrid id="grid5" columns="2" columnClasses="col8,col8" styleClass="row1" style="width:100%;">
                            <h:outputLabel id="matDt" styleClass="label" value="Maturity Date"/>
                            <h:outputText id="stxtMatDt" styleClass="output" style="width:50%" value="#{TermDepositRenewal.renewedMatDate}">
                            </h:outputText>
                        </h:panelGrid>
                        <h:panelGrid id="grid6" columns="2" columnClasses="col8,col8" styleClass="row2" style="width:100%;">
                            <h:outputLabel id="renewAmount" styleClass="label" value="Renewal Amount"/>
                            <h:outputText id="stxtRenewAmt" styleClass="output" style="width:50%" value="#{TermDepositRenewal.renewalAmount}">
                            </h:outputText>
                        </h:panelGrid>
                        <h:panelGrid id="grid7" columns="2" columnClasses="col8,col8" styleClass="row1" style="width:100%;">
                            <h:outputLabel id="matAmount" styleClass="label" value="Maturity Amount"/>
                            <h:outputText id="stxtMatAmt" styleClass="output" style="width:50%" value="#{TermDepositRenewal.renewedMatAmt}">
                            </h:outputText>
                        </h:panelGrid>
                        <h:panelGrid id="grid8" columns="2" columnClasses="col8,col8" styleClass="row2" style="width:100%;">
                            <h:outputLabel id="roi" styleClass="label" value="ROI"/>
                            <h:outputText id="stxtRoi" styleClass="output" style="width:50%" value="#{TermDepositRenewal.roi}">
                            </h:outputText>
                        </h:panelGrid>
                        <h:panelGrid id="grid9" columns="2" columnClasses="col8,col8" styleClass="row1" style="width:100%;">
                            <h:outputLabel id="remInt" styleClass="label" value="Remaining Interest"/>
                            <h:outputText id="stxtRemInt" styleClass="output" style="width:50%" value="#{TermDepositRenewal.remainInt}">
                            </h:outputText>
                        </h:panelGrid>
                        <h:panelGrid id="grid10" columns="2" columnClasses="col8,col8" styleClass="row2" style="width:100%;">
                            <h:outputLabel id="batchNo" styleClass="label" value="Generated Batch No."/>
                            <h:outputText id="stxtBatchNo" styleClass="output" style="width:50%" value="#{TermDepositRenewal.batchNo}">
                            </h:outputText>
                        </h:panelGrid>
                        <h:panelGrid columns="1" id="btnPanel"
                                     style="width:100%;text-align:center;" styleClass="footer">
                            <a4j:commandButton id="btnClose"value="Close" ajaxSingle="true" action="#{TermDepositRenewal.clearActionPopUp}" oncomplete="#{rich:component('custInfoViewPanel')}.hide();return false;" reRender="subbodyPanel,gridPanel2,gridPanel2,Panel3,Panel4,Panel5,Panel6,functionList"/>
                        </h:panelGrid>
                    </h:panelGrid>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>



