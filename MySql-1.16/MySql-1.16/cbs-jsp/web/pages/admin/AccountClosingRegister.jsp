<%-- 
    Document   : AccountClosingRegister
    Created on : Dec 3, 2010, 4:02:50 PM
    Author     : ROHIT KRISHNA GUPTA
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
            <title>Account Closing Register</title>
            <script type="text/javascript">

            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{AccountClosingRegister.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Account Closing Register"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>  
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{AccountClosingRegister.userName}"/>   
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7;border:1px ridge #BED6F8;">
                        <h:outputText id="errorMessage" styleClass="error" value="#{AccountClosingRegister.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{AccountClosingRegister.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="6" id="a3" style="height:30px;border:1px ridge #BED6F8;" styleClass="row1" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                        <h:outputLabel id="lblAcNo" styleClass="label" value="Account No. :" ><font class="required" color="red">*</font></h:outputLabel>
                            <a4j:region id="acnoRegion">
                                <h:panelGroup>
                                    <h:inputText id="txtAcNo" tabindex="1" size="#{AccountClosingRegister.acNoMaxLen}" maxlength="#{AccountClosingRegister.acNoMaxLen}" disabled="#{AccountClosingRegister.acNoDisFlag}" value="#{AccountClosingRegister.oldAcctNo}" styleClass="input">
                                        <a4j:status onstart="#{rich:component('wait')}.show()" for="acnoRegion" onstop="#{rich:component('wait')}.hide()" />
                                        <a4j:support actionListener="#{AccountClosingRegister.customerDetail}" event="onblur" oncomplete="if(#{AccountClosingRegister.errorMessage=='PLEASE ENTER ACCOUNT NO.'}){#{rich:element('txtAcNo')}.disabled=false;}
                                                     else if(#{AccountClosingRegister.errorMessage=='PLEASE ENTER ACCOUNT NO. CAREFULLY.'}){#{rich:element('txtAcNo')}.focus();}
                                                     else if(#{AccountClosingRegister.errorMessage=='PROBLEM IN GETTING ACCOUNT DETAIL !!!'}){#{rich:element('txtAcNo')}.focus();}
                                                     else if(#{AccountClosingRegister.errorMessage=='ACCOUNT NATURE NOT FOUND FOR THIS ACCOUNT NO. !!!'}){#{rich:element('txtAcNo')}.focus();}
                                                     else if(#{AccountClosingRegister.errorMessage=='THIS ACCOUNT NO. DOES NOT EXISTS !!!'}){#{rich:element('txtAcNo')}.focus();}
                                                     else if(#{AccountClosingRegister.errorMessage=='THIS ACCOUNT HAS BEEN CLOSED !!!'}){#{rich:element('txtAcNo')}.focus();}
                                                     else{#{rich:element('btnIntCal')}.focus();#{rich:element('txtAcNo')}.disabled=true;}"
                                                     reRender="message,errorMessage,chqtable,chqTaskList,a3,a4,a5,a6,a7,a8,gpFooter,table,taskList,txtAcNo,stxtAcctNo,otxtNewAcno" limitToList="true" />
                                    </h:inputText>

                            </h:panelGroup>
                        </a4j:region>
                        <h:outputLabel id="lblAcctNo" styleClass="label" value="Account No. :" />
                        <h:outputText id="stxtAcctNo" styleClass="output" value="#{AccountClosingRegister.acctNo1}" style="color:purple;"/>
                        <h:outputLabel id="lblCustName" styleClass="label" value="Customer Name :" />
                        <h:outputText id="stxtCustName" styleClass="output" value="#{AccountClosingRegister.custName}" style="color:purple;"/>
                    </h:panelGrid>
                    <h:panelGrid columns="6" id="a4" style="height:30px;border:1px ridge #BED6F8;" styleClass="row2" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                        <h:outputLabel id="lblAcType" styleClass="label" value="Account Type :" />
                        <h:outputText id="stxtAcType" styleClass="output" value="#{AccountClosingRegister.acctType}" style="color:purple;"/>
                        <h:outputLabel id="lblAcStatus" styleClass="label" value="Account Status :"/>
                        <h:outputText id="stxtAcStatus" styleClass="output" value="#{AccountClosingRegister.accStatus}" style="color:purple;"/>
                        <h:outputLabel id="lblTodayTxnAmt" styleClass="label" value="Today Txn Amount(Rs.) :"/>
                        <h:outputText id="stxtTodayTxnAmt" styleClass="output" value="#{AccountClosingRegister.todayTxnAmt}" style="color:purple;"/>
                    </h:panelGrid>
                    <h:panelGrid columns="6" id="a5" style="height:30px;border:1px ridge #BED6F8;" styleClass="row1" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                        <h:outputLabel id="lblAvailBal" styleClass="label" value="Available Balance(Rs.) :" />
                        <h:outputText id="stxtAvailBal" styleClass="output" value="#{AccountClosingRegister.availBal}" style="color:purple;"/>
                        <h:outputLabel id="lblPendingBal" styleClass="label" value="Pending Balance(Rs.) :" />
                        <h:outputText id="stxtPendingBal" styleClass="output" value="#{AccountClosingRegister.pendingBal}" style="color:purple;"/>
                        <h:outputLabel id="lblAcInstruction" styleClass="label" value="Account Instructions :" />
                        <h:outputText id="stxtAcInstruction" styleClass="output" value="#{AccountClosingRegister.accInstruction}" style="color:purple;"/>
                    </h:panelGrid>
                    <h:panelGrid columns="6" id="a8" style="height:30px;border:1px ridge #BED6F8;display:#{AccountClosingRegister.closureFlag}" styleClass="row1" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                        <h:outputLabel id="lblCloserChg" styleClass="label" value="Account Closure Charges:" />
                        <h:inputText id="stxtCloserChg" styleClass="output" value="#{AccountClosingRegister.closerCharge}" style="color:purple;width:100px;"/>

                        <h:outputLabel id="lblServCloserChg" styleClass="label" value="Services Tax charges:" />
                        <h:inputText id="stxtServCloserChg" styleClass="output" value="#{AccountClosingRegister.serviceTax}" style="color:purple;width:100px;"/>

                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columns="4" id="a7" style="width:100%;text-align:left;display:#{AccountClosingRegister.flag3}" styleClass="row2" columnClasses="col3,col21,col2,col20" width="100%">
                        <h:outputLabel id="lblAccStatus" styleClass="label" value="Interest Status"/>
                        <h:outputText id="stxtAccStatus" styleClass="blink_text" style="color:blue;"value="#{AccountClosingRegister.newStatus}"/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>     
                    <h:panelGrid columnClasses="vtop" columns="1" id="chqtable" style="height:auto;border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{AccountClosingRegister.unusedChqDetail}" var="dataItem" rendered="#{AccountClosingRegister.flag1=='true'}"
                                            rowClasses="gridrow1,gridrow2" id="chqTaskList" rows="4" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="15"><h:outputText value="List Of Unused Cheques" /></rich:column>
                                        <rich:column breakBefore="true" width="50%"><h:outputText value="Cheque No." /></rich:column>
                                        <rich:column width="50%"><h:outputText value="Issue Date" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column width="50%"><h:outputText value="#{dataItem.chqNo}" /></rich:column>
                                <rich:column width="50%"><h:outputText value="#{dataItem.issueDt}" /></rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="chqTaskList" maxPages="20" rendered="#{AccountClosingRegister.flag1=='true'}"/>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columns="6" id="a6" style="height:30px;" styleClass="footer" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnIntCal" tabindex="2" disabled="#{AccountClosingRegister.intCalDisFlag}" value="Interest Calculation" oncomplete="#{rich:element('btnSecDetail')}.focus();" action = "#{AccountClosingRegister.redirectOnSBInterestCalculation}" reRender="message,errorMessage,chqtable,chqTaskList,a3,a4,a5,a6,gpFooter,table,taskList"/>
                            <a4j:commandButton id="btnSecDetail" tabindex="3" disabled="#{AccountClosingRegister.secDisFlag}" oncomplete="#{rich:element('btnLockerSur')}.focus();"
                                               action = "#{AccountClosingRegister.redirectOnSecurityDetail}" value="Security Detail" reRender="message,errorMessage,chqtable,chqTaskList,a3,a4,a5,a6,gpFooter,table,taskList"/>
                            <a4j:commandButton id="btnLockerSur" tabindex="4" disabled="#{AccountClosingRegister.lockerDisFlag}" oncomplete="#{rich:element('btnStandInst')}.focus();"
                                               action = "#{AccountClosingRegister.redirectOnLockerSurr}" value="Locker Surrender" reRender="message,errorMessage,chqtable,chqTaskList,a3,a4,a5,a6,gpFooter,table,taskList"/>
                            <a4j:commandButton id="btnStandInst" tabindex="5" disabled="#{AccountClosingRegister.standInstDisFlag}" oncomplete="#{rich:element('btnEmiMaster')}.focus();"
                                               action = "#{AccountClosingRegister.redirectOnInstrCancel}" value="Standing Instruction" reRender="message,errorMessage,chqtable,chqTaskList,a3,a4,a5,a6,gpFooter,table,taskList"/>
                            <a4j:commandButton id="btnEmiMaster" tabindex="6" disabled="#{AccountClosingRegister.emiDisFlag}" oncomplete="if(#{AccountClosingRegister.flag2=='false'}){#{rich:element('btnCloseAcct')}.focus();}else{#{rich:element('btnRefresh')}.focus();}"
                                               action = "#{AccountClosingRegister.redirectOnEMIMaster}" value="EMI Master"  reRender="message,errorMessage,chqtable,chqTaskList,a3,a4,a5,a6,gpFooter,table,taskList"/>
                            <a4j:commandButton id="btnCloseAcct" tabindex="7" value="Close Account" disabled="#{AccountClosingRegister.flag2}" oncomplete="#{rich:component('closePanel')}.show()" reRender="message,errorMessage,chqtable,chqTaskList,a3,a4,a5,a6,gpFooter,table,taskList"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="table" style="height:auto;border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{AccountClosingRegister.gridDetail}" var="dataItem" 
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="4" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="15"><h:outputText value="Deleted Accounts Details Which Are Not Authorized(Pending)" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Account No." /></rich:column>
                                        <rich:column><h:outputText value="Customer Name" /></rich:column>
                                        <rich:column><h:outputText value="Closed By" /></rich:column>
                                        <rich:column><h:outputText value="Authorize" /></rich:column>
                                        <rich:column width="20"><h:outputText value="Delete" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.acctNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.custName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.closeBy}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.authorize}" /></rich:column>
                                <rich:column>
                                    <a4j:commandLink ajaxSingle="true" id="deletelink" oncomplete="#{rich:component('deletePanel')}.show()" reRender="message,errorMessage,chqtable,chqTaskList,a3,a4,a5,a6,gpFooter,table,taskList">
                                        <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{AccountClosingRegister.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{AccountClosingRegister.currentRow}" />
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="20" />
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnRefresh" tabindex="8" value="Refresh" action="#{AccountClosingRegister.resetForm}" reRender="message,errorMessage,chqtable,chqTaskList,a3,a4,a5,a6,a7,a8,gpFooter,table,taskList" focus="txtAcNo"/>
                            <a4j:commandButton id="btnExit" tabindex="9" value="Exit"  action="#{AccountClosingRegister.exitBtnAction}" reRender="message,errorMessage,chqtable,chqTaskList,a3,a4,a5,a6,a7,a8,gpFooter,table,taskList"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
                <rich:messages></rich:messages>
            </a4j:form>
            <rich:modalPanel id="deletePanel" autosized="true" width="250" onshow="#{rich:element('btnYesDel')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure To Delete This Record?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesDel" ajaxSingle="true"  action="#{AccountClosingRegister.deleteClosedAcFromGrid}"
                                                       oncomplete="#{rich:component('deletePanel')}.hide();" reRender="message,errorMessage,chqtable,chqTaskList,a3,a4,a5,a6,gpFooter,table,taskList" focus="txtAcNo"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNoDel" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="closePanel" autosized="true" width="250" onshow="#{rich:element('btnYesClose')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure To Close This Account ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesClose" ajaxSingle="true"  action="#{AccountClosingRegister.closeAccountAction}"
                                                       oncomplete="#{rich:component('closePanel')}.hide();" reRender="message,errorMessage,chqtable,chqTaskList,a3,a4,a5,a6,gpFooter,table,taskList" focus="txtAcNo"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNoClose" onclick="#{rich:component('closePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>

        </body>
    </html>
</f:view>
