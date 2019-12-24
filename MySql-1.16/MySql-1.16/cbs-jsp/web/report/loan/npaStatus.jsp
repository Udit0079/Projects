<%-- 
    Document   : npaStatus
    Created on : Feb 13, 2013, 11:23:04 AM
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
            <title>Standard/Npa Status Report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{npaStatus.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Standard/Npa Status Report"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{npaStatus.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row2" style="height:30px;text-align:center;" width="100%">
                        <h:outputLabel id="lblMsg" styleClass="error" style="color:red;" value="#{npaStatus.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel11" columns="4" columnClasses="col2,col7,col2,col7" styleClass="row1" width="100%">
                        <h:outputText value="Branch Wise" styleClass="label"/>
                        <h:selectOneListbox id="branch" size="1" value="#{npaStatus.branchCode}" styleClass="ddlist">
                            <f:selectItems value="#{npaStatus.branchCodeList}" />
                        </h:selectOneListbox>  
                        <h:outputLabel id="lblReportType" styleClass="label" value="Report Type"/>
                        <h:selectOneListbox id="ddReportType" size="1" styleClass="ddlist" value="#{npaStatus.reportType}">
                            <f:selectItems value="#{npaStatus.reportTypeList}"/>
                            <a4j:support event="onblur" actionListener="#{npaStatus.reportTypeAction}" reRender="lblMsg,lblStatus,ddStatus,gridPanel3,gridPanel4,gridPanel5,gridPanel6" oncomplete="if(#{npaStatus.reportType == '0'}){#{rich:element('ddStatus')}.focus();}
                                         else if(#{npaStatus.reportType == '1'}){#{rich:element('ddAccountType')}.focus();}
                                         else if(#{npaStatus.reportType == '2'}){#{rich:element('ddForQuarter')}.focus();}
                                         setMask();"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel2" columns="4"  columnClasses="col2,col7,col2,col7"  width="100%" styleClass="row2">
                        <h:outputLabel id="lblStatus" styleClass="label" value="Status" style="display:#{npaStatus.labelStatus};"/>
                        <h:selectOneListbox id="ddStatus" size="1" styleClass="ddlist" value="#{npaStatus.status}" style="display:#{npaStatus.dropStatus};">
                            <f:selectItems value="#{npaStatus.statusList}"/>
                            <a4j:support event="onblur" actionListener="#{npaStatus.statusAction}" reRender="lblMsg,gridPanel6,ddAcType" oncomplete="setMask();" focus="ddAcType"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblAcType" styleClass="label" value="A/c Type"/>
                        <h:selectOneListbox id="ddAcType" size="1" styleClass="ddlist" value="#{npaStatus.acType}">
                            <f:selectItems value="#{npaStatus.acTypeList}"/>
                            <a4j:support event="onblur" oncomplete="setMask();" focus="txtFrOnDt"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel3" columns="4"  columnClasses="col2,col7,col2,col7"  width="100%" styleClass="row1" style="display:#{npaStatus.secondRowFirst};">
                        <h:outputLabel id="lblFrOnDt" styleClass="label" value="From Date"/>
                        <h:inputText id="txtFrOnDt" styleClass="input issueDt" value="#{npaStatus.frDt}" size="8">
                            <a4j:support event="onblur" actionListener="#{npaStatus.fromDateAction}" reRender="lblMsg,btnHtml,ddAcStatus"focus="txtAsOnDt" />
                        </h:inputText>
                        <h:outputLabel id="lblAsOnDt" styleClass="label" value="To Date"/>
                        <h:inputText id="txtAsOnDt" styleClass="input issueDt" value="#{npaStatus.toDt}" size="8">
                            <a4j:support event="onblur" actionListener="#{npaStatus.asOnDateAction}" reRender="lblMsg,btnHtml,ddAcStatus" oncomplete="if(#{npaStatus.status == '0'}){#{rich:element('btnHtml')}.focus();}
                                         else if(#{npaStatus.status == '1'}){#{rich:element('ddAcStatus')}.focus();}setMask();"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel4" columns="4"  columnClasses="col2,col7,col2,col7"  width="100%" styleClass="row2" style="display:#{npaStatus.secondRowSecond};">
                        <h:outputLabel id="lblAccountType" styleClass="label" value="A/c Type"/>
                        <h:selectOneListbox id="ddAccountType" size="1" styleClass="ddlist" value="#{npaStatus.npaReportAcType}">
                            <f:selectItems value="#{npaStatus.npaReportAcTypeList}"/>
                            <a4j:support event="onblur" oncomplete="setMask();" focus="txtTillDt"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblTillDt" styleClass="label" value="Till Date"/>
                        <h:inputText id="txtTillDt" styleClass="input issueDt" value="#{npaStatus.tillDt}" size="8">
                            <a4j:support event="onblur" actionListener="#{npaStatus.tillAction}" oncomplete="setMask();" focus="btnHtml"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel5" columns="4"  columnClasses="col2,col7,col2,col7"  width="100%" styleClass="row1" style="display:#{npaStatus.secondRowThird};">
                        <h:outputLabel id="lblForQuarter" styleClass="label" value="For Quarter"/>
                        <h:selectOneListbox id="ddForQuarter" size="1" styleClass="ddlist" value="#{npaStatus.forQuarter}">
                            <f:selectItems value="#{npaStatus.forQuarterList}"/>
                            <a4j:support event="onblur" oncomplete="setMask();" focus="ddYear"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblYear" styleClass="label" value="Year"/>
                        <h:selectOneListbox id="ddYear" size="1" styleClass="ddlist" value="#{npaStatus.year}">
                            <f:selectItems value="#{npaStatus.yearList}"/>
                            <a4j:support event="onblur" oncomplete="setMask();" focus="btnHtml"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel6" columns="4"  columnClasses="col2,col7,col2,col7"  width="100%" styleClass="row2" style="display:#{npaStatus.thirdRow};">
                        <h:outputLabel id="lblAcStatus" styleClass="label" value="A/c Status"/>
                        <h:selectOneListbox id="ddAcStatus" size="1" styleClass="ddlist" value="#{npaStatus.acStatus}">
                            <f:selectItems value="#{npaStatus.acStatusList}"/>
                            <a4j:support event="onblur" oncomplete="setMask();" focus="btnHtml"/>
                        </h:selectOneListbox>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton action="#{npaStatus.btnHtmlAction}" id="btnHtml" value="Print Report" reRender="lblMsg"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{npaStatus.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton action="#{npaStatus.btnRefreshAction}" id="btnRefresh" value="Refresh" reRender="mainPanel"/>
                            <a4j:commandButton action="#{npaStatus.btnExitAction}" id="btnExit" value="Exit" reRender="mainPanel"/>
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

