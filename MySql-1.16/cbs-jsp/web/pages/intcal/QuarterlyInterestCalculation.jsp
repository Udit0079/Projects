<%-- 
    Document   : QuarterlyInterestCalculation
    Created on : Sep 8, 2010, 6:23:15 PM
    Author     : Rohit Krishna Gupta
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
            <title>Quarterly Interest Calculation</title>
            <script type="text/javascript">

            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{QuarterlyInterestCalculation.todayDate}"/>
                        </h:panelGroup>

                        <h:outputLabel styleClass="headerLabel" value="Quarterly Interest Calculation"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{QuarterlyInterestCalculation.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="errorMessage" styleClass="error" value="#{QuarterlyInterestCalculation.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{QuarterlyInterestCalculation.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="2" id="a3" width="100%" style="height:30px;" styleClass="row1">
                        <h:outputLabel id="lblAcctType" styleClass="label" value="Account Type :" style="padding-left:350px;"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddAcctType" tabindex="1" styleClass="ddlist" value="#{QuarterlyInterestCalculation.acctType}" size="1" style="width: 102px">
                            <f:selectItems value="#{QuarterlyInterestCalculation.acctTypeList}"/>
                            <a4j:support event="onblur"  action="#{QuarterlyInterestCalculation.acTypeLostFocus}" reRender="a7,a8,message,errorMessage,gpFooter" focus="ddInterestOption"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="2" id="a4" width="100%" style="height:30px;" styleClass="row2">
                        <h:outputLabel id="lblInterestOption" styleClass="label" value="Interest Option :" style="padding-left:350px;"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddInterestOption" tabindex="2" styleClass="ddlist" value="#{QuarterlyInterestCalculation.intOpt}" size="1" style="width: 102px">
                            <f:selectItems value="#{QuarterlyInterestCalculation.intOptionList}"/>
                            <%--<a4j:support event="onchange" action="#{QuarterlyInterestCalculation.intOptLostFocus}" reRender="a7,message,errorMessage,gpFooter"/>--%>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="2" id="a5" width="100%" style="height:30px;" styleClass="row1">
                        <h:outputLabel id="lblQuarterEnding" styleClass="label" value="Quarter Ending :" style="padding-left:350px;"><font class="required" color="red">*</font></h:outputLabel>
                        <rich:calendar datePattern="dd/MM/yyyy" tabindex="3" id="calQuarterEnding" value="#{QuarterlyInterestCalculation.quarEndDt}" focus="btnCalculation" inputSize="12"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="2" id="a6" width="100%">
                        <h:panelGrid columnClasses="col9" columns="2" id="a7" width="100%" style="height:30px;" styleClass="row2">
                            <h:outputLabel id="lblAcToBeCr" styleClass="label" value="A/c.To Be Credited :" style="padding-left:70px;"/>
                            <h:outputText id="stxtAcToBeCr" styleClass="output" value="#{QuarterlyInterestCalculation.acNoCr}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="2" id="a8" width="100%" style="height:30px;" styleClass="row2">
                            <h:outputLabel id="lblCrAmt" styleClass="label" value="Cr Amount :" style="padding-left:70px;"/>
                            <h:outputText id="stxtCrAmt" styleClass="output" value="#{QuarterlyInterestCalculation.crAmt}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="2" id="a9" width="100%" style="height:30px;" styleClass="row1">
                            <h:outputLabel id="lblAcToBeDr" styleClass="label" value="A/c.To Be Debited :" style="padding-left:70px;"/>
                            <h:outputText id="stxtAcToBeDr" styleClass="output" value="#{QuarterlyInterestCalculation.acNoDr}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="2" id="a10" width="100%" style="height:30px;" styleClass="row1">
                            <h:outputLabel id="lblDrAmt" styleClass="label" value="Dr Amount :" style="padding-left:70px;"/>
                            <h:outputText id="stxtDrAmt" styleClass="output" value="#{QuarterlyInterestCalculation.drAmt}"/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="a19" style="height:auto;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{QuarterlyInterestCalculation.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="6" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="12"><h:outputText value="Details" /></rich:column>
                                        <rich:column width="20" breakBefore="true"><h:outputText value="Serial No." /></rich:column>
                                        <rich:column><h:outputText value="Account No." /></rich:column>
                                        <rich:column><h:outputText value="Customer Name" /></rich:column>
                                        <rich:column><h:outputText value="Starting Date" /></rich:column>
                                        <rich:column><h:outputText value="Installment" /></rich:column>
                                        <rich:column><h:outputText value="ROI" /></rich:column>
                                        <rich:column><h:outputText value="Balance" /></rich:column>
                                        <rich:column><h:outputText value="Interest" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.sNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.acno}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.custName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.stDate}" /></rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem.instalment}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem.roi}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem.balance}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem.interest}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="20" />
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnCalculation" tabindex="4" value="Calculate"
                                               oncomplete="if(#{QuarterlyInterestCalculation.calcFlag==true}){
                                                                                                                  #{rich:component('popUpRepPanel')}.show();
                                                                                                             }
                                                                                                             else if(#{QuarterlyInterestCalculation.calcFlag==false})
                                                                                                             {
                                                                                                                  #{rich:component('popUpRepPanel')}.hide();
                                                                                                              }"
                                               action="#{QuarterlyInterestCalculation.calculation}" reRender="a5,message,errorMessage,a19,taskList,a8,a10,popUpRepPanel" focus="btnPost"/>
                            <a4j:commandButton id="btnPost" tabindex="5" value="Post" oncomplete="#{rich:component('postPanel')}.show()" reRender="message,errorMessage" focus="ddAcctType"/>
                            <a4j:commandButton id="btnRefresh" tabindex="6" value="Refresh" action="#{QuarterlyInterestCalculation.resetForm}" reRender="a3,a4,a5,a7,a8,a9,a10,message,errorMessage,taskList,a19" focus="ddAcctType"/>
                            <a4j:commandButton id="btnExit" tabindex="7" value="Exit" action="#{QuarterlyInterestCalculation.exitFrm}" reRender="message,errorMessage" />
                            <%--<a4j:commandButton id="btnReport" tabindex="8" onclick="window.open('RDQuarterlyIntCalReport.jsp?intOption=#{QuarterlyInterestCalculation.intOpt}:#{QuarterlyInterestCalculation.reportMonth}', 'dependent=yes, menubar=no, toolbar=no','resizable=yes, width=1000pt, height=750pt'); return false;"
                                               value="Report" rendered="#{QuarterlyInterestCalculation.calcFlag==true}"  reRender="message,errorMessage" >
                            </a4j:commandButton>--%>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
                <rich:messages></rich:messages>
            </a4j:form>
            <rich:modalPanel id="postPanel" autosized="true" width="250" onshow="#{rich:element('btnPanelPost')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure For Posting ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnPanelPost" ajaxSingle="true" action="#{QuarterlyInterestCalculation.postInterest}"
                                                       oncomplete="#{rich:component('postPanel')}.hide();" reRender="message,errorMessage,lpg,a8,a3,a10,a4,a5,a7,a9,a19,taskList" focus="btnRefresh"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Cancel" id="btnPanelPostNo" onclick="#{rich:component('postPanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
             <rich:modalPanel id="popUpRepPanel" label="Form" width="1000" height="550" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpRepPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="#{QuarterlyInterestCalculation.reportName}" style="text-align:center;"/>
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
                                <a4j:include viewId="#{QuarterlyInterestCalculation.viewID}" />
                            </td>

                        </tr>
                    </tbody>
                </table>
            </rich:modalPanel>            
        </body>
    </html>
</f:view>
