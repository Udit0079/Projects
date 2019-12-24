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
            <title>Super User Creation</title>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="gridPanel" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{SuperUser.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Super User Creation"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{SuperUser.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="errorMsgReporting"   width="100%"   style="width:100%;text-align:center;" styleClass="row1">
                        <h:outputText id="errorGrid" value="#{SuperUser.message}" style="color:red" styleClass="output"/>
                    </h:panelGrid>

                    <h:panelGrid id="gpBranchCode" columns="4" columnClasses="col13,col13,col13,col13" style="height:30px;width:100%"  styleClass="row2" width="100%">
                        <h:outputLabel id="lblBranch" styleClass="label" value="Branch :"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddBranch" styleClass="ddlist" value="#{SuperUser.brCode}" size="1" style="width: 120px" disabled="#{SuperUser.flag1}">
                            <f:selectItems value="#{SuperUser.brCodeList}"/>
                            <a4j:support event="onblur" reRender="input1,newpasswword,newpasswwordretype,newpasswwordretype1,ddAcNo35,errorGrid,Create,btnExit,gpBranchCode"
                                         action="#{SuperUser.branchOnblur}" focus="input1"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblFromDate" styleClass="label" value="UserId :">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:inputText  id="input1" styleClass="input" value="#{SuperUser.userId}"  maxlength="30" style="width:120px">
                            <a4j:support event="onblur"  reRender="ddAcNo35,input1,newpasswword,newpasswwordretype,newpasswwordretype1,ddAcNo35,errorGrid,Create,btnExit,gpBranchCode,newpasswwordretyp6e"
                                         action="#{SuperUser.userIdChange}" focus="newpasswword"/>
                        </h:inputText>
                    </h:panelGrid>

                    <h:panelGrid id="gridPanel2" columns="4" columnClasses="col13,col13,col13,col13" style="height:30px;width:100%"  styleClass="row1" width="100%">
                        <h:outputLabel id="lblToDate" styleClass="label" value="User Name :" >
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:inputText  id="newpasswword" styleClass="input"  value="#{SuperUser.user}" maxlength="40" style="width:120px">
                            <a4j:support event="onblur" action="#{SuperUser.onBlurUserName}" reRender="newpasswword,errorGrid,Create,gpBranchCode"/>
                        </h:inputText>
                        <h:outputLabel id="retypenewpassword" styleClass="label" value="Password :" >
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:inputSecret  disabled="#{SuperUser.textpasswordvalue}"  id="newpasswwordretype" styleClass="input" value="#{SuperUser.password}" maxlength="60" style="width:120px">
                        </h:inputSecret>
                    </h:panelGrid>

                    <h:panelGrid id="gridPanel3" columns="4" columnClasses="col13,col13,col13,col13" style="height:30px;width:100%"  styleClass="row2" width="100%">
                        <h:outputLabel id="retypenewpassword1" styleClass="label" value="Re Type Password :">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:inputSecret  disabled="#{SuperUser.textRePasswordvalue}"  id="newpasswwordretype1" styleClass="input"  value="#{SuperUser.reTypePassword}" maxlength="60" style="width:120px">
                        </h:inputSecret>
                        <h:outputLabel id="retypenewpassword2" styleClass="label" value="Authorization Level :" >
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:selectOneListbox  id="ddAcNo35" styleClass="ddlist" size="1"  style="width:120px"  value="#{SuperUser.authorizationLevel}">
                            <f:selectItems value="#{SuperUser.authorizationList}"/>
                            <a4j:support event="onblur" action="#{SuperUser.onSelectListOption}" reRender="Create,gpBranchCode" focus="newpasswwordretyp6e"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="newpasspanel1" columns="4" columnClasses="col13,col13,col13,col13" style="height:30px;width:100%"  styleClass="row2" width="100%">

                        <h:outputLabel id="retypenewpassword6" styleClass="label" value="User Address :" />
                        <h:inputText  id="newpasswwordretyp6e" styleClass="input"  value="#{SuperUser.userAddress}"  maxlength="100" onkeypress="this.value = this.value.toUpperCase();" style="width:230px"/>
                        <h:outputText/>
                        <h:outputText/>
                    </h:panelGrid>

                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel" styleClass="vtop">
                            <a4j:commandButton  disabled="#{SuperUser.createButtonValue}" id="Create" value="Create" action="#{SuperUser.createButton}"
                                                reRender="Create,errorGrid,input1,newpasswword,newpasswwordretype,newpasswwordretype1,ddAcNo35,errorGrid,Create,btnExit,gpBranchCode,newpasswwordretyp6e" />
                            <a4j:commandButton  id="btnRefresh"  value="Refresh" action="#{SuperUser.refreshPage}"
                                                reRender="input1,newpasswword,newpasswwordretype,newpasswwordretype1,ddAcNo35,errorGrid,Create,btnExit,gpBranchCode,newpasswwordretyp6e" focus="ddBranch"/>
                            <a4j:commandButton  id="btnExit" value="Exit" action="#{SuperUser.exitBtnAction}"
                                                reRender="input1,newpasswword,newpasswwordretype,newpasswwordretype1,ddAcNo35,errorGrid,Create,btnExit,gpBranchCode,newpasswwordretyp6e"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
