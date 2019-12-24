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
            <title>Outward Clearing Register</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />

            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                var row;
                function setMask() {
                    jQuery(".calInstDate").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel0" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date:"/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{OutwardClearingRegister.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblAccountMaintenanceRegister" styleClass="headerLabel" value="Outward Clearing Register"/>
                        <h:panelGroup id="a4" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User:"/>
                            <h:outputText styleClass="output" id="stxtUser" value="#{OutwardClearingRegister.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col5,col8" columns="2" id="bodyPanel" width="100%">
                        <h:panelGrid columns="1" id="gridPanel4" width="100%">
                            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel2" style="width:100%;" styleClass="row2">
                                <h:outputLabel id="lblCircleType" styleClass="label" value="Circle Type"/>
                                <h:selectOneListbox id="ddCircleType"  style="width:100px;" styleClass="ddlist" size="1" value="#{OutwardClearingRegister.circleType}" tabindex="1">
                                    <f:selectItems value="#{OutwardClearingRegister.circleTypeList}"/>
                                    <a4j:support actionListener="#{OutwardClearingRegister.visibilityInvisibilityDropdownBankNameAndAddrEntryTypeGrid}" event="onblur" oncomplete="if(#{OutwardClearingRegister.flag2=='false'}){#{rich:element('gridPanel14')}.hide();#{rich:element('gridPanel7')}.show();} else if(#{OutwardClearingRegister.flag2=='true'}){#{rich:element('gridPanel14')}.show();#{rich:element('gridPanel7')}.hide();}" reRender="gridPanel14,gridPanel7,ddRegisDate,stxtError,taskList2,gridPanelParameter1,gridPanelParameter2" focus="ddRegisDate"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblRegisDate" styleClass="label" value="Register Date"/>
                                <h:selectOneListbox id="ddRegisDate"  size="1"styleClass="ddlist"value="#{OutwardClearingRegister.regisDate}" style="width:100px;" tabindex="2">
                                    <f:selectItems value="#{OutwardClearingRegister.regisDateList}"/>
                                    <a4j:support actionListener="#{OutwardClearingRegister.getUnverifiedInstrumentsOnRegisDatelostFocus}" event="onblur" reRender="taskList1,stxtError,taskList2,gridPanel10,txtAccNo" focus="txtAccNo"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblHide1" value=""/>
                                <h:outputLabel id="lblHide2" value=""/>
                                <%--<h:selectBooleanCheckbox id="chkMltplCreditMode" />
                                    <h:outputLabel id="lblMltplCreditMode" styleClass="label" value="Multiple Credit Mode"/>--%>

                            </h:panelGrid>
                            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel3" style="width:100%;" styleClass="row1">
                                <h:outputLabel id="lblAccNo" styleClass="label" value="Account Number"/>
                                <h:panelGroup layout="block">
                                    <h:inputText id="txtAccNo"  disabled="#{OutwardClearingRegister.flag10}"styleClass="input" maxlength="#{OutwardClearingRegister.acNoMaxLen}" value="#{OutwardClearingRegister.oldAccNo}" tabindex="3" style="width:100px;">
                                        <a4j:support actionListener="#{OutwardClearingRegister.getDetailsOnBlurAccountNo}"  event="onblur" 
                                                     focus="txtInstAmt" reRender="stxtError,lblCustName,stxtCustName,stxtacctOpeningDate,stxtJointName,
                                                     lblRelatedTo,ddRelatedTo,gridPanel13,txtInstAmt,txtInstNo,txtRemarks,calInstDate,txtDrawerName,
                                                     txtDrawerAccNo,txtBankCode1,txtBankCode2,txtBankCode3,txtBankName,txtBranch,txtAccNo,taskList2,
                                                     stxtAccStatus,stxtAccNo,txtAccountName" oncomplete="if(#{OutwardClearingRegister.flag5=='true'} 
                                                     && #{OutwardClearingRegister.accStatusFlag=='2'}){#{rich:element('lblCustName')}.show();
                                                     #{rich:element('stxtCustName')}.show();#{rich:element('lblacctOpeningDate')}.show();
                                                     #{rich:element('stxtacctOpeningDate')}.show();#{rich:component('showInoperativePanel')}.show();} 
                                                     else if(#{OutwardClearingRegister.flag5=='true'} && #{OutwardClearingRegister.accStatusFlag!='2'})
                                                     {#{rich:element('lblCustName')}.show();#{rich:element('stxtCustName')}.show();#{rich:component('showInoperativePanel')}.hide();} 
                                                     else if(#{OutwardClearingRegister.flag5=='false'}){#{rich:element('lblCustName')}.hide();
                                                     #{rich:component('stxtCustName')}.hide();#{rich:element('lblacctOpeningDate')}.hide();
                                                     #{rich:component('stxtacctOpeningDate')}.hide();} setMask();"/>
                                    </h:inputText>
                                    <h:outputText id="stxtAccNo" styleClass="output" value="#{OutwardClearingRegister.accountNo}"/>
                                </h:panelGroup>
                                <h:outputLabel id="lblCustName" styleClass="label" value="Customer name" style="display:none;"/>
                                <h:outputText id="stxtCustName" styleClass="output" value="#{OutwardClearingRegister.custName}" style="display:none;"/>
                                <h:outputLabel id="lblHide3" value=""/>
                                <h:outputLabel id="lblHide4" value=""/>
                            </h:panelGrid>
                            <%--<h:panelGrid id="gridPanel4" columns="1" styleClass="row2" style="width:100%;text-align:center;">
                                <h:outputLabel id="lblOutClearRegis" styleClass="label" value="Outward Clearing Register "/>
                            </h:panelGrid>--%>
                            <h:panelGrid id="gridPanel13" columns="4" columnClasses="col2,col7,col2,col7"styleClass="row1" style="width:100%;display:#{OutwardClearingRegister.flag8};">
                                <h:outputLabel id="lblEntryType" styleClass="label" value="Entry Type" style="display:#{OutwardClearingRegister.flag8};"/>
                                <h:selectOneListbox id="ddEntryType" style="width:70px;display:#{OutwardClearingRegister.flag8};"styleClass="ddlist" size="1" value="#{OutwardClearingRegister.entryType}">
                                    <f:selectItems value="#{OutwardClearingRegister.entryTypeList}"/>
                                    <a4j:support actionListener="#{OutwardClearingRegister.visibilityOfBcbpAndAlphaCode}" event="onblur" reRender="lblAlphaCode,ddAlphaCode,lblBcbpNo,txtBcbpNo"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblAlphaCode" styleClass="label" value="Alpha Code" style="display:#{OutwardClearingRegister.flag9};"/>
                                <h:selectOneListbox id="ddAlphaCode" style="width:70px;display:#{OutwardClearingRegister.flag9};"styleClass="ddlist" size="1" value="#{OutwardClearingRegister.alphaCode}">
                                    <f:selectItems value="#{OutwardClearingRegister.alphaCodeList}"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblBcbpNo" styleClass="label" value="BC/ BP No." style="display:#{OutwardClearingRegister.flag9};"/>
                                <h:inputText id="txtBcbpNo" styleClass="input" style="display:#{OutwardClearingRegister.flag9};" value="#{OutwardClearingRegister.bcbpNo}"/>
                            </h:panelGrid>
                            <h:panelGrid id="gridPanel5" columns="4" columnClasses="col2,col7,col2,col7"styleClass="row2" style="width:100%;">
                                <h:outputLabel id="lblInstAmt" styleClass="label" value="Instrument Amount"/>
                                <h:inputText id="txtInstAmt" styleClass="input" value="#{OutwardClearingRegister.instrAmt}" disabled="#{OutwardClearingRegister.flag16}" maxlength="15" tabindex="4" style="width:100px;" >
                                    <a4j:support actionListener="#{OutwardClearingRegister.instrAmount}" event="onblur" reRender="stxtError"/>
                                </h:inputText>
                                <h:outputLabel id="lblInstNo" styleClass="label" value="Instrument No."/>
                                <h:inputText id="txtInstNo" styleClass="input" value="#{OutwardClearingRegister.instrNo}" disabled="#{OutwardClearingRegister.flag16}" maxlength="6" style="width:100px;">
                                    <a4j:support actionListener="#{OutwardClearingRegister.instrNumber}" event="onblur" reRender="stxtError"/>
                                </h:inputText>
                            </h:panelGrid>
                            <h:panelGrid id="gridPanele" columns="4" columnClasses="col2,col7,col2,col7"styleClass="row1" style="width:100%;">
                                <h:outputLabel id="lblInstDate" styleClass="label" value="Instrument Date"/>
                                <h:inputText id="calInstDate" styleClass="input calInstDate" value="#{OutwardClearingRegister.instrDate}" disabled="#{OutwardClearingRegister.flag16}" style="width:75px;">
                                    <f:convertDateTime pattern="dd/MM/yyyy" />
                                    <a4j:support actionListener="#{OutwardClearingRegister.instrdatelostFocus}" event="onblur" reRender="stxtError,stxtErrorDate" oncomplete="if(#{OutwardClearingRegister.flag13=='true'}){#{rich:component('showPanel4')}.show();} else if(#{OutwardClearingRegister.flag12=='false'}) {#{rich:component('showPanel4')}.hide();} setMask()"/>
                                </h:inputText>
                                <%--<rich:calendar id="calInstDate" datePattern="dd/MM/yyyy" value="#{OutwardClearingRegister.instrDate}" disabled="#{OutwardClearingRegister.flag16}" style="width:60px;">
                                    <a4j:support action="#{OutwardClearingRegister.instrdatelostFocus}" event="onchanged" reRender="stxtError,stxtErrorDate" oncomplete="if(#{OutwardClearingRegister.flag13=='true'}){#{rich:component('showPanel4')}.show();} else if(#{OutwardClearingRegister.flag12=='false'}) {#{rich:component('showPanel4')}.hide();}"/>
                                </rich:calendar>--%>
                                <h:outputLabel id="lblRemarks" styleClass="label" value="Remarks"/>
                                <h:inputText id="txtRemarks" styleClass="input" value="#{OutwardClearingRegister.remarks}" disabled="#{OutwardClearingRegister.flag16}" maxlength="20" style="width:100px;">
                                </h:inputText>
                            </h:panelGrid>
                            <h:panelGrid id="gridPanel6" columns="4" columnClasses="col2,col7,col2,col7"styleClass="row2" style="width:100%;">
                                <h:outputLabel id="lblBankCode" styleClass="label" value="Bank Code"/>
                                <h:panelGroup id="panelGrp3" layout="block">
                                    <h:inputText id="txtBankCode1" styleClass="input" style="width:40px;" disabled="#{OutwardClearingRegister.flag3}" value="#{OutwardClearingRegister.bankCode1}" maxlength="3">
                                        <a4j:support actionListener="#{OutwardClearingRegister.bankCode1LostFocus}" event="onblur" reRender="stxtError"/>
                                    </h:inputText>
                                    <h:inputText id="txtBankCode2" styleClass="input" style="width:40px;"disabled="#{OutwardClearingRegister.flag3}" value="#{OutwardClearingRegister.bankCode2}" maxlength="3">
                                        <a4j:support actionListener="#{OutwardClearingRegister.bankCode2LostFocus}" event="onblur" reRender="stxtError,txtBankCode2"/>
                                    </h:inputText>
                                    <h:inputText id="txtBankCode3" styleClass="input" style="width:40px;"disabled="#{OutwardClearingRegister.flag3}" value="#{OutwardClearingRegister.bankCode3}" maxlength="3">
                                        <a4j:support actionListener="#{OutwardClearingRegister.bankCode3LostFocus}" event="onblur" reRender="stxtError,txtBankName,txtBranch,txtBankCode3"/>
                                    </h:inputText>
                                </h:panelGroup>
                                <h:outputLabel id="lblRelatedTo" styleClass="label" value="Related To" />
                                <h:selectOneListbox id="ddRelatedTo" size="1"styleClass="ddlist" style="width:100px;" value="#{OutwardClearingRegister.relatedTo}">
                                    <f:selectItems value="#{OutwardClearingRegister.relatedToList}"/>
                                </h:selectOneListbox>
                            </h:panelGrid>
                            <h:panelGrid id="gridPanel7" columns="4" columnClasses="col2,col7,col2,col7" styleClass="row1" style="width:100%;">
                                <h:outputLabel id="lblBankName" styleClass="label" value="Bank Name"/>
                                <h:inputText id="txtBankName" styleClass="input"disabled="#{OutwardClearingRegister.flag4}" value="#{OutwardClearingRegister.bankName}" />
                                <h:outputLabel id="lblBranch" styleClass="label" value="Branch"/>
                                <h:inputText id="txtBranch" styleClass="input"disabled="#{OutwardClearingRegister.flag4}" value="#{OutwardClearingRegister.branchName}"/>
                            </h:panelGrid>
                            <h:panelGrid id="gridPanel14" columns="4" columnClasses="col2,col7,col2,col7" styleClass="row1" style="width:100%;display:none;">
                                <h:outputLabel id="lblBankName2" styleClass="label" value="Bank Name" />
                                <h:selectOneListbox id="ddBankName2" style="width:122px;" styleClass="ddlist" size="1" value="#{OutwardClearingRegister.bankNames}">
                                    <f:selectItems value="#{OutwardClearingRegister.bankNamesList}"/>
                                    <a4j:support actionListener="#{OutwardClearingRegister.getBranchNameOnBlurBankName}" event="onblur" reRender="ddBranch2,txtBankCode2" focus="ddBranch2"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblBranch2" styleClass="label" value="Branch"/>
                                <h:selectOneListbox id="ddBranch2" size="1"style="width:122px;" styleClass="ddlist"  value="#{OutwardClearingRegister.branchNames}">
                                    <f:selectItems value="#{OutwardClearingRegister.branchNamesList}"/>                                    
                                </h:selectOneListbox>
                                <h:outputLabel/>
                                <h:outputLabel/>
                            </h:panelGrid>

                            <h:panelGrid id="gridPanelParameter1" columns="4" columnClasses="col2,col7,col2,col7"styleClass="row2" style="width:100%;display:#{OutwardClearingRegister.fileFlag}">
                                <%--h:outputLabel id="sortCodeId" styleClass="label" value="Sort Code"/>
                                <h:inputText id="sortCodeInId" styleClass="input"maxlength="9" value="#{OutwardClearingRegister.sortCode}"  style="width:100px;">
                                </h:inputText--%>
                                <h:outputLabel id="sanId" styleClass="label" value="Account No /SAN No"/>
                                <h:inputText id="txtsanId" styleClass="input" maxlength="7" value="#{OutwardClearingRegister.accountNoSanNo}"  onkeyup="this.value=this.value.toUpperCase();" style="width:100px;" >
                                </h:inputText>
                                <h:outputLabel id="lbltxnCode" styleClass="label" value="Transaction Code"/>
                                <h:inputText id="txttxnCode" styleClass="input" maxlength="3"value="#{OutwardClearingRegister.transactionCode}"  style="width:100px;">
                                </h:inputText>
                                <h:outputLabel/>
                                <h:outputLabel/>
                            </h:panelGrid>

                            <h:panelGrid id="gridPanelParameter2" columns="4" columnClasses="col2,col7,col2,col7"styleClass="row2" style="width:100%;display:#{OutwardClearingRegister.fileFlag}">
                                <h:outputLabel id="lblAccountName" styleClass="label" value="Account Name"/>
                                <h:inputText id="txtAccountName" styleClass="input" maxlength="25" value="#{OutwardClearingRegister.accountName}" onkeyup="this.value=this.value.toUpperCase();" style="width:210px;" >
                                    <a4j:support actionListener="#{OutwardClearingRegister.isAccountName}" event="onblur"reRender="stxtError"/>
                                </h:inputText>
                                <h:outputLabel/>
                                <h:outputLabel/>
                                <h:outputLabel/>
                                <h:outputLabel/>
                            </h:panelGrid>

                            <h:panelGrid id="gridPanel8" columns="4" columnClasses="col2,col7,col2,col7"styleClass="row2" style="width:100%;">
                                <h:outputLabel id="lblDrawerName" styleClass="label" value="Drawer's Name"/>
                                <h:inputText id="txtDrawerName" styleClass="input" value="#{OutwardClearingRegister.drawerName}" disabled="#{OutwardClearingRegister.flag16}" style="width:100px;">
                                </h:inputText>
                                <h:outputLabel id="lblDrawerAccNo" styleClass="label" value="Drawer's Account No."/>
                                <h:inputText id="txtDrawerAccNo" styleClass="input" maxlength="32" value="#{OutwardClearingRegister.drawAccNo}" disabled="#{OutwardClearingRegister.flag16}" onkeyup="this.value=this.value.toUpperCase();" style="width:100px;" >
                                    <a4j:support actionListener="#{OutwardClearingRegister.drawerAccNoLostFocus}" event="onblur" focus="ddNextChq"reRender="gridPanel11,stxtError"/>
                                </h:inputText>
                                <h:outputLabel/>
                                <h:outputLabel/>
                            </h:panelGrid>
                            <h:panelGrid id="gridPanel9" columns="4" columnClasses="col2,col7,col2,col7"styleClass="row1" style="width:100%;">
                                <h:outputLabel id="lblNextChq" styleClass="label" value="Next Cheque for same Voucher"/>
                                <h:selectOneListbox id="ddNextChq" style="width:100px;" styleClass="ddlist" size="1" value="#{OutwardClearingRegister.nextChq}">
                                    <f:selectItems value="#{OutwardClearingRegister.nextChqList}"/>
                                    <a4j:support actionListener="#{OutwardClearingRegister.nextChqLostFocus}" event="onblur"  reRender="txtAccNo,txtInstAmt,txtInstNo,txtRemarks,calInstDate,txtDrawerName,txtDrawerAccNo,txtBankCode1,txtBankCode2,txtBankCode3,txtBankName,txtBranch,stxtError,ddBankName2,ddBranch2,stxtAccNo"  oncomplete="if((#{OutwardClearingRegister.flag15 == 'true'})) {#{rich:element('btnSave')}.focus();}
                                                 else if ((#{OutwardClearingRegister.flag15 == 'false'})) {#{rich:element('txtInstAmt')}.focus();} setMask();"
                                                 />
                                </h:selectOneListbox>
                                <h:outputLabel id="lblJointName" styleClass="label" value="Joint Name"/>
                                <h:outputText id="stxtJointName" styleClass="output" value="#{OutwardClearingRegister.jtName}"/>
                                <h:outputLabel/>
                                <h:outputLabel/>
                                <h:outputLabel/>
                                <h:outputLabel/>
                            </h:panelGrid>
                            <h:panelGrid id="gridPaneldate" columns="4" columnClasses="col2,col7,col2,col7"styleClass="row2" style="width:100%;">
                                <h:outputLabel id="lblacctOpeningDate" styleClass="label" value="Account Opening Date"/>
                                <h:outputText id="stxtacctOpeningDate" styleClass="output" value="#{OutwardClearingRegister.acctopeningdate}"/>
                                <h:outputLabel />
                                <h:outputText />
                                <h:outputLabel/>
                                <h:outputLabel/>
                                <h:outputLabel/>
                                <h:outputLabel/>
                            </h:panelGrid>                             
                        </h:panelGrid>
                        <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel10" width="100%" styleClass="row2" style="height:320px;">
                            
                            <rich:dataTable value="#{OutwardClearingRegister.unverifyInstrList}" id="taskList1" width="100%" var="dataItem" rows="4"  rowClasses="row1,row2"  columnsWidth="100"
                                            rowKeyVar="row" onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="6"><h:outputText value="Unverified Instruments"/></rich:column>
                                        <rich:column breakBefore="true"> <h:outputText value="A/C. No." /> </rich:column>
                                        <rich:column><h:outputText value="EM Flag"/></rich:column>
                                        <rich:column><h:outputText value="Inst. Amount"/></rich:column>
                                        <rich:column><h:outputText value="Inst. No."/></rich:column>
                                        <rich:column><h:outputText value="Vtot"/></rich:column>
                                        <rich:column><h:outputText value="Select"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.acNo}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.emflag}"/></rich:column>
                                <%--    <rich:column><h:outputText value="#{dataItem.txnInstrAmt}"/></rich:column>   --%>
                                <rich:column>
                                    <h:outputText value="#{dataItem.txnInstrAmt}">
                                        <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                    </h:outputText>
                                </rich:column>
                                <rich:column><h:outputText value="#{dataItem.txnInstrNo}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.vtot}"/></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink ajaxSingle="false" id="selectlink2" action="#{OutwardClearingRegister.getDetailsOnPopUp}" oncomplete="if(#{OutwardClearingRegister.flag17=='false'}){#{rich:component('updateDeletePanel')}.show();}" reRender="stxtError,txtAccountNo,txtinstrNo,stxtInstrType,txtInstrAmt,calInsDate,txtMicrCode1,txtMicrCode2,txtMicrCode3,txtBnkAddress,txtBnkName">
                                        <h:graphicImage value="/resources/images/edit.gif" style="border:0"/>
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{OutwardClearingRegister.unverifyInstr}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{OutwardClearingRegister.currentRow}"/>  
                                    </a4j:commandLink>
                                </rich:column>

                            </rich:dataTable>
                            <rich:datascroller id="as" align="left" for="taskList1"/>
                            <h:panelGrid id="newAccPanel" width="100%" columns="1" style="width:100%;text-align:center;" styleClass="row2" >
                                <h:outputText id="stxtAccStatus" styleClass="blink_text" style="color:blue;display:#{OutwardClearingRegister.flag30};"value="#{OutwardClearingRegister.accStatus}"/>
                            </h:panelGrid>
                            <h:panelGrid id="errorPanel" width="100%" columns="1" style="width:100%;text-align:center;" styleClass="row1" >
                                <h:outputText id="stxtError" styleClass="error" value="#{OutwardClearingRegister.errorMsg}"/>
                            </h:panelGrid>

                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel11" style="height:200px;display:#{OutwardClearingRegister.flag21};" styleClass="row1" width="100%">
                        <rich:dataTable value="#{OutwardClearingRegister.instrSaveList}" id="taskList2" width="100%" var="item" rowClasses="gridrow1, gridrow2" columnsWidth="100" rows="8"
                                        rowKeyVar="row" onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                        onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column breakBefore="true"><h:outputText value="Inst. No."/></rich:column>
                                    <rich:column><h:outputText value="Inst. Date"/></rich:column>
                                    <rich:column><h:outputText value="Amount"/></rich:column>
                                    <rich:column><h:outputText value="Drawer Name"/></rich:column>
                                    <rich:column><h:outputText value="Drawer A/c.No"/></rich:column>
                                    <rich:column><h:outputText value="Bank Name"/></rich:column>
                                    <rich:column><h:outputText value="Bank Addr"/></rich:column>
                                    <rich:column><h:outputText value="City Code"/></rich:column>
                                    <rich:column><h:outputText value="Bank Code"/></rich:column>
                                    <rich:column><h:outputText value="Branch Code"/></rich:column>
                                    <rich:column><h:outputText value="Alpha Code"/></rich:column>
                                    <rich:column><h:outputText value="Bcbp Code"/></rich:column>
                                    <rich:column><h:outputText value="Bill Type"/></rich:column>
                                    <rich:column><h:outputText value="Clr. Mode"/></rich:column>
                                    <rich:column><h:outputText value="FYear"/></rich:column>
                                    <rich:column visible="false"><h:outputText value="IY"/></rich:column>
                                    <rich:column><h:outputText value="Remarks"/></rich:column>
                                    <rich:column visible="false"><h:outputText value="Trans. Desc"/></rich:column>
                                    <rich:column><h:outputText value="Select"/></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{item.instructionNo}"/></rich:column>
                            <rich:column><h:outputText value="#{item.instructionDate}"/></rich:column>
                            <rich:column><h:outputText value="#{item.amount}"/></rich:column>
                            <rich:column><h:outputText value="#{item.drawerName}"/></rich:column>
                            <rich:column><h:outputText value="#{item.drawerAccNo}"/></rich:column>
                            <rich:column width="40%"><h:outputText value="#{item.bnkName}"/></rich:column>
                            <rich:column width="900%" ><h:outputText value="#{item.bnkAddress}"/></rich:column>
                            <rich:column><h:outputText value="#{item.cityCode}"/></rich:column>
                            <rich:column><h:outputText value="#{item.bnkCode}"/></rich:column>
                            <rich:column><h:outputText value="#{item.brnchCode}"/></rich:column>
                            <rich:column><h:outputText value="#{item.alphaCode}"/></rich:column>
                            <rich:column><h:outputText value="#{item.bcbpCode}"/></rich:column>
                            <rich:column><h:outputText value="#{item.billType}"/></rich:column>
                            <rich:column><h:outputText value="#{item.circleMode}"/></rich:column>
                            <rich:column><h:outputText value="#{item.fyear}"/></rich:column>
                            <rich:column visible="false"><h:outputText value="#{item.iy}"/></rich:column>
                            <rich:column><h:outputText value="#{item.remarks}"/></rich:column>
                            <rich:column visible="false"><h:outputText value="#{item.transdes}"/></rich:column>
                            <rich:column style="text-align:center;width:40px">
                                <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{OutwardClearingRegister.setRowValues}" reRender="txtInstAmt,txtInstNo,txtRemarks,calInstDate,txtDrawerName,txtDrawerAccNo,txtBankCode1,txtBankCode2,txtBankCode3,txtBankName,txtBranch,taskList2"  focus="txtInstAmt"
                                                 oncomplete="setMask();">
                                    <h:graphicImage value="/resources/images/edit.gif" style="border:0"/>
                                    <f:setPropertyActionListener value="#{item}" target="#{OutwardClearingRegister.instrSaveVouch}"/>
                                    <f:setPropertyActionListener value="#{row}" target="#{OutwardClearingRegister.currentRow}"/>
                                </a4j:commandLink>
                            </rich:column>
                        </rich:dataTable>
                        <rich:datascroller  align="left" for="taskList2" maxPages="10"/>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gridPanel12"
                                 style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="panelGrp2" layout="block" style="width:100%;text-align:center; ">
                            <a4j:commandButton id="btnSave" value="Save"  reRender="stxtVoucherNo,stxtError"actionListener="#{OutwardClearingRegister.getVoucherNumber}" oncomplete="if(#{OutwardClearingRegister.flag11=='true'}){#{rich:component('showPanel2')}.show();} else if(#{OutwardClearingRegister.flag11=='false'}){#{rich:component('showPanel2')}.hide();}"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{OutwardClearingRegister.refreshForm}" reRender="stxtError,txtAccNo,ddCircleType,ddRegisDate,txtInstAmt,txtInstNo,txtRemarks,calInstDate,txtDrawerName,txtDrawerAccNo,txtBankCode1,txtBankCode2,txtBankCode3,txtBankName,txtBranch,gridPanel11,taskList1,stxtJointName,stxtCustName,stxtacctOpeningDate,as,stxtAccStatus,gridPanel14,stxtAccNo,gridPanelParameter1,gridPanelParameter2" focus="ddCircleType" oncomplete="#{rich:element('gridPanel14')}.hide();#{rich:element('gridPanel7')}.show(); setMask();"/>
                            <a4j:commandButton id="btnExit"value="Exit" action="#{OutwardClearingRegister.exitForm}"reRender="stxtError,txtAccNo,ddCircleType,ddRegisDate,txtInstAmt,txtInstNo,txtRemarks,calInstDate,txtDrawerName,txtDrawerAccNo,txtBankCode1,txtBankCode2,txtBankCode3,txtBankName,txtBranch,gridPanel11,taskList1,stxtJointName,stxtCustName,stxtacctOpeningDate,stxtAccStatus,stxtAccNo" oncomplete="setMask();"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="msgPanel" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputText id="stxtInstruction1" styleClass="output" value="Ctrl+B-A/C Type Desc." style="color:blue;"/>
                    </h:panelGrid>
                </h:panelGrid>

                <rich:modalPanel id="showPanel" autosized="true" width="200" onshow="#{rich:element('btnOkShowP')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Please enter a Numeric Value in Instrument Amount." style="padding-right:15px;" />
                    </f:facet>
                    <f:facet name="controls">
                        <h:panelGroup>
                            <h:graphicImage  value="/resources/images/close.png" styleClass="hidelink" id="hidelink2" />
                            <rich:componentControl for="showPanel" attachTo="hidelink2" operation="hide" event="onclick" />
                        </h:panelGroup>
                    </f:facet>
                    <h:form>
                        <table width="100%">
                            <tbody>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="OK" id="btnOkShowP" onclick="#{rich:component('showPanel')}.hide();return false;" />
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
                <rich:modalPanel id="showPanel10" autosized="true" width="200" onshow="#{rich:element('btnOkShowP10')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Please enter a Numeric Value in Instrument No." style="padding-right:15px;" />
                    </f:facet>
                    <f:facet name="controls">
                        <h:panelGroup>
                            <h:graphicImage  value="/resources/images/close.png" styleClass="hidelink" id="hidelink10" />
                            <rich:componentControl for="showPanel10" attachTo="hidelink10" operation="hide" event="onclick" />
                        </h:panelGroup>
                    </f:facet>
                    <h:form>
                        <table width="100%">
                            <tbody>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="OK" id="btnOkShowP10" onclick="#{rich:component('showPanel10')}.hide();return false;" />
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
                <rich:modalPanel id="showPanel2" autosized="true" width="200" onshow="#{rich:element('btnOkShowP12')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="e-Bank(CBS) Alert" style="padding-right:15px;" />
                    </f:facet>
                    <f:facet name="controls">
                        <h:panelGroup>
                            <h:graphicImage  value="/resources/images/close.png" styleClass="hidelink" id="hidelink3" />
                            <rich:componentControl for="showPanel2" attachTo="hidelink3" operation="hide" event="onclick" />
                        </h:panelGroup>
                    </f:facet>
                    <h:form>
                        <table width="100%">
                            <tbody>
                                <tr>
                                    <td align="center" width="50%" height="50px">
                                        <h:outputText  id="stxtVoucherNo"value="Generated Voucher No.: #{OutwardClearingRegister.voucherNo}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton id="btnOkShowP12"  ajaxSingle="true"action="#{OutwardClearingRegister.saveGridData}" value="OK"  oncomplete="#{rich:component('showPanel3')}.show();" reRender="stxtSuccFail,txtAccNo,taskList1,stxtError,taskList2,gridPanel10,stxtCustName,stxtacctOpeningDate,stxtJointName,stxtAccStatus,stxtAccNo,gridPanelParameter1,gridPanelParameter2" />
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
                <rich:modalPanel id="showPanel3" autosized="true" width="200" onshow="#{rich:element('btnOkShowP13')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="e-Bank(CBS) Alert" style="padding-right:15px;" />
                    </f:facet>
                    <f:facet name="controls">
                        <h:panelGroup>
                            <h:graphicImage  value="/resources/images/close.png" styleClass="hidelink" id="hidelink4" />
                            <rich:componentControl for="showPanel3" attachTo="hidelink4" operation="hide" event="onclick" />
                        </h:panelGroup>
                    </f:facet>
                    <h:form>
                        <table width="100%">
                            <tbody>
                                <tr>
                                    <td align="center" width="50%" height="50px">
                                        <h:outputText  id="stxtSuccFail" value="#{OutwardClearingRegister.resultOfSave}" />
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="OK" id="btnOkShowP13" ajaxSingle="true"onclick="#{rich:component('showPanel3')}.hide();#{rich:component('showPanel2')}.hide();" reRender="stxtError,taskList2"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
                <rich:modalPanel id="showPanel4" autosized="true" width="200" onshow="#{rich:element('btnOkShowP14')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog ?" style="padding-right:15px;" />
                    </f:facet>
                    <f:facet name="controls">
                        <h:panelGroup>
                            <h:graphicImage  value="/resources/images/close.png" styleClass="hidelink" id="hidelink5" />
                            <rich:componentControl for="showPanel4" attachTo="hidelink5" operation="hide" event="onclick" />
                        </h:panelGroup>
                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td align="center" width="50%" height="50px">
                                        <h:outputText  id="stxtErrorDate" styleClass="output" value="#{OutwardClearingRegister.flag12}"/>
                                    </td>
                                </tr>
                                <tr> 
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="OK" id="btnOkShowP14" onclick="#{rich:component('showPanel4')}.hide();#{rich:element('calInstDate')}.focus();" />
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
                <rich:modalPanel id="showInoperativePanel" autosized="true" width="200" onshow="#{rich:element('btnInoperativeOk')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog ?" style="padding-right:15px;" />
                    </f:facet>
                    <f:facet name="controls">
                        <h:panelGroup>
                            <h:graphicImage  value="/resources/images/close.png" styleClass="hidelink" id="hidelinkInoperative" />
                            <rich:componentControl for="showInoperativePanel" attachTo="hidelinkInoperative" operation="hide" event="onclick" />
                        </h:panelGroup>
                    </f:facet>
                    <h:form>
                        <table width="100%">
                            <tbody>
                                <tr>
                                    <td align="center" width="50%">
                                        <h:outputText  id="stxtAccountStatus" styleClass="output" value="Account has marked as Inoperative."/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="OK" id="btnInoperativeOk" onclick="#{rich:component('showInoperativePanel')}.hide();#{rich:element('txtInstAmt')}.focus();"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
                <rich:modalPanel id="updateDeletePanel" autosized="true" width="600" onshow="#{rich:element('txtinstrNo')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Instrument Updation and Deletion Register" style="padding-right:15px;" />
                    </f:facet>
                    <f:facet name="controls">
                        <h:panelGroup>
                            <h:graphicImage  value="/resources/images/close.png" styleClass="hidelink" id="updateDeleteHideLink" />
                            <rich:componentControl for="updateDeletePanel" attachTo="updateDeleteHideLink" operation="hide" event="onclick"/>
                        </h:panelGroup>
                    </f:facet>
                    <h:form id="form2">
                        <h:panelGrid id="mainPanel2" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                            <h:panelGrid id="errorPanelPopup" width="100%" columns="1">
                                <h:outputText id="stxtErrorPopup" styleClass="error" value="#{OutwardClearingRegister.errorMsgPopup}"/>
                            </h:panelGrid>
                            <h:panelGrid id="accNoPanelGrid" columns="4" columnClasses="col2,col7,col2,col7" styleClass="row1" style="width:100%;">
                                <h:outputLabel id="lblAccountNo" styleClass="label" value="Account No."/>
                                <h:outputText id="txtAccountNo" styleClass="output" value="#{OutwardClearingRegister.accNoPopup}"/>
                                <h:outputLabel id="lblinstrNo" styleClass="label" value="Instr. No."/>
                                <h:inputText id="txtinstrNo" styleClass="input" maxlength="6" style="width:50%" value="#{OutwardClearingRegister.instrNoPopup}">
                                    <a4j:support event="onblur" ajaxSingle="true"/>
                                </h:inputText>
                            </h:panelGrid>
                            <h:panelGrid id="instrTypePanelGrid" columns="4" columnClasses="col2,col7,col2,col7" styleClass="row1" style="width:100%;">
                                <h:outputLabel id="lblInstrType" styleClass="label" value="Instr. Type"/>
                                <h:outputText id="stxtInstrType" styleClass="output" value="#{OutwardClearingRegister.instrTypePopup}">
                                    <a4j:support event="onblur" ajaxSingle="true"/>
                                </h:outputText>
                                <h:outputLabel id="lblInstrAmt" styleClass="label" value="Instr. Amount"/>
                                <h:inputText id="txtInstrAmt" styleClass="input" maxlength="15" style="width:50%" value="#{OutwardClearingRegister.instrAmtPopup}">
                                    <a4j:support event="onblur" ajaxSingle="true"/>
                                </h:inputText>
                            </h:panelGrid>
                            <h:panelGrid id="instrDatePanelGrid" columns="4" columnClasses="col2,col7,col2,col7" styleClass="row1" style="width:100%;">
                                <h:outputLabel id="lblInstrDate" styleClass="label" value="Instr. Date"/>
                                <rich:calendar id="calInsDate" datePattern="dd/MM/yyyy" value="#{OutwardClearingRegister.instrDatePopup}" inputSize="8">
                                    <a4j:support event="onchanged" actionListener="#{OutwardClearingRegister.instrdatelostFocusPopUp}" ajaxSingle="true" reRender="stxtErrorPopup" />
                                </rich:calendar>
                                <h:outputLabel id="lblMicrCode" styleClass="label" value="Micr. Code"/>
                                <h:panelGroup id="micrCodePanelGroup" layout="block">
                                    <h:inputText id="txtMicrCode1" maxlength="3" disabled="#{OutwardClearingRegister.flag19}"styleClass="input" style="width:16%" value="#{OutwardClearingRegister.micrCode1Popup}">
                                        <a4j:support event="onblur" ajaxSingle="true"/>
                                    </h:inputText>
                                    <h:inputText id="txtMicrCode2" maxlength="3" disabled="#{OutwardClearingRegister.flag19}"styleClass="input" style="width:16%" value="#{OutwardClearingRegister.micrCode2Popup}">
                                        <a4j:support event="onblur" ajaxSingle="true"/>
                                    </h:inputText>
                                    <h:inputText id="txtMicrCode3" maxlength="3" disabled="#{OutwardClearingRegister.flag19}"styleClass="input" style="width:16%" value="#{OutwardClearingRegister.micrCode3Popup}">
                                        <a4j:support event="onblur" ajaxSingle="true" action="#{OutwardClearingRegister.getBankNameAddressOnPopup}" reRender="txtBnkAddress,txtBnkName,stxtErrorPopup"/>
                                    </h:inputText>
                                </h:panelGroup>
                            </h:panelGrid>
                            <h:panelGrid id="bnkAddressPanelGrid" columns="4" columnClasses="col2,col7,col2,col7" styleClass="row1" style="width:100%;">
                                <h:outputLabel id="lblBnkAddress" styleClass="label" value="Bank Address"/>
                                <h:inputText id="txtBnkAddress"  disabled="#{OutwardClearingRegister.flag18}"styleClass="input" style="width:150px" value="#{OutwardClearingRegister.bankAddressPopup}">
                                    <a4j:support event="onblur" ajaxSingle="true"/>
                                </h:inputText>
                                <h:outputLabel id="lblBnkName" styleClass="label" value="Bank Name"/>
                                <h:inputText id="txtBnkName" disabled="#{OutwardClearingRegister.flag18}"styleClass="input" style="width:130px" value="#{OutwardClearingRegister.bankNamePopup}">
                                    <a4j:support event="onblur" ajaxSingle="true"/>
                                </h:inputText>
                            </h:panelGrid>
                            <h:panelGrid columns="1" id="btnPanel"
                                         style="width:100%;text-align:center;" styleClass="footer">
                                <h:panelGroup layout="block" style="width:100%;text-align:center; "/>
                                <h:panelGroup id="panelGrp4" layout="block" style="width:100%;text-align:center; ">
                                    <a4j:commandButton id="btnUpdate" value="Update" ajaxSingle="true" action="#{OutwardClearingRegister.clickOnUpdateButton}" reRender="stxtErrorPopup,txtAccountNo,txtinstrNo,stxtInstrType,txtInstrAmt,calInsDate,txtMicrCode1,txtMicrCode2,txtMicrCode3,txtBnkAddress,txtBnkName,as"/>
                                    <a4j:commandButton id="btnDelete" value="Delete" action="#{OutwardClearingRegister.clickOnDeleteButton}" ajaxSingle="true" reRender="stxtErrorPopup,txtAccountNo,txtinstrNo,stxtInstrType,txtInstrAmt,calInsDate,txtMicrCode1,txtMicrCode2,txtMicrCode3,txtBnkAddress,txtBnkName,as"/>
                                    <a4j:commandButton id="btnReturn"value="Close" ajaxSingle="true" action="#{OutwardClearingRegister.refreshForm}" oncomplete="#{rich:component('updateDeletePanel')}.hide();return false;" reRender="stxtError,txtAccNo,ddCircleType,ddRegisDate,txtInstAmt,txtInstNo,txtRemarks,calInstDate,txtDrawerName,txtDrawerAccNo,txtBankCode1,txtBankCode2,txtBankCode3,txtBankName,txtBranch,gridPanel11,taskList1,stxtJointName,stxtErrorPopup,stxtCustName,stxtacctOpeningDate,as,stxtAccStatus,stxtAccNo,gridPanelParameter1,gridPanelParameter2"/>
                                </h:panelGroup>
                            </h:panelGrid>
                        </h:panelGrid>
                    </h:form>
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
                            <rich:dataTable  var="item" value="#{OutwardClearingRegister.accTypeDescList}"
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
                <a4j:jsFunction name="showAccTypesDesc" action="#{OutwardClearingRegister.accTypeNatureDesc()}" oncomplete="#{rich:component('accTypeDescView')}.show();" reRender="accTypeDesc,scrollAccTypeDesc"/>
            </a4j:form>
            <rich:hotKey key="Ctrl+B" handler="showAccTypesDesc(); return false;"/>
        </body>
    </html>
</f:view>

