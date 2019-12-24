<%-- 
    Document   : shareissue
    Created on : 26 Aug, 2011, 11:14:22 AM
    Author     : Zeeshan Waris
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Share Issue</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery){
                    setMask();
                    setTimeMask();
                });
                var row;
                function setMask(){
                    jQuery(".calInstDate").mask("39/19/9999");
                }
                function setTimeMask(){
                    jQuery(".calInstTime").mask("99:99");
                }
            </script>
        </head>
        <body>
            <a4j:keepAlive beanName="ShareIssue"/>
            <a4j:form id="ShareIssue">
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{ShareIssue.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Share Issue"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ShareIssue.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                        <h:panelGrid columns="2" id="Panel790" style="width:100%;height:30px;text-align:center;" styleClass="row1">
                            <h:outputText id="lblMsg" styleClass="error" value="#{ShareIssue.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row1" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel styleClass="label"/>
                            <h:outputLabel styleClass="label"/>
                            <h:outputLabel id="lblShareIssueForm" styleClass="label" value="Share Issue From"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtShareIssueForm" styleClass="input" maxlength="20" value="#{ShareIssue.shareIssueFrom}" style="width:100px;" onkeyup="this.value = this.value.toUpperCase();" disabled="true">
                            </h:inputText>
                            <h:outputLabel styleClass="label"/>
                            <h:outputLabel styleClass="label"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row2" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel styleClass="label"/>
                            <h:outputLabel styleClass="label"/>
                            <h:outputLabel id="lblNoOfShareIssue" styleClass="label" value="No.Of Share Issue"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtNoOfShareIssue" styleClass="input" maxlength="14" value="#{ShareIssue.noOfShareIssue}" style="width:100px;" onkeyup="this.value = this.value.toUpperCase();">
                            </h:inputText>
                            <h:outputLabel styleClass="label"/>
                            <h:outputLabel styleClass="label"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row3" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel styleClass="label"/>
                            <h:outputLabel styleClass="label"/>
                            <h:outputLabel id="lblIssueDate" styleClass="label" value="Issue Date"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:panelGroup id="groupPanelIssueDate" layout="block">
                                <h:inputText id="calIntDateOfBirth" styleClass="input calInstDate"  style="width:70px;setMask()" maxlength="10"  value="#{ShareIssue.issueDate}">
                                    <f:convertDateTime pattern="dd/MM/yyyy" />
                                </h:inputText>
                                <h:outputLabel id="lblDateofbirthStyle" styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                            </h:panelGroup>
                            <h:outputLabel styleClass="label"/>
                            <h:outputLabel styleClass="label"/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="BtnPanel">
                            <a4j:commandButton id="btnSave" value="Save"  action="#{ShareIssue.saveBtnAction}"
                                               oncomplete="#{rich:element('calIntDateOfBirth')}.style=setMask();"
                                               reRender="mainPanel,lblMsg" focus="btnExit"/>
                            <a4j:commandButton  id="btnrefresh"  value="Refresh" action="#{ShareIssue.btnRefresh}" 
                                                oncomplete="#{rich:element('calIntDateOfBirth')}.style=setMask();"
                                                reRender="mainPanel" />
                            <a4j:commandButton id="btnExit" value="Exit" action="#{ShareIssue.btnExit}" reRender="leftPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>