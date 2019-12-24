<%-- 
    Document   : AttendenceTracking
    Created on : Jul 5, 2011, 12:41:16 AM
    Author     : Administrator
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
            <title>Attendence Tracking</title>
             <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
            </script>
        </head>
        <body>
            <a4j:form id="attendencetracking">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel1" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stDate" styleClass="output" value="#{AttendenceTracking.todayDate}" />
                        </h:panelGroup>
                        <h:outputLabel id="OL30" styleClass="headerLabel" value="Attendence Tracking"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{AttendenceTracking.userName}"  />
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel5" columns="1"  style="width:100%;text-align:center;" styleClass="row2">
                        <h:outputLabel id="errmsg" value="#{AttendenceTracking.err}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel2" columns="6" columnClasses="col3,col3,col4,col3,col3,col3" style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputText />
                        <h:outputText />
                        <h:outputLabel value="Company Name" styleClass="output"/>
                        <h:outputText id="stxtCmpny" styleClass="output" value="#{AttendenceTracking.cmpname}" />
                        <h:outputText />
                        <h:outputText />
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel3" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputText />
                        <h:outputText />
                        <h:outputLabel value="Login" styleClass="output"/>
                        <h:inputText id="userText" styleClass="input" value="#{AttendenceTracking.user}" onkeyup="this.value = this.value.toUpperCase();"/>
                        <h:outputText />
                        <h:outputText />
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel4" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputText />
                        <h:outputText />
                        <h:outputLabel value="Password" styleClass="output"/>
                        <h:inputSecret id="passwordSecret" styleClass="input"  value="#{AttendenceTracking.password}"/>
                        <h:outputText />
                        <h:outputText />
                    </h:panelGrid>
                    <h:panelGrid id="gridPanelTable" style=" width:100%;height:0px" styleClass="row1" columnClasses="vtop">
                        <a4j:region>
                            <rich:dataTable  value="#{AttendenceTracking.resultgrid}" var="dataItem"
                                             rowClasses="gridrow1, gridrow2" id="taskList" rows="10" rowKeyVar="row"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                             onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column width="100px"><h:outputText value="Date"/></rich:column>
                                        <rich:column width="100px"><h:outputText value="In Time"/></rich:column>
                                        <rich:column width="100px"><h:outputText value="Out Time"/></rich:column>
                                        <rich:column width="100px"><h:outputText value="Month"/></rich:column>
                                        <rich:column width="100px"><h:outputText value="Update"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                 <rich:column>
                                    <h:outputText value="#{dataItem.date}"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem.timein}"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem.timeout}"/>
                                </rich:column>

                                <rich:column>
                                    <h:outputText value="#{dataItem.month}"/>
                                </rich:column>
                                <rich:column>
                                    <a4j:commandLink ajaxSingle="true" id="deletelink" reRender="appliCalen,noofDsaysText,decsText,leaveText,errormsg,fromDateText,toDateText,enCashText,leaveNatureList" >
                                        <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                    </a4j:commandLink>
                                </rich:column>

                            </rich:dataTable>
                             <rich:datascroller align="left" for="taskList" maxPages="5"/>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel6" columns="6"  style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="groupPanel8" layout="block" >
                            <a4j:commandButton id="viewattendencebutton"  value="View Attendence" action="#{AttendenceTracking.getdatagriditem}"  reRender="mainPanel"/>
                            <a4j:commandButton id="saveButton"  value="Save"  action="#{AttendenceTracking.savedataAttend}" reRender="mainPanel"/>
                            <a4j:commandButton id="refreshButton"  value="Refresh"  action="#{AttendenceTracking.reset}" reRender="mainPanel" />
                            <a4j:commandButton id="exitButton" value="Exit" action="#{AttendenceTracking.btnExitAction}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>