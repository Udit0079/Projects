<%-- 
    Document   : secIntPost
    Created on : Aug 26, 2013, 1:07:27 PM
    Author     : sipl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link rel="stylesheet" href="/cbs-jsp/resources/txn_style.css" type="text/css"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Security HY-Interest Posting</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".frDt").mask("99/99/9999");
                    jQuery(".toDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{SecurityIntPost.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Security HY-Interest Posting"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{SecurityIntPost.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row2" style="height:30px;text-align:center;" width="100%">
                        <h:outputLabel id="message" styleClass="error" style="color:red;" value="#{SecurityIntPost.message}"/>
                    </h:panelGrid>
                    <h:panelGrid  id="repTypeGrid" columns="6" columnClasses="col1,col3,col1,col3,col4,col4" styleClass="row1" width="100%">
                        <h:outputLabel id="lblRepType" styleClass="label" value="Security Type"/>
                        <h:selectOneListbox id="ddRepType" size="1" styleClass="ddlist" value="#{SecurityIntPost.repType}">
                            <f:selectItems value="#{SecurityIntPost.repTypeList}"/> 
                            <a4j:support action="#{SecurityIntPost.optTypeLostFocus}" event="onblur" reRender="ddRepDesc,message"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblRepDesc" styleClass="label" value="Security Name"/>
                        <h:selectOneListbox id="ddRepDesc" size="1" styleClass="ddlist" value="#{SecurityIntPost.repDesc}">
                            <f:selectItems value="#{SecurityIntPost.repDescList}"/> 
                            <a4j:support action="#{SecurityIntPost.optDescLostFocus}" event="onblur" focus="ctrlNoDD" reRender="ctrlNoDD,message"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="ctrlType" styleClass="label" value="Control No."/>
                        <h:selectOneListbox id="ctrlNoDD" styleClass="ddlist" size="1" style="width: 220px" value="#{SecurityIntPost.ctrlNoDd}"> 
                            <f:selectItems value="#{SecurityIntPost.ctrlNoList}"/>
                            <a4j:support action="#{SecurityIntPost.ctrlNoLostFocus}" event="onblur" reRender="frDt,toDt,message" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="repTypeGrid1" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row2" width="100%">
                        <h:outputLabel styleClass="label" value="From Date"/>
                        <h:inputText id="frDt" value="#{SecurityIntPost.frmDt}" maxlength="10" styleClass="input frDt" style="setMask();width:70px;" disabled="true"/>
                        <h:outputLabel value="To Date" styleClass="label"/>
                        <h:inputText id="toDt" value="#{SecurityIntPost.toDt}" maxlength="10" styleClass="input toDt" style="width:70px;setMask();" disabled="true"/>
                    </h:panelGrid>
                    <h:panelGrid id="debitAcnoGrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputLabel id="lblDebitAcno" styleClass="label" value="Debit A/c No."></h:outputLabel>
                        <h:outputText id="stxtDebitAcno" styleClass="output" value="#{SecurityIntPost.drAcno}"/>
                        <h:outputText id="stxtDebitAcnoDesc" styleClass="output" value="#{SecurityIntPost.drHeadDesc}"/>
                        <h:outputLabel id="lblDebitAmt" styleClass="label" value="Total Debit Amount"></h:outputLabel>
                        <h:outputText id="stxtDebitAmt" styleClass="output" value="#{SecurityIntPost.drAmt}"/>
                    </h:panelGrid>
                    <h:panelGrid id="creditAcnoGrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%">
                        <h:outputLabel id="lblCreditAcno" styleClass="label" value="Credit A/c No."></h:outputLabel>
                        <h:outputText id="stxtCreditAcno" styleClass="output" value="#{SecurityIntPost.crAcno}"/>
                        <h:outputText id="stxtCreditAcnoDesc" styleClass="output" value="#{SecurityIntPost.crHeadDesc}"/>
                        <h:outputLabel id="lblCreditAmt" styleClass="label" value="Total Credit Amount (Bal Days)"></h:outputLabel>
                        <h:inputText id="stxtCreditAmt" value="#{SecurityIntPost.crAmt}" styleClass="input">
                            <a4j:support action="#{SecurityIntPost.onBlurValAction}" event="onblur" reRender="stxtMsg,stxtDebitAmt"/>
                        </h:inputText>                        
                    </h:panelGrid>
                    <h:panelGrid id="accrAcnoGrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputLabel id="lblAccrAcno" styleClass="label" value="Accured A/c No."></h:outputLabel>
                        <h:outputText id="stxtAccrAcno" styleClass="output" value="#{SecurityIntPost.accrAcno}"/>
                        <h:outputText id="stxtAccrAcnoDesc" styleClass="output" value="#{SecurityIntPost.accrHeadDesc}"/>
                        <h:outputLabel id="lblAccrAmt" styleClass="label" value="Total Credit Amount (Posted)"></h:outputLabel>
                        <h:outputText id="stxtAccrAmt" styleClass="output" value="#{SecurityIntPost.accrAmt}"/>
                    </h:panelGrid>
                    <h:panelGrid id="remarkIdGrid" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row2" width="100%">
                        <h:outputLabel id="lblRemark" styleClass="label" value="Remark"/>
                        <h:inputText id="stxtRemark" styleClass="output" style="width: 220px" value="#{SecurityIntPost.remark}"/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnCal" action="#{SecurityIntPost.calTxn}" value="Calculate" oncomplete="if(#{SecurityIntPost.calcFlag==true}){
                                               #{rich:component('popUpRepPanel')}.show();
                                               }
                                               else if(#{SecurityIntPost.calcFlag==false})
                                               {
                                               #{rich:component('popUpRepPanel')}.hide();
                                               }" reRender="message,debitAcnoGrid,creditAcnoGrid,accrAcnoGrid,popUpRepPanel,btnPost"/>
                            <a4j:commandButton id="btnPost" disabled="#{SecurityIntPost.visiblePost}" action="#{SecurityIntPost.postTxn}" value="Post" reRender="message"/>
                            <a4j:commandButton id="btnRefresh" action="#{SecurityIntPost.ClearAll}" value="Refresh" reRender="mainPanel"/>
                            <a4j:commandButton id="btnExit" action="#{SecurityIntPost.exitButton}" value="Exit"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="popUpRepPanel" label="Form" width="1000" height="550" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpRepPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Security Interest Posting" style="text-align:center;"/>
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
                                <a4j:include viewId="#{SecurityIntPost.viewID}" />
                            </td>

                        </tr>
                    </tbody>
                </table>
            </rich:modalPanel>
        </body>
    </html>
</f:view>