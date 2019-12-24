<%-- 
    Document   : InoperativeMarking
    Created on : Aug 16, 2010, 12:49:51 PM
    Author     : admin
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
            <title>Inoperative Accounts</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="InoperativeMarking">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <!--Header--> <h:panelGrid columns="3" id="Header" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{InoperativeMarking.todayDate}" />
                        </h:panelGroup>
                        <h:outputLabel id="lblInoperativeMarking" styleClass="headerLabel" value="Inoperative Marking"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{InoperativeMarking.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>
                    <!--Body-->
                    <h:panelGrid columnClasses="col9" columns="1" id="msggrid" style="width:100%;text-align:center" styleClass="row1">
                        <h:outputText id="stxtMssg" styleClass="error" value="#{InoperativeMarking.message}"/>
                    </h:panelGrid>
                    <h:panelGrid  columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="bodyPanel" style="width:100%;" styleClass="row2">
                        <h:outputLabel id="lblAccountType" styleClass="label" value="Account Type"/>
                        <h:selectOneListbox style="width:110px" styleClass="ddlist" id="ddAccType" size="1" value="#{InoperativeMarking.acType}">
                            <f:selectItems value="#{InoperativeMarking.acctNo}"/>
                            <a4j:support event="onchange" action="#{InoperativeMarking.onchangeAcno}" reRender="stxtMssg"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblInOptDays" styleClass="label"  value="In Opt Days"/>
                        <h:selectOneListbox style="width:110px" styleClass="ddlist" id="lstInOptDays" size="1" value="#{InoperativeMarking.days}">
                            <f:selectItems value="#{InoperativeMarking.daysList}"/>
                            <a4j:support event="onchange" action="#{InoperativeMarking.onChangeNumber}" reRender="stxtMssg"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblTillDate" styleClass="label" value="Till Date"/>
                        <rich:calendar datePattern="dd/MM/yyyy" id="calTillDate" value="#{InoperativeMarking.tillDate}" inputSize="10" disabled="true"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel103" width="100%" styleClass="row2" style="height:268px;">
                        <a4j:region>
                            <rich:dataTable value="#{InoperativeMarking.inoperativeMark}" var="item"
                                            rowClasses="row1, row2" id="taskList" rows="10" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup >
                                        <rich:column breakBefore="true"><h:outputText value="S.No." /></rich:column>
                                        <rich:column><h:outputText value="account Number" /></rich:column>
                                        <rich:column><h:outputText value="Customer Name" /></rich:column>
                                        <rich:column><h:outputText value="Last Transaction Date" /></rich:column>
                                        <rich:column><h:outputText value="Day Difference" /></rich:column>
                                        <rich:column><h:outputText value="Balance" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{item.sNo}" /></rich:column>
                                <rich:column><h:outputText value="#{item.acNo}" /></rich:column>
                                <rich:column><h:outputText value="#{item.customerName}" /></rich:column>
                                <rich:column ><h:outputText value="#{item.lastTrctionDt}" /></rich:column>
                                <rich:column ><h:outputText value="#{item.dayDiff}" /></rich:column>
                                <rich:column >
                                    <h:outputText value="#{item.balance}">
                                        <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                    </h:outputText>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller id="datascroller" align="left" for="taskList" maxPages="20"/>
                        </a4j:region>
                    </h:panelGrid>
                    <!--Footer-->
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="BtnPanel">
                            <a4j:commandButton id="btnStatus"  value ="Status" actionListener="#{InoperativeMarking.getTableDetails}" reRender="taskList,datascroller,btnMark,stxtMssg,popUpRepPanel"  
                                               oncomplete="if(#{InoperativeMarking.flag=='true'}){#{rich:component('popUpRepPanel')}.show();} 
                                               else if(#{InoperativeMarking.flag=='false'}){#{rich:component('popUpRepPanel')}.hide();}" />
                            <a4j:commandButton disabled="#{InoperativeMarking.isdisable}" id="btnMark" value="Marking" oncomplete="#{rich:component('markPanel')}.show()"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{InoperativeMarking.refreshForm}" reRender="stxtMssg,ddAccType,lstInOptDays,gridPanel103"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{InoperativeMarking.exitForm}" reRender="stxtMssg,ddAccType,lstInOptDays,gridPanel103"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
                <rich:messages></rich:messages>
            </a4j:form>
            <rich:modalPanel id="markPanel" autosized="true" width="200">
                <f:facet name="header">
                    <h:outputText value="Are You Sure For Inoperative Marking ?" style="padding-right:15px;" />
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/images/modal/close.png" styleClass="hidelink" id="hidelink3" />
                        <rich:componentControl for="markPanel" attachTo="hidelink3" operation="hide" event="onclick" />
                    </h:panelGroup>
                </f:facet>
                <h:form>
                    <table width="100%">
                        <tbody>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" ajaxSingle="true" action="#{InoperativeMarking.markingClick}"
                                                       oncomplete="#{rich:component('markPanel')}.hide();" reRender="stxtMssg,ddAccType,lstInOptDays,gridPanel103" />
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Cancel" onclick="#{rich:component('markPanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
             <rich:modalPanel id="popUpRepPanel" label="Form" width="1000" height="550" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpRepPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Inoperative Marking" style="text-align:center;"/>
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
                                <a4j:include viewId="#{InoperativeMarking.viewID}" />
                            </td>

                        </tr>
                    </tbody>
                </table>
            </rich:modalPanel>        
        </body>
    </html>
</f:view>
