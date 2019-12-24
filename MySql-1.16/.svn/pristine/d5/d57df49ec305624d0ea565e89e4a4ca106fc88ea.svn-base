<%-- 
    Document   : fixedchargeposting
    Created on : 25 Feb, 2014, 11:26:59 AM
    Author     : Dinesh Pratap Singh
    This form will operate on branch level not on Ho.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Charge Master</title>
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
            <a4j:form>
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <a4j:region id="btnAjaxGrid">
                        <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                            <h:panelGroup id="groupPanel2" layout="block">
                                <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                                <h:outputText id="stxtDate" styleClass="output" value="#{fixedChargePosting.todayDate}"/>
                            </h:panelGroup>
                            <h:outputLabel id="label2" styleClass="headerLabel" value="Charge Posting"/>
                            <h:panelGroup id="groupPanel3" layout="block">
                                <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                                <h:outputText id="stxtUser" styleClass="output" value="#{fixedChargePosting.userName}"/>
                            </h:panelGroup>                         
                        </h:panelGrid>
                        <h:panelGrid id="inputPanel" columnClasses="col13,col13,col13,col13" columns="4" styleClass="row1" style="width:100%;text-align:left;">
                            <h:outputLabel id="lblType" styleClass="label" value="Type"/>
                            <h:selectOneListbox id="rDDType" value="#{fixedChargePosting.type}" style="ddstyle" size="1">
                                <f:selectItem itemLabel="--SELECT--" itemValue="0"/>
                                <f:selectItem itemLabel="ALL" itemValue="A"/>
                                <f:selectItem itemLabel="INDIVIDUAL" itemValue="I"/>
                                <a4j:support event="onblur" action="#{fixedChargePosting.onBlurOfType}" reRender="msg,txtAccountNo,ddNature,ddType,txtFrDt,txtToDt,update" focus="#{fixedChargePosting.focusId}" oncomplete="setMask();"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblAccountNo" value="Account No." styleClass="label"/>
                            <h:inputText id="txtAccountNo" styleClass="input" value="#{fixedChargePosting.accountNo}" maxlength="12" size="12" disabled="#{fixedChargePosting.disableAccountNo}" onkeyup="this.value=this.value.toUpperCase();">
                                <a4j:support event="onblur" action="#{fixedChargePosting.accountFieldAction}" reRender="msg,ddNature,ddType,txtFrDt,txtToDt,update" focus="update" oncomplete="setMsk();"/> 
                            </h:inputText>   
                        </h:panelGrid>
                        <h:panelGrid id="naturePanel" columnClasses="col13,col13,col13,col13" columns="4" styleClass="row2" style="width:100%;text-align:left;">
                            <h:outputLabel id="lblNature" styleClass="label" value="Nature"/>
                            <h:selectOneListbox id="ddNature" value="#{fixedChargePosting.nature}" style="ddlist" size="1" disabled="#{fixedChargePosting.disableNature}">
                                <f:selectItems value="#{fixedChargePosting.natureList}"/>
                                <a4j:support event="onblur" action="#{fixedChargePosting.onBlurNature}" reRender="msg,ddType,txtFrDt,txtToDt,update" oncomplete="setMsk();" focus="#{fixedChargePosting.focusId}"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblAcType" styleClass="label" value="A/c Type"/>
                            <h:selectOneListbox id="ddType" value="#{fixedChargePosting.acType}" style="ddlist" size="1" disabled="#{fixedChargePosting.disableAcType}">
                                <f:selectItems value="#{fixedChargePosting.acTypeList}"/>
                                <a4j:support event="onblur" action="#{fixedChargePosting.onBlurAcType}" reRender="msg,txtFrDt,txtToDt,update" focus="update"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid id="inputDatePanel" columnClasses="col13,col13,col13,col13" columns="4" styleClass="row1" style="width:100%;text-align:left;">
                            <h:outputLabel id="lblFromDate" styleClass="label" value="From Date"/>
                            <h:inputText id="txtFrDt" styleClass="input issueDt" value="#{fixedChargePosting.frDt}" size="10" disabled="#{fixedChargePosting.dtEnable}">
                                <a4j:support event="onblur" reRender="msg" oncomplete="setMask();"/>
                            </h:inputText>
                            <h:outputLabel id="lblToDate" styleClass="label" value="To Date"/>
                            <h:inputText  id="txtToDt" styleClass="input issueDt" value="#{fixedChargePosting.toDt}" size="10"  disabled="#{fixedChargePosting.toDtEnable}">
                                <a4j:support event="onblur" reRender="msg" oncomplete="setMask();"/>
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid id="inputLabelPanel" columnClasses="col13,col13,col13,col13" columns="4" styleClass="row2" style="width:100%;text-align:left;">
                            <h:outputLabel id="lblTotCharge" styleClass="label" value="Total Charge"/>
                            <h:outputText id="stxtTotCharge" styleClass="output" value="#{fixedChargePosting.totalChg}"/>
                            <h:outputLabel id="lblCrHead" styleClass="label" value="Credit Head"/>
                            <h:outputText  id="stxtCrHead" styleClass="output" value="#{fixedChargePosting.crHead}"/>
                        </h:panelGrid>
                        <h:panelGrid id="fixedPanelGrid" style="width:100%;display:#{fixedChargePosting.showFixedPanel}"> 
                            <rich:dataTable id="grid" value="#{fixedChargePosting.chargePostingList}" var="dataItem" rows="10" width="100%" onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" rowClasses="gridrow1,gridrow2">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column width=""><h:outputText value="S.No." /></rich:column>
                                        <rich:column><h:outputText value="Account no." /></rich:column>
                                        <rich:column><h:outputText value="Billing Account no." /></rich:column>
                                        <rich:column><h:outputText value="From Date" /></rich:column>
                                        <rich:column><h:outputText value="To Date" /></rich:column>
                                        <rich:column><h:outputText value="Charges"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.sno}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.acno}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.billingAcno}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.fromDate}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.toDate}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.charges}"/></rich:column>
                            </rich:dataTable>
                            <rich:datascroller for="grid" maxPages="50" style="text-align:left"/>
                        </h:panelGrid> 
                        
                        <h:panelGrid id="smsBasisGrid" style="width:100%;display:#{fixedChargePosting.showPerSMSPanel}"> 
                            <rich:dataTable id="smsGrid" value="#{fixedChargePosting.chargePostingList}" var="dataItem" rows="10" width="100%" onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" rowClasses="gridrow1,gridrow2">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column width=""><h:outputText value="S.No." /></rich:column>
                                        <rich:column><h:outputText value="Account no." /></rich:column>
                                        <rich:column><h:outputText value="Billing Account no." /></rich:column>
                                        <rich:column><h:outputText value="No of Message" /></rich:column>
                                        <rich:column><h:outputText value="Charges"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.sno}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.acno}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.billingAcno}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.noOfMsg}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.charges}"/></rich:column>
                            </rich:dataTable>
                            <rich:datascroller for="smsGrid" maxPages="50" style="text-align:left"/>
                        </h:panelGrid>
                        <rich:modalPanel id="savePanel" autosized="true" width="250" onshow="#{rich:element('btnSaveYes')}.focus();">
                            <f:facet name="header">
                                <h:outputText value="Confirmation Dialog" />
                            </f:facet>
                            <h:form>
                                <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                    <tbody>
                                        <tr style="height:40px">
                                            <td colspan="2">
                                                <h:outputText value="Are you sure to post charges ?"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="Yes" id="btnSaveYes" ajaxSingle="true" action="#{fixedChargePosting.postAction}" oncomplete="#{rich:component('savePanel')}.hide();" reRender="mainPanel"/>
                                            </td>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="No" id="btnSaveNo" onclick="#{rich:component('savePanel')}.hide();"/>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </h:form>
                        </rich:modalPanel>
                        <h:panelGrid columns="2" columnClasses="col10,col11" style="text-align:left;width:100%" width="100%" styleClass="footer" >
                            <h:outputText id="msg" styleClass="error" value="#{fixedChargePosting.msg}"/>
                            <h:panelGroup id="btnGroupPanel" >
                                <a4j:commandButton id="update" value="Calculate" actionListener="#{fixedChargePosting.calculateAction}" reRender="mainPanel" disabled="#{fixedChargePosting.btnCalFlag}"/>
                                <a4j:commandButton id="save" value="Post" oncomplete="#{rich:component('savePanel')}.show();" reRender="savePanel" disabled="#{fixedChargePosting.btnPostFlag}"/>
                                <a4j:commandButton id="refresh" value="Refresh" action="#{fixedChargePosting.refreshForm}"  reRender="mainPanel" focus="rDDType" oncomplete="setMask();"/>
                                <a4j:commandButton id="poBtnCancel" value="Exit"  action="#{fixedChargePosting.exitForm}" reRender="mainPanel"/>
                            </h:panelGroup>
                        </h:panelGrid>
                    </a4j:region>
                </h:panelGrid>

                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="btnAjaxGrid"/>
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
            </a4j:form>
        </body>
    </html>
</f:view>
