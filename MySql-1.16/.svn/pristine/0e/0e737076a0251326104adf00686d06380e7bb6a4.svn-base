<%--
    Document   : NpciInputDetail
    Created on : Feb 1, 2016, 12:56:06 PM
    Author     : Administrator
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
            <title>Npci Input Details</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
        </head>
        <body>
            <a4j:form id="npciInputForm">
                <h:panelGrid bgcolor="#fff" columns="1" id="outerPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="headerPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{NpciInputDetail.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Npci Input Details"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{NpciInputDetail.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="mainPanel" style="width:100%;text-align:center;">
                        <h:panelGrid columnClasses="col2,col7" columns="2" id="msgRow" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputLabel id="lblMsg" styleClass="error" value="#{NpciInputDetail.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel1" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblFuntion" styleClass="label" value="Function" style="display:"/>
                            <h:selectOneListbox id="ddFunction" styleClass="ddlist" size="1" style="width:80px;" value="#{NpciInputDetail.function}" >
                                <f:selectItems value="#{NpciInputDetail.functionList}"/>
                                <a4j:support event="onblur" action="#{NpciInputDetail.funcAction}" oncomplete="if(#{rich:element('ddFunction')}.value=='A'){#{rich:element('txtMicr')}.focus();}
                                             else if((#{rich:element('ddFunction')}.value=='M') || (#{rich:element('ddFunction')}.value=='D')){#{rich:element('ddType')}.focus();}"
                                             reRender="lblMsg,txtMicr,txtAcType,txtAcNo,txtName,txtAmt,ddType,npciPanelGrid,btnLabel,
                                             postPanel,txtOwnAcNo"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="labelMicr" styleClass="label" value="Dest. Micr "/>
                            <h:inputText id="txtMicr" styleClass="input" value="#{NpciInputDetail.micr}" maxlength="9" size="10"/>
                            <h:outputLabel id="labelAcType" styleClass="label" value="Dest. A/c Type "/>
                            <h:inputText id="txtAcType" styleClass="input" value="#{NpciInputDetail.acType}" maxlength="2"  size="10"/>                                                        
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel2" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="labelAcNo" styleClass="label" value="Dest. A/c No. "/>
                            <h:inputText id="txtAcNo" styleClass="input" value="#{NpciInputDetail.acNo}" maxlength="15"  size="20" onkeyup="this.value=this.value.toUpperCase();"/>
                            <h:outputLabel id="labelName" styleClass="label" value="Dest. Beneficiary Name "/>
                            <h:inputText id="txtName" styleClass="input" value="#{NpciInputDetail.name}" maxlength="40" onkeyup="this.value = this.value.toUpperCase();"/>
                            <h:outputLabel id="labelAmt" styleClass="label" value="Amount "/>
                            <h:inputText id="txtAmt" styleClass="input" value="#{NpciInputDetail.amt}" maxlength="13" size="10"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel3" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="labelOwnAcNo" styleClass="label" value="Own Bank. A/c No. "/>
                            <h:panelGroup layout="block" style="width:100%;text-align:left;">
                                <h:inputText id="txtOwnAcNo" styleClass="input" value="#{NpciInputDetail.accountNo}" maxlength="#{NpciInputDetail.acNoMaxLen}" size="#{NpciInputDetail.acNoMaxLen}">
                                    <a4j:support event="onblur" action="#{NpciInputDetail.retrieveValidAcno}" reRender="stxtAccNo,lblMsg"/>
                                </h:inputText>
                                <h:outputText id="stxtAccNo" styleClass="output" value="#{NpciInputDetail.ownAcNo}"/>
                            </h:panelGroup>                            
                            <h:outputLabel id="lblType" styleClass="label" value="Transaction Type" style="display:"/>
                            <h:selectOneListbox id="ddType" styleClass="ddlist" size="1" style="width:80px;" value="#{NpciInputDetail.type}" disabled="#{NpciInputDetail.disableType}">
                                <f:selectItems value="#{NpciInputDetail.typeList}"/>
                                <a4j:support action="#{NpciInputDetail.gridDetail}" event="onblur" reRender="lblMsg,gridStyle,npciPanelGrid"/>
                            </h:selectOneListbox>
                            <h:outputLabel/>
                            <h:outputLabel/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col1,col2" columns="1" id="npciPanelGrid" width="100%" styleClass="row1" style="display:#{NpciInputDetail.gridStyle};height:50%">
                        <a4j:region>
                            <rich:dataTable value ="#{NpciInputDetail.tableList}"
                                            rowClasses="row1, row2" id = "taskList" rows="6" columnsWidth="100" rowKeyVar="row" var ="dataItem"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="8"><h:outputText value="Npci Input Details"/></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Unique Ref No"/> </rich:column>
                                        <rich:column><h:outputText value="Micr" /></rich:column>
                                        <rich:column><h:outputText value="A/c Type" /></rich:column>
                                        <rich:column><h:outputText value="A/c No" /></rich:column>
                                        <rich:column><h:outputText value="Name" /></rich:column>
                                        <rich:column><h:outputText value="Amount" /></rich:column>
                                        <rich:column><h:outputText value="Own Bank A/c No" /></rich:column>
                                        <rich:column><h:outputText value="Select" /></rich:column>                                        
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.uRefNo}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.micr}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.acType}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.acNo}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.name}"/></rich:column>
                                <rich:column style="text-align:right;"><h:outputText value="#{dataItem.amount}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.ownAcno}"/></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{NpciInputDetail.processData}" reRender="lblMsg,ddFunction,txtMicr,txtAcType,txtAcNo,txtName,txtAmt,ddType,npciPanelGrid,btnLabel,postPanel,txtOwnAcNo">
                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{NpciInputDetail.currentItem}"/>                                    
                                    </a4j:commandLink>
                                </rich:column>                                
                            </rich:dataTable>
                        </a4j:region>
                        <rich:datascroller align="left" for="taskList" maxPages="20"/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnLabel" value="#{NpciInputDetail.btnLbl}" onclick="#{rich:component('postPanel')}.show()" disabled="#{NpciInputDetail.disableBtn}"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{NpciInputDetail.refresh}" reRender="lblMsg,ddFunction,txtMicr,txtAcType,txtAcNo,txtName,txtAmt,ddType,npciPanelGrid,btnLabel,postPanel,txtOwnAcNo,gridPanel3"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{NpciInputDetail.exitForm}" reRender="lblMsg"/>                            
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="postPanel" autosized="true" width="200" onshow="#{rich:element('btnYes')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirm Dialog ?" style="padding-right:15px;" />
                </f:facet>
                <a4j:region id="postRegion">
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px;">
                                    <td colspan="2">
                                        <h:outputText value="#{NpciInputDetail.btnPopUp}" styleClass="output"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="btnYes" ajaxSingle="true" action="#{NpciInputDetail.Post}"
                                                           onclick="#{rich:component('postPanel')}.hide();" reRender="lblMsg,ddFunction,txtMicr,txtAcType,txtAcNo,txtName,txtAmt,ddType,npciPanelGrid,btnLabel,postPanel,txtOwnAcNo"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Cancel"  ajaxSingle="true" onclick="#{rich:component('postPanel')}.hide();return false;"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </a4j:region>
            </rich:modalPanel>            
        </body>
    </html>
</f:view>