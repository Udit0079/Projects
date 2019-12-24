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
            <title>Branch Wise Account Statement</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".frDt").mask("99/99/9999");
                    jQuery(".toDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="BranchWiseSmt">
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/> 
                    </f:facet>
                </rich:modalPanel>
                <h:panelGrid id="PanelGridMain" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="HeaderPanel" columns="3"  style="text-align:center;" styleClass="header" width="100%">
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{BranchWiseSmt.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="Branch Wise Account Statement"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUsername" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="otUsername" styleClass="output" value="#{BranchWiseSmt.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>    
                    <h:panelGrid  id="errPanel" style="text-align:center" styleClass="row1">
                        <h:outputText id="errmsg" value="#{BranchWiseSmt.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel4"  styleClass="row2">
                        <h:outputText id="lblacType" value="GL Head" styleClass="label"/>
                        <h:panelGroup layout="block">
                            <h:inputText id="txtAccountNo" styleClass="input" maxlength="#{BranchWiseSmt.acNoMaxLen}" value="#{BranchWiseSmt.codeBr}"  style="width:90px"> 
                                <a4j:support action="#{BranchWiseSmt.validateGlHead}" event="onblur" reRender="txtAccountNo,errmsg,txtAccNo,branch" focus="branch"/>
                            </h:inputText>
                            <h:outputText id="txtAccNo" styleClass="output" value="#{BranchWiseSmt.acNo}" style="color:blue;"/> 
                        </h:panelGroup>
                         <h:outputText value="Advice Branch" styleClass="label"/>
                         <h:selectOneListbox id="branch" size="1" value="#{BranchWiseSmt.adviceBrCode}" styleClass="ddlist" disabled="#{BranchWiseSmt.disabled}">
                            <f:selectItems value="#{BranchWiseSmt.adviceBrCodeList}" />
                        </h:selectOneListbox>
                        <h:outputLabel id="lblOrderBy" styleClass="label" value="Order By"/>
                        <h:selectOneListbox id="ddOrderBy" style="width:120px" styleClass="ddlist" value="#{BranchWiseSmt.orderBy}" size="1">
                            <f:selectItems value="#{BranchWiseSmt.orderByList}"/>
                            <a4j:support event="onblur" oncomplete="setMask();" focus="ddtransaction"/>
                        </h:selectOneListbox>
                    </h:panelGrid>       
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel5"  styleClass="row1">  
                         <h:outputLabel id="lbltransaction" styleClass="label" value="Transaction"/>
                        <h:selectOneListbox id="ddtransaction" style="width:90px" styleClass="ddlist" value="#{BranchWiseSmt.tranType}" size="1">
                            <f:selectItems value="#{BranchWiseSmt.tranTypeList}"/>
                            <%--a4j:support event="onblur" oncomplete="setMask();" focus="frDt"/--%>
                        </h:selectOneListbox>   
                        <h:outputLabel id="lblFromDate" value="From Date" styleClass="label"/>
                        <h:inputText id="frDt" styleClass="input frDt" maxlength="10" value="#{BranchWiseSmt.calFromDate}" style="width:70px;setMask();"/> 
                        <h:outputLabel id="lbltoDate"  value="To Date" styleClass="label" />
                        <h:inputText id="toDt" styleClass="input toDt" maxlength="10" value="#{BranchWiseSmt.caltoDate}" style="width:70px;setMask();"/>   
                    </h:panelGrid> 
                    <h:panelGrid id="FooterPanel" bgcolor="#fff" style="text-align:center;" styleClass="footer" width="100%">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnPrint" value="Print Report" action="#{BranchWiseSmt.PrintViwe}" reRender="errmsg"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{BranchWiseSmt.viewPdf}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh " action="#{BranchWiseSmt.refresh}" reRender="PanelGridMain"/>
                            <a4j:commandButton id="btnClose" value="Close" action="#{BranchWiseSmt.closeAction}" reRender="errmsg"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>