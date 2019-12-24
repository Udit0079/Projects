<%-- 
    Document   : tdActive
    Created on : Dec 15, 2011, 3:10:53 PM
    Author     : admin
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
             <title>T D Active Report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            
            <script type="text/javascript"> 
                jQuery(function(jQuery){
                    setMask();
                });
                var row;
                function setMask(){
                    jQuery(".tillDateText").mask("99/99/9999");
                }
            </script>
            
        </head>
        <body>
            <a4j:form id="cashtransfer">
                <h:panelGrid columns="1" id="mainPanel" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{tdActive.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="T D Active Report"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{tdActive.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{tdActive.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel11" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%">
                        <h:outputText />
                        <h:outputLabel value="Branch" styleClass="label"/>
                        <h:selectOneListbox id="branchddl" size="1" value="#{tdActive.branch}" styleClass="ddlist">
                            <f:selectItems value="#{tdActive.branchList}" />
                        </h:selectOneListbox>
                        <h:outputText />
                        <h:outputText />
                        <h:outputText />
                    </h:panelGrid>
                    <h:panelGrid id="panel1" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputText />
                        <h:outputText value="A/c. Type :" styleClass="label" />
                        <h:selectOneListbox id="ddlacType" size="1" styleClass="ddlist" value="#{tdActive.acType}"  >
                            <f:selectItems value="#{tdActive.acTypeList}"/>
                        </h:selectOneListbox>
                        <h:outputText value="Report Option :" styleClass="label" />
                        <h:selectOneListbox id="ddlrepOption" size="1" styleClass="ddlist" value="#{tdActive.reportType}"  >
                            <f:selectItem itemLabel="--Select--" itemValue="--Select--" />
                            <f:selectItem itemLabel="ALL" itemValue="0" />
                            <f:selectItem itemLabel="Duration Wise" itemValue="1" />
                            <f:selectItem itemLabel="Maturity Wise" itemValue="2" />
                            <f:selectItem itemLabel="Financial Year wise" itemValue="3" />
                            <f:selectItem itemLabel="Roi  Wise" itemValue="4" />
                            <f:selectItem itemLabel="As On Date Wise" itemValue="5" />
                            <a4j:support event="onblur" actionListener="#{tdActive.onblurReportType}" reRender="hiddenPanel" oncomplete="setMask()"/>
                        </h:selectOneListbox>
                        <h:outputText />
                    </h:panelGrid>
                    <h:panelGrid id="hiddenPanel" columns="1" width="100%" cellpadding="0" cellspacing="0">
                        <h:panelGrid id="panel2" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%" rendered="#{tdActive.flagPanel2}" >
                            <h:outputText value="Year" styleClass="label" />
                            <h:inputText id="txtYear" value="#{tdActive.yyyy}" styleClass="input"  style="width:50px" maxlength="4"> 
                            </h:inputText>
                            <h:outputText value="Month" styleClass="label" />
                            <h:inputText id="txtMonth" value="#{tdActive.mm}" styleClass="input"  style="width:50px" maxlength="2">
                            </h:inputText>    
                            <h:outputText value="Date" styleClass="label" />
                            <h:inputText id="txtDate" value="#{tdActive.dd}" styleClass="input"  style="width:50px" maxlength="2">
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid id="panel3" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%" rendered="#{tdActive.flagPanel3}">
                            <h:outputText />
                            <h:outputText value="Month" styleClass="label" />
                            <h:selectOneListbox id="ddlmonth" value="#{tdActive.mm1}" size="1" styleClass="label" >
                                <f:selectItem itemLabel="All" itemValue="13" />
                                <f:selectItem itemLabel="January" itemValue="1" />
                                <f:selectItem itemLabel="Februry" itemValue="2" />
                                <f:selectItem itemLabel="March" itemValue="3" />
                                <f:selectItem itemLabel="Aprail" itemValue="4" />
                                <f:selectItem itemLabel="May" itemValue="5" />
                                <f:selectItem itemLabel="June" itemValue="6" />
                                <f:selectItem itemLabel="July" itemValue="7" />
                                <f:selectItem itemLabel="Augast" itemValue="8" />
                                <f:selectItem itemLabel="Septamber" itemValue="9" />
                                <f:selectItem itemLabel="October" itemValue="10" />
                                <f:selectItem itemLabel="November" itemValue="11" />
                                <f:selectItem itemLabel="Decmber" itemValue="12" />
                            </h:selectOneListbox>
                            <h:outputText value="Year (YYYY)" styleClass="label" />
                            <h:inputText id="txtyyyy1" value="#{tdActive.yyyy1}" maxlength="4" styleClass="input" style="width:40px"/> 
                            <h:outputText/>

                        </h:panelGrid>
                        <h:panelGrid id="panel4" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%" rendered="#{tdActive.flagPanel5}">
                            <h:outputText value="From roi" styleClass="label" />
                            <h:inputText id="txtfrmoi" value="#{tdActive.frmRoi}"  styleClass="input"  style="width:50px"/>
                            <h:outputText value="To roi" styleClass="label" />
                            <h:inputText id="txttoroi" value="#{tdActive.toRoi}"  styleClass="input" style="width:50px" />
                            <h:outputText value="Order by" styleClass="label" />
                            <h:selectOneListbox id="ddlorderby" size="1" styleClass="ddlist" value="#{tdActive.orderBy}" >
                                <f:selectItem itemLabel="Roi" itemValue="vm.roi" />
                                <f:selectItem itemLabel="Account number" itemValue="vm.acno" />
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid id="panel5" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%" rendered="#{tdActive.flagPanel4}">
                            <h:outputText/>
                            <h:outputText value="From date " styleClass="label" />
                            <h:inputText value="#{tdActive.frmyear}"  styleClass="input tillDateText" style="width:70px" ><f:convertDateTime pattern="dd/MM/yyyy"/></h:inputText>
                            <h:outputText value="To date" styleClass="label" />
                            <h:inputText value="#{tdActive.toyear}"  styleClass="input tillDateText" style="width:70px"><f:convertDateTime pattern="dd/MM/yyyy"/></h:inputText>
                            <h:outputText/>
                        </h:panelGrid>
                        <h:panelGrid id="panel6" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%" rendered="#{tdActive.flagPanel6}">
                            <h:outputText/>
                            <h:outputText value="As On Date " styleClass="label" />
                             <h:inputText value="#{tdActive.asOnDate}"  styleClass="input tillDateText" style="width:70px" ><f:convertDateTime pattern="dd/MM/yyyy"/></h:inputText>
                              <font color="purple">DD/MM/YYYY</font>
                            <h:outputText/>
                            <h:outputText/>
                            <h:outputText/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                        <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                            <h:commandButton id="btnExcPrint" value="Download Excel" actionListener="#{tdActive.excelPrint}"/>
                            <a4j:commandButton  id="printBtn" value="Print Report" action="#{tdActive.viewReport()}" reRender="errorMsg"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{tdActive.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton value="Refresh" actionListener="#{tdActive.refreshForm()}" reRender="mainPanel" oncomplete="setMask()"/>
                            <a4j:commandButton   value="Exit" action="#{tdActive.exitAction}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
