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
            <title>Instant Messaging</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{instantMessaging.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Instant Messaging"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{instantMessaging.userName}"/>
                        </h:panelGroup>                         
                    </h:panelGrid>
                    <h:panelGrid columns="4" columnClasses="col3,col3,col3,col3" id="gridpanel1"   style="height:30px;txt-align:leftext-align:left" styleClass="row2" width="100%" >
                        <h:outputLabel id="lblBranch" styleClass="label" value="Branch"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddBranch" style="width: 120px" styleClass="ddlist" value="#{instantMessaging.branch}" size="1">
                                <f:selectItems value="#{instantMessaging.branchList}" />
                            </h:selectOneListbox>
                            <h:outputLabel id="lblMsgType" styleClass="label" value="Message Type"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddMsgType" style="width: 120px" styleClass="ddlist" value="#{instantMessaging.msgType}" size="1">
                                <f:selectItems value="#{instantMessaging.msgTypeList}" />
                                <a4j:support event="onblur" action="#{instantMessaging.msgTypeAction}" reRender="gridpanel1,functionGrid3,footerPanel,functionGrid2"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid id="functionGrid3" columnClasses="col3,col3,col3,col3"  columns="4" style="height:30px;display:#{instantMessaging.displayOnIndividual}" styleClass="row1" width="100%">
                            <h:outputLabel id="lblModfyAcno" styleClass="label" value="Mobile No" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtmdfyAcno" styleClass="input" value="#{instantMessaging.mobileNo}" maxlength="10" size="15"/>
                            <h:outputLabel id="lblMobileAcno" styleClass="label" value="A/C Number" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:panelGroup>
                                <h:inputText id="txtmobileAcno" styleClass="input" value="#{instantMessaging.acno}" maxlength="#{instantMessaging.acNoMaxLen}" size="15">
                                    <a4j:support event="onblur" action="#{instantMessaging.accountAction}" reRender="mainPanel,footerPanel,stxtNewAccount" />
                                </h:inputText>
                                <h:outputText id="stxtNewAccount" styleClass="output" value="#{instantMessaging.newAccountNo}"/>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid id="functionGrid2" columnClasses="col1,col4,col1,col4,col1,col4" columns="6" style="height:30px;display:#{instantMessaging.displayOnProductWise}" styleClass="row1" width="100%">
                            <h:outputLabel id="lblForgetFlag" styleClass="label" value="Filter"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddForgetFlag" style="width:150px" styleClass="ddlist"  value="#{instantMessaging.filterType}" size="1">
                                <f:selectItems value="#{instantMessaging.filterTypeList}"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblSeqType" styleClass="label" value="Nature"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddSeqType" style="width: 100px" styleClass="ddlist"  value="#{instantMessaging.natureType}" size="1">
                                <f:selectItems value="#{instantMessaging.natureTypeList}"/>
                                <a4j:support  action="#{instantMessaging.getAccountDetails}" event="onchange"
                                              reRender="ddFreqType" oncomplete="setMask();"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblFreqType" styleClass="label" value="Account Type"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddFreqType" style="width: 100px" styleClass="ddlist"  value="#{instantMessaging.accountType}" size="1">
                                <f:selectItems value="#{instantMessaging.accountTypeList}"/>
                                <a4j:support event="onblur"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columns="1" columnClasses="col3,col3,col3,col3" id="gridpanelInfo"   style="height:30px;" styleClass="row2" width="100%" >
                            <h:outputLabel  value="Instant Message Active, Message should not exceed 160 characters" styleClass="error" style="color:blue"/>
                        </h:panelGrid>
                        <h:panelGrid columns="1" columnClasses="col3,col3,col3,col3" id="gridpanelMessage"   style="height:30px;" styleClass="row1" width="100%" >
                            <h:inputTextarea rows="3" cols="80" id="txtAreaMessage"  value="#{instantMessaging.message}" styleClass="input" style="height:60px;width:450px">
                                <a4j:support event="onkeyup"  action="#{instantMessaging.countingAction}" reRender="count"  />
                            </h:inputTextarea>
                            <h:outputText id="count" value="Total Characters : #{instantMessaging.count}" styleClass="output"/>
                        </h:panelGrid>

                    <h:panelGrid id="footerPanel" style="width:100%;text-align:left;" columns="2" columnClasses="col10,col11" styleClass="footer">
                        <h:outputLabel id="lblMsg" styleClass="error" value="#{instantMessaging.msg}" />
                        <a4j:region id="a4jregion">
                            <h:panelGroup id="BtnPanel">
                                <a4j:commandButton id="btnSend"  value="Send" reRender="mainPanel" action="#{instantMessaging.sendMessage}"/>
                                <a4j:commandButton id="btnRefresh" value="Refresh" action="#{instantMessaging.refreshPage()}" reRender="mainPanel"/>
                                <a4j:commandButton id="poBtnCancel" value="Exit"  action="#{instantMessaging.exitForm}" reRender="mainPanel"/>
                            </h:panelGroup>
                        </a4j:region>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="a4jregion"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </td>
    </tr></table>
</body>   
</html>
</f:view>
