<%--
    Document   : term deposit slab
    Created on : Sep 6, 2010, 12:09:02 PM
    Author     : stellar
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Term Deposit Interest Rate Slab</title>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{TermDepositSlab.todayDate}" />
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Term Deposit Slab"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label1" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{TermDepositSlab.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid id="error" columns="1" columnClasses="row2" width="100%" style="text-align:center">
                        <h:outputText id="errorMsg" styleClass="error" value="#{TermDepositSlab.errorMsg}"/>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col3,col2,col3,col3,col3,col3" columns="6" id="gridPanel1" style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel id="lblacNature" styleClass="label" value="Nature"/>
                        <h:selectOneListbox id="ddFunction" value="#{TermDepositSlab.nature}" styleClass="ddlist" size="1" style="width:90px">
                            <f:selectItems value="#{TermDepositSlab.natureList}"/>
                            <a4j:support actionListener="#{TermDepositSlab.changeFunction()}" event="onblur" reRender="errorMsg,taskList,gridPanel14"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblDtEffect" styleClass="label" value="Date of Effect"/>
                        <rich:calendar datePattern="dd/MM/yyyy"  id="calDtEffect" value="#{TermDepositSlab.dtOfEffect}" inputSize="10">
                            <a4j:support event="onchanged"  action="#{TermDepositSlab.onblurDate}" reRender="errorMsg" />
                        </rich:calendar>
                        <h:outputLabel id="lblIntRt" styleClass="label" value="Interest Rate"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:inputText id="txtIntRt" styleClass="input" style="width:50px" value="#{TermDepositSlab.intRate}">
                                <a4j:support actionListener="#{TermDepositSlab.onblurInterestRate}" event="onblur" reRender="errorMsg"/>
                            </h:inputText>
                            <h:outputLabel id="lblIntRt1" styleClass="label" value="%"/>
                        </h:panelGroup>                        
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col2,col3,col3,col3,col3" columns="6" id="gridPanel4" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputLabel id="lblFrom" styleClass="label" value=" From Period "/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:inputText id="txtYears" size="3"  styleClass="input" style="width:30px" value="#{TermDepositSlab.frmYear}">
                                <a4j:support actionListener="#{TermDepositSlab.onblurFromYear}" event="onblur" reRender="errorMsg,stxtTotalFromDays"/>
                            </h:inputText>
                            <h:inputText id="txtMonths" size="2"  styleClass="input" style="width:30px" value="#{TermDepositSlab.frmMonth}">
                                <a4j:support actionListener="#{TermDepositSlab.onblurFromMOnth}" event="onblur" reRender="errorMsg,stxtTotalFromDays"/>
                            </h:inputText>
                            <h:inputText id="txtDays" size="2" styleClass="input" style="width:30px" value="#{TermDepositSlab.frmDays}">
                                <a4j:support actionListener="#{TermDepositSlab.onblurFromDays}" event="onblur" reRender="errorMsg,stxtTotalFromDays"/>
                            </h:inputText>
                            <h:outputLabel id="lbly" styleClass="label" value=" "/>
                            <h:outputLabel id="lblyyyymmdd" styleClass="label" value="Years/Months/Days"/>
                        </h:panelGroup>
                        <h:outputLabel id="ToPd01" styleClass="label" value=" To Period "/>
                        <h:panelGroup id="groupPanel4" layout="block">
                            <h:inputText id="txtYears1" size="3" styleClass="input" style="width:30px" value="#{TermDepositSlab.toYear}">
                                <a4j:support actionListener="#{TermDepositSlab.onblurToYear}" event="onblur" reRender="errorMsg,stxtTotalToDays"/>
                            </h:inputText>
                            <h:inputText id="txtMonths1" size="2" styleClass="input" style="width:30px" value="#{TermDepositSlab.toMonth}">
                                <a4j:support actionListener="#{TermDepositSlab.onblurToMonth}" event="onblur" reRender="errorMsg,stxtTotalToDays"/>
                            </h:inputText>
                            <h:inputText id="txtDays1" size="2" styleClass="input" style="width:30px" value="#{TermDepositSlab.toDays}">
                                <a4j:support actionListener="#{TermDepositSlab.onblurToDays}" event="onblur" reRender="stxtTotalToDays,errorMsg"/>
                            </h:inputText>
                            <h:outputLabel id="lblya" styleClass="label" value=" "/>
                            <h:outputLabel id="lblyyyymmdd1" styleClass="label" value="Years/Months/Days"/>
                        </h:panelGroup>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col2,col3,col3,col3,col3" columns="6" id="gridPanel7" width="100%" styleClass="row1">
                        <h:outputLabel id="lblTotal" styleClass="label" value="Total"/>
                        <h:panelGroup id="groupPanel5" layout="block">
                            <h:outputText id="stxtTotalFromDays" styleClass="output" value="#{TermDepositSlab.totalFromDays}"/>
                            <h:outputLabel id="lblTotalFromDays" styleClass="label" value="Days"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTotal1" styleClass="label" value="Total"/>
                        <h:panelGroup id="groupPanel6" layout="block">
                            <h:outputText id="stxtTotalToDays" styleClass="output" value="#{TermDepositSlab.totalToDays}"/>
                            <h:outputLabel id="lblTotalToDays" styleClass="label" value="Days"/>
                        </h:panelGroup>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>

                    <h:panelGrid id="gridPanel15" columns="6" columnClasses="col3,col2,col3,col3,col3,col3" width="100%" styleClass="row2">
                        <h:outputLabel id="lblFromAmt" styleClass="label" value="From Amount"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtFromAmt" styleClass="input" style="width:100px" value="#{TermDepositSlab.fromAmount}" maxlength="15">
                            <a4j:support actionListener="#{TermDepositSlab.onblurFromAmount}" event="onblur" reRender="errorMsg"/>
                        </h:inputText>
                        <h:outputLabel id="lblToAmt" styleClass="label" value="To Amount"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtToAmt" styleClass="input" style="width:100px" value="#{TermDepositSlab.toAmount}" maxlength="15" >
                            <a4j:support actionListener="#{TermDepositSlab.onblurToAmount}" event="onblur" reRender="errorMsg"/>
                        </h:inputText>
                        <h:outputLabel id="lblCitizen" styleClass="label" value="Senior Citizen"/>
                        <h:panelGroup id="groupPanel7" layout="block">
                            <h:inputText id="txtCitizen" styleClass="input" style="width:70px" value="#{TermDepositSlab.sc}">
                                <a4j:support actionListener="#{TermDepositSlab.onblurSeniorCitizen}" event="onblur" reRender="errorMsg"/>
                            </h:inputText>
                            <h:outputLabel id="lblCitizen1" styleClass="label" value="%"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col2,col3,col3,col3,col3" columns="6" id="gridPanel10" width="100%" styleClass="row1">
                        <h:outputLabel id="lblStaff" styleClass="label" value="Staff"/>
                        <h:panelGroup id="groupPanel8" layout="block">
                            <h:inputText id="txtStaff" styleClass="input" style="width:70px" value="#{TermDepositSlab.st}">
                                <a4j:support actionListener="#{TermDepositSlab.onblurStaff}" event="onblur" reRender="errorMsg"/>
                            </h:inputText>
                            <h:outputLabel id="lblStaff1" styleClass="label" value="%"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblOther" styleClass="label" value="Others"/>
                        <h:panelGroup id="groupPanel9" layout="block">
                            <h:inputText id="txtOther" styleClass="input" style="width:70px" value="#{TermDepositSlab.ot}">
                                <a4j:support actionListener="#{TermDepositSlab.onblurOthers}" event="onblur" focus="btnSave" reRender="errorMsg"/>
                            </h:inputText>
                            <h:outputLabel id="lblOther1" styleClass="label" value="%"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblMinorGirl" styleClass="label" value="Minor Girl"/>
                        <h:panelGroup id="groupPanelMg" layout="block">
                            <h:inputText id="txtMinorGirl" styleClass="input" style="width:70px" value="#{TermDepositSlab.mg}" disabled="#{TermDepositSlab.disMg}">
                                <a4j:support actionListener="#{TermDepositSlab.onblurMinorGirl}" event="onblur" reRender="errorMsg"/>
                            </h:inputText>
                            <h:outputLabel id="lblMg1" styleClass="label" value="%"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  columnClasses="vtop" columns="1" id="gridPanel14" width="100%" styleClass="row2" style="height:150px;">
                        <rich:dataTable rows="3" value="#{TermDepositSlab.tableDtList}" var="dataItem" rowClasses="gridrow1, gridrow2" id="taskList" columnsWidth="100" onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="14"><h:outputText value="Term Deposite Interest Details" /></rich:column>
                                    <rich:column breakBefore="true"><h:outputText value="S.No"/></rich:column>
                                    <rich:column><h:outputText value="Applicable Date"/></rich:column>
                                    <rich:column><h:outputText value="Interest Rate (%)"/></rich:column>
                                    <rich:column><h:outputText value="From Period"/></rich:column>
                                    <rich:column><h:outputText value="To Period" /></rich:column>
                                    <rich:column><h:outputText value="From Amount (Rs)"/></rich:column>
                                    <rich:column><h:outputText value="To Amount (Rs)"/></rich:column>
                                    <rich:column><h:outputText value="Add.Int.(Sr. Citizen)"/></rich:column>
                                    <rich:column><h:outputText value="Add.Int.(Staff)" /></rich:column>
                                    <rich:column><h:outputText value="Add.Int.(Other)" /></rich:column>
                                    <rich:column><h:outputText value="Add.Int.(Minor Girl)" /></rich:column>
                                    <rich:column><h:outputText value="Nature" /></rich:column>
                                    <rich:column><h:outputText value="Update"/></rich:column>
                                    <rich:column><h:outputText value="Delete"/></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{dataItem.seqNo}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.applicableDate}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.intRate}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.fromPeriod}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.toPeriod}"/></rich:column>
                            <rich:column>
                                <h:outputText value="#{dataItem.fromAmt}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataItem.toAmt}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                            </rich:column>
                            <rich:column><h:outputText value="#{dataItem.addIntSr}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.addIntSt}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.addIntOt}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.addIntMg}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.acNature}"/></rich:column>
                            <rich:column style="text-align:center;width:40px">
                                <a4j:commandLink  reRender="taskList" ajaxSingle="true" id="selectlink" onclick="#{rich:component('showPanelForGrid')}.show();">
                                    <h:graphicImage   id="imagerender"  value="/resources/images/edit.gif" style="border:0"/>
                                    <f:setPropertyActionListener value="#{dataItem}" target="#{TermDepositSlab.authorized}"/>
                                    <f:setPropertyActionListener value="#{row}" target="#{TermDepositSlab.currentRow}"/>
                                </a4j:commandLink>
                               
                            </rich:column>
                            <rich:column>
                                <a4j:commandLink ajaxSingle="true" id="deletelink" oncomplete="#{rich:component('deletePanel')}.show()">
                                    <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                    <f:setPropertyActionListener value="#{dataItem}" target="#{TermDepositSlab.authorized}"/>
                                    <f:setPropertyActionListener value="#{row}" target="#{TermDepositSlab.currentRow}" />
                                </a4j:commandLink>
                               
                            </rich:column>
                        </rich:dataTable>
                        <rich:datascroller align="left" for="taskList" maxPages="10"/>
                        <rich:modalPanel id="showPanelForGrid" autosized="true" width="200">
                            <f:facet name="header">
                                <h:outputText value="Do you want to update this record ?" style="padding-right:15px;" />
                            </f:facet>
                            <f:facet name="controls">
                                <h:panelGroup>
                                    <h:graphicImage value="/images/close.png" styleClass="hidelink" id="hidelink1" />
                                    <rich:componentControl for="showPanelForGrid" attachTo="hidelink1" operation="hide" event="onclick" />
                                </h:panelGroup>
                            </f:facet>
                            <h:form>
                                <table width="100%">
                                    <tbody>
                                        <tr>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="Yes" ajaxSingle="true" action="#{TermDepositSlab.setRowValues}"
                                                                   onclick="#{rich:component('showPanelForGrid')}.hide();" reRender="calDtEffect,txtIntRt,txtYears,txtMonths,txtDays,txtYears1,txtMonths1,txtDays1,stxtTotalFromDays,stxtTotalToDays,txtFromAmt,txtToAmt,txtCitizen,txtStaff,txtOther,btnSave,errorMsg,btnUpdate"/>
                                            </td>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="Cancel" onclick="#{rich:component('showPanelForGrid')}.hide();return false;" />
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </h:form>
                        </rich:modalPanel>
                    </h:panelGrid>                   
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel" styleClass="vtop">
                            <a4j:commandButton disabled="#{TermDepositSlab.saveValue}" reRender="gridPanel14,txtIntRt,txtFromAmt,txtToAmt,txtDays,txtYears,txtMonths,txtYears1,txtMonths1,txtDays1,stxtTotalFromDays,stxtTotalToDays,txtCitizen,txtStaff,txtOther,errorMsg" action="#{TermDepositSlab.save}" id="btnSave" value="Save" />
                            <a4j:commandButton id="btnUpdate"  disabled="#{TermDepositSlab.updateValue}" value="Update" action="#{TermDepositSlab.upDateRecord}" 
                                               reRender="gridPanel14,btnSave,btnUpdate,txtIntRt,txtFromAmt,txtToAmt,txtDays,txtYears,txtMonths,txtYears1,txtMonths1,txtDays1,stxtTotalFromDays,stxtTotalToDays,txtCitizen,txtStaff,txtOther,gridPanel14,errorMsg,btnSave" />
                            <a4j:commandButton reRender="gridPanel14,errorMsg" action="#{TermDepositSlab.getTableHistory}"id="btnShowHistory" value="Show History"/>
                            <a4j:commandButton reRender="gridPanel14,errorMsg" action="#{TermDepositSlab.retuCurrentDateIte}"id="btnShowCurrent" value="Show Current"/>
                            <a4j:commandButton id="btnPrint" value="Print" disabled="true"/>
                            <a4j:commandButton reRender="txtIntRt,btnSave,btnUpdate,txtFromAmt,txtToAmt,txtDays,txtYears,txtMonths,txtYears1,txtMonths1,txtDays1,stxtTotalFromDays,calDtEffect,stxtTotalToDays,txtCitizen,txtStaff,txtOther,errorMsg,gridPanel14" 
                                               id="btnRefresh" value="Refresh" action="#{TermDepositSlab.refreshForm}"  disabled="#{TermDepositSlab.saveValue},#{TermDepositSlab.updateValue}" focus="#{rich:clientId('calDtEffect')}InputDate"/>
                               
                            <a4j:commandButton id="btnExit" value="Exit" action="#{TermDepositSlab.exitForm}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="deletePanel" autosized="true" width="200">
                <f:facet name="header">
                    <h:outputText value="Are You Sure, You Want to Delete this Record?" style="padding-right:15px;" />
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/images/modal/close.png" styleClass="hidelink" id="hidelink2" />
                        <rich:componentControl for="deletePanel" attachTo="hidelink2" operation="hide" event="onclick" />
                    </h:panelGroup>
                </f:facet>
                <h:form>
                    <table width="100%">
                        <tbody>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" ajaxSingle="true" action="#{TermDepositSlab.delete}"
                                                       oncomplete="#{rich:component('deletePanel')}.hide();" reRender="errorMsg,taskList,gridPanel14" />
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Cancel" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
