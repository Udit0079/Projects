<%-- 
    Document   : RealizationOutwardBill
    Created on : Oct 1, 2010, 12:47:09 PM
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
            <title>Realization Outward Bill</title>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{RealizationOutwardBill.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Realization Outward Bill"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{RealizationOutwardBill.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="errorMessage" styleClass="error" value="#{RealizationOutwardBill.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{RealizationOutwardBill.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="6" id="a3" style="height:30px;" styleClass="row1" columnClasses="col3,col3,col3,col3,col3,col1" width="100%">
                        <h:outputLabel id="lblBillType" styleClass="label" value="Bill Type :" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddBillType" tabindex="1" styleClass="ddlist"  value="#{RealizationOutwardBill.billType}" size="1" style="width: 102px">
                            <f:selectItems value="#{RealizationOutwardBill.billTypeList}" />
                        </h:selectOneListbox>
                        <h:outputLabel id="lblBillNo" styleClass="label" value="Bill No. :" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtBillNo" maxlength="9" tabindex="2" size="15" value="#{RealizationOutwardBill.billNo}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                        <h:outputLabel id="lblYear" styleClass="label" value="Year :" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddYear" tabindex="3" styleClass="ddlist"  value="#{RealizationOutwardBill.year}" size="1" style="width: 102px">
                            <f:selectItems value="#{RealizationOutwardBill.yearList}" />
                            <a4j:support action="#{RealizationOutwardBill.yearComboLostFocus}" event="onblur"
                                         reRender="message,errorMessage,a3,a4,a5,a6,a7,a8,table,taskList" limitToList="true" focus="txtOtherChgAc" />
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columns="6" id="a4" style="height:30px;" styleClass="row2" columnClasses="col3,col3,col3,col3,col3,col1" width="100%">
                        <h:outputLabel id="lblInstAmt" styleClass="label" value="Bill Amount :" style=""/>
                        <h:outputText id="stxtInstAmt" styleClass="output" value="#{RealizationOutwardBill.instAmt}">
                            <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                        </h:outputText>
                        <h:outputLabel id="lblInstNo" styleClass="label" value="LR/RR/PP/AW No. :" style=""/>
                        <h:outputText id="stxtInstNo" styleClass="output" value="#{RealizationOutwardBill.instNo}"/>
                        <h:outputLabel id="lblInstDate" styleClass="label" value="LR/RR/PP/AW DT :" style=""/>
                        <h:outputText id="stxtInstDate" styleClass="output" value="#{RealizationOutwardBill.instDate}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="6" id="a5" style="height:30px;" styleClass="row1" columnClasses="col3,col3,col3,col3,col3,col1" width="100%">
                        <h:outputLabel id="lblAcNo" styleClass="label" value="Account No. :" style=""/>
                        <h:outputText id="stxtAcNo" styleClass="output" value="#{RealizationOutwardBill.actNo}"/>
                        <h:outputLabel id="lblName" styleClass="label" value="Name :" style=""/>
                        <h:outputText id="stxtName" styleClass="output" value="#{RealizationOutwardBill.custName}"/>
                        <h:outputLabel id="lblhide" styleClass="label" />
                        <h:outputText id="stxthide" styleClass="output" />
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" style="height:30px;" styleClass="row2" columns="6" id="a6" width="100%">
                        <h:outputLabel id="lblCommision" styleClass="label" value="Commision :" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtCommision" maxlength="9" tabindex="4" disabled="#{RealizationOutwardBill.comFlag}" size="15" value="#{RealizationOutwardBill.comm}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input">
                            <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                         </h:inputText>
                        <h:outputLabel id="lblPostage" styleClass="label" value="Postage Amount :" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtPostage" maxlength="9" tabindex="5" disabled="#{RealizationOutwardBill.comFlag}" size="15" value="#{RealizationOutwardBill.postage}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input">
                            <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                         </h:inputText>
                        <h:outputLabel id="lblDtOfRelization" styleClass="label" value="Date Of Realization :" ><font class="required" color="red">*</font></h:outputLabel>
                        <rich:calendar datePattern="dd/MM/yyyy" id="calDtOfRelization" value="#{RealizationOutwardBill.dtOfRel}" tabindex="6" />
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" style="height:30px;" styleClass="row1" columns="6" id="a7" width="100%" >
                        <h:outputLabel id="lblOtherChgAc" styleClass="label" value="Other Charges Debited To Account:" rendered="#{RealizationOutwardBill.dishonorFlag==false}"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtOtherChgAc" maxlength="9" tabindex="7" size="15" value="#{RealizationOutwardBill.otherChgAct}" rendered="#{RealizationOutwardBill.dishonorFlag==false}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input">
                            <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                        </h:inputText>
                        <h:outputLabel id="lblOtherBankChg" styleClass="label" value="Other bank Charges :" style="" rendered="#{RealizationOutwardBill.dishonorFlag==false}"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtOtherBankChg" maxlength="9" tabindex="8" size="15" value="#{RealizationOutwardBill.otherBankChg}" rendered="#{RealizationOutwardBill.dishonorFlag==false}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input" >
                            <a4j:support action="#{RealizationOutwardBill.setRealInstAmount}" event="onblur"
                                         reRender="message,errorMessage,txtRelInstAmt" limitToList="true" focus="txtRelInstAmt" />
                        </h:inputText>
                        <h:outputLabel id="lblRelInstAmt" styleClass="label" value="Realized Inst. Amount :" style="" rendered="#{RealizationOutwardBill.dishonorFlag==false}"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtRelInstAmt" maxlength="9" tabindex="9" size="15" value="#{RealizationOutwardBill.relInstAmt}" rendered="#{RealizationOutwardBill.dishonorFlag==false}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input">
                            <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid columns="6" id="a8" style="height:30px;" styleClass="row2" columnClasses="col3,col3,col3,col3,col3,col1" width="100%" >
                        <h:outputLabel id="lblReturnCharges" styleClass="label" value="Return Charges :" style="" rendered="#{RealizationOutwardBill.dishonorFlag==true}"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtReturnCharges" maxlength="9" tabindex="10" size="15" value="#{RealizationOutwardBill.returnChg}" rendered="#{RealizationOutwardBill.dishonorFlag==true}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                        <h:outputLabel id="lblReasonOfCan" styleClass="label" value="Reason For Cancel :" style="" rendered="#{RealizationOutwardBill.dishonorFlag==true}"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddReasonOfCan" tabindex="11" styleClass="ddlist"  value="#{RealizationOutwardBill.reason}" size="1" style="width: 102px" rendered="#{RealizationOutwardBill.dishonorFlag==true}">
                            <f:selectItems value="#{RealizationOutwardBill.reasonList}" />
                        </h:selectOneListbox>
                        <h:outputLabel id="lblhide1" styleClass="label" />
                        <h:outputText id="stxthide1" styleClass="output" />
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="table" style="height:auto;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{RealizationOutwardBill.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="15"><h:outputText value="List Of OBC's To Be Realized" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="S. No." /></rich:column>
                                        <rich:column><h:outputText value="Bill No." /></rich:column>
                                        <rich:column><h:outputText value="Bill Type" /></rich:column>
                                        <rich:column><h:outputText value="FYear." /></rich:column>
                                        <rich:column><h:outputText value="Account No." /></rich:column>
                                        <rich:column><h:outputText value="Inst. No." /></rich:column>
                                        <rich:column><h:outputText value="Inst. Amount" /></rich:column>
                                        <rich:column><h:outputText value="Inst. Date" /></rich:column>
                                        <rich:column><h:outputText value="Collecting Bank Name" /></rich:column>
                                        <rich:column><h:outputText value="Collecting Bank Address" /></rich:column>
                                        <rich:column><h:outputText value="Enter By" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.sNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.billNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.billType}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.fYear}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.acNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.instNo}" /></rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem.instAmt}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column><h:outputText value="#{dataItem.instDate}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.colBankName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.colBankAdd}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.enterBy}" /></rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="20" />
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnRealized" tabindex="12"  value="Realized" disabled="#{RealizationOutwardBill.dishonorFlag}" oncomplete="if(#{RealizationOutwardBill.realizationFlag==true}){#{rich:component('realizePanel')}.show();} else if(#{RealizationOutwardBill.realizationFlag==false}){#{rich:component('realizePanel1')}.show();}" reRender="message,errorMessage,gpFooter,a3,a4,a5,a6,a7,a8,table,taskList" />
                            <a4j:commandButton id="btnDishonored" tabindex="13"  value="Dishonored" oncomplete="#{rich:component('dishonoredPanel')}.show()" rendered="#{RealizationOutwardBill.dishonorFlag==false}" reRender="message,errorMessage,gpFooter,a3,a4,a5,a6,a7,a8,table,taskList" />
                            <a4j:commandButton id="btnDishonor" tabindex="14"  value="Dishonore" oncomplete="#{rich:component('dishonorPanel')}.show()" rendered="#{RealizationOutwardBill.dishonorFlag==true}" reRender="message,errorMessage,gpFooter,a3,a4,a5,a6,a7,a8,table,taskList" />
                            <a4j:commandButton id="btnRefresh" tabindex="15" value="Refresh" action="#{RealizationOutwardBill.resetForm}"  reRender="message,errorMessage,gpFooter,a3,a4,a5,a6,a7,a8,table,taskList" focus="ddBillType"/>
                            <a4j:commandButton id="btnExit" tabindex="16" value="Exit" action="#{RealizationOutwardBill.exitForm}" reRender="message,errorMessage" />
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
               </a4j:form>
            <rich:modalPanel id="dishonoredPanel" autosized="true" width="250" onshow="#{rich:element('btnYesDis')}.focus()">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Do You Want To Dishonor This Record ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesDis" ajaxSingle="true" action="#{RealizationOutwardBill.dishonorPanel}"
                                                       oncomplete="#{rich:component('dishonoredPanel')}.hide();" reRender="message,errorMessage,a3,a4,a5,a6,a7,a8,table,taskList,gpFooter" focus="txtReturnCharges"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNoDis" onclick="#{rich:component('dishonoredPanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="dishonorPanel" autosized="true" width="250" onshow="#{rich:element('btnYesDis1')}.focus()">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Do You Want To Dishonor This Record ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesDis1" ajaxSingle="true" action="#{RealizationOutwardBill.dishonoredRecord}"
                                                       oncomplete="#{rich:component('dishonorPanel')}.hide();" reRender="message,errorMessage,gpFooter,a3,a4,a5,a6,a7,a8,table,taskList,gpFooter" focus="ddBillType"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNoDis1" onclick="#{rich:component('dishonorPanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="realizePanel" autosized="true" width="250" onshow="#{rich:element('btnYesRel')}.focus()">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Do You Want To Pass This Entry ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesRel" ajaxSingle="true" action="#{RealizationOutwardBill.realizeRecord}"
                                                       oncomplete="#{rich:component('realizePanel')}.hide();" reRender="message,errorMessage,gpFooter,a3,a4,a5,a6,a7,a8,table,taskList" focus="ddBillType"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNoRel" onclick="#{rich:component('realizePanel')}.hide();return false;"  />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="realizePanel1" autosized="true" width="250" onshow="#{rich:element('btnYesRel1')}.focus()">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Do You Want To Deduct Extra Charges ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesRel1" ajaxSingle="true" oncomplete="#{rich:component('realizePanel1')}.hide();" reRender="message,errorMessage,gpFooter,a3,a4,a5,a6,a7,a8,table,taskList" focus="txtOtherChgAc"/>

                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNoRel1" oncomplete="#{rich:component('realizePanel')}.show();#{rich:component('realizePanel1')}.hide();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
