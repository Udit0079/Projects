<%-- 
    Document   : AllBranchTdRdDsIntCalc
    Created on : 28 Oct, 2017, 11:05:53 AM
    Author     : root
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
            <title>All Term Deposit/RD Interest Calculation</title>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{AllBranchTdRdDsIntCalc.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="All Term Deposit/RD Interest Calculation"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{AllBranchTdRdDsIntCalc.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="errorMessage" styleClass="error" value="#{AllBranchTdRdDsIntCalc.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{AllBranchTdRdDsIntCalc.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="9" id="a3" style="height:30px;" styleClass="row1" width="100%" columnClasses="col1,col1,col1,col1,col1,col1,col1,col1,col1">
                        <h:outputText value="Branch Wise" styleClass="label"/>
                        <h:selectOneListbox id="branchHeader" size="1" value="#{AllBranchTdRdDsIntCalc.branchCode}" styleClass="ddlist">
                            <f:selectItems value="#{AllBranchTdRdDsIntCalc.branchCodeList}" />
                        </h:selectOneListbox> 
                        <h:outputLabel id="lblAcType" styleClass="label" value="Account Type :"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddAcType" styleClass="ddlist" style="width: 70px" value="#{AllBranchTdRdDsIntCalc.acType}" size="1" >
                            <f:selectItems value="#{AllBranchTdRdDsIntCalc.acTypeList}" />
                            <a4j:support action="#{AllBranchTdRdDsIntCalc.setdescription}" event="onblur" focus="ddIntOption"
                                         reRender="a3,a4,a5,a6,message,errorMessage,lpg,gpFooter,table,taskList" limitToList="true" />
                        </h:selectOneListbox>
                        <h:outputLabel id="lblIntOption" styleClass="label" value="Interest Option :"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddIntOption" styleClass="ddlist" style="width: 100px" value="#{AllBranchTdRdDsIntCalc.interestOption}" size="1" >
                            <f:selectItems value="#{AllBranchTdRdDsIntCalc.interestOptionList}" />
                            <a4j:support action="#{AllBranchTdRdDsIntCalc.setMonthEndQuarEnd}" event="onblur" focus="ddMonthEnd" 
                                         reRender="a3,a4,a5,a6,message,errorMessage,lpg,gpFooter,table,taskList" limitToList="true" />
                        </h:selectOneListbox>
                        
                        <h:outputLabel id="lblMonthEnd" styleClass="label" value="#{AllBranchTdRdDsIntCalc.lblMonthEnd}"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddMonthEnd" styleClass="ddlist" style="width: 100px" value="#{AllBranchTdRdDsIntCalc.monthEnd}" size="1" >
                            <f:selectItems value="#{AllBranchTdRdDsIntCalc.monthEndList}" />
                            <a4j:support action="#{AllBranchTdRdDsIntCalc.monthEndlostFocus}" event="onblur" focus="ddFinYear"
                                         reRender="a3,a4,a5,a6,message,errorMessage,lpg,gpFooter,table,taskList" limitToList="true"  />
                        </h:selectOneListbox>
                        <h:selectOneListbox id="ddFinYear" styleClass="ddlist" style="width: 75px" value="#{AllBranchTdRdDsIntCalc.finYear}" size="1" >
                            <f:selectItems value="#{AllBranchTdRdDsIntCalc.finYearList}" />
                        </h:selectOneListbox>                        
                    </h:panelGrid>
                    <h:panelGrid columns="6" id="a6" style="height:30px;" styleClass="row2" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                        <h:outputLabel id="lblAcToBeDebt" styleClass="label" value="Account To Be Debited(For TD Interest) :" style="text-align:left;"/>
                        <h:outputText id="stxtAcToBeDebt" styleClass="output" value="#{AllBranchTdRdDsIntCalc.glAcNo}" style="color:purple;text-align:left;"/>
                        <h:outputLabel id="lblDebtAmt" styleClass="label" value="Debit Amount  :" style="text-align:left;"/>
                        <h:outputText id="stxtDebtAmt" styleClass="output" value="#{AllBranchTdRdDsIntCalc.debtAmt}" style="color:purple;text-align:left;"/>
                    </h:panelGrid>
                    <h:panelGrid columns="6" id="a7" style="height:30px;" styleClass="row1" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                        <h:outputLabel id="lblAcToBeCr" styleClass="label" value="Account To Be Credited(For TDS) :" style="text-align:left;"/>
                        <h:outputText id="stxtAcToBeCr" styleClass="output" value="#{AllBranchTdRdDsIntCalc.acNoToBeCr}" style="color:purple;text-align:left;"/>
                        <h:outputLabel id="lblCrAmt" styleClass="label" value="Credit Amount   :" style="text-align:left;"/>
                        <h:outputText id="stxtCrAmt" styleClass="output" value="#{AllBranchTdRdDsIntCalc.crAmt}" style="color:purple;text-align:left;"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="table" style="height:auto;" width="100%" styleClass="row2">
                        <a4j:region>
                            <rich:dataTable value="#{AllBranchTdRdDsIntCalc.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" columnsWidth="100" rowKeyVar="row" 
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'" rows="15"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="21"><h:outputText value="Detail Of Account Numbers" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Serial No." /></rich:column>
                                        <rich:column><h:outputText value="Cust Id." /></rich:column>
                                        <rich:column><h:outputText value="Account No." /></rich:column>
                                        <rich:column><h:outputText value="Customer Name" /></rich:column>
                                        <rich:column><h:outputText value="RT No." /></rich:column>
                                        <rich:column><h:outputText value="Int To Acno" /></rich:column>
                                        <rich:column><h:outputText value="Int To Cust Name" /></rich:column>
                                        <rich:column><h:outputText value="Prin. Amt."/></rich:column>
                                        <rich:column><h:outputText value="ROI" /></rich:column>
                                        <rich:column><h:outputText value="Interest"/></rich:column>
                                        <rich:column><h:outputText value="Tds to be Deducted"/></rich:column>
                                        <rich:column><h:outputText value="Interest to be Posted"/></rich:column>
                                        <rich:column><h:outputText value="Control No." /></rich:column>
                                        <rich:column><h:outputText value="Total Interest Vouch Wise" /></rich:column>
                                        <rich:column><h:outputText value="Total Interest CustId Wise" /></rich:column>
                                        <rich:column><h:outputText value="Total TDS" /></rich:column>
                                        <rich:column><h:outputText value="Minor" /></rich:column>
                                        <rich:column><h:outputText value="Tds Flag" /></rich:column>
                                        <rich:column><h:outputText value="Major Int" /></rich:column>
                                        <rich:column><h:outputText value="Minor Int" /></rich:column>
                                        <rich:column><h:outputText value="Total Int" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.sno}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.custId}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.acno}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.custName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.rtNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.intToAcno}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.intToAcnoCustname}" /></rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem.prinAmt}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem.roi}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem.interest}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem.tds}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem.intToPosted}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column><h:outputText value="#{dataItem.controlNo}" /></rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem.totalIntPaidVouchWise}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem.totalInterest}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem.totalTds}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column><h:outputText value="#{dataItem.minorFlag}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.tdsFlag}" /></rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem.majorInterest}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem.minorInterest}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem.interestWithMinMaj}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="20" />
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnCalculate" value="Calculate" onclick="#{rich:component('calculatePanel')}.show()" rendered="#{AllBranchTdRdDsIntCalc.calcFlag1==false}" reRender="a3,a4,a5,a6,a7,message,errorMessage,lpg,gpFooter,table,taskList" focus=""/>
                            <a4j:commandButton id="btnPost" value="Post" onclick="#{rich:component('postPanel')}.show();#{rich:component('btnPost')}.disabled=true;" rendered="#{AllBranchTdRdDsIntCalc.calcFlag1==true}" reRender="a3,a4,a5,a6,a7,message,errorMessage,lpg,gpFooter,table,taskList" />
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{AllBranchTdRdDsIntCalc.resetForm}" oncomplete="#{rich:element('btnPost')}.disabled = true;" reRender="a3,a4,a5,a6,a7,message,errorMessage,lpg,gpFooter,table,taskList" />
                            <a4j:commandButton id="btnExit" value="Exit" action="#{AllBranchTdRdDsIntCalc.exitBtnAction}" reRender="message,errorMessage" />
                            <a4j:commandButton id="viewReport" value="Interest Report"
                                               reRender="a3,a4,a5,a6,a7,message,errorMessage,lpg,table,taskList" 
                                               oncomplete="#{rich:component('popUpRepPanel')}.show();"/>
                            
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
            </a4j:form>
            <rich:modalPanel id="calculatePanel" autosized="true" width="250" onshow="#{rich:element('btnYesCalC')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure To Calculate Interest ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesCalC" ajaxSingle="true" action="#{AllBranchTdRdDsIntCalc.callProcedureForCalculateAndPost}"
                                                       oncomplete="#{rich:component('calculatePanel')}.hide();
                                                       if(#{AllBranchTdRdDsIntCalc.calcFlag1==true}){
                                                            #{rich:component('popUpRepPanel')}.show();
                                                          }
                                                          else if(#{AllBranchTdRdDsIntCalc.calcFlag1==false})
                                                          {
                                                                #{rich:component('popUpRepPanel')}.hide();
                                                       
                                                          }"
                                                       reRender="a3,a4,a5,a6,a7,message,errorMessage,lpg,gpFooter,table,taskList,popUpRepPanel" />
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNoCalC" onclick="#{rich:component('calculatePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="postPanel" autosized="true" width="250" onshow="#{rich:element('btnYesPost')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure To Post ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesPost" ajaxSingle="true" action="#{AllBranchTdRdDsIntCalc.callProcedureForCalculateAndPost}"
                                                       oncomplete="#{rich:component('postPanel')}.hide();" reRender="a3,a4,a5,a6,a7,message,errorMessage,lpg,gpFooter,table,taskList" />
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNoPost" onclick="#{rich:component('postPanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="popUpRepPanel" label="Form" width="1000" height="550" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpRepPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Term Deposite Interest Calculation" style="text-align:center;"/>
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
                                <a4j:include viewId="#{AllBranchTdRdDsIntCalc.viewID}" />
                            </td>

                        </tr>
                    </tbody>
                </table>
            </rich:modalPanel>            
        </body>
    </html>
</f:view>

