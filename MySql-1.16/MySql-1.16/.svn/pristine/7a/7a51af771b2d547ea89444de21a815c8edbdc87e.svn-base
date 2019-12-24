
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
            <title>Centralized Penal Interest Calculation</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="CentralizedPenalInterestCalculation">
                <h:panelGrid bgcolor="#fff" columns="1" id="outerPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{CentralizedPenalInterestCalculation.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Centralized Penal Interest Calculation"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{CentralizedPenalInterestCalculation.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="errorMessage" styleClass="error" value="#{CentralizedPenalInterestCalculation.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{CentralizedPenalInterestCalculation.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                        <h:panelGrid columnClasses="col1,col1,col1,col1,col1,col1,col1,col1,col1" columns="9" id="Row1" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblBranchWise" styleClass="label" style="width: 100px" value="Branch Wise"><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="branchHeader" size="1"  value="#{CentralizedPenalInterestCalculation.branchCode}" styleClass="ddlist">
                                    <f:selectItems value="#{CentralizedPenalInterestCalculation.branchCodeList}" />
                                </h:selectOneListbox> 
                                <h:outputLabel id="lblAcNature" styleClass="label" value="A/c Nature:"><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddAcNature"  styleClass="ddlist" style="margin-right:80px;width: 70px" value="#{CentralizedPenalInterestCalculation.acNature}" size="1" >
                                    <f:selectItems value="#{CentralizedPenalInterestCalculation.acNatureList}" />
                                    <a4j:support action="#{CentralizedPenalInterestCalculation.blurAcctNature}" event="onblur"
                                                 reRender="Row1,Row2,message,errorMessage,lpg" limitToList="true" focus="ddAcType" />
                                </h:selectOneListbox>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col1,col1,col1,col1,col1,col1,col1,col1,col1" columns="9" id="Row2" style="width:100%;text-align:left;" styleClass="row2">
                                <h:outputLabel id="lblAcType" styleClass="label" value="A/c Type :"><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddAcType"  styleClass="ddlist" style="width: 70px" value="#{CentralizedPenalInterestCalculation.acType}" size="1" >
                                    <f:selectItems value="#{CentralizedPenalInterestCalculation.acTypeList}" />
                                    <a4j:support action="#{CentralizedPenalInterestCalculation.setMonthEnd}" event="onblur"
                                                 reRender="Row1,Row2,message,errorMessage,lpg" limitToList="true" focus="ddMonthEnd" />
                                </h:selectOneListbox>
                                <h:outputLabel id="lblMonthEnd" styleClass="label" value="#{CentralizedPenalInterestCalculation.lblMonthEnd}"><font class="required" color="red">*</font>
                                    <h:selectOneListbox id="ddMonthEnd" styleClass="ddlist" style="margin-left:80px;width: 70px" value="#{CentralizedPenalInterestCalculation.monthEnd}" size="1" >
                                        <f:selectItems value="#{CentralizedPenalInterestCalculation.monthEndList}" />
                                        <a4j:support action="#{CentralizedPenalInterestCalculation.monthEndlostFocus}" event="onblur" 
                                                     reRender="Row1,Row2,message,errorMessage,lpg" limitToList="true" focus="ddFinYear" />
                                    </h:selectOneListbox>
                                    <h:selectOneListbox id="ddFinYear" styleClass="ddlist" style="width: 75px" value="#{CentralizedPenalInterestCalculation.finYear}" size="1" >
                                        <f:selectItems value="#{CentralizedPenalInterestCalculation.finYearList}" />
                                    <a4j:support  event="onblur"/>
                                    </h:selectOneListbox> 
                                </h:outputLabel>
                                <h:outputLabel></h:outputLabel>
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1,col2" columns="1" id="InterestCal"  width="100%" styleClass="row2">

                        <rich:dataTable value ="#{CentralizedPenalInterestCalculation.intCalc}"
                                        rowClasses="row1, row2" id = "InterestCalTable" rows="2" columnsWidth="100" rowKeyVar="row" var ="item"
                                        onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                        onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="10"><h:outputText value="Details"/></rich:column>
                                    <rich:column breakBefore="true"><h:outputText value="S.No"  /> </rich:column>
                                    <rich:column><h:outputText value="Account No." /></rich:column>
                                    <rich:column><h:outputText value="Customer Name" /></rich:column>
                                    <rich:column><h:outputText value="From Date" /></rich:column>
                                    <rich:column><h:outputText value="To Date" /></rich:column>
                                    <rich:column><h:outputText value="ROI" /></rich:column>
                                    <rich:column><h:outputText value="Closing Bal" /></rich:column>
                                    <rich:column><h:outputText value="Product" /></rich:column>
                                    <rich:column><h:outputText value="Interest Amt" /></rich:column>
                                    <rich:column><h:outputText value="Interest Table Code" /></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column style="text-align:center;width:3%;"><h:outputText value="#{item.sNo}"/></rich:column>
                            <rich:column style="text-align:center;width:8%;"><h:outputText value="#{item.acNo}"/></rich:column>
                            <rich:column style="text-align:left;width:20%;"><h:outputText value="#{item.custName}"/></rich:column>
                            <rich:column style="text-align:center;width:10%;"><h:outputText value="#{item.firstDt}"/></rich:column>
                            <rich:column style="text-align:center;width:10%;"><h:outputText value="#{item.lastDt}"/></rich:column>
                            <rich:column style="text-align:right;width:7%;">
                                <h:outputText value="#{item.roi}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                            </rich:column>
                            <rich:column style="text-align:right;width:10%;">
                                <h:outputText value="#{item.closingBal}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                            </rich:column>
                            <rich:column style="text-align:right;width:10%;">
                                <h:outputText value="#{item.product}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                            </rich:column>
                            <rich:column style="text-align:right;width:10%;">
                                <h:outputText value="#{item.totalInt}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                            </rich:column>
                            <rich:column style="text-align:center;width:15%;"><h:outputText value="#{item.intTableCode}"/></rich:column>
                            <f:facet name="footer">
                                <rich:columnGroup style="background-color: #b0c4de;">
                                    <rich:column>Totals</rich:column>
                                    <rich:column></rich:column>
                                    <rich:column></rich:column>
                                    <rich:column></rich:column>
                                    <rich:column></rich:column>
                                    <rich:column></rich:column>
                                    <rich:column></rich:column>
                                    <rich:column></rich:column>
                                    <rich:column>
                                    </rich:column>
                                    <rich:column></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                        </rich:dataTable>

                        <rich:datascroller align="left" for="InterestCalTable" maxPages="20" />
                    </h:panelGrid>
                    <h:panelGrid id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">  
                        <h:panelGroup id="btnPanel">
                            <a4j:region id="btnPnl">
                                <a4j:commandButton id="btnCalculate" value="Calculate" action="#{CentralizedPenalInterestCalculation.centralizedPenalInterestCalculation}"
                                                   oncomplete="if(#{CentralizedPenalInterestCalculation.reportFlag==true}){
                                                   #{rich:component('popUpRepPanel')}.show();
                                                   }
                                                   else if(#{CentralizedPenalInterestCalculation.reportFlag==false})
                                                   {
                                                   #{rich:component('popUpRepPanel')}.hide();
                                                   }"
                                                   reRender="message,errorMessage,btnPost,InterestCal,popUpRepPanel"/>
                                <a4j:commandButton id="btnPost" value="Post" disabled="#{CentralizedPenalInterestCalculation.disablePost}" action="#{CentralizedPenalInterestCalculation.Post}"
                                                   reRender="Row1,Row2,message,errorMessage,InterestCal,btnPost"/>
                                <a4j:commandButton id="btnRefresh" value="Refresh" action="#{CentralizedPenalInterestCalculation.refreshAction}" reRender="message,errorMessage,leftPanel,InterestCal,btnPost,InterestCalTable" focus=""/>
                                <a4j:commandButton id="btnExit" value="Exit" action="#{CentralizedPenalInterestCalculation.exitAction}"/>
                            </a4j:region>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="btnPnl" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
            </a4j:form>
            <rich:modalPanel id="popUpRepPanel" label="Form" width="1000" height="550" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpRepPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Centralized Penal Interest Calculation" style="text-align:center;"/>
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
                                <a4j:include viewId="#{CentralizedPenalInterestCalculation.viewID}" />
                            </td>

                        </tr>
                    </tbody>
                </table>
            </rich:modalPanel> 
        </body>
    </html>
</f:view>