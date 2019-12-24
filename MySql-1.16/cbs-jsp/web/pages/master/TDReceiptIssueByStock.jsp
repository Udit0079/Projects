<%--
    Document   : standingInstructionMaster
    Created on : May 12, 2010, 10:44:37 AM
    Author     : jitendra kumar chaudhary
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!-- RichFaces tag library declaration -->
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <title>Receipt Issue By Stock</title>
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{TDReceiptIssueByStock.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Receipt Issue By Stock"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{TDReceiptIssueByStock.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel1" width="100%">
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel15" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="label1" styleClass="label" for="ddScheme" value="Scheme"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddScheme" styleClass="ddlist" size="1" style="width:90px"  value="#{TDReceiptIssueByStock.scheme}" tabindex="1">
                                <a4j:support action="#{TDReceiptIssueByStock.getTableDetails}"  event="onblur"  focus="txtBookNo"
                                             reRender="gridPanel103,stxtMsg,stxtTd8,p4" limitToList="true" />
                                <f:selectItem itemValue="--SELECT--"/>
                                <f:selectItems value="#{TDReceiptIssueByStock.schemeTypeOption}" />
                            </h:selectOneListbox>

                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel18" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="label12" styleClass="label" value="Book No." ><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtBookNo" styleClass="input"style="width:100px" value="#{TDReceiptIssueByStock.bookNo}" tabindex="2" maxlength="14">

                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanela" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblReceiptNoFrom" styleClass="label" value="Receipt No From" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtReceiptNoFrom" styleClass="input"style="width:100px" value="#{TDReceiptIssueByStock.receiptNoFrom}" tabindex="3">

                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel1e" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblReceiptNoTo" styleClass="label" value="Receipt No. To" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtReceiptNoTo" styleClass="input"style="width:100px" value="#{TDReceiptIssueByStock.receiptNoTo}" tabindex="4">
                                <a4j:support action="#{TDReceiptIssueByStock.getNoOfLeave}" event="onblur" focus="txtSeries"
                                             reRender="txtNoOfLeaves,stxtMsg,txtReceiptNoFrom" limitToList="true"  />

                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel1z" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblNoOfLeaves" styleClass="label" value="No. Of Leafs" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtNoOfLeaves" styleClass="input"style="width:100px" value="#{TDReceiptIssueByStock.noOfLeaves}" disabled="true">

                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel1x" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblSeries" styleClass="label" value="Series" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtSeries" styleClass="input"style="width:100px" value="#{TDReceiptIssueByStock.series}" tabindex="5" maxlength="6">

                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel14" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblIssueDate" styleClass="label"  value="Issue Date" ><font class="required" color="red">*</font></h:outputLabel>
                            <rich:calendar datePattern="dd/MM/yyyy" id="calDate" value="#{TDReceiptIssueByStock.issueDt}"  tabindex="6" inputSize="10">
                                <a4j:support actionListener="#{TDReceiptIssueByStock.onblurIssueDate}"  event="onchanged" focus="ddShowTableData"
                                             reRender="stxtMsg"  />
                            </rich:calendar>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel1b" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblShowTableData" styleClass="label" value="Show All Receipt Details"/>
                            <h:selectOneListbox id="ddShowTableData" styleClass="ddlist" size="1" style="width: 100px" value="#{TDReceiptIssueByStock.showTableData}"  tabindex="7">
                                <f:selectItem itemValue="ALL RECEIPT"/>
                                <f:selectItem itemValue="BACK"/>
                                <a4j:support ajaxSingle="true" event="onblur" reRender="p4" focus="btnSave"/>
                            </h:selectOneListbox>

                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel1c" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblNoStockAvailable" styleClass="label" value="No Stock Available"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanedrt" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputText id="stxtTd8" styleClass="error" value="#{TDReceiptIssueByStock.tdlevel8}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel001" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputText id="stxtMsg" styleClass="error" value="#{TDReceiptIssueByStock.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanelfr" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblAccNames" styleClass="label" value="No Stock Available Please fill Security Forms Stock Register Required" />
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel103" width="100%" styleClass="row2" style="height:235px;">
                        <h:panelGroup id="p4">
                            <rich:contextMenu attached="false" id="menu" submitMode="ajax" oncollapse="row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'">
                                <rich:menuItem value="Select Record" ajaxSingle="true" actionListener="#{TDReceiptIssueByStock.fetchCurrentRow}">
                                    <a4j:actionparam name="acNo" value="{acNo}" />
                                    <a4j:actionparam name="row" value="{currentRow}" />
                                </rich:menuItem>
                            </rich:contextMenu>
                            <rich:contextMenu attached="false" id="menu1" submitMode="ajax" oncollapse="row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'">
                                <rich:menuItem value="Select Record" ajaxSingle="true" actionListener="#{TDReceiptIssueByStock.fetchCurrentRow}">
                                    <a4j:actionparam name="acNo" value="{acNo}" />
                                    <a4j:actionparam name="row" value="{currentRow}" />
                                </rich:menuItem>
                            </rich:contextMenu>
                            <a4j:region>
                                <rich:dataTable value="#{TDReceiptIssueByStock.allReceiptEntries}" var="dataItem" rendered="#{TDReceiptIssueByStock.showTableData == 'ALL RECEIPT'}"  rowClasses="gridrow1,gridrow2" id="taskList" rows="4" columnsWidth="100" rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                       <rich:columnGroup>
                                            <rich:column colspan="7"><h:outputText value="TD Receipt Issue By Stock" /></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="S.No" /></rich:column>
                                            <rich:column><h:outputText value="Scheme" /></rich:column>
                                            <rich:column><h:outputText value="Book No" /></rich:column>
                                            <rich:column><h:outputText value="Series" /></rich:column>
                                            <rich:column><h:outputText value="Receipt No" /></rich:column>
                                            <rich:column><h:outputText value="Entry Date" /></rich:column>
                                            <rich:column><h:outputText value="Status" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{dataItem.seqNum1}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.scheme}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.bookNo}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.series}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.receiptNo}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.entryDate}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.status}" />
                                        <a4j:support action="#{TDReceiptIssueByStock.changeValue}" ajaxSingle="true" event="ondblclick" reRender="btnSave,btnUpdate,taskList" >
                                            <f:setPropertyActionListener value="#{dataItem}" target="#{TDReceiptIssueByStock.currentItem1}"/>
                                            <f:setPropertyActionListener value="#{row}" target="#{TDReceiptIssueByStock.currentRow1}"/>
                                        </a4j:support>
                                    </rich:column>
           
                                </rich:dataTable>
                                <rich:datascroller align="left" for="taskList" maxPages="20" rendered="#{TDReceiptIssueByStock.showTableData == 'ALL RECEIPT'}"/>
                            </a4j:region>
                            <rich:modalPanel id="deletePanel" autosized="true" width="200">
                                <f:facet name="header">
                                    <h:outputText value="Are You Sure, You Want to Delete this Record?" style="padding-right:15px;" />
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
                                                <td align="center" width="50%">
                                                    <a4j:commandButton value="Yes" ajaxSingle="true" action="#{TDReceiptIssueByStock.delete}"
                                                                       oncomplete="#{rich:component('deletePanel')}.hide();" reRender="p4,stxtMsg,taskList" />
                                                </td>
                                                <td align="center" width="50%">
                                                    <a4j:commandButton value="Cancel" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </h:form>
                            </rich:modalPanel>
                            
                            <a4j:region>
                                <rich:dataTable value="#{TDReceiptIssueByStock.backEntries}" var="Item" rendered="#{TDReceiptIssueByStock.showTableData == 'BACK'}"  rowClasses="gridrow1,gridrow2" id="taskList1" rows="4" columnsWidth="100" rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="10"><h:outputText value="Td Receipt Issue By Stock For All Receipt" /></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="Scheme" /></rich:column>
                                            <rich:column><h:outputText value="Book No" /></rich:column>
                                            <rich:column><h:outputText value="Series" /></rich:column>
                                            <rich:column><h:outputText value="RecFrom" /></rich:column>
                                            <rich:column><h:outputText value="RecTo" /></rich:column>
                                            <rich:column><h:outputText value="Leaf" /></rich:column>
                                            <rich:column><h:outputText value="S.No" /></rich:column>
                                            <rich:column visible="false"><h:outputText value="IssueDt" /></rich:column>
                                            <rich:column><h:outputText value="Select" /></rich:column>
                                            <rich:column><h:outputText value="Delete" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{Item.scheme}" /></rich:column>
                                    <rich:column><h:outputText value="#{Item.bookNo}" /></rich:column>
                                    <rich:column><h:outputText value="#{Item.series}" /></rich:column>
                                    <rich:column><h:outputText value="#{Item.recFrom}" /></rich:column>
                                    <rich:column><h:outputText value="#{Item.recTo}" /></rich:column>
                                    <rich:column><h:outputText value="#{Item.leaf}" /></rich:column>
                                    <rich:column><h:outputText value="#{Item.seqNum}" /></rich:column>
                                    <rich:column visible="false"><h:outputText value="#{Item.issueDt}" /></rich:column>
                                    <rich:column style="text-align:center;width:40px">
                                        <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{TDReceiptIssueByStock.setRowValues}"
                                                         reRender="txtBookNo,ddScheme,btnSave,btnUpdate,calDate,txtReceiptNoFrom,txtNoOfLeaves,txtSeries,txtReceiptNoTo"   >
                                            <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                            <f:setPropertyActionListener value="#{Item}" target="#{TDReceiptIssueByStock.currentItem}"/>
                                            <f:setPropertyActionListener value="#{row}" target="#{TDReceiptIssueByStock.currentRow}"/>
                                        </a4j:commandLink>

                                    </rich:column> 
                                    <rich:column>
                                        <a4j:commandLink ajaxSingle="true" id="deletelink" oncomplete="#{rich:component('deletePanel')}.show()">
                                            <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                            <f:setPropertyActionListener value="#{Item}" target="#{TDReceiptIssueByStock.currentItem}"/>
                                            <f:setPropertyActionListener value="#{row}" target="#{TDReceiptIssueByStock.currentRow}" />
                                        </a4j:commandLink>

                                    </rich:column>
                                </rich:dataTable>
                                <rich:datascroller align="left" for="taskList1" maxPages="20" rendered="#{TDReceiptIssueByStock.showTableData == 'BACK'}"/>
                            </a4j:region>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton disabled="#{TDReceiptIssueByStock.flag1}" id="btnSave" value="Save" action="#{TDReceiptIssueByStock.saveData}" reRender="taskList,gridPanel103,taskList1,ddScheme,stxtTd8,p4,txtBookNo,txtReceiptNoFrom,txtReceiptNoTo,txtNoOfLeaves,txtSeries,stxtMsg">

                            </a4j:commandButton>
                            <a4j:commandButton disabled="#{TDReceiptIssueByStock.flag}" id="btnUpdate" value="Update" action="#{TDReceiptIssueByStock.upDateData}" reRender="taskList1,btnSave,btnUpdate,ddScheme,stxtTd8,p4,txtBookNo,txtReceiptNoFrom,txtReceiptNoTo,txtNoOfLeaves,txtSeries,stxtMsg"  >

                            </a4j:commandButton>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{TDReceiptIssueByStock.clearText}" reRender="ddScheme,calDate,stxtTd8,btnSave,btnUpdate,p4,txtBookNo,txtReceiptNoFrom,txtReceiptNoTo,txtNoOfLeaves,txtSeries,stxtMsg" focus="ddScheme"  >

                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{TDReceiptIssueByStock.exitFrm}" >

                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>

            </a4j:form>
        </body>
    </html>
</f:view>
