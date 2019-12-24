<%-- 
    Document   : fdrclose
    Created on : Jun 17, 2013, 2:58:48 PM
    Author     : sipl
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
            <title>FDR Closing</title>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery){
                    setMask();
                });
                var row;
                function setMask(){
                    jQuery(".mainPurchaseDt").mask("99/99/9999");
                    jQuery(".fPurchaseDt").mask("99/99/9999");
                    jQuery(".fMatDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="fdrClosingForm">
                <h:panelGrid id="fdrClosingGrid" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="fdrClosingGrpPanel" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="fdrClosingPanel" layout="block">
                            <h:outputLabel id="fdrClosingDtLvl" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="fdrClosingDtOp" styleClass="output" value=" #{fdrClosing.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="fdrClosingNmLvl" styleClass="headerLabel" value="FDR Closing"/>
                        <h:panelGroup id="fdrClosingPanel1" layout="block">
                            <h:outputLabel id="fdrClosingUsrLvl" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="fdrClosingUsrOp" styleClass="output" value="#{fdrClosing.loggedInUser}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="fdrClosingPanel2" columns="1" style="width:100%;text-align:center;" styleClass="row2" width="100%">
                        <h:outputText id="stxtMsg" styleClass="error" value="#{fdrClosing.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="fdrClosingPanel3" columns="6" columnClasses="col1,col3,col1,col3,col4,col4" style="width:100%;text-align:left;"  styleClass="row1" width="100%">
                        <h:outputLabel id="lblFunction" styleClass="label" value="Function"/>
                        <h:selectOneListbox id="ddFunction" style="width:120px" styleClass="ddlist" value="#{fdrClosing.function}" size="1" disabled="#{fdrClosing.disFunction}">
                            <f:selectItems value="#{fdrClosing.functionList}"/>
                            <a4j:support action="#{fdrClosing.functionAction}" event="onblur" reRender="stxtMsg,ddCtrlNo,btnSave,btnAuth,btnDel" focus="ddCtrlNo"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblCtrlNo" styleClass="label" value="Control No."/>
                        <h:selectOneListbox id="ddCtrlNo" style="width:120px" styleClass="ddlist"  value="#{fdrClosing.ctrl}" size="1" disabled="#{fdrClosing.disCtrl}">
                            <f:selectItems value="#{fdrClosing.ctrlList}"/>
                            <a4j:support action="#{fdrClosing.ctrlAction}" event="onblur" reRender="stxtMsg,stxtFdrNo,stxtSPurDt,stxtSMaturityDt,stxtSFaceVal,stxtSBookValue,
                                         fPurchaseDt,fMatDt,txtRoi,txtFaceVal,txtInterest,ddIntOpt,txtBookValue,stxtSellerName,stxtSRoi,stxtSIntOpt,stxtSBranch,txtdays,txtMonth,txtYears,txtIntAccGlHead,stxtIntAccGlHeadDesc,txtIntCrGlHead,stxtIntCrGlHeadDesc,stxtIntCrGlHead,txtIntRecCrGlHead,
                                         stxtIntRecCrGlHeadDesc,stxtIntRecCrGlHead,txtFaceValCrGlHead,stxtFaceValCrGlHeadDesc,stxtFaceValCrGlHead,txtBookValDrGlHead,stxtBookValDrGlHeadDesc,stxtBookValDrGlHead,
                                         stxtCloseRen,radioRenClose,radioClosing,stxtIntAccGlHead,ddFunction,ddCtrlNo"/>
                        </h:selectOneListbox>                        
                        <h:outputLabel/>
                        <h:outputLabel/>                                        
                    </h:panelGrid>                  
                    <h:panelGrid id="fdrClosingPanel4" columns="6" columnClasses="col1,col3,col1,col3,col3,col3" style="width:100%;text-align:left;"  styleClass="row2" width="100%">
                        <h:outputLabel id="lblFdrNo" styleClass="label" value="FDR No"></h:outputLabel>
                        <h:outputText id="stxtFdrNo" styleClass="output" value="#{fdrClosing.fdrNo}"/>
                        <h:outputLabel id="lblSPurDt" styleClass="label" value="Purchase Date"></h:outputLabel>
                        <h:outputText id="stxtSPurDt" styleClass="output" value="#{fdrClosing.sPurDate}"/>
                        <h:outputLabel id="lblSMaturityDt" styleClass="label" value="Maturity Date"></h:outputLabel>
                        <h:outputText id="stxtSMaturityDt" styleClass="output" value="#{fdrClosing.sMatDate}"/>
                    </h:panelGrid>
                    <h:panelGrid id="fdrClosingPanel5" columns="6" columnClasses="col1,col3,col1,col3,col3,col3" style="width:100%;text-align:left;"  styleClass="row1" width="100%">
                        <h:outputLabel id="lblSFaceVal" styleClass="label" value="Face Value"/>
                        <h:outputText id="stxtSFaceVal" styleClass="output" value="#{fdrClosing.sFaceValue}"/>
                        <h:outputLabel id="lblSBookValue" styleClass="label" value="Maturity Value"/>
                        <h:outputText id="stxtSBookValue" styleClass="output" value="#{fdrClosing.sBookvalue}"/>
                        <h:outputLabel id="lblSellerName" styleClass="label" value="Seller Name"/>
                        <h:outputText id="stxtSellerName" styleClass="output" value="#{fdrClosing.sellerNmVal}"/>                                        
                    </h:panelGrid>
                    <h:panelGrid id="fdrClosingPanel6" columns="6" columnClasses="col1,col3,col1,col3,col3,col3" style="width:100%;text-align:left;"  styleClass="row2" width="100%">
                        <h:outputLabel id="lblSRoi" styleClass="label" value="Roi"/>
                        <h:outputText id="stxtSRoi" styleClass="output" value="#{fdrClosing.sRoi}"/>
                        <h:outputLabel id="lblSIntOpt" styleClass="label" value="Int Opt"/>
                        <h:outputText id="stxtSIntOpt" styleClass="output" value="#{fdrClosing.sIntOpt}"/>
                        <h:outputLabel id="lblSBranch" styleClass="label" value="Branch"/>
                        <h:outputText id="stxtSBranch" styleClass="output" value="#{fdrClosing.sBranch}"/>
                    </h:panelGrid>
                    <rich:panel header="Closing/Renewal Option">
                        <h:panelGrid id="fdrClosingPanel7" columns="5" columnClasses="col2,col2,col7,col17,col2" style="width:100%;text-align:left;"  styleClass="row1" width="100%">
                            <h:selectOneRadio id="radioRenClose" style="width:140px;" styleClass="label" value="#{fdrClosing.renClose}" disabled="#{fdrClosing.disRadioClose}">
                                <f:selectItem id="itemClose" itemLabel="Closing" itemValue="C"/>
                                <f:selectItem id="itemRenew" itemLabel="Renewal" itemValue="R"/>
                                <a4j:support action="#{fdrClosing.disableType()}" event="onchange" reRender="stxtMsg,fPurchaseDt,txtdays,txtMonth,txtYears,fMatDt,txtRoi,ddIntOpt,txtFaceVal,txtInterest,txtBookValue,stxtCloseRen,radioClosing,mainPurchaseDt,
                                             txtIntCrGlHead,stxtIntCrGlHeadDesc,stxtIntCrGlHead,txtIntRecCrGlHead,stxtIntRecCrGlHeadDesc,stxtIntRecCrGlHead,txtFaceValCrGlHead,
                                             stxtFaceValCrGlHeadDesc,stxtFaceValCrGlHead,txtBookValDrGlHead,stxtBookValDrGlHeadDesc,stxtBookValDrGlHead,fPurchaseDt,ddIntOpt"
                                             oncomplete="#{rich:element('mainPurchaseDt')}.style=setMask();#{rich:element('fPurchaseDt')}.style=setMask();#{rich:element('fMatDt')}.style=setMask();"/>
                            </h:selectOneRadio>
                            <h:outputText id="stxtCloseRen" styleClass="output" value="#{fdrClosing.renCloseVal}"/>        
                            <h:selectOneRadio id="radioClosing" style="width:240px;" styleClass="label" value="#{fdrClosing.close}" disabled="#{fdrClosing.disClose}">
                                <f:selectItem id="itemMClose" itemLabel="Manual Closing" itemValue="M"/>
                                <f:selectItem id="itemAClose" itemLabel="Auto Closing" itemValue="A"/>
                                <a4j:support reRender="stxtMsg"/>   
                            </h:selectOneRadio>
                            <h:outputLabel id="lblRenPurDt" styleClass="label" value="Purchase Date"></h:outputLabel>
                            <h:inputText id="mainPurchaseDt" value="#{fdrClosing.renPurDt}" size="10" maxlength="10" styleClass="input mainPurchaseDt" style="setMask();width:70px;" disabled="#{fdrClosing.disRenPurDt}"/>
                        </h:panelGrid>
                    </rich:panel>
                    <rich:panel header="Renewal">
                        <h:panelGrid id="periodGrid" columns="6" columnClasses="col1,col1,col1,col2,col1,col2" styleClass="row2" style="width:100%;text-align:left;" width="100%">
                            <h:outputLabel id="lblPurDt" styleClass="label" value="Renewal Date"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="fPurchaseDt" value="#{fdrClosing.purchaseDate}" size="10" maxlength="10" styleClass="input fPurchaseDt" style="setMask();width:70px;" disabled="#{fdrClosing.disPurDt}"/>
                            <h:outputLabel id="lblPeriod" styleClass="label" value="Period"></h:outputLabel>
                            <h:panelGroup layout="block">
                                <h:outputLabel id="lbldays" styleClass="label" value="Days"></h:outputLabel>
                                <h:inputText id="txtdays" value="#{fdrClosing.days}" size="3" styleClass="input" disabled="#{fdrClosing.disDays}">
                                    <a4j:support action="#{fdrClosing.onBlurMatDate}" event="onblur" reRender="stxtMsg,fMatDt"/>
                                </h:inputText>   
                                <h:outputLabel id="lblMonth" styleClass="label" value="Months"></h:outputLabel>
                                <h:inputText id="txtMonth" value="#{fdrClosing.months}" size="3" styleClass="input" disabled="#{fdrClosing.disMonths}">
                                    <a4j:support action="#{fdrClosing.onBlurMatDate}" event="onblur" reRender="stxtMsg,fMatDt"/>
                                </h:inputText>
                                <h:outputLabel id="lblYears" styleClass="label" value="Years"></h:outputLabel>
                                <h:inputText id="txtYears" value="#{fdrClosing.years}" size="5" styleClass="input" disabled="#{fdrClosing.disYears}">
                                    <a4j:support action="#{fdrClosing.onBlurMatDate}" event="onblur" reRender="stxtMsg,fMatDt"/>
                                </h:inputText>
                            </h:panelGroup>
                            <h:outputLabel id="lblMaturityDt" styleClass="label" value="Maturity Date"></h:outputLabel>
                            <h:inputText id="fMatDt" value="#{fdrClosing.matDate}" size="10" maxlength="10" styleClass="input fMatDt" style="setMask();width:70px;" disabled="#{fdrClosing.disMatDt}"/>
                        </h:panelGrid>
                        <h:panelGrid id="intOptGrid" columns="6" columnClasses="col1,col1,col1,col2,col1,col2" style="width:100%;text-align:left;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblRoi" styleClass="label" value="Roi"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtRoi" value="#{fdrClosing.roi}" size="10" styleClass="input" disabled="#{fdrClosing.disRoi}">
                                <a4j:support reRender="stxtMsg"/>
                            </h:inputText>
                            <h:outputLabel id="lblIntOpt" styleClass="label" value="Interest Option"></h:outputLabel>
                                <h:selectOneListbox id="ddIntOpt" style="width: 120px" styleClass="ddlist" value="#{fdrClosing.intOpt}" size="1" disabled="#{fdrClosing.disIntOpt}">
                                    <f:selectItems value="#{fdrClosing.intOptList}" />
                                        <a4j:support action="#{fdrClosing.onBlurIntOp}" event="onblur" reRender="stxtMsg,txtInterest"/>
                                </h:selectOneListbox>
                            <h:outputLabel id="lblFaceVal" styleClass="label" value="Face Value"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtFaceVal" value="#{fdrClosing.faceValue}" size="10" styleClass="input" disabled="#{fdrClosing.disFaceValue}"/>
                        </h:panelGrid>
                        <h:panelGrid id="bookValueGrid" columns="6" columnClasses="col1,col1,col1,col2,col1,col2" style="width:100%;text-align:left;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblInterest" styleClass="label" value="Interest"></h:outputLabel>
                            <h:inputText id="txtInterest" value="#{fdrClosing.interest}" styleClass="output" disabled="#{fdrClosing.disInt}">
                                <a4j:support action="#{fdrClosing.intOnBlurAction}" event="onblur" reRender="stxtMsg,txtBookValue"/>
                            </h:inputText>
                            <h:outputLabel id="lblBookValue" styleClass="label" value="Maturity Value"></h:outputLabel>
                            <h:inputText id="txtBookValue" value="#{fdrClosing.bookvalue}" styleClass="output" disabled="#{fdrClosing.disBookValue}"/>
                            <h:outputLabel/>
                            <h:outputLabel/>
                        </h:panelGrid>
                    </rich:panel>
                    <h:panelGrid id="fdrClosingPanel8" columns="4" columnClasses="col13,col13,col13,col13" style="width:100%;text-align:left;"  styleClass="row1" width="100%">
                        <h:outputLabel id="lblIntAccGlHead" styleClass="label" value="Int. Accrued Gl Head"/>
                        <h:inputText id="txtIntAccGlHead" value="#{fdrClosing.intAccGlHead}" styleClass="input" disabled="#{fdrClosing.disIntAccGlHead}">
                            <a4j:support action="#{fdrClosing.intOnBlurAccGlAction}" event="onblur" reRender="stxtMsg,stxtIntCrGlHeadDesc"/>
                        </h:inputText>
                        <h:outputText id="stxtIntAccGlHeadDesc" styleClass="output" value="#{fdrClosing.intAccGlHeadDesc}"/>
                        <h:outputText id="stxtIntAccGlHead" value="#{fdrClosing.intAccGlHeadVal}" styleClass="output"/>                        
                    </h:panelGrid>
                    <h:panelGrid id="fdrClosingPanel9" columns="4" columnClasses="col13,col13,col13,col13" style="width:100%;text-align:left;"  styleClass="row2" width="100%">
                        <h:outputLabel id="lblIntRecCrGlHead" styleClass="label" value="Int.Rec.(Balance) Credit Gl Head"/>
                        <h:inputText id="txtIntRecCrGlHead" value="#{fdrClosing.intRecCrGlHead}" styleClass="input" disabled="#{fdrClosing.disIntRecGlHead}">
                            <a4j:support action="#{fdrClosing.intOnBlurRecCrGlAction}" event="onblur" reRender="stxtMsg,stxtIntRecCrGlHeadDesc"/>
                        </h:inputText>
                        <h:outputText id="stxtIntRecCrGlHeadDesc" styleClass="output" value="#{fdrClosing.intRecCrGlHeadDesc}"/>
                        <h:inputText id="stxtIntRecCrGlHead" value="#{fdrClosing.intRecCrGlHeadVal}" styleClass="input" disabled="#{fdrClosing.disRecCrGlHeadVal}">
                            <a4j:support action="#{fdrClosing.intOnBlurRecCrGlHeadVal}" event="onblur" reRender="stxtMsg,stxtIntCrGlHead,stxtIntRecCrGlHead,stxtFaceValCrGlHead,stxtBookValDrGlHead"/>
                        </h:inputText>
                    </h:panelGrid>        
                    <h:panelGrid id="fdrClosingPanel10" columns="4" columnClasses="col13,col13,col13,col13" style="width:100%;text-align:left;"  styleClass="row1" width="100%">
                        <h:outputLabel id="lblIntCrGlHead" styleClass="label" value="Int. Debit Gl Head"/>
                        <h:inputText id="txtIntCrGlHead" value="#{fdrClosing.intCrGlHead}" styleClass="input" disabled="#{fdrClosing.disCrGlHead}">
                            <a4j:support action="#{fdrClosing.intOnBlurCrGlAction}" event="onblur" reRender="stxtMsg,stxtIntCrGlHeadDesc"/>
                        </h:inputText>
                        <h:outputText id="stxtIntCrGlHeadDesc" styleClass="output" value="#{fdrClosing.intCrGlHeadDesc}"/>                        
                        <h:outputText id="stxtIntCrGlHead" value="#{fdrClosing.intCrGlHeadVal}" styleClass="output"/>                            
                    </h:panelGrid>        
                    <h:panelGrid id="fdrClosingPanel11" columns="4" columnClasses="col13,col13,col13,col13" style="width:100%;text-align:left;"  styleClass="row2" width="100%">
                        <h:outputLabel id="lblFaceValCrGlHead" styleClass="label" value="Face Value Credit Gl Head"/>
                        <h:inputText id="txtFaceValCrGlHead" value="#{fdrClosing.faceValCrGlHead}" styleClass="input" disabled="#{fdrClosing.disFCrGlHead}">
                            <a4j:support action="#{fdrClosing.intOnBlurFaceValCrGlAction}" event="onblur" reRender="stxtMsg,stxtFaceValCrGlHeadDesc"/>
                        </h:inputText>
                        <h:outputText id="stxtFaceValCrGlHeadDesc" styleClass="output" value="#{fdrClosing.faceValCrGlHeadDesc}"/>
                        <h:outputText id="stxtFaceValCrGlHead" styleClass="output" value="#{fdrClosing.faceValCrGlHeadVal}"/>
                    </h:panelGrid>
                    <h:panelGrid id="fdrClosingPanel12" columns="4" columnClasses="col13,col13,col13,col13" style="width:100%;text-align:left;"  styleClass="row1" width="100%">
                        <h:outputLabel id="lblBookValDrGlHead" styleClass="label" value="Maturity Value Debit Gl Head"/>
                        <h:inputText id="txtBookValDrGlHead" value="#{fdrClosing.bookValDrGlHead}" styleClass="input" disabled="#{fdrClosing.disBookGlHead}">
                            <a4j:support action="#{fdrClosing.intOnBlurBookValDrGlAction}" event="onblur" reRender="stxtMsg,stxtBookValDrGlHeadDesc"/>
                        </h:inputText>
                        <h:outputText id="stxtBookValDrGlHeadDesc" styleClass="output" value="#{fdrClosing.bookValDrGlHeadDesc}"/>
                        <h:outputText id="stxtBookValDrGlHead" styleClass="output" value="#{fdrClosing.bookValDrGlHeadVal}"/>
                    </h:panelGrid> 
                    <h:panelGrid id="frmFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="frmBtnPanel">
                            <a4j:commandButton id="btnSave" action="#{fdrClosing.saveTxn}" value="Close/Renew FDR" reRender="stxtMsg,ddFunction,ddCtrlNo,stxtFdrNo,stxtSPurDt,
                                               stxtSMaturityDt,stxtSFaceVal,stxtSBookValue,stxtSellerName,stxtSRoi,stxtSIntOpt,stxtSBranch,fdrClosingPanel7,fPurchaseDt,
                                               txtdays,txtMonth,txtYears,fMatDt,txtRoi,ddIntOpt,txtFaceVal,txtInterest,txtBookValue,txtIntAccGlHead,stxtIntAccGlHeadDesc,
                                               stxtIntAccGlHead,txtIntRecCrGlHead,stxtIntRecCrGlHeadDesc,stxtIntRecCrGlHead,txtIntCrGlHead,stxtIntCrGlHeadDesc,stxtIntCrGlHead,
                                               txtFaceValCrGlHead,stxtFaceValCrGlHeadDesc,stxtFaceValCrGlHead,txtBookValDrGlHead,stxtBookValDrGlHeadDesc,stxtBookValDrGlHead,
                                               btnSave,btnAuth,btnDel,mainPurchaseDt" disabled="#{fdrClosing.disSave}"/>
                            <a4j:commandButton id="btnAuth" value="Authorize" oncomplete="#{rich:component('authorizePanel')}.show()" disabled="#{fdrClosing.disAuth}"/>
                            <a4j:commandButton id="btnDel" value="Delete" oncomplete="#{rich:component('deletePanel')}.show()" disabled="#{fdrClosing.disDelete}"/>
                            <a4j:commandButton id="btnRefresh" action="#{fdrClosing.ClearAll}" value="Refresh" reRender="stxtMsg,ddFunction,ddCtrlNo,stxtFdrNo,stxtSPurDt,
                                               stxtSMaturityDt,stxtSFaceVal,stxtSBookValue,stxtSellerName,stxtSRoi,stxtSIntOpt,stxtSBranch,fdrClosingPanel7,fPurchaseDt,
                                               txtdays,txtMonth,txtYears,fMatDt,txtRoi,ddIntOpt,txtFaceVal,txtInterest,txtBookValue,txtIntAccGlHead,stxtIntAccGlHeadDesc,
                                               stxtIntAccGlHead,txtIntRecCrGlHead,stxtIntRecCrGlHeadDesc,stxtIntRecCrGlHead,txtIntCrGlHead,stxtIntCrGlHeadDesc,stxtIntCrGlHead,
                                               txtFaceValCrGlHead,stxtFaceValCrGlHeadDesc,stxtFaceValCrGlHead,txtBookValDrGlHead,stxtBookValDrGlHeadDesc,stxtBookValDrGlHead,
                                               btnSave,btnAuth,btnDel,mainPurchaseDt" focus="ddFunction"/>
                            <a4j:commandButton id="btnExit" action="#{fdrClosing.exitButton}" value="Exit"/>                        
                        </h:panelGroup>
                    </h:panelGrid>        
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="authorizePanel" autosized="true" width="200" onshow="btnYes">
                <f:facet name="header">
                    <h:outputText value="Are You Sure To Authorize?" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%">
                        <tbody>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" ajaxSingle="true" action="#{fdrClosing.authTxn}"
                                               oncomplete="#{rich:component('authorizePanel')}.hide();" reRender="stxtMsg,ddFunction,ddCtrlNo,stxtFdrNo,stxtSPurDt,
                                               stxtSMaturityDt,stxtSFaceVal,stxtSBookValue,stxtSellerName,stxtSRoi,stxtSIntOpt,stxtSBranch,fdrClosingPanel7,fPurchaseDt,
                                               txtdays,txtMonth,txtYears,fMatDt,txtRoi,ddIntOpt,txtFaceVal,txtInterest,txtBookValue,txtIntAccGlHead,stxtIntAccGlHeadDesc,
                                               stxtIntAccGlHead,txtIntRecCrGlHead,stxtIntRecCrGlHeadDesc,stxtIntRecCrGlHead,txtIntCrGlHead,stxtIntCrGlHeadDesc,stxtIntCrGlHead,
                                               txtFaceValCrGlHead,stxtFaceValCrGlHeadDesc,stxtFaceValCrGlHead,txtBookValDrGlHead,stxtBookValDrGlHeadDesc,stxtBookValDrGlHead,
                                               btnSave,btnAuth,btnDel,mainPurchaseDt" />
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Cancel" onclick="#{rich:component('authorizePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="deletePanel" autosized="true" width="200" onshow="btnYes">
                <f:facet name="header">
                    <h:outputText value="Are You Sure To Delete?" style="padding-right:15px;"/>
                </f:facet>
                <h:form>
                    <table width="100%">
                        <tbody>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnDelYes" ajaxSingle="true" action="#{fdrClosing.DeleteTxn}"
                                               oncomplete="#{rich:component('deletePanel')}.hide();" reRender="stxtMsg,ddFunction,ddCtrlNo,stxtFdrNo,stxtSPurDt,
                                               stxtSMaturityDt,stxtSFaceVal,stxtSBookValue,stxtSellerName,stxtSRoi,stxtSIntOpt,stxtSBranch,fdrClosingPanel7,fPurchaseDt,
                                               txtdays,txtMonth,txtYears,fMatDt,txtRoi,ddIntOpt,txtFaceVal,txtInterest,txtBookValue,txtIntAccGlHead,stxtIntAccGlHeadDesc,
                                               stxtIntAccGlHead,txtIntRecCrGlHead,stxtIntRecCrGlHeadDesc,stxtIntRecCrGlHead,txtIntCrGlHead,stxtIntCrGlHeadDesc,stxtIntCrGlHead,
                                               txtFaceValCrGlHead,stxtFaceValCrGlHeadDesc,stxtFaceValCrGlHead,txtBookValDrGlHead,stxtBookValDrGlHeadDesc,stxtBookValDrGlHead,
                                               btnSave,btnAuth,btnDel,mainPurchaseDt" />
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Cancel" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>                
        </body>
    </html>
</f:view>