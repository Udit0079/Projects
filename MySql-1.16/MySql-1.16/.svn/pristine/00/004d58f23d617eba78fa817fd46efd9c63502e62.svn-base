<%-- 
    Document   : PlMaster
    Created on : Aug 31, 2010, 1:08:43 PM
    Author     : Admin
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
            <title>PL Master</title>
            <script type="text/javascript">
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{PlMaster.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="PL Master"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{PlMaster.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="errorMessage" styleClass="error" value="#{PlMaster.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{PlMaster.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="2" id="a7" width="100%">
                        <h:panelGrid  columns="1" id="gridPanel1" width="100%">
                            <h:panelGrid columnClasses="col9" columns="2" id="a8" width="100%" style="height:30px;" styleClass="row1">
                                <h:outputLabel id="lblClassification" styleClass="label" value="Classification :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddClassification" tabindex="1" styleClass="ddlist" value="#{PlMaster.classification}" size="1" style="width: 102px">
                                    <f:selectItems value="#{PlMaster.classificationList}"/>
                                </h:selectOneListbox>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="3" id="a11" width="100%" style="height:30px;" styleClass="row2">
                                <h:outputLabel id="lblGroupCode" styleClass="label" value="Group Code :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtGroupCode" size="15" tabindex="2" value="#{PlMaster.grCode}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input">
                                    <a4j:support action="#{PlMaster.grCodeLostFocus}" event="onblur"
                                                 reRender="a8,a11,a12,a13,a10,a14,a15,a19,message,errorMessage,taskList" limitToList="true" focus="txtSubGroupCode"/>
                                </h:inputText>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="3" id="a13" width="100%" style="height:30px;" styleClass="row1">
                                <h:outputLabel id="lblCode" styleClass="label" value="Code (Account No)" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                               <h:selectOneListbox id="ddCode" tabindex="4" styleClass="ddlist" value="#{PlMaster.codeTy}" size="1" style="width: 102px">
                                    <f:selectItems value="#{PlMaster.codeList}"/>
                                </h:selectOneListbox> 
                              <h:inputText id="txtCode" size="15" tabindex="5" value="#{PlMaster.code}" maxlength="8" onkeyup="this.value = this.value.toUpperCase();" styleClass="input">
                                    <a4j:support action="#{PlMaster.codeLostFocus}" event="onblur"
                                                 reRender="a13,a15,a12,message,errorMessage" limitToList="true" focus="ddMode"/>
                                </h:inputText>
                               <%--  <h:outputLabel id="stxtCode" styleClass="label" value="#{PlMaster.code}" style="padding-left:70px;"></h:outputLabel> --%>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="2" id="a15" width="100%" style="height:30px;" styleClass="row2">
                                <h:outputLabel id="lblCodeDescription" styleClass="label" value="Code Description :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtCodeDesc" tabindex="7" onkeyup="this.value = this.value.toUpperCase();"  size="50" styleClass="input" value="#{PlMaster.codeDescTxt}" disabled="#{PlMaster.glCodeDesc}"/>
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="2" id="a9" width="100%">
                            <h:panelGrid columns="1" id="gridPanel3" width="100%">
                                <h:panelGrid columnClasses="col9" columns="2" id="a18" width="100%" style="height:30px;" styleClass="row1">
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col9" columns="2" id="a10" width="100%" style="height:30px;" styleClass="row2">
                                    <h:outputLabel id="lblSubGroupCode" styleClass="label" value="Sub Group Code :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                                    <h:inputText id="txtSubGroupCode" size="15" tabindex="3" value="#{PlMaster.subGrCode}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input">
                                        <a4j:support action="#{PlMaster.subGrCodeLostFocus}" event="onblur"
                                                 reRender="a8,a11,a10,a12,a15,message,errorMessage" limitToList="true" focus="ddCode"/>
                                    </h:inputText>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col9" columns="2" id="a14" width="100%" style="height:30px;" styleClass="row1">
                                    <h:outputLabel id="lblMode" styleClass="label" value="Mode :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                                    <h:selectOneListbox id="ddMode" tabindex="6" styleClass="ddlist" value="#{PlMaster.mode}" size="1" style="width: 102px">
                                    <f:selectItems value="#{PlMaster.modeList}"/>
                                    </h:selectOneListbox>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col9" columns="2" id="a12" width="100%" style="height:30px;" styleClass="row2">
                                    <h:panelGroup layout="block" style="width:100%;text-align:center;">
                                        <h:outputText id="stxtCodeDescription" styleClass="output" value="#{PlMaster.codeDesc}"/>
                                    </h:panelGroup>
                                </h:panelGrid>                                
                            </h:panelGrid>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="a19" style="height:auto;" styleClass="row2" width="100%">
                        <rich:contextMenu attached="false" id="menu" submitMode="ajax" oncollapse="row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'">
                            <rich:menuItem value="Remove Record" ajaxSingle="true" oncomplete="#{rich:component('deletePanel')}.show()"
                                           actionListener="#{PlMaster.fetchCurrentRow}">
                                <a4j:actionparam name="sNo" value="{sNo}" />
                                <a4j:actionparam name="row" value="{currentRow}" />
                            </rich:menuItem>
                        </rich:contextMenu>
                        <a4j:region>
                            <rich:dataTable value="#{PlMaster.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="8" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="12"><h:outputText value="Details" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Serial No." /></rich:column>
                                        <rich:column><h:outputText value="Group Code" /></rich:column>
                                        <rich:column><h:outputText value="Sub Group Code" /></rich:column>
                                        <rich:column><h:outputText value="Code" /></rich:column>
                                        <rich:column><h:outputText value="Descriptions" /></rich:column>
                                        <rich:column><h:outputText value="Classification" /></rich:column>
                                        <rich:column><h:outputText value="Last Updated By" /></rich:column>
                                        <rich:column><h:outputText value="Mode" /></rich:column>
                                        <rich:column><h:outputText value="Tran Time" /></rich:column>
                                        <rich:column width="20"><h:outputText value="Delete" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.sNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.grCode}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.subGrCode}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.code}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.description}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.classification}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.lastUpdateBy}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.mode}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.tranTime}" /></rich:column>
                                <rich:column>
                                    <a4j:commandLink ajaxSingle="true" id="deletelink" oncomplete="#{rich:component('deletePanel')}.show()" reRender="taskList,message,errorMessage,lpg,a19">
                                        <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{PlMaster.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{PlMaster.currentRow}" />
                                    </a4j:commandLink>                                    
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="20" />
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnSave" tabindex="8" value="Save" oncomplete="#{rich:component('savePanel')}.show()"  reRender="message,errorMessage" >
                            </a4j:commandButton>
                            <a4j:commandButton id="btnRefresh" tabindex="9" value="Refresh" action="#{PlMaster.resetForm}"  reRender="message,errorMessage,lpg,a8,a14,a10,a11,a12,a13,a15,a19,taskList" focus="ddClassification">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" tabindex="10" value="Exit" action="#{PlMaster.exitFrm}" reRender="message,errorMessage" >
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="savePanel" autosized="true" width="250" onshow="#{rich:element('btnYesSave')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure To Save?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesSave" ajaxSingle="true" action="#{PlMaster.saveDetail}"
                                                       oncomplete="#{rich:component('savePanel')}.hide();" reRender="message,errorMessage,lpg,a8,a14,a10,a11,a12,a13,a15,a19,taskList" focus="ddClassification"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNoSave" onclick="#{rich:component('savePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="deletePanel" autosized="true" width="250" onshow="#{rich:element('btnYesDel')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure To Delete This Record?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesDel" ajaxSingle="true"  action="#{PlMaster.delete}"
                                                       oncomplete="#{rich:component('deletePanel')}.hide();" reRender="message,errorMessage,lpg,a8,a14,a10,a11,a12,a13,a15,a19,taskList" focus="ddClassification"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNoDel" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>