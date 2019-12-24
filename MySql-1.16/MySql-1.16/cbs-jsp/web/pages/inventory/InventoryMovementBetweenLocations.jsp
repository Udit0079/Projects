<%-- 
    Document   : InventoryMovementBetweenLocations
    Created on : May 22,2012
    Author     : Dinesh Pratap Singh
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
            <title>Inventory Movement Between Locations</title>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form>
                <h:panelGrid id="maigrid" columns="1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid id="headergrid" columns="3" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{InventoryMovementBetweenLocations.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Inventory Movement Between Locations"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{InventoryMovementBetweenLocations.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid id="msggrid" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7;border:1px ridge #BED6F8;">
                        <h:outputText id="message" styleClass="error" value="#{InventoryMovementBetweenLocations.message}"/>
                    </h:panelGrid>

                    <h:panelGrid id="functiongrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputLabel id="lblFunction" styleClass="label" value="Function :"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddFunction" style="width: 120px" styleClass="ddlist"  value="#{InventoryMovementBetweenLocations.function}" size="1">
                            <f:selectItems value="#{InventoryMovementBetweenLocations.functionList}" />
                            <a4j:support action="#{InventoryMovementBetweenLocations.onChangeFunction}" event="onchange" reRender="msggrid,txtFromBrnId,stxtFromBrnName,txtToBrnId,stxtToBrnName,frbrgrid,invtgrid,tonogrid,taskList,tablePanel,btnProcess"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblFromBrnId" styleClass="label" value="From Branch ID :"><font class="required" color="red">*</font></h:outputLabel>
                        <h:panelGroup layout="block">
                            <h:inputText id="txtFromBrnId" value="#{InventoryMovementBetweenLocations.fromBrnId}" maxlength="10" onkeyup="this.value = this.value.toUpperCase();"  size="10" styleClass="input">
                                <a4j:support action="#{InventoryMovementBetweenLocations.setFromBranchName}" event="onblur" reRender="message,stxtFromBrnName" limitToList="true"/>
                            </h:inputText>
                            <h:outputText id="stxtFromBrnName" styleClass="output" value="#{InventoryMovementBetweenLocations.fromBrnName}" style="color:purple;"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblToBrnId" styleClass="label" value="To Branch ID :"><font class="required" color="red">*</font></h:outputLabel>
                        <h:panelGroup layout="block">
                            <h:inputText id="txtToBrnId" value="#{InventoryMovementBetweenLocations.toBrnId}" maxlength="10" onkeyup="this.value = this.value.toUpperCase();"  size="10" styleClass="input">
                                <a4j:support action="#{InventoryMovementBetweenLocations.setToBranchName}" event="onblur" reRender="message,stxtToBrnName" limitToList="true" />
                            </h:inputText>
                            <h:outputText id="stxtToBrnName" styleClass="output" value="#{InventoryMovementBetweenLocations.toBrnName}" style="color:purple;"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid id="frbrgrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%">
                        <h:outputLabel id="lblFromBrnLocation" styleClass="label" value="From Location:"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddFromBrnLocation" style="width: 120px" styleClass="ddlist"  value="#{InventoryMovementBetweenLocations.fromBrnLocClass}" size="1" >
                            <f:selectItems value="#{InventoryMovementBetweenLocations.fromBrnLocClassList}" />
                            <a4j:support action="#{InventoryMovementBetweenLocations.onBlurFrBrLocation}" event="onblur" reRender="message"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblToBrnLocation" styleClass="label" value="To Location:"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddToBrnLocation" style="width: 120px" styleClass="ddlist"  value="#{InventoryMovementBetweenLocations.toBrnLocClass}" size="1" >
                            <f:selectItems value="#{InventoryMovementBetweenLocations.toBrnLocClassList}"/>
                            <a4j:support action="#{InventoryMovementBetweenLocations.onBlurToBrLocation}" event="onblur" reRender="message"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblInvtClass" styleClass="label" value="Inventory Class :" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddInvtClass" styleClass="ddlist" value="#{InventoryMovementBetweenLocations.invtClass}" size="1" style="width: 120px">
                            <f:selectItems value="#{InventoryMovementBetweenLocations.invtClassList}" />
                            <a4j:support action="#{InventoryMovementBetweenLocations.setInventoryType}" event="onblur" reRender="message,ddInvtType"/>
                        </h:selectOneListbox>
                    </h:panelGrid>

                    <h:panelGrid id="invtgrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputLabel id="lblInvtType" styleClass="label" value="Inventory Type :" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddInvtType" styleClass="ddlist" value="#{InventoryMovementBetweenLocations.invtType}" size="1" style="width: 120px">
                            <f:selectItems value="#{InventoryMovementBetweenLocations.invtTypeList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblInvtFromNo" styleClass="label" value="From No. :" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtInvtFromNo" maxlength="20" size="10" value="#{InventoryMovementBetweenLocations.fromNo}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input">
                            <a4j:support action="#{InventoryMovementBetweenLocations.setFromNo}" event="onblur" reRender="message" limitToList="true" />
                        </h:inputText>
                        <h:outputLabel id="lblInvtToNo" styleClass="label" value="To No. :" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtInvtToNo" maxlength="20" size="10" value="#{InventoryMovementBetweenLocations.toNo}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input">
                            <a4j:support action="#{InventoryMovementBetweenLocations.toNoOnBlur}" event="onblur" reRender="message,txtQuantity" limitToList="true" focus="txtTranParticulars"/>
                        </h:inputText>
                    </h:panelGrid>

                    <h:panelGrid id="tonogrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%">
                        <h:outputLabel id="lblQuantity" styleClass="label" value="Quantity :" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtQuantity" maxlength="12" disabled="#{InventoryMovementBetweenLocations.quantityVisible}" size="10" value="#{InventoryMovementBetweenLocations.quantity}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                        <h:outputLabel id="lblTranParticulars" styleClass="label" value="Transfer Particulars :" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtTranParticulars" maxlength="100" size="20" value="#{InventoryMovementBetweenLocations.trfParticular}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input">
                            <a4j:support action="#{InventoryMovementBetweenLocations.setTrfParticular}" event="onblur" reRender="message" limitToList="true" />
                        </h:inputText>
                        <h:outputLabel id="lblValOfInvt" styleClass="label" value="Value Of Invt. :" />
                        <h:inputText id="txtValOfInvt" maxlength="10" size="10" value="#{InventoryMovementBetweenLocations.valueOfInvt}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input">
                            <a4j:support action="#{InventoryMovementBetweenLocations.addCaseOperation}" event="onblur" reRender="message,tablePanel,taskList,btnProcess" focus="btnProcess"/>
                        </h:inputText>
                    </h:panelGrid>

                    <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                        <a4j:region id="tblRegion">
                            <rich:dataTable value="#{InventoryMovementBetweenLocations.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="15"><h:outputText value="Inventory Detail" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="From Branch ID" /></rich:column>
                                        <rich:column><h:outputText value="To Branch ID" /></rich:column>
                                        <rich:column><h:outputText value="Invt.Class" /></rich:column>
                                        <rich:column><h:outputText value="Invt Type" /></rich:column>
                                        <rich:column><h:outputText value="Star No." /></rich:column>
                                        <rich:column><h:outputText value="End No." /></rich:column>
                                        <rich:column><h:outputText value="Quantity" /></rich:column>
                                        <rich:column><h:outputText value="Transfer Particulars" /></rich:column>
                                        <rich:column><h:outputText value="#{InventoryMovementBetweenLocations.gridOperation}"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.fromBrnId}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.toBrnId}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.invtClass}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.invtType}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.startNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.endNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.quantity}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.trfParticular}" /></rich:column>
                                <rich:column style="text-align:center;width:40px" rendered="#{InventoryMovementBetweenLocations.addFlag}">
                                    <a4j:commandLink id="deletelink" ajaxSingle="true" oncomplete="#{rich:component('deletePanel')}.show();">
                                        <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{InventoryMovementBetweenLocations.currentItem}"/>
                                    </a4j:commandLink>
                                </rich:column>
                                <rich:column style="text-align:center;width:40px" rendered="#{InventoryMovementBetweenLocations.enquiryFlag}">
                                    <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{InventoryMovementBetweenLocations.setTableDataToForm}" reRender="maigrid">
                                        <h:graphicImage value="/resources/images/select.gif" style="border:0"/>
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{InventoryMovementBetweenLocations.currentItem}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="10"/>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnProcess" value="#{InventoryMovementBetweenLocations.processButtonValue}" 
                                               action="#{InventoryMovementBetweenLocations.populateMessage}" 
                                               oncomplete="#{rich:component('processPanel')}.show()" 
                                               disabled="#{InventoryMovementBetweenLocations.processVisible}" reRender="message,processPanel,confirmid"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{InventoryMovementBetweenLocations.resetForm}" reRender="maigrid"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{InventoryMovementBetweenLocations.exitBtnAction}" reRender="maigrid"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="processPanel" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText id="confirmid" value="#{InventoryMovementBetweenLocations.confirmationMsg}"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" action="#{InventoryMovementBetweenLocations.processAction}" 
                                                       oncomplete="#{rich:component('processPanel')}.hide(); #{rich:element('ddFunction')}.focus();" 
                                                       reRender="message,maigrid,tablePanel,taskList"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('processPanel')}.hide();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="deletePanel" autosized="true" width="250" onshow="#{rich:element('btnYes')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are you sure to delete this record ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" action="#{InventoryMovementBetweenLocations.delete}" oncomplete="#{rich:component('deletePanel')}.hide();" reRender="message,maigrid,tablePanel,taskList"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('deletePanel')}.hide();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>