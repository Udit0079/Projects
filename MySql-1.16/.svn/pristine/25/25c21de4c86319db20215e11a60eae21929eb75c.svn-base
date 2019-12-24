<%-- 
    Document   : NPAOverdueReminderCharges
    Created on : 8 Aug, 2017, 11:54:55 AM
    Author     : Manish kumar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE html>
<f:view>
<html>
    <head>
        <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Overdue Reminder Charges</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".dt").mask("99/99/9999");
                }
            </script>
    </head>
    <body>
        <h:form id="npaOverdueReminderChages" enctype="multipart/form-data">
            <h:panelGrid id="mainPanel" columns="1" style="border:1px ridge #BED6F8" width="100%">
                <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{nPAOverdueReminderCharges.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="OVER DUE REMINDER CHARGES"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{nPAOverdueReminderCharges.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
            </h:panelGrid>
            <h:panelGrid id="errormsg" style="height:25px;text-align:center" styleClass="#{nPAOverdueReminderCharges.msgStyle}" width="100%">
                <h:outputText id="errmsg" value="#{nPAOverdueReminderCharges.msg}"/>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel1" width="100%" styleClass="row2">
                <h:outputLabel value="Mode" id="mode" styleClass="label"/>
                <h:selectOneListbox id="modeId" value="#{nPAOverdueReminderCharges.mode}" styleClass="ddlist" size="1"  style="width:100px" >
                    <f:selectItems value="#{nPAOverdueReminderCharges.modeOption}" />  
                    <a4j:support event="onblur" action="#{nPAOverdueReminderCharges.intAcountNature}"  reRender="show,acountNatureId,chargesId,errmsg,save,print,pdf,txnGrid" focus="acountNatureId" />
                </h:selectOneListbox>
                <h:outputLabel value="Account Nature" id="acountNature" styleClass="label"/>
                <h:selectOneListbox id="acountNatureId" value="#{nPAOverdueReminderCharges.acNature}"  styleClass="ddlist" size="1"  style="width:100px" >
                    <f:selectItems value="#{nPAOverdueReminderCharges.acNatureOption}" />  
                    <a4j:support event="onblur" action="#{nPAOverdueReminderCharges.intAcountType}"  reRender="acountTypeId,chargesId,errmsg" focus="acountTypeId" />
                </h:selectOneListbox>

                <h:outputLabel value="Account Type" id="acountType" styleClass="label"/>
                <h:selectOneListbox id="acountTypeId" value="#{nPAOverdueReminderCharges.acType}" styleClass="ddlist" size="1"  style="width:100px" >
                    <f:selectItems value="#{nPAOverdueReminderCharges.acTypeOption}" />     
                    <a4j:support event="onblur" action="#{nPAOverdueReminderCharges.initData}"  reRender="overDueRemId,errmsg" focus="overDueRemId" />
                </h:selectOneListbox>

                
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel2" width="100%" styleClass="row1">
                <h:outputLabel value="Over Due Reminder" id="overDueRem" styleClass="label"/>
                <h:selectOneListbox id="overDueRemId" value="#{nPAOverdueReminderCharges.overDueRem}"  styleClass="ddlist" size="1"  style="width:130px" >
                    <f:selectItems value="#{nPAOverdueReminderCharges.overDueRemOption}"  />              
                </h:selectOneListbox>

                <h:outputLabel value="Date" styleClass="label"/>
                <h:inputText id="dt" styleClass="input dt" maxlength="10"  value="#{nPAOverdueReminderCharges.dt}" style="width:100px;setMask();" >
                </h:inputText>
                <h:outputLabel value="Is Bank Address Show" styleClass="label"/>
                 <h:selectBooleanCheckbox id="include"  value ="#{nPAOverdueReminderCharges.isBankAddress}" >
                      <a4j:support event="onblur" reRender="overDueRemId,errmsg" oncomplete="setMask();" />           
                            </h:selectBooleanCheckbox>
            </h:panelGrid>
            <h:panelGrid columns="2" id="a6" style="height:30px;" styleClass="footer" columnClasses="col7,col7,col7" width="100%">
                <h:panelGroup layout="block" style="width:100%;text-align:center;">
                    <a4j:commandButton id="show" value="#{nPAOverdueReminderCharges.btnName}" action="#{nPAOverdueReminderCharges.getOverDue}" disabled="#{nPAOverdueReminderCharges.disableShow}" reRender="gridPanel1,gridPanel2,errmsg,txnGrid,errormsg" />
                    <h:commandButton id="btnRefresh" value="Refresh" action="#{nPAOverdueReminderCharges.refreshForm}"/>
                    <h:commandButton id="btnExit" value="Exit" action="#{nPAOverdueReminderCharges.exitForm}"/>
                </h:panelGroup>
            </h:panelGrid>
            <h:panelGrid id="txnGrid" width="100%" style="background-color:#edebeb;height:200px;" columnClasses="vtop">
                <rich:dataTable  value="#{nPAOverdueReminderCharges.overDueList}" var="item" 
                                 rowClasses="gridrow1, gridrow2" id="txnList" rows="5" columnsWidth="100"
                                 onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                 onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                 width="100%">
                    <f:facet name="header">
                        <rich:columnGroup>
                            <rich:column colspan="11"><h:outputText value="LIST"/></rich:column>
                            <rich:column breakBefore="true" width="5"><h:outputText value="SNo."/></rich:column>
                            <rich:column width="200"><h:outputText value="Acount No" /></rich:column>
                            <rich:column width="90"><h:outputText value="Senction Dt" /></rich:column>
                            <rich:column width="90"><h:outputText value="Senction Amount" /></rich:column>
                            <rich:column width="110"><h:outputText value="EMI Overdue" /></rich:column>
                            <rich:column width="110"><h:outputText value="Outstanding Balance" /></rich:column>
                            <rich:column width="110"><h:outputText value="Amount Overdue" /></rich:column>
                            <rich:column width="200"><h:outputText value="Amount" /></rich:column>
                            <rich:column width="50"><h:outputText value="Select" /><h:selectBooleanCheckbox id="checkAllId" value="#{nPAOverdueReminderCharges.checkAll}" disabled="#{nPAOverdueReminderCharges.disableCheckAll}">
                            <a4j:support actionListener="#{nPAOverdueReminderCharges.checkAllBox}" event="onclick" 
                                         reRender="txnGrid,save"/> 
                        </h:selectBooleanCheckbox></rich:column>
                        </rich:columnGroup>
                    </f:facet>
                    <rich:column width="6"><h:outputText value="#{item.sNo}"/></rich:column> 
                    <rich:column><h:outputText value="#{item.accountNumber}"/></rich:column>
                    <rich:column style="text-align:center;"><h:outputText value="#{item.sanctionDate}"/></rich:column>
                    <rich:column style="text-align:right;"><h:outputText value="#{item.sanctionedamt}"/></rich:column>
                    <rich:column style="text-align:right;"><h:outputText value="#{item.noOfActualEmiOverdue}"/></rich:column>
                    <rich:column style="text-align:right;"><h:outputText value="#{item.outstandingBalance}"/></rich:column>
                    <rich:column style="text-align:right;"><h:outputText value="#{item.amountOverdue}"/></rich:column>
                    <rich:column style="text-align:right;"><h:outputText value="#{item.amount}"/></rich:column>
                    <rich:column style="text-align:center;">
                        <h:selectBooleanCheckbox  id="c1"  value="#{item.checkBoxFlag}"  style="text-align: center;" disabled="#{nPAOverdueReminderCharges.disableCheckBox}">
                            <a4j:support actionListener="#{nPAOverdueReminderCharges.checkNotAllBox}" event="onclick" reRender="txnGrid,save"/><%--target="#{nPAChargesMaster.currentIndex}" --%> 
                        </h:selectBooleanCheckbox>
                    </rich:column>
                </rich:dataTable>
                <rich:datascroller id="deafDataScroll"align="left" for="txnList" maxPages="20"/>
            </h:panelGrid>
            <h:panelGrid columns="2" id="a7" style="height:30px;" styleClass="footer" columnClasses="col7,col7,col7" width="100%">
                <h:panelGroup layout="block" style="width:100%;text-align:center;">
                    <a4j:commandButton id="save" value="Save" action="#{nPAOverdueReminderCharges.saveOverDue}" disabled="#{nPAOverdueReminderCharges.disableSave}" reRender="gridPanel1,gridPanel2,errmsg,txnGrid,errormsg"/>
                    <a4j:commandButton id="print" value="Print Report" action="#{nPAOverdueReminderCharges.printReport}" disabled="#{nPAOverdueReminderCharges.disablePrint}"  
                                       oncomplete="#{rich:component('popUpRepPanel')}.show();"
                                       reRender="gridPanel1,gridPanel2,errmsg,txnGrid,errormsg,popUpRepPanel"/> 
                    <a4j:commandButton id="pdf" value="PDF Download" action="#{nPAOverdueReminderCharges.printPDF}" disabled="#{nPAOverdueReminderCharges.disablePrint}"  reRender="gridPanel1,gridPanel2,errmsg,txnGrid,errormsg"/>
                    <h:commandButton id="btnExit1" value="Exit" action="#{nPAOverdueReminderCharges.exitForm}"/>
                </h:panelGroup>
            </h:panelGrid>
        </h:form>
        <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
        <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
            <f:facet name="header">
                <h:graphicImage url="/resources/images/ajax-loader.gif"/>
            </f:facet>
        </rich:modalPanel>
        <rich:modalPanel id="popUpRepPanel" label="Form" width="1000" height="550" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpRepPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="#{nPAOverdueReminderCharges.reportName}" style="text-align:center;"/> 
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
                                <a4j:include viewId="#{nPAOverdueReminderCharges.viewID}" />
                            </td>

                        </tr>
                    </tbody>
                </table>
            </rich:modalPanel>
    </body>
</html>
</f:view>
