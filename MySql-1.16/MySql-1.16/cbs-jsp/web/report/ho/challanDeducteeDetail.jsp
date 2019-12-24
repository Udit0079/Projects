<%-- 
    Document   : challanDeducteeDetail
    Created on : 21 Oct, 2017, 11:55:45 AM
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".calInstDate").mask("39/19/9999");
                }
            </script>
            <title>Challan/Deductee</title>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1"  style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="headerPanel" columns="3"  style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup>
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{ChallanDeducteeDetail.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="Challan/Deductee"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ChallanDeducteeDetail.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="messagePanel" columnClasses="col8,col8" columns="2" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                        <h:outputText id="stxtMessage" styleClass="error" value="#{ChallanDeducteeDetail.errorMessage}"/>
                    </h:panelGrid>
                    <h:panelGrid id="placeOfWorkingGrid" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row2" width="100%">
                      <h:outputText value="Branch Wise" styleClass="label"/>
                      <h:selectOneListbox id="branch" size="1" value="#{ChallanDeducteeDetail.branchCode}" styleClass="ddlist">
                          <f:selectItems value="#{ChallanDeducteeDetail.branchCodeList}" />
                      </h:selectOneListbox> 
                        <h:outputLabel id="lblPlaceOfWorking" styleClass="label" value="TDS OPTION"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddPlaceOfWorking" styleClass="ddlist" size="1" value="#{ChallanDeducteeDetail.tdsOption}">
                            <f:selectItems value="#{ChallanDeducteeDetail.tdsOptionList}"/>
                            <a4j:support action="#{ChallanDeducteeDetail.setMonthEndQuarEnd}" event="onchange"reRender="ddMonth" />
                        </h:selectOneListbox>
                        <h:outputLabel id="lblMonth" styleClass="label"  value="MONTH"><font class="required" color="red">*</font></h:outputLabel>
                        <h:panelGroup>
                            <h:selectOneListbox id="ddMonth" styleClass="ddlist" size="1" style="width: 70px" value="#{ChallanDeducteeDetail.month}">
                                <f:selectItems value="#{ChallanDeducteeDetail.monthList}"/>
                            </h:selectOneListbox>
                            <h:inputText id="txtYear" styleClass="input" value="#{ChallanDeducteeDetail.year}" maxlength="4" size="5"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="placeOfBsrGrid" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblFinYear" styleClass="label"  value="Financial Year"><font class="required" color="red">*</font></h:outputLabel>
                        <h:panelGroup id="finanId">
                            <h:inputText id="txtFinan" value="#{ChallanDeducteeDetail.finYear}" styleClass="input" size="8" maxlength="4">
                                <a4j:support event="onblur" actionListener="#{ChallanDeducteeDetail.getNewfYear}" reRender="newFinan,stxtMessage" oncomplete="setMask();"/>
                            </h:inputText>
                            <h:outputText id="newFinan" value="#{ChallanDeducteeDetail.newFinYear}" styleClass="output" style="color:green"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblbsrCode" styleClass="label" value="BSR CODE"  />
                        <h:inputText id="stxtbsrCode" styleClass="input" style="width:80px;" value="#{ChallanDeducteeDetail.bsrCode}"/>
                        <h:outputLabel id="lbltotalAmt" styleClass="label" value="TOTAL AMOUNT"  />
                        <h:inputText id="stxttotalAmt" styleClass="input" style="width:80px;" value="#{ChallanDeducteeDetail.totalAmt}"/>
                    </h:panelGrid> 
                    <h:panelGrid id="placeOfSrNoGrid" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblSubmmissionDate" styleClass="label" value="SUBMISSION DATE" > </h:outputLabel>  
                        <h:inputText id="stxtsubDate" styleClass="input calInstDate" style="width:80px;" value="#{ChallanDeducteeDetail.submmitDt}">
                            <a4j:support oncomplete="setMask();"/>
                        </h:inputText> 
                        <h:outputLabel id="lblSrNo" styleClass="label" value="CHALLAN SERIAL NO."  />
                        <h:inputText id="stxtSrNo" styleClass="input" style="width:80px;" value="#{ChallanDeducteeDetail.srNo}"/>
                        <h:outputLabel id="lblVoucherNo" styleClass="label" value="VOUCHER/ RECEIPT NO."  />
                        <h:inputText id="stxtVoucherNo" styleClass="input" style="width:80px;" value="#{ChallanDeducteeDetail.voucherNo}"/>   
                    </h:panelGrid>
                    <h:panelGrid id="placeOfIntGrid" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row2" width="100%">
                         <h:outputLabel id="lblCustType" styleClass="label" value="CUST.TYPE" ><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddtCustType" styleClass="ddlist" size="1" style="width:80px;" value="#{ChallanDeducteeDetail.custType}">
                            <f:selectItems value="#{ChallanDeducteeDetail.custTypeList}"/> 
                        </h:selectOneListbox>
                        <h:outputLabel id="lblSurchg" styleClass="label" value="SURCHARGE"  />
                        <h:inputText id="stxtSurchg" styleClass="input" style="width:80px;" value="#{ChallanDeducteeDetail.surCharge}"/>
                        <h:outputLabel id="lbleduchee" styleClass="label" value="EDU.CESS"  />
                        <h:inputText id="stxteduchee" styleClass="input" style="width:80px;" value="#{ChallanDeducteeDetail.eduCess}"/>    
                    </h:panelGrid>
                    <h:panelGrid id="placeOffeeGrid" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblintAmt" styleClass="label" value="INTEREST"  />
                        <h:inputText id="stxtintAmt" styleClass="input" style="width:80px;" value="#{ChallanDeducteeDetail.interest}"/> 
                        <h:outputLabel id="lblfee" styleClass="label" value="FEE"  />
                        <h:inputText id="stxtfee" styleClass="input" style="width:80px;" value="#{ChallanDeducteeDetail.fee}"/>
                        <h:outputLabel id="lblpenaltyOthers" styleClass="label" value="PENALTY.OTHERS"  />
                        <h:inputText id="stxtpenaltyOthers" styleClass="input" style="width:80px;" value="#{ChallanDeducteeDetail.pentalyOther}"/> 
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel7" styleClass="row1" style="display:#{ChallanDeducteeDetail.hyperPanel}">
                        <h:outputLabel/>
                        <h:commandLink id="challan" value="CHALLAN.xls" actionListener="#{ChallanDeducteeDetail.printReport('CHALLAN')}"/>
                        <h:outputLabel/>
                        <h:commandLink id="deductee" value="DEDUCTEE DETAIL.xls" actionListener="#{ChallanDeducteeDetail.printReport('DEDUCTEE')}"/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <%--h:panelGrid id="fileLinkGrid" columns="2" style="width:100%;height:30px;text-align:center;" styleClass="row1" rendered="#{ChallanDeducteeDetail.excelLink}">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandLink id="excelSaveLink" value="#{ChallanDeducteeDetail.demandFileName}" style="color:blue;" styleClass="headerLabel" action="#{ChallanDeducteeDetail.downloadExcel}"/>
                        </h:panelGroup>
                    </h:panelGrid--%>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnHtml" value="Excel Download" oncomplete="#{rich:component('processPanel')}.show();" reRender="stxtMessage,processPanel,gridPanel7"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{ChallanDeducteeDetail.btnRefreshAction}" oncomplete="setMask()" reRender="mainPanel"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{ChallanDeducteeDetail.btnExitAction}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="btnActionMpanal"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" 
                             style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
            <a4j:region id="btnActionMpanal">
                <rich:modalPanel id="processPanel" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText id="confirmid" value="Are you sure to generate the file ?"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="btnYes" action="#{ChallanDeducteeDetail.processAction}" 
                                                           oncomplete="#{rich:component('processPanel')}.hide(); #{rich:element('ddPlaceOfWorking')}.focus();" 
                                                           reRender="stxtMessage,mainPanel"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('processPanel')}.hide();"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
            </a4j:region>
        </body>
    </html>
</f:view>
