<%-- 
    Document   : BranchMasterEft
    Created on : May 20, 2011, 2:09:11 PM
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>NeftRtgs Branch Master</title>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="gridPanel1" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{BranchMasterEft.currentDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Branch Master"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{BranchMasterEft.loggedInUser}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel001" style="width:100%;height:30px;text-align:center;border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                        <h:outputText id="stxtMsg" styleClass="error" value="#{BranchMasterEft.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel3" columns="4"  columnClasses="col2,col7,col2,col7" style="height:30px;border:1px ridge #BED6F8;padding-left:120px"  styleClass="row1" width="100%">
                            <h:outputLabel id="brancode" styleClass="label" value="Branch Code" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText  id="branCodeText" styleClass="input"  value="#{BranchMasterEft.branchCode}" maxlength="6" onkeyup="this.value=this.value.toUpperCase();">
                                <a4j:support reRender="bankCodeText,branCodeText,btnUpdate,btnSave,stxtMsg,branchNameText,shortNameText,
                                             addressText,tradeFinanceFlagText,regionCodeText,zonecodeText,ifsCodeText"  action="#{BranchMasterEft.onBlurBranchCode}" event="onblur" oncomplete="{#{rich:element('bankCodeText')}.focus();}"/>
                            </h:inputText>
                            <h:outputLabel id="bankCode" styleClass="label" value="Bank Code" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText  id="bankCodeText" styleClass="input" value="#{BranchMasterEft.bankCode}"  maxlength="6" onkeyup="this.value=this.value.toUpperCase();"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel6" columns="4" columnClasses="col2,col7,col2,col7" style="height:30px;border:1px ridge #BED6F8;padding-left:120px"  styleClass="row2" width="100%">
                            <h:outputLabel id="branchName" styleClass="label" value="Branch Name" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText   id="branchNameText" styleClass="input"  value="#{BranchMasterEft.branchName}" maxlength="50" onkeyup="this.value=this.value.toUpperCase();">
                            </h:inputText>
                            <h:outputLabel id="shortName" styleClass="label" value="Short Name" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText  id="shortNameText" styleClass="input"  value="#{BranchMasterEft.shortName}" maxlength="20" onkeyup="this.value=this.value.toUpperCase();">
                            </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel10" columns="4" columnClasses="col2,col7,col2,col7" style="height:30px;border:1px ridge #BED6F8;padding-left:120px"  styleClass="row1" width="100%">
                            <h:outputLabel id="address" styleClass="label" value="Address" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText  id="addressText" styleClass="input"  value="#{BranchMasterEft.address}" maxlength="60" onkeyup="this.value=this.value.toUpperCase();"/>
                            <h:outputLabel id="tradeFinanceFlag" styleClass="label" value="Trade Finance Flag"/>
                            <h:inputText    id="tradeFinanceFlagText" styleClass="input"  value="#{BranchMasterEft.tradeFinanceFlag}"  maxlength="1" onkeyup="this.value=this.value.toUpperCase();"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel13" columns="4" columnClasses="col2,col7,col2,col7" style="height:30px;border:1px ridge #BED6F8;padding-left:120px" styleClass="row2" width="100%">
                            <h:outputLabel id="regionCode" styleClass="label" value="Region Code"/>
                            <h:inputText id="regionCodeText" styleClass="input"  value="#{BranchMasterEft.regionCode}" maxlength="6" onkeyup="this.value=this.value.toUpperCase();"/>
                            <h:outputLabel id="zonecode" styleClass="label" value="Zone Code"/>
                            <h:inputText id="zonecodeText" styleClass="input"  value="#{BranchMasterEft.zoneCode}"  maxlength="3" onkeyup="this.value=this.value.toUpperCase();"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel16" columns="4" columnClasses="col2,col7,col2,col7" style="height:30px;border:1px ridge #BED6F8;padding-left:120px" styleClass="row1" width="100%">
                            <h:outputLabel id="ifsCode" styleClass="label" value="IFS Code" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="ifsCodeText" styleClass="input"  value="#{BranchMasterEft.ifsCode}"  maxlength="11" onkeyup="this.value=this.value.toUpperCase();"/>
                            <h:outputLabel id="labeldg"/>
                            <h:outputLabel id="labelblank"/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup  style="width:100%;text-align:center;" styleClass="vtop">

                            <a4j:commandButton disabled="#{BranchMasterEft.add}" id="btnSave" value="Save"  action="#{BranchMasterEft.saveButton}"  reRender="btnUpdate,bankCodeText,btnSave,stxtMsg,branCodeText,
                                               branchNameText,shortNameText,addressText,tradeFinanceFlagText,regionCodeText,zonecodeText,ifsCodeText">
                            </a4j:commandButton>
                            <a4j:commandButton disabled="#{BranchMasterEft.update}" id="btnUpdate" value="Update" action="#{BranchMasterEft.updateButton}" reRender="bankCodeText,btnUpdate,btnSave,stxtMsg,branCodeText,
                                               branchNameText,shortNameText,addressText,tradeFinanceFlagText,regionCodeText,zonecodeText,ifsCodeText">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{BranchMasterEft.refresh}" reRender="bankCodeText,btnUpdate,btnSave,stxtMsg,branCodeText,
                                               branchNameText,shortNameText,addressText,tradeFinanceFlagText,regionCodeText,zonecodeText,ifsCodeText" oncomplete="{#{rich:element('branCodeText')}.focus();}">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{BranchMasterEft.exitButton}" reRender="bankCodeText,btnUpdate,btnSave,stxtMsg,branCodeText,
                                               branchNameText,shortNameText,addressText,tradeFinanceFlagText,regionCodeText,zonecodeText,ifsCodeText">
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>

            </a4j:form>
        </body>
    </html>
</f:view>
