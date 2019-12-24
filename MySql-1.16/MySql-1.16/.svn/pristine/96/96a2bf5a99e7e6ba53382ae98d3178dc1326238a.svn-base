<%-- 
    Document   : EPSNodalScreen
    Created on : Jun 27, 2011, 2:15:38 PM
    Author     : root
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Nodal Screen</title>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="gridPanel1" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{EPSNodalScreen.currentDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Nodal Screen"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{EPSNodalScreen.loggedInUser}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel001" style="width:100%;height:30px;text-align:center;border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                        <h:outputText id="stxtMsg" styleClass="error" value="#{EPSNodalScreen.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="PanelGrid1" columns="4" columnClasses="col2,col7,col2,col7" style="height:30px;border:1px ridge #BED6F8;padding-left:120px"  styleClass="row1" width="100%">
                        <h:outputLabel id="lblPaySysId" styleClass="label" value="PaySysID" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddPaySysId" styleClass="ddlist" size="1" style="width:80px" value="#{EPSNodalScreen.paySysId}">
                            <a4j:support action="#{EPSNodalScreen.getMessageStatus}" event="onblur" reRender="ddMsgStatus,stxtMsg" oncomplete="#{rich:element('ddMsgStatus')}.focus();"/>
                            <f:selectItems value="#{EPSNodalScreen.paySysIdList}" />
                        </h:selectOneListbox>
                        <h:outputLabel id="lblMsgStatus" styleClass="label" value="MsgStatus" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddMsgStatus" styleClass="ddlist" size="1" style="width:90px" value="#{EPSNodalScreen.msgStatus}" disabled="#{EPSNodalScreen.msgStatusFlag}">
                            <a4j:support action="#{EPSNodalScreen.getReturnData}" event="onblur" reRender="txnGrid,txnList,stxtMsg,btnSubmit"/>
                            <f:selectItems value="#{EPSNodalScreen.msgStatusList}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="txnGrid" width="100%" style="background-color:#edebeb;height:200px;" columnClasses="vtop">
                        <rich:dataTable  var="unAuthItem" value="#{EPSNodalScreen.unAuthList}"
                                         rowClasses="gridrow1, gridrow2" id="txnList" rows="5"
                                         onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                         onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                         width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="8"><h:outputText value="Message List" /></rich:column>
                                    <rich:column breakBefore="true"><h:outputText value="Sr.No." /></rich:column>
                                    <rich:column><h:outputText value="MsgType" /></rich:column>
                                    <rich:column><h:outputText value="SubMsgType" /></rich:column>
                                    <rich:column><h:outputText value="SenderIFSC" /></rich:column>
                                    <rich:column><h:outputText value="ReceiverIFSC" /></rich:column>
                                    <rich:column><h:outputText value="UTR" /></rich:column>
                                    <rich:column><h:outputText value="Amount" /></rich:column>
                                    <rich:column><h:outputText value="Beneficiary A/c" /></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{unAuthItem.sNo}"/></rich:column>
                            <rich:column><h:outputText value="#{unAuthItem.msgType}"/></rich:column>
                            <rich:column><h:outputText value="#{unAuthItem.subMsgType}"/></rich:column>
                            <rich:column><h:outputText value="#{unAuthItem.senderIFSC}"/></rich:column>
                            <rich:column><h:outputText value="#{unAuthItem.receiverIFSC}"/></rich:column>
                            <rich:column><h:outputText value="#{unAuthItem.utr}"/></rich:column>
                            <rich:column>
                                <h:outputText value="#{unAuthItem.amount}">
                                    <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                                </h:outputText>
                            </rich:column>
                            <rich:column><h:outputText value="#{unAuthItem.beneficiaryAcc}"/></rich:column>
                        </rich:dataTable>
                        <rich:datascroller id="unauthDataScroll"align="left" for="txnList" maxPages="20" />
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup  style="width:100%;text-align:center;" styleClass="vtop">
                            <a4j:commandButton disabled="#{EPSNodalScreen.submitButtonFlag}" id="btnSubmit" value="Submit" action="#{EPSNodalScreen.processAction}" reRender="mainPanel"></a4j:commandButton>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{EPSNodalScreen.refreshForm}" reRender="mainPanel"></a4j:commandButton>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{EPSNodalScreen.exitForm}" reRender="mainPanel"></a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
