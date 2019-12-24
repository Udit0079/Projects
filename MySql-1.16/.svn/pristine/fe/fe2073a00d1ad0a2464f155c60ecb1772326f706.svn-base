<%-- 
    Document   : StockStatement
    Created on : Oct 13, 2010, 3:03:22 PM
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
            <title>Stock Statement</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{StockStatement.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Stock Statement"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{StockStatement.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="errorMessage" styleClass="error" value="#{StockStatement.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{StockStatement.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="2" id="a3" width="100%">
                        <h:panelGrid columns="6" id="a5" style="height:30px;" styleClass="row1" columnClasses="col9" width="100%">
                           <h:outputLabel id="lblAcctNo" styleClass="label" value="Account No. :" style=""><font class="required" color="red">*</font></h:outputLabel>
                          <h:inputText id="txtAcNo" maxlength="#{StockStatement.acNoMaxLen}" tabindex="2" value="#{StockStatement.oldAcctNo}" style="width:90px" styleClass="input"  >
                                <a4j:support action="#{StockStatement.getAccountDetail}" event="onblur" oncomplete="if(#{StockStatement.errorMessage=='Account No. Does Not Exist.'}){#{rich:element('txtAcNo')}.focus();}
                                             else{#{rich:element('ddSecDesc')}.focus();}"
                                             reRender="a5,a6,a7,a8,a9,a10,a11,a12,a14,a15,a16,a17,a18,a19,message,errorMessage,lpg,table,taskList,gpFooter" limitToList="true" focus="ddSecDesc" />
                          </h:inputText>
                          <h:outputLabel id="stxtAcctNo" styleClass="label" value="#{StockStatement.acctNo}" ></h:outputLabel>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="2" id="a6" width="100%" style="height:30px;" styleClass="row1">
                            <h:outputLabel id="lblName" styleClass="label" value="Name :" style=""/>
                            <h:outputText id="stxtName" styleClass="output" value="#{StockStatement.custName}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="2" id="a8" width="100%" style="height:30px;" styleClass="row2">
                            <h:outputLabel id="lblSecDesc" styleClass="label" value="Security Desc. :"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddSecDesc" tabindex="3" styleClass="ddlist"  value="#{StockStatement.secDesc}" size="1" style="width:120px">
                                <f:selectItems value="#{StockStatement.secDescList}" />
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="2" id="a7" width="100%" style="height:30px;" styleClass="row2">
                            <h:outputLabel id="lblDesc" styleClass="label" value="Description :" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtDesc" maxlength="20" tabindex="4" style="width:150px" value="#{StockStatement.desc}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="2" id="a9" width="100%" style="height:30px;" styleClass="row1">
                            <h:outputLabel id="lblValHypo" styleClass="label" value="Value Hypothicated :" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtValHypo" maxlength="20" tabindex="5" style="width:90px" value="#{StockStatement.valueHypo}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="2" id="a10" width="100%" style="height:30px;" styleClass="row1">
                            <h:outputLabel id="lblMargin" styleClass="label" value="Margin % :"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtMargin" maxlength="3" tabindex="6" style="width:60px" value="#{StockStatement.margin}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="2" id="a11" width="100%" style="height:30px;" styleClass="row2">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnSubmit" tabindex="7"  value="Submit" oncomplete="#{rich:component('submitPanel')}.show()" reRender="a5,a6,a7,a8,a9,a10,a11,a12,a14,a15,a16,a17,a18,a19,message,errorMessage,lpg,table,taskList" focus=""/>
                            <a4j:commandButton id="btnNewStatement" tabindex="8" value="New Statement" action="#{StockStatement.newStatementDpBtn}" disabled="#{StockStatement.flag5}" reRender="a14,a19,table,taskList" />
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="table" style="height:auto;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{StockStatement.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="3" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="15"><h:outputText value="Details" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="S. No." /></rich:column>
                                        <rich:column><h:outputText value="STM NO" /></rich:column>
                                        <rich:column><h:outputText value="SEC NO" /></rich:column>
                                        <rich:column><h:outputText value="SECURITY" /></rich:column>
                                        <rich:column><h:outputText value="SEC DESC" /></rich:column>
                                        <rich:column><h:outputText value="VALUE" /></rich:column>
                                        <rich:column><h:outputText value="MARGIN" /></rich:column>
                                        <rich:column><h:outputText value="DP PARTITION" /></rich:column>
                                        <rich:column><h:outputText value="NET DP" /></rich:column>
                                        <rich:column><h:outputText value="RENEW DUE" /></rich:column>
                                        <rich:column><h:outputText value="STATUS TODAY" /></rich:column>
                                        <rich:column width="20"><h:outputText value="Delete" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.sNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.stmNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.secNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.security}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.secDesc}" /></rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem.value}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem.margin}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem.dpPartition}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem.netDp}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column><h:outputText value="#{dataItem.renewDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.statusToday}" /></rich:column>
                                <rich:column>
                                    <a4j:commandLink ajaxSingle="true" id="deletelink" oncomplete="#{rich:component('deletePanel')}.show()" reRender="a5,a6,a7,a8,a9,a10,a11,a12,a14,a15,a16,a17,a18,a19,message,errorMessage,lpg,table,taskList">
                                        <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{StockStatement.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{StockStatement.currentRow}" />
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="20" />
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="2" id="a12" width="100%">
                        <h:panelGrid columnClasses="col9" columns="2" id="a14" width="100%" style="height:30px;" styleClass="row1">
                            <h:outputLabel id="lblStockStatSubmitDt" styleClass="label" value="Stock Statement Submission Date :" ><font class="required" color="red">*</font></h:outputLabel>
                        <rich:calendar datePattern="dd/MM/yyyy" id="calStockStatSubmitDt" value="#{StockStatement.stockStSubDt}" tabindex="9" inputSize="10"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="2" id="a15" width="100%" style="height:30px;" styleClass="row1">
                            <h:outputLabel id="lblActStatSubmitDt" styleClass="label" value="Actual Statement Submission Date :" ><font class="required" color="red">*</font></h:outputLabel>
                            <rich:calendar datePattern="dd/MM/yyyy" id="calActStatSubmitDt" value="#{StockStatement.actualStSubDt}" tabindex="10" inputSize="10"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="2" id="a16" width="100%" style="height:30px;" styleClass="row2">
                            <h:outputLabel id="lblStockStatDueDt" styleClass="label" value="Stock Statement Due Date :" ><font class="required" color="red">*</font></h:outputLabel>
                            <rich:calendar datePattern="dd/MM/yyyy" id="calStockStatDueDt" value="#{StockStatement.stockStDueDt}" tabindex="11" inputSize="10">
                                <a4j:support action="#{StockStatement.onChangeStockStatementDueDate}" event="onchanged" reRender="message,errorMessage" focus="txtGracePeriod"/>
                            </rich:calendar>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="2" id="a17" width="100%" style="height:30px;" styleClass="row2">
                            <h:outputLabel id="lblGracePeriod" styleClass="label" value="Grace Period (In Days) :" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtGracePeriod" maxlength="3" tabindex="12" size="8" value="#{StockStatement.gracePeriod}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="2" id="a19" width="100%" style="height:30px;" styleClass="row2">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnSave" tabindex="13" value="Save" oncomplete="#{rich:component('savePanel')}.show()" disabled="#{StockStatement.flag1}"
                                               reRender="a5,a6,a7,a8,a9,a10,a11,a12,a14,a15,a16,a17,a18,a19,message,errorMessage,lpg,table,taskList" focus="">
                                <rich:toolTip for="btnSave" value="Click To Save." styleClass="output"></rich:toolTip>
                            </a4j:commandButton>
                            <a4j:commandButton id="btnResubmit" tabindex="14" value="Resubmit" disabled="#{StockStatement.flag4}" oncomplete="#{rich:component('resubmitPanel')}.show()" reRender="a5,a6,a7,a8,a9,a10,a11,a12,a14,a15,a16,a17,a18,a19,message,errorMessage,lpg,table,taskList,gpFooter" >
                                <rich:toolTip for="btnResubmit" value="Click To Resubmit." styleClass="output"></rich:toolTip>
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columns="6" id="a18" style="height:30px;" styleClass="row1" columnClasses="col3,col3,col3,col3,col3,col1" width="100%">
                        <h:outputLabel id="lblSancLimit" styleClass="label" value="Sanction Limit :" />
                        <h:outputText id="stxtSancLimit" styleClass="output" value="#{StockStatement.sancLimit}" style="color:green;"/>
                        <h:outputLabel id="lblPrevLimit" styleClass="label" value="Previous Limit :" />
                        <h:outputText id="stxtPrevLimit" styleClass="output" value="#{StockStatement.prevDp}" style="color:green;"/>
                        <a4j:commandButton id="btnNetProposedDp" tabindex="15" value="Net Proposed DP" action="#{StockStatement.netProposedDpBtn}" disabled="#{StockStatement.flag2}" reRender="a5,a6,a7,a8,a9,a10,a11,a12,a14,a15,a16,a17,a18,a19,message,errorMessage,lpg,table,taskList,gpFooter" focus="btnUpdateDp"/>
                        <h:outputText id="stxtNetProposedDp" styleClass="output" value="#{StockStatement.netDp}"style="color:blue;"/>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnUpdateDp" tabindex="16" value="Update DP" oncomplete="#{rich:component('dpPanel')}.show()" disabled="#{StockStatement.flag3}" reRender="a5,a6,a7,a8,a9,a10,a11,a12,a14,a15,a16,a17,a18,a19,message,errorMessage,lpg,table,taskList" />
                            <a4j:commandButton id="btnRefresh" tabindex="17" value="Refresh" oncomplete="#{rich:component('resetPanel')}.show()" reRender="a5,a6,a7,a8,a9,a10,a11,a12,a14,a15,a16,a17,a18,a19,message,errorMessage,lpg,table,taskList,gpFooter" focus="txtAcNo"/>
                            <a4j:commandButton id="btnExit" tabindex="18" value="Exit" reRender="a5,a6,a7,a8,a9,a10,a11,a12,a14,a15,a16,a17,a18,a19,message,errorMessage,lpg,table,taskList,gpFooter" action="#{StockStatement.exitForm}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="deletePanel" autosized="true" width="250" onshow="#{rich:element('btnYesDel')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Do You Want To Delete This Record ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesDel" ajaxSingle="true" action="#{StockStatement.delete}"
                                                       oncomplete="#{rich:component('deletePanel')}.hide();" reRender="a5,a6,a7,a8,a9,a10,a11,a12,a14,a15,a16,a17,a18,a19,message,errorMessage,lpg,table,taskList" />
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Cancel" id="btnNoDel" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="submitPanel" autosized="true" width="250" onshow="#{rich:element('btnYesSubmit')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Do You Want To Add More Record ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesSubmit" ajaxSingle="true" action="#{StockStatement.gridLoad}"
                                                       oncomplete="#{rich:component('submitPanel')}.hide();" reRender="a5,a6,a7,a8,a9,a10,a11,a12,a14,a15,a16,a17,a18,a19,message,errorMessage,lpg,table,taskList" focus="ddSecDesc"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNoSubmit" onclick="#{rich:component('submitPanel')}.hide();return false;" focus="#{rich:clientId('calStockStatSubmitDt')}InputDate" reRender="a14,message,errorMessage,calStockStatSubmitDt"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="resubmitPanel" autosized="true" width="300" onshow="#{rich:element('btnYesResubmit')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Do you want to submit new Stock Statement with same Details, as shown in the Grid ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesResubmit" ajaxSingle="true" action="#{StockStatement.resubmitBtn}"
                                                       oncomplete="#{rich:component('resubmitPanel')}.hide();" reRender="a5,a6,a7,a8,a9,a10,a11,a12,a14,a15,a16,a17,a18,a19,message,errorMessage,lpg,table,taskList,gpFooter" focus="calStockStatSubmitDt"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNoResubmit" onclick="#{rich:component('resubmitPanel')}.hide();return false;" focus="btnNetProposedDp"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="savePanel" autosized="true" width="250" onshow="#{rich:element('btnYesSave')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Do You Want To Save Stock Statement As Shown In The Grid ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesSave" ajaxSingle="true" action="#{StockStatement.saveStockDetail}"
                                                       oncomplete="#{rich:component('savePanel')}.hide();" reRender="a5,a6,a7,a8,a9,a10,a11,a12,a14,a15,a16,a17,a18,a19,message,errorMessage,lpg,table,taskList,gpFooter" focus="btnNetProposedDp"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNoSave" onclick="#{rich:component('savePanel')}.hide();return false;" focus="ddSecDesc"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="resetPanel" autosized="true" width="300" onshow="#{rich:element('btnYesReset')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="DP Limit Will Not Be Updated After Reset the Screen. Do you want to Reset the Screen ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesReset" ajaxSingle="true" action="#{StockStatement.resetForm}"
                                                       oncomplete="#{rich:component('resetPanel')}.hide();" reRender="a5,a6,a7,a8,a9,a10,a11,a12,a14,a15,a16,a17,a18,a19,message,errorMessage,lpg,table,taskList,gpFooter" focus="btnNetProposedDp"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNoReset" onclick="#{rich:component('resetPanel')}.hide();return false;" focus="ddSecDesc"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="dpPanel" autosized="true" width="250" onshow="#{rich:element('btnYesdpPanel')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure You Want To Set DP Limit To This A/c. ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesdpPanel" ajaxSingle="true" action="#{StockStatement.setNewDp}"
                                                       oncomplete="#{rich:component('dpPanel')}.hide();" reRender="a5,a6,a7,a8,a9,a10,a11,a12,a14,a15,a16,a17,a18,a19,message,errorMessage,lpg,table,taskList,gpFooter" focus="ddAcctNo"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNodpPanel" onclick="#{rich:component('dpPanel')}.hide();return false;" focus="ddSecDesc"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
