<%-- 
    Document   : DailyGlbMaster
    Created on : Oct 26, 2010, 2:21:01 PM
    Author     : ROHIT KRISHNA GUPTA
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>
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
            <title>GLB Master</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{DailyGlbMaster.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="GLB Master"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{DailyGlbMaster.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="errorMessage" styleClass="error" value="#{DailyGlbMaster.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{DailyGlbMaster.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9,col9" columns="2" id="a3" width="100%">
                        <h:panelGrid  columns="1" id="gridPanel1" width="100%">
                            <h:panelGrid columnClasses="col22,col23" columns="2" id="a5" width="100%" style="height:30px;" styleClass="row1">
                                <h:outputLabel id="lblType" styleClass="label" value="Type :" style=""><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddType" tabindex="1" styleClass="ddlist"  value="#{DailyGlbMaster.type}" size="1" style="width: 102px">
                                    <f:selectItems value="#{DailyGlbMaster.typeList}" />
                                </h:selectOneListbox>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col22,col23" columns="2" id="a7" width="100%" style="height:30px;" styleClass="row2">
                                <h:outputLabel id="lblGrCode" styleClass="label" value="Group Code :" ><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtGrCode" tabindex="2" size="15" value="#{DailyGlbMaster.grCode}" maxlength="10" onblur="this.value = this.value.toUpperCase();" styleClass="input"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col22,col23" columns="2" id="a8" width="100%" style="height:30px;" styleClass="row1">
                                <h:outputLabel id="lblCode" styleClass="label" value="Code :" style=""><font class="required" color="red">*</font></h:outputLabel>
                                <h:panelGroup>
                                    <h:inputText id="txtCode" tabindex="5" value="#{DailyGlbMaster.oldCodeAcNo}" maxlength="8" onkeyup="this.value = this.value.toUpperCase();"  size="15" styleClass="input">
                                        <a4j:support action="#{DailyGlbMaster.getAccountDetail}" event="onblur"
                                                     reRender="message,errorMessage,a8,a9,a11" limitToList="true" focus="txtCodeDesc" />
                                    </h:inputText>
                                    <h:outputLabel id="newCode" styleClass="label" value="#{DailyGlbMaster.newAcno}" style="color:blue" ></h:outputLabel>
                                </h:panelGroup>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col22,col23" columns="2" id="a9" width="100%" style="height:30px;" styleClass="row2">
                                <h:outputLabel id="lblCodeDesc" styleClass="label" value="Code Description :" />
                                <h:outputText id="stxtCodeDesc" styleClass="output" value="#{DailyGlbMaster.codeDesc}" style="color:blue;"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col22,col23" columns="2" id="a14" width="100%" style="height:30px;" styleClass="row1">
                                <h:outputLabel id="lblAcType" styleClass="label" value="Account Type :" style=""><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddAcType" tabindex="8" styleClass="ddlist"  value="#{DailyGlbMaster.acType}" size="1" style="width: 102px">
                                    <f:selectItems value="#{DailyGlbMaster.acTypeList}" />
                                    <a4j:support action="#{DailyGlbMaster.actypeDesc()}" event="onchange"
                                                 reRender="message,errorMessage,a14,a13" limitToList="true" focus="btnSave" />
                                </h:selectOneListbox>
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid id="a4" width="100%">
                            <h:panelGrid id="a6" width="100%" style="height:30px;" styleClass="row1">
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col22,col23" columns="2" id="a10" width="100%" style="height:30px;" styleClass="row2">
                                <h:outputLabel id="lblSubGrCode" styleClass="label" value="Sub Group Code :" ><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtSubGrCode" tabindex="3" size="15" value="#{DailyGlbMaster.subGrCode}" maxlength="10" onkeyup="this.value = this.value.toUpperCase();" styleClass="input">
                                    <a4j:support action="#{DailyGlbMaster.subGrCheck}" event="onblur"
                                                 reRender="message,errorMessage,a10,a5,a7" limitToList="true" focus="txtCode" />
                                </h:inputText>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col22,col23" columns="2" id="a11" width="100%" style="height:30px;" styleClass="row1">
                                <h:outputLabel id="lblCodeDesc1" styleClass="label" value="Code Description :" ><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtCodeDesc" tabindex="6" size="57" value="#{DailyGlbMaster.codeDesc}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col22,col23" columns="2" id="a12" width="100%" style="height:30px;" styleClass="row2">
                                <h:outputLabel id="lblAcStatus" styleClass="label" value="Account Status :" style=""><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddAcStatus" tabindex="7" styleClass="ddlist"  value="#{DailyGlbMaster.acStatus}" size="1" style="width: 102px">
                                    <f:selectItems value="#{DailyGlbMaster.acStatusList}" />
                                </h:selectOneListbox>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col22,col23" columns="2" id="a13" width="100%" style="height:30px;" styleClass="row1">
                                <h:outputLabel id="lblAcTypeDesc" styleClass="label" value="A/c. Type Description :" />
                                <h:outputText id="stxtAcTypeDesc" styleClass="output" value="#{DailyGlbMaster.acTypeDesc}" style="color:blue;"/>
                            </h:panelGrid>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="table" style="height:auto;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{DailyGlbMaster.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="7" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="15"><h:outputText value="Details" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Serial No." /></rich:column>
                                        <rich:column><h:outputText value="Group Code" /></rich:column>
                                        <rich:column><h:outputText value="Sub Group Code" /></rich:column>
                                        <rich:column><h:outputText value="Code" /></rich:column>
                                        <rich:column><h:outputText value="Description" /></rich:column>
                                        <rich:column><h:outputText value="A/C. Type" /></rich:column>
                                        <rich:column><h:outputText value="GLB A/C. Type" /></rich:column>
                                        <rich:column><h:outputText value="A/C. Status" /></rich:column>
                                        <rich:column width="20"><h:outputText value="Delete" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.sno}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.groupCode}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.subgroupCode}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.code}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.description}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.acctType}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.glbacctType}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.acctStatus}" /></rich:column>
                                <rich:column>
                                    <a4j:commandLink ajaxSingle="true" id="deletelink" oncomplete="#{rich:component('deletePanel')}.show()" reRender="a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,message,errorMessage,lpg,gpFooter,table,taskList">
                                        <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{DailyGlbMaster.currentItem}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="20" />
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnSave" tabindex="9" value="Save" oncomplete="#{rich:component('savePanel')}.show()" focus="ddType"
                                               reRender="a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,message,errorMessage,lpg,gpFooter,table,taskList"/>
                            <a4j:commandButton id="btnRefresh" tabindex="10" value="Refresh" action="#{DailyGlbMaster.resetForm}" focus="ddType"
                                               reRender="a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,message,errorMessage,lpg,gpFooter,table,taskList"/>
                            <a4j:commandButton id="btnExit" tabindex="11" value="Exit" action="#{DailyGlbMaster.exitBtnAction}" 
                                               reRender="a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,message,errorMessage,lpg,gpFooter,table,taskList"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
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
                                    <a4j:commandButton value="Yes" id="btnYesDel" ajaxSingle="true"  action="#{DailyGlbMaster.delete}"
                                                       oncomplete="#{rich:component('deletePanel')}.hide();" reRender="a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,message,errorMessage,lpg,gpFooter,table,taskList" focus="ddType"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNoDel" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
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
                                    <a4j:commandButton value="Yes" id="btnYesSave" ajaxSingle="true" action="#{DailyGlbMaster.saveDetail}"
                                                       oncomplete="#{rich:component('savePanel')}.hide();" reRender="a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,message,errorMessage,lpg,gpFooter,table,taskList" focus="ddType"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNoSave" onclick="#{rich:component('savePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
