<%-- 
    Document   : AmortPosting
    Created on : Jun 20, 2012, 5:12:12 PM
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
        <title>Amortization Posting</title>
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
                 jQuery(".postDt").mask("99/99/9999");                   
            }
        </script>
    </head>
    <body>
        <a4j:form id="AmortPostingForm">
            <h:panelGrid id="AmortPanelGrid" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                <h:panelGrid id="AmortGrpPanel" columns="3" style="width:100%;text-align:center;" styleClass="header">
                    <h:panelGroup id="AmortPanel" layout="block">
                        <h:outputLabel id="AmortDtLvl" styleClass="headerLabel" value="Date: "/>
                        <h:outputText id="AmortDtOp" styleClass="output" value=" #{AmortPosting.todayDate}"/>
                    </h:panelGroup>
                    <h:outputLabel id="AmortNmLvl" styleClass="headerLabel" value="Amortization Posting"/>
                    <h:panelGroup id="AmortPanel1" layout="block">
                        <h:outputLabel id="AmortUsrLvl" styleClass="headerLabel" value="User: "/>
                        <h:outputText id="AmortUsrOp" styleClass="output" value="#{AmortPosting.userName}"/>
                    </h:panelGroup>
                </h:panelGrid>
                <h:panelGrid columnClasses="col8,col8" columns="2" id="AmortPanel2" style="width:100%;height:30px;text-align:center;border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                    <h:outputText id="stxtMsg" styleClass="error" value="#{AmortPosting.message}"/>
                </h:panelGrid>
                <h:panelGrid id="AmortPanel3" columns="6" columnClasses="col2,col2,col2,col10" style="height:30px;border:1px ridge #BED6F8;"  styleClass="row1" width="100%">
                    <h:outputLabel id="optType" styleClass="label" value="Option"/>
                    <h:selectOneListbox id="optTypeDD" styleClass="ddlist" size="1" style="width: 200px" value="#{AmortPosting.optTpDd}"> 
                        <f:selectItems value="#{AmortPosting.optTypeList}"/>                        
                        <a4j:support action="#{AmortPosting.optTypeLostFocus}" event="onblur" reRender="ctrlNoDD,stxtMsg"/>
                    </h:selectOneListbox>
                    <h:outputLabel id="ctrlType" styleClass="label" value="Control No."/>
                    <h:selectOneListbox id="ctrlNoDD" styleClass="ddlist" size="1" style="width: 220px" value="#{AmortPosting.ctrlNoDd}" disabled="#{AmortPosting.ctrlFlag}"> 
                        <f:selectItems value="#{AmortPosting.ctrlNoList}"/>                        
                        <a4j:support/>                        
                    </h:selectOneListbox>                    
                </h:panelGrid>
                <h:panelGrid id="AmortPanel4" columns="6" columnClasses="col2,col18,col18,col17,col3,col21" style="height:30px;border:1px ridge #BED6F8;"  styleClass="row2" width="100%">
                    <h:outputLabel id="postingDt" styleClass="label" value="Date"/>
                    <h:selectOneListbox id="dtTpDD" styleClass="ddlist" size="1" style="width: 80px" value="#{AmortPosting.dtDd}"> 
                        <f:selectItems value="#{AmortPosting.dtList}"/>                        
                    </h:selectOneListbox>
                    <h:selectOneListbox id="freqTpDD" styleClass="ddlist" size="1" style="width: 80px" value="#{AmortPosting.freqDd}"> 
                        <f:selectItems value="#{AmortPosting.freqList}"/>                        
                    </h:selectOneListbox>
                    <h:inputText id="postDt" styleClass="input postDt" value="#{AmortPosting.postDate}" style="width:75px;"/> 
                    <h:outputLabel/>
                    <h:outputLabel/>                    
                </h:panelGrid>
                <h:panelGrid id="AmortPanel5" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;border:1px ridge #BED6F8;"  styleClass="row1" width="100%">
                    <h:outputLabel id="lblCrGlHead" styleClass="label" value = "Credited GL Head"/>
                    <h:outputText id="stxtCrGl" styleClass="output" value="#{AmortPosting.creditGl}"/>
                    <h:outputText id="stxtCrAcnoDesc" styleClass="output" value="#{AmortPosting.crHeadDesc}"/>
                    <h:outputLabel id="lblCrAmt" styleClass="label" value="Credit Account :"/>
                    <h:outputText id="stxtCrAmt" styleClass="output" value="#{AmortPosting.creditAmt}"/>                    
                </h:panelGrid>
                <h:panelGrid id="AmortPanel6" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;border:1px ridge #BED6F8;"  styleClass="row2" width="100%">
                    <h:outputLabel id="lblDrGlHead" styleClass="label" value = "Debited GL Head" />
                    <h:outputText id="stxtDrGl" styleClass="output" value="#{AmortPosting.debitGl}"/>
                    <h:outputText id="stxtDrAcnoDesc" styleClass="output" value="#{AmortPosting.drHeadDesc}"/>
                    <h:outputLabel id="lblDrAmt" styleClass="label" value="Debited Amount :" />
                    <h:outputText id="stxtDrAmt" styleClass="output" value="#{AmortPosting.debitAmt}"/>
                </h:panelGrid>
                <h:panelGrid id="frmFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                    <h:panelGroup id="frmBtnPanel">
                        <a4j:commandButton id="btnCal" action="#{AmortPosting.calTxn}" value="Calculate" reRender="stxtMsg,AmortPanel5,AmortPanel6"/>
                        <a4j:commandButton id="btnPost" action="#{AmortPosting.postTxn}" value="Post" reRender="stxtMsg,AmortPanel5,AmortPanel6"/>
                        <a4j:commandButton id="btnRefresh" action="#{AmortPosting.ClearAll}" value="Refresh" reRender="stxtMsg,AmortPanel5,AmortPanel6"/>
                        <a4j:commandButton id="btnExit" action="#{AmortPosting.exitButton}" value="Exit"/>                        
                    </h:panelGroup>
                </h:panelGrid>              
            </h:panelGrid>    
        </a4j:form>    
    </body>        
    </html>
</f:view>