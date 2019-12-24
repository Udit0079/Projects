<%-- 
    Document   : LoanEmiMaster
    Created on : Jan 17, 2011, 12:24:26 PM
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <title>EMI Master Register</title>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:keepAlive beanName="LoanEmiMaster"/>
            <a4j:form id="LoanEmiMaster">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{LoanEmiMaster.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="EMI Master Register"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{LoanEmiMaster.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="errorMessage" styleClass="error" value="#{LoanEmiMaster.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{LoanEmiMaster.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="2" id="a4" width="100%">
                        <h:panelGrid columnClasses="col9" columns="6" id="a3" width="100%" style="height:30px;" styleClass="row1">
                            <h:outputLabel id="lblAcNo" styleClass="label" value="Account No. :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtAcNo" style="width:90px" tabindex="1" value="#{LoanEmiMaster.oldAcctNo}" maxlength="#{LoanEmiMaster.acNoMaxLen}" styleClass="input">
                                    <a4j:support event="onblur"  action="#{LoanEmiMaster.getCustomerEMIDetail}" focus="txtSNo"
                                                 reRender="a4,a5,a7,a8,a9,a10,a11,a12,table,taskList,message,errorMessage,gpFooter" />
                                </h:inputText>
                                <h:outputLabel id="stxtAcNo" styleClass="label" value="#{LoanEmiMaster.acctNo}" style="padding-left:70px;"></h:outputLabel>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="6" id="a13" width="100%" style="height:30px;" styleClass="row1"/>
                            <h:panelGrid columnClasses="col9" columns="6" id="a5" width="100%" style="height:30px;" styleClass="row2">
                                <h:outputLabel id="lblCustName" styleClass="label" value="Customer Name :" style="padding-left:70px;"/>
                                <h:outputText id="stxtCustName" styleClass="output" value="#{LoanEmiMaster.custName}"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="6" id="a6" width="100%" style="height:30px;" styleClass="row2"/>
                            <h:panelGrid columnClasses="col9" columns="6" id="a7" width="100%" style="height:30px;" styleClass="row1">
                                <h:outputLabel id="lblSNo" styleClass="label" value="S. No. :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtSNo" style="width:60px" tabindex="2" value="#{LoanEmiMaster.sno}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="6" id="a8" width="100%" style="height:30px;" styleClass="row1">
                                <h:outputLabel id="lblInstallAmt" styleClass="label" value="Installment Amount :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtInstallAmt" style="width:90px" tabindex="3" value="#{LoanEmiMaster.installAmt}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="6" id="a9" width="100%" style="height:30px;" styleClass="row2">
                                <h:outputLabel id="lblStatus" styleClass="label" value="Status :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddStatus" tabindex="4" styleClass="ddlist" value="#{LoanEmiMaster.status}" size="1" style="width:90px">
                                    <f:selectItems value="#{LoanEmiMaster.statusList}"/>
                                </h:selectOneListbox>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="6" id="a10" width="100%" style="height:30px;" styleClass="row2">
                                <h:outputLabel id="lblDueDt" styleClass="label" value="Due Date :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                                <rich:calendar datePattern="dd/MM/yyyy" id="calDueDt" value="#{LoanEmiMaster.dueDt}" readonly="true" tabindex="5" inputSize="10"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="6" id="a11" width="100%" style="height:30px;" styleClass="row1">
                                <h:outputLabel id="lblExcessAmt" styleClass="label" value="Excess Amount :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtExcessAmt" style="width:90px" tabindex="6" value="#{LoanEmiMaster.excessAmt}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="6" id="a12" width="100%" style="height:30px;" styleClass="row1">
                                <h:outputLabel id="lblPaymentDt" styleClass="label" value="Payment Date :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                                <rich:calendar datePattern="dd/MM/yyyy" id="calPaymentDt" value="#{LoanEmiMaster.paytmentDt}" tabindex="7" ondateselect="#{rich:element('btnSave')}.focus();" inputSize="10"/>
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="vtop" columns="1" id="table" style="height:auto;" styleClass="row2" width="100%">
                            <a4j:region>
                                <rich:dataTable value="#{LoanEmiMaster.gridDetail}" var="dataItem"
                                                rowClasses="gridrow1,gridrow2" id="taskList" rows="10" columnsWidth="100" rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="15"><h:outputText value="EMI DETAILS" /></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="S. No." /></rich:column>
                                            <rich:column><h:outputText value="DUE DT" /></rich:column>
                                            <rich:column><h:outputText value="INSTALL AMT" /></rich:column>
                                            <rich:column><h:outputText value="PRINCIPAL AMT" /></rich:column>
                                            <rich:column><h:outputText value="INTEREST AMT." /></rich:column>
                                            <rich:column><h:outputText value="STATUS" /></rich:column>
                                            <rich:column><h:outputText value="PAY DATE" /></rich:column>
                                            <rich:column><h:outputText value="EXCESS AMT" /></rich:column>
                                            <rich:column width="20"><h:outputText value="SELECT" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{dataItem.sno}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.dueDt}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.installAmt}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.prinAmt}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.interestAmt}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.status}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.payDt}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.excessAmt}" /></rich:column>
                                    <rich:column style="text-align:center;width:40px">
                                        <a4j:commandLink id="selectlink" action="#{LoanEmiMaster.fillValuesofGridInFields}" oncomplete="#{rich:element('txtSNo')}.disabled = true;#{rich:element('txtInstallAmt')}.disabled = true;#{rich:element('calDueDt')}.disabled = true;"
                                                         reRender="a4,a5,a7,a8,a9,a10,a11,a12,table,taskList,message,errorMessage,gpFooter" focus="ddStatus">
                                            <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                            <f:setPropertyActionListener value="#{dataItem}" target="#{LoanEmiMaster.currentItem}"/>
                                            <f:setPropertyActionListener value="#{row}" target="#{LoanEmiMaster.currentRow}"/>
                                        </a4j:commandLink>
                                    </rich:column>
                                </rich:dataTable>
                                <rich:datascroller align="left" for="taskList" maxPages="20" />
                            </a4j:region>
                        </h:panelGrid>
                        <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup layout="block" style="width:100%;text-align:center;">
                                <a4j:commandButton id="btnSave" tabindex="8" value="Save" disabled="#{LoanEmiMaster.flag1}" oncomplete="#{rich:component('savePanel')}.show()" reRender="a4,a5,a7,a8,a9,a10,a11,a12,table,taskList,message,errorMessage,gpFooter" focus=""/>
                                <a4j:commandButton id="btnUpdatePrev" tabindex="9" value="Update Previous" oncomplete="#{rich:component('updatePanel')}.show()" reRender="a4,a5,a7,a8,a9,a10,a11,a12,table,taskList,message,errorMessage,gpFooter" focus=""/>
                                <a4j:commandButton id="btnRefresh" tabindex="10" value="Refresh" action="#{LoanEmiMaster.resetForm}" oncomplete="#{rich:element('txtSNo')}.disabled = false;#{rich:element('txtInstallAmt')}.disabled = false;#{rich:element('calDueDt')}.disabled = false;" reRender="a4,a5,a7,a8,a9,a10,a11,a12,table,taskList,message,errorMessage,gpFooter,stxtAcNo" focus="txtAcNo"/>
                                <a4j:commandButton id="btnExit" tabindex="11" value="Exit" reRender="message,errorMessage" action="#{LoanEmiMaster.exitForm}" />
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                </a4j:form>
                <rich:modalPanel id="savePanel" autosized="true" width="250" onshow="#{rich:element('btnYes1')}.focus();">
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
                                    <a4j:commandButton value="Yes" id="btnYes1" ajaxSingle="true" action="#{LoanEmiMaster.saveBtnAction}"
                                                       oncomplete="#{rich:component('savePanel')}.hide();#{rich:element('txtSNo')}.disabled = false;#{rich:element('txtInstallAmt')}.disabled = false;#{rich:element('calDueDt')}.disabled = false;" reRender="a4,a5,a7,a8,a9,a10,a11,a12,table,taskList,message,errorMessage,gpFooter" focus="btnRefresh"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo1" onclick="#{rich:component('savePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="updatePanel" autosized="true" width="250" onshow="#{rich:element('btnYes')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" />
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
                                    <a4j:commandButton value="Yes" id="btnYes" ajaxSingle="true" action="#{LoanEmiMaster.updatePrevAction}"
                                                       oncomplete="#{rich:component('updatePanel')}.hide();#{rich:element('txtSNo')}.disabled = false;#{rich:element('txtInstallAmt')}.disabled = false;#{rich:element('calDueDt')}.disabled = false;" reRender="a4,a5,a7,a8,a9,a10,a11,a12,table,taskList,message,errorMessage,gpFooter" focus="btnRefresh"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('updatePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
