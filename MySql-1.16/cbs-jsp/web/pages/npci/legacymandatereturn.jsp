<%-- 
    Document   : legacymandatereturn
    Created on : 3 Apr, 2017, 5:29:15 PM
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
            <title>Legacy Mandate File</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{legacyMandateReturnController.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Legacy Mandate File"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{legacyMandateReturnController.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="msggrid" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7;border:1px ridge #BED6F8;">
                        <h:outputText id="stxtMessage" styleClass="error" value="#{legacyMandateReturnController.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel3" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblFileType" styleClass="label" value="File Type"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddFileType" styleClass="ddlist" size="1" style="width:80px" value="#{legacyMandateReturnController.fileType}">
                            <f:selectItems value="#{legacyMandateReturnController.fileTypeList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblMmsType" styleClass="label" value="Mandate Type"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddMmsType" styleClass="ddlist" size="1" style="width:80px" value="#{legacyMandateReturnController.mmsType}">
                            <f:selectItems value="#{legacyMandateReturnController.mmsTypeList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblRetFileDt" styleClass="label" value="Date"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtRetFileDt" styleClass="input issuedt" style="width:70px" value="#{legacyMandateReturnController.returnDate}">
                            <a4j:support event="onblur" action="#{legacyMandateReturnController.returnDateAction}" 
                                         reRender="stxtMessage,tablePanel,taskList" oncomplete="setMask();"/>
                        </h:inputText> 
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <%--a4j:commandButton id="btnProcess" value="Generate File" oncomplete="#{rich:component('processPanel')}.show()" 
                                               reRender="message,processPanel"/--%>
                            <h:commandButton id="btnPrint" value="Download Excel" action="#{legacyMandateReturnController.processAction}"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{legacyMandateReturnController.refreshForm}" reRender="mainPanel" oncomplete="setMask();"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{legacyMandateReturnController.exitForm}" reRender="mainPanel" oncomplete="setMask();"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                        <a4j:region id="tblRegion">
                            <rich:dataTable value="#{legacyMandateReturnController.gridDetail}" var="dataItem" 
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="5" 
                                            columnsWidth="100" rowKeyVar="row" 
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'" 
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" 
                                            width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="13"><h:outputText value="Mandate Detail" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="UMRN" /></rich:column>
                                        <rich:column><h:outputText value="A/c Type" /></rich:column>
                                        <rich:column><h:outputText value="A/c No" /></rich:column>
                                        <rich:column><h:outputText value="A/c Name" /></rich:column>
                                        <rich:column><h:outputText value="Frequency" /></rich:column>
                                        <rich:column><h:outputText value="First Collection Date" /></rich:column>
                                        <rich:column><h:outputText value="Final Collection Date" /></rich:column>
                                        <rich:column><h:outputText value="Collection Amount" /></rich:column>
                                        <rich:column><h:outputText value="Maximum Amount" /></rich:column>
                                        <rich:column><h:outputText value="Debtor IFSC/MICR/IIN" /></rich:column>
                                        <rich:column><h:outputText value="Debtor Bank Name" /></rich:column>
                                        <rich:column><h:outputText value="Creditor Name" /></rich:column>
                                        <rich:column><h:outputText value="Reject Reason" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.mndtId}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.dbtrAcctTpPrtry}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.dbtrAcctIdOthrId}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.dbtrNm}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.ocrncsFrqcy}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.ocrncsFrstColltnDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.ocrncsFnlColltnDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.colltnAmt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.maxAmt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.dbtrAgtFinInstnIdClrSysMmbIdMmbId}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.dbtrAgtFinInstnIdNm}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.cdtrNm}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.rejectCode}" /></rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="10"/>
                        </a4j:region>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:region id="processActionRegion">
                <rich:modalPanel id="processPanel" autosized="true" width="250" onshow="#{rich:element('btnYes')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText id="confirmid" value="Are you sure to generate it ?"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="btnYes" action="#{legacyMandateReturnController.processAction}" 
                                                           onclick="#{rich:component('processPanel')}.hide();" 
                                                           reRender="mainPanel" oncomplete="setMask()"/>
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
        </body>
    </html>
</f:view>
