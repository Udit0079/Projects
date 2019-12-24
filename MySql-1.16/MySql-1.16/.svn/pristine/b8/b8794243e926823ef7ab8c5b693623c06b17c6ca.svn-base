<%-- 
    Document   : dayBook
    Created on : 16 Dec, 2011, 9:14:02 PM
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
                <a4j:keepAlive beanName="DayBook"/> 
                <h:panelGrid bgcolor="#fff" columns="1" id="a1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel value="Date : " styleClass="headerLabel"/>
                            <h:outputText value="#{DayBook.todayDate}" id="stxtDate" styleClass="output"/>
                        </h:panelGroup>
                        <h:outputLabel value="Day Book Report" styleClass="headerLabel"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel value="User : " styleClass="headerLabel"/>
                            <h:outputText value="#{DayBook.userName}" id="stxtUser" styleClass="output"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row1" style="height:30px;text-align:center;" width="100%">
                        <h:outputLabel id="lblMsg" styleClass="error" style="color:red;" value="#{DayBook.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col3" id="gridPanel4" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel value="Branch" styleClass="label"><font class="required" style="color:red">*</font></h:outputLabel>
                        <h:selectOneListbox id="listbranch" value="#{DayBook.branchOption}" styleClass="ddlist" size="1"  style="width:80px">
                            <f:selectItems value="#{DayBook.branchOptionList}" />
                            <a4j:support action="#{DayBook.checkExtCounter}" event="onblur" reRender="exLabel,exddl,lblMsg" focus="#{consolidateglb.focusId}"/>
                        </h:selectOneListbox> 
                        <h:outputLabel id="exLabel" value="Ext Counter Enclude" styleClass="label" style="display:#{DayBook.displayExCtr}"/>
                        <h:selectOneListbox id="exddl" size="1" value="#{DayBook.exCounter}" styleClass="ddlist" style="display:#{DayBook.displayExCtr}">
                            <f:selectItems value="#{DayBook.exCounterList}" />
                            
                        </h:selectOneListbox>
                        
                        <h:outputLabel id="label4" value="Date :" styleClass="label"><font class="required" style="color:red">*</font></h:outputLabel>
                        <h:inputText id="txtDate"   styleClass="input calInstDate" style="width:70px;"  value="#{DayBook.calDate}">
                            <font color="purple">DD/MM/YYYY</font>
                        </h:inputText>            
                    </h:panelGrid>                                 
                    <h:panelGrid columns="1" id="gridPanel9" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="a100" layout="block">
                            <a4j:commandButton action="#{DayBook.btnHtmlAction}"
                                               id="btnHtml" value="Print Report" reRender="lblMsg,txtDate" oncomplete="setMask()"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{DayBook.btnPdfAction}"  styleClass="color:#044F93;text-decoration:none;"/>

                            <a4j:commandButton action="#{DayBook.btnExitAction}" 
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
