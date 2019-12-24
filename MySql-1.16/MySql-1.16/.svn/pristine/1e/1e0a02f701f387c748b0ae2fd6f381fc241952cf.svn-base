<%-- 
    Document   : chequePurchaseRealisation
    Created on : 13 Nov, 2017, 12:26:00 PM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <title>Cheque Purchase Realisation</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                var row;
                function setMask() {
                    jQuery(".chkDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="formAtm">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{ChequePurchaseRealisation.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Cheque Purchase Realisation"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ChequePurchaseRealisation.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                        <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;" styleClass="row1">
                            <h:outputText id="message" styleClass="msg" value="#{ChequePurchaseRealisation.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row2" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblBcbillNo" styleClass="label" value="BC Bill No." ><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddBcbillNo" styleClass="ddlist" size="1" style="width:100px;" value="#{ChequePurchaseRealisation.bcBillNo}">
                                <f:selectItems value="#{ChequePurchaseRealisation.bcBillNoList}"/>
                                    <a4j:support action="#{ChequePurchaseRealisation.getChequePurchaseData}" event="onblur" oncomplete="setMask();" reRender="mainPanel,message,btnSave"/>
                                </h:selectOneListbox>
                            <h:outputLabel id="lblbillNo" styleClass="label" value="Bill No."  />
                            <h:inputText id="stxtbillNo" styleClass="input" value="#{ChequePurchaseRealisation.billNo}"/>
                            <h:outputLabel id="lblactivityType" styleClass="label" value="Activity Type"  />
                            <h:inputText id="stxactivityType" styleClass="input" style="width:180px;" value="#{ChequePurchaseRealisation.activityType}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row3" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblacNo" styleClass="label" value="A/C No."  />
                            <h:inputText id="stxtacNo" styleClass="input" style="width:80px;" value="#{ChequePurchaseRealisation.acNo}"  disabled="#{ChequePurchaseRealisation.disableField}">
                                <%--a4j:support action="#{ChequePurchaseRealisation.processOnAccount}" event="onblur" reRender="Row3,Row4,Row9,mainPanel"> </a4j:support--%>
                            </h:inputText>
                            <h:outputLabel id="lblacHolder" styleClass="label" value="A/C Holder Name"  />
                            <h:inputText id="stxtacHolder" styleClass="input" value="#{ChequePurchaseRealisation.acholderName}"/>
                            <h:outputLabel id="lblacType" styleClass="label" value="A/C Type"  />
                            <h:inputText id="stxtacType" styleClass="input" value="#{ChequePurchaseRealisation.acType}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row4" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblgroup" styleClass="label" value="Group"  />
                            <h:inputText id="stxtgroup" styleClass="input" value="#{ChequePurchaseRealisation.group}"/>
                            <h:outputLabel id="lblscheme" styleClass="label" value="Scheme Code"  />
                            <h:inputText id="stxtScheme" styleClass="input" value="#{ChequePurchaseRealisation.schemeCode}"/>
                            <h:outputLabel id="lblschemeDesc" styleClass="label" value="Scheme Desc"  />
                            <h:inputText id="stxtSchemeDesc" styleClass="input" value="#{ChequePurchaseRealisation.schemeCodeDesc}"/>
                        </h:panelGrid>

                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row5" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblBankCode" styleClass="label" value="Bank Code"/>
                            <h:panelGroup id="panelGrp3" layout="block">
                                <h:inputText id="txtBankCode1" styleClass="input" style="width:40px;" disabled="#{ChequePurchaseRealisation.flag3}" value="#{ChequePurchaseRealisation.bankCode1}" maxlength="3">
                                    <a4j:support actionListener="#{ChequePurchaseRealisation.bankCode1LostFocus}" event="onblur" reRender="stxtError"/>
                                </h:inputText>
                                <h:inputText id="txtBankCode2" styleClass="input" style="width:40px;"disabled="#{ChequePurchaseRealisation.flag3}" value="#{ChequePurchaseRealisation.bankCode2}" maxlength="3">
                                    <a4j:support actionListener="#{ChequePurchaseRealisation.bankCode2LostFocus}" event="onblur" reRender="stxtError,txtBankCode2"/>
                                </h:inputText>
                                <h:inputText id="txtBankCode3" styleClass="input" style="width:40px;"disabled="#{ChequePurchaseRealisation.flag3}" value="#{ChequePurchaseRealisation.bankCode3}" maxlength="3">
                                    <a4j:support actionListener="#{ChequePurchaseRealisation.bankCode3LostFocus}" event="onblur" reRender="message,stxtbranch,txtBankName,txtBankCode3"/>
                                </h:inputText>
                            </h:panelGroup>
                            <h:outputLabel id="lblbranch" styleClass="label" value="Bank Name"  /> 
                            <h:inputText id="stxtbranch" styleClass="input"disabled="#{ChequePurchaseRealisation.flag4}" value="#{ChequePurchaseRealisation.bankName}" />
                            <h:outputLabel id="lblBankName" styleClass="label" value="Branch Name"/>
                            <h:inputText id="txtBankName" styleClass="input"disabled="#{ChequePurchaseRealisation.flag4}" value="#{ChequePurchaseRealisation.branchName}" />
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row6" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lbltotalLimit" styleClass="label" value="Total limit"  />
                            <h:inputText id="stxttotalLimit" styleClass="input" style="width:80px;" value="#{ChequePurchaseRealisation.totalLimit}"/>
                            <h:outputLabel id="lblavaLimit" styleClass="label" value="Ava. Limit"  />
                            <h:inputText id="stxtavaLimit" styleClass="input" style="width:80px;" value="#{ChequePurchaseRealisation.avaLimit}"/>
                            <h:outputLabel id="lbltxnType" styleClass="label" value="Trans. Type" ></h:outputLabel>
                            <h:selectOneListbox id="ddtxnType" styleClass="ddlist" size="1" style="width:80px;" value="#{ChequePurchaseRealisation.transType}">
                                <f:selectItems value="#{ChequePurchaseRealisation.transTypeList}"/> 
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row7" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lbldocType" styleClass="label" value="Doc. Type"></h:outputLabel>
                            <h:selectOneListbox id="dddocType" styleClass="ddlist" size="1" style="width:80px;" value="#{ChequePurchaseRealisation.docType}" >
                                <f:selectItems value="#{ChequePurchaseRealisation.docTypeList}"/>
                                <%--a4j:support action="#{BillsPurchase.nomRelation}" event="onchange" reRender="ddnomRelationship"> </a4j:support--%>
                            </h:selectOneListbox>
                            <h:outputLabel id="lbldocNo" styleClass="label" value="Instrument No." > </h:outputLabel>  
                            <h:inputText id="stxtdocNo" styleClass="input" style="width:80px;" value="#{ChequePurchaseRealisation.docNo}" maxlength = "6" /> 
                            <h:outputLabel id="lbldocDate" styleClass="label" value="Instrument Date" > </h:outputLabel>  
                            <h:inputText id="stxtdocDate" styleClass="input chkDt" style="width:80px;" value="#{ChequePurchaseRealisation.docDate}" disabled="#{ChequePurchaseRealisation.disableField}">
                                <a4j:support oncomplete="setMask();"/>
                            </h:inputText> 
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row8" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lbldocSeries" styleClass="label" value="Instrument Series." > </h:outputLabel>  
                            <h:inputText id="stxtdocSeries" styleClass="input" style="width:80px;" value="#{ChequePurchaseRealisation.docSeriies}"  /> 
                            <h:outputLabel id="lblgracePeriod" styleClass="label" value="Grace Period" > </h:outputLabel>  
                            <h:inputText id="stxtgracePeriod" styleClass="input" style="width:80px;" value="#{ChequePurchaseRealisation.gracePeriod}"  /> 
                            <h:outputLabel id="lblbillDate" styleClass="label" value="Bill Date" > </h:outputLabel>  
                            <h:inputText id="stxtbillDate" styleClass="input chkDt" style="width:80px;" value="#{ChequePurchaseRealisation.billDate}" disabled="#{ChequePurchaseRealisation.disableField}">
                                <a4j:support oncomplete="setMask();"/>
                            </h:inputText> 
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row9" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblunsance" styleClass="label" value="Usance Days" > </h:outputLabel>  
                            <h:inputText id="stxtunsance" styleClass="input" style="width:80px;" value="#{ChequePurchaseRealisation.usanceDays}" disabled="#{ChequePurchaseRealisation.disableField}" >
                                <%--a4j:support action="#{ChequePurchaseRealisation.setMaturityDateByUsanceDays}" event="onblur" oncomplete="setMask();" reRender="stxtMaturityDate"/--%>
                            </h:inputText> 
                            <h:outputLabel id="lblMaturityDate" styleClass="label" value="Maturity Date" > </h:outputLabel>  
                            <h:inputText id="stxtMaturityDate" styleClass="input chkDt" style="width:80px;" value="#{ChequePurchaseRealisation.maturityDate}" disabled="#{ChequePurchaseRealisation.disableField}" >
                                <a4j:support oncomplete="setMask();"/>
                            </h:inputText>
                            <h:outputLabel id="lblAmount" styleClass="label" value="Amount" > </h:outputLabel>  
                            <h:inputText id="stxtAmount" styleClass="input" style="width:80px;" value="#{ChequePurchaseRealisation.amount}" disabled="#{ChequePurchaseRealisation.disableField}"  /> 
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row10" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblcollectionChr" styleClass="label" value="Collection Chr" > </h:outputLabel>  
                            <h:inputText id="stxtcollectionChr" styleClass="input" style="width:80px;" value="#{ChequePurchaseRealisation.collectionChr}" disabled="#{ChequePurchaseRealisation.disableField}" /> 
                            <h:outputLabel id="lblpostageAmt" styleClass="label" value="Postage Amt"  />
                            <h:inputText id="stxtpostageAmt" styleClass="input" style="width:80px;" value="#{ChequePurchaseRealisation.postageAmt}"/>
                            <h:outputLabel id="lblPocketExp" styleClass="label" value="Out Of Pocket Exp."  />
                            <h:inputText id="stxtPocketExp" styleClass="input" style="width:80px;" value="#{ChequePurchaseRealisation.pocketExp}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row11" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblmargin" styleClass="label" value="Margin"  />
                            <h:inputText id="stxtmargin" styleClass="input" style="width:80px;" value="#{ChequePurchaseRealisation.margin}"/>
                            <h:outputLabel id="lblIntrestRate" styleClass="label" value="Interest Rate"  />
                            <h:inputText id="stxtInterestRate" styleClass="input" style="width:80px;" value="#{ChequePurchaseRealisation.interestRate}"/>
                            <h:outputLabel id="lblIntrestAmt" styleClass="label" value="Interest Amt"  />
                            <h:inputText id="stxtIntrestAmt" styleClass="input" style="width:80px;" value="#{ChequePurchaseRealisation.interestAmt}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row12" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblstatus" styleClass="label" value="Status" > </h:outputLabel>  
                            <h:inputText id="stxtstatus" styleClass="input" style="width:80px;" value="#{ChequePurchaseRealisation.status}"  />  
                            <h:outputLabel id="lblRealisationAmount" styleClass="label" value="Realisation Amount" > </h:outputLabel>  
                            <h:inputText id="stxtRealisationAmount" styleClass="input" style="width:80px;" value="#{ChequePurchaseRealisation.realisationAmt}"  />  
                            <h:outputLabel id="lblCommPaid" styleClass="label" value="Comm.Paid to Bankers"  />
                            <h:inputText id="stxtCommPaid" styleClass="input" style="width:80px;" value="#{ChequePurchaseRealisation.commPaidToBankers}"/>
                        </h:panelGrid>
                        <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup layout="block" style="width:100%;text-align:center;">
                                <a4j:commandButton id="btnSave" value="Realisation" disabled="#{ChequePurchaseRealisation.btnFlag}" oncomplete="#{rich:component('processPanel')}.show();setMask();" reRender="mainPanel,processPanel,confirmid"/>
                                <a4j:commandButton id="btnRefresh" value="Refresh" action="#{ChequePurchaseRealisation.refreshForm}" oncomplete="setMask();" reRender="mainPanel"/>
                                <a4j:commandButton id="btnExit" value="Exit" action="#{ChequePurchaseRealisation.exitBtnAction}" reRender="mainPanel"/>
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <%--New Addition--%>
            <a4j:region id="processActionRegion">
                <rich:modalPanel id="processPanel" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText id="confirmid" value="Are you sure to Authorize"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="btnYes" action="#{ChequePurchaseRealisation.realisationTxn}" onclick="#{rich:component('processPanel')}.hide();" 
                                                           oncomplete="setMask();" reRender="message,mainPanel"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('processPanel')}.hide();"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
            </a4j:region>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="processActionRegion"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>