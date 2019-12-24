<%--
    Document   : TdDuplicateRecptAuth
    Created on : Aug 9, 2010, 6:40:33 PM
    Author     : root
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
            <title>Duplicate Receipt Authorization</title>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPane42" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{TdDuplicateRecptAuth.todayDate}"  />
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel"  value="Duplicate Receipt Authorization"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{TdDuplicateRecptAuth.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="gridPanel103" width="100%" styleClass="row1" style="height:200px;">
                        <rich:dataTable  value="#{TdDuplicateRecptAuth.gridData}" var="dataItem"
                                         rowClasses="row1,row2" id="taskList" rows="6" columnsWidth="100" rowKeyVar="row"
                                         onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                         onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="18">
                                        <h:outputText value="Duplicate Receipt Authorization" />
                                    </rich:column>
                                    <rich:column breakBefore="true"><h:outputText value="Int To Acno" /></rich:column>
                                    <rich:column><h:outputText value="Control No" /></rich:column>
                                    <rich:column><h:outputText value="RT No" /></rich:column>
                                    <rich:column><h:outputText value="Receipt No" /></rich:column>
                                    <rich:column><h:outputText value="ROI" /></rich:column>
                                    <rich:column><h:outputText value="TD Date" /></rich:column>
                                    <rich:column><h:outputText value="TD Date wef" /></rich:column>
                                    <rich:column><h:outputText value="Mat Date" /></rich:column>
                                    <rich:column><h:outputText value="Prin Amn(rs)" /></rich:column>
                                    <rich:column><h:outputText value=" Int Opt" /></rich:column>
                                    <rich:column><h:outputText value="Period" /></rich:column>
                                    <rich:column><h:outputText value="Int.at Mat(Rs.)" /></rich:column>
                                    <rich:column><h:outputText value="Tot.TD Amt(Rs.)" /></rich:column>
                                    <rich:column><h:outputText value="Status" /></rich:column>
                                    <rich:column><h:outputText value="Enter By" /></rich:column>
                                    <rich:column><h:outputText value="Auth" /></rich:column>
                                    <rich:column><h:outputText value="Select Row" /></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{dataItem.acNo}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.seqNo}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.vchNo}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.receiptNo}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.roi}" /></rich:column>
                            <rich:column ><h:outputText value="#{dataItem.tdMadeDt}" /></rich:column>
                            <rich:column ><h:outputText value="#{dataItem.fdDt}" /></rich:column>
                            <rich:column ><h:outputText value="#{dataItem.matDt}" /></rich:column>
                            <rich:column ><h:outputText value="#{dataItem.prinAmt}" /></rich:column>
                            <rich:column ><h:outputText value="#{dataItem.intOpt}" /></rich:column>
                            <rich:column ><h:outputText value="#{dataItem.period}" /></rich:column>
                            <rich:column ><h:outputText value="#{dataItem.intAtMat}" /></rich:column>
                            <rich:column ><h:outputText value="#{dataItem.totTdAmt}" /></rich:column>
                            <rich:column ><h:outputText value="#{dataItem.status}" /></rich:column>
                            <rich:column ><h:outputText value="#{dataItem.enterBy}" /></rich:column>
                            <rich:column ><h:outputText value="#{dataItem.auth}"/></rich:column>
                            <rich:column style="text-align:center;width:40px">
                                <a4j:commandLink  reRender="btnModify"  ajaxSingle="true" id="selectlink" onclick="#{rich:component('showPanelForGrid')}.show();">
                                    <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                    <f:setPropertyActionListener value="#{dataItem}" target="#{TdDuplicateRecptAuth.authorized}"/>
                                    <f:setPropertyActionListener value="#{row}" target="#{TdDuplicateRecptAuth.currentRow}"/>
                                </a4j:commandLink>
                            </rich:column>
                        </rich:dataTable>
                        <rich:modalPanel id="showPanelForGrid" autosized="true" width="220" >
                            <f:facet name="header">
                                <h:outputText value="Confirmation Alert" style="padding-right:15px;" />
                            </f:facet>
                            <f:facet name="controls">
                                <h:panelGroup>
                                    <h:graphicImage value="/images/close.png" styleClass="hidelink" id="hidelink1" />
                                    <rich:componentControl for="showPanelForGrid" attachTo="hidelink1" operation="hide" event="onclick" />
                                </h:panelGroup>
                            </f:facet>
                            <h:form>
                                <table width="100%" bgcolor="#edebeb">
                                    <tbody>
                                        <tr style="height: 50px">
                                            <td colspan="2">
                                                <h:outputText value="Are you sure you want authorize the record ?" style="padding-right:15px;"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="Yes" ajaxSingle="true" action="#{TdDuplicateRecptAuth.authorizeClick}"
                                                                   onclick="#{rich:component('showPanelForGrid')}.hide();" reRender="taskList,errMsg,btnModify"/>
                                            </td>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="No" onclick="#{rich:component('showPanelForGrid')}.hide();return false;" />
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </h:form>
                        </rich:modalPanel>
                        <rich:datascroller align="left" for="taskList" maxPages="20" />
                    </h:panelGrid>
                </h:panelGrid>
                <h:panelGrid  id="errorMsg"   width="100%" style="height:50px;" styleClass="error">
                    <h:outputText id="errMsg" value="#{TdDuplicateRecptAuth.msg}"/>
                </h:panelGrid>
                <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                    <h:panelGroup id="btnPanel">
                        <a4j:commandButton id="btnRefresh"  value="Refresh" action="#{TdDuplicateRecptAuth.refreshBtnAction}" reRender="errMsg,taskList,btnModify"/>
                        <a4j:commandButton id="btnClose" value="Exit" action="#{TdDuplicateRecptAuth.exitForm}"/>
                    </h:panelGroup>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>

