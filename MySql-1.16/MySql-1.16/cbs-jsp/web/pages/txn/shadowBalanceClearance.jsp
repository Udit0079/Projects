<%--
    Document   : Shadow Balance Clearance
    Created on : May 15, 2010, 3:05:24 PM
    Author     : jitendra kumar Chaudhary
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
            <title>Shadow Balance Clearance</title>
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
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <!-- Header-->
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{ShadowBalanceClearance.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Shadow Balance Clearance"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ShadowBalanceClearance.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <!-- Body-->
                    <h:panelGrid columnClasses="col7,col12" columns="2" width="100%" style="border:1px ridge #BED6F8">
                        <h:panelGrid width="100%">
                            <h:panelGrid columnClasses="col8" id="gridPanel19" style="text-align:center;" styleClass="row1" width="100%">
                                <h:outputLabel id="labelClearShBalance" styleClass="headerLabel" value="Clear Shadow Balance"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col8,col5" columns="2" id="branchGrid" style="width:100%;text-align:left;" styleClass="row2">
                                <h:outputLabel id="lblBranch" styleClass="label" value="Branch"><font class="required" color="red">*</font></h:outputLabel>
                                    <h:selectOneListbox id="ddBranch" styleClass="ddlist" size="1" style="width:120px" value="#{ShadowBalanceClearance.branch}" tabindex="1">
                                        <f:selectItems value="#{ShadowBalanceClearance.branchList}"/>
                                    </h:selectOneListbox>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col8,col5" columns="2" id="row2" style="width:100%;text-align:left;" styleClass="row1">
                                    <h:outputLabel id="label1" styleClass="label" for="ddClearingMode" value="Clearing Mode"><font class="required" color="red">*</font></h:outputLabel>
                                    <h:selectOneListbox id="ddClearingMode" styleClass="ddlist" size="1" style="width:140px" value="#{ShadowBalanceClearance.emFlags}" tabindex="2">
                                        <f:selectItem itemValue="--SELECT--"/>
                                        <f:selectItems value="#{ShadowBalanceClearance.clearingModeOption}"/>
                                        <a4j:support actionListener="#{ShadowBalanceClearance.registerDtClearrance}"  event="onblur" focus="ddRegisterDate"
                                                     reRender="taskList,ddRegisterDate,stxtMsg" limitToList="true" />
                                    </h:selectOneListbox>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col8,col5" columns="2" id="row5" style="width:100%;" styleClass="row2">
                                    <h:outputLabel id="lblRegisterDate" styleClass="label" for="ddRegisterDate" value="Register Date"><font class="required" color="red">*</font></h:outputLabel>
                                    <h:panelGroup id="tt" layout="block" style="width:100%">
                                        <h:selectOneListbox id="ddRegisterDate" styleClass="ddlist" size="1" style="width:80px" value="#{ShadowBalanceClearance.registerDate}" tabindex="3">
                                            <a4j:support actionListener="#{ShadowBalanceClearance.registerDateLostFocus}"  event="onblur" focus="ddRegisterDate1"
                                                         reRender="txtTotalCheques,stxtClearingDt,ddDywthabc,txtTotalAmount,txtRtCheques,txtRtAmount,rightPanel,taskList2,taskList1,stxtMsg"  limitToList="true" />
                                            <f:selectItem itemValue="--SELECT--"/>
                                            <f:selectItems value="#{ShadowBalanceClearance.registerDtOption}"/>
                                        </h:selectOneListbox>
                                        <h:selectOneListbox id="ddRegisterDate1" styleClass="ddlist" size="1" style="width:65px" value="#{ShadowBalanceClearance.orderByRegisterDt}" tabindex="4">
                                            <f:selectItem itemValue="A/c No"/>
                                            <f:selectItem itemValue="Inst No"/>
                                            <f:selectItem itemValue="Voucher No"/>
                                        </h:selectOneListbox>
                                    </h:panelGroup>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col8,col5" columns="2" id="row6" style="width:100%;text-align:left;" styleClass="row1">
                                    <h:outputLabel id="lblClearingDt" styleClass="label" value="Clearing Date"/>
                                    <h:outputText id="stxtClearingDt" styleClass="error" value="#{ShadowBalanceClearance.clearingDate}"/>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col8,col5" columns="2" id="gridPanel1w5" style="height:30px;" styleClass="row2" width="100%">
                                    <h:outputLabel id="lblReturnCheques" styleClass="label" value="Returned Cheques"><font class="required" color="red">*</font></h:outputLabel>
                                    <h:selectOneListbox id="ddReturnCheques" styleClass="ddlist" size="1" style="width:80px" value="#{ShadowBalanceClearance.returnCheques}" tabindex="5">
                                        <f:selectItem itemValue="NO"/>
                                        <f:selectItem itemValue="YES"/>
                                        <a4j:support actionListener="#{ShadowBalanceClearance.processValueChange}"  event="onblur"  oncomplete="if((#{ShadowBalanceClearance.returnCheques == 'YES'})) {#{rich:element('txtInstNo')}.focus();}
                                                     else{#{rich:element('ddDywthabc')}.focus();} setMask();"
                                                     reRender="row4,row9,stxtMsg,txtInstNo,ddDywthabc,calInstDate,txtInstNo,txtInstAmounts"  limitToList="true" />
                                    </h:selectOneListbox>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col85,col1" columns="2" id="row3" style="width:100%;text-align:left;" styleClass="row1">
                                    <h:outputLabel id="lblDywthabc" escape="false" styleClass="label" value="Do you want to <b>Hold/ Release </b> clearing of any bank"><font class="required" color="red">*</font></h:outputLabel>
                                    <h:selectOneListbox id="ddDywthabc" styleClass="ddlist" size="1" style="width:55px" value="#{ShadowBalanceClearance.bankClearingHold}" tabindex="6">
                                        <f:selectItem itemValue="NO"/>
                                        <f:selectItem itemValue="YES"/>
                                        <a4j:support event="onblur" reRender="rightPanel,ddDywthabc,txtBankMicrCode,taskList,taskList1,stxtMsg,rowdw3" focus="txtInstNo"  action="#{ShadowBalanceClearance.onBlurDoYouWantToHold}"
                                                     oncomplete="if((#{ShadowBalanceClearance.bankClearingHold == 'YES'})) {#{rich:element('ddHoldOpt')}.focus();}
                                                     else{#{rich:element('ddReasonForReturn')}.focus();}"/>
                                    </h:selectOneListbox>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel19c" style="width:100%;text-align:center;" styleClass="row2" width="100%">
                                    <h:outputText id="stxtMsg" styleClass="error" value="#{ShadowBalanceClearance.message}" />
                                </h:panelGrid>
                            </h:panelGrid>
                            <h:panelGroup layout="block" id="rightPanel" style="width:100%;height:240px;text-align:center;background-color:#e8eef7;overflow:auto;">
                                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="rowdw3" style="width:100%;text-align:left;" >
                                    <h:outputLabel id="lblDDHold" styleClass="label" value="Holding Option" rendered="#{ShadowBalanceClearance.bankClearingHoldRel == 'YES'}"/>
                                    <h:selectOneListbox id="ddHoldOpt" styleClass="ddlist" size="1" value="#{ShadowBalanceClearance.ddHoldOpt}" rendered="#{ShadowBalanceClearance.bankClearingHoldRel == 'YES'}">
                                        <f:selectItems value="#{ShadowBalanceClearance.ddHoldOption}"/>
                                        <a4j:support event="onblur" action="#{ShadowBalanceClearance.setLength}" reRender="txtBankMicrCode" oncomplete="#{rich:element('txtBankMicrCode')}.focus()"/>
                                    </h:selectOneListbox>
                                    <h:outputLabel id="lblBankmicrCode" styleClass="label" value="Bank Micr Code" rendered="#{ShadowBalanceClearance.bankClearingHoldRel == 'YES'}"/>
                                    <h:inputText id="txtBankMicrCode" styleClass="input" size="#{ShadowBalanceClearance.bnkHoldLen}" value="#{ShadowBalanceClearance.bankHoldName}" maxlength="#{ShadowBalanceClearance.bnkHoldLen}" rendered="#{ShadowBalanceClearance.bankClearingHoldRel == 'YES'}" >
                                        <a4j:support actionListener="#{ShadowBalanceClearance.bankClearingHoldValueChange}"  event="onblur"
                                                     reRender="ddClearingMode,stxtMsg,taskList,row3,rightPanel"  limitToList="true" />
                                    </h:inputText>
                                </h:panelGrid>

                            <a4j:region>
                                <rich:dataTable rows="3" value="#{ShadowBalanceClearance.bankNameTable}" var="dataIt" rendered="#{ShadowBalanceClearance.bankClearingHoldRel == 'YES'}" id="taskList" rowKeyVar="row" rowClasses="gridrow1,gridrow2"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column><h:outputText value="Bank Name"/></rich:column>
                                            <rich:column><h:outputText value="Select"/></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{dataIt.bankName}"/></rich:column>
                                    <rich:column>
                                        <a4j:commandLink ajaxSingle="true" id="updatelink" oncomplete="#{rich:component('deletePanel1')}.show()">
                                            <h:graphicImage value="/resources/images/passed.gif" style="border:0" />
                                            <f:setPropertyActionListener value="#{dataIt}" target="#{ShadowBalanceClearance.currentItem1}"/>
                                            <f:setPropertyActionListener value="#{row}" target="#{ShadowBalanceClearance.currentRow}" />
                                        </a4j:commandLink>
                                    </rich:column>
                                </rich:dataTable>
                                <rich:datascroller align="left" for="taskList" maxPages="20" rendered="#{ShadowBalanceClearance.bankClearingHoldRel == 'YES'}"/>
                            </a4j:region>
                            <a4j:commandButton id="btnClose" value="Close" action="#{ShadowBalanceClearance.close}" reRender="row3, stxtMsg,stxtClearingDt,ddRegisterDate,ddClearingMode,ddDywthabc,txtInstNo,txtInstAmounts,stxtBankName,stxtBankAddress,stxtAcno,ddReasonForReturn,txtTotalCheques,txtTotalAmount,txtRtCheques,txtRtAmount,rightPanel1,rightPanel"  rendered="#{ShadowBalanceClearance.bankClearingHoldRel == 'YES'}" focus="ddClearingMode">
                            </a4j:commandButton>
                            <rich:modalPanel id="deletePanel1" autosized="true" width="310">
                                <f:facet name="header">
                                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                                </f:facet>
                                <f:facet name="controls">
                                    <h:panelGroup>
                                        <h:graphicImage value="/images/modal/close.png" styleClass="hidelink" id="hidelink2" />
                                        <rich:componentControl for="deletePanel1" attachTo="hidelink2" operation="hide" event="onclick" />
                                    </h:panelGroup>
                                </f:facet>
                                <h:form>
                                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                        <tbody>
                                            <tr style="height:40px">
                                                <td colspan="2">
                                                    <h:outputText value="Are you sure that you want to release the clearing of : "/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center" width="50%">
                                                    <a4j:commandButton value="Yes" ajaxSingle="true" action="#{ShadowBalanceClearance.bankNameTableUpdation}"
                                                                       oncomplete="#{rich:component('deletePanel1')}.hide();" reRender="row3,taskList,stxtMsg" />
                                                </td>
                                                <td align="center" width="50%">
                                                    <a4j:commandButton value="Cancel" onclick="#{rich:component('deletePanel1')}.hide();return false;" />
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </h:form>
                            </rich:modalPanel>
                            <a4j:region>
                                <rich:dataTable value="#{ShadowBalanceClearance.balClearanceTable}" var="dataItem"  rendered="#{ShadowBalanceClearance.bankClearingHoldRel == 'NO'}"  id="taskList1" rowKeyVar="row" rowClasses="gridrow1,gridrow2"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column><h:outputText value="Status"/></rich:column>
                                            <rich:column><h:outputText value="A/C No."/></rich:column>
                                            <rich:column><h:outputText value="Instr. Dt"/></rich:column>
                                            <rich:column><h:outputText value="Instr.No"/></rich:column>
                                            <rich:column><h:outputText value="Amount(Rs.)"/></rich:column>
                                            <rich:column><h:outputText value="Bank Name"/></rich:column>
                                            <rich:column><h:outputText value="Remarks"/></rich:column>
                                            <rich:column><h:outputText value="VoucherNo"/></rich:column>
                                            <rich:column><h:outputText value="Bank Address"/></rich:column>
                                            <rich:column><h:outputText value="Return Reason"/></rich:column>
                                            <rich:column><h:outputText value="Select"/></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{dataItem.txnStatus}"/></rich:column>
                                    <rich:column style="text-align:left;"><h:outputText value="#{dataItem.acno}" /></rich:column>
                                    <rich:column style="text-align:left;"><h:outputText value="#{dataItem.txnInstDate}"/></rich:column>
                                    <rich:column style="text-align:left;"><h:outputText value="#{dataItem.txnInstNo}" /></rich:column>
                                    <rich:column style="text-align:right;">
                                        <h:outputText value="#{dataItem.txnInstAmt}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                    </rich:column>
                                    <rich:column style="text-align:left;"><h:outputText value="#{dataItem.txnBankName}" /></rich:column>
                                    <rich:column style="text-align:left;"><h:outputText value="#{dataItem.remarks}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.vTot}" /></rich:column>
                                    <rich:column style="text-align:left;"><h:outputText value="#{dataItem.txnBankAddress}" /></rich:column>
                                    <rich:column style="text-align:left;"><h:outputText value="#{dataItem.returnDesc}" /></rich:column>
                                    <rich:column style="text-align:center;width:40px">
                                        <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{ShadowBalanceClearance.setRowValues}"  focus="ddReasonForReturn"
                                                         reRender="stxtBankAddress,stxtAcno,txtInstNo,stxtBankName,txtInstAmounts,calInstDate,stxtMsg,stxtClearingDt,ddReasonForReturn,rightPanel1,taskList2,taskList1,txtTotalCheques,txtTotalAmount,txtRtCheques,txtRtAmount" oncomplete="setMask()" >
                                            <h:graphicImage value="/resources/images/passed.gif" style="border:0" />
                                            <f:setPropertyActionListener value="#{dataItem}" target="#{ShadowBalanceClearance.currentItem}"/>
                                            <f:setPropertyActionListener value="#{row}" target="#{ShadowBalanceClearance.currentRow}"/>
                                        </a4j:commandLink>
                                    </rich:column>
                                </rich:dataTable>
                            </a4j:region>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col7,col12" columns="2" width="100%" style="border:1px ridge #BED6F8">
                        <h:panelGrid id="gridPaneldf1" width="100%">
                            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="row9" style="width:100%;text-align:left;" styleClass="row2">
                                <h:outputLabel id="lblInstNo" styleClass="label" value="Inst. No"/>
                                <h:inputText id="txtInstNo" styleClass="input" disabled="#{ShadowBalanceClearance.flag}" value="#{ShadowBalanceClearance.instrNo}" style="width:80px" maxlength="10"/>
                                <h:outputLabel id="lblInstDt" styleClass="label" value="Inst. Date"/>
                                <h:inputText id="calInstDate" styleClass="input calInstDate" value="#{ShadowBalanceClearance.instDate}" disabled="#{ShadowBalanceClearance.flag}" style="width:75px;">
                                    <f:convertDateTime pattern="dd/MM/yyyy" />
                                    <a4j:support  event="onchanged"  oncomplete="setMask()" />
                                </h:inputText>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="row4" style="width:100%;text-align:left;" styleClass="row1">
                                <h:outputLabel id="lblInstAmount" styleClass="label" value="Inst. Amount"/>
                                <h:inputText id="txtInstAmounts" styleClass="input" disabled="#{ShadowBalanceClearance.flag}" value="#{ShadowBalanceClearance.txtInstAmount}"  style="width:80px" maxlength="16">
                                    <a4j:support event="onblur" actionListener="#{ShadowBalanceClearance.getCurrentTableData}"  focus="ddReasonForReturn" limitToList="true"
                                                 reRender="stxtAcno,stxtBankName,stxtBankAddress,ddReasonForReturn,stxtMsg,txtInstNo,calInstDate,txtInstAmounts"/>
                                </h:inputText>
                                <h:outputLabel id="lblAcno" styleClass="label" value="Account No"/>
                                <h:outputText id="stxtAcno" styleClass="output" value="#{ShadowBalanceClearance.acno}"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col7,col12" columns="2" id="row7" style="width:100%;text-align:left;" styleClass="row2">
                                <h:outputLabel id="lblMicrCode" styleClass="label" for="txtMicrCode" value="Micr Code" />
                                <h:panelGroup id="tt1" layout="block" style="width:100%">
                                    <h:inputText id="txtMicrCode" styleClass="input" style="width:45px"  disabled="true"/>
                                    <h:inputText id="txtMicrCode1" styleClass="input" style="width:45px"  disabled="true"/>
                                    <h:inputText id="txtMicrCode2" styleClass="input" style="width:45px"  disabled="true"/>
                                </h:panelGroup>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col7,col12" columns="2" id="rowds7" style="width:100%;text-align:left;" styleClass="row1">
                                <h:outputLabel id="lblBankName" styleClass="label" value="Bank Name"/>
                                <h:outputText id="stxtBankName" styleClass="output" value="#{ShadowBalanceClearance.bankName}"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col7,col12" columns="2" id="row10" style="width:100%;text-align:left;" styleClass="row2">
                                <h:outputLabel id="lblBankAddress" styleClass="label" value="Bank Address"/>
                                <h:outputText id="stxtBankAddress" styleClass="output" value="#{ShadowBalanceClearance.bankAdd}"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col7,col12" columns="2" id="rowfr10" style="width:100%;text-align:left;" styleClass="row1">
                                <h:outputLabel id="lblReasonForReturn" styleClass="label" value="Reason For Return"/>
                                <h:selectOneListbox id="ddReasonForReturn" styleClass="ddlist" size="1" style="width:150px" value="#{ShadowBalanceClearance.reasonsReturn}" tabindex="7">
                                    <f:selectItem itemValue="--SELECT--"/>
                                    <f:selectItems value="#{ShadowBalanceClearance.reasonForCancel}" />
                                    <a4j:support actionListener="#{ShadowBalanceClearance.reasonForReturnProcessValueChange}" event="onchange" oncomplete="if(#{ShadowBalanceClearance.reasonPenalDisplay=='True'}){#{rich:component('reasonDisPanel')}.show();}"
                                                 reRender="taskList2,txtInstNo,txtInstAmounts,stxtMsg,stxtBankName,stxtAcno"  limitToList="true" />
                                </h:selectOneListbox>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col7,col12" columns="2" id="rowfrsgfg10" style="width:100%;text-align:left;" styleClass="row2">
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGroup layout="block" id="rightPanel1" style="width:100%;height:240px;background-color:#e8eef7;overflow:auto;">
                            <a4j:region>
                                <rich:dataTable  value="#{ShadowBalanceClearance.balReturnTable}" var="dataItem1"  rowClasses="gridrow1,gridrow2" id="taskList2" rowKeyVar="row"
                                                 onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="10"><h:outputText value="Returned Cheques Details" /></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="Account No"/></rich:column>
                                            <rich:column><h:outputText value="Txn InstNo"/></rich:column>
                                            <rich:column><h:outputText value="Txn Inst Date"/></rich:column>
                                            <rich:column><h:outputText value="Txn Inst Amt"/></rich:column>
                                            <rich:column><h:outputText value="ReasonForCancel"/></rich:column>
                                            <rich:column><h:outputText value="TxnType"/></rich:column>
                                            <rich:column><h:outputText value="Txn Bank Name"/></rich:column>
                                            <rich:column><h:outputText value="Txn Bank Address"/></rich:column>
                                            <rich:column><h:outputText value="Vtot"/></rich:column>
                                            <rich:column><h:outputText value="Select"/></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{dataItem1.acno}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem1.txnInstNo}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem1.txnInstDate}" /></rich:column>
                                    <rich:column>
                                        <h:outputText value="#{dataItem1.txnInstAmt}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                    </rich:column>
                                    <rich:column><h:outputText value="#{dataItem1.reasonForCancel}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem1.enFlag}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem1.txnBankName}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem1.txnBankAddress}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem1.vtot}" /></rich:column>
                                    <rich:column>
                                        <a4j:commandLink ajaxSingle="true" id="update1link" oncomplete="#{rich:component('shadowTxnTablePanel')}.show()">
                                            <h:graphicImage value="/resources/images/passed.gif" style="border:0" />
                                            <f:setPropertyActionListener value="#{dataItem1}" target="#{ShadowBalanceClearance.currentItem2}"/>
                                            <f:setPropertyActionListener value="#{row}" target="#{ShadowBalanceClearance.currentRow}" />
                                        </a4j:commandLink>
                                    </rich:column>
                                </rich:dataTable>
                            </a4j:region>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columns="8" id="row11" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputLabel id="lblTotalCheques" styleClass="label" value="Total Cheques"/>
                        <h:inputText id="txtTotalCheques" styleClass="input"style="width:80px" value="#{ShadowBalanceClearance.totalCheque}"  disabled="true"/>
                        <h:outputLabel id="lblTotalAmount" styleClass="label" value="Total Amount"/>
                        <h:inputText id="txtTotalAmount" styleClass="input"style="width:100px" value="#{ShadowBalanceClearance.totalAmount}"  disabled="true"/>
                        <h:outputLabel id="lblRtCheques" styleClass="label" value="Return Cheques"/>
                        <h:inputText id="txtRtCheques" styleClass="input"style="width:80px" value="#{ShadowBalanceClearance.rtTotalCheque}"  disabled="true"/>
                        <h:outputLabel id="lblRtAmount" styleClass="label" value="Return Amount"/>
                        <h:inputText id="txtRtAmount" styleClass="input"style="width:100px" value="#{ShadowBalanceClearance.rtTotalAmount}"  disabled="true"/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnRelease" value="Release" action="#{ShadowBalanceClearance.btnReleaseClearance}" disabled = "#{ShadowBalanceClearance.disableReleaseBtn}"reRender="stxtMsg,rightPanel,txtBankMicrCode,ddDywthabc" tabindex="8"  focus="txtBankMicrCode">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnClear" value="Clear" oncomplete="#{rich:component('clearPanel')}.show()"  tabindex="9">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{ShadowBalanceClearance.resetAllValue1}" reRender="stxtMsg,stxtClearingDt,ddRegisterDate,ddClearingMode,ddDywthabc,txtInstNo,txtInstAmounts,stxtBankName,stxtBankAddress,stxtAcno,ddReasonForReturn,txtTotalCheques,txtTotalAmount,txtRtCheques,txtRtAmount,rightPanel1,rightPanel,ddBranch" tabindex="10" focus="ddBranch">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{ShadowBalanceClearance.exitFrm}" reRender="stxtMsg,stxtClearingDt,ddRegisterDate,ddClearingMode,ddDywthabc,txtInstNo,txtInstAmounts,stxtBankName,stxtBankAddress,stxtAcno,ddReasonForReturn,txtTotalCheques,txtTotalAmount,txtRtCheques,txtRtAmount,rightPanel1,rightPanel,ddBranch"  tabindex="11">
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
            </a4j:form>

            <rich:modalPanel id="reasonDisPanel" autosized="true" width="310">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/images/modal/close.png" styleClass="hidelink" id="hidelink3" />
                        <rich:componentControl for="reasonDisPanel" attachTo="hidelink3" operation="hide" event="onclick" />
                    </h:panelGroup>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are you sure that you want to Return this Instrument.?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" ajaxSingle="true"  action="#{ShadowBalanceClearance.reasonForReturnInformation}"
                                                       oncomplete="#{rich:component('reasonDisPanel')}.hide(); setMask()" reRender="taskList2,stxtMsg,taskList1,txtInstNo,stxtBankName,txtInstAmounts,ddReasonForReturn,stxtAcno,txtTotalCheques,stxtClearingDt,ddDywthabc,txtTotalAmount,txtRtCheques,txtRtAmount,rightPanel,rightPanel1,stxtBankAddress,calInstDate"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Cancel" onclick="#{rich:component('reasonDisPanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="shadowTxnTablePanel" autosized="true" width="230">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/images/modal/close.png" styleClass="hidelink" id="hidelinka3" />
                        <rich:componentControl for="shadowTxnTablePanel" attachTo="hidelinka3" operation="hide" event="onclick" />
                    </h:panelGroup>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Do you want to reverse this cheque ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" ajaxSingle="true"    action="#{ShadowBalanceClearance.grdTxnDblClickUpdation}"
                                                       oncomplete="#{rich:component('shadowTxnTablePanel')}.hide();" reRender="txtTotalCheques,stxtClearingDt,ddDywthabc,txtTotalAmount,txtInstNo,txtInstAmounts,txtRtCheques,txtRtAmount,taskList2,taskList1,rightPanel1,rightPanel,stxtMsg"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Cancel" onclick="#{rich:component('shadowTxnTablePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="clearPanel" autosized="true" width="200">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/images/modal/close.png" styleClass="hidelink" id="hidelinkap" />
                        <rich:componentControl for="clearPanel" attachTo="hidelinkap" operation="hide" event="onclick" />
                    </h:panelGroup>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Do You Want to Clear this Cheque ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" ajaxSingle="true"    action="#{ShadowBalanceClearance.clearButton}"
                                                       oncomplete="#{rich:component('clearPanel')}.hide();" reRender="stxtMsg,stxtClearingDt,ddRegisterDate,ddClearingMode,ddDywthabc,txtInstNo,txtInstAmounts,stxtBankName,stxtBankAddress,stxtAcno,ddReasonForReturn,txtTotalCheques,txtTotalAmount,txtRtCheques,txtRtAmount,rightPanel1,rightPanel"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Cancel" onclick="#{rich:component('clearPanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>