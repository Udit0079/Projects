<%--
    Document   : TDPayment
    Created on : May 15, 2010, 10:45:54 AM
    Author     : jitendra chaudhary
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
            <title>TD Payment</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{TDPayment.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="TD Payment (Maturity/PreMature) "/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{TDPayment.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel001" style="width:100%;text-align:center;" styleClass="row2" width="100%">
                        <h:outputText id="stxtMsg" styleClass="error" value="#{TDPayment.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col2,col3,col3,col3,col3" columns="6" id="functionList" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblpfuntion" styleClass="label" value="Function."><font class="error">*</font></h:outputLabel>
                        <h:selectOneListbox size="1" id="ddFunction"  styleClass="ddlist" value="#{TDPayment.function}" style="width:100px;">  
                            <f:selectItems value="#{TDPayment.functionList}"/>
                            <a4j:support actionListener="#{TDPayment.changeFunction()}" event="onblur"  
                                         reRender="txtAcno,lblPAcno,ddPAcNo,lblPVNo,ddPRtNo,btnCompleted,stxtMsg,taskList,scrollerId,stxtAcno" limitToList="true" focus="#{TDPayment.focusId}" />
                        </h:selectOneListbox>
                        <h:outputLabel id="lblPAcno"  styleClass="label" value="Pending Account No"  style="display:#{TDPayment.inputDisFlag};"/>
                        <h:selectOneListbox id="ddPAcNo" size="1" styleClass="ddlist" value="#{TDPayment.pendingAcNo}" style="width:100px;display:#{TDPayment.inputDisFlag};">  
                            <f:selectItems value="#{TDPayment.pendingAcNoList}"/>
                            <a4j:support actionListener="#{TDPayment.getUnAuthRtNo}"  event="onblur"  focus="#{TDPayment.focusId}" reRender="ddPRtNo,stxtMsg"  limitToList="true" />
                        </h:selectOneListbox>
                        <h:outputLabel id="lblPVNo" styleClass="label" value="Pending Voucher No"  style="display:#{TDPayment.inputDisFlag};"/>
                        <h:selectOneListbox id="ddPRtNo" size="1" styleClass="ddlist" value="#{TDPayment.pendingRtNo}" style="width:100px;display:#{TDPayment.inputDisFlag};">  
                            <f:selectItems value="#{TDPayment.pendingRtNoList}"/>
                            <a4j:support actionListener="#{TDPayment.getRtDetails()}"  event="onblur" limitToList="true" focus="#{TDPayment.focusId}"
                                         reRender="gridPanel15,gridPanel16,gridPanel1t,gridPanel17,gridPanel1ht7,gridPanel1kds,gridPaneler,gridPanel1k,footerPanel,stxtMsg,gridPanel1tds"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col2,col3,col3,col3,col3" columns="8" id="gridPanel15" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblAcno" styleClass="label" value="Account No."><font class="error">*</font></h:outputLabel>
                        <h:panelGroup layout="block">
                            <h:inputText id="txtAcno" styleClass="input" value="#{TDPayment.oldAcNo}" tabindex="2" maxlength="#{TDPayment.acNoMaxLen}" style="width:100px;" disabled="#{TDPayment.disableFlag}">  
                                <a4j:support actionListener="#{TDPayment.getAccountNoInfo}"  event="onblur" limitToList="true"
                                         reRender="txtRtNo,stxtMaturityDate,stxtInterestPaid,stxtActualTotalInterest,stxtRemainingInterest,
                                         stxtTDSDeductedLastFinYear,stxtNetContractedROI,stxtTDSDeducted,stxtTDStobeDeducted,stxtNetAmount,
                                         txtPenaltyApplication,stxtInterestOption,stxtContractedROI,stxtNoActiveDays,stxtCurrentStatus,
                                         txtApplicableRate,stxtIssuedDate,stxtPrincipleAmount,stxtJtUgName,stxtName,stxtOperationMode,gridPaneler,
                                         gridPanele,stxtMsg,stxtAcno,stxtTDStobeDeductedCust,stxtTDSDeductedCust,stxtCloseActInt"/>
                            </h:inputText>
                            <h:outputText id="stxtAcno" styleClass="output" value="#{TDPayment.fullAccNo}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblName" styleClass="label" value="Name"  />
                        <h:outputText id="stxtName" styleClass="output" value="#{TDPayment.custName}"/>
                        <h:outputLabel id="lblJtUgName" styleClass="label" value="Jt / Ug Name"  />
                        <h:outputText id="stxtJtUgName" styleClass="output" value="#{TDPayment.jtUgName}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col2,col3,col3,col3,col3" columns="8" id="gridPanel16" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblOperationMode" styleClass="label" value="Operation Mode"  />
                        <h:outputText id="stxtOperationMode" styleClass="output" value="#{TDPayment.operationMode}"/>
                        <h:outputLabel id="lblCurrentStatus" styleClass="label" value="Current Status" />
                        <h:outputText id="stxtCurrentStatus" styleClass="output"  value="#{TDPayment.currentStatus}"/>
                        <h:outputLabel id="lblIssuedDate" styleClass="label" value="Issued Date" />
                        <h:outputText id="stxtIssuedDate" styleClass="output"  value="#{TDPayment.issueDt}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col2,col3,col3,col3,col3" columns="8" id="gridPanel1t" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblMaturityDate" styleClass="label" value="Maturity Date" />
                        <h:outputText id="stxtMaturityDate" styleClass="output"  value="#{TDPayment.matDt}"/>
                        <h:outputLabel id="lblPrincipleAmount" styleClass="label" value="Principle Amount" />
                        <h:outputText id="stxtPrincipleAmount" styleClass="output"  value="#{TDPayment.prinAmount}" >
                            <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                        </h:outputText>
                        <h:outputLabel id="lblInterestPaid" styleClass="label" value="#{TDPayment.intLbl}" />
                        <h:outputText id="stxtInterestPaid" styleClass="output"  value="#{TDPayment.interestPaid}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col2,col3,col3,col3,col3" columns="8" id="gridPanel17" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblRemainingInterest" styleClass="label" value="Remaining Interest" />
                        <h:outputText id="stxtRemainingInterest" styleClass="output" value="#{TDPayment.remainingInterest}" />
                        <h:outputLabel id="lblContractedROI" styleClass="label" value="Contracted ROI" />
                        <h:panelGroup id="tdcon">
                            <h:outputText id="stxtContractedROI" styleClass="output" value="#{TDPayment.contractedRoi}" />
                            <h:outputLabel id="lblConper" styleClass="label" value="%" />
                        </h:panelGroup>
                        <h:outputLabel id="lblInterestOption" styleClass="label" value="Interest Option" />
                        <h:outputText id="stxtInterestOption" styleClass="output"  value="#{TDPayment.interestOption}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col2,col3,col3,col3,col3" columns="8" id="gridPanel1ht7" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblRtNo" styleClass="label" value="R.T. No." />
                        <h:inputText id="txtRtNo" styleClass="input"style="width:70px" value="#{TDPayment.rtNo}" disabled="#{TDPayment.disableFlag}">
                        </h:inputText>
                        <h:outputLabel id="lblActualTotalInterest" styleClass="label" value="Actual Total Interest" />
                        <h:outputText id="stxtActualTotalInterest" styleClass="output"  value="#{TDPayment.actualTotalInterest}"/>
                        <h:outputLabel id="lblNetAmount" styleClass="label" value="Net Amount" />
                        <h:outputText id="stxtNetAmount" styleClass="output"  value="#{TDPayment.netAmount}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col2,col3,col3,col3,col3" columns="8" id="gridPanel1kds" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblTDStobeDeducted" styleClass="label" value="TDS to be Deducted"/>
                        <h:outputText id="stxtTDStobeDeducted" styleClass="output"  value="#{TDPayment.tdsToBeDeducted}"/>
                        <h:outputLabel id="lblTDSDeducted" styleClass="label" value="TDS Deducted"/>
                        <h:outputText id="stxtTDSDeducted" styleClass="output"  value="#{TDPayment.tdsDeducted}"/>
                        <h:outputLabel id="lblTDSDeductedLastFinYear" styleClass="label" value="TDS Deducted For Last Fin. Year"/>
                        <h:outputText id="stxtTDSDeductedLastFinYear" styleClass="output"  value="#{TDPayment.deductForLastFinalFear}"/>
                    </h:panelGrid>
                        
                    <h:panelGrid columnClasses="col3,col2,col3,col3,col3,col3" columns="6" id="gridPanel1tds" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblTDStobeDeductedCust" styleClass="label" value="TDS to be Deducted For Closed Accounts / Receipts/Un-Recover "/>
                        <h:outputText id="stxtTDStobeDeductedCust" styleClass="output"  value="#{TDPayment.closeActTdsToBeDeducted}"/>
                        <h:outputLabel id="lblTDSDeductedCust" styleClass="label" value="TDS Deducted For Closed Accounts / Receipts"/>
                        <h:outputText id="stxtTDSDeductedCust" styleClass="output"  value="#{TDPayment.closeActTdsDeducted}"/>
                        <h:outputLabel id="lblCloseActInt" styleClass="label" value="Interest For Closed Accounts / Receipts"/>
                        <h:outputText id="stxtCloseActInt" styleClass="output"  value="#{TDPayment.closeActIntFinYear}"/>
                    </h:panelGrid>  
                        
                    <h:panelGrid columnClasses="col3,col2,col3,col3,col3,col3" columns="8" id="gridPaneler" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblNoActiveDays" styleClass="label" value="No. Of Active Days(Before Premature)" rendered="#{TDPayment.noOfActiveDays > 0}"/>
                        <h:outputText id="stxtNoActiveDays" styleClass="output"  value="#{TDPayment.noOfActiveDays}"  rendered="#{TDPayment.noOfActiveDays > 0}"/>
                        <h:outputLabel id="lblPreviousBalance" styleClass="label" value="Previous Balance in Account" rendered="#{TDPayment.flag =='True'}"/>
                        <h:outputText id="stxtPreviousBalanceAccount" styleClass="output"  value="#{TDPayment.previousBalanceAc}"  rendered="#{TDPayment.flag =='True'}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="1" id="gridPanel14y" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblPreMatureDetails" styleClass="label" value="PreMature Details :" />
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col2,col3,col3,col3,col3" columns="8" id="gridPanel1k" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblApplicableRate" styleClass="label" value="Applicable Rate"/>
                        <h:inputText id="txtApplicableRate" styleClass="input"style="width:70px"  value="#{TDPayment.applicableRate}" disabled="#{TDPayment.penalityFlag}" maxlength="10">
                            <a4j:support event="onblur" actionListener="#{TDPayment.checkApplicableRate}" reRender="stxtMsg"/>
                        </h:inputText>
                        <h:outputLabel id="lblPenaltyApplication" styleClass="label" value="Penalty Application" />
                        <h:panelGroup id="tdPenali">
                            <h:inputText id="txtPenaltyApplication" styleClass="input"style="width:70px" value="#{TDPayment.penalityApplicable}" disabled="#{TDPayment.penalityFlag}" maxlength="10">
                                <a4j:support actionListener="#{TDPayment.getPenalityApplication}"  event="onblur"  
                                             reRender="stxtPreviousBalanceAccount,stxtNetAmount,stxtMsg,txtRtNo,stxtMaturityDate,stxtInterestPaid,stxtActualTotalInterest,
                                             stxtRemainingInterest,stxtTDSDeductedLastFinYear,stxtNetContractedROI,stxtTDSDeducted,stxtTDStobeDeducted,stxtNetAmount,
                                             txtPenaltyApplication,stxtInterestOption,stxtContractedROI,stxtNoActiveDays,stxtCurrentStatus,txtApplicableRate,stxtIssuedDate,
                                             stxtPrincipleAmount,stxtJtUgName,stxtName,stxtOperationMode,gridPaneler,gridPanele,stxtTDStobeDeductedCust,stxtTDSDeductedCust,stxtCloseActInt" 
                                             limitToList="true" />

                            </h:inputText>
                            <h:outputLabel id="lblPenPer" styleClass="label" value="%" />
                        </h:panelGroup>
                        <h:outputLabel id="lblNetContractedROI" styleClass="label" value="Net Contracted ROI"/>
                        <h:panelGroup id="tdDec">
                            <h:outputText id="stxtNetContractedROI" styleClass="output"  value="#{TDPayment.netContractedRoi}"/>
                            <h:outputLabel id="lblTDRoi" styleClass="label" value="%"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="2" id="gridPanele" width="100%" styleClass="row2" style="height:150px;">
                        <a4j:region>
                            <rich:dataTable value="#{TDPayment.payment}" var="dataItem" id="taskList" rows="4" columnsWidth="100" rowKeyVar="row" rowClasses="gridrow1,gridrow2"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="11"><h:outputText value="TD Payment Information" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Voucher No." /></rich:column>
                                        <rich:column><h:outputText value="Receipt No" /></rich:column>
                                        <rich:column><h:outputText value="Seq. No" /></rich:column>
                                        <rich:column><h:outputText value="Print Amt" /></rich:column>
                                        <rich:column><h:outputText value="TD MadeDate" /></rich:column>
                                        <rich:column><h:outputText value="FD Date" /></rich:column>
                                        <rich:column><h:outputText value="MAT Date" /></rich:column>
                                        <rich:column><h:outputText value="Int Opt" /></rich:column>
                                        <rich:column><h:outputText value="ROI" /></rich:column>
                                        <rich:column><h:outputText value="Status" /></rich:column>
                                        <rich:column><h:outputText value="Action" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.voucherNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.receiptNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.seqNo}" /></rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem.prinAmt}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column><h:outputText value="#{dataItem.tdMadeDate}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.fdDate}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.matDate}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.intOpt}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.roi}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.status}"/></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink ajaxSingle="true" id="selectlink" oncomplete="#{rich:component('rtNoPanel')}.show()">
                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{TDPayment.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{TDPayment.currentRow}"/>
                                    </a4j:commandLink>

                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller id="scrollerId" align="left" for="taskList" maxPages="20"/>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnCompleted" value="#{TDPayment.btnLabel}"  action="#{TDPayment.saveAction}" reRender="mainPanel" focus="ddFunction"/>
                            <a4j:commandButton id="btnReset" value="Reset" action="#{TDPayment.pageRefresh}" reRender="mainPanel" focus="ddFunction"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{TDPayment.btnExit_action}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
            </a4j:form>
            <rich:modalPanel id="rtNoPanel" autosized="true" width="200">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/images/modal/close.png" styleClass="hidelink" id="hidelink21" />
                        <rich:componentControl for="rtNoPanel" attachTo="hidelink21" operation="hide" event="onclick" />
                    </h:panelGroup>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure, You want to get the Rt No.information?" />
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" ajaxSingle="true" action="#{TDPayment.getRtInfo}" oncomplete="#{rich:component('rtNoPanel')}.hide();" focus="#{TDPayment.focusId}"
                                                       reRender="txtRtNo,stxtMaturityDate,stxtInterestPaid,stxtActualTotalInterest,stxtRemainingInterest,
                                                       stxtTDSDeductedLastFinYear,stxtNetContractedROI,stxtTDSDeducted,stxtTDStobeDeducted,stxtNetAmount,
                                                       txtPenaltyApplication,stxtInterestOption,stxtContractedROI,stxtNoActiveDays,stxtCurrentStatus,
                                                       txtApplicableRate,stxtIssuedDate,stxtPrincipleAmount,stxtJtUgName,stxtName,stxtOperationMode,gridPaneler,
                                                       gridPanele,stxtMsg,lblInterestPaid,stxtTDStobeDeductedCust,stxtTDSDeductedCust,stxtCloseActInt" />
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" onclick="#{rich:component('rtNoPanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="completePanel" autosized="true" width="200">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/images/modal/close.png" styleClass="hidelink" id="hidelink3" />
                        <rich:componentControl for="completePanel" attachTo="hidelink3" operation="hide" event="onclick" />
                    </h:panelGroup>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="To close the Deposit press Yes and to cancel the transaction press No" />
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" ajaxSingle="true" action="#{TDPayment.completedButton}" 
                                                       oncomplete="#{rich:component('completePanel')}.hide();" reRender="txtRtNo,stxtMaturityDate,stxtInterestPaid,stxtActualTotalInterest,stxtRemainingInterest,stxtTDSDeductedLastFinYear,stxtNetContractedROI,stxtTDSDeducted,stxtTDStobeDeducted,stxtNetAmount,txtPenaltyApplication,stxtInterestOption,stxtContractedROI,stxtNoActiveDays,stxtCurrentStatus,txtApplicableRate,stxtIssuedDate,stxtPrincipleAmount,stxtJtUgName,stxtName,stxtOperationMode,gridPaneler,txtAcno,ddTdAcno,stxtAcno,gridPanele,stxtMsg" />
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" onclick="#{rich:component('completePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>   
        </body>
    </html>
</f:view>
