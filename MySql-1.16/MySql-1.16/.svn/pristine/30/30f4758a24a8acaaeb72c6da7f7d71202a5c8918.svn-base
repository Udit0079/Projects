<%-- 
    Document   : consolidatemsr
    Created on : 12 Apr, 2019, 1:03:33 PM
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
            <title>Consolidated Monthly Salary Register</title>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>        
        </head>
        <body>
            <a4j:form id="salaryRegisterForm">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel1" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date:"/>
                            <h:outputText id="stDate" styleClass="output" value="#{consolidatedMsr.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="OL30" styleClass="headerLabel" value="Consolidated Monthly Salary Register"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User:"/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{consolidatedMsr.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel2" width="100%" style="height:1px;text-align:center" styleClass="error">
                        <h:outputText id="errMsg" value="#{consolidatedMsr.message}"/>
                    </h:panelGrid>                    
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel4"  styleClass="row1">
                        <h:outputLabel id="lblMonth" styleClass="label"  value="Month:"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddMonth" styleClass="ddlist" size="1" style="width: 100px" value="#{consolidatedMsr.month}">
                            <f:selectItems value="#{consolidatedMsr.monthList}"/>
                         </h:selectOneListbox>  
                        <h:outputLabel id="lblYear" styleClass="output" value="Year:"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddYear" styleClass="ddlist" size="1" style="width: 100px" value="#{consolidatedMsr.year}">
                           <f:selectItems value="#{consolidatedMsr.yearList}"/>
                        </h:selectOneListbox> 
                    </h:panelGrid>                       
                    <h:panelGrid id="salryFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="footerPanel">
                           <h:commandButton id="printPdf" value="PDF Download" action="#{consolidatedMsr.getSalRegisterRep}" />
                           <a4j:commandButton id="cancel" value="Refresh" action="#{consolidatedMsr.refresh}" reRender="ddMonth,ddYear,errMsg,ddBranchwise,ddBranch" />
                           <a4j:commandButton id="exit" value="Exit" action="#{consolidatedMsr.btnExitAction}" />
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
             </a4j:form>
         </body>
    </html>
</f:view>
  
