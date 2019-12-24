<%-- 
    Document   : holidayMarkProcess
    Created on : 27 Nov, 2018, 3:33:09 PM
    Author     : root
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <title>Holiday Marking Process</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".calInstDate").mask("39/19/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="brStatus">
                <h:panelGrid columns="1" id="gridPanel1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="grpPanel1" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{HolidayMarkProcess.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Holiday Marking process"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{HolidayMarkProcess.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid> 
                    <h:panelGrid  id="grdpane6" style="height:30px;text-align:center" styleClass="row1" width="100%" >
                      <h:outputText id="stxtError"  styleClass="error" value="#{HolidayMarkProcess.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col3" id="gridpanel12"   style="height:30px;" styleClass="row2" width="100%" >    
                     <h:outputLabel id="lblMode" styleClass="label" value="Mode"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddMode" style="width:180px" styleClass="ddlist"  value="#{HolidayMarkProcess.option}" size="1">
                            <f:selectItems value="#{HolidayMarkProcess.optionList}"/>
                            <a4j:support event="onblur" action="#{HolidayMarkProcess.onChangeFunction}" reRender="stxtError,lblToDate,txtDate,lblFromDate,txtfrDate" oncomplete="setMask();" focus="ddFileType"/>
                        </h:selectOneListbox>
                     <h:outputLabel id="lblFromDate" styleClass="headerLabel"  value="From Date" style="display:#{HolidayMarkProcess.displayDate}" />
                     <h:inputText id="txtfrDate"   styleClass="input calInstDate" style="setMask();display:#{HolidayMarkProcess.displayDate}" value="#{HolidayMarkProcess.frDate}" size="10"/>
                      <h:outputLabel id="lblToDate" styleClass="headerLabel"  value="To Date" style="display:#{HolidayMarkProcess.displayDate}" />
                        <h:panelGroup styleClass="label">
                            <h:inputText id="txtDate"   styleClass="input calInstDate" style="setMask();display:#{HolidayMarkProcess.displayDate}" value="#{HolidayMarkProcess.date}" size="10">
                            <a4j:support event="onblur" action="#{HolidayMarkProcess.ondateFunction}" reRender="stxtError,lblToDate,txtDate" oncomplete="setMask();"/>
                            </h:inputText>
                        </h:panelGroup>          
                    </h:panelGrid> 
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton  id="btnproces" value="Proceed"   action="#{HolidayMarkProcess.proceedAction}" reRender="stxtError,wait"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{HolidayMarkProcess.refreshForm}" reRender="gridPanel1" oncomplete="#{rich:element('txtDate')}.style=setMask()"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{HolidayMarkProcess.ExitForm}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                      <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
                        <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <f:facet name="header">
                                <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                            </f:facet>
                        </rich:modalPanel>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>