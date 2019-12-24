<%-- 
    Document   : experionloanreport
    Created on : 8 Jun, 2012, 3:17:22 PM
    Author     : Shipra 
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
        <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
        <meta http-equiv="Pragma" content="no-cache"/>
        <meta http-equiv="Cache-Control" content="no-cache"/>
        <title>CIBIL Report</title>
        <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
        <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
        <script type="text/javascript">
            jQuery(function(jQuery) {
                setMask();
            });
            function setMask() {
                jQuery(".fromcalDate").mask("99/99/9999");
                jQuery(".tocalDate").mask("99/99/9999");
            }

        </script>
    </head>
    <body>
        <%--a4j:keepAlive beanName="ClearingTextFilePrint"/--%>
        <a4j:form id="form1"> 
            <h:panelGrid bgcolor="#fff" columns="1" id="a1" style="border:1px ridge #BED6F8" width="100%">
                <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                    <h:panelGroup id="a2" layout="block">
                        <h:outputLabel value="Date : " styleClass="headerLabel"/>
                        <h:outputText value="#{ExperionLoanReport.todayDate}" id="stxtDate" styleClass="output"/>
                    </h:panelGroup>
                    <h:outputLabel value="CIBIL Report" styleClass="headerLabel"/>
                    <h:panelGroup layout="block">
                        <h:outputLabel value="User : " styleClass="headerLabel"/>
                        <h:outputText value="#{ExperionLoanReport.userName}" id="stxtUser" styleClass="output"/>
                    </h:panelGroup>
                </h:panelGrid>
                <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row1" style="text-align:center" width="100%">
                    <h:outputLabel id="lblMsg" styleClass="error" style="color:red;" value="#{ExperionLoanReport.message}"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3" columns="6" id="a5" styleClass="row1" style="height:30px;" width="100%">                         
                    <%--h:panelGroup layout="block" style="text-align:center;"--%>
                    <h:outputLabel id="membercode" value="Member Code : " styleClass="label">
                        <font class="required" style="color:red">*</font>
                        </h:outputLabel>
                        <h:inputText id="membercodetxt" styleClass="input" value="#{ExperionLoanReport.memberCode}"  size="20" >
                            <a4j:support event="onblur" oncomplete="setMask()" />
                        </h:inputText>                            
                        <%--/h:panelGroup--%>
                        <%--h:panelGroup layout="block" style="text-align:center;"--%>
                        <h:outputLabel id="lblCibilCompList" styleClass="label" value="Company Name: "/>
                        <h:selectOneListbox id="ddCibilCompList" styleClass="ddlist" value="#{ExperionLoanReport.cibilCompany}" size="1" style="width:90px">
                            <f:selectItems value="#{ExperionLoanReport.ddCibilCompanyList}"/>
                            <a4j:support event="onblur" oncomplete="setMask()" />
                        </h:selectOneListbox>
                        <%--/h:panelGroup--%>
                        <%--h:panelGroup layout="block" style="text-align:center;">
                            <h:outputLabel id="membercode1"  styleClass="label">
                            </h:outputLabel>
                            <h:outputLabel id="membercode2"  styleClass="label">
                            </h:outputLabel>
                        </h:panelGroup--%> 
                        <h:outputLabel id="repType" value="Report Type : " styleClass="label">
                        <font class="required" style="color:red">*</font>
                        </h:outputLabel>
                        <h:selectOneListbox id="ddCibilRepType" styleClass="ddlist" value="#{ExperionLoanReport.reportType}" size="1" style="width:90px">
                            <f:selectItems value="#{ExperionLoanReport.reportTypeList}"/>
                            <a4j:support event="onblur" action="#{ExperionLoanReport.disableSaveBnt}" reRender="btnSaveToFile,lblCibilrepType,ddCibilrepTypelist" oncomplete="setMask()" />
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="a6" styleClass="row1" style="height:30px;" width="100%">

                    <%--h:panelGroup layout="block" style="text-align:center;"--%>
                    <h:outputLabel id="fromdate" value="From Date : " styleClass="label">
                        <font class="required" style="color:red">*</font>
                        </h:outputLabel>
                        <h:inputText id="fromcalDate" styleClass="input fromcalDate" style="setMask();" size="10" value="#{ExperionLoanReport.fromCalDate}">
                            <f:convertDateTime pattern="dd/MM/yyyy"/>
                            <a4j:support event="onblur" oncomplete="setMask()" />
                        </h:inputText>
                        <%--/h:panelGroup--%> 
                        <%--h:panelGroup layout="block" style="text-align:center;"--%>
                        <h:outputLabel id="todate" value="To Date : " styleClass="label">
                        <font class="required" style="color:red">*</font>
                        </h:outputLabel>
                        <h:inputText id="tocalDate" styleClass="input tocalDate" style="setMask();" size="10" value="#{ExperionLoanReport.toCalDate}">
                            <f:convertDateTime pattern="dd/MM/yyyy"/>
                            <a4j:support event="onblur" oncomplete="setMask()" />
                        </h:inputText>
                        <%--/h:panelGroup--%>   
                        
                        <h:outputLabel id="lblCibilrepType" styleClass="label" value="Report Format: " style ="display:#{ExperionLoanReport.displayRepType}" />
                        <h:selectOneListbox id="ddCibilrepTypelist" styleClass="ddlist" value="#{ExperionLoanReport.repType}" size="1" style="width:90px;display:#{ExperionLoanReport.displayRepType}">
                            <f:selectItems value="#{ExperionLoanReport.repTypeList}"/>
                            <a4j:support event="onblur" oncomplete="setMask()" />
                        </h:selectOneListbox>
                        
                    </h:panelGrid>
                    <h:panelGrid id="a7" columns="2" style="width:100%;height:30px;text-align:center;" styleClass="row1" width="100%">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:repeat value="#{ExperionLoanReport.txtFileNameList}" var="fileName">
                                <a4j:commandLink id="saveLink" value="#{fileName}"  style="color:blue;" styleClass="headerLabel"
                                                 action="#{ExperionLoanReport.downloadFile}" rendered="#{ExperionLoanReport.saveLink}">
                                    <f:setPropertyActionListener target="#{ExperionLoanReport.fileName}" value="#{fileName}"/>
                                </a4j:commandLink>
                            &nbsp;&nbsp;
                        </a4j:repeat>
                    </h:panelGroup>
                </h:panelGrid>
                <%--<h:panelGrid columnClasses="col15,col15,col15,col15,col15,col15,col15,col1,col15" columns="9" id="gridPanel8" styleClass="row1" style="display:#{ExperionLoanReport.commandLink}">
                   <h:commandLink id="HD" value="HD.xls" actionListener="#{ExperionLoanReport.cibilComReport('HD')}"/>
                   <h:commandLink id="BS" value="BS.xls" actionListener="#{ExperionLoanReport.cibilComReport('BS')}"/>
                   <h:commandLink id="AS" value="AS.xls" actionListener="#{ExperionLoanReport.cibilComReport('AS')}"/>
                   <h:commandLink id="RS" value="RS.xls" actionListener="#{ExperionLoanReport.cibilComReport('RS')}"/>
                   <h:commandLink id="CR" value="CR.xls" actionListener="#{ExperionLoanReport.cibilComReport('CR')}"/>
                   <h:commandLink id="GS" value="GS.xls" actionListener="#{ExperionLoanReport.cibilComReport('GS')}"/>
                   <h:commandLink id="SS" value="SS.xls" actionListener="#{ExperionLoanReport.cibilComReport('SS')}"/>
                   <h:commandLink id="CD" value="CD.xls" actionListener="#{ExperionLoanReport.cibilComReport('CD')}"/>
                   <h:commandLink id="TS" value="TS.xls" actionListener="#{ExperionLoanReport.cibilComReport('TS')}"/>
               </h:panelGrid>--%>
                <h:panelGrid  columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                    <a4j:region id="btnRgn">
                        <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnSaveToFile" value="Save To File" actionListener="#{ExperionLoanReport.cibilTextGen}" oncomplete="setMask();" disabled="#{ExperionLoanReport.disableSave}" reRender="a5,a6,lblMsg,a7,saveLink"/>
                            <h:commandButton  value="Download Excel"  actionListener="#{ExperionLoanReport.btnPdfAction}" />
                            <a4j:commandButton value="Refresh" reRender="a1" action="#{ExperionLoanReport.refreshForm}" oncomplete="setMask();" />
                            <a4j:commandButton   value="Exit" action="case1" reRender="a1"/>
                        </h:panelGroup>
                    </a4j:region>
                </h:panelGrid>
            </h:panelGrid>
        </a4j:form>  
        <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="btnRgn"/>
        <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;" >
            <f:facet name="header">
                <h:graphicImage url="/resources/images/ajax-loader.gif"/>
            </f:facet>
        </rich:modalPanel>                    
        <%--   <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
           <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
               <f:facet name="header">
                   <h:graphicImage url="/resources/images/ajax-loader.gif"/>
               </f:facet>
           </rich:modalPanel>   --%>                  
    </body>
</html>
</f:view>
