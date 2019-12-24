<%-- 
    Document   : ecrfile
    Created on : 31 Dec, 2018, 12:42:36 PM
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
            <title>ECR File</title>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>        
    </head>
    <body>
       <a4j:form id="ecrfileForm">
          <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
              <h:panelGrid columns="3" id="gridPanel1" style="width:100%;text-align:center;" styleClass="header">
                <h:panelGroup id="groupPanel2" layout="block">
                <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date:"/>
                <h:outputText id="stDate" styleClass="output" value="#{ECRFile.todayDate}"/>
                </h:panelGroup>
                   <h:outputLabel id="OL30" styleClass="headerLabel" value="ECR FILE"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User:"/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ECRFile.userName}"/>
                        </h:panelGroup>
              </h:panelGrid>
              <h:panelGrid id="gridPanel2" width="100%" style="height:1px;text-align:center" styleClass="error">
                        <h:outputText id="errMsg" value="#{ECRFile.message}"/>
              </h:panelGrid>  
              <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel4"  styleClass="row1">
                        <h:outputLabel id="lblMonth" styleClass="label"  value="Month:"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddMonth" styleClass="ddlist" size="1" style="width: 100px" value="#{ECRFile.month}">
                            <f:selectItems value="#{ECRFile.monthList}"/>
                         </h:selectOneListbox>  
                        <h:outputLabel id="lblYear" styleClass="output" value="Year:"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddYear" styleClass="ddlist" size="1" style="width: 100px" value="#{ECRFile.year}">
                           <f:selectItems value="#{ECRFile.yearList}"/>
                        </h:selectOneListbox> 
               </h:panelGrid>   
               <h:panelGrid id="a6" columns="2" style="width:100%;height:30px;text-align:center;" styleClass="row1" width="100%">
                    <h:panelGroup layout="block" style="width:100%;text-align:center;">
                    <a4j:repeat value="#{ECRFile.ecrTxtFileName}" var="fileName">
                    <a4j:commandLink id="saveLink" value="#{fileName}"  style="color:blue;" styleClass="headerLabel"
                                                 action="#{ECRFile.downloadFile}" rendered="#{ECRFile.saveLink}">
                      <f:setPropertyActionListener target="#{ECRFile.fileName}" value="#{fileName}"/>
                    </a4j:commandLink>
                                &nbsp;&nbsp;
                    </a4j:repeat>
                    </h:panelGroup>
               </h:panelGrid>    
               <h:panelGrid id="salryFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="footerPanel">
                           <h:commandButton id="printPdf" value="Generate ECR" action="#{ECRFile.gpfRegisterRep}" />
                           <a4j:commandButton id="cancel" value="Refresh" action="#{ECRFile.refresh}" reRender="ddMonth,ddYear,errMsg,saveLink" />
                           <a4j:commandButton id="exit" value="Exit" action="#{ECRFile.btnExitAction}" />
                        </h:panelGroup>
              </h:panelGrid>
             </h:panelGrid>
         </a4j:form>
     </body>
    </html>
</f:view>

