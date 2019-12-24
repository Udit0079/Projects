<%-- 
    Document   : repaymentshedule
    Created on : August 30, 2012, 2:03:27 PM
    Author     : Ankit Verma
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
          <%--  <a4j:keepAlive beanName="repaymentShedule"/> --%>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Repayment Schedule</title>
             <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
             <script type="text/javascript">
                var row;
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".calInstDate").mask("39/19/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="mainPanelGrid" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{repaymentShedule.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Repayment Schedule"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{repaymentShedule.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="grdpane6" style="height:30px;text-align:center" styleClass="row2" width="100%" >
                        <h:outputText id="stxtError"  styleClass="error" value="#{repaymentShedule.msg}"/>
                    </h:panelGrid>
                     <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col3" id="gridpanel"   style="height:30px;" styleClass="row1" width="100%" >
                        <h:outputLabel id="lblAcno" styleClass="headerLabel"  value="Account No."  />
                         <h:inputText id="txtAcno" styleClass="input"  maxlength="#{repaymentShedule.acNoMaxLen}" style="width:100px" value="#{repaymentShedule.acno}" />    
                         <h:outputLabel id="lblAmount" styleClass="headerLabel"  value="Installment Amount"  />
                         <h:inputText id="txtAmount" styleClass="input" style="width:80px" value="#{repaymentShedule.amount}" />
                          <h:outputLabel id="lblPeriodicity" styleClass="headerLabel"  value="Periodicity"  />
                          <h:selectOneListbox id="list" value="#{repaymentShedule.selectPeriodicity}" size="1" styleClass="ddlist"  style="width:100px" >
                                <f:selectItem itemValue="0"  itemLabel="--Select--"/>
                                <f:selectItem itemValue="M"  itemLabel="Monthly"/>
                                <f:selectItem itemValue="Q" itemLabel="Quaterly"/>
                                <f:selectItem itemValue="HY" itemLabel="HalfYearly"/>
                          </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col3" id="gridpanel1"   style="height:30px;" styleClass="row2" width="100%" >
                         <h:outputLabel id="lblPeriod" styleClass="headerLabel"  value="No. of Installments"  />
                         <h:inputText id="txtPeriod" styleClass="input"  maxlength="10" style="width:60px" value="#{repaymentShedule.period}"/>
                           <h:outputLabel id="lblEmiDate" styleClass="headerLabel"  value="EMI Starting Date"  />
                         <h:inputText id="txtEmiDate" styleClass="input calInstDate"  maxlength="10" style="width:60px" value="#{repaymentShedule.emiDate}">
                             <f:convertDateTime pattern="dd/MM/yyyy" />
                         </h:inputText>
                           <h:outputText/>
                           <h:outputText/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanelTable" style=" width:100%;height:0px" styleClass="row1" columnClasses="vtop">
                        <a4j:region>
                            <rich:dataTable  value="#{repaymentShedule.sheduleList}" var="dataItem1"
                                             rowClasses="gridrow1, gridrow2" id="taskList1" rows="3" rowKeyVar="row"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                             onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="S No." /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Account No." /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Periodicity" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Installment Amount" /></rich:column>
                                         <rich:column width="100px" ><h:outputText style="text-align:center" value="Due Date" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Status" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column width="100px" ><h:outputText  style="text-align:center" value="#{dataItem1.sno}"/></rich:column>
                                <rich:column width="100px" ><h:outputText style="text-align:center" value="#{dataItem1.acno}"/></rich:column>
                                <rich:column width="100px" ><h:outputText style="text-align:center" value="#{dataItem1.selectPeriodicity}"/></rich:column>
                                <rich:column width="100px" ><h:outputText style="text-align:center" value="#{dataItem1.amount}"/></rich:column>
                                <rich:column width="100px" ><h:outputText style="text-align:center"  value="#{dataItem1.dueDate}"/></rich:column>
                                <rich:column width="100px" ><h:outputText style="text-align:center"  value="#{dataItem1.status}"/></rich:column>                              
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList1" maxPages="5"/>
                        </a4j:region>
                    </h:panelGrid>								
                     <h:panelGrid columns="2" id="gridpanel5" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton  id="btnSave" value="Save"   action="#{repaymentShedule.saveData}" reRender="stxtError,mainPanelGrid"  oncomplete="#{rich:element('txtEmiDate')}.style=setMask();">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnRefresh" value="Refresh"  action="#{repaymentShedule.refreshForm}"  reRender="mainPanelGrid" oncomplete="#{rich:element('txtEmiDate')}.style=setMask();">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" value="Exit"  action="#{repaymentShedule.exitAction}" reRender="mainPanelGrid" >
                            </a4j:commandButton>
                      </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
