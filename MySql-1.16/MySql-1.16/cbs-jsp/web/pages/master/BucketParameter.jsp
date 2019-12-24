<%-- 
    Document   : BucketParameter
    Created on : Nov 11, 2010, 10:34:41 AM
    Author     : ROHIT KRISHNA GUPTA
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
            <title>Bucket Parameter</title>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{BucketParameter.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Bucket Parameter"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{BucketParameter.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="errorMessage" styleClass="error" value="#{BucketParameter.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{BucketParameter.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="2" id="a3" width="100%" style="height:30px;" styleClass="row1">
                        <h:outputLabel id="lblFunction" styleClass="label" value="Function :" style="padding-left:250px;"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddFunction" tabindex="1" styleClass="ddlist" value="#{BucketParameter.function}" size="1" style="width: 125px">
                                <f:selectItems value="#{BucketParameter.functionList}"/>
                                <a4j:support ajaxSingle="true" event="onchange"  action="#{BucketParameter.functionMethod}" reRender="message,errorMessage,a5,a6,a8,a9,taskList,table,gpFooter" focus="txtBucketNo"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="2" id="a4" width="100%">
                            <h:panelGrid columnClasses="col9" columns="6" id="a5" width="100%" style="height:30px;" styleClass="row2">
                                <h:outputLabel id="lblBucketNo" styleClass="label" value="Bucket No.(Numbers Only) :" style=""><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtBucketNo" maxlength="4" tabindex="2" size="20" value="#{BucketParameter.bucketNo}" disabled="#{BucketParameter.fieldDisFlag}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="6" id="a8" width="100%" style="height:30px;" styleClass="row2">
                                <h:outputLabel id="lblBucketDesc" styleClass="label" value="Bucket Description :" style=""><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtBucketDesc" maxlength="100" tabindex="3" size="20" value="#{BucketParameter.bucketDesc}" disabled="#{BucketParameter.fieldDisFlag}" onblur="this.value = this.value.toUpperCase();" styleClass="input"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="6" id="a6" width="100%" style="height:30px;" styleClass="row1">
                                <h:outputLabel id="lblStartDay" styleClass="label" value="Start Day(Numbers Only) :" style=""><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtStartDay" maxlength="6" tabindex="4" size="20" value="#{BucketParameter.startDay}" disabled="#{BucketParameter.fieldDisFlag}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="6" id="a9" width="100%" style="height:30px;" styleClass="row1">
                                <h:outputLabel id="lblEndDay" styleClass="label" value="End Day(Numbers Only) :" style=""><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtEndDay" maxlength="6" tabindex="5" size="20" value="#{BucketParameter.endDay}" disabled="#{BucketParameter.fieldDisFlag}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input">
                                    <a4j:support event="onblur" focus="ddParameter"/>
                                </h:inputText>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="2" id="a55" width="100%" style="height:30px;" styleClass="row2">
                                <h:outputLabel id="lblParameter" styleClass="label" value="Profile Parameter :"><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddParameter" tabindex="1" styleClass="ddlist" value="#{BucketParameter.parameter}" size="1" style="width: 125px">
                                    <f:selectItems value="#{BucketParameter.parameterList}"/>
                                    <a4j:support event="onblur" action="#{BucketParameter.loadGrid}" reRender="message,errorMessage,a5,a6,a8,a9,a3,taskList,table,gpFooter" oncomplete="if(#{BucketParameter.function == 'ADD'}){#{rich:element('btnSave')}.focus();}else{#{rich:element('btnUpdate')}.focus();}"/>
                                </h:selectOneListbox>
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="vtop" columns="1" id="table" style="height:auto;" styleClass="row2" width="100%">
                            <a4j:region>
                                <rich:dataTable value="#{BucketParameter.gridDetail}" var="dataItem"
                                                rowClasses="gridrow1,gridrow2" id="taskList" columnsWidth="100" rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="15"><h:outputText value="Detail" /></rich:column>
                                            <rich:column breakBefore="true" width="25%"><h:outputText value="Bucket No." /></rich:column>
                                            <rich:column width="25%"><h:outputText value="Bucket Description" /></rich:column>
                                            <rich:column width="25%"><h:outputText value="Start Day" /></rich:column>
                                            <rich:column width="25%"><h:outputText value="End Day" /></rich:column>
                                            <rich:column width="25%"><h:outputText value="Parameter" /></rich:column>
                                            <rich:column width="20" rendered="#{BucketParameter.flag1==false}"><h:outputText value="Edit" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{dataItem.bucketNo}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.bucketDesc}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.startDay}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.endDay}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.profileParameter}" /></rich:column>
                                    <rich:column style="text-align:center;width:40px" rendered="#{BucketParameter.flag1==false}">
                                        <a4j:commandLink id="selectlink" action="#{BucketParameter.fillValuesofGridInFields}" oncomplete="#{rich:element('btnSave')}.disabled = true"
                                                         reRender="message,errorMessage,a5,a6,a8,a9,a3,taskList,table,gpFooter,ddParameter"focus="btnUpdate">
                                            <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                            <f:setPropertyActionListener value="#{dataItem}" target="#{BucketParameter.currentItem}"/>
                                            <f:setPropertyActionListener value="#{row}" target="#{BucketParameter.currentRow}"/>
                                        </a4j:commandLink>
                                    </rich:column>
                                </rich:dataTable>
                                <rich:datascroller align="left" for="taskList" maxPages="10" />
                            </a4j:region>
                        </h:panelGrid>
                        <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup layout="block" style="width:100%;text-align:center;">
                                <a4j:commandButton id="btnSave" tabindex="6" value="Save" disabled="#{BucketParameter.btnDisFlag1}" oncomplete="#{rich:component('savePanel')}.show()" reRender="message,errorMessage,a3,a5,a6,a8,a9,taskList,table,gpFooter" focus="btnSavePanel" />
                                <a4j:commandButton id="btnUpdate" tabindex="7" value="Update" disabled="#{BucketParameter.btnDisFlag2}" oncomplete="#{rich:component('modifyPanel')}.show()" reRender="message,errorMessage,a3,a5,a6,a8,a9,taskList,table,gpFooter" focus="btnUpdatePanel"/>
                                <a4j:commandButton id="btnRefresh" tabindex="8" value="Refresh" action="#{BucketParameter.resetForm}" reRender="message,errorMessage,a3,a5,a6,a8,a9,taskList,table,gpFooter" focus="ddFunction"/>
                                <a4j:commandButton id="btnExit" tabindex="9" value="Exit" action="#{BucketParameter.exitBtn}" reRender="message,errorMessage" />
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                </a4j:form>
                <rich:modalPanel id="savePanel" autosized="true" width="200" onshow="#{rich:element('btnSavePanel')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Are You Sure To Save?" style="padding-right:15px;" />
                    </f:facet>
                    <h:form>
                    <table width="100%">
                        <tbody>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnSavePanel" ajaxSingle="true" action="#{BucketParameter.saveDetail}"
                                                       oncomplete="#{rich:component('savePanel')}.hide();" reRender="message,errorMessage,a3,a5,a6,a8,a9,taskList,table,gpFooter,a3" />
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Cancel" onclick="#{rich:component('savePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="modifyPanel" autosized="true" width="200" onshow="#{rich:element('btnUpdatePanel')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Are You Sure To Update ?" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%">
                        <tbody>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnUpdatePanel" ajaxSingle="true" action="#{BucketParameter.updateBucketParameter}"
                                                       oncomplete="#{rich:component('modifyPanel')}.hide();" reRender="message,errorMessage,a3,a5,a6,a8,a9,taskList,table,gpFooter,a3" />
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Cancel" onclick="#{rich:component('modifyPanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
