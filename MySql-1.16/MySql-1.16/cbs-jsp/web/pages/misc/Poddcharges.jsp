<%-- 
    Document   : Poddcharges
    Created on : May 11, 2011, 4:03:40 AM
    Author     : Himanshu Bhatia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>PO DD Charges</title>
            <script type="text/javascript">
            </script>
        </head>
        <body>
            <a4j:form id="PODDcharges">
                <h:panelGrid id="PanelGridMain" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="HeaderPanel" columns="3"  style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{Poddcharges.datetoday}" />
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="PO DD Charges Calculator "/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUsername" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="otUsername" styleClass="output" value="#{Poddcharges.user}" />
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid id="BodyPanel0" styleClass="row1" columns="1" style="border:1px ridge #BED6F8;text-align:center"  width="100%">
                        <h:outputLabel id="errmsg" value="#{Poddcharges.msg}"  styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid id="BodyPanel1" styleClass="row2"  columns="6" columnClasses="col3,col3,col3,col3,col3,col1" style="border:1px ridge #BED6F8;text-align:left"  width="100%">
                        <h:outputLabel value="Remittence Type"  styleClass="label"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="reTypeSelect" styleClass="ddlist" size="1" tabindex="1" style="width:115px" value="#{Poddcharges.paytype}" >
                            <f:selectItems value="#{Poddcharges.modeListOption}" />
                        </h:selectOneListbox>
                        <h:outputLabel value="Mode" style="padding-left:70px" styleClass="label"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="modeSelect" styleClass="ddlist" size="1" tabindex="2" style="width:115px;" value="#{Poddcharges.mode}">
                            <f:selectItem itemValue="--Select--"/>
                            <f:selectItem itemValue="Cash" />
                            <f:selectItem itemValue="Transfer" />
                        </h:selectOneListbox>
                        <h:outputLabel value="Amount" style="padding-left:70px" styleClass="label"> <font class="required" color="red">*</font> </h:outputLabel>
                        <h:inputText id="amtText" size="20" style="width:125px" value="#{Poddcharges.amt}" styleClass="input" tabindex="3"  >
                            <a4j:support actionListener="#{Poddcharges.calcul}" event="onblur"   reRender="PanelGridMain" />
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid id="BodyPanel2" styleClass="row1" columns="6" columnClasses="col2,col1,col1,col1,col1,col1" style="text-align:left"  width="100%" >
                        <h:outputLabel value="Commission" style="padding-left:170px"  styleClass="label"/>
                        <h:outputText id="commText" value="#{Poddcharges.comm}" style="padding-left:0px" styleClass="output"/>
                        <h:outputLabel value="Goods and Service Tax" style="padding-right:100px" styleClass="label"/>
                        <h:outputText id="servText" value="#{Poddcharges.sercharge}" style="padding-right:150px" styleClass="output"/>
                    </h:panelGrid>
                    <h:panelGrid id="BodyPanel4"  style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="group1" >
                            <a4j:commandButton id="refreshbtn" value="Refresh"   action="#{Poddcharges.reset}" reRender="PanelGridMain" />
                            <a4j:commandButton id="exitBtn" value="Exit" ajaxSingle="true" action="#{Poddcharges.btn_exit}" />
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
