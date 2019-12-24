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
            <title>Unclaimed Account Detail Report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
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
            <h:form id="loanPeriod">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{UnclaimedAccountStatement.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Unclaimed Account Detail Report"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{UnclaimedAccountStatement.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{UnclaimedAccountStatement.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel1" columns="6" columnClasses="col3,col3,col3,col3,col3,col3"  styleClass="row2" width="100%">
                        <h:outputText value="Branch Wise:" styleClass="label"/>
                        <h:selectOneListbox id="branch" size="1" value="#{UnclaimedAccountStatement.branchCode}" styleClass="ddlist">
                            <f:selectItems value="#{UnclaimedAccountStatement.branchCodeList}" />
                        </h:selectOneListbox> 
                        <h:outputText value="A/c Nature"styleClass="label" />
                        <h:selectOneListbox id="ddacNature" size="1" value="#{UnclaimedAccountStatement.acNature}" styleClass="ddlist" style="width: 70px" >
                            <f:selectItems value="#{UnclaimedAccountStatement.acNatureList}"/>
                            <a4j:support  action="#{UnclaimedAccountStatement.getAcTypeByAcNature}" event="onchange"
                                          reRender="ddacType" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblacType" styleClass="label"  value="A/c Type"/>
                        <h:selectOneListbox id="ddacType" styleClass="ddlist" size="1" style="width: 70px" value="#{UnclaimedAccountStatement.acType}" >
                            <f:selectItems value="#{UnclaimedAccountStatement.acTypeList}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="panel2" columns="6" columnClasses="col3,col3,col3,col3,col3,col3"  styleClass="row1" width="100%">
                        <h:outputText value="From Year" styleClass="label" />
                        <h:inputText id="txtFormYear" value="#{UnclaimedAccountStatement.frYear}" size="12" styleClass="input" style="width: 70px"/>
                        <h:outputText value="To Year" styleClass="label" />
                        <h:inputText id="txtToYear" value="#{UnclaimedAccountStatement.toYear}" size="12" styleClass="input" style="width: 70px"/>

                        <h:outputText value="As On Date" styleClass="label"/>
                        <h:panelGroup styleClass="label">
                            <h:inputText id="txtFrmDate" styleClass="input calInstDate" style="setMask();width:70px;"  value="#{UnclaimedAccountStatement.asOnDate}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="panel3" columns="6" columnClasses="col3,col3,col3,col3,col3,col3"  styleClass="row2" width="100%">
                        <h:outputLabel value="Report Type" styleClass="label"/>
                        <h:selectOneListbox id="ddReportType" value="#{UnclaimedAccountStatement.reportType}" styleClass="ddlist" size="1" style="width:70px">
                            <f:selectItems value="#{UnclaimedAccountStatement.reportTypeList}"/> 
                        </h:selectOneListbox>
                        <h:outputLabel id="roiLabel" value="Saving Roi" styleClass="label" style="display:#{UnclaimedAccountStatement.roiVisible}"/>
                        <h:inputText id="roiTxt" styleClass="input" value="#{UnclaimedAccountStatement.savingRoi}" 
                                     style="display:#{UnclaimedAccountStatement.roiVisible}" maxlength="5" size="5"/>
                        <h:outputText/>
                        <h:outputText/>
                    </h:panelGrid>
                    <h:panelGrid id="panel4" columns="2" columnClasses="col2,col6" styleClass="row1" width="100%">
                        <h:outputLabel styleClass="label" style="width:100px;color:blue" value="Un-Claimed Marking Condition-->"/>
                        <h:outputText id="markNote" styleClass="output" style="width:100px;color:blue" value="Marking will allow only for a single branch, a single A/c Type and Deatil Report Type."/>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                        <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnPrint" value="Print Report" action="#{UnclaimedAccountStatement.viewReport}" reRender="errorMsg,btnUnclaimedMark,roiLabel,roiTxt"/>
                            <a4j:commandButton id="btnUnclaimedMark" value="Mark Unclaimed" oncomplete="#{rich:component('postPanel')}.show();" disabled="true" reRender="errorMsg,postPanel,roiLabel,roiTxt" />
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{UnclaimedAccountStatement.refresh}" reRender="a1" oncomplete="setMask();"/>
                            <a4j:commandButton id="btnClose" value="Exit" action="#{UnclaimedAccountStatement.exitAction}" reRender="errorMsg"/>
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
            <a4j:region id="btnActionGrid">
                <rich:modalPanel id="postPanel" autosized="true" width="250" onshow="#{rich:element('btnSaveNo')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog" />
                    </f:facet>
                    <h:form>
                        <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                        <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <f:facet name="header">
                                <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                            </f:facet>
                        </rich:modalPanel>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText value="Are you sure to mark unclaimed?"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="btnSaveYes" action="#{UnclaimedAccountStatement.markUnClaimedAction}" 
                                                           oncomplete="#{rich:component('postPanel')}.hide();" reRender="errorMsg,btnUnclaimedMark"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="No" id="btnSaveNo" onclick="#{rich:component('postPanel')}.hide();"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
            </a4j:region>
        </body>
    </html>
</f:view>
