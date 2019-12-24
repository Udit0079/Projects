<%-- 
    Document   : npcioacverification
    Created on : 25 Nov, 2015, 6:10:06 PM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>OAC Verification</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".issuedt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="gridPanel1" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{npciOacVerification.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="OAC Verification"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{npciOacVerification.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel2" columnClasses="col8,col8" columns="2" style="width:100%;text-align:center;" styleClass="row2" width="100%">
                        <h:outputText id="stxtMsg" styleClass="error" value="#{npciOacVerification.errMessage}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel3" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblBranch" styleClass="label" value="Branch"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddBranch" styleClass="ddlist" size="1" style="width:80px" value="#{npciOacVerification.branch}">
                            <f:selectItems value="#{npciOacVerification.branchList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblFileUpDt" styleClass="label" value="File Upload Date"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtFileUpDt" styleClass="input issuedt" style="width:70px" value="#{npciOacVerification.fileUpDt}">
                            <a4j:support event="onblur" action="#{npciOacVerification.fileUpDtProcess}" reRender="stxtMsg,ddSeqNo" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel id="lblSeqNo" styleClass="label" value="Seq No"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddSeqNo" styleClass="ddlist" size="1" style="width:80px" value="#{npciOacVerification.seqNo}">
                            <f:selectItems value="#{npciOacVerification.seqNoList}"/>
                            <a4j:support event="onblur" action="#{npciOacVerification.retrieveProcess}" reRender="stxtMsg,mainPanel" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel4" columns="4" columnClasses="col13,col13,col13,col13" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblOldAcno" styleClass="label" value="Old A/c No."></h:outputLabel>
                        <h:outputText id="stxtOldAcno" styleClass="output" value="#{npciOacVerification.oldAcno}"/>
                        <h:outputLabel id="lblOldAcName" styleClass="label" value="Old A/c Name"></h:outputLabel>
                        <h:outputText id="stxtOldAcName" styleClass="output" value="#{npciOacVerification.oldAcName}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel5" columns="4" columnClasses="col13,col13,col13,col13" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblMicr" styleClass="label" value="Micr Code"></h:outputLabel>
                        <h:outputText id="stxtMicr" styleClass="output" value="#{npciOacVerification.micr}"/>
                        <h:outputLabel id="lblAcType" styleClass="label" value="A/c Type"></h:outputLabel>
                        <h:outputText id="stxtAcType" styleClass="output" value="#{npciOacVerification.acType}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel6" columns="4" columnClasses="col13,col13,col13,col13" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblRrn" styleClass="label" value="RRN"></h:outputLabel>
                        <h:outputText id="stxtRrn" styleClass="output" value="#{npciOacVerification.stRrn}"/>
                        <h:outputLabel id="lblAcValidFlag" styleClass="label" value="A/c Valid Flag"></h:outputLabel>
                        <h:outputText id="stxtAcValidFlag" styleClass="output" value="#{npciOacVerification.acValidFlag}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel7" columns="4" columnClasses="col13,col13,col13,col13" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblAcno" styleClass="label" value="A/c No"></h:outputLabel>
                        <h:inputText id="txtAcno" styleClass="input" value="#{npciOacVerification.acno}" maxlength="12" size="10">
                            <a4j:support event="onblur" action="#{npciOacVerification.retrieveValidAcno}" reRender="stxtMsg,stxtName" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel id="lblName" styleClass="label" value="Name"></h:outputLabel>
                        <h:outputText id="stxtName" styleClass="output" value="#{npciOacVerification.name}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel8" columns="4" columnClasses="col13,col13,col13,col13" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblAcValid" styleClass="label" value="A/c Valid"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddAcValid" styleClass="ddlist" size="1" style="width:80px" value="#{npciOacVerification.acValid}">
                            <f:selectItems value="#{npciOacVerification.acValidList}"/>
                            <a4j:support event="onblur" action="#{npciOacVerification.acValSelectProcess}" reRender="stxtMsg,ddReason" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblReason" styleClass="label" value="Reason"/>
                        <h:selectOneListbox id="ddReason" styleClass="ddlist" size="1" style="width:80px" 
                                            value="#{npciOacVerification.reason}" disabled="#{npciOacVerification.reasonDisable}">
                            <f:selectItems value="#{npciOacVerification.reasonList}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;" styleClass="row1" width="100%">
                        <a4j:region id="tblRegion">
                            <rich:dataTable value="#{npciOacVerification.gridDetail}" var="dataItem" 
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="5" 
                                            columnsWidth="100" rowKeyVar="row" 
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'" 
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" 
                                            width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="10"><h:outputText value="OAC Details" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Originator Code" /></rich:column>
                                        <rich:column><h:outputText value="Responder Code" /></rich:column>
                                        <rich:column><h:outputText value="RRN" /></rich:column>
                                        <rich:column><h:outputText value="Old A/c No" /></rich:column>
                                        <rich:column><h:outputText value="Old A/c Name" /></rich:column>
                                        <rich:column><h:outputText value="A/c Valid Flag" /></rich:column>
                                        <rich:column><h:outputText value="Reason Code" /></rich:column>
                                        <rich:column><h:outputText value="New A/c No" /></rich:column>
                                        <rich:column><h:outputText value="New A/c Name" /></rich:column>
                                        <rich:column><h:outputText value="Select"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.orgCode}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.resCode}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.rrn}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.oldAcno}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.oldAcName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.acValFlag}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.returnCode}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.cbsAcno}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.cbsName}" /></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{npciOacVerification.setTableDataToForm}" reRender="mainPanel">
                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{npciOacVerification.currentItem}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="10"/>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup  style="width:100%;text-align:center;" styleClass="vtop">
                            <a4j:commandButton id="btnSave" value="Verify" oncomplete="#{rich:component('processPanel')}.show()" reRender="stxtMsg,processPanel"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{npciOacVerification.refreshForm}" reRender="mainPanel" oncomplete="setMask();"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{npciOacVerification.exitForm}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:region id="processActionRegion">
                <rich:modalPanel id="processPanel" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText id="confirmid" value="Are you sure to verify it ?"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="btnYes" action="#{npciOacVerification.processAction}" onclick="#{rich:component('processPanel')}.hide();" 
                                                           reRender="stxtMsg,mainPanel,tablePanel,taskList" oncomplete="setMask();"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('processPanel')}.hide();"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
            </a4j:region>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="processActionRegion"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
