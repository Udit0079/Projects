<%--
    Document   : SICancellation
    Created on : May 12, 2010, 10:44:37 AM
    Author     : Ashish Mishra
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
            <title>Instruction Cancellation</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="instructionCancellation">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block" >
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{SICancellation.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Instruction Cancellation"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{SICancellation.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel001" style="height:30px;text-align:center;" styleClass="row2" width="100%">
                        <h:outputText id="stxtMsg" styleClass="error" value="#{SICancellation.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel15" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblInstructionType" style="padding-left:350px" styleClass="label" value="Instruction Type"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:panelGroup layout="block" style="width:100%;text-align:left;">
                            <h:selectOneListbox id="ddInstructionType" styleClass="ddlist" size="1" style="width: 103px" value="#{SICancellation.instructionType}">
                                <f:selectItem itemValue="--SELECT--"/>
                                <f:selectItem itemValue="TRANSACTION"/>
                                <f:selectItem itemValue="GENERAL"/>
                                <a4j:support ajaxSingle="true" event="onchange" action="#{SICancellation.reFresh}" reRender="taskList,taskList1,stxtMsg,stxtName1,stxtacno1,txtAccountToBeDebited,p4,newAcno"/>
                            </h:selectOneListbox></h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel1y1" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblAccountToBeDebited" style="padding-left:350px" styleClass="label" value="Account To Be Debited" ><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:inputText id="txtAccountToBeDebited" styleClass="input" style="width:100px" value="#{SICancellation.oldAcno}" maxlength="#{SICancellation.acNoMaxLen}">
                            <a4j:support action="#{SICancellation.gridData}" event="onblur"
                                         reRender="gridPanel103,taskList,taskList1,stxtMsg,stxtName1,stxtacno1,p4,newAcno" limitToList="true"  />
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="3" id="gridPanel16" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblName1" style="padding-left:350px" styleClass="label"  value="Name"/>
                        <h:outputText id="stxtName1"  styleClass="output"  value="#{SICancellation.name}" />
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanelacno" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblacno" style="padding-left:350px" styleClass="label"  value="Account No."/>
                        <%-- <h:outputText id="stxtacno1"  styleClass="output"  value="#{SICancellation.accno}" /> --%>
                        <h:outputText id="newAcno"  styleClass="output"  value="#{SICancellation.newAcno}" />
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel103" width="100%" styleClass="row2   " style="height:235px;">
                        <h:panelGroup id="p4">
                            <a4j:region>
                                <rich:dataTable value="#{SICancellation.siTransTables}" var="dataItem" rendered="#{SICancellation.instructionType == 'TRANSACTION'}" rowClasses="gridrow1,gridrow2" id="taskList" rows="4" columnsWidth="100" rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="13"><h:outputText value="Instruction Cancellation" /></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="Instruction No." /></rich:column>
                                            <rich:column><h:outputText value="S. No" /></rich:column>
                                            <rich:column><h:outputText value="Debit A/C" /></rich:column>
                                            <rich:column><h:outputText value="Credit A/C" /></rich:column>
                                            <rich:column><h:outputText value="By Amount" /></rich:column>
                                            <rich:column><h:outputText value="Effective Date" /></rich:column>
                                            <rich:column><h:outputText value="Status" /></rich:column>
                                            <rich:column><h:outputText value="Remarks" /></rich:column>
                                            <rich:column><h:outputText value="Enter By" /></rich:column>
                                            <rich:column><h:outputText value="EnterDate" /></rich:column>
                                            <rich:column><h:outputText value="Charge Flag" /></rich:column>
                                            <rich:column><h:outputText value="Expiry Dt" /></rich:column>
                                            <rich:column><h:outputText value="Delete" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{dataItem.instrNo}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.sNo}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.fromAcno}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.toAcno}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.amount}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.effDate}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.status}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.remarks}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.enterBy}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.entryDate}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.chargeFlag}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.expiryDt}" /></rich:column>
                                    <rich:column>
                                        <a4j:commandLink ajaxSingle="true" id="deletelink" oncomplete="#{rich:component('deletePanel')}.show()" action="#{SICancellation.setPanel}" reRender="deletePanel">
                                            <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                            <f:setPropertyActionListener value="#{dataItem}" target="#{SICancellation.currentItem}"/>
                                        </a4j:commandLink>

                                    </rich:column>
                                </rich:dataTable>
                                <rich:datascroller align="left" for="taskList" maxPages="20" rendered="#{SICancellation.instructionType == 'TRANSACTION'}"/>
                            </a4j:region>
                            <rich:modalPanel id="deletePanel" autosized="true" width="200">
                                <f:facet name="header">
                                    <h:outputText value="#{SICancellation.var}" style="padding-right:15px;" />
                                </f:facet>
                                <f:facet name="controls">
                                    <h:panelGroup>
                                        <h:graphicImage value="/images/modal/close.png" styleClass="hidelink" id="hidelink2" />
                                        <rich:componentControl for="deletePanel" attachTo="hidelink2" operation="hide" event="onclick" />
                                    </h:panelGroup>
                                </f:facet>
                                <h:form>
                                    <table width="100%">
                                        <tbody>
                                            <tr>
                                                <td align="center" width="40%">
                                                    <a4j:commandButton value="Yes" ajaxSingle="true"  action="#{SICancellation.delete}"
                                                                       oncomplete="#{rich:component('deletePanel')}.hide();" reRender="taskList,stxtMsg"   />
                                                </td>
                                                <td align="center" width="30%">
                                                    <a4j:commandButton value="No" ajaxSingle="true"  action="#{SICancellation.deleteTr}"
                                                                       oncomplete="#{rich:component('deletePanel')}.hide();" reRender="taskList,stxtMsg"  />
                                                </td>

                                                <td align="center" width="30%">
                                                    <a4j:commandButton value="cancel" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </h:form>
                            </rich:modalPanel>
                            <a4j:region>
                                <rich:dataTable value="#{SICancellation.siTransGeneTbs}" var="Item" rendered="#{SICancellation.instructionType == 'GENERAL'}" rowClasses="gridrow1,gridrow2" id="taskList1" rows="4" columnsWidth="100" rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="9"><h:outputText value="Instruction Cancellation" /></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="Instruction No." /></rich:column>
                                            <rich:column><h:outputText value="S. No" /></rich:column>
                                            <rich:column><h:outputText value="Acno" /></rich:column>
                                            <rich:column><h:outputText value="Effective Date" /></rich:column>
                                            <rich:column><h:outputText value="Status" /></rich:column>
                                            <rich:column><h:outputText value="Remarks" /></rich:column>
                                            <rich:column><h:outputText value="EntryDate" /></rich:column>
                                            <rich:column><h:outputText value="Enter By" /></rich:column>
                                            <rich:column><h:outputText value="Delete" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{Item.instrNo}" /></rich:column>
                                    <rich:column><h:outputText value="#{Item.sNo}" /></rich:column>
                                    <rich:column><h:outputText value="#{Item.acno}" /></rich:column>
                                    <rich:column><h:outputText value="#{Item.effDate}" /></rich:column>
                                    <rich:column><h:outputText value="#{Item.status}" /></rich:column>
                                    <rich:column><h:outputText value="#{Item.remarks}" /></rich:column>
                                    <rich:column><h:outputText value="#{Item.enterBy}" /></rich:column>
                                    <rich:column><h:outputText value="#{Item.entryDate}" /></rich:column>
                                    <rich:column>
                                        <a4j:commandLink ajaxSingle="true" id="deletelink" oncomplete="#{rich:component('deletePanel1')}.show()">
                                            <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                            <f:setPropertyActionListener value="#{Item}" target="#{SICancellation.currentItem1}"/>
                                        </a4j:commandLink>
                                    </rich:column>
                                </rich:dataTable>
                                <rich:datascroller align="left" for="taskList1" maxPages="20" rendered="#{SICancellation.instructionType == 'GENERAL'}"/>
                            </a4j:region>
                            <rich:modalPanel id="deletePanel1" autosized="true" width="200">
                                <f:facet name="header">
                                    <h:outputText value="Are You Sure, You Want to Delete this Record?" style="padding-right:15px;" />
                                </f:facet>
                                <f:facet name="controls">
                                    <h:panelGroup>
                                        <h:graphicImage value="/images/modal/close.png" styleClass="hidelink" id="hidelink21" />
                                        <rich:componentControl for="deletePanel1" attachTo="hidelink21" operation="hide" event="onclick" />
                                    </h:panelGroup>
                                </f:facet>
                                <h:form>
                                    <table width="100%">
                                        <tbody>
                                            <tr>
                                                <td align="center" width="50%">
                                                    <a4j:commandButton value="Yes,For All Record have Same Instruction No." ajaxSingle="true" action="#{SICancellation.delete1}"
                                                                       oncomplete="#{rich:component('deletePanel1')}.hide();" reRender="taskList1,stxtMsg" />
                                                </td>
                                                <td align="center" width="50%">
                                                    <a4j:commandButton value="Cancel" onclick="#{rich:component('deletePanel1')}.hide();return false;" />
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </h:form>
                            </rich:modalPanel>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnRefresh" value="Refresh" reRender="newAcno,taskList,taskList1,stxtMsg,stxtName1,stxtacno1,txtAccountToBeDebited,p4,ddInstructionType" action="#{SICancellation.reFresh1}">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{SICancellation.exitForm}">
                            </a4j:commandButton>

                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <%--    <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                 <rich:modalPanel id="wait" autosized="true" width="200" height="60" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:outputText value="Processing" />
                    </f:facet>
                    <h:outputText value="Wait Please..." />
                </rich:modalPanel>
<rich:messages></rich:messages>--%>
            </a4j:form>
        </body>
    </html>
</f:view>