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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>Online Aadhar Updation</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
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
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1"  style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="headerPanel" columns="3"  style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup>
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{OnlineAadharUpdation.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="Online Aadhar Updation"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{OnlineAadharUpdation.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="messagePanel" style="width:100%;text-align:center;" 
                                 styleClass="row1" width="100%">
                        <h:outputText id="stxtMessage" styleClass="error" value="#{OnlineAadharUpdation.errorMessage}"/>
                    </h:panelGrid>
                    <h:panelGrid id="txnPanelGrid" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;"  styleClass="row2" width="100%">
                        <h:outputLabel id="lblFrDt" styleClass="label" value="From Date"/>
                        <h:inputText id="frDate" size="10" styleClass="input issueDt" value="#{OnlineAadharUpdation.frDt}" ></h:inputText>
                        <h:outputLabel id="lblToDt" styleClass="label" value="To Date"/>
                        <h:inputText id="toDate" size="10" styleClass="input issueDt" value="#{OnlineAadharUpdation.toDt}" ></h:inputText>
                        <h:outputLabel id="lblRecord" value="No of Record" styleClass="label"/>
                        <h:inputText id="txtRecord" value="#{OnlineAadharUpdation.noOfRecord}" styleClass="input" maxlength="15" size="11">
                            <a4j:support event="onblur" action="#{OnlineAadharUpdation.setAadharData}"  reRender="stxtMessage,tablePanel,taskList"/> 
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;" styleClass="row1" width="100%">
                        <rich:extendedDataTable enableContextMenu="false" id="taskList" width="100%" 
                                                value="#{OnlineAadharUpdation.gridDetail}" styleClass="gridrow1,gridrow2,noTableBorder"
                                                height="231px" var="dataItem" rowKeyVar="row" 
                                                noDataLabel="No Record Found">
                            <f:facet name="header">
                                <h:outputText value="Entry Detail"/>
                            </f:facet>
                            <rich:column width="6%" style="text-align:center">
                                <f:facet name="header">
                                    <h:outputText value="Customer Id"/>
                                </f:facet>
                                <h:outputText value="#{dataItem.custId}"/>
                            </rich:column>
                            <rich:column width="18%" style="text-align:center">
                                <f:facet name="header">
                                    <h:outputText value="Customer Name"/>
                                </f:facet>
                                <h:outputText value="#{dataItem.custName}"/>
                            </rich:column>
                            <rich:column width="12%" style="text-align:center">
                                <f:facet name="header">
                                    <h:outputText value="Father Name"/>
                                </f:facet>
                                <h:outputText value="#{dataItem.fatherName}"/>
                            </rich:column>
                            <rich:column width="6%" style="text-align:center">
                                <f:facet name="header">
                                    <h:outputText value="Date of Birth"/>
                                </f:facet>
                                <h:outputText value="#{dataItem.dob}"/>
                            </rich:column>
                            <rich:column width="18%" style="text-align:center">
                                <f:facet name="header">
                                    <h:outputText value="Address"/>
                                </f:facet>
                                <h:outputText value="#{dataItem.address}"/>
                            </rich:column>
                            <rich:column width="12%" style="text-align:center">
                                <f:facet name="header">
                                    <h:outputText value="Mobile No."/>
                                </f:facet>
                                <h:outputText value="#{dataItem.mobileNo}"/>
                            </rich:column>
                            <rich:column width="8%" style="text-align:center">
                                <f:facet name="header">
                                    <h:outputText value="Request Aadhar No."/>
                                </f:facet>
                                <h:outputText value="#{dataItem.aadharNo}"/>
                            </rich:column>
                            <rich:column width="8%" style="text-align:center" >
                                <f:facet name="header">
                                    <h:outputText value="Aadhar No. in CBS"/>
                                </f:facet>
                                <h:outputText value="#{dataItem.aadharNoInCbs}"/>
                            </rich:column>
                            <rich:column width="8%" style="text-align:center">
                                <f:facet name="header">
                                    <h:outputText value="Enter By"/>
                                </f:facet>
                                <h:outputText value="#{dataItem.entry_by}"/>
                            </rich:column>
                            <rich:column width="5%" style="text-align:center"> 
                                <f:facet name="header">
                                    <h:selectBooleanCheckbox value="#{OnlineAadharUpdation.allSelected}">
                                        <a4j:support event="onchange" action="#{OnlineAadharUpdation.setAllBoxSelected}" reRender="taskList"/>
                                    </h:selectBooleanCheckbox>
                                </f:facet>
                                <h:selectBooleanCheckbox value="#{dataItem.selected}"/>
                            </rich:column>
                        </rich:extendedDataTable>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnHtml" value="Update" oncomplete="#{rich:component('processPanel')}.show();" 
                                               reRender="stxtMessage,processPanel"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{OnlineAadharUpdation.btnRefreshAction}" oncomplete="setMask();" 
                                               reRender="mainPanel"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{OnlineAadharUpdation.btnExitAction}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            
            <a4j:status onstart="#{rich:component('wait1')}.show()" onstop="#{rich:component('wait1')}.hide()"/>
            <rich:modalPanel id="wait1" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
            
            <a4j:region id="btnProcessRegion">
                <rich:modalPanel id="processPanel" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText id="confirmid" value="Are you sure to Update ?"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="btnYes" action="#{OnlineAadharUpdation.processAction}" 
                                                           oncomplete="#{rich:component('processPanel')}.hide(); #{rich:element('txtRecord')}.focus();" 
                                                           reRender="stxtMessage,mainPanel"/>
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
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="btnProcessRegion"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
