<%-- 
    Document   : tdReceiptIntDetail
    Created on : Mar 4, 2014, 12:01:54 PM
    Author     : Athar Reza
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
            <title>Td Receipt Detail Report</title>
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
            <a4j:form id="tdReceipt">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{TdReceiptIntDetail.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Td Receipt Detail Report  "/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{TdReceiptIntDetail.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row1" style="height:30px;text-align:center;" width="100%">
                        <h:outputLabel id="message" styleClass="error" style="color:red;" value="#{TdReceiptIntDetail.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel2" columns="6" columnClasses="col1,col3,col3,col3,col3,col3" width="100%" styleClass="row2">
                        <h:outputLabel id="lblreportTtpe" styleClass="label"  value="Report Type"/>
                        <h:selectOneListbox id="reportTtpeid" value="#{TdReceiptIntDetail.repType}" styleClass="ddlist" size="1"  style="width:100px">
                            <f:selectItems value="#{TdReceiptIntDetail.repTypeList}" />
                            <a4j:support  action="#{TdReceiptIntDetail.getdisableAccountNo}" event="onchange"
                                          reRender="txtAccountNo,receiptid,newAcNo,message,frDt,toDt,ddlacType,optionId,tdsOptionId,btnInterestCertificate,actCatgId,gridPanel7" focus="#{TdReceiptIntDetail.focusId}" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                        <h:outputLabel value="Branch" styleClass="label"/>
                        <h:selectOneListbox id="branchddl" size="1" value="#{TdReceiptIntDetail.branch}" styleClass="ddlist">
                            <f:selectItems value="#{TdReceiptIntDetail.branchList}" />
                        </h:selectOneListbox>
                        <h:outputText value="A/c. Type :" styleClass="label" />
                        <h:selectOneListbox id="ddlacType" size="1" styleClass="ddlist" value="#{TdReceiptIntDetail.acType}" disabled="#{TdReceiptIntDetail.disableAcType}">
                            <f:selectItems value="#{TdReceiptIntDetail.acTypeList}"/>
                        </h:selectOneListbox>
                        <%--</h:panelGrid>
                        <h:panelGrid id="gridPanel3" columns="4"  columnClasses="col13,col13,col13,col13"  width="100%" styleClass="row1">--%>

                        <%--h:outputLabel id="lblbranchCodeList" styleClass="label"  value="Recepit Wise"  />
                        <h:selectOneListbox id="receiptid" value="#{TdReceiptIntDetail.receiptNo}" styleClass="ddlist" size="1"  style="width:70px" disabled="#{TdReceiptIntDetail.disableAcctNo}">
                            <f:selectItems value="#{TdReceiptIntDetail.receiptNoList}" />
                        </h:selectOneListbox--%>
                    </h:panelGrid>                    
                    <h:panelGrid id="gridPanel4" columns="6"  columnClasses="col1,col3,col3,col3,col3,col3"  width="100%" styleClass="row1">
                        <h:outputLabel value="Customer Id" styleClass="label"/>
                        <h:panelGroup layout="block">
                            <h:inputText id="txtAccountNo" styleClass="input" style="width:100px" value="#{TdReceiptIntDetail.acNo}" onkeyup="this.value = this.value.toUpperCase();" maxlength="12" disabled="#{TdReceiptIntDetail.disableAcctNo}">
                                <a4j:support event="onblur" actionListener="#{TdReceiptIntDetail.onBlurAccountNumber}" reRender="receiptid,message,newAcNo" oncomplete="setMask();" focus="frDt"/>
                            </h:inputText>
                            <%--h:outputText id="newAcNo" value="#{TdReceiptIntDetail.newAcNo}" styleClass="output" style="color:green"/--%>
                        </h:panelGroup>
                        <h:outputLabel value="From Date" styleClass="label"/>
                        <h:inputText id="frDt" styleClass="input frDt" maxlength="10" value="#{TdReceiptIntDetail.frmDt}" style="width:70px;setMask();"/>
                        <h:outputLabel value="To Date" styleClass="label"/>
                        <h:inputText id="toDt" styleClass="input toDt" maxlength="10" value="#{TdReceiptIntDetail.toDt}" style="width:70px;setMask();"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel5" columns="6"  columnClasses="col1,col3,col3,col3,col3,col3" width="100%" styleClass="row2">
                        <h:outputLabel value="TDS Option" styleClass="label"/>
                        <h:selectOneListbox id="tdsOptionId" value="#{TdReceiptIntDetail.tdsOption}" styleClass="ddlist" size="1"  style="width:100px"  disabled="#{TdReceiptIntDetail.disableTdsOption}">
                            <f:selectItems value="#{TdReceiptIntDetail.tdsOptionList}" />
                            <a4j:support  action="#{TdReceiptIntDetail.getOnBlurTdsOption}" event="onchange"
                                          reRender="recFrDt, recToDt,btnInterestCertificate" focus="optionId" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                        <h:outputLabel value="Option" styleClass="label"/>
                        <h:selectOneListbox id="optionId" value="#{TdReceiptIntDetail.option}" styleClass="ddlist" size="1"  style="width:70px" disabled="#{TdReceiptIntDetail.disableOption}">
                            <f:selectItems value="#{TdReceiptIntDetail.optionList}" />                            
                        </h:selectOneListbox>
                        <h:outputLabel value="Account Category" styleClass="label"/>
                        <h:selectOneListbox id="actCatgId" value="#{TdReceiptIntDetail.actCatgory}" styleClass="ddlist" size="1"  style="width:70px" disabled="#{TdReceiptIntDetail.disableActCatg}">
                            <f:selectItems value="#{TdReceiptIntDetail.actCatgoryList}" />                            
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel6" columns="6"  columnClasses="col1,col3,col3,col3,col3,col3" width="100%" styleClass="row1">
                        <h:outputLabel value="Recovered From Date" styleClass="label"/>
                        <h:inputText id="recFrDt" styleClass="input frDt" maxlength="10" value="#{TdReceiptIntDetail.recFrmDt}"  style="width:70px;setMask();"  disabled="#{TdReceiptIntDetail.disableRecFrmDt}"/>
                        <h:outputLabel value="Recover To Date" styleClass="label"/>
                        <h:inputText id="recToDt" styleClass="input toDt" maxlength="10" value="#{TdReceiptIntDetail.recToDt}" style="width:70px;setMask();"  disabled="#{TdReceiptIntDetail.disableRecToDt}"/>
                        <h:outputLabel/><h:outputLabel/><h:outputLabel/><h:outputLabel/>
                    </h:panelGrid>
                        <h:panelGrid id="gridPanel7" columns="6"  columnClasses="col1,col3,col3,col3,col3,col3" width="100%" styleClass="row1">
                            <h:outputLabel value="Interest From Amount" id="intFrAmt" styleClass="label" style="display:#{TdReceiptIntDetail.viewFrmAmt}"/>
                         <h:inputText id="txtintFrAmt" value="#{TdReceiptIntDetail.intFrmAmt}" maxlength="10"
                                      styleClass="input" style="width:140px;display:#{TdReceiptIntDetail.viewFrmAmt}">
                                  <a4j:support event="onblur"  focus="txtIntToAmt"  />
                            </h:inputText>
                                      
                            <h:outputLabel value="Interest To Amount" styleClass="label" id="IntToAmt" style="display:#{TdReceiptIntDetail.viewToAmt}"/>
                        <h:inputText id="txtIntToAmt" styleClass="interest toAmt" maxlength="10" value="#{TdReceiptIntDetail.intToAmt}" 
                                     style="width:100px;display:#{TdReceiptIntDetail.viewToAmt};setMask();"> 
                            <a4j:support event="onblur"  focus="btnPrint" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel/>
                        <h:outputLabel/><h:outputLabel/><h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnPrint" value="Print Report" action="#{TdReceiptIntDetail.viewReport}" reRender="message"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{TdReceiptIntDetail.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <h:commandButton id="btnInterestCertificate"  value="Interest Certificate" disabled="#{TdReceiptIntDetail.disableFlag}" action="#{TdReceiptIntDetail.interestCertificate}" styleClass="color:#044F93;text-decoration:none;" />
                            <a4j:commandButton action="#{TdReceiptIntDetail.btnRefreshAction}" id="btnRefresh" value="Refresh" reRender="mainPanel" oncomplete="setMask();"/>
                            <a4j:commandButton action="#{TdReceiptIntDetail.btnExitAction}" id="btnExit" value="Exit" reRender="mainPanel" oncomplete="setMask();"/>
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
        </body>
    </html>
</f:view>

