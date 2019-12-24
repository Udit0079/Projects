<%-- 
    Document   : recoveryDetail
    Created on : Dec 2, 2014, 4:36:04 PM
    Author     : Admin
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
            <title>Loan Slip</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
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
                            <h:outputText value="#{RecoveryDetail.todayDate}" id="stxtDate" styleClass="output"/>
                        </h:panelGroup>
                        <h:outputLabel value="Loan Slip" styleClass="headerLabel"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel value="User : " styleClass="headerLabel"/>
                            <h:outputText value="#{RecoveryDetail.userName}" id="stxtUser" styleClass="output"/>
                        </h:panelGroup>
                    </h:panelGrid> 
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row1" style="height:30px;text-align:center" width="100%">
                        <h:outputLabel id="lblMsg" styleClass="error" style="color:red;" value="#{RecoveryDetail.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel1" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%">
                        <h:outputText id="lblrepType" value="Report Type:" styleClass="label"/>
                        <h:selectOneListbox id="ddrepType111" value="#{RecoveryDetail.reportType}" styleClass="ddlist"  style="width:110px" size="1">
                            <f:selectItems id="selectRepTypeList111" value="#{RecoveryDetail.reportTypeList}" />
                            <a4j:support event="onblur" action="#{RecoveryDetail.reportAction}" reRender="cstId,txtacno,lblacType,ddacType111,selectRepTypeList11"></a4j:support>
                        </h:selectOneListbox> 

                        <h:outputText id="cstId"value="#{RecoveryDetail.lableButton}" styleClass="label"/>
                        <h:panelGroup id="areaId">
                            <h:inputText id="txtacno" value="#{RecoveryDetail.custId}" styleClass="input" size="10" maxlength="12" style="display:#{RecoveryDetail.disPlayPanalCustId}"> 
                                 <a4j:support event="onblur" action="#{RecoveryDetail.onBlurFolio}" reRender="stxtFolioacnoAcNo,lblMsg"></a4j:support>
                            </h:inputText>
                            <h:selectOneListbox id="ddacType111" value="#{RecoveryDetail.area}" styleClass="ddlist"  style="width:110px;display:#{RecoveryDetail.disPlayPanalArea}" size="1">
                                <f:selectItems id="selectRepTypeList11" value="#{RecoveryDetail.areaList}" />
                                 <a4j:support event="onblur" action="#{RecoveryDetail.onBlurFolio}" reRender="stxtFolioacnoAcNo,lblMsg"></a4j:support>
                            </h:selectOneListbox> 
                             <h:outputLabel id="stxtFolioacnoAcNo" styleClass="label" value="#{RecoveryDetail.folioNo}"style="color:green"></h:outputLabel>
                        </h:panelGroup>

                        <%--h:outputLabel id="label4" value="Date : " styleClass="label"><font class="required" style="color:red">*</font></h:outputLabel>
                        <h:inputText id="calDate" styleClass="input calDate" value="#{RecoveryDetail.calDate}" size="8">
                            <f:convertDateTime pattern="dd/MM/yyyy"/>
                        </h:inputText--%>
                        
                          <h:outputLabel id="lblMonth" styleClass="label"  value="Month"><font class="required" color="red">*</font></h:outputLabel>
                        <h:panelGroup>
                            <h:selectOneListbox id="ddMonth" styleClass="ddlist" size="1" style="width: 70px" value="#{RecoveryDetail.month}">
                                <f:selectItems value="#{RecoveryDetail.monthList}"/>
                            </h:selectOneListbox>
                            <h:inputText id="txtYear" styleClass="input" value="#{RecoveryDetail.year}" maxlength="4" size="5"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columns="1" styleClass="footer" style="width:100%;text-align:center;">  
                        <a4j:region>
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <%--a4j:commandButton action="#{RecoveryDetail.btnHtmlAction}" id="btnHtml"value="Print Report" reRender="a1,lblMsg,calDate"/--%>
                             <h:commandButton id="btnDownload"  value="PDF Download" action="#{RecoveryDetail.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{RecoveryDetail.refreshAction}" reRender="lblMsg,a1,panel1"/>      
                            <a4j:commandButton action="#{RecoveryDetail.btnExitAction}" id="btnExit" value="Exit"/>
                        </h:panelGroup>
                        </a4j:region>
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