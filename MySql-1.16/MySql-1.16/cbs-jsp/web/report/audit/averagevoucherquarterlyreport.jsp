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
            <title>Average Voucher Quarterly Report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery){
                    setMask();
                });
                var row;
                function setMask(){
                    jQuery(".calInstDate").mask("39/19/9999");
                }
            </script>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="averagevoucherquarterlyreport">
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>

                <h:panelGrid id="PanelGridMain" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="HeaderPanel" columns="3"  style="text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{averageVoucherQuarterly.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="Average Voucher Quarterly Report"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUsername" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="otUsername" styleClass="output" value="#{averageVoucherQuarterly.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>  
                    <h:panelGrid  id="errPanel" style="text-align:center" styleClass="row1">
                        <h:outputText id="errmsg" value="#{averageVoucherQuarterly.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel4" style="text-align:center;" styleClass="row2">
                        <h:outputLabel/>
                        <h:outputLabel  value="Quarter End Month" styleClass="label"/>
                        <h:selectOneListbox id="ddQuarter" styleClass="ddlist" value="#{averageVoucherQuarterly.quarter}" size="1">
                            <f:selectItems value="#{averageVoucherQuarterly.querterList}"/>                            
                        </h:selectOneListbox>
                        <h:outputLabel  value="Date" styleClass="label"/>
                        <h:panelGroup id="groupPanelapptDt" layout="block">
                            <h:inputText id="calToDate" styleClass="input calInstDate"   style="width:70px;" maxlength="10" value="#{averageVoucherQuarterly.calDate}" >
                                <f:convertDateTime pattern="dd/MM/yyyy" />
                            </h:inputText>
                            <h:outputLabel id="lblapptDT" styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                        </h:panelGroup>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="FooterPanel" bgcolor="#fff" style="text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnPrint" action="#{averageVoucherQuarterly.printAction}" type="submit" value="Print Report" oncomplete="setMask();" reRender="errmsg,groupPanelapptDt"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{averageVoucherQuarterly.btnPdfAction}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="btnExit" action="#{averageVoucherQuarterly.exitAction}" value="Exit" reRender="errmsg"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>