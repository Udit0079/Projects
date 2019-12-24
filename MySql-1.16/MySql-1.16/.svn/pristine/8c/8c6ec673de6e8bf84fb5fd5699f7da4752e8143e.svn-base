<%-- 
    Document   : outwardClearing
    Created on : 21 Dec, 2011, 10:54:20 AM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Outward Clearing Report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />

            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                var row;
                function setMask() {
                    jQuery(".calDate").mask("99/99/9999");
                }
            </script>
            <script type="text/javascript">
            </script>
        </head>
        <body>
            <a4j:form id="form1"> 
                <h:panelGrid bgcolor="#fff" columns="1" id="a1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel value="Date : " styleClass="headerLabel"/>
                            <h:outputText value="#{OutwardClearing.stxtDate}" id="stxtDate" styleClass="output"/>
                        </h:panelGroup>
                        <h:outputLabel value="Outward Clearing Report" styleClass="headerLabel"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel value="User : " styleClass="headerLabel"/>
                            <h:outputText value="#{OutwardClearing.stxtUser}" id="stxtUser" styleClass="output"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="af5" style="width:100%;height:30px;text-align:center;" styleClass="row1" width="100%">
                        <h:outputText id="stxtErrMsg" style="color:red;text-align:center;" styleClass="error" value="#{OutwardClearing.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col7,col3,col7" columns="4" id="gridPanel2s" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel/>
                        <h:outputLabel id="branchIdLabel" value="Branch :" styleClass="output" style="padding-left:300px;"/>
                        <h:selectOneListbox id="branchId" styleClass="ddlist" value="#{OutwardClearing.branch}" size="1" style="width:185px">
                            <f:selectItems value="#{OutwardClearing.branchList}"/>
                            <%-- <a4j:support action="#{OutwardClearing.ddAcTypeProcessValueChange}" event="onblur" reRender="af11" focus="dropDown1"/> --%>
                        </h:selectOneListbox>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col7,col3,col7" columns="4" id="gridPanel3" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel/>
                        <h:outputLabel id="label3rt" value="Report :" styleClass="output" style="padding-left:300px;"/>
                        <h:selectOneListbox id="ddAcType" styleClass="ddlist" value="#{OutwardClearing.ddAcType}" size="1">
                            <f:selectItems value="#{OutwardClearing.ddAcTypeList}"/>
                            <a4j:support action="#{OutwardClearing.ddAcTypeProcessValueChange}" event="onblur" reRender="af11" focus="dropDown1"/>
                        </h:selectOneListbox>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col7,col3,col7" columns="4" id="gridPanel3q" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel/>
                        <h:outputLabel id="label3" value="Circle Type :" styleClass="output" style="padding-left:300px;"/>
                        <h:selectOneListbox id="dropDown1" styleClass="ddlist" value="#{OutwardClearing.dropDown1}" size="1" style="width:185px">
                            <f:selectItems value="#{OutwardClearing.dropDown1List}"/>
                        </h:selectOneListbox>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col7,col3,col7" columns="4" id="gridPanel4a" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel/>
                        <h:outputLabel id="label2" value="Date :" styleClass="output" style="padding-left:300px;"/>
                        <%--<rich:calendar value="#{OutwardClearing.calDate}" datePattern="dd/MM/yyyy" id="calDate"/> --%>
                        <h:inputText id="calDate" styleClass="input calDate"  style="setMask();" value="#{OutwardClearing.calDate}">
                            <f:convertDateTime pattern="dd/MM/yyyy" />
                        </h:inputText>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col7,col3,col7" columns="4" id="af11" style="height:30px;width:100%;display:#{OutwardClearing.flag}" styleClass="row2" width="100%">
                        <h:outputLabel/>
                        <h:outputLabel id="label2hdfc" styleClass="output"
                                       value="For HDFC :" style="padding-left:300px;"/>
                        <h:selectOneListbox id="dropDown2" styleClass="ddlist" value="#{OutwardClearing.dropDown2}" size="1" style="width:185px">
                            <f:selectItems value="#{OutwardClearing.dropDown2List}"/>
                        </h:selectOneListbox>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col7,col3,col7" columns="4" id="gridPanel4b" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel/>
                        <h:outputLabel id="labelorderby" value="Order By :" styleClass="output" style="padding-left:300px;"/>
                        <h:selectOneListbox id="dropDown1orderby" styleClass="ddlist" value="#{OutwardClearing.ddOrderBy}"  size="1" style="width:185px">
                            <f:selectItems value="#{OutwardClearing.ddOrderByList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                        <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton action="#{OutwardClearing.btnHtmlAction}" id="btnHtml" style="width:90px" value="Print Report" reRender="stxtErrMsg"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{OutwardClearing.btnPdfAction}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton action="#{OutwardClearing.refreshForm}" id="refresh" style="width:90px" oncomplete="setMask();" value="Refresh" reRender="ddAcType,dropDown1,calDate,dropDown2,stxtErrMsg,branchId"/>
                            <a4j:commandButton action="#{OutwardClearing.btnExitAction}" id="btnExit" style="width:90px" value="Exit" reRender="ddAcType,dropDown1,calDate,dropDown2,stxtErrMsg"/>
                        </h:panelGroup> 
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
