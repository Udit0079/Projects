<%-- 
    Document   : loanAcStatement
    Created on : Dec 14, 2011, 7:26:06 PM
    Author     : admin
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>Loan Account Statement</title>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
            <script type="text/javascript">
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".frDt").mask("99/99/9999");
                    jQuery(".toDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <h:form id="loanAcStateme">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">

                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{loanAcStatement.currentDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Loan Account Statement"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{loanAcStatement.loggedInUser}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{loanAcStatement.errorMsg}"/>
                    </h:panelGrid>

                    <h:panelGrid id="panel1" columns="6" columnClasses="col3,col3,col3,col5,col3,col3" styleClass="row2" width="100%">
                        <h:outputLabel/>
                        <h:outputText value="Account Number" styleClass="label"/>
                        <h:inputText id="txtacno" value="#{loanAcStatement.acNo}" styleClass="input" size="#{loanAcStatement.acNoMaxLen}" maxlength="#{loanAcStatement.acNoMaxLen}" onkeyup="this.value=this.value.toUpperCase();">
                            <a4j:support event="onblur" actionListener="#{loanAcStatement.getNewAccNo}" reRender="newAcno,errorMsg" oncomplete="setMask();" focus="frDt"/>
                        </h:inputText>
                        <h:outputText id="newAcno" value="#{loanAcStatement.acnoName}" styleClass="output" style="color:green"/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="panel2" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputLabel/>
                        <h:outputLabel value="From Date" styleClass="label"/>
                        <h:inputText id="frDt" styleClass="input frDt" size="12" value="#{loanAcStatement.frmDt}">
                            <a4j:support event="onblur" focus="toDt" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel value="To Date" styleClass="label"/>
                        <h:inputText id="toDt" styleClass="input toDt" size="12" value="#{loanAcStatement.toDt}">
                            <a4j:support event="onblur" focus="print" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                        <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                           <a4j:commandButton  id="print1" value="Overdue Remainder" action="#{loanAcStatement.overdueRemainder}" disabled="#{loanAcStatement.isbuttonDisb}" reRender="errorMsg,tablePanel,taskList"/>
                            <a4j:commandButton  id="print" value="Print Report" action="#{loanAcStatement.viewReport}" reRender="errorMsg"/>
                            <h:commandButton id="btnPDF"  value="PDF Download" actionListener="#{loanAcStatement.viewPdfReport}"  styleClass="color: #044F93;text-decoration: none;"  ></h:commandButton>
                            <h:commandButton id="btnExcel"  value="Download Excel" actionListener="#{loanAcStatement.downLoadExcel}"  styleClass="color: #044F93;text-decoration: none;"  ></h:commandButton>
                            <a4j:commandButton id="reset" value="Refresh" actionListener="#{loanAcStatement.refreshPage}" reRender="errorMsg,txtacno,newAcno,frDt,toDt,taskList,tablePanel"/>
                            <a4j:commandButton  id="exit" value="Exit" action="#{loanAcStatement.exitPage}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                     <h:panelGrid id="seqPanel" style="width:100%;display:#{loanAcStatement.seqFileDisplay}">
                     <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                    <a4j:region id="tblRegion">
                        <rich:dataTable value="#{loanAcStatement.gridDetail}" var="dataItem"
                                        rowClasses="gridrow1,gridrow2" id="taskList" rows="7" columnsWidth="100" rowKeyVar="row"
                                        onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                        onclick="this.style.backgroundColor='#428bca'"
                                        onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">

                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="10"><h:outputText value="Overdue Remainder detail" /></rich:column>
                                    <rich:column breakBefore="true"><h:outputText value="Account No." /></rich:column>
                                    <rich:column><h:outputText value="Customer Name" /></rich:column>
                                    <rich:column><h:outputText value="Loan Amount" /></rich:column>
                                    <rich:column><h:outputText value="Sanction Date" /></rich:column>
                                    <rich:column><h:outputText value="Outstanding Balance" /></rich:column>
                                    <rich:column><h:outputText value="Overdue Emi" /></rich:column>
                                    <rich:column><h:outputText value="Overdue Princple as on Date" /></rich:column>
                                    <rich:column><h:outputText value="Overdue Amount to pay as on Date" /></rich:column>
                                    <rich:column><h:outputText value="Refenernce No." /></rich:column>   
                                    <rich:column><h:outputText value="Print" /></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{dataItem.acNo}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.custName}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.loanAmt}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.sanctionDT}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.outStandingBalance}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.overdueEmi}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.principleAmt}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.totalDue}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.refno}" /></rich:column>  
                            <rich:column>
                                <a4j:commandLink ajaxSingle="true" id="selectLink"  oncomplete="#{rich:component('processPanel')}.show();" 
                                                 reRender="errorMsg,processPanel" >
                                    <h:graphicImage value="/resources/images/select.gif" style="border:0" width="15" height="15"/>
                                    <f:setPropertyActionListener value="#{dataItem}" target="#{loanAcStatement.currentItem}"/>
                                </a4j:commandLink>
                            </rich:column>
                        </rich:dataTable>
                        <rich:datascroller align="left" for="taskList" maxPages="10"/>
                    </a4j:region>
                </h:panelGrid>
                     </h:panelGrid>
                    <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
                    <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <f:facet name="header">
                            <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                        </f:facet>
                    </rich:modalPanel>
                </h:panelGrid>
         </h:form>
            <rich:modalPanel id="processPanel" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                    </f:facet>
                    <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText id="confirmid" value="Are you sure to want print ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" action="#{loanAcStatement.printOverdueRemainder}" 
                                                       oncomplete="#{rich:component('processPanel')}.hide();" 
                                                       reRender="errorMsg,tablePanel,taskList"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('processPanel')}.hide();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
