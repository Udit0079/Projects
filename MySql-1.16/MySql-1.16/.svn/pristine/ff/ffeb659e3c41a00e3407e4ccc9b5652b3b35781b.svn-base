<%-- 
    Document   : nonaadhaarAccountvalidation
    Created on : 12 Jan, 2018, 2:40:58 PM
    Author     : root
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Non Aadhar Account Validation Detail</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".issueDt").mask("99/99/9999");
                }

            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{ThresoldTransactionInfo.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Non Aadhaar Account Validation Detail"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{NonAadhaarAccountValidation.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row2" style="height:30px;text-align:center;" width="100%">
                        <h:outputLabel id="lblMsg" styleClass="error" style="color:red;" value="#{NonAadhaarAccountValidation.message}"/>
                    </h:panelGrid>
                    
                    <h:panelGrid id="gridPanel5" columns="6"  columnClasses="col1,col4,col1,col4,col1,col4"  width="100%" styleClass="row2">
                        <h:outputLabel id="lblFrDt" styleClass="label" value="From Date"/>
                        <h:inputText id="frDate" size="10" styleClass="input issueDt" value="#{NonAadhaarAccountValidation.frdt}"  >
                            <%--a4j:support event="onblur" oncomplete="setMask();"/--%>
                        </h:inputText>
                        <h:outputLabel id="lblToDt" styleClass="label" value="To Date"/>
                        <h:inputText id="toDate" size="10" styleClass="input issueDt" value="#{NonAadhaarAccountValidation.todt}" >
                            <%--a4j:support event="onblur" oncomplete="setMask();"/--%>
                        </h:inputText>
                        
                        <h:outputLabel id="lblOpDt" styleClass="label" value="Report Option :"></h:outputLabel>
                        <h:selectOneListbox id="ddOpDt" styleClass="ddlist" size="1" style="width:100px;" value="#{NonAadhaarAccountValidation.status}">
                            <f:selectItems value="#{NonAadhaarAccountValidation.statusList}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>     
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton action="#{NonAadhaarAccountValidation.btnHtmlAction}" id="btnHtml" value="Print Report" reRender="lblMsg,mainPanel"/>
                             <h:commandButton id="btnPDF"  value="PDF Download" actionListener="#{NonAadhaarAccountValidation.viewPdfReport}"  styleClass="color: #044F93;text-decoration: none;" /> 
                            <a4j:commandButton action="#{NonAadhaarAccountValidation.btnRefreshAction}" id="btnRefresh" value="Refresh" oncomplete="setMask();" reRender="lblMsg,mainPanel"/>
                            <a4j:commandButton action="#{NonAadhaarAccountValidation.btnExitAction}" id="btnExit" value="Exit" reRender="mainPanel"/>
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
