<%-- 
    Document   : HolidaYMarking_Register
    Created on : May 15, 2010, 3:05:24 PM
    Author     : jitendra kumar Chaudhary
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <title>Holiday Marking Register</title>
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{HolidayMarkingRegister.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Holiday Marking Register"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{HolidayMarkingRegister.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel1" width="100%">
                        <h:panelGrid columns="1" id="gridPanel4" width="100%">
                            <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel15" style="height:30px;" styleClass="row1" width="100%">
                                <h:outputLabel id="label1" styleClass="label" for="ddWhatDoYouWantToDo" value="What Do You Want To Do"><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddWhatDoYouWantToDo" styleClass="ddlist" size="1" style="width:130px" value="#{HolidayMarkingRegister.whatDoWant}" tabindex="1">
                                    <f:selectItem itemValue="HOLIDAY MARK"/>
                                    <f:selectItem itemValue="HOLIDAY UNMARK"/>
                                    <a4j:support ajaxSingle="true" event="onchange" action="#{HolidayMarkingRegister.whatdou}" reRender="btnMark,btnPanel,btnUnMark,stxtMsg," focus="txtFestival"/>
                                </h:selectOneListbox>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel18" style="height:30px;" styleClass="row2" width="100%">
                                <h:outputLabel id="label12" styleClass="label" value="Festival"/>
                                <h:inputText id="txtFestival" styleClass="input"style="width:130px" value="#{HolidayMarkingRegister.holidayDescriptions}" tabindex="2" onkeyup="this.value=this.value.toUpperCase();"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel19" style="height:30px;" styleClass="row1" width="100%">
                                <h:outputLabel id="label13" styleClass="label" value="Date"/>
                                <rich:calendar datePattern="dd/MM/yyyy" id="calDate" value="#{HolidayMarkingRegister.holidayDates}"  tabindex="3"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel1a" style="height:30px;" styleClass="row2" width="100%"/>
                            <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel19b" style="height:30px;" styleClass="row1" width="100%"/>
                            <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel19c" style="height:30px;" styleClass="row2" width="100%">
                                <h:outputText id="stxtMsg" styleClass="error" value="#{HolidayMarkingRegister.message}"/>
                            </h:panelGrid>
                        </h:panelGrid>                         
                        <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel103" width="100%" styleClass="row2" style="height:200px;">
                            <a4j:region>
                                <rich:dataTable value="#{HolidayMarkingRegister.register}" var="dataItem" rowClasses="gridrow1,gridrow2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="3"><h:outputText value="Holiday Marking Register" /></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="Holiday Date" /></rich:column>
                                            <rich:column><h:outputText value="Holiday Name" /></rich:column>
                                            <rich:column><h:outputText value="Action" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{dataItem.holidayDate}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.holidayDescription}" /></rich:column>
                                    <rich:column style="text-align:center;width:40px">
                                        <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{HolidayMarkingRegister.setRowValues}" reRender="txtFestival,calDate,ddWhatDoYouWantToDo,btnMark,btnPanel,btnUnMark">
                                            <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                            <f:setPropertyActionListener value="#{dataItem}" target="#{HolidayMarkingRegister.currentItem}"/>
                                        </a4j:commandLink>
                                    </rich:column>
                                </rich:dataTable>
                                <rich:datascroller id="datscroller" align="left" for="taskList" maxPages="20"/>
                            </a4j:region>
                        </h:panelGrid>                     
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnMark" value="Mark" rendered="#{HolidayMarkingRegister.flag1}" action="#{HolidayMarkingRegister.SaveData}"  reRender="calDate,txtFestival,stxtMsg,taskList,btnMark,btnPanel,btnUnMark,ddWhatDoYouWantToDo,datscroller,gridPanel103"/>
                            <a4j:commandButton id="btnUpdate" value="Update" action="#{HolidayMarkingRegister.upDate}"  reRender="calDate,gridPanel103,txtFestival,stxtMsg,btnMark,btnPanel,btnUnMark,ddWhatDoYouWantToDo,datscroller" rendered="#{HolidayMarkingRegister.whatDoWant == 'HOLIDAY UNMARK'}"/>
                            <a4j:commandButton id="btnUnMark" value="Delete" action="#{HolidayMarkingRegister.delete}"  reRender="calDate,gridPanel103,txtFestival,stxtMsg,btnMark,btnPanel,btnUnMark,ddWhatDoYouWantToDo,datscroller" rendered="#{HolidayMarkingRegister.whatDoWant == 'HOLIDAY UNMARK'}"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{HolidayMarkingRegister.resetAllValue}" reRender="calDate,ddWhatDoYouWantToDo,txtFestival,stxtMsg,btnMark,btnPanel,btnUnMark,btnMark,datscroller"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{HolidayMarkingRegister.exitFrm}"  reRender="calDate,ddWhatDoYouWantToDo,txtFestival,stxtMsg,btnMark,btnPanel,btnUnMark,btnMark,datscroller"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>

                <%--           <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
<rich:modalPanel id="wait" autosized="true" width="200" height="60" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
    <f:facet name="header">
        <h:outputText value="Processing" />
    </f:facet>
    <h:outputText value="Wait Please..." />
</rich:modalPanel>
<rich:messages></rich:messages>--%>
            </a4j:form>
        </body>
    </html>
</f:view>
