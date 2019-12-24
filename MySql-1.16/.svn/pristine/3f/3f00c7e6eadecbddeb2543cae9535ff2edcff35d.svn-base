<%-- 
    Document   : AuthDdPayCancel
    Created on : Jan 23, 2016, 1:02:45 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!-- RichFaces tag library declaration -->
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>DD Payment/Cancel</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{AuthDdPayCancel.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="DD (Payment/Cancel)"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{AuthDdPayCancel.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel001" style="width:100%;text-align:center;" styleClass="row2" width="100%">
                        <h:outputText id="stxtMsg" styleClass="error" value="#{AuthDdPayCancel.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col2,col3,col3,col3,col3" columns="6" id="functionList" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblpfuntion" styleClass="label" value="Function."><font class="error">*</font></h:outputLabel>
                        <h:selectOneListbox size="1" id="ddFunction" styleClass="ddlist" value="#{AuthDdPayCancel.function}" style="width:100px;">
                            <f:selectItems value="#{AuthDdPayCancel.functionList}"/>
                            <a4j:support actionListener="#{AuthDdPayCancel.changeFunction()}" event="onblur" focus="ddInstNo"
                                         reRender="ddInstNo,stxtName,stxtSeqNo,stxtFYear,stxtIssueDate,stxtPayableAt,stxtInFvrOf,
                                         stxtAmount,stxtOrgBranch,ddCrHead,stxtCrHeadName,ddInstNo,btnDelete,stxtMsg" limitToList="true"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblInstno" styleClass="label" value="Inst No."><font class="error">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddInstNo" size="1" styleClass="ddlist" value="#{AuthDdPayCancel.instNo}" style="width:100px;display:#{AuthDdPayCancel.disableFlag};">
                            <f:selectItems value="#{AuthDdPayCancel.instNoList}"/>
                            <a4j:support actionListener="#{AuthDdPayCancel.getInstNoInfo}" event="onblur"
                                         reRender="stxtName,stxtSeqNo,stxtFYear,stxtIssueDate,stxtPayableAt,stxtInFvrOf,
                                         stxtAmount,stxtOrgBranch,ddCrHead,stxtCrHeadName,ddInstNo,btnDelete,stxtMsg" oncomplete="if(#{rich:element('ddFunction')}.value=='V'){#{rich:element('btnCompleted')}.focus();}
                                             else {#{rich:element('ddCrHead')}.focus();}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblTrnMode" styleClass="label" value="Transaction Mode"/>
                        <h:selectOneListbox id="ddTrnMode" size="1" styleClass="ddlist" value="#{AuthDdPayCancel.trnMode}" disabled="true">
                            <f:selectItems value="#{AuthDdPayCancel.trnList}"/>                            
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col2,col3,col3,col3,col3" columns="8" id="gridPanel15" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblName" styleClass="label" value="Cust Name"/>
                        <h:outputText id="stxtName" styleClass="output" value="#{AuthDdPayCancel.custName}"/>
                        <h:outputLabel id="lblSeqNo" styleClass="label" value="Seq No."/>
                        <h:outputText id="stxtSeqNo" styleClass="output" value="#{AuthDdPayCancel.seqNo}"/>
                        <h:outputLabel id="lblFyear" styleClass="label" value="FYear."/>
                        <h:outputText id="stxtFYear" styleClass="output" value="#{AuthDdPayCancel.fYear}"/>                        
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col2,col3,col3,col3,col3" columns="8" id="gridPanel16" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblIssueDate" styleClass="label" value="Issue Date"/>
                        <h:outputText id="stxtIssueDate" styleClass="output"  value="#{AuthDdPayCancel.issueDt}"/>
                        <h:outputLabel id="lblPayableAt" styleClass="label" value="Payable At"/>
                        <h:outputText id="stxtPayableAt" styleClass="output" value="#{AuthDdPayCancel.payableAt}"/>
                        <h:outputLabel id="lblInFvrOf" styleClass="label" value="In Favour Of"/>
                        <h:outputText id="stxtInFvrOf" styleClass="output"  value="#{AuthDdPayCancel.inFvrOf}"/>                        
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col2,col3,col3,col3,col3" columns="8" id="gridPanel7" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblAmount" styleClass="label" value="Amount"/>
                        <h:outputText id="stxtAmount" styleClass="output"  value="#{AuthDdPayCancel.amt}"/>
                        <h:outputLabel id="lblOrgBranch" styleClass="label" value="Origin Branch"/>
                        <h:outputText id="stxtOrgBranch" styleClass="output"  value="#{AuthDdPayCancel.orgBranch}"/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col2,col3,col3,col3,col3" columns="8" id="gridPanel8" style="height:30px;" styleClass="row2" width="100%">    
                        <h:outputLabel id="lblCrHead" styleClass="label" value="Credit Head"/>
                        <h:selectOneListbox id="ddCrHead" size="1" styleClass="ddlist" value="#{AuthDdPayCancel.crHead}" disabled="#{AuthDdPayCancel.disCrHead}" >
                            <f:selectItems value="#{AuthDdPayCancel.crHeadList}"/>
                            <a4j:support actionListener="#{AuthDdPayCancel.getGlHeadName}" event="onblur" reRender="stxtCrHeadName" limitToList="true"/>
                        </h:selectOneListbox>
                        <h:outputText id="stxtCrHeadName" styleClass="output"  value="#{AuthDdPayCancel.crHeadname}"/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnCompleted" value="#{AuthDdPayCancel.btnLabel}" action="#{AuthDdPayCancel.saveAction}" reRender="mainPanel" focus="ddFunction"/>
                            <a4j:commandButton id="btnDelete" value="Delete" action="#{AuthDdPayCancel.deleteAction}" disabled="#{AuthDdPayCancel.disDelete}" reRender="mainPanel" focus="ddFunction"/>
                            <a4j:commandButton id="btnReset" value="Reset" action="#{AuthDdPayCancel.pageRefresh}" reRender="mainPanel" focus="ddFunction"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{AuthDdPayCancel.btnExit_action}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
            </a4j:form>               
        </body>
    </html>
</f:view>