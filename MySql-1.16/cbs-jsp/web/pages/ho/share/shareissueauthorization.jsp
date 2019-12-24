<%-- 
    Document   : shareholderissueauthorization
    Created on : 2 Sep, 2011, 5:21:41 PM
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
            <title>Share Holder Issue Authorization</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:keepAlive beanName="ShareIssueAuthorization"/>
            <a4j:form id="ShareIssueAuthorization">
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{ShareIssueAuthorization.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Share Issue Authorization"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="labelUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ShareIssueAuthorization.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid   columns="1" id="subbodyPanel" style="width:100%;">
                        <h:panelGrid columnClasses="col2,col6" columns="2" id="Panel790" style="width:100%;text-align:center;" styleClass="row2">
                            <h:outputText id="stxtMsg" styleClass="error" value="#{ShareIssueAuthorization.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col6" columns="2" id="Panel791" style="width:100%;" styleClass="row1">
                            <h:outputLabel id="pendingTxn" styleClass="label" value="Pending Issue Shares: "/>
                            <h:selectOneListbox id="ddusers" styleClass="ddlist"  size="1" style="width:80px;" value="#{ShareIssueAuthorization.txnNo}" >
                                <f:selectItems value="#{ShareIssueAuthorization.txnNoList}"/>
                                <a4j:support action="#{ShareIssueAuthorization.getShareDetails}" event="onblur" oncomplete="setMask();" 
                                             reRender="stxtMsg,taskList" />
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="vtop" columns="1" id="Stocktable" width="100%" styleClass="row2" style="height:168px;">
                            <a4j:region>
                                <rich:dataTable value="#{ShareIssueAuthorization.shareHolder}" var="item"
                                                rowClasses="row1, row2" id="taskList" rows="10" columnsWidth="100" rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="11"></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="Sno"/> </rich:column>
                                            <rich:column ><h:outputText value="Share From No"/></rich:column>
                                            <rich:column ><h:outputText value="Share To No"/></rich:column>
                                            <rich:column ><h:outputText value="No of Share"/></rich:column>
                                            <rich:column ><h:outputText value="Issue Date"/></rich:column>
                                            <rich:column ><h:outputText value="Issue By" /> </rich:column>
                                            <rich:column ><h:outputText value="Auth"/> </rich:column>
                                            <rich:column ><h:outputText value="Delete"/> </rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column> <h:outputText value="#{item.sNo}"/></rich:column>
                                    <rich:column> <h:outputText value="#{item.shareNoFrom}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.shareNoTo}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.noOfShare}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.issueDt}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.issueBy}"/></rich:column>
                                    <rich:column style="cursor:pointer;text-align:center">
                                        <h:outputText value="#{item.auth}"/>
                                        <a4j:support action="#{ShareIssueAuthorization.changeAuth}" ajaxSingle="true" event="ondblclick" reRender="stxtMsg,taskList,btnSave">
                                            <f:setPropertyActionListener value="#{item}" target="#{ShareIssueAuthorization.currentItem}"/>
                                            <f:setPropertyActionListener value="#{row}" target="#{ShareIssueAuthorization.currentRow}" />
                                        </a4j:support>
                                    </rich:column>
                                    <rich:column style="cursor:pointer;text-align:center">
                                        <a4j:commandLink ajaxSingle="true" id="deletelink" oncomplete="#{rich:component('deletePanel')}.show()">
                                            <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                            <f:setPropertyActionListener value="#{item}" target="#{ShareIssueAuthorization.currentItem}"/>
                                            <f:setPropertyActionListener value="#{row}" target="#{ShareIssueAuthorization.currentRow}" />
                                        </a4j:commandLink>
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
                            <a4j:commandButton id="btnSave" value="Authorize" action="#{ShareIssueAuthorization.authorizeBtnAction}" disabled="#{ShareIssueAuthorization.btnDisable}"  reRender="subbodyPanel,BtnPanel"/>
                            <a4j:commandButton  id="btnUpdate" value="Refresh" action="#{ShareIssueAuthorization.btnRefrsh}" reRender="subbodyPanel,BtnPanel"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{ShareIssueAuthorization.btnExit}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="deletePanel" label="Confirmation Dialog" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();" style="">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are you sure to delete this transaction ? "/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" ajaxSingle="true" action="#{ShareIssueAuthorization.btnDeleteAction}" 
                                                       reRender="btnDelete,btnSave,taskList,stxtMsg,ddusers" oncomplete="#{rich:component('deletePanel')}.hide();"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
