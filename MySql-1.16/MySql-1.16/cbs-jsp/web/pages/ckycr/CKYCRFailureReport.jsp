<%-- 
    Document   : CKYCRFailureReport
    Created on : 10 Feb, 2017, 12:16:05 PM
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
            <title>CKYCR Report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".fromDate").mask("99/99/9999");
                    jQuery(".toDate").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <h:form id="ckycrFailureReport" enctype="multipart/form-data">
                <h:panelGrid id="mainPanel" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{cKYCRFailureReportController.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="CKYCR Report"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{cKYCRFailureReportController.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="msgLabelId" style="height:25px;text-align:center" styleClass="#{cKYCRFailureReportController.msgStyle}" width="100%">
                        <h:outputText id="msgId" value="#{cKYCRFailureReportController.msg}"/>
                    </h:panelGrid>
                   
                    <h:panelGrid columnClasses="col2,col2,col2,col2,col2,col2" columns="6" id="gridPanel1" width="100%" styleClass="row2">
                        <h:outputLabel value="Branch" id="branchLabel" styleClass="label"/>
                        <h:selectOneListbox id="branchId" value="#{cKYCRFailureReportController.branch}" styleClass="ddlist" size="1"  style="width:200px" >
                            <f:selectItems value="#{cKYCRFailureReportController.branchList}"/>
                        </h:selectOneListbox>

                        <h:outputLabel value="Mode" id="modeLabel" styleClass="label"/>
                        <h:selectOneListbox id="modeId" value="#{cKYCRFailureReportController.mode}"  styleClass="ddlist" size="1"  style="width:100px" >
                            <f:selectItems value="#{cKYCRFailureReportController.modeList}" />
                            <a4j:support  action="#{cKYCRFailureReportController.onChangeMode()}" event="onblur"  reRender="reportTypeId,get,pritReport,msgId,msgLabelId,txnGrid"/>
                        </h:selectOneListbox>

                        <h:outputLabel value="Report Type" id="reportLabel" styleClass="label"/>
                        <h:selectOneListbox id="reportTypeId" value="#{cKYCRFailureReportController.reportType}"  styleClass="ddlist" size="1"  style="width:100px" >
                            <f:selectItems value="#{cKYCRFailureReportController.reportTypeList}" />
                            <a4j:support  action="#{cKYCRFailureReportController.validation}" event="onchange"  reRender="get,pritReport,msgId,msgLabelId,txnGrid"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col4,col4" columns="6" id="gridPanel2" width="100%" styleClass="row1">
                        <h:outputLabel value="From Date" styleClass="label"/>
                        <h:inputText id="fromDate" styleClass="input fromDate" maxlength="10"   value="#{cKYCRFailureReportController.fromDate}" style="width:96px;setMask();" >
                        </h:inputText>
                        <h:outputLabel value="To Date" styleClass="label"/>
                        <h:inputText id="toDate" styleClass="input toDate" maxlength="10"  value="#{cKYCRFailureReportController.toDate}" style="width:99px;setMask();" >
                        </h:inputText>   
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="txnGrid" width="100%" style="background-color:#edebeb;height:200px;" columnClasses="vtop">
                        <rich:dataTable  value="#{cKYCRFailureReportController.customerFailureList}" var="item" 
                                         rowClasses="gridrow1, gridrow2" id="txnList" rows="5" columnsWidth="100"
                                         onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                         onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                         width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="11"><h:outputText value="CKYCR Failure Deatil"/></rich:column>
                                    <rich:column breakBefore="true" width="5"><h:outputText value="SNo."/></rich:column>
                                    <rich:column width="150"><h:outputText value="Customer Id" /></rich:column>
                                    <rich:column width="400"><h:outputText value="Customer Name" /></rich:column>
                                    <rich:column width="100"><h:outputText value="Branch Name" /></rich:column>
                                    <rich:column width="100"><h:outputText value="Select" /></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{item.sno}"/></rich:column> <%--value="#{item.sno}" --%>
                            <rich:column><h:outputText value="#{item.customerId}"/></rich:column>
                            <rich:column><h:outputText value="#{item.customerName}"/></rich:column>
                            <rich:column><h:outputText value="#{item.branchName}"/></rich:column>
                            <rich:column><h:selectBooleanCheckbox  id="c1"  value="#{item.checkBox}">
                                    <a4j:support actionListener="#{cKYCRFailureReportController.viewReport}" event="onclick" oncomplete="#{rich:component('popUpRepPanel')}.show();
                                                 " reRender="txnGrid,popUpRepPanel"/> 
                                </h:selectBooleanCheckbox>
                            </rich:column>
                        </rich:dataTable>
                        <rich:datascroller id="deafDataScroll"align="left" for="txnList" maxPages="20"/>
                    </h:panelGrid>
                    <h:panelGrid columns="2" id="a6" style="height:30px;" styleClass="footer" columnClasses="col7,col7,col7" width="100%">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="get" value="Get Failure" action="#{cKYCRFailureReportController.FailureCustomerDetail}" oncomplete="setMask();" disabled="#{cKYCRFailureReportController.btnGetDisable}"  reRender="modeId,get,errorMsg,successMsg,fromDate,toDate,branchId,txnGrid,msgId,msgLabelId"/>
                            <a4j:commandButton id="pritReport" value="Print Report" action="#{cKYCRFailureReportController.FailureCustomerDetail}" oncomplete="if(#{cKYCRFailureReportController.msg == ''}){
                                               #{rich:component('popUpRepPanel')}.show();setMask();
                                               }else{
                                               #{rich:component('popUpRepPanel')}.hide();setMask();
                                               }" disabled="#{cKYCRFailureReportController.btnPrintReportDisable}"  reRender="modeId,get,fromDate,toDate,branchId,txnGrid,msgId,msgLabelId,popUpRepPanel"/>
                            
                            <h:commandButton id="btnPDF" value="PDF Download" actionListener="#{cKYCRFailureReportController.viewPdfReport}" styleClass="color: #044F93;text-decoration: none;"/>
                            <h:commandButton id="btnExcel" value="Excel Download" actionListener="#{cKYCRFailureReportController.viewExcelReport}" styleClass="color: #044F93;text-decoration: none;"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{cKYCRFailureReportController.refersh}" oncomplete="setMask();" reRender="modeId,get,fromDate,toDate,branchId,txnGrid,msgId,msgLabelId,pritReport"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{cKYCRFailureReportController.exitForm}"/>

                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </h:form>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
            <rich:modalPanel id="popUpRepPanel" label="Form" width="1000" height="550" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpRepPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="#{cKYCRFailureReportController.reportName}" style="text-align:center;"/> 
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
                                <a4j:include viewId="#{cKYCRFailureReportController.viewID}" />
                            </td>

                        </tr>
                    </tbody>
                </table>
            </rich:modalPanel> 
        </body>
    </html>
</f:view>