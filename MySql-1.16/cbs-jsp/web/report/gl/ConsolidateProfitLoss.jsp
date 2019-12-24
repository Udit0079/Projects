<%--
    Document   : ConsolidateProfitLoss
    Created on : Feb 16, 2013, 2:24:00 PM
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
            <title>Consolidated Profit And Loss</title>
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
                            <h:outputText value="#{ConsolidateProfitLoss.todayDate}" id="stxtDate" styleClass="output"/>
                        </h:panelGroup>
                        <h:outputLabel value="Consolidated Profit And Loss" styleClass="headerLabel"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel value="User : " styleClass="headerLabel"/>
                            <h:outputText value="#{ConsolidateProfitLoss.userName}" id="stxtUser" styleClass="output"/>
                        </h:panelGroup>
                    </h:panelGrid> 
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row1" style="height:30px;" width="100%">
                        <h:outputLabel id="lblMsg" styleClass="error" style="color:red;" value="#{ConsolidateProfitLoss.message}"/>
                    </h:panelGrid>
                     <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Panel1" style="text-align:center;" styleClass="row2">
                        <h:outputLabel value="Branch" styleClass="label"/>
                        <h:selectOneListbox id="branchddl" size="1" value="#{ConsolidateProfitLoss.branch}" styleClass="ddlist">
                            <f:selectItems value="#{ConsolidateProfitLoss.branchList}" />
                             <a4j:support action="#{ConsolidateProfitLoss.checkExtCounter}" event="onblur" reRender="exLabel,exddl,lblMsg"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="exLabel" value="Ext Counter Enclude" styleClass="label" style="display:#{ConsolidateProfitLoss.displayExCtr}"/>
                        <h:selectOneListbox id="exddl" size="1" value="#{ConsolidateProfitLoss.exCounter}" styleClass="ddlist" style="display:#{ConsolidateProfitLoss.displayExCtr}">
                            <f:selectItems value="#{ConsolidateProfitLoss.exCounterList}" />
                        </h:selectOneListbox>
                        <h:outputLabel id="label4" value="Date : " styleClass="label">
                            <font class="required" style="color:red">*</font>
                        </h:outputLabel>
                        <h:inputText id="calDate" styleClass="input calDate" value="#{ConsolidateProfitLoss.calDate}" size="10">
                            <f:convertDateTime pattern="dd/MM/yyyy"/>
                            <a4j:support event="onblur" action="#{ConsolidateProfitLoss.onBlurOfDate}" reRender="repPanel" focus="ddOptionList"/>
                        </h:inputText>                        
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="repPanel" styleClass="row1" width="100%">
                        <h:outputLabel/>
                        <h:selectOneListbox id="ddOptionList" styleClass="ddlist" value="#{ConsolidateProfitLoss.selectOption}" style="display:#{ConsolidateProfitLoss.showPanel}" size="1">
                            <f:selectItems value="#{ConsolidateProfitLoss.selectOptionList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel/>                                                
                        <h:outputLabel/>                        
                    </h:panelGrid>
                    <h:panelGrid columns="1" styleClass="footer" style="width:100%;text-align:center;">  
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton action="#{ConsolidateProfitLoss.btnHtmlAction}" id="btnHtml"value="Print Report" reRender="lblMsg,calDate,repPanel"/>
                            <h:commandButton id="btnPDF"  value="PDF Download" actionListener="#{ConsolidateProfitLoss.viewPdfReport}"  styleClass="color: #044F93;text-decoration: none;"  ></h:commandButton>
                            <a4j:commandButton action="#{ConsolidateProfitLoss.btnExitAction}" id="btnExit" value="Exit" reRender="lblMsg,calDate,repPanel"/>
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