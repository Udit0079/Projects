<%-- 
    Document   : hrBasicSalaryStructure
    Created on : 29 Aug, 2017, 5:31:43 PM
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
            <title> Basic Salary Structure Master</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{hrBasicSalaryStructure.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Basic Salary Structure Master"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{hrBasicSalaryStructure.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row2" style="height:30px;text-align:center;" width="100%">
                        <h:outputLabel id="lblMsg" styleClass="error" style="color:red;" value="#{hrBasicSalaryStructure.message}"/>
                    </h:panelGrid>
                     <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="gridPanel5" styleClass="row1">
                        <h:outputLabel id="lblFromAmt" value="From Amount" styleClass="label"/>
                        <h:inputText id="txtFrAmt" value="#{hrBasicSalaryStructure.frAmt}" styleClass="input" maxlength="15" size="11" />
                        <h:outputLabel id="lblToAmt" value="To Amount" styleClass="label"/>
                        <h:inputText id="txtToAmt" value="#{hrBasicSalaryStructure.toAmt}" styleClass="input" maxlength="15" size="11"/>
                    </h:panelGrid> 
                   <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row2" style="width:120%;text-align:left;" styleClass="row2"> 
                        <h:outputLabel id="lblFrDt" styleClass="label" value="Effective Date"/>
                        <h:inputText id="frDate" size="10" styleClass="input issueDt" value="#{hrBasicSalaryStructure.effectivedt}">
                            <a4j:support event="onblur" oncomplete="setMask();"focus="btnSave"/>
                        </h:inputText>
                        <h:outputLabel id="ext"  styleClass="label" value=""/>
                        <h:outputLabel id="ext2"  styleClass="label" value="" />
                         </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton action="#{hrBasicSalaryStructure.btnSaveAction}" id="btnSave" value="Save" reRender="lblMsg,txtFrAmt,txtToAmt" oncomplete="setMask();"/>
                            <a4j:commandButton action="#{hrBasicSalaryStructure.btnRefreshAction}" id="btnRefresh" value="Refresh" reRender="mainPanel"oncomplete="setMask();"/>
                            <a4j:commandButton action="#{hrBasicSalaryStructure.btnExitAction}" id="btnExit" value="Exit" reRender="mainPanel"oncomplete="setMask();"/>
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

   