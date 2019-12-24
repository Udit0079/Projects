<%--
    Document   : InterestCalculation
    Created on : 03 Apr, 2012, 3:06:48 PM
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>SB Interest Calculation</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".calInstDate").mask("39/19/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="SbIntCalAll">
                <h:panelGrid  columns="1" id="outerPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{sbInterestReport.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="SB Interest Report"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{sbInterestReport.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                        <h:panelGrid columnClasses="col2,col7" columns="2" id="msgRow" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputLabel id="lblMsg" styleClass="error" value="#{sbInterestReport.message}"/>
                        </h:panelGrid>

                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row2" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblBranchWise" styleClass="label" value="Interest Option : "><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddBranch" styleClass="ddlist" size="1" style="width:150px;" value="#{sbInterestReport.intOpt}">
                                    <f:selectItems value="#{sbInterestReport.intOptionList}"/>
                                    <a4j:support  action="#{sbInterestReport.setIntOptions}" event="onblur"
                                                  reRender="lblMsg,Row12" focus="#{sbInterestReport.focusEle}"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblAccountType" styleClass="label" value="Account Type :" ><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddAccountType" styleClass="ddlist" size="1" style="width: 70px"  value="#{sbInterestReport.accountType}" >
                                    <f:selectItems value="#{sbInterestReport.accountTypeList}"/>
                                    <a4j:support  event="onblur" reRender="msgRow,Row12" focus="ddAcctStatus"/>
                                </h:selectOneListbox>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row12" style="width:100%;text-align:left;" styleClass="row1">    
                                <h:outputLabel id="lblAccountStatus" styleClass="label" value="Account Status : "><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddAcctStatus" styleClass="ddlist" size="1" style="width:100px;"  value="#{sbInterestReport.acctStatus}">
                                    <f:selectItems value="#{sbInterestReport.acctStatusList}"/>
                                    <a4j:support action="#{sbInterestReport.getIntPostingDate}" event="onblur" reRender="msgRow,Row12" focus="#{sbInterestReport.focusEle}"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblToDate" styleClass="label" value="Interest Posting Date:"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="calToDate" styleClass="input calInstDate"  maxlength="10" style="width:70px" value="#{sbInterestReport.postingDate}">
                                    <f:convertDateTime pattern="dd/MM/yyyy" />
                                </h:inputText>
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid id="poFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup id="btnPanel">
                                <a4j:region id="btnPnl">
                                    <a4j:commandButton id="btnPrint" value="Print Report" action="#{sbInterestReport.interestReport}" reRender="msgRow,Row2,Row12,popUpRepPanel" 
                                                       oncomplete="if(#{sbInterestReport.reportFlag}){#{rich:component('popUpRepPanel')}.show();} else {#{rich:component('popUpRepPanel')}.hide();}"/>
                            
                                    <h:commandButton id="btnDownload"  value="PDF Download" action="#{sbInterestReport.btnPdfAction}"  styleClass="color:#044F93;text-decoration:none;" />
                                    
                                    <a4j:commandButton id="btnRefresh" value="Refresh" action="#{sbInterestReport.refresh}" reRender="lblMsg,leftPanel,calToDate" focus="ddBranch"/>
                                    <a4j:commandButton id="btnExit" value="Exit" action="#{sbInterestReport.exitBtnAction}"/>
                                </a4j:region>
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                    <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="btnPnl"/>
                    <rich:modalPanel id="wait" autosized="true" width="250" height="60" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;" >
                        <f:facet name="header">
                            <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                        </f:facet>
                        <h:outputText value="It may take some time. Please Wait..." />
                    </rich:modalPanel>
                </a4j:form>

            <rich:modalPanel id="popUpRepPanel" label="Form" width="1000" height="550" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpRepPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="SB Interest Report" style="text-align:center;"/>
                    </h:panelGrid>
                </f:facet>
                <f:facet name="controls">
                    <h:outputLink  id="closelink" onclick="#{rich:component('popUpRepPanel')}.hide()">
                        <h:graphicImage value="/resources/images/close.gif" style="border:0" />
                    </h:outputLink>
                </f:facet>  
                <table width="100%">
                    <tbody>
                        <tr>
                            <td>
                                <a4j:include viewId="#{sbInterestReport.viewID}" />
                            </td>
                        </tr>
                    </tbody>
                </table>
            </rich:modalPanel>            
        </body>
    </html>
</f:view>