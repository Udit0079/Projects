<%-- 
    Document   : FidelityAdjustment
    Created on : May 13, 2014, 2:32:39 PM
    Author     : sipl
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
            <title>Fidelity Adjustment</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery){
                    setMask();                    
                });                
                function setMask(){
                    jQuery(".calInstDate").mask("39/19/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="fidelityAdjustment">
                <h:panelGrid bgcolor="#fff" columns="1" id="outerPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{fidelityAdjustment.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Fidelity Adjustment"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{fidelityAdjustment.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                        <h:panelGrid columnClasses="col2,col7" columns="2" id="msgRow" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputLabel id="lblMsg" styleClass="error" value="#{fidelityAdjustment.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row2" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblTillDate" styleClass="label" value = "Till Date :"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="calTillDate" styleClass="input calInstDate" style="width:70px;" maxlength="10" value="#{fidelityAdjustment.tillDate}" disabled="#{fidelityAdjustment.disDt}">
                                <f:convertDateTime pattern="dd/MM/yyyy" />
                                <a4j:support event="onblur" action="#{fidelityAdjustment.setDesg}" reRender="msgRow,ddDesgWise,Row4,Row5,InterestCal"/>
                            </h:inputText>
                            <h:outputLabel id="lblDesgWise" styleClass="label" value="Designation Option : "><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddDesgWise" styleClass="ddlist" size="1" style="width:100px;" value="#{fidelityAdjustment.desgOption}" disabled="#{fidelityAdjustment.disDesg}">
                                <f:selectItems value="#{fidelityAdjustment.desgOptionList}"/>
                                <a4j:support event="onblur" action="#{fidelityAdjustment.setAcAmt}" reRender="msgRow,Row4,Row5,InterestCal"/>
                            </h:selectOneListbox>                            
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row4" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblDebitAmt" styleClass="label" value = "Debit Account :"/>
                            <h:outputText id="stxtDebitAmt" styleClass="output" value="#{fidelityAdjustment.debitAcc}"/>
                            <h:outputLabel id="lblTotalDebit" styleClass="label" value="Total Debit :"/>
                            <h:outputText id="stxtTotalDebit" styleClass="output" value="#{fidelityAdjustment.totalDebit}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row5" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblCreditAmt" styleClass="label" value = "Credit Account :" />
                            <h:outputText id="stxtCreditAmt" styleClass="output" value="#{fidelityAdjustment.creditAcc}"/>
                            <h:outputLabel id="lblTotalCredit" styleClass="label" value="Total Credit :" />
                            <h:outputText id="stxtTotalCredit" styleClass="output" value="#{fidelityAdjustment.totalCredit}"/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col1,col2" columns="1" id="InterestCal"  width="100%" styleClass="row2">
                        <rich:modalPanel id="calculatePanel" autosized="true" width="300" onshow="#{rich:element('btnCalcYes')}.focus();">
                            <f:facet name="header">
                                <h:outputText value="Confirmation Dialog" style="padding-right:15px;"/>
                            </f:facet>
                            <h:form>
                                <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                    <tbody>
                                        <tr style="height:40px">
                                            <td colspan="2">
                                                <h:outputText value="Are You Sure To Calculate ?"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="Yes" ajaxSingle="true" id="btnCalcYes" action="#{fidelityAdjustment.calculate}"
                                                    oncomplete="#{rich:component('calculatePanel')}.hide();if(#{fidelityAdjustment.reportFlag})
                                                    {
                                                        #{rich:component('popUpRepPanel')}.show();
                                                    }
                                                    else if(#{!fidelityAdjustment.reportFlag})
                                                    {
                                                        #{rich:component('popUpRepPanel')}.hide();
                                                    }"
                                                    reRender="lblMsg,leftPanel,InterestCal,btnPost,popUpRepPanel"/>
                                            </td>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="No" onclick="#{rich:component('calculatePanel')}.hide();return false;"/>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </h:form>
                        </rich:modalPanel>
                        <rich:modalPanel id="postPanel" autosized="true" width="250" onshow="#{rich:element('btnPostNo')}.focus();">
                            <f:facet name="header">
                                <h:outputText value="Confirmation Dialog"/>
                            </f:facet>
                            <h:form>
                                <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                    <tbody>
                                        <tr style="height:40px">
                                            <td colspan="2">
                                                <h:outputText value="Are You Sure To Post ?"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="Yes" ajaxSingle="true" action="#{fidelityAdjustment.Post}"
                                                    oncomplete="#{rich:component('postPanel')}.hide();" reRender="msgRow,InterestCal,Row2,Row4,Row5,btnPost"/>
                                            </td>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="No" id="btnPostNo" onclick="#{rich:component('postPanel')}.hide();return false;"/>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </h:form>
                        </rich:modalPanel>
                    </h:panelGrid>
                    <h:panelGrid id="poFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:region id="btnPnl">
                                <a4j:commandButton id="btnCalculate" value="Calculate" oncomplete="#{rich:component('calculatePanel')}.show()"
                                        reRender="lblMsg,leftPanel,InterestCal,btnPost"/>
                                <a4j:commandButton id="btnPost" value="Post" disabled="#{fidelityAdjustment.disablePost}" oncomplete="#{rich:component('postPanel')}.show()"
                                        reRender="msgRow,InterestCal,Row2,Row4,Row5,btnPost"/>
                                <a4j:commandButton id="btnRefresh" value="Refresh" action="#{fidelityAdjustment.refresh}" oncomplete="setMask();" reRender="lblMsg,leftPanel,InterestCal,btnPost"/>
                                <a4j:commandButton id="btnExit" value="Exit" action="#{fidelityAdjustment.exitBtnAction}"/>
                            </a4j:region>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
                <rich:modalPanel id="wait" autosized="true" width="250" height="60" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;" >
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                    <h:outputText value="It May Take Some Time. Please Wait..." />
                </rich:modalPanel>
            </a4j:form>
            <rich:modalPanel id="popUpRepPanel" label="Form" width="1000" height="550" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpRepPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Fidelity Adjustment Report" style="text-align:center;"/>
                    </h:panelGrid>
                </f:facet>
                <f:facet name="controls">
                    <h:outputLink  id="closelink" onclick="#{rich:component('popUpRepPanel')}.hide()">
                        <h:graphicImage value="/resources/images/close.gif" style="border:0" />
                    </h:outputLink>
                </f:facet>
                <table width="100%">
                    <tbody>
                        <tr>
                            <td>
                                <a4j:include viewId="#{fidelityAdjustment.viewID}"/>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </rich:modalPanel>
        </body>
    </html>
</f:view>