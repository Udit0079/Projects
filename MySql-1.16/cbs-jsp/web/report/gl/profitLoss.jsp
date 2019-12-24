<%-- 
    Document   : finalBalRep
    Created on : 13 Dec, 2011, 4:51:32 PM
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
            <title>Profit And Loss Report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />

            <script type="text/javascript"> 
                jQuery(function(jQuery){
                    setMask();
                });
                var row;
                function setMask(){
                    jQuery(".asondate").mask("99/99/9999");                  
                }
            </script>
        </head>
        <body>
            <a4j:form id="form1"> 
                <h:panelGrid bgcolor="#fff" columns="1" id="a1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel value="Date : " styleClass="headerLabel"/>
                            <h:outputText value="#{ProfitAndLoss.stxtDate}" id="stxtDate" styleClass="output"/>
                        </h:panelGroup>
                        <h:outputLabel value="Profit And Loss Report" styleClass="headerLabel"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel value="User : " styleClass="headerLabel"/>
                            <h:outputText value="#{ProfitAndLoss.stxtUser}" id="stxtUser" styleClass="output"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row1" style="height:30px;" width="100%">
                        <h:outputLabel id="lblMsg" styleClass="error" style="color:red;" value="#{ProfitAndLoss.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="2" id="a5" styleClass="row2" width="100%">
                        <h:outputLabel id="label4" value="Report Type : " style="padding-left:350px;" styleClass="label"/>
                        <h:selectOneListbox id="ddRep" styleClass="ddlist" value="#{ProfitAndLoss.ddRep}" size="1" style="width:185px">
                            <f:selectItems value="#{ProfitAndLoss.ddRepList}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    
                    <h:panelGrid columnClasses="col9" columns="3"  id="a6" styleClass="row1" width="100%">
                        <h:outputLabel id="label5" value="As On Date : " style="padding-left:350px;" styleClass="label"/>
                        
                        <h:inputText id="asondate" styleClass="input asondate" value="#{ProfitAndLoss.calDate}" size="10">
                                <f:convertDateTime pattern="dd/MM/yyyy"/>
                                <a4j:support event="onblur" action="#{ProfitAndLoss.onBlurOfDate}" reRender="repPanel" />
                        </h:inputText>
                        <h:panelGrid id="repPanel" style="display:#{ProfitAndLoss.showPanel}" columnClasses="col3" columns="1">
                          <h:selectOneListbox id="ddOptionList" styleClass="ddlist" value="#{ProfitAndLoss.selectOption}" size="1" style="width:180px">
                            <f:selectItems value="#{ProfitAndLoss.selectOptionList}"/>
                        </h:selectOneListbox>
                          </h:panelGrid>
                      
                       
                       <%-- <rich:calendar value="#{ProfitAndLoss.calDate}" datePattern="dd/MM/yyyy" id="calDate"/> --%>
                    </h:panelGrid>
                    <h:panelGrid columns="1" styleClass="footer" style="width:100%;text-align:center;">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton action="#{ProfitAndLoss.btnHtmlAction}" id="btnHtml"style="width: 100px" value="Print Report"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{ProfitAndLoss.btnPdfAction}"  styleClass="color:#044F93;text-decoration:none;"/>  
                            <a4j:commandButton action="#{ProfitAndLoss.btnExitAction}" id="btnExit"style="width: 100px" value="Exit" reRender="lblMsg,ddRep,calDate"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>