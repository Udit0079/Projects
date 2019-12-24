<%-- 
    Document   : RTGSSMain
    Created on : May 30, 2010, 11:39:14 AM
    Author     : jitendra Chaudhary
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Real Time Gross Settlement System</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery){
                    setMask();
                });
                var row;
                function setMask(){
                    jQuery(".calInstDate").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="MainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="HeaderPanel" columns="3"  style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup>
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{RTGSCriteria.currentDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel"value="Real Time Gross Settlement System"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUsername" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="otUsername" styleClass="output" value="#{RTGSCriteria.loggedInUser}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel001" style="width:100%;height:30px;text-align:center;border:1px ridge #BED6F8;" styleClass="row1" width="100%">
                        <h:outputText id="stxtMsg" styleClass="error" value="#{RTGSCriteria.message}"/>
                    </h:panelGrid>
                    <h:panelGrid rendered="#{RTGSCriteria.criteriaRenderValue == 'true'}" id="lblHeaderPenal" styleClass="row2" columns="1" style="width:100%;height:30px;text-align:center;border:1px ridge #BED6F8;" width="100%">
                        <h:outputLabel id="lblHeading" styleClass="headerLabel" value="Criteria"/>
                    </h:panelGrid>
                    <h:panelGrid rendered="#{RTGSCriteria.criteriaRenderValue == 'true'}" id="PanelGrid1" styleClass="row1" columns="2" columnClasses="col8,col8" style="height:30px;border:1px ridge #BED6F8;" width="100%">
                        <h:outputLabel id="lblFunction"style="padding-left:350px" styleClass="label" value="Function" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddlFunction" styleClass="ddlist" size="1" style="width: 120px" value="#{RTGSCriteria.function}">
                            <a4j:support ajaxSingle="true" actionListener="#{RTGSCriteria.getFunctionDetails}" event="onchange"  reRender="txtUTR,txtTranID,txtPaysysTranRefNo,MainPanel,btnSubmitAll" oncomplete="setMask()"/>
                            <f:selectItems value="#{RTGSCriteria.functionList}" />
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid rendered="#{RTGSCriteria.criteriaRenderValue == 'true'}" id="PanelGrid2" styleClass="row2" columns="2" columnClasses="col8,col8" style="height:30px;border:1px ridge #BED6F8;"  width="100%">
                        <h:outputLabel id="lblTransType"style="padding-left:350px" styleClass="label" value="Trans.Type"/>
                        <h:selectOneListbox id="ddTransType" styleClass="ddlist" size="1" style="width: 120px" value="#{RTGSCriteria.tranType}" >
                            <f:selectItems value="#{RTGSCriteria.tranTypeList}" />
                            <a4j:support ajaxSingle="true" event="onchange" oncomplete="setMask()" />
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid rendered="#{RTGSCriteria.criteriaRenderValue == 'true'}" id="PanelGrid3" styleClass="row1" columns="2" columnClasses="col8,col8" style="height:30px;border:1px ridge #BED6F8;"  width="100%">
                        <h:outputLabel id="lblBranchId"style="padding-left:350px" styleClass="label" value="Branch ID"/>
                        <h:inputText id="txtBranchId" styleClass="input" value="#{RTGSCriteria.branchId}" disabled="true" >
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid rendered="#{RTGSCriteria.criteriaRenderValue == 'true'}" id="PanelGrid4" styleClass="row2" columns="2" columnClasses="col8,col8" style="height:30px;border:1px ridge #BED6F8;"  width="100%">
                        <h:outputLabel id="lblPaySysId"style="padding-left:350px" styleClass="label" value="Paysys ID"/>
                        <h:selectOneListbox id="txtPaySysId" styleClass="ddlist" size="1" style="width: 120px" value="#{RTGSCriteria.paySysId}" >
                            <f:selectItems value="#{RTGSCriteria.paySystemIdList}" />
                            <a4j:support actionListener="#{RTGSCriteria.getMessageTypeInfo}" ajaxSingle="true" oncomplete="setMask()"  event="onblur"  reRender="ddMsgType,stxtMsg" limitToList="true"  />
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid rendered="#{RTGSCriteria.criteriaRenderValue == 'true'}" id="PanelGrid5" styleClass="row1" columns="2" columnClasses="col8,col8," style="height:30px;border:1px ridge #BED6F8;"  width="100%">
                        <h:outputLabel id="lblMsgType"style="padding-left:350px" styleClass="label" value="Message Type" />
                        <h:selectOneListbox id="ddMsgType" styleClass="ddlist" size="1" style="width: 120px" value="#{RTGSCriteria.msgType}">
                            <a4j:support actionListener="#{RTGSCriteria.getUTRNumber}" event="onblur" oncomplete="setMask()"  reRender="txtUTR,txtTranID,txtPaysysTranRefNo,ddlFunction,calInstDate,stxtMsg"/>
                            <f:selectItems value="#{RTGSCriteria.messageTypeOption}" />
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid rendered="#{RTGSCriteria.criteriaRenderValue == 'true'}" id="PanelGrid6" styleClass="row2" columns="2" columnClasses="col8,col8" style="height:30px;border:1px ridge #BED6F8;"  width="100%">
                        <h:outputLabel id="lblUTR"style="padding-left:350px" styleClass="label" value="UTR"/>
                        <h:inputText id="txtUTR" styleClass="input" value="#{RTGSCriteria.utrNumber}" disabled="#{RTGSCriteria.functionValue}">
                            <a4j:support actionListener="#{RTGSCriteria.onBlurUtrNo}" event="onblur"  oncomplete="setMask()" reRender="MainPanel,ddMsgType" />
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid rendered="#{RTGSCriteria.criteriaRenderValue == 'true'}" id="PanelGrid7" styleClass="row1" columns="2" columnClasses="col8,col8" style="height:30px;border:1px ridge #BED6F8;"  width="100%">
                        <h:outputLabel id="lblTranID"style="padding-left:350px" styleClass="label" value="Tran.ID" />
                        <h:inputText id="txtTranID" styleClass="input"  value="#{RTGSCriteria.tranId}" disabled="#{RTGSCriteria.functionValue}" >
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid rendered="#{RTGSCriteria.criteriaRenderValue == 'true'}" id="PanelGrid8" styleClass="row2" columns="2" columnClasses="col8,col8" style="height:30px;border:1px ridge #BED6F8;"  width="100%">
                        <h:outputLabel id="lblTranDate"style="padding-left:350px" styleClass="label" value="Tran.Date"/>
                        <h:inputText id="calInstDate" styleClass="input calInstDate" value="#{RTGSCriteria.transDt}" style="width:118px;"  size="29">
                            <f:convertDateTime pattern="dd/MM/yyyy" />
                            <a4j:support  event="onchanged"  oncomplete="setMask()" />
                        </h:inputText>

                    </h:panelGrid>
                    <h:panelGrid rendered="#{RTGSCriteria.criteriaRenderValue == 'true'}" id="PanelGrid9" styleClass="row1" columns="2" columnClasses="col8,col8" style="height:30px;border:1px ridge #BED6F8;"  width="100%">
                        <h:outputLabel id="lblPaysysTranRefNo"style="padding-left:350px" styleClass="label" value="Paysys Tran.Ref.No"/>
                        <h:inputText id="txtPaysysTranRefNo" styleClass="input" value="#{RTGSCriteria.payTrnRefNo}" disabled="#{RTGSCriteria.functionValue}" >

                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid rendered="#{RTGSCriteria.criteriaRenderValue == 'true'}" id="FooterPanel" columns="3" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="footer">
                            <a4j:commandButton value="Proceed" id="btnProceed" actionListener="#{RTGSCriteria.getGeneralDetails}" oncomplete="setMask()"  reRender="ddTransType,stxtMsg,calInstDate,MainPanel">

                            </a4j:commandButton>
                            <a4j:commandButton id="btnRefresh" value="Refresh" actionListener="#{RTGSCriteria.getRefreshCriteria}" reRender="MainPanel,ddMsgType" focus="ddlFunction" oncomplete="setMask()" >

                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{RTGSCriteria.exitBtnAction}"  ajaxSingle="true" oncomplete="setMask()">

                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                    <%-- ////////////////////   End Criteria Penal    /////////////////// --%>

                    <%-- ////////////////////   Start General Penal    /////////////////// --%>
                    <h:panelGrid rendered="#{RTGSCriteria.criteriaRenderValue == 'false'}" id="DetailsPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                        <h:panelGrid id="penal1" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;border:1px ridge #BED6F8;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblFuction" value="Function" style="width:80%" styleClass="label"/>
                            <h:outputText id="stxtFunction" styleClass="output" value="#{RTGSCriteria.sendFunction}"/>
                            <h:outputLabel id="lblMsgType1" value="Message Type" style="width:80%" styleClass="label"/>
                            <h:outputText id="stxtMsgType" styleClass="output" value="#{RTGSCriteria.sendMsgType}"/>
                            <h:outputLabel id="lblUtr" value="UTR" style="width:80%" styleClass="label"/>
                            <h:outputText id="stxtUtr" styleClass="output" value="#{RTGSCriteria.sendUtrNumber}"/>
                        </h:panelGrid>
                        <h:panelGrid id="penal2" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblSqlId" value="Branch ID" style="width:80%" styleClass="label"/>
                            <h:outputText id="stxtSqlid" styleClass="output" value="#{RTGSCriteria.sendBranchId}"/>
                            <h:outputLabel id="lblPayId" value="Paysys ID" style="width:80%" styleClass="label"/>
                            <h:outputText id="stxtPaysid" styleClass="output" value="#{RTGSCriteria.sendPaySysId}"/>
                            <h:outputLabel id="lblTrans" value="Trans. Type" style="width:80%" styleClass="label"/>
                            <h:outputText id="stxtTransType" styleClass="output" value="#{RTGSCriteria.sendTranType}"/>
                        </h:panelGrid>
                        <h:panelGrid id="panel3" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;border:1px ridge #BED6F8;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblTrId" value="Trans. ID" style="width:80%" styleClass="label"/>
                            <h:outputText id="stxtTransId" styleClass="output" value="#{RTGSCriteria.sendTranId}"/>
                            <h:outputLabel id="lblTrDate" value="Trans. Date" style="width:80%" styleClass="label"/>
                            <h:outputText id="stxtTransDate" styleClass="output" value="#{RTGSCriteria.setTransDt}"/>
                            <h:outputLabel id="lblrgr" value="" style="width:80%" styleClass="label"/>
                            <h:outputLabel id="lblIfhf" value="" style="width:80%" styleClass="label"/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid rendered="#{RTGSCriteria.criteriaRenderValue == 'false'}" id="HeaderPanel2" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                        <h:panelGrid id="PanelGrid__" columns="1"  border="1" style="width:100%;height:30px;text-align:center;border:1px ridge #BED6F8;" styleClass="row2">
                            <h:outputLabel id="lblTitle2" styleClass="headerLabel" value="General Details"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" id="PanelGrid1Msg" styleClass="row1" columns="4" style="height:30px;border:1px ridge #BED6F8;" width="100%">
                            <h:outputLabel id="lblMessageStatus" style="width:80%" styleClass="label" value="Message Status"/>
                            <h:inputText id="txtMessageStatus" styleClass="input" disabled="true" size="16" value="#{RTGSCriteria.msgStatus}">
                            </h:inputText>
                            <h:outputLabel id="lblRejectReasonCode" style="width:80%" styleClass="label" value="Reject Reason Code"/>
                            <h:inputText id="txtRejectReasonCode" styleClass="input" disabled="true" size="16" value="#{RTGSCriteria.rejectReasCode}">
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" id="PanelGrid2Rej" columns="4"  style="height:30px;border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblRejectReason" styleClass="label" value="Reject Reason"/>
                            <h:inputText id="txtRejectReason" styleClass="input" disabled="true" size="16" value="#{RTGSCriteria.rejectReason}">
                            </h:inputText>
                            <h:outputLabel id="lblMessagePriority" styleClass="label" value="Message Priority(1-99)" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtMessagePriority" styleClass="input" value="#{RTGSCriteria.msgpriority}" disabled="true" size="16">
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" id="PanelGrid5Value" columns="4"  style="height:30px;border:1px ridge #BED6F8;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblValueDate" style="width:80%"styleClass="label" value="Value Date" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="calValueDate" styleClass="input calInstDate" value="#{RTGSCriteria.valueDate}" style="width:98px;"  size="16">
                                <f:convertDateTime pattern="dd/MM/yyyy" />
                                <a4j:support  event="onchanged"  oncomplete="setMask()" />
                            </h:inputText>
                            <h:outputLabel id="lblDrACID" style="width:80%"styleClass="label" value="Dr.A/C>ID" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:panelGroup>
                                <h:inputText disabled="#{RTGSCriteria.drAccIDEnable}" id="txtDrACID" styleClass="input" value="#{RTGSCriteria.drAccId}" onkeyup="this.value = this.value.toUpperCase();" maxlength="12" size="16">
                                    <a4j:support actionListener="#{RTGSCriteria.getDrAccountInformation}" event="onblur" reRender="stxtCrInr2,stxtCrInr5,stxtMsg,txtCrACID,txtCustName" oncomplete="setMask()" />
                                </h:inputText>
                                <h:outputText id="stxtInr" styleClass="output" value="INR"/>
                                <h:outputText id="stxtCrIfnfnr1" styleClass="output" value="    "/>
                                <h:outputText id="stxtCrdIfnfnr1" styleClass="output" value="     "/>
                                <h:outputText id="stxtCrInr5" styleClass="output" value="#{RTGSCriteria.drCustName}" />
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" id="PanelGrid6Crid" columns="4"  style="height:30px;border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblCrACID" style="width:80%" styleClass="label" value="Cr.A/C>ID"/>
                            <h:panelGroup>
                                <h:inputText id="txtCrACID" styleClass="input" onkeyup="this.value = this.value.toUpperCase();" value="#{RTGSCriteria.crAccId}"  maxlength="12" size="16" disabled="true">
                                </h:inputText>
                                <h:outputText id="stxtCrInr" styleClass="output" value="INR"/>
                                <h:outputText id="stxtCrInr1" styleClass="output" value="    "/>
                                <h:outputText id="stxtCrbfInr1" styleClass="output" value="    "/>
                                <h:outputText id="stxtCrInr2" styleClass="output" value="#{RTGSCriteria.crCustName}" />
                            </h:panelGroup>
                            <h:outputLabel id="lblTranAmount" style="width:80%" styleClass="label" value="Tran. Amount" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:panelGroup>
                                <h:inputText id="txtTranAmount" styleClass="input" size="3" value="#{RTGSCriteria.tranAmt1}" disabled="true">
                                </h:inputText>
                                <h:inputText id="txtTrAmt" styleClass="input" size="16" value="#{RTGSCriteria.transAmount}">
                                </h:inputText>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" id="PanelGrid9Charge" columns="4"  style="height:30px;border:1px ridge #BED6F8;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblChargeEventID" style="width:80%" styleClass="label" value="Charge Event ID"/>
                            <h:selectOneListbox id="ddChargeEventID" styleClass="ddlist" size="1" style="width: 100px" value="#{RTGSCriteria.chargeEventID}">
                                <a4j:support actionListener="#{RTGSCriteria.getChargeCashAccNo}" event="onblur" oncomplete="#{rich:element('txtChargeAccountID')}.focus();setMask()"  reRender="txtChargeAccountID,stxtMsg"/>
                                <f:selectItems value="#{RTGSCriteria.chargeEventIDOption}" />
                            </h:selectOneListbox>
                            <h:outputLabel id="lblChargeAccountID" style="width:80%" styleClass="label" value="Charge Account ID"/>
                            <h:panelGroup>
                                <h:inputText id="txtChargeAccountID" styleClass="input" size="16" maxlength="12" value="#{RTGSCriteria.chargeAccountID}" onkeyup="this.value = this.value.toUpperCase();">
                                    <a4j:support actionListener="#{RTGSCriteria.getChargeAccountInDetails}" event="onblur" reRender="stxtChargeAccId,txtTrAmt,stxtMsg" oncomplete="setMask()"/>
                                </h:inputText>
                                <h:outputText id="stxtChargeAcc" styleClass="output" value="INR         " />
                                <h:outputText id="stxtChargeAccgfh" styleClass="output" value="   " />
                                <h:outputText id="stxtChargeAccId" styleClass="output" value="#{RTGSCriteria.chargeAccCustName}"/>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" id="PanelGrid10" columns="7"  style="height:30px;border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblInstrumentType" styleClass="label" value="Instrument Type"/>
                            <h:selectOneListbox id="ddInstrumentType" styleClass="ddlist" size="1" style="width: 100px" value="#{RTGSCriteria.instrumentType}">
                                <a4j:support actionListener="#{RTGSCriteria.instrumentType}" event="onblur"  reRender="txtInstrumentNo" oncomplete="if((#{RTGSCriteria.instrumentType == 'CHQ'})) {#{rich:element('txtInstrumentNo')}.focus();}
                                             else if ((#{RTGSCriteria.instrumentType == 'VCH'})) {#{rich:element('calInstrumentDate')}.focus();} setMask();"/>
                                <f:selectItems value="#{RTGSCriteria.instTypeList}" />
                            </h:selectOneListbox>
                            <h:outputLabel id="lblInstrumentNo" styleClass="label" value="Instrument No."/>
                            <h:inputText id="txtInstrumentNo" styleClass="input" disabled="#{RTGSCriteria.instrValue}" value="#{RTGSCriteria.instrNo}" size="16">
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" id="PanelGrid13" columns="4"  style="height:30px;border:1px ridge #BED6F8;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblInstrumentDate" style="width:80%" styleClass="label" value="Instrument Date"/>
                            <h:inputText id="calInstrumentDate" styleClass="input calInstDate" value="#{RTGSCriteria.instDate}" style="width:98px;"  size="16">
                                <f:convertDateTime pattern="dd/MM/yyyy" />
                                <a4j:support  event="onchanged"  oncomplete="setMask()" />
                            </h:inputText>
                            <%-- <rich:calendar datePattern="dd/MM/yyyy" id="calInstrumentDate" value="#{RTGSCriteria.instDate}" >
                                 <a4j:support event="onchanged" reRender="stxtMsg"  />
                             </rich:calendar>  --%>
                            <h:outputLabel id="lblTranParticulars" style="width:80%" styleClass="label" value="Tran. Particulars"/>
                            <h:inputText id="txtTranParticulars" styleClass="input" size="16" value="#{RTGSCriteria.tranParticular}" onkeyup="this.value=this.value.toUpperCase();" maxlength="99">
                            </h:inputText>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid rendered="#{RTGSCriteria.criteriaRenderValue == 'false'}" id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton value="Previous" id="btnPrevious" actionListener="#{RTGSCriteria.getRTGSCriteriaDetails}" reRender="MainPanel" oncomplete="setMask()">
                            </a4j:commandButton>
                            <a4j:commandButton value="Next" id="btnNext" actionListener="#{RTGSCriteria.getAllDetails}" reRender="stxtMsg,txtDrACID,MainPanel" oncomplete="setMask()" >
                            </a4j:commandButton>
                            <a4j:commandButton id="btnRefresh1" value="Refresh" actionListener="#{RTGSCriteria.getRefreshGeneralDetails}" reRender="MainPanel" oncomplete="setMask()">
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                    <%-- ////////////////////   End General Penal    /////////////////// --%>

                    <%-- ////////////////////   Start AllDetail Penal    /////////////////// --%>
                    <h:panelGrid rendered="#{RTGSCriteria.criteriaRenderValue == 'allValue'}" id="penal1All" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:27px;border:1px ridge #BED6F8;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblFuction1" value="Function" style="width:80%" styleClass="label"/>
                        <h:outputText id="stxtFunction1" styleClass="output" value="#{RTGSCriteria.sendFunction}"/>
                        <h:outputLabel id="lblMsgType12" value="Message Type" style="width:80%" styleClass="label"/>
                        <h:outputText id="stxtMsgType12" styleClass="output" value="#{RTGSCriteria.sendMsgType}"/>
                        <h:outputLabel id="lblUtr1" value="UTR" style="width:80%" styleClass="label"/>
                        <h:outputText id="stxtUtr1" styleClass="output" value="#{RTGSCriteria.sendUtrNumber}"/>
                    </h:panelGrid>
                    <h:panelGrid rendered="#{RTGSCriteria.criteriaRenderValue == 'allValue'}" id="penal2All" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:27px;border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblBranchIdAll" value="Branch ID" style="width:80%" styleClass="label"/>
                        <h:outputText id="stxtBranchidAll" styleClass="output" value="#{RTGSCriteria.sendBranchId}"/>
                        <h:outputLabel id="lblPayIdAll" value="Paysys ID" style="width:80%" styleClass="label"/>
                        <h:outputText id="stxtPaysidAll" styleClass="output" value="#{RTGSCriteria.sendPaySysId}"/>
                        <h:outputLabel id="lblTransAll" value="Trans. Type" style="width:80%" styleClass="label"/>
                        <h:outputText id="stxtTransTypeAll" styleClass="output" value="#{RTGSCriteria.sendTranType}"/>
                    </h:panelGrid>
                    <h:panelGrid rendered="#{RTGSCriteria.criteriaRenderValue == 'allValue'}" id="panel3All" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:27px;border:1px ridge #BED6F8;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblTrIdAll" value="Trans. ID" style="width:80%" styleClass="label"/>
                        <h:outputText id="stxtTransIdAll" styleClass="output" value="#{RTGSCriteria.sendTranId}"/>
                        <h:outputLabel id="lblTrDateAll" value="Trans. Date" style="width:80%" styleClass="label"/>
                        <h:outputText id="stxtTransDateAll" styleClass="output" value="#{RTGSCriteria.setTransDt}"/>
                        <h:outputLabel id="lblrgrAll" value="" style="width:80%" styleClass="label"/>
                        <h:outputLabel id="lblIfhfAll" value="" style="width:80%" styleClass="label"/>
                    </h:panelGrid>
                    <h:panelGrid rendered="#{RTGSCriteria.criteriaRenderValue == 'allValue'}" id="PanelGridSender" columns="1" style="width:100%;height:20px;text-align:center;border:1px ridge #BED6F8;" styleClass="row2">
                        <h:outputLabel id="lblTitle2All" styleClass="label" value="Sender’s Correspondent Details"/>
                    </h:panelGrid>
                    <h:panelGrid rendered="#{RTGSCriteria.criteriaRenderValue == 'allValue'}" id="panel4" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:27px;border:1px ridge #BED6F8;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblBnkCodeAll" value="Bank/Branch Code" styleClass="label"/>
                        <h:panelGroup>
                            <h:inputText id="txtBankAll" styleClass="input" size="8" disabled="true" value="#{RTGSCriteria.senderCorrBnkCode}">
                            </h:inputText>
                            <h:inputText id="txtBranchAll" styleClass="input" size="8" disabled="true" value="#{RTGSCriteria.senderCorrBranchCode}">
                            </h:inputText>
                        </h:panelGroup>
                        <h:outputLabel id="lblBnkCodeIsoAll" value="IFS Code" styleClass="label"/>
                        <h:inputText id="txtBankIsoAll" styleClass="input" size="20" disabled="true" value="#{RTGSCriteria.senderCorrIfsCode}">
                        </h:inputText>
                        <h:outputText id="stxtMsgAll" styleClass="error" />
                        <h:outputLabel id="lblBnkC58All" value="" styleClass="label"/>
                    </h:panelGrid>
                    <h:panelGrid rendered="#{RTGSCriteria.criteriaRenderValue == 'allValue'}" id="Panel5" columns="1" style="width:100%;height:20px;text-align:center;border:1px ridge #BED6F8;" styleClass="row2">
                        <h:outputLabel id="lblRecDetails" styleClass="label" value="Receiver Institution Details"/>
                    </h:panelGrid>
                    <h:panelGrid rendered="#{RTGSCriteria.criteriaRenderValue == 'allValue'}" id="panel6" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:27px;border:1px ridge #BED6F8;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblRecBnkCode" value="Bank/Branch Code" styleClass="label" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:panelGroup>
                            <h:inputText id="txtRecBank" styleClass="input" size="8" value="#{RTGSCriteria.receiverBankCode}" maxlength="6" onkeyup="this.value=this.value.toUpperCase();">
                            </h:inputText>
                            <h:inputText id="txtRecBranch" styleClass="input" size="8" value="#{RTGSCriteria.receiverBranchCode}" onkeyup="this.value=this.value.toUpperCase();" maxlength="6">
                                <%-- <a4j:support action="#{RTGSCriteria.getReceiverIfsCodes}" event="onblur" reRender="txtRecBankIso,txtRecBank,stxtMsg" oncomplete="setMask()"/> --%>
                            </h:inputText>
                        </h:panelGroup>
                        <h:outputLabel id="lblRecBnkCodeIso" value="IFS Code" styleClass="label" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtRecBankIso" styleClass="input" size="20"  value="#{RTGSCriteria.receiverIfsCode}" onkeyup="this.value=this.value.toUpperCase();" maxlength="11" >
                        </h:inputText>
                        <h:outputLabel id="lblRecBnfh" value="" styleClass="label"/>
                        <h:outputLabel id="lblRecBnkCogf6" value="" styleClass="label"/>
                    </h:panelGrid>
                    <h:panelGrid rendered="#{RTGSCriteria.criteriaRenderValue == 'allValue'}" id="Panel7" columns="1" style="width:100%;height:20px;text-align:center;border:1px ridge #BED6F8;" styleClass="row2">
                        <h:outputLabel id="lblOrdDetails" styleClass="label" value="Ordering Institution Details"/>
                    </h:panelGrid>
                    <h:panelGrid rendered="#{RTGSCriteria.criteriaRenderValue == 'allValue'}" id="panel8" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:27px;border:1px ridge #BED6F8;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblOrdBnkCode" value="Bank/Branch Code" styleClass="label"/>
                        <h:panelGroup>
                            <h:inputText id="txtOrdBank" styleClass="input" size="8" disabled="true" value="#{RTGSCriteria.orderingBankCode}">
                            </h:inputText>
                            <h:inputText id="txtOrdBranch" styleClass="input" size="8" disabled="true" value="#{RTGSCriteria.orderingBranchCode}">
                            </h:inputText>
                        </h:panelGroup>
                        <h:outputLabel id="lblOrdBnkCodeIso" value="IFS Code" styleClass="label"/>
                        <h:inputText id="txtOrdBankIso" styleClass="input" size="20" disabled="true" value="#{RTGSCriteria.orderingIfsCode}">
                        </h:inputText>
                        <h:outputLabel id="lblInstitutionId" value="Institution Id" styleClass="label"/>
                        <h:inputText id="txtInstitutionId" styleClass="input" size="20" value="#{RTGSCriteria.orderingInstId}" maxlength="6">
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid rendered="#{RTGSCriteria.criteriaRenderValue == 'allValue'}" id="panel9" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:27px;border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblName" value="Name" styleClass="label"/>
                        <h:inputText id="txtName" styleClass="input" size="20" value="#{RTGSCriteria.orderingInstName}" maxlength="49">
                        </h:inputText>
                        <h:outputLabel id="lblAddress1" value="Address1" styleClass="label"/>
                        <h:inputText id="txtAddress1" styleClass="input" size="20" value="#{RTGSCriteria.orderingInstAddress1}" maxlength="149">
                        </h:inputText>
                        <h:outputLabel id="lblAddress2" value="Address2" styleClass="label"/>
                        <h:inputText id="txtAddress2" styleClass="input" size="20" value="#{RTGSCriteria.orderingInstAddress2}" maxlength="149">
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid rendered="#{RTGSCriteria.criteriaRenderValue == 'allValue'}" id="panel11" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:27px;border:1px ridge #BED6F8;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblAddress3" value="Address3" styleClass="label"/>
                        <h:inputText id="txtAddress3" styleClass="input" size="20" value="#{RTGSCriteria.orderingInstAddress3}" maxlength="149">
                        </h:inputText>
                        <h:outputLabel id="lblfree" value="" styleClass="label"/>
                        <h:outputLabel id="lblfree1" value="" styleClass="label"/>
                        <h:outputLabel id="lblfree46" value="" styleClass="label"/>
                        <h:outputLabel id="lblfree17378" value="" styleClass="label"/>
                    </h:panelGrid>
                    <h:panelGrid rendered="#{RTGSCriteria.criteriaRenderValue == 'allValue'}" id="Panel12" columns="1" style="width:100%;height:20px;text-align:center;border:1px ridge #BED6F8;" styleClass="row2">
                        <h:outputLabel id="lblCustDetails" styleClass="label" value="Ordering Customer Details"/>
                    </h:panelGrid>
                    <h:panelGrid rendered="#{RTGSCriteria.criteriaRenderValue == 'allValue'}" id="panel13" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:27px;border:1px ridge #BED6F8;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblCustName" value="Cust. Name" styleClass="label" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtCustName" styleClass="input" size="20" value="#{RTGSCriteria.orderingCustName}" maxlength="49" disabled="true">
                        </h:inputText>
                        <h:outputLabel id="lblCustAddress1" value="Address1" styleClass="label" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtCustAddress1" styleClass="input" size="20" value="#{RTGSCriteria.orderingCustAddress1}" onkeyup="this.value=this.value.toUpperCase();" maxlength="149">
                        </h:inputText>
                        <h:outputLabel id="lblCustAddress2" value="Address2" styleClass="label"/>
                        <h:inputText id="txtCustAddress2" styleClass="input" size="20" value="#{RTGSCriteria.orderingCustAddress2}" onkeyup="this.value=this.value.toUpperCase();" maxlength="149">
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid rendered="#{RTGSCriteria.criteriaRenderValue == 'allValue'}" id="panel14" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:27px;border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblCustAddress3" value="Address3" styleClass="label"/>
                        <h:inputText id="txtCustAddress3" styleClass="input" size="20" value="#{RTGSCriteria.orderingCustAddress3}" maxlength="149">
                        </h:inputText>
                        <h:outputLabel id="lblCustAddrrg5" value="" styleClass="label"/>
                        <h:outputLabel id="lblCustg3" value="" styleClass="label"/>
                        <h:outputLabel id="lblCustsdv5" value="" styleClass="label"/>
                        <h:outputLabel id="lblCustg3hfr" value="" styleClass="label"/>
                    </h:panelGrid>
                    <h:panelGrid rendered="#{RTGSCriteria.criteriaRenderValue == 'allValue'}" id="Panel15" columns="1" style="width:100%;height:20px;text-align:center;border:1px ridge #BED6F8;" styleClass="row1">
                        <h:outputLabel id="lblAcInsDetails" styleClass="label" value="Account with Institution Details"/>
                    </h:panelGrid>
                    <h:panelGrid rendered="#{RTGSCriteria.criteriaRenderValue == 'allValue'}" id="panel16" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:27px;border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblAcInsBnkCode" value="Bank/Branch Code" styleClass="label"/>
                        <h:panelGroup>
                            <h:inputText id="txtAcInsBank" styleClass="input" size="8" value="#{RTGSCriteria.accInstBnkCode}" maxlength="6">
                            </h:inputText>
                            <h:inputText id="txtAcInsBranch" styleClass="input" size="8" value="#{RTGSCriteria.accInstBrnCode}" maxlength="6">
                            </h:inputText>
                        </h:panelGroup>
                        <h:outputLabel id="lblAcInsBnkCodeIso" value="IFS Code" styleClass="label"/>
                        <h:inputText id="txtAcInsBankIso" styleClass="input" size="20" value="#{RTGSCriteria.accInstBrnIFSCode}" maxlength="11">
                        </h:inputText>
                        <h:outputLabel id="lblAcInsInstitutionId" value="Institution Id" styleClass="label"/>
                        <h:inputText id="txtAcInsInstitutionId" styleClass="input" size="20" value="#{RTGSCriteria.accInstInsId}" maxlength="6">
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid rendered="#{RTGSCriteria.criteriaRenderValue == 'allValue'}" id="panel18" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:27px;border:1px ridge #BED6F8;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblAcInsName" value="Name" styleClass="label"/>
                        <h:inputText id="txtAcInsName" styleClass="input" size="20" value="#{RTGSCriteria.accInstName}" maxlength="49">
                        </h:inputText>
                        <h:outputLabel id="lblAcInsAddress1" value="Address1" styleClass="label"/>
                        <h:inputText id="txtAcInsAddress1" styleClass="input" size="20" value="#{RTGSCriteria.accInstAddress1}" maxlength="149">
                        </h:inputText>
                        <h:outputLabel id="lblAcInsAddress2" value="Address2" styleClass="label"/>
                        <h:inputText id="txtAcInsAddress2" styleClass="input" size="20" value="#{RTGSCriteria.accInstAddress2}" maxlength="149">
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid rendered="#{RTGSCriteria.criteriaRenderValue == 'allValue'}" id="panel19" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:27px;border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblAcInsAddress3" value="Address3" styleClass="label"/>
                        <h:inputText id="txtAcInsAddress3" styleClass="input" size="20" value="#{RTGSCriteria.accInstAddress3}" maxlength="149">
                        </h:inputText>
                        <h:outputLabel id="lblAcIn112" value="" styleClass="label"/>
                        <h:outputLabel id="lblAcIn7457" value="" styleClass="label"/>
                        <h:outputLabel id="lblAcIns897" value="" styleClass="label"/>
                        <h:outputLabel id="lblAcIns07" value="" styleClass="label"/>
                    </h:panelGrid>
                    <h:panelGrid rendered="#{RTGSCriteria.criteriaRenderValue == 'allValue'}" id="Panel20" columns="1" style="width:100%;height:20px;text-align:center;border:1px ridge #BED6F8;" styleClass="row1">
                        <h:outputLabel id="lblBenCustDetails" styleClass="label" value="Beneficiary Customer Details"/>
                    </h:panelGrid>
                    <h:panelGrid rendered="#{RTGSCriteria.criteriaRenderValue == 'allValue'}" id="panel21" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:27px;border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblBenCustAcId" value="A/c Id" styleClass="label" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtBenCustAcId" styleClass="input" size="20" value="#{RTGSCriteria.beneficiaryAccId}" onkeyup="this.value=this.value.toUpperCase();" maxlength="16">
                        </h:inputText>
                        <h:outputLabel id="lblBenCustName" value="Cust. Name" styleClass="label" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtBenCustName" styleClass="input" size="20" value="#{RTGSCriteria.beneficiaryCustName}" onkeyup="this.value=this.value.toUpperCase();" maxlength="50">
                        </h:inputText>
                        <h:outputLabel id="lblBenCustAddress1" value="Address1" styleClass="label" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtBenCustAddress1" styleClass="input" size="20" value="#{RTGSCriteria.beneficiaryCustAddress1}" onkeyup="this.value=this.value.toUpperCase();" maxlength="149">
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid rendered="#{RTGSCriteria.criteriaRenderValue == 'allValue'}" id="panel22" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:27px;border:1px ridge #BED6F8;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblBenCustAddress2" value="Address2" styleClass="label"/>
                        <h:inputText id="txtBenCustAddress2" styleClass="input" size="20" value="#{RTGSCriteria.beneficiaryCustAddress2}" maxlength="149">
                        </h:inputText>
                        <h:outputLabel id="lblBenCustAddress3" value="Address3" styleClass="label"/>
                        <h:inputText id="txtBenCustAddress3" styleClass="input" size="20" value="#{RTGSCriteria.beneficiaryCustAddress3}" maxlength="149" >
                        </h:inputText>
                        <h:outputLabel id="lblAdb" value="" styleClass="label"/>
                        <h:outputLabel id="lblAcj" value="" styleClass="label"/>
                    </h:panelGrid>
                    <h:panelGrid rendered="#{RTGSCriteria.criteriaRenderValue == 'allValue'}" id="Panel24" columns="1" style="width:100%;height:20px;text-align:center;border:1px ridge #BED6F8;" styleClass="row2">
                        <h:outputLabel id="lblPayDetails" styleClass="label" value="Payment Details"/>
                    </h:panelGrid>
                    <h:panelGrid rendered="#{RTGSCriteria.criteriaRenderValue == 'allValue'}" id="panel25" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:27px;border:1px ridge #BED6F8;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblPayLine1" value="Line1" styleClass="label"/>
                        <h:inputText id="txtPayLine1" styleClass="input" size="20" value="#{RTGSCriteria.paymentDetailsAddLine1}" maxlength="149">
                        </h:inputText>
                        <h:outputLabel id="lblPayLine2" value="Line2" styleClass="label"/>
                        <h:inputText id="txtPayLine2" styleClass="input" size="20" value="#{RTGSCriteria.paymentDetailsAddLine2}" maxlength="149">
                        </h:inputText>
                        <h:outputLabel id="lblPayLine3" value="Line3" styleClass="label"/>
                        <h:inputText id="txtPayLine3" styleClass="input" size="20" value="#{RTGSCriteria.paymentDetailsAddLine3}" maxlength="149">
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid rendered="#{RTGSCriteria.criteriaRenderValue == 'allValue'}" id="panel26" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:27px;border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblPayLine4" value="Line4" styleClass="label"/>
                        <h:inputText id="txtPayLine4" styleClass="input" size="20" value="#{RTGSCriteria.paymentDetailsAddLine4}" maxlength="149">
                        </h:inputText>
                        <h:outputLabel id="lblChargeDetails" value="Charge Details" styleClass="label"/>
                        <h:selectOneListbox id="ddChargeDetails" styleClass="ddlist" size="1" style="width: 120px" value="#{RTGSCriteria.paymentChargeDetails}">
                        </h:selectOneListbox>
                        <h:outputLabel id="lblAdbChargeDetails" value="" styleClass="label"/>
                        <h:outputLabel id="lblAcjChargeDetails" value="" styleClass="label"/>
                    </h:panelGrid>
                    <h:panelGrid rendered="#{RTGSCriteria.criteriaRenderValue == 'allValue'}" id="Panel28" columns="1" style="width:100%;height:20px;text-align:center;border:1px ridge #BED6F8;" styleClass="row1">
                        <h:outputLabel id="lblSenderRec" styleClass="label" value="Sender to Receiver Information"/>
                    </h:panelGrid>
                    <h:panelGrid rendered="#{RTGSCriteria.criteriaRenderValue == 'allValue'}" id="panel29" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:27px;border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblSenderRecLine1" value="Line1" styleClass="label"/>
                        <h:inputText id="txtSenderRecLine1" styleClass="input" size="20" value="#{RTGSCriteria.senderToRecLine1}" maxlength="149">
                        </h:inputText>
                        <h:outputLabel id="lblSenderRecLine2" value="Line2" styleClass="label"/>
                        <h:inputText id="txtSenderRecLine2" styleClass="input" size="20" value="#{RTGSCriteria.senderToRecLine2}" maxlength="149">
                        </h:inputText>
                        <h:outputLabel id="lblSenderRecLine3" value="Line3" styleClass="label"/>
                        <h:inputText id="txtSenderRecLine3" styleClass="input" size="20" value="#{RTGSCriteria.senderToRecLine3}" maxlength="149">
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid rendered="#{RTGSCriteria.criteriaRenderValue == 'allValue'}" id="panel30" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:27px;border:1px ridge #BED6F8;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblSenderRecLine4" value="Line4" styleClass="label"/>
                        <h:inputText id="txtSenderRecLine4" styleClass="input" size="20" value="#{RTGSCriteria.senderToRecLine4}" maxlength="149">
                        </h:inputText>
                        <h:outputLabel id="lblSenderRecLine5" value="Line5" styleClass="label"/>
                        <h:inputText id="txtSenderRecLine5" styleClass="input" size="20" value="#{RTGSCriteria.senderToRecLine5}" maxlength="149">
                        </h:inputText>
                        <h:outputLabel id="lblSenderRecLine6" value="Line6" styleClass="label"/>
                        <h:inputText id="txtSenderRecLine6" styleClass="input" size="20" value="#{RTGSCriteria.senderToRecLine6}" maxlength="149">
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid rendered="#{RTGSCriteria.criteriaRenderValue == 'allValue'}" id="footerPanelAll" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanelAll">
                            <a4j:commandButton  id="btnPreviousAll" value="Previous" actionListener="#{RTGSCriteria.getGeneralFromAllDetail}" reRender="stxtMsg,MainPanel" oncomplete="setMask()">
                            </a4j:commandButton>
                            <a4j:commandButton  id="btnSubmitAll" value="Submit" actionListener="#{RTGSCriteria.saveButton}" reRender="stxtMsg,MainPanel" disabled="#{RTGSCriteria.buttonFlag}" oncomplete="setMask()">
                            </a4j:commandButton>
                            <a4j:commandButton  id="btnRefreshAll" value="Refresh" actionListener="#{RTGSCriteria.getRefreshAllDetails}" reRender="MainPanel" oncomplete="setMask()">
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>

                    <%-- ////////////////////   Start AllDetail Penal    /////////////////// --%>

                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
