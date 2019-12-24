<%-- 
    Document   : chargemaster
    Created on : Jul 28, 2012, 10:45:46 AM
    Author     : Ankit Verma
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <link href="mycss.css" rel="stylesheet" type="text/css"/>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Charge Master</title>
        </head>
        <body>
            <a4j:form>
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{chargeMaster.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Charge Master"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{chargeMaster.userName}"/>
                        </h:panelGroup>                         
                    </h:panelGrid>
                    <h:panelGrid id="inputPanel" columnClasses="col3,col3,col3,col3,col3,col1" columns="6" styleClass="row1" style="width:100%;text-align:left;">
                        <h:outputLabel id="lblMessageType" styleClass="label" value="Message Type"/>
                        <h:selectOneListbox value="#{chargeMaster.messageType}" style="ddstyle" size="1">
                            <f:selectItem itemValue="--SELECT--"/>
                            <f:selectItem itemValue="PROMOTIONAL"/>
                            <f:selectItem itemValue="TRANSACTIONAL"/>
                            <f:selectItem itemValue="ON-REQUEST"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblChargePerMessage" value="Charge Per Message" styleClass="label"/>
                        <h:inputText id="txtChargePerMessage" styleClass="input" value="#{chargeMaster.chargePerMessage}" maxlength="8" size="10"/>
                        <h:outputLabel id="lblCrGlHead" value="Credited GL Head" styleClass="label"/>
                        <h:inputText id="txtCrGlHead" styleClass="input" value="#{chargeMaster.crHead}" maxlength="6" size="12"/>
                    </h:panelGrid>
                    <h:panelGrid columns="2" columnClasses="col10,col11" style="text-align:left;width:100%" width="100%" styleClass="footer" >
                        <h:outputText id="msg" styleClass="error" value="#{chargeMaster.message}"/>
                        <h:panelGroup id="btnGroupPanel" >
                            <a4j:commandButton id="save" value="Save" actionListener="#{chargeMaster.saveAction}" reRender="mainPanel,msg"/>
                            <a4j:commandButton id="refresh" value="Refresh" action="#{chargeMaster.refreshForm}"  reRender="mainPanel"/>
                            <a4j:commandButton id="poBtnCancel" value="Exit"  action="#{chargeMaster.exitForm}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </td>
    </tr></table>
</body>
</html>
</f:view>
