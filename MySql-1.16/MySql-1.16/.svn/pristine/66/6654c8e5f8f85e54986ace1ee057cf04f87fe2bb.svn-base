<%-- 
    Document   : UtilityReport
    Created on : 13 Jun, 2018, 10:49:17 AM
    Author     : root
--%>

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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title> Utility Report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".calInstDate").mask("99/99/9999");
                }

            </script>
        </head>
        <body>
            <h:form id="UtilityReport">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{UtilityReport.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Utility Report"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{UtilityReport.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{UtilityReport.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panelmain" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row2" width="100%">
                        <h:outputLabel id="lblfuncion" value="Mode" styleClass="label"/>
                        <h:selectOneListbox id="ddMode" value="#{UtilityReport.mode}" styleClass="ddlist" style="width:100px" size="1">
                            <f:selectItems id="selectMode" value="#{UtilityReport.modeList}"/>
                          <a4j:support event="onblur" action="#{UtilityReport.onBlurmode}" reRender="errorMsg,lblbranch,lblacNo,ddbranch,txtAcno,stxtNewAccount" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                        <h:outputLabel></h:outputLabel>
                        <h:outputText></h:outputText>
                    </h:panelGrid>
                <h:panelGrid id="filter" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row2" width="100%">
                     <h:outputLabel id="lblsearchBy" value="Search By" styleClass="label"/>  
                     <h:selectOneListbox id="ddsearchBy" value="#{UtilityReport.searchBy}"  styleClass="ddlist" style="width:100px" size="1" title="code no(429)">
                        <f:selectItems id="selectsearchBy" value="#{UtilityReport.searchByList}"/>
                        <a4j:support event="onblur" action="#{UtilityReport.searchByAction}" oncomplete="setMask();" reRender="a1,ddsearchByOption,errorMsg," />
                        </h:selectOneListbox>
                        <h:outputLabel id="lblsearchByOption" value="Search Option" styleClass="label"/>
                        <h:selectOneListbox id="ddsearchByOption" value="#{UtilityReport.searchByOption}"  styleClass="ddlist" style="width:100px" size="1" title="code no(430)">
                       <f:selectItems id="selectsearchByOption" value="#{UtilityReport.searchByOptionList}"/>
                         </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="branch1" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row2" width="100%">
                        <h:panelGroup>
                        <h:outputLabel id="lblbranch" value="Branch" styleClass="label" style = "display:#{UtilityReport.branchDisplay}"><font class="required" color="red">*</font></h:outputLabel>
                         <h:outputLabel id="lblacNo" styleClass="label" value="Account No." style = "display:#{UtilityReport.acdisplay}" ><font class="required" color="red">*</font></h:outputLabel>
                        </h:panelGroup>
                        <h:panelGroup>
                        <h:selectOneListbox id="ddbranch" value="#{UtilityReport.branch}" styleClass="ddlist" style="width:100px;display:#{UtilityReport.branchDisplay}" size="1">
                        <f:selectItems id="selectbranch" value="#{UtilityReport.branchList}"/>
                        </h:selectOneListbox>
                        <h:inputText id="txtAcno" styleClass="input" value="#{UtilityReport.acno}"  style ="display:#{UtilityReport.acdisplay}" size="15">
                               <a4j:support event="onblur" action="#{UtilityReport.accountAction}" oncomplete="setMask();" reRender="a1,stxtNewAccount,errorMsg," />
                                </h:inputText>
                                <h:outputText id="stxtNewAccount" styleClass="output" style="display:#{UtilityReport.acdisplay}" value="#{UtilityReport.newAccountNo}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblRemarks" value="Remarks" styleClass="label"/>
                        <h:inputText id="Remarkss"  value="#{UtilityReport.remarks}" style="width:280px" styleClass="row1"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel2" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row1" width="100%">                       
                        <h:outputLabel value="From Date" styleClass="label"/>
                        <h:inputText id="txtFrmDate" styleClass="input calInstDate" style="setMask();width:70px;"  value="#{UtilityReport.frmDt}"/>
                        <h:outputLabel value="To Date" styleClass="label"/>
                        <h:inputText id="txtToDate" styleClass="input calInstDate" style="setMask();width:70px;"  value="#{UtilityReport.toDt}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                        <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnPrint" value="Print Report" action="#{UtilityReport.viewReport}" reRender="errorMsg"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{UtilityReport.btnPdfAction}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{UtilityReport.refresh}" reRender="a1,errorMsg,ddbranch,Remarkss" oncomplete="setMask()"/>
                            <a4j:commandButton id="btnClose" value="Exit" action="#{UtilityReport.exitAction}" reRender="errorMsg"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                    <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <f:facet name="header">
                            <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                        </f:facet>
                    </rich:modalPanel>
                </h:panelGrid>
            </h:form>
        </body>  
    </html>
</f:view>
