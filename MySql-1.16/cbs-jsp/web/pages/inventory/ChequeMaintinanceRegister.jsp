<%-- 
    Document   : ChequeMaintinanceRegister
    Created on : Oct 30, 2010, 1:18:15 PM
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
            <title>Cheque Maintenance Register</title>
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
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{ChequeMaintinanceRegister.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Cheque Maintinance Register"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{ChequeMaintinanceRegister.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7;border:1px ridge #BED6F8;">
                        <h:outputText id="errorMessage" styleClass="error" value="#{ChequeMaintinanceRegister.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{ChequeMaintinanceRegister.message}"/>
                    </h:panelGrid>    
                    <h:panelGrid columnClasses="col9" columns="2" id="a4" width="100%">
                        <h:panelGrid  columns="1" id="gridPanel1" width="100%">
                            <h:panelGrid columns="6" id="a5" style="height:30px;border:1px ridge #BED6F8;" styleClass="row1" columnClasses="col9" width="100%">
                                <h:outputLabel id="lblAcctNo" styleClass="label" value="Account No. :" style=""><font class="required" color="red">*</font></h:outputLabel>
                                <h:panelGroup layout="block">
                                    <h:inputText id="txtAcNo" value="#{ChequeMaintinanceRegister.oldAccNo}" maxlength="#{ChequeMaintinanceRegister.acNoMaxLen}" styleClass="input" tabindex="1" style="width:90px">
                                        <a4j:support actionListener="#{ChequeMaintinanceRegister.getAccountDetail}" event="onblur" oncomplete="
                                                     if(#{ChequeMaintinanceRegister.errorMessage=='Please Enter the Account No.'}){#{rich:element('txtAcNo')}.focus();}
                                                     else if(#{ChequeMaintinanceRegister.errorMessage=='This Account Has Been Closed'}){#{rich:element('txtAcNo')}.focus();}
                                                     else if(#{ChequeMaintinanceRegister.errorMessage=='This Account No Does Not Exist'}){#{rich:element('txtAcNo')}.focus();}
                                                     else{#{rich:element('broadCastOption')}.focus();}"
                                                     reRender="message,errorMessage,a5,a6,a7,a8,a9,a10,a12,a13,a14,a16,a17,a18,a19,taskList,taskList1,table,table1" limitToList="true"  />
                                    </h:inputText>
                                    <h:outputText id="stxtAccNo" styleClass="output" value="#{ChequeMaintinanceRegister.acctNo}"/>
                                </h:panelGroup>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="6" id="a6" width="100%" style="height:30px;border:1px ridge #BED6F8;" styleClass="row2">
                                <h:outputLabel id="lblCustName" styleClass="label" value="Customer Name :" style=""/>
                                <h:outputText id="stxtCustName" styleClass="output" value="#{ChequeMaintinanceRegister.custName}"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="6" id="a7" width="100%" style="height:30px;border:1px ridge #BED6F8;" styleClass="row1">
                                <h:outputLabel id="lblJtName1" styleClass="label" value="Joint Name 1 :" style=""/>
                                <h:outputText id="stxtJtName1" styleClass="output" value="#{ChequeMaintinanceRegister.jtName1}"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="6" id="a8" width="100%" style="height:30px;border:1px ridge #BED6F8;" styleClass="row2">
                                <h:outputLabel id="lblJtName2" styleClass="label" value="Joint Name 2 :" style=""/>
                                <h:outputText id="stxtJtName2" styleClass="output" value="#{ChequeMaintinanceRegister.jtName2}"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="6" id="a9" width="100%" style="height:30px;border:1px ridge #BED6F8;" styleClass="row1">
                                <h:outputLabel id="lblOprMode" styleClass="label" value="Operation Mode :" style=""/>
                                <h:outputText id="stxtOprMode" styleClass="output" value="#{ChequeMaintinanceRegister.oprMode}"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="6" id="a10" width="100%" style="height:30px;border:1px ridge #BED6F8;" styleClass="row2">
                                <h:outputLabel id="lblBalance" styleClass="label" value="Balance :" style=""/>
                                <h:outputText id="stxtBalance" styleClass="output" value="#{ChequeMaintinanceRegister.balance}"/>
                            </h:panelGrid>
                            <h:panelGrid columns="6" id="a12" style="height:30px;border:1px ridge #BED6F8;" styleClass="row1" columnClasses="col3,col3,col3,col3,col3,col1" width="100%">
                                <h:selectOneRadio  id="broadCastOption" tabindex="2" valueChangeListener="#{ChequeMaintinanceRegister.selectRadioValue}" style="padding-left:50px" styleClass="output">
                                    <a4j:support event="onclick" ajaxSingle="true"
                                                 reRender="broadCastOption,a13,a14" oncomplete="if(#{ChequeMaintinanceRegister.rdoButtonFlag=='true'}){#{rich:element('txtChqNo')}.focus();}else{#{rich:element('txtChqNo')}.focus();}"
                                                 data="#{ChequeMaintinanceRegister.rdoButtonFlag}"/>
                                    <f:selectItem id="yesoption" itemValue="true" itemLabel="Single"/>
                                    <f:selectItem id="Nooption" itemValue="false" itemLabel="Series"/>
                                </h:selectOneRadio>
                                <%--h:outputLabel id="lblDebtCharges" styleClass="label" value="Debit Charges :" style="padding-left:50px"><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddDebtCharges" tabindex="3" styleClass="ddlist"  value="#{ChequeMaintinanceRegister.debitChg}" size="1" style="width: 80px">  
                                    <f:selectItems value="#{ChequeMaintinanceRegister.debitChgList}" />
                                </h:selectOneListbox--%>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="6" id="a13" width="100%" style="height:30px;border:1px ridge #BED6F8;" styleClass="row2">
                                <h:outputLabel id="lblChqNo" styleClass="label" value="Cheque No :" style=""><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtChqNo" tabindex="4" size="20" value="#{ChequeMaintinanceRegister.chqNoFrom}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input" style="width:90px"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="6" id="a14" width="100%" style="height:30px;border:1px ridge #BED6F8;" styleClass="row1">
                                <h:outputLabel id="lblChqNoTo" styleClass="label" value="Cheque No To :" style="" rendered="#{ChequeMaintinanceRegister.rdoButtonFlag==false}"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtChqNoTo" tabindex="5" size="20" value="#{ChequeMaintinanceRegister.chqNoTo}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input" rendered="#{ChequeMaintinanceRegister.rdoButtonFlag==false}" style="width:90px"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="6" id="a16" width="100%" style="height:30px;border:1px ridge #BED6F8;" styleClass="row2">
                                <h:outputLabel id="lblChqDate" styleClass="label" value="Cheque Date :" style="" ><font class="required" color="red">*</font></h:outputLabel>
                            <rich:calendar datePattern="dd/MM/yyyy" id="calChqDate" value="#{ChequeMaintinanceRegister.chqDate}" tabindex="6" inputSize="10"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="6" id="a17" width="100%" style="height:30px;border:1px ridge #BED6F8;" styleClass="row1">
                                <h:outputLabel id="lblAmount" styleClass="label" value="Amount :" style=""/>
                                <h:inputText id="txtAmount" tabindex="7" size="20" value="#{ChequeMaintinanceRegister.amount}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input" style="width:90px"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="6" id="a18" width="100%" style="height:30px;border:1px ridge #BED6F8;" styleClass="row2">
                                <h:outputLabel id="lblFavouring" styleClass="label" value="Favouring :" style=""/>
                                <h:inputText id="txtFavouring" tabindex="8" value="#{ChequeMaintinanceRegister.favouring}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input" style="width:150px"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="6" id="a19" width="100%" style="height:30px;border:1px ridge #BED6F8;" styleClass="row2">
                                <h:outputLabel id="lblStatus" styleClass="label" value="Status :" style=""><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddStatus" tabindex="9" styleClass="ddlist"  value="#{ChequeMaintinanceRegister.status}" size="1" style="width: 125px">
                                    <f:selectItems value="#{ChequeMaintinanceRegister.statusList}" />
                                </h:selectOneListbox>
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="1" id="a11" width="100%">
                            <%--<h:panelGrid columns="1" id="gridPanel3" width="100%">--%>
                            <h:panelGrid columnClasses="vtop" columns="1" id="table" style="height:221px;border:1px ridge #BED6F8;" styleClass="row1" width="100%">
                                <a4j:region>
                                    <rich:dataTable value="#{ChequeMaintinanceRegister.unusedInstGridDetail}" var="dataItem"
                                                    rowClasses="gridrow1,gridrow2" id="taskList" rows="6" columnsWidth="100" rowKeyVar="row"
                                                    onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                    onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                        <f:facet name="header">
                                            <rich:columnGroup>
                                                <rich:column colspan="15"><h:outputText value="Unused Instruments" /></rich:column>
                                                <rich:column breakBefore="true" width="50%"><h:outputText value="Cheque No." /></rich:column>
                                                <rich:column width="50%"><h:outputText value="Issue Date" /></rich:column>
                                            </rich:columnGroup>
                                        </f:facet>
                                        <rich:column><h:outputText value="#{dataItem.chqNoUnused}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.issueDtUnused}" /></rich:column>
                                    </rich:dataTable>
                                    <rich:datascroller align="left" for="taskList" maxPages="10" />
                                </a4j:region>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="vtop" columns="1" id="table1" style="height:215px;border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                                <a4j:region>
                                    <rich:dataTable value="#{ChequeMaintinanceRegister.stopInstGridDetail}" var="dataItem"
                                                    rowClasses="gridrow1,gridrow2" id="taskList1" rows="6" columnsWidth="100" rowKeyVar="row"
                                                    onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                    onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                        <f:facet name="header">
                                            <rich:columnGroup>
                                                <rich:column colspan="15"><h:outputText value="Stop Payment Marked Instruments" /></rich:column>
                                                <rich:column breakBefore="true" width="33%"><h:outputText value="Cheque No." /></rich:column>
                                                <rich:column width="33%"><h:outputText value="Issue Date" /></rich:column>
                                                <rich:column width="33%"><h:outputText value="Enter By" /></rich:column>
                                                <rich:column width="20"><h:outputText value="Select" /></rich:column>
                                            </rich:columnGroup>
                                        </f:facet>
                                        <rich:column><h:outputText value="#{dataItem.chqNoStop}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.issueDtStop}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.enterBy}" /></rich:column>
                                        <rich:column style="text-align:center;width:40px">
                                            <a4j:commandLink id="selectlink" action="#{ChequeMaintinanceRegister.fillValuesofGridInFields}" oncomplete="#{rich:element('txtAcNo')}.disabled = true;#{rich:element('btnSave')}.disabled = true"
                                                             reRender="message,errorMessage,a12,a13,a14,a16,a19,gpFooter,taskList,taskList1,table,table1" focus="txtChqNo">
                                                <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                                <f:setPropertyActionListener value="#{dataItem}" target="#{ChequeMaintinanceRegister.currentItem}"/>
                                                <f:setPropertyActionListener value="#{row}" target="#{ChequeMaintinanceRegister.currentRow}"/>
                                            </a4j:commandLink>
                                        </rich:column>
                                    </rich:dataTable>
                                    <rich:datascroller align="left" for="taskList1" maxPages="10" />
                                </a4j:region>
                            </h:panelGrid>
                            <%--</h:panelGrid>--%>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnSave" tabindex="10" value="Save" oncomplete="#{rich:component('savePanel')}.show()" reRender="message,errorMessage,a5,a6,a7,a8,a9,a10,a12,a13,a14,a16,a17,a18,a19,taskList,taskList1,table,table1,gpFooter" focus="btnSavePanel" rendered="#{ChequeMaintinanceRegister.suFlag=='S'}"/>
                            <a4j:commandButton id="btnUpdate" tabindex="11" value="Update" oncomplete="#{rich:component('modifyPanel')}.show()" reRender="message,errorMessage,a5,a6,a7,a8,a9,a10,a12,a13,a14,a16,a17,a18,a19,taskList,taskList1,table,table1,gpFooter" rendered="#{ChequeMaintinanceRegister.suFlag=='U'}" focus="btnUpdatePanel"/>
                            <a4j:commandButton id="btnRefresh" tabindex="12" value="Refresh" actionListener="#{ChequeMaintinanceRegister.resetForm}" reRender="message,errorMessage,a5,a6,a7,a8,a9,a10,a12,a13,a14,a16,a17,a18,a19,taskList,taskList1,table,table1,gpFooter"/>
                            <a4j:commandButton id="btnExit" tabindex="13" value="Exit" action="#{ChequeMaintinanceRegister.exitBtnAction}" reRender="message,errorMessage,stxtAccNo" />
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
                <rich:messages></rich:messages>
            </a4j:form>
            <rich:modalPanel id="savePanel" autosized="true" width="250" onshow="#{rich:element('btnYesSavePanel')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
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
                                    <a4j:commandButton value="Yes" id="btnYesSavePanel" ajaxSingle="true" action="#{ChequeMaintinanceRegister.saveDetail}"
                                                       oncomplete="#{rich:component('savePanel')}.hide();" reRender="message,errorMessage,a5,a6,a7,a8,a9,a10,a12,a13,a14,a16,a17,a18,a19,taskList,taskList1,table,table1,gpFooter" />
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNoSavePanel" onclick="#{rich:component('savePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="modifyPanel" autosized="true" width="250" onshow="#{rich:element('btnYesUpdatePanel')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure To Update ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesUpdatePanel" ajaxSingle="true" action="#{ChequeMaintinanceRegister.saveDetail}"
                                                       oncomplete="#{rich:component('modifyPanel')}.hide();" reRender="message,errorMessage,a5,a6,a7,a8,a9,a10,a12,a13,a14,a16,a17,a18,a19,taskList,taskList1,table,table1,gpFooter"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNoUpdatePanel" onclick="#{rich:component('modifyPanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
