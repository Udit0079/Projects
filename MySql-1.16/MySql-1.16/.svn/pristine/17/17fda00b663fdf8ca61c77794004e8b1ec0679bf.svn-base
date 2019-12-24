<%-- 
    Document   : sharememberexpire
    Created on : 16 Jul, 2019, 3:54:14 PM
    Author     : root
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Member to be Retire</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".frDt").mask("99/99/9999");
                    jQuery(".toDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="RetireShareMember">
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/> 
                    </f:facet>
                </rich:modalPanel>
                <h:panelGrid id="PanelGridMain" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="HeaderPanel" columns="3"  style="text-align:center;" styleClass="header" width="100%">
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{ShareMemberExpire.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="Member to be Retire"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUsername" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="otUsername" styleClass="output" value="#{ShareMemberExpire.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>    
                    <h:panelGrid  id="errPanel" style="text-align:center" styleClass="row1">
                        <h:outputText id="errmsg" value="#{ShareMemberExpire.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col15,col15,col15,col15,col15,col15,col15,col1" columns="8" id="gridPanel4"  styleClass="row2"> 
                        <h:outputLabel id="brCode" styleClass="label"  value="Branch"/>
                        <h:selectOneListbox id="brCodeList" styleClass="ddlist" size="1" style="width: 80px" value="#{ShareMemberExpire.branch}" >
                            <f:selectItems value="#{ShareMemberExpire.branchList}"/>                     
                        </h:selectOneListbox>
                       <h:outputLabel id="dayage1" styleClass="label" value="Retirement Age" ></h:outputLabel>
                       <h:inputText id="dayage2" tabindex="6" value="#{ShareMemberExpire.retireAge}" styleClass="input" style="width:60px" disabled = "true" /> 
                        <h:outputLabel id="lbltoDate"  value="As On Date" styleClass="label" />
                        <h:inputText id="toDt" styleClass="input toDt" maxlength="10" value="#{ShareMemberExpire.asOndate}" style="width:70px;">
                             <a4j:support event="onblur" focus="day2" reRender="day2"/>
                        </h:inputText>   
                        <h:outputLabel id="day1" styleClass="label" value="Retirement Period(Days)" ></h:outputLabel>
                        <h:inputText id="day2" tabindex="6" value="#{ShareMemberExpire.retirePeriod}" styleClass="input" style="width:60px" >
                            <a4j:support event="onblur" focus="btnHtml" reRender="btnHtml"/> 
                        </h:inputText> 
                    </h:panelGrid>  
                    <h:panelGrid id="FooterPanel" bgcolor="#fff" style="text-align:center;" styleClass="footer" width="100%">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnHtml" value="Print Report" action="#{ShareMemberExpire.PrintViwe}" reRender="PanelGridMain,errmsg"/>
                             <h:commandButton id="btnDownload"  value="PDF Download" action="#{ShareMemberExpire.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="btnReset" value="Refresh" action="#{ShareMemberExpire.refresh()}" reRender="PanelGridMain,errmsg" oncomplete="setMask();"/>        
                            <a4j:commandButton id="btnClose" value="Exit" action="#{ShareMemberExpire.closeAction}" reRender="errmsg"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>