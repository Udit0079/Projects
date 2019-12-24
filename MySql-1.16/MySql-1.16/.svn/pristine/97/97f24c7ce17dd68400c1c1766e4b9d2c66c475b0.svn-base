<%-- 
    Document   : CrrEntryForm
    Created on : Sep 27, 2010, 10:18:54 AM
    Author     : Dinesh Pratap Singh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>CRR Entry Form</title>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="gridPanel" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{CrrEntryForm.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="CRR Entry Form"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{CrrEntryForm.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorGrid" width="100%" style="text-align:center" styleClass="row2">
                        <h:outputText id="stxtError" value="#{CrrEntryForm.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanelAcDesc" columns="4" columnClasses="col2,col7,col2,col7" width="100%" styleClass="row1">
                        <h:outputLabel id="lblAcDesc" styleClass="label" value="Account Description"/>
                        <h:selectOneListbox id="comboAcDesc" styleClass="ddlist"  style="width:120px" size="1" value="#{CrrEntryForm.acdesc}">
                            <f:selectItems value="#{CrrEntryForm.acdescList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblAcCategory" styleClass="label" value="Account Category"/>
                        <h:selectOneListbox id="listBoxAcCategory" styleClass="ddlist" size="1" style="width:120px" value="#{CrrEntryForm.acCategory}">
                            <f:selectItems  value="#{CrrEntryForm.acCategoryList}"/>
                            <a4j:support event="onchange" actionListener="#{CrrEntryForm.processAccountCategory}" reRender="stxtError,txtFromHead,lblLabelFromHead,txtToHead,lblLabelToHead,addAcnoList,visibleReport" oncomplete="if(#{CrrEntryForm.acCategory=='I'}){#{rich:element('addAcnoList')}.focus();}else{#{rich:element('txtFromHead')}.focus();}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanelHead" columnClasses="col2,col7,col2,col7" style="height:px;" columns="4" width="100%" styleClass="row2">
                        <h:outputLabel id="lblFromHead" value="From GL Head" styleClass="label"/>
                        <h:panelGroup>
                            <h:inputText id="txtFromHead" value="#{CrrEntryForm.fromHead}" maxlength="8" styleClass="input" onkeyup="this.value = this.value.toUpperCase();" disabled="#{CrrEntryForm.seriesDisable}">
                                <a4j:support event="onblur" ajaxSingle="true" actionListener="#{CrrEntryForm.fromGLHeadAction}" reRender="stxtError,lblLabelFromHead"/>
                            </h:inputText>
                            <h:outputLabel id="lblLabelFromHead" value="#{CrrEntryForm.newFromGLHead}" styleClass="label" style="color:blue"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblToHead" value="To GL Head" styleClass="label"/>
                        <h:panelGroup>
                            <h:inputText id="txtToHead" maxlength="8" value="#{CrrEntryForm.toHead}" styleClass="input" onkeyup="this.value = this.value.toUpperCase();" disabled="#{CrrEntryForm.seriesDisable}">
                                <a4j:support event="onblur" ajaxSingle="true" actionListener="#{CrrEntryForm.toGLHeadAction}" reRender="stxtError,lblLabelToHead"/>
                            </h:inputText>
                            <h:outputLabel id="lblLabelToHead" value="#{CrrEntryForm.newToGLHead}" styleClass="label" style="color:blue"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanelAlt" columns="4" columnClasses="col2,col7,col2,col7" styleClass="row1" width="100%">
                        <h:outputLabel id="lblIndivisual" value="Indivisual Description" styleClass="label"/>
                        <h:panelGroup>
                            <h:selectOneListbox id="addAcnoList" value="#{CrrEntryForm.acnoItem}" styleClass="ddlist" size="1" style="width:120px" disabled="#{CrrEntryForm.indivisualDisable}">
                                <f:selectItems value="#{CrrEntryForm.acnoItemList}"/>
                                <a4j:support event="onblur" ajaxSingle="true"/>
                            </h:selectOneListbox>
                            <h:selectOneListbox  id="visibleReport" value="#{CrrEntryForm.cmbview}" styleClass="ddlist" size="1" style="width:120px" disabled="#{CrrEntryForm.indivisualDisable}">
                                <f:selectItems value="#{CrrEntryForm.cmbviewList}"/>
                                <a4j:support event="onblur" ajaxSingle="true"/>
                            </h:selectOneListbox>
                        </h:panelGroup>
                        <h:outputLabel id="lblAlternate" value="Alternate Column" styleClass="label"/>
                        <h:panelGroup>
                            <h:selectOneListbox id="altOption" value="#{CrrEntryForm.altOption}" styleClass="ddlist" size="1" style="width:120px">
                                <f:selectItems value="#{CrrEntryForm.altOptionList}"/>
                                <a4j:support event="onblur" ajaxSingle="true"/>
                            </h:selectOneListbox>
                            <h:selectOneListbox id="comboID" value="#{CrrEntryForm.alternateValue}" styleClass="ddlist" size="1" style="width:120px">
                                <f:selectItems value="#{CrrEntryForm.alternateList}"/>
                                <a4j:support event="onblur" ajaxSingle="true"/>
                            </h:selectOneListbox>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanelCrr" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="panelbutton">
                            <a4j:commandButton id="btnSave" value="Save" ajaxSingle="true" action="#{CrrEntryForm.processSaveAction}" reRender="stxtError"/>
                            <a4j:commandButton id="btnDelete" value="Delete" ajaxSingle="true" action="#{CrrEntryForm.processDeleteAction}" reRender="stxtError"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" ajaxSingle="true" action="#{CrrEntryForm.refreshForm}" reRender="stxtError,listBoxAcCategory,txtFromHead,lblLabelFromHead,txtToHead,lblLabelToHead"/>
                            <a4j:commandButton id="btnExitExit" value="Exit" ajaxSingle="true" action="#{CrrEntryForm.exitForm}" reRender="stxtError,listBoxAcCategory,txtFromHead,lblLabelFromHead,txtToHead,lblLabelToHead"/>
                            <%--<a4j:commandButton  disabled="#{CrrEntryForm.from9status}" id="form9" value="Form9 Performa"/>
                            <a4j:commandButton id="treeveiw" value="Tree Veiw"/>--%>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>