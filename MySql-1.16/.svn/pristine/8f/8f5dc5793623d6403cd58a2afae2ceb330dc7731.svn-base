<%-- 
    Document   : trialBalance
    Created on : 15 Dec, 2011, 12:23:38 PM
    Author     : root
--%>

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
            <title>Trial Balance Report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />

            <script type="text/javascript"> 
                jQuery(function(jQuery){
                    setMask();
                });
                var row;
                function setMask(){
                    jQuery(".date").mask("99/99/9999");                  
                }
            </script>
        </head>
        <body>
            <a4j:form id="form1"> 
                <h:panelGrid bgcolor="#fff" columns="1" id="a1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel value="Date : " styleClass="headerLabel"/>
                            <h:outputText value="#{TrialBalance.todayDate}" id="stxtDate" styleClass="output"/>
                        </h:panelGroup>
                        <h:outputLabel value="Trial Balance Report" styleClass="headerLabel"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel value="User : " styleClass="headerLabel"/>
                            <h:outputText value="#{TrialBalance.userName}" id="stxtUser" styleClass="output"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="af5" style="width:100%;height:30px;text-align:center;" styleClass="row1" width="100%">
                        <h:outputText id="stxtErrMsg" styleClass="error" value="#{TrialBalance.message}" style="color:red;padding-left:100px;"/>
                    </h:panelGrid>
                   <h:panelGrid id="a5" columns="6" columnClasses="col1,col3,col3,col3,col3,col3" styleClass="row2" width="100%">
                        <h:outputLabel value="Branch" styleClass="label"/>
                        <h:selectOneListbox id="branchddl" size="1" value="#{TrialBalance.branch}" styleClass="ddlist">
                            <f:selectItems value="#{TrialBalance.branchList}" />
                             <a4j:support action="#{TrialBalance.checkExtCounter}" event="onblur" reRender="exLabel,exddl,lblMsg" focus="#{TrialBalance.focusId}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="exLabel" value="Ext Counter Enclude" styleClass="label" style="display:#{TrialBalance.displayExCtr}"/>
                        <h:selectOneListbox id="exddl" size="1" value="#{TrialBalance.exCounter}" styleClass="ddlist" style="display:#{TrialBalance.displayExCtr}">
                            <f:selectItems value="#{TrialBalance.exCounterList}" />
                        </h:selectOneListbox>
                        <h:outputLabel id="label4" value="Date : " styleClass="output">
                            <font class="required" style="color:red">*</font>
                        </h:outputLabel>
                        <h:inputText id="date" styleClass="input date" value="#{TrialBalance.calDate}" size="8">
                            <f:convertDateTime pattern="dd/MM/yyyy"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid columns="1" styleClass="footer" style="width:100%;text-align:center;">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton action="#{TrialBalance.btnHtmlAction}" 
                                               id="btnHtml" style="width: 100px" value="Print Report"/>
                             <h:commandButton id="btnDownload"  value="PDF Download" action="#{TrialBalance.btnPdfAction}"  styleClass="color:#044F93;text-decoration:none;"/>

                            <a4j:commandButton action="#{TrialBalance.btnExitAction}" 
                                               id="btnExit" style="width: 100px" value="Exit" reRender="stxtErrMsg,calDate"/>
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
