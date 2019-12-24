<%-- 
    Document   : SecurityCloseAuth
    Created on : Oct 23, 2017, 12:19:55 PM
    Author     : user
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>Authorize Security Close</title>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header" width="100%">
                        <h:panelGroup id="groupPanel" layout="block" >
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date:" />
                            <h:outputText id="stxtDate" styleClass="output" value="#{SecurityCloseAuth.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblincident" styleClass="headerLabel" value="Authorization Of Close Security"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lbluser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{SecurityCloseAuth.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="1" id="lpg" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                        <h:outputText id="message" styleClass="msg" value="#{SecurityCloseAuth.message}"/>
                    </h:panelGrid>                    
                    <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="gridPanel15" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblSecurityType" styleClass="label" value="Security Type"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddSecurityType" style="width:120px" styleClass="ddlist"  value="#{SecurityCloseAuth.securityType}" size="1">
                            <f:selectItems value="#{SecurityCloseAuth.securityTypeList}" />
                            <a4j:support action="#{SecurityCloseAuth.getPedingControl}" event="onblur" reRender="message,ddCtrlNo"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="labelAcno" styleClass="label" value="Control No"/>
                        <h:selectOneListbox id="ddCtrlNo" styleClass="ddlist" size="1" style="width:100px" value="#{SecurityCloseAuth.ctrlNo}" tabindex="1">
                            <f:selectItem itemValue="--SELECT--"/>
                            <f:selectItems value="#{SecurityCloseAuth.pendingCtrlNoList}" />
                            <a4j:support actionListener="#{SecurityCloseAuth.getList}" event="onblur" reRender="taskList,taskList1,message" limitToList="true"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="a6" style="height:auto;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{SecurityCloseAuth.secFirst}" var="dataItem" rowClasses="gridrow1,gridrow2" id="taskList" rows="6" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="8"><h:outputText value="" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Control No"/></rich:column>
                                        <rich:column><h:outputText value="Security Type" /></rich:column>
                                        <rich:column><h:outputText value="Security Detail" /></rich:column>
                                        <rich:column><h:outputText value="Seller Name" /></rich:column>
                                        <rich:column><h:outputText value="Face Value" /></rich:column>
                                        <rich:column><h:outputText value="Book Value" /></rich:column>
                                        <rich:column><h:outputText value="Maturity Date" /></rich:column>
                                        <rich:column><h:outputText value="Accrued Interest" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.ctrlNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.secTp}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.secDetail}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.sellerName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.faceValue}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.bookValue}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.matDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.accrInt}" /></rich:column>
                            </rich:dataTable>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="a7" style="height:auto;" styleClass="row1" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{SecurityCloseAuth.secFirst}" var="dataItem" align="center" rowClasses="gridrow1,gridrow2" id="taskList1" rows="7" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="9"><h:outputText value="" /></rich:column>
                                        <rich:column breakBefore="true"> <h:outputText value="Amortization Value"/></rich:column>
                                        <rich:column><h:outputText value="Balance Interest"/></rich:column>
                                        <rich:column><h:outputText value="Issuing Price"/></rich:column>
                                        <rich:column><h:outputText value="Seling Status"/></rich:column>
                                        <rich:column><h:outputText value="Seling Amount"/></rich:column>
                                        <rich:column><h:outputText value="Seling Date"/></rich:column>
                                        <rich:column><h:outputText value="GL Head"/></rich:column>
                                        <rich:column><h:outputText value="GL Name"/></rich:column>
                                        <rich:column><h:outputText value="Branch"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.amortVal}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.balInt}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.issuePrice}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.selingStatus}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.selingAmt}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.selingDt}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.drGlHead}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.drGlName}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.branch}"/></rich:column>
                            </rich:dataTable>
                        </a4j:region>
                    </h:panelGrid>          
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnAuthorize" value="Authorize" oncomplete="#{rich:component('authorizePanel')}.show()"/>
                            <a4j:commandButton id="btnDelete" value="Delete" oncomplete="#{rich:component('deletePanel')}.show()"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{SecurityCloseAuth.clearText}" reRender="taskList,taskList1,ddCtrlNo,lpg,message"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{SecurityCloseAuth.exitFrm}" reRender="taskList,taskList1,ddCtrlNo,message"/>                            
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" height="60" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:outputText value="Processing" />
                    </f:facet>
                    <h:outputText value="Wait Please..." />
                </rich:modalPanel>
                <rich:messages></rich:messages>
            </a4j:form>
            <rich:modalPanel id="authorizePanel" autosized="true" width="200" onshow="btnYes">
                <f:facet name="header">
                    <h:outputText value="Are You Sure To Authorize?" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%">
                        <tbody>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" ajaxSingle="true" action="#{SecurityCloseAuth.ctrlAuth}"
                                                       oncomplete="#{rich:component('authorizePanel')}.hide();" reRender="taskList,taskList1,ddCtrlNo,message" />
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Cancel" onclick="#{rich:component('authorizePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="deletePanel" autosized="true" width="200" onshow="btnYes">
                <f:facet name="header">
                    <h:outputText value="Are You Sure To Delete?" style="padding-right:15px;"/>
                </f:facet>
                <h:form>
                    <table width="100%">
                        <tbody>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnDelYes" ajaxSingle="true" action="#{SecurityCloseAuth.ctrlDelete}"
                                                       oncomplete="#{rich:component('deletePanel')}.hide();" reRender="taskList,taskList1,ddCtrlNo,message" />
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
