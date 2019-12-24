<%-- 
    Document   : npaInterest
    Created on : Jan 15, 2013, 4:21:45 PM
    Author     : sipl
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
            <title>NPA INTEREST REPORT </title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".frDate").mask("39/19/9999");
                    jQuery(".toDate").mask("39/19/9999");    
                }

            </script>
        </head>
        <body>
            <a4j:form id="npaInterest">
                <h:panelGrid columns="1" id="mainPanel" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="blockPanel" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{npaInterest.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="NPA Interest Report"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{npaInterest.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{npaInterest.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel11" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row1" width="100%">
                        <h:outputText value="Branch Wise" styleClass="label"/>
                        <h:selectOneListbox id="branch" size="1" value="#{npaInterest.branchCode}" styleClass="ddlist">
                            <f:selectItems value="#{npaInterest.branchCodeList}" />
                        </h:selectOneListbox>  
                        <h:outputText/>
                        <h:outputText/>
                    </h:panelGrid>
                    <h:panelGrid id="panel1" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row2" width="100%">
                        <h:outputText id="stxtRepTp" value="Report Type" styleClass="label"/>
                        <h:selectOneListbox id="selectRep" value="#{npaInterest.selectRepType}" styleClass="ddlist"  style="width:100px" size="1">
                            <f:selectItems id="selectRepTypeList" value="#{npaInterest.selectRepTpList}" />
                        </h:selectOneListbox> 
                        <h:outputText id="stxtAcTp" value="Account Type" styleClass="label"/>
                        <h:selectOneListbox id="selectAcTp" value="#{npaInterest.selectAcType}" styleClass="ddlist"  style="width:70px" size="1">
                            <f:selectItems id="selectAcTypeList" value="#{npaInterest.selectAcTpList}" />
                        </h:selectOneListbox> 
                    </h:panelGrid>
                    <h:panelGrid id="panel2" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row1" width="100%">
                        <h:outputText value="From Date" styleClass="label"/>
                         <h:panelGroup styleClass="label">
                            <h:inputText id="frDate" styleClass="input frDate" style="width:70px;"  value="#{npaInterest.frmDt}">                            
                                <f:convertDateTime pattern="dd/MM/yyyy"/>
                            </h:inputText>    
                        </h:panelGroup>
                        <h:outputText value="To Date" styleClass="label"/>
                        <h:panelGroup styleClass="label">
                            <h:inputText id="toDate" styleClass="input toDate" style="width:70px;"  value="#{npaInterest.toDt}">                            
                                <f:convertDateTime pattern="dd/MM/yyyy"/>
                            </h:inputText>    
                        </h:panelGroup>                        
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                        <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton value="Print Report" action="#{npaInterest.viewReport}" reRender="errorMsg,txtFrmDate,txtToDate" oncomplete="setMask()"/>
                             <h:commandButton id="btnDownload"  value="PDF Download" action="#{npaInterest.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton value="Refresh" action="#{npaInterest.refresh}" oncomplete="setMask()" reRender="frDate,toDate,mainPanel"/>
                            <a4j:commandButton value="Exit" action="#{npaInterest.exitAction}" oncomplete="setMask()" reRender="frDate,toDate,mainPanel"/>
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