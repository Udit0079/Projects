<%-- 
    Document   : PlMaster1
    Created on : Oct 26, 2010, 2:36:44 PM
    Author     : sudhir Kr Bisht
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title> PlMaster  </title>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{HoPlMaster.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="PL Master"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{HoPlMaster.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" width="100%" style="text-align:center" styleClass="row2">
                        <h:outputText id="errorMessage" styleClass="error" value="#{HoPlMaster.errorMessage}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9,col9" columns="2" id="a8" width="100%" style="height:30px;" styleClass="row1">
                        <h:panelGrid columnClasses="col9,col9" columns="2" id="a81" width="100%" style="height:30px;">
                            <h:outputLabel id="lblClassification" styleClass="label" value="Classification:"/>
                            <h:selectOneListbox id="ddClassification"  value="#{HoPlMaster.classification}" size="1" style="width: 102px" styleClass="ddlist">
                                <f:selectItems value="#{HoPlMaster.classificationList}"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9,col9" columns="2" id="a811" width="100%" style="height:30px;"/>                        
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9,col9" columns="2" id="a11" width="100%" style="height:30px;" styleClass="row2">
                        <h:panelGrid columnClasses="col9,col9" columns="2" id="a111" width="100%" style="height:30px;">
                            <h:outputLabel id="lblGroupCode" styleClass="label" value="Group Code :"/>
                            <h:inputText id="txtGroupCode" size="15"  value="#{HoPlMaster.grCode}"  styleClass="input">
                                <a4j:support event="onblur" action="#{HoPlMaster.SubCode}" reRender="txtGroupCode,txtSubGroupCode,errorMessage,ddCode,txtcod1,txtCode,txtCode1" focus="txtSubGroupCode"/>
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9,col9" columns="2" id="a10" width="100%" style="height:30px;">
                            <h:panelGrid columnClasses="col9,col9" columns="1" id="a101" width="100%" style="height:30px;">
                                <h:outputLabel id="lblSubGroupCode" styleClass="label" value="Sub Group Code :"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9,col9" columns="1" id="a1011" width="100%" style="height:30px;">
                                <h:inputText id="txtSubGroupCode" size="15" value="#{HoPlMaster.subGrCode}"   styleClass="input"/>                                
                            </h:panelGrid>
                        </h:panelGrid>
                    </h:panelGrid>  
                    <h:panelGrid columnClasses="col9,col9" columns="2" id="a13" width="100%" style="height:30px;" styleClass="row1">
                        <h:panelGrid columnClasses="col9,col9" columns="2" id="a15" width="100%" style="height:30px;">
                            <h:outputLabel id="lblCodeDescription" styleClass="label" value="Group Description :"/>
                            <h:inputText id="txtCode1"  disabled="#{HoPlMaster.disableGroupDesc}"value="#{HoPlMaster.groupDesc}" size="30" styleClass="input" onkeyup="this.value = this.value.toUpperCase();"/>                            
                        </h:panelGrid>
                        <h:panelGrid id="a152"  columnClasses="col17,col1,col13" columns="3"  width="100%" style="height:30px;">
                            <h:outputLabel id="lblCode" styleClass="label" value="Code:"/>
                            <h:panelGroup>
                                <h:inputText id="txtcod1" size="15"  disabled="#{HoPlMaster.disableCode}" value="#{HoPlMaster.code}"  styleClass="input" maxlength="8">
                                    <a4j:support event="onblur"  reRender="txtCode,errorMessage,txtcod1,txtCode,newCode" action="#{HoPlMaster.getAcNo}"/>
                                </h:inputText>
                                <h:outputLabel id="newCode" styleClass="label" value="#{HoPlMaster.newCode}"/>
                            </h:panelGroup>
                            <h:inputText id="txtCode" disabled="#{HoPlMaster.disableCode}" size="30"  value="#{HoPlMaster.acname}" styleClass="input"/>
                        </h:panelGrid>
                    </h:panelGrid> 
                    <h:panelGrid columnClasses="col9,col9"  columns="2" styleClass="row2"  id="a9" width="100%">
                        <h:panelGrid columnClasses=" col9,col9 "   columns="2" id="a14" width="100%" style="height:30px;">
                            <h:outputLabel id="lblMode" styleClass="label" value="Mode :"/>
                            <h:selectOneListbox id="ddMode"  styleClass="ddlist" value="#{HoPlMaster.mode}" size="1" style="width: 102px">
                                <f:selectItems value="#{HoPlMaster.modeList}"/>
                                <a4j:support reRender="errorMessage" event="onblur" action="#{HoPlMaster.onMode()}" focus="plmastsave"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="2" id="a12" width="100%" style="height:30px;">
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="a19" style="height:auto;" styleClass="row2" width="100%">
                        <rich:dataTable  value="#{HoPlMaster.gridData}" var="dataitem" rowClasses="gridrow1, gridrow2" rows="10" id="taskList1" columnsWidth="100" onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor=''" width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column><h:outputText value="Classification" /></rich:column>
                                    <rich:column><h:outputText value="Group Code" /></rich:column>
                                    <rich:column><h:outputText value="Sub Group Code" /></rich:column>
                                    <rich:column><h:outputText value="Code" /></rich:column>
                                    <rich:column><h:outputText value="Group Descriptions" /></rich:column>
                                    <rich:column><h:outputText value="Last Updated By" /></rich:column>
                                    <rich:column><h:outputText value="Mode" /></rich:column>
                                    <rich:column><h:outputText value="Sub Grp Desc" /></rich:column>
                                    <rich:column width="20"><h:outputText value="Delete" /></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{dataitem.classfication}"/></rich:column>
                            <rich:column><h:outputText value="#{dataitem.groupcode}"/></rich:column>
                            <rich:column><h:outputText value="#{dataitem.subgroupcode}"/></rich:column>
                            <rich:column><h:outputText value="#{dataitem.code}"/></rich:column>
                            <rich:column><h:outputText value="#{dataitem.groupDescriptions}" /></rich:column>
                            <rich:column><h:outputText value="#{dataitem.lastUpdatedBy}"/></rich:column>
                            <rich:column><h:outputText value="#{dataitem.mode}"/></rich:column>
                            <rich:column><h:outputText value="#{dataitem.subGrpDesc}" /></rich:column>
                            <rich:column style="text-align:center;width:40px">
                                <a4j:commandLink  reRender="taskList1"  ajaxSingle="true" id="selectlink" onclick="#{rich:component('showPanelForGrid')}.show();">
                                    <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                    <f:setPropertyActionListener value="#{dataitem}" target="#{HoPlMaster.authorized}"/>
                                </a4j:commandLink>
                            </rich:column>
                        </rich:dataTable>
                        <rich:datascroller align="left" for="taskList1" maxPages="20" />
                    </h:panelGrid>
                    <rich:modalPanel id="showPanelForGrid" autosized="true" width="200">
                        <f:facet name="header">
                            <h:outputText value="Do you want to delete this record ?" style="padding-right:15px;" />
                        </f:facet>
                        <f:facet name="controls">
                            <h:panelGroup>
                                <h:graphicImage value="/images/close.png" styleClass="hidelink" id="hidelink1" />
                                <rich:componentControl for="showPanelForGrid" attachTo="hidelink" operation="hide" event="onclick" />
                            </h:panelGroup>
                        </f:facet>
                        <h:form>
                            <table width="100%">
                                <tbody>
                                    <tr>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="Yes" ajaxSingle="true" action="#{HoPlMaster.setRowValues}"
                                                               onclick="#{rich:component('showPanelForGrid')}.hide();" 
                                                               reRender="txtSubGroupCode,ddMode,errorMessage,txtCode,txtcod1,ddCode,txtCode1,lblSubGroupCode,txtGroupCode,ddClassification,taskList1,newCode"/>
                                        </td>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="Cancel" onclick="#{rich:component('showPanelForGrid')}.hide();return false;" />
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </h:form>
                    </rich:modalPanel>
                    <rich:modalPanel id="showPanelForGrid2" autosized="true" width="200">
                        <f:facet name="header">
                            <h:outputText value="Are you sure u want to save the record ?" style="padding-right:15px;" />
                        </f:facet>
                        <f:facet name="controls">
                            <h:panelGroup>
                                <h:graphicImage value="/images/close.png" styleClass="hidelink" id="hidelink2" />
                                <rich:componentControl for="showPanelForGrid" attachTo="hidelink" operation="hide" event="onclick" />
                            </h:panelGroup>
                        </f:facet>
                        <h:form>
                            <table width="100%">
                                <tbody>
                                    <tr>
                                        <td align="center" width="50%">
                                            <a4j:commandButton id="modalSaveButton" value="Yes" ajaxSingle="true" action="#{HoPlMaster.save}"
                                                               onclick="#{rich:component('showPanelForGrid2')}.hide();" reRender="taskList1,ddMode,errorMessage,txtCode,txtcod1,ddCode,txtCode1,txtSubGroupCode,txtGroupCode,ddClassification,plmastsave,txtSubGroupCode,newCode"/>
                                        </td>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="Cancel" onclick="#{rich:component('showPanelForGrid2')}.hide();return false;" />
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </h:form>
                    </rich:modalPanel>
                    <h:panelGrid id="plmastfooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="plmastfootergroup">
                            <a4j:commandButton disabled="#{HoPlMaster.save}" id="plmastsave" value="Save" focus="modalSaveButton" oncomplete="#{rich:component('showPanelForGrid2')}.show();"/>
                            <a4j:commandButton  id="plmastRefresh"  value="Refresh" action="#{HoPlMaster.refresh()}" reRender="txtSubGroupCode,ddMode,errorMessage,txtCode,txtcod1,ddCode,txtCode1,lblSubGroupCode,txtGroupCode,ddClassification,taskList1,newCode,a19"/>
                            <a4j:commandButton  id="plmastexit" value=" Exit" action="#{HoPlMaster.exitForm}" reRender="a19,taskList1"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
