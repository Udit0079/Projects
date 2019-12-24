<%-- 
    Document   : BillCommissionRegister
    Created on : Sep 3, 2010, 10:36:03 AM
    Author     : Rohit Krishna Gupta
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
            <title>Bill Commission Register</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{BillCommissionRegister.todayDate}"/>
                        </h:panelGroup>

                        <h:outputLabel styleClass="headerLabel" value="Bill Commission Register"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{BillCommissionRegister.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="errorMessage" styleClass="error" value="#{BillCommissionRegister.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{BillCommissionRegister.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="2" id="a7" width="100%">
                        <h:panelGrid  columns="1" id="gridPanel1" width="100%">
                            <h:panelGrid columnClasses="col9" columns="2" id="a8" width="100%" style="height:30px;" styleClass="row1">
                                <h:outputLabel id="lblBillType" styleClass="label" value="Bill Type :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddBillType" tabindex="1" styleClass="ddlist" value="#{BillCommissionRegister.billType}"  size="1" style="width: 102px">
                                    <f:selectItems value="#{BillCommissionRegister.billTypeList}"/>
                                    <a4j:support  event="onblur" focus="#{rich:clientId('calWithEffectFrom')}InputDate"
                                                 action="#{BillCommissionRegister.billTypeSelection}" reRender="a15,a16,a17,a18,a8" />
                                </h:selectOneListbox>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="2" id="a11" width="100%" style="height:30px;" styleClass="row2">
                                <h:outputLabel id="lblAmountFrom" styleClass="label" value="Amount From :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtAmountFrom" maxlength="9" tabindex="3" size="15" onkeyup="this.value = this.value.toUpperCase();" styleClass="input" value="#{BillCommissionRegister.amtFrom}" />
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="4" id="a13" width="100%" style="height:30px;" styleClass="row1">
                                <h:outputLabel id="lblCommCharges" styleClass="label" value="Commission Charge :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtCommCharges" maxlength="7" tabindex="5" size="15" onkeyup="this.value = this.value.toUpperCase();" styleClass="input" value="#{BillCommissionRegister.comChg}" />
                                <h:outputLabel id="lblPt" styleClass="label" value="P.T. :"><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddPt" tabindex="6" styleClass="ddlist" value="#{BillCommissionRegister.pt}" size="1" style="width: 70px">
                                    <f:selectItems value="#{BillCommissionRegister.ptList}"/>
                                </h:selectOneListbox>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="2" id="a15" width="100%" style="height:30px;" styleClass="row2" >
                                <h:outputLabel id="lblCancelCharges" styleClass="label" value="#{BillCommissionRegister.labelCancelChg}" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtCancelCharges" maxlength="7" tabindex="8"  size="15" onkeyup="this.value = this.value.toUpperCase();" styleClass="input" value="#{BillCommissionRegister.cancelChg}" disabled="#{BillCommissionRegister.cancelChargeDisabled}"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="2" id="a17" width="100%" style="height:30px;" styleClass="row1">
                                <h:outputLabel id="lblPlaceType" styleClass="label" value="Place Type :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddPlaceType" tabindex="10" styleClass="ddlist" value="#{BillCommissionRegister.placeType}" size="1" style="width: 102px" disabled="#{BillCommissionRegister.placeTypeDisabled}">
                                    <f:selectItems value="#{BillCommissionRegister.palceTypeList}"/>
                                </h:selectOneListbox>
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="2" id="a9" width="100%">
                            <h:panelGrid columns="1" id="gridPanel3" width="100%">
                                <h:panelGrid columnClasses="col9" columns="2" id="a10" width="100%" style="height:30px;" styleClass="row1">
                                    <h:outputLabel id="lblWithEffectFrom" styleClass="label" value="With Effect From :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                                    <rich:calendar datePattern="dd/MM/yyyy" tabindex="2" id="calWithEffectFrom" value="#{BillCommissionRegister.wefDate}" ondateselect="#{rich:element('txtAmountFrom')}.focus();" inputSize="10"/>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col9" columns="2" id="a12" width="100%" style="height:30px;" styleClass="row2">
                                    <h:outputLabel id="lblAmountTo" styleClass="label" value="Amount To :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                                    <h:inputText id="txtAmountTo" maxlength="9" tabindex="4"  size="15" onkeyup="this.value = this.value.toUpperCase();" styleClass="input" value="#{BillCommissionRegister.amtTo}" />
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col9" columns="2" id="a14" width="100%" style="height:30px;" styleClass="row1">
                                    <h:outputLabel id="lblMinCharges" styleClass="label" value="Minimum Charges :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                                    <h:inputText id="txtMinCharges" maxlength="7" tabindex="7" size="15" onkeyup="this.value = this.value.toUpperCase();" styleClass="input" value="#{BillCommissionRegister.minChg}" />
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col9" columns="2" id="a16" width="100%" style="height:30px;" styleClass="row2">
                                    <h:outputLabel id="lblPayBy" styleClass="label" value="Pay By :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                                    <h:selectOneListbox id="ddPayBy" tabindex="9" styleClass="ddlist" value="#{BillCommissionRegister.payBy}" size="1" style="width: 102px">
                                        <f:selectItems value="#{BillCommissionRegister.payByList}"/>
                                    </h:selectOneListbox>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col9" columns="2" id="a18" width="100%" style="height:30px;" styleClass="row1">
                                    <h:outputLabel id="lblSurCharge" styleClass="label" value="SurCharge :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                                    <h:inputText id="txtSurCharge" maxlength="7" tabindex="11" size="15" onkeyup="this.value = this.value.toUpperCase();" styleClass="input" value="#{BillCommissionRegister.surCharges}" disabled="#{BillCommissionRegister.surChargeDisabled}"/>
                                </h:panelGrid>
                            </h:panelGrid>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="a19" style="height:auto;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{BillCommissionRegister.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="7" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="15"><h:outputText value="Details" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Bill Type" /></rich:column>
                                        <rich:column><h:outputText value="Amount From" /></rich:column>
                                        <rich:column><h:outputText value="Amount To" /></rich:column>
                                        <rich:column><h:outputText value="Commission Charges" /></rich:column>
                                        <rich:column><h:outputText value="Postage Charges" /></rich:column>
                                        <rich:column><h:outputText value="Place Type" /></rich:column>
                                        <rich:column><h:outputText value="Minimum Charges" /></rich:column>
                                        <rich:column><h:outputText value="Sur Charges" /></rich:column>
                                        <rich:column><h:outputText value="Pay By" /></rich:column>
                                        <rich:column><h:outputText value="Cancel Charges" /></rich:column>
                                        <rich:column><h:outputText value="Date" /></rich:column>
                                        <rich:column width="20"><h:outputText value="Delete" /></rich:column>
                                        <rich:column width="20"><h:outputText value="Select" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.collectType}" /></rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem.amtFrom}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem.amtTo}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column><h:outputText value="#{dataItem.commCharge}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.postage}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.placeType}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.minCharge}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.surCharge}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.payBy}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.maxCharge}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.slabDate}" /></rich:column>
                                <rich:column>
                                    <a4j:commandLink ajaxSingle="true" id="deletelink" oncomplete="#{rich:component('deletePanel')}.show()" reRender="a8,a10,message,errorMessage,lpg,a16,a18,a14,a17,a11,a12">
                                        <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{BillCommissionRegister.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{BillCommissionRegister.currentRow}" />
                                    </a4j:commandLink>
                                </rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink id="selectlink" action="#{BillCommissionRegister.fillValuesofGridInFields}" reRender="message,errorMessage,lpg,a8,a16,a18,a14,a17,a11,a12,a13,a15" focus="btnSave">
                                        <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{BillCommissionRegister.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{BillCommissionRegister.currentRow}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="20" />
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnSave" tabindex="12" value="Save" oncomplete="#{rich:component('savePanel')}.show()"  reRender="message,errorMessage"/>
                            <a4j:commandButton id="btnRefresh" tabindex="13" ajaxSingle="true" value="Refresh" action="#{BillCommissionRegister.resetForm1}"
                                               focus="ddBillType" reRender="taskList,message,errorMessage,lpg,a8,a10,a16,a18,a14,a17,a11,a12,a13,a15,a18,a19">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" tabindex="14" ajaxSingle="true" value="Exit" action="#{BillCommissionRegister.exitForm}" reRender="taskList,message,errorMessage,lpg,a8,a10,a16,a18,a14,a17,a11,a12,a13,a15,a18,a19"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="savePanel" autosized="true" width="250" onshow="#{rich:element('save')}.focus()">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog"/>
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
                                    <a4j:commandButton value="Yes" id="save" ajaxSingle="true" action="#{BillCommissionRegister.saveDetail}" oncomplete="#{rich:component('savePanel')}.hide();return false;"
                                                       reRender="message,errorMessage,lpg,a8,a16,a18,a14,a17,a11,a12,a13,a15,taskList" focus="ddBillType"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" onclick="#{rich:component('savePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="deletePanel" autosized="true" width="250" onshow="#{rich:element('del')}.focus()">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure To Delete This Selected Record?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="del" ajaxSingle="true"  action="#{BillCommissionRegister.delete}"
                                                       oncomplete="#{rich:component('deletePanel')}.hide();" reRender="taskList,message,errorMessage,lpg,a8,a16,a18,a14,a17,a11,a12,a13,a15" focus="ddBillType"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
