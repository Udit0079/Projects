<%-- 
    Document   : misSheet
    Created on : Feb 13, 2019, 4:00:57 PM
    Author     : SAMY
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <title>MIS REPORT</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".calFromDate").mask("99/99/9999");
                    jQuery(".calToDate").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="Crr">
                <h:panelGrid id="PanelGridMain" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="HeaderPanel" columns="3"  style="text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{MisSheet.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="MIS REPORT"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUsername" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="otUsername" styleClass="output" value="#{MisSheet.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid  id="errPanel" style="text-align:center" styleClass="row1">
                        <h:outputText id="errmsg" value="#{MisSheet.message}" styleClass="error"/>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel4" style="text-align:center;" styleClass="row1">
                        <h:outputLabel id="lbRepName" styleClass="label"  value="Report Name"/>
                        <h:selectOneListbox id="ddRepName" styleClass="ddlist" size="1" style="width: 100px" value="#{MisSheet.repName}" >
                            <f:selectItems value="#{MisSheet.repNameList}"/>                            
                        </h:selectOneListbox>
                        <h:outputLabel id="lbRepOpt" styleClass="label"  value="Report Option"/>
                        <h:selectOneListbox id="ddRepOpt" styleClass="ddlist" size="1" style="width: 100px" value="#{MisSheet.repOpt}" >
                            <f:selectItems value="#{MisSheet.repOptionList}"/>                            
                        </h:selectOneListbox>
                        <h:outputLabel  value="Till Date" styleClass="label"/>
                        <h:inputText id="calToDate" size="10" styleClass="input calFromDate" value="#{MisSheet.toDate}" >
                            <a4j:support event="onblur" oncomplete="setMask();"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid id="FooterPanel" bgcolor="#fff" style="text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{MisSheet.viewPdf}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="btnRefresh" action="#{MisSheet.refresh}" value="Refresh" reRender="errmsg,ddtType,ddRepOpt"/>
                            <a4j:commandButton id="btnExit" action="#{MisSheet.exit}" value="Exit" reRender="errmsg"/>
                        </h:panelGroup>
                    </h:panelGrid>

                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
