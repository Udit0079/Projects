<%-- 
    Document   : ledgerReport
    Created on : Dec 20, 2011, 3:58:15 PM
    Author     : Ankit Verma
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>

            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Ledger Report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".calInstDate").mask("39/19/9999");
                }

            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="mainPanelGrid" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{LedgerReport.todayDate}"/>
                        </h:panelGroup>

                        <h:outputLabel styleClass="headerLabel" value="Ledger Report"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{LedgerReport.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="grdpane6" style="height:30px;text-align:center" styleClass="row2" width="100%" >
                        <h:outputText id="stxtError"  styleClass="error" value="#{LedgerReport.msg}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col3" id="gridpanel12"   style="height:30px;" styleClass="row1" width="100%" >
                      <h:outputText value="Branch" styleClass="label"/>
                        <h:selectOneListbox id="listbranch" value="#{LedgerReport.branch}" styleClass="ddlist" size="1"  style="width:80px">
                            <f:selectItems value="#{LedgerReport.branchList}" />
                        </h:selectOneListbox>  
                        
                        <h:outputLabel id="lblaccttype" styleClass="headerLabel"  value="Account Type"  />
                        <h:selectOneListbox id="selectListBox" value="#{LedgerReport.selectAcType}" styleClass="ddlist" size="1"  style="width:80px" >
                            <f:selectItems id="selectAcTypeList" value="#{LedgerReport.selectAcTypeList}" />
                        </h:selectOneListbox>  
                        <h:outputLabel id="lblFromAcNo" styleClass="headerLabel"  value="From Account No."  />
                        <h:inputText id="txtFromAcNo"   styleClass="input"  value="#{LedgerReport.fromAcNo}" style="width:70px;" maxlength="6" onkeyup="this.value=this.value.toUpperCase()" />
                        
                    </h:panelGrid>
                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col3" id="gridpanel1"   style="height:30px;" styleClass="row2" width="100%" >
                       
                        <h:outputLabel id="lblToAcNo" styleClass="headerLabel"  value="To Account No."  />
                        <h:inputText id="txtToAcNo"   styleClass="input"  value="#{LedgerReport.toAcNo}" style="width:70px;" maxlength="6" onkeyup="this.value=this.value.toUpperCase()" />
                        <h:outputLabel id="lblFromDate" styleClass="headerLabel"  value="From Date"  />
                        <h:panelGroup styleClass="label">
                            <h:inputText id="txtFromDate"  styleClass="input calInstDate" style="width:70px"  maxlength="40" value="#{LedgerReport.fromDate}" > 
                                <f:convertDateTime pattern="dd/MM/yyyy" /> </h:inputText>
                            <font color="purple" >DD/MM/YYYY</font>
                        </h:panelGroup>
                        <h:outputLabel id="lblToDate" styleClass="headerLabel"  value="To Date"  />
                        <h:panelGroup styleClass="label">
                            <h:inputText id="txtToDate"   styleClass="input calInstDate" style="width:70px"  value="#{LedgerReport.toDate}" >
                                <f:convertDateTime pattern="dd/MM/yyyy" />
                            </h:inputText>
                            <font color="purple">DD/MM/YYYY</font>
                        </h:panelGroup>
                            
                    
                        
                    </h:panelGrid>
                    <h:panelGrid columns="2" id="gridpanel5" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton  id="btnView" value="View"   action="#{LedgerReport.viewReport}" reRender="stxtError,mainPanelGrid,txtFromDate,txtToDate" oncomplete="setMask();">
                            </a4j:commandButton>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{LedgerReport.btnPdfAction}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh"  action="#{LedgerReport.refreshForm}"  reRender="mainPanelGrid,txtFromDate,txtToDate" oncomplete="setMask();">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" value="Exit"  action="#{LedgerReport.exitAction}" reRender="mainPanelGrid" >
                            </a4j:commandButton>

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
