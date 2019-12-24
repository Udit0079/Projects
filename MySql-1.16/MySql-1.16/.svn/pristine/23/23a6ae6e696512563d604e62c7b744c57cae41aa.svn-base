<%-- 
    Document   : demandrecoverydetail
    Created on : 23 Aug, 2019, 12:47:30 PM
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Demand Recovery With Detail</title>
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
            <h:form id="demandrecoverywithdetail">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{DemandRecoveryDetail.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Demand Recovery Details Report"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{DemandRecoveryDetail.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{DemandRecoveryDetail.errMessage}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel1" columns="6" columnClasses="col3,col3,col3,col3,col3,col3"  styleClass="row2" width="100%">
                        <h:outputText value="Branch Wise" styleClass="label"/>
                        <h:selectOneListbox id="branch" size="1" value="#{DemandRecoveryDetail.branchcode}" styleClass="ddlist">
                            <f:selectItems value="#{DemandRecoveryDetail.branchcodeList}" />
                        </h:selectOneListbox>  
                        <h:outputText value="A/c Nature" styleClass="label"/>
                        <h:selectOneListbox id="ddlAcNatureHeader" value="#{DemandRecoveryDetail.acnature}" size="1" styleClass="ddlist" style="width:100px">
                            <f:selectItems value="#{DemandRecoveryDetail.acnatureList}"/>
                            <a4j:support event="onblur" action="#{DemandRecoveryDetail.blurAcctNature}"  reRender="ddlAccTypeHeader,ddlAccTypeHeader" />
                        </h:selectOneListbox> 
                        <h:outputText value="A/c. Type" styleClass="label"/>
                        <h:selectOneListbox id="ddlAccTypeHeader" value="#{DemandRecoveryDetail.actype}" size="1" styleClass="ddlist" style="width:100px">
                            <f:selectItems value="#{DemandRecoveryDetail.actypeList}"/>                       
                        </h:selectOneListbox>  
                    </h:panelGrid>
                    <h:panelGrid id="panel2" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputLabel id="lblMonth" styleClass="label"  value="Month"><font class="required" color="red">*</font></h:outputLabel>
                            <h:panelGroup>
                                <h:selectOneListbox id="ddMonth" styleClass="ddlist" size="1" style="width: 70px" value="#{DemandRecoveryDetail.month}">
                                    <f:selectItems value="#{DemandRecoveryDetail.monthList}"/>
                                </h:selectOneListbox>
                                <h:inputText id="txtYear" styleClass="input" value="#{DemandRecoveryDetail.year}" maxlength="4" size="5"/>
                            </h:panelGroup>  
                            <h:outputLabel value="With Recovery" styleClass="label"/>
                            <h:selectBooleanCheckbox value = "Security" id = "checkMe" style="width:10px" >
                            <a4j:support event="onclick" action="#{DemandRecoveryDetail.onCheckRecovery()}" reRender="Security"></a4j:support>

                        </h:selectBooleanCheckbox>
                        <h:outputLabel></h:outputLabel>
                        <h:outputLabel></h:outputLabel>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                        <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">                             
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{DemandRecoveryDetail.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{DemandRecoveryDetail.refreshPage}" reRender="a1" oncomplete="setMask()"/>
                            <a4j:commandButton  id="exit" value="Exit" action="case1"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
                    <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <f:facet name="header">
                            <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                        </f:facet>
                    </rich:modalPanel>                
                </h:panelGrid>
            </h:form>
        </body>
    </html>
</f:view>
