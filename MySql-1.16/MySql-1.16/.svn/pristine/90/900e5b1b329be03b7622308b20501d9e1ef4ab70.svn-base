<%-- 
    Document   : Projected Tds Calculation
    Created on : 15 Sept, 2015, 6:41:40 PM
    Author     : Dhirendra Singh
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Projected TDS Calculation</title>
            <script type="text/javascript">
            </script>
        </head>
        <body>
            <a4j:form id="ProjectedTdsCalCulation">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{ProjectedTdsCalCulation.todayDate}" />
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Projected TDS Calculation"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ProjectedTdsCalCulation.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="panelError" width="100%" style="width:100%;text-align:center;" styleClass="row1">
                        <h:outputText id="stxtError" styleClass="error" value="#{ProjectedTdsCalCulation.error}"/>
                    </h:panelGrid>
                    <h:panelGrid  id="gridPanel1" columnClasses="col8,col8,col8" columns="3"  width="100%" styleClass="row2" style="width:100%;text-align:center;" >
                        <h:panelGroup id ="branch" layout="block">
                            <h:outputLabel id="brnName" styleClass="label" value="Branch Name"/>
                            <h:selectOneListbox size="1" id="brnList" styleClass="ddBrnList"value="#{ProjectedTdsCalCulation.brnName}">
                                <f:selectItems value="#{ProjectedTdsCalCulation.brnList}"/>
                            </h:selectOneListbox>
                        </h:panelGroup>
                        <h:panelGroup id="panelGroup1" layout="block">
                            <h:outputLabel id="label1" styleClass="label" value="Quarter End" />
                            <h:selectOneListbox size="1" id="list1" styleClass="ddlist"value="#{ProjectedTdsCalCulation.monthType}">
                                <f:selectItems value="#{ProjectedTdsCalCulation.monthTypeOption}"/>
                                <a4j:support event="onblur"/>
                            </h:selectOneListbox>
                            <h:selectOneListbox id="list2" size="1" styleClass="ddlist" value="#{ProjectedTdsCalCulation.year}">
                                <f:selectItems value="#{ProjectedTdsCalCulation.finYearList}"/>
                                <a4j:support event="onblur"/>
                            </h:selectOneListbox>
                        </h:panelGroup>
                        <h:panelGroup></h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel2" width="100%" style="text-align:center;">
                        <h:panelGrid  columns="1" id="gridpanel3" style="height:30px;" styleClass="row1" width="100%" >
                            <h:outputLabel id="label2" styleClass="label" value="A/C To Be Credited[For TDS]" />
                        </h:panelGrid>
                        <h:panelGrid columns="1" id="gridpanel4" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputText id="text1" styleClass="output" value="#{ProjectedTdsCalCulation.accToBeCredited}"/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gridPanel3" width="100%" style="text-align:center;" styleClass="row2" >
                        <h:outputText id="textMsg" styleClass="msg" value="Projected TDS may differ from Actual TDS"/>
                    </h:panelGrid>
                    <h:panelGrid id="mainPane2" bgcolor="#fff" styleClass="row2" style="border:1px ridge #BED6F8" width="100%">
                        <a4j:region >
                            <rich:dataTable rows="15" value="#{ProjectedTdsCalCulation.reportDetail}"var="dataItem"
                                            rowClasses="gridrow1, gridrow2" id="tableDetails"  columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                            onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="12"><h:outputText value="Projected TDS Calculation Details" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="S.No." /></rich:column>
                                        <rich:column><h:outputText value="Cust Id" /></rich:column>
                                        <rich:column><h:outputText value="A/C No." /></rich:column>
                                        <rich:column><h:outputText value="Name" /></rich:column>
                                        <rich:column><h:outputText value="Voucher No." /></rich:column>
                                        <rich:column><h:outputText value="Option" /></rich:column>
                                        <rich:column><h:outputText value="Status" /></rich:column>
                                        <rich:column><h:outputText value="TDS Deducted" /></rich:column>
                                        <rich:column><h:outputText value="TDS Calculated" /></rich:column>
                                        <rich:column><h:outputText value="TDS To Be Deducted" /></rich:column>
                                        <rich:column><h:outputText value="Interest Paid" /></rich:column>
                                        <rich:column><h:outputText value="Interest Calculated" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.sNo}"/></rich:column>
                                <rich:column><h:outputText  value="#{dataItem.custId}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.accNo}" /></rich:column>
                                <rich:column><h:outputText  value="#{dataItem.name}" /></rich:column>
                                <rich:column ><h:outputText  value="#{dataItem.voucherNo}" /></rich:column>
                                <rich:column ><h:outputText  value="#{dataItem.option}" /></rich:column>
                                <rich:column ><h:outputText  value="#{dataItem.status}" /></rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem.tdsDeducted}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem.tdsCalculated}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem.tdsToBeDed}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem.intPaid}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem.intCalculated}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller  id="dataScroll"for="tableDetails" maxPages="20" align="left">
                            </rich:datascroller>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:region id="calRegion">
                                <a4j:commandButton  id="btnCalculation" value="Calculation" action="#{ProjectedTdsCalCulation.getTableDetails}" disabled="#{ProjectedTdsCalCulation.cFlag}" reRender="text1,mainPane2,textForDate1,textForDate2,stxtError,dataScroll,tableDetails,popUpRepPanel,btnPost,btnCalculation"
                                                    oncomplete="if(#{ProjectedTdsCalCulation.calcFlag==true})
                                                                    { 
                                                                        #{rich:component('popUpRepPanel')}.show();
                                                                    }
                                                                    else if(#{ProjectedTdsCalCulation.calcFlag==false})
                                                                    {
                                                                       #{rich:component('popUpRepPanel')}.hide();
                                                                    }  " >
                                    
                                </a4j:commandButton>
                            </a4j:region>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{ProjectedTdsCalCulation.refreshForm}" reRender="mainPanel"/>
                            <a4j:commandButton id="btnClose" value="Exit" action="#{ProjectedTdsCalCulation.exitForm}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="calRegion"/>
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
                <rich:messages></rich:messages>
            </a4j:form>
              <rich:modalPanel id="popUpRepPanel" label="Form" width="1000" height="550" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpRepPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="TDS Calculation" style="text-align:center;"/>
                    </h:panelGrid>
                </f:facet>
                <f:facet name="controls">
                    <h:outputLink  id="closelink" onclick="#{rich:component('popUpRepPanel')}.hide()">
                        <h:graphicImage value="/resources/images/close.gif" style="border:0" />
                    </h:outputLink>
                </f:facet>  
                <table width="100%">
                    <tbody>
                        <tr>
                            <td>
                                <a4j:include viewId="#{ProjectedTdsCalCulation.viewID}" />
                            </td>

                        </tr>
                    </tbody>
                </table>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
