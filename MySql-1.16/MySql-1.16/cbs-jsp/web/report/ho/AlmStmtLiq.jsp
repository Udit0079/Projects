<%-- 
    Document   : AlmStmtLiq
    Created on : Jul 18, 2014, 11:16:10 AM
    Author     : sipl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link rel="stylesheet" href="/cbs-jsp/resources/txn_style.css" type="text/css"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Statement of Short Term Dynamic Liquidity</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery){
                    setMask();
                });                
            </script>
        </head>
        <body class="body">
            <h:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{AlmStmtLiq.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Statement of Short Term Dynamic Liquidity"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>  
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{AlmStmtLiq.userName}"/>   
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="panel" style="width:100%;" columns="4">
                        <h:outputLabel style="font-weight:bold;" value="Description"/>
                        <h:outputLabel style="font-weight:bold;" value="1 To 14 Days"/>
                        <h:outputLabel style="font-weight:bold;" value="15 To 28 Days"/>
                        <h:outputLabel style="font-weight:bold;" value="29 To 90 Days"/>
                        <c:forEach var="listMenu" items="#{AlmStmtLiq.selectStatusList}">
                            <h:outputText value="#{listMenu.description}"/>                            
                            <h:inputText value="#{listMenu.fBkt}"/>
                            <h:inputText value="#{listMenu.sBkt}"/>
                            <h:inputText value="#{listMenu.tBkt}"/>                            
                        </c:forEach>                        
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <h:commandButton id="btnDownload" value="PDF Download" action="#{AlmStmtLiq.printPDF}" styleClass="color:#044F93;text-decoration:none;">
                                <a4j:support reRender="errorGrid"/>
                            </h:commandButton>
                            <a4j:commandButton action="#{AlmStmtLiq.printAction}" id="btnPrint" value="Print Report" reRender="mainPanel"/>                            
                            <a4j:commandButton action="#{AlmStmtLiq.exitAction}" id="btnExit" value="Exit" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </h:form>
        </body>
    </html>
</f:view>