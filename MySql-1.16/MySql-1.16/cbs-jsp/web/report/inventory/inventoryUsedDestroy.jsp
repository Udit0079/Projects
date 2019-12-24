<%-- 
    Document   : inventoryUsedDestroy
    Created on : Dec 26, 2011, 6:43:59 PM
    Author     : Ankit Verma
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Used/Destroyed Inventory</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".calInstDate").mask("39/19/9999");
                }

            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="mainPanelGrid" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{InventoryUsedDestroy.todayDate}"/>
                        </h:panelGroup>

                        <h:outputLabel styleClass="headerLabel" value="Used/Destroyed Inventory"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{InventoryUsedDestroy.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="grdpane6" style="height:30px;text-align:center" styleClass="row2" width="100%" >
                        <h:outputText id="stxtError"  styleClass="error" value="#{InventoryUsedDestroy.msg}"/>
                    </h:panelGrid>
                    <h:panelGrid style="height:30px" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" id="gridpanel1"  styleClass="row1" width="100%" >
                        <h:outputLabel id="branch" styleClass="label" value="Branch"/>
                        <h:selectOneListbox id="branchList" style="width:100px;" styleClass="ddlist" size="1" value="#{InventoryUsedDestroy.branch}"> 
                            <f:selectItems value="#{InventoryUsedDestroy.branchList}"/>
                            <a4j:support ajaxSingle="true" event="onblur"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblInventoryType" styleClass="output"  value="Inventory Type"></h:outputLabel>
                        <h:selectOneListbox id="type" value="#{InventoryUsedDestroy.type}" styleClass="ddlist" size="1" style="width:120px" >
                            <f:selectItems value="#{InventoryUsedDestroy.typeList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblToDate" styleClass="headerLabel"  value="Date"  />
                        <h:panelGroup styleClass="label">
                            <h:inputText id="txtDate"   styleClass="input calInstDate" style="setMask();width:70px;"  value="#{InventoryUsedDestroy.date}"/>
                            <font color="purple">DD/MM/YYYY</font>
                            </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columns="2" id="gridpanel5" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton  id="btnView" value="View"   action="#{InventoryUsedDestroy.viewReport}" reRender="stxtError,mainPanelGrid,txtDate" oncomplete="#{rich:element('txtDate')}.style=setMask()"/> 
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{InventoryUsedDestroy.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh"  action="#{InventoryUsedDestroy.refreshForm}"  reRender="mainPanelGrid,txtDate" oncomplete="#{rich:element('txtDate')}.style=setMask()"/>
                            <a4j:commandButton id="btnExit" value="Exit"  action="#{InventoryUsedDestroy.exitAction}" reRender="mainPanelGrid" />
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>              
            </a4j:form>
        </body>
    </html>
</f:view>
