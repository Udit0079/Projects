<%-- 
    Document   : IbRequestProcessing
    Created on : 12 Jan, 2015, 10:10:00 AM
    Author     : Dinesh Pratap Singh
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Internet Banking Request Process</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
        </head>
        <body>
            <a4j:form id="form">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{ibRequestProcess.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Internet Banking Request"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="labelUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ibRequestProcess.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="subBodyPanel" style="width:100%;">
                        <h:panelGrid columnClasses="col2,col7,col9" columns="2" id="errorgrid" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputText id="stxtMsg" styleClass="error" value="#{ibRequestProcess.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columns="2" id="subBodyPanel1" style="width:100%;">
                            <h:panelGrid id="leftPanel" columns="1" style="width:100%;" >
                                <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="panel1" style="width:100%;" styleClass="row2">
                                    <h:outputLabel id="lblRequestType" styleClass="label" value="Request Type" ><font class="required" color="red">*</font></h:outputLabel>
                                        <h:selectOneListbox id="ddRequestType" styleClass="ddlist" value="#{ibRequestProcess.requestType}" size="1" style="width:134px">
                                            <f:selectItems value="#{ibRequestProcess.requestTypeList}" />
                                            <a4j:support action="#{ibRequestProcess.requestTypeAction}" event="onblur" reRender="stxtMsg,requestTableGrid,requestTable"/>
                                        </h:selectOneListbox>
                                        <h:outputLabel id="lblAction" styleClass="label" value="Action" ><font class="required" color="red">*</font></h:outputLabel>
                                        <h:selectOneListbox id="ddAction" styleClass="ddlist" value="#{ibRequestProcess.action}" size="1" style="width: 134px">
                                            <f:selectItems value="#{ibRequestProcess.actionList}" />
                                            <a4j:support action="#{ibRequestProcess.processAction}" event="onblur" 
                                                         reRender="stxtMsg,lblChqNoFrom,lblReason,stxtChqNoFrom,txtReason,lblChqNoTo,
                                                         stxtChqNoTo,panel3,panel4,stocktable,taskList,panel8,ddChequeSeries" focus="ddChequeSeries"/>
                                        </h:selectOneListbox>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="panel8" style="width:100%;display:#{ibRequestProcess.chqBookDisplay}" styleClass="row1">
                                        <h:outputLabel id="lblChequeSeries" styleClass="label"  value="Cheque Series"><font class="required" color="red">*</font></h:outputLabel>
                                        <h:selectOneListbox id="ddChequeSeries" size="1" styleClass="ddlist" style="width:134px" value="#{ibRequestProcess.chequeSeries}">
                                            <f:selectItems value="#{ibRequestProcess.chequeSeriesList}" />
                                            <a4j:support action="#{ibRequestProcess.getTableStock}" event="onblur" reRender="stxtMsg,stocktable,taskList"/>
                                        </h:selectOneListbox>
                                        <h:outputLabel/>
                                        <h:outputLabel/>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="panel2" style="width:100%;" styleClass="row2">
                                        <h:panelGroup>
                                            <h:outputLabel id="lblChqNoFrom" styleClass="label"  value="Chq. No. From" style="display:#{ibRequestProcess.chqBookDisplay}"/>
                                            <h:outputLabel id="lblReason" styleClass="label" value="Reason" style="display:#{ibRequestProcess.rescheduleDisplay}"><font class="required" color="red">*</font></h:outputLabel>
                                        </h:panelGroup>
                                        <h:panelGroup>
                                            <h:outputText id="stxtChqNoFrom" style="display:#{ibRequestProcess.chqBookDisplay}" value="#{ibRequestProcess.chqFrom}" styleClass="output"/>
                                            <h:inputText id="txtReason" style="width:134px;display:#{ibRequestProcess.rescheduleDisplay}" value="#{ibRequestProcess.rescheduleReason}" styleClass="input" maxlength="100" onkeyup="this.value = this.value.toUpperCase();"/>
                                        </h:panelGroup>
                                        <h:outputLabel id="lblChqNoTo" styleClass="label" value="Chq.No.To" style="display:#{ibRequestProcess.chqBookDisplay}"/>
                                        <h:outputText id="stxtChqNoTo" style="display:#{ibRequestProcess.chqBookDisplay}" value="#{ibRequestProcess.chqTo}" styleClass="output"/>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="panel3" style="width:100%;display:#{ibRequestProcess.chqBookDisplay}" styleClass="row1">
                                        <h:outputLabel id="lblNoOfLeaves" styleClass="label"  value="No.Of Leaves"/>
                                        <h:outputText id="stxtNoOfLeaves" value="#{ibRequestProcess.noOfLeaves}" styleClass="output"/>
                                        <h:outputLabel id="lblDeliverAgency" styleClass="label" value="Delivery Agency"/>
                                        <h:inputText id="txtDeliverAgency" style="width:134px;" value="#{ibRequestProcess.deliveryAgency}" styleClass="input" maxlength="100"/>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="panel4" style="width:100%;display:#{ibRequestProcess.chqBookDisplay}" styleClass="row2">
                                        <h:outputLabel id="lblDeliveryMode" styleClass="label" value="Delivery Mode"/>
                                        <h:selectOneListbox id="ddDeliveryMode" styleClass="ddlist" value="#{ibRequestProcess.deliveryMode}" size="1">
                                            <f:selectItems value="#{ibRequestProcess.deliveryModeList}" />
                                        </h:selectOneListbox>
                                        <h:outputLabel/>
                                        <h:outputLabel/>
                                    </h:panelGrid>
                                </h:panelGrid>
                                <h:panelGrid id="rightPanel" columns="1" style="width:100%;">
                                    <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="panel5" style="width:100%;" styleClass="row2">
                                        <h:outputLabel id="lblAccountNo" styleClass="label"  value="A/c No"/>
                                        <h:outputText id="stxtAccountNo" styleClass="output" value="#{ibRequestProcess.stAccountNo}"/>
                                        <h:outputLabel id="lblInvtType" styleClass="label"  value="Inventory Type"/>
                                        <h:outputText id="stxtInvtType" styleClass="output" value="#{ibRequestProcess.stInvtType}"/>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col9,col9" columns="2" id="panel6" style="width:100%;" styleClass="row1">
                                        <h:outputLabel id="lblPartyName" styleClass="label"  value="Party Name"/>
                                        <h:outputText id="stxtPartyName" styleClass="output" value="#{ibRequestProcess.stPartyName}"/>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="panel7" style="width:100%;" styleClass="row2">
                                        <h:outputLabel id="lblReqStatus" styleClass="label"  value="Request Status"/>
                                        <h:outputText id="stxtReqStatus" styleClass="output" value="#{ibRequestProcess.stRequestStatus}"/>
                                        <h:outputLabel id="lblRequestDate" styleClass="label"  value="Request Date"/>
                                        <h:outputText id="stxtRequestDate" styleClass="output" value="#{ibRequestProcess.stCbsRequestDt}"/>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="vtop" columns="1" id="stocktable" width="100%" styleClass="row1" 
                                                 style="height:168px;display:#{ibRequestProcess.chqBookDisplay}">
                                        <a4j:region>
                                            <rich:dataTable value="#{ibRequestProcess.stocktable}" var="item" 
                                                            rowClasses="row1, row2" id="taskList" rows="3" columnsWidth="100" rowKeyVar="row"
                                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                                <f:facet name="header">
                                                    <rich:columnGroup>
                                                        <rich:column colspan="5"><h:outputText value="Detail of inventory" /></rich:column>
                                                        <rich:column breakBefore="true" width="10%"><h:outputText value="S.No."/></rich:column>
                                                        <rich:column width="28%"><h:outputText value="Chq. No. From"/></rich:column>
                                                        <rich:column width="28%"><h:outputText value="Chq. No. To" /></rich:column>
                                                        <rich:column width="28%" style="text-align:center;"><h:outputText value="Stock Date" /></rich:column>
                                                        <rich:column width="6%"><h:outputText value="select" /></rich:column>
                                                    </rich:columnGroup>
                                                </f:facet>
                                                <rich:column width="10%"><h:outputText value="#{item.sno}" /></rich:column>
                                                <rich:column width="28%"><h:outputText value="#{item.chNofrom}" /></rich:column>
                                                <rich:column width="28%"><h:outputText value="#{item.chNoTo}" /></rich:column>
                                                <rich:column width="28%" style="text-align:center;"><h:outputText value="#{item.stockDt}" /></rich:column>
                                                <rich:column width="6%">
                                                    <a4j:commandLink ajaxSingle="true" id="selectlink" 
                                                                     action="#{ibRequestProcess.selectInventory}" 
                                                                     reRender="stxtMsg,stxtChqNoFrom,stxtChqNoTo,stxtNoOfLeaves" 
                                                                     focus="txtDeliverAgency">
                                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                                        <f:setPropertyActionListener value="#{item}" target="#{ibRequestProcess.currentItem}"/>
                                                    </a4j:commandLink>
                                                </rich:column>
                                            </rich:dataTable>
                                            <rich:datascroller id="scroller" align="left" for="taskList" maxPages="10"/>
                                        </a4j:region>
                                    </h:panelGrid>
                                </h:panelGrid>
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="vtop" columns="1" id="requestTableGrid" width="100%" styleClass="row2" style="height:168px;">
                            <a4j:region>
                                <rich:dataTable value="#{ibRequestProcess.requestTable}" var="dataItem" 
                                                rowClasses="row1,row2" id="requestTable" rows="3" 
                                                columnsWidth="100" rowKeyVar="row" width="100%" 
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'" 
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="13"><h:outputText value="Detail of request"/></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="Account No" /></rich:column>
                                            <rich:column><h:outputText value="Request No" /></rich:column>
                                            <rich:column><h:outputText value="IB Request Date" /></rich:column>
                                            <rich:column><h:outputText value="CBS Request Date" /></rich:column>
                                            <rich:column><h:outputText value="Request Status" /></rich:column>
                                            <rich:column><h:outputText value="Request Br.No" /></rich:column>
                                            <rich:column><h:outputText value="Class" /></rich:column>
                                            <rich:column><h:outputText value="Type" /></rich:column>
                                            <rich:column><h:outputText value="Charge Status" /></rich:column>
                                            <rich:column><h:outputText value="Reschedule Date" /></rich:column>
                                            <rich:column><h:outputText value="Reschedule Reason" /></rich:column>
                                            <rich:column><h:outputText value="Reschedule By" /></rich:column>
                                            <rich:column><h:outputText value="Select" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{dataItem.acno}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.requestNo}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.ibRequestDt}" /></rich:column>
                                    <rich:column ><h:outputText value="#{dataItem.cbsRequestDt}" /></rich:column>
                                    <rich:column ><h:outputText value="#{dataItem.requestStatus}" /></rich:column>
                                    <rich:column ><h:outputText value="#{dataItem.requestBreakNo}" /></rich:column>
                                    <rich:column ><h:outputText value="#{dataItem.invtClass}" /></rich:column>
                                    <rich:column ><h:outputText value="#{dataItem.invtType}" /></rich:column>
                                    <rich:column ><h:outputText value="#{dataItem.chargeStatus}" /></rich:column>
                                    <rich:column ><h:outputText value="#{dataItem.rescheduleDt}" /></rich:column>
                                    <rich:column ><h:outputText value="#{dataItem.rescheduleReason}" /></rich:column>
                                    <rich:column ><h:outputText value="#{dataItem.rescheduleBy}" /></rich:column>
                                    <rich:column>
                                        <a4j:commandLink ajaxSingle="true" id="rqSelectlink" 
                                                         action="#{ibRequestProcess.selectRequestFromTable}" 
                                                         reRender="stxtMsg,ddAction,stxtAccountNo,stxtInvtType,stxtPartyName,
                                                         stxtReqStatus,stxtRequestDate" focus="ddAction">
                                            <h:graphicImage value="/resources/images/passed.gif" style="border:0" />
                                            <f:setPropertyActionListener value="#{dataItem}" target="#{ibRequestProcess.rqCurrentItem}"/>
                                        </a4j:commandLink>
                                    </rich:column>
                                </rich:dataTable>
                                <rich:datascroller id="scrollerIssue" align="left" for="requestTable" maxPages="10" />
                            </a4j:region>
                            <rich:modalPanel id="savePanel" autosized="true" width="250" onshow="#{rich:element('saveYes')}.focus()">
                                <f:facet name="header">
                                    <h:outputText value="Confirmation DialogBox" />
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
                                                <a4j:region id="yesBtn">
                                                    <a4j:commandButton id="saveYes" value="Yes" ajaxSingle="true" 
                                                                       action="#{ibRequestProcess.formProcessing}" 
                                                                       oncomplete="#{rich:component('savePanel')}.hide();" 
                                                                       reRender="stxtMsg,ddRequestType,mainPanel" 
                                                                       focus="ddRequestType"/>
                                                </a4j:region>
                                            </td>
                                            <td align="center" width="50%">
                                                <a4j:commandButton id="saveNo" value="No" onclick="#{rich:component('savePanel')}.hide();return false;" />
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </h:form>
                        </rich:modalPanel>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="BtnPanel">
                            <a4j:commandButton id="btnSave" value="Save" oncomplete="#{rich:component('savePanel')}.show()" reRender="stxtMsg,savePanel"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{ibRequestProcess.btnRefresh}" reRender="mainPanel"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{ibRequestProcess.btnExit}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="yesBtn"/>
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>                    
            </a4j:form>
        </body>
    </html>
</f:view>


