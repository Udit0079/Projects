<%-- 
    Document   : IndividualCharge
    Created on : Nov 15, 2017, 11:16:52 AM
    Author     : user
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Individual Charge Posting</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>            
        </head>
        <body>
            <a4j:form>
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{IndividualCharge.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Individual Charge Posting"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{IndividualCharge.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="6" id="a8" width="100%" style="width:100%;text-align:center;" styleClass="row2">
                        <h:outputText id="message" styleClass="msg" value="#{IndividualCharge.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="inputPanel" columnClasses="col13,col13,col13,col13" columns="4" styleClass="row1" style="width:100%;text-align:left;">
                        <h:outputLabel id="lblAcNo" styleClass="label" value="Account No."/>
                        <h:panelGroup>
                            <h:inputText id="txtAcNo" styleClass="input" value="#{IndividualCharge.acNo}" size="15" maxlength="#{IndividualCharge.acNoMaxLen}">
                                <a4j:support action="#{IndividualCharge.getAccountDetail}" event="onblur" ajaxSingle="true" 
                                             reRender="message,stxtCustName,stxtAcBal,stxtAccNo,stxtChargeAmt,stxtCrHead,inputGstPanel"/>
                            </h:inputText>
                            <h:outputText id="stxtAccNo" styleClass="output" value="#{IndividualCharge.accNo}"/>
                        </h:panelGroup>                        
                        <h:outputLabel id="lblChgLst" styleClass="label" value="Charge Type : "><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddCharge" styleClass="ddlist" size="1" value="#{IndividualCharge.chgValue}">
                                <f:selectItems value="#{IndividualCharge.chgList}"/>
                                <a4j:support action="#{IndividualCharge.setGlAndAmount}" event="onblur" focus="cal"
                                             reRender="message,stxtChargeAmt,stxtCrHead"/>
                            </h:selectOneListbox>                            
                        </h:panelGrid>
                        <h:panelGrid id="inputCustPanel" columnClasses="col13,col13,col13,col13" columns="4" styleClass="row2" style="width:100%;text-align:left;">
                            <h:outputLabel id="lblCustName" styleClass="label" value="Customer Name"/>
                            <h:outputText id="stxtCustName" styleClass="output" value="#{IndividualCharge.custName}"/>
                            <h:outputLabel id="lblAcBal" styleClass="label" value="Balance"/>
                            <h:outputText id="stxtAcBal" styleClass="output" value="#{IndividualCharge.bal}"/>
                        </h:panelGrid>    
                        <h:panelGrid id="inputLabelPanel" columnClasses="col13,col13,col13,col13" columns="4" styleClass="row1" style="width:100%;text-align:left;">
                            <h:outputLabel id="lblAmount" styleClass="label" value="Charge Amount"/>
                            <h:inputText id="stxtChargeAmt" styleClass="input" value="#{IndividualCharge.chgAmt}"/>
                            <h:outputLabel id="lblCrHead" styleClass="label" value="Credit Head"/>
                            <h:inputText id="stxtCrHead" styleClass="input" value="#{IndividualCharge.crHead}"/>
                        </h:panelGrid>
                        <h:panelGrid id="inputGstPanel" columnClasses="col13,col13,col13,col13" columns="4" styleClass="row2" style="width:100%;text-align:left;display:#{IndividualCharge.show}">
                            <h:outputLabel id="lblGstLst" styleClass="label" value="GST Type : "><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddGst" styleClass="ddlist" size="1" value="#{IndividualCharge.gstType}">
                                <f:selectItems value="#{IndividualCharge.gstTypeList}"/>  
                            </h:selectOneListbox> 
                            <h:outputLabel/>
                            <h:outputLabel/>
                        </h:panelGrid>    
                        <rich:modalPanel id="savePanel" autosized="true" width="250" onshow="#{rich:element('btnSaveYes')}.focus();">
                            <f:facet name="header">
                                <h:outputText value="Confirmation Dialog"/>
                            </f:facet>
                            <h:form>
                            <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                <tbody>
                                    <tr style="height:40px">
                                        <td colspan="2">
                                            <h:outputText value="Are you sure to post charges ?"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="Yes" id="btnSaveYes" ajaxSingle="true" action="#{IndividualCharge.postAction}" oncomplete="#{rich:component('savePanel')}.hide();" reRender="stxtMessage,mainPanel"/>
                                        </td>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="No" id="btnSaveNo" onclick="#{rich:component('savePanel')}.hide();"/>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </h:form>
                    </rich:modalPanel>    
                    <h:panelGrid columns="2" columnClasses="col10,col11" style="text-align:center;width:100%" width="100%" styleClass="footer">
                        <h:panelGroup id="btnGroupPanel" >
                            <a4j:commandButton id="save" value="Post" oncomplete="#{rich:component('savePanel')}.show();" reRender="message,savePanel"/>
                            <a4j:commandButton id="refresh" value="Refresh" action="#{IndividualCharge.refreshForm}" reRender="message,txtAcNo,stxtAccNo,ddCharge,
                                               stxtCustName,stxtAcBal,stxtChargeAmt,stxtCrHead" focus="txtAcNo"/>
                            <a4j:commandButton id="poBtnCancel" value="Exit" action="#{IndividualCharge.exitForm}" reRender="message"/>
                        </h:panelGroup>
                    </h:panelGrid>    
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>