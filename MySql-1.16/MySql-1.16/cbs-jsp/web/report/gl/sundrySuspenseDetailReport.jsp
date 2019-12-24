<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Sundry Suspense Detail Report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />

            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                var row;
                function setMask() {
                    jQuery(".fromdate").mask("99/99/9999");
                    jQuery(".toDate").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel0" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date:"/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{SundrySuspenseDetailReport.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblName" styleClass="headerLabel" value="Sundry Suspense Detail Report"/>
                        <h:panelGroup id="a4" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User:"/>
                            <h:outputText styleClass="output" id="stxtUser" value="#{SundrySuspenseDetailReport.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid> 
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{SundrySuspenseDetailReport.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="mainPanel1" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" width="100%" styleClass="row2">                 
                        <h:outputText value="Branch Wise" styleClass="label"/>
                        <h:selectOneListbox id="branch" size="1" value="#{SundrySuspenseDetailReport.branchCode}" styleClass="ddlist">
                            <f:selectItems value="#{SundrySuspenseDetailReport.branchCodeList}" />
                        </h:selectOneListbox>  
                        <h:outputLabel id="reportType" styleClass="label" value="Report Type"/>
                        <h:selectOneListbox id="reportTypeList"  style="width:150px;" styleClass="ddlist" size="1" value="#{SundrySuspenseDetailReport.reportType}"> 
                            <f:selectItems value="#{SundrySuspenseDetailReport.reportTypeList}"/>
                            <a4j:support actionListener="#{SundrySuspenseDetailReport.glHeadDetail}" event="onblur"reRender="glHead,glList"> </a4j:support>
                        </h:selectOneListbox> 
                        <h:outputLabel id="glHead" styleClass="label" value="GlHead"/>
                        <h:selectOneListbox id="glList"  style="width:150px;" styleClass="ddlist" size="1" value="#{SundrySuspenseDetailReport.glHead}"> 
                            <f:selectItems value="#{SundrySuspenseDetailReport.glHeadList}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="mainpanel2" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" width="100%" styleClass="row1">
                        <h:outputLabel id="lblfromDate" styleClass="label" value="From Date"/>
                        <h:inputText id="fromdate" styleClass="input fromdate" style="width:70px;setMask();" value="#{SundrySuspenseDetailReport.fromDate}">
                            <f:convertDateTime pattern="dd/MM/yyyy"/>
                        </h:inputText>
                        <h:outputLabel id="lblTODate" styleClass="label" value="To Date" />
                        <h:inputText id="toDate" styleClass="input toDate"  style="width:70px;setMask();" value="#{SundrySuspenseDetailReport.toDate}">
                            <f:convertDateTime pattern="dd/MM/yyyy" />
                        </h:inputText>
                        <h:outputLabel id="typeid" styleClass="label" value="Report Option"/>
                        <h:selectOneListbox id="typelList"  style="width:150px;" styleClass="ddlist" size="1" value="#{SundrySuspenseDetailReport.reportOption}"> 
                            <f:selectItems value="#{SundrySuspenseDetailReport.reportOptionList}"/>
                            <a4j:support actionListener="#{SundrySuspenseDetailReport.onDisable}" event="onblur"reRender="partialidList"> </a4j:support>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="mainpanel3" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" width="100%" styleClass="row1">
                        <h:outputLabel id="partialid" styleClass="label" value="Partial Option"/>
                        <h:selectOneListbox id="partialidList"  style="width:150px;" styleClass="ddlist" size="1" value="#{SundrySuspenseDetailReport.partialReconcilType}" disabled="#{SundrySuspenseDetailReport.disablePartial}"> 
                            <f:selectItems value="#{SundrySuspenseDetailReport.partialReconcilTypeList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columns="5" id="gridpanel6" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="a1" layout="block">
                            <a4j:commandButton id="btnReport" value="Print Report" action="#{SundrySuspenseDetailReport.printReport()}" 
                                               reRender="errorMsg"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{SundrySuspenseDetailReport.printPDF}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{SundrySuspenseDetailReport.refresh}" reRender="a1,reportTypeList,errorMsg,fromdate,toDate,partialidList" oncomplete="setMask()"/>
                            <a4j:commandButton id="exit" value="Exit" action="#{SundrySuspenseDetailReport.exitFrm()}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:status  onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>

