<%--
    Document   : HolidaYMarking_Register
    Created on : May 15, 2010, 3:05:24 PM
    Author     : jitendra kumar Chaudhary
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
            <title>Register Maintenance</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{RegisterMaintenance.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Register Maintenance"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{RegisterMaintenance.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel19c" style="width:100%;text-align:center;" styleClass="row2" width="100%">
                        <h:outputText id="stxtMsg" styleClass="error" value="#{RegisterMaintenance.message}" />
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gridPanel4" width="100%">
                        <h:panelGrid columnClasses="col9" columns="2" id="gridPanel19" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="label13" style="padding-left:350px" styleClass="label" value="Todays Login Date"/>
                            <h:outputText id="stxtTodaysLoginDate"  styleClass="output" value="#{RegisterMaintenance.loginDt}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="4" id="gridPanel15" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="label1" styleClass="label" style="padding-left:350px" for="ddSelectCircleType" value="Select Circle Type"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddSelectCircleType" styleClass="ddlist" size="1" style="width:130px" value="#{RegisterMaintenance.emFlags}">
                                <a4j:support actionListener="#{RegisterMaintenance.allDates}"  event="onchange"
                                             reRender="calEntryDate,calPostingDate,stxtMsg,calNewPostingDate,calOldPostingDate,calOldClearingDate,calNewClearingDate,callearingRegisterDatedO,callearingRegisterDated,calClearingDate" limitToList="true" focus="btnOpen"/>
                                <f:selectItem itemValue="--SELECT--"/>
                                <f:selectItems value="#{RegisterMaintenance.circleTypeOption}"/>
                            </h:selectOneListbox>
                            
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel1a" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                            <h:outputLabel id="labelW"  styleClass="headerLabel" style="color:purple;" value="What Activity Would you like To Perform Regarding Clearing Register for Selected Circle Type"/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col7,col7" columns="3" id="gridPanel1" width="100%">
                        <h:panelGrid columnClasses="col9" columns="2" id="gridPanel18style" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblHeading" styleClass="headerLabel" value="Open" style="color:purple;"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="2" id="gridPanel18style1" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblHeading1" styleClass="headerLabel" value="Close" style="color:purple;"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="2" id="gridPanel18style2" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblHeading2" styleClass="headerLabel" value="Others" style="color:purple;"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="2" id="gridPanel18" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="label12" styleClass="label" value="Entry Date" rendered="#{RegisterMaintenance.flag == '1'}" />
                            <rich:calendar datePattern="dd/MM/yyyy" id="calEntryDate" value="#{RegisterMaintenance.entryDate}" rendered="#{RegisterMaintenance.flag == '1'}" />
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="2" id="gridPanel18m" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblClearingRegisterDated" styleClass="label" value="Clearing Register Dated" rendered="#{RegisterMaintenance.flag == '2'}" />
                            <rich:calendar datePattern="dd/MM/yyyy" id="callearingRegisterDated" value="#{RegisterMaintenance.closeCleRegisterDts}" rendered="#{RegisterMaintenance.flag == '2'}" />
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="1" id="gridPanel18mg" style="width:100%;text-align:center;" styleClass="row2" width="100%">
                            <h:outputLabel id="labelEtc" styleClass="label" value="Extend The Clearing/ Posting Date For The Register Dated" rendered="#{RegisterMaintenance.flag == '3'}" />
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="2" id="gridPanel1a9" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblPosting" styleClass="label" value="Posting Date" rendered="#{RegisterMaintenance.flag == '1'}" />
                            <rich:calendar datePattern="dd/MM/yyyy" id="calPostingDate" value="#{RegisterMaintenance.postingDate}"  rendered="#{RegisterMaintenance.flag == '1'}" />
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="2" id="gridPanel1qa8ar" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="lbla" styleClass="label" value="Are You Confirm That All The Users Have Completed The Entries In This Register" rendered="#{RegisterMaintenance.flag == '2'}" />
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="2" id="gridPanel1qa8Pl" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblClearingRegisterDatedO" styleClass="label" value="Clearing Register Dated" rendered="#{RegisterMaintenance.flag == '3'}" />
                            <rich:calendar datePattern="dd/MM/yyyy" id="callearingRegisterDatedO" value="#{RegisterMaintenance.otherCleRegisterDts}" rendered="#{RegisterMaintenance.flag == '3'}" >
                                      <a4j:support actionListener="#{RegisterMaintenance.onChangeOtherButtonCalender}"  event="onchanged" focus="calNewPostingDate"
                                                 reRender="stxtMsg,calNewPostingDate,calNewClearingDate,calOldPostingDate,calOldClearingDate"  />
                            </rich:calendar>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="2" id="gridPanel1qa8" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblClearing" styleClass="label" value="Clearing Date"  rendered="#{RegisterMaintenance.flag == '1'}" />
                            <rich:calendar datePattern="dd/MM/yyyy" id="calClearingDate" value="#{RegisterMaintenance.clearingDate}"  rendered="#{RegisterMaintenance.flag == '1'}" />

                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="2" id="gridPanel1btn" style="height:30px;" styleClass="row2" width="100%">
                            <a4j:commandButton id="btnYes" value="Yes" style="width:80px;" action="#{RegisterMaintenance.yesButtonRegisterMaintenance}"  reRender="calEntryDate,stxtMsg" rendered="#{RegisterMaintenance.flag == '2'}">
                                
                            </a4j:commandButton>
                            <a4j:commandButton id="btnNo" value="No" style="width:80px;" action="#{RegisterMaintenance.resetAllValueNo}" reRender="gridPanel1a9,ddSelectCircleType,gridPanel1btn,gridPanedgdgl1btn,gridPanel1qa8,gridPanel1btn12,gridPanel1bt,stxtMsg,gridPanel18  ,gridPanel18m,gridPanel1qa8ar,gridPanel18mg,gridPanel1qa8Pl,gridPanel1bolpsek,gridPanel1bolp"  rendered="#{RegisterMaintenance.flag == '2'}">
                                
                            </a4j:commandButton>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="2" id="gridPanel1bolpsek" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblTwoBtn" styleClass="label" value="Please Select Posting And Clearing"  rendered="#{RegisterMaintenance.flag == '3'}" />
                            <h:selectOneListbox id="ddPostingAndClearing" styleClass="ddlist" size="1" style="width:120px" value="#{RegisterMaintenance.buttonTab}" rendered="#{RegisterMaintenance.flag == '3'}">
                                <f:selectItem itemValue="--SELECT--"/>
                                <f:selectItem itemValue="Posting"/>
                                <f:selectItem itemValue="Clearing"/>
                                <a4j:support ajaxSingle="true" event="onchange" actionListener="#{RegisterMaintenance.resetMessage}"  reRender="gridPanel1btn,gridPanedgdgl1btn,gridPanel1bt,stxtMsg   ,gridPanel1bolp" />
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="2" id="gridPanedgdgl1btn" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                            <a4j:commandButton id="btnOk" value="Ok" style="width:80px;" action="#{RegisterMaintenance.saveRegisterMaintenance}"  reRender="callearingRegisterDated,ddSelectCircleType,calPostingDate,stxtMsg,calClearingDate" rendered="#{RegisterMaintenance.flag == '1'}">
                                
                            </a4j:commandButton>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="2" id="gridPanedFree" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="2" id="gridPanel1bolp" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblOldPosting" styleClass="label" value="Old Posting Date" rendered="#{RegisterMaintenance.buttonTab == 'Posting'}" />
                            <rich:calendar datePattern="dd/MM/yyyy" id="calOldPostingDate" value="#{RegisterMaintenance.otherOldPosting}" rendered="#{RegisterMaintenance.buttonTab == 'Posting'}" disabled="true"/>
                            <h:outputLabel id="lblOldClearing" styleClass="label" value="Old Clearing Date" rendered="#{RegisterMaintenance.buttonTab == 'Clearing'}" />
                            <rich:calendar datePattern="dd/MM/yyyy" id="calOldClearingDate" value="#{RegisterMaintenance.otherOldClearing}" rendered="#{RegisterMaintenance.buttonTab == 'Clearing'}" disabled="true"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="2" id="penal123" style="width:100%;text-align:center;" styleClass="row2" width="100%">
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="2" id="penal546" style="width:100%;text-align:center;" styleClass="row2" width="100%">
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="2" id="gridPanel1bt" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblNewPosting" styleClass="label" value="New Posting Date" rendered="#{RegisterMaintenance.buttonTab == 'Posting'}" />
                            <rich:calendar datePattern="dd/MM/yyyy" id="calNewPostingDate" value="#{RegisterMaintenance.otherNewPosting}" rendered="#{RegisterMaintenance.buttonTab == 'Posting'}" />
                            <h:outputLabel id="lblNewClearing" styleClass="label" value="New Clearing Date" rendered="#{RegisterMaintenance.buttonTab == 'Clearing'}" />
                            <rich:calendar datePattern="dd/MM/yyyy" id="calNewClearingDate" value="#{RegisterMaintenance.otherNewClearing}" rendered="#{RegisterMaintenance.buttonTab == 'Clearing'}" />
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="2" id="penal78" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="2" id="penal910" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                        </h:panelGrid>

                        <h:panelGrid columnClasses="col9" columns="2" id="gridPanel1btn12" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                            <a4j:commandButton id="btnChange" value="Change" action="#{RegisterMaintenance.changeButtonRegisterMaintenance}" reRender="callearingRegisterDatedO,ddSelectCircleType,calNewPostingDate,stxtMsg,calOldClearingDate,calNewClearingDate,calOldPostingDate,gridPanel1a9,gridPanel1btn,gridPanel1qa8,gridPanel1btn12,gridPanel1bt,calOldClearingDate,gridPanel18"  rendered="#{RegisterMaintenance.flag == '3'}">
                                
                            </a4j:commandButton>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnOpen" value="Open" action="#{RegisterMaintenance.open}" reRender="gridPanel1a9,gridPanel1btn,gridPanedgdgl1btn,gridPanel1qa8,gridPanel1btn12,stxtMsg,gridPanel1bt,gridPanel18            ,gridPanel18m,gridPanel1qa8ar,gridPanel18mg,gridPanel1qa8Pl,gridPanel1bolpsek,gridPanel1bolp" focus="#{rich:clientId('calEntryDate')}InputDate">
                                
                            </a4j:commandButton>
                            <a4j:commandButton id="btnClose" value="Close" action="#{RegisterMaintenance.close}" reRender="gridPanel1a9,gridPanel1btn,gridPanedgdgl1btn,gridPanel1qa8,gridPanel1btn12,stxtMsg,gridPanel1bt,gridPanel18         ,gridPanel18m,gridPanel1qa8ar,gridPanel18mg,gridPanel1qa8Pl,gridPanel1bolpsek,gridPanel1bolp" focus="#{rich:clientId('callearingRegisterDated')}InputDate" >
                                
                            </a4j:commandButton>
                            <a4j:commandButton id="btnOthers" value="Others" action="#{RegisterMaintenance.others}" reRender="gridPanel1a9,gridPanel1btn,gridPanedgdgl1btn,gridPanel1qa8,gridPanel1btn12,stxtMsg,gridPanel1bt,gridPanel18      ,gridPanel18mg,gridPanel1qa8Pl,gridPanel1bolpsek,gridPanel18m,gridPanel1qa8ar,gridPanel1bolp" focus="#{rich:clientId('callearingRegisterDatedO')}InputDate" >
                                
                            </a4j:commandButton>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{RegisterMaintenance.resetAllValue}"  reRender="gridPanel1a9,ddSelectCircleType,gridPanedgdgl1btn,gridPanel1btn,gridPanel1qa8,gridPanel1btn12,gridPanel1bt,stxtMsg,gridPanel18  ,gridPanel18m,gridPanel1qa8ar,gridPanel18mg,gridPanel1qa8Pl,gridPanel1bolpsek,gridPanel1bolp" focus="ddSelectCircleType">
                                
                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" value="Exit"  action="#{RegisterMaintenance.exitFrm}"  reRender="gridPanel1a9,ddSelectCircleType,gridPanel1btn,gridPanedgdgl1btn,gridPanel1qa8,gridPanel1btn12,gridPanel1bt,stxtMsg,gridPanel18  ,gridPanel18m,gridPanel1qa8ar,gridPanel18mg,gridPanel1qa8Pl,gridPanel1bolpsek,gridPanel1bolp" >
                                
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
                <rich:messages></rich:messages>
            </a4j:form>
        </body>
    </html>
</f:view>
