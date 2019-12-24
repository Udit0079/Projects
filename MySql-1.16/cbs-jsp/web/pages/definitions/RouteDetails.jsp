<%--
    Document   : RouteDetails
    Created on : Jul 19, 2011, 6:31:14 PM
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
            <title>Route Details</title>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="RouteDetails">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">

                    <h:panelGrid columns="3" id="headerpanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stDate" styleClass="output" value="#{RouteDetails.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="OL30" styleClass="headerLabel" value="Route Details"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{RouteDetails.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid  id="errPanel"   width="100%" style="height:1px;text-align:center;" styleClass="row2">
                        <h:outputText id="errMsg" value="#{RouteDetails.message}" styleClass="error"/>
                    </h:panelGrid>
                  <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanelFunc" style="text-align:left;" width="100%" styleClass="row2">
                      <h:outputLabel styleClass="label" style="padding-left:80px" value="Operation"><font class="required" style="color:red;">*</font></h:outputLabel>  
                      <h:selectOneListbox size="1" id="operationList"  styleClass="ddlist" value="#{RouteDetails.operation}">
                          <f:selectItems value="#{RouteDetails.operationList}"/>
                          <a4j:support event="onblur" action="#{RouteDetails.setOperation}" reRender="popUpGridPanel,mainPanel" oncomplete="if(#{RouteDetails.operation=='2'}){
                                                                                                                                                                #{rich:component('popUpGridPanel')}.show();
                                                                                                                                                                }
                                                                                                                                                                else 
                                                                                                                                                                {
                                                                                                                                                                #{rich:component('popUpGridPanel')}.hide();
                                                                                                                                                                };#{rich:element('txtRouteCode')}.style=setTimeMask(); 
                                                                                                                                                                #{rich:element('txtRouteStart')}.style=setTimeMask();
                                                                                                                                                                #{rich:element('txtRouteEnd')}.style=setTimeMask();
                                                                                                                                                                #{rich:element('txtRouteVia')}.style=setTimeMask();"/>
                        </h:selectOneListbox>
                      <h:outputText style="padding-left:80px"/>
                      <h:outputText style="padding-left:80px"/>
                      </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel1" style="text-align:left;" width="100%" styleClass="row1">
                        <h:outputLabel styleClass="label" style="padding-left:80px" value="Route Code"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:inputText id="txtRouteCode" maxlength="10" disabled="#{RouteDetails.disableRouteCode}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input" value="#{RouteDetails.routeCode}"/>
                        <h:outputLabel styleClass="label"style="padding-left:80px" value="Route Start"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:inputText id="txtRouteStart" maxlength="25" disabled="#{RouteDetails.disableRouteStart}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"  value="#{RouteDetails.routeStart}"/>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel2" style="text-align:left;" width="100%" styleClass="row2">
                        <h:outputLabel styleClass="label" style="padding-left:80px" value="Route End"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:inputText id="txtRouteEnd" maxlength="25" disabled="#{RouteDetails.disableRouteEnd}" styleClass="input" onkeyup="this.value = this.value.toUpperCase();" value="#{RouteDetails.routeEnd}"/>
                        <h:outputLabel styleClass="label" style="padding-left:80px" value="Route Via"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:inputText id="txtRouteVia" maxlength="25" disabled="#{RouteDetails.disableRouteVia}" styleClass="input" onkeyup="this.value = this.value.toUpperCase();" value="#{RouteDetails.routeVia}"/>
                    </h:panelGrid>

                   
                    <h:panelGrid id="footerPanel4" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                       
                            <a4j:commandButton id="save" value="Save" action="#{RouteDetails.saveAction}" disabled="#{RouteDetails.disableSaveButton}"
                                               reRender="mainPanel"/>
                            <a4j:commandButton id="delete" value="Delete" action="#{RouteDetails.deleteAction}" disabled="#{RouteDetails.disableDeleteButton}"
                                               reRender="mainPanel"/>
                            <a4j:commandButton id="cancel" value="Cancel" action="#{RouteDetails.cancelAction}" type="reset"
                                               reRender="mainPanel"/>
                            <a4j:commandButton id="exit" value="Exit" action="#{RouteDetails.exitAction}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
               <rich:modalPanel id="popUpGridPanel" label="Form" width="700" height="200" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpRepPanel')}.hide();">
                 <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Route Details" style="text-align:center;"/>
                    </h:panelGrid>
                </f:facet>  
                 <h:panelGrid columnClasses="vtop" columns="1" id="tablePanel1"  styleClass="row2" width="100%">
                     <a4j:region>
                         <rich:dataTable value="#{RouteDetails.routeDetailsTable}"
                                            var="dataItem"
                                            rowClasses="gridrow1,gridrow2"
                                            id="taskList1"
                                            rows="3"
                                            columnsWidth="100"
                                            rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                            width="100%">
                           <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column><h:outputText value="S.No." style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Route Code" style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Route Start" style="text-align:left" /></rich:column>
                                        <rich:column><h:outputText value="Route End"  style="text-align:left"/></rich:column>
                                        <rich:column><h:outputText value="Route Via" style="text-align:left" /></rich:column>
                                        <rich:column><h:outputText value="Select"  style="text-align:center"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.sno}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.code}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.start}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.end}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.via}" /></rich:column>
                                 <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink id="selectlink" action="#{RouteDetails.setRouteRowValues}"
                                                     oncomplete="#{rich:element('txtRouteCode')}.style=setTimeMask(); 
                                                     #{rich:element('txtRouteStart')}.style=setTimeMask();
                                                     #{rich:element('txtRouteEnd')}.style=setTimeMask();
                                                     #{rich:element('txtRouteVia')}.style=setTimeMask();
                                                     #{rich:element('tablePanel1')}.style.display=none;"
                                                     reRender="mainPanel,txtRouteStart,txtRouteEnd,popUpGridPanel" focus="selectlink">
                                        <h:graphicImage value="/resources/images/select.gif" style="border:0" ondblclick=" #{rich:component('popUpGridPanel')}.hide();"/>
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{RouteDetails.currentRouteItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{RouteDetails.currentRow}"/>
                                    </a4j:commandLink>
                                </rich:column>
                                   <f:facet name="footer">
                                    <h:outputText value="#{RouteDetails.totalRouteRecords} rows found" style="text-align:center" />
                                </f:facet>
                         </rich:dataTable>
                          <rich:datascroller align="left" for="taskList1" maxPages="10" />
                     </a4j:region>
                 </h:panelGrid>
              </rich:modalPanel>
            </a4j:form>
        </body>
    </html>
</f:view>

