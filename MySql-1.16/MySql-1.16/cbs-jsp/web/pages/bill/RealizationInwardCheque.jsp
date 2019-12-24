<%-- 
    Document   : RealizationInwardCheque
    Created on : Oct 5, 2010, 8:07:28 PM
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
            <title>Realization Inward Cheque</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{RealizationInwardCheque.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Realization Inward Cheque"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{RealizationInwardCheque.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="errorMessage" styleClass="error" value="#{RealizationInwardCheque.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{RealizationInwardCheque.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="6" id="a3" style="height:30px;" styleClass="row1" columnClasses="col3,col3,col3,col3,col3,col1" width="100%">
                        <h:outputLabel id="lblBillType" styleClass="label" value="Bill Type :" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddBillType" tabindex="1" styleClass="ddlist"  value="#{RealizationInwardCheque.billType}" size="1" style="width: 102px">
                            <f:selectItems value="#{RealizationInwardCheque.billTypeList}" />
                        </h:selectOneListbox>
                        <h:outputLabel id="lblBillNo" styleClass="label" value="Bill No. :" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtBillNo" tabindex="2" size="15" value="#{RealizationInwardCheque.billNo}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                        <h:outputLabel id="lblYear" styleClass="label" value="Year :" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddYear" tabindex="3" styleClass="ddlist"  value="#{RealizationInwardCheque.year}" size="1" style="width: 102px">
                            <f:selectItems value="#{RealizationInwardCheque.yearList}" />
                            <a4j:support action="#{RealizationInwardCheque.yearComboLostFocus}" event="onblur"
                                         reRender="message,errorMessage,a3,a4,a5,a6,a7,a8,a9,table,taskList" limitToList="true" focus="txtOurChg" />
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columns="6" id="a4" style="height:30px;" styleClass="row2" columnClasses="col3,col3,col3,col3,col3,col1" width="100%">
                        <h:outputLabel id="lblInstAmt" styleClass="label" value="Inst. Amount :" style=""/>
                        <h:outputText id="stxtInstAmt" styleClass="output" value="#{RealizationInwardCheque.instAmt}"/>
                        <h:outputLabel id="lblInstNo" styleClass="label" value="Inst. No. :" style=""/>
                        <h:outputText id="stxtInstNo" styleClass="output" value="#{RealizationInwardCheque.instNo}"/>
                        <h:outputLabel id="lblInstDate" styleClass="label" value="Inst. Date :" style=""/>
                        <h:outputText id="stxtInstDate" styleClass="output" value="#{RealizationInwardCheque.instDate}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="6" id="a5" style="height:30px;" styleClass="row1" columnClasses="col3,col3,col3,col3,col3,col1" width="100%">
                        <h:outputLabel id="lblAcNo" styleClass="label" value="Account No. :" style=""/>
                        <h:outputText id="stxtAcNo" styleClass="output" value="#{RealizationInwardCheque.actNo}"/>
                        <h:outputLabel id="lblCustName" styleClass="label" value="Name :" style=""/>
                        <h:outputText id="stxtCustName" styleClass="output" value="#{RealizationInwardCheque.custName}"/>
                        <h:outputLabel id="lblhide" styleClass="label" />
                        <h:outputText id="stxthide" styleClass="output" />
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" style="height:30px;" styleClass="row2" columns="6" id="a6" width="100%">
                        <h:outputLabel id="lblCommision" styleClass="label" value="Other Bank Commision :" style=""/>
                        <h:outputText id="stxtCommision" styleClass="output" value="#{RealizationInwardCheque.comm}"/>
                        <h:outputLabel id="lblPostage" styleClass="label" value="Other Bank Postage Amount :" style=""/>
                        <h:outputText id="stxtPostage" styleClass="output" value="#{RealizationInwardCheque.postage}"/>
                        <h:outputLabel id="lblhide1" styleClass="label" />
                        <h:outputText id="stxthide1" styleClass="output" />
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" style="height:30px;" styleClass="row1" columns="6" id="a7" width="100%" >
                        <h:outputLabel id="lblOurChg" styleClass="label" value="Our Charges :" style="" rendered="#{RealizationInwardCheque.dishonorFlag==false}"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtOurChg" tabindex="4" size="15" value="#{RealizationInwardCheque.ourChg}" rendered="#{RealizationInwardCheque.dishonorFlag==false}" onkeyup="this.value = this.value.toUpperCase();"  styleClass="input">
                            <a4j:support action="#{RealizationInwardCheque.setAmtDebited}" event="onblur" oncomplete="if(#{RealizationInwardCheque.dishonorFlag=='false'}){#{rich:element('btnPass')}.focus();}else{#{rich:element('btnDishonored')}.focus();}"
                                         reRender="message,errorMessage,txtOtherBankChg" limitToList="true" focus="btnPass" />
                        </h:inputText>
                        <h:outputLabel id="lblAmtToBeDebited" styleClass="label" value="Amount To be Debited :" style="" rendered="#{RealizationInwardCheque.dishonorFlag==false}"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtOtherBankChg" tabindex="5" size="15" value="#{RealizationInwardCheque.amtDebited}" disabled="#{RealizationInwardCheque.amFlag}" rendered="#{RealizationInwardCheque.dishonorFlag==false}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input" />
                        <h:outputLabel id="lblhide2" styleClass="label" />
                        <h:outputText id="stxthide2" styleClass="output" />
                    </h:panelGrid>

                    <h:panelGrid columns="6" id="a8" style="height:30px;" styleClass="row2" columnClasses="col3,col3,col3,col3,col3,col1" width="100%">
                        <h:outputLabel id="lblAcNotoBeCredited" styleClass="label" value="Account No. To Be Credited :"/>
                        <h:outputText id="stxtAcNotoBeCredited" styleClass="output" value="#{RealizationInwardCheque.acNoCredit}"/>
                        <h:outputText id="stxtName" styleClass="output" value="#{RealizationInwardCheque.acNoCreditName}"/>
                        <h:outputLabel id="lblhid4" styleClass="label"/>
                        <h:outputLabel id="lblhid3" styleClass="label" />
                        <h:outputText id="stxthid3" styleClass="output" />
                    </h:panelGrid>
                    <h:panelGrid columns="6" id="a9" style="height:30px;" styleClass="row1" columnClasses="col3,col3,col3,col3,col3,col1" width="100%" >
                        <h:outputLabel id="lblReturnCharges" styleClass="label" value="Return Charges :" style="" rendered="#{RealizationInwardCheque.dishonorFlag==true}"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtReturnCharges" tabindex="6" size="15" value="#{RealizationInwardCheque.returnChg}" rendered="#{RealizationInwardCheque.dishonorFlag==true}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                        <h:outputLabel id="lblReasonOfCan" styleClass="label" value="Reason For Cancel :" style="" rendered="#{RealizationInwardCheque.dishonorFlag==true}"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddReasonOfCan" tabindex="7" styleClass="ddlist"  value="#{RealizationInwardCheque.reason}" size="1" style="width: 102px" rendered="#{RealizationInwardCheque.dishonorFlag==true}">
                            <f:selectItems value="#{RealizationInwardCheque.reasonList}" />
                        </h:selectOneListbox>
                        <h:outputLabel id="lblhide5" styleClass="label" />
                        <h:outputText id="stxthide5" styleClass="output" />
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="table" style="height:auto;" styleClass="row2" width="100%">

                        <a4j:region>
                            <rich:dataTable value="#{RealizationInwardCheque.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="15"><h:outputText value="List Of IBC's To Be Realized" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="S. No." /></rich:column>
                                        <rich:column><h:outputText value="Bill No." /></rich:column>
                                        <rich:column><h:outputText value="FYear." /></rich:column>
                                        <rich:column><h:outputText value="Account No." /></rich:column>
                                        <rich:column><h:outputText value="Bill Type" /></rich:column>
                                        <rich:column><h:outputText value="Inst. No." /></rich:column>
                                        <rich:column><h:outputText value="Inst. Amount" /></rich:column>
                                        <rich:column><h:outputText value="Inst. Date" /></rich:column>
                                        <rich:column><h:outputText value="Bank Name" /></rich:column>
                                        <rich:column><h:outputText value="Bank Address" /></rich:column>
                                        <rich:column><h:outputText value="Enter By" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.sNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.billNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.fYear}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.acNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.billType}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.instNo}" /></rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem.instAmt}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column><h:outputText value="#{dataItem.instDate}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.bankName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.bankAdd}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.enterBy}" /></rich:column>

                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="20" />
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnPass" tabindex="8"  value="Pass" disabled="#{RealizationInwardCheque.dishonorFlag}" oncomplete="#{rich:component('passPanel')}.show()" reRender="message,errorMessage,gpFooter,a3,a4,a5,a6,a7,a8,a9,table,taskList" />
                            <a4j:commandButton id="btnDishonored" tabindex="9"  value="Dishonored" oncomplete="#{rich:component('dishonoredPanel')}.show()" rendered="#{RealizationInwardCheque.dishonorFlag==false}" reRender="message,errorMessage,gpFooter,a3,a4,a5,a6,a7,a8,a9,table,taskList" />
                            <a4j:commandButton id="btnDishonor" tabindex="10"  value="Dishonore" oncomplete="#{rich:component('dishonorPanel')}.show()" rendered="#{RealizationInwardCheque.dishonorFlag==true}" reRender="message,errorMessage,gpFooter,a3,a4,a5,a6,a7,a8,a9,table,taskList" />
                            <a4j:commandButton id="btnRefresh" tabindex="11" value="Refresh" action="#{RealizationInwardCheque.resetForm}"  reRender="message,errorMessage,gpFooter,a3,a4,a5,a6,a7,a8,a9,table,taskList" focus="ddBillType"/>
                            <a4j:commandButton id="btnExit" tabindex="12" value="Exit" action="#{RealizationInwardCheque.exitForm}" reRender="message,errorMessage" />
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
               
            </a4j:form>
            <rich:modalPanel id="dishonoredPanel" autosized="true" width="250" onshow="#{rich:element('btnYesDish')}.focus();">
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
                                    <a4j:commandButton value="Yes" id="btnYesDish" ajaxSingle="true" action="#{RealizationInwardCheque.dishonorPanel}"
                                                       oncomplete="#{rich:component('dishonoredPanel')}.hide();" reRender="message,errorMessage,a3,a4,a5,a6,a7,a8,a9,table,taskList,gpFooter" focus="txtReturnCharges"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNoDish" onclick="#{rich:component('dishonoredPanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="dishonorPanel" autosized="true" width="250" onshow="#{rich:element('btnYesDis')}.focus();">
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
                                    <a4j:commandButton value="Yes" id="btnYesDis" ajaxSingle="true" action="#{RealizationInwardCheque.dishonoredRecord}"
                                                       oncomplete="#{rich:component('dishonorPanel')}.hide();" reRender="message,errorMessage,gpFooter,a3,a4,a5,a6,a7,a8,a9,table,taskList,gpFooter" focus="btnRefresh"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNoDis" onclick="#{rich:component('dishonorPanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="passPanel" autosized="true" width="250" onshow="#{rich:element('btnYesPas')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Do You Want To Pass This Record ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesPas" ajaxSingle="true" action="#{RealizationInwardCheque.chkInstNo}"
                                                       oncomplete="#{rich:component('passPanel')}.hide();if(#{RealizationInwardCheque.instFlag==false}){#{rich:component('passPanel1')}.show();}"
                                                       reRender="message,errorMessage,a3,a4,a5,a6,a7,a8,a9,table,taskList,gpFooter"
                                                       focus="btnRefresh"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNoPass" onclick="#{rich:component('passPanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="passPanel1" autosized="true" width="300" onshow="#{rich:element('btnYesPass1')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Instrument is Not Issued , Press Yes for Pass And Press No for Cancel."/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesPass1" ajaxSingle="true" action="#{RealizationInwardCheque.passRecord}"
                                                       oncomplete="#{rich:component('passPanel1')}.hide();" reRender="message,errorMessage,a3,a4,a5,a6,a7,a8,a9,table,taskList,gpFooter" focus="btnRefresh"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNoPass1" onclick="#{rich:component('passPanel1')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
