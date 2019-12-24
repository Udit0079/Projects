<%--
    Document   : InoperativeCharges
    Created on : 11 Aug, 2010, 7:23:06 PM
    Author     : root
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
            <title>Inoperative Charges</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="txnForm">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{InoperativeCharges.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Inoperative Charges"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{InoperativeCharges.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <h:panelGrid columnClasses="col2,col7" columns="2" id="msgRow" style="width:100%;text-align:center;" styleClass="row1">
                    <h:outputText id="stxtMessage" styleClass="error" value="#{InoperativeCharges.message}" style="text-align:right;"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col1" columns="1" id="bodyPanel" style="width:100%;text-align:center;">
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="2" id="row1" style="width:100%;text-align:center;" styleClass="row2">
                        <h:panelGroup id="AcTypePanel" >
                            <h:outputLabel id="AcType" styleClass="headerLabel" value="A/c Type :"><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddAcType" styleClass="ddlist" style="width:80px" size ="1" value ="#{InoperativeCharges.acType}">
                                    <a4j:support action="#{InoperativeCharges.setAccountToCredit}" event="onchange"
                                                 reRender="stxtCrAcct,gridPanel103,stxtCrAmt,stxtdrAmt,poBtnCalculate,poBtnPost,stxtMessage,fromDt,toDt1" focus="lblFromDate"/>
                                    <f:selectItems value ="#{InoperativeCharges.acctNo}"/>
                                </h:selectOneListbox>
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9"  columns="2" id="subbodyPanel2" style="width:100%;">
                        <h:panelGrid id="leftPanel1" columns="1" width="100%" >
                            <h:panelGrid columnClasses="col1,col2" columns="2" id="FramPanel" style="width:100%;" styleClass="row1">
                                <h:outputLabel id="lblFromDate" styleClass="label"  value="From Date :"><font class="required" color="red">*</font></h:outputLabel>
                                <rich:calendar datePattern="dd/MM/yyyy" id="fromDt" value="#{InoperativeCharges.fromDate}" readonly="true" inputSize="10">
                                </rich:calendar>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col1,col2" columns="2" id="Panel2" style="width:100%;" styleClass="row2">
                                <h:outputLabel id="label9" styleClass="label"  value="A/c To Be Debited"/>
                                <h:outputText id ="stxtdrAcct" styleClass="label" value ="As Per List In The Report"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col1,col2" columns="2" id="Panel3" style="width:100%;" styleClass="row1">
                                <h:outputLabel id="label11" styleClass="label"  value="A/c To Be Credited"/>
                                <h:outputText id ="stxtCrAcct" styleClass="label" value = "#{InoperativeCharges.amtToCrd}"/>
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid id="rightPanel1" columns="1" width="100%">
                            <h:panelGrid columnClasses="col1,col2" columns="2" id="ToPanel" style="width:100%;" styleClass="row1">
                                <h:outputLabel id="lblToDate" styleClass="label"  value="To Date :"><font class="required" color="red">*</font></h:outputLabel>
                                <rich:calendar datePattern="dd/MM/yyyy" id="toDt1" value="#{InoperativeCharges.toDate}" readonly="true" inputSize="10">
                                </rich:calendar>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col1,col2" columns="2" id="Panel12" style="width:100%;" styleClass="row2">
                                <h:outputLabel id="label10" styleClass="label"  value="Debit Amount"/>
                                <h:outputText id ="stxtdrAmt" styleClass="label" value ="#{InoperativeCharges.crAmt}"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col1,col2" columns="2" id="Panel13" style="width:100%;" styleClass="row1">
                                <h:outputLabel id="label12" styleClass="label"  value="Credit Amount"/>
                                <h:outputText id ="stxtCrAmt" styleClass="label" value ="#{InoperativeCharges.drAmt}"/>
                            </h:panelGrid>
                        </h:panelGrid>
                    </h:panelGrid>

                <h:panelGrid columnClasses="col7" columns="1" id="gridPanel103" width="100%" styleClass="row2">
                    <rich:contextMenu attached="false" id="menu" submitMode="ajax" oncollapse="row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'">
                        <rich:menuItem value="post Record" ajaxSingle="true" oncomplete="#{rich:component('postPanel')}.show()">
                        </rich:menuItem>
                    </rich:contextMenu>
                    <a4j:region>
                        <rich:dataTable value ="#{InoperativeCharges.instCharges}"
                                        rowClasses="row1, row2" id="taskList" rows="6" columnsWidth="100" rowKeyVar="row" var ="item"
                                        onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                        onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="5"></rich:column>
                                    <rich:column breakBefore="true"><h:outputText value="S No" /> </rich:column>
                                    <rich:column><h:outputText value="Account No" /></rich:column>
                                    <rich:column><h:outputText value="Customer Name" /></rich:column>
                                    <rich:column><h:outputText value="Penalty" /></rich:column>
                                    <rich:column><h:outputText value="Status" /></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{item.sno}"/></rich:column>
                            <rich:column><h:outputText value="#{item.accountNo}"/></rich:column>
                            <rich:column><h:outputText value="#{item.customerName}"/></rich:column>
                            <rich:column >
                                <h:outputText value="#{item.penalty}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                            </rich:column>
                            <rich:column ><h:outputText value="#{item.status}"/></rich:column>
                        </rich:dataTable>
                        <rich:datascroller id="taskListScr" align="left" for="taskList" maxPages="10" />
                    </a4j:region>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
                <rich:modalPanel id="postPanel" autosized="true" width="200" onshow="#{rich:element('btnYes')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />

                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td align="center" width="50%" colspan="2">
                                        <h:outputText value="Are You Sure To Post Data?" style="padding-right:15px;" />
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id = "btnYes" ajaxSingle="true" action="#{InoperativeCharges.postBtn}"
                                                           onclick="#{rich:component('postPanel')}.hide();" reRender="stxtMessage,poBtnPost,ToPanel"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Cancel" id = "btnNo" ajaxSingle="true" onclick="#{rich:component('postPanel')}.hide();return false;" />
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
                <h:panelGrid id="poFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                    <h:panelGroup id="poBtnPanel">
                        <a4j:commandButton id="poBtnCalculate" action="#{InoperativeCharges.calculateBtn}" value="Calculate" disabled="#{InoperativeCharges.calEnable}" reRender="row1,FramPanel,ToPanel,Panel12,Panel13,Panel2,Panel3,taskList,taskListScr,msgRow,poFooterPanel"/>
                        <a4j:commandButton id="poBtnPost" value="Post" disabled="#{InoperativeCharges.postEnable}" onclick="#{rich:component('postPanel')}.show()"/>
                        <a4j:commandButton id="btnRefresh" value="Refresh" action="#{InoperativeCharges.refreshPage}" reRender="row1,FramPanel,ToPanel,Panel12,Panel13,Panel2,Panel3,taskList,taskListScr,msgRow,poFooterPanel,ddAcType,fromDt,toDt1"/>
                        <a4j:commandButton id="poBtnExit" value="Exit" action="#{InoperativeCharges.exitForm}"/>
                    </h:panelGroup>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>