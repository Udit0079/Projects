<%-- 
    Document   : BankHolidayMarkingRegister
    Created on : May 31, 2011, 11:35:44 AM
    Author     : ROHIT KRISHNA
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <title>Bank Holiday Marking Register</title>
            <script type="text/javascript">
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{BankHolidayMarkingRegister.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Bank Holiday Marking Register"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{BankHolidayMarkingRegister.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="a5" width="100%">
                        <h:panelGrid columns="1" id="a6" width="100%">
                            <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;border:1px ridge #BED6F8;" styleClass="row2">
                                <h:outputText id="errorMessage" styleClass="error" value="#{BankHolidayMarkingRegister.errorMessage}"/>
                                <h:outputText id="message" styleClass="msg" value="#{BankHolidayMarkingRegister.message}"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="6" id="a8" width="100%" style="height:30px;border:1px ridge #BED6F8;" styleClass="row1">
                                <h:outputLabel id="lblChoice" styleClass="label" value="Please Select Your Action :" ><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddChoice" tabindex="1" styleClass="ddlist" disabled="#{BankHolidayMarkingRegister.updateFlag}" value="#{BankHolidayMarkingRegister.choice}" size="1" style="width: 100px">
                                    <f:selectItems value="#{BankHolidayMarkingRegister.choiceOption}" />
                                    <a4j:support action="#{BankHolidayMarkingRegister.choiceOnblur}" event="onblur" reRender="message,errorMessage,a7,a8,a9,a10,taskList1,gpFooter" limitToList="true" focus="ddBranch"/>
                                </h:selectOneListbox>
                            </h:panelGrid>
                                <h:panelGrid columnClasses="col9" columns="6" id="a15" width="100%" style="height:30px;border:1px ridge #BED6F8;" styleClass="row2">
                               <h:outputLabel id="lblBranch" styleClass="label" value="Please Select Branch :" ><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddBranch" tabindex="1" styleClass="ddlist" value="#{BankHolidayMarkingRegister.branch}" size="1" style="width: 100px">
                                    <f:selectItems value="#{BankHolidayMarkingRegister.branchOption}" />
                                </h:selectOneListbox> 
                               </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="6" id="a9" width="100%" style="height:30px;border:1px ridge #BED6F8;" styleClass="row2">
                                <h:outputLabel id="lblFestible" styleClass="label" value="Festival(Holiday Description) :" ><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtFestible" maxlength="50" tabindex="2" size="20" value="#{BankHolidayMarkingRegister.festible}" onblur="this.value = this.value.toUpperCase();" styleClass="input"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="6" id="a10" width="100%" style="height:30px;border:1px ridge #BED6F8;" styleClass="row1">
                                <h:outputLabel id="lblDate" styleClass="label" value="Date :" ><font class="required" color="red">*</font></h:outputLabel>
                                <rich:calendar datePattern="dd/MM/yyyy" disabled="#{BankHolidayMarkingRegister.updateFlag}" tabindex="3" id="calDate" value="#{BankHolidayMarkingRegister.hoidayDate}" ondateselect="#{rich:element('btnMark')}.focus();" inputSize="10"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="6" id="a11" width="100%" style="height:30px;border:1px ridge #BED6F8;" styleClass="row2"/>
                            <h:panelGrid columnClasses="col9" columns="6" id="a12" width="100%" style="height:30px;border:1px ridge #BED6F8;" styleClass="row1"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="vtop" columns="1" id="a7" width="100%" styleClass="row2" style="height:200px;border:1px ridge #BED6F8;">
                            <a4j:region>
                                <rich:dataTable value="#{BankHolidayMarkingRegister.hoidayList}" var="dataItem"
                                                rowClasses="gridrow1,gridrow2" id="taskList1" rows="5" columnsWidth="100" rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="15"><h:outputText value="Holidays Detail" /></rich:column>
                                            <rich:column breakBefore="true" width="30%"><h:outputText value="Holiday Date" /></rich:column>
                                            <rich:column width="60%"><h:outputText value="Holiday Name" /></rich:column>
                                            <rich:column width="10"><h:outputText value="Select" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{dataItem.holidayDate}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.holidayDescription}" /></rich:column>
                                    <rich:column style="text-align:center;width:40px">
                                        <a4j:commandLink id="selectlink" action="#{BankHolidayMarkingRegister.fillValuesofGridInFields}"
                                                         oncomplete="#{rich:element('ddChoice')}.disabled = true;#{rich:element('calDate')}.disabled = true;"
                                                         reRender="message,errorMessage,a7,a8,a9,a10,taskList1,gpFooter" focus="ddBranch">
                                            <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                            <f:setPropertyActionListener value="#{dataItem}" target="#{BankHolidayMarkingRegister.currentItem}"/>
                                            <f:setPropertyActionListener value="#{row}" target="#{BankHolidayMarkingRegister.currentRow}"/>
                                        </a4j:commandLink>
                                    </rich:column>
                                </rich:dataTable>
                                <rich:datascroller align="left" for="taskList1" maxPages="10" />
                                <rich:modalPanel id="modifyPanel" autosized="true" width="250" onshow="#{rich:element('btnYesUpdatePanel')}.focus();">
                                    <f:facet name="header">
                                        <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                                    </f:facet>
                                    <h:form>
                                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                            <tbody>
                                                <tr style="height:40px">
                                                    <td colspan="2">
                                                        <h:outputText value="Are you sure to update ?"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td align="center" width="50%">
                                                        <a4j:commandButton value="Yes" id="btnYesUpdatePanel" ajaxSingle="true" action="#{BankHolidayMarkingRegister.updateRecord}"
                                                                           oncomplete="#{rich:component('modifyPanel')}.hide();" reRender="message,errorMessage,a7,a8,a9,a10,taskList1,gpFooter" focus=""/>
                                                    </td>
                                                    <td align="center" width="50%">
                                                        <a4j:commandButton value="No" id="btnNoUpdatePanel" onclick="#{rich:component('modifyPanel')}.hide();return false;" />
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </h:form>
                                </rich:modalPanel>
                            </a4j:region>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnMark" value="Mark" oncomplete="#{rich:component('markPanel')}.show()" rendered="#{BankHolidayMarkingRegister.markFlag}" reRender="message,errorMessage,a7,a8,a9,a10,taskList1,gpFooter" tabindex="4"/>
                            <%--a4j:commandButton id="btnUpdate" value="Update" oncomplete="#{rich:component('modifyPanel')}.show()" rendered="#{BankHolidayMarkingRegister.updateFlag}" reRender="message,errorMessage,a7,a8,a9,a10,taskList1,gpFooter" tabindex="5"/--%>
                            <a4j:commandButton id="btnDelete"  value="Delete" oncomplete="#{rich:component('deletePanel')}.show()" rendered="#{BankHolidayMarkingRegister.updateFlag}" reRender="message,errorMessage,a7,a8,a9,a10,taskList1,gpFooter" tabindex="6"/>
                            <a4j:commandButton id="btnRefresh" tabindex="7" value="Refresh" action="#{BankHolidayMarkingRegister.resetForm}" reRender="message,errorMessage,a7,a8,a9,a10,taskList1,gpFooter" focus="ddChoice"/>
                            <a4j:commandButton id="btnExit" tabindex="8" value="Exit" action="#{BankHolidayMarkingRegister.exitBtnAction}" reRender="message,errorMessage" />
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <rich:modalPanel id="markPanel" autosized="true" width="250" onshow="#{rich:element('btnYesSavePanel')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText value="Are you sure to mark holiday ?"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="btnYesSavePanel" ajaxSingle="true" action="#{BankHolidayMarkingRegister.markHoliday}"
                                                           oncomplete="#{rich:component('markPanel')}.hide();" reRender="message,errorMessage,a7,a8,a9,a10,taskList1,gpFooter" focus="btnRefresh"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="No" id="btnNoSavePanel" onclick="#{rich:component('markPanel')}.hide();return false;" />
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
                <rich:modalPanel id="deletePanel" autosized="true" width="250" onshow="#{rich:element('btnYesDeletePanel')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText value="Are you sure to delete ?"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="btnYesDeletePanel" ajaxSingle="true" action="#{BankHolidayMarkingRegister.deleteRecord}"
                                                           oncomplete="#{rich:component('deletePanel')}.hide();" reRender="message,errorMessage,a7,a8,a9,a10,taskList1,gpFooter" focus=""/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="No" id="btnNoDeletePanel" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
            </a4j:form>
        </body>
    </html>
</f:view>
