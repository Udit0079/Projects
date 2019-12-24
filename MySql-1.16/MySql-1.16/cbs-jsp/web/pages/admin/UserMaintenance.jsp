<%--
    Document   : UserMaintenance
    Created on : Dec 3, 2010, 11:15:21 AM
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
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>User Maintenance</title>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="gridPanel" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{UserMaintenance.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="User Maintenance"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{UserMaintenance.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="errorMsgReporting"   width="100%"   style="width:100%;text-align:center;" styleClass="row1">
                        <h:outputText id="errorGrid" value="#{UserMaintenance.message}" style="color:red" styleClass="output"/>
                    </h:panelGrid>

                    <h:panelGrid id="gpBranchCode" columns="4" columnClasses="col13,col13,col13,col13" style="height:30px;width:100%"  styleClass="row2" width="100%">
                        <h:outputLabel id="lblBranch" styleClass="label" value="Branch :"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddBranch" styleClass="ddlist" value="#{UserMaintenance.brCode}" size="1" style="width: 120px" disabled="#{UserMaintenance.flag1}">
                                <f:selectItems value="#{UserMaintenance.brCodeList}"/>
                                <a4j:support event="onblur" reRender="input1,newpasswword,newpasswwordretype,newpasswwordretype1,ddAcNo35,newpasswwordretype3,newpasswwordretype4,newpasswwordretype5,newpasswwordretyp6e,errorGrid,Create,Active,Delete,Expire,btnExit,Update,gpBranchCode"
                                             actionListener="#{UserMaintenance.branchOnblur}" focus="input1"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblFromDate" styleClass="label" value="UserId :">
                            <font class="required" style="color:red;">*</font>
                            </h:outputLabel>
                            <h:inputText  id="input1" styleClass="input" value="#{UserMaintenance.userId}"  maxlength="30" style="width:120px">
                                <a4j:support event="onblur"  reRender="ddAcNo35,input1,newpasswword,newpasswwordretype,newpasswwordretype1,ddAcNo35,newpasswwordretype3,newpasswwordretype4,newpasswwordretype5,newpasswwordretyp6e,errorGrid,Create,Active,Delete,Expire,btnExit,Update,gpBranchCode,deputXferPanel1,deputXferPanel2,npciUserName"
                                             actionListener="#{UserMaintenance.userIdChange}" focus="newpasswword"/>
                            </h:inputText>
                        </h:panelGrid>

                    <h:panelGrid id="gridPanel2" columns="4" columnClasses="col13,col13,col13,col13" style="height:30px;width:100%"  styleClass="row1" width="100%">
                        <h:outputLabel id="lblToDate" styleClass="label" value="User Name :" >
                            <font class="required" style="color:red;">*</font>
                            </h:outputLabel>
                            <h:inputText  id="newpasswword" styleClass="input"  value="#{UserMaintenance.user}" maxlength="40" style="width:120px">
                                <a4j:support event="onblur" actionListener="#{UserMaintenance.onBlurUserName}" reRender="newpasswword,errorGrid,Create,gpBranchCode"/>
                            </h:inputText>
                            <h:outputLabel id="retypenewpassword" styleClass="label" value="Password :" >
                            <font class="required" style="color:red;">*</font>
                            </h:outputLabel>
                            <h:inputSecret  disabled="#{UserMaintenance.textpasswordvalue}"  id="newpasswwordretype" styleClass="input" value="#{UserMaintenance.password}" maxlength="60" style="width:120px">
                            </h:inputSecret>
                        </h:panelGrid>

                    <h:panelGrid id="gridPanel3" columns="4" columnClasses="col13,col13,col13,col13" style="height:30px;width:100%"  styleClass="row2" width="100%">
                        <h:outputLabel id="retypenewpassword1" styleClass="label" value="Re Type Password :">
                            <font class="required" style="color:red;">*</font>
                            </h:outputLabel>
                            <h:inputSecret  disabled="#{UserMaintenance.textRePasswordvalue}"  id="newpasswwordretype1" styleClass="input"  value="#{UserMaintenance.reTypePassword}" maxlength="60" style="width:120px">  
                            </h:inputSecret>
                            <h:outputLabel id="retypenewpassword2" styleClass="label" value="Authorization Level :" >
                            <font class="required" style="color:red;">*</font>
                            </h:outputLabel>
                            <h:selectOneListbox  id="ddAcNo35" styleClass="ddlist" size="1"  style="width:120px"  value="#{UserMaintenance.authorizationLevel}" disabled="#{UserMaintenance.authorizationLevelFlag}">
                                <f:selectItems value="#{UserMaintenance.authorizationList}"/>
                                <a4j:support event="onblur" actionListener="#{UserMaintenance.onSelectListOption}" reRender="Create,newpasswwordretype3,newpasswwordretype4,newpasswwordretype5,gpBranchCode,deputXferPanel1,deputXferPanel2" focus="newpasswwordretype3"/>
                            </h:selectOneListbox> 
                        </h:panelGrid>

                    <h:panelGrid id="newpasspanel" columns="4" columnClasses="col13,col13,col13,col13" style="height:30px;width:100%"  styleClass="row1" width="100%">
                        <h:outputLabel id="retypenewpassword3" styleClass="label" value="Passing Cash Debit Limit :" />
                        <h:inputText   disabled="#{UserMaintenance.cashLimitValue}"  id="newpasswwordretype3" styleClass="input"  value="#{UserMaintenance.cashLimit}"  maxlength="14" style="width:120px"/>
                        <h:outputLabel id="retypenewpassword4" styleClass="label" value="Passing Clearing Debit Limit :" />
                        <h:inputText id="newpasswwordretype4"  disabled="#{UserMaintenance.clearingLimitvalue}" styleClass="input"  value="#{UserMaintenance.clearingLimit}" maxlength="14" style="width:120px"/>
                    </h:panelGrid>

                    <h:panelGrid id="newpasspanel1" columns="4" columnClasses="col13,col13,col13,col13" style="height:30px;width:100%"  styleClass="row2" width="100%">
                        <h:outputLabel id="retypenewpassword5" styleClass="label" value="Passing Transfer Debit Limit :" />
                        <h:inputText id="newpasswwordretype5"  disabled="#{UserMaintenance.transferLImitValue}" styleClass="input"  value="#{UserMaintenance.transferLimit}"  maxlength="14" style="width:120px"/>
                        <h:outputLabel id="lblnpciUserName" styleClass="label" value="NPCI User Name" />
                        <h:inputText  id="npciUserName" styleClass="input" value="#{UserMaintenance.npciUserName}"  maxlength="30" style="width:120px"/>
                        
                    </h:panelGrid>
                    <h:panelGrid id="npciusername" columns="4" columnClasses="col13,col13,col13,col13" style="height:30px;width:100%;"  styleClass="row2" width="100%">
                        <h:outputLabel id="retypenewpassword6" styleClass="label" value="User Address :" />
                        <h:inputText  id="newpasswwordretyp6e" styleClass="input"  value="#{UserMaintenance.userAddress}"  maxlength="100" onblur="this.value = this.value.toUpperCase();" style="width:230px"/>
                        <h:outputLabel />
                        <h:outputLabel />
                    </h:panelGrid>                
                    <h:panelGrid id="deputXferPanel1" columns="4" columnClasses="col13,col13,col13,col13" style="height:30px;width:100%;display:#{UserMaintenance.deputXferFlag};"  styleClass="row1" width="100%">
                        <h:outputLabel id="lblDeputXfer" styleClass="label" value="Deputation/Transfer" />
                        <h:selectOneListbox  id="ddDeputXfer" styleClass="ddlist" size="1" style="width:120px"  value="#{UserMaintenance.deputOrXfer}">
                            <f:selectItems value="#{UserMaintenance.deputOrXferList}"/>
                            <a4j:support actionListener="#{UserMaintenance.onblurDeputXfer}" event="onblur" reRender="deputXferPanel1,deputXferPanel2" focus="ddOperBrnCode"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblOperBrnCode" styleClass="label" value="Operational Branch Code"/>
                        <h:selectOneListbox  id="ddOperBrnCode" styleClass="ddlist" size="1" style="width:120px;"  value="#{UserMaintenance.operBrnCode}">
                            <f:selectItems value="#{UserMaintenance.operBrnCodeList}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="deputXferPanel2" columns="4" columnClasses="col13,col13,col13,col13" style="height:30px;width:100%;display:#{UserMaintenance.fromToDateFlag};"  styleClass="row1" width="100%">
                        <h:outputLabel id="lblFromDt" styleClass="label" value="From Date" />
                        <rich:calendar  value="#{UserMaintenance.fromDt}" datePattern="dd/MM/yyyy" id="calFromDt" jointPoint="top-left" direction="top-right" cellWidth="10" enableManualInput="true" inputSize="10">
                            <f:attribute name="maxlength" value="10"/>
                        </rich:calendar>
                        <h:outputLabel id="lblToDt" styleClass="label" value="To Date" />
                        <rich:calendar  value="#{UserMaintenance.toDate}" datePattern="dd/MM/yyyy" id="calToDt" jointPoint="top-left" direction="top-right" cellWidth="10" enableManualInput="true" inputSize="10">
                            <f:attribute name="maxlength" value="10"/>
                        </rich:calendar>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer"> 
                        <h:panelGroup id="btnPanel" styleClass="vtop">
                            <a4j:commandButton  disabled="#{UserMaintenance.createButtonValue}" id="Create" value="Create" action="#{UserMaintenance.createButton}"
                                                reRender="Create,errorGrid,input1,newpasswword,newpasswwordretype,newpasswwordretype1,ddAcNo35,newpasswwordretype3,newpasswwordretype4,newpasswwordretype5,newpasswwordretyp6e,errorGrid,Create,Active,Delete,Expire,btnExit,Update,gpBranchCode,npciUserName" />
                            <a4j:commandButton  disabled="#{UserMaintenance.updateButtonValue}" id="Update" value="Update" action="#{UserMaintenance.updateButton}"
                                                reRender="errorGrid,input1,newpasswword,newpasswwordretype,newpasswwordretype1,ddAcNo35,newpasswwordretype3,newpasswwordretype4,newpasswwordretype5,newpasswwordretyp6e,errorGrid,Create,Active,Delete,Expire,btnExit,Update,gpBranchCode,deputXferPanel1,deputXferPanel2,npciUserName" />
                            <a4j:commandButton  disabled="#{UserMaintenance.activeButtonValue}"  id="Active" value="Active" action="#{UserMaintenance.active}"
                                                reRender="errorGrid,Active,input1,newpasswword,newpasswwordretype,newpasswwordretype1,ddAcNo35,newpasswwordretype3,newpasswwordretype4,newpasswwordretype5,newpasswwordretyp6e,errorGrid,Create,Active,Delete,Expire,btnExit,Update,gpBranchCode,npciUserName" />
                            <a4j:commandButton  disabled="#{UserMaintenance.expireButtonValue}" id="Expire" value="Expire" action="#{UserMaintenance.expire}"
                                                reRender="errorGrid,input1,newpasswword,newpasswwordretype,newpasswwordretype1,ddAcNo35,newpasswwordretype3,newpasswwordretype4,newpasswwordretype5,newpasswwordretyp6e,errorGrid,Create,Active,Delete,Expire,btnExit,Update,gpBranchCode,npciUserName" />
                            <a4j:commandButton  disabled="#{UserMaintenance.deleteButtonValue}" id="Delete" value="Delete"  action="#{UserMaintenance.delete}"
                                                reRender="errorGrid,input1,newpasswword,newpasswwordretype,newpasswwordretype1,ddAcNo35,newpasswwordretype3,newpasswwordretype4,newpasswwordretype5,newpasswwordretyp6e,errorGrid,Create,Active,Delete,Expire,btnExit,Update,gpBranchCode,npciUserName" />
                            <a4j:commandButton  disabled="true" id="Report"  value="Report"/>
                            <a4j:commandButton  id="btnRefresh"  value="Refresh" action="#{UserMaintenance.refreshPage}"
                                                reRender="input1,newpasswword,newpasswwordretype,newpasswwordretype1,ddAcNo35,newpasswwordretype3,newpasswwordretype4,newpasswwordretype5,newpasswwordretyp6e,errorGrid,Create,Active,Delete,Expire,btnExit,Update,gpBranchCode,deputXferPanel1,deputXferPanel2,npciUserName" focus="ddBranch"/>
                            <a4j:commandButton  id="btnExit" value="Exit" action="#{UserMaintenance.exitBtnAction}"
                                                reRender="input1,newpasswword,newpasswwordretype,newpasswwordretype1,ddAcNo35,newpasswwordretype3,newpasswwordretype4,newpasswwordretype5,newpasswwordretyp6e,errorGrid,Create,Active,Delete,Expire,btnExit,Update,gpBranchCode,npciUserName"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
