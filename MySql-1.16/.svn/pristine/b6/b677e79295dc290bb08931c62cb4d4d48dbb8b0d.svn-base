<%-- 
    Document   : atmReversalPosting
    Created on : Mar 31, 2015, 5:06:12 PM
    Author     : Athar Reza
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>ATM-Reversal Posting</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".calInstDate").mask("99/99/9999");
                }

            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{AtmReversalPosting.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="ATM-Reversal Posting"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{AtmReversalPosting.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="message" styleClass="msg" value="#{AtmReversalPosting.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel1" columns="2" columnClasses="col8,col8" width="100%">
                        <h:panelGrid  columnClasses="col8,col8" columns="2" id="gridpanel3" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputText value="From Date" styleClass="label"/>
                            <h:inputText id="frDt" styleClass="input calInstDate" style="width:70px;"  value="#{AtmReversalPosting.frDate}">
                                <a4j:support event="onblur" action="#{AtmReversalPosting.dateValdation}" reRender="toDt" oncomplete="setMask();"/>
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid  columnClasses="col8,col8" columns="2" id="gridpanel4" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputText value="To Date" styleClass="label"/>
                            <h:inputText id="toDt" styleClass="input calInstDate" style="width:70px;"  value="#{AtmReversalPosting.toDate}">
                                <a4j:support event="onblur" action="#{AtmReversalPosting.gridLoad}" reRender="a19,taskList,message,btnSave" oncomplete="setMask();"/>
                            </h:inputText>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="a19" style="height:auto;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{AtmReversalPosting.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="7" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="15"><h:outputText value="Details" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Tran Type" /></rich:column>
                                        <rich:column><h:outputText value="Amount" /></rich:column>
                                        <rich:column><h:outputText value="Stan" /></rich:column>
                                        <rich:column><h:outputText value="Reversal Stan" /></rich:column>
                                        <rich:column><h:outputText value="Card Number" /></rich:column>
                                        <rich:column><h:outputText value="Txn Date" /></rich:column>
                                        <rich:column><h:outputText value="Comming Date" /></rich:column>
                                        <rich:column><h:outputText value="Process Flag" /></rich:column>
                                        <rich:column><h:outputText value="Process Status" /></rich:column>

                                        <rich:column width="20"><h:outputText value="Select" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.reversalIndicater}" /></rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem.amount}" style="text-align:right;">
                                    </h:outputText>
                                </rich:column>
                                <rich:column><h:outputText value="#{dataItem.stNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.ostNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.cardNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.tranDate}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.incomingDate}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.processFlag}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.processStatus}" /></rich:column>   

                                <rich:column style="text-align:center;width:40px">     
                                    <a4j:commandLink ajaxSingle="true" id="selectlink" oncomplete="#{rich:component('savePanel')}.show()" reRender=",message,lpg,frDt,toDt">
                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{AtmReversalPosting.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{AtmReversalPosting.currentRow}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="20" />
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnSave" tabindex="12" value="Post" oncomplete="#{rich:component('savePanel')}.show()" disabled="#{AtmReversalPosting.postButtonDisable}" reRender="message,a19,taskList"/>
                            <a4j:commandButton id="btnRefresh" tabindex="13" ajaxSingle="true" value="Refresh" action="#{AtmReversalPosting.resetForm}"
                                               reRender="taskList,message,lpg,a19,frDt,toDt,btnSave" oncomplete="setMask();">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" tabindex="14" ajaxSingle="true" value="Exit" action="#{AtmReversalPosting.exitForm}" reRender="taskList,message,lpg,a19"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="savePanel" autosized="true" width="250" onshow="#{rich:element('save')}.focus()">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure To Reversal ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="save" ajaxSingle="true" action="#{AtmReversalPosting.postDetail}" oncomplete="#{rich:component('savePanel')}.hide();return false;"
                                                       reRender="message,lpg,taskList" />
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" onclick="#{rich:component('savePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>

