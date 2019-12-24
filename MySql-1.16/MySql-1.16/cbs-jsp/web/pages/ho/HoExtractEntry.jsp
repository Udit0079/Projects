<%--
    Document   : HoExtractEntry
    Created on : Oct 14, 2010, 12:14:08 PM
    Author     : Sudhir kr Bisht
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
            <title>Ho extract form</title>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
            </script>
        </head>
        <body>
            <a4j:form id="HoExtractForm">
                <h:panelGrid id="HoextractPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="HoextractPanel1" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="HoextractPanel2" layout="block">
                            <h:outputLabel id="HoextractPanel3" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="HoextractPanel4" styleClass="output" value=" #{HoExtractEntry.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="HoextractPanel5" styleClass="headerLabel" value=" Ho Extract Form"/>
                        <h:panelGroup id="HoextractPanel6" layout="block">
                            <h:outputLabel id="HoextractPanel7" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="HoextractPanel8" styleClass="output" value="#{HoExtractEntry.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="errorMsg11"   width="100%"   style="height:1px;text-align:center" styleClass="error">
                        <h:outputText id="errMsg11"  value="#{HoExtractEntry.msg}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="HoextractPanel9" style="text-align:left;" width="100%">
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="newpanel" style="text-align:left;" width="100%" styleClass="row1">
                            <h:panelGrid columnClasses="col8,col8" columns="2" id="HoextractPanel58" style="text-align:left;" width="100%">
                                <h:outputLabel id="HoextractPanel13" styleClass="output" value="Origin Branch"/>
                                <h:inputText id="HoextractPanel14" styleClass="input" value="#{HoExtractEntry.originBranchText}" maxlength="6"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col8,col8" columns="2" id="HoextractPanel57" style="text-align:left;" width="100%">
                                <h:outputLabel id="HoextractPanel15" styleClass="output" value="Responding Branch "/>
                                <h:inputText id="HoextractPanel16" styleClass="input" value="#{HoExtractEntry.respondingBranchText}" maxlength="6"/>
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="newel56" style="text-align:left;" width="100%" styleClass="row2">
                            <h:panelGrid columnClasses="col8,col8" columns="2" id="HoextractPanel56" style="text-align:left;" width="100%">
                                <h:outputLabel id="HoextractPanel17" styleClass="output" value="Dt "/>
                                <rich:calendar  enableManualInput="true" id="calToDatenew" datePattern="dd/MM/yyyy" value="#{HoExtractEntry.dt}"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col8,col8" columns="2" id="HoextractPanel55" style="text-align:left;" width="100%">
                                <h:outputLabel id="HoextractPanel19" styleClass="output" value="OriginDt "/>
                                <rich:calendar  enableManualInput="true" id="calToDatenew1" datePattern="dd/MM/yyyy" value="#{HoExtractEntry.origindt}"/>
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="newpanelextarct" style="text-align:left;" width="100%" styleClass="row1">
                            <h:panelGrid columnClasses="col8,col8" columns="2" id="HoextractPanel54" style="text-align:left;" width="100%">
                                <h:outputLabel id="HoextractPanel21" styleClass="output" value="Respond Dt "/>
                                <rich:calendar enableManualInput="true"  id="calToDatenew2" datePattern="dd/MM/yyyy" value="#{HoExtractEntry.responddt}"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col8,col8" columns="2" id="HoextractPanel53" style="text-align:left;" width="100%">
                                <h:outputLabel id="HoextractPanel23" styleClass="output" value="Amount"/>
                                <h:inputText id="HoextractPanel24" styleClass="input" value="#{HoExtractEntry.amount}" maxlength="12"/>
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="newexpanel3" style="text-align:left;" width="100%" styleClass="row2">
                            <h:panelGrid columnClasses="col8,col8" columns="2" id="HoextractPanel52" style="text-align:left;" width="100%">
                                <h:outputLabel id="HoextractPanel251" styleClass="output" value="EnterBY"/>
                                <h:inputText id="HoextractPanel26" styleClass="input" value="#{HoExtractEntry.enterByText}" maxlength="40"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col8,col8" columns="2" id="HoextractPanel51" style="text-align:left;" width="100%">
                                <h:outputLabel id="HoextractPanel27" styleClass="output" value=" Details"/>
                                <h:inputText id="HoextractPanel28" styleClass="input" value="#{HoExtractEntry.detailsText}" maxlength="100"/>
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="newextarctpanel4" style="text-align:left;" width="100%"  styleClass="row1">
                            <h:panelGrid columnClasses="col8,col8" columns="2" id="HoextractPanel50" style="text-align:left;" width="100%">
                                <h:outputLabel id="HoextractPanel29" styleClass="output" value="AuthBy"/>
                                <h:inputText id="HoextractPanel30" styleClass="input" value="#{HoExtractEntry.authByText}" maxlength="40"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col8,col8" columns="2" id="HoextractPanel49" style="text-align:left;" width="100%">
                                <h:outputLabel id="HoextractPanel31" styleClass="output" value="LastUpdateBy "/>
                                <h:inputText disabled="#{HoExtractEntry.inputtextby}"id="HoextractPanel32" styleClass="input"  value="#{HoExtractEntry.lastUpdateByText}"/>
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="HoextractPanel48" style="text-align:left;" width="100%" styleClass="row2">
                            <h:panelGrid columnClasses="col8,col8" columns="2" id="HoextractPanel481" style="text-align:left;" width="100%">
                                <h:outputLabel id="HoextractPanel33" styleClass="output" value="LastUpdateDt "/>
                                <h:inputText disabled="#{HoExtractEntry.inputtextdt}"id="HoextractPanel34" styleClass="input" value="#{HoExtractEntry.lastUpdateDtText}"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col8,col8" columns="2" id="HoextractPanel47" style="text-align:left;" width="100%">
                                <h:outputLabel id="HoextractPanel35" styleClass="output" value=" InstNo"/>
                                <h:inputText id="HoextractPanel36" styleClass="input" value="#{HoExtractEntry.instNoText}" maxlength="12"/>
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="newexpanel5" style="text-align:left;" width="100%" styleClass="row1">
                            <h:panelGrid columnClasses="col8,col8" columns="2" id="HoextractPanel59" style="text-align:left;" width="100%">
                                <h:outputLabel id="HoextractPanel37" styleClass="output" value="Entry Type"/>
                                <h:panelGroup>
                                    <h:selectOneListbox id="nwHoextractPanel12" styleClass="ddlist" size="1" style="width:120px" value="#{HoExtractEntry.entryType}">
                                        <f:selectItems value="#{HoExtractEntry.entryTypeList}"/>
                                    </h:selectOneListbox>
                                </h:panelGroup>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col8,col8" columns="2" id="HoextractPanel46" style="text-align:left;" width="100%">
                                <h:outputLabel id="HoextractPanel39" styleClass="output" value="Transaction Type"/>
                                <h:panelGroup>
                                    <h:selectOneListbox id="newHoextractPanel12" styleClass="ddlist" size="1" style="width:120px" value="#{HoExtractEntry.transactionType}">
                                        <f:selectItems value="#{HoExtractEntry.transactionTypelist}"/>
                                    </h:selectOneListbox>
                                </h:panelGroup>
                            </h:panelGrid>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="1" id="HextractPanel47" style="text-align:left;" width="100%" styleClass="row2">
                        <h:panelGroup>
                            <a4j:commandButton  id="addBt"  action="#{HoExtractEntry.addButton}" value="Add"  reRender="taskList1,errMsg11,extractsave,HoextractPanel14,HoextractPanel16,calToDatenew,
                                                calToDatenew1,HoextractPanel24,HoextractPanel26,HoextractPanel28,HoextractPanel30,HoextractPanel32,
                                                HoextractPanel36,HoextractPanel33,nwHoextractPanel12,newHoextractPanel12" disabled="false" focus="HoextractPanel14"/> 
                            <h:outputLabel value="Click  add  button  after  filling  entries  to  form" styleClass="label" style="color:blue"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="HoextractPanel41" columns="1" columnClasses="vtop" styleClass="row2" style="height:200px;" width="100%">
                        <rich:dataTable  value="#{HoExtractEntry.griddata}" var="dataitem" rowClasses="gridrow1, gridrow2" rows="10" id="taskList1" columnsWidth="100" 
                                         onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column><h:outputText value="Origin Branch"/></rich:column>
                                    <rich:column><h:outputText value="Responding Branch"/></rich:column>
                                    <rich:column><h:outputText value="Dt"/></rich:column>
                                    <rich:column><h:outputText value="OriginDt"/></rich:column>
                                    <rich:column><h:outputText value="Respond Dt"/></rich:column>
                                    <rich:column><h:outputText value="Amount"/></rich:column>
                                    <rich:column><h:outputText value="EnterBY"/></rich:column>
                                    <rich:column><h:outputText value="Details"/></rich:column>
                                    <rich:column><h:outputText value="AuthBy"/></rich:column>
                                    <rich:column><h:outputText value=" LastUpdateBy"/></rich:column>
                                    <rich:column><h:outputText value="LastUpdateDt"/></rich:column>
                                    <rich:column><h:outputText value="InstNo"/></rich:column>
                                    <rich:column><h:outputText value="entrytype"/></rich:column>
                                    <rich:column><h:outputText value="ty"/></rich:column>
                                    <rich:column><h:outputText value="Delete"/></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column>
                                <h:outputText value="#{dataitem.originBranch}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataitem.respondingBranch}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataitem.dt}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value=" #{dataitem.originDt}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataitem.respondDt}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataitem.amount}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataitem.enterBY }"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value=" #{dataitem.details}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataitem.authBy}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataitem.lastUpdateBy}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataitem.lastUpdateDt}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataitem.instNo}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataitem.entrytype}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataitem.ty}"/>
                            </rich:column>
                            <rich:column style="text-align:center;width:40px">
                                <a4j:commandLink  reRender="taskList1" ajaxSingle="true" id="selectlink" onclick="#{rich:component('deletePanelGrid')}.show();">
                                    <h:graphicImage   id="imagerender"  value="/resources/images/delete.gif" style="border:0"/>
                                    <f:setPropertyActionListener value="#{dataitem}" target="#{HoExtractEntry.authorized}"/>
                                </a4j:commandLink>
                            </rich:column>
                        </rich:dataTable>
                        <rich:datascroller align="left" for="taskList1" maxPages="20"/>
                        <rich:modalPanel id="deletePanelGrid" autosized="true" width="200">
                            <f:facet name="header">
                                <h:outputText value="Do you want to delete this record ?" style="padding-right:15px;" />
                            </f:facet>
                            <f:facet name="controls">
                                <h:panelGroup>
                                    <h:graphicImage value="/images/close.png" styleClass="hidelink" id="hidelink1" />
                                    <rich:componentControl for="deletePanelGrid" attachTo="hidelink1" operation="hide" event="onclick" />
                                </h:panelGroup>
                            </f:facet>
                            <h:form>
                                <table width="100%">
                                    <tbody>
                                        <tr>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="Yes" ajaxSingle="true" action="#{HoExtractEntry.deleteRowValues()}"
                                                                   onclick="#{rich:component('deletePanelGrid')}.hide();" reRender="taskList1"/>
                                            </td>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="Cancel" onclick="#{rich:component('deletePanelGrid')}.hide();return false;" />
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </h:form>
                        </rich:modalPanel>
                        <rich:modalPanel id="savePanelGrid" autosized="true" width="200">
                            <f:facet name="header">
                                <h:outputText value="Do you want to save these records ?" style="padding-right:15px;"/>
                            </f:facet>
                            <f:facet name="controls">
                                <h:panelGroup>
                                    <h:graphicImage value="/images/close.png" styleClass="hidelink" id="hidelink12" />
                                    <rich:componentControl for="savePanelGrid" attachTo="hidelink12" operation="hide" event="onclick" />
                                </h:panelGroup>
                            </f:facet>
                            <h:form>
                                <table width="100%">
                                    <tbody>
                                        <tr>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="Yes" ajaxSingle="true" action="#{HoExtractEntry.save}"
                                                                   onclick="#{rich:component('savePanelGrid')}.hide();"  reRender="errMsg11,taskList1,extractsave"/>
                                            </td>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="Cancel" onclick="#{rich:component('savePanelGrid')}.hide();return false;" />
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </h:form>
                        </rich:modalPanel>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel21" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel21">
                            <a4j:commandButton disabled="#{HoExtractEntry.save}"  id="extractsave" oncomplete="#{rich:component('savePanelGrid')}.show();"  reRender="HoextractPanel14"  value="Save" focus="HoextractPanel14"/>
                            <a4j:commandButton id="refresh"  value="Refresh"  action="#{HoExtractEntry.refresh()}" reRender="HoextractPanel14,HoextractPanel16,calToDatenew,
                                               calToDatenew1,HoextractPanel24,HoextractPanel26,HoextractPanel28,HoextractPanel30,HoextractPanel32,HoextractPanel36,HoextractPanel33,
                                               nwHoextractPanel12,newHoextractPanel12,errMsg11,extractsave"/>
                            <a4j:commandButton  id="extractExit" value=" Exit"  action="#{HoExtractEntry.exitBtnAction}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>

