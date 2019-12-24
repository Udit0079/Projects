<%-- 
    Document   : Demand Marking
    Created on : 14 Oct, 2010, 1:28:05 PM
    Author     : root
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
            <title>Demand Marking</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="custForm1">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="grpPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{DemandMarkingManaged.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Demand Marking"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output"  value="#{DemandMarkingManaged.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorGrid" style="width:100%;text-align:centre;" styleClass="row1">
                        <h:outputText id="stxtError" styleClass="error" value="#{DemandMarkingManaged.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid  columns="3" columnClasses="col10,col10,col10"id="stmtDmndMrkngGrid" style="width:100%;text-align:centre;" styleClass="row2">
                        <h:outputLabel/>
                        <h:outputLabel id="lblStmtDmndMrkng"styleClass="label" value="Statement For Demand Marking"/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid  columns="4" columnClasses="col2,col7,col2,col7"id="accNoPartyGrid" style="width:100%;text-align:centre;" styleClass="row1">
                        <h:outputLabel id="lblaccNo"styleClass="label" value="Account No."/>
                        <h:inputText  maxlength="12"id="txtaccNo" styleClass="input" value="#{DemandMarkingManaged.accNo}" onkeyup="this.value=this.value.toUpperCase()">
                            <a4j:support action="#{DemandMarkingManaged.setdataForPresentOtgAndPartyName}" event="onblur" oncomplete="if(#{DemandMarkingManaged.acNoFlag=='true'}){#{rich:element('txtaccNo')}.focus();}"reRender="stxtParty,stxtError,txtPrinOut,txtIntOut,txtChgsOut,txtTotalOut,txtDemandPrin,txtDemandInt,txtDemandChgs,txtTotalDemand,calAsOn"/>
                        </h:inputText>
                        <h:outputLabel id="lblParty"styleClass="label" value="Party Name"/>
                        <h:outputText id="stxtParty" styleClass="output" value="#{DemandMarkingManaged.partyName}"style="color:green;"/>
                    </h:panelGrid>
                    <h:panelGrid  columns="4" columnClasses="col2,col7,col2,col7"id="presentOutDemandGrid" style="width:100%;text-align:centre;" styleClass="row2">
                        <h:outputLabel/>
                        <h:outputLabel id="lblpresentOut"styleClass="label" value="Present Outstanding"/>
                        <h:outputLabel/>
                        <h:outputLabel id="lblDemand"styleClass="label" value="Demand"/>
                    </h:panelGrid>
                    <h:panelGrid  columns="4" columnClasses="col2,col7,col2,col7"id="demandMarkingGrid2" style="width:100%;text-align:centre;" styleClass="row1">
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel id="lblAsOn"styleClass="label" value="As On #"/>
                        <rich:calendar id="calAsOn" datePattern="dd/MM/yyyy"value="#{DemandMarkingManaged.asOnDate}">
                            <a4j:support  event="onblur" focus="txtDemandPrin"/>
                        </rich:calendar>
                    </h:panelGrid>
                    <h:panelGrid  columns="4" columnClasses="col2,col7,col2,col7"id="demandMarkingGrid" style="width:100%;text-align:centre;" styleClass="row2">
                        <h:outputLabel id="lblPrinOut"styleClass="label" value="Principal Outstanding"/>
                        <h:inputText id="txtPrinOut" styleClass="input" value="#{DemandMarkingManaged.otgPrinAmt}" disabled="true">
                        </h:inputText>
                        <h:outputLabel id="lblDemandPrin"styleClass="label" value="Demand Principal"/>
                        <h:inputText id="txtDemandPrin" styleClass="input" value="#{DemandMarkingManaged.demPrinAmt}">
                            <a4j:support action="#{DemandMarkingManaged.demPrinLostFocus}" event="onblur"  reRender="txtTotalDemand,stxtError" focus="txtDemandInt"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid  columns="4" columnClasses="col2,col7,col2,col7"id="demandMarkingGrid3" style="width:100%;text-align:centre;" styleClass="row1">
                        <h:outputLabel id="lblIntOut"styleClass="label" value="Interest Outstanding"/>
                        <h:inputText id="txtIntOut" styleClass="input" value="#{DemandMarkingManaged.otgIntAmt}"disabled="true">
                        </h:inputText>
                        <h:outputLabel id="lblDemandInt"styleClass="label" value="Demand Interest"/>
                        <h:inputText id="txtDemandInt" styleClass="input" value="#{DemandMarkingManaged.demIntAmt}">
                            <a4j:support action="#{DemandMarkingManaged.demIntLostFocus}" event="onblur"  reRender="txtTotalDemand,stxtError" focus="txtDemandChgs"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid  columns="4" columnClasses="col2,col7,col2,col7"id="demandMarkingGrid4" style="width:100%;text-align:centre;" styleClass="row2">
                        <h:outputLabel id="lblChgsOut"styleClass="label" value="Charges Outstanding"/>
                        <h:inputText id="txtChgsOut" styleClass="input" value="#{DemandMarkingManaged.otgChg}"disabled="true">
                        </h:inputText>
                        <h:outputLabel id="lblDemandChgs"styleClass="label" value="Demand Charges"/>
                        <h:inputText id="txtDemandChgs" styleClass="input" value="#{DemandMarkingManaged.demChg}">
                            <a4j:support action="#{DemandMarkingManaged.demChgLostFocus}" event="onblur"  reRender="txtTotalDemand,stxtError" focus="txtTotalDemand"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid  columns="4" columnClasses="col2,col7,col2,col7"id="demandMarkingGrid5" style="width:100%;text-align:centre;" styleClass="row1">
                        <h:outputLabel id="lblTotalOut"styleClass="label" value="Total Outstanding"/>
                        <h:inputText id="txtTotalOut" styleClass="input" value="#{DemandMarkingManaged.otgTotalAmt}"disabled="true">
                        </h:inputText>
                        <h:outputLabel id="lblTotalDemand"styleClass="label" value="Total Demand"/>
                        <h:inputText id="txtTotalDemand" styleClass="input" value="#{DemandMarkingManaged.demTotalAmt}" disabled="true">
                            <a4j:support event="onblur" focus="btnClick"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid  columns="4" columnClasses="col2,col7,col2,col7"id="demandMarkingGrid6" style="width:100%;text-align:centre;" styleClass="row2">
                        <h:panelGroup id="authPanelGroup" layout="block">
                            <h:selectBooleanCheckbox id="chkAuth" value="#{DemandMarkingManaged.chkAuthValue}">
                                <a4j:support event="onclick"  ajaxSingle="true"action="#{DemandMarkingManaged.chkAuthBoxClick}" reRender="taskList,btnSave" oncomplete="if(#{DemandMarkingManaged.isGridEmpty=='False'}){#{rich:component('showPanelForGrid2')}.show();} else if(#{DemandMarkingManaged.isGridEmpty=='True'}){#{rich:component('showPanelForGrid2')}.hide();}"/>
                            </h:selectBooleanCheckbox>
                            <h:outputLabel id="lblAuth" value="Click here for all pending authorizations." styleClass="output"/>
                        </h:panelGroup>
                        <h:outputLabel/>
                        <a4j:commandButton id="btnClick" value="Click" reRender="taskList,stxtError,chkAuth,btnSave" action="#{DemandMarkingManaged.setDataIntoGridOnClickButton}">
                        </a4j:commandButton>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="gridPanelTable" width="100%" styleClass="row2" style="height:200px;">
                        <rich:contextMenu attached="false" id="menu" submitMode="ajax" oncollapse="row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'">
                            <rich:menuItem value="Delete Record" ajaxSingle="true"actionListener="#{DemandMarkingManaged.fetchCurrentRow}">
                                <a4j:actionparam name="accCode" value="{accountNo}" />
                                <a4j:actionparam name="row" value="{currentRow}" />
                            </rich:menuItem>
                        </rich:contextMenu>
                        <rich:dataTable  value="#{DemandMarkingManaged.dmTableList}"var="dataItem" rowClasses="gridrow1, gridrow2" id="taskList"  columnsWidth="100"
                                         rowKeyVar="row" onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                         onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%" >
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="8"><h:outputText value="Demand Marking" /></rich:column>
                                    <rich:column breakBefore="true"> <h:outputText value="A/C No." /> </rich:column> 
                                    <rich:column><h:outputText value="Party Name" /> </rich:column>
                                    <rich:column><h:outputText value="Demand Date" /></rich:column>
                                    <rich:column><h:outputText value="Principal Demand" /></rich:column>
                                    <rich:column><h:outputText value="Interest Demand" /></rich:column>
                                    <rich:column><h:outputText value="Charges Demand" /></rich:column>
                                    <rich:column><h:outputText value="Delete Record" /></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{dataItem.accountNo}"/></rich:column>
                            <rich:column><h:outputText  value="#{dataItem.partyName}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.demandDate}" /></rich:column>
                            <rich:column><h:outputText  value="#{dataItem.prinDemand}" /></rich:column>
                            <rich:column ><h:outputText  value="#{dataItem.intDemand}" /></rich:column>
                            <rich:column ><h:outputText  value="#{dataItem.chgDemand}" /></rich:column>
                            <rich:column style="text-align:center;width:40px">
                                <a4j:commandLink ajaxSingle="true" id="selectlink" onclick="#{rich:component('showPanelForGrid')}.show();">
                                    <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                    <f:setPropertyActionListener value="#{dataItem}" target="#{DemandMarkingManaged.dmTable}"/>
                                    <f:setPropertyActionListener value="#{row}" target="#{DemandMarkingManaged.currentRow}"/>
                                </a4j:commandLink>
                                <rich:toolTip for="selectlink" value="Select This Row"/>
                            </rich:column>
                        </rich:dataTable>
                        <rich:datascroller  align="left" for="taskList" maxPages="2" />
                        <rich:modalPanel id="showPanelForGrid" autosized="true" width="200">
                            <f:facet name="header">
                                <h:outputText value="Do you want to delete this record ?" style="padding-right:15px;" />
                            </f:facet>
                            <f:facet name="controls">
                                <h:panelGroup>
                                    <h:graphicImage value="/images/close.png" styleClass="hidelink" id="hidelink1" />
                                    <rich:componentControl for="showPanelForGrid" attachTo="hidelink1" operation="hide" event="onclick" />
                                </h:panelGroup>
                            </f:facet>
                            <h:form>
                                <table width="100%">
                                    <tbody>
                                        <tr>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="Yes" ajaxSingle="true" action="#{DemandMarkingManaged.removeCurrentRow}"
                                                                   onclick="#{rich:component('showPanelForGrid')}.hide();" reRender="taskList"/>
                                            </td>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="Cancel" onclick="#{rich:component('showPanelForGrid')}.hide();return false;" />
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </h:form>
                        </rich:modalPanel>
                        <rich:modalPanel id="showPanelForGrid2" autosized="true" width="200">
                            <f:facet name="header">
                                <h:outputText value="Do you want to save the record on the Grid?" style="padding-right:15px;" />
                            </f:facet>
                            <f:facet name="controls">
                                <h:panelGroup>
                                    <h:graphicImage value="/images/close.png" styleClass="hidelink" id="hidelink2" />
                                    <rich:componentControl for="showPanelForGrid2" attachTo="hidelink2" operation="hide" event="onclick" />
                                </h:panelGroup>
                            </f:facet>
                            <h:form>
                                <table width="100%">
                                    <tbody>
                                        <tr>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="Yes"ajaxSingle="true"action="#{DemandMarkingManaged.saveDataFromGridAfterPopUp}"
                                                                   onclick="#{rich:component('showPanelForGrid2')}.hide();" reRender="taskList,stxtError,btnSave,txtaccNo,stxtParty,calAsOn,txtPrinOut,txtDemandPrin,txtIntOut,txtDemandInt,txtChgsOut,txtDemandChgs,txtTotalOut,txtTotalDemand"/>
                                            </td>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="Cancel" ajaxSingle="true"action="#{DemandMarkingManaged.discardDataFromGridAfterPopUp}"onclick="#{rich:component('showPanelForGrid2')}.hide();"reRender="taskList,stxtError,btnSave,txtPrinOut,txtDemandPrin,txtIntOut,txtDemandInt,txtChgsOut,txtDemandChgs,txtTotalOut,txtTotalDemand"/>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </h:form>
                        </rich:modalPanel>
                    </h:panelGrid>
                    <h:panelGrid id="demandMarkingGrid7" style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel id="lblDoubleClick"styleClass="label" value="Click on the particular row of the grid to DELETE the record."/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnSave" disabled="#{DemandMarkingManaged.flag}" value="Save"  action="#{DemandMarkingManaged.saveDataFromGridInTable}" reRender="stxtError,txtaccNo,stxtParty,calAsOn,txtPrinOut,txtDemandPrin,txtIntOut,txtDemandInt,txtChgsOut,txtDemandChgs,txtTotalOut,txtTotalDemand,taskList">

                            </a4j:commandButton>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{DemandMarkingManaged.formRefresh}" reRender="stxtError,txtaccNo,stxtParty,calAsOn,txtPrinOut,txtDemandPrin,txtIntOut,txtDemandInt,txtChgsOut,txtDemandChgs,txtTotalOut,txtTotalDemand,taskList">

                            </a4j:commandButton>
                            <a4j:commandButton id="btnClose" value="Close" action="#{DemandMarkingManaged.exitForm}" reRender="stxtError,txtaccNo,stxtParty,calAsOn,txtPrinOut,txtDemandPrin,txtIntOut,txtDemandInt,txtChgsOut,txtDemandChgs,txtTotalOut,txtTotalDemand,taskList">

                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
