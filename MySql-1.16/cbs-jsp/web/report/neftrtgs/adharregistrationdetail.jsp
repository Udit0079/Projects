<%-- 
    Document   : adharregistrationdetail
    Created on : Mar 19, 2015, 3:12:10 PM
    Author     : Ajeet
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link rel="stylesheet" href="/cbs-jsp/resources/txn_style.css" type="text/css"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Adhar Registraion Detail</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".frDt").mask("99/99/9999");
                    jQuery(".toDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="kycCateg">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{AdharRegistraionDetail.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Adhar Registraion Detail  "/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{AdharRegistraionDetail.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row1" style="height:30px;text-align:center;" width="100%">
                        <h:outputLabel id="message" styleClass="error" style="color:red;" value="#{AdharRegistraionDetail.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel3" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" width="100%" styleClass="row2">
                        <h:outputLabel value="Branch" styleClass="label"/>
                        <h:selectOneListbox id="branchddl" size="1" value="#{AdharRegistraionDetail.branch}" styleClass="ddlist" style="width:70px;setMask();">
                            <f:selectItems value="#{AdharRegistraionDetail.branchList}" />
                        </h:selectOneListbox>
                        <h:outputText value="Aadhar Type" styleClass="label"/>
                        <h:selectOneListbox id="statusid" size="1" value="#{AdharRegistraionDetail.adhartype}" styleClass="ddlist" style="width:70px;setMask();">
                            <f:selectItems value="#{AdharRegistraionDetail.adharList}" /> 
                        </h:selectOneListbox> 
                        <h:outputText value="Filter Type" styleClass="label"/>
                        <h:selectOneListbox id="filterid" size="1" value="#{AdharRegistraionDetail.filter}" styleClass="ddlist" style="width:70px;setMask();">
                            <f:selectItems value="#{AdharRegistraionDetail.filterList}" /> 
                            <a4j:support action="#{AdharRegistraionDetail.hideDate}"  event="onchange" ajaxSingle="true" 
                                         reRender="gridPanel4" limitToList="true" />
                        </h:selectOneListbox> 
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel4" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" width="100%" styleClass="row1" style="display:#{AdharRegistraionDetail.displayDate};">
                        <h:outputLabel value="From Date" styleClass="label"/>
                        <h:inputText id="frDt" styleClass="input frDt" maxlength="10" value="#{AdharRegistraionDetail.fromdate}" style="width:70px;setMask();"/>
                        <h:outputLabel value="To Date" styleClass="label"/>
                        <h:inputText id="toDt" styleClass="input toDt" maxlength="10" value="#{AdharRegistraionDetail.todate}" style="width:70px;setMask();"/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnPrint" value="Print Report" action="#{AdharRegistraionDetail.viewReport}" reRender="message"/>
                            <a4j:commandButton action="#{AdharRegistraionDetail.btnRefreshAction}" id="btnRefresh" value="Refresh" reRender="mainPanel" oncomplete="setMask();"/>
                            <a4j:commandButton action="#{AdharRegistraionDetail.btnExitAction}" id="btnExit" value="Exit" reRender="mainPanel,message,gridPanel4" oncomplete="setMask();"/>
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