<%-- 
    Document   : salaryProcess
    Created on : Apr 5, 2013, 11:50:53 AM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Transfer Batch Processing</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".issueDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1"  style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="headerPanel" columns="3"  style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup>
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{salaryProcess.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="Transfer Batch Processing"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{salaryProcess.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="messagePanel" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                        <h:outputText id="stxtMessage" styleClass="error" value="#{salaryProcess.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="componentPanel" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblMode" styleClass="label" value="Transaction Mode"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddMode" styleClass="ddlist" size="1" value="#{salaryProcess.mode}">
                            <f:selectItems value="#{salaryProcess.modeList}"/>
                            <a4j:support action="#{salaryProcess.modeProcess}" event="onblur" reRender="stxtMessage,txtDetails" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblAcno" styleClass="label" value="Account No."><font class="required" color="red">*</font></h:outputLabel>
                        <h:panelGroup layout="block" style="width:100%;text-align:left;">
                           <h:inputText id="txtAcno" styleClass="input" style="width:100px" maxlength="#{salaryProcess.acNoMaxLen}" value="#{salaryProcess.accountNo}">
                                <a4j:support action="#{salaryProcess.accountProcess}" event="onblur" reRender="stxtMessage,stxtAccountName,panelGrpCrDr" oncomplete="setMask();"/>
                            </h:inputText>
                           <h:outputText id="stxtAccNo" styleClass="output" value="#{salaryProcess.acno}"/>
                        </h:panelGroup>                        
                        <h:outputLabel id="label9" styleClass="label" value="Balance"/>
                        <h:panelGroup id="panelGrpCrDr" layout="block">
                            <h:outputText id="stxtBal" styleClass="output" value="#{salaryProcess.balance}">
                                <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                            </h:outputText>
                            <h:outputText id="stxtBalCrDr" styleClass="output" value="#{salaryProcess.labelCrDr}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="instComponentPanel" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblAccountName" styleClass="label" value="Account Name"/>
                        <h:outputText id="stxtAccountName" styleClass="output" value="#{salaryProcess.accountName}"/>
                        <h:outputLabel id="lblAmount" styleClass="label" value="Amount"><font class="required" color="red">*</font></h:outputLabel>    
                        <h:inputText id="txtAmount" styleClass="input" style="width:120px" value="#{salaryProcess.amount}">
                            <a4j:support action="#{salaryProcess.amountProcess}" event="onblur" reRender="stxtMessage" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel id="lblDrCr" styleClass="label" value="Transaction Type"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddTranType" styleClass="ddlist" size="1" value="#{salaryProcess.tranType}">
                            <f:selectItems value="#{salaryProcess.tranTypeList}"/>
                            <a4j:support action="#{salaryProcess.tranTypeProcess}" event="onblur" reRender="stxtMessage,txtInstNo,txtInstDt,txtDetails" oncomplete="if(#{salaryProcess.tranType == '0'}){#{rich:element('txtDetails')}.focus();}
                                         else if(#{salaryProcess.tranType == '1'}){#{rich:element('txtInstNo')}.focus();}
                                         setMask();"/>
                        </h:selectOneListbox>                        
                    </h:panelGrid>
                    <h:panelGrid id="detailsComponentPanel" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblInstNo" styleClass="label" value="Inst.No."/>
                        <h:inputText id="txtInstNo" styleClass="input" style="width:70px" maxlength="10" value="#{salaryProcess.instNo}" disabled="#{salaryProcess.instNoFlag}">
                            <a4j:support event="onblur" action="#{salaryProcess.instNoProcess}" reRender="stxtMessage" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel id="lblInstDt" styleClass="label" value="Inst.Date"/>
                        <h:inputText id="txtInstDt" styleClass="input issueDt" style="width:70px" value="#{salaryProcess.instDt}" disabled="#{salaryProcess.instDtFlag}">
                            <a4j:support action="#{salaryProcess.instDtProcess}" event="onblur" reRender="stxtMessage" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel id="lblDetails" styleClass="label" value="Details"/>
                        <h:inputText id="txtDetails" styleClass="input" style="width:200px" value="#{salaryProcess.details}" disabled="#{salaryProcess.detailsFlag}" maxlength="150">
                            <a4j:support action="#{salaryProcess.detailProcess('')}" event="onblur" 
                                         reRender="stxtMessage,tablePanel,taskList,txtInstNo,
                                         txtInstDt,txtDetails,txtAcno,txtAmount,stxtTotalCr,
                                         stxtTotalDr,stxtAccountName,processPanel,confirmid,stxtBal" 
                                         oncomplete="setMask();if(#{salaryProcess.odBalFlag==true}){#{rich:component('processPanel')}.show()}"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid id="totalComponentPanel" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblTotalCr" styleClass="label" value="Total Cr:"/>
                        <h:outputText id="stxtTotalCr" styleClass="output" value="#{salaryProcess.totalCr}"/>
                        <h:outputLabel id="lblTotalDr" styleClass="label" value="Total Dr:"/>
                        <h:outputText id="stxtTotalDr" styleClass="output" value="#{salaryProcess.totalDr}"/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnHtml" value="Save Batch" oncomplete="#{rich:component('savePanel')}.show();" reRender="stxtMessage,savePanel"/>
                            <a4j:commandButton action="#{salaryProcess.btnRefreshAction}" id="btnRefresh" value="Refresh" reRender="stxtMessage,mainPanel,tablePanel,taskList,panelGrpCrDr"/>
                            <a4j:commandButton action="#{salaryProcess.btnExitAction}" id="btnExit" value="Exit" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                        <a4j:region id="tblRegion">
                            <rich:dataTable value="#{salaryProcess.tableDataList}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="10" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="7"><h:outputText value="Salary Posting Detail" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="S.No" /></rich:column>
                                        <rich:column><h:outputText value="Transaction No." /></rich:column>
                                        <rich:column><h:outputText value="Account No." /></rich:column>
                                        <rich:column><h:outputText value="Account Name" /></rich:column>
                                        <rich:column><h:outputText value="Amount" /></rich:column>
                                        <rich:column><h:outputText value="Type" /></rich:column>
                                        <rich:column><h:outputText value="Delete"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.sno}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.recno}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.salaryAccount}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.salaryAccountName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.salaryAmount}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.salaryTranType}" /></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink id="deletelink" ajaxSingle="true" oncomplete="#{rich:component('deletePanel')}.show();">
                                        <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{salaryProcess.currentItem}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="100"/>
                        </a4j:region>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:region id="btnActionGrid">
                <rich:modalPanel id="deletePanel" autosized="true" width="250" onshow="#{rich:element('btnYes')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog"/>
                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText value="Are you sure to delete this record ?"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="btnYes" action="#{salaryProcess.deleteProcess}" onclick="#{rich:component('deletePanel')}.hide();" reRender="stxtMessage,mainPanel,tablePanel,taskList"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('deletePanel')}.hide();"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
                <rich:modalPanel id="savePanel" autosized="true" width="250" onshow="#{rich:element('btnSaveYes')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog" />
                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText value="Are you sure to save this batch ?"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="btnSaveYes" action="#{salaryProcess.btnSaveAction}" onclick="#{rich:component('savePanel')}.hide();" reRender="stxtMessage,mainPanel,tablePanel,taskList,stxtTotalCr,stxtTotalDr,stxtAccountName"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="No" id="btnSaveNo" onclick="#{rich:component('savePanel')}.hide();"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
                <rich:modalPanel id="processPanel" autosized="true" width="250" onshow="#{rich:element('btnOdNo')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText id="confirmid" value="#{salaryProcess.confirmationMsg}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="btnOdYes" action="#{salaryProcess.odBalExceedProcess}" 
                                                           onclick="#{rich:component('processPanel')}.hide();" 
                                                           reRender="stxtMessage,tablePanel,taskList,txtInstNo,
                                                           txtInstDt,txtDetails,txtAcno,txtAmount,stxtTotalCr,
                                                           stxtTotalDr,stxtAccountName"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="No" id="btnOdNo" onclick="#{rich:component('processPanel')}.hide();"
                                                           reRender="stxtMessage"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
            </a4j:region>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="btnActionGrid"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
