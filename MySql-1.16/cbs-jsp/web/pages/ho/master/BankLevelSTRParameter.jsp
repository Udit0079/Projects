<%-- 
    Document   : BankLevelSTRParameter
    Created on : 7 Mar, 2017, 2:46:23 PM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>STR Alert System</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
        </head>
        <body>
            <h:form id="ckycrFailureReport" enctype="multipart/form-data">
                <h:panelGrid id="mainPanel" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{bankLevelSTRParameterController.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="BANK LEVEL STR"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{bankLevelSTRParameterController.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="msgLabelId" style="height:25px;text-align:center" styleClass="#{bankLevelSTRParameterController.msgStyle}" width="100%">
                        <h:outputText id="msgId" value="#{bankLevelSTRParameterController.msg}"/>
                    </h:panelGrid>
                   
                     <h:panelGrid id="txnGrid" width="100%" style="background-color:#edebeb;height:200px;" columnClasses="vtop">
                        <rich:dataTable  value="#{bankLevelSTRParameterController.strAlertList}" var="item" 
                                         rowClasses="gridrow1, gridrow2" id="txnList" rows="5" columnsWidth="100"
                                         onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                         onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                         width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="11"><h:outputText value="STR Alert"/></rich:column>
                                    <rich:column breakBefore="true" width="5"><h:outputText value="SNo."/></rich:column>
                                    <rich:column width="300"><h:outputText value="Description" /></rich:column>
                                    <rich:column width="50"><h:outputText value="From"  /></rich:column>
                                    <rich:column width="50"><h:outputText value="To" /></rich:column>
                                    <rich:column width="50"><h:outputText value="No. of Txn" /></rich:column>
                                    <rich:column width="50"><h:outputText value="Day Or Month" /></rich:column>
                                    <rich:column width="50"><h:outputText value="#" /></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{item.sno}"/></rich:column>
                            <rich:column><h:outputText value="#{item.description}"/></rich:column>
                            <rich:column><h:inputText value="#{item.fromAmt}" >
                                        </h:inputText>
                            </rich:column>
                            <rich:column><h:inputText value="#{item.toAmt}"/></rich:column>
                            <rich:column><h:inputText value="#{item.noOfTxn}"/></rich:column>
                            <rich:column><h:inputText value="#{item.dayMonth}"/></rich:column>
                            <rich:column><h:selectBooleanCheckbox  id="c1"  value="#{item.checkBox}">
                                    <a4j:support actionListener="#{bankLevelSTRParameterController.checkUnCheck}" event="onclick" reRender="txnGrid"/> 
                                </h:selectBooleanCheckbox>
                            </rich:column> 
                        </rich:dataTable>
                        <rich:datascroller id="deafDataScroll"align="left" for="txnList" maxPages="20"/>
                    </h:panelGrid>
                    
                    <h:panelGrid columns="2" id="a6" style="height:30px;" styleClass="footer" columnClasses="col7,col7,col7" width="100%">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <%-- <a4j:commandButton id="get" value="Get" action="#{bankLevelSTRParameterController.getStrList}" reRender="txnGrid,msgLabelId,msgId"/> --%>
                            <a4j:commandButton id="save" value="Save" oncomplete="#{rich:component('processPanel')}.show()" reRender="id1,txnGrid,msgLabelId,msgId"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{bankLevelSTRParameterController.refresh}" reRender="txnGrid,msgLabelId,msgId"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{bankLevelSTRParameterController.exitForm}"/>
                            
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </h:form>
            <rich:modalPanel id="processPanel" autosized="true" width="250" onshow="#{rich:element('btnYes')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are you sure to Save ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" action="#{bankLevelSTRParameterController.saveStr}" 
                                                       oncomplete="#{rich:component('processPanel')}.hide(); #{rich:element('processPanel')}.focus();" 
                                                       reRender="txnGrid,msgLabelId,msgId"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('processPanel')}.hide();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
