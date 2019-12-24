<%-- 
    Document   : AccountStatus
    Created on : 3 Dec, 2010, 5:17:31 PM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Account Status</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript"></script>
        </head>
        <body>
            <a4j:form>
                <h:panelGrid bgcolor="#fff" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header" width="100%">
                        <h:panelGroup id="groupPanel" layout="block" >
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date:" />
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{AccountStatus.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblincident" styleClass="headerLabel" value="Account Status"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lbluser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{AccountStatus.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="d22" style="width:100%;text-align:center;" styleClass="row2"  >
                        <h:outputText id="stxtmessage" styleClass="headerLabel" value="#{AccountStatus.message}"  style="width:100%;color:red"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col7,col7" columns="2" id="a9" width="100%">
                        <h:panelGrid columns="2" columnClasses="col7,col7" id="accountType" style="width:100%;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblAccountNumber" styleClass="headerLabel"  value="Account Number"  style="width:100%;text-align:left;"><font class="required" color="red">*</font></h:outputLabel>
                            <h:panelGroup layout="block"  style="width:100%;text-align:left;" >
                                <h:inputText id="txtAccountNumber" styleClass="input" maxlength="#{AccountStatus.acNoMaxLen}" style="width: 120px" value="#{AccountStatus.oldAcno}">
                                    <a4j:support id="a4j2" event="onblur" focus="ddnStatus" limitToList="true" actionListener="#{AccountStatus.custName}" reRender="a9,Name,lien,ll,pStatus,nStatus,wefDate,remarks,ReportDate,ReportDate1,a19,gridpanel6,d22,otxtAcno"/>
                                </h:inputText>
                                <h:outputText id="otxtAcno" styleClass="output" value="#{AccountStatus.acno}" style="color:green"/>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid columns="2" columnClasses="col7,col7"  id="Name" style="width:100%;"  styleClass="row1" width="100%"  >
                            <h:outputLabel id="lblName" styleClass="headerLabel"  value="Name"  style="width:100%;text-align:left;"></h:outputLabel>
                            <h:outputText id="txtName" styleClass="output" value="#{AccountStatus.name}" style="color:purple;"/>
                        </h:panelGrid>
                        <h:panelGrid columns="2" columnClasses="col7,col7" id="pStatus" style="height:30px;"  styleClass="row2" width="100%"  >
                            <h:outputLabel id="lblpStatus" styleClass="headerLabel"  value="Present Status"  style="width:100%;text-align:left;"> </h:outputLabel>
                            <h:outputText id="txtpStatus" styleClass="output" value="#{AccountStatus.pStatus}"/>
                        </h:panelGrid>
                        <h:panelGrid columns="2" columnClasses="col7,col7,col7" id="nStatus"  style="height:30px;"  styleClass="row2" width="100%"  >
                            <h:outputLabel id="lblnStatus" styleClass="headerLabel"  value="New Status"  style="width:100%;text-align:left;"><font class="required" color="red">*</font></h:outputLabel>
                            <h:panelGroup layout="block"  style="width:100%;text-align:left;" >
                                <h:selectOneListbox id="ddnStatus" styleClass="ddlist" size="1" style="width: 120px"   value="#{AccountStatus.nStatus}">
                                    <f:selectItems value="#{AccountStatus.statusList}"/>
                                    <a4j:support id="soppt" event="onchange" oncomplete="if(#{rich:element('ddnStatus')}.value == 'Lien Marked'){#{rich:element('txtlienAccountNumber')}.focus();}
                                                 else{#{rich:element('ddnStatus')}.focus();}"
                                                 reRender="a9,txtlienAccountNumber,accountType,Name,lien,ll,pStatus,nStatus,wefDate,remarks,ReportDate,ReportDate1,a19,gridpanel6,d22" actionListener="#{AccountStatus.isLienMarked}"/>
                                </h:selectOneListbox>
                            </h:panelGroup>
                        </h:panelGrid>    
                        <h:panelGrid columns="2" columnClasses="col7,col7,col7" id="lien" style="height:30px;" rendered="#{AccountStatus.flag=='true'}"  styleClass="row1" width="100%"  >
                            <h:outputLabel id="lbllien" styleClass="headerLabel"  value="Lien Account No."  style="width:100%;text-align:left;"><font class="required" color="red">*</font></h:outputLabel>
                            <h:panelGroup layout="block"  style="width:100%;text-align:left;" >
                                <h:inputText id="txtlienAccountNumber" maxlength="12" styleClass="input" style="width:120px" value="#{AccountStatus.oldLiencode}" onkeyup="this.value = this.value.toUpperCase();">
                                    <a4j:support action="#{AccountStatus.getNewLineAcNo}" reRender="otxtLineAcno,stxtmessage" event="onblur"/>
                            </h:inputText>
                                <h:outputText id="otxtLineAcno" styleClass="output" value="#{AccountStatus.liencode}" style="color:green"/>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid columns="2" columnClasses="col7,col7" id="ll" style="height:30px;" rendered="#{AccountStatus.flag=='true'}"  styleClass="row1" width="100%"  >
                            <h:outputLabel id="lbllamt" styleClass="headerLabel"  value="Lien Amount"  style="width:120px;text-align:left;"> </h:outputLabel>
                            <h:inputText id="txtlamt" styleClass="input" value="#{AccountStatus.lienAmt}" maxlength="15" style="width:120px"/>
                        </h:panelGrid>
                        <h:panelGrid columns="3" columnClasses="col7,col7,col7" id="wefDate" style="height:30px;"  styleClass="row2" width="100%"  >
                            <h:outputLabel id="lblwefDate" styleClass="headerLabel"  value="W.E.F.Date"  style="width:100%;text-align:left;"><font class="required" color="red">*</font></h:outputLabel>
                            <h:panelGroup layout="block"  style="width:100%;text-align:left;" >
                                <rich:calendar id="ddwefDate" styleClass="rich-calendar-input" datePattern="dd/MM/yyyy" value="#{AccountStatus.wefDate}" inputSize="10"/>
                                
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid columns="2" columnClasses="col7,col7,col7" id="remarks" style="height:30px;"   styleClass="row2" width="100%"  >
                            <h:outputLabel id="lblRemarks" styleClass="headerLabel"  value="Remarks"  style="width:100%;text-align:left;"></h:outputLabel>
                            <h:inputText id="txtRemarks" maxlength="100" styleClass="input" style="width: 200px" disabled="#{AccountStatus.fflag=='false'}" value="#{AccountStatus.remarks}" onblur="this.value = this.value.toUpperCase();"/>
                        </h:panelGrid>
                        <h:panelGrid columns="2" columnClasses="col7,col7,col7" id="ReportDate" style="height:30px;"  styleClass="row1" width="100%"  >
                            <h:outputLabel id="lblReportDate" styleClass="headerLabel"  value="Report Date"  style="width:100%;text-align:left;"><font class="required" color="red">*</font></h:outputLabel>
                            <rich:calendar id="ddReportDate" styleClass="rich-calendar-input" datePattern="dd/MM/yyyy" value="#{AccountStatus.reportDate}" inputSize="10"/>
                        </h:panelGrid>
                        <h:panelGrid columns="2" columnClasses="col7,col7,col7" id="ReportDate1" style="height:30px;"  styleClass="row1" width="100%"  >
                            <h:selectBooleanCheckbox id="includeFl" >
                                <a4j:support ajaxSingle="true" event="onclick">
                                    <a4j:actionparam name="rowId" value="#{result.rowId}" />
                                </a4j:support>
                                <h:outputLabel id="lblnForAllStatusReport" styleClass="headerLabel"  value="For All Status Report"  style="width:100%;text-align:left;"></h:outputLabel>
                            </h:selectBooleanCheckbox>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="a19" style="height:auto;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{AccountStatus.incis}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="6" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="8"><h:outputText value="Details" /></rich:column>
                                        <rich:column breakBefore="true"  ><h:outputText  value="AccountNo"  /></rich:column>
                                        <rich:column><h:outputText value="Remark"/></rich:column>
                                        <rich:column><h:outputText value="Status" /></rich:column>
                                        <rich:column visible="false"><h:outputText value="Date" /></rich:column>
                                        <rich:column><h:outputText value="Amount" /></rich:column>
                                        <rich:column><h:outputText value="Auth" /></rich:column>
                                        <rich:column><h:outputText value="EnterBy" /></rich:column>
                                        <rich:column ><h:outputText value="WEF Date"/></rich:column>
                                        <rich:column><h:outputText value="Marking Date" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column ><h:outputText value="#{dataItem.acctNo}" /></rich:column>
                                <rich:column ><h:outputText value="#{dataItem.remark}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.spFlag}" /></rich:column>
                                <rich:column visible="false"><h:outputText value="#{dataItem.date}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.amount}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.auth}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.enterBy}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.efftDate}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.date}" /></rich:column>
                            </rich:dataTable>
                            <rich:datascroller  align="left" for="taskList"  maxPages="20" />
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columns="5" id="gridpanel6" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="a1" layout="block" >
                            <a4j:commandButton id="btnSave" disabled="#{AccountStatus.fflag=='false'}" value="Save" oncomplete="#{rich:component('savePanel')}.show()" reRender="btnSave"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" reRender="a9,accountType,Name,lien,ll,pStatus,nStatus,wefDate,remarks,ReportDate,ReportDate1,a19,gridpanel6,d22" action="#{AccountStatus.reFresh}" focus="ddAccountNumber">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{AccountStatus.exitBtnAction}" reRender="stxtmessage,a9,c9,d91,lien,ll,a92,a93,a19">
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
                <rich:messages>   </rich:messages>
            </a4j:form>
            <rich:modalPanel id="savePanel" autosized="true" width="200" onshow="#{rich:element('btnYes')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Do You Want To Save This Record" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%">
                        <tbody>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" action="#{AccountStatus.save}" focus="btnRefresh"
                                                       oncomplete="#{rich:component('savePanel')}.hide();" reRender="a9,accountType,Name,lien,ll,pStatus,nStatus,wefDate,remarks,ReportDate,ReportDate1,a19,gridpanel6,d22"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Cancel" id="btnNo" onclick="#{rich:component('savePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
