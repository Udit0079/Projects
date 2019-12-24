<%-- 
    Document   : MessageCategory
    Created on : june 09, 2011, 05:34:16 PM
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
            <title>Message Category Detail</title>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="panelGrid1" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{MessageCategory.currentDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Message Category Detail"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{MessageCategory.loggedInUser}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel001" style="width:100%;height:30px;text-align:center;border:1px ridge #BED6F8;" styleClass="row1" width="100%">
                        <h:outputText id="stxtMsg" styleClass="error" value="#{MessageCategory.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panelGrid6" columns="4" columnClasses="col2,col7,col2,col7" style="height:30px;border:1px ridge #BED6F8;padding-left:120px"  styleClass="row2" width="100%">
                        <h:outputLabel id="lblMessageSubType" styleClass="label" value="Message Sub Type"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText  id="txtMsgSubType" styleClass="input"  value="#{MessageCategory.msgSubType}" maxlength="3" onkeyup="this.value=this.value.toUpperCase();">
                            <a4j:support reRender="txtMsgType,txtScreenFlag,btnUpdate,btnSave,stxtMsg,paysysIdText,txtMsgSubType,
                                         txtMessage,btnDelete"  action="#{MessageCategory.onBlurMessageSubType}" event="onblur" focus="paysysIdText"/>
                        </h:inputText>
                        <h:outputLabel id="paysysId" styleClass="label" value="Pay SysId"></h:outputLabel>
                        <h:inputText   id="paysysIdText" styleClass="input"  value="#{MessageCategory.paySysId}" maxlength="5" onkeyup="this.value=this.value.toUpperCase();">
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid id="panelGrid3" columns="4"  columnClasses="col2,col7,col2,col7" style="height:30px;border:1px ridge #BED6F8;padding-left:120px"  styleClass="row1" width="100%">
                        <h:outputLabel id="lblMsgType" styleClass="label" value="Message Type"></h:outputLabel>
                        <h:inputText  id="txtMsgType" styleClass="input"  value="#{MessageCategory.msgType}" maxlength="3" onkeyup="this.value=this.value.toUpperCase();">
                        </h:inputText>
                        <h:outputLabel id="lblScreenFlag" styleClass="label" value="Screen Flag"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText  id="txtScreenFlag" styleClass="input" value="#{MessageCategory.screenFlag}"  maxlength="1" onkeyup="this.value=this.value.toUpperCase();"/>
                    </h:panelGrid>
                    <h:panelGrid id="panelGrid9" columns="4" columnClasses="col2,col7,col2,col7" style="height:30px;border:1px ridge #BED6F8;padding-left:120px"  styleClass="row2" width="100%">
                        <h:outputLabel id="lblMessage" styleClass="label" value="Message" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText  id="txtMessage" styleClass="input"  value="#{MessageCategory.eftMessage}"  maxlength="50" onkeyup="this.value=this.value.toUpperCase();" size="45"/>
                        <%--<h:outputLabel id="outwdAcHold" styleClass="label" value=""/>
<h:outputLabel id="outwdAcHoldfs" styleClass="label" value=""/>--%>
                        <h:outputLabel id="lblScheduleId" styleClass="label" value="Schedule"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="txtScheduleId" styleClass="ddlist" size="1" style="width: 70px" value="#{MessageCategory.scheduleId}" >
                            <f:selectItems value="#{MessageCategory.scheduleIdList}" />
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup  style="width:100%;text-align:center;" styleClass="vtop">
                            <a4j:commandButton disabled="#{MessageCategory.add}" id="btnSave" value="Save"  action="#{MessageCategory.saveButton}"  reRender="txtScreenFlag,btnUpdate,btnDelete,btnSave,stxtMsg,txtMsgType,
                                               paysysIdText,txtMsgSubType,txtMessage,txtScheduleId">
                            </a4j:commandButton>
                            <a4j:commandButton disabled="#{MessageCategory.update}" id="btnUpdate" value="Update" action="#{MessageCategory.updateButton}" reRender="txtScreenFlag,btnUpdate,btnDelete,btnSave,stxtMsg,txtMsgType,
                                               paysysIdText,txtMsgSubType,txtMessage,txtScheduleId">
                            </a4j:commandButton>
                            <a4j:commandButton disabled="#{MessageCategory.delete}" id="btnDelete" value="Delete" action="#{MessageCategory.deleteButton}" reRender="txtScreenFlag,btnUpdate,btnDelete,btnSave,stxtMsg,txtMsgType,
                                               paysysIdText,txtMsgSubType,txtMessage,txtScheduleId">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{MessageCategory.refresh}" reRender="txtScreenFlag,btnUpdate,btnSave,stxtMsg,txtMsgType,
                                               paysysIdText,txtMsgSubType,btnDelete,txtMessage,txtScheduleId" oncomplete="{#{rich:element('txtMsgSubType')}.focus();}">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{MessageCategory.exitButton}" reRender="txtScreenFlag,btnUpdate,btnSave,stxtMsg,txtMsgType,
                                               paysysIdText,txtMsgSubType,btnDelete,txtMessage,txtScheduleId">
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>

            </a4j:form>
        </body>
    </html>
</f:view>

