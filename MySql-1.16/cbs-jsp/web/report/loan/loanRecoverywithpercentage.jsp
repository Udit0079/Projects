<%-- 
    Document   : loanRecoverywithpercentage
    Created on : 9 May, 2018, 2:29:20 PM
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
            <title>LOAN RECOVERY WITH PERCENTAGE</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{LoanRecoverywithPercentage.todayDate}"/>
                        </h:panelGroup>

                        <h:outputLabel styleClass="headerLabel" value="Loan Recovery with Percentage"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{LoanRecoverywithPercentage.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="grdpane6" style="height:30px;text-align:center" styleClass="row2" width="100%" >
                        <h:outputText id="stxtError"  styleClass="error" value="#{LoanRecoverywithPercentage.msg}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col0,col13" id="gridPanel4"  styleClass="row2">         
                        <h:outputText value="Branch Wise:" styleClass="label"/>
                        <h:selectOneListbox id="branch" size="1" value="#{LoanRecoverywithPercentage.branchCode}" styleClass="ddlist">
                            <f:selectItems value="#{LoanRecoverywithPercentage.branchCodeList}" />
                        </h:selectOneListbox>
                        <h:outputLabel value="Account Nature" styleClass="label"/>
                        <h:selectOneListbox id="ddNature" value="#{LoanRecoverywithPercentage.acNature}" styleClass="ddlist" size="1" style="width:70px">
                            <f:selectItems value="#{LoanRecoverywithPercentage.acNatureList}"/>
                            <a4j:support actionListener="#{LoanRecoverywithPercentage.getAcTypeForNature}" event="onblur" reRender="selectListBox" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblAcType" styleClass="label"  value="Account Type" />
                        <h:selectOneListbox id="selectListBox" value="#{LoanRecoverywithPercentage.selectAcType}" styleClass="ddlist"  style="width:70px" size="1" >
                            <f:selectItems id="selectAcTypeList" value="#{LoanRecoverywithPercentage.selectAcTypeList}" />
                        </h:selectOneListbox>  
                        </h:panelGrid>
                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col0,col13" id="gridPanel6"  styleClass="row2">         
                         <h:outputLabel id="lblpercen" styleClass="label" value="Percentage of Recovery"><font class="required" color="red">*</font></h:outputLabel>
                         <h:inputText id="txtpercen" styleClass="input" value="#{LoanRecoverywithPercentage.percentage}" maxlength="8" size="20">
                         <a4j:support actionListener="#{LoanRecoverywithPercentage.perfunctionvalidate}" event = "onblur" reRender="stxtError" oncomplete="setMask();"/>
                         </h:inputText>
                          <h:outputLabel id="lblrecovery" styleClass="label"  value="Recovery Mode" />
                        <h:selectOneListbox id="selectRecoveryMode" value="#{LoanRecoverywithPercentage.recoveryMode}" styleClass="ddlist"  style="width:80px" size="1" >
                            <f:selectItems id="selectmodeList" value="#{LoanRecoverywithPercentage.recoveryModeList}" />
                        </h:selectOneListbox>  
                         <h:outputLabel/>
                         <h:outputText>
                         </h:outputText>
                        </h:panelGrid>
                      <h:panelGrid id="panel2" columns="6" columnClasses="col3,col3,col3,col3,col0,col3" styleClass="row2">
                       <h:outputLabel id="lblfrdate" value="From Date" styleClass="label"/>
                        <h:inputText id="frDt" styleClass="input calInstDate" size="12" value="#{LoanRecoverywithPercentage.frmDt}">
                            <a4j:support event="onblur" focus="toDt" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel id= "lbltoDate" value="To Date" styleClass="label"/>
                        <h:inputText id="toDt" styleClass="input calInstDate" size="12" value="#{LoanRecoverywithPercentage.toDt}">
                            <a4j:support event="onblur" focus="btnView" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel/>
                        <h:outputText>
                        </h:outputText>
                    </h:panelGrid>   
                    
                        <h:panelGrid columns="2" id="gridpanel5" style="width:100%;text-align:center;" styleClass="footer" >
                            <h:panelGroup id="btnPanel">
                                <a4j:commandButton  id="btnView" value="View"   action="#{LoanRecoverywithPercentage.viewReport}" reRender="stxtError,mainPanelGrid,txtDate,branch,ddNature,selectListBox" oncomplete="setMask()"/>
                                <h:commandButton id="btnDownload"  value="PDF Download" action="#{LoanRecoverywithPercentage.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                                <a4j:commandButton id="btnRefresh" value="Refresh"  action="#{LoanRecoverywithPercentage.refreshForm}"  reRender="mainPanelGrid,frDt,toDt,branch,ddNature,txtpercen,selectListBox,selectRecoveryMode,stxtError" oncomplete="setMask()"/>
                                <a4j:commandButton id="btnExit" value="Exit"  action="#{LoanRecoverywithPercentage.exitAction}" reRender="mainPanelGrid" />
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
        </body>
    </html>
</f:view>
