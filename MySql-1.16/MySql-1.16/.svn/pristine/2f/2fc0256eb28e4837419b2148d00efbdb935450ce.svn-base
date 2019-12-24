<%--
    Document   : ZoneLocationMaster
    Created on : Jul 20, 2011, 2:54:12 PM
    Author     : Administrator
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
            <title>Zone Location Master</title>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            
            <a4j:form id="ZoneLocationMaster">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">

                    <h:panelGrid columns="3" id="headerpanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stDate" styleClass="output" value="#{ZoneLocationMaster.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="OL30" styleClass="headerLabel" value="Zone Location Master"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ZoneLocationMaster.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid  id="errPanel"   width="100%" style="height:1px;text-align:center;" styleClass="row2">
                        <h:outputText id="errMsg" value="#{ZoneLocationMaster.message}" styleClass="error"/>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col8" columns="2" id="gridPanel1" style="text-align:left;" width="100%" styleClass="row1">
                        <h:outputLabel styleClass="label" value="Zone" style="padding-left:300px"/>
                        <h:selectOneListbox id="ddZone" size="1" styleClass="ddlist" style="width:200px" value="#{ZoneLocationMaster.zoneValue}">
                            <f:selectItems value="#{ZoneLocationMaster.zoneList}"/>
                            <a4j:support event="onchange" action="#{ZoneLocationMaster.getAssignedLocations}"
                                         reRender="ddLocationsAvailable,errMsg,ddLocationsAssigned"/>
                        </h:selectOneListbox>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col8" columns="2" id="gridPanel2" style="text-align:left;" width="100%" styleClass="row2">
                        <h:outputLabel styleClass="label" value="Locations Available" style="padding-left:300px"/>
                        <h:selectManyListbox id="ddLocationsAvailable" size="1" styleClass="ddlist" style="height:95px;width:200px" value="#{ZoneLocationMaster.locationValues}">
                            <f:selectItems value="#{ZoneLocationMaster.locationAvailableList}"/>
                        </h:selectManyListbox>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col8" columns="2" id="gridPanel3" style="text-align:left;" width="100%" styleClass="row1">
                        <h:outputLabel styleClass="label" value="Locations Assigned" style="padding-left:300px"/>
                        <h:selectManyListbox id="ddLocationsAssigned" size="1" styleClass="ddlist" style="height:95px;width:200px" value="#{ZoneLocationMaster.locationAssignedValues}">
                            <f:selectItems value="#{ZoneLocationMaster.locationAssignedList}"/>
                        </h:selectManyListbox>
                    </h:panelGrid>


                    <h:panelGrid id="footerPanel4" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="save" value="Save" action="#{ZoneLocationMaster.saveAction}"
                                               reRender="ddLocationsAvailable,errMsg,ddLocationsAssigned"/>
                            <a4j:commandButton id="delete" value="Delete" action="#{ZoneLocationMaster.deleteAction}"
                                               reRender="ddLocationsAvailable,errMsg,ddLocationsAssigned"/>
                            <a4j:commandButton id="cancel" value="Cancel" action="#{ZoneLocationMaster.cancelAction}" type="reset" reRender="mainPanel,ddLocationsAvailable,errMsg,ddLocationsAssigned"/>
                            <a4j:commandButton id="exit" value="Exit" action="#{ZoneLocationMaster.exitAction}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
