<%-- 
    Document   : mismatchXferScroll
    Created on : 21 Apr, 2018, 2:51:13 PM
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
            <title>Mismatch Transfer Scroll</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".frDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="form1"> 
                <h:panelGrid bgcolor="#fff" columns="1" id="a1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel value="Date : " styleClass="headerLabel"/>
                            <h:outputText value="#{MismatchXferScroll.todayDate}" id="stxtDate" styleClass="output"/>
                        </h:panelGroup>
                        <h:outputLabel value="Mismatch Transfer Scroll" styleClass="headerLabel"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel value="User : " styleClass="headerLabel"/>
                            <h:outputText value="#{MismatchXferScroll.userName}" id="stxtUser" styleClass="output"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row1" style="height:30px;text-align:center;" width="100%">
                        <h:outputLabel id="lblMsg" styleClass="error" style="color:red;" value="#{MismatchXferScroll.message}"/>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col1,col3,col3,col3,col3,col3" columns="6" style="height:30px;text-align:center;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblBranchWise" styleClass="label" value="Branch : "><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddBranch" styleClass="ddlist" size="1" value="#{MismatchXferScroll.branchOption}">
                            <f:selectItems value="#{MismatchXferScroll.branchOptionList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel value="Date :" styleClass="label"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="frDt" styleClass="input frDt" maxlength="10" value="#{MismatchXferScroll.date}" style="width:70px;setMask();"/>

                        <h:outputLabel id="lblAuth" styleClass="label" value="Authorize Type: "><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddAuth" styleClass="ddlist" size="1" value="#{MismatchXferScroll.auth}">
                            <f:selectItems value="#{MismatchXferScroll.authList}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>

                    <h:panelGrid columns="1" id="gridPanel9" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="a100" layout="block">
                            <a4j:commandButton action="#{MismatchXferScroll.btnHtmlAction}" id="btnHtml" value="Print Report" reRender="lblMsg,txtDate"  oncomplete="setMask()"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{MismatchXferScroll.btnPdfAction}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton action="#{MismatchXferScroll.refreshForm}"  id="btnRefresh" value="Refresh" reRender="a1"  oncomplete="setMask()"/>
                            <a4j:commandButton action="#{MismatchXferScroll.btnExitAction}" id="btnExit" value="Exit" reRender=""  oncomplete="setMask()"/>
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
