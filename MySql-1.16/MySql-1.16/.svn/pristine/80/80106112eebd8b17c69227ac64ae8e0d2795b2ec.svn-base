<%-- 
    Document   : consolidateglb
    Created on : Feb 4, 2013, 2:48:58 PM
    Author     : sipl
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
            <title>General Ledger Book</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".calDate").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="form1"> 
                <h:panelGrid bgcolor="#fff" columns="1" id="a1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel value="Date : " styleClass="headerLabel"/>
                            <h:outputText value="#{consolidateglb.todayDate}" id="stxtDate" styleClass="output"/>
                        </h:panelGroup>
                        <h:outputLabel value="General Ledger Book" styleClass="headerLabel"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel value="User : " styleClass="headerLabel"/>
                            <h:outputText value="#{consolidateglb.userName}" id="stxtUser" styleClass="output"/>
                        </h:panelGroup>
                    </h:panelGrid> 
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row1" style="height:30px;" width="100%">
                        <h:outputLabel id="lblMsg" styleClass="error" style="color:red;" value="#{consolidateglb.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel1" columns="6" columnClasses="col1,col3,col3,col3,col3,col3" styleClass="row2" width="100%">
                        <h:outputLabel id="brLbl" value="Branch" styleClass="label"/>
                        <h:selectOneListbox id="branchddl" size="1" value="#{consolidateglb.branch}" styleClass="ddlist">
                            <f:selectItems value="#{consolidateglb.branchList}" />
                            <a4j:support action="#{consolidateglb.checkExtCounter}" event="onblur" reRender="exLabel,exddl,lblMsg" focus="#{consolidateglb.focusId}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="exLabel" value="Ext Counter Enclude" styleClass="label" style="display:#{consolidateglb.displayExCtr}"/>
                        <h:selectOneListbox id="exddl" size="1" value="#{consolidateglb.exCounter}" styleClass="ddlist" style="display:#{consolidateglb.displayExCtr}">
                            <f:selectItems value="#{consolidateglb.exCounterList}" />
                            
                        </h:selectOneListbox>
                        <h:outputLabel id="label4" value="Date : " styleClass="label">
                            <font class="required" style="color:red">*</font>
                        </h:outputLabel>
                        <h:inputText id="calDate" styleClass="input calDate" value="#{consolidateglb.calDate}" size="8">
                            <f:convertDateTime pattern="dd/MM/yyyy"/>
                        </h:inputText>                        
                    </h:panelGrid>
                       <h:panelGrid columns="1" styleClass="footer" style="width:100%;text-align:center;">  
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton action="#{consolidateglb.btnHtmlAction}" id="btnHtml"
                                               value="Print Report" reRender="lblMsg,calDate"/>
                             <h:commandButton id="btnDownload"  value="PDF Download" action="#{consolidateglb.btnPdfAction}"  styleClass="color:#044F93;text-decoration:none;"/>
                             <a4j:commandButton action="#{consolidateglb.refreshAction}" id="btnRefresh" value="Refresh" reRender="lblMsg,calDate,brLbl,branchddl,exLabel,exddl" oncomplete="setMask()"/>
                             <a4j:commandButton action="#{consolidateglb.btnExitAction}" id="btnExit" value="Exit" reRender="lblMsg,calDate"/>
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