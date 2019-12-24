<%-- 
    Document   : AgentRdCommission
    Created on : Sep 11, 2018, 2:29:52 PM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Agent-RD Commission</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
        </head>
        <body>
            <a4j:form id="agentRdCommForm">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{AgentRdComm.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblAgentComm" styleClass="headerLabel" value="Agent-RD Commission"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{AgentRdComm.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col9" columns="1" id="Panel51" style="width:100%;text-align:center;" styleClass="row2">
                        <h:outputText id="stxtMsg" styleClass="error" value="#{AgentRdComm.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="Panel0" style="width:100%;" styleClass="row1">
                        <h:outputLabel id="lblBranchWise" styleClass="label" value="Branch : "><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddBranch" styleClass="ddlist" size="1" style="width:100px;" value="#{AgentRdComm.branchOpt}">
                            <f:selectItems value="#{AgentRdComm.branchOptionList}"/>
                            <a4j:support action="#{AgentRdComm.setDates}" event="onblur" focus="btnCalculation"
                                                  reRender="stxtMsg,fromDt,toDt,grid,cal,save"/>
                        </h:selectOneListbox>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="Panel1" style="width:100%;" styleClass="row2">
                        <h:outputLabel id="lblFromDate" styleClass="label"  value="From Date :"/>
                        <rich:calendar datePattern="dd/MM/yyyy" id="fromDt" value="#{AgentRdComm.fromDate}" inputSize="10" disabled="true"/>
                        <h:outputLabel id="lblToDate" styleClass="label"  value="To Date :"/>
                        <rich:calendar datePattern="dd/MM/yyyy" id="toDt" value="#{AgentRdComm.toDate}" inputSize="10" disabled="true"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="Panel2" style="width:100%;" styleClass="row2">
                        <h:outputLabel id="lblAcctToBeCredited" styleClass="label"  value="A/c To Be Credited :"/>
                        <h:outputText id="acctDebit" styleClass="output" value="#{AgentRdComm.accoutToCredited}"/>
                        <h:outputLabel id="lblCreditAmount" styleClass="label"  value="Credit Amount :"/>
                        <h:outputText id="txtdrAmt" style="width:110px" value="#{AgentRdComm.creditAmount}" styleClass="input">
                            <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                        </h:outputText>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col7" columns="1" id="gridPanel103" width="100%" styleClass="row2">
                        <rich:modalPanel id="postPanel" autosized="true" width="250" onshow="#{rich:element('No')}.focus()">
                            <f:facet name="header">
                                <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                            </f:facet>
                            <h:form>
                                <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                    <tbody>
                                        <tr style="height:40px">
                                            <td colspan="2">
                                                <h:outputText value="Are You Sure To Post Data?"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="Yes" id="yes" ajaxSingle="true" action="#{AgentRdComm.postBtnAction}"  
                                                                   oncomplete="#{rich:component('postPanel')}.hide();" reRender="stxtMsg,btnPost"/>
                                            </td>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="No" id="No" onclick="#{rich:component('postPanel')}.hide();return false;" />
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </h:form>
                        </rich:modalPanel>
                    </h:panelGrid>   
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="BtnPanel">
                            <a4j:commandButton  id="btnCalculation" disabled="#{AgentRdComm.calDisable}" value="Calculation" oncomplete="if(#{AgentRdComm.calcFlag==true}){
                                                #{rich:component('popUpRepPanel')}.show();
                                                }
                                                else if(#{AgentRdComm.calcFlag==false})
                                                {
                                                #{rich:component('popUpRepPanel')}.hide();
                                                }"  action="#{AgentRdComm.calculateBtnAction}" focus="btnPost" reRender="stxtMsg,gridPanel103,txtdrAmt,txtcrAmt,btnPost,acctDebit,txtcrAcct,btnCalculation,txtcrAcct,popUpRepPanel" />                           
                            <a4j:commandButton id="btnPost" disabled="#{AgentRdComm.postDisable}" value="Post"  oncomplete="#{rich:component('postPanel')}.show()" reRender="stxtMsg,btnCalculation,btnPost" />
                            <a4j:commandButton  id="btnRefresh" value="Refresh" action="#{AgentRdComm.resetValue}" focus="ddAccountType" reRender="mainPanel" />
                            <a4j:commandButton id="btnExit" value="Exit" action="#{AgentRdComm.btnExit}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>    
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
            </a4j:form>                       
            <rich:modalPanel id="popUpRepPanel" label="Form" width="1000" height="550" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpRepPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Agent-RD Commission" style="text-align:center;"/>
                    </h:panelGrid>
                </f:facet>
                <f:facet name="controls">
                    <h:outputLink  id="closelink" onclick="#{rich:component('popUpRepPanel')}.hide()">
                        <h:graphicImage value="/resources/images/close.gif" style="border:0" />
                    </h:outputLink>
                </f:facet>  
                <table width="100%">
                    <tbody>
                        <tr>
                            <td>
                                <a4j:include viewId="#{AgentRdComm.viewID}" />
                            </td>
                        </tr>
                    </tbody>
                </table>
            </rich:modalPanel>            
        </body>
    </html>
</f:view>
