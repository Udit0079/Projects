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
            <title>ALM Accounts Classification</title>
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
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{ALMAccountsClassification.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="ALM Accounts Classification"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{ALMAccountsClassification.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="errorMessage" styleClass="error" value="#{ALMAccountsClassification.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{ALMAccountsClassification.message}"/>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col9" columns="2" id="a10" width="100%" style="height:30px;" styleClass="row1">
                        <h:outputLabel id="lblFunction" styleClass="label" value="Function :" style="padding-left:250px;"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddFunction" tabindex="1" styleClass="ddlist" value="#{ALMAccountsClassification.function}" size="1" style="width: 125px">
                                <f:selectItems value="#{ALMAccountsClassification.functionList}"/>
                                <a4j:support event="onchange" action="#{ALMAccountsClassification.functionMethod}" reRender="message,errorMessage,a3,a5,a6,a8,a9,a10,taskList,table,gpFooter" focus="ddFlow"/>
                            </h:selectOneListbox>
                        </h:panelGrid>

                    <h:panelGrid columnClasses="col9" columns="2" id="a3" width="100%" style="height:30px;" styleClass="row2">
                        <h:outputLabel id="lblFlow" styleClass="label" value="ALM Flow :" style="padding-left:250px;"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddFlow" value="#{ALMAccountsClassification.flow}" styleClass="ddlist" size="1">
                                <f:selectItems value="#{ALMAccountsClassification.flowList}"/>
                                <a4j:support event="onblur" action="#{ALMAccountsClassification.flowChangeMethod}" reRender="message,errorMessage,a3,a5,a6,a8,a9,a10,a4,a5,taskList,table,gpFooter,ddHeadsOfAc" focus="ddHeadsOfAc"/>
                            </h:selectOneListbox>
                        </h:panelGrid>

                    <h:panelGrid columnClasses="col9" columns="2" id="a4" width="100%">

                        <h:panelGrid columnClasses="col9" columns="6" id="a5" width="100%" style="height:30px;" styleClass="row1">
                            <h:outputLabel id="lblHeadsOfAc" styleClass="label" value="Heads Of Accounts :" style=""><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddHeadsOfAc" tabindex="3" styleClass="ddlist" disabled="#{ALMAccountsClassification.fieldDisFlag}" value="#{ALMAccountsClassification.headsOfAccounts}" size="1" style="width: 125px">
                                    <f:selectItems value="#{ALMAccountsClassification.headsOfAccountsList}"/>
                                </h:selectOneListbox>
                            </h:panelGrid>

                        <h:panelGrid columnClasses="col9" columns="6" id="a8" width="100%" style="height:30px;" styleClass="row1">
                            <h:outputLabel id="lblBucketDesc" styleClass="label" value="Bucket Description :" style=""><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddBucketDesc" tabindex="4" styleClass="ddlist" disabled="#{ALMAccountsClassification.fieldDisFlag}" value="#{ALMAccountsClassification.bucketDesc}" size="1" style="width: 125px">
                                    <f:selectItems value="#{ALMAccountsClassification.bucketDescList}"/>
                                </h:selectOneListbox>
                            </h:panelGrid>

                        <h:panelGrid columnClasses="col9" columns="6" id="a6" width="100%" style="height:30px;" styleClass="row2">
                            <h:outputLabel id="lblAmtPercentage" styleClass="label" value="Amount (In %, Numbers Only) :" style=""><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtAmtPercentage" maxlength="4" tabindex="5" size="21" disabled="#{ALMAccountsClassification.fieldDisFlag}" value="#{ALMAccountsClassification.amount}" onblur="this.value = this.value.toUpperCase();" styleClass="input"/>
                            </h:panelGrid>

                        <h:panelGrid columnClasses="col9" columns="6" id="a9" width="100%" style="height:30px;" styleClass="row2">
                            <h:outputLabel id="lblRemarks" styleClass="label" value="Remarks :" style=""></h:outputLabel>
                            <h:inputText id="txtRemarks" maxlength="200" tabindex="6" size="21" disabled="#{ALMAccountsClassification.fieldDisFlag}" value="#{ALMAccountsClassification.remarks}"  onkeyup="this.value = this.value.toUpperCase();" styleClass="input">
                                <a4j:support action="#{ALMAccountsClassification.loadGrid}" event="onblur"
                                             reRender="message,errorMessage,a5,a6,a8,a9,a3,a10,taskList,table,gpFooter" limitToList="true"/>
                            </h:inputText>
                        </h:panelGrid>

                    </h:panelGrid>

                    <h:panelGrid columnClasses="vtop" columns="1" id="table" style="height:auto;" styleClass="row1" width="100%">

                        <a4j:region>
                            <rich:dataTable value="#{ALMAccountsClassification.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="8" columnsWidth="100" rowKeyVar="row"
                                            width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="15"><h:outputText value="Detail" /></rich:column>
                                        <rich:column breakBefore="true" width="20%"><h:outputText value="Flow" /></rich:column>
                                        <rich:column width="20%"><h:outputText value="Heads Of Accounts" /></rich:column>
                                        <rich:column width="20%"><h:outputText value="Bucket Description" /></rich:column>
                                        <rich:column width="20%"><h:outputText value="Amount (In %)" /></rich:column>
                                        <rich:column width="20%"><h:outputText value="Remarks" /></rich:column>
                                        <rich:column width="20" rendered="#{ALMAccountsClassification.flag1==false}"><h:outputText value="Edit" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.flow}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.headsOfAccounts}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.bucketDesc}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.percentageAmt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.remarks}" /></rich:column>
                                <rich:column style="text-align:center;width:40px" rendered="#{ALMAccountsClassification.flag1==false}">
                                    <a4j:commandLink id="selectlink" action="#{ALMAccountsClassification.fillValuesofGridInFields}" oncomplete="#{rich:element('btnSave')}.disabled = true"
                                                     reRender="message,errorMessage,a5,a6,a8,a9,a3,a10,taskList,table,gpFooter" focus="ddBucketDesc">
                                        <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{ALMAccountsClassification.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{ALMAccountsClassification.currentRow}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="10" />
                        </a4j:region>

                    </h:panelGrid>

                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">

                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnSave" tabindex="7" value="Save" disabled="#{ALMAccountsClassification.btnDisFlag1}" oncomplete="#{rich:component('savePanel')}.show()" reRender="message,errorMessage,a3,a5,a6,a8,a9,a10,taskList,table,gpFooter" focus="btnSavePanel" />
                            <a4j:commandButton id="btnUpdate" tabindex="8" value="Update" disabled="#{ALMAccountsClassification.btnDisFlag2}" oncomplete="#{rich:component('modifyPanel')}.show()" reRender="message,errorMessage,a3,a5,a6,a8,a9,a10,taskList,table,gpFooter" focus="btnUpdatePanel"/>
                            <a4j:commandButton id="btnRefresh" tabindex="9" value="Refresh" action="#{ALMAccountsClassification.resetForm}" reRender="message,errorMessage,a3,a5,a6,a8,a9,a10,taskList,table,gpFooter" focus="ddFunction"/>
                            <a4j:commandButton id="btnExit" tabindex="10" value="Exit" action="#{ALMAccountsClassification.exitBtn}" reRender="message,errorMessage" />
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
                                    <a4j:commandButton value="Yes" id="btnSavePanel" ajaxSingle="true" action="#{ALMAccountsClassification.saveAlmActClassification}"
                                                       oncomplete="#{rich:component('savePanel')}.hide();" reRender="message,errorMessage,a3,a5,a6,a8,a9,a10,taskList,table,gpFooter,a3" />
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
                                    <a4j:commandButton value="Yes" id="btnUpdatePanel" ajaxSingle="true" action="#{ALMAccountsClassification.updateAlmActClassification}"
                                                       oncomplete="#{rich:component('modifyPanel')}.hide();" reRender="message,errorMessage,a3,a5,a6,a8,a9,a10,taskList,table,gpFooter,a3" />
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
