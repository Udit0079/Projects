<%-- 
    Document   : MutualFundCreation
    Created on : Jun 9, 2017, 3:33:11 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib  uri="http://richfaces.org/rich" prefix="rich"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Mutual Fund Creation Form</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                var row;
            </script>      
        </head>
        <body>            
            <a4j:form id="MutualFundCreate">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{MutualFundCreate.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Mutual Fund Creation Form"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{MutualFundCreate.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                        <h:panelGrid columns="2" id="Panel790" style="width:100%;height:30px;text-align:center;" styleClass="row1">
                            <h:outputText id="lblMsg" styleClass="error" value="#{MutualFundCreate.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="Row1" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblFuntion" styleClass="label" value="Function"/>
                            <h:selectOneListbox id="ddFunction" styleClass="ddlist" size="1" value="#{MutualFundCreate.function}" disabled="#{MutualFundCreate.disFunction}">
                                <f:selectItems value="#{MutualFundCreate.functionList}"/>
                                <a4j:support event="onblur" actionListener="#{MutualFundCreate.chenageOperation}" focus="#{MutualFundCreate.focusId}"
                                             reRender="lblPAcno,ddPAcNo,lblMsg,Row2,Row3,Row4,Row5,BtnPanel,ddFunction"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblPAcno" styleClass="label" value="Pending Sequence No." style="display:#{MutualFundCreate.inputDisFlag};"/>
                            <h:selectOneListbox id="ddPAcNo" size="1" styleClass="ddlist" value="#{MutualFundCreate.pendingSeqNo}" style="width:100px;display:#{MutualFundCreate.inputDisFlag};">
                                <f:selectItems value="#{MutualFundCreate.pendingSeqNoList}"/>
                                <a4j:support actionListener="#{MutualFundCreate.getUnAuthSeqNoDetail}" event="onblur" focus="btnSave" reRender="lblMsg,Row2,Row3,Row4,Row5,btnRegion"/>
                            </h:selectOneListbox>                        
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="Row2" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblMaxDays" styleClass="label" value="Max Days"></h:outputLabel>
                            <h:inputText id="txtMaxDays" styleClass="input" value="#{MutualFundCreate.maxDay}" disabled="#{MutualFundCreate.disMaxDays}">
                                <a4j:support event="onblur" focus="txtAmount"/>
                            </h:inputText>
                            <h:outputLabel id="lblAmount" styleClass="label" value="Amount"></h:outputLabel>
                            <h:inputText id="txtAmount" styleClass="input" value="#{MutualFundCreate.amt}" disabled="#{MutualFundCreate.disAmt}">
                                <a4j:support event="onblur" focus="txtCrAcNo"/>
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="Row3" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblCrAcNo" styleClass="label" value="Credit Account No."></h:outputLabel>
                            <h:inputText id="txtCrAcNo" styleClass="input" size="#{MutualFundCreate.acNoMaxLen}" maxlength="#{MutualFundCreate.acNoMaxLen}" value="#{MutualFundCreate.crAcNo}" disabled="#{MutualFundCreate.disCrAcNo}">
                                <a4j:support actionListener="#{MutualFundCreate.getNewCrAcNo}" event="onblur" focus="txtDrAcNo" reRender="lblMsg,stxtCrAcNewNo,stxtCrAcDesc"/>
                            </h:inputText>
                            <h:outputText id="stxtCrAcNewNo" styleClass="output" value="#{MutualFundCreate.crAcNewNo}"/>
                            <h:outputText id="stxtCrAcDesc" styleClass="output" value="#{MutualFundCreate.crAcDesc}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="Row4" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblDrAcNo" styleClass="label" value="Debit Account No."></h:outputLabel>
                            <h:inputText id="txtDrAcNo" styleClass="input" size="#{MutualFundCreate.acNoMaxLen}" maxlength="#{MutualFundCreate.acNoMaxLen}" value="#{MutualFundCreate.drAcNo}" disabled="#{MutualFundCreate.disDrAcNo}">
                                <a4j:support actionListener="#{MutualFundCreate.getNewDrAcNo}" event="onblur" focus="ddmark" reRender="lblMsg,stxtDrAcNewNo,stxtDrAcDesc"/>
                            </h:inputText>    
                            <h:outputText id="stxtDrAcNewNo" styleClass="output" value="#{MutualFundCreate.drAcNewNo}"/>
                            <h:outputText id="stxtDrAcDesc" styleClass="output" value="#{MutualFundCreate.drAcDesc}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="Row5" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblMark" styleClass="label" value="Marking"/>
                            <h:selectOneListbox id="ddmark" size="1" styleClass="ddlist" value="#{MutualFundCreate.markingDd}" disabled="#{MutualFundCreate.markFlag}">
                                <f:selectItems value="#{MutualFundCreate.markList}"/>
                                <a4j:support event="onblur" focus="txtRemark" reRender="lblMsg,btnRegion"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblRemark" styleClass="label" value="Details"></h:outputLabel>
                            <h:inputText id="txtRemark" styleClass="input" value="#{MutualFundCreate.remark}" disabled="#{MutualFundCreate.disRemark}">
                                <a4j:support event="onblur" focus="btnSave" reRender="lblMsg,btnRegion"/>
                            </h:inputText>    
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <a4j:region id="btnRegion">
                            <h:panelGroup id="BtnPanel">
                                <a4j:commandButton id="btnSave" value="#{MutualFundCreate.btnValue}" oncomplete="#{rich:component('saveAuthPanel')}.show()" disabled="#{MutualFundCreate.saveDisable}"/>
                                <a4j:commandButton id="btnDelete" value="Delete" oncomplete="#{rich:component('deletePanel')}.show()" disabled="#{MutualFundCreate.deleteDisable}"/>
                                <a4j:commandButton id="btnrefresh" value="Refresh" reRender="leftPanel,btnRegion,ddmark" action="#{MutualFundCreate.refreshButtonAction}"/>
                                <a4j:commandButton id="btnExit" value="Exit" action="#{MutualFundCreate.btnExit}" reRender="leftPanel,lblMsg,btnSave,btnDelete"/>
                            </h:panelGroup>
                        </a4j:region>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="saveAuthPanel" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog"/>
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="savehidelink" />
                        <rich:componentControl for="saveAuthPanel" attachTo="savehidelink" operation="hide" event="onclick" />
                    </h:panelGroup>
                </f:facet>
                <a4j:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure To #{MutualFundCreate.btnValue}"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" action="#{MutualFundCreate.saveAction}" onclick="#{rich:component('saveAuthPanel')}.hide();" reRender="lblMsg,leftPanel,btnRegion"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('saveAuthPanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </a4j:form>
            </rich:modalPanel>
            <rich:modalPanel id="deletePanel" autosized="true" width="250" onshow="#{rich:element('btnDelNo')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog"/>
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="delhidelink" />
                        <rich:componentControl for="deletePanel" attachTo="delhidelink" operation="hide" event="onclick" />
                    </h:panelGroup>
                </f:facet>
                <a4j:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure To Delete "/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" action="#{MutualFundCreate.deleteAction}" onclick="#{rich:component('deletePanel')}.hide();" reRender="lblMsg,leftPanel,btnRegion"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnDelNo" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </a4j:form>
            </rich:modalPanel>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>