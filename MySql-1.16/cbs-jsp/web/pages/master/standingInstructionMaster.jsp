<%-- 
    Document   : standingInstructionMaster
    Created on : May 12, 2010, 10:44:37 AM
    Author     : jitendra kumar chaudhary
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <title>Standing Instruction Master</title>
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{standingInstructionMaster.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Standing Instruction Master"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{standingInstructionMaster.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col1,col1,col1,col1,col2,col2" columns="6" id="gridPanel15" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblInstructionType" styleClass="label" value="Instruction Type"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddInstructionType" styleClass="ddlist" size="1" style="width: 103px" value="#{standingInstructionMaster.instructionType}" tabindex="1" >
                            <f:selectItem itemValue="TRANSACTION"/>
                            <f:selectItem itemValue="GENERAL"/>
                            <a4j:support ajaxSingle="true" event="onblur" focus="ddFunction" reRender="gridPanel1,gridPanel16,gridPanel1y1,gridPanedrt,gridPanel14,gridPanel1e,gridPanel1x,p4,gridPanel103,txtEffectivePeriod,txtRemarks,txtByAmount,stxtName1,stxtName,txtAccountToBeDebited,txtAccountToBeCredited,ddPeriodicity,txtValidityDays,calEffectiveDate,ddDeductCharges,stxtFullAcno,stxtUser,stxtMsg" action="#{standingInstructionMaster.clearText}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblpfuntion" styleClass="label" value="Function."><font class="error">*</font></h:outputLabel>
                        <h:selectOneListbox size="1" id="ddFunction"  styleClass="ddlist" value="#{standingInstructionMaster.function}" style="width:100px;">
                            <f:selectItems value="#{standingInstructionMaster.functionList}"/>
                                <a4j:support actionListener="#{standingInstructionMaster.changeFunction()}" event="onblur"
                                    reRender="gridPanel1,gridPanel16,gridPanel1y1,gridPanedrt,gridPanel14,gridPanel1e,gridPanel1x,p4,gridPanel103,txtEffectivePeriod,txtRemarks,txtByAmount,stxtName1,stxtName,txtAccountToBeDebited,txtAccountToBeCredited,ddPeriodicity,txtValidityDays,calEffectiveDate,ddDeductCharges,stxtFullAcno,stxtUser,stxtMsg,btnSave,ddPInst,lblPInst" limitToList="true" focus="#{standingInstructionMaster.focusId}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblPInst"  styleClass="label" value="Pending Instruction" style="display:#{standingInstructionMaster.inputDisFlag};"/>
                        <h:selectOneListbox id="ddPInst" size="1" styleClass="ddlist" value="#{standingInstructionMaster.pendingInst}" style="display:#{standingInstructionMaster.inputDisFlag};">
                            <f:selectItems value="#{standingInstructionMaster.pendingInstList}"/>
                                <a4j:support actionListener="#{standingInstructionMaster.getInstDetail()}" event="onblur" focus="btnSave" reRender="gridPanel1,gridPanel16,gridPanel1y1,gridPanedrt,gridPanel14,gridPanel1e,gridPanel1x,p4,gridPanel103,txtEffectivePeriod,txtRemarks,txtByAmount,stxtName1,stxtName,txtAccountToBeDebited,txtAccountToBeCredited,ddPeriodicity,txtValidityDays,calEffectiveDate,ddDeductCharges,stxtFullAcno,stxtUser,stxtMsg,btnSave,ddPInst,lblPInst" limitToList="true"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel1" width="100%">
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel1y1" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblAccountToBeDebited" styleClass="label" rendered="#{standingInstructionMaster.instructionType == 'TRANSACTION'}" value="Account To Be Debited" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:outputLabel id="lblAccountNo" styleClass="label" rendered="#{standingInstructionMaster.instructionType == 'GENERAL'}" value="Account No" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:panelGroup id="groupPanel2p1" layout="block">
                                <h:inputText id="txtAccountToBeDebited" styleClass="input"style="width:100px" value="#{standingInstructionMaster.oldActToBeDebited}" tabindex="2" maxlength="#{standingInstructionMaster.acNoMaxLen}" disabled="#{standingInstructionMaster.acnoDisb}">
                                    <a4j:support action="#{standingInstructionMaster.getGrdTransactionDetails}" event="onblur"
                                                 reRender="gridPanel103,stxtFullAcno,gridPanel1y1,p4,stxtName,stxtName1,stxtMsg" limitToList="true" focus="#{rich:clientId('calEffectiveDate')}InputDate" />
                                </h:inputText>
                               <h:outputText id="stxtFullAcno" styleClass="output" value="#{standingInstructionMaster.actToBeDebited}"/>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel1y" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblEffectiveDate" styleClass="label" value="Effective Date" ><font class="required" color="red">*</font></h:outputLabel>
                            <rich:calendar datePattern="dd/MM/yyyy" id="calEffectiveDate" value="#{standingInstructionMaster.dates}" disabled="#{standingInstructionMaster.dtDisb}" tabindex="3" inputSize="10">
                                <a4j:support event="onchanged" actionListener="#{standingInstructionMaster.onblurDt}" reRender="stxtMsg" focus="txtEffectivePeriod"/>
                            </rich:calendar>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanela" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblEffectivePeriod" styleClass="label" value="Effective Period (In Month)" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtEffectivePeriod" styleClass="input"style="width:100px" value="#{standingInstructionMaster.effPeriod}" disabled="#{standingInstructionMaster.prdDisb}" tabindex="4" maxlength="15">
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel16" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblName1" styleClass="label" rendered="#{standingInstructionMaster.instructionType == 'TRANSACTION'}" value="Name"/>
                            <h:outputText id="stxtName1"  styleClass="output" rendered="#{standingInstructionMaster.instructionType == 'TRANSACTION'}" value="#{standingInstructionMaster.name1}" />
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel1e" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblAccountToBeCredited" styleClass="label" rendered="#{standingInstructionMaster.instructionType == 'TRANSACTION'}" value="Account To Be Credited" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:outputLabel id="lblName" styleClass="label" rendered="#{standingInstructionMaster.instructionType == 'GENERAL'}" value="Name"/>
                            <h:outputText id="stxtName"  styleClass="output" rendered="#{standingInstructionMaster.instructionType == 'GENERAL'}" value="#{standingInstructionMaster.name}" />
                            <h:panelGroup id="groupPanel2p2" layout="block">
                                <h:inputText id="txtAccountToBeCredited" styleClass="input"style="width:100px" rendered="#{standingInstructionMaster.instructionType == 'TRANSACTION'}" value="#{standingInstructionMaster.oldActToBeCredited}" disabled="#{standingInstructionMaster.crAcnoDisb}" tabindex="5" maxlength="#{standingInstructionMaster.acNoMaxLen}">
                                    <a4j:support action="#{standingInstructionMaster.accountNoTo}" event="onblur"
                                                 reRender="stxtAccNames,gridPanel1e,stxtMsg,ddPeriodicity" limitToList="true" focus="ddPeriodicity"/>
                                </h:inputText>
                               <h:outputText id="stxtFullAcno1" styleClass="output" rendered="#{standingInstructionMaster.instructionType == 'TRANSACTION'}" value="#{standingInstructionMaster.actToBeCredited}"/>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel1z" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblPeriodicity" styleClass="label" value="Periodicity" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddPeriodicity" styleClass="ddlist" size="1" style="width: 100px" value="#{standingInstructionMaster.periodicity}" disabled="#{standingInstructionMaster.periodDisb}" tabindex="6">
                                <f:selectItem itemValue="--SELECT--"/>
                                <f:selectItem itemValue="MONTHLY"/>
                                <f:selectItem itemValue="QUARTERLY"/>
                                <f:selectItem itemValue="HALF-YEARLY"/>
                                <f:selectItem itemValue="YEARLY"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel1x" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblValidityDays" styleClass="label" rendered="#{standingInstructionMaster.instructionType == 'TRANSACTION'}" value="Validity Days" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtValidityDays" styleClass="input"style="width:100px" rendered="#{standingInstructionMaster.instructionType == 'TRANSACTION'}" value="#{standingInstructionMaster.validityDays}" disabled="#{standingInstructionMaster.valDisb}" tabindex="7" maxlength="12">
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel14" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblByAmount" styleClass="label" rendered="#{standingInstructionMaster.instructionType == 'TRANSACTION'}" value="By Amount"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtByAmount" styleClass="input"style="width:100px" rendered="#{standingInstructionMaster.instructionType == 'TRANSACTION'}" value="#{standingInstructionMaster.amount}" disabled="#{standingInstructionMaster.amtDisb}" tabindex="8" maxlength="15">
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel1b" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblRemarks" styleClass="label" value="Remarks" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtRemarks" styleClass="input"style="width:100px" value="#{standingInstructionMaster.remarks}" disabled="#{standingInstructionMaster.remDisb}" tabindex="9" maxlength="59" onkeyup="this.value=this.value.toUpperCase();">
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel1c" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblDeductCharges" styleClass="label" value="Deduct Charges"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddDeductCharges" styleClass="ddlist" size="1" style="width:100px" value="#{standingInstructionMaster.deductCharges}" disabled="#{standingInstructionMaster.chgDisb}" tabindex="10">
                                <f:selectItem itemValue="--SELECT--"/>
                                <f:selectItem itemValue="YES"/>
                                <f:selectItem itemValue="No"/>
                                <a4j:support event="onblur" focus="btnSave"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanedrt" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblAccNames" styleClass="label" value="Account To Be Credited Name" rendered="#{standingInstructionMaster.instructionType == 'TRANSACTION'}"/>
                            <h:outputText id="stxtAccNames" styleClass="output" value="#{standingInstructionMaster.creditAcctName}" rendered="#{standingInstructionMaster.instructionType == 'TRANSACTION'}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel001" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputText id="stxtMsg" styleClass="msg" value="#{standingInstructionMaster.message}"/>
                        </h:panelGrid>
                    </h:panelGrid>                    
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnSave" value="#{standingInstructionMaster.btnLabel}" action="#{standingInstructionMaster.save}" reRender="ddInstructionType,txtEffectivePeriod,txtRemarks,txtByAmount,txtAccountToBeDebited,txtAccountToBeCredited,ddPeriodicity,txtValidityDays,calEffectiveDate,ddDeductCharges,stxtUser,gridPanel103,stxtMsg,stxtName1,stxtName,stxtAccNames,stxtFullAcno,stxtFullAcno1"/>
                            <a4j:commandButton id="btnReset" value="Refresh" action="#{standingInstructionMaster.clearText}" reRender="gridPanel14,gridPanel1x,lblPInst,ddPInst,ddFunction,ddInstructionType,gridPanel103,txtEffectivePeriod,txtRemarks,txtByAmount,stxtName1,gridPanel16,stxtName,txtAccountToBeDebited,txtAccountToBeCredited,ddPeriodicity,txtValidityDays,calEffectiveDate,ddDeductCharges,gridPanel1y1,gridPanedrt,stxtFullAcno,gridPanel1e,stxtUser,stxtMsg,stxtAccNames,stxtFullAcno1" focus="ddInstructionType"/>
                            <a4j:commandButton id="btnExit" action="#{standingInstructionMaster.exitForm}" value="Exit" reRender="ddInstructionType,gridPanel103,txtEffectivePeriod,txtRemarks,txtByAmount,stxtName1,gridPanel16,stxtName,txtAccountToBeDebited,txtAccountToBeCredited,ddPeriodicity,txtValidityDays,calEffectiveDate,ddDeductCharges,gridPanel1y1,gridPanedrt,stxtFullAcno,gridPanel1e,stxtUser,stxtMsg,stxtAccNames,stxtFullAcno1"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
                <rich:messages></rich:messages>
            </a4j:form>
        </body>
    </html>
</f:view>
