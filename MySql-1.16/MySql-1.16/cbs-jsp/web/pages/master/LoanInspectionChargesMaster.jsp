<%-- 
    Document   : LoanInspectionChargesMaster
    Created on : 22 Mar, 2011, 4:32:46 PM
    Author     : Zeeshan Waris[zeeshan.glorious@gmail.com]
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

            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <title>Loan Inspection Charges Master</title>
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{LoanInspectionChargesMaster.todayDate}" />
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Loan Inspection Charges Master"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{LoanInspectionChargesMaster.userName}"  />
                        </h:panelGroup>
                    </h:panelGrid>



                    <h:panelGrid  columns="1" id="bodyPanel" style="width:100%;">
                        <h:panelGrid columnClasses="col11"  columns="1" id="msggrid" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputText  id="stxtMssg"   value="#{LoanInspectionChargesMaster.message}" styleClass="error"/>
                        </h:panelGrid>
                        <h:panelGrid  columns="2" id="subbodyPanel1" style="width:100%;">
                            <h:panelGrid columnClasses="col7" id="leftPanel" columns="1" width="100%" >
                                <h:panelGrid columnClasses="col10,col11" columns="2" id="Panel1" style="width:100%;" styleClass="row2">
                                    <h:outputLabel id="lblInstrumentCode" styleClass="label"  value="A/C Type"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox id="lstInstrumentCode" style="width:90px" styleClass="ddlist" size="1"  value="#{LoanInspectionChargesMaster.insmntCode}">
                                       <f:selectItems value="#{LoanInspectionChargesMaster.instrumentCodeOption}"/>
                                       <a4j:support  event="onchange" action="#{LoanInspectionChargesMaster.onClick}" reRender="gridPanel1030"/>
                                    </h:selectOneListbox>
                                </h:panelGrid>

                                <h:panelGrid columnClasses="col10,col11" columns="2" id="bodyPane3" style="width:100%;" styleClass="row2">
                                    <h:outputLabel id="lblAsOn" styleClass="label" value="Applicable Date"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <rich:calendar datePattern="dd/MM/yyyy" id="calAsOn" value ="#{LoanInspectionChargesMaster.asOnDate}" inputSize="10">
                                    </rich:calendar>
                                </h:panelGrid>

                                 <h:panelGrid columnClasses="col10,col11" columns="2" id="Panel21" style="width:100%;" styleClass="row1">
                                    <h:outputLabel id="lblchargesAmt" styleClass="label"  value="Charges Amount"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:inputText id="txtchargesAmt" style="width:90px"  value="#{LoanInspectionChargesMaster.chargesAmt}"  styleClass="input">
                                        <a4j:support action="#{LoanInspectionChargesMaster.setIntoFrom}" event="onblur"
                                                     reRender="gridPanel103,taskList,txtFrom,msggrid,stxtMssg,txtFrom,chkShowAllEntries"/>
                                        </h:inputText>
                                </h:panelGrid>

                                <h:panelGrid columnClasses="col10,col11" columns="2" id="Panel2" style="width:100%;" styleClass="row2">
                                    <h:outputLabel id="lblFrom" styleClass="label"  value="Amount From"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:inputText id="txtFrom" style="width:90px"  value="#{LoanInspectionChargesMaster.from}"  styleClass="input" disabled="#{LoanInspectionChargesMaster.fromAmountDisable}"/>
                                </h:panelGrid>

                                <h:panelGrid columnClasses="col10,col11" columns="2" id="Panel3" style="width:100%;" styleClass="row1">
                                    <h:outputLabel id="lblTo" styleClass="label"  value="Amount To"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:inputText id="txtTo" style="width:90px" value="#{LoanInspectionChargesMaster.to}" styleClass="input" maxlength="9" >
                                        <a4j:support action="#{LoanInspectionChargesMaster.tableData}" event="onblur"
                                                     oncomplete="if(#{rich:element('txtTo')}.value == ''){#{rich:element('txtTo')}.focus();}else{#{rich:element('calAsOn')}.focus();}"
                                                     reRender="txtTo,stxtMssg,gridPanel103,taskList,datascroller,stxtMssg,txtTo,txtFrom,btnSave,Panel21" focus="btnSave"/>
                                    </h:inputText>
                                </h:panelGrid>
                            </h:panelGrid>

                            <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel103" width="100%" styleClass="row2" style="height:168px;">
                                <a4j:region>
                                    <rich:dataTable  var="dataItem" value="#{LoanInspectionChargesMaster.list}"
                                                     rowClasses="row1, row2" id="taskList" rows="3" columnsWidth="80" rowKeyVar="row"
                                                     onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                     onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                                     onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;
                                                     #{rich:component('menu')}.show(event,{sNo:'#{item.sno}', currentRow:'#{row}'});return false;">
                                        <f:facet name="header">
                                            <rich:columnGroup>
                                                 <rich:column><h:outputText value="Acount No." /></rich:column>
                                                <rich:column><h:outputText value="Roi" /></rich:column>
                                                <rich:column><h:outputText value="Amt From" /></rich:column>
                                                <rich:column><h:outputText value="Amt To" /></rich:column>
                                                <rich:column><h:outputText value="Applicable Date" /></rich:column>
                                                <rich:column><h:outputText value="Enter By" /></rich:column>
                                                <rich:column><h:outputText value="Transaction Time" /></rich:column>
                                                <rich:column><h:outputText value="Action" /></rich:column>
                                            </rich:columnGroup>
                                        </f:facet>
                                        <rich:column><h:outputText value="#{dataItem.acno}"/></rich:column>
                                        <rich:column><h:outputText  value="#{dataItem.roi}" /></rich:column>
                                        <rich:column ><h:outputText value="#{dataItem.slabciel}"  /></rich:column>
                                        <rich:column ><h:outputText value="#{dataItem.slabFloor}"/></rich:column>
                                        <rich:column ><h:outputText value="#{dataItem.fromDate}"/></rich:column>
                                        <rich:column ><h:outputText value="#{dataItem.enterby}"/></rich:column>
                                        <rich:column ><h:outputText value="#{dataItem.trantime}"/></rich:column>
                                        <rich:column>
                                            <a4j:commandLink ajaxSingle="true" id="deletelink" oncomplete="#{rich:component('deletePanel')}.show()">
                                                <h:graphicImage value="/resources/images/delete.gif" style="border:0"/>
                                                <f:setPropertyActionListener value="#{dataItem}" target="#{LoanInspectionChargesMaster.currentItem}"/>
                                                <f:setPropertyActionListener value="#{row}" target="#{LoanInspectionChargesMaster.currentRow}" />
                                            </a4j:commandLink>
                                        </rich:column>
                                    </rich:dataTable>
                                    <rich:datascroller align="left" id="datascroller" for="taskList" maxPages="20" />
                                </a4j:region>
                                <rich:modalPanel id="deletePanel" autosized="true" width="200" onshow="#{rich:element('delYes')}.focus()">
                                    <f:facet name="header">
                                        <h:outputText value="Are You Sure To delete This Row?" style="padding-right:15px;" />
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
                                                <tr>
                                                    <td align="center" width="50%">
                                                        <a4j:commandButton id="delYes" value="Yes" ajaxSingle="true" action="#{LoanInspectionChargesMaster.delete}"
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
                        <h:panelGrid columnClasses="col1,col2" columns="2" id="bodyPaneIndicator" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputLabel id="lblIndicator" styleClass="label"  value="To Delete Entery From The Table, Click In Action Colomn In the Grid At Cross Image"/>
                        </h:panelGrid>

                         <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel1030" width="100%" styleClass="row2" style="height:168px;">
                                <a4j:region>
                                    <rich:dataTable  var="dataItem1" value="#{LoanInspectionChargesMaster.datalist}"
                                                     rowClasses="row1, row2" id="taskList1" rows="3" columnsWidth="80" rowKeyVar="row"
                                                     onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                     onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                                     onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;
                                                     #{rich:component('menu')}.show(event,{sNo:'#{item.sno}', currentRow:'#{row}'});return false;">
                                        <f:facet name="header">
                                            <rich:columnGroup>
                                                <rich:column colspan="8"><h:outputText value="Loan Inspection charges Record table"  style="color:navy"/></rich:column>
                                                <rich:column breakBefore="true"><h:outputText value="RecNo" /></rich:column>
                                                 <rich:column><h:outputText value="Acount No." /></rich:column>
                                                <rich:column><h:outputText value="Roi" /></rich:column>
                                                <rich:column><h:outputText value="Amt From" /></rich:column>
                                                <rich:column><h:outputText value="Amt To" /></rich:column>
                                                <rich:column><h:outputText value="Applicable Date" /></rich:column>
                                                <rich:column><h:outputText value="Enter By" /></rich:column>
                                                <rich:column><h:outputText value="Transaction Time" /></rich:column>
                                            </rich:columnGroup>
                                        </f:facet>
                                         <rich:column><h:outputText value="#{dataItem1.recno}"/></rich:column>
                                        <rich:column><h:outputText value="#{dataItem1.acno}"/></rich:column>
                                        <rich:column><h:outputText  value="#{dataItem1.roi}" /></rich:column>
                                        <rich:column ><h:outputText value="#{dataItem1.slabciel}"  /></rich:column>
                                        <rich:column ><h:outputText value="#{dataItem1.slabFloor}"/></rich:column>
                                        <rich:column ><h:outputText value="#{dataItem1.fromDate}"/></rich:column>
                                         <rich:column ><h:outputText value="#{dataItem1.enterby}"/></rich:column>
                                         <rich:column ><h:outputText value="#{dataItem1.trantime}"/></rich:column>
                                    </rich:dataTable>
                                    <rich:datascroller align="left" id="datascroller1" for="taskList" maxPages="20" />
                                </a4j:region>
                            </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="BtnPanel">
                            <a4j:commandButton id="btnSave" value="Save" disabled="#{LoanInspectionChargesMaster.btnDisable}" action="#{LoanInspectionChargesMaster.saveClick}" 
                                               reRender="stxtMssg,mainPanel" focus="lstInstrumentCode"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{LoanInspectionChargesMaster.refreshBtn}" reRender="mainPanel" focus="lstInstrumentCode"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{LoanInspectionChargesMaster.exitForm}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>

            <rich:modalPanel id="savePanel" autosized="true" width="200" onshow="#{rich:element('saveYes1')}.focus()">
                <f:facet name="header">
                    <h:outputText value="Do you want to See The data?" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%">
                        <tbody>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton id="saveYes1" value="Yes" action="#{RemittanceAmountSlabMaster.onChkBxClick}"
                                                       oncomplete="#{rich:component('savePanel')}.hide();return false;"   reRender="gridPanel103,taskList,datascroller,stxtMssg,lstInstrumentCode,chkShowAllEntries" />
                                </td>
                                <td align="center" width="50%">
                                     <a4j:commandButton id="saveNo" value="Cancel" onclick="#{rich:component('savePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>

        </body>
    </html>
</f:view>
