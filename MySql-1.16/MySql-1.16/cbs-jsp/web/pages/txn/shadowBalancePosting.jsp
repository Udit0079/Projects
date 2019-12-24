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
            <title>Shadow Balance Posting</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{ShadowBalancePosting.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Shadow Balance Posting"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ShadowBalancePosting.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="branchGrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputLabel id="lblBranch" styleClass="label" value="Branch"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddBranch" styleClass="ddlist" size="1" style="width:120px" value="#{ShadowBalancePosting.branch}">
                                <f:selectItems value="#{ShadowBalancePosting.branchList}"/>
                                <a4j:support action="#{ShadowBalancePosting.resetAllValue1}"  event="onblur" reRender="mainPanel" focus="ddClearingMode"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="label1" styleClass="label" value="Clearing Mode"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddClearingMode" styleClass="ddlist" size="1" style="width:120px" value="#{ShadowBalancePosting.emFlags}">
                                <f:selectItem itemValue="--SELECT--"/>
                                <f:selectItems value="#{ShadowBalancePosting.clearingModeOption}"/>
                                <a4j:support actionListener="#{ShadowBalancePosting.getRegisterDate}"  event="onblur" focus="ddRegisterDate"
                                             reRender="ddRegisterDate,stxtMsg" limitToList="true" />
                            </h:selectOneListbox>
                            <h:outputLabel id="label12" styleClass="label" value="Register Date" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddRegisterDate" styleClass="ddlist" size="1" style="width:120px" value="#{ShadowBalancePosting.registerDt}">
                                <f:selectItem itemValue="--SELECT--"/>
                                <f:selectItems value="#{ShadowBalancePosting.registerDateOption}" />
                                <a4j:support actionListener="#{ShadowBalancePosting.getPostingDate}"  event="onblur" focus="txtTotalAmount"
                                             reRender="stxtPostingDate,txtTotalInst,ddRegisterDate,txtTotalAmount,stxtMsg" limitToList="true" />
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid id="amountGrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%">
                            <h:outputLabel id="label13" styleClass="label" value="Posting Date"/>
                            <h:outputText id="stxtPostingDate" styleClass="output" value="#{ShadowBalancePosting.postingDt}"/>
                            <h:outputLabel id="labelTotalAmount" styleClass="label" value="Total Amount"/>
                            <h:inputText id="txtTotalAmount" styleClass="input" style="width:100px" value="#{ShadowBalancePosting.totalAmt}" disabled="true"/>
                            <h:outputLabel id="labelTotalInst" styleClass="label" value="Total Instruments"/>
                            <h:inputText id="txtTotalInst" styleClass="input"style="width:100px" value="#{ShadowBalancePosting.totalinstrument}"/>
                        </h:panelGrid>
                        <h:panelGrid id="messageGrid" columns="2" columnClasses="col8,col8" styleClass="row1" width="100%">
                            <h:outputText id="stxtMsg" styleClass="error" value="#{ShadowBalancePosting.message}" />
                        </h:panelGrid>
                        <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                            <h:panelGroup id="btnPanel">
                                <a4j:commandButton id="btnPost" value="Post" oncomplete="#{rich:component('postPanel')}.show()" >
                                </a4j:commandButton>
                                <a4j:commandButton id="btnRefresh" value="Refresh" action="#{ShadowBalancePosting.resetAllValue1}" reRender="ddRegisterDate,txtTotalInst,ddClearingMode,txtTotalAmount,stxtPostingDate,stxtMsg" focus="ddClearingMode">
                                </a4j:commandButton>
                                <a4j:commandButton id="btnExit" value="Exit" action="#{ShadowBalancePosting.exitFrm}" reRender="ddRegisterDate,txtTotalInst,ddClearingMode,txtTotalAmount,stxtPostingDate,stxtMsg">
                                </a4j:commandButton>
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
                <rich:modalPanel id="postPanel" autosized="true" width="200">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                    </f:facet>
                    <f:facet name="controls">
                        <h:panelGroup>
                            <h:graphicImage value="/images/modal/close.png" styleClass="hidelink" id="hidelinkap" />
                            <rich:componentControl for="postPanel" attachTo="hidelinkap" operation="hide" event="onclick" />
                        </h:panelGroup>
                    </f:facet>
                    <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure You Want To Post ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" ajaxSingle="true" action="#{ShadowBalancePosting.save}"
                                                       oncomplete="#{rich:component('postPanel')}.hide();" reRender="ddRegisterDate,ddClearingMode,stxtPostingDate,txtTotalAmount,txtTotalInst,stxtMsg"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" onclick="#{rich:component('postPanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
