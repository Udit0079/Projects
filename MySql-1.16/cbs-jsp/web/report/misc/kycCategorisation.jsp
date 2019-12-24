<%-- 
    Document   : kycCategorisation
    Created on : Nov 26, 2013, 5:24:10 PM
    Author     : Athar Reza
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
            <title>Risk Category Detail Report</title>
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
            <a4j:form id="kycCateg">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{KYCcategorisation.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Risk Category Detail Report"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{KYCcategorisation.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row2" style="height:30px;text-align:center;" width="100%">
                        <h:outputLabel id="message" styleClass="error" style="color:red;" value="#{KYCcategorisation.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel2" columns="8"  columnClasses="col15,col15,col15,col15,col15,col15,col15,col1"  width="100%" styleClass="row1">
                        <h:outputText value="Branch Wise :" styleClass="label"/>
                        <h:selectOneListbox id="branch" size="1" value="#{KYCcategorisation.branchCode}" styleClass="ddlist">
                            <f:selectItems value="#{KYCcategorisation.branchCodeList}" /> 
                        </h:selectOneListbox>
                        <h:outputText value="A/c Nature :"styleClass="label" />
                        <h:selectOneListbox id="ddacType" size="1" value="#{KYCcategorisation.acnoNature}" styleClass="ddlist" style="width:70px" >
                            <f:selectItems value="#{KYCcategorisation.acnoNatureList}"/>
                            <a4j:support  action="#{KYCcategorisation.getAcTypeByAcNature}" event="onblur"reRender="acctid" oncomplete="setMask();"/>
                        </h:selectOneListbox>    
                        <h:outputText value="Account Type :" styleClass="label"/>
                        <h:selectOneListbox id="acctid" size="1" value="#{KYCcategorisation.acctType}" styleClass="ddlist" style="width:70px;setMask();">
                            <f:selectItems value="#{KYCcategorisation.acctTypeList}" /> 
                        </h:selectOneListbox> 
                        <h:outputLabel value="As On Date :" styleClass="label"/>
                        <h:inputText id="toDt" styleClass="input toDt" maxlength="10" value="#{KYCcategorisation.toDt}" style="width:70px;setMask();"/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <h:commandButton id="btnPDF"  value="PDF Download" actionListener="#{KYCcategorisation.viewPdfReport}"  styleClass="color: #044F93;text-decoration: none;"  ></h:commandButton>
                            <h:commandButton id="btnPrint" value="Excel Download" actionListener="#{KYCcategorisation.excelPrint}"/>
                            <a4j:commandButton action="#{KYCcategorisation.btnRefreshAction}" id="btnRefresh" value="Refresh" reRender="mainPanel" oncomplete="setMask();"/>
                            <a4j:commandButton action="#{KYCcategorisation.btnExitAction}" id="btnExit" value="Exit" reRender="mainPanel" oncomplete="setMask();"/>
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
