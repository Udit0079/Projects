<%-- 
    Document   : printadvice
    Created on : 23 Nov, 2016, 11:58:53 AM
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Print Advice Matching</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
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
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="gridPanel1" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{printAdviceBean.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Print Advice Matching"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{printAdviceBean.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanelMsg" columnClasses="col8,col8" columns="2" style="width:100%;text-align:center;" 
                                 styleClass="row2" width="100%">
                        <h:outputText id="stxtMsg" styleClass="error" value="#{printAdviceBean.errorMessage}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel2" columns="4" columnClasses="col13,col13,col13,col13" style="height:30px;" 
                                 styleClass="row1" width="100%">
                        <h:outputLabel id="lblDt" style="width:80%" styleClass="label" value="Date"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:inputText id="txtDt" styleClass="input issueDt" style="width:70px" value="#{printAdviceBean.exactDt}" maxlength="10">
                            <a4j:support event="onblur" action="#{printAdviceBean.exactDtProcess}" reRender="stxtMsg,fileDetailGrid,detailList" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel3" columns="4" columnClasses="col13,col13,col13,col13" style="height:30px;display:#{printAdviceBean.displayFlag}" 
                                 styleClass="row2" width="100%">
                        <h:outputLabel id="lblFileMessageId" styleClass="label" value="File Message Id"></h:outputLabel>
                        <h:outputText id="stxtFileMessageId" styleClass="output" value="#{printAdviceBean.paymentMessageId}"/>
                        <h:outputLabel id="lblBatchNo" styleClass="label" value="Batch No"></h:outputLabel>
                        <h:outputText id="stxtBatchNo" styleClass="output" value="#{printAdviceBean.batchNo}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel4" columns="4" columnClasses="col13,col13,col13,col13" style="height:30px;display:#{printAdviceBean.displayFlag}" 
                                 styleClass="row1" width="100%">
                        <h:outputLabel id="lblTotalDebit" styleClass="label" value="Total Debit"></h:outputLabel>
                        <h:outputText id="stxtTotalDebit" styleClass="output" value="#{printAdviceBean.totalDebit}"/>
                        <h:outputLabel id="lblTotalCredit" styleClass="label" value="Total Credit"></h:outputLabel>
                        <h:outputText id="stxtTotalCredit" styleClass="output" value="#{printAdviceBean.totalCredit}"/>
                    </h:panelGrid>
                    <h:panelGrid id="fileDetailGrid" width="100%" style="background-color:#edebeb;height:200px;" columnClasses="vtop">
                        <rich:dataTable  value="#{printAdviceBean.dataList}" var="item"
                                         rowClasses="gridrow1, gridrow2" id="detailList" rows="5" columnsWidth="100"
                                         onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                         onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                         width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="7"><h:outputText value="Batch Detail"/></rich:column>
                                    <rich:column breakBefore="true"><h:outputText value="Message Id" /></rich:column>
                                    <rich:column><h:outputText value="Batch No" /></rich:column>
                                    <rich:column><h:outputText value="Total Debit" /></rich:column>
                                    <rich:column><h:outputText value="Total Credit" /></rich:column>
                                    <rich:column><h:outputText value="Select"/></rich:column>
                                    <rich:column><h:outputText value="Show"/></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{item.messageId}"/></rich:column>
                            <rich:column><h:outputText value="#{item.batchNo}"/></rich:column>
                            <rich:column><h:outputText value="#{item.totalDebit}"/></rich:column>
                            <rich:column><h:outputText value="#{item.totalCredit}"/></rich:column>
                            <rich:column style="text-align:center;width:40px">
                                <a4j:commandLink id="selectlink" action="#{printAdviceBean.setDataToProcess}" 
                                                 reRender="stxtMsg,stxtFileMessageId,stxtBatchNo,stxtTotalDebit,stxtTotalCredit,gridPanel3,gridPanel4">
                                    <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                    <f:setPropertyActionListener value="#{item}" target="#{printAdviceBean.currentItem}"/>                                    
                                </a4j:commandLink>
                            </rich:column>
                            <rich:column style="text-align:center;width:40px">
                                <a4j:commandLink id="showlink" action="#{printAdviceBean.showBatchDetail}" 
                                                 oncomplete="#{rich:component('batchView')}.show();" reRender="stxtMsg,batchView" >
                                    <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                    <f:setPropertyActionListener value="#{item}" target="#{printAdviceBean.currentItem}"/>                                    
                                </a4j:commandLink>
                            </rich:column>
                        </rich:dataTable>
                        <rich:datascroller id="scroller" align="left" for="detailList" maxPages="20"/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup  style="width:100%;text-align:center;" styleClass="vtop">
                            <a4j:commandButton id="btnProcess" value="Process" oncomplete="#{rich:component('processPanel')}.show();setMask();" reRender="stxtMsg,processPanel"/>
                            <a4j:commandButton id="btnReturn" value="Return" oncomplete="#{rich:component('returnPanel')}.show();setMask();" reRender="stxtMsg,returnPanel"></a4j:commandButton>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{printAdviceBean.refreshForm}" reRender="mainPanel" oncomplete="setMask();"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{printAdviceBean.exitForm}" reRender="mainPanel" oncomplete="setMask();"/>
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
                                        <h:outputText value="Are you sure to process ?"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="btnYes" action="#{printAdviceBean.processButtonAction}" 
                                                           onclick="#{rich:component('processPanel')}.hide();" 
                                                           reRender="stxtMsg,mainPanel" oncomplete="setMask();"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('processPanel')}.hide();"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
                <rich:modalPanel id="returnPanel" autosized="true" width="250" onshow="#{rich:element('retBtnNo')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText value="Are you sure to return ?"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="retBtnYes" action="#{printAdviceBean.processReturnAction}" 
                                                           onclick="#{rich:component('returnPanel')}.hide();" 
                                                           reRender="stxtMsg,mainPanel" oncomplete="setMask();"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="No" id="retBtnNo" onclick="#{rich:component('returnPanel')}.hide();"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
                <rich:modalPanel id="batchView" height="560" width="900" left="true">
                    <f:facet name="header">
                        <h:outputText value="Batch Detail"/>
                    </f:facet>
                    <f:facet name="controls">
                        <h:panelGroup>
                            <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="closelink"/>
                            <rich:componentControl for="batchView" attachTo="closelink" operation="hide" event="onclick"/>
                        </h:panelGroup>
                    </f:facet>
                    <a4j:form>
                        <h:panelGrid id="batchViewPanel" width="100%">
                            <h:panelGrid id="batchViewGridPanel1" columns="4" columnClasses="col13,col13,col13,col13" style="height:30px;" 
                                         styleClass="row2" width="100%">
                                <h:outputLabel id="lblViewFileMessageId" styleClass="label" value="File Message Id"></h:outputLabel>
                                <h:outputText id="stxtViewFileMessageId" styleClass="output" value="#{printAdviceBean.viewPaymentMessageId}"/>
                                <h:outputLabel id="lblViewBatchNo" styleClass="label" value="Batch No"></h:outputLabel>
                                <h:outputText id="stxtViewBatchNo" styleClass="output" value="#{printAdviceBean.viewBatchNo}"/>
                            </h:panelGrid>
                            <h:panelGrid id="batchViewGridPanel2" columns="4" columnClasses="col13,col13,col13,col13" style="height:30px;" 
                                         styleClass="row1" width="100%">
                                <h:outputLabel id="lblViewTotalDebit" styleClass="label" value="Total Debit"></h:outputLabel>
                                <h:outputText id="stxtViewTotalDebit" styleClass="output" value="#{printAdviceBean.viewTotalDebit}"/>
                                <h:outputLabel id="lblViewTotalCredit" styleClass="label" value="Total Credit"></h:outputLabel>
                                <h:outputText id="stxtViewTotalCredit" styleClass="output" value="#{printAdviceBean.viewTotalCredit}"/>
                            </h:panelGrid>
                            <h:panelGrid id="batchViewGridPanel3" width="100%" style="background-color:#e8eef7;height:260px" columnClasses="vtop">
                                <rich:dataTable  var="viewItem" value="#{printAdviceBean.batchDetailList}"
                                                 rowClasses="gridrow1, gridrow2" id="batchViewList" rows="8"
                                                 onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                 onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                                 width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="4"><h:outputText value="Batch Detail" /></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="A/c No" /></rich:column>
                                            <rich:column><h:outputText value="Name" /></rich:column>
                                            <rich:column><h:outputText value="Txn Type" /></rich:column>
                                            <rich:column><h:outputText value="Amount" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{viewItem.acno}"/></rich:column>
                                    <rich:column><h:outputText value="#{viewItem.name}"/></rich:column>
                                    <rich:column><h:outputText value="#{viewItem.accountType}"/></rich:column>
                                    <rich:column><h:outputText value="#{viewItem.amount}"/></rich:column>
                                </rich:dataTable>
                                <rich:datascroller align="left" for="batchViewList" maxPages="20"/>
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid id="batchViewFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup id="batchViewBtnPanel">
                                <a4j:commandButton id="batchViewClose" value="Close" onclick="#{rich:component('batchView')}.hide(); return false;"/>
                            </h:panelGroup>
                        </h:panelGrid>
                    </a4j:form>
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

