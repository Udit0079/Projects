<%-- 
    Document   : InwardCheques
    Created on : Sep 27, 2010, 10:41:51 AM
    Author     : ROHIT KRISHNA GUPTA
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>IBC Cheques</title>
            <script type="text/javascript">

            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{InwardCheques.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="IBC Cheques"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{InwardCheques.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="errorMessage" styleClass="error" value="#{InwardCheques.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{InwardCheques.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="2" id="a7" width="100%">
                        <h:panelGrid columns="6" id="a6" style="height:30px;" styleClass="row1" columnClasses="col9" width="100%">
                            <h:outputLabel id="lblAcctNo" styleClass="label" value="Account No. :" style=""><font class="required" color="red">*</font></h:outputLabel>
                         <%--   <h:selectOneListbox id="ddAcctNo" tabindex="1" styleClass="ddlist"  value="#{InwardCheques.acctType}" size="1" >
                                <f:selectItems value="#{InwardCheques.acctNoOption}" />
                            </h:selectOneListbox> --%>
                         <h:inputText id="txtAcNo" tabindex="2" value="#{InwardCheques.oldAcctNo}" maxlength="12" onkeyup="this.value = this.value.toUpperCase();"  size="12" styleClass="input"  >
                                <a4j:support action="#{InwardCheques.getAccountDetail}" event="onblur"
                                             reRender="message,errorMessage,a10,a11,a13,a14" limitToList="true" focus="txtInstAmt" />
                            </h:inputText>
                            <h:outputLabel id="stxtAcctNo" styleClass="label" value="#{InwardCheques.acctNo}" style=""></h:outputLabel>
                        </h:panelGrid>
                        <h:panelGrid columns="6" id="a9" style="height:30px;" styleClass="row1" columnClasses="col9" width="100%">
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a10" width="100%" style="height:30px;" styleClass="row2">
                            <h:outputLabel id="lblAcNo" styleClass="label" value="Account No. :" />
                            <h:outputText id="stxtAcNo" styleClass="output" value="#{InwardCheques.actNo}" style="color:purple;"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a13" width="100%" style="height:30px;" styleClass="row2">
                            <h:outputLabel id="lblCustName" styleClass="label" value="Customer Name :" />
                            <h:outputText id="stxtCustName" styleClass="output" value="#{InwardCheques.custName}" style="color:purple;"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a11" width="100%" style="height:30px;" styleClass="row1">
                            <h:outputLabel id="lblJtName1" styleClass="label" value="Joint Name 1 :" />
                            <h:outputText id="stxtJtName1" styleClass="output" value="#{InwardCheques.jtName1}" style="color:purple;"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a14" width="100%" style="height:30px;" styleClass="row1">
                            <h:outputLabel id="lblJtName2" styleClass="label" value="Joint Name 2 :" />
                            <h:outputText id="stxtJtName2" styleClass="output" value="#{InwardCheques.jtName2}" style="color:purple;"/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columns="6" id="a15" style="height:30px;" styleClass="row2" columnClasses="col3,col3,col3,col3,col3,col1" width="100%">
                        <h:outputLabel id="lblInstAmt" styleClass="label" value="Inst. Amount :" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtInstAmt" maxlength="11" tabindex="3" size="15" value="#{InwardCheques.instAmt}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                        <h:outputLabel id="lblInstNo" styleClass="label" value="Inst No. :" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtInstNo" maxlength="10" tabindex="4" size="15" value="#{InwardCheques.instNo}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input">
                            <a4j:support action="#{InwardCheques.instNoLostFocus}" event="onblur"
                                         reRender="message,errorMessage,txtInstNo" limitToList="true" focus="#{rich:clientId('calInstDate')}InputDate"/>
                        </h:inputText>
                        <h:outputLabel id="lblInstDate" styleClass="label" value="Inst Date :" ><font class="required" color="red">*</font></h:outputLabel>
                        <rich:calendar datePattern="dd/MM/yyyy" id="calInstDate" value="#{InwardCheques.instDate}" tabindex="5" />
                    </h:panelGrid>
                    <h:panelGrid columns="6" id="a16" style="height:30px;" styleClass="row1" columnClasses="col3,col3,col3,col3,col3,col1" width="100%">
                        <h:outputLabel id="lblBankType" styleClass="label" value="Bank Type :" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddBankType" tabindex="6" styleClass="ddlist"  value="#{InwardCheques.bankType}" size="1" style="width: 102px">
                            <f:selectItems value="#{InwardCheques.bankTypeList}" />
                            <a4j:support action="#{InwardCheques.bankTypeComboLostFocus}" event="onblur" oncomplete="if(#{rich:element('ddBankType')}.value == 'Our Bank'){#{rich:element('ddAlphaCode')}.focus();}else{#{rich:element('ddBillType')}.focus();}"
                                         reRender="message,errorMessage,a20,a21,ddAlphaCode,ddRemType,a20,a21,lblInFavOf,gpFooter" limitToList="true"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblAlphaCode" styleClass="label" value="Alpha Code :" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddAlphaCode" tabindex="7" styleClass="ddlist" disabled="#{InwardCheques.bankFlag}" value="#{InwardCheques.alphaCode}" size="1" style="width: 102px">
                            <f:selectItems value="#{InwardCheques.alphaCodeList}" />
                            <a4j:support action="#{InwardCheques.alphaCodeLostFocus}" event="onchange"
                                         reRender="message,errorMessage,a20,a21" limitToList="true" focus="ddBillType" />
                        </h:selectOneListbox>
                        <h:outputLabel id="lblBillType" styleClass="label" value="Bill Type :" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddBillType" tabindex="8" styleClass="ddlist"  value="#{InwardCheques.billType}" size="1" style="width: 102px">
                            <f:selectItems value="#{InwardCheques.billTypeList}" />
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columns="6" id="a17" style="height:30px;" styleClass="row2" columnClasses="col3,col3,col3,col3,col3,col1" width="100%">
                        <h:outputLabel id="lblInFavOf" styleClass="label" value="#{InwardCheques.labelParty}" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtInFavOf" maxlength="100" tabindex="9" size="15" value="#{InwardCheques.inFavOf}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                        <h:outputLabel id="lblPayableAt" styleClass="label" value="Payable At :" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddPayableAt" tabindex="10" styleClass="ddlist"  value="#{InwardCheques.payableAt}" size="1" style="width: 102px">
                            <f:selectItems value="#{InwardCheques.alphaCodeList}" />
                        </h:selectOneListbox>
                        <h:outputLabel id="lblRefNo" styleClass="label" value="Ref No. :" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtRefNo" maxlength="15" tabindex="11" size="15" value="#{InwardCheques.refNo}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="2" id="a18" width="100%">
                        <h:panelGrid columnClasses="col9" columns="6" id="a20" width="100%" style="height:30px;" styleClass="row1">
                            <h:outputLabel id="lblBankName" styleClass="label" value="Bank Name :" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtBankName" maxlength="100" tabindex="12" size="30" value="#{InwardCheques.bankName}" disabled="#{InwardCheques.bankPanelFlag}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a21" width="100%" style="height:30px;" styleClass="row1">
                            <h:outputLabel id="lblBankAddress" styleClass="label" value="Bank Address :" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtBankAddress" maxlength="100" tabindex="13" size="30" value="#{InwardCheques.bankAddress}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input" disabled="#{InwardCheques.bankPanelFlag}"/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columns="6" id="a22" style="height:30px;" styleClass="row2" columnClasses="col3,col3,col3,col3,col3,col1" width="100%">
                        <h:outputLabel id="lblRemType" styleClass="label" value="Remittance Type :" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddRemType" tabindex="14" styleClass="ddlist"  value="#{InwardCheques.remType}" size="1" style="width: 102px" >
                            <f:selectItems value="#{InwardCheques.remTypeList}" />
                        </h:selectOneListbox>
                        <h:outputLabel id="lblOtherBankComm" styleClass="label" value="Other Bank Comm. :" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtOtherBankComm" maxlength="9" tabindex="15" size="15" value="#{InwardCheques.otherBankCom}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                        <h:outputLabel id="lblOtherBankPost" styleClass="label" value="Other Bank Postage :" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtOtherBankPost" maxlength="9" tabindex="16" size="15" value="#{InwardCheques.otherBankPostage}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="table" style="height:auto;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{InwardCheques.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="3" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="15"><h:outputText value="Details" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Serial No." /></rich:column>
                                        <rich:column><h:outputText value="Account No." /></rich:column>
                                        <rich:column><h:outputText value="Inst. No." /></rich:column>
                                        <rich:column><h:outputText value="Inst. Amount" /></rich:column>
                                        <rich:column><h:outputText value="Inst. Date" /></rich:column>
                                        <rich:column><h:outputText value="Payable At" /></rich:column>
                                        <rich:column><h:outputText value="In Favour Of" /></rich:column>
                                        <rich:column><h:outputText value="Bank Name" /></rich:column>
                                        <rich:column><h:outputText value="Bank Address" /></rich:column>
                                        <rich:column><h:outputText value="Bill Type" /></rich:column>
                                        <rich:column><h:outputText value="Remittance Type" /></rich:column>
                                        <rich:column><h:outputText value="Enter By" /></rich:column>
                                        <rich:column width="20"><h:outputText value="Delete" /></rich:column>
                                        <rich:column width="20"><h:outputText value="Select" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.sNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.acNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.instNo}" /></rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem.instAmt}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column><h:outputText value="#{dataItem.instDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.payableAt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.inFavOf}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.bankName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.bankAddress}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.billType}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.remType}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.enterBy}" /></rich:column>
                                <rich:column>
                                    <a4j:commandLink ajaxSingle="true" id="deletelink" oncomplete="#{rich:component('deletePanel')}.show()" reRender="a6,a10,a11,a13,a14,message,errorMessage,lpg,a16,a15,a20,a17,a21,a22,gpFooter,table,taskList">
                                        <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{InwardCheques.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{InwardCheques.currentRow}" />
                                    </a4j:commandLink>
                                </rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink id="selectlink" action="#{InwardCheques.fillValuesofGridInFields}" oncomplete="#{rich:element('txtAcNo')}.disabled = true;#{rich:element('txtInstNo')}.disabled = true;#{rich:element('btnSave')}.disabled = true;#{rich:element('btnUpdate')}.focus()"
                                                     reRender="a6,a10,a11,a13,a14,message,errorMessage,lpg,a16,a15,a20,a17,a21,a22,gpFooter,table,taskList" >
                                        <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{InwardCheques.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{InwardCheques.currentRow}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="20" />
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnSave" tabindex="17" disabled="#{InwardCheques.btnFlag}" value="Save" oncomplete="#{rich:component('savePanel')}.show()" reRender="a6,a10,a11,a13,a14,message,errorMessage,lpg,a16,a15,a20,a17,a21,a22,gpFooter,table,taskList" focus=""/>
                            <a4j:commandButton id="btnUpdate" tabindex="18" disabled="#{InwardCheques.btnUpdateFlag}" value="Update" oncomplete="#{rich:component('modifyPanel')}.show()" reRender="a6,a10,a11,a13,a14,message,errorMessage,lpg,a16,a15,a20,a17,a21,a22,gpFooter,table,taskList" />
                            <a4j:commandButton id="btnRefresh" tabindex="19" value="Refresh" action="#{InwardCheques.resetForm}" oncomplete="#{rich:element('txtInstNo')}.disabled = false;#{rich:element('txtAcNo')}.disabled = false;" reRender="a6,a10,a11,a13,a14,message,errorMessage,lpg,a16,a15,a20,a17,a21,a22,gpFooter,table,taskList" focus="txtAcNo"/>
                            <a4j:commandButton id="btnExit" tabindex="20" value="Exit" action="#{InwardCheques.exitForm}" reRender="message,errorMessage" />
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="deletePanel" autosized="true" width="250" onshow="#{rich:element('btnYesDel')}.focus()">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure To Delete This Record ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesDel" ajaxSingle="true"  action="#{InwardCheques.delete}"
                                                       oncomplete="#{rich:component('deletePanel')}.hide();" reRender="a6,a10,a11,a13,a14,message,errorMessage,lpg,a16,a15,a20,a17,a21,a22,gpFooter,table,taskList" focus="ddAcctNo"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNoDel" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="savePanel" autosized="true" width="250" onshow="#{rich:element('btnYesSave')}.focus()">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure To Save ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesSave" ajaxSingle="true" action="#{InwardCheques.saveDetail}"
                                                       oncomplete="#{rich:component('savePanel')}.hide();" reRender="a6,a10,a11,a13,a14,message,errorMessage,lpg,a16,a15,a20,a17,a21,a22,gpFooter,table,taskList" focus="ddAcctNo"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNoSave" onclick="#{rich:component('savePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="modifyPanel" autosized="true" width="250" onshow="#{rich:element('btnYesModify')}.focus()">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure To Modify ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesModify" ajaxSingle="true" action="#{InwardCheques.updateDetail}"
                                                       oncomplete="#{rich:component('modifyPanel')}.hide();" reRender="a6,a10,a11,a13,a14,message,errorMessage,lpg,a16,a15,a20,a17,a21,a22,gpFooter,table,taskList" focus="ddAcctNo"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNoModify" onclick="#{rich:component('modifyPanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
