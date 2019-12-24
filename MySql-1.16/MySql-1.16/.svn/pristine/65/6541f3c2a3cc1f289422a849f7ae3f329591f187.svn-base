<%-- 
    Document   : PassBookPrint
    Created on : Oct 27, 2010, 11:04:11 AM
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
            <title>Print A PassBook</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
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
            <a4j:form id="passBook">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="grpPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{PassBookPrint.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Pass Book Printing"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{PassBookPrint.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <rich:tabPanel>
                        <rich:tab label="Print PassBook" switchType="ajax" title="Print PassBook" action="#{PassBookPrint.clearAll}" reRender="prnPassBookPanel,issPassBookPanel,canPassBookPanel,taskList1">
                            <h:panelGrid id="prnPassBookPanel" columns="2" style="width:100%;text-align:center;">
                                <h:panelGrid id="leftPanel1" style="width:100%;text-align:center;">
                                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row5" style="width:100%;text-align:left;" styleClass="row1">
                                        <h:outputLabel id="lblPrnAcNo" styleClass="label" value="Account No"/>
                                        <h:inputText maxlength="#{PassBookPrint.acNoMaxLen}" value="#{PassBookPrint.prnAcNo}" id="txtPrnAcNo" styleClass="input" style="120px">
                                            <a4j:support action="#{PassBookPrint.getPrnAccountDetail}" event="onblur" ajaxSingle="true" 
                                                         reRender="txtPrnAcNo,prnAcNoMsg,Row6,Row7,btnRePrn,newAcno" limitToList="true"
                                                         oncomplete="if((#{PassBookPrint.msgFlag} == 4)) {#{rich:element('txtPrnAcNo')}.focus();}
                                                         else #{rich:element('btnPrn')}.focus();"/>
                                        </h:inputText>
                                        <h:outputLabel id="newAcno" value="#{PassBookPrint.printNewAcno}" styleClass="label" style="padding-right:60px"/>
                                        <h:outputText id="prnAcNoMsg" styleClass="error" value="#{PassBookPrint.prnAcNoMsg}"/>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row6" style="width:100%;text-align:left;" styleClass="row2">
                                        <h:outputLabel id="lblPrnName" styleClass="label" value="Name"/>
                                        <h:outputText id="prnAcName" value="#{PassBookPrint.prnAcName}"/>
                                        <h:outputLabel id="lblPrnBal" styleClass="label" value="Balance"/>
                                        <h:outputText id="prnAcBal" value="#{PassBookPrint.prnBalance}"/>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col7,col2,col2,col7" columns="4" id="Row7" style="width:100%;text-align:left;" styleClass="row1">
                                        <h:outputLabel/>
                                        <a4j:commandButton id="btnPrn" value="Print" onclick="#{rich:element('btnPrn')}.disabled=true" action="#{PassBookPrint.prnPassBook}" reRender="btnRePrn,stxtMsg,stxtMsg2,prnAcNoMsg" oncomplete="if(#{PassBookPrint.flag2=='true'}){#{rich:component('showPanel1')}.show();} else if(#{PassBookPrint.flag2=='true2'}){#{rich:component('showPanel2')}.show();#{rich:component('showPanel1')}.hide();} else{#{rich:component('showPanel1')}.hide();#{rich:component('showPanel2')}.hide();}"/>
                                        <a4j:region id="reprintRegion">
                                            <a4j:commandButton id="btnRePrn" value="RePrint" action="#{PassBookPrint.reprintPassbook}" style="display:#{PassBookPrint.flag1};" reRender="prnAcNoMsg"/>
                                        </a4j:region>

                                        <h:outputLabel/>
                                        <rich:modalPanel id="showPanel1" autosized="true" width="250" onshow="#{rich:element('btnPrintYes')}.focus()">
                                            <f:facet name="header">
                                                <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                                            </f:facet>
                                            <f:facet name="controls">
                                                <h:panelGroup>
                                                    <h:graphicImage  value="images/close.png" styleClass="hidelink" id="hidelink1" />
                                                    <rich:componentControl for="showPanel1" attachTo="hidelink1" operation="hide" event="onclick" />
                                                </h:panelGroup>
                                            </f:facet>
                                            <h:form>
                                                <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                                    <tbody>
                                                        <tr style="height:40px">
                                                            <td align="center" width="50%" colspan="2">
                                                                <h:outputText id="stxtMsg" style="align:center;"styleClass="output" value="#{PassBookPrint.flag3}"/>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td align="center" width="50%">
                                                                <a4j:commandButton  id="btnPrintYes" value="YES" ajaxSingle="true" reRender="stxtMsg2"action="#{PassBookPrint.printStatusOKClick}"oncomplete="if(#{PassBookPrint.flag4=='true'}){#{rich:component('showPanel2')}.show();#{rich:component('showPanel1')}.hide();} else if(#{PassBookPrint.flag4=='false'}){#{rich:component('showPanel2')}.hide();#{rich:component('showPanel1')}.hide();}" />
                                                            </td>
                                                            <td align="center" width="50%">
                                                                <a4j:commandButton value="NO" ajaxSingle="true" action="#{PassBookPrint.clickWhenPrintFail}"reRender="stxtMsg3" oncomplete="if(#{PassBookPrint.flag6=='true'}){#{rich:component('showPanel3')}.show();#{rich:component('showPanel2')}.hide();} else{#{rich:component('showPanel3')}.hide();#{rich:component('showPanel2')}.hide();}" />
                                                            </td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                            </h:form>
                                        </rich:modalPanel>
                                    </h:panelGrid>
                                    <rich:modalPanel id="showPanel2" autosized="true" width="250" onshow="#{rich:element('btnPrintOk')}.focus()">
                                        <f:facet name="header">
                                            <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                                        </f:facet>
                                        <f:facet name="controls">
                                            <h:panelGroup>
                                                <h:graphicImage  value="images/close.png" styleClass="hidelink" id="hidelink2" />
                                                <rich:componentControl for="showPanel2" attachTo="hidelink2" operation="hide" event="onclick" />
                                            </h:panelGroup>
                                        </f:facet>
                                        <h:form>
                                            <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                                <tbody>
                                                    <tr style="height:40px">
                                                        <td align="center" width="50%" colspan="2">
                                                            <h:outputText  id="stxtMsg2" styleClass="output" value="#{PassBookPrint.reply}"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td align="center" width="50%">
                                                            <a4j:commandButton id="btnPrintOk" value="OK" ajaxSingle="true" reRender="prnAcNoMsg"onclick="#{rich:component('showPanel2')}.hide();#{rich:element('prnAcNoMsg')}.show();" />
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </h:form>
                                    </rich:modalPanel>
                                    <rich:modalPanel id="showPanel3" autosized="true" width="250" onshow="#{rich:element('btnPrinYes')}.focus()">
                                        <f:facet name="header">
                                            <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                                        </f:facet>
                                        <f:facet name="controls">
                                            <h:panelGroup>
                                                <h:graphicImage  value="images/close.png" styleClass="hidelink" id="hidelink3" />
                                                <rich:componentControl for="showPanel3" attachTo="hidelink3" operation="hide" event="onclick" />
                                            </h:panelGroup>
                                        </f:facet>
                                        <h:form>
                                            <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                                <tbody>
                                                    <tr style="height:40px">
                                                        <td align="center" width="50%" colspan="2">
                                                            <h:outputText  id="stxtMsg3" styleClass="output" value="#{PassBookPrint.flag5}"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td align="center" width="50%">
                                                            <a4j:commandButton id="btnPrinYes" value="YES" action="#{PassBookPrint.clickWhenPrintAgain}" oncomplete="#{rich:component('showPanel3')}.hide();#{rich:component('showPanel1')}.hide();return false;"/>
                                                        </td>
                                                        <td align="center" width="50%">
                                                            <a4j:commandButton value="NO" onclick="#{rich:component('showPanel3')}.hide();#{rich:component('showPanel1')}.hide();return false;" />
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </h:form>
                                    </rich:modalPanel>
                                </h:panelGrid>
                            </h:panelGrid>
                        </rich:tab>
                        <rich:tab label="Issue PassBook" switchType="ajax" title="Issue PassBook" action="#{PassBookPrint.clearAll}" reRender="issPassBookPanel,prnPassBookPanel,canPassBookPanel,taskList1">
                            <h:panelGrid id="issPassBookPanel" columns="2" style="width:100%;text-align:center;">
                                <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row1" style="width:100%;text-align:left;" styleClass="row1">
                                        <h:outputLabel id="lblAcNo" styleClass="label" value="Account No"/>
                                        <h:inputText maxlength="#{PassBookPrint.acNoMaxLen}" value="#{PassBookPrint.acNo}" id="txtAcNo" styleClass="input" style="120px">
                                            <a4j:support action="#{PassBookPrint.getIssAccountDetail}" event="onblur" ajaxSingle="true"
                                                         reRender="txtAcNo,acNoMsg,Row2,Row3,gridPanel1,inewAcno" limitToList="true"
                                                         oncomplete="if((#{PassBookPrint.msgFlag} == 4)) {#{rich:element('txtAcNo')}.focus();}
                                                         else #{rich:element('txtPassBookNo')}.focus();
                                                         #{rich:element('prnFrmDt')}.style=setMask();"/>
                                        </h:inputText>
                                        <h:outputLabel id="inewAcno" value="#{PassBookPrint.issueNewAcno}" styleClass="label" style="padding-right:60px"/>
                                        <h:outputText id="acNoMsg" styleClass="error" value="#{PassBookPrint.acNoMsg}"/>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row2" style="width:100%;text-align:left;" styleClass="row2">
                                        <h:outputLabel id="lblName" styleClass="label" value="Name"/>
                                        <h:outputText id="acName" value="#{PassBookPrint.acName}"/>
                                        <h:outputLabel id="lblBal" styleClass="label" value="Balance"/>
                                        <h:outputText id="acBal" value="#{PassBookPrint.balance}"/>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row3" style="width:100%;text-align:left;" styleClass="row1">
                                        <h:outputLabel id="lblPassBNo" styleClass="label" value="Pass Book No"/>
                                        <h:inputText value="#{PassBookPrint.passBookNo}" id="txtPassBookNo" styleClass="input" style="120px">
                                            <a4j:support event="onblur" action="#{PassBookPrint.onblurPassbookNumber}" reRender="acNoMsg"></a4j:support>

                                        </h:inputText>
                                        <h:outputLabel id="lblPrnFrm" styleClass="label" value="Print From"/>
                                        <h:panelGroup id="groupPanelapptDt" layout="block">
                                            <h:inputText id="prnFrmDt" styleClass="input calInstDate"   style="width:70px;setMask()" maxlength="10" value="#{PassBookPrint.printFrom}">
                                                <f:convertDateTime pattern="dd/MM/yyyy" />
                                            </h:inputText>
                                            <h:outputLabel id="lblapptDT" styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                                        </h:panelGroup>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col7,col2,col2,col7" columns="4" id="Row4" style="width:100%;text-align:left;" styleClass="row2">
                                        <h:outputLabel/>
                                        <a4j:commandButton id="btnIssue" value="Issue" action="#{PassBookPrint.clickIssueButton}" reRender="acNoMsg,txtPassBookNo,stxtMsgForIssuePass" oncomplete="if(#{PassBookPrint.flag7=='true'}){#{rich:component('issuePassbookPanel')}.show();} else{#{rich:component('issuePassbookPanel')}.hide();}"/>
                                        <a4j:commandButton id="btnFPrn" value="First Print" action="#{PassBookPrint.firstPrintClick}" oncomplete="if(#{PassBookPrint.flag7=='true'}){#{rich:component('issuePassbookPanel')}.show();} else{#{rich:component('issuePassbookPanel')}.hide();}" reRender="acNoMsg,stxtMsgForIssuePass"/>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel1" width="100%" styleClass="row2" style="height:200px;">
                                        <rich:dataTable value="#{PassBookPrint.issPassList}" var="dataItem1" rowClasses="gridrow1,gridrow2" id="taskList1" columnsWidth="100"
                                                        rowKeyVar="row" onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                        onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%" >
                                            <f:facet name="header">
                                                <rich:columnGroup>
                                                    <rich:column colspan="5"><h:outputText value="Pass Book Issuing History" /></rich:column>
                                                    <rich:column breakBefore="true"><h:outputText value="Account No"/></rich:column>
                                                    <rich:column><h:outputText value="Cust Name"/></rich:column>
                                                    <rich:column><h:outputText value="PassBkNo"/></rich:column>
                                                    <rich:column><h:outputText value="IssueDT"/></rich:column>
                                                    <rich:column><h:outputText value="Status"/></rich:column>
                                                </rich:columnGroup>
                                            </f:facet>
                                            <rich:column><h:outputText value="#{dataItem1.accountNo}"/></rich:column>
                                            <rich:column><h:outputText value="#{dataItem1.custName}"/></rich:column>
                                            <rich:column><h:outputText value="#{dataItem1.passBkNo}"/></rich:column>
                                            <rich:column><h:outputText value="#{dataItem1.issDt}"/></rich:column>
                                            <rich:column><h:outputText value="#{dataItem1.status}"/></rich:column>
                                        </rich:dataTable>
                                    </h:panelGrid>
                                    <rich:modalPanel id="showPanel" autosized="true" width="250" onshow="#{rich:element('btnOk')}.focus()">
                                        <f:facet name="header">
                                            <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                                        </f:facet>
                                        <f:facet name="controls">
                                            <h:panelGroup>
                                                <h:graphicImage  value="images/close.png" styleClass="hidelink" id="hidelink" />
                                                <rich:componentControl for="showPanel" attachTo="hidelink" operation="hide" event="onclick" />
                                            </h:panelGroup>
                                        </f:facet>
                                        <h:form>
                                            <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                                <tbody>
                                                    <tr style="height:40px">
                                                        <td align="center" width="50%" colspan="2">
                                                            <h:outputText  id="stxtMesg" styleClass="output" value="#{PassBookPrint.flag3}"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td align="center" width="50%">
                                                            <a4j:commandButton id="btnOk" value="OK" onclick="#{rich:component('showPanel')}.hide();return false;" />
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </h:form>
                                    </rich:modalPanel>
                                    <rich:modalPanel id="issuePassbookPanel" autosized="true" width="250" onshow="#{rich:element('btnIssueYes')}.focus()">
                                        <f:facet name="header">
                                            <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                                        </f:facet>
                                        <f:facet name="controls">
                                            <h:panelGroup>
                                                <h:graphicImage  value="images/close.png" styleClass="hidelink" id="hideLinkForIssuePass" />
                                                <rich:componentControl for="issuePassbookPanel" attachTo="hideLinkForIssuePass" operation="hide" event="onclick" />
                                            </h:panelGroup>
                                        </f:facet>
                                        <h:form>
                                            <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                                <tbody>
                                                    <tr style="height:40px">
                                                        <td align="center" width="50%" colspan="2">
                                                            <h:outputText  id="stxtMsgForIssuePass" styleClass="output" value="#{PassBookPrint.firstPrintMsg}"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td align="center" width="50%">
                                                            <a4j:commandButton id="btnIssueYes" value="YES" ajaxSingle="true" action="#{PassBookPrint.clickToPrintFirstPage}" reRender="acNoMsg"onclick="#{rich:component('issuePassbookPanel')}.hide();" />
                                                        </td>
                                                        <td align="center" width="50%">
                                                            <a4j:commandButton value="NO" onclick="#{rich:component('issuePassbookPanel')}.hide();return false;" />
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </h:form>
                                    </rich:modalPanel>
                                </h:panelGrid>
                            </h:panelGrid>
                        </rich:tab>
                        <rich:tab label="Cancel PassBook" switchType="ajax" title="Cancel PassBook" action="#{PassBookPrint.clearAll}" reRender="prnPassBookPanel,issPassBookPanel,canPassBookPanel,taskList1">
                            <h:panelGrid id="canPassBookPanel" columns="2" style="width:100%;text-align:center;">
                                <h:panelGrid id="leftPanel2" style="width:100%;text-align:center;">
                                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row8" style="width:100%;text-align:left;" styleClass="row1">
                                        <h:outputLabel id="lblCanAcNo" styleClass="label" value="Account No"/>
                                        <h:inputText maxlength="#{PassBookPrint.acNoMaxLen}" value="#{PassBookPrint.canAcNo}" id="txtCanAcNo" styleClass="input" style="120px">
                                            <a4j:support action="#{PassBookPrint.getAccountDetail}" event="onblur" ajaxSingle="true" 
                                                         reRender="txtCanAcNo,canAcNoMsg,Row9,Row10,Row11,cnewAcno" limitToList="true"
                                                         oncomplete="if((#{PassBookPrint.msgFlag} == 4)) {#{rich:element('txtCanAcNo')}.focus();}
                                                         else #{rich:element('txtReason')}.focus();"/>
                                        </h:inputText>
                                        <h:outputLabel id="cnewAcno" value="#{PassBookPrint.cancelNewAcno}" styleClass="label" style="padding-right:60px"/>
                                        <h:outputText id="canAcNoMsg" styleClass="error" value="#{PassBookPrint.canAcNoMsg}"/>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row9" style="width:100%;text-align:left;" styleClass="row2">
                                        <h:outputLabel id="lblCanName" styleClass="label" value="Name"/>
                                        <h:outputText id="canAcName" value="#{PassBookPrint.canAcName}"/>
                                        <h:outputLabel id="lblCanBal" styleClass="label" value="Balance"/>
                                        <h:outputText id="canAcBal" value="#{PassBookPrint.canBalance}"/>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row10" style="width:100%;text-align:left;" styleClass="row1">
                                        <h:outputLabel id="lblReason" styleClass="label" value="Reason"/>
                                        <h:inputText value="#{PassBookPrint.reason}" id="txtReason" styleClass="input" style="120px">
                                            <a4j:support action="#{PassBookPrint.onblurReason}" event="onblur" reRender="canAcNoMsg"/>
                                        </h:inputText>
                                        <h:outputLabel/>
                                        <h:outputLabel/>
                                    </h:panelGrid>                                    
                                    <h:panelGrid columnClasses="col7,col2,col2,col7" columns="4" id="Row11" style="width:100%;text-align:left;" styleClass="row2">
                                        <h:outputLabel/>
                                        <a4j:commandButton id="btnCanPass" value="Cancel Passbook" action="#{PassBookPrint.canPassBook}" reRender="txtCanAcNo,canAcNoMsg,Row9,Row10,Row11"/>
                                        <h:outputLabel/>
                                    </h:panelGrid>                                    
                                </h:panelGrid>
                            </h:panelGrid>
                        </rich:tab>
                    </rich:tabPanel>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnExit" value="Exit" action="#{PassBookPrint.exitBtnAction}" reRender="taskList1,Row1,Row2,Row3,Row4,txtPrnAcNo,prnAcName,prnAcBal,prnAcNoMsg,txtCanAcNo,canAcNoMsg,canAcName,canAcBal,txtReason,btnRePrn"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="reprintRegion"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
            <rich:messages></rich:messages>
            </body>
        </html>
</f:view>