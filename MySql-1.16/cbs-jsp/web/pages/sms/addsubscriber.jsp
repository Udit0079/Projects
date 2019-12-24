<%-- 
    Document   : addsubscriber
    Created on : Mar 15, 2012, 3:34:20 PM
    Author     : ANKIT VERMA
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <link href="mycss.css" rel="stylesheet" type="text/css"/>
            <title>Add New Subscriber</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{addSubscriber.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="New Subscriber"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{addSubscriber.userName}"/>
                        </h:panelGroup>                         
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="messagePanel" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                        <h:outputText id="lblMsg" styleClass="error" value="#{addSubscriber.msg}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="6" columnClasses="col91,col91,col91,col9,col91,col91" id="gridpanel1"   style="height:30px;text-align:left" styleClass="row2" width="100%" >
                        <h:outputLabel id="lblFunction" styleClass="headerLabel" value="Function"/>
                        <h:selectOneListbox value="#{addSubscriber.function}" style="ddstyle" size="1">
                            <f:selectItem itemValue="ADD"/>
                            <f:selectItem itemValue="EDIT"/>
                            <%--<f:selectItem itemValue="DELETE"/>--%>
                            <f:selectItem itemValue="AUTHORIZE"/>
                            <f:selectItem itemValue="ACTIVATE"/>
                            <f:selectItem itemValue="DE-ACTIVATE"/>
                            <a4j:support event="onchange" action="#{addSubscriber.processFunctionAction}" reRender="mainPanel"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblAccountNumber" styleClass="headerLabel"  value="Account Number" />
                        <h:panelGroup>
                            <h:inputText id="txtAccountNumber" styleClass="input" value="#{addSubscriber.acno}" 
                                         style="width:100px" onkeyup="this.value=this.value.toUpperCase();" maxlength="#{addSubscriber.acNoMaxLen}" size="15">
                                <a4j:support event="onblur" action="#{addSubscriber.findNameByAccountNumber}" reRender="mainPanel"/>
                            </h:inputText>
                            <a4j:outputPanel id="a4jOutputPanelAcno" ajaxRendered="true" >
                                <h:outputText id="stxtacno" styleClass="label"  value="#{addSubscriber.name}" style="color:green;" rendered="true"/><h:outputText styleClass="label" value="-"/><h:outputText id="stxtNewAcno" styleClass="label"  value="#{addSubscriber.newAcno}" style="color:black;" rendered="true"/>
                            </a4j:outputPanel>
                        </h:panelGroup>
                        <h:outputLabel id="lblAcnoType" styleClass="headerLabel" value="Account Type"/>
                        <h:selectOneListbox value="#{addSubscriber.acnoType}" style="ddstyle" size="1">
                            <f:selectItem itemValue="OTHER"/>
                            <f:selectItem itemValue="STAFF"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <%--<h:panelGrid columns="1" columnClasses="col3,col3,col3,col3" id="gridpanelWarn"   style="height:30px;text-align:left;" styleClass="row1" width="100%" >
                        <h:outputLabel value="Mobile number must be start with +91" styleClass="error" style="color:blue"/>
                    </h:panelGrid>--%>
                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col3" id="gridpanel2"   style="height:30px;text-align:left;" styleClass="row1" width="100%" >
                        <h:outputLabel id="lblMobile" styleClass="headerLabel"  value="Mobile Number"  />
                        <h:inputText id="txtMobile"    styleClass="input"  maxlength="10" value="#{addSubscriber.mobileNo}" style="width:100px" />
                        <h:outputLabel id="lblBillingAc" styleClass="headerLabel"  value="Billing A/c Number"/>                   
                        <h:inputText id="txtBillingAc" styleClass="input"  value="#{addSubscriber.billingAcno}" style="width:100px" onkeyup="this.value=this.value.toUpperCase();" maxlength="#{addSubscriber.acNoMaxLen}" size="15">
                            <a4j:support  event="onblur" action="#{addSubscriber.findBillingAcNameByAccountNumber}" reRender="a4jOutputPanel,btnSave,lblMsg" focus="booleanCheckBox"/>
                        </h:inputText>
                        <a4j:outputPanel id="a4jOutputPanel" ajaxRendered="true">
                            <h:outputText id="stxtBiilingacno" styleClass="label"  value="#{addSubscriber.billingAcCustName}" style="color:green;" /><h:outputText styleClass="label" value="-"/><h:outputText id="stxtNewBillingAcno" styleClass="label"  value="#{addSubscriber.newBillingAcno}" style="color:black;" rendered="true"/>
                        </a4j:outputPanel>   
                        <h:outputText />
                    </h:panelGrid>  
                    <h:panelGrid columns="4" columnClasses="col3,col3,col3,col3" id="gridpanelFacilities"   style="height:30px;text-align:left" styleClass="row2" width="100%" >    
                        <h:outputLabel value="Check Facilities Availed By The Customer" styleClass="headerLabel" style="font-weight: bolder;" />
                    </h:panelGrid>
                    <h:panelGrid columns="4" columnClasses="col3,col3,col3,col3" id="gridpanelStatement"   style="height:30px;text-align:left" styleClass="row1" width="100%" >  
                        <h:outputLabel value="Statement Services" styleClass="headerLabel" style="font-weight: bolder;"/>
                    </h:panelGrid>
                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col3" id="gridpane"   style="height:30px;text-align:left" styleClass="row2" width="100%" >                            
                        <h:panelGroup>
                            <h:selectBooleanCheckbox id="booleanCheckBox"  value="#{addSubscriber.balanceInAccountChkBx}"  styleClass="input">
                                <a4j:support event="onchange"/>
                            </h:selectBooleanCheckbox>                                                                                
                            <h:outputLabel id="lblCheckBox1" styleClass="label"  value="Balance In Account"/>
                        </h:panelGroup>
                        <h:panelGroup>
                            <h:selectBooleanCheckbox id="booleanCheckBox4"    styleClass="input" value="#{addSubscriber.miniAccountStatementChkBx}">
                                <a4j:support event="onchange"/>
                            </h:selectBooleanCheckbox>
                            <h:outputLabel id="lblCheckBox5" styleClass="label" value="Mini A/C Statement"/>
                        </h:panelGroup>
                        <h:panelGroup style="display:#{addSubscriber.missedCallActive}">
                            <h:selectBooleanCheckbox id="missedCallChkBox" styleClass="input" value="#{addSubscriber.missedCallAlert}">
                                <a4j:support event="onchange"/>
                            </h:selectBooleanCheckbox>
                            <h:outputLabel id="lblmissedCall" styleClass="label" value="Missed Call Balance Alert"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <%--<h:panelGrid columns="4" columnClasses="col3,col3,col3,col3" id="gridpane3"   style="height:30px;text-align:left" styleClass="row2" width="100%" >
                        <h:panelGroup>
                            <h:selectBooleanCheckbox id="booleanCheckBox2"  styleClass="input" value="#{addSubscriber.termDepositeMaturityChkBx}"/>
                            <h:outputLabel id="lblCheckBox3" styleClass="label" value="Term Deposite Maturity" />
                        </h:panelGroup>  
                        <h:panelGroup>
                            <h:selectBooleanCheckbox id="booleanCheckBox6" value="#{addSubscriber.dailyAccountStatementChkBx}"  styleClass="input" />
                            <h:outputLabel id="lblCheckBox7" styleClass="label"   value="Daily A/C Statement"/> 
                        </h:panelGroup>   
</h:panelGrid>--%>
                    <h:panelGrid columns="4" columnClasses="col3,col3,col3,col3" id="gridpanelTransaction1"   style="height:30px;text-align:left" styleClass="row1" width="100%" >    
                        <h:outputLabel value="Transaction Services" styleClass="headerLabel" style="font-weight: bolder;" />
                    </h:panelGrid>
                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col3" id="gridpaneltransaction"   style="height:30px;text-align:left" styleClass="row2" width="100%" >                                                     
                        <h:panelGroup>
                            <h:selectBooleanCheckbox id ="booleanCheckBox1"  styleClass="input" value="#{addSubscriber.cashCrChkBx}" >
                                <a4j:support event="onchange" reRender="txtCashCr" action="#{addSubscriber.uncheckedOperation}" focus="txtCashCr"/>
                            </h:selectBooleanCheckbox>
                            <h:outputLabel id="lblCheckBox2" styleClass="label" value="Cash cr"/>   
                        </h:panelGroup>
                        <h:inputText id="txtCashCr"   styleClass="input"  maxlength="8" value="#{addSubscriber.cashCrLimit}" style="width:80px" disabled="#{!addSubscriber.cashCrChkBx}">
                            <a4j:support event="onblur" ajaxSingle="true" action="#{addSubscriber.checkFloatValues}" reRender="txtCashCr" focus="booleanCheckBox3"/>
                        </h:inputText>  
                        <h:panelGroup>
                            <h:selectBooleanCheckbox id="booleanCheckBox3" value="#{addSubscriber.cashDrChkBx}"  styleClass="input">
                                <a4j:support event="onchange" reRender="txtCashDr" action="#{addSubscriber.uncheckedOperation}" focus="txtCashDr"/>
                            </h:selectBooleanCheckbox>
                            <h:outputLabel id="lblCheckBox4" styleClass="label" value="Cash Dr"/>
                        </h:panelGroup>
                        <h:inputText id="txtCashDr"    styleClass="input"  maxlength="8" value="#{addSubscriber.cashDrLimit}" style="width:80px" disabled="#{!addSubscriber.cashDrChkBx}">
                            <a4j:support event="onblur" ajaxSingle="true" action="#{addSubscriber.checkFloatValues}" reRender="txtCashDr" focus="booleanCheckBox5"/>
                        </h:inputText>  
                        <h:panelGroup>
                            <h:selectBooleanCheckbox id="booleanCheckBox5" value="#{addSubscriber.trfCrChkBx}"  styleClass="input">
                                <a4j:support event="onchange" reRender="txtTrfCr" action="#{addSubscriber.uncheckedOperation}" focus="txtTrfCr"/>
                            </h:selectBooleanCheckbox>   
                            <h:outputLabel id="lblCheckBox6" styleClass="label" value="Trf. Cr"/>
                        </h:panelGroup>
                        <h:inputText id="txtTrfCr"    styleClass="input"  maxlength="8" value="#{addSubscriber.trfCrLimit}"  style="width:80px" disabled="#{!addSubscriber.trfCrChkBx}">
                            <a4j:support event="onblur" ajaxSingle="true" action="#{addSubscriber.checkFloatValues}" reRender="txtTrfCr" focus="booleanCheckBox7"/>
                        </h:inputText>  
                    </h:panelGrid>
                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col3" id="gridpane4"   style="height:30px;text-align:left" styleClass="row1" width="100%" >                       
                        <h:panelGroup>   
                            <h:selectBooleanCheckbox id="booleanCheckBox7"  styleClass="input" value="#{addSubscriber.trfDrChkBx}">
                                <a4j:support event="onchange" reRender="txtTrfDr" action="#{addSubscriber.uncheckedOperation}" focus="txtTrfDr"/>
                            </h:selectBooleanCheckbox>    
                            <h:outputLabel id="lblCheckBox8" value="Trf. Dr" styleClass="label"/> 
                        </h:panelGroup>   
                        <h:inputText id="txtTrfDr"    styleClass="input"  maxlength="8" value="#{addSubscriber.trfDrLimit}" style="width:80px" disabled="#{!addSubscriber.trfDrChkBx}">
                            <a4j:support event="onblur" ajaxSingle="true" action="#{addSubscriber.checkFloatValues}" reRender="txtTrfDr" focus="booleanCheckBox8"/>
                        </h:inputText>  
                        <h:panelGroup>   
                            <h:selectBooleanCheckbox id="booleanCheckBox8" value="#{addSubscriber.clgDrChkBx}"  styleClass="input">
                                <a4j:support event="onchange" reRender="txtClgDr" action="#{addSubscriber.uncheckedOperation}" focus="txtClgDr"/>
                            </h:selectBooleanCheckbox>
                            <h:outputLabel id="lblCheckBox9" styleClass="label" value="Clg Dr"/> 
                        </h:panelGroup>   
                        <h:inputText id="txtClgDr"    styleClass="input"  maxlength="8" value="#{addSubscriber.clgDrLimit}" style="width:80px" disabled="#{!addSubscriber.clgDrChkBx}">
                            <a4j:support event="onblur" ajaxSingle="true" action="#{addSubscriber.checkFloatValues}" reRender="txtClgDr" focus="booleanCheckBox9"/>
                        </h:inputText>  
                        <h:panelGroup> 
                            <h:selectBooleanCheckbox id="booleanCheckBox9" value="#{addSubscriber.clgCrChkBx}"  styleClass="input">
                                <a4j:support event="onchange" reRender="txtClgCr" action="#{addSubscriber.uncheckedOperation}" focus="txtClgCr"/>
                            </h:selectBooleanCheckbox>
                            <h:outputLabel id="lblCheckBox10" styleClass="label" value="Clg Cr"/> 
                        </h:panelGroup>
                        <h:inputText id="txtClgCr" styleClass="input"  maxlength="8" value="#{addSubscriber.clgCrLimit}" style="width:80px" disabled="#{!addSubscriber.clgCrChkBx}" >
                            <a4j:support event="onblur" ajaxSingle="true" action="#{addSubscriber.checkFloatValues}" reRender="txtClgCr" focus="booleanCheckBoxInt"/>
                        </h:inputText>   
                    </h:panelGrid>
                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col3" id="gridpane44" style="height:30px;text-align:left" styleClass="row2" width="100%" >
                        <h:panelGroup>
                            <h:selectBooleanCheckbox id ="booleanCheckBoxInt"  styleClass="input" value="#{addSubscriber.interestChkBx}" >
                                <a4j:support event="onchange" reRender="txtInterestLimit" action="#{addSubscriber.uncheckedOperation}" focus="txtInterestLimit"/>
                            </h:selectBooleanCheckbox>
                            <h:outputLabel id="lblCheckBoxInterest" styleClass="label" value="Interest"/>   
                        </h:panelGroup>
                        <h:inputText id="txtInterestLimit"   styleClass="input"  maxlength="8" value="#{addSubscriber.interestLimit}" style="width:80px" disabled="#{!addSubscriber.interestChkBx}">
                            <a4j:support event="onblur" ajaxSingle="true" action="#{addSubscriber.checkFloatValues}" reRender="txtInterestLimit" focus="booleanCheckBoxCharge"/>
                        </h:inputText>
                        <h:panelGroup>
                            <h:selectBooleanCheckbox id ="booleanCheckBoxCharge"  styleClass="input" value="#{addSubscriber.chargeChkBx}" >
                                <a4j:support event="onchange" reRender="txtChargeLimit" action="#{addSubscriber.uncheckedOperation}" focus="txtChargeLimit"/>
                            </h:selectBooleanCheckbox>
                            <h:outputLabel id="lblCheckBoxCharge" styleClass="label" value="Charge"/>   
                        </h:panelGroup>
                        <h:inputText id="txtChargeLimit"   styleClass="input"  maxlength="8" value="#{addSubscriber.chargeLimit}" style="width:80px" disabled="#{!addSubscriber.chargeChkBx}">
                            <a4j:support event="onblur" ajaxSingle="true" action="#{addSubscriber.checkFloatValues}" reRender="txtChargeLimit"/>
                        </h:inputText>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="BtnPanel">
                            <a4j:commandButton id="btnSave" value="#{addSubscriber.btnActionValue}" 
                                               action="#{addSubscriber.processCombinedMessage}" 
                                               oncomplete="#{rich:component('combinedPanel')}.show();" reRender="lblMsg,combinedPanel"  disabled="#{addSubscriber.disableBtnSave}" 
                                               />
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{addSubscriber.refreshPage}" 
                                               reRender="mainPanel"/>
                            <a4j:commandButton id="poBtnCancel" value="Exit"  action="#{addSubscriber.exitForm}" 
                                               reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                        <rich:dataTable value="#{addSubscriber.gridDetail}" var="dataItem" rowClasses="gridrow1,gridrow2" 
                                        id="taskList" rows="5" columnsWidth="100" onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                        onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="13"><h:outputText value="Subscriber Detail"/></rich:column>
                                    <rich:column breakBefore="true"><h:outputText value="Accunt No"/></rich:column>
                                    <rich:column><h:outputText value="Mobile" /></rich:column>
                                    <rich:column><h:outputText value="Customer Type" /></rich:column>
                                    <rich:column><h:outputText value="Cash Cr Limit" /></rich:column>
                                    <rich:column><h:outputText value="Cash Dr Limit" /></rich:column>
                                    <rich:column><h:outputText value="Trf. Cr Limit" /></rich:column>
                                    <rich:column><h:outputText value="Trf Dr Limit" /></rich:column>
                                    <rich:column><h:outputText value="Clg. Cr Limit" /></rich:column>
                                    <rich:column><h:outputText value="Clg. Dr Limit" /></rich:column>
                                    <rich:column><h:outputText value="Status" /></rich:column>
                                    <rich:column><h:outputText value="Enter By" /></rich:column>
                                    <rich:column><h:outputText value="Updated By" /></rich:column>
                                    <rich:column id="opLabel"><h:outputText value="#{addSubscriber.gridOperation}"/></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{dataItem.acno}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.mobileNo}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.acnoType}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.cashCrLimit}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.cashDrLimit}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.trfCrLimit}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.trfDrLimit}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.clgCrLimit}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.clgDrLimit}" /></rich:column>
                            <rich:column><h:outputText value="#{(dataItem.authStatus == 'D')?'DE-ACTIVATE' : 'ACTIVATE'}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.enterBy}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.updateBy}" /></rich:column>
                            <rich:column id="delColumn" style="text-align:center;width:40px" rendered="#{addSubscriber.delFlag}">
                                <a4j:commandLink id="deletelink" ajaxSingle="true" oncomplete="#{rich:component('deletePanel')}.show();">
                                    <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                    <f:setPropertyActionListener value="#{dataItem}" target="#{addSubscriber.currentSubscriber}"/>
                                </a4j:commandLink>
                            </rich:column>
                            <rich:column id="selectColumn" style="text-align:center;width:40px" rendered="#{addSubscriber.authFlag}">
                                <a4j:commandLink id="selectlink" ajaxSingle="true" oncomplete="#{rich:component('selectPanel')}.show();">
                                    <h:graphicImage value="/resources/images/passed.gif" style="border:0" />
                                    <f:setPropertyActionListener value="#{dataItem}" target="#{addSubscriber.currentSubscriber}"/>
                                </a4j:commandLink>
                            </rich:column>
                        </rich:dataTable>
                        <rich:datascroller align="left" for="taskList" maxPages="10"/>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="combinedPanel" autosized="true" width="250" onshow="#{rich:element('btnCombinedNo')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="#{addSubscriber.combinedConfirmation}"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnCombinedYes" action="#{addSubscriber.processCombinedFunction}" 
                                                       oncomplete="#{rich:component('combinedPanel')}.hide();" reRender="mainPanel"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnCombinedNo" onclick="#{rich:component('combinedPanel')}.hide();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="deletePanel" autosized="true" width="250" onshow="#{rich:element('btnDeleteNo')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are you sure to delete this ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnDeleteYes" action="#{addSubscriber.processAction}" 
                                                       oncomplete="#{rich:component('deletePanel')}.hide();" 
                                                       reRender="lblMsg,tablePanel,taskList"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnDeleteNo" onclick="#{rich:component('deletePanel')}.hide();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="selectPanel" autosized="true" width="250" onshow="#{rich:element('btnSelectNo')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are you sure to authorize ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnSelectYes" action="#{addSubscriber.processAction}" 
                                                       oncomplete="#{rich:component('selectPanel')}.hide();" 
                                                       reRender="lblMsg,tablePanel,taskList"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnSelectNo" onclick="#{rich:component('selectPanel')}.hide();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
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

