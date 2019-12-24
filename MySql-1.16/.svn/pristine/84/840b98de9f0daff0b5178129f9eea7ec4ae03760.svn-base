<%-- 
    Document   : accountTransfer
    Created on : Jan 18, 2012, 12:36:08 PM
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Account Transfer</title>
             <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />

        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="mainPanelGrid" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{AccountTransfer.todayDate}"/>
                        </h:panelGroup>

                        <h:outputLabel styleClass="headerLabel" value="Account Transfer"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{AccountTransfer.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="grdpane6" style="height:30px;text-align:center" styleClass="row2" width="100%" >
                        <h:outputText id="stxtError" styleClass="error" value="#{AccountTransfer.msg}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col3" id="gridpanel1"   style="height:30px;" styleClass="row1" width="100%" >
                        <h:outputLabel id="lblAccountNo" styleClass="headerLabel"  value="Account no."  />
                         <h:panelGroup >
                        <h:inputText id="txtAccountNo" styleClass="input"  maxlength="12" style="width:120px" value="#{AccountTransfer.oldAcno}" onkeyup="this.value = this.value.toUpperCase();">
                             <a4j:support event="onblur" action="#{AccountTransfer.getPrimaryBranchOnBlur}" reRender="stxtPrimaryBranch,stxtNewAcno,btnSave,stxtError" />
                         </h:inputText>
                        <h:outputText id="stxtNewAcno" styleClass="output" style="color:green" value="#{AccountTransfer.acno}" />
                         </h:panelGroup> 
                         <h:outputLabel id="lblPrimaryBranch" styleClass="headerLabel"  value="Primary Branch"  />
                         <h:outputText id="stxtPrimaryBranch" styleClass="output" style="color:green" value="#{AccountTransfer.primaryBranch}"/>
                        <h:outputLabel id="lblRespondingBranch" styleClass="headerLabel"  value="Responding Branch"  />
                              <h:selectOneListbox id="listGroup" value="#{AccountTransfer.resBranch}" size="1" styleClass="ddlist"  style="width:120px" >
                                <f:selectItems value="#{AccountTransfer.resBranchList}"/>
                            </h:selectOneListbox>
                        <h:outputText/>
                    </h:panelGrid>
                   
                     <h:panelGrid columns="2" id="gridpanel5" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton  id="btnSave" value="Transfer"   action="#{AccountTransfer.transferAccountNo}" reRender="stxtError,mainPanelGrid" disabled="#{AccountTransfer.saveDisable}">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnRefresh" value="Refresh"  action="#{AccountTransfer.refreshForm}"  reRender="mainPanelGrid" >
                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" value="Exit"  action="#{AccountTransfer.exitAction}" reRender="mainPanelGrid" >
                            </a4j:commandButton>

                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
