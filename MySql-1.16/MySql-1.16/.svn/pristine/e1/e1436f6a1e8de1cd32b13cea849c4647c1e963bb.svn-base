<%-- 
    Document   : introducercertificate
    Created on : 15 Dec, 2011, 2:41:26 PM
    Author     : Zeeshan Waris
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Introducer Certificate</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                var row;
                function setMask() {
                    jQuery(".calInstDate").mask("39/19/9999");
                }
            </script>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="IntroducerCertificate">
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
                <h:panelGrid id="PanelGridMain" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="HeaderPanel" columns="3"  style="text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{IntroducerCertificate.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="Introducer Certificate"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUsername" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="otUsername" styleClass="output" value="#{IntroducerCertificate.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="errPanel" style="text-align:center" styleClass="row1">
                        <h:outputText id="errmsg" value="#{IntroducerCertificate.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel3"  styleClass="row2"> 
                        <%--h:panelGroup>
                            <h:outputLabel id="lblAll" styleClass="label"  value="All"/>
                            <h:selectBooleanCheckbox id="checkbox" value="#{IntroducerCertificate.checkbox}">
                                <a4j:support event="onchange" actionListener="#{IntroducerCertificate.checkboxprocessValueChange}" oncomplete="#{rich:element('calfrm')}.style=setMask();
                                             #{rich:element('calto')}.style=setMask();" reRender="gridPanel4,groupPanelapptDt,groupPanelapptDt1"/>
                            </h:selectBooleanCheckbox>
                        </h:panelGroup--%>
                        <h:outputLabel id="lblSelType" styleClass="label"  value="Select Option"/>
                        <h:selectOneListbox id="ddSelType" styleClass="ddlist" size="1" style="width: 90px" value="#{IntroducerCertificate.selTp}">
                            <f:selectItems value="#{IntroducerCertificate.selTpList}"/>
                            <a4j:support event="onblur" actionListener="#{IntroducerCertificate.checkboxprocessValueChange}" 
                                         oncomplete="if(#{rich:element('ddSelType')}.value=='AT'){#{rich:element('ddAcType')}.focus();}else{#{rich:element('ddAccountOwner')}.focus();}setMask();" 
                                         reRender="gridPanel4,groupPanelapptDt,groupPanelapptDt1,ddAcType,errmsg"/>                           
                        </h:selectOneListbox>
                        <h:outputLabel id="lblAcType" styleClass="label"  value="Account Type"/>
                        <h:selectOneListbox id="ddAcType" styleClass="ddlist" size="1" style="width:140px" value="#{IntroducerCertificate.acTp}" disabled="#{IntroducerCertificate.acTpDisable}">
                            <f:selectItems value="#{IntroducerCertificate.acTpList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblAccountOwner" styleClass="label"  value="Account Owner"/>
                        <h:selectOneListbox id="ddAccountOwner" styleClass="ddlist" size="1" style="width: 134px" value="#{IntroducerCertificate.owner}">
                            <f:selectItems value="#{IntroducerCertificate.introducerList}"/>
                        </h:selectOneListbox>                        
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel4"  styleClass="row1"> 
                        <h:outputLabel id="lblAccountNo" styleClass="label"  value="Account No"/>
                        <h:panelGroup  layout="block">
                            <h:inputText id="txtAccountNo"  style="width: 90px" value="#{IntroducerCertificate.accountNo}" styleClass="input" maxlength="#{IntroducerCertificate.acNoMaxLen}" disabled="#{IntroducerCertificate.acnoDisable}">
                                <a4j:support actionListener="#{IntroducerCertificate.getNewAccountNo}"  event="onblur" 
                                             oncomplete="setMask();" reRender="stxtStAccountNo,groupPanelapptDt,groupPanelapptDt1,errmsg"/> 
                            </h:inputText>
                            <h:outputText id="stxtStAccountNo" styleClass="output" value="#{IntroducerCertificate.newAcno}" />
                        </h:panelGroup>
                        <h:outputLabel id="lblFromDate" value="From Date" styleClass="label"/>
                        <h:panelGroup id="groupPanelapptDt" layout="block">
                            <h:inputText id="calfrm" styleClass="input calInstDate"   style="width:70px;" maxlength="10" value="#{IntroducerCertificate.calFromDate}" disabled="#{IntroducerCertificate.fomDtDisable}" >
                                <f:convertDateTime pattern="dd/MM/yyyy" />
                            </h:inputText>
                            <h:outputLabel id="lblapptDT" styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                        </h:panelGroup>
                        <h:outputLabel id="lbltoDate"  value="To Date"  styleClass="label"/>
                        <h:panelGroup id="groupPanelapptDt1" layout="block">
                            <h:inputText id="calto" styleClass="input calInstDate"   style="width:70px;" maxlength="10" value="#{IntroducerCertificate.caltoDate}" disabled="#{IntroducerCertificate.toDtDisable}">
                                <f:convertDateTime pattern="dd/MM/yyyy" />
                            </h:inputText>
                            <h:outputLabel id="lblapptDT1" styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="FooterPanel" bgcolor="#fff" style="text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnPrint" value="Print Report" action="#{IntroducerCertificate.printAction}" oncomplete="setMask();" reRender="errmsg,groupPanelapptDt,groupPanelapptDt1"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{IntroducerCertificate.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{IntroducerCertificate.refreshAction}"  oncomplete="setMask();" reRender="groupPanelapptDt,groupPanelapptDt1,errmsg,txtAccountNo,stxtStAccountNo,ddSelType,ddAcType"/>
                            <a4j:commandButton action="#{IntroducerCertificate.exitAction}" value="Exit" reRender="errmsg"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>

