<%-- 
    Document   : IdMergeAuth
    Created on : Nov 15, 2014, 4:09:24 PM
    Author     : mayank
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
            <title>ID Merge Authorization</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="idMergeForm">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{IdMergeAuth.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="ID Merge Authorization"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{IdMergeAuth.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columns="6" id="gridPanel3" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="message" value="#{IdMergeAuth.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanelTable" style=" width:100%;height:auto" styleClass="row1" columnClasses="vtop">
                        <a4j:region>
                            <rich:dataTable value="#{IdMergeAuth.idMergeList}" var="dataItem" 
                                            rowClasses="gridrow1, gridrow2" id="taskList" rows="5" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column width="32px"><h:outputText value="Sr No."/></rich:column>
                                        <rich:column width="80px"><h:outputText value="Original Id"/></rich:column>
                                        <rich:column width="80px"><h:outputText value="To Be Merged Id"/></rich:column>
                                        <rich:column width="65px"><h:outputText value="Enter By"/></rich:column>
                                        <rich:column width="65px"><h:outputText value="Auth Status"/></rich:column>
                                        <rich:column width="45px"><h:outputText value="Delete"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.sNo}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.orgId}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.mergId}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.enterBy}" /></rich:column>
                                <rich:column style="text-align:center;cursor:pointer;">
                                    <h:outputText value="#{dataItem.auth}" />
                                    <a4j:support action="#{IdMergeAuth.changeAuth}" ajaxSingle="true" event="ondblclick" reRender="message,taskList,btnSave,table,table2" >
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{IdMergeAuth.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{IdMergeAuth.currentRow}"/>
                                    </a4j:support>
                                </rich:column>
                                <rich:column style="text-align:center;">
                                    <a4j:commandLink ajaxSingle="true" id="deletelink" oncomplete="#{rich:component('deletePanel')}.show()">
                                        <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{row}" target="#{IdMergeAuth.currentRow}" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{IdMergeAuth.currentItem}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="5"/>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="table" style="height:auto;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{IdMergeAuth.gridDetail1}" var="dataItem1"
                                rowClasses="row1,row2" id="taskList1" rows="5" columnsWidth="100" rowKeyVar="row"
                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="15"><h:outputText value="ORIGINAL ID DETAILS"/></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="S.No."/></rich:column>
                                        <rich:column><h:outputText value="Account No"/></rich:column>
                                        <rich:column><h:outputText value="Name"/></rich:column>
                                        <rich:column><h:outputText value="Father Name"/></rich:column>
                                        <rich:column><h:outputText value="Date Of Birth"/></rich:column>
                                        <rich:column><h:outputText value="Pan No."/></rich:column>
                                        <rich:column><h:outputText value="Corr. Address"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem1.sNo}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.acno}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.custname}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.fname}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.dob}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.panno}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.craddress}"/></rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList1" maxPages="20"/>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="table2" style="height:auto;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{IdMergeAuth.gridDetail2}" var="dataItem2"
                                rowClasses="row1,row2" id="taskList2" rows="5" columnsWidth="100" rowKeyVar="row"
                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="15"><h:outputText value="MERGED ID DETAILS"/></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="S.No."/></rich:column>
                                        <rich:column><h:outputText value="Account No"/></rich:column>
                                        <rich:column><h:outputText value="Name"/></rich:column>
                                        <rich:column><h:outputText value="Father Name"/></rich:column>
                                        <rich:column><h:outputText value="Date Of Birth"/></rich:column>
                                        <rich:column><h:outputText value="Pan No."/></rich:column>
                                        <rich:column><h:outputText value="Corr. Address"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet> 
                                <rich:column><h:outputText value="#{dataItem2.sNo}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem2.acno}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem2.custname}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem2.fname}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem2.dob}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem2.panno}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem2.craddress}"/></rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList2" maxPages="20"/>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columns="3" id="gridPanelBtn" style="width:100%;text-align:center;" styleClass="footer" columnClasses="col19,col19,col19">
                        <h:panelGroup id="imageMsgPanel" style="text-align:left;" layout="block">
                            <h:outputText id="stxtInstruction" styleClass="output" value="After Authorization Entry Can Not Be Reversed" style="color:blue;"/>
                        </h:panelGroup>
                        <h:panelGroup id="gridPaneGrp" layout="block" style="width:100%;text-align:center;">
                            <a4j:region id="btnPnl">
                                <a4j:commandButton id="btnSave" disabled="#{IdMergeAuth.authorizeValue}" value="Authorize" reRender="btnSave,taskList,message,table,table2" oncomplete="#{rich:component('authPanel')}.show()"/>                                
                                <a4j:commandButton id="btnRefresh" value="Refresh" action="#{IdMergeAuth.btnRefreshAction}" reRender="btnSave,taskList,message,table,table2"/>
                                <a4j:commandButton id="btnExit" value="Exit" action="#{IdMergeAuth.btnExitAction}" reRender="btnSave,taskList,message,table,table2"/>
                            </a4j:region>
                        </h:panelGroup>
                        <h:panelGroup layout="block">
                            <h:outputLabel/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>                            
            </a4j:form>            
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="btnPnl"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
            <rich:modalPanel id="authPanel" label="Confirmation Dialog" autosized="true" width="250" onshow="#{rich:element('btnNo1')}.focus();" style="">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are you sure to Authorize, After this Authorization Entry Can Not be Reversed ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" ajaxSingle="true" action="#{IdMergeAuth.btnAuthAction}" reRender="btnSave,taskList,message,table,table2"
                                            oncomplete="#{rich:component('authPanel')}.hide();"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo1" onclick="#{rich:component('authPanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="deletePanel" label="Confirmation Dialog" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();" style="">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are you sure to delete this transaction ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" ajaxSingle="true" action="#{IdMergeAuth.btnDeleteAction}" reRender="btnSave,taskList,message,table,table2"
                                            oncomplete="#{rich:component('deletePanel')}.hide();"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>            
        </body>
    </html>
</f:view>