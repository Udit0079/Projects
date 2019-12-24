<%--
    Document   : NewTdRecieptCreation
    Created on : 30 Oct, 2010, 4:10:53 PM
    Author     : Nishant
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
            <title>New Term Deposit Reciept Creation</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="txnForm">
                <h:panelGrid bgcolor="#fff" columns="1" id="outerPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{NewTdRecieptCreation.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="New Term Deposit Reciept Creation"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{NewTdRecieptCreation.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                        <h:panelGrid columnClasses="col2,col7" columns="2" id="msgRow" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputLabel id="lblMsg" styleClass="error" value="#{NewTdRecieptCreation.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col2,col3,col3,col3,col3" columns="6" id="Row1" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblFunction" styleClass="label" value="Function"></h:outputLabel>
                            <h:selectOneListbox id="ddFunction" value="#{NewTdRecieptCreation.function}" styleClass="ddlist"  size="1" tabindex="1" style="width:90px">
                                <f:selectItems value="#{NewTdRecieptCreation.functionList}" />
                                <a4j:support actionListener="#{NewTdRecieptCreation.changeFunction()}"  event="onblur" focus="#{NewTdRecieptCreation.focusId}" 
                                             reRender="Row1,txtAccountNo,Row2,Row7,Row3,Row4,Row5,Row6,lblMsg,lblReceiptNo,stxtReceiptNo,btnSave,ddAutoPay,txtAutoPayAc"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblPAcno"  styleClass="label" value="Pending Account No"  style="display:#{NewTdRecieptCreation.inputDisFlag};"/>
                            <h:selectOneListbox id="ddPAcNo" size="1" styleClass="ddlist" value="#{NewTdRecieptCreation.pendingAcNo}" style="width:100px;display:#{NewTdRecieptCreation.inputDisFlag};">  
                                <f:selectItems value="#{NewTdRecieptCreation.pendingAcNoList}"/>
                                <a4j:support actionListener="#{NewTdRecieptCreation.getUnAuthTxnId}" event="onblur" focus="#{NewTdRecieptCreation.focusId}" reRender="ddPRtNo,lblMsg" limitToList="true" />
                            </h:selectOneListbox>
                            <h:outputLabel id="lblPVNo" styleClass="label" value="Pending Voucher No"  style="display:#{NewTdRecieptCreation.inputDisFlag};"/>
                            <h:selectOneListbox id="ddPRtNo" size="1" styleClass="ddlist" value="#{NewTdRecieptCreation.pendingRtNo}" style="width:100px;display:#{NewTdRecieptCreation.inputDisFlag};">  
                                <f:selectItems value="#{NewTdRecieptCreation.pendingRtNoList}"/>
                                <a4j:support actionListener="#{NewTdRecieptCreation.getRtDetails()}"  event="onblur" limitToList="true" focus="#{NewTdRecieptCreation.focusId}"
                                             reRender="txtAccountNo,Row2,Row7,Row3,Row4,Row5,Row6,lblMsg,lblReceiptNo,stxtReceiptNo,ddAutoPay,txtAutoPayAc,stxtAutoPayAcName"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col2,col3,col3,col3,col3" columns="6" id="Row2" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblAccountNo" styleClass="label" value="Account No" ><font class="error">*</font></h:outputLabel>
                                <h:panelGroup id = "pnlAcctno">
                                    <h:inputText id="txtAccountNo" maxlength="#{NewTdRecieptCreation.acNoMaxLen}" styleClass="input" value="#{NewTdRecieptCreation.oldAcNo}" tabindex="2" style="width:90px" disabled="#{NewTdRecieptCreation.modifyFlag}">
                                        <a4j:support actionListener="#{NewTdRecieptCreation.getCustomerFdInfo}" event="onblur" focus="txtAmount"
                                                     reRender="leftPanel,stxtAcNo,ddInterestOption"/>
                                    </h:inputText>
                                    <h:outputText id="stxtAcNo" styleClass="output" value="#{NewTdRecieptCreation.acctNo}"/>
                                </h:panelGroup>
                                <h:outputLabel id="lblName" styleClass="label" value="Name"/>
                                <h:outputText id = "stxtName" styleClass="label" value="#{NewTdRecieptCreation.name}"/>
                                <h:outputLabel id="lblJTUGName" styleClass="label" value="JT/UG Name"/>
                                <h:outputText id = "stxtJTUGName" styleClass="label" value="#{NewTdRecieptCreation.jtName}"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col3,col2,col3,col3,col3,col3" columns="6" id="Row7" style="width:100%;text-align:left;" styleClass="row2">
                                <h:outputLabel id="lblBalance" styleClass="label" value="Balance"/>
                                <h:outputText id = "stxtBalance" styleClass="label" value="#{NewTdRecieptCreation.balance}"/>
                                <h:outputLabel id="lblCustNature" styleClass="label" value="Cust Nature"/>
                                <h:outputText id = "stxtCustNature" styleClass="label" value="#{NewTdRecieptCreation.custNature}"/>
                                <h:outputLabel id="lblTDS" styleClass="label" value="TDS"/>
                                <h:outputText id = "stxtTDS" styleClass="label" value="#{NewTdRecieptCreation.tds}"/>
                            </h:panelGrid>
                            <h:panelGrid  columnClasses="col3,col2,col3,col3,col3,col3" columns="6" id="Row3" style="width:100%;text-align:left;" styleClass="row1">
                                <h:outputLabel id="lblAmount" styleClass="label" value="Amount "><font class="error">*</font></h:outputLabel>
                                <h:inputText id="txtAmount" styleClass="input" value="#{NewTdRecieptCreation.amount}" style="width:90px;" maxlength="15"  tabindex="3" disabled="#{NewTdRecieptCreation.modifyFlag}">
                                    <a4j:support actionListener="#{NewTdRecieptCreation.setPrincAmount}" event="onblur" focus="calTdDate"
                                                 reRender="txtPrincipalAmount,lblMsg"/>
                                </h:inputText>
                                <h:outputLabel id="lblTdDate" styleClass="label" value="TD Dt w.e.f."><font class="error">*</font></h:outputLabel>
                                <rich:calendar datePattern="dd/MM/yyyy" id="calTdDate" style="width:122px" value="#{NewTdRecieptCreation.tdDate}" tabindex="4" inputSize="8" disabled="#{NewTdRecieptCreation.modifyFlag}">
                                    <a4j:support actionListener="#{NewTdRecieptCreation.checkTdDate}" event="onblur" focus="ddInterestOption"
                                                 reRender="lblMsg"/>
                                </rich:calendar>
                                <h:outputLabel id="lblInterestOption" styleClass="label" value="Interest Option"><font class="error">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddInterestOption" styleClass="ddlist"  value="#{NewTdRecieptCreation.intOption}" size="1" tabindex="5" disabled="#{NewTdRecieptCreation.verifyFlag}">
                                    <f:selectItems value="#{NewTdRecieptCreation.intOptionList}"/>
                                    <a4j:support  event="onblur" actionListener="#{NewTdRecieptCreation.setInVIsibleAcToCredit}" oncomplete="if(#{NewTdRecieptCreation.intOption=='Q'} 
                                                  || #{NewTdRecieptCreation.intOption=='M'} || #{NewTdRecieptCreation.intOption=='Y'}){#{rich:element('txtAcToBeCredited')}.focus();}
                                                  else{#{rich:element('txtYear')}.focus();}"
                                                  reRender="Row4,lblMsg,ddInterestOption"/>
                                </h:selectOneListbox>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col3,col2,col3,col3,col3,col3" columns="6" id="Row4" style="width:100%;text-align:left;" styleClass="row2">
                                <h:panelGroup>
                                    <h:outputLabel id="lblAcToBeCredited" styleClass="label" value="A/c. To Be Credited" rendered="#{NewTdRecieptCreation.acctCrdVisible}"/>
                                    <h:outputText rendered="#{!NewTdRecieptCreation.acctCrdVisible}"/>
                                </h:panelGroup>
                                <h:panelGroup id = "pnlAcCredit">
                                    <h:inputText id="txtAcToBeCredited" maxlength="#{NewTdRecieptCreation.acNoMaxLen}" styleClass="input"  rendered="#{NewTdRecieptCreation.acctCrdVisible}" value="#{NewTdRecieptCreation.acToCredit}" tabindex="7" onkeyup="this.value=this.value.toUpperCase();" style="width:90px" disabled="#{NewTdRecieptCreation.verifyFlag}">
                                        <a4j:support actionListener="#{NewTdRecieptCreation.getAcctToBeCredited}" event="onblur" reRender="Row9,lblMsg,txtAcToBeCredited,stxtAccountNoCredit"/>
                                    </h:inputText>
                                    <h:outputText id = "stxtAccountNoCredit" styleClass="label" value="#{NewTdRecieptCreation.crediterAccount}" rendered="#{NewTdRecieptCreation.acctCrdVisible}"/>
                                    <h:outputText rendered="#{!NewTdRecieptCreation.acctCrdVisible}"/>
                                </h:panelGroup>
                                <h:outputLabel id="lblYear" styleClass="label" value="Year / Months / Days"><font class="error">*</font></h:outputLabel>
                                <h:panelGroup id = "pnlPeriod">
                                    <h:inputText id="txtYear" styleClass="input" value="#{NewTdRecieptCreation.year}" maxlength="4" style="width:30px;" tabindex="8" disabled="#{NewTdRecieptCreation.verifyFlag}">
                                        <a4j:support actionListener="#{NewTdRecieptCreation.setMatDate}" event="onblur" focus="txtMonths"
                                                     reRender="stxtMaturityDate,lblMsg,txtRateOfInt"/>
                                    </h:inputText>
                                    <h:outputLabel id="lblMonths" styleClass="label" value=" / "/>
                                    <h:inputText id="txtMonths" styleClass="input" value="#{NewTdRecieptCreation.months}" maxlength="3" style="width:30px;" disabled="#{NewTdRecieptCreation.verifyFlag}">
                                        <a4j:support actionListener="#{NewTdRecieptCreation.setMatDate}" focus="txtDays" event="onblur"
                                                     reRender="stxtMaturityDate,lblMsg,txtRateOfInt"/>
                                    </h:inputText>
                                    <h:outputLabel id="lblDays" styleClass="label" value=" / "/>
                                    <h:inputText id="txtDays" styleClass="input" value="#{NewTdRecieptCreation.days}" maxlength="3" style="width:30px;" disabled="#{NewTdRecieptCreation.verifyFlag}">
                                        <a4j:support actionListener="#{NewTdRecieptCreation.setMatDate}" event="onblur" focus="txtRateOfInt"
                                                     reRender="stxtMaturityDate,txtRateOfInt,lblMsg"/>
                                    </h:inputText>
                                </h:panelGroup>
                                <h:outputLabel id="lblRateOfInt" styleClass="label" value="Rate Of Interest"><font class="error">*</font></h:outputLabel>
                                <h:inputText id="txtRateOfInt" styleClass="input" style="width:90px;" value="#{NewTdRecieptCreation.rateOfInterest}" maxlength="5" disabled="#{NewTdRecieptCreation.verifyFlag}">
                                    <a4j:support actionListener="#{NewTdRecieptCreation.setMatAmount}" event="onblur" reRender="stxtMaturityAmt,lblInterest,stxtInterest"/>
                                </h:inputText>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col3,col2,col3,col3,col3,col3" columns="6" id="Row5" style="width:100%;text-align:left;" styleClass="row1">
                                <h:outputLabel id="lblMaturityDate" styleClass="label" value="MaturityDate"/>
                                <h:outputText id = "stxtMaturityDate" styleClass="label" value="#{NewTdRecieptCreation.matDate}"/>
                                <h:outputLabel id="lblPrincipalAmount" styleClass="label" value="Principal Amount "/>
                                <h:outputText id="txtPrincipalAmount" styleClass="label" value="#{NewTdRecieptCreation.principalAmt}"/>
                                <h:outputLabel id="lblMaturityAmt" styleClass="label" value="MaturityAmt"/>
                                <h:outputText id = "stxtMaturityAmt" styleClass="label" value="#{NewTdRecieptCreation.matAmount}"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col3,col2,col3,col3,col3,col3" columns="6" id="Row6" style="width:100%;text-align:left;" styleClass="row2">
                                <h:outputLabel id="lblInterest" styleClass="label" value="#{NewTdRecieptCreation.intLabel}"/>
                                <h:outputText id = "stxtInterest" styleClass="label" value="#{NewTdRecieptCreation.interest}"/>
                            
                                <h:outputLabel id="lblBookSeries" styleClass="label" value="Book Series" ><font class="error">*</font></h:outputLabel>    
                                <h:selectOneListbox id="ddBookSeries" value="#{NewTdRecieptCreation.bookSeries}" 
                                                    styleClass="ddlist"  size="1" style="width:60px;" 
                                                    disabled="#{NewTdRecieptCreation.modifyFlag}">
                                    <f:selectItems value="#{NewTdRecieptCreation.bookSeriesList}"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblAutoRenew" styleClass="label" value="Auto Renew" style="display:#{NewTdRecieptCreation.autoRenewVar}"/>
                                <h:selectOneListbox id="ddAutoRenew" value="#{NewTdRecieptCreation.autoRenew}" styleClass="ddlist"  size="1" disabled="#{NewTdRecieptCreation.verifyFlag}" style="display:#{NewTdRecieptCreation.autoRenewVar}">
                                    <f:selectItems value="#{NewTdRecieptCreation.autoRenewList}" />
                                </h:selectOneListbox>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col3,col2,col3,col3,col3,col3" columns="6" id="Row77" style="width:100%;text-align:left;display:#{NewTdRecieptCreation.autoPaymentVar}" styleClass="row1">
                                <h:outputLabel id="lblAutoPay" styleClass="label" value="Auto Payment" style="display:#{NewTdRecieptCreation.autoPaymentVar}"/>
                                <h:selectOneListbox id="ddAutoPay" value="#{NewTdRecieptCreation.autoPay}" styleClass="ddlist" 
                                                    size="1" disabled="#{NewTdRecieptCreation.verifyFlag}" style="display:#{NewTdRecieptCreation.autoPaymentVar}">
                                    <f:selectItems value="#{NewTdRecieptCreation.autoPayList}" />
                                </h:selectOneListbox>
                                <h:outputLabel id="lblAutoPayAc" styleClass="label" value="Auto Payment A/c"/>
                                <h:panelGroup>
                                    <h:inputText id="txtAutoPayAc" styleClass="input" value="#{NewTdRecieptCreation.paidAcno}" maxlength="12" size="12" disabled="#{NewTdRecieptCreation.verifyFlag}">
                                        <a4j:support event="onblur" action="#{NewTdRecieptCreation.getCustName}" reRender="stxtAutoPayAcName,lblMsg"/>
                                    </h:inputText>
                                    <h:outputText id="stxtAutoPayAcName" styleClass="output" value="#{NewTdRecieptCreation.paidAcnoName}"/>
                                </h:panelGroup>
                                <h:outputLabel/>
                                <h:outputLabel/>
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid id="poFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup id="btnPanel">
                                <a4j:commandButton id="btnSave" value="#{NewTdRecieptCreation.btnLabel}" action="#{NewTdRecieptCreation.saveAction}" reRender="leftPanel,lblMsg,stxtAcNo" />
                                <a4j:commandButton id="btnReset" value="Reset" action="#{NewTdRecieptCreation.resetValue}" reRender="leftPanel" />
                                <a4j:commandButton id="btnExit" value="Exit" action="#{NewTdRecieptCreation.exitBtnAction}" reRender="stxtAcNo" /> 
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                </a4j:form>
        </body>
    </html>
</f:view>
