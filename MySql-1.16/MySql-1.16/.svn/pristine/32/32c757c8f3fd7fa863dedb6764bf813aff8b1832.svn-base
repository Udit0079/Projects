<%-- 
    Document   : InventorySplitAndMerge
    Created on : Jul 29, 2011, 11:03:39 AM
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Inventory Split And Merge</title>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:keepAlive beanName="InventorySplitAndMerge"/>
            <a4j:form id="InventorySplitAndMerge">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{InventorySplitAndMerge.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Inventory Split And Merge"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{InventorySplitAndMerge.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7;border:1px ridge #BED6F8;">
                        <h:outputText id="errorMessage" styleClass="error" value="#{InventorySplitAndMerge.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{InventorySplitAndMerge.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="2" id="divPanel" width="100%">
                        <h:panelGrid columnClasses="col9" columns="6" id="a3" width="100%" style="height:30px;border:1px ridge #BED6F8;" styleClass="row1">
                            <h:outputLabel id="lblFunction" styleClass="label" value="Function :"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddFunction" tabindex="1" style="width: 120px" styleClass="ddlist"  value="#{InventorySplitAndMerge.function}" size="1" >
                                <f:selectItems value="#{InventorySplitAndMerge.functionList}" />
                                <a4j:support action="#{InventorySplitAndMerge.functionOnBlurMethod}" event="onblur" reRender="message,errorMessage,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,table1,taskList1,gpFooter" focus="txtBrnId"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a4" width="100%" style="height:30px;border:1px ridge #BED6F8;" styleClass="row1">
                            <%--This is blank space.--%>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a5" width="100%" style="height:30px;border:1px ridge #BED6F8;" styleClass="row2">
                            <h:outputLabel id="lblBrnId" styleClass="label" value="Branch ID :"><font class="required" color="red">*</font></h:outputLabel>
                            <h:panelGroup layout="block">
                                <h:inputText id="txtBrnId" tabindex="2" value="#{InventorySplitAndMerge.brnId}" maxlength="10" onkeyup="this.value = this.value.toUpperCase();"  size="20" styleClass="input">
                                    <a4j:support action="#{InventorySplitAndMerge.setFromBranchName}" event="onblur" reRender="message,errorMessage,a5" focus="ddInvtClass" limitToList="true"/>
                                </h:inputText>
                                <h:outputText id="stxtBrnName" styleClass="output" value="#{InventorySplitAndMerge.brnName}" style="color:purple;"/>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a7" width="100%" style="height:30px;border:1px ridge #BED6F8;" styleClass="row2">
                            <h:outputLabel id="lblInvtClass" styleClass="label" value="Inventory Class :" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddInvtClass" tabindex="4" styleClass="ddlist" value="#{InventorySplitAndMerge.invtClass}" size="1" style="width: 120px">
                                <f:selectItems value="#{InventorySplitAndMerge.invtClassList}" />
                                <a4j:support action="#{InventorySplitAndMerge.setInventoryType}" event="onblur"
                                             reRender="message,errorMessage,a7,a8,ddInvtType" focus="ddInvtType"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a8" width="100%" style="height:30px;border:1px ridge #BED6F8;" styleClass="row1">
                            <h:outputLabel id="lblInvtType" styleClass="label" value="Inventory Type :" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddInvtType" tabindex="5" styleClass="ddlist" value="#{InventorySplitAndMerge.invtType}" size="1" style="width: 120px">
                                <f:selectItems value="#{InventorySplitAndMerge.invtTypeList}" />
                                <a4j:support action="#{InventorySplitAndMerge.setChequeSeriesList}" event="onblur"
                                             oncomplete="if(#{InventorySplitAndMerge.invtType=='--Select--'}){#{rich:element('ddInvtType')}.focus();}else{#{rich:element('txtInvtSrNo')}.focus();}"
                                             reRender="txtInvtSrNo,ddInvtType,table1"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a6" width="100%" style="height:30px;border:1px ridge #BED6F8;" styleClass="row1">
                            <h:outputLabel id="lblInvtSrNo" styleClass="label" value="Inventory Series No. :" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="txtInvtSrNo" tabindex="4" size="1" styleClass="ddlist" style="width:134px " value="#{InventorySplitAndMerge.invtSrNo}">
                                <f:selectItems value="#{InventorySplitAndMerge.chequeSeriesList}" />
                                <a4j:support  action="#{InventorySplitAndMerge.gridLoadForSplittingAndMerging}" event="onblur"
                                              oncomplete="if(#{InventorySplitAndMerge.invtType=='--Select--'}){#{rich:element('ddInvtType')}.focus();}
                                              else if(#{InventorySplitAndMerge.function=='S'}){#{rich:element('txtItemsPerUnit')}.focus();}else{#{rich:element('txtFromNo')}.focus();}"
                                              reRender="message,errorMessage,a3,a4,a5,a6,a7,a8,a10,a11,table1,taskList1,gpFooter" />
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a9" width="100%" style="height:30px;border:1px ridge #BED6F8;" styleClass="row2">
                            <h:outputLabel id="lblFromNo" styleClass="label" value="From No. :" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtFromNo" maxlength="20" tabindex="6" size="20" disabled="#{InventorySplitAndMerge.disFlag}" value="#{InventorySplitAndMerge.startNo}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a10" width="100%" style="height:30px;border:1px ridge #BED6F8;" styleClass="row2">
                            <h:outputLabel id="lblToNo" styleClass="label" value="To No. :" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtToNo" maxlength="20" tabindex="7" size="20" disabled="#{InventorySplitAndMerge.disFlag}" value="#{InventorySplitAndMerge.endNo}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input">
                                <a4j:support action="#{InventorySplitAndMerge.toNoOnBlur}" event="onblur" reRender="message,errorMessage,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,table1,taskList1,gpFooter" limitToList="true" focus="txtItemsPerUnit" />
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a11" width="100%" style="height:30px;border:1px ridge #BED6F8;" styleClass="row1">
                            <h:outputLabel id="lblQuantity" styleClass="label" value="Quantity :" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtQuantity" maxlength="12" tabindex="8" disabled="#{InventorySplitAndMerge.quanDisFlag}" size="20" value="#{InventorySplitAndMerge.quantity}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a12" width="100%" style="height:30px;border:1px ridge #BED6F8;" styleClass="row1">
                            <h:outputLabel id="lblItemsPerUnit" styleClass="label" value="Items Per Unit :" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtItemsPerUnit" maxlength="12" tabindex="9" size="20" value="#{InventorySplitAndMerge.itemsPerUnit}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="table1" style="border:1px ridge #BED6F8;" styleClass="row1" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{InventorySplitAndMerge.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList1" rows="5" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="15"><h:outputText value="Inventory to be split" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Alpha(Invt Sr.No.)" /></rich:column>
                                        <rich:column><h:outputText value="From Number" /></rich:column>
                                        <rich:column><h:outputText value="To Number" /></rich:column>
                                        <rich:column><h:outputText value="Invt Class" /></rich:column>
                                        <rich:column><h:outputText value="Invt Type" /></rich:column>
                                        <rich:column><h:outputText value="Stock Date" /></rich:column>
                                        <rich:column><h:outputText value="Quantity" /></rich:column>
                                        <rich:column><h:outputText value="Status" /></rich:column>
                                        <rich:column visible="false"><h:outputText value="TxnID" /></rich:column>
                                        <rich:column id="selCol" style="text-align:center;" rendered="#{InventorySplitAndMerge.splitFlag}"><h:outputText value="Select" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.invtSrNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.startNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.endno}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.invtClass}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.invtType}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.stockDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.invtQuantity}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.status}" /></rich:column>
                                <rich:column visible="false"><h:outputText value="#{dataItem.txnId}" /></rich:column>
                                <rich:column style="text-align:center;width:40px" rendered="#{InventorySplitAndMerge.splitFlag}">
                                    <a4j:commandLink id="selectlink" action="#{InventorySplitAndMerge.fillValuesofGridInFields}"
                                                     reRender="message,errorMessage,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,table1,taskList1,gpFooter" focus="txtItemsPerUnit">
                                        <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{InventorySplitAndMerge.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{InventorySplitAndMerge.currentRow}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:modalPanel id="splitPanel" autosized="true" width="250" onshow="#{rich:element('btnYesSplitPanel')}.focus();">
                                <f:facet name="header">
                                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                                </f:facet>
                                <h:form>
                                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                        <tbody>
                                            <tr style="height:40px">
                                                <td colspan="2">
                                                    <h:outputText value="Are you sure to split?"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center" width="50%">
                                                    <a4j:commandButton value="Yes" id="btnYesSplitPanel" ajaxSingle="true" action="#{InventorySplitAndMerge.splitRecord}"
                                                                       oncomplete="#{rich:component('splitPanel')}.hide();" reRender="message,errorMessage,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,table1,taskList1,gpFooter" focus="btnRefresh"/>
                                                </td>
                                                <td align="center" width="50%">
                                                    <a4j:commandButton value="No" id="btnNoSplitPanel" onclick="#{rich:component('splitPanel')}.hide();return false;" />
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </h:form>
                            </rich:modalPanel>
                            <rich:datascroller align="left" for="taskList1" maxPages="10" />
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnSplit" tabindex="10" value="Split" oncomplete="#{rich:component('splitPanel')}.show()" reRender="message,errorMessage,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,table1,taskList1,gpFooter" rendered="#{InventorySplitAndMerge.splitFlag}"/>
                            <a4j:commandButton id="btnMerge" tabindex="12" value="Merge" oncomplete="#{rich:component('mergePanel')}.show()" reRender="message,errorMessage,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,table1,taskList1,gpFooter" rendered="#{InventorySplitAndMerge.mergeFlag}"/>
                            <a4j:commandButton id="btnRefresh" tabindex="13" value="Refresh" action="#{InventorySplitAndMerge.resetForm}" reRender="message,errorMessage,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,table1,taskList1,gpFooter,txtInvtSrNo" focus="ddFunction"/>
                            <a4j:commandButton id="btnExit" tabindex="14" value="Exit" action="#{InventorySplitAndMerge.exitBtnAction}" reRender="message,errorMessage" />
                        </h:panelGroup>
                    </h:panelGrid>
                    <rich:modalPanel id="mergePanel" autosized="true" width="250" onshow="#{rich:element('btnYesMergePanel')}.focus();">
                        <f:facet name="header">
                            <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                        </f:facet>
                        <h:form>
                            <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                <tbody>
                                    <tr style="height:40px">
                                        <td colspan="2">
                                            <h:outputText value="Are you sure to merge?"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="Yes" id="btnYesMergePanel" ajaxSingle="true" action="#{InventorySplitAndMerge.mergeRecord}"
                                                               oncomplete="#{rich:component('mergePanel')}.hide();" reRender="message,errorMessage,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,table1,taskList1,gpFooter" focus="btnRefresh"/>
                                        </td>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="No" id="btnNoMergePanel" onclick="#{rich:component('mergePanel')}.hide();return false;" />
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </h:form>
                    </rich:modalPanel>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
