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
            <link rel="stylesheet" href="/cbs-jsp/resources/txn_style.css" type="text/css"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Inward Authorization</title>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{InwardAuthorization.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Inward Authorization"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{InwardAuthorization.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="image" columns="2" columnClasses="col11,col10" width="100%" style="height:270px;vertical-align:top;display:none;" styleClass="row1">
                        <h:panelGrid id="sigGrid" columns="1" style="width:100%;text-align:right;" columnClasses="vtop">
                            <a4j:mediaOutput id="signature" style="width:400px;height:270px;"element="img" 
                                             createContent="#{InwardAuthorization.createSignature}" 
                                             value="#{InwardAuthorization.gridAcno}"/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel1" columns="2" columnClasses="col10,col11" width="100%" styleClass="row2">
                        <h:panelGrid  columnClasses="col17,col2" columns="2" id="gridpanel3" style="height:30px;"  width="100%">
                            <h:outputLabel id="lblBranch" styleClass="label" value="Branch Name"><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddBranchName" styleClass="ddlist" size="1" style="width:25%" value="#{InwardAuthorization.branchName}">
                                    <f:selectItems value="#{InwardAuthorization.branchNameList}"/>
                                    <a4j:support event="onblur" action="#{InwardAuthorization.dataInGrid}" reRender="errmsg,datalist,sId"/>
                                </h:selectOneListbox>
                            </h:panelGrid>
                            <h:panelGrid  columnClasses="col17,col2" columns="2" id="gridpanel5" style="height:30px;" width="100%">
                                <h:outputLabel id="lblReturnReason" styleClass="label" value="Returned Reason"/>
                                <h:selectOneListbox id="ddReason" styleClass="ddlist" size="1" style="width:25%" value="#{InwardAuthorization.reasonType}" disabled="#{InwardAuthorization.reasonEnable}">
                                    <f:selectItems value="#{InwardAuthorization.reasonOption}"/>
                                    <a4j:support event="onblur"/>
                                </h:selectOneListbox>
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid id="gridpanel55" columns="2" columnClasses="col10,col11" width="100%" style="height:30px;display:#{InwardAuthorization.compAmountDetail};" styleClass="row1">
                            <h:panelGrid  columnClasses="col17,col2" columns="2" id="gridpanel33" style="height:30px;"  width="100%">
                                <h:outputLabel styleClass="label" value="Passed Amount" style=""/>
                                <h:outputText styleClass="output" value="#{InwardAuthorization.compPassedAmount}"/>
                            </h:panelGrid>
                            <h:panelGrid  columnClasses="col17,col2" columns="2" id="gridpanel56" style="height:30px;" width="100%">
                                <h:outputLabel styleClass="label" value="Returned Amount"/>
                                <h:outputText styleClass="output" value="#{InwardAuthorization.compReturnedAmount}"/>
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel15" width="100%" styleClass="row1" style="height:150px;">
                            <rich:dataTable  rows="3" value="#{InwardAuthorization.dataItemList}" var="dataItem" rowClasses="gridrow1, gridrow2" id="datalist"
                                             rowKeyVar="row" onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column width="30px;"><h:outputText value="Sr.No."/> </rich:column>
                                        <rich:column width="30px;"><h:outputText value="Vch No." /></rich:column>
                                        <rich:column width="70px;"><h:outputText value="A/c No." /></rich:column>
                                        <rich:column width="100px;"><h:outputText value="Cust Name" /></rich:column>
                                        <rich:column width="50px;"><h:outputText value="Inst.Amt" /></rich:column>
                                        <rich:column width="60px;"><h:outputText value="Inst.No" /></rich:column>
                                        <rich:column width="60px;"><h:outputText value="Inst.Date" /></rich:column>
                                        <rich:column width="100px;"><h:outputText value="Payee" /></rich:column>
                                        <rich:column width="80px;"><h:outputText value="Oper.Mode" /></rich:column>
                                        <rich:column width="50px;"><h:outputText value="Status" /></rich:column>
                                        <rich:column width="80px;"><h:outputText value="Enter By" /></rich:column>
                                        <rich:column width="120px;"><h:outputText value="Details" /></rich:column>
                                        <rich:column width="40px;"><h:outputText value="Pr.BankCode" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.sNo}" style="text-align:right"/></rich:column>
                                <rich:column><h:outputText  value="#{dataItem.vchNo}" style="text-align:right"/></rich:column>
                                <rich:column ><h:outputText  value="#{dataItem.acNo}" style="text-align:center"/></rich:column>
                                <rich:column ><h:outputText  value="#{dataItem.custName}" style="text-align:center"/></rich:column>
                                <rich:column >
                                    <h:outputText  value="#{dataItem.instAmt}" style="text-align:right">
                                        <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                    </h:outputText>
                                </rich:column>
                                <rich:column ><h:outputText  value="#{dataItem.instNo}" style="text-align:right"/></rich:column>
                                <rich:column ><h:outputText  value="#{dataItem.instDt}" style="text-align:right"/></rich:column>
                                <rich:column ><h:outputText  value="#{dataItem.payee}" style="text-align:left"/></rich:column>
                                <rich:column ><h:outputText  value="#{dataItem.operMode}" style="text-align:left"/></rich:column>
                                <rich:column >
                                    <h:outputText  value="#{dataItem.varStatus}" style="text-align:left"/>
                                    <a4j:support action="#{InwardAuthorization.changeStatus}" ajaxSingle="true" event="ondblclick" 
                                                 oncomplete="#{rich:element('image')}.style.display='';if(#{InwardAuthorization.odBalFlag==true}){#{rich:component('processPanel')}.show()}"
                                                 reRender="datalist,errormsg,btnPost,image,sigGrid,chqImage,signature,ddReason,processPanel,confirmid">
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{InwardAuthorization.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{InwardAuthorization.currentRow}"/>
                                    </a4j:support>
                                </rich:column>

                            <rich:column ><h:outputText  value="#{dataItem.enterBy}" style="text-align:left"/></rich:column>

                            <rich:column ><h:outputText  value="#{dataItem.details}" style="text-align:left"/></rich:column>
                            <rich:column ><h:outputText  value="#{dataItem.prBankCode}" style="text-align:right"/></rich:column>
                        </rich:dataTable>
                        <rich:datascroller  id="sId" align="left" for="datalist" maxPages="6"/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" columnClasses="col19,col20,col21" columns="3" style="width:100%;" styleClass="footer_left">
                        <h:panelGroup id="btnPanel1" styleClass="vtop">
                            <h:outputText id="stxtInstruction1" styleClass="output" value="F4-A/c View, F7-Signature" style="color:blue;"/>
                        </h:panelGroup>
                        <h:panelGroup id="btnPanel" styleClass="vtop">
                            <a4j:region id="waiting">
                                <a4j:commandButton id="btnPost" value="Post" action="#{InwardAuthorization.authorizeAction}" oncomplete="#{rich:element('image')}.style.display='none';" reRender="errmsg,datalist,image,ddReason,btnPost,btnComplete,gridpanel55" disabled="#{InwardAuthorization.postValue}"/>
                                <a4j:commandButton id="btnComplete" value="Complete" action="#{InwardAuthorization.actionToCompleteCTS}" reRender="errmsg,datalist,ddReason,btnPost,btnComplete" disabled="#{InwardAuthorization.completeValue}"/>
                            </a4j:region>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{InwardAuthorization.refreshForm}" oncomplete="#{rich:element('image')}.style.display='none';" reRender="errmsg,datalist,ddReason,btnPost,ddBranchName,gridpanel55,confirmid"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{InwardAuthorization.exitForm}" reRender="errmsg,datalist"/>
                        </h:panelGroup>
                        <h:panelGroup id="errormsg" styleClass="vtop">
                            <h:outputText id="errmsg" styleClass="output" value="#{InwardAuthorization.msg}" style="color:red;"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('waitPost')}.show()" onstop="#{rich:component('waitPost')}.hide()" for="waiting"/>
                <rich:modalPanel id="waitPost" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
                <rich:modalPanel id="acView" height="560" width="900" left="true">
                    <f:facet name="header">
                        <h:outputText value="Account Detail"/>
                    </f:facet>
                    <f:facet name="controls">
                        <h:panelGroup>
                            <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="closelink"/>
                            <rich:componentControl for="acView" attachTo="closelink" operation="hide" event="onclick"/>
                        </h:panelGroup>
                    </f:facet>
                    <a4j:form>
                        <h:panelGrid id="acViewPanel" width="100%">
                            <h:panelGrid id="acViewRow1" columns="6" columnClasses="col1,col2,col1,col2,col1,col1," width="100%" styleClass="row1" style="text-align:left;">                            <h:outputLabel id="lblAccNo" styleClass="label" value="Account No."/>
                                <h:outputText id="txtAccNo" style="output" value="#{InwardAuthorization.gridAcno}"/>
                                <h:outputLabel id="lblOpeningBal" styleClass="label" value="Opening Balance:"/>
                                <h:outputText id="txtOpeningBal" style="output" value="#{InwardAuthorization.openBalance}">
                                    <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                                </h:outputText>
                                <h:outputLabel id="lblPresentBal" styleClass="label" value="Present Balance:"/>
                                <h:outputText id="txtPresentBal" style="output" value="#{InwardAuthorization.presentBalance}">
                                    <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                                </h:outputText>
                            </h:panelGrid>

                            <h:panelGrid id="acViewAuthGrid" width="100%" style="background-color:#e8eef7;height:260px" columnClasses="vtop">
                                <rich:dataTable  var="txnItem" value="#{InwardAuthorization.txnDetailList}"
                                                 rowClasses="gridrow1, gridrow2" id="authTxnList" rows="8"
                                                 onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                 onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                                 width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="8"><h:outputText value="Account Details" /></rich:column>
                                            <rich:column breakBefore="true" style="width:"><h:outputText value="Date" /></rich:column>
                                            <rich:column><h:outputText value="Particulars" /></rich:column>
                                            <rich:column><h:outputText value="Cheque No." /></rich:column>
                                            <rich:column><h:outputText value="Withdrawl" /></rich:column>
                                            <rich:column><h:outputText value="Deposite" /></rich:column>
                                            <rich:column><h:outputText value="Balance" /></rich:column>
                                            <rich:column><h:outputText value="Entered By" /></rich:column>
                                            <rich:column><h:outputText value="Authorized" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{txnItem.dt}"/></rich:column>
                                    <rich:column><h:outputText value="#{txnItem.details}"/></rich:column>
                                    <rich:column><h:outputText value="#{txnItem.instNo}"/></rich:column>
                                    <rich:column><h:outputText value="#{txnItem.drAmt}">
                                            <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                                        </h:outputText>
                                    </rich:column>
                                    <rich:column><h:outputText value="#{txnItem.crAmt}">
                                            <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                                        </h:outputText>
                                    </rich:column>
                                    <rich:column><h:outputText value="#{txnItem.amount}">
                                            <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                                        </h:outputText>
                                    </rich:column>
                                    <rich:column><h:outputText value="#{txnItem.enterBy}"/></rich:column>
                                    <rich:column><h:outputText  value="#{txnItem.authBy}"/></rich:column>
                                </rich:dataTable>
                                <rich:datascroller align="left" for="authTxnList" maxPages="20" />
                            </h:panelGrid>


                            <h:panelGrid id="pendingTxnGrid" width="100%" style="background-color:#edebeb;height:200px;" columnClasses="vtop">
                                <rich:dataTable  var="unAuthItem" value="#{InwardAuthorization.txnDetailUnAuthList}"
                                                 rowClasses="gridrow1, gridrow2" id="pendingTxnList" rows="5"
                                                 onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                 onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                                 width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="8"><h:outputText value="Today's Pending Authorization" /></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="Date" /></rich:column>
                                            <rich:column><h:outputText value="Particulars" /></rich:column>
                                            <rich:column><h:outputText value="Cheque No." /></rich:column>
                                            <rich:column><h:outputText value="Withdrawl" /></rich:column>
                                            <rich:column><h:outputText value="Deposite" /></rich:column>
                                            <rich:column><h:outputText value="Balance" /></rich:column>
                                            <rich:column><h:outputText value="Entered By" /></rich:column>
                                            <rich:column><h:outputText value="Authorized" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{unAuthItem.dt}"/></rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.details}"/></rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.instNo}"/></rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.drAmt}">
                                            <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                                        </h:outputText>
                                    </rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.crAmt}">
                                            <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                                        </h:outputText>
                                    </rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.amount}">
                                            <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                                        </h:outputText>
                                    </rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.enterBy}"/></rich:column>
                                    <rich:column><h:outputText value="#{unAuthItem.authBy}"/></rich:column>
                                </rich:dataTable>
                                <rich:datascroller id="unauthDataScroll"align="left" for="pendingTxnList" maxPages="20" />
                            </h:panelGrid>
                        </h:panelGrid>

                        <h:panelGrid id="acViewFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup id="acViewBtnPanel">
                                <a4j:commandButton id="acViewClose" value="Close" onclick="#{rich:component('acView')}.hide(); return false;">
                                </a4j:commandButton>
                            </h:panelGroup>
                        </h:panelGrid>
                    </a4j:form>
                </rich:modalPanel>
                <rich:modalPanel id="sigView" height="530" width="700" style="align:right">
                    <f:facet name="header">
                        <h:outputText value="Signature Detail"/>
                    </f:facet>
                    <f:facet name="controls">
                        <h:panelGroup>
                            <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="closelink1"/>
                            <rich:componentControl for="sigView" attachTo="closelink1" operation="hide" event="onclick"/>
                        </h:panelGroup>
                    </f:facet>
                    <a4j:form>

                        <h:panelGrid id="sigViewRow1" columns="6" columnClasses="col1,col3,col3,col3,col3,col3" width="100%" styleClass="row2" style="text-align:left;">
                            <h:outputLabel id="lblAccNo" styleClass="label" value="Account No."/>
                            <h:outputText id="txtAcNo" style="output" value="#{InwardAuthorization.accNo}"/>
                            <h:outputLabel id="lblAcName" styleClass="label" value="Name"/>
                            <h:outputText id="txtAcName" style="output" value="#{InwardAuthorization.accountName}"/>
                            <h:outputLabel id="lblCustomerId" styleClass="label" value="Customer Id"/>
                            <h:outputText id="txtCustomerId" style="output" value="#{InwardAuthorization.custId}"/>
                        </h:panelGrid>

                        <h:panelGrid id="sigViewRow2" columns="6" columnClasses="col1,col3,col3,col3,col3,col3" width="100%" styleClass="row1" style="text-align:left;">
                            <h:outputLabel id="lblOpMode" styleClass="label" value="Operation Mode"/>
                            <h:outputText id="txtOpMode" style="output" value="#{InwardAuthorization.opMode}"/>
                            <h:outputLabel id="lblOpDt" styleClass="label" value="Opening Date"/>
                            <h:outputText id="txtOpDt" style="output" value="#{InwardAuthorization.openDate}"/>
                            <h:outputLabel id="lblPanNo" styleClass="label" value="Pan No"/>
                            <h:outputText id="txtPanNo" style="output" value="#{InwardAuthorization.custPanNo}"/>
                        </h:panelGrid>

                        <h:panelGrid id="sigViewRow3" columns="4" columnClasses="col1,col51,col3,col3" width="100%" styleClass="row2" style="text-align:left;">
                            <h:outputLabel id="lblJtName" styleClass="label" value="Jt.Name"/>
                            <h:outputText id="txtJtName" style="output" value="#{InwardAuthorization.jtName}"/>
                            <h:outputLabel id="lblSigAnnualTrunOver" styleClass="label" value="Annual Turnover"/>
                            <h:outputText id="txtSigAnnualTrunOver" style="output" value="#{InwardAuthorization.annualTurnover}"/>
                        </h:panelGrid>

                        <h:panelGrid id="sigViewRow4" columns="4" columnClasses="col1,col51,col3,col3" width="100%" styleClass="row1" style="text-align:left;">
                            <h:outputLabel id="lblAcInst" styleClass="label" value="A/c Instruction"/>
                            <h:outputText id="txtAcIns" style="output" value="#{InwardAuthorization.accInstruction}"/>
                            <h:outputLabel id="lblProfession" styleClass="label" value="Profession"/>
                            <h:outputText id="txtProfession" style="output" value="#{InwardAuthorization.profession}"/>
                        </h:panelGrid>

                        <h:panelGrid id="custIdPanelGrid" columns="4" columnClasses="col1,col51,col3,col3" width="100%" styleClass="row2" style="text-align:left;">
                            <h:outputLabel id="lblSigRiskCat" styleClass="label" value="Risk Category"/>
                            <h:outputText id="txtSigRiskCat" style="output" value="#{InwardAuthorization.riskCategorization}"/>
                            <h:outputLabel id="lblSigDpLimit" styleClass="label" value="DP Limit"/>
                            <h:outputText id="txtSigDpLimit" style="output" value="#{InwardAuthorization.dpLimit}"/>
                        </h:panelGrid>

                        <h:panelGrid id="Panel" style="width:100%;text-align:center;">
                            <h:panelGroup layout="block" id="sigViewPanel" style="overflow:auto;width:700px;height:300px;text-align:center;background-color:#e8eef7">
                                <a4j:mediaOutput id="signature" element="img" createContent="#{InwardAuthorization.createSignature}" value="#{InwardAuthorization.accNo}"/>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid id="sigViewFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup id="sigViewBtnPanel">
                                <a4j:commandButton id="sigViewClose" value="Close" onclick="#{rich:component('sigView')}.hide(); return false;">
                                </a4j:commandButton>
                            </h:panelGroup>
                        </h:panelGrid>
                    </a4j:form>
                </rich:modalPanel>
                <rich:messages></rich:messages>
                <%--<a4j:jsFunction name="showWhilteImage" action="#{InwardAuthorization.getWhiteImage}" reRender="chqImage" />
                <a4j:jsFunction name="showBackImage" action="#{InwardAuthorization.getBackImage}" reRender="chqImage" />
<a4j:jsFunction name="showGrayImage" action="#{InwardAuthorization.getGrayImage}" reRender="chqImage" />--%>
                <a4j:jsFunction name="showAcDeatil" oncomplete="if(#{InwardAuthorization.accViewFlag=='true'}){#{rich:component('acView')}.show();}" reRender="acView,errmsg" action="#{InwardAuthorization.acctViewAuthUnAuth}"/>
                <a4j:jsFunction name="showSignDetail" oncomplete="if(#{InwardAuthorization.accViewFlag=='true'}){ #{rich:component('sigView')}.show();}" reRender="sigView,errmsg" action="#{InwardAuthorization.getSignatureDetail}"/>

                <rich:hotKey key="F7" handler="showSignDetail();"/>
                <%--<rich:hotKey key="F8" handler="showWhilteImage();"/>
                <rich:hotKey key="F9" handler="showBackImage();"/>
<rich:hotKey key="F10" handler="showGrayImage();"/>--%>
                <rich:hotKey id="F4Key"key="F4" handler="showAcDeatil();"/>
                <%--New Addition On 26/12/2015--%>
                <a4j:region id="processActionRegion">
                    <rich:modalPanel id="processPanel" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
                        <f:facet name="header">
                            <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                        </f:facet>
                        <h:form>
                            <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                <tbody>
                                    <tr style="height:40px">
                                        <td colspan="2">
                                            <h:outputText id="confirmid" value="#{InwardAuthorization.confirmationMsg}"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="Yes" id="btnYes" action="#{InwardAuthorization.setExceedTransaction}" onclick="#{rich:component('processPanel')}.hide();" 
                                                               reRender="errmsg"/>
                                        </td>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="No" id="btnNo" action="#{InwardAuthorization.odBalExceedStopProcess}" 
                                                               onclick="#{rich:component('processPanel')}.hide();"
                                                               reRender="errmsg,datalist,image,ddReason,btnPost,btnComplete"/>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </h:form>
                    </rich:modalPanel>
                </a4j:region>
                <%--New Addition On 26/12/2015 End Here--%>
            </a4j:form>
        </body>
    </html>
</f:view>
