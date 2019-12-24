<%-- 
    Document   : headcreation
    Created on : 17 May, 2014, 12:34:49 PM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>New Head Creation</title>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1"  style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="headerPanel" columns="3"  style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup>
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{headCreation.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="GL Head Creation"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{headCreation.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="messagePanel" columns="2" columnClasses="col8,col8" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                        <h:outputText id="stxtMessage" styleClass="error" value="#{headCreation.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="codePanel" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row2" width="100%" style="height:30px;">
                        <h:outputLabel id="lblCode" styleClass="label" value="Code" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddCode" styleClass="ddlist" size="1" value="#{headCreation.code}" onchange="submit()" valueChangeListener="#{headCreation.processCodeAction}">
                            <f:selectItems value="#{headCreation.codeList}"/>
                            <%--<a4j:support event="onblur" action="#{headCreation.processCodeAction}" reRender="stxtMessage,ddGroupCode"/>--%>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblGroupCode" styleClass="label" value="GroupCode" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddGroupCode" styleClass="ddlist" value="#{headCreation.groupCode}" size="1" style="width:120px;" onchange="submit()" valueChangeListener="#{headCreation.processGroupCodeAction}">
                            <f:selectItems value="#{headCreation.groupCodeList}"/>
                            <%--<a4j:support event="onblur" action="#{headCreation.processGroupCodeAction()}" reRender="stxtMessage,ddSubGroupCode"/>--%>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="subGroupCodePanel" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row1" width="100%">
                        <h:outputLabel id="lblSubGroupCode" styleClass="label" value="SubGroupCode" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddSubGroupCode" styleClass="ddlist" value="#{headCreation.subGroupCode}" size="1" style="width:120px;" onchange="submit()" valueChangeListener="#{headCreation.processSubGroupCodeAction}">
                            <f:selectItems value="#{headCreation.subGroupCodeList}"/>
                            <%--<a4j:support event="onblur" action="#{headCreation.processSubGroupCodeAction}" reRender="stxtMessage,ddSubSubGroupCode"/>--%>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblSubSubGroupCode" styleClass="label" value="SSGroupCode"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddSubSubGroupCode" styleClass="ddlist" value="#{headCreation.subSubGroupCode}" size="1" style="width:120px;" onchange="submit()" valueChangeListener="#{headCreation.processMaxHeadAction}">
                            <f:selectItems value="#{headCreation.subSubGroupCodeList}"/>
                            <%--<a4j:support event="onblur" action="#{headCreation.processMaxHeadAction}" reRender="stxtMessage,stxtHeadNumber"/>--%>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="headNumberPanel" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row2" width="100%">
                        <h:outputLabel id="lblHeadNumber" styleClass="label" value="Head Number" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:outputText id="stxtHeadNumber" styleClass="output" value="#{headCreation.headNumber}"/>
                        <h:outputLabel id="lblHeadName" styleClass="label" value="Head Name" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtHeadName" styleClass="input" value="#{headCreation.headName}" maxlength="100" size="15" onkeyup="this.value = this.value.toUpperCase();"/>
                    </h:panelGrid>
                    <h:panelGrid id="headNameGrid" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row1" width="100%">
                        <h:outputLabel id="lblHeadType" styleClass="label" value="Head Type" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddHeadType" styleClass="ddlist" value="#{headCreation.headType}" size="1" style="width:120px">
                            <f:selectItems value="#{headCreation.headTypeList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblTranType" styleClass="label" value="Tran Type" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddTranType" styleClass="ddlist" value="#{headCreation.tranType}" size="1" style="width:120px">
                            <f:selectItems value="#{headCreation.tranTypeList}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnProcess" value="Save"  oncomplete="#{rich:component('processPanel')}.show()" reRender="stxtMessage,processPanel"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{headCreation.btnRefreshAction}" reRender="mainPanel"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{headCreation.btnExitAction}" reRender="mainPanel"/>
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
                                    <h:outputText id="confirmid" value="Are you sure to save ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" action="#{headCreation.processAction}" 
                                                       oncomplete="#{rich:component('processPanel')}.hide(); #{rich:element('ddCode')}.focus();" 
                                                       reRender="stxtMessage,mainPanel"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('processPanel')}.hide();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
