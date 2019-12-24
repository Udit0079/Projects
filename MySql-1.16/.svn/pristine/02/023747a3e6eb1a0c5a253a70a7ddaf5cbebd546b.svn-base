<%-- 
    Document   : HoGlbFlatFile
    Created on : Apr 25, 2011, 7:43:53 PM
    Author     : ROHIT KRISHNA
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
            <title>HO GLB Flat File</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery){
                    setMask();
                });
                var row;
                function setMask(){
                    jQuery(".calInstDate").mask("99/99/9999");
                }
            </script>
            
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{HoGlbFlatFile.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="HO GLB Flat File"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{HoGlbFlatFile.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="errorMessage" styleClass="error" value="#{HoGlbFlatFile.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{HoGlbFlatFile.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="2" id="a5" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblDt" styleClass="label" value="Date :" style="padding-left:350px;"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="calInstDate" maxlength="10" tabindex="1" styleClass="input calInstDate" value="#{HoGlbFlatFile.calDate}" style="width:75px;">
                            <f:convertDateTime pattern="dd/MM/yyyy" />
                            <a4j:support  event="onblur"  oncomplete="setMask()" focus="btnViewReport"/>
                        </h:inputText>
                        <%--<rich:calendar datePattern="dd/MM/yyyy" tabindex="1" id="calDt" value="#{HoGlbFlatFile.calDate}">
                            <a4j:support event="ondateselected"/>
                        </rich:calendar>--%>
                    </h:panelGrid>
                    <h:panelGrid id="a6" columns="2" style="width:100%;height:30px;text-align:center;" styleClass="row2" width="100%">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandLink id="saveLink" value="#{HoGlbFlatFile.fileName} "  style="color:blue;" styleClass="headerLabel"
                                             action="#{HoGlbFlatFile.downloadFile}" rendered="#{HoGlbFlatFile.saveLink}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:region id="btnRegion">
                            <a4j:commandButton id="btnViewReport" tabindex="2" value="View" action="#{HoGlbFlatFile.openReport}"
                                               oncomplete="if(#{HoGlbFlatFile.flag1==true})
                                                            {
                                                              #{rich:component('popUpRepPanel')}.show();
                                                            }
                                                            else if(#{HoGlbFlatFile.flag1==false})
                                                            {
                                                              #{rich:component('popUpRepPanel')}.hide();
                                                            }" reRender="popUpRepPanel,message,errorMessage,a5,a6,lpg"/>
                            <a4j:commandButton id="btnSaveToFile" tabindex="3" value="Save To File" action="#{HoGlbFlatFile.saveToFile}" reRender="message,errorMessage,a5,a6,lpg,saveLink"
                                               oncomplete="setMask()"/>
                            <a4j:commandButton id="btnRefresh" tabindex="5" value="Refresh" action="#{HoGlbFlatFile.resetForm}" reRender="message,errorMessage,a5,a6,lpg,saveLink" oncomplete="setMask()" />
                            <a4j:commandButton id="btnExit" tabindex="6" value="Exit" action="#{HoGlbFlatFile.exitBtnAction}" reRender="message,errorMessage,a5,lpg,a6,saveLink"/>
                            </a4j:region>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status  onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="btnRegion"/>
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
            </a4j:form>
            <rich:modalPanel id="popUpRepPanel" label="Form" width="1000" height="550" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpRepPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="GLB Report" style="text-align:center;"/>
                    </h:panelGrid>
                </f:facet>
                <f:facet name="controls">
                    <h:outputLink  id="closelink" onclick="#{rich:component('popUpRepPanel')}.hide()">
                        <h:graphicImage value="/resources/images/close.gif" style="border:0" />
                    </h:outputLink>
                </f:facet>  
                <table width="100%">
                    <tbody>
                        <tr>
                            <td>
                                <a4j:include viewId="#{HoGlbFlatFile.viewID}" />
                            </td>
                        </tr>
                    </tbody>
                </table>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
