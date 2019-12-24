<%-- 
    Document   : DayBookMaster
    Created on : Aug 31, 2010, 11:21:40 AM
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
            <title>Day Book Master</title>
            <script type="text/javascript">

            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{DayBookMaster.todayDate}"/>
                        </h:panelGroup>

                        <h:outputLabel styleClass="headerLabel" value="Day Book Master"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{DayBookMaster.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="errorMessage" styleClass="error" value="#{DayBookMaster.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{DayBookMaster.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="2" id="a7" width="100%">
                        <h:panelGrid  columns="1" id="gridPanel1" width="100%">
                            <h:panelGrid columnClasses="col9" columns="2" id="a8" width="100%" style="height:30px;" styleClass="row1">
                                <h:outputLabel id="lblGroupCode" styleClass="label" value="Group Code :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtGroupCode" size="15" tabindex="1" value="#{DayBookMaster.grCode}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input">
                                    <a4j:support action="#{DayBookMaster.grCodeLostFocus}" event="onblur" focus="txtSubGroupCode"
                                                 reRender="a8,a10,a12,a19,message,errorMessage,taskList,gpFooter" limitToList="true"/>
                                </h:inputText>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="3" id="a11" width="100%" style="height:30px;" styleClass="row2">
                                <h:outputLabel id="lblAccount" styleClass="label" value="Account :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddAcType" tabindex="3" styleClass="ddlist" value="#{DayBookMaster.acType}" size="1" style="width: 102px">
                                    <f:selectItems value="#{DayBookMaster.acTypeList}"/>
                                </h:selectOneListbox> 
                              <h:panelGroup id = "panal">
                                  <h:inputText id="txtCode" size="15" tabindex="4" value="#{DayBookMaster.code}" onkeyup="this.value = this.value.toUpperCase();" maxlength="6" styleClass="input">
                                    <a4j:support action="#{DayBookMaster.codeLostFocus}" event="onblur" focus="txtAccountDesc"
                                                 reRender="a11,a13,a15,a12,message,errorMessage" limitToList="true" />
                                </h:inputText>
                               <%--<h:outputLabel id="stxtAccount" styleClass="label" value="#{DayBookMaster.code}" style="padding-left:70px;"></h:outputLabel> --%>
                             </h:panelGroup>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="2" id="a13" width="100%" style="height:30px;" styleClass="row1">
                                <h:outputLabel id="lblFromGlHead" styleClass="label" value="From GL Head :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtFromGlHead" size="15" tabindex="6" value="#{DayBookMaster.frGlHead}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input">
                                    <a4j:support action="#{DayBookMaster.frGlHeadLostFocus}" event="onblur" focus="txtToGlHead"
                                                 reRender="a13,message,errorMessage" limitToList="true" />
                                </h:inputText>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="2" id="a15" width="100%" style="height:30px;" styleClass="row2">
                                <h:outputLabel id="lblAcType" styleClass="label" value="Account Type :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddAcctType" tabindex="8" styleClass="ddlist" value="#{DayBookMaster.acctType}" size="1" style="width: 102px">
                                    <f:selectItems value="#{DayBookMaster.acctTypeList}"/>
                                    <a4j:support ajaxSingle="true" event="onchange" action="#{DayBookMaster.acctTypeLostFocus}" reRender="a17,message,errorMessage" focus="ddAcctStatus"/>

                                </h:selectOneListbox>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="2" id="a17" width="100%" style="height:30px;" styleClass="row1">
                                <h:outputLabel id="lblAccountTypeDesc" styleClass="label" value="A/c. Type Description :" style="padding-left:70px;"/>
                                <h:outputText id="stxtAccountTypeDesc" styleClass="output" value="#{DayBookMaster.acTypeDesc}"/>
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="2" id="a9" width="100%">
                            <h:panelGrid columns="1" id="gridPanel3" width="100%">
                                <h:panelGrid columnClasses="col9" columns="2" id="a10" width="100%" style="height:30px;" styleClass="row1">
                                    <h:outputLabel id="lblSubGroupCode" styleClass="label" value="Sub Group Code :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                                    <h:inputText id="txtSubGroupCode" size="15" tabindex="2" value="#{DayBookMaster.subGrCode}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input">
                                        <a4j:support action="#{DayBookMaster.subGrCodeLostFocus}" event="onblur" focus="ddAcType"
                                                     reRender="a8,a10,a12,message,errorMessage" limitToList="true" />
                                    </h:inputText>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col9" columns="3" id="a12" width="100%" style="height:30px;" styleClass="row2">
                                    <h:outputLabel id="lblAccountDesc" styleClass="label" value="A/c. Description :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                                    <h:inputText id="txtAccountDesc" size="15" tabindex="5" value="#{DayBookMaster.codeDescTxt}" disabled="#{DayBookMaster.glCodeDesc}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                                    <%--<h:outputText id="stxtAccountDesc" styleClass="output" value="#{DayBookMaster.codeDesc}"/>--%>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col9" columns="2" id="a14" width="100%" style="height:30px;" styleClass="row1">
                                    <h:outputLabel id="lblToGlHead" styleClass="label" value="To GL Head :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                                    <h:inputText id="txtToGlHead" size="15" tabindex="7" value="#{DayBookMaster.toGlHead}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input">
                                        <a4j:support action="#{DayBookMaster.toGlHeadLostFocus}" event="onblur" focus="ddAcctType"
                                                     reRender="a14,message,errorMessage" limitToList="true" />
                                    </h:inputText>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col9" columns="2" id="a16" width="100%" style="height:30px;" styleClass="row2">
                                    <h:outputLabel id="lblAcStatus" styleClass="label" value="A/c. Status :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                                    <h:selectOneListbox id="ddAcctStatus" tabindex="9" styleClass="ddlist" value="#{DayBookMaster.acctStatus}" size="1" style="width: 102px">
                                        <f:selectItems value="#{DayBookMaster.acctStatusList}"/>
                                    </h:selectOneListbox>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col9" columns="2" id="a18" width="100%" style="height:30px;" styleClass="row1">
                                </h:panelGrid>
                            </h:panelGrid>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="a19" style="height:auto;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{DayBookMaster.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="6" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="12">
                                            <h:outputText value="Details" />
                                        </rich:column>
                                        <rich:column breakBefore="true">
                                            <h:outputText value="Serial No." />
                                        </rich:column>
                                        <rich:column>
                                            <h:outputText value="Group Code" />
                                        </rich:column>
                                        <rich:column>
                                            <h:outputText value="Sub Group Code" />
                                        </rich:column>
                                        <rich:column>
                                            <h:outputText value="Code" />
                                        </rich:column>
                                        <rich:column>
                                            <h:outputText value="Description" />
                                        </rich:column>
                                        <rich:column>
                                            <h:outputText value="Fr Code" />
                                        </rich:column>
                                        <rich:column>
                                            <h:outputText value="To Code" />
                                        </rich:column>
                                        <rich:column>
                                            <h:outputText value="Account Type" />
                                        </rich:column>
                                        <rich:column>
                                            <h:outputText value="Account Status" />
                                        </rich:column>
                                        <rich:column>
                                            <h:outputText value="Last Updated By" />
                                        </rich:column>
                                        <rich:column>
                                            <h:outputText value="Tran Time" />
                                        </rich:column>
                                        <rich:column width="20"><h:outputText value="Delete" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.sNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.grCode}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.subGrCode}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.code}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.description}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.frCode}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.toCode}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.acType}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.acStatus}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.lastUpdateBy}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.tranTime}" /></rich:column>
                                <rich:column>
                                    <a4j:commandLink ajaxSingle="true" id="deletelink" oncomplete="#{rich:component('deletePanel')}.show()" reRender="taskList,message,errorMessage,lpg,a19">
                                        <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{DayBookMaster.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{DayBookMaster.currentRow}" />
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="20" />
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnSave" tabindex="10" value="Save" oncomplete="#{rich:component('savePanel')}.show()" reRender="message,errorMessage,lpg,a8,a14,a10,a11,a12,a13,a15,a16,a17,a19,taskList"/>
                            <a4j:commandButton id="btnRefresh" tabindex="11" value="Refresh" action="#{DayBookMaster.resetForm}"  reRender="message,errorMessage,lpg,a8,a14,a10,a11,a12,a13,a15,a16,a17,a19,taskList" focus="txtGroupCode"/>
                            <a4j:commandButton id="btnExit" tabindex="12" action="#{DayBookMaster.exitFrm}" value="Exit" reRender="message,errorMessage,lpg,a8,a14,a10,a11,a12,a13,a15,a16,a17,a19,taskList"/>
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
                                    <a4j:commandButton value="Yes" id="btnYesSave" ajaxSingle="true" action="#{DayBookMaster.saveDetail}"
                                                       oncomplete="#{rich:component('savePanel')}.hide();" reRender="message,errorMessage,lpg,a8,a14,a10,a11,a12,a13,a15,a16,a17,a19,taskList" focus="txtGroupCode"/>
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
                                    <a4j:commandButton value="Yes" id="btnYesDel" ajaxSingle="true"  action="#{DayBookMaster.delete}"
                                                       oncomplete="#{rich:component('deletePanel')}.hide();" reRender="message,errorMessage,lpg,a8,a14,a10,a11,a12,a13,a15,a16,a17,a19,taskList" focus="txtGroupCode"/>
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
