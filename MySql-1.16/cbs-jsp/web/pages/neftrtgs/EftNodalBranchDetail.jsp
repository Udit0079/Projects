<%-- 
    Document   : EftNodalBranchDetail
    Created on : june 02, 2011, 11:03:16 AM
    Author     : jitendra Chaudhary
--%>

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
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>Nodal Branch Detail</title>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="panelGrid1" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{EftNodalBranchDetail.currentDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Nodal Branch Detail"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{EftNodalBranchDetail.loggedInUser}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel001" style="width:100%;height:30px;text-align:center;border:1px ridge #BED6F8;" styleClass="row1" width="100%">
                        <h:outputText id="stxtMsg" styleClass="error" value="#{EftNodalBranchDetail.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panelGrid6" columns="4" columnClasses="col2,col7,col2,col7" style="height:30px;border:1px ridge #BED6F8;padding-left:120px"  styleClass="row2" width="100%">
                            <h:outputLabel id="crAcId" styleClass="label" value="Cr A/c Id"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText  id="crAcIdText" styleClass="input"  value="#{EftNodalBranchDetail.crAccId}" maxlength="16" onkeyup="this.value=this.value.toUpperCase();">
                                <a4j:support reRender="branchcodetext,bankCodeText,btnUpdate,btnSave,stxtMsg,paysysIdText,crAcIdText,
                                             inwdAcText,outwdAcHoldText,inwdSundryText,commAcHolderText"  action="#{EftNodalBranchDetail.onBlurBranchCode}" event="onblur" focus="paysysIdText"/>
                            </h:inputText>
                            <h:outputLabel id="paysysId" styleClass="label" value="Pay SysId"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText   id="paysysIdText" styleClass="input"  value="#{EftNodalBranchDetail.paySysId}" maxlength="5" onkeyup="this.value=this.value.toUpperCase();">
                            </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid id="panelGrid3" columns="4"  columnClasses="col2,col7,col2,col7" style="height:30px;border:1px ridge #BED6F8;padding-left:120px"  styleClass="row1" width="100%">
                            <h:outputLabel id="branchcode" styleClass="label" value="Branch Code"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText  id="branchcodetext" styleClass="input"  value="#{EftNodalBranchDetail.branchCode}" maxlength="6" onkeyup="this.value=this.value.toUpperCase();">
                            </h:inputText>
                            <h:outputLabel id="bankcode" styleClass="label" value="Bank Code"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText  id="bankCodeText" styleClass="input" value="#{EftNodalBranchDetail.bankCode}"  maxlength="6" onkeyup="this.value=this.value.toUpperCase();"/>
                    </h:panelGrid>
                    <h:panelGrid id="panelGrid9" columns="4" columnClasses="col2,col7,col2,col7" style="height:30px;border:1px ridge #BED6F8;padding-left:120px"  styleClass="row2" width="100%">
                            <h:outputLabel id="inwdAc" styleClass="label" value="Inward A/c Place Holder"/>
                            <h:inputText  id="inwdAcText" styleClass="input"  value="#{EftNodalBranchDetail.inwardAccPlaceHolder}"  maxlength="16" onkeyup="this.value=this.value.toUpperCase();"/>
                            <h:outputLabel id="outwdAcHold" styleClass="label" value="Outward A/c Place Holder"/>
                            <h:inputText    id="outwdAcHoldText" styleClass="input"  value="#{EftNodalBranchDetail.outwardAccPlaceHolder}"  maxlength="16" onkeyup="this.value=this.value.toUpperCase();"/>
                    </h:panelGrid>
                    <h:panelGrid id="panelGrid12" columns="4" columnClasses="col2,col7,col2,col7" style="height:30px;border:1px ridge #BED6F8;padding-left:120px" styleClass="row1" width="100%">
                            <h:outputLabel id="inwdSundry" styleClass="label" value="Inward Sundry Place Holder"/>
                            <h:inputText id="inwdSundryText" styleClass="input"  value="#{EftNodalBranchDetail.inwardSundryPlaceHolder}" maxlength="16" onkeyup="this.value=this.value.toUpperCase();"/>
                            <h:outputLabel id="commAcHolder" styleClass="label" value="Comm Acc Place Holder"/>
                            <h:inputText id="commAcHolderText" styleClass="input"  value="#{EftNodalBranchDetail.commAccPlaceHolder}"  maxlength="16" onkeyup="this.value=this.value.toUpperCase();"/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup  style="width:100%;text-align:center;" styleClass="vtop">
                            <a4j:commandButton disabled="#{EftNodalBranchDetail.add}" id="btnSave" value="Save"  action="#{EftNodalBranchDetail.saveButton}"  reRender="bankCodeText,btnUpdate,btnSave,stxtMsg,branchcodetext,
                                               paysysIdText,crAcIdText,inwdAcText,outwdAcHoldText,inwdSundryText,commAcHolderText">
                            </a4j:commandButton>
                            <a4j:commandButton disabled="#{EftNodalBranchDetail.update}" id="btnUpdate" value="Update" action="#{EftNodalBranchDetail.updateButton}" reRender="bankCodeText,btnUpdate,btnSave,stxtMsg,branchcodetext,
                                               paysysIdText,crAcIdText,inwdAcText,outwdAcHoldText,inwdSundryText,commAcHolderText">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{EftNodalBranchDetail.refresh}" reRender="bankCodeText,btnUpdate,btnSave,stxtMsg,branchcodetext,
                                               paysysIdText,crAcIdText,inwdAcText,outwdAcHoldText,inwdSundryText,commAcHolderText" oncomplete="{#{rich:element('crAcIdText')}.focus();}">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{EftNodalBranchDetail.exitButton}" reRender="bankCodeText,btnUpdate,btnSave,stxtMsg,branchcodetext,
                                               paysysIdText,crAcIdText,inwdAcText,outwdAcHoldText,inwdSundryText,commAcHolderText">
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>

            </a4j:form>
        </body>
    </html>
</f:view>

