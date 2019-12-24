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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <title>Parameter Info Report</title>
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{ParameterInfoReport.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Parameter Info Report"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="labelUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ParameterInfoReport.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="subbodyPanel" style="width:100%;">
                        <h:panelGrid columnClasses="col2,col7,col9" columns="2" id="Panel790" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputText id="stxtMsg" styleClass="error" value="#{ParameterInfoReport.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="" columns="12" id="Panel1" style="width:100%;" styleClass="row2">
                            <h:outputLabel id="lblReportName" styleClass="label"  value="Report Name"/>
                            <h:selectOneListbox id="ddReportNamelst" styleClass="ddlist" size="1" style="width: 134px" value="#{ParameterInfoReport.reportNamePage}" >
                                <f:selectItems value="#{ParameterInfoReport.ddReportName}" />
                                <a4j:support action="#{ParameterInfoReport.valueChange}"  event="onchange" ajaxSingle="true" reRender="footerPanel,stxtMsg,txtcode" focus="txtcode" />
                            </h:selectOneListbox>
                            <h:outputLabel id="lblcode" styleClass="label"  value="Code"/>
                            <h:inputText id="txtcode" style="width: 134px" value="#{ParameterInfoReport.codePage}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input">
                            </h:inputText>
                            <h:outputText id="stxt1" styleClass="output"/>
                            <h:outputText id="stxt2" styleClass="output"/>
                            <h:outputText id="stxt3" styleClass="output"/>
                            <h:outputText id="stxt4" styleClass="output"/>
                            <h:outputText id="stxt5" styleClass="output"/>
                            <h:outputText id="stxt6" styleClass="output"/>
                            <h:outputText id="stxt7" styleClass="output"/>
                            <h:outputText id="stxt8" styleClass="output"/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel103" width="100%" styleClass="row2" style="height:168px;">
                        <a4j:region>
                            <rich:dataTable value="#{ParameterInfoReport.tripFile}" var="item"
                                            rowClasses="row1, row2" id="taskList" rows="9" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column style="width:45%;"><h:outputText value="ReportName" /></rich:column>
                                        <rich:column style="width:45%;"><h:outputText value="Code" /></rich:column>
                                        <rich:column  style="width:10%;"><h:outputText value="Edit" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{item.desc}" /></rich:column>
                                <rich:column><h:outputText value="#{item.code}" /></rich:column>
                                <rich:column>
                                    <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{ParameterInfoReport.select}" reRender="subbodyPanel,footerPanel,BtnPanel,stxtMsg">
                                        <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{item}" target="#{ParameterInfoReport.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{ParameterInfoReport.currentRow}" />
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller id="scroller" align="left" for="taskList" maxPages="20" />
                        </a4j:region>
                        <rich:modalPanel id="updatePanel" autosized="true" width="200" onshow="#{rich:element('upYes')}.focus()">
                            <f:facet name="header">
                                <h:outputText value="Are You Sure To update?" style="padding-right:15px;" />
                            </f:facet>
                            <h:form>
                                <table width="100%">
                                    <tbody>
                                        <tr>
                                            <td align="center" width="50%">
                                                <a4j:commandButton id="upYes" value="Yes" ajaxSingle="true" action="#{ParameterInfoReport.updateBtnAction}"
                                                                   onclick="#{rich:component('updatePanel')}.hide();" reRender="stxtMsg,taskList,subbodyPanel" />
                                            </td>
                                            <td align="center" width="50%">
                                                <a4j:commandButton id="upNo" value="Cancel" onclick="#{rich:component('updatePanel')}.hide();return false;" />
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </h:form>
                        </rich:modalPanel>
                        <rich:modalPanel id="savePanel" autosized="true" width="200" onshow="#{rich:element('saveYes')}.focus()">
                            <f:facet name="header">
                                <h:outputText value="Are You Sure To Save?" style="padding-right:15px;" />
                            </f:facet>

                            <h:form>
                                <table width="100%">
                                    <tbody>
                                        <tr>
                                            <td align="center" width="50%">
                                                <a4j:commandButton id="saveYes" value="Yes" ajaxSingle="true" action="#{ParameterInfoReport.saveBtnAction}"
                                                                   oncomplete="#{rich:component('savePanel')}.hide();" reRender="taskList,stxtMsg,subbodyPanel" />
                                            </td>
                                            <td align="center" width="50%">
                                                <a4j:commandButton id="saveNo" value="Cancel" onclick="#{rich:component('savePanel')}.hide();return false;" />
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </h:form>
                        </rich:modalPanel>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col1" columns="1" id="Panelinfo" style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel id="lblinfo" styleClass="label"  value="->> for Updation Click In The Edit Colomn at Edit Image" style="font-size:15px;color:green"/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="BtnPanel">
                            <a4j:commandButton id="btnSave" value="Save" oncomplete="#{rich:component('savePanel')}.show()"  reRender="taskList,stxtMsg,subbodyPanel,txtcode" rendered="#{ParameterInfoReport.flag == 'true'}" focus="btnRefresh"/>
                            <a4j:commandButton  id="btnUpdate" value="Update" oncomplete="#{rich:component('updatePanel')}.show()" reRender="taskList,stxtMsg,subbodyPanel,txtcode" rendered="#{ParameterInfoReport.flag == 'false'}" focus="btnRefresh"/>
                            <a4j:commandButton  id="btnRefresh" value="Refresh" action="#{ParameterInfoReport.refreshAction}" reRender="mainPanel" focus="ddReportNamelst"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{ParameterInfoReport.exitBtnAction}" reRender="stxtMsg,txtcode" focus="ddReportNamelst"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>