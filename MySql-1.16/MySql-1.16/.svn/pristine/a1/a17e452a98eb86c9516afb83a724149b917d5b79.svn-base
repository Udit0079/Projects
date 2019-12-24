<%-- 
    Document   : HDFCInwardNEFTReturn
    Created on : 2 Jan, 2017, 3:52:12 PM
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
        <link rel="stylesheet" href="/cbs-jsp/resources/txn_style.css" type="text/css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="Pragma" content="no-cache"/>
        <meta http-equiv="Cache-Control" content="no-cache"/>
        <title>HDFC INWARD NEFT RETURN</title>
        <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
        <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
        <script type="text/javascript">
        jQuery(function(jQuery) {
            setMask();
        });
        function setMask() {
            jQuery(".toDt").mask("99/99/9999");
        }
        </script>
    </head>
    <body>
        <a4j:form id="hdfcInwdNeft">
            <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{HDFCInwardNEFTReturn.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="HDFC IW RETURN Report"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{HDFCInwardNEFTReturn.userName}"/>
                        </h:panelGroup>
                </h:panelGrid>
                <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row2" style="height:30px;text-align:center;" width="100%">
                        <h:outputLabel id="message" styleClass="error" style="color:red;" value="#{HDFCInwardNEFTReturn.message}"/>
                    </h:panelGrid>
                <h:panelGrid columnClasses="col1,col6" columns="8" id="gridPanel2" styleClass="row1" style="height:30px;text-align:left;" width="100%">
                        <h:outputLabel id="inrDt" styleClass="label" value="Date">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:panelGroup styleClass="label">
                                <h:inputText id="toDt"  styleClass="input toDt" maxlength="10" style="setMask();width:70px;"  value="#{HDFCInwardNEFTReturn.toDt}"/>
                        </h:panelGroup>
                           
                </h:panelGrid>  
                <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <h:commandButton id="btnPrint" value="Excel Download" actionListener="#{HDFCInwardNEFTReturn.excelPrint}"/>
                            <a4j:commandButton action="#{HDFCInwardNEFTReturn.btnRefreshAction}" id="btnRefresh" value="Refresh" reRender="mainPanel" oncomplete="setMask();"/>
                            <a4j:commandButton action="#{HDFCInwardNEFTReturn.btnExitAction}" id="btnExit" value="Exit" reRender="mainPanel" oncomplete="setMask();"/>
                        </h:panelGroup>
                </h:panelGrid>                
                
            </h:panelGrid>
        
        </a4j:form>
    </body>
</html>
</f:view>
