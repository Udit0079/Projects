<%-- 
    Document   : OutwardCheques
    Created on : Sep 29, 2010, 11:18:05 AM
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
            <title>Booking Outward Cheque</title>
            <script type="text/javascript">

            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{OutwardCheques.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Booking Outward Cheque"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{OutwardCheques.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="errorMessage" styleClass="error" value="#{OutwardCheques.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{OutwardCheques.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="2" id="a7" width="100%">
                        <h:panelGrid columns="6" id="a8" style="height:30px;" styleClass="row1" columnClasses="col9" width="100%">
                            <h:outputLabel id="lblAcctNo" styleClass="label" value="Account No. :" ><font class="required" color="red">*</font></h:outputLabel>
                           <%--   <h:selectOneListbox id="ddAcctNo" tabindex="1" styleClass="ddlist"  value="#{OutwardCheques.acctType}" size="1" >
                                <f:selectItems value="#{OutwardCheques.acctNoOption}" />
                            </h:selectOneListbox>  --%>
                            <h:inputText id="txtAcNo" tabindex="2" value="#{OutwardCheques.oldAcctNo}" onkeyup="this.value = this.value.toUpperCase();"  size="12" styleClass="input" maxlength="12" >
                                <a4j:support action="#{OutwardCheques.getAccountDetail}" event="onblur"
                                             reRender="message,errorMessage,a9,a10,a11,a13,a14,stxtAcctNo" limitToList="true" focus="txtInstAmt" />
                            </h:inputText>
                            <h:outputLabel id="stxtAcctNo" styleClass="label" value="#{OutwardCheques.acctNo}" ></h:outputLabel>
                        </h:panelGrid>
                        <h:panelGrid columns="6" id="a12" style="height:30px;" styleClass="row1" columnClasses="col9" width="100%">
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a9" width="100%" style="height:30px;" styleClass="row2">
                            <h:outputLabel id="lblAcNo" styleClass="label" value="Account No. :" style=""/>
                            <h:outputText id="stxtAcNo" styleClass="output" value="#{OutwardCheques.actNo}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a13" width="100%" style="height:30px;" styleClass="row2">
                            <h:outputLabel id="lblCustName" styleClass="label" value="Customer Name :" style=""/>
                            <h:outputText id="stxtCustName" styleClass="output" value="#{OutwardCheques.custName}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a10" width="100%" style="height:30px;" styleClass="row1">
                            <h:outputLabel id="lblJtName1" styleClass="label" value="Joint Name 1 :" style=""/>
                            <h:outputText id="stxtJtName1" styleClass="output" value="#{OutwardCheques.jtName1}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a14" width="100%" style="height:30px;" styleClass="row1">
                            <h:outputLabel id="lblJtName2" styleClass="label" value="Joint Name 2 :" style=""/>
                            <h:outputText id="stxtJtName2" styleClass="output" value="#{OutwardCheques.jtName2}"/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columns="6" id="a15" style="height:30px;" styleClass="row2" columnClasses="col3,col3,col3,col3,col3,col1" width="100%">
                        <h:outputLabel id="lblInstAmt" styleClass="label" value="Inst. Amount :" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtInstAmt" maxlength="9" tabindex="3" size="15" value="#{OutwardCheques.instAmt}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                        <h:outputLabel id="lblInstNo" styleClass="label" value="Inst. No. :" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtInstNo" maxlength="10" tabindex="4" size="15" value="#{OutwardCheques.instNo}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input">
                            <a4j:support action="#{OutwardCheques.instNoLostFocus}" event="onblur"
                                         reRender="message,errorMessage,txtInstNo" limitToList="true" focus="#{rich:clientId('calInstDate')}InputDate" />
                        </h:inputText>
                        <h:outputLabel id="lblInstDate" styleClass="label" value="Inst. Date :"><font class="required" color="red">*</font></h:outputLabel>
                        <rich:calendar datePattern="dd/MM/yyyy" id="calInstDate" value="#{OutwardCheques.instDate}" tabindex="5" />
                    </h:panelGrid>
                    <h:panelGrid columns="6" id="a16" style="height:30px;" styleClass="row1" columnClasses="col3,col3,col3,col3,col3,col1" width="100%">
                        <h:outputLabel id="lblInstBankType" styleClass="label" value="Inst. Bank Type :" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddInstBankType" tabindex="6" styleClass="ddlist"  value="#{OutwardCheques.instBankType}" size="1" style="width: 102px">
                            <f:selectItems value="#{OutwardCheques.instBankTypeList}" />
                            <a4j:support action="#{OutwardCheques.instBankTypeComboLostFocus}" event="onblur" oncomplete="if(#{rich:element('ddInstBankType')}.value == 'Our Bank'){#{rich:element('ddInstAlphaCode')}.focus();}else{#{rich:element('txtInFavOf')}.focus();}"
                                         reRender="message,errorMessage,a17,a20,a21,ddInstAlphaCode,gpFooter" limitToList="true" focus="ddInstAlphaCode"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblInstAlphaCode" styleClass="label" value="Inst. Alpha Code :" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddInstAlphaCode" tabindex="7" styleClass="ddlist" disabled="#{OutwardCheques.bankFlag}" value="#{OutwardCheques.instAlphaCode}" size="1" style="width: 102px">
                            <f:selectItems value="#{OutwardCheques.instAlphaCodeList}" />
                            <a4j:support action="#{OutwardCheques.instAlphaCodeLostFocus}" event="onchange"
                                         reRender="message,errorMessage,a17" limitToList="true" focus="txtInFavOf" />
                        </h:selectOneListbox>
                        <h:outputLabel id="lblInFavOf" styleClass="label" value="In Favour Of :" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtInFavOf" maxlength="100" tabindex="8" size="15" value="#{OutwardCheques.inFavOf}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="2" id="a17" width="100%">
                        <h:panelGrid columnClasses="col9" columns="6" id="a20" width="100%" style="height:30px;" styleClass="row1">
                            <h:outputLabel id="lblInstBankName" styleClass="label" value="Inst. Bank Name :" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtInstBankName" maxlength="100" tabindex="9" size="40" value="#{OutwardCheques.instBankName}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input" disabled="#{OutwardCheques.bankPanelFlag}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a21" width="100%" style="height:30px;" styleClass="row1">
                            <h:outputLabel id="lblInstBankAddress" styleClass="label" value="Inst. Bank Address :" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtInstBankAddress" maxlength="100" tabindex="10" size="40" value="#{OutwardCheques.instBankAddress}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input" disabled="#{OutwardCheques.bankPanelFlag}"/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columns="6" id="a18" style="height:30px;" styleClass="row1" columnClasses="col3,col3,col3,col3,col3,col1" width="100%">
                        <h:outputLabel id="lblColBankType" styleClass="label" value="Collecting Bank Type :" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddColBankType" tabindex="11" styleClass="ddlist"  value="#{OutwardCheques.collectBankType}" size="1" style="width: 102px">
                            <f:selectItems value="#{OutwardCheques.collectBankTypeList}" />
                            <a4j:support action="#{OutwardCheques.collectBankTypeComboLostFocus}" event="onblur" oncomplete="if(#{rich:element('ddColBankType')}.value == 'Our Bank'){#{rich:element('ddColAlphaCode')}.focus();}else{#{rich:element('ddBillType')}.focus();}"
                                         reRender="message,errorMessage,a19,a20,a21,a22,a23,txtColBankName,txtColBankAddress,ddColAlphaCode,gpFooter" limitToList="true"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblColAlphaCode" styleClass="label" value="Collecting Alpha Code :" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddColAlphaCode" tabindex="12" styleClass="ddlist" value="#{OutwardCheques.collectAlphaCode}" size="1" style="width: 102px" disabled="#{OutwardCheques.bankFlag1}">
                            <f:selectItems value="#{OutwardCheques.collectAlphaCodeList}" />
                            <a4j:support action="#{OutwardCheques.collectAlphaCodeLostFocus}" event="onchange"
                                         reRender="message,errorMessage,a19" limitToList="true" focus="ddBillType" />
                        </h:selectOneListbox>
                        <h:outputLabel id="lblBillType" styleClass="label" value="Bill Type :" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddBillType" tabindex="13" styleClass="ddlist"  value="#{OutwardCheques.billType}" size="1" style="width: 102px">
                            <f:selectItems value="#{OutwardCheques.billTypeList}" />
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="2" id="a19" width="100%">
                        <h:panelGrid columnClasses="col9" columns="6" id="a22" width="100%" style="height:30px;" styleClass="row1">
                            <h:outputLabel id="lblColBankName" styleClass="label" value="Colecting Bank Name :" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtColBankName" maxlength="100" tabindex="14" size="40" value="#{OutwardCheques.collectBankName}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input" disabled="#{OutwardCheques.bankPanelFlag1}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a23" width="100%" style="height:30px;" styleClass="row1">
                            <h:outputLabel id="lblColBankAddress" styleClass="label" value="Collecting Bank Address :" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtColBankAddress" maxlength="100" tabindex="15" size="40" value="#{OutwardCheques.collectBankAddress}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input" disabled="#{OutwardCheques.bankPanelFlag1}"/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="2" id="a24" width="100%">
                        <h:panelGrid columnClasses="col9" columns="6" id="a25" width="100%" style="height:30px;" styleClass="row1">
                            <h:outputLabel id="lblCommision" styleClass="label" value="Commision :" />
                            <h:inputText id="txtCommision" maxlength="9" tabindex="16" size="16" value="#{OutwardCheques.comm}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a26" width="100%" style="height:30px;" styleClass="row1">
                            <h:outputLabel id="lblPostage" styleClass="label" value="Postage :" />
                            <h:inputText id="txtPostage" maxlength="9" tabindex="17" size="17" value="#{OutwardCheques.postage}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input" />
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="table" style="height:auto;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{OutwardCheques.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="2" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="15"><h:outputText value="Details" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Serial No." /></rich:column>
                                        <rich:column><h:outputText value="Account No." /></rich:column>
                                        <rich:column><h:outputText value="Inst. No." /></rich:column>
                                        <rich:column>
                                            <h:outputText value="Inst. Amount">
                                                <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                             </h:outputText>
                                        </rich:column>
                                        <rich:column><h:outputText value="Inst. Date" /></rich:column>
                                        <rich:column><h:outputText value="In Favour Of" /></rich:column>
                                        <rich:column><h:outputText value="Bank Name" /></rich:column>
                                        <rich:column><h:outputText value="Bank Address" /></rich:column>
                                        <rich:column><h:outputText value="Bill Type" /></rich:column>
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
                                <rich:column><h:outputText value="#{dataItem.inFavOf}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.bankName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.bankAddress}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.billType}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.enterBy}" /></rich:column>
                                <rich:column>
                                    <a4j:commandLink ajaxSingle="true" id="deletelink" oncomplete="#{rich:component('deletePanel')}.show()" reRender="a9,a10,a8,a13,a14,message,errorMessage,lpg,a15,a16,a18,a17,a22,a23,a25,a26,gpFooter,table,taskList">
                                        <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{OutwardCheques.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{OutwardCheques.currentRow}" />
                                    </a4j:commandLink>
                                </rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink id="selectlink" action="#{OutwardCheques.fillValuesofGridInFields}" oncomplete="#{rich:element('txtAcNo')}.disabled = true;#{rich:element('txtInstNo')}.disabled = true;#{rich:element('btnSave')}.disabled = true;#{rich:element('btnUpdate')}.focus()"
                                                     reRender="a9,a10,a8,a13,a14,message,errorMessage,lpg,a15,a16,a18,a17,a22,a23,a25,a26,gpFooter,table,taskList" >
                                        <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{OutwardCheques.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{OutwardCheques.currentRow}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="20" />
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnSave" tabindex="18" disabled="#{OutwardCheques.btnFlag}" value="Save" oncomplete="#{rich:component('savePanel')}.show()" reRender="a9,a10,a8,a13,a14,message,errorMessage,lpg,a15,a16,a18,a22,a23,a25,a26,gpFooter,table,taskList" focus=""/>
                            <a4j:commandButton id="btnUpdate" tabindex="19" disabled="#{OutwardCheques.btnUpdateFlag}" value="Update" oncomplete="#{rich:component('modifyPanel')}.show()" reRender="a9,a10,a8,a13,a14,message,errorMessage,lpg,a15,a16,a18,a17,a22,a23,a25,a26,gpFooter,table,taskList" />
                            <a4j:commandButton id="btnRefresh" tabindex="20" value="Refresh" action="#{OutwardCheques.resetForm}" oncomplete="#{rich:element('txtAcNo')}.disabled = false;#{rich:element('txtInstNo')}.disabled = false;" reRender="a9,a10,a8,a13,a14,message,errorMessage,lpg,a15,a16,a18,a17,a22,a23,a25,a26,gpFooter,table,taskList" focus="txtAcNo"/>
                            <a4j:commandButton id="btnExit" tabindex="21" value="Exit" action="#{OutwardCheques.exitForm}" reRender="message,errorMessage" />
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="deletePanel" autosized="true" width="250" onshow="#{rich:element('btnYesDel')}.focus()">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" />
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
                                    <a4j:commandButton value="Yes" id="btnYesDel" ajaxSingle="true"  action="#{OutwardCheques.delete}"
                                                       oncomplete="#{rich:component('deletePanel')}.hide();" reRender="a9,a10,a8,a13,a14,message,errorMessage,lpg,a15,a16,a18,a17,a22,a23,a25,a26,gpFooter,table,taskList" focus="txtAcNo"/>
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
                    <h:outputText value="Confirmation Dialog"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure To Save?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesSave" ajaxSingle="true" action="#{OutwardCheques.saveDetail}"
                                                       oncomplete="#{rich:component('savePanel')}.hide();" reRender="a9,a10,a8,a13,a14,message,errorMessage,lpg,a15,a16,a18,a17,a22,a23,a25,a26,gpFooter,table,taskList" focus="txtAcNo"/>
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
                                    <a4j:commandButton value="Yes" id="btnYesModify" ajaxSingle="true" action="#{OutwardCheques.updateDetail}"
                                                       oncomplete="#{rich:component('modifyPanel')}.hide();" reRender="a9,a10,a8,a13,a14,message,errorMessage,lpg,a15,a16,a18,a17,a22,a23,a25,a26,gpFooter,table,taskList" focus="txtAcNo"/>
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