<%-- 
    Document   : masterclose
    Created on : Jun 1, 2012, 12:06:13 PM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Master Close</title>
        </head>
        <body>
            <a4j:form>
                <h:panelGrid id="maingrid" columns="1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid id="headergrid" columns="3" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{masterClose.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Master Close"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{masterClose.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid id="msggrid" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7;border:1px ridge #BED6F8;">
                        <h:outputText id="message" styleClass="error" value="#{masterClose.message}"/>
                    </h:panelGrid>

                    <h:panelGrid id="sectypegrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputLabel id="lblSecurityType" styleClass="label" value="Security Type"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddSecurityType" style="width:120px" styleClass="ddlist"  value="#{masterClose.securityType}" size="1">
                            <f:selectItems value="#{masterClose.securityTypeList}" />
                            <a4j:support action="#{masterClose.onBlurSecurity}" event="onblur" reRender="message,ddRepDesc"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblRepDesc" styleClass="label" value="Security Name"/>
                        <h:selectOneListbox id="ddRepDesc" size="1" styleClass="ddlist" value="#{masterClose.repDesc}">
                            <f:selectItems value="#{masterClose.repDescList}"/> 
                            <a4j:support action="#{masterClose.optDescLostFocus}" event="onblur" reRender="ddCtrlNo,message" focus="ddCtrlNo"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblCtrlNo" styleClass="label" value="Ctrl.No"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddCtrlNo" style="width:120px" styleClass="ddlist" value="#{masterClose.ctrlNo}" size="1">
                            <f:selectItems value="#{masterClose.ctrlNoList}" />
                            <a4j:support action="#{masterClose.onBlurCtrlNo}" event="onblur" reRender="message,stxtSellerName,stxtFaceValue,stxtBookValue,stxtMaturityDate,stxtAccruedInt,stxtAmortValue,stxtIssuingPrice,ddSellingStatus,txtSellingAmount,txtCrGlHead,stxtBalIntValue" focus="txtCrGlHead"/>
                        </h:selectOneListbox>                        
                    </h:panelGrid>

                    <h:panelGrid id="facevaluegrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%">
                        <h:outputLabel id="lblSellerName" styleClass="label" value="Seller Name"/>
                        <h:outputText id="stxtSellerName" styleClass="output" value="#{masterClose.sellerName}"/>
                        <h:outputLabel id="lblFaceValue" styleClass="label" value="Face Value"/>
                        <h:outputText id="stxtFaceValue" styleClass="output" value="#{masterClose.faceValue}"/>
                        <h:outputLabel id="lblBookValue" styleClass="label" value="Book Value"/>
                        <h:outputText id="stxtBookValue" styleClass="output" value="#{masterClose.bookValue}"/>                        
                    </h:panelGrid>

                    <h:panelGrid id="accruedinterestgrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputLabel id="lblMaturityDate" styleClass="label" value="Maturity Date"/>
                        <h:outputText id="stxtMaturityDate" styleClass="output" value="#{masterClose.maturityDate}"/>
                        <h:outputLabel id="lblAccruedInt" styleClass="label" value="Accrued Interest"/>
                        <h:inputText id="stxtAccruedInt" styleClass="output" value="#{masterClose.accruedInterest}"/>
                        <h:outputLabel id="lblAmortValue" styleClass="label" value="Amortization Value"/>
                        <h:inputText id="stxtAmortValue" styleClass="output" value="#{masterClose.amortizationValue}"/>                        
                    </h:panelGrid>

                    <h:panelGrid id="sellinggrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%">
                        <h:outputLabel id="lblBalIntValue" styleClass="label" value="Balance Interest"/>
                        <h:inputText id="stxtBalIntValue" styleClass="output" value="#{masterClose.balIntValue}"/>
                        <h:outputLabel id="lblIssuingPrice" styleClass="label" value="Issuing Price"/>
                        <h:outputText id="stxtIssuingPrice" styleClass="output" value="#{masterClose.issuingPrice}"/>
                        <h:outputLabel id="lblSellingStatus" styleClass="label" value="Selling Status"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddSellingStatus" style="width:120px" styleClass="ddlist"  value="#{masterClose.sellingStatus}" size="1">
                            <f:selectItems value="#{masterClose.sellingStatusList}" />
                            <a4j:support action="#{masterClose.onBlurSellingStatus}" event="onblur" oncomplete="if(#{masterClose.sellingStatus} == 'Full'){#{rich:element('txtCrGlHead')}.focus();} else{#{rich:element('txtSellingAmount')}.focus();}" reRender="txtSellingAmount,txtCrGlHead"/>
                        </h:selectOneListbox>                                                
                    </h:panelGrid>

                    <h:panelGrid id="glgrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputLabel id="lblSellingAmount" styleClass="label" value="Selling Amount"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtSellingAmount" size="10" styleClass="input" value="#{masterClose.sellingAmount}" disabled="#{masterClose.sellAmtVisible}"/>
                        <h:outputLabel id="lblSellingDate" styleClass="label" value="Selling Date"/>
                        <h:outputText id="stxtSellingDate" styleClass="output" value="#{masterClose.sellingDate}"/>
                        <h:outputLabel id="lblCrGlHead" styleClass="label" value="Debited GL Head"><font class="required" color="red">*</font></h:outputLabel>
                        <h:panelGroup layout="block">
                            <h:inputText id="txtCrGlHead" size="15" maxlength="12" styleClass="input" value="#{masterClose.crGLHead}">
                                <a4j:support action="#{masterClose.onBlurCrHead}" event="onblur" reRender="ddBrnAlpha,stxtCrHeadName,message"/>
                            </h:inputText>
                            <h:outputText id="stxtCrHeadName" value="#{masterClose.crHeadName}" styleClass="output"/>
                        </h:panelGroup>                                                
                    </h:panelGrid>
                    <h:panelGrid id="branchGrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%">
                        <h:outputLabel id="lblBrnAlpha" styleClass="label" value="Branch"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddBrnAlpha" style="width:120px" styleClass="ddlist"  value="#{masterClose.alphacode}" size="1">
                            <f:selectItems value="#{masterClose.alphacodeList}" />
                        </h:selectOneListbox>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>    

                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnProcess" value="Save" action="#{masterClose.saveAction}" reRender="maingrid"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{masterClose.resetForm}" reRender="maingrid"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{masterClose.exitBtnAction}" reRender="maingrid"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>