<%-- 
    Document   : biometricVerification
    Created on : 21 Dec, 2016, 6:04:58 PM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <title>Biometric Verification</title>

        </head>
        <body>
            <a4j:form id="form1" prependId="false">
                <h:panelGrid id="mainPanel" columns="1" style="text-align:center;height:300px" width="100%">
                    <h:outputLabel value="#{BioMetricVefirication.textMsg}" styleClass="headerLabel" style="font-size:14px;color:blue"/>
                </h:panelGrid>
                <a4j:jsFunction name="fingerVerify" action="#{BioMetricVefirication.thumbVerification}" />
            </a4j:form>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel> 
        </body>
        
        <script language="javascript" type="text/javascript">
            jQuery(document).ready(function() {
                fingerVerify();
            });
        </script>
    </html>
</f:view>