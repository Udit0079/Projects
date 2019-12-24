<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Report Profile</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".issueDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <h:form id="reportProfile">
                <h:panelGrid columns="1" id="mainPanel" width="100%" style="ridge #BED6F8">
                    <h:panelGrid id="headerPanel" columns="3" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{reportProfile.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Report Profile"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{reportProfile.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row2" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{reportProfile.errMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel1" columns="4" columnClasses="col3,col3,col3,col3" width="100%" styleClass="row1">
                        <h:outputText value="Branch" styleClass="label"/>
                        <h:selectOneListbox id="listbranch" value="#{reportProfile.branch}" styleClass="ddlist" size="1"  style="width:80px">
                            <f:selectItems value="#{reportProfile.branchList}" />
                        </h:selectOneListbox>
                        <h:outputLabel value="Report Type" styleClass="label"/>
                        <h:selectOneListbox id="ddReportType" value="#{reportProfile.reportType}" styleClass="ddlist" size="1" style="width:70px">
                            <f:selectItems value="#{reportProfile.reportTypeList}"/>
                            <a4j:support event="onblur" action="#{reportProfile.reportTypeAction}" reRender="ddReportBased,txtFrom,txtTo,txtNoOfSlab,ddNature,ddAcType,errorPanel" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="panel2" columns="4" columnClasses="col3,col3,col3,col3" width="100%" styleClass="row2" style="margin:1px">
                        <h:outputLabel value="A/c Classification" styleClass="label"/>
                        <h:selectOneListbox id="ddClassification" value="#{reportProfile.classification}" styleClass="ddlist" size="1" style="width:70px">
                            <f:selectItems value="#{reportProfile.classificationList}"/>
                            <a4j:support actionListener="#{reportProfile.classificationAction}" event="onblur" reRender="ddNature,ddAcType,errorPanel" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                        <h:outputLabel value="Account Nature" styleClass="label"/>
                        <h:selectOneListbox id="ddNature" value="#{reportProfile.acNature}" styleClass="ddlist" size="1" style="width:70px">
                            <f:selectItems value="#{reportProfile.acNatureList}"/>
                            <a4j:support actionListener="#{reportProfile.getAcTypeForNature}" event="onblur" reRender="ddAcType,errorPanel" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="panel3" columns="4" columnClasses="col3,col3,col3,col3" width="100%" styleClass="row1" style="margin:1px">
                        <h:outputLabel value="Account Type" styleClass="label"/>
                        <h:selectOneListbox id="ddAcType" value="#{reportProfile.acType}" styleClass="ddlist,errorPanel" size="1" style="width:70px">
                            <f:selectItems value="#{reportProfile.acTypeList}" />
                        </h:selectOneListbox>
                        <h:outputText value="Report Based On" styleClass="label"/>
                        <h:selectOneListbox id="ddReportBased" value="#{reportProfile.reportBasedOn}" styleClass="ddlist" size="1" style="width:80px">
                            <f:selectItems value="#{reportProfile.reportBasedOnList}" />
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="panel4" columns="4" columnClasses="col3,col3,col3,col3" width="100%" styleClass="row2" style="margin:1px">
                        <h:outputLabel value="From " styleClass="label"/>
                        <h:inputText id="txtFrom" value="#{reportProfile.from}" size="10"/>
                        <h:outputLabel value="To " styleClass="label"/>
                        <h:inputText id="txtTo" value="#{reportProfile.to}" size="10"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel5" columns="4" columnClasses="col3,col3,col3,col3" width="100%" styleClass="row1" style="margin:1px">
                        <h:outputLabel value="No Of Slab " styleClass="label"/>
                        <h:inputText id="txtNoOfSlab" value="#{reportProfile.noOfSlab}" size="10" maxlength="2"/>
                        <h:outputLabel styleClass="label" value="As On Date"/>
                        <h:inputText id="txtAsOnDt" styleClass="input issueDt" style="width:70px" value="#{reportProfile.asOnDt}"/>
                    </h:panelGrid>

                    <h:panelGrid id="panel6" columns="4" columnClasses="col3,col3,col3,col3" width="100%" styleClass="row2" style="margin:1px">
                        <h:outputLabel value="Report Base" styleClass="label"/>
                        <h:selectOneListbox id="ddReportBase" value="#{reportProfile.reportBase}" styleClass="ddlist" size="1" style="width:120px">
                            <f:selectItems value="#{reportProfile.reportBaseList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>

                    <h:panelGrid id="panel7" columns="2" width="100%" styleClass="row2" style="margin:1px">  
                        <h:outputLabel value="Note :  " styleClass="label" style="color:blue;font-weight:bold"/>
                        <h:outputLabel value="To Slab will not include in the Data " styleClass="label" style="color:blue;font-weight:bold"/>
                    </h:panelGrid>
                    <h:panelGrid  columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                        <a4j:region id="buttonRegion">
                            <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                                <h:commandButton value="Download Pdf" action="#{reportProfile.pdfReport}" />
                                <h:commandButton value="View Report" action="#{reportProfile.htmlReport}" />
                                <a4j:commandButton value="Refresh" action="#{reportProfile.refresh}" reRender="mainPanel" oncomplete="setMask();"/>
                                <a4j:commandButton value="Exit" action="#{reportProfile.exit}" reRender="mainPanel" oncomplete="setMask();"/>
                            </h:panelGroup>
                        </a4j:region>
                    </h:panelGrid>
                    <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="buttonRegion"/>
                    <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;" >
                        <f:facet name="header">
                            <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                        </f:facet>
                    </rich:modalPanel>
                </h:panelGrid>
            </h:form>
        </body>
    </html>
</f:view>
