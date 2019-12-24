<%-- 
    Document   : dividendauthorization
    Created on : 25 Aug, 2011, 4:21:35 PM
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
            <title>Dividend Authorization</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:keepAlive beanName="DividendAuthorization"/>
            <a4j:form id="DividendAuthorization">
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{DividendAuthorization.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Dividend Authorization"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="labelUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{DividendAuthorization.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid   columns="1" id="subbodyPanel" style="width:100%;">
                        <h:panelGrid columnClasses="col2,col7,col9" columns="2" id="Panel790" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputText id="stxtMsg" styleClass="error" value="#{DividendAuthorization.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="vtop" columns="1" id="Stocktable" width="100%" styleClass="row2" style="height:168px;">
                            <rich:contextMenu attached="false" id="menu" submitMode="ajax" oncollapse="row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'">
                                <rich:menuItem value="Remove Record" ajaxSingle="true" oncomplete="#{rich:component('deletePanel')}.show()"
                                               actionListener="#{DividendAuthorization.fetchCurrentRow}">
                                    <a4j:actionparam name="Name" value="{Name}"/>
                                    <a4j:actionparam name="row" value="{currentRow}" />
                                </rich:menuItem>
                            </rich:contextMenu>
                            <a4j:region>
                                <rich:dataTable value="#{DividendAuthorization.closeTable}" var="item"
                                                rowClasses="row1, row2" id="taskList" rows="10" columnsWidth="100" rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="11"></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="Account No."/> </rich:column>
                                            <rich:column ><h:outputText value="Name"/></rich:column>
                                            <rich:column ><h:outputText value="CrAmount"/></rich:column>
                                            <rich:column ><h:outputText value="DrAmount"/></rich:column>
                                            <rich:column ><h:outputText value="Tran Date"/></rich:column>
                                            <rich:column ><h:outputText value="Details"/></rich:column>
                                            <rich:column ><h:outputText value="Txn ID"/></rich:column>
                                            <rich:column ><h:outputText value="Entered By" /> </rich:column>
                                            <rich:column ><h:outputText value="Auth"/> </rich:column>
                                            <rich:column ><h:outputText value="Delete"/> </rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column> <h:outputText value="#{item.acno}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.name}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.crAmt}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.drAmt}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.trnTime}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.details}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.txnId}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.enterBy}"/></rich:column>
                                    <rich:column>
                                        <h:outputText value="#{item.auth}" />
                                        <a4j:support action="#{DividendAuthorization.changeAuth}" ajaxSingle="true" event="ondblclick" reRender="stxtMsg,taskList,btnSave">
                                            <f:setPropertyActionListener value="#{item}" target="#{DividendAuthorization.currentItem}"/>
                                            <f:setPropertyActionListener value="#{row}" target="#{DividendAuthorization.currentRow}" />
                                        </a4j:support>
                                    </rich:column>
                                    <rich:column>
                                        <a4j:commandLink ajaxSingle="true" id="deletelink" reRender="subbodyPanel" oncomplete="#{rich:component('deletePanel')}.show()">
                                            <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                            <f:setPropertyActionListener value="#{row}" target="#{DividendAuthorization.currentRow}" />
                                            <f:setPropertyActionListener value="#{item}" target="#{DividendAuthorization.currentItem}"/>
                                        </a4j:commandLink>
                                    </rich:column>
                                </rich:dataTable>
                                <rich:datascroller id="scroller" align="left" for="taskList" maxPages="10" />
                            </a4j:region>
                            <rich:modalPanel id="deletePanel" autosized="true" width="250" onshow="#{rich:element('Yes')}.focus()">
                                <f:facet name="header">
                                    <h:outputText value="Confirmation DialogBox" />
                                </f:facet>

                                <h:form>
                                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                        <tbody>
                                            <tr style="height:40px">
                                                <td colspan="2">
                                                    <h:outputText value="Are You Sure To Delete This Row ?"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center" width="50%">
                                                    <a4j:region id="yesBtn">
                                                        <a4j:commandButton id="Yes" value="Yes" ajaxSingle="true" action="#{DividendAuthorization.deleteBtnAction}"
                                                                           onclick="#{rich:component('deletePanel')}.hide();" reRender="lblMsg,mainPanel" />
                                                    </a4j:region>
                                                </td>
                                                <td align="center" width="50%">
                                                    <a4j:commandButton id="No" value="No" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </h:form>
                            </rich:modalPanel>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1" columns="2" id="PanelSignal" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblSignal" style="font-size:15px;color:purple" styleClass="headerLabel" value=" -->>For Authorization Double Click On Auth Colomn In The Table And Then Press Authorize Button"/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="BtnPanel">
                            <a4j:commandButton id="btnSave" value="Authorize" action="#{DividendAuthorization.authorizeBtnAction}" disabled="#{DividendAuthorization.btnDisable}"  reRender="subbodyPanel,gridPanel2"/>
                            <a4j:commandButton  id="btnUpdate" value="Refresh" action="#{DividendAuthorization.btnRefrsh}" reRender="stxtMsg,taskList"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{DividendAuthorization.btnExit}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>

        </body>
    </html>
</f:view>
