<%-- 
    Document   : AccountStatusAuthorization
    Created on : 22 Nov, 2010, 10:31:03 AM
    Author     : Zeeshan Waris[zeeshan.glorious@gmail.com]
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
            <title>Account Status Authorization</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{AccountStatusAuthorization.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Account Status Authorization"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="labelUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{AccountStatusAuthorization.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid   columns="1" id="subbodyPanel" style="width:100%;">
                        <h:panelGrid columnClasses="col2,col7,col9" columns="2" id="Panel790" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputText id="stxtMsg" styleClass="error" value="#{AccountStatusAuthorization.message}"/>
                        </h:panelGrid>

                        <h:panelGrid columnClasses="vtop" columns="1" id="Stocktable" width="100%" styleClass="row2" style="height:168px;">
                            <a4j:region>
                                <rich:dataTable value="#{AccountStatusAuthorization.statusTable}" var="item"
                                                rowClasses="row1, row2" id="taskList" rows="10" columnsWidth="100" rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="10"><h:outputText value="Pending Details"/></rich:column>
                                            <rich:column breakBefore="true" ><h:outputText value="S.No"/></rich:column>
                                            <rich:column ><h:outputText value="Account No" /></rich:column>
                                            <rich:column ><h:outputText value="Customer Name" /></rich:column>
                                            <rich:column ><h:outputText value="Date" /></rich:column>
                                            <rich:column ><h:outputText value="Remark" /></rich:column>
                                            <rich:column ><h:outputText value="Old Status"/></rich:column>
                                            <rich:column ><h:outputText value="New Status"/></rich:column>
                                            <rich:column ><h:outputText value="Enter By "/></rich:column>
                                            <rich:column ><h:outputText value="Auth"/></rich:column>
                                            <rich:column ><h:outputText value="Delete"/></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{item.sno}" /></rich:column>
                                    <rich:column><h:outputText value="#{item.acno}" /></rich:column>
                                    <rich:column><h:outputText value="#{item.custName}" /></rich:column>
                                    <rich:column><h:outputText value="#{item.effDate}" /></rich:column>
                                    <rich:column><h:outputText value="#{item.remark}" /></rich:column>
                                    <rich:column><h:outputText value="#{item.oldStatus}" /></rich:column>
                                    <rich:column><h:outputText value="#{item.spflag}" /></rich:column>
                                    <rich:column><h:outputText value="#{item.enterBy}" /></rich:column>
                                    <rich:column style="text-align:center;cursor:pointer;">
                                        <h:outputText value="#{item.auth}" />
                                        <a4j:support action="#{AccountStatusAuthorization.changeAuth}" ajaxSingle="true" event="ondblclick" reRender="stxtMsg,btnSave,taskList">
                                            <f:setPropertyActionListener value="#{item}" target="#{AccountStatusAuthorization.currentItem}"/>
                                            <f:setPropertyActionListener value="#{row}" target="#{AccountStatusAuthorization.currentRow}" />
                                        </a4j:support>
                                    </rich:column>
                                    <rich:column style="text-align:center;" >
                                        <a4j:commandLink ajaxSingle="true" id="deletelink" oncomplete="#{rich:component('deletePanel')}.show()">
                                            <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                            <f:setPropertyActionListener value="#{item}" target="#{AccountStatusAuthorization.currentItem}"/>
                                            <f:setPropertyActionListener value="#{row}" target="#{AccountStatusAuthorization.currentRow}" />
                                        </a4j:commandLink>
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
                                                   <a4j:region>
                                                  <a4j:commandButton id="yes" value="Yes"  action="#{AccountStatusAuthorization.authorizeBtnAction}"
                                                    onclick="#{rich:component('savePanel')}.hide();" reRender="stxtMsg,subbodyPanel,Stocktable,gridPanel2" />
                                                   </a4j:region>
                                                </td>
                                                <td align="center" width="50%">
                                                    <a4j:commandButton  id="No" value="No" onclick="#{rich:component('savePanel')}.hide();return false;" />
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </h:form>
                            </rich:modalPanel>
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
                                                    <a4j:commandButton value="Yes" ajaxSingle="true" action="#{AccountStatusAuthorization.deleteDetails}" reRender="stxtMsg,subbodyPanel,Stocktable,gridPanel2" 
                                                                       oncomplete="#{rich:component('deletePanel')}.hide();"/>
                                                </td>
                                                <td align="center" width="50%">
                                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </h:form>
                            </rich:modalPanel>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1" columns="2" id="PanelSignal" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblSignal" style="font-size:15px;color:green" styleClass="headerLabel" value=" -->>For Authorization Double Click On Auth Colomn In The Table And Then Press Authorize Button"/>
                        </h:panelGrid>
                    </h:panelGrid>

                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="BtnPanel">
                            <a4j:commandButton id="btnSave" value="Authorize"  disabled="#{AccountStatusAuthorization.btndisable}" oncomplete="#{rich:component('savePanel')}.show()"  reRender="subbodyPanel,gridPanel2" />
                            <a4j:commandButton  id="btnUpdate" value="Refresh" action="#{AccountStatusAuthorization.btnRefresh}" reRender="stxtMsg,taskList" />
                            <a4j:commandButton id="btnExit" value="Exit" action="#{AccountStatusAuthorization.btnExit}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>


