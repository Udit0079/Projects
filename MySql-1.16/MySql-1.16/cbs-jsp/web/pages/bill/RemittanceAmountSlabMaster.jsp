<%--
    Document   : AmountSlab
    Created on : Aug 6, 2010, 11:50:06 AM
    Author     : Zeeshan Waris
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
            <title>Amount Slab</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>

        </head>
        <body>
            <a4j:form id="Form">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{RemittanceAmountSlabMaster.todayDate}" />
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Amount Slab"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{RemittanceAmountSlabMaster.userName}"  />
                        </h:panelGroup>
                    </h:panelGrid>



                    <h:panelGrid  columns="1" id="bodyPanel" style="width:100%;">
                        <h:panelGrid columnClasses="col11"  columns="1" id="msggrid" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputText  id="stxtMssg"   value="#{RemittanceAmountSlabMaster.mssg}" styleClass="error"/>
                        </h:panelGrid>
                        <h:panelGrid  columns="2" id="subbodyPanel1" style="width:100%;">
                            <h:panelGrid columnClasses="col7" id="leftPanel" columns="1" width="100%" >
                                <h:panelGrid columnClasses="col11" columns="2" id="Panel1" style="width:100%;" styleClass="row2">
                                    <h:outputLabel id="lblInstrumentCode" styleClass="label"  value="Instrument code"/>
                                    <h:selectOneListbox id="lstInstrumentCode" style="width:112px" styleClass="ddlist" size="1"  value="#{RemittanceAmountSlabMaster.insmntCode}"  >
                                        <f:selectItems value="#{RemittanceAmountSlabMaster.instrumentCodeOption}"/>
                                        <a4j:support actionListener="#{RemittanceAmountSlabMaster.getInstSlabDetails}" event="onblur"
                                                     limitToList="true"  reRender="gridPanel103,taskList,txtFrom,msggrid,stxtMssg,txtFrom,chkShowAllEntries"/>
                                    </h:selectOneListbox>
                                </h:panelGrid>

                                <h:panelGrid columnClasses="col11" columns="2" id="Panel2" style="width:100%;" styleClass="row1">
                                    <h:outputLabel id="lblFrom" styleClass="label"  value="From"/>
                                    <h:inputText id="txtFrom" style="width:110px"  value="#{RemittanceAmountSlabMaster.from}"  styleClass="input" disabled="#{RemittanceAmountSlabMaster.fromAmountDisable}" maxlength="9"/>
                                </h:panelGrid>

                                <h:panelGrid columnClasses="col11" columns="2" id="Panel3" style="width:100%;" styleClass="row2">
                                    <h:outputLabel id="lblTo" styleClass="label"  value="To"/>
                                    <h:inputText id="txtTo" style="width:110px" value="#{RemittanceAmountSlabMaster.to}" styleClass="input" maxlength="9">
                                        <a4j:support actionListener="#{RemittanceAmountSlabMaster.onblurChecking}" event="onblur"
                                                     oncomplete="if(#{rich:element('txtTo')}.value == ''){#{rich:element('txtTo')}.focus();}else{#{rich:element('calAsOn')}.focus();}"
                                                     reRender="txtTo,stxtMssg"/>
                                    </h:inputText>
                                </h:panelGrid>

                                <h:panelGrid columnClasses="col11" columns="2" id="bodyPane3" style="width:100%;" styleClass="row1">
                                    <h:outputLabel id="lblAsOn" styleClass="label" value="Effective Date"/>
                                    <rich:calendar datePattern="dd/MM/yyyy" id="calAsOn" value ="#{RemittanceAmountSlabMaster.asOnDate}" inputSize="10">
                                    </rich:calendar>
                                </h:panelGrid>
                            </h:panelGrid>

                            <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel103" width="100%" styleClass="row2" style="height:168px;">
                                <a4j:region>
                                    <rich:dataTable  var="dataItem" value="#{RemittanceAmountSlabMaster.list}"
                                                     rowClasses="row1, row2" id="taskList" rows="3" columnsWidth="80" rowKeyVar="row"
                                                     onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                     onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                                     onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;
                                                     #{rich:component('menu')}.show(event,{sNo:'#{item.sno}', currentRow:'#{row}'});return false;">
                                        <f:facet name="header">
                                            <rich:columnGroup>
                                                <rich:column colspan="7"><h:outputText value="Remmitance Type Master" /></rich:column>
                                                <rich:column breakBefore="true"><h:outputText value="Inst Code" /></rich:column>
                                                <rich:column><h:outputText value="Slab Code" /></rich:column>
                                                <rich:column><h:outputText value="Amt Frm" /></rich:column>
                                                <rich:column><h:outputText value="Amt To" /></rich:column>
                                                <rich:column><h:outputText value="Eff dt" /></rich:column>
                                                <rich:column><h:outputText value="Flag" /></rich:column>
                                                <rich:column><h:outputText value="Action" /></rich:column>
                                            </rich:columnGroup>
                                        </f:facet>
                                        <rich:column><h:outputText value="#{dataItem.instCode}"  /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.slabCode}"/></rich:column>
                                        <rich:column><h:outputText  value="#{dataItem.amtFrom}" /></rich:column>
                                        <rich:column ><h:outputText value="#{dataItem.amtTo}"  /></rich:column>
                                        <rich:column ><h:outputText value="#{dataItem.effDt}"/></rich:column>
                                        <rich:column ><h:outputText value="#{RemittanceAmountSlabMaster.flag}"/></rich:column>
                                        <rich:column>
                                            <a4j:commandLink ajaxSingle="true" id="deletelink" oncomplete="#{rich:component('deletePanel')}.show()">
                                                <h:graphicImage value="/resources/images/delete.gif" style="border:0"/>
                                                <f:setPropertyActionListener value="#{dataItem}" target="#{RemittanceAmountSlabMaster.currentItem}"/>
                                                <f:setPropertyActionListener value="#{row}" target="#{RemittanceAmountSlabMaster.currentRow}" />
                                            </a4j:commandLink>
                                        </rich:column>

                                    </rich:dataTable>
                                    <rich:datascroller align="left" id="datascroller" for="taskList" maxPages="20" />
                                </a4j:region>
                                <rich:modalPanel id="deletePanel" autosized="true" width="200" onshow="#{rich:element('delYes')}.focus()">
                                    <f:facet name="header">
                                        <h:outputText value="Confirmation Dialog Box" style="padding-right:15px;" />
                                    </f:facet>
                                    <f:facet name="controls">
                                        <h:panelGroup>
                                            <h:graphicImage value="/images/modal/close.png" styleClass="hidelink" id="hidelink2" />
                                            <rich:componentControl for="deletePanel"  attachTo="hidelink2" operation="hide" event="onclick"/>
                                        </h:panelGroup>
                                    </f:facet>
                                    <h:form>
                                        <table width="100%">
                                            <tbody>
                                                <tr style="height:40px">
                                                    <td colspan="2">
                                                        <h:outputText value="Are you sure, You want to delete selected slab?"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td align="center" width="50%">
                                                        <a4j:commandButton id="delYes" value="Yes" ajaxSingle="true" action="#{RemittanceAmountSlabMaster.delete}"
                                                                           oncomplete="#{rich:component('deletePanel')}.hide();" reRender="gridPanel103,stxtMssg" />
                                                    </td>
                                                    <td align="center" width="50%">
                                                        <a4j:commandButton id="delNo" value="Cancel" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </h:form>
                                </rich:modalPanel>
                            </h:panelGrid>
                        </h:panelGrid>

                        <h:panelGrid columnClasses="col1" columns="1" id="bodyPaneIndicator" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputLabel id="lblIndicator" styleClass="label"  value="To Delete Entery From The Table, Click In Action Colomn In the Grid At Cross Image"/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="BtnPanel">
                            <a4j:commandButton id="btnSave" value="Save" action="#{RemittanceAmountSlabMaster.saveClick}" reRender="stxtMssg,mainPanel" focus="ddInstumentCode"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{RemittanceAmountSlabMaster.refreshBtn}" 
                                               reRender="mainPanel,chkShowAllEntries" focus="lstInstrumentCode"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{RemittanceAmountSlabMaster.exitForm}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
