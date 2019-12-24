<%-- 
    Document   : HolidayMaster
    Created on : May 25, 2011, 4:28:36 PM
    Author     : Sudhir Kumar Bisht
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
            <title>Holiday Master</title>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;

                jQuery(function(jQuery) {
                    setMask();
                });
                var row;
                function setMask() {
                    jQuery(".calInstDate").mask("39/99/9999");
                }
            </script>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="HolidayMaster">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1"  width="100%">
                    <h:panelGrid columns="3" id="gridPanel1" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stDate" styleClass="output" value="#{HolydayMaster.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="OL30" styleClass="headerLabel" value="Holiday Master"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{HolydayMaster.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2" columns="2" id="errPanel" style="text-align:center;" width="100%" styleClass="row2">
                        <h:outputText id="errMsg" styleClass="error" value="#{HolidayMaster.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanelFunc" style="text-align:left;" width="100%" styleClass="row1">
                        <h:outputLabel styleClass="label"  value="Operation"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox size="1" id="optionList"  styleClass="ddlist" value="#{HolidayMaster.function}" style="width:85px">
                                <f:selectItems value="#{HolidayMaster.functionList}"/>
                                <a4j:support event="onblur" action="#{HolidayMaster.onChangeFunction}" reRender="errMsg,popUpGridPanel,holidayMasterGrid,btnPanel" 
                                             oncomplete="if(#{HolidayMaster.function=='2'}){ #{rich:component('popUpGridPanel')}.show(); } 
                                             else {  #{rich:component('popUpGridPanel')}.hide();}"/>
                            </h:selectOneListbox>
                            <h:outputLabel styleClass="label"  value="Holiday Option"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox size="1" id="operationList"  styleClass="ddlist" value="#{HolidayMaster.option}" style="width:85px">
                                <f:selectItems value="#{HolidayMaster.optionList}"/>
                                <a4j:support event="onblur" action="#{HolidayMaster.setWeeklyHoliday}" reRender="gridPanel6,gridPanel9" oncomplete="setMask()" focus="#{HolidayMaster.focusId}"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="ccol2,col7,col2,col7" columns="4" id="gridPanel6" style="text-align:left;" width="100%" styleClass="row2">
                            <h:outputLabel id="holifrmdate" styleClass="output" value="Holiday Date From"/>
                            <h:panelGroup>
                                <h:inputText id="calIntDate1" styleClass="input calInstDate"  style="width:80px;" maxlength="10"  value="#{HolidayMaster.holidayFromDt}" disabled="#{HolidayMaster.weekHoliday}">
                                    <f:convertDateTime pattern="dd/MM/yyyy" />
                                </h:inputText>
                                <h:outputLabel id="lblDurationFrom2" styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                            </h:panelGroup>
                            <h:outputLabel id="holiToDate" styleClass="output" value="Holiday Date To "/>
                            <h:panelGroup>
                                <h:inputText id="calIntDate2" styleClass="input calInstDate"  style="width:80px;" maxlength="10" value="#{HolidayMaster.holidayToDt}" disabled="#{HolidayMaster.weekHoliday}" >
                                    <f:convertDateTime pattern="dd/MM/yyyy" />
                                </h:inputText>
                                <h:outputLabel id="lblDurationFrom1" styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel9" style="text-align:left;" width="100%" styleClass="row1">
                            <h:outputLabel id="holidayDescrip" styleClass="output" value="Holiday Description"/>
                            <h:inputText id="holidayDescInput" styleClass="input" value="#{HolidayMaster.holidayDesc}" disabled="#{HolidayMaster.weekHoliday}" onkeyup="this.value=this.value.toUpperCase();"/>

                        <h:outputLabel id="weekDayLbl" value="Week Days" styleClass="output"/>
                        <h:selectOneListbox id="weekDayList" styleClass="ddlist" size="1" style="width:85px" value="#{HolidayMaster.weekDay}" disabled="#{!HolidayMaster.weekHoliday}">
                            <f:selectItems value="#{HolidayMaster.weekDaysList}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>

                    <h:panelGrid id="footerPanel4" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="save"  value="Save" action="#{HolidayMaster.saveDetails}" disabled="#{HolidayMaster.disableSave}"
                                               reRender="mainPanel,errMsg" oncomplete="setMask()"/>
                            <a4j:commandButton id="delete" value="Delete" action="#{HolidayMaster.deleteDetails}" disabled="#{HolidayMaster.function== '1'}" oncomplete="setMask()" reRender="mainPanel,errMsg"/>
                            <a4j:commandButton id="cancel" value="Refresh" action="#{HolidayMaster.refresh}" reRender="mainPanel,errMsg" oncomplete="setMask()"/>
                            <a4j:commandButton id="exit" value="Exit" action="#{HolidayMaster.exit}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>

            <rich:modalPanel id="popUpGridPanel" width="600" moveable="false" height="250" onmaskdblclick="#{rich:component('popUpGridPanel')}.hide();">
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
                    <h:panelGrid id="holidayMasterGrid" style="width:100%;" styleClass="row1" columnClasses="vtop">
                        <rich:dataTable  value="#{HolidayMaster.dataTable}" var="dataItem"
                                         rowClasses="gridrow1, gridrow2" id="taskList1" rows="5" rowKeyVar="row"
                                         onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                         onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                         onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column width="100px" ><h:outputText style="text-align:center" value="HoliDay Date" /></rich:column>
                                    <rich:column width="100px" ><h:outputText style="text-align:center" value="Description" /></rich:column>
                                    <rich:column width="100px" ><h:outputText style="text-align:center" value="Day" /></rich:column>
                                    <rich:column width="100px" ><h:outputText style="text-align:center" value="Select" /></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column width="100px" ><h:outputText style="text-align:center" value="#{dataItem.holidaydate}" /></rich:column>
                            <rich:column width="100px" ><h:outputText style="text-align:center" value="#{dataItem.holidaydes}" /></rich:column>
                            <rich:column width="100px" ><h:outputText style="text-align:center" value="#{dataItem.holidayday}" /></rich:column>
                            <rich:column width="100px" style="text-align:center">
                                <a4j:commandLink id="selectlink" ajaxSingle="true" action="#{HolidayMaster.selectItem}" reRender="mainPanel" onclick="#{rich:component('popUpGridPanel')}.hide();">
                                    <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                    <f:setPropertyActionListener value="#{dataItem}" target="#{HolidayMaster.currentItem}"/>
                                </a4j:commandLink>
                            </rich:column>

                        </rich:dataTable>
                        <rich:datascroller align="left" for="taskList1" maxPages="20"/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPaneInRich" style="width:100%;text-align:center;" styleClass="footer">
                        <a4j:commandButton id="close" value="Close" onclick="#{rich:component('popUpGridPanel')}.hide()" reRender="popUpGridPanel"/>
                    </h:panelGrid>
                </h:form>
            </rich:modalPanel>           
        </body>
    </html>
</f:view>
