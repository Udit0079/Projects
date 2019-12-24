<%--
    Document   : TdsEntry
    Created on : Aug 9, 2010, 3:47:19 PM
    Author     : jitendra chaudhary
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
            <title>TDS Entry For Single Account</title>
            <script type="text/javascript">
            </script>
        </head>
        <body>
            <a4j:form id="form3">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="header" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{TdsEntryForSingle.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblName" styleClass="headerLabel" value="TDS Entry For Single A/c"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User:"/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{TdsEntryForSingle.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel1" width="100%">
                        <h:panelGrid columns="1" id="gridPanel4" width="100%">
                            <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel15" style="height:30px;" styleClass="row1" width="100%">   
                                <h:outputLabel id="lblAcNo" styleClass="label" value="Account No"/>
                                <h:panelGroup>
                                    <h:inputText id="txtAcNo" styleClass="input" value="#{TdsEntryForSingle.oldAcNo}" maxlength="#{TdsEntryForSingle.acNoMaxLen}" size="#{TdsEntryForSingle.acNoMaxLen}">
                                        <a4j:support actionListener="#{TdsEntryForSingle.getAccDetail}" event="onblur"  reRender="stxtCustName,stxtJtName,stxtOpMode,taskList1,gridPanelTable2,stxtError,stxtAcNo,txtRtNo,txtTdsAmt" 
                                                     oncomplete="if(#{TdsEntryForSingle.accountFlag == 1}) {#{rich:element('txtAcNo')}.focus();}
                                                     else if(#{TdsEntryForSingle.rtFlag == 1}){#{rich:element('txtRtNo')}.focus();}
                                                     else if(#{TdsEntryForSingle.rtFlag == 0}){#{rich:element('txtTdsAmt')}.focus();}"  />
                                    </h:inputText>
                                    <h:outputText id="stxtAcNo" styleClass="output" value="#{TdsEntryForSingle.fullAcno}"/>
                                </h:panelGroup>
                            </h:panelGrid>

                            <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel16" style="height:30px;" styleClass="row2" width="100%">
                                <h:outputLabel id="lblCustName" styleClass="label" value="Customer Name"/>
                                <h:outputText id="stxtCustName" styleClass="output" value="#{TdsEntryForSingle.custName}"/>
                            </h:panelGrid>

                            <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel17" style="height:30px;" styleClass="row1" width="100%">
                                <h:outputLabel id="lblJtName" styleClass="label" value="Joint Name"/>
                                <h:outputText id="stxtJtName" styleClass="output" value="#{TdsEntryForSingle.jointName}" />
                            </h:panelGrid>

                            <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel18" style="height:30px;" styleClass="row2" width="100%">
                                <h:outputLabel id="lblOpMode" styleClass="label" value="Opreation Mode"/>
                                <h:outputText id="stxtOpMode" styleClass="output" value="#{TdsEntryForSingle.opMode}" />
                            </h:panelGrid>

                            <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel19" style="height:30px;" styleClass="row1" width="100%">
                                <h:outputLabel id="lblRtNo" styleClass="label" value="R.T.No."/>
                                <h:inputText id="txtRtNo" styleClass="input" style="width:80px" value="#{TdsEntryForSingle.rtNo}" maxlength="14" disabled="#{TdsEntryForSingle.rtDisab}">
                                                     
                                </h:inputText>
                            </h:panelGrid>

                            <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel20" style="height:30px;" styleClass="row2" width="100%">
                                <h:outputLabel id="lblTdsAmt" styleClass="label" value="TDS Amount"/>
                                <h:inputText id="txtTdsAmt" styleClass="input" style="width:80px" value="#{TdsEntryForSingle.tdsAmt}" maxlength="15">
                                    <a4j:support actionListener="#{TdsEntryForSingle.checkTdsAmount}" event="onblur"  reRender="stxtReciptNo,stxtError"/>
                                   
                                </h:inputText>
                            </h:panelGrid>

                            <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel21" style="height:30px;" styleClass="row1" width="100%">
                                <h:outputLabel id="lblReciptNo" styleClass="label" value="Receipt No."/>
                                <h:outputText id="stxtReciptNo" styleClass="output" value="#{TdsEntryForSingle.recpNo}" />
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanelLoad" style="width:100%;text-align:center;" styleClass="row2" width="100%">
                                <a4j:commandButton focus="btnLoadUnSec" id="btnLoadUnSec" value="Show Un-Recovered TDS" action="#{TdsEntryForSingle.loadGridUnSec}" reRender="gridPanelTable2" />
                                <a4j:commandButton focus="btnPost" id="btnLoad" value="LoadGrid" action="#{TdsEntryForSingle.loadGrid}" reRender="taskList,stxtTotAmt,stxtError,txtAcNo,stxtCustName,stxtJtName,stxtOpMode,gridPanelTable,txtRtNo,txtTdsAmt,gridPanelTable2,stxtReciptNo,rowAcno,rowVchNo,rowTdsUnRec" />
                            </h:panelGrid>
                        </h:panelGrid>

                        <h:panelGrid columnClasses="vtop" columns="1" id="gridPanelTable" width="100%" styleClass="row2" style="height:270px;">
                            <a4j:region>
                                <rich:dataTable value="#{TdsEntryForSingle.gdl}" var="dataItem"
                                                rowClasses="gridrow1, gridrow2" id="taskList" rows="3" columnsWidth="100" rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="6"><h:outputText value="TDS Entry" /></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="Account No" /></rich:column>
                                            <rich:column><h:outputText value="Receipt No" /></rich:column>
                                            <rich:column><h:outputText value="TDS Amount" /></rich:column>
                                            <rich:column><h:outputText value="R.T.No." /></rich:column>
                                            <rich:column><h:outputText value="IntOpt." /></rich:column>
                                            <rich:column><h:outputText value="Delete" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><f:facet name="header"></f:facet><h:outputText  value="#{dataItem.acNo}"  /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.recptNo}"   /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.tdsAmt}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.rtNo}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.intOpt}" /></rich:column>
                                    <rich:column>
                                        <a4j:commandLink ajaxSingle="true" id="deletelink" oncomplete="#{rich:component('deletePanel')}.show()">
                                            <h:graphicImage value="/resources/images/delete.gif" style="border:0"/>
                                            <f:setPropertyActionListener value="#{dataItem}" target="#{TdsEntryForSingle.currentItem}"/>
                                            <f:setPropertyActionListener value="#{row}" target="#{TdsEntryForSingle.currentRow}" />
                                        </a4j:commandLink>
                                       
                                    </rich:column>

                                </rich:dataTable>
                                <rich:datascroller align="left" for="taskList" maxPages="20" />
                            </a4j:region>

                            <rich:modalPanel id="deletePanel" autosized="true" width="200">
                                <f:facet name="header">
                                    <h:outputText value="Confirmation Dialog" />
                                </f:facet>
                                <f:facet name="controls">
                                    <h:panelGroup>
                                        <h:graphicImage value="/images/modal/close.png" styleClass="hidelink" id="hidelink2" />
                                        <rich:componentControl for="deletePanel" attachTo="hidelink2" operation="hide" event="onclick" />
                                    </h:panelGroup>
                                </f:facet>
                                <h:form>
                                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                        <tbody>
                                            <tr style="height:40px">
                                                <td colspan="2">
                                                    <h:outputText value="Do you want to delete this record?"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center" width="50%">
                                                    <a4j:commandButton value="Yes" ajaxSingle="true" action="#{TdsEntryForSingle.delete}"
                                                                       onclick="#{rich:component('deletePanel')}.hide();" reRender="taskList,gridPanelTable,stxtError" />
                                                </td>
                                                <td align="center" width="50%">
                                                    <a4j:commandButton value="No" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </h:form>
                            </rich:modalPanel>
                            <h:panelGrid  columns="2" id="gridPanel22" style="height:30px;" styleClass="row1" width="100%">
                                <h:outputLabel id="lblTotAmt" styleClass="label" value="TDS Total Amount"/>
                                <h:outputText id="stxtTotAmt" styleClass="output" style="width:100px" value="#{TdsEntryForSingle.totAmt}" >
                                    <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                </h:outputText>
                            </h:panelGrid>
                            <h:panelGrid  columns="1" id="gridPaneledgh" style="height:30px;" styleClass="row1" width="100%">
                                <h:outputText id="stxtError" styleClass="error" value="#{TdsEntryForSingle.erMsg}" />
                            </h:panelGrid>
                        </h:panelGrid>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="vtop" columns="1" id="gridPanelTable2" width="100%" styleClass="row1" style="height:200px;">
                        <a4j:region>
                            <rich:dataTable value="#{TdsEntryForSingle.acd}" var="dataItem"
                                            rowClasses="gridrow1, gridrow2" id="taskList1" rows="6" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="11"><h:outputText value="TDS Entry" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="#{TdsEntryForSingle.rowAcno}" /></rich:column>
                                        <rich:column><h:outputText value="#{TdsEntryForSingle.rowVchNo}" /></rich:column>
                                        <rich:column><h:outputText value="Control No." /></rich:column>
                                        <rich:column><h:outputText value="#{TdsEntryForSingle.rowTdsUnRec}" /></rich:column>
                                        <rich:column><h:outputText value="TD Made Date" /></rich:column>
                                        <rich:column><h:outputText value="TD Date" /></rich:column>
                                        <rich:column><h:outputText value="Modurity Date" /></rich:column>
                                        <rich:column><h:outputText value="Int.Opt." /></rich:column>
                                        <rich:column><h:outputText value="ROI" /></rich:column>
                                        <rich:column><h:outputText value="Status" /></rich:column>
                                        <rich:column><h:outputText value="Select Row" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><f:facet name="header"></f:facet>
                                    <h:outputText value="#{dataItem.rtNo}"  />
                                </rich:column>
                                <rich:column><h:outputText value="#{dataItem.receiptNo}"   /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.ctrlNo}"  /></rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem.printAmt}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column><h:outputText value="#{dataItem.tdMDate}"  /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.tdDate}"  /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.moduDate}"  /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.intOpt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.roi}"  /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.status}"  /></rich:column>
                                <rich:column>
                                    <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{TdsEntryForSingle.setRowValues}" reRender="txtRtNo,stxtError,stxtReciptNo,txtAcNo,txtTdsAmt,stxtAcNo,stxtCustName,stxtJtName,stxtOpMode,taskList1,gridPanelTable2,stxtError" focus="txtTdsAmt">
                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{TdsEntryForSingle.currentItem1}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{TdsEntryForSingle.currentRow1}"/>
                                    </a4j:commandLink>
                                </rich:column>

                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList1" maxPages="20" />
                        </a4j:region>
                    </h:panelGrid>

                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnPost" value="Post" oncomplete="#{rich:component('postPanel')}.show()"/>
                            <a4j:commandButton id="btnReset" value="Reset" action="#{TdsEntryForSingle.reSetForm1}" reRender="taskList,stxtTotAmt,stxtError,txtAcNo,stxtCustName,stxtJtName,stxtOpMode,gridPanelTable,txtRtNo,txtTdsAmt,gridPanelTable2,stxtReciptNo,stxtAcNo" focus="ddAcNo"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{TdsEntryForSingle.exitForm}" reRender="taskList,stxtTotAmt,stxtError,txtAcNo,stxtCustName,stxtJtName,stxtOpMode,gridPanelTable,txtRtNo,txtTdsAmt,gridPanelTable2,stxtReciptNo,stxtAcNo"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <rich:modalPanel id="postPanel" autosized="true" width="250" onshow="#{rich:element('Yes')}.focus()">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure For TDS Posting? "/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton id="Yes" value="Yes" ajaxSingle="true" action="#{TdsEntryForSingle.toPost}"
                                                       oncomplete="#{rich:component('postPanel')}.hide();" reRender="taskList,stxtTotAmt,stxtError,txtAcNo,stxtCustName,stxtJtName,stxtOpMode,gridPanelTable,txtRtNo,txtTdsAmt,gridPanelTable2,stxtReciptNo,stxtAcNo" />
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton id="No" value="No" onclick="#{rich:component('postPanel')}.hide();return false;" />
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
