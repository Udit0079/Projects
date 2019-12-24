<%-- 
    Document   : TdRecieptIssueAuthorization
    Created on : 29 Oct, 2010, 11:55:50 AM
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
            <title>Td Reciept Issue Authorization</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{TdRecieptIssueAuthorization.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="TD Receipt Issue Authorization"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="labelUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{TdRecieptIssueAuthorization.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid   columns="1" id="subbodyPanel" style="width:100%;">
                        <h:panelGrid columnClasses="col2,col7,col9" columns="2" id="Panel790" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputText id="stxtMsg" styleClass="error" value="#{TdRecieptIssueAuthorization.message}"/>
                        </h:panelGrid>

                        <h:panelGrid columnClasses="vtop" columns="1" id="Stocktable" width="100%" styleClass="row2" style="height:168px;">
                            <a4j:region>
                                <rich:dataTable value="#{TdRecieptIssueAuthorization.authtable}" var="item"
                                                rowClasses="row1, row2" id="taskList" rows="9" columnsWidth="100" rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="11">
                                            </rich:column>
                                            <rich:column breakBefore="true" >
                                                <h:outputText value="Scheme"   />
                                            </rich:column>
                                            <rich:column >
                                                <h:outputText value="Book No" />
                                            </rich:column>
                                            <rich:column >
                                                <h:outputText value="Series" />
                                            </rich:column>
                                            <rich:column >
                                                <h:outputText value="RNo.From " />
                                            </rich:column>
                                            <rich:column >
                                                <h:outputText value="RN.To" />
                                            </rich:column>
                                            <rich:column >
                                                <h:outputText value="Leaves" />
                                            </rich:column>
                                            <rich:column >
                                                <h:outputText value="IssueDate" />
                                            </rich:column>
                                            <rich:column >
                                                <h:outputText value="EnterBy" />
                                            </rich:column>
                                            <rich:column >
                                                <h:outputText value="Auth" />
                                            </rich:column>
                                            <rich:column  >
                                                <h:outputText value="S.No." rendered="#{TdRecieptIssueAuthorization.snoInvisible}"/>
                                            </rich:column>
                                             <%-- <rich:column  >
                                                <h:outputText value="select" />
                                             </rich:column> --%>
                                        </rich:columnGroup>
                                    </f:facet>

                                    <rich:column>
                                        <h:outputText value="#{item.scheme}" />
                                    </rich:column>
                                    <rich:column>
                                        <h:outputText value="#{item.bookNo}" />
                                    </rich:column>
                                    <rich:column>
                                        <h:outputText value="#{item.series}" />
                                    </rich:column>
                                    <rich:column>
                                        <h:outputText value="#{item.rNoFrom}" />
                                    </rich:column>
                                    <rich:column>
                                        <h:outputText value="#{item.rNoTo}" />
                                    </rich:column>
                                    <rich:column>
                                        <h:outputText value="#{item.leaves}" />
                                    </rich:column>
                                    <rich:column>
                                        <h:outputText value="#{item.issueDt}" />
                                    </rich:column>
                                    <rich:column>
                                        <h:outputText value="#{item.enterBy}" />
                                    </rich:column>
                                <%--    <rich:column>    
                                        <h:outputText value="#{item.auth}" />
                                    </rich:column> --%>
                                    <rich:column style="text-align:center;cursor:pointer;"><h:outputText value="#{item.auth}"/>
                                        <a4j:support action="#{TdRecieptIssueAuthorization.select}" ajaxSingle="true" event="ondblclick" reRender="Panel14,Panel15,stxtMsg,Stocktable,btnSave" >
                                            <f:setPropertyActionListener value="#{item}" target="#{TdRecieptIssueAuthorization.currentItem}"/>
                                            <f:setPropertyActionListener value="#{row}" target="#{TdRecieptIssueAuthorization.currentRow}" />
                                        </a4j:support>
                                    </rich:column>
                                    <rich:column>
                                        <h:outputText value="#{item.sno}"  rendered="#{TdRecieptIssueAuthorization.snoInvisible}" />
                                    </rich:column>


                             <%--     <rich:column>
                                        <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{TdRecieptIssueAuthorization.select}" reRender="Panel14,Panel15,stxtMsg">
                                            <h:graphicImage value="/resources/images/passed.gif" style="border:0" />

                                            <f:setPropertyActionListener value="#{item}" target="#{TdRecieptIssueAuthorization.currentItem}"/>
                                            <f:setPropertyActionListener value="#{row}" target="#{TdRecieptIssueAuthorization.currentRow}" />
                                        </a4j:commandLink>
                                        <rich:toolTip for="selectlink" value="Select" />
                                    </rich:column> --%>


                                </rich:dataTable>
                                <rich:datascroller id="scroller" align="left" for="taskList" maxPages="10" />
                            </a4j:region>
                            <h:panelGrid columnClasses="co1,col2,co1,col2" columns="4" id="Panel14" style="width:100%;" styleClass="row1">
                                <h:outputText id="stxtRNfrom" styleClass="output" value="#{TdRecieptIssueAuthorization.stRNfrom}" rendered="#{TdRecieptIssueAuthorization.flag}" />
                                <h:outputText id="stxtRNTo" styleClass="output" value="#{TdRecieptIssueAuthorization.stRNTo}" rendered="#{TdRecieptIssueAuthorization.flag}"/>
                                <h:outputText id="stxtBookNo" styleClass="output" value="#{TdRecieptIssueAuthorization.stBookNo}" rendered="#{TdRecieptIssueAuthorization.flag}" />
                                <h:outputText id="stxtSno" styleClass="output" value="#{TdRecieptIssueAuthorization.stSNo}" rendered="#{TdRecieptIssueAuthorization.flag}" />
                            </h:panelGrid>
                            <h:panelGrid columnClasses="co1,col2,co1,col2" columns="2" id="Panel15" style="width:100%;" styleClass="row2">
                                <h:outputText id="stxtScheme" styleClass="output" value="#{TdRecieptIssueAuthorization.scheme}" rendered="#{TdRecieptIssueAuthorization.flag}" />
                                <h:outputText id="stxtSeries" styleClass="output" value="#{TdRecieptIssueAuthorization.series}" rendered="#{TdRecieptIssueAuthorization.flag}" />

                            </h:panelGrid>

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
                                                        <a4j:commandButton id="yes" value="Yes" ajaxSingle="true" action="#{TdRecieptIssueAuthorization.saveBtnAction}"
                                                                           oncomplete="#{rich:component('savePanel')}.hide();" reRender="stxtMsg,subbodyPanel,gridPanel2,btnSave" />
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


                    </h:panelGrid>


                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="BtnPanel">

                            <a4j:commandButton id="btnSave" value="Authorize" oncomplete="#{rich:component('savePanel')}.show()" reRender="stxtMsg,subbodyPanel,gridPanel2" disabled="#{TdRecieptIssueAuthorization.btnDisable}" >
                            </a4j:commandButton>

                            <a4j:commandButton  id="btnUpdate" value="Refresh" action="#{TdRecieptIssueAuthorization.Clear}" reRender="stxtMsg,taskList" >
                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{TdRecieptIssueAuthorization.btnExit}">
                            </a4j:commandButton>


                        </h:panelGroup>
                    </h:panelGrid>

                </h:panelGrid>
            </a4j:form>

        </body>
    </html>
</f:view>



