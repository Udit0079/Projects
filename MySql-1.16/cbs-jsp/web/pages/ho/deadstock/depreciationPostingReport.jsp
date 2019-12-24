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
            <title> Depreciation Posting Report</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".calFromDate").mask("99/99/9999");
                    jQuery(".calToDate").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="depreciationpostingreport">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="grpPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{DepreciationPostingReport.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Depreciation Posting Report"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{DepreciationPostingReport.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="msgGrid" columns="1" width="100%" styleClass="row2" style="width:100%;text-align:center;">
                        <h:outputText id="msg" styleClass="error" value="#{DepreciationPostingReport.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="inputPanel" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" style="width:100%;text-align:left;">
                        <h:outputLabel id="lblItemGroup" styleClass="label" value="Item Group"/>
                        <h:selectOneListbox id="ddItemGroup" styleClass="ddlist"  size="1" style="width:120px" value="#{DepreciationPostingReport.itemGroup}">
                            <f:selectItems value="#{DepreciationPostingReport.itemGroupList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblitemOption" styleClass="label" value="Item Option" />
                        <h:selectOneListbox id="dditemListOption" size="1" styleClass="ddlist" value="#{DepreciationPostingReport.itemOption}" >
                            <f:selectItems value="#{DepreciationPostingReport.itemOptionList}" />
                            <a4j:support event="onblur" action="#{DepreciationPostingReport.itemOptionAction}" reRender="dditemList,ddItemdistinctNo,msg"  />
                        </h:selectOneListbox> 
                        <h:outputLabel id="lblitemcode" styleClass="label" value=" Select Item" />
                        <h:selectOneListbox id="dditemList" size="1" styleClass="ddlist" style = "width:120px" value="#{DepreciationPostingReport.itemCodeString}" disabled= "#{DepreciationPostingReport.disableItemDisNo}">
                            <f:selectItems value="#{DepreciationPostingReport.itemList}" />
                            <a4j:support event="onblur" action="#{DepreciationPostingReport.distinctNo}" reRender="ddItemdistinctNo"  />
                        </h:selectOneListbox> 
                    </h:panelGrid>
                    <h:panelGrid id="inputPanel1" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" style="width:100%;text-align:left;">
                        <h:outputLabel id="lblItemdistinctNo" styleClass="label" value="Item Distinct No."/>
                        <h:selectOneListbox id="ddItemdistinctNo" styleClass="ddlist"  size="1" style="width:120px" value="#{DepreciationPostingReport.itemDistinctNo}" disabled= "#{DepreciationPostingReport.disableItemDisNo}">
                            <f:selectItems value="#{DepreciationPostingReport.itemDistinctNoList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblFromDate" value="From Date" styleClass="label"/>
                        <h:inputText id="calFromDate" size="10" styleClass="input calFromDate" value="#{DepreciationPostingReport.calFromDate}">
                            <f:convertDateTime pattern="dd/MM/yyyy" />
                        </h:inputText>
                        <h:outputLabel id="lbltoDate"  value="To Date"  styleClass="label"/>
                        <h:inputText id="calToDate" size="10" styleClass="input calFromDate"  value="#{DepreciationPostingReport.caltoDate}">
                            <f:convertDateTime pattern="dd/MM/yyyy" />
                        </h:inputText>
                        <h:outputLabel/>
                        <h:outputLabel/> 
                    </h:panelGrid>
                    <h:panelGrid columns="1" styleClass="footer" style="width:100%;text-align:center;">  
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton action="#{DepreciationPostingReport.btnHtmlAction}" id="btnHtml"
                                               value="Print Report" reRender="lblMsg,calDate"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{DepreciationPostingReport.btnPdfAction}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh"  action="#{DepreciationPostingReport.refreshData}"  reRender="mainPanel,msg" >
                            </a4j:commandButton>
                            <a4j:commandButton action="#{DepreciationPostingReport.exitAction}" id="btnExit" value="Exit" reRender="lblMsg,calDate"/>
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

