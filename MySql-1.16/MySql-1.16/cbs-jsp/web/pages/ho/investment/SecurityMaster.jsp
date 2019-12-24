<%-- 
    Document   : SecurityMaster
    Created on : May 29, 2012, 1:05:55 PM
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
        <title>Security Creation</title>
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
                    jQuery(".matDt").mask("99/99/9999");  
                    jQuery(".prnIssueDt").mask("99/99/9999");  
                    jQuery(".purchaseDt").mask("99/99/9999");  
                    jQuery(".trnDt").mask("99/99/9999");
                    jQuery(".settleDt").mask("99/99/9999");
                    jQuery(".fInttPayDt").mask("99/99/9999");
                    jQuery(".sInttPayDt").mask("99/99/9999");
                }
        </script>
    </head>
    <body>
        <a4j:form id="SecurityMasterForm">
            <h:panelGrid id="SecurityPanelGrid" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                <h:panelGrid id="SecurityGrpPanel" columns="3" style="width:100%;text-align:center;" styleClass="header">
                    <h:panelGroup id="SecurityPanel" layout="block">
                        <h:outputLabel id="SecurityDtLvl" styleClass="headerLabel" value="Date: "/>
                        <h:outputText id="SecurityDtOp" styleClass="output" value=" #{SecurityMaster.todayDate}"/>
                    </h:panelGroup>
                    <h:outputLabel id="SecurityNmLvl" styleClass="headerLabel" value="Security Creation"/>
                    <h:panelGroup id="SecurityPanel1" layout="block">
                        <h:outputLabel id="SecurityUsrLvl" styleClass="headerLabel" value="User: "/>
                        <h:outputText id="SecurityUsrOp" styleClass="output" value="#{SecurityMaster.userName}"/>
                    </h:panelGroup>
                </h:panelGrid>
                <h:panelGrid columnClasses="col8,col8" columns="2" id="SecurityPanel2" style="width:100%;height:30px;text-align:center;border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                    <h:outputText id="stxtMsg" styleClass="error" value="#{SecurityMaster.message}"/>
                </h:panelGrid>
                <h:panelGrid id="SecurityPanel3" columns="6" columnClasses="col1,col3,col1,col3,col4,col4" style="height:30px;border:1px ridge #BED6F8;"  styleClass="row1" width="100%">
                    <h:outputLabel id="secType" styleClass="label" value="Security Type"/>
                    <h:selectOneListbox id="secTypeDD" styleClass="ddlist" size="1" style="width: 200px" value="#{SecurityMaster.secTpDd}"> 
                        <f:selectItems value="#{SecurityMaster.secTypeList}"/>                        
                            <a4j:support action="#{SecurityMaster.secTypeLostFocus}" event="onblur" 
                                         reRender="stxtMsg,issueFrmDD,secDtlDD,matDt,prnIssueDt,trnDt,settleDt,markDD,fInttPayDt,sInttPayDt,conAcNoTxt,issuePrTxt,prTxt,bookValTxt,conSgAcDD,brokerAcTxt,crGlTxt,crBrnDD,noOfShrTxt,secDtlTxt"
                                         oncomplete="#{rich:element('matDt')}.style=setMask();#{rich:element('prnIssueDt')}.style=setMask();"/>
                    </h:selectOneListbox>
                    <h:outputLabel id="secDtl" styleClass="label" value="Security Detail"/>
                    <h:inputText id="secDtlTxt" styleClass="input" value="#{SecurityMaster.secDtlVal}" disabled="#{SecurityMaster.secTxtFlag}"/>
                    <h:selectOneListbox id="secDtlDD" styleClass="ddlist" size="1" style="width: 220px" value="#{SecurityMaster.secDetailDd}" disabled="#{SecurityMaster.secDtlFlag}"> 
                        <f:selectItems value="#{SecurityMaster.secDtlList}"/>                        
                        <a4j:support action="#{SecurityMaster.secDtlLostFocus}" event="onblur" reRender="stxtMsg,roiTxt,matDt,fInttPayDt,sInttPayDt"/>                        
                    </h:selectOneListbox>
                    <h:outputLabel/>                                        
                </h:panelGrid>
                <h:panelGrid id="SecurityPanel4" columns="6" columnClasses="col1,col3,col1,col3,col4,col4" style="height:30px;border:1px ridge #BED6F8;"  styleClass="row2" width="100%">
                    <h:outputLabel id="matDtLbl" styleClass="label" value="Maturity Date"/>
                    <h:inputText id="matDt" styleClass="input matDt" value="#{SecurityMaster.matDate}" style="width:75px;" disabled="#{SecurityMaster.matFlag}">
                        <f:convertDateTime pattern="dd/MM/yyyy"/>                       
                    </h:inputText>
                    <h:outputLabel id="issueFrmLbl" styleClass="label" value="Seller Name/Issue From"/>
                    <h:selectOneListbox id="issueFrmDD" styleClass="ddlist" size="1" style="width: 90px" value="#{SecurityMaster.issueFrDd}"> 
                        <f:selectItems value="#{SecurityMaster.issueFrmList}"/>
                        <a4j:support action="#{SecurityMaster.secSelLostFocus}" event="onblur" reRender="stxtMsg,sellerNmTxt"/>
                    </h:selectOneListbox>
                    <h:outputLabel id="sellerNmLbl" styleClass="label" value="Seller Name"/>
                    <h:inputText id="sellerNmTxt" styleClass="input" value="#{SecurityMaster.sellerNmVal}" disabled="#{SecurityMaster.selFlag}"/>                  
                </h:panelGrid>
                <h:panelGrid id="SecurityPanel5" columns="6" columnClasses="col1,col3,col1,col3,col4,col4" style="height:30px;border:1px ridge #BED6F8;"  styleClass="row1" width="100%">
                    <h:outputLabel id="codeNoLbl" styleClass="label" value="Code/Cert. No."/>
                    <h:inputText id="codeNoTxt" styleClass="input" value="#{SecurityMaster.codeNoVal}"/>
                    <h:outputLabel id="prnIssueDtLbl" styleClass="label" value="Original Issue Date"/>
                    <h:inputText id="prnIssueDt" styleClass="input prnIssueDt" value="#{SecurityMaster.prnIssueDate}" style="width:75px;" disabled="#{SecurityMaster.issDtFlag}"/>
                    <h:outputLabel id="purDtLbl" styleClass="label" value="Purchase Date"/>
                    <h:inputText id="purchaseDt" styleClass="input purchaseDt" value="#{SecurityMaster.purchaseDate}" style="width:75px;">
                        <a4j:support action="#{SecurityMaster.purDateLostFocus}" oncomplete="setMask()" event="onblur" reRender="stxtMsg,fInttPayDt,sInttPayDt"/>
                    </h:inputText>                    
                </h:panelGrid>
                <h:panelGrid id="SecurityPanel6" columns="6" columnClasses="col1,col3,col1,col3,col4,col4" style="height:30px;border:1px ridge #BED6F8;"  styleClass="row2" width="100%">
                    <h:outputLabel id="dtTranLbl" styleClass="label" value="Date Of Transaction"/>
                    <h:inputText id="trnDt" styleClass="input trnDt" value="#{SecurityMaster.transactionDate}" style="width:75px;" disabled="#{SecurityMaster.trnFlag}">
                        <f:convertDateTime pattern="dd/MM/yyyy" />
                        <a4j:support oncomplete="setMask()"/>
                    </h:inputText>
                    <h:outputLabel id="settleDtLbl" styleClass="label" value="Date Of Settlement"/>
                    <h:inputText id="settleDt" styleClass="input settleDt" value="#{SecurityMaster.settlementDate}" style="width:75px;" disabled="#{SecurityMaster.setFlag}">
                        <f:convertDateTime pattern="dd/MM/yyyy" />
                        <a4j:support oncomplete="setMask()"/>
                    </h:inputText>
                    <h:outputLabel id="markLbl" styleClass="label" value="Marking"/>
                    <h:selectOneListbox id="markDD" styleClass="ddlist" size="1" style="width: 110px" value="#{SecurityMaster.markingDd}" disabled="#{SecurityMaster.markFlag}"> 
                        <f:selectItems value="#{SecurityMaster.markList}"/>
                    </h:selectOneListbox>                   
                </h:panelGrid>
                <h:panelGrid id="SecurityPanel7" columns="6" columnClasses="col1,col3,col1,col3,col4,col4" style="height:30px;border:1px ridge #BED6F8;"  styleClass="row1" width="100%">
                    <h:outputLabel id="fIntPayDtLbl" styleClass="label" value="1st Intt. Payable Date"/>
                    <h:inputText id="fInttPayDt" styleClass="input fInttPayDt" value="#{SecurityMaster.fInttPayDate}" style="width:75px;" disabled="#{SecurityMaster.fstFlag}">
                        <a4j:support oncomplete="setMask()"/>
                    </h:inputText>
                    <h:outputLabel id="sIntPayDtLbl" styleClass="label" value="2nd Intt. Payable Date"/>
                    <h:inputText id="sInttPayDt" styleClass="input sInttPayDt" value="#{SecurityMaster.sInttPayDate}" style="width:75px;" disabled="#{SecurityMaster.secIFlag}">
                        <a4j:support oncomplete="setMask()"/>
                    </h:inputText>                    
                    <h:outputLabel id="roiLbl" styleClass="label" value="Rate Of Interest"/>
                    <h:inputText id="roiTxt" styleClass="input" value="#{SecurityMaster.roiVal}"/>                     
                </h:panelGrid>
                <h:panelGrid id="SecurityPanel8" columns="6" columnClasses="col1,col3,col1,col3,col4,col4" style="height:30px;border:1px ridge #BED6F8;"  styleClass="row2" width="100%">
                    <h:outputLabel id="noOfShrLbl" styleClass="label" value="No Of Share"/>
                    <h:inputText id="noOfShrTxt" styleClass="input" value="#{SecurityMaster.noOfShrVal}" disabled="#{SecurityMaster.shareFlag}"/>
                    <h:outputLabel id="conAccNoLbl" styleClass="label" value="Constituent A/C No."/>
                    <h:inputText id="conAcNoTxt" styleClass="input" value="#{SecurityMaster.conAcNoVal}" disabled="#{SecurityMaster.conAcFlag}"/>
                    <h:outputLabel id="issuedPrLbl" styleClass="label" value="Issued Unit"/>
                    <h:inputText id="issuePrTxt" styleClass="input" value="#{SecurityMaster.issuePrVal}" disabled="#{SecurityMaster.issPFlag}"/>
                </h:panelGrid>
                <h:panelGrid id="SecurityPanel9" columns="6" columnClasses="col1,col3,col1,col3,col4,col4" style="height:30px;border:1px ridge #BED6F8;"  styleClass="row1" width="100%">
                    <h:outputLabel id="prLbl" styleClass="label" value="Issuing Price"/>
                    <h:inputText id="prTxt" styleClass="input" value="#{SecurityMaster.priceVal}" disabled="#{SecurityMaster.prFlag}"/>
                    <h:outputLabel id="faceValLbl" styleClass="label" value="Face Value"/>
                    <h:inputText id="faceValTxt" styleClass="input" value="#{SecurityMaster.faceValue}"/>
                    <h:outputLabel id="bookValLbl" styleClass="label" value="Book Value"/>
                    <h:inputText id="bookValTxt" styleClass="input" value="#{SecurityMaster.bookValue}" disabled="#{SecurityMaster.bookFlag}"> 
                        <a4j:support event="onblur" actionListener="#{SecurityMaster.bookLostFocus}" reRender="stxtMsg,accrIntTxt"/>
                    </h:inputText>
                </h:panelGrid>
                <h:panelGrid id="SecurityPanel10" columns="6" columnClasses="col1,col3,col1,col3,col4,col4" style="height:30px;border:1px ridge #BED6F8;"  styleClass="row2" width="100%">
                    <h:outputLabel id="ytmIntLbl" styleClass="label" value="YTM(Purchased ROI)"/>
                    <h:inputText id="ytmIntTxt" styleClass="input" value="#{SecurityMaster.ytmVal}"/>
                    <h:outputLabel id="accrIntLbl" styleClass="label" value="Accrued Interest"/>
                    <h:inputText id="accrIntTxt" styleClass="input" value="#{SecurityMaster.accrIntVal}"/>                    
                    <h:outputLabel id="conSglAcLbl" styleClass="label" value="Constituent SGL A/C With"/>
                    <h:selectOneListbox id="conSgAcDD" styleClass="ddlist" size="1" style="width: 70px" value="#{SecurityMaster.conSgAccountDd}" disabled="#{SecurityMaster.sglFlag}"> 
                        <f:selectItems value="#{SecurityMaster.conSgAcList}"/>
                    </h:selectOneListbox>                    
                </h:panelGrid>
                <h:panelGrid id="SecurityPanel11" columns="6" columnClasses="col1,col3,col1,col3,col4,col4" style="height:30px;border:1px ridge #BED6F8;"  styleClass="row1" width="100%">
                    <h:outputLabel id="brokerAcLbl" styleClass="label" value="Broker A/C No."/>
                    <h:inputText id="brokerAcTxt" styleClass="input" value="#{SecurityMaster.brokerAcVal}" disabled="#{SecurityMaster.conFlag}"/>
                    <h:outputLabel id="brokerageAmtLbl" styleClass="label" value="Brokerage Amount"/>
                    <h:inputText id="brokerageAmtTxt" styleClass="input" value="#{SecurityMaster.brokerageAmtVal}"/>
                    <h:outputLabel id="crGlLbl" styleClass="label" value="Credit GL Head"/>
                    <h:panelGroup layout="block">
                        <h:inputText id="crGlTxt" styleClass="input" value="#{SecurityMaster.crGlVal}" disabled="#{SecurityMaster.crGlFlag}">
                            <a4j:support action="#{SecurityMaster.onBlurCrHead}" event="onblur" reRender="stxtMsg,stxtCrHeadName" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputText id="stxtCrHeadName" value="#{SecurityMaster.crHeadName}" styleClass="output"/>
                    </h:panelGroup>                    
                </h:panelGrid>
                <h:panelGrid id="SecurityPanel12" columns="6" columnClasses="col1,col3,col1,col3,col4,col4" style="height:30px;border:1px ridge #BED6F8;"  styleClass="row2" width="100%">
                    <h:outputLabel id="crBrnLbl" styleClass="label" value="Credit Branch"/>
                    <h:selectOneListbox id="crBrnDD" styleClass="ddlist" size="1" style="width: 70px" value="#{SecurityMaster.crBranchDd}" disabled="#{SecurityMaster.crBrFlag}"> 
                        <f:selectItems value="#{SecurityMaster.crBrnList}"/>
                    </h:selectOneListbox>
                    <h:outputLabel id="PartLbl" styleClass="label" value="Particulars"/>
                    <h:inputText id="PartTxt" styleClass="input" value="#{SecurityMaster.particularVal}"/>
                    <h:outputLabel/>
                    <h:outputLabel/>                    
                </h:panelGrid>
                <h:panelGrid id="frmFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                    <h:panelGroup id="frmBtnPanel">
                        <a4j:commandButton id="btnSave" action="#{SecurityMaster.saveTxn}" value="Save" reRender="stxtMsg,SecurityPanel3,SecurityPanel4,SecurityPanel5,SecurityPanel6,SecurityPanel7,SecurityPanel8,SecurityPanel9,SecurityPanel10,SecurityPanel11,SecurityPanel12"/>
                        <a4j:commandButton id="btnRefresh" action="#{SecurityMaster.ClearAll}" value="Refresh" reRender="stxtMsg,SecurityPanel3,SecurityPanel4,SecurityPanel5,SecurityPanel6,SecurityPanel7,SecurityPanel8,SecurityPanel9,SecurityPanel10,SecurityPanel11"/>
                        <a4j:commandButton id="btnExit" action="#{SecurityMaster.exitButton}" value="Exit"/>                        
                    </h:panelGroup>                    
                </h:panelGrid>                
            </h:panelGrid>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </a4j:form>        
    </body>
</html>
</f:view>