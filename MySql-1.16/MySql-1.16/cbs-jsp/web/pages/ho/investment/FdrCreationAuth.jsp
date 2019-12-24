<%-- 
    Document   : FdrCreationAuth
    Created on : Oct 24, 2017, 10:02:10 AM
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
            <title>FDR Creation Authorize</title>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header" width="100%">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date:"/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{FdrCreationAuth.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblincident" styleClass="headerLabel" value="Authorization Of FDR Creation"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lbluser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{FdrCreationAuth.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="1" id="lpg" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                        <h:outputText id="message" styleClass="msg" value="#{FdrCreationAuth.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="gridPanel15" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="labelSeqNo" styleClass="label" value="Sequence No"/>
                        <h:selectOneListbox id="ddSeqNo" styleClass="ddlist" size="1" style="width:100px" value="#{FdrCreationAuth.seqNo}">
                            <f:selectItem itemValue="--SELECT--"/>
                            <f:selectItems value="#{FdrCreationAuth.pendingSeqNoList}"/>
                            <a4j:support actionListener="#{FdrCreationAuth.getList}" event="onblur" reRender="taskList,taskList1,message" limitToList="true"/>
                        </h:selectOneListbox>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="a6" style="height:auto;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{FdrCreationAuth.secFirst}" var="dataItem" rowClasses="gridrow1,gridrow2" id="taskList" rows="6" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="11"><h:outputText value="" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Sequence No"/></rich:column>
                                        <rich:column><h:outputText value="Security Type" /></rich:column>
                                        <rich:column><h:outputText value="TD Detail" /></rich:column>
                                        <rich:column><h:outputText value="FDR No." /></rich:column>
                                        <rich:column><h:outputText value="Bank" /></rich:column>
                                        <rich:column><h:outputText value="Branch" /></rich:column>
                                        <rich:column><h:outputText value="Purchase Date" /></rich:column>
                                        <rich:column><h:outputText value="Days" /></rich:column>
                                        <rich:column><h:outputText value="Months" /></rich:column>
                                        <rich:column><h:outputText value="Years" /></rich:column>
                                        <rich:column><h:outputText value="Maturity Date" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.ctrlNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.secType}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.secDesc}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.fdrNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.sellerName}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.branch1}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.purchaseDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.days}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.months}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.years}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.matDt}" /></rich:column>
                            </rich:dataTable>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="a7" style="height:auto;" styleClass="row1" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{FdrCreationAuth.secFirst}" var="dataItem" align="center" rowClasses="gridrow1,gridrow2" id="taskList1" rows="7" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="10"><h:outputText value="" /></rich:column>
                                        <rich:column breakBefore="true"> <h:outputText value="ROI"/></rich:column>
                                        <rich:column><h:outputText value="Interest Option"/></rich:column>
                                        <rich:column><h:outputText value="Face Value"/></rich:column>
                                        <rich:column><h:outputText value="Interest"/></rich:column>
                                        <rich:column><h:outputText value="Maturity Value"/></rich:column>
                                        <rich:column><h:outputText value="Branch"/></rich:column>
                                        <rich:column><h:outputText value="Credit GL"/></rich:column>
                                        <rich:column><h:outputText value="Credit GL Name"/></rich:column>
                                        <rich:column><h:outputText value="Debit GL"/></rich:column>
                                        <rich:column><h:outputText value="Debit GL Name"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.roi}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.intOpt}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.faceValue}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.amtIntRec}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.totAmtIntRec}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.branch2}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.crHead}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.crHeadName}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.drHead}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.drHeadName}" /></rich:column>                                
                            </rich:dataTable>
                        </a4j:region>
                    </h:panelGrid>          
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnAuthorize" value="Authorize" oncomplete="#{rich:component('authorizePanel')}.show()"/>
                            <a4j:commandButton id="btnDelete" value="Delete" oncomplete="#{rich:component('deletePanel')}.show()"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{FdrCreationAuth.clearText}" reRender="taskList,taskList1,ddSeqNo,lpg,message"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{FdrCreationAuth.exitFrm}" reRender="taskList,taskList1,ddSeqNo,message"/>                            
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
                                    <a4j:commandButton value="Yes" id="btnYes" ajaxSingle="true" action="#{FdrCreationAuth.ctrlAuth}"
                                                       oncomplete="#{rich:component('authorizePanel')}.hide();" reRender="taskList,taskList1,ddSeqNo,message" />
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
                                    <a4j:commandButton value="Yes" id="btnDelYes" ajaxSingle="true" action="#{FdrCreationAuth.ctrlDelete}"
                                                       oncomplete="#{rich:component('deletePanel')}.hide();" reRender="taskList,taskList1,ddSeqNo,message" />
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
