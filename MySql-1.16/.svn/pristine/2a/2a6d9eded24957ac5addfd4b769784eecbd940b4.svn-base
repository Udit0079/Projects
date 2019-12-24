<%-- 
    Document   : DemandMarkAndPost
    Created on : Jan 12, 2016, 2:34:04 PM
    Author     : Administrator
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
            <title>Demand Generate And Posting</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".calDate").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="txnForm">
                <h:panelGrid bgcolor="#fff" columns="1" id="outerPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="headerPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{DemandMarkAndPost.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Demand Generate And Posting"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{DemandMarkAndPost.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="mainPanel" style="width:100%;text-align:center;">
                        <h:panelGrid columnClasses="col2,col7" columns="2" id="msgRow" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputLabel id="lblMsg" styleClass="error" value="#{DemandMarkAndPost.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1,col22,col1,col22" columns="4" id="gridPanel1" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel value="Activity Option " styleClass="label"/>
                            <h:selectOneListbox id="ddActivityOpt" styleClass="ddlist" value="#{DemandMarkAndPost.actType}" disabled="#{DemandMarkAndPost.disableActFn}" size="1">
                                <f:selectItems value="#{DemandMarkAndPost.actList}"/>
                                <a4j:support event="onblur" action="#{DemandMarkAndPost.eventAction}" oncomplete="setMask();" reRender="lblMsg,demandPanelGrid,ddAccountId,ddDemandId,btnGen,btnPDF,btnPost" focus="ddOffId"/>
                            </h:selectOneListbox>
                            <h:outputLabel value="Office Id " styleClass="label"/>
                            <h:selectOneListbox id="ddOffId" styleClass="ddlist" value="#{DemandMarkAndPost.offId}" disabled="#{DemandMarkAndPost.disableOffId}" size="1" style="width:300px">
                                <f:selectItems value="#{DemandMarkAndPost.offIdList}"/>
                                <a4j:support event="onblur" action="#{DemandMarkAndPost.getAcctIdData}" 
                                             oncomplete="if(#{rich:element('ddActivityOpt')}.value=='G'){#{rich:element('ddAccountId')}.focus();}
                                             else if(#{rich:element('ddActivityOpt')}.value=='P'){#{rich:element('ddDemandId')}.focus();} setMask();" 
                                             reRender="lblMsg,gridPanel2,gridPanel3"/>
                            </h:selectOneListbox>                            
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1,col22,col1,col22" columns="4" id="gridPanel2" style="width:100%;text-align:left;" styleClass="row1">
                            <h:panelGroup layout="block">
                                <h:outputLabel id="lblAccountId" styleClass="label" value="Department Id" style="display:#{DemandMarkAndPost.acctIdStyle}"/>
                                <h:outputLabel id="lblDemandId" styleClass="label" value="Demand Id" style="display:#{DemandMarkAndPost.demandIdStyle}"/>
                            </h:panelGroup>
                            <h:panelGroup layout="block">
                                <h:selectOneListbox id="ddAccountId" styleClass="ddlist" size="1" style="display:#{DemandMarkAndPost.acctIdStyle};width:100px;" value="#{DemandMarkAndPost.acctId}" disabled="#{DemandMarkAndPost.disableAcctId}">
                                    <f:selectItems value="#{DemandMarkAndPost.accountIdList}"/>
                                    <a4j:support event="onblur" focus="ddMonthYear" oncomplete="setMask();"/>
                                </h:selectOneListbox>
                                <h:selectOneListbox id="ddDemandId" styleClass="ddlist" size="1" style="display:#{DemandMarkAndPost.demandIdStyle};width:100px;" value="#{DemandMarkAndPost.demandId}" disabled="#{DemandMarkAndPost.disableDemId}">
                                    <f:selectItems value="#{DemandMarkAndPost.demandIdList}"/>
                                    <a4j:support event="onblur" action="#{DemandMarkAndPost.demandAction}" oncomplete="setMask();" reRender="lblMsg,demandPanelGrid,stxtDemandAmt" focus="ddRecoveryOpt"/>
                                </h:selectOneListbox>
                            </h:panelGroup>
                            <h:outputLabel id="lblMonth" styleClass="label" value = "Month / Year"/>
                            <h:panelGroup id = "bind">
                                <h:selectOneListbox id="ddMonthYear" styleClass="ddlist" size="1" style="width:80px"  value="#{DemandMarkAndPost.month}" disabled="#{DemandMarkAndPost.disableMonth}">
                                    <f:selectItems value="#{DemandMarkAndPost.monthYearList}"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblseperatar" styleClass="label" value = " / "/>
                                <h:inputText id="txtYear" styleClass="input" style="width:70px" value="#{DemandMarkAndPost.year}" disabled="#{DemandMarkAndPost.disableYear}"/>
                            </h:panelGroup>                            
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1,col22,col1,col22" columns="4" id="gridPanel3" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel value="Recovery Type " styleClass="label"/>
                            <h:selectOneListbox id="ddRecoveryOpt" styleClass="ddlist" value="#{DemandMarkAndPost.recoveryType}" size="1" disabled="#{DemandMarkAndPost.disableRecTp}">
                                <f:selectItems value="#{DemandMarkAndPost.recoveryList}"/>
                                <a4j:support event="onblur" action="#{DemandMarkAndPost.recoveryOptAction}" oncomplete="setMask();" reRender="lblMsg,mainPanel,footerPanel" focus="txtaccountNo"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblAcNo" styleClass="label" value="Account No."/>
                            <h:inputText id="txtaccountNo" value="#{DemandMarkAndPost.acctNo}" maxlength="#{DemandMarkAndPost.acNoMaxLen}" styleClass="input" style="width:90px" disabled="#{DemandMarkAndPost.disableAccNo}">
                                <%--a4j:support event="onblur" action="#{DemandMarkAndPost.accNoAction}" oncomplete="if(#{DemandMarkAndPost.acFlag=='C'}){
                                               #{rich:element('txtCheNo')}.focus();
                                               }
                                               else if(#{DemandMarkAndPost.acFlag=='S'}) {#{rich:element('orgDate')}.focus();}" 
                                               reRender="lblMsg,mainPanel,footerPanel"/--%>
                                <a4j:support event="onblur" action="#{DemandMarkAndPost.accNoAction}" oncomplete="if(#{DemandMarkAndPost.acFlag=='C'}){
                                               #{rich:element('txtCheNo')}.focus();
                                               }setMask();" 
                                               reRender="lblMsg,mainPanel,footerPanel"/>
                            </h:inputText>                            
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1,col22,col1,col22" columns="4" id="gridPanel4" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="label19" styleClass="label" value="Cheque No."/>
                            <h:inputText id="txtCheNo" styleClass="input" style="width:110px" value="#{DemandMarkAndPost.chqNo}" disabled="#{DemandMarkAndPost.chqNoFlag}" maxlength="10"/>
                            <h:outputLabel id="label20" styleClass="label" value="Cheque Date"/>
                            <h:inputText id="chkDt" value="#{DemandMarkAndPost.chqueDate}" disabled="#{DemandMarkAndPost.chqDateFlag}" size="10"
                                              styleClass="input calDate">
                                    <f:convertDateTime pattern="dd/MM/yyyy" />
                                    <a4j:support event="onblur" actionListener="#{DemandMarkAndPost.selectChqDate}" oncomplete="setMask();"
                                                 reRender="lblMsg,mainPanel,footerPanel"/>
                            </h:inputText>                            
                        </h:panelGrid>
                        <%--h:panelGrid columnClasses="col1,col22,col1,col22" columns="4" id="gridPanel5" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel value="Origin Date " styleClass="label"/>
                            <h:inputText id="orgDate" styleClass="input calDate" value="#{DemandMarkAndPost.orgDate}" size="10" disabled="#{DemandMarkAndPost.disableOrgDt}">
                                <f:convertDateTime pattern="dd/MM/yyyy"/>                                
                            </h:inputText>
                            <h:outputLabel id="lblSeqNo" styleClass="label" value="Seq No."/>
                            <h:inputText id="txtSeqNo" value="#{DemandMarkAndPost.seqNo}" maxlength="12" styleClass="input" style="width:90px" disabled="#{DemandMarkAndPost.disableSeqNo}"/>
                        </h:panelGrid--%>
                        <h:panelGrid columnClasses="col1,col22,col1,col22" columns="4" id="gridPanel6" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel value="Demand Total" styleClass="label"/>
                            <h:outputText id="stxtDemandAmt" styleClass="output" value="#{DemandMarkAndPost.totAmt}"/>
                            <h:outputLabel/>
                            <h:outputLabel/>                            
                        </h:panelGrid>
                    </h:panelGrid>
                    
                    <h:panelGrid columnClasses="col1,col2" columns="1" id="demandPanelGrid" width="100%" styleClass="row1" style="display:#{DemandMarkAndPost.gridStyle};height:50%">
                        <a4j:region>
                            <rich:dataTable value ="#{DemandMarkAndPost.tableList}"
                                    rowClasses="row1, row2" id = "taskList" rows="6" columnsWidth="100" rowKeyVar="row" var ="item"
                                    onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                    onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="9"><h:outputText value="Demand Details"/></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="S.No"/> </rich:column>
                                        <rich:column><h:outputText value="Account No." /></rich:column>
                                        <rich:column><h:outputText value="Customer Name" /></rich:column>
                                        <rich:column><h:outputText value="Account Description" /></rich:column>
                                        <rich:column><h:outputText value="Schedule No." /></rich:column>
                                        <rich:column><h:outputText value="Demand Date" /></rich:column>
                                        <rich:column><h:outputText value="Demand Eff. Date" /></rich:column>
                                        <rich:column><h:outputText value="Demand Amt" /></rich:column>
                                        <rich:column><h:outputText value="Demand Amt"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{item.sNo}"/></rich:column>
                                <rich:column><h:outputText value="#{item.acNo}"/></rich:column>
                                <rich:column><h:outputText value="#{item.name}"/></rich:column>
                                <rich:column style="text-align:right;"><h:outputText value="#{item.acTypeDesc}"/></rich:column>
                                <rich:column style="text-align:right;"><h:outputText value="#{item.schdlNo}"/></rich:column>
                                <rich:column style="text-align:right;"><h:outputText value="#{item.date}"/></rich:column>
                                <rich:column style="text-align:right;"><h:outputText value="#{item.demEffDt}"/></rich:column>
                                <rich:column style="text-align:right;"><h:outputText value="#{item.installment}"/></rich:column>
                                <rich:column>
                                    <h:inputText size="14" value="#{item.demandAmt}" style="text-align:right;">
                                        <a4j:support event="onblur" action="#{DemandMarkAndPost.gridAmtAction}" reRender="lblMsg,stxtDemandAmt">
                                            <f:setPropertyActionListener value="#{item}" target="#{DemandMarkAndPost.currentItem}"/>
                                        </a4j:support>
                                    </h:inputText>
                                </rich:column>                                
                            </rich:dataTable>
                        </a4j:region>
                        <rich:datascroller align="left" for="taskList" maxPages="20"/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnGen" value="Mark" action="#{DemandMarkAndPost.btnMarkAction}" disabled="#{DemandMarkAndPost.disableMark}" reRender="lblMsg,mainPanel"/>
                            <a4j:commandButton id="btnPDF" value="Print"  disabled="#{DemandMarkAndPost.disablePrn}"
                                action="#{DemandMarkAndPost.btnHtmlAction}" oncomplete="if(#{DemandMarkAndPost.calcFlag==true}){
                                               #{rich:component('popUpRepPanel')}.show();
                                               }
                                               else if(#{DemandMarkAndPost.calcFlag==false})
                                               {
                                               #{rich:component('popUpRepPanel')}.hide();
                                               }" reRender="lblMsg,popUpRepPanel,ddOffId,ddAccountId,ddMonthYear,ddActivityOpt,txtYear,footerPanel"
                            />
                            <a4j:commandButton id="btnPost" value="Post" disabled="#{DemandMarkAndPost.disablePost}" onclick="#{rich:component('postPanel')}.show()"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{DemandMarkAndPost.refresh}" reRender="lblMsg,mainPanel,footerPanel,demandPanelGrid"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{DemandMarkAndPost.exitForm}" reRender="lblMsg,mainPanel,footerPanel"/>                            
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
            <rich:modalPanel id="postPanel" autosized="true" width="200" onshow="#{rich:element('btnYes')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirm Dialog ?" style="padding-right:15px;" />
                </f:facet>
                <a4j:region id="postRegion">
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px;">
                                    <td colspan="2">
                                        <h:outputText value="Are You Sure To Post Data?" styleClass="output"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="btnYes" ajaxSingle="true" action="#{DemandMarkAndPost.Post}"
                                                    onclick="#{rich:component('postPanel')}.hide();" reRender="lblMsg,demandPanelGrid,mainPanel,footerPanel"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Cancel"  ajaxSingle="true" onclick="#{rich:component('postPanel')}.hide();return false;"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </a4j:region>
            </rich:modalPanel>
            <rich:modalPanel id="popUpRepPanel" label="Form" width="1000" height="550" resizeable="true" moveable="false" >
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Office Id Wise EMI Detail Report" style="text-align:center;"/>
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
                                <a4j:include viewId="#{DemandMarkAndPost.viewID}" />
                            </td>
                        </tr>
                    </tbody>
                </table>
            </rich:modalPanel>
        </body>
    </html>
</f:view>