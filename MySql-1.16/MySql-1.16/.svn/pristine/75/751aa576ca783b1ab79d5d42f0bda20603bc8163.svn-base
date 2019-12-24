<%-- 
    Document   : MarketingMis
    Created on : 23 Apr, 2019, 3:37:52 PM
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Lead Mis </title>
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
            <h:form id="form1">
                <h:panelGrid columns="1" id="mainPanel" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{MarketingMis.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Lead Mis"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{MarketingMis.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{MarketingMis.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="branchgrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%" >
                        <h:outputLabel id="branch" value="Branch" styleClass="label" style="width:100%;height:0px;"><font class="required" style="color:red;">*</font></h:outputLabel> 
                        <h:selectOneListbox id="branchvalue" value="#{MarketingMis.branch}" styleClass="ddlist" style="width:130px" size="1">
                            <f:selectItems id="selectbranch" value="#{MarketingMis.branchList}"/>
                            <a4j:support event="onblur"   focus="entryvalue"/>
                        </h:selectOneListbox >
                        <h:outputLabel id="entryType" value="Entry Type" styleClass="label" style="width:100%;height:0px"><font class="required" style="color:red;">*</font></h:outputLabel> 
                        <h:selectOneListbox id="entryvalue" value="#{MarketingMis.entryType}" styleClass="ddlist" size="1"  >
                            <f:selectItems id="selectentryType" value="#{MarketingMis.entryTypeList}"/>
                            <a4j:support event="onblur" action="#{MarketingMis.entryTypeMode}" 
                                         reRender="errorMsg,typevalue,followdategrid" 
                                         focus="typevalue"/>
                        </h:selectOneListbox >
                        <h:outputLabel id="type" value="Mis Type" styleClass="label" style="width:100%;height:0px;"><font class="required" style="color:red;">*</font></h:outputLabel> 
                        <h:selectOneListbox id="typevalue" value="#{MarketingMis.misType}" styleClass="ddlist" style="width:130px" size="1">
                            <f:selectItems id="selecttype" value="#{MarketingMis.misTypeList}"/>
                            <a4j:support event="onblur"  focus="txtFrmDate"/>
                        </h:selectOneListbox >
                    </h:panelGrid>
                    <h:panelGrid id="dategrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%" >
                        <h:outputLabel value="(Entry)From Date" styleClass="label"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtFrmDate" styleClass="input calInstDate" style="setMask();width:70px;"  value="#{MarketingMis.frmdt}">
                            <a4j:support event="onblur" action="#{MarketingMis.setDateCheck}" oncomplete="setMask();"  reRender="txtToDate, errorMsg" focus="txtToDate"/>  
                        </h:inputText>  
                        <h:outputLabel value="(Entry) To Date" styleClass="label"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtToDate" styleClass="input calInstDate" style="setMask();width:70px;"  value="#{MarketingMis.toDt}">
                            <a4j:support event="onblur" action="#{MarketingMis.dailyDateCheck}" oncomplete="setMask();"  reRender="errorMsg" focus="reportvalue"/>  
                        </h:inputText>
                        <h:outputLabel id="reportType" value="Report Type" styleClass="label" style="width:100%;height:0px"><font class="required" style="color:red;">*</font></h:outputLabel> 
                        <h:selectOneListbox id="reportvalue" value="#{MarketingMis.reportType}" styleClass="ddlist" size="1"  >
                            <f:selectItems id="selectreportType" value="#{MarketingMis.reportTypeList}"/>      
                            <a4j:support event="onblur"  action="#{MarketingMis.misTypeMode}" reRender="dategrid,followdategrid"  oncomplete="if(#{rich:element('reportvalue')}.value=='F'){#{rich:element('txtFrmDate1')}.focus();}
                                         else if(#{rich:element('reportvalue')}.value=='N'){#{rich:element('print')}.focus();}"/> 
                        </h:selectOneListbox >

                    </h:panelGrid>
                    <h:panelGrid id="followdategrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%" style="display:#{MarketingMis.followdatedisplay}" >
                        <h:outputLabel value="(FollowUp) From Date" styleClass="label"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtFrmDate1" styleClass="input calInstDate" style="setMask();width:70px;"  value="#{MarketingMis.followUpfrmDt}">
                            <a4j:support event="onblur" oncomplete="setMask();" focus="txtToDate1"/>  
                        </h:inputText>  
                        <h:outputLabel value="(FollowUp) To Date" styleClass="label"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtToDate1" styleClass="input calInstDate" style="setMask();width:70px;"  value="#{MarketingMis.followUptoDt}">
                            <a4j:support event="onblur" oncomplete="setMask();" focus="print"/>  
                        </h:inputText>  
                        <h:outputLabel> </h:outputLabel>
                        <h:outputLabel> </h:outputLabel>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer" >
                        <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">                           
                            <a4j:commandButton id="print" value="Print Report" action="#{MarketingMis.printreport}" reRender="errorMsg"/>
                            &nbsp;
                            <a4j:commandButton id="pdf" value="PDF Download" action="#{MarketingMis.btnPdfAction}" reRender="errorMsg"/>
                            &nbsp;
                            <h:commandButton id="btnExcel" value="Download Excel" actionListener="#{MarketingMis.downloadExcel}"/>
                            &nbsp;
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{MarketingMis.refresh}" reRender="mainPanel"  />
                            &nbsp;
                            <a4j:commandButton id="btnClose" value="Exit" action="#{MarketingMis.exit}" reRender="errorMsg"/>
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