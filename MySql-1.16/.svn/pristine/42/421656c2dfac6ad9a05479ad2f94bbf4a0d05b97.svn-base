<%-- 
    Document   : syncinactiveaadhar
    Created on : 18 Jun, 2016, 11:43:13 AM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link rel="stylesheet" href="/cbs-jsp/resources/txn_style.css" type="text/css"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Inactive Aadhar Sync</title>
        </head>
        <body>
            <a4j:form id="uploadForm" enctype="multipart/form-data" prependId="false">
                <h:panelGrid id="mainPanel" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="gridPanel" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{syncController.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Inactive Aadhar Sync"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{syncController.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errormsg" style="height:1px;text-align:center" styleClass="error" width="100%">
                        <h:outputText id="errmsg" value="#{syncController.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel1" columnClasses="col2,col7,col7,col2" columns="4" width="100%">
                        <h:outputLabel/>
                        <h:outputLabel id="fileuploadlbl" value="Upload File Path" styleClass="label"/>
                        <t:inputFileUpload id="file" value="#{syncController.uploadedFile}" required="false"/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columns="2" id="a6" style="height:30px;" styleClass="footer" columnClasses="col7,col7,col7" width="100%">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <h:commandButton id="btnUpload" value="Upload" action="#{syncController.uploadProcess}"/>
                            <a4j:commandButton id="btnPost" value="Mark Inactive" oncomplete="#{rich:component('processPanel')}.show();" reRender="errmsg,processPanel"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{syncController.refreshForm}" reRender="errmsg,tablePanel,taskList,activetablePanel,activeTaskList"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{syncController.exitForm}" reRender="errmsg,tablePanel,taskList"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col1,col2" columns="1" id="tablePanel" width="100%" styleClass="row1" style="height:50%">
                        <a4j:region>
                            <rich:dataTable value ="#{syncController.tableList}" rowClasses="row1, row2" id = "taskList" 
                                            rows="3" columnsWidth="100" rowKeyVar="row" var ="dataItem"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="3"><h:outputText value="Currently Active Aadhar No At CBS But InActive On NPCI"/></rich:column>
                                        <rich:column breakBefore="true" width="30px;"><h:outputText value="Customer Id"/> </rich:column>
                                        <rich:column width="30px;"><h:outputText value="Aadhar No" /></rich:column>
                                        <rich:column width="40px;"><h:outputText value="Branch" /></rich:column>

                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.custId}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.aadharNo}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.branch}"/></rich:column>
                            </rich:dataTable>
                        </a4j:region>
                        <rich:datascroller align="left" for="taskList" maxPages="20"/>
                    </h:panelGrid>
                    
                    <h:panelGrid columnClasses="col1,col2" columns="1" id="activetablePanel" width="100%" styleClass="row2" style="height:50%">
                        <a4j:region>
                            <rich:dataTable value ="#{syncController.activeTableList}" rowClasses="row1, row2" id = "activeTaskList" 
                                            rows="3" columnsWidth="100" rowKeyVar="row" var ="dataItem"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="3"><h:outputText value="Currently InActive Aadhar No At CBS But Active On NPCI"/></rich:column>
                                        <rich:column breakBefore="true" width="30px;"><h:outputText value="Customer Id"/> </rich:column>
                                        <rich:column width="30px;"><h:outputText value="Aadhar No" /></rich:column>
                                        <rich:column width="40px;"><h:outputText value="Branch" /></rich:column>

                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.custId}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.aadharNo}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.branch}"/></rich:column>
                            </rich:dataTable>
                        </a4j:region>
                        <rich:datascroller align="left" for="activeTaskList" maxPages="20"/>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:region id="btnProcessRegion">
                <rich:modalPanel id="processPanel" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText id="confirmid" value="Are you sure to process ?"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="btnYes" action="#{syncController.post}" 
                                                           oncomplete="#{rich:component('processPanel')}.hide();" 
                                                           reRender="errmsg,mainPanel"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('processPanel')}.hide();"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
            </a4j:region>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="btnProcessRegion"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
