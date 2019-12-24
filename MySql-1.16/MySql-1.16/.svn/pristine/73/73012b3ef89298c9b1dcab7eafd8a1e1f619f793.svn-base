<%-- 
    Document   : bonusChecklist
    Created on : 20 Jan, 2018, 10:25:46 AM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
 <html>
 <head>
     <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
     <title>Bonus Checklist</title>
     <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
     <meta http-equiv="Pragma" content="no-cache"/>
     <meta http-equiv="Cache-Control" content="no-cache"/>
  </head>
   <body>
   <a4j:form id="bonusChecklistform">
     <h:panelGrid id="mainPanel" columns="1" style="border:1px ridge #BED6F8" width="100%">
       <h:panelGrid id="Panel1" columns="3"  style="width:100%;text-align:center;" styleClass="header">
          <h:panelGroup>
              <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
              <h:outputText id="stxtDate" styleClass="output" value="#{BonusChecklist.todayDate}"/>
          </h:panelGroup>
          <h:outputLabel id="lblincident" styleClass="headerLabel" value="Bonus Checklist"/>
          <h:panelGroup id="groupPanel2" layout="block">
              <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
              <h:outputText id="stxtUser" styleClass="output" value="#{BonusChecklist.userName}"/>
          </h:panelGroup>
       </h:panelGrid>             
       <h:panelGrid id="messagePanel" columnClasses="col8,col8" columns="2"  style="width:100%;text-align:center;"styleClass="row1" width="100%"> 
              <h:outputText id="stxtMessage" styleClass="error" value="#{BonusChecklist.message}"/>
       </h:panelGrid>            
       <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Panel2" style="text-align:left;" width="100%" styleClass="row2">                  
              <h:outputLabel id="lblBrnchwise" styleClass="output" value="Category"><font class="required" style="color:red;">*</font></h:outputLabel>
              <h:selectOneListbox id="ddBranchwise" styleClass="ddlist"  size="1" style="width:150px" value="#{BonusChecklist.category}">
                 <f:selectItems value="#{BonusChecklist.categoryList}"/>
                 <a4j:support event="onblur" action="#{BonusChecklist.branchwiseCategory}" reRender="lblBranch,ddBranch"/>
              </h:selectOneListbox>
              <h:outputLabel id="lblBranch" styleClass="output" value="Bank Branch"><font class="required" style="color:red;">*</font></h:outputLabel>
               <h:selectOneListbox id="ddBranch"  styleClass="ddlist"  style="width:150px" disabled="#{BonusChecklist.disableBankBranch}" value="#{BonusChecklist.branch}" size="1">
                  <f:selectItems value="#{BonusChecklist.selectBranchList}" />
                  <a4j:support event="onblur" action="#{BonusChecklist.bonusChecklistReport}" reRender="fromDate,toDate"/>
               </h:selectOneListbox>
       </h:panelGrid>
       <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Panel3" style="text-align:left;" width="100%" styleClass="row1">
              <h:outputLabel id="dateFrom" styleClass="output" value="Date From"><font class="required" style="color:red;">*</font></h:outputLabel>
              <rich:calendar   enableManualInput="true" id="fromDate" datePattern="dd/MM/yyyy" value="#{BonusChecklist.frDate}" inputSize="10"/>
              <h:outputLabel id="DateTo" styleClass="output" value="Date To"><font class="required" style="color:red;">*</font></h:outputLabel>
              <rich:calendar  enableManualInput="true" id="toDate" datePattern="dd/MM/yyyy" value="#{BonusChecklist.toDate}" inputSize="10"/>
        </h:panelGrid>            
       <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
              <h:panelGroup id="btnPanel">
              <h:commandButton id="btnPdf" value="PDF Download" action="#{BonusChecklist.bonusChecklistReport}" />
              <a4j:commandButton id="btnRefresh" value="Refresh" action="#{BonusChecklist.btnRefreshAction}"                                               reRender="stxtMessage,lblBrnchwish,ddBranchwise,ddBranch,lblBranch,dateFrom,fromDate,DateTo,toDate"/>
              <a4j:commandButton id="btnExit" value="Exit" action="#{BonusChecklist.btnExitAction}"
                                reRender="stxtMessage,lblBrnchwish,ddBranchwise,ddBranch,lblBranch,dateFrom,fromDate,DateTo,toDate"/>                  
              </h:panelGroup>
       </h:panelGrid>                      
    </h:panelGrid>
  </a4j:form>
  </body>
  </html>
</f:view>
