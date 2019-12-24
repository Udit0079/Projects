<%-- 
    Document   : CrrDailyUpdationMaster
    Created on : Oct 1, 2010, 12:20:45 PM
    Author     : Dinesh Pratap Singh
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
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>Crr Daily Updation Master</title>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="headerPanel" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{CrrDailyUpdationMaster.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="CRR Daily Updation Master"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{CrrDailyUpdationMaster.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" width="100%;"  style="text-align:center" styleClass="row2">
                        <h:outputText id="message" value="#{CrrDailyUpdationMaster.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanelAcDesc" columns="4" columnClasses="col2,col7,col2,col7" width="100%" styleClass="row1">
                        <h:outputLabel id="lblAcDesc" styleClass="label" value="Account Description"/>
                        <h:selectOneListbox id="ddAcDesc" style="width:120px" styleClass="ddlist" size="1" value="#{CrrDailyUpdationMaster.accDescType}">
                            <f:selectItems value="#{CrrDailyUpdationMaster.acctDescOption}"/>
                            <a4j:support action="#{CrrDailyUpdationMaster.gridLoad}" event="onchange" reRender="mainPanel"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblFunction" styleClass="label" value="Function"/>
                        <h:selectOneListbox id="ddFunction" style="width:120px" styleClass="ddlist" size="1" value="#{CrrDailyUpdationMaster.function}">
                            <f:selectItems value="#{CrrDailyUpdationMaster.functionList}"/>
                            <a4j:support event="onchange" actionListener="#{CrrDailyUpdationMaster.functionProcess}" reRender="message,txtGLHeadFrom,fromlabel,txtGLHeadTO,toLabel,btnProcess"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanelFrHead" columns="4" columnClasses="col2,col7,col2,col7" width="100%" styleClass="row2">
                        <h:outputLabel id="lblGLHeadFrom" styleClass="label" value="From GL Head"/>
                        <h:panelGroup>
                            <h:inputText id="txtGLHeadFrom"  maxlength="8"  styleClass="input"  style="width:120px" value="#{CrrDailyUpdationMaster.glHeadFrom}" disabled="#{CrrDailyUpdationMaster.frTxtEnable}">
                                <a4j:support event="onblur" action="#{CrrDailyUpdationMaster.fromGlHead}" reRender="message,fromlabel"/>
                            </h:inputText>
                            <h:outputLabel id="fromlabel" styleClass="label" value="#{CrrDailyUpdationMaster.newGlheadFrom}" style="color:blue;"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblGLHeadTO" styleClass="label" value="To GL Head"/>
                        <h:panelGroup>
                            <h:inputText id="txtGLHeadTO" styleClass="input" maxlength="8" style="width:120px" value="#{CrrDailyUpdationMaster.glHeadTO}" disabled="#{CrrDailyUpdationMaster.toTxtEnable}">
                                <a4j:support event="onblur" action="#{CrrDailyUpdationMaster.toGLHead}" reRender="message,toLabel,btnProcess" focus="btnProcess"/>
                            </h:inputText>
                            <h:outputLabel id="toLabel" styleClass="label" value="#{CrrDailyUpdationMaster.newGlheadTo}" style="color:blue;"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" styleClass="row1" style="height:30px;" width="100%">
                        <rich:dataTable value="#{CrrDailyUpdationMaster.tableList}"  var="dataitem" rowClasses="gridrow1, gridrow2" rows="10" id="taskList" columnsWidth="100" onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="50%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column width="40%"><h:outputText value="Account Description"/></rich:column>
                                    <rich:column width="30%"><h:outputText value="From GL Head"/></rich:column>
                                    <rich:column width="30%"><h:outputText value="To GL Head"/></rich:column>
                                    <rich:column><h:outputText value="Update"/></rich:column>
                                    <rich:column><h:outputText value="Delete"/></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column>
                                <h:outputText value="#{dataitem.acDesc}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataitem.fomGlHead}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataitem.toGlHead}"/>
                            </rich:column>
                            <rich:column style="text-align:center;width:40px">
                                <a4j:commandLink  reRender="taskList" ajaxSingle="true" id="selectlink" onclick="#{rich:component('updatePanelGrid')}.show();">
                                    <h:graphicImage   id="imagerender"  value="/resources/images/edit.gif" style="border:0"/>
                                    <f:setPropertyActionListener value="#{dataitem}" target="#{CrrDailyUpdationMaster.authorized}"/>
                                </a4j:commandLink>
                            </rich:column>
                            <rich:column style="text-align:center;width:40px">
                                <a4j:commandLink  reRender="taskList" ajaxSingle="true" id="selectlink2" onclick="#{rich:component('deletePanelGrid')}.show();">
                                    <h:graphicImage id="imagerender2" value="/resources/images/delete.gif" style="border:0"/>
                                    <f:setPropertyActionListener value="#{dataitem}" target="#{CrrDailyUpdationMaster.authorized}"/>
                                </a4j:commandLink>
                            </rich:column>
                        </rich:dataTable>
                        <rich:datascroller align="left" for="taskList" maxPages="10"/>
                    </h:panelGrid>
                    <rich:modalPanel id="updatePanelGrid" autosized="true" width="200">
                        <f:facet name="header">
                            <h:outputText value="Click yes to update" style="padding-right:15px;" />
                        </f:facet>
                        <f:facet name="controls">
                            <h:panelGroup>
                                <h:graphicImage value="/images/close.png" styleClass="hidelink" id="hidelink1" />
                                <rich:componentControl for="updatePanelGrid" attachTo="hidelink1" operation="hide" event="onclick" />
                            </h:panelGroup>
                        </f:facet>
                        <h:form>
                            <table width="100%">
                                <tbody>
                                    <tr>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="Yes" ajaxSingle="true" action="#{CrrDailyUpdationMaster.setRowValues}"
                                                               onclick="#{rich:component('updatePanelGrid')}.hide();" reRender="mainPanel"/>
                                        </td>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="Cancel" onclick="#{rich:component('updatePanelGrid')}.hide();return false;" />
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </h:form>
                    </rich:modalPanel>
                    <rich:modalPanel id="deletePanelGrid" autosized="true" width="200">
                        <f:facet name="header">
                            <h:outputText value="Click yes to delete" style="padding-right:15px;" />
                        </f:facet>
                        <f:facet name="controls">
                            <h:panelGroup>
                                <h:graphicImage value="/images/close.png" styleClass="hidelink" id="hidelink2" />
                                <rich:componentControl for="deletePanelGrid" attachTo="hidelink2" operation="hide" event="onclick" />
                            </h:panelGroup>
                        </f:facet>
                        <h:form>
                            <table width="100%">
                                <tbody>
                                    <tr>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="Yes" ajaxSingle="true" action="#{CrrDailyUpdationMaster.deleteData}"
                                                               onclick="#{rich:component('deletePanelGrid')}.hide();" reRender="taskList,message,btnProcess,ddFunction"/>
                                        </td>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="Cancel" onclick="#{rich:component('deletePanelGrid')}.hide();return false;" />
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </h:form>
                    </rich:modalPanel>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel" styleClass="vtop">
                            <a4j:commandButton id="btnProcess" value="#{CrrDailyUpdationMaster.btnValue}" action="#{CrrDailyUpdationMaster.processAction}" disabled="#{CrrDailyUpdationMaster.enableProcessBtn}" reRender="mainPanel"/>
                            <a4j:commandButton id="btnReset" value="Refresh" action="#{CrrDailyUpdationMaster.refresh()}" reRender="mainPanel"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{CrrDailyUpdationMaster.exitForm}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>