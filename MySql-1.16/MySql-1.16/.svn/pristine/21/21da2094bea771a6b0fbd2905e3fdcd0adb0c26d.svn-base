<%-- 
    Document   : LeaveMaster
    Created on : May 25, 2011, 4:48:43 PM
    Author     : sudhir Bisht
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
            <title>Leave Master</title>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
            </script>
        </head>
        <body>
            <a4j:form id="LeaveMaster">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel1" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stDate" styleClass="output" value="#{LeaveMaster.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="OL30" styleClass="headerLabel" value="Leave Master"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{LeaveMaster.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="gridPanel2"   width="100%" style="height:1px;text-align:center" styleClass="row2">
                        <h:outputText id="errormsg" value="#{LeaveMaster.message}" styleClass="error"/>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel6" style="text-align:left;" width="100%" styleClass="row1">
                        <h:outputLabel styleClass="label"  value="Operation"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox size="1" id="optionList"  styleClass="ddlist" value="#{LeaveMaster.function}" style="width:90px">
                                <f:selectItems value="#{LeaveMaster.functionList}"/>
                                <a4j:support event="onblur" action="#{LeaveMaster.onChangeFunction}" reRender="popUpGridPanel,leaveList,gridPanelTable,errormsg" 
                                             oncomplete="if(#{LeaveMaster.function=='2'}){ #{rich:component('popUpGridPanel')}.show(); } 
                                             else {  #{rich:component('popUpGridPanel')}.hide(); }"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="leaveCode" styleClass="output" value="Leave Code"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText disabled="#{LeaveMaster.leaveCodeDisable}" maxlength="6" id="leaveText" styleClass="input" value="#{LeaveMaster.leaveCode}" style="width:90px" onkeyup="this.value = this.value.toUpperCase();">
                                <a4j:support event="onblur"/>
                            </h:inputText>
                        </h:panelGrid>

                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel9" style="text-align:left;" width="100%" styleClass="row2">
                        <h:outputLabel id="leaveNature" styleClass="output" value="Leave Nature"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="leaveNatureList" disabled="#{LeaveMaster.disableLeaveNature}" styleClass="ddlist" size="1" style="width:90px" value="#{LeaveMaster.leaveNature}" >
                                <f:selectItems value="#{LeaveMaster.leaveNatureOption}"/>
                                <a4j:support event="onblur"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="applicable" styleClass="output" value="Applicable Date"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <rich:calendar enableManualInput="true" id="appliCalen" datePattern="dd/MM/yyyy" value="#{LeaveMaster.applicablDt}" inputSize="10">
                                <a4j:support event="onselect" reRender="appliCalen"/>
                            </rich:calendar>
                        </h:panelGrid>

                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel12" style="text-align:left;" width="100%" styleClass="row1">
                        <h:outputLabel id="noOfDays" styleClass="output" value="No. Of Days"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText maxlength="4" id="noofDsaysText" styleClass="input" value="#{LeaveMaster.noOfDays}" style="width:90px">
                                <a4j:support event="onblur"/>
                            </h:inputText>
                            <h:outputLabel id="encash" styleClass="output" value="EnCash"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="enCashText" styleClass="ddlist" size="1" style="width:90px" value="#{LeaveMaster.encash}" >
                                <f:selectItems value="#{LeaveMaster.encashOption}"/>
                                <a4j:support event="onblur"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel13" style="text-align:left;" width="100%" styleClass="row2">
                            <h:outputLabel id="description" styleClass="output" value="Description"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText maxlength="100" id="decsText" styleClass="input" value="#{LeaveMaster.description}" onkeyup="this.value = this.value.toUpperCase();">
                                <a4j:support event="onblur"/>
                            </h:inputText>
                            <h:outputText />
                            <h:outputText />
                        </h:panelGrid>
                        <h:panelGrid id="footerPanel4" style="width:100%;text-align:center;" styleClass="footer">
                            <a4j:region id="btnProcessRegion">
                                <h:panelGroup id="btnPanel">
                                    <a4j:commandButton id="save" value="Save" action="#{LeaveMaster.saveLeaveDetail}" 
                                                       reRender="leaveList,gridPanelTable,save,delete,appliCalen,noofDsaysText,decsText,leaveText,errormsg,enCashText,leaveNatureList,optionList"/>
                                    <a4j:commandButton disabled="#{LeaveMaster.deleteButtonBoolean}" id="delete" value="Delete" action="#{LeaveMaster.deleteLeaveDetails}" 
                                                       reRender="leaveList,gridPanelTable,save,delete,appliCalen,noofDsaysText,decsText,leaveText,errormsg,enCashText,leaveNatureList,optionList"/>
                                    <a4j:commandButton id="refresh" value="Refresh" action="#{LeaveMaster.refresh}" 
                                                       reRender="leaveList,gridPanelTable,save,delete,appliCalen,noofDsaysText,decsText,leaveText,errormsg,enCashText,leaveNatureList,optionList"/>
                                    <a4j:commandButton id="exit" value="Exit" action="#{LeaveMaster.btnExitAction}"/>
                                </h:panelGroup>
                            </a4j:region>
                        </h:panelGrid>
                    </h:panelGrid>
                </a4j:form>
                <rich:modalPanel id="popUpGridPanel" width="600" moveable="false" height="370" onmaskdblclick="#{rich:component('popUpGridPanel')}.hide();">
                    <f:facet name="header">
                        <h:panelGrid style="width:100%;text-align:center;">
                            <h:outputText value="Holiday Details" style="text-align:center;"/>
                        </h:panelGrid>
                    </f:facet>
                    <f:facet name="controls">
                        <h:panelGroup>
                            <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="closelink"/>
                            <rich:componentControl for="popUpGridPanel" attachTo="closelink" operation="hide" event="onclick"/>
                        </h:panelGroup>
                    </f:facet>
                    <h:form>
                        <h:panelGrid id="gridPanelTable" style="width:100%;height:300px;" styleClass="row1" columnClasses="vtop" >
                            <rich:dataTable  value="#{LeaveMaster.leaveMasterGrid}" var="dataItem"
                                             rowClasses="gridrow1, gridrow2" id="leaveList" rows="5" rowKeyVar="row"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                             onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column width="70px"><h:outputText value="Leave Code"/></rich:column>
                                        <rich:column width="80px"><h:outputText value="Leave Nature"/></rich:column>
                                        <rich:column width="90px"><h:outputText value="Applicable Date"/></rich:column>
                                        <rich:column width="80px"><h:outputText value="No of Days"/></rich:column>
                                        <rich:column width="60px"><h:outputText value="Encash"/></rich:column>
                                        <rich:column width="130px"><h:outputText value="Description"/></rich:column>
                                        <rich:column width="50px"><h:outputText value="Select"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.leaveCode}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.leaveDesc}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.applicableDate}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.noOfDays}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.enCash}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.description}"/></rich:column>
                                <rich:column style="text-align:center">
                                    <a4j:commandLink ajaxSingle="true" id="deletelink" action="#{LeaveMaster.setLeaveDetails}" oncomplete="#{rich:component('popUpGridPanel')}.hide()"
                                                     reRender="delete,appliCalen,noofDsaysText,decsText,leaveText,errormsg,enCashText,leaveNatureList,popUpGridPanel">
                                        <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{LeaveMaster.currentItem}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="leaveList" maxPages="20"/>
                        </h:panelGrid>
                        <h:panelGrid id="footerPaneInRich" style="width:100%;text-align:center;" styleClass="footer">
                            <a4j:commandButton id="close" value="Close" onclick="#{rich:component('popUpGridPanel')}.hide()" reRender="popUpGridPanel"/>
                        </h:panelGrid>
                    </h:form>
                </rich:modalPanel> 
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="btnProcessRegion"/>
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
        </body>
    </html>
</f:view>

