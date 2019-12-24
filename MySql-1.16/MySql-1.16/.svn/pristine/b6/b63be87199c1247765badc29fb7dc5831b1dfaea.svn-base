<%-- 
    Document   : IMPSMMIDGeneration
    Author     : root
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
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>IMPS MMID Generation</title>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="mainPanel" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{IMPSMMIDGeneration.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="IMPS Request"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{IMPSMMIDGeneration.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{IMPSMMIDGeneration.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="genpanel" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%">
                        <h:outputLabel id="Func" value="Function" styleClass="label"><font class="required" style="color:red;">*</font></h:outputLabel> 
                            <h:selectOneListbox id="ddfunction" value="#{IMPSMMIDGeneration.function}" styleClass="ddlist" style="width:130px" size="1"  >
                                <f:selectItems id="selectfuction" value="#{IMPSMMIDGeneration.functionList}"/>
                                <a4j:support event="onblur" action="#{IMPSMMIDGeneration.optionsMode}" 
                                             reRender="errorMsg,aaccontno,ddmode,btngen1,gridPanelTable,a119,a1184,a19,a1194,a11494,accountno,stxtNewAcNo,gridPanelP2ATable,gridPanelP2PTable,gridPanelTable" />
                            </h:selectOneListbox>
                            <h:outputLabel id="modeid" value="Mode" styleClass="label"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <a4j:region id="idRegion2">
                                <h:selectOneListbox id="ddmode" value="#{IMPSMMIDGeneration.mode}" styleClass="ddlist" style="width:100px" size="1"  >
                                    <f:selectItems id="selectmode" value="#{IMPSMMIDGeneration.modeList}"/>
                                    <a4j:support event="onblur" action="#{IMPSMMIDGeneration.optionsdisplay}" oncomplete="if(#{rich:element('ddmode')}.value=='A'){#{rich:element('accountno')}.focus();}"
                                                 reRender="accountno,gridPanelTable,txnList,errorMsg,ConformationPanel,btngen1,btnExit,
                                                 a1194,a19,a1184,a119,a11494,a1194,stxtNewAcNo,gridPanelP2ATable,gridPanelP2PTable,p2ATxnList,p2PTxnList"/>
                                </h:selectOneListbox>
                            </a4j:region>
                            <h:outputLabel id="accno" value="Account No" styleClass="label"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:panelGroup id="panelGrpAccountNo" layout="block">
                                <a4j:region id="idRegion">
                                    <h:inputText id="accountno"  styleClass="input" value="#{IMPSMMIDGeneration.accountNo}" style="width:80px;" maxlength="#{IMPSMMIDGeneration.acNoMaxLen}" disabled="#{IMPSMMIDGeneration.disableaccountno}" >
                                        <a4j:support event="onblur" action="#{IMPSMMIDGeneration.details}" oncomplete="if(#{rich:element('ddfunction')}.value=='FP2A'){#{rich:element('BFnamevalue')}.focus();}"
                                                     reRender="a19,a119,a1184,a1194,a11494,taskList,errorMsg,gridPanel7,custnamevlue,
                                                     custmobilevlue,custadharvlue,custpanvlue,custidvlue,btnRefresh,btnExit,stxtNewAcNo,gridPanelTable"/>                     
                                    </h:inputText>
                                </a4j:region>
                                <h:outputText id="stxtNewAcNo" styleClass="output" value="#{IMPSMMIDGeneration.newAcNo}"/>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid  id="a119"  columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="display:#{IMPSMMIDGeneration.panelDisplay4}"  styleClass="row1" width="100%">
                            <h:outputLabel id="custname" value="Name" styleClass="label" />
                            <h:outputText id="custnamevlue" value="#{IMPSMMIDGeneration.name}" styleClass="output"/>
                            <h:outputLabel id="custno" value="Mobile No" styleClass="label" />
                            <h:inputText  id="custmobilevlue" value="#{IMPSMMIDGeneration.mobileno}" styleClass="input" maxlength="10" size="12"/>
                            <h:outputLabel></h:outputLabel>
                            <h:outputLabel></h:outputLabel>
                        </h:panelGrid>
                        <h:panelGrid  id="a19"  columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="display:#{IMPSMMIDGeneration.panelDisplay}"  styleClass="row2" width="100%">
                            <h:outputLabel id="custid" value="Customerid" styleClass="label" />
                            <h:outputText id="custidvlue" value="#{IMPSMMIDGeneration.customerid}" styleClass="output"/>
                            <h:outputLabel id="custpan" value="PanNo." styleClass="label" />
                            <h:outputText  id="custpanvlue" value="#{IMPSMMIDGeneration.panno}" styleClass="output"/>
                            <h:outputLabel id="custadhr" value="AdharNo." styleClass="label" />
                            <h:outputText id="custadharvlue" value="#{IMPSMMIDGeneration.adharno}" styleClass="output"/>
                        </h:panelGrid>
                        <h:panelGrid  id="a1194"  columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="width:100%;display:#{IMPSMMIDGeneration.panelDisplay1}"  styleClass="row2" width="100%">
                            <h:outputLabel id="BFName" value="Beneficiary Name" styleClass="label"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="BFnamevalue"  value="#{IMPSMMIDGeneration.request_beneficaryName}" styleClass="input" maxlength="100" onkeyup="this.value = this.value.toUpperCase();" size="33">
                                <a4j:support event="onblur" action="#{IMPSMMIDGeneration.validateBeneficiaryname}" reRender="errorMsg"/>
                            </h:inputText>
                            <h:outputLabel id="bfacc" value="Beneficiary A/c" styleClass="label"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="BFaccountvalue"  value="#{IMPSMMIDGeneration.beneficaryAccountno}" styleClass="input" maxlength="35" onkeyup="this.value = this.value.toUpperCase();" size="30">
                                <a4j:support event= "onblur" action="#{IMPSMMIDGeneration.validateBeneficaryAccountno}" reRender="errorMsg"/>
                            </h:inputText>
                            <h:outputLabel id="bfIfsc" value="Beneficiary IFSC" styleClass="label"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="BFifscvalue"  value="#{IMPSMMIDGeneration.beneficaryIFSC}" style="width:80px"  styleClass="input" maxlength="11" onkeyup="this.value = this.value.toUpperCase();">
                                <a4j:support event="onblur" action="#{IMPSMMIDGeneration.validateIfscCode}" reRender="errorMsg"/>
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid  id="a1184"  columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="width:100%;display:#{IMPSMMIDGeneration.panelDisplay6}"  styleClass="row2" width="100%">
                            <h:outputLabel id="BFMobile" value="Beneficiary MobileNo." styleClass="label"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="BFmobile"  value="#{IMPSMMIDGeneration.beneficaryMobileno}" styleClass="input" maxlength="10" onkeyup="this.value = this.value.toUpperCase();" size="10">
                                <a4j:support event="onblur" action="#{IMPSMMIDGeneration.validateBeneficaryMobileno}" reRender="errorMsg"/>
                            </h:inputText>
                            <h:outputLabel id="bfmmid" value="Beneficiary MMID" styleClass="label"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="BFmmidvalue"  value="#{IMPSMMIDGeneration.beneficaryMmid}" styleClass="input" maxlength="7" onkeyup="this.value = this.value.toUpperCase();" size="10">
                                <a4j:support event= "onblur" action="#{IMPSMMIDGeneration.validatebeneficaryMMID}" reRender="errorMsg"/>
                            </h:inputText>
                            <h:outputLabel></h:outputLabel>
                            <h:outputLabel></h:outputLabel>
                        </h:panelGrid>
                        <h:panelGrid  id="a11494"  columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="display:#{IMPSMMIDGeneration.panelDisplay3}"  styleClass="row1" width="100%">
                            <h:outputLabel id="lblChargeType" value="Charge Type" styleClass="label"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:panelGroup>
                                <h:selectOneListbox id="ddChargeType" styleClass="ddlist" size="1" style="width:120px" value="#{IMPSMMIDGeneration.chargeType}" disabled="#{IMPSMMIDGeneration.fieldDisableFlag}">
                                    <f:selectItems value="#{IMPSMMIDGeneration.chargeTypeList}"/>  
                                    <a4j:support event="onblur" oncomplete="setMask();" reRender="errorMsg" />
                                </h:selectOneListbox> 
                                <h:outputText id="stxtChargeAmount" styleClass="output" value="#{IMPSMMIDGeneration.chargeAmount}" style="output"/>
                            </h:panelGroup>
                            <h:outputLabel id="tranamount" value="Txn. Amount" styleClass="label"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="Tranamountvalue"  value="#{IMPSMMIDGeneration.tranAmount}" style="width:80px" styleClass="input" maxlength="14">
                                <f:convertNumber type="currency" pattern="#.00" maxFractionDigits="2"/>
                                <a4j:support event="onblur" actionListener="#{IMPSMMIDGeneration.validateTranAmount}" reRender="errorMsg,Tranamountvalue,stxtChargeAmount"/>
                            </h:inputText>
                            <h:outputLabel id="remarkid" value="Remarks" styleClass="label"/>
                            <h:inputText id="remarkvalue"  value="#{IMPSMMIDGeneration.remark}" style="width:80px" styleClass="input" maxlength="100" onkeyup="this.value = this.value.toUpperCase();">
                                <a4j:support event="onblur" actionListener="#{IMPSMMIDGeneration.validateRemark}" reRender="errorMsg"/>
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid id="gridPanelTable" style="width:100%;height:0px;display:#{IMPSMMIDGeneration.panelDisplay2}" styleClass="row1"  columnClasses="vtop">
                            <a4j:region>
                                <rich:dataTable  value="#{IMPSMMIDGeneration.gridDetail}" var="tempItem"
                                                 rowClasses="gridrow1, gridrow2" id="txnList" rows="10" columnsWidth="100" rowKeyVar="row"
                                                 onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                 onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                                 width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="9"><h:outputText value="Transaction Detail"/></rich:column>
                                            <rich:column breakBefore="true"style="text-align:center;width:100px"><h:outputText value="Remitter Name" /></rich:column>
                                            <rich:column style="text-align:center;width:100px"><h:outputText value="Remitter AccNo" /></rich:column>
                                            <rich:column style="text-align:center;width:100px"><h:outputText value="Remitter MobNo" /></rich:column>
                                            <rich:column style="text-align:center;width:100px"><h:outputText value="Select" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column style="text-align:center;width:100px"><h:outputText value="#{tempItem.name}" /></rich:column>
                                    <rich:column style="text-align:center;width:70px"><h:outputText value="#{tempItem.accountNo}" /></rich:column>
                                    <rich:column style="text-align:center;width:70px"><h:outputText value="#{tempItem.mobileno}" /></rich:column>                               

                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink id="selectlink" oncomplete="#{rich:component('ConformationPanel')}.show()" reRender="ConformationPanel,errorPanel,errorMsg,fundTrfFieldRefresh">
                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                        <f:setPropertyActionListener value="#{tempItem}" target="#{IMPSMMIDGeneration.tempCurrentItem}"/>  
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller id="unAuthTempDataScroll" align="center" for="txnList" maxPages="18" />
                        </a4j:region> 
                    </h:panelGrid>
                    <h:panelGrid id="gridPanelP2ATable" style="width:100%;height:0px;display:#{IMPSMMIDGeneration.panelDisplay5}" styleClass="row1"  columnClasses="vtop">
                        <a4j:region>
                            <rich:dataTable  value="#{IMPSMMIDGeneration.p2AGridDetail}" var="tempItem"
                                             rowClasses="gridrow1, gridrow2" id="p2ATxnList" rows="10" columnsWidth="100" rowKeyVar="row"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                             width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="9"><h:outputText value="Transaction Detail"/></rich:column>
                                        <rich:column breakBefore="true"style="text-align:center;width:100px"><h:outputText value="Remitter Name" /></rich:column>
                                        <rich:column style="text-align:center;width:100px"><h:outputText value="Remitter A/c" /></rich:column>
                                        <rich:column style="text-align:center;width:100px"><h:outputText value="Remitter Mobile" /></rich:column>
                                        <rich:column style="text-align:center;width:100px"><h:outputText value="Beneficiary A/c" /></rich:column>
                                        <rich:column style="text-align:center;width:100px"><h:outputText value="Beneficiary IFSC" /></rich:column>
                                        <rich:column style="text-align:center;width:100px"><h:outputText value="Txn. Amount" /></rich:column>
                                        <rich:column style="text-align:center;width:100px"><h:outputText value="Select" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column style="text-align:center;width:100px"><h:outputText value="#{tempItem.name}" /></rich:column>
                                <rich:column style="text-align:center;width:70px"><h:outputText value="#{tempItem.accountNo}" /></rich:column>
                                <rich:column style="text-align:center;width:70px"><h:outputText value="#{tempItem.mobileno}" /></rich:column>                               
                                <rich:column style="text-align:center;width:70px"><h:outputText value="#{tempItem.beneAccNo}" /></rich:column>
                                <rich:column style="text-align:center;width:70px"><h:outputText value="#{tempItem.beneIFSC}" /></rich:column>
                                <rich:column style="text-align:center;width:70px"><h:outputText value="#{tempItem.tranAmount}" /></rich:column>

                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink id="selectlink" oncomplete="#{rich:component('ConformationPanel')}.show()" reRender="ConformationPanel,errorPanel,errorMsg">
                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                        <f:setPropertyActionListener value="#{tempItem}" target="#{IMPSMMIDGeneration.p2ATempCurrentItem}"/>  
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller id="p2AUnAuthTempDataScroll" align="center" for="p2ATxnList" maxPages="18" />
                        </a4j:region> 
                    </h:panelGrid>    
                    <h:panelGrid id="gridPanelP2PTable" style="width:100%;height:0px;display:#{IMPSMMIDGeneration.panelDisplay7}" styleClass="row1"  columnClasses="vtop">
                        <a4j:region>
                            <rich:dataTable  value="#{IMPSMMIDGeneration.p2PGridDetail}" var="tempItem"
                                             rowClasses="gridrow1, gridrow2" id="p2PTxnList" rows="10" columnsWidth="100" rowKeyVar="row"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                             width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="9"><h:outputText value="Transaction Detail"/></rich:column>
                                        <rich:column breakBefore="true"style="text-align:center;width:100px"><h:outputText value="Remitter Name" /></rich:column>
                                        <rich:column style="text-align:center;width:100px"><h:outputText value="Remitter A/c" /></rich:column>
                                        <rich:column style="text-align:center;width:100px"><h:outputText value="Remitter Mobile" /></rich:column>
                                        <rich:column style="text-align:center;width:100px"><h:outputText value="Beneficiary Mobile" /></rich:column>
                                        <rich:column style="text-align:center;width:100px"><h:outputText value="Beneficiary MMID" /></rich:column>
                                        <rich:column style="text-align:center;width:100px"><h:outputText value="Txn. Amount" /></rich:column>
                                        <rich:column style="text-align:center;width:100px"><h:outputText value="Select" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column style="text-align:center;width:100px"><h:outputText value="#{tempItem.name}" /></rich:column>
                                <rich:column style="text-align:center;width:70px"><h:outputText value="#{tempItem.accountNo}" /></rich:column>
                                <rich:column style="text-align:center;width:70px"><h:outputText value="#{tempItem.mobileno}" /></rich:column>                               
                                <rich:column style="text-align:center;width:70px"><h:outputText value="#{tempItem.beneMobileNo}" /></rich:column>
                                <rich:column style="text-align:center;width:70px"><h:outputText value="#{tempItem.beneMMID}" /></rich:column>
                                <rich:column style="text-align:center;width:70px"><h:outputText value="#{tempItem.tranAmount}" /></rich:column>

                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink id="selectlink1" oncomplete="#{rich:component('ConformationPanel')}.show()" reRender="ConformationPanel,errorPanel,errorMsg">
                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                        <f:setPropertyActionListener value="#{tempItem}" target="#{IMPSMMIDGeneration.p2PTempCurrentItem}"/>  
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller id="p2PUnAuthTempDataScroll" align="center" for="p2PTxnList" maxPages="18" />
                        </a4j:region> 
                    </h:panelGrid>

                    <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                        <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">

                            <a4j:commandButton id="btngen1" value="#{IMPSMMIDGeneration.btnvalue}" 
                                               action="#{IMPSMMIDGeneration.showButtonAlertMessage}" 
                                               oncomplete="#{rich:component('buttonConfirmationPanel')}.show()" 
                                               reRender="errorMsg,buttonConfirmationPanel,btnConfirmid" disabled="#{IMPSMMIDGeneration.disableProcessBtn}"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{IMPSMMIDGeneration.refresh}" reRender="mainPanel"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{IMPSMMIDGeneration.exitForm}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:region id="idBtnRegion1">
                <rich:modalPanel id="ConformationPanel" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Conformation Dialog" style="padding-right:15px;text-align:center;font-weight:bold;" />
                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText id="confirmid" value="#{IMPSMMIDGeneration.msgvalue}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="btnYes" action="#{IMPSMMIDGeneration.verifyUpdate}" onclick="#{rich:component('ConformationPanel')}.hide();" 
                                                           reRender="errorPanel,gridPanelTable,errorMsg,txnList,gridPanelP2ATable,gridPanelP2PTable,p2ATxnList,p2PTxnList,genpanel"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('ConformationPanel')}.hide();"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
            </a4j:region>
            <a4j:region id="idBtnRegion">
                <rich:modalPanel id="buttonConfirmationPanel" autosized="true" width="250" onshow="#{rich:element('btnAlertNo')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Conformation Dialog" style="padding-right:15px;text-align:center;font-weight:bold;" />
                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText id="btnConfirmid" value="#{IMPSMMIDGeneration.btnAlertMessage}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="btnAlertYes" action="#{IMPSMMIDGeneration.buttonClick}" onclick="#{rich:component('buttonConfirmationPanel')}.hide();" 
                                                           reRender="mainPanel"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="No" id="btnAlertNo" onclick="#{rich:component('buttonConfirmationPanel')}.hide();"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
            </a4j:region>
            <a4j:status onstart="#{rich:component('alertWait')}.show()" onstop="#{rich:component('alertWait')}.hide()" for="idRegion"/>
            <rich:modalPanel id="alertWait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
            <a4j:status onstart="#{rich:component('btnAlertWait')}.show()" onstop="#{rich:component('btnAlertWait')}.hide()" for="idBtnRegion"/>
            <rich:modalPanel id="btnAlertWait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
            <a4j:status onstart="#{rich:component('dialogboxalertWait')}.show()" onstop="#{rich:component('dialogboxalertWait')}.hide()" for="idBtnRegion1"/>
            <rich:modalPanel id="dialogboxalertWait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
            <a4j:status onstart="#{rich:component('ModealertWait')}.show()" onstop="#{rich:component('ModealertWait')}.hide()" for="idRegion2"/>
            <rich:modalPanel id="ModealertWait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>

