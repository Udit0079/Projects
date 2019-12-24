<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <title>DICGC Report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".asOnDate").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="dicgcreport">
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
                <h:panelGrid id="PanelGridMain" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="HeaderPanel" columns="3" style="text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{dicgc.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="DICGC Report"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUsername" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="otUsername" styleClass="output" value="#{dicgc.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errPanel" style="text-align:center" styleClass="row1">
                        <h:outputText id="errmsg" value="#{dicgc.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;text-align:left;" styleClass="row2" width="100%">
                        <h:outputLabel value="Branch" styleClass="label"/>
                        <h:selectOneListbox id="branchddl" size="1" value="#{dicgc.branch}" styleClass="ddlist">
                            <f:selectItems value="#{dicgc.branchList}" />
                        </h:selectOneListbox>
                        <h:outputLabel value="Select Account Type" styleClass="label"/>
                        <h:selectOneListbox id="ddAccountType" value="#{dicgc.acType}" styleClass="ddlist" size="1">
                            <f:selectItems value="#{dicgc.acTypeList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel value="Report Type" styleClass="label"/>
                        <h:selectOneListbox id="ddReportType" value="#{dicgc.reportType}" styleClass="ddlist" size="1">
                            <f:selectItems value="#{dicgc.reportTypeList}"/>
                            <a4j:support actionListener="#{dicgc.onBlurReportType}" event="onblur" reRender="btnPanel,panelGrid1,panelGrid2"/> 
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="panelGrid1" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;text-align:left;" styleClass="row2" width="100%">
                        <h:outputLabel value="Institution Code No." styleClass="label"/>
                        <h:inputText id="txtInstCode" value="#{dicgc.instCode}" styleClass="input"/>
                        <h:outputLabel value="As On Date" styleClass="label"/>
                        <h:inputText id="asOnDate" styleClass="input asOnDate" maxlength="10" value="#{dicgc.calDate}" style="width:70px"/>
                        <h:outputLabel value="A/C Balance Type" styleClass="label" style="display:#{dicgc.accBalTypeDisplay}"/>
                        <h:selectOneListbox id="ddAcbalType" value="#{dicgc.accBalType}" styleClass="ddlist" size="1" style="display:#{dicgc.accBalTypeDisplay}">
                            <f:selectItems value="#{dicgc.accBalTypeList}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="panelGrid2" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;text-align:left;" styleClass="row2" width="100%">
                        <h:outputLabel value="Deaf A/C Consider" styleClass="label" style="display:#{dicgc.accBalTypeDisplay}"/>
                        <h:selectOneListbox id="ddDeafAcType" value="#{dicgc.deafAcType}" styleClass="ddlist" size="1" style="display:#{dicgc.accBalTypeDisplay}">
                            <f:selectItems value="#{dicgc.deafAcTypeList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel/>
                        <h:outputLabel/>  
                        <h:outputLabel/>
                        <h:outputLabel/>  
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel8" styleClass="row1" >
                        <h:outputLabel value="Download: " styleClass="label" style="display:#{dicgc.hyperLinkDisplay13}"/>
                        <h:commandLink id="first" value="File upto 30000" actionListener="#{dicgc.splitReport('FIRST')}" style="display:#{dicgc.hyperLinkDisplay13}"/>
                        <h:commandLink id="second" value="File 30000 to 60000" actionListener="#{dicgc.splitReport('SECOND')}" style="display:#{dicgc.hyperLinkDisplay36}"/>
                        <h:commandLink id="third" value="File >60000" actionListener="#{dicgc.splitReport('THIRD')}" style="display:#{dicgc.hyperLinkDisplay6last}"/>
                    </h:panelGrid>

                    <h:panelGrid id="FooterPanel" bgcolor="#fff" style="text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnPrint" value="Print Report" action="#{dicgc.printAction}" reRender="errmsg" oncomplete="setMask();" style="display:#{dicgc.dispalyPDFDownload}"/>
                            <h:commandButton id="btnPDF"  value="PDF Download" actionListener="#{dicgc.viewPdfReport}" style="display:#{dicgc.dispalyPDFDownload}" styleClass="color: #044F93;text-decoration: none;" /> 
                            <%-- <h:commandButton id="btnExcel"  value="Excel Download" actionListener="#{dicgc.excelPrint}" style="display:#{dicgc.dispalyExcelDownload}"
                                              styleClass="color: #044F93;text-decoration: none;" /> 
                            --%>
                            <a4j:commandButton id="btnExcel" value="Excel Download" action="#{dicgc.excelPrint}" reRender="errmsg,gridPanel8,gridPanel8" oncomplete="setMask();" style="display:#{dicgc.dispalyExcelDownload}"/>
                            <a4j:commandButton id="btnRefresh" value="Reset" action="#{dicgc.resetAction}" reRender="errmsg,PanelGridMain" oncomplete="setMask();"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{dicgc.exitAction}" reRender="errmsg,ddAccountType,txtInstCode,asOnDate,ddReportType"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>