<%-- 
    Document   : dspurchasetransfer
    Created on : Nov 10, 2011, 10:58:17 AM
    Author     : ANKIT VERMA
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Deadstock Transaction</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery){
                    setMask();
                    setYearMask();
                });
                var row;
                function setMask(){
                    jQuery(".calInstDate").mask("99/99/9999");
                }
                function setYearMask()
                {
                    jQuery(".calSeqYear").mask("9999");
                }
            </script>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <%--  <a4j:keepAlive beanName="DsPurchaseTransfer"/> --%>
            <a4j:form id="form1">
                <h:panelGrid id="PanelGridMain" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="HeaderPanel" columns="3"  style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{DsPurchaseTransfer.todayDate}" />
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="Deadstock Transaction"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUsername" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="otUsername" styleClass="output" value="#{DsPurchaseTransfer.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="BodyPanel0" styleClass="row2"  columns="1" style="border:1px ridge #BED6F8;text-align:center;height:30px"  width="100%">
                        <h:outputLabel id="errmsg" value="#{DsPurchaseTransfer.msg}"  styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col5,col8" columns="2" id="bodyPanel" style="width:100%;text-align:center;">
                        <h:panelGrid id="leftPanel" style="width:100%;text-align:center;" >
                            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="row1" style="text-align:left;" width="100%" styleClass="row1">
                                <h:outputLabel id="label1" styleClass="label" value="Account No."/>
                                <a4j:region id="acnoRegion">
                                     <a4j:status onstart="#{rich:component('wait')}.show()" for="acnoRegion" onstop="#{rich:component('wait')}.hide()" />
                                     <h:panelGroup>
                                     <h:inputText id="txtAcNo" styleClass="input" maxlength="#{DsPurchaseTransfer.acNoMaxLen}" value="#{DsPurchaseTransfer.oldAcno}" style="120px">
                                         <a4j:support event="onblur" ajaxSingle="true" action="#{DsPurchaseTransfer.onBlurOfAcno}" oncomplete="#{rich:element('listTransactionMode')}.focus();" reRender="newAcno,errmsg,outtextaccountname,savemain,txtSeqenceNo,txtYear,txtFavorOf,txtCustName,panelGrpCrDr,stxtBal,stxtBalCrDr"/>
                                    </h:inputText>
                                         <h:outputText id="newAcno" styleClass="output" style="color:green" value="#{DsPurchaseTransfer.acno}"/>
                                     </h:panelGroup>
                                </a4j:region>
                                <h:outputLabel value="Account Name" styleClass="output"/>
                                <h:outputText id="outtextaccountname" value="#{DsPurchaseTransfer.accountName}" styleClass="label" style="color:green"/>
                            </h:panelGrid>
                            <h:panelGrid id="panelGrid2" width="100%" columns="4" columnClasses="col2,col7,col2,col7" style="text-align:left" styleClass="row2" >
                                <h:outputLabel id="lblBalance" value="Balance" styleClass="label" />
                                 <h:panelGroup id="panelGrpCrDr" layout="block">
                                    <h:outputText id="stxtBal" styleClass="output" value="#{DsPurchaseTransfer.balan}">
                                        <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                                    </h:outputText>
                                    <h:outputText id="stxtBalCrDr" styleClass="output" value="#{DsPurchaseTransfer.labelCrDr}"/>
                                </h:panelGroup>
                                <h:outputLabel id="lblTransaction" value="Transaction Mode" styleClass="label"/>
                                <h:selectOneListbox id="listTransactionMode" style="width:80px" styleClass="ddlist" size="1" value="#{DsPurchaseTransfer.tranMode}">
                                    <f:selectItems value="#{DsPurchaseTransfer.transactionModeList}"/>
                                    <a4j:support ajaxSingle="true" event="onblur" action="#{DsPurchaseTransfer.onBlurOfMode}" reRender="listBranch,listRespondingBranch"/>
                                </h:selectOneListbox>
                            </h:panelGrid>
                             <h:panelGrid id="panelGri2" width="100%" columns="4" columnClasses="col2,col7,col2,col7" style="text-align:left" styleClass="row1" >
                                <h:outputLabel id="lblSequenceNo" value="Sequence no" styleClass="label"/>
                                <h:inputText id="txtSeqenceNo" styleClass="input" value="#{DsPurchaseTransfer.seqenceNo}" size="5" disabled="#{DsPurchaseTransfer.disableSeqNo}" >
                                </h:inputText>

                                <h:outputLabel id="lblYear" value="Year" styleClass="label"/>
                                <h:panelGroup>
                                    <h:inputText id="txtYear" styleClass="input calSeqYear" style="setYearMask();" value="#{DsPurchaseTransfer.year}" size="3"  maxlength="4" disabled="#{DsPurchaseTransfer.disableYear}">
                                </h:inputText>
                                 <h:outputLabel id="lblYYYY" styleClass="label" value="YYYY" style="color:purple"/>
                                </h:panelGroup>

                             </h:panelGrid>

                            <h:panelGrid id="panelGrid3" width="100%" columns="4" columnClasses="col2,col7,col2,col7" style="text-align:left" styleClass="row2" >

                                <h:outputLabel id="lblBranch" value="Origin Branch" styleClass="output"/>
                                <h:selectOneListbox id="listBranch" style="width:80px" styleClass="ddlist" size="1" value="#{DsPurchaseTransfer.originBranch}" disabled="#{DsPurchaseTransfer.disableOriginBranch}">
                                    <f:selectItems value="#{DsPurchaseTransfer.branchList}"/>
                                    <a4j:support ajaxSingle="true" event="onblur"  />
                                </h:selectOneListbox>
                                <h:outputLabel id="lblRespondingBranch" value="Responding Branch" styleClass="output"/>
                                <h:selectOneListbox id="listRespondingBranch" style="width:80px" styleClass="ddlist" size="1"  value="#{DsPurchaseTransfer.destBranch}" disabled="#{DsPurchaseTransfer.disableDestBranch}" >
                                    <f:selectItems value="#{DsPurchaseTransfer.distBranchList}"/>
                                    <a4j:support ajaxSingle="true" event="onblur"  />
                                </h:selectOneListbox>
                            </h:panelGrid>
                            <h:panelGrid id="panelGrid4" width="100%" columns="4" columnClasses="col2,col7,col2,col7" style="text-align:left"styleClass="row1" >
                                <h:outputLabel id="lblTranRef" value="Transaction Ref." styleClass="label"/>
                                <h:inputText id="txtTranRef" styleClass="input" onkeyup="this.value = this.value.toUpperCase();" maxlength="20" value="#{DsPurchaseTransfer.tranRef}"
                                             style="120px">

                                </h:inputText>
                                <h:outputLabel id="lblTranRefDate" value="Ref. Date" styleClass="label"/>
                                <h:panelGroup>
                                    <h:inputText id="txtRefDate" styleClass="input calInstDate"  style="width:70px;setMask()"  maxlength="10" value="#{DsPurchaseTransfer.refDate}"  >
                                        <f:convertDateTime pattern="dd/MM/yyyy" />
                                    </h:inputText>
                                    <h:outputLabel id="lblDurationFromProduct1" styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                                </h:panelGroup>
                            </h:panelGrid>
                            <h:panelGrid id="panelGrid20" width="100%" columns="4" columnClasses="col2,col7,col2,col7" style="text-align:left" styleClass="row2">

                                <h:outputLabel id="lblType" value="Type" styleClass="label"/>
                                <h:selectOneListbox id="listType" style="width:80px" styleClass="ddlist" size="1" value="#{DsPurchaseTransfer.type}">
                                    <f:selectItems value="#{DsPurchaseTransfer.tranTypeList}"/>
                                    <a4j:support  event="onblur" action="#{DsPurchaseTransfer.onblurType}" ajaxSingle="true" oncomplete="if(#{DsPurchaseTransfer.purflag=='true'})
                                                  {
                                                  #{rich:component('purchaseView')}.show();
                                                  }
                                                  else if(#{DsPurchaseTransfer.issueFlag=='true'})
                                                  {
                                                  #{rich:component('issueView')}.show();
                                                  }
                                                  else if(#{DsPurchaseTransfer.writeFlag=='true'})
                                                  {
                                                    #{rich:component('writeView')}.show();
                                                  }"
                                                  reRender="writeView,issueView,itemCodeListBox,itemCodeListBox1,purchaseView,taskList1,codeLabel,errmsg,txtRate,txtQuantity,txtTranTotal"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblBy" value="By" styleClass="output"/>
                                <h:selectOneListbox id="listBy" style="width:80px" styleClass="ddlist" size="1" value="#{DsPurchaseTransfer.tranBy}">
                                    <f:selectItems value="#{DsPurchaseTransfer.tranByList}"/>
                                </h:selectOneListbox>
                            </h:panelGrid>
                            <h:panelGrid id="panelGrid5" width="100%" columns="4" columnClasses="col2,col7,col2,col7" style="text-align:left" styleClass="row1" >

                                <h:outputLabel id="lblInstNo" value="Inst No." styleClass="label"/>
                                <h:inputText id="txtInstNo" styleClass="input" onkeyup="this.value = this.value.toUpperCase();" disabled="#{DsPurchaseTransfer.disableInstNo}" value="#{DsPurchaseTransfer.instNo}"  >

                                </h:inputText>
                                <h:outputLabel id="lblInstDate" value="Inst. Date" styleClass="label"/>
                                <h:panelGroup>
                                    <h:inputText id="txtInstDate" styleClass="input calInstDate"  style="width:70px;setMask()"  maxlength="10" value="#{DsPurchaseTransfer.instDate}" disabled="#{DsPurchaseTransfer.disableInstDate}" >
                                        <f:convertDateTime pattern="dd/MM/yyyy" />
                                    </h:inputText>
                                    <h:outputLabel  styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                                </h:panelGroup>
                            </h:panelGrid>
                            <h:panelGrid id="panelGrid6" width="100%" columns="4" columnClasses="col2,col7,col2,col7"style="text-align:left" styleClass="row2" >

                                <h:outputLabel id="lblInvoiceNo" value="Invoice No." styleClass="label"/>
                                <h:inputText id="txtInvoiceNo" styleClass="input" onkeyup="this.value = this.value.toUpperCase();" value="#{DsPurchaseTransfer.invoiceNo}" >

                                </h:inputText>
                                <h:outputLabel id="lblInvoiceDate" value="Invoice Date" styleClass="label"/>
                                <h:panelGroup>
                                    <h:inputText id="txtInvoiceDate" styleClass="input calInstDate"  style="width:70px;setMask()"  maxlength="10" value="#{DsPurchaseTransfer.invoiceDate}"  >
                                        <f:convertDateTime pattern="dd/MM/yyyy" />
                                    </h:inputText>
                                    <h:outputLabel  styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                                </h:panelGroup>

                            </h:panelGrid>
                             <h:panelGrid id="panelGrid2255" width="100%" columns="4" columnClasses="col2,col7,col2,col7" style="text-align:left" styleClass="row1" >
                                 <h:outputLabel id="lblFavorOf" value="Favour Of" styleClass="label"/>
                                <h:inputText id="txtFavorOf" styleClass="input" onkeyup="this.value = this.value.toUpperCase();" value="#{DsPurchaseTransfer.favorOf}" disabled="#{DsPurchaseTransfer.disableFavorOf}">
                                    <a4j:support  event="onblur" />
                                </h:inputText>
                                <h:outputLabel id="lblCustName" value="Customer Name" styleClass="label"/>
                                <h:inputText id="txtCustName" styleClass="input" value="#{DsPurchaseTransfer.custName}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{DsPurchaseTransfer.disableCustName}">
                                    <a4j:support  event="onblur" />
                                </h:inputText>
                            </h:panelGrid>
                            <h:panelGrid id="panelGrid22" width="100%" columns="4" columnClasses="col2,col7,col2,col7" style="text-align:left" styleClass="row2" >

                                <h:outputLabel id="lblDetails" value="Details" styleClass="label"/>
                                <h:inputText id="txtDetails" styleClass="input" onkeyup="this.value = this.value.toUpperCase();" value="#{DsPurchaseTransfer.details}" >
                                    <a4j:support  event="onblur" />
                                </h:inputText>
                                <h:outputLabel id="lblAmount" value="Amount" styleClass="label"/>
                                <h:inputText id="txtAmount" styleClass="input" value="#{DsPurchaseTransfer.amount}" disabled="#{DsPurchaseTransfer.disableAmount}">
                                    <a4j:support  event="onblur" ajaxSingle="true" action="#{DsPurchaseTransfer.checkAmountForSeqno}" reRender="savemain,errmsg"/>
                                </h:inputText>
                            </h:panelGrid>

                        </h:panelGrid>
                        <h:panelGrid id="Panelgridff" styleClass="row2" width="100%" style="height:330px"  columnClasses="vtop">
                            <h:panelGrid id="gridPanelTable" style=" width:100%;height:0px" styleClass="row1"  columnClasses="vtop">
                                <a4j:region>
                                    <rich:dataTable  value="#{DsPurchaseTransfer.batchTable}" var="dataItem1"
                                                     rowClasses="gridrow1, gridrow2" id="taskList1" rows="5" rowKeyVar="row"
                                                     onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                     onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                                     onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;">
                                        <f:facet name="header">
                                            <rich:columnGroup>

                                                <rich:column width="100px" ><h:outputText style="text-align:center" value="Account No." /></rich:column>
                                                <rich:column width="100px" ><h:outputText style="text-align:center" value="Date" /></rich:column>
                                                <rich:column width="100px" ><h:outputText style="text-align:center" value="Type" /></rich:column>
                                                <rich:column width="100px" ><h:outputText style="text-align:center" value="Credit" /></rich:column>
                                                <rich:column width="100px" ><h:outputText style="text-align:center" value="Debit" /></rich:column>
                                                <rich:column width="100px" ><h:outputText style="text-align:center" value="Details" /></rich:column>
                                                <rich:column width="100px" ><h:outputText style="text-align:center" value="Action"/></rich:column>
                                            </rich:columnGroup>
                                        </f:facet>
                                        <rich:column width="100px" ><h:outputText value="#{dataItem1.acno}" style="text-align:center" /></rich:column>
                                        <rich:column width="100px" ><h:outputText value="#{dataItem1.tranDate}" style="text-align:center" /></rich:column>
                                        <rich:column width="100px" ><h:outputText value="#{dataItem1.ty}" style="text-align:center"  /></rich:column>
                                        <rich:column width="100px" ><h:outputText value="#{dataItem1.crAmt}" style="text-align:center"  /></rich:column>
                                        <rich:column width="100px" ><h:outputText value="#{dataItem1.drAmt}" style="text-align:center"  /></rich:column>
                                        <rich:column width="100px" ><h:outputText value="#{dataItem1.details}" style="text-align:center"  /></rich:column>
                                         <rich:column>
                                            <a4j:commandLink ajaxSingle="true" id="deletelink" oncomplete="#{rich:component('deletePanel')}.show()">
                                                <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                                <f:setPropertyActionListener value="#{row}" target="#{DsPurchaseTransfer.currentRow1}" />
                                            </a4j:commandLink>
                                        </rich:column>
                                    </rich:dataTable>
                                    <rich:datascroller align="left" for="taskList1" maxPages="5"/>
                                </a4j:region>
                                  <rich:modalPanel id="deletePanel" autosized="true" width="200">
                                    <f:facet name="header">
                                        <h:outputText value="Delete this transaction?" style="padding-right:15px;" />
                                    </f:facet>
                                    <f:facet name="controls">
                                        <h:panelGroup>
                                            <h:graphicImage value="/images/modal/close.png" styleClass="hidelink" id="hidelink2" />
                                            <rich:componentControl for="deletePanel" attachTo="hidelink2" operation="hide" event="onclick" />
                                        </h:panelGroup>
                                    </f:facet>
                                    <h:form>
                                        <table width="100%">
                                            <tbody>
                                                <tr>
                                                    <td align="center" width="50%">
                                                        <a4j:commandButton value="Yes" ajaxSingle="true" action="#{DsPurchaseTransfer.delete}"
                                                                           oncomplete="#{rich:component('deletePanel')}.hide();" reRender="taskList1,txtcr,txtdr,savemain,savebatch" />
                                                    </td>
                                                    <td align="center" width="50%">
                                                        <a4j:commandButton value="Cancel" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </h:form>
                                </rich:modalPanel>
                            </h:panelGrid>
                            <h:panelGrid id="pppp" style="width:100%;text-align:center;" styleClass="footer">
                                <h:panelGroup id="group2">
                                    <a4j:region id="xferRegion">
                                        <a4j:commandButton id="savebatch" ajaxSingle="true" value="Save Batch" disabled="#{DsPurchaseTransfer.boolSaveBatch}" action="#{DsPurchaseTransfer.saveBatch}" reRender="errmsg,Panelgridff,savemain,savebatch,txtdr,txtcr" />
                                        <a4j:commandButton id="cancelbatch" ajaxSingle="true" value="Cancel Batch" action="#{DsPurchaseTransfer.btnRefresh}" reRender="savebatch,txtdr,txtcr,Panelgridff,savemain,leftPanel,purchasepanel,issuepanel,listType,errmsg"  oncomplete="#{rich:element('txtRefDate')}.style=setMask();#{rich:element('txtInstDate')}.style=setMask();#{rich:element('txtInvoiceDate')}.style=setMask(); #{rich:element('txtYear')}.style=setYearMask();"/>
                                    </a4j:region>
                                </h:panelGroup>
                            </h:panelGrid>
                            <h:panelGrid id="ulpg" columns="4" style="width:100%;height:30px;text-align:center;">
                                <h:outputLabel value="Dr Amount" styleClass="output"/>
                                <h:outputText id="txtdr" value="#{DsPurchaseTransfer.drAmt}"  styleClass="label" style="color:blue">
                                </h:outputText>
                                <h:outputLabel value="Cr Amount" styleClass="output"/>
                                <h:outputText id="txtcr" value="#{DsPurchaseTransfer.crAmt}" styleClass="label" style="color:blue">
                                </h:outputText>
                            </h:panelGrid>

                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="ppppllllll" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="group3">
                            <a4j:region id="xferRegiongh">
                                <a4j:commandButton id="savemain" value="Save" ajaxSingle="true" action="#{DsPurchaseTransfer.btnSave}" reRender="savebatch,taskList1,txtdr,txtcr,Panelgridff,savemain,leftPanel,purchasepanel,issuepanel,listType,errmsg,Panelgridff" disabled="#{DsPurchaseTransfer.disableMainSave}" oncomplete="#{rich:element('txtRefDate')}.style=setMask();#{rich:element('txtInstDate')}.style=setMask();#{rich:element('txtInvoiceDate')}.style=setMask();#{rich:element('txtYear')}.style=setYearMask();"/>
                                <a4j:commandButton id="btnRefresh" value="Refresh" ajaxSingle="true" action="#{DsPurchaseTransfer.btnRefresh}" reRender="savebatch,txtdr,txtcr,Panelgridff,savemain,leftPanel,purchasepanel,issuepanel,listType,errmsg"  oncomplete="#{rich:element('txtRefDate')}.style=setMask();#{rich:element('txtInstDate')}.style=setMask();#{rich:element('txtInvoiceDate')}.style=setMask();#{rich:element('txtYear')}.style=setYearMask();"/>
                                <a4j:commandButton id="exitmain" value="Exit" action="#{DsPurchaseTransfer.btnExit}"/>
                            </a4j:region>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="msgPanel" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputText id="stxtInstruction1" styleClass="output" value="Press F2-GL Head "style="color:blue;"/>
                    </h:panelGrid>
                    <a4j:region id="keyRegion">
                        <a4j:jsFunction name="showGlHeadDetails" action="#{DsPurchaseTransfer.selectGlHeadOnPressF2}" oncomplete="#{rich:component('glheadView')}.show();" reRender="glHeadList,dataScroGLHead"/>
                    </a4j:region>
                     <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>

                </h:panelGrid>
                <%------------------- MODAL PANEl FOR GL HEADS-------------%>
                <rich:modalPanel id="glheadView" height="350" width="700" style="align:right" autosized="true">
                    <f:facet name="header">
                        <h:outputText value="GL Heads Register" />
                    </f:facet>
                    <f:facet name="controls">
                        <h:panelGroup>
                            <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="glheadlink1"/>
                            <rich:componentControl for="glheadView" attachTo="glheadlink1" operation="hide" event="onclick"/>
                        </h:panelGroup>
                    </f:facet>
                    <a4j:form>
                        <h:panelGrid id="glheadPanel" width="100%" style="background-color:#e8eef7;height:300px;" columnClasses="vtop">
                            <rich:dataTable  var="item" value="#{DsPurchaseTransfer.listForF6}"
                                             rowClasses="gridrow1, gridrow2" id="glHeadList" rows="15"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                             width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="2"></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="GL Head" /></rich:column>
                                        <rich:column><h:outputText value="Acno" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{item.accName}"/></rich:column>
                                <rich:column><h:outputText value="#{item.glhead}"/></rich:column>
                            </rich:dataTable>
                            <rich:datascroller id="dataScroGLHead"align="left" for="glHeadList" maxPages="20" />
                            <h:panelGrid id="glheadFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                                <h:panelGroup id="glheadBtnPanel">
                                    <a4j:commandButton id="glheadClose" value="Close" onclick="#{rich:component('glheadView')}.hide(); return false;">
                                    </a4j:commandButton>
                                </h:panelGroup>
                            </h:panelGrid>
                        </h:panelGrid>
                    </a4j:form>
                </rich:modalPanel>
                <%------------------- MODAL PANEl FOR PURCHASE AND ISSUE PURPOSE-------------%>
                <rich:modalPanel id="purchaseView" height="180" width="550" autosized="true" style="align:right" onshow="#{rich:element('itemCodeListBox')}.focus()">
                    <a4j:region>
                        <h:panelGrid id="purchasepanel" width="100%">
                            <h:panelGrid id="purchasePanelHeader" styleClass="header" width="100%" style="text-align-center">
                                <h:outputLabel id="lblTitle11" styleClass="headerLabel" value="Purchase Item"/>
                            </h:panelGrid>
                            <h:panelGrid id="purchaseMessage" styleClass="row2" columns="1" width="100%" style="border:1px ridge #BED6F8;text-align:center;height:30px">
                                <h:outputLabel id="purmsg" value="#{DsPurchaseTransfer.purMessage}" styleClass="error"/>
                            </h:panelGrid>
                            <h:panelGrid id="itemCodePanel1" width="100%" columns="4" columnClasses="col2,col7,col2,col7" style="text-align:left" styleClass="row1">
                                <h:outputLabel value="Item code" styleClass="output" />
                                <h:panelGroup>
                                    <h:selectOneListbox id="itemCodeListBox" style="width:40px" styleClass="ddlist" size="1" value="#{DsPurchaseTransfer.itemCode}">
                                        <f:selectItems value="#{DsPurchaseTransfer.itemCodeList}"/>
                                        <a4j:support  event="onblur" action="#{DsPurchaseTransfer.onChangeItemCode}" ajaxSingle="true" reRender="codeLabel"/>
                                    </h:selectOneListbox>
                                    <h:outputText id="codeLabel"  value="#{DsPurchaseTransfer.itemName}" style="color:green;font-size:9px"/>
                                </h:panelGroup>
                                <h:outputLabel value="Item Rate" styleClass="label" />
                                <h:inputText id="txtRate" styleClass="input" value="#{DsPurchaseTransfer.rate}" size="10">
                                    <a4j:support ajaxSingle="true" event="onblur"/>
                                </h:inputText>
                            </h:panelGrid>
                            <h:panelGrid id="panelGrid95" width="100%" columns="4" columnClasses="col2,col7,col2,col7" style="text-align:left" styleClass="row2" >
                                <h:outputLabel id="lblQuantity" value="Item Quantity" styleClass="label"/>
                                <h:inputText id="txtQuantity" styleClass="input"    maxlength="10" value="#{DsPurchaseTransfer.itemQty}" size="10">
                                    <a4j:support ajaxSingle="true" event="onblur" action="#{DsPurchaseTransfer.calculateTotal}" reRender="txtTranTotal"/>
                                </h:inputText>

                                <h:outputLabel id="lblTranTotal" value="Total Amount" styleClass="label"/>
                                <h:outputText id="txtTranTotal" styleClass="output" value="#{DsPurchaseTransfer.tranTotal}" style="color:blue" />
                            </h:panelGrid>
                            <h:panelGrid id="buttonGrid"  style="width:100%;text-align:center;" styleClass="footer">

                                <h:panelGroup id="group1">
                                    <a4j:commandButton id="saveBtn" value="Save" ajaxSingle="true" action="#{DsPurchaseTransfer.savePurchaseAmount}" oncomplete=" #{rich:element('txtDetails')}.focus() ; if(#{DsPurchaseTransfer.purflag=='false'}){ #{rich:component('purchaseView')}.hide();}return false; "
                                                       reRender="purmsg,gridPanelTable,taskList1,txtAmount,purchaseView"></a4j:commandButton>
                                    <a4j:commandButton id="exitBtn" value="Close" onclick="#{rich:component('purchaseView')}.hide(); return false;" reRender="purchaseView"/>
                                </h:panelGroup>

                            </h:panelGrid>
                        </h:panelGrid>
                    </a4j:region>
                </rich:modalPanel>

                <%--------------- MODAL PANEL FOR ISSUE PURPOSE --------------------------%>
                <rich:modalPanel id="issueView" height="200" width="600" autosized="true" style="align:right" onshow="#{rich:element('itemCodeListBox1')}.focus()">
                    <a4j:region>
                        <h:panelGrid id="issuepanel" width="100%">
                            <h:panelGrid id="itemNoGrid" styleClass="header" width="100%" style="text-align-center">
                                <h:outputLabel id="ItemNo" styleClass="headerLabel" value="Item Transfer"/>
                            </h:panelGrid>
                            <h:panelGrid id="issuemsgGrid" styleClass="row2" columns="1" width="100%" style="border:1px ridge #BED6F8;text-align:center;height:30px">
                                <h:outputLabel id="issuemsg" value="#{DsPurchaseTransfer.issuemsg}" styleClass="error"/>
                            </h:panelGrid>
                            <h:panelGrid id="itemDescPanelGrid" width="100%" styleClass="row1" columns="2" columnClasses="col8,col8" style="text-align:left">
                                <h:panelGrid id="itemCodePanell" width="100%" columns="4" columnClasses="col13,col13,col3,col3" style="text-align:left" styleClass="row1">
                                    <h:outputLabel value="Item code" styleClass="output" />
                                        <h:selectOneListbox id="itemCodeListBox1" style="width:40px" styleClass="ddlist" size="1" value="#{DsPurchaseTransfer.itemCode}">
                                            <f:selectItems value="#{DsPurchaseTransfer.itemCodeList}"/>
                                            <a4j:support event="onblur" action="#{DsPurchaseTransfer.onChangeItemCode1}" ajaxSingle="true" reRender="codeLabel1,gridPanelTableForTransfer"/>
                                        </h:selectOneListbox>
                                    <h:outputLabel value="Item Name" styleClass="label" />
                                    <h:outputText id="codeLabel1"  value="#{DsPurchaseTransfer.itemName}" style="color:green;font-size:9px"/>
                                </h:panelGrid>
                            </h:panelGrid>

                            <h:panelGrid id="itemDescGrid12" width="100%" columns="4" columnClasses="col13,col13,col3,col3" style="text-align:left" styleClass="row2">
                                <h:outputLabel value="Quantity to be transfered" styleClass="output" />
                                <h:inputText id="txtTransfered" styleClass="input" value="#{DsPurchaseTransfer.transferedQty}" size="5" disabled="#{DsPurchaseTransfer.disableQty}">
                                    <a4j:support event="onblur" ajaxSingle="true" action="#{DsPurchaseTransfer.calculateTransferTotal}" reRender="totAmountId,issuemsg,issueSave"/>

                                </h:inputText>
                                <h:outputLabel value="Total Amount" styleClass="output"/>
                                <h:outputLabel id="totAmountId" styleClass="output" value="#{DsPurchaseTransfer.totalAmountInTransfer}" style="color:blue"/>
                            </h:panelGrid>
                            <h:panelGrid id="gridPanelTableForTransfer" style=" width:100%;height:0px" styleClass="row1"  columnClasses="vtop">
                                <a4j:region>
                                    <rich:dataTable  value="#{DsPurchaseTransfer.itemTable}" var="dataItem2"
                                                     rowClasses="gridrow1, gridrow2" id="taskList2" rows="5" rowKeyVar="row"
                                                     onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                     onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                                     onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;cursor;">
                                        <f:facet name="header">
                                            <rich:columnGroup>
                                                <rich:column width="100px" ><h:outputText style="text-align:center" value="S.no" /></rich:column>
                                                <rich:column width="100px" ><h:outputText style="text-align:center" value="Item Code" /></rich:column>
                                                <rich:column width="100px" ><h:outputText style="text-align:center" value="Item Distinctive Sno" /></rich:column>
                                                <rich:column width="100px" ><h:outputText style="text-align:center" value="Amount per Unit" /></rich:column>
                                                <rich:column width="100px" ><h:outputText style="text-align:center" value="Transfer Flag" /></rich:column>
                                            </rich:columnGroup>
                                        </f:facet>
                                        <rich:column width="100px" style="text-align:center"><h:outputText value="#{dataItem2.sno}"  /></rich:column>
                                        <rich:column width="100px" style="text-align:center"><h:outputText value="#{dataItem2.itemCode}" /></rich:column>
                                        <rich:column width="100px" style="text-align:center"><h:outputText value="#{dataItem2.itemDistinctiveSno}" /></rich:column>
                                        <rich:column width="100px" style="text-align:center"><h:outputText value="#{dataItem2.amtPerUnit}" /></rich:column>
                                        <rich:column width="100px" style="text-align:center"><h:outputText value="#{dataItem2.tranFlag}" style="cursor:pointer;"  />
                                        <a4j:support action="#{DsPurchaseTransfer.changeTranFlag}" ajaxSingle="true" event="ondblclick" reRender="issuemsg,taskList2,txtTransfered,totAmountId">
                                            <f:setPropertyActionListener value="#{row}" target="#{DsPurchaseTransfer.currentRow}" />
                                        </a4j:support>
                                        </rich:column>
                                    </rich:dataTable>
                                    <rich:datascroller align="left" for="taskList2" maxPages="5"/>
                                </a4j:region>
                            </h:panelGrid>
                            <h:panelGrid id="gridButton"  style="width:100%;text-align:center;" styleClass="footer">
                                <h:panelGroup id="grp">
                                    <a4j:commandButton id="issueSave" value="Save" ajaxSingle="true" action="#{DsPurchaseTransfer.saveTransferAmount}" reRender="issuemsg,txtAmount,taskList1"
                                                       oncomplete="#{rich:component('issueView')}.hide(); #{rich:element('txtDetails')}.focus()" disabled="#{DsPurchaseTransfer.disableIssueSave}"/>
                                    <a4j:commandButton id="issueExit" value="Close" onclick="#{rich:component('issueView')}.hide(); return false;"/>
                                </h:panelGroup>
                            </h:panelGrid>
                             <h:panelGrid id="warnPanelGrid" width="100%" columns="1" style="text-align:left" styleClass="row2">
                                 <h:outputLabel id="alertLabel" value="Please Double Click on Transfer Flag, Which Item you want to transfer->>" styleClass="output" style="color:green" />
                            </h:panelGrid>
                        </h:panelGrid>
                    </a4j:region>
                </rich:modalPanel>
                     <%--------------- MODAL PANEL FOR WRITE OFF --------------------------%>
                <rich:modalPanel id="writeView" height="200" width="600" autosized="true" style="align:right" onshow="#{rich:element('itemCodeListBox1Write')}.focus()">
                    <a4j:region>
                        <h:panelGrid id="writepanel" width="100%">
                            <h:panelGrid id="itemNoGridWrite" styleClass="header" width="100%" style="text-align-center">
                                <h:outputLabel id="writeItemNo" styleClass="headerLabel" value="Item Written Off"/>
                            </h:panelGrid>
                            <h:panelGrid id="writemsgGrid" styleClass="row2" columns="1" width="100%" style="border:1px ridge #BED6F8;text-align:center;height:30px">
                                <h:outputLabel id="writemsg" value="#{DsPurchaseTransfer.msg}" styleClass="error"/>
                            </h:panelGrid>
                            <h:panelGrid id="itemDescPanelGridWrite" width="100%" styleClass="row1" columns="2" columnClasses="col8,col8" style="text-align:left">
                                <h:panelGrid id="itemCodePanellWrite" width="100%" columns="4" columnClasses="col13,col13,col3,col3" style="text-align:left" styleClass="row1">
                                    <h:outputLabel value="Item code" styleClass="output" />
                                        <h:selectOneListbox id="itemCodeListBox1Write" style="width:40px" styleClass="ddlist" size="1" value="#{DsPurchaseTransfer.itemCode}">
                                            <f:selectItems value="#{DsPurchaseTransfer.itemCodeList}"/>
                                            <a4j:support event="onblur" action="#{DsPurchaseTransfer.onChangeItemCode1}" ajaxSingle="true" reRender="codeLabel1Write,gridPanelTableForWrite"/>
                                        </h:selectOneListbox>
                                    <h:outputLabel value="Item Name" styleClass="label" />
                                    <h:outputText id="codeLabel1Write"  value="#{DsPurchaseTransfer.itemName}" style="color:green;font-size:9px"/>
                                </h:panelGrid>
                            </h:panelGrid>

                            <h:panelGrid id="itemDescGrid12Write" width="100%" columns="4" columnClasses="col13,col13,col3,col3" style="text-align:left" styleClass="row2">
                                <h:outputLabel value="Quantity to be Write Off" styleClass="output" />
                                <h:inputText id="txtWrite" styleClass="input" value="#{DsPurchaseTransfer.transferedQty}" size="6" disabled="#{DsPurchaseTransfer.disableQty}">
                                    <a4j:support event="onblur" ajaxSingle="true" action="#{DsPurchaseTransfer.calculateTransferTotal}" reRender="totAmountIdWrite,writemsg,writeSave"/>
                                </h:inputText>
                                <h:outputLabel value="Total Amount" styleClass="output"/>
                                <h:outputLabel id="totAmountIdWrite" styleClass="output" value="#{DsPurchaseTransfer.totalAmountInTransfer}" style="color:blue"/>
                            </h:panelGrid>
                            <h:panelGrid id="gridPanelTableForWrite" style=" width:100%;height:0px" styleClass="row1"  columnClasses="vtop">
                                <a4j:region>
                                    <rich:dataTable  value="#{DsPurchaseTransfer.itemTable}" var="dataItem2"
                                                     rowClasses="gridrow1, gridrow2" id="taskList4" rows="3" rowKeyVar="row"
                                                     onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                     onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                                     onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;cursor;">
                                        <f:facet name="header">
                                            <rich:columnGroup>
                                                <rich:column width="100px" ><h:outputText style="text-align:center" value="S.no" /></rich:column>
                                                <rich:column width="100px" ><h:outputText style="text-align:center" value="Item Code" /></rich:column>
                                                <rich:column width="100px" ><h:outputText style="text-align:center" value="Item Distinctive Sno" /></rich:column>
                                                <rich:column width="100px" ><h:outputText style="text-align:center" value="Item Book Value" /></rich:column>
                                                <rich:column width="100px" ><h:outputText style="text-align:center" value="Item Sale Value" /></rich:column>
                                                <rich:column width="100px" ><h:outputText style="text-align:center" value="Write-Off Flag" /></rich:column>
                                            </rich:columnGroup>
                                        </f:facet>
                                        <rich:column width="100px" style="text-align:center"><h:outputText value="#{dataItem2.sno}"  /></rich:column>
                                        <rich:column width="100px" style="text-align:center"><h:outputText value="#{dataItem2.itemCode}" /></rich:column>
                                        <rich:column width="100px" style="text-align:center"><h:outputText value="#{dataItem2.itemDistinctiveSno}" /></rich:column>
                                        <rich:column width="100px" style="text-align:center"><h:outputText value="#{dataItem2.amtPerUnit}" /></rich:column>
                                        <rich:column width="100px" style="text-align:center"><h:inputText id="txtYear" styleClass="input" value="#{dataItem2.itemSaleValue}" size="10" /></rich:column>
                                        <rich:column width="100px" style="text-align:center"><h:outputText value="#{dataItem2.tranFlag}" style="cursor:pointer;"  />
                                        <a4j:support action="#{DsPurchaseTransfer.changeTranFlag}" ajaxSingle="true" event="ondblclick" reRender="writemsg,taskList4,txtWrite,totAmountIdWrite">
                                            <f:setPropertyActionListener value="#{row}" target="#{DsPurchaseTransfer.currentRow}" />
                                        </a4j:support>
                                        </rich:column>
                                    </rich:dataTable>
                                    <rich:datascroller align="left" for="taskList4" maxPages="5"/>
                                </a4j:region>
                            </h:panelGrid>
                            <h:panelGrid id="gridButtonWrite"  style="width:100%;text-align:center;" styleClass="footer">
                                <h:panelGroup id="grpWrite">
                                    <a4j:commandButton id="writeSave" value="Save" ajaxSingle="true" action="#{DsPurchaseTransfer.saveTransferAmount}" reRender="writemsg,txtAmount,taskList1"
                                                       oncomplete="#{rich:component('writeView')}.hide(); #{rich:element('txtDetails')}.focus()" disabled="#{DsPurchaseTransfer.disableIssueSave}"/>
                                    <a4j:commandButton id="writeExit" value="Close" onclick="#{rich:component('writeView')}.hide(); return false;"/>
                                </h:panelGroup>
                            </h:panelGrid>
                             <h:panelGrid id="warnPanelGridWrite" width="100%" columns="1" style="text-align:left" styleClass="row2">
                                 <h:outputLabel id="alertLabelWrite" value="Please Double Click on Write-Off Flag, Which Item you want to Write off->>" styleClass="output" style="color:green" />
                            </h:panelGrid>
                        </h:panelGrid>
                    </a4j:region>
                </rich:modalPanel>
            </a4j:form>
            <rich:hotKey key="F2" handler="showGlHeadDetails();"/>
        </body>
    </html>
</f:view>
