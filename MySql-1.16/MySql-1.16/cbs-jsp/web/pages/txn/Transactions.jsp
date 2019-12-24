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
            <title>Transaction Details</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />

            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                var row;
                function setMask() {
                    jQuery(".valueDt").mask("99/99/9999");
                    jQuery(".chkDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="txnForm">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{Transactions.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Transactions Details"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{Transactions.userName}"/>
                        </h:panelGroup>                         
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col5,col8" columns="3" id="bodyPanel" style="width:100%;text-align:center;">
                        <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="row1" style="width:100%;text-align:left;" styleClass="row1">
                                <h:outputLabel id="label1" styleClass="label" value="Account No."/>
                                <h:panelGroup>
                                    <a4j:region id="accNoRegion">
                                        <h:inputText id="txtAcNo" styleClass="input" onkeyup="this.value = this.value.toUpperCase();" maxlength="#{Transactions.acNoMaxLen}" value="#{Transactions.oldAcno}"
                                                     style="width:90px">
                                            <a4j:support event="onblur" ajaxSingle="true" actionListener="#{Transactions.accNoLostFocus}" oncomplete="if(#{Transactions.flag1=='false'}){#{rich:element('txtAcNo')}.focus();}else{ if(#{Transactions.aadharAlert}) #{rich:component('aadharAlert')}.show()} setMask();"
                                                         reRender="avalLimit,avalLimitLable,txtCheNo,chkDt,txtAmt,stxtCheFac,txtAcNo,acNoMsg,row2,row3,row4,row5,row6,message,errorMessage,accStatus,row44,ulpg,mpg,odfdrList,ddTranType,ddTranCrDr,labelEtoken,txtEtoken,mp,ddRelated,ddBy,txtDetail,valueDt,labelWV,stxtAccNo,stxtCustId,npaRow" limitToList="true" focus="ddTranType" />

                                        </h:inputText>
                                    </a4j:region>
                                    <h:outputText id="stxtAccNo" styleClass="output" value="#{Transactions.accNo}"/>
                                </h:panelGroup>
                                <h:outputLabel id="custLabelId" styleClass="label" value="Customer ID"/>
                                <h:outputText id="stxtCustId" styleClass="output" value="#{Transactions.custId}"/>
                            </h:panelGrid>

                            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="row2" style="width:100%;text-align:left;" styleClass="row2">
                                <h:outputLabel id="label6" styleClass="label" value="Account Name"/>
                                <h:outputText id="stxtAccName" styleClass="output" value="#{Transactions.accountName}"/>
                                <h:outputLabel id="label4" styleClass="label" value="Cheque Facility"/>
                                <h:outputText id="stxtCheFac" style="color:green;"styleClass="output" value="#{Transactions.chqFaci}"/>
                            </h:panelGrid>

                            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="row3" style="width:100%;text-align:left;" styleClass="row1">
                                <h:outputLabel id="label7" styleClass="label" value="JT/GU Name"/>
                                <h:outputText id="stxtJTGU" styleClass="output" value="#{Transactions.jtName}"/>
                                <h:outputLabel id="label8" styleClass="label" value="Operation Mode"/>
                                <h:outputText id="stxtOperMode" styleClass="output" value="#{Transactions.opMode}"/>
                            </h:panelGrid>

                            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="row4" style="width:100%;text-align:left;" styleClass="row2">
                                <h:outputLabel id="label10" styleClass="label" value="Opening Date"/>
                                <h:outputText id="stxtDOO" styleClass="output" value="#{Transactions.openDate}"/>
                                <h:outputLabel id="label11" styleClass="label" value="Adhoc Limit"/>
                                <h:outputText id="stxtAdhLimit" styleClass="output" value="#{Transactions.adhoclimit}"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="row44" style="width:100%;text-align:left;display:#{Transactions.limitDpLimitFlag};" styleClass="row1">
                                <h:outputLabel id="label44" styleClass="label" value="Limit"/>
                                <h:outputText id="stxtLim" styleClass="output" style="display:#{Transactions.limitFlag};" value="#{Transactions.limit}"/>
                                <h:outputLabel id="label45" styleClass="label" value="DP Limit" style="display:#{Transactions.dplimitFlag};"/>
                                <h:outputText id="stxtDpLimit" styleClass="output" style="display:#{Transactions.dplimitFlag};" value="#{Transactions.dpLimit}"/>
                            </h:panelGrid>

                            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="row5" style="width:100%;text-align:left;" styleClass="row1">
                                <h:outputLabel id="label9" styleClass="label" value="Balance"/>
                                <h:panelGroup id="panelGrpCrDr" layout="block">
                                    <h:outputText id="stxtBal" styleClass="output" value="#{Transactions.balance}">
                                        <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                                    </h:outputText>
                                    <h:outputText id="stxtBalCrDr" styleClass="output" value="#{Transactions.labelCrDr}"/>
                                </h:panelGroup>
                                <h:outputLabel id="avalLimitLable" styleClass="label" value="Available Limit" style="display:#{Transactions.limitDpLimitFlag};"/>
                                <h:outputLabel id="avalLimit" styleClass="label" value="#{Transactions.availLimit}" style="display:#{Transactions.limitDpLimitFlag};"/>    
                                <%--h:panelGroup id="panelGrpCrDr2" layout="block">
                                    <h:outputLabel id="labelSeqNo" styleClass="label" value="Seq. No." style="display:#{Transactions.seqNoYearFlag};"/>
                                    <h:inputText id="txtSeqNo" styleClass="input"style="width:60px;display:#{Transactions.seqNoYearFlag};" value="#{Transactions.seqNo}">
                                        <a4j:support event="onblur" actionListener="#{Transactions.onblurSundrySeqNo}" reRender="txtSeqNo,message"/>
                                    </h:inputText>
                                    <h:outputLabel id="avalLimitLable" styleClass="label" value="Available Limit" style="display:#{Transactions.limitDpLimitFlag};"/>
                                </h:panelGroup>
                                <h:panelGroup id="panelGrpCrDr3" layout="block">
                                    <h:outputLabel id="labelYear" styleClass="label" value="Year" style="display:#{Transactions.seqNoYearFlag};"/>
                                    <h:inputText id="txtYear" styleClass="input"style="width:60px;display:#{Transactions.seqNoYearFlag};" value="#{Transactions.seqYear}">
                                        <a4j:support event="onblur" actionListener="#{Transactions.onblurSundryYear}" reRender="message,txtYear,txtSeqNo"/>
                                    </h:inputText>
                                    
                                </h:panelGroup--%>

                            </h:panelGrid>
                            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="accStatus" style="width:100%;text-align:left;display:#{Transactions.flag3}" styleClass="row2">
                                <h:outputLabel id="lblAccStatus" styleClass="label" value="Account Status"/>
                                <h:outputText id="stxtAccStatus" styleClass="blink_text" style="color:blue;"value="#{Transactions.newAccNo}"/>
                                <h:outputLabel/>
                                <h:outputLabel/>
                            </h:panelGrid>

                            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="row6" style="width:100%;text-align:left;" styleClass="row2">
                                <h:outputLabel id="label13" styleClass="label" value="A/C Instruction"/>
                                <h:outputText id="stxtACIns" styleClass="output" value="#{Transactions.accInstruction}"/>
                                <h:outputLabel id="label12" styleClass="label" value="Pending Balance"/>
                                <h:outputText id="stxtPenBal" styleClass="output" value="#{Transactions.pendingBalance}"/>
                            </h:panelGrid>

                            <h:panelGrid columnClasses="col2,col6" columns="2" id="row7" style="width:100%;text-align:left;" styleClass="row1">
                                <h:outputLabel id="label14" styleClass="label" value="Transaction"/>
                                <h:panelGroup id="tt" layout="block" style="width:100%">
                                    <h:selectOneListbox id="ddTranType" styleClass="ddlist" size="1" value="#{Transactions.tranType}" disabled="#{Transactions.tranTypeVisibility}">
                                        <f:selectItems value="#{Transactions.tranTypeList}"/>
                                        <a4j:support actionListener="#{Transactions.tranTypeLostFocus}" event="onblur" reRender="upg,ulpg,mpg"/>
                                    </h:selectOneListbox>
                                    <a4j:region id="crdr">
                                        <h:selectOneListbox id="ddTranCrDr" styleClass="ddlist" size="1" value="#{Transactions.selectCrDr}">
                                            <f:selectItems value="#{Transactions.crDrList}"/>
                                            <a4j:support action="#{Transactions.tyLostFocus}" event="onblur" oncomplete="if(#{Transactions.poddFlag=='true'}){#{rich:component('mp')}.show();#{rich:element('ddMode')}.focus()} setMask();"reRender="npaRow,leftPanel,poRow9,ddBy,ddBillType,ddAlphaCode,byoutPut,ddMode,mp,message,ddRelated,errorMessage" focus="#{Transactions.focusId}"/>
                                        </h:selectOneListbox>
                                    </a4j:region>
                                </h:panelGroup>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="npaRow" style="width:100%;text-align:left;display:#{Transactions.displayNPA}" styleClass="row1">
                                <h:outputLabel id="lblNpa" styleClass="label" value="NPA Account No."/>
                                <h:panelGroup>
                                    <a4j:region id="npaAccNoRegion">
                                        <h:inputText id="stxtNpa" styleClass="input" value="#{Transactions.npaAcNo}" maxlength="#{Transactions.acNoMaxLen}" size="10">
                                            <a4j:support action="#{Transactions.getNPAAcctDetails}" event="onblur" focus="#{Transactions.focusId}" reRender="stxtNpa,txtNpanewAcNo,txtNpaName,errorMessage"/>
                                        </h:inputText>
                                    </a4j:region>
                                    <h:outputText id="txtNpanewAcNo" styleClass="output" value="#{Transactions.newNpaAcNo}"/>
                                </h:panelGroup>
                                <h:outputLabel id="lblNpaName" styleClass="label" value="NPA Account Name"/>
                                <h:outputText id="txtNpaName" styleClass="output" value="#{Transactions.npaAcName}"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col2,col7,col9" columns="3" id="row8" style="width:100%;text-align:left;" styleClass="row2">
                                <h:outputLabel id="label15" styleClass="label" value="Related To"/>
                                <h:selectOneListbox id="ddRelated" styleClass="ddlist" style="width:150px" size="1" value="#{Transactions.relatedTo}">
                                    <f:selectItems value="#{Transactions.relatedToList}"/>
                                    <a4j:support event="onblur" oncomplete="if(#{Transactions.relatedTo==8}){#{rich:element('lblChargesDetail')}.style.display='';
                                                 #{rich:element('ddChargesDetail')}.style.display='';} else{#{rich:element('lblChargesDetail')}.style.display='none';
                                                 #{rich:element('ddChargesDetail')}.style.display='none';} setMask();"/>

                                </h:selectOneListbox>
                                <h:panelGroup id="chargesDetail" layout="block">
                                    <h:outputLabel id="lblChargesDetail" styleClass="label" value="Charges Detail " style="display:none;" />
                                    <h:selectOneListbox id="ddChargesDetail" styleClass="ddlist" style="width:150px;display:none;" size="1" value="#{Transactions.chargeDtl}" >
                                        <f:selectItems value="#{Transactions.chargeDtlList}"/>
                                    </h:selectOneListbox>
                                </h:panelGroup>
                            </h:panelGrid>

                            <h:panelGrid columnClasses="col2,col6" columns="2" id="row9" style="width:100%;text-align:left;" styleClass="row2">
                                <h:outputLabel id="label16" styleClass="label" value="Details"/>
                                <h:inputText id="txtDetail" styleClass="input" style="width:320px"  onkeyup="this.value = this.value.toUpperCase();" value="#{Transactions.details}" maxlength="100"
                                             onkeypress="if(event.which == 35)event.preventDefault();">
                                    <a4j:support actionListener="#{Transactions.stopSingleQuote}" event="onblur" reRender="errorMessage"/>
                                </h:inputText>
                            </h:panelGrid>

                            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="row10" style="width:100%;text-align:left;" styleClass="row1">
                                <h:outputLabel id="label17" styleClass="label" value="By"/>
                                <h:panelGroup layout="block">
                                    <h:selectOneListbox id="ddBy" styleClass="ddlist" size="1" value="#{Transactions.by}">
                                        <f:selectItems value="#{Transactions.byList}"/>
                                        <a4j:support actionListener="#{Transactions.cmbByLostFocus}" event="onblur"
                                                     oncomplete="if(#{Transactions.chqDateFocus=='true'}){#{rich:element('chkDt')}.focus();}
                                                     else if(#{Transactions.tokenFocus=='true'}){#{rich:element('txtToken')}.focus();}
                                                     else if(#{Transactions.chqFocus=='true'}){#{rich:element('txtCheNo')}.focus();}
                                                     else if(#{Transactions.amountFocus=='true'}){#{rich:element('txtAmt')}.focus();} setMask();"
                                                     reRender="txtCheNo,chkDt,labelWV,txtAmt"/>

                                    </h:selectOneListbox>
                                </h:panelGroup>

                                <h:outputLabel id="label18" styleClass="label" value="Token No."/>
                                <h:panelGroup>
                                    <h:inputText id="txtToken" styleClass="input" style="width:45px" value="#{Transactions.tokenNumber}" maxlength="3">
                                        <a4j:support event="onblur" oncomplete="if((#{rich:element('ddBy')}.value=='2'
                                                     || #{rich:element('ddBy')}.value=='3') &&
                                                     #{rich:element('ddTranType')}.value=='2'
                                                     && #{Transactions.msgFlag==4}){#{rich:element('btnSave')}.focus();} setMask();" />

                                    </h:inputText>

                                    <h:inputText id="txtSubToken" styleClass="input" style="display:#{Transactions.tokenFlag};width:30px" value="#{Transactions.subTokenNo}" onkeyup="this.value = this.value.toUpperCase();" maxlength="3">
                                        <a4j:support actionListener="#{Transactions.checkDuplicateToken}" event="onblur" reRender="message" oncomplete="if(#{Transactions.duplicateToken=='true'}){#{rich:element('txtToken')}.focus();} setMask();" />
                                    </h:inputText>
                                </h:panelGroup>
                            </h:panelGrid>

                            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="row11" style="width:100%;text-align:left;" styleClass="row2">
                                <h:outputLabel id="label19" styleClass="label" value="Cheque No."/>
                                <h:inputText id="txtCheNo" styleClass="input" style="width:110px" value="#{Transactions.chqNo}" disabled="#{Transactions.chqNoFlag}" maxlength="10">
                                    <a4j:support  event="onblur" reRender="errorMessage" oncomplete="setMask();"/>

                                </h:inputText>
                                <h:outputLabel id="label20" styleClass="label" value="Cheque Date"/>
                                <h:inputText id="chkDt" value="#{Transactions.chqueDate}" disabled="#{Transactions.chqDateFlag}" size="10"
                                             onblur="if((#{rich:element('ddBy')}.value=='1'
                                             || #{rich:element('ddBy')}.value=='4') &&
                                             #{rich:element('ddTranType')}.value=='2'
                                             && #{Transactions.msgFlag==4}){#{rich:element('btnSave')}.focus();}" styleClass="input chkDt">
                                    <f:convertDateTime pattern="dd/MM/yyyy" />
                                    <a4j:support actionListener="#{Transactions.selectChqDate}" event="onblur"
                                                 oncomplete="if(#{Transactions.chqDateFocus=='false'}){
                                                 if(#{Transactions.chqFocus=='true'} && #{Transactions.tokenFocus=='false'} &&
                                                 #{Transactions.amountFocus=='false'}){#{rich:element('valueDt')}.focus();}
                                                 else if(#{Transactions.chqFocus=='false'} && #{Transactions.tokenFocus=='true'}
                                                 && #{Transactions.amountFocus=='false'}){
                                                 #{rich:element('txtToken')}.focus();}
                                                 else if(#{Transactions.chqFocus=='false'} && #{Transactions.tokenFocus=='false'} &&
                                                 #{Transactions.amountFocus=='true'}){#{rich:element('valueDt')}.focus();}}
                                                 else if(#{Transactions.chqDateFocus=='true'}){#{rich:element('chkDt')}.focus();} setMask();"
                                                 reRender="txtToken,txtCheNo,txtAmt,message"/>
                                </h:inputText>
                            </h:panelGrid> 

                            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="row12" style="width:100%;text-align:left;" styleClass="row1">
                                <h:outputLabel id="valueDtLbl" styleClass="label" value="Value Date" style="display:#{Transactions.valueDatedFlag}"/>
                                <h:inputText id="valueDt" styleClass="input valueDt" value="#{Transactions.valueDate}" size="10" readonly="#{Transactions.valueDatedFlag}">
                                    <a4j:support event="onblur" focus="txtAmt"/>
                                </h:inputText>

                                <h:outputLabel id="label21" styleClass="label" value="Amount"/>
                                <h:panelGroup>
                                    <a4j:region id="accNoAmtRegion">
                                        <h:inputText id="txtAmt"  styleClass="input" style="width:110px" value="#{Transactions.amountTxn}" disabled="#{Transactions.flag2}">
                                            <a4j:support actionListener="#{Transactions.amountTxnLostFocus}" event="onblur" reRender="message,txtAmt,labelSeqNo,txtSeqNo,labelYear,txtYear,ddTranType,ddRelated,strAlert" oncomplete="if(#{Transactions.amountTxnFocus=='true'}){#{rich:element('txtAmt')}.focus();} else{if(#{Transactions.seqNoYearFocus=='true'}){#{rich:element('txtSeqNo')}.focus();} else if(#{Transactions.seqNoYearFocus=='false'}){#{rich:element('btnSave')}.focus();}};setMask();if(#{Transactions.alertSubCode != ''}){#{rich:component('strAlert')}.show();}"/>
                                        </h:inputText>
                                    </a4j:region>   
                                </h:panelGroup>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="row122" style="width:100%;text-align:left;" styleClass="row2">
                                <h:outputLabel id="labelSeqNo" styleClass="label" value="Seq. No." style="display:#{Transactions.seqNoYearFlag};"/>
                                <h:inputText id="txtSeqNo" styleClass="input"style="width:60px;display:#{Transactions.seqNoYearFlag};" value="#{Transactions.seqNo}">
                                    <a4j:support event="onblur" actionListener="#{Transactions.onblurSundrySeqNo}" reRender="errorMessage,txtSeqNo,message"/>
                                </h:inputText>
                                <h:outputLabel id="labelYear" styleClass="label" value="Year" style="display:#{Transactions.seqNoYearFlag};"/>
                                <h:inputText id="txtYear" styleClass="input"style="width:60px;display:#{Transactions.seqNoYearFlag};" value="#{Transactions.seqYear}">
                                    <a4j:support event="onblur" actionListener="#{Transactions.onblurSundryYear}" reRender="errorMessage,message,txtYear,txtSeqNo" focus="btnSave"/>
                                </h:inputText>
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid columns="1" id="rightPanel" style="width:100%;height:413px;text-align:center;background-color:#e8eef7;">
                            <h:panelGrid columns="1" id="upg" cellpadding="0" cellspacing="0" style="width:100%;height:255px;text-align:center;background-color:#e8eef7;display:#{Transactions.flag4}" columnClasses="vtop">
                                <a4j:region>
                                    <rich:dataTable  var="dataItem" value="#{Transactions.dataList}"
                                                     rowClasses="gridrow1, gridrow2" id="taskList" rows="6" columnsWidth="100" rowKeyVar="row"
                                                     onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                     onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                        <f:facet name="header">
                                            <rich:columnGroup>
                                                <rich:column colspan="6"><h:outputText value="Transaction Details"/></rich:column>
                                                <rich:column breakBefore="true"><h:outputText value="Account No"/></rich:column>
                                                <rich:column><h:outputText value="Txn Type"/></rich:column>
                                                <rich:column><h:outputText value="Ty"/></rich:column>
                                                <rich:column><h:outputText value="Debit Amount"/></rich:column>
                                                <rich:column><h:outputText value="Credit Amount"/></rich:column>
                                                <rich:column><h:outputText value="Action"/></rich:column>
                                            </rich:columnGroup>
                                        </f:facet>

                                        <rich:column>
                                            <f:facet name="header"><h:outputText value=" " title=""></h:outputText></f:facet>
                                            <h:outputText value="#{dataItem.acNo}"/>
                                        </rich:column>
                                        <rich:column><h:outputText value="#{dataItem.tranTypeDesc}"/></rich:column>
                                        <rich:column><h:outputText  value="#{dataItem.tyDesc}"/></rich:column>
                                        <rich:column style="text-align:right"><h:outputText value="#{dataItem.drAmt}">
                                                <f:convertNumber type="currency" pattern="#.00" maxFractionDigits="2"/>
                                            </h:outputText>
                                        </rich:column>
                                        <rich:column style="text-align:right"><h:outputText  value="#{dataItem.crAmt}">
                                                <f:convertNumber type="currency" pattern="#.00" maxFractionDigits="2"/>
                                            </h:outputText>
                                        </rich:column>
                                        <rich:column>
                                            <a4j:commandLink ajaxSingle="true" id="deletelink" oncomplete="#{rich:component('deletePanel')}.show()">
                                                <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                                <f:setPropertyActionListener value="#{dataItem}" target="#{Transactions.currentItem}"/>
                                                <f:setPropertyActionListener value="#{row}" target="#{Transactions.currentRow}" />
                                            </a4j:commandLink>

                                        </rich:column>

                                    </rich:dataTable>
                                    <rich:datascroller id="xferScroller"align="left" for="taskList" maxPages="50" />
                                </a4j:region>
                                <rich:modalPanel id="deletePanel" autosized="true" width="200">
                                    <f:facet name="header">
                                        <h:outputText value="Delete this transaction?" style="padding-right:15px;" />
                                    </f:facet>
                                    <f:facet name="controls">
                                        <h:panelGroup>
                                            <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="hidelink2" />
                                            <rich:componentControl for="deletePanel" attachTo="hidelink2" operation="hide" event="onclick" />
                                        </h:panelGroup>
                                    </f:facet>
                                    <h:form>
                                        <table width="100%">
                                            <tbody>
                                                <tr>
                                                    <td align="center" width="50%">
                                                        <a4j:commandButton value="Yes" ajaxSingle="true" action="#{Transactions.delete}"
                                                                           oncomplete="#{rich:component('deletePanel')}.hide();" reRender="taskList,stxtCrAmt,stxtDrAmt,btnSave,btnBatchSave" />
                                                    </td>
                                                    <td align="center" width="50%">
                                                        <a4j:commandButton value="No" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </h:form>
                                </rich:modalPanel>
                            </h:panelGrid>

                            <h:panelGrid id="ulpg" columns="4" style="width:100%;height:30px;text-align:center;background-color:#e8eef7;display:#{Transactions.flag4}">
                                <h:outputLabel id="lblDrAMT" value="Dr Amount" styleClass="label" />
                                <h:outputText id="stxtDrAmt" styleClass="output" value="#{Transactions.sumDr}">
                                    <f:convertNumber type="currency" pattern="#.00" maxFractionDigits="2"/>
                                </h:outputText>
                                <h:outputLabel id="lblCrAmt" value="Cr Amount" styleClass="label" />
                                <h:outputText id="stxtCrAmt" styleClass="output" value="#{Transactions.sumCr}">
                                    <f:convertNumber type="currency" pattern="#.00" maxFractionDigits="2"/>
                                </h:outputText>
                            </h:panelGrid>

                            <h:panelGroup id="mpg" layout="block" style="width:100%;height:30px;text-align:center;background-color:#e8eef7;display:#{Transactions.flag4}">
                                <a4j:region id="xferRegion">
                                    <a4j:commandButton id="btnBatchSave" value="Save Batch" action="#{Transactions.saveXferDetail}"  reRender="acNoMsg,message,txtCheNo,txtAmt,taskList,btnBatchSave,stxtCrAmt,stxtDrAmt,btnSave,bodyPanel,txtAcNo,ddTranType,ddTranCrDr,ddRelated,ddChargesDetail,ddBy,stxtAccNo"
                                                       disabled="#{Transactions.flag5}"/>
                                </a4j:region>
                                <a4j:commandButton id="btnBatchCancel" value="Cancel Batch"  action="#{Transactions.cancelXferBatch}" reRender="bodyPanel,taskList,stxtCrAmt,stxtDrAmt,btnBatchSave" focus="txtAcNo"/>
                            </h:panelGroup>

                            <h:panelGrid id="lpg" columns="1" style="width:100%;height:70px;text-align:center;background-color:#e8eef7">
                                <h:outputText id="acNoMsg" styleClass="error" value="#{Transactions.accNoMsg}"/>
                                <h:outputText id="errorMessage" styleClass="error" value="#{Transactions.errorMsg}"/>
                                <h:outputText id="message" styleClass="msg" value="#{Transactions.message}"/>
                            </h:panelGrid>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:region id="saveRegion">
                                <a4j:commandButton  id="btnSave" value="Save" reRender="balCMsg,panCMsg,errorMessage,acNoMsg,message,txtCheNo,txtAmt,taskList,btnBatchSave,stxtCrAmt,stxtDrAmt,btnSave,bodyPanel,txtAcNo,ddTranType,ddTranCrDr,ddRelated,ddChargesDetail,ddBy,xferScroller,stxtAccNo,stxtCustId,npaRow,stxtNpa,txtNpanewAcNo,txtNpaName"
                                                    disabled="#{Transactions.flag6}" action="#{Transactions.cmdSaveCashClick}" onclick="#{rich:element('btnSave')}.disabled = true;"
                                                    oncomplete="if(#{Transactions.panRepFlag=='true'}){#{rich:component('showPanNoGrid')}.show();}
                                                    else if(#{Transactions.balConfirm=='true'}){#{rich:component('balConfirm')}.show();} setMask();   
                                                    if(#{Transactions.flag6 == 'false'}){#{rich:element('btnSave')}.disabled = false}">

                                </a4j:commandButton>
                            </a4j:region>
                            <a4j:commandButton id="btnCancel" value="Refresh"  reRender="bodyPanel,taskList,stxtDrAmt,stxtCrAmt,btnSave" action="#{Transactions.cancelClick}"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{Transactions.exit}" reRender="bodyPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid id="msgPanel" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputText id="stxtInstruction1" styleClass="output" value="F3-Disb.,F7-Sign,F4-A/C View,Ctrl+S-Stop Pay.,Ctrl+I-Cust. Inform.,F6-GL Head,Ctrl+P-PO Out. Details,Ctrl+U-Other Accounts, Ctrl+B-A/C Type Desc.,F10-Str Alert ! #{Transactions.intDtlVar}, F8-Demonination Detail" style="color:blue;"/>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:region id="keyRegion">
                    <a4j:jsFunction name="showAcDeatil" oncomplete="if(#{rich:element('txtAcNo')}.value!='') #{rich:component('acView')}.show();" reRender="acView" action="#{Transactions.acctViewAuthUnAuth}"/>
                    <a4j:jsFunction name="showSignDetail" oncomplete="if(#{rich:element('txtAcNo')}.value!='') #{rich:component('sigView')}.show();" reRender="sigView"/>
                    <a4j:jsFunction name="showGlHeadDetails" action="#{Transactions.selectGlHeadOnPressF6}" oncomplete="#{rich:component('glheadView')}.show();" reRender="glHeadList,dataScroGLHead,searchListId,searchValueId"/>
                    <a4j:jsFunction name="showStopPaymentDetails" action="#{Transactions.selectStopPayment}" oncomplete="if(#{rich:element('txtAcNo')}.value!='')#{rich:component('stopPaymentView')}.show();" reRender="stopPaymentList,stopDataScroll"/>
                    <a4j:jsFunction name="showPOOutstandDetails" action="#{Transactions.selectPOOutstandDetails}" oncomplete="if(#{rich:element('txtAcNo')}.value!='') #{rich:component('poOutstandingView')}.show();" reRender="poOutstandingList,scrollPoOutstand"/>
                    <a4j:jsFunction name="showLoanDisbDetails"  action="#{Transactions.loadDisbursementGrid}" reRender="loanDisbList,dataScroloanDisb" oncomplete="if(#{Transactions.loanDisbFlag=='true'}){#{rich:component('loanDisbView')}.show();} else {#{rich:component('loanDisbView')}.hide();}"/>
                    <a4j:jsFunction name="showCustDetails" oncomplete="#{rich:component('custDetailsView')}.show();"/>
                    <a4j:jsFunction name="showAccTypesDesc" action="#{Transactions.accTypeNatureDesc()}" oncomplete="#{rich:component('accTypeDescView')}.show();" reRender="accTypeDesc,scrollAccTypeDesc"/>
                    <a4j:jsFunction name="showIntDetail" action="#{Transactions.intDetails()}" oncomplete="#{rich:component('intDetailView')}.show();" reRender="intDetailView,intDetailId"/>
                    <a4j:jsFunction name="showOtherActDetail" action="#{Transactions.otherActDetails()}" oncomplete="#{rich:component('actDetailView')}.show();" reRender="actDetailView,actDetailId"/>                    
                    <a4j:jsFunction name="showAlertType" action="#{Transactions.selectAlertType()}" oncomplete="if(#{rich:element('txtAcNo')}.value!='') #{rich:component('alertId')}.show();" reRender="alertId"/>
                    <%--a4j:jsFunction name="showDenominationDetail" action="#{Transactions.pageInclude()}" oncomplete="#{rich:component('cashDenoView')}.show();" reRender="cashDenoView"/--%>
                    <a4j:jsFunction name="showDenominationDetail" action="#{Transactions.pageInclude()}" oncomplete="if(#{Transactions.popUpFlag==true}){#{rich:component('cashDenoView')}.show();} else {#{rich:component('cashDenoView')}.hide();}" reRender="cashDenoView"/>
                </a4j:region>

            </a4j:form>

            <rich:modalPanel id="custDetailsView" height="475" width="1000" style="align:right" autosized="true">
                <f:facet name="header">
                </f:facet>
                <f:facet name="controls">  
                    <h:panelGroup>
                        <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="custDetailsLink"/>
                        <rich:componentControl for="custDetailsView" attachTo="custDetailsLink" operation="hide" event="onclick"/>    
                    </h:panelGroup>
                </f:facet>
                <jsp:include page="TxnCustomerDetails.jsp"/>
            </rich:modalPanel>
            <rich:modalPanel id="poOutstandingView" height="350" width="500" style="align:right" autosized="true">
                <f:facet name="header">
                    <h:outputText value="PAYORDER OUTSTANDING" />
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="poOutstandingLink"/>
                        <rich:componentControl for="poOutstandingView" attachTo="poOutstandingLink" operation="hide" event="onclick"/>
                    </h:panelGroup>
                </f:facet>
                <a4j:form>
                    <h:panelGrid id="poOutstandingPanel" width="100%" style="background-color:#e8eef7;height:300px;" columnClasses="vtop">
                        <rich:dataTable  var="item" value="#{Transactions.poList}"
                                         rowClasses="gridrow1, gridrow2" id="poOutstandingList" rows="10"
                                         onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                         onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                         width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="7"></rich:column>
                                    <rich:column breakBefore="true"><h:outputText value="Fin. Year" /></rich:column>
                                    <rich:column><h:outputText value="Seq. No." /></rich:column>
                                    <rich:column><h:outputText value="Inst. No." /></rich:column>
                                    <rich:column><h:outputText value="Inst. Amt." /></rich:column>
                                    <rich:column><h:outputText value="Issued Date" /></rich:column>
                                    <rich:column><h:outputText value="In Favour Of" /></rich:column>
                                    <rich:column><h:outputText value="Issued By" /></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{item.finYear}"/></rich:column>
                            <rich:column><h:outputText value="#{item.seqNo}"/></rich:column>
                            <rich:column><h:outputText value="#{item.instNo}"/></rich:column>
                            <rich:column><h:outputText value="#{item.instAmt}"/></rich:column>
                            <rich:column><h:outputText value="#{item.issueDate}"/></rich:column>
                            <rich:column><h:outputText value="#{item.inFavourOf}"/></rich:column>
                            <rich:column><h:outputText value="#{item.issuedBy}"/></rich:column>
                        </rich:dataTable>
                        <rich:datascroller id="scrollPoOutstand"align="left" for="poOutstandingList" maxPages="20" />

                    </h:panelGrid>
                    <h:panelGrid id="payOrderFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="payOrderBtnPanel">
                            <a4j:commandButton id="payOrderClose" value="Close" onclick="#{rich:component('poOutstandingView')}.hide(); return false;">

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
                    <h:panelGrid id="Panel" style="width:100%;text-align:center;">
                        <h:panelGrid id="sigViewRow1" columns="6" columnClasses="col1,col3,col3,col3,col3,col3" width="100%" styleClass="row2" style="text-align:left;">
                            <h:outputLabel id="lblAccNo" styleClass="label" value="Account No."/>
                            <h:outputText id="txtAccNo" style="output" value="#{Transactions.accNo}"/>
                            <h:outputLabel id="lblAcName" styleClass="label" value="Name"/>
                            <h:outputText id="txtAcName" style="output" value="#{Transactions.accountName}"/>
                            <h:outputLabel id="lblCustomerId" styleClass="label" value="Customer Id"/>
                            <h:outputText id="txtCustomerId" style="output" value="#{Transactions.custId}"/>
                        </h:panelGrid>
                        <h:panelGrid id="sigViewRow2" columns="6" columnClasses="col1,col3,col3,col3,col3,col3" width="100%" styleClass="row1" style="text-align:left;">
                            <h:outputLabel id="lblOpMode" styleClass="label" value="Operation Mode"/>
                            <h:outputText id="txtOpMode" style="output" value="#{Transactions.opMode}"/>
                            <h:outputLabel id="lblOpDt" styleClass="label" value="Opening Date"/>
                            <h:outputText id="txtOpDt" style="output" value="#{Transactions.openDate}"/>
                            <h:outputLabel id="lblPanNo" styleClass="label" value="Pan No"/>
                            <h:outputText id="txtPanNo" style="output" value="#{Transactions.custPanNo}"/>
                        </h:panelGrid>
                        <h:panelGrid id="sigViewRow3" columns="6" columnClasses="col1,col3,col3,col3,col3,col3" width="100%" styleClass="row2" style="text-align:left;">
                            <h:outputLabel id="lblAadharNo" styleClass="label" value="Aadhar No"/>
                            <h:outputText id="txtAadharNo" style="output" value="#{Transactions.custAadharNo}"/>
                            <h:outputLabel id="lblJtName" styleClass="label" value="Jt.Name"/>
                            <h:outputText id="txtJtName" style="output" value="#{Transactions.jtName}"/>
                            <h:outputLabel id="lblSigAnnualTrunOver" styleClass="label" value="Annual Turnover"/>
                            <h:outputText id="txtSigAnnualTrunOver" style="output" value="#{Transactions.annualTurnover}"/>
                        </h:panelGrid>
                        <h:panelGrid id="sigViewRow4" columns="4" columnClasses="col1,col51,col3,col3" width="100%" styleClass="row1" style="text-align:left;">
                            <h:outputLabel id="lblAcInst" styleClass="label" value="A/c Instruction"/>
                            <h:outputText id="txtAcIns" style="output" value="#{Transactions.accInstruction}"/>
                            <h:outputLabel id="lblProfession" styleClass="label" value="Profession"/>
                            <h:outputText id="txtProfession" style="output" value="#{Transactions.profession}"/>
                        </h:panelGrid>
                        <h:panelGrid id="custIdPanelGrid" columns="4" columnClasses="col1,col51,col3,col3" width="100%" styleClass="row2" style="text-align:left;">
                            <h:outputLabel id="lblSigRiskCat" styleClass="label" value="Risk Category"/>
                            <h:outputText id="txtSigRiskCat" style="output" value="#{Transactions.riskCategorization}"/>
                            <h:outputLabel id="lblSigDpLimit" styleClass="label" value="DP Limit"/>
                            <h:outputText id="txtSigDpLimit" style="output" value="#{Transactions.dpLimit}"/>
                        </h:panelGrid>
                        <h:panelGroup layout="block" id="sigViewPanel" style="overflow:auto;width:700px;height:300px;text-align:center;background-color:#e8eef7">
                            <a4j:mediaOutput id="signature" element="img" createContent="#{Transactions.createSignature}" value="#{Transactions.accNo}"/>
                        </h:panelGroup>
                        <h:panelGrid id="sigViewFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup id="sigViewBtnPanel">
                                <a4j:commandButton id="sigViewClose" value="Close" onclick="#{rich:component('sigView')}.hide(); return false;">

                                </a4j:commandButton>
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                </a4j:form>
            </rich:modalPanel>
            <rich:modalPanel id="glheadView" height="350" width="500" style="align:right" autosized="true">
                <a4j:include viewId="glheadviewmp.jsp"/>
            </rich:modalPanel>
            <rich:modalPanel id="loanDisbView" height="350" width="500" style="align:right" autosized="true">
                <f:facet name="header">
                    <h:outputText value="Disbursement Schedule" />
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="loanDisbViewLink"/>
                        <rich:componentControl for="loanDisbView" attachTo="loanDisbViewLink" operation="hide" event="onclick"/>
                    </h:panelGroup>
                </f:facet>
                <a4j:form>
                    <h:panelGrid id="loanDisbPanel" width="100%" style="background-color:#e8eef7;height:300px;" columnClasses="vtop">
                        <rich:dataTable  var="item" value="#{Transactions.loanDisbGridList}"
                                         rowClasses="gridrow1, gridrow2" id="loanDisbList" rows="15"
                                         onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                         onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                         width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="4"></rich:column>
                                    <rich:column breakBefore="true"><h:outputText value="S. No." /></rich:column>
                                    <rich:column><h:outputText value="Amt Disbursed" /></rich:column>
                                    <rich:column><h:outputText value="Disb Date" /></rich:column>
                                    <rich:column><h:outputText value="Remarks" /></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{item.srNo}"/></rich:column>
                            <rich:column><h:outputText value="#{item.disburseAmt}"/></rich:column>
                            <rich:column><h:outputText value="#{item.disbDate}"/></rich:column>
                            <rich:column><h:outputText value="#{item.remarks}"/></rich:column>
                        </rich:dataTable>
                        <rich:datascroller id="dataScroloanDisb"align="left" for="loanDisbList" maxPages="20" />
                    </h:panelGrid>
                </a4j:form>
                <h:panelGrid id="loanDisbFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                    <h:panelGroup id="loanDisbBtnPanel">
                        <a4j:commandButton id="loanDisbClose" value="Close" onclick="#{rich:component('loanDisbView')}.hide(); return false;"/>
                    </h:panelGroup>
                </h:panelGrid>
            </rich:modalPanel>
            <rich:modalPanel id="stopPaymentView" height="350" width="400" style="align:right" autosized="true">
                <f:facet name="header">
                    <h:outputText value="Stop Payment Details" />
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="stopPaymentLink1"/>
                        <rich:componentControl for="stopPaymentView" attachTo="stopPaymentLink1" operation="hide" event="onclick"/>
                    </h:panelGroup>
                </f:facet>
                <a4j:form>
                    <h:panelGrid id="stopPaymentPanel" width="100%" style="background-color:#e8eef7;height:300px;" columnClasses="vtop">
                        <rich:dataTable  var="item" value="#{Transactions.stopPayList}"
                                         rowClasses="gridrow1, gridrow2" id="stopPaymentList" rows="15"
                                         onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                         onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                         width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="3"><h:outputText value="Stop Payment" /></rich:column>
                                    <rich:column breakBefore="true"><h:outputText value="Sno." /></rich:column>
                                    <rich:column><h:outputText value="Instrument No." /></rich:column>
                                    <rich:column><h:outputText value="Issued Date" /></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{item.srNo}"/></rich:column>
                            <rich:column><h:outputText value="#{item.instrumentNo}"/></rich:column>
                            <rich:column><h:outputText value="#{item.issueDate}"/></rich:column>
                        </rich:dataTable>
                        <rich:datascroller id="stopDataScroll" align="left" for="stopPaymentList" maxPages="20" />

                    </h:panelGrid>
                    <h:panelGrid id="stopPaymentFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="stopPaymentBtnPanel">
                            <a4j:commandButton id="stopPaymentClose" value="Close" onclick="#{rich:component('stopPaymentView')}.hide(); return false;">

                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </a4j:form>
            </rich:modalPanel>
            <rich:modalPanel id="mp" height="370" width="900" onshow="if(#{Transactions.tranType == 2} && #{Transactions.selectCrDr == 0}){#{rich:element('poRow9')}.setAttribute('style','display:table');
                             #{rich:element('poRow11')}.setAttribute('style','display:table');#{rich:element('poRow7')}.setAttribute('style','display:none');}
                             else{#{rich:element('poRow9')}.setAttribute('style','display:none');#{rich:element('poRow11')}.setAttribute('style','display:none');
                             #{rich:element('poRow7')}.setAttribute('style','display:table');}">
                <f:facet name="header">
                    <h:outputText value="PO/DD Detail" />
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="hidelink"/>
                        <rich:componentControl for="mp" attachTo="hidelink" operation="hide" event="onclick"/>
                    </h:panelGroup>
                </f:facet>
                <h:form>
                    <h:panelGrid id="poPanel" columns="2" columnClasses="col9,col9" width="100%">
                        <h:panelGrid id="poLeftPanel" columns="1" width="100%">

                            <h:panelGrid id="poRow1" columns="4" columnClasses="col2,col7,col2,col7" width="100%" styleClass="row1">
                                <h:outputLabel id="lblMode" styleClass="label" value="Mode"/>
                                <h:selectOneListbox id="ddMode" styleClass="ddlist" size="1" style="width:100px;" value="#{Transactions.poMode}">
                                    <f:selectItems value="#{Transactions.poModeList}"/>
                                    <a4j:support actionListener="#{Transactions.poModeLostFocus}" event="onblur" 
                                                 reRender="poBtnSave,lblInstNo,txtInstNo,lblIssueDt,txtIssueDt,lblSeqNo,txtSeqNo"/>

                                </h:selectOneListbox>
                                <h:outputLabel id="lblBy" styleClass="label" value="By"/>
                                <h:outputText id="byoutPut" styleClass="output" value="#{Transactions.poBy}"/>
                            </h:panelGrid>
                            <h:panelGrid id="poRow2" columns="3" columnClasses="col2,col7,col9" width="100%" styleClass="row2" style="text-align:left;">
                                <h:outputLabel id="lblBillType" styleClass="label" value="Bill Type"/>
                                <h:selectOneListbox id="ddBillType" styleClass="ddlist" size="1" style="width:100px;" value="#{Transactions.billType}">
                                    <f:selectItems value="#{Transactions.billTypeList}"/>
                                    <a4j:support actionListener="#{Transactions.poBillTypeLostFocus}" event="onblur" reRender="lblComm,txtComm,lblSeqNo,txtSeqNo,ddAlphaCode" 
                                                 oncomplete="if( #{rich:element('ddMode')}.value=='ISSUED')
                                                 {#{rich:element('ddAlphaCode')}.focus();}"/>

                                </h:selectOneListbox>
                                <h:panelGrid columns="3" columnClasses="col10,col11" width="100%">
                                    <h:outputLabel id="lblIssueDt" styleClass="label" value="Issue Date" rendered="#{Transactions.selectCrDr == 1}"/>
                                    <rich:calendar id="txtIssueDt" datePattern="dd/MM/yyyy" value="#{Transactions.poIssueDate}" rendered="#{Transactions.selectCrDr == 1}" inputSize="10">
                                        <a4j:support event="onchanged" focus="txtSeqNo"/>
                                    </rich:calendar>
                                </h:panelGrid>
                            </h:panelGrid>

                            <h:panelGrid id="poRow8" columns="4" columnClasses="col2,col7,col2,col7" width="100%" styleClass="row1">
                                <h:outputLabel id="lblSeqNo" styleClass="label" value="Sequence No" style="display:#{Transactions.seqNoFlag};"/>
                                <h:inputText id="txtSeqNo" styleClass="input" style="width:110px;display:#{Transactions.seqNoFlag};" value="#{Transactions.poSeqNo}">
                                    <a4j:support  event="onblur" actionListener="#{Transactions.poSeqNoLostFocus}" 
                                                  oncomplete="if(#{Transactions.poSeqIssuDtFlag=='true'}){#{rich:element('txtSeqNo')}.focus();} else{#{rich:element('txtSeqNo')}.disabled = true;#{rich:element('txtInstNo')}.disabled = true;
                                                  #{rich:element('ddAlphaCode')}.disabled = true;#{rich:element('txtAmount')}.disabled = true;
                                                  #{rich:element('txtComm')}.disabled = true;#{rich:element('txtInFavOf')}.disabled = true;} setMask();" 
                                                  reRender="txtCustName,txtInFavOf,txtComm,txtAmount,stxtBrAddr,stxtBrName,txtInstNo,stxtError,ddAlphaCode" focus="txtCustName"/>

                                </h:inputText>
                                <h:outputLabel id="lblInstNo" styleClass="label" value="Inst.No" style="display:#{Transactions.poInstNoFlag}"/>
                                <h:inputText id="txtInstNo" styleClass="input" style="width:110px;display:#{Transactions.poInstNoFlag}" value="#{Transactions.poInstNo}">

                                </h:inputText>
                            </h:panelGrid>

                            <h:panelGrid id="poRow3" columns="4" columnClasses="col2,col7,col2,col7" width="100%" styleClass="row2">
                                <h:outputLabel id="lblAlphaCode" styleClass="label" value="#{Transactions.lblAlpha}"/>
                                <h:selectOneListbox id="ddAlphaCode" styleClass="ddlist" size="1" value="#{Transactions.alphaCode}">
                                    <f:selectItems value="#{Transactions.alphaCodeList}"/>
                                    <a4j:support  event="onblur" actionListener="#{Transactions.onblurPOAlphaCode}" 
                                                  reRender="stxtBrName,stxtBrAddr,errorMessage"/>

                                </h:selectOneListbox>
                                <h:outputLabel id="lblBrName" styleClass="label" value="Branch Name"/>
                                <h:outputText id="stxtBrName" styleClass="output" value="#{Transactions.poBrnchName}"/>
                            </h:panelGrid>

                            <h:panelGrid id="poRow4" columns="2" columnClasses="col2,col6" width="100%" styleClass="row1">
                                <h:outputLabel id="lblBrAddr" styleClass="label" value="Branch Address"/>
                                <h:outputText id="stxtBrAddr" styleClass="output" value="#{Transactions.poBrnchAdd}"/>
                            </h:panelGrid>

                            <h:panelGrid id="poRow5" columns="4" columnClasses="col2,col7,col2,col7" width="100%" styleClass="row2">
                                <h:outputLabel id="lblAmount" styleClass="label" value="Amount"/>
                                <h:inputText id="txtAmount" styleClass="input" style="width:110px" value="#{Transactions.poAmount}">
                                    <a4j:support  event="onblur" actionListener="#{Transactions.poAmountLostFocus}"
                                                  reRender="txtComm,stxtError,poBtnSave" oncomplete="if(#{Transactions.poAmtFocus=='true'}){#{rich:element('txtAmount')}.focus();} else if(#{Transactions.poAmtFocus=='false'}){#{rich:element('txtComm')}.focus();}"/>

                                </h:inputText>
                                <h:outputLabel id="lblComm" styleClass="label" value="Commission" style="display:#{Transactions.commFlag}"/>
                                <h:inputText id="txtComm" styleClass="input" style="width:110px;display:#{Transactions.commFlag}" value="#{Transactions.poComm}">
                                    <a4j:support actionListener="#{Transactions.txtCommLostFocus}" event="onblur" reRender="stxtError,txtComm"/>
                                </h:inputText>
                            </h:panelGrid>

                            <h:panelGrid id="poRow6" columns="2" columnClasses="col2,col6" width="100%" styleClass="row1">
                                <h:outputLabel id="lblInFavOf" styleClass="label" value="In Favour Of" />
                                <h:inputText id="txtInFavOf" styleClass="input" style="width:325px"  onkeyup="this.value = this.value.toUpperCase();" value="#{Transactions.inFavOf}">
                                    <a4j:support actionListener="#{Transactions.infavofLostFocus}" event="onblur" 
                                                 oncomplete="if(#{Transactions.infavOfFlag=='true'}){#{rich:element('txtInFavOf')}.focus();}"/>
                                </h:inputText>

                            </h:panelGrid>
                            <h:panelGrid id="poRow9" columns="2" columnClasses="col2,col6" width="100%" styleClass="row2">
                                <h:outputLabel id="lblPoAcNo" styleClass="label" value="Account No."/>
                                <h:panelGroup>
                                    <h:inputText id="txtPoAcno" styleClass="input" style="width:100px" value="#{Transactions.poAccNum}" maxlength="#{Transactions.acNoMaxLen}">
                                        <a4j:support actionListener="#{Transactions.poAccNumLostFocus}" event="onblur" reRender="txtPoAcno,viewCustName,poRightPanel,billTypeList" oncomplete="if(#{Transactions.poAccNumFocus=='true'}){#{rich:element('txtPoAcno')}.focus();}"/>
                                    </h:inputText>
                                    <h:outputText styleClass="output" value="For GST Purpose"/>
                                </h:panelGroup>
                            </h:panelGrid>
                            <h:panelGrid id="poRow11" columns="2" columnClasses="col2,col6" width="100%" styleClass="row2">
                                <h:outputLabel id="lblViewCustName" styleClass="label" value="Customer Name"/>
                                <h:outputText id="viewCustName" styleClass="output" value="#{Transactions.poCustName}"/>
                            </h:panelGrid>
                            <h:panelGrid id="poRow7" columns="2" columnClasses="col2,col6" width="100%" styleClass="row2">
                                <h:outputLabel id="lblCustName" styleClass="label" value="Customer Name"/>
                                <h:inputText id="txtCustName" styleClass="input" style="width:325px"  onkeyup="this.value = this.value.toUpperCase();" value="#{Transactions.poCustName}">
                                    <a4j:support  actionListener="#{Transactions.poCustNameLostFocus}"event="onblur" reRender="poBtnSave,poRightPanel" />
                                </h:inputText>
                            </h:panelGrid>

                        </h:panelGrid>
                        <h:panelGrid id="poRightPanel" width="100%" style="background-color:#e8eef7;height:300px;" columnClasses="vtop">
                            <rich:dataTable  var="item" value="#{Transactions.billTypeBeanList}"
                                             rowClasses="gridrow1, gridrow2" id="billTypeList" rows="5"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                             width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="5"><h:outputText value="Bill Type Details" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Bill Type" /></rich:column>
                                        <rich:column><h:outputText value="Account No." /></rich:column>
                                        <rich:column><h:outputText value="Details." /></rich:column>
                                        <rich:column><h:outputText value="Inst No." /></rich:column>
                                        <rich:column><h:outputText value="Amount" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{item.billType}"/></rich:column>
                                <rich:column><h:outputText value="#{item.acNo}"/></rich:column>
                                <rich:column><h:outputText value="#{item.details}"/></rich:column>
                                <rich:column><h:outputText value="#{item.instNo}"/></rich:column>
                                <rich:column style="text-align:right">
                                    <h:outputText value="#{item.poAmount}">
                                        <f:convertNumber type="currency" pattern="#.00" minFractionDigits="2"/>
                                    </h:outputText>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="billTypeList" maxPages="20" />
                            <h:panelGrid id="errorPanel" columns="1" columnClasses="col8" width="100%" styleClass="row2">
                                <h:outputText id="stxtError" styleClass="output" style="text-align:center;color:blue;" value="#{Transactions.poError}"/>
                            </h:panelGrid>
                        </h:panelGrid>


                    </h:panelGrid>
                    <h:panelGrid id="poFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="poBtnPanel">
                            <a4j:commandButton id="poBtnSave" actionListener="#{Transactions.saveBillTypeTxn}"ajaxSingle="true"value="#{Transactions.saveCaptionFlag}"
                                               reRender="txtDetail,poPanel,txtCheNo,txtAmt,poBtnSave,chkDt" disabled="#{Transactions.poSaveFlag}" oncomplete="if(#{Transactions.flag10=='true'})
                                               {#{rich:element('poBtnSave')}.focus();} else if(#{Transactions.flag10=='false'}){#{rich:component('mp')}.hide();#{rich:element('ddRelated')}.focus();}"/>
                            <a4j:commandButton id="poBtnCancel" ajaxSingle="true"action="#{Transactions.cancelBillTypeTxn}" value="Cancel" oncomplete="#{rich:component('mp')}.hide()" focus="txtAcNo" reRender="poRow1,poRow2,poRow8,poRow3,poRow4,poRow5,poRow6,poRow7,poRow9,poRightPanel,ddMode,byoutPut,stxtBrName,stxtBrAddr"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:form>
            </rich:modalPanel>

            <rich:modalPanel id="balConfirm" autosized="true" width="200" onshow="#{rich:element('btnBalNo')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Balance Exceed Confirmation" style="padding-right:150px;"/>
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="balhidelink" />
                        <rich:componentControl for="balConfirm" attachTo="balhidelink" operation="hide" event="onclick" />
                    </h:panelGroup>
                </f:facet>
                <h:form>
                    <h:panelGrid id="balCMsgGrid" columns="1" columnClasses="col8" width="100%" styleClass="row2">
                        <h:outputLabel id="balCMsg" value="#{Transactions.balConfirmMsg}" styleClass="label" style="font-weight:bold;color:blue"/>
                    </h:panelGrid>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" ajaxSingle="true" action="#{Transactions.saveCashDetail()}" onclick="#{rich:component('balConfirm')}.hide();" reRender="errorMessage,acNoMsg,message,txtCheNo,txtAmt,taskList,btnBatchSave,stxtCrAmt,stxtDrAmt,btnSave,bodyPanel,txtAcNo,ddTranType,ddTranCrDr,ddRelated,ddChargesDetail,ddBy,xferScroller,stxtAccNo"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton id="btnBalNo" value="No" onclick="#{rich:component('balConfirm')}.hide();#{rich:element('txtAcNo')}.focus();" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>

            <rich:modalPanel id="strAlert" autosized="true" width="200" onshow="#{rich:element('btnStrNo')}.focus();">
                <f:facet name="header">
                    <h:outputText value="STR Confirmation" style="padding-right:150px;"/>
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="strHidelink" />
                        <rich:componentControl for="strAlert" attachTo="strHidelink" operation="hide" event="onclick" />
                    </h:panelGroup>
                </f:facet>
                <h:form>
                    <h:panelGrid id="strMsgGrid" columns="1" columnClasses="col8" width="100%" styleClass="row2">
                        <h:outputLabel id="strMsg" value="#{Transactions.alertSubCode}" styleClass="label" style="font-weight:bold;color:blue"/>
                    </h:panelGrid>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" ajaxSingle="true" action="#{Transactions.onStrAlertMod('Y')}" onclick="#{rich:component('strAlert')}.hide();" reRender="errorMessage" focus="btnSave" />
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton id="btnStrNo" value="No" action="#{Transactions.onStrAlertMod('N')}" onclick="#{rich:component('strAlert')}.hide();" reRender="errorMessage" focus="btnSave"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>

            <rich:modalPanel id="showPanNoGrid" autosized="true" width="200" onshow="#{rich:element('btnPanNo')}.focus();">
                <f:facet name="header">
                    <h:outputText value="PAN Number Confirmation" style="padding-right:150px;"/>
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="hidelink1" />
                        <rich:componentControl for="showPanNoGrid" attachTo="hidelink1" operation="hide" event="onclick" />
                    </h:panelGroup>
                </f:facet>
                <h:form>
                    <h:panelGrid id="panCMsgGrid" columns="1" columnClasses="col8" width="100%" styleClass="row2">
                        <h:outputLabel id="panCMsg" value="#{Transactions.panConfirmMsg}" styleClass="label"/>
                    </h:panelGrid>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" ajaxSingle="true" action="#{Transactions.saveCashDetail()}"onclick="#{rich:component('showPanNoGrid')}.hide();" reRender="errorMessage,acNoMsg,message,txtCheNo,txtAmt,taskList,btnBatchSave,stxtCrAmt,stxtDrAmt,btnSave,bodyPanel,txtAcNo,ddTranType,ddTranCrDr,ddRelated,ddChargesDetail,ddBy,xferScroller,stxtAccNo"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton id="btnPanNo" value="No" onclick="#{rich:component('showPanNoGrid')}.hide();#{rich:element('txtAcNo')}.focus();" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
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
                            <h:outputText id="txtAccNo" style="output" value="#{Transactions.accNo}"/>
                            <h:outputLabel id="lblOpeningBal" styleClass="label" value="Opening Balance:"/>
                            <h:outputText id="txtOpeningBal" style="output" value="#{Transactions.openBalance}">
                                <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                            </h:outputText>
                            <h:outputLabel id="lblPresentBal" styleClass="label" value="Present Balance:"/>
                            <h:outputText id="txtPresentBal" style="output" value="#{Transactions.presentBalance}">
                                <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                            </h:outputText>
                        </h:panelGrid>

                        <h:panelGrid id="acViewAuthGrid" width="100%" style="background-color:#e8eef7;height:260px" columnClasses="vtop">
                            <rich:dataTable  var="txnItem" value="#{Transactions.txnDetailList}"
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
                                        <rich:column><h:outputText value="Withdrawal" /></rich:column>
                                        <rich:column><h:outputText value="Deposit" /></rich:column>
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
                            <rich:dataTable  var="unAuthItem" value="#{Transactions.txnDetailUnAuthList}"
                                             rowClasses="gridrow1, gridrow2" id="pendingTxnList" rows="5"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                             width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="9"><h:outputText value="Today's Pending Authorization" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Date" /></rich:column>
                                        <rich:column><h:outputText value="Particulars" /></rich:column>
                                        <rich:column><h:outputText value="Cheque No." /></rich:column>
                                        <rich:column><h:outputText value="Withdrawal" /></rich:column>
                                        <rich:column><h:outputText value="Deposit" /></rich:column>
                                        <rich:column><h:outputText value="Balance" /></rich:column>
                                        <rich:column><h:outputText value="Entered By" /></rich:column>
                                        <rich:column><h:outputText value="Authorized" /></rich:column>
                                        <rich:column><h:outputText value="Origin Branch" /></rich:column>
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
                                <rich:column><h:outputText value="#{unAuthItem.orgnBrId}"/></rich:column>
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
            <rich:modalPanel id="accTypeDescView" height="350" width="500" style="align:right" autosized="true">
                <f:facet name="header">
                    <h:outputText value="Account Type Description" />
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="accTypeDescLink"/>
                        <rich:componentControl for="accTypeDescView" attachTo="accTypeDescLink" operation="hide" event="onclick"/>
                    </h:panelGroup>
                </f:facet>
                <a4j:form>
                    <h:panelGrid id="accTypeDescPanel" width="100%" style="background-color:#e8eef7;height:300px;" columnClasses="vtop">
                        <rich:dataTable  var="item" value="#{Transactions.accTypeDescList}"
                                         rowClasses="gridrow1, gridrow2" id="accTypeDesc" rows="10"
                                         onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                         onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                         width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="7"></rich:column>
                                    <rich:column breakBefore="true"><h:outputText value="A/C Code" /></rich:column>
                                    <rich:column><h:outputText value="A/C Type" /></rich:column>
                                    <rich:column><h:outputText value="A/C Nature" /></rich:column>
                                    <rich:column><h:outputText value="A/C Description" /></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{item.acctCode}"/></rich:column>
                            <rich:column><h:outputText value="#{item.accType}"/></rich:column>
                            <rich:column><h:outputText value="#{item.accNature}"/></rich:column>
                            <rich:column><h:outputText value="#{item.accDesc}"/></rich:column>
                        </rich:dataTable>
                        <rich:datascroller id="scrollAccTypeDesc"align="left" for="accTypeDesc" maxPages="10" />

                    </h:panelGrid>
                    <h:panelGrid id="accTypeDescFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup>
                            <a4j:commandButton id="accTypeDescClose" value="Close" onclick="#{rich:component('accTypeDescView')}.hide(); return false;">
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </a4j:form>
            </rich:modalPanel>
            <rich:modalPanel id="intDetailView" height="350" width="750" style="align:right" autosized="true">
                <f:facet name="header">
                    <h:outputText value="Interest Detail" />
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="intDtlLink"/>
                        <rich:componentControl for="intDetailView" attachTo="intDtlLink" operation="hide" event="onclick"/>
                    </h:panelGroup>
                </f:facet>
                <a4j:form>
                    <h:panelGrid id="intDtlPanel" width="100%" style="background-color:#e8eef7;height:300px;" columnClasses="vtop">
                        <rich:dataTable var="item" value="#{Transactions.intCalc}"
                                        rowClasses="gridrow1, gridrow2" id="intDetailId" rows="10"
                                        onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                        onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                        width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="6"><h:outputText value="Details"/></rich:column>
                                    <rich:column breakBefore="true"><h:outputText value="S.No"  /> </rich:column>
                                    <rich:column><h:outputText value="Account No." /></rich:column>
                                    <rich:column><h:outputText value="From Date" /></rich:column>
                                    <rich:column><h:outputText value="To Date" /></rich:column>
                                    <rich:column><h:outputText value="ROI" /></rich:column>
                                    <rich:column><h:outputText value="Interest Amt" /></rich:column>                                    
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column style="text-align:center;width:3%;"><h:outputText value="#{item.sNo}"/></rich:column>
                            <rich:column style="text-align:center;width:8%;"><h:outputText value="#{item.acNo}"/></rich:column>
                            <rich:column style="text-align:center;width:10%;"><h:outputText value="#{item.firstDt}"/></rich:column>
                            <rich:column style="text-align:center;width:10%;"><h:outputText value="#{item.lastDt}"/></rich:column>
                            <rich:column style="text-align:right;width:7%;">
                                <h:outputText value="#{item.roi}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                            </rich:column>
                            <rich:column style="text-align:right;width:10%;">
                                <h:outputText value="#{item.totalInt}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                            </rich:column>                            
                        </rich:dataTable>
                        <rich:datascroller align="left" for="intDetailId" maxPages="20" />
                    </h:panelGrid>
                    <h:panelGrid id="intDetailFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup>
                            <a4j:commandButton id="intDetailClose" value="Close" onclick="#{rich:component('intDetailView')}.hide(); return false;">
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </a4j:form>
            </rich:modalPanel>

            <rich:modalPanel id="actDetailView" height="300" width="450" style="align:right" autosized="false">
                <f:facet name="header">
                    <h:outputText value="All Accounts Detail" />
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="actDtlLink"/>
                        <rich:componentControl for="actDetailView" attachTo="actDtlLink" operation="hide" event="onclick"/>
                    </h:panelGroup>
                </f:facet>
                <a4j:form>
                    <h:panelGrid id="actDtlPanel" width="100%" style="background-color:#e8eef7;height:250px;" columnClasses="vtop">
                        <rich:dataTable var="item" value="#{Transactions.otherActList}"
                                        rowClasses="gridrow1, gridrow2" id="actDetailId" rows="6"
                                        onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                        onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                        width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column><h:outputText value="Customer Id"  /> </rich:column>
                                    <rich:column><h:outputText value="Account No." /></rich:column>
                                    <rich:column><h:outputText value="Account Status" /></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column style="text-align:center;width:3%;"><h:outputText value="#{item.custId}"/></rich:column>
                            <rich:column style="text-align:center;width:8%;"><h:outputText value="#{item.acNo}"/></rich:column>
                            <rich:column style="text-align:center;width:8%;"><h:outputText value="#{item.name}"/></rich:column>
                        </rich:dataTable>
                        <rich:datascroller align="left" for="actDetailId" maxPages="20" />
                    </h:panelGrid>
                    <h:panelGrid id="actDetailFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup>
                            <a4j:commandButton id="actDetailClose" value="Close" onclick="#{rich:component('actDetailView')}.hide(); return false;">
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </a4j:form>
            </rich:modalPanel>


            <rich:modalPanel id="alertId" height="150" width="500" style="align:right" autosized="true">
                <f:facet name="header">
                    <h:outputText value="Str Alert Indicators !" />
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="alertLink"/>
                        <rich:componentControl for="alertId" attachTo="alertLink" operation="hide" event="onclick"/>
                    </h:panelGroup>
                </f:facet>
                <a4j:form>
                    <h:panelGrid id="alertPanel" style="width:100%;text-align:center;">
                        <h:panelGrid id="AlertRow1" columns="2" columnClasses="col1,col2,col1,col2,col1,col1," width="100%" styleClass="row1" style="text-align:left;">                           
                            <h:outputLabel id="alertl14" styleClass="label" value="Alert Type"/>
                            <h:selectOneListbox id="ddalertType" styleClass="ddlist" size="1" value="#{Transactions.alertType}" style="width:200px">
                                <f:selectItems value="#{Transactions.alertTypeList}"/>
                                <a4j:support actionListener="#{Transactions.onAlertCode}" event="onblur" reRender="ddalertCode"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid id="AlertRow2" columns="2" columnClasses="col1,col2,col1,col2,col1,col1," width="100%" styleClass="row2" style="text-align:left;">
                            <h:outputLabel id="alertl15" styleClass="label" value="Alert Code"/>
                            <h:selectOneListbox id="ddalertCode" styleClass="ddlist" size="1" value="#{Transactions.alertCode}" style="width:200px">
                                <f:selectItems value="#{Transactions.alertCodeList}"/>
                                <a4j:support actionListener="#{Transactions.onAlertCodeSub}" event="onblur" reRender="ddalertCodesub"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid id="AlertRow3" columns="2" columnClasses="col1,col2,col1,col2,col1,col1," width="100%" styleClass="row1" style="text-align:left;">
                            <h:outputLabel id="alertl16" styleClass="label" value="Alert Sub Code"/>
                            <h:panelGroup id="alertPanael">
                                <h:selectOneListbox id="ddalertCodesub" styleClass="ddlist" size="1" value="#{Transactions.alertSubCode}" style="width:200px">
                                    <f:selectItems value="#{Transactions.alertSubCodeList}"/> 
                                    <a4j:support actionListener="#{Transactions.onAlertCodeSubDesc}" event="onchange" reRender="subcodeDescId"/>
                                </h:selectOneListbox>
                                <h:outputText id="subcodeDescId" value="#{Transactions.subCodeDesc}" styleClass="output" style="color:green"/>   
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid id="AlertRow4" columns="2" columnClasses="col1,col2,col1,col2,col1,col1," width="100%" styleClass="row2" style="text-align:left;">

                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="alertPanel1" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup>
                            <a4j:commandButton id="alertClose" value="OK" onclick="#{rich:component('alertId')}.hide(); return false;">
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </a4j:form>
            </rich:modalPanel>

            <rich:modalPanel id="cashDenoView" height="475" width="1000" style="align:right" autosized="true">
                <f:facet name="header">
                    <h:outputText value="Currency Details" />
                </f:facet>
                <%--f:facet name="controls">  
                    <h:panelGroup>
                        <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="cashDenoLink"/>
                        <rich:componentControl for="cashDenoView" attachTo="cashDenoLink" operation="hide" event="onclick"/>    
                    </h:panelGroup>
                </f:facet--%>
                <jsp:include page="cashDenominationDtl.jsp"/>
            </rich:modalPanel>
            <rich:modalPanel id="aadharAlert" autosized="true" width="320" >
                <f:facet name="header">
                    <h:outputText value="Aadhar Alert" style="color:blue;padding-right:15px;font-weight:bold;font-size:12px" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:justify;font-weight:bold;">
                        <tbody>
                            <tr style="height:60px">
                                <td>
                                    <h:outputText value="Please ask to Customer to submit the Aadhar Card" style="color:blue;font-size:12px"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center">
                                    <a4j:commandButton value="OK" id="btnY" onclick="#{rich:component('aadharAlert')}.hide()" reRender="aadharAlert"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>

            <a4j:status onstart="#{rich:component('wait6')}.show()" onstop="#{rich:component('wait6')}.hide()" for="npaAccNoRegion"/>
            <rich:modalPanel id="wait7" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>

            <a4j:status onstart="#{rich:component('wait6')}.show()" onstop="#{rich:component('wait6')}.hide()" for="accNoAmtRegion"/>
            <rich:modalPanel id="wait6" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
            <a4j:status onstart="#{rich:component('wait5')}.show()" onstop="#{rich:component('wait5')}.hide()" for="crdr"/>
            <rich:modalPanel id="wait5" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>

            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="saveRegion"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
            <a4j:status onstart="#{rich:component('wait1')}.show()" onstop="#{rich:component('wait1')}.hide()" for="keyRegion"/>
            <rich:modalPanel id="wait1" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
            <a4j:status onstart="#{rich:component('wait2')}.show()" onstop="#{rich:component('wait2')}.hide()" for="xferRegion"/>
            <rich:modalPanel id="wait2" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
            <a4j:status onstart="#{rich:component('wait3')}.show()" onstop="#{rich:component('wait3')}.hide()" for="accNoRegion"/>
            <rich:modalPanel id="wait3" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
            <rich:hotKey id="F4Key"key="F4" handler="showAcDeatil();"/>
            <rich:hotKey key="F7" handler="showSignDetail();"/>
            <rich:hotKey key="F6" handler="showGlHeadDetails();"/>
            <rich:hotKey key="Ctrl+S" handler="showStopPaymentDetails(); return false;"/>
            <rich:hotKey key="Ctrl+P" handler="showPOOutstandDetails(); return false;"/>
            <rich:hotKey key="Ctrl+I" handler="showCustDetails(); return false;"/>
            <rich:hotKey key="F3" handler="showLoanDisbDetails(); return false;"/>
            <rich:hotKey key="Ctrl+B" handler="showAccTypesDesc(); return false;"/>
            <rich:hotKey key="Ctrl+L" handler="showIntDetail(); return false;"/>
            <rich:hotKey key="Ctrl+U" handler="showOtherActDetail(); return false;"/>
            <rich:hotKey key="F10" handler="showAlertType();"/>
            <rich:hotKey key="F8" handler="showDenominationDetail(); return false;"/>
            <a4j:region>
                <h:form>
                    <a4j:poll id="poll" interval="#{Transactions.pollInterval}" enabled="#{Transactions.pollEnabled}" reRender="poll,pymtAlert" 
                              oncomplete="#{rich:component('pymtAlert')}.show()" timeout="5000"/>
                </h:form>
            </a4j:region>
            <a4j:region>
                <rich:modalPanel id="pymtAlert" autosized="true" width="310" >
                    <f:facet name="header">
                        <h:outputText value="Payment Alert" style="color:red;padding-right:15px;font-size:15px;font-weight:bold;font-size:15px" />
                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:justify;font-weight:bold;">
                            <tbody>
                                <tr style="height:60px">
                                    <td>
                                        <h:outputText value="#{Transactions.pymtAlertMsg}" style="color:red;font-weight:bold;font-size:15px"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center">
                                        <a4j:commandButton value="OK" id="btnYes" onclick="#{rich:component('pymtAlert')}.show()" reRender="pymtAlert"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
            </a4j:region>
        </body>
    </html>
</f:view>

