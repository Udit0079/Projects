<%--
    Document   : interestprovision
    Created on : Jul 20, 2011, 3:27:16 PM
    Author     : Ankit
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
            <title>DDS Interest Provision</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />

            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setYearMask();
                });
                function setYearMask() {
                    jQuery(".calInstYear").mask("9999");
                }
            </script>
        </head>
        <body>
            <a4j:keepAlive beanName="InterestProvision"/>
            <a4j:form id="provision">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel1" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stDate" styleClass="output" value="#{InterestProvision.todayDate}" />
                        </h:panelGroup>
                        <h:outputLabel id="OL30" styleClass="headerLabel" value="DDS Interest Provision"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{InterestProvision.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="gridPanel2"   width="100%" style="text-align:center"  styleClass="row1">
                        <h:outputText id="errMsg" value="#{InterestProvision.msg}" styleClass="error" />
                    </h:panelGrid>

                    <h:panelGrid style="width: 100%">
                        <h:panelGrid columnClasses="col3,col3,col3,col3" columns="4" id="Row2" style="width:100%;text-align:left;" styleClass="row2">
                            <%--h:outputLabel id="lblMonthEnd" styleClass="output" value="Quarter End / Year"></h:outputLabel>
                            <h:panelGroup layout="block">
                                <h:selectOneListbox id="month" value="#{InterestProvision.selectMonth}" styleClass="ddlist" size="1" style="width:100px" >
                                    <f:selectItem itemValue="MARCH"/>
                                    <f:selectItem itemValue="JUNE"/>
                                    <f:selectItem itemValue="SEPTEMBER"/>
                                    <f:selectItem itemValue="DECEMBER"/>
                                </h:selectOneListbox>
                                <h:inputText id="lblyear" value="#{InterestProvision.year}" styleClass="input calInstYear" style="width:40px;" maxlength="4"/>
                                <h:outputLabel value="YYYY" styleClass="output" style="color:purple"/>
                            </h:panelGroup--%>
                            <h:outputLabel id="lblAccountType" styleClass="output"  value="Account Type"/>
                            <h:selectOneListbox id="type" value="#{InterestProvision.typeBox}" styleClass="ddlist" size="1" style="width:60px" >
                                <f:selectItems value="#{InterestProvision.typeList}"/>
                                <a4j:support  action="#{InterestProvision.setDateAcTypeWise}" event="onblur"
                                                      reRender="errMsg,calFromDate,calToDate" focus="#{rich:clientId('calToDate')}InputDate"/>
                            </h:selectOneListbox> 
                            <h:outputLabel/>
                            <h:outputLabel/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3" columns="4" id="Row4" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblFromDate" styleClass="label" value = "From Date :"><font class="required" color="red">*</font></h:outputLabel>
                            <rich:calendar datePattern="dd/MM/yyyy"  id="calFromDate" readonly="true" disabled="#{InterestProvision.fromDisable}" value="#{InterestProvision.fromDate}" inputStyle="width:75px">
                                <a4j:support event="onchanged"/>
                            </rich:calendar>
                            <h:outputLabel id="lblToDate" styleClass="label" value="To Date :"><font class="required" color="red">*</font></h:outputLabel>
                            <rich:calendar datePattern="dd/MM/yyyy"  id="calToDate" disabled="#{InterestProvision.toDisable}" value="#{InterestProvision.toDate}" inputStyle="width:75px">
                                <a4j:support event="onchanged"/>
                            </rich:calendar>    
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3" columns="4" id="Row3" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel styleClass="output" value="Total No. of Accounts"/>
                            <h:outputLabel styleClass="output" id="lblTotalAc" value="#{InterestProvision.totalAc}"/>
                            <h:outputLabel styleClass="output" value="Total Amount to be Credited"/>
                            <h:outputLabel styleClass="output" id="lblTotalInterestAmount" value="#{InterestProvision.totalAmount}" >
                                <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                            </h:outputLabel>    
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="BtnPanel">
                            <a4j:commandButton id="btnCalculate" value="Calculate" action="#{InterestProvision.btnCalculate}" disabled="#{InterestProvision.cal}"  reRender="popUpRepPanel,lblTotalAc,lblTotalInterestAmount,errMsg,gridPanelTable,footerPanel"
                                               oncomplete="if(#{InterestProvision.reportflag==true}){#{rich:component('popUpRepPanel')}.show();}
                                               else if(#{InterestProvision.reportflag==false}){#{rich:component('popUpRepPanel')}.hide();} setYearMask();"/>
                            <a4j:commandButton id="btnPost" value="Post" action="#{InterestProvision.btnPosting}" disabled="#{InterestProvision.post}"  reRender="errMsg,footerPanel,txtOt" oncomplete="setYearMask()"/>
                            <a4j:commandButton id="btnRefersh" value="Refresh" reRender="mainPanel" action="#{InterestProvision.btnRefresh}" oncomplete="setYearMask();"/>
                            <a4j:commandButton id="btnClose" value="Exit" action="#{InterestProvision.exitAction}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanelTable" style=" width:100%;height:0px" styleClass="row1" columnClasses="vtop">
                        <a4j:region>
                            <rich:dataTable  value="#{InterestProvision.datagrid}" var="dataItem1"
                                             rowClasses="gridrow1, gridrow2" id="taskList1" rows="15" rowKeyVar="row"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                             onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column width="50px" ><h:outputText style="text-align:center" value="S No." /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Account No." /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Customer Name" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="From Date" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="To Date" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="ROI" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Product" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Interest Amount" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem1.sno}" style="text-align:center"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.acno}"style="text-align:center"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.name}"style="text-align:center"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.fromDt}"style="text-align:center"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem1.toDt}"style="text-align:center"/></rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem1.roi}"style="text-align:center">
                                        <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                    </h:outputText>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem1.product}"style="text-align:center">
                                        <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                    </h:outputText>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem1.iamt}"style="text-align:center">
                                        <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                    </h:outputText>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList1" maxPages="5"/>
                        </a4j:region>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
            </a4j:form>
            <rich:modalPanel id="popUpRepPanel" label="Form" width="1000" height="550" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpRepPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="DDS Interest Provision" style="text-align:center;"/>
                    </h:panelGrid>
                </f:facet>
                <f:facet name="controls">
                    <h:outputLink  id="closelink" onclick="#{rich:component('popUpRepPanel')}.hide()">
                        <h:graphicImage value="/resources/images/close.gif" style="border:0" />
                    </h:outputLink>>
                </f:facet>  
                <table width="100%">
                    <tbody>
                        <tr>
                            <td>
                                <a4j:include viewId="#{InterestProvision.viewID}" />
                            </td>

                        </tr>
                    </tbody>
                </table>
            </rich:modalPanel>
        </body>
    </html>
</f:view>