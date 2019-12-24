<%-- 
    Document   : Saving Deposit
    Created on : Sep 24, 2010, 3:03:54 PM
    Author     : Sudhir Kr bisht
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>Saving Deposit(Time Liability Position)</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery){
                    setMask();
                });
                var row;
                function setMask(){
                    jQuery(".dtEffective").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="gridPanel" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{SavingDeposit.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Saving Deposit"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{SavingDeposit.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="errorMsgReporting"   width="100%"   style="text-align:center" styleClass="row2">
                        <h:outputText id="errorGrid" value="#{SavingDeposit.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel1" columns="2" columnClasses="col8,col8" width="100%">
                        <h:panelGrid  columnClasses="col8,col8" columns="2" id="gridpanel2" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblEffDate" styleClass="label" value="Effective Date"/>
                            <h:inputText id="dtEffective" styleClass="input dtEffective" value="#{SavingDeposit.newDate1}" size="10">                                             
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid  columnClasses="col8,col8" columns="2" id="gridpanel3" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblAmt" styleClass="label" value="Amount"/>
                            <h:inputText id="txtAmt" maxlength="12" styleClass="input" style="width:100px" value="#{SavingDeposit.amount}">
                            </h:inputText>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel4" width="100%" styleClass="row2" style="height:200px;">
                        <rich:dataTable value="#{SavingDeposit.tableDtList}" var="dataItem" rowClasses="gridrow1, gridrow2" id="taskList" columnsWidth="100" 
                                        rows="7" rowKeyVar="row" onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                        onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%" onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;
                                        #{rich:component('menu')}.show(event,{addIntEnter'#{dataItem.addIntEnter}', currentRow:'#{row}'});return false;">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column><h:outputText value="Effective Date"/> </rich:column>
                                    <rich:column><h:outputText value="Amount"/></rich:column>
                                    <rich:column><h:outputText value="EnterBy"/></rich:column>
                                    <rich:column><h:outputText value="Select Record" /></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{dataItem.applicableDate}"/></rich:column>
                            <rich:column><h:outputText  value="#{dataItem.addIntAmount}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.addIntEnter}"/></rich:column>
                            <rich:column style="text-align:center;width:40px">
                                <a4j:commandLink ajaxSingle="true" id="selectlink" reRender="txtAmt,gridpanel3,dtEffective,btnSave,btnDetail" action="#{SavingDeposit.setRowValues}">
                                    <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                    <f:setPropertyActionListener value="#{dataItem}" target="#{SavingDeposit.tableDt}"/>
                                </a4j:commandLink>
                            </rich:column>
                        </rich:dataTable>
                        <rich:datascroller align="left" for="taskList" maxPages="5"/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel" styleClass="vtop">
                            <a4j:commandButton  action="#{SavingDeposit.saveRecord}" id="btnSave" value="Save" reRender="errorGrid,txtAmt,gridPanel4" disabled="#{SavingDeposit.saveFlg}"/>
                            <a4j:commandButton id="btnDetail" value="Update" action="#{SavingDeposit.setRowUpdate}" reRender="errorGrid,txtAmt,taskList" disabled="#{SavingDeposit.updateFlg}"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{SavingDeposit.refresh()}" reRender="dtEffective,txtAmt,errorGrid,btnSave,btnDetail"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{SavingDeposit.exitForm}" reRender="dtEffective,txtAmt,errorGrid"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
