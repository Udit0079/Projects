<%-- 
    Document   : glbEntry
    Created on : 7 Aug, 2015, 5:22:23 PM
    Author     : root
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html>

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Daily Report</title>
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
            <a4j:form id="form1"> 
                <a4j:keepAlive beanName="GlbEntry"/> 
                <h:panelGrid bgcolor="#fff" columns="1" id="a1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel value="Date : " styleClass="headerLabel"/>
                            <h:outputText value="#{GlbEntry.todayDate}" id="stxtDate" styleClass="output"/>
                        </h:panelGroup>
                        <h:outputLabel value="Month End Balance Entry" styleClass="headerLabel"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel value="User : " styleClass="headerLabel"/>
                            <h:outputText value="#{GlbEntry.userName}" id="stxtUser" styleClass="output"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row1" style="height:30px;text-align:center;" width="100%">
                        <h:outputLabel id="lblMsg" styleClass="error" style="color:red;" value="#{GlbEntry.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="2" id="gridPanel4" style="height:30px;text-align:center;" styleClass="row2" width="100%">
                        <h:panelGroup layout="block">
                            <h:outputLabel id="lblRpType" styleClass="label" value="Report Type"/>
                            <h:selectOneListbox id="ddRpType" size="1" styleClass="ddlist" value="#{GlbEntry.reportType}">
                                <f:selectItems value="#{GlbEntry.reportTypeList}"/>
                                <a4j:support event="onblur" oncomplete="setMask();" focus="txtDate"/>
                            </h:selectOneListbox>
                        </h:panelGroup>
                        <h:panelGroup layout="block">
                            <h:outputLabel id="label4" value="Date :" styleClass="label">
                                <font class="required" style="color:red">*</font>
                            </h:outputLabel>
                            <h:panelGroup styleClass="label">
                              <h:inputText id="txtDate"   styleClass="input calInstDate" style="width:70px;"  value="#{GlbEntry.calDate}"/>
                                   <font color="purple">DD/MM/YYYY</font>
                            </h:panelGroup>                                                     
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gridPanel9" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="a100" layout="block">
                            <a4j:commandButton action="#{GlbEntry.btnPdfAction}"
                                               id="btnHtml" value="SAVE DATA" reRender="lblMsg,txtDate" oncomplete="setMask()"/>
                            <a4j:commandButton action="#{GlbEntry.btnExitAction}" 
                                               id="btnExit" value="Exit" reRender="calDate,lblMsg,txtDate" oncomplete="setMask()"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
