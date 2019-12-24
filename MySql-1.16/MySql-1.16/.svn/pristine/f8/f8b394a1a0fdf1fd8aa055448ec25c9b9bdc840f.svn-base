<%-- 
    Document   : PopUpReportShare
    Created on : Sep 1, 2011, 6:05:46 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title><h:outputText value="#{PopUpReportShare.reportName}"/></title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript"></script>
        </head>
        <body>
            <a4j:keepAlive beanName="PopUpReport"/>
            <a4j:form id="PopUpReport">
                <h:panelGrid bgcolor="#fff" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header" width="100%">
                        <h:panelGroup id="groupPanel" layout="block" >
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date:" />
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{PopUpReportShare.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblincident" styleClass="headerLabel"  value="#{PopUpReportShare.reportName}"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lbluser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{PopUpReportShare.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGroup layout="block" style="width:100%;height:450px;overflow:auto;">
                        <%if (session.getAttribute("HTMLReport") != null) {%>
                        <iframe src="popUpRepFrame.jsp" frameborder="0" width="100%" height="430px"></iframe>
                        <%}%>
                    </h:panelGroup>
                    <h:panelGrid columns="5" id="gridpanel6" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="a1" layout="block" >
                            <a4j:commandButton id="btnReport" value="Print" action="#{PopUpReportShare.printReport}"/>
                            <a4j:commandButton id="btnExit" value="Exit" onclick="window.close();" action="#{PopUpReportShare.exitAction}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
            </a4j:form>
        </body>
    </html>
</f:view>

