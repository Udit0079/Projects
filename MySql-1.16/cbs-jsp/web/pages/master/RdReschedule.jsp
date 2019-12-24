<%-- 
    Document   : RdReschedule
    Created on : 13 May, 2013, 7:09:33 PM
    Author     : Nishant Srivastava
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <title>Rd Reschedule</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery){
                    setMask();
                });                
            </script>
        </head>
        <body>
            <a4j:form id="RdReschedule">
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
                <h:panelGrid id="PanelGridMain" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="HeaderPanel" columns="3"  style="text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{RdReschedule.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="Rd Reschedule"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUsername" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="otUsername" styleClass="output" value="#{RdReschedule.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="errPanel" style="text-align:center" styleClass="row1">
                        <h:outputText id="errmsg" value="#{RdReschedule.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" style="text-align:left;" styleClass="row2">
                        <h:outputLabel value="Account No :" styleClass="label"/>
                        <h:inputText id="txtAccountNo" styleClass="input" style="width:100px" value="#{RdReschedule.accountNo}" maxlength="#{RdReschedule.acNoMaxLen}">
                            <a4j:support event="onblur" actionListener="#{RdReschedule.onBlurAccountNumber}" reRender="summaryPanel,errmsg,newAcNo,btnSave,table" oncomplete="setMask();" focus="summaryPanel"/>
                        </h:inputText>
                        <h:outputText id="newAcNo" value="#{RdReschedule.newAcNo}" styleClass="output" style="color:green"/>
                        <h:outputLabel/>                                                
                    </h:panelGrid>                              
                    <rich:panel id="summaryPanel" header="" headerClass="rich-header">
                        <h:panelGrid style="width: 100%;">
                            <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" style="text-align:left;" styleClass="row1">
                                <h:outputLabel id="lblCustName" value="Customer Name" styleClass="label"/>
                                <h:outputText id="stxtCustName" value="#{RdReschedule.custName}" styleClass="output"/>                                
                                <h:outputLabel id="lblRoi" value="ROI" styleClass="label"/>
                                <h:inputText id="stxtRoi" value="#{RdReschedule.roi}" styleClass="input" style="width:120px" disabled="#{RdReschedule.roiDisb}"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" style="text-align:left;" styleClass="row2">
                                <h:outputLabel id="lblOpDate" value="Opening Date" styleClass="label"/>
                                <h:outputText id="stxtOpDate" value="#{RdReschedule.openingDate}" styleClass="output"/>
                                <h:outputLabel id="lblInst" value="Installment" styleClass="label"/>
                                <h:inputText id="stxtInst" value="#{RdReschedule.installment}" styleClass="input" style="width:120px" disabled="#{RdReschedule.installmentDisb}"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" style="text-align:left;" styleClass="row1">
                                <h:outputLabel id="lblPrd" value="Period" styleClass="label"/>
                                <h:inputText id="stxtPrd" value="#{RdReschedule.period}" styleClass="input" style="width:120px" disabled="#{RdReschedule.periodDisb}"/>
                                <h:outputLabel/>
                                <h:outputLabel/>
                            </h:panelGrid>
                        </h:panelGrid>
                    </rich:panel>
                </h:panelGrid>
                <h:panelGrid columnClasses="vtop" columns="1" id="table" style="height:auto;" styleClass="row2" width="100%">
                    <a4j:region>
                        <rich:dataTable value="#{RdReschedule.gridDetail}" var="dataItem"
                                        rowClasses="row1,row2" id="taskList" rows="10" columnsWidth="100" rowKeyVar="row"
                                        onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                        onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                            <f:facet name="header">
                                <rich:columnGroup> 
                                    <rich:column colspan="12"><h:outputText value="Rd Reschedule Detail"/></rich:column>
                                    <rich:column breakBefore="true"><h:outputText value="S.NO:" /></rich:column>
                                    <rich:column><h:outputText value="DueDt:" /></rich:column>
                                    <rich:column><h:outputText value="Installamt:" /></rich:column>
                                    <rich:column><h:outputText value="Status:" /></rich:column>
                                    <rich:column><h:outputText value="PaymentDt:" /></rich:column>
                                    <rich:column><h:outputText value="EnterBy:" /></rich:column>                                    
                                </rich:columnGroup>
                            </f:facet>                              
                            <rich:column><h:outputText value="#{dataItem.sno}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.dueDt}" /></rich:column>              
                            <rich:column><h:outputText value="#{dataItem.installment}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.status}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.paymentDt}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.enterBy}" /></rich:column>                            
                        </rich:dataTable>
                        <rich:datascroller id="tList" align="left" for="taskList" maxPages="20" />
                    </a4j:region>
                </h:panelGrid>
                <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                    <h:panelGroup layout="block" style="width:100%;text-align:center;">
                        <a4j:commandButton id="btnSave" tabindex="10" value="Save" oncomplete="#{rich:component('savePanel')}.show()" reRender="summaryPanel,table,errmsg,gpFooter,tList,taskList,btnSave" disabled="#{RdReschedule.saveDisb}"/>
                        <a4j:commandButton id="btnRefresh" value="Refresh" action="#{RdReschedule.refreshForm}" reRender="summaryPanel,table,errmsg,gpFooter,txtAccountNo,newAcNo" focus="btnExit"/>
                        <a4j:commandButton id="btnExit" value="Exit" action="#{RdReschedule.exitBtnAction}" reRender="message,errorMessage"/>
                    </h:panelGroup>
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
                                    <a4j:commandButton value="Yes" id="btnYes1" action="#{RdReschedule.saveMasterDetail}"  oncomplete="#{rich:component('savePanel')}.hide();return false;" reRender="summaryPanel,taskList,errmsg,tList,btnSave"/>

                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo1" onclick="#{rich:component('savePanel')}.hide();return false;"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>