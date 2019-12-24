<%-- 
    Document   : AccountClosingAuthorization
    Created on : 30 Oct, 2010, 3:25:00 PM
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
            <title>Account Closing Authorization</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="Form">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{AccountClosingAuthorization.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Account Closing Authorization"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="labelUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{AccountClosingAuthorization.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid   columns="1" id="subbodyPanel" style="width:100%;">
                        <h:panelGrid columnClasses="col2,col7,col9" columns="2" id="Panel790" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputText id="stxtMsg" styleClass="error" value="#{AccountClosingAuthorization.message}"/>
                        </h:panelGrid>

                        <h:panelGrid columnClasses="vtop" columns="1" id="Stocktable" width="100%" styleClass="row2" style="height:168px;">

                            <rich:contextMenu attached="false" id="menu" submitMode="ajax" oncollapse="row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'">
                                <rich:menuItem value="Remove Record" ajaxSingle="true" oncomplete="#{rich:component('deletePanel')}.show()"
                                               actionListener="#{AccountClosingAuthorization.fetchCurrentRow}">
                                    <a4j:actionparam name="Name" value="{Name}"/>
                                    <a4j:actionparam name="row" value="{currentRow}" />
                                </rich:menuItem>
                            </rich:contextMenu>

                            <a4j:region>
                                <rich:dataTable value="#{AccountClosingAuthorization.closeTable}" var="item"
                                                rowClasses="row1, row2" id="taskList" rows="10" columnsWidth="100" rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>

                                            <rich:column colspan="4">

                                            </rich:column>

                                            <rich:column breakBefore="true" >
                                                <h:outputText value="Account No."   />
                                            </rich:column>
                                            <rich:column >
                                                <h:outputText value="Name" />
                                            </rich:column>

                                            <rich:column >
                                                <h:outputText value="Closed By" />
                                            </rich:column>
                                            <rich:column >
                                                <h:outputText value="Authorize"/>
                                            </rich:column>

                                        </rich:columnGroup>
                                    </f:facet>

                                    <rich:column>
                                        <h:outputText value="#{item.accountNo}" />
                                    </rich:column>

                                    <rich:column>
                                        <h:outputText value="#{item.name}" />
                                    </rich:column>
                                    <rich:column>
                                        <h:outputText value="#{item.clossedby}" />
                                    </rich:column>
                                    <rich:column>
                                        <h:outputText value="#{item.authorize}" />
                                        <a4j:support actionListener="#{AccountClosingAuthorization.changeAuth}" ajaxSingle="true" event="ondblclick" reRender="stxtMsg,taskList">
                                            <f:setPropertyActionListener value="#{item}" target="#{AccountClosingAuthorization.currentItem}"/>
                                            <f:setPropertyActionListener value="#{row}" target="#{AccountClosingAuthorization.currentRow}" />
                                        </a4j:support>
                                    </rich:column>

                                </rich:dataTable>
                                <rich:datascroller id="scroller" align="left" for="taskList" maxPages="10" />
                            </a4j:region>


                            <rich:modalPanel id="savePanel" autosized="true" width="250" onshow="#{rich:element('yes')}.focus()">
                                <f:facet name="header">
                                    <h:outputText value="Confirmation DialogBox" />
                                </f:facet>
                                <h:form>
                                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                        <tbody>
                                            <tr style="height:40px">
                                                <td colspan="2">
                                                    <h:outputText value="Are You Sure To Authorize?"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center" width="50%">
                                                    <a4j:region id="yesBtn">
                                                        <a4j:commandButton id="yes" value="Yes" ajaxSingle="true" action="#{AccountClosingAuthorization.authorizeBtnAction}"
                                                                           oncomplete="#{rich:component('savePanel')}.hide();" reRender="stxtMsg,subbodyPanel,Stocktable,gridPanel2" />
                                                    </a4j:region>
                                                </td>
                                                <td align="center" width="50%">
                                                    <a4j:commandButton id="No" value="No" onclick="#{rich:component('savePanel')}.hide();return false;" />
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </h:form>
                            </rich:modalPanel>

                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1" columns="2" id="PanelSignal" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblSignal" style="font-size:15px;color:green" styleClass="headerLabel" value=" -->>For Authorization Double Click On Authorize Column In The Table And Then Press Authorize Button"/>
                        </h:panelGrid>

                    </h:panelGrid>


                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="BtnPanel">

                            <a4j:commandButton id="btnSave" value="Authorize" oncomplete="#{rich:component('savePanel')}.show()" disabled="#{AccountClosingAuthorization.btnDisable}"  reRender="subbodyPanel,gridPanel2" >
                            </a4j:commandButton>

                            <a4j:commandButton  id="btnUpdate" value="Refresh" action="#{AccountClosingAuthorization.btnRefrsh}" reRender="stxtMsg,taskList" >
                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{AccountClosingAuthorization.btnExit}">
                            </a4j:commandButton>


                        </h:panelGroup>
                    </h:panelGrid>

                </h:panelGrid>
            </a4j:form>

        </body>
    </html>
</f:view>


