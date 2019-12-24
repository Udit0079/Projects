<%-- 
    Document   : sharecertificateissue
    Created on : 31 Aug, 2011, 4:53:10 PM
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
            <title>Share Certificate Issue Authorization</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:keepAlive beanName="ShareCertificateIssueAuthorization"/>
            <a4j:form id="ShareCertificateIssueAuthorization">
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{ShareCertificateIssueAuthorization.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Share Certificate Issue Authorization"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="labelUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ShareCertificateIssueAuthorization.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid   columns="1" id="subbodyPanel" style="width:100%;">
                        <h:panelGrid columnClasses="col2,col7,col9" columns="2" id="Panel790" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputText id="stxtMsg" styleClass="error" value="#{ShareCertificateIssueAuthorization.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="vtop" columns="1" id="Stocktable" width="100%" styleClass="row2" style="height:168px;">
                            <rich:contextMenu attached="false" id="menu" submitMode="ajax" oncollapse="row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'">
                                <rich:menuItem value="Remove Record" ajaxSingle="true" oncomplete="#{rich:component('deletePanel')}.show()"
                                               actionListener="#{ShareCertificateIssueAuthorization.fetchCurrentRow}">
                                    <a4j:actionparam name="Name" value="{Name}"/>
                                    <a4j:actionparam name="row" value="{currentRow}" />
                                </rich:menuItem>
                            </rich:contextMenu>
                            <a4j:region>
                                <rich:dataTable value="#{ShareCertificateIssueAuthorization.closeTable}" var="item"
                                                rowClasses="row1, row2" id="taskList" rows="10" columnsWidth="100" rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="11"></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="Foliio No"/> </rich:column>
                                            <rich:column ><h:outputText value="Share Holder"/></rich:column>
                                            <rich:column ><h:outputText value="Certificate No"/></rich:column>
                                            <rich:column ><h:outputText value="Issue Date"/></rich:column>
                                            <rich:column ><h:outputText value="Entered By" /> </rich:column>
                                            <rich:column ><h:outputText value="Auth"/> </rich:column>

                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column> <h:outputText value="#{item.regfolioNo}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.name}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.certNo}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.issueDt}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.enterBy}"/></rich:column>
                                    <rich:column>
                                        <h:outputText value="#{item.auth}" />
                                        <a4j:support action="#{ShareCertificateIssueAuthorization.changeAuth}" ajaxSingle="true" event="ondblclick" reRender="stxtMsg,taskList,btnSave">
                                            <f:setPropertyActionListener value="#{item}" target="#{ShareCertificateIssueAuthorization.currentItem}"/>
                                            <f:setPropertyActionListener value="#{row}" target="#{ShareCertificateIssueAuthorization.currentRow}" />
                                        </a4j:support>
                                    </rich:column>
                                </rich:dataTable>
                                <rich:datascroller id="scroller" align="left" for="taskList" maxPages="10" />
                            </a4j:region>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1" columns="2" id="PanelSignal" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblSignal" style="font-size:15px;color:purple" styleClass="headerLabel" value=" -->>For Authorization Double Click On Auth Colomn In The Table And Then Press Authorize Button"/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="BtnPanel">
                            <a4j:commandButton id="btnSave" value="Authorize" action="#{ShareCertificateIssueAuthorization.authorizeBtnAction}" disabled="#{ShareCertificateIssueAuthorization.btnDisable}"  reRender="subbodyPanel,gridPanel2"/>
                            <a4j:commandButton  id="btnUpdate" value="Refresh" action="#{ShareCertificateIssueAuthorization.btnRefrsh}" reRender="stxtMsg,taskList"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{ShareCertificateIssueAuthorization.btnExit}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>

        </body>
    </html>
</f:view>
